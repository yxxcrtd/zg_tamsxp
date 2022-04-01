package com.joyintech.tams.invest.trade.exchangetransmatch.common.service.bond;

import com.joyintech.framework.util.*;
import com.joyintech.tams.common.base.entity.TrdDealTotalAdd;
import com.joyintech.tams.common.base.entity.TrdFinProductDealTotal;
import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.invest.common.dto.StlPayOrdersDto;
import com.joyintech.tams.invest.common.transmatch.dto.ExchangeTransMatchCommonDto;
import com.joyintech.tams.invest.common.transmatch.dto.ExchangeTransMatchCommonEntrustDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 可转债申购交易
 */
@Service
public class ExchangeTransMatchBond2Service extends ExchangeTransMatchBondCommonService {

    @Override
    public TrdDealTotalAdd generateDealTotalAdd(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        TrdDealTotalAdd trdDealTotalAdd =  new TrdDealTotalAdd();
        BeanUtil.copyPropertiesIgnoreNull(trdDealTotalAdd, trdFinProductDealTotal);
        trdDealTotalAdd.setTotleTradeId(trdFinProductDealTotal.getTotleTradeId());
        trdDealTotalAdd.setPurchModle(DictCodeListExt2.申购方式_网上申购.getValue());
        trdDealTotalAdd.setPurchType(DictCodeListExt2.申购类型_普通申购.getValue());
        trdDealTotalAdd.setPurchDate(trdFinProductDealTotal.getTradeDate());
        trdDealTotalAdd.setPurCfmDate(DateUtil.addDay(trdFinProductDealTotal.getTradeDate(),2));
        trdDealTotalAdd.setPurPayDate(trdDealTotalAdd.getPurCfmDate());
        trdDealTotalAdd.setDepositAmt(BigDecimal.ZERO);
        trdDealTotalAdd.setPurAmt(trdFinProductDealTotal.getPrinAmt());
        trdDealTotalAdd.setEntrustId(entrustDto.getEntrustId());

        return trdDealTotalAdd;
    }

    /**
     * 可转债申购
     * @param
     * @return
     */
    @Override
    protected TrdProductDeal createDeal(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        // 交易字段设置共通
        TrdProductDeal trdProductDeal = createProductDeal(dealDto, entrustDto,trdFinProductDealTotal);
        // 单位净价
        trdProductDeal.setUnitCprice(new BigDecimal(100));
        // 单位利息
        trdProductDeal.setUnitInt(BigDecimal.ZERO);
        // 单位全价
        trdProductDeal.setUnitFprice(new BigDecimal(100));
        // 单位面值
        trdProductDeal.setParValue(new BigDecimal(100));
        // 净价总额，交易份额，交易本金，利息总额，全价总额，随交易支付费用，交易金额，未随交易支付费用，总交易费用
        calcFeeAmtPay(dealDto, entrustDto,trdProductDeal);
        // 交易方向
        trdProductDeal.setPs(DictCodeList.交易方向_申购.getValue());
        trdProductDeal.setTgBusiType(DictCodeListExt2.平安业务类型_保证金划款.getValue());

        return trdProductDeal;
    }

    @Override
    protected void setDealTotal(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto,TrdFinProductDealTotal trdFinProductDealTotal) {
        // 业务类型
        trdFinProductDealTotal.setBusiType(DictCodeListExt2.共通业务类型_可转债申购交易.getValue());
        // 净价总额
        trdFinProductDealTotal.setCpriceAmt(ArithmeticUtil.multiply(entrustDto.getMarchQty(),dealDto.getUnitCprice()));
        // 全价总额
        trdFinProductDealTotal.setFpriceAmt(ArithmeticUtil.multiply(entrustDto.getMarchQty(),dealDto.getUnitFprice()));
        // 交易本金
        trdFinProductDealTotal.setTradeAmt(ArithmeticUtil.subtract(trdFinProductDealTotal.getFpriceAmt(),entrustDto.getMarchFeeAmtPay()));
        // 利息总额
        trdFinProductDealTotal.setIntAmt(ArithmeticUtil.multiply(entrustDto.getMarchQty(),dealDto.getUnitInt()));
        // 交易本金
        trdFinProductDealTotal.setPrinAmt(entrustDto.getMarchQty().multiply(new BigDecimal(100)));
    }

    // GZYHTZJY-1001:债券的申购不处理持仓,只处理现金 modify by fuyongsheng 20220104 start
    @Override
    public List<StlPayOrdersDto> generateStlPayOrdersList(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdProductDeal trdProductDeal) {
        List<StlPayOrdersDto> stlPayOrdersList = new ArrayList<>();

        StlPayOrdersDto stlPayOsrdersDto = createStlPayOrders(dealDto, entrustDto, trdProductDeal);
        // 收付方向
        if(StringUtil.equals(DictCodeList.交易方向_买入.getValue(), dealDto.getPs())) {
            stlPayOsrdersDto.setPs(DictCodeListExt1.收付方向_支出.getValue());
        }
        else {
            stlPayOsrdersDto.setPs(DictCodeList.收付方向_收入.getValue());
        }
        stlPayOrdersList.add(stlPayOsrdersDto);

        return stlPayOrdersList;
    }
    // GZYHTZJY-1001:债券的申购不处理持仓,只处理现金 modify by fuyongsheng 20220104 end
}
