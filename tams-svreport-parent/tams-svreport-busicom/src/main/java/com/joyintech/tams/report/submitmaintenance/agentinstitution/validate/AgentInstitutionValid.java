package com.joyintech.tams.report.submitmaintenance.agentinstitution.validate;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.dto.AgentInstitutionDto;
import com.joyintech.tams.report.submitmaintenance.agentinstitution.service.AgentInstitution002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class AgentInstitutionValid implements BeanValidator {
    /**
     * 获取service对象
     */
    @Autowired
    private AgentInstitution002Service agentInstitution002Service;

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        AgentInstitutionDto dto = (AgentInstitutionDto)value;
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();
        //代理销售机构代码重复校验
        if(dto.getPageModel() != null && dto.getPageModel() != "" && dto.getPageModel().equals(GlobalConstant.DETAIL_PAGE_MODEL_NEW)){
            if(dto.getAgentInstitutionCode() != null && dto.getAgentInstitutionCode() != ""){
                    if(agentInstitution002Service.checkAgentInstitutionCode(dto.getAgentInstitutionCode()) > 0) {
                        errorList.add(new ValidMessage("", "代理销售机构代码已存在。"));
                    }
                }
            //代理销售机构名称重复校验
            if(dto.getAgentInstitutionName() != null && dto.getAgentInstitutionName() != ""){
                if(agentInstitution002Service.checkAgentInstitutionName(dto.getAgentInstitutionName()) > 0) {
                    errorList.add(new ValidMessage("", "代理销售机构名称已存在。"));
                }
            }
        }
        if(dto.getPageModel() != null && dto.getPageModel() != "" && dto.getPageModel().equals(GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){
            if(agentInstitution002Service.checkEditAgentInstitutionName(dto.getAgentInstitutionName(),dto.getAgentInstitutionCode()) > 0) {
                errorList.add(new ValidMessage("agentInstitutionName", "代理销售机构名称已存在。"));
            }
        }
        //调用数据库配置脚本check方法
        errorList = chbDataCheckCommonUtil.chbCheckCommon(errorList, dto, "AgentInstitution002", null, ValidMessage.class);
        return errorList;
    }
}
