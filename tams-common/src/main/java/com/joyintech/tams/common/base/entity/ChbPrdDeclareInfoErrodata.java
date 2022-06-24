package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.util.Date;

public class ChbPrdDeclareInfoErrodata extends BaseEntity {
    /**
     * uuid主键
     */
    private String datauuid;

    /**
     * null
     */
    private String workflowId;

    /**
     * 产品名称
     */
    private String prodName;

    /**
     * 产品期限
     */
    private String termtype;

    /**
     * 产品运作模式
     */
    private String operateMode;

    /**
     * 募集起始日
     */
    private Date raisePstrdate;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 行内标识码
     */
    private String prodInsId;

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
     * 取得 产品名称
     * @return 产品名称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 设置 产品名称
     * @param prodName 产品名称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    /**
     * 取得 产品期限
     * @return 产品期限
     */
    public String getTermtype() {
        return termtype;
    }

    /**
     * 设置 产品期限
     * @param termtype 产品期限
     */
    public void setTermtype(String termtype) {
        this.termtype = termtype == null ? null : termtype.trim();
    }

    /**
     * 取得 产品运作模式
     * @return 产品运作模式
     */
    public String getOperateMode() {
        return operateMode;
    }

    /**
     * 设置 产品运作模式
     * @param operateMode 产品运作模式
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode == null ? null : operateMode.trim();
    }

    /**
     * 取得 募集起始日
     * @return 募集起始日
     */
    public Date getRaisePstrdate() {
        return raisePstrdate;
    }

    /**
     * 设置 募集起始日
     * @param raisePstrdate 募集起始日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setRaisePstrdate(Date raisePstrdate) {
        this.raisePstrdate = raisePstrdate;
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

    /**
     * 取得 行内标识码
     * @return 行内标识码
     */
    public String getProdInsId() {
        return prodInsId;
    }

    /**
     * 设置 行内标识码
     * @param prodInsId 行内标识码
     */
    public void setProdInsId(String prodInsId) {
        this.prodInsId = prodInsId == null ? null : prodInsId.trim();
    }
}