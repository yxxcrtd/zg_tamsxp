package com.joyintech.tams.invest.trade.exchangetransmatch.common.service.bond;

import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.FinProductMapper;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.dto.StlPayOrdersDto;
import com.joyintech.tams.invest.common.dto.common.TrdProductDealDto;
import com.joyintech.tams.invest.common.transmatch.dto.ExchangeTransMatchCommonDto;
import com.joyintech.tams.invest.common.transmatch.dto.ExchangeTransMatchCommonEntrustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 债转股
 */
@Service
public class ExchangeTransMatchBond4Service extends ExchangeTransMatchBondCommonService {

    @Autowired
    private FinProductMapper finProductMapper;
    /**
     * 债券交易处理
     * @param
     * @return
     */
    @Override
    protected void setDealTotal(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        // 业务类型
        trdFinProductDealTotal.setBusiType(DictCodeList.共通业务类型_债券类资产交易.getValue());
        trdFinProductDealTotal.setTgBusiType(DictCodeListExt3.平安业务类型_债转股.getValue());
//        // 债转股的零股折算现金赋值 add by fuyongsheng 20220116 start
//        trdFinProductDealTotal.setTradeAmt(dealDto.getFpriceAmt());
//        // 债转股的零股折算现金赋值 add by fuyongsheng 20220116 end
    }

    @Override
    public List<StlPayOrdersDto> generateStlPayOrdersList(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdProductDeal trdProductDeal) {
        List<StlPayOrdersDto> stlPayOrdersList = new ArrayList<>();

        StlPayOrdersDto stlPayOrdersDto = createStlPayOrders(dealDto, entrustDto, trdProductDeal);
        // 收付方向
        stlPayOrdersDto.setPs(DictCodeList.收付方向_收入.getValue());;
        stlPayOrdersList.add(stlPayOrdersDto);

        return stlPayOrdersList;
    }

    @Override
    protected TrdProductDeal createDeal(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        // 交易字段设置共通
        TrdProductDeal trdProductDeal = createProductDeal(dealDto, entrustDto,trdFinProductDealTotal);
        trdProductDeal.setSettleSpeed(dealDto.getSettleSpeed());
        // 单位净价
        trdProductDeal.setUnitCprice(dealDto.getUnitCprice());
        // 单位利息
        trdProductDeal.setUnitInt(dealDto.getUnitInt());
        // 单位全价
        trdProductDeal.setUnitFprice(dealDto.getUnitFprice());
        // 单位面值
        trdProductDeal.setParValue(null);
        // 净价总额，交易份额，交易本金，利息总额，全价总额，随交易支付费用，交易金额，未随交易支付费用，总交易费用
        calcFeeAmtPay(dealDto, entrustDto,trdProductDeal);
        // 交易方向
        trdProductDeal.setPs(entrustDto.getPs());
        // 到期收益率和行权收益率
        this.calc(trdProductDeal);

        trdProductDeal.setTgBusiType( DictCodeListExt3.平安业务类型_债转股.getValue());

        // 到期收益率
        trdFinProductDealTotal.setYield(trdProductDeal.getYield());
        // 行权收益率
        trdFinProductDealTotal.setExerYield(trdProductDeal.getExerYield());

        return trdProductDeal;
    }

    @Override
    public TrdDealTotalAdd generateDealTotalAdd(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        TrdDealTotalAdd trdDealTotalAdd = new TrdDealTotalAdd();
        trdDealTotalAdd.setTotleTradeId(trdFinProductDealTotal.getTotleTradeId());
        trdDealTotalAdd.setEntrustId(entrustDto.getEntrustId());

        // 零星股数
//        trdDealTotalAdd.setSpoShareNum(x.getSpoShareNum());

        return trdDealTotalAdd;
    }

    @Override
    public List<TrdFinProductBarterTotal> generateDealBarterTotal(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal){
        List<TrdFinProductBarterTotal> trdFinProductBarterTotalList = new ArrayList<>();
        TrdFinProductBarterTotal trdFinProductBarterTotal = new TrdFinProductBarterTotal();
        SettleTransDataTotalAdd settleTransDataTotalAdd = dealDto.getSettleTransDataTotalAdd();

        trdFinProductBarterTotal.setTradeId(trdFinProductDealTotal.getTotleTradeId());
        trdFinProductBarterTotal.setInvAim(entrustDto.getInvAim());
        trdFinProductBarterTotal.setBranch(0L);
        // 金融产品代码
        trdFinProductBarterTotal.setFinprodId(settleTransDataTotalAdd.getrFinprodId());
        trdFinProductBarterTotal.setFinprodType(DictCodeListExt1.金融产品类型_投管用_股票.getValue());
        // 转股价格
        trdFinProductBarterTotal.setUnitCprice(settleTransDataTotalAdd.getConPrice());
        // 转股数
        trdFinProductBarterTotal.setShareAmt(settleTransDataTotalAdd.getConAmt());
        trdFinProductBarterTotalList.add(trdFinProductBarterTotal);
        return trdFinProductBarterTotalList;
    }


    @Override
    protected List<TrdFinProductBarter> generateDealBarterList(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdProductDeal trdProductDeal) {
        List<TrdFinProductBarter> trdFinProductBarterList = new ArrayList<>();

        TrdFinProductBarter trdFinProductBarter = new TrdFinProductBarter();
        SettleTransDataTotalAdd settleTransDataTotalAdd = dealDto.getSettleTransDataTotalAdd();
        trdFinProductBarter.setTradeId(trdProductDeal.getTradeId());
        trdFinProductBarter.setInvAim(entrustDto.getInvAim());
        trdFinProductBarter.setBranch(0L);
        // 金融产品代码
        trdFinProductBarter.setFinprodId(settleTransDataTotalAdd.getrFinprodId());
        // 金融产品类型
        trdFinProductBarter.setFinprodType(DictCodeListExt1.金融产品类型_投管用_股票.getValue());
        // 转股价格
        trdFinProductBarter.setUnitCprice(settleTransDataTotalAdd.getConPrice());
        // 转股数
        trdFinProductBarter.setShareAmt(settleTransDataTotalAdd.getConAmt());
        trdFinProductBarterList.add(trdFinProductBarter);

        return trdFinProductBarterList;
    }
}
