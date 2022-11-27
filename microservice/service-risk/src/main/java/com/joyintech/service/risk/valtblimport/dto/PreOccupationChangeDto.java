package com.joyintech.service.risk.valtblimport.dto;

import java.math.BigDecimal;
import java.util.List;

import com.joyintech.framework.base.BaseEntity;

/**
 * 估值表导入-预占实占变更Dto
 * @author zhangpei
 * 20201222
 */
public class PreOccupationChangeDto extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 899786868411085054L;
	/**
	 * 委托代码
	 */
	private String entrustId;
	/**
	 * 组合代码
	 */
	private String portfolioId;
	/**
	 * 持仓份额
	 */
	private BigDecimal amount;
	/**
	 * 占比
	 */
	private BigDecimal percentage;
	/**
	 * 底层资产id
	 */
	private String bottomAssetId;
	/**
	 * 底层资产的投管分类
	 */
	private String finprodType2;
	/**
	 * 交易方向
	 */
	private String ps;
	/**
	 * 委托状态
	 */
	private String entrustStatus;
	/**
	 * 分配至组合的估值表资产持仓明细额度
	 */
	private List<PreOccupActualDetailDto> detailDtos;
	

	public String getEntrustId() {
		return entrustId;
	}
	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	public String getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	public List<PreOccupActualDetailDto> getDetailDtos() {
		return detailDtos;
	}
	public void setDetailDtos(List<PreOccupActualDetailDto> detailDtos) {
		this.detailDtos = detailDtos;
	}
	public String getBottomAssetId() {
		return bottomAssetId;
	}
	public void setBottomAssetId(String bottomAssetId) {
		this.bottomAssetId = bottomAssetId;
	}
	public String getFinprodType2() {
		return finprodType2;
	}
	public void setFinprodType2(String finprodType2) {
		this.finprodType2 = finprodType2;
	}
	public String getPs() {
		return ps;
	}
	public void setPs(String ps) {
		this.ps = ps;
	}
	public String getEntrustStatus() {
		return entrustStatus;
	}
	public void setEntrustStatus(String entrustStatus) {
		this.entrustStatus = entrustStatus;
	}
	
}
