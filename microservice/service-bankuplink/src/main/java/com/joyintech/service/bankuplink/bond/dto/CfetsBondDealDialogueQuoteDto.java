/*
 * 文件名：CfetsBondDealDialogueQuoteDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangliang
 * 修改时间：2020年5月13日
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
 * 对话报价dto
 * 
 * @author zhangliang
 * @version 1.0 2020年5月13日
 * @see CfetsBondDealDialogueQuoteDto
 * @since 1.0
 */
public class CfetsBondDealDialogueQuoteDto extends CfetsBondDealEntrustOrderDto {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -8293473776098112670L;
    
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
     * 应计利息
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal unitInt;
    
    /**
     * 全价
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal unitFprice;
    
    /**
     * 净价总额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal cpriceAmt;
    
    /**
     * 每百元金额(元)
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal parValue;
    
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
    
    /**
     * 本金额(元)
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal parValueAmt;
    
    /**
     * 回复量
     */
    private BigDecimal responseAmt;
    
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
     * 
     */
    private String quotaPageModel;
    
    
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
     * 是否多组合
     */
    private String isDzh;
    /**
     * 组合分配信息
     */
    private List<CfetsBondDealDialogueQuoteDto> cfetsBondDealDialogueQuoteDtoList;

    private BigDecimal dealFeeAmt;

    private BigDecimal settFeeAmt;

    private String distribution;

    private String type11;

    private BigDecimal lendinShareAmt;

    private BigDecimal ourShareAmt;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;

    @ApiModelProperty(name="softSkipMode",value="软控是否继续？Y：继续",example="Y")
    private String softSkipMode;

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

    public String getType11() {
        return type11;
    }

    public void setType11(String type11) {
        this.type11 = type11;
    }

    public String getPortfolioId() {
		return portfolioId;
	}


	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}


	/**
     * 获取 quoteId
     * @return quoteId.
     */
    public String getQuoteId() {
        return quoteId;
    }


    /**
     * 设置 quoteId
     * @param quoteId quoteId
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }
    
    
    /**
     * 获取 settleDate
     * @return settleDate.
     */
    public Date getSettleDate() {
        return settleDate;
    }

    
    /**
     * 设置 settleDate
     * @param settleDate settleDate
     */
    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }


    
    /**
     * 获取 settleMethod
     * @return settleMethod.
     */
    public String getSettleMethod() {
        return settleMethod;
    }


    
    /**
     * 设置 settleMethod
     * @param settleMethod settleMethod
     */
    public void setSettleMethod(String settleMethod) {
        this.settleMethod = settleMethod;
    }


    
    /**
     * 获取 settleType
     * @return settleType.
     */
    public String getSettleType() {
        return settleType;
    }


    
    /**
     * 设置 settleType
     * @param settleType settleType
     */
    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }


    
    /**
     * 获取 settleCcy
     * @return settleCcy.
     */
    public String getSettleCcy() {
        return settleCcy;
    }


    
    /**
     * 设置 settleCcy
     * @param settleCcy settleCcy
     */
    public void setSettleCcy(String settleCcy) {
        this.settleCcy = settleCcy;
    }


    
    /**
     * 获取 fpriceAmt
     * @return fpriceAmt.
     */
    public BigDecimal getFpriceAmt() {
        return fpriceAmt;
    }


    
    /**
     * 设置 fpriceAmt
     * @param fpriceAmt fpriceAmt
     */
    public void setFpriceAmt(BigDecimal fpriceAmt) {
        this.fpriceAmt = fpriceAmt;
    }


    
    /**
     * 获取 unitInt
     * @return unitInt.
     */
    public BigDecimal getUnitInt() {
        return unitInt;
    }


    
    /**
     * 设置 unitInt
     * @param unitInt unitInt
     */
    public void setUnitInt(BigDecimal unitInt) {
        this.unitInt = unitInt;
    }


    
    /**
     * 获取 unitFprice
     * @return unitFprice.
     */
    public BigDecimal getUnitFprice() {
        return unitFprice;
    }


    
    /**
     * 设置 unitFprice
     * @param unitFprice unitFprice
     */
    public void setUnitFprice(BigDecimal unitFprice) {
        this.unitFprice = unitFprice;
    }


    
    /**
     * 获取 cpriceAmt
     * @return cpriceAmt.
     */
    public BigDecimal getCpriceAmt() {
        return cpriceAmt;
    }


    
    /**
     * 设置 cpriceAmt
     * @param cpriceAmt cpriceAmt
     */
    public void setCpriceAmt(BigDecimal cpriceAmt) {
        this.cpriceAmt = cpriceAmt;
    }


    
    /**
     * 获取 parValue
     * @return parValue.
     */
    public BigDecimal getParValue() {
        return parValue;
    }


    
    /**
     * 设置 parValue
     * @param parValue parValue
     */
    public void setParValue(BigDecimal parValue) {
        this.parValue = parValue;
    }


    
    /**
     * 获取 intAmt
     * @return intAmt.
     */
    public BigDecimal getIntAmt() {
        return intAmt;
    }


    
    /**
     * 设置 intAmt
     * @param intAmt intAmt
     */
    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }


    
    /**
     * 获取 parValueAmt
     * @return parValueAmt.
     */
    public BigDecimal getParValueAmt() {
        return parValueAmt;
    }


    
    /**
     * 设置 parValueAmt
     * @param parValueAmt parValueAmt
     */
    public void setParValueAmt(BigDecimal parValueAmt) {
        this.parValueAmt = parValueAmt;
    }


    
    /**
     * 获取 responseAmt
     * @return responseAmt.
     */
    public BigDecimal getResponseAmt() {
        return responseAmt;
    }


    
    /**
     * 设置 responseAmt
     * @param responseAmt responseAmt
     */
    public void setResponseAmt(BigDecimal responseAmt) {
        this.responseAmt = responseAmt;
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


	public String getQuotaPageModel() {
		return quotaPageModel;
	}


	public void setQuotaPageModel(String quotaPageModel) {
		this.quotaPageModel = quotaPageModel;
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


	public List<CfetsBondDealDialogueQuoteDto> getCfetsBondDealDialogueQuoteDtoList() {
		return cfetsBondDealDialogueQuoteDtoList;
	}


	public void setCfetsBondDealDialogueQuoteDtoList(List<CfetsBondDealDialogueQuoteDto> cfetsBondDealDialogueQuoteDtoList) {
		this.cfetsBondDealDialogueQuoteDtoList = cfetsBondDealDialogueQuoteDtoList;
	}


	public BigDecimal getEntrustAvaNum() {
		return entrustAvaNum;
	}


	public void setEntrustAvaNum(BigDecimal entrustAvaNum) {
		this.entrustAvaNum = entrustAvaNum;
	}


    public String getIsDzh() {
        return isDzh;
    }

    public void setIsDzh(String isDzh) {
        this.isDzh = isDzh;
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
