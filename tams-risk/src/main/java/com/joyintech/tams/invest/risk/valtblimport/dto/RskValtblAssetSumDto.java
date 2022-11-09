package com.joyintech.tams.invest.risk.valtblimport.dto;

import com.joyintech.tams.common.base.entity.RskValtblSumAssetSub;

import java.math.BigDecimal;
import java.util.Date;

public class RskValtblAssetSumDto extends RskValtblSumAssetSub {

    /**
     * 组合名称
     */
    private String portfolioName;
    /**
     * 估值日期
     */
    private Date valDate;
    /**
     * 总资产成本
     */
    private BigDecimal totalAssestCost;
    /**
     * 总资产市值
     */
    private BigDecimal totalAssestValue;
    /**
     * 净资产成本
     */
    private BigDecimal netAssestCost;
    /**
     * 净资产市值
     */
    private BigDecimal netAssestValue;
    /**
     * 单位净值成本
     */
    private BigDecimal unitNetWorthCost;
    /**
     * 单位净值市值
     */
    private BigDecimal unitNetWorthValue;
    /**
     * 净值波动-较上一日
     */
    private BigDecimal comparedWithDay;
    /**
     * 净值波动-较上月
     */
    private BigDecimal comparedWithMonth;
    /**
     * 净值波动-较年初
     */
    private BigDecimal comparedWithYear;
    /**
     * 偏离金额
     */
    private BigDecimal deviatingAmount;
    /**
     * 偏离度
     */
    private BigDecimal degreeOfDeviate;

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    @Override
    public Date getValDate() {
        return valDate;
    }

    @Override
    public void setValDate(Date valDate) {
        this.valDate = valDate;
    }

    public BigDecimal getTotalAssestCost() {
        return totalAssestCost;
    }

    public void setTotalAssestCost(BigDecimal totalAssestCost) {
        this.totalAssestCost = totalAssestCost;
    }

    public BigDecimal getTotalAssestValue() {
        return totalAssestValue;
    }

    public void setTotalAssestValue(BigDecimal totalAssestValue) {
        this.totalAssestValue = totalAssestValue;
    }

    public BigDecimal getNetAssestCost() {
        return netAssestCost;
    }

    public void setNetAssestCost(BigDecimal netAssestCost) {
        this.netAssestCost = netAssestCost;
    }

    public BigDecimal getNetAssestValue() {
        return netAssestValue;
    }

    public void setNetAssestValue(BigDecimal netAssestValue) {
        this.netAssestValue = netAssestValue;
    }

    public BigDecimal getUnitNetWorthCost() {
        return unitNetWorthCost;
    }

    public void setUnitNetWorthCost(BigDecimal unitNetWorthCost) {
        this.unitNetWorthCost = unitNetWorthCost;
    }

    public BigDecimal getUnitNetWorthValue() {
        return unitNetWorthValue;
    }

    public void setUnitNetWorthValue(BigDecimal unitNetWorthValue) {
        this.unitNetWorthValue = unitNetWorthValue;
    }

    public BigDecimal getComparedWithDay() {
        return comparedWithDay;
    }

    public void setComparedWithDay(BigDecimal comparedWithDay) {
        this.comparedWithDay = comparedWithDay;
    }

    public BigDecimal getComparedWithMonth() {
        return comparedWithMonth;
    }

    public void setComparedWithMonth(BigDecimal comparedWithMonth) {
        this.comparedWithMonth = comparedWithMonth;
    }

    public BigDecimal getComparedWithYear() {
        return comparedWithYear;
    }

    public void setComparedWithYear(BigDecimal comparedWithYear) {
        this.comparedWithYear = comparedWithYear;
    }

    public BigDecimal getDeviatingAmount() {
        return deviatingAmount;
    }

    public void setDeviatingAmount(BigDecimal deviatingAmount) {
        this.deviatingAmount = deviatingAmount;
    }

    public BigDecimal getDegreeOfDeviate() {
        return degreeOfDeviate;
    }

    public void setDegreeOfDeviate(BigDecimal degreeOfDeviate) {
        this.degreeOfDeviate = degreeOfDeviate;
    }
}
