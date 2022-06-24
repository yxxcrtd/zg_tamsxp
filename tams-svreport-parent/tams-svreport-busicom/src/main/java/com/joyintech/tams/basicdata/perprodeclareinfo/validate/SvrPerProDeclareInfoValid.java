package com.joyintech.tams.basicdata.perprodeclareinfo.validate;


import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品申报登记信息维护 校验
 * @author yxj
 * @date 2022-03-11
 */
@Component
public class SvrPerProDeclareInfoValid implements BeanValidator {

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    @Override
    public List<ValidMessage> valid(Object value) {
        SvrPerProDeclareInfo001Dto dto = (SvrPerProDeclareInfo001Dto)value;
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();

        //调用数据库配置脚本check方法
        errorList = chbDataCheckCommonUtil.chbCheckCommon(errorList, dto, "SvrPerProDeclareInfo002", null, ValidMessage.class);

        return errorList;
    }
}
