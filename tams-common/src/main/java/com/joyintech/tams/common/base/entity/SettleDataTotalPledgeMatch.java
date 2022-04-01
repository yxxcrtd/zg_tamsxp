package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import java.math.BigDecimal;

public class SettleDataTotalPledgeMatch extends SettleDataTotalPledgeMatchKey {
    /**
     * 目标类型
     */
    private String matchType;

    /**
     * 已匹配份额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal marchQty;

    /**
     * 已匹配金额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal marchAmt;

    /**
     * 指令已匹配份额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal marchQtyEn;

    /**
     * 指令已匹配金额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal marchAmtEn;

    /**
     * 置换方向，中途换券、解除质押时登录
     */
    private String displaceDirection;

    /**
     * 取得 目标类型
     * @return 目标类型
     */
    public String getMatchType() {
        return matchType;
    }

    /**
     * 设置 目标类型
     * @param matchType 目标类型
     */
    public void setMatchType(String matchType) {
        this.matchType = matchType == null ? null : matchType.trim();
    }

    /**
     * 取得 已匹配份额
     * @return 已匹配份额
     */
    public BigDecimal getMarchQty() {
        return marchQty;
    }

    /**
     * 设置 已匹配份额
     * @param marchQty 已匹配份额
     */
    public void setMarchQty(BigDecimal marchQty) {
        this.marchQty = marchQty;
    }

    /**
     * 取得 已匹配金额
     * @return 已匹配金额
     */
    public BigDecimal getMarchAmt() {
        return marchAmt;
    }

    /**
     * 设置 已匹配金额
     * @param marchAmt 已匹配金额
     */
    public void setMarchAmt(BigDecimal marchAmt) {
        this.marchAmt = marchAmt;
    }

    /**
     * 取得 指令已匹配份额
     * @return 指令已匹配份额
     */
    public BigDecimal getMarchQtyEn() {
        return marchQtyEn;
    }

    /**
     * 设置 指令已匹配份额
     * @param marchQtyEn 指令已匹配份额
     */
    public void setMarchQtyEn(BigDecimal marchQtyEn) {
        this.marchQtyEn = marchQtyEn;
    }

    /**
     * 取得 指令已匹配金额
     * @return 指令已匹配金额
     */
    public BigDecimal getMarchAmtEn() {
        return marchAmtEn;
    }

    /**
     * 设置 指令已匹配金额
     * @param marchAmtEn 指令已匹配金额
     */
    public void setMarchAmtEn(BigDecimal marchAmtEn) {
        this.marchAmtEn = marchAmtEn;
    }

    /**
     * 取得 置换方向，中途换券、解除质押时登录
     * @return 置换方向，中途换券、解除质押时登录
     */
    public String getDisplaceDirection() {
        return displaceDirection;
    }

    /**
     * 设置 置换方向，中途换券、解除质押时登录
     * @param displaceDirection 置换方向，中途换券、解除质押时登录
     */
    public void setDisplaceDirection(String displaceDirection) {
        this.displaceDirection = displaceDirection == null ? null : displaceDirection.trim();
    }
}