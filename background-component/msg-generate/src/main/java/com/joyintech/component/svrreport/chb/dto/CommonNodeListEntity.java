/*
 * 文件名：CommonNodeListEntity.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：刘蓉蓉
 * 修改时间：2016年11月7日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */
package com.joyintech.component.svrreport.chb.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;


/**
 * 业务数据共通子节点Entity
 * 
 * @author 刘溶容
 * @version 1.0 2016年11月7日
 * @see CommonNodeListEntity
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CommonNodeListEntity implements Feild {

    // @XmlAttribute(name="contextRef")
    // private String contextRef;
    /**
     * 产品投资国家或地区（境外）
     */
    @XmlElement(name = "wemax:SpecificCountryOrRegion")
    private CommonNodeEntity specificCountryOrRegion;
    
    /**
     * 登记业务分类
     */
    @XmlElement(name = "wemax:RegistrationClassification")
    private CommonNodeEntity registrationClassification;
    
    /**
     * 登记人员类别
     */
    @XmlElement(name = "wemax:TypeOfRegistrar")
    private CommonNodeEntity typeOfRegistrar;

    /**
     * 结构性产品挂钩标的
     */
    @XmlElement(name = "wemax:StructuredProductUnderlyingAsset")
    private CommonNodeEntity structuredProductUnderlyingAsset;

    /**
     * 产品销售区域
     */
    @XmlElement(name = "wemax:ProductSalesRegion")
    private CommonNodeEntity productSalesRegion;
    /**
     * 募集币种
     */
    @XmlElement(name = "wemax:FundingCurrency")
    private CommonNodeEntity fundingCurrency;
    /**
     * 兑付本金币种
     */
    @XmlElement(name = "wemax:PrincipalPaymentCurrency")
    private CommonNodeEntity principalPaymentCurrency;
    /**
     * 兑付收益币种
     */
    @XmlElement(name = "wemax:IncomePaymentCurrency")
    private CommonNodeEntity incomePaymentCurrency;

    /**
     * 境外托管机构国别
     */
    @XmlElement(name = "wemax:OverseasCustodianNationality")
    private CommonNodeEntity overseasCustodianNationality;

    /**
     * 投资者风险偏好
     */
    @XmlElement(name = "wemax:InvestorRiskPreference")
    private CommonNodeEntity investorRiskPreference;

    /**
     * 产品增信机构类型
     */
    @XmlElement(name = "wemax:TypeOfProductCreditEnhancementInstitution")
    private CommonNodeEntity typeOfProductCreditEnhancementInstitution;

    /**
     * 报告主文件
     */
    @XmlElement(name = "wemax:MainDocuments")
    private CommonNodeEntity mainDocuments;

    /**
     * 理财产品可行性评估报告
     */
    @XmlElement(name = "wemax:WealthManagementProductFeasibilityReports")
    private CommonNodeEntity wealthManagementProductFeasibilityReports;

    /**
     * 内部审核文件
     */
    @XmlElement(name = "wemax:InternalAuthorizedDocuments")
    private CommonNodeEntity internalAuthorizedDocuments;

    /**
     * 对理财产品投资管理人、托管人、投资顾问等相关方的尽职调查文件
     */
    @XmlElement(name = "wemax:DueDiligenceDocumentsOnRelevantParties")
    private CommonNodeEntity dueDiligenceDocumentsOnRelevantParties;

    /**
     * 与理财产品投资管理人、托管人、投资顾问等相关方签署的法律文件
     */
    @XmlElement(name = "wemax:LegalDocumentsSignedByRelevantParties")
    private CommonNodeEntity legalDocumentsSignedByRelevantParties;

    /**
     * 理财产品销售文件
     */
    @XmlElement(name = "wemax:WealthManagementProductSalesDocuments")
    private CommonNodeEntity wealthManagementProductSalesDocuments;

    /**
     * 理财产品说明书
     */
    @XmlElement(name = "wemax:WealthManagementProductSpecifications")
    private CommonNodeEntity wealthManagementProductSpecifications;

    /**
     * 理财产品宣传材料
     */
    @XmlElement(name = "wemax:WealthManagementProductMarketingDocuments")
    private CommonNodeEntity wealthManagementProductMarketingDocuments;

    /**
     * 其他材料
     */
    @XmlElement(name = "wemax:OtherDocuments")
    private CommonNodeEntity otherDocuments;

    /**
     * 区域募集金额
     */
    @XmlElement(name = "wemax:SubscriptionAmountByRegion")
    private CommonNodeEntity subscriptionAmountByRegion;

    /**
     * 认购金额
     */
    @XmlElement(name = "wemax:SubscriptionAmount")
    private CommonNodeEntity subscriptionAmount;

    /**
     * 折算人民币金额
     */
    @XmlElement(name = "wemax:SubscriptionAmountCNY")
    private CommonNodeEntity subscriptionAmountCNY;

    /**
     * 币种
     */
    @XmlElement(name = "wemax:Currency")
    private CommonNodeEntity currency;

    /**
     * 该币种累计申购金额
     */
    @XmlElement(name = "wemax:SubscribedAmountOverLatestSubscriptionPeriodCNY")
    private CommonNodeEntity subscribedAmountOverLatestSubscriptionPeriodCNY;

    /**
     * 该币种累计兑付金额
     */
    @XmlElement(name = "wemax:RedeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY")
    private CommonNodeEntity redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY;

    /**
     * 该币种累计兑付收益金额
     */
    @XmlElement(name = "wemax:RedeemedInterestPaymentOverLatestSubscriptionPeriodCNY")
    private CommonNodeEntity redeemedInterestPaymentOverLatestSubscriptionPeriodCNY;

    /**
     * 结构性存款挂钩标的类别
     */
    @XmlElement(name = "wemax:UnderlyingAssetTypeOfStructuredDeposits")
    private CommonNodeEntity underlyingAssetTypeOfStructuredDeposits;

    /**
     * 标的类别
     */
    @XmlElement(name = "wemax:UnderlyingAssetType")
    private CommonNodeEntity underlyingAssetType;

    /**
     * 底层资产行内资产/负债编码
     */
    @XmlElement(name = "wemax:AssetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities")
    private CommonNodeEntity assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities;

    /**
     * 底层资产持仓数量
     */
    @XmlElement(name = "wemax:QuantityOfUnderlyingAssetsLiabilities")
    private CommonNodeEntity quantityOfUnderlyingAssetsLiabilities;

    /**
     * 底层资产折算人民币市值
     */
    @XmlElement(name = "wemax:MarketValueOfUnderlyingAssetsLiabilitiesCNY")
    private CommonNodeEntity marketValueOfUnderlyingAssetsLiabilitiesCNY;

    /**
     * 登记流水号
     */
    @XmlElement(name = "wemax:RegistrationSerialNumber")
    private CommonNodeEntity registrationSerialNumber;

    /**
     * 理财专业证书
     */
    @XmlElement(name = "wemax:WealthManagementCertification")
    private CommonNodeEntity wealthManagementCertification;

    /**
     * 登记银行代码
     */
    @XmlElement(name = "wemax:RegistrationBankCode")
    private CommonNodeEntity registrationBankCode;

    /**
     * 分类总量登记明细
     */
    @XmlElement(name = "wemax:InvestorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple")
    private List<CommonNodeEntity> investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple;

    /**
     * 份额
     */
    @XmlElement(name = "wemax:Units")
    private CommonNodeEntity units;

    /**
     * 明细避暑
     */
    @XmlElement(name = "wemax:NumberOfTransactions")
    private CommonNodeEntity numberOfTransactions;

    /**
     * 业务种类
     */
    @XmlElement(name = "wemax:BusinessType")
    private CommonNodeEntity businessType;


    /****1217升级**/




    @XmlElement(name = "wemax:ProductSpecialPropertyTuple")
    private CommonNodeEntity productSpecialPropertyTuple;
    /**
     * 产品特殊属性
     */
    @XmlElement(name = "wemax:ProductSpecialProperty")
    private CommonNodeEntity productSpecialProperty;

    /****1217升级**/

    // /**
    // * 获取 contextRef
    // * @return contextRef.
    // */
    // public String getContextRef() {
    // return contextRef;
    // }
    //
    //
    // /**
    // * 设置 contextRef
    // * @param contextRef contextRef
    // */
    // public void setContextRef(String contextRef) {
    // this.contextRef = contextRef;
    // }

    /**
     * 获取 specificCountryOrRegion
     * 
     * @return specificCountryOrRegion.
     */
    public CommonNodeEntity getSpecificCountryOrRegion() {
        return specificCountryOrRegion;
    }

    /**
     * 设置 specificCountryOrRegion
     * 
     * @param specificCountryOrRegion specificCountryOrRegion
     */
    public void setSpecificCountryOrRegion(CommonNodeEntity specificCountryOrRegion) {
        this.specificCountryOrRegion = specificCountryOrRegion;
    }

    /**
     * 获取 productSalesRegion
     * 
     * @return productSalesRegion.
     */
    public CommonNodeEntity getProductSalesRegion() {
        return productSalesRegion;
    }

    /**
     * 设置 productSalesRegion
     * 
     * @param productSalesRegion productSalesRegion
     */
    public void setProductSalesRegion(CommonNodeEntity productSalesRegion) {
        this.productSalesRegion = productSalesRegion;
    }

    /**
     * 获取 overseasCustodianNationality
     * 
     * @return overseasCustodianNationality.
     */
    public CommonNodeEntity getOverseasCustodianNationality() {
        return overseasCustodianNationality;
    }

    /**
     * 设置 overseasCustodianNationality
     * 
     * @param overseasCustodianNationality overseasCustodianNationality
     */
    public void setOverseasCustodianNationality(CommonNodeEntity overseasCustodianNationality) {
        this.overseasCustodianNationality = overseasCustodianNationality;
    }

    /**
     * 获取 investorRiskPreference
     * 
     * @return investorRiskPreference.
     */
    public CommonNodeEntity getInvestorRiskPreference() {
        return investorRiskPreference;
    }

    /**
     * 设置 investorRiskPreference
     * 
     * @param investorRiskPreference investorRiskPreference
     */
    public void setInvestorRiskPreference(CommonNodeEntity investorRiskPreference) {
        this.investorRiskPreference = investorRiskPreference;
    }

    /**
     * 获取 typeOfProductCreditEnhancementInstitution
     * 
     * @return typeOfProductCreditEnhancementInstitution.
     */
    public CommonNodeEntity getTypeOfProductCreditEnhancementInstitution() {
        return typeOfProductCreditEnhancementInstitution;
    }

    /**
     * 设置 typeOfProductCreditEnhancementInstitution
     * 
     * @param typeOfProductCreditEnhancementInstitution typeOfProductCreditEnhancementInstitution
     */
    public void setTypeOfProductCreditEnhancementInstitution(CommonNodeEntity typeOfProductCreditEnhancementInstitution) {
        this.typeOfProductCreditEnhancementInstitution = typeOfProductCreditEnhancementInstitution;
    }

    /**
     * 获取 mainDocuments
     * 
     * @return mainDocuments.
     */
    public CommonNodeEntity getMainDocuments() {
        return mainDocuments;
    }

    /**
     * 设置 mainDocuments
     * 
     * @param mainDocuments mainDocuments
     */
    public void setMainDocuments(CommonNodeEntity mainDocuments) {
        this.mainDocuments = mainDocuments;
    }

    /**
     * 获取 wealthManagementProductFeasibilityReports
     * 
     * @return wealthManagementProductFeasibilityReports.
     */
    public CommonNodeEntity getWealthManagementProductFeasibilityReports() {
        return wealthManagementProductFeasibilityReports;
    }

    /**
     * 设置 wealthManagementProductFeasibilityReports
     * 
     * @param wealthManagementProductFeasibilityReports wealthManagementProductFeasibilityReports
     */
    public void setWealthManagementProductFeasibilityReports(CommonNodeEntity wealthManagementProductFeasibilityReports) {
        this.wealthManagementProductFeasibilityReports = wealthManagementProductFeasibilityReports;
    }

    /**
     * 获取 internalAuthorizedDocuments
     * 
     * @return internalAuthorizedDocuments.
     */
    public CommonNodeEntity getInternalAuthorizedDocuments() {
        return internalAuthorizedDocuments;
    }

    /**
     * 设置 internalAuthorizedDocuments
     * 
     * @param internalAuthorizedDocuments internalAuthorizedDocuments
     */
    public void setInternalAuthorizedDocuments(CommonNodeEntity internalAuthorizedDocuments) {
        this.internalAuthorizedDocuments = internalAuthorizedDocuments;
    }

    /**
     * 获取 dueDiligenceDocumentsOnRelevantParties
     * 
     * @return dueDiligenceDocumentsOnRelevantParties.
     */
    public CommonNodeEntity getDueDiligenceDocumentsOnRelevantParties() {
        return dueDiligenceDocumentsOnRelevantParties;
    }

    /**
     * 设置 dueDiligenceDocumentsOnRelevantParties
     * 
     * @param dueDiligenceDocumentsOnRelevantParties dueDiligenceDocumentsOnRelevantParties
     */
    public void setDueDiligenceDocumentsOnRelevantParties(CommonNodeEntity dueDiligenceDocumentsOnRelevantParties) {
        this.dueDiligenceDocumentsOnRelevantParties = dueDiligenceDocumentsOnRelevantParties;
    }

    /**
     * 获取 legalDocumentsSignedByRelevantParties
     * 
     * @return legalDocumentsSignedByRelevantParties.
     */
    public CommonNodeEntity getLegalDocumentsSignedByRelevantParties() {
        return legalDocumentsSignedByRelevantParties;
    }

    /**
     * 设置 legalDocumentsSignedByRelevantParties
     * 
     * @param legalDocumentsSignedByRelevantParties legalDocumentsSignedByRelevantParties
     */
    public void setLegalDocumentsSignedByRelevantParties(CommonNodeEntity legalDocumentsSignedByRelevantParties) {
        this.legalDocumentsSignedByRelevantParties = legalDocumentsSignedByRelevantParties;
    }

    /**
     * 获取 walthManagementProductSalesDocuments
     * 
     * @return walthManagementProductSalesDocuments.
     */
    public CommonNodeEntity getWealthManagementProductSalesDocuments() {
        return wealthManagementProductSalesDocuments;
    }

    /**
     * 设置 walthManagementProductSalesDocuments
     * 
     * @param wealthManagementProductSalesDocuments walthManagementProductSalesDocuments
     */
    public void setWealthManagementProductSalesDocuments(CommonNodeEntity wealthManagementProductSalesDocuments) {
        this.wealthManagementProductSalesDocuments = wealthManagementProductSalesDocuments;
    }

    /**
     * 获取 wealthManagementProductSpecifications
     * 
     * @return wealthManagementProductSpecifications.
     */
    public CommonNodeEntity getWealthManagementProductSpecifications() {
        return wealthManagementProductSpecifications;
    }

    /**
     * 设置 wealthManagementProductSpecifications
     * 
     * @param wealthManagementProductSpecifications wealthManagementProductSpecifications
     */
    public void setWealthManagementProductSpecifications(CommonNodeEntity wealthManagementProductSpecifications) {
        this.wealthManagementProductSpecifications = wealthManagementProductSpecifications;
    }

    /**
     * 获取 wealthManagementProductMarketingDocuments
     * 
     * @return wealthManagementProductMarketingDocuments.
     */
    public CommonNodeEntity getWealthManagementProductMarketingDocuments() {
        return wealthManagementProductMarketingDocuments;
    }

    /**
     * 设置 wealthManagementProductMarketingDocuments
     * 
     * @param wealthManagementProductMarketingDocuments wealthManagementProductMarketingDocuments
     */
    public void setWealthManagementProductMarketingDocuments(CommonNodeEntity wealthManagementProductMarketingDocuments) {
        this.wealthManagementProductMarketingDocuments = wealthManagementProductMarketingDocuments;
    }

    /**
     * 获取 otherDocuments
     * 
     * @return otherDocuments.
     */
    public CommonNodeEntity getOtherDocuments() {
        return otherDocuments;
    }

    /**
     * 设置 otherDocuments
     * 
     * @param otherDocuments otherDocuments
     */
    public void setOtherDocuments(CommonNodeEntity otherDocuments) {
        this.otherDocuments = otherDocuments;
    }

    /**
     * 获取 structuredProductUnderlyingAsset
     * 
     * @return structuredProductUnderlyingAsset.
     */
    public CommonNodeEntity getStructuredProductUnderlyingAsset() {
        return structuredProductUnderlyingAsset;
    }

    /**
     * 设置 structuredProductUnderlyingAsset
     * 
     * @param structuredProductUnderlyingAsset structuredProductUnderlyingAsset
     */
    public void setStructuredProductUnderlyingAsset(CommonNodeEntity structuredProductUnderlyingAsset) {
        this.structuredProductUnderlyingAsset = structuredProductUnderlyingAsset;
    }

    /**
     * 获取 subscriptionAmountByRegion
     * 
     * @return subscriptionAmountByRegion.
     */
    public CommonNodeEntity getSubscriptionAmountByRegion() {
        return subscriptionAmountByRegion;
    }

    /**
     * 设置 subscriptionAmountByRegion
     * 
     * @param subscriptionAmountByRegion subscriptionAmountByRegion
     */
    public void setSubscriptionAmountByRegion(CommonNodeEntity subscriptionAmountByRegion) {
        this.subscriptionAmountByRegion = subscriptionAmountByRegion;
    }

    /**
     * 获取 subscriptionAmount
     * 
     * @return subscriptionAmount.
     */
    public CommonNodeEntity getSubscriptionAmount() {
        return subscriptionAmount;
    }

    /**
     * 设置 subscriptionAmount
     * 
     * @param subscriptionAmount subscriptionAmount
     */
    public void setSubscriptionAmount(CommonNodeEntity subscriptionAmount) {
        this.subscriptionAmount = subscriptionAmount;
    }

    /**
     * 获取 subscriptionAmountCNY
     * 
     * @return subscriptionAmountCNY.
     */
    public CommonNodeEntity getSubscriptionAmountCNY() {
        return subscriptionAmountCNY;
    }

    /**
     * 设置 subscriptionAmountCNY
     * 
     * @param subscriptionAmountCNY subscriptionAmountCNY
     */
    public void setSubscriptionAmountCNY(CommonNodeEntity subscriptionAmountCNY) {
        this.subscriptionAmountCNY = subscriptionAmountCNY;
    }

    /**
     * 获取 currency
     * 
     * @return currency.
     */
    public CommonNodeEntity getCurrency() {
        return currency;
    }

    /**
     * 设置 currency
     * 
     * @param currency currency
     */
    public void setCurrency(CommonNodeEntity currency) {
        this.currency = currency;
    }

    /**
     * 获取 subscribedAmountOverLatestSubscriptionPeriodCNY
     * 
     * @return subscribedAmountOverLatestSubscriptionPeriodCNY.
     */
    public CommonNodeEntity getSubscribedAmountOverLatestSubscriptionPeriodCNY() {
        return subscribedAmountOverLatestSubscriptionPeriodCNY;
    }

    /**
     * 设置 subscribedAmountOverLatestSubscriptionPeriodCNY
     * 
     * @param subscribedAmountOverLatestSubscriptionPeriodCNY
     *            subscribedAmountOverLatestSubscriptionPeriodCNY
     */
    public void setSubscribedAmountOverLatestSubscriptionPeriodCNY(CommonNodeEntity subscribedAmountOverLatestSubscriptionPeriodCNY) {
        this.subscribedAmountOverLatestSubscriptionPeriodCNY = subscribedAmountOverLatestSubscriptionPeriodCNY;
    }

    /**
     * 获取 redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY
     * 
     * @return redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY.
     */
    public CommonNodeEntity getRedeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY() {
        return redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY;
    }

    /**
     * 设置 redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY
     * 
     * @param redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY
     *            redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY
     */
    public void setRedeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY(CommonNodeEntity redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY) {
        this.redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY = redeemedPrincipalAndInterestPaymentOverLatestSubscriptionPeriodCNY;
    }

    /**
     * 获取 redeemedInterestPaymentOverLatestSubscriptionPeriodCNY
     * 
     * @return redeemedInterestPaymentOverLatestSubscriptionPeriodCNY.
     */
    public CommonNodeEntity getRedeemedInterestPaymentOverLatestSubscriptionPeriodCNY() {
        return redeemedInterestPaymentOverLatestSubscriptionPeriodCNY;
    }

    /**
     * 设置 redeemedInterestPaymentOverLatestSubscriptionPeriodCNY
     * 
     * @param redeemedInterestPaymentOverLatestSubscriptionPeriodCNY
     *            redeemedInterestPaymentOverLatestSubscriptionPeriodCNY
     */
    public void setRedeemedInterestPaymentOverLatestSubscriptionPeriodCNY(CommonNodeEntity redeemedInterestPaymentOverLatestSubscriptionPeriodCNY) {
        this.redeemedInterestPaymentOverLatestSubscriptionPeriodCNY = redeemedInterestPaymentOverLatestSubscriptionPeriodCNY;
    }

    /**
     * 获取 underlyingAssetTypeOfStructuredDeposits
     * 
     * @return underlyingAssetTypeOfStructuredDeposits.
     */
    public CommonNodeEntity getUnderlyingAssetTypeOfStructuredDeposits() {
        return underlyingAssetTypeOfStructuredDeposits;
    }

    /**
     * 设置 underlyingAssetTypeOfStructuredDeposits
     * 
     * @param underlyingAssetTypeOfStructuredDeposits underlyingAssetTypeOfStructuredDeposits
     */
    public void setUnderlyingAssetTypeOfStructuredDeposits(CommonNodeEntity underlyingAssetTypeOfStructuredDeposits) {
        this.underlyingAssetTypeOfStructuredDeposits = underlyingAssetTypeOfStructuredDeposits;
    }

    /**
     * 获取 underlyingAssetType
     * 
     * @return underlyingAssetType.
     */
    public CommonNodeEntity getUnderlyingAssetType() {
        return underlyingAssetType;
    }

    /**
     * 设置 underlyingAssetType
     * 
     * @param underlyingAssetType underlyingAssetType
     */
    public void setUnderlyingAssetType(CommonNodeEntity underlyingAssetType) {
        this.underlyingAssetType = underlyingAssetType;
    }

    /**
     * 获取 assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities
     * 
     * @return assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities.
     */
    public CommonNodeEntity getAssetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities() {
        return assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities;
    }

    /**
     * 设置 assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities
     * 
     * @param assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities
     *            assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities
     */
    public void setAssetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities(CommonNodeEntity assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities) {
        this.assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities = assetsLiabilitiesIdentificationCodeOfUnderlyingAssetsLiabilities;
    }

    /**
     * 获取 quantityOfUnderlyingAssetsLiabilities
     * 
     * @return quantityOfUnderlyingAssetsLiabilities.
     */
    public CommonNodeEntity getQuantityOfUnderlyingAssetsLiabilities() {
        return quantityOfUnderlyingAssetsLiabilities;
    }

    /**
     * 设置 quantityOfUnderlyingAssetsLiabilities
     * 
     * @param quantityOfUnderlyingAssetsLiabilities quantityOfUnderlyingAssetsLiabilities
     */
    public void setQuantityOfUnderlyingAssetsLiabilities(CommonNodeEntity quantityOfUnderlyingAssetsLiabilities) {
        this.quantityOfUnderlyingAssetsLiabilities = quantityOfUnderlyingAssetsLiabilities;
    }

    /**
     * 获取 marketValueOfUnderlyingAssetsLiabilitiesCNY
     * 
     * @return marketValueOfUnderlyingAssetsLiabilitiesCNY.
     */
    public CommonNodeEntity getMarketValueOfUnderlyingAssetsLiabilitiesCNY() {
        return marketValueOfUnderlyingAssetsLiabilitiesCNY;
    }

    /**
     * 设置 marketValueOfUnderlyingAssetsLiabilitiesCNY
     * 
     * @param marketValueOfUnderlyingAssetsLiabilitiesCNY
     *            marketValueOfUnderlyingAssetsLiabilitiesCNY
     */
    public void setMarketValueOfUnderlyingAssetsLiabilitiesCNY(CommonNodeEntity marketValueOfUnderlyingAssetsLiabilitiesCNY) {
        this.marketValueOfUnderlyingAssetsLiabilitiesCNY = marketValueOfUnderlyingAssetsLiabilitiesCNY;
    }

    /**
     * 获取 registrationSerialNumber
     * 
     * @return registrationSerialNumber.
     */
    public CommonNodeEntity getRegistrationSerialNumber() {
        return registrationSerialNumber;
    }

    /**
     * 设置 registrationSerialNumber
     * 
     * @param registrationSerialNumber registrationSerialNumber
     */
    public void setRegistrationSerialNumber(CommonNodeEntity registrationSerialNumber) {
        this.registrationSerialNumber = registrationSerialNumber;
    }

    /**
     * 获取 wealthManagementCertification
     * 
     * @return wealthManagementCertification.
     */
    public CommonNodeEntity getWealthManagementCertification() {
        return wealthManagementCertification;
    }

    /**
     * 设置 wealthManagementCertification
     * 
     * @param wealthManagementCertification wealthManagementCertification
     */
    public void setWealthManagementCertification(CommonNodeEntity wealthManagementCertification) {
        this.wealthManagementCertification = wealthManagementCertification;
    }

    /**
     * 获取 registrationBankCode
     * 
     * @return registrationBankCode.
     */
    public CommonNodeEntity getRegistrationBankCode() {
        return registrationBankCode;
    }

    /**
     * 设置 registrationBankCode
     * 
     * @param registrationBankCode registrationBankCode
     */
    public void setRegistrationBankCode(CommonNodeEntity registrationBankCode) {
        this.registrationBankCode = registrationBankCode;
    }

    /**
     * 获取 investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     * 
     * @return investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple.
     */
    public List<CommonNodeEntity> getInvestorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple() {
        return investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple;
    }

    /**
     * 设置 investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     * 
     * @param investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     *            investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     */
    public void setInvestorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple(List<CommonNodeEntity> investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple) {
        this.investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple = investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple;
    }

    /**
     * 获取 businessType
     * 
     * @return businessType.
     */
    public CommonNodeEntity getBusinessType() {
        return businessType;
    }

    /**
     * 设置 businessType
     * 
     * @param businessType businessType
     */
    public void setBusinessType(CommonNodeEntity businessType) {
        this.businessType = businessType;
    }

    /**
     * 获取 units
     * 
     * @return units.
     */
    public CommonNodeEntity getUnits() {
        return units;
    }

    /**
     * 设置 units
     * 
     * @param units units
     */
    public void setUnits(CommonNodeEntity units) {
        this.units = units;
    }

    /**
     * 获取 numberOfTransactions
     * 
     * @return numberOfTransactions.
     */
    public CommonNodeEntity getNumberOfTransactions() {
        return numberOfTransactions;
    }

    /**
     * 设置 numberOfTransactions
     * 
     * @param numberOfTransactions numberOfTransactions
     */
    public void setNumberOfTransactions(CommonNodeEntity numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

	public CommonNodeEntity getFundingCurrency() {
		return fundingCurrency;
	}

	public void setFundingCurrency(CommonNodeEntity fundingCurrency) {
		this.fundingCurrency = fundingCurrency;
	}

	public CommonNodeEntity getPrincipalPaymentCurrency() {
		return principalPaymentCurrency;
	}

	public void setPrincipalPaymentCurrency(CommonNodeEntity principalPaymentCurrency) {
		this.principalPaymentCurrency = principalPaymentCurrency;
	}

	public CommonNodeEntity getIncomePaymentCurrency() {
		return incomePaymentCurrency;
	}

	public void setIncomePaymentCurrency(CommonNodeEntity incomePaymentCurrency) {
		this.incomePaymentCurrency = incomePaymentCurrency;
	}

	public CommonNodeEntity getRegistrationClassification() {
		return registrationClassification;
	}

	public void setRegistrationClassification(CommonNodeEntity registrationClassification) {
		this.registrationClassification = registrationClassification;
	}

	public CommonNodeEntity getTypeOfRegistrar() {
		return typeOfRegistrar;
	}

	public void setTypeOfRegistrar(CommonNodeEntity typeOfRegistrar) {
		this.typeOfRegistrar = typeOfRegistrar;
	}

    public CommonNodeEntity getProductSpecialPropertyTuple() {
        return productSpecialPropertyTuple;
    }

    public void setProductSpecialPropertyTuple(CommonNodeEntity productSpecialPropertyTuple) {
        this.productSpecialPropertyTuple = productSpecialPropertyTuple;
    }

    public CommonNodeEntity getProductSpecialProperty() {
        return productSpecialProperty;
    }

    public void setProductSpecialProperty(CommonNodeEntity productSpecialProperty) {
        this.productSpecialProperty = productSpecialProperty;
    }
}
