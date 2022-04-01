/*
 * 文件名：CfetsRepoDealQuotedPriceDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：yufei
 * 修改时间：2020年5月14日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.bankuplink.repo.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.invest.common.constant.BussinessDictCode;

/**
 * CFETS质押式回购报价画面质押券dto 〈功能详细描述〉
 * 
 * @author yufei
 * @version 1.0 2020年5月14日
 * @see CfetsRepoDealQuotedPrice003Dto
 * @since 1.0
 */
public class CfetsRepoDealQuotedPrice003Dto extends BaseEntity {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -5534440153855152285L;

    /**
     * 报价编号
     */
    private String quoteId;

    /**
     * 金融产品代码
     */
    private String finprodId;

    /**
     * 债券代码
     */
    private String bondCode;

    /**
     * 债券简称
     */
    private String bondAbbrName;

    /**
     * 是否永续
     */
    private String isSus;

    /**
     * 是否次级
     */
    private String isSecBond;

    /**
     * 证券账户
     */
    private String secAcctId;

    /**
     * 证券账户
     */
    private String secAcctName;

    /**
     * 到期日
     */
    private Date mdate;

    /**
     * 券面总额
     */
    private BigDecimal prin;

    /**
     * 可质押券面总额
     */
    private BigDecimal avaPledgePrin;

    /**
     * 估值净价
     */
    private BigDecimal netPrice;

    /**
     * 今日已质押面额
     */
    private BigDecimal pledgeNumber;

    /**
     * 剩余可质押面额
     */
    private BigDecimal avaPledgeNumber;

    /**
     * 本次质押券面
     */
    private BigDecimal pledgePrin;

    /**
     * 折算比例%
     */
    private BigDecimal pledgeRate;

    /**
     * 最新净价日期
     */
    private Date valueDate;

    /**
     * 下一行权日
     */
    private Date exerciseDate;

    /**
     * 登记托管机构
     */
    private String repRegistOrg;

    /**
     * 是否冻结
     */
    private String freezeFlag;

    /**
     * 当日的有效卖出(T+0/+1)委托指令券面
     */
    private BigDecimal prinSysDate;

    /**
     * 昨日的T+1卖出券面
     */
    private BigDecimal prinDayBefore;

    /**
     * 昨日的债券持仓券面-昨日已质押券面
     */
    private BigDecimal avaPrinDayBefore;

    /**
     * 组合代码
     */
    private String portfolioId;

    private List<CfetsRepoDealQuotedPrice003Dto> pledgeBondList;

    private String portfolioName;

    /**
     * 委托编号
     */
    private String entrustId;

    /**
     * 质押率
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal pledgeRatio;

    /*
     * 首次净价
     */
    private String firstNetPrice;

    /**
     * 获取 prinSysDate
     * 
     * @return prinSysDate.
     */
    public BigDecimal getPrinSysDate() {
        return prinSysDate;
    }

    /**
     * 设置 prinSysDate
     * 
     * @param prinSysDate prinSysDate
     */
    public void setPrinSysDate(BigDecimal prinSysDate) {
        this.prinSysDate = prinSysDate;
    }

    /**
     * 获取 prinDayBefore
     * 
     * @return prinDayBefore.
     */
    public BigDecimal getPrinDayBefore() {
        return prinDayBefore;
    }

    /**
     * 设置 prinDayBefore
     * 
     * @param prinDayBefore prinDayBefore
     */
    public void setPrinDayBefore(BigDecimal prinDayBefore) {
        this.prinDayBefore = prinDayBefore;
    }

    /**
     * 获取 avaPrinDayBefore
     * 
     * @return avaPrinDayBefore.
     */
    public BigDecimal getAvaPrinDayBefore() {
        return avaPrinDayBefore;
    }

    /**
     * 设置 avaPrinDayBefore
     * 
     * @param avaPrinDayBefore avaPrinDayBefore
     */
    public void setAvaPrinDayBefore(BigDecimal avaPrinDayBefore) {
        this.avaPrinDayBefore = avaPrinDayBefore;
    }

    /**
     * 获取 pledgePrin
     * 
     * @return pledgePrin.
     */
    public BigDecimal getPledgePrin() {
        return pledgePrin;
    }

    /**
     * 设置 pledgePrin
     * 
     * @param pledgePrin pledgePrin
     */
    public void setPledgePrin(BigDecimal pledgePrin) {
        this.pledgePrin = pledgePrin;
    }

    /**
     * 获取 pledgeRate
     * 
     * @return pledgeRate.
     */
    public BigDecimal getPledgeRate() {
        return pledgeRate;
    }

    /**
     * 设置 pledgeRate
     * 
     * @param pledgeRate pledgeRate
     */
    public void setPledgeRate(BigDecimal pledgeRate) {
        this.pledgeRate = pledgeRate;
    }

    /**
     * 获取 pledgeBondList
     * 
     * @return pledgeBondList.
     */
    public List<CfetsRepoDealQuotedPrice003Dto> getPledgeBondList() {
        return pledgeBondList;
    }

    /**
     * 设置 pledgeBondList
     * 
     * @param pledgeBondList pledgeBondList
     */
    public void setPledgeBondList(List<CfetsRepoDealQuotedPrice003Dto> pledgeBondList) {
        this.pledgeBondList = pledgeBondList;
    }

    /**
     * 获取 freezeFlag
     * 
     * @return freezeFlag.
     */
    public String getFreezeFlag() {
        return freezeFlag;
    }

    /**
     * 获取 secAcctName
     * 
     * @return secAcctName.
     */
    public String getSecAcctName() {
        return secAcctName;
    }

    /**
     * 设置 secAcctName
     * 
     * @param secAcctName secAcctName
     */
    public void setSecAcctName(String secAcctName) {
        this.secAcctName = secAcctName;
    }

    /**
     * 设置 freezeFlag
     * 
     * @param freezeFlag freezeFlag
     */
    public void setFreezeFlag(String freezeFlag) {
        this.freezeFlag = freezeFlag;
    }

    /**
     * 获取 exerciseDate
     * 
     * @return exerciseDate.
     */
    public Date getExerciseDate() {
        return exerciseDate;
    }

    /**
     * <一句话功能简述>
     * 
     * @return 格式化日期字符串
     * @see
     * @since 1.0
     */
    public String getExerciseDateStr() {
        if (this.getExerciseDate() != null) {
            return DateUtil.format(getExerciseDate(), DateUtil.DEFAULT_DATE_FORMAT_EN);
        }
        else {
            return null;
        }
    }

    /**
     * 获取 pledgeNumber
     * 
     * @return pledgeNumber.
     */
    public BigDecimal getPledgeNumber() {
        return pledgeNumber;
    }

    /**
     * 设置 pledgeNumber
     * 
     * @param pledgeNumber pledgeNumber
     */
    public void setPledgeNumber(BigDecimal pledgeNumber) {
        this.pledgeNumber = pledgeNumber;
    }

    /**
     * 获取 avaPledgeNumber
     * 
     * @return avaPledgeNumber.
     */
    public BigDecimal getAvaPledgeNumber() {
        return avaPledgeNumber;
    }

    /**
     * 设置 avaPledgeNumber
     * 
     * @param avaPledgeNumber avaPledgeNumber
     */
    public void setAvaPledgeNumber(BigDecimal avaPledgeNumber) {
        this.avaPledgeNumber = avaPledgeNumber;
    }

    /**
     * 设置 exerciseDate
     * 
     * @param exerciseDate exerciseDate
     */
    public void setExerciseDate(Date exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    /**
     * 获取 finprodId
     * 
     * @return finprodId.
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

    /**
     * 获取 bondCode
     * 
     * @return bondCode.
     */
    public String getBondCode() {
        return bondCode;
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
     * @return bondAbbrName.
     */
    public String getBondAbbrName() {
        return bondAbbrName;
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
     * 获取 isSus
     * 
     * @return isSus.
     */
    public String getIsSus() {
        return isSus;
    }

    /**
     * 设置 isSus
     * 
     * @param isSus isSus
     */
    public void setIsSus(String isSus) {
        this.isSus = isSus;
    }

    /**
     * 获取 isSecBond
     * 
     * @return isSecBond.
     */
    public String getIsSecBond() {
        return isSecBond;
    }

    /**
     * 设置 isSecBond
     * 
     * @param isSecBond isSecBond
     */
    public void setIsSecBond(String isSecBond) {
        this.isSecBond = isSecBond;
    }

    /**
     * 获取 secAcctId
     * 
     * @return secAcctId.
     */
    public String getSecAcctId() {
        return secAcctId;
    }

    /**
     * 设置 secAcctId
     * 
     * @param secAcctId secAcctId
     */
    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    /**
     * 获取 mdate
     * 
     * @return mdate.
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * <一句话功能简述>
     * 
     * @return 格式化日期字符串
     * @see
     * @since 1.0
     */
    public String getMdateStr() {
        if (this.getMdate() != null) {
            return DateUtil.format(getMdate(), DateUtil.DEFAULT_DATE_FORMAT_EN);
        }
        else {
            return null;
        }
    }

    /**
     * 设置 mdate
     * 
     * @param mdate mdate
     */
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    /**
     * 获取 prin
     * 
     * @return prin.
     */
    public BigDecimal getPrin() {
        return prin;
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
     * 获取 avaPledgePrin
     * 
     * @return avaPledgePrin.
     */
    public BigDecimal getAvaPledgePrin() {
        return avaPledgePrin;
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
     * 获取 netPrice
     * 
     * @return netPrice.
     */
    public BigDecimal getNetPrice() {
        return netPrice;
    }

    /**
     * 设置 netPrice
     * 
     * @param netPrice netPrice
     */
    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    /**
     * 获取 valueDate
     * 
     * @return valueDate.
     */
    public Date getValueDate() {
        return valueDate;
    }

    /**
     * <一句话功能简述>
     * 
     * @return 格式化日期字符串
     * @see
     * @since 1.0
     */
    public String getValueDateStr() {
        if (this.getValueDate() != null) {
            return DateUtil.format(getValueDate(), DateUtil.DEFAULT_DATE_FORMAT_EN);
        }
        else {
            return null;
        }
    }

    /**
     * 设置 valueDate
     * 
     * @param valueDate valueDate
     */
    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    /**
     * 获取 repRegistOrg
     * 
     * @return repRegistOrg.
     */
    public String getRepRegistOrg() {
        return repRegistOrg;
    }

    /**
     * 设置 repRegistOrg
     * 
     * @param repRegistOrg repRegistOrg
     */
    public void setRepRegistOrg(String repRegistOrg) {
        this.repRegistOrg = repRegistOrg;
    }

    public String getIsSecBondStr() {
        if (getIsSecBond() != null) {
            return SysDataUtil.getNameFromDataDict(BussinessDictCode.是否, this.getIsSecBond());
        }
        else {
            return null;
        }
    }

    public String getisSusStr() {
        if (getIsSus() != null) {
            return SysDataUtil.getNameFromDataDict(BussinessDictCode.是否, this.getIsSus());
        }
        else {
            return null;
        }
    }

    public String getFreezeFlagStr() {
        if (getFreezeFlag() != null) {
            return SysDataUtil.getNameFromDataDict(BussinessDictCode.是否, this.getFreezeFlag());
        }
        else {
            return null;
        }
    }

    public String getRepRegistOrgStr() {
        /*if (getRepRegistOrg() != null) {
            return SysDataUtil.getNameFromDataDict(BussinessDictCode.中债_登记托管机构,
                this.getRepRegistOrg());
        }*/
    	if(getRepRegistOrg() != null){
    		return this.getRepRegistOrg();
    	}
        else {
            return null;
        }
    }

    /**
     * 获取 quoteId
     * 
     * @return quoteId.
     */
    public String getQuoteId() {
        return quoteId;
    }

    /**
     * 设置 quoteId
     * 
     * @param quoteId quoteId
     */
    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public BigDecimal getPledgeRatio() {
        return pledgeRatio;
    }

    public void setPledgeRatio(BigDecimal pledgeRatio) {
        this.pledgeRatio = pledgeRatio;
    }

    public String getFirstNetPrice() {
        return firstNetPrice;
    }

    public void setFirstNetPrice(String firstNetPrice) {
        this.firstNetPrice = firstNetPrice;
    }
}
