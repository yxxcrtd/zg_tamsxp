package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

public class BokSubjectErrodata extends BaseEntity {
    /**
     * uuid主键
     */
    private String datauuid;

    /**
     * null
     */
    private String workflowId;

    /**
     * 账套代码
     */
    private String booksetId;

    /**
     * 科目号
     */
    private String subjectNo;

    /**
     * 科目名称
     */
    private String subjectName;

    /**
     * 上级科目号
     */
    private String fsubjectId;

    /**
     * 科目类型
     */
    private String subjectType;

    /**
     * 科目级别
     */
    private String subjectLevel;

    /**
     * 科目方向
     */
    private String balFlag;

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
     * 取得 账套代码
     * @return 账套代码
     */
    public String getBooksetId() {
        return booksetId;
    }

    /**
     * 设置 账套代码
     * @param booksetId 账套代码
     */
    public void setBooksetId(String booksetId) {
        this.booksetId = booksetId == null ? null : booksetId.trim();
    }

    /**
     * 取得 科目号
     * @return 科目号
     */
    public String getSubjectNo() {
        return subjectNo;
    }

    /**
     * 设置 科目号
     * @param subjectNo 科目号
     */
    public void setSubjectNo(String subjectNo) {
        this.subjectNo = subjectNo == null ? null : subjectNo.trim();
    }

    /**
     * 取得 科目名称
     * @return 科目名称
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 设置 科目名称
     * @param subjectName 科目名称
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 取得 上级科目号
     * @return 上级科目号
     */
    public String getFsubjectId() {
        return fsubjectId;
    }

    /**
     * 设置 上级科目号
     * @param fsubjectId 上级科目号
     */
    public void setFsubjectId(String fsubjectId) {
        this.fsubjectId = fsubjectId == null ? null : fsubjectId.trim();
    }

    /**
     * 取得 科目类型
     * @return 科目类型
     */
    public String getSubjectType() {
        return subjectType;
    }

    /**
     * 设置 科目类型
     * @param subjectType 科目类型
     */
    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.trim();
    }

    /**
     * 取得 科目级别
     * @return 科目级别
     */
    public String getSubjectLevel() {
        return subjectLevel;
    }

    /**
     * 设置 科目级别
     * @param subjectLevel 科目级别
     */
    public void setSubjectLevel(String subjectLevel) {
        this.subjectLevel = subjectLevel == null ? null : subjectLevel.trim();
    }

    /**
     * 取得 科目方向
     * @return 科目方向
     */
    public String getBalFlag() {
        return balFlag;
    }

    /**
     * 设置 科目方向
     * @param balFlag 科目方向
     */
    public void setBalFlag(String balFlag) {
        this.balFlag = balFlag == null ? null : balFlag.trim();
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