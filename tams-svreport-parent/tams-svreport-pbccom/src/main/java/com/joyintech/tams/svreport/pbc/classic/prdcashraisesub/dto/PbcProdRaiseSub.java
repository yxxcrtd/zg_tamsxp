package com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

/**
 * 描述:PBC_PROD_RAISING_SUB表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-06-11
 */
@TableName("PBC_PROD_RAISING_SUB")
public class PbcProdRaiseSub extends BaseEntity{
    /**
     * 登记序列:主键uuid
     */
    private String registSeqno;

    /**
     * 募集日期（月末日期）
     */
    @BusinessKey("RAISING_DATE")
    private Date raisingDate;

    /**
     * 募集日期（月末日期）
     */
    private String raisingDateStr;

    /**
     * 人行产品代码
     */
    @BusinessKey("PBC_PROD_ID")
    private String pbcProdId;

    /**
     * 地区代码:销售区域代码
     */
    @BusinessKey("BUY_PLACE")
    @DictFormat(DictCode.行政区划_省)
    private String buyPlace;

    /**
     * 客户类型:客户一级类型，枚举值参见CUSTOMER_TYPE1
     */
    @BusinessKey("CUSTOMER_TYPE1")
    @DictFormat(DictCode.人行客户类型)
    private String customerType1;

    /**
     * 币种代码:币种
     */
    @BusinessKey("CCY")
    private String ccy;

    /**
     * 当期申购金额
     */
    private BigDecimal raisingAmtsum;

    /**
     * 当期申购金额折人民币
     */
    private BigDecimal raisingAmtsumCny;

    /**
     * 当期申购份额
     */
    private BigDecimal raisingQty;

    /**
     * 当期兑付/赎回金额
     */
    private BigDecimal redamt;

    /**
     * 当期兑付/赎回金额折人民币
     */
    private BigDecimal redamtCny;

    /**
     * 当期兑付/赎回份额
     */
    private BigDecimal redqty;

    /**
     * 期末产品金额
     */
    private BigDecimal endamtsum;

    /**
     * 期末产品金额折人民币
     */
    private BigDecimal endamtsumCny;

    /**
     * 期末产品份额
     */
    private BigDecimal endqty;

    /**
     * 开放式净值型产品期末净值
     */
    private BigDecimal prodValue;

    /**
     * 开放式净值型产品期末净值折人民币
     */
    private BigDecimal prodValueCny;

    /**
     * 开放式净值型产品期末累计净值
     */
    private BigDecimal unitValueTotal;

    /**
     * 开放式净值型产品期末累计净值折人民币
     */
    private BigDecimal unitValueTotalCny;

    /**
     * 开放式非净值型产品预期最高收益率
     */
    private BigDecimal prodHighYield;

    /**
     * 开放式非净值型产品预期最低收益率
     */
    private BigDecimal prodLowYield;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 报送日期
     */
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
     * 是否生成登记
     */
    private String isGen;

    /**
     * 人行产品代码
     */
    private String prodId;
    
    /**
     * 收益率币种
     */
    private String yieldCcy;

    
    /**
     * 导出Excel的code
     */
    private String code;
    
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
    
    /**
     * 币种（导出净值sheet页用）
     */
    private String raiseCcy; 
    
    public String getRaiseCcy() {
		return raiseCcy;
	}

	public void setRaiseCcy(String raiseCcy) {
		this.raiseCcy = raiseCcy;
	}
    
    
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
    

	/**
     * 登记序列:主键uuid
     * @return REGIST_SEQNO 登记序列:主键uuid
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 登记序列:主键uuid
     * @param registSeqno 登记序列:主键uuid
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 募集日期（月末日期）
     * @return RAISING_DATE 募集日期（月末日期）
     */
    public Date getRaisingDate() {
        return raisingDate;
    }

    /**
     * 募集日期（月末日期）
     * @param raisingDate 募集日期（月末日期）
     */
    public void setRaisingDate(Date raisingDate) {
        this.raisingDate = raisingDate;
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
     * 地区代码:销售区域代码
     * @return BUY_PLACE 地区代码:销售区域代码
     */
    public String getBuyPlace() {
        return buyPlace;
    }

    /**
     * 地区代码:销售区域代码
     * @param buyPlace 地区代码:销售区域代码
     */
    public void setBuyPlace(String buyPlace) {
        this.buyPlace = buyPlace == null ? null : buyPlace.trim();
    }

    /**
     * 客户类型:客户一级类型，枚举值参见CUSTOMER_TYPE1
     * @return CUSTOMER_TYPE1 客户类型:客户一级类型，枚举值参见CUSTOMER_TYPE1
     */
    public String getCustomerType1() {
        return customerType1;
    }

    /**
     * 客户类型:客户一级类型，枚举值参见CUSTOMER_TYPE1
     * @param customerType1 客户类型:客户一级类型，枚举值参见CUSTOMER_TYPE1
     */
    public void setCustomerType1(String customerType1) {
        this.customerType1 = customerType1 == null ? null : customerType1.trim();
    }

    /**
     * 币种代码:币种
     * @return CCY 币种代码:币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种代码:币种
     * @param ccy 币种代码:币种
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 当期申购金额
     * @return RAISING_AMTSUM 当期申购金额
     */
    public BigDecimal getRaisingAmtsum() {
        return raisingAmtsum;
    }

    /**
     * 当期申购金额
     * @param raisingAmtsum 当期申购金额
     */
    public void setRaisingAmtsum(BigDecimal raisingAmtsum) {
        this.raisingAmtsum = raisingAmtsum;
    }

    /**
     * 当期申购金额折人民币
     * @return RAISING_AMTSUM_CNY 当期申购金额折人民币
     */
    public BigDecimal getRaisingAmtsumCny() {
        return raisingAmtsumCny;
    }

    /**
     * 当期申购金额折人民币
     * @param raisingAmtsumCny 当期申购金额折人民币
     */
    public void setRaisingAmtsumCny(BigDecimal raisingAmtsumCny) {
        this.raisingAmtsumCny = raisingAmtsumCny;
    }

    /**
     * 当期申购份额
     * @return RAISING_QTY 当期申购份额
     */
    public BigDecimal getRaisingQty() {
        return raisingQty;
    }

    /**
     * 当期申购份额
     * @param raisingQty 当期申购份额
     */
    public void setRaisingQty(BigDecimal raisingQty) {
        this.raisingQty = raisingQty;
    }

    /**
     * 当期兑付/赎回金额
     * @return REDAMT 当期兑付/赎回金额
     */
    public BigDecimal getRedamt() {
        return redamt;
    }

    /**
     * 当期兑付/赎回金额
     * @param redamt 当期兑付/赎回金额
     */
    public void setRedamt(BigDecimal redamt) {
        this.redamt = redamt;
    }

    /**
     * 当期兑付/赎回金额折人民币
     * @return REDAMT_CNY 当期兑付/赎回金额折人民币
     */
    public BigDecimal getRedamtCny() {
        return redamtCny;
    }

    /**
     * 当期兑付/赎回金额折人民币
     * @param redamtCny 当期兑付/赎回金额折人民币
     */
    public void setRedamtCny(BigDecimal redamtCny) {
        this.redamtCny = redamtCny;
    }

    /**
     * 当期兑付/赎回份额
     * @return REDQTY 当期兑付/赎回份额
     */
    public BigDecimal getRedqty() {
        return redqty;
    }

    /**
     * 当期兑付/赎回份额
     * @param redqty 当期兑付/赎回份额
     */
    public void setRedqty(BigDecimal redqty) {
        this.redqty = redqty;
    }

    /**
     * 期末产品金额
     * @return ENDAMTSUM 期末产品金额
     */
    public BigDecimal getEndamtsum() {
        return endamtsum;
    }

    /**
     * 期末产品金额
     * @param endamtsum 期末产品金额
     */
    public void setEndamtsum(BigDecimal endamtsum) {
        this.endamtsum = endamtsum;
    }

    /**
     * 期末产品金额折人民币
     * @return ENDAMTSUM_CNY 期末产品金额折人民币
     */
    public BigDecimal getEndamtsumCny() {
        return endamtsumCny;
    }

    /**
     * 期末产品金额折人民币
     * @param endamtsumCny 期末产品金额折人民币
     */
    public void setEndamtsumCny(BigDecimal endamtsumCny) {
        this.endamtsumCny = endamtsumCny;
    }

    /**
     * 期末产品份额
     * @return ENDQTY 期末产品份额
     */
    public BigDecimal getEndqty() {
        return endqty;
    }

    /**
     * 期末产品份额
     * @param endqty 期末产品份额
     */
    public void setEndqty(BigDecimal endqty) {
        this.endqty = endqty;
    }

    /**
     * 开放式净值型产品期末净值
     * @return PROD_VALUE 开放式净值型产品期末净值
     */
    public BigDecimal getProdValue() {
        return prodValue;
    }

    /**
     * 开放式净值型产品期末净值
     * @param prodValue 开放式净值型产品期末净值
     */
    public void setProdValue(BigDecimal prodValue) {
        this.prodValue = prodValue;
    }

    /**
     * 开放式净值型产品期末净值折人民币
     * @return PROD_VALUE_CNY 开放式净值型产品期末净值折人民币
     */
    public BigDecimal getProdValueCny() {
        return prodValueCny;
    }

    /**
     * 开放式净值型产品期末净值折人民币
     * @param prodValueCny 开放式净值型产品期末净值折人民币
     */
    public void setProdValueCny(BigDecimal prodValueCny) {
        this.prodValueCny = prodValueCny;
    }

    /**
     * 开放式净值型产品期末累计净值
     * @return UNIT_VALUE_TOTAL 开放式净值型产品期末累计净值
     */
    public BigDecimal getUnitValueTotal() {
        return unitValueTotal;
    }

    /**
     * 开放式净值型产品期末累计净值
     * @param unitValueTotal 开放式净值型产品期末累计净值
     */
    public void setUnitValueTotal(BigDecimal unitValueTotal) {
        this.unitValueTotal = unitValueTotal;
    }

    /**
     * 开放式净值型产品期末累计净值折人民币
     * @return UNIT_VALUE_TOTAL_CNY 开放式净值型产品期末累计净值折人民币
     */
    public BigDecimal getUnitValueTotalCny() {
        return unitValueTotalCny;
    }

    /**
     * 开放式净值型产品期末累计净值折人民币
     * @param unitValueTotalCny 开放式净值型产品期末累计净值折人民币
     */
    public void setUnitValueTotalCny(BigDecimal unitValueTotalCny) {
        this.unitValueTotalCny = unitValueTotalCny;
    }

    /**
     * 开放式非净值型产品预期最高收益率
     * @return PROD_HIGH_YIELD 开放式非净值型产品预期最高收益率
     */
    public BigDecimal getProdHighYield() {
        return prodHighYield;
    }

    /**
     * 开放式非净值型产品预期最高收益率
     * @param prodHighYield 开放式非净值型产品预期最高收益率
     */
    public void setProdHighYield(BigDecimal prodHighYield) {
        this.prodHighYield = prodHighYield;
    }

    /**
     * 开放式非净值型产品预期最低收益率
     * @return PROD_LOW_YIELD 开放式非净值型产品预期最低收益率
     */
    public BigDecimal getProdLowYield() {
        return prodLowYield;
    }

    /**
     * 开放式非净值型产品预期最低收益率
     * @param prodLowYield 开放式非净值型产品预期最低收益率
     */
    public void setProdLowYield(BigDecimal prodLowYield) {
        this.prodLowYield = prodLowYield;
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

	public String getIsGen() {
		return isGen;
	}

	public void setIsGen(String isGen) {
		this.isGen = isGen;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getYieldCcy() {
		return yieldCcy;
	}

	public void setYieldCcy(String yieldCcy) {
		this.yieldCcy = yieldCcy;
	}


    /**
     * 产品代码+机构代码
     * @return  品代码+机构代码
     */
    public String getProdidAndOrgCode() {
        return prodId + GlobalConstant.VERTICAL_LINE + orgCode;
    }

    /**
     * 产品代码+机构代码
     * @return  品代码+机构代码
     */
    public String getPbcProdidAndOrgCode() {
        return pbcProdId + GlobalConstant.VERTICAL_LINE + orgCode;
    }
    /**
     * 人行产品代码 + 地区代码:销售区域代码 + 客户类型:客户一级类型，枚举值参见CUSTOMER_TYPE1 + 币种代码:币种
     * @return  品代码+机构代码
     */
    public String getUniqueKey() {
        return pbcProdId + GlobalConstant.VERTICAL_LINE + buyPlace 
                + GlobalConstant.VERTICAL_LINE + customerType1
                + GlobalConstant.VERTICAL_LINE + ccy;
    }

    public String getRaisingDateStr() {
        return raisingDateStr;
    }

    public void setRaisingDateStr(String raisingDateStr) {
        this.raisingDateStr = raisingDateStr;
    }
    public String getStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
	}
	
	public String getSendStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
	}
	
	public String getSubmitFeedbackStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, this.getSubmitFeedbackStatus());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public String getStatusDateStr(){
        return null !=statusDate? DateUtil.formatDateTime(statusDate):null;
    }
    public String getSubmitTimeStr(){
        return null !=submitTime?DateUtil.formatDateTime(submitTime):null;
    }
    public String getPbcSubmitDeadlineStr(){
        return null !=pbcSubmitDeadline?DateUtil.format(pbcSubmitDeadline):null;
    }
	
}