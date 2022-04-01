/*
 * 文件名：PtlFundAllocationDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：fanshuanghu
 * 修改时间：2019年1月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.portfolio.ptlfundallocation.dto;

import java.util.List;

import com.joyintech.tams.common.base.entity.PaTrdProductDeal;
import com.joyintech.tams.common.base.entity.PtlCashPosition;
import com.joyintech.tams.common.base.entity.PtlCashPositionFlow;
import com.joyintech.tams.common.base.entity.PtlTrade;
import com.joyintech.tams.common.base.entity.StlPayOrders;
import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.common.base.entity.TrdProductDealAdd;
import com.joyintech.tams.invest.common.dto.StlPayOrdersDto;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资金划拨实体类
 * @author fanshuanghu
 * @version 1.0 2019年1月29日
 * @see PtlFundAllocationDto
 * @since 1.0
 */
public class PtlFundAllocationDto extends StlPayOrders{
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -1322063902964780230L;
    /**
     * 实体组合名称
     */
    private String actPortfolioId;
    /**
     * 组合名称
     */
    private String portfolioName;
    /**
     * 节点名称
     */
    private String elementId;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 组合现金流水
     */
    private PtlCashPositionFlow ptlCashPositionFlow;
    /**
     * 组合交易
     */
    private PtlTrade ptlTrade;
    /**
     * 金融产品交易
     */
    private TrdProductDeal trdProductDeal;
    /**
     * 金融产品交易附属
     */
    private TrdProductDealAdd trdProductDealAdd;
    /**
     * 金融产品交易扩展表
     */
    private PaTrdProductDeal paTrdProductDeal;
    /**
     * 组合现金余额
     */
    private PtlCashPosition ptlCashPosition;
    /**
     * 划款指令表
     */
    private List<StlPayOrdersDto> stlPayOrdersList;


    @ApiModelProperty(name="softSkipMode",value="软控是否继续？Y：继续",example="Y")
    private String softSkipMode;

    private String positionType;

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getSoftSkipMode() {
        return softSkipMode;
    }

    public void setSoftSkipMode(String softSkipMode) {
        this.softSkipMode = softSkipMode;
    }

    
    /**
     * 获取 stlPayOrdersList
     * @return stlPayOrdersList.
     */
    public List<StlPayOrdersDto> getStlPayOrdersList() {
        return stlPayOrdersList;
    }
    
    /**
     * 设置 stlPayOrdersList
     * @param stlPayOrdersList stlPayOrdersList
     */
    public void setStlPayOrdersList(List<StlPayOrdersDto> stlPayOrdersList) {
        this.stlPayOrdersList = stlPayOrdersList;
    }

    /**
     * 获取 ptlCashPositionFlow
     * @return ptlCashPositionFlow.
     */
    public PtlCashPositionFlow getPtlCashPositionFlow() {
        return ptlCashPositionFlow;
    }
    
    /**
     * 设置 ptlCashPositionFlow
     * @param ptlCashPositionFlow ptlCashPositionFlow
     */
    public void setPtlCashPositionFlow(PtlCashPositionFlow ptlCashPositionFlow) {
        this.ptlCashPositionFlow = ptlCashPositionFlow;
    }

    /**
     * 获取 ptlTrade
     * @return ptlTrade.
     */
    public PtlTrade getPtlTrade() {
        return ptlTrade;
    }
    
    /**
     * 设置 ptlTrade
     * @param ptlTrade ptlTrade
     */
    public void setPtlTrade(PtlTrade ptlTrade) {
        this.ptlTrade = ptlTrade;
    }
    
    /**
     * 获取 trdProductDeal
     * @return trdProductDeal.
     */
    public TrdProductDeal getTrdProductDeal() {
        return trdProductDeal;
    }
    
    /**
     * 设置 trdProductDeal
     * @param trdProductDeal trdProductDeal
     */
    public void setTrdProductDeal(TrdProductDeal trdProductDeal) {
        this.trdProductDeal = trdProductDeal;
    }
    
    /**
     * 获取 ptlCashPosition
     * @return ptlCashPosition.
     */
    public PtlCashPosition getPtlCashPosition() {
        return ptlCashPosition;
    }
    
    /**
     * 设置 ptlCashPosition
     * @param ptlCashPosition ptlCashPosition
     */
    public void setPtlCashPosition(PtlCashPosition ptlCashPosition) {
        this.ptlCashPosition = ptlCashPosition;
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
     * 获取 portfolioName
     * @return portfolioName.
     */
    public String getPortfolioName() {
        return portfolioName;
    }

    /**
     * 设置 portfolioName
     * @param portfolioName portfolioName
     */
    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }
    
    public TrdProductDealAdd getTrdProductDealAdd() {
        return trdProductDealAdd;
    }

    public void setTrdProductDealAdd(TrdProductDealAdd trdProductDealAdd) {
        this.trdProductDealAdd = trdProductDealAdd;
    }

    /**
     * 获取 paTrdProductDeal
     * @return paTrdProductDeal.
     */
    public PaTrdProductDeal getPaTrdProductDeal() {
        return paTrdProductDeal;
    }

    /**
     * 设置 paTrdProductDeal
     * @param paTrdProductDeal paTrdProductDeal
     */
    public void setPaTrdProductDeal(PaTrdProductDeal paTrdProductDeal) {
        this.paTrdProductDeal = paTrdProductDeal;
    }

    /**
     * 获取 actPortfolioId
     *
     * @return actPortfolioId
     */
    public String getActPortfolioId() {
        return actPortfolioId;
    }

    /**
     * 设置 actPortfolioId
     *
     * @param actPortfolioId actPortfolioId
     */
    public void setActPortfolioId(String actPortfolioId) {
        this.actPortfolioId = actPortfolioId;
    }
}
