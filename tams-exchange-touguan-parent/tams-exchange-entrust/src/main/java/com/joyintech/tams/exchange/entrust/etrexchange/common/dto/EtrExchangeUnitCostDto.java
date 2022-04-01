package com.joyintech.tams.exchange.entrust.etrexchange.common.dto;

import java.math.BigDecimal;

/**
 * 单位成本价dto
 */
public class EtrExchangeUnitCostDto {

    public EtrExchangeUnitCostDto(){

    }

    public EtrExchangeUnitCostDto(BigDecimal unitCost, BigDecimal buyCostAmt, BigDecimal shareAmt){
        this.unitCost=unitCost;
        this.buyCostAmt=buyCostAmt;
        this.shareAmt=shareAmt;
    }
    
    /**
     * 单位成本
     */
    private BigDecimal unitCost;
    /**
     * 成本
     */
    private BigDecimal buyCostAmt;
    /**
     * 数量
     */
    private BigDecimal shareAmt;

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getBuyCostAmt() {
        return buyCostAmt;
    }

    public void setBuyCostAmt(BigDecimal buyCostAmt) {
        this.buyCostAmt = buyCostAmt;
    }

    public BigDecimal getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(BigDecimal shareAmt) {
        this.shareAmt = shareAmt;
    }
}
