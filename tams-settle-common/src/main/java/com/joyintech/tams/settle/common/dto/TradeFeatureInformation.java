/*
 * 文件名：TradeFeatureInformation.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年7月13日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.tams.settle.common.dto;

import java.util.Date;

/**
 * 交易特征信息
 * 
 * @description 
 * @author 王波
 * @version 1.0 2020年7月13日
 * @see TradeFeatureInformation
 * @since 1.0
 */
public class TradeFeatureInformation {
	
	// 交易场所
	private String tradeMarket;
	
	// 交易平台
	private String tradePlat;
	
	// 交易方向
	private String ps;

	// 期限品种
	private String termType;

	/**
	 * 证券账户代码
	 */
	private String secAcctId;

	/**
	 * 组合代码
	 */
	private String portfolioId;

	/**
	 * 金融产品代码
	 */
	private String finprodId;

	/**
	 * 分支序号
	 */
	private Long branch;

	/**
	 * 交易日期
	 */
	private Date dealDate;

	/**
	 * 金融产品类型（投管分类）
	 */
	private String finprodType2;

	// add by etliu 20220104 start
	// GZYHTZJY-868: 日终清算，协议回购的佣金没有计算出来
	/**
	 * 回购类型
	 */
	private String repoType;

	public String getRepoType() {
		return repoType;
	}

	public void setRepoType(String repoType) {
		this.repoType = repoType;
	}
	// add by etliu 20220104 end

	public String getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(String tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public String getTradePlat() {
		return tradePlat;
	}

	public void setTradePlat(String tradePlat) {
		this.tradePlat = tradePlat;
	}

	public String getPs() {
		return ps;
	}

	public void setPs(String ps) {
		this.ps = ps;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getSecAcctId() {
		return secAcctId;
	}

	public void setSecAcctId(String secAcctId) {
		this.secAcctId = secAcctId;
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

	public Long getBranch() {
		return branch;
	}

	public void setBranch(Long branch) {
		this.branch = branch;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getFinprodType2() {
		return finprodType2;
	}

	public void setFinprodType2(String finprodType2) {
		this.finprodType2 = finprodType2;
	}
}
