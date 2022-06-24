package com.joyintech.tams.svreport.interf.chb.investtrade.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.ChbInvestTrade;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.assetinfo.dao.ChbAssetInfoInterfMapper;
import com.joyintech.tams.svreport.interf.chb.investtrade.dao.ChbInvestTradeInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:投资交易文件解析方式入库
 * @since 1.7.0
 * @version 1.7.0
 * @作者：weiyuqiang
 * @创建时间：2019年4月29日c
 * @修改记录：
 */
@Service
public class SvrInvestTradeFileImpService  extends FileImpService{

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 接口文件名称类型
     */
    private static final String[] FILENAME_LIST = {InterfDefine.投资交易信息接口数据.getCode()};
    
    /**
     * 业务主键
     */
    private static final String BUSSINESSKEY = "TRADE_CODE";
    
    /**
     * 重复性检查字段
     */
    private static final String[] DUPLICATIED_LIST = {"TRADE_CODE"};
	
	/**
	 * 使用sqlloader入库的条数限制
	 */
	private static final int SQLLOADER_MAX_SIZE = 1000;
    
    /**
     * mybatis的sqlsession管理核心
     */
    @Autowired
	private SqlSessionTemplate template;
    
    /**
     * 投资交易接入mapper
     */
    @Autowired
	private ChbInvestTradeInterfMapper chbInvestTradeInterfMapper;
    
    /**
     * 投资交易接入mapper
     */
    @Autowired
    private ChbAssetInfoInterfMapper chbAssetInfoInterfMapper;
    
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
     * 数据批量一次性insert条数
     */
    @Value("${application.reportInsertCount}")
    private int reportInsertCount;


    /**
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;


	/**
     * 废弃
	 */
    @Override
    public String importDB(List<Map<String, String>> investTradeList,InterfFile interfFile, String date) {
        return null;
    }

    /**
     * 功能:文件处理方法
     * 描述：
     *      1.parseFile --将文件转换为Map<String,String>
     *      2.checkData --校验数据
     *      3.saveData --入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月5日
     * @see com.joyintech.tams.common.FileImpService#importDB(java.util.List)
     */
    @Override
    public String importDB(List<ExcelValidDto> listExcel, List<Map<String, String>> investTradeList,InterfFile interfFile, String date) {
        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.投资交易信息接口数据.getCode());
        if (interfConfig == null){
            logger.info("CHB_INTERF_CONFIG表中无配置项！");
            return "fail";
        }
        checkImportData(listExcel, investTradeList, interfFile.getFilename(), ChbCheckTableInfo.投资交易信息表,interfConfig.getCoverFlag(), listExcel == null? "I" : "D");
        if (CollectionUtils.isEmpty(investTradeList)){
            return "success";
        }
        String saveFlag = interfConfig.getContentType();
        String result= "";
        if(investTradeList.size()>0){
            result = saveData(investTradeList, listExcel == null?saveFlag:InterfConstants.SAVEFLAG_ADD, listExcel == null? "I" : "D");
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
     * @see
     */
    @Override
    public String saveData(List<Map<String, String>> listData,String saveFlag,String dataSource) {
        // 解析成功或失败的标识
        List<String> listCloumn = baseMapper.getTableColumnList(InterfDefine.投资交易信息接口数据.getCode());
        // 转dto
        List<ChbInvestTrade> chbInvestTradeList = (List<ChbInvestTrade>)convertMapToEntity(listData, ChbInvestTrade.class, dataSource);
        // 删除已存在的数据
        chbInvestTradeInterfMapper.deleteChbInvestTrade(chbInvestTradeList);
        chbInvestTradeInterfMapper.deleteChbInvestTradeErroData(chbInvestTradeList);
        // 再加工
        // 汇率信息放至最外层，每次计算折算人民币，先查rateMap
        Map<String, BigDecimal> rateMap = new HashMap<>();
        // 根据资产代码和资产分类：资管计划去中债要素表中查询
        List<String> assetTypeList = new ArrayList<String>();
        assetTypeList.add(DictCodeListExt2.资产_负债类别_资产管理产品.getValue());
        assetTypeList.add(DictCodeListExt2.资产_负债类别_私募基金.getValue());
        assetTypeList.add(DictCodeListExt2.资产_负债类别_委外投资_协议方式.getValue());
        List<String> assetPlanLList = chbAssetInfoInterfMapper.selectAssetCodeByAssetType(assetTypeList, null);
        Map<String,String> assetPlanLMap = assetPlanLList.stream().collect(Collectors.toMap(p -> p,p -> p,(key1, key2) -> key2));

        chbInvestTradeList.stream().forEach(chbInvestTrade->{
            if(StringUtil.isEmpty(chbInvestTrade.getDataSource())){
                chbInvestTrade.setDataSource(SysDataUtil.getNameFromDataDict(DictCode.数据来源, dataSource));
            }
            // 主键
            chbInvestTrade.setTradeSeqno(IDGenerator.generate(36));
            // 非人民币币种折算人民币金額
            if (!StringUtil.equals(chbInvestTrade.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue())) {
                chbInvestTrade.setCnyAmt(ChbCcyConvertUtil.changeCnyMoney(chbInvestTrade.getCcy(), chbInvestTrade.getAmt(), chbInvestTrade.getDealdate(), rateMap));
            } else {
                chbInvestTrade.setCnyAmt(chbInvestTrade.getAmt());
            }
            // 面额折算人民币
            if (chbInvestTrade.getFacevalue() != null){
                chbInvestTrade.setFacevalue(ChbCcyConvertUtil.changeCnyMoney(chbInvestTrade.getCcy(), chbInvestTrade.getFacevalue(), chbInvestTrade.getDealdate(), rateMap));
            }

            // TRADE_TYPE资产交易默认'01'，判断是否为资管计划交易,即资产一级分类为资管计划
            if (CollectionUtils.isNotEmpty(assetPlanLList)
                    && assetPlanLMap.get(chbInvestTrade.getAssetCode()) != null){
                chbInvestTrade.setTradeType(DictCodeListExt2.投资端交易类型_资管计划交易.getValue());
            }else{
                chbInvestTrade.setTradeType("01");
            }
            // 是否底层交易流水 01 是
            chbInvestTrade.setIsUnderTrade(DictCodeList.是否_中债_否.getValue());
            // 利率为空
            chbInvestTrade.setRate(null);
            // 部门代码
            chbInvestTrade.setDeptCode(CommonUtil.getDeptCode());
            // 机构代码
            chbInvestTrade.setOrgCode(CommonUtil.getOrgCode());
        });
        if (chbInvestTradeList != null && chbInvestTradeList.size() > 0) {
            if(InterfConstants.SAVEFLAG_ADD.equals(saveFlag)){
                insertDataMerge(chbInvestTradeList,saveFlag);
            }
        }


        return "success";
    }
    /**
     * 功能:数据入库前删除数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月5日 
     * @param investTradeList 投资交易信息
     * @param saveFlag:F－全量，I－增量
     */
    public void deleteDataBatch(List<ChbInvestTrade> investTradeList,String saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            logger.debug("投资交易删除当日数据开始--------------" + new Date());
            // 全量直接清表
            if (com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ALL.equals(saveFlag)) {
                // chbInvestTradeInterfMapper.deleteAllData();
                chbInvestTradeInterfMapper.deleteBySysTime(true);
                // 查询之前接入的资产交易
                List<String> tradeList = chbInvestTradeInterfMapper.selectTradeCodeBySysdate(false);
                Map<String,String> tradeMap = tradeList.stream().collect(Collectors.toMap(p -> p, p -> p,(key1,key2) -> key2));
                List<ChbInvestTrade> deleteTradeList = investTradeList.stream().filter(trade -> StringUtil.isNotEmpty(tradeMap.get(trade.getTradeCode()))).collect(Collectors.toList());
                logger.debug("资产要素删除昨日数据开始--------------");    
                int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"50" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));//每批commit的个数
                int batchLastIndex = batchCount;//每批最后一个的下标
                for(int index = 0; index < deleteTradeList.size();){
                    if(batchLastIndex > deleteTradeList.size()-1){
                        batchLastIndex = deleteTradeList.size();
                        chbInvestTradeInterfMapper.deleteByTradeCode(deleteTradeList.subList(index, batchLastIndex));
                        break;//数据插入完毕,退出循环
                    }else{
                        chbInvestTradeInterfMapper.deleteByTradeCode(deleteTradeList.subList(index, batchLastIndex));
                        index = batchLastIndex;//设置下一批下标
                        batchLastIndex = index + batchCount;                     
                    }
                }
                logger.debug("资产要素删除昨日数据结束--------------");    
            } else if (com.joyintech.tams.report.interf.common.constant.InterfConstants.SAVEFLAG_ADD.equals(saveFlag)) {// 增量
                // chbInvestTradeInterfMapper.deleteBySysTime( true);
                logger.info("增量类型无需删除！");
            } else {
                logger.error("无法识别全增量类型！");
            }
            logger.debug("资产要素删除当日数据结束--------------" + new Date());
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
     * @param investTradeList 投资交易信息
     * @param saveFlag:F－全量，I－增量
     */
    public void insertDataMerge(List<ChbInvestTrade> investTradeList,String saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("投资交易插入开始--------------" + new Date());
            ChbInvestTradeInterfMapper batchMapper = session.getMapper(ChbInvestTradeInterfMapper.class);
            int batchCount = reportInsertLongCount==0?50:reportInsertLongCount;//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < investTradeList.size();){
                if(batchLastIndex > investTradeList.size()-1){
                    batchLastIndex = investTradeList.size();
                    batchMapper.mergeInfoList(investTradeList.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.mergeInfoList(investTradeList.subList(index, batchLastIndex));
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
            logger.debug("投资交易插入结束--------------" + new Date());
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        finally {
            session.close();
        }
        logger.debug("投资交易插入结束--------------" + new Date());
    }

}
