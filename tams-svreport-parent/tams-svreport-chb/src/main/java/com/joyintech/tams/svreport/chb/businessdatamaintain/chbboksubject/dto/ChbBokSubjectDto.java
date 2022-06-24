package com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.validation.annotation.Length;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.BokSubject;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.validator.ChbBokSubjectDtoValidator;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.List;

/**
 * @author wubingbing
 * @date 2022-03-28 14:42
 */
@PageId("ChbBokSubject002")
@ValidLogic(targetClass = ChbBokSubjectDtoValidator.class, groups = {ValidateGroupEdit.class,
        ValidateGroupDelete.class})

public class ChbBokSubjectDto extends BokSubject implements ProcessEntity {



    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private String status;
    /**
     * 错误信息
     */
    private String errMsg;


    /**
     * 要素id
     */
    private String elementId;


    private String operateType;

    private List<ChbBokSubjectDto> selectedItem;

    /**
     * 目的和意义以及可能会用到的地方
     */
    private String pageModel;
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
    @PrimaryKey(Field.PK_1)
    public String getBooksetId(){
        return super.getBooksetId();
    }
    @PrimaryKey(Field.PK_2)
    public String getSubjectNo() {
        return super.getSubjectNo();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public List<ChbBokSubjectDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<ChbBokSubjectDto> selectedItem) {
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

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTimeStr() {
        return DateUtil.format(super.getUpdateTime());
    }

    public String getSubjectTypeStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.科目类型,super.getSubjectType());
    }


    public String getDataSourceStr() {
        return  null!=dataSource&&dataSource.equals("D")?"新建导入":"接口";
    }


    public String getStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,status);
    }

    public String getSubjectLevelStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.科目级别,super.getSubjectLevel().toString());
    }
    public String getBalFlagStr(){
        return SysDataUtil.getNameFromDataDict(DictCode.余额方向,super.getBalFlag());
    }
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


}
