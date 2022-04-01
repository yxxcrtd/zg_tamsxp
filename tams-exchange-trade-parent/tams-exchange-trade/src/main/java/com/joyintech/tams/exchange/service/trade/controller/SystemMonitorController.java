package com.joyintech.tams.exchange.service.trade.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.tams.exchange.common.bean.DynamicMonitorDTO;
import com.joyintech.tams.exchange.common.bean.MonitorIndicator;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.common.request.DynamicMonitorInfoRequest;
import com.joyintech.tams.exchange.service.trade.management.service.SystemMonitorManagement;
import com.joyintech.tams.exchange.util.MasterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trade/joyin")
@Api(tags = "场内交易-动态监控数据接口")
public class SystemMonitorController {

    @Autowired
    private SystemMonitorManagement systemMonitorManagement;

//    /**
//     * @Deprecated 不再使用
//     */
//    @Deprecated
//    @PostMapping(value = "/monitor")
//    public Message<ExchangeMonitorBean> queryExchangeMonitor() {
//        return new Message<>(systemMonitorManagement.queryExchangeMonitor());
//    }


    @ApiOperation(value = "查询动态监控数据", notes = "查询动态监控数据")
    @PostMapping("/monitor/dynamic")
    public Message<MonitorIndicator> queryExchangeMonitorDynamic(@RequestBody DynamicMonitorInfoRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        DynamicMonitorDTO dto = new DynamicMonitorDTO();
        BeanUtils.copyProperties(request, dto);
        return new Message<>(systemMonitorManagement.queryExchangeMonitorDynamic(dto));
    }
}
