/*
 * 文件名：EtrBondFirstLBidDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：yangjm
 * 修改时间：2019年5月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.entrust.etrbondfirstlbidding.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.annotation.NotEmpty;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.EtrEntrust;
import com.joyintech.tams.common.base.entity.SysUploadFileInfo;
import com.joyintech.tams.common.base.entity.EtrEntrustBidPrice;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.invest.common.constant.BussinessDictCode;
import com.joyintech.tams.invest.common.risktrial.dto.RiskTrialRetDto;

/**
 * 债券一级投标委托dto 〈功能详细描述〉
 *
 * @author yangjm
 * @version 1.0 2019年5月6日
 * @see EtrBondFirstLBiddingDto
 * @since 1.0
 */
public class EtrBondFirstLBiddingDto extends EtrEntrust {


    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -5106366412674252223L;
    /**
     * 缴款日期
     */
    private Date paymentDate;

    /**
     * 上市日期
     */
    private Date listingDate;

    /**
     * 主承销商
     */
    private String mainConsignee;

    /**
     * 担保人
     */
    private String warrantor;

    /**
     * 投标区间%
     */
    private String biddingRange;

    /**
     * 资产二级分类（原资产一级分类，委托页面的债券一级分类，修改：王贵奇，2019/9/27）
     */
    private String assetType1;
    /**
     * 新增个性分类三、四。王贵奇，2019/9/27 开始
     * 个性分类三，对应资产三级分类
     */
    private String assetType10;
    /**
     * 个性分类四，对应资产四级分类
     */
    private String assetType11;
    /** 新增个性分类三、四。王贵奇，2019/9/27 结束**/

    /**
     * 预计委托可用现金
     */
    private BigDecimal expectAvailableCash;

    /**
     * 文件上传dto
     */
    private List<SysUploadFileInfo> fileArray;
    /**
     * 市场代码
     */
    private String finprodMarketId;

    /**
     * 投资人员信息DTO
     */
    @Valid
    private BussEtrInvestPersonInfoDto etrInvestPersonInfo;



    /**
     * 接收状态
     */
    private String recStatus;

    /**
     * 指定交易员
     */
    private String trader;

    /**
     * 委托关闭方式
     */
    private String CloseWay;

    /**
     * 选中的数据
     */
    private transient List<EtrBondFirstLBiddingDto> selectedItem;
    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 组合名称
     */
    private String portfolioName;


    /**
     * 金融产品全称
     */
    private String finprodName;


    /**
     * 金融产品类型（投管分类），债券、理财产品、基金、理财产品模板、理财产品分层
     */
    private String finprodType2;
    /**
     * 缴款方式
     */
    private String etrPayType;

    /**
     * 风险试算实体类
     */
    private List<RiskTrialRetDto> riskTrialRetDto;


    /**
     * 投标券面总额
     */
    private BigDecimal bidPriceTotal;

    /**
     * 中标券面总额
     */
    private BigDecimal winPriceTotal;

    /**
     * 中标状态
     */
    private String isWin;

    /**
     * 投标标位
     */
    private String bidPrice;

    /**
     * 投标券面
     */
    private BigDecimal bidPrinAmt;
    /**
     * 投标标位表dto
     */
    private List<EtrEntrustBidPrice> bidPriceList;

    /**
     * 中标标位
     */
    private BigDecimal winPrice;
    private String winPriceStr;

    /**
     * 中标券面
     */
    private BigDecimal winPrinAmt;

    //add by lishuping 20191030 start
    /**
     * 资产简称
     */
    private String finprodAbbr;

    /**
     * 父页面的parentPageId，用于区分字典值控制字段
     */
    private String parentPageId;

    public String getParentPageId() {
        return parentPageId;
    }

    public void setParentPageId(String parentPageId) {
        this.parentPageId = parentPageId;
    }
    /**
     *业务模式
     */
    private String businessModel;

    /**
     * finprodAbbr
     * @return finprodAbbr
     */
    public String getFinprodAbbr() {
        return finprodAbbr;
    }

    /**
     * finprodAbbr
     * @param finprodAbbr finprodAbbr
     */
    public void setFinprodAbbr(String finprodAbbr) {
        this.finprodAbbr = finprodAbbr;
    }
    //add by lishuping 20191030 end

    /**
     * 获取 assetType1
     * @return assetType1.
     */
    public String getAssetType1() {
        return assetType1;
    }
    /**
     * 设置 assetType1
     * @param assetType1 assetType1
     */
    public void setAssetType1(String assetType1) {
        this.assetType1 = assetType1;
    }

    /**
     * 新增个性分类三、四getters和setters。王贵奇，2019/9/27 开始
     * 获取 assetType10
     * @return assetType10.
     */
    public String getAssetType10() {
        return assetType10;
    }
    /**
     * 设置 assetType10
     * @param assetType10 assetType10
     */
    public void setAssetType10(String assetType10) {
        this.assetType10 = assetType10;
    }
    /**
     * 获取 assetType11
     * @return assetType11.
     */
    public String getAssetType11() {
        return assetType11;
    }
    /**
     * 设置 assetType11
     * @param assetType11 assetType11
     */
    public void setAssetType11(String assetType11) {
        this.assetType11 = assetType11;
    }
    /** 新增个性分类三、四getters和setters。王贵奇，2019/9/27 结束 **/

    /**
     * 获取 fileArray
     * @return fileArray.
     */
    public List<SysUploadFileInfo> getFileArray() {
        return fileArray;
    }

    /**
     * 设置 fileArray
     * @param fileArray fileArray
     */
    public void setFileArray(List<SysUploadFileInfo> fileArray) {
        this.fileArray = fileArray;
    }

    /**
     * 获取 finprodMarketId
     * @return finprodMarketId.
     */
    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    /**
     * 设置 finprodMarketId
     * @param finprodMarketId finprodMarketId
     */
    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    /**
     * 获取 etrInvestPersonInfo
     * @return etrInvestPersonInfo.
     */
    public BussEtrInvestPersonInfoDto getEtrInvestPersonInfo() {
        return etrInvestPersonInfo;
    }

    /**
     * 设置 etrInvestPersonInfo
     * @param etrInvestPersonInfo etrInvestPersonInfo
     */
    public void setEtrInvestPersonInfo(BussEtrInvestPersonInfoDto etrInvestPersonInfo) {
        this.etrInvestPersonInfo = etrInvestPersonInfo;
    }

    /**
     * 获取 recStatus
     * @return recStatus.
     */
    public String getRecStatus() {
        return recStatus;
    }

    /**
     * 设置 recStatus
     * @param recStatus recStatus
     */
    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    /**
     * 获取 trader
     * @return trader.
     */
    public String getTrader() {
        return trader;
    }

    /**
     * 设置 trader
     * @param trader trader
     */
    public void setTrader(String trader) {
        this.trader = trader;
    }

    /**
     * 获取 closeWay
     * @return closeWay.
     */
    public String getCloseWay() {
        return CloseWay;
    }

    /**
     * 设置 closeWay
     * @param closeWay closeWay
     */
    public void setCloseWay(String closeWay) {
        CloseWay = closeWay;
    }

    /**
     * 获取 selectedItem
     * @return selectedItem.
     */
    public List<EtrBondFirstLBiddingDto> getSelectedItem() {
        return selectedItem;
    }

    /**
     * 设置 selectedItem
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<EtrBondFirstLBiddingDto> selectedItem) {
        this.selectedItem = selectedItem;
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
     * 获取 finprodType2
     * @return finprodType2.
     */
    public String getFinprodType2() {
        return finprodType2;
    }

    /**
     * 设置 finprodType2
     * @param finprodType2 finprodType2
     */
    public void setFinprodType2(String finprodType2) {
        this.finprodType2 = finprodType2;
    }


    /**
     * 获取 bidPriceTotal
     * @return bidPriceTotal.
     */
    public BigDecimal getBidPriceTotal() {
        return bidPriceTotal;
    }

    /**
     * 设置 bidPriceTotal
     * @param bidPriceTotal bidPriceTotal
     */
    public void setBidPriceTotal(BigDecimal bidPriceTotal) {
        this.bidPriceTotal = bidPriceTotal;
    }

    /**
     * 获取 winPriceTotal
     * @return winPriceTotal.
     */
    public BigDecimal getWinPriceTotal() {
        return winPriceTotal;
    }

    /**
     * 设置 winPriceTotal
     * @param winPriceTotal winPriceTotal
     */
    public void setWinPriceTotal(BigDecimal winPriceTotal) {
        this.winPriceTotal = winPriceTotal;
    }
    /**
     * 获取 etrPayType
     * @return etrPayType.
     */
    public String getEtrPayType() {
        return etrPayType;
    }
    /**
     * 设置 etrPayType
     * @param etrPayType etrPayType
     */
    public void setEtrPayType(String etrPayType) {
        this.etrPayType = etrPayType;
    }
    /**
     * 获取 expectAvailableCash
     * @return expectAvailableCash.
     */
    public BigDecimal getExpectAvailableCash() {
        return expectAvailableCash;
    }
    /**
     * 设置 expectAvailableCash
     * @param expectAvailableCash expectAvailableCash
     */
    public void setExpectAvailableCash(BigDecimal expectAvailableCash) {
        this.expectAvailableCash = expectAvailableCash;
    }
    /**
     * 获取 riskTrialRetDto
     * @return riskTrialRetDto.
     */
    public List<RiskTrialRetDto> getRiskTrialRetDto() {
        return riskTrialRetDto;
    }
    /**
     * 设置 riskTrialRetDto
     * @param riskTrialRetDto riskTrialRetDto
     */
    public void setRiskTrialRetDto(List<RiskTrialRetDto> riskTrialRetDto) {
        this.riskTrialRetDto = riskTrialRetDto;
    }

    /**
     * 获取 isWin
     * @return isWin.
     */
    public String getIsWin() {
        return isWin;
    }

    /**
     * 设置 isWin
     * @param isWin isWin
     */
    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    /**
     * 获取 bidPrice
     * @return bidPrice.
     */
    public String getBidPrice() {
        return bidPrice;
    }

    /**
     * 设置 bidPrice
     * @param bidPrice bidPrice
     */
    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

    /**
     * 获取 bidPrinAmt
     * @return bidPrinAmt.
     */
    public BigDecimal getBidPrinAmt() {
        return bidPrinAmt;
    }

    /**
     * 设置 bidPrinAmt
     * @param bidPrinAmt bidPrinAmt
     */
    public void setBidPrinAmt(BigDecimal bidPrinAmt) {
        this.bidPrinAmt = bidPrinAmt;
    }

    /**
     * 获取 winPrice
     * @return winPrice.
     */
    public BigDecimal getWinPrice() {
        return winPrice;
    }

    /**
     * 设置 winPrice
     * @param winPrice winPrice
     */
    public void setWinPrice(BigDecimal winPrice) {
        this.winPrice = winPrice;
    }
    /**
     * 获取 winPrinAmt
     * @return winPrinAmt.
     */
    public BigDecimal getWinPrinAmt() {
        return winPrinAmt;
    }
    /**
     * 设置 winPrinAmt
     * @param winPrinAmt winPrinAmt
     */
    public void setWinPrinAmt(BigDecimal winPrinAmt) {
        this.winPrinAmt = winPrinAmt;
    }
    /**
     * 获取 bidPriceList
     * @return bidPriceList.
     */
    public List<EtrEntrustBidPrice> getBidPriceList() {
        return bidPriceList;
    }
    /**
     * 设置 bidPriceList
     * @param bidPriceList bidPriceList
     */
    public void setBidPriceList(List<EtrEntrustBidPrice> bidPriceList) {
        this.bidPriceList = bidPriceList;
    }
    /**
     * 获取 winPriceStr
     * @return winPriceStr.
     */
    public String getWinPriceStr() {
        return winPriceStr;
    }
    /**
     * 设置 winPriceStr
     * @param winPriceStr winPriceStr
     */
    public void setWinPriceStr(String winPriceStr) {
        this.winPriceStr = winPriceStr;
    }


    /**
     * 获取 缴款日期
     *
     * @return paymentDate 缴款日期
     */
    public Date getPaymentDate() {
        return this.paymentDate;
    }

    /**
     * 设置 缴款日期
     *
     * @param paymentDate 缴款日期
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * 获取 上市日期
     *
     * @return listingDate 上市日期
     */
    public Date getListingDate() {
        return this.listingDate;
    }

    /**
     * 设置 上市日期
     *
     * @param listingDate 上市日期
     */
    public void setListingDate(Date listingDate) {
        this.listingDate = listingDate;
    }

    /**
     * 获取 主承销商
     *
     * @return mainConsignee 主承销商
     */
    public String getMainConsignee() {
        return this.mainConsignee;
    }

    /**
     * 设置 主承销商
     *
     * @param mainConsignee 主承销商
     */
    public void setMainConsignee(String mainConsignee) {
        this.mainConsignee = mainConsignee;
    }

    /**
     * 获取 担保人
     *
     * @return warrantor 担保人
     */
    public String getWarrantor() {
        return this.warrantor;
    }

    /**
     * 设置 担保人
     *
     * @param warrantor 担保人
     */
    public void setWarrantor(String warrantor) {
        this.warrantor = warrantor;
    }

    /**
     * 获取 投标区间%
     *
     * @return biddingRange 投标区间%
     */
    public String getBiddingRange() {
        return this.biddingRange;
    }

    /**
     * 设置 投标区间%
     *
     * @param biddingRange 投标区间%
     */
    public void setBiddingRange(String biddingRange) {
        this.biddingRange = biddingRange;
    }

    public String getBusinessModel() {
        return businessModel;
    }

    public void setBusinessModel(String businessModel) {
        this.businessModel = businessModel;
    }

    private String nextUser;

    public String getNextUser() {
        return nextUser;
    }

    public void setNextUser(String nextUser) {
        this.nextUser = nextUser;
    }

    /*********************** 下面是指令分发明细 使用的字段 **************************************/
    //资产二级分类
    private String assetType1Value;
    //资产三级分类
    private String assetType10Value;
    //资产四级分类
    private String assetType11Value;
    //币种
    private String ccyValue;
    //交易场所
    private String tradeMarketValue;
    //缴款方式
    private String etrPayTypeValue;
    //投资目的
    private String invAimValue;
    //主承销商
    private String mainConsigneeValue;
    private BigDecimal proAvailableCash1;
    private BigDecimal proAvailableCash;

    public String getAssetType1Value() {
        return SysDataUtil.getNameFromDataDict(DictCode.行内资产二级分类,this.getAssetType1());
    }

    public void setAssetType1Value(String assetType1Value) {
        this.assetType1Value = assetType1Value;
    }

    public String getAssetType10Value() {
        return SysDataUtil.getNameFromDataDict(DictCode.行内资产三级分类,this.getAssetType10());
    }

    public void setAssetType10Value(String assetType10Value) {
        this.assetType10Value = assetType10Value;
    }

    public String getAssetType11Value() {
        return SysDataUtil.getNameFromDataDict(DictCode.行内资产四级分类,this.getAssetType11());
    }

    public void setAssetType11Value(String assetType11Value) {
        this.assetType11Value = assetType11Value;
    }

    public String getCcyValue() {
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.币种,this.getCcy());
    }

    public void setCcyValue(String ccyValue) {
        this.ccyValue = ccyValue;
    }

    public String getTradeMarketValue() {
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.交易场所,this.getTradeMarket());
    }

    public void setTradeMarketValue(String tradeMarketValue) {
        this.tradeMarketValue = tradeMarketValue;
    }

    public String getEtrPayTypeValue() {
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.缴款方式,this.getEtrPayType());
    }

    public void setEtrPayTypeValue(String etrPayTypeValue) {
        this.etrPayTypeValue = etrPayTypeValue;
    }

    public String getInvAimValue() {
        return SysDataUtil.getNameFromDataDict(BussinessDictCode.投资目的,this.getInvAim());
    }

    public void setInvAimValue(String invAimValue) {
        this.invAimValue = invAimValue;
    }

    public String getMainConsigneeValue() {
        return this.mainConsigneeValue;
    }

    public void setMainConsigneeValue(String mainConsigneeValue) {
        this.mainConsigneeValue = mainConsigneeValue;
    }

    public BigDecimal getProAvailableCash1() {
        return proAvailableCash1;
    }

    public void setProAvailableCash1(BigDecimal proAvailableCash1) {
        this.proAvailableCash1 = proAvailableCash1;
    }

    @Override
    public BigDecimal getProAvailableCash() {
        return proAvailableCash;
    }

    @Override
    public void setProAvailableCash(BigDecimal proAvailableCash) {
        this.proAvailableCash = proAvailableCash;
    }
}
