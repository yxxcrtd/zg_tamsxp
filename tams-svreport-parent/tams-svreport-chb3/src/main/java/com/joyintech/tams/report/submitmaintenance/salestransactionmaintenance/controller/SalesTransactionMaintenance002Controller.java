/*
 * 文件名：SalesTransactionMaintenance002Controller.java
 */
 
package com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.dto.SalesTransactionMaintenanceDto;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.service.SalesTransactionMaintenanceService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * 销售结算资金交易信息维护新建
 */
@Controller
@PageId("SalesTransactionMaintenance002")
@RequestMapping("/report/submitmaintenance/salestransactionmaintenance/SalesTransactionMaintenance002")
public class SalesTransactionMaintenance002Controller extends BaseController {
    
    /**
     * 业务entity
     */
    @Autowired
    private SalesTransactionMaintenanceService SalesTransactionMaintenanceService;
    /**
    *
    * {@inheritDoc}
    */
   @Override
   @RequestMapping(PAGE_INIT)
   public String init(Model model) {
       super.setSelectOptionToModel(model, DictCode.销售结算资金流动方向,DictCode.币种_中债);
       String registSeqno = getParam("registSeqno");
       String maintainStatus = getParam("maintainStatus");
       if(WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(registSeqno)) {
           //从info表取
           if(maintainStatus.equals("02")) {
               SalesTransactionMaintenanceDto entity = SalesTransactionMaintenanceService.selectDetail(registSeqno);
               // 设置到model中
               model.addAttribute("entity", entity);
           }
           //从erro表取
           else{
               SalesTransactionMaintenanceDto entity = SalesTransactionMaintenanceService.selectErroDetail(registSeqno);
               // 设置到model中
               model.addAttribute("entity", entity);
           }
       }
       return super.init(model);
   }

    /**
     * 保存按钮点击时的check
     *
     * @param dto dto
     * @param result BindingResult
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({
            ValidateGroupEdit.class}) SalesTransactionMaintenanceDto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
    }
    /**
     *
     * 保存提交工作流
     */
    @ResponseBody
    @RequestMapping(OPERATE_TYPE_SAVE)
    public AjaxResult save(@RequestBody @Validated({
            ValidateGroupEdit.class}) SalesTransactionMaintenanceDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)){
            SalesTransactionMaintenanceService.save(dto);
            return new AjaxResult("保存成功");
        }
        if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT) && Objects.equals(dto.getMaintainStatus(),"02")){
            SalesTransactionMaintenanceService.update(dto);
            return new AjaxResult("操作成功");
        }
        //来自err表的数据要先删除err表中的数据再保存在info表中
        if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT) && StringUtils.isBlank(dto.getMaintainStatus())){
            SalesTransactionMaintenanceService.deleteAndSave(dto);
            return new AjaxResult("操作成功");
        }
        return new AjaxResult("保存失败");
    }
}
