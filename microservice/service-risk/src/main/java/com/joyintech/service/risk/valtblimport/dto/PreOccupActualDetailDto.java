package com.joyintech.service.risk.valtblimport.dto;

import java.math.BigDecimal;

/**
 * 估值表持仓情况
 * @author zhangpei
 *
 */
public class PreOccupActualDetailDto {
	/**
	 * 估值表持仓情况明细_资产代码
	 */
	private String assetFinprodId;
	/**
	 * 估值表的持仓情况明细_具体持仓
	 */
	private BigDecimal valAmout;
	/**
	 * 分配的持仓
	 */
	private BigDecimal allotAmout;
	private BigDecimal allotAmoutCredit;
	/**
	 * 投管分类
	 */
	private String finprodType2;
	
	public String getAssetFinprodId() {
		return assetFinprodId;
	}
	public void setAssetFinprodId(String assetFinprodId) {
		this.assetFinprodId = assetFinprodId;
	}
	public BigDecimal getAllotAmout() {
		return allotAmout;
	}
	public void setAllotAmout(BigDecimal allotAmout) {
		this.allotAmout = allotAmout;
	}
	public BigDecimal getValAmout() {
		return valAmout;
	}
	public void setValAmout(BigDecimal valAmout) {
		this.valAmout = valAmout;
	}
	public String getFinprodType2() {
		return finprodType2;
	}
	public void setFinprodType2(String finprodType2) {
		this.finprodType2 = finprodType2;
	}
	public BigDecimal getAllotAmoutCredit() {
		return allotAmoutCredit;
	}
	public void setAllotAmoutCredit(BigDecimal allotAmoutCredit) {
		this.allotAmoutCredit = allotAmoutCredit;
	}
	
	

}
