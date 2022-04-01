/*
 * 文件名：EtrBoutRepoController.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2019年1月10日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.entrust.etrboutrepo.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.joyintech.service.entrust.common.service.WFlowCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.service.entrust.etrboutrepo.dto.EtrBoutRepoDto;
import com.joyintech.service.entrust.etrboutrepo.service.EtrBoutRepoService;

/**
 * 买断式回购委托
 * 〈功能详细描述〉
 * @author xiexinting
 * @version 1.0 2019年1月10日
 * @see EtrBoutRepoController
 * @since 1.0
 */
@RestController("EtrBoutRepoCtr_dtx")
@RequestMapping("/EtrBoutRepo")
public class EtrBoutRepoController {

    @Autowired
    private EtrBoutRepoService etrBoutRepoService;
    
    /**
     * 
     * 买断式回购委托分页查询
     * <功能详细描述>
     * @param param
     * @param page
     * @param length
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<EtrBoutRepoDto> selectForPage(@RequestBody Map<String, Object> param, @PathVariable("page") int page, @PathVariable("length") int length)
    {
//        String assetType1 = (String)param.get("assetType1");
//        if(StringUtil.isNotEmpty(assetType1)){
//            param.put("assetType1List", StringUtil.split(assetType1, GlobalConstant.COMMA));
//        }
        return etrBoutRepoService.selectForPage(page,length,param);
    }
    
    /**
     * 
     * 获取组合代码联想文本框信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @PostMapping("getentrustId")
    public List<SuggestDto> getentrustId(@RequestBody Map<String, Object> map)
    {
        List<SuggestDto> temp = etrBoutRepoService.getentrustId(map);
        return temp;
    }
    
    /**
     * 
     * 获取新建界面的组合代码联想下拉框框
     * <功能详细描述>
     * @param param 
     * @return
     * @see
     * @since 1.0
     */
    @PostMapping("getentrustIdAdd/{page}/{length}")
    @ResponseBody
    public Page<SelectOptionDto> getentrustIdAdd(@RequestBody Map<String, Object> map, @PathVariable("page") int page, @PathVariable("length") int length){
        Page<SelectOptionDto> temp = etrBoutRepoService.getentrustIdAdd(page,length,map);
        return temp;
    }
    /**
     * 撤回委托 
     * <功能详细描述>
     * @param dto 实体类
     * @return boolean
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("recall")
    public boolean recallDelegate(@RequestBody EtrBoutRepoDto dto) throws IllegalAccessException, InvocationTargetException{
        return etrBoutRepoService.recallDelegate(dto);
    }
    /**
     * 关闭委托 
     * 
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @GetMapping("close")
    public boolean closeDelegate(String entrustId,String closeWay) {
        return etrBoutRepoService.closeDelegate(entrustId, closeWay);
    }
    
    /**
     * 
     * 工作流完成方法
     * <功能详细描述>
     * @param map
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("doComplete")
    public void doComplete(@RequestBody EtrBoutRepoDto dto) throws IllegalAccessException, InvocationTargetException{
        WFlowCommonService.doCompleteFlow(dto.getWorkflowId());
        
        etrBoutRepoService.doComplete(dto, dto.getOperateType());
    }

    @Transactional
    @DTXTransaction
    @PostMapping("doInsert")
    public void doInsert(@RequestBody EtrBoutRepoDto dto){
        WFlowCommonService.doInsertFlow(dto.getOperateType(), etrBoutRepoService.initBaseDto(dto));
        
        etrBoutRepoService.doInsert(dto, dto.getOperateType());
    }

    @Transactional
    @DTXTransaction
    @PostMapping("doDelete")
    public void doDelete(@RequestBody String workflowId){
        WFlowCommonService.deleteFlow(workflowId);
    }

    @Transactional
    @DTXTransaction
    @PostMapping("doUpdate")
    public void doUpdate(@RequestBody EtrBoutRepoDto dto){
        WFlowCommonService.doUpdateFlow(dto.getOperateType(), etrBoutRepoService.initBaseDto(dto));

        etrBoutRepoService.doInsert(dto, dto.getOperateType());
    }

    @Transactional
    @DTXTransaction
    @PostMapping("doBack")
    public void doBack(@RequestBody EtrBoutRepoDto dto){
        WFlowCommonService.doBackFlowExecute(dto.getWorkflowId(), dto.getEntrustId());
    }
    /**
     * 
     * 通过主键查询数据
     * <功能详细描述>
     * @param entrustId
     * @return
     * @see
     * @since 1.0
     */
    @GetMapping("selectByKey")
    public EtrBoutRepoDto selectByKey(String entrustId) {
        return etrBoutRepoService.selectByKey(entrustId);
    }
    /**
     * 
     * 组合可用现金查询
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    @PostMapping("getProAvailableCash")
    @ResponseBody
    public Map<String, Object> getProAvailableCash(@RequestBody Map<String, Object> param) {
        return etrBoutRepoService.getProAvailableCash(param);
    }
    /**
     * 
     * 查询流程中的质押额度
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    @PostMapping("selectWflAmt")
    public BigDecimal selectWflAmt(@RequestBody Map<String, Object> map){
        return etrBoutRepoService.selectWflAmt(map);
    }
    /**
     * 查询证券托管户：联想框文本框
     *
     * @param param 画面参数
     * @return 证券托管户：联想框文本框
     * @see
     * @since 1.0
     */
    @RequestMapping("getSecManageAcctId")
    public List<SuggestDto> getSecManageAcctId(@RequestBody Map<String, Object> param) {
        return etrBoutRepoService.getSecManageAcctId(param);
    }
    /**
     * 
     * 查询交易中的质押额度
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    @PostMapping("selectAmt")
    public BigDecimal selectAmt(@RequestBody Map<String, Object> map){
        return etrBoutRepoService.selectAmt(map);
    }
    /**
     * 
     * 查询标准券额度
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    @PostMapping("selectStandardAmt")
    public Map<String, Object> selectStandardAmt(@RequestBody Map<String, Object> map){
        return etrBoutRepoService.selectStandardAmt(map);
    }
    /**
     * 
     * 操作前校验
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    @PostMapping("check")
    public List<ValidMessage> check(@RequestBody EtrBoutRepoDto dto){
        return etrBoutRepoService.check(dto);
    }
}
