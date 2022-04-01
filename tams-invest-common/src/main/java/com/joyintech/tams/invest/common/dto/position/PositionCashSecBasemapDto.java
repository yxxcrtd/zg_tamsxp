package com.joyintech.tams.invest.common.dto.position;

import com.joyintech.tams.business.common.pojo.BaseMap;

import java.util.List;

/**
 * 组合数据集
 */
public class PositionCashSecBasemapDto {

    private String portfolioId; // 组合代码

    private String acctId ; // 资金账号

    private String secAcctId  ; // 证券管理账户/通道代码 查询持仓时必传

    private String cdate  ; // 日期

    private String finprodId ; // 查询持仓时必传

    private String dateType; // 处理速度
    
    private String ps; //查询资金时必传

    private String tradeMarket; //交易场所

    private String ccy; //币种

    private  List<BaseMap> list;
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


    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(String tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public List<BaseMap> getList() {
        return list;
    }

    public void setList(List<BaseMap> list) {
        this.list = list;
    }
}
