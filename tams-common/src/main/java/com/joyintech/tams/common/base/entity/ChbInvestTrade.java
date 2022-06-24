package com.joyintech.tams.common.base.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.framework.base.BaseEntity;

/**
 * 描述:CHB_INVEST_TRADE表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-02-18
 */
public class ChbInvestTrade extends BaseChbEntity implements Cloneable{
    /**
     * 交易UUID
     */
    private String tradeSeqno;

    /**
     * 行内交易编码
     */
    private String tradeCode;

    /**
     * 关联系统交易编号
     */
    private String fTradeCode;

    /**
     * 投资品种 01债券 02ABS 03计划类资产（利率型）04计划类资产（净值型）05计划类资产（货币型）06存款类资产 07拆借拆入08拆借拆出 09债券质押式正回购 10债券质押式逆回购 11债券买断式正回购 12债券买断式逆回购 13基金类资产（货币型）14基金类资产（净值型）15债权类、权益类 16通道费用 17交易场所费用 18其他费用（他行费用）19理财托管户收息 20其他账户收息 21自定义现金流 22营改增 23银行间费用 24通道交易
     */
    private String investVariety;

    /**
     * 投资类型AFS 可供出售 TRA 交易性 HTM 持有到期 SRM应收款项
     */
    private String investType;

    /**
     * 产品代码
     */
    private String prodId;

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
     * 发生金额
     */
    private BigDecimal amt;

    /**
     * 折算人民币金额
     */
    private BigDecimal cnyAmt;

    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 资产流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     */
    private String curAssetType;

    /**
     * 资产流动类型说明
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
     * 通道代码
     */
    private String chlCpctId;

    /**
     * 交易类型01 资产交易 02通道交易 03产品费用交易 04其他交易 05资管计划交易  06通道费用交易
     */
    private String tradeType;

    /**
     * 通道名称
     */
    private String chlAgrtName;

    /**
     * 是否底层交易流水 01 是/ 02 否
     */
    private String isUnderTrade;

    /**
     * 数据状态 D	删除 A	待处理 E	有效
     */
    private String status;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 撤销日
     */
    private Date revDate;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 利率
     */
    private BigDecimal rate;

    /**
     * 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     */
    private String basis;

    /**
     * 交易类型01	部分还本/02	到期-本金/03	到期-利息/04	份额调减/05	份额调增/06	红利再投/07	交易场所费用支出/08	理财托管户收息确认/09	买入/10	卖出/11	其他费用支出/12	其他账户收息确认/13	申购/14	自定义现金流收入/15	自定义现金流支出/16	赎回/17	通道费用支付/18	通道交易-买入/19	通道交易-卖出/20	通道托管（管理）费/21	现金分红/22	银行间费用支出/23	营改增缴纳/24	中间付息/25	中间还本-本金/26	中间还本-利息
     */
    private String dealtype;

    /**
     * 面值
     */
    private BigDecimal facevalue;
    
    /**
     * 当日分红累计份额
     */
    private BigDecimal intPayAmt;
    
    /**
     * 划款方式
     */
    private String payType;
    
   /**
     * 资产计量方式
     */
    private String assetMethod;

    /**
     * 交易对手方类型
     */
    private String rivalPartyType;

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

    /**
     * 交易UUID
     * @return TRADE_SEQNO 交易UUID
     */
    public String getTradeSeqno() {
        return tradeSeqno;
    }

    /**
     * 交易UUID
     * @param tradeSeqno 交易UUID
     */
    public void setTradeSeqno(String tradeSeqno) {
        this.tradeSeqno = tradeSeqno == null ? null : tradeSeqno.trim();
    }

    /**
     * 行内交易编码
     * @return TRADE_CODE 行内交易编码
     */
    public String getTradeCode() {
        return tradeCode;
    }

    /**
     * 行内交易编码
     * @param tradeCode 行内交易编码
     */
    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
    }

    /**
     * 投资品种 01债券 02ABS 03计划类资产（利率型）04计划类资产（净值型）05计划类资产（货币型）06存款类资产 07拆借拆入08拆借拆出 09债券质押式正回购 10债券质押式逆回购 11债券买断式正回购 12债券买断式逆回购 13基金类资产（货币型）14基金类资产（净值型）15债权类、权益类 16通道费用 17交易场所费用 18其他费用（他行费用）19理财托管户收息 20其他账户收息 21自定义现金流 22营改增 23银行间费用 24通道交易
     * @return INVEST_VARIETY 投资品种 01债券 02ABS 03计划类资产（利率型）04计划类资产（净值型）05计划类资产（货币型）06存款类资产 07拆借拆入08拆借拆出 09债券质押式正回购 10债券质押式逆回购 11债券买断式正回购 12债券买断式逆回购 13基金类资产（货币型）14基金类资产（净值型）15债权类、权益类 16通道费用 17交易场所费用 18其他费用（他行费用）19理财托管户收息 20其他账户收息 21自定义现金流 22营改增 23银行间费用 24通道交易
     */
    public String getInvestVariety() {
        return investVariety;
    }

    /**
     * 投资品种 01债券 02ABS 03计划类资产（利率型）04计划类资产（净值型）05计划类资产（货币型）06存款类资产 07拆借拆入08拆借拆出 09债券质押式正回购 10债券质押式逆回购 11债券买断式正回购 12债券买断式逆回购 13基金类资产（货币型）14基金类资产（净值型）15债权类、权益类 16通道费用 17交易场所费用 18其他费用（他行费用）19理财托管户收息 20其他账户收息 21自定义现金流 22营改增 23银行间费用 24通道交易
     * @param investVariety 投资品种 01债券 02ABS 03计划类资产（利率型）04计划类资产（净值型）05计划类资产（货币型）06存款类资产 07拆借拆入08拆借拆出 09债券质押式正回购 10债券质押式逆回购 11债券买断式正回购 12债券买断式逆回购 13基金类资产（货币型）14基金类资产（净值型）15债权类、权益类 16通道费用 17交易场所费用 18其他费用（他行费用）19理财托管户收息 20其他账户收息 21自定义现金流 22营改增 23银行间费用 24通道交易
     */
    public void setInvestVariety(String investVariety) {
        this.investVariety = investVariety == null ? null : investVariety.trim();
    }

    /**
     * 投资类型AFS 可供出售 TRA 交易性 HTM 持有到期 SRM应收款项
     * @return INVEST_TYPE 投资类型AFS 可供出售 TRA 交易性 HTM 持有到期 SRM应收款项
     */
    public String getInvestType() {
        return investType;
    }

    /**
     * 投资类型AFS 可供出售 TRA 交易性 HTM 持有到期 SRM应收款项
     * @param investType 投资类型AFS 可供出售 TRA 交易性 HTM 持有到期 SRM应收款项
     */
    public void setInvestType(String investType) {
        this.investType = investType == null ? null : investType.trim();
    }

    /**
     * 产品代码
     * @return PROD_ID 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 发行机构代码
     * @return REGIST_BANK_CODE 发行机构代码
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 发行机构代码
     * @param registBankCode 发行机构代码
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
    }

    /**
     * 行内资产/负债编码
     * @return ASSET_CODE 行内资产/负债编码
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 行内资产/负债编码
     * @param assetCode 行内资产/负债编码
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 币种 参考数据字典：CURRENCY_CHB
     * @return CCY 币种 参考数据字典：CURRENCY_CHB
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种 参考数据字典：CURRENCY_CHB
     * @param ccy 币种 参考数据字典：CURRENCY_CHB
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 发生金额
     * @return AMT 发生金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 发生金额
     * @param amt 发生金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 折算人民币金额
     * @return CNY_AMT 折算人民币金额
     */
    public BigDecimal getCnyAmt() {
        return cnyAmt;
    }

    /**
     * 折算人民币金额
     * @param cnyAmt 折算人民币金额
     */
    public void setCnyAmt(BigDecimal cnyAmt) {
        this.cnyAmt = cnyAmt;
    }

    /**
     * 数量
     * @return NUM 数量
     */
    public BigDecimal getNum() {
        return num;
    }

    /**
     * 数量
     * @param num 数量
     */
    public void setNum(BigDecimal num) {
        this.num = num;
    }

    /**
     * 资产流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     * @return CUR_ASSET_TYPE 资产流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     */
    public String getCurAssetType() {
        return curAssetType;
    }

    /**
     * 资产流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     * @param curAssetType 资产流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
     */
    public void setCurAssetType(String curAssetType) {
        this.curAssetType = curAssetType == null ? null : curAssetType.trim();
    }

    /**
     * 资产流动类型说明
     * @return CUR_ASSETDET_TYPE 资产流动类型说明
     */
    public String getCurAssetdetType() {
        return curAssetdetType;
    }

    /**
     * 资产流动类型说明
     * @param curAssetdetType 资产流动类型说明
     */
    public void setCurAssetdetType(String curAssetdetType) {
        this.curAssetdetType = curAssetdetType == null ? null : curAssetdetType.trim();
    }

    /**
     * 交易日
     * @return DEALDATE 交易日
     */
    public Date getDealdate() {
        return dealdate;
    }

    /**
     * 交易日
     * @param dealdate 交易日
     */
    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
    }

    /**
     * 交易对手
     * @return TRADE_COUNTER 交易对手
     */
    public String getTradeCounter() {
        return tradeCounter;
    }

    /**
     * 交易对手
     * @param tradeCounter 交易对手
     */
    public void setTradeCounter(String tradeCounter) {
        this.tradeCounter = tradeCounter == null ? null : tradeCounter.trim();
    }

    /**
     * 单位成交价格（全价）
     * @return UNIT_PRICE_FULL 单位成交价格（全价）
     */
    public BigDecimal getUnitPriceFull() {
        return unitPriceFull;
    }

    /**
     * 单位成交价格（全价）
     * @param unitPriceFull 单位成交价格（全价）
     */
    public void setUnitPriceFull(BigDecimal unitPriceFull) {
        this.unitPriceFull = unitPriceFull;
    }

    /**
     * 单位成交价格（净价）
     * @return UNIT_PRICE_NET 单位成交价格（净价）
     */
    public BigDecimal getUnitPriceNet() {
        return unitPriceNet;
    }

    /**
     * 单位成交价格（净价）
     * @param unitPriceNet 单位成交价格（净价）
     */
    public void setUnitPriceNet(BigDecimal unitPriceNet) {
        this.unitPriceNet = unitPriceNet;
    }

    /**
     * 到期收益率
     * @return END_RATE 到期收益率
     */
    public BigDecimal getEndRate() {
        return endRate;
    }

    /**
     * 到期收益率
     * @param endRate 到期收益率
     */
    public void setEndRate(BigDecimal endRate) {
        this.endRate = endRate;
    }

    /**
     * 成交编号/合同号
     * @return CONTRACT_CODE 成交编号/合同号
     */
    public String getContractCode() {
        return contractCode;
    }

    /**
     * 成交编号/合同号
     * @param contractCode 成交编号/合同号
     */
    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 通道代码
     * @return CHL_CPCT_ID 通道代码
     */
    public String getChlCpctId() {
        return chlCpctId;
    }

    /**
     * 通道代码
     * @param chlCpctId 通道代码
     */
    public void setChlCpctId(String chlCpctId) {
        this.chlCpctId = chlCpctId == null ? null : chlCpctId.trim();
    }

    /**
     * 交易类型01 资产交易 02通道交易 03产品费用交易 04其他交易 05资管计划交易  06通道费用交易
     * @return TRADE_TYPE 交易类型01 资产交易 02通道交易 03产品费用交易 04其他交易 05资管计划交易  06通道费用交易
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 交易类型01 资产交易 02通道交易 03产品费用交易 04其他交易 05资管计划交易  06通道费用交易
     * @param tradeType 交易类型01 资产交易 02通道交易 03产品费用交易 04其他交易 05资管计划交易  06通道费用交易
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    /**
     * 通道名称
     * @return CHL_AGRT_NAME 通道名称
     */
    public String getChlAgrtName() {
        return chlAgrtName;
    }

    /**
     * 通道名称
     * @param chlAgrtName 通道名称
     */
    public void setChlAgrtName(String chlAgrtName) {
        this.chlAgrtName = chlAgrtName == null ? null : chlAgrtName.trim();
    }

    /**
     * 是否底层交易流水 01 是/ 02 否
     * @return IS_UNDER_TRADE 是否底层交易流水 01 是/ 02 否
     */
    public String getIsUnderTrade() {
        return isUnderTrade;
    }

    /**
     * 是否底层交易流水 01 是/ 02 否
     * @param isUnderTrade 是否底层交易流水 01 是/ 02 否
     */
    public void setIsUnderTrade(String isUnderTrade) {
        this.isUnderTrade = isUnderTrade == null ? null : isUnderTrade.trim();
    }

    /**
     * 数据状态 D	删除 A	待处理 E	有效
     * @return STATUS 数据状态 D	删除 A	待处理 E	有效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 数据状态 D	删除 A	待处理 E	有效
     * @param status 数据状态 D	删除 A	待处理 E	有效
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 机构代码
     * @return ORG_CODE 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 部门代码
     * @return DEPT_CODE 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新人
     * @return UPDATE_USER 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 更新时间
     * @return UPDATE_TIME 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 数据日期
     * @return STATUS_DATE 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 撤销日
     * @return REV_DATE 撤销日
     */
    public Date getRevDate() {
        return revDate;
    }

    /**
     * 撤销日
     * @param revDate 撤销日
     */
    public void setRevDate(Date revDate) {
        this.revDate = revDate;
    }

    /**
     * 数据来源
     * @return DATA_SOURCE 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 数据来源
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    /**
     * 利率
     * @return RATE 利率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 利率
     * @param rate 利率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     * @return BASIS 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     */
    public String getBasis() {
        return basis;
    }

    /**
     * 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     * @param basis 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     */
    public void setBasis(String basis) {
        this.basis = basis == null ? null : basis.trim();
    }

    /**
     * 交易类型01	部分还本/02	到期-本金/03	到期-利息/04	份额调减/05	份额调增/06	红利再投/07	交易场所费用支出/08	理财托管户收息确认/09	买入/10	卖出/11	其他费用支出/12	其他账户收息确认/13	申购/14	自定义现金流收入/15	自定义现金流支出/16	赎回/17	通道费用支付/18	通道交易-买入/19	通道交易-卖出/20	通道托管（管理）费/21	现金分红/22	银行间费用支出/23	营改增缴纳/24	中间付息/25	中间还本-本金/26	中间还本-利息
     * @return DEALTYPE 交易类型01	部分还本/02	到期-本金/03	到期-利息/04	份额调减/05	份额调增/06	红利再投/07	交易场所费用支出/08	理财托管户收息确认/09	买入/10	卖出/11	其他费用支出/12	其他账户收息确认/13	申购/14	自定义现金流收入/15	自定义现金流支出/16	赎回/17	通道费用支付/18	通道交易-买入/19	通道交易-卖出/20	通道托管（管理）费/21	现金分红/22	银行间费用支出/23	营改增缴纳/24	中间付息/25	中间还本-本金/26	中间还本-利息
     */
    public String getDealtype() {
        return dealtype;
    }

    /**
     * 交易类型01	部分还本/02	到期-本金/03	到期-利息/04	份额调减/05	份额调增/06	红利再投/07	交易场所费用支出/08	理财托管户收息确认/09	买入/10	卖出/11	其他费用支出/12	其他账户收息确认/13	申购/14	自定义现金流收入/15	自定义现金流支出/16	赎回/17	通道费用支付/18	通道交易-买入/19	通道交易-卖出/20	通道托管（管理）费/21	现金分红/22	银行间费用支出/23	营改增缴纳/24	中间付息/25	中间还本-本金/26	中间还本-利息
     * @param dealtype 交易类型01	部分还本/02	到期-本金/03	到期-利息/04	份额调减/05	份额调增/06	红利再投/07	交易场所费用支出/08	理财托管户收息确认/09	买入/10	卖出/11	其他费用支出/12	其他账户收息确认/13	申购/14	自定义现金流收入/15	自定义现金流支出/16	赎回/17	通道费用支付/18	通道交易-买入/19	通道交易-卖出/20	通道托管（管理）费/21	现金分红/22	银行间费用支出/23	营改增缴纳/24	中间付息/25	中间还本-本金/26	中间还本-利息
     */
    public void setDealtype(String dealtype) {
        this.dealtype = dealtype == null ? null : dealtype.trim();
	}

	public Object clone() throws CloneNotSupportedException {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			throw e;
		}
		return obj;
	}

	public BigDecimal getFacevalue() {
		return facevalue;
	}

	public void setFacevalue(BigDecimal facevalue) {
		this.facevalue = facevalue;
	}

	public BigDecimal getIntPayAmt() {
		return intPayAmt;
	}

	public void setIntPayAmt(BigDecimal intPayAmt) {
		this.intPayAmt = intPayAmt;
	}

    public String getfTradeCode() {
        return fTradeCode;
    }

    public void setfTradeCode(String fTradeCode) {
        this.fTradeCode = fTradeCode;
    }

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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
}