package com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto.ChbBokSubjectDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.service.ChbBokSubjectService;
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

import java.util.Date;

/**
 * 科目定义controller
 * @author wubingbing
 * @date 2022-03-28 14:36
 */
@Controller
@PageId("ChbBokSubject002")
@RequestMapping("/chb/businessdatamaintain/chbboksubject/chbboksubject002")
public class ChbBokSubject002Controller extends BaseController {
    @Autowired
    private ChbBokSubjectService bokSubjectService;

    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.科目类型);
        this.setSelectOptionToModel(model, DictCode.科目级别);
        this.setSelectOptionToModel(model, DictCode.余额方向);
        this.setSelectOptionToModel(model, DictCode.维护数据状态);
        String workflowId = getParam(GlobalConstant.WORKFLOW_ID);
        String subjectNo = getParam("subjectNo");
        String booksetId = getParam("booksetId");
        if (!WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(workflowId)) {
            ChbBokSubjectDto dto = bokSubjectService.getBussinessEntityByWorkflowId(
                    workflowId);
            model.addAttribute("pageModel",dto.getPageModel());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        else if (WorkFlowUtil.isNotFromToDoList() &&StringUtil.isNotEmpty(subjectNo) ) {
            //通过主键查询数据
            ChbBokSubjectDto dto = bokSubjectService.selcetByKey(subjectNo,booksetId);
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
    public AjaxResult check(@RequestBody @Validated({ValidateGroupEdit.class}) ChbBokSubjectDto dto,
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
            ValidateGroupEdit.class}) ChbBokSubjectDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        // 新建留痕表参数和工作流参数
        ProcessParameter<ChbBokSubjectDto> processParameter = new ProcessParameter<ChbBokSubjectDto>();
        WorkFlowParameter<ChbBokSubjectDto> params = new WorkFlowParameter<ChbBokSubjectDto>();
        params.setPageId("ChbBokSubject002");
        params.setElementId("ChbBokSubject002_btn_submit");
        params.setBusinessCode(item -> item.getSubjectNo()+item.getBooksetId());
        params.setBusinessType(DictCodeList.共通业务类型_科目定义信息.getValue());
        params.setComment(entity -> this.getMessage("ChbBokSubject.subjectNo") + "("
                + entity.getSubjectNo() + ")"
                + this.getMessage("ChbBokSubject.subjectName")
                + "(" + entity.getSubjectName() + ")");
        // 塞入工作流参数
        processParameter.setWorkFlowParameter(params);
        dto.setCreateUser(CommonUtil.getUserName());
        dto.setCreateTime(DateUtil.now());
        dto.setUpdateUser(CommonUtil.getUserName());
        dto.setUpdateTime(DateUtil.now());
        // 流程开始
        String msg = bokSubjectService.start(processParameter, dto);
        return new AjaxResult(msg);
    }

}
