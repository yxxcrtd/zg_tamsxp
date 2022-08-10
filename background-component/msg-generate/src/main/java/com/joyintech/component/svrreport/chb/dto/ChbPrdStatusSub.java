package com.joyintech.component.svrreport.chb.dto;

import com.joyintech.component.svrreport.chb.contant.annotate.RateFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ChbPrdStatusSub{
    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 产品登记编码
     */
    private String prdRegistCode;

    /**
     * 理财产品总资产金额（元）
     */
    private BigDecimal prdTotalAmt;

    /**
     * 理财产品杠杆率（%）
     */
    @RateFormat
    private BigDecimal prdLeverage;

    /**
     * 产品状态统计日
     */
    private Date prdStatusDate;

    /**
     * 期数
     */
    private String termid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 反馈状态
     */
    private String submitFeedbackStatus;
    /**
     * 报送流水号
     */
    private String submitSeq;


    /**
     * 反馈信息
     */
    private String submitFeedbackMsg;

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
     * 取得 产品代码
     * @return 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 设置 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
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
     * 取得 产品登记编码
     * @return 产品登记编码
     */
    public String getPrdRegistCode() {
        return prdRegistCode;
    }

    /**
     * 设置 产品登记编码
     * @param prdRegistCode 产品登记编码
     */
    public void setPrdRegistCode(String prdRegistCode) {
        this.prdRegistCode = prdRegistCode == null ? null : prdRegistCode.trim();
    }

    /**
     * 取得 理财产品总资产金额（元）
     * @return 理财产品总资产金额（元）
     */
    public BigDecimal getPrdTotalAmt() {
        return prdTotalAmt;
    }

    /**
     * 设置 理财产品总资产金额（元）
     * @param prdTotalAmt 理财产品总资产金额（元）
     */
    public void setPrdTotalAmt(BigDecimal prdTotalAmt) {
        this.prdTotalAmt = prdTotalAmt;
    }

    /**
     * 取得 理财产品杠杆率（%）
     * @return 理财产品杠杆率（%）
     */
    public BigDecimal getPrdLeverage() {
        return prdLeverage;
    }

    /**
     * 设置 理财产品杠杆率（%）
     * @param prdLeverage 理财产品杠杆率（%）
     */
    public void setPrdLeverage(BigDecimal prdLeverage) {
        this.prdLeverage = prdLeverage;
    }

    /**
     * 取得 产品状态统计日
     * @return 产品状态统计日
     */
    public Date getPrdStatusDate() {
        return prdStatusDate;
    }

    /**
     * 设置 产品状态统计日
     * @param prdStatusDate 产品状态统计日
     */
    public void setPrdStatusDate(Date prdStatusDate) {
        this.prdStatusDate = prdStatusDate;
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
     * 取得 备注
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 取得 报送截止日
     * @return 报送截止日
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 设置 报送截止日
     * @param chbSubmitDeadline 报送截止日
     */
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
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
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
     * 取得 反馈状态
     * @return 反馈状态
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 设置 反馈状态
     * @param submitFeedbackStatus 反馈状态
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    public String getSubmitSeq() {
        return submitSeq;
    }

    public void setSubmitSeq(String submitSeq) {
        this.submitSeq = submitSeq;
    }

    public String getSubmitFeedbackMsg() {
        return submitFeedbackMsg;
    }

    public void setSubmitFeedbackMsg(String submitFeedbackMsg) {
        this.submitFeedbackMsg = submitFeedbackMsg;
    }
}