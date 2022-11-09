/*
 * 文件名：RskValtblAssetSubMapDtlDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月25日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto;

import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RskValtblAssetSubMapDtl;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.valid.RskValtblAssetSubMapDtlValidator;

import java.util.List;

/**
 * 资产科目映射明细DTO 
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月25日
 * @see RskValtblAssetSubMapDtlDto
 * @since 1.0
 */
@ValidLogic(targetClass = RskValtblAssetSubMapDtlValidator.class, groups = {ValidateGroupEdit.class,
    ValidateGroupDelete.class})
public class RskValtblAssetSubMapDtlDto extends RskValtblAssetSubMapDtl{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 目标科目代码名称

     */
    private String tgtName;

    
    /**
     * 源科目代码(旧)

     */
    private String srcCodeOld;
    
    /**
     * 操作类型
     */
    private String operateType;
    
    
    /**
     * 选中的记录
     */
    private transient List<RskValtblAssetSubMapDtlDto> selectedItem;

    
    /**
     * 获取 srcCodeOld
     * @return srcCodeOld.
     */
    public String getSrcCodeOld() {
        return srcCodeOld;
    }

    
    /**
     * 设置 srcCodeOld
     * @param srcCodeOld srcCodeOld
     */
    public void setSrcCodeOld(String srcCodeOld) {
        this.srcCodeOld = srcCodeOld;
    }
    
    public String getAllowSumName(){
        return SysDataUtil.getNameFromDataDict(DictCode.是否, this.getAllowSum());
    }


    
    /**
     * 获取 selectedItem
     * @return selectedItem.
     */
    public List<RskValtblAssetSubMapDtlDto> getSelectedItem() {
        return selectedItem;
    }


    
    /**
     * 设置 selectedItem
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<RskValtblAssetSubMapDtlDto> selectedItem) {
        this.selectedItem = selectedItem;
    }


    
    /**
     * 获取 operateType
     * @return operateType.
     */
    public String getOperateType() {
        return operateType;
    }


    
    /**
     * 设置 operateType
     * @param operateType operateType
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    @Override
    @NotEmpty(fieldName = "RskValAssetCode004.srcCode", groups = {ValidateGroupEdit.class})
    public String getSrcCode(){
        return super.getSrcCode();
    }
    
    
    @Override
    @NotEmpty(fieldName = "RskValAssetCode004.tgtCode", groups = {ValidateGroupEdit.class})
    public String getTgtCode(){
        return super.getTgtCode();
    }


    
    /**
     * 获取 tgtName
     * @return tgtName.
     */
    public String getTgtName() {
        return tgtName;
    }


    
    /**
     * 设置 tgtName
     * @param tgtName tgtName
     */
    public void setTgtName(String tgtName) {
        this.tgtName = tgtName;
    }
    
    
    
}
