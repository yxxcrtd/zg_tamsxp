package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.math.BigDecimal;
import java.util.Date;

public class EtrCashPositionFlow extends BaseEntity {
    /**
     * 流水号
     */
    private String seqno;

    /**
     * 组合代码
     */
    private String portfolioId;

    /**
     * 账号
     */
    private String acctId;

    /**
     * 持仓类型，可用、实际、在途等
     */
    private String hodingType;

    /**
     * 日期
     */
    private Date cdate;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 数量
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal amount;

    /**
     * 业务编号
     */
    private String orderId;

    /**
     * 通道金融产品代码，是通道账户时存储
     */
    private String chlFinprodId;

    /**
     * 冲正标识
     */
    private String offsetFlag;

    /**
     * 冲销业务编号
     */
    private String offsetId;

    /**
     * 关闭时冲销标识，关闭：01，重新打开：02
     */
    private String closeFlag;

    /**
     * 备注
     */
    private String remark;
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
     * 取得 账号
     * @return 账号
     */
    public String getAcctId() {
        return acctId;
    }

    /**
     * 设置 账号
     * @param acctId 账号
     */
    public void setAcctId(String acctId) {
        this.acctId = acctId == null ? null : acctId.trim();
    }

    /**
     * 取得 持仓类型，可用、实际、在途等
     * @return 持仓类型，可用、实际、在途等
     */
    public String getHodingType() {
        return hodingType;
    }

    /**
     * 设置 持仓类型，可用、实际、在途等
     * @param hodingType 持仓类型，可用、实际、在途等
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
     * 取得 币种
     * @return 币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 设置 币种
     * @param ccy 币种
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
     * 取得 业务编号
     * @return 业务编号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置 业务编号
     * @param orderId 业务编号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 取得 通道金融产品代码，是通道账户时存储
     * @return 通道金融产品代码，是通道账户时存储
     */
    public String getChlFinprodId() {
        return chlFinprodId;
    }

    /**
     * 设置 通道金融产品代码，是通道账户时存储
     * @param chlFinprodId 通道金融产品代码，是通道账户时存储
     */
    public void setChlFinprodId(String chlFinprodId) {
        this.chlFinprodId = chlFinprodId == null ? null : chlFinprodId.trim();
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
     * 取得 备注
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
}