package com.joyintech.tams.svreport.interf.chb.investtrade.dto;

import java.math.BigDecimal;
import java.util.Date;


public class ChbSecRepayInfoDto {

    /**
     * 债券代码
     */
    private String secId;
    /**
     * 债券名称
     */
    private String secNm;
    /**
     * 还本日期
     */
    private Date repayDt;
    /**
     * 还本比例
     */
    private BigDecimal repayRate;
    /**
     * 还本后面值
     */
    private BigDecimal repayNet;
    /**
     * 上市地点
     */
    private String tradeMarket;
    /**
     * 还本周期计息截止日
     */
    private Date repayCycleDay;
    /**
     * 还本方式
     */
    private String repayMode;

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSecNm() {
        return secNm;
    }

    public void setSecNm(String secNm) {
        this.secNm = secNm;
    }

    public Date getRepayDt() {
        return repayDt;
    }

    public void setRepayDt(Date repayDt) {
        this.repayDt = repayDt;
    }

    public BigDecimal getRepayRate() {
        return repayRate;
    }

    public void setRepayRate(BigDecimal repayRate) {
        this.repayRate = repayRate;
    }

    public BigDecimal getRepayNet() {
        return repayNet;
    }

    public void setRepayNet(BigDecimal repayNet) {
        this.repayNet = repayNet;
    }

    public String getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(String tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public Date getRepayCycleDay() {
        return repayCycleDay;
    }

    public void setRepayCycleDay(Date repayCycleDay) {
        this.repayCycleDay = repayCycleDay;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }
}