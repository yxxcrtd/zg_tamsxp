package com.joyintech.component.svrreport.chb.dto;


import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.component.svrreport.chb.contant.annotate.RateFormat;

/**
 * 产品发行登记
 * @description 
 * @author 王波
 * @version 1.0 2020年9月22日
 * @see ChbPrdIssueSubDto
 * @since 1.0
 */
public class ChbPrdIssueSubDto {
    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 产品登记编码
     */
    private String prodRegistCode;

    /**
     * 理财产品代码
     */
    private String prodId;

    /**
     * 募集起始日期
     */
    private Date raiseVdate;

    /**
     * 募集结束日期
     */
    private Date raiseEnddate;

    /**
     * 产品起始日期
     */
    private Date prdVdate;

    /**
     * 产品终止日期
     */
    private Date prdMdate;

    /**
     * 是否为结构化（分级）产品
     */
    private String isConstracte;


    /***1217升级**/


    /**
     * 分级比例
     */
    @RateFormat
    private  BigDecimal   gradProportion;

    public BigDecimal getGradProportion() {
        return gradProportion;
    }

    public void setGradProportion(BigDecimal gradProportion) {
        this.gradProportion = gradProportion;
    }

    /***1217升级**/


    /**
     * 层级类别      11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五
     */
    private String gradeType;

    /**
     * 收益率分档情况说明
     */
    private String yieldComment;

    /**
     * 开放模式      01 有规律开放/02 无规律开放
     */
    private String openMode;

    /**
     * 规律开放周期
     */
    private String openCircle;

    /**
     * 其他规律开放周期 (天)
     */
    private BigDecimal openCircleDays;

    /**
     * 无规律开放说明
     */
    private String ruleComment;

    /**
     * 首次开放周期起始日
     */
    private Date firstOpenDay;

    /**
     * 节假日是否开放            01 是/02 否
     */
    private String holidayOnFlag;

    /**
     * 平均开放次数（年化）
     */
    private BigDecimal avgOpenCount;

    /**
     * 开放期业务
     */
    private String openBuss;

    /**
     * 开放期业务说明
     */
    private String openBussRemark;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败
     */
    private String submitFeedbackStatus;

    /**
     * 报送状态      未报送/接口报送/手工处理
     */
    private String sendStatus;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 报送截止日
     */
    private Date submitDeadline;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 预计客户最低年收益率 %
     */
    private BigDecimal expMinYield;

    /**
     * 预计客户最高年收益率 %
     */
    private BigDecimal expMaxYield;

    /**
     * 平均预计客户年收益率%
     */
    private BigDecimal expAvgYield;

    /**
     * 管理方式
     */
    private String manageType;

    /**
     * 业绩比较基准下限
     */
    @RateFormat
    private BigDecimal yieldLow;

    /**
     * 业绩比较基准上限
     */
    @RateFormat
    private BigDecimal yieldHigh;

    /**
     * 业绩比较基准说明
     */
    private String yieldRemark;

    /**
     * 资金托管账号
     */
    private String busiAcctNo;

    /**
     * 资金托管账户
     */
    private String acctName;

    /**
     * 取得 登记序列
     * @return 登记序列
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 设置 登记序列
     * @param registSeqno 登记序列
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 取得 产品登记编码
     * @return 产品登记编码
     */
    public String getProdRegistCode() {
        return prodRegistCode;
    }

    /**
     * 设置 产品登记编码
     * @param prodRegistCode 产品登记编码
     */
    public void setProdRegistCode(String prodRegistCode) {
        this.prodRegistCode = prodRegistCode == null ? null : prodRegistCode.trim();
    }

    /**
     * 取得 理财产品代码
     * @return 理财产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 设置 理财产品代码
     * @param prodId 理财产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 取得 募集起始日期
     * @return 募集起始日期
     */
    public Date getRaiseVdate() {
        return raiseVdate;
    }

    /**
     * 设置 募集起始日期
     * @param raiseVdate 募集起始日期
     */
    public void setRaiseVdate(Date raiseVdate) {
        this.raiseVdate = raiseVdate;
    }

    /**
     * 取得 募集结束日期
     * @return 募集结束日期
     */
    public Date getRaiseEnddate() {
        return raiseEnddate;
    }

    /**
     * 设置 募集结束日期
     * @param raiseEnddate 募集结束日期
     */
    public void setRaiseEnddate(Date raiseEnddate) {
        this.raiseEnddate = raiseEnddate;
    }

    /**
     * 取得 产品起始日期
     * @return 产品起始日期
     */
    public Date getPrdVdate() {
        return prdVdate;
    }

    /**
     * 设置 产品起始日期
     * @param prdVdate 产品起始日期
     */
    public void setPrdVdate(Date prdVdate) {
        this.prdVdate = prdVdate;
    }

    /**
     * 取得 产品终止日期
     * @return 产品终止日期
     */
    public Date getPrdMdate() {
        return prdMdate;
    }

    /**
     * 设置 产品终止日期
     * @param prdMdate 产品终止日期
     */
    public void setPrdMdate(Date prdMdate) {
        this.prdMdate = prdMdate;
    }

    /**
     * 取得 是否为结构化（分级）产品
     * @return 是否为结构化（分级）产品
     */
    public String getIsConstracte() {
        return isConstracte;
    }

    /**
     * 设置 是否为结构化（分级）产品
     * @param isConstracte 是否为结构化（分级）产品
     */
    public void setIsConstracte(String isConstracte) {
        this.isConstracte = isConstracte == null ? null : isConstracte.trim();
    }

    /**
     * 取得 层级类别      11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五
     * @return 层级类别      11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五
     */
    public String getGradeType() {
        return gradeType;
    }

    /**
     * 设置 层级类别      11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五
     * @param gradeType 层级类别      11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五
     */
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType == null ? null : gradeType.trim();
    }

    /**
     * 取得 收益率分档情况说明
     * @return 收益率分档情况说明
     */
    public String getYieldComment() {
        return yieldComment;
    }

    /**
     * 设置 收益率分档情况说明
     * @param yieldComment 收益率分档情况说明
     */
    public void setYieldComment(String yieldComment) {
        this.yieldComment = yieldComment == null ? null : yieldComment.trim();
    }

    /**
     * 取得 开放模式      01 有规律开放/02 无规律开放
     * @return 开放模式      01 有规律开放/02 无规律开放
     */
    public String getOpenMode() {
        return openMode;
    }

    /**
     * 设置 开放模式      01 有规律开放/02 无规律开放
     * @param openMode 开放模式      01 有规律开放/02 无规律开放
     */
    public void setOpenMode(String openMode) {
        this.openMode = openMode == null ? null : openMode.trim();
    }

    /**
     * 取得 规律开放周期
     * @return 规律开放周期
     */
    public String getOpenCircle() {
        return openCircle;
    }

    /**
     * 设置 规律开放周期
     * @param openCircle 规律开放周期
     */
    public void setOpenCircle(String openCircle) {
        this.openCircle = openCircle == null ? null : openCircle.trim();
    }

    /**
     * 取得 其他规律开放周期 (天)
     * @return 其他规律开放周期 (天)
     */
    public BigDecimal getOpenCircleDays() {
        return openCircleDays;
    }

    /**
     * 设置 其他规律开放周期 (天)
     * @param openCircleDays 其他规律开放周期 (天)
     */
    public void setOpenCircleDays(BigDecimal openCircleDays) {
        this.openCircleDays = openCircleDays;
    }

    /**
     * 取得 无规律开放说明
     * @return 无规律开放说明
     */
    public String getRuleComment() {
        return ruleComment;
    }

    /**
     * 设置 无规律开放说明
     * @param ruleComment 无规律开放说明
     */
    public void setRuleComment(String ruleComment) {
        this.ruleComment = ruleComment == null ? null : ruleComment.trim();
    }

    /**
     * 取得 首次开放周期起始日
     * @return 首次开放周期起始日
     */
    public Date getFirstOpenDay() {
        return firstOpenDay;
    }

    /**
     * 设置 首次开放周期起始日
     * @param firstOpenDay 首次开放周期起始日
     */
    public void setFirstOpenDay(Date firstOpenDay) {
        this.firstOpenDay = firstOpenDay;
    }

    /**
     * 取得 节假日是否开放            01 是/02 否
     * @return 节假日是否开放            01 是/02 否
     */
    public String getHolidayOnFlag() {
        return holidayOnFlag;
    }

    /**
     * 设置 节假日是否开放            01 是/02 否
     * @param holidayOnFlag 节假日是否开放            01 是/02 否
     */
    public void setHolidayOnFlag(String holidayOnFlag) {
        this.holidayOnFlag = holidayOnFlag == null ? null : holidayOnFlag.trim();
    }

    /**
     * 取得 平均开放次数（年化）
     * @return 平均开放次数（年化）
     */
    public BigDecimal getAvgOpenCount() {
        return avgOpenCount;
    }

    /**
     * 设置 平均开放次数（年化）
     * @param avgOpenCount 平均开放次数（年化）
     */
    public void setAvgOpenCount(BigDecimal avgOpenCount) {
        this.avgOpenCount = avgOpenCount;
    }

    /**
     * 取得 开放期业务
     * @return 开放期业务
     */
    public String getOpenBuss() {
        return openBuss;
    }

    /**
     * 设置 开放期业务
     * @param openBuss 开放期业务
     */
    public void setOpenBuss(String openBuss) {
        this.openBuss = openBuss == null ? null : openBuss.trim();
    }

    /**
     * 取得 开放期业务说明
     * @return 开放期业务说明
     */
    public String getOpenBussRemark() {
        return openBussRemark;
    }

    /**
     * 设置 开放期业务说明
     * @param openBussRemark 开放期业务说明
     */
    public void setOpenBussRemark(String openBussRemark) {
        this.openBussRemark = openBussRemark == null ? null : openBussRemark.trim();
    }

    /**
     * 取得 工作流代码
     * @return 工作流代码
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 设置 工作流代码
     * @param workflowId 工作流代码
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 取得 反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败
     * @return 反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 设置 反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败
     * @param submitFeedbackStatus 反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 取得 报送状态      未报送/接口报送/手工处理
     * @return 报送状态      未报送/接口报送/手工处理
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置 报送状态      未报送/接口报送/手工处理
     * @param sendStatus 报送状态      未报送/接口报送/手工处理
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 取得 数据日期
     * @return 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 设置 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 取得 机构代码
     * @return 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 取得 报送日期
     * @return 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 设置 报送日期
     * @param submitTime 报送日期
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 取得 中债报送截止日
     * @return 中债报送截止日
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 设置 中债报送截止日
     * @param chbSubmitDeadline 中债报送截止日
     */
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    /**
     * 取得 报送截止日
     * @return 报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 设置 报送截止日
     * @param submitDeadline 报送截止日
     */
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    /**
     * 取得 发行机构代码
     * @return 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 设置 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 取得 报送流水号
     * @return 报送流水号
     */
    public String getSumitSeq() {
        return sumitSeq;
    }

    /**
     * 设置 报送流水号
     * @param sumitSeq 报送流水号
     */
    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq == null ? null : sumitSeq.trim();
    }

    /**
     * 取得 预计客户最低年收益率 %
     * @return 预计客户最低年收益率 %
     */
    public BigDecimal getExpMinYield() {
        return expMinYield;
    }

    /**
     * 设置 预计客户最低年收益率 %
     * @param expMinYield 预计客户最低年收益率 %
     */
    public void setExpMinYield(BigDecimal expMinYield) {
        this.expMinYield = expMinYield;
    }

    /**
     * 取得 预计客户最高年收益率 %
     * @return 预计客户最高年收益率 %
     */
    public BigDecimal getExpMaxYield() {
        return expMaxYield;
    }

    /**
     * 设置 预计客户最高年收益率 %
     * @param expMaxYield 预计客户最高年收益率 %
     */
    public void setExpMaxYield(BigDecimal expMaxYield) {
        this.expMaxYield = expMaxYield;
    }

    /**
     * 取得 平均预计客户年收益率%
     * @return 平均预计客户年收益率%
     */
    public BigDecimal getExpAvgYield() {
        return expAvgYield;
    }

    /**
     * 设置 平均预计客户年收益率%
     * @param expAvgYield 平均预计客户年收益率%
     */
    public void setExpAvgYield(BigDecimal expAvgYield) {
        this.expAvgYield = expAvgYield;
    }

    /**
     * 取得 管理方式
     * @return 管理方式
     */
    public String getManageType() {
        return manageType;
    }

    /**
     * 设置 管理方式
     * @param manageType 管理方式
     */
    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    /**
     * 取得 业绩比较基准下限
     * @return 业绩比较基准下限
     */
    public BigDecimal getYieldLow() {
        return yieldLow;
    }

    /**
     * 设置 业绩比较基准下限
     * @param yieldLow 业绩比较基准下限
     */
    public void setYieldLow(BigDecimal yieldLow) {
        this.yieldLow = yieldLow;
    }

    /**
     * 取得 业绩比较基准上限
     * @return 业绩比较基准上限
     */
    public BigDecimal getYieldHigh() {
        return yieldHigh;
    }

    /**
     * 设置 业绩比较基准上限
     * @param yieldHigh 业绩比较基准上限
     */
    public void setYieldHigh(BigDecimal yieldHigh) {
        this.yieldHigh = yieldHigh;
    }

    /**
     * 取得 业绩比较基准说明
     * @return 业绩比较基准说明
     */
    public String getYieldRemark() {
        return yieldRemark;
    }

    /**
     * 设置 业绩比较基准说明
     * @param yieldRemark 业绩比较基准说明
     */
    public void setYieldRemark(String yieldRemark) {
        this.yieldRemark = yieldRemark == null ? null : yieldRemark.trim();
    }

    /**
     * 取得 资金托管账号
     * @return 资金托管账号
     */
    public String getBusiAcctNo() {
        return busiAcctNo;
    }

    /**
     * 设置 资金托管账号
     * @param busiAcctNo 资金托管账号
     */
    public void setBusiAcctNo(String busiAcctNo) {
        this.busiAcctNo = busiAcctNo == null ? null : busiAcctNo.trim();
    }

    /**
     * 取得 资金托管账户
     * @return 资金托管账户
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * 设置 资金托管账户
     * @param acctName 资金托管账户
     */
    public void setAcctName(String acctName) {
        this.acctName = acctName == null ? null : acctName.trim();
    }
}
