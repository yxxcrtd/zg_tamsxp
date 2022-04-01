package com.joyintech.tams.invest.common.dto.position;

import java.math.BigDecimal;

/**
 * 组合数据集
 */
public class PositionQueryAmountDto {

    private String portfolioId; // 组合代码

    private String acctId ; // 资金账号

    private String secAcctId  ; // 证券管理账户/通道代码 查询资产时必传

    private String cdate  ; // 日期

    private String finprodId ; // 查询资产时必传

    private String dateType; // 处理速度
    
    private String flowType; // etr 指令 trd 交易

    private String ps; //查询资金时必传

    private Integer amount; //金额


    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
