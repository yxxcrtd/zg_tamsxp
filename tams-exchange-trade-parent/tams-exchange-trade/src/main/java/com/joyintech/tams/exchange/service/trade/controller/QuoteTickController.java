package com.joyintech.tams.exchange.service.trade.controller;

import com.joyintech.exchange.service.quote.bean.InterfQuoteBean;
import com.joyintech.exchange.service.quote.service.QuoteService;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.bean.TickBean;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.util.MasterUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/service/joyin")
public class QuoteTickController {
    private static final Logger logger = LoggerFactory.getLogger(QuoteTickController.class);

    @Autowired
    private QuoteService quoteService;


    /**
     * 查询行情tick信息
     *
     * @param stockCode 合约代码
     * @return ResultBean
     */
    @RequestMapping(value = {"/quote/tick"}, method = RequestMethod.GET)
    public ResultBean queryTick(@RequestParam(value = "stock_code") String stockCode) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.getForObjectResultBean();
    	}
        return quoteService.getTick(stockCode);
    }

    /**
     * 转换行情tick信息
     *
     * @return ResultBean
     */
    @RequestMapping(value = {"/quote/tick/convert"}, method = RequestMethod.POST)
    public ResultBean convertTick(@RequestBody InterfQuoteBean interfQuoteBean) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(interfQuoteBean);
    	}
        return new ResultBean(ErrorCode.SUCCESS, quoteService.convertTick(interfQuoteBean));
    }

    @RequestMapping(value = {"add/quote/tick"}, method = RequestMethod.POST)
    public ResultBean addTicksForByteFile(HttpServletRequest httpServletRequest) {
        byte[] buff = new byte[1024000];
        int len = 0;
        ServletInputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = httpServletRequest.getInputStream();
            os = new ByteArrayOutputStream();
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            return quoteService.addTicks(null, os.toByteArray());
        } catch (IOException e) {
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
        } finally {
            try {
                if (Objects.nonNull(os))
                    os.close();
                if (Objects.nonNull(is))
                    is.close();
            } catch (IOException e) {
                logger.error("addTicksForByteFile close fail {}", e);
            }
        }
    }

    @RequestMapping(value = {"add/quote/tick/json"}, method = RequestMethod.POST)
    public ResultBean addTicksForJson(@RequestBody List<TickBean> tickBeanList) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(tickBeanList);
    	}
        return quoteService.addTicks(tickBeanList, null);
    }


}
