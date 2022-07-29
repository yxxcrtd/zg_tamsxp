package com.joyintech.tams.svreport.chb.astposregist.dto;

import com.csii.dc.client.util.StringUtil;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;

import java.math.BigDecimal;
import java.util.Date;
public class ChbAstPosBatchResultDto extends BaseEntity implements ITableRow {

    /**
     * 当前持仓
     */
    private BigDecimal posAmt;

    /**
     * 当日成本
     */
    private BigDecimal tdyCostAmt;

    /**
     * 当日估值增值
     */
    private BigDecimal tdyFloatIngpl;

    /**
     * 原币-当日累计应计利息余额
     */
    private BigDecimal tdyIntincexp;

    /**
     * 影子价格
     */
    private BigDecimal   shadowAmt;

    /**
     * 原币-减值余额
     */
    private BigDecimal tdyLossAmt;

    /**
     *应付/应收债券借贷费用
     */
    private  BigDecimal recPayLendFeeAmt;

    /**
     * 资产负债二级类别
     */
    private String  assetType2;

    /**
     * 份额面值
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal prinBaseamt;

    /**
     * 到期日
     */
    private Date mdate;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 持仓日期
     */
    private Date valueDate;


    /**
     * 币种
     */
    private String ccy;

    /**
     * 产品登记编码
     */
    private String prdRegistCode;


    /**
     * 行内资产/负债编码
     */
    private String assetId;


    /**
     * 中间层数
     */
    private String interLeveNo;


    /**
     * 中间层行内资产/负债编码
     */
    private String interAssetCode;

    /**
     * 持仓类别
     */
    @DictFormat(DictCode.中债持仓类型)
    private String posType;

    /**
     * 科目名称
     */
    private  String subjectName;

    /**
     * 科目代码
     */
    private  String subjectNo;

    /**
     * 原币余额
     */
    private  BigDecimal oAmt;

    /**
     * 原币-底层资产影子价格
     */
    private  BigDecimal  oPosShadowAmt;

    /**
     * 原币-资管计划总发行金额
     */
    private  BigDecimal  oIssueAmt;

    /**
     * 原币-底层资产市值
     */
    private  BigDecimal  oPosAmt;

    /**
     * 是否有预期收益率 01是/02否
     */
    private String rateFlag;

    /**
     * 资管计划行内资产/负债编码
     */
    private String planAssetId;

    /**
     * 总发行数量
     */
    private BigDecimal totalNum;

    /**
     * 原币-未投资头寸
     */
    private BigDecimal oUninvestCash;


    /**
     * 持仓数量
     */
    private BigDecimal posNum;


    /**
     *原币-底层资产净价市值
     */
    private BigDecimal   oPosCleanPricEmarketValue;

    /**
     *错误信息
     */
    private   String  errMsg;


    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectNo() {
        return subjectNo;
    }

    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo;
    }

    public BigDecimal getPosAmt() {
        return posAmt;
    }

    public void setPosAmt(BigDecimal posAmt) {
        this.posAmt = posAmt;
    }


    public String getAssetType2() {
        return assetType2;
    }

    public void setAssetType2(String assetType2) {
        this.assetType2 = assetType2;
    }

    public BigDecimal getPrinBaseamt() {
        return prinBaseamt;
    }

    public void setPrinBaseamt(BigDecimal prinBaseamt) {
        this.prinBaseamt = prinBaseamt;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    public String getIssuePartyId() {
        return issuePartyId;
    }

    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId;
    }

    public String getRegistSeqno() {
        return registSeqno;
    }

    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getPrdRegistCode() {
        return prdRegistCode;
    }

    public void setPrdRegistCode(String prdRegistCode) {
        this.prdRegistCode = prdRegistCode;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getInterLeveNo() {
        return interLeveNo;
    }

    public void setInterLeveNo(String interLeveNo) {
        this.interLeveNo = interLeveNo;
    }

    public String getInterAssetCode() {
        return interAssetCode;
    }

    public void setInterAssetCode(String interAssetCode) {
        this.interAssetCode = interAssetCode;
    }

    public String getPosType() {
        return posType;
    }

    public void setPosType(String posType) {
        this.posType = posType;
    }

    public String getRateFlag() {
        return rateFlag;
    }

    public void setRateFlag(String rateFlag) {
        this.rateFlag = rateFlag;
    }

    public BigDecimal getoAmt() {
        return oAmt;
    }

    public void setoAmt(BigDecimal oAmt) {
        this.oAmt = oAmt;
    }

    public String getPlanAssetId() {
        return planAssetId;
    }

    public void setPlanAssetId(String planAssetId) {
        this.planAssetId = planAssetId;
    }

    public BigDecimal getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(BigDecimal totalNum) {
        this.totalNum = totalNum;
    }


    public BigDecimal getPosNum() {
        return posNum;
    }

    public void setPosNum(BigDecimal posNum) {
        this.posNum = posNum;
    }

    public BigDecimal getTdyCostAmt() {
        return tdyCostAmt;
    }

    public void setTdyCostAmt(BigDecimal tdyCostAmt) {
        this.tdyCostAmt = tdyCostAmt;
    }

    public BigDecimal getTdyFloatIngpl() {
        return tdyFloatIngpl;
    }

    public void setTdyFloatIngpl(BigDecimal tdyFloatIngpl) {
        this.tdyFloatIngpl = tdyFloatIngpl;
    }

    public BigDecimal getTdyIntincexp() {
        return tdyIntincexp;
    }

    public void setTdyIntincexp(BigDecimal tdyIntincexp) {
        this.tdyIntincexp = tdyIntincexp;
    }

    public BigDecimal getShadowAmt() {
        return shadowAmt;
    }

    public void setShadowAmt(BigDecimal shadowAmt) {
        this.shadowAmt = shadowAmt;
    }

    public BigDecimal getTdyLossAmt() {
        return tdyLossAmt;
    }

    public void setTdyLossAmt(BigDecimal tdyLossAmt) {
        this.tdyLossAmt = tdyLossAmt;
    }

    public BigDecimal getoPosShadowAmt() {
        return oPosShadowAmt;
    }

    public void setoPosShadowAmt(BigDecimal oPosShadowAmt) {
        this.oPosShadowAmt = oPosShadowAmt;
    }

    public BigDecimal getoIssueAmt() {
        return oIssueAmt;
    }

    public void setoIssueAmt(BigDecimal oIssueAmt) {
        this.oIssueAmt = oIssueAmt;
    }

    public BigDecimal getoPosAmt() {
        return oPosAmt;
    }

    public void setoPosAmt(BigDecimal oPosAmt) {
        this.oPosAmt = oPosAmt;
    }

    public BigDecimal getoUninvestCash() {
        return oUninvestCash;
    }

    public void setoUninvestCash(BigDecimal oUninvestCash) {
        this.oUninvestCash = oUninvestCash;
    }

    public BigDecimal getoPosCleanPricEmarketValue() {
        return oPosCleanPricEmarketValue;
    }

    public void setoPosCleanPricEmarketValue(BigDecimal oPosCleanPricEmarketValue) {
        this.oPosCleanPricEmarketValue = oPosCleanPricEmarketValue;
    }

    public BigDecimal getRecPayLendFeeAmt() {
        return recPayLendFeeAmt;
    }

    public void setRecPayLendFeeAmt(BigDecimal recPayLendFeeAmt) {
        this.recPayLendFeeAmt = recPayLendFeeAmt;
    }
}