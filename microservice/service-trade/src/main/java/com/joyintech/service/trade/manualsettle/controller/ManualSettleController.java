package com.joyintech.service.trade.manualsettle.controller;

import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.grid.Page;
import com.joyintech.service.trade.manualsettle.dto.ManualSettleDto;
import com.joyintech.service.trade.manualsettle.dto.ManualSettleFlowDto;
import com.joyintech.service.trade.manualsettle.service.ManualSettleService;
import com.joyintech.service.trade.manualsettle.service.PositionRequestManualSettleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author zhangnian
 * @ClassName ManualSettleController
 * @description TODO
 * @date 2020/10/22 0022 11:11
 **/
@RestController("/ManualSettleCtr_dtx")
@RequestMapping("/ManualSettle")
public class ManualSettleController {

    @Autowired
    private ManualSettleService manualSettleService;

    @Autowired
    private PositionRequestManualSettleService positionRequestService;

    @PostMapping("findPage/{page}/{length}")
    public Page<ManualSettleDto> selectAll(@RequestBody Map<String, Object> param,
                                                 @PathVariable("page") int page, @PathVariable("length") int length) throws Exception {
        return manualSettleService.selectForPage(page, length, param);
    }

    @PostMapping("queryForFlowPage")
    public List<ManualSettleFlowDto> queryForFlowPage(@RequestBody Map<String, Object> param) throws Exception {
        return manualSettleService.queryForFlowPage(param);
    }

    @Transactional
    @DTXTransaction
    @PostMapping("settleCash")
    public void settleCash(@RequestBody ManualSettleDto dto) throws IllegalAccessException, InvocationTargetException {
        manualSettleService.settleCash(dto);
        positionRequestService.sendRequest(dto);
    }

    @Transactional
    @DTXTransaction
    @PostMapping("recall")
    public boolean recallDelegate(@RequestBody ManualSettleDto dto) throws IllegalAccessException, InvocationTargetException {
        return manualSettleService.recallDelegate(dto);
    }

    @Transactional
    @DTXTransaction
    @PostMapping("settle")
    public void settledelay(@RequestBody ManualSettleDto dto) throws Exception {
         manualSettleService.settle(dto);
    }
    @PostMapping("findPageExport/{page}/{length}")
    public Page<ManualSettleDto> findPageExport(@RequestBody Map<String, Object> param,
                                           @PathVariable("page") int page, @PathVariable("length") int length) throws Exception {
        return manualSettleService.findPageExport(page, length, param);
    }
} 

