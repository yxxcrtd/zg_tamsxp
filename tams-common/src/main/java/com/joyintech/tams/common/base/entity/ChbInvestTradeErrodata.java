package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.util.Date;

public class ChbInvestTradeErrodata extends BaseEntity {
    /**
     * uuid主键
     */
    private String datauuid;

    /**
     * null
     */
    private String workflowId;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 产品登记编码
     */
    private String regCode;

    /**
     * 交易对手
     */
    private String tradeCounter;

    /**
     * 行内交易编码
     */
    private String tradeCode;

    /**
     * 行内资产/负债编码
     */
    private String assetCode;

    /**
     * 交易日
     */
    private Date dealdate;

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
     * 取得 产品代码
     * @return 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 设置 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 取得 产品登记编码
     * @return 产品登记编码
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * 设置 产品登记编码
     * @param regCode 产品登记编码
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
    }

    /**
     * 取得 交易对手
     * @return 交易对手
     */
    public String getTradeCounter() {
        return tradeCounter;
    }

    /**
     * 设置 交易对手
     * @param tradeCounter 交易对手
     */
    public void setTradeCounter(String tradeCounter) {
        this.tradeCounter = tradeCounter == null ? null : tradeCounter.trim();
    }

    /**
     * 取得 行内交易编码
     * @return 行内交易编码
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * 设置 行内交易编码
     * @param tradeCode 行内交易编码
     */
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    /**
     * 取得 行内资产/负债编码
     * @return 行内资产/负债编码
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 设置 行内资产/负债编码
     * @param assetCode 行内资产/负债编码
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 取得 交易日
     * @return 交易日
     */
    public Date getDealdate() {
        return dealdate;
    }

    /**
     * 设置 交易日
     * @param dealdate 交易日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
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