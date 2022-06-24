package com.joyintech.tams.svreport.pbc.classic.prdinitraisesub.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.position.dto.ChbPrdPosition;

/**
 * 描述:PBC_INIT_PROD_RAISING_SUB表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-06-04
 */
@TableName("PBC_INIT_PROD_RAISING_SUB")
public class PbcInitProdRaisingSub extends BaseEntity {
	private String regseq;
    /**
     * 主键uuid
     */
    private String registSeqno;

    /**
     * 人行产品代码
     */
    @BusinessKey("PBC_PROD_ID")
    private String pbcProdId;

    /**
     * 销售区域代码
     */
    @BusinessKey("BUY_PLACE")
    @DictFormat(DictCode.行政区划_省)
    private String buyPlace;

    /**
     * 客户一级类型，枚举值参见CUSTOMER_TYPE1
     */
    @BusinessKey("CUSTOMER_TYPE1")
    @DictFormat(DictCode.人行客户类型)
    private String customerType1;

    /**
     * 币种
     */
    @BusinessKey("CCY")
    private String ccy;

    /**
     * 起始募集金额
     */
    private BigDecimal initRaisingAmtsum;

    /**
     * 起始募集金额折人民币
     */
    private BigDecimal initRaisingAmtsumcny;

    /**
     * 起始募集份额
     */
    private BigDecimal initRaisingQty;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 报送日期
     */
//    @JsonFormat(pattern="yyyy-MM-dd")
    private Date submitTime;

    /**
     * 报送反馈状态
     */
    @DictFormat(DictCode.人行报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 人行报送截止日
     */
    private Date pbcSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 机构代码
     */
    private String orgCode;

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
     * 产品头寸列表
     */
    private List<ChbPrdPosition> prdPositionList;
    
    /**
     * 产品内部代码
     */
    private String prodId;
    
    /**
     * 运行方式
     */
    private String operateMode;
    
    /**
     * 产品类型
     */
    private String ivstorientation;
    
    /**
     * 产品起始日期
     */
    private Date vdate;
    
    private Date raiseEnddate;
    
    /**
     *  内部产品代码
     */
    private String insideProdId;
    
    /**
     *  人行代码
     */
    private String pbcCode;
    
    /**
     *  人行产品代码（页面展示用）
     */
    private String pbcProdIdShow;
    
    
    /** 人行产品代码（页面展示用）
     * @return PBC_PROD_ID
     */
    public String getPbcProdIdShow() {
    	return pbcProdIdShow;
    }
    
    /** 
     * @param pbcProdIdShow
     */
    public void setPbcProdIdShow(String pbcProdIdShow) {
    	this.pbcProdIdShow = pbcProdIdShow;
    }
   
    /** 人行代码
     * @return PBC_CODE 人行代码
     */
    public String getPbcCode() {
		return pbcCode;
	}

	/**
	 * @param pbcCode
	 */
	public void setPbcCode(String pbcCode) {
		this.pbcCode = pbcCode;
	}

    
    /** 内部产品代码
     * @return INSIDE_PROD_ID 内部产品代码
     */
    public String getInsideProdId() {
		return insideProdId;
	}

	/**
	 * @param insideProdId
	 */
	public void setInsideProdId(String insideProdId) {
		this.insideProdId = insideProdId;
	}
    
    public Date getRaiseEnddate() {
		return raiseEnddate;
	}

	public void setRaiseEnddate(Date raiseEnddate) {
		this.raiseEnddate = raiseEnddate;
	}

	public List<ChbPrdPosition> getPrdPositionList() {
		return prdPositionList;
	}

	public void setPrdPositionList(List<ChbPrdPosition> prdPositionList) {
		this.prdPositionList = prdPositionList;
	}

	/**
     * 主键uuid
     * @return REGIST_SEQNO 主键uuid
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 主键uuid
     * @param registSeqno 主键uuid
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 人行产品代码
     * @return PBC_PROD_ID 人行产品代码
     */
    public String getPbcProdId() {
        return pbcProdId;
    }

    /**
     * 人行产品代码
     * @param pbcProdId 人行产品代码
     */
    public void setPbcProdId(String pbcProdId) {
        this.pbcProdId = pbcProdId == null ? null : pbcProdId.trim();
    }

    /**
     * 销售区域代码
     * @return BUY_PLACE 销售区域代码
     */
    public String getBuyPlace() {
        return buyPlace;
    }

    /**
     * 销售区域代码
     * @param buyPlace 销售区域代码
     */
    public void setBuyPlace(String buyPlace) {
        this.buyPlace = buyPlace == null ? null : buyPlace.trim();
    }
    
    /**
     * 客户一级类型，枚举值参见CUSTOMER_TYPE1
     * @return CUSTOMER_TYPE1 客户一级类型，枚举值参见CUSTOMER_TYPE1
     */
    public String getCustomerType1() {
        return customerType1;
    }

    /**
     * 客户一级类型，枚举值参见CUSTOMER_TYPE1
     * @param customerType1 客户一级类型，枚举值参见CUSTOMER_TYPE1
     */
    public void setCustomerType1(String customerType1) {
        this.customerType1 = customerType1 == null ? null : customerType1.trim();
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
     * 起始募集金额
     * @return INIT_RAISING_AMTSUM 起始募集金额
     */
    public BigDecimal getInitRaisingAmtsum() {
        return initRaisingAmtsum;
    }

    /**
     * 起始募集金额
     * @param initRaisingAmtsum 起始募集金额
     */
    public void setInitRaisingAmtsum(BigDecimal initRaisingAmtsum) {
        this.initRaisingAmtsum = initRaisingAmtsum;
    }

    /**
     * 起始募集金额折人民币
     * @return INIT_RAISING_AMTSUMCNY 起始募集金额折人民币
     */
    public BigDecimal getInitRaisingAmtsumcny() {
        return initRaisingAmtsumcny;
    }

    /**
     * 起始募集金额折人民币
     * @param initRaisingAmtsumcny 起始募集金额折人民币
     */
    public void setInitRaisingAmtsumcny(BigDecimal initRaisingAmtsumcny) {
        this.initRaisingAmtsumcny = initRaisingAmtsumcny;
    }

    /**
     * 起始募集份额
     * @return INIT_RAISING_QTY 起始募集份额
     */
    public BigDecimal getInitRaisingQty() {
        return initRaisingQty;
    }

    /**
     * 起始募集份额
     * @param initRaisingQty 起始募集份额
     */
    public void setInitRaisingQty(BigDecimal initRaisingQty) {
        this.initRaisingQty = initRaisingQty;
    }

    /**
     * 报送状态
     * @return SEND_STATUS 报送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 报送状态
     * @param sendStatus 报送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 报送日期
     * @return SUBMIT_TIME 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 报送日期
     * @param submitTime 报送日期
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 报送反馈状态
     * @return SUBMIT_FEEDBACK_STATUS 报送反馈状态
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 报送反馈状态
     * @param submitFeedbackStatus 报送反馈状态
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 报送流水号
     * @return SUMIT_SEQ 报送流水号
     */
    public String getSumitSeq() {
        return sumitSeq;
    }

    /**
     * 报送流水号
     * @param sumitSeq 报送流水号
     */
    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq == null ? null : sumitSeq.trim();
    }

    /**
     * 数据日期
     * @return STATUS_DATE 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 人行报送截止日
     * @return PBC_SUBMIT_DEADLINE 人行报送截止日
     */
    public Date getPbcSubmitDeadline() {
        return pbcSubmitDeadline;
    }



    /**
     * 人行报送截止日
     * @param pbcSubmitDeadline 人行报送截止日
     */
    public void setPbcSubmitDeadline(Date pbcSubmitDeadline) {
        this.pbcSubmitDeadline = pbcSubmitDeadline;
    }

    /**
     * 行内报送截止日
     * @return SUBMIT_DEADLINE 行内报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 行内报送截止日
     * @param submitDeadline 行内报送截止日
     */
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
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
     * 
     * @return regseq
     */
	public String getRegseq() {
		return regseq;
	}
	
	/**
	 * 
	 * @param regseq
	 */
	public void setRegseq(String regseq) {
		this.regseq = regseq;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getProdId() {
		return prodId;
	}
	
	/**
	 * @param prodId
	 */
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return operateMode
	 */
	public String getOperateMode() {
		return operateMode;
	}

	/**
	 * @param operateMode
	 */
	public void setOperateMode(String operateMode) {
		this.operateMode = operateMode;
	}

	/**
	 * 
	 * @return ivstorientation
	 */
	public String getIvstorientation() {
		return ivstorientation;
	}

	/**
	 * @param ivstorientation
	 */
	public void setIvstorientation(String ivstorientation) {
		this.ivstorientation = ivstorientation;
	}

	/**
	 * 
	 * @return vdate
	 */
	public Date getVdate() {
		return vdate;
	}

	/**
	 * 
	 * @param vdate
	 */
	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}
	public String getStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
	}
    public String getVdateStr() {
        return DateUtil.format(this.getVdate());
    }
    public String getPbcSubmitDeadlineStr() {
        return  DateUtil.format(this.getPbcSubmitDeadline());
    }

    public String getSendStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
	}
		
	public String getSubmitFeedbackStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, this.getSubmitFeedbackStatus());
	}
	public String getStatusDateStr(){
	    return null!=statusDate?DateUtil.formatDateTime(statusDate):null;
    }
    public String getSubmitTimeStr(){
	    return null!=submitTime?DateUtil.formatDateTime(submitTime):null;
    }
}