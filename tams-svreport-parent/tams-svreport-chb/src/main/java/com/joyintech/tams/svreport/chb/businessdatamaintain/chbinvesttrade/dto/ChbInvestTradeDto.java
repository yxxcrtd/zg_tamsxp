package com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.ChbInvestTrade;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.validator.ChbInvestTradeValidator;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

/**
 * @author wubingbing
 * @date 2022-04-27 14:09
 */
@PageId("CchbInvestTrade002")
@ValidLogic(targetClass = ChbInvestTradeValidator.class, groups = { ValidateGroupEdit.class,
        ValidateGroupDelete.class })
public class ChbInvestTradeDto extends ChbInvestTrade implements ProcessEntity {
    private String prodName;
    private String regCode;
    private String assetName;
    private String orgData;


    /**
     * 操作类型
     */
    private String operateType;

    private List<ChbInvestTradeDto> selectedItem;

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

    public List<ChbInvestTradeDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<ChbInvestTradeDto> selectedItem) {
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
    @PrimaryKey(PrimaryKey.Field.PK_1)
    public String getTradeCode(){
        return super.getTradeCode();
    }
    @PrimaryKey(PrimaryKey.Field.PK_2)
    public String getProdId(){
        return super.getProdId();
    }
    @PrimaryKey(PrimaryKey.Field.PK_3)
    public String getRegistBankCode(){
        return super.getRegistBankCode();
    }
    @PrimaryKey(PrimaryKey.Field.PK_4)
    public String getAssetCode(){
        return super.getAssetCode();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    public String getCcyStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.币种,getCcy());
    }
    public String getAssetMethodStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.资产计量方式,getAssetMethod());
    }
    public String getCurAssetTypeStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.资金流动类型,getCurAssetType());
    }
    public String getRivalPartyTypeStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.交易对手类型,getRivalPartyType());
    }
    public String getDealdateStr(){
        return null !=getDealdate()? DateUtil.format(getDealdate()):null;
    }
    public String getupdateTimeStr(){
        return DateUtil.formatDateTime(getUpdateTime());
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }
}
