package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.util.Date;

public class MstExchargeQuoteErrodata extends BaseEntity {
    /**
     * uuid主键
     */
    private String datauuid;

    /**
     * null
     */
    private String workflowId;

    /**
     * 货币对
     */
    private String exchargeCode;

    /**
     * 数据来源
     */
    private String datasourceType;

    /**
     * 日期
     */
    private Date tradeDate;

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
     * 取得 货币对
     * @return 货币对
     */
    public String getExchargeCode() {
        return exchargeCode;
    }

    /**
     * 设置 货币对
     * @param exchargeCode 货币对
     */
    public void setExchargeCode(String exchargeCode) {
        this.exchargeCode = exchargeCode == null ? null : exchargeCode.trim();
    }

    /**
     * 取得 数据来源
     * @return 数据来源
     */
    public String getDatasourceType() {
        return datasourceType;
    }

    /**
     * 设置 数据来源
     * @param datasourceType 数据来源
     */
    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType == null ? null : datasourceType.trim();
    }

    /**
     * 取得 日期
     * @return 日期
     */
    public Date getTradeDate() {
        return tradeDate;
    }

    /**
     * 设置 日期
     * @param tradeDate 日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
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