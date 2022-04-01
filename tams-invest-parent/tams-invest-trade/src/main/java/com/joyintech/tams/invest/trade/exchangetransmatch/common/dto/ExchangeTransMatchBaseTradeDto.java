package com.joyintech.tams.invest.trade.exchangetransmatch.common.dto;

import com.joyintech.tams.common.base.entity.FinDealPledge;
import com.joyintech.tams.common.base.entity.FinProductType;
import com.joyintech.tams.common.base.entity.FinTradeProduct;
import com.joyintech.tams.invest.common.dto.BaseTradeProductDealDto;

import java.util.List;

public class ExchangeTransMatchBaseTradeDto extends BaseTradeProductDealDto {
    //交易类金融产品list
    private FinTradeProduct finTradeProduct;

    //金融产品类型
    private FinProductType finProductType;

    //回购质押品list
    private List<FinDealPledge> finDealPledgeList;

    public FinTradeProduct getFinTradeProduct() {
        return finTradeProduct;
    }

    public void setFinTradeProduct(FinTradeProduct finTradeProduct) {
        this.finTradeProduct = finTradeProduct;
    }

    public FinProductType getFinProductType() {
        return finProductType;
    }

    public void setFinProductType(FinProductType finProductType) {
        this.finProductType = finProductType;
    }

    public List<FinDealPledge> getFinDealPledgeList() {
        return finDealPledgeList;
    }

    public void setFinDealPledgeList(List<FinDealPledge> finDealPledgeList) {
        this.finDealPledgeList = finDealPledgeList;
    }
}
