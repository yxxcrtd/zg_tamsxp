/*
 * 文件名：EtrExchangeTradeDto002.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.exchange.entrust.etrexchange.agreementrep.dto;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.systemtools.util.SysDatadictUtil;

import java.math.BigDecimal;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author lby
 * @version 1.0 2020年9月17日
 * @see EtrExchangeUnOpenQuota002Dto
 * @since 1.0
 */
@PageId("EtrExchangeUnOpenQuota002Dto")
public class EtrExchangeUnOpenQuota002Dto extends BaseEntity {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -8997549399769922323L;

    /**
     * 请求编号
     */
    private String reqOnId;
    /**
     * 请求类型
     */
    private String reqType;
    private String reqTypeStr;
    /**
     * 质押券代码
     */
    private String repSecId;
    /**
     * 质押券名称
     */
    private String pledgeStampName;
    /**
     * 原质押代码
     */
    private String oriRepSecId;
    /**
     * 质押数量
     */
    private BigDecimal repSecNum;
    /**
     * 质押券面总额合计
     */
    private BigDecimal pledgeStampBalance;

    /**
     * 折算比例
     */
    private BigDecimal conRatio;
    /**
     * 回购金额
     */
    private BigDecimal tradeAmt;
    /**
     * 回购利率
     */
    private BigDecimal yield;
    /**
     * 回购期限
     */
    private Integer term;
    /**
     * 实际占款天数
     */
    private Integer occupyDays;
    /**
     * 首次结算日
     */
    private String fristSettleDate;

    /**
     * 回购到期日
     */
    private String backDate;

    /**
     * 到期结算日
     */
    private String expireSettleDate;

    /**
     * 原质押券代码
     */
    private String origPledgeStampCode;

    /**
     * 原质押券名称
     */
    private String origPledgeStampName;

    /**
     * 回购利息
     */
    private BigDecimal intAmt;

    /**
     * 到期结算金额
     */
    private BigDecimal expireSettleAmt;
    /**
     * 成交编号
     */
    private String tradeId;
    /**
     * 发起方交易员代码
     */
    private String sponsorTraderCode;

    /**
     * 发起方交易商代码
     */
    private String sponsorDealerCode;
    /**
     * 本方交易员
     */
    private String traderCode;
    /**
     * 补充协议
     */
    private String addCla;

    /**
     * 资金账号
     */
    private String acctNo;

    private String redoType;

    /**
     * 交易场所
     */
    private String tradeMarket;

    /**
     * 交易场所
     */
    private String tradeMarketStr;

    public String getReqOnId() {
        return reqOnId;
    }

    public void setReqOnId(String reqOnId) {
        this.reqOnId = reqOnId;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getRepSecId() {
        return repSecId;
    }

    public void setRepSecId(String repSecId) {
        this.repSecId = repSecId;
    }

    public String getPledgeStampName() {
        return pledgeStampName;
    }

    public void setPledgeStampName(String pledgeStampName) {
        this.pledgeStampName = pledgeStampName;
    }

    public String getOriRepSecId() {
        return oriRepSecId;
    }

    public void setOriRepSecId(String oriRepSecId) {
        this.oriRepSecId = oriRepSecId;
    }

    public BigDecimal getRepSecNum() {
        return repSecNum;
    }

    public void setRepSecNum(BigDecimal repSecNum) {
        this.repSecNum = repSecNum;
    }

    public BigDecimal getPledgeStampBalance() {
        return pledgeStampBalance;
    }

    public void setPledgeStampBalance(BigDecimal pledgeStampBalance) {
        this.pledgeStampBalance = pledgeStampBalance;
    }

    public BigDecimal getConRatio() {
        return conRatio;
    }

    public void setConRatio(BigDecimal conRatio) {
        this.conRatio = conRatio;
    }

    public BigDecimal getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(BigDecimal tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    public BigDecimal getYield() {
        return yield;
    }

    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getOccupyDays() {
        return occupyDays;
    }

    public void setOccupyDays(Integer occupyDays) {
        this.occupyDays = occupyDays;
    }

    public String getFristSettleDate() {
        return fristSettleDate;
    }

    public void setFristSettleDate(String fristSettleDate) {
        this.fristSettleDate = fristSettleDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getExpireSettleDate() {
        return expireSettleDate;
    }

    public void setExpireSettleDate(String expireSettleDate) {
        this.expireSettleDate = expireSettleDate;
    }

    public String getOrigPledgeStampCode() {
        return origPledgeStampCode;
    }

    public void setOrigPledgeStampCode(String origPledgeStampCode) {
        this.origPledgeStampCode = origPledgeStampCode;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    public BigDecimal getExpireSettleAmt() {
        return expireSettleAmt;
    }

    public void setExpireSettleAmt(BigDecimal expireSettleAmt) {
        this.expireSettleAmt = expireSettleAmt;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getSponsorTraderCode() {
        return sponsorTraderCode;
    }

    public void setSponsorTraderCode(String sponsorTraderCode) {
        this.sponsorTraderCode = sponsorTraderCode;
    }

    public String getSponsorDealerCode() {
        return sponsorDealerCode;
    }

    public void setSponsorDealerCode(String sponsorDealerCode) {
        this.sponsorDealerCode = sponsorDealerCode;
    }

    public String getTraderCode() {
        return traderCode;
    }

    public void setTraderCode(String traderCode) {
        this.traderCode = traderCode;
    }

    public String getAddCla() {
        return addCla;
    }

    public void setAddCla(String addCla) {
        this.addCla = addCla;
    }

    public String getReqTypeStr() {
        return SysDatadictUtil.getNameFromDataDict(DictCode.交易方向,this.reqType);
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getRedoType() {
        return redoType;
    }

    public void setRedoType(String redoType) {
        this.redoType = redoType;
    }

    public String getRedoTypeStr() {
        if ("0".equals(redoType)){
            return "非三方续做";
        }else if ("1".equals(redoType)){
            return "三方续做";
        }else {
            return "";
        }
    }

    public String getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(String tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public String getTradeMarketStr() {
        return SysDatadictUtil.getNameFromDataDict(DictCode.交易场所, this.tradeMarket);
    }

    public String getOrigPledgeStampName() {
        return origPledgeStampName;
    }

    public void setOrigPledgeStampName(String origPledgeStampName) {
        this.origPledgeStampName = origPledgeStampName;
    }
}
