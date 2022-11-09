/*
 * 文件名：PriviewParamDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月27日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalassetcode.dto;


/**
 *  预览参数DTO
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月27日
 * @see PriviewParamDto
 * @since 1.0
 */
public class PriviewParamDto {

    /**
     * 文件名
     */
    private String fileName;
    
    /**
     * 起始行
     */
    private int startRow;


    /**
     * 科目代码列
     */
    private int assetCodeColIndex;

    /**
     * 科目代码列(字母)
     */
    private String assetCodeColIndexLetter;
    
    /**
     * 科目代码级别
     */
    private String assetCodeType;
    
    /**
     * 过滤器ID
     */
    private String filterId;
    
    /**
     * 获取 fileName
     * @return fileName.
     */
    public String getFileName() {
        return fileName;
    }

    
    /**
     * 设置 fileName
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    
    /**
     * 获取 startRow
     * @return startRow.
     */
    public int getStartRow() {
        return startRow;
    }


    
    /**
     * 设置 startRow
     * @param startRow startRow
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }


    
    /**
     * 获取 assetCodeColIndex
     * @return assetCodeColIndex.
     */
    public int getAssetCodeColIndex() {
        return assetCodeColIndex;
    }


    
    /**
     * 设置 assetCodeColIndex
     * @param assetCodeColIndex assetCodeColIndex
     */
    public void setAssetCodeColIndex(int assetCodeColIndex) {
        this.assetCodeColIndex = assetCodeColIndex;
    }


    
    /**
     * 获取 assetCodeType
     * @return assetCodeType.
     */
    public String getAssetCodeType() {
        return assetCodeType;
    }


    
    /**
     * 设置 assetCodeType
     * @param assetCodeType assetCodeType
     */
    public void setAssetCodeType(String assetCodeType) {
        this.assetCodeType = assetCodeType;
    }


	public String getFilterId() {
		return filterId;
	}


	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}


    public String getAssetCodeColIndexLetter() {
        return assetCodeColIndexLetter;
    }

    public void setAssetCodeColIndexLetter(String assetCodeColIndexLetter) {
        this.assetCodeColIndexLetter = assetCodeColIndexLetter;
    }
}
