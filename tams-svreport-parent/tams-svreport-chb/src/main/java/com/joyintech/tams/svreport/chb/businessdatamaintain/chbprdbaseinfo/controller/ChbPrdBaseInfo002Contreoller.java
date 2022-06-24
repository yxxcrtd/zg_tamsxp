package com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.controller;

import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.service.ChbPrdBaseInfoService;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-05-09 11:15
 */
@Controller
@PageId("CchbPrdBaseInfo002")
@RequestMapping("/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo002")
public class ChbPrdBaseInfo002Contreoller extends BaseController {
    @Autowired
    private ChbPrdBaseInfoService chbPrdBaseInfoService;

    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.管理方式_发行登记);
        this.setSelectOptionToModel(model, DictCode.是否_中债);
        this.setSelectOptionToModel(model, DictCode.开放模式_中债);
        this.setSelectOptionToModel(model, DictCode.规律开放周期);
        this.setSelectOptionToModel(model, DictCode.投资性质);
        this.setSelectOptionToModel(model, DictCode.维护数据状态);
        this.setSelectOptionToModel(model, DictCode.产品运作模式);
        this.setSelectOptionToModel(model, DictCode.开放期业务);
        this.setSelectOptionToModel(model, DictCode.产品类型_中债);
        this.setSelectOptionToModel(model, DictCode.报送产品发行状态);
        this.setSelectOptionToModel(model, DictCode.计息基础_产品要素维护);
        this.setSelectOptionToModel(model, DictCode.产品受托职责_中债);
        this.setSelectOptionToModel(model, DictCode.产品转让类型_中债);
        this.setSelectOptionToModel(model, DictCode.人行客户类型_SVR);
        String workflowId = getParam(GlobalConstant.WORKFLOW_ID);
        String produuid = getParam("produuid");
        String status = getParam("statusValid");
        ChbPrdBaseInfoDto dto =null;
        if (!WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(workflowId)) {
             dto = chbPrdBaseInfoService.getBussinessEntityByWorkflowId(
                    workflowId);
            model.addAttribute("pageModel",dto.getPageModel());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        else if (WorkFlowUtil.isNotFromToDoList() &&StringUtil.isNotEmpty(produuid) ) {
            //通过主键查询数据
             dto = chbPrdBaseInfoService.selectByKey(produuid,status);
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        if(null !=dto){
            if(dto.getAvgOpenCount()!=null){
                dto.setAvgOpenCount(dto.getAvgOpenCount().setScale(2 ,BigDecimal.ROUND_HALF_UP));
            }
        }
        //获取本行登记代码
        model.addAttribute("issuePartyId_New", CommonUtil.selectBankCode());
        return super.init(model);
    }
    @ResponseBody
    @RequestMapping("/selectRegCodeByProdId")
    public Map<String,String> selectRegCodeByProdId(String prodId){
        Map<String, String> map = chbPrdBaseInfoService.selectRegCodeByProdId(prodId);
        return map==null?new HashMap<>():map;
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
    public AjaxResult check(@RequestBody @Validated({ValidateGroupEdit.class}) ChbPrdBaseInfoDto dto,
                            BindingResult result) {

        return commonCheck(result, dto.getOperateType());
    }
    /**
     * 数据保存操作
     * @param dto 请求实体类
     */
    @ResponseBody
    @RequestMapping(OPERATE_TYPE_SAVE)
    public AjaxResult save(@RequestBody ChbPrdBaseInfoDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        // 新建留痕表参数和工作流参数
        ProcessParameter<ChbPrdBaseInfoDto> processParameter = new ProcessParameter<ChbPrdBaseInfoDto>();
        WorkFlowParameter<ChbPrdBaseInfoDto> params = new WorkFlowParameter<ChbPrdBaseInfoDto>();
        params.setPageId("CchbPrdBaseInfo002");
        params.setElementId("CchbPrdBaseInfo002_btn_submit");
        params.setBusinessCode(item -> item.getProdId());
        params.setBusinessType(DictCodeList.共通业务类型_产品基本信息.getValue());
        params.setComment(entity -> this.getMessage("CchbPrdBaseInfo001.prodId") + "("
                + entity.getProdId() + ")");
        // 塞入工作流参数
        processParameter.setWorkFlowParameter(params);
        dto.setCreateUser(CommonUtil.getUserName());
        dto.setCreateTime(DateUtil.now());
        dto.setUpdateUser(CommonUtil.getUserName());
        dto.setUpdateTime(DateUtil.now());
        // 流程开始
        String msg = chbPrdBaseInfoService.start(processParameter, dto);
        return new AjaxResult(msg);
    }
    /**
     * 行内标识码
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProdInsId")
    public Page<SelectOptionDto> getProdInsId()
    {
        return chbPrdBaseInfoService.getProdInsId(RequestUtil.getPageParam());
    }

    /**
     * 产品申报信息
     * @param prodInsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectChbPrdDeclareByProdInsId")
    public ChbPrdDeclareInfoDto selectChbPrdDeclareByProdInsId(String prodInsId){
        return chbPrdBaseInfoService.selectChbPrdDeclareByProdInsId(prodInsId);
    }
    /**
     * 行内标识码
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectByKey")
    public List<Map<String,String>> selectByProdIdKey(String prodId)
    {
        List<Map<String, String>> list = chbPrdBaseInfoService.selectByProdIdKey(prodId);
        return list==null? Lists.newArrayList():list;
    }
}
