package com.joyintech.tams.svreport.chb.liquidityriskreg.dto;

import com.joyintech.tams.common.base.entity.LiquidityRiskSub;
import com.joyintech.tams.report.common.base.annotate.TableName;

/**
 * 描述:LIQUIDITY_RISK_SUB表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-25
 */
@TableName("LIQUIDITY_RISK_SUB")
public class LiquidityRiskRegist001Dto extends LiquidityRiskSub {
    //数据状态
    private String statusStr;
    //报送状态
    private String sendStatusStr;
    //报送反馈状态
    private String submitFeedbackStatusStr;
    //联系人姓名
    private String contactName;
    //联系人电话
    private String contactPhoneNum;
    //发生次数
    private String frequency;
    //产品只数
    private String prodParameter;
    //机构名称
    private String orgName;
    /**
     * 风险发生日期
     */
    private String occurDateStr;
    /**
     * 风险应对日期
     */
    private String replyDateStr;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getSendStatusStr() {
        return sendStatusStr;
    }

    public void setSendStatusStr(String sendStatusStr) {
        this.sendStatusStr = sendStatusStr;
    }

    public String getSubmitFeedbackStatusStr() {
        return submitFeedbackStatusStr;
    }

    public void setSubmitFeedbackStatusStr(String submitFeedbackStatusStr) {
        this.submitFeedbackStatusStr = submitFeedbackStatusStr;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getProdParameter() {
        return prodParameter;
    }

    public void setProdParameter(String prodParameter) {
        this.prodParameter = prodParameter;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOccurDateStr() {
        return occurDateStr;
    }

    public void setOccurDateStr(String occurDateStr) {
        this.occurDateStr = occurDateStr;
    }

    public String getReplyDateStr() {
        return replyDateStr;
    }

    public void setReplyDateStr(String replyDateStr) {
        this.replyDateStr = replyDateStr;
    }
}