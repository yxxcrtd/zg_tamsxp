package com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.validate;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dto.SalerRewPenaInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.service.SalerRewPenaInfo002Service;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class SalerRewPenaInfoValid implements BeanValidator {
    /**
     * 获取service对象
     */
    @Autowired
    private SalerRewPenaInfo002Service salerRewPenaInfo002Service;

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        SalerRewPenaInfoDto dto = (SalerRewPenaInfoDto)value;
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();
        if(dto.getPageModel() != null && dto.getPageModel() != "" &&
                (StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_NEW) || StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_EDIT))){
            //销售机构代码
            String orgCode = CommonUtil.getOrgCode();
            String issuePartyId = salerRewPenaInfo002Service.selectIssuePartyId(orgCode);
            if(dto.getSalerInstCode() != null && dto.getSalerInstCode() != ""){
                    if(issuePartyId != null && issuePartyId != ""){
                        if(!StringUtil.equals(issuePartyId,dto.getSalerInstCode())){
                            errorList.add(new ValidMessage("salerInstCode", "销售机构代码非本行销售机构代码。"));
                        }
                    }
            }
            //内部编号
            if(dto.getSalerInsNo() != null && dto.getSalerInsNo() != ""){
                if(salerRewPenaInfo002Service.checkSalerInsNo(dto.getSalerInsNo()) == 0) {
                    errorList.add(new ValidMessage("salerInsNo", "内部编号未被维护。"));
                }
            }
            //奖惩名称
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerRewPenaName() != null && dto.getSalerRewPenaName() != "") {
                        errorList.add(new ValidMessage("salerRewPenaName", "当“记录分类”选择投诉时,奖惩名称不可填。"));
                    }
                }else{
                    if(dto.getSalerRewPenaName() == null || dto.getSalerRewPenaName() == "") {
                        errorList.add(new ValidMessage("salerRewPenaName", "当“记录分类”选择奖励或惩处时,奖惩名称不可为空。"));
                    }
                }
            }
            //奖惩说明
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerRewPenaComments() != null && dto.getSalerRewPenaComments() != "") {
                        errorList.add(new ValidMessage("salerRewPenaComments", "当“记录分类”选择投诉时,奖惩说明不可填。"));
                    }
                }else{
                    if(dto.getSalerRewPenaComments() == null || dto.getSalerRewPenaComments() == "") {
                        errorList.add(new ValidMessage("salerRewPenaComments", "当“记录分类”选择奖励或惩处时,奖惩说明不可为空。"));
                    }
                }
            }
            //奖惩单位
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerRewPenaInst() != null && dto.getSalerRewPenaInst() != "") {
                        errorList.add(new ValidMessage("salerRewPenaInst", "当“记录分类”选择投诉时,奖惩单位不可填。"));
                    }
                }else{
                    if(dto.getSalerRewPenaInst() == null || dto.getSalerRewPenaInst() == "") {
                        errorList.add(new ValidMessage("salerRewPenaInst", "当“记录分类”选择奖励或惩处时,奖惩单位不可为空。"));
                    }
                }
            }
            //奖惩单位具体名称
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (!StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerRewPenaInst() != null && dto.getSalerRewPenaInst() != "" && StringUtil.equals(dto.getSalerRewPenaInst(),"99")) {
                        if(dto.getSalerRewPenaInstName() == null || dto.getSalerRewPenaInstName() == "") {
                            errorList.add(new ValidMessage("salerRewPenaInstName", "当“记录分类”选择奖励或惩处且奖惩单位选择其他时，奖惩单位具体名称不可为空。"));
                        }
                    }else if(dto.getSalerRewPenaInst() != null && dto.getSalerRewPenaInst() != "" && !StringUtil.equals(dto.getSalerRewPenaInst(),"99")){
                        if(dto.getSalerRewPenaInstName() != null && dto.getSalerRewPenaInstName() != "") {
                            errorList.add(new ValidMessage("salerRewPenaInstName", "当“记录分类”选择奖励或惩处且奖惩单位选择非其他时，奖惩单位具体名称不可填。"));
                        }
                    }
                }else{
                    if(dto.getSalerRewPenaInstName() != null && dto.getSalerRewPenaInstName() != "") {
                        errorList.add(new ValidMessage("salerRewPenaInstName", "当“记录分类”选择投诉时，奖惩单位具体名称不可填。"));
                    }
                }
            }
            //奖惩起始日期
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerRewPenaStart() != null) {
                        errorList.add(new ValidMessage("salerRewPenaStart", "当“记录分类”选择投诉时,奖惩起始日期不可填。"));
                    }
                }else{
                    if(dto.getSalerRewPenaStart() == null) {
                        errorList.add(new ValidMessage("salerRewPenaStart", "当“记录分类”选择奖励或惩处时,奖惩起始日期不可为空。"));
                    }
                }
            }
            //奖惩结束日期
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerRewPenaEnd() != null) {
                        errorList.add(new ValidMessage("salerRewPenaEnd", "当“记录分类”选择投诉时,奖惩结束日期不可填。"));
                    }
                }else{
                    if(dto.getSalerRewPenaEnd() == null) {
                        errorList.add(new ValidMessage("salerRewPenaEnd", "当“记录分类”选择奖励或惩处时,奖惩结束日期不可为空。"));
                    }
                }
            }
            //奖惩结束日期与奖惩开始日期校验
            if(dto.getSalerRewPenaStart() != null && dto.getSalerRewPenaEnd() != null) {
                if (dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                    if (!StringUtil.equals(dto.getSalerRewPenaTyep(),"03")) {
                        Integer i = dto.getSalerRewPenaStart().compareTo(dto.getSalerRewPenaEnd());
                        if (i > 0) {
                            errorList.add(new ValidMessage("salerRewPenaEnd", "奖惩结束日期不可小于奖惩开始日期。"));
                        }
                    }
                }
            }
            //投诉详情
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerComplainDetail() == null || dto.getSalerComplainDetail() == "") {
                        errorList.add(new ValidMessage("salerComplainDetail", "当“记录分类”选择投诉时,投诉详情不可为空。"));
                    }
                }else{
                    if(dto.getSalerComplainDetail() != null && dto.getSalerComplainDetail() != "") {
                        errorList.add(new ValidMessage("salerComplainDetail", "当“记录分类”选择奖励或惩处时,投诉详情不可填。"));
                    }
                }
            }
            //投诉处理结果
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerComplainResult() == null || dto.getSalerComplainResult() == "") {
                        errorList.add(new ValidMessage("salerComplainResult", "当“记录分类”选择投诉时,投诉处理结果不可为空。"));
                    }
                }else{
                    if(dto.getSalerComplainResult() != null && dto.getSalerComplainResult() != "") {
                        errorList.add(new ValidMessage("salerComplainResult", "当“记录分类”选择奖励或惩处时,投诉处理结果不可填。"));
                    }
                }
            }
            //投诉日期
            if(dto.getSalerRewPenaTyep() != null && dto.getSalerRewPenaTyep() != "") {
                if (StringUtil.equals(dto.getSalerRewPenaTyep(),"03")){
                    if(dto.getSalerComplainDate() == null) {
                        errorList.add(new ValidMessage("salerComplainDate", "当“记录分类”选择奖励或惩处时,投诉日期日期不可为空。"));
                    }
                }else{
                    if(dto.getSalerComplainDate() != null) {
                        errorList.add(new ValidMessage("salerComplainDate", "当“记录分类”选择投诉时,投诉日期日期不可填。"));
                    }
                }
            }
        }
        if(dto.getPageModel() != null && dto.getPageModel() != "" && StringUtil.equals(dto.getPageModel(),GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){
        }
        //调用数据库配置脚本check方法
        errorList = chbDataCheckCommonUtil.chbCheckCommon(errorList, dto, "SalerRewPenaInfo002", null, ValidMessage.class);
        return errorList;
    }
}
