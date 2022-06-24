package com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;

import com.joyintech.tams.common.base.entity.MstBondCreditRating;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.validator.ChbMstBondCreditRatingValidator;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

/**
 * 债券信用评级
 * @TableName MST_BOND_CREDIT_RATING
 */
@PageId("ChbMstBondCreditRating002")
@ValidLogic(targetClass = ChbMstBondCreditRatingValidator.class, groups = {ValidateGroupEdit.class,
        ValidateGroupDelete.class})
public class ChbMstBondCreditRatingDto extends MstBondCreditRating implements ProcessEntity {

    /**
     * 数据来源 
     */
    private String dataSource;


    /**
     * 状态
     */
    private String status;

    /**
     * 债券名称
     */
    private String oName;

    /**
     * 描述
     */
    private String errMsg;
    /**
     * 操作类型
     */
    private String operateType;

    private List<ChbMstBondCreditRatingDto> selectedItem;

    /**
     * 目的和意义以及可能会用到的地方
     */
    private String pageModel;

    /**
     * 要素id
     */
    private String elementId;

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


    private static final long serialVersionUID = 1L;

    @PrimaryKey(PrimaryKey.Field.PK_3)
    public String getGradeDateStr() {
        return DateUtil.format(super.getGradeDate());
    }


    @PrimaryKey(PrimaryKey.Field.PK_1)
    public String getoCode(){
        return super.getoCode();
    }
    @PrimaryKey(PrimaryKey.Field.PK_2)
    public String getGradeOrgId(){
        return super.getGradeOrgId();
    }

    @PrimaryKey(PrimaryKey.Field.PK_4)
    public String getoType(){
        return super.getoType();
    }

    /**
     * 数据来源 
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 数据来源 
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 债券名称
     */
    public String getoName() {
        return oName;
    }

    public String getSource() {
        return super.getSource();
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public List<ChbMstBondCreditRatingDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<ChbMstBondCreditRatingDto> selectedItem) {
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

    public String getGradeOrgIdStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.评级机构,getGradeOrgId());
    }



    public String getShortLongTermStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.长期短期,getShortLongTerm());
    }



    public String getGradeResultStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.评级结果,getGradeResult());
    }


    public String getSourceStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.评级来源类型,getSource());
    }


    public String getDataSourceStr() {
        return null!=dataSource&&dataSource.equals("D")?"新建导入":"接口";
    }


    public String getStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,status);
    }


    public String getoTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.评级类型,getoType());
    }

    public void setoName(String oName) {
        this.oName = oName;
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


}