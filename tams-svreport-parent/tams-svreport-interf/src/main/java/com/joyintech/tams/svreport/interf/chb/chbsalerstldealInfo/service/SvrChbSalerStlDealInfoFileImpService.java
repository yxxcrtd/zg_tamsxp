package com.joyintech.tams.svreport.interf.chb.chbsalerstldealInfo.service;

import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.ChbSalerStlDealInfo;
import com.joyintech.tams.common.base.entity.MstExchargeQuote;
import com.joyintech.tams.common.base.entity.SaleCustSvrreport;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.chbsalerstldealInfo.dao.ChbSalerStlDealInfoMpper;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SvrChbSalerStlDealInfoFileImpService extends FileImpService {

    /**
     * 接口文件名称类型
     */
    private static final String[] FILENAME_LIST = {InterfDefine.销售结算资金交易信息表报送数据接入.getCode()};

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 重复性检查字段
     */
    private static final String[] DUPLICATIED_LIST = {"INSTITUTION_CODE","PRD_REGIST_CODE","ACCOUNT_NO","TRADE_NO"};

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
     * mybatis的sqlsession管理核心
     */
    @Autowired
    private SqlSessionTemplate template;

    /**
     * 基础共通mapper
     */
    @Autowired
    private BaseMapper baseMapper;

    /**
     * 执行步骤
     * @param infoList
     * @param tmpPath
     * @param table
     * @param subTable
     * @param saveFlag
     * @param dataSource
     * @return
     */
    private boolean excuteInfoByStep(List<Map<String,String>> infoList, String tmpPath, ImpTable table,
                                     ImpTable subTable, String saveFlag, String dataSource) {
        //销售结算资金交易信息表数据
        List<ChbSalerStlDealInfo> chbSalerStlDealInfoList;
        List<String> listColumn = baseMapper.getTableColumnList(InterfDefine.销售结算资金交易信息表报送数据接入.getCode());
        //mod by wangyaning 20220122 start
//        chbSalerStlDealInfoList = this.convertMapToEntity(infoList,dataSource,listColumn);
        chbSalerStlDealInfoList = (List<ChbSalerStlDealInfo>) convertMapToEntity(infoList,ChbSalerStlDealInfo.class, dataSource);
        for(ChbSalerStlDealInfo info:chbSalerStlDealInfoList){
            info.setRegistSeqno(IDGenerator.generate(36));
            info.setStatus("E");
        }
        //mod by wangyaning 20220122 end
        if (chbSalerStlDealInfoList != null && chbSalerStlDealInfoList.size() > 0) {
            insertDataMerge(chbSalerStlDealInfoList, saveFlag);
        }
        return true;
    }

    /**
     * 入库
     * @param chbSalerStlDealInfoList
     * @param saveFlag
     */
    private void insertDataMerge(List<ChbSalerStlDealInfo> chbSalerStlDealInfoList, String saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try{
            int count = 0;
            logger.debug("销售结算资金交易信息表数据插入开始--------------" + new Date());
            ChbSalerStlDealInfoMpper batchMapper = session.getMapper(ChbSalerStlDealInfoMpper.class);
            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;
            int batchLastIndex = batchCount;
            for(int index = 0; index < chbSalerStlDealInfoList.size();){
                if(batchLastIndex > chbSalerStlDealInfoList.size()-1){
                    batchLastIndex = chbSalerStlDealInfoList.size();
                    batchMapper.mergeInfoList(chbSalerStlDealInfoList.subList(index,batchLastIndex));
                    break;
                }else{
                    batchMapper.mergeInfoList(chbSalerStlDealInfoList.subList(index,batchLastIndex));
                    index = batchLastIndex;
                    batchLastIndex = index + batchCount;
                }
                count ++;
                if (count % 20 ==0 ){
                    session.commit();
                    session.clearCache();
                }
            }
            logger.debug("销售结算资金交易信息表数据插入结束--------------" + new Date());
            session.commit();
            session.clearCache();
        }finally {
            session.close();
        }
        logger.debug("销售结算资金交易信息表数据插入结束--------------" + new Date());
    }

    /**
     * Map 转 ENTITY
     * @param infoList
     * @param dataSource
     * @param listColumn
     * @return
     */
    private List<ChbSalerStlDealInfo> convertMapToEntity(List<Map<String, String>> infoList, String dataSource, List<String> listColumn) {
        List<ChbSalerStlDealInfo> entities = new ArrayList<>();
        for (Map<String, String> map : infoList) {
            ChbSalerStlDealInfo chbSalerStlDealInfo = new ChbSalerStlDealInfo();
            for (String key : listColumn) {
                String value = map.get(key);
                Field field = ReflectionUtils.findField(ChbSalerStlDealInfo.class,
                        this.subBlank(StringUtil.lowerCase(key)));
                field.setAccessible(true);
                Type type = field.getGenericType();
                if ("java.math.BigDecimal".equals(type.getTypeName())) {
                    try {
                        field.set(chbSalerStlDealInfo,(value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if ("java.lang.Integer".equals(type.getTypeName())
                        || "int".equals(type.getTypeName())){
                    try {
                        field.set(chbSalerStlDealInfo,(value == null || "".equals(value)) ? 0 : Integer.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if ("java.lang.Long".equals(type.getTypeName())) {
                    try {
                        field.set(chbSalerStlDealInfo, (value == null || "".equals(value)) ? 0 : Long.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if ("java.lang.Short".equals(type.getTypeName())) {
                    try {
                        field.set(chbSalerStlDealInfo, (value == null || "".equals(value)) ? 0 : Short.valueOf(value));
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if("java.util.Date".equals(type.getTypeName())) {
                    try {
                        if (value != null&&""!=value) {
                            if(field.getName().equals("tradeVdate")){
                                field.set(chbSalerStlDealInfo, DateUtil.parse(value));
                            }
                        }
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }

                } else {
                    try {
                        field.set(chbSalerStlDealInfo,( value == null || "".equals(value)) ? "" : value);
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }
            }
            //创建人
            chbSalerStlDealInfo.setCreateUser("interf");
            //更新人
            chbSalerStlDealInfo.setUpdateUser("interf");

            chbSalerStlDealInfo.setRegistSeqno(IDGenerator.generate(36));

            chbSalerStlDealInfo.setStatus("E");

            entities.add(chbSalerStlDealInfo);
        }
        return entities;
    }
    
    @Override
    public String saveData(List<Map<String, String>> chbSalerStlDealInfoList, String saveFlag, String dataSource) {
        //解析成功或失败的标识
        boolean success = excuteInfoByStep(chbSalerStlDealInfoList, dataSource, null, null, null, saveFlag);
        if (success) {
            return "success";
        }
        return "fail";
    }

    @Override
    public String importDB(List<Map<String, String>> chbSalerStlDealInfoList, InterfFile interfFile, String date) {
        //待入库为空则直接返回
        checkDataCodeCheck(chbSalerStlDealInfoList, interfFile.getFilename(), ChbCheckTableInfo.销售结算资金交易信息表INFO);
        if (CollectionUtils.isEmpty(chbSalerStlDealInfoList)){
            return "success";
        }

        String saveResult = saveData(chbSalerStlDealInfoList, null , interfFile.getDataSource());

        // 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate(InterfDefine.销售结算资金交易信息表报送数据接入.getCode());

        return saveResult;
    }
}
