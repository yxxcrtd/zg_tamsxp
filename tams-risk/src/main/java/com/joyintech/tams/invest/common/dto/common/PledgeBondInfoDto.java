/*
 * 文件名：EtrInvestPersonInfo002Dto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2018年9月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.common.dto.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.CustomDateDeserialize;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.constant.DictCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 添加质押券Dto
 * 〈功能详细描述〉
 *
 * @author chenzhili
 * @version 1.0 2019年01月12日
 * @see PledgeBondInfoDto
 * @since 1.0
 */
public class PledgeBondInfoDto extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3215912348116904352L;
    /**
     * 债券代码
     */
    private String bondCode;

    /**
     * 债券金融产品代码
     */
    private String finprodId;

    /**
     * 剩余委托额度
     */
    private String entrustSurAmt;
    
    /**
     * 证券账户
     */
    private String secAcctId;
    
    /**
     * 回购利率%
     */
    private BigDecimal rebackPrice;
    
    /**
     * 回购利息
     */
    private BigDecimal rebackIntAmt;
    
    /**
     * 到期结算金额
     */
    private BigDecimal mdateSettleAmt;
    
    /**
     * 交易手续费
     */
    private BigDecimal tranFeeAmt;
    
    /**
     * 结算费
     */
    private BigDecimal settlementFee;
    
    /**
     * 交易金额
     */
    private BigDecimal tradeAmt;
    
    /**
     * 质押券汇总券面总额
     */
    private BigDecimal totalPrinAmt;

    // 20191230 质押券信息添加“质押券折算券面总额”属性信息 wangwei start
    /**
     * 质押券折算券面总额
     */
    private BigDecimal conversionTotalPrinAmt;
    // 20191230 质押券信息添加“质押券折算券面总额”属性信息 wangwei end
    
    /**
     * 佣金
     */
    private BigDecimal commission;
    
    /**
     * 委托代码
     */
    private String entrustId;
    
    /**
     * 本金金额
     */
    private BigDecimal prinAmt;
    
    
    /**
     * 收益类型
     * 债券质押回购多组合新增
     * 张庚林
     * 20190723
     */
    private String portfolioType;

    /**
     * 债券简称
     */
    private String bondAbbrName;
    
    /**
     * 债券类型
     * 债券质押回购多组合新增
     * 张庚林
     * 20190723
     */
    private String secType;
    
    /**
     * 组合代码
     */
    private String portfolioId;
    
    /**
     * 组合名称
     */
    private String portfolioName;
    
    /**
     * 到期日
     */
    private Date mdate;

    /**
     * 可质押券面总额
     */
    private BigDecimal avaPledgePrin;
    /**
     * 可质押本方券券面总额
     */
    private BigDecimal avaPledgeBfPrin;
    /**
     * 可质押融入券券面总额
     */
    private BigDecimal avaPledgeRrPrin;

    /**
     * 质押券面额
     */
    private BigDecimal pledgePrin;

    /**
     * 质押券折算总额
     */
    private BigDecimal pledgeAmt;
    /**
     * 质押本方券券面总额
     */
    private BigDecimal pledgeBfPrin;
    /**
     * 质押融入券券面总额
     */
    private BigDecimal pledgeRrPrin;


    /**
     * 质押券面额（初始）
     */
    private BigDecimal pledgePrinInit;

    /**
     * 首次净价
     */
    private BigDecimal firstNetPrice;

    /**
     * 质押比例%
     */
    private BigDecimal pledgeRatio;

    /**
     * 券面总额
     */
    private BigDecimal prin;

    /**
     * 登记托管机构
     */
    private String repRegistOrg;
 
    /**
     * 投资目的
     */
    private String invAim;

    /**
     * 按投资目的排序：摊余成本类->公允价值变动计入其他综合收益->公允价值变动计入当期损益
     */
    private Long invAimSort;

    /**
     * Name	Code	Comment	Data Type	Length
     * HODING_TYPE	HODING_TYPE	持仓类型，实际、在途、质押、融入、融出	VARCHAR2(50)	50
     */
    private String hodingType;
    /**
     * 标准券额度
     */
    private BigDecimal avaStandardStockPosition;
    /**
     * 组合T日
     */
    private BigDecimal avalibleCash;
    /**
    组合T+1日
     */
    private BigDecimal avalibleCashT1;
    /**
     * 通道可用现金
     */
    private BigDecimal channelAvailableCash;

    // 可质押列表增加“债项评级”、“到期日”、“登记托管机构”、“剩余期限”字段 add by renping 20191012 start
    /**
     * 债项评级
     */
    private String gradeResult;

    /**
     * 剩余期限
     */
    private BigDecimal endDays1;

    /**
     * 获取 gradeResult
     * 
     * @return gradeResult.
     */
    public String getGradeResult() {
        return gradeResult;
    }
    
    /**
     * 获取 评级结果
     * 
     * @return 评级结果.
     */
    public String getGradeResultStr() {
        if (gradeResult != null) {
            return SysDataUtil.getNameFromDataDict(DictCode.评级结果, this.getGradeResult());
        }
        return gradeResult;
    }

    /**
     * 设置 gradeResult
     * 
     * @param gradeResult gradeResult
     */
    public void setGradeResult(String gradeResult) {
        this.gradeResult = gradeResult;
    }

    /**
     * 获取 endDays1
     * 
     * @return endDays1.
     */
    public BigDecimal getEndDays1() {
        return endDays1;
    }

    /**
     * 设置 endDays1
     * 
     * @param endDays1 endDays1
     */
    public void setEndDays1(BigDecimal endDays1) {
        this.endDays1 = endDays1;
    }
    // 可质押列表增加“债项评级”、“到期日”、“登记托管机构”、“剩余期限”字段 add by renping 20191012 end
    public BigDecimal getAvaStandardStockPosition() {
        return avaStandardStockPosition;
    }

    public void setAvaStandardStockPosition(BigDecimal avaStandardStockPosition) {
        this.avaStandardStockPosition = avaStandardStockPosition;
    }

    public BigDecimal getAvalibleCash() {
        return avalibleCash;
    }

    public void setAvalibleCash(BigDecimal avalibleCash) {
        this.avalibleCash = avalibleCash;
    }

    public BigDecimal getAvalibleCashT1() {
        return avalibleCashT1;
    }

    public void setAvalibleCashT1(BigDecimal avalibleCashT1) {
        this.avalibleCashT1 = avalibleCashT1;
    }

    public BigDecimal getChannelAvailableCash() {
        return channelAvailableCash;
    }

    public void setChannelAvailableCash(BigDecimal channelAvailableCash) {
        this.channelAvailableCash = channelAvailableCash;
    }

    /**
     * 获取 bondCode
     *
     * @rturn bondCode
     */
    public String getBondCode() {
        return this.bondCode;
    }

    /**
     * 设置 bondCode
     *
     * @param bondCode bondCode
     */
    public void setBondCode(String bondCode) {
        this.bondCode = bondCode;
    }

    /**
     * 获取 bondAbbrName
     *
     * @rturn bondAbbrName
     */
    public String getBondAbbrName() {
        return this.bondAbbrName;
    }

    /**
     * 设置 bondAbbrName
     *
     * @param bondAbbrName bondAbbrName
     */
    public void setBondAbbrName(String bondAbbrName) {
        this.bondAbbrName = bondAbbrName;
    }

    /**
     * 获取 mdate
     *
     * @rturn mdate
     */
    public Date getMdate() {
        return this.mdate;
    }

    /**
     * 设置 mdate
     *
     * @param mdate mdate
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    /**
     * 获取 avaPledgePrin
     *
     * @rturn avaPledgePrin
     */
    public BigDecimal getAvaPledgePrin() {
        return this.avaPledgePrin;
    }

    /**
     * 设置 avaPledgePrin
     *
     * @param avaPledgePrin avaPledgePrin
     */
    public void setAvaPledgePrin(BigDecimal avaPledgePrin) {
        this.avaPledgePrin = avaPledgePrin;
    }

    /**
     * 获取 pledgePrin
     *
     * @rturn pledgePrin
     */
    public BigDecimal getPledgePrin() {
        return this.pledgePrin;
    }

    /**
     * 设置 pledgePrin
     *
     * @param pledgePrin pledgePrin
     */
    public void setPledgePrin(BigDecimal pledgePrin) {
        this.pledgePrin = pledgePrin;
    }

    public BigDecimal getPledgeAmt() {
        return pledgeAmt;
    }

    public void setPledgeAmt(BigDecimal pledgeAmt) {
        this.pledgeAmt = pledgeAmt;
    }

    /**
     * 获取 pledgePrinInit
     *
     * @rturn pledgePrinInit
     */
    public BigDecimal getPledgePrinInit() {
        return this.pledgePrinInit;
    }

    /**
     * 设置 pledgePrinInit
     *
     * @param pledgePrinInit pledgePrinInit
     */
    public void setPledgePrinInit(BigDecimal pledgePrinInit) {
        this.pledgePrinInit = pledgePrinInit;
    }

    /**
     * 获取 firstNetPrice
     *
     * @rturn firstNetPrice
     */
    public BigDecimal getFirstNetPrice() {
        return this.firstNetPrice;
    }

    /**
     * 设置 firstNetPrice
     *
     * @param firstNetPrice firstNetPrice
     */
    public void setFirstNetPrice(BigDecimal firstNetPrice) {
        this.firstNetPrice = firstNetPrice;
    }

    /**
     * 获取 pledgeRatio
     *
     * @rturn pledgeRatio
     */
    public BigDecimal getPledgeRatio() {
        return this.pledgeRatio;
    }

    /**
     * 设置 pledgeRatio
     *
     * @param pledgeRatio pledgeRatio
     */
    public void setPledgeRatio(BigDecimal pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
    }

    /**
     * 获取 prin
     *
     * @rturn prin
     */
    public BigDecimal getPrin() {
        return this.prin;
    }

    /**
     * 设置 prin
     *
     * @param prin prin
     */
    public void setPrin(BigDecimal prin) {
        this.prin = prin;
    }

    /**
     * 获取 repRegistOrg
     *
     * @rturn repRegistOrg
     */
    public String getRepRegistOrg() {
        return this.repRegistOrg;
    }

    /**
     * 设置 repRegistOrg
     *
     * @param repRegistOrg repRegistOrg
     */
    public void setRepRegistOrg(String repRegistOrg) {
        this.repRegistOrg = repRegistOrg;
    }

    /**
     * 获取 登记托管机构
     * @rturn ccyText
     */
    public String getRepRegistOrgStr() {
        if(StringUtil.isNoneBlank( this.getRepRegistOrg())){
            return SysDataUtil.getNameFromDataDict(DictCode.登记托管机构_中债, this.getRepRegistOrg());
        }
        return null;
    }
    
    /**
     * 获取债券一级类型name
     * @return
     * @see
     * @since 1.0
     */
    public String getSecTypeStr() {
        if(StringUtil.isNoneBlank( this.getRepRegistOrg())){
            return SysDataUtil.getNameFromDataDict(DictCode.WIND一级分类_含债券一级分类等, this.getSecType());
        }
        return null;
    }

    /**
     * 获取 invAim
     *
     * @rturn invAim
     */
    public String getInvAim() {
        return this.invAim;
    }

    /**
     * 设置 invAim
     *
     * @param invAim invAim
     */
    public void setInvAim(String invAim) {
        this.invAim = invAim;
    }

    /**
     * 获取 invAimSort
     *
     * @rturn invAimSort
     */
    public Long getInvAimSort() {
        return this.invAimSort;
    }

    /**
     * 设置 invAimSort
     *
     * @param invAimSort invAimSort
     */
    public void setInvAimSort(Long invAimSort) {
        this.invAimSort = invAimSort;
    }

    
    /**
     * 获取 secType
     * @return secType.
     */
    public String getSecType() {
        return secType;
    }

    
    /**
     * 设置 secType
     * @param secType secType
     */
    public void setSecType(String secType) {
        this.secType = secType;
    }

    
    /**
     * 获取 portfolioId
     * @return portfolioId.
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    
    /**
     * 设置 portfolioId
     * @param portfolioId portfolioId
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    
    /**
     * 获取 portfolioName
     * @return portfolioName.
     */
    public String getPortfolioName() {
        return portfolioName;
    }

    
    /**
     * 设置 portfolioName
     * @param portfolioName portfolioName
     */
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    
    /**
     * 获取 serialversionuid
     * @return serialversionuid.
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    
    /**
     * 获取 portfolioType
     * @return portfolioType.
     */
    public String getPortfolioType() {
        return portfolioType;
    }

    
    /**
     * 设置 portfolioType
     * @param portfolioType portfolioType
     */
    public void setPortfolioType(String portfolioType) {
        this.portfolioType = portfolioType;
    }

    
    /**
     * 获取 entrustId
     * @return entrustId.
     */
    public String getEntrustId() {
        return entrustId;
    }

    
    /**
     * 设置 entrustId
     * @param entrustId entrustId
     */
    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    
    /**
     * 获取 prinAmt
     * @return prinAmt.
     */
    public BigDecimal getPrinAmt() {
        return prinAmt;
    }

    
    /**
     * 设置 prinAmt
     * @param prinAmt prinAmt
     */
    public void setPrinAmt(BigDecimal prinAmt) {
        this.prinAmt = prinAmt;
    }

    
    /**
     * 获取 entrustSurAmt
     * @return entrustSurAmt.
     */
    public String getEntrustSurAmt() {
        return entrustSurAmt;
    }

    
    /**
     * 设置 entrustSurAmt
     * @param entrustSurAmt entrustSurAmt
     */
    public void setEntrustSurAmt(String entrustSurAmt) {
        this.entrustSurAmt = entrustSurAmt;
    }

    
    /**
     * 获取 rebackPrice
     * @return rebackPrice.
     */
    public BigDecimal getRebackPrice() {
        return rebackPrice;
    }

    
    /**
     * 设置 rebackPrice
     * @param rebackPrice rebackPrice
     */
    public void setRebackPrice(BigDecimal rebackPrice) {
        this.rebackPrice = rebackPrice;
    }

    
    /**
     * 获取 secAcctId
     * @return secAcctId.
     */
    public String getSecAcctId() {
        return secAcctId;
    }

    
    /**
     * 设置 secAcctId
     * @param secAcctId secAcctId
     */
    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    
    /**
     * 获取 rebackIntAmt
     * @return rebackIntAmt.
     */
    public BigDecimal getRebackIntAmt() {
        return rebackIntAmt;
    }

    
    /**
     * 设置 rebackIntAmt
     * @param rebackIntAmt rebackIntAmt
     */
    public void setRebackIntAmt(BigDecimal rebackIntAmt) {
        this.rebackIntAmt = rebackIntAmt;
    }

    
    /**
     * 获取 mdateSettleAmt
     * @return mdateSettleAmt.
     */
    public BigDecimal getMdateSettleAmt() {
        return mdateSettleAmt;
    }

    
    /**
     * 设置 mdateSettleAmt
     * @param mdateSettleAmt mdateSettleAmt
     */
    public void setMdateSettleAmt(BigDecimal mdateSettleAmt) {
        this.mdateSettleAmt = mdateSettleAmt;
    }

    
    /**
     * 获取 tranFeeAmt
     * @return tranFeeAmt.
     */
    public BigDecimal getTranFeeAmt() {
        return tranFeeAmt;
    }

    
    /**
     * 设置 tranFeeAmt
     * @param tranFeeAmt tranFeeAmt
     */
    public void setTranFeeAmt(BigDecimal tranFeeAmt) {
        this.tranFeeAmt = tranFeeAmt;
    }

    
    /**
     * 获取 settlementFee
     * @return settlementFee.
     */
    public BigDecimal getSettlementFee() {
        return settlementFee;
    }

    
    /**
     * 设置 settlementFee
     * @param settlementFee settlementFee
     */
    public void setSettlementFee(BigDecimal settlementFee) {
        this.settlementFee = settlementFee;
    }

    
    /**
     * 获取 tradeAmt
     * @return tradeAmt.
     */
    public BigDecimal getTradeAmt() {
        return tradeAmt;
    }

    
    /**
     * 设置 tradeAmt
     * @param tradeAmt tradeAmt
     */
    public void setTradeAmt(BigDecimal tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    
    /**
     * 获取 totalPrinAmt
     * @return totalPrinAmt.
     */
    public BigDecimal getTotalPrinAmt() {
        return totalPrinAmt;
    }

    
    /**
     * 设置 totalPrinAmt
     * @param totalPrinAmt totalPrinAmt
     */
    public void setTotalPrinAmt(BigDecimal totalPrinAmt) {
        this.totalPrinAmt = totalPrinAmt;
    }

    
    /**
     * 获取 commission
     * @return commission.
     */
    public BigDecimal getCommission() {
        return commission;
    }

    
    /**
     * 设置 commission
     * @param commission commission
     */
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public String getHodingType() {
        return hodingType;
    }

    public void setHodingType(String hodingType) {
        this.hodingType = hodingType;
    }

    public BigDecimal getAvaPledgeBfPrin() {
        return avaPledgeBfPrin;
    }

    public void setAvaPledgeBfPrin(BigDecimal avaPledgeBfPrin) {
        this.avaPledgeBfPrin = avaPledgeBfPrin;
    }

    public BigDecimal getAvaPledgeRrPrin() {
        return avaPledgeRrPrin;
    }

    public void setAvaPledgeRrPrin(BigDecimal avaPledgeRrPrin) {
        this.avaPledgeRrPrin = avaPledgeRrPrin;
    }

    public BigDecimal getPledgeBfPrin() {
        return pledgeBfPrin;
    }

    public void setPledgeBfPrin(BigDecimal pledgeBfPrin) {
        this.pledgeBfPrin = pledgeBfPrin;
    }

    public BigDecimal getPledgeRrPrin() {
        return pledgeRrPrin;
    }

    public void setPledgeRrPrin(BigDecimal pledgeRrPrin) {
        this.pledgeRrPrin = pledgeRrPrin;
    }

    /**
     * 获取 finprodId
     *
     * @return finprodId
     */
    public String getFinprodId() {
        return finprodId;
    }

    /**
     * 设置 finprodId
     *
     * @param finprodId finprodId
     */
    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    // 20191230 质押券信息添加“质押券折算券面总额”属性信息 wangwei start
    /**
     * 取得 质押券折算券面总额
     * @return 质押券折算券面总额
     */
    public BigDecimal getConversionTotalPrinAmt() {
        return conversionTotalPrinAmt;
    }

    /**
     * 设置 质押券折算券面总额
     * @param conversionTotalPrinAmt 质押券折算券面总额
     */
    public void setConversionTotalPrinAmt(BigDecimal conversionTotalPrinAmt) {
        this.conversionTotalPrinAmt = conversionTotalPrinAmt;
    }
    // 20191230 质押券信息添加“质押券折算券面总额”属性信息 wangwei end
    
    // 2020-1-7 自动导入质押券 新增属性 lizhi start
    /**
     * 一级分类
     */
    private String windTypeOne ;
    
    /**
     * 二级分类
     */
    private String windTypeTwo ;
    
    /**
     * 质押优先级
     */
    private String pledgePri ;
    /**
     * 质押优先级
     */
    private String lastPeriod ;
    
    public String getWindTypeOne() {
        return windTypeOne;
    }
    
    public void setWindTypeOne(String windTypeOne) {
        this.windTypeOne = windTypeOne;
    }

    public String getWindTypeTwo() {
        return windTypeTwo;
    }
    
    public void setWindTypeTwo(String windTypeTwo) {
        this.windTypeTwo = windTypeTwo;
    }
    
    public String getPledgePri() {
        return pledgePri;
    }
    
    public void setPledgePri(String pledgePri) {
        this.pledgePri = pledgePri;
    }
    
    public String getLastPeriod() {
        return lastPeriod;
    }
    
    public void setLastPeriod(String lastPeriod) {
        this.lastPeriod = lastPeriod;
    }
    // 2020-1-7 自动导入质押券 新增属性 lizhi start
    
}
