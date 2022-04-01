/*
 * 文件名：FilingRegistrationTupleEntity.java
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
 * 一般个人产品申报登记
 *
 * @author 刘溶容
 * @version 1.0 2016年11月7日
 * @see BaseInfoEntity
 * @since 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseInfoEntity implements Feild {

    /**
     * 产品登记编码
     */
    @XmlElement(name = "wemax:ProductRegistrationCode")
    private CommonNodeEntity productRegistrationCode;

    /**
     * 产品名称
     */
    @XmlElement(name = "wemax:ProductName")
    private CommonNodeEntity productName;

    /**
     * 行内标识码
     */
    @XmlElement(name = "wemax:PreidentificationCode")
    private CommonNodeEntity preidentificationCode;

    /**
     * 理财产品代码
     */
    @XmlElement(name = "wemax:ProductCode")
    private CommonNodeEntity productCode;

    /**
     * 行内资产/负债编码
     */
    @XmlElement(name = "wemax:AssetsAndLiabilitiesIdentificationCode")
    private CommonNodeEntity assetsAndLiabilitiesIdentificationCode;

    /**
     * 发行机构代码
     */
    @XmlElement(name = "wemax:IssuerIdentificationCode")
    private CommonNodeEntity issuerIdentificationCode;

    /**
     * 资产/负债类别
     */
    @XmlElement(name = "wemax:TypeOfAssetsAndLiabilities")
    private CommonNodeEntity typeOfAssetsAndLiabilities;

    /**
     * 交易流通场所
     */
    @XmlElement(name = "wemax:TradingVenue")
    private CommonNodeEntity tradingVenue;

    /**
     * 币种
     */
    @XmlElement(name = "wemax:Currency")
    private CommonNodeEntity currency;

    /**
     * 资产负债登记备注
     */
    @XmlElement(name = "wemax:AssetsAndLiabilitiesRegistrationDetails")
    private CommonNodeEntity assetsAndLiabilitiesRegistrationDetails;

    /**
     * 资金存入银行
     */
    @XmlElement(name = "wemax:DepositBank")
    private CommonNodeEntity depositBank;

    /***1217升级**/

    /**
     * 存款账号
     */
    @XmlElement(name = "wemax:DepositAccountNumber")
    private CommonNodeEntity depositAccountNumber;
    /***1217升级**/

    /**
     * 存款金额
     */
    @XmlElement(name = "wemax:DepositAmount")
    private CommonNodeEntity depositAmount;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForCashAndBankDepositsIncludingCds")
    private CommonNodeEntity valueDateForCashAndBankDepositsIncludingCds;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForCashAndBankDepositsIncludingCds")
    private CommonNodeEntity maturityDateForCashAndBankDepositsIncludingCds;

    /**
     * 年利率
     */
    @XmlElement(name = "wemax:AnnualInterestRateForCashAndBankDepositsIncludingCds")
    private CommonNodeEntity annualInterestRateForCashAndBankDepositsIncludingCds;

    /**
     * 计息基础
     */
    @XmlElement(name = "wemax:InterestAccrualBasisForCashAndBankDepositsIncludingCds")
    private CommonNodeEntity interestAccrualBasisForCashAndBankDepositsIncludingCds;

    /**
     * 存款类型
     */
    @XmlElement(name = "wemax:DepositsType")
    private CommonNodeEntity depositsType;

    /**
     * 是否为结构性存款
     */
    @XmlElement(name = "wemax:StructuredDepositsPleaseSelect")
    private CommonNodeEntity structuredDepositsPleaseSelect;

    /**
     * 结构性存款挂钩标的类别
     */
    @XmlElement(name = "wemax:UnderlyingAssetTypeOfStructuredDepositsTuple")
    private List<CommonNodeListEntity> underlyingAssetTypeOfStructuredDepositsTuple;
    /**
     * 结构性存款挂钩标的
     */
    @XmlElement(name = "wemax:UnderlyingAssetOfStructuredDeposits")
    private CommonNodeEntity underlyingAssetOfStructuredDeposits;

    /**
     * 代码
     */
    @XmlElement(name = "wemax:IdentificationCode")
    private CommonNodeEntity identificationCode;

    /**
     * 名称
     */
    @XmlElement(name = "wemax:NameForBondsDirectFinancingVehiclesAndInterbankCds")
    private CommonNodeEntity nameForBondsDirectFinancingVehiclesAndInterbankCds;

    /**
     * 具体类别
     */
    @XmlElement(name = "wemax:SpecificBondType")
    private CommonNodeEntity specificBondType;

    /**
     * 发行方式
     */
    @XmlElement(name = "wemax:IssuingModeOfBond")
    private CommonNodeEntity issuingModeOfBond;

    /**
     * 主体评级
     */
    @XmlElement(name = "wemax:IssuerRatingForBondsDirectFinancingVehiclesAndInterbankCds")
    private CommonNodeEntity issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds;

    /**
     * 发行机构类型（按规模划分）
     */
    @XmlElement(name = "wemax:IssuingInstitutionTypeByScale")
    private CommonNodeEntity issuingInstitutionTypeByScale;

    /**
     * 发行机构类型（按技术领域划分）
     */
    @XmlElement(name = "wemax:IssuingInstitutionTypeByTechnicalField")
    private CommonNodeEntity issuingInstitutionTypeByTechnicalField;

    /**
     * 发行机构类型（按经济类型划分）
     */
    @XmlElement(name = "wemax:IssuingInstitutionTypeByEconomicType")
    private CommonNodeEntity issuingInstitutionTypeByEconomicType;

    /**
     * 发行机构所属行业
     */
    @XmlElement(name = "wemax:IndustryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds")
    private CommonNodeEntity industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds;

    /**
     * 登记托管机构
     */
    @XmlElement(name = "wemax:RegistryAndDepository")
    private CommonNodeEntity registryAndDepository;

    /**
     * 登记托管机构说明
     */
    @XmlElement(name = "wemax:DetailsOnRegistryAndDepository")
    private CommonNodeEntity detailsOnRegistryAndDepository;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo")
    private CommonNodeEntity valueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo")
    private CommonNodeEntity maturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;

    /**
     * 对手方
     */
    @XmlElement(name = "wemax:CounterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo")
    private CommonNodeEntity counterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo;

    /**
     * 对手方类型
     */
    @XmlElement(name = "wemax:CounterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo")
    private CommonNodeEntity counterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo;

    /**
     * 年利率
     */
    @XmlElement(name = "wemax:AnnualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo")
    private CommonNodeEntity annualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;

    /**
     * 计息基础
     */
    @XmlElement(name = "wemax:InterestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo")
    private CommonNodeEntity interestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo;

    /**
     * 回购标的类别
     */
    @XmlElement(name = "wemax:CollateralType")
    private CommonNodeEntity collateralType;

    /**
     * 回购标的金额
     */
    @XmlElement(name = "wemax:CollateralValue")
    private CommonNodeEntity collateralValue;

    /**
     * 收/受益权类型
     */
    @XmlElement(name = "wemax:IncomeBeneficialOwnershipType")
    private CommonNodeEntity incomeBeneficialOwnershipType;

    /**
     * 是否属于买入返售
     */
    @XmlElement(name = "wemax:BuybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 名称
     */
    @XmlElement(name = "wemax:NameForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity nameForCreditAssetTradingsAndNonStandardDebtObligations;


    /***1217升级**/
    /**
     * 资产代码
     */
    @XmlElement(name = "wemax:CreditAssetTradingCode")
    private CommonNodeEntity creditAssetTradingCode;
    /***1217升级**/


    /**
     * 金额
     */
    @XmlElement(name = "wemax:AmountForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity amountForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 份额面值
     */
    @XmlElement(name = "wemax:UnitParValue")
    private CommonNodeEntity unitParValue;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity valueDateForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity maturityDateForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 法定到期日
     */
    @XmlElement(name = "wemax:StatutoryMaturityDate")
    private CommonNodeEntity statutoryMaturityDate;

    /**
     * 是否有预期收益率
     */
    @XmlElement(name = "wemax:ExpectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 项目收益率（利率）
     */
    @XmlElement(name = "wemax:ProjectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 计息类型
     */
    @XmlElement(name = "wemax:CouponType")
    private CommonNodeEntity couponType;

    /**
     * 规则付息标识
     */
    @XmlElement(name = "wemax:RegularInterestPaymentPleaseSelect")
    private CommonNodeEntity regularInterestPaymentPleaseSelect;

    /**
     * 付息频率（个月/次）
     */
    @XmlElement(name = "wemax:InterestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 利息分布方式
     */
    @XmlElement(name = "wemax:CouponAllocationType")
    private CommonNodeEntity couponAllocationType;

    /**
     * 还本付息情况说明
     */
    @XmlElement(name = "wemax:DetailsOnPrincipalAndInterestPayment")
    private CommonNodeEntity detailsOnPrincipalAndInterestPayment;

    /**
     * 计息基础
     */
    @XmlElement(name = "wemax:InterestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 增信机构代码
     */
    @XmlElement(name = "wemax:CreditEnhancementInstitutionCode")
    private CommonNodeEntity creditEnhancementInstitutionCode;

    /**
     * 增信机构名称
     */
    @XmlElement(name = "wemax:CreditEnhancementInstitutionName")
    private CommonNodeEntity creditEnhancementInstitutionName;

    /**
     * 融资总费率
     */
    @XmlElement(name = "wemax:TotalFeeRate")
    private CommonNodeEntity totalFeeRate;

    /**
     * 基准利率种类
     */
    @XmlElement(name = "wemax:BenchmarkInterestRateType")
    private CommonNodeEntity benchmarkInterestRateType;

    /**
     * 是否有浮动因子
     */
    @XmlElement(name = "wemax:FloatingFactorPleaseSelect")
    private CommonNodeEntity floatingFactorPleaseSelect;

    /**
     * 浮动因子
     */
    @XmlElement(name = "wemax:FloatingRate")
    private CommonNodeEntity floatingRate;

    /**
     * 利差（BP）
     */
    @XmlElement(name = "wemax:YieldSpreadBasisPoints")
    private CommonNodeEntity yieldSpreadBasisPoints;

    /**
     * 结构档次
     */
    @XmlElement(name = "wemax:StructureGrade")
    private CommonNodeEntity structureGrade;

    /**
     * 还本方式
     */
    @XmlElement(name = "wemax:PrincipalPaymentType")
    private CommonNodeEntity principalPaymentType;

    /**
     * 分期还本条款标识
     */
    @XmlElement(name = "wemax:InstallmentRepayType")
    private CommonNodeEntity installmentRepayType;

    /**
     * 基础资产类型
     */
    @XmlElement(name = "wemax:BaseAssetType")
    private CommonNodeEntity baseAssetType;

    /**
     * 超额收益分配比例
     */
    @XmlElement(name = "wemax:PercentageOfExcessEarningsDistribution")
    private CommonNodeEntity percentageOfExcessEarningsDistribution;

    /**
     * 融资人
     */
    @XmlElement(name = "wemax:DebtorForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity debtorForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 融资人内部信用评级
     */
    @XmlElement(name = "wemax:InternalDebtorRating")
    private CommonNodeEntity internalDebtorRating;

    /**
     * 外部评级机构名称及对融资人评级结果
     */
    @XmlElement(name = "wemax:RatingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 融资人类型（按规模划分）
     */
    @XmlElement(name = "wemax:DebtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 融资人类型（按技术领域划分）
     */
    @XmlElement(name = "wemax:DebtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 融资人类型（按经济类型划分）
     */
    @XmlElement(name = "wemax:DebtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 融资人所属行业
     */
    @XmlElement(name = "wemax:IndustryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 融资人所属地区
     */
    @XmlElement(name = "wemax:RegionOfDebtor")
    private CommonNodeEntity regionOfDebtor;

    /**
     * 融资人组织机构代码
     */
    @XmlElement(name = "wemax:OrganizationCodeForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 项目是否属于重点监控行业和领域
     */
    @XmlElement(name = "wemax:KeyMonitoringIndustryPleaseSelect")
    private CommonNodeEntity keyMonitoringIndustryPleaseSelect;

    /**
     * 重点监控行业和领域类别
     */
    @XmlElement(name = "wemax:KeyMonitoringIndustryType")
    private CommonNodeEntity keyMonitoringIndustryType;

    /**
     * 重点监控行业和领域类别说明
     */
    @XmlElement(name = "wemax:DetailsOnKeyMonitoringIndustryType")
    private CommonNodeEntity detailsOnKeyMonitoringIndustryType;

    /**
     * 融资项目所属行业
     */
    @XmlElement(name = "wemax:IndustryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 融资项目
     */
    @XmlElement(name = "wemax:ProjectForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity projectForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 担保方式
     */
    @XmlElement(name = "wemax:GuaranteeMethod")
    private CommonNodeEntity guaranteeMethod;

    /**
     * 抵质押物类型
     */
    @XmlElement(name = "wemax:PledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity pledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 抵质押物价值
     */
    @XmlElement(name = "wemax:PledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity pledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 担保性质
     */
    @XmlElement(name = "wemax:GuaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity guaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 担保人与融资人关系
     */
    @XmlElement(name = "wemax:GuarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity guarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 担保情况说明
     */
    @XmlElement(name = "wemax:DetailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 行权方式
     */
    @XmlElement(name = "wemax:ExerciseDateType")
    private CommonNodeEntity exerciseDateType;

    /**
     * 固定行权日
     */
    @XmlElement(name = "wemax:FixedExerciseDate")
    private CommonNodeEntity fixedExerciseDate;

    /**
     * 首次行权日期
     */
    @XmlElement(name = "wemax:FirstExerciseDate")
    private CommonNodeEntity firstExerciseDate;

    /**
     * 行权周期
     */
    @XmlElement(name = "wemax:ExercisePeriod")
    private CommonNodeEntity exercisePeriod;

    /**
     * 行权价格
     */
    @XmlElement(name = "wemax:ExercisePrice")
    private CommonNodeEntity exercisePrice;

    /**
     * 永续条款类型
     */
    @XmlElement(name = "wemax:PerpetualType")
    private CommonNodeEntity perpetualType;

    /**
     * 利息递延条款类型
     */
    @XmlElement(name = "wemax:DeferredInterestType")
    private CommonNodeEntity deferredInterestType;

    /**
     * 递延利息是否计息
     */
    @XmlElement(name = "wemax:InterestBearingForDeferredInterestPleaseSelect")
    private CommonNodeEntity interestBearingForDeferredInterestPleaseSelect;

    /**
     * 首次重定价日期
     */
    @XmlElement(name = "wemax:FirstRepriceDate")
    private CommonNodeEntity firstRepriceDate;

    /**
     * 重定价周期
     */
    @XmlElement(name = "wemax:RepricePeriod")
    private CommonNodeEntity repricePeriod;

    /**
     * 部分赎回标识
     */
    @XmlElement(name = "wemax:PartialRedemptionPleaseSelect")
    private CommonNodeEntity partialRedemptionPleaseSelect;

    /**
     * 部分赎回比例
     */
    @XmlElement(name = "wemax:PartialRedemptionRates")
    private CommonNodeEntity partialRedemptionRates;

    /**
     * 融资人主体评级
     */
    @XmlElement(name = "wemax:DebtorRating")
    private CommonNodeEntity debtorRating;

    /**
     * 资产内部评级
     */
    @XmlElement(name = "wemax:InternalAssetRating")
    private CommonNodeEntity internalAssetRating;

    /**
     * 资产外部评级
     */
    @XmlElement(name = "wemax:StandardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations")
    private CommonNodeEntity standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations;

    /**
     * 含权类型
     */
    @XmlElement(name = "wemax:OptionType")
    private CommonNodeEntity optionType;

    /**
     * 选择权
     */
    @XmlElement(name = "wemax:OptionRight")
    private CommonNodeEntity optionRight;

    /**
     * 行权条件说明
     */
    @XmlElement(name = "wemax:DetailsOnOptionExerciseCondition")
    private CommonNodeEntity detailsOnOptionExerciseCondition;

    /**
     * 是否为收/受益权
     */
    @XmlElement(name = "wemax:IncomeBeneficialOwnershipPleaseSelect")
    private CommonNodeEntity incomeBeneficialOwnershipPleaseSelect;

    /**
     * 是否属于买入返售
     */
    @XmlElement(name = "wemax:BuybackPleaseSelectForNonStandardDebtObligations")
    private CommonNodeEntity buybackPleaseSelectForNonStandardDebtObligations;

    /**
     * 类型
     */
    @XmlElement(name = "wemax:TypeForNonStandardDebtObligations")
    private CommonNodeEntity typeForNonStandardDebtObligations;


    /***1217升级**/

    /**
     * 是否为标准化票据
     */
    @XmlElement(name = "wemax:StandardizedBillOrNotePleaseSelect")
    private CommonNodeEntity standardizedBillOrNotePleaseSelect;

    /**
     * 票据代码
     */
    @XmlElement(name = "wemax:BillOrNoteCode")
    private CommonNodeEntity billOrNoteCode;
    /***1217升级**/


    /**
     * 数量
     */
    @XmlElement(name = "wemax:QuantityForNonStandardDebtObligations")
    private CommonNodeEntity quantityForNonStandardDebtObligations;

    /**
     * 合计金额
     */
    @XmlElement(name = "wemax:AggregateAmount")
    private CommonNodeEntity aggregateAmount;

    /**
     * 加权剩余期限（天）
     */
    @XmlElement(name = "wemax:WeightedAverageRemainingMaturityDays")
    private CommonNodeEntity weightedAverageRemainingMaturityDays;

    /**
     * 最长剩余期限（天）
     */
    @XmlElement(name = "wemax:MaximumRemainingMaturityDays")
    private CommonNodeEntity maximumRemainingMaturityDays;

    /**
     * 最短剩余期限（天）
     */
    @XmlElement(name = "wemax:MinimumRemainingMaturityDays")
    private CommonNodeEntity minimumRemainingMaturityDays;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForNonStandardDebtObligations")
    private CommonNodeEntity valueDateForNonStandardDebtObligations;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForNonStandardDebtObligations")
    private CommonNodeEntity maturityDateForNonStandardDebtObligations;

    /**
     * 行业
     */
    @XmlElement(name = "wemax:IndustryForNonStandardDebtObligations")
    private CommonNodeEntity industryForNonStandardDebtObligations;

    /**
     * 贴现利率
     */
    @XmlElement(name = "wemax:DiscountRate")
    private CommonNodeEntity discountRate;

    /**
     * 股票代码
     */
    @XmlElement(name = "wemax:StockCode")
    private CommonNodeEntity stockCode;

    /**
     * 股票/企业名称
     */
    @XmlElement(name = "wemax:NameOfStockEnterprise")
    private CommonNodeEntity nameOfStockEnterprise;

    /**
     * 股票类型
     */
    @XmlElement(name = "wemax:StockType")
    private CommonNodeEntity stockType;

    /**
     * 行业
     */
    @XmlElement(name = "wemax:IndustryForEquities")
    private CommonNodeEntity industryForEquities;

    /**
     * 投资阶段
     */
    @XmlElement(name = "wemax:InvestmentStageForEquities")
    private CommonNodeEntity investmentStageForEquities;

    /**
     * 股权退出安排
     */
    @XmlElement(name = "wemax:EquityWithdrawalDate")
    private CommonNodeEntity equityWithdrawalDate;

    /**
     * 企业类型（按规模划分）
     */
    @XmlElement(name = "wemax:EnterpriseTypeByScale")
    private CommonNodeEntity enterpriseTypeByScale;

    /**
     * 企业类型（按技术领域划分）
     */
    @XmlElement(name = "wemax:EnterpriseTypeByTechnicalField")
    private CommonNodeEntity enterpriseTypeByTechnicalField;

    /**
     * 企业类型（按经济类型划分）
     */
    @XmlElement(name = "wemax:EnterpriseTypeByEconomicType")
    private CommonNodeEntity enterpriseTypeByEconomicType;

    /**
     * 是否为质押融资
     */
    @XmlElement(name = "wemax:PledgedFinancingPleaseSelect")
    private CommonNodeEntity pledgedFinancingPleaseSelect;

    /**
     * 是否为债转股
     */
    @XmlElement(name = "wemax:DebtToEquitySwapPleaseSelectForEquities")
    private CommonNodeEntity debtToEquitySwapPleaseSelectForEquities;

    /**
     * 名称
     */
    @XmlElement(name = "wemax:NameForDerivatives")
    private CommonNodeEntity nameForDerivatives;

    /**
     * 名义本金
     */
    @XmlElement(name = "wemax:NominalPrincipal")
    private CommonNodeEntity nominalPrincipal;

    /**
     * 标的类别
     */
    @XmlElement(name = "wemax:UnderlyingAssetTypeTuple")
    private List<CommonNodeListEntity> underlyingAssetTypeTuple;

    /**
     * 持有目的
     */
    @XmlElement(name = "wemax:HoldingObjective")
    private CommonNodeEntity holdingObjective;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForQdiiBonds")
    private CommonNodeEntity countryOrRegionForQdiiBonds;

    /**
     * 债券名称
     */
    @XmlElement(name = "wemax:BondName")
    private CommonNodeEntity bondName;

    /**
     * 债券代码
     */
    @XmlElement(name = "wemax:BondIdentificationCode")
    private CommonNodeEntity bondIdentificationCode;

    /**
     * 发行机构
     */
    @XmlElement(name = "wemax:IssuerForQdiiBonds")
    private CommonNodeEntity issuerForQdiiBonds;

    /**
     * 发行机构所属行业
     */
    @XmlElement(name = "wemax:IndustryOfIssuerForQdiiBonds")
    private CommonNodeEntity industryOfIssuerForQdiiBonds;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForQdiiBonds")
    private CommonNodeEntity valueDateForQdiiBonds;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForQdiiBonds")
    private CommonNodeEntity maturityDateForQdiiBonds;

    /**
     * 期限（月）
     */
    @XmlElement(name = "wemax:TermToMaturityMonths")
    private CommonNodeEntity termToMaturityMonths;

    /**
     * 发行机构主体信用评级
     */
    @XmlElement(name = "wemax:IssuerRatingForQdiiBonds")
    private CommonNodeEntity issuerRatingForQdiiBonds;

    /**
     * 债券信用评级
     */
    @XmlElement(name = "wemax:BondRating")
    private CommonNodeEntity bondRating;

    /**
     * 票面利率
     */
    @XmlElement(name = "wemax:CouponRateForQdiiBonds")
    private CommonNodeEntity couponRateForQdiiBonds;

    /**
     * 付息频率（个月/次）
     */
    @XmlElement(name = "wemax:InterestPaymentFrequencyMeasuredByMonthForQdiiBonds")
    private CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForQdiiBonds;

    /**
     * 担保情况说明
     */
    @XmlElement(name = "wemax:DetailsOnGuaranteeStatusForQdiiBonds")
    private CommonNodeEntity detailsOnGuaranteeStatusForQdiiBonds;

    /**
     * 是否含权等特殊条款情况说明
     */
    @XmlElement(name = "wemax:DetailsOnSpecialTermsIEEmbeddedOptions")
    private CommonNodeEntity detailsOnSpecialTermsIEEmbeddedOptions;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForQdiiInterbankLendingReverseRepo")
    private CommonNodeEntity countryOrRegionForQdiiInterbankLendingReverseRepo;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForQdiiInterbankLendingReverseRepo")
    private CommonNodeEntity valueDateForQdiiInterbankLendingReverseRepo;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForQdiiInterbankLendingReverseRepo")
    private CommonNodeEntity maturityDateForQdiiInterbankLendingReverseRepo;

    /**
     * 对手方
     */
    @XmlElement(name = "wemax:CounterpartyForQdiiInterbankLendingReverseRepo")
    private CommonNodeEntity counterpartyForQdiiInterbankLendingReverseRepo;

    /**
     * 年利率
     */
    @XmlElement(name = "wemax:AnnualInterestRateForQdiiInterbankLendingReverseRepo")
    private CommonNodeEntity annualInterestRateForQdiiInterbankLendingReverseRepo;

    /**
     * 计息基础
     */
    @XmlElement(name = "wemax:InterestAccrualBasisForQdiiInterbankLendingReverseRepo")
    private CommonNodeEntity interestAccrualBasisForQdiiInterbankLendingReverseRepo;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForQdiiFundsAndBonds")
    private CommonNodeEntity countryOrRegionForQdiiFundsAndBonds;

    /**
     * 股票/基金代码
     */
    @XmlElement(name = "wemax:StockFundIdentificationCode")
    private CommonNodeEntity stockFundIdentificationCode;

    /**
     * 股票/基金名称
     */
    @XmlElement(name = "wemax:StockFundName")
    private CommonNodeEntity stockFundName;

    /**
     * 发行机构
     */
    @XmlElement(name = "wemax:IssuerForQdiiFundsAndBonds")
    private CommonNodeEntity issuerForQdiiFundsAndBonds;

    /**
     * 行业
     */
    @XmlElement(name = "wemax:IndustryForQdiiFundsAndBonds")
    private CommonNodeEntity industryForQdiiFundsAndBonds;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForQdiiStructuredNotes")
    private CommonNodeEntity countryOrRegionForQdiiStructuredNotes;

    /**
     * 合约名称
     */
    @XmlElement(name = "wemax:ContractName")
    private CommonNodeEntity contractName;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForQdiiStructuredNotes")
    private CommonNodeEntity valueDateForQdiiStructuredNotes;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForQdiiStructuredNotes")
    private CommonNodeEntity maturityDateForQdiiStructuredNotes;

    /**
     * 票面利率
     */
    @XmlElement(name = "wemax:CouponRateForQdiiStructuredNotes")
    private CommonNodeEntity couponRateForQdiiStructuredNotes;

    /**
     * 付息频率（个月/次）
     */
    @XmlElement(name = "wemax:InterestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes")
    private CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes;

    /**
     * 固定收益部分所占比例
     */
    @XmlElement(name = "wemax:PercentageOfFixedIncomeInvestments")
    private CommonNodeEntity percentageOfFixedIncomeInvestments;

    /**
     * 衍生金融工具所占比例
     */
    @XmlElement(name = "wemax:PercentageOfDerivativeInvestments")
    private CommonNodeEntity percentageOfDerivativeInvestments;

    /**
     * 衍生金融工具具体投资方式
     */
    @XmlElement(name = "wemax:TypeOfDerivativeInvestments")
    private CommonNodeEntity typeOfDerivativeInvestments;

    /**
     * 衍生金融工具挂钩标的资产
     */
    @XmlElement(name = "wemax:UnderlyingAsset")
    private CommonNodeEntity underlyingAsset;

    /**
     * 利息结算方式
     */
    @XmlElement(name = "wemax:DetailsOnProceeds")
    private CommonNodeEntity detailsOnProceeds;

    /**
     * 含权情况说明
     */
    @XmlElement(name = "wemax:DetailsOnOption")
    private CommonNodeEntity detailsOnOption;

    /**
     * 结构化票据最高收益率
     */
    @XmlElement(name = "wemax:StructuredNoteMaximumAnnualReturn")
    private CommonNodeEntity structuredNoteMaximumAnnualReturn;

    /**
     * 结构化票据最低收益率
     */
    @XmlElement(name = "wemax:StructuredNoteMinimumAnnualReturn")
    private CommonNodeEntity structuredNoteMinimumAnnualReturn;

    /**
     * 挂钩标的资产基准价格
     */
    @XmlElement(name = "wemax:UnderlyingAssetStrikePrice")
    private CommonNodeEntity underlyingAssetStrikePrice;

    /**
     * 挂钩标的资产登记日价格
     */
    @XmlElement(name = "wemax:UnderlyingAssetPriceOnRegistrationDate")
    private CommonNodeEntity underlyingAssetPriceOnRegistrationDate;

    /**
     * 交易费
     */
    @XmlElement(name = "wemax:TransactionCosts")
    private CommonNodeEntity transactionCosts;

    /**
     * 资管计划名称
     */
    @XmlElement(name = "wemax:AssetManagementPlanName")
    private CommonNodeEntity assetManagementPlanName;


    /***1217升级**/

    /**
     * 是否为银行理财产品
     */
    @XmlElement(name = "wemax:WealthManagementProductPleaseSelect")
    private CommonNodeEntity wealthManagementProductPleaseSelect;

    /* *//**
     * 产品登记编码
     *//*
    @XmlElement(name = "wemax:ProductRegistrationCode")
    private CommonNodeEntity productRegistrationCode;*/
    /***1217升级**/

    /**
     * 是否由金融资产投资公司发行
     */
    @XmlElement(name = "wemax:IssuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans")
    private CommonNodeEntity issuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans;

    /**
     * 资管计划发起人机构编码
     */
    @XmlElement(name = "wemax:AssetManagementPlanIssuerCode")
    private CommonNodeEntity assetManagementPlanIssuerCode;

    /**
     * 资管计划登记编码
     */
    @XmlElement(name = "wemax:AssetManagementPlanRegistrationCode")
    private CommonNodeEntity assetManagementPlanRegistrationCode;

    /**
     * 管理人
     */
    @XmlElement(name = "wemax:Manager")
    private CommonNodeEntity manager;

    /**
     * 托管人
     */
    @XmlElement(name = "wemax:Custodian")
    private CommonNodeEntity custodian;

    /**
     * 金额
     */
    @XmlElement(name = "wemax:AmountForAssetManagementPlans")
    private CommonNodeEntity amountForAssetManagementPlans;

    /**
     * 资金实际投向
     */
    @XmlElement(name = "wemax:ActualInvestmentDirectionOfFund")
    private CommonNodeEntity actualInvestmentDirectionOfFund;

    /**
     * 资金运用方式
     */
    @XmlElement(name = "wemax:DetailsOnInvestment")
    private CommonNodeEntity detailsOnInvestment;

    /**
     * 资金运用行业
     */
    @XmlElement(name = "wemax:IndustryOfInvestment")
    private CommonNodeEntity industryOfInvestment;

    /**
     * 资管计划成立日期
     */
    @XmlElement(name = "wemax:ValueDateOfAssetManagementPlan")
    private CommonNodeEntity valueDateOfAssetManagementPlan;

    /**
     * 资管计划终止日期
     */
    @XmlElement(name = "wemax:MaturityDateOfAssetManagementPlan")
    private CommonNodeEntity maturityDateOfAssetManagementPlan;

    /**
     * 资管计划属性
     */
    @XmlElement(name = "wemax:TypeOfAssetManagementPlan")
    private CommonNodeEntity typeOfAssetManagementPlan;

    /**
     * 是否有预期收益率
     */
    @XmlElement(name = "wemax:ExpectedReturnPleaseSelectForAssetManagementPlans")
    private CommonNodeEntity expectedReturnPleaseSelectForAssetManagementPlans;

    /**
     * 预期最高收益率
     */
    @XmlElement(name = "wemax:MaximumExpectedAnnualReturn")
    private CommonNodeEntity maximumExpectedAnnualReturn;

    /**
     * 预期最低收益率
     */
    @XmlElement(name = "wemax:MinimumExpectedAnnualReturn")
    private CommonNodeEntity minimumExpectedAnnualReturn;

    /**
     * 购买结构
     */
    @XmlElement(name = "wemax:InvestmentStructure")
    private CommonNodeEntity investmentStructure;

    /**
     * 管理方式
     */
    @XmlElement(name = "wemax:ManagementType")
    private CommonNodeEntity managementType;
    /**
     * 管理费率
     */
    @XmlElement(name = "wemax:ManagementFeeRate")
    private CommonNodeEntity managementFeeRate;

    /**
     * 托管费率
     */
    @XmlElement(name = "wemax:CustodianFeeRateForAssetManagementPlans")
    private CommonNodeEntity custodianFeeRateForAssetManagementPlans;

    /**
     * 交易相关合计费率
     */
    @XmlElement(name = "wemax:TransactionCostRate")
    private CommonNodeEntity transactionCostRate;

    /**
     * 中介服务机构合计费率
     */
    @XmlElement(name = "wemax:IntermediaryServiceFeeRate")
    private CommonNodeEntity intermediaryServiceFeeRate;

    /**
     * 其他合计费率
     */
    @XmlElement(name = "wemax:OtherExpensesRate")
    private CommonNodeEntity otherExpensesRate;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForCommodities")
    private CommonNodeEntity countryOrRegionForCommodities;

    /**
     * 名称
     */
    @XmlElement(name = "wemax:NameForCommodities")
    private CommonNodeEntity nameForCommodities;

    /**
     * 期限（天）
     */
    @XmlElement(name = "wemax:TermToMaturityDaysForCommodities")
    private CommonNodeEntity termToMaturityDaysForCommodities;

    /**
     * 资产收益率
     */
    @XmlElement(name = "wemax:AssetAnnualReturnForCommodities")
    private CommonNodeEntity assetAnnualReturnForCommodities;

    /**
     * 资产价值
     */
    @XmlElement(name = "wemax:AssetValueForCommodities")
    private CommonNodeEntity assetValueForCommodities;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForAlternativeInvestments")
    private CommonNodeEntity countryOrRegionForAlternativeInvestments;

    /**
     * 名称
     */
    @XmlElement(name = "wemax:NameForAlternativeInvestments")
    private CommonNodeEntity nameForAlternativeInvestments;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForAlternativeInvestments")
    private CommonNodeEntity valueDateForAlternativeInvestments;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForAlternativeInvestments")
    private CommonNodeEntity maturityDateForAlternativeInvestments;

    /**
     * 资产价值
     */
    @XmlElement(name = "wemax:AssetValueForAlternativeInvestments")
    private CommonNodeEntity assetValueForAlternativeInvestments;

    /**
     * 资产收益率
     */
    @XmlElement(name = "wemax:AssetAnnualReturnForAlternativeInvestments")
    private CommonNodeEntity assetAnnualReturnForAlternativeInvestments;

    /**
     * 基金代码
     */
    @XmlElement(name = "wemax:FundIdentificationCode")
    private CommonNodeEntity fundIdentificationCode;

    /**
     * 基金名称
     */
    @XmlElement(name = "wemax:FundName")
    private CommonNodeEntity fundName;

    /**
     * 行业
     */
    @XmlElement(name = "wemax:IndustryForPublicFundsPrivateFunds")
    private CommonNodeEntity industryForPublicFundsPrivateFunds;

    /**
     * 登记备案机构
     */
    @XmlElement(name = "wemax:RegistrationAgency")
    private CommonNodeEntity registrationAgency;


    /***1217升级**/

    /**
     * 是否为固定收益类
     */
    @XmlElement(name = "wemax:FixedIncomeFundPleaseSelect")
    private CommonNodeEntity fixedIncomeFundPleaseSelect;
    /***1217升级**/

    /**
     * 是否属于政府投资基金
     */
    @XmlElement(name = "wemax:GovernmentInvestmentFundPleaseSelect")
    private CommonNodeEntity governmentInvestmentFundPleaseSelect;

    /**
     * 政府投资基金投向
     */
    @XmlElement(name = "wemax:InvestmentDirectionOfGovernmentInvestmentFund")
    private CommonNodeEntity investmentDirectionOfGovernmentInvestmentFund;

    /**
     * 是否由金融资产投资公司发行
     */
    @XmlElement(name = "wemax:IssuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds")
    private CommonNodeEntity issuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds;

    /**
     * 产业投资基金公司名称
     */
    @XmlElement(name = "wemax:NameOfInvestmentFund")
    private CommonNodeEntity nameOfInvestmentFund;

    /**
     * 基金管理机构名称
     */
    @XmlElement(name = "wemax:NameOfFundManagementInstitution")
    private CommonNodeEntity nameOfFundManagementInstitution;

    /**
     * 基金托管机构名称
     */
    @XmlElement(name = "wemax:NameOfFundCustodianInstitution")
    private CommonNodeEntity nameOfFundCustodianInstitution;

    /**
     * 投资阶段
     */
    @XmlElement(name = "wemax:InvestmentStageForPublicFundsPrivateFunds")
    private CommonNodeEntity investmentStageForPublicFundsPrivateFunds;

    /**
     * 投资企业类型（按规模划分）
     */
    @XmlElement(name = "wemax:InvestmentEnterpriseTypeByScale")
    private CommonNodeEntity investmentEnterpriseTypeByScale;

    /**
     * 投资企业类型（按技术领域划分）
     */
    @XmlElement(name = "wemax:InvestmentEnterpriseTypeByTechnicalField")
    private CommonNodeEntity investmentEnterpriseTypeByTechnicalField;

    /**
     * 投资企业类型（按经济类型划分）
     */
    @XmlElement(name = "wemax:InvestmentEnterpriseTypeByEconomicType")
    private CommonNodeEntity investmentEnterpriseTypeByEconomicType;

    /**
     * 基金投资资产
     */
    @XmlElement(name = "wemax:InvestmentAssets")
    private CommonNodeEntity investmentAssets;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForOtherLiabilities")
    private CommonNodeEntity countryOrRegionForOtherLiabilities;

    /**
     * 名称
     */
    @XmlElement(name = "wemax:NameForOtherLiabilities")
    private CommonNodeEntity nameForOtherLiabilities;

    /**
     * 期限（天）
     */
    @XmlElement(name = "wemax:TermToMaturityDaysForOtherLiabilities")
    private CommonNodeEntity termToMaturityDaysForOtherLiabilities;

    /**
     * 负债规模
     */
    @XmlElement(name = "wemax:LiabilityAmount")
    private CommonNodeEntity liabilityAmount;

    /**
     * 利率
     */
    @XmlElement(name = "wemax:AnnualInterestRate")
    private CommonNodeEntity annualInterestRate;

    /***1217升级**/

    /**
     * 是否为同业借款
     */
    @XmlElement(name = "wemax:InterbankCashBorrowingPleaseSelect")
    private CommonNodeEntity interbankCashBorrowingPleaseSelect;
    /***1217升级**/


    /**
     * 名称
     */
    @XmlElement(name = "wemax:NameForOther")
    private CommonNodeEntity nameForOther;

    /**
     * 行内资产类别
     */
    @XmlElement(name = "wemax:AssetType")
    private CommonNodeEntity assetType;

    /**
     * 行内资产类别说明
     */
    @XmlElement(name = "wemax:DetailsOnAssetType")
    private CommonNodeEntity detailsOnAssetType;

    /**
     * 金额
     */
    @XmlElement(name = "wemax:AmountForOther")
    private CommonNodeEntity amountForOther;

    /**
     * 起息日
     */
    @XmlElement(name = "wemax:ValueDateForOther")
    private CommonNodeEntity valueDateForOther;

    /**
     * 到期日
     */
    @XmlElement(name = "wemax:MaturityDateForOther")
    private CommonNodeEntity maturityDateForOther;

    /**
     * 所属国家或地区
     */
    @XmlElement(name = "wemax:CountryOrRegionForOther")
    private CommonNodeEntity countryOrRegionForOther;

    /**
     * 是否有预期收益率
     */
    @XmlElement(name = "wemax:ExpectedReturnPleaseSelectForOther")
    private CommonNodeEntity expectedReturnPleaseSelectForOther;

    /**
     * 项目收益率（利率）
     */
    @XmlElement(name = "wemax:ProjectAnnualReturnForOther")
    private CommonNodeEntity projectAnnualReturnForOther;

    /**
     * 付息频率（个月/次）
     */
    @XmlElement(name = "wemax:InterestPaymentFrequencyMeasuredByMonthForOther")
    private CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForOther;

    /**
     * 融资人
     */
    @XmlElement(name = "wemax:DebtorForOther")
    private CommonNodeEntity debtorForOther;

    /**
     * 融资人组织机构代码
     */
    @XmlElement(name = "wemax:OrganizationCodeForOther")
    private CommonNodeEntity organizationCodeForOther;

    /**
     * 外部评级机构名称及对融资人评级结果
     */
    @XmlElement(name = "wemax:RatingAgencyAndIssuerRatingForOther")
    private CommonNodeEntity ratingAgencyAndIssuerRatingForOther;

    /**
     * 融资人类型（按规模划分）
     */
    @XmlElement(name = "wemax:DebtorTypeByScaleForOther")
    private CommonNodeEntity debtorTypeByScaleForOther;

    /**
     * 融资人类型（按技术领域划分）
     */
    @XmlElement(name = "wemax:DebtorTypeByTechnicalFieldForOther")
    private CommonNodeEntity debtorTypeByTechnicalFieldForOther;

    /**
     * 融资人类型（按经济类型划分）
     */
    @XmlElement(name = "wemax:DebtorTypeByEconomicTypeForOther")
    private CommonNodeEntity debtorTypeByEconomicTypeForOther;

    /**
     * 融资人所属行业
     */
    @XmlElement(name = "wemax:IndustryOfDebtorForOther")
    private CommonNodeEntity industryOfDebtorForOther;

    /**
     * 融资人所属行业
     */
    @XmlElement(name = "wemax:ProjectForOther")
    private CommonNodeEntity projectForOther;

    /**
     * 融资项目所属行业
     */
    @XmlElement(name = "wemax:IndustryOfProjectForOther")
    private CommonNodeEntity industryOfProjectForOther;

    /**
     * 项目是否属于重点监控行业和领域
     */
    @XmlElement(name = "wemax:KeyMonitoringIndustryPleaseSelectForOther")
    private CommonNodeEntity keyMonitoringIndustryPleaseSelectForOther;

    /**
     * 重点监控行业和领域类别
     */
    @XmlElement(name = "wemax:KeyMonitoringIndustryTypeForOther")
    private CommonNodeEntity keyMonitoringIndustryTypeForOther;

    /**
     * 重点监控行业和领域类别说明
     */
    @XmlElement(name = "wemax:DetailsOnKeyMonitoringIndustryTypeForOther")
    private CommonNodeEntity detailsOnKeyMonitoringIndustryTypeForOther;

    /**
     * 对应资产外部评级
     */
    @XmlElement(name = "wemax:StandardizedInternalAssetRatingForOther")
    private CommonNodeEntity standardizedInternalAssetRatingForOther;

    /**
     * 担保方式
     */
    @XmlElement(name = "wemax:GuaranteeMethodForOther")
    private CommonNodeEntity guaranteeMethodForOther;

    /**
     * 抵质押物类型
     */
    @XmlElement(name = "wemax:PledgeTypeForOther")
    private CommonNodeEntity pledgeTypeForOther;

    /**
     * 抵质押物价值
     */
    @XmlElement(name = "wemax:PledgeValueCNYForOther")
    private CommonNodeEntity pledgeValueCNYForOther;

    /**
     * 担保性质
     */
    @XmlElement(name = "wemax:GuaranteeTypeForOther")
    private CommonNodeEntity guaranteeTypeForOther;

    /**
     * 担保人与融资人关系
     */
    @XmlElement(name = "wemax:GuarantorTypeForOther")
    private CommonNodeEntity guarantorTypeForOther;

    /**
     * 担保情况说明
     */
    @XmlElement(name = "wemax:DetailsOnGuaranteeStatusForOther")
    private CommonNodeEntity detailsOnGuaranteeStatusForOther;

    /**
     * 是否为债转股
     */
    @XmlElement(name = "wemax:DebtToEquitySwapPleaseSelectForOther")
    private CommonNodeEntity debtToEquitySwapPleaseSelectForOther;
    /**
     * 委外投资协议名称
     */
    @XmlElement(name = "wemax:OutsourcingInvestmentAgreementName")
    private CommonNodeEntity outsourcingInvestmentAgreementName;

    /**
     * 委外投资协议编号
     */
    @XmlElement(name = "wemax:OutsourcingInvestmentAgreementCode")
    private CommonNodeEntity outsourcingInvestmentAgreementCode;

    /**
     * 受托人
     */
    @XmlElement(name = "wemax:Trustee")
    private CommonNodeEntity trustee;

    /**
     * 实际管理人
     */
    @XmlElement(name = "wemax:ActualManager")
    private CommonNodeEntity actualManager;

    /**
     * 托管人
     */
    @XmlElement(name = "wemax:CustodianForOutsourcingInvestmentAgreement")
    private CommonNodeEntity custodianForOutsourcingInvestmentAgreement;

    /**
     * 委托投资金额
     */
    @XmlElement(name = "wemax:OutsourcingInvestmentAmount")
    private CommonNodeEntity outsourcingInvestmentAmount;

    /**
     * 资金实际投向
     */
    @XmlElement(name = "wemax:ActualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement")
    private CommonNodeEntity actualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement;

    /**
     * 资金运用方式
     */
    @XmlElement(name = "wemax:DetailsOnInvestmentForOutsourcingInvestmentAgreement")
    private CommonNodeEntity detailsOnInvestmentForOutsourcingInvestmentAgreement;

    /**
     * 资金运用行业
     */
    @XmlElement(name = "wemax:IndustryOfInvestmentForOutsourcingInvestmentAgreement")
    private CommonNodeEntity industryOfInvestmentForOutsourcingInvestmentAgreement;

    /**
     * 投资运作起始日期
     */
    @XmlElement(name = "wemax:ValueDateOfOutsourcingInvestment")
    private CommonNodeEntity valueDateOfOutsourcingInvestment;

    /**
     * 投资运作终止日期
     */
    @XmlElement(name = "wemax:MaturityDateOfOutsourcingInvestment")
    private CommonNodeEntity maturityDateOfOutsourcingInvestment;

    /**
     * 委外投资属性
     */
    @XmlElement(name = "wemax:TypeOfOutsourcingInvestment")
    private CommonNodeEntity typeOfOutsourcingInvestment;

    /**
     *  是否有预期收益率
     */
    @XmlElement(name = "wemax:ExpectedReturnPleaseSelectForOutsourcingInvestmentAgreement")
    private CommonNodeEntity expectedReturnPleaseSelectForOutsourcingInvestmentAgreement;

    /**
     * 预期最高收益率
     */
    @XmlElement(name = "wemax:MaximumExpectedAnnualReturnForOutsourcingInvestmentAgreement")
    private CommonNodeEntity maximumExpectedAnnualReturnForOutsourcingInvestmentAgreement;

    /**
     *  预期最低收益率
     */
    @XmlElement(name = "wemax:MinimumExpectedAnnualReturnForOutsourcingInvestmentAgreement")
    private CommonNodeEntity minimumExpectedAnnualReturnForOutsourcingInvestmentAgreement;

    /**
     * 管理费率
     */
    @XmlElement(name = "wemax:ManagementFeeRateForOutsourcingInvestmentAgreement")
    private CommonNodeEntity managementFeeRateForOutsourcingInvestmentAgreement;

    /**
     * 托管费率
     */
    @XmlElement(name = "wemax:CustodianFeeRateForOutsourcingInvestmentAgreement")
    private CommonNodeEntity custodianFeeRateForOutsourcingInvestmentAgreement;

    /**
     * 交易相关合计费率
     */
    @XmlElement(name = "wemax:TransactionCostRateForOutsourcingInvestmentAgreement")
    private CommonNodeEntity transactionCostRateForOutsourcingInvestmentAgreement;

    /**
     * 中介服务机构合计费率
     */
    @XmlElement(name = "wemax:IntermediaryServiceFeeRateForOutsourcingInvestmentAgreement")
    private CommonNodeEntity intermediaryServiceFeeRateForOutsourcingInvestmentAgreement;

    /**
     * 其他合计费率
     */
    @XmlElement(name = "wemax:OtherExpensesRateForOutsourcingInvestmentAgreement")
    private CommonNodeEntity otherExpensesRateForOutsourcingInvestmentAgreement;

    /**
     * 行内交易编码
     */
    @XmlElement(name = "wemax:TransactionRegistrationCode")
    private CommonNodeEntity transactionRegistrationCode;

    /**
     * 资金流动类型
     */
    @XmlElement(name = "wemax:CashflowType")
    private CommonNodeEntity cashflowType;

    /**
     * 资金流动类型说明
     */
    @XmlElement(name = "wemax:DetailsOnCashflowType")
    private CommonNodeEntity detailsOnCashflowType;

    /**
     * 数量
     */
    @XmlElement(name = "wemax:Quantity")
    private CommonNodeEntity quantity;

    /**
     * 余额
     */
    @XmlElement(name = "wemax:OutstandingAmount")
    private CommonNodeEntity outstandingAmount;

    /**
     * 日期
     */
    @XmlElement(name = "wemax:Date")
    private CommonNodeEntity date;

    /**
     * 持仓登记备注
     */
    @XmlElement(name = "wemax:InvestedAssetsLiabilitiesRegistrationDetails")
    private CommonNodeEntity investedAssetsLiabilitiesRegistrationDetails;

    /**
     * 资产计量方式
     */
    @XmlElement(name = "wemax:MethodOfAssetMeasurement")
    private CommonNodeEntity methodOfAssetMeasurement;


    /**
     * 折算人民币金额
     */
    @XmlElement(name = "wemax:SettlementAmountCNYForTransaction")
    private CommonNodeEntity settlementAmountCNYForTransaction;

    /**
     * 发生金额
     */
    @XmlElement(name = "wemax:SettlementAmount")
    private CommonNodeEntity settlementAmount;

    /**
     * 单位成交价格（净价）
     */
    @XmlElement(name = "wemax:PerUnitTransactionPriceNet")
    private CommonNodeEntity perUnitTransactionPriceNet;

    /**
     * 单位成交价格（全价）
     */
    @XmlElement(name = "wemax:PerUnitTransactionPriceFull")
    private CommonNodeEntity perUnitTransactionPriceFull;

    /**
     * 到期收益率
     */
    @XmlElement(name = "wemax:RateOfAnnualReturn")
    private CommonNodeEntity rateOfAnnualReturn;

    /**
     * 交易对手方
     */
    @XmlElement(name = "wemax:TradeCounterparty")
    private CommonNodeEntity tradeCounterparty;

    /**
     * 交易对手方类型
     */
    @XmlElement(name = "wemax:CounterpartyType")
    private CommonNodeEntity counterpartyType;


    /**
     * 成交编号/合同号
     */
    @XmlElement(name = "wemax:TransactionIdentificationCodeContractNumber")
    private CommonNodeEntity transactionIdentificationCodeContractNumber;


    /***1217升级**/

    /**
     * 交易审批人身份证号
     */
    @XmlElement(name = "wemax:TransactionApproverIdNumber")
    private CommonNodeEntity transactionApproverIDNumber;

    /**
     * 交易审批人姓名
     */
    @XmlElement(name = "wemax:TransactionApproverName")
    private CommonNodeEntity transactionApproverName;

    /**
     * 交易员身份证号
     */
    @XmlElement(name = "wemax:TraderIdNumber")
    private CommonNodeEntity traderIDNumber;

    /**
     * 交易员姓名
     */
    @XmlElement(name = "wemax:TraderName")
    private CommonNodeEntity traderName;
    /***1217升级**/



    /**
     * 交易日
     */
    @XmlElement(name = "wemax:TradeDate")
    private CommonNodeEntity tradeDate;

    /**
     * 交易登记备注
     */
    @XmlElement(name = "wemax:TransactionRegistrationDetails")
    private CommonNodeEntity transactionRegistrationDetails;

    /**
     * 估值日期
     */
    @XmlElement(name = "wemax:ValuationDate")
    private CommonNodeEntity valuationDate;

    /**
     * 单位估值（净价）
     */
    @XmlElement(name = "wemax:PerUnitValuationNet")
    private CommonNodeEntity perUnitValuationNet;

    /**
     * 单位估值（全价）
     */
    @XmlElement(name = "wemax:PerUnitValuationFull")
    private CommonNodeEntity perUnitValuationFull;

    /**
     * 估值信息登记备注
     */
    @XmlElement(name = "wemax:ValuationRegistrationDetails")
    private CommonNodeEntity valuationRegistrationDetails;

    /**
     * 对应资产管理计划行内资产/负债编码
     */
    @XmlElement(name = "wemax:AssetsLiabilitiesIdentificationCodeOfAssetManagementPlan")
    private CommonNodeEntity assetsLiabilitiesIdentificationCodeOfAssetManagementPlan;

    /**
     * 资管计划当前总发行数量
     */
    @XmlElement(name = "wemax:AggregateIssuedQuantityOfAssetManagementPlan")
    private CommonNodeEntity aggregateIssuedQuantityOfAssetManagementPlan;

    /**
     * 资管计划当前总折算人民币发行金额
     */
    @XmlElement(name = "wemax:AggregateIssuedAmountOfAssetManagementPlanCNY")
    private CommonNodeEntity aggregateIssuedAmountOfAssetManagementPlanCNY;

    /**
     * 资管计划折算人民币未投资头寸
     */
    @XmlElement(name = "wemax:NonInvestedAmountofAssetManagementPlanCNY")
    private CommonNodeEntity nonInvestedAmountofAssetManagementPlanCNY;

    /**
     * 对应资管及委外资产行内资产/负债编码
     */
    @XmlElement(name = "wemax:AssetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset")
    private CommonNodeEntity assetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset;

    /**
     * 资管及委外资产当前总数量
     */
    @XmlElement(name = "wemax:AggregateQuantityOfAssetManagementProductOrOutsourcingAsset")
    private CommonNodeEntity aggregateQuantityOfAssetManagementProductOrOutsourcingAsset;

    /**
     * 资管及委外资产当前总折算人民币金额
     */
    @XmlElement(name = "wemax:AggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY")
    private CommonNodeEntity aggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY;

    /**
     * 资管及委外资产未投资头寸
     */
    @XmlElement(name = "wemax:NonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY")
    private CommonNodeEntity nonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY;

    /**
     * 持仓日期
     */
    @XmlElement(name = "wemax:ReportDate")
    private CommonNodeEntity reportDate;

    /**
     * 底层资产情况
     */
    @XmlElement(name = "wemax:UnderlyingAssetTuple")
    private List<CommonNodeListEntity> underlyingAssetTuple;

    /**
     * 产品审批人身份证号
     */
    @XmlElement(name = "wemax:ProductApproverIdNumber")
    private CommonNodeEntity productApproverIdNumber;

    /**
     * 产品设计人身份证号
     */
    @XmlElement(name = "wemax:ProductDesignerIdNumber")
    private CommonNodeEntity productDesignerIdNumber;

    /**
     * 投资经理身份证号
     */
    @XmlElement(name = "wemax:InvestmentManagerIdNumber")
    private CommonNodeEntity investmentManagerIdNumber;

    /**
     * 业务联络人姓名
     */
    @XmlElement(name = "wemax:FilingContactName")
    private CommonNodeEntity filingContactName;

    /**
     * 业务联络人座机
     */
    @XmlElement(name = "wemax:FilingContactWorkPhone")
    private CommonNodeEntity filingContactWorkPhone;

    /**
     * 业务联络人手机
     */
    @XmlElement(name = "wemax:FilingContactMobilePhone")
    private CommonNodeEntity filingContactMobilePhone;

    /**
     * 业务联络人邮箱
     */
    @XmlElement(name = "wemax:FilingContactEmail")
    private CommonNodeEntity filingContactEmail;

    /**
     * 产品收益类型
     */
    @XmlElement(name = "wemax:ProductReturnType")
    private CommonNodeEntity productReturnType;
    /**
     * 产品收益类型
     */
    @XmlElement(name = "wemax:ProductTermToMaturityOfPublicOrPrivateProduct")
    private CommonNodeEntity productTermToMaturityOfPublicOrPrivateProduct;
    /**
     * 是否金融同业专属
     */
    @XmlElement(name = "wemax:FinancialInstitutionExclusivePleaseSelect")
    private CommonNodeEntity financialInstitutionExclusivePleaseSelect;


    /**
     * 产品期限
     */
    @XmlElement(name = "wemax:ProductTermToMaturity")
    private CommonNodeEntity productTermToMaturity;

    /**
     * 资金投向地区
     */
    @XmlElement(name = "wemax:InvestmentRegion")
    private CommonNodeEntity investmentRegion;

    /**
     * 产品投资国家或地区（境外）
     */
    @XmlElement(name = "wemax:SpecificCountryOrRegionTuple")
    private List<CommonNodeListEntity> specificCountryOrRegionTuple;

    /**
     * 理财业务服务模式
     */
    @XmlElement(name = "wemax:WealthManagementServiceMode")
    private CommonNodeEntity wealthManagementServiceMode;

    /**
     * 产品运作模式
     */
    @XmlElement(name = "wemax:ProductOperationMode")
    private CommonNodeEntity productOperationMode;

    /**
     * 产品运作模式TypeOfSubscription
     */
    @XmlElement(name = "wemax:TypeOfSubscription")
    private CommonNodeEntity typeOfSubscription;

    /**
     * 会计核算方式
     */
    @XmlElement(name = "wemax:AccountingMethod")
    private CommonNodeEntity accountingMethod;

    /**
     * 产品资产配置方式
     */
    @XmlElement(name = "wemax:ProductAssetAllocationMethod")
    private CommonNodeEntity productAssetAllocationMethod;

    /**
     * 产品管理模式
     */
    @XmlElement(name = "wemax:ProductManagementMode")
    private CommonNodeEntity productManagementMode;

    /**
     * 实际管理人名称
     */
    @XmlElement(name = "wemax:ActualProductManagerName")
    private CommonNodeEntity actualProductManagerName;

    /**
     * 产品定价方式
     */
    @XmlElement(name = "wemax:PricingMethod")
    private CommonNodeEntity pricingMethod;

    /**
     * 产品投资性质
     */
    @XmlElement(name = "wemax:TypeOfInvestmentAsset")
    private CommonNodeEntity typeOfInvestmentAsset;

    /**
     * 投资资产类型
     */
    @XmlElement(name = "wemax:InvestmentAssetType")
    private CommonNodeEntity investmentAssetType;

    /**
     * 业绩比较基准
     */
    @XmlElement(name = "wemax:PerformanceBenchmarkRate")
    private CommonNodeEntity performanceBenchmarkRate;

    /**
     * 是否设置最短持有期限
     */
    @XmlElement(name = "wemax:MinimumHoldingPeriodPleaseSelect")
    private CommonNodeEntity minimumHoldingPeriodPleaseSelect;

    /**
     * 最短持有期限（天）
     */
    @XmlElement(name = "wemax:MinimumHoldingPeriodDays")
    private CommonNodeEntity minimumHoldingPeriodDays;

    /**
     * 最短持有期后是否自由赎回
     */
    @XmlElement(name = "wemax:OptionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect")
    private CommonNodeEntity optionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect;

    /**
     * 是否现金管理类
     */
    @XmlElement(name = "wemax:CashManagementPleaseSelect")
    private CommonNodeEntity cashManagementPleaseSelect;

    /**
     * 结构性产品挂钩标的
     */
    @XmlElement(name = "wemax:StructuredProductUnderlyingAssetTuple")
    private List<CommonNodeListEntity> structuredProductUnderlyingAssetTuple;

    /**
     * 是否有预期收益率
     */
    @XmlElement(name = "wemax:ExpectedReturnPleaseSelect")
    private CommonNodeEntity expectedReturnPleaseSelect;

    /**
     * 是否有收益率测算依据
     */
    @XmlElement(name = "wemax:ReturnCalculationBasisPleaseSelect")
    private CommonNodeEntity returnCalculationBasisPleaseSelect;

    /**
     * 预计客户最高年收益率
     */
    @XmlElement(name = "wemax:InvestorMaximumExpectedAnnualReturn")
    private CommonNodeEntity investorMaximumExpectedAnnualReturn;

    /**
     * 预计客户最低年收益率
     */
    @XmlElement(name = "wemax:InvestorMinimumExpectedAnnualReturn")
    private CommonNodeEntity investorMinimumExpectedAnnualReturn;

    /**
     * 产品销售区域
     */
    @XmlElement(name = "wemax:ProductSalesRegionTuple")
    private List<CommonNodeListEntity> productSalesRegionTuple;

    /**
     * 起点销售金额
     */
    @XmlElement(name = "wemax:InvestmentThreshold")
    private CommonNodeEntity investmentThreshold;


    /**
     * 子节点(募集币种 [tuple])
     */
    @XmlElement(name = "wemax:FundingCurrencyTuple")
    private List<CommonNodeListEntity> fundingCurrencyTuple;


    /**
     * 募集币种
     */
/*    @XmlElement(name = "wemax:FundingCurrency")
    private CommonNodeEntity fundingCurrency;
    */
    /**
     * 子节点(兑付本金币种 [tuple])
     */
    @XmlElement(name = "wemax:PrincipalPaymentCurrencyTuple")
    private List<CommonNodeListEntity> principalPaymentCurrencyTuple;


    /**
     * 兑付本金币种
     */
/*    @XmlElement(name = "wemax:PrincipalPaymentCurrency")
    private CommonNodeEntity principalPaymentCurrency;
    */
    /**
     * 子节点(兑付收益币种 [tuple])
     */
    @XmlElement(name = "wemax:IncomePaymentCurrencyTuple")
    private List<CommonNodeListEntity> incomePaymentCurrencyTuple;

    /**
     * 销售手续费率
     */
    @XmlElement(name = "wemax:SalesCommissionRate")
    private CommonNodeEntity salesCommissionRate;

    /**
     * 托管费率
     */
    @XmlElement(name = "wemax:CustodianFeeRate")
    private CommonNodeEntity custodianFeeRate;

    /**
     * 募集起始日期（从）
     */
    @XmlElement(name = "wemax:SubscriptionStartDateEarliest")
    private CommonNodeEntity subscriptionStartDateEarliest;

    /**
     * 募集起始日期（到）
     */
    @XmlElement(name = "wemax:SubscriptionStartDateLatest")
    private CommonNodeEntity subscriptionStartDateLatest;

    /**
     * 计划募集金额
     */
    @XmlElement(name = "wemax:PlannedFundingAmountCNY")
    private CommonNodeEntity plannedFundingAmountCNY;

    /**
     * 境内托管机构名称
     */
    @XmlElement(name = "wemax:DomesticCustodianNameOfPublicOrPrivateProduct")
    private CommonNodeEntity domesticCustodianNameOfPublicOrPrivateProduct;

    /**
     * 境内托管机构名称
     */
    @XmlElement(name = "wemax:DomesticCustodianName")
    private CommonNodeEntity domesticCustodianName;

    /**
     * 境内托管机构代码
     */
    @XmlElement(name = "wemax:DomesticCustodianIdentificationCode")
    private CommonNodeEntity domesticCustodianIdentificationCode;

    /**
     * 境外托管机构国别
     */
    @XmlElement(name = "wemax:OverseasCustodianNationalityTuple")
    private List<CommonNodeListEntity> overseasCustodianNationalityTuple;

    /**
     * 境外托管机构名称
     */
    @XmlElement(name = "wemax:OverseasCustodianName")
    private CommonNodeEntity overseasCustodianName;

    /**
     * 投资者风险偏好
     */
    @XmlElement(name = "wemax:InvestorRiskPreferenceTuple")
    private List<CommonNodeListEntity> investorRiskPreferenceTuple;

    /**
     * 产品风险等级
     */
    @XmlElement(name = "wemax:ProductRiskRating")
    private CommonNodeEntity productRiskRating;

    /**
     * 发行机构提前终止权标识
     */
    @XmlElement(name = "wemax:IssuerEarlyTerminationOptionPleaseSelect")
    private CommonNodeEntity issuerEarlyTerminationOptionPleaseSelect;

    /**
     * 客户赎回权标识
     */
    @XmlElement(name = "wemax:InvestorRedemptionOptionPleaseSelect")
    private CommonNodeEntity investorRedemptionOptionPleaseSelect;

    /**
     * 产品品牌
     */
    @XmlElement(name = "wemax:ProductBrand")
    private CommonNodeEntity productBrand;

    /****1217升级**/




    @XmlElement(name = "wemax:ProductSpecialPropertyTuple")
    private List<CommonNodeEntity> productSpecialPropertyTuple;
    /**
     * 产品特殊属性
     */
    @XmlElement(name = "wemax:ProductSpecialProperty")
    private CommonNodeEntity productSpecialProperty;

    /****1217升级**/


    /**
     * 产品期次
     */
    @XmlElement(name = "wemax:ProductTermNumber")
    private CommonNodeEntity productTermNumber;

    /**
     * 投资管理费率
     */
    @XmlElement(name = "wemax:InvestmentManagementFeeRate")
    private CommonNodeEntity investmentManagementFeeRate;

    /**
     * 合作模式
     */
    @XmlElement(name = "wemax:CooperationMode")
    private CommonNodeEntity cooperationMode;

    /**
     * 合作机构名称
     */
    @XmlElement(name = "wemax:Cooperator")
    private CommonNodeEntity cooperator;

    /**
     * 投资本金到账日
     */
    @XmlElement(name = "wemax:DueDateOfInvestedPrincipal")
    private CommonNodeEntity dueDateOfInvestedPrincipal;

    /**
     * 投资收益到账日
     */
    @XmlElement(name = "wemax:DueDateOfInvestmentIncome")
    private CommonNodeEntity dueDateOfInvestmentIncome;

    /**
     * 产品增信标识
     */
    @XmlElement(name = "wemax:ProductCreditEnhancementPleaseSelect")
    private CommonNodeEntity productCreditEnhancementPleaseSelect;

    /**
     * 产品增信机构类型
     */
    @XmlElement(name = "wemax:TypeOfProductCreditEnhancementInstitutionTuple")
    private List<CommonNodeListEntity> typeOfProductCreditEnhancementInstitutionTuple;

    /**
     * 产品增信形式
     */
    @XmlElement(name = "wemax:MethodOfProductCreditEnhancement")
    private CommonNodeEntity methodOfProductCreditEnhancement;

    /**
     * 投资资产种类及比例
     */
    @XmlElement(name = "wemax:InvestmentAssetTypeAndRatio")
    private CommonNodeEntity investmentAssetTypeAndRatio;

    /**
     * 一般个人产品申报登记备注
     */
    @XmlElement(name = "wemax:FilingRegistrationDetails")
    private CommonNodeEntity filingRegistrationDetails;

    /**
     * 报告主文件
     */
    @XmlElement(name = "wemax:MainDocumentsTuple")
    private List<CommonNodeListEntity> mainDocumentsTuple;

    /**
     * 理财产品可行性评估报告
     */
    @XmlElement(name = "wemax:WealthManagementProductFeasibilityReportsTuple")
    private List<CommonNodeListEntity> wealthManagementProductFeasibilityReportsTuple;

    /**
     * 内部审核文件
     */
    @XmlElement(name = "wemax:InternalAuthorizedDocumentsTuple")
    private List<CommonNodeListEntity> internalAuthorizedDocumentsTuple;

    /**
     * 对理财产品投资管理人、托管人、投资顾问等相关方的尽职调查文件
     */
    @XmlElement(name = "wemax:DueDiligenceDocumentsOnRelevantPartiesTuple")
    private List<CommonNodeListEntity> dueDiligenceDocumentsOnRelevantPartiesTuple;

    /**
     * 与理财产品投资管理人、托管人、投资顾问等相关方签署的法律文件
     */
    @XmlElement(name = "wemax:LegalDocumentsSignedByRelevantPartiesTuple")
    private List<CommonNodeListEntity> legalDocumentsSignedByRelevantPartiesTuple;

    /**
     * 理财产品销售文件
     */
    @XmlElement(name = "wemax:WealthManagementProductSalesDocumentsTuple")
    private List<CommonNodeListEntity> wealthManagementProductSalesDocumentsTuple;

    /**
     * 理财产品说明书
     */
    @XmlElement(name = "wemax:WealthManagementProductSpecificationsTuple")
    private List<CommonNodeListEntity> wealthManagementProductSpecificationsTuple;

    /**
     * 理财产品宣传材料
     */
    @XmlElement(name = "wemax:WealthManagementProductMarketingDocumentsTuple")
    private List<CommonNodeListEntity> wealthManagementProductMarketingDocumentsTuple;

    /**
     * 其他材料
     */
    @XmlElement(name = "wemax:OtherDocumentsTuple")
    private List<CommonNodeListEntity> otherDocumentsTuple;

    /**
     * 公募私募产品报告登记备注
     */
    @XmlElement(name = "wemax:FilingRegistrationElementsOfPublicOrPrivateProductDetails")
    private CommonNodeEntity filingRegistrationElementsOfPublicOrPrivateProductDetails;

    /**
     * 投资者类型
     */
    @XmlElement(name = "wemax:InvestorType")
    private CommonNodeEntity investorType;

    /**
     * 平均预计客户年收益率
     */
    @XmlElement(name = "wemax:InvestorAverageExpectedAnnualReturn")
    private CommonNodeEntity investorAverageExpectedAnnualReturn;

    /**
     * 收益率分档情况说明
     */
    @XmlElement(name = "wemax:RatesOfReturn")
    private CommonNodeEntity ratesOfReturn;

    /**
     * 募集起始日期
     */
    @XmlElement(name = "wemax:SubscriptionStartDate")
    private CommonNodeEntity subscriptionStartDate;

    /**
     * 募集结束日期
     */
    @XmlElement(name = "wemax:SubscriptionEndDate")
    private CommonNodeEntity subscriptionEndDate;

    /**
     * 产品起始日期
     */
    @XmlElement(name = "wemax:ProductValueDate")
    private CommonNodeEntity productValueDate;

    /**
     * 产品终止日期
     */
    @XmlElement(name = "wemax:ProductMaturityDate")
    private CommonNodeEntity productMaturityDate;

    /**
     * 管理方式
     */
    @XmlElement(name = "wemax:ManagementMethod")
    private CommonNodeEntity managementMethod;

    /**
     * 是否为结构化（分级）产品
     */
    @XmlElement(name = "wemax:StructuredProductPleaseSelect")
    private CommonNodeEntity structuredProductPleaseSelect;

    /***1217升级**/
    /**
     * 分级比例
     */
    @XmlElement(name = "wemax:RatioOfPriority")
    private CommonNodeEntity ratioOfPriority;
    /***1217升级**/

    /**
     * 业绩比较基准上限
     */
    @XmlElement(name = "wemax:UpperLimitOfPerformanceBenchmarkRate")
    private CommonNodeEntity upperLimitOfPerformanceBenchmarkRate;

    /**
     * 业绩比较基准下限
     */
    @XmlElement(name = "wemax:LowerLimitOfPerformanceBenchmarkRate")
    private CommonNodeEntity lowerLimitOfPerformanceBenchmarkRate;
    /**
     * 业绩比较基准说明
     */
    @XmlElement(name = "wemax:DetailsOnPerformanceBenchmarkRate")
    private CommonNodeEntity detailsOnPerformanceBenchmarkRate;

    /**
     * 层级类别
     */
    @XmlElement(name = "wemax:GradeType")
    private CommonNodeEntity gradeType;

    /**
     * 开放模式
     */
    @XmlElement(name = "wemax:OpeningMode")
    private CommonNodeEntity openingMode;

    /**
     * 规律开放周期
     */
    @XmlElement(name = "wemax:RegularOpeningPeriod")
    private CommonNodeEntity regularOpeningPeriod;

    /**
     * 其他规律开放周期 （天）
     */
    @XmlElement(name = "wemax:OtherRegularOpeningPeriodDays")
    private CommonNodeEntity otherRegularOpeningPeriodDays;

    /**
     * 无规律开放说明
     */
    @XmlElement(name = "wemax:DetailsOnIrregularOpeningPeriod")
    private CommonNodeEntity detailsOnIrregularOpeningPeriod;

    /**
     * 首次开放周期起始日
     */
    @XmlElement(name = "wemax:StartDateOfFirstOpeningPeriod")
    private CommonNodeEntity startDateOfFirstOpeningPeriod;

    /**
     * 节假日是否开放
     */
    @XmlElement(name = "wemax:HolidayOpeningPleaseSelect")
    private CommonNodeEntity holidayOpeningPleaseSelect;

    /**
     * 平均开放次数（年化）
     */
    @XmlElement(name = "wemax:AverageOpeningFrequencyAnnualized")
    private CommonNodeEntity averageOpeningFrequencyAnnualized;

    /**
     * 开放期业务
     */
    @XmlElement(name = "wemax:BusinessTypeDuringOpeningPeriod")
    private CommonNodeEntity businessTypeDuringOpeningPeriod;

    /**
     * 开放期业务说明
     */
    @XmlElement(name = "wemax:DetailsOnBusinessTypeDuringOpeningPeriod")
    private CommonNodeEntity detailsOnBusinessTypeDuringOpeningPeriod;

    /**
     * 机构和私人银行产品发行登记备注
     */
    @XmlElement(name = "wemax:IssueRegistrationForAgencyAndPrivateBankDetails")
    private CommonNodeEntity issueRegistrationForAgencyAndPrivateBankDetails;

    /**
     * 理财产品实际终止日期
     */
    @XmlElement(name = "wemax:ActualProductTerminationDate")
    private CommonNodeEntity actualProductTerminationDate;

    /**
     * 银行实际实现收入
     */
    @XmlElement(name = "wemax:RealizedNetIncomeOfBankCNY")
    private CommonNodeEntity realizedNetIncomeOfBankCNY;

    /**
     * 兑付客户收益
     */
    @XmlElement(name = "wemax:InterestPaymentCNY")
    private CommonNodeEntity interestPaymentCNY;

    /**
     * 兑付客户总金额
     */
    @XmlElement(name = "wemax:PrincipalAndInterestPaymentCNY")
    private CommonNodeEntity principalAndInterestPaymentCNY;

    /**
     * 兑付总份额
     */
    @XmlElement(name = "wemax:DeliveredUnits")
    private CommonNodeEntity deliveredUnits;

    /**
     * 本机构托管费
     */
    @XmlElement(name = "wemax:InternalCustodianFeeCNY")
    private CommonNodeEntity internalCustodianFeeCNY;

    /**
     * 本机构管理费
     */
    @XmlElement(name = "wemax:InternalManagementFeeCNY")
    private CommonNodeEntity internalManagementFeeCNY;

    /**
     * 本机构销售手续费
     */
    @XmlElement(name = "wemax:InternalSalesCommissionCNY")
    private CommonNodeEntity internalSalesCommissionCNY;

    /**
     * 本机构其他产品费用
     */
    @XmlElement(name = "wemax:InternalOtherProductFeeCNY")
    private CommonNodeEntity internalOtherProductFeeCNY;

    /**
     * 其他机构托管费
     */
    @XmlElement(name = "wemax:OtherInstitutionCustodianFeeCNY")
    private CommonNodeEntity otherInstitutionCustodianFeeCNY;

    /**
     * 其他机构管理费
     */
    @XmlElement(name = "wemax:OtherInstitutionManagementFeeCNY")
    private CommonNodeEntity otherInstitutionManagementFeeCNY;

    /**
     * 其他机构销售手续费
     */
    @XmlElement(name = "wemax:OtherInstitutionSalesCommissionCNY")
    private CommonNodeEntity otherInstitutionSalesCommissionCNY;

    /**
     * 投资顾问费用
     */
    @XmlElement(name = "wemax:InvestmentConsultingFeeCNY")
    private CommonNodeEntity investmentConsultingFeeCNY;

    /**
     * 其他机构其他产品费用
     */
    @XmlElement(name = "wemax:OtherInstitutionOtherProductFeeCNY")
    private CommonNodeEntity otherInstitutionOtherProductFeeCNY;

    /**
     * 客户实际年化收益率
     */
    @XmlElement(name = "wemax:RealizedAnnualReturnToInvestors")
    private CommonNodeEntity realizedAnnualReturnToInvestors;

    /**
     * 产品实际年化收益率
     */
    @XmlElement(name = "wemax:RealizedAnnualReturnToProduct")
    private CommonNodeEntity realizedAnnualReturnToProduct;


    /**
     * 资金托管账号
     */
    @XmlElement(name = "wemax:FundCustodyAccountNumber")
    private CommonNodeEntity fundCustodyAccountNumber;

    /**
     * 资金托管账户
     */
    @XmlElement(name = "wemax:FundCustodyAccountName")
    private CommonNodeEntity fundCustodyAccountName;

    /**
     * 个人投资者总数
     */
    @XmlElement(name = "wemax:NumberOfIndividualInvestors")
    private CommonNodeEntity numberOfIndividualInvestors;

    /**
     * 法人投资者总数
     */
    @XmlElement(name = "wemax:NumberOfCorporateInvestors")
    private CommonNodeEntity numberOfCorporateInvestors;

    /**
     * 非法人投资者总数
     */
    @XmlElement(name = "wemax:NumberOfUnincorporatedInvestors")
    private CommonNodeEntity numberOfUnincorporatedInvestors;

    /**
     * 实际募集金额
     */
    @XmlElement(name = "wemax:ActualSubscribedAmountCNY")
    private CommonNodeEntity actualSubscribedAmountCNY;

    /**
     * 募集总份额
     */
    @XmlElement(name = "wemax:SubscribedUnits")
    private CommonNodeEntity subscribedUnits;

    /**
     * 是否有其他机构代销
     */
    @XmlElement(name = "wemax:OtherDistributionAgentsPleaseSelect")
    private CommonNodeEntity otherDistributionAgentsPleaseSelect;

    /**
     * 代销总金额
     */
    @XmlElement(name = "wemax:AmountByOtherDistributionAgents")
    private CommonNodeEntity amountByOtherDistributionAgents;

    /**
     * 产品销售区域及募集金额
     */
    @XmlElement(name = "wemax:ProductAreaAndSubscriptionAmountTuple")
    private List<CommonNodeListEntity> productAreaAndSubscriptionAmountTuple;

    /**
     * 认购币种及金额
     */
    @XmlElement(name = "wemax:SubscriptionCurrencyAndSubscriptionAmountTuple")
    private List<CommonNodeListEntity> subscriptionCurrencyAndSubscriptionAmountTuple;

    /**
     * 募集总量登记备注
     */
    @XmlElement(name = "wemax:InitialSubscriptionRegistrationDetails")
    private CommonNodeEntity initialSubscriptionRegistrationDetails;

    /**
     * 初始净值
     */
    @XmlElement(name = "wemax:InitialNetValue")
    private CommonNodeEntity initialNetValue;

    /**
     * 产品净值
     */
    @XmlElement(name = "wemax:NetValueOfProduct")
    private CommonNodeEntity netValueOfProduct;

    /**
     * 累计净值
     */
    @XmlElement(name = "wemax:AggregateNetValue")
    private CommonNodeEntity aggregateNetValue;

    /**
     * 折算人民币初始净值
     */
    @XmlElement(name = "wemax:InitialNetValueCNY")
    private CommonNodeEntity initialNetValueCNY;

    /**
     * 折算人民币净值
     */
    @XmlElement(name = "wemax:NetValueOfProductCNY")
    private CommonNodeEntity netValueOfProductCNY;

    /**
     * 折算人民币累计净值
     */
    @XmlElement(name = "wemax:AggregateNetValueCNY")
    private CommonNodeEntity aggregateNetValueCNY;

    /***1217升级**/
    /**
     * 净值日期
     */
    @XmlElement(name = "wemax:NetValueDate")
    private CommonNodeEntity netValueDate;
    /***1217升级**/

    /**
     * 实现收益率
     */
    @XmlElement(name = "wemax:RealizedAnnualReturn")
    private CommonNodeEntity realizedAnnualReturn;

    /**
     * 最新预期收益率
     */
    @XmlElement(name = "wemax:ExpectedAnnualReturn")
    private CommonNodeEntity expectedAnnualReturn;


    /**
     * 银行实际实现收入
     */
    @XmlElement(name = "wemax:NetIncomeOfBankCNY")
    private CommonNodeEntity netIncomeOfBankCNY;


    /**
     * 业务起始日
     */
    @XmlElement(name = "wemax:BusinessStartDate")
    private CommonNodeEntity businessStartDate;

    /**
     * 业务结束日
     */
    @XmlElement(name = "wemax:BusinessEndDate")
    private CommonNodeEntity businessEndDate;

    /**
     * 累计申购份额
     */
    @XmlElement(name = "wemax:SubscribedUnitsOverLatestSubscriptionPeriod")
    private CommonNodeEntity subscribedUnitsOverLatestSubscriptionPeriod;

    /**
     * 累计赎回份额
     */
    @XmlElement(name = "wemax:RedeemedUnitsOverLatestSubscriptionPeriod")
    private CommonNodeEntity redeemedUnitsOverLatestSubscriptionPeriod;
    /**
     * 每万份份额分红
     */
    @XmlElement(name = "wemax:UnitsBonusPerTenThousandUnits")
    private CommonNodeEntity unitsBonusPerTenThousandUnits;
    /**
     * 每万份现金分红
     */
    @XmlElement(name = "wemax:CashBonusPerTenThousandUnitsCNY")
    private CommonNodeEntity cashBonusPerTenThousandUnitsCNY;

    /**
     * 累计赎回份额
     */
    @XmlElement(name = "wemax:ProductOutstandingAmountCNY")
    private CommonNodeEntity productOutstandingAmountCNY;

    /**
     * 产品余额
     */
    @XmlElement(name = "wemax:OutstandingAmountCNY")
    private CommonNodeEntity outstandingAmountCNY;

    /**
     * 产品份额
     */
    @XmlElement(name = "wemax:OutstandingUnits")
    private CommonNodeEntity outstandingUnits;

    /**
     * 币种及申购兑付信息
     */
    @XmlElement(name = "wemax:CurrencySubscriptionAndRedemptionTuple")
    private List<CommonNodeListEntity> currencySubscriptionAndRedemptionTuple;

    /**
     * 存续期登记备注
     */
    @XmlElement(name = "wemax:SubsequentSubscriptionRegistrationDetails")
    private CommonNodeEntity subsequentSubscriptionRegistrationDetails;

    /**
     * 从业人员类型
     */
    @XmlElement(name = "wemax:Profession")
    private CommonNodeEntity profession;

    /**
     * 从业人员姓名
     */
    @XmlElement(name = "wemax:Name")
    private CommonNodeEntity name;

    /**
     * 性别
     */
    @XmlElement(name = "wemax:Gender")
    private CommonNodeEntity gender;

    /**
     * 身份证号
     */
    @XmlElement(name = "wemax:PractitionerIdNumber")
    private CommonNodeEntity practitionerIdNumber;

    /**
     * 所属总行或分支行
     */
    @XmlElement(name = "wemax:HeadquartersBranchOfficePleaseSelect")
    private CommonNodeEntity headquartersBranchOfficePleaseSelect;

    /**
     * 所属区域
     */
    @XmlElement(name = "wemax:Region")
    private CommonNodeEntity region;

    /**
     * 具体单位名称
     */
    @XmlElement(name = "wemax:FirmName")
    private CommonNodeEntity firmName;

    /**
     * 所属部门
     */
    @XmlElement(name = "wemax:Department")
    private CommonNodeEntity department;

    /**
     * 职位
     */
    @XmlElement(name = "wemax:Position")
    private CommonNodeEntity position;

    /**
     * 学历
     */
    @XmlElement(name = "wemax:Education")
    private CommonNodeEntity education;

    /**
     * 学位
     */
    @XmlElement(name = "wemax:Degree")
    private CommonNodeEntity degree;

    /**
     * 首次参加工作时间
     */
    @XmlElement(name = "wemax:CareerStartDate")
    private CommonNodeEntity careerStartDate;

    /**
     * 首次从事理财业务时间
     */
    @XmlElement(name = "wemax:StartDateOfWorkingExperienceInWealthManagement")
    private CommonNodeEntity startDateOfWorkingExperienceInWealthManagement;

    /**
     * 专业技术职称
     */
    @XmlElement(name = "wemax:LevelofProfessionalQualifications")
    private CommonNodeEntity levelofProfessionalQualifications;

    /**
     * 理财专业证书
     */
    @XmlElement(name = "wemax:WealthManagementCertificationTuple")
    private List<CommonNodeListEntity> wealthManagementCertificationTuple;

    /**
     * 理财登记培训证书编号
     */
    @XmlElement(name = "wemax:WealthManagementRegistrationTrainingCertificateNumber")
    private CommonNodeEntity wealthManagementRegistrationTrainingCertificateNumber;

    /**
     * 所获奖励
     */
    @XmlElement(name = "wemax:RewardsInWealthManagement")
    private CommonNodeEntity rewardsInWealthManagement;

    /**
     * 办公电话
     */
    @XmlElement(name = "wemax:WorkPhoneNumberForPractitioner")
    private CommonNodeEntity workPhoneNumberForPractitioner;

    /**
     * 移动电话
     */
    @XmlElement(name = "wemax:MobilePhoneNumberForPractitioner")
    private CommonNodeEntity mobilePhoneNumberForPractitioner;

    /**
     * 从业人员电子邮箱
     */
    @XmlElement(name = "wemax:Email")
    private CommonNodeEntity email;

    /**
     * 登记业务分类
     */
    @XmlElement(name = "wemax:RegistrationClassificationTuple")
    private List<CommonNodeListEntity> registrationClassificationTuple;

    /**
     * 登记人员类别
     */
    @XmlElement(name = "wemax:TypeOfRegistrarTuple")
    private List<CommonNodeListEntity> typeOfRegistrarTuple;

    /**
     * 从业人员登记备注
     */
    @XmlElement(name = "wemax:PractitionerRegistrationDetails")
    private CommonNodeEntity practitionerRegistrationDetails;

    /**
     * 登记银行代码
     */
    @XmlElement(name = "wemax:RegistrationBankCode")
    private CommonNodeEntity registrationBankCode;

    /**
     * 登记流水号
     */
    @XmlElement(name = "wemax:RegistrationSerialNumber")
    private CommonNodeEntity registrationSerialNumber;

    /**
     * 分类总量登记明细
     */
    @XmlElement(name = "wemax:InvestorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple")
    private List<CommonNodeListEntity> investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple;

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

    /**
     * 子节点(分类总量)
     */
    @XmlElement(name = "wemax:InvestorsSummationOfDiffenrentTypesInformationRegistrationTuple")
    private List<BaseInfoEntity> investorsSummationOfDiffenrentTypesInformationRegistrationTuple;

    /**
     * 子节点(分类总量)
     */
    @XmlElement(name = "wemax:InvestorsInformationRegistrationCSVDataExplanatory")
    private CommonNodeEntity investorsInformationRegistrationCSVDataExplanatory;

    /**
     * 业务种类
     */
    @XmlElement(name = "wemax:NewProductPleaseSelect")
    private CommonNodeEntity newProductPleaseSelect;




    /**
     * 获取 methodOfAssetMeasurement
     *
     * @return methodOfAssetMeasurement.
     */
    public CommonNodeEntity getMethodOfAssetMeasurement() {
        return methodOfAssetMeasurement;
    }

    /**
     * 设置 methodOfAssetMeasurement
     *
     * @param methodOfAssetMeasurement methodOfAssetMeasurement
     */
    public void setMethodOfAssetMeasurement(CommonNodeEntity methodOfAssetMeasurement) {
        this.methodOfAssetMeasurement = methodOfAssetMeasurement;
    }

    /**
     * 获取 counterpartyType
     *
     * @return counterpartyType.
     */
    public CommonNodeEntity getCounterpartyType() {
        return counterpartyType;
    }

    /**
     * 设置 counterpartyType
     *
     * @param counterpartyType counterpartyType
     */
    public void setCounterpartyType(CommonNodeEntity counterpartyType) {
        this.counterpartyType = counterpartyType;
    }

    /**
     * 获取 productName
     *
     * @return productName.
     */
    public CommonNodeEntity getProductName() {
        return productName;
    }

    /**
     * 设置 productName
     *
     * @param productName productName
     */
    public void setProductName(CommonNodeEntity productName) {
        this.productName = productName;
    }

    /**
     * 获取 preidentificationCode
     *
     * @return preidentificationCode.
     */
    public CommonNodeEntity getPreidentificationCode() {
        return preidentificationCode;
    }

    /**
     * 设置 preidentificationCode
     *
     * @param preidentificationCode preidentificationCode
     */
    public void setPreidentificationCode(CommonNodeEntity preidentificationCode) {
        this.preidentificationCode = preidentificationCode;
    }

    /**
     * 获取 issuerIdentificationCode
     *
     * @return issuerIdentificationCode.
     */
    public CommonNodeEntity getIssuerIdentificationCode() {
        return issuerIdentificationCode;
    }

    /**
     * 设置 issuerIdentificationCode
     *
     * @param issuerIdentificationCode issuerIdentificationCode
     */
    public void setIssuerIdentificationCode(CommonNodeEntity issuerIdentificationCode) {
        this.issuerIdentificationCode = issuerIdentificationCode;
    }

    /**
     * 获取 productApproverIdNumber
     *
     * @return productApproverIdNumber.
     */
    public CommonNodeEntity getProductApproverIdNumber() {
        return productApproverIdNumber;
    }

    /**
     * 设置 productApproverIdNumber
     *
     * @param productApproverIdNumber productApproverIdNumber
     */
    public void setProductApproverIdNumber(CommonNodeEntity productApproverIdNumber) {
        this.productApproverIdNumber = productApproverIdNumber;
    }

    /**
     * 获取 productDesignerIdNumber
     *
     * @return productDesignerIdNumber.
     */
    public CommonNodeEntity getProductDesignerIdNumber() {
        return productDesignerIdNumber;
    }

    /**
     * 设置 productDesignerIdNumber
     *
     * @param productDesignerIdNumber productDesignerIdNumber
     */
    public void setProductDesignerIdNumber(CommonNodeEntity productDesignerIdNumber) {
        this.productDesignerIdNumber = productDesignerIdNumber;
    }

    /**
     * 获取 investmentManagerIdNumber
     *
     * @return investmentManagerIdNumber.
     */
    public CommonNodeEntity getInvestmentManagerIdNumber() {
        return investmentManagerIdNumber;
    }

    /**
     * 设置 investmentManagerIdNumber
     *
     * @param investmentManagerIdNumber investmentManagerIdNumber
     */
    public void setInvestmentManagerIdNumber(CommonNodeEntity investmentManagerIdNumber) {
        this.investmentManagerIdNumber = investmentManagerIdNumber;
    }

    /**
     * 获取 filingContactName
     *
     * @return filingContactName.
     */
    public CommonNodeEntity getFilingContactName() {
        return filingContactName;
    }

    /**
     * 设置 filingContactName
     *
     * @param filingContactName filingContactName
     */
    public void setFilingContactName(CommonNodeEntity filingContactName) {
        this.filingContactName = filingContactName;
    }

    /**
     * 获取 filingContactWorkPhone
     *
     * @return filingContactWorkPhone.
     */
    public CommonNodeEntity getFilingContactWorkPhone() {
        return filingContactWorkPhone;
    }

    /**
     * 设置 filingContactWorkPhone
     *
     * @param filingContactWorkPhone filingContactWorkPhone
     */
    public void setFilingContactWorkPhone(CommonNodeEntity filingContactWorkPhone) {
        this.filingContactWorkPhone = filingContactWorkPhone;
    }

    /**
     * 获取 filingContactMobilePhone
     *
     * @return filingContactMobilePhone.
     */
    public CommonNodeEntity getFilingContactMobilePhone() {
        return filingContactMobilePhone;
    }

    /**
     * 设置 filingContactMobilePhone
     *
     * @param filingContactMobilePhone filingContactMobilePhone
     */
    public void setFilingContactMobilePhone(CommonNodeEntity filingContactMobilePhone) {
        this.filingContactMobilePhone = filingContactMobilePhone;
    }

    /**
     * 获取 filingContactEmail
     *
     * @return filingContactEmail.
     */
    public CommonNodeEntity getFilingContactEmail() {
        return filingContactEmail;
    }

    /**
     * 设置 filingContactEmail
     *
     * @param filingContactEmail filingContactEmail
     */
    public void setFilingContactEmail(CommonNodeEntity filingContactEmail) {
        this.filingContactEmail = filingContactEmail;
    }

    /**
     * 获取 productReturnType
     *
     * @return productReturnType.
     */
    public CommonNodeEntity getProductReturnType() {
        return productReturnType;
    }

    /**
     * 设置 productTermToMaturityOfPublicOrPrivateProduct
     *
     * @param productTermToMaturityOfPublicOrPrivateProduct productTermToMaturityOfPublicOrPrivateProduct
     */
    public void setProductTermToMaturityOfPublicOrPrivateProduct(CommonNodeEntity productTermToMaturityOfPublicOrPrivateProduct) {
        this.productTermToMaturityOfPublicOrPrivateProduct = productTermToMaturityOfPublicOrPrivateProduct;
    }
    /**
     * 获取 productTermToMaturityOfPublicOrPrivateProduct
     *
     * @return productTermToMaturityOfPublicOrPrivateProduct.
     */
    public CommonNodeEntity getProductTermToMaturityOfPublicOrPrivateProduct() {
        return productTermToMaturityOfPublicOrPrivateProduct;
    }

    /**
     * 设置 productReturnType
     *
     * @param productReturnType productReturnType
     */
    public void setProductReturnType(CommonNodeEntity productReturnType) {
        this.productReturnType = productReturnType;
    }
    /**
     * 获取 productTermToMaturity
     *
     * @return productTermToMaturity.
     */
    public CommonNodeEntity getProductTermToMaturity() {
        return productTermToMaturity;
    }

    /**
     * 设置 productTermToMaturity
     *
     * @param productTermToMaturity productTermToMaturity
     */
    public void setProductTermToMaturity(CommonNodeEntity productTermToMaturity) {
        this.productTermToMaturity = productTermToMaturity;
    }

    /**
     * 获取 investmentRegion
     *
     * @return investmentRegion.
     */
    public CommonNodeEntity getInvestmentRegion() {
        return investmentRegion;
    }

    /**
     * 设置 investmentRegion
     *
     * @param investmentRegion investmentRegion
     */
    public void setInvestmentRegion(CommonNodeEntity investmentRegion) {
        this.investmentRegion = investmentRegion;
    }

    /**
     * 获取 specificCountryOrRegionTuple
     *
     * @return specificCountryOrRegionTuple.
     */
    public List<CommonNodeListEntity> getSpecificCountryOrRegionTuple() {
        return specificCountryOrRegionTuple;
    }

    /**
     * 设置 specificCountryOrRegionTuple
     *
     * @param specificCountryOrRegionTuple specificCountryOrRegionTuple
     */
    public void setSpecificCountryOrRegionTuple(List<CommonNodeListEntity> specificCountryOrRegionTuple) {
        this.specificCountryOrRegionTuple = specificCountryOrRegionTuple;
    }

    /**
     * 获取 wealthManagementServiceMode
     *
     * @return wealthManagementServiceMode.
     */
    public CommonNodeEntity getWealthManagementServiceMode() {
        return wealthManagementServiceMode;
    }

    /**
     * 设置 wealthManagementServiceMode
     *
     * @param wealthManagementServiceMode wealthManagementServiceMode
     */
    public void setWealthManagementServiceMode(CommonNodeEntity wealthManagementServiceMode) {
        this.wealthManagementServiceMode = wealthManagementServiceMode;
    }

    /**
     * 获取 productOperationMode
     *
     * @return productOperationMode.
     */
    public CommonNodeEntity getProductOperationMode() {
        return productOperationMode;
    }

    /**
     * 设置 productOperationMode
     *
     * @param productOperationMode productOperationMode
     */
    public void setProductOperationMode(CommonNodeEntity productOperationMode) {
        this.productOperationMode = productOperationMode;
    }

    /**
     * 获取 accountingMethod
     *
     * @return accountingMethod.
     */
    public CommonNodeEntity getAccountingMethod() {
        return accountingMethod;
    }

    /**
     * 设置 accountingMethod
     *
     * @param accountingMethod accountingMethod
     */
    public void setAccountingMethod(CommonNodeEntity accountingMethod) {
        this.accountingMethod = accountingMethod;
    }

    /**
     * 获取 productAssetAllocationMethod
     *
     * @return productAssetAllocationMethod.
     */
    public CommonNodeEntity getProductAssetAllocationMethod() {
        return productAssetAllocationMethod;
    }

    /**
     * 设置 productAssetAllocationMethod
     *
     * @param productAssetAllocationMethod productAssetAllocationMethod
     */
    public void setProductAssetAllocationMethod(CommonNodeEntity productAssetAllocationMethod) {
        this.productAssetAllocationMethod = productAssetAllocationMethod;
    }

    /**
     * 获取 productManagementMode
     *
     * @return productManagementMode.
     */
    public CommonNodeEntity getProductManagementMode() {
        return productManagementMode;
    }

    /**
     * 设置 productManagementMode
     *
     * @param productManagementMode productManagementMode
     */
    public void setProductManagementMode(CommonNodeEntity productManagementMode) {
        this.productManagementMode = productManagementMode;
    }

    /**
     * 获取 actualProductManagerName
     *
     * @return actualProductManagerName.
     */
    public CommonNodeEntity getActualProductManagerName() {
        return actualProductManagerName;
    }

    /**
     * 设置 actualProductManagerName
     *
     * @param actualProductManagerName actualProductManagerName
     */
    public void setActualProductManagerName(CommonNodeEntity actualProductManagerName) {
        this.actualProductManagerName = actualProductManagerName;
    }

    /**
     * 获取 pricingMethod
     *
     * @return pricingMethod.
     */
    public CommonNodeEntity getPricingMethod() {
        return pricingMethod;
    }

    /**
     * 设置 pricingMethod
     *
     * @param pricingMethod pricingMethod
     */
    public void setPricingMethod(CommonNodeEntity pricingMethod) {
        this.pricingMethod = pricingMethod;
    }

    /**
     * 获取 investmentAssetType
     *
     * @return investmentAssetType.
     */
    public CommonNodeEntity getInvestmentAssetType() {
        return investmentAssetType;
    }

    /**
     * 设置 investmentAssetType
     *
     * @param investmentAssetType investmentAssetType
     */
    public void setInvestmentAssetType(CommonNodeEntity investmentAssetType) {
        this.investmentAssetType = investmentAssetType;
    }

    /**
     * 获取 structuredProductUnderlyingAssetTuple
     *
     * @return structuredProductUnderlyingAssetTuple.
     */
    public List<CommonNodeListEntity> getStructuredProductUnderlyingAssetTuple() {
        return structuredProductUnderlyingAssetTuple;
    }

    /**
     * 设置 structuredProductUnderlyingAssetTuple
     *
     * @param structuredProductUnderlyingAssetTuple structuredProductUnderlyingAssetTuple
     */
    public void setStructuredProductUnderlyingAssetTuple(List<CommonNodeListEntity> structuredProductUnderlyingAssetTuple) {
        this.structuredProductUnderlyingAssetTuple = structuredProductUnderlyingAssetTuple;
    }

    /**
     * 获取 expectedReturnPleaseSelect
     *
     * @return expectedReturnPleaseSelect.
     */
    public CommonNodeEntity getExpectedReturnPleaseSelect() {
        return expectedReturnPleaseSelect;
    }

    /**
     * 设置 expectedReturnPleaseSelect
     *
     * @param expectedReturnPleaseSelect expectedReturnPleaseSelect
     */
    public void setExpectedReturnPleaseSelect(CommonNodeEntity expectedReturnPleaseSelect) {
        this.expectedReturnPleaseSelect = expectedReturnPleaseSelect;
    }

    /**
     * 获取 returnCalculationBasisPleaseSelect
     *
     * @return returnCalculationBasisPleaseSelect.
     */
    public CommonNodeEntity getReturnCalculationBasisPleaseSelect() {
        return returnCalculationBasisPleaseSelect;
    }

    /**
     * 设置 returnCalculationBasisPleaseSelect
     *
     * @param returnCalculationBasisPleaseSelect returnCalculationBasisPleaseSelect
     */
    public void setReturnCalculationBasisPleaseSelect(CommonNodeEntity returnCalculationBasisPleaseSelect) {
        this.returnCalculationBasisPleaseSelect = returnCalculationBasisPleaseSelect;
    }

    /**
     * 获取 investorMaximumExpectedAnnualReturn
     *
     * @return investorMaximumExpectedAnnualReturn.
     */
    public CommonNodeEntity getInvestorMaximumExpectedAnnualReturn() {
        return investorMaximumExpectedAnnualReturn;
    }

    /**
     * 设置 investorMaximumExpectedAnnualReturn
     *
     * @param investorMaximumExpectedAnnualReturn investorMaximumExpectedAnnualReturn
     */
    public void setInvestorMaximumExpectedAnnualReturn(CommonNodeEntity investorMaximumExpectedAnnualReturn) {
        this.investorMaximumExpectedAnnualReturn = investorMaximumExpectedAnnualReturn;
    }

    /**
     * 获取 investorMinimumExpectedAnnualReturn
     *
     * @return investorMinimumExpectedAnnualReturn.
     */
    public CommonNodeEntity getInvestorMinimumExpectedAnnualReturn() {
        return investorMinimumExpectedAnnualReturn;
    }

    /**
     * 设置 investorMinimumExpectedAnnualReturn
     *
     * @param investorMinimumExpectedAnnualReturn investorMinimumExpectedAnnualReturn
     */
    public void setInvestorMinimumExpectedAnnualReturn(CommonNodeEntity investorMinimumExpectedAnnualReturn) {
        this.investorMinimumExpectedAnnualReturn = investorMinimumExpectedAnnualReturn;
    }

    /**
     * 获取 productSalesRegionTuple
     *
     * @return productSalesRegionTuple.
     */
    public List<CommonNodeListEntity> getProductSalesRegionTuple() {
        return productSalesRegionTuple;
    }

    /**
     * 设置 productSalesRegionTuple
     *
     * @param productSalesRegionTuple productSalesRegionTuple
     */
    public void setProductSalesRegionTuple(List<CommonNodeListEntity> productSalesRegionTuple) {
        this.productSalesRegionTuple = productSalesRegionTuple;
    }

    /**
     * 获取 investmentThreshold
     *
     * @return investmentThreshold.
     */
    public CommonNodeEntity getInvestmentThreshold() {
        return investmentThreshold;
    }

    /**
     * 设置 investmentThreshold
     *
     * @param investmentThreshold investmentThreshold
     */
    public void setInvestmentThreshold(CommonNodeEntity investmentThreshold) {
        this.investmentThreshold = investmentThreshold;
    }

    /**
     * 获取 fundingCurrency
     *
     * @return fundingCurrency.
     */
/*    public CommonNodeEntity getFundingCurrency() {
        return fundingCurrency;
    }
*/
    /**
     * 设置 fundingCurrency
     *
     * @param fundingCurrency fundingCurrency
     */
/*    public void setFundingCurrency(CommonNodeEntity fundingCurrency) {
        this.fundingCurrency = fundingCurrency;
    }
*/
    /**
     * 获取 principalPaymentCurrency
     *
     * @return principalPaymentCurrency.
     */
/*    public CommonNodeEntity getPrincipalPaymentCurrency() {
        return principalPaymentCurrency;
    }*/

    /**
     * 设置 principalPaymentCurrency
     *
     * @param principalPaymentCurrency principalPaymentCurrency
     */
/*    public void setPrincipalPaymentCurrency(CommonNodeEntity principalPaymentCurrency) {
        this.principalPaymentCurrency = principalPaymentCurrency;
    }*/

    /**
     * 获取 incomePaymentCurrencyTuple
     *
     * @return incomePaymentCurrencyTuple.
     */
    public List<CommonNodeListEntity> getIncomePaymentCurrencyTuple() {
        return incomePaymentCurrencyTuple;
    }

    /**
     * 设置 incomePaymentCurrencyTuple
     *
     * @param incomePaymentCurrencyTuple incomePaymentCurrencyTuple
     */
    public void setIncomePaymentCurrencyTuple(List<CommonNodeListEntity> incomePaymentCurrencyTuple) {
        this.incomePaymentCurrencyTuple = incomePaymentCurrencyTuple;
    }

    /**
     * 获取 salesCommissionRate
     *
     * @return salesCommissionRate.
     */
    public CommonNodeEntity getSalesCommissionRate() {
        return salesCommissionRate;
    }

    /**
     * 设置 salesCommissionRate
     *
     * @param salesCommissionRate salesCommissionRate
     */
    public void setSalesCommissionRate(CommonNodeEntity salesCommissionRate) {
        this.salesCommissionRate = salesCommissionRate;
    }

    /**
     * 获取 custodianFeeRate
     *
     * @return custodianFeeRate.
     */
    public CommonNodeEntity getCustodianFeeRate() {
        return custodianFeeRate;
    }

    /**
     * 设置 custodianFeeRate
     *
     * @param custodianFeeRate custodianFeeRate
     */
    public void setCustodianFeeRate(CommonNodeEntity custodianFeeRate) {
        this.custodianFeeRate = custodianFeeRate;
    }

    /**
     * 获取 subscriptionStartDateEarliest
     *
     * @return subscriptionStartDateEarliest.
     */
    public CommonNodeEntity getSubscriptionStartDateEarliest() {
        return subscriptionStartDateEarliest;
    }

    /**
     * 设置 subscriptionStartDateEarliest
     *
     * @param subscriptionStartDateEarliest subscriptionStartDateEarliest
     */
    public void setSubscriptionStartDateEarliest(CommonNodeEntity subscriptionStartDateEarliest) {
        this.subscriptionStartDateEarliest = subscriptionStartDateEarliest;
    }

    /**
     * 获取 subscriptionStartDateLatest
     *
     * @return subscriptionStartDateLatest.
     */
    public CommonNodeEntity getSubscriptionStartDateLatest() {
        return subscriptionStartDateLatest;
    }

    /**
     * 设置 subscriptionStartDateLatest
     *
     * @param subscriptionStartDateLatest subscriptionStartDateLatest
     */
    public void setSubscriptionStartDateLatest(CommonNodeEntity subscriptionStartDateLatest) {
        this.subscriptionStartDateLatest = subscriptionStartDateLatest;
    }

    /**
     * 获取 plannedFundingAmountCNY
     *
     * @return plannedFundingAmountCNY.
     */
    public CommonNodeEntity getPlannedFundingAmountCNY() {
        return plannedFundingAmountCNY;
    }

    /**
     * 设置 plannedFundingAmountCNY
     *
     * @param plannedFundingAmountCNY plannedFundingAmountCNY
     */
    public void setPlannedFundingAmountCNY(CommonNodeEntity plannedFundingAmountCNY) {
        this.plannedFundingAmountCNY = plannedFundingAmountCNY;
    }

    /**
     * 获取 domesticCustodianName
     *
     * @return domesticCustodianName.
     */
    public CommonNodeEntity getDomesticCustodianName() {
        return domesticCustodianName;
    }

    /**
     * 设置 domesticCustodianName
     *
     * @param domesticCustodianName domesticCustodianName
     */
    public void setDomesticCustodianName(CommonNodeEntity domesticCustodianName) {
        this.domesticCustodianName = domesticCustodianName;
    }

    /**
     * 获取 domesticCustodianIdentificationCode
     *
     * @return domesticCustodianIdentificationCode.
     */
    public CommonNodeEntity getDomesticCustodianIdentificationCode() {
        return domesticCustodianIdentificationCode;
    }

    /**
     * 设置 domesticCustodianIdentificationCode
     *
     * @param domesticCustodianIdentificationCode domesticCustodianIdentificationCode
     */
    public void setDomesticCustodianIdentificationCode(CommonNodeEntity domesticCustodianIdentificationCode) {
        this.domesticCustodianIdentificationCode = domesticCustodianIdentificationCode;
    }

    /**
     * 获取 overseasCustodianNationalityTuple
     *
     * @return overseasCustodianNationalityTuple.
     */
    public List<CommonNodeListEntity> getOverseasCustodianNationalityTuple() {
        return overseasCustodianNationalityTuple;
    }

    /**
     * 设置 overseasCustodianNationalityTuple
     *
     * @param overseasCustodianNationalityTuple overseasCustodianNationalityTuple
     */
    public void setOverseasCustodianNationalityTuple(List<CommonNodeListEntity> overseasCustodianNationalityTuple) {
        this.overseasCustodianNationalityTuple = overseasCustodianNationalityTuple;
    }

    /**
     * 获取 overseasCustodianName
     *
     * @return overseasCustodianName.
     */
    public CommonNodeEntity getOverseasCustodianName() {
        return overseasCustodianName;
    }

    /**
     * 设置 overseasCustodianName
     *
     * @param overseasCustodianName overseasCustodianName
     */
    public void setOverseasCustodianName(CommonNodeEntity overseasCustodianName) {
        this.overseasCustodianName = overseasCustodianName;
    }

    /**
     * 获取 investorRiskPreferenceTuple
     *
     * @return investorRiskPreferenceTuple.
     */
    public List<CommonNodeListEntity> getInvestorRiskPreferenceTuple() {
        return investorRiskPreferenceTuple;
    }

    /**
     * 设置 investorRiskPreferenceTuple
     *
     * @param investorRiskPreferenceTuple investorRiskPreferenceTuple
     */
    public void setInvestorRiskPreferenceTuple(List<CommonNodeListEntity> investorRiskPreferenceTuple) {
        this.investorRiskPreferenceTuple = investorRiskPreferenceTuple;
    }

    /**
     * 获取 productRiskRating
     *
     * @return productRiskRating.
     */
    public CommonNodeEntity getProductRiskRating() {
        return productRiskRating;
    }

    /**
     * 设置 productRiskRating
     *
     * @param productRiskRating productRiskRating
     */
    public void setProductRiskRating(CommonNodeEntity productRiskRating) {
        this.productRiskRating = productRiskRating;
    }

    /**
     * 获取 issuerEarlyTerminationOptionPleaseSelect
     *
     * @return issuerEarlyTerminationOptionPleaseSelect.
     */
    public CommonNodeEntity getIssuerEarlyTerminationOptionPleaseSelect() {
        return issuerEarlyTerminationOptionPleaseSelect;
    }

    /**
     * 设置 issuerEarlyTerminationOptionPleaseSelect
     *
     * @param issuerEarlyTerminationOptionPleaseSelect issuerEarlyTerminationOptionPleaseSelect
     */
    public void setIssuerEarlyTerminationOptionPleaseSelect(CommonNodeEntity issuerEarlyTerminationOptionPleaseSelect) {
        this.issuerEarlyTerminationOptionPleaseSelect = issuerEarlyTerminationOptionPleaseSelect;
    }

    /**
     * 获取 investorRedemptionOptionPleaseSelect
     *
     * @return investorRedemptionOptionPleaseSelect.
     */
    public CommonNodeEntity getInvestorRedemptionOptionPleaseSelect() {
        return investorRedemptionOptionPleaseSelect;
    }

    /**
     * 设置 investorRedemptionOptionPleaseSelect
     *
     * @param investorRedemptionOptionPleaseSelect investorRedemptionOptionPleaseSelect
     */
    public void setInvestorRedemptionOptionPleaseSelect(CommonNodeEntity investorRedemptionOptionPleaseSelect) {
        this.investorRedemptionOptionPleaseSelect = investorRedemptionOptionPleaseSelect;
    }

    /**
     * 获取 productBrand
     *
     * @return productBrand.
     */
    public CommonNodeEntity getProductBrand() {
        return productBrand;
    }

    /**
     * 设置 productBrand
     *
     * @param productBrand productBrand
     */
    public void setProductBrand(CommonNodeEntity productBrand) {
        this.productBrand = productBrand;
    }

    /**
     * 获取 productTermNumber
     *
     * @return productTermNumber.
     */
    public CommonNodeEntity getProductTermNumber() {
        return productTermNumber;
    }

    /**
     * 设置 productTermNumber
     *
     * @param productTermNumber productTermNumber
     */
    public void setProductTermNumber(CommonNodeEntity productTermNumber) {
        this.productTermNumber = productTermNumber;
    }

    /**
     * 获取 cooperationMode
     *
     * @return cooperationMode.
     */
    public CommonNodeEntity getCooperationMode() {
        return cooperationMode;
    }

    /**
     * 设置 cooperationMode
     *
     * @param cooperationMode cooperationMode
     */
    public void setCooperationMode(CommonNodeEntity cooperationMode) {
        this.cooperationMode = cooperationMode;
    }

    /**
     * 获取 cooperator
     *
     * @return cooperator.
     */
    public CommonNodeEntity getCooperator() {
        return cooperator;
    }

    /**
     * 设置 cooperator
     *
     * @param cooperator cooperator
     */
    public void setCooperator(CommonNodeEntity cooperator) {
        this.cooperator = cooperator;
    }

    /**
     * 获取 dueDateOfInvestedPrincipal
     *
     * @return dueDateOfInvestedPrincipal.
     */
    public CommonNodeEntity getDueDateOfInvestedPrincipal() {
        return dueDateOfInvestedPrincipal;
    }

    /**
     * 设置 dueDateOfInvestedPrincipal
     *
     * @param dueDateOfInvestedPrincipal dueDateOfInvestedPrincipal
     */
    public void setDueDateOfInvestedPrincipal(CommonNodeEntity dueDateOfInvestedPrincipal) {
        this.dueDateOfInvestedPrincipal = dueDateOfInvestedPrincipal;
    }

    /**
     * 获取 dueDateOfInvestmentIncome
     *
     * @return dueDateOfInvestmentIncome.
     */
    public CommonNodeEntity getDueDateOfInvestmentIncome() {
        return dueDateOfInvestmentIncome;
    }

    /**
     * 设置 dueDateOfInvestmentIncome
     *
     * @param dueDateOfInvestmentIncome dueDateOfInvestmentIncome
     */
    public void setDueDateOfInvestmentIncome(CommonNodeEntity dueDateOfInvestmentIncome) {
        this.dueDateOfInvestmentIncome = dueDateOfInvestmentIncome;
    }

    /**
     * 获取 productCreditEnhancementPleaseSelect
     *
     * @return productCreditEnhancementPleaseSelect.
     */
    public CommonNodeEntity getProductCreditEnhancementPleaseSelect() {
        return productCreditEnhancementPleaseSelect;
    }

    /**
     * 设置 productCreditEnhancementPleaseSelect
     *
     * @param productCreditEnhancementPleaseSelect productCreditEnhancementPleaseSelect
     */
    public void setProductCreditEnhancementPleaseSelect(CommonNodeEntity productCreditEnhancementPleaseSelect) {
        this.productCreditEnhancementPleaseSelect = productCreditEnhancementPleaseSelect;
    }

    /**
     * 获取 typeOfProductCreditEnhancementInstitutionTuple
     *
     * @return typeOfProductCreditEnhancementInstitutionTuple.
     */
    public List<CommonNodeListEntity> getTypeOfProductCreditEnhancementInstitutionTuple() {
        return typeOfProductCreditEnhancementInstitutionTuple;
    }

    /**
     * 设置 typeOfProductCreditEnhancementInstitutionTuple
     *
     * @param typeOfProductCreditEnhancementInstitutionTuple
     *            typeOfProductCreditEnhancementInstitutionTuple
     */
    public void setTypeOfProductCreditEnhancementInstitutionTuple(List<CommonNodeListEntity> typeOfProductCreditEnhancementInstitutionTuple) {
        this.typeOfProductCreditEnhancementInstitutionTuple = typeOfProductCreditEnhancementInstitutionTuple;
    }

    /**
     * 获取 methodOfProductCreditEnhancement
     *
     * @return methodOfProductCreditEnhancement.
     */
    public CommonNodeEntity getMethodOfProductCreditEnhancement() {
        return methodOfProductCreditEnhancement;
    }

    /**
     * 设置 methodOfProductCreditEnhancement
     *
     * @param methodOfProductCreditEnhancement methodOfProductCreditEnhancement
     */
    public void setMethodOfProductCreditEnhancement(CommonNodeEntity methodOfProductCreditEnhancement) {
        this.methodOfProductCreditEnhancement = methodOfProductCreditEnhancement;
    }

    /**
     * 获取 investmentAssetTypeAndRatio
     *
     * @return investmentAssetTypeAndRatio.
     */
    public CommonNodeEntity getInvestmentAssetTypeAndRatio() {
        return investmentAssetTypeAndRatio;
    }

    /**
     * 设置 investmentAssetTypeAndRatio
     *
     * @param investmentAssetTypeAndRatio investmentAssetTypeAndRatio
     */
    public void setInvestmentAssetTypeAndRatio(CommonNodeEntity investmentAssetTypeAndRatio) {
        this.investmentAssetTypeAndRatio = investmentAssetTypeAndRatio;
    }

    /**
     * 获取 filingRegistrationDetails
     *
     * @return filingRegistrationDetails.
     */
    public CommonNodeEntity getFilingRegistrationDetails() {
        return filingRegistrationDetails;
    }

    /**
     * 设置 filingRegistrationDetails
     *
     * @param filingRegistrationDetails filingRegistrationDetails
     */
    public void setFilingRegistrationDetails(CommonNodeEntity filingRegistrationDetails) {
        this.filingRegistrationDetails = filingRegistrationDetails;
    }

    /**
     * 获取 mainDocumentsTuple
     *
     * @return mainDocumentsTuple.
     */
    public List<CommonNodeListEntity> getMainDocumentsTuple() {
        return mainDocumentsTuple;
    }

    /**
     * 设置 mainDocumentsTuple
     *
     * @param mainDocumentsTuple mainDocumentsTuple
     */
    public void setMainDocumentsTuple(List<CommonNodeListEntity> mainDocumentsTuple) {
        this.mainDocumentsTuple = mainDocumentsTuple;
    }

    /**
     * 获取 wealthManagementProductFeasibilityReportsTuple
     *
     * @return wealthManagementProductFeasibilityReportsTuple.
     */
    public List<CommonNodeListEntity> getWealthManagementProductFeasibilityReportsTuple() {
        return wealthManagementProductFeasibilityReportsTuple;
    }

    /**
     * 设置 wealthManagementProductFeasibilityReportsTuple
     *
     * @param wealthManagementProductFeasibilityReportsTuple
     *            wealthManagementProductFeasibilityReportsTuple
     */
    public void setWealthManagementProductFeasibilityReportsTuple(List<CommonNodeListEntity> wealthManagementProductFeasibilityReportsTuple) {
        this.wealthManagementProductFeasibilityReportsTuple = wealthManagementProductFeasibilityReportsTuple;
    }

    /**
     * 获取 internalAuthorizedDocumentsTuple
     *
     * @return internalAuthorizedDocumentsTuple.
     */
    public List<CommonNodeListEntity> getInternalAuthorizedDocumentsTuple() {
        return internalAuthorizedDocumentsTuple;
    }

    /**
     * 设置 internalAuthorizedDocumentsTuple
     *
     * @param internalAuthorizedDocumentsTuple internalAuthorizedDocumentsTuple
     */
    public void setInternalAuthorizedDocumentsTuple(List<CommonNodeListEntity> internalAuthorizedDocumentsTuple) {
        this.internalAuthorizedDocumentsTuple = internalAuthorizedDocumentsTuple;
    }

    /**
     * 获取 dueDiligenceDocumentsOnRelevantPartiesTuple
     *
     * @return dueDiligenceDocumentsOnRelevantPartiesTuple.
     */
    public List<CommonNodeListEntity> getDueDiligenceDocumentsOnRelevantPartiesTuple() {
        return dueDiligenceDocumentsOnRelevantPartiesTuple;
    }

    /**
     * 设置 dueDiligenceDocumentsOnRelevantPartiesTuple
     *
     * @param dueDiligenceDocumentsOnRelevantPartiesTuple
     *            dueDiligenceDocumentsOnRelevantPartiesTuple
     */
    public void setDueDiligenceDocumentsOnRelevantPartiesTuple(List<CommonNodeListEntity> dueDiligenceDocumentsOnRelevantPartiesTuple) {
        this.dueDiligenceDocumentsOnRelevantPartiesTuple = dueDiligenceDocumentsOnRelevantPartiesTuple;
    }

    /**
     * 获取 legalDocumentsSignedByRelevantPartiesTuple
     *
     * @return legalDocumentsSignedByRelevantPartiesTuple.
     */
    public List<CommonNodeListEntity> getLegalDocumentsSignedByRelevantPartiesTuple() {
        return legalDocumentsSignedByRelevantPartiesTuple;
    }

    /**
     * 设置 legalDocumentsSignedByRelevantPartiesTuple
     *
     * @param legalDocumentsSignedByRelevantPartiesTuple legalDocumentsSignedByRelevantPartiesTuple
     */
    public void setLegalDocumentsSignedByRelevantPartiesTuple(List<CommonNodeListEntity> legalDocumentsSignedByRelevantPartiesTuple) {
        this.legalDocumentsSignedByRelevantPartiesTuple = legalDocumentsSignedByRelevantPartiesTuple;
    }

    /**
     * 获取 wealthManagementProductSalesDocumentsTuple
     *
     * @return wealthManagementProductSalesDocumentsTuple.
     */
    public List<CommonNodeListEntity> getWealthManagementProductSalesDocumentsTuple() {
        return wealthManagementProductSalesDocumentsTuple;
    }

    /**
     * 设置 wealthManagementProductSalesDocumentsTuple
     *
     * @param wealthManagementProductSalesDocumentsTuple wealthManagementProductSalesDocumentsTuple
     */
    public void setWealthManagementProductSalesDocumentsTuple(List<CommonNodeListEntity> wealthManagementProductSalesDocumentsTuple) {
        this.wealthManagementProductSalesDocumentsTuple = wealthManagementProductSalesDocumentsTuple;
    }

    /**
     * 获取 wealthManagementProductSpecificationsTuple
     *
     * @return wealthManagementProductSpecificationsTuple.
     */
    public List<CommonNodeListEntity> getWealthManagementProductSpecificationsTuple() {
        return wealthManagementProductSpecificationsTuple;
    }

    /**
     * 设置 wealthManagementProductSpecificationsTuple
     *
     * @param wealthManagementProductSpecificationsTuple wealthManagementProductSpecificationsTuple
     */
    public void setWealthManagementProductSpecificationsTuple(List<CommonNodeListEntity> wealthManagementProductSpecificationsTuple) {
        this.wealthManagementProductSpecificationsTuple = wealthManagementProductSpecificationsTuple;
    }

    /**
     * 获取 wealthManagementProductMarketingDocumentsTuple
     *
     * @return wealthManagementProductMarketingDocumentsTuple.
     */
    public List<CommonNodeListEntity> getWealthManagementProductMarketingDocumentsTuple() {
        return wealthManagementProductMarketingDocumentsTuple;
    }

    /**
     * 设置 wealthManagementProductMarketingDocumentsTuple
     *
     * @param wealthManagementProductMarketingDocumentsTuple
     *            wealthManagementProductMarketingDocumentsTuple
     */
    public void setWealthManagementProductMarketingDocumentsTuple(List<CommonNodeListEntity> wealthManagementProductMarketingDocumentsTuple) {
        this.wealthManagementProductMarketingDocumentsTuple = wealthManagementProductMarketingDocumentsTuple;
    }

    /**
     * 获取 otherDocumentsTuple
     *
     * @return otherDocumentsTuple.
     */
    public List<CommonNodeListEntity> getOtherDocumentsTuple() {
        return otherDocumentsTuple;
    }

    /**
     * 设置 otherDocumentsTuple
     *
     * @param otherDocumentsTuple otherDocumentsTuple
     */
    public void setOtherDocumentsTuple(List<CommonNodeListEntity> otherDocumentsTuple) {
        this.otherDocumentsTuple = otherDocumentsTuple;
    }

    /**
     * 获取 investorType
     *
     * @return investorType.
     */
    public CommonNodeEntity getInvestorType() {
        return investorType;
    }

    /**
     * 设置 investorType
     *
     * @param investorType investorType
     */
    public void setInvestorType(CommonNodeEntity investorType) {
        this.investorType = investorType;
    }

    /**
     * 获取 investorAverageExpectedAnnualReturn
     *
     * @return investorAverageExpectedAnnualReturn.
     */
    public CommonNodeEntity getInvestorAverageExpectedAnnualReturn() {
        return investorAverageExpectedAnnualReturn;
    }

    /**
     * 设置 investorAverageExpectedAnnualReturn
     *
     * @param investorAverageExpectedAnnualReturn investorAverageExpectedAnnualReturn
     */
    public void setInvestorAverageExpectedAnnualReturn(CommonNodeEntity investorAverageExpectedAnnualReturn) {
        this.investorAverageExpectedAnnualReturn = investorAverageExpectedAnnualReturn;
    }

    /**
     * 获取 ratesOfReturn
     *
     * @return ratesOfReturn.
     */
    public CommonNodeEntity getRatesOfReturn() {
        return ratesOfReturn;
    }

    /**
     * 设置 ratesOfReturn
     *
     * @param ratesOfReturn ratesOfReturn
     */
    public void setRatesOfReturn(CommonNodeEntity ratesOfReturn) {
        this.ratesOfReturn = ratesOfReturn;
    }

    /**
     * 获取 subscriptionStartDate
     *
     * @return subscriptionStartDate.
     */
    public CommonNodeEntity getSubscriptionStartDate() {
        return subscriptionStartDate;
    }

    /**
     * 设置 subscriptionStartDate
     *
     * @param subscriptionStartDate subscriptionStartDate
     */
    public void setSubscriptionStartDate(CommonNodeEntity subscriptionStartDate) {
        this.subscriptionStartDate = subscriptionStartDate;
    }

    /**
     * 获取 subscriptionEndDate
     *
     * @return subscriptionEndDate.
     */
    public CommonNodeEntity getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    /**
     * 设置 subscriptionEndDate
     *
     * @param subscriptionEndDate subscriptionEndDate
     */
    public void setSubscriptionEndDate(CommonNodeEntity subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    /**
     * 获取 productValueDate
     *
     * @return productValueDate.
     */
    public CommonNodeEntity getProductValueDate() {
        return productValueDate;
    }

    /**
     * 设置 productValueDate
     *
     * @param productValueDate productValueDate
     */
    public void setProductValueDate(CommonNodeEntity productValueDate) {
        this.productValueDate = productValueDate;
    }

    /**
     * 获取 productMaturityDate
     *
     * @return productMaturityDate.
     */
    public CommonNodeEntity getProductMaturityDate() {
        return productMaturityDate;
    }

    /**
     * 设置 productMaturityDate
     *
     * @param productMaturityDate productMaturityDate
     */
    public void setProductMaturityDate(CommonNodeEntity productMaturityDate) {
        this.productMaturityDate = productMaturityDate;
    }

    /**
     * 获取 structuredProductPleaseSelect
     *
     * @return structuredProductPleaseSelect.
     */
    public CommonNodeEntity getStructuredProductPleaseSelect() {
        return structuredProductPleaseSelect;
    }

    /**
     * 设置 structuredProductPleaseSelect
     *
     * @param structuredProductPleaseSelect structuredProductPleaseSelect
     */
    public void setStructuredProductPleaseSelect(CommonNodeEntity structuredProductPleaseSelect) {
        this.structuredProductPleaseSelect = structuredProductPleaseSelect;
    }

    /**
     * 获取 gradeType
     *
     * @return gradeType.
     */
    public CommonNodeEntity getGradeType() {
        return gradeType;
    }

    /**
     * 设置 gradeType
     *
     * @param gradeType gradeType
     */
    public void setGradeType(CommonNodeEntity gradeType) {
        this.gradeType = gradeType;
    }

    /**
     * 获取 openingMode
     *
     * @return openingMode.
     */
    public CommonNodeEntity getOpeningMode() {
        return openingMode;
    }

    /**
     * 设置 openingMode
     *
     * @param openingMode openingMode
     */
    public void setOpeningMode(CommonNodeEntity openingMode) {
        this.openingMode = openingMode;
    }

    /**
     * 获取 regularOpeningPeriod
     *
     * @return regularOpeningPeriod.
     */
    public CommonNodeEntity getRegularOpeningPeriod() {
        return regularOpeningPeriod;
    }

    /**
     * 设置 regularOpeningPeriod
     *
     * @param regularOpeningPeriod regularOpeningPeriod
     */
    public void setRegularOpeningPeriod(CommonNodeEntity regularOpeningPeriod) {
        this.regularOpeningPeriod = regularOpeningPeriod;
    }

    /**
     * 获取 otherRegularOpeningPeriodDays
     *
     * @return otherRegularOpeningPeriodDays.
     */
    public CommonNodeEntity getOtherRegularOpeningPeriodDays() {
        return otherRegularOpeningPeriodDays;
    }

    /**
     * 设置 otherRegularOpeningPeriodDays
     *
     * @param otherRegularOpeningPeriodDays otherRegularOpeningPeriodDays
     */
    public void setOtherRegularOpeningPeriodDays(CommonNodeEntity otherRegularOpeningPeriodDays) {
        this.otherRegularOpeningPeriodDays = otherRegularOpeningPeriodDays;
    }

    /**
     * 获取 detailsOnIrregularOpeningPeriod
     *
     * @return detailsOnIrregularOpeningPeriod.
     */
    public CommonNodeEntity getDetailsOnIrregularOpeningPeriod() {
        return detailsOnIrregularOpeningPeriod;
    }

    /**
     * 设置 detailsOnIrregularOpeningPeriod
     *
     * @param detailsOnIrregularOpeningPeriod detailsOnIrregularOpeningPeriod
     */
    public void setDetailsOnIrregularOpeningPeriod(CommonNodeEntity detailsOnIrregularOpeningPeriod) {
        this.detailsOnIrregularOpeningPeriod = detailsOnIrregularOpeningPeriod;
    }

    /**
     * 获取 startDateOfFirstOpeningPeriod
     *
     * @return startDateOfFirstOpeningPeriod.
     */
    public CommonNodeEntity getStartDateOfFirstOpeningPeriod() {
        return startDateOfFirstOpeningPeriod;
    }

    /**
     * 设置 startDateOfFirstOpeningPeriod
     *
     * @param startDateOfFirstOpeningPeriod startDateOfFirstOpeningPeriod
     */
    public void setStartDateOfFirstOpeningPeriod(CommonNodeEntity startDateOfFirstOpeningPeriod) {
        this.startDateOfFirstOpeningPeriod = startDateOfFirstOpeningPeriod;
    }

    /**
     * 获取 holidayOpeningPleaseSelect
     *
     * @return holidayOpeningPleaseSelect.
     */
    public CommonNodeEntity getHolidayOpeningPleaseSelect() {
        return holidayOpeningPleaseSelect;
    }

    /**
     * 设置 holidayOpeningPleaseSelect
     *
     * @param holidayOpeningPleaseSelect holidayOpeningPleaseSelect
     */
    public void setHolidayOpeningPleaseSelect(CommonNodeEntity holidayOpeningPleaseSelect) {
        this.holidayOpeningPleaseSelect = holidayOpeningPleaseSelect;
    }

    /**
     * 获取 averageOpeningFrequencyAnnualized
     *
     * @return averageOpeningFrequencyAnnualized.
     */
    public CommonNodeEntity getAverageOpeningFrequencyAnnualized() {
        return averageOpeningFrequencyAnnualized;
    }

    /**
     * 设置 averageOpeningFrequencyAnnualized
     *
     * @param averageOpeningFrequencyAnnualized averageOpeningFrequencyAnnualized
     */
    public void setAverageOpeningFrequencyAnnualized(CommonNodeEntity averageOpeningFrequencyAnnualized) {
        this.averageOpeningFrequencyAnnualized = averageOpeningFrequencyAnnualized;
    }

    /**
     * 获取 businessTypeDuringOpeningPeriod
     *
     * @return businessTypeDuringOpeningPeriod.
     */
    public CommonNodeEntity getBusinessTypeDuringOpeningPeriod() {
        return businessTypeDuringOpeningPeriod;
    }

    /**
     * 设置 businessTypeDuringOpeningPeriod
     *
     * @param businessTypeDuringOpeningPeriod businessTypeDuringOpeningPeriod
     */
    public void setBusinessTypeDuringOpeningPeriod(CommonNodeEntity businessTypeDuringOpeningPeriod) {
        this.businessTypeDuringOpeningPeriod = businessTypeDuringOpeningPeriod;
    }

    /**
     * 获取 detailsOnBusinessTypeDuringOpeningPeriod
     *
     * @return detailsOnBusinessTypeDuringOpeningPeriod.
     */
    public CommonNodeEntity getDetailsOnBusinessTypeDuringOpeningPeriod() {
        return detailsOnBusinessTypeDuringOpeningPeriod;
    }

    /**
     * 设置 detailsOnBusinessTypeDuringOpeningPeriod
     *
     * @param detailsOnBusinessTypeDuringOpeningPeriod detailsOnBusinessTypeDuringOpeningPeriod
     */
    public void setDetailsOnBusinessTypeDuringOpeningPeriod(CommonNodeEntity detailsOnBusinessTypeDuringOpeningPeriod) {
        this.detailsOnBusinessTypeDuringOpeningPeriod = detailsOnBusinessTypeDuringOpeningPeriod;
    }

    /**
     * 获取 issueRegistrationForAgencyAndPrivateBankDetails
     *
     * @return issueRegistrationForAgencyAndPrivateBankDetails.
     */
    public CommonNodeEntity getIssueRegistrationForAgencyAndPrivateBankDetails() {
        return issueRegistrationForAgencyAndPrivateBankDetails;
    }

    /**
     * 设置 issueRegistrationForAgencyAndPrivateBankDetails
     *
     * @param issueRegistrationForAgencyAndPrivateBankDetails
     *            issueRegistrationForAgencyAndPrivateBankDetails
     */
    public void setIssueRegistrationForAgencyAndPrivateBankDetails(CommonNodeEntity issueRegistrationForAgencyAndPrivateBankDetails) {
        this.issueRegistrationForAgencyAndPrivateBankDetails = issueRegistrationForAgencyAndPrivateBankDetails;
    }

    /**
     * 获取 productCode
     *
     * @return productCode.
     */
    public CommonNodeEntity getProductCode() {
        return productCode;
    }

    /**
     * 设置 productCode
     *
     * @param productCode productCode
     */
    public void setProductCode(CommonNodeEntity productCode) {
        this.productCode = productCode;
    }

    /**
     * 获取 actualProductTerminationDate
     *
     * @return actualProductTerminationDate.
     */
    public CommonNodeEntity getActualProductTerminationDate() {
        return actualProductTerminationDate;
    }

    /**
     * 设置 actualProductTerminationDate
     *
     * @param actualProductTerminationDate actualProductTerminationDate
     */
    public void setActualProductTerminationDate(CommonNodeEntity actualProductTerminationDate) {
        this.actualProductTerminationDate = actualProductTerminationDate;
    }

    /**
     * 获取 netIncomeOfBankCNY
     *
     * @return netIncomeOfBankCNY.
     */
    public CommonNodeEntity getNetIncomeOfBankCNY() {
        return netIncomeOfBankCNY;
    }

    /**
     * 设置 netIncomeOfBankCNY
     *
     * @param netIncomeOfBankCNY netIncomeOfBankCNY
     */
    public void setNetIncomeOfBankCNY(CommonNodeEntity netIncomeOfBankCNY) {
        this.netIncomeOfBankCNY = netIncomeOfBankCNY;
    }

    /**
     * 获取 interestPaymentCNY
     *
     * @return interestPaymentCNY.
     */
    public CommonNodeEntity getInterestPaymentCNY() {
        return interestPaymentCNY;
    }

    /**
     * 设置 interestPaymentCNY
     *
     * @param interestPaymentCNY interestPaymentCNY
     */
    public void setInterestPaymentCNY(CommonNodeEntity interestPaymentCNY) {
        this.interestPaymentCNY = interestPaymentCNY;
    }

    /**
     * 获取 principalAndInterestPaymentCNY
     *
     * @return principalAndInterestPaymentCNY.
     */
    public CommonNodeEntity getPrincipalAndInterestPaymentCNY() {
        return principalAndInterestPaymentCNY;
    }

    /**
     * 设置 principalAndInterestPaymentCNY
     *
     * @param principalAndInterestPaymentCNY principalAndInterestPaymentCNY
     */
    public void setPrincipalAndInterestPaymentCNY(CommonNodeEntity principalAndInterestPaymentCNY) {
        this.principalAndInterestPaymentCNY = principalAndInterestPaymentCNY;
    }

    /**
     * 获取 deliveredUnits
     *
     * @return deliveredUnits.
     */
    public CommonNodeEntity getDeliveredUnits() {
        return deliveredUnits;
    }

    /**
     * 设置 deliveredUnits
     *
     * @param deliveredUnits deliveredUnits
     */
    public void setDeliveredUnits(CommonNodeEntity deliveredUnits) {
        this.deliveredUnits = deliveredUnits;
    }

    /**
     * 获取 internalCustodianFeeCNY
     *
     * @return internalCustodianFeeCNY.
     */
    public CommonNodeEntity getInternalCustodianFeeCNY() {
        return internalCustodianFeeCNY;
    }

    /**
     * 设置 internalCustodianFeeCNY
     *
     * @param internalCustodianFeeCNY internalCustodianFeeCNY
     */
    public void setInternalCustodianFeeCNY(CommonNodeEntity internalCustodianFeeCNY) {
        this.internalCustodianFeeCNY = internalCustodianFeeCNY;
    }

    /**
     * 获取 internalManagementFeeCNY
     *
     * @return internalManagementFeeCNY.
     */
    public CommonNodeEntity getInternalManagementFeeCNY() {
        return internalManagementFeeCNY;
    }

    /**
     * 设置 internalManagementFeeCNY
     *
     * @param internalManagementFeeCNY internalManagementFeeCNY
     */
    public void setInternalManagementFeeCNY(CommonNodeEntity internalManagementFeeCNY) {
        this.internalManagementFeeCNY = internalManagementFeeCNY;
    }

    /**
     * 获取 internalSalesCommissionCNY
     *
     * @return internalSalesCommissionCNY.
     */
    public CommonNodeEntity getInternalSalesCommissionCNY() {
        return internalSalesCommissionCNY;
    }

    /**
     * 设置 internalSalesCommissionCNY
     *
     * @param internalSalesCommissionCNY internalSalesCommissionCNY
     */
    public void setInternalSalesCommissionCNY(CommonNodeEntity internalSalesCommissionCNY) {
        this.internalSalesCommissionCNY = internalSalesCommissionCNY;
    }

    /**
     * 获取 internalOtherProductFeeCNY
     *
     * @return internalOtherProductFeeCNY.
     */
    public CommonNodeEntity getInternalOtherProductFeeCNY() {
        return internalOtherProductFeeCNY;
    }

    /**
     * 设置 internalOtherProductFeeCNY
     *
     * @param internalOtherProductFeeCNY internalOtherProductFeeCNY
     */
    public void setInternalOtherProductFeeCNY(CommonNodeEntity internalOtherProductFeeCNY) {
        this.internalOtherProductFeeCNY = internalOtherProductFeeCNY;
    }

    /**
     * 获取 otherInstitutionCustodianFeeCNY
     *
     * @return otherInstitutionCustodianFeeCNY.
     */
    public CommonNodeEntity getOtherInstitutionCustodianFeeCNY() {
        return otherInstitutionCustodianFeeCNY;
    }

    /**
     * 设置 otherInstitutionCustodianFeeCNY
     *
     * @param otherInstitutionCustodianFeeCNY otherInstitutionCustodianFeeCNY
     */
    public void setOtherInstitutionCustodianFeeCNY(CommonNodeEntity otherInstitutionCustodianFeeCNY) {
        this.otherInstitutionCustodianFeeCNY = otherInstitutionCustodianFeeCNY;
    }

    /**
     * 获取 otherInstitutionManagementFeeCNY
     *
     * @return otherInstitutionManagementFeeCNY.
     */
    public CommonNodeEntity getOtherInstitutionManagementFeeCNY() {
        return otherInstitutionManagementFeeCNY;
    }

    /**
     * 设置 otherInstitutionManagementFeeCNY
     *
     * @param otherInstitutionManagementFeeCNY otherInstitutionManagementFeeCNY
     */
    public void setOtherInstitutionManagementFeeCNY(CommonNodeEntity otherInstitutionManagementFeeCNY) {
        this.otherInstitutionManagementFeeCNY = otherInstitutionManagementFeeCNY;
    }

    /**
     * 获取 otherInstitutionSalesCommissionCNY
     *
     * @return otherInstitutionSalesCommissionCNY.
     */
    public CommonNodeEntity getOtherInstitutionSalesCommissionCNY() {
        return otherInstitutionSalesCommissionCNY;
    }

    /**
     * 设置 otherInstitutionSalesCommissionCNY
     *
     * @param otherInstitutionSalesCommissionCNY otherInstitutionSalesCommissionCNY
     */
    public void setOtherInstitutionSalesCommissionCNY(CommonNodeEntity otherInstitutionSalesCommissionCNY) {
        this.otherInstitutionSalesCommissionCNY = otherInstitutionSalesCommissionCNY;
    }

    /**
     * 获取 investmentConsultingFeeCNY
     *
     * @return investmentConsultingFeeCNY.
     */
    public CommonNodeEntity getInvestmentConsultingFeeCNY() {
        return investmentConsultingFeeCNY;
    }

    /**
     * 设置 investmentConsultingFeeCNY
     *
     * @param investmentConsultingFeeCNY investmentConsultingFeeCNY
     */
    public void setInvestmentConsultingFeeCNY(CommonNodeEntity investmentConsultingFeeCNY) {
        this.investmentConsultingFeeCNY = investmentConsultingFeeCNY;
    }

    /**
     * 获取 otherInstitutionOtherProductFeeCNY
     *
     * @return otherInstitutionOtherProductFeeCNY.
     */
    public CommonNodeEntity getOtherInstitutionOtherProductFeeCNY() {
        return otherInstitutionOtherProductFeeCNY;
    }

    /**
     * 设置 otherInstitutionOtherProductFeeCNY
     *
     * @param otherInstitutionOtherProductFeeCNY otherInstitutionOtherProductFeeCNY
     */
    public void setOtherInstitutionOtherProductFeeCNY(CommonNodeEntity otherInstitutionOtherProductFeeCNY) {
        this.otherInstitutionOtherProductFeeCNY = otherInstitutionOtherProductFeeCNY;
    }

    /**
     * 获取 realizedAnnualReturnToInvestors
     *
     * @return realizedAnnualReturnToInvestors.
     */
    public CommonNodeEntity getRealizedAnnualReturnToInvestors() {
        return realizedAnnualReturnToInvestors;
    }

    /**
     * 设置 realizedAnnualReturnToInvestors
     *
     * @param realizedAnnualReturnToInvestors realizedAnnualReturnToInvestors
     */
    public void setRealizedAnnualReturnToInvestors(CommonNodeEntity realizedAnnualReturnToInvestors) {
        this.realizedAnnualReturnToInvestors = realizedAnnualReturnToInvestors;
    }

    /**
     * 获取 realizedAnnualReturnToProduct
     *
     * @return realizedAnnualReturnToProduct.
     */
    public CommonNodeEntity getRealizedAnnualReturnToProduct() {
        return realizedAnnualReturnToProduct;
    }

    /**
     * 设置 realizedAnnualReturnToProduct
     *
     * @param realizedAnnualReturnToProduct realizedAnnualReturnToProduct
     */
    public void setRealizedAnnualReturnToProduct(CommonNodeEntity realizedAnnualReturnToProduct) {
        this.realizedAnnualReturnToProduct = realizedAnnualReturnToProduct;
    }

    /**
     * 获取 numberOfIndividualInvestors
     *
     * @return numberOfIndividualInvestors.
     */
    public CommonNodeEntity getNumberOfIndividualInvestors() {
        return numberOfIndividualInvestors;
    }

    /**
     * 设置 numberOfIndividualInvestors
     *
     * @param numberOfIndividualInvestors numberOfIndividualInvestors
     */
    public void setNumberOfIndividualInvestors(CommonNodeEntity numberOfIndividualInvestors) {
        this.numberOfIndividualInvestors = numberOfIndividualInvestors;
    }

    /**
     * 获取 numberOfCorporateInvestors
     *
     * @return numberOfCorporateInvestors.
     */
    public CommonNodeEntity getNumberOfCorporateInvestors() {
        return numberOfCorporateInvestors;
    }

    /**
     * 设置 numberOfCorporateInvestors
     *
     * @param numberOfCorporateInvestors numberOfCorporateInvestors
     */
    public void setNumberOfCorporateInvestors(CommonNodeEntity numberOfCorporateInvestors) {
        this.numberOfCorporateInvestors = numberOfCorporateInvestors;
    }

    /**
     * 获取 numberOfUnincorporatedInvestors
     *
     * @return numberOfUnincorporatedInvestors.
     */
    public CommonNodeEntity getNumberOfUnincorporatedInvestors() {
        return numberOfUnincorporatedInvestors;
    }

    /**
     * 设置 numberOfUnincorporatedInvestors
     *
     * @param numberOfUnincorporatedInvestors numberOfUnincorporatedInvestors
     */
    public void setNumberOfUnincorporatedInvestors(CommonNodeEntity numberOfUnincorporatedInvestors) {
        this.numberOfUnincorporatedInvestors = numberOfUnincorporatedInvestors;
    }

    /**
     * 获取 actualSubscribedAmountCNY
     *
     * @return actualSubscribedAmountCNY.
     */
    public CommonNodeEntity getActualSubscribedAmountCNY() {
        return actualSubscribedAmountCNY;
    }

    /**
     * 设置 actualSubscribedAmountCNY
     *
     * @param actualSubscribedAmountCNY actualSubscribedAmountCNY
     */
    public void setActualSubscribedAmountCNY(CommonNodeEntity actualSubscribedAmountCNY) {
        this.actualSubscribedAmountCNY = actualSubscribedAmountCNY;
    }

    /**
     * 获取 subscribedUnits
     *
     * @return subscribedUnits.
     */
    public CommonNodeEntity getSubscribedUnits() {
        return subscribedUnits;
    }

    /**
     * 设置 subscribedUnits
     *
     * @param subscribedUnits subscribedUnits
     */
    public void setSubscribedUnits(CommonNodeEntity subscribedUnits) {
        this.subscribedUnits = subscribedUnits;
    }

    /**
     * 获取 otherDistributionAgentsPleaseSelect
     *
     * @return otherDistributionAgentsPleaseSelect.
     */
    public CommonNodeEntity getOtherDistributionAgentsPleaseSelect() {
        return otherDistributionAgentsPleaseSelect;
    }

    /**
     * 设置 otherDistributionAgentsPleaseSelect
     *
     * @param otherDistributionAgentsPleaseSelect otherDistributionAgentsPleaseSelect
     */
    public void setOtherDistributionAgentsPleaseSelect(CommonNodeEntity otherDistributionAgentsPleaseSelect) {
        this.otherDistributionAgentsPleaseSelect = otherDistributionAgentsPleaseSelect;
    }

    /**
     * 获取 amountByOtherDistributionAgents
     *
     * @return amountByOtherDistributionAgents.
     */
    public CommonNodeEntity getAmountByOtherDistributionAgents() {
        return amountByOtherDistributionAgents;
    }

    /**
     * 设置 amountByOtherDistributionAgents
     *
     * @param amountByOtherDistributionAgents amountByOtherDistributionAgents
     */
    public void setAmountByOtherDistributionAgents(CommonNodeEntity amountByOtherDistributionAgents) {
        this.amountByOtherDistributionAgents = amountByOtherDistributionAgents;
    }

    /**
     * 获取 productAreaAndSubscriptionAmountTuple
     *
     * @return productAreaAndSubscriptionAmountTuple.
     */
    public List<CommonNodeListEntity> getProductAreaAndSubscriptionAmountTuple() {
        return productAreaAndSubscriptionAmountTuple;
    }

    /**
     * 设置 productAreaAndSubscriptionAmountTuple
     *
     * @param productAreaAndSubscriptionAmountTuple productAreaAndSubscriptionAmountTuple
     */
    public void setProductAreaAndSubscriptionAmountTuple(List<CommonNodeListEntity> productAreaAndSubscriptionAmountTuple) {
        this.productAreaAndSubscriptionAmountTuple = productAreaAndSubscriptionAmountTuple;
    }

    /**
     * 获取 subscriptionCurrencyAndSubscriptionAmountTuple
     *
     * @return subscriptionCurrencyAndSubscriptionAmountTuple.
     */
    public List<CommonNodeListEntity> getSubscriptionCurrencyAndSubscriptionAmountTuple() {
        return subscriptionCurrencyAndSubscriptionAmountTuple;
    }

    /**
     * 设置 subscriptionCurrencyAndSubscriptionAmountTuple
     *
     * @param subscriptionCurrencyAndSubscriptionAmountTuple
     *            subscriptionCurrencyAndSubscriptionAmountTuple
     */
    public void setSubscriptionCurrencyAndSubscriptionAmountTuple(List<CommonNodeListEntity> subscriptionCurrencyAndSubscriptionAmountTuple) {
        this.subscriptionCurrencyAndSubscriptionAmountTuple = subscriptionCurrencyAndSubscriptionAmountTuple;
    }

    /**
     * 获取 initialSubscriptionRegistrationDetails
     *
     * @return initialSubscriptionRegistrationDetails.
     */
    public CommonNodeEntity getInitialSubscriptionRegistrationDetails() {
        return initialSubscriptionRegistrationDetails;
    }

    /**
     * 设置 initialSubscriptionRegistrationDetails
     *
     * @param initialSubscriptionRegistrationDetails initialSubscriptionRegistrationDetails
     */
    public void setInitialSubscriptionRegistrationDetails(CommonNodeEntity initialSubscriptionRegistrationDetails) {
        this.initialSubscriptionRegistrationDetails = initialSubscriptionRegistrationDetails;
    }

    /**
     * 获取 initialNetValue
     *
     * @return initialNetValue.
     */
    public CommonNodeEntity getInitialNetValue() {
        return initialNetValue;
    }

    /**
     * 设置 initialNetValue
     *
     * @param initialNetValue initialNetValue
     */
    public void setInitialNetValue(CommonNodeEntity initialNetValue) {
        this.initialNetValue = initialNetValue;
    }

    /**
     * 获取 netValueOfProduct
     *
     * @return netValueOfProduct.
     */
    public CommonNodeEntity getNetValueOfProduct() {
        return netValueOfProduct;
    }

    /**
     * 设置 netValueOfProduct
     *
     * @param netValueOfProduct netValueOfProduct
     */
    public void setNetValueOfProduct(CommonNodeEntity netValueOfProduct) {
        this.netValueOfProduct = netValueOfProduct;
    }

    /**
     * 获取 aggregateNetValue
     *
     * @return aggregateNetValue.
     */
    public CommonNodeEntity getAggregateNetValue() {
        return aggregateNetValue;
    }

    /**
     * 设置 aggregateNetValue
     *
     * @param aggregateNetValue aggregateNetValue
     */
    public void setAggregateNetValue(CommonNodeEntity aggregateNetValue) {
        this.aggregateNetValue = aggregateNetValue;
    }

    /**
     * 获取 initialNetValueCNY
     *
     * @return initialNetValueCNY.
     */
    public CommonNodeEntity getInitialNetValueCNY() {
        return initialNetValueCNY;
    }

    /**
     * 设置 initialNetValueCNY
     *
     * @param initialNetValueCNY initialNetValueCNY
     */
    public void setInitialNetValueCNY(CommonNodeEntity initialNetValueCNY) {
        this.initialNetValueCNY = initialNetValueCNY;
    }

    /**
     * 获取 netValueOfProductCNY
     *
     * @return netValueOfProductCNY.
     */
    public CommonNodeEntity getNetValueOfProductCNY() {
        return netValueOfProductCNY;
    }

    /**
     * 设置 netValueOfProductCNY
     *
     * @param netValueOfProductCNY netValueOfProductCNY
     */
    public void setNetValueOfProductCNY(CommonNodeEntity netValueOfProductCNY) {
        this.netValueOfProductCNY = netValueOfProductCNY;
    }

    /**
     * 获取 aggregateNetValueCNY
     *
     * @return aggregateNetValueCNY.
     */
    public CommonNodeEntity getAggregateNetValueCNY() {
        return aggregateNetValueCNY;
    }

    /**
     * 设置 aggregateNetValueCNY
     *
     * @param aggregateNetValueCNY aggregateNetValueCNY
     */
    public void setAggregateNetValueCNY(CommonNodeEntity aggregateNetValueCNY) {
        this.aggregateNetValueCNY = aggregateNetValueCNY;
    }

    /**
     * 获取 realizedAnnualReturn
     *
     * @return realizedAnnualReturn.
     */
    public CommonNodeEntity getRealizedAnnualReturn() {
        return realizedAnnualReturn;
    }

    /**
     * 设置 realizedAnnualReturn
     *
     * @param realizedAnnualReturn realizedAnnualReturn
     */
    public void setRealizedAnnualReturn(CommonNodeEntity realizedAnnualReturn) {
        this.realizedAnnualReturn = realizedAnnualReturn;
    }

    /**
     * 获取 expectedAnnualReturn
     *
     * @return expectedAnnualReturn.
     */
    public CommonNodeEntity getExpectedAnnualReturn() {
        return expectedAnnualReturn;
    }

    /**
     * 设置 expectedAnnualReturn
     *
     * @param expectedAnnualReturn expectedAnnualReturn
     */
    public void setExpectedAnnualReturn(CommonNodeEntity expectedAnnualReturn) {
        this.expectedAnnualReturn = expectedAnnualReturn;
    }

    /**
     * 获取 businessStartDate
     *
     * @return businessStartDate.
     */
    public CommonNodeEntity getBusinessStartDate() {
        return businessStartDate;
    }

    /**
     * 设置 businessStartDate
     *
     * @param businessStartDate businessStartDate
     */
    public void setBusinessStartDate(CommonNodeEntity businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    /**
     * 获取 businessEndDate
     *
     * @return businessEndDate.
     */
    public CommonNodeEntity getBusinessEndDate() {
        return businessEndDate;
    }

    /**
     * 设置 businessEndDate
     *
     * @param businessEndDate businessEndDate
     */
    public void setBusinessEndDate(CommonNodeEntity businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    /**
     * 获取 subscribedUnitsOverLatestSubscriptionPeriod
     *
     * @return subscribedUnitsOverLatestSubscriptionPeriod.
     */
    public CommonNodeEntity getSubscribedUnitsOverLatestSubscriptionPeriod() {
        return subscribedUnitsOverLatestSubscriptionPeriod;
    }

    /**
     * 设置 subscribedUnitsOverLatestSubscriptionPeriod
     *
     * @param subscribedUnitsOverLatestSubscriptionPeriod
     *            subscribedUnitsOverLatestSubscriptionPeriod
     */
    public void setSubscribedUnitsOverLatestSubscriptionPeriod(CommonNodeEntity subscribedUnitsOverLatestSubscriptionPeriod) {
        this.subscribedUnitsOverLatestSubscriptionPeriod = subscribedUnitsOverLatestSubscriptionPeriod;
    }

    /**
     * 获取 redeemedUnitsOverLatestSubscriptionPeriod
     *
     * @return redeemedUnitsOverLatestSubscriptionPeriod.
     */
    public CommonNodeEntity getRedeemedUnitsOverLatestSubscriptionPeriod() {
        return redeemedUnitsOverLatestSubscriptionPeriod;
    }

    /**
     * 设置 redeemedUnitsOverLatestSubscriptionPeriod
     *
     * @param redeemedUnitsOverLatestSubscriptionPeriod redeemedUnitsOverLatestSubscriptionPeriod
     */
    public void setRedeemedUnitsOverLatestSubscriptionPeriod(CommonNodeEntity redeemedUnitsOverLatestSubscriptionPeriod) {
        this.redeemedUnitsOverLatestSubscriptionPeriod = redeemedUnitsOverLatestSubscriptionPeriod;
    }

    /**
     * 获取 outstandingAmountCNY
     *
     * @return outstandingAmountCNY.
     */
    public CommonNodeEntity getOutstandingAmountCNY() {
        return outstandingAmountCNY;
    }

    /**
     * 设置 outstandingAmountCNY
     *
     * @param outstandingAmountCNY outstandingAmountCNY
     */
    public void setOutstandingAmountCNY(CommonNodeEntity outstandingAmountCNY) {
        this.outstandingAmountCNY = outstandingAmountCNY;
    }

    /**
     * 获取 outstandingUnits
     *
     * @return outstandingUnits.
     */
    public CommonNodeEntity getOutstandingUnits() {
        return outstandingUnits;
    }

    /**
     * 设置 outstandingUnits
     *
     * @param outstandingUnits outstandingUnits
     */
    public void setOutstandingUnits(CommonNodeEntity outstandingUnits) {
        this.outstandingUnits = outstandingUnits;
    }

    /**
     * 获取 currencySubscriptionAndRedemptionTuple
     *
     * @return currencySubscriptionAndRedemptionTuple.
     */
    public List<CommonNodeListEntity> getCurrencySubscriptionAndRedemptionTuple() {
        return currencySubscriptionAndRedemptionTuple;
    }

    /**
     * 设置 currencySubscriptionAndRedemptionTuple
     *
     * @param currencySubscriptionAndRedemptionTuple currencySubscriptionAndRedemptionTuple
     */
    public void setCurrencySubscriptionAndRedemptionTuple(List<CommonNodeListEntity> currencySubscriptionAndRedemptionTuple) {
        this.currencySubscriptionAndRedemptionTuple = currencySubscriptionAndRedemptionTuple;
    }

    /**
     * 获取 subsequentSubscriptionRegistrationDetails
     *
     * @return subsequentSubscriptionRegistrationDetails.
     */
    public CommonNodeEntity getSubsequentSubscriptionRegistrationDetails() {
        return subsequentSubscriptionRegistrationDetails;
    }

    /**
     * 设置 subsequentSubscriptionRegistrationDetails
     *
     * @param subsequentSubscriptionRegistrationDetails subsequentSubscriptionRegistrationDetails
     */
    public void setSubsequentSubscriptionRegistrationDetails(CommonNodeEntity subsequentSubscriptionRegistrationDetails) {
        this.subsequentSubscriptionRegistrationDetails = subsequentSubscriptionRegistrationDetails;
    }

    /**
     * 获取 assetsAndLiabilitiesIdentificationCode
     *
     * @return assetsAndLiabilitiesIdentificationCode.
     */
    public CommonNodeEntity getAssetsAndLiabilitiesIdentificationCode() {
        return assetsAndLiabilitiesIdentificationCode;
    }

    /**
     * 设置 assetsAndLiabilitiesIdentificationCode
     *
     * @param assetsAndLiabilitiesIdentificationCode assetsAndLiabilitiesIdentificationCode
     */
    public void setAssetsAndLiabilitiesIdentificationCode(CommonNodeEntity assetsAndLiabilitiesIdentificationCode) {
        this.assetsAndLiabilitiesIdentificationCode = assetsAndLiabilitiesIdentificationCode;
    }

    /**
     * 获取 typeOfAssetsAndLiabilities
     *
     * @return typeOfAssetsAndLiabilities.
     */
    public CommonNodeEntity getTypeOfAssetsAndLiabilities() {
        return typeOfAssetsAndLiabilities;
    }

    /**
     * 设置 typeOfAssetsAndLiabilities
     *
     * @param typeOfAssetsAndLiabilities typeOfAssetsAndLiabilities
     */
    public void setTypeOfAssetsAndLiabilities(CommonNodeEntity typeOfAssetsAndLiabilities) {
        this.typeOfAssetsAndLiabilities = typeOfAssetsAndLiabilities;
    }

    /**
     * 获取 tradingVenue
     *
     * @return tradingVenue.
     */
    public CommonNodeEntity getTradingVenue() {
        return tradingVenue;
    }

    /**
     * 设置 tradingVenue
     *
     * @param tradingVenue tradingVenue
     */
    public void setTradingVenue(CommonNodeEntity tradingVenue) {
        this.tradingVenue = tradingVenue;
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
     * 获取 assetsAndLiabilitiesRegistrationDetails
     *
     * @return assetsAndLiabilitiesRegistrationDetails.
     */
    public CommonNodeEntity getAssetsAndLiabilitiesRegistrationDetails() {
        return assetsAndLiabilitiesRegistrationDetails;
    }

    /**
     * 设置 assetsAndLiabilitiesRegistrationDetails
     *
     * @param assetsAndLiabilitiesRegistrationDetails assetsAndLiabilitiesRegistrationDetails
     */
    public void setAssetsAndLiabilitiesRegistrationDetails(CommonNodeEntity assetsAndLiabilitiesRegistrationDetails) {
        this.assetsAndLiabilitiesRegistrationDetails = assetsAndLiabilitiesRegistrationDetails;
    }

    /**
     * 获取 depositBank
     *
     * @return depositBank.
     */
    public CommonNodeEntity getDepositBank() {
        return depositBank;
    }

    /**
     * 设置 depositBank
     *
     * @param depositBank depositBank
     */
    public void setDepositBank(CommonNodeEntity depositBank) {
        this.depositBank = depositBank;
    }

    /**
     * 获取 depositAmount
     *
     * @return depositAmount.
     */
    public CommonNodeEntity getDepositAmount() {
        return depositAmount;
    }

    /**
     * 设置 depositAmount
     *
     * @param depositAmount depositAmount
     */
    public void setDepositAmount(CommonNodeEntity depositAmount) {
        this.depositAmount = depositAmount;
    }

    /**
     * 获取 valueDateForCashAndBankDepositsIncludingCds
     *
     * @return valueDateForCashAndBankDepositsIncludingCds.
     */
    public CommonNodeEntity getValueDateForCashAndBankDepositsIncludingCds() {
        return valueDateForCashAndBankDepositsIncludingCds;
    }

    /**
     * 设置 valueDateForCashAndBankDepositsIncludingCds
     *
     * @param valueDateForCashAndBankDepositsIncludingCds
     *            valueDateForCashAndBankDepositsIncludingCds
     */
    public void setValueDateForCashAndBankDepositsIncludingCds(CommonNodeEntity valueDateForCashAndBankDepositsIncludingCds) {
        this.valueDateForCashAndBankDepositsIncludingCds = valueDateForCashAndBankDepositsIncludingCds;
    }

    /**
     * 获取 maturityDateForCashAndBankDepositsIncludingCds
     *
     * @return maturityDateForCashAndBankDepositsIncludingCds.
     */
    public CommonNodeEntity getMaturityDateForCashAndBankDepositsIncludingCds() {
        return maturityDateForCashAndBankDepositsIncludingCds;
    }

    /**
     * 设置 maturityDateForCashAndBankDepositsIncludingCds
     *
     * @param maturityDateForCashAndBankDepositsIncludingCds
     *            maturityDateForCashAndBankDepositsIncludingCds
     */
    public void setMaturityDateForCashAndBankDepositsIncludingCds(CommonNodeEntity maturityDateForCashAndBankDepositsIncludingCds) {
        this.maturityDateForCashAndBankDepositsIncludingCds = maturityDateForCashAndBankDepositsIncludingCds;
    }

    /**
     * 获取 annualInterestRateForCashAndBankDepositsIncludingCds
     *
     * @return annualInterestRateForCashAndBankDepositsIncludingCds.
     */
    public CommonNodeEntity getAnnualInterestRateForCashAndBankDepositsIncludingCds() {
        return annualInterestRateForCashAndBankDepositsIncludingCds;
    }

    /**
     * 设置 annualInterestRateForCashAndBankDepositsIncludingCds
     *
     * @param annualInterestRateForCashAndBankDepositsIncludingCds
     *            annualInterestRateForCashAndBankDepositsIncludingCds
     */
    public void setAnnualInterestRateForCashAndBankDepositsIncludingCds(CommonNodeEntity annualInterestRateForCashAndBankDepositsIncludingCds) {
        this.annualInterestRateForCashAndBankDepositsIncludingCds = annualInterestRateForCashAndBankDepositsIncludingCds;
    }

    /**
     * 获取 interestAccrualBasisForCashAndBankDepositsIncludingCds
     *
     * @return interestAccrualBasisForCashAndBankDepositsIncludingCds.
     */
    public CommonNodeEntity getInterestAccrualBasisForCashAndBankDepositsIncludingCds() {
        return interestAccrualBasisForCashAndBankDepositsIncludingCds;
    }

    /**
     * 设置 interestAccrualBasisForCashAndBankDepositsIncludingCds
     *
     * @param interestAccrualBasisForCashAndBankDepositsIncludingCds
     *            interestAccrualBasisForCashAndBankDepositsIncludingCds
     */
    public void setInterestAccrualBasisForCashAndBankDepositsIncludingCds(CommonNodeEntity interestAccrualBasisForCashAndBankDepositsIncludingCds) {
        this.interestAccrualBasisForCashAndBankDepositsIncludingCds = interestAccrualBasisForCashAndBankDepositsIncludingCds;
    }

    /**
     * 获取 structuredDepositsPleaseSelect
     *
     * @return structuredDepositsPleaseSelect.
     */
    public CommonNodeEntity getStructuredDepositsPleaseSelect() {
        return structuredDepositsPleaseSelect;
    }

    /**
     * 设置 structuredDepositsPleaseSelect
     *
     * @param structuredDepositsPleaseSelect structuredDepositsPleaseSelect
     */
    public void setStructuredDepositsPleaseSelect(CommonNodeEntity structuredDepositsPleaseSelect) {
        this.structuredDepositsPleaseSelect = structuredDepositsPleaseSelect;
    }

    /**
     * 获取 underlyingAssetTypeOfStructuredDepositsTuple
     *
     * @return underlyingAssetTypeOfStructuredDepositsTuple.
     */
    public List<CommonNodeListEntity> getUnderlyingAssetTypeOfStructuredDepositsTuple() {
        return underlyingAssetTypeOfStructuredDepositsTuple;
    }

    /**
     * 设置 underlyingAssetTypeOfStructuredDepositsTuple
     *
     * @param underlyingAssetTypeOfStructuredDepositsTuple
     *            underlyingAssetTypeOfStructuredDepositsTuple
     */
    public void setUnderlyingAssetTypeOfStructuredDepositsTuple(List<CommonNodeListEntity> underlyingAssetTypeOfStructuredDepositsTuple) {
        this.underlyingAssetTypeOfStructuredDepositsTuple = underlyingAssetTypeOfStructuredDepositsTuple;
    }

    /**
     * 获取 underlyingAssetOfStructuredDeposits
     *
     * @return underlyingAssetOfStructuredDeposits.
     */
    public CommonNodeEntity getUnderlyingAssetOfStructuredDeposits() {
        return underlyingAssetOfStructuredDeposits;
    }

    /**
     * 设置 underlyingAssetOfStructuredDeposits
     *
     * @param underlyingAssetOfStructuredDeposits underlyingAssetOfStructuredDeposits
     */
    public void setUnderlyingAssetOfStructuredDeposits(CommonNodeEntity underlyingAssetOfStructuredDeposits) {
        this.underlyingAssetOfStructuredDeposits = underlyingAssetOfStructuredDeposits;
    }

    /**
     * 获取 identificationCode
     *
     * @return identificationCode.
     */
    public CommonNodeEntity getIdentificationCode() {
        return identificationCode;
    }

    /**
     * 设置 identificationCode
     *
     * @param identificationCode identificationCode
     */
    public void setIdentificationCode(CommonNodeEntity identificationCode) {
        this.identificationCode = identificationCode;
    }

    /**
     * 获取 nameForBondsDirectFinancingVehiclesAndInterbankCds
     *
     * @return nameForBondsDirectFinancingVehiclesAndInterbankCds.
     */
    public CommonNodeEntity getNameForBondsDirectFinancingVehiclesAndInterbankCds() {
        return nameForBondsDirectFinancingVehiclesAndInterbankCds;
    }

    /**
     * 设置 nameForBondsDirectFinancingVehiclesAndInterbankCds
     *
     * @param nameForBondsDirectFinancingVehiclesAndInterbankCds
     *            nameForBondsDirectFinancingVehiclesAndInterbankCds
     */
    public void setNameForBondsDirectFinancingVehiclesAndInterbankCds(CommonNodeEntity nameForBondsDirectFinancingVehiclesAndInterbankCds) {
        this.nameForBondsDirectFinancingVehiclesAndInterbankCds = nameForBondsDirectFinancingVehiclesAndInterbankCds;
    }

    /**
     * 获取 specificBondType
     *
     * @return specificBondType.
     */
    public CommonNodeEntity getSpecificBondType() {
        return specificBondType;
    }

    /**
     * 设置 specificBondType
     *
     * @param specificBondType specificBondType
     */
    public void setSpecificBondType(CommonNodeEntity specificBondType) {
        this.specificBondType = specificBondType;
    }

    /**
     * 获取 issuingModeOfBond
     *
     * @return issuingModeOfBond.
     */
    public CommonNodeEntity getIssuingModeOfBond() {
        return issuingModeOfBond;
    }

    /**
     * 设置 issuingModeOfBond
     *
     * @param issuingModeOfBond issuingModeOfBond
     */
    public void setIssuingModeOfBond(CommonNodeEntity issuingModeOfBond) {
        this.issuingModeOfBond = issuingModeOfBond;
    }

    /**
     * 获取 issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds
     *
     * @return issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds.
     */
    public CommonNodeEntity getIssuerRatingForBondsDirectFinancingVehiclesAndInterbankCds() {
        return issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds;
    }

    /**
     * 设置 issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds
     *
     * @param issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds
     *            issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds
     */
    public void setIssuerRatingForBondsDirectFinancingVehiclesAndInterbankCds(CommonNodeEntity issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds) {
        this.issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds = issuerRatingForBondsDirectFinancingVehiclesAndInterbankCds;
    }

    /**
     * 获取 issuingInstitutionTypeByScale
     *
     * @return issuingInstitutionTypeByScale.
     */
    public CommonNodeEntity getIssuingInstitutionTypeByScale() {
        return issuingInstitutionTypeByScale;
    }

    /**
     * 设置 issuingInstitutionTypeByScale
     *
     * @param issuingInstitutionTypeByScale issuingInstitutionTypeByScale
     */
    public void setIssuingInstitutionTypeByScale(CommonNodeEntity issuingInstitutionTypeByScale) {
        this.issuingInstitutionTypeByScale = issuingInstitutionTypeByScale;
    }

    /**
     * 获取 issuingInstitutionTypeByTechnicalField
     *
     * @return issuingInstitutionTypeByTechnicalField.
     */
    public CommonNodeEntity getIssuingInstitutionTypeByTechnicalField() {
        return issuingInstitutionTypeByTechnicalField;
    }

    /**
     * 设置 issuingInstitutionTypeByTechnicalField
     *
     * @param issuingInstitutionTypeByTechnicalField issuingInstitutionTypeByTechnicalField
     */
    public void setIssuingInstitutionTypeByTechnicalField(CommonNodeEntity issuingInstitutionTypeByTechnicalField) {
        this.issuingInstitutionTypeByTechnicalField = issuingInstitutionTypeByTechnicalField;
    }

    /**
     * 获取 issuingInstitutionTypeByEconomicType
     *
     * @return issuingInstitutionTypeByEconomicType.
     */
    public CommonNodeEntity getIssuingInstitutionTypeByEconomicType() {
        return issuingInstitutionTypeByEconomicType;
    }

    /**
     * 设置 issuingInstitutionTypeByEconomicType
     *
     * @param issuingInstitutionTypeByEconomicType issuingInstitutionTypeByEconomicType
     */
    public void setIssuingInstitutionTypeByEconomicType(CommonNodeEntity issuingInstitutionTypeByEconomicType) {
        this.issuingInstitutionTypeByEconomicType = issuingInstitutionTypeByEconomicType;
    }

    /**
     * 获取 industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds
     *
     * @return industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds.
     */
    public CommonNodeEntity getIndustryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds() {
        return industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds;
    }

    /**
     * 设置 industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds
     *
     * @param industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds
     *            industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds
     */
    public void setIndustryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds(CommonNodeEntity industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds) {
        this.industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds = industryOfIssuerForBondsDirectFinancingVehiclesAndInterbankCds;
    }

    /**
     * 获取 registryAndDepository
     *
     * @return registryAndDepository.
     */
    public CommonNodeEntity getRegistryAndDepository() {
        return registryAndDepository;
    }

    /**
     * 设置 registryAndDepository
     *
     * @param registryAndDepository registryAndDepository
     */
    public void setRegistryAndDepository(CommonNodeEntity registryAndDepository) {
        this.registryAndDepository = registryAndDepository;
    }

    /**
     * 获取 detailsOnRegistryAndDepository
     *
     * @return detailsOnRegistryAndDepository.
     */
    public CommonNodeEntity getDetailsOnRegistryAndDepository() {
        return detailsOnRegistryAndDepository;
    }

    /**
     * 获取 collateralType
     *
     * @return collateralType.
     */
    public CommonNodeEntity getCollateralType() {
        return collateralType;
    }

    /**
     * 设置 collateralType
     *
     * @param collateralType collateralType
     */
    public void setCollateralType(CommonNodeEntity collateralType) {
        this.collateralType = collateralType;
    }

    /**
     * 获取 collateralValue
     *
     * @return collateralValue.
     */
    public CommonNodeEntity getCollateralValue() {
        return collateralValue;
    }

    /**
     * 设置 collateralValue
     *
     * @param collateralValue collateralValue
     */
    public void setCollateralValue(CommonNodeEntity collateralValue) {
        this.collateralValue = collateralValue;
    }

    /**
     * 获取 incomeBeneficialOwnershipType
     *
     * @return incomeBeneficialOwnershipType.
     */
    public CommonNodeEntity getIncomeBeneficialOwnershipType() {
        return incomeBeneficialOwnershipType;
    }

    /**
     * 设置 incomeBeneficialOwnershipType
     *
     * @param incomeBeneficialOwnershipType incomeBeneficialOwnershipType
     */
    public void setIncomeBeneficialOwnershipType(CommonNodeEntity incomeBeneficialOwnershipType) {
        this.incomeBeneficialOwnershipType = incomeBeneficialOwnershipType;
    }

    /**
     * 获取 buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getBuybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations() {
        return buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     *            buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setBuybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations = buybackPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 nameForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return nameForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getNameForCreditAssetTradingsAndNonStandardDebtObligations() {
        return nameForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 nameForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param nameForCreditAssetTradingsAndNonStandardDebtObligations
     *            nameForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setNameForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity nameForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.nameForCreditAssetTradingsAndNonStandardDebtObligations = nameForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 amountForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return amountForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getAmountForCreditAssetTradingsAndNonStandardDebtObligations() {
        return amountForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 amountForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param amountForCreditAssetTradingsAndNonStandardDebtObligations
     *            amountForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setAmountForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity amountForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.amountForCreditAssetTradingsAndNonStandardDebtObligations = amountForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 valueDateForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return valueDateForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getValueDateForCreditAssetTradingsAndNonStandardDebtObligations() {
        return valueDateForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 valueDateForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param valueDateForCreditAssetTradingsAndNonStandardDebtObligations
     *            valueDateForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setValueDateForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity valueDateForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.valueDateForCreditAssetTradingsAndNonStandardDebtObligations = valueDateForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 maturityDateForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return maturityDateForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getMaturityDateForCreditAssetTradingsAndNonStandardDebtObligations() {
        return maturityDateForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 maturityDateForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param maturityDateForCreditAssetTradingsAndNonStandardDebtObligations
     *            maturityDateForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setMaturityDateForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity maturityDateForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.maturityDateForCreditAssetTradingsAndNonStandardDebtObligations = maturityDateForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getExpectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations() {
        return expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     *            expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setExpectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations = expectedReturnPleaseSelectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getProjectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations() {
        return projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations
     *            projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setProjectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations = projectAnnualReturnForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取
     * interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getInterestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations() {
        return interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置
     * interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations
     *            interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setInterestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations = interestPaymentFrequencyMeasuredByMonthForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 detailsOnPrincipalAndInterestPayment
     *
     * @return detailsOnPrincipalAndInterestPayment.
     */
    public CommonNodeEntity getDetailsOnPrincipalAndInterestPayment() {
        return detailsOnPrincipalAndInterestPayment;
    }

    /**
     * 设置 detailsOnPrincipalAndInterestPayment
     *
     * @param detailsOnPrincipalAndInterestPayment detailsOnPrincipalAndInterestPayment
     */
    public void setDetailsOnPrincipalAndInterestPayment(CommonNodeEntity detailsOnPrincipalAndInterestPayment) {
        this.detailsOnPrincipalAndInterestPayment = detailsOnPrincipalAndInterestPayment;
    }

    /**
     * 获取 interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getInterestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations() {
        return interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations
     *            interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setInterestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations = interestAccrualBasisForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 totalFeeRate
     *
     * @return totalFeeRate.
     */
    public CommonNodeEntity getTotalFeeRate() {
        return totalFeeRate;
    }

    /**
     * 设置 totalFeeRate
     *
     * @param totalFeeRate totalFeeRate
     */
    public void setTotalFeeRate(CommonNodeEntity totalFeeRate) {
        this.totalFeeRate = totalFeeRate;
    }

    /**
     * 获取 benchmarkInterestRateType
     *
     * @return benchmarkInterestRateType.
     */
    public CommonNodeEntity getBenchmarkInterestRateType() {
        return benchmarkInterestRateType;
    }

    /**
     * 设置 benchmarkInterestRateType
     *
     * @param benchmarkInterestRateType benchmarkInterestRateType
     */
    public void setBenchmarkInterestRateType(CommonNodeEntity benchmarkInterestRateType) {
        this.benchmarkInterestRateType = benchmarkInterestRateType;
    }

    /**
     * 获取 floatingRate
     *
     * @return floatingRate.
     */
    public CommonNodeEntity getFloatingRate() {
        return floatingRate;
    }

    /**
     * 设置 floatingRate
     *
     * @param floatingRate floatingRate
     */
    public void setFloatingRate(CommonNodeEntity floatingRate) {
        this.floatingRate = floatingRate;
    }

    /**
     * 获取 yieldSpreadBasisPoints
     *
     * @return yieldSpreadBasisPoints.
     */
    public CommonNodeEntity getYieldSpreadBasisPoints() {
        return yieldSpreadBasisPoints;
    }

    /**
     * 设置 yieldSpreadBasisPoints
     *
     * @param yieldSpreadBasisPoints yieldSpreadBasisPoints
     */
    public void setYieldSpreadBasisPoints(CommonNodeEntity yieldSpreadBasisPoints) {
        this.yieldSpreadBasisPoints = yieldSpreadBasisPoints;
    }

    /**
     * 获取 debtorForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return debtorForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getDebtorForCreditAssetTradingsAndNonStandardDebtObligations() {
        return debtorForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 debtorForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param debtorForCreditAssetTradingsAndNonStandardDebtObligations
     *            debtorForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setDebtorForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity debtorForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.debtorForCreditAssetTradingsAndNonStandardDebtObligations = debtorForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 internalDebtorRating
     *
     * @return internalDebtorRating.
     */
    public CommonNodeEntity getInternalDebtorRating() {
        return internalDebtorRating;
    }

    /**
     * 设置 internalDebtorRating
     *
     * @param internalDebtorRating internalDebtorRating
     */
    public void setInternalDebtorRating(CommonNodeEntity internalDebtorRating) {
        this.internalDebtorRating = internalDebtorRating;
    }

    /**
     * 获取 ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getRatingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations() {
        return ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations
     *            ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setRatingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations = ratingAgencyAndIssuerRatingForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getDebtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations() {
        return debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations
     *            debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setDebtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations = debtorTypeByScaleForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getDebtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations() {
        return debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations
     *            debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setDebtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations = debtorTypeByTechnicalFieldForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getDebtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations() {
        return debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations
     *            debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setDebtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations = debtorTypeByEconomicTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getIndustryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations() {
        return industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations
     *            industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setIndustryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations = industryOfDebtorForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 regionOfDebtor
     *
     * @return regionOfDebtor.
     */
    public CommonNodeEntity getRegionOfDebtor() {
        return regionOfDebtor;
    }

    /**
     * 设置 regionOfDebtor
     *
     * @param regionOfDebtor regionOfDebtor
     */
    public void setRegionOfDebtor(CommonNodeEntity regionOfDebtor) {
        this.regionOfDebtor = regionOfDebtor;
    }

    /**
     * 获取 organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getOrganizationCodeForCreditAssetTradingsAndNonStandardDebtObligations() {
        return organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations
     *            organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setOrganizationCodeForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations = organizationCodeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 keyMonitoringIndustryPleaseSelect
     *
     * @return keyMonitoringIndustryPleaseSelect.
     */
    public CommonNodeEntity getKeyMonitoringIndustryPleaseSelect() {
        return keyMonitoringIndustryPleaseSelect;
    }

    /**
     * 设置 keyMonitoringIndustryPleaseSelect
     *
     * @param keyMonitoringIndustryPleaseSelect keyMonitoringIndustryPleaseSelect
     */
    public void setKeyMonitoringIndustryPleaseSelect(CommonNodeEntity keyMonitoringIndustryPleaseSelect) {
        this.keyMonitoringIndustryPleaseSelect = keyMonitoringIndustryPleaseSelect;
    }

    /**
     * 获取 keyMonitoringIndustryType
     *
     * @return keyMonitoringIndustryType.
     */
    public CommonNodeEntity getKeyMonitoringIndustryType() {
        return keyMonitoringIndustryType;
    }

    /**
     * 设置 keyMonitoringIndustryType
     *
     * @param keyMonitoringIndustryType keyMonitoringIndustryType
     */
    public void setKeyMonitoringIndustryType(CommonNodeEntity keyMonitoringIndustryType) {
        this.keyMonitoringIndustryType = keyMonitoringIndustryType;
    }

    /**
     * 获取 detailsOnKeyMonitoringIndustryType
     *
     * @return detailsOnKeyMonitoringIndustryType.
     */
    public CommonNodeEntity getDetailsOnKeyMonitoringIndustryType() {
        return detailsOnKeyMonitoringIndustryType;
    }

    /**
     * 设置 detailsOnKeyMonitoringIndustryType
     *
     * @param detailsOnKeyMonitoringIndustryType detailsOnKeyMonitoringIndustryType
     */
    public void setDetailsOnKeyMonitoringIndustryType(CommonNodeEntity detailsOnKeyMonitoringIndustryType) {
        this.detailsOnKeyMonitoringIndustryType = detailsOnKeyMonitoringIndustryType;
    }

    /**
     * 获取 industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getIndustryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations() {
        return industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations
     *            industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setIndustryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations = industryOfProjectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 projectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return projectForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getProjectForCreditAssetTradingsAndNonStandardDebtObligations() {
        return projectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 projectForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param projectForCreditAssetTradingsAndNonStandardDebtObligations
     *            projectForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setProjectForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity projectForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.projectForCreditAssetTradingsAndNonStandardDebtObligations = projectForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 guaranteeMethod
     *
     * @return guaranteeMethod.
     */
    public CommonNodeEntity getGuaranteeMethod() {
        return guaranteeMethod;
    }

    /**
     * 设置 guaranteeMethod
     *
     * @param guaranteeMethod guaranteeMethod
     */
    public void setGuaranteeMethod(CommonNodeEntity guaranteeMethod) {
        this.guaranteeMethod = guaranteeMethod;
    }

    /**
     * 获取 detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getDetailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations() {
        return detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations
     *            detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setDetailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations = detailsOnGuaranteeStatusForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 debtorRating
     *
     * @return debtorRating.
     */
    public CommonNodeEntity getDebtorRating() {
        return debtorRating;
    }

    /**
     * 设置 debtorRating
     *
     * @param debtorRating debtorRating
     */
    public void setDebtorRating(CommonNodeEntity debtorRating) {
        this.debtorRating = debtorRating;
    }

    /**
     * 获取 internalAssetRating
     *
     * @return internalAssetRating.
     */
    public CommonNodeEntity getInternalAssetRating() {
        return internalAssetRating;
    }

    /**
     * 设置 internalAssetRating
     *
     * @param internalAssetRating internalAssetRating
     */
    public void setInternalAssetRating(CommonNodeEntity internalAssetRating) {
        this.internalAssetRating = internalAssetRating;
    }

    /**
     * 获取 standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @return standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations.
     */
    public CommonNodeEntity getStandardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations() {
        return standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 设置 standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations
     *
     * @param standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations
     *            standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations
     */
    public void setStandardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations(CommonNodeEntity standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations = standardizedInternalAssetRatingForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    /**
     * 获取 optionRight
     *
     * @return optionRight.
     */
    public CommonNodeEntity getOptionRight() {
        return optionRight;
    }

    /**
     * 设置 optionRight
     *
     * @param optionRight optionRight
     */
    public void setOptionRight(CommonNodeEntity optionRight) {
        this.optionRight = optionRight;
    }

    /**
     * 获取 detailsOnOptionExerciseCondition
     *
     * @return detailsOnOptionExerciseCondition.
     */
    public CommonNodeEntity getDetailsOnOptionExerciseCondition() {
        return detailsOnOptionExerciseCondition;
    }

    /**
     * 设置 detailsOnOptionExerciseCondition
     *
     * @param detailsOnOptionExerciseCondition detailsOnOptionExerciseCondition
     */
    public void setDetailsOnOptionExerciseCondition(CommonNodeEntity detailsOnOptionExerciseCondition) {
        this.detailsOnOptionExerciseCondition = detailsOnOptionExerciseCondition;
    }

    /**
     * 获取 incomeBeneficialOwnershipPleaseSelect
     *
     * @return incomeBeneficialOwnershipPleaseSelect.
     */
    public CommonNodeEntity getIncomeBeneficialOwnershipPleaseSelect() {
        return incomeBeneficialOwnershipPleaseSelect;
    }

    /**
     * 设置 incomeBeneficialOwnershipPleaseSelect
     *
     * @param incomeBeneficialOwnershipPleaseSelect incomeBeneficialOwnershipPleaseSelect
     */
    public void setIncomeBeneficialOwnershipPleaseSelect(CommonNodeEntity incomeBeneficialOwnershipPleaseSelect) {
        this.incomeBeneficialOwnershipPleaseSelect = incomeBeneficialOwnershipPleaseSelect;
    }

    /**
     * 获取 buybackPleaseSelectForNonStandardDebtObligations
     *
     * @return buybackPleaseSelectForNonStandardDebtObligations.
     */
    public CommonNodeEntity getBuybackPleaseSelectForNonStandardDebtObligations() {
        return buybackPleaseSelectForNonStandardDebtObligations;
    }

    /**
     * 设置 buybackPleaseSelectForNonStandardDebtObligations
     *
     * @param buybackPleaseSelectForNonStandardDebtObligations
     *            buybackPleaseSelectForNonStandardDebtObligations
     */
    public void setBuybackPleaseSelectForNonStandardDebtObligations(CommonNodeEntity buybackPleaseSelectForNonStandardDebtObligations) {
        this.buybackPleaseSelectForNonStandardDebtObligations = buybackPleaseSelectForNonStandardDebtObligations;
    }

    /**
     * 获取 typeForNonStandardDebtObligations
     *
     * @return typeForNonStandardDebtObligations.
     */
    public CommonNodeEntity getTypeForNonStandardDebtObligations() {
        return typeForNonStandardDebtObligations;
    }

    /**
     * 设置 typeForNonStandardDebtObligations
     *
     * @param typeForNonStandardDebtObligations typeForNonStandardDebtObligations
     */
    public void setTypeForNonStandardDebtObligations(CommonNodeEntity typeForNonStandardDebtObligations) {
        this.typeForNonStandardDebtObligations = typeForNonStandardDebtObligations;
    }

    /**
     * 获取 quantityForNonStandardDebtObligations
     *
     * @return quantityForNonStandardDebtObligations.
     */
    public CommonNodeEntity getQuantityForNonStandardDebtObligations() {
        return quantityForNonStandardDebtObligations;
    }

    /**
     * 设置 quantityForNonStandardDebtObligations
     *
     * @param quantityForNonStandardDebtObligations quantityForNonStandardDebtObligations
     */
    public void setQuantityForNonStandardDebtObligations(CommonNodeEntity quantityForNonStandardDebtObligations) {
        this.quantityForNonStandardDebtObligations = quantityForNonStandardDebtObligations;
    }

    /**
     * 获取 aggregateAmount
     *
     * @return aggregateAmount.
     */
    public CommonNodeEntity getAggregateAmount() {
        return aggregateAmount;
    }

    /**
     * 设置 aggregateAmount
     *
     * @param aggregateAmount aggregateAmount
     */
    public void setAggregateAmount(CommonNodeEntity aggregateAmount) {
        this.aggregateAmount = aggregateAmount;
    }

    /**
     * 获取 weightedAverageRemainingMaturityDays
     *
     * @return weightedAverageRemainingMaturityDays.
     */
    public CommonNodeEntity getWeightedAverageRemainingMaturityDays() {
        return weightedAverageRemainingMaturityDays;
    }

    /**
     * 设置 weightedAverageRemainingMaturityDays
     *
     * @param weightedAverageRemainingMaturityDays weightedAverageRemainingMaturityDays
     */
    public void setWeightedAverageRemainingMaturityDays(CommonNodeEntity weightedAverageRemainingMaturityDays) {
        this.weightedAverageRemainingMaturityDays = weightedAverageRemainingMaturityDays;
    }

    /**
     * 获取 maximumRemainingMaturityDays
     *
     * @return maximumRemainingMaturityDays.
     */
    public CommonNodeEntity getMaximumRemainingMaturityDays() {
        return maximumRemainingMaturityDays;
    }

    /**
     * 设置 maximumRemainingMaturityDays
     *
     * @param maximumRemainingMaturityDays maximumRemainingMaturityDays
     */
    public void setMaximumRemainingMaturityDays(CommonNodeEntity maximumRemainingMaturityDays) {
        this.maximumRemainingMaturityDays = maximumRemainingMaturityDays;
    }

    /**
     * 获取 minimumRemainingMaturityDays
     *
     * @return minimumRemainingMaturityDays.
     */
    public CommonNodeEntity getMinimumRemainingMaturityDays() {
        return minimumRemainingMaturityDays;
    }

    /**
     * 设置 minimumRemainingMaturityDays
     *
     * @param minimumRemainingMaturityDays minimumRemainingMaturityDays
     */
    public void setMinimumRemainingMaturityDays(CommonNodeEntity minimumRemainingMaturityDays) {
        this.minimumRemainingMaturityDays = minimumRemainingMaturityDays;
    }

    /**
     * 获取 valueDateForNonStandardDebtObligations
     *
     * @return valueDateForNonStandardDebtObligations.
     */
    public CommonNodeEntity getValueDateForNonStandardDebtObligations() {
        return valueDateForNonStandardDebtObligations;
    }

    /**
     * 设置 valueDateForNonStandardDebtObligations
     *
     * @param valueDateForNonStandardDebtObligations valueDateForNonStandardDebtObligations
     */
    public void setValueDateForNonStandardDebtObligations(CommonNodeEntity valueDateForNonStandardDebtObligations) {
        this.valueDateForNonStandardDebtObligations = valueDateForNonStandardDebtObligations;
    }

    /**
     * 获取 maturityDateForNonStandardDebtObligations
     *
     * @return maturityDateForNonStandardDebtObligations.
     */
    public CommonNodeEntity getMaturityDateForNonStandardDebtObligations() {
        return maturityDateForNonStandardDebtObligations;
    }

    /**
     * 设置 maturityDateForNonStandardDebtObligations
     *
     * @param maturityDateForNonStandardDebtObligations maturityDateForNonStandardDebtObligations
     */
    public void setMaturityDateForNonStandardDebtObligations(CommonNodeEntity maturityDateForNonStandardDebtObligations) {
        this.maturityDateForNonStandardDebtObligations = maturityDateForNonStandardDebtObligations;
    }

    /**
     * 获取 industryForNonStandardDebtObligations
     *
     * @return industryForNonStandardDebtObligations.
     */
    public CommonNodeEntity getIndustryForNonStandardDebtObligations() {
        return industryForNonStandardDebtObligations;
    }

    /**
     * 设置 industryForNonStandardDebtObligations
     *
     * @param industryForNonStandardDebtObligations industryForNonStandardDebtObligations
     */
    public void setIndustryForNonStandardDebtObligations(CommonNodeEntity industryForNonStandardDebtObligations) {
        this.industryForNonStandardDebtObligations = industryForNonStandardDebtObligations;
    }

    /**
     * 获取 discountRate
     *
     * @return discountRate.
     */
    public CommonNodeEntity getDiscountRate() {
        return discountRate;
    }

    /**
     * 设置 discountRate
     *
     * @param discountRate discountRate
     */
    public void setDiscountRate(CommonNodeEntity discountRate) {
        this.discountRate = discountRate;
    }

    /**
     * 获取 stockCode
     *
     * @return stockCode.
     */
    public CommonNodeEntity getStockCode() {
        return stockCode;
    }

    /**
     * 设置 stockCode
     *
     * @param stockCode stockCode
     */
    public void setStockCode(CommonNodeEntity stockCode) {
        this.stockCode = stockCode;
    }

    /**
     * 获取 nameOfStockEnterprise
     *
     * @return nameOfStockEnterprise.
     */
    public CommonNodeEntity getNameOfStockEnterprise() {
        return nameOfStockEnterprise;
    }

    /**
     * 设置 nameOfStockEnterprise
     *
     * @param nameOfStockEnterprise nameOfStockEnterprise
     */
    public void setNameOfStockEnterprise(CommonNodeEntity nameOfStockEnterprise) {
        this.nameOfStockEnterprise = nameOfStockEnterprise;
    }

    /**
     * 获取 stockType
     *
     * @return stockType.
     */
    public CommonNodeEntity getStockType() {
        return stockType;
    }

    /**
     * 设置 stockType
     *
     * @param stockType stockType
     */
    public void setStockType(CommonNodeEntity stockType) {
        this.stockType = stockType;
    }

    /**
     * 获取 industryForEquities
     *
     * @return industryForEquities.
     */
    public CommonNodeEntity getIndustryForEquities() {
        return industryForEquities;
    }

    /**
     * 设置 industryForEquities
     *
     * @param industryForEquities industryForEquities
     */
    public void setIndustryForEquities(CommonNodeEntity industryForEquities) {
        this.industryForEquities = industryForEquities;
    }

    /**
     * 获取 investmentStageForEquities
     *
     * @return investmentStageForEquities.
     */
    public CommonNodeEntity getInvestmentStageForEquities() {
        return investmentStageForEquities;
    }

    /**
     * 设置 investmentStageForEquities
     *
     * @param investmentStageForEquities investmentStageForEquities
     */
    public void setInvestmentStageForEquities(CommonNodeEntity investmentStageForEquities) {
        this.investmentStageForEquities = investmentStageForEquities;
    }

    /**
     * 获取 enterpriseTypeByScale
     *
     * @return enterpriseTypeByScale.
     */
    public CommonNodeEntity getEnterpriseTypeByScale() {
        return enterpriseTypeByScale;
    }

    /**
     * 设置 enterpriseTypeByScale
     *
     * @param enterpriseTypeByScale enterpriseTypeByScale
     */
    public void setEnterpriseTypeByScale(CommonNodeEntity enterpriseTypeByScale) {
        this.enterpriseTypeByScale = enterpriseTypeByScale;
    }

    /**
     * 获取 enterpriseTypeByTechnicalField
     *
     * @return enterpriseTypeByTechnicalField.
     */
    public CommonNodeEntity getEnterpriseTypeByTechnicalField() {
        return enterpriseTypeByTechnicalField;
    }

    /**
     * 设置 enterpriseTypeByTechnicalField
     *
     * @param enterpriseTypeByTechnicalField enterpriseTypeByTechnicalField
     */
    public void setEnterpriseTypeByTechnicalField(CommonNodeEntity enterpriseTypeByTechnicalField) {
        this.enterpriseTypeByTechnicalField = enterpriseTypeByTechnicalField;
    }

    /**
     * 获取 enterpriseTypeByEconomicType
     *
     * @return enterpriseTypeByEconomicType.
     */
    public CommonNodeEntity getEnterpriseTypeByEconomicType() {
        return enterpriseTypeByEconomicType;
    }

    /**
     * 设置 enterpriseTypeByEconomicType
     *
     * @param enterpriseTypeByEconomicType enterpriseTypeByEconomicType
     */
    public void setEnterpriseTypeByEconomicType(CommonNodeEntity enterpriseTypeByEconomicType) {
        this.enterpriseTypeByEconomicType = enterpriseTypeByEconomicType;
    }

    /**
     * 获取 pledgedFinancingPleaseSelect
     *
     * @return pledgedFinancingPleaseSelect.
     */
    public CommonNodeEntity getPledgedFinancingPleaseSelect() {
        return pledgedFinancingPleaseSelect;
    }

    /**
     * 设置 pledgedFinancingPleaseSelect
     *
     * @param pledgedFinancingPleaseSelect pledgedFinancingPleaseSelect
     */
    public void setPledgedFinancingPleaseSelect(CommonNodeEntity pledgedFinancingPleaseSelect) {
        this.pledgedFinancingPleaseSelect = pledgedFinancingPleaseSelect;
    }

    /**
     * 获取 nameForDerivatives
     *
     * @return nameForDerivatives.
     */
    public CommonNodeEntity getNameForDerivatives() {
        return nameForDerivatives;
    }

    /**
     * 设置 nameForDerivatives
     *
     * @param nameForDerivatives nameForDerivatives
     */
    public void setNameForDerivatives(CommonNodeEntity nameForDerivatives) {
        this.nameForDerivatives = nameForDerivatives;
    }

    /**
     * 获取 nominalPrincipal
     *
     * @return nominalPrincipal.
     */
    public CommonNodeEntity getNominalPrincipal() {
        return nominalPrincipal;
    }

    /**
     * 设置 nominalPrincipal
     *
     * @param nominalPrincipal nominalPrincipal
     */
    public void setNominalPrincipal(CommonNodeEntity nominalPrincipal) {
        this.nominalPrincipal = nominalPrincipal;
    }

    /**
     * 获取 underlyingAssetTypeTuple
     *
     * @return underlyingAssetTypeTuple.
     */
    public List<CommonNodeListEntity> getUnderlyingAssetTypeTuple() {
        return underlyingAssetTypeTuple;
    }

    /**
     * 设置 underlyingAssetTypeTuple
     *
     * @param underlyingAssetTypeTuple underlyingAssetTypeTuple
     */
    public void setUnderlyingAssetTypeTuple(List<CommonNodeListEntity> underlyingAssetTypeTuple) {
        this.underlyingAssetTypeTuple = underlyingAssetTypeTuple;
    }

    /**
     * 获取 countryOrRegionForQdiiBonds
     *
     * @return countryOrRegionForQdiiBonds.
     */
    public CommonNodeEntity getCountryOrRegionForQdiiBonds() {
        return countryOrRegionForQdiiBonds;
    }

    /**
     * 设置 countryOrRegionForQdiiBonds
     *
     * @param countryOrRegionForQdiiBonds countryOrRegionForQdiiBonds
     */
    public void setCountryOrRegionForQdiiBonds(CommonNodeEntity countryOrRegionForQdiiBonds) {
        this.countryOrRegionForQdiiBonds = countryOrRegionForQdiiBonds;
    }

    /**
     * 获取 bondName
     *
     * @return bondName.
     */
    public CommonNodeEntity getBondName() {
        return bondName;
    }

    /**
     * 设置 bondName
     *
     * @param bondName bondName
     */
    public void setBondName(CommonNodeEntity bondName) {
        this.bondName = bondName;
    }

    /**
     * 获取 bondIdentificationCode
     *
     * @return bondIdentificationCode.
     */
    public CommonNodeEntity getBondIdentificationCode() {
        return bondIdentificationCode;
    }

    /**
     * 设置 bondIdentificationCode
     *
     * @param bondIdentificationCode bondIdentificationCode
     */
    public void setBondIdentificationCode(CommonNodeEntity bondIdentificationCode) {
        this.bondIdentificationCode = bondIdentificationCode;
    }

    /**
     * 获取 issuerForQdiiBonds
     *
     * @return issuerForQdiiBonds.
     */
    public CommonNodeEntity getIssuerForQdiiBonds() {
        return issuerForQdiiBonds;
    }

    /**
     * 设置 issuerForQdiiBonds
     *
     * @param issuerForQdiiBonds issuerForQdiiBonds
     */
    public void setIssuerForQdiiBonds(CommonNodeEntity issuerForQdiiBonds) {
        this.issuerForQdiiBonds = issuerForQdiiBonds;
    }

    /**
     * 获取 industryOfIssuerForQdiiBonds
     *
     * @return industryOfIssuerForQdiiBonds.
     */
    public CommonNodeEntity getIndustryOfIssuerForQdiiBonds() {
        return industryOfIssuerForQdiiBonds;
    }

    /**
     * 设置 industryOfIssuerForQdiiBonds
     *
     * @param industryOfIssuerForQdiiBonds industryOfIssuerForQdiiBonds
     */
    public void setIndustryOfIssuerForQdiiBonds(CommonNodeEntity industryOfIssuerForQdiiBonds) {
        this.industryOfIssuerForQdiiBonds = industryOfIssuerForQdiiBonds;
    }

    /**
     * 获取 valueDateForQdiiBonds
     *
     * @return valueDateForQdiiBonds.
     */
    public CommonNodeEntity getValueDateForQdiiBonds() {
        return valueDateForQdiiBonds;
    }

    /**
     * 设置 valueDateForQdiiBonds
     *
     * @param valueDateForQdiiBonds valueDateForQdiiBonds
     */
    public void setValueDateForQdiiBonds(CommonNodeEntity valueDateForQdiiBonds) {
        this.valueDateForQdiiBonds = valueDateForQdiiBonds;
    }

    /**
     * 获取 maturityDateForQdiiBonds
     *
     * @return maturityDateForQdiiBonds.
     */
    public CommonNodeEntity getMaturityDateForQdiiBonds() {
        return maturityDateForQdiiBonds;
    }

    /**
     * 设置 maturityDateForQdiiBonds
     *
     * @param maturityDateForQdiiBonds maturityDateForQdiiBonds
     */
    public void setMaturityDateForQdiiBonds(CommonNodeEntity maturityDateForQdiiBonds) {
        this.maturityDateForQdiiBonds = maturityDateForQdiiBonds;
    }

    /**
     * 获取 termToMaturityMonths
     *
     * @return termToMaturityMonths.
     */
    public CommonNodeEntity getTermToMaturityMonths() {
        return termToMaturityMonths;
    }

    /**
     * 设置 termToMaturityMonths
     *
     * @param termToMaturityMonths termToMaturityMonths
     */
    public void setTermToMaturityMonths(CommonNodeEntity termToMaturityMonths) {
        this.termToMaturityMonths = termToMaturityMonths;
    }

    /**
     * 获取 issuerRatingForQdiiBonds
     *
     * @return issuerRatingForQdiiBonds.
     */
    public CommonNodeEntity getIssuerRatingForQdiiBonds() {
        return issuerRatingForQdiiBonds;
    }

    /**
     * 设置 issuerRatingForQdiiBonds
     *
     * @param issuerRatingForQdiiBonds issuerRatingForQdiiBonds
     */
    public void setIssuerRatingForQdiiBonds(CommonNodeEntity issuerRatingForQdiiBonds) {
        this.issuerRatingForQdiiBonds = issuerRatingForQdiiBonds;
    }

    /**
     * 获取 bondRating
     *
     * @return bondRating.
     */
    public CommonNodeEntity getBondRating() {
        return bondRating;
    }

    /**
     * 设置 bondRating
     *
     * @param bondRating bondRating
     */
    public void setBondRating(CommonNodeEntity bondRating) {
        this.bondRating = bondRating;
    }

    /**
     * 获取 couponRateForQdiiBonds
     *
     * @return couponRateForQdiiBonds.
     */
    public CommonNodeEntity getCouponRateForQdiiBonds() {
        return couponRateForQdiiBonds;
    }

    /**
     * 设置 couponRateForQdiiBonds
     *
     * @param couponRateForQdiiBonds couponRateForQdiiBonds
     */
    public void setCouponRateForQdiiBonds(CommonNodeEntity couponRateForQdiiBonds) {
        this.couponRateForQdiiBonds = couponRateForQdiiBonds;
    }

    /**
     * 获取 interestPaymentFrequencyMeasuredByMonthForQdiiBonds
     *
     * @return interestPaymentFrequencyMeasuredByMonthForQdiiBonds.
     */
    public CommonNodeEntity getInterestPaymentFrequencyMeasuredByMonthForQdiiBonds() {
        return interestPaymentFrequencyMeasuredByMonthForQdiiBonds;
    }

    /**
     * 设置 interestPaymentFrequencyMeasuredByMonthForQdiiBonds
     *
     * @param interestPaymentFrequencyMeasuredByMonthForQdiiBonds
     *            interestPaymentFrequencyMeasuredByMonthForQdiiBonds
     */
    public void setInterestPaymentFrequencyMeasuredByMonthForQdiiBonds(CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForQdiiBonds) {
        this.interestPaymentFrequencyMeasuredByMonthForQdiiBonds = interestPaymentFrequencyMeasuredByMonthForQdiiBonds;
    }

    /**
     * 获取 detailsOnGuaranteeStatusForQdiiBonds
     *
     * @return detailsOnGuaranteeStatusForQdiiBonds.
     */
    public CommonNodeEntity getDetailsOnGuaranteeStatusForQdiiBonds() {
        return detailsOnGuaranteeStatusForQdiiBonds;
    }

    /**
     * 设置 detailsOnGuaranteeStatusForQdiiBonds
     *
     * @param detailsOnGuaranteeStatusForQdiiBonds detailsOnGuaranteeStatusForQdiiBonds
     */
    public void setDetailsOnGuaranteeStatusForQdiiBonds(CommonNodeEntity detailsOnGuaranteeStatusForQdiiBonds) {
        this.detailsOnGuaranteeStatusForQdiiBonds = detailsOnGuaranteeStatusForQdiiBonds;
    }

    /**
     * 获取 detailsOnSpecialTermsIEEmbeddedOptions
     *
     * @return detailsOnSpecialTermsIEEmbeddedOptions.
     */
    public CommonNodeEntity getDetailsOnSpecialTermsIEEmbeddedOptions() {
        return detailsOnSpecialTermsIEEmbeddedOptions;
    }

    /**
     * 设置 detailsOnSpecialTermsIEEmbeddedOptions
     *
     * @param detailsOnSpecialTermsIEEmbeddedOptions detailsOnSpecialTermsIEEmbeddedOptions
     */
    public void setDetailsOnSpecialTermsIEEmbeddedOptions(CommonNodeEntity detailsOnSpecialTermsIEEmbeddedOptions) {
        this.detailsOnSpecialTermsIEEmbeddedOptions = detailsOnSpecialTermsIEEmbeddedOptions;
    }

    /**
     * 获取 countryOrRegionForQdiiInterbankLendingReverseRepo
     *
     * @return countryOrRegionForQdiiInterbankLendingReverseRepo.
     */
    public CommonNodeEntity getCountryOrRegionForQdiiInterbankLendingReverseRepo() {
        return countryOrRegionForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 设置 countryOrRegionForQdiiInterbankLendingReverseRepo
     *
     * @param countryOrRegionForQdiiInterbankLendingReverseRepo
     *            countryOrRegionForQdiiInterbankLendingReverseRepo
     */
    public void setCountryOrRegionForQdiiInterbankLendingReverseRepo(CommonNodeEntity countryOrRegionForQdiiInterbankLendingReverseRepo) {
        this.countryOrRegionForQdiiInterbankLendingReverseRepo = countryOrRegionForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 获取 valueDateForQdiiInterbankLendingReverseRepo
     *
     * @return valueDateForQdiiInterbankLendingReverseRepo.
     */
    public CommonNodeEntity getValueDateForQdiiInterbankLendingReverseRepo() {
        return valueDateForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 设置 valueDateForQdiiInterbankLendingReverseRepo
     *
     * @param valueDateForQdiiInterbankLendingReverseRepo
     *            valueDateForQdiiInterbankLendingReverseRepo
     */
    public void setValueDateForQdiiInterbankLendingReverseRepo(CommonNodeEntity valueDateForQdiiInterbankLendingReverseRepo) {
        this.valueDateForQdiiInterbankLendingReverseRepo = valueDateForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 获取 maturityDateForQdiiInterbankLendingReverseRepo
     *
     * @return maturityDateForQdiiInterbankLendingReverseRepo.
     */
    public CommonNodeEntity getMaturityDateForQdiiInterbankLendingReverseRepo() {
        return maturityDateForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 设置 maturityDateForQdiiInterbankLendingReverseRepo
     *
     * @param maturityDateForQdiiInterbankLendingReverseRepo
     *            maturityDateForQdiiInterbankLendingReverseRepo
     */
    public void setMaturityDateForQdiiInterbankLendingReverseRepo(CommonNodeEntity maturityDateForQdiiInterbankLendingReverseRepo) {
        this.maturityDateForQdiiInterbankLendingReverseRepo = maturityDateForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 获取 counterpartyForQdiiInterbankLendingReverseRepo
     *
     * @return counterpartyForQdiiInterbankLendingReverseRepo.
     */
    public CommonNodeEntity getCounterpartyForQdiiInterbankLendingReverseRepo() {
        return counterpartyForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 设置 counterpartyForQdiiInterbankLendingReverseRepo
     *
     * @param counterpartyForQdiiInterbankLendingReverseRepo
     *            counterpartyForQdiiInterbankLendingReverseRepo
     */
    public void setCounterpartyForQdiiInterbankLendingReverseRepo(CommonNodeEntity counterpartyForQdiiInterbankLendingReverseRepo) {
        this.counterpartyForQdiiInterbankLendingReverseRepo = counterpartyForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 获取 annualInterestRateForQdiiInterbankLendingReverseRepo
     *
     * @return annualInterestRateForQdiiInterbankLendingReverseRepo.
     */
    public CommonNodeEntity getAnnualInterestRateForQdiiInterbankLendingReverseRepo() {
        return annualInterestRateForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 设置 annualInterestRateForQdiiInterbankLendingReverseRepo
     *
     * @param annualInterestRateForQdiiInterbankLendingReverseRepo
     *            annualInterestRateForQdiiInterbankLendingReverseRepo
     */
    public void setAnnualInterestRateForQdiiInterbankLendingReverseRepo(CommonNodeEntity annualInterestRateForQdiiInterbankLendingReverseRepo) {
        this.annualInterestRateForQdiiInterbankLendingReverseRepo = annualInterestRateForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 获取 interestAccrualBasisForQdiiInterbankLendingReverseRepo
     *
     * @return interestAccrualBasisForQdiiInterbankLendingReverseRepo.
     */
    public CommonNodeEntity getInterestAccrualBasisForQdiiInterbankLendingReverseRepo() {
        return interestAccrualBasisForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 设置 interestAccrualBasisForQdiiInterbankLendingReverseRepo
     *
     * @param interestAccrualBasisForQdiiInterbankLendingReverseRepo
     *            interestAccrualBasisForQdiiInterbankLendingReverseRepo
     */
    public void setInterestAccrualBasisForQdiiInterbankLendingReverseRepo(CommonNodeEntity interestAccrualBasisForQdiiInterbankLendingReverseRepo) {
        this.interestAccrualBasisForQdiiInterbankLendingReverseRepo = interestAccrualBasisForQdiiInterbankLendingReverseRepo;
    }

    /**
     * 获取 countryOrRegionForQdiiFundsAndBonds
     *
     * @return countryOrRegionForQdiiFundsAndBonds.
     */
    public CommonNodeEntity getCountryOrRegionForQdiiFundsAndBonds() {
        return countryOrRegionForQdiiFundsAndBonds;
    }

    /**
     * 设置 countryOrRegionForQdiiFundsAndBonds
     *
     * @param countryOrRegionForQdiiFundsAndBonds countryOrRegionForQdiiFundsAndBonds
     */
    public void setCountryOrRegionForQdiiFundsAndBonds(CommonNodeEntity countryOrRegionForQdiiFundsAndBonds) {
        this.countryOrRegionForQdiiFundsAndBonds = countryOrRegionForQdiiFundsAndBonds;
    }

    /**
     * 获取 stockFundIdentificationCode
     *
     * @return stockFundIdentificationCode.
     */
    public CommonNodeEntity getStockFundIdentificationCode() {
        return stockFundIdentificationCode;
    }

    /**
     * 设置 stockFundIdentificationCode
     *
     * @param stockFundIdentificationCode stockFundIdentificationCode
     */
    public void setStockFundIdentificationCode(CommonNodeEntity stockFundIdentificationCode) {
        this.stockFundIdentificationCode = stockFundIdentificationCode;
    }

    /**
     * 获取 stockFundName
     *
     * @return stockFundName.
     */
    public CommonNodeEntity getStockFundName() {
        return stockFundName;
    }

    /**
     * 设置 stockFundName
     *
     * @param stockFundName stockFundName
     */
    public void setStockFundName(CommonNodeEntity stockFundName) {
        this.stockFundName = stockFundName;
    }

    /**
     * 获取 issuerForQdiiFundsAndBonds
     *
     * @return issuerForQdiiFundsAndBonds.
     */
    public CommonNodeEntity getIssuerForQdiiFundsAndBonds() {
        return issuerForQdiiFundsAndBonds;
    }

    /**
     * 设置 issuerForQdiiFundsAndBonds
     *
     * @param issuerForQdiiFundsAndBonds issuerForQdiiFundsAndBonds
     */
    public void setIssuerForQdiiFundsAndBonds(CommonNodeEntity issuerForQdiiFundsAndBonds) {
        this.issuerForQdiiFundsAndBonds = issuerForQdiiFundsAndBonds;
    }

    /**
     * 获取 industryForQdiiFundsAndBonds
     *
     * @return industryForQdiiFundsAndBonds.
     */
    public CommonNodeEntity getIndustryForQdiiFundsAndBonds() {
        return industryForQdiiFundsAndBonds;
    }

    /**
     * 设置 industryForQdiiFundsAndBonds
     *
     * @param industryForQdiiFundsAndBonds industryForQdiiFundsAndBonds
     */
    public void setIndustryForQdiiFundsAndBonds(CommonNodeEntity industryForQdiiFundsAndBonds) {
        this.industryForQdiiFundsAndBonds = industryForQdiiFundsAndBonds;
    }

    /**
     * 获取 countryOrRegionForQdiiStructuredNotes
     *
     * @return countryOrRegionForQdiiStructuredNotes.
     */
    public CommonNodeEntity getCountryOrRegionForQdiiStructuredNotes() {
        return countryOrRegionForQdiiStructuredNotes;
    }

    /**
     * 设置 countryOrRegionForQdiiStructuredNotes
     *
     * @param countryOrRegionForQdiiStructuredNotes countryOrRegionForQdiiStructuredNotes
     */
    public void setCountryOrRegionForQdiiStructuredNotes(CommonNodeEntity countryOrRegionForQdiiStructuredNotes) {
        this.countryOrRegionForQdiiStructuredNotes = countryOrRegionForQdiiStructuredNotes;
    }

    /**
     * 获取 contractName
     *
     * @return contractName.
     */
    public CommonNodeEntity getContractName() {
        return contractName;
    }

    /**
     * 设置 contractName
     *
     * @param contractName contractName
     */
    public void setContractName(CommonNodeEntity contractName) {
        this.contractName = contractName;
    }

    /**
     * 获取 valueDateForQdiiStructuredNotes
     *
     * @return valueDateForQdiiStructuredNotes.
     */
    public CommonNodeEntity getValueDateForQdiiStructuredNotes() {
        return valueDateForQdiiStructuredNotes;
    }

    /**
     * 设置 valueDateForQdiiStructuredNotes
     *
     * @param valueDateForQdiiStructuredNotes valueDateForQdiiStructuredNotes
     */
    public void setValueDateForQdiiStructuredNotes(CommonNodeEntity valueDateForQdiiStructuredNotes) {
        this.valueDateForQdiiStructuredNotes = valueDateForQdiiStructuredNotes;
    }

    /**
     * 获取 maturityDateForQdiiStructuredNotes
     *
     * @return maturityDateForQdiiStructuredNotes.
     */
    public CommonNodeEntity getMaturityDateForQdiiStructuredNotes() {
        return maturityDateForQdiiStructuredNotes;
    }

    /**
     * 设置 maturityDateForQdiiStructuredNotes
     *
     * @param maturityDateForQdiiStructuredNotes maturityDateForQdiiStructuredNotes
     */
    public void setMaturityDateForQdiiStructuredNotes(CommonNodeEntity maturityDateForQdiiStructuredNotes) {
        this.maturityDateForQdiiStructuredNotes = maturityDateForQdiiStructuredNotes;
    }

    /**
     * 获取 couponRateForQdiiStructuredNotes
     *
     * @return couponRateForQdiiStructuredNotes.
     */
    public CommonNodeEntity getCouponRateForQdiiStructuredNotes() {
        return couponRateForQdiiStructuredNotes;
    }

    /**
     * 设置 couponRateForQdiiStructuredNotes
     *
     * @param couponRateForQdiiStructuredNotes couponRateForQdiiStructuredNotes
     */
    public void setCouponRateForQdiiStructuredNotes(CommonNodeEntity couponRateForQdiiStructuredNotes) {
        this.couponRateForQdiiStructuredNotes = couponRateForQdiiStructuredNotes;
    }

    /**
     * 获取 interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes
     *
     * @return interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes.
     */
    public CommonNodeEntity getInterestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes() {
        return interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes;
    }

    /**
     * 设置 interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes
     *
     * @param interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes
     *            interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes
     */
    public void setInterestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes(CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes) {
        this.interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes = interestPaymentFrequencyMeasuredByMonthForQdiiStructuredNotes;
    }

    /**
     * 获取 percentageOfFixedIncomeInvestments
     *
     * @return percentageOfFixedIncomeInvestments.
     */
    public CommonNodeEntity getPercentageOfFixedIncomeInvestments() {
        return percentageOfFixedIncomeInvestments;
    }

    /**
     * 设置 percentageOfFixedIncomeInvestments
     *
     * @param percentageOfFixedIncomeInvestments percentageOfFixedIncomeInvestments
     */
    public void setPercentageOfFixedIncomeInvestments(CommonNodeEntity percentageOfFixedIncomeInvestments) {
        this.percentageOfFixedIncomeInvestments = percentageOfFixedIncomeInvestments;
    }

    /**
     * 获取 percentageOfDerivativeInvestments
     *
     * @return percentageOfDerivativeInvestments.
     */
    public CommonNodeEntity getPercentageOfDerivativeInvestments() {
        return percentageOfDerivativeInvestments;
    }

    /**
     * 设置 percentageOfDerivativeInvestments
     *
     * @param percentageOfDerivativeInvestments percentageOfDerivativeInvestments
     */
    public void setPercentageOfDerivativeInvestments(CommonNodeEntity percentageOfDerivativeInvestments) {
        this.percentageOfDerivativeInvestments = percentageOfDerivativeInvestments;
    }

    /**
     * 获取 typeOfDerivativeInvestments
     *
     * @return typeOfDerivativeInvestments.
     */
    public CommonNodeEntity getTypeOfDerivativeInvestments() {
        return typeOfDerivativeInvestments;
    }

    /**
     * 设置 typeOfDerivativeInvestments
     *
     * @param typeOfDerivativeInvestments typeOfDerivativeInvestments
     */
    public void setTypeOfDerivativeInvestments(CommonNodeEntity typeOfDerivativeInvestments) {
        this.typeOfDerivativeInvestments = typeOfDerivativeInvestments;
    }

    /**
     * 获取 underlyingAsset
     *
     * @return underlyingAsset.
     */
    public CommonNodeEntity getUnderlyingAsset() {
        return underlyingAsset;
    }

    /**
     * 设置 underlyingAsset
     *
     * @param underlyingAsset underlyingAsset
     */
    public void setUnderlyingAsset(CommonNodeEntity underlyingAsset) {
        this.underlyingAsset = underlyingAsset;
    }

    /**
     * 获取 detailsOnProceeds
     *
     * @return detailsOnProceeds.
     */
    public CommonNodeEntity getDetailsOnProceeds() {
        return detailsOnProceeds;
    }

    /**
     * 设置 detailsOnProceeds
     *
     * @param detailsOnProceeds detailsOnProceeds
     */
    public void setDetailsOnProceeds(CommonNodeEntity detailsOnProceeds) {
        this.detailsOnProceeds = detailsOnProceeds;
    }

    /**
     * 获取 detailsOnOption
     *
     * @return detailsOnOption.
     */
    public CommonNodeEntity getDetailsOnOption() {
        return detailsOnOption;
    }

    /**
     * 设置 detailsOnOption
     *
     * @param detailsOnOption detailsOnOption
     */
    public void setDetailsOnOption(CommonNodeEntity detailsOnOption) {
        this.detailsOnOption = detailsOnOption;
    }

    /**
     * 获取 structuredNoteMaximumAnnualReturn
     *
     * @return structuredNoteMaximumAnnualReturn.
     */
    public CommonNodeEntity getStructuredNoteMaximumAnnualReturn() {
        return structuredNoteMaximumAnnualReturn;
    }

    /**
     * 设置 structuredNoteMaximumAnnualReturn
     *
     * @param structuredNoteMaximumAnnualReturn structuredNoteMaximumAnnualReturn
     */
    public void setStructuredNoteMaximumAnnualReturn(CommonNodeEntity structuredNoteMaximumAnnualReturn) {
        this.structuredNoteMaximumAnnualReturn = structuredNoteMaximumAnnualReturn;
    }

    /**
     * 获取 structuredNoteMinimumAnnualReturn
     *
     * @return structuredNoteMinimumAnnualReturn.
     */
    public CommonNodeEntity getStructuredNoteMinimumAnnualReturn() {
        return structuredNoteMinimumAnnualReturn;
    }

    /**
     * 设置 structuredNoteMinimumAnnualReturn
     *
     * @param structuredNoteMinimumAnnualReturn structuredNoteMinimumAnnualReturn
     */
    public void setStructuredNoteMinimumAnnualReturn(CommonNodeEntity structuredNoteMinimumAnnualReturn) {
        this.structuredNoteMinimumAnnualReturn = structuredNoteMinimumAnnualReturn;
    }

    /**
     * 获取 underlyingAssetStrikePrice
     *
     * @return underlyingAssetStrikePrice.
     */
    public CommonNodeEntity getUnderlyingAssetStrikePrice() {
        return underlyingAssetStrikePrice;
    }

    /**
     * 设置 underlyingAssetStrikePrice
     *
     * @param underlyingAssetStrikePrice underlyingAssetStrikePrice
     */
    public void setUnderlyingAssetStrikePrice(CommonNodeEntity underlyingAssetStrikePrice) {
        this.underlyingAssetStrikePrice = underlyingAssetStrikePrice;
    }

    /**
     * 获取 underlyingAssetPriceOnRegistrationDate
     *
     * @return underlyingAssetPriceOnRegistrationDate.
     */
    public CommonNodeEntity getUnderlyingAssetPriceOnRegistrationDate() {
        return underlyingAssetPriceOnRegistrationDate;
    }

    /**
     * 设置 underlyingAssetPriceOnRegistrationDate
     *
     * @param underlyingAssetPriceOnRegistrationDate underlyingAssetPriceOnRegistrationDate
     */
    public void setUnderlyingAssetPriceOnRegistrationDate(CommonNodeEntity underlyingAssetPriceOnRegistrationDate) {
        this.underlyingAssetPriceOnRegistrationDate = underlyingAssetPriceOnRegistrationDate;
    }

    /**
     * 获取 transactionCosts
     *
     * @return transactionCosts.
     */
    public CommonNodeEntity getTransactionCosts() {
        return transactionCosts;
    }

    /**
     * 设置 transactionCosts
     *
     * @param transactionCosts transactionCosts
     */
    public void setTransactionCosts(CommonNodeEntity transactionCosts) {
        this.transactionCosts = transactionCosts;
    }

    /**
     * 获取 assetManagementPlanName
     *
     * @return assetManagementPlanName.
     */
    public CommonNodeEntity getAssetManagementPlanName() {
        return assetManagementPlanName;
    }

    /**
     * 设置 assetManagementPlanName
     *
     * @param assetManagementPlanName assetManagementPlanName
     */
    public void setAssetManagementPlanName(CommonNodeEntity assetManagementPlanName) {
        this.assetManagementPlanName = assetManagementPlanName;
    }

    /**
     * 获取 assetManagementPlanIssuerCode
     *
     * @return assetManagementPlanIssuerCode.
     */
    public CommonNodeEntity getAssetManagementPlanIssuerCode() {
        return assetManagementPlanIssuerCode;
    }

    /**
     * 设置 assetManagementPlanIssuerCode
     *
     * @param assetManagementPlanIssuerCode assetManagementPlanIssuerCode
     */
    public void setAssetManagementPlanIssuerCode(CommonNodeEntity assetManagementPlanIssuerCode) {
        this.assetManagementPlanIssuerCode = assetManagementPlanIssuerCode;
    }

    /**
     * 获取 assetManagementPlanRegistrationCode
     *
     * @return assetManagementPlanRegistrationCode.
     */
    public CommonNodeEntity getAssetManagementPlanRegistrationCode() {
        return assetManagementPlanRegistrationCode;
    }

    /**
     * 设置 assetManagementPlanRegistrationCode
     *
     * @param assetManagementPlanRegistrationCode assetManagementPlanRegistrationCode
     */
    public void setAssetManagementPlanRegistrationCode(CommonNodeEntity assetManagementPlanRegistrationCode) {
        this.assetManagementPlanRegistrationCode = assetManagementPlanRegistrationCode;
    }

    /**
     * 获取 manager
     *
     * @return manager.
     */
    public CommonNodeEntity getManager() {
        return manager;
    }

    /**
     * 设置 manager
     *
     * @param manager manager
     */
    public void setManager(CommonNodeEntity manager) {
        this.manager = manager;
    }

    /**
     * 获取 custodian
     *
     * @return custodian.
     */
    public CommonNodeEntity getCustodian() {
        return custodian;
    }

    /**
     * 设置 custodian
     *
     * @param custodian custodian
     */
    public void setCustodian(CommonNodeEntity custodian) {
        this.custodian = custodian;
    }

    /**
     * 获取 amountForAssetManagementPlans
     *
     * @return amountForAssetManagementPlans.
     */
    public CommonNodeEntity getAmountForAssetManagementPlans() {
        return amountForAssetManagementPlans;
    }

    /**
     * 设置 amountForAssetManagementPlans
     *
     * @param amountForAssetManagementPlans amountForAssetManagementPlans
     */
    public void setAmountForAssetManagementPlans(CommonNodeEntity amountForAssetManagementPlans) {
        this.amountForAssetManagementPlans = amountForAssetManagementPlans;
    }

    /**
     * 获取 actualInvestmentDirectionOfFund
     *
     * @return actualInvestmentDirectionOfFund.
     */
    public CommonNodeEntity getActualInvestmentDirectionOfFund() {
        return actualInvestmentDirectionOfFund;
    }

    /**
     * 设置 actualInvestmentDirectionOfFund
     *
     * @param actualInvestmentDirectionOfFund actualInvestmentDirectionOfFund
     */
    public void setActualInvestmentDirectionOfFund(CommonNodeEntity actualInvestmentDirectionOfFund) {
        this.actualInvestmentDirectionOfFund = actualInvestmentDirectionOfFund;
    }

    /**
     * 获取 detailsOnInvestment
     *
     * @return detailsOnInvestment.
     */
    public CommonNodeEntity getDetailsOnInvestment() {
        return detailsOnInvestment;
    }

    /**
     * 设置 detailsOnInvestment
     *
     * @param detailsOnInvestment detailsOnInvestment
     */
    public void setDetailsOnInvestment(CommonNodeEntity detailsOnInvestment) {
        this.detailsOnInvestment = detailsOnInvestment;
    }

    /**
     * 获取 industryOfInvestment
     *
     * @return industryOfInvestment.
     */
    public CommonNodeEntity getIndustryOfInvestment() {
        return industryOfInvestment;
    }

    /**
     * 设置 industryOfInvestment
     *
     * @param industryOfInvestment industryOfInvestment
     */
    public void setIndustryOfInvestment(CommonNodeEntity industryOfInvestment) {
        this.industryOfInvestment = industryOfInvestment;
    }

    /**
     * 获取 valueDateOfAssetManagementPlan
     *
     * @return valueDateOfAssetManagementPlan.
     */
    public CommonNodeEntity getValueDateOfAssetManagementPlan() {
        return valueDateOfAssetManagementPlan;
    }

    /**
     * 设置 valueDateOfAssetManagementPlan
     *
     * @param valueDateOfAssetManagementPlan valueDateOfAssetManagementPlan
     */
    public void setValueDateOfAssetManagementPlan(CommonNodeEntity valueDateOfAssetManagementPlan) {
        this.valueDateOfAssetManagementPlan = valueDateOfAssetManagementPlan;
    }

    /**
     * 获取 maturityDateOfAssetManagementPlan
     *
     * @return maturityDateOfAssetManagementPlan.
     */
    public CommonNodeEntity getMaturityDateOfAssetManagementPlan() {
        return maturityDateOfAssetManagementPlan;
    }

    /**
     * 设置 maturityDateOfAssetManagementPlan
     *
     * @param maturityDateOfAssetManagementPlan maturityDateOfAssetManagementPlan
     */
    public void setMaturityDateOfAssetManagementPlan(CommonNodeEntity maturityDateOfAssetManagementPlan) {
        this.maturityDateOfAssetManagementPlan = maturityDateOfAssetManagementPlan;
    }

    /**
     * 获取 typeOfAssetManagementPlan
     *
     * @return typeOfAssetManagementPlan.
     */
    public CommonNodeEntity getTypeOfAssetManagementPlan() {
        return typeOfAssetManagementPlan;
    }

    /**
     * 设置 typeOfAssetManagementPlan
     *
     * @param typeOfAssetManagementPlan typeOfAssetManagementPlan
     */
    public void setTypeOfAssetManagementPlan(CommonNodeEntity typeOfAssetManagementPlan) {
        this.typeOfAssetManagementPlan = typeOfAssetManagementPlan;
    }

    /**
     * 获取 expectedReturnPleaseSelectForAssetManagementPlans
     *
     * @return expectedReturnPleaseSelectForAssetManagementPlans.
     */
    public CommonNodeEntity getExpectedReturnPleaseSelectForAssetManagementPlans() {
        return expectedReturnPleaseSelectForAssetManagementPlans;
    }

    /**
     * 设置 expectedReturnPleaseSelectForAssetManagementPlans
     *
     * @param expectedReturnPleaseSelectForAssetManagementPlans
     *            expectedReturnPleaseSelectForAssetManagementPlans
     */
    public void setExpectedReturnPleaseSelectForAssetManagementPlans(CommonNodeEntity expectedReturnPleaseSelectForAssetManagementPlans) {
        this.expectedReturnPleaseSelectForAssetManagementPlans = expectedReturnPleaseSelectForAssetManagementPlans;
    }

    /**
     * 获取 maximumExpectedAnnualReturn
     *
     * @return maximumExpectedAnnualReturn.
     */
    public CommonNodeEntity getMaximumExpectedAnnualReturn() {
        return maximumExpectedAnnualReturn;
    }

    /**
     * 设置 maximumExpectedAnnualReturn
     *
     * @param maximumExpectedAnnualReturn maximumExpectedAnnualReturn
     */
    public void setMaximumExpectedAnnualReturn(CommonNodeEntity maximumExpectedAnnualReturn) {
        this.maximumExpectedAnnualReturn = maximumExpectedAnnualReturn;
    }

    /**
     * 获取 minimumExpectedAnnualReturn
     *
     * @return minimumExpectedAnnualReturn.
     */
    public CommonNodeEntity getMinimumExpectedAnnualReturn() {
        return minimumExpectedAnnualReturn;
    }

    /**
     * 设置 minimumExpectedAnnualReturn
     *
     * @param minimumExpectedAnnualReturn minimumExpectedAnnualReturn
     */
    public void setMinimumExpectedAnnualReturn(CommonNodeEntity minimumExpectedAnnualReturn) {
        this.minimumExpectedAnnualReturn = minimumExpectedAnnualReturn;
    }

    /**
     * 获取 investmentStructure
     *
     * @return investmentStructure.
     */
    public CommonNodeEntity getInvestmentStructure() {
        return investmentStructure;
    }

    /**
     * 设置 investmentStructure
     *
     * @param investmentStructure investmentStructure
     */
    public void setInvestmentStructure(CommonNodeEntity investmentStructure) {
        this.investmentStructure = investmentStructure;
    }

    /**
     * 获取 managementType
     *
     * @return managementType.
     */
    public CommonNodeEntity getManagementType() {
        return managementType;
    }

    /**
     * 设置 managementType
     *
     * @param managementType managementType
     */
    public void setManagementType(CommonNodeEntity managementType) {
        this.managementType = managementType;
    }

    /**
     * 获取 managementFeeRate
     *
     * @return managementFeeRate.
     */
    public CommonNodeEntity getManagementFeeRate() {
        return managementFeeRate;
    }

    /**
     * 设置 managementFeeRate
     *
     * @param managementFeeRate managementFeeRate
     */
    public void setManagementFeeRate(CommonNodeEntity managementFeeRate) {
        this.managementFeeRate = managementFeeRate;
    }

    /**
     * 获取 custodianFeeRateForAssetManagementPlans
     *
     * @return custodianFeeRateForAssetManagementPlans.
     */
    public CommonNodeEntity getCustodianFeeRateForAssetManagementPlans() {
        return custodianFeeRateForAssetManagementPlans;
    }

    /**
     * 设置 custodianFeeRateForAssetManagementPlans
     *
     * @param custodianFeeRateForAssetManagementPlans custodianFeeRateForAssetManagementPlans
     */
    public void setCustodianFeeRateForAssetManagementPlans(CommonNodeEntity custodianFeeRateForAssetManagementPlans) {
        this.custodianFeeRateForAssetManagementPlans = custodianFeeRateForAssetManagementPlans;
    }

    /**
     * 获取 transactionCostRate
     *
     * @return transactionCostRate.
     */
    public CommonNodeEntity getTransactionCostRate() {
        return transactionCostRate;
    }

    /**
     * 设置 transactionCostRate
     *
     * @param transactionCostRate transactionCostRate
     */
    public void setTransactionCostRate(CommonNodeEntity transactionCostRate) {
        this.transactionCostRate = transactionCostRate;
    }

    /**
     * 获取 intermediaryServiceFeeRate
     *
     * @return intermediaryServiceFeeRate.
     */
    public CommonNodeEntity getIntermediaryServiceFeeRate() {
        return intermediaryServiceFeeRate;
    }

    /**
     * 设置 intermediaryServiceFeeRate
     *
     * @param intermediaryServiceFeeRate intermediaryServiceFeeRate
     */
    public void setIntermediaryServiceFeeRate(CommonNodeEntity intermediaryServiceFeeRate) {
        this.intermediaryServiceFeeRate = intermediaryServiceFeeRate;
    }

    /**
     * 获取 otherExpensesRate
     *
     * @return otherExpensesRate.
     */
    public CommonNodeEntity getOtherExpensesRate() {
        return otherExpensesRate;
    }

    /**
     * 设置 otherExpensesRate
     *
     * @param otherExpensesRate otherExpensesRate
     */
    public void setOtherExpensesRate(CommonNodeEntity otherExpensesRate) {
        this.otherExpensesRate = otherExpensesRate;
    }

    /**
     * 获取 countryOrRegionForCommodities
     *
     * @return countryOrRegionForCommodities.
     */
    public CommonNodeEntity getCountryOrRegionForCommodities() {
        return countryOrRegionForCommodities;
    }

    /**
     * 设置 countryOrRegionForCommodities
     *
     * @param countryOrRegionForCommodities countryOrRegionForCommodities
     */
    public void setCountryOrRegionForCommodities(CommonNodeEntity countryOrRegionForCommodities) {
        this.countryOrRegionForCommodities = countryOrRegionForCommodities;
    }

    /**
     * 获取 nameForCommodities
     *
     * @return nameForCommodities.
     */
    public CommonNodeEntity getNameForCommodities() {
        return nameForCommodities;
    }

    /**
     * 设置 nameForCommodities
     *
     * @param nameForCommodities nameForCommodities
     */
    public void setNameForCommodities(CommonNodeEntity nameForCommodities) {
        this.nameForCommodities = nameForCommodities;
    }

    /**
     * 获取 termToMaturityDaysForCommodities
     *
     * @return termToMaturityDaysForCommodities.
     */
    public CommonNodeEntity getTermToMaturityDaysForCommodities() {
        return termToMaturityDaysForCommodities;
    }

    /**
     * 设置 termToMaturityDaysForCommodities
     *
     * @param termToMaturityDaysForCommodities termToMaturityDaysForCommodities
     */
    public void setTermToMaturityDaysForCommodities(CommonNodeEntity termToMaturityDaysForCommodities) {
        this.termToMaturityDaysForCommodities = termToMaturityDaysForCommodities;
    }

    /**
     * 获取 assetAnnualReturnForCommodities
     *
     * @return assetAnnualReturnForCommodities.
     */
    public CommonNodeEntity getAssetAnnualReturnForCommodities() {
        return assetAnnualReturnForCommodities;
    }

    /**
     * 设置 assetAnnualReturnForCommodities
     *
     * @param assetAnnualReturnForCommodities assetAnnualReturnForCommodities
     */
    public void setAssetAnnualReturnForCommodities(CommonNodeEntity assetAnnualReturnForCommodities) {
        this.assetAnnualReturnForCommodities = assetAnnualReturnForCommodities;
    }

    /**
     * 获取 assetValueForCommodities
     *
     * @return assetValueForCommodities.
     */
    public CommonNodeEntity getAssetValueForCommodities() {
        return assetValueForCommodities;
    }

    /**
     * 设置 assetValueForCommodities
     *
     * @param assetValueForCommodities assetValueForCommodities
     */
    public void setAssetValueForCommodities(CommonNodeEntity assetValueForCommodities) {
        this.assetValueForCommodities = assetValueForCommodities;
    }

    /**
     * 获取 countryOrRegionForAlternativeInvestments
     *
     * @return countryOrRegionForAlternativeInvestments.
     */
    public CommonNodeEntity getCountryOrRegionForAlternativeInvestments() {
        return countryOrRegionForAlternativeInvestments;
    }

    /**
     * 设置 countryOrRegionForAlternativeInvestments
     *
     * @param countryOrRegionForAlternativeInvestments countryOrRegionForAlternativeInvestments
     */
    public void setCountryOrRegionForAlternativeInvestments(CommonNodeEntity countryOrRegionForAlternativeInvestments) {
        this.countryOrRegionForAlternativeInvestments = countryOrRegionForAlternativeInvestments;
    }

    /**
     * 获取 nameForAlternativeInvestments
     *
     * @return nameForAlternativeInvestments.
     */
    public CommonNodeEntity getNameForAlternativeInvestments() {
        return nameForAlternativeInvestments;
    }

    /**
     * 设置 nameForAlternativeInvestments
     *
     * @param nameForAlternativeInvestments nameForAlternativeInvestments
     */
    public void setNameForAlternativeInvestments(CommonNodeEntity nameForAlternativeInvestments) {
        this.nameForAlternativeInvestments = nameForAlternativeInvestments;
    }

    /**
     * 获取 valueDateForAlternativeInvestments
     *
     * @return valueDateForAlternativeInvestments.
     */
    public CommonNodeEntity getValueDateForAlternativeInvestments() {
        return valueDateForAlternativeInvestments;
    }

    /**
     * 设置 valueDateForAlternativeInvestments
     *
     * @param valueDateForAlternativeInvestments valueDateForAlternativeInvestments
     */
    public void setValueDateForAlternativeInvestments(CommonNodeEntity valueDateForAlternativeInvestments) {
        this.valueDateForAlternativeInvestments = valueDateForAlternativeInvestments;
    }

    /**
     * 获取 maturityDateForAlternativeInvestments
     *
     * @return maturityDateForAlternativeInvestments.
     */
    public CommonNodeEntity getMaturityDateForAlternativeInvestments() {
        return maturityDateForAlternativeInvestments;
    }

    /**
     * 设置 maturityDateForAlternativeInvestments
     *
     * @param maturityDateForAlternativeInvestments maturityDateForAlternativeInvestments
     */
    public void setMaturityDateForAlternativeInvestments(CommonNodeEntity maturityDateForAlternativeInvestments) {
        this.maturityDateForAlternativeInvestments = maturityDateForAlternativeInvestments;
    }

    /**
     * 获取 assetValueForAlternativeInvestments
     *
     * @return assetValueForAlternativeInvestments.
     */
    public CommonNodeEntity getAssetValueForAlternativeInvestments() {
        return assetValueForAlternativeInvestments;
    }

    /**
     * 设置 assetValueForAlternativeInvestments
     *
     * @param assetValueForAlternativeInvestments assetValueForAlternativeInvestments
     */
    public void setAssetValueForAlternativeInvestments(CommonNodeEntity assetValueForAlternativeInvestments) {
        this.assetValueForAlternativeInvestments = assetValueForAlternativeInvestments;
    }

    /**
     * 获取 assetAnnualReturnForAlternativeInvestments
     *
     * @return assetAnnualReturnForAlternativeInvestments.
     */
    public CommonNodeEntity getAssetAnnualReturnForAlternativeInvestments() {
        return assetAnnualReturnForAlternativeInvestments;
    }

    /**
     * 设置 assetAnnualReturnForAlternativeInvestments
     *
     * @param assetAnnualReturnForAlternativeInvestments assetAnnualReturnForAlternativeInvestments
     */
    public void setAssetAnnualReturnForAlternativeInvestments(CommonNodeEntity assetAnnualReturnForAlternativeInvestments) {
        this.assetAnnualReturnForAlternativeInvestments = assetAnnualReturnForAlternativeInvestments;
    }

    /**
     * 获取 fundIdentificationCode
     *
     * @return fundIdentificationCode.
     */
    public CommonNodeEntity getFundIdentificationCode() {
        return fundIdentificationCode;
    }

    /**
     * 设置 fundIdentificationCode
     *
     * @param fundIdentificationCode fundIdentificationCode
     */
    public void setFundIdentificationCode(CommonNodeEntity fundIdentificationCode) {
        this.fundIdentificationCode = fundIdentificationCode;
    }

    /**
     * 获取 fundName
     *
     * @return fundName.
     */
    public CommonNodeEntity getFundName() {
        return fundName;
    }

    /**
     * 设置 fundName
     *
     * @param fundName fundName
     */
    public void setFundName(CommonNodeEntity fundName) {
        this.fundName = fundName;
    }


    /**
     * 获取 registrationAgency
     *
     * @return registrationAgency.
     */
    public CommonNodeEntity getRegistrationAgency() {
        return registrationAgency;
    }

    /**
     * 设置 registrationAgency
     *
     * @param registrationAgency registrationAgency
     */
    public void setRegistrationAgency(CommonNodeEntity registrationAgency) {
        this.registrationAgency = registrationAgency;
    }

    public CommonNodeEntity getValueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo() {
        return valueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public void setValueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo(
            CommonNodeEntity valueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo) {
        this.valueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo = valueDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public CommonNodeEntity getMaturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo() {
        return maturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public void setMaturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo(
            CommonNodeEntity maturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo) {
        this.maturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo = maturityDateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public CommonNodeEntity getCounterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo() {
        return counterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public void setCounterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo(
            CommonNodeEntity counterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo) {
        this.counterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo = counterpartyForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public CommonNodeEntity getCounterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo() {
        return counterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public void setCounterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo(
            CommonNodeEntity counterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo) {
        this.counterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo = counterpartyTypeForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public CommonNodeEntity getAnnualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo() {
        return annualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public void setAnnualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo(
            CommonNodeEntity annualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo) {
        this.annualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo = annualInterestRateForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public CommonNodeEntity getInterestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo() {
        return interestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public void setInterestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo(
            CommonNodeEntity interestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo) {
        this.interestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo = interestAccrualBasisForInterbankLendingReverseRepoAndInterbankBorrowingRepo;
    }

    public CommonNodeEntity getUnitParValue() {
        return unitParValue;
    }

    public void setUnitParValue(CommonNodeEntity unitParValue) {
        this.unitParValue = unitParValue;
    }

    public CommonNodeEntity getStatutoryMaturityDate() {
        return statutoryMaturityDate;
    }

    public void setStatutoryMaturityDate(CommonNodeEntity statutoryMaturityDate) {
        this.statutoryMaturityDate = statutoryMaturityDate;
    }

    public CommonNodeEntity getCouponType() {
        return couponType;
    }

    public void setCouponType(CommonNodeEntity couponType) {
        this.couponType = couponType;
    }

    public CommonNodeEntity getRegularInterestPaymentPleaseSelect() {
        return regularInterestPaymentPleaseSelect;
    }

    public void setRegularInterestPaymentPleaseSelect(CommonNodeEntity regularInterestPaymentPleaseSelect) {
        this.regularInterestPaymentPleaseSelect = regularInterestPaymentPleaseSelect;
    }

    public CommonNodeEntity getCouponAllocationType() {
        return couponAllocationType;
    }

    public void setCouponAllocationType(CommonNodeEntity couponAllocationType) {
        this.couponAllocationType = couponAllocationType;
    }

    public CommonNodeEntity getCreditEnhancementInstitutionCode() {
        return creditEnhancementInstitutionCode;
    }

    public void setCreditEnhancementInstitutionCode(CommonNodeEntity creditEnhancementInstitutionCode) {
        this.creditEnhancementInstitutionCode = creditEnhancementInstitutionCode;
    }

    public CommonNodeEntity getCreditEnhancementInstitutionName() {
        return creditEnhancementInstitutionName;
    }

    public void setCreditEnhancementInstitutionName(CommonNodeEntity creditEnhancementInstitutionName) {
        this.creditEnhancementInstitutionName = creditEnhancementInstitutionName;
    }

    public CommonNodeEntity getFloatingFactorPleaseSelect() {
        return floatingFactorPleaseSelect;
    }

    public void setFloatingFactorPleaseSelect(CommonNodeEntity floatingFactorPleaseSelect) {
        this.floatingFactorPleaseSelect = floatingFactorPleaseSelect;
    }

    public CommonNodeEntity getStructureGrade() {
        return structureGrade;
    }

    public void setStructureGrade(CommonNodeEntity structureGrade) {
        this.structureGrade = structureGrade;
    }

    public CommonNodeEntity getPrincipalPaymentType() {
        return principalPaymentType;
    }

    public void setPrincipalPaymentType(CommonNodeEntity principalPaymentType) {
        this.principalPaymentType = principalPaymentType;
    }

    public CommonNodeEntity getInstallmentRepayType() {
        return installmentRepayType;
    }

    public void setInstallmentRepayType(CommonNodeEntity installmentRepayType) {
        this.installmentRepayType = installmentRepayType;
    }

    public CommonNodeEntity getBaseAssetType() {
        return baseAssetType;
    }

    public void setBaseAssetType(CommonNodeEntity baseAssetType) {
        this.baseAssetType = baseAssetType;
    }

    public CommonNodeEntity getPercentageOfExcessEarningsDistribution() {
        return percentageOfExcessEarningsDistribution;
    }

    public void setPercentageOfExcessEarningsDistribution(CommonNodeEntity percentageOfExcessEarningsDistribution) {
        this.percentageOfExcessEarningsDistribution = percentageOfExcessEarningsDistribution;
    }

    public CommonNodeEntity getPledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations() {
        return pledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public void setPledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations(
            CommonNodeEntity pledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.pledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations = pledgeTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public CommonNodeEntity getPledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations() {
        return pledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public void setPledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations(
            CommonNodeEntity pledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.pledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations = pledgeValueCNYForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public CommonNodeEntity getGuaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations() {
        return guaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public void setGuaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations(
            CommonNodeEntity guaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.guaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations = guaranteeTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public CommonNodeEntity getGuarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations() {
        return guarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public void setGuarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations(
            CommonNodeEntity guarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations) {
        this.guarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations = guarantorTypeForCreditAssetTradingsAndNonStandardDebtObligations;
    }

    public CommonNodeEntity getExerciseDateType() {
        return exerciseDateType;
    }

    public void setExerciseDateType(CommonNodeEntity exerciseDateType) {
        this.exerciseDateType = exerciseDateType;
    }

    public CommonNodeEntity getFixedExerciseDate() {
        return fixedExerciseDate;
    }

    public void setFixedExerciseDate(CommonNodeEntity fixedExerciseDate) {
        this.fixedExerciseDate = fixedExerciseDate;
    }

    public CommonNodeEntity getFirstExerciseDate() {
        return firstExerciseDate;
    }

    public void setFirstExerciseDate(CommonNodeEntity firstExerciseDate) {
        this.firstExerciseDate = firstExerciseDate;
    }

    public CommonNodeEntity getExercisePeriod() {
        return exercisePeriod;
    }

    public void setExercisePeriod(CommonNodeEntity exercisePeriod) {
        this.exercisePeriod = exercisePeriod;
    }

    public CommonNodeEntity getExercisePrice() {
        return exercisePrice;
    }

    public void setExercisePrice(CommonNodeEntity exercisePrice) {
        this.exercisePrice = exercisePrice;
    }

    public CommonNodeEntity getPerpetualType() {
        return perpetualType;
    }

    public void setPerpetualType(CommonNodeEntity perpetualType) {
        this.perpetualType = perpetualType;
    }

    public CommonNodeEntity getDeferredInterestType() {
        return deferredInterestType;
    }

    public void setDeferredInterestType(CommonNodeEntity deferredInterestType) {
        this.deferredInterestType = deferredInterestType;
    }

    public CommonNodeEntity getInterestBearingForDeferredInterestPleaseSelect() {
        return interestBearingForDeferredInterestPleaseSelect;
    }

    public void setInterestBearingForDeferredInterestPleaseSelect(
            CommonNodeEntity interestBearingForDeferredInterestPleaseSelect) {
        this.interestBearingForDeferredInterestPleaseSelect = interestBearingForDeferredInterestPleaseSelect;
    }

    public CommonNodeEntity getFirstRepriceDate() {
        return firstRepriceDate;
    }

    public void setFirstRepriceDate(CommonNodeEntity firstRepriceDate) {
        this.firstRepriceDate = firstRepriceDate;
    }

    public CommonNodeEntity getRepricePeriod() {
        return repricePeriod;
    }

    public void setRepricePeriod(CommonNodeEntity repricePeriod) {
        this.repricePeriod = repricePeriod;
    }

    public CommonNodeEntity getPartialRedemptionPleaseSelect() {
        return partialRedemptionPleaseSelect;
    }

    public void setPartialRedemptionPleaseSelect(CommonNodeEntity partialRedemptionPleaseSelect) {
        this.partialRedemptionPleaseSelect = partialRedemptionPleaseSelect;
    }

    public CommonNodeEntity getPartialRedemptionRates() {
        return partialRedemptionRates;
    }

    public void setPartialRedemptionRates(CommonNodeEntity partialRedemptionRates) {
        this.partialRedemptionRates = partialRedemptionRates;
    }

    public CommonNodeEntity getOptionType() {
        return optionType;
    }

    public void setOptionType(CommonNodeEntity optionType) {
        this.optionType = optionType;
    }

    public CommonNodeEntity getEquityWithdrawalDate() {
        return equityWithdrawalDate;
    }

    public void setEquityWithdrawalDate(CommonNodeEntity equityWithdrawalDate) {
        this.equityWithdrawalDate = equityWithdrawalDate;
    }

    public CommonNodeEntity getDebtToEquitySwapPleaseSelectForEquities() {
        return debtToEquitySwapPleaseSelectForEquities;
    }

    public void setDebtToEquitySwapPleaseSelectForEquities(CommonNodeEntity debtToEquitySwapPleaseSelectForEquities) {
        this.debtToEquitySwapPleaseSelectForEquities = debtToEquitySwapPleaseSelectForEquities;
    }

    public CommonNodeEntity getHoldingObjective() {
        return holdingObjective;
    }

    public void setHoldingObjective(CommonNodeEntity holdingObjective) {
        this.holdingObjective = holdingObjective;
    }

    public CommonNodeEntity getIssuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans() {
        return issuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans;
    }

    public void setIssuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans(
            CommonNodeEntity issuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans) {
        this.issuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans = issuedByFinancialAssetInvestmentCompanyPleaseSelectForAssetManagementPlans;
    }

    public CommonNodeEntity getIndustryForPublicFundsPrivateFunds() {
        return industryForPublicFundsPrivateFunds;
    }

    public void setIndustryForPublicFundsPrivateFunds(CommonNodeEntity industryForPublicFundsPrivateFunds) {
        this.industryForPublicFundsPrivateFunds = industryForPublicFundsPrivateFunds;
    }

    public CommonNodeEntity getGovernmentInvestmentFundPleaseSelect() {
        return governmentInvestmentFundPleaseSelect;
    }

    public void setGovernmentInvestmentFundPleaseSelect(CommonNodeEntity governmentInvestmentFundPleaseSelect) {
        this.governmentInvestmentFundPleaseSelect = governmentInvestmentFundPleaseSelect;
    }

    public CommonNodeEntity getInvestmentDirectionOfGovernmentInvestmentFund() {
        return investmentDirectionOfGovernmentInvestmentFund;
    }

    public void setInvestmentDirectionOfGovernmentInvestmentFund(
            CommonNodeEntity investmentDirectionOfGovernmentInvestmentFund) {
        this.investmentDirectionOfGovernmentInvestmentFund = investmentDirectionOfGovernmentInvestmentFund;
    }

    public CommonNodeEntity getIssuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds() {
        return issuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds;
    }

    public void setIssuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds(
            CommonNodeEntity issuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds) {
        this.issuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds = issuedByFinancialAssetInvestmentCompanyPleaseSelectForPublicFundsPrivateFunds;
    }

    public CommonNodeEntity getNameOfInvestmentFund() {
        return nameOfInvestmentFund;
    }

    public void setNameOfInvestmentFund(CommonNodeEntity nameOfInvestmentFund) {
        this.nameOfInvestmentFund = nameOfInvestmentFund;
    }

    public CommonNodeEntity getInvestmentStageForPublicFundsPrivateFunds() {
        return investmentStageForPublicFundsPrivateFunds;
    }

    public void setInvestmentStageForPublicFundsPrivateFunds(CommonNodeEntity investmentStageForPublicFundsPrivateFunds) {
        this.investmentStageForPublicFundsPrivateFunds = investmentStageForPublicFundsPrivateFunds;
    }

    public CommonNodeEntity getDetailsOnAssetType() {
        return detailsOnAssetType;
    }

    public void setDetailsOnAssetType(CommonNodeEntity detailsOnAssetType) {
        this.detailsOnAssetType = detailsOnAssetType;
    }

    public CommonNodeEntity getGuaranteeMethodForOther() {
        return guaranteeMethodForOther;
    }

    public void setGuaranteeMethodForOther(CommonNodeEntity guaranteeMethodForOther) {
        this.guaranteeMethodForOther = guaranteeMethodForOther;
    }

    public CommonNodeEntity getPledgeTypeForOther() {
        return pledgeTypeForOther;
    }

    public void setPledgeTypeForOther(CommonNodeEntity pledgeTypeForOther) {
        this.pledgeTypeForOther = pledgeTypeForOther;
    }

    public CommonNodeEntity getPledgeValueCNYForOther() {
        return pledgeValueCNYForOther;
    }

    public void setPledgeValueCNYForOther(CommonNodeEntity pledgeValueCNYForOther) {
        this.pledgeValueCNYForOther = pledgeValueCNYForOther;
    }

    public CommonNodeEntity getGuaranteeTypeForOther() {
        return guaranteeTypeForOther;
    }

    public void setGuaranteeTypeForOther(CommonNodeEntity guaranteeTypeForOther) {
        this.guaranteeTypeForOther = guaranteeTypeForOther;
    }

    public CommonNodeEntity getGuarantorTypeForOther() {
        return guarantorTypeForOther;
    }

    public void setGuarantorTypeForOther(CommonNodeEntity guarantorTypeForOther) {
        this.guarantorTypeForOther = guarantorTypeForOther;
    }

    public CommonNodeEntity getDetailsOnGuaranteeStatusForOther() {
        return detailsOnGuaranteeStatusForOther;
    }

    public void setDetailsOnGuaranteeStatusForOther(CommonNodeEntity detailsOnGuaranteeStatusForOther) {
        this.detailsOnGuaranteeStatusForOther = detailsOnGuaranteeStatusForOther;
    }

    public CommonNodeEntity getDebtToEquitySwapPleaseSelectForOther() {
        return debtToEquitySwapPleaseSelectForOther;
    }

    public void setDebtToEquitySwapPleaseSelectForOther(CommonNodeEntity debtToEquitySwapPleaseSelectForOther) {
        this.debtToEquitySwapPleaseSelectForOther = debtToEquitySwapPleaseSelectForOther;
    }

    public CommonNodeEntity getOutsourcingInvestmentAgreementName() {
        return outsourcingInvestmentAgreementName;
    }

    public void setOutsourcingInvestmentAgreementName(CommonNodeEntity outsourcingInvestmentAgreementName) {
        this.outsourcingInvestmentAgreementName = outsourcingInvestmentAgreementName;
    }

    public CommonNodeEntity getOutsourcingInvestmentAgreementCode() {
        return outsourcingInvestmentAgreementCode;
    }

    public void setOutsourcingInvestmentAgreementCode(CommonNodeEntity outsourcingInvestmentAgreementCode) {
        this.outsourcingInvestmentAgreementCode = outsourcingInvestmentAgreementCode;
    }

    public CommonNodeEntity getTrustee() {
        return trustee;
    }

    public void setTrustee(CommonNodeEntity trustee) {
        this.trustee = trustee;
    }

    public CommonNodeEntity getActualManager() {
        return actualManager;
    }

    public void setActualManager(CommonNodeEntity actualManager) {
        this.actualManager = actualManager;
    }

    public CommonNodeEntity getCustodianForOutsourcingInvestmentAgreement() {
        return custodianForOutsourcingInvestmentAgreement;
    }

    public void setCustodianForOutsourcingInvestmentAgreement(CommonNodeEntity custodianForOutsourcingInvestmentAgreement) {
        this.custodianForOutsourcingInvestmentAgreement = custodianForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getOutsourcingInvestmentAmount() {
        return outsourcingInvestmentAmount;
    }

    public void setOutsourcingInvestmentAmount(CommonNodeEntity outsourcingInvestmentAmount) {
        this.outsourcingInvestmentAmount = outsourcingInvestmentAmount;
    }

    public CommonNodeEntity getActualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement() {
        return actualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement;
    }

    public void setActualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement(
            CommonNodeEntity actualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement) {
        this.actualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement = actualInvestmentDirectionOfFundForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getDetailsOnInvestmentForOutsourcingInvestmentAgreement() {
        return detailsOnInvestmentForOutsourcingInvestmentAgreement;
    }

    public void setDetailsOnInvestmentForOutsourcingInvestmentAgreement(
            CommonNodeEntity detailsOnInvestmentForOutsourcingInvestmentAgreement) {
        this.detailsOnInvestmentForOutsourcingInvestmentAgreement = detailsOnInvestmentForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getIndustryOfInvestmentForOutsourcingInvestmentAgreement() {
        return industryOfInvestmentForOutsourcingInvestmentAgreement;
    }

    public void setIndustryOfInvestmentForOutsourcingInvestmentAgreement(
            CommonNodeEntity industryOfInvestmentForOutsourcingInvestmentAgreement) {
        this.industryOfInvestmentForOutsourcingInvestmentAgreement = industryOfInvestmentForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getValueDateOfOutsourcingInvestment() {
        return valueDateOfOutsourcingInvestment;
    }

    public void setValueDateOfOutsourcingInvestment(CommonNodeEntity valueDateOfOutsourcingInvestment) {
        this.valueDateOfOutsourcingInvestment = valueDateOfOutsourcingInvestment;
    }

    public CommonNodeEntity getMaturityDateOfOutsourcingInvestment() {
        return maturityDateOfOutsourcingInvestment;
    }

    public void setMaturityDateOfOutsourcingInvestment(CommonNodeEntity maturityDateOfOutsourcingInvestment) {
        this.maturityDateOfOutsourcingInvestment = maturityDateOfOutsourcingInvestment;
    }

    public CommonNodeEntity getTypeOfOutsourcingInvestment() {
        return typeOfOutsourcingInvestment;
    }

    public void setTypeOfOutsourcingInvestment(CommonNodeEntity typeOfOutsourcingInvestment) {
        this.typeOfOutsourcingInvestment = typeOfOutsourcingInvestment;
    }

    public CommonNodeEntity getExpectedReturnPleaseSelectForOutsourcingInvestmentAgreement() {
        return expectedReturnPleaseSelectForOutsourcingInvestmentAgreement;
    }

    public void setExpectedReturnPleaseSelectForOutsourcingInvestmentAgreement(
            CommonNodeEntity expectedReturnPleaseSelectForOutsourcingInvestmentAgreement) {
        this.expectedReturnPleaseSelectForOutsourcingInvestmentAgreement = expectedReturnPleaseSelectForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getMaximumExpectedAnnualReturnForOutsourcingInvestmentAgreement() {
        return maximumExpectedAnnualReturnForOutsourcingInvestmentAgreement;
    }

    public void setMaximumExpectedAnnualReturnForOutsourcingInvestmentAgreement(
            CommonNodeEntity maximumExpectedAnnualReturnForOutsourcingInvestmentAgreement) {
        this.maximumExpectedAnnualReturnForOutsourcingInvestmentAgreement = maximumExpectedAnnualReturnForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getMinimumExpectedAnnualReturnForOutsourcingInvestmentAgreement() {
        return minimumExpectedAnnualReturnForOutsourcingInvestmentAgreement;
    }

    public void setMinimumExpectedAnnualReturnForOutsourcingInvestmentAgreement(
            CommonNodeEntity minimumExpectedAnnualReturnForOutsourcingInvestmentAgreement) {
        this.minimumExpectedAnnualReturnForOutsourcingInvestmentAgreement = minimumExpectedAnnualReturnForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getManagementFeeRateForOutsourcingInvestmentAgreement() {
        return managementFeeRateForOutsourcingInvestmentAgreement;
    }

    public void setManagementFeeRateForOutsourcingInvestmentAgreement(
            CommonNodeEntity managementFeeRateForOutsourcingInvestmentAgreement) {
        this.managementFeeRateForOutsourcingInvestmentAgreement = managementFeeRateForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getCustodianFeeRateForOutsourcingInvestmentAgreement() {
        return custodianFeeRateForOutsourcingInvestmentAgreement;
    }

    public void setCustodianFeeRateForOutsourcingInvestmentAgreement(
            CommonNodeEntity custodianFeeRateForOutsourcingInvestmentAgreement) {
        this.custodianFeeRateForOutsourcingInvestmentAgreement = custodianFeeRateForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getTransactionCostRateForOutsourcingInvestmentAgreement() {
        return transactionCostRateForOutsourcingInvestmentAgreement;
    }

    public void setTransactionCostRateForOutsourcingInvestmentAgreement(
            CommonNodeEntity transactionCostRateForOutsourcingInvestmentAgreement) {
        this.transactionCostRateForOutsourcingInvestmentAgreement = transactionCostRateForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getIntermediaryServiceFeeRateForOutsourcingInvestmentAgreement() {
        return intermediaryServiceFeeRateForOutsourcingInvestmentAgreement;
    }

    public void setIntermediaryServiceFeeRateForOutsourcingInvestmentAgreement(
            CommonNodeEntity intermediaryServiceFeeRateForOutsourcingInvestmentAgreement) {
        this.intermediaryServiceFeeRateForOutsourcingInvestmentAgreement = intermediaryServiceFeeRateForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getOtherExpensesRateForOutsourcingInvestmentAgreement() {
        return otherExpensesRateForOutsourcingInvestmentAgreement;
    }

    public void setOtherExpensesRateForOutsourcingInvestmentAgreement(
            CommonNodeEntity otherExpensesRateForOutsourcingInvestmentAgreement) {
        this.otherExpensesRateForOutsourcingInvestmentAgreement = otherExpensesRateForOutsourcingInvestmentAgreement;
    }

    public CommonNodeEntity getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(CommonNodeEntity outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public CommonNodeEntity getDate() {
        return date;
    }

    public void setDate(CommonNodeEntity date) {
        this.date = date;
    }

    public CommonNodeEntity getInvestedAssetsLiabilitiesRegistrationDetails() {
        return investedAssetsLiabilitiesRegistrationDetails;
    }

    public void setInvestedAssetsLiabilitiesRegistrationDetails(
            CommonNodeEntity investedAssetsLiabilitiesRegistrationDetails) {
        this.investedAssetsLiabilitiesRegistrationDetails = investedAssetsLiabilitiesRegistrationDetails;
    }

    public void setDetailsOnRegistryAndDepository(CommonNodeEntity detailsOnRegistryAndDepository) {
        this.detailsOnRegistryAndDepository = detailsOnRegistryAndDepository;
    }

    /**
     * 获取 nameOfFundManagementInstitution
     *
     * @return nameOfFundManagementInstitution.
     */
    public CommonNodeEntity getNameOfFundManagementInstitution() {
        return nameOfFundManagementInstitution;
    }

    /**
     * 设置 nameOfFundManagementInstitution
     *
     * @param nameOfFundManagementInstitution nameOfFundManagementInstitution
     */
    public void setNameOfFundManagementInstitution(CommonNodeEntity nameOfFundManagementInstitution) {
        this.nameOfFundManagementInstitution = nameOfFundManagementInstitution;
    }

    /**
     * 获取 nameOfFundCustodianInstitution
     *
     * @return nameOfFundCustodianInstitution.
     */
    public CommonNodeEntity getNameOfFundCustodianInstitution() {
        return nameOfFundCustodianInstitution;
    }

    /**
     * 设置 nameOfFundCustodianInstitution
     *
     * @param nameOfFundCustodianInstitution nameOfFundCustodianInstitution
     */
    public void setNameOfFundCustodianInstitution(CommonNodeEntity nameOfFundCustodianInstitution) {
        this.nameOfFundCustodianInstitution = nameOfFundCustodianInstitution;
    }

    /**
     * 获取 investmentEnterpriseTypeByScale
     *
     * @return investmentEnterpriseTypeByScale.
     */
    public CommonNodeEntity getInvestmentEnterpriseTypeByScale() {
        return investmentEnterpriseTypeByScale;
    }

    /**
     * 设置 investmentEnterpriseTypeByScale
     *
     * @param investmentEnterpriseTypeByScale investmentEnterpriseTypeByScale
     */
    public void setInvestmentEnterpriseTypeByScale(CommonNodeEntity investmentEnterpriseTypeByScale) {
        this.investmentEnterpriseTypeByScale = investmentEnterpriseTypeByScale;
    }

    /**
     * 获取 investmentEnterpriseTypeByTechnicalField
     *
     * @return investmentEnterpriseTypeByTechnicalField.
     */
    public CommonNodeEntity getInvestmentEnterpriseTypeByTechnicalField() {
        return investmentEnterpriseTypeByTechnicalField;
    }

    /**
     * 设置 investmentEnterpriseTypeByTechnicalField
     *
     * @param investmentEnterpriseTypeByTechnicalField investmentEnterpriseTypeByTechnicalField
     */
    public void setInvestmentEnterpriseTypeByTechnicalField(CommonNodeEntity investmentEnterpriseTypeByTechnicalField) {
        this.investmentEnterpriseTypeByTechnicalField = investmentEnterpriseTypeByTechnicalField;
    }

    /**
     * 获取 investmentEnterpriseTypeByEconomicType
     *
     * @return investmentEnterpriseTypeByEconomicType.
     */
    public CommonNodeEntity getInvestmentEnterpriseTypeByEconomicType() {
        return investmentEnterpriseTypeByEconomicType;
    }

    /**
     * 设置 investmentEnterpriseTypeByEconomicType
     *
     * @param investmentEnterpriseTypeByEconomicType investmentEnterpriseTypeByEconomicType
     */
    public void setInvestmentEnterpriseTypeByEconomicType(CommonNodeEntity investmentEnterpriseTypeByEconomicType) {
        this.investmentEnterpriseTypeByEconomicType = investmentEnterpriseTypeByEconomicType;
    }

    /**
     * 获取 investmentAssets
     *
     * @return investmentAssets.
     */
    public CommonNodeEntity getInvestmentAssets() {
        return investmentAssets;
    }

    /**
     * 设置 investmentAssets
     *
     * @param investmentAssets investmentAssets
     */
    public void setInvestmentAssets(CommonNodeEntity investmentAssets) {
        this.investmentAssets = investmentAssets;
    }

    /**
     * 获取 countryOrRegionForOtherLiabilities
     *
     * @return countryOrRegionForOtherLiabilities.
     */
    public CommonNodeEntity getCountryOrRegionForOtherLiabilities() {
        return countryOrRegionForOtherLiabilities;
    }

    /**
     * 设置 countryOrRegionForOtherLiabilities
     *
     * @param countryOrRegionForOtherLiabilities countryOrRegionForOtherLiabilities
     */
    public void setCountryOrRegionForOtherLiabilities(CommonNodeEntity countryOrRegionForOtherLiabilities) {
        this.countryOrRegionForOtherLiabilities = countryOrRegionForOtherLiabilities;
    }

    /**
     * 获取 nameForOtherLiabilities
     *
     * @return nameForOtherLiabilities.
     */
    public CommonNodeEntity getNameForOtherLiabilities() {
        return nameForOtherLiabilities;
    }

    /**
     * 设置 nameForOtherLiabilities
     *
     * @param nameForOtherLiabilities nameForOtherLiabilities
     */
    public void setNameForOtherLiabilities(CommonNodeEntity nameForOtherLiabilities) {
        this.nameForOtherLiabilities = nameForOtherLiabilities;
    }

    /**
     * 获取 termToMaturityDaysForOtherLiabilities
     *
     * @return termToMaturityDaysForOtherLiabilities.
     */
    public CommonNodeEntity getTermToMaturityDaysForOtherLiabilities() {
        return termToMaturityDaysForOtherLiabilities;
    }

    /**
     * 设置 termToMaturityDaysForOtherLiabilities
     *
     * @param termToMaturityDaysForOtherLiabilities termToMaturityDaysForOtherLiabilities
     */
    public void setTermToMaturityDaysForOtherLiabilities(CommonNodeEntity termToMaturityDaysForOtherLiabilities) {
        this.termToMaturityDaysForOtherLiabilities = termToMaturityDaysForOtherLiabilities;
    }

    /**
     * 获取 liabilityAmount
     *
     * @return liabilityAmount.
     */
    public CommonNodeEntity getLiabilityAmount() {
        return liabilityAmount;
    }

    /**
     * 设置 liabilityAmount
     *
     * @param liabilityAmount liabilityAmount
     */
    public void setLiabilityAmount(CommonNodeEntity liabilityAmount) {
        this.liabilityAmount = liabilityAmount;
    }

    /**
     * 获取 annualInterestRate
     *
     * @return annualInterestRate.
     */
    public CommonNodeEntity getAnnualInterestRate() {
        return annualInterestRate;
    }

    /**
     * 设置 annualInterestRate
     *
     * @param annualInterestRate annualInterestRate
     */
    public void setAnnualInterestRate(CommonNodeEntity annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    /**
     * 获取 nameForOther
     *
     * @return nameForOther.
     */
    public CommonNodeEntity getNameForOther() {
        return nameForOther;
    }

    /**
     * 设置 nameForOther
     *
     * @param nameForOther nameForOther
     */
    public void setNameForOther(CommonNodeEntity nameForOther) {
        this.nameForOther = nameForOther;
    }

    /**
     * 获取 assetType
     *
     * @return assetType.
     */
    public CommonNodeEntity getAssetType() {
        return assetType;
    }

    /**
     * 设置 assetType
     *
     * @param assetType assetType
     */
    public void setAssetType(CommonNodeEntity assetType) {
        this.assetType = assetType;
    }

    /**
     * 获取 amountForOther
     *
     * @return amountForOther.
     */
    public CommonNodeEntity getAmountForOther() {
        return amountForOther;
    }

    /**
     * 设置 amountForOther
     *
     * @param amountForOther amountForOther
     */
    public void setAmountForOther(CommonNodeEntity amountForOther) {
        this.amountForOther = amountForOther;
    }

    /**
     * 获取 valueDateForOther
     *
     * @return valueDateForOther.
     */
    public CommonNodeEntity getValueDateForOther() {
        return valueDateForOther;
    }

    /**
     * 设置 valueDateForOther
     *
     * @param valueDateForOther valueDateForOther
     */
    public void setValueDateForOther(CommonNodeEntity valueDateForOther) {
        this.valueDateForOther = valueDateForOther;
    }

    /**
     * 获取 maturityDateForOther
     *
     * @return maturityDateForOther.
     */
    public CommonNodeEntity getMaturityDateForOther() {
        return maturityDateForOther;
    }

    /**
     * 设置 maturityDateForOther
     *
     * @param maturityDateForOther maturityDateForOther
     */
    public void setMaturityDateForOther(CommonNodeEntity maturityDateForOther) {
        this.maturityDateForOther = maturityDateForOther;
    }

    /**
     * 获取 countryOrRegionForOther
     *
     * @return countryOrRegionForOther.
     */
    public CommonNodeEntity getCountryOrRegionForOther() {
        return countryOrRegionForOther;
    }

    /**
     * 设置 countryOrRegionForOther
     *
     * @param countryOrRegionForOther countryOrRegionForOther
     */
    public void setCountryOrRegionForOther(CommonNodeEntity countryOrRegionForOther) {
        this.countryOrRegionForOther = countryOrRegionForOther;
    }

    /**
     * 获取 expectedReturnPleaseSelectForOther
     *
     * @return expectedReturnPleaseSelectForOther.
     */
    public CommonNodeEntity getExpectedReturnPleaseSelectForOther() {
        return expectedReturnPleaseSelectForOther;
    }

    /**
     * 设置 expectedReturnPleaseSelectForOther
     *
     * @param expectedReturnPleaseSelectForOther expectedReturnPleaseSelectForOther
     */
    public void setExpectedReturnPleaseSelectForOther(CommonNodeEntity expectedReturnPleaseSelectForOther) {
        this.expectedReturnPleaseSelectForOther = expectedReturnPleaseSelectForOther;
    }

    /**
     * 获取 projectAnnualReturnForOther
     *
     * @return projectAnnualReturnForOther.
     */
    public CommonNodeEntity getProjectAnnualReturnForOther() {
        return projectAnnualReturnForOther;
    }

    /**
     * 设置 projectAnnualReturnForOther
     *
     * @param projectAnnualReturnForOther projectAnnualReturnForOther
     */
    public void setProjectAnnualReturnForOther(CommonNodeEntity projectAnnualReturnForOther) {
        this.projectAnnualReturnForOther = projectAnnualReturnForOther;
    }

    /**
     * 获取 interestPaymentFrequencyMeasuredByMonthForOther
     *
     * @return interestPaymentFrequencyMeasuredByMonthForOther.
     */
    public CommonNodeEntity getInterestPaymentFrequencyMeasuredByMonthForOther() {
        return interestPaymentFrequencyMeasuredByMonthForOther;
    }

    /**
     * 设置 interestPaymentFrequencyMeasuredByMonthForOther
     *
     * @param interestPaymentFrequencyMeasuredByMonthForOther
     *            interestPaymentFrequencyMeasuredByMonthForOther
     */
    public void setInterestPaymentFrequencyMeasuredByMonthForOther(CommonNodeEntity interestPaymentFrequencyMeasuredByMonthForOther) {
        this.interestPaymentFrequencyMeasuredByMonthForOther = interestPaymentFrequencyMeasuredByMonthForOther;
    }

    /**
     * 获取 debtorForOther
     *
     * @return debtorForOther.
     */
    public CommonNodeEntity getDebtorForOther() {
        return debtorForOther;
    }

    /**
     * 设置 debtorForOther
     *
     * @param debtorForOther debtorForOther
     */
    public void setDebtorForOther(CommonNodeEntity debtorForOther) {
        this.debtorForOther = debtorForOther;
    }

    /**
     * 获取 organizationCodeForOther
     *
     * @return organizationCodeForOther.
     */
    public CommonNodeEntity getOrganizationCodeForOther() {
        return organizationCodeForOther;
    }

    /**
     * 设置 organizationCodeForOther
     *
     * @param organizationCodeForOther organizationCodeForOther
     */
    public void setOrganizationCodeForOther(CommonNodeEntity organizationCodeForOther) {
        this.organizationCodeForOther = organizationCodeForOther;
    }

    /**
     * 获取 ratingAgencyAndIssuerRatingForOther
     *
     * @return ratingAgencyAndIssuerRatingForOther.
     */
    public CommonNodeEntity getRatingAgencyAndIssuerRatingForOther() {
        return ratingAgencyAndIssuerRatingForOther;
    }

    /**
     * 设置 ratingAgencyAndIssuerRatingForOther
     *
     * @param ratingAgencyAndIssuerRatingForOther ratingAgencyAndIssuerRatingForOther
     */
    public void setRatingAgencyAndIssuerRatingForOther(CommonNodeEntity ratingAgencyAndIssuerRatingForOther) {
        this.ratingAgencyAndIssuerRatingForOther = ratingAgencyAndIssuerRatingForOther;
    }

    /**
     * 获取 debtorTypeByScaleForOther
     *
     * @return debtorTypeByScaleForOther.
     */
    public CommonNodeEntity getDebtorTypeByScaleForOther() {
        return debtorTypeByScaleForOther;
    }

    /**
     * 设置 debtorTypeByScaleForOther
     *
     * @param debtorTypeByScaleForOther debtorTypeByScaleForOther
     */
    public void setDebtorTypeByScaleForOther(CommonNodeEntity debtorTypeByScaleForOther) {
        this.debtorTypeByScaleForOther = debtorTypeByScaleForOther;
    }

    /**
     * 获取 debtorTypeByTechnicalFieldForOther
     *
     * @return debtorTypeByTechnicalFieldForOther.
     */
    public CommonNodeEntity getDebtorTypeByTechnicalFieldForOther() {
        return debtorTypeByTechnicalFieldForOther;
    }

    /**
     * 设置 debtorTypeByTechnicalFieldForOther
     *
     * @param debtorTypeByTechnicalFieldForOther debtorTypeByTechnicalFieldForOther
     */
    public void setDebtorTypeByTechnicalFieldForOther(CommonNodeEntity debtorTypeByTechnicalFieldForOther) {
        this.debtorTypeByTechnicalFieldForOther = debtorTypeByTechnicalFieldForOther;
    }

    /**
     * 获取 debtorTypeByEconomicTypeForOther
     *
     * @return debtorTypeByEconomicTypeForOther.
     */
    public CommonNodeEntity getDebtorTypeByEconomicTypeForOther() {
        return debtorTypeByEconomicTypeForOther;
    }

    /**
     * 设置 debtorTypeByEconomicTypeForOther
     *
     * @param debtorTypeByEconomicTypeForOther debtorTypeByEconomicTypeForOther
     */
    public void setDebtorTypeByEconomicTypeForOther(CommonNodeEntity debtorTypeByEconomicTypeForOther) {
        this.debtorTypeByEconomicTypeForOther = debtorTypeByEconomicTypeForOther;
    }

    /**
     * 获取 industryOfDebtorForOther
     *
     * @return industryOfDebtorForOther.
     */
    public CommonNodeEntity getIndustryOfDebtorForOther() {
        return industryOfDebtorForOther;
    }

    /**
     * 设置 industryOfDebtorForOther
     *
     * @param industryOfDebtorForOther industryOfDebtorForOther
     */
    public void setIndustryOfDebtorForOther(CommonNodeEntity industryOfDebtorForOther) {
        this.industryOfDebtorForOther = industryOfDebtorForOther;
    }

    /**
     * 获取 projectForOther
     *
     * @return projectForOther.
     */
    public CommonNodeEntity getProjectForOther() {
        return projectForOther;
    }

    /**
     * 设置 projectForOther
     *
     * @param projectForOther projectForOther
     */
    public void setProjectForOther(CommonNodeEntity projectForOther) {
        this.projectForOther = projectForOther;
    }

    /**
     * 获取 industryOfProjectForOther
     *
     * @return industryOfProjectForOther.
     */
    public CommonNodeEntity getIndustryOfProjectForOther() {
        return industryOfProjectForOther;
    }

    /**
     * 设置 industryOfProjectForOther
     *
     * @param industryOfProjectForOther industryOfProjectForOther
     */
    public void setIndustryOfProjectForOther(CommonNodeEntity industryOfProjectForOther) {
        this.industryOfProjectForOther = industryOfProjectForOther;
    }

    /**
     * 获取 keyMonitoringIndustryPleaseSelectForOther
     *
     * @return keyMonitoringIndustryPleaseSelectForOther.
     */
    public CommonNodeEntity getKeyMonitoringIndustryPleaseSelectForOther() {
        return keyMonitoringIndustryPleaseSelectForOther;
    }

    /**
     * 设置 keyMonitoringIndustryPleaseSelectForOther
     *
     * @param keyMonitoringIndustryPleaseSelectForOther keyMonitoringIndustryPleaseSelectForOther
     */
    public void setKeyMonitoringIndustryPleaseSelectForOther(CommonNodeEntity keyMonitoringIndustryPleaseSelectForOther) {
        this.keyMonitoringIndustryPleaseSelectForOther = keyMonitoringIndustryPleaseSelectForOther;
    }

    /**
     * 获取 keyMonitoringIndustryTypeForOther
     *
     * @return keyMonitoringIndustryTypeForOther.
     */
    public CommonNodeEntity getKeyMonitoringIndustryTypeForOther() {
        return keyMonitoringIndustryTypeForOther;
    }

    /**
     * 设置 keyMonitoringIndustryTypeForOther
     *
     * @param keyMonitoringIndustryTypeForOther keyMonitoringIndustryTypeForOther
     */
    public void setKeyMonitoringIndustryTypeForOther(CommonNodeEntity keyMonitoringIndustryTypeForOther) {
        this.keyMonitoringIndustryTypeForOther = keyMonitoringIndustryTypeForOther;
    }

    /**
     * 获取 detailsOnKeyMonitoringIndustryTypeForOther
     *
     * @return detailsOnKeyMonitoringIndustryTypeForOther.
     */
    public CommonNodeEntity getDetailsOnKeyMonitoringIndustryTypeForOther() {
        return detailsOnKeyMonitoringIndustryTypeForOther;
    }

    /**
     * 设置 detailsOnKeyMonitoringIndustryTypeForOther
     *
     * @param detailsOnKeyMonitoringIndustryTypeForOther detailsOnKeyMonitoringIndustryTypeForOther
     */
    public void setDetailsOnKeyMonitoringIndustryTypeForOther(CommonNodeEntity detailsOnKeyMonitoringIndustryTypeForOther) {
        this.detailsOnKeyMonitoringIndustryTypeForOther = detailsOnKeyMonitoringIndustryTypeForOther;
    }

    /**
     * 获取 standardizedInternalAssetRatingForOther
     *
     * @return standardizedInternalAssetRatingForOther.
     */
    public CommonNodeEntity getStandardizedInternalAssetRatingForOther() {
        return standardizedInternalAssetRatingForOther;
    }

    /**
     * 设置 standardizedInternalAssetRatingForOther
     *
     * @param standardizedInternalAssetRatingForOther standardizedInternalAssetRatingForOther
     */
    public void setStandardizedInternalAssetRatingForOther(CommonNodeEntity standardizedInternalAssetRatingForOther) {
        this.standardizedInternalAssetRatingForOther = standardizedInternalAssetRatingForOther;
    }

    /**
     * 获取 transactionRegistrationCode
     *
     * @return transactionRegistrationCode.
     */
    public CommonNodeEntity getTransactionRegistrationCode() {
        return transactionRegistrationCode;
    }

    /**
     * 设置 transactionRegistrationCode
     *
     * @param transactionRegistrationCode transactionRegistrationCode
     */
    public void setTransactionRegistrationCode(CommonNodeEntity transactionRegistrationCode) {
        this.transactionRegistrationCode = transactionRegistrationCode;
    }

    /**
     * 获取 cashflowType
     *
     * @return cashflowType.
     */
    public CommonNodeEntity getCashflowType() {
        return cashflowType;
    }

    /**
     * 设置 cashflowType
     *
     * @param cashflowType cashflowType
     */
    public void setCashflowType(CommonNodeEntity cashflowType) {
        this.cashflowType = cashflowType;
    }

    /**
     * 获取 detailsOnCashflowType
     *
     * @return detailsOnCashflowType.
     */
    public CommonNodeEntity getDetailsOnCashflowType() {
        return detailsOnCashflowType;
    }

    /**
     * 设置 detailsOnCashflowType
     *
     * @param detailsOnCashflowType detailsOnCashflowType
     */
    public void setDetailsOnCashflowType(CommonNodeEntity detailsOnCashflowType) {
        this.detailsOnCashflowType = detailsOnCashflowType;
    }

    /**
     * 获取 quantity
     *
     * @return quantity.
     */
    public CommonNodeEntity getQuantity() {
        return quantity;
    }

    /**
     * 设置 quantity
     *
     * @param quantity quantity
     */
    public void setQuantity(CommonNodeEntity quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取 settlementAmountCNYForTransaction
     *
     * @return settlementAmountCNYForTransaction.
     */
    public CommonNodeEntity getSettlementAmountCNYForTransaction() {
        return settlementAmountCNYForTransaction;
    }

    /**
     * 设置 settlementAmountCNYForTransaction
     *
     * @param settlementAmountCNYForTransaction settlementAmountCNYForTransaction
     */
    public void setSettlementAmountCNYForTransaction(CommonNodeEntity settlementAmountCNYForTransaction) {
        this.settlementAmountCNYForTransaction = settlementAmountCNYForTransaction;
    }

    /**
     * 获取 settlementAmount
     *
     * @return settlementAmount.
     */
    public CommonNodeEntity getSettlementAmount() {
        return settlementAmount;
    }

    /**
     * 设置 settlementAmount
     *
     * @param settlementAmount settlementAmount
     */
    public void setSettlementAmount(CommonNodeEntity settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    /**
     * 获取 perUnitTransactionPriceNet
     *
     * @return perUnitTransactionPriceNet.
     */
    public CommonNodeEntity getPerUnitTransactionPriceNet() {
        return perUnitTransactionPriceNet;
    }

    /**
     * 设置 perUnitTransactionPriceNet
     *
     * @param perUnitTransactionPriceNet perUnitTransactionPriceNet
     */
    public void setPerUnitTransactionPriceNet(CommonNodeEntity perUnitTransactionPriceNet) {
        this.perUnitTransactionPriceNet = perUnitTransactionPriceNet;
    }

    /**
     * 获取 perUnitTransactionPriceFull
     *
     * @return perUnitTransactionPriceFull.
     */
    public CommonNodeEntity getPerUnitTransactionPriceFull() {
        return perUnitTransactionPriceFull;
    }

    /**
     * 设置 perUnitTransactionPriceFull
     *
     * @param perUnitTransactionPriceFull perUnitTransactionPriceFull
     */
    public void setPerUnitTransactionPriceFull(CommonNodeEntity perUnitTransactionPriceFull) {
        this.perUnitTransactionPriceFull = perUnitTransactionPriceFull;
    }

    /**
     * 获取 rateOfAnnualReturn
     *
     * @return rateOfAnnualReturn.
     */
    public CommonNodeEntity getRateOfAnnualReturn() {
        return rateOfAnnualReturn;
    }

    /**
     * 设置 rateOfAnnualReturn
     *
     * @param rateOfAnnualReturn rateOfAnnualReturn
     */
    public void setRateOfAnnualReturn(CommonNodeEntity rateOfAnnualReturn) {
        this.rateOfAnnualReturn = rateOfAnnualReturn;
    }

    /**
     * 获取 tradeCounterparty
     *
     * @return tradeCounterparty.
     */
    public CommonNodeEntity getTradeCounterparty() {
        return tradeCounterparty;
    }

    /**
     * 设置 tradeCounterparty
     *
     * @param tradeCounterparty tradeCounterparty
     */
    public void setTradeCounterparty(CommonNodeEntity tradeCounterparty) {
        this.tradeCounterparty = tradeCounterparty;
    }

    /**
     * 获取 transactionIdentificationCodeContractNumber
     *
     * @return transactionIdentificationCodeContractNumber.
     */
    public CommonNodeEntity getTransactionIdentificationCodeContractNumber() {
        return transactionIdentificationCodeContractNumber;
    }

    /**
     * 设置 transactionIdentificationCodeContractNumber
     *
     * @param transactionIdentificationCodeContractNumber
     *            transactionIdentificationCodeContractNumber
     */
    public void setTransactionIdentificationCodeContractNumber(CommonNodeEntity transactionIdentificationCodeContractNumber) {
        this.transactionIdentificationCodeContractNumber = transactionIdentificationCodeContractNumber;
    }

    /**
     * 获取 transactionRegistrationDetails
     *
     * @return transactionRegistrationDetails.
     */
    public CommonNodeEntity getTransactionRegistrationDetails() {
        return transactionRegistrationDetails;
    }

    /**
     * 设置 transactionRegistrationDetails
     *
     * @param transactionRegistrationDetails transactionRegistrationDetails
     */
    public void setTransactionRegistrationDetails(CommonNodeEntity transactionRegistrationDetails) {
        this.transactionRegistrationDetails = transactionRegistrationDetails;
    }

    /**
     * 获取 tradeDate
     *
     * @return tradeDate.
     */
    public CommonNodeEntity getTradeDate() {
        return tradeDate;
    }

    /**
     * 设置 tradeDate
     *
     * @param tradeDate tradeDate
     */
    public void setTradeDate(CommonNodeEntity tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * 获取 valuationDate
     *
     * @return valuationDate.
     */
    public CommonNodeEntity getValuationDate() {
        return valuationDate;
    }

    /**
     * 设置 valuationDate
     *
     * @param valuationDate valuationDate
     */
    public void setValuationDate(CommonNodeEntity valuationDate) {
        this.valuationDate = valuationDate;
    }

    /**
     * 获取 perUnitValuationNet
     *
     * @return perUnitValuationNet.
     */
    public CommonNodeEntity getPerUnitValuationNet() {
        return perUnitValuationNet;
    }

    /**
     * 设置 perUnitValuationNet
     *
     * @param perUnitValuationNet perUnitValuationNet
     */
    public void setPerUnitValuationNet(CommonNodeEntity perUnitValuationNet) {
        this.perUnitValuationNet = perUnitValuationNet;
    }

    /**
     * 获取 perUnitValuationFull
     *
     * @return perUnitValuationFull.
     */
    public CommonNodeEntity getPerUnitValuationFull() {
        return perUnitValuationFull;
    }

    /**
     * 设置 perUnitValuationFull
     *
     * @param perUnitValuationFull perUnitValuationFull
     */
    public void setPerUnitValuationFull(CommonNodeEntity perUnitValuationFull) {
        this.perUnitValuationFull = perUnitValuationFull;
    }

    /**
     * 获取 valuationRegistrationDetails
     *
     * @return valuationRegistrationDetails.
     */
    public CommonNodeEntity getValuationRegistrationDetails() {
        return valuationRegistrationDetails;
    }

    /**
     * 设置 valuationRegistrationDetails
     *
     * @param valuationRegistrationDetails valuationRegistrationDetails
     */
    public void setValuationRegistrationDetails(CommonNodeEntity valuationRegistrationDetails) {
        this.valuationRegistrationDetails = valuationRegistrationDetails;
    }

    /**
     * 获取 assetsLiabilitiesIdentificationCodeOfAssetManagementPlan
     *
     * @return assetsLiabilitiesIdentificationCodeOfAssetManagementPlan.
     */
    public CommonNodeEntity getAssetsLiabilitiesIdentificationCodeOfAssetManagementPlan() {
        return assetsLiabilitiesIdentificationCodeOfAssetManagementPlan;
    }

    /**
     * 设置 assetsLiabilitiesIdentificationCodeOfAssetManagementPlan
     *
     * @param assetsLiabilitiesIdentificationCodeOfAssetManagementPlan
     *            assetsLiabilitiesIdentificationCodeOfAssetManagementPlan
     */
    public void setAssetsLiabilitiesIdentificationCodeOfAssetManagementPlan(CommonNodeEntity assetsLiabilitiesIdentificationCodeOfAssetManagementPlan) {
        this.assetsLiabilitiesIdentificationCodeOfAssetManagementPlan = assetsLiabilitiesIdentificationCodeOfAssetManagementPlan;
    }

    /**
     * 获取 aggregateIssuedQuantityOfAssetManagementPlan
     *
     * @return aggregateIssuedQuantityOfAssetManagementPlan.
     */
    public CommonNodeEntity getAggregateIssuedQuantityOfAssetManagementPlan() {
        return aggregateIssuedQuantityOfAssetManagementPlan;
    }

    /**
     * 设置 aggregateIssuedQuantityOfAssetManagementPlan
     *
     * @param aggregateIssuedQuantityOfAssetManagementPlan
     *            aggregateIssuedQuantityOfAssetManagementPlan
     */
    public void setAggregateIssuedQuantityOfAssetManagementPlan(CommonNodeEntity aggregateIssuedQuantityOfAssetManagementPlan) {
        this.aggregateIssuedQuantityOfAssetManagementPlan = aggregateIssuedQuantityOfAssetManagementPlan;
    }

    /**
     * 获取 aggregateIssuedAmountOfAssetManagementPlanCNY
     *
     * @return aggregateIssuedAmountOfAssetManagementPlanCNY.
     */
    public CommonNodeEntity getAggregateIssuedAmountOfAssetManagementPlanCNY() {
        return aggregateIssuedAmountOfAssetManagementPlanCNY;
    }

    /**
     * 设置 aggregateIssuedAmountOfAssetManagementPlanCNY
     *
     * @param aggregateIssuedAmountOfAssetManagementPlanCNY
     *            aggregateIssuedAmountOfAssetManagementPlanCNY
     */
    public void setAggregateIssuedAmountOfAssetManagementPlanCNY(CommonNodeEntity aggregateIssuedAmountOfAssetManagementPlanCNY) {
        this.aggregateIssuedAmountOfAssetManagementPlanCNY = aggregateIssuedAmountOfAssetManagementPlanCNY;
    }

    /**
     * 获取 nonInvestedAmountofAssetManagementPlanCNY
     *
     * @return nonInvestedAmountofAssetManagementPlanCNY.
     */
    public CommonNodeEntity getNonInvestedAmountofAssetManagementPlanCNY() {
        return nonInvestedAmountofAssetManagementPlanCNY;
    }

    /**
     * 设置 nonInvestedAmountofAssetManagementPlanCNY
     *
     * @param nonInvestedAmountofAssetManagementPlanCNY nonInvestedAmountofAssetManagementPlanCNY
     */
    public void setNonInvestedAmountofAssetManagementPlanCNY(CommonNodeEntity nonInvestedAmountofAssetManagementPlanCNY) {
        this.nonInvestedAmountofAssetManagementPlanCNY = nonInvestedAmountofAssetManagementPlanCNY;
    }

    /**
     * 获取 underlyingAssetTuple
     *
     * @return underlyingAssetTuple.
     */
    public List<CommonNodeListEntity> getUnderlyingAssetTuple() {
        return underlyingAssetTuple;
    }

    /**
     * 设置 underlyingAssetTuple
     *
     * @param underlyingAssetTuple underlyingAssetTuple
     */
    public void setUnderlyingAssetTuple(List<CommonNodeListEntity> underlyingAssetTuple) {
        this.underlyingAssetTuple = underlyingAssetTuple;
    }

    /**
     * 获取 profession
     *
     * @return profession.
     */
    public CommonNodeEntity getProfession() {
        return profession;
    }

    /**
     * 设置 profession
     *
     * @param profession profession
     */
    public void setProfession(CommonNodeEntity profession) {
        this.profession = profession;
    }

    /**
     * 获取 name
     *
     * @return name.
     */
    public CommonNodeEntity getName() {
        return name;
    }

    /**
     * 设置 name
     *
     * @param name name
     */
    public void setName(CommonNodeEntity name) {
        this.name = name;
    }

    /**
     * 获取 gender
     *
     * @return gender.
     */
    public CommonNodeEntity getGender() {
        return gender;
    }

    /**
     * 设置 gender
     *
     * @param gender gender
     */
    public void setGender(CommonNodeEntity gender) {
        this.gender = gender;
    }

    /**
     * 获取 practitionerIdNumber
     *
     * @return practitionerIdNumber.
     */
    public CommonNodeEntity getPractitionerIdNumber() {
        return practitionerIdNumber;
    }

    /**
     * 设置 practitionerIdNumber
     *
     * @param practitionerIdNumber practitionerIdNumber
     */
    public void setPractitionerIdNumber(CommonNodeEntity practitionerIdNumber) {
        this.practitionerIdNumber = practitionerIdNumber;
    }

    /**
     * 获取 headquartersBranchOfficePleaseSelect
     *
     * @return headquartersBranchOfficePleaseSelect.
     */
    public CommonNodeEntity getHeadquartersBranchOfficePleaseSelect() {
        return headquartersBranchOfficePleaseSelect;
    }

    /**
     * 设置 headquartersBranchOfficePleaseSelect
     *
     * @param headquartersBranchOfficePleaseSelect headquartersBranchOfficePleaseSelect
     */
    public void setHeadquartersBranchOfficePleaseSelect(CommonNodeEntity headquartersBranchOfficePleaseSelect) {
        this.headquartersBranchOfficePleaseSelect = headquartersBranchOfficePleaseSelect;
    }

    /**
     * 获取 region
     *
     * @return region.
     */
    public CommonNodeEntity getRegion() {
        return region;
    }

    /**
     * 设置 region
     *
     * @param region region
     */
    public void setRegion(CommonNodeEntity region) {
        this.region = region;
    }

    /**
     * 获取 firmName
     *
     * @return firmName.
     */
    public CommonNodeEntity getFirmName() {
        return firmName;
    }

    /**
     * 设置 firmName
     *
     * @param firmName firmName
     */
    public void setFirmName(CommonNodeEntity firmName) {
        this.firmName = firmName;
    }

    /**
     * 获取 department
     *
     * @return department.
     */
    public CommonNodeEntity getDepartment() {
        return department;
    }

    /**
     * 设置 department
     *
     * @param department department
     */
    public void setDepartment(CommonNodeEntity department) {
        this.department = department;
    }

    /**
     * 获取 position
     *
     * @return position.
     */
    public CommonNodeEntity getPosition() {
        return position;
    }

    /**
     * 设置 position
     *
     * @param position position
     */
    public void setPosition(CommonNodeEntity position) {
        this.position = position;
    }

    /**
     * 获取 education
     *
     * @return education.
     */
    public CommonNodeEntity getEducation() {
        return education;
    }

    /**
     * 设置 education
     *
     * @param education education
     */
    public void setEducation(CommonNodeEntity education) {
        this.education = education;
    }

    /**
     * 获取 degree
     *
     * @return degree.
     */
    public CommonNodeEntity getDegree() {
        return degree;
    }

    /**
     * 设置 degree
     *
     * @param degree degree
     */
    public void setDegree(CommonNodeEntity degree) {
        this.degree = degree;
    }

    /**
     * 获取 careerStartDate
     *
     * @return careerStartDate.
     */
    public CommonNodeEntity getCareerStartDate() {
        return careerStartDate;
    }

    /**
     * 设置 careerStartDate
     *
     * @param careerStartDate careerStartDate
     */
    public void setCareerStartDate(CommonNodeEntity careerStartDate) {
        this.careerStartDate = careerStartDate;
    }

    /**
     * 获取 startDateOfWorkingExperienceInWealthManagement
     *
     * @return startDateOfWorkingExperienceInWealthManagement.
     */
    public CommonNodeEntity getStartDateOfWorkingExperienceInWealthManagement() {
        return startDateOfWorkingExperienceInWealthManagement;
    }

    /**
     * 设置 startDateOfWorkingExperienceInWealthManagement
     *
     * @param startDateOfWorkingExperienceInWealthManagement
     *            startDateOfWorkingExperienceInWealthManagement
     */
    public void setStartDateOfWorkingExperienceInWealthManagement(CommonNodeEntity startDateOfWorkingExperienceInWealthManagement) {
        this.startDateOfWorkingExperienceInWealthManagement = startDateOfWorkingExperienceInWealthManagement;
    }

    /**
     * 获取 levelofProfessionalQualifications
     *
     * @return levelofProfessionalQualifications.
     */
    public CommonNodeEntity getLevelofProfessionalQualifications() {
        return levelofProfessionalQualifications;
    }

    /**
     * 设置 levelofProfessionalQualifications
     *
     * @param levelofProfessionalQualifications levelofProfessionalQualifications
     */
    public void setLevelofProfessionalQualifications(CommonNodeEntity levelofProfessionalQualifications) {
        this.levelofProfessionalQualifications = levelofProfessionalQualifications;
    }

    /**
     * 获取 wealthManagementCertificationTuple
     *
     * @return wealthManagementCertificationTuple.
     */
    public List<CommonNodeListEntity> getWealthManagementCertificationTuple() {
        return wealthManagementCertificationTuple;
    }

    /**
     * 设置 wealthManagementCertificationTuple
     *
     * @param wealthManagementCertificationTuple wealthManagementCertificationTuple
     */
    public void setWealthManagementCertificationTuple(List<CommonNodeListEntity> wealthManagementCertificationTuple) {
        this.wealthManagementCertificationTuple = wealthManagementCertificationTuple;
    }

    /**
     * 获取 rewardsInWealthManagement
     *
     * @return rewardsInWealthManagement.
     */
    public CommonNodeEntity getRewardsInWealthManagement() {
        return rewardsInWealthManagement;
    }

    /**
     * 设置 rewardsInWealthManagement
     *
     * @param rewardsInWealthManagement rewardsInWealthManagement
     */
    public void setRewardsInWealthManagement(CommonNodeEntity rewardsInWealthManagement) {
        this.rewardsInWealthManagement = rewardsInWealthManagement;
    }

    /**
     * 获取 workPhoneNumberForPractitioner
     *
     * @return workPhoneNumberForPractitioner.
     */
    public CommonNodeEntity getWorkPhoneNumberForPractitioner() {
        return workPhoneNumberForPractitioner;
    }

    /**
     * 设置 workPhoneNumberForPractitioner
     *
     * @param workPhoneNumberForPractitioner workPhoneNumberForPractitioner
     */
    public void setWorkPhoneNumberForPractitioner(CommonNodeEntity workPhoneNumberForPractitioner) {
        this.workPhoneNumberForPractitioner = workPhoneNumberForPractitioner;
    }

    /**
     * 获取 mobilePhoneNumberForPractitioner
     *
     * @return mobilePhoneNumberForPractitioner.
     */
    public CommonNodeEntity getMobilePhoneNumberForPractitioner() {
        return mobilePhoneNumberForPractitioner;
    }

    /**
     * 设置 mobilePhoneNumberForPractitioner
     *
     * @param mobilePhoneNumberForPractitioner mobilePhoneNumberForPractitioner
     */
    public void setMobilePhoneNumberForPractitioner(CommonNodeEntity mobilePhoneNumberForPractitioner) {
        this.mobilePhoneNumberForPractitioner = mobilePhoneNumberForPractitioner;
    }

    /**
     * 获取 email
     *
     * @return email.
     */
    public CommonNodeEntity getEmail() {
        return email;
    }

    /**
     * 设置 email
     *
     * @param email email
     */
    public void setEmail(CommonNodeEntity email) {
        this.email = email;
    }

    /**
     * 获取 practitionerRegistrationDetails
     *
     * @return practitionerRegistrationDetails.
     */
    public CommonNodeEntity getPractitionerRegistrationDetails() {
        return practitionerRegistrationDetails;
    }

    /**
     * 设置 practitionerRegistrationDetails
     *
     * @param practitionerRegistrationDetails practitionerRegistrationDetails
     */
    public void setPractitionerRegistrationDetails(CommonNodeEntity practitionerRegistrationDetails) {
        this.practitionerRegistrationDetails = practitionerRegistrationDetails;
    }

    /**
     * 获取 reportDate
     *
     * @return reportDate.
     */
    public CommonNodeEntity getReportDate() {
        return reportDate;
    }

    /**
     * 设置 reportDate
     *
     * @param reportDate reportDate
     */
    public void setReportDate(CommonNodeEntity reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * 获取 investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     *
     * @return investorsSummationOfDiffenrentTypesInformationRegistrationTuple.
     */
    public List<BaseInfoEntity> getInvestorsSummationOfDiffenrentTypesInformationRegistrationTuple() {
        return investorsSummationOfDiffenrentTypesInformationRegistrationTuple;
    }

    /**
     * 设置 investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     *
     * @param investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     *            investorsSummationOfDiffenrentTypesInformationRegistrationTuple
     */
    public void setInvestorsSummationOfDiffenrentTypesInformationRegistrationTuple(List<BaseInfoEntity> investorsSummationOfDiffenrentTypesInformationRegistrationTuple) {
        this.investorsSummationOfDiffenrentTypesInformationRegistrationTuple = investorsSummationOfDiffenrentTypesInformationRegistrationTuple;
    }

    /**
     * 获取 investorsInformationRegistrationCSVDataExplanatory
     *
     * @return investorsInformationRegistrationCSVDataExplanatory.
     */
    public CommonNodeEntity getInvestorsInformationRegistrationCSVDataExplanatory() {
        return investorsInformationRegistrationCSVDataExplanatory;
    }

    /**
     * 设置 investorsInformationRegistrationCSVDataExplanatory
     *
     * @param investorsInformationRegistrationCSVDataExplanatory
     *            investorsInformationRegistrationCSVDataExplanatory
     */
    public void setInvestorsInformationRegistrationCSVDataExplanatory(CommonNodeEntity investorsInformationRegistrationCSVDataExplanatory) {
        this.investorsInformationRegistrationCSVDataExplanatory = investorsInformationRegistrationCSVDataExplanatory;
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
     * 获取 productRegistrationCode
     *
     * @return productRegistrationCode.
     */
    public CommonNodeEntity getProductRegistrationCode() {
        return productRegistrationCode;
    }

    /**
     * 设置 productRegistrationCode
     *
     * @param productRegistrationCode productRegistrationCode
     */
    public void setProductRegistrationCode(CommonNodeEntity productRegistrationCode) {
        this.productRegistrationCode = productRegistrationCode;
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
     * 获取 investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     *
     * @return investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple.
     */
    public List<CommonNodeListEntity> getInvestorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple() {
        return investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple;
    }

    /**
     * 设置 investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     *
     * @param investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     *            investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple
     */
    public void setInvestorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple(List<CommonNodeListEntity> investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple) {
        this.investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple = investorsSummationOfDiffenrentTypesInformationRegistrationDetailTuple;
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
     * 获取 financialInstitutionExclusivePleaseSelect
     *
     * @return financialInstitutionExclusivePleaseSelect.
     */
    public CommonNodeEntity getFinancialInstitutionExclusivePleaseSelect() {
        return financialInstitutionExclusivePleaseSelect;
    }
    /**
     * 设置 financialInstitutionExclusivePleaseSelect
     *
     * @param financialInstitutionExclusivePleaseSelect financialInstitutionExclusivePleaseSelect
     */
    public void setFinancialInstitutionExclusivePleaseSelect(CommonNodeEntity financialInstitutionExclusivePleaseSelect) {
        this.financialInstitutionExclusivePleaseSelect = financialInstitutionExclusivePleaseSelect;
    }

    public CommonNodeEntity getTypeOfSubscription() {
        return typeOfSubscription;
    }

    public void setTypeOfSubscription(CommonNodeEntity typeOfSubscription) {
        this.typeOfSubscription = typeOfSubscription;
    }

    public CommonNodeEntity getTypeOfInvestmentAsset() {
        return typeOfInvestmentAsset;
    }

    public void setTypeOfInvestmentAsset(CommonNodeEntity typeOfInvestmentAsset) {
        this.typeOfInvestmentAsset = typeOfInvestmentAsset;
    }

    public CommonNodeEntity getPerformanceBenchmarkRate() {
        return performanceBenchmarkRate;
    }

    public void setPerformanceBenchmarkRate(CommonNodeEntity performanceBenchmarkRate) {
        this.performanceBenchmarkRate = performanceBenchmarkRate;
    }

    public CommonNodeEntity getMinimumHoldingPeriodPleaseSelect() {
        return minimumHoldingPeriodPleaseSelect;
    }

    public void setMinimumHoldingPeriodPleaseSelect(CommonNodeEntity minimumHoldingPeriodPleaseSelect) {
        this.minimumHoldingPeriodPleaseSelect = minimumHoldingPeriodPleaseSelect;
    }

    public CommonNodeEntity getMinimumHoldingPeriodDays() {
        return minimumHoldingPeriodDays;
    }

    public void setMinimumHoldingPeriodDays(CommonNodeEntity minimumHoldingPeriodDays) {
        this.minimumHoldingPeriodDays = minimumHoldingPeriodDays;
    }

    public CommonNodeEntity getOptionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect() {
        return optionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect;
    }

    public void setOptionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect(
            CommonNodeEntity optionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect) {
        this.optionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect = optionOfRedemptionAfterMinimumHoldingPeriodPleaseSelect;
    }

    public CommonNodeEntity getCashManagementPleaseSelect() {
        return cashManagementPleaseSelect;
    }

    public void setCashManagementPleaseSelect(CommonNodeEntity cashManagementPleaseSelect) {
        this.cashManagementPleaseSelect = cashManagementPleaseSelect;
    }

    /**
     * 获取 fundingCurrencyTuple
     *
     * @return fundingCurrencyTuple.
     */
    public List<CommonNodeListEntity> getFundingCurrencyTuple() {
        return fundingCurrencyTuple;
    }

    /**
     * 设置 fundingCurrencyTuple
     *
     * @param fundingCurrencyTuple fundingCurrencyTuple
     */
    public void setFundingCurrencyTuple(List<CommonNodeListEntity> fundingCurrencyTuple) {
        this.fundingCurrencyTuple = fundingCurrencyTuple;
    }
    /**
     * 获取 PrincipalPaymentCurrencyTuple
     *
     * @return PrincipalPaymentCurrencyTuple.
     */
    public List<CommonNodeListEntity> getPrincipalPaymentCurrencyTuple() {
        return principalPaymentCurrencyTuple;
    }

    /**
     * 设置 PrincipalPaymentCurrencyTuple
     *
     * @param principalPaymentCurrencyTuple PrincipalPaymentCurrencyTuple
     */
    public void setPrincipalPaymentCurrencyTuple(List<CommonNodeListEntity> principalPaymentCurrencyTuple) {
        this.principalPaymentCurrencyTuple = principalPaymentCurrencyTuple;
    }

    public CommonNodeEntity getDomesticCustodianNameOfPublicOrPrivateProduct() {
        return domesticCustodianNameOfPublicOrPrivateProduct;
    }

    public void setDomesticCustodianNameOfPublicOrPrivateProduct(
            CommonNodeEntity domesticCustodianNameOfPublicOrPrivateProduct) {
        this.domesticCustodianNameOfPublicOrPrivateProduct = domesticCustodianNameOfPublicOrPrivateProduct;
    }

    public CommonNodeEntity getInvestmentManagementFeeRate() {
        return investmentManagementFeeRate;
    }

    public void setInvestmentManagementFeeRate(CommonNodeEntity investmentManagementFeeRate) {
        this.investmentManagementFeeRate = investmentManagementFeeRate;
    }

    public CommonNodeEntity getManagementMethod() {
        return managementMethod;
    }

    public void setManagementMethod(CommonNodeEntity managementMethod) {
        this.managementMethod = managementMethod;
    }

    public CommonNodeEntity getUpperLimitOfPerformanceBenchmarkRate() {
        return upperLimitOfPerformanceBenchmarkRate;
    }

    public void setUpperLimitOfPerformanceBenchmarkRate(CommonNodeEntity upperLimitOfPerformanceBenchmarkRate) {
        this.upperLimitOfPerformanceBenchmarkRate = upperLimitOfPerformanceBenchmarkRate;
    }

    public CommonNodeEntity getLowerLimitOfPerformanceBenchmarkRate() {
        return lowerLimitOfPerformanceBenchmarkRate;
    }

    public void setLowerLimitOfPerformanceBenchmarkRate(CommonNodeEntity lowerLimitOfPerformanceBenchmarkRate) {
        this.lowerLimitOfPerformanceBenchmarkRate = lowerLimitOfPerformanceBenchmarkRate;
    }

    public CommonNodeEntity getFundCustodyAccountNumber() {
        return fundCustodyAccountNumber;
    }

    public void setFundCustodyAccountNumber(CommonNodeEntity fundCustodyAccountNumber) {
        this.fundCustodyAccountNumber = fundCustodyAccountNumber;
    }

    public CommonNodeEntity getFundCustodyAccountName() {
        return fundCustodyAccountName;
    }

    public void setFundCustodyAccountName(CommonNodeEntity fundCustodyAccountName) {
        this.fundCustodyAccountName = fundCustodyAccountName;
    }

    public CommonNodeEntity getProductOutstandingAmountCNY() {
        return productOutstandingAmountCNY;
    }

    public void setProductOutstandingAmountCNY(CommonNodeEntity productOutstandingAmountCNY) {
        this.productOutstandingAmountCNY = productOutstandingAmountCNY;
    }

    public CommonNodeEntity getAssetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset() {
        return assetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset;
    }

    public void setAssetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset(
            CommonNodeEntity assetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset) {
        this.assetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset = assetsLiabilitiesIdentificationCodeOfAssetManagementProductOrOutsourcingAsset;
    }

    public CommonNodeEntity getAggregateQuantityOfAssetManagementProductOrOutsourcingAsset() {
        return aggregateQuantityOfAssetManagementProductOrOutsourcingAsset;
    }

    public void setAggregateQuantityOfAssetManagementProductOrOutsourcingAsset(
            CommonNodeEntity aggregateQuantityOfAssetManagementProductOrOutsourcingAsset) {
        this.aggregateQuantityOfAssetManagementProductOrOutsourcingAsset = aggregateQuantityOfAssetManagementProductOrOutsourcingAsset;
    }

    public CommonNodeEntity getAggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY() {
        return aggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY;
    }

    public void setAggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY(
            CommonNodeEntity aggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY) {
        this.aggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY = aggregateIssuedAmountOfAssetManagementProductOrOutsourcingAssetCNY;
    }

    public CommonNodeEntity getNonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY() {
        return nonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY;
    }

    public void setNonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY(
            CommonNodeEntity nonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY) {
        this.nonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY = nonInvestedAmountOfAssetManagementProductOrOutsourcingAssetCNY;
    }

    public CommonNodeEntity getRealizedNetIncomeOfBankCNY() {
        return realizedNetIncomeOfBankCNY;
    }

    public void setRealizedNetIncomeOfBankCNY(CommonNodeEntity realizedNetIncomeOfBankCNY) {
        this.realizedNetIncomeOfBankCNY = realizedNetIncomeOfBankCNY;
    }

    public CommonNodeEntity getDepositsType() {
        return depositsType;
    }

    public void setDepositsType(CommonNodeEntity depositsType) {
        this.depositsType = depositsType;
    }

    public CommonNodeEntity getFilingRegistrationElementsOfPublicOrPrivateProductDetails() {
        return filingRegistrationElementsOfPublicOrPrivateProductDetails;
    }

    public void setFilingRegistrationElementsOfPublicOrPrivateProductDetails(
            CommonNodeEntity filingRegistrationElementsOfPublicOrPrivateProductDetails) {
        this.filingRegistrationElementsOfPublicOrPrivateProductDetails = filingRegistrationElementsOfPublicOrPrivateProductDetails;
    }

    public CommonNodeEntity getDetailsOnPerformanceBenchmarkRate() {
        return detailsOnPerformanceBenchmarkRate;
    }

    public void setDetailsOnPerformanceBenchmarkRate(CommonNodeEntity detailsOnPerformanceBenchmarkRate) {
        this.detailsOnPerformanceBenchmarkRate = detailsOnPerformanceBenchmarkRate;
    }

    public CommonNodeEntity getUnitsBonusPerTenThousandUnits() {
        return unitsBonusPerTenThousandUnits;
    }

    public void setUnitsBonusPerTenThousandUnits(CommonNodeEntity unitsBonusPerTenThousandUnits) {
        this.unitsBonusPerTenThousandUnits = unitsBonusPerTenThousandUnits;
    }

    public CommonNodeEntity getCashBonusPerTenThousandUnitsCNY() {
        return cashBonusPerTenThousandUnitsCNY;
    }

    public void setCashBonusPerTenThousandUnitsCNY(CommonNodeEntity cashBonusPerTenThousandUnitsCNY) {
        this.cashBonusPerTenThousandUnitsCNY = cashBonusPerTenThousandUnitsCNY;
    }

    public CommonNodeEntity getWealthManagementRegistrationTrainingCertificateNumber() {
        return wealthManagementRegistrationTrainingCertificateNumber;
    }

    public void setWealthManagementRegistrationTrainingCertificateNumber(
            CommonNodeEntity wealthManagementRegistrationTrainingCertificateNumber) {
        this.wealthManagementRegistrationTrainingCertificateNumber = wealthManagementRegistrationTrainingCertificateNumber;
    }

    public List<CommonNodeListEntity> getRegistrationClassificationTuple() {
        return registrationClassificationTuple;
    }

    public void setRegistrationClassificationTuple(List<CommonNodeListEntity> registrationClassificationTuple) {
        this.registrationClassificationTuple = registrationClassificationTuple;
    }

    public List<CommonNodeListEntity> getTypeOfRegistrarTuple() {
        return typeOfRegistrarTuple;
    }

    public void setTypeOfRegistrarTuple(List<CommonNodeListEntity> typeOfRegistrarTuple) {
        this.typeOfRegistrarTuple = typeOfRegistrarTuple;
    }

    public CommonNodeEntity getNewProductPleaseSelect() {
        return newProductPleaseSelect;
    }

    public void setNewProductPleaseSelect(CommonNodeEntity newProductPleaseSelect) {
        this.newProductPleaseSelect = newProductPleaseSelect;
    }

    public CommonNodeEntity getDepositAccountNumber() {
        return depositAccountNumber;
    }

    public void setDepositAccountNumber(CommonNodeEntity depositAccountNumber) {
        this.depositAccountNumber = depositAccountNumber;
    }

    public CommonNodeEntity getCreditAssetTradingCode() {
        return creditAssetTradingCode;
    }

    public void setCreditAssetTradingCode(CommonNodeEntity creditAssetTradingCode) {
        this.creditAssetTradingCode = creditAssetTradingCode;
    }

    public CommonNodeEntity getBillOrNoteCode() {
        return billOrNoteCode;
    }

    public void setBillOrNoteCode(CommonNodeEntity billOrNoteCode) {
        this.billOrNoteCode = billOrNoteCode;
    }

    public CommonNodeEntity getTransactionApproverIDNumber() {
        return transactionApproverIDNumber;
    }

    public void setTransactionApproverIDNumber(CommonNodeEntity transactionApproverIDNumber) {
        this.transactionApproverIDNumber = transactionApproverIDNumber;
    }

    public CommonNodeEntity getTransactionApproverName() {
        return transactionApproverName;
    }

    public void setTransactionApproverName(CommonNodeEntity transactionApproverName) {
        this.transactionApproverName = transactionApproverName;
    }

    public CommonNodeEntity getTraderIDNumber() {
        return traderIDNumber;
    }

    public void setTraderIDNumber(CommonNodeEntity traderIDNumber) {
        this.traderIDNumber = traderIDNumber;
    }

    public CommonNodeEntity getTraderName() {
        return traderName;
    }

    public void setTraderName(CommonNodeEntity traderName) {
        this.traderName = traderName;
    }

    public CommonNodeEntity getNetValueDate() {
        return netValueDate;
    }

    public void setNetValueDate(CommonNodeEntity netValueDate) {
        this.netValueDate = netValueDate;
    }

    public CommonNodeEntity getStandardizedBillOrNotePleaseSelect() {
        return standardizedBillOrNotePleaseSelect;
    }

    public void setStandardizedBillOrNotePleaseSelect(CommonNodeEntity standardizedBillOrNotePleaseSelect) {
        this.standardizedBillOrNotePleaseSelect = standardizedBillOrNotePleaseSelect;
    }

    public CommonNodeEntity getWealthManagementProductPleaseSelect() {
        return wealthManagementProductPleaseSelect;
    }

    public void setWealthManagementProductPleaseSelect(CommonNodeEntity wealthManagementProductPleaseSelect) {
        this.wealthManagementProductPleaseSelect = wealthManagementProductPleaseSelect;
    }

    public CommonNodeEntity getFixedIncomeFundPleaseSelect() {
        return fixedIncomeFundPleaseSelect;
    }

    public void setFixedIncomeFundPleaseSelect(CommonNodeEntity fixedIncomeFundPleaseSelect) {
        this.fixedIncomeFundPleaseSelect = fixedIncomeFundPleaseSelect;
    }

    public CommonNodeEntity getInterbankCashBorrowingPleaseSelect() {
        return interbankCashBorrowingPleaseSelect;
    }

    public void setInterbankCashBorrowingPleaseSelect(CommonNodeEntity interbankCashBorrowingPleaseSelect) {
        this.interbankCashBorrowingPleaseSelect = interbankCashBorrowingPleaseSelect;
    }

    public CommonNodeEntity getProductSpecialProperty() {
        return productSpecialProperty;
    }

    public void setProductSpecialProperty(CommonNodeEntity productSpecialProperty) {
        this.productSpecialProperty = productSpecialProperty;
    }

    public CommonNodeEntity getRatioOfPriority() {
        return ratioOfPriority;
    }

    public void setRatioOfPriority(CommonNodeEntity ratioOfPriority) {
        this.ratioOfPriority = ratioOfPriority;
    }

    public List<CommonNodeEntity> getProductSpecialPropertyTuple() {
        return productSpecialPropertyTuple;
    }

    public void setProductSpecialPropertyTuple(List<CommonNodeEntity> productSpecialPropertyTuple) {
        this.productSpecialPropertyTuple = productSpecialPropertyTuple;
    }
}
