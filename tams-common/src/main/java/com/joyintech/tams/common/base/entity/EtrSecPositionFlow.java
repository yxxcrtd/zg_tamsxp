package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.math.BigDecimal;
import java.util.Date;

public class EtrSecPositionFlow extends BaseEntity {
    /**
     * 流水号
     */
    private String seqno;

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * 证券管理账户/通道代码，无通道无证券管理账户的存999999
     */
    private String secAcctId;

    /**
     * 金融产品代码
     */
    private String finprodId;

    /**
     * 分支序号
     */
    private Long branch;

    /**
     * 投资目的
     */
    private String invAim;

    /**
     * 持仓类型，可用、实际、在途、质押、融入、融出
     */
    private String hodingType;

    /**
     * 日期
     */
    private Date cdate;

    /**
     * 币种，交易或者指令对应的币种
     */
    private String ccy;

    /**
     * 数量
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal amount;

    /**
     * 母金融产品代码
     */
    private String pFinprodId;

    /**
     * 交易编号
     */
    private String tradeId;

    /**
     * 关闭时冲销标识，关闭：01，重新打开：02
     */
    private String closeFlag;

    /**
     * 冲销业务编号
     */
    private String offsetId;

    /**
     * 冲正标识
     */
    private String offsetFlag;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;

    /**
     * 取得 流水号
     * @return 流水号
     */
    public String getSeqno() {
        return seqno;
    }

    /**
     * 设置 流水号
     * @param seqno 流水号
     */
    public void setSeqno(String seqno) {
        this.seqno = seqno == null ? null : seqno.trim();
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
     * 取得 证券管理账户/通道代码，无通道无证券管理账户的存999999
     * @return 证券管理账户/通道代码，无通道无证券管理账户的存999999
     */
    public String getSecAcctId() {
        return secAcctId;
    }

    /**
     * 设置 证券管理账户/通道代码，无通道无证券管理账户的存999999
     * @param secAcctId 证券管理账户/通道代码，无通道无证券管理账户的存999999
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
     * 取得 投资目的
     * @return 投资目的
     */
    public String getInvAim() {
        return invAim;
    }

    /**
     * 设置 投资目的
     * @param invAim 投资目的
     */
    public void setInvAim(String invAim) {
        this.invAim = invAim == null ? null : invAim.trim();
    }

    /**
     * 取得 持仓类型，可用、实际、在途、质押、融入、融出
     * @return 持仓类型，可用、实际、在途、质押、融入、融出
     */
    public String getHodingType() {
        return hodingType;
    }

    /**
     * 设置 持仓类型，可用、实际、在途、质押、融入、融出
     * @param hodingType 持仓类型，可用、实际、在途、质押、融入、融出
     */
    public void setHodingType(String hodingType) {
        this.hodingType = hodingType == null ? null : hodingType.trim();
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
     * 取得 币种，交易或者指令对应的币种
     * @return 币种，交易或者指令对应的币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 设置 币种，交易或者指令对应的币种
     * @param ccy 币种，交易或者指令对应的币种
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 取得 数量
     * @return 数量
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置 数量
     * @param amount 数量
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 取得 母金融产品代码
     * @return 母金融产品代码
     */
    public String getpFinprodId() {
        return pFinprodId;
    }

    /**
     * 设置 母金融产品代码
     * @param pFinprodId 母金融产品代码
     */
    public void setpFinprodId(String pFinprodId) {
        this.pFinprodId = pFinprodId == null ? null : pFinprodId.trim();
    }

    /**
     * 取得 交易编号
     * @return 交易编号
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * 设置 交易编号
     * @param tradeId 交易编号
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId == null ? null : tradeId.trim();
    }

    /**
     * 取得 关闭时冲销标识，关闭：01，重新打开：02
     * @return 关闭时冲销标识，关闭：01，重新打开：02
     */
    public String getCloseFlag() {
        return closeFlag;
    }

    /**
     * 设置 关闭时冲销标识，关闭：01，重新打开：02
     * @param closeFlag 关闭时冲销标识，关闭：01，重新打开：02
     */
    public void setCloseFlag(String closeFlag) {
        this.closeFlag = closeFlag == null ? null : closeFlag.trim();
    }

    /**
     * 取得 冲销业务编号
     * @return 冲销业务编号
     */
    public String getOffsetId() {
        return offsetId;
    }

    /**
     * 设置 冲销业务编号
     * @param offsetId 冲销业务编号
     */
    public void setOffsetId(String offsetId) {
        this.offsetId = offsetId == null ? null : offsetId.trim();
    }

    /**
     * 取得 冲正标识
     * @return 冲正标识
     */
    public String getOffsetFlag() {
        return offsetFlag;
    }

    /**
     * 设置 冲正标识
     * @param offsetFlag 冲正标识
     */
    public void setOffsetFlag(String offsetFlag) {
        this.offsetFlag = offsetFlag == null ? null : offsetFlag.trim();
    }

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
}