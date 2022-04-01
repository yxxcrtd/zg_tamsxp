package com.joyintech.tams.report.investormerger.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;

/**
 * 描述:CHB_INVESTHOLD_INFO_CHECK表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-03-18
 */
public class ChbInvestholdInfoCheckDto extends BaseEntity implements ITableRow {
    /**
     * 登记银行代码
     */
    private String registBankCode;

    /**
     * 识别标识
     */
    private String indentifyMark;

    /**
     * 产品登记编码
     */
    private String prdRegistCode;

    /**
     * 持有日期
     */
    private Date holdDate;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 份额
     */
    private BigDecimal qty;

    /**
     * 折算人民币金额（元）
     */
    private BigDecimal cnyAmt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 经办人
     */
    private String agent;

    /**
     * 创建部门
     */
    private String createDept;

    /**
     * null
     */
    private String prodid;

    /**
     * 接入检查结果
     */
    private String interfCheckResult;
    /**
     * 处理状态 01未处理 02已处理
     */
    private String operatestatus;
    /**
     * 合并后识别标识
     */
    private String mergerindentifymark;

    /**
     * 登记银行代码
     * @return REGIST_BANK_CODE 登记银行代码
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 登记银行代码
     * @param registBankCode 登记银行代码
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
    }

    /**
     * 识别标识
     * @return INDENTIFY_MARK 识别标识
     */
    public String getIndentifyMark() {
        return indentifyMark;
    }

    /**
     * 识别标识
     * @param indentifyMark 识别标识
     */
    public void setIndentifyMark(String indentifyMark) {
        this.indentifyMark = indentifyMark == null ? null : indentifyMark.trim();
    }

    /**
     * 产品登记编码
     * @return PRD_REGIST_CODE 产品登记编码
     */
    public String getPrdRegistCode() {
        return prdRegistCode;
    }

    /**
     * 产品登记编码
     * @param prdRegistCode 产品登记编码
     */
    public void setPrdRegistCode(String prdRegistCode) {
        this.prdRegistCode = prdRegistCode == null ? null : prdRegistCode.trim();
    }

    /**
     * 持有日期
     * @return HOLD_DATE 持有日期
     */
    public Date getHoldDate() {
        return holdDate;
    }

    /**
     * 持有日期
     * @param holdDate 持有日期
     */
    public void setHoldDate(Date holdDate) {
        this.holdDate = holdDate;
    }

    /**
     * 币种
     * @return CCY 币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种
     * @param ccy 币种
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 金额
     * @return AMOUNT 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 金额
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 份额
     * @return QTY 份额
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * 份额
     * @param qty 份额
     */
    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    /**
     * 折算人民币金额（元）
     * @return CNY_AMT 折算人民币金额（元）
     */
    public BigDecimal getCnyAmt() {
        return cnyAmt;
    }

    /**
     * 折算人民币金额（元）
     * @param cnyAmt 折算人民币金额（元）
     */
    public void setCnyAmt(BigDecimal cnyAmt) {
        this.cnyAmt = cnyAmt;
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 数据来源
     * @return DATA_SOURCE 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 数据来源
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    /**
     * 数据状态
     * @return STATUS 数据状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 数据状态
     * @param status 数据状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 工作流代码
     * @return WORKFLOW_ID 工作流代码
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 工作流代码
     * @param workflowId 工作流代码
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 机构代码
     * @return ORG_CODE 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 部门代码
     * @return DEPT_CODE 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 更新人
     * @return UPDATE_USER 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 更新时间
     * @return UPDATE_TIME 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 经办人
     * @return AGENT 经办人
     */
    public String getAgent() {
        return agent;
    }

    /**
     * 经办人
     * @param agent 经办人
     */
    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    /**
     * 创建部门
     * @return CREATE_DEPT 创建部门
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * 创建部门
     * @param createDept 创建部门
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    /**
     * null
     * @return PRODID null
     */
    public String getProdid() {
        return prodid;
    }

    /**
     * null
     * @param prodid null
     */
    public void setProdid(String prodid) {
        this.prodid = prodid == null ? null : prodid.trim();
    }

    /**
     * 接入检查结果
     * @return INTERF_CHECK_RESULT 接入检查结果
     */
    public String getInterfCheckResult() {
        return interfCheckResult;
    }

    /**
     * 接入检查结果
     * @param interfCheckResult 接入检查结果
     */
    public void setInterfCheckResult(String interfCheckResult) {
        this.interfCheckResult = interfCheckResult == null ? null : interfCheckResult.trim();
    }

	public String getOperatestatus() {
		return operatestatus;
	}

	public void setOperatestatus(String operatestatus) {
		this.operatestatus = operatestatus;
	}

	public String getMergerindentifymark() {
		return mergerindentifymark;
	}

	public void setMergerindentifymark(String mergerindentifymark) {
		this.mergerindentifymark = mergerindentifymark;
	}
    
}