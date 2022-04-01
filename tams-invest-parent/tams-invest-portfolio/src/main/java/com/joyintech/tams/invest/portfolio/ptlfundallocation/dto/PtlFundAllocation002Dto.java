/*
 * 文件名：PtlFundAllocationDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：fanshuanghu
 * 修改时间：2019年1月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.portfolio.ptlfundallocation.dto;

import com.joyintech.tams.common.base.entity.*;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.invest.common.constant.BussinessDictCode;
import com.joyintech.tams.invest.common.dto.StlPayOrdersDto;
import com.joyintech.tams.invest.common.dto.period.common.BussTrdTransferOrderDto;
import com.joyintech.tams.invest.portfolio.ptlfundallocation.validate.PtlFundAllocation001Validator;
import com.joyintech.tams.invest.portfolio.ptlfundallocation.validate.PtlFundAllocation002Validator;
import com.joyintech.tams.invest.portfolio.ptlfundallocation.dto.PtlFundAllocation001Dto;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import com.joyintech.workflow.engine.dto.ProcessEntity;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金划拨
 * @author fanshuanghu
 * @version 1.0 2019年1月29日
 * @see PtlFundAllocation002Dto
 * @since 1.0
 */
@PageId("PtlFundAllocation002")
@ValidLogic(targetClass = PtlFundAllocation002Validator.class, groups = {ValidateGroupEdit.class,
        ValidateGroupDelete.class})
public class PtlFundAllocation002Dto extends StlPayOrders implements ITableRow,ProcessEntity{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -8529608709130282761L;
    /**
     * 组合名称
     */
    private String portfolioName;

    /**
     * 实体投资组合代码
     */
    private String actPortfolioId;
    /**
     * 业务编号
     */
    private String busiNo;
    /**
     * 账户余额
     */
    private BigDecimal amount;
    /**
     * 节点ID
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
     * 组合现金余额
     */
    private PtlCashPosition ptlCashPosition;
    /**
     * 选中的实体类
     */
    private List<PtlFundAllocation002Dto> selectedItem;
    /**
     *明细信息
     */
    private List<PtlFundAllocation001Dto> ptlFundAllocation001Dtos;

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

    public List<PtlFundAllocation001Dto> getPtlFundAllocation001Dtos() {
        return ptlFundAllocation001Dtos;
    }

    public void setPtlFundAllocation001Dtos(List<PtlFundAllocation001Dto> ptlFundAllocation001Dtos) {
        this.ptlFundAllocation001Dtos = ptlFundAllocation001Dtos;
    }

    /**
     * 获取 selectedItem
     * @return selectedItem.
     */
    public List<PtlFundAllocation002Dto> getSelectedItem() {
        return selectedItem;
    }
    
    /**
     * 设置 selectedItem
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<PtlFundAllocation002Dto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    /**
     * 获取 busiNo
     * @return busiNo.
     */
    public String getBusiNo() {
        return busiNo;
    }
    
    /**
     * 设置 busiNo
     * @param busiNo busiNo
     */
    public void setBusiNo(String busiNo) {
        this.busiNo = busiNo;
    }
    
    /**
     * 获取 amount
     * @return amount.
     */
    public BigDecimal getAmount() {
        return amount;
    }
    
    /**
     * 设置 amount
     * @param amount amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
     * 划款指令表
     */
    private List<StlPayOrdersDto> stlPayOrdersList;
    /**
     * 划款指令
     */
    @NotEmpty(fieldName="TrdPlanRateInvestTrade002.stlPayInfo",groups = {ValidateGroupEdit.class})
    private List<BussTrdTransferOrderDto> bussTrdTransferOrder;
    /**
     * 货款指令
     * @return
     * @see
     * @since 1.0
     */
    @Override
    @PrimaryKey(Field.PK_1)
    public String getOrderId(){
        return super.getOrderId();
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
    /**
     * 获取币种字符串
     * @return 币种字符串
     * @see
     * @since 1.0
     */
    public String getCcyStr(){
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.币种, super.getCcy());
    }
    /**
     * 获取划款性质字符串
     * @return 划款性质字符串
     * @see
     * @since 1.0
     */
    public String getOrderStatusStr(){
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.画面数据状态, super.getOrderStatus());
    }
    /**
     * 获取划款类型字符串
     * @return 划款类型字符串
     * @see
     * @since 1.0
     */
    public String getPayTypeStr(){
//        return SysDataUtil.getNameFromDataDict(BussinessDictCode.指令类型, super.getPayType());
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.交易方向, super.getPayType());
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
     * 获取 bussTrdTransferOrder
     * @return bussTrdTransferOrder.
     */
    public List<BussTrdTransferOrderDto> getBussTrdTransferOrder() {
        return bussTrdTransferOrder;
    }

    /**
     * 设置 bussTrdTransferOrder
     * @param bussTrdTransferOrder bussTrdTransferOrder
     */
    public void setBussTrdTransferOrder(List<BussTrdTransferOrderDto> bussTrdTransferOrder) {
        this.bussTrdTransferOrder = bussTrdTransferOrder;
    }

	public TrdProductDealAdd getTrdProductDealAdd() {
		return trdProductDealAdd;
	}

	public void setTrdProductDealAdd(TrdProductDealAdd trdProductDealAdd) {
		this.trdProductDealAdd = trdProductDealAdd;
	}
	
	//add by yejiejie 2019-3-19 start
	/**
     * 金融产品交易扩展表
     */
    private PaTrdProductDeal paTrdProductDeal;

	public PaTrdProductDeal getPaTrdProductDeal() {
		return paTrdProductDeal;
	}

	public void setPaTrdProductDeal(PaTrdProductDeal paTrdProductDeal) {
		this.paTrdProductDeal = paTrdProductDeal;
	}
	//add by yejiejie 2019-3-19 end

    /* 多组合添加tradeId为主键 add by liuwei 20201124 start */
    /**
     * 货款指令
     * @return
     * @see
     * @since 1.0
     */
    @Override
    @PrimaryKey(Field.PK_2)
    public String getTradeId(){
        return super.getTradeId();
    }
    /* 多组合添加tradeId为主键 add by liuwei 20201124 end */

    /* 添加导入标志 add by liuwei 20210401 start */
    /**
     * 导入标志
     */
    public String isImportFlag;

    /**
     * 获取 导入标志
     *
     * @return isImportFlag 导入标志
     */
    public String getIsImportFlag() {
        return this.isImportFlag;
    }

    /**
     * 设置 导入标志
     *
     * @param isImportFlag 导入标志
     */
    public void setIsImportFlag(String isImportFlag) {
        this.isImportFlag = isImportFlag;
    }
    /* 添加导入标志 add by liuwei 20210401 end */

    public String getActPortfolioId() {
        return actPortfolioId;
    }

    public void setActPortfolioId(String actPortfolioId) {
        this.actPortfolioId = actPortfolioId;
    }
}
