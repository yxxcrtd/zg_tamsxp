package com.joyintech.tams.invest.common.dto.position;

import java.math.BigDecimal;

/**
 * 组合数据集
 */
public class PositionCashSecData {

    private BigDecimal cashT; // 可用

    private BigDecimal realCash ;  // 实际现金（账户现金）


    public BigDecimal getCashT() {
        return cashT;
    }

    public void setCashT(BigDecimal cashT) {
        this.cashT = cashT;
    }

    public BigDecimal getRealCash() {
        return realCash;
    }

    public void setRealCash(BigDecimal realCash) {
        this.realCash = realCash;
    }
}
