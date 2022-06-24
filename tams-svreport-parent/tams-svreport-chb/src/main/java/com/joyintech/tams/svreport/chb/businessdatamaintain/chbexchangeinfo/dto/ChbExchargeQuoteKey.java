package com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dto;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.BaseChbEntity;
import com.joyintech.workflow.engine.annotation.PrimaryKey;

import java.util.Date;

public class ChbExchargeQuoteKey extends BaseChbEntity {
    /**
     * 行情代码，一般为货币对
     */
    @PrimaryKey(PrimaryKey.Field.PK_1)
    @NotEmpty(fieldName = "MstExchangeQuotationInfo001.exchargeCode", groups = {ValidateGroupEdit.class})
    private String exchargeCode;

    /**
     * 交易日
     */

    @NotEmpty(fieldName = "MstExchangeQuotationInfo001.tradeDate", groups = {ValidateGroupEdit.class})
    private Date tradeDate;

    /**
     * 数据来源
     */
    @PrimaryKey(PrimaryKey.Field.PK_3)
    @NotEmpty(fieldName = "MstExchangeQuotationInfo001.datasourceType", groups = {ValidateGroupEdit.class})
    private String datasourceType;

    /**
     * 取得 行情代码，一般为货币对
     * @return 行情代码，一般为货币对
     */
    public String getExchargeCode() {
        return exchargeCode;
    }

    @PrimaryKey(PrimaryKey.Field.PK_2)
    public String getTradeDateStr() {
        return DateUtil.format(getTradeDate());
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