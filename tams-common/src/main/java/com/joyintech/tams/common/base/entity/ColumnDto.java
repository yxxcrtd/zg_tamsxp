package com.joyintech.tams.common.base.entity;

import java.io.Serializable;

/**
 *数据库表的列对象
 * @author wubingbing
 * @date 2022-03-31 15:01
 */
public class ColumnDto implements Serializable {
    /**
     * 列名
     */
    private String COLUMN_NAME;
    /**
     * 顺序
     */
    private String ORDINAL_POSITION;
    /**
     * 是否为空
     */
    private String IS_NULLABLE;
    /**
     * 数据类型
     */
    private String DATA_TYPE;
    /**
     * 字符串最大长度
     */
    private String CHARACTER_MAXIMUM_LENGTH;
    private String NUMERIC_PRECISION;
    private String NUMERIC_SCALE;
    /**
     * 索引
     */
    private String COLUMN_TYPE;
    /**
     * 注解
     */
    private String COLUMN_COMMENT;
    /**
     * 列权限
     */
    private String PRIVILEGES;

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getORDINAL_POSITION() {
        return ORDINAL_POSITION;
    }

    public void setORDINAL_POSITION(String ORDINAL_POSITION) {
        this.ORDINAL_POSITION = ORDINAL_POSITION;
    }

    public String getIS_NULLABLE() {
        return IS_NULLABLE;
    }

    public void setIS_NULLABLE(String IS_NULLABLE) {
        this.IS_NULLABLE = IS_NULLABLE;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public String getCHARACTER_MAXIMUM_LENGTH() {
        return CHARACTER_MAXIMUM_LENGTH;
    }

    public void setCHARACTER_MAXIMUM_LENGTH(String CHARACTER_MAXIMUM_LENGTH) {
        this.CHARACTER_MAXIMUM_LENGTH = CHARACTER_MAXIMUM_LENGTH;
    }

    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE;
    }

    public String getCOLUMN_COMMENT() {
        return COLUMN_COMMENT;
    }

    public void setCOLUMN_COMMENT(String COLUMN_COMMENT) {
        this.COLUMN_COMMENT = COLUMN_COMMENT;
    }

    public String getPRIVILEGES() {
        return PRIVILEGES;
    }

    public void setPRIVILEGES(String PRIVILEGES) {
        this.PRIVILEGES = PRIVILEGES;
    }

    public String getNUMERIC_PRECISION() {
        return NUMERIC_PRECISION;
    }

    public void setNUMERIC_PRECISION(String NUMERIC_PRECISION) {
        this.NUMERIC_PRECISION = NUMERIC_PRECISION;
    }

    public String getNUMERIC_SCALE() {
        return NUMERIC_SCALE;
    }

    public void setNUMERIC_SCALE(String NUMERIC_SCALE) {
        this.NUMERIC_SCALE = NUMERIC_SCALE;
    }
}
