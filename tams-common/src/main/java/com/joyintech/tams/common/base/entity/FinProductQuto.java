package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.math.BigDecimal;
import java.util.Date;

public class FinProductQuto extends BaseEntity {
    /**
     * 流水号
     */
    private String seqNo;

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * 证券账户代码
     */
    private String secAcctId;

    /**
     * 金融产品代码
     */
    private String finprodId;

    /**
     * 业务类型
     */
    private String othType;

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    /**
     * 交易方向
     */
    private String ps;

    /**
     * 日期
     */
    private Date cdate;

    /**
     * 额度
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal quto;

    /**
     * 科创板额度
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal ipoQuto;

    /**
     * 股东代码
     */
    private String bondId;

    /**
     * 交易场所
     */
    private String tradeMarket;

    /**
     * 取得 流水号
     * @return 流水号
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * 设置 流水号
     * @param seqNo 流水号
     */
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo == null ? null : seqNo.trim();
    }

    /**
     * 取得 组合代码
     * @return 组合代码
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * 设置 组合代码
     * @param portfolioId 组合代码
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId == null ? null : portfolioId.trim();
    }

    /**
     * 取得 证券账户代码
     * @return 证券账户代码
     */
    public String getSecAcctId() {
        return secAcctId;
    }

    /**
     * 设置 证券账户代码
     * @param secAcctId 证券账户代码
     */
    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId == null ? null : secAcctId.trim();
    }

    /**
     * 取得 金融产品代码
     * @return 金融产品代码
     */
    public String getFinprodId() {
        return finprodId;
    }

    /**
     * 设置 金融产品代码
     * @param finprodId 金融产品代码
     */
    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId == null ? null : finprodId.trim();
    }

    /**
     * 取得 业务类型
     * @return 业务类型
     */
    public String getOthType() {
        return othType;
    }

    /**
     * 设置 业务类型
     * @param othType 业务类型
     */
    public void setOthType(String othType) {
        this.othType = othType == null ? null : othType.trim();
    }

    /**
     * 取得 日期
     * @return 日期
     */
    public Date getCdate() {
        return cdate;
    }

    /**
     * 设置 日期
     * @param cdate 日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    /**
     * 取得 额度
     * @return 额度
     */
    public BigDecimal getQuto() {
        return quto;
    }

    /**
     * 设置 额度
     * @param quto 额度
     */
    public void setQuto(BigDecimal quto) {
        this.quto = quto;
    }

    /**
     * 取得 科创板额度
     * @return 科创板额度
     */
    public BigDecimal getIpoQuto() {
        return ipoQuto;
    }

    /**
     * 设置 科创板额度
     * @param ipoQuto 科创板额度
     */
    public void setIpoQuto(BigDecimal ipoQuto) {
        this.ipoQuto = ipoQuto;
    }

    /**
     * 取得 股东代码
     * @return 股东代码
     */
    public String getBondId() {
        return bondId;
    }

    /**
     * 设置 股东代码
     * @param bondId 股东代码
     */
    public void setBondId(String bondId) {
        this.bondId = bondId == null ? null : bondId.trim();
    }

    /**
     * 取得 交易场所
     * @return 交易场所
     */
    public String getTradeMarket() {
        return tradeMarket;
    }

    /**
     * 设置 交易场所
     * @param tradeMarket 交易场所
     */
    public void setTradeMarket(String tradeMarket) {
        this.tradeMarket = tradeMarket == null ? null : tradeMarket.trim();
    }


}