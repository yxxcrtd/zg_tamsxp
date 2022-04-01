package com.joyintech.tams.exchange.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "组合信息", description = "组合信息")
public class PortfolioInfoResponse implements Serializable {

    private static final long serialVersionUID = 5892155368791512437L;

    @ApiModelProperty("组合代码")
    private String portfolioId;

    @ApiModelProperty("组合T日可用持仓")
    private BigDecimal proAvailablePositionT0;

    @ApiModelProperty("组合T1日可用持仓")
    private BigDecimal proAvailablePositionT1;

    @ApiModelProperty("组合T日可用现金")
    private BigDecimal proAvailableCashT0;

    @ApiModelProperty("组合T1日可用现金")
    private BigDecimal proAvailableCashT1;

    @ApiModelProperty("托管户T0日可用现金")
    private BigDecimal secManageAvailableCashT0;

    @ApiModelProperty("托管户T1日可用现金")
    private BigDecimal secManageAvailableCashT1;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public BigDecimal getProAvailablePositionT0() {
        return proAvailablePositionT0;
    }

    public void setProAvailablePositionT0(BigDecimal proAvailablePositionT0) {
        this.proAvailablePositionT0 = proAvailablePositionT0;
    }

    public BigDecimal getProAvailablePositionT1() {
        return proAvailablePositionT1;
    }

    public void setProAvailablePositionT1(BigDecimal proAvailablePositionT1) {
        this.proAvailablePositionT1 = proAvailablePositionT1;
    }

    public BigDecimal getProAvailableCashT0() {
        return proAvailableCashT0;
    }

    public void setProAvailableCashT0(BigDecimal proAvailableCashT0) {
        this.proAvailableCashT0 = proAvailableCashT0;
    }

    public BigDecimal getProAvailableCashT1() {
        return proAvailableCashT1;
    }

    public void setProAvailableCashT1(BigDecimal proAvailableCashT1) {
        this.proAvailableCashT1 = proAvailableCashT1;
    }

    public BigDecimal getSecManageAvailableCashT0() {
        return secManageAvailableCashT0;
    }

    public void setSecManageAvailableCashT0(BigDecimal secManageAvailableCashT0) {
        this.secManageAvailableCashT0 = secManageAvailableCashT0;
    }

    public BigDecimal getSecManageAvailableCashT1() {
        return secManageAvailableCashT1;
    }

    public void setSecManageAvailableCashT1(BigDecimal secManageAvailableCashT1) {
        this.secManageAvailableCashT1 = secManageAvailableCashT1;
    }

    @Override
    public String toString() {
        return "PortfolioInfoResponse{" +
                "portfolioId='" + portfolioId + '\'' +
                ", proAvailablePositionT0=" + proAvailablePositionT0 +
                ", proAvailablePositionT1=" + proAvailablePositionT1 +
                ", proAvailableCashT0=" + proAvailableCashT0 +
                ", proAvailableCashT1=" + proAvailableCashT1 +
                ", secManageAvailableCashT0=" + secManageAvailableCashT0 +
                ", secManageAvailableCashT1=" + secManageAvailableCashT1 +
                '}';
    }
}
