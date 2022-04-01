package com.joyintech.tams.svreport.chb.monthcollect.dto;

import com.joyintech.framework.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class ChbMonthCollect extends BaseEntity {
    //识别标识
    private String indentifyMark;
    //折算人民币金额
    private BigDecimal cnyAmt;
    //币种
    private String ccy;
    //机构代码
    private String orgCode;
   //主键
    private String detailuuid;
    //关联report的外键
    private String reportuuid;
    //报表类型：1_1分投资者类型,1_2分地区类型,1_3分销售渠道
    private String reporttype;
    //产品登记编码
    private String prdRegistCode;
    //是否代销
    private String isConsign;
    //销售机构代码
    private String institutionCode;
    //地区类型
    private String areaCode;
    //投资者类型
    private String holderType;
    //投资者数量
    private BigDecimal holderNum;
    //总销售金额
    private BigDecimal saleAmountTotal;
    //净销售金额
    private BigDecimal valueAmountTotal;
    //单持有余额
    private BigDecimal amount;
    //持有余额
    private BigDecimal balanceAmt;
    //持有份额
    private BigDecimal balanceShares;
    //总销售份额
    private BigDecimal saleSharesTotal;
    //净销售份额
    private BigDecimal valueSharesTotal;
    //产品代码
    private String prodid;
    //本期总募集金额总值
    private BigDecimal dirtyamtsum;
    //本期净募集金额总值
    private BigDecimal cleanamtsum;
    //期末余额总值
    private BigDecimal endamtsumOth;
    //期数
    private String termid;
    //报表名称
    private String reportName;
    //开始日期
    private Date startdate;
    //结束日期
    private Date enddate;
    //发行机构代码
    private String issuePartyId;
    //发行机构名称
    private String issuePartyName;
    //报表频率：D-日，WE-周报，M-月报，S-季，Y-年
    private String reportfrequ;
    //制表日期
    private Date reportDate;
    //报送日期
    private Date submitTime;
    //中债报送截止日
    private Date chbSubmitDeadline;
    //行内报送截止日
    private Date submitDeadline;
    //反馈状态
    private String submitFeedbackStatus;
    //数据状态
    private String dataStatus;
    //报送状态
    private String sendStatus;
    //数据日期
    private Date statusDate;
    //持有日期
    private Date holdDate;

    private BigDecimal qty;

    public String getTermid() {
        return termid;
    }

    public void setTermid(String termid) {
        this.termid = termid;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getIssuePartyId() {
        return issuePartyId;
    }

    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId;
    }

    public String getIssuePartyName() {
        return issuePartyName;
    }

    public void setIssuePartyName(String issuePartyName) {
        this.issuePartyName = issuePartyName;
    }

    public String getReportfrequ() {
        return reportfrequ;
    }

    public void setReportfrequ(String reportfrequ) {
        this.reportfrequ = reportfrequ;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getHoldDate() {
        return holdDate;
    }

    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    public String getHolderTypeFlag() {
        return this.getPrdRegistCode() + ":" + this.getHolderType();
    }

    public String getAreaCodeFlag() {
        return this.getPrdRegistCode() + ":" + this.getAreaCode();
    }

    public String getInstitutionCodeFlag() {
        return this.getPrdRegistCode() + ":" + this.getInstitutionCode();
    }

    public String getProdid() {
        return prodid;
    }

    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

    public BigDecimal getDirtyamtsum() {
        return dirtyamtsum;
    }

    public void setDirtyamtsum(BigDecimal dirtyamtsum) {
        this.dirtyamtsum = dirtyamtsum;
    }

    public BigDecimal getCleanamtsum() {
        return cleanamtsum;
    }

    public void setCleanamtsum(BigDecimal cleanamtsum) {
        this.cleanamtsum = cleanamtsum;
    }

    public BigDecimal getEndamtsumOth() {
        return endamtsumOth;
    }

    public void setEndamtsumOth(BigDecimal endamtsumOth) {
        this.endamtsumOth = endamtsumOth;
    }

    public String getIndentifyMark() {
        return indentifyMark;
    }

    public void setIndentifyMark(String indentifyMark) {
        this.indentifyMark = indentifyMark;
    }

    public BigDecimal getCnyAmt() {
        return cnyAmt;
    }

    public void setCnyAmt(BigDecimal cnyAmt) {
        this.cnyAmt = cnyAmt;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getDetailuuid() {
        return detailuuid;
    }

    public void setDetailuuid(String detailuuid) {
        this.detailuuid = detailuuid;
    }

    public String getReportuuid() {
        return reportuuid;
    }

    public void setReportuuid(String reportuuid) {
        this.reportuuid = reportuuid;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public String getPrdRegistCode() {
        return prdRegistCode;
    }

    public void setPrdRegistCode(String prdRegistCode) {
        this.prdRegistCode = prdRegistCode;
    }

    public String getIsConsign() {
        return isConsign;
    }

    public void setIsConsign(String isConsign) {
        this.isConsign = isConsign;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getHolderType() {
        return holderType;
    }

    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }

    public BigDecimal getHolderNum() {
        return holderNum;
    }

    public void setHolderNum(BigDecimal holderNum) {
        this.holderNum = holderNum;
    }

    public BigDecimal getSaleAmountTotal() {
        return saleAmountTotal;
    }

    public void setSaleAmountTotal(BigDecimal saleAmountTotal) {
        this.saleAmountTotal = saleAmountTotal;
    }

    public BigDecimal getValueAmountTotal() {
        return valueAmountTotal;
    }

    public void setValueAmountTotal(BigDecimal valueAmountTotal) {
        this.valueAmountTotal = valueAmountTotal;
    }

    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public BigDecimal getBalanceShares() {
        return balanceShares;
    }

    public void setBalanceShares(BigDecimal balanceShares) {
        this.balanceShares = balanceShares;
    }

    public BigDecimal getSaleSharesTotal() {
        return saleSharesTotal;
    }

    public void setSaleSharesTotal(BigDecimal saleSharesTotal) {
        this.saleSharesTotal = saleSharesTotal;
    }

    public BigDecimal getValueSharesTotal() {
        return valueSharesTotal;
    }

    public void setValueSharesTotal(BigDecimal valueSharesTotal) {
        this.valueSharesTotal = valueSharesTotal;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}