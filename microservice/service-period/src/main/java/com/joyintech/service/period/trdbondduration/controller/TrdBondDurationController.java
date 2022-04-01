package com.joyintech.service.period.trdbondduration.controller;

import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.service.period.trdbondduration.service.TrdBondDurationService;
import com.joyintech.tams.invest.common.dto.common.FinAssetIntInfo;
import com.joyintech.tams.invest.common.dto.common.TrdCommonDeleteDto;
import com.joyintech.tams.invest.common.dto.period.trdbondduration.SecPositionExerciseInfoDto;
import com.joyintech.tams.invest.common.dto.period.trdbondduration.TrdBondDurationDto;
import com.joyintech.tams.invest.common.dto.period.trdbondduration.TrdBondExerciseProcessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 产品存续期-强增强减管理
 *
 * @author : 强成西
 * @version V1.0
 * @Project: microservice
 * @Package com.joyintech.service.product.productduration.addreduce.controller
 * @date Date : 2018年09月19日 10:02
 */
@RestController("TrdBondDurationCtr_dtx")
@RequestMapping("/TrdBondDuration")
public class TrdBondDurationController {

    @Autowired
    private TrdBondDurationService trdBondDurationService;

    /**
     * 画面查询方法-交易编号
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("getBusiCode/{page}/{length}")
    public Page<SelectOptionDto> getBusiCode(@RequestBody Map<String, Object> param, @PathVariable("page") int page,
                                             @PathVariable("length") int length) {
        return trdBondDurationService.getBusiCode(page, length, param);
    }

    /**
     * 画面查询方法-证券账户
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("getSecManageAcctId/{page}/{length}")
    public Page<SelectOptionDto> getSecManageAcctId(@RequestBody Map<String, Object> param, @PathVariable("page") int page,
                                                    @PathVariable("length") int length)
    {
        return trdBondDurationService.getSecManageAcctId(page, length, param);
    }

    /**
     * 画面查询方法-市场代码
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("getFinprodMarketId/{page}/{length}")
    public Page<SelectOptionDto> getFinprodMarketId(@RequestBody Map<String, Object> param, @PathVariable("page") int page,
                                                    @PathVariable("length") int length)
    {
        return trdBondDurationService.getFinprodMarketId(page, length, param);
    }

    /**
     * 查询有效、撤销或待处理的存续期交易数据
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<TrdBondDurationDto> findPage(@RequestBody Map<String, Object> param, @PathVariable("page") int page,
                                             @PathVariable("length") int length) {
        return trdBondDurationService.findPage(page, length, param);
    }

    /**
     * 查询有效、撤销或待处理的存续期交易数据（所有数据）
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("findAllData")
    public List<TrdBondDurationDto> findAllData(@RequestBody Map<String, Object> param) {
        return trdBondDurationService.findAllData(param);
    }

    @PostMapping("findSecPosition")
    public List<SecPositionExerciseInfoDto> findSecPosition(@RequestBody Map<String, Object> param) {
        return trdBondDurationService.findSecPosition(param);
    }

    /**
     * 查询存续期交易数据
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("selectMatchedData")
    public int selectMatchedData(@RequestBody String bizCode) {
        return trdBondDurationService.selectMatchedData(bizCode);
    }

    /**
     * 查询存续期交易数据
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("findEffectiveData")
    public TrdBondDurationDto findEffectiveData(@RequestBody Map<String, Object> params) {
        return trdBondDurationService.findEffectiveData(params);
    }

    /**
     * 插入数据，并删除流程表数据
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @Transactional
    @DTXTransaction
    @PostMapping("doComplete")
    public String doComplete(@RequestBody TrdBondDurationDto trdBondDurDbInertDto) {
        return trdBondDurationService.doComplete(trdBondDurDbInertDto);
    }

    /**
     * 作废
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @Transactional
    @DTXTransaction
    @PostMapping("doDelete")
    public String doDelete(@RequestBody TrdCommonDeleteDto trdCommonDeleteDto) {
        return trdBondDurationService.doDelete(trdCommonDeleteDto);
    }

    /**
     * 撤销
     *
     * @return 结果
     * @throws Exception 异常
     */
    @Transactional
    @DTXTransaction
    @PostMapping("doRevoke")
    public String doRevoke(@RequestBody TrdBondDurationDto dto) {
        return trdBondDurationService.doRevoke(dto);
    }

    /**
     * 判断当前计息截止日在有效数据中是不是最大的
     *
     * @return 结果
     * @throws Exception 异常
     */
    @PostMapping("checkIfMaxDate")
    public Boolean checkIfMaxDate(@RequestBody Map<String, String> params) {
        return trdBondDurationService.checkIfMaxDate(params);
    }

    /**
     * 判断当前计息截止日在待处理数据中是不是最小的
     *
     * @return 结果
     * @throws Exception 异常
     */
    @PostMapping("checkIfMinDate")
    public Boolean checkIfMinDate(@RequestBody Map<String, String> params) {
        return trdBondDurationService.checkIfMinDate(params);
    }

    /**
     * 查询利息明细信息
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("findAssetIntInfo")
    public List<FinAssetIntInfo> findAssetIntInfo(@RequestBody Map<String, Object> params) {
        return trdBondDurationService.findAssetIntInfo(params);
    }

    /**
     * 统计是否存在流程中的同类交易
     * 
     * @param map 参数
     * @return 统计结果
     */
    @PostMapping("getFlowDeal")
    public List<String> getFlowDeal(@RequestBody Map<String, Object> map) {
        return trdBondDurationService.getFlowDeal(map);
    }

    @PostMapping("findProcessDtos")
    public List<String> findProcessDtos(@RequestBody Map<String, Object> params) {
        return trdBondDurationService.findProcessDtos(params);
    }
    
    @GetMapping("getPkByBizCode")
    public Map<String,Object> getPkByBizCode(@RequestParam("bizCode") String bizCode) {
    	if(StringUtil.isNotEmpty(bizCode)) {
    		return trdBondDurationService.getPkByBizCode(bizCode);
    	}
        return null;
    }

    @PostMapping("findWorkflowProcessDtos")
    public List<String> findWorkflowProcessDtos(@RequestBody Map<String, Object> params) {
        return trdBondDurationService.findWorkflowProcessDtos(params);
    }

    /**
     * 资产代码：下拉框
     *
     * @param param 画面参数
     * @return 资产代码：下拉框
     * @throws Exception
     * @see
     * @since 1.0
     */
    @RequestMapping("getFinprodId/{page}/{length}")
    public Page<SelectOptionDto> getFinprodId(@RequestBody Map<String, Object> param,
                                              @PathVariable("page") int page, @PathVariable("length") int length){
        return trdBondDurationService.getFinprodId(page, length, param);
    }

    /**
     * 画面查询方法-交易编号
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("getBusiCodeNew/{page}/{length}")
    public Page<SelectOptionDto> getBusiCodeNew(@RequestBody Map<String, Object> param, @PathVariable("page") int page,
                                             @PathVariable("length") int length) {
        return trdBondDurationService.getBusiCodeNew(page, length, param);
    }

    // modify by etliu 20220228 start
    // 广农商二阶段 GNS-896:【广农商场内清算】场内债券存续期-行权赎回，批处理后未生成交易
    /**
     * 查询存续期交易数据(行权)
     *
     * @return 查询结果集
     * @throws Exception 异常
     */
    @PostMapping("findEffectiveDataXq")
    public TrdBondExerciseProcessDto findEffectiveDataXq(@RequestBody Map<String, Object> params) {
        return trdBondDurationService.findEffectiveDataXq(params);
    }
    // modify by etliu 20220228 end
}
