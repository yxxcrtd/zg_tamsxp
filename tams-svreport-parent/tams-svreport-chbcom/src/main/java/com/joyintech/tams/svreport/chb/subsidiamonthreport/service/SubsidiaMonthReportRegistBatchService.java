package com.joyintech.tams.svreport.chb.subsidiamonthreport.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthReportPrdConstants;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.dao.ChbMonthReportSubMapper;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSub;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dao.SubsidiaMonthReportRegistBatchMapper;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDto;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:中债理财子公司月度统计表-报表生成批处理
 * 
 * @作者：dubaojun
 * @创建时间：2020年6月8日
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SubsidiaMonthReportRegistBatchService extends SvrCBBaseService {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ChbMonthReportSubMapper chbMonthReportSubMapper;
    @Autowired
    private SubsidiaMonthReportRegistBatchMapper subsidiaMonthReportRegistBatchMapper;
    @Autowired
    private SubsidiaMonthReportProdBatchService monthReportProdService;
    @Autowired
    private SubsidiaMonthReportAssetBatchService monthReportAssetService;

    /**
     * 生成报表方法
     * 
     * @param executeDate 跑批日
     * @param prodids 产品列表
     * @param ifTrigger 是否触发
     */
    @Transactional(rollbackFor = Exception.class)
    public void genReport(Date executeDate, List<String> prodids, boolean ifTrigger) {
        // 设置批处理bean
        setBatchBeanName(BatchBeanName.理财子公司月度统计表生成);
        // 批处理开始
        logInfoBatchStart();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(executeDate);

        if (calendar.get(Calendar.DATE) != 1 && !ifTrigger) { // 每月1日生成上月的数据
            return;
        }

        if (prodids == null)
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

        int year = calendar.get(Calendar.YEAR);// 获取年份
        int month = calendar.get(Calendar.MONTH) + 1;// 获取月份
        logger.info("生成理财子公司月度报表：" + year + "年" + month + "月");

        ChbMonthReportSubDto report = new ChbMonthReportSubDto();
        report.setStartdate(firstday);
        report.setEnddate(lastday);
        report.setStatusDate(ToolsUtil.getCurrentTime(executeDate));
        report.setProdids(prodids);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        ChbMonthReportSub chbMonthReportSub = new ChbMonthReportSub();
        chbMonthReportSub.setTermid(sdf.format(firstday));
        // 报表ID
        chbMonthReportSub.setReportuuid(UUID.randomUUID().toString());

        // 报表类型
        chbMonthReportSub.setReporttype(ChbMonthReportPrdConstants.REPORT_TYPE_MONTH);

        // 起止日期
        chbMonthReportSub.setStartdate(firstday);
        chbMonthReportSub.setEnddate(lastday);

        // 数据日期：批处理日期
        chbMonthReportSub.setStatusDate(report.getStatusDate());

        // 报表频率：月报
        chbMonthReportSub.setReportfrequ(ChbMonthReportPrdConstants.REPORT_FREQU_M);

        // 发行机构代码取值
        chbMonthReportSub.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
        chbMonthReportSub.setIssuePartyName(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_NAME));

        // 用户信息
        chbMonthReportSub.setOrgCode(CommonUtil.getOrgCode());
        chbMonthReportSub.setCreateDept(CommonUtil.getDeptCode());
        chbMonthReportSub.setCreateUser(CommonUtil.getUserName());
        chbMonthReportSub.setUpdateUser(CommonUtil.getUserName());

        // 数据状态
        chbMonthReportSub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
        chbMonthReportSub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
        // 报送时点
        // 当前批次报送时点集合
        Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
        List<Date> dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_理财子公司月度统计表.getValue(),
                chbMonthReportSub.getStatus(), dateMap, chbMonthReportSub.getEnddate());
        if (!StringUtil.isEmptyList(dateList)) {
            chbMonthReportSub.setChbSubmitDeadline(dateList.get(0));
            chbMonthReportSub.setSubmitDeadline(dateList.get(1));
        }
        // 插入月报主表数据
        List<ChbMonthReportSub> ybs = subsidiaMonthReportRegistBatchMapper.selectSendDataByTermId(chbMonthReportSub.getTermid());
        calendar.setTime(executeDate);
        ChbMonthReportSub chbDelReport = new ChbMonthReportSub();
        chbDelReport.setTermid(chbMonthReportSub.getTermid());
        // 查询未报送
        List<ChbMonthReportSub> wbs = subsidiaMonthReportRegistBatchMapper.selectUnSendDataByTermId(chbMonthReportSub.getTermid());
        if (calendar.get(Calendar.DATE) == 1 && ybs != null && !ybs.isEmpty() && (wbs == null || wbs.isEmpty())
                && !ifTrigger) {
            return;
        }
        if (wbs != null && !wbs.isEmpty()) {
            report.setWbsreportuuid(wbs.get(0).getReportuuid());
        }
        if (ybs != null && !ybs.isEmpty()) {// 有已报送数据 不作处理
            // 增加留痕处理 已报送后可生成修改未报送
            if (wbs != null && !wbs.isEmpty()) {
                chbDelReport.setReportuuid(wbs.get(0).getReportuuid());
                subsidiaMonthReportRegistBatchMapper.deleteByModel(chbDelReport);
            }
            chbMonthReportSub.setStatus("02");// 修改
        } else {
            subsidiaMonthReportRegistBatchMapper.deleteByModel(chbDelReport);
        }
        chbMonthReportSubMapper.insert(chbMonthReportSub);
        report.setReportuuid(chbMonthReportSub.getReportuuid());
        // 生成产品端月报明细
        monthReportProdService.genMonthPrd(report);

        // 生成资产端月报明细
        monthReportAssetService.genMonthAsset(report);
        // 批处理结束
        logInfoBatchEnd();
    }

    /**
     * 功能:数据变更接口统一调度入口
     * 
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月9日
     * @param executeDate
     * @param prodList
     * @param assetList
     * @throws BusinessException
     * @see SvrCBBaseService#execute(Date,
     *      List, List)
     */
    @Override
    public void execute(Date executeDate, List<String> prodList, List<String> assetList,
            Map<String, List<String>> param) throws BusinessException {
        try {
            genReport(executeDate, null, true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
    }

}
