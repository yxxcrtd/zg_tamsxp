package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.math.BigDecimal;
import java.util.Date;

public class ChbSalerStlDealInfo extends ChbSalerStlDealInfoKey {
    /**
     * 主键uuid 
     */
    private String registSeqno;

    /**
     * 数据状态 
     */
    private String dataStatus;

    /**
     * 产品登记编码 
     */
    private String prdRegistCode;

    /**
     * 销售结算资金账号 
     */
    private String accountNo;

    /**
     * 销售结算资金账户名称 
     */
    private String accountName;

    /**
     * 销售结算资金流动方向 
     */
    private String tradePs;

    /**
     * 销售结算资金交易对手方账号 
     */
    private String tradeCounterAccNo;

    /**
     * 销售结算资金交易对手方账户名称 
     */
    private String tradeCounterAccName;

    /**
     * 销售结算资金交易对手方开户行名称 
     */
    private String tradeCounterAccBankName;

    /**
     * 销售结算资金交易对手方账户开户行号 
     */
    private String tradeCounterAccBankNo;

    /**
     * 销售结算资金交易币种 
     */
    private String tradeCcy;

    /**
     * 销售结算资金交易金额 
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal tradeAmt;

    /**
     * 销售结算资金交易日期 
     */
    private Date tradeVdate;

    /**
     * 销售结算资金交易时间 
     */
    private String tradeTime;

    /**
     * 销售结算资金交易附言 
     */
    private String postscript;

    /**
     * 备注 
     */
    private String remark;

    /**
     * 数据接入日期 
     */
    private Date receptDate;

    /**
     * 机构代码 
     */
    private String orgCode;

    /**
     * 销售结算交易类型
     */
    private String tradeType;

    /**
     * 取得 主键uuid 
     * @return 主键uuid 
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 设置 主键uuid 
     * @param registSeqno 主键uuid 
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 取得 数据状态 
     * @return 数据状态 
     */
    public String getDataStatus() {
        return dataStatus;
    }

    /**
     * 设置 数据状态 
     * @param dataStatus 数据状态 
     */
    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus == null ? null : dataStatus.trim();
    }

    /**
     * 取得 产品登记编码 
     * @return 产品登记编码 
     */
    public String getPrdRegistCode() {
        return prdRegistCode;
    }

    /**
     * 设置 产品登记编码 
     * @param prdRegistCode 产品登记编码 
     */
    public void setPrdRegistCode(String prdRegistCode) {
        this.prdRegistCode = prdRegistCode == null ? null : prdRegistCode.trim();
    }

    /**
     * 取得 销售结算资金账号 
     * @return 销售结算资金账号 
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 设置 销售结算资金账号 
     * @param accountNo 销售结算资金账号 
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    /**
     * 取得 销售结算资金账户名称 
     * @return 销售结算资金账户名称 
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置 销售结算资金账户名称 
     * @param accountName 销售结算资金账户名称 
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    /**
     * 取得 销售结算资金流动方向 
     * @return 销售结算资金流动方向 
     */
    public String getTradePs() {
        return tradePs;
    }

    /**
     * 设置 销售结算资金流动方向 
     * @param tradePs 销售结算资金流动方向 
     */
    public void setTradePs(String tradePs) {
        this.tradePs = tradePs == null ? null : tradePs.trim();
    }

    /**
     * 取得 销售结算资金交易对手方账号 
     * @return 销售结算资金交易对手方账号 
     */
    public String getTradeCounterAccNo() {
        return tradeCounterAccNo;
    }

    /**
     * 设置 销售结算资金交易对手方账号 
     * @param tradeCounterAccNo 销售结算资金交易对手方账号 
     */
    public void setTradeCounterAccNo(String tradeCounterAccNo) {
        this.tradeCounterAccNo = tradeCounterAccNo == null ? null : tradeCounterAccNo.trim();
    }

    /**
     * 取得 销售结算资金交易对手方账户名称 
     * @return 销售结算资金交易对手方账户名称 
     */
    public String getTradeCounterAccName() {
        return tradeCounterAccName;
    }

    /**
     * 设置 销售结算资金交易对手方账户名称 
     * @param tradeCounterAccName 销售结算资金交易对手方账户名称 
     */
    public void setTradeCounterAccName(String tradeCounterAccName) {
        this.tradeCounterAccName = tradeCounterAccName == null ? null : tradeCounterAccName.trim();
    }

    /**
     * 取得 销售结算资金交易对手方开户行名称 
     * @return 销售结算资金交易对手方开户行名称 
     */
    public String getTradeCounterAccBankName() {
        return tradeCounterAccBankName;
    }

    /**
     * 设置 销售结算资金交易对手方开户行名称 
     * @param tradeCounterAccBankName 销售结算资金交易对手方开户行名称 
     */
    public void setTradeCounterAccBankName(String tradeCounterAccBankName) {
        this.tradeCounterAccBankName = tradeCounterAccBankName == null ? null : tradeCounterAccBankName.trim();
    }

    /**
     * 取得 销售结算资金交易对手方账户开户行号 
     * @return 销售结算资金交易对手方账户开户行号 
     */
    public String getTradeCounterAccBankNo() {
        return tradeCounterAccBankNo;
    }

    /**
     * 设置 销售结算资金交易对手方账户开户行号 
     * @param tradeCounterAccBankNo 销售结算资金交易对手方账户开户行号 
     */
    public void setTradeCounterAccBankNo(String tradeCounterAccBankNo) {
        this.tradeCounterAccBankNo = tradeCounterAccBankNo == null ? null : tradeCounterAccBankNo.trim();
    }

    /**
     * 取得 销售结算资金交易币种 
     * @return 销售结算资金交易币种 
     */
    public String getTradeCcy() {
        return tradeCcy;
    }

    /**
     * 设置 销售结算资金交易币种 
     * @param tradeCcy 销售结算资金交易币种 
     */
    public void setTradeCcy(String tradeCcy) {
        this.tradeCcy = tradeCcy == null ? null : tradeCcy.trim();
    }

    /**
     * 取得 销售结算资金交易金额 
     * @return 销售结算资金交易金额 
     */
    public BigDecimal getTradeAmt() {
        return tradeAmt;
    }

    /**
     * 设置 销售结算资金交易金额 
     * @param tradeAmt 销售结算资金交易金额 
     */
    public void setTradeAmt(BigDecimal tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    /**
     * 取得 销售结算资金交易日期 
     * @return 销售结算资金交易日期 
     */
    public Date getTradeVdate() {
        return tradeVdate;
    }

    /**
     * 设置 销售结算资金交易日期 
     * @param tradeVdate 销售结算资金交易日期 
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setTradeVdate(Date tradeVdate) {
        this.tradeVdate = tradeVdate;
    }

    /**
     * 取得 销售结算资金交易时间 
     * @return 销售结算资金交易时间 
     */
    public String getTradeTime() {
        return tradeTime;
    }

    /**
     * 设置 销售结算资金交易时间 
     * @param tradeTime 销售结算资金交易时间 
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * 取得 销售结算资金交易附言 
     * @return 销售结算资金交易附言 
     */
    public String getPostscript() {
        return postscript;
    }

    /**
     * 设置 销售结算资金交易附言 
     * @param postscript 销售结算资金交易附言 
     */
    public void setPostscript(String postscript) {
        this.postscript = postscript == null ? null : postscript.trim();
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

    /**
     * 取得 数据接入日期 
     * @return 数据接入日期 
     */
    public Date getReceptDate() {
        return receptDate;
    }

    /**
     * 设置 数据接入日期 
     * @param receptDate 数据接入日期 
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setReceptDate(Date receptDate) {
        this.receptDate = receptDate;
    }

    /**
     * 取得 机构代码 
     * @return 机构代码 
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 机构代码 
     * @param orgCode 机构代码 
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}