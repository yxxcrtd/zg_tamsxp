/*
 * 文件名：TrdCommDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：renping
 * 修改时间：2020年2月5日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.common.dto.common;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.common.base.entity.FinCashCalcRule;
import com.joyintech.tams.common.base.entity.FinCashInfo;
import com.joyintech.tams.common.base.entity.FinCashPayRule;
import com.joyintech.tams.common.base.entity.FinCashPlan;
import com.joyintech.tams.common.base.entity.FinCashResetPlan;
import com.joyintech.tams.common.base.entity.FinExercisePlan;
import com.joyintech.tams.common.base.entity.FinProduct;
import com.joyintech.tams.common.base.entity.FinProductAdd;
import com.joyintech.tams.common.base.entity.FinProductValuation;
import com.joyintech.tams.common.base.entity.FinRateInfo;
import com.joyintech.tams.common.base.entity.FinRateInfoStructure;
import com.joyintech.tams.common.base.entity.FinTradeProduct;
import com.joyintech.tams.common.base.entity.FinTradeProductAdd;
import com.joyintech.tams.common.base.entity.MstBondValuationInfo;
import com.joyintech.tams.common.base.entity.MstCounterParty;
import com.joyintech.tams.common.base.entity.MstCustomerInfo;
import com.joyintech.tams.common.base.entity.MstCustomerInfoAddWithBLOBs;
import com.joyintech.tams.common.base.entity.PaTrdProductDeal;
import com.joyintech.tams.common.base.entity.PtlTrade;
import com.joyintech.tams.common.base.entity.StlFinProductAcct;
import com.joyintech.tams.common.base.entity.StlPayOrders;
import com.joyintech.tams.common.base.entity.TrdFinProductBarter;
import com.joyintech.tams.common.base.entity.TrdFinProductDealTotal;
import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.common.base.entity.TrdProductDealAdd;
import com.joyintech.tams.common.base.entity.TrdTradeFeeDetail;
import com.joyintech.tams.common.base.entity.WflTrdProductDealOth;

import java.util.List;

/**
 * 投管接口用DTO
 * 
 * @author renping
 * @version 1.0 2020年2月5日
 * @see InvestInterfCommDto
 * @since 1.0
 */
public class InvestInterfCommDto extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1348326097351724914L;

    // 画面ID
    private String pageId;

    // 交易状态：DictCode.画面数据状态
    private String tradeStatus;

    // 投资组合代码，子母组合交易的时候传子组合
    private String portfolioId;

    // 撤销说明
    private String revokeReason;

    // 交易类型转换用其他类型
    private String otherTypeForConvert;

    // 交易汇总表
    private TrdFinProductDealTotal trdFinProductDealTotal;

    // 交易类金融产品
    private FinTradeProduct finTradeProduct;

    // 金融产品交易
    private TrdProductDeal trdProductDeal;

    // 金融产品交易附属表
    private TrdProductDealAdd trdProductDealAdd;

    // 费用明细
    private TrdTradeFeeDetail trdTradeFeeDetail;

    /**
     * 金融产品交易扩展表
     */
    private PaTrdProductDeal paTrdProductDeal;

    // 划款指令（交易与指令一对一时使用）
    private StlPayOrders stlPayOrders;

    // 划款指令List（交易与指令一对多时使用）
    private List<StlPayOrders> stlPayOrdersList;

    // 金融产品账户表
    private List<StlFinProductAcct> stlFinProductAcctList;

    /**
     * 金融产品实物交易表
     */
    private TrdFinProductBarter trdFinProductBarter;

    // 费用明细
    private List<TrdTradeFeeDetail> tradeFeeDetaillist;

    // 标的金融产品信息
    private FinProduct finProduct;

    // 标的金融产品附属
    private FinProductAdd finProductAdd;

    // 交易对手信息
    private MstCounterParty mstCounterParty;

    // 交易类金融产品附属表
    private FinTradeProductAdd finTradeProductAdd;

    // 行权计划表
    private FinExercisePlan exercisePlan;

    /**
     * List<现金流信息Entity>
     */
    private List<FinCashInfo> finCashInfoList;

    /**
     * List<现金流支付规则表Entity>
     */
    private List<FinCashPayRule> finCashPayRuleList;

    /**
     * List<现金流计算规则表Entity>
     */
    private List<FinCashCalcRule> finCashCalcRuleList;

    /**
     * List<现金流利率信息表Entity>
     */
    private List<FinRateInfo> finRateInfoList;

    /**
     * 市场机构附属表
     */
    private MstCustomerInfoAddWithBLOBs mstCustomerInfoAddWithBLOBs;

    /**
     * 市场机构信息表
     */
    private MstCustomerInfo mstCustomerInfo;

    /**
     * 融产品交易业务流程其他信息
     */
    private WflTrdProductDealOth wflTrdProductDealOth;

    /**
     * List<现金流计划表Entity>
     */
    private List<FinCashPlan> finCashPlanList;

    /**
     * List<利率重置计划表Entity>
     */
    private List<FinCashResetPlan> finCashResetPlanList;

    /**
     * 组合交易表List
     */
    private List<PtlTrade> ptlTradeList;

    // 结构类型
    private FinRateInfoStructure finRateInfoStructure;

    /**
     * 获取 pageId
     * 
     * @return pageId.
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * 设置 pageId
     * 
     * @param pageId pageId
     */
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    /**
     * 获取 stlPayOrders
     * 
     * @return stlPayOrders.
     */
    public StlPayOrders getStlPayOrders() {
        return stlPayOrders;
    }

    /**
     * 设置 stlPayOrders
     * 
     * @param stlPayOrders stlPayOrders
     */
    public void setStlPayOrders(StlPayOrders stlPayOrders) {
        this.stlPayOrders = stlPayOrders;
    }

    /**
     * 获取 tradeStatus
     * 
     * @return tradeStatus.
     */
    public String getTradeStatus() {
        return tradeStatus;
    }

    /**
     * 设置 tradeStatus
     * 
     * @param tradeStatus tradeStatus
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * 获取 otherTypeForConvert
     * 
     * @return otherTypeForConvert.
     */
    public String getOtherTypeForConvert() {
        return otherTypeForConvert;
    }

    /**
     * 设置 otherTypeForConvert
     * 
     * @param otherTypeForConvert otherTypeForConvert
     */
    public void setOtherTypeForConvert(String otherTypeForConvert) {
        this.otherTypeForConvert = otherTypeForConvert;
    }

    /**
     * 获取 portfolioId
     * 
     * @return portfolioId.
     */
    public String getPortfolioId() {
        return portfolioId;
    }

    /**
     * 设置 portfolioId
     * 
     * @param portfolioId portfolioId
     */
    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    /**
     * 获取 revokeReason
     * 
     * @return revokeReason.
     */
    public String getRevokeReason() {
        return revokeReason;
    }

    /**
     * 设置 revokeReason
     * 
     * @param revokeReason revokeReason
     */
    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    /**
     * 获取 trdFinProductDealTotal
     * 
     * @return trdFinProductDealTotal.
     */
    public TrdFinProductDealTotal getTrdFinProductDealTotal() {
        return trdFinProductDealTotal;
    }

    /**
     * 设置 trdFinProductDealTotal
     * 
     * @param trdFinProductDealTotal trdFinProductDealTotal
     */
    public void setTrdFinProductDealTotal(TrdFinProductDealTotal trdFinProductDealTotal) {
        this.trdFinProductDealTotal = trdFinProductDealTotal;
    }

    /**
     * 获取 finTradeProduct
     * 
     * @return finTradeProduct.
     */
    public FinTradeProduct getFinTradeProduct() {
        return finTradeProduct;
    }

    /**
     * 设置 finTradeProduct
     * 
     * @param finTradeProduct finTradeProduct
     */
    public void setFinTradeProduct(FinTradeProduct finTradeProduct) {
        this.finTradeProduct = finTradeProduct;
    }

    /**
     * 获取 trdProductDeal
     * 
     * @return trdProductDeal.
     */
    public TrdProductDeal getTrdProductDeal() {
        return trdProductDeal;
    }

    /**
     * 设置 trdProductDeal
     * 
     * @param trdProductDeal trdProductDeal
     */
    public void setTrdProductDeal(TrdProductDeal trdProductDeal) {
        this.trdProductDeal = trdProductDeal;
    }

    /**
     * 获取 trdProductDealAdd
     * 
     * @return trdProductDealAdd.
     */
    public TrdProductDealAdd getTrdProductDealAdd() {
        return trdProductDealAdd;
    }

    /**
     * 设置 trdProductDealAdd
     * 
     * @param trdProductDealAdd trdProductDealAdd
     */
    public void setTrdProductDealAdd(TrdProductDealAdd trdProductDealAdd) {
        this.trdProductDealAdd = trdProductDealAdd;
    }

    /**
     * 获取 stlPayOrdersList
     * 
     * @return stlPayOrdersList.
     */
    public List<StlPayOrders> getStlPayOrdersList() {
        return stlPayOrdersList;
    }

    /**
     * 设置 stlPayOrdersList
     * 
     * @param stlPayOrdersList stlPayOrdersList
     */
    public void setStlPayOrdersList(List<StlPayOrders> stlPayOrdersList) {
        this.stlPayOrdersList = stlPayOrdersList;
    }

    /**
     * 获取 stlFinProductAcctList
     * 
     * @return stlFinProductAcctList.
     */
    public List<StlFinProductAcct> getStlFinProductAcctList() {
        return stlFinProductAcctList;
    }

    /**
     * 设置 stlFinProductAcctList
     * 
     * @param stlFinProductAcctList stlFinProductAcctList
     */
    public void setStlFinProductAcctList(List<StlFinProductAcct> stlFinProductAcctList) {
        this.stlFinProductAcctList = stlFinProductAcctList;
    }

    /**
     * 获取 trdFinProductBarter
     * 
     * @return trdFinProductBarter.
     */
    public TrdFinProductBarter getTrdFinProductBarter() {
        return trdFinProductBarter;
    }

    /**
     * 设置 trdFinProductBarter
     * 
     * @param trdFinProductBarter trdFinProductBarter
     */
    public void setTrdFinProductBarter(TrdFinProductBarter trdFinProductBarter) {
        this.trdFinProductBarter = trdFinProductBarter;
    }

    /**
     * 获取 tradeFeeDetaillist
     * 
     * @return tradeFeeDetaillist.
     */
    public List<TrdTradeFeeDetail> getTradeFeeDetaillist() {
        return tradeFeeDetaillist;
    }

    /**
     * 设置 tradeFeeDetaillist
     * 
     * @param tradeFeeDetaillist tradeFeeDetaillist
     */
    public void setTradeFeeDetaillist(List<TrdTradeFeeDetail> tradeFeeDetaillist) {
        this.tradeFeeDetaillist = tradeFeeDetaillist;
    }

    /**
     * 获取 finProduct
     * 
     * @return finProduct.
     */
    public FinProduct getFinProduct() {
        return finProduct;
    }

    /**
     * 设置 finProduct
     * 
     * @param finProduct finProduct
     */
    public void setFinProduct(FinProduct finProduct) {
        this.finProduct = finProduct;
    }

    /**
     * 获取 finProductAdd
     * 
     * @return finProductAdd.
     */
    public FinProductAdd getFinProductAdd() {
        return finProductAdd;
    }

    /**
     * 设置 finProductAdd
     * 
     * @param finProductAdd finProductAdd
     */
    public void setFinProductAdd(FinProductAdd finProductAdd) {
        this.finProductAdd = finProductAdd;
    }

    /**
     * 获取 finCashInfoList
     * 
     * @return finCashInfoList.
     */
    public List<FinCashInfo> getFinCashInfoList() {
        return finCashInfoList;
    }

    /**
     * 设置 finCashInfoList
     * 
     * @param finCashInfoList finCashInfoList
     */
    public void setFinCashInfoList(List<FinCashInfo> finCashInfoList) {
        this.finCashInfoList = finCashInfoList;
    }

    /**
     * 获取 finCashPayRuleList
     * 
     * @return finCashPayRuleList.
     */
    public List<FinCashPayRule> getFinCashPayRuleList() {
        return finCashPayRuleList;
    }

    /**
     * 设置 finCashPayRuleList
     * 
     * @param finCashPayRuleList finCashPayRuleList
     */
    public void setFinCashPayRuleList(List<FinCashPayRule> finCashPayRuleList) {
        this.finCashPayRuleList = finCashPayRuleList;
    }

    /**
     * 获取 finCashCalcRuleList
     * 
     * @return finCashCalcRuleList.
     */
    public List<FinCashCalcRule> getFinCashCalcRuleList() {
        return finCashCalcRuleList;
    }

    /**
     * 设置 finCashCalcRuleList
     * 
     * @param finCashCalcRuleList finCashCalcRuleList
     */
    public void setFinCashCalcRuleList(List<FinCashCalcRule> finCashCalcRuleList) {
        this.finCashCalcRuleList = finCashCalcRuleList;
    }

    /**
     * 获取 finRateInfoList
     * 
     * @return finRateInfoList.
     */
    public List<FinRateInfo> getFinRateInfoList() {
        return finRateInfoList;
    }

    /**
     * 设置 finRateInfoList
     * 
     * @param finRateInfoList finRateInfoList
     */
    public void setFinRateInfoList(List<FinRateInfo> finRateInfoList) {
        this.finRateInfoList = finRateInfoList;
    }

    /**
     * 获取 finCashPlanList
     * 
     * @return finCashPlanList.
     */
    public List<FinCashPlan> getFinCashPlanList() {
        return finCashPlanList;
    }

    /**
     * 设置 finCashPlanList
     * 
     * @param finCashPlanList finCashPlanList
     */
    public void setFinCashPlanList(List<FinCashPlan> finCashPlanList) {
        this.finCashPlanList = finCashPlanList;
    }

    /**
     * 获取 finCashResetPlanList
     * 
     * @return finCashResetPlanList.
     */
    public List<FinCashResetPlan> getFinCashResetPlanList() {
        return finCashResetPlanList;
    }

    /**
     * 设置 finCashResetPlanList
     * 
     * @param finCashResetPlanList finCashResetPlanList
     */
    public void setFinCashResetPlanList(List<FinCashResetPlan> finCashResetPlanList) {
        this.finCashResetPlanList = finCashResetPlanList;
    }

    public MstCustomerInfoAddWithBLOBs getMstCustomerInfoAddWithBLOBs() {
        return mstCustomerInfoAddWithBLOBs;
    }

    public void setMstCustomerInfoAddWithBLOBs(MstCustomerInfoAddWithBLOBs mstCustomerInfoAddWithBLOBs) {
        this.mstCustomerInfoAddWithBLOBs = mstCustomerInfoAddWithBLOBs;
    }

    public MstCustomerInfo getMstCustomerInfo() {
        return mstCustomerInfo;
    }

    public void setMstCustomerInfo(MstCustomerInfo mstCustomerInfo) {
        this.mstCustomerInfo = mstCustomerInfo;
    }

    /**
     * 获取 ptlTradeList
     * 
     * @return ptlTradeList.
     */
    public List<PtlTrade> getPtlTradeList() {
        return ptlTradeList;
    }

    /**
     * 设置 ptlTradeList
     * 
     * @param ptlTradeList ptlTradeList
     */
    public void setPtlTradeList(List<PtlTrade> ptlTradeList) {
        this.ptlTradeList = ptlTradeList;
    }

    /**
     * 获取 paTrdProductDeal
     *
     * @return paTrdProductDeal
     */
    public PaTrdProductDeal getPaTrdProductDeal() {
        return paTrdProductDeal;
    }

    /**
     * 设置 paTrdProductDeal
     *
     * @param paTrdProductDeal paTrdProductDeal
     */
    public void setPaTrdProductDeal(PaTrdProductDeal paTrdProductDeal) {
        this.paTrdProductDeal = paTrdProductDeal;
    }

    public MstCounterParty getMstCounterParty() {
        return mstCounterParty;
    }

    public void setMstCounterParty(MstCounterParty mstCounterParty) {
        this.mstCounterParty = mstCounterParty;
    }

    public WflTrdProductDealOth getWflTrdProductDealOth() {
        return wflTrdProductDealOth;
    }

    public void setWflTrdProductDealOth(WflTrdProductDealOth wflTrdProductDealOth) {
        this.wflTrdProductDealOth = wflTrdProductDealOth;
    }

    /**
     * @return the finTradeProductAdd
     */
    public FinTradeProductAdd getFinTradeProductAdd() {
        return finTradeProductAdd;
    }

    /**
     * @param finTradeProductAdd the finTradeProductAdd to set
     */
    public void setFinTradeProductAdd(FinTradeProductAdd finTradeProductAdd) {
        this.finTradeProductAdd = finTradeProductAdd;
    }

    /**
     * @return the exercisePlan
     */
    public FinExercisePlan getExercisePlan() {
        return exercisePlan;
    }

    /**
     * @param exercisePlan the exercisePlan to set
     */
    public void setExercisePlan(FinExercisePlan exercisePlan) {
        this.exercisePlan = exercisePlan;
    }

    /**
     * @return the finRateInfoStructure
     */
    public FinRateInfoStructure getFinRateInfoStructure() {
        return finRateInfoStructure;
    }

    /**
     * @param finRateInfoStructure the finRateInfoStructure to set
     */
    public void setFinRateInfoStructure(FinRateInfoStructure finRateInfoStructure) {
        this.finRateInfoStructure = finRateInfoStructure;
    }

    public TrdTradeFeeDetail getTrdTradeFeeDetail() {
        return trdTradeFeeDetail;
    }

    public void setTrdTradeFeeDetail(TrdTradeFeeDetail trdTradeFeeDetail) {
        this.trdTradeFeeDetail = trdTradeFeeDetail;
    }

    /**
     * @return the finCashCalcRule
     */
    public FinCashCalcRule getFinCashCalcRule() {
        return finCashCalcRule;
    }

    /**
     * @param finCashCalcRule the finCashCalcRule to set
     */
    public void setFinCashCalcRule(FinCashCalcRule finCashCalcRule) {
        this.finCashCalcRule = finCashCalcRule;
    }

    // 现金流计算规则表
    private FinCashCalcRule finCashCalcRule;

    /**
     * @param 质押券明细列表
     */
    private List<PledgeBondInfoDto> pledgeBondListDetailed;

    /**
     * @return 质押券明细列表
     */
    public List<PledgeBondInfoDto> getPledgeBondListDetailed() {
        return pledgeBondListDetailed;
    }

    /**
     * @param 质押券明细列表
     */
    public void setPledgeBondListDetailed(List<PledgeBondInfoDto> pledgeBondListDetailed) {
        this.pledgeBondListDetailed = pledgeBondListDetailed;
    }

    // 场外金融产品估值表
    private FinProductValuation finProductValuation;

    /**
     * @return the finProductValuation
     */
    public FinProductValuation getFinProductValuation() {
        return finProductValuation;
    }

    /**
     * @param finProductValuation the finProductValuation to set
     */
    public void setFinProductValuation(FinProductValuation finProductValuation) {
        this.finProductValuation = finProductValuation;
    }

    // 债券估值行情
    private MstBondValuationInfo mstBondValuationInfo;

    /**
     * @return the mstBondValuationInfo
     */
    public MstBondValuationInfo getMstBondValuationInfo() {
        return mstBondValuationInfo;
    }

    /**
     * @param mstBondValuationInfo the mstBondValuationInfo to set
     */
    public void setMstBondValuationInfo(MstBondValuationInfo mstBondValuationInfo) {
        this.mstBondValuationInfo = mstBondValuationInfo;
    }

}
