package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import java.math.BigDecimal;

public class EtrCashPosition extends EtrCashPositionKey {
    /**
     * 数量
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal amount;

    /**
     * 通道金融产品代码，是通道账户时存储
     */
    private String chlFinprodId;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;

    /**
     * 取得 数量
     * @return 数量
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置 数量
     * @param amount 数量
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 取得 通道金融产品代码，是通道账户时存储
     * @return 通道金融产品代码，是通道账户时存储
     */
    public String getChlFinprodId() {
        return chlFinprodId;
    }

    /**
     * 设置 通道金融产品代码，是通道账户时存储
     * @param chlFinprodId 通道金融产品代码，是通道账户时存储
     */
    public void setChlFinprodId(String chlFinprodId) {
        this.chlFinprodId = chlFinprodId == null ? null : chlFinprodId.trim();
    }

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
}