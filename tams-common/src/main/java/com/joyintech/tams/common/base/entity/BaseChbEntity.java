package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

/**
 * check共通dto
 */
public class BaseChbEntity extends BaseEntity {

    //错误信息
    private String errMsg;

    //数据来源
    private String dataSource;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}