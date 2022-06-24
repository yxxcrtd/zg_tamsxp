package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.util.Date;

public class MstHolidayErrodata extends BaseEntity {
    /**
     * uuid主键
     */
    private String datauuid;

    /**
     * null
     */
    private String workflowId;

    /**
     * 日历编号
     */
    private String calendarId;

    /**
     * 是否节假日，0：工作日，1：节假日
     */
    private Long isHoliday;

    /**
     * 日期
     */
    private Date cdate;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 取得 uuid主键
     * @return uuid主键
     */
    public String getDatauuid() {
        return datauuid;
    }

    /**
     * 设置 uuid主键
     * @param datauuid uuid主键
     */
    public void setDatauuid(String datauuid) {
        this.datauuid = datauuid == null ? null : datauuid.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 设置 null
     * @param workflowId null
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 取得 日历编号
     * @return 日历编号
     */
    public String getCalendarId() {
        return calendarId;
    }

    /**
     * 设置 日历编号
     * @param calendarId 日历编号
     */
    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId == null ? null : calendarId.trim();
    }

    /**
     * 取得 是否节假日，0：工作日，1：节假日
     * @return 是否节假日，0：工作日，1：节假日
     */
    public Long getIsHoliday() {
        return isHoliday;
    }

    /**
     * 设置 是否节假日，0：工作日，1：节假日
     * @param isHoliday 是否节假日，0：工作日，1：节假日
     */
    public void setIsHoliday(Long isHoliday) {
        this.isHoliday = isHoliday;
    }

    /**
     * 取得 日期
     * @return 日期
     */
    public Date getCdate() {
        return cdate;
    }

    /**
     * 设置 日期
     * @param cdate 日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }

    /**
     * 取得 机构代码
     * @return 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 取得 部门代码
     * @return 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }
}