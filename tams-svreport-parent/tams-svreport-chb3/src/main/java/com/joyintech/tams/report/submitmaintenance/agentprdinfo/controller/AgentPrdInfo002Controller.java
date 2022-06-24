package com.joyintech.tams.report.submitmaintenance.agentprdinfo.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.report.submitmaintenance.agentprdinfo.dao.ChbAgentPrdInfoMapper;
import com.joyintech.tams.report.submitmaintenance.agentprdinfo.dto.AgentPrdInfoDto;
import com.joyintech.tams.report.submitmaintenance.agentprdinfo.service.AgentPrdInfoService;
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
@PageId("AgentPrdInfo002")
@RequestMapping("/report/submitmaintenance/agentprdinfo/AgentPrdInfo002")
public class AgentPrdInfo002Controller extends BaseController {
    
    /**
     * 业务entity
     */
    @Autowired
    private AgentPrdInfoService agentPrdInfoService;
    @Autowired
    private ChbAgentPrdInfoMapper chbAgentPrdInfoMapper;
    /**
    *
    * {@inheritDoc}
    */
   @Override
   @RequestMapping(PAGE_INIT)
   public String init(Model model) {
       String agentInstitutionCode = getParam("agentInstitutionCode");
       String prodRegistCode = getParam("prodRegistCode");
       if(WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(agentInstitutionCode) && StringUtil.isNotEmpty(prodRegistCode)) {
           AgentPrdInfoDto entity = agentPrdInfoService.selectDetail(agentInstitutionCode,prodRegistCode);
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
            ValidateGroupEdit.class}) AgentPrdInfoDto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
    }

    /**
     *
     * 保存提交工作流
     */
    @ResponseBody
    @RequestMapping(OPERATE_TYPE_SAVE)
    public AjaxResult save(@RequestBody @Validated({
            ValidateGroupEdit.class}) AgentPrdInfoDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
            agentPrdInfoService.saveAndSubmit(dto);
            return new AjaxResult("提交成功");
    }

    /**
     * 代销机构代码下拉框
     * @param
     * @return
     */
    @RequestMapping("selectAgentInstitutionCode")
    @ResponseBody
    public List<SelectOptionDto> selectAgentInstitutionCode() {
        return agentPrdInfoService.selectAgentInstitutionCode(RequestUtil.getPageParam());
    }

    /**
     * 产品登记编码下拉框
     * @param
     * @return
     */
    @RequestMapping("selectProdRegistCode")
    @ResponseBody
    public List<SelectOptionDto> selectProdRegistCode() {
        return agentPrdInfoService.selectProdRegistCode(RequestUtil.getPageParam());
    }

    /**
     * 根据代销机构代码查询代销机构名称
     *
     * @param agentInstitutionCode 代销机构代码
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("queryNameByCode")
    public AgentPrdInfoDto queryNameByCode(String agentInstitutionCode) {
        return chbAgentPrdInfoMapper.selectByPrimaryKey(agentInstitutionCode);
    }

    /**
     * 获取本行中债机构代码
     * @return
     */
    @ResponseBody
    @RequestMapping("getIssuePartyId")
    public String getIssuePartyId() {
        //获取本行中债机构代码
        String orgCode = CommonUtil.getOrgCode();
        if(!StringUtil.isEmpty(orgCode)) {
            return agentPrdInfoService.selectIssuePartyId(orgCode);
        }
        return null;
    }
}
