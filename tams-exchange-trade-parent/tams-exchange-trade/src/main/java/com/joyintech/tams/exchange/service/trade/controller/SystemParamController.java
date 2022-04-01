package com.joyintech.tams.exchange.service.trade.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.tams.exchange.common.db.domain.ServiceNode;
import com.joyintech.tams.exchange.common.db.domain.Terminal;
import com.joyintech.tams.exchange.common.db.domain.TerminalMapping;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.common.request.DeleteTerminalMappingRequest;
import com.joyintech.tams.exchange.common.request.QueryServiceNodeRequest;
import com.joyintech.tams.exchange.common.request.QueryTerminalRequest;
import com.joyintech.tams.exchange.common.request.ServiceNodeRequest;
import com.joyintech.tams.exchange.common.request.TerminalInsertRequest;
import com.joyintech.tams.exchange.common.request.TerminalMappingInsertRequest;
import com.joyintech.tams.exchange.common.request.TerminalMappingRequest;
import com.joyintech.tams.exchange.service.trade.management.service.ServiceNodeManagement;
import com.joyintech.tams.exchange.service.trade.management.service.TerminalManagement;
import com.joyintech.tams.exchange.service.trade.management.service.TerminalMappingManagement;
import com.joyintech.tams.exchange.util.MasterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trade/joyin")
@Api(tags = "场内交易-终端管理操作接口")
public class SystemParamController {

    @Autowired
    private ServiceNodeManagement serviceNodeManagement;

    @Autowired
    private TerminalManagement terminalManagement;

    @Autowired
    private TerminalMappingManagement terminalMappingManagement;

    /**
     * 自定义节点查询
     *
     * @param request 查询参数
     * @return ResultBean
     */
    @RequestMapping(value = "/serviceNode", method = RequestMethod.POST)
    public Message<Object> queryServiceNode(@RequestBody QueryServiceNodeRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        return new Message<Object>(serviceNodeManagement.queryServiceNode(request.getNodeName()));
    }

    /**
     * 节点添加
     *
     * @param request 新增节点
     * @return ResultBean
     */
    @RequestMapping(value = "/add/serviceNode", method = RequestMethod.POST)
    public Message<Object> addServiceNode(@RequestBody ServiceNodeRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        ServiceNode serviceNode = new ServiceNode();
        BeanUtils.copyProperties(request, serviceNode);
        return new Message<Object>(serviceNodeManagement.addServiceNode(serviceNode));
    }

    /**
     * 节点修改
     *
     * @param request 修改节点信息
     * @return ResultBean
     */
    @RequestMapping(value = "/update/serviceNode", method = RequestMethod.POST)
    public Message<Object> updateServiceNode(@RequestBody ServiceNodeRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        ServiceNode serviceNode = new ServiceNode();
        BeanUtils.copyProperties(request, serviceNode);
        return new Message<Object>(serviceNodeManagement.updateServiceNode(serviceNode));
    }

    /**
     * 账号删除
     *
     * @param request 节点名称
     * @return ResultBean
     */
    @RequestMapping(value = "/delete/serviceNode", method = RequestMethod.POST)
    public Message<Object> deleteServiceNode(@RequestBody QueryServiceNodeRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        return new Message<Object>(serviceNodeManagement.deleteServiceNode(request.getNodeName()));
    }

    /**
     * 终端管理：查询所有交易终端
     */
    @ApiOperation(value = "交易终端查询", notes = "交易终端查询")
    @RequestMapping(value = "/terminal", method = RequestMethod.GET)
    public Message<Object> queryTerminal(@RequestBody QueryTerminalRequest request) {
        return new Message<Object>(terminalManagement.queryTerminalInfo(request.getTerminalName()));
    }

    /**
     * 终端管理：新增交易终端
     */
    @ApiOperation(value = "新增交易终端", notes = "新增交易终端")
    @RequestMapping(value = "/add/terminal", method = RequestMethod.POST)
    public Message<Object> addTerminal(@RequestBody TerminalInsertRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        Terminal terminal = new Terminal();
        BeanUtils.copyProperties(request, terminal);
        return new Message<Object>(terminalManagement.addTerminalInfo(terminal));
    }

//    /**
//     * 终端管理：修改交易终端
//     */
//    @ApiOperation(value = "修改交易终端", notes = "修改交易终端")
//    @RequestMapping(value = "/update/terminal", method = RequestMethod.POST)
//    public Message<Object> updateTerminal(@RequestBody TerminalUpdateRequest request) {
//        Terminal terminal = new Terminal();
//        terminal.setTerminalName(request.getDestTerminalName());
//        terminal.setTerminalType(request.getTerminalType());
//        return new Message<Object>(terminalManagement.updateTerminalInfo(request.getSourceTerminalName(), terminal));
//    }

    /**
     * 终端管理：删除交易终端
     */
    @ApiOperation(value = "删除交易终端", notes = "删除交易终端")
    @RequestMapping(value = "/delete/terminal", method = RequestMethod.POST)
    public Message<Object> deleteTerminal(@RequestBody QueryTerminalRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        return new Message<Object>(terminalManagement.deleteTerminalInfo(request.getTerminalName()));
    }

    /**
     * 账号管理：查询所有账号终端配置
     */
    @ApiOperation(value = "查询所有账号终端配置关系", notes = "查询所有账号终端配置关系")
    @RequestMapping(value = "/terminalMapping", method = RequestMethod.GET)
    public Message<Object> queryTerminalMapping(@RequestBody TerminalMappingRequest request) {
        return new Message<Object>(terminalMappingManagement.queryTerminalMappingInfo(request.getAccountId(), request.getTerminalName()));
    }

    /**
     * 账号管理：添加账号终端配置关系
     */
    @ApiOperation(value = "添加账号终端配置关系", notes = "添加账号终端配置关系")
    @RequestMapping(value = "/add/terminalMapping", method = RequestMethod.POST)
    public Message<Object> addTerminalMapping(@RequestBody @Validated TerminalMappingInsertRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        List<TerminalMapping> terminalMappings = request.getTerminalMappings();
        if (Objects.isNull(terminalMappings)) {
            return new Message<Object>(terminalMappingManagement.deleteTerminalMappings(request.getAccountId()));
        }
        return new Message<Object>(terminalMappingManagement.addTerminalMappingInfo(terminalMappings));
    }

//    /**
//     * 账号管理：修改账号终端配置关系
//     */
//    @ApiOperation(value = "修改账号终端配置关系", notes = "修改账号终端配置关系")
//    @RequestMapping(value = "/update/terminalMapping", method = RequestMethod.POST)
//    public Message<Object> updateTerminalMapping(@RequestParam(value = "user_id") String userId,
//                                            @RequestParam(value = "id") String id,
//                                            @RequestBody TerminalMapping terminalMapping) {
//        return new Message<Object>(terminalMappingManagement.updateTerminalMappingInfo(id, terminalMapping));
//    }
//

    /**
     * 账号管理：删除账号终端配置关系
     */
    @ApiOperation(value = "删除账号终端配置关系", notes = "删除账号终端配置关系")
    @RequestMapping(value = "/delete/terminalMapping", method = RequestMethod.POST)
    public Message<Object> deleteTerminalMapping(@RequestBody @Validated DeleteTerminalMappingRequest request) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(request);
    	}
        return new Message<Object>(terminalMappingManagement.deleteTerminalMappingInfo(request.getId()));
    }
}

