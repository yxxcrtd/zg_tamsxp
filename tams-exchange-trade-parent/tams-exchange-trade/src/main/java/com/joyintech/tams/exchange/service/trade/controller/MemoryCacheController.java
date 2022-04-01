package com.joyintech.tams.exchange.service.trade.controller;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.bean.page.PageBean;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.service.trade.management.service.MemoryCacheManagement;
import com.joyintech.tams.exchange.util.MasterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trade/joyin")
@Api(tags = "场内交易-内存数据监控接口")
public class MemoryCacheController {

    @Autowired
    private MemoryCacheManagement memoryCacheManagement;

    @ApiOperation(value = "查询内存监控数据概况", notes = "查询内存监控数据概况")
    @PostMapping(value = "/cache/monitor")
    public Message<Map<String, Integer>> queryCacheMonitor() {
        return new Message<>(memoryCacheManagement.queryCacheMonitor());
    }

    @ApiOperation(value = "查询具体内存监控数据", notes = "查询具体内存监控数据")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "用户编号", paramType = "query"),
            @ApiImplicitParam(required = true, name = "serviceName", value = "表格名称", paramType = "query"),
            @ApiImplicitParam(required = false, name = "page", value = "当前页，默认为1", paramType = "query"),
            @ApiImplicitParam(required = false, name = "perPage", value = "每页数量，默认为1000", paramType = "query"),
    })
    @PostMapping(value = "/cache")
    public Message<PageBean> queryCache(@RequestParam @NotBlank(message = "用户编号不可为空") String userId,
                                        @RequestParam @NotBlank(message = "表格名称不可为空") String serviceName,
                                        @RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "1000") Integer perPage) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<>(memoryCacheManagement.getData(serviceName, page, perPage));
    }

    @ApiOperation(value = "内存数据覆盖", notes = "内存数据覆盖")
    @PostMapping(value = "/cache/recover")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "用户编号", paramType = "query"),
            @ApiImplicitParam(required = true, name = "serviceName", value = "表格名称", paramType = "query"),
            @ApiImplicitParam(required = false, name = "id", value = "主键编号", paramType = "query"),
            @ApiImplicitParam(required = false, name = "type", value = "覆盖类型，默认为0 0：从DB加载到内存；1：从内存写到DB；2：先删除DB数据再从内存写到DB", paramType = "query"),
    })
    public Message<Object> recoverCache(@RequestParam @NotBlank(message = "用户编号不可为空") String userId,
                                        @RequestParam @NotBlank(message = "表格名称不可为空") String serviceName,
                                        @RequestParam(value = "id", required = false) String id,
                                        @RequestParam(value = "type", required = false, defaultValue = "0") Integer type) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<>(memoryCacheManagement.recoverData(serviceName, id, type));
    }

    @ApiOperation(value = "重新写入行情", notes = "重新写入行情")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "用户编号", paramType = "query"),
    })
    @PostMapping(value = "/cache/quote")
    public ResultBean recoverCacheQuoteFromRedis(@RequestParam @NotBlank(message = "用户编号不可为空") String userId) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        return memoryCacheManagement.recoverQuoteData();
    }

    @ApiOperation(value = "内存数据清空", notes = "内存数据清空")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "用户编号", paramType = "query"),
            @ApiImplicitParam(required = true, name = "serviceName", value = "表格名称", paramType = "query"),
    })
    @PostMapping(value = "/cache/clear")
    public Message<Object> clearCache(@RequestParam @NotBlank(message = "用户编号不可为空") String userId,
                                      @RequestParam @NotBlank(message = "表格名称不可为空") String serviceName) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<>(memoryCacheManagement.clearData(serviceName));
    }

    @ApiOperation(value = "保存监控数据", notes = "保存监控数据")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "用户编号", paramType = "query"),
            @ApiImplicitParam(required = true, name = "serviceName", value = "表格名称", paramType = "query"),
            @ApiImplicitParam(required = true, name = "beanInfoList", value = "待保存数据"),
    })
    @PostMapping(value = "/cache/add")
    public Message<Object> addData(@RequestParam @NotBlank(message = "用户编号不可为空") String userId,
                                   @RequestParam @NotBlank(message = "表格名称不可为空") String serviceName,
                                   @RequestBody @NotBlank(message = "待保存数据不可为空") String beanInfoList) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<>(memoryCacheManagement.addData(serviceName, beanInfoList));
    }

    @ApiOperation(value = "删除监控数据", notes = "删除监控数据")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "用户编号", paramType = "query"),
            @ApiImplicitParam(required = true, name = "serviceName", value = "表格名称", paramType = "query"),
            @ApiImplicitParam(required = true, name = "idList", value = "待删除主键列表"),
    })
    @PostMapping(value = "/cache/delete")
    public Message<Object> deleteData(@RequestParam @NotBlank(message = "用户编号不可为空") String userId,
                                      @RequestParam @NotBlank(message = "表格名称不可为空") String serviceName,
                                      @RequestBody @NotBlank(message = "待删除主键列表不可为空") List<String> idList) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<>(memoryCacheManagement.deleteData(serviceName, idList));
    }
}
