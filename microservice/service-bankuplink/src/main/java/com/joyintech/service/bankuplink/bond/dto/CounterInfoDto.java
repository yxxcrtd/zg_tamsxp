/*
 * 文件名：CounterInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：wangzhuangzhuang
 * 修改时间：2020年5月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.bankuplink.bond.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.background.component.common.pack.common.json.BigDecimalSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 对手方信息dto
 * 
 * @author wangzhuangzhuang
 * @version 1.0 2020年5月19日
 * @see CounterInfoDto
 * @since 1.0
 */
public class CounterInfoDto{
    /**
     * 明细编号
     */
    private String detId;

    /**
     * 对手方名称
     */
    private String counterValue;
    /**
     * 对手方Id
     */
    private String counterId;
    /**
     * 对手银行间机构，21位编号
     */
    private String cpPartyId;
    /**
     * 对手方交易员
     */
    private String cpTrader;
    /**
     * 交易状态
     */
    private String tradeStatus;
    /**
     * 回复量(万元)
     */
    private BigDecimal shareAmt;
    /**
     * 净价
     */
    private BigDecimal unitCprice;
    /**
     * 全价
     */
    private BigDecimal unitFprice;
    /**
     * 到期收益率(%)
     */
    private BigDecimal yield;
    /**
     * 行权收益率(%
     */
    private BigDecimal exerYield;
    /**
     * 净价总额
     */
    private BigDecimal cpriceAmt;

    /**
     * 利息总额
     */
    private BigDecimal intAmt;

    /**
     * 全价总额
     */
    private BigDecimal fpriceAmt;
    
    /**
     * 获取 counterValue
     * @return counterValue.
     */
    public String getCounterValue() {
        return counterValue;
    }
    
    /**
     * 设置 counterValue
     * @param counterValue counterValue
     */
    public void setCounterValue(String counterValue) {
        this.counterValue = counterValue;
    }
    
    /**
     * 获取 counterId
     * @return counterId.
     */
    public String getCounterId() {
        return counterId;
    }
    
    /**
     * 设置 counterId
     * @param counterId counterId
     */
    public void setCounterId(String counterId) {
        this.counterId = counterId;
    }

    
    /**
     * 获取 cpTrader
     * @return cpTrader.
     */
    public String getCpTrader() {
        return cpTrader;
    }

    
    /**
     * 设置 cpTrader
     * @param cpTrader cpTrader
     */
    public void setCpTrader(String cpTrader) {
        this.cpTrader = cpTrader;
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
     * 获取 cpPartyId
     * @return cpPartyId.
     */
    public String getCpPartyId() {
        return cpPartyId;
    }

    
    /**
     * 设置 cpPartyId
     * @param cpPartyId cpPartyId
     */
    public void setCpPartyId(String cpPartyId) {
        this.cpPartyId = cpPartyId;
    }

    
    /**
     * 获取 shareAmt
     * @return shareAmt.
     */
    public BigDecimal getShareAmt() {
        return shareAmt;
    }

    
    /**
     * 设置 shareAmt
     * @param shareAmt shareAmt
     */
    public void setShareAmt(BigDecimal shareAmt) {
        this.shareAmt = shareAmt;
    }

    
    /**
     * 获取 unitCprice
     * @return unitCprice.
     */
    public BigDecimal getUnitCprice() {
        return unitCprice;
    }

    
    /**
     * 设置 unitCprice
     * @param unitCprice unitCprice
     */
    public void setUnitCprice(BigDecimal unitCprice) {
        this.unitCprice = unitCprice;
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
     * 获取 yield
     * @return yield.
     */
    public BigDecimal getYield() {
        return yield;
    }

    
    /**
     * 设置 yield
     * @param yield yield
     */
    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    
    /**
     * 获取 exerYield
     * @return exerYield.
     */
    public BigDecimal getExerYield() {
        return exerYield;
    }

    
    /**
     * 设置 exerYield
     * @param exerYield exerYield
     */
    public void setExerYield(BigDecimal exerYield) {
        this.exerYield = exerYield;
    }

    
    /**
     * 获取 detId
     * @return detId.
     */
    public String getDetId() {
        return detId;
    }

    
    /**
     * 设置 detId
     * @param detId detId
     */
    public void setDetId(String detId) {
        this.detId = detId;
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
}
