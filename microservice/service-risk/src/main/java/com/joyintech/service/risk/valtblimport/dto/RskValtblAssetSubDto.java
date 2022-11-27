/*
 * 文件名：RskValtblAssetSubDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.risk.valtblimport.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.tams.common.base.entity.RskValtblAssetSub;
import com.joyintech.framework.json.BigDecimalSerializer;

/**
 *  估值表资产科目代码DTO
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月29日
 * @see RskValtblAssetSubDto
 * @since 1.0
 */
public class RskValtblAssetSubDto extends RskValtblAssetSub{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
    

    /**
     * 是否允许汇总
     */
    private String allowSum;
    
    /**
     * 汇总科目下一列
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal cost;
    
    private ValType valType;
    /**
     * 是否关联上内部科目
     */
    private String isMatch;
    
    private List<RskValtblAssetSubDto>  selectedItem;
    

    
    
    
    /**
     * 获取 cost
     * @return cost.
     */
    public BigDecimal getCost() {
        return cost;
    }
    
    /**
     * 设置 cost
     * @param cost cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    
    
    /**
     * 获取 allowSum
     * @return allowSum.
     */
    public String getAllowSum() {
        return allowSum;
    }
    
    /**
     * 设置 allowSum
     * @param allowSum allowSum
     */
    public void setAllowSum(String allowSum) {
        this.allowSum = allowSum;
    }
    
    
    
    
    /**
     * 获取 selectedItem
     * @return selectedItem.
     */
    public List<RskValtblAssetSubDto> getSelectedItem() {
        return selectedItem;
    }
    
    /**
     * 设置 selectedItem
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<RskValtblAssetSubDto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    
    /**
     * 获取 valType
     * @return valType.
     */
    public ValType getValType() {
        return valType;
    }

    
    /**
     * 设置 valType
     * @param valType valType
     */
    public void setValType(ValType valType) {
        this.valType = valType;
    }


    public String getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(String isMatch) {
        this.isMatch = isMatch;
    }
}
