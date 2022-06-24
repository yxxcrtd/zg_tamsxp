package com.joyintech.tams.svreport.interf.chb.assetinfo.service;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.system.base.entity.SysDatadictListLink;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstCustomerInfoAddImpMapper;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
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

import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.svreport.base.service.ChbTypeMappingService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.constant.InterfConstant;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.assetinfo.dao.ChbAssetInfoInterfMapper;
import com.joyintech.tams.svreport.interf.chb.investtrade.dao.ChbMstCounterPartyInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:资产要素文件解析方式入库
 * @since 1.7.0
 * @version 1.7.0
 * @作者：weiyuqiang
 * @创建时间：2019年4月29日
 * @修改记录：
 */
 
@Service
public class SvrAssetInfoFileImpService  extends FileImpService{

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
     * mybatis的sqlsession管理核心
     */
    @Autowired
	private SqlSessionTemplate template;
    
    /**
     * 资产要素接入mapper
     */
    @Autowired
	private ChbAssetInfoInterfMapper chbAssetInfoInterfMapper;
    
    /**
     * 映射service
     */
    @Autowired
    private ChbTypeMappingService chbTypeMappingService;
    
    @Autowired
    private ChbMstCounterPartyInterfMapper chbMstCounterPartyInterfMapper;

    @Autowired
    private SvrMstCustomerInfoAddImpMapper svrMstCustomerInfoAddImpMapper;
    
    /**
     * 中债基础数据mapper
     */
    @Autowired
    private BaseMapper baseMapper;
    
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
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
    
    /**
     * 功能:从接口中获取资产要素信息
     * 描述：
     *      1.调用共通方法传入接口文件
     *      2.文件解析
     *      3.数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年4月29日
     * @param currentDate 解析文件日期
     * @param xmlFileList 下载文件list
     * @return 
     * String
     */
    @SuppressWarnings("unchecked")
    public String getAssetInfoByInterf(String currentDate) throws ParseException {
	    // 调用共通方法传入接口文件
	    List<InterfFile> xmlFileList = queryAndDownloadFiles(currentDate, null);
        for (InterfFile interfFile : xmlFileList){
            if (interfFile != null
                    && StringUtil.isNotEmpty(interfFile.getFilename())){
                // 文件解析
                List<Map<String, String>> assetInfoList = new ArrayList<Map<String, String>>();
                try {
                    // 解析文件
                    Map<String, Object> map = parseFile(interfFile);
                    assetInfoList = (List<Map<String, String>>) map.get("dataList");
                    String result = (String) map.get("remoteDir");
                    if ("fail".equals(result)){
                        return "fail";
                    }
                } catch (FileNotFoundException e) {
                    logger.error("资产要素信息解析错误！",e.getMessage(),e);
                    return "fail";
                }
                // 文件处理
                String saveResult = importDB(assetInfoList,interfFile, currentDate);
                if ("fail".equals(saveResult)){
                    return "fail";
                }
            } else {
                return "fail";
            }
        }
        return "success";
	}

	/**
	 * 功能:文件处理方法
     * 描述： 
     *      1.checkData --校验数据
     *      2.saveData --入库
	 * @since 1.7.0
	 * @version 1.7.0
	 * @author：weiyuqiang 2019年5月5日
     * @param listData 解析出的数据
     * @param interfFile 接口文件
	 * @see com.joyintech.tams.common.FileImpService#saveDataBySteps(java.util.List)
	 */
    @Override
    public String importDB(List<ExcelValidDto> listExcel,List<Map<String, String>> assetInfoList, InterfFile interfFile, String date){
        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.资产要素信息接口数据.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        checkImportData(listExcel, assetInfoList, interfFile.getFilename(), ChbCheckTableInfo.资产要素登记表INFO,interfConfig.getCoverFlag(), listExcel == null? "I" : "D");
        if (CollectionUtils.isEmpty(assetInfoList)){
            return "success";
        }
        String saveFlag = interfConfig.getContentType();
        String result = "";
        if(assetInfoList.size()>0){
            result=saveData(assetInfoList, listExcel == null?saveFlag: InterfConstants.SAVEFLAG_ADD, listExcel == null? "I" : "D");
        }
        // 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate( InterfDefine.投资交易信息接口数据.getCode());
        return result;

    }
    
    /**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月5日 
     * @param listData 数据
     * @param saveFlag:F－全量，I－增量
     * @param  dataSource 数据来源 
     * @see com.joyintech.tams.common.FileImpService#saveData()
     */
    @Override
    public String saveData(List<Map<String, String>> listData,String saveFlag,String dataSource) {
        // 解析成功或失败的标识
        boolean success = excuteInfoByStep(listData, dataSource, null, null, null,saveFlag);
        // 数据解析完成，并导入数据库成功后，将ftp文件修改名称
        if (success) {
            return "success";
        }
        return "fail";
    }

    @Override
    public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
        return null;
    }

    /**
     * 功能:批量入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年4月29日
     * @param assetInfoList 资产要素解析list
     * @param dataSource 数据来源
     * @param tmpPath 临时文件路径
     * @param table 基础表
     * @param subTable 登记表
     * @param saveFlag:F－全量，I－增量
     * @return 
     * boolean
     */
    private boolean excuteInfoByStep(List<Map<String, String>> infoList, String dataSource,
            String tmpPath, ImpTable table, ImpTable subTable,String saveFlag) {
        // 将map转换成dto
        List<ChbAssetInfo> assetInfoList = new ArrayList<>(infoList.size());
        List<String> listCloumn = baseMapper.getTableColumnList(InterfDefine.资产要素信息接口数据.getCode());
        assetInfoList = (List<ChbAssetInfo>)super.convertMapToEntity(infoList, ChbAssetInfo.class, dataSource);
        chbAssetInfoInterfMapper.deleteByAssetCodeList(assetInfoList);
        chbAssetInfoInterfMapper.deleteChbAssetInfoErroData(assetInfoList);
        // 交易对手信息
        List<ChbMstCounterParty> allCounterList = chbMstCounterPartyInterfMapper.selectAllCounterParty();
        // 交易对手信息
        Map<String,ChbMstCounterParty> counterPartyMap = allCounterList.stream().collect(
                Collectors.toMap(item->item.getCounterId(), item->item,(k1,k2)->k1));

        //获取市场机构规模数据
        List<MstCustomerInfoAdd> institList = svrMstCustomerInfoAddImpMapper.getAllMstCustomerInfoInstit();
        Map<String,String> institMap = institList.stream().collect(HashMap<String,String>::new,
                (map, item)->map.put(item.getCustomerId(), item.getPartyType1()), (m, n)->m.putAll(n));

        //获取融资人信息
        List<MstCustomerInfo> customerList = svrMstCustomerInfoAddImpMapper.getAllCustomerInfo();
        Map<String,String> customerMap = customerList.stream().collect(HashMap<String,String>::new,
                (map, item)->map.put(item.getCustomerName(), item.getCustomerId()), (m, n)->m.putAll(n));

        //获取行业二级分类与一级分类的映射关系
        List<SysDatadictListLink> industrySharesList = svrMstCustomerInfoAddImpMapper.getAllIndustryShares();
        Map<String,String> industrySharesMap = industrySharesList.stream().collect(HashMap<String,String>::new,
                (map, item)->map.put(item.getItemCode(), item.getLinkedItemCode()), (m, n)->m.putAll(n));

        for(ChbAssetInfo chbAssetInfo : assetInfoList){
            chbAssetInfo.setAssetUuid(IDGenerator.generate(36));
            // 客户主体类型映射
            if (StringUtil.isNotEmpty(chbAssetInfo.getCustomerObjType())){
                // 映射后的数据
                String customerType = chbTypeMappingService.getMappingValue(InterfConstant.MAPPINGCODE_KHZTLX,chbAssetInfo.getCustomerObjType());
                if (StringUtil.isNotEmpty(customerType)){
                    chbAssetInfo.setCustomerObjType(customerType);
                }
            }

            //处理融资人 FINANCIE
            if(StringUtil.isNotEmpty(chbAssetInfo.getFinancie())){
                 if(customerMap.get(chbAssetInfo.getFinancie()) != null){
                     chbAssetInfo.setFinancie(customerMap.get(chbAssetInfo.getFinancie()));
                 }
            }

            //处理行业一级分类
            if(StringUtil.isNotEmpty(chbAssetInfo.getIndustryShares2())){
                if(!StringUtil.isNotEmpty(chbAssetInfo.getIndustryShares())){
                    if(industrySharesMap.get(chbAssetInfo.getIndustryShares2()) != null){
                        chbAssetInfo.setIndustryShares(industrySharesMap.get(chbAssetInfo.getIndustryShares2()));
                    }
                }
            }
            //处理融资人所属行业分类，融资项目所属行业分类 CAPUSEINDULEVEL2
            if(StringUtil.isNotEmpty(chbAssetInfo.getIndustryLevel2Type())){
                if(!StringUtil.isNotEmpty(chbAssetInfo.getIndustry1())){
                    if(industrySharesMap.get(chbAssetInfo.getIndustryLevel2Type()) != null){
                        chbAssetInfo.setIndustry1(industrySharesMap.get(chbAssetInfo.getIndustryLevel2Type()));
                    }
                }
            }
            if(StringUtil.isNotEmpty(chbAssetInfo.getProIndustryLevel2())){
                if(!StringUtil.isNotEmpty(chbAssetInfo.getProIndustry())){
                    if(industrySharesMap.get(chbAssetInfo.getProIndustryLevel2()) != null){
                        chbAssetInfo.setProIndustry(industrySharesMap.get(chbAssetInfo.getProIndustryLevel2()));
                    }
                }
            }
            //处理发行机构所属行业
            if(StringUtil.isNotEmpty(chbAssetInfo.getIndustryLevel2())){
                if(!StringUtil.isNotEmpty(chbAssetInfo.getIndustry())){
                    if(industrySharesMap.get(chbAssetInfo.getIndustryLevel2()) != null){
                        chbAssetInfo.setIndustry(industrySharesMap.get(chbAssetInfo.getIndustryLevel2()));
                    }
                }
            }

            //处理资金运用行业
            if(StringUtil.isNotEmpty(chbAssetInfo.getCapUseInduLevel2())){
                if(!StringUtil.isNotEmpty(chbAssetInfo.getCapUseIndu())){
                    if(industrySharesMap.get(chbAssetInfo.getCapUseInduLevel2()) != null){
                        chbAssetInfo.setCapUseIndu(industrySharesMap.get(chbAssetInfo.getCapUseInduLevel2()));
                    }
                }
            }

            //处理基金管理机构名称和基金托管机构名称
            if(StringUtil.isNotEmpty(chbAssetInfo.getMngOrgName())){
                if(!StringUtil.isNotEmpty(chbAssetInfo.getManagerId())) {
                    chbAssetInfo.setManagerId(this.subDictcode(chbAssetInfo.getMngOrgName(), " "));
                }
            }
            if(StringUtil.isNotEmpty(chbAssetInfo.getTruOrgName())){
                if(!StringUtil.isNotEmpty(chbAssetInfo.getTrusteeId())) {
                    chbAssetInfo.setTrusteeId(this.subDictcode(chbAssetInfo.getTruOrgName(), " "));
                }
            }

            // 映射人行资产一级二级三级分类
            ChbAssetInfoInterf.convertPbcAssetType(chbAssetInfo,counterPartyMap);

            //2-5企业债券分行业、企业规模统计的列统计维度调整为根据市场机构管理中“机构类型（规模）”字段来取值。
            ChbAssetInfoInterf.convertInstitSize(chbAssetInfo,institMap);
        }

        if (assetInfoList != null && assetInfoList.size() > 0) {
            // 增量方式入库
            insertDataMerge(assetInfoList,saveFlag);
        }
        return true;
    }

    /**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月5日 
     * @param assetInfoList 资产要素信息
     * @param saveFlag:F－全量，I－增量
     */
    public void insertDataMerge(List<ChbAssetInfo> assetInfoList,String saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("资产要素插入开始--------------" + new Date());
            ChbAssetInfoInterfMapper batchMapper = session.getMapper(ChbAssetInfoInterfMapper.class);
            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < assetInfoList.size();){
                if(batchLastIndex > assetInfoList.size()-1){
                    batchLastIndex = assetInfoList.size();
                    batchMapper.mergeInfoList(assetInfoList.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.mergeInfoList(assetInfoList.subList(index, batchLastIndex));
                    index = batchLastIndex;//设置下一批下标
                    batchLastIndex = index + batchCount;                     
                }
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            logger.debug("资产要素插入结束--------------" + new Date());
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        finally {
            session.close();
        }
    }

    /**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月5日 
     * @param assetInfoList 资产要素信息
     * @param saveFlag:0-当日全删全插，1－增量(默认)
     */
    public void insertDataBatch(List<ChbAssetInfo> assetInfoList,int saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("资产要素插入开始--------------" + new Date());
            ChbAssetInfoInterfMapper batchMapper = session.getMapper(ChbAssetInfoInterfMapper.class);
            for (ChbAssetInfo asset : assetInfoList) {
                batchMapper.insert(asset);
                count++;
                if (count % 1000 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                    logger.debug("资产要素素已插入条数-----------" + count + new Date());
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        finally {
            session.close();
        }
        logger.debug("资产要素插入结束--------------" + new Date());
    }

//    @Test
//    public void test(){
//        List<ChbAssetInfo> assetInfoList = new ArrayList<>();
//        for (int i = 0;i<100;i++){
//            ChbAssetInfo asset = new ChbAssetInfo();
//            asset.setAssetCode("ASSETCODE"+i);
//            assetInfoList.add(asset);
//        }
//        logger.info("待入库{}条数据！",Integer.toString(assetInfoList.size()));
//        List<String> assetList = new ArrayList<>();
//        for (int i = 0;i<10;i++){
//            assetList.add("ASSETCODE"+i);
//        }
//        logger.info("数据库中{}条数据！",Integer.toString(assetList.size()));
//        Map<String,String> assetMap = assetList.stream().collect(Collectors.toMap(p -> p, p -> p,(key1,key2) -> key2));
//        List<ChbAssetInfo> deleteAssetList = assetInfoList.stream().filter(asset -> StringUtil.isNotEmpty(assetMap.get(asset.getAssetCode()))).collect(Collectors.toList());
//        deleteAssetList.stream().forEach(item->{
//            logger.info(item.getAssetCode());
//            });
//        logger.info("过滤数据：{}条数据！",Integer.toString(assetInfoList.size()-assetList.size()));
//        logger.info("剩余待删除数据：{}条数据！",Integer.toString(deleteAssetList.size()));
//        logger.info("源数据："+assetInfoList.get(0).getAssetCode());
//        logger.info("待删除数据："+deleteAssetList.get(0).getAssetCode());
//        assetInfoList.get(0).setAssetCode("修改咯");
//        logger.info("源数据："+assetInfoList.get(0).getAssetCode());
//        logger.info("待删除数据："+deleteAssetList.get(0).getAssetCode());
//    }

    /**
     * 功能:带提交的方式单条处理数据结果，入库
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月7日
     * @param assetList 
     * void
     */
    @SuppressWarnings("unused")
    private void dealAssetInfo(List<ChbAssetInfo> insertList,List<ChbAssetInfo> updateList){
        logger.debug("资产要素信息文件接入-插入:----开始------" );    
        if (CollectionUtils.isNotEmpty(insertList)) {
            int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"50" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            int count = 0;
            SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            try{
                ChbAssetInfoInterfMapper batchMapper =  session.getMapper(ChbAssetInfoInterfMapper.class);
                for(ChbAssetInfo asset : insertList){
                    batchMapper.insert(asset);
                    count++;
                    if(count%1000==0){
                        session.commit();
                        // 清理缓存，防止溢出
                        session.clearCache();
                    }
                }
                session.commit();
                // 清理缓存，防止溢出
                session.clearCache();
            }
            finally {
                session.close();
            }
        }
        logger.debug("资产要素信息文件接入-插入:----结束------" );    
        logger.debug("资产要素信息文件接入-更新:----开始------" );    
        if (CollectionUtils.isNotEmpty(updateList)) {
            int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"50" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            int count = 0;
            SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            try{
                ChbAssetInfoInterfMapper batchMapper =  session.getMapper(ChbAssetInfoInterfMapper.class);
                for(ChbAssetInfo asset : insertList){
                    batchMapper.insert(asset);
                    count++;
                    if(count%1000==0){
                        session.commit();
                        // 清理缓存，防止溢出
                        session.clearCache();
                    }
                }
                session.commit();
                // 清理缓存，防止溢出
                session.clearCache();
            }
            finally {
                session.close();
            }
        }
        logger.debug("资产要素信息文件接入-更新:----结束------" );   
    }


    public static String subDictcode(String value, String sign) {
        if (StringUtil.isNotEmpty(value)) {
            int i = value.indexOf(sign);
            if (i > -1) {
                value = value.substring(0, i);
            }
        }
        return value;
    }
}
