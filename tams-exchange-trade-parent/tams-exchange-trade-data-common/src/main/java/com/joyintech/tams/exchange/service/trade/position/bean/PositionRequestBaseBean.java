package com.joyintech.tams.exchange.service.trade.position.bean;


import com.joyintech.tams.exchange.common.request.PositionInstructionRequest;

import java.math.BigDecimal;

/**
 * @author swwan
 * @date 2022/2/22 - 10:49
 */
public class PositionRequestBaseBean {

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * 证券代码
     */
    private String finprodId;

    /**
     * 业务编号
     */
    private String tradeId;

    /**
     * 指令编号
     */
    private String etrId;
    
    /**
     * 下单编号
     */
    private String ordId;

    /**
     * 证券管理账户
     */
    private String secAcctId;

    /**
     * 资金账号
     */
    private String cashAcctId;

    /**
     * 交易数量
     */
    private BigDecimal secAmount;

    /**
     * 交易金额
     */
    private BigDecimal cashAmount;


    public PositionRequestBaseBean() {
    }

    public PositionRequestBaseBean(PositionInstructionRequest instruction) {
        this.portfolioId = instruction.getPortfolioId();
        this.finprodId = instruction.getFinprodId();
        this.tradeId = instruction.getEntrustNo();
        this.ordId = instruction.getEntrustNo();
        this.etrId = instruction.getEntrustId();
        this.cashAcctId = instruction.getAcctNo();
        this.secAcctId = instruction.getSecAcctId();
        this.cashAmount = BigDecimal.ZERO;
        this.secAmount = BigDecimal.ZERO;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public String getCashAcctId() {
        return cashAcctId;
    }

    public void setCashAcctId(String cashAcctId) {
        this.cashAcctId = cashAcctId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getEtrId() {
        return etrId;
    }

    public void setEtrId(String etrId) {
        this.etrId = etrId;
    }

    public BigDecimal getSecAmount() {
        return secAmount;
    }

    public void setSecAmount(BigDecimal secAmount) {
        this.secAmount = secAmount;
    }

    public void setSecAmount(Long secAmount) {
        this.secAmount = BigDecimal.valueOf(secAmount);
    }

    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
}
