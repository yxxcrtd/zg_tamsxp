package com.joyintech.tams.report.submitmaintenance.salerinstinfo.validate;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dto.SalerInstInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.service.SalerInstInfo002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SalerInstInfoValid implements BeanValidator {
    /**
     * 获取service对象
     */
    @Autowired
    private SalerInstInfo002Service salerInstInfo002Service;

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        SalerInstInfoDto dto = (SalerInstInfoDto)value;
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();
        if(dto.getPageModel() != null && dto.getPageModel() != "" && StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_NEW)){
            //总部或分支机构编码重复校验
            if(dto.getSalerOrgCode() != null && dto.getSalerOrgCode() != ""){
                    if(salerInstInfo002Service.checkSalerOrgCode(dto.getSalerOrgCode()) > 0) {
                        errorList.add(new ValidMessage("", "总部或分支机构编码已存在。"));
                    }
                }
            //总部或分支机构名称重复校验
            if(dto.getSalerOrgName() != null && dto.getSalerOrgName() != ""){
                if(salerInstInfo002Service.checkSalerOrgName(dto.getSalerOrgName()) > 0) {
                    errorList.add(new ValidMessage("", "总部或分支机构名称已存在。"));
                }
            }
            }
        if(dto.getPageModel() != null && dto.getPageModel() != "" &&  StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){
            //总部或分支机构名称重复校验
            if(dto.getSalerOrgName() != null && dto.getSalerOrgName() != ""){
                if(salerInstInfo002Service.checkSalerOrgNameEdit(dto.getSalerOrgName(),dto.getSalerOrgCode()) > 0) {
                    errorList.add(new ValidMessage("", "总部或分支机构名称已存在。"));
                }
            }
        }
        //调用数据库配置脚本check方法
        errorList = chbDataCheckCommonUtil.chbCheckCommon(errorList, dto, "SalerInstInfo002", null, ValidMessage.class);
        return errorList;
    }
}
