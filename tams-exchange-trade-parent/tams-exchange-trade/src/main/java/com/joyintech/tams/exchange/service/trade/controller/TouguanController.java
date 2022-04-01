package com.joyintech.tams.exchange.service.trade.controller;

import com.joyintech.tams.exchange.common.bean.*;
import com.joyintech.tams.exchange.common.db.domain.Instruction;
import com.joyintech.tams.exchange.common.db.domain.InstructionDetail;
import com.joyintech.tams.exchange.common.db.domain.InstructionPledge;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.datacommon.db.service.EntrustService;
import com.joyintech.tams.exchange.datacommon.db.service.TradeService;
import com.joyintech.tams.exchange.service.trade.management.service.InvestmentManagement;
import com.joyintech.tams.exchange.service.trade.management.service.SettleManagement;
import com.joyintech.tams.exchange.util.MasterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/joyin")
@Api(tags = "场内交易-投管系统使用接口")
public class TouguanController {

    @Autowired
    private InvestmentManagement investmentManagement;

    @Autowired
    private SettleManagement settleManagement;

    @Autowired
    private EntrustService entrustService;

    @Autowired
    private TradeService tradeService;

    /**
     * 处理审核指令
     *
     * @param orderId            订单号
     * @param instructionsStatus 指令状态
     * @return ResultBean
     */
    @ApiOperation(value = "投管用-风控 审批通过|拒绝接口", notes = "投管用-风控 审批通过|拒绝接口")
    @RequestMapping(value = "/instruction/examine", method = RequestMethod.POST)
    public ResultBean handleExamine(@RequestParam(value = "eventId") String orderId,
                                    @RequestParam(value = "approveResult") String instructionsStatus,
                                    @RequestParam(value = "approveReason", required = false) String approveReason) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        return investmentManagement.handleExamine(orderId, instructionsStatus, approveReason);
    }

    /**
     * 处理指令变更
     *
     * @param instructionId 订单号
     * @param status        状态
     * @return ResultBean
     */
    @ApiOperation(value = "投管用-指令状态变更接口", notes = "投管用-指令状态变更接口")
    @RequestMapping(value = "/instructionChange", method = RequestMethod.POST)
    public ResultBean instructionChange(@RequestParam(value = "entrustId") String instructionId,
                                        @RequestParam(value = "entrustStatus") String status) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        return investmentManagement.instructionChange(instructionId, status);
    }


    /**
     * 指令撤销
     *
     * @param instructionId     指令id
     * @param instructionStatus 指令状态
     * @return
     */
    @ApiOperation(value = "投管用-指令撤销|关闭接口", notes = "投管用-指令撤销|关闭接口")
    @RequestMapping(value = "/instruction/cancel", method = RequestMethod.POST)
    public ResultBean cancelInstruction(@RequestParam(value = "instruction_Id") String instructionId,
                                        @RequestParam(value = "entrustStatus", required = false) String instructionStatus) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        return investmentManagement.cancelInstruction(instructionId, instructionStatus);
    }


    /**
     * 指令分发
     *
     * @param instructionPage 指令及质押券列表
     * @return
     */
    @ApiOperation(value = "投管用-指令分发接收接口", notes = "投管用-指令分发接收接口")
    @RequestMapping(value = "/instruction/distribute", method = RequestMethod.POST)
    public ResultBean appointInstruction(@RequestBody InstructionPage<Instruction, InstructionPledge, InstructionDetail> instructionPage) throws Exception {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObjectResultBean(null);
    	}
        return investmentManagement.appointInstruction(instructionPage);
    }

    @ApiOperation(value = "投管用-场内交易历史数据清算接口", notes = "投管用-场内交易历史数据清算接口")
    @RequestMapping(value = "/settle", method = RequestMethod.POST)
    public Message<String> settleHistory() {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<String>(settleManagement.settleHistory());
    }

    /**
     * 投管用委托查询
     *
     * @param instructionId 指令编号号
     * @return ResultBean
     */
    @ApiOperation(value = "投管用-委托查询接口", notes = "投管用-委托查询接口")
    @RequestMapping(value = "/queryEntrust", method = RequestMethod.POST)
    public Message<List<EntrustQueryDto>> queryEntrust(@RequestParam(value = "entrustId") String instructionId) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<List<EntrustQueryDto>>(entrustService.queryEntrust2Touguan(instructionId));
    }

    /**
     * 投管用成交查询
     *
     * @param instructionId 指令编号号
     * @return ResultBean
     */
    @ApiOperation(value = "投管用-成交查询接口", notes = "投管用-成交查询接口")
    @RequestMapping(value = "/queryTrade", method = RequestMethod.POST)
    public Message<List<TradeQueryDto>> queryTrade(@RequestParam(value = "entrustId") String instructionId) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(null);
    	}
        return new Message<List<TradeQueryDto>>(tradeService.queryTrade2Touguan(instructionId));
    }

}
