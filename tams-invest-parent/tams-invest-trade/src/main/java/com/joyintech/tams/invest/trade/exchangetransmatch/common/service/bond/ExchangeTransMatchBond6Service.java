package com.joyintech.tams.invest.trade.exchangetransmatch.common.service.bond;

import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.component.business.core.service.BaseGetBusinessId;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import com.joyintech.tams.invest.common.dto.StlPayOrdersDto;
import com.joyintech.tams.invest.common.dto.common.BussinessCodeParameter;
import com.joyintech.tams.invest.common.transmatch.dto.ExchangeTransMatchCommonDto;
import com.joyintech.tams.invest.common.transmatch.dto.ExchangeTransMatchCommonEntrustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 质押券出入库
 */
@Service
public class ExchangeTransMatchBond6Service extends ExchangeTransMatchBondCommonService {

    @Autowired(required = false)
    private BaseGetBusinessId baseGetBusinessId;

    /**
     * 质押券出入库
     * @param
     * @return
     */
    @Override
    protected void setDealTotal(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        // 业务类型
        trdFinProductDealTotal.setBusiType(DictCodeListExt3.共通业务类型_质押券出入库交易.getValue());
    }

    @Override
    public TrdDealTotalAdd generateDealTotalAdd(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        TrdDealTotalAdd trdDealTotalAdd =  new TrdDealTotalAdd();
        BeanUtil.copyPropertiesIgnoreNull(trdDealTotalAdd, trdFinProductDealTotal);
        trdDealTotalAdd.setTotleTradeId(trdFinProductDealTotal.getTotleTradeId());
        trdDealTotalAdd.setEntrustId(entrustDto.getEntrustId());
        return trdDealTotalAdd;
    }

    @Override
    protected TrdProductDeal createDeal(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto, TrdFinProductDealTotal trdFinProductDealTotal) {
        // 交易字段设置共通
        TrdProductDeal trdProductDeal = new TrdProductDeal();
        // 汇总交易编号
        BussinessCodeParameter codeParameter = new BussinessCodeParameter();
        codeParameter.setCommonBusinessType(DictCodeListExt3.共通业务类型_质押券出入库交易);
        trdProductDeal.setTradeId(baseGetBusinessId.buildBussinessCode(codeParameter));
        //金融产品代码
        trdProductDeal.setFinprodId(dealDto.getFinprodId());
        //金融产品类型（投管分类）
        trdProductDeal.setFinprodType2(dealDto.getFinprodType2());
        //金融产品类型
        trdProductDeal.setFinprodType(dealDto.getFinprodType());
        // 交易方向
        trdProductDeal.setPs(entrustDto.getPs());
        //投管业务类型
        trdProductDeal.setTgBusiType(DictCodeListExt3.平安业务类型_质押券出入库.getValue());
        //交易份额
        trdProductDeal.setShareAmt(entrustDto.getMarchQty());
        //交易金额
        trdProductDeal.setTradeAmt(entrustDto.getMarchAmt());
        //交易日期
        trdProductDeal.setTradeDate(dealDto.getTradeDate());
        //起息日
        trdProductDeal.setVdate(dealDto.getVdate());
        //到期日
        trdProductDeal.setMdate(dealDto.getMdate());
        //清算日
        trdProductDeal.setSettleDate(dealDto.getTradeDate());
        // 分支序号
        trdProductDeal.setBranch(0L);
        // 交易状态
        trdProductDeal.setTradeStatus(BussinessDictCodeList.交易状态_初始.getValue());
        // 是否已撤销
        trdProductDeal.setIsCancel(DictCodeList.是否_否.getValue());
        // 证券管理户代码
        trdProductDeal.setSecManageAcctId(dealDto.getSecAcctId());
        // 前置交易编号
        trdProductDeal.setbTradeId(null);
        // 是否外部交易
        trdProductDeal.setIsOutTrade(DictCodeList.是否_是.getValue());

        // 交易场所
        trdProductDeal.setTradeMarket(dealDto.getTradeMarket());

        if(StringUtil.equals(dealDto.getTradeMarket(), DictCodeListExt1.交易场所_上海证券交易所.getValue())) {
            trdProductDeal.setCounterId(BussinessConstant.COUNTER_PARTY_ID_SHANGHAI);
        }
        else if(StringUtil.equals(dealDto.getTradeMarket(), DictCodeListExt1.交易场所_深圳证券交易所.getValue())){
            trdProductDeal.setCounterId(BussinessConstant.COUNTER_PARTY_ID_SHENZHEN);
        }
        // 交易平台
        trdProductDeal.setTradePlat(dealDto.getTradePlat());
        // 交易市场
        trdProductDeal.setTradeMarketMode(dealDto.getTradeMarketMode());

        trdProductDeal.setTotleTradeId(trdFinProductDealTotal.getTotleTradeId());

        trdProductDeal.setRemark("数据清算自动处理");

        // GNS-922: modify by fuyongsheng 20220220 start
        trdProductDeal.setCcy(dealDto.getCcy());
        // GNS-922: modify by fuyongsheng 20220220 end

        return trdProductDeal;
    }

    //生成标准券入出库
    public TrdStasecFlow generateTrdStasecFlow(ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto,TrdProductDeal trdProductDeal) {
        TrdStasecFlow trdStasecFlow = new TrdStasecFlow();
        trdStasecFlow.setSeqno(trdProductDeal.getTradeId());
        trdStasecFlow.setPortfolioId(entrustDto.getPortfolioId());
        trdStasecFlow.setCdate(trdProductDeal.getTradeDate());
        if (StringUtil.equals(trdProductDeal.getPs(), DictCodeListExt3.交易方向_质押券入库.getValue())) {
            trdStasecFlow.setPs(DictCodeList.出入库方向_入库.getValue());
            trdStasecFlow.setRemark("质押券入库");
        }
        else {
            trdStasecFlow.setPs(DictCodeList.出入库方向_出库.getValue());
            trdStasecFlow.setRemark("质押券出库");
        }
        trdStasecFlow.setSecAcctId(dealDto.getSecAcctId());

        return trdStasecFlow;
    }

    //生成标准券入出库明细
    public TrdStasecFlowDetail generateTrdStasecFlowDetail (ExchangeTransMatchCommonDto dealDto, ExchangeTransMatchCommonEntrustDto entrustDto,TrdProductDeal trdProductDeal) {
        TrdStasecFlowDetail trdStasecFlowDetail = new TrdStasecFlowDetail();
        trdStasecFlowDetail.setSeqno(trdProductDeal.getTradeId());
        trdStasecFlowDetail.setBranch(0L);
        trdStasecFlowDetail.setFinprodId(dealDto.getFinprodId());
        trdStasecFlowDetail.setAmt(trdProductDeal.getShareAmt());
        return trdStasecFlowDetail;
    }

}
