package com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dto;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.ChbSalerIdenInfo;
import com.joyintech.tams.common.base.entity.ChbSalerRewPenaInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.validate.SalerIdenInfoValid;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.validate.SalerRewPenaInfoValid;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

@ValidLogic(targetClass = SalerRewPenaInfoValid.class, groups = {ValidateGroupEdit.class,
        ValidateGroupDelete.class})
public class SalerRewPenaInfoDto extends ChbSalerRewPenaInfo implements ProcessEntity {
    private String operateType;//操作类型
    private String elementId;//页面元素
    private String pageModel;//页面模型
    private String statusValid;//数据状态
    private String disabledThing;//置灰控制
    private String erroOrInfo;//来自erro还是info
    /**
     * 原始数据
     */
    private String orgData;
    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 姓名
     */
    private String salerName;

    /**
     * 选中的数据
     */
    private transient List<SalerRewPenaInfoDto> selectedItem;

    public String getStatusValid() {
        return statusValid;
    }

    public void setStatusValid(String statusValid) {
        this.statusValid = statusValid;
    }

    // updateFlg 02 不展示  01 显示
    public String getUpdateFlg() {
        if(!StringUtil.isEmpty(statusValid) && StringUtil.equals(DictCodeList.维护数据状态_删除.getValue(), statusValid)){
            return "02";
        }else{
            return "01";
        }
    }
    public String getStatusValidStr() {
        return com.joyintech.framework.util.SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,
                getStatusValid());
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public List<SalerRewPenaInfoDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<SalerRewPenaInfoDto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.维护数据状态, this.getStatus());
    }

    public String getSalerRewPenaTyepStr() {
        return this.getSalerRewPenaTyep() + " " +  SysDataUtil.getNameFromDataDict(DictCode.记录分类, this.getSalerRewPenaTyep());
    }

    public String getSalerRewPenaInstStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.奖惩单位, this.getSalerRewPenaInst());
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getPageModel() {
        return pageModel;
    }

    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
    }

    public String getDisabledThing() {
        return disabledThing;
    }

    public void setDisabledThing(String disabledThing) {
        this.disabledThing = disabledThing;
    }

    public String getErroOrInfo() {
        return erroOrInfo;
    }

    public void setErroOrInfo(String erroOrInfo) {
        this.erroOrInfo = erroOrInfo;
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getSalerRewPenaStartStr() {
        return DateUtil.format(this.getSalerRewPenaStart());
    }

    public String getSalerRewPenaEndStr() {
        return DateUtil.format(this.getSalerRewPenaEnd());
    }

    public String getSalerComplainDateStr() {
        return DateUtil.format(this.getSalerComplainDate());
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

}