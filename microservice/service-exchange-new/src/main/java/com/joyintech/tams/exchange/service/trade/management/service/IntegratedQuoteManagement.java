package com.joyintech.tams.exchange.service.trade.management.service;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.exchange.common.bean.*;
import com.joyintech.tams.exchange.common.constant.EntrustProp;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.service.trade.processor.others.TerminalHelper;
import com.joyintech.tams.exchange.service.trade.scheduled.quote.QuoteJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName IntegratedQuoteManagement
 * @Description 综合交易行情查询
 * @Author liujianping
 * @Date 2020/9/21
 * @Version
 **/
@Component
public class IntegratedQuoteManagement {
    @Autowired
    private TerminalHelper terminalHelper;
    @Autowired
    private QuoteJob quoteJob;

    public ResultBean queryIntegratedBulkQuote(String stockCode, String entrustProp, String entrustBs, String accountId, int pageSize) {
        List<IntegratedBulkQuoteBean> integratedBulkQuotes = terminalHelper.queryIntegratedBulkQuote(stockCode, entrustProp, entrustBs, accountId, pageSize);
        return new ResultBean(ErrorCode.SUCCESS, integratedBulkQuotes);
    }

    public ResultBean queryIntegratedBulkCloseQuote(String stockCode, String exchangeType, String accountId, int pageSize) {
        List<IntegratedBulkCloseQuoteBean> integratedBulkCloseQuoteBeans = terminalHelper.queryIntegratedBulkCloseQuote(stockCode, exchangeType, accountId, pageSize);
        return new ResultBean(ErrorCode.SUCCESS, integratedBulkCloseQuoteBeans);
    }

    public ResultBean queryIntegratedRepurchaseQuote(int actionIn, String entrustProp, String accountId, int pageSize) {
        // modify by etliu 20211228 start
        // GZYHTZJY-817:协议回购业务，接口接入了非公开行情信息，协议回购指令非公开行情tab页查询不到
//        if (!"".equals(entrustProp)
//                && !EntrustProp.SH_REPURCHASE_TRADE.value.equals(entrustProp)
//                && !EntrustProp.SH_REPURCHASE_RENEWAL.value.equals(entrustProp)
//                && !EntrustProp.SH_REPURCHASE_RELEASE.value.equals(entrustProp)
//                && !EntrustProp.SH_REPURCHASE_EXCHANGE.value.equals(entrustProp)
//                && !EntrustProp.SH_REPURCHASE_TERMINATE.value.equals(entrustProp)
//                && !EntrustProp.SH_REPURCHASE_EXPIRE.value.equals(entrustProp)
//                && !EntrustProp.SH_REPURCHASE_INTENTION.value.equals(entrustProp)) {
//            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR, null);
//        }
//        List<IntegratedRepurchaseQuoteBean> integratedRepurchaseQuoteBeans = terminalHelper.queryIntegratedRepurchaseQuote(actionIn, entrustProp, accountId, pageSize);
        List<IntegratedRepurchaseQuoteBean> integratedRepurchaseQuoteBeans = new ArrayList<>();
        if (StringUtil.isBlank(entrustProp)) {
            integratedRepurchaseQuoteBeans.addAll(terminalHelper.queryIntegratedRepurchaseQuote(actionIn, EntrustProp.SH_REPURCHASE_TRADE.value, accountId, pageSize));
            integratedRepurchaseQuoteBeans.addAll(terminalHelper.queryIntegratedRepurchaseQuote(actionIn, EntrustProp.SH_REPURCHASE_RENEWAL.value, accountId, pageSize));
            integratedRepurchaseQuoteBeans.addAll(terminalHelper.queryIntegratedRepurchaseQuote(actionIn, EntrustProp.SH_REPURCHASE_RELEASE.value, accountId, pageSize));
            integratedRepurchaseQuoteBeans.addAll(terminalHelper.queryIntegratedRepurchaseQuote(actionIn, EntrustProp.SH_REPURCHASE_EXCHANGE.value, accountId, pageSize));
            integratedRepurchaseQuoteBeans.addAll(terminalHelper.queryIntegratedRepurchaseQuote(actionIn, EntrustProp.SH_REPURCHASE_TERMINATE.value, accountId, pageSize));
            integratedRepurchaseQuoteBeans.addAll(terminalHelper.queryIntegratedRepurchaseQuote(actionIn, EntrustProp.SH_REPURCHASE_EXPIRE.value, accountId, pageSize));
            integratedRepurchaseQuoteBeans.addAll(terminalHelper.queryIntegratedRepurchaseQuote(actionIn, EntrustProp.SH_REPURCHASE_INTENTION.value, accountId, pageSize));
        }
        else {
            integratedRepurchaseQuoteBeans = terminalHelper.queryIntegratedRepurchaseQuote(actionIn, entrustProp, accountId, pageSize);
        }
        // modify by etliu 20211228 end
        return new ResultBean(ErrorCode.SUCCESS, integratedRepurchaseQuoteBeans);
    }

    public ResultBean queryIntegratedFixedIncomeQuote(String stockCode, String exchangeType, String accountId, int pageSize) {
        List<IntegratedFixedIncomeQuoteBean> integratedFixedIncomeBeans = terminalHelper.queryIntegratedFixedIncomeQuote(stockCode, exchangeType, accountId, pageSize);
        return new ResultBean(ErrorCode.SUCCESS, integratedFixedIncomeBeans);
    }

    public ResultBean addIntegratedRepurchaseQuote(IntegratedRepurchaseQuoteBean repurchaseQuoteBean) {
        if (Objects.nonNull(repurchaseQuoteBean)) {
            quoteJob.addIntegratedRepurchaseQuote(repurchaseQuoteBean);
            return new ResultBean(ErrorCode.SUCCESS);
        } else {
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
        }
    }
}


