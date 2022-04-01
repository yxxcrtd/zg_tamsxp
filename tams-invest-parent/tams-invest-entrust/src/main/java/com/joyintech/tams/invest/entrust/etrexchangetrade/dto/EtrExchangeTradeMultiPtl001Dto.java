/*
 * 文件名：EtrExchangeTradeDto001.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.entrust.etrexchangetrade.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.background.component.common.pack.common.base.BaseEntity;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.EtrEntrust;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.invest.common.risktrial.dto.RiskTrialRetDto;
import com.joyintech.tams.invest.entrust.etrexchangetrade.validator.EtrExchangeTrade001Validator;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.math.BigDecimal;
import java.util.List;

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
     * 净价价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal price;

    /**
     * 全价价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal fprice;

    /**
     * 收益率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal yield;

    /**
     * 委托额度，交易金额,折算率，回购利率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal entrustPrice;


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
        return StringUtil.isNotEmpty(proportion)?proportion:BigDecimal.ZERO;
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
        return StringUtil.isNotEmpty(availableCashT)?availableCashT:BigDecimal.ZERO;
    }

    public void setAvailableCashT(BigDecimal availableCashT) {
        this.availableCashT = availableCashT;
    }

    public BigDecimal getAvailablePositionsT() {
        return StringUtil.isNotEmpty(availablePositionsT)?availablePositionsT:BigDecimal.ZERO;
    }

    public void setAvailablePositionsT(BigDecimal availablePositionsT) {
        this.availablePositionsT = availablePositionsT;
    }

    public BigDecimal getAvailableCashT1() {
        return StringUtil.isNotEmpty(availableCashT1)?availableCashT1:BigDecimal.ZERO;
    }

    public void setAvailableCashT1(BigDecimal availableCashT1) {
        this.availableCashT1 = availableCashT1;
    }

    public BigDecimal getAvailablePositionsT1() {
        return StringUtil.isNotEmpty(availablePositionsT1)?availablePositionsT1:BigDecimal.ZERO;
    }

    public void setAvailablePositionsT1(BigDecimal availablePositionsT1) {
        this.availablePositionsT1 = availablePositionsT1;
    }

    public BigDecimal getProductNetValue() {
        return StringUtil.isNotEmpty(productNetValue)?productNetValue:BigDecimal.ZERO;
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

    public BigDecimal getFprice() {
        return fprice;
    }

    public void setFprice(BigDecimal fprice) {
        this.fprice = fprice;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public BigDecimal getEntrustPrice() {
        return StringUtil.isNotEmpty(entrustPrice)?entrustPrice:BigDecimal.ZERO;
    }

    public void setEntrustPrice(BigDecimal entrustPrice) {
        this.entrustPrice = entrustPrice;
    }


    public String getProportionStr() {
        return this.getProportion() +"%";
    }

    public String getPsStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.交易方向, this.getPs());
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
}
