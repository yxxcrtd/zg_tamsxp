package com.joyintech.tams.settle.batch.datagather.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chenzhuo
 * @date 2020/7/17
 * @see SettleDelDealDetailDto
 * @since 1.0
 */
public class SettleDelDealDetailDto {
	
	/**
	 * 组合代码
	 */
	private String portfolioId;
	
	/**
	 * 金融产品代码
	 */
	private String finprodId;
	
	/**
	 * 金融产品类型（投管）
	 */
	private String finprodType2;
	
	/**
	 * 币种
	 */
	private String ccy;
	
	/**
	 * 权益登记日份额
	 */
	private BigDecimal redDateAmt;
	
	/**
	 * 权益登记日
	 */
	private Date regDate;
	
	/**
	 * 除权日
	 */
	private Date delDate;
	
	/**
	 * 派息日
	 */
	private Date devidentDate;
	
	/**
	 * 红股上市日
	 */
	private Date redShareListingDate;
	
	/**
	 * 每股送转
	 */
	private BigDecimal preDevidengAmt;
	
	/**
	 * 每股派息
	 */
	private BigDecimal preDevidengCash;
	
	/**
	 * 分红金额
	 */
	private BigDecimal bonusAmt;
	
	/**
	 * 分红份额
	 */
	private BigDecimal bonusQty;
	
	/**
	 * 证券账户代码
	 */
	private String secAcctId;

    /**
     * 单位分红净值
     */
    private BigDecimal unitBonusUnitValue;

    /**
     * 分红类型
     */
    private String bonusMode;
    
    /**
     * 交易场所
     */
    private String tradeMarket;

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

	public String getFinprodType2() {
		return finprodType2;
	}

	public void setFinprodType2(String finprodType2) {
		this.finprodType2 = finprodType2;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getRedDateAmt() {
		return redDateAmt;
	}

	public void setRedDateAmt(BigDecimal redDateAmt) {
		this.redDateAmt = redDateAmt;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	public Date getDevidentDate() {
		return devidentDate;
	}

	public void setDevidentDate(Date devidentDate) {
		this.devidentDate = devidentDate;
	}

	public Date getRedShareListingDate() {
		return redShareListingDate;
	}

	public void setRedShareListingDate(Date redShareListingDate) {
		this.redShareListingDate = redShareListingDate;
	}

	public BigDecimal getPreDevidengAmt() {
		return preDevidengAmt;
	}

	public void setPreDevidengAmt(BigDecimal preDevidengAmt) {
		this.preDevidengAmt = preDevidengAmt;
	}

	public BigDecimal getPreDevidengCash() {
		return preDevidengCash;
	}

	public void setPreDevidengCash(BigDecimal preDevidengCash) {
		this.preDevidengCash = preDevidengCash;
	}

	public BigDecimal getBonusAmt() {
		return bonusAmt;
	}

	public void setBonusAmt(BigDecimal bonusAmt) {
		this.bonusAmt = bonusAmt;
	}

	public BigDecimal getBonusQty() {
		return bonusQty;
	}

	public void setBonusQty(BigDecimal bonusQty) {
		this.bonusQty = bonusQty;
	}

	public String getSecAcctId() {
		return secAcctId;
	}

	public void setSecAcctId(String secAcctId) {
		this.secAcctId = secAcctId;
	}

	public BigDecimal getUnitBonusUnitValue() {
		return unitBonusUnitValue;
	}

	public void setUnitBonusUnitValue(BigDecimal unitBonusUnitValue) {
		this.unitBonusUnitValue = unitBonusUnitValue;
	}

	public String getBonusMode() {
		return bonusMode;
	}

	public void setBonusMode(String bonusMode) {
		this.bonusMode = bonusMode;
	}

	public String getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(String tradeMarket) {
		this.tradeMarket = tradeMarket;
	}
}
