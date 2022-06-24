package com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import java.math.BigDecimal;

public class ChbMonthReportSubDetail extends BaseEntity {
    /**
     * null
     */
    private String detailuuid;

    /**
     * 期数
     */
    private String termid;

    /**
     * 报表类型：PROD-产品，ASSET-资产
     */
    private String reporttype;

    /**
     * 属性代码
     */
    private String propertycode;

    /**
     * 余额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal balance;

    /**
     * 加权价格/剩余期限
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal terms;

    /**
     * 符合《通知》余额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal balanceAcc;

    /**
     * 符合《通知》加权价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal termsAcc;

    /**
     * 不符合《通知》余额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal balanceInc;

    /**
     * 不符合《通知》加权价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal termsInc;

    /**
     * 母行余额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal balanceBan;

    /**
     * 母行只数
     */
    private Long prodcountsBan;

    /**
     * 母行加权价格/剩余期限
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal termsBan;

    /**
     * 子公司余额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal balanceSub;

    /**
     * 子公司只数
     */
    private Long prodcountsSub;

    /**
     * 子公司加权价格/剩余期限
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal termsSub;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 月报主表主键
     */
    private String reportuuid;

    /**
     * 取得 null
     * @return null
     */
    public String getDetailuuid() {
        return detailuuid;
    }

    /**
     * 设置 null
     * @param detailuuid null
     */
    public void setDetailuuid(String detailuuid) {
        this.detailuuid = detailuuid == null ? null : detailuuid.trim();
    }

    /**
     * 取得 期数
     * @return 期数
     */
    public String getTermid() {
        return termid;
    }

    /**
     * 设置 期数
     * @param termid 期数
     */
    public void setTermid(String termid) {
        this.termid = termid == null ? null : termid.trim();
    }

    /**
     * 取得 报表类型：PROD-产品，ASSET-资产
     * @return 报表类型：PROD-产品，ASSET-资产
     */
    public String getReporttype() {
        return reporttype;
    }

    /**
     * 设置 报表类型：PROD-产品，ASSET-资产
     * @param reporttype 报表类型：PROD-产品，ASSET-资产
     */
    public void setReporttype(String reporttype) {
        this.reporttype = reporttype == null ? null : reporttype.trim();
    }

    /**
     * 取得 属性代码
     * @return 属性代码
     */
    public String getPropertycode() {
        return propertycode;
    }

    /**
     * 设置 属性代码
     * @param propertycode 属性代码
     */
    public void setPropertycode(String propertycode) {
        this.propertycode = propertycode == null ? null : propertycode.trim();
    }

    /**
     * 取得 余额
     * @return 余额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置 余额
     * @param balance 余额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 取得 加权价格/剩余期限
     * @return 加权价格/剩余期限
     */
    public BigDecimal getTerms() {
        return terms;
    }

    /**
     * 设置 加权价格/剩余期限
     * @param terms 加权价格/剩余期限
     */
    public void setTerms(BigDecimal terms) {
        this.terms = terms;
    }

    /**
     * 取得 符合《通知》余额
     * @return 符合《通知》余额
     */
    public BigDecimal getBalanceAcc() {
        return balanceAcc;
    }

    /**
     * 设置 符合《通知》余额
     * @param balanceAcc 符合《通知》余额
     */
    public void setBalanceAcc(BigDecimal balanceAcc) {
        this.balanceAcc = balanceAcc;
    }

    /**
     * 取得 符合《通知》加权价格
     * @return 符合《通知》加权价格
     */
    public BigDecimal getTermsAcc() {
        return termsAcc;
    }

    /**
     * 设置 符合《通知》加权价格
     * @param termsAcc 符合《通知》加权价格
     */
    public void setTermsAcc(BigDecimal termsAcc) {
        this.termsAcc = termsAcc;
    }

    /**
     * 取得 不符合《通知》余额
     * @return 不符合《通知》余额
     */
    public BigDecimal getBalanceInc() {
        return balanceInc;
    }

    /**
     * 设置 不符合《通知》余额
     * @param balanceInc 不符合《通知》余额
     */
    public void setBalanceInc(BigDecimal balanceInc) {
        this.balanceInc = balanceInc;
    }

    /**
     * 取得 不符合《通知》加权价格
     * @return 不符合《通知》加权价格
     */
    public BigDecimal getTermsInc() {
        return termsInc;
    }

    /**
     * 设置 不符合《通知》加权价格
     * @param termsInc 不符合《通知》加权价格
     */
    public void setTermsInc(BigDecimal termsInc) {
        this.termsInc = termsInc;
    }

    /**
     * 取得 母行余额
     * @return 母行余额
     */
    public BigDecimal getBalanceBan() {
        return balanceBan;
    }

    /**
     * 设置 母行余额
     * @param balanceBan 母行余额
     */
    public void setBalanceBan(BigDecimal balanceBan) {
        this.balanceBan = balanceBan;
    }

    /**
     * 取得 母行只数
     * @return 母行只数
     */
    public Long getProdcountsBan() {
        return prodcountsBan;
    }

    /**
     * 设置 母行只数
     * @param prodcountsBan 母行只数
     */
    public void setProdcountsBan(Long prodcountsBan) {
        this.prodcountsBan = prodcountsBan;
    }

    /**
     * 取得 母行加权价格/剩余期限
     * @return 母行加权价格/剩余期限
     */
    public BigDecimal getTermsBan() {
        return termsBan;
    }

    /**
     * 设置 母行加权价格/剩余期限
     * @param termsBan 母行加权价格/剩余期限
     */
    public void setTermsBan(BigDecimal termsBan) {
        this.termsBan = termsBan;
    }

    /**
     * 取得 子公司余额
     * @return 子公司余额
     */
    public BigDecimal getBalanceSub() {
        return balanceSub;
    }

    /**
     * 设置 子公司余额
     * @param balanceSub 子公司余额
     */
    public void setBalanceSub(BigDecimal balanceSub) {
        this.balanceSub = balanceSub;
    }

    /**
     * 取得 子公司只数
     * @return 子公司只数
     */
    public Long getProdcountsSub() {
        return prodcountsSub;
    }

    /**
     * 设置 子公司只数
     * @param prodcountsSub 子公司只数
     */
    public void setProdcountsSub(Long prodcountsSub) {
        this.prodcountsSub = prodcountsSub;
    }

    /**
     * 取得 子公司加权价格/剩余期限
     * @return 子公司加权价格/剩余期限
     */
    public BigDecimal getTermsSub() {
        return termsSub;
    }

    /**
     * 设置 子公司加权价格/剩余期限
     * @param termsSub 子公司加权价格/剩余期限
     */
    public void setTermsSub(BigDecimal termsSub) {
        this.termsSub = termsSub;
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

    /**
     * 取得 部门代码
     * @return 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 取得 月报主表主键
     * @return 月报主表主键
     */
    public String getReportuuid() {
        return reportuuid;
    }

    /**
     * 设置 月报主表主键
     * @param reportuuid 月报主表主键
     */
    public void setReportuuid(String reportuuid) {
        this.reportuuid = reportuuid == null ? null : reportuuid.trim();
    }
}