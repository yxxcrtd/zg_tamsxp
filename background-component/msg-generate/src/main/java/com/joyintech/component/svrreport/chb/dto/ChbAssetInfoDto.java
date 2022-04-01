/*
 * 文件名：ChbAssetSubDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年9月24日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.component.svrreport.chb.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.joyintech.component.svrreport.chb.contant.annotate.RateFormat;

/**
 * 资产负债登记要素
 * @description 
 * @author 王波
 * @version 1.0 2020年9月24日
 * @see ChbAssetInfoDto
 * @since 1.0
 */
public class ChbAssetInfoDto {
	/**
	 * 行内资产负债编码
	 */
	private String assetCode;

	/**
	 * 资产/负债类别
	 */
	private String assetType;

	/**
	 * 资产/负债类别
	 */
	private String assetTypeName;

	/**
	 * 资产/负债二级类别
	 */
	private String assetType2;

	/**
	 * 资产/负债二级类别
	 */
	private String assetType2Name;

	/**
	 * 币种
	 */
	private String ccy;

	/**
	 * 币种
	 */
	private String ccyName;

	/**
	 * 交易流通场所
	 */
	private String tradeMarket;

	/**
	 * 交易流通场所
	 */
	private String tradeMarketName;

	/**
	 * 起息日
	 */
	private Date vdate;

	/**
	 * 到期日
	 */
	private Date mdate;

	/**
	 * 收益率/年利率
	 */
	@RateFormat
	private BigDecimal assetYield;

	/**
	 * 计息基础
	 */
	private String basis;

	/**
	 * 计息基础
	 */
	private String basisName;

	/**
	 * 金额
	 */
	private BigDecimal amt;

	/**
	 * 资金存入银行
	 */
	private String bankName;

	/**
	 * 是否为结构性存款
	 */
	private String isStruct;

	/**
	 * 是否为结构性存款
	 */
	private String isStructName;

	/**
	 * 结构性存款挂钩标的类别
	 */
	private String targetType;

	/**
	 * 结构性存款挂钩标的类别
	 */
	private String targetTypeName;

	/**
	 * 结构性存款挂钩标的
	 */
	private String target;

	/**
	 * 资产代码
	 */
	private String assetId;

	/**
	 * 资产名称
	 */
	private String assetName;

	/**
	 * 具体类别
	 */
	private String chbSectype;

	/**
	 * 具体类别
	 */
	private String chbSectypeName;

	/**
	 * 发行方式
	 */
	private String issueMode;

	/**
	 * 发行方式
	 */
	private String issueModeName;

	/**
	 * 发行机构主体信用评级
	 */
	private String partyGrade;

	/**
	 * 发行机构主体信用评级
	 */
	private String partyGradeName;

	/**
	 * 发行机构类型（按规模划分）
	 */
	private String partyType1;

	/**
	 * 发行机构类型（按规模划分）
	 */
	private String partyType1Name;

	/**
	 * 发行机构类型（按技术领域划分）
	 */
	private String partyType3;

	/**
	 * 发行机构类型（按技术领域划分）
	 */
	private String partyType3Name;

	/**
	 * 发行机构类型（按经济类型划分）
	 */
	private String partyType2;

	/**
	 * 发行机构类型（按技术领域划分）
	 */
	private String partyType2Name;

	/**
	 * 发行机构所属行业一级分类
	 */
	private String industry;

	/**
	 * 发行机构所属行业一级分类
	 */
	private String industryName;

	/**
	 * 发行机构所属行业二级分类
	 */
	private String industryLevel2;

	/**
	 * 发行机构所属行业二级分类
	 */
	private String industryLevel2Name;

	/**
	 * 债券主体评级
	 */
	private String secMainGrade;

	/**
	 * 债券主体评级
	 */
	private String secMainGradeName;

	/**
	 * 债券债项评级
	 */
	private String secCreditGrade;

	/**
	 * 债券债项评级
	 */
	private String secCreditGradeName;

	/**
	 * 债券信用评级
	 */
	private String secCreditGradeType;
	
	/**
	 * 债券信用评级
	 */
	private String secCreditGradeTypeName;

	/**
	 * 登记托管机构
	 */
	private String registOrg;

	/**
	 * 登记托管机构
	 */
	private String registOrgName;

	/**
	 * 登记托管机构说明
	 */
	private String registorgComment;

	/**
	 * 对手方
	 */
	private String rivalParty;

	/**
	 * 对手方
	 */
	private String rivalPartyName;

	/**
	 * 回购标的类别
	 */
	private String targetType1;

	/**
	 * 回购标的类别
	 */
	private String targetType1Name;

	/**
	 * 回购标的金额
	 */
	private BigDecimal targetAmt;

	/**
	 * 所属国家或地区
	 */
	private String countryArea;

	/**
	 * 所属国家或地区
	 */
	private String countryAreaName;

	/**
	 * 收/受益权类型
	 */
	private String profitType;

	/**
	 * 收/受益权类型
	 */
	private String profitTypeName;

	/**
	 * 是否属于买入返售
	 */
	private String isResale;

	/**
	 * 是否属于买入返售
	 */
	private String isResaleName;

	/**
	 * 期限（天）
	 */
	private Integer termDate;

	/**
	 * 是否有预期收益率
	 */
	private String rateFlag;

	/**
	 * 是否有预期收益率
	 */
	private String rateFlagName;

	/**
	 * 付息频率（个月/次）
	 */
	private Integer intpayCycle;

	/**
	 * 还本付息说明
	 */
	private String repayCmt;

	/**
	 * 基准利率种类
	 */
	private String basisType;

	/**
	 * 基准利率种类
	 */
	private String basisTypeName;

	/**
	 * 浮动因子
	 */
	@RateFormat
	private BigDecimal floatFactor;

	/**
	 * 利差
	 */
	@RateFormat
	private BigDecimal spreadRate;

	/**
	 * 融资人
	 */
	private String financie;

	/**
	 * 融资人组织机构代码
	 */
	private String finOrgCode;

	/**
	 * 融资人内部信用评级
	 */
	private String fcrGradeIn;

	/**
	 * 外部评级机构名称及融资人评级结果
	 */
	private String fcrGradeOut;

	/**
	 * 融资人类型（按规模划分）
	 */
	private String financierType1;

	/**
	 * 融资人类型（按规模划分）
	 */
	private String financierType1Name;

	/**
	 * 融资人类型（按技术领域划分）
	 */
	private String financierType2;

	/**
	 * 融资人类型（按技术领域划分）
	 */
	private String financierType2Name;

	/**
	 * 融资人类型（按经济类型划分）
	 */
	private String financierType3;

	/**
	 * 融资人类型（按技术领域划分）
	 */
	private String financierType3Name;

	/**
	 * 融资项目
	 */
	private String financeProj;

	/**
	 * 融资人所属行业一级分类
	 */
	private String industry1;

	/**
	 * 融资人所属行业一级分类
	 */
	private String industry1Name;

	/**
	 * 融资人所属行业二级分类
	 */
	private String industryLevel2Type;

	/**
	 * 融资人所属行业二级分类
	 */
	private String industryLevel2TypeName;

	/**
	 * 融资项目所属行业一级分类
	 */
	private String proIndustry;

	/**
	 * 融资项目所属行业一级分类
	 */
	private String proIndustryName;

	/**
	 * 融资项目所属行业二级分类
	 */
	private String proIndustryLevel2;

	/**
	 * 融资项目所属行业二级分类
	 */
	private String proIndustryLevel2Name;

	/**
	 * 项目是否属于重点监控行业和领域
	 */
	private String industryFlag;

	/**
	 * 项目是否属于重点监控行业和领域
	 */
	private String industryFlagName;

	/**
	 * 重点监控行业和领域类别
	 */
	private String monitType;

	/**
	 * 重点监控行业和领域类别
	 */
	private String monitTypeName;

	/**
	 * 重点监控行业和领域类别说明
	 */
	private String monTypeComm;

	/**
	 * 担保方式
	 */
	private String guaType;

	/**
	 * 担保方式
	 */
	private String guaTypeName;

	/**
	 * 担保方式说明
	 */
	private String guaTypeRemark;

	/**
	 * 融资人主体评级
	 */
	private String financierGrade;

	/**
	 * 融资人主体评级
	 */
	private String financierGradeName;

	/**
	 * 资产内部评级
	 */
	private String inGrade;

	/**
	 * 资产内部评级
	 */
	private String inGradeName;

	/**
	 * 资产外部评级
	 */
	private String outGrade;

	/**
	 * 资产外部评级
	 */
	private String outGradeName;

	/**
	 * 选择权
	 */
	private String chose;

	/**
	 * 行权条件说明
	 */
	private String conditionCmm;

	/**
	 * 融资人所属地区
	 */
	private String finArea;

	/**
	 * 融资人所属地区
	 */
	private String finAreaName;

	/**
	 * 融资总费率
	 */
	@RateFormat
	private BigDecimal totalFeeRate;

	/**
	 * 是否为收/受益权
	 */
	private String isRight;

	/**
	 * 是否为收/受益权
	 */
	private String isRightName;

	/**
	 * 非标准化债券类资产类型
	 */
	private String notstandSecType;

	/**
	 * 非标准化债券类资产类型
	 */
	private String notstandSecTypeName;

	/**
	 * 非标准化债券类资产数量
	 */
	private Integer notstandSecNum;

	/**
	 * 加权剩余期限(天)
	 */
	private BigDecimal remainTerm;

	/**
	 * 最长剩余期限(天)
	 */
	private Integer maxRemainTerm;

	/**
	 * 最短剩余期限(天)
	 */
	private Integer minRemainTerm;

	/**
	 * 股票类型
	 */
	private String sharesType;

	/**
	 * 股票类型
	 */
	private String sharesTypeName;

	/**
	 * 行业一级分类
	 */
	private String industryShares;

	/**
	 * 行业一级分类
	 */
	private String industrySharesName;

	/**
	 * 行业二级分类
	 */
	private String industryShares2;

	/**
	 * 行业二级分类
	 */
	private String industryShares2Name;

	/**
	 * 投资阶段
	 */
	private String investStage;

	/**
	 * 投资阶段
	 */
	private String investStageName;

	/**
	 * 投资企业类型（按规模划分）
	 */
	private String sharesType1;

	/**
	 * 投资企业类型（按规模划分）
	 */
	private String sharesType1Name;

	/**
	 * 投资企业类型（按经济类型划分）
	 */
	private String sharesType2;

	/**
	 * 投资企业类型（按经济类型划分）
	 */
	private String sharesType2Name;

	/**
	 * 投资企业类型（按技术领域划分）
	 */
	private String sharesType3;

	/**
	 * 投资企业类型（按技术领域划分）
	 */
	private String sharesType3Name;

	/**
	 * 是否为质押融资
	 */
	private String isPledge;

	/**
	 * 是否为质押融资
	 */
	private String isPledgeName;

	/**
	 * 名义本金
	 */
	private BigDecimal nomCapi;
	
	/**
	 * 标的类别
	 */
	private String targetType3;

	/**
	 * 标的类别
	 */
	private String targetType3Name;

	/**
	 * 发行机构代码
	 */
	private String issuePartyId;

	/**
	 * 发行机构
	 */
	private String issuePartyName;

	/**
	 * 期限（月）
	 */
	private Integer termMonth;

	/**
	 * 担保情况说明
	 */
	private String guaranteeCmt;

	/**
	 * 是否含权等特条款情况说明
	 */
	private String specialCmt;

	/**
	 * 合约名称
	 */
	private String contractName;

	/**
	 * 固定收益部分所占比例
	 */
	@RateFormat
	private BigDecimal incomeRatio;

	/**
	 * 衍生金融工具所占比例
	 */
	@RateFormat
	private BigDecimal derInstrRatio;

	/**
	 * 衍生金融工具具体投资方式
	 */
	private String derInstrInvType;

	/**
	 * 衍生金融工具挂钩标的资产
	 */
	private String derInstrTarget;

	/**
	 * 利息结算方式
	 */
	private String acctMode;

	/**
	 * 含权情况说明
	 */
	private String specialCmt1;

	/**
	 * 结构性票据最高收益率
	 */
	@RateFormat
	private BigDecimal maxYield;

	/**
	 * 结构性票据最低收益率
	 */
	@RateFormat
	private BigDecimal minYield;

	/**
	 * 挂钩标的资产基准价格
	 */
	private BigDecimal basicPrice;

	/**
	 * 挂钩标的资产登记日价格
	 */
	private BigDecimal regDatePrice;

	
	/**
	 * 交易费
	 */
	private String tradeFee;
	


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
	 * 资金实际投向
	 */
	private String truActType;

	/**
	 * 资金实际投向
	 */
	private String truActTypeName;

	/**
	 * 资金运用方式
	 */
	private String investType;

	/**
	 * 资金运用行业
	 */
	private String capUseIndu;

	/**
	 * 资金运用行业
	 */
	private String capUseInduName;

    /**
     * 资金运用行业二級
     */
    private String capUseInduLevel2;

    /**
     * 资金运用行业二級
     */
    private String capUseInduLevel2Name;

	/**
	 * 资管计划成立日期
	 */
	private Date planSdate;

	/**
	 * 资管计划终止日期
	 */
	private Date planEdate;

	/**
	 * 资管计划属性
	 */
	private String proProperty;

	/**
	 * 资管计划属性
	 */
	private String proPropertyName;

	/**
	 * 委外投资属性
	 */
	private String proProperty1;

	/**
	 * 委外投资属性
	 */
	private String proPropertyName1;
	
	/**
	 * 存款类型
	*/
	private String depoType;
	
	/**
	 * 对手方类型
	*/
	private String rivalPartyType;
	
	/**
	 * 份额面值
	*/
	private BigDecimal prinBaseamt;
	
	/**
	 * 法定到期日
	*/
	private Date fmdate;
	
	/**
	 * 计息类型
	*/
	private String couponSpecies;
	
	/**
	 * 规则付息标识
	*/
	private String interestRuleFlag;
	
	/**
	 * 利息分布方式
	*/
	private String intpayRule;
	
	/**
	 * 是否有浮动因子
	*/
	private String floatFactorFlag;
	
	/**
	 * 结构档次
	*/
	private String structGrade;
	
	/**
	 * 还本方式
	*/
	private String repaymentType;
	
	/**
	 * 分期还本条款标识
	*/
	private String periodRepaymentFlag;
	
	/**
	 * 基础资产类型
	*/
	private String basicAssetType;
	
	/**
	 * 超额收益分配比例%
	*/
	@RateFormat
	private BigDecimal excessProfitRate;
	
	/**
	 * 抵质押物类型
	*/
	private String pledgeType;
	
	/**
	 * 抵质押物价值（元）
	*/
	private BigDecimal pledgeValue;
	
	/**
	 * 担保性质
	*/
	private String natureGuarantee;
	
	/**
	 * 担保人与融资人关系
	*/
	private String relationFinan;
	
	/**
	 * 含权类型
	*/
	private String containType;
	
	/**
	 * 行权方式
	*/
	private String exerciseType;
	
	/**
	 * 固定行权日
	*/
	private Date exerciseDate;
	
	/**
	 * 首次行权日期
	*/
	private Date firstExerciseDate;
	
	/**
	 * 行权周期
	*/
	private Long exercisePeriod;
	
	/**
	 * 行权价格
	*/
	private BigDecimal exercisePrice;
	
	/**
	 * 永续条款类型
	*/
	private String perpetualClauseType;
	
	/**
	 * 利息递延条款类型
	*/
	private String deferredInterestType;
	
	/**
	 * 递延利息是否计息
	*/
	private String deferredInterestInt;
	
	/**
	 * 首次重定价日期
	*/
	private Date firstReprceDate;
	
	/**
	 * 重定价周期
	*/
	private Long reprceCycle;
	
	/**
	 * 部分赎回标识
	*/
	private String partRedemFlag;
	
	/**
	 * 部分赎回比例%
	*/
	@RateFormat
	private BigDecimal partRedemRate;
	
	/**
	 * 增信机构代码
	*/
	private String creditOrgCode;
	
	/**
	 * 增信机构名称
	*/
	private String creditOrgName;
	
	/**
	 * 股权退出安排
	*/
	private Date equityQuit;
	
	/**
	 * 是否为债转股
	*/
	private String deptEquity;
	
	/**
	 * 持有目的
	*/
	private String holdPurpose;
	
	/**
	 * 是否由金融资产投资公司发行
	*/
	private String financialComissue;
	
	
	public String getProProperty1() {
		return proProperty1;
	}

	public String getProPropertyName1() {
		return proPropertyName1;
	}

	public void setProProperty1(String proProperty1) {
		this.proProperty1 = proProperty1;
	}

	public void setProPropertyName1(String proPropertyName1) {
		this.proPropertyName1 = proPropertyName1;
	}

	/**
	 * 最高预期收益率
	 */
	@RateFormat
	private BigDecimal maxRate;

	/**
	 * 最低预期收益率
	 */
	@RateFormat
	private BigDecimal minRate;

	/**
	 * 负债规模
	 */
	private BigDecimal debtScale;

	/**
	 * 购买结构
	 */
	private String buyStruct;

	/**
	 * 购买结构
	 */
	private String buyStructName;

	/**
	 * 管理方式
	 */
	private String manageType;

	/**
	 * 管理方式
	 */
	private String manageTypeName;

	/**
	 * 是否掌控计划底层资产详情
	 */
	private String isCtrlBottom;

	/**
	 * 是否掌控计划底层资产详情
	 */
	private String isCtrlBottomName;

	/**
	 * 管理费率
	 */
	@RateFormat
	private BigDecimal mgrFeeRate;

	/**
	 * 托管费率
	 */
	@RateFormat
	private BigDecimal truFeeRate;

	/**
	 * 交易相关合计费率
	 */
	@RateFormat
	private BigDecimal tradeFeeRate;

	/**
	 * 中介服务机构合计费率
	 */
	@RateFormat
	private BigDecimal agencyFeeRate;

	/**
	 * 其他合计费率
	 */
	@RateFormat
	private BigDecimal otherFeeRate;

	/**
	 * 资产价值
	 */
	private BigDecimal assetValue;

	/**
	 * 登记备案机构
	 */
	private String registOrg1;

	/**
	 * 登记备案机构
	 */
	private String registOrg1Name;

	/**
	 * 基金管理机构名称
	 */
	private String mngOrgName;

	/**
	 * 基金托管机构名称
	 */
	private String truOrgName;

	/**
	 * 基金投资资产
	 */
	private String fundInvestAsset;

	/**
	 * 行内资产类别
	 */
	private String inAssetType;

	/**
	 * 行内资产类别
	 */
	private String inAssetTypeName;

	/**
	 * 行内资产类别说明
	 */
	private String inAssetTypeCmm;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否底层资产
	 */
	private String isUnderAsset;

	/**
	 * 是否属于政府投资基金
	 */
	private String isGovernFund;

	/**
	 * 是否属于政府投资基金
	 */
	private String isGovernFundName;

	/**
	 * 政府投资基金投向
	 */
	private String governInvestFund;

	/**
	 * 政府投资基金投向
	 */
	private String governInvestFundName;

	/**
	 * 基金公司名称
	 */
	private String fundEnpeName;

	/**
	 * 受托人
	 */
	private String trustee;

	/**
	 * 行内报送截止日
	 */
	private Date submitDeadline;

	/**
	 * 中债报送截止日
	 */
	private Date chbSubmitDeadline;

	/**
	 * 报送日期
	 */
	private Date submitTime;

    /**
     * 报送流水号
     */
    private String sumitSeq;

	/**
	 * 数据日期
	 */
	private Date statusDate;

	/**
	 * 数据状态
	 */
	private String status;

	/**
	 * 数据状态名称
	 */
	private String statusName;

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
	 * 机构代码
	 */
	private String orgCode;

	/**
	 * 部门代码
	 */
	private String deptCode;

	/**
	 * 报送状态
	 */
	private String sendStatus;

	/**
	 * 报送状态
	 */
	private String sendStatusName;

	/**
	 * 报送反馈状态
	 */
	private String submitFeedbackStatus;

	/**
	 * 报送反馈状态
	 */
	private String submitFeedbackStatusName;

	/**
	 * 报送反馈状态
	 */
	private String submitFeedbackStatusname;

	/**
	 * 创建部门
	 */
	private String createDept;

	/**
	 * 数据来源
	 */
	private String dataSource;

	/**
	 * 删除原因
	 */
	private String deleteRes;





	/***1217升级**/


	/**
	 * 存款账号
	 */
	private String accountNo;

	/**
	 * 资产代码
	 */
	private String creditAssetCode;

	/**
	 * 是否标准化票据
	 */
	private String isBzhpj;

	/**
	 * 票据代码
	 */
	private String billCode;

	/**
	 * 是否为银行理财产品
	 */
	private String financialProductFlag;

	/**
	 * 产品登记编码
	 */
	private String prodRegisterCode;

	/**
	 * 是否为固定收益类
	 */
	private String profitFlag;

	/**
	 * 是否为同业借款
	 */
	private String isDepositFlag;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getCreditAssetCode() {
		return creditAssetCode;
	}

	public void setCreditAssetCode(String creditAssetCode) {
		this.creditAssetCode = creditAssetCode;
	}

	public String getIsBzhpj() {
		return isBzhpj;
	}

	public void setIsBzhpj(String isBzhpj) {
		this.isBzhpj = isBzhpj;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
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

	public String getProfitFlag() {
		return profitFlag;
	}

	public void setProfitFlag(String profitFlag) {
		this.profitFlag = profitFlag;
	}

	public String getIsDepositFlag() {
		return isDepositFlag;
	}

	public void setIsDepositFlag(String isDepositFlag) {
		this.isDepositFlag = isDepositFlag;
	}


	/***1217升级**/






	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public String getAssetType2() {
		return assetType2;
	}

	public void setAssetType2(String assetType2) {
		this.assetType2 = assetType2;
	}

	public String getAssetType2Name() {
		return assetType2Name;
	}

	public void setAssetType2Name(String assetType2Name) {
		this.assetType2Name = assetType2Name;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getCcyName() {
		return ccyName;
	}

	public void setCcyName(String ccyName) {
		this.ccyName = ccyName;
	}

	public String getTradeMarket() {
		return tradeMarket;
	}

	public void setTradeMarket(String tradeMarket) {
		this.tradeMarket = tradeMarket;
	}

	public String getTradeMarketName() {
		return tradeMarketName;
	}

	public void setTradeMarketName(String tradeMarketName) {
		this.tradeMarketName = tradeMarketName;
	}

	public Date getVdate() {
		return vdate;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public BigDecimal getAssetYield() {
		return assetYield;
	}

	public void setAssetYield(BigDecimal assetYield) {
		this.assetYield = assetYield;
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public String getBasisName() {
		return basisName;
	}

	public void setBasisName(String basisName) {
		this.basisName = basisName;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIsStruct() {
		return isStruct;
	}

	public void setIsStruct(String isStruct) {
		this.isStruct = isStruct;
	}

	public String getIsStructName() {
		return isStructName;
	}

	public void setIsStructName(String isStructName) {
		this.isStructName = isStructName;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetTypeName() {
		return targetTypeName;
	}

	public void setTargetTypeName(String targetTypeName) {
		this.targetTypeName = targetTypeName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getChbSectype() {
		return chbSectype;
	}

	public void setChbSectype(String chbSectype) {
		this.chbSectype = chbSectype;
	}

	public String getChbSectypeName() {
		return chbSectypeName;
	}

	public void setChbSectypeName(String chbSectypeName) {
		this.chbSectypeName = chbSectypeName;
	}

	public String getIssueMode() {
		return issueMode;
	}

	public void setIssueMode(String issueMode) {
		this.issueMode = issueMode;
	}

	public String getIssueModeName() {
		return issueModeName;
	}

	public void setIssueModeName(String issueModeName) {
		this.issueModeName = issueModeName;
	}

	public String getPartyGrade() {
		return partyGrade;
	}

	public void setPartyGrade(String partyGrade) {
		this.partyGrade = partyGrade;
	}

	public String getPartyGradeName() {
		return partyGradeName;
	}

	public void setPartyGradeName(String partyGradeName) {
		this.partyGradeName = partyGradeName;
	}

	public String getPartyType1() {
		return partyType1;
	}

	public void setPartyType1(String partyType1) {
		this.partyType1 = partyType1;
	}

	public String getPartyType1Name() {
		return partyType1Name;
	}

	public void setPartyType1Name(String partyType1Name) {
		this.partyType1Name = partyType1Name;
	}

	public String getPartyType3() {
		return partyType3;
	}

	public void setPartyType3(String partyType3) {
		this.partyType3 = partyType3;
	}

	public String getPartyType3Name() {
		return partyType3Name;
	}

	public void setPartyType3Name(String partyType3Name) {
		this.partyType3Name = partyType3Name;
	}

	public String getPartyType2() {
		return partyType2;
	}

	public void setPartyType2(String partyType2) {
		this.partyType2 = partyType2;
	}

	public String getPartyType2Name() {
		return partyType2Name;
	}

	public void setPartyType2Name(String partyType2Name) {
		this.partyType2Name = partyType2Name;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getIndustryLevel2() {
		return industryLevel2;
	}

	public void setIndustryLevel2(String industryLevel2) {
		this.industryLevel2 = industryLevel2;
	}

	public String getIndustryLevel2Name() {
		return industryLevel2Name;
	}

	public void setIndustryLevel2Name(String industryLevel2Name) {
		this.industryLevel2Name = industryLevel2Name;
	}

	public String getSecMainGrade() {
		return secMainGrade;
	}

	public void setSecMainGrade(String secMainGrade) {
		this.secMainGrade = secMainGrade;
	}

	public String getSecMainGradeName() {
		return secMainGradeName;
	}

	public void setSecMainGradeName(String secMainGradeName) {
		this.secMainGradeName = secMainGradeName;
	}

	public String getSecCreditGrade() {
		return secCreditGrade;
	}

	public void setSecCreditGrade(String secCreditGrade) {
		this.secCreditGrade = secCreditGrade;
	}

	public String getSecCreditGradeName() {
		return secCreditGradeName;
	}

	public void setSecCreditGradeName(String secCreditGradeName) {
		this.secCreditGradeName = secCreditGradeName;
	}

	public String getSecCreditGradeType() {
		return secCreditGradeType;
	}

	public void setSecCreditGradeType(String secCreditGradeType) {
		this.secCreditGradeType = secCreditGradeType;
	}

	public String getSecCreditGradeTypeName() {
		return secCreditGradeTypeName;
	}

	public void setSecCreditGradeTypeName(String secCreditGradeTypeName) {
		this.secCreditGradeTypeName = secCreditGradeTypeName;
	}

	public String getRegistOrg() {
		return registOrg;
	}

	public void setRegistOrg(String registOrg) {
		this.registOrg = registOrg;
	}

	public String getRegistOrgName() {
		return registOrgName;
	}

	public void setRegistOrgName(String registOrgName) {
		this.registOrgName = registOrgName;
	}

	public String getRegistorgComment() {
		return registorgComment;
	}

	public void setRegistorgComment(String registorgComment) {
		this.registorgComment = registorgComment;
	}

	public String getRivalParty() {
		return rivalParty;
	}

	public void setRivalParty(String rivalParty) {
		this.rivalParty = rivalParty;
	}

	public String getRivalPartyName() {
		return rivalPartyName;
	}

	public void setRivalPartyName(String rivalPartyName) {
		this.rivalPartyName = rivalPartyName;
	}

	public String getTargetType1() {
		return targetType1;
	}

	public void setTargetType1(String targetType1) {
		this.targetType1 = targetType1;
	}

	public String getTargetType1Name() {
		return targetType1Name;
	}

	public void setTargetType1Name(String targetType1Name) {
		this.targetType1Name = targetType1Name;
	}

	public BigDecimal getTargetAmt() {
		return targetAmt;
	}

	public void setTargetAmt(BigDecimal targetAmt) {
		this.targetAmt = targetAmt;
	}

	public String getCountryArea() {
		return countryArea;
	}

	public void setCountryArea(String countryArea) {
		this.countryArea = countryArea;
	}

	public String getCountryAreaName() {
		return countryAreaName;
	}

	public void setCountryAreaName(String countryAreaName) {
		this.countryAreaName = countryAreaName;
	}

	public String getProfitType() {
		return profitType;
	}

	public void setProfitType(String profitType) {
		this.profitType = profitType;
	}

	public String getProfitTypeName() {
		return profitTypeName;
	}

	public void setProfitTypeName(String profitTypeName) {
		this.profitTypeName = profitTypeName;
	}

	public String getIsResale() {
		return isResale;
	}

	public void setIsResale(String isResale) {
		this.isResale = isResale;
	}

	public String getIsResaleName() {
		return isResaleName;
	}

	public void setIsResaleName(String isResaleName) {
		this.isResaleName = isResaleName;
	}

	public Integer getTermDate() {
		return termDate;
	}

	public void setTermDate(Integer termDate) {
		this.termDate = termDate;
	}

	public String getRateFlag() {
		return rateFlag;
	}

	public void setRateFlag(String rateFlag) {
		this.rateFlag = rateFlag;
	}

	public String getRateFlagName() {
		return rateFlagName;
	}

	public void setRateFlagName(String rateFlagName) {
		this.rateFlagName = rateFlagName;
	}

	public Integer getIntpayCycle() {
		return intpayCycle;
	}

	public void setIntpayCycle(Integer intpayCycle) {
		this.intpayCycle = intpayCycle;
	}

	public String getRepayCmt() {
		return repayCmt;
	}

	public void setRepayCmt(String repayCmt) {
		this.repayCmt = repayCmt;
	}

	public String getBasisType() {
		return basisType;
	}

	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

	public String getBasisTypeName() {
		return basisTypeName;
	}

	public void setBasisTypeName(String basisTypeName) {
		this.basisTypeName = basisTypeName;
	}

	public BigDecimal getFloatFactor() {
		return floatFactor;
	}

	public void setFloatFactor(BigDecimal floatFactor) {
		this.floatFactor = floatFactor;
	}

	public BigDecimal getSpreadRate() {
		return spreadRate;
	}

	public void setSpreadRate(BigDecimal spreadRate) {
		this.spreadRate = spreadRate;
	}

	public String getFinancie() {
		return financie;
	}

	public void setFinancie(String financie) {
		this.financie = financie;
	}

	public String getFinOrgCode() {
		return finOrgCode;
	}

	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode;
	}

	public String getFcrGradeIn() {
		return fcrGradeIn;
	}

	public void setFcrGradeIn(String fcrGradeIn) {
		this.fcrGradeIn = fcrGradeIn;
	}

	public String getFcrGradeOut() {
		return fcrGradeOut;
	}

	public void setFcrGradeOut(String fcrGradeOut) {
		this.fcrGradeOut = fcrGradeOut;
	}

	public String getFinancierType1() {
		return financierType1;
	}

	public void setFinancierType1(String financierType1) {
		this.financierType1 = financierType1;
	}

	public String getFinancierType1Name() {
		return financierType1Name;
	}

	public void setFinancierType1Name(String financierType1Name) {
		this.financierType1Name = financierType1Name;
	}

	public String getFinancierType2() {
		return financierType2;
	}

	public void setFinancierType2(String financierType2) {
		this.financierType2 = financierType2;
	}

	public String getFinancierType2Name() {
		return financierType2Name;
	}

	public void setFinancierType2Name(String financierType2Name) {
		this.financierType2Name = financierType2Name;
	}

	public String getFinancierType3() {
		return financierType3;
	}

	public void setFinancierType3(String financierType3) {
		this.financierType3 = financierType3;
	}

	public String getFinancierType3Name() {
		return financierType3Name;
	}

	public void setFinancierType3Name(String financierType3Name) {
		this.financierType3Name = financierType3Name;
	}

	public String getFinanceProj() {
		return financeProj;
	}

	public void setFinanceProj(String financeProj) {
		this.financeProj = financeProj;
	}

	public String getIndustry1() {
		return industry1;
	}

	public void setIndustry1(String industry1) {
		this.industry1 = industry1;
	}

	public String getIndustry1Name() {
		return industry1Name;
	}

	public void setIndustry1Name(String industry1Name) {
		this.industry1Name = industry1Name;
	}

	public String getIndustryLevel2Type() {
		return industryLevel2Type;
	}

	public void setIndustryLevel2Type(String industryLevel2Type) {
		this.industryLevel2Type = industryLevel2Type;
	}

	public String getIndustryLevel2TypeName() {
		return industryLevel2TypeName;
	}

	public void setIndustryLevel2TypeName(String industryLevel2TypeName) {
		this.industryLevel2TypeName = industryLevel2TypeName;
	}

	public String getProIndustry() {
		return proIndustry;
	}

	public void setProIndustry(String proIndustry) {
		this.proIndustry = proIndustry;
	}

	public String getProIndustryName() {
		return proIndustryName;
	}

	public void setProIndustryName(String proIndustryName) {
		this.proIndustryName = proIndustryName;
	}

	public String getProIndustryLevel2() {
		return proIndustryLevel2;
	}

	public void setProIndustryLevel2(String proIndustryLevel2) {
		this.proIndustryLevel2 = proIndustryLevel2;
	}

	public String getProIndustryLevel2Name() {
		return proIndustryLevel2Name;
	}

	public void setProIndustryLevel2Name(String proIndustryLevel2Name) {
		this.proIndustryLevel2Name = proIndustryLevel2Name;
	}

	public String getIndustryFlag() {
		return industryFlag;
	}

	public void setIndustryFlag(String industryFlag) {
		this.industryFlag = industryFlag;
	}

	public String getIndustryFlagName() {
		return industryFlagName;
	}

	public void setIndustryFlagName(String industryFlagName) {
		this.industryFlagName = industryFlagName;
	}

	public String getMonitType() {
		return monitType;
	}

	public void setMonitType(String monitType) {
		this.monitType = monitType;
	}

	public String getMonitTypeName() {
		return monitTypeName;
	}

	public void setMonitTypeName(String monitTypeName) {
		this.monitTypeName = monitTypeName;
	}

	public String getMonTypeComm() {
		return monTypeComm;
	}

	public void setMonTypeComm(String monTypeComm) {
		this.monTypeComm = monTypeComm;
	}

	public String getGuaType() {
		return guaType;
	}

	public void setGuaType(String guaType) {
		this.guaType = guaType;
	}

	public String getGuaTypeName() {
		return guaTypeName;
	}

	public void setGuaTypeName(String guaTypeName) {
		this.guaTypeName = guaTypeName;
	}

	public String getGuaTypeRemark() {
		return guaTypeRemark;
	}

	public void setGuaTypeRemark(String guaTypeRemark) {
		this.guaTypeRemark = guaTypeRemark;
	}

	public String getFinancierGrade() {
		return financierGrade;
	}

	public void setFinancierGrade(String financierGrade) {
		this.financierGrade = financierGrade;
	}

	public String getFinancierGradeName() {
		return financierGradeName;
	}

	public void setFinancierGradeName(String financierGradeName) {
		this.financierGradeName = financierGradeName;
	}

	public String getInGrade() {
		return inGrade;
	}

	public void setInGrade(String inGrade) {
		this.inGrade = inGrade;
	}

	public String getInGradeName() {
		return inGradeName;
	}

	public void setInGradeName(String inGradeName) {
		this.inGradeName = inGradeName;
	}

	public String getOutGrade() {
		return outGrade;
	}

	public void setOutGrade(String outGrade) {
		this.outGrade = outGrade;
	}

	public String getOutGradeName() {
		return outGradeName;
	}

	public void setOutGradeName(String outGradeName) {
		this.outGradeName = outGradeName;
	}

	public String getChose() {
		return chose;
	}

	public void setChose(String chose) {
		this.chose = chose;
	}

	public String getConditionCmm() {
		return conditionCmm;
	}

	public void setConditionCmm(String conditionCmm) {
		this.conditionCmm = conditionCmm;
	}

	public String getFinArea() {
		return finArea;
	}

	public void setFinArea(String finArea) {
		this.finArea = finArea;
	}

	public String getFinAreaName() {
		return finAreaName;
	}

	public void setFinAreaName(String finAreaName) {
		this.finAreaName = finAreaName;
	}

	public BigDecimal getTotalFeeRate() {
		return totalFeeRate;
	}

	public void setTotalFeeRate(BigDecimal totalFeeRate) {
		this.totalFeeRate = totalFeeRate;
	}

	public String getIsRight() {
		return isRight;
	}

	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}

	public String getIsRightName() {
		return isRightName;
	}

	public void setIsRightName(String isRightName) {
		this.isRightName = isRightName;
	}

	public String getNotstandSecType() {
		return notstandSecType;
	}

	public void setNotstandSecType(String notstandSecType) {
		this.notstandSecType = notstandSecType;
	}

	public String getNotstandSecTypeName() {
		return notstandSecTypeName;
	}

	public void setNotstandSecTypeName(String notstandSecTypeName) {
		this.notstandSecTypeName = notstandSecTypeName;
	}

	public Integer getNotstandSecNum() {
		return notstandSecNum;
	}

	public void setNotstandSecNum(Integer notstandSecNum) {
		this.notstandSecNum = notstandSecNum;
	}

	public BigDecimal getRemainTerm() {
		return remainTerm;
	}

	public void setRemainTerm(BigDecimal remainTerm) {
		this.remainTerm = remainTerm;
	}

	public Integer getMaxRemainTerm() {
		return maxRemainTerm;
	}

	public void setMaxRemainTerm(Integer maxRemainTerm) {
		this.maxRemainTerm = maxRemainTerm;
	}

	public Integer getMinRemainTerm() {
		return minRemainTerm;
	}

	public void setMinRemainTerm(Integer minRemainTerm) {
		this.minRemainTerm = minRemainTerm;
	}

	public String getSharesType() {
		return sharesType;
	}

	public void setSharesType(String sharesType) {
		this.sharesType = sharesType;
	}

	public String getSharesTypeName() {
		return sharesTypeName;
	}

	public void setSharesTypeName(String sharesTypeName) {
		this.sharesTypeName = sharesTypeName;
	}

	public String getIndustryShares() {
		return industryShares;
	}

	public void setIndustryShares(String industryShares) {
		this.industryShares = industryShares;
	}

	public String getIndustrySharesName() {
		return industrySharesName;
	}

	public void setIndustrySharesName(String industrySharesName) {
		this.industrySharesName = industrySharesName;
	}

	public String getIndustryShares2() {
		return industryShares2;
	}

	public void setIndustryShares2(String industryShares2) {
		this.industryShares2 = industryShares2;
	}

	public String getIndustryShares2Name() {
		return industryShares2Name;
	}

	public void setIndustryShares2Name(String industryShares2Name) {
		this.industryShares2Name = industryShares2Name;
	}

	public String getInvestStage() {
		return investStage;
	}

	public void setInvestStage(String investStage) {
		this.investStage = investStage;
	}

	public String getInvestStageName() {
		return investStageName;
	}

	public void setInvestStageName(String investStageName) {
		this.investStageName = investStageName;
	}

	public String getSharesType1() {
		return sharesType1;
	}

	public void setSharesType1(String sharesType1) {
		this.sharesType1 = sharesType1;
	}

	public String getSharesType1Name() {
		return sharesType1Name;
	}

	public void setSharesType1Name(String sharesType1Name) {
		this.sharesType1Name = sharesType1Name;
	}

	public String getSharesType2() {
		return sharesType2;
	}

	public void setSharesType2(String sharesType2) {
		this.sharesType2 = sharesType2;
	}

	public String getSharesType2Name() {
		return sharesType2Name;
	}

	public void setSharesType2Name(String sharesType2Name) {
		this.sharesType2Name = sharesType2Name;
	}

	public String getSharesType3() {
		return sharesType3;
	}

	public void setSharesType3(String sharesType3) {
		this.sharesType3 = sharesType3;
	}

	public String getSharesType3Name() {
		return sharesType3Name;
	}

	public void setSharesType3Name(String sharesType3Name) {
		this.sharesType3Name = sharesType3Name;
	}

	public String getIsPledge() {
		return isPledge;
	}

	public void setIsPledge(String isPledge) {
		this.isPledge = isPledge;
	}

	public String getIsPledgeName() {
		return isPledgeName;
	}

	public void setIsPledgeName(String isPledgeName) {
		this.isPledgeName = isPledgeName;
	}

	public BigDecimal getNomCapi() {
		return nomCapi;
	}

	public void setNomCapi(BigDecimal nomCapi) {
		this.nomCapi = nomCapi;
	}

	public String getTargetType3() {
		return targetType3;
	}

	public void setTargetType3(String targetType3) {
		this.targetType3 = targetType3;
	}

	public String getTargetType3Name() {
		return targetType3Name;
	}

	public void setTargetType3Name(String targetType3Name) {
		this.targetType3Name = targetType3Name;
	}

	public String getIssuePartyId() {
		return issuePartyId;
	}

	public void setIssuePartyId(String issuePartyId) {
		this.issuePartyId = issuePartyId;
	}

	public String getIssuePartyName() {
		return issuePartyName;
	}

	public void setIssuePartyName(String issuePartyName) {
		this.issuePartyName = issuePartyName;
	}

	public Integer getTermMonth() {
		return termMonth;
	}

	public void setTermMonth(Integer termMonth) {
		this.termMonth = termMonth;
	}

	public String getGuaranteeCmt() {
		return guaranteeCmt;
	}

	public void setGuaranteeCmt(String guaranteeCmt) {
		this.guaranteeCmt = guaranteeCmt;
	}

	public String getSpecialCmt() {
		return specialCmt;
	}

	public void setSpecialCmt(String specialCmt) {
		this.specialCmt = specialCmt;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public BigDecimal getIncomeRatio() {
		return incomeRatio;
	}

	public void setIncomeRatio(BigDecimal incomeRatio) {
		this.incomeRatio = incomeRatio;
	}

	public BigDecimal getDerInstrRatio() {
		return derInstrRatio;
	}

	public void setDerInstrRatio(BigDecimal derInstrRatio) {
		this.derInstrRatio = derInstrRatio;
	}

	public String getDerInstrInvType() {
		return derInstrInvType;
	}

	public void setDerInstrInvType(String derInstrInvType) {
		this.derInstrInvType = derInstrInvType;
	}

	public String getDerInstrTarget() {
		return derInstrTarget;
	}

	public void setDerInstrTarget(String derInstrTarget) {
		this.derInstrTarget = derInstrTarget;
	}

	public String getAcctMode() {
		return acctMode;
	}

	public void setAcctMode(String acctMode) {
		this.acctMode = acctMode;
	}

	public String getSpecialCmt1() {
		return specialCmt1;
	}

	public void setSpecialCmt1(String specialCmt1) {
		this.specialCmt1 = specialCmt1;
	}

	public BigDecimal getMaxYield() {
		return maxYield;
	}

	public void setMaxYield(BigDecimal maxYield) {
		this.maxYield = maxYield;
	}

	public BigDecimal getMinYield() {
		return minYield;
	}

	public void setMinYield(BigDecimal minYield) {
		this.minYield = minYield;
	}

	public BigDecimal getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(BigDecimal basicPrice) {
		this.basicPrice = basicPrice;
	}

	public BigDecimal getRegDatePrice() {
		return regDatePrice;
	}

	public void setRegDatePrice(BigDecimal regDatePrice) {
		this.regDatePrice = regDatePrice;
	}

	public String getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee;
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

	public String getTruActType() {
		return truActType;
	}

	public void setTruActType(String truActType) {
		this.truActType = truActType;
	}

	public String getTruActTypeName() {
		return truActTypeName;
	}

	public void setTruActTypeName(String truActTypeName) {
		this.truActTypeName = truActTypeName;
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

	public String getCapUseInduName() {
		return capUseInduName;
	}

	public void setCapUseInduName(String capUseInduName) {
		this.capUseInduName = capUseInduName;
	}

	public String getCapUseInduLevel2() {
		return capUseInduLevel2;
	}

	public void setCapUseInduLevel2(String capUseInduLevel2) {
		this.capUseInduLevel2 = capUseInduLevel2;
	}

	public String getCapUseInduLevel2Name() {
		return capUseInduLevel2Name;
	}

	public void setCapUseInduLevel2Name(String capUseInduLevel2Name) {
		this.capUseInduLevel2Name = capUseInduLevel2Name;
	}

	public Date getPlanSdate() {
		return planSdate;
	}

	public void setPlanSdate(Date planSdate) {
		this.planSdate = planSdate;
	}

	public Date getPlanEdate() {
		return planEdate;
	}

	public void setPlanEdate(Date planEdate) {
		this.planEdate = planEdate;
	}

	public String getProProperty() {
		return proProperty;
	}

	public void setProProperty(String proProperty) {
		this.proProperty = proProperty;
	}

	public String getProPropertyName() {
		return proPropertyName;
	}

	public void setProPropertyName(String proPropertyName) {
		this.proPropertyName = proPropertyName;
	}

	public String getDepoType() {
		return depoType;
	}

	public void setDepoType(String depoType) {
		this.depoType = depoType;
	}

	public String getRivalPartyType() {
		return rivalPartyType;
	}

	public void setRivalPartyType(String rivalPartyType) {
		this.rivalPartyType = rivalPartyType;
	}

	
	public BigDecimal getPrinBaseamt() {
		return prinBaseamt;
	}

	public void setPrinBaseamt(BigDecimal prinBaseamt) {
		this.prinBaseamt = prinBaseamt;
	}

	public Date getFmdate() {
		return fmdate;
	}

	public void setFmdate(Date fmdate) {
		this.fmdate = fmdate;
	}

	public String getCouponSpecies() {
		return couponSpecies;
	}

	public void setCouponSpecies(String couponSpecies) {
		this.couponSpecies = couponSpecies;
	}

	public String getInterestRuleFlag() {
		return interestRuleFlag;
	}

	public void setInterestRuleFlag(String interestRuleFlag) {
		this.interestRuleFlag = interestRuleFlag;
	}

	public String getIntpayRule() {
		return intpayRule;
	}

	public void setIntpayRule(String intpayRule) {
		this.intpayRule = intpayRule;
	}

	public String getFloatFactorFlag() {
		return floatFactorFlag;
	}

	public void setFloatFactorFlag(String floatFactorFlag) {
		this.floatFactorFlag = floatFactorFlag;
	}

	public String getStructGrade() {
		return structGrade;
	}

	public void setStructGrade(String structGrade) {
		this.structGrade = structGrade;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getPeriodRepaymentFlag() {
		return periodRepaymentFlag;
	}

	public void setPeriodRepaymentFlag(String periodRepaymentFlag) {
		this.periodRepaymentFlag = periodRepaymentFlag;
	}

	public String getBasicAssetType() {
		return basicAssetType;
	}

	public void setBasicAssetType(String basicAssetType) {
		this.basicAssetType = basicAssetType;
	}

	public BigDecimal getExcessProfitRate() {
		return excessProfitRate;
	}

	public void setExcessProfitRate(BigDecimal excessProfitRate) {
		this.excessProfitRate = excessProfitRate;
	}

	public String getPledgeType() {
		return pledgeType;
	}

	public void setPledgeType(String pledgeType) {
		this.pledgeType = pledgeType;
	}

	public BigDecimal getPledgeValue() {
		return pledgeValue;
	}

	public void setPledgeValue(BigDecimal pledgeValue) {
		this.pledgeValue = pledgeValue;
	}

	public String getNatureGuarantee() {
		return natureGuarantee;
	}

	public void setNatureGuarantee(String natureGuarantee) {
		this.natureGuarantee = natureGuarantee;
	}

	public String getRelationFinan() {
		return relationFinan;
	}

	public void setRelationFinan(String relationFinan) {
		this.relationFinan = relationFinan;
	}

	public String getContainType() {
		return containType;
	}

	public void setContainType(String containType) {
		this.containType = containType;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public Date getExerciseDate() {
		return exerciseDate;
	}

	public void setExerciseDate(Date exerciseDate) {
		this.exerciseDate = exerciseDate;
	}

	public Date getFirstExerciseDate() {
		return firstExerciseDate;
	}

	public void setFirstExerciseDate(Date firstExerciseDate) {
		this.firstExerciseDate = firstExerciseDate;
	}

	public Long getExercisePeriod() {
		return exercisePeriod;
	}

	public void setExercisePeriod(Long exercisePeriod) {
		this.exercisePeriod = exercisePeriod;
	}

	public BigDecimal getExercisePrice() {
		return exercisePrice;
	}

	public void setExercisePrice(BigDecimal exercisePrice) {
		this.exercisePrice = exercisePrice;
	}

	public String getPerpetualClauseType() {
		return perpetualClauseType;
	}

	public void setPerpetualClauseType(String perpetualClauseType) {
		this.perpetualClauseType = perpetualClauseType;
	}

	public String getDeferredInterestType() {
		return deferredInterestType;
	}

	public void setDeferredInterestType(String deferredInterestType) {
		this.deferredInterestType = deferredInterestType;
	}

	public String getDeferredInterestInt() {
		return deferredInterestInt;
	}

	public void setDeferredInterestInt(String deferredInterestInt) {
		this.deferredInterestInt = deferredInterestInt;
	}

	public Date getFirstReprceDate() {
		return firstReprceDate;
	}

	public void setFirstReprceDate(Date firstReprceDate) {
		this.firstReprceDate = firstReprceDate;
	}

	public Long getReprceCycle() {
		return reprceCycle;
	}

	public void setReprceCycle(Long reprceCycle) {
		this.reprceCycle = reprceCycle;
	}

	public String getPartRedemFlag() {
		return partRedemFlag;
	}

	public void setPartRedemFlag(String partRedemFlag) {
		this.partRedemFlag = partRedemFlag;
	}

	public BigDecimal getPartRedemRate() {
		return partRedemRate;
	}

	public void setPartRedemRate(BigDecimal partRedemRate) {
		this.partRedemRate = partRedemRate;
	}

	public String getCreditOrgCode() {
		return creditOrgCode;
	}

	public void setCreditOrgCode(String creditOrgCode) {
		this.creditOrgCode = creditOrgCode;
	}

	public String getCreditOrgName() {
		return creditOrgName;
	}

	public void setCreditOrgName(String creditOrgName) {
		this.creditOrgName = creditOrgName;
	}

	public Date getEquityQuit() {
		return equityQuit;
	}

	public void setEquityQuit(Date equityQuit) {
		this.equityQuit = equityQuit;
	}

	public String getDeptEquity() {
		return deptEquity;
	}

	public void setDeptEquity(String deptEquity) {
		this.deptEquity = deptEquity;
	}

	public String getHoldPurpose() {
		return holdPurpose;
	}

	public void setHoldPurpose(String holdPurpose) {
		this.holdPurpose = holdPurpose;
	}

	public String getFinancialComissue() {
		return financialComissue;
	}

	public void setFinancialComissue(String financialComissue) {
		this.financialComissue = financialComissue;
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

	public BigDecimal getDebtScale() {
		return debtScale;
	}

	public void setDebtScale(BigDecimal debtScale) {
		this.debtScale = debtScale;
	}

	public String getBuyStruct() {
		return buyStruct;
	}

	public void setBuyStruct(String buyStruct) {
		this.buyStruct = buyStruct;
	}

	public String getBuyStructName() {
		return buyStructName;
	}

	public void setBuyStructName(String buyStructName) {
		this.buyStructName = buyStructName;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public String getManageTypeName() {
		return manageTypeName;
	}

	public void setManageTypeName(String manageTypeName) {
		this.manageTypeName = manageTypeName;
	}

	public String getIsCtrlBottom() {
		return isCtrlBottom;
	}

	public void setIsCtrlBottom(String isCtrlBottom) {
		this.isCtrlBottom = isCtrlBottom;
	}

	public String getIsCtrlBottomName() {
		return isCtrlBottomName;
	}

	public void setIsCtrlBottomName(String isCtrlBottomName) {
		this.isCtrlBottomName = isCtrlBottomName;
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

	public BigDecimal getAssetValue() {
		return assetValue;
	}

	public void setAssetValue(BigDecimal assetValue) {
		this.assetValue = assetValue;
	}

	public String getRegistOrg1() {
		return registOrg1;
	}

	public void setRegistOrg1(String registOrg1) {
		this.registOrg1 = registOrg1;
	}

	public String getRegistOrg1Name() {
		return registOrg1Name;
	}

	public void setRegistOrg1Name(String registOrg1Name) {
		this.registOrg1Name = registOrg1Name;
	}

	public String getMngOrgName() {
		return mngOrgName;
	}

	public void setMngOrgName(String mngOrgName) {
		this.mngOrgName = mngOrgName;
	}

	public String getTruOrgName() {
		return truOrgName;
	}

	public void setTruOrgName(String truOrgName) {
		this.truOrgName = truOrgName;
	}

	public String getFundInvestAsset() {
		return fundInvestAsset;
	}

	public void setFundInvestAsset(String fundInvestAsset) {
		this.fundInvestAsset = fundInvestAsset;
	}

	public String getInAssetType() {
		return inAssetType;
	}

	public void setInAssetType(String inAssetType) {
		this.inAssetType = inAssetType;
	}

	public String getInAssetTypeName() {
		return inAssetTypeName;
	}

	public void setInAssetTypeName(String inAssetTypeName) {
		this.inAssetTypeName = inAssetTypeName;
	}

	public String getInAssetTypeCmm() {
		return inAssetTypeCmm;
	}

	public void setInAssetTypeCmm(String inAssetTypeCmm) {
		this.inAssetTypeCmm = inAssetTypeCmm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsUnderAsset() {
		return isUnderAsset;
	}

	public void setIsUnderAsset(String isUnderAsset) {
		this.isUnderAsset = isUnderAsset;
	}

	public String getIsGovernFund() {
		return isGovernFund;
	}

	public void setIsGovernFund(String isGovernFund) {
		this.isGovernFund = isGovernFund;
	}

	public String getIsGovernFundName() {
		return isGovernFundName;
	}

	public void setIsGovernFundName(String isGovernFundName) {
		this.isGovernFundName = isGovernFundName;
	}

	public String getGovernInvestFund() {
		return governInvestFund;
	}

	public void setGovernInvestFund(String governInvestFund) {
		this.governInvestFund = governInvestFund;
	}

	public String getGovernInvestFundName() {
		return governInvestFundName;
	}

	public void setGovernInvestFundName(String governInvestFundName) {
		this.governInvestFundName = governInvestFundName;
	}

	public String getFundEnpeName() {
		return fundEnpeName;
	}

	public void setFundEnpeName(String fundEnpeName) {
		this.fundEnpeName = fundEnpeName;
	}

	public String getTrustee() {
		return trustee;
	}

	public void setTrustee(String trustee) {
		this.trustee = trustee;
	}

	public Date getSubmitDeadline() {
		return submitDeadline;
	}

	public void setSubmitDeadline(Date submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	public Date getChbSubmitDeadline() {
		return chbSubmitDeadline;
	}

	public void setChbSubmitDeadline(Date chbSubmitDeadline) {
		this.chbSubmitDeadline = chbSubmitDeadline;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSumitSeq() {
		return sumitSeq;
	}

	public void setSumitSeq(String sumitSeq) {
		this.sumitSeq = sumitSeq;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getSendStatusName() {
		return sendStatusName;
	}

	public void setSendStatusName(String sendStatusName) {
		this.sendStatusName = sendStatusName;
	}

	public String getSubmitFeedbackStatus() {
		return submitFeedbackStatus;
	}

	public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
		this.submitFeedbackStatus = submitFeedbackStatus;
	}

	public String getSubmitFeedbackStatusName() {
		return submitFeedbackStatusName;
	}

	public void setSubmitFeedbackStatusName(String submitFeedbackStatusName) {
		this.submitFeedbackStatusName = submitFeedbackStatusName;
	}

	public String getSubmitFeedbackStatusname() {
		return submitFeedbackStatusname;
	}

	public void setSubmitFeedbackStatusname(String submitFeedbackStatusname) {
		this.submitFeedbackStatusname = submitFeedbackStatusname;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDeleteRes() {
		return deleteRes;
	}

	public void setDeleteRes(String deleteRes) {
		this.deleteRes = deleteRes;
	}
	
}
