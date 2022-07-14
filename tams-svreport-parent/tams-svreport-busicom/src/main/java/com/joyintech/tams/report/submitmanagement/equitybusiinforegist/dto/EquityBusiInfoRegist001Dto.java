package com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
@TableName("CHB_EQUDET_SUB")
public class EquityBusiInfoRegist001Dto extends BaseEntity {
	  /**
     * 业务流水号
     */
	@BusinessKey("BUSI_SEQNO")
    private String busiSeqno;

    /**
     * 产品登记编码
     */
	@BusinessKey("REG_CODE")
    private String regCode;

    /**
     * 登记银行代码（即发行机构代码）
     */
	@BusinessKey("REGIST_BANK_CODE")
    private String registBankCode;

    /**
     * 识别标识
     */
	@BusinessKey("INDENTIFY_MARK")
    private String indentifyMark;

    /**
     * 报送状态
     */
    private String submitStatus;
    /**
     * 报送状态名
     */
    private String submitStatusName;
    
    /**
     * 数据状态名
     */
    private String statusName;
    /**
     * 经办人
     */
    private String agent;
    /**
     * 是否报送
     */
    private String isSubmit;
    /**
     * 合并前识别标识
     */
    private String orgIndentifyMark;
    
    /**
     * 取得 业务流水号
     * @return 业务流水号
     */
    public String getBusiSeqno() {
        return busiSeqno;
    }

    /**
     * 设置 业务流水号
     * @param busiSeqno 业务流水号
     */
    public void setBusiSeqno(String busiSeqno) {
        this.busiSeqno = busiSeqno == null ? null : busiSeqno.trim();
    }

    /**
     * 取得 产品登记编码
     * @return 产品登记编码
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * 设置 产品登记编码
     * @param regCode 产品登记编码
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
    }

    /**
     * 取得 登记银行代码（即发行机构代码）
     * @return 登记银行代码（即发行机构代码）
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 设置 登记银行代码（即发行机构代码）
     * @param registBankCode 登记银行代码（即发行机构代码）
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
    }

    /**
     * 取得 识别标识
     * @return 识别标识
     */
    public String getIndentifyMark() {
        return indentifyMark;
    }

    /**
     * 设置 识别标识
     * @param indentifyMark 识别标识
     */
    public void setIndentifyMark(String indentifyMark) {
        this.indentifyMark = indentifyMark == null ? null : indentifyMark.trim();
    }

    /**
     * 取得 报送状态
     * @return 报送状态
     */
    public String getSubmitStatus() {
        return submitStatus;
    }
    
    public String getSubmitStatusStr() {
        return submitStatus==null?null:SysDataUtil.getNameFromDataDict(DictCode.报送状态, this.submitStatus);
    }

    /**
     * 设置 报送状态
     * @param submitStatus 报送状态
     */
    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus == null ? null : submitStatus.trim();
    }
    /**
     * 产品代码
     */
    private String account;

    /**
     * 业务确认日期
     */
    private Date equityConfDate;
    
    /**
     * 业务确认日期str
     */
    private String equityConfDateStr;

    /**
     * 业务确认时间
     */
    private String shareConfDatetime;

    /**
     * 关联账号开户所在地
     */
    @DictFormat(DictCode.销售区域_中债)
    private String fundAccPlace;

    /**
     * 业务种类
     */
    @DictFormat(DictCode.业务种类)
    private String busiType;

    /**
     * 币种
     */
    @DictFormat(DictCode.币种_中债)
    private String ccy;

    /**
     * 金额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal amount;

    /**
     * 折算人民币金额（元）
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal amountCny;

    /**
     * 份额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal shares;

    /**
     * 渠道
     */
    @DictFormat(DictCode.投资者明细渠道)
    private String channel;

    /**
     * 关联活期存款账号
     */
    private String fundAcc;

    /**
     * 是否代销
     */
    @DictFormat(DictCode.是否_中债)
    private String isConsign;

    /**
     * 销售合同号
     */
    private String contractNo;

    /**
     * 理财账号
     */
    private String finAcc;

    /**
     * 客户统一编号
     */
    private String custContractNo;

    /**
     * 客户姓名
     */
    private String custName;

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
    @DictFormat(DictCode.监管机构)
    private String agencyFsa;

    /**
     * 业务发生地所属监管
     */
    @DictFormat(DictCode.监管机构2)
    private String busiFsa;
    /**
     * 接入检查结果
     */
    private String interfCheckResult;

    /**
     * 确认净值
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal prodValue;

    /**
     * 费用
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal feeAmt;

    /**
     * 交易柜员号
     */
    private String tranTellName;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 报送日期
     */
    private Date submitTime;
    
    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 报送截止日
     */
    private Date submitDeadline;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 反馈状态
     */
    @DictFormat(DictCode.报送反馈状态)
    private String submitFeedbackStatus;
    
    /**
     * 检查序列号
     */
    private String checkSeqno;
    /**
     * 数据来源
     */
    private String dataSource;
    
    private Map<String,String> tempMap;
    /**
     * 持有金额 导出中债excel用
     */
    private String amountExportExcel;

    /**
     * 持有份额 导出中债excel用
     */
    private String sharesExportExcel;

    /**
     * 转换人民币金额 导出中债excel用
     */
    private String amountCnyExportExcel;
    private String prodValueExportExcel;
    private String feeAmtExportExcel;
    /**
     * 删除原因
     */
    private String deleteReason;

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
    /**
     * 取得 产品代码
     * @return 产品代码
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置 产品代码
     * @param account 产品代码
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 取得 业务确认日期
     * @return 业务确认日期
     */
    public Date getEquityConfDate() {
        return equityConfDate;
    }
    
    public String getEquityConfDateStr() {
        return equityConfDateStr;
    }

    /**
     * 设置 业务确认日期
     * @param equityConfDate 业务确认日期
     */
    public void setEquityConfDate(Date equityConfDate) {
        this.equityConfDate = equityConfDate;
    }

    /**
     * 取得 业务确认时间
     * @return 业务确认时间
     */
    public String getShareConfDatetime() {
        return shareConfDatetime;
    }

    /**
     * 设置 业务确认时间
     * @param shareConfDatetime 业务确认时间
     */
    public void setShareConfDatetime(String shareConfDatetime) {
        this.shareConfDatetime = shareConfDatetime;
    }

    /**
     * 取得 关联账号开户所在地
     * @return 关联账号开户所在地
     */
    public String getFundAccPlace() {
        return fundAccPlace;
    }

    /**
     * 设置 关联账号开户所在地
     * @param fundAccPlace 关联账号开户所在地
     */
    public void setFundAccPlace(String fundAccPlace) {
        this.fundAccPlace = fundAccPlace == null ? null : fundAccPlace.trim();
    }

    /**
     * 取得 业务种类
     * @return 业务种类
     */
    public String getBusiType() {
        return busiType;
    }
    public String getBusiTypeStr() {
        return busiType==null?null:SvrDataUtil.getNameFromDataDict(DictCode.业务种类, this.busiType);
    }

    /**
     * 设置 业务种类
     * @param busiType 业务种类
     */
    public void setBusiType(String busiType) {
        this.busiType = busiType == null ? null : busiType.trim();
    }

    /**
     * 取得 币种
     * @return 币种
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 设置 币种
     * @param ccy 币种
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 取得 金额
     * @return 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置 金额
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 取得 折算人民币金额（元）
     * @return 折算人民币金额（元）
     */
    public BigDecimal getAmountCny() {
        return amountCny;
    }

    /**
     * 设置 折算人民币金额（元）
     * @param amountCny 折算人民币金额（元）
     */
    public void setAmountCny(BigDecimal amountCny) {
        this.amountCny = amountCny;
    }

    /**
     * 取得 份额
     * @return 份额
     */
    public BigDecimal getShares() {
        return shares;
    }

    /**
     * 设置 份额
     * @param shares 份额
     */
    public void setShares(BigDecimal shares) {
        this.shares = shares;
    }

    /**
     * 取得 渠道
     * @return 渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * 设置 渠道
     * @param channel 渠道
     */
    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    /**
     * 取得 关联活期存款账号
     * @return 关联活期存款账号
     */
    public String getFundAcc() {
        return fundAcc;
    }

    /**
     * 设置 关联活期存款账号
     * @param fundAcc 关联活期存款账号
     */
    public void setFundAcc(String fundAcc) {
        this.fundAcc = fundAcc == null ? null : fundAcc.trim();
    }

    /**
     * 取得 是否代销
     * @return 是否代销
     */
    public String getIsConsign() {
        return isConsign;
    }

    /**
     * 设置 是否代销
     * @param isConsign 是否代销
     */
    public void setIsConsign(String isConsign) {
        this.isConsign = isConsign == null ? null : isConsign.trim();
    }

    /**
     * 取得 销售合同号
     * @return 销售合同号
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置 销售合同号
     * @param contractNo 销售合同号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    /**
     * 取得 理财账号
     * @return 理财账号
     */
    public String getFinAcc() {
        return finAcc;
    }

    /**
     * 设置 理财账号
     * @param finAcc 理财账号
     */
    public void setFinAcc(String finAcc) {
        this.finAcc = finAcc == null ? null : finAcc.trim();
    }

    /**
     * 取得 客户统一编号
     * @return 客户统一编号
     */
    public String getCustContractNo() {
        return custContractNo;
    }

    /**
     * 设置 客户统一编号
     * @param custContractNo 客户统一编号
     */
    public void setCustContractNo(String custContractNo) {
        this.custContractNo = custContractNo == null ? null : custContractNo.trim();
    }

    /**
     * 取得 客户姓名
     * @return 客户姓名
     */
    public String getCustName() {
        return custName;
    }

    /**
     * 设置 客户姓名
     * @param custName 客户姓名
     */
    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    /**
     * 取得 代销机构代码
     * @return 代销机构代码
     */
    public String getAgencyCode() {
        return agencyCode;
    }

    /**
     * 设置 代销机构代码
     * @param agencyCode 代销机构代码
     */
    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode == null ? null : agencyCode.trim();
    }

    /**
     * 取得 代销机构名称
     * @return 代销机构名称
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * 设置 代销机构名称
     * @param agencyName 代销机构名称
     */
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName == null ? null : agencyName.trim();
    }

    /**
     * 取得 代销机构所属监管机构
     * @return 代销机构所属监管机构
     */
    public String getAgencyFsa() {
        return agencyFsa;
    }

    /**
     * 设置 代销机构所属监管机构
     * @param agencyFsa 代销机构所属监管机构
     */
    public void setAgencyFsa(String agencyFsa) {
        this.agencyFsa = agencyFsa == null ? null : agencyFsa.trim();
    }

    /**
     * 取得 业务发生地所属监管
     * @return 业务发生地所属监管
     */
    public String getBusiFsa() {
        return busiFsa;
    }

    /**
     * 设置 业务发生地所属监管
     * @param busiFsa 业务发生地所属监管
     */
    public void setBusiFsa(String busiFsa) {
        this.busiFsa = busiFsa == null ? null : busiFsa.trim();
    }

    /**
     * 取得 确认净值
     * @return 确认净值
     */
    public BigDecimal getProdValue() {
        return prodValue;
    }

    /**
     * 设置 确认净值
     * @param prodValue 确认净值
     */
    public void setProdValue(BigDecimal prodValue) {
        this.prodValue = prodValue;
    }

    /**
     * 取得 费用
     * @return 费用
     */
    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    /**
     * 设置 费用
     * @param feeAmt 费用
     */
    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    /**
     * 取得 交易柜员号
     * @return 交易柜员号
     */
    public String getTranTellName() {
        return tranTellName;
    }

    /**
     * 设置 交易柜员号
     * @param tranTellName 交易柜员号
     */
    public void setTranTellName(String tranTellName) {
        this.tranTellName = tranTellName == null ? null : tranTellName.trim();
    }

    /**
     * 取得 数据类型
     * @return 数据类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置 数据类型
     * @param type 数据类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 取得 报送日期
     * @return 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }
    public String getSubmitTimeStr() {
        return submitTime==null?null:DateUtil.format(submitTime);
    }
    /**
     * 设置 报送日期
     * @param submitTime 报送日期
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    
    /**
     * 取得 数据日期
     * @return 报送日期
     */
    public Date getStatusDate() {
        return statusDate;
    }
    public String getStatusDateStr() {
        return statusDate==null?null:DateUtil.format(statusDate);
    }
    /**
     * 设置 数据日期
     * @param statuDate 数据日期
     */
    public void setStatusDate(Date statuDate) {
        this.statusDate = statuDate;
    }
    

   
    /**
     * 取得 报送截止日
     * @return 报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 设置 报送截止日
     * @param submitDeadline 报送截止日
     */
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    /**
     * 取得 中债报送截止日
     * @return 中债报送截止日
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }
    public String getChbSubmitDeadlineStr() {
        return chbSubmitDeadline==null?null:DateUtil.format(chbSubmitDeadline);
    }

    /**
     * 设置 中债报送截止日
     * @param chbSubmitDeadline 中债报送截止日
     */
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    /**
     * 取得 报送流水号
     * @return 报送流水号
     */
    public String getSumitSeq() {
        return sumitSeq;
    }

    /**
     * 设置 报送流水号
     * @param sumitSeq 报送流水号
     */
    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq == null ? null : sumitSeq.trim();
    }

    /**
     * 取得 部门代码
     * @return 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 取得 机构代码
     * @return 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

	public String getSubmitFeedbackStatus() {
		return submitFeedbackStatus;
	}
	public String getSubmitFeedbackStatusStr() {
		return submitFeedbackStatus==null?null:SysDataUtil.getNameFromDataDict(DictCode.报送反馈状态, this.submitFeedbackStatus);
	}

	public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
		this.submitFeedbackStatus = submitFeedbackStatus== null ? null : submitFeedbackStatus.trim();
	}

	public String getCheckSeqno() {
		return checkSeqno;
	}

	public void setCheckSeqno(String checkSeqno) {
		this.checkSeqno = checkSeqno == null ? null : checkSeqno.trim();
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSubmitStatusName() {
		return submitStatusName;
	}

	public void setSubmitStatusName(String submitStatusName) {
		this.submitStatusName = submitStatusName;
	}

    
    public String getDataSource() {
        return dataSource;
    }

    
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    
    public String getInterfCheckResult() {
        return interfCheckResult;
    }

    
    public void setInterfCheckResult(String interfCheckResult) {
        this.interfCheckResult = interfCheckResult;
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

    
    public String getOrgIndentifyMark() {
        return orgIndentifyMark;
    }

    
    public void setOrgIndentifyMark(String orgIndentifyMark) {
        this.orgIndentifyMark = orgIndentifyMark;
    }

	public void setEquityConfDateStr(String equityConfDateStr) {
		this.equityConfDateStr = equityConfDateStr;
	}

	public Map<String, String> getTempMap() {
		return tempMap;
	}

	public void setTempMap(Map<String, String> tempMap) {
		this.tempMap = tempMap;
	}

	public String getAmountExportExcel() {
		return amountExportExcel;
	}

	public void setAmountExportExcel(String amountExportExcel) {
		this.amountExportExcel = amountExportExcel;
	}

	public String getSharesExportExcel() {
		return sharesExportExcel;
	}

	public void setSharesExportExcel(String sharesExportExcel) {
		this.sharesExportExcel = sharesExportExcel;
	}

	public String getAmountCnyExportExcel() {
		return amountCnyExportExcel;
	}

	public void setAmountCnyExportExcel(String amountCnyExportExcel) {
		this.amountCnyExportExcel = amountCnyExportExcel;
	}

	public String getProdValueExportExcel() {
		return prodValueExportExcel;
	}

	public void setProdValueExportExcel(String prodValueExportExcel) {
		this.prodValueExportExcel = prodValueExportExcel;
	}

	public String getFeeAmtExportExcel() {
		return feeAmtExportExcel;
	}

	public void setFeeAmtExportExcel(String feeAmtExportExcel) {
		this.feeAmtExportExcel = feeAmtExportExcel;
	}

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
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