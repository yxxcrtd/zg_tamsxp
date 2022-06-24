package com.joyintech.tams.common.svreport.base.dto;

import java.io.Serializable;

/**
 *数据库表的列对象
 * @author wubingbing
 * @date 2022-03-31 15:01
 */
public class BaseCheckMysqlDto {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 顺序
     */
    private String ordinalPosition;
    /**
     * 是否为空
     */
    private String isNullable;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * 字符串最大长度
     */
    private String characterMaximumLength;
    private String numericPrecision;
    private String numericScale;
    /**
     * 索引
     */
    private String columnType;
    /**
     * 注解
     */
    private String columnComment;
    /**
     * 列权限
     */
    private String privileges;
    /**
     * 索引类型
     */
    private String columnKey;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(String ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(String characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    public String getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(String numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public String getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(String numericScale) {
        this.numericScale = numericScale;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }
}
