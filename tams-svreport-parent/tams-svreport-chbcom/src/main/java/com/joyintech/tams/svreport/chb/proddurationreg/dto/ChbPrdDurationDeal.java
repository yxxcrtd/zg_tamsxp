/*
 * 文件名：PrdDealProdsEntity.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：Administrator
 * 修改时间：2017年12月22日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */
 
package com.joyintech.tams.svreport.chb.proddurationreg.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.joyintech.tams.common.base.entity.ChbPrdDeal;
import com.joyintech.tams.common.base.entity.ChbPrdFeeDeal;



/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2017年12月22日
 * @see ChbPrdDurationDeal
 * @since 1.0
 */
public class ChbPrdDurationDeal {
    /**
     * 产品登记编码
     */
    private String regCode;
    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 产品代码
     */
    private String prodId;
    /**
     * 父产品代码
     */
    private String fprodId;
    /**
     * 业务开始日
     */
    private Date openStart;
    /**
     * 业务结束日
     */
    private Date openEnd;
    /**
     * 业务周期起息日
     */
    private Date openVdate;
    /**
     * 产品起息日
     */
    private Date vdate;
    /**
     * 业务结束日1
     */
    private Date openEnd1;
    /**
     * 产品运作模式
     */
    private String operateMode;
    /**
     * 变动类型
     */
    private String changeType;
    /**
     * 登记银行代码
     */
    private String registBankCode;
    /**
     * 交易list
     */
    private List<ChbPrdDeal> dealList;
    /**
     * 费用list
     */
    private List<ChbPrdFeeDeal> feeList;
    /**
     * 募集币种
     */
    private String raiseCcy;
    /**
     * 交易本金
     */
    private BigDecimal tradePrince;
    /**
     * 交易金额
     */
    private BigDecimal tradeQty;
    /**
     * 上条开放周几起息日
     */
    private Date openVdateLast;
    /**
     * 已报送流水号
     */
    private String regSeq;
    /**
     * 未报送流水号
     */
    private String regSeq1;
    /**
     * 数据状态
     */
    private String status;
    /**
     * 币种
     */
    private String ccy;
    /**
     * 跑批日
     */
    private Date batchDate;
    /**
     * 获取 orgCode
     * @return orgCode.
     */
    public String getOrgCode() {
        return orgCode;
    }
    
    /**
     * 设置 orgCode
     * @param orgCode orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    /**
     * 获取 prodId
     * @return prodId.
     */
    public String getProdId() {
        return prodId;
    }
    
    /**
     * 设置 prodId
     * @param prodId prodId
     */
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    
    
    public String getFprodId() {
		return fprodId;
	}

	public void setFprodId(String fprodId) {
		this.fprodId = fprodId;
	}

	/**
     * 获取 openStart
     * @return openStart.
     */
    public Date getOpenStart() {
        return openStart;
    }

    
    /**
     * 设置 openStart
     * @param openStart openStart
     */
    public void setOpenStart(Date openStart) {
        this.openStart = openStart;
    }

    
    /**
     * 获取 openEnd
     * @return openEnd.
     */
    public Date getOpenEnd() {
        return openEnd;
    }

    
    /**
     * 设置 openEnd
     * @param openEnd openEnd
     */
    public void setOpenEnd(Date openEnd) {
        this.openEnd = openEnd;
    }

    
    /**
     * 获取 openVdate
     * @return openVdate.
     */
    public Date getOpenVdate() {
        return openVdate;
    }

    
    /**
     * 设置 openVdate
     * @param openVdate openVdate
     */
    public void setOpenVdate(Date openVdate) {
        this.openVdate = openVdate;
    }

    
    /**
     * 获取 vdate
     * @return vdate.
     */
    public Date getVdate() {
        return vdate;
    }

    
    /**
     * 设置 vdate
     * @param vdate vdate
     */
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    
    /**
     * 获取 operateMode
     * @return operateMode.
     */
    public String getOperateMode() {
        return operateMode;
    }

    
    /**
     * 设置 operateMode
     * @param operateMode operateMode
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    /**
     * 获取 registBankCode
     * @return registBankCode.
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    
    /**
     * 设置 registBankCode
     * @param registBankCode registBankCode
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode;
    }

    
    /**
     * 获取 dealList
     * @return dealList.
     */
    public List<ChbPrdDeal> getDealList() {
        return dealList;
    }

    
    /**
     * 设置 dealList
     * @param dealList dealList
     */
    public void setDealList(List<ChbPrdDeal> dealList) {
        this.dealList = dealList;
    }

    
    /**
     * 获取 feeList
     * @return feeList.
     */
    public List<ChbPrdFeeDeal> getFeeList() {
        return feeList;
    }

    
    /**
     * 设置 feeList
     * @param feeList feeList
     */
    public void setFeeList(List<ChbPrdFeeDeal> feeList) {
        this.feeList = feeList;
    }

    
    /**
     * 获取 openVdateLast
     * @return openVdateLast.
     */
    public Date getOpenVdateLast() {
        return openVdateLast;
    }

    
    /**
     * 设置 openVdateLast
     * @param openVdateLast openVdateLast
     */
    public void setOpenVdateLast(Date openVdateLast) {
        this.openVdateLast = openVdateLast;
    }

    
    /**
     * 获取 regSeq
     * @return regSeq.
     */
    public String getRegSeq() {
        return regSeq;
    }

    
    /**
     * 设置 regSeq
     * @param regSeq regSeq
     */
    public void setRegSeq(String regSeq) {
        this.regSeq = regSeq;
    }

    
    /**
     * 获取 regSeq1
     * @return regSeq1.
     */
    public String getRegSeq1() {
        return regSeq1;
    }

    
    /**
     * 设置 regSeq1
     * @param regSeq1 regSeq1
     */
    public void setRegSeq1(String regSeq1) {
        this.regSeq1 = regSeq1;
    }

    
    /**
     * 获取 raiseCcy
     * @return raiseCcy.
     */
    public String getRaiseCcy() {
        return raiseCcy;
    }

    
    /**
     * 设置 raiseCcy
     * @param raiseCcy raiseCcy
     */
    public void setRaiseCcy(String raiseCcy) {
        this.raiseCcy = raiseCcy;
    }

    
    /**
     * 获取 tradePrince
     * @return tradePrince.
     */
    public BigDecimal getTradePrince() {
        return tradePrince;
    }

    
    /**
     * 设置 tradePrince
     * @param tradePrince tradePrince
     */
    public void setTradePrince(BigDecimal tradePrince) {
        this.tradePrince = tradePrince;
    }

    
    /**
     * 获取 tradeQty
     * @return tradeQty.
     */
    public BigDecimal getTradeQty() {
        return tradeQty;
    }

    
    /**
     * 设置 tradeQty
     * @param tradeQty tradeQty
     */
    public void setTradeQty(BigDecimal tradeQty) {
        this.tradeQty = tradeQty;
    }

    
    /**
     * 获取 status
     * @return status.
     */
    public String getStatus() {
        return status;
    }

    
    /**
     * 设置 status
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    
    /**
     * 获取 ccy
     * @return ccy.
     */
    public String getCcy() {
        return ccy;
    }

    
    /**
     * 设置 ccy
     * @param ccy ccy
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    
    /**
     * 获取 regCode
     * @return regCode.
     */
    public String getRegCode() {
        return regCode;
    }

    
    /**
     * 设置 regCode
     * @param regCode regCode
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }

    
    /**
     * 获取 openEnd1
     * @return openEnd1.
     */
    public Date getOpenEnd1() {
        return openEnd1;
    }

    
    /**
     * 设置 openEnd1
     * @param openEnd1 openEnd1
     */
    public void setOpenEnd1(Date openEnd1) {
        this.openEnd1 = openEnd1;
    }

    
    /**
     * 获取 batchDate
     * @return batchDate.
     */
    public Date getBatchDate() {
        return batchDate;
    }

    
    /**
     * 设置 batchDate
     * @param batchDate batchDate
     */
    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
    }
    
}
