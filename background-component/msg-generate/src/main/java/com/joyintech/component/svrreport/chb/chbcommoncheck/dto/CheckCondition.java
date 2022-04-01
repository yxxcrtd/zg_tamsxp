package com.joyintech.component.svrreport.chb.chbcommoncheck.dto;

/**
 * 描述:CHB_DATACHECK_CONDITION表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-04-26
 */
public class CheckCondition extends CheckElement{
    /**
     * 规则标示
     */
    private String checkconditionid;

    /**
     * 检查的对象类型
     */
    private String checkruleid;

    /**
     * 规则标示
     * @return CHECKCONDITIONID 规则标示
     */
    public String getCheckconditionid() {
        return checkconditionid;
    }

    /**
     * 规则标示
     * @param checkconditionid 规则标示
     */
    public void setCheckconditionid(String checkconditionid) {
        this.checkconditionid = checkconditionid == null ? null : checkconditionid.trim();
    }

    /**
     * 检查的对象类型
     * @return CHECKRULEID 检查的对象类型
     */
    public String getCheckruleid() {
        return checkruleid;
    }

    /**
     * 检查的对象类型
     * @param checkruleid 检查的对象类型
     */
    public void setCheckruleid(String checkruleid) {
        this.checkruleid = checkruleid == null ? null : checkruleid.trim();
    }

}