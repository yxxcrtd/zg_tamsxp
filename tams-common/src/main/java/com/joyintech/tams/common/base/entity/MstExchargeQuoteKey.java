package com.joyintech.tams.common.base.entity;

import java.util.Date;

public class MstExchargeQuoteKey extends BaseChbEntity {
    /**
     * 行情代码，一般为货币对
     */
    private String exchargeCode;

    /**
     * 交易日
     */
    private Date tradeDate;

    /**
     * 数据来源
     */
    private String datasourceType;

    /**
     * 取得 行情代码，一般为货币对
     * @return 行情代码，一般为货币对
     */
    public String getExchargeCode() {
        return exchargeCode;
    }

    /**
     * 设置 行情代码，一般为货币对
     * @param exchargeCode 行情代码，一般为货币对
     */
    public void setExchargeCode(String exchargeCode) {
        this.exchargeCode = exchargeCode == null ? null : exchargeCode.trim();
    }

    /**
     * 取得 交易日
     * @return 交易日
     */
    public Date getTradeDate() {
        return tradeDate;
    }

    /**
     * 设置 交易日
     * @param tradeDate 交易日
     */
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * 取得 数据来源
     * @return 数据来源
     */
    public String getDatasourceType() {
        return datasourceType;
    }

    /**
     * 设置 数据来源
     * @param datasourceType 数据来源
     */
    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType == null ? null : datasourceType.trim();
    }
}