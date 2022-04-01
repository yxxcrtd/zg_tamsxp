package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

public class SettleTransDataTotalPledgeKey extends BaseEntity {
    /**
     * 流水号
     */
    private String tradeId;

    /**
     * 分支序号
     */
    private Long branch;

    /**
     * 质押金融产品代码
     */
    private String pledgeFinprodId;

    /**
     * 取得 流水号
     * @return 流水号
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * 设置 流水号
     * @param tradeId 流水号
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    /**
     * 取得 分支序号
     * @return 分支序号
     */
    public Long getBranch() {
        return branch;
    }

    /**
     * 设置 分支序号
     * @param branch 分支序号
     */
    public void setBranch(Long branch) {
        this.branch = branch;
    }

    /**
     * 取得 质押金融产品代码
     * @return 质押金融产品代码
     */
    public String getPledgeFinprodId() {
        return pledgeFinprodId;
    }

    /**
     * 设置 质押金融产品代码
     * @param pledgeFinprodId 质押金融产品代码
     */
    public void setPledgeFinprodId(String pledgeFinprodId) {
        this.pledgeFinprodId = pledgeFinprodId == null ? null : pledgeFinprodId.trim();
    }
}