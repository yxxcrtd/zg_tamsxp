package com.joyintech.tams.svreport.chb.investtradereg.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.report.common.util.SysDataUtil;

/**
 * 描述:CHB_INVEST_TRADE_SUB表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-25
 */
@TableName("CHB_INVEST_TRADE_SUB")
public class InvestTradeRegist001Dto extends BaseEntity implements ITableRow {
    /**
     * 产品登记编码
     */
	private  String  prodRegistCode;
	/**
     * 登记序号
     */
    private String registSeqno;

    /**
     * 行内交易编码
     */
    @BusinessKey("TRADE_CODE")
    private String tradeCode;

    /**
     * 投资品种
     */
    private String investVariety;

    /**
     * 投资类型
     */
    private String investType;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 发行机构代码
     */
    private String registBankCode;

    /**
     * 行内资产/负债编码
     */
    private String assetCode;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 币种
     */
    private String ccyName;

    /**
     * 折算人民币金额
     */
    private BigDecimal cnyAmt;

    /**
     * 发生金额
     */
    private BigDecimal amt;

    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 资产流动类型
     */
    @DictFormat(DictCode.资金流动类型)
    private String curAssetType;

    /**
     * 资产流动类型
     */
    private String curAssetTypeName;

    /**
     * 资产流动类型说明
     */
    private String curAssetdetType;

    /**
     * 交易日
     */
    private Date dealdate;

    /**
     * 交易交易起始日
     */
    private Date tradeTime;
    /**
     * 关联交易情况
     */
    @DictFormat(DictCode.关联交易情况)
    private String relatedTradeStatus;

    /**
     * 交易对手
     */
    private String tradeCounter;

    /**
     * 交易对手
     */
    private String tradeCounterName;

    /**
     * 单位成交价格（全价）
     */
    private BigDecimal unitPriceFull;

    /**
     * 单位成交价格（净价）
     */
    private BigDecimal unitPriceNet;

    /**
     * 到期收益率
     */
    private BigDecimal endRate;

    /**
     * 到期收益率
     */
    private String endRateString;

    /**
     * 成交编号/合同号
     */
    private String contractCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 通道代码
     */
    private String chlCpctId;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 通道名称
     */
    private String chlAgrtName;

    /**
     * 费用代码
     */
    private String feeId;

    /**
     * 费用名称
     */
    private String feeName;

    /**
     * 费用类型
     */
    private String feeType;

    /**
     * 费用二级类型
     */
    private String feeSectype;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 数据状态
     */
    private String statusName;

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
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 报送状态
     */
    private String sendStatusName;

    /**
     * 报送反馈状态
     */
    @DictFormat(DictCode.报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 报送反馈状态
     */
    private String submitFeedbackStatusName;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 删除原因
     */
    private String deleteRes;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 创建部门
     */
    private String createDept;
    
    /**
     * 交易登记日
     */
    private Date dealRegistDate;
    
    /**
     * 资产是否已登记
     */
    private String isAssetSub;
    
    /**
     * 资产是否已登记
     */
    private String isAssetSubName;
    
    /**
     * 产品名称
     */
    private String prodName;
    
    /**
     * 产品登记编码
     */
    private String prodRegCode;
    
    /**
     * 关联交易seqno
     */
    private String refTradeseqno;
    
    /**
     * 产品类型标识 N M 净值型
     */
    private String prodType;
    
    /**
     * 交易类型01	到期-本金/02	到期-利息/03	份额调减/04	份额调增/05	红利再投/06	交易场所费用支出/07	理财托管户收息确认/08	买入/09	卖出/10	其他费用支出/11	其他账户收息确认/12	申购/13	自定义现金流收入/14	自定义现金流支出/15	赎回/16	通道费用支付/17	通道交易-买入/18	通道交易-卖出/19	通道托管（管理）费/20	现金分红/21	银行间费用支出/22	营改增缴纳/23	中间付息/24	中间还本-本金/25	中间还本-利息
     */
    private String dealtype;
    /**
     * 资产计量方式
     */
	@DictFormat(DictCode.资产计量方式)
    private String assetMethod;

    /**
     * 交易对手方类型
     */
	@DictFormat(DictCode.对手方类型)
    private String rivalPartyType;
    
    private  String  assetMethodNum;
    
    
    private String rivalPartyTypeNum;

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
     * 登记序号
     * @return REGIST_SEQNO 登记序号
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 登记序号
     * @param registSeqno 登记序号
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 行内交易编码
     * @return TRADE_CODE 行内交易编码
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * 行内交易编码
     * @param tradeCode 行内交易编码
     */
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    /**
     * 投资品种
     * @return INVEST_VARIETY 投资品种
     */
    public String getInvestVariety() {
        return investVariety;
    }

    /**
     * 投资品种
     * @param investVariety 投资品种
     */
    public void setInvestVariety(String investVariety) {
        this.investVariety = investVariety == null ? null : investVariety.trim();
    }

    /**
     * 投资类型
     * @return INVEST_TYPE 投资类型
     */
    public String getInvestType() {
        return investType;
    }

    /**
     * 投资类型
     * @param investType 投资类型
     */
    public void setInvestType(String investType) {
        this.investType = investType == null ? null : investType.trim();
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
     * 发行机构代码
     * @return REGIST_BANK_CODE 发行机构代码
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 发行机构代码
     * @param registBankCode 发行机构代码
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
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
     * 折算人民币金额
     * @return CNY_AMT 折算人民币金额
     */
    public BigDecimal getCnyAmt() {
        return cnyAmt;
    }

    /**
     * 折算人民币金额
     * @param cnyAmt 折算人民币金额
     */
    public void setCnyAmt(BigDecimal cnyAmt) {
        this.cnyAmt = cnyAmt;
    }

    /**
     * 发生金额
     * @return AMT 发生金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 发生金额
     * @param amt 发生金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 数量
     * @return NUM 数量
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * 数量
     * @param num 数量
     */
    public void setNum(BigDecimal num) {
        this.num = num;
    }

    /**
     * 资产流动类型
     * @return CUR_ASSET_TYPE 资产流动类型
     */
    public String getCurAssetType() {
        return curAssetType;
    }

    /**
     * 资产流动类型
     * @param curAssetType 资产流动类型
     */
    public void setCurAssetType(String curAssetType) {
        this.curAssetType = curAssetType == null ? null : curAssetType.trim();
    }

    /**
     * 资产流动类型说明
     * @return CUR_ASSETDET_TYPE 资产流动类型说明
     */
    public String getCurAssetdetType() {
        return curAssetdetType;
    }

    /**
     * 资产流动类型说明
     * @param curAssetdetType 资产流动类型说明
     */
    public void setCurAssetdetType(String curAssetdetType) {
        this.curAssetdetType = curAssetdetType == null ? null : curAssetdetType.trim();
    }

    /**
     * 交易日
     * @return DEALDATE 交易日
     */
    public Date getDealdate() {
        return dealdate;
    }
    
    /**
     * 交易日
     * @return DEALDATE 交易日
     */
    public String getDealdateString() {
        return DateUtil.format(dealdate,"yyyyMMdd");
    }

    /**
     * 交易日
     * @param dealdate 交易日
     */
    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
    }

    /**
     * 交易对手
     * @return TRADE_COUNTER 交易对手
     */
    public String getTradeCounter() {
        return tradeCounter;
    }

    /**
     * 交易对手
     * @param tradeCounter 交易对手
     */
    public void setTradeCounter(String tradeCounter) {
        this.tradeCounter = tradeCounter == null ? null : tradeCounter.trim();
    }

    /**
     * 单位成交价格（全价）
     * @return UNIT_PRICE_FULL 单位成交价格（全价）
     */
    public BigDecimal getUnitPriceFull() {
        return unitPriceFull;
    }

    /**
     * 单位成交价格（全价）
     * @param unitPriceFull 单位成交价格（全价）
     */
    public void setUnitPriceFull(BigDecimal unitPriceFull) {
        this.unitPriceFull = unitPriceFull;
    }

    /**
     * 单位成交价格（净价）
     * @return UNIT_PRICE_NET 单位成交价格（净价）
     */
    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    /**
     * 单位成交价格（净价）
     * @param unitPriceNet 单位成交价格（净价）
     */
    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

    /**
     * 到期收益率
     * @return END_RATE 到期收益率
     */
    public BigDecimal getEndRate() {
        return endRate;
    }

    /**
     * 到期收益率
     * @param endRate 到期收益率
     */
    public void setEndRate(BigDecimal endRate) {
        this.endRate = endRate;
    }

    /**
     * 成交编号/合同号
     * @return CONTRACT_CODE 成交编号/合同号
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * 成交编号/合同号
     * @param contractCode 成交编号/合同号
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
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
     * 通道代码
     * @return CHL_CPCT_ID 通道代码
     */
    public String getChlCpctId() {
        return chlCpctId;
    }

    /**
     * 通道代码
     * @param chlCpctId 通道代码
     */
    public void setChlCpctId(String chlCpctId) {
        this.chlCpctId = chlCpctId == null ? null : chlCpctId.trim();
    }

    /**
     * 交易类型
     * @return TRADE_TYE 交易类型
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 交易类型
     * @param tradeTye 交易类型
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    /**
     * 通道名称
     * @return CHL_AGRT_NAME 通道名称
     */
    public String getChlAgrtName() {
        return chlAgrtName;
    }

    /**
     * 通道名称
     * @param chlAgrtName 通道名称
     */
    public void setChlAgrtName(String chlAgrtName) {
        this.chlAgrtName = chlAgrtName == null ? null : chlAgrtName.trim();
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
     * 费用类型
     * @return FEE_TYPE 费用类型
     */
    public String getFeeType() {
        return feeType;
    }

    /**
     * 费用类型
     * @param feeType 费用类型
     */
    public void setFeeType(String feeType) {
        this.feeType = feeType == null ? null : feeType.trim();
    }

    /**
     * 费用二级类型
     * @return FEE_SECTYPE 费用二级类型
     */
    public String getFeeSectype() {
        return feeSectype;
    }

    /**
     * 费用二级类型
     * @param feeSectype 费用二级类型
     */
    public void setFeeSectype(String feeSectype) {
        this.feeSectype = feeSectype == null ? null : feeSectype.trim();
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

    public String getSumitSeq() {
		return sumitSeq;
	}

	public void setSumitSeq(String sumitSeq) {
		this.sumitSeq = sumitSeq;
	}

	public void setAssetMethodStr(String assetMethodStr) {
		this.assetMethodStr = assetMethodStr;
	}

	public void setRivalPartyTypeStr(String rivalPartyTypeStr) {
		this.rivalPartyTypeStr = rivalPartyTypeStr;
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
     * 删除原因
     * @return DELETE_RES 删除原因
     */
    public String getDeleteRes() {
        return deleteRes;
    }

    /**
     * 删除原因
     * @param deleteRes 删除原因
     */
    public void setDeleteRes(String deleteRes) {
        this.deleteRes = deleteRes == null ? null : deleteRes.trim();
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

	public Date getDealRegistDate() {
		return dealRegistDate;
	}

	public void setDealRegistDate(Date dealRegistDate) {
		this.dealRegistDate = dealRegistDate;
	}

	public String getSendStatusName() {
		return sendStatusName;
	}

	public void setSendStatusName(String sendStatusName) {
		this.sendStatusName = sendStatusName;
	}

	public String getSubmitFeedbackStatusName() {
		return submitFeedbackStatusName;
	}

	public void setSubmitFeedbackStatusName(String submitFeedbackStatusName) {
		this.submitFeedbackStatusName = submitFeedbackStatusName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCcyName() {
		return ccyName;
	}

	public void setCcyName(String ccyName) {
		this.ccyName = ccyName;
	}

	public String getIsAssetSub() {
		return isAssetSub;
	}

	public void setIsAssetSub(String isAssetSub) {
		this.isAssetSub = isAssetSub;
	}

	public String getIsAssetSubName() {
		return isAssetSubName;
	}

	public void setIsAssetSubName(String isAssetSubName) {
		this.isAssetSubName = isAssetSubName;
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

	public String getCurAssetTypeName() {
		return curAssetTypeName;
	}

	public void setCurAssetTypeName(String curAssetTypeName) {
		this.curAssetTypeName = curAssetTypeName;
	}

	public String getRefTradeseqno() {
		return refTradeseqno;
	}

	public void setRefTradeseqno(String refTradeseqno) {
		this.refTradeseqno = refTradeseqno;
	}

	public String getTradeCounterName() {
		return tradeCounterName;
	}

	public void setTradeCounterName(String tradeCounterName) {
		this.tradeCounterName = tradeCounterName;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getDealtype() {
		return dealtype;
	}

	public void setDealtype(String dealtype) {
		this.dealtype = dealtype;
	}

	public String getEndRateString() {
		return endRateString;
	}

	public void setEndRateString(String endRateString) {
		this.endRateString = endRateString;
	}
	/**
     * 取得 资产计量方式
     * @return 资产计量方式
     */
    public String getAssetMethod() {
        return assetMethod;
    }

    /**
     * 设置 资产计量方式
     * @param assetMethod 资产计量方式
     */
    public void setAssetMethod(String assetMethod) {
        this.assetMethod = assetMethod == null ? null : assetMethod.trim();
    }

    /**
     * 取得 交易对手方类型
     * @return 交易对手方类型
     */
    public String getRivalPartyType() {
        return rivalPartyType;
    }

    /**
     * 设置 交易对手方类型
     * @param rivalPartyType 交易对手方类型
     */
    public void setRivalPartyType(String rivalPartyType) {
        this.rivalPartyType = rivalPartyType == null ? null : rivalPartyType.trim();
    }
    private  String  assetMethodStr;
    public String getAssetMethodStr() {
        if (!StringUtil.isEmpty(this.getAssetMethodNum())){
            return this.getAssetMethodNum().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资产计量方式, this.getAssetMethodNum());
        }
        return this.getAssetMethodNum();
	}
    
    private  String   rivalPartyTypeStr;
    public String getRivalPartyTypeStr() {
        if (!StringUtil.isEmpty(this.getRivalPartyTypeNum())){
            return this.getRivalPartyTypeNum().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.对手方类型, this.getRivalPartyTypeNum());
        }
        return this.getRivalPartyTypeNum();
	}

	public String getAssetMethodNum() {
		return assetMethodNum;
	}

	public void setAssetMethodNum(String assetMethodNum) {
		this.assetMethodNum = assetMethodNum;
	}

	public String getRivalPartyTypeNum() {
		return rivalPartyTypeNum;
	}

	public void setRivalPartyTypeNum(String rivalPartyTypeNum) {
		this.rivalPartyTypeNum = rivalPartyTypeNum;
	}

	public String getProdRegistCode() {
		return prodRegistCode;
	}

	public void setProdRegistCode(String prodRegistCode) {
		this.prodRegistCode = prodRegistCode;
	}


	private String dealdateStr;

    public String getDealdateStr() {
    	if(StringUtil.isEmpty(dealdateStr)&&StringUtil.isNotEmpty(dealdate)){
    		return DateUtil.format(dealdate,"yyyyMMdd");
    	}
        return dealdateStr;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getRelatedTradeStatus() {
        return relatedTradeStatus;
    }

    public void setRelatedTradeStatus(String relatedTradeStatus) {
        this.relatedTradeStatus = relatedTradeStatus;
    }
    public String getRelatedTradeStatusStr() {
        if (StringUtil.isNotBlank(relatedTradeStatus)){
            return relatedTradeStatus.trim() +" "+SysDataUtil.getNameFromDataDict(DictCode.关联交易情况, relatedTradeStatus);
        }
        return relatedTradeStatus;
    }

    public String getTradeTimeStr() {
        return DateUtil.format(this.getTradeTime(), "yyyyMMddHHmmss");
    }
    public void setDealdateStr(String dealdateStr) {
        this.dealdateStr = dealdateStr;
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