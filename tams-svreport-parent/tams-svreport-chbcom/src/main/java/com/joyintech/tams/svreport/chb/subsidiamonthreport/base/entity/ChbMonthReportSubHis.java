package com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.util.Date;

public class ChbMonthReportSubHis extends BaseEntity {
    /**
     * 主键
     */
    private String reportuuid;

    /**
     * 期数
     */
    private String termid;

    /**
     * 报表类型：MONTH-月报
     */
    private String reporttype;

    /**
     * 开始日期
     */
    private Date startdate;

    /**
     * 结束日期
     */
    private Date enddate;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 发行机构名称
     */
    private String issuePartyName;

    /**
     * 报表频率
     */
    private String reportfrequ;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 返回信息
     */
    private String submitFeedbackStatus;

    /**
     * 报送状态
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
     * 部门代码
     */
    private String deptCode;

    /**
     * 取得 主键
     * @return 主键
     */
    public String getReportuuid() {
        return reportuuid;
    }

    /**
     * 设置 主键
     * @param reportuuid 主键
     */
    public void setReportuuid(String reportuuid) {
        this.reportuuid = reportuuid == null ? null : reportuuid.trim();
    }

    /**
     * 取得 期数
     * @return 期数
     */
    public String getTermid() {
        return termid;
    }

    /**
     * 设置 期数
     * @param termid 期数
     */
    public void setTermid(String termid) {
        this.termid = termid == null ? null : termid.trim();
    }

    /**
     * 取得 报表类型：MONTH-月报
     * @return 报表类型：MONTH-月报
     */
    public String getReporttype() {
        return reporttype;
    }

    /**
     * 设置 报表类型：MONTH-月报
     * @param reporttype 报表类型：MONTH-月报
     */
    public void setReporttype(String reporttype) {
        this.reporttype = reporttype == null ? null : reporttype.trim();
    }

    /**
     * 取得 开始日期
     * @return 开始日期
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * 设置 开始日期
     * @param startdate 开始日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * 取得 结束日期
     * @return 结束日期
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * 设置 结束日期
     * @param enddate 结束日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
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
     * 取得 发行机构名称
     * @return 发行机构名称
     */
    public String getIssuePartyName() {
        return issuePartyName;
    }

    /**
     * 设置 发行机构名称
     * @param issuePartyName 发行机构名称
     */
    public void setIssuePartyName(String issuePartyName) {
        this.issuePartyName = issuePartyName == null ? null : issuePartyName.trim();
    }

    /**
     * 取得 报表频率
     * @return 报表频率
     */
    public String getReportfrequ() {
        return reportfrequ;
    }

    /**
     * 设置 报表频率
     * @param reportfrequ 报表频率
     */
    public void setReportfrequ(String reportfrequ) {
        this.reportfrequ = reportfrequ == null ? null : reportfrequ.trim();
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
    @JsonDeserialize(using = CustomDateDeserialize.class)
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
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    /**
     * 取得 行内报送截止日
     * @return 行内报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 设置 行内报送截止日
     * @param submitDeadline 行内报送截止日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    /**
     * 取得 返回信息
     * @return 返回信息
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 设置 返回信息
     * @param submitFeedbackStatus 返回信息
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 取得 报送状态
     * @return 报送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置 报送状态
     * @param sendStatus 报送状态
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
    @JsonDeserialize(using = CustomDateDeserialize.class)
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
     * 取得 部门代码
     * @return 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }
}