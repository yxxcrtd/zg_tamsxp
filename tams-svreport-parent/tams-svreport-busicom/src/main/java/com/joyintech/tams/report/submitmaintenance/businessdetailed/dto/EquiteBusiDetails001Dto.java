/*
 * 文件名：EquiteBaseInfo001Dto.java
 * 版权：Copyright 2017-2019 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中债报送子系统
 * 修改人：何阳阳
 * 修改时间：2017年9月14日
 * 修改内容：新建
 * 系统名称：中债报送子系统
 */

package com.joyintech.tams.report.submitmaintenance.businessdetailed.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.validation.annotation.*;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.tams.report.common.base.TableRow;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.valid.EquiteBusiDetailsValidator;

/**
 * 投资者明细信息管理dto
 *
 */
@ValidLogic(targetClass = EquiteBusiDetailsValidator.class, groups = {ValidateGroupEdit.class,
	    ValidateGroupDelete.class})
public class EquiteBusiDetails001Dto extends TableRow {
	/**
     * 操作类型
     */
    private String operateType;
    
    private String pageModel;
    /**
     * 选中的数据
     */
    private List <EquiteBusiDetails001Dto > selectedItem;
    /**
     * excel行号
     */
    private String excelNum;
    
	/**
     * 数据状态-文字
     */
    private String statusStr;

    /**
     * 业务确认日期字符串类型
     */
    private String eQuityConfDateStr;
    
    /**
     * 业务流水号（核心交易流水号）
     */
    private String busiSeqno;

    /**
     * 产品登记编码
     */
    private String regCode;
    
    /**
     * 登记银行代码（即发行机构代码）
     */
    private String registBankCode;

    /**
     * 销售合同号
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.contractNo",groups={ValidateGroupEdit.class})
	private String contractNo;

    /**
     * 理财账号
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.finAcc",groups={ValidateGroupEdit.class})
	private String finAcc;

    /**
     * 客户统一编号
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.custContractNo",groups={ValidateGroupEdit.class})
	private String custContractNo;
    
    /**
     * 识别标识
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.indentifyMark",groups={ValidateGroupEdit.class})
	private String indentifyMark;

    /**
     * 客户姓名
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.custName",groups={ValidateGroupEdit.class})
	private String custName;

    /**
     * 关联活期存款账号
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.fundAcc",groups={ValidateGroupEdit.class})
	private String fundAcc;

    /**
     * 关联账号开户所在地
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.fundAccPlace",groups={ValidateGroupEdit.class})
	private String fundAccPlace;

    /**
     * 是否代销
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.isConsign",groups={ValidateGroupEdit.class})
	private String isConsign;

    /**
     * 代销机构代码
     */
    private String agencyCode;

    /**
     * 代销机构名称
     */
    private String agencyName;

    /**
     * 代销机构所属监管机构
     */
    private String agencyFsa;

    /**
     * 产品代码
     */
    private String account;

    /**
     * 产品名称
     */
    private String accountName;

    /**
     * 业务种类
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.busiType",groups={ValidateGroupEdit.class})
	private String busiType;

    /**
     * 业务发生地所属监管
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.busiFsa",groups={ValidateGroupEdit.class})
	private String busiFsa;
    
    /**
     * 业务确认日期
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.equityConfDate",groups={ValidateGroupEdit.class})
	private Date equityConfDate;

    /**
     * 业务确认时间
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.shareConfDatetime",groups={ValidateGroupEdit.class})
    private String shareConfDatetime;

    /**
     * 币种
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.ccy",groups={ValidateGroupEdit.class})
	private String ccy;

    /**
     * 金额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.amount",groups={ValidateGroupEdit.class})
	@Amount(fieldName = "SvrEquityBusinessDetails002.amount",groups={ValidateGroupEdit.class})
	private BigDecimal amount;

    /**
     * 折算人民币金额（元）
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.amountCny",groups={ValidateGroupEdit.class})
	@Amount(fieldName = "SvrEquityBusinessDetails002.amountCny",groups={ValidateGroupEdit.class})
	private BigDecimal amountCny;

    /**
     * 确认净值
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
	@NetValue(fieldName = "SvrEquityBusinessDetails002.prodValue",groups = {ValidateGroupEdit.class})
    private BigDecimal prodValue;

    /**
     * 份额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.shares",groups={ValidateGroupEdit.class})
	@Amount(fieldName = "SvrEquityBusinessDetails002.shares",maxDecBit = 5,
			groups = {ValidateGroupEdit.class}) //校验位不小于0
	private BigDecimal shares;

    /**
     * 费用
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.feeAmt",groups={ValidateGroupEdit.class})
	@Amount(fieldName = "SvrEquityBusinessDetails002.feeAmt",groups = {ValidateGroupEdit.class})
	private BigDecimal feeAmt;

    /**
     * 渠道
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.channel",groups={ValidateGroupEdit.class})
	private String channel;

    /**
     * 交易柜员号
     */
	@NotEmpty(fieldName = "SvrEquityBusinessDetails002.tranTellName",groups={ValidateGroupEdit.class})
	private String tranTellName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 导入检查结果
     */
    private String interfCheckResult;
    
    /**
     * 合并前识别标识
     */
    private String orgIndentifyMark;
    
    /**
     * 数据状态
     */
    private String dataStatus;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 经办人
     */
    private String agent;

    /**
     * 是否报送
     */
    private String isSubmit;
    
    /**
     * 创建部门
     */
    private String createDept;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 产品运作模式
     */
    private String operateMode;

	/**
	 * 中债反馈信息
	 */
	private String repFailedInfo;
	/**
	 *交易序列号
	 */
	private  String  tradeSeq;
	/**
	 *关联活期存款账号开户行代码
	 */
	private String  fundAccCode;
	/**
	 *关联活期存款账号开户行名称
	 */
	private  String fundAccName;


	private Map<String,String> tempMap;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getPageModel() {
		return pageModel;
	}

	public void setPageModel(String pageModel) {
		this.pageModel = pageModel;
	}

	public List<EquiteBusiDetails001Dto> getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(List<EquiteBusiDetails001Dto> selectedItem) {
		this.selectedItem = selectedItem;
	}

	public String getExcelNum() {
		return excelNum;
	}

	public void setExcelNum(String excelNum) {
		this.excelNum = excelNum;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String geteQuityConfDateStr() {
		return eQuityConfDateStr;
	}

	public void seteQuityConfDateStr(String eQuityConfDateStr) {
		this.eQuityConfDateStr = eQuityConfDateStr;
	}
	
    //业务流水号
    @PrimaryKey(value=Field.PK_1)
	public String getBusiSeqno() {
		return busiSeqno;
	}

	public void setBusiSeqno(String busiSeqno) {
		this.busiSeqno = busiSeqno;
	}

	//产品登记编码
    @PrimaryKey(value=Field.PK_2)
	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	//登记银行代码（即发行机构代码）
    @PrimaryKey(value=Field.PK_3)
	public String getRegistBankCode() {
		return registBankCode;
	}

	public void setRegistBankCode(String registBankCode) {
		this.registBankCode = registBankCode;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getFinAcc() {
		return finAcc;
	}

	public void setFinAcc(String finAcc) {
		this.finAcc = finAcc;
	}

	public String getCustContractNo() {
		return custContractNo;
	}

	public void setCustContractNo(String custContractNo) {
		this.custContractNo = custContractNo;
	}

	public String getIndentifyMark() {
		return indentifyMark;
	}

	public void setIndentifyMark(String indentifyMark) {
		this.indentifyMark = indentifyMark;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getFundAcc() {
		return fundAcc;
	}

	public void setFundAcc(String fundAcc) {
		this.fundAcc = fundAcc;
	}

	public String getFundAccPlace() {
		return fundAccPlace;
	}

	public void setFundAccPlace(String fundAccPlace) {
		this.fundAccPlace = fundAccPlace;
	}

	public String getIsConsign() {
		return isConsign;
	}

	public void setIsConsign(String isConsign) {
		this.isConsign = isConsign;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyFsa() {
		return agencyFsa;
	}

	public void setAgencyFsa(String agencyFsa) {
		this.agencyFsa = agencyFsa;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiFsa() {
		return busiFsa;
	}

	public void setBusiFsa(String busiFsa) {
		this.busiFsa = busiFsa;
	}

	public Date getEquityConfDate() {
		return equityConfDate;
	}

	public void setEquityConfDate(Date equityConfDate) {
		this.equityConfDate = equityConfDate;
	}

	public String getShareConfDatetime() {
		return shareConfDatetime;
	}

	public void setShareConfDatetime(String shareConfDatetime) {
		this.shareConfDatetime = shareConfDatetime;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountCny() {
		return amountCny;
	}

	public void setAmountCny(BigDecimal amountCny) {
		this.amountCny = amountCny;
	}

	public BigDecimal getProdValue() {
		return prodValue;
	}

	public void setProdValue(BigDecimal prodValue) {
		this.prodValue = prodValue;
	}

	public BigDecimal getShares() {
		return shares;
	}

	public void setShares(BigDecimal shares) {
		this.shares = shares;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTranTellName() {
		return tranTellName;
	}

	public void setTranTellName(String tranTellName) {
		this.tranTellName = tranTellName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInterfCheckResult() {
		return interfCheckResult;
	}

	public void setInterfCheckResult(String interfCheckResult) {
		this.interfCheckResult = interfCheckResult;
	}

	public String getOrgIndentifyMark() {
		return orgIndentifyMark;
	}

	public void setOrgIndentifyMark(String orgIndentifyMark) {
		this.orgIndentifyMark = orgIndentifyMark;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
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

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    public String getOperateMode() {
        return operateMode;
    }

    
	public void setOperateMode(String operateMode) {
	    this.operateMode = operateMode;
	}

	public Map<String, String> getTempMap() {
		return tempMap;
	}

	public void setTempMap(Map<String, String> tempMap) {
		this.tempMap = tempMap;
	}

	public String getRepFailedInfo() {
		return repFailedInfo;
	}

	public void setRepFailedInfo(String repFailedInfo) {
		this.repFailedInfo = repFailedInfo;
	}

	public String getTradeSeq() {
		return tradeSeq;
	}

	public void setTradeSeq(String tradeSeq) {
		this.tradeSeq = tradeSeq;
	}

	public String getFundAccCode() {
		return fundAccCode;
	}

	public void setFundAccCode(String fundAccCode) {
		this.fundAccCode = fundAccCode;
	}

	public String getFundAccName() {
		return fundAccName;
	}

	public void setFundAccName(String fundAccName) {
		this.fundAccName = fundAccName;
	}
}