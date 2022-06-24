package com.joyintech.tams.report.submitmaintenance.salerideninfo.validate;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dto.SalerIdenInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.service.SalerIdenInfo002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
public class SalerIdenInfoValid implements BeanValidator {
    /**
     * 获取service对象
     */
    @Autowired
    private SalerIdenInfo002Service salerIdenInfo002Service;

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        SalerIdenInfoDto dto = (SalerIdenInfoDto)value;
        List<ValidMessage> errorList = new ArrayList<>();

            if(StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_NEW) || Objects.equals(dto.getErroOrInfo(),"erro")) {
                //内部编码重复校验
                if (dto.getSalerInsNo() != null && dto.getSalerInsNo() != "") {
                    if (salerIdenInfo002Service.checkSalerInsNo(dto.getSalerInsNo()) > 0) {
                        errorList.add(new ValidMessage("salerInsNo", "内部编号已存在。"));
                    }
                }

                //证件号码重复校验
                if (dto.getSalerCredentType() != null && dto.getSalerCredentType() != "") {
                    if (StringUtil.equals(dto.getSalerCredentType(),"99")) {
                        if (dto.getSalerOtherCredentName() != null && dto.getSalerOtherCredentName() != "") {
                            if (dto.getSalerCredentNo() != null && dto.getSalerCredentNo() != "") {
                                if (salerIdenInfo002Service.checkSalerCredentNo(null, dto.getSalerOtherCredentName(), dto.getSalerCredentNo()) > 0) {
                                    errorList.add(new ValidMessage("salerCredentNo", "其他证件名称相同时，证件号码不可重复。"));
                                }
                            }
                        }
                    } else {
                        if (dto.getSalerCredentNo() != null && dto.getSalerCredentNo() != "") {
                            if (salerIdenInfo002Service.checkSalerCredentNo(dto.getSalerCredentType(), null, dto.getSalerCredentNo()) > 0) {
                                errorList.add(new ValidMessage("salerCredentNo", "证件类别相同时，证件号码不可重复。"));
                            }
                        }
                    }
                }
            }
            //其他证件名称
            if(dto.getSalerCredentType() != null && dto.getSalerCredentType() != "") {
                if (StringUtil.equals(dto.getSalerCredentType(),"99")) {
                    if (StringUtil.isEmpty(dto.getSalerOtherCredentName())){
                        errorList.add(new ValidMessage("salerOtherCredentName", "证件类别选择其他时，其他证件名称不可为空。"));
                    }
                }
            }

            //理财登记培训证书编号格式校验
            if(dto.getSalerChbTrainNo() != null && dto.getSalerChbTrainNo() != ""){
                String regex = "^([L][C][0-9]{12})$";
                if(!dto.getSalerChbTrainNo().matches(regex)){
                    errorList.add(new ValidMessage("salerChbTrainNo", "理财登记培训证书编号格式不对。"));
                }
            }
            //备注非空校验(所属区域，学位，学历，专业技术职称，理财专业证书为“其他”时备注必填)
            if((dto.getSalerAreaType() != null && dto.getSalerAreaType() != "") || (dto.getSalerDegree() != null && dto.getSalerDegree() != "")
            || (dto.getSalerEduLevel() != null && dto.getSalerEduLevel() != "") || (dto.getSalerLevle() != null && dto.getSalerLevle() != "")
            ||(dto.getSalerCertificateType() != null && dto.getSalerCertificateType() != "")){
                if (StringUtil.equals(dto.getSalerAreaType(),"900000") || StringUtil.equals(dto.getSalerDegree(),"99")
                        || StringUtil.equals(dto.getSalerEduLevel(),"99") || StringUtil.equals(dto.getSalerLevle(),"99")
                        || this.inclodeNine(dto.getSalerCertificateType())) {
                    if (StringUtil.isEmpty(dto.getRemark())) {
                        errorList.add(new ValidMessage("remark", "所属区域，学位，学历，专业技术职称或理财专业证书选择其他时，备注不能为空。"));
                    }
                }

            }

            //所属总部或分支机构校验有效性
            if(dto.getSalerOrgCode() != null && dto.getSalerOrgCode() != ""){
                if(salerIdenInfo002Service.checkSalerOrgCode(dto.getSalerOrgCode()) == 0){
                    errorList.add(new ValidMessage("salerOrgCode", "所属总部或分支机构已被删除请重新选择。"));
                }
            }

        if(dto.getPageModel() != null && dto.getPageModel() != "" && StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){

        }
        //调用数据库配置脚本check方法
        errorList = chbDataCheckCommonUtil.chbCheckCommon(errorList, dto, "SalerIdenInfo002", null, ValidMessage.class);
        return errorList;
    }

    public static boolean inclodeNine(String value){
        boolean status = false;
        if (!isEmpty(value)) {
            status = value.contains("99");
        }
        return status;
    }

    public static boolean isEmpty(String value) {
        boolean flag = false;
        if(StringUtil.isEmpty(value)){
            flag = true;
        }
        else if(StringUtil.isEmpty(value.trim())){
            flag = true;
        }
        return flag;
    }
}
