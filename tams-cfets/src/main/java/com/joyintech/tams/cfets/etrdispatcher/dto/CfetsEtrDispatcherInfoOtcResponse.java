package com.joyintech.tams.cfets.etrdispatcher.dto;

import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.constant.DictCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @Author: kchen
 * @Date: 2022/2/23 19:11
 */
@ApiModel(value = "指令明细返回数据_场外", description = "指令明细数据_场外")
public class CfetsEtrDispatcherInfoOtcResponse extends CfetsEtrDispatcherBaseInfo {

    @ApiModelProperty(name = "assetType1", value = "资产二级分类", example = "字符串1")
    private String assetType1;

    @ApiModelProperty(name = "assetType10", value = "资产三级分类", example = "字符串1")
    private String assetType10;

    @ApiModelProperty(name = "assetType11", value = "资产四级分类", example = "字符串1")
    private String assetType11;

    @ApiModelProperty(name="vDate",value="起息日",example="2021-01-01")
    private String vDate;

    @ApiModelProperty(name="mDate",value="到期日",example="2021-01-01")
    private String mDate;

    @ApiModelProperty(name="termType",value="期限品种",example="1Y/1M")
    private String termType;

    @ApiModelProperty(name="termDays",value="期限(天)",example="7")
    private String termDays;

    @ApiModelProperty(name = "isAgreementEnd", value = "终止协议", example = "字符串1")
    private String isAgreementEnd;

    @ApiModelProperty(name="shareAmt",value="委托金额",example="1000000")
    private BigDecimal shareAmt;

    @ApiModelProperty(name="shareNum",value="委托份额",example="1000000")
    private BigDecimal shareNum;

    @ApiModelProperty(name="rate",value="利率",example="20.1%")
    private BigDecimal rate;

    @ApiModelProperty(name="chlId",value="通道代码",example="字符串1")
    private String chlId;

    @ApiModelProperty(name="chlIdName",value="通道名称",example="字符串1")
    private String chlIdName;

    @ApiModelProperty(name="otherPs",value="交易方向",example="字符串1")
    private String otherPs;

    @ApiModelProperty(name="calendarId",value="交易日历",example="字符串1")
    private String calendarId;

    @ApiModelProperty(name="calendarName",value="交易日历名称",example="字符串1")
    private String calendarName;

    @ApiModelProperty(name="trmFundType",value="交易所基金类型",example="字符串1")
    private String trmFundType;

    @ApiModelProperty(name="trmFundAbbr",value="交易所基金简称",example="字符串1")
    private String trmFundAbbr;

    @ApiModelProperty(name="price",value="指令价格",example="字符串1")
    private BigDecimal price;

    @ApiModelProperty(name="secAcctId",value="证券账户",example="XXX证券账户")
    private String secAcctId;

    @ApiModelProperty(name="remark",value="备注",example="A1")
    private String remark;

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public String getOtherPs() {
        if (otherPs != null) {
            return otherPs;
        }
        return SysDataUtil.getNameFromDataDict(DictCode.交易方向, super.getPs());
    }

    public void setOtherPs(String otherPs) {
        this.otherPs = otherPs;
    }

    public String getAssetType1() {
        return SysDataUtil.getNameFromDataDict(DictCode.行内资产二级分类, assetType1);
    }

    public void setAssetType1(String assetType1) {
        this.assetType1 = assetType1;
    }

    public String getAssetType10() {
        return SysDataUtil.getNameFromDataDict(DictCode.行内资产三级分类, assetType10);
    }

    public void setAssetType10(String assetType10) {
        this.assetType10 = assetType10;
    }

    public String getAssetType11() {
        return SysDataUtil.getNameFromDataDict(DictCode.行内资产四级分类, assetType11);
    }

    public void setAssetType11(String assetType11) {
        this.assetType11 = assetType11;
    }

    /**
     * 获取 otherTradeMarket
     * @return otherTradeMarket.
     */
    public String getOtherTradeMarket() {
        return SysDataUtil.getNameFromDataDict(DictCode.交易场所, super.getTradeMarket());
    }

    public String getvDate() {
        return vDate;
    }

    public void setvDate(String vDate) {
        this.vDate = vDate;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getIsAgreementEnd() {
        return isAgreementEnd;
    }

    public void setIsAgreementEnd(String isAgreementEnd) {
        this.isAgreementEnd = isAgreementEnd;
    }

    public BigDecimal getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(BigDecimal shareAmt) {
        this.shareAmt = shareAmt;
    }

    public BigDecimal getShareNum() {
        return shareNum;
    }

    public void setShareNum(BigDecimal shareNum) {
        this.shareNum = shareNum;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getChlId() {
        return chlId;
    }

    public void setChlId(String chlId) {
        this.chlId = chlId;
    }

    public String getChlIdName() {
        return chlIdName;
    }

    public void setChlIdName(String chlIdName) {
        this.chlIdName = chlIdName;
    }

    public String getTrmFundType() {
        return SysDataUtil.getNameFromDataDict(DictCode.交易所基金类型, trmFundType);
    }

    public void setTrmFundType(String trmFundType) {
        this.trmFundType = trmFundType;
    }

    public String getTrmFundAbbr() {
        return trmFundAbbr;
    }

    public void setTrmFundAbbr(String trmFundAbbr) {
        this.trmFundAbbr = trmFundAbbr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public String getOtherCcy() {
        return SysDataUtil.getNameFromDataDict(DictCode.币种, super.getCcy());
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTermDays() {
        return termDays;
    }

    public void setTermDays(String termDays) {
        this.termDays = termDays;
    }
}
