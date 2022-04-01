package com.joyintech.tams.exchange.service.trade.controller;

import com.joyintech.tams.exchange.common.bean.CounterSeatInfo;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.service.trade.management.service.CounterManagement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trade/joyin/counter")
@Api(tags = "场内交易-席位及交易员信息操作接口")
public class CounterController {

    @Autowired
    private CounterManagement counterManagement;

    @PostMapping(value = "/counterPartyInfo")
    @ApiOperation(value = "查询对手方信息", notes = "查询对手方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "counterId", value = "对手方编号", paramType = "query")
    })
    public Message<List<CounterSeatInfo>> counterPartyInfo(@RequestParam(required = false) String counterId) {
        return counterManagement.queryCounterParty(counterId);
    }


    @PostMapping(value = "/counterSeatInfo")
    @ApiOperation(value = "查询对手方席位信息", notes = "查询对手方席位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "counterId", value = "对手方编号", paramType = "query"),
            @ApiImplicitParam(required = false, name = "counterSeatId", value = "对手方席位编号", paramType = "query")
    })
    public Message<List<CounterSeatInfo>> queryCounterSeatInfo(@RequestParam(required = false) String counterId,
                                                               @RequestParam(required = false) String counterSeatId) {
        return counterManagement.queryCounterSeatInfo(counterId, counterSeatId);
    }

    /*** 
     * @Description: 查询对手方交易员信息
     * @Param: [counterId, counterTraderId] 
     * @return: com.joyintech.tams.exchange.common.bean.ResultBean
     * @Author: MAXX
     * @Date: 2020/9/30 
     */
    @PostMapping(value = "/trader/counter")
    @ApiOperation(value = "查询对手方交易员信息", notes = "查询对手方交易员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "counterId", value = "对手方编号", paramType = "query"),
            @ApiImplicitParam(required = false, name = "counterTraderId", value = "对手方交易员编号", paramType = "query")
    })
    public Message<List<CounterSeatInfo>> queryCounterTraderInfo(@RequestParam(required = false) String counterId,
                                                                 @RequestParam(required = false) String counterTraderId) {
        return counterManagement.queryCounterTraderInfo(counterId, counterTraderId);
    }

    /**
     * 本方交易员查询接口
     *
     * @param counterTraderId 本方交易员编号
     * @return ResultBean
     */
    @PostMapping(value = "/trader/our")
    @ApiOperation(value = "查询本方交易员信息", notes = "查询本方交易员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = false, name = "counterTraderId", value = "本方交易员编号", paramType = "query")
    })
    public Message<List<CounterSeatInfo>> queryOurTraderInfo(@RequestParam(required = false) String counterTraderId) {
        return counterManagement.queryCounterTraderInfo(counterTraderId);
    }

    /*** 
     * @Description: 添加对手方席位
     * @Param: [counterId, counterSeatId] 
     * @return: com.joyintech.tams.exchange.common.bean.ResultBean
     * @Author: MAXX
     * @Date: 2020/9/30 
     */
    @PostMapping(value = "/add/counterSeat")
    @ApiOperation(value = "添加对手方席位信息", notes = "添加对手方席位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "counterId", value = "对手方编号", paramType = "query"),
            @ApiImplicitParam(required = true, name = "counterSeatId", value = "对手方席位编号", paramType = "query")
    })
    public Message<Object> addCounterSeat(@RequestParam @NotBlank(message = "对手方编号不可为空") String counterId,
                                          @RequestParam @NotBlank(message = "对手方席位编号不可为空") String counterSeatId) {
        return counterManagement.addCounterSeat(counterId, counterSeatId);
    }

    /*** 
     * @Description: 添加对手方交易员
     * @Param: [counterId, counterTraderId] 
     * @return: com.joyintech.tams.exchange.common.bean.ResultBean
     * @Author: MAXX
     * @Date: 2020/9/30 
     */
    @PostMapping(value = "/add/trader/counter")
    @ApiOperation(value = "添加对手方交易员", notes = "添加对手方交易员")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "counterId", value = "对手方编号", paramType = "query"),
            @ApiImplicitParam(required = true, name = "counterTraderId", value = "对手方交易员编号", paramType = "query")
    })
    public Message<Object> addCounterTrader(@RequestParam @NotBlank(message = "对手方编号不可为空") String counterId,
                                            @RequestParam @NotBlank(message = "对手方交易员编号不可为空") String counterTraderId) {
        return counterManagement.addCounterTrader(counterId, counterTraderId);
    }

    /**
     * 添加本方交易员信息
     *
     * @param counterTraderId 本方交易员编号
     * @return ResultBean
     */
    @PostMapping(value = "/add/trader/our")
    @ApiOperation(value = "添加本方交易员信息", notes = "添加本方交易员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "counterTraderId", value = "本方交易员编号", paramType = "query")
    })
    public Message<Object> addOurTrader(@RequestParam @NotBlank(message = "本方交易员编号不可为空") String counterTraderId) {
        return counterManagement.addCounterTrader(counterTraderId);
    }
}