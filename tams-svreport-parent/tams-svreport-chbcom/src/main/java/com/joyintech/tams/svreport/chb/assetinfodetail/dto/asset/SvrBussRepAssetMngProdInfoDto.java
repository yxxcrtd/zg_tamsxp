/*
 * 文件名：BussRepAssetMngProdInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：liuwei
 * 修改时间：2018年9月18日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset;


import com.joyintech.framework.base.BaseEntity;


import java.math.BigDecimal;

/**
 * 资产管理计划报送信息组件Dto
 *
 * @author liuwei
 * @version 1.0 2018年9月18日
 * @see SvrBussRepAssetMngProdInfoDto
 * @since 1.0
 */
public class SvrBussRepAssetMngProdInfoDto extends BaseEntity {

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
    /**
     * 资产名称
     */
    private String assetName;
    /**
     * 是否由金融资产投资公司发行
     */
    private String financialComissue;

    /**
     * 资管计划发起人机构编码
     */
    private String partyCode;

    /**
     * 资管计划登记编码
     */
    private String checkCode;

    /**
     * 管理人
     */
    private String managerId;

    /**
     * 托管人
     */
    private String trusteeId;
    /**
     * 金额
     */
    private BigDecimal amt;
    /**
     * 资金实际投向 01 结构性投资类/02 货币市场工具类/03 债券类/04 股票类/05 股权类/06 另类投资类/07 混合类/08 非标准化债权类/09 银行存款类/10 理财直接融资工具类/11 新增可投资资产类/12 代客境外投资类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类/18 同业存单类/19拆放同业及买入返售类
     */
    private String truActType;

    /**
     * 资金运用方式
     */
    private String investType;

    /**
     * 资金运用行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    private String capUseIndu;
    /**
     * 资金运用行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String capUseInduLevel2;
    /**
     * 资管计划属性/委外投资属性 01 集合/02 单一/03 专项/04 定向/05 专户/99 其他
     */
    private String proProperty;
    /**
     * 是否有预期收益率 01是/02否
     */
    private String rateFlag;
    /**
     * 最高预期收益率
     */
    private BigDecimal maxRate;

    /**
     * 最低预期收益率
     */
    private BigDecimal minRate;
    /**
     * 购买结构 01 优先/02 劣后/03 整体
     */
    private String buyStruct;

    /**
     * 管理方式 01 主动（自主管理）/02被动（委托管理）/99其他
     */
    private String manageType;
    /**
     * 管理费率
     */
    private BigDecimal mgrFeeRate;

    /**
     * 托管费率
     */
    private BigDecimal truFeeRate;

    /**
     * 交易相关合计费率
     */
    private BigDecimal tradeFeeRate;

    /**
     * 中介服务机构合计费率
     */
    private BigDecimal agencyFeeRate;

    /**
     * 其他合计费率
     */
    private BigDecimal otherFeeRate;
    /**
     * 是否投向金融资产投资公司发行的私募资产管理产品
     */
    private String privateprdFlag;
    /**
     * 备注
     */
    private String remark;
    /**
     * 新人行资产一级分类
     */
    private String pbcAssetType1;

    /**
     * 新人行资产二级分类
     */
    private String pbcAssetType2;

    /**
     * 新人行资产三级分类
     */
    private String pbcAssetType3;
    
    /**
     * 管理人代码
     */
    private String chbManagerId;
	
	/**
     * 是否为银行理财产品，01 是/02 否，中债新增字段
     */
    private String financialProductFlag;

    /**
     * 产品登记编码，中债新增字段
     */
    private String prodRegisterCode;

    /**
     * 公共PPP
     */
    private String pppflag;

    /**
     * 是否投向市场化债转股相关产品的部分
     */
    private String bondtostockFlag;

    public String getPppflag() {
        return pppflag;
    }

    public void setPppflag(String pppflag) {
        this.pppflag = pppflag;
    }

    public String getBondtostockFlag() {
        return bondtostockFlag;
    }

    public void setBondtostockFlag(String bondtostockFlag) {
        this.bondtostockFlag = bondtostockFlag;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getFinancialComissue() {
        return financialComissue;
    }

    public void setFinancialComissue(String financialComissue) {
        this.financialComissue = financialComissue;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getTrusteeId() {
        return trusteeId;
    }

    public void setTrusteeId(String trusteeId) {
        this.trusteeId = trusteeId;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getTruActType() {
        return truActType;
    }

    public void setTruActType(String truActType) {
        this.truActType = truActType;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getCapUseIndu() {
        return capUseIndu;
    }

    public void setCapUseIndu(String capUseIndu) {
        this.capUseIndu = capUseIndu;
    }

    public String getCapUseInduLevel2() {
        return capUseInduLevel2;
    }

    public void setCapUseInduLevel2(String capUseInduLevel2) {
        this.capUseInduLevel2 = capUseInduLevel2;
    }

    public String getProProperty() {
        return proProperty;
    }

    public void setProProperty(String proProperty) {
        this.proProperty = proProperty;
    }

    public String getRateFlag() {
        return rateFlag;
    }

    public void setRateFlag(String rateFlag) {
        this.rateFlag = rateFlag;
    }

    public BigDecimal getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(BigDecimal maxRate) {
        this.maxRate = maxRate;
    }

    public BigDecimal getMinRate() {
        return minRate;
    }

    public void setMinRate(BigDecimal minRate) {
        this.minRate = minRate;
    }

    public String getBuyStruct() {
        return buyStruct;
    }

    public void setBuyStruct(String buyStruct) {
        this.buyStruct = buyStruct;
    }

    public String getManageType() {
        return manageType;
    }

    public void setManageType(String manageType) {
        this.manageType = manageType;
    }

    public BigDecimal getMgrFeeRate() {
        return mgrFeeRate;
    }

    public void setMgrFeeRate(BigDecimal mgrFeeRate) {
        this.mgrFeeRate = mgrFeeRate;
    }

    public BigDecimal getTruFeeRate() {
        return truFeeRate;
    }

    public void setTruFeeRate(BigDecimal truFeeRate) {
        this.truFeeRate = truFeeRate;
    }

    public BigDecimal getTradeFeeRate() {
        return tradeFeeRate;
    }

    public void setTradeFeeRate(BigDecimal tradeFeeRate) {
        this.tradeFeeRate = tradeFeeRate;
    }

    public BigDecimal getAgencyFeeRate() {
        return agencyFeeRate;
    }

    public void setAgencyFeeRate(BigDecimal agencyFeeRate) {
        this.agencyFeeRate = agencyFeeRate;
    }

    public BigDecimal getOtherFeeRate() {
        return otherFeeRate;
    }

    public void setOtherFeeRate(BigDecimal otherFeeRate) {
        this.otherFeeRate = otherFeeRate;
    }

    public String getPrivateprdFlag() {
        return privateprdFlag;
    }

    public void setPrivateprdFlag(String privateprdFlag) {
        this.privateprdFlag = privateprdFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPbcAssetType1() {
        return pbcAssetType1;
    }

    public void setPbcAssetType1(String pbcAssetType1) {
        this.pbcAssetType1 = pbcAssetType1;
    }

    public String getPbcAssetType2() {
        return pbcAssetType2;
    }

    public void setPbcAssetType2(String pbcAssetType2) {
        this.pbcAssetType2 = pbcAssetType2;
    }

    public String getPbcAssetType3() {
        return pbcAssetType3;
    }

    public void setPbcAssetType3(String pbcAssetType3) {
        this.pbcAssetType3 = pbcAssetType3;
    }
    
    public String getChbManagerId() {
		return chbManagerId;
	}

	public void setChbManagerId(String chbManagerId) {
		this.chbManagerId = chbManagerId;
	}
	
	public String getFinancialProductFlag() {
        return financialProductFlag;
    }

    public void setFinancialProductFlag(String financialProductFlag) {
        this.financialProductFlag = financialProductFlag;
    }

    public String getProdRegisterCode() {
        return prodRegisterCode;
    }

    public void setProdRegisterCode(String prodRegisterCode) {
        this.prodRegisterCode = prodRegisterCode;
    }
}
