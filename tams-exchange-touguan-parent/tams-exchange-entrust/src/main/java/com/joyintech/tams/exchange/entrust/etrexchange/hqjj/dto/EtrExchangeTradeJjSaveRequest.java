/*
 * 文件名：EtrExchangeTradeDto001.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.exchange.entrust.etrexchange.hqjj.dto;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.EtrEntrust;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradePortfolioRequest;
import com.joyintech.tams.exchange.entrust.etrexchange.hqjj.validator.EtrExchangeTradeJj001Validator;
import com.joyintech.tams.invest.common.risktrial.dto.RiskTrialRetDto;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.workflow.engine.dto.ProcessEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 *  交易信息 （左边下）
 * @author meifaxin
 * @version 1.0 2020年7月2日
 * @see EtrExchangeTradeJjSaveRequest
 * @since 1.0
 */
@PageId("JjEtrExchangeTrade001")
@ValidLogic(targetClass = EtrExchangeTradeJj001Validator.class , groups = {ValidateGroupEdit.class,
    ValidateGroupDelete.class})
@ApiModel(value="竞价指令下单表单",description="竞价指令下单表单")
public class EtrExchangeTradeJjSaveRequest extends EtrEntrust implements ProcessEntity{


    @ApiModelProperty(name="operateType",value="操作类型",example="submit/save", required = true)
    private String operateType;



    @ApiModelProperty(name="acctNo",value="资金账户",example="submit/save", required = true)
    private String acctNo;

    /**
     * 证券账户代码
     */
    private String secAcctId;

    @ApiModelProperty(name="finprodName",value="资产名称",example="", required = true)
    private String finprodName;

    @ApiModelProperty(name="targetType",value="目标类型",example="")
    private String targetType;

    @ApiModelProperty(name="priceModel",value="价格模式Code",example="01")
    private String priceModel;

    @ApiModelProperty(name="price",value="净价",example="")
    private BigDecimal price;

    @ApiModelProperty(name="declaType",value="全价价格",example="")
    private BigDecimal fprice;
    
    @ApiModelProperty(name="yield",value="收益率",example="")
    private BigDecimal yield;

    @ApiModelProperty(name="entrustPrice",value="指令价格",example="")
    private BigDecimal entrustPrice; 


    @ApiModelProperty(name="entrustBussType",value="指令业务类型",example="")
    private String entrustBussType;

    @ApiModelProperty(name="businessTypeName",value="业务类型名称",example="")
    private String businessTypeName;

    @ApiModelProperty(name="standardBondVolume",value="标准券数量",example="")
    private Integer standardBondVolume;

    @ApiModelProperty(name="repoAmt",value="回购金额",example="12")
    private BigDecimal repoAmt;

    @ApiModelProperty(name="repoRate",value="回购利率",example="12")
    private BigDecimal repoRate;

    /**
     * 资产类型
     */
    private String assetsType;
    /**
     * 涨停
     */
    private BigDecimal limitUp;

    /**
     * 跌停
     */
    private BigDecimal limitDown;
    /**
     * 记录操作方式
     */
    private String optType;
    /**
     * 席位号
     */
    private String seatId;

    /**
     * 约定号
     */
    private String agreeOnId;

    private String elementId;

    /**
     * 计算方式
     */
    private String calculateField;
    /**
     * 对手方交易商
     */
    private String counterOrg;

    /**
     * 本方交易员
     */
    private String trader;
    /**
            * 可转股数量
     */
    private BigDecimal toShareAvailableNum;

    /**
     * 清算速度
     */
    private String settleSpeed;
    /**
     * 申报类型
     */
    private String declaType;

    List<EtrExchangeTradePortfolioRequest> portfolioDtoList;

    /**
     * 选中的数据
     */
    private transient List<EtrExchangeTradeJjSaveRequest> selectedItem;

    /**
     * 风险试算实体类
     */
    private List<RiskTrialRetDto> riskTrialRetDto;

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
     * 获取 yield
     * @return yield.
     */
    public BigDecimal getEntrustPrice() {
        return entrustPrice;
    }


    
    /**
     * 设置 yield
     * @param entrustPrice
     */
    public void setEntrustPrice(BigDecimal entrustPrice) {
        this.entrustPrice = entrustPrice;
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










	public String getEntrustBussType() {
		return entrustBussType;
	}
	public void setEntrustBussType(String entrustBussType) {
		this.entrustBussType = entrustBussType;
	}

	public String getBusinessTypeName() {
		return businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

    @Override
    public String getPriceModel() {
        return priceModel;
    }

    @Override
    public void setPriceModel(String priceModel) {
        this.priceModel = priceModel;
    }

    public Integer getStandardBondVolume() {
        return standardBondVolume;
    }

    public void setStandardBondVolume(Integer standardBondVolume) {
        this.standardBondVolume = standardBondVolume;
    }

    public BigDecimal getRepoAmt() {
        return repoAmt;
    }

    public void setRepoAmt(BigDecimal repoAmt) {
        this.repoAmt = repoAmt;
    }

    public BigDecimal getRepoRate() {
        return repoRate;
    }

    public void setRepoRate(BigDecimal repoRate) {
        this.repoRate = repoRate;
    }

    /**
     * 获取 getPsStr
     * @return getPsStr.
     */
    public String getPsStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.交易方向, super.getPs());
    }

    public String getTradeMarketString(){
       return  SysDataUtil.getNameFromDataDict(DictCode.交易场所, super.getTradeMarket());
    }
    //add by chenjiaxing 投资目的值转化 start
    public String getInvAimStr(){
        return  SysDataUtil.getNameFromDataDict(DictCode.投资目的, super.getInvAim());
    }
    //add by chenjiaxing 投资目的值转化 end

    public String getPriceModelStr(){
        return  SysDataUtil.getNameFromDataDict(DictCode.价格模式, super.getPriceModel());
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<EtrExchangeTradePortfolioRequest> getPortfolioDtoList() {
        return portfolioDtoList;
    }

    public void setPortfolioDtoList(List<EtrExchangeTradePortfolioRequest> portfolioDtoList) {
        this.portfolioDtoList = portfolioDtoList;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public List<EtrExchangeTradeJjSaveRequest> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<EtrExchangeTradeJjSaveRequest> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getAssetsType() {
        return assetsType;
    }

    public void setAssetsType(String assetsType) {
        this.assetsType = assetsType;
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

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getAgreeOnId() {
        return agreeOnId;
    }

    public void setAgreeOnId(String agreeOnId) {
        this.agreeOnId = agreeOnId;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getCalculateField() {
        return calculateField;
    }

    public void setCalculateField(String calculateField) {
        this.calculateField = calculateField;
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

    public String getDeclaType() {
        return declaType;
    }

    public void setDeclaType(String declaType) {
        this.declaType = declaType;
    }

    public List<RiskTrialRetDto> getRiskTrialRetDto() {
        return riskTrialRetDto;
    }

    public void setRiskTrialRetDto(List<RiskTrialRetDto> riskTrialRetDto) {
        this.riskTrialRetDto = riskTrialRetDto;
    }
}
