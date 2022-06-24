package com.joyintech.tams.report.submitmaintenance.salerideninfo.dto;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.ChbSalerIdenInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.validate.SalerIdenInfoValid;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

@ValidLogic(targetClass = SalerIdenInfoValid.class, groups = {ValidateGroupEdit.class})
public class SalerIdenInfoDto extends ChbSalerIdenInfo implements ProcessEntity {
    private String operateType;//操作类型
    private String elementId;//页面元素
    private String pageModel;//页面模型
    private String disabledThing;//置灰控制
    private String erroOrInfo;//来自erro还是info
    private String dataStatus;//来自erro还是info
    /**
     * 原始数据
     */
    private String orgData;
    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 选中的数据
     */
    private transient List<SalerIdenInfoDto> selectedItem;

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public List<SalerIdenInfoDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<SalerIdenInfoDto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getDataStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.维护数据状态, this.getDataStatus());
    }

    public String getSalerSexStr() {
        return this.getSalerSex() + " " + SysDataUtil.getNameFromDataDict(DictCode.性别_中债, this.getSalerSex());
    }

    public String getSalerCredentTypeStr() {
        return this.getSalerCredentType() + " " +  SysDataUtil.getNameFromDataDict(DictCode.销售人员证件类型, this.getSalerCredentType());
    }

    public String getSalerAreaTypeStr() {
        return this.getSalerAreaType() + " " +  SysDataUtil.getNameFromDataDict(DictCode.销售区域_中债, this.getSalerAreaType());
    }

    public String getSalerDegreeStr() {
        return this.getSalerDegree() + " " +  SysDataUtil.getNameFromDataDict(DictCode.销售人员学位, this.getSalerDegree());
    }

    public String getSalerEduLevelStr() {
        return this.getSalerEduLevel() + " " +  SysDataUtil.getNameFromDataDict(DictCode.销售人员学历, this.getSalerEduLevel());
    }

    public String getSalerLevleStr() {
        return this.getSalerLevle() + " " +  SysDataUtil.getNameFromDataDict(DictCode.销售人员专业技术职称, this.getSalerLevle());
    }

    public String getSalerRiskLevelStr() {
        if(this.getSalerRiskLevel() != null && this.getSalerRiskLevel() != ""){
            int SalerRiskLevelNum = ciShu(this.getSalerRiskLevel(),",");
            if(SalerRiskLevelNum == 0){
                return this.getSalerRiskLevel() + " " +  SysDataUtil.getNameFromDataDict(DictCode.可销售产品风险等级, this.getSalerRiskLevel());
            }else{
                String SalerRiskLevelSr = this.getSalerRiskLevel().replace(",","");
                String SalerRiskLevel = "无";
                for(int i = 0;i < (SalerRiskLevelNum + 1);i++){
                    int j = (i+1) * 2;
                    if(SalerRiskLevel.equals("无")){
                        SalerRiskLevel =SalerRiskLevelSr.substring(j-2,j) + " " + SysDataUtil.getNameFromDataDict(DictCode.可销售产品风险等级, SalerRiskLevelSr.substring(j-2,j));
                    }else{
                        SalerRiskLevel = SalerRiskLevel + "," + SalerRiskLevelSr.substring(j-2,j) + " " + SysDataUtil.getNameFromDataDict(DictCode.可销售产品风险等级, SalerRiskLevelSr.substring(j-2,j));
                    }
                }
                return SalerRiskLevel;
            }
        }
        return null;
    }

    public String getSalerStatusStr() {
        return this.getSalerStatus() + " " +  SysDataUtil.getNameFromDataDict(DictCode.销售人员当前状态, this.getSalerStatus());
    }

    public String getSalerCertificateTypeStr() {
        if(this.getSalerCertificateType() != null && this.getSalerCertificateType() != ""){
           int SalerCertificateTypeNum = ciShu(this.getSalerCertificateType(),",");
           if(SalerCertificateTypeNum == 0){
               return this.getSalerCertificateType() + " " +  SysDataUtil.getNameFromDataDict(DictCode.销售人员理财专业证书, this.getSalerCertificateType());
           }else{
               String SalerCertificateTypeSr = this.getSalerCertificateType().replace(",","");
               String SalerCertificateType = "无";
               for(int i = 0;i < (SalerCertificateTypeNum + 1);i++){
                  int j = (i+1) * 2;
                  if(SalerCertificateType.equals("无")){
                      SalerCertificateType =SalerCertificateTypeSr.substring(j-2,j) + " " + SysDataUtil.getNameFromDataDict(DictCode.销售人员理财专业证书, SalerCertificateTypeSr.substring(j-2,j));
                  }else{
                      SalerCertificateType = SalerCertificateType + "," + SalerCertificateTypeSr.substring(j-2,j) + " " + SysDataUtil.getNameFromDataDict(DictCode.销售人员理财专业证书, SalerCertificateTypeSr.substring(j-2,j));
                  }
               }
               return SalerCertificateType;
           }
        }
        return null;
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

    public String getSalerBirthdayStr() {
        return DateUtil.format(this.getSalerBirthday());
    }

    /**
     * 判断str1中包含str2的个数
     * @param str1
     * @param str2
     * @return counter
     */
    public static int ciShu(String str1,String str2){
        int counter = 0;
        for(int i = 0;i < str1.length() ;i++) {
            int index = str1.indexOf(str2, i);
            if (i == index) {
                counter++;
            }
        }
        return counter;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }
}