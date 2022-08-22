package com.joyintech.tams.common.base.entity;


import com.joyintech.framework.base.BaseEntity;
import com.joyintech.workflow.engine.annotation.PrimaryKey;

import java.util.Date;

/**
 * 流动性风险应对情况信息维护
 *
 * @author 周彬
 */
public class LiquidityRiskInfo extends BaseEntity {
    /**
     * 主键
     */
    @PrimaryKey(value= PrimaryKey.Field.PK_1)
    private String seqno;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 产品登记编码
     */
    private String regCode;
    /**
     * 流动性风险应对措施
     */
    private String solutions;


    /**
     * 风险发生日期
     */
    private Date occurDate;


    /**
     * 风险应对日期
     */
    private Date replyDate;


    /**
     * 风险事件描述
     */
    private String described;


    /**
     * 风险处理结果
     */
    private String disposeResult;
    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 备注
     */
    private String remark;
    /**
     * 错误信息
     */
    private String errMsg;


    public String getSeqno() {
        return seqno;
    }

    public void setSeqno(String seqno) {
        this.seqno = seqno;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    public String getSolutions() {
        return solutions;
    }

    public void setSolutions(String solutions) {
        this.solutions = solutions;
    }

    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public String getDisposeResult() {
        return disposeResult;
    }

    public void setDisposeResult(String disposeResult) {
        this.disposeResult = disposeResult;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}