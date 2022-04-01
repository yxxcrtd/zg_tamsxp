/*
 * 文件名：ChbInvestTradeSubDto.java
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
 * 资产交易登记
 * @description 
 * @author 王波
 * @version 1.0 2020年9月24日
 * @see ChbInvestTradeSubDto
 * @since 1.0
 */
public class ChbInvestTradeSubDto {
    /**
     * 登记序号
     */
    private String registSeqno;
	/**
     * 产品代码
     */
    private String prodId;

    /**
     * 行内交易编码
     */
    private String tradeCode;

    /**
     * 发行机构代码
     */
    private String registBankCode;

    /**
     * 行内资产/负债编码
     */
    private String assetCode;

    /**
     * 币种 参考数据字典：CURRENCY_CHB
     */
    private String ccy;

    /**
     * 折算人民币金额
     */
    private BigDecimal cnyAmt;

    /**
     * 发生金额
     */
    private BigDecimal amt;

    /**
     * 资金流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     */
    private String curAssetType;

    /**
     * 资金流动类型说明
     */
    private String curAssetdetType;

    /**
     * 交易日
     */
    private Date dealdate;

    /**
     * 交易对手
     */
    private String tradeCounter;

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
    @RateFormat
    private BigDecimal endRate;

    /**
     * 成交编号/合同号
     */
    private String contractCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 交易登记日
     */
    private Date dealRegistDate;

    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 资产计量方式
     */
    private String assetMethod;

    /**
     * 交易对手方类型
     */
    private String rivalPartyType;

    /**
     * 登记编码
     */
    private String prodRegistCode;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;
    /**
     * 创建人员id
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
     * 更新人id
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 数据日期
     */
    private Date rowDate;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 反馈状态
     */
    private String submitFeedbackStatus;



    /***1217升级**/

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
     * 取得 行内交易编码
     * @return 行内交易编码
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * 设置 行内交易编码
     * @param tradeCode 行内交易编码
     */
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    /**
     * 取得 发行机构代码
     * @return 发行机构代码
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 设置 发行机构代码
     * @param registBankCode 发行机构代码
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
    }

    /**
     * 取得 行内资产/负债编码
     * @return 行内资产/负债编码
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 设置 行内资产/负债编码
     * @param assetCode 行内资产/负债编码
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 取得 币种 参考数据字典：CURRENCY_CHB
     * @return 币种 参考数据字典：CURRENCY_CHB
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 设置 币种 参考数据字典：CURRENCY_CHB
     * @param ccy 币种 参考数据字典：CURRENCY_CHB
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 取得 折算人民币金额
     * @return 折算人民币金额
     */
    public BigDecimal getCnyAmt() {
        return cnyAmt;
    }

    /**
     * 设置 折算人民币金额
     * @param cnyAmt 折算人民币金额
     */
    public void setCnyAmt(BigDecimal cnyAmt) {
        this.cnyAmt = cnyAmt;
    }

    /**
     * 取得 发生金额
     * @return 发生金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 设置 发生金额
     * @param amt 发生金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 取得 资金流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     * @return 资金流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     */
    public String getCurAssetType() {
        return curAssetType;
    }

    /**
     * 设置 资金流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     * @param curAssetType 资金流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     */
    public void setCurAssetType(String curAssetType) {
        this.curAssetType = curAssetType == null ? null : curAssetType.trim();
    }

    /**
     * 取得 资金流动类型说明
     * @return 资金流动类型说明
     */
    public String getCurAssetdetType() {
        return curAssetdetType;
    }

    /**
     * 设置 资金流动类型说明
     * @param curAssetdetType 资金流动类型说明
     */
    public void setCurAssetdetType(String curAssetdetType) {
        this.curAssetdetType = curAssetdetType == null ? null : curAssetdetType.trim();
    }

    /**
     * 取得 交易日
     * @return 交易日
     */
    public Date getDealdate() {
        return dealdate;
    }

    /**
     * 设置 交易日
     * @param dealdate 交易日
     */
    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
    }

    /**
     * 取得 交易对手
     * @return 交易对手
     */
    public String getTradeCounter() {
        return tradeCounter;
    }

    /**
     * 设置 交易对手
     * @param tradeCounter 交易对手
     */
    public void setTradeCounter(String tradeCounter) {
        this.tradeCounter = tradeCounter == null ? null : tradeCounter.trim();
    }

    /**
     * 取得 单位成交价格（全价）
     * @return 单位成交价格（全价）
     */
    public BigDecimal getUnitPriceFull() {
        return unitPriceFull;
    }

    /**
     * 设置 单位成交价格（全价）
     * @param unitPriceFull 单位成交价格（全价）
     */
    public void setUnitPriceFull(BigDecimal unitPriceFull) {
        this.unitPriceFull = unitPriceFull;
    }

    /**
     * 取得 单位成交价格（净价）
     * @return 单位成交价格（净价）
     */
    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    /**
     * 设置 单位成交价格（净价）
     * @param unitPriceNet 单位成交价格（净价）
     */
    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

    /**
     * 取得 到期收益率
     * @return 到期收益率
     */
    public BigDecimal getEndRate() {
        return endRate;
    }

    /**
     * 设置 到期收益率
     * @param endRate 到期收益率
     */
    public void setEndRate(BigDecimal endRate) {
        this.endRate = endRate;
    }

    /**
     * 取得 成交编号/合同号
     * @return 成交编号/合同号
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * 设置 成交编号/合同号
     * @param contractCode 成交编号/合同号
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
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
     * 取得 交易登记日
     * @return 交易登记日
     */
    public Date getDealRegistDate() {
        return dealRegistDate;
    }

    /**
     * 设置 交易登记日
     * @param dealRegistDate 交易登记日
     */
    public void setDealRegistDate(Date dealRegistDate) {
        this.dealRegistDate = dealRegistDate;
    }

    /**
     * 取得 数量
     * @return 数量
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * 设置 数量
     * @param num 数量
     */
    public void setNum(BigDecimal num) {
        this.num = num;
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

    /**
     * 取得 登记编码
     * @return 登记编码
     */
    public String getProdRegistCode() {
        return prodRegistCode;
    }

    /**
     * 设置 登记编码
     * @param prodRegistCode 登记编码
     */
    public void setProdRegistCode(String prodRegistCode) {
        this.prodRegistCode = prodRegistCode == null ? null : prodRegistCode.trim();
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
     * 取得 数据日期
     * @return 数据日期
     */
    public Date getRowDate() {
        return rowDate;
    }

    /**
     * 设置 数据日期
     * @param rowDate 数据日期
     */
    public void setRowDate(Date rowDate) {
        this.rowDate = rowDate;
    }

    /**
     * 取得 数据来源
     * @return 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 设置 数据来源
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
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

	public String getRegistSeqno() {
		return registSeqno;
	}

	public void setRegistSeqno(String registSeqno) {
		this.registSeqno = registSeqno;
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
}
