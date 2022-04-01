package com.joyintech.tams.exchange.service.trade.processor;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.joyintech.framework.JoyinRestTemplate;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.exchange.common.bean.ExpireIncomeRateRet;
import com.joyintech.tams.exchange.common.constant.Market;
import com.joyintech.tams.exchange.common.constant.joyin.DictCodeListExt2;
import com.joyintech.tams.exchange.common.microservice.ServiceApplication;
import com.joyintech.tams.exchange.common.microservice.TradeServiceURL;
import com.joyintech.tams.exchange.common.request.PositionInstructionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExpireIncomeRateHelper {
    private static final Logger logger = LoggerFactory.getLogger(ExpireIncomeRateHelper.class);

    @Autowired(required = false)
    @Qualifier("loadBalancedRestTemplate")
    private JoyinRestTemplate restTemplate;

    public BigDecimal calculateFullPrice(PositionInstructionRequest instructionClone) {
        Map<String, Object> pageParam = new HashMap<String, Object>();
        pageParam.put("finprodId", instructionClone.getFinprodId());
        String tradeMarket = instructionClone.getTradeMarket();
        if (tradeMarket.equals(Market.SZSE.value)) {
            pageParam.put("tradeMarket", DictCodeListExt2.交易流通场所_中债_深圳证券交易所.getValue());
        } else if (tradeMarket.equals(Market.SHSE.value)) {
            pageParam.put("tradeMarket", DictCodeListExt2.交易流通场所_中债_上海证券交易所.getValue());
        }
        pageParam.put("settleDate", DateUtil.format(instructionClone.getTradeDate()));
        pageParam.put("invAim", null);
        pageParam.put("tradeAmt", instructionClone.getEntrustAmt().doubleValue());
        pageParam.put("fpriceAmt", 0);
        pageParam.put("yield", 0);
        pageParam.put("unitCprice", instructionClone.getEntrustPrice().doubleValue());
        pageParam.put("tradeMarketMode", "02");

        String url = ServiceApplication.TRADE_SERVICE_NAME + TradeServiceURL.unitCprice;
        logger.info("调用投管接口：url:{}, param:{}", url, JSONUtil.toJsonStr(pageParam));
        ExpireIncomeRateRet expireIncomeRateRet = restTemplate.postForObject(url, pageParam, ExpireIncomeRateRet.class);
        logger.info("ExpireIncomeRateRet:{}", JSONObject.toJSONString(expireIncomeRateRet));
        if (expireIncomeRateRet.getData() == null) {
            logger.error("计算全价异常。。。。");
            return BigDecimal.valueOf(0);
        }
        return BigDecimal.valueOf(expireIncomeRateRet.getData().getFpriceAmt());
    }
}
