package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;

import java.math.BigDecimal;
import java.util.Date;

public class ChbAstPosSub extends BaseEntity {
    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 产品登记编码
     */
    private String prdRegistCode;

    /**
     * 期数
     */
    private String termid;

    /**
     * 持仓日期
     */
    private Date valueDate;

    /**
     * 行内资产/负债编码
     */
    private String assetId;

    /**
     * 持仓类别
     */
    private String posType;

    /**
     * 中间层数
     */
    private String interLeveNo;

    /**
     * 资产穿透情况
     */
    private String interAssetType;

    /**
     * 中间层行内资产/负债编码
     */
    private String interAssetCode;

    /**
     * 会计科目名称
     */
    private String subjectName;

    /**
     * 金额
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal amt;

    /**
     * 折算人民币金额
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal cnyAmt;

    /**
     * 公允价值
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal floatIngpl;

    /**
     * 折算人民币公允价值
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal cnyFloatIngpl;

    /**
     * 单位估值（净价）
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal unitValueCprice;

    /**
     * 单位估值（全价）
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal unitValueFprice;

    /**
     * 数量
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal qty;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 余额
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal balanceAmt;

    /**
     * 持仓登记日期
     */
    private Date valueRegDate;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 反馈状态
     */
    private String submitFeedbackStatus;

    /**
     *错误信息
     */
    private   String  errMsg;

    /**
     * 报送流水号
     */
    private  String  submitSeq;

    /**
     * 报送反馈消息
     */
    private  String submitFeedbackMsg;


    public String getSubmitSeq() {
        return submitSeq;
    }

    public void setSubmitSeq(String submitSeq) {
        this.submitSeq = submitSeq;
    }

    public String getSubmitFeedbackMsg() {
        return submitFeedbackMsg;
    }

    public void setSubmitFeedbackMsg(String submitFeedbackMsg) {
        this.submitFeedbackMsg = submitFeedbackMsg;
    }

    /**
     * 取得 登记序列
     * @return 登记序列
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 设置 登记序列
     * @param registSeqno 登记序列
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 取得 产品代码
     * @return 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 设置 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 取得 发行机构代码
     * @return 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 设置 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
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
     * 取得 持仓日期
     * @return 持仓日期
     */
    public Date getValueDate() {
        return valueDate;
    }

    /**
     * 设置 持仓日期
     * @param valueDate 持仓日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    /**
     * 取得 行内资产/负债编码
     * @return 行内资产/负债编码
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * 设置 行内资产/负债编码
     * @param assetId 行内资产/负债编码
     */
    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    /**
     * 取得 持仓类别
     * @return 持仓类别
     */
    public String getPosType() {
        return posType;
    }

    /**
     * 设置 持仓类别
     * @param posType 持仓类别
     */
    public void setPosType(String posType) {
        this.posType = posType == null ? null : posType.trim();
    }

    /**
     * 取得 中间层数
     * @return 中间层数
     */
    public String getInterLeveNo() {
        return interLeveNo;
    }

    /**
     * 设置 中间层数
     * @param interLeveNo 中间层数
     */
    public void setInterLeveNo(String interLeveNo) {
        this.interLeveNo = interLeveNo == null ? null : interLeveNo.trim();
    }

    /**
     * 取得 资产穿透情况
     * @return 资产穿透情况
     */
    public String getInterAssetType() {
        return interAssetType;
    }

    /**
     * 设置 资产穿透情况
     * @param interAssetType 资产穿透情况
     */
    public void setInterAssetType(String interAssetType) {
        this.interAssetType = interAssetType == null ? null : interAssetType.trim();
    }

    /**
     * 取得 中间层行内资产/负债编码
     * @return 中间层行内资产/负债编码
     */
    public String getInterAssetCode() {
        return interAssetCode;
    }

    /**
     * 设置 中间层行内资产/负债编码
     * @param interAssetCode 中间层行内资产/负债编码
     */
    public void setInterAssetCode(String interAssetCode) {
        this.interAssetCode = interAssetCode == null ? null : interAssetCode.trim();
    }

    /**
     * 取得 会计科目名称
     * @return 会计科目名称
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 设置 会计科目名称
     * @param subjectName 会计科目名称
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 取得 金额
     * @return 金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 设置 金额
     * @param amt 金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 取得 折算人民币金额
     * @return 折算人民币金额
     */
    public BigDecimal getCnyAmt() {
        return cnyAmt;
    }

    /**
     * 设置 折算人民币金额
     * @param cnyAmt 折算人民币金额
     */
    public void setCnyAmt(BigDecimal cnyAmt) {
        this.cnyAmt = cnyAmt;
    }

    /**
     * 取得 公允价值
     * @return 公允价值
     */
    public BigDecimal getFloatIngpl() {
        return floatIngpl;
    }

    /**
     * 设置 公允价值
     * @param floatIngpl 公允价值
     */
    public void setFloatIngpl(BigDecimal floatIngpl) {
        this.floatIngpl = floatIngpl;
    }

    /**
     * 取得 折算人民币公允价值
     * @return 折算人民币公允价值
     */
    public BigDecimal getCnyFloatIngpl() {
        return cnyFloatIngpl;
    }

    /**
     * 设置 折算人民币公允价值
     * @param cnyFloatIngpl 折算人民币公允价值
     */
    public void setCnyFloatIngpl(BigDecimal cnyFloatIngpl) {
        this.cnyFloatIngpl = cnyFloatIngpl;
    }

    /**
     * 取得 单位估值（净价）
     * @return 单位估值（净价）
     */
    public BigDecimal getUnitValueCprice() {
        return unitValueCprice;
    }

    /**
     * 设置 单位估值（净价）
     * @param unitValueCprice 单位估值（净价）
     */
    public void setUnitValueCprice(BigDecimal unitValueCprice) {
        this.unitValueCprice = unitValueCprice;
    }

    /**
     * 取得 单位估值（全价）
     * @return 单位估值（全价）
     */
    public BigDecimal getUnitValueFprice() {
        return unitValueFprice;
    }

    /**
     * 设置 单位估值（全价）
     * @param unitValueFprice 单位估值（全价）
     */
    public void setUnitValueFprice(BigDecimal unitValueFprice) {
        this.unitValueFprice = unitValueFprice;
    }

    /**
     * 取得 数量
     * @return 数量
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * 设置 数量
     * @param qty 数量
     */
    public void setQty(BigDecimal qty) {
        this.qty = qty;
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
     * 取得 余额
     * @return 余额
     */
    public BigDecimal getBalanceAmt() {
        return balanceAmt;
    }

    /**
     * 设置 余额
     * @param balanceAmt 余额
     */
    public void setBalanceAmt(BigDecimal balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    /**
     * 取得 持仓登记日期
     * @return 持仓登记日期
     */
    public Date getValueRegDate() {
        return valueRegDate;
    }

    /**
     * 设置 持仓登记日期
     * @param valueRegDate 持仓登记日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setValueRegDate(Date valueRegDate) {
        this.valueRegDate = valueRegDate;
    }

    /**
     * 取得 报送状态
     * @return 报送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置 报送状态
     * @param sendStatus 报送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 取得 数据日期
     * @return 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 设置 数据日期
     * @param statusDate 数据日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 取得 报送截止日
     * @return 报送截止日
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 设置 报送截止日
     * @param chbSubmitDeadline 报送截止日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    /**
     * 取得 行内报送截止日
     * @return 行内报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 设置 行内报送截止日
     * @param submitDeadline 行内报送截止日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    /**
     * 取得 报送日期
     * @return 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 设置 报送日期
     * @param submitTime 报送日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 取得 反馈状态
     * @return 反馈状态
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 设置 反馈状态
     * @param submitFeedbackStatus 反馈状态
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}