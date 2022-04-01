/*
 * 文件名：ChbPrdRaisetotSubDto.java
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


/**
 * 产品募集总量登记
 * @description 
 * @author 王波
 * @version 1.0 2020年9月24日
 * @see ChbPrdRaisetotSubDto
 * @since 1.0
 */
public class ChbPrdRaisetotSubDto {

	
	private String prodRegCode;	


    /**
     * 数据状态 参照数据字典:STATUS
     */
    private String status;

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
     * 报送日期
     */
    private Date submitTime;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;
    //报送截止日开始
    private Date submitendDateStart;
    //报送截止日结束
    private Date submitendDateEnd;
    //数据日期开始
    private Date dataDateStart;
    //数据日期结束
    private Date dataDateEnd;
    //报送日期开始
    private Date submitDateStart;
    //报送日期结束
    private Date submitDateEnd;

    //募集总量已登记序列
    private String regseq;
    //募集总量未登记序列
    private String unRegseq;
	/**
     * 登记序列
     */
    private String registSeqno;
    //产品名称
    private String prodName;
    /**
     * 取得 登记序列
     * @return 登记序列
     */
    public String getRegistSeqno() {
        return registSeqno;
    }
	/**
	 * 发行状态
	 */
	private String issuingStatus;
    //募集结束日
    private Date raiseEnddate;
    /**
     * 产品销售区域及募集金额
     */
    private String saleAreaAmt;
    /**
     * 认购币种及金额
     */
    private String subscribeCcyAmt;
    //目标客户类型（个人，机构）
    private String saletarget;
    //产品登记编码
    private String regCode;
    /**
     * 设置 登记序列
     * @param registSeqno 登记序列
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }
	 /**
     * 产品代码
     */
    private String prodId;

    /**
     * 发行机构代码
     */
    private String issuePartyId;
	/**
     * 反馈状态
     */
    private String submitFeedbackStatus;
    /**
     * 报送流水号
     */
    private String sumitSeq;
    /**
     * 产品登记编码
     */
    private String prodRegistCode;

    /**
     * 个人投资者总数
     */
    private Integer singleTotal;

    /**
     * 法人投资者总数
     */
    private Integer legalTotal;

    /**
     * 非法人投资者总数
     */
    private Integer unlegalTotal;

    /**
     * 认购币种
     */
    private String subscribeCcy;

    /**
     * 认购金额
     */
    private String subscribeAmt;

    /**
     * 折算人民币金额
     */
    private String cnyAmt;

    /**
     * 产品销售区域 参照数据字典
     */
    private String saleArea;

    /**
     * 区域募集金额
     */
    private String areaAmt;

    /**
     * 实际募集金额
     */
    private BigDecimal totalAmt;

    /**
     * 募集总份额
     */
    private BigDecimal raiseQty;

    /**
     * 是否代销 参照数据字典
     */
    private String isConsign;

    /**
     * 代销总金额
     */
    private BigDecimal consignAmt;

    /**
     * 备注
     */
    private String remark;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 数据日期
     */
    private Date statusDate;



    /***1217升级**/


    /**
     * 资金托管账号
     */
    private String busiAcctNo;

    /**
     * 资金托管账户
     */
    private String acctName;

    public String getBusiAcctNo() {
        return busiAcctNo;
    }

    public void setBusiAcctNo(String busiAcctNo) {
        this.busiAcctNo = busiAcctNo;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    /***1217升级**/

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
     * 取得 发行机构代码
     * @return 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 设置 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 取得 产品登记编码
     * @return 产品登记编码
     */
    public String getProdRegistCode() {
        return prodRegistCode;
    }

    /**
     * 设置 产品登记编码
     * @param prodRegistCode 产品登记编码
     */
    public void setProdRegistCode(String prodRegistCode) {
        this.prodRegistCode = prodRegistCode == null ? null : prodRegistCode.trim();
    }

    /**
     * 取得 个人投资者总数
     * @return 个人投资者总数
     */
    public Integer getSingleTotal() {
        return singleTotal;
    }

    /**
     * 设置 个人投资者总数
     * @param singleTotal 个人投资者总数
     */
    public void setSingleTotal(Integer singleTotal) {
        this.singleTotal = singleTotal;
    }

    /**
     * 取得 法人投资者总数
     * @return 法人投资者总数
     */
    public Integer getLegalTotal() {
        return legalTotal;
    }

    /**
     * 设置 法人投资者总数
     * @param legalTotal 法人投资者总数
     */
    public void setLegalTotal(Integer legalTotal) {
        this.legalTotal = legalTotal;
    }

    /**
     * 取得 非法人投资者总数
     * @return 非法人投资者总数
     */
    public Integer getUnlegalTotal() {
        return unlegalTotal;
    }

    /**
     * 设置 非法人投资者总数
     * @param unlegalTotal 非法人投资者总数
     */
    public void setUnlegalTotal(Integer unlegalTotal) {
        this.unlegalTotal = unlegalTotal;
    }

    /**
     * 取得 认购币种
     * @return 认购币种
     */
    public String getSubscribeCcy() {
        return subscribeCcy;
    }

    /**
     * 设置 认购币种
     * @param subscribeCcy 认购币种
     */
    public void setSubscribeCcy(String subscribeCcy) {
        this.subscribeCcy = subscribeCcy == null ? null : subscribeCcy.trim();
    }

    /**
     * 取得 认购金额
     * @return 认购金额
     */
    public String getSubscribeAmt() {
        return subscribeAmt;
    }

    /**
     * 设置 认购金额
     * @param subscribeAmt 认购金额
     */
    public void setSubscribeAmt(String subscribeAmt) {
        this.subscribeAmt = subscribeAmt == null ? null : subscribeAmt.trim();
    }

    /**
     * 取得 折算人民币金额
     * @return 折算人民币金额
     */
    public String getCnyAmt() {
        return cnyAmt;
    }

    /**
     * 设置 折算人民币金额
     * @param cnyAmt 折算人民币金额
     */
    public void setCnyAmt(String cnyAmt) {
        this.cnyAmt = cnyAmt == null ? null : cnyAmt.trim();
    }

    /**
     * 取得 产品销售区域 参照数据字典
     * @return 产品销售区域 参照数据字典
     */
    public String getSaleArea() {
        return saleArea;
    }

    /**
     * 设置 产品销售区域 参照数据字典
     * @param saleArea 产品销售区域 参照数据字典
     */
    public void setSaleArea(String saleArea) {
        this.saleArea = saleArea == null ? null : saleArea.trim();
    }

    /**
     * 取得 区域募集金额
     * @return 区域募集金额
     */
    public String getAreaAmt() {
        return areaAmt;
    }

    /**
     * 设置 区域募集金额
     * @param areaAmt 区域募集金额
     */
    public void setAreaAmt(String areaAmt) {
        this.areaAmt = areaAmt == null ? null : areaAmt.trim();
    }

    /**
     * 取得 实际募集金额
     * @return 实际募集金额
     */
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    /**
     * 设置 实际募集金额
     * @param totalAmt 实际募集金额
     */
    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    /**
     * 取得 募集总份额
     * @return 募集总份额
     */
    public BigDecimal getRaiseQty() {
        return raiseQty;
    }

    /**
     * 设置 募集总份额
     * @param raiseQty 募集总份额
     */
    public void setRaiseQty(BigDecimal raiseQty) {
        this.raiseQty = raiseQty;
    }

    /**
     * 取得 是否代销 参照数据字典
     * @return 是否代销 参照数据字典
     */
    public String getIsConsign() {
        return isConsign;
    }

    /**
     * 设置 是否代销 参照数据字典
     * @param isConsign 是否代销 参照数据字典
     */
    public void setIsConsign(String isConsign) {
        this.isConsign = isConsign == null ? null : isConsign.trim();
    }

    /**
     * 取得 代销总金额
     * @return 代销总金额
     */
    public BigDecimal getConsignAmt() {
        return consignAmt;
    }

    /**
     * 设置 代销总金额
     * @param consignAmt 代销总金额
     */
    public void setConsignAmt(BigDecimal consignAmt) {
        this.consignAmt = consignAmt;
    }

    /**
     * 取得 备注
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 取得 工作流代码
     * @return 工作流代码
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 设置 工作流代码
     * @param workflowId 工作流代码
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 取得 报送状态
     * @return 报送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置 报送状态
     * @param sendStatus 报送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 取得 数据日期
     * @return 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 设置 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

	public String getSubmitFeedbackStatus() {
		return submitFeedbackStatus;
	}

	public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
		this.submitFeedbackStatus = submitFeedbackStatus;
	}

	public String getSumitSeq() {
		return sumitSeq;
	}

	public void setSumitSeq(String sumitSeq) {
		this.sumitSeq = sumitSeq;
	}

	public String getSaleAreaAmt() {
		return saleAreaAmt;
	}

	public void setSaleAreaAmt(String saleAreaAmt) {
		this.saleAreaAmt = saleAreaAmt;
	}

	public String getSubscribeCcyAmt() {
		return subscribeCcyAmt;
	}

	public void setSubscribeCcyAmt(String subscribeCcyAmt) {
		this.subscribeCcyAmt = subscribeCcyAmt;
	}

	public String getIssuingStatus() {
		return issuingStatus;
	}

	public void setIssuingStatus(String issuingStatus) {
		this.issuingStatus = issuingStatus;
	}

	public Date getRaiseEnddate() {
		return raiseEnddate;
	}

	public void setRaiseEnddate(Date raiseEnddate) {
		this.raiseEnddate = raiseEnddate;
	}

	public String getSaletarget() {
		return saletarget;
	}

	public void setSaletarget(String saletarget) {
		this.saletarget = saletarget;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdRegCode() {
		return prodRegCode;
	}

	public void setProdRegCode(String prodRegCode) {
		this.prodRegCode = prodRegCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
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

	public Date getSubmitendDateStart() {
		return submitendDateStart;
	}

	public void setSubmitendDateStart(Date submitendDateStart) {
		this.submitendDateStart = submitendDateStart;
	}

	public Date getSubmitendDateEnd() {
		return submitendDateEnd;
	}

	public void setSubmitendDateEnd(Date submitendDateEnd) {
		this.submitendDateEnd = submitendDateEnd;
	}

	public Date getDataDateStart() {
		return dataDateStart;
	}

	public void setDataDateStart(Date dataDateStart) {
		this.dataDateStart = dataDateStart;
	}

	public Date getDataDateEnd() {
		return dataDateEnd;
	}

	public void setDataDateEnd(Date dataDateEnd) {
		this.dataDateEnd = dataDateEnd;
	}

	public Date getSubmitDateStart() {
		return submitDateStart;
	}

	public void setSubmitDateStart(Date submitDateStart) {
		this.submitDateStart = submitDateStart;
	}

	public Date getSubmitDateEnd() {
		return submitDateEnd;
	}

	public void setSubmitDateEnd(Date submitDateEnd) {
		this.submitDateEnd = submitDateEnd;
	}

	public String getRegseq() {
		return regseq;
	}

	public void setRegseq(String regseq) {
		this.regseq = regseq;
	}

	public String getUnRegseq() {
		return unRegseq;
	}

	public void setUnRegseq(String unRegseq) {
		this.unRegseq = unRegseq;
	}
}
