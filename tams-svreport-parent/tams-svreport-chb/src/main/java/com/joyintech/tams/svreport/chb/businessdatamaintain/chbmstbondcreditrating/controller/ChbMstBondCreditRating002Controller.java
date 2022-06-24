package com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto.ChbMstBondCreditRatingDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.service.ChbMstBondCreditRateService;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;

/**
 * @author wubingbing
 * @date 2022-04-02 9:38
 */
@Controller
@PageId("ChbMstBondCreditRating002")
@RequestMapping("/chb/businessdatamaintain/chbmstbondcreditrating/chbmstbondcreditrating002")
public class ChbMstBondCreditRating002Controller extends BaseController {
    @Autowired
    private ChbMstBondCreditRateService mstBondCreditRateService;
    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.评级类型);
        this.setSelectOptionToModel(model, DictCode.长期短期);
        this.setSelectOptionToModel(model, DictCode.评级机构);
        this.setSelectOptionToModel(model, DictCode.评级结果);
//        this.setSelectOptionToModel(model, DictCode.维护数据状态);
        this.setSelectOptionToModel(model, DictCode.评级来源类型);

        String workflowId = getParam(GlobalConstant.WORKFLOW_ID);
        String oCode = getParam("oCode");
        String oType = getParam("oType");
        String gradeOrgId = getParam("gradeOrgId");
        String gradeDateStr = getParam("gradeDateStr");
        if (!WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(workflowId)) {
            ChbMstBondCreditRatingDto dto = mstBondCreditRateService.getBussinessEntityByWorkflowId(
                    workflowId);
            model.addAttribute("pageModel",dto.getPageModel());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        else if (WorkFlowUtil.isNotFromToDoList() &&StringUtil.isNotEmpty(oCode) ) {
            //通过主键查询数据
            ChbMstBondCreditRatingDto dto = null;
            try {
                dto = mstBondCreditRateService.selcetByKey(oCode,gradeOrgId, DateUtil.parseDate(gradeDateStr,"yyyy-MM-dd"),oType);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            model.addAttribute(GlobalConstant.ENTITY, dto);
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
    public AjaxResult check(@RequestBody @Validated({ValidateGroupEdit.class}) ChbMstBondCreditRatingDto dto,
                            BindingResult result) {

        return commonCheck(result, dto.getOperateType());
    }

    /**
     * 数据保存操作
     * @param dto 请求实体类
     */
    @ResponseBody
    @RequestMapping(OPERATE_TYPE_SAVE)
    public AjaxResult save(@RequestBody @Validated({
            ValidateGroupEdit.class}) ChbMstBondCreditRatingDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        // 新建留痕表参数和工作流参数
        ProcessParameter<ChbMstBondCreditRatingDto> processParameter = new ProcessParameter<ChbMstBondCreditRatingDto>();
        WorkFlowParameter<ChbMstBondCreditRatingDto> params = new WorkFlowParameter<ChbMstBondCreditRatingDto>();
        params.setPageId("ChbMstBondCreditRating002");
        params.setElementId("ChbMstBondCreditRating002_btn_submit");
        params.setBusinessCode(item -> item.getoCode()+item.getGradeOrgId()+item.getGradeDateStr()+item.getoType());
        params.setBusinessType(DictCodeList.共通业务类型_债券评级信息.getValue());
        params.setComment(entity -> this.getMessage("ChbMstBondCreditRating001.oCode") + "("
                + entity.getoCode() + ")"
                +this.getMessage("ChbMstBondCreditRating001.oType")
                +"("+entity.getoType()+")");
        // 塞入工作流参数
        processParameter.setWorkFlowParameter(params);
        dto.setCreateUser(CommonUtil.getUserName());
        dto.setCreateTime(DateUtil.now());
        dto.setUpdateUser(CommonUtil.getUserName());
        dto.setUpdateTime(DateUtil.now());
        // 流程开始
        String msg = mstBondCreditRateService.start(processParameter, dto);
        return new AjaxResult(msg);
    }
}
