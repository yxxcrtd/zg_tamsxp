/*
 * 文件名：CfetsBondDealRequestQuoteDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：wangzhuangzhuang
 * 修改时间：2020年5月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.bankuplink.bond.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



/**
 * 请求报价dto
 * 
 * @author wangzhuangzhuang
 * @version 1.0 2020年5月19日
 * @see CfetsBondDealRequestQuoteDto
 * @since 1.0
 */
public class CfetsBondDealRequestQuoteDto extends CfetsBondDealEntrustOrderDto {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -6517441367865558290L;

    /**
     * 报价编号（内部代码）
     */
    private String quoteId;

    /**
     * 交割日期
     */
    private Date settleDate;

    /**
     * 结算方式
     */
    private String settleMethod;

    /**
     * 清算类型
     */
    private String settleType;

    /**
     * 结算币种
     */
    private String settleCcy;

    /**
     * 生效日期
     */
    private String effDate;

    /**
     * 画面打开模式
     */
    private String quotaPageModel;

    /**
     * 对手方列表
     */
    private List<String> counterIdList;
    /**
     * 对手方信息列表
     */
    private List<CounterInfoDto> counterInfoList;
    /**
     * 明细编号列表
     */
    private List<String> detIdList;
    
    /**
     * 本方角色
     */
    private String quoteRoleType;
    
    /**
     * 报价状态，新增中/已新增/修改中/已修改/撤回中/已撤回/成交中/已成交/终止中/已终止/待回复/已撤销/
     */
    private String tradeStatus;
    
    /**
     * 发送状态，待发送/成功/失败/发送中
     */
    private String sendStatus;
    
    /**
     * 操作类型
     */
    private String OperateType;
    
    /**
     * 组合代码
     */
    private String portfolioId;
    
    /**
     * 按钮操作类型
     */
    private String optType;
    
    private BigDecimal entrustAvaNum;
    /**
     * 组合分配信息
     */
    private List<CfetsBondDealRequestQuoteDto> cfetsBondDealRequestQuoteDtoList;

    private BigDecimal dealFeeAmt;

    private BigDecimal settFeeAmt;

    private String distribution;

    /**
     * 净价总额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal cpriceAmt;

    /**
     * 应计利息总额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal intAmt;

    /**
     * 全价总额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal fpriceAmt;

    private BigDecimal lendinShareAmt;

    private BigDecimal ourShareAmt;

    private BigDecimal unitFprice;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;

    @ApiModelProperty(name="softSkipMode",value="软控是否继续？Y：继续",example="Y")
    private String softSkipMode;

    public BigDecimal getUnitFprice() {
        return unitFprice;
    }

    public void setUnitFprice(BigDecimal unitFprice) {
        this.unitFprice = unitFprice;
    }

    public BigDecimal getLendinShareAmt() {
        return lendinShareAmt;
    }

    public void setLendinShareAmt(BigDecimal lendinShareAmt) {
        this.lendinShareAmt = lendinShareAmt;
    }

    public BigDecimal getOurShareAmt() {
        return ourShareAmt;
    }

    public void setOurShareAmt(BigDecimal ourShareAmt) {
        this.ourShareAmt = ourShareAmt;
    }

    public BigDecimal getCpriceAmt() {
        return cpriceAmt;
    }

    public void setCpriceAmt(BigDecimal cpriceAmt) {
        this.cpriceAmt = cpriceAmt;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    public BigDecimal getFpriceAmt() {
        return fpriceAmt;
    }

    public void setFpriceAmt(BigDecimal fpriceAmt) {
        this.fpriceAmt = fpriceAmt;
    }

    public String getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	/**
     * 券面（万）(份额 * 100)
     */
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal prinAmt;
    
    
    /**
     * 获取 quoteId
     * 
     * @return quoteId.
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * 设置 quoteId
     * 
     * @param quoteId quoteId
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    /**
     * 获取 settleDate
     * 
     * @return settleDate.
     */
    public Date getSettleDate() {
        return settleDate;
    }

    /**
     * 设置 settleDate
     * 
     * @param settleDate settleDate
     */
    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    /**
     * 获取 settleMethod
     * 
     * @return settleMethod.
     */
    public String getSettleMethod() {
        return settleMethod;
    }

    /**
     * 设置 settleMethod
     * 
     * @param settleMethod settleMethod
     */
    public void setSettleMethod(String settleMethod) {
        this.settleMethod = settleMethod;
    }

    /**
     * 获取 settleType
     * 
     * @return settleType.
     */
    public String getSettleType() {
        return settleType;
    }

    /**
     * 设置 settleType
     * 
     * @param settleType settleType
     */
    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    /**
     * 获取 settleCcy
     * 
     * @return settleCcy.
     */
    public String getSettleCcy() {
        return settleCcy;
    }

    /**
     * 设置 settleCcy
     * 
     * @param settleCcy settleCcy
     */
    public void setSettleCcy(String settleCcy) {
        this.settleCcy = settleCcy;
    }


    /**
     * 获取 effDate
     * 
     * @return effDate.
     */
    public String getEffDate() {
        return effDate;
    }

    /**
     * 获取 quotaPageModel
     * 
     * @return quotaPageModel.
     */
    public String getQuotaPageModel() {
        return quotaPageModel;
    }

    /**
     * 设置 quotaPageModel
     * 
     * @param quotaPageModel quotaPageModel
     */
    public void setQuotaPageModel(String quotaPageModel) {
        this.quotaPageModel = quotaPageModel;
    }

    /**
     * 设置 effDate
     * 
     * @param effDate effDate
     */
    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }

    /**
     * 获取 counterIdList
     * 
     * @return counterIdList.
     */
    public List<String> getCounterIdList() {
        return counterIdList;
    }

    /**
     * 设置 counterIdList
     * 
     * @param counterIdList counterIdList
     */
    public void setCounterIdList(List<String> counterIdList) {
        this.counterIdList = counterIdList;
    }

    
    /**
     * 获取 counterInfoList
     * @return counterInfoList.
     */
    public List<CounterInfoDto> getCounterInfoList() {
        return counterInfoList;
    }

    
    /**
     * 设置 counterInfoList
     * @param counterInfoList counterInfoList
     */
    public void setCounterInfoList(List<CounterInfoDto> counterInfoList) {
        this.counterInfoList = counterInfoList;
    }

    
    /**
     * 获取 quoteRoleType
     * @return quoteRoleType.
     */
    public String getQuoteRoleType() {
        return quoteRoleType;
    }

    
    /**
     * 设置 quoteRoleType
     * @param quoteRoleType quoteRoleType
     */
    public void setQuoteRoleType(String quoteRoleType) {
        this.quoteRoleType = quoteRoleType;
    }

    
    /**
     * 获取 tradeStatus
     * @return tradeStatus.
     */
    public String getTradeStatus() {
        return tradeStatus;
    }

    
    /**
     * 设置 tradeStatus
     * @param tradeStatus tradeStatus
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    
    /**
     * 获取 sendStatus
     * @return sendStatus.
     */
    public String getSendStatus() {
        return sendStatus;
    }

    
    /**
     * 设置 sendStatus
     * @param sendStatus sendStatus
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    
    /**
     * 获取 detIdList
     * @return detIdList.
     */
    public List<String> getDetIdList() {
        return detIdList;
    }

    
    /**
     * 设置 detIdList
     * @param detIdList detIdList
     */
    public void setDetIdList(List<String> detIdList) {
        this.detIdList = detIdList;
    }
    
    
    /**
     * 获取 prinAmt
     * 
     * @return prinAmt.
     */
    public BigDecimal getPrinAmt() {
        return prinAmt;
    }

    /**
     * 设置 prinAmt
     * 
     * @param prinAmt prinAmt
     */
    public void setPrinAmt(BigDecimal prinAmt) {
        this.prinAmt = prinAmt;
    }

	public String getOperateType() {
		return OperateType;
	}

	public void setOperateType(String operateType) {
		OperateType = operateType;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public BigDecimal getEntrustAvaNum() {
		return entrustAvaNum;
	}

	public void setEntrustAvaNum(BigDecimal entrustAvaNum) {
		this.entrustAvaNum = entrustAvaNum;
	}

	public List<CfetsBondDealRequestQuoteDto> getCfetsBondDealRequestQuoteDtoList() {
		return cfetsBondDealRequestQuoteDtoList;
	}

	public void setCfetsBondDealRequestQuoteDtoList(List<CfetsBondDealRequestQuoteDto> cfetsBondDealRequestQuoteDtoList) {
		this.cfetsBondDealRequestQuoteDtoList = cfetsBondDealRequestQuoteDtoList;
	}

    public BigDecimal getDealFeeAmt() {
        return dealFeeAmt;
    }

    public void setDealFeeAmt(BigDecimal dealFeeAmt) {
        this.dealFeeAmt = dealFeeAmt;
    }

    public BigDecimal getSettFeeAmt() {
        return settFeeAmt;
    }

    public void setSettFeeAmt(BigDecimal settFeeAmt) {
        this.settFeeAmt = settFeeAmt;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSoftSkipMode() {
        return softSkipMode;
    }

    public void setSoftSkipMode(String softSkipMode) {
        this.softSkipMode = softSkipMode;
    }
}
