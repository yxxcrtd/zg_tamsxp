package com.joyintech.tams.svreport.chb.assetinforeg.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.TableRow;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

/**
 * 描述:CHB_ASSET_INFO_SUB表的实体类,用于前台分类展示
 * DateUtil
 * @version
 * @author: Administrator
 * @创建时间: 2019-01-21
 */
@TableName("CHB_ASSET_INFO_SUB")
public class AssetInfoRegist001Dto extends BaseEntity implements ITableRow {
	private DecimalFormat df5 = new DecimalFormat("0.00000");
	private DecimalFormat df2 = new DecimalFormat("0.00");
	private DecimalFormat df4 = new DecimalFormat("0.0000");
	/**
	 * 登记序号
	 */
	private String registSeqno;

	/**
	 * 行内资产负债编码
	 */
    @BusinessKey("ASSET_CODE")
	private String assetCode;

	/**
	 * 资产/负债类别
	 */
    @DictFormat(DictCode.资产_负债类别)
	private String assetType;

	/**
	 * 资产/负债类别
	 */
	private String assetTypeName;

	/**
	 * 资产/负债二级类别
	 */
	@DictFormat(DictCode.资产_负债二级类别)
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
	@DictFormat(DictCode.交易流通场所_中债)
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
	private BigDecimal assetYield;

	/**
	 * 计息基础
	 */
	@DictFormat(DictCode.计息基础_中债)
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
	@DictFormat(DictCode.是否_中债)
	private String isStruct;

	/**
	 * 是否为结构性存款
	 */
	private String isStructName;

	/**
	 * 结构性存款挂钩标的类别
	 */
	@DictFormat(DictCode.标的类别)
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
	@DictFormat(DictCode.具体类别)
	private String chbSectype;

	/**
	 * 具体类别
	 */
	private String chbSectypeName;

	/**
	 * 发行方式
	 */
	@DictFormat(DictCode.债券类资产发行方式)
	private String issueMode;

	/**
	 * 发行方式
	 */
	private String issueModeName;

	/**
	 * 发行机构主体信用评级
	 */
	// @DictFormat(DictCode.资产评级)
	private String partyGrade;

	/**
	 * 发行机构主体信用评级
	 */
	private String partyGradeName;

	/**
	 * 发行机构类型（按规模划分）
	 */
	@DictFormat(DictCode.发行机构类型_按规模划分)
	private String partyType1;

	/**
	 * 发行机构类型（按规模划分）
	 */
	private String partyType1Name;

	/**
	 * 发行机构类型（按技术领域划分）
	 */
	@DictFormat(DictCode.发行机构类型_按技术领域划分)
	private String partyType3;

	/**
	 * 发行机构类型（按技术领域划分）
	 */
	private String partyType3Name;

	/**
	 * 发行机构类型（按经济类型划分）
	 */
	@DictFormat(DictCode.发行机构类型_按经济类型划分)
	private String partyType2;

	/**
	 * 发行机构类型（按技术领域划分）
	 */
	private String partyType2Name;

	/**
	 * 发行机构所属行业一级分类
	 */
	@DictFormat(DictCode.机构所属行业一级分类)
	private String industry;

	/**
	 * 发行机构所属行业一级分类
	 */
	private String industryName;

	/**
	 * 发行机构所属行业二级分类
	 */
	@DictFormat(DictCode.机构所属行业二级分类)
	private String industryLevel2;

	/**
	 * 发行机构所属行业二级分类
	 */
	private String industryLevel2Name;

	/**
	 * 债券主体评级
	 */
	@DictFormat(DictCode.资产评级)
	private String secMainGrade;

	/**
	 * 债券主体评级
	 */
	private String secMainGradeName;

	/**
	 * 债券债项评级
	 */
	@DictFormat(DictCode.资产评级)
	private String secCreditGrade;

	/**
	 * 债券债项评级
	 */
	private String secCreditGradeName;

	/**
	 * 债券信用评级
	 */
	//@DictFormat(DictCode.资产评级)
	private String secCreditGradeType;
	
	/**
	 * 债券信用评级
	 */
	private String secCreditGradeTypeName;

	/**
	 * 登记托管机构
	 */
	@DictFormat(DictCode.登记托管机构_中债)
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
	@DictFormat(DictCode.回购标的类别)
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
	@DictFormat(DictCode.所属国家或地区)
	private String countryArea;

	/**
	 * 所属国家或地区
	 */
	private String countryAreaName;

	/**
	 * 收/受益权类型
	 */
	@DictFormat(DictCode.收受益权类型)
	private String profitType;

	/**
	 * 收/受益权类型
	 */
	private String profitTypeName;

	/**
	 * 是否属于买入返售
	 */
	@DictFormat(DictCode.是否_中债)
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
	@DictFormat(DictCode.是否_中债)
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
	@DictFormat(DictCode.基准利率)
	private String basisType;

	/**
	 * 基准利率种类
	 */
	private String basisTypeName;

	/**
	 * 浮动因子
	 */
	private BigDecimal floatFactor;

	/**
	 * 利差
	 */
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
	@DictFormat(DictCode.发行机构类型_按规模划分)
	private String financierType1;

	/**
	 * 融资人类型（按规模划分）
	 */
	private String financierType1Name;

	/**
	 * 融资人类型（按技术领域划分）
	 */
	@DictFormat(DictCode.发行机构类型_按技术领域划分)
	private String financierType2;

	/**
	 * 融资人类型（按技术领域划分）
	 */
	private String financierType2Name;

	/**
	 * 融资人类型（按经济类型划分）
	 */
	@DictFormat(DictCode.发行机构类型_按经济类型划分)
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
	@DictFormat(DictCode.机构所属行业二级分类)
	private String industry1;

	/**
	 * 融资人所属行业一级分类
	 */
	private String industry1Name;

	/**
	 * 融资人所属行业二级分类
	 */
	@DictFormat(DictCode.机构所属行业二级分类)
	private String industryLevel2Type;

	/**
	 * 融资人所属行业二级分类
	 */
	private String industryLevel2TypeName;

	/**
	 * 融资项目所属行业一级分类
	 */
	@DictFormat(DictCode.机构所属行业二级分类)
	private String proIndustry;

	/**
	 * 融资项目所属行业一级分类
	 */
	private String proIndustryName;

	/**
	 * 融资项目所属行业二级分类
	 */
	@DictFormat(DictCode.机构所属行业二级分类)
	private String proIndustryLevel2;

	/**
	 * 融资项目所属行业二级分类
	 */
	private String proIndustryLevel2Name;

	/**
	 * 项目是否属于重点监控行业和领域
	 */
	@DictFormat(DictCode.是否_中债)
	private String industryFlag;

	/**
	 * 项目是否属于重点监控行业和领域
	 */
	private String industryFlagName;

	/**
	 * 重点监控行业和领域类别
	 */
	@DictFormat(DictCode.重点监控行业和领域类别)
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
	@DictFormat(DictCode.担保方式)
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
	@DictFormat(DictCode.资产评级)
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
	@DictFormat(DictCode.资产评级)
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
	/*@DictFormat(DictCode.行政区划_市)*/
	@DictFormat(DictCode.行政区划_省市县)
	private String finArea;

	/**
	 * 融资人所属地区
	 */
	private String finAreaName;

	/**
	 * 融资总费率
	 */
	private BigDecimal totalFeeRate;

	/**
	 * 是否为收/受益权
	 */
	@DictFormat(DictCode.是否_中债)
	private String isRight;

	/**
	 * 是否为收/受益权
	 */
	private String isRightName;

	/**
	 * 非标准化债券类资产类型
	 */
	@DictFormat(DictCode.票据类信用证类资产类型)
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
	@DictFormat(DictCode.股票类型)
	private String sharesType;

	/**
	 * 股票类型
	 */
	private String sharesTypeName;

	/**
	 * 行业一级分类
	 */
	@DictFormat(DictCode.机构所属行业一级分类)
	private String industryShares;

	/**
	 * 行业一级分类
	 */
	private String industrySharesName;

	/**
	 * 行业二级分类
	 */
	@DictFormat(DictCode.机构所属行业二级分类)
	private String industryShares2;

	/**
	 * 行业二级分类
	 */
	private String industryShares2Name;

	/**
	 * 投资阶段
	 */
	@DictFormat(DictCode.投资阶段)
	private String investStage;

	/**
	 * 投资阶段
	 */
	private String investStageName;

	/**
	 * 投资企业类型（按规模划分）
	 */
	@DictFormat(DictCode.发行机构类型_按规模划分)
	private String sharesType1;

	/**
	 * 投资企业类型（按规模划分）
	 */
	private String sharesType1Name;

	/**
	 * 投资企业类型（按经济类型划分）
	 */
	@DictFormat(DictCode.发行机构类型_按经济类型划分)
	private String sharesType2;

	/**
	 * 投资企业类型（按经济类型划分）
	 */
	private String sharesType2Name;

	/**
	 * 投资企业类型（按技术领域划分）
	 */
	@DictFormat(DictCode.发行机构类型_按技术领域划分)
	private String sharesType3;

	/**
	 * 投资企业类型（按技术领域划分）
	 */
	private String sharesType3Name;

	/**
	 * 是否为质押融资
	 */
	@DictFormat(DictCode.是否_中债)
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
	 * 名义本金str
	 */
	private String nomCapiStr;

	/**
	 * 标的类别
	 */
	@DictFormat(DictCode.标的类别)
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
	private BigDecimal incomeRatio;

	/**
	 * 衍生金融工具所占比例
	 */
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
	private BigDecimal maxYield;

	/**
	 * 结构性票据最低收益率
	 */
	private BigDecimal minYield;

	/**
	 * 挂钩标的资产基准价格
	 */
	private BigDecimal basicPrice;
	
	/**
	 * 挂钩标的资产基准价格Str
	 */
	private String basicPriceStr;

	
	
	/**
	 * 挂钩标的资产登记日价格
	 */
	private BigDecimal regDatePrice;
	
	/**
	 * 挂钩标的资产登记日价格Str
	 */
	private String regDatePriceStr;

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
	@DictFormat(DictCode.资金实际投向)
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
	@DictFormat(DictCode.机构所属行业二级分类)
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
	@DictFormat(DictCode.资管计划属性)
	private String proProperty;

	/**
	 * 资管计划属性
	 */
	private String proPropertyName;

	/**
	 * 委外投资属性
	 */
	@DictFormat(DictCode.委外投资属性_中债)
	private String proProperty1;

	/**
	 * 委外投资属性
	 */
	private String proPropertyName1;
	
	/**
	 * 存款类型
	*/
	@DictFormat(DictCode.存款类型)
	private String depoType;
	
	/**
	 * 对手方类型
	*/
	@DictFormat(DictCode.对手方类型)
	private String rivalPartyType;
	
	/**
	 * 份额面值
	*/
	private BigDecimal prinBaseamt;
	
	/**
	 * 法定到期日
	*/
	private Date fmdate;
	private String fmdateStr;
	public String getFmdateStr(){
        if (StringUtil.isNotEmpty(this.getFmdate())){
            return DateUtil.format(this.getFmdate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	/**
	 * 计息类型
	*/
	@DictFormat(DictCode.计息类型)
	private String couponSpecies;
	
	/**
	 * 规则付息标识
	*/
	@DictFormat(DictCode.是否_中债)
	private String interestRuleFlag;
	
	/**
	 * 利息分布方式
	*/
	@DictFormat(DictCode.利息分布方式)
	private String intpayRule;
	
	/**
	 * 是否有浮动因子
	*/
	@DictFormat(DictCode.是否_中债)
	private String floatFactorFlag;
	
	/**
	 * 结构档次
	*/
	@DictFormat(DictCode.结构档次)
	private String structGrade;
	
	/**
	 * 还本方式
	*/
	@DictFormat(DictCode.还本方式)
	private String repaymentType;
	
	/**
	 * 分期还本条款标识
	*/
	@DictFormat(DictCode.分期还本条款标识)
	private String periodRepaymentFlag;
	
	/**
	 * 基础资产类型
	*/
	private String basicAssetType;
	
	/**
	 * 超额收益分配比例%
	*/
	private BigDecimal excessProfitRate;
	
	/**
	 * 抵质押物类型
	*/
	@DictFormat(DictCode.抵质押物类型)
	private String pledgeType;
	
	/**
	 * 抵质押物价值（元）
	*/
	private BigDecimal pledgeValue;
	
	/**
	 * 担保性质
	*/
	@DictFormat(DictCode.担保性质)
	private String natureGuarantee;
	
	/**
	 * 担保人与融资人关系
	*/
	@DictFormat(DictCode.担保人与融资人关系)
	private String relationFinan;
	
	/**
	 * 含权类型
	*/
	@DictFormat(DictCode.含权类型)
	private String containType;
	
	/**
	 * 行权方式
	*/
	@DictFormat(DictCode.资产行权方式)
	private String exerciseType;
	
	/**
	 * 固定行权日
	*/
	private Date exerciseDate;
	private String exerciseDateStr;
	public String getExerciseDateStr(){
        if (StringUtil.isNotEmpty(this.getExerciseDate())){
            return DateUtil.format(this.getExerciseDate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	/**
	 * 首次行权日期
	*/
	private Date firstExerciseDate;
	private String firstExerciseDateStr;
	public String getFirstExerciseDateStr(){
        if (StringUtil.isNotEmpty(this.getFirstExerciseDate())){
            return DateUtil.format(this.getFirstExerciseDate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	
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
	@DictFormat(DictCode.永续条款类型)
	private String perpetualClauseType;
	
	/**
	 * 利息递延条款类型
	*/
	@DictFormat(DictCode.利息递延条款类型)
	private String deferredInterestType;
	
	/**
	 * 递延利息是否计息
	*/
	@DictFormat(DictCode.是否_中债)
	private String deferredInterestInt;
	
	/**
	 * 首次重定价日期
	*/
	private Date firstReprceDate;
	private String firstReprceDateStr;
	public String getFirstReprceDateStr(){
        if (StringUtil.isNotEmpty(this.getFirstReprceDate())){
            return DateUtil.format(this.getFirstReprceDate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	
	/**
	 * 重定价周期
	*/
	private Long reprceCycle;
	
	/**
	 * 部分赎回标识
	*/
	@DictFormat(DictCode.是否_中债)
	private String partRedemFlag;
	
	/**
	 * 部分赎回比例%
	*/
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
	
	private String equityQuitStr;
	public String getEquityQuitStr(){
        if (StringUtil.isNotEmpty(this.getEquityQuit())){
            return DateUtil.format(this.getEquityQuit(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	/**
	 * 是否为债转股
	*/
	@DictFormat(DictCode.是否_中债)
	private String deptEquity;
	
	/**
	 * 持有目的
	*/
	@DictFormat(DictCode.持有目的)
	private String holdPurpose;
	
	/**
	 * 是否由金融资产投资公司发行
	*/
	@DictFormat(DictCode.是否_中债)
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
	private BigDecimal maxRate;

	/**
	 * 最低预期收益率
	 */
	private BigDecimal minRate;

	/**
	 * 负债规模
	 */
	private BigDecimal debtScale;

	/**
	 * 购买结构
	 */
	@DictFormat(DictCode.购买结构)
	private String buyStruct;

	/**
	 * 购买结构
	 */
	private String buyStructName;

	/**
	 * 管理方式
	 */
	@DictFormat(DictCode.管理方式)
	private String manageType;

	/**
	 * 管理方式
	 */
	private String manageTypeName;

	/**
	 * 是否掌控计划底层资产详情
	 */
	@DictFormat(DictCode.是否_中债)
	private String isCtrlBottom;

	/**
	 * 是否掌控计划底层资产详情
	 */
	private String isCtrlBottomName;

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
	 * 资产价值
	 */
	private BigDecimal assetValue;

	/**
	 * 登记备案机构
	 */
	@DictFormat(DictCode.登记备案机构)
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
	@DictFormat(DictCode.行内资产类别)
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
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRemark21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return remark;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

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
	@DictFormat(DictCode.是否_中债)
	private String isGovernFund;

	/**
	 * 是否属于政府投资基金
	 */
	private String isGovernFundName;

	/**
	 * 政府投资基金投向
	 */
	@DictFormat(DictCode.政府投资基金投向)
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
    @DictFormat(DictCode.报送反馈状态)
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

	/**
	 * 登记序号
	 * 
	 * @return REGIST_SEQNO 登记序号
	 */
	public String getRegistSeqno() {
		return registSeqno;
	}

	/**
	 * 登记序号
	 * 
	 * @param registSeqno
	 *            登记序号
	 */
	public void setRegistSeqno(String registSeqno) {
		this.registSeqno = registSeqno == null ? null : registSeqno.trim();
	}

	/**
	 * 行内资产负债编码
	 * 
	 * @return ASSET_CODE 行内资产负债编码
	 */
	public String getAssetCode() {
		return assetCode;
	}

	/**
	 * 行内资产负债编码
	 * 
	 * @param assetCode
	 *            行内资产负债编码
	 */
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode == null ? null : assetCode.trim();
	}

	/**
	 * 资产/负债类别
	 * 
	 * @return ASSET_TPEY 资产/负债类别
	 */
	public String getAssetType() {
		return assetType;
	}

	/**
	 * 资产/负债类别
	 * 
	 * @param assetTpey
	 *            资产/负债类别
	 */
	public void setAssetType(String assetType) {
		this.assetType = assetType == null ? null : assetType.trim();
	}

	/**
	 * 资产/负债二级类别
	 * 
	 * @return ASSET_TYPE2 资产/负债二级类别
	 */
	public String getAssetType2() {
		return assetType2;
	}

	/**
	 * 资产/负债二级类别
	 * 
	 * @param assetType2
	 *            资产/负债二级类别
	 */
	public void setAssetType2(String assetType2) {
		this.assetType2 = assetType2 == null ? null : assetType2.trim();
	}

	/**
	 * 币种
	 * 
	 * @return CCY 币种
	 */
	public String getCcy() {
		return ccy;
	}

	/**
	 * 币种
	 * 
	 * @param ccy
	 *            币种
	 */
	public void setCcy(String ccy) {
		this.ccy = ccy == null ? null : ccy.trim();
	}

	/**
	 * 交易流通场所
	 * 
	 * @return TRADE_MARKET 交易流通场所
	 */
	public String getTradeMarket() {
		return tradeMarket;
	}

	/**
	 * 交易流通场所
	 * 
	 * @param tradeMarket
	 *            交易流通场所
	 */
	public void setTradeMarket(String tradeMarket) {
		this.tradeMarket = tradeMarket == null ? null : tradeMarket.trim();
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Date getVdate() {
		return vdate;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getVdate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return DateUtil.format(vdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @param vdate
	 *            起息日
	 */
	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	/**
	 * 到期日
	 * 
	 * @return MDATE 到期日
	 */
	public Date getMdate() {
		return mdate;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMdate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return DateUtil.format(mdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 到期日
	 * 
	 * @param mdate
	 *            到期日
	 */
	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	/**
	 * 收益率/年利率
	 * 
	 * @return ASSET_YIELD 收益率/年利率
	 */
	public BigDecimal getAssetYield() {
		return assetYield;
	}
	public String getAssetYieldString() {
		return assetYield==null?"":df5.format(assetYield);
	}

	/**
	 * 收益率/年利率
	 * 
	 * @param assetYield
	 *            收益率/年利率
	 */
	public void setAssetYield(BigDecimal assetYield) {
		this.assetYield = assetYield;
	}

	/**
	 * 计息基础
	 * 
	 * @return BASIS 计息基础
	 */
	public String getBasis() {
		return basis;
	}

	/**
	 * 计息基础
	 * 
	 * @param basis
	 *            计息基础
	 */
	public void setBasis(String basis) {
		this.basis = basis == null ? null : basis.trim();
	}

	/**
	 * 金额
	 * 
	 * @return AMT 金额
	 */
	public BigDecimal getAmt() {
		return amt;
	}

	public String getAmtString() {
		return amt==null?"":df2.format(amt);
	}

	/**
	 * 金额
	 * 
	 * @param amt
	 *            金额
	 */
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	/**
	 * 资金存入银行
	 * 
	 * @return BANK_NAME 资金存入银行
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 资金存入银行
	 * 
	 * @param bankName
	 *            资金存入银行
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	/**
	 * 是否为结构性存款
	 * 
	 * @return IS_STRUCT 是否为结构性存款
	 */
	public String getIsStruct() {
		return isStruct;
	}

	/**
	 * 是否为结构性存款
	 * 
	 * @param isStruct
	 *            是否为结构性存款
	 */
	public void setIsStruct(String isStruct) {
		this.isStruct = isStruct == null ? null : isStruct.trim();
	}

	/**
	 * 结构性存款挂钩标的类别
	 * 
	 * @return TARGET_TYPE 结构性存款挂钩标的类别
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * 结构性存款挂钩标的类别
	 * 
	 * @param targetType
	 *            结构性存款挂钩标的类别
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType == null ? null : targetType.trim();
	}

	/**
	 * 结构性存款挂钩标的
	 * 
	 * @return TARGET 结构性存款挂钩标的
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * 结构性存款挂钩标的
	 * 
	 * @param target
	 *            结构性存款挂钩标的
	 */
	public void setTarget(String target) {
		this.target = target == null ? null : target.trim();
	}

	/**
	 * 资产代码
	 * 
	 * @return ASSET_ID 资产代码
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * 资产代码
	 * 
	 * @param assetId
	 *            资产代码
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId == null ? null : assetId.trim();
	}

	/**
	 * 资产名称
	 * 
	 * @return ASSET_NAME 资产名称
	 */
	public String getAssetName() {
		return assetName;
	}

	/**
	 * 资产名称
	 * 
	 * @param assetName
	 *            资产名称
	 */
	public void setAssetName(String assetName) {
		this.assetName = assetName == null ? null : assetName.trim();
	}

	/**
	 * 具体类别
	 * 
	 * @return CHB_SECTYPE 具体类别
	 */
	public String getChbSectype() {
		return chbSectype;
	}

	/**
	 * 具体类别
	 * 
	 * @param chbSectype
	 *            具体类别
	 */
	public void setChbSectype(String chbSectype) {
		this.chbSectype = chbSectype == null ? null : chbSectype.trim();
	}

	/**
	 * 发行方式
	 * 
	 * @return ISSUE_MODE 发行方式
	 */
	public String getIssueMode() {
		return issueMode;
	}

	/**
	 * 发行方式
	 * 
	 * @param issueMode
	 *            发行方式
	 */
	public void setIssueMode(String issueMode) {
		this.issueMode = issueMode == null ? null : issueMode.trim();
	}

	/**
	 * 发行机构主体信用评级
	 * 
	 * @return PARTY_GRADE 发行机构主体信用评级
	 */
	public String getPartyGrade() {
		return partyGrade;
	}

	/**
	 * 发行机构主体信用评级
	 * 
	 * @param partyGrade
	 *            发行机构主体信用评级
	 */
	public void setPartyGrade(String partyGrade) {
		this.partyGrade = partyGrade == null ? null : partyGrade.trim();
	}

	/**
	 * 发行机构类型（按规模划分）
	 * 
	 * @return PARTY_TYPE1 发行机构类型（按规模划分）
	 */
	public String getPartyType1() {
		return partyType1;
	}

	/**
	 * 发行机构类型（按规模划分）
	 * 
	 * @param partyType1
	 *            发行机构类型（按规模划分）
	 */
	public void setPartyType1(String partyType1) {
		this.partyType1 = partyType1 == null ? null : partyType1.trim();
	}

	/**
	 * 发行机构类型（按技术领域划分）
	 * 
	 * @return PARTY_TYPE3 发行机构类型（按技术领域划分）
	 */
	public String getPartyType3() {
		return partyType3;
	}

	/**
	 * 发行机构类型（按技术领域划分）
	 * 
	 * @param partyType3
	 *            发行机构类型（按技术领域划分）
	 */
	public void setPartyType3(String partyType3) {
		this.partyType3 = partyType3 == null ? null : partyType3.trim();
	}

	/**
	 * 发行机构类型（按经济类型划分）
	 * 
	 * @return PARTY_TYPE2 发行机构类型（按经济类型划分）
	 */
	public String getPartyType2() {
		return partyType2;
	}

	/**
	 * 发行机构类型（按经济类型划分）
	 * 
	 * @param partyType2
	 *            发行机构类型（按经济类型划分）
	 */
	public void setPartyType2(String partyType2) {
		this.partyType2 = partyType2 == null ? null : partyType2.trim();
	}

	/**
	 * 发行机构所属行业一级分类
	 * 
	 * @return INDUSTRY 发行机构所属行业一级分类
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * 发行机构所属行业一级分类
	 * 
	 * @param industry
	 *            发行机构所属行业一级分类
	 */
	public void setIndustry(String industry) {
		this.industry = industry == null ? null : industry.trim();
	}

	/**
	 * 发行机构所属行业二级分类
	 * 
	 * @return INDUSTRY_LEVEL2 发行机构所属行业二级分类
	 */
	public String getIndustryLevel2() {
		return industryLevel2;
	}

	/**
	 * 发行机构所属行业二级分类
	 * 
	 * @param industryLevel2
	 *            发行机构所属行业二级分类
	 */
	public void setIndustryLevel2(String industryLevel2) {
		this.industryLevel2 = industryLevel2 == null ? null : industryLevel2.trim();
	}

	/**
	 * 债券主体评级
	 * 
	 * @return SEC_MAIN_GRADE 债券主体评级
	 */
	public String getSecMainGrade() {
		return secMainGrade;
	}

	/**
	 * 债券主体评级
	 * 
	 * @param secMainGrade
	 *            债券主体评级
	 */
	public void setSecMainGrade(String secMainGrade) {
		this.secMainGrade = secMainGrade == null ? null : secMainGrade.trim();
	}

	/**
	 * 债券债项评级
	 * 
	 * @return SEC_CREDIT_GRADE 债券债项评级
	 */
	public String getSecCreditGrade() {
		return secCreditGrade;
	}

	/**
	 * 债券债项评级
	 * 
	 * @param secCreditGrade
	 *            债券债项评级
	 */
	public void setSecCreditGrade(String secCreditGrade) {
		this.secCreditGrade = secCreditGrade == null ? null : secCreditGrade.trim();
	}

	/**
	 * 登记托管机构
	 * 
	 * @return REGIST_ORG 登记托管机构
	 */
	public String getRegistOrg() {
		return registOrg;
	}

	/**
	 * 登记托管机构
	 * 
	 * @param registOrg
	 *            登记托管机构
	 */
	public void setRegistOrg(String registOrg) {
		this.registOrg = registOrg == null ? null : registOrg.trim();
	}

	/**
	 * 登记托管机构说明
	 * 
	 * @return REGISTORG_COMMENT 登记托管机构说明
	 */
	public String getRegistorgComment() {
		return registorgComment;
	}

	/**
	 * 登记托管机构说明
	 * 
	 * @param registorgComment
	 *            登记托管机构说明
	 */
	public void setRegistorgComment(String registorgComment) {
		this.registorgComment = registorgComment == null ? null : registorgComment.trim();
	}

	/**
	 * 对手方
	 * 
	 * @return RIVAL_PARTY 对手方
	 */
	public String getRivalParty() {
		return rivalParty;
	}

	/**
	 * 对手方
	 * 
	 * @param rivalParty
	 *            对手方
	 */
	public void setRivalParty(String rivalParty) {
		this.rivalParty = rivalParty == null ? null : rivalParty.trim();
	}

	/**
	 * 回购标的类别
	 * 
	 * @return TARGET_TYPE1 回购标的类别
	 */
	public String getTargetType1() {
		return targetType1;
	}

	/**
	 * 回购标的类别
	 * 
	 * @param targetType1
	 *            回购标的类别
	 */
	public void setTargetType1(String targetType1) {
		this.targetType1 = targetType1 == null ? null : targetType1.trim();
	}

	/**
	 * 回购标的金额
	 * 
	 * @return TARGET_AMT 回购标的金额
	 */
	public BigDecimal getTargetAmt() {
		return targetAmt;
	}

	public String ggetTargetAmtString() {
		return targetAmt==null?"":df2.format(targetAmt);
	}

	/**
	 * 回购标的金额
	 * 
	 * @param targetAmt
	 *            回购标的金额
	 */
	public void setTargetAmt(BigDecimal targetAmt) {
		this.targetAmt = targetAmt;
	}

	/**
	 * 所属国家或地区
	 * 
	 * @return COUNTRY_AREA 所属国家或地区
	 */
	public String getCountryArea() {
		return countryArea;
	}

	/**
	 * 所属国家或地区
	 * 
	 * @param countryArea
	 *            所属国家或地区
	 */
	public void setCountryArea(String countryArea) {
		this.countryArea = countryArea == null ? null : countryArea.trim();
	}

	/**
	 * 收/受益权类型
	 * 
	 * @return PROFIT_TYPE 收/受益权类型
	 */
	public String getProfitType() {
		return profitType;
	}

	/**
	 * 收/受益权类型
	 * 
	 * @param profitType
	 *            收/受益权类型
	 */
	public void setProfitType(String profitType) {
		this.profitType = profitType == null ? null : profitType.trim();
	}

	/**
	 * 是否属于买入返售
	 * 
	 * @return IS_RESALE 是否属于买入返售
	 */
	public String getIsResale() {
		return isResale;
	}

	/**
	 * 是否属于买入返售
	 * 
	 * @param isResale
	 *            是否属于买入返售
	 */
	public void setIsResale(String isResale) {
		this.isResale = isResale == null ? null : isResale.trim();
	}

	/**
	 * 期限（天）
	 * 
	 * @return TERM_DATE 期限（天）
	 */
	public Integer getTermDate() {
		return termDate;
	}

	/**
	 * 期限（天）
	 * 
	 * @param termDate
	 *            期限（天）
	 */
	public void setTermDate(Integer termDate) {
		this.termDate = termDate;
	}

	/**
	 * 是否有预期收益率
	 * 
	 * @return RATE_FLAG 是否有预期收益率
	 */
	public String getRateFlag() {
		return rateFlag;
	}

	/**
	 * 是否有预期收益率
	 * 
	 * @param rateFlag
	 *            是否有预期收益率
	 */
	public void setRateFlag(String rateFlag) {
		this.rateFlag = rateFlag == null ? null : rateFlag.trim();
	}

	/**
	 * 付息频率（个月/次）
	 * 
	 * @return INTPAY_CYCLE 付息频率（个月/次）
	 */
	public Integer getIntpayCycle() {
		return intpayCycle;
	}

	/**
	 * 付息频率（个月/次）
	 * 
	 * @param intpayCycle
	 *            付息频率（个月/次）
	 */
	public void setIntpayCycle(Integer intpayCycle) {
		this.intpayCycle = intpayCycle;
	}

	/**
	 * 还本付息说明
	 * 
	 * @return REPAY_CMT 还本付息说明
	 */
	public String getRepayCmt() {
		return repayCmt;
	}

	/**
	 * 还本付息说明
	 * 
	 * @param repayCmt
	 *            还本付息说明
	 */
	public void setRepayCmt(String repayCmt) {
		this.repayCmt = repayCmt == null ? null : repayCmt.trim();
	}

	/**
	 * 基准利率种类
	 * 
	 * @return BASIS_TYPE 基准利率种类
	 */
	public String getBasisType() {
		return basisType;
	}

	/**
	 * 基准利率种类
	 * 
	 * @param basisType
	 *            基准利率种类
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType == null ? null : basisType.trim();
	}

	/**
	 * 浮动因子
	 * 
	 * @return FLOAT_FACTOR 浮动因子
	 */
	public BigDecimal getFloatFactor() {
		return floatFactor;
	}
	
	public String getFloatFactorString() {
		return floatFactor==null?"":df5.format(floatFactor);
	}

	/**
	 * 浮动因子
	 * 
	 * @param floatFactor
	 *            浮动因子
	 */
	public void setFloatFactor(BigDecimal floatFactor) {
		this.floatFactor = floatFactor;
	}

	/**
	 * 利差
	 * 
	 * @return SPREAD_RATE 利差
	 */
	public BigDecimal getSpreadRate() {
		return spreadRate;
	}
	
	public String getSpreadRateString() {
		return spreadRate==null?"":df5.format(spreadRate);
	}

	/**
	 * 利差
	 * 
	 * @param spreadRate
	 *            利差
	 */
	public void setSpreadRate(BigDecimal spreadRate) {
		this.spreadRate = spreadRate;
	}

	/**
	 * 融资人
	 * 
	 * @return FINANCIE 融资人
	 */
	public String getFinancie() {
		return financie;
	}

	/**
	 * 融资人
	 * 
	 * @param financie
	 *            融资人
	 */
	public void setFinancie(String financie) {
		this.financie = financie == null ? null : financie.trim();
	}

	/**
	 * 融资人组织机构代码
	 * 
	 * @return FIN_ORG_CODE 融资人组织机构代码
	 */
	public String getFinOrgCode() {
		return finOrgCode;
	}

	/**
	 * 融资人组织机构代码
	 * 
	 * @param finOrgCode
	 *            融资人组织机构代码
	 */
	public void setFinOrgCode(String finOrgCode) {
		this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
	}

	/**
	 * 融资人内部信用评级
	 * 
	 * @return FCR_GRADE_IN 融资人内部信用评级
	 */
	public String getFcrGradeIn() {
		return fcrGradeIn;
	}

	/**
	 * 融资人内部信用评级
	 * 
	 * @param fcrGradeIn
	 *            融资人内部信用评级
	 */
	public void setFcrGradeIn(String fcrGradeIn) {
		this.fcrGradeIn = fcrGradeIn == null ? null : fcrGradeIn.trim();
	}

	/**
	 * 外部评级机构名称及融资人评级结果
	 * 
	 * @return FCR_GRADE_OUT 外部评级机构名称及融资人评级结果
	 */
	public String getFcrGradeOut() {
		return fcrGradeOut;
	}

	/**
	 * 外部评级机构名称及融资人评级结果
	 * 
	 * @param fcrGradeOut
	 *            外部评级机构名称及融资人评级结果
	 */
	public void setFcrGradeOut(String fcrGradeOut) {
		this.fcrGradeOut = fcrGradeOut == null ? null : fcrGradeOut.trim();
	}

	/**
	 * 融资人类型（按规模划分）
	 * 
	 * @return FINANCIER_TYPE1 融资人类型（按规模划分）
	 */
	public String getFinancierType1() {
		return financierType1;
	}

	/**
	 * 融资人类型（按规模划分）
	 * 
	 * @param financierType1
	 *            融资人类型（按规模划分）
	 */
	public void setFinancierType1(String financierType1) {
		this.financierType1 = financierType1 == null ? null : financierType1.trim();
	}

	/**
	 * 融资人类型（按技术领域划分）
	 * 
	 * @return FINANCIER_TYPE2 融资人类型（按技术领域划分）
	 */
	public String getFinancierType2() {
		return financierType2;
	}

	/**
	 * 融资人类型（按技术领域划分）
	 * 
	 * @param financierType2
	 *            融资人类型（按技术领域划分）
	 */
	public void setFinancierType2(String financierType2) {
		this.financierType2 = financierType2 == null ? null : financierType2.trim();
	}

	/**
	 * 融资人类型（按经济类型划分）
	 * 
	 * @return FINANCIER_TYPE3 融资人类型（按经济类型划分）
	 */
	public String getFinancierType3() {
		return financierType3;
	}

	/**
	 * 融资人类型（按经济类型划分）
	 * 
	 * @param financierType3
	 *            融资人类型（按经济类型划分）
	 */
	public void setFinancierType3(String financierType3) {
		this.financierType3 = financierType3 == null ? null : financierType3.trim();
	}

	/**
	 * 融资项目
	 * 
	 * @return FINANCE_PROJ 融资项目
	 */
	public String getFinanceProj() {
		return financeProj;
	}

	/**
	 * 融资项目
	 * 
	 * @param financeProj
	 *            融资项目
	 */
	public void setFinanceProj(String financeProj) {
		this.financeProj = financeProj == null ? null : financeProj.trim();
	}

	/**
	 * 融资人所属行业一级分类
	 * 
	 * @return INDUSTRY1 融资人所属行业一级分类
	 */
	public String getIndustry1() {
		return industry1;
	}

	/**
	 * 融资人所属行业一级分类
	 * 
	 * @param industry1
	 *            融资人所属行业一级分类
	 */
	public void setIndustry1(String industry1) {
		this.industry1 = industry1 == null ? null : industry1.trim();
	}

	/**
	 * 融资人所属行业二级分类
	 * 
	 * @return INDUSTRY_LEVEL2_TYPE 融资人所属行业二级分类
	 */
	public String getIndustryLevel2Type() {
		return industryLevel2Type;
	}

	/**
	 * 融资人所属行业二级分类
	 * 
	 * @param industryLevel2Type
	 *            融资人所属行业二级分类
	 */
	public void setIndustryLevel2Type(String industryLevel2Type) {
		this.industryLevel2Type = industryLevel2Type == null ? null : industryLevel2Type.trim();
	}

	/**
	 * 融资项目所属行业一级分类
	 * 
	 * @return PRO_INDUSTRY 融资项目所属行业一级分类
	 */
	public String getProIndustry() {
		return proIndustry;
	}

	/**
	 * 融资项目所属行业一级分类
	 * 
	 * @param proIndustry
	 *            融资项目所属行业一级分类
	 */
	public void setProIndustry(String proIndustry) {
		this.proIndustry = proIndustry == null ? null : proIndustry.trim();
	}

	/**
	 * 融资项目所属行业二级分类
	 * 
	 * @return PRO_INDUSTRY_LEVEL2 融资项目所属行业二级分类
	 */
	public String getProIndustryLevel2() {
		return proIndustryLevel2;
	}

	/**
	 * 融资项目所属行业二级分类
	 * 
	 * @param proIndustryLevel2
	 *            融资项目所属行业二级分类
	 */
	public void setProIndustryLevel2(String proIndustryLevel2) {
		this.proIndustryLevel2 = proIndustryLevel2 == null ? null : proIndustryLevel2.trim();
	}

	/**
	 * 项目是否属于重点监控行业和领域
	 * 
	 * @return INDUSTRY_FLAG 项目是否属于重点监控行业和领域
	 */
	public String getIndustryFlag() {
		return industryFlag;
	}

	/**
	 * 项目是否属于重点监控行业和领域
	 * 
	 * @param industryFlag
	 *            项目是否属于重点监控行业和领域
	 */
	public void setIndustryFlag(String industryFlag) {
		this.industryFlag = industryFlag == null ? null : industryFlag.trim();
	}

	/**
	 * 重点监控行业和领域类别
	 * 
	 * @return MONIT_TYPE 重点监控行业和领域类别
	 */
	public String getMonitType() {
		return monitType;
	}

	/**
	 * 重点监控行业和领域类别
	 * 
	 * @param monitType
	 *            重点监控行业和领域类别
	 */
	public void setMonitType(String monitType) {
		this.monitType = monitType == null ? null : monitType.trim();
	}

	/**
	 * 重点监控行业和领域类别说明
	 * 
	 * @return MON_TYPE_COMM 重点监控行业和领域类别说明
	 */
	public String getMonTypeComm() {
		return monTypeComm;
	}

	/**
	 * 重点监控行业和领域类别说明
	 * 
	 * @param monTypeComm
	 *            重点监控行业和领域类别说明
	 */
	public void setMonTypeComm(String monTypeComm) {
		this.monTypeComm = monTypeComm == null ? null : monTypeComm.trim();
	}

	/**
	 * 担保方式
	 * 
	 * @return GUA_TYPE 担保方式
	 */
	public String getGuaType() {
		return guaType;
	}

	/**
	 * 担保方式
	 * 
	 * @param guaType
	 *            担保方式
	 */
	public void setGuaType(String guaType) {
		this.guaType = guaType == null ? null : guaType.trim();
	}

	/**
	 * 担保方式说明
	 * 
	 * @return GUA_TYPE_REMARK 担保方式说明
	 */
	public String getGuaTypeRemark() {
		return guaTypeRemark;
	}


    /**
     * 起息日
     * 
     * @return VDATE 起息日
     */
    public String getGuaTypeRemark08() {
        if (!StringUtil.isBlank(getAssetType())) {
            if ("08".equals(getAssetType())) {
                return guaTypeRemark;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    /**
     * 起息日
     * 
     * @return VDATE 起息日
     */
    public String getGuaTypeRemark04() {
        if (!StringUtil.isBlank(getAssetType())) {
            if ("04".equals(getAssetType())) {
                return guaTypeRemark;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
	/**
	 * 担保方式说明
	 * 
	 * @param guaTypeRemark
	 *            担保方式说明
	 */
	public void setGuaTypeRemark(String guaTypeRemark) {
		this.guaTypeRemark = guaTypeRemark == null ? null : guaTypeRemark.trim();
	}

	/**
	 * 融资人主体评级
	 * 
	 * @return FINANCIER_GRADE 融资人主体评级
	 */
	public String getFinancierGrade() {
		return financierGrade;
	}

	/**
	 * 融资人主体评级
	 * 
	 * @param financierGrade
	 *            融资人主体评级
	 */
	public void setFinancierGrade(String financierGrade) {
		this.financierGrade = financierGrade == null ? null : financierGrade.trim();
	}

	/**
	 * 资产内部评级
	 * 
	 * @return IN_GRADE 资产内部评级
	 */
	public String getInGrade() {
		return inGrade;
	}

	/**
	 * 资产内部评级
	 * 
	 * @param inGrade
	 *            资产内部评级
	 */
	public void setInGrade(String inGrade) {
		this.inGrade = inGrade == null ? null : inGrade.trim();
	}

	/**
	 * 资产外部评级
	 * 
	 * @return OUT_GRADE 资产外部评级
	 */
	public String getOutGrade() {
		return outGrade;
	}

	/**
	 * 资产外部评级
	 * 
	 * @param outGrade
	 *            资产外部评级
	 */
	public void setOutGrade(String outGrade) {
		this.outGrade = outGrade == null ? null : outGrade.trim();
	}

	/**
	 * 选择权
	 * 
	 * @return CHOSE 选择权
	 */
	public String getChose() {
		return chose;
	}

	/**
	 * 选择权
	 * 
	 * @param chose
	 *            选择权
	 */
	public void setChose(String chose) {
		this.chose = chose == null ? null : chose.trim();
	}

	/**
	 * 行权条件说明
	 * 
	 * @return CONDITION_CMM 行权条件说明
	 */
	public String getConditionCmm() {
		return conditionCmm;
	}

	/**
	 * 行权条件说明
	 * 
	 * @param conditionCmm
	 *            行权条件说明
	 */
	public void setConditionCmm(String conditionCmm) {
		this.conditionCmm = conditionCmm == null ? null : conditionCmm.trim();
	}

	/**
	 * 融资人所属地区
	 * 
	 * @return FIN_AREA 融资人所属地区
	 */
	public String getFinArea() {
		return finArea;
	}

	/**
	 * 融资人所属地区
	 * 
	 * @param finArea
	 *            融资人所属地区
	 */
	public void setFinArea(String finArea) {
		this.finArea = finArea == null ? null : finArea.trim();
	}

	/**
	 * 融资总费率
	 * 
	 * @return TOTAL_FEE_RATE 融资总费率
	 */
	public BigDecimal getTotalFeeRate() {
		return totalFeeRate;
	}
	
	public String getTotalFeeRateString() {
		return totalFeeRate==null?"":df5.format(totalFeeRate);
	}

	/**
	 * 融资总费率
	 * 
	 * @param totalFeeRate
	 *            融资总费率
	 */
	public void setTotalFeeRate(BigDecimal totalFeeRate) {
		this.totalFeeRate = totalFeeRate;
	}

	/**
	 * 是否为收/受益权
	 * 
	 * @return IS_RIGHT 是否为收/受益权
	 */
	public String getIsRight() {
		return isRight;
	}

	/**
	 * 是否为收/受益权
	 * 
	 * @param isRight
	 *            是否为收/受益权
	 */
	public void setIsRight(String isRight) {
		this.isRight = isRight == null ? null : isRight.trim();
	}

	/**
	 * 非标准化债券类资产类型
	 * 
	 * @return NOTSTAND_SEC_TYPE 非标准化债券类资产类型
	 */
	public String getNotstandSecType() {
		return notstandSecType;
	}

	/**
	 * 非标准化债券类资产类型
	 * 
	 * @param notstandSecType
	 *            非标准化债券类资产类型
	 */
	public void setNotstandSecType(String notstandSecType) {
		this.notstandSecType = notstandSecType == null ? null : notstandSecType.trim();
	}

	/**
	 * 非标准化债券类资产数量
	 * 
	 * @return NOTSTAND_SEC_NUM 非标准化债券类资产数量
	 */
	public Integer getNotstandSecNum() {
		return notstandSecNum;
	}

	/**
	 * 非标准化债券类资产数量
	 * 
	 * @param notstandSecNum
	 *            非标准化债券类资产数量
	 */
	public void setNotstandSecNum(Integer notstandSecNum) {
		this.notstandSecNum = notstandSecNum;
	}

	/**
	 * 加权剩余期限(天)
	 * 
	 * @return REMAIN_TERM 加权剩余期限(天)
	 */
	public BigDecimal getRemainTerm() {
		return remainTerm;
	}
	public String getRemainTermString() {
		return remainTerm==null?"":df2.format(remainTerm);
	}

	/**
	 * 加权剩余期限(天)
	 * 
	 * @param remainTerm
	 *            加权剩余期限(天)
	 */
	public void setRemainTerm(BigDecimal remainTerm) {
		this.remainTerm = remainTerm;
	}

	/**
	 * 最长剩余期限(天)
	 * 
	 * @return MAX_REMAIN_TERM 最长剩余期限(天)
	 */
	public Integer getMaxRemainTerm() {
		return maxRemainTerm;
	}

	/**
	 * 最长剩余期限(天)
	 * 
	 * @param maxRemainTerm
	 *            最长剩余期限(天)
	 */
	public void setMaxRemainTerm(Integer maxRemainTerm) {
		this.maxRemainTerm = maxRemainTerm;
	}

	/**
	 * 最短剩余期限(天)
	 * 
	 * @return MIN_REMAIN_TERM 最短剩余期限(天)
	 */
	public Integer getMinRemainTerm() {
		return minRemainTerm;
	}

	/**
	 * 最短剩余期限(天)
	 * 
	 * @param minRemainTerm
	 *            最短剩余期限(天)
	 */
	public void setMinRemainTerm(Integer minRemainTerm) {
		this.minRemainTerm = minRemainTerm;
	}

	/**
	 * 股票类型
	 * 
	 * @return SHARES_TYPE 股票类型
	 */
	public String getSharesType() {
		return sharesType;
	}

	/**
	 * 股票类型
	 * 
	 * @param sharesType
	 *            股票类型
	 */
	public void setSharesType(String sharesType) {
		this.sharesType = sharesType == null ? null : sharesType.trim();
	}

	/**
	 * 行业一级分类
	 * 
	 * @return INDUSTRY_SHARES 行业一级分类
	 */
	public String getIndustryShares() {
		return industryShares;
	}

	/**
	 * 行业一级分类
	 * 
	 * @param industryShares
	 *            行业一级分类
	 */
	public void setIndustryShares(String industryShares) {
		this.industryShares = industryShares == null ? null : industryShares.trim();
	}

	/**
	 * 行业二级分类
	 * 
	 * @return INDUSTRY_SHARES2 行业二级分类
	 */
	public String getIndustryShares2() {
		return industryShares2;
	}

	/**
	 * 行业二级分类
	 * 
	 * @param industryShares2
	 *            行业二级分类
	 */
	public void setIndustryShares2(String industryShares2) {
		this.industryShares2 = industryShares2 == null ? null : industryShares2.trim();
	}

	/**
	 * 投资阶段
	 * 
	 * @return INVEST_STAGE 投资阶段
	 */
	public String getInvestStage() {
		return investStage;
	}

	/**
	 * 投资阶段
	 * 
	 * @param investStage
	 *            投资阶段
	 */
	public void setInvestStage(String investStage) {
		this.investStage = investStage == null ? null : investStage.trim();
	}

	/**
	 * 投资企业类型（按规模划分）
	 * 
	 * @return SHARES_TYPE1 投资企业类型（按规模划分）
	 */
	public String getSharesType1() {
		return sharesType1;
	}

	/**
	 * 投资企业类型（按规模划分）
	 * 
	 * @param sharesType1
	 *            投资企业类型（按规模划分）
	 */
	public void setSharesType1(String sharesType1) {
		this.sharesType1 = sharesType1 == null ? null : sharesType1.trim();
	}

	/**
	 * 投资企业类型（按经济类型划分）
	 * 
	 * @return SHARES_TYPE2 投资企业类型（按经济类型划分）
	 */
	public String getSharesType2() {
		return sharesType2;
	}

	/**
	 * 投资企业类型（按经济类型划分）
	 * 
	 * @param sharesType2
	 *            投资企业类型（按经济类型划分）
	 */
	public void setSharesType2(String sharesType2) {
		this.sharesType2 = sharesType2 == null ? null : sharesType2.trim();
	}

	/**
	 * 投资企业类型（按技术领域划分）
	 * 
	 * @return SHARES_TYPE3 投资企业类型（按技术领域划分）
	 */
	public String getSharesType3() {
		return sharesType3;
	}

	/**
	 * 投资企业类型（按技术领域划分）
	 * 
	 * @param sharesType3
	 *            投资企业类型（按技术领域划分）
	 */
	public void setSharesType3(String sharesType3) {
		this.sharesType3 = sharesType3 == null ? null : sharesType3.trim();
	}

	/**
	 * 是否为质押融资
	 * 
	 * @return IS_PLEDGE 是否为质押融资
	 */
	public String getIsPledge() {
		return isPledge;
	}

	/**
	 * 是否为质押融资
	 * 
	 * @param isPledge
	 *            是否为质押融资
	 */
	public void setIsPledge(String isPledge) {
		this.isPledge = isPledge == null ? null : isPledge.trim();
	}

	/**
	 * 名义本金
	 * 
	 * @return NOM_CAPI 名义本金
	 */
	public BigDecimal getNomCapi() {
		return nomCapi;
	}

	/**
	 * 名义本金
	 * 
	 * @param nomCapi
	 *            名义本金
	 */
	public void setNomCapi(BigDecimal nomCapi) {
		this.nomCapi = nomCapi;
	}

	/**
	 * 标的类别
	 * 
	 * @return TARGET_TYPE3 标的类别
	 */
	public String getTargetType3() {
		return targetType3;
	}

	/**
	 * 标的类别
	 * 
	 * @param targetType3
	 *            标的类别
	 */
	public void setTargetType3(String targetType3) {
		this.targetType3 = targetType3 == null ? null : targetType3.trim();
	}

	/**
	 * 发行机构代码
	 * 
	 * @return ISSUE_PARTY_ID 发行机构代码
	 */
	public String getIssuePartyId() {
		return issuePartyId;
	}

	/**
	 * 发行机构代码
	 * 
	 * @param issuePartyId
	 *            发行机构代码
	 */
	public void setIssuePartyId(String issuePartyId) {
		this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
	}

	/**
	 * 期限（月）
	 * 
	 * @return TERM_MONTH 期限（月）
	 */
	public Integer getTermMonth() {
		return termMonth;
	}

	/**
	 * 期限（月）
	 * 
	 * @param termMonth
	 *            期限（月）
	 */
	public void setTermMonth(Integer termMonth) {
		this.termMonth = termMonth;
	}

	/**
	 * 担保情况说明
	 * 
	 * @return GUARANTEE_CMT 担保情况说明
	 */
	public String getGuaranteeCmt() {
		return guaranteeCmt;
	}

	/**
	 * 担保情况说明
	 * 
	 * @param guaranteeCmt
	 *            担保情况说明
	 */
	public void setGuaranteeCmt(String guaranteeCmt) {
		this.guaranteeCmt = guaranteeCmt == null ? null : guaranteeCmt.trim();
	}

	/**
	 * 是否含权等特条款情况说明
	 * 
	 * @return SPECIAL_CMT 是否含权等特条款情况说明
	 */
	public String getSpecialCmt() {
		return specialCmt;
	}

	/**
	 * 是否含权等特条款情况说明
	 * 
	 * @param specialCmt
	 *            是否含权等特条款情况说明
	 */
	public void setSpecialCmt(String specialCmt) {
		this.specialCmt = specialCmt == null ? null : specialCmt.trim();
	}

	/**
	 * 合约名称
	 * 
	 * @return CONTRACT_NAME 合约名称
	 */
	public String getContractName() {
		return contractName;
	}

	/**
	 * 合约名称
	 * 
	 * @param contractName
	 *            合约名称
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName == null ? null : contractName.trim();
	}

	/**
	 * 固定收益部分所占比例
	 * 
	 * @return INCOME_RATIO 固定收益部分所占比例
	 */
	public BigDecimal getIncomeRatio() {
		return incomeRatio;
	}
	
	public String getIncomeRatioString() {
		return incomeRatio==null?"":df5.format(incomeRatio);
	}

	/**
	 * 固定收益部分所占比例
	 * 
	 * @param incomeRatio
	 *            固定收益部分所占比例
	 */
	public void setIncomeRatio(BigDecimal incomeRatio) {
		this.incomeRatio = incomeRatio;
	}

	/**
	 * 衍生金融工具所占比例
	 * 
	 * @return DER_INSTR_RATIO 衍生金融工具所占比例
	 */
	public BigDecimal getDerInstrRatio() {
		return derInstrRatio;
	}
	public String getDerInstrRatioString() {
		return derInstrRatio==null?"":df5.format(derInstrRatio);
	}

	/**
	 * 衍生金融工具所占比例
	 * 
	 * @param derInstrRatio
	 *            衍生金融工具所占比例
	 */
	public void setDerInstrRatio(BigDecimal derInstrRatio) {
		this.derInstrRatio = derInstrRatio;
	}

	/**
	 * 衍生金融工具具体投资方式
	 * 
	 * @return DER_INSTR_INV_TYPE 衍生金融工具具体投资方式
	 */
	public String getDerInstrInvType() {
		return derInstrInvType;
	}

	/**
	 * 衍生金融工具具体投资方式
	 * 
	 * @param derInstrInvType
	 *            衍生金融工具具体投资方式
	 */
	public void setDerInstrInvType(String derInstrInvType) {
		this.derInstrInvType = derInstrInvType == null ? null : derInstrInvType.trim();
	}

	/**
	 * 衍生金融工具挂钩标的资产
	 * 
	 * @return DER_INSTR_TARGET 衍生金融工具挂钩标的资产
	 */
	public String getDerInstrTarget() {
		return derInstrTarget;
	}

	/**
	 * 衍生金融工具挂钩标的资产
	 * 
	 * @param derInstrTarget
	 *            衍生金融工具挂钩标的资产
	 */
	public void setDerInstrTarget(String derInstrTarget) {
		this.derInstrTarget = derInstrTarget == null ? null : derInstrTarget.trim();
	}

	/**
	 * 利息结算方式
	 * 
	 * @return ACCT_MODE 利息结算方式
	 */
	public String getAcctMode() {
		return acctMode;
	}

	/**
	 * 利息结算方式
	 * 
	 * @param acctMode
	 *            利息结算方式
	 */
	public void setAcctMode(String acctMode) {
		this.acctMode = acctMode == null ? null : acctMode.trim();
	}

	/**
	 * 含权情况说明
	 * 
	 * @return SPECIAL_CMT1 含权情况说明
	 */
	public String getSpecialCmt1() {
		return specialCmt1;
	}

	/**
	 * 含权情况说明
	 * 
	 * @param specialCmt1
	 *            含权情况说明
	 */
	public void setSpecialCmt1(String specialCmt1) {
		this.specialCmt1 = specialCmt1 == null ? null : specialCmt1.trim();
	}

	/**
	 * 结构性票据最高收益率
	 * 
	 * @return MAX_YIELD 结构性票据最高收益率
	 */
	public BigDecimal getMaxYield() {
		return maxYield;
	}
	
	public String getMaxYieldString() {
		return maxYield==null?"":df5.format(maxYield);
	}

	/**
	 * 结构性票据最高收益率
	 * 
	 * @param maxYield
	 *            结构性票据最高收益率
	 */
	public void setMaxYield(BigDecimal maxYield) {
		this.maxYield = maxYield;
	}

	/**
	 * 结构性票据最低收益率
	 * 
	 * @return MIN_YIELD 结构性票据最低收益率
	 */
	public BigDecimal getMinYield() {
		return minYield;
	}
	public String getMinYieldString() {
		return minYield==null?"":df5.format(minYield);
	}

	/**
	 * 结构性票据最低收益率
	 * 
	 * @param minYield
	 *            结构性票据最低收益率
	 */
	public void setMinYield(BigDecimal minYield) {
		this.minYield = minYield;
	}

	/**
	 * 挂钩标的资产基准价格
	 * 
	 * @return BASIC_PRICE 挂钩标的资产基准价格
	 */
	public BigDecimal getBasicPrice() {
		return basicPrice;
	}

	/**
	 * 挂钩标的资产基准价格
	 * 
	 * @param basicPrice
	 *            挂钩标的资产基准价格
	 */
	public void setBasicPrice(BigDecimal basicPrice) {
		this.basicPrice = basicPrice;
	}

	/**
	 * 挂钩标的资产登记日价格
	 * 
	 * @return REG_DATE_PRICE 挂钩标的资产登记日价格
	 */
	public BigDecimal getRegDatePrice() {
		return regDatePrice;
	}

	/**
	 * 挂钩标的资产登记日价格
	 * 
	 * @param regDatePrice
	 *            挂钩标的资产登记日价格
	 */
	public void setRegDatePrice(BigDecimal regDatePrice) {
		this.regDatePrice = regDatePrice;
	}

	/**
	 * 交易费
	 * 
	 * @return TRADE_FEE 交易费
	 */
	public String getTradeFee() {
		/*return tradeFee==null?"":df2.format(new  BigDecimal(tradeFee));*/
		return tradeFee;
	}

	/**
	 * 交易费
	 * 
	 * @param tradeFee
	 *            交易费
	 */
	public void setTradeFee(String tradeFee) {
		this.tradeFee = tradeFee == null ? null : tradeFee.trim();
	}

	/**
	 * 资管计划发起人机构编码
	 * 
	 * @return PARTY_CODE 资管计划发起人机构编码
	 */
	public String getPartyCode() {
		return partyCode;
	}

	/**
	 * 资管计划发起人机构编码
	 * 
	 * @param partyCode
	 *            资管计划发起人机构编码
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode == null ? null : partyCode.trim();
	}

	/**
	 * 资管计划登记编码
	 * 
	 * @return CHECK_CODE 资管计划登记编码
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * 资管计划登记编码
	 * 
	 * @param checkCode
	 *            资管计划登记编码
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode == null ? null : checkCode.trim();
	}

	/**
	 * 管理人
	 * 
	 * @return MANAGER_ID 管理人
	 */
	public String getManagerId() {
		return managerId;
	}

	/**
	 * 管理人
	 * 
	 * @param managerId
	 *            管理人
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId == null ? null : managerId.trim();
	}

	/**
	 * 托管人
	 * 
	 * @return TRUSTEE_ID 托管人
	 */
	public String getTrusteeId() {
		return trusteeId;
	}

	/**
	 * 托管人
	 * 
	 * @param trusteeId
	 *            托管人
	 */
	public void setTrusteeId(String trusteeId) {
		this.trusteeId = trusteeId == null ? null : trusteeId.trim();
	}

	/**
	 * 资金实际投向
	 * 
	 * @return TRU_ACT_TYPE 资金实际投向
	 */
	public String getTruActType() {
		return truActType;
	}

	/**
	 * 资金实际投向
	 * 
	 * @param truActType
	 *            资金实际投向
	 */
	public void setTruActType(String truActType) {
		this.truActType = truActType == null ? null : truActType.trim();
	}

	/**
	 * 资金运用方式
	 * 
	 * @return INVEST_TYPE 资金运用方式
	 */
	public String getInvestType() {
		return investType;
	}

	/**
	 * 资金运用方式
	 * 
	 * @param investType
	 *            资金运用方式
	 */
	public void setInvestType(String investType) {
		this.investType = investType == null ? null : investType.trim();
	}

	/**
	 * 资金运用行业
	 * 
	 * @return CAP_USE_INDU 资金运用行业
	 */
	public String getCapUseIndu() {
		return capUseIndu;
	}

	/**
	 * 资金运用行业
	 * 
	 * @param capUseIndu
	 *            资金运用行业
	 */
	public void setCapUseIndu(String capUseIndu) {
		this.capUseIndu = capUseIndu == null ? null : capUseIndu.trim();
	}

	/**
	 * 资管计划成立日期
	 * 
	 * @return PLAN_SDATE 资管计划成立日期
	 */
	public Date getPlanSdate() {
		return planSdate;
	}

	/**
	 * 资管计划成立日期
	 * 
	 * @param planSdate
	 *            资管计划成立日期
	 */
	public void setPlanSdate(Date planSdate) {
		this.planSdate = planSdate;
	}

	/**
	 * 资管计划终止日期
	 * 
	 * @return PLAN_EDATE 资管计划终止日期
	 */
	public Date getPlanEdate() {
		return planEdate;
	}

	/**
	 * 资管计划终止日期
	 * 
	 * @param planEdate
	 *            资管计划终止日期
	 */
	public void setPlanEdate(Date planEdate) {
		this.planEdate = planEdate;
	}

	/**
	 * 资管计划属性/委外投资属性
	 * 
	 * @return PRO_PROPERTY 资管计划属性/委外投资属性
	 */
	public String getProProperty() {
		return proProperty;
	}

	/**
	 * 资管计划属性/委外投资属性
	 * 
	 * @param proProperty
	 *            资管计划属性/委外投资属性
	 */
	public void setProProperty(String proProperty) {
		this.proProperty = proProperty == null ? null : proProperty.trim();
	}

	/**
	 * 最高预期收益率
	 * 
	 * @return MAX_RATE 最高预期收益率
	 */
	public BigDecimal getMaxRate() {
		return maxRate;
	}

	public String getMaxRateString() {
		return maxRate==null?"":df5.format(maxRate);
	}
	/**
	 * 最高预期收益率
	 * 
	 * @param maxRate
	 *            最高预期收益率
	 */
	public void setMaxRate(BigDecimal maxRate) {
		this.maxRate = maxRate;
	}

	/**
	 * 最低预期收益率
	 * 
	 * @return MIN_RATE 最低预期收益率
	 */
	public BigDecimal getMinRate() {
		return minRate;
	}

	public String getMinRateString() {
		return minRate==null?"":df5.format(minRate);
	}

	/**
	 * 最低预期收益率
	 * 
	 * @param minRate
	 *            最低预期收益率
	 */
	public void setMinRate(BigDecimal minRate) {
		this.minRate = minRate;
	}

	/**
	 * 负债规模
	 * 
	 * @return DEBT_SCALE 负债规模
	 */
	public BigDecimal getDebtScale() {
		return debtScale;
	}

	/**
	 * 负债规模
	 * 
	 * @param debtScale
	 *            负债规模
	 */
	public void setDebtScale(BigDecimal debtScale) {
		this.debtScale = debtScale;
	}

	/**
	 * 购买结构
	 * 
	 * @return BUY_STRUCT 购买结构
	 */
	public String getBuyStruct() {
		return buyStruct;
	}

	/**
	 * 购买结构
	 * 
	 * @param buyStruct
	 *            购买结构
	 */
	public void setBuyStruct(String buyStruct) {
		this.buyStruct = buyStruct == null ? null : buyStruct.trim();
	}

	/**
	 * 管理方式
	 * 
	 * @return MANAGE_TYPE 管理方式
	 */
	public String getManageType() {
		return manageType;
	}

	/**
	 * 管理方式
	 * 
	 * @param manageType
	 *            管理方式
	 */
	public void setManageType(String manageType) {
		this.manageType = manageType == null ? null : manageType.trim();
	}

	/**
	 * 是否掌控计划底层资产详情
	 * 
	 * @return IS_CTRL_BOTTOM 是否掌控计划底层资产详情
	 */
	public String getIsCtrlBottom() {
		return isCtrlBottom;
	}

	/**
	 * 是否掌控计划底层资产详情
	 * 
	 * @param isCtrlBottom
	 *            是否掌控计划底层资产详情
	 */
	public void setIsCtrlBottom(String isCtrlBottom) {
		this.isCtrlBottom = isCtrlBottom == null ? null : isCtrlBottom.trim();
	}

	/**
	 * 管理费率
	 * 
	 * @return MGR_FEE_RATE 管理费率
	 */
	public BigDecimal getMgrFeeRate() {
		return mgrFeeRate;
	}

	public String getMgrFeeRateString() {
		return mgrFeeRate==null?"":df5.format(mgrFeeRate);
	}

	/**
	 * 管理费率
	 * 
	 * @param mgrFeeRate
	 *            管理费率
	 */
	public void setMgrFeeRate(BigDecimal mgrFeeRate) {
		this.mgrFeeRate = mgrFeeRate;
	}

	/**
	 * 托管费率
	 * 
	 * @return TRU_FEE_RATE 托管费率
	 */
	public BigDecimal getTruFeeRate() {
		return truFeeRate;
	}

	public String getTruFeeRateString() {
		return truFeeRate==null?"":df5.format(truFeeRate);
	}

	/**
	 * 托管费率
	 * 
	 * @param truFeeRate
	 *            托管费率
	 */
	public void setTruFeeRate(BigDecimal truFeeRate) {
		this.truFeeRate = truFeeRate;
	}

	/**
	 * 交易相关合计费率
	 * 
	 * @return TRADE_FEE_RATE 交易相关合计费率
	 */
	public BigDecimal getTradeFeeRate() {
		return tradeFeeRate;
	}

	public String getTradeFeeRateString() {
		return tradeFeeRate==null?"":df5.format(tradeFeeRate);
	}

	/**
	 * 交易相关合计费率
	 * 
	 * @param tradeFeeRate
	 *            交易相关合计费率
	 */
	public void setTradeFeeRate(BigDecimal tradeFeeRate) {
		this.tradeFeeRate = tradeFeeRate;
	}

	/**
	 * 中介服务机构合计费率
	 * 
	 * @return AGENCY_FEE_RATE 中介服务机构合计费率
	 */
	public BigDecimal getAgencyFeeRate() {
		return agencyFeeRate;
	}

	public String getAgencyFeeRateString() {
		return agencyFeeRate==null?"":df5.format(agencyFeeRate);
	}

	/**
	 * 中介服务机构合计费率
	 * 
	 * @param agencyFeeRate
	 *            中介服务机构合计费率
	 */
	public void setAgencyFeeRate(BigDecimal agencyFeeRate) {
		this.agencyFeeRate = agencyFeeRate;
	}

	/**
	 * 其他合计费率
	 * 
	 * @return OTHER_FEE_RATE 其他合计费率
	 */
	public BigDecimal getOtherFeeRate() {
		return otherFeeRate;
	}

	public String getOtherFeeRateString() {
		return otherFeeRate==null?"":df5.format(otherFeeRate);
	}

	/**
	 * 其他合计费率
	 * 
	 * @param otherFeeRate
	 *            其他合计费率
	 */
	public void setOtherFeeRate(BigDecimal otherFeeRate) {
		this.otherFeeRate = otherFeeRate;
	}

	/**
	 * 资产价值
	 * 
	 * @return ASSET_VALUE 资产价值
	 */
	public BigDecimal getAssetValue() {
		return assetValue;
	}

	/**
	 * 资产价值
	 * 
	 * @param assetValue
	 *            资产价值
	 */
	public void setAssetValue(BigDecimal assetValue) {
		this.assetValue = assetValue;
	}

	/**
	 * 登记备案机构
	 * 
	 * @return REGIST_ORG1 登记备案机构
	 */
	public String getRegistOrg1() {
		return registOrg1;
	}

	/**
	 * 登记备案机构
	 * 
	 * @param registOrg1
	 *            登记备案机构
	 */
	public void setRegistOrg1(String registOrg1) {
		this.registOrg1 = registOrg1 == null ? null : registOrg1.trim();
	}

	/**
	 * 基金管理机构名称
	 * 
	 * @return MNG_ORG_NAME 基金管理机构名称
	 */
	public String getMngOrgName() {
		return mngOrgName;
	}

	/**
	 * 基金管理机构名称
	 * 
	 * @param mngOrgName
	 *            基金管理机构名称
	 */
	public void setMngOrgName(String mngOrgName) {
		this.mngOrgName = mngOrgName == null ? null : mngOrgName.trim();
	}

	/**
	 * 基金托管机构名称
	 * 
	 * @return TRU_ORG_NAME 基金托管机构名称
	 */
	public String getTruOrgName() {
		return truOrgName;
	}

	/**
	 * 基金托管机构名称
	 * 
	 * @param truOrgName
	 *            基金托管机构名称
	 */
	public void setTruOrgName(String truOrgName) {
		this.truOrgName = truOrgName == null ? null : truOrgName.trim();
	}

	/**
	 * 基金投资资产
	 * 
	 * @return FUND_INVEST_ASSET 基金投资资产
	 */
	public String getFundInvestAsset() {
		return fundInvestAsset;
	}

	/**
	 * 基金投资资产
	 * 
	 * @param fundInvestAsset
	 *            基金投资资产
	 */
	public void setFundInvestAsset(String fundInvestAsset) {
		this.fundInvestAsset = fundInvestAsset == null ? null : fundInvestAsset.trim();
	}

	/**
	 * 行内资产类别
	 * 
	 * @return IN_ASSET_TYPE 行内资产类别
	 */
	public String getInAssetType() {
		return inAssetType;
	}

	/**
	 * 行内资产类别
	 * 
	 * @param inAssetType
	 *            行内资产类别
	 */
	public void setInAssetType(String inAssetType) {
		this.inAssetType = inAssetType == null ? null : inAssetType.trim();
	}

	/**
	 * 行内资产类别说明
	 * 
	 * @return IN_ASSET_TYPE_CMM 行内资产类别说明
	 */
	public String getInAssetTypeCmm() {
		return inAssetTypeCmm;
	}

	/**
	 * 行内资产类别说明
	 * 
	 * @param inAssetTypeCmm
	 *            行内资产类别说明
	 */
	public void setInAssetTypeCmm(String inAssetTypeCmm) {
		this.inAssetTypeCmm = inAssetTypeCmm == null ? null : inAssetTypeCmm.trim();
	}

	/**
	 * 备注
	 * 
	 * @return REMARK 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 是否底层资产
	 * 
	 * @return IS_UNDER_ASSET 是否底层资产
	 */
	public String getIsUnderAsset() {
		return isUnderAsset;
	}

	/**
	 * 是否底层资产
	 * 
	 * @param isUnderAsset
	 *            是否底层资产
	 */
	public void setIsUnderAsset(String isUnderAsset) {
		this.isUnderAsset = isUnderAsset == null ? null : isUnderAsset.trim();
	}

	/**
	 * 是否属于政府投资基金
	 * 
	 * @return IS_GOVERN_FUND 是否属于政府投资基金
	 */
	public String getIsGovernFund() {
		return isGovernFund;
	}

	/**
	 * 是否属于政府投资基金
	 * 
	 * @param isGovernFund
	 *            是否属于政府投资基金
	 */
	public void setIsGovernFund(String isGovernFund) {
		this.isGovernFund = isGovernFund == null ? null : isGovernFund.trim();
	}

	/**
	 * 政府投资基金投向
	 * 
	 * @return GOVERN_INVEST_FUND 政府投资基金投向
	 */
	public String getGovernInvestFund() {
		return governInvestFund;
	}

	/**
	 * 政府投资基金投向
	 * 
	 * @param governInvestFund
	 *            政府投资基金投向
	 */
	public void setGovernInvestFund(String governInvestFund) {
		this.governInvestFund = governInvestFund == null ? null : governInvestFund.trim();
	}

	/**
	 * 基金公司名称
	 * 
	 * @return FUND_ENPE_NAME 基金公司名称
	 */
	public String getFundEnpeName() {
		return fundEnpeName;
	}

	/**
	 * 基金公司名称
	 * 
	 * @param fundEnpeName
	 *            基金公司名称
	 */
	public void setFundEnpeName(String fundEnpeName) {
		this.fundEnpeName = fundEnpeName == null ? null : fundEnpeName.trim();
	}

	/**
	 * 受托人
	 * 
	 * @return TRUSTEE 受托人
	 */
	public String getTrustee() {
		return trustee;
	}

	/**
	 * 受托人
	 * 
	 * @param trustee
	 *            受托人
	 */
	public void setTrustee(String trustee) {
		this.trustee = trustee == null ? null : trustee.trim();
	}

	/**
	 * 行内报送截止日
	 * 
	 * @return SUBMIT_DEADLINE 行内报送截止日
	 */
	public Date getSubmitDeadline() {
		return submitDeadline;
	}

	/**
	 * 行内报送截止日
	 * 
	 * @param submitDeadline
	 *            行内报送截止日
	 */
	public void setSubmitDeadline(Date submitDeadline) {
		this.submitDeadline = submitDeadline;
	}

	/**
	 * 中债报送截止日
	 * 
	 * @return CHB_SUBMIT_DEADLINE 中债报送截止日
	 */
	public Date getChbSubmitDeadline() {
		return chbSubmitDeadline;
	}

	/**
	 * 中债报送截止日
	 * 
	 * @param chbSubmitDeadline
	 *            中债报送截止日
	 */
	public void setChbSubmitDeadline(Date chbSubmitDeadline) {
		this.chbSubmitDeadline = chbSubmitDeadline;
	}

	/**
	 * 报送日期
	 * 
	 * @return SUBMIT_TIME 报送日期
	 */
	public Date getSubmitTime() {
		return submitTime;
	}

	/**
	 * 报送日期
	 * 
	 * @param submitTime
	 *            报送日期
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSumitSeq() {
		return sumitSeq;
	}

	public void setSumitSeq(String sumitSeq) {
		this.sumitSeq = sumitSeq;
	}

	/**
	 * 数据日期
	 * 
	 * @return STATUS_DATE 数据日期
	 */
	public Date getStatusDate() {
		return statusDate;
	}

	/**
	 * 数据日期
	 * 
	 * @param statusDate
	 *            数据日期
	 */
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	/**
	 * 数据状态
	 * 
	 * @return STATUS 数据状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 数据状态
	 * 
	 * @param status
	 *            数据状态
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * 创建人
	 * 
	 * @return CREATE_USER 创建人
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 创建人
	 * 
	 * @param createUser
	 *            创建人
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	/**
	 * 创建时间
	 * 
	 * @return CREATE_TIME 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建时间
	 * 
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 更新人
	 * 
	 * @return UPDATE_USER 更新人
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * 更新人
	 * 
	 * @param updateUser
	 *            更新人
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser == null ? null : updateUser.trim();
	}

	/**
	 * 更新时间
	 * 
	 * @return UPDATE_TI 更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 更新时间
	 * 
	 * @param updateTime
	 *            更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 机构代码
	 * 
	 * @return ORG_CODE 机构代码
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 机构代码
	 * 
	 * @param orgCode
	 *            机构代码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode == null ? null : orgCode.trim();
	}

	/**
	 * 部门代码
	 * 
	 * @return DEPT_CODE 部门代码
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 部门代码
	 * 
	 * @param deptCode
	 *            部门代码
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode == null ? null : deptCode.trim();
	}

	/**
	 * 报送状态
	 * 
	 * @return SEND_STATUS 报送状态
	 */
	public String getSendStatus() {
		return sendStatus;
	}

	/**
	 * 报送状态
	 * 
	 * @param sendStatus
	 *            报送状态
	 */
	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus == null ? null : sendStatus.trim();
	}

	/**
	 * 报送反馈状态
	 * 
	 * @return SUBMIT_FEEDBACK_STATUS 报送反馈状态
	 */
	public String getSubmitFeedbackStatus() {
		return submitFeedbackStatus;
	}

	/**
	 * 报送反馈状态
	 * 
	 * @param submitFeedbackStatus
	 *            报送反馈状态
	 */
	public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
		this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
	}

	/**
	 * 创建部门
	 * 
	 * @return CREATE_DEPT 创建部门
	 */
	public String getCreateDept() {
		return createDept;
	}

	/**
	 * 创建部门
	 * 
	 * @param createDept
	 *            创建部门
	 */
	public void setCreateDept(String createDept) {
		this.createDept = createDept == null ? null : createDept.trim();
	}

	/**
	 * 数据来源
	 * 
	 * @return DATA_SOURCE 数据来源
	 */
	public String getDataSource() {
		return dataSource;
	}

	/**
	 * 数据来源
	 * 
	 * @param dataSource
	 *            数据来源
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource == null ? null : dataSource.trim();
	}

	/**
	 * 删除原因
	 * 
	 * @return DELETE_RES 删除原因
	 */
	public String getDeleteRes() {
		return deleteRes;
	}

	/**
	 * 删除原因
	 * 
	 * @param deleteRes
	 *            删除原因
	 */
	public void setDeleteRes(String deleteRes) {
		this.deleteRes = deleteRes == null ? null : deleteRes.trim();
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public String getAssetType2Name() {
		return assetType2Name;
	}

	public void setAssetType2Name(String assetType2Name) {
		this.assetType2Name = assetType2Name;
	}

	public String getTradeMarketName() {
		return tradeMarketName;
	}

	public void setTradeMarketName(String tradeMarketName) {
		this.tradeMarketName = tradeMarketName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSendStatusName() {
		return sendStatusName;
	}

	public void setSendStatusName(String sendStatusName) {
		this.sendStatusName = sendStatusName;
	}

	public String getSubmitFeedbackStatusname() {
		return submitFeedbackStatusname;
	}

	public void setSubmitFeedbackStatusname(String submitFeedbackStatusname) {
		this.submitFeedbackStatusname = submitFeedbackStatusname;
	}

	public String getAssetTpye() {
		return assetType;
	}

	public String getCcyName() {
		return ccyName;
	}

	public void setCcyName(String ccyName) {
		this.ccyName = ccyName;
	}

	public String getSubmitFeedbackStatusName() {
		return submitFeedbackStatusName;
	}

	public void setSubmitFeedbackStatusName(String submitFeedbackStatusName) {
		this.submitFeedbackStatusName = submitFeedbackStatusName;
	}

	public String getBasisName() {
		return basisName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getBasisName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return basisName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetYield21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return assetYield==null?"":df5.format(assetYield);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public void setBasisName(String basisName) {
		this.basisName = basisName;
	}

	public String getIsStructName() {
		return isStructName;
	}

	public void setIsStructName(String isStructName) {
		this.isStructName = isStructName;
	}

	public String getTargetTypeName() {
		return targetTypeName;
	}

	public void setTargetTypeName(String targetTypeName) {
		this.targetTypeName = targetTypeName;
	}

	public String getChbSectypeName() {
		return chbSectypeName;
	}

	public void setChbSectypeName(String chbSectypeName) {
		this.chbSectypeName = chbSectypeName;
	}

	public String getIssueModeName() {
		return issueModeName;
	}

	public void setIssueModeName(String issueModeName) {
		this.issueModeName = issueModeName;
	}

	public String getSecMainGradeName() {
		return secMainGradeName;
	}

	public void setSecMainGradeName(String secMainGradeName) {
		this.secMainGradeName = secMainGradeName;
	}

	public String getSecCreditGradeName() {
		return secCreditGradeName;
	}

	public void setSecCreditGradeName(String secCreditGradeName) {
		this.secCreditGradeName = secCreditGradeName;
	}

	public String getSecCreditGradeTypeName() {
		return secCreditGradeTypeName;
	}

	public void setSecCreditGradeTypeName(String secCreditGradeTypeName) {
		this.secCreditGradeTypeName = secCreditGradeTypeName;
	}

	public String getPartyType1Name() {
		return partyType1Name;
	}

	public void setPartyType1Name(String partyType1Name) {
		this.partyType1Name = partyType1Name;
	}

	public String getPartyType3Name() {
		return partyType3Name;
	}

	public void setPartyType3Name(String partyType3Name) {
		this.partyType3Name = partyType3Name;
	}

	public String getPartyType2Name() {
		return partyType2Name;
	}

	public void setPartyType2Name(String partyType2Name) {
		this.partyType2Name = partyType2Name;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return industryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getIndustryLevel2Name() {
		return industryLevel2Name;
	}

	public void setIndustryLevel2Name(String industryLevel2Name) {
		this.industryLevel2Name = industryLevel2Name;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return industryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getRegistOrgName() {
		return registOrgName;
	}

	public void setRegistOrgName(String registOrgName) {
		this.registOrgName = registOrgName;
	}

	public String getRivalPartyName() {
		return rivalPartyName;
	}

	public void setRivalPartyName(String rivalPartyName) {
		this.rivalPartyName = rivalPartyName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return rivalParty;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRivalPartyName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return rivalPartyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getTargetType1Name() {
		return targetType1Name;
	}

	public void setTargetType1Name(String targetType1Name) {
		this.targetType1Name = targetType1Name;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetType1Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return targetType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTargetAmt21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return targetAmt==null?"":df2.format(targetAmt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetId21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return assetId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAssetName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return assetName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * 份额面值
	 */
	public String getPrinBaseamt04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return prinBaseamt==null?"":df4.format(prinBaseamt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * 超额分配比例
	 */
	public String getExcessProfitRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return excessProfitRate==null?"":df5.format(excessProfitRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 行权价格
	 */
	public String getExercisePrice04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return exercisePrice==null?"":df4.format(exercisePrice);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * 部分赎回比例
	 */
	public String getPartRedemRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return partRedemRate==null?"":df5.format(partRedemRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAmt21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return amt==null?"":df2.format(amt);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getProfitTypeName() {
		return profitTypeName;
	}

	public void setProfitTypeName(String profitTypeName) {
		this.profitTypeName = profitTypeName;
	}

	public String getIsResaleName() {
		return isResaleName;
	}

	public void setIsResaleName(String isResaleName) {
		this.isResaleName = isResaleName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIsResaleName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return isResaleName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getTermDate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return termDate;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getRateFlagName() {
		return rateFlagName;
	}

	public void setRateFlagName(String rateFlagName) {
		this.rateFlagName = rateFlagName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getRateFlagName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return rateFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public Integer getIntpayCycle21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return intpayCycle;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getBasisTypeName() {
		return basisTypeName;
	}

	public void setBasisTypeName(String basisTypeName) {
		this.basisTypeName = basisTypeName;
	}

	public String getIndustry1Name() {
		return industry1Name;
	}

	public void setIndustry1Name(String industry1Name) {
		this.industry1Name = industry1Name;
	}

	public String getIndustryLevel2TypeName() {
		return industryLevel2TypeName;
	}

	public void setIndustryLevel2TypeName(String industryLevel2TypeName) {
		this.industryLevel2TypeName = industryLevel2TypeName;
	}

	public String getProIndustryName() {
		return proIndustryName;
	}

	public void setProIndustryName(String proIndustryName) {
		this.proIndustryName = proIndustryName;
	}

	public String getProIndustryLevel2Name() {
		return proIndustryLevel2Name;
	}

	public void setProIndustryLevel2Name(String proIndustryLevel2Name) {
		this.proIndustryLevel2Name = proIndustryLevel2Name;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustry1Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return industry1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryLevel2TypeName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return industryLevel2TypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return proIndustryName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProIndustryLevel2Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return proIndustryLevel2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryFlagName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return industryFlagName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getIndustryFlagName() {
		return industryFlagName;
	}

	public void setIndustryFlagName(String industryFlagName) {
		this.industryFlagName = industryFlagName;
	}

	public String getMonitTypeName() {
		return monitTypeName;
	}

	public void setMonitTypeName(String monitTypeName) {
		this.monitTypeName = monitTypeName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMonitTypeName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return monitTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getGuaTypeName() {
		return guaTypeName;
	}

	public void setGuaTypeName(String guaTypeName) {
		this.guaTypeName = guaTypeName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaTypeName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return guaTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getFinancierGradeName() {
		return financierGradeName;
	}

	public void setFinancierGradeName(String financierGradeName) {
		this.financierGradeName = financierGradeName;
	}

	public String getInGradeName() {
		return inGradeName;
	}

	public void setInGradeName(String inGradeName) {
		this.inGradeName = inGradeName;
	}

	public String getOutGradeName() {
		return outGradeName;
	}

	public void setOutGradeName(String outGradeName) {
		this.outGradeName = outGradeName;
	}

	public String getFinAreaName() {
		return finAreaName;
	}

	public void setFinAreaName(String finAreaName) {
		this.finAreaName = finAreaName;
	}

	public String getIsRightName() {
		return isRightName;
	}

	public void setIsRightName(String isRightName) {
		this.isRightName = isRightName;
	}

	public String getNotstandSecTypeName() {
		return notstandSecTypeName;
	}

	public void setNotstandSecTypeName(String notstandSecTypeName) {
		this.notstandSecTypeName = notstandSecTypeName;
	}

	public String getIndustrySharesName() {
		return industrySharesName;
	}

	public void setIndustrySharesName(String industrySharesName) {
		this.industrySharesName = industrySharesName;
	}

	public String getIndustryShares2Name() {
		return industryShares2Name;
	}

	public void setIndustryShares2Name(String industryShares2Name) {
		this.industryShares2Name = industryShares2Name;
	}

	public String getSharesTypeName() {
		return sharesTypeName;
	}

	public void setSharesTypeName(String sharesTypeName) {
		this.sharesTypeName = sharesTypeName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustrySharesName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return industrySharesName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getIndustryShares2Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return industryShares2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getInvestStageName() {
		return investStageName;
	}

	public void setInvestStageName(String investStageName) {
		this.investStageName = investStageName;
	}

	public String getSharesType1Name() {
		return sharesType1Name;
	}

	public void setSharesType1Name(String sharesType1Name) {
		this.sharesType1Name = sharesType1Name;
	}

	public String getSharesType3Name() {
		return sharesType3Name;
	}

	public void setSharesType3Name(String sharesType3Name) {
		this.sharesType3Name = sharesType3Name;
	}

	public String getSharesType2Name() {
		return sharesType2Name;
	}

	public void setSharesType2Name(String sharesType2Name) {
		this.sharesType2Name = sharesType2Name;
	}

	public String getIsPledgeName() {
		return isPledgeName;
	}

	public void setIsPledgeName(String isPledgeName) {
		this.isPledgeName = isPledgeName;
	}

	public String getTargetType3Name() {
		return targetType3Name;
	}

	public void setTargetType3Name(String targetType3Name) {
		this.targetType3Name = targetType3Name;
	}

	public String getCountryAreaName() {
		return countryAreaName;
	}

	public void setCountryAreaName(String countryAreaName) {
		this.countryAreaName = countryAreaName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName18() {
 		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getCountryAreaName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return countryAreaName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getPartyGradeName() {
		return partyGradeName;
	}

	public void setPartyGradeName(String partyGradeName) {
		this.partyGradeName = partyGradeName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getGuaranteeCmt21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return guaranteeCmt;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getManagerId21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return managerId;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getTruActTypeName() {
		return truActTypeName;
	}

	public void setTruActTypeName(String truActTypeName) {
		this.truActTypeName = truActTypeName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruActTypeName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return truActTypeName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getInvestType21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return investType;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanSdate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return DateUtil.format(planSdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getPlanEdate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return DateUtil.format(planEdate,"yyyyMMdd");
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getProPropertyName() {
		return proPropertyName;
	}

	public void setProPropertyName(String proPropertyName) {
		this.proPropertyName = proPropertyName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return proPropertyName1;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getProPropertyName21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return proPropertyName;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMaxRate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return maxRate==null?"":df5.format(maxRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMinRate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return minRate==null?"":df5.format(minRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getBuyStructName() {
		return buyStructName;
	}

	public void setBuyStructName(String buyStructName) {
		this.buyStructName = buyStructName;
	}

	public String getManageTypeName() {
		return manageTypeName;
	}

	public void setManageTypeName(String manageTypeName) {
		this.manageTypeName = manageTypeName;
	}

	public String getIsCtrlBottomName() {
		return isCtrlBottomName;
	}

	public void setIsCtrlBottomName(String isCtrlBottomName) {
		this.isCtrlBottomName = isCtrlBottomName;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getMgrFeeRate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return mgrFeeRate==null?"":df5.format(mgrFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTruFeeRate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return truFeeRate==null?"":df5.format(truFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getTradeFeeRate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return tradeFeeRate==null?"":df5.format(tradeFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getAgencyFeeRate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return agencyFeeRate==null?"":df5.format(agencyFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getOtherFeeRate21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return otherFeeRate==null?"":df5.format(otherFeeRate);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String getRegistOrg1Name() {
		return registOrg1Name;
	}

	public void setRegistOrg1Name(String registOrg1Name) {
		this.registOrg1Name = registOrg1Name;
	}

	public String getIsGovernFundName() {
		return isGovernFundName;
	}

	public void setIsGovernFundName(String isGovernFundName) {
		this.isGovernFundName = isGovernFundName;
	}

	public String getGovernInvestFundName() {
		return governInvestFundName;
	}

	public void setGovernInvestFundName(String governInvestFundName) {
		this.governInvestFundName = governInvestFundName;
	}

	public String getInAssetTypeName() {
		return inAssetTypeName;
	}

	public void setInAssetTypeName(String inAssetTypeName) {
		this.inAssetTypeName = inAssetTypeName;
	}

	public String getFinancierType1Name() {
		return financierType1Name;
	}

	public void setFinancierType1Name(String financierType1Name) {
		this.financierType1Name = financierType1Name;
	}

	public String getFinancierType2Name() {
		return financierType2Name;
	}

	public void setFinancierType2Name(String financierType2Name) {
		this.financierType2Name = financierType2Name;
	}

	public String getFinancierType3Name() {
		return financierType3Name;
	}

	public void setFinancierType3Name(String financierType3Name) {
		this.financierType3Name = financierType3Name;
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancie21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return financie;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFcrGradeOut21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return fcrGradeOut;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType1Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return financierType1Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType2Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return financierType2Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name01() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("01".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name02() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("02".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name03() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("03".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name04() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("04".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name05() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("05".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name06() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("06".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name07() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("07".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name08() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("08".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name09() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("09".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name10() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("10".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name11() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("11".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name12() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("12".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name13() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("13".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name14() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("14".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name15() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name16() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("16".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name17() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("17".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name18() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("18".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name19() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("19".equals(getAssetType())||"15".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name20() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("20".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 起息日
	 * 
	 * @return VDATE 起息日
	 */
	public String getFinancierType3Name21() {
		if (!StringUtil.isBlank(getAssetType())) {
			if ("21".equals(getAssetType())) {
				return financierType3Name;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	  /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinanceProj21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return financeProj;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGrade21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return outGrade;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getCapUseInduName21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return capUseIndu;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

	/**
	 * 投管人
	 * @return 投管人
	 */
	public String getTgr12() {
		if (!StringUtil.isBlank(getAssetType())){
			if ("12".equals(getAssetType())){
				return trusteeId;
			} else {
				return null;
			}
		} else{
			return null;
		}
	}
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getTrusteeId21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return trusteeId;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

	public String getCapUseInduLevel2() {
		return capUseInduLevel2;
	}

	public void setCapUseInduLevel2(String capUseInduLevel2) {
		this.capUseInduLevel2 = capUseInduLevel2;
	}

	public String getSecCreditGradeType() {
		return secCreditGradeType;
	}

	public void setSecCreditGradeType(String secCreditGradeType) {
		this.secCreditGradeType = secCreditGradeType;
	}

	public String getCapUseInduName() {
		return capUseInduName;
	}

	public void setCapUseInduName(String capUseInduName) {
		this.capUseInduName = capUseInduName;
	}

	public String getCapUseInduLevel2Name() {
		return capUseInduLevel2Name;
	}

	public void setCapUseInduLevel2Name(String capUseInduLevel2Name) {
		this.capUseInduLevel2Name = capUseInduLevel2Name;
	}
	/**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getOutGradeName21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return outGradeName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

	public String getIssuePartyName() {
		return issuePartyName;
	}

	public void setIssuePartyName(String issuePartyName) {
		this.issuePartyName = issuePartyName;
	}    
	
	public String getIssuePartyName01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getIssuePartyName21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return issuePartyName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	public BigDecimal getAssetValue01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getAssetValue13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
    			return assetValue==null?"":df2.format(assetValue);
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getAssetValue14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
    			return assetValue==null?"":df2.format(assetValue);
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    private  String assetValue18;
    
    public String getAssetValue18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
    			return assetValue==null?"":df2.format(assetValue);
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public BigDecimal getAssetValue21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return assetValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	public String getMonTypeComm01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getMonTypeComm21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return monTypeComm;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

	public String getFinOrgCode01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getFinOrgCode21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return finOrgCode;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	public String getInvestStageName01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getInvestStageName21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return investStageName;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	public String getSharesType1Name01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType1Name21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return sharesType1Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	public String getSharesType2Name01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType2Name21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return sharesType2Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	public String getSharesType3Name01() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("01".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
	
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name02() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("02".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name03() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("03".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name05() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("05".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name07() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("07".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name08() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("08".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name09() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("09".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name10() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("10".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name11() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("11".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name13() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("13".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name16() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("16".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }

    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name17() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("17".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name18() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("18".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name19() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name20() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("20".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    /**
     * 起息日
     * @return VDATE 起息日
     */
    public String getSharesType3Name21() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("21".equals(getAssetType())){
        		return sharesType3Name;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    
    public String getPledgeType04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return pledgeType;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    public String getPledgeType14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return pledgeType;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }  
    
    public String getPledgeValue04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return pledgeValue==null?"":df2.format(pledgeValue);
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    public BigDecimal getPledgeValue14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return pledgeValue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    } 
    
    public String getNatureGuarantee04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return natureGuarantee;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    public String getNatureGuarantee14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return natureGuarantee;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }   
    
    public String getRelationFinan04() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("04".equals(getAssetType())){
        		return relationFinan;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    public String getRelationFinan14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return relationFinan;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }  
    
    
    public String getGuaTypeRemark14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return guaTypeRemark;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }   
    
    
      
    public String getDeptEquity06() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("06".equals(getAssetType())){
        		return deptEquity;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    public String getDeptEquity14() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("14".equals(getAssetType())){
        		return deptEquity;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }  
    
    public String getFinancialComissue12() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("12".equals(getAssetType())){
        		return financialComissue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
    }
    
    public String getfinancialComissue15() {
    	if (!StringUtil.isBlank(getAssetType())){
    		if ("19".equals(getAssetType())||"15".equals(getAssetType())){
        		return financialComissue;
    		} else {
    			return null;
    		}
    	} else{
    		return null;
    	}
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
  	private  String  assetType2Str;
	public String getAssetType2Str() {
        if (!StringUtil.isEmpty(this.getAssetType2())){
            return this.getAssetType2().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.资产_负债二级类别, this.getAssetType2());
        }
        return this.getAssetType2();
	}
	
	
 private  String	buyStructName12;
 
 public String   getBuyStructName12(){
 	if (!StringUtil.isBlank(getAssetType())){
 		if ("12".equals(getAssetType())){
     		return buyStructName;
 		} else {
 			return null;
 		}
 	} else{
 		return null;
 	}
 }
 
private  String  manageTypeName12;
public String   getManageTypeName12(){
 	if (!StringUtil.isBlank(getAssetType())){
 		if ("12".equals(getAssetType())){
     		return manageTypeName;
 		} else {
 			return null;
 		}
 	} else{
 		return null;
 	}
 }
public String getNomCapiStr() {
	return nomCapi==null?"":df2.format(nomCapi);
}

public void setNomCapiStr(String nomCapiStr) {
	this.nomCapiStr = nomCapiStr;
}

public String getBasicPriceStr() {
	return basicPrice==null?"":df2.format(basicPrice);
}

public void setBasicPriceStr(String basicPriceStr) {
	this.basicPriceStr = basicPriceStr;
}

public String getRegDatePriceStr() {
	return regDatePrice==null?"":df2.format(regDatePrice);
}

public void setRegDatePriceStr(String regDatePriceStr) {
	this.regDatePriceStr = regDatePriceStr;
}

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
	@DictFormat(DictCode.是否_中债)
	private String isBzhpj;

	/**
	 * 票据代码
	 */
	private String billCode;

	/**
	 * 是否为银行理财产品
	 */
	@DictFormat(DictCode.是否_中债)
	private String financialProductFlag;

	/**
	 * 产品登记编码
	 */
	private String prodRegisterCode;

	/**
	 * 是否为固定收益类
	 */
	@DictFormat(DictCode.是否_中债)
	private String profitFlag;

	/**
	 * 是否为同业借款
	 */
	@DictFormat(DictCode.是否_中债)
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
}