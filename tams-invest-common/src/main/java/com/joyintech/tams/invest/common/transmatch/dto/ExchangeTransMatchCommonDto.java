package com.joyintech.tams.invest.common.transmatch.dto;

import com.joyintech.background.component.common.pack.common.base.BaseEntity;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.SettleTransDataAdd;
import com.joyintech.tams.common.base.entity.SettleTransDataTotal;
import com.joyintech.tams.common.base.entity.SettleTransDataTotalAdd;
import com.joyintech.tams.common.base.entity.SettleTransDataTotalPledge;
import com.joyintech.tams.constant.DictCodeList;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeTransMatchCommonDto extends SettleTransDataTotal {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 8844629119926341373L;

    /**
     * 匹配类型
     */
    private String matchType;


    /**
     * 剩余成交金额
     */
    private BigDecimal remainAmount;

    /**
     * 剩余成交数量
     */
    private BigDecimal remainNum;

    /**
     * 随交易费用
     */
    private BigDecimal totalFeeAmtPay;

    /**
     * 不随交易费用
     */
    private BigDecimal totalUnFeeAmtPay;

    /**
     * 关联的指令编号list
     */
    private List<String> entrustIdList;

    /**
     * 汇总成交数据附属表
     */
    private SettleTransDataTotalAdd settleTransDataTotalAdd;

    /**
     * 汇总成交质押品信息表(质押式协议回购时使用)
     */
    private List<SettleTransDataTotalPledge> settleTransDataTotalPledgeList;

    /**
     *  转换资产代码/债转股代码（基金转换/债转股）
     */
    private String rFinprodType;

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public List<String> getEntrustIdList() {
        return entrustIdList;
    }

    public void setEntrustIdList(List<String> entrustIdList) {
        this.entrustIdList = entrustIdList;
    }

    public BigDecimal getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(BigDecimal remainAmount) {
        this.remainAmount = remainAmount;
    }

    public BigDecimal getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(BigDecimal remainNum) {
        this.remainNum = remainNum;
    }

    public BigDecimal getTotalFeeAmtPay() {
        if(StringUtil.equals(getIsPayWithTrade(), DictCodeList.是否_是.getValue())) {
            return ArithmeticUtil.doAdd(getStampFee(),getJsfAmt(),getGhfAmt(),getZgfAmt(),getSxfAmt(),getSettleFee(),getCommissionAmt());
        }
        else {
            return ArithmeticUtil.doAdd(getStampFee(),getJsfAmt(),getGhfAmt(),getZgfAmt(),getSxfAmt(),getSettleFee());
        }

    }

    public void setTotalFeeAmtPay(BigDecimal totalFeeAmtPay) {
        this.totalFeeAmtPay = totalFeeAmtPay;
    }

    public BigDecimal getTotalUnFeeAmtPay() {
        if(StringUtil.equals(getIsPayWithTrade(), DictCodeList.是否_是.getValue())) {
            return getCommissionAmt();
        }
        else {
            return BigDecimal.ZERO;
        }
    }

    public void setTotalUnFeeAmtPay(BigDecimal totalUnFeeAmtPay) {
        this.totalUnFeeAmtPay = totalUnFeeAmtPay;
    }

    public SettleTransDataTotalAdd getSettleTransDataTotalAdd() {
        return settleTransDataTotalAdd;
    }

    public void setSettleTransDataTotalAdd(SettleTransDataTotalAdd settleTransDataTotalAdd) {
        this.settleTransDataTotalAdd = settleTransDataTotalAdd;
    }

    public String getrFinprodType() {
        return rFinprodType;
    }

    public void setrFinprodType(String rFinprodType) {
        this.rFinprodType = rFinprodType;
    }

    public List<SettleTransDataTotalPledge> getSettleTransDataTotalPledgeList() {
        return settleTransDataTotalPledgeList;
    }

    public void setSettleTransDataTotalPledgeList(List<SettleTransDataTotalPledge> settleTransDataTotalPledgeList) {
        this.settleTransDataTotalPledgeList = settleTransDataTotalPledgeList;
    }
}
