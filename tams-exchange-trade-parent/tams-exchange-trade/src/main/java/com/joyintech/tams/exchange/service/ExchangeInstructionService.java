package com.joyintech.tams.exchange.service;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.joyintech.framework.base.BaseService;
import com.joyintech.tams.exchange.common.bean.*;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.common.microservice.ServiceApplication;
import com.joyintech.tams.exchange.common.microservice.TradeServiceURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ExchangeInstructionService
 * @Description TODO
 * @Author swwan
 * @Date 2020/4/15 19:52
 * @Version 1.0
 **/
@Service
public class ExchangeInstructionService extends BaseService {
    private Logger logger = LoggerFactory.getLogger(ExchangeInstructionService.class);

    public ResultBean queryAll(Map<String, Object> params) {
        return restTemplate.postForObject(ServiceApplication.EXCHANGE_SERVICE_NAME + TradeServiceURL.prefix + TradeServiceURL.queryInstructionAll, params, ResultBean.class);
    }

    /*** 
     * @Description: 指令接收|放弃接口 
     * @Param: [params]
     * @return: com.joyintech.tams.exchange.common.bean.ResultBean
     * @Author: MAXX
     * @Date: 2020/4/28
     */
    public ResultBean acceptOrRefuse(List<InstructionOpBean> instructionOpBeans) {
        return restTemplate.postForObject(ServiceApplication.EXCHANGE_SERVICE_NAME + TradeServiceURL.prefix + TradeServiceURL.instructionConfirm, instructionOpBeans, ResultBean.class);
    }

    /***
     * @Description: 到期收益率
     * @Param: [params]
     * @return: com.joyintech.tams.exchange.common.bean.ResultBean
     * @Author: MAXX
     * @Date: 2020/5/8 
     */
    public ResultBean expireIncomeRate(Map<String, Object> params, int calculateType) {
        logger.info("params:{}", params);
        String url = TradeServiceURL.expireIncomeRate;
        if (calculateType == 1) {//净价
            url = TradeServiceURL.unitCprice;
        } else if (calculateType == 2) {
            url = TradeServiceURL.fpriceAmt;
        }
        url = ServiceApplication.TRADE_SERVICE_NAME + url;
        ExpireIncomeRateRet expireIncomeRateRetultBean = restTemplate.postForObject(url, params, ExpireIncomeRateRet.class);
        logger.info("expireIncomeRateRetultBean:{}", JSONObject.toJSONString(expireIncomeRateRetultBean));
        if ("200".equals(expireIncomeRateRetultBean.getStatuCode())) {//成功
            return new ResultBean(ErrorCode.SUCCESS, expireIncomeRateRetultBean.getData());
        }
        return new ResultBean(ErrorCode.TERMINAL_API_ERROR, expireIncomeRateRetultBean.getErrorList());
    }

    public Message<CalcPriceAndYieldResponse> calcPriceAndYield(CalcPriceAndYieldRequest request) {
        logger.info("calcPriceAndYield() called with parameters => 【request = {}】", JSONUtil.toJsonStr(request));
        String url = TradeServiceURL.calcPriceAndYield;
        url = ServiceApplication.INSTRUCTION_SERVICE + url;
        Message response = restTemplate.postForObject(url, request, Message.class);
        logger.info("calcPriceAndYield() called with result => 【result = {}】", JSONUtil.toJsonStr(response));
        return response;

    }

    public ResultBean flushInstructionFromInvest(String param) {
        return restTemplate.postForObject(ServiceApplication.EXCHANGE_SERVICE_NAME + TradeServiceURL.prefix + TradeServiceURL.flushInstructionAll + param, null, ResultBean.class);
    }
}
