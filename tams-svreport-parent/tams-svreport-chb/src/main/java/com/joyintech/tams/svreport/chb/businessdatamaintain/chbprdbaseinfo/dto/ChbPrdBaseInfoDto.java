package com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.validator.ChbPrdBseInfoValidator;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wubingbing
 * @date 2022-05-09 10:41
 */
@PageId("CchbPrdBaseInfo002")
@ValidLogic(targetClass = ChbPrdBseInfoValidator.class, groups = { ValidateGroupEdit.class,
        ValidateGroupDelete.class })
public class ChbPrdBaseInfoDto extends ChbPrdBaseinfo implements ProcessEntity {
    /**
     * 现金管理类产品-是否_中债完全符合
     */
    private String manaCateFit;
    /**
     * 状态备注
     */
    private String issueStatusRemark;
    /**
     * 定期开放周期(天)
     */
    private BigDecimal fitCircleDays;
    /**
     * 操作类型
     */
    private String operateType;

    private List<ChbPrdBaseInfoDto> selectedItem;

    /**
     * 目的和意义以及可能会用到的地方
     */
    private String pageModel;

    /**
     * 要素id
     */
    private String elementId;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 有效状态
     */
    private String statusValid;
    /**
     * 新发行状态
     */
    private String issuingStatusNew;

    private String orgData;

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

    public String getStatusValid() {
        return statusValid;
    }

    public void setStatusValid(String statusValid) {
        this.statusValid = statusValid;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public List<ChbPrdBaseInfoDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<ChbPrdBaseInfoDto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getPageModel() {
        return pageModel;
    }

    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public String getupdateTimeStr(){
        return DateUtil.format(getUpdateTime());
    }

    public String getManaCateFit() {
        return manaCateFit;
    }

    public void setManaCateFit(String manaCateFit) {
        this.manaCateFit = manaCateFit;
    }

    public String getIssueStatusRemark() {
        return issueStatusRemark;
    }

    public void setIssueStatusRemark(String issueStatusRemark) {
        this.issueStatusRemark = issueStatusRemark;
    }

    public BigDecimal getFitCircleDays() {
        return fitCircleDays;
    }

    public void setFitCircleDays(BigDecimal fitCircleDays) {
        this.fitCircleDays = fitCircleDays;
    }

    @PrimaryKey(PrimaryKey.Field.PK_1)
    public String getProdId(){
        return super.getProdId();
    }

    public String getRaiseVdateStr(){
        return DateUtil.format(getRaiseVdate());
    }
    public String getRaiseEnddateStr(){
        return DateUtil.format(getRaiseEnddate());
    }
    public String getRaisePstrdatestr(){
        return DateUtil.format(getRaisePstrdate());
    }
    public String getRaisePenddateStr(){
        return DateUtil.format(getRaisePenddate());
    }
    public String getVdateStr(){
        return DateUtil.format(getVdate());
    }
    public String getMdateStr(){
        return DateUtil.format(getMdate());
    }
    public String getManageTypeStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.管理方式_发行登记,getManageType());
    }
    public String getIsConstracteStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债,getIsConstracte());
    }
    public String getOpenModeStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.开放模式_中债,getOpenMode());
    }
    public String getOpenCircleStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.规律开放周期,getOpenCircle());
    }
    public String getHolidayOnFlagStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债,getHolidayOnFlag());
    }
    public String getOpenBussStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.开放期业务,getOpenBuss());
    }
    public String getProdtypeStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.产品类型_中债,getProdtype());
    }
    public String getBasicStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.计息基础_中债,getBasic());
    }
    public String getIssuingStatusStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.报送产品发行状态,getIssuingStatus());
    }
    public String getFiduciaryDutyStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.产品受托职责_中债,getFiduciaryDuty());
    }
    public String getTransferTypeStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.产品转让类型_中债,getTransferType());
    }
    public String getCustomerTypeStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.人行客户类型_SVR,getCustomerType());
    }
    public String getZjxtFlagStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债,getZjxtFlag());
    }
    public String getFirstOpenDayStr(){
        return DateUtil.format(getFirstOpenDay());
    }
    public String getTerminatedateStr(){
        return DateUtil.format(getTerminatedate());
    }

    public String getIssuingStatusNew() {
        return issuingStatusNew;
    }

    public void setIssuingStatusNew(String issuingStatusNew) {
        this.issuingStatusNew = issuingStatusNew;
    }

    public String getIsTransferStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债,getIsTransfer());
    }
    public String getManaCateFitStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.是否_中债,getManaCateFit());
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }
}
