package com.joyintech.tams.exchange.service.trade.controller;

import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.service.trade.management.service.RevokeManagement;
import com.joyintech.tams.exchange.util.MasterUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ApprovalController
 * @Description TODO
 * @Author swwan
 * @Date 2020/7/29 17:13
 * @Version 1.0
 **/
@RestController
//@PageId("TrdExchange002")
@RequestMapping("/service/joyin")
public class RevokeController {
    private static final Logger logger = LoggerFactory.getLogger(RevokeController.class);

    @Autowired
    private RevokeManagement revokeManagement;

    @RequestMapping(value = "/entrust/revoke/examine", method = RequestMethod.POST)
    public ResultBean handleEntrustRevokeExamine(@RequestParam(value = "eventId") String eventId,
                                                 @RequestParam(value = "approveResult") String status,
                                                 @RequestParam(value = "approveReason", required = false) String reason) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        return revokeManagement.handleExamine(eventId, status, reason);
    }
    //    @RequestMapping(value = "/order/revoke/examine", method = RequestMethod.POST)
    //    public ResultBean handleOrderRevokeExamine(@RequestParam(value = "eventId") String eventId,
    //                                               @RequestParam(value = "approveResult") String status,
    //                                               @RequestParam(value = "approveReason", required = false) String reason) {
    //        return revokeManagement.handleExamine(eventId, status, reason);
    //    }
}
