package com.joyintech.tams.invest.trade.exchangetransmatch.manualmatch.dto;

import com.joyintech.background.component.common.pack.common.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.base.entity.SettleTransDataTotal;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;

import java.util.Date;
import java.util.List;

public class ExchangeTransMatch004Dto extends SettleTransDataTotal implements ITableRow {
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 4449595953418685312L;

    /**
     * 匹配方式
     */
    private String matchType;

    /**
     * 业务编号
     */
    private String busiCode;

    /**
     * 组合代码
     */
    private String portfolioName;

    /**
     * 资产市场代码
     */
    private String finprodMarketId;

    /**
     * 交易数量
     */
    private String tradeNum;

    /**
     * 交易价格
     */
    private String tradePrice;

    private List<ExchangeTransMatch004Dto> selectItem;

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(String tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradePlatStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.交易平台, this.getTradePlat());
    }

    public String getMatchTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.下行交易匹配类型, this.getMatchType());
    }

    public String getTradeMarketStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.交易场所, this.getTradeMarket());
    }

    public String getFinprodType2Str() {
        return SysDataUtil.getNameFromDataDict(DictCode.金融产品类型_投管用, this.getFinprodType2());
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
        }else{
            return SysDataUtil.getNameFromDataDict(DictCode.交易方向, this.getPs());
        }
        // GZYHTZJY-716:成交明细汇总表回购方向与指令方向不一致,需特殊处理 fuyongsheng 20211206 edit end
    }

    public String getCcyStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.币种, this.getCcy());
    }

    public List<ExchangeTransMatch004Dto> getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(List<ExchangeTransMatch004Dto> selectItem) {
        this.selectItem = selectItem;
    }
}