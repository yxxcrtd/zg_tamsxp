package com.joyintech.tams.exchange.controller;

import com.google.common.base.Strings;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.service.trade.management.service.EntrustManagement;
import com.joyintech.tams.exchange.service.trade.management.service.IntegratedQuoteManagement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joyin")
public class ExchangeEntrustController {

//    @Autowired
//    private ExchangeEntrustService entrustService;

    @Autowired
    private EntrustManagement entrustManagement;

    @Autowired
    private IntegratedQuoteManagement integratedQuoteManagement;

    /**
     * 查询委托信息
     *
     * @param userId        用户Id
     * @param instructionId 指令Id
     * @param accountId     账户Id
     * @param orderId       订单Id
     * @return ResultBean
     */
    @RequestMapping(value = {"/entrust"}, method = RequestMethod.GET)
    public ResultBean queryAll(@RequestParam(value = "user_id") String userId,
                               @RequestParam(value = "instruction_id", required = false) String instructionId,
                               @RequestParam(value = "account_id", required = false) String accountId,
                               @RequestParam(value = "order_id", required = false) String orderId) {
//        String param = "?user_id=" + userId;
//        if (Objects.nonNull(instructionId))
//            param += "&instruction_id=" + instructionId;
//        if (Objects.nonNull(accountId))
//            param += "&account_id=" + accountId;
//        if (Objects.nonNull(orderId))
//            param += "&order_id=" + orderId;
//        return entrustService.queryAll(param);
//////////////////////////兼容新UI后端
        return entrustManagement.queryEntrust(userId, instructionId, accountId, orderId);
    }

    /**
     * 查询综合业务信息
     *
     * @param userId        用户Id
     * @param instructionId 指令Id
     * @param accountId     账户Id
     * @param orderId       订单Id
     * @return ResultBean
     */
    @RequestMapping(value = {"/entrust/integrated"}, method = RequestMethod.GET)
    public ResultBean queryIntegratedEntrustAll(@RequestParam(value = "user_id") String userId,
                                                @RequestParam(value = "instruction_id", required = false) String instructionId,
                                                @RequestParam(value = "account_id", required = false) String accountId,
                                                @RequestParam(value = "order_id", required = false) String orderId) {
//        String param = "?user_id=" + userId;
//        if (Objects.nonNull(instructionId))
//            param += "&instruction_id=" + instructionId;
//        if (Objects.nonNull(accountId))
//            param += "&account_id=" + accountId;
//        if (Objects.nonNull(orderId))
//            param += "&order_id=" + orderId;
//        return entrustService.queryIntegratedEntrustAll(param);
//////////////////////////兼容新UI后端
        return entrustManagement.queryIntegratedEntrust(userId, instructionId, accountId, orderId);
    }

    /**
     * 撤销user下对应entrust
     *
     * @param userId    用户Id
     * @param entrustId 委托单号
     * @return ResultBean
     */
    @RequestMapping(value = "/entrust/cancel", method = RequestMethod.PUT)
    public ResultBean cancelEntrust(@RequestParam(value = "user_id") String userId,
                                    @RequestParam(value = "entrust_id") String entrustId) {
//        String param = "?user_id=" + userId + "&entrust_id=" + entrustId;
//        return entrustService.cancelEntrust(param);
//////////////////////////兼容新UI后端
        return entrustManagement.cancelEntrust(userId, entrustId);
    }

    /**
     * 大宗行情查询
     *
     * @param userId    用户Id
     * @param stockCode 市场.证券代码
     * @return ResultBean
     */
    @RequestMapping(value = "/entrust/integrated/quote", method = RequestMethod.GET)
    public ResultBean queryIntegratedQuote(@RequestParam(value = "user_id", required = false) String userId,
                                           @RequestParam(value = "quote_type") int quoteType,
                                           @RequestParam(value = "account_id", required = false) String accountId,
                                           @RequestParam(value = "stock_code", required = false) String stockCode,
                                           @RequestParam(value = "opposite_liaisonTel", required = false) String oppositeLiaisonTel,
                                           @RequestParam(value = "liaisonTel", required = false) String liaisonTel,
                                           @RequestParam(value = "page_size", required = false, defaultValue = "0") int pageSize,
                                           @RequestParam(value = "posStr", required = false) String posStr) {
//        String param = "?quote_type=" + quoteType;
//        if (!Strings.isNullOrEmpty(userId)) {
//            param = param + "&user_id=" + userId;
//        }
//        if (!Strings.isNullOrEmpty(accountId)) {
//            param = param + "&account_id=" + accountId;
//        }
//        if (!Strings.isNullOrEmpty(oppositeLiaisonTel)) {
//            param = param + "&opposite_liaisonTel=" + oppositeLiaisonTel;
//        }
//        if (!Strings.isNullOrEmpty(liaisonTel)) {
//            param = param + "&liaisonTel=" + liaisonTel;
//        }
//        if (!Strings.isNullOrEmpty(stockCode)) {
//            param = param + "&stock_code=" + stockCode;
//        }
//        if (pageSize > 0) {
//            param = param + "&page_size=" + pageSize;
//        }
//        if (!Strings.isNullOrEmpty(posStr)) {
//            param = param + "&posStr=" + posStr;
//        }
//        return entrustService.queryIntegratedQuote(param);
//////////////////////////兼容新UI后端
        return integratedQuoteManagement.queryIntegratedQuote(userId, quoteType, stockCode, accountId, oppositeLiaisonTel, liaisonTel, pageSize, posStr);
    }

    /**
     * 最大交易数量查询
     *
     * @param userId       用户Id
     * @param stockCode    市场.证券代码
     * @param entrustProp  交易类型
     *                     * @param entrustBs    交易方向
     * @param entrustPrice 委托价格
     * @param accountId    账户Id
     * @return ResultBean
     */
    @RequestMapping(value = "/entrust/enable", method = RequestMethod.GET)
    public ResultBean queryEnable(@RequestParam(value = "user_id") String userId,
                                  @RequestParam(value = "stock_code") String stockCode,
                                  @RequestParam(value = "entrust_prop") String entrustProp,
                                  @RequestParam(value = "entrust_bs") String entrustBs,
                                  @RequestParam(value = "entrust_price") double entrustPrice,
                                  @RequestParam(value = "account_id") String accountId) {
//        String param = "?user_id=" + userId + "&stock_code=" + stockCode + "&account_id=" + accountId + "&entrust_prop=" + entrustProp + "&entrust_bs=" + entrustBs + "&entrust_price=" + entrustPrice;
//        return entrustService.queryEnable(param);
//////////////////////////兼容新UI后端
        return entrustManagement.queryEnable(userId, stockCode, entrustProp, entrustBs, entrustPrice, accountId);
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param historyType
     * @param userId
     * @param startDay
     * @param endDay
     * @param accountId
     * @param portfolioId
     * @param market
     * @param stockCode
     * @param entrustProp
     * @param entrustStatus
     * @return
     */
    @RequestMapping(value = "/entrust/page", method = RequestMethod.GET)
    public ResultBean queryAllForPage(@RequestParam(value = "page_num", required = false, defaultValue = "1") int pageNum,
                                      @RequestParam(value = "page_size", required = false, defaultValue = "999999") int pageSize,
                                      @RequestParam(value = "history_type") int historyType,
                                      @RequestParam(value = "user_id") String userId,
                                      @RequestParam(value = "start_day", required = false) String startDay,
                                      @RequestParam(value = "end_day", required = false) String endDay,
                                      @RequestParam(value = "account_id", required = false) String accountId,
                                      @RequestParam(value = "portfolio_id", required = false) String portfolioId,
                                      @RequestParam(value = "market", required = false) String market,
                                      @RequestParam(value = "stock_code", required = false) String stockCode,
                                      @RequestParam(value = "entrust_prop", required = false) String entrustProp,
                                      @RequestParam(value = "entrust_status", required = false, defaultValue = "-100") int entrustStatus) {
//        String param = queryEntrustParams(pageNum, pageSize, historyType, userId, startDay, endDay, accountId, portfolioId, market, stockCode, entrustProp, entrustStatus);
//        return entrustService.queryAllForPage(param);
//////////////////////////兼容新UI后端
        return entrustManagement.queryAllEntrustForPage(pageNum, pageSize, historyType, userId, startDay, endDay, accountId, portfolioId, market, stockCode, entrustProp, entrustStatus);
    }

    private String queryEntrustParams(int pageNum, int pageSize, int historyType, String userId, String startDay, String endDay, String accountId, String portfolioId, String market, String stockCode, String entrustProp, int entrustStatus) {
        String param = "?user_id=" + userId + "&history_type=" + historyType;
        if (pageNum > 0) {
            param = param + "&page_num=" + pageNum;
        }
        if (pageSize > 0) {
            param = param + "&page_size=" + pageSize;
        }
        if (!Strings.isNullOrEmpty(startDay)) {
            param = param + "&start_day=" + startDay;
        }
        if (!Strings.isNullOrEmpty(endDay)) {
            param = param + "&end_day=" + endDay;
        }
        if (!Strings.isNullOrEmpty(accountId)) {
            param = param + "&account_id=" + accountId;
        }
        if (!Strings.isNullOrEmpty(portfolioId)) {
            param = param + "&portfolio_id=" + portfolioId;
        }
        if (!Strings.isNullOrEmpty(market)) {
            param = param + "&market=" + market;
        }
        if (!Strings.isNullOrEmpty(stockCode)) {
            param = param + "&stock_code=" + stockCode;
        }
        if (!Strings.isNullOrEmpty(entrustProp)) {
            param = param + "&entrust_prop=" + entrustProp;
        }
        if (entrustStatus > -100) {
            param = param + "&entrust_status=" + entrustStatus;
        }

        return param;
    }

    /**
     * @param pageNum
     * @param pageSize
     * @param historyType
     * @param userId
     * @param startDay
     * @param endDay
     * @param accountId
     * @param portfolioId
     * @param market
     * @param stockCode
     * @param entrustProp
     * @param entrustStatus
     * @return
     */
    @RequestMapping(value = "/entrust/integrated/page", method = RequestMethod.GET)
    public ResultBean queryIntegratedEntrustForPage(@RequestParam(value = "page_num", required = false, defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "page_size", required = false, defaultValue = "99999") int pageSize,
                                                    @RequestParam(value = "history_type") int historyType,
                                                    @RequestParam(value = "user_id") String userId,
                                                    @RequestParam(value = "start_day", required = false) String startDay,
                                                    @RequestParam(value = "end_day", required = false) String endDay,
                                                    @RequestParam(value = "account_id", required = false) String accountId,
                                                    @RequestParam(value = "portfolio_id", required = false) String portfolioId,
                                                    @RequestParam(value = "market", required = false) String market,
                                                    @RequestParam(value = "stock_code", required = false) String stockCode,
                                                    @RequestParam(value = "entrust_prop", required = false) String entrustProp,
                                                    @RequestParam(value = "entrust_status", required = false, defaultValue = "-100") int entrustStatus) {
//        String param = queryEntrustParams(pageNum, pageSize, historyType, userId, startDay, endDay, accountId, portfolioId, market, stockCode, entrustProp, entrustStatus);
//        return entrustService.queryIntegratedEntrustForPage(param);
//////////////////////////兼容新UI后端
        return entrustManagement.queryIntegratedEntrustForPage(pageNum, pageSize, historyType, userId, startDay, endDay, accountId, portfolioId, market, stockCode, entrustProp, entrustStatus);
    }

    /**
     * 根据委托状态查询委托
     *
     * @param userId        用户Id
     * @param entrustStatus 委托状态数组
     * @return ResultBean
     */
    @RequestMapping(value = "/entrust/exception", method = RequestMethod.GET)
    public ResultBean queryExceptionEntrust(@RequestParam(value = "user_id") String userId,
                                            @RequestParam(value = "entrust_status", required = false, defaultValue = "1,3,4") String entrustStatus) {
        String param = "?user_id=" + userId + "&entrust_status=" + entrustStatus;
//        return entrustService.queryExceptionEntrust(param);
//////////////////////////兼容新UI后端
        if (StringUtils.isEmpty(entrustStatus)) {
            entrustStatus = "1,3,4";
        }
        return entrustManagement.queryEntrustByStatus(userId, entrustStatus);
    }

    /**
     * 撤销异常委托
     *
     * @param userId    用户Id
     * @param entrustId 委托单号
     * @return ResultBean
     */
    @RequestMapping(value = "/entrust/exception/cancel", method = RequestMethod.PUT)
    public ResultBean cancelExceptionEntrust(@RequestParam(value = "user_id") String userId,
                                             @RequestParam(value = "user_name") String userName,
                                             @RequestParam(value = "entrust_id") String entrustId) {
        String param = "?user_id=" + userId + "&user_name=" + userName + "&entrust_id=" + entrustId;
//        return entrustService.cancelExceptionEntrust(param);
//////////////////////////兼容新UI后端
        return entrustManagement.cancelExceptionEntrust(userId, userName, entrustId);
    }
}
