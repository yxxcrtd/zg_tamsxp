package com.joyintech.tams.exchange.controller;

import com.joyintech.tams.exchange.common.bean.AlgorithmPlaceBean;
import com.joyintech.tams.exchange.common.bean.OrderPlaceBean;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.db.domain.Algorithm;
import com.joyintech.tams.exchange.common.db.domain.IntegratedEntrust;
import com.joyintech.tams.exchange.service.trade.management.service.OrderManagement;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 兼容新UI后端
 */
@RestController
@RequestMapping("/joyin")
public class ExchangeOrderController {

//    @Autowired
//    private ExchangeOrderService orderService;

    @Autowired
    private OrderManagement orderManagement;

    /**
     * 查询订单信息
     *
     * @param userId        用户Id
     * @param instructionId 指令Id
     * @param accountId     账户Id
     * @return ResultBean
     */
//    @RequestMapping(value = "/order", method = RequestMethod.GET)
//    public ResultBean queryAll(@RequestParam(value = "user_id") String userId,
//                               @RequestParam(value = "instruction_id", required = false) String instructionId,
//                               @RequestParam(value = "account_id", required = false) String accountId) {
//        String param = "?user_id="+userId;
//        if (Objects.nonNull(instructionId))param += "&instruction_id="+instructionId;
//        if (Objects.nonNull(accountId))param += "&account_id="+accountId;
//        return orderService.queryAll(param);
//    }

    /**
     * 下单
     *
     * @param userId 用户id
     * @param order  下单信息
     * @return ResultBean
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResultBean sendOrder(@RequestParam(value = "user_id") String userId, @RequestBody OrderPlaceBean order) {
//        String param = "?user_id=" + userId;
//        HttpEntity<OrderPlaceBean> request = new HttpEntity<>(order);
//        return orderService.sendOrder(param, request);
//////////////////////////兼容新UI后端
        return orderManagement.sendOrder(userId, order);
    }

    /**
     * 综合业务下单
     *
     * @param userId            用户Id
     * @param integratedEntrust 综合订单信息
     * @return ResultBean
     */
    @RequestMapping(value = "/order/integratedEntrust", method = RequestMethod.POST)
    public ResultBean sendIntegratedEntrust(@RequestParam(value = "user_id") String userId, @RequestBody IntegratedEntrust integratedEntrust) {
//        String param = "?user_id=" + userId;
//        HttpEntity<IntegratedEntrust> request = new HttpEntity<>(integratedEntrust);
//        return orderService.sendIntegratedEntrust(param, request);
//////////////////////////兼容新UI后端
        return orderManagement.sendIntegratedEntrust(userId, integratedEntrust);
    }

    /**
     * 撤单
     *
     * @param userId  用户Id
     * @param orderId 订单Id
     * @return ResultBean
     */
    @RequestMapping(value = "/order/cancel", method = RequestMethod.PUT)
    public ResultBean cancelOrder(@RequestParam(value = "user_id") String userId, @RequestParam(value = "order_id") String orderId) {
//        String param = "?user_id=" + userId + "&order_id=" + orderId;
//        return orderService.cancelOrder(param);
//////////////////////////兼容新UI后端
        return orderManagement.cancelOrder(userId, orderId);
    }

    /**
     * 算法单创建
     *
     * @param userId    用户id
     * @param algorithm 算法母单信息
     * @return ResultBean
     */
    @RequestMapping(value = "/order/algorithm", method = RequestMethod.POST)
    public ResultBean createAlgorithm(@RequestParam(value = "user_id") String userId, @RequestBody Algorithm algorithm) {
//        String param = "?user_id=" + userId;
//        HttpEntity<Algorithm> request = new HttpEntity<>(algorithm);
//        return orderService.createAlgorithm(param, request);
//////////////////////////兼容新UI后端
        AlgorithmPlaceBean algorithmPlaceBean = new AlgorithmPlaceBean();
        BeanUtils.copyProperties(algorithm, algorithmPlaceBean);
        return orderManagement.createAlgorithm(userId, algorithmPlaceBean);
    }

}
