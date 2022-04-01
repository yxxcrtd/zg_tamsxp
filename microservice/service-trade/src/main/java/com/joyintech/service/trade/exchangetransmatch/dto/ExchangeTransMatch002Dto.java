package com.joyintech.service.trade.exchangetransmatch.dto;

import com.joyintech.background.component.common.pack.common.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.base.entity.SettleTransDataTotal;
import com.joyintech.tams.constant.DictCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ExchangeTransMatch002Dto extends SettleTransDataTotal implements ITableRow {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -1231102556224322743L;

    /**
     * 匹配状态
     */
    private String matchStatus;

    /**
     * 匹配方式
     */
    private String matchType;

    /**
     * 匹配进度
     */
    private BigDecimal matchProgress;

    /**
     * 资产简称
     */
    private String finprodAbbr;

    /**
     * 资产市场代码
     */
    private String finprodMarketId;

    /**
     * 剩余成交金额
     */
    private BigDecimal remainAmount;

    /**
     * 剩余成交数量
     */
    private BigDecimal remainNum;

    /**
     * 资金账号名称
     */
    private String acctName;

    /**
     * 证券账户名称
     */
    private String secAcctName;

    /**
     * 回购类型
     */
    private String repoType;

    /**
     * 转股价格
     */
    private String conPrice;

    /**
     * 转股数量
     */
    private String conAmt;

    /**
     * 债转股的股票资产代码
     */
    private String rFinMarketId;

    /**
     *债转股的股票金融产品代码
     */
    private String rFinprodId;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 委托交易list
     */
    private List<ExchangeTransMatch003Dto> entrustList;


    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public BigDecimal getMatchProgress() {
        return matchProgress;
    }

    public void setMatchProgress(BigDecimal matchProgress) {
        this.matchProgress = matchProgress;
    }

    public String getFinprodAbbr() {
        return finprodAbbr;
    }

    public void setFinprodAbbr(String finprodAbbr) {
        this.finprodAbbr = finprodAbbr;
    }

    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(BigDecimal remainNum) {
        this.remainNum = remainNum;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getSecAcctName() {
        return secAcctName;
    }

    public void setSecAcctName(String secAcctName) {
        this.secAcctName = secAcctName;
    }

    public List<ExchangeTransMatch003Dto> getEntrustList() {
        return entrustList;
    }

    public void setEntrustList(List<ExchangeTransMatch003Dto> entrustList) {
        this.entrustList = entrustList;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getRepoType() {
        return repoType;
    }

    public void setRepoType(String repoType) {
        this.repoType = repoType;
    }

    public String getConPrice() {
        return conPrice;
    }

    public void setConPrice(String conPrice) {
        this.conPrice = conPrice;
    }

    public String getConAmt() {
        return conAmt;
    }

    public void setConAmt(String conAmt) {
        this.conAmt = conAmt;
    }

    public String getrFinMarketId() {
        return rFinMarketId;
    }

    public void setrFinMarketId(String rFinMarketId) {
        this.rFinMarketId = rFinMarketId;
    }

    public String getrFinprodId() {
        return rFinprodId;
    }

    public void setrFinprodId(String rFinprodId) {
        this.rFinprodId = rFinprodId;
    }
}
