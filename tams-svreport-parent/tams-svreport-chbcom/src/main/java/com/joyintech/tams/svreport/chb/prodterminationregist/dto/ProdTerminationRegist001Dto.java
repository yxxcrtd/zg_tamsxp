package com.joyintech.tams.svreport.chb.prodterminationregist.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;

/**
 * 描述:CHB_PRD_END_SUB表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-23
 */
@TableName("CHB_PRD_END_SUB")
public class ProdTerminationRegist001Dto extends BaseEntity implements ITableRow {
	
	private String prodRegCode;
	private String prodRegistCode;
    /**
     * 登记序列
     */
    private String registSeqno;
    /**
     * 报送流水号
     */
    private String sumitSeq;
    /**
     * 理财产品代码
     */
    @BusinessKey("PROD_ID")
    private String prodId;

    /**
     * 实际终止日期
     */
    private Date mdate;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 银行实际实现收入
     */
    private BigDecimal profit;

    /**
     * 兑付客户收益
     */
    private BigDecimal customProfit;

    /**
     * 兑付客户收益(原币种)
     */
    private BigDecimal customProfitOri;

    /**
     * 兑付客户总金额(原币种)
     */
    private BigDecimal customAmtOri;

    /**
     * 兑付客户总金额
     */
    private BigDecimal customAmt;

    /**
     * 兑付总份额
     */
    private BigDecimal customQty;

    /**
     * 客户实际年化收益率
     */
    private BigDecimal cusYearYield;

    /**
     * 产品实际年化收益率
     */
    private BigDecimal prdYearYield;

    /**
     * 本机构托管费
     */
    private BigDecimal custFeeIn;

    /**
     * 本机构管理费
     */
    private BigDecimal manFeeIn;

    /**
     * 本机构销售手续费
     */
    private BigDecimal couFeeIn;

    /**
     * 本机构其他产品费用
     */
    private BigDecimal otherFeeIn;

    /**
     * 其他机构托管费
     */
    private BigDecimal custFeeOut;

    /**
     * 其他机构管理费
     */
    private BigDecimal manFeeOut;

    /**
     * 其他机构销售手续费
     */
    private BigDecimal couFeeOut;

    /**
     * 投资顾问费用
     */
    private BigDecimal advFeeOut;

    /**
     * 其他机构其他产品费用
     */
    private BigDecimal otherFeeOut;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 反馈状态
     */
    @DictFormat(DictCode.报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 数据日期
     */
    private Date statusDate;

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
    
    //产品名称
    private String prodName;
    
    //产品登记编码
    private String regCode;

    
    public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	/**
     * 登记序列
     * @return REGIST_SEQNO 登记序列
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 登记序列
     * @param registSeqno 登记序列
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 理财产品代码
     * @return PROD_ID 理财产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 理财产品代码
     * @param prodId 理财产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 实际终止日期
     * @return MDATE 实际终止日期
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * 实际终止日期
     * @param mdate 实际终止日期
     */
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

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
     * 银行实际实现收入
     * @return PROFIT 银行实际实现收入
     */
    public BigDecimal getProfit() {
        return profit==null?BigDecimal.ZERO:profit;
    }

    /**
     * 银行实际实现收入
     * @param profit 银行实际实现收入
     */
    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    /**
     * 兑付客户收益
     * @return CUSTOM_PROFIT 兑付客户收益
     */
    public BigDecimal getCustomProfit() {
        return customProfit==null?BigDecimal.ZERO:customProfit;
    }

    /**
     * 兑付客户收益
     * @param customProfit 兑付客户收益
     */
    public void setCustomProfit(BigDecimal customProfit) {
        this.customProfit = customProfit;
    }

    /**
     * 兑付客户收益(原币种)
     * @return CUSTOM_PROFIT_ORI 兑付客户收益(原币种)
     */
    public BigDecimal getCustomProfitOri() {
        return customProfitOri;
    }

    /**
     * 兑付客户收益(原币种)
     * @param customProfitOri 兑付客户收益(原币种)
     */
    public void setCustomProfitOri(BigDecimal customProfitOri) {
        this.customProfitOri = customProfitOri == null ? null : customProfitOri;
    }

    /**
     * 兑付客户总金额(原币种)
     * @return CUSTOM_AMT_ORI 兑付客户总金额(原币种)
     */
    public BigDecimal getCustomAmtOri() {
        return customAmtOri;
    }

    /**
     * 兑付客户总金额(原币种)
     * @param customAmtOri 兑付客户总金额(原币种)
     */
    public void setCustomAmtOri(BigDecimal customAmtOri) {
        this.customAmtOri = customAmtOri == null ? null : customAmtOri;
    }

    /**
     * 兑付客户总金额
     * @return CUSTOM_AMT 兑付客户总金额
     */
    public BigDecimal getCustomAmt() {
        return customAmt==null?BigDecimal.ZERO:customAmt;
    }

    /**
     * 兑付客户总金额
     * @param customAmt 兑付客户总金额
     */
    public void setCustomAmt(BigDecimal customAmt) {
        this.customAmt = customAmt;
    }

    /**
     * 兑付总份额
     * @return CUSTOM_QTY 兑付总份额
     */
    public BigDecimal getCustomQty() {
        return customQty==null?BigDecimal.ZERO:customQty;
    }

    /**
     * 兑付总份额
     * @param customQty 兑付总份额
     */
    public void setCustomQty(BigDecimal customQty) {
        this.customQty = customQty;
    }

    /**
     * 客户实际年化收益率
     * @return CUS_YEAR_YIELD 客户实际年化收益率
     */
    public BigDecimal getCusYearYield() {
        return cusYearYield;
    }

    /**
     * 客户实际年化收益率
     * @param cusYearYield 客户实际年化收益率
     */
    public void setCusYearYield(BigDecimal cusYearYield) {
        this.cusYearYield = cusYearYield;
    }

    /**
     * 产品实际年化收益率
     * @return PRD_YEAR_YIELD 产品实际年化收益率
     */
    public BigDecimal getPrdYearYield() {
        return prdYearYield;
    }

    /**
     * 产品实际年化收益率
     * @param prdYearYield 产品实际年化收益率
     */
    public void setPrdYearYield(BigDecimal prdYearYield) {
        this.prdYearYield = prdYearYield;
    }

    /**
     * 本机构托管费
     * @return CUST_FEE_IN 本机构托管费
     */
    public BigDecimal getCustFeeIn() {
        return custFeeIn==null?BigDecimal.ZERO:custFeeIn;
    }

    /**
     * 本机构托管费
     * @param custFeeIn 本机构托管费
     */
    public void setCustFeeIn(BigDecimal custFeeIn) {
        this.custFeeIn = custFeeIn;
    }

    /**
     * 本机构管理费
     * @return MAN_FEE_IN 本机构管理费
     */
    public BigDecimal getManFeeIn() {
        return manFeeIn==null?BigDecimal.ZERO:manFeeIn;
    }

    /**
     * 本机构管理费
     * @param manFeeIn 本机构管理费
     */
    public void setManFeeIn(BigDecimal manFeeIn) {
        this.manFeeIn = manFeeIn;
    }

    /**
     * 本机构销售手续费
     * @return COU_FEE_IN 本机构销售手续费
     */
    public BigDecimal getCouFeeIn() {
        return couFeeIn==null?BigDecimal.ZERO:couFeeIn;
    }

    /**
     * 本机构销售手续费
     * @param couFeeIn 本机构销售手续费
     */
    public void setCouFeeIn(BigDecimal couFeeIn) {
        this.couFeeIn = couFeeIn;
    }

    /**
     * 本机构其他产品费用
     * @return OTHER_FEE_IN 本机构其他产品费用
     */
    public BigDecimal getOtherFeeIn() {
        return otherFeeIn==null?BigDecimal.ZERO:otherFeeIn;
    }

    /**
     * 本机构其他产品费用
     * @param otherFeeIn 本机构其他产品费用
     */
    public void setOtherFeeIn(BigDecimal otherFeeIn) {
        this.otherFeeIn = otherFeeIn;
    }

    /**
     * 其他机构托管费
     * @return CUST_FEE_OUT 其他机构托管费
     */
    public BigDecimal getCustFeeOut() {
        return custFeeOut==null?BigDecimal.ZERO:custFeeOut;
    }

    /**
     * 其他机构托管费
     * @param custFeeOut 其他机构托管费
     */
    public void setCustFeeOut(BigDecimal custFeeOut) {
        this.custFeeOut = custFeeOut;
    }

    /**
     * 其他机构管理费
     * @return MAN_FEE_OUT 其他机构管理费
     */
    public BigDecimal getManFeeOut() {
        return manFeeOut==null?BigDecimal.ZERO:manFeeOut;
    }

    /**
     * 其他机构管理费
     * @param manFeeOut 其他机构管理费
     */
    public void setManFeeOut(BigDecimal manFeeOut) {
        this.manFeeOut = manFeeOut;
    }

    /**
     * 其他机构销售手续费
     * @return COU_FEE_OUT 其他机构销售手续费
     */
    public BigDecimal getCouFeeOut() {
        return couFeeOut==null?BigDecimal.ZERO:couFeeOut;
    }

    /**
     * 其他机构销售手续费
     * @param couFeeOut 其他机构销售手续费
     */
    public void setCouFeeOut(BigDecimal couFeeOut) {
        this.couFeeOut = couFeeOut;
    }

    /**
     * 投资顾问费用
     * @return ADV_FEE_OUT 投资顾问费用
     */
    public BigDecimal getAdvFeeOut() {
        return advFeeOut==null?BigDecimal.ZERO:advFeeOut;
    }

    /**
     * 投资顾问费用
     * @param advFeeOut 投资顾问费用
     */
    public void setAdvFeeOut(BigDecimal advFeeOut) {
        this.advFeeOut = advFeeOut;
    }

    /**
     * 其他机构其他产品费用
     * @return OTHER_FEE_OUT 其他机构其他产品费用
     */
    public BigDecimal getOtherFeeOut() {
        return otherFeeOut==null?BigDecimal.ZERO:otherFeeOut;
    }

    /**
     * 其他机构其他产品费用
     * @param otherFeeOut 其他机构其他产品费用
     */
    public void setOtherFeeOut(BigDecimal otherFeeOut) {
        this.otherFeeOut = otherFeeOut;
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
     * 反馈状态
     * @return SUBMIT_FEEDBACK_STATUS 反馈状态
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 反馈状态
     * @param submitFeedbackStatus 反馈状态
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
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
     * 数据状态 参照数据字典:STATUS
     * @return STATUS 数据状态 参照数据字典:STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * 数据状态 参照数据字典:STATUS
     * @param status 数据状态 参照数据字典:STATUS
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
     * 中债报送截止日
     * @return CHB_SUBMIT_DEADLINE 中债报送截止日
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 中债报送截止日
     * @param chbSubmitDeadline 中债报送截止日
     */
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
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
    //报送状态
    public String getSendStatusStr() {
		return SvrDataUtil.getNameFromDataDict(DictCode.报送状态,getSendStatus());
	}
    //反馈状态
    public String getSubmitFeedbackStatusStr() {
		return SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态,getSubmitFeedbackStatus());
	}
    //数据状态
    public String getStatusStr() {
		return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态,getStatus());
	}
    
    public String getMdateStr(){
        // modify by dubaojun 2020-10-31 当mdate为null时报错修复 start
//    	DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
//    	return format1.format(mdate);
        return DateUtil.format(mdate, DateUtil.DEFAULT_OTHER_FORMAT);
        // modify by dubaojun 2020-10-31 当mdate为null时报错修复 end
    }

	public String getProdRegCode() {
		return prodRegCode;
	}

	public void setProdRegCode(String prodRegCode) {
		this.prodRegCode = prodRegCode;
	}

	public String getSumitSeq() {
		return sumitSeq;
	}

	public void setSumitSeq(String sumitSeq) {
		this.sumitSeq = sumitSeq;
	}

	public String getProdRegistCode() {
		return prodRegistCode;
	}

	public void setProdRegistCode(String prodRegistCode) {
		this.prodRegistCode = prodRegistCode;
	}
    
}