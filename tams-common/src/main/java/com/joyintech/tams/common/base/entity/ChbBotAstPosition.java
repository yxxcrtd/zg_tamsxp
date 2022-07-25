package com.joyintech.tams.common.base.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述:CHB_BOT_AST_POS表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-28
 */
public class ChbBotAstPosition extends ChbBotAstPositionKey {

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 持仓日期
     */
    private Date posDate;

    /**
     * 资管计划行内资产/负债编码
     */
    private String planAssetId;

    /**
     * 行内资产/负债编码
     */
    private String assetCode;

    /**
     * 总发行数量
     */
    private BigDecimal totalNum;

    /**
     * 人民币发行金额
     */
    private BigDecimal issueAmt;

    /**
     * 人民币发行金额-原币
     */
    private BigDecimal oIssueAmt;

    /**
     * 未投资头寸
     */
    private BigDecimal uninvestCash;

    /**
     * 未投资头寸-原币
     */
    private BigDecimal oUninvestCash;

    /**
     * 持仓数量
     */
    private BigDecimal posNum;

    /**
     * 底层资产折算人民币币值（元）
     */
    private BigDecimal posAmt;

    /**
     * 是否资管计划 01是 02 否
     */
    private String isfinaplan;

    /**
     * 是否通过通道01是 02 否
     */
    private String ischl;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建部门
     */
    private String createDept;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 数据来源
     */
    private String datasource;
    
    /**
     * 剩余金额
     */
    private BigDecimal accuamt;
    
    /**
     * 币种
     */
    private String ccy;
    
    /**
     * 穿透后资产价值取值控制，01：市值(产品端期末余额等于份额*单位净值)，02：份额
     */
    private String monthReport;

    /**
     * 资管计划是否只有现金 01：是  02：否
     */
    private String cashFlag;

    /**
     * 利息
     */
    private BigDecimal intAmt;

    /**
     * 底层资产折算人民币影子价格
     */
    private BigDecimal    shadowAmt;

    /**
     * 底层资产折算人民币净价市值
     */
    private BigDecimal  posAmtCprice;

    /**
     * 原币-底层资产影子价格
     */
    private  BigDecimal oShadowAmt;
    /**
     * 原币-利息
     */
    private  BigDecimal oIntAmt;

    /**
     * 原币-底层资产净价市值
     */
    private  BigDecimal oPosAmtCprice;

    /**
     * 原币-底层资产全价币值（元）
     */
    private  BigDecimal oPosAmt;


    /**
     * 发行机构代码
     * @return ISSUE_PARTY_ID 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 持仓日期
     * @return POS_DATE 持仓日期
     */
    public Date getPosDate() {
        return posDate;
    }

    /**
     * 持仓日期
     * @param posDate 持仓日期
     */
    public void setPosDate(Date posDate) {
        this.posDate = posDate;
    }

    /**
     * 资管计划行内资产/负债编码
     * @return PLAN_ASSET_ID 资管计划行内资产/负债编码
     */
    public String getPlanAssetId() {
        return planAssetId;
    }

    /**
     * 资管计划行内资产/负债编码
     * @param planAssetId 资管计划行内资产/负债编码
     */
    public void setPlanAssetId(String planAssetId) {
        this.planAssetId = planAssetId == null ? null : planAssetId.trim();
    }

    /**
     * 行内资产/负债编码
     * @return ASSET_CODE 行内资产/负债编码
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 行内资产/负债编码
     * @param assetCode 行内资产/负债编码
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 总发行数量
     * @return TOTAL_NUM 总发行数量
     */
    public BigDecimal getTotalNum() {
        return totalNum;
    }

    /**
     * 总发行数量
     * @param totalNum 总发行数量
     */
    public void setTotalNum(BigDecimal totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 人民币发行金额
     * @return ISSUE_AMT 人民币发行金额
     */
    public BigDecimal getIssueAmt() {
        return issueAmt;
    }

    /**
     * 人民币发行金额
     * @param issueAmt 人民币发行金额
     */
    public void setIssueAmt(BigDecimal issueAmt) {
        this.issueAmt = issueAmt;
    }

    /**
     * 未投资头寸
     * @return UNINVEST_CASH 未投资头寸
     */
    public BigDecimal getUninvestCash() {
        return uninvestCash;
    }

    /**
     * 未投资头寸
     * @param uninvestCash 未投资头寸
     */
    public void setUninvestCash(BigDecimal uninvestCash) {
        this.uninvestCash = uninvestCash;
    }

    /**
     * 持仓数量
     * @return POS_NUM 持仓数量
     */
    public BigDecimal getPosNum() {
        return posNum;
    }

    /**
     * 持仓数量
     * @param posNum 持仓数量
     */
    public void setPosNum(BigDecimal posNum) {
        this.posNum = posNum;
    }

    /**
     * 底层资产折算人民币币值（元）
     * @return POS_AMT 底层资产折算人民币币值（元）
     */
    public BigDecimal getPosAmt() {
        return posAmt;
    }

    /**
     * 底层资产折算人民币币值（元）
     * @param posAmt 底层资产折算人民币币值（元）
     */
    public void setPosAmt(BigDecimal posAmt) {
        this.posAmt = posAmt;
    }

    /**
     * 是否资管计划 1是 0 否
     * @return ISFINAPLAN 是否资管计划 1是 0 否
     */
    public String getIsfinaplan() {
        return isfinaplan;
    }

    /**
     * 是否资管计划 1是 0 否
     * @param isfinaplan 是否资管计划 1是 0 否
     */
    public void setIsfinaplan(String isfinaplan) {
        this.isfinaplan = isfinaplan == null ? null : isfinaplan.trim();
    }

    /**
     * 是否通过通道 1是 0 否
     * @return ISCHL 是否通过通道 1是 0 否
     */
    public String getIschl() {
        return ischl;
    }

    /**
     * 是否通过通道 1是 0 否
     * @param ischl 是否通过通道 1是 0 否
     */
    public void setIschl(String ischl) {
        this.ischl = ischl == null ? null : ischl.trim();
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
     * 数据来源
     * @return DATASOURCE 数据来源
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * 数据来源
     * @param datasource 数据来源
     */
    public void setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
    }

    /**
     * 剩余金额
     */
	public BigDecimal getAccuamt() {
		return accuamt;
	}

	 /**
     * 剩余金额
     */
	public void setAccuamt(BigDecimal accuamt) {
		this.accuamt = accuamt;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getMonthReport() {
		return monthReport;
	}

	public void setMonthReport(String monthReport) {
		this.monthReport = monthReport;
	}

    public String getCashFlag() {
        return cashFlag;
    }

    public void setCashFlag(String cashFlag) {
        this.cashFlag = cashFlag;
    }

    public BigDecimal getIntAmt() {
        return intAmt;
    }

    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    public String getPlanAssetIdAndSubjectCd() {
	    return this.getPlanAssetId() + this.getAssetCode();
    }

    public BigDecimal getoUninvestCash() {
        return oUninvestCash;
    }

    public void setoUninvestCash(BigDecimal oUninvestCash) {
        this.oUninvestCash = oUninvestCash;
    }

    public BigDecimal getoIssueAmt() {
        return oIssueAmt;
    }

    public void setoIssueAmt(BigDecimal oIssueAmt) {
        this.oIssueAmt = oIssueAmt;
    }


    public BigDecimal getShadowAmt() {
        return shadowAmt;
    }

    public void setShadowAmt(BigDecimal shadowAmt) {
        this.shadowAmt = shadowAmt;
    }

    public BigDecimal getoShadowAmt() {
        return oShadowAmt;
    }

    public void setoShadowAmt(BigDecimal oShadowAmt) {
        this.oShadowAmt = oShadowAmt;
    }

    public BigDecimal getoIntAmt() {
        return oIntAmt;
    }

    public void setoIntAmt(BigDecimal oIntAmt) {
        this.oIntAmt = oIntAmt;
    }


    public BigDecimal getoPosAmt() {
        return oPosAmt;
    }

    public void setoPosAmt(BigDecimal oPosAmt) {
        this.oPosAmt = oPosAmt;
    }

    public BigDecimal getPosAmtCprice() {
        return posAmtCprice;
    }

    public void setPosAmtCprice(BigDecimal posAmtCprice) {
        this.posAmtCprice = posAmtCprice;
    }

    public BigDecimal getoPosAmtCprice() {
        return oPosAmtCprice;
    }

    public void setoPosAmtCprice(BigDecimal oPosAmtCprice) {
        this.oPosAmtCprice = oPosAmtCprice;
    }

    private List<String> subjectNoList;

    public List<String> getSubjectNoList() {
        return subjectNoList;
    }

    public void setSubjectNoList(List<String> subjectNoList) {
        this.subjectNoList = subjectNoList;
    }
}