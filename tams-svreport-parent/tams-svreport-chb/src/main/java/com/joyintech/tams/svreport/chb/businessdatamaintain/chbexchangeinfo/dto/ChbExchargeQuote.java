package com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.MstExchargeQuoteKey;

import java.math.BigDecimal;

public class ChbExchargeQuote extends ChbExchargeQuoteKey {

    /**
     * 开盘价
     */
    @NotEmpty(fieldName = "MstExchangeQuotationInfo002.openPrice", groups = {ValidateGroupEdit.class})
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal openPrice;

    /**
     * 收盘价
     */
    @NotEmpty(fieldName = "MstExchangeQuotationInfo002.closePrice", groups = {ValidateGroupEdit.class})
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal closePrice;

    /**
     * 最高价
     */
    @NotEmpty(fieldName = "MstExchangeQuotationInfo002.highPrice", groups = {ValidateGroupEdit.class})
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal highPrice;

    /**
     * 最低价
     */
    @NotEmpty(fieldName = "MstExchangeQuotationInfo002.lowPrice", groups = {ValidateGroupEdit.class})
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal lowPrice;

    /**
     * 中间价
     */
    @NotEmpty(fieldName = "MstExchangeQuotationInfo002.midPrice", groups = {ValidateGroupEdit.class})
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal midPrice;

    /**
     * 基础币种，比如USD/CNY中的USD
     */
    private String basisCyy;

    /**
     * 报价币种，比如USD/CNY中的CNY
     */
    private String quotCyy;

    /**
     * 报价单位
     */
    @NotEmpty(fieldName = "MstExchangeQuotationInfo002.quotUnit", groups = {ValidateGroupEdit.class})
    private Long quotUnit;

    /**
     * 录入方式，接口、手工等
     */
    private String inputType;


    /**
     * 行情类型
     */
    private String marketType;

    /**
     * 期限
     */
    private String deadLine;

    /**
     * 取得 开盘价
     * @return 开盘价
     */
    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    /**
     * 设置 开盘价
     * @param openPrice 开盘价
     */
    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    /**
     * 取得 收盘价
     * @return 收盘价
     */
    public BigDecimal getClosePrice() {
        return closePrice;
    }

    /**
     * 设置 收盘价
     * @param closePrice 收盘价
     */
    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    /**
     * 取得 最高价
     * @return 最高价
     */
    public BigDecimal getHighPrice() {
        return highPrice;
    }

    /**
     * 设置 最高价
     * @param highPrice 最高价
     */
    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    /**
     * 取得 最低价
     * @return 最低价
     */
    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    /**
     * 设置 最低价
     * @param lowPrice 最低价
     */
    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    /**
     * 取得 中间价
     * @return 中间价
     */
    public BigDecimal getMidPrice() {
        return midPrice;
    }

    /**
     * 设置 中间价
     * @param midPrice 中间价
     */
    public void setMidPrice(BigDecimal midPrice) {
        this.midPrice = midPrice;
    }

    /**
     * 取得 基础币种，比如USD/CNY中的USD
     * @return 基础币种，比如USD/CNY中的USD
     */
    public String getBasisCyy() {
        return basisCyy;
    }

    /**
     * 设置 基础币种，比如USD/CNY中的USD
     * @param basisCyy 基础币种，比如USD/CNY中的USD
     */
    public void setBasisCyy(String basisCyy) {
        this.basisCyy = basisCyy == null ? null : basisCyy.trim();
    }

    /**
     * 取得 报价币种，比如USD/CNY中的CNY
     * @return 报价币种，比如USD/CNY中的CNY
     */
    public String getQuotCyy() {
        return quotCyy;
    }

    /**
     * 设置 报价币种，比如USD/CNY中的CNY
     * @param quotCyy 报价币种，比如USD/CNY中的CNY
     */
    public void setQuotCyy(String quotCyy) {
        this.quotCyy = quotCyy == null ? null : quotCyy.trim();
    }

    /**
     * 取得 报价单位
     * @return 报价单位
     */
    public Long getQuotUnit() {
        return quotUnit;
    }

    /**
     * 设置 报价单位
     * @param quotUnit 报价单位
     */
    public void setQuotUnit(Long quotUnit) {
        this.quotUnit = quotUnit;
    }

    /**
     * 取得 录入方式，接口、手工等
     * @return 录入方式，接口、手工等
     */
    public String getInputType() {
        return inputType;
    }

    /**
     * 设置 录入方式，接口、手工等
     * @param inputType 录入方式，接口、手工等
     */
    public void setInputType(String inputType) {
        this.inputType = inputType == null ? null : inputType.trim();
    }

    /**
     * 取得 行情类型
     * @return 行情类型
     */
    public String getMarketType() {
        return marketType;
    }

    /**
     * 设置 行情类型
     * @param marketType 行情类型
     */
    public void setMarketType(String marketType) {
        this.marketType = marketType == null ? null : marketType.trim();
    }

    /**
     * 取得 期限
     * @return 期限
     */
    public String getDeadLine() {
        return deadLine;
    }

    /**
     * 设置 期限
     * @param deadLine 期限
     */
    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine == null ? null : deadLine.trim();
    }
}