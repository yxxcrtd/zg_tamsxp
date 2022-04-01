package com.joyintech.tams.invest.trade.exchangetransmatch.manualmatch.dto;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.tams.common.base.entity.SettleTransDataTotal;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.trade.exchangetransmatch.manualmatch.validator.ExchangeTransMatchValidator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ValidLogic(targetClass = ExchangeTransMatchValidator.class)
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
     * 操作类型
     */
    private String operateType;

    /**
     * 元素ID
     */
    private String elementId;

    /**
     * 组合名称
     */
    private String portfolioName;

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
     * 委托交易list
     */
    private List<ExchangeTransMatch003Dto> entrustList;


    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getMatchStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.下行交易匹配状态, this.getMatchStatus());
    }

    public BigDecimal getMatchProgress() {
        return matchProgress;
    }

    public void setMatchProgress(BigDecimal matchProgress) {
        this.matchProgress = matchProgress;
    }

    public String getMatchProgressStr() {
        if(StringUtil.isNotEmpty(this.getMatchProgress())) {
            return ArithmeticUtil.multiply(this.getMatchProgress(),new BigDecimal(100))+"%";
        }
        else {
            return "0";
        }
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
        if(DictCodeListExt3.交易方向_债转股.getValue().equals(this.getPs())){
            return BigDecimal.ZERO;
        }
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

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getTradeMarketStr() {
        return  SysDataUtil.getNameFromDataDict(DictCode.交易场所, this.getTradeMarket());
    }

    public String getFinprodType2Str() {
        return  SysDataUtil.getNameFromDataDict(DictCode.金融产品类型_投管用, this.getFinprodType2());
    }

    public String getTradePlatStr() {
        return  SysDataUtil.getNameFromDataDict(DictCode.交易平台, this.getTradePlat());
    }

    public String getPsStr() {
        // GZYHTZJY-716:成交明细汇总表回购方向与指令方向不一致,需特殊处理 fuyongsheng 20211206 edit start
        if(DictCodeListExt1.金融产品类型_投管用_债券质押式正回购.getValue().equals(this.getFinprodType2())){
            if(StringUtil.equals(this.getPs(), DictCodeList.交易方向_买入.getValue())) {
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, DictCodeListExt3.交易方向_正回购.getValue());
            }
            else if(StringUtil.equals(this.getPs(), DictCodeList.交易方向_到期.getValue())){
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, DictCodeListExt3.交易方向_到期购回.getValue());
            }
            else if(StringUtil.equals(this.getPs(), DictCodeListExt3.交易方向_投资端_提前终止.getValue())){
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, DictCodeListExt3.交易方向_提前购回.getValue());
            }
            else {
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, this.getPs());
            }

        }else if(DictCodeListExt1.金融产品类型_投管用_债券质押式逆回购.getValue().equals(this.getFinprodType2())){

            if(StringUtil.equals(this.getPs(), DictCodeList.交易方向_买入.getValue())) {
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, DictCodeListExt3.交易方向_逆回购.getValue());
            }
            else if(StringUtil.equals(this.getPs(), DictCodeList.交易方向_到期.getValue())){
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, DictCodeListExt3.交易方向_到期购回.getValue());
            }
            else if(StringUtil.equals(this.getPs(), DictCodeListExt3.交易方向_投资端_提前终止.getValue())){
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, DictCodeListExt3.交易方向_提前购回.getValue());
            }
            else {
                return SysDataUtil.getNameFromDataDict(DictCode.交易方向, this.getPs());
            }
        }
        else{
            return SysDataUtil.getNameFromDataDict(DictCode.交易方向, this.getPs());
        }
        // GZYHTZJY-716:成交明细汇总表回购方向与指令方向不一致,需特殊处理 fuyongsheng 20211206 edit end
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.下行交易匹配类型, this.getMatchType());
    }

    /**
     * 取得 交易日期
     * @return 交易日期
     */
    public String getTradeDateStr() {
        return DateUtil.format(this.getTradeDate());
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

