package com.joyintech.tams.exchange.service.trade.dao.domain;

import java.util.Date;

/**
 * @author lifangpeng
 * @date 2021/10/12
 */
public class TerminalAgentInfo {
    /**
     * 账户
     */
    private String accountId;
    /**
     * 终端类型：kcbp-金证(W版)，kdgateway-金证(U版)，ufx-恒生柜台，jsd-金仕达，ctp-国债期货
     */
    private String agentType;
    /**
     * 请求码
     */
    private int functionCode;
    /**
     * 委托属性
     */
    private String entrustProp;
    /**
     * 行情类型：
     */
    private int quoteType;
    /**
     * 市场
     */
    private String market;
    /**
     * 交易方向
     */
    private String entrustBs;
    /**
     * 分组
     */
    private String gpCode;
    /**
     * 触发器编号
     */
    private String triggerCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 状态
     */
    private int status;

    /**
     * 接口平台地址
     */
    private String dspUrl;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(int functionCode) {
        this.functionCode = functionCode;
    }

    public String getEntrustProp() {
        return entrustProp;
    }

    public void setEntrustProp(String entrustProp) {
        this.entrustProp = entrustProp;
    }

    public int getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(int quoteType) {
        this.quoteType = quoteType;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getEntrustBs() {
        return entrustBs;
    }

    public void setEntrustBs(String entrustBs) {
        this.entrustBs = entrustBs;
    }

    public String getGpCode() {
        return gpCode;
    }

    public void setGpCode(String gpCode) {
        this.gpCode = gpCode;
    }

    public String getTriggerCode() {
        return triggerCode;
    }

    public void setTriggerCode(String triggerCode) {
        this.triggerCode = triggerCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getDspUrl() {
        return dspUrl;
    }

    public void setDspUrl(String dspUrl) {
        this.dspUrl = dspUrl;
    }
}
