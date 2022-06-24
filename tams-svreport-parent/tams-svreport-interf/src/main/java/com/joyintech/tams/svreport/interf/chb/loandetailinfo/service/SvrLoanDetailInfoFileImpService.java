package com.joyintech.tams.svreport.interf.chb.loandetailinfo.service;

import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.ChbLoanDetailInfo;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.loandetailinfo.dao.ChbLoanDetailInfoInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SvrLoanDetailInfoFileImpService extends FileImpService {

    /**
     * CHB_LOAN_DETAIL_INFO
     */
//    private static final String CHB_LOAN_DETAIL_INFO = "CHB_LOAN_DETAIL_INFO";

    /**
     * 接口文件名称类型
     */
    private static final String[] FILENAME_LIST = {InterfDefine.贷款明细信息接口数据.getCode()};


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 重复性检查字段
     */
    private static final String[] DUPLICATIED_LIST = {"ASSET_CODE"};

    /**
     * 业务主键
     */
    private static final String BUSSINESSKEY = "ASSET_CODE";

    /**
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
    /**
     * 上游接口文件编码格式
     */
    @Value("${application.reportInterfFileEncoding}")
    public String reportInterfFileEncoding;
    /**
     * 上游接口解析分割符
     */
    @Value("${application.reportInterfSplitCharacter}")
    private String reportInterfSplitCharacter;
    /**
     * 上游接口解析读取文件处理最大线程数
     */
    @Value("${application.interfMaxCorePoolSize}")
    private int interfMaxCorePoolSize;
    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrDataCount}")
    private int reportInterfOneTimeSqlLdrDataCount;

    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
    private int reportInterfOneTimeSqlLdrCommitCount;
    /**
     * sqlldr用数据库连接
     */
    @Value("${spring.datasource.reportSqlldrDatabaseName}")
    private String reportSqlldrDatabaseName;
    /**
     * mybatis的sqlsession管理核心
     */
    @Autowired
    private SqlSessionTemplate template;


    @Autowired
    private BaseMapper baseMapper;

    @Autowired
    private ChbLoanDetailInfoInterfMapper chbLoanDetailInfoInterfMapper;
    private boolean excuteInfoByStep(List<Map<String,String>> infoList, String tmpPath, ImpTable table,
                                     ImpTable subTable, String saveFlag, String dataSource) {
//        List<ChbLoanDetailInfo> loanDetailInfoList = new ArrayList<>(infoList.size());
        List<ChbLoanDetailInfo> loanDetailInfoList;
        // modify by convertMapToEntity yangnxianjiu 2022-01-19 start
//        List<String> listColumn = baseMapper.getTableColumnList(InterfDefine.贷款明细信息接口数据.getCode());
//        loanDetailInfoList = this.convertMapToEntity(infoList,dataSource,listColumn);
        loanDetailInfoList = (List<ChbLoanDetailInfo>) convertMapToEntity(infoList,ChbLoanDetailInfo.class, dataSource);
        loanDetailInfoList.stream().forEach(chbLoanDetailInfo->{
            chbLoanDetailInfo.setAssetUuid(IDGenerator.generate(36));
        });
        // modify by convertMapToEntity yangnxianjiu 2022-01-19 end
        if (loanDetailInfoList != null && loanDetailInfoList.size() > 0) {
            insertDataMerge(loanDetailInfoList, saveFlag);
        }
        return true;
    }

    @Override
    public String saveData(List<Map<String, String>> loanDetailInfoList, String saveFlag, String dataSource) {
        //解析成功或失败的标识
        boolean success = excuteInfoByStep(loanDetailInfoList, dataSource, null, null, null, saveFlag);
        if (success) {
            return "success";
        }
        return "fail";
    }



    @Override
    public String importDB(List<Map<String, String>> loanDetailInfoList, InterfFile interfFile, String date) {
        //数据检查
        //添加待处理的数据
        // add by zhangmingming 从CHB_ASSET_INFO中读入人行资产一级分类和人行资产二级分类，用于数据校验 20191224 start
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            for (Map<String, String> loanInfo : loanDetailInfoList) {
                ChbLoanDetailInfoInterfMapper batchMapper = session.getMapper(ChbLoanDetailInfoInterfMapper.class);
                Map<String,String> typeMap = batchMapper.selectPbcAssetType(loanInfo.get("ASSET_CODE"));
                if (typeMap!=null){
                    if(typeMap.get("pbcAssetType1")!=null&&!"".equals(typeMap.get("pbcAssetType1"))&&typeMap.get("pbcAssetType2")!=null&&!"".equals(typeMap.get("pbcAssetType2"))){
                        loanInfo.put("PBC_ASSET_TYPE1",typeMap.get("pbcAssetType1"));
                        loanInfo.put("PBC_ASSET_TYPE2",typeMap.get("pbcAssetType2"));
                    }
                }
                /****单体数据判断**/
                if(StringUtil.isNotEmpty(loanInfo.get("ASSET_TYPE")) && !StringUtil.equals(loanInfo.get("ASSET_TYPE"), DictCodeListExt3.中债_中债贷款分类_除回购或拆借外贷款.getValue())) {
                    int each = 0;
                    try{
                        for (String code : loanInfo.get("EACH_PROPORTION").split(":")) {
                            each = each + Integer.parseInt(code);
                        }
                    }catch(Exception e){
                        logger.error("数据转换错误:",e);
                        each = 0;
                    }
                    loanInfo.put("EACH_PROPORTION_SUM",each+"");
                }

            }
        } finally {
            session.close();
        }
        // add by zhangmingming 从CHB_ASSET_INFO中读入人行资产一级分类和人行资产二级分类，用于数据校验 20191224 end
        checkData(loanDetailInfoList, interfFile.getFilename(),
                InterfDefine.贷款明细信息接口数据.getCode(), InterfDefine.贷款明细信息接口数据.getCode(),BUSSINESSKEY,DUPLICATIED_LIST);





        //待入库为空则直接返回
        if (CollectionUtils.isEmpty(loanDetailInfoList)){
            return "success";
        }
        String saveResult = saveData(loanDetailInfoList, null , interfFile.getDataSource());
        // 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate(InterfDefine.贷款明细信息接口数据.getCode());
        return saveResult;
    }


    public void insertDataMerge(List<ChbLoanDetailInfo> loanDetailInfoList, String saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("贷款明细信息插入开始--------------" + new Date());
            ChbLoanDetailInfoInterfMapper batchMapper = session.getMapper(ChbLoanDetailInfoInterfMapper.class);
            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;
            int batchLastIndex = batchCount;
            for(int index = 0; index < loanDetailInfoList.size();){
                if(batchLastIndex > loanDetailInfoList.size()-1){
                    batchLastIndex = loanDetailInfoList.size();
                    batchMapper.mergeInfoList(loanDetailInfoList.subList(index,batchLastIndex));
                    break;
                }else{
                    batchMapper.mergeInfoList(loanDetailInfoList.subList(index,batchLastIndex));
                    index = batchLastIndex;
                    batchLastIndex = index + batchCount;
                }
                count ++;
                if (count % 20 ==0 ){
                    session.commit();
                    session.clearCache();
                }
            }
            logger.debug("贷款明细信息插入结束--------------" + new Date());
            session.commit();
            session.clearCache();
        } finally {
            session.close();
        }
        logger.debug("贷款明细信息插入结束--------------" + new Date());
    }

    private List<ChbLoanDetailInfo> convertMapToEntity(List<Map<String,String>> infoList, String dataSource, List<String> listColumn){

        List<ChbLoanDetailInfo> entities = new ArrayList<>();
        for (Map<String, String> map : infoList) {
            ChbLoanDetailInfo chbLoanDetailInfo = new ChbLoanDetailInfo();
            for (String key : listColumn) {
                String value = map.get(key);
                Field field = ReflectionUtils.findField(chbLoanDetailInfo.getClass(),
                        this.subBlank(StringUtil.lowerCase(key)));
                field.setAccessible(true);
                Type type = field.getGenericType();
                if ("java.math.BigDecimal".equals(type.getTypeName())) {
                    try {
                        field.set(chbLoanDetailInfo,(value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if ("java.lang.Integer".equals(type.getTypeName())
                || "int".equals(type.getTypeName())){
                    try {
                        field.set(chbLoanDetailInfo,(value == null || "".equals(value)) ? 0 : Integer.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if ("java.lang.Long".equals(type.getTypeName())) {
                    try {
                        field.set(chbLoanDetailInfo, (value == null || "".equals(value)) ? 0 : Long.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if ("java.lang.Short".equals(type.getTypeName())) {
                    try {
                        field.set(chbLoanDetailInfo, (value == null || "".equals(value)) ? 0 : Short.valueOf(value));
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if("java.util.Date".equals(type.getTypeName())) {
                        try {
                            if (value != null&&""!=value) {
                                field.set(chbLoanDetailInfo, DateUtil.parse(value));
                            }
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(),e);
                            return entities;
                        }

                } else {
                    try {
                        field.set(chbLoanDetailInfo,( value == null || "".equals(value)) ? "" : value);
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }
            }
            chbLoanDetailInfo.setDataSource(SysDataUtil.getNameFromDataDict(DictCode.数据来源,dataSource));
            chbLoanDetailInfo.setAssetUuid(IDGenerator.generate(36));

            chbLoanDetailInfo.setCreateUser(CommonUtil.getUserName());
            chbLoanDetailInfo.setUpdateUser(CommonUtil.getUserName());
//            chbLoanDetailInfo.setCreateDept(CommonUtil.getDeptCode());
//            chbLoanDetailInfo.setDeptCode(CommonUtil.getDeptCode());
//            chbLoanDetailInfo.setOrgCode(CommonUtil.getOrgCode());
            entities.add(chbLoanDetailInfo);

        }

        return entities;
    }
}
