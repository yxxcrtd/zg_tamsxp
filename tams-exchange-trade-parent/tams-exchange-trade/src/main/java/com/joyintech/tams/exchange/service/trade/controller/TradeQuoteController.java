package com.joyintech.tams.exchange.service.trade.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.exchange.service.quote.service.QuoteService;
import com.joyintech.tams.exchange.common.bean.IntegratedBulkCloseQuoteBean;
import com.joyintech.tams.exchange.common.bean.IntegratedBulkCloseQuoteQryBean;
import com.joyintech.tams.exchange.common.bean.IntegratedBulkQuoteBean;
import com.joyintech.tams.exchange.common.bean.IntegratedBulkQuoteQryBean;
import com.joyintech.tams.exchange.common.bean.IntegratedFixedIncomeQuoteBean;
import com.joyintech.tams.exchange.common.bean.IntegratedFixedIncomeQuoteQryBean;
import com.joyintech.tams.exchange.common.bean.IntegratedRepurchaseQuoteBean;
import com.joyintech.tams.exchange.common.bean.IntegratedRepurchaseQuoteQryBean;
import com.joyintech.tams.exchange.common.bean.TickBean;
import com.joyintech.tams.exchange.common.constant.EntrustBsProp;
import com.joyintech.tams.exchange.common.db.domain.Instruction;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.common.request.QueryTickRequest;
import com.joyintech.tams.exchange.common.utils.EntrustPropUtils;
import com.joyintech.tams.exchange.datacommon.db.service.InstructionService;
import com.joyintech.tams.exchange.service.trade.management.service.IntegratedQuoteManagement;
import com.joyintech.tams.exchange.util.MasterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trade/joyin")
@Api(tags = "场内交易-交易行情查询接口")
public class TradeQuoteController {
    private static final Logger logger = LoggerFactory.getLogger(TradeQuoteController.class);

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private IntegratedQuoteManagement integratedQuoteManagement;
    @Autowired
    private InstructionService instructionService;

    @ApiOperation(value = "查询行情tick信息", notes = "查询行情tick信息")
    @RequestMapping(value = {"/quote/tick"}, method = RequestMethod.POST)
    public Message<TickBean> queryTick(@RequestBody @Validated QueryTickRequest queryTickRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(queryTickRequest);
    	}
        return new Message<TickBean>(quoteService.getTick(queryTickRequest.getStockCode()));
    }

    @ApiOperation(value = "大宗行情查询", notes = "大宗行情查询")
    @RequestMapping(value = "/entrust/integrated/bulk_quote", method = RequestMethod.POST)
    public Message<List<IntegratedBulkQuoteBean>> queryIntegratedBulkQuote(@RequestBody @Validated IntegratedBulkQuoteQryBean integratedBulkQuoteQryBean) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(integratedBulkQuoteQryBean);
    	}
        Instruction instruction = instructionService.findById(integratedBulkQuoteQryBean.getInstructionId());
        String entrustProp = EntrustPropUtils.getEntrustProp(instruction, integratedBulkQuoteQryBean.getEntrustProp());
        integratedBulkQuoteQryBean.setEntrustProp(entrustProp);
        integratedBulkQuoteQryBean.setEntrustBs(EntrustBsProp.getEntrustBs(instruction.getPs()));
        if (StringUtils.isEmpty(integratedBulkQuoteQryBean.getStockCode())) {
            integratedBulkQuoteQryBean.setStockCode(instruction.getFinprodMarketId());
        }
        if (StringUtils.isEmpty(integratedBulkQuoteQryBean.getAccountId())) {
            integratedBulkQuoteQryBean.setAccountId(instruction.getAcctNo());
        }
        return new Message<List<IntegratedBulkQuoteBean>>(integratedQuoteManagement.queryIntegratedBulkQuote(integratedBulkQuoteQryBean));
    }

    @ApiOperation(value = "盘后大宗行情查询", notes = "盘后大宗行情查询")
    @RequestMapping(value = "/entrust/integrated/bulk_close_quote", method = RequestMethod.POST)
    public Message<List<IntegratedBulkCloseQuoteBean>> queryIntegratedBulkCloseQuote(@RequestBody @Validated IntegratedBulkCloseQuoteQryBean integratedBulkCloseQuoteQryBean) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(integratedBulkCloseQuoteQryBean);
    	}
        return new Message<List<IntegratedBulkCloseQuoteBean>>(integratedQuoteManagement.queryIntegratedBulkCloseQuote(integratedBulkCloseQuoteQryBean));
    }

    @ApiOperation(value = "质押式协议回购行情查询", notes = "质押式协议回购行情查询")
    @RequestMapping(value = "/entrust/integrated/repurchase_quote", method = RequestMethod.POST)
    public Message<List<IntegratedRepurchaseQuoteBean>> queryIntegratedRepurchaseQuote(@RequestBody @Validated IntegratedRepurchaseQuoteQryBean integratedRepurchaseQuoteQryBean) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(integratedRepurchaseQuoteQryBean);
    	}
        Instruction instruction = instructionService.findById(integratedRepurchaseQuoteQryBean.getInstructionId());
        String entrustProp = EntrustPropUtils.getEntrustProp(instruction, integratedRepurchaseQuoteQryBean.getEntrustProp());
        integratedRepurchaseQuoteQryBean.setEntrustProp(entrustProp);
        if (StringUtils.isEmpty(integratedRepurchaseQuoteQryBean.getAccountId())) {
            integratedRepurchaseQuoteQryBean.setAccountId(instruction.getAcctNo());
        }
        return new Message<List<IntegratedRepurchaseQuoteBean>>(integratedQuoteManagement.queryIntegratedRepurchaseQuote(integratedRepurchaseQuoteQryBean));
    }

    @ApiOperation(value = "上海固收行情查询", notes = "上海固收行情查询")
    @RequestMapping(value = "/entrust/integrated/fixed_income_quote", method = RequestMethod.POST)
    public Message<List<IntegratedFixedIncomeQuoteBean>> queryIntegratedFixedIncomeQuote(@RequestBody @Validated IntegratedFixedIncomeQuoteQryBean integratedFixedIncomeQuoteQryBean) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(integratedFixedIncomeQuoteQryBean);
    	}
        return new Message<List<IntegratedFixedIncomeQuoteBean>>(integratedQuoteManagement.queryIntegratedFixedIncomeQuote(integratedFixedIncomeQuoteQryBean));
    }


}
