/*
 * 文件名：EtrExchangeTradeDto001.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.entrust.etrexchangetrade.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.tams.common.base.entity.EtrEntrust;
import com.joyintech.tams.common.base.entity.SysUploadFileInfo;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;

/**
 *  场内交易指令画面dto
 * @author chendijun
 * @version 1.0 2020年7月2日
 * @see EtrExchangeTrade001Dto
 * @since 1.0
 */
public class EtrExchangeTrade001Dto extends EtrEntrust{

	
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 操作类型
     */
    private String operateType;
    
    /**
     * 按钮id
     */
    private String elementId;
    /**
     * 资产名称
     */
    private String finprodName;
    
    /**
     * 全价价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal fprice;
    
    /**
     * 收益率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal yield;
    
    /**
     * 席位号
     */
    private String seatId;
    
    /**
     * 约定号
     */
    private String agreeOnId;
    
    /**
     * 对手方交易商
     */
    private String counterOrg;
    
    /**
     * 本方交易员
     */
    private String trader;

    /**
     * 回购期限
     */
    private String term;
    
    /**
     * 证券账户
     */
    private String secAcctId;
    
    /**
     * 组合资金账户
     */
    private String acctNo;

    /**
     * 委托状态
     */
    private String recStatus;
    
    /**
     * 指令业务类型
     */
    private String entrustBussType;
    
    /**
     * 指令价格
     */
    private BigDecimal entrustPrice; 
    
    /**
     * 计算方式
     */
    private String calculateField;
    
    /**
     * 涨停
     */
    private BigDecimal limitUp; 
    
    /**
     * 跌停
     */
    private BigDecimal limitDown; 
    
    /**
     * 资产类型
     */
    private String assetsType;
    
    /**
     * 操作类型
     */
    private String pageModel;
    
    /**
     * 记录操作方式
     */
    private String optType;
    
    /**
     * 目录类型
     */
    private String targetType;
    
    /**
     * 申报类型
     */
    private String declaType;
    
    /**
     * 可转股数量
     */
    private BigDecimal toShareAvailableNum; 
    
    /**
     * 清算速度
     */
    private String settleSpeed;

    /**
     * 是否多组合
     */
    private String isMulti;
    /**
     * 取得 委托代码
     * @return 委托代码
     */
    @Override
    @PrimaryKey(value = Field.PK_1)
    public String getEntrustId() {
        return super.getEntrustId();
    }
    /**
     * 获取 fprice
     * @return fprice.
     */
    public BigDecimal getFprice() {
        return fprice;
    }

    
    /**
     * 设置 fprice
     * @param fprice fprice
     */
    public void setFprice(BigDecimal fprice) {
        this.fprice = fprice;
    }

    /**
     * 获取 finprodName
     * @return finprodName.
     */
    public String getFinprodName() {
        return finprodName;
    }

    /**
     * 设置 finprodName
     * @param finprodName finprodName
     */
    public void setFinprodName(String finprodName) {
        this.finprodName = finprodName;
    }


    
    /**
     * 获取 yield
     * @return yield.
     */
    public BigDecimal getYield() {
        return yield;
    }


    
    /**
     * 设置 yield
     * @param yield yield
     */
    public void setYield(BigDecimal yield) {
        this.yield = yield;
    }


    /**
     * 获取 operateType
     * @return operateType.
     */
    public String getOperateType() {
        return operateType;
    }


    /**
     * 设置 operateType
     * @param operateType operateType
     */
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }


    /**
     * 获取 elementId
     * @return elementId.
     */
    public String getElementId() {
        return elementId;
    }


    /**
     * 设置 elementId
     * @param elementId elementId
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }


    /**
     * 获取 seatId
     * @return seatId.
     */
    public String getSeatId() {
        return seatId;
    }


    /**
     * 设置 seatId
     * @param seatId seatId
     */
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }


    /**
     * 获取 agreeOnId
     * @return agreeOnId.
     */
    public String getAgreeOnId() {
        return agreeOnId;
    }


    /**
     * 设置 agreeOnId
     * @param agreeOnId agreeOnId
     */
    public void setAgreeOnId(String agreeOnId) {
        this.agreeOnId = agreeOnId;
    }


    /**
     * 获取 term
     * @return term.
     */
    public String getTerm() {
        return term;
    }


    /**
     * 设置 term
     * @param term term
     */
    public void setTerm(String term) {
        this.term = term;
    }


	public String getSecAcctId() {
		return secAcctId;
	}


	public void setSecAcctId(String secAcctId) {
		this.secAcctId = secAcctId;
	}
	public String getRecStatus() {
		return recStatus;
	}
	public void setRecStatus(String recStatus) {
		this.recStatus = recStatus;
	}
	public String getEntrustBussType() {
		return entrustBussType;
	}
	public void setEntrustBussType(String entrustBussType) {
		this.entrustBussType = entrustBussType;
	}
	public BigDecimal getEntrustPrice() {
		return entrustPrice;
	}
	public void setEntrustPrice(BigDecimal entrustPrice) {
		this.entrustPrice = entrustPrice;
	}
	public String getCalculateField() {
		return calculateField;
	}
	public void setCalculateField(String calculateField) {
		this.calculateField = calculateField;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public BigDecimal getLimitUp() {
		return limitUp;
	}
	public void setLimitUp(BigDecimal limitUp) {
		this.limitUp = limitUp;
	}
	public BigDecimal getLimitDown() {
		return limitDown;
	}
	public void setLimitDown(BigDecimal limitDown) {
		this.limitDown = limitDown;
	}
	public String getAssetsType() {
		return assetsType;
	}
	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}
	public String getPageModel() {
		return pageModel;
	}
	public void setPageModel(String pageModel) {
		this.pageModel = pageModel;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getCounterOrg() {
		return counterOrg;
	}
	public void setCounterOrg(String counterOrg) {
		this.counterOrg = counterOrg;
	}
	public String getTrader() {
		return trader;
	}
	public void setTrader(String trader) {
		this.trader = trader;
	}
	public String getDeclaType() {
		return declaType;
	}
	public void setDeclaType(String declaType) {
		this.declaType = declaType;
	}
	public BigDecimal getToShareAvailableNum() {
		return toShareAvailableNum;
	}
	public void setToShareAvailableNum(BigDecimal toShareAvailableNum) {
		this.toShareAvailableNum = toShareAvailableNum;
	}
	public String getSettleSpeed() {
		return settleSpeed;
	}
	public void setSettleSpeed(String settleSpeed) {
		this.settleSpeed = settleSpeed;
	}

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }
}
