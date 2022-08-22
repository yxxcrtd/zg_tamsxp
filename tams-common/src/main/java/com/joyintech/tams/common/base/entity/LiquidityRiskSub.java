package com.joyintech.tams.common.base.entity;

import java.util.Date;

/**
 * 流动性风险应对情况信息登记
 *
 * @author wangyaning
 */
public class LiquidityRiskSub extends LiquidityRiskInfo {
    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 统计年月
     */
    private String itemId;

    /**
     * 报表名称
     */
    private String itemName;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 报送时间
     */
    private Date submitTime;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 反馈状态
     */
    private String submitFeedbackStatus;

    /**
     * 报送反馈信息
     */
    private String submitFeedbackMsg;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 创建部门
     */
    private String deptCode;

    public String getRegistSeqno() {
        return registSeqno;
    }

    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus;
    }

    public String getSubmitFeedbackMsg() {
        return submitFeedbackMsg;
    }

    public void setSubmitFeedbackMsg(String submitFeedbackMsg) {
        this.submitFeedbackMsg = submitFeedbackMsg;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}