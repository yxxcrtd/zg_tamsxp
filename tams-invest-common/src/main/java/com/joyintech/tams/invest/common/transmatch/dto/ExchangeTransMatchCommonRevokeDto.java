package com.joyintech.tams.invest.common.transmatch.dto;

import com.joyintech.tams.common.base.entity.SettleTransTgMapping;

public class ExchangeTransMatchCommonRevokeDto extends SettleTransTgMapping {

    /**
     * 是否汇总交易
     */
    private String isTotalDeal;

    /**
     * 金融产品类型投管用
     */
    private String finprodType2;

    /**
     * 交易方向
     */
    private String ps;

    /**
     * 金融产品交易表ID，如果不是汇总交易，等于tradeIdTg，如果是汇总交易，等于tradeIdTg对应的明细交易tradeId
     */
    private String tradeIdTgDetail;

    /**
     * 汇总成交流水号
     */
    private String seqTotalId;

    /**
     * 组合代码
     */
    private String portfolioId;

    public String getIsTotalDeal() {
        return isTotalDeal;
    }

    public void setIsTotalDeal(String isTotalDeal) {
        this.isTotalDeal = isTotalDeal;
    }

    public String getFinprodType2() {
        return finprodType2;
    }

    public void setFinprodType2(String finprodType2) {
        this.finprodType2 = finprodType2;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getTradeIdTgDetail() {
        return tradeIdTgDetail;
    }

    public void setTradeIdTgDetail(String tradeIdTgDetail) {
        this.tradeIdTgDetail = tradeIdTgDetail;
    }

    public String getSeqTotalId() {
        return seqTotalId;
    }

    public void setSeqTotalId(String seqTotalId) {
        this.seqTotalId = seqTotalId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }
}
