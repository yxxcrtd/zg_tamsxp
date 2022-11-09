/*
 * 文件名：RskValtblAssetSubDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.valtblimport.dto;

import com.joyintech.tams.common.base.entity.RskOutValtblImportHis;
import com.joyintech.tams.common.base.entity.RskValtblAssetSub;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.systemtools.util.SysDatadictUtil;

import java.util.List;
import java.util.Queue;

/**
 *  估值表资产科目代码DTO
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月29日
 * @see RskValtblAssetSubDto
 * @since 1.0
 */
public class RskValtblAssetSubDto extends RskValtblAssetSub implements Comparable<RskValtblAssetSubDto>{

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
    /*@JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal cost;*/
    
    private ValType valType;
    
    /**
     * 科目资产来源
     */
    private String assetSource;
    
    /**
     * 科目市场代码
     */
    private String finprodMarketId;
    
    private List<RskValtblAssetSubDto>  selectedItem;
    
    /**
     * 是否写入底层资产表
     */
    private boolean isWritebotAssetMap;
    
    /**
     * 是否更新底层资产表
     */
    private boolean isUpdatebotAssetMap;
    
    /**
     * 债券市场代码
     */
    private String marketId;
    /**
     * 是否关联上内部科目
     */
    private String isMatch;

    public String getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(String isMatch) {
        this.isMatch = isMatch;
    }
    /**
     * 是否关联上内部科目
     */
    private String isMatchStr;

    public String getIsMatchStr() {
        return SysDatadictUtil.getNameFromDataDict(DictCode.是否,isMatch);
    }
    public ValType getValType() {
        return valType;
    }
    public void setValType(ValType valType) {
        this.valType = valType;
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
     * 获取 isWritebotAssetMap
     * @return isWritebotAssetMap.
     */
    public boolean isWritebotAssetMap() {
        return isWritebotAssetMap;
    }
    
    /**
     * 设置 isWritebotAssetMap
     * @param isWritebotAssetMap isWritebotAssetMap
     */
    public void setWritebotAssetMap(boolean isWritebotAssetMap) {
        this.isWritebotAssetMap = isWritebotAssetMap;
    }
    
    
    
    /**
     * 获取 assetSource
     * @return assetSource.
     */
    public String getAssetSource() {
        return assetSource;
    }
    
    /**
     * 设置 assetSource
     * @param assetSource assetSource
     */
    public void setAssetSource(String assetSource) {
        this.assetSource = assetSource;
    }
    
    
    
    /**
     * 获取 finprodMarketId
     * @return finprodMarketId.
     */
    public String getFinprodMarketId() {
        return finprodMarketId;
    }
    
    /**
     * 设置 finprodMarketId
     * @param finprodMarketId finprodMarketId
     */
    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }
    
    
    
    /**
     * 获取 isUpdatebotAssetMap
     * @return isUpdatebotAssetMap.
     */
    public boolean isUpdatebotAssetMap() {
        return isUpdatebotAssetMap;
    }
    
    /**
     * 设置 isUpdatebotAssetMap
     * @param isUpdatebotAssetMap isUpdatebotAssetMap
     */
    public void setUpdatebotAssetMap(boolean isUpdatebotAssetMap) {
        this.isUpdatebotAssetMap = isUpdatebotAssetMap;
    }
    
    public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	/**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(RskValtblAssetSubDto por) {
        if(this.valType.toString().compareTo(por.getValType().toString())==0){
            return this.getSubCode().compareTo(por.getSubCode());
        }else{
            return this.valType.toString().compareTo(por.getValType().toString());
        }
    }

 	private Queue<RskOutValtblImportHis> importHisQueue;

    public Queue<RskOutValtblImportHis> getImportHisQueue() {
        return importHisQueue;
    }

    public void setImportHisQueue(Queue<RskOutValtblImportHis> importHisQueue) {
        this.importHisQueue = importHisQueue;
    }
}
