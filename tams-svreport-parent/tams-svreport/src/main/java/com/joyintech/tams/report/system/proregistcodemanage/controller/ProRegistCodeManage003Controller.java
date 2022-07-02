package com.joyintech.tams.report.system.proregistcodemanage.controller;

import java.util.List;
import java.util.Map;

import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.workflow.engine.exception.WorkFlowException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.report.system.proregistcodemanage.dto.ProRegistCodeManage002Dto;
import com.joyintech.tams.report.system.proregistcodemanage.service.ProRegistCodeManage001Service;
import com.joyintech.tams.report.system.proregistcodemanage.service.ProRegistCodeManage003Service;
import com.joyintech.tams.report.system.proregistcodemanage.service.ProRegistCodeManageMService;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.util.WorkFlowUtil;

@Controller
@PageId("ProRegistCodeManage003")
@RequestMapping("/svreport/system/proregistcodemanage/ProRegistCodeManage003")
public class ProRegistCodeManage003Controller extends BaseController {

    /**
     * 获取service层对象
     */
	@Autowired
    private ProRegistCodeManage003Service proRegistCodeManage003Service;
	 @Autowired
	private ProRegistCodeManageMService proRegistCodeManageMService;
    
    /**
     * 新建页面初始化
     */
    @Override
    public String init(Model model) {
    	setSelectOptionToModel(model, new DictCode[] {DictCode.产品运作模式 });
    	// 流程
                String prodInsId = RequestUtil.getParam("prodInsId");
    			String workflowId = this.getParam("workflowId");
    			if (!WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(workflowId)) {
    				ProRegistCodeManage002Dto entity = proRegistCodeManage003Service
    						.getBussinessEntityByWorkflowId(workflowId);
    				model.addAttribute(GlobalConstant.ENTITY, entity);
    			}else{
    			    if(StringUtils.isNotEmpty(prodInsId)){
                        ProRegistCodeManage002Dto entity = proRegistCodeManage003Service.selectByProdInsId(prodInsId);
                        model.addAttribute(GlobalConstant.ENTITY, entity);
                        model.addAttribute("pageModel",RequestUtil.getParam("pageModel"));
                    }
                }
        return super.init(model);
    }

    /**
     * 画面点击保存按钮时check
     * 
     * @param dto 实体类
     * @param result 校验结果
     * @return check结果
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({
        ValidateGroupEdit.class}) ProRegistCodeManage002Dto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
    }
    /**
     * 
     * 保存提交工作流
     * <功能详细描述>
     * @param dto 保存参数
     * @param br 保存参数
     * @return 保存返回信息
     * @throws Exception 抛出异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("save")
    public AjaxResult save(@RequestBody @Validated({
        ValidateGroupEdit.class}) ProRegistCodeManage002Dto dto, BindingResult br) throws WorkFlowException {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        ProcessParameter<ProRegistCodeManage002Dto> processParameter = new ProcessParameter<ProRegistCodeManage002Dto>();
        WorkFlowParameter<ProRegistCodeManage002Dto> params = new WorkFlowParameter<ProRegistCodeManage002Dto>();
        params.setPageId(this.getPageId());
        params.setElementId(dto.getElementId());
        params.setBusinessCode(item -> item.getProdId());
        params.setBusinessType(DictCodeList.共通业务类型_产品登记编码管理.getValue());

        // 设置流程业务说明, entity即传入的dto
        params.setComment(entity -> "产品代码" + "(" + entity.getProdId()+")"+"-"+"产品登记编码"+"("+entity.getRegCode()+")");
        processParameter.setWorkFlowParameter(params);
        String msg = proRegistCodeManage003Service.start(processParameter, dto);
        return new AjaxResult(msg);
    }
    
    @ResponseBody
    @RequestMapping("selectInfo")
    public ProRegistCodeManage002Dto selectInfo(String prodId) {
        return proRegistCodeManage003Service.selectInfo(prodId);
    }
    
    @ResponseBody
    @RequestMapping("selectId")
    public AjaxResult selectId() {
        AjaxResult ar = new AjaxResult();
        ar.setData(proRegistCodeManage003Service.selectId(RequestUtil.getPageParam()));
        return ar;
    }
    
    @ResponseBody
    @RequestMapping("selectCode")
    public AjaxResult selectCode() {
        AjaxResult ar = new AjaxResult();
        ar.setData(proRegistCodeManage003Service.selectCode(RequestUtil.getPageParam()));
        return ar;
    }
    
    /**
     * 产品下拉框
     * @return 产品代码和名称列表
     */
    @RequestMapping(value = "getProdInfo", method = RequestMethod.GET)
    @ResponseBody
    public List<SuggestDto> getProdInfo(String param) {
        return proRegistCodeManageMService.getProdInfo(param);
    } 
    
    
    @ResponseBody
    @RequestMapping("checkInsertData")
    public AjaxResult checkInsertData(String prodInsId,String regCode)  {
        return proRegistCodeManage003Service.checkInsertData(prodInsId,regCode);
    }
    
    @ResponseBody
    @RequestMapping("checkInsertData1")
    public AjaxResult checkInsertData1(String prodInsId,String regCode)  {
        return proRegistCodeManage003Service.checkInsertData1(prodInsId,regCode);
    }

    /**
     * 行内识别码
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProdInsId")
    public Page<SelectOptionDto> getProdInsId()
    {
        return proRegistCodeManage003Service.getProdInsId(RequestUtil.getPageParam());
    }

    /**
     * 产品名称
     * @param prodInsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProdNameByProdInsId")
    public String getProdNameByProdInsId(String prodInsId){
        return proRegistCodeManage003Service.getProdNameByProdInsId(prodInsId);
    }

    /**
     * 产品名称
     * @param prodInsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectRegCode")
    public int selectRegCode(String prodInsId,String regCode){
        return proRegistCodeManage003Service.selectRegCode(prodInsId,regCode);
    }
    @ResponseBody
    @RequestMapping("/checkRegCode")
    public int checkRegCode(String regCode){
        return proRegistCodeManage003Service.checkRegCode(regCode);
    }
}
