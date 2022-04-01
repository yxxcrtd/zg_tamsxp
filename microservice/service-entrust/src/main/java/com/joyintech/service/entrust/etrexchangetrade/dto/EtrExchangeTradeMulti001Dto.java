/*
 * 文件名：EtrExchangeTradeDto001.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.entrust.etrexchangetrade.dto;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 *  交易信息 （左边下）
 * @author chendijun
 * @version 1.0 2020年7月2日
 * @see EtrExchangeTradeMulti001Dto
 * @since 1.0
 */
public class EtrExchangeTradeMulti001Dto extends EtrExchangeTrade001Dto implements ProcessEntity{

    /**
     * 多组合代码
     */
    private String portfolioTreeId;

    /**
     * 资产市场代码
     */
    private String finprodMarketId;

    /**
     * 权重方式
     */
    private String weightType;

    /**
     * 折算率
     */
    private String conversionRate;

    /**
     * 分配方式
     */
    private String  mulDistriClass;

    /**
     * 目标值
     */
    private BigDecimal targetValue;

    /**
     * 目标值单位
     */
    private String mulTargetUnit;

    /**
     * 多组合分配列表
     */
    private List<EtrExchangeTradeMultiPtl001Dto> tradeMultiPtl001Dtos;

    public String getPortfolioTreeId() {
        return portfolioTreeId;
    }

    public void setPortfolioTreeId(String portfolioTreeId) {
        this.portfolioTreeId = portfolioTreeId;
    }

    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    public List<EtrExchangeTradeMultiPtl001Dto> getTradeMultiPtl001Dtos() {
        return tradeMultiPtl001Dtos;
    }

    public void setTradeMultiPtl001Dtos(List<EtrExchangeTradeMultiPtl001Dto> tradeMultiPtl001Dtos) {
        this.tradeMultiPtl001Dtos = tradeMultiPtl001Dtos;
    }

    public String getWeightType() {
        return weightType;
    }

    public void setWeightType(String weightType) {
        this.weightType = weightType;
    }

    public String getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(String conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String getMulDistriClass() {
        return mulDistriClass;
    }

    public void setMulDistriClass(String mulDistriClass) {
        this.mulDistriClass = mulDistriClass;
    }

    public BigDecimal getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(BigDecimal targetValue) {
        this.targetValue = targetValue;
    }

    public String getMulTargetUnit() {
        return mulTargetUnit;
    }

    public void setMulTargetUnit(String mulTargetUnit) {
        this.mulTargetUnit = mulTargetUnit;
    }
}
