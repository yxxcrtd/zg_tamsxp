/*
 * 文件名：RskValtblAssetSubMapDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月26日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto;

import com.joyintech.framework.validation.annotation.Length;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RskValtblAssetSubMap;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.valid.RskValtblAssetSubMapValidator;

import java.util.List;

/**
 * 资产科目映射明细DTO
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月26日
 * @see RskValtblAssetSubMapDto
 * @since 1.0
 */
@ValidLogic(targetClass = RskValtblAssetSubMapValidator.class, groups = {ValidateGroupEdit.class,
    ValidateGroupDelete.class})
public class RskValtblAssetSubMapDto extends RskValtblAssetSubMap{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 操作类型
     */
    private String operateType;
    
    private String pageModel;
    
    /**
     * 选中的记录
     */
    private transient List<RskValtblAssetSubMapDto> selectedItem;

    @Override
    @Length(fieldName = "RskValAssetCode002.name", max = 200, groups = {ValidateGroupEdit.class})
    @NotEmpty(fieldName = "RskValAssetCode002.name", groups = {ValidateGroupEdit.class})
    public String getName(){
        return super.getName();
    }
    
    /**
     * 获取 pageModel
     * @return pageModel.
     */
    public String getPageModel() {
        return pageModel;
    }

    
    /**
     * 设置 pageModel
     * @param pageModel pageModel
     */
    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
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

    
    /**
     * 获取 selectedItem
     * @return selectedItem.
     */
    public List<RskValtblAssetSubMapDto> getSelectedItem() {
        return selectedItem;
    }

    
    /**
     * 设置 selectedItem
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<RskValtblAssetSubMapDto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    
    
    
    

}
