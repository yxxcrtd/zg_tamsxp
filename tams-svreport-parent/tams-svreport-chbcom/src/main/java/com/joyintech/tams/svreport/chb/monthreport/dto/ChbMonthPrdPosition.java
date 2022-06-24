package com.joyintech.tams.svreport.chb.monthreport.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述:CHB_MONTH_PRD_POSITION表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-24
 */
public class ChbMonthPrdPosition {
    /**
     * null
     */
    private String posuuid;

    /**
     * 产品代码
     */
    private String prodid;

    /**
     * 报表开始日期
     */
    private Date startdate;

    /**
     * 报表结束日期
     */
    private Date enddate;

    /**
     * 统计日期
     */
    private Date bookdate;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 募集方式
     */
    private String raisemode;

    /**
     * 投资性质
     */
    private String ivstorientation;

    /**
     * 期限品种
     */
    private String termtype;

    /**
     * 运行方式
     */
    private String operateMode;

    /**
     * 产品收益方式
     */
    private String profitType;

    /**
     * 客户类型
     */
    private String saleTarget;

    /**
     * 结构性产品挂钩标的
     */
    private String conPrdTraget;

    /**
     * 投资者类别
     */
    private String ordIdentified;

    /**
     * 本期总募集金额
     */
    private BigDecimal dirtyamtsum;

    /**
     * 本期净募集金额
     */
    private BigDecimal cleanamtsum;

    /**
     * 本期赎回本金
     */
    private BigDecimal redeemamt;

    /**
     * 提前终止/到期本金
     */
    private BigDecimal terminationamt;

    /**
     * 期末余额
     */
    private BigDecimal endamtsumOth;

    /**
     * 本期客户实现收益总额
     */
    private BigDecimal customeramt;

    /**
     * 本期赎回利息
     */
    private BigDecimal redeemintamt;

    /**
     * 本期提前终止/到期 利息
     */
    private BigDecimal terminationintamt;

    /**
     * 本期现金分红/中间付息
     */
    private BigDecimal cashamt;

    /**
     * 存续状态
     */
    private String durationflag;

    /**
     * 起息日
     */
    private Date vdate;

    /**
     * 开放期限
     */
    private String openduration;

    /**
     * 是否现金管理类
     */
    private String cashmanacate;


    private List<String> prodids;

    public List<String> getProdids() {
        return prodids;
    }

    public void setProdids(List<String> prodids) {
        this.prodids = prodids;
    }

    //add by leipeng 20200606
    /**
     * 机构编号
     */
    private String  institutionCode;


    /**
     * 本期兑付金额
     * @return
     */
    private  BigDecimal  payamtsum;

    /**
     * 是否母行划转产品
     * @return
     */
    private String    isTransfer;


    /**
     * 是否合规新产品
     * @return
     */
    private   String  isFhxgcp;

    /**
     * 是否养老理财产品
     * @return
     */
    private   String  isYllccp;


    /**
     * 封闭式产品期限
     * @return
     */
    private   BigDecimal closetermday;

    /**
     * 加权率
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal yieldE;


    /**
     * 产品特殊属性
     */
    private  String  prdSpecProperty;

    public String getPrdSpecProperty() {
        return prdSpecProperty;
    }

    public void setPrdSpecProperty(String prdSpecProperty) {
        this.prdSpecProperty = prdSpecProperty;
    }

    public BigDecimal getPayamtsum() {
        return payamtsum;
    }

    public void setPayamtsum(BigDecimal payamtsum) {
        this.payamtsum = payamtsum;
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getIsFhxgcp() {
        return isFhxgcp;
    }

    public void setIsFhxgcp(String isFhxgcp) {
        this.isFhxgcp = isFhxgcp;
    }

    public String getIsYllccp() {
        return isYllccp;
    }

    public void setIsYllccp(String isYllccp) {
        this.isYllccp = isYllccp;
    }

    public BigDecimal getClosetermday() {
        return closetermday;
    }

    public void setClosetermday(BigDecimal closetermday) {
        this.closetermday = closetermday;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }
    //add by leipeng 20200606


    /**
     * null
     * @return POSUUID null
     */
    public String getPosuuid() {
        return posuuid;
    }

    /**
     * null
     * @param posuuid null
     */
    public void setPosuuid(String posuuid) {
        this.posuuid = posuuid == null ? null : posuuid.trim();
    }

    /**
     * 产品代码
     * @return PRODID 产品代码
     */
    public String getProdid() {
        return prodid;
    }

    /**
     * 产品代码
     * @param prodid 产品代码
     */
    public void setProdid(String prodid) {
        this.prodid = prodid == null ? null : prodid.trim();
    }

    /**
     * 报表开始日期
     * @return STARTDATE 报表开始日期
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * 报表开始日期
     * @param startdate 报表开始日期
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * 报表结束日期
     * @return ENDDATE 报表结束日期
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * 报表结束日期
     * @param enddate 报表结束日期
     */
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * 统计日期
     * @return BOOKDATE 统计日期
     */
    public Date getBookdate() {
        return bookdate;
    }

    /**
     * 统计日期
     * @param bookdate 统计日期
     */
    public void setBookdate(Date bookdate) {
        this.bookdate = bookdate;
    }

    /**
     * 币种
     * @return CCY 币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种
     * @param ccy 币种
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 募集方式
     * @return RAISEMODE 募集方式
     */
    public String getRaisemode() {
        return raisemode;
    }

    /**
     * 募集方式
     * @param raisemode 募集方式
     */
    public void setRaisemode(String raisemode) {
        this.raisemode = raisemode == null ? null : raisemode.trim();
    }

    /**
     * 投资性质
     * @return IVSTORIENTATION 投资性质
     */
    public String getIvstorientation() {
        return ivstorientation;
    }

    /**
     * 投资性质
     * @param ivstorientation 投资性质
     */
    public void setIvstorientation(String ivstorientation) {
        this.ivstorientation = ivstorientation == null ? null : ivstorientation.trim();
    }

    /**
     * 期限品种
     * @return TERMTYPE 期限品种
     */
    public String getTermtype() {
        return termtype;
    }

    /**
     * 期限品种
     * @param termtype 期限品种
     */
    public void setTermtype(String termtype) {
        this.termtype = termtype == null ? null : termtype.trim();
    }

    /**
     * 运行方式
     * @return OPERATE_MODE 运行方式
     */
    public String getOperateMode() {
        return operateMode;
    }

    /**
     * 运行方式
     * @param operateMode 运行方式
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode == null ? null : operateMode.trim();
    }

    /**
     * 产品收益方式
     * @return PROFIT_TYPE 产品收益方式
     */
    public String getProfitType() {
        return profitType;
    }

    /**
     * 产品收益方式
     * @param profitType 产品收益方式
     */
    public void setProfitType(String profitType) {
        this.profitType = profitType == null ? null : profitType.trim();
    }

    /**
     * 客户类型
     * @return SALE_TARGET 客户类型
     */
    public String getSaleTarget() {
        return saleTarget;
    }

    /**
     * 客户类型
     * @param saleTarget 客户类型
     */
    public void setSaleTarget(String saleTarget) {
        this.saleTarget = saleTarget == null ? null : saleTarget.trim();
    }

    /**
     * 结构性产品挂钩标的
     * @return CON_PRD_TRAGET 结构性产品挂钩标的
     */
    public String getConPrdTraget() {
        return conPrdTraget;
    }

    /**
     * 结构性产品挂钩标的
     * @param conPrdTraget 结构性产品挂钩标的
     */
    public void setConPrdTraget(String conPrdTraget) {
        this.conPrdTraget = conPrdTraget == null ? null : conPrdTraget.trim();
    }

    /**
     * 投资者类别
     * @return ORD_IDENTIFIED 投资者类别
     */
    public String getOrdIdentified() {
        return ordIdentified;
    }

    /**
     * 投资者类别
     * @param ordIdentified 投资者类别
     */
    public void setOrdIdentified(String ordIdentified) {
        this.ordIdentified = ordIdentified == null ? null : ordIdentified.trim();
    }

    /**
     * 本期总募集金额
     * @return DIRTYAMTSUM 本期总募集金额
     */
    public BigDecimal getDirtyamtsum() {
        return dirtyamtsum;
    }

    /**
     * 本期总募集金额
     * @param dirtyamtsum 本期总募集金额
     */
    public void setDirtyamtsum(BigDecimal dirtyamtsum) {
        this.dirtyamtsum = dirtyamtsum;
    }

    /**
     * 本期净募集金额
     * @return CLEANAMTSUM 本期净募集金额
     */
    public BigDecimal getCleanamtsum() {
        return cleanamtsum;
    }

    /**
     * 本期净募集金额
     * @param cleanamtsum 本期净募集金额
     */
    public void setCleanamtsum(BigDecimal cleanamtsum) {
        this.cleanamtsum = cleanamtsum;
    }

    /**
     * 本期赎回本金
     * @return REDEEMAMT 本期赎回本金
     */
    public BigDecimal getRedeemamt() {
        return redeemamt;
    }

    /**
     * 本期赎回本金
     * @param redeemamt 本期赎回本金
     */
    public void setRedeemamt(BigDecimal redeemamt) {
        this.redeemamt = redeemamt;
    }

    /**
     * 提前终止/到期本金
     * @return TERMINATIONAMT 提前终止/到期本金
     */
    public BigDecimal getTerminationamt() {
        return terminationamt;
    }

    /**
     * 提前终止/到期本金
     * @param terminationamt 提前终止/到期本金
     */
    public void setTerminationamt(BigDecimal terminationamt) {
        this.terminationamt = terminationamt;
    }

    /**
     * 期末余额
     * @return ENDAMTSUM_OTH 期末余额
     */
    public BigDecimal getEndamtsumOth() {
        return endamtsumOth;
    }

    /**
     * 期末余额
     * @param endamtsumOth 期末余额
     */
    public void setEndamtsumOth(BigDecimal endamtsumOth) {
        this.endamtsumOth = endamtsumOth;
    }

    /**
     * 本期客户实现收益总额
     * @return CUSTOMERAMT 本期客户实现收益总额
     */
    public BigDecimal getCustomeramt() {
        return customeramt;
    }

    /**
     * 本期客户实现收益总额
     * @param customeramt 本期客户实现收益总额
     */
    public void setCustomeramt(BigDecimal customeramt) {
        this.customeramt = customeramt;
    }

    /**
     * 本期赎回利息
     * @return REDEEMINTAMT 本期赎回利息
     */
    public BigDecimal getRedeemintamt() {
        return redeemintamt;
    }

    /**
     * 本期赎回利息
     * @param redeemintamt 本期赎回利息
     */
    public void setRedeemintamt(BigDecimal redeemintamt) {
        this.redeemintamt = redeemintamt;
    }

    /**
     * 本期提前终止/到期 利息
     * @return TERMINATIONINTAMT 本期提前终止/到期 利息
     */
    public BigDecimal getTerminationintamt() {
        return terminationintamt;
    }

    /**
     * 本期提前终止/到期 利息
     * @param terminationintamt 本期提前终止/到期 利息
     */
    public void setTerminationintamt(BigDecimal terminationintamt) {
        this.terminationintamt = terminationintamt;
    }

    /**
     * 本期现金分红/中间付息
     * @return CASHAMT 本期现金分红/中间付息
     */
    public BigDecimal getCashamt() {
        return cashamt;
    }

    /**
     * 本期现金分红/中间付息
     * @param cashamt 本期现金分红/中间付息
     */
    public void setCashamt(BigDecimal cashamt) {
        this.cashamt = cashamt;
    }

    /**
     * 存续状态
     * @return DURATIONFLAG 存续状态
     */
    public String getDurationflag() {
        return durationflag;
    }

    /**
     * 存续状态
     * @param durationflag 存续状态
     */
    public void setDurationflag(String durationflag) {
        this.durationflag = durationflag == null ? null : durationflag.trim();
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public Date getVdate() {
        return vdate;
    }

    /**
     * 起息日
     * @param vdate 起息日
     */
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    public String getOpenduration() {
        return openduration;
    }

    public void setOpenduration(String openduration) {
        this.openduration = openduration;
    }

    public String getCashmanacate() {
        return cashmanacate;
    }

    public void setCashmanacate(String cashmanacate) {
        this.cashmanacate = cashmanacate;
    }

    public BigDecimal getYieldE() {
        return yieldE;
    }

    public void setYieldE(BigDecimal yieldE) {
        this.yieldE = yieldE;
    }

    /**
     * 期限品种2 01 T+0产品/02 7天（含）以内/03 7天-1个月（含）/04 1-3个月（含）/05 3-6 25个月(含)/06 6-12个月（含）/08 1-3年（含）/09 3年以上
     */
    private String termtype2;

    public String getTermtype2() {
        return termtype2;
    }

    public void setTermtype2(String termtype2) {
        this.termtype2 = termtype2;
    }

    /**
     * 是否完全符合《通知》要求,01是/02否
     */
    private String isNotice;

    public String getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(String isNotice) {
        this.isNotice = isNotice;
    }

    /**
     * 本位币
     */
    private String functionalCurrency;

    /**
     * 获取 本位币
     * @return
     */
    public String getFunctionalCurrency() {
        return functionalCurrency;
    }

    /**
     * 设置 本位币
     * @param functionalCurrency
     */
    public void setFunctionalCurrency(String functionalCurrency) {
        this.functionalCurrency = functionalCurrency;
    }



    /**
     * 现金管理类产品-是否完全符合
     */
    private String isFullyCompliant;

    /**
     * 期限(天)
     */
    private BigDecimal termday;

    public BigDecimal getTermday() {
        return termday;
    }

    public void setTermday(BigDecimal termday) {
        this.termday = termday;
    }


    public String getIsFullyCompliant() {
        return isFullyCompliant;
    }

    public void setIsFullyCompliant(String isFullyCompliant) {
        this.isFullyCompliant = isFullyCompliant;
    }
}