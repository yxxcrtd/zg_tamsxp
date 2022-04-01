package com.joyintech.tams.exchange.common.bean;

import java.io.Serializable;

public class IntegratedQuote implements Serializable {
    private static final long serialVersionUID = -8963982621496824107L;
    private int quoteType;

    private int errorCode;
    private String errorMsg;
    // 大宗行情查询
    private String orderNo;
    private String propSeatNo; // 发起方交易商代码 PartyIDfqsa 对方席位号
    private String bsFlag;//买卖方向
    private String entrustProp;
    private String entrustBs; //买卖方向 Side

    private String stockCode;//证券代码
    private String stockType;//证券类别
    private double entrustPrice;//价格（净价） orderprice   回购利率 Price  价格1(收盘价格)
    private int entrustVolume;//数量 orderqty 成交数量1
    private String liaisonName;//  发起方交易员姓名 PartyIDfqyaname
    // 交易员代码
    private String liaisonTel; //  发起方交易员代码 PartyIDfqya
    private String contractId;// 订单编号 orderid
    private String reportTime;//报价时间 ordertime 更新时间
    private String accountId;

    //盘后大宗
    private double weightavgPrice;//日均价
    private int dealCount1;//  成交笔数1
    private int dealCount2;//  成交笔数2
    private double buyRemainQuantity1;//买盘剩余数量1
    private double sellRemainQuantity1;//卖盘剩余数量1
    private double buyRemainQuantity2;//买盘剩余数量2
    private double sellRemainQuantity2;//卖盘剩余数量2
    // 固收平台确定报价行情查询
    private String market;//交易市场
    private int pageSize;//请求行数 count
    private String posstr;//定位串
    private String stockName;//证券名称
    private String company;//报价方
    private double fullPrice;//全价 fullprice 价格2加权平均价格
    private double expireIncomeRate;//到期收益率 lastrate 到期年收益率
    private double bondAccruedRate;//债券应计利息 bondintr
    private int status;//报价状态
    private String buyConfirmId;//买方主场单号
    private String sellConfirmId;//卖方主场单号
    private double buyPrice;//回购赎回协议价
    private int buyQuoteAmount;//买方报价数量
    private double buyQuotePrice;//买方报价价格
    private double buyExchangeRate;//买入汇率
    private String reportTimeX;//申报时间
    private double sellPrice;//卖出价格
    private int sellQuoteAmount;//卖方报价数量
    private double sellQuotePrice;//卖方报价价格
    private double sellExchangeRate;//卖出汇率

    // 上海债券协议回购非公开报价行情 && 上海债券协议回购公开报价行情
    private String msgType;//消息类型 MsgType
    private String queryReqId;//查询请求编号 ApplReqID
    private String endQuoteNo;//结束行情序号 EndSeqNo
    private String brokerEntrustId;//交易所报价请求编号 QuoteRefID
    private String reportType;//报价申报类型 OrdType
    private int compactTerm;//回购期限 RepurchaseTerm
    private int realOccupyDays;//实际占款天数 UAInterestAccrualDays
    private String firstSettleDate;//首次结算日 SettlDate
    private String backDate;//回购到期日 MaturityDate
    private String expireSettleDate;//到期结算日 SettlDate2 交收结束日期
    private String pledgeStampCode;//质押券代码 SecurityID
    // Symbol 质押债券简称
    private String pledgeStampName;//质押券名称 SecurityIDname
    private String origPledgeStampCode;//原质押券代码 UnderlyingSecurityID
    private int pledgeStampVolume;//质押券数量 OrderQty 成交数量2
    private double discount;//折算比例 ContractMultiplier 折算率系数
    private double tradeBalance;//成交金额 TotalValueTraded 成交金额1
    private double origTradeBalance;//原成交金额 UnderlyingQty 成交金额2
    private double backInterest;//回购利息 AccruedInterestAmt
    private double expireSettleBalance;//到期结算金额 SettlCurrAmt
    private double pledgeStampBalance;//质押券面总额合计 LastQty 资产净值
    private String reductionType;//到期续做类型 OrderRestrictions
    private String origTradeDate;//初始交易日期 OrigTradeDate 首次交易日期
    private String origSerialNo;//成交编号 ExecRefID
    private int componentNum;//成份股个数
    private double impawnBalance;//质押金额
    private double prevImpawnBalance;//前质押金额
    private String bprsrcStatus;//协议回购质权人类型
    private String origEntrustDate;//原申请日期
    private String origCbpconferId;//原约定号
    private String fundAccount;//资产账户
    private String stockAccount;//证券账号
    private String seatNo;//席位编号
    private String propStockAccount;//对方申报账号
    private String propAccountName;//对方账户名称
    private String realDateBack;//实际购回日期
    // 交易商简称 CompanyName
    private String propSeatName;//发起方交易商名称 PartyIDfqname
    private String agencyNo;//销售商代码
    private String oppositeLiaisonTel;//对手方交易员代码 PartyIDdsfyb
    private String oppositeLiaisonName;//对手方交易员姓名 Partyiddsfybname
    private String oppositePropSeatNo;//对手方交易商代码 PartyIddsfsb 对方销售商
    private String oppositePropSeatName;//对手方交易商名称 PartyIDname
    private String ZQRName;//质权人 Zqrname
    private String remark;//补充条款 Remark
    private String tradeDay;//交易日期 TRD_DATE
    private String tradeTime;//成交时间 MATCH_TIME
    // 上海债券协议回购公开报价行情


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(int quoteType) {
        this.quoteType = quoteType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPropSeatNo() {
        return propSeatNo;
    }

    public void setPropSeatNo(String propSeatNo) {
        this.propSeatNo = propSeatNo;
    }

    public String getBsFlag() {
        return bsFlag;
    }

    public void setBsFlag(String bsFlag) {
        this.bsFlag = bsFlag;
    }

    public String getEntrustProp() {
        return entrustProp;
    }

    public void setEntrustProp(String entrustProp) {
        this.entrustProp = entrustProp;
    }

    public String getEntrustBs() {
        return entrustBs;
    }

    public void setEntrustBs(String entrustBs) {
        this.entrustBs = entrustBs;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public double getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(double entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public int getEntrustVolume() {
        return entrustVolume;
    }

    public void setEntrustVolume(int entrustVolume) {
        this.entrustVolume = entrustVolume;
    }

    public String getLiaisonName() {
        return liaisonName;
    }

    public void setLiaisonName(String liaisonName) {
        this.liaisonName = liaisonName;
    }

    public String getLiaisonTel() {
        return liaisonTel;
    }

    public void setLiaisonTel(String liaisonTel) {
        this.liaisonTel = liaisonTel;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPosstr() {
        return posstr;
    }

    public void setPosstr(String posstr) {
        this.posstr = posstr;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public double getExpireIncomeRate() {
        return expireIncomeRate;
    }

    public void setExpireIncomeRate(double expireIncomeRate) {
        this.expireIncomeRate = expireIncomeRate;
    }

    public double getBondAccruedRate() {
        return bondAccruedRate;
    }

    public void setBondAccruedRate(double bondAccruedRate) {
        this.bondAccruedRate = bondAccruedRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getQueryReqId() {
        return queryReqId;
    }

    public void setQueryReqId(String queryReqId) {
        this.queryReqId = queryReqId;
    }

    public String getEndQuoteNo() {
        return endQuoteNo;
    }

    public void setEndQuoteNo(String endQuoteNo) {
        this.endQuoteNo = endQuoteNo;
    }

    public String getBrokerEntrustId() {
        return brokerEntrustId;
    }

    public void setBrokerEntrustId(String brokerEntrustId) {
        this.brokerEntrustId = brokerEntrustId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public int getCompactTerm() {
        return compactTerm;
    }

    public void setCompactTerm(int compactTerm) {
        this.compactTerm = compactTerm;
    }

    public int getRealOccupyDays() {
        return realOccupyDays;
    }

    public void setRealOccupyDays(int realOccupyDays) {
        this.realOccupyDays = realOccupyDays;
    }

    public String getFirstSettleDate() {
        return firstSettleDate;
    }

    public void setFirstSettleDate(String firstSettleDate) {
        this.firstSettleDate = firstSettleDate;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getExpireSettleDate() {
        return expireSettleDate;
    }

    public void setExpireSettleDate(String expireSettleDate) {
        this.expireSettleDate = expireSettleDate;
    }

    public String getPledgeStampCode() {
        return pledgeStampCode;
    }

    public void setPledgeStampCode(String pledgeStampCode) {
        this.pledgeStampCode = pledgeStampCode;
    }

    public String getPledgeStampName() {
        return pledgeStampName;
    }

    public void setPledgeStampName(String pledgeStampName) {
        this.pledgeStampName = pledgeStampName;
    }

    public String getOrigPledgeStampCode() {
        return origPledgeStampCode;
    }

    public void setOrigPledgeStampCode(String origPledgeStampCode) {
        this.origPledgeStampCode = origPledgeStampCode;
    }

    public int getPledgeStampVolume() {
        return pledgeStampVolume;
    }

    public void setPledgeStampVolume(int pledgeStampVolume) {
        this.pledgeStampVolume = pledgeStampVolume;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTradeBalance() {
        return tradeBalance;
    }

    public void setTradeBalance(double tradeBalance) {
        this.tradeBalance = tradeBalance;
    }

    public double getOrigTradeBalance() {
        return origTradeBalance;
    }

    public void setOrigTradeBalance(double origTradeBalance) {
        this.origTradeBalance = origTradeBalance;
    }

    public double getBackInterest() {
        return backInterest;
    }

    public void setBackInterest(double backInterest) {
        this.backInterest = backInterest;
    }

    public double getExpireSettleBalance() {
        return expireSettleBalance;
    }

    public void setExpireSettleBalance(double expireSettleBalance) {
        this.expireSettleBalance = expireSettleBalance;
    }

    public double getPledgeStampBalance() {
        return pledgeStampBalance;
    }

    public void setPledgeStampBalance(double pledgeStampBalance) {
        this.pledgeStampBalance = pledgeStampBalance;
    }

    public String getReductionType() {
        return reductionType;
    }

    public void setReductionType(String reductionType) {
        this.reductionType = reductionType;
    }

    public String getOrigTradeDate() {
        return origTradeDate;
    }

    public void setOrigTradeDate(String origTradeDate) {
        this.origTradeDate = origTradeDate;
    }

    public String getOrigSerialNo() {
        return origSerialNo;
    }

    public void setOrigSerialNo(String origSerialNo) {
        this.origSerialNo = origSerialNo;
    }

    public int getComponentNum() {
        return componentNum;
    }

    public void setComponentNum(int componentNum) {
        this.componentNum = componentNum;
    }

    public double getImpawnBalance() {
        return impawnBalance;
    }

    public void setImpawnBalance(double impawnBalance) {
        this.impawnBalance = impawnBalance;
    }

    public double getPrevImpawnBalance() {
        return prevImpawnBalance;
    }

    public void setPrevImpawnBalance(double prevImpawnBalance) {
        this.prevImpawnBalance = prevImpawnBalance;
    }

    public String getBprsrcStatus() {
        return bprsrcStatus;
    }

    public void setBprsrcStatus(String bprsrcStatus) {
        this.bprsrcStatus = bprsrcStatus;
    }

    public String getOrigEntrustDate() {
        return origEntrustDate;
    }

    public void setOrigEntrustDate(String origEntrustDate) {
        this.origEntrustDate = origEntrustDate;
    }

    public String getOrigCbpconferId() {
        return origCbpconferId;
    }

    public void setOrigCbpconferId(String origCbpconferId) {
        this.origCbpconferId = origCbpconferId;
    }

    public String getFundAccount() {
        return fundAccount;
    }

    public void setFundAccount(String fundAccount) {
        this.fundAccount = fundAccount;
    }

    public String getStockAccount() {
        return stockAccount;
    }

    public void setStockAccount(String stockAccount) {
        this.stockAccount = stockAccount;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getPropStockAccount() {
        return propStockAccount;
    }

    public void setPropStockAccount(String propStockAccount) {
        this.propStockAccount = propStockAccount;
    }

    public String getPropAccountName() {
        return propAccountName;
    }

    public void setPropAccountName(String propAccountName) {
        this.propAccountName = propAccountName;
    }

    public String getRealDateBack() {
        return realDateBack;
    }

    public void setRealDateBack(String realDateBack) {
        this.realDateBack = realDateBack;
    }

    public String getPropSeatName() {
        return propSeatName;
    }

    public void setPropSeatName(String propSeatName) {
        this.propSeatName = propSeatName;
    }

    public String getAgencyNo() {
        return agencyNo;
    }

    public void setAgencyNo(String agencyNo) {
        this.agencyNo = agencyNo;
    }

    public String getOppositeLiaisonTel() {
        return oppositeLiaisonTel;
    }

    public void setOppositeLiaisonTel(String oppositeLiaisonTel) {
        this.oppositeLiaisonTel = oppositeLiaisonTel;
    }

    public String getOppositeLiaisonName() {
        return oppositeLiaisonName;
    }

    public void setOppositeLiaisonName(String oppositeLiaisonName) {
        this.oppositeLiaisonName = oppositeLiaisonName;
    }

    public String getOppositePropSeatNo() {
        return oppositePropSeatNo;
    }

    public void setOppositePropSeatNo(String oppositePropSeatNo) {
        this.oppositePropSeatNo = oppositePropSeatNo;
    }

    public String getOppositePropSeatName() {
        return oppositePropSeatName;
    }

    public void setOppositePropSeatName(String oppositePropSeatName) {
        this.oppositePropSeatName = oppositePropSeatName;
    }

    public String getZQRName() {
        return ZQRName;
    }

    public void setZQRName(String ZQRName) {
        this.ZQRName = ZQRName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBuyConfirmId() {
        return buyConfirmId;
    }

    public void setBuyConfirmId(String buyConfirmId) {
        this.buyConfirmId = buyConfirmId;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getBuyQuoteAmount() {
        return buyQuoteAmount;
    }

    public void setBuyQuoteAmount(int buyQuoteAmount) {
        this.buyQuoteAmount = buyQuoteAmount;
    }

    public double getBuyQuotePrice() {
        return buyQuotePrice;
    }

    public void setBuyQuotePrice(double buyQuotePrice) {
        this.buyQuotePrice = buyQuotePrice;
    }

    public double getBuyExchangeRate() {
        return buyExchangeRate;
    }

    public void setBuyExchangeRate(double buyExchangeRate) {
        this.buyExchangeRate = buyExchangeRate;
    }

    public String getSellConfirmId() {
        return sellConfirmId;
    }

    public void setSellConfirmId(String sellConfirmId) {
        this.sellConfirmId = sellConfirmId;
    }

    public String getReportTimeX() {
        return reportTimeX;
    }

    public void setReportTimeX(String reportTimeX) {
        this.reportTimeX = reportTimeX;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getSellQuoteAmount() {
        return sellQuoteAmount;
    }

    public void setSellQuoteAmount(int sellQuoteAmount) {
        this.sellQuoteAmount = sellQuoteAmount;
    }

    public double getSellQuotePrice() {
        return sellQuotePrice;
    }

    public void setSellQuotePrice(double sellQuotePrice) {
        this.sellQuotePrice = sellQuotePrice;
    }

    public double getSellExchangeRate() {
        return sellExchangeRate;
    }

    public void setSellExchangeRate(double sellExchangeRate) {
        this.sellExchangeRate = sellExchangeRate;
    }

    public String getTradeDay() {
        return tradeDay;
    }

    public void setTradeDay(String tradeDay) {
        this.tradeDay = tradeDay;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public int getDealCount1() {
        return dealCount1;
    }

    public void setDealCount1(int dealCount1) {
        this.dealCount1 = dealCount1;
    }

    public double getBuyRemainQuantity1() {
        return buyRemainQuantity1;
    }

    public void setBuyRemainQuantity1(double buyRemainQuantity1) {
        this.buyRemainQuantity1 = buyRemainQuantity1;
    }

    public double getSellRemainQuantity1() {
        return sellRemainQuantity1;
    }

    public void setSellRemainQuantity1(double sellRemainQuantity1) {
        this.sellRemainQuantity1 = sellRemainQuantity1;
    }

    public double getBuyRemainQuantity2() {
        return buyRemainQuantity2;
    }

    public void setBuyRemainQuantity2(double buyRemainQuantity2) {
        this.buyRemainQuantity2 = buyRemainQuantity2;
    }

    public double getSellRemainQuantity2() {
        return sellRemainQuantity2;
    }

    public void setSellRemainQuantity2(double sellRemainQuantity2) {
        this.sellRemainQuantity2 = sellRemainQuantity2;
    }

    public int getDealCount2() {
        return dealCount2;
    }

    public void setDealCount2(int dealCount2) {
        this.dealCount2 = dealCount2;
    }

    public double getWeightavgPrice() {
        return weightavgPrice;
    }

    public void setWeightavgPrice(double weightavgPrice) {
        this.weightavgPrice = weightavgPrice;
    }
}
