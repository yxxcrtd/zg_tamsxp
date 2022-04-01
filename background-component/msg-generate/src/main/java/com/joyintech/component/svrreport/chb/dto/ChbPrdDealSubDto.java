/*
 * 文件名：ChbPrdDealSubDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月24日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.component.svrreport.chb.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.component.svrreport.chb.contant.annotate.RateFormat;

/**
 * 产品存续期登记
 * @description 
 * @author 王波
 * @version 1.0 2020年9月24日
 * @see ChbPrdDealSubDto
 * @since 1.0
 */
public class ChbPrdDealSubDto {
	/**
	 * 主键
	 */
	private String registSeqno;
    /**
     * 理财产品代码
     */
    private String prodId;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 产品登记编码
     */
    private String prodRegistCode;

    /**
     * 业务起始日
     */
    private String vdate;

    /**
     * 业务结束日
     */
    private String mdate;

    /**
     * 银行实现收益(元)
     */
    private BigDecimal bankImpInt;

    /**
     * 最新预期收益率
     */
    @RateFormat
    private BigDecimal newExpYield;

    /**
     * 累计申购份额
     */
    private BigDecimal purchaseQty;

    /**
     * 累计赎回份额
     */
    private BigDecimal redeemQty;
    
    /**
     * 累计申购金额
     */
    private BigDecimal purchaseAmt;
    
    /**
     * 累计赎回金额
     */
    private BigDecimal redeemAmt;

    /**
     * 兑付收益
     */
    private BigDecimal intamt;

    /**
     * 产品余额
     */
    private BigDecimal balanceAmt;

    /**
     * 产品份额
     */
    private BigDecimal prdQty;
    
    /**
     * 币种及申购兑付金额
     */
    private String ccyPurchase;

    /**
     * 初始净值
     */
    private BigDecimal originalValue;

    /**
     * 产品净值
     */
    private BigDecimal prdValue;

    /**
     * 累计净值
     */
    private BigDecimal totalValue;

    /**
     * 净值币种
     */
    private String valueCcy;

    /**
     * 人民币初始净值
     */
    private BigDecimal cnyOrlValue;

    /**
     * 折算人民币净值
     */
    private BigDecimal cnyPrdValue;

    /**
     * 人民币累计净值
     */
    private BigDecimal cnyTalValue;

    /**
     * 实现收益率
     */
    @RateFormat
    private BigDecimal reaYield;

    /**
     * 备注
     */
    private String remark;
    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 反馈状态
     */
    private String submitFeedbackStatus;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 数据日期
     */
    private Date statusDate;
    

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
     * 每万份份额分红
     */
    private BigDecimal shareAmtWan;

    /**
     * 每万份现金分红
     */
    private BigDecimal cashAmtWan;


	/***1217升级**/


	/***
	 *净值日期
	 */
	private Date  yieldDate;


	public Date getYieldDate() {
		return yieldDate;
	}

	public void setYieldDate(Date yieldDate) {
		this.yieldDate = yieldDate;
	}

	/***1217升级**/

	public String getRegistSeqno() {
		return registSeqno;
	}

	public void setRegistSeqno(String registSeqno) {
		this.registSeqno = registSeqno;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getIssuePartyId() {
		return issuePartyId;
	}

	public void setIssuePartyId(String issuePartyId) {
		this.issuePartyId = issuePartyId;
	}

	public String getProdRegistCode() {
		return prodRegistCode;
	}

	public void setProdRegistCode(String prodRegistCode) {
		this.prodRegistCode = prodRegistCode;
	}

	public String getVdate() {
		return vdate;
	}

	public void setVdate(String vdate) {
		this.vdate = vdate;
	}

	public String getMdate() {
		return mdate;
	}

	public void setMdate(String mdate) {
		this.mdate = mdate;
	}

	public BigDecimal getBankImpInt() {
		return bankImpInt;
	}

	public void setBankImpInt(BigDecimal bankImpInt) {
		this.bankImpInt = bankImpInt;
	}

	public BigDecimal getNewExpYield() {
		return newExpYield;
	}

	public void setNewExpYield(BigDecimal newExpYield) {
		this.newExpYield = newExpYield;
	}

	public BigDecimal getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(BigDecimal purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public BigDecimal getRedeemQty() {
		return redeemQty;
	}

	public void setRedeemQty(BigDecimal redeemQty) {
		this.redeemQty = redeemQty;
	}

	public BigDecimal getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public BigDecimal getPrdQty() {
		return prdQty;
	}

	public void setPrdQty(BigDecimal prdQty) {
		this.prdQty = prdQty;
	}

	public String getCcyPurchase() {
		return ccyPurchase;
	}

	public void setCcyPurchase(String ccyPurchase) {
		this.ccyPurchase = ccyPurchase;
	}

	public BigDecimal getPurchaseAmt() {
		return purchaseAmt;
	}

	public void setPurchaseAmt(BigDecimal purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}

	public BigDecimal getRedeemAmt() {
		return redeemAmt;
	}

	public void setRedeemAmt(BigDecimal redeemAmt) {
		this.redeemAmt = redeemAmt;
	}

	public BigDecimal getIntamt() {
		return intamt;
	}

	public void setIntamt(BigDecimal intamt) {
		this.intamt = intamt;
	}

	public BigDecimal getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(BigDecimal originalValue) {
		this.originalValue = originalValue;
	}

	public BigDecimal getPrdValue() {
		return prdValue;
	}

	public void setPrdValue(BigDecimal prdValue) {
		this.prdValue = prdValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public String getValueCcy() {
		return valueCcy;
	}

	public void setValueCcy(String valueCcy) {
		this.valueCcy = valueCcy;
	}

	public BigDecimal getCnyOrlValue() {
		return cnyOrlValue;
	}

	public void setCnyOrlValue(BigDecimal cnyOrlValue) {
		this.cnyOrlValue = cnyOrlValue;
	}

	public BigDecimal getCnyPrdValue() {
		return cnyPrdValue;
	}

	public void setCnyPrdValue(BigDecimal cnyPrdValue) {
		this.cnyPrdValue = cnyPrdValue;
	}

	public BigDecimal getCnyTalValue() {
		return cnyTalValue;
	}

	public void setCnyTalValue(BigDecimal cnyTalValue) {
		this.cnyTalValue = cnyTalValue;
	}

	public BigDecimal getReaYield() {
		return reaYield;
	}

	public void setReaYield(BigDecimal reaYield) {
		this.reaYield = reaYield;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getChbSubmitDeadline() {
		return chbSubmitDeadline;
	}

	public void setChbSubmitDeadline(Date chbSubmitDeadline) {
		this.chbSubmitDeadline = chbSubmitDeadline;
	}

	public Date getSubmitDeadline() {
		return submitDeadline;
	}

	public void setSubmitDeadline(Date submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSumitSeq() {
		return sumitSeq;
	}

	public void setSumitSeq(String sumitSeq) {
		this.sumitSeq = sumitSeq;
	}

	public String getSubmitFeedbackStatus() {
		return submitFeedbackStatus;
	}

	public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
		this.submitFeedbackStatus = submitFeedbackStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
     * 取得 每万份份额分红
     * @return 每万份份额分红
     */
    public BigDecimal getShareAmtWan() {
        return shareAmtWan;
    }

    /**
     * 设置 每万份份额分红
     * @param shareAmtWan 每万份份额分红
     */
    public void setShareAmtWan(BigDecimal shareAmtWan) {
        this.shareAmtWan = shareAmtWan;
    }

    /**
     * 取得 每万份现金分红
     * @return 每万份现金分红
     */
    public BigDecimal getCashAmtWan() {
        return cashAmtWan;
    }

    /**
     * 设置 每万份现金分红
     * @param cashAmtWan 每万份现金分红
     */
    public void setCashAmtWan(BigDecimal cashAmtWan) {
        this.cashAmtWan = cashAmtWan;
    }


}
