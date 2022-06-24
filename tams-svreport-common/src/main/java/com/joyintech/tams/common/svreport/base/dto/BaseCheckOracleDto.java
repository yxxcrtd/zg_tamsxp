package com.joyintech.tams.common.svreport.base.dto;


/**
 * 表的定义
 * @author zhoubin
 */
public class BaseCheckOracleDto {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 列名
     */
    private String columnName;
    /**
     * 字段类型
     */
    private String dataType;
    /**
     * 字段长度
     */
    private Integer length;

    /**
     * 数字长度
     */
    private Integer numberLength;

    /**
     * 小数点长度
     */
    private Integer  dataScale;
    /**
     * 字段是否为null
     */
    private String nullAble;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getNumberLength() {
        return numberLength;
    }

    public void setNumberLength(Integer numberLength) {
        this.numberLength = numberLength;
    }

    public String getNullAble() {
        return nullAble;
    }

    public void setNullAble(String nullAble) {
        this.nullAble = nullAble;
    }

    public Integer getDataScale() {
        return dataScale;
    }

    public void setDataScale(Integer dataScale) {
        this.dataScale = dataScale;
    }
}
