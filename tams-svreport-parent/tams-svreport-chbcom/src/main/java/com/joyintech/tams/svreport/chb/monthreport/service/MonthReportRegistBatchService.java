package com.joyintech.tams.svreport.chb.monthreport.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.joyintech.tams.svreport.chb.subsidiamonthreport.service.SubsidiaMonthReportRegistBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.chb.util.ToolsUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.svreport.chb.monthreport.dao.ChbMonthReportMapper;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthReport;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthReportPrdConstants;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:中债银行月度统计表批处理
 * @since 1.7.0
 * @version 1.7.0
 * @作者：weiyuqiang
 * @创建时间：2019年5月30日
 * @修改记录：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MonthReportRegistBatchService  extends SvrCBBaseService{
	/**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MonthReportProdService monthReportProdService;
    @Autowired
    private MonthReportAssetService monthReportAssetService;

    @Autowired
    private SubsidiaMonthReportRegistBatchService subsidiaMonthReportRegistBatchService;



    @Transactional(rollbackFor = Exception.class)
	public void genReport(Date executeDate,List<String> prodids,boolean ifTrigger){
        // 设置批处理bean
        setBatchBeanName(BatchBeanName.银行月度统计表生成);
        // 批处理开始
        logInfoBatchStart();
	    Calendar calendar = Calendar.getInstance();
        calendar.setTime(executeDate);
        
        if ( calendar.get(Calendar.DATE)!=1 &&!ifTrigger) {  //每月1日生成上月的数据
        	return;
        }
        
        if ( prodids==null ) 
            prodids = new ArrayList<String>();
        // 获取上月的第一天
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstday = calendar.getTime();
        // 获取上月的最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date lastday = calendar.getTime();
        calendar.setTime(lastday);
        
        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        logger.info("生成银行月度报表："+year+"年"+month+"月");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        ChbMonthReport report=new ChbMonthReport();
        report.setStartdate(firstday);
        report.setEnddate(lastday);
        report.setStatusDate(ToolsUtil.getCurrentTime(executeDate));
        report.setProdids(prodids);
        report.setTermid(sdf.format(firstday));
        
        
        /****update by leipeng 20200606  多法人改造.主表由数据实时生成**/
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//        ChbMonthReport chbMonthReport = new ChbMonthReport();
//        chbMonthReport.setTermid(sdf.format(firstday));
//        //报表ID
//        chbMonthReport.setReportuuid(UUID.randomUUID().toString());
//        
//        //报表类型
//        chbMonthReport.setReporttype(ChbMonthReportPrdConstants.REPORT_TYPE_MONTH);
//
//        //起止日期
//        chbMonthReport.setStartdate(firstday);
//        chbMonthReport.setEnddate(lastday);
//        
//        //数据日期：批处理日期
//        chbMonthReport.setStatusDate(report.getStatusDate());  
//        
//        //报表频率：月报
//        chbMonthReport.setReportfrequ(ChbMonthReportPrdConstants.REPORT_FREQU_M);
//        
//        // 发行机构代码取值
//        chbMonthReport.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
//        chbMonthReport.setIssuePartyName(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_NAME));
//
//        //用户信息
//        chbMonthReport.setOrgCode(CommonUtil.getOrgCode());
//        chbMonthReport.setCreateDept(CommonUtil.getDeptCode());
//        chbMonthReport.setCreateUser(CommonUtil.getUserName());
//        chbMonthReport.setUpdateUser(CommonUtil.getUserName());
//        
//        //数据状态
//        chbMonthReport.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
//        chbMonthReport.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
//        //报送时点
//        //当前批次报送时点集合
//        Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
//        List<Date> dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_银行月度统计表.getValue(),
//        		chbMonthReport.getStatus(),dateMap, chbMonthReport.getEnddate());
//		if (!StringUtil.isEmptyList(dateList)) {
//			chbMonthReport.setChbSubmitDeadline(dateList.get(0));
//			chbMonthReport.setSubmitDeadline(dateList.get(1));
//		}
//		//插入月报主表数据
//		List<ChbMonthReport> ybs=chbMonthReportMapper.selectSendDataByTermId(chbMonthReport.getTermid());
//		 calendar.setTime(executeDate);
//		  ChbMonthReport chbDelReport = new ChbMonthReport();
//		  chbDelReport.setTermid(chbMonthReport.getTermid());
//		//查询未报送
//		List<ChbMonthReport> wbs=chbMonthReportMapper.selectUnSendDataByTermId(chbMonthReport.getTermid());
//		if ( calendar.get(Calendar.DATE)==1 &&ybs!=null&&!ybs.isEmpty()&&(wbs==null||wbs.isEmpty())&&!ifTrigger) {
//			return;
//		}
//		if(wbs!=null&&!wbs.isEmpty()){
//			report.setWbsreportuuid(wbs.get(0).getReportuuid());
//		}
//		if(ybs!=null&&!ybs.isEmpty()){//有已报送数据 不作处理
////			// 如果有已登记的资产要素则抛异常
////			throw new BatchException("存在已报送的期次:"+chbMonthReport.getTermid()+"\n");
//			//增加留痕处理  已报送后可生成修改未报送 
//			if(wbs!=null&&!wbs.isEmpty()){
//				chbDelReport.setReportuuid(wbs.get(0).getReportuuid());
//				chbMonthReportMapper.deleteByModel(chbDelReport);
//			}
//			chbMonthReport.setStatus("02");//修改
////			return ;
////			for(ChbMonthReport single:ybs){
////				ChbMonthReport upd=new ChbMonthReport();
////				upd.setReportuuid(single.getReportuuid());
////				upd.setStatus(DictCodeList.报送数据状态_删除.getValue());
////				chbMonthReportMapper.updateByPrimaryKeySelective(upd);
////			}
//		}else{
//			chbMonthReportMapper.deleteByModel(chbDelReport);
//		}
//		chbMonthReportMapper.insert(chbMonthReport);
//		report.setReportuuid(chbMonthReport.getReportuuid());
        
        /****update by leipeng 20200606  多法人改造.主表由数据实时生成**/

        
    	//生成产品端月报明细
    	monthReportProdService.genReport(report,ifTrigger);
        
        //生成资产端月报明细
    	monthReportAssetService.genReport(report,ifTrigger);


        /** 中债理财子公司月度统计表-报表生成(从宁银批处理中拆出来放到该逻辑中) modify by yanya on 20210511 start**/
        subsidiaMonthReportRegistBatchService.genReport(executeDate, prodids, ifTrigger);
        /** 中债理财子公司月度统计表-报表生成(从宁银批处理中拆出来放到该逻辑中) modify by yanya on 20210511 end**/

        // 批处理结束
        logInfoBatchEnd();
	}

    /**
     * 功能:数据变更接口统一调度入口
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月9日
     * @param executeDate
     * @param prodList
     * @param assetList
     * @throws BusinessException 
     * @see com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService#execute(java.util.Date, java.util.List, java.util.List)
     */
    @Override
    public void execute(Date executeDate, List<String> prodList, List<String> assetList,Map<String,List<String>> param) throws BusinessException {
        try {
            genReport(executeDate,null,true);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            throw new BusinessException(e);
        }
    }

}
