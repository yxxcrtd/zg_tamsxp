/*
 * 文件名：RskValTableImportParam.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.valtblimport.dto;

import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RskOutValtblImportHis;
import com.joyintech.tams.invest.risk.valtblimport.valid.RskValTableImportParamValidator;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *  估值表导入预览参数Dto
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月29日
 * @see RskValTableImportParamDto
 * @since 1.0
 */
@ValidLogic(targetClass = RskValTableImportParamValidator.class, groups = {ValidateGroupEdit.class})
public class RskValTableImportParamDto {

    private String operateType;
    /**
     * 组合代码
     */
    private String ptlId;
    
    /**
     * 估值日期
     */
    private Date valDate;
    
    /**
     * 文件名
     */
    private String valTableFileName;
    
    private String structMapId;
    
    private String sumsubMapId;
    
    private String assetSubMapId;
    
    private String subjectCodeCol;
    
    private String startRow;
    
    private String trimChars;
    
    //---add by 裴家俊 20190514------
    private String filterId;

    // add by swwan 20210615 start
    private String assetName;

    private Map<String, Integer> warnMap;
    // add by swwan 20210615 end

	private List<RskValtblAssetSubDto>  selectedItem;

    /**
     * 获取 ptlId
     * @return ptlId.
     */
    public String getPtlId() {
        return ptlId;
    }

    
    /**
     * 设置 ptlId
     * @param ptlId ptlId
     */
    public void setPtlId(String ptlId) {
        this.ptlId = ptlId;
    }

    
    /**
     * 获取 valDate
     * @return valDate.
     */
    public Date getValDate() {
        return valDate;
    }

    
    /**
     * 设置 valDate
     * @param valDate valDate
     */
    public void setValDate(Date valDate) {
        this.valDate = valDate;
    }

    
    /**
     * 获取 valTableFileName
     * @return valTableFileName.
     */
    public String getValTableFileName() {
        return valTableFileName;
    }

    
    /**
     * 设置 valTableFileName
     * @param valTableFileName valTableFileName
     */
    public void setValTableFileName(String valTableFileName) {
        this.valTableFileName = valTableFileName;
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
     * 获取 structMapId
     * @return structMapId.
     */
    public String getStructMapId() {
        return structMapId;
    }


    
    /**
     * 设置 structMapId
     * @param structMapId structMapId
     */
    public void setStructMapId(String structMapId) {
        this.structMapId = structMapId;
    }


    
    /**
     * 获取 sumsubMapId
     * @return sumsubMapId.
     */
    public String getSumsubMapId() {
        return sumsubMapId;
    }


    
    /**
     * 设置 sumsubMapId
     * @param sumsubMapId sumsubMapId
     */
    public void setSumsubMapId(String sumsubMapId) {
        this.sumsubMapId = sumsubMapId;
    }


    
    /**
     * 获取 assetSubMapId
     * @return assetSubMapId.
     */
    public String getAssetSubMapId() {
        return assetSubMapId;
    }


    
    /**
     * 设置 assetSubMapId
     * @param assetSubMapId assetSubMapId
     */
    public void setAssetSubMapId(String assetSubMapId) {
        this.assetSubMapId = assetSubMapId;
    }


    
    /**
     * 获取 subjectCodeCol
     * @return subjectCodeCol.
     */
    public String getSubjectCodeCol() {
        return subjectCodeCol;
    }


    
    /**
     * 设置 subjectCodeCol
     * @param subjectCodeCol subjectCodeCol
     */
    public void setSubjectCodeCol(String subjectCodeCol) {
        this.subjectCodeCol = subjectCodeCol;
    }


    
    /**
     * 获取 startRow
     * @return startRow.
     */
    public String getStartRow() {
        return startRow;
    }


    
    /**
     * 设置 startRow
     * @param startRow startRow
     */
    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }


    
    /**
     * 获取 trimChars
     * @return trimChars.
     */
    public String getTrimChars() {
        return trimChars;
    }


    
    /**
     * 设置 trimChars
     * @param trimChars trimChars
     */
    public void setTrimChars(String trimChars) {
        this.trimChars = trimChars;
    }


	public String getFilterId() {
		return filterId;
	}


	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}


    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Map<String, Integer> getWarnMap() {
        return warnMap;
    }

    public void setWarnMap(Map<String, Integer> warnMap) {
        this.warnMap = warnMap;
    }

	public List<RskValtblAssetSubDto> getSelectedItem() {
		return selectedItem;
	}


	public void setSelectedItem(List<RskValtblAssetSubDto> selectedItem) {
		this.selectedItem = selectedItem;
	}

    private Queue<RskOutValtblImportHis> importHisQueue;

    public Queue<RskOutValtblImportHis> getImportHisQueue() {
        return importHisQueue;
    }

    public void setImportHisQueue(Queue<RskOutValtblImportHis> importHisQueue) {
        this.importHisQueue = importHisQueue;
    }
}
