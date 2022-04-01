package com.joyintech.tams.invest.common.trade.dto;

import java.math.BigDecimal;

/**
 *  场内组合分配计算dto
 * @author fuyongsheng
 * @version 1.0 2022年3月3日
 * @see PortfolioCalculateDto
 * @since 1.0
 */
public class PortfolioCalculateDto {

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * 下单编号
     */
    private String orderId;

    /**
     * 指令券面总额
     */
    private BigDecimal etrSecAmount;

    /**
     * 下单券面总额
     */
    private BigDecimal ordSecAmount;

    /**
     * 券面总额
     */
    private BigDecimal secAmount;

    /**
     * 交易金额
     */
    private BigDecimal cashAmount;

    /**
     * 本方券全面总额
     */
    private BigDecimal ownSecAmount;

    /**
     * 融入券券面总额
     */
    private BigDecimal integrateSecAmount;

    /**
     * 资金账号
     */
    private String cashAcctId;

    /**
     * 证券管理账号
     */
    private String secAcctId;

    /**
     * 标准券总额
     */
    private BigDecimal standardSecAmount;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public BigDecimal getEtrSecAmount() {
        return etrSecAmount;
    }

    public void setEtrSecAmount(BigDecimal etrSecAmount) {
        this.etrSecAmount = etrSecAmount;
    }

    public BigDecimal getOrdSecAmount() {
        return ordSecAmount;
    }

    public void setOrdSecAmount(BigDecimal ordSecAmount) {
        this.ordSecAmount = ordSecAmount;
    }

    public BigDecimal getSecAmount() {
        return secAmount;
    }

    public void setSecAmount(BigDecimal secAmount) {
        this.secAmount = secAmount;
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    public BigDecimal getOwnSecAmount() {
        return ownSecAmount;
    }

    public void setOwnSecAmount(BigDecimal ownSecAmount) {
        this.ownSecAmount = ownSecAmount;
    }

    public BigDecimal getIntegrateSecAmount() {
        return integrateSecAmount;
    }

    public void setIntegrateSecAmount(BigDecimal integrateSecAmount) {
        this.integrateSecAmount = integrateSecAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCashAcctId() {
        return cashAcctId;
    }

    public void setCashAcctId(String cashAcctId) {
        this.cashAcctId = cashAcctId;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public BigDecimal getStandardSecAmount() {
        return standardSecAmount;
    }

    public void setStandardSecAmount(BigDecimal standardSecAmount) {
        this.standardSecAmount = standardSecAmount;
    }
}
