package com.joyintech.tams.exchange.controller;

import com.google.common.base.Strings;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.db.domain.ServiceNode;
import com.joyintech.tams.exchange.common.db.domain.Terminal;
import com.joyintech.tams.exchange.common.db.domain.TerminalMapping;
import com.joyintech.tams.exchange.service.trade.management.service.ServiceNodeManagement;
import com.joyintech.tams.exchange.service.trade.management.service.TerminalManagement;
import com.joyintech.tams.exchange.service.trade.management.service.TerminalMappingManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/joyin")
public class ExchangeSystemParamController {

//    @Autowired
//    private ExchangeSystemParamService exchangeSystemParamService;

    @Autowired
    private TerminalMappingManagement terminalMappingManagement;

    @Autowired
    private TerminalManagement terminalManagement;

    @Autowired
    private ServiceNodeManagement serviceNodeManagement;

    /**
     * 自定义节点查询
     *
     * @param userId 用户Id
     * @return ResultBean
     */
    @RequestMapping(value = "/serviceNode", method = RequestMethod.GET)
    public ResultBean queryServiceNode(@RequestParam(value = "user_id") String userId,
                                       @RequestParam(value = "node_name", required = false) String nodeName) {
//        String param = "?user_id=" + userId;
//        if (!Strings.isNullOrEmpty(nodeName)) param += "&node_name=" + nodeName;
//        return exchangeSystemParamService.queryServiceNode(param);
////////////////////////兼容新UI后端
        return serviceNodeManagement.queryServiceNode(nodeName);
    }

    /**
     * 节点添加
     *
     * @param userId      用户Id
     * @param serviceNode 新增节点
     * @return ResultBean
     */
    @RequestMapping(value = "/add/serviceNode", method = RequestMethod.POST)
    public ResultBean addServiceNode(@RequestParam(value = "user_id") String userId,
                                     @RequestBody ServiceNode serviceNode) {
//        String param = "?user_id=" + userId;
//        HttpEntity<ServiceNode> request = new HttpEntity<>(serviceNode);
//        return exchangeSystemParamService.addServiceNode(param, request);
////////////////////////兼容新UI后端
        return serviceNodeManagement.addServiceNode(serviceNode);
    }

    /**
     * 节点修改
     *
     * @param userId      用户Id
     * @param serviceNode 修改节点信息
     * @return ResultBean
     */
    @RequestMapping(value = "/update/serviceNode", method = RequestMethod.PUT)
    public ResultBean updateServiceNode(@RequestParam(value = "user_id") String userId,
                                        @RequestParam(value = "node_name") String nodeName,
                                        @RequestBody ServiceNode serviceNode) {
//        String param = "?user_id=" + userId + "&node_name=" + nodeName;
//        HttpEntity<ServiceNode> request = new HttpEntity<>(serviceNode);
//        return exchangeSystemParamService.updateServiceNode(param, request);
////////////////////////兼容新UI后端
        return serviceNodeManagement.updateServiceNode(serviceNode);
    }

    /**
     * 账号删除
     *
     * @param userId   用户Id
     * @param nodeName 节点名称
     * @return ResultBean
     */
    @RequestMapping(value = "/delete/serviceNode", method = RequestMethod.DELETE)
    public ResultBean deleteServiceNode(@RequestParam(value = "user_id") String userId,
                                        @RequestParam(value = "node_name") String nodeName) {
//        String param = "?user_id=" + userId + "&node_name=" + nodeName;
//        return exchangeSystemParamService.deleteServiceNode(param);
//////////////////////兼容新UI后端
        return serviceNodeManagement.deleteServiceNode(nodeName);
    }

    /**
     * @param userId
     * @param terminalName
     * @return
     */
    @RequestMapping(value = "/terminal", method = RequestMethod.GET)
    public ResultBean queryTerminal(@RequestParam(value = "user_id") String userId,
                                    @RequestParam(value = "terminal_name", required = false) String terminalName) {
        String param = "?user_id=" + userId;
        if (!Strings.isNullOrEmpty(terminalName)) param += "&terminal_name=" + terminalName;
//        return exchangeSystemParamService.queryTerminal(param);
//////////////////////////兼容新UI后端
        return terminalManagement.queryTerminalInfo(terminalName);
    }

    /**
     * @param userId
     * @param terminal
     * @return
     */
    @RequestMapping(value = "/add/terminal", method = RequestMethod.POST)
    public ResultBean addTerminal(@RequestParam(value = "user_id") String userId,
                                  @RequestBody Terminal terminal) {
        String param = "?user_id=" + userId;
        HttpEntity<Terminal> request = new HttpEntity<>(terminal);
//        return exchangeSystemParamService.addTerminal(param, request);
//////////////////////////兼容新UI后端
        return terminalManagement.addTerminalInfo(terminal);
    }

    /**
     * @param userId
     * @param terminal
     * @return
     */
    @RequestMapping(value = "/update/terminal", method = RequestMethod.PUT)
    public ResultBean updateTerminal(@RequestParam(value = "user_id") String userId,
                                     @RequestParam(value = "terminal_name") String terminalName,
                                     @RequestBody Terminal terminal) {
//        String param = "?user_id=" + userId + "&terminal_name=" + terminalName;
//        HttpEntity<Terminal> request = new HttpEntity<>(terminal);
//        return exchangeSystemParamService.updateTerminal(param, request);
//////////////////////////兼容新UI后端
        return terminalManagement.updateTerminalInfo(terminalName, terminal);
    }

    /**
     * @param userId
     * @param terminalName
     * @return
     */
    @RequestMapping(value = "/delete/terminal", method = RequestMethod.DELETE)
    public ResultBean deleteTerminal(@RequestParam(value = "user_id") String userId,
                                     @RequestParam(value = "terminal_name") String terminalName) {
//        String param = "?user_id=" + userId + "&terminal_name=" + terminalName;
//        return exchangeSystemParamService.deleteTerminal(param);
//////////////////////////兼容新UI后端
        return terminalManagement.deleteTerminalInfo(terminalName);
    }

    /**
     * @param userId
     * @param accountId
     * @param terminalName
     * @return
     */
    @RequestMapping(value = "/terminalMapping", method = RequestMethod.GET)
    public ResultBean queryTerminalMapping(@RequestParam(value = "user_id") String userId,
                                           @RequestParam(value = "account_id", required = false) String accountId,
                                           @RequestParam(value = "terminal_name", required = false) String terminalName) {
        String param = "?user_id=" + userId;
        if (!Strings.isNullOrEmpty(accountId)) param += "&account_id=" + accountId;
        if (!Strings.isNullOrEmpty(terminalName)) param += "&terminal_name=" + terminalName;
//        return exchangeSystemParamService.queryTerminalMapping(param);
//////////////////////////兼容新UI后端
        return terminalMappingManagement.queryTerminalMappingInfo(accountId, terminalName);
    }

    /**
     * @param userId
     * @param terminalMappings
     * @return
     */
    @RequestMapping(value = "/add/terminalMapping", method = RequestMethod.POST)
    public ResultBean addTerminalMapping(@RequestParam(value = "user_id") String userId,
                                         @RequestBody List<TerminalMapping> terminalMappings) {
        String param = "?user_id=" + userId;
        HttpEntity<List<TerminalMapping>> request = new HttpEntity<>(terminalMappings);
//        return exchangeSystemParamService.addTerminalMapping(param, request);
//////////////////////////兼容新UI后端
        return terminalMappingManagement.addTerminalMappingInfo(terminalMappings);
    }

    /**
     * @param userId
     * @param terminalMapping
     * @return
     */
    @RequestMapping(value = "/update/terminalMapping", method = RequestMethod.PUT)
    public ResultBean updateTerminalMapping(@RequestParam(value = "user_id") String userId,
                                            @RequestParam(value = "id") String id,
                                            @RequestBody TerminalMapping terminalMapping) {
//        String param = "?user_id=" + userId + "&id=" + id;
//        HttpEntity<TerminalMapping> request = new HttpEntity<>(terminalMapping);
//        return exchangeSystemParamService.updateTerminalMapping(param, request);
//////////////////////////兼容新UI后端
        return terminalMappingManagement.updateTerminalMappingInfo(id, terminalMapping);
    }

    /**
     * @param userId
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/terminalMapping", method = RequestMethod.DELETE)
    public ResultBean deleteTerminalMapping(@RequestParam(value = "user_id") String userId,
                                            @RequestParam(value = "id") String id) {
        String param = "?user_id=" + userId + "&id=" + id;
//        return exchangeSystemParamService.deleteTerminalMapping(param);
//////////////////////////兼容新UI后端
        return terminalMappingManagement.deleteTerminalMappingInfo(id);
    }
}
