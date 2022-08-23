package com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dto.SalerRewPenaInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.service.SalerRewPenaInfoService;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@PageId("SalerRewPenaInfo002")
@RequestMapping("/report/submitmaintenance/salerrewpenainfo/SalerRewPenaInfo002")
public class SalerRewPenaInfo002Controller extends BaseController {
    
    /**
     * 业务entity
     */
    @Autowired
    private SalerRewPenaInfoService salerRewPenaInfoService;

    /**
     *
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        super.setSelectOptionToModel(model, DictCode.记录分类,DictCode.奖惩单位);
        String salerRewPenaNo = getParam("salerRewPenaNo");
        String erroOrInfo = getParam("erroOrInfo");
        if(WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(salerRewPenaNo) && StringUtil.isNotEmpty(erroOrInfo)) {
            SalerRewPenaInfoDto entity = salerRewPenaInfoService.selectDetail(salerRewPenaNo,erroOrInfo);
            // 设置到model中
            model.addAttribute("entity", entity);
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
            ValidateGroupEdit.class}) SalerRewPenaInfoDto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
    }

    /**
     *
     * 保存提交工作流
     */
    @ResponseBody
    @RequestMapping(OPERATE_TYPE_SAVE)
    public AjaxResult save(@RequestBody @Validated({
            ValidateGroupEdit.class}) SalerRewPenaInfoDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        //新建保存
        if(dto.getPageModel().equals(GlobalConstant.DETAIL_PAGE_MODEL_NEW)){
            salerRewPenaInfoService.saveAndSubmit(dto);
            return new AjaxResult("提交成功");
        }
        //编辑保存
        if(dto.getPageModel().equals(GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){
            salerRewPenaInfoService.update(dto);
            return new AjaxResult("提交成功");
        }
        return new AjaxResult("提交失败");
    }

    /**
     * 获取本行中债机构代码
     * @return
     */
    @ResponseBody
    @RequestMapping("getIssuePartyId")
    public String getIssuePartyId() {
        //获取本行中债机构代码
        /*String orgCode = CommonUtil.getOrgCode();*/
        String orgCode =CommonUtil.selectBankCode();
        return orgCode;
    }

    /**
     * 获取姓名
     * @return
     */
    @ResponseBody
    @RequestMapping("getSalerName")
    public String getSalerName(String salerInsNo) {
        return salerRewPenaInfoService.getSalerName(salerInsNo);
    }

    /**
     * 内部编号下拉框
     * @param
     * @return
     */
    @RequestMapping("selectSalerInsNo")
    @ResponseBody
    public List<SelectOptionDto> selectSalerInsNo() {
        return salerRewPenaInfoService.selectSalerInsNo(RequestUtil.getPageParam());
    }
}
