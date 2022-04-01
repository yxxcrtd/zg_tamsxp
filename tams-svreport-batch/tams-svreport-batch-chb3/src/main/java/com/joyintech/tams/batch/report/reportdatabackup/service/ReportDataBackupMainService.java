/*
 * 文件名：GetInvestorsHoldInfoRegistAutoSendCBMainService.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：wangmin
 * 修改时间：2018年9月25日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */
 
package com.joyintech.tams.batch.report.reportdatabackup.service;




import java.util.Date;
import java.util.List;

import com.joyintech.framework.exception.BusinessException;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.batch.report.reportdatabackup.dao.ReportDataBackupMapper;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.batch.report.autosendcb.common.service.AutoSendCBBaseService;
import com.joyintech.tams.batch.report.reportdatabackup.dao.ReportDataBackupMapper;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据归档批处理service
 * @author wangmin
 * @version 1.0 2018年10月9日
 * @see ReportDataBackupMainService
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportDataBackupMainService  extends AutoSendCBBaseService{

  
    /**
     * 投资者持有信息登记数据自动生成批处理 业务mapper
     */
    @Autowired
    private ReportDataBackupMapper mapper;

	/*** 数据库配置*/
	@Value("${base.datasource.username}")
	public String tableOwner;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     *需要归档的表
     */
	/***三期CHB_SUBMIT_STATUS去除改造  20220310**/
//    private static final String[] DATA_BACKUP_TABLENAME = new String[] {"CHB_INFO_REPORT_", "CHB_SUBMIT_STATUS_","CHB_INVESTHOLD_INFO_","CHB_INVESTHOLD_INFO_SUB_","CHB_INVESTHOLD_INFO_CHECK_"};
	private static final String[] ORIGINAL_DATA_BACKUP_TABLE_NAME = new String[] {"CHB_INFO_REPORT", "CHB_INVESTHOLD_INFO","CHB_INVESTHOLD_INFO_SUB","CHB_INVESTHOLD_INFO_CHECK"};
	/**
	 * 归档的表
	 */
	private static final String[] DATA_BACKUP_TABLE_NAME = new String[] {"CHB_INFO_REPORT_", "CHB_INVESTHOLD_INFO_","CHB_INVESTHOLD_INFO_SUB_","CHB_HOLD_INFO_CHECK_"};
	/***三期CHB_SUBMIT_STATUS去除改造  20220310**/
    /**
     * 自动检查报送
     * @param executeDateStr
     *
     * @see
     * @since 1.0
     */
    public void generateRegistData(String executeDateStr) {
    	
    	setBatchBeanName(BatchBeanName.投资者报送数据归档);
    	
    	try{
            logInfoBatchStart();
            /**
        	 * 1.从缓存中获取归档天数（即原表中保留多少天数据）
        	 * 2.获取当前月份，查询当前月份是否有备份表
        	 *  2.1 若有备份表，则使用insert into select
        	 *  2.2若无备份表，则根据当前日期create table_date as select
        	 * 3.完成之后，drop归档的分区并进行表、索引的分析
        	 * 
        	 */
            //缓存中获取原表中保留多少天数据
    		String dataBackupDays = CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, ReportGlobalConstant.DATA_BACKUP_DAYS);
        	String currentDate = DateUtil.getCurrentDate();
        	//归档日期
        	String dataBackupDate = DateUtil.addDay(currentDate, -1*Integer.parseInt(dataBackupDays));
        	if(mapper.isExistBackupData(dataBackupDate)>0){
        		logger.info("归档数据中存在未报送数据，等待下一日归档！");
        	}
        	else{
        		int month = DateUtil.getMonth(currentDate);
            	int year = DateUtil.getYear(currentDate);
            	logger.info("查询需要归档的数据日期开始！");
				Map<String,Date> map = mapper.getDateByBackupDataEx(dataBackupDate);
            	logger.info("查询需要归档的数据日期结束！");
				if (map!=null && !map.isEmpty()) {
                	//chb_submit_status
					/***三期CHB_SUBMIT_STATUS去除改造  20220310**/
//                	doDataBackupDB2(year, month, 1 ,map);
					/***三期CHB_SUBMIT_STATUS去除改造  20220310**/
					//chb_investhold_info
                	doDataBackupDB2(year, month, 2 ,map);
                	//chb_investhold_info_sub
                	doDataBackupDB2(year, month, 3 ,map);
					//chb_investhold_info_check
					doDataBackupDB2(year, month, 4 ,map);
            	}
            	else {
            		logger.info("当天无归档数据！");
            	}
        	}
        	logInfoBatchEnd();
    	}
    	 catch (Exception e) {
             logInfo(e.getMessage());
			 logger.error("投资者持有信息登记数据自动生成批处理Error",e);
             insertResultLogException(DateUtil.now());
             throw new BusinessException(e);
         }
    }

	/**
	 *
	 * @param year
	 * @param month
	 * @param flag
	 * @param map
	 */
	private void doDataBackupDB2(int year, int month, int flag, Map<String,Date> map){
//		String tableName_ori = StringUtil.substring(DATA_BACKUP_TABLENAME[flag],0,DATA_BACKUP_TABLENAME[flag].length()-1);
//		String tableName = DATA_BACKUP_TABLENAME[flag]+year+month;
		String tableName_ori = ORIGINAL_DATA_BACKUP_TABLE_NAME[flag];
		String tableName = DATA_BACKUP_TABLE_NAME[flag]+year+month;
    	String reportDataBackup = mapper.queryTableIsExist(tableName);
    	//备份开始日
		String startDate = DateUtil.format(map.get("MINDATE"),"yyyy-MM-dd");
		//备份结束日
		String endDate = DateUtil.format(map.get("MAXDATE"),"yyyy-MM-dd");
		logger.info("关闭归档日志,开启并发,备份开始！");
		mapper.nologgingParrel(tableName_ori);
    	if(flag == 1){
			/***三期CHB_SUBMIT_STATUS去除改造  20220310**/

//    		if(StringUtil.isEmpty(reportDataBackup)){
//    			logger.info("创建"+ tableName +"表开始！");
//        		mapper.createChbSubmitStatus(tableName);
//				mapper.nologgingParrel(tableName);
//        		logger.info("创建"+ tableName +"表结束！");
//        	}
//			logger.info("插入chb_submit_status表" + startDate + "~" + endDate + "日数据归档至"+tableName+"表开始！");
////			mapper.insertChbSubmitStatusData(tableName,startDate,endDate);
//			logger.info("插入chb_submit_status表"+  startDate + "~" + endDate + "日数据归档至"+tableName+"表结束！");
//			//关闭并发，否则dml语句不可执行
//			mapper.loggingNoparallel(tableName_ori);
//			logger.info("删除chb_submit_status表" + startDate + "~" + endDate + "日数据开始！");
//			//状态表中有身份数据，不能删除分区，还是按照原逻辑delete
////			mapper.deleteChbSubmitStatusData(startDate,endDate);
//			logger.info("删除chb_submit_status表" + startDate + "~" + endDate + "日数据结束！");
			/***三期CHB_SUBMIT_STATUS去除改造  20220310**/
		}
    	else if(flag == 2){
    		if(StringUtil.isEmpty(reportDataBackup)){
        		logger.info("创建"+ tableName +"表开始！");
        		mapper.createChbInvestHoldInfo(tableName);
				mapper.nologgingParrel(tableName);
        		logger.info("创建"+ tableName +"表结束！");
        	}
			logger.info("插入chb_investhold_info表" + startDate + "~" + endDate + "日数据归档至"+tableName+"表开始！");
			mapper.insertChbInvestHoldInfoData(tableName,startDate,endDate);
			logger.info("插入chb_investhold_info表" + startDate + "~" + endDate + "日数据归档至"+tableName+"表结束！");
			//下述直接删除分区
//			logger.info("删除chb_investhold_info表" + startDate + "~" + endDate + "日数据开始！");
//			mapper.deleteChbInvestHoldInfoData(startDate,endDate);、
//			logger.info("删除chb_investhold_info表" + startDate + "~" + endDate + "日数据结束！");
    	}
    	else if(flag == 3){
    		if(StringUtil.isEmpty(reportDataBackup)){
        		logger.info("创建"+ tableName +"表开始！");
        		mapper.createChbInvestHoldInfoSub(tableName);
				mapper.nologgingParrel(tableName);
        		logger.info("创建"+ tableName +"表结束！");
        	}
			logger.info("插入chb_investhold_info_sub表" + startDate + "~" + endDate + "日数据归档至"+tableName+"表开始！");
			mapper.insertChbInvestHoldInfoSubData(tableName,startDate,endDate);
			logger.info("插入chb_investhold_info_sub表" + startDate + "~" + endDate + "日数据归档至"+tableName+"表结束！");
			//下述直接删除分区
//			logger.info("删除chb_investhold_info_sub表" + startDate + "~" + endDate + "日数据开始！");
//			mapper.deleteChbInvestHoldInfoSubData(startDate,endDate);
//			logger.info("删除chb_investhold_info_sub表" + startDate + "~" + endDate + "日数据结束！");
    	}
		else if(flag == 4){
			if(StringUtil.isEmpty(reportDataBackup)){
				logger.info("创建"+ tableName +"表开始！");
				mapper.createChbInvestHoldInfoCheck(tableName);
				mapper.nologgingParrel(tableName);
				logger.info("创建"+ tableName +"表结束！");
			}
			logger.info("插入chb_investhold_info_check表" + startDate + "~" + endDate + "日数据归档至"+tableName+"表开始！");
			mapper.insertChbInvestHoldInfoCheckData(tableName,startDate,endDate);
			logger.info("插入chb_investhold_info_check表" + startDate + "~" + endDate + "日数据归档至"+tableName+"表结束！");
			//下述直接删除分区
//			logger.info("删除chb_investhold_info_check表" + startDate + "~" + endDate + "日数据开始！");
//			mapper.deleteChbInvestHoldInfoCheckData(startDate,endDate);
//			logger.info("删除chb_investhold_info_check表" + startDate + "~" + endDate + "日数据结束！");
		}
		if(flag != 1){
			//关闭并发，否则dml语句不可执行
			mapper.loggingNoparallel(tableName_ori);
			logger.info("删除" + tableName_ori + "表分区开始开始！");
			//直接删除分区
			dropPartition(tableName_ori, startDate, endDate);
			logger.info("删除" + tableName_ori + "表分区开始结束！");
		}
		mapper.loggingNoparallel(tableName);
		logger.info("开启归档日志,关闭并发结束,备份结束!");

		//表分析
		logger.info(tableName_ori + " 表索引分析开始.");
		mapper.gathertableStatistics(tableOwner.toUpperCase(),tableName_ori);

		//索引分析
		List<String> indexNms = mapper.queryIndexName(tableName_ori);
		indexNms.forEach(x ->{
//			mapper.gatherindexStatistics(tableOwner.toUpperCase(), x);
			mapper.rebuildIndex(x);
		});
		logger.info(tableName_ori + " 表索引分析结束.");
    }

	/**
	 * 查询备份数据在哪个分区块并删除分区块
	 * @param tableName_ori
	 * @param startDate
	 * @param endDate
	 */
	private void dropPartition(String tableName_ori, String startDate, String endDate) {
		//查询数据表的分区
		List<String> partitions = mapper.queryPartitions(tableName_ori);
		partitions.forEach(x -> {
			//判断该分区是否有备份的数据，如果有则删除分区
			if(mapper.isHaveData(tableName_ori,x, startDate, endDate) > 0){
                 mapper.dropPartition(tableName_ori,x);
			}
		});
	}
}
