package com.joyintech.service.trade.manualsettle.dto;

import com.joyintech.tams.common.base.entity.TrdManualSettleFlow;

import java.math.BigDecimal;

/**
 * @author zhangnian
 * @ClassName ManualSettleFlowDto
 * @description TODO
 * @date 2020/10/22 0022 14:05
 **/
public class ManualSettleFlowDto extends TrdManualSettleFlow {

    /**
     * 市场代码
     */
    private String finprodMarketId;

    /**
     * 金融产品全称
     */
    private String finprodName;

    /**
     * 金融产品类型
     */
    private String finprodType2;

    /**
     * 质押品ID
     */
    private String pledgeId;

    /**
     * 质押品ID abbr
     */
    private String pledgeIdAbbr;

    /**
     * 原质押品ID
     */
    private String bPledgeId;

    /**
     * 原质押品ID简称
     */
    private String bPledgeIdAbbr;

    /**
     * 质押品名称 abbr
     */
    private String pledgeName;

    /**
     *回购金额（质押式回购）
     */
    private BigDecimal prin;

    /**
     *到期结算金额（质押式回购）
     */
    private BigDecimal mTradeAmt;

    /**
     * 质押数量（质押式回购）
     */
    private BigDecimal pledgeNumber;

    private String operator;

    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    public String getFinprodName() {
        return finprodName;
    }

    public void setFinprodName(String finprodName) {
        this.finprodName = finprodName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(String pledgeId) {
        this.pledgeId = pledgeId;
    }

    public String getPledgeIdAbbr() {
        return pledgeIdAbbr;
    }

    public void setPledgeIdAbbr(String pledgeIdAbbr) {
        this.pledgeIdAbbr = pledgeIdAbbr;
    }

    public String getPledgeName() {
        return pledgeName;
    }

    public void setPledgeName(String pledgeName) {
        this.pledgeName = pledgeName;
    }

    public BigDecimal getPrin() {
        return prin;
    }

    public void setPrin(BigDecimal prin) {
        this.prin = prin;
    }

    public BigDecimal getmTradeAmt() {
        return mTradeAmt;
    }

    public void setmTradeAmt(BigDecimal mTradeAmt) {
        this.mTradeAmt = mTradeAmt;
    }

    public BigDecimal getPledgeNumber() {
        return pledgeNumber;
    }

    public void setPledgeNumber(BigDecimal pledgeNumber) {
        this.pledgeNumber = pledgeNumber;
    }

    public String getFinprodType2() {
        return finprodType2;
    }

    public void setFinprodType2(String finprodType2) {
        this.finprodType2 = finprodType2;
    }

    public String getbPledgeId() {
        return bPledgeId;
    }

    public void setbPledgeId(String bPledgeId) {
        this.bPledgeId = bPledgeId;
    }

    public String getbPledgeIdAbbr() {
        return bPledgeIdAbbr;
    }

    public void setbPledgeIdAbbr(String bPledgeIdAbbr) {
        this.bPledgeIdAbbr = bPledgeIdAbbr;
    }
}

