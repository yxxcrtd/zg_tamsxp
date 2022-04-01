package com.joyintech.tams.exchange.terminal.trade.mock;

import com.joyintech.tams.exchange.common.bean.IntegratedBulkCloseQuoteBean;
import com.joyintech.tams.exchange.common.bean.IntegratedBulkQuoteBean;
import com.joyintech.tams.exchange.common.bean.IntegratedFixedIncomeQuoteBean;
import com.joyintech.tams.exchange.common.bean.IntegratedQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MockConverter {
    private static final Logger logger = LoggerFactory.getLogger(MockConverter.class);

    public static List<IntegratedBulkQuoteBean> convertIntegratedBulkQuoteBeans(List<IntegratedQuote> integratedQuotes) {
        List<IntegratedBulkQuoteBean> integratedBulkQuoteBeans = new ArrayList<>();
        integratedQuotes.forEach(integratedQuote -> {
            IntegratedBulkQuoteBean integratedBulkQuoteBean = new IntegratedBulkQuoteBean();
            integratedBulkQuoteBean.setErrorCode(integratedQuote.getErrorCode());
            integratedBulkQuoteBean.setErrorMsg(integratedQuote.getErrorMsg());
            integratedBulkQuoteBean.setOrderNo(integratedQuote.getOrderNo());
            integratedBulkQuoteBean.setSeatNo(integratedQuote.getPropSeatNo());
            integratedBulkQuoteBean.setEntrustProp(integratedQuote.getEntrustProp());
            integratedBulkQuoteBean.setEntrustBs(integratedQuote.getEntrustBs());
            integratedBulkQuoteBean.setStockCode(integratedQuote.getStockCode());
            //integratedBulkQuoteBean.setAssetType(integratedQuote.getA());
            integratedBulkQuoteBean.setStockName(integratedQuote.getStockName());
            integratedBulkQuoteBean.setEntrustPrice(integratedQuote.getEntrustPrice());
            integratedBulkQuoteBean.setEntrustAmount(integratedQuote.getEntrustVolume());
            //integratedBulkQuoteBean.setBusinessAmount(integratedQuote.get);
            integratedBulkQuoteBean.setCompactTerm(integratedQuote.getCompactTerm());
            integratedBulkQuoteBean.setRelationName(integratedQuote.getLiaisonName());
            integratedBulkQuoteBean.setRelationTel(integratedQuote.getLiaisonTel());
            integratedBulkQuoteBean.setCbpconferId(integratedQuote.getOrigCbpconferId());
            integratedBulkQuoteBean.setReportTime(integratedQuote.getReportTime());
            integratedBulkQuoteBean.setPositionStr(integratedQuote.getPosstr());
            integratedBulkQuoteBean.setRemark(integratedQuote.getRemark());
            integratedBulkQuoteBeans.add(integratedBulkQuoteBean);
        });
        return integratedBulkQuoteBeans;
    }

    public static List<IntegratedBulkCloseQuoteBean> convertIntegratedBulkCloseQuoteBeans(List<IntegratedQuote> integratedQuotes) {
        List<IntegratedBulkCloseQuoteBean> integratedBulkQuoteBeans = new ArrayList<>();
        integratedQuotes.forEach(integratedQuote -> {
            IntegratedBulkCloseQuoteBean item = new IntegratedBulkCloseQuoteBean();
            item.setErrorCode(integratedQuote.getErrorCode());
            item.setErrorMsg(integratedQuote.getErrorMsg());
            item.setStockCode(integratedQuote.getStockCode());
            // item.setExchangeType(integratedQuote.get);
            item.setStockName(integratedQuote.getStockName());
            item.setBusinessBalance(integratedQuote.getTradeBalance());
            //item.setBusinessAmount(integratedQuote.getTR);
            item.setClosingPrice(integratedQuote.getEntrustPrice());
            item.setBuyAmount1(integratedQuote.getBuyRemainQuantity1());
            item.setSaleAmount1(integratedQuote.getSellRemainQuantity1());
            item.setWeightavgPrice(integratedQuote.getWeightavgPrice());
            item.setBuyAmount2(integratedQuote.getBuyRemainQuantity2());
            item.setSaleAmount2(integratedQuote.getSellRemainQuantity2());

            integratedBulkQuoteBeans.add(item);
        });
        return integratedBulkQuoteBeans;
    }

    public static List<IntegratedFixedIncomeQuoteBean> convertIntegratedFixedIncomeQuoteBeans(List<IntegratedQuote> integratedQuotes) {
        List<IntegratedFixedIncomeQuoteBean> integratedFixedIncomeQuoteBeans = new ArrayList<>();
        integratedQuotes.forEach(integratedQuote -> {
            IntegratedFixedIncomeQuoteBean item = new IntegratedFixedIncomeQuoteBean();
            item.setErrorCode(integratedQuote.getErrorCode());
            item.setErrorMsg(integratedQuote.getErrorMsg());
            item.setStockCode(integratedQuote.getStockCode());
            item.setStockName(integratedQuote.getStockName());

            item.setOrderNo(integratedQuote.getOrderNo());
            item.setAgencyName(integratedQuote.getAgencyNo());
            item.setEntrustBs(integratedQuote.getEntrustBs());
            item.setEntrustPrice(integratedQuote.getEntrustPrice());
            item.setFullPrice(integratedQuote.getFullPrice());
            item.setEntrustVolume(integratedQuote.getEntrustVolume());
            item.setExpireIncomeRate(integratedQuote.getExpireIncomeRate());
            item.setInterest(integratedQuote.getBackInterest());
            //item.setReportTime(Integer.valueOf(integratedQuote.getReportTime()).intValue());
            integratedFixedIncomeQuoteBeans.add(item);
        });
        return integratedFixedIncomeQuoteBeans;
    }


}
