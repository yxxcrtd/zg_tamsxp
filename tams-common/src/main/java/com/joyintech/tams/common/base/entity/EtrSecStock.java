package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

public class EtrSecStock extends BaseEntity {
    /**
     * 质押库代码
     */
    private String pledgeStockId;

    /**
     * 质押库名称
     */
    private String pledgeStockName;

    /**
     * 是否默认质押券库
     */
    private String isDefStock;

    /**
     * 取得 质押库代码
     * @return 质押库代码
     */
    public String getPledgeStockId() {
        return pledgeStockId;
    }

    /**
     * 设置 质押库代码
     * @param pledgeStockId 质押库代码
     */
    public void setPledgeStockId(String pledgeStockId) {
        this.pledgeStockId = pledgeStockId == null ? null : pledgeStockId.trim();
    }

    /**
     * 取得 质押库名称
     * @return 质押库名称
     */
    public String getPledgeStockName() {
        return pledgeStockName;
    }

    /**
     * 设置 质押库名称
     * @param pledgeStockName 质押库名称
     */
    public void setPledgeStockName(String pledgeStockName) {
        this.pledgeStockName = pledgeStockName == null ? null : pledgeStockName.trim();
    }

    public String getIsDefStock() {
        return isDefStock;
    }

    public void setIsDefStock(String isDefStock) {
        this.isDefStock = isDefStock;
    }
}