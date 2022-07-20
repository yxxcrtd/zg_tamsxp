package com.joyintech.tams.common.base.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.framework.base.BaseEntity;

/**
 * 描述:CHB_PRD_FEE_DEAL表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-31
 */
public class ChbPrdFeeDeal extends BaseEntity{
    /**
     * 主键 
     */
    private String seqno;
    
    /**
     * 上游系统交易编码
     */
    private String tradeId;

    /**
     * 父产品代码
     */
    private String fprodId;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 产品名称
     */
    private String prodName;

    /**
     * 产品登记编码
     */
    private String regCode;

    /**
     * 费用代码
     */
    private String feeId;

    /**
     * 费用名称
     */
    private String feeName;

    /**
     * 费用类型 01:本机构费用 02:其他机构费用
     */
    private String feeType;

    /**
     * 01:本机构托管费 02:本机构管理费 03:本机构销售手续费 04:本机构其他产品费用 05:其他机构托管费 06:其他机构管理费 07:其他机构销售手续费 08:投资顾问费用 09:其他机构其他产品费用 10：中收费 11：浮动费
     */
    private String feeSectype;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 支付日
     */
    private Date payDate;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 撤销日
     */
    private Date revDate;

    /**
     * 交易发起时间
     */
    private Date tradeTime;

    /**
     * 交易发起日格式化的时间
     */
    private String tradeDate;

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
     * 备注
     */
    private String remark;
    
    /**
     * 交易对手
     */
    private String tradeCounter;
    
    /**
     * 对手方类型
     */
    private String rivalPartyType;

    /**
     * 交易审批人身份证号
     */
    private String investApproverId;

    /**
     * 交易审批人姓名
     */
    private String investApproverName;

    /**
     * 交易员身份证号
     */
    private String investTraderId;

    /**
     * 交易员姓名
     */
    private String investTraderName;

    /**
     * 主键 
     * @return SEQNO 主键 
     */
    public String getSeqno() {
        return seqno;
    }

    /**
     * 主键 
     * @param seqno 主键 
     */
    public void setSeqno(String seqno) {
        this.seqno = seqno == null ? null : seqno.trim();
    }
    

    public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	/**
     * 父产品代码
     * @return FPROD_ID 父产品代码
     */
    public String getFprodId() {
        return fprodId;
    }

    /**
     * 父产品代码
     * @param fprodId 父产品代码
     */
    public void setFprodId(String fprodId) {
        this.fprodId = fprodId == null ? null : fprodId.trim();
    }

    /**
     * 产品代码
     * @return PROD_ID 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 产品名称
     * @return PROD_NAME 产品名称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 产品名称
     * @param prodName 产品名称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    /**
     * 产品登记编码
     * @return REG_CODE 产品登记编码
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * 产品登记编码
     * @param regCode 产品登记编码
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
    }

    /**
     * 费用代码
     * @return FEE_ID 费用代码
     */
    public String getFeeId() {
        return feeId;
    }

    /**
     * 费用代码
     * @param feeId 费用代码
     */
    public void setFeeId(String feeId) {
        this.feeId = feeId == null ? null : feeId.trim();
    }

    /**
     * 费用名称
     * @return FEE_NAME 费用名称
     */
    public String getFeeName() {
        return feeName;
    }

    /**
     * 费用名称
     * @param feeName 费用名称
     */
    public void setFeeName(String feeName) {
        this.feeName = feeName == null ? null : feeName.trim();
    }

    /**
     * 费用类型 01:本机构费用 02:其他机构费用
     * @return FEE_TYPE 费用类型 01:本机构费用 02:其他机构费用
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * 费用类型 01:本机构费用 02:其他机构费用
     * @param feeType 费用类型 01:本机构费用 02:其他机构费用
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    /**
     * 01:本机构托管费 02:本机构管理费 03:本机构销售手续费 04:本机构其他产品费用 05:其他机构托管费 06:其他机构管理费 07:其他机构销售手续费 08:投资顾问费用 09:其他机构其他产品费用 10：中收费 11：浮动费
     * @return FEE_SECTYPE 01:本机构托管费 02:本机构管理费 03:本机构销售手续费 04:本机构其他产品费用 05:其他机构托管费 06:其他机构管理费 07:其他机构销售手续费 08:投资顾问费用 09:其他机构其他产品费用 10：中收费 11：浮动费
     */
    public String getFeeSectype() {
        return feeSectype;
    }

    /**
     * 01:本机构托管费 02:本机构管理费 03:本机构销售手续费 04:本机构其他产品费用 05:其他机构托管费 06:其他机构管理费 07:其他机构销售手续费 08:投资顾问费用 09:其他机构其他产品费用 10：中收费 11：浮动费
     * @param feeSectype 01:本机构托管费 02:本机构管理费 03:本机构销售手续费 04:本机构其他产品费用 05:其他机构托管费 06:其他机构管理费 07:其他机构销售手续费 08:投资顾问费用 09:其他机构其他产品费用 10：中收费 11：浮动费
     */
    public void setFeeSectype(String feeSectype) {
        this.feeSectype = feeSectype == null ? null : feeSectype.trim();
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
     * @return AMT 金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 金额
     * @param amt 金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 支付日
     * @return PAY_DATE 支付日
     */
    public Date getPayDate() {
        return payDate;
    }

    /**
     * 支付日
     * @param payDate 支付日
     */
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
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
     * 撤销日
     * @return REV_DATE 撤销日
     */
    public Date getRevDate() {
        return revDate;
    }

    /**
     * 撤销日
     * @param revDate 撤销日
     */
    public void setRevDate(Date revDate) {
        this.revDate = revDate;
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

	public String getTradeCounter() {
		return tradeCounter;
	}

	public void setTradeCounter(String tradeCounter) {
		this.tradeCounter = tradeCounter;
	}

	public String getRivalPartyType() {
		return rivalPartyType;
	}

	public void setRivalPartyType(String rivalPartyType) {
		this.rivalPartyType = rivalPartyType;
	}

    public String getInvestApproverId() {
        return investApproverId;
    }

    public void setInvestApproverId(String investApproverId) {
        this.investApproverId = investApproverId;
    }

    public String getInvestApproverName() {
        return investApproverName;
    }

    public void setInvestApproverName(String investApproverName) {
        this.investApproverName = investApproverName;
    }

    public String getInvestTraderId() {
        return investTraderId;
    }

    public void setInvestTraderId(String investTraderId) {
        this.investTraderId = investTraderId;
    }

    public String getInvestTraderName() {
        return investTraderName;
    }

    public void setInvestTraderName(String investTraderName) {
        this.investTraderName = investTraderName;
    }
}