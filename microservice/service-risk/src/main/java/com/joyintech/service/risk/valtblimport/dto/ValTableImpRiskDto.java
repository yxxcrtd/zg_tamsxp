package com.joyintech.service.risk.valtblimport.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 估值表导入风险实占留痕表
 * @author zhangpei
 *
 */
public class ValTableImpRiskDto {
	/**
	 * 交易编号
	 */
	private String tradeId;
	/**
	 * 委外组合
	 */
	private String wwPortfolioid;
	/**
	 * 组合代码
	 */
	private String portfolioId;
	/**
	 * 资产代码
	 */
	private String finprodId;
	/**
	 * 交易方向
	 */
	private String ps;
	/**
	 * 指令日期
	 */
	private Date eventDate;
	/**
	 * 金额
	 */
	private  BigDecimal amount;
	
	private BigDecimal creditAmount;
	/**
	 * 资产类型
	 */
	private String assetType;
	
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getWwPortfolioid() {
		return wwPortfolioid;
	}
	public void setWwPortfolioid(String wwPortfolioid) {
		this.wwPortfolioid = wwPortfolioid;
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
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public BigDecimal getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}
	
	

}
