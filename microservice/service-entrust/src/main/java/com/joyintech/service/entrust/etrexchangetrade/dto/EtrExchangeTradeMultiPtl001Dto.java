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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.background.component.common.pack.common.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.constant.DictCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  交易信息 （左边下）
 * @author chendijun
 * @version 1.0 2020年7月2日
 * @see EtrExchangeTradeMultiPtl001Dto
 * @since 1.0
 */
public class EtrExchangeTradeMultiPtl001Dto extends BaseEntity {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 多组合树ID
     */
    private String treeId;

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * 组合名称
     */
    private String portfolioName;

    /**
     * 资金账户
     */
    private String acctNo;

    /**
     * 资金账户名称
     */
    private String acctName;

    /**
     * 证券账户
     */
    private String secAcctId;

    /**
     * 资产代码
     */
    private String finprodId;

    /**
     * 资产市场代码
     */
    private String finprodMarketId;

    /**
     * 资产名称
     */
    private String finprodName;

    /**
     * 资产类型
     */
    private String finprodType2;

    /**
     * 分配比例
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal proportion;

    /**
     * 委托方向
     */
    private String ps;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 指令价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal price;


    /**
     * 委托额度，交易金额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal entrustAmt;

    /**
     * 委托数量，数量或者券面总额/100
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal entrustNum;



    /**
     * T日可用现金
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal availableCashT;

    /**
     * T日可用持仓
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal availablePositionsT;

    /**
     * T+1日可用现金
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal availableCashT1;

    /**
     * T+1日可用持仓
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal availablePositionsT1;

    /**
     * 产品净资产
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal productNetValue;

    /**
     * 买入全价
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal fullPrice;
    /**
     * 收益率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal yield;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    public String getFinprodName() {
        return finprodName;
    }

    public void setFinprodName(String finprodName) {
        this.finprodName = finprodName;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public BigDecimal getEntrustAmt() {
        return entrustAmt;
    }

    public void setEntrustAmt(BigDecimal entrustAmt) {
        this.entrustAmt = entrustAmt;
    }

    public BigDecimal getEntrustNum() {
        return entrustNum;
    }

    public void setEntrustNum(BigDecimal entrustNum) {
        this.entrustNum = entrustNum;
    }

    public BigDecimal getAvailableCashT() {
        return availableCashT;
    }

    public void setAvailableCashT(BigDecimal availableCashT) {
        this.availableCashT = availableCashT;
    }

    public BigDecimal getAvailablePositionsT() {
        return availablePositionsT;
    }

    public void setAvailablePositionsT(BigDecimal availablePositionsT) {
        this.availablePositionsT = availablePositionsT;
    }

    public BigDecimal getAvailableCashT1() {
        return availableCashT1;
    }

    public void setAvailableCashT1(BigDecimal availableCashT1) {
        this.availableCashT1 = availableCashT1;
    }

    public BigDecimal getAvailablePositionsT1() {
        return availablePositionsT1;
    }

    public void setAvailablePositionsT1(BigDecimal availablePositionsT1) {
        this.availablePositionsT1 = availablePositionsT1;
    }

    public BigDecimal getProductNetValue() {
        return productNetValue;
    }

    public void setProductNetValue(BigDecimal productNetValue) {
        this.productNetValue = productNetValue;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getFinprodType2() {
        return finprodType2;
    }

    public void setFinprodType2(String finprodType2) {
        this.finprodType2 = finprodType2;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
	}
	
    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

}
