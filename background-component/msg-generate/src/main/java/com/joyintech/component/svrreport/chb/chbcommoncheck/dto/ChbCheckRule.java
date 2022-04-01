package com.joyintech.component.svrreport.chb.chbcommoncheck.dto;

import java.util.List;

/**
 * 描述:CHB_DATACHECK_RULE表的实体类
 * @version
 * @author:  chenxc
 * @创建时间: 2019-04-24
 */
public class ChbCheckRule extends CheckElement {
    /**
     * 规则标示
     */
    private String checkruleid;

    /**
     * 检查的对象类型
     */
    private String pageId;

    /**
     * 检查类
     */
    private String checkclass;
    
    /**
     * 前置条件
     */
    private List<CheckCondition> listConditions;

    public String getCheckruleid() {
        return checkruleid;
    }

    public void setCheckruleid(String checkruleid) {
        this.checkruleid = checkruleid;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getCheckclass() {
        return checkclass;
    }

    public void setCheckclass(String checkclass) {
        this.checkclass = checkclass;
    }

    public List<CheckCondition> getListConditions() {
        return listConditions;
    }

    public void setListConditions(List<CheckCondition> listConditions) {
        this.listConditions = listConditions;
    }
}