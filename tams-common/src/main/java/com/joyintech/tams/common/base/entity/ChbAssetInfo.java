package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;

import java.math.BigDecimal;
import java.util.Date;

public class ChbAssetInfo extends BaseEntity {
    /**
     * 资产要素uuid（为修改删除使用）
     */
    private String assetUuid;

    /**
     * 行内资产负债编码
     */
    @PrimaryKey(Field.PK_1)
    private String assetCode;

    /**
     * 资产类型
     */
    private String assetType;

    /**
     * 资产/负债二级类别 参考数据字典:ASSET_TYPE_SECOND_LEVEL
     */
    private String assetType2;

    /**
     * 币种 参考数据字典：CURRENCY_CHB
     */
    private String ccy;

    /**
     * 交易流通场所 01 银行间债券市场/02 商业银行柜台市场/03 上海证券交易所/04 深圳证券交易所/05 全国中小企业股份转让系统/06 上海期货交易所/07 郑州商品交易所/08 大连商品交易所/09 中国金融期货交易所/10 上海黄金交易所/11 区域性产权/资产交易市场/12 区域性商品交易所/13 区域性贵金属交易所/14 艺术品市场/15 基金市场/16 信托市场/17全国银行间同业拆借市场 /18 上海票据交易所/99 其他交易流通场所
     */
    private String tradeMarket;

    /**
     * 起息日
     */
    private Date vdate;

    /**
     * 到期日
     */
    private Date mdate;

    /**
     * 贴现利率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal discountYield;

    /**
     * 收益率/年利率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal assetYield;

    /**
     * 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     */
    private String basis;

    /**
     * 金额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal amt;

    /**
     * 资金存入银行
     */
    private String bankName;

    /**
     * 是否为结构性存款 01是/02否
     */
    private String isStruct;

    /**
     * 结构性存款挂钩标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    private String targetType;

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
     * 具体类别01 商业银行普通债/ 02 商业银行次级债/ 03 商业银行混合资本债/ 04 商业银行二级资本工具/ 05 证券公司金融债/ 06 证券公司短期融资券/ 07 保险公司金融债/ 08 财务公司金融债/ 09 融资租赁公司金融债/ 10 资产管理公司金融债/ 11 集合企业债/ 12 项目收益债券/ 13 可转换公司债券/ 14 可交换公司债券/ 15 分离交易的可转换公司债券/ 16 短期融资券（CP）/ 17 超短期融资券（SCP）/ 18 中期票据（MTN）/ 19 项目收益票据（PRN）/ 20 中小企业集合票据（SMECN）/ 21非公开定向债务融资工具/ 22 信贷资产支持证券/ 23 资产支持专项计划/ 24 非金融企业资产支持票据（ABN）/ 25 国际金融机构债券/ 26 外国政府债券/ 27 外国金融机构债券/ 28 外国公司债券/ 99 其他债券
     */
    private String chbSectype;

    /**
     * 发行方式 01 公开发行/02 非公开发行
     */
    private String issueMode;

    /**
     * 发行机构主体信用评级
     */
    private String partyGrade;

    /**
     * 发行机构类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    private String partyType1;

    /**
     * 发行机构类型（按技术领域划分）01 高新技术企业/99 其他
     */
    private String partyType3;

    /**
     * 发行机构类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    private String partyType2;

    /**
     * 发行机构所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    private String industry;

    /**
     * 发行机构所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String industryLevel2;

    /**
     * 登记托管机构 01 中央国债登记结算有限责任公司/ 02 上海清算所/03 中国证券登记结算有限责任公司/99 其他
     */
    private String registOrg;

    /**
     * 登记托管机构说明
     */
    private String registorgComment;

    /**
     * 对手方
     */
    private String rivalParty;

    /**
     * 回购标的类别 01 债券/ 02 股票/03 基金类产品/99 其他
     */
    private String targetType1;

    /**
     * 回购标的金额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal targetAmt;

    /**
     * 所属国家或地区 参考数据字典：COUNTRY_AREA
     */
    private String countryArea;

    /**
     * 收/受益权类型 01 信托贷款/ 02 信贷资产转让/ 03 委托债权/ 04 应收账款/ 05 带回购条款的股权性融资/06 私募债权/ 07 融资融券/ 08 证券公司收益凭证/ 09 委托贷款/ 99 其他
     */
    private String profitType;

    /**
     * 是否属于买入返售 01是/02否
     */
    private String isResale;

    /**
     * 期限（天）
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal termDate;

    /**
     * 是否有预期收益率 01是/02否
     */
    private String rateFlag;

    /**
     * 付息频率（个月/次）
     */
    private Long intpayCycle;

    /**
     * 还本付息说明
     */
    private String repayCmt;

    /**
     * 基准利率种类 参考数据字典：BASIS_TYPE
     */
    private String basisType;

    /**
     * 浮动因子
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal floatFactor;

    /**
     * 利差
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
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
     * 融资人类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    private String financierType1;

    /**
     * 融资人类型（按技术领域划分）01 高新技术企业/99 其他
     */
    private String financierType2;

    /**
     * 融资人类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    private String financierType3;

    /**
     * 融资项目
     */
    private String financeProj;

    /**
     * 融资人所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    private String industry1;

    /**
     * 融资人所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String industryLevel2Type;

    /**
     * 融资项目所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    private String proIndustry;

    /**
     * 融资项目所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String proIndustryLevel2;

    /**
     * 项目是否属于重点监控行业和领域 01 是/ 02 否
     */
    private String industryFlag;

    /**
     * 重点监控行业和领域类别 01 房地产(保障房除外)/ 02 地方政府融资平台/ 03 “铁公基”行业/ 04 “两高一剩”行业/ 99 其他
     */
    private String monitType;

    /**
     * 重点监控行业和领域类别说明
     */
    private String monTypeComm;

    /**
     * 担保方式  01 信用担保/ 02 第三方保证/ 03 抵押/ 04 质押/ 05 信用增级/ 99 其他
     */
    private String guaType;

    /**
     * 担保方式说明
     */
    private String guaTypeRemark;

    /**
     * 融资人主体评级
     */
    private String financierGrade;

    /**
     * 资产内部评级
     */
    private String inGrade;

    /**
     * 资产外部评级
     */
    private String outGrade;

    /**
     * 选择权
     */
    private String chose;

    /**
     * 行权条件说明
     */
    private String conditionCmm;

    /**
     * 融资人所属地区  参考数据字典：COUNTRY_AREA
     */
    private String finArea;

    /**
     * 融资总费率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal totalFeeRate;

    /**
     * 是否为收/受益权 01是/02否
     */
    private String isRight;

    /**
     * 非标准化债券类资产类型 01 商业承兑汇票/ 02 银行承兑汇票/ 03 其他票据/ 04 混合类票据/ 05 信用证
     */
    private String notstandSecType;

    /**
     * 非标准化债券类资产数量
     */
    private Long notstandSecNum;

    /**
     * 加权剩余期限(天)
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal remainTerm;

    /**
     * 最长剩余期限(天)
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal maxRemainTerm;

    /**
     * 最短剩余期限(天)
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal minRemainTerm;

    /**
     * 股票类型 01 普通股/02 优先股/99 其他
     */
    private String sharesType;

    /**
     * 行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    private String industryShares;

    /**
     * 行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String industryShares2;

    /**
     * 投资阶段 01 种子期/ 02 起步期/ 03 扩张期/ 04 过渡期/ 05 重建期/ 06 已上市/ 99 其它
     */
    private String investStage;

    /**
     * 投资企业类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    private String sharesType1;

    /**
     * 投资企业类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    private String sharesType2;

    /**
     * 投资企业类型（按技术领域划分）01 高新技术企业/99 其他
     */
    private String sharesType3;

    /**
     * 是否为质押融资 01是/02否
     */
    private String isPledge;

    /**
     * 名义本金
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal nomCapi;

    /**
     * 标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    private String targetType3;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 期限（月）
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal termMonth;

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
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal incomeRatio;

    /**
     * 衍生金融工具所占比例
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
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
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal maxYield;

    /**
     * 结构性票据最低收益率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal minYield;

    /**
     * 挂钩标的资产基准价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal basicPrice;

    /**
     * 挂钩标的资产登记日价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
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
     * 资管计划成立日期
     */
    private Date planSdate;

    /**
     * 资管计划终止日期
     */
    private Date planEdate;

    /**
     * 资管计划属性/委外投资属性 01 集合/02 单一/03 专项/04 定向/05 专户/99 其他
     */
    private String proProperty;

    /**
     * 最高预期收益率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal maxRate;

    /**
     * 最低预期收益率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal minRate;

    /**
     * 负债规模
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal debtScale;

    /**
     * 购买结构 01 优先/02 劣后/03 整体
     */
    private String buyStruct;

    /**
     * 管理方式 01 主动（自主管理）/02被动（委托管理）/99其他
     */
    private String manageType;

    /**
     * 是否掌控计划底层资产详情 01 是/ 02 否
     */
    private String isCtrlBottom;

    /**
     * 管理费率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal mgrFeeRate;

    /**
     * 托管费率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal truFeeRate;

    /**
     * 交易相关合计费率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal tradeFeeRate;

    /**
     * 中介服务机构合计费率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal agencyFeeRate;

    /**
     * 其他合计费率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal otherFeeRate;

    /**
     * 资产价值
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal assetValue;

    /**
     * 登记备案机构 01 中央国债登记结算有限责任公司/ 02 上海清算所 / 03 中国证券登记结算有限责任公司 / 04 中国证券投资基金业协会 /99 其他
     */
    private String registOrg1;

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
     * 行内资产类别 01 场内股票质押回购/02 场外股票质押融资/03 融资租赁/99 其他
     */
    private String inAssetType;

    /**
     * 行内资产类别说明
     */
    private String inAssetTypeCmm;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否底层资产 01 是/ 02 否
     */
    private String isUnderAsset;

    /**
     * 是否属于政府投资基金 01 是/ 02 否
     */
    private String isGovernFund;

    /**
     * 政府投资基金投向 01 投向创业早期企业/ 02 投向中小微企业/ 03 投向产业转型升级和发展/ 04 投向基础设施和公共服务领域/99 其他
     */
    private String governInvestFund;

    /**
     * 基金公司名称
     */
    private String fundEnpeName;

    /**
     * 受托人
     */
    private String trustee;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    private String privatefundFlag;

    /**
     * 是否投向金融资产投资公司发行的私募资产管理产品
     */
    private String privateprdFlag;

    /**
     * 是否投向“市场化债转股相关产品的部分”
     */
    private String bondtostockFlag;

    /**
     * 公共PPP
     */
    private String pppflag;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 资金运用行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String capUseInduLevel2;

    /**
     * 发行机构
     */
    private String issuePartyName;

    /**
     * 债券信用评级
     */
    private String secCreditGradeType;

    /**
     * 债券主体评级
     */
    private String secMainGrade;

    /**
     * 境内外
     */
    private String blnArea;

    /**
     * 融资人所属行业一级分类
     */
    private String industryPbc;

    /**
     * 客户主体类型,数据字典:SVR_CUSTOMER_PRIN_TYPE
     */
    private String customerObjType;

    /**
     * 上游资产一级分类
     */
    private String orgAssetType1;

    /**
     * 上游资产二级分类
     */
    private String orgAssetType2;

    /**
     * 上游资产三级分类
     */
    private String orgAssetType3;

    /**
     * 发行人/融资人所属地区
     */
    private String buyPlace;

    /**
     * 历史资产代码，数据移植时刷入
     */
    private String assetCodeOld;

    /**
     * 二级资产细分类:资产/负债二级类别=2201 信贷资产流转项目时必填，枚举值：01 小微企业信贷资产流转和收益权转让/02 信贷资产收益权转让
     */
    private String assetType3;

    /**
     * 人行资产一级分类
     */
    private String pbcAssetType1;

    /**
     * 人行资产二级分类
     */
    private String pbcAssetType2;

    /**
     * 人行资产三级分类
     */
    private String pbcAssetType3;

    /**
     * 上游资产四级分类
     */
    private String orgAssetType4;

    /**
     * 上游资产五级分类
     */
    private String orgAssetType5;

    /**
     * 是否投向地方政府融资平台
     */
    private String privateFlag;

    /**
     * 人行机构规模
     */
    private String institSize;

    /**
     * 交易所基金类型
     */
    private String trmFundType;

    /**
     * 商业银行二级资本债
     */
    private String bankSecCapital;

    /**
     * 商业银行优先股
     */
    private String bankSecPrefer;

    /**
     * 商业银行永续债
     */
    private String bankSecPerpetual;

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
    @JsonSerialize(using=BigDecimalSerializer.class)
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
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal excessProfitRate;

    /**
     * 抵质押物类型
     */
    private String pledgeType;

    /**
     * 抵质押物价值（元）
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
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
    private Integer exercisePeriod;

    /**
     * 行权价格
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
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
    private Integer reprceCycle;

    /**
     * 部分赎回标识
     */
    private String partRedemFlag;

    /**
     * 部分赎回比例%
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
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

    /**
     * null
     */
    private String isChl;

    /**
     * 发行人代码
     */
    private String chbIssuerId;

    /**
     * 管理人代码
     */
    private String chbManagerId;

    /**
     * 融资人代码
     */
    private String chbFinancierId;

    /**
     * 是否永续债
     */
    private String perpetualFlag;

    /**
     * 是否含权债
     */
    private String optionFlag;

    /**
     * 是否资产四级分类为“北金所债权”
     */
    private String isBjszq;

    /**
     * 是否商业银行可转债
     */
    private String bankSecKzzq;

    /**
     * 是否专项债券
     */
    private String bankSecZxzq;

    /**
     * 是否政府出资产业投资基金
     */
    private String isunlisted;

    /**
     * 是否标准化票据
     */
    private String isBzhpj;

    /**
     * 永续债属性（债性/股性）
     */
    private String isPerpetual;

    /**
     * 是否非标转标资产
     */
    private String isFbzb;

    /****是否结构化资产**/
    private String isConstracteBusi;

    /**
     * 结构化业务类型
     */
    private String constracteBusiType;

    /**
     * 结构化层级
     */
    private String constracteGrade;

    /**
     * 结构化杠杆倍数
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal constracteHeaverTimes;

    /**
     * 是否银行资本补充工具
     */
    private String isBankCap;


    /**
     * 银行资本补充工具
     */
    private String bankCapital;

    /**
     * 是否PPN等低流动性债券
     */
    private String isPpn;

    /**
     * null
     */
    private String orgAssetTypeEx;

    /**
     * 是否持有金融牌照
     */
    private String isFinLicence;

    /**
     * 存款账号
     */
    private String accountNo;

    /**
     * 资产代码
     */
    private String creditAssetCode;

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

    /**
     * 取得 资产要素uuid（为修改删除使用）
     * @return 资产要素uuid（为修改删除使用）
     */
    public String getAssetUuid() {
        return assetUuid;
    }

    /**
     * 设置 资产要素uuid（为修改删除使用）
     * @param assetUuid 资产要素uuid（为修改删除使用）
     */
    public void setAssetUuid(String assetUuid) {
        this.assetUuid = assetUuid == null ? null : assetUuid.trim();
    }

    /**
     * 取得 行内资产负债编码
     * @return 行内资产负债编码
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 设置 行内资产负债编码
     * @param assetCode 行内资产负债编码
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 取得 资产类型
     * @return 资产类型
     */
    public String getAssetType() {
        return assetType;
    }




    /**
     * 设置 资产类型
     * @param assetType 资产类型
     */
    public void setAssetType(String assetType) {
        this.assetType = assetType == null ? null : assetType.trim();
    }

    /**
     * 取得 资产/负债二级类别 参考数据字典:ASSET_TYPE_SECOND_LEVEL
     * @return 资产/负债二级类别 参考数据字典:ASSET_TYPE_SECOND_LEVEL
     */
    public String getAssetType2() {
        return assetType2;
    }

    /**
     * 设置 资产/负债二级类别 参考数据字典:ASSET_TYPE_SECOND_LEVEL
     * @param assetType2 资产/负债二级类别 参考数据字典:ASSET_TYPE_SECOND_LEVEL
     */
    public void setAssetType2(String assetType2) {
        this.assetType2 = assetType2 == null ? null : assetType2.trim();
    }

    /**
     * 取得 币种 参考数据字典：CURRENCY_CHB
     * @return 币种 参考数据字典：CURRENCY_CHB
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 设置 币种 参考数据字典：CURRENCY_CHB
     * @param ccy 币种 参考数据字典：CURRENCY_CHB
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 取得 交易流通场所 01 银行间债券市场/02 商业银行柜台市场/03 上海证券交易所/04 深圳证券交易所/05 全国中小企业股份转让系统/06 上海期货交易所/07 郑州商品交易所/08 大连商品交易所/09 中国金融期货交易所/10 上海黄金交易所/11 区域性产权/资产交易市场/12 区域性商品交易所/13 区域性贵金属交易所/14 艺术品市场/15 基金市场/16 信托市场/17全国银行间同业拆借市场 /18 上海票据交易所/99 其他交易流通场所
     * @return 交易流通场所 01 银行间债券市场/02 商业银行柜台市场/03 上海证券交易所/04 深圳证券交易所/05 全国中小企业股份转让系统/06 上海期货交易所/07 郑州商品交易所/08 大连商品交易所/09 中国金融期货交易所/10 上海黄金交易所/11 区域性产权/资产交易市场/12 区域性商品交易所/13 区域性贵金属交易所/14 艺术品市场/15 基金市场/16 信托市场/17全国银行间同业拆借市场 /18 上海票据交易所/99 其他交易流通场所
     */
    public String getTradeMarket() {
        return tradeMarket;
    }

    /**
     * 设置 交易流通场所 01 银行间债券市场/02 商业银行柜台市场/03 上海证券交易所/04 深圳证券交易所/05 全国中小企业股份转让系统/06 上海期货交易所/07 郑州商品交易所/08 大连商品交易所/09 中国金融期货交易所/10 上海黄金交易所/11 区域性产权/资产交易市场/12 区域性商品交易所/13 区域性贵金属交易所/14 艺术品市场/15 基金市场/16 信托市场/17全国银行间同业拆借市场 /18 上海票据交易所/99 其他交易流通场所
     * @param tradeMarket 交易流通场所 01 银行间债券市场/02 商业银行柜台市场/03 上海证券交易所/04 深圳证券交易所/05 全国中小企业股份转让系统/06 上海期货交易所/07 郑州商品交易所/08 大连商品交易所/09 中国金融期货交易所/10 上海黄金交易所/11 区域性产权/资产交易市场/12 区域性商品交易所/13 区域性贵金属交易所/14 艺术品市场/15 基金市场/16 信托市场/17全国银行间同业拆借市场 /18 上海票据交易所/99 其他交易流通场所
     */
    public void setTradeMarket(String tradeMarket) {
        this.tradeMarket = tradeMarket == null ? null : tradeMarket.trim();
    }

    /**
     * 取得 起息日
     * @return 起息日
     */
    public Date getVdate() {
        return vdate;
    }

    /**
     * 设置 起息日
     * @param vdate 起息日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    /**
     * 取得 到期日
     * @return 到期日
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * 设置 到期日
     * @param mdate 到期日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    /**
     * 取得 贴现利率
     * @return 贴现利率
     */
    public BigDecimal getDiscountYield() {
        return discountYield;
    }

    /**
     * 设置 贴现利率
     * @param discountYield 贴现利率
     */
    public void setDiscountYield(BigDecimal discountYield) {
        this.discountYield = discountYield;
    }

    /**
     * 取得 收益率/年利率
     * @return 收益率/年利率
     */
    public BigDecimal getAssetYield() {
        return assetYield;
    }

    /**
     * 设置 收益率/年利率
     * @param assetYield 收益率/年利率
     */
    public void setAssetYield(BigDecimal assetYield) {
        this.assetYield = assetYield;
    }

    /**
     * 取得 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     * @return 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     */
    public String getBasis() {
        return basis;
    }

    /**
     * 设置 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     * @param basis 计息基础 01 ACT/366；02 ACT/360；03 ACT/365；04 ACT/ACT；05 30/360
     */
    public void setBasis(String basis) {
        this.basis = basis == null ? null : basis.trim();
    }

    /**
     * 取得 金额
     * @return 金额
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * 设置 金额
     * @param amt 金额
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 取得 资金存入银行
     * @return 资金存入银行
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * 设置 资金存入银行
     * @param bankName 资金存入银行
     */
    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    /**
     * 取得 是否为结构性存款 01是/02否
     * @return 是否为结构性存款 01是/02否
     */
    public String getIsStruct() {
        return isStruct;
    }

    /**
     * 设置 是否为结构性存款 01是/02否
     * @param isStruct 是否为结构性存款 01是/02否
     */
    public void setIsStruct(String isStruct) {
        this.isStruct = isStruct == null ? null : isStruct.trim();
    }

    /**
     * 取得 结构性存款挂钩标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     * @return 结构性存款挂钩标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * 设置 结构性存款挂钩标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     * @param targetType 结构性存款挂钩标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType == null ? null : targetType.trim();
    }

    /**
     * 取得 结构性存款挂钩标的
     * @return 结构性存款挂钩标的
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置 结构性存款挂钩标的
     * @param target 结构性存款挂钩标的
     */
    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    /**
     * 取得 资产代码
     * @return 资产代码
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * 设置 资产代码
     * @param assetId 资产代码
     */
    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    /**
     * 取得 资产名称
     * @return 资产名称
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * 设置 资产名称
     * @param assetName 资产名称
     */
    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    /**
     * 取得 具体类别01 商业银行普通债/ 02 商业银行次级债/ 03 商业银行混合资本债/ 04 商业银行二级资本工具/ 05 证券公司金融债/ 06 证券公司短期融资券/ 07 保险公司金融债/ 08 财务公司金融债/ 09 融资租赁公司金融债/ 10 资产管理公司金融债/ 11 集合企业债/ 12 项目收益债券/ 13 可转换公司债券/ 14 可交换公司债券/ 15 分离交易的可转换公司债券/ 16 短期融资券（CP）/ 17 超短期融资券（SCP）/ 18 中期票据（MTN）/ 19 项目收益票据（PRN）/ 20 中小企业集合票据（SMECN）/ 21非公开定向债务融资工具/ 22 信贷资产支持证券/ 23 资产支持专项计划/ 24 非金融企业资产支持票据（ABN）/ 25 国际金融机构债券/ 26 外国政府债券/ 27 外国金融机构债券/ 28 外国公司债券/ 99 其他债券
     * @return 具体类别01 商业银行普通债/ 02 商业银行次级债/ 03 商业银行混合资本债/ 04 商业银行二级资本工具/ 05 证券公司金融债/ 06 证券公司短期融资券/ 07 保险公司金融债/ 08 财务公司金融债/ 09 融资租赁公司金融债/ 10 资产管理公司金融债/ 11 集合企业债/ 12 项目收益债券/ 13 可转换公司债券/ 14 可交换公司债券/ 15 分离交易的可转换公司债券/ 16 短期融资券（CP）/ 17 超短期融资券（SCP）/ 18 中期票据（MTN）/ 19 项目收益票据（PRN）/ 20 中小企业集合票据（SMECN）/ 21非公开定向债务融资工具/ 22 信贷资产支持证券/ 23 资产支持专项计划/ 24 非金融企业资产支持票据（ABN）/ 25 国际金融机构债券/ 26 外国政府债券/ 27 外国金融机构债券/ 28 外国公司债券/ 99 其他债券
     */
    public String getChbSectype() {
        return chbSectype;
    }

    /**
     * 设置 具体类别01 商业银行普通债/ 02 商业银行次级债/ 03 商业银行混合资本债/ 04 商业银行二级资本工具/ 05 证券公司金融债/ 06 证券公司短期融资券/ 07 保险公司金融债/ 08 财务公司金融债/ 09 融资租赁公司金融债/ 10 资产管理公司金融债/ 11 集合企业债/ 12 项目收益债券/ 13 可转换公司债券/ 14 可交换公司债券/ 15 分离交易的可转换公司债券/ 16 短期融资券（CP）/ 17 超短期融资券（SCP）/ 18 中期票据（MTN）/ 19 项目收益票据（PRN）/ 20 中小企业集合票据（SMECN）/ 21非公开定向债务融资工具/ 22 信贷资产支持证券/ 23 资产支持专项计划/ 24 非金融企业资产支持票据（ABN）/ 25 国际金融机构债券/ 26 外国政府债券/ 27 外国金融机构债券/ 28 外国公司债券/ 99 其他债券
     * @param chbSectype 具体类别01 商业银行普通债/ 02 商业银行次级债/ 03 商业银行混合资本债/ 04 商业银行二级资本工具/ 05 证券公司金融债/ 06 证券公司短期融资券/ 07 保险公司金融债/ 08 财务公司金融债/ 09 融资租赁公司金融债/ 10 资产管理公司金融债/ 11 集合企业债/ 12 项目收益债券/ 13 可转换公司债券/ 14 可交换公司债券/ 15 分离交易的可转换公司债券/ 16 短期融资券（CP）/ 17 超短期融资券（SCP）/ 18 中期票据（MTN）/ 19 项目收益票据（PRN）/ 20 中小企业集合票据（SMECN）/ 21非公开定向债务融资工具/ 22 信贷资产支持证券/ 23 资产支持专项计划/ 24 非金融企业资产支持票据（ABN）/ 25 国际金融机构债券/ 26 外国政府债券/ 27 外国金融机构债券/ 28 外国公司债券/ 99 其他债券
     */
    public void setChbSectype(String chbSectype) {
        this.chbSectype = chbSectype == null ? null : chbSectype.trim();
    }

    /**
     * 取得 发行方式 01 公开发行/02 非公开发行
     * @return 发行方式 01 公开发行/02 非公开发行
     */
    public String getIssueMode() {
        return issueMode;
    }

    /**
     * 设置 发行方式 01 公开发行/02 非公开发行
     * @param issueMode 发行方式 01 公开发行/02 非公开发行
     */
    public void setIssueMode(String issueMode) {
        this.issueMode = issueMode == null ? null : issueMode.trim();
    }

    /**
     * 取得 发行机构主体信用评级
     * @return 发行机构主体信用评级
     */
    public String getPartyGrade() {
        return partyGrade;
    }

    /**
     * 设置 发行机构主体信用评级
     * @param partyGrade 发行机构主体信用评级
     */
    public void setPartyGrade(String partyGrade) {
        this.partyGrade = partyGrade == null ? null : partyGrade.trim();
    }

    /**
     * 取得 发行机构类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     * @return 发行机构类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    public String getPartyType1() {
        return partyType1;
    }

    /**
     * 设置 发行机构类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     * @param partyType1 发行机构类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    public void setPartyType1(String partyType1) {
        this.partyType1 = partyType1 == null ? null : partyType1.trim();
    }

    /**
     * 取得 发行机构类型（按技术领域划分）01 高新技术企业/99 其他
     * @return 发行机构类型（按技术领域划分）01 高新技术企业/99 其他
     */
    public String getPartyType3() {
        return partyType3;
    }

    /**
     * 设置 发行机构类型（按技术领域划分）01 高新技术企业/99 其他
     * @param partyType3 发行机构类型（按技术领域划分）01 高新技术企业/99 其他
     */
    public void setPartyType3(String partyType3) {
        this.partyType3 = partyType3 == null ? null : partyType3.trim();
    }

    /**
     * 取得 发行机构类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     * @return 发行机构类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    public String getPartyType2() {
        return partyType2;
    }

    /**
     * 设置 发行机构类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     * @param partyType2 发行机构类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    public void setPartyType2(String partyType2) {
        this.partyType2 = partyType2 == null ? null : partyType2.trim();
    }

    /**
     * 取得 发行机构所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @return 发行机构所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置 发行机构所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @param industry 发行机构所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * 取得 发行机构所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @return 发行机构所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public String getIndustryLevel2() {
        return industryLevel2;
    }

    /**
     * 设置 发行机构所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @param industryLevel2 发行机构所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public void setIndustryLevel2(String industryLevel2) {
        this.industryLevel2 = industryLevel2 == null ? null : industryLevel2.trim();
    }

    /**
     * 取得 登记托管机构 01 中央国债登记结算有限责任公司/ 02 上海清算所/03 中国证券登记结算有限责任公司/99 其他
     * @return 登记托管机构 01 中央国债登记结算有限责任公司/ 02 上海清算所/03 中国证券登记结算有限责任公司/99 其他
     */
    public String getRegistOrg() {
        return registOrg;
    }

    /**
     * 设置 登记托管机构 01 中央国债登记结算有限责任公司/ 02 上海清算所/03 中国证券登记结算有限责任公司/99 其他
     * @param registOrg 登记托管机构 01 中央国债登记结算有限责任公司/ 02 上海清算所/03 中国证券登记结算有限责任公司/99 其他
     */
    public void setRegistOrg(String registOrg) {
        this.registOrg = registOrg == null ? null : registOrg.trim();
    }

    /**
     * 取得 登记托管机构说明
     * @return 登记托管机构说明
     */
    public String getRegistorgComment() {
        return registorgComment;
    }

    /**
     * 设置 登记托管机构说明
     * @param registorgComment 登记托管机构说明
     */
    public void setRegistorgComment(String registorgComment) {
        this.registorgComment = registorgComment == null ? null : registorgComment.trim();
    }

    /**
     * 取得 对手方
     * @return 对手方
     */
    public String getRivalParty() {
        return rivalParty;
    }

    /**
     * 设置 对手方
     * @param rivalParty 对手方
     */
    public void setRivalParty(String rivalParty) {
        this.rivalParty = rivalParty == null ? null : rivalParty.trim();
    }

    /**
     * 取得 回购标的类别 01 债券/ 02 股票/03 基金类产品/99 其他
     * @return 回购标的类别 01 债券/ 02 股票/03 基金类产品/99 其他
     */
    public String getTargetType1() {
        return targetType1;
    }

    /**
     * 设置 回购标的类别 01 债券/ 02 股票/03 基金类产品/99 其他
     * @param targetType1 回购标的类别 01 债券/ 02 股票/03 基金类产品/99 其他
     */
    public void setTargetType1(String targetType1) {
        this.targetType1 = targetType1 == null ? null : targetType1.trim();
    }

    /**
     * 取得 回购标的金额
     * @return 回购标的金额
     */
    public BigDecimal getTargetAmt() {
        return targetAmt;
    }

    /**
     * 设置 回购标的金额
     * @param targetAmt 回购标的金额
     */
    public void setTargetAmt(BigDecimal targetAmt) {
        this.targetAmt = targetAmt;
    }

    /**
     * 取得 所属国家或地区 参考数据字典：COUNTRY_AREA
     * @return 所属国家或地区 参考数据字典：COUNTRY_AREA
     */
    public String getCountryArea() {
        return countryArea;
    }

    /**
     * 设置 所属国家或地区 参考数据字典：COUNTRY_AREA
     * @param countryArea 所属国家或地区 参考数据字典：COUNTRY_AREA
     */
    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea == null ? null : countryArea.trim();
    }

    /**
     * 取得 收/受益权类型 01 信托贷款/ 02 信贷资产转让/ 03 委托债权/ 04 应收账款/ 05 带回购条款的股权性融资/06 私募债权/ 07 融资融券/ 08 证券公司收益凭证/ 09 委托贷款/ 99 其他
     * @return 收/受益权类型 01 信托贷款/ 02 信贷资产转让/ 03 委托债权/ 04 应收账款/ 05 带回购条款的股权性融资/06 私募债权/ 07 融资融券/ 08 证券公司收益凭证/ 09 委托贷款/ 99 其他
     */
    public String getProfitType() {
        return profitType;
    }

    /**
     * 设置 收/受益权类型 01 信托贷款/ 02 信贷资产转让/ 03 委托债权/ 04 应收账款/ 05 带回购条款的股权性融资/06 私募债权/ 07 融资融券/ 08 证券公司收益凭证/ 09 委托贷款/ 99 其他
     * @param profitType 收/受益权类型 01 信托贷款/ 02 信贷资产转让/ 03 委托债权/ 04 应收账款/ 05 带回购条款的股权性融资/06 私募债权/ 07 融资融券/ 08 证券公司收益凭证/ 09 委托贷款/ 99 其他
     */
    public void setProfitType(String profitType) {
        this.profitType = profitType == null ? null : profitType.trim();
    }

    /**
     * 取得 是否属于买入返售 01是/02否
     * @return 是否属于买入返售 01是/02否
     */
    public String getIsResale() {
        return isResale;
    }

    /**
     * 设置 是否属于买入返售 01是/02否
     * @param isResale 是否属于买入返售 01是/02否
     */
    public void setIsResale(String isResale) {
        this.isResale = isResale == null ? null : isResale.trim();
    }

    /**
     * 取得 期限（天）
     * @return 期限（天）
     */
    public BigDecimal getTermDate() {
        return termDate;
    }

    /**
     * 设置 期限（天）
     * @param termDate 期限（天）
     */
    public void setTermDate(BigDecimal termDate) {
        this.termDate = termDate;
    }

    /**
     * 取得 是否有预期收益率 01是/02否
     * @return 是否有预期收益率 01是/02否
     */
    public String getRateFlag() {
        return rateFlag;
    }

    /**
     * 设置 是否有预期收益率 01是/02否
     * @param rateFlag 是否有预期收益率 01是/02否
     */
    public void setRateFlag(String rateFlag) {
        this.rateFlag = rateFlag == null ? null : rateFlag.trim();
    }

    /**
     * 取得 付息频率（个月/次）
     * @return 付息频率（个月/次）
     */
    public Long getIntpayCycle() {
        return intpayCycle;
    }

    /**
     * 设置 付息频率（个月/次）
     * @param intpayCycle 付息频率（个月/次）
     */
    public void setIntpayCycle(Long intpayCycle) {
        this.intpayCycle = intpayCycle;
    }

    /**
     * 取得 还本付息说明
     * @return 还本付息说明
     */
    public String getRepayCmt() {
        return repayCmt;
    }

    /**
     * 设置 还本付息说明
     * @param repayCmt 还本付息说明
     */
    public void setRepayCmt(String repayCmt) {
        this.repayCmt = repayCmt == null ? null : repayCmt.trim();
    }

    /**
     * 取得 基准利率种类 参考数据字典：BASIS_TYPE
     * @return 基准利率种类 参考数据字典：BASIS_TYPE
     */
    public String getBasisType() {
        return basisType;
    }

    /**
     * 设置 基准利率种类 参考数据字典：BASIS_TYPE
     * @param basisType 基准利率种类 参考数据字典：BASIS_TYPE
     */
    public void setBasisType(String basisType) {
        this.basisType = basisType == null ? null : basisType.trim();
    }

    /**
     * 取得 浮动因子
     * @return 浮动因子
     */
    public BigDecimal getFloatFactor() {
        return floatFactor;
    }

    /**
     * 设置 浮动因子
     * @param floatFactor 浮动因子
     */
    public void setFloatFactor(BigDecimal floatFactor) {
        this.floatFactor = floatFactor;
    }

    /**
     * 取得 利差
     * @return 利差
     */
    public BigDecimal getSpreadRate() {
        return spreadRate;
    }

    /**
     * 设置 利差
     * @param spreadRate 利差
     */
    public void setSpreadRate(BigDecimal spreadRate) {
        this.spreadRate = spreadRate;
    }

    /**
     * 取得 融资人
     * @return 融资人
     */
    public String getFinancie() {
        return financie;
    }

    /**
     * 设置 融资人
     * @param financie 融资人
     */
    public void setFinancie(String financie) {
        this.financie = financie == null ? null : financie.trim();
    }

    /**
     * 取得 融资人组织机构代码
     * @return 融资人组织机构代码
     */
    public String getFinOrgCode() {
        return finOrgCode;
    }

    /**
     * 设置 融资人组织机构代码
     * @param finOrgCode 融资人组织机构代码
     */
    public void setFinOrgCode(String finOrgCode) {
        this.finOrgCode = finOrgCode == null ? null : finOrgCode.trim();
    }

    /**
     * 取得 融资人内部信用评级
     * @return 融资人内部信用评级
     */
    public String getFcrGradeIn() {
        return fcrGradeIn;
    }

    /**
     * 设置 融资人内部信用评级
     * @param fcrGradeIn 融资人内部信用评级
     */
    public void setFcrGradeIn(String fcrGradeIn) {
        this.fcrGradeIn = fcrGradeIn == null ? null : fcrGradeIn.trim();
    }

    /**
     * 取得 外部评级机构名称及融资人评级结果
     * @return 外部评级机构名称及融资人评级结果
     */
    public String getFcrGradeOut() {
        return fcrGradeOut;
    }

    /**
     * 设置 外部评级机构名称及融资人评级结果
     * @param fcrGradeOut 外部评级机构名称及融资人评级结果
     */
    public void setFcrGradeOut(String fcrGradeOut) {
        this.fcrGradeOut = fcrGradeOut == null ? null : fcrGradeOut.trim();
    }

    /**
     * 取得 融资人类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     * @return 融资人类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    public String getFinancierType1() {
        return financierType1;
    }

    /**
     * 设置 融资人类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     * @param financierType1 融资人类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    public void setFinancierType1(String financierType1) {
        this.financierType1 = financierType1 == null ? null : financierType1.trim();
    }

    /**
     * 取得 融资人类型（按技术领域划分）01 高新技术企业/99 其他
     * @return 融资人类型（按技术领域划分）01 高新技术企业/99 其他
     */
    public String getFinancierType2() {
        return financierType2;
    }

    /**
     * 设置 融资人类型（按技术领域划分）01 高新技术企业/99 其他
     * @param financierType2 融资人类型（按技术领域划分）01 高新技术企业/99 其他
     */
    public void setFinancierType2(String financierType2) {
        this.financierType2 = financierType2 == null ? null : financierType2.trim();
    }

    /**
     * 取得 融资人类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     * @return 融资人类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    public String getFinancierType3() {
        return financierType3;
    }

    /**
     * 设置 融资人类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     * @param financierType3 融资人类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    public void setFinancierType3(String financierType3) {
        this.financierType3 = financierType3 == null ? null : financierType3.trim();
    }

    /**
     * 取得 融资项目
     * @return 融资项目
     */
    public String getFinanceProj() {
        return financeProj;
    }

    /**
     * 设置 融资项目
     * @param financeProj 融资项目
     */
    public void setFinanceProj(String financeProj) {
        this.financeProj = financeProj == null ? null : financeProj.trim();
    }

    /**
     * 取得 融资人所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @return 融资人所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public String getIndustry1() {
        return industry1;
    }

    /**
     * 设置 融资人所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @param industry1 融资人所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public void setIndustry1(String industry1) {
        this.industry1 = industry1 == null ? null : industry1.trim();
    }

    /**
     * 取得 融资人所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @return 融资人所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public String getIndustryLevel2Type() {
        return industryLevel2Type;
    }

    /**
     * 设置 融资人所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @param industryLevel2Type 融资人所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public void setIndustryLevel2Type(String industryLevel2Type) {
        this.industryLevel2Type = industryLevel2Type == null ? null : industryLevel2Type.trim();
    }

    /**
     * 取得 融资项目所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @return 融资项目所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public String getProIndustry() {
        return proIndustry;
    }

    /**
     * 设置 融资项目所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @param proIndustry 融资项目所属行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public void setProIndustry(String proIndustry) {
        this.proIndustry = proIndustry == null ? null : proIndustry.trim();
    }

    /**
     * 取得 融资项目所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @return 融资项目所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public String getProIndustryLevel2() {
        return proIndustryLevel2;
    }

    /**
     * 设置 融资项目所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @param proIndustryLevel2 融资项目所属行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public void setProIndustryLevel2(String proIndustryLevel2) {
        this.proIndustryLevel2 = proIndustryLevel2 == null ? null : proIndustryLevel2.trim();
    }

    /**
     * 取得 项目是否属于重点监控行业和领域 01 是/ 02 否
     * @return 项目是否属于重点监控行业和领域 01 是/ 02 否
     */
    public String getIndustryFlag() {
        return industryFlag;
    }

    /**
     * 设置 项目是否属于重点监控行业和领域 01 是/ 02 否
     * @param industryFlag 项目是否属于重点监控行业和领域 01 是/ 02 否
     */
    public void setIndustryFlag(String industryFlag) {
        this.industryFlag = industryFlag == null ? null : industryFlag.trim();
    }

    /**
     * 取得 重点监控行业和领域类别 01 房地产(保障房除外)/ 02 地方政府融资平台/ 03 “铁公基”行业/ 04 “两高一剩”行业/ 99 其他
     * @return 重点监控行业和领域类别 01 房地产(保障房除外)/ 02 地方政府融资平台/ 03 “铁公基”行业/ 04 “两高一剩”行业/ 99 其他
     */
    public String getMonitType() {
        return monitType;
    }

    /**
     * 设置 重点监控行业和领域类别 01 房地产(保障房除外)/ 02 地方政府融资平台/ 03 “铁公基”行业/ 04 “两高一剩”行业/ 99 其他
     * @param monitType 重点监控行业和领域类别 01 房地产(保障房除外)/ 02 地方政府融资平台/ 03 “铁公基”行业/ 04 “两高一剩”行业/ 99 其他
     */
    public void setMonitType(String monitType) {
        this.monitType = monitType == null ? null : monitType.trim();
    }

    /**
     * 取得 重点监控行业和领域类别说明
     * @return 重点监控行业和领域类别说明
     */
    public String getMonTypeComm() {
        return monTypeComm;
    }

    /**
     * 设置 重点监控行业和领域类别说明
     * @param monTypeComm 重点监控行业和领域类别说明
     */
    public void setMonTypeComm(String monTypeComm) {
        this.monTypeComm = monTypeComm == null ? null : monTypeComm.trim();
    }

    /**
     * 取得 担保方式  01 信用担保/ 02 第三方保证/ 03 抵押/ 04 质押/ 05 信用增级/ 99 其他
     * @return 担保方式  01 信用担保/ 02 第三方保证/ 03 抵押/ 04 质押/ 05 信用增级/ 99 其他
     */
    public String getGuaType() {
        return guaType;
    }

    /**
     * 设置 担保方式  01 信用担保/ 02 第三方保证/ 03 抵押/ 04 质押/ 05 信用增级/ 99 其他
     * @param guaType 担保方式  01 信用担保/ 02 第三方保证/ 03 抵押/ 04 质押/ 05 信用增级/ 99 其他
     */
    public void setGuaType(String guaType) {
        this.guaType = guaType == null ? null : guaType.trim();
    }

    /**
     * 取得 担保方式说明
     * @return 担保方式说明
     */
    public String getGuaTypeRemark() {
        return guaTypeRemark;
    }

    /**
     * 设置 担保方式说明
     * @param guaTypeRemark 担保方式说明
     */
    public void setGuaTypeRemark(String guaTypeRemark) {
        this.guaTypeRemark = guaTypeRemark == null ? null : guaTypeRemark.trim();
    }

    /**
     * 取得 融资人主体评级
     * @return 融资人主体评级
     */
    public String getFinancierGrade() {
        return financierGrade;
    }

    /**
     * 设置 融资人主体评级
     * @param financierGrade 融资人主体评级
     */
    public void setFinancierGrade(String financierGrade) {
        this.financierGrade = financierGrade == null ? null : financierGrade.trim();
    }

    /**
     * 取得 资产内部评级
     * @return 资产内部评级
     */
    public String getInGrade() {
        return inGrade;
    }

    /**
     * 设置 资产内部评级
     * @param inGrade 资产内部评级
     */
    public void setInGrade(String inGrade) {
        this.inGrade = inGrade == null ? null : inGrade.trim();
    }

    /**
     * 取得 资产外部评级
     * @return 资产外部评级
     */
    public String getOutGrade() {
        return outGrade;
    }

    /**
     * 设置 资产外部评级
     * @param outGrade 资产外部评级
     */
    public void setOutGrade(String outGrade) {
        this.outGrade = outGrade == null ? null : outGrade.trim();
    }

    /**
     * 取得 选择权
     * @return 选择权
     */
    public String getChose() {
        return chose;
    }

    /**
     * 设置 选择权
     * @param chose 选择权
     */
    public void setChose(String chose) {
        this.chose = chose == null ? null : chose.trim();
    }

    /**
     * 取得 行权条件说明
     * @return 行权条件说明
     */
    public String getConditionCmm() {
        return conditionCmm;
    }

    /**
     * 设置 行权条件说明
     * @param conditionCmm 行权条件说明
     */
    public void setConditionCmm(String conditionCmm) {
        this.conditionCmm = conditionCmm == null ? null : conditionCmm.trim();
    }

    /**
     * 取得 融资人所属地区  参考数据字典：COUNTRY_AREA
     * @return 融资人所属地区  参考数据字典：COUNTRY_AREA
     */
    public String getFinArea() {
        return finArea;
    }

    /**
     * 设置 融资人所属地区  参考数据字典：COUNTRY_AREA
     * @param finArea 融资人所属地区  参考数据字典：COUNTRY_AREA
     */
    public void setFinArea(String finArea) {
        this.finArea = finArea == null ? null : finArea.trim();
    }

    /**
     * 取得 融资总费率
     * @return 融资总费率
     */
    public BigDecimal getTotalFeeRate() {
        return totalFeeRate;
    }

    /**
     * 设置 融资总费率
     * @param totalFeeRate 融资总费率
     */
    public void setTotalFeeRate(BigDecimal totalFeeRate) {
        this.totalFeeRate = totalFeeRate;
    }

    /**
     * 取得 是否为收/受益权 01是/02否
     * @return 是否为收/受益权 01是/02否
     */
    public String getIsRight() {
        return isRight;
    }

    /**
     * 设置 是否为收/受益权 01是/02否
     * @param isRight 是否为收/受益权 01是/02否
     */
    public void setIsRight(String isRight) {
        this.isRight = isRight == null ? null : isRight.trim();
    }

    /**
     * 取得 非标准化债券类资产类型 01 商业承兑汇票/ 02 银行承兑汇票/ 03 其他票据/ 04 混合类票据/ 05 信用证
     * @return 非标准化债券类资产类型 01 商业承兑汇票/ 02 银行承兑汇票/ 03 其他票据/ 04 混合类票据/ 05 信用证
     */
    public String getNotstandSecType() {
        return notstandSecType;
    }

    /**
     * 设置 非标准化债券类资产类型 01 商业承兑汇票/ 02 银行承兑汇票/ 03 其他票据/ 04 混合类票据/ 05 信用证
     * @param notstandSecType 非标准化债券类资产类型 01 商业承兑汇票/ 02 银行承兑汇票/ 03 其他票据/ 04 混合类票据/ 05 信用证
     */
    public void setNotstandSecType(String notstandSecType) {
        this.notstandSecType = notstandSecType == null ? null : notstandSecType.trim();
    }

    /**
     * 取得 非标准化债券类资产数量
     * @return 非标准化债券类资产数量
     */
    public Long getNotstandSecNum() {
        return notstandSecNum;
    }

    /**
     * 设置 非标准化债券类资产数量
     * @param notstandSecNum 非标准化债券类资产数量
     */
    public void setNotstandSecNum(Long notstandSecNum) {
        this.notstandSecNum = notstandSecNum;
    }

    /**
     * 取得 加权剩余期限(天)
     * @return 加权剩余期限(天)
     */
    public BigDecimal getRemainTerm() {
        return remainTerm;
    }

    /**
     * 设置 加权剩余期限(天)
     * @param remainTerm 加权剩余期限(天)
     */
    public void setRemainTerm(BigDecimal remainTerm) {
        this.remainTerm = remainTerm;
    }

    /**
     * 取得 最长剩余期限(天)
     * @return 最长剩余期限(天)
     */
    public BigDecimal getMaxRemainTerm() {
        return maxRemainTerm;
    }

    /**
     * 设置 最长剩余期限(天)
     * @param maxRemainTerm 最长剩余期限(天)
     */
    public void setMaxRemainTerm(BigDecimal maxRemainTerm) {
        this.maxRemainTerm = maxRemainTerm;
    }

    /**
     * 取得 最短剩余期限(天)
     * @return 最短剩余期限(天)
     */
    public BigDecimal getMinRemainTerm() {
        return minRemainTerm;
    }

    /**
     * 设置 最短剩余期限(天)
     * @param minRemainTerm 最短剩余期限(天)
     */
    public void setMinRemainTerm(BigDecimal minRemainTerm) {
        this.minRemainTerm = minRemainTerm;
    }

    /**
     * 取得 股票类型 01 普通股/02 优先股/99 其他
     * @return 股票类型 01 普通股/02 优先股/99 其他
     */
    public String getSharesType() {
        return sharesType;
    }

    /**
     * 设置 股票类型 01 普通股/02 优先股/99 其他
     * @param sharesType 股票类型 01 普通股/02 优先股/99 其他
     */
    public void setSharesType(String sharesType) {
        this.sharesType = sharesType == null ? null : sharesType.trim();
    }

    /**
     * 取得 行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @return 行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public String getIndustryShares() {
        return industryShares;
    }

    /**
     * 设置 行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @param industryShares 行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public void setIndustryShares(String industryShares) {
        this.industryShares = industryShares == null ? null : industryShares.trim();
    }

    /**
     * 取得 行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @return 行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public String getIndustryShares2() {
        return industryShares2;
    }

    /**
     * 设置 行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @param industryShares2 行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public void setIndustryShares2(String industryShares2) {
        this.industryShares2 = industryShares2 == null ? null : industryShares2.trim();
    }

    /**
     * 取得 投资阶段 01 种子期/ 02 起步期/ 03 扩张期/ 04 过渡期/ 05 重建期/ 06 已上市/ 99 其它
     * @return 投资阶段 01 种子期/ 02 起步期/ 03 扩张期/ 04 过渡期/ 05 重建期/ 06 已上市/ 99 其它
     */
    public String getInvestStage() {
        return investStage;
    }

    /**
     * 设置 投资阶段 01 种子期/ 02 起步期/ 03 扩张期/ 04 过渡期/ 05 重建期/ 06 已上市/ 99 其它
     * @param investStage 投资阶段 01 种子期/ 02 起步期/ 03 扩张期/ 04 过渡期/ 05 重建期/ 06 已上市/ 99 其它
     */
    public void setInvestStage(String investStage) {
        this.investStage = investStage == null ? null : investStage.trim();
    }

    /**
     * 取得 投资企业类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     * @return 投资企业类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    public String getSharesType1() {
        return sharesType1;
    }

    /**
     * 设置 投资企业类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     * @param sharesType1 投资企业类型（按规模划分） 01 中小微企业/02 大型企业/99 其他
     */
    public void setSharesType1(String sharesType1) {
        this.sharesType1 = sharesType1 == null ? null : sharesType1.trim();
    }

    /**
     * 取得 投资企业类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     * @return 投资企业类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    public String getSharesType2() {
        return sharesType2;
    }

    /**
     * 设置 投资企业类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     * @param sharesType2 投资企业类型（按经济类型划分）01 国有企业/02 集体企业/03 私营企业/99 其他
     */
    public void setSharesType2(String sharesType2) {
        this.sharesType2 = sharesType2 == null ? null : sharesType2.trim();
    }

    /**
     * 取得 投资企业类型（按技术领域划分）01 高新技术企业/99 其他
     * @return 投资企业类型（按技术领域划分）01 高新技术企业/99 其他
     */
    public String getSharesType3() {
        return sharesType3;
    }

    /**
     * 设置 投资企业类型（按技术领域划分）01 高新技术企业/99 其他
     * @param sharesType3 投资企业类型（按技术领域划分）01 高新技术企业/99 其他
     */
    public void setSharesType3(String sharesType3) {
        this.sharesType3 = sharesType3 == null ? null : sharesType3.trim();
    }

    /**
     * 取得 是否为质押融资 01是/02否
     * @return 是否为质押融资 01是/02否
     */
    public String getIsPledge() {
        return isPledge;
    }

    /**
     * 设置 是否为质押融资 01是/02否
     * @param isPledge 是否为质押融资 01是/02否
     */
    public void setIsPledge(String isPledge) {
        this.isPledge = isPledge == null ? null : isPledge.trim();
    }

    /**
     * 取得 名义本金
     * @return 名义本金
     */
    public BigDecimal getNomCapi() {
        return nomCapi;
    }

    /**
     * 设置 名义本金
     * @param nomCapi 名义本金
     */
    public void setNomCapi(BigDecimal nomCapi) {
        this.nomCapi = nomCapi;
    }

    /**
     * 取得 标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     * @return 标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    public String getTargetType3() {
        return targetType3;
    }

    /**
     * 设置 标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     * @param targetType3 标的类别 01 利率/02 汇率/03 股票/04 农产品/05 大宗商品/06 贵金属/07 债券/08 指数 /09 基金/10 另类资产/11 期货/12 期权/13 远期/14 互换/15 复合金融衍生品/99 其他
     */
    public void setTargetType3(String targetType3) {
        this.targetType3 = targetType3 == null ? null : targetType3.trim();
    }

    /**
     * 取得 发行机构代码
     * @return 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 设置 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 取得 期限（月）
     * @return 期限（月）
     */
    public BigDecimal getTermMonth() {
        return termMonth;
    }

    /**
     * 设置 期限（月）
     * @param termMonth 期限（月）
     */
    public void setTermMonth(BigDecimal termMonth) {
        this.termMonth = termMonth;
    }

    /**
     * 取得 担保情况说明
     * @return 担保情况说明
     */
    public String getGuaranteeCmt() {
        return guaranteeCmt;
    }

    /**
     * 设置 担保情况说明
     * @param guaranteeCmt 担保情况说明
     */
    public void setGuaranteeCmt(String guaranteeCmt) {
        this.guaranteeCmt = guaranteeCmt == null ? null : guaranteeCmt.trim();
    }

    /**
     * 取得 是否含权等特条款情况说明
     * @return 是否含权等特条款情况说明
     */
    public String getSpecialCmt() {
        return specialCmt;
    }

    /**
     * 设置 是否含权等特条款情况说明
     * @param specialCmt 是否含权等特条款情况说明
     */
    public void setSpecialCmt(String specialCmt) {
        this.specialCmt = specialCmt == null ? null : specialCmt.trim();
    }

    /**
     * 取得 合约名称
     * @return 合约名称
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * 设置 合约名称
     * @param contractName 合约名称
     */
    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    /**
     * 取得 固定收益部分所占比例
     * @return 固定收益部分所占比例
     */
    public BigDecimal getIncomeRatio() {
        return incomeRatio;
    }

    /**
     * 设置 固定收益部分所占比例
     * @param incomeRatio 固定收益部分所占比例
     */
    public void setIncomeRatio(BigDecimal incomeRatio) {
        this.incomeRatio = incomeRatio;
    }

    /**
     * 取得 衍生金融工具所占比例
     * @return 衍生金融工具所占比例
     */
    public BigDecimal getDerInstrRatio() {
        return derInstrRatio;
    }

    /**
     * 设置 衍生金融工具所占比例
     * @param derInstrRatio 衍生金融工具所占比例
     */
    public void setDerInstrRatio(BigDecimal derInstrRatio) {
        this.derInstrRatio = derInstrRatio;
    }

    /**
     * 取得 衍生金融工具具体投资方式
     * @return 衍生金融工具具体投资方式
     */
    public String getDerInstrInvType() {
        return derInstrInvType;
    }

    /**
     * 设置 衍生金融工具具体投资方式
     * @param derInstrInvType 衍生金融工具具体投资方式
     */
    public void setDerInstrInvType(String derInstrInvType) {
        this.derInstrInvType = derInstrInvType == null ? null : derInstrInvType.trim();
    }

    /**
     * 取得 衍生金融工具挂钩标的资产
     * @return 衍生金融工具挂钩标的资产
     */
    public String getDerInstrTarget() {
        return derInstrTarget;
    }

    /**
     * 设置 衍生金融工具挂钩标的资产
     * @param derInstrTarget 衍生金融工具挂钩标的资产
     */
    public void setDerInstrTarget(String derInstrTarget) {
        this.derInstrTarget = derInstrTarget == null ? null : derInstrTarget.trim();
    }

    /**
     * 取得 利息结算方式
     * @return 利息结算方式
     */
    public String getAcctMode() {
        return acctMode;
    }

    /**
     * 设置 利息结算方式
     * @param acctMode 利息结算方式
     */
    public void setAcctMode(String acctMode) {
        this.acctMode = acctMode == null ? null : acctMode.trim();
    }

    /**
     * 取得 含权情况说明
     * @return 含权情况说明
     */
    public String getSpecialCmt1() {
        return specialCmt1;
    }

    /**
     * 设置 含权情况说明
     * @param specialCmt1 含权情况说明
     */
    public void setSpecialCmt1(String specialCmt1) {
        this.specialCmt1 = specialCmt1 == null ? null : specialCmt1.trim();
    }

    /**
     * 取得 结构性票据最高收益率
     * @return 结构性票据最高收益率
     */
    public BigDecimal getMaxYield() {
        return maxYield;
    }

    /**
     * 设置 结构性票据最高收益率
     * @param maxYield 结构性票据最高收益率
     */
    public void setMaxYield(BigDecimal maxYield) {
        this.maxYield = maxYield;
    }

    /**
     * 取得 结构性票据最低收益率
     * @return 结构性票据最低收益率
     */
    public BigDecimal getMinYield() {
        return minYield;
    }

    /**
     * 设置 结构性票据最低收益率
     * @param minYield 结构性票据最低收益率
     */
    public void setMinYield(BigDecimal minYield) {
        this.minYield = minYield;
    }

    /**
     * 取得 挂钩标的资产基准价格
     * @return 挂钩标的资产基准价格
     */
    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    /**
     * 设置 挂钩标的资产基准价格
     * @param basicPrice 挂钩标的资产基准价格
     */
    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    /**
     * 取得 挂钩标的资产登记日价格
     * @return 挂钩标的资产登记日价格
     */
    public BigDecimal getRegDatePrice() {
        return regDatePrice;
    }

    /**
     * 设置 挂钩标的资产登记日价格
     * @param regDatePrice 挂钩标的资产登记日价格
     */
    public void setRegDatePrice(BigDecimal regDatePrice) {
        this.regDatePrice = regDatePrice;
    }

    /**
     * 取得 交易费
     * @return 交易费
     */
    public String getTradeFee() {
        return tradeFee;
    }

    /**
     * 设置 交易费
     * @param tradeFee 交易费
     */
    public void setTradeFee(String tradeFee) {
        this.tradeFee = tradeFee == null ? null : tradeFee.trim();
    }

    /**
     * 取得 资管计划发起人机构编码
     * @return 资管计划发起人机构编码
     */
    public String getPartyCode() {
        return partyCode;
    }

    /**
     * 设置 资管计划发起人机构编码
     * @param partyCode 资管计划发起人机构编码
     */
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode == null ? null : partyCode.trim();
    }

    /**
     * 取得 资管计划登记编码
     * @return 资管计划登记编码
     */
    public String getCheckCode() {
        return checkCode;
    }

    /**
     * 设置 资管计划登记编码
     * @param checkCode 资管计划登记编码
     */
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode == null ? null : checkCode.trim();
    }

    /**
     * 取得 管理人
     * @return 管理人
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * 设置 管理人
     * @param managerId 管理人
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    /**
     * 取得 托管人
     * @return 托管人
     */
    public String getTrusteeId() {
        return trusteeId;
    }

    /**
     * 设置 托管人
     * @param trusteeId 托管人
     */
    public void setTrusteeId(String trusteeId) {
        this.trusteeId = trusteeId == null ? null : trusteeId.trim();
    }

    /**
     * 取得 资金实际投向 01 结构性投资类/02 货币市场工具类/03 债券类/04 股票类/05 股权类/06 另类投资类/07 混合类/08 非标准化债权类/09 银行存款类/10 理财直接融资工具类/11 新增可投资资产类/12 代客境外投资类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类/18 同业存单类/19拆放同业及买入返售类
     * @return 资金实际投向 01 结构性投资类/02 货币市场工具类/03 债券类/04 股票类/05 股权类/06 另类投资类/07 混合类/08 非标准化债权类/09 银行存款类/10 理财直接融资工具类/11 新增可投资资产类/12 代客境外投资类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类/18 同业存单类/19拆放同业及买入返售类
     */
    public String getTruActType() {
        return truActType;
    }

    /**
     * 设置 资金实际投向 01 结构性投资类/02 货币市场工具类/03 债券类/04 股票类/05 股权类/06 另类投资类/07 混合类/08 非标准化债权类/09 银行存款类/10 理财直接融资工具类/11 新增可投资资产类/12 代客境外投资类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类/18 同业存单类/19拆放同业及买入返售类
     * @param truActType 资金实际投向 01 结构性投资类/02 货币市场工具类/03 债券类/04 股票类/05 股权类/06 另类投资类/07 混合类/08 非标准化债权类/09 银行存款类/10 理财直接融资工具类/11 新增可投资资产类/12 代客境外投资类/13 公募基金类/ 14 私募基金类/ 15 产业基金类 /16 衍生产品类 /17 商品类/18 同业存单类/19拆放同业及买入返售类
     */
    public void setTruActType(String truActType) {
        this.truActType = truActType == null ? null : truActType.trim();
    }

    /**
     * 取得 资金运用方式
     * @return 资金运用方式
     */
    public String getInvestType() {
        return investType;
    }

    /**
     * 设置 资金运用方式
     * @param investType 资金运用方式
     */
    public void setInvestType(String investType) {
        this.investType = investType == null ? null : investType.trim();
    }

    /**
     * 取得 资金运用行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @return 资金运用行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public String getCapUseIndu() {
        return capUseIndu;
    }

    /**
     * 设置 资金运用行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     * @param capUseIndu 资金运用行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    public void setCapUseIndu(String capUseIndu) {
        this.capUseIndu = capUseIndu == null ? null : capUseIndu.trim();
    }

    /**
     * 取得 资管计划成立日期
     * @return 资管计划成立日期
     */
    public Date getPlanSdate() {
        return planSdate;
    }

    /**
     * 设置 资管计划成立日期
     * @param planSdate 资管计划成立日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setPlanSdate(Date planSdate) {
        this.planSdate = planSdate;
    }

    /**
     * 取得 资管计划终止日期
     * @return 资管计划终止日期
     */
    public Date getPlanEdate() {
        return planEdate;
    }

    /**
     * 设置 资管计划终止日期
     * @param planEdate 资管计划终止日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setPlanEdate(Date planEdate) {
        this.planEdate = planEdate;
    }

    /**
     * 取得 资管计划属性/委外投资属性 01 集合/02 单一/03 专项/04 定向/05 专户/99 其他
     * @return 资管计划属性/委外投资属性 01 集合/02 单一/03 专项/04 定向/05 专户/99 其他
     */
    public String getProProperty() {
        return proProperty;
    }

    /**
     * 设置 资管计划属性/委外投资属性 01 集合/02 单一/03 专项/04 定向/05 专户/99 其他
     * @param proProperty 资管计划属性/委外投资属性 01 集合/02 单一/03 专项/04 定向/05 专户/99 其他
     */
    public void setProProperty(String proProperty) {
        this.proProperty = proProperty == null ? null : proProperty.trim();
    }

    /**
     * 取得 最高预期收益率
     * @return 最高预期收益率
     */
    public BigDecimal getMaxRate() {
        return maxRate;
    }

    /**
     * 设置 最高预期收益率
     * @param maxRate 最高预期收益率
     */
    public void setMaxRate(BigDecimal maxRate) {
        this.maxRate = maxRate;
    }

    /**
     * 取得 最低预期收益率
     * @return 最低预期收益率
     */
    public BigDecimal getMinRate() {
        return minRate;
    }

    /**
     * 设置 最低预期收益率
     * @param minRate 最低预期收益率
     */
    public void setMinRate(BigDecimal minRate) {
        this.minRate = minRate;
    }

    /**
     * 取得 负债规模
     * @return 负债规模
     */
    public BigDecimal getDebtScale() {
        return debtScale;
    }

    /**
     * 设置 负债规模
     * @param debtScale 负债规模
     */
    public void setDebtScale(BigDecimal debtScale) {
        this.debtScale = debtScale;
    }

    /**
     * 取得 购买结构 01 优先/02 劣后/03 整体
     * @return 购买结构 01 优先/02 劣后/03 整体
     */
    public String getBuyStruct() {
        return buyStruct;
    }

    /**
     * 设置 购买结构 01 优先/02 劣后/03 整体
     * @param buyStruct 购买结构 01 优先/02 劣后/03 整体
     */
    public void setBuyStruct(String buyStruct) {
        this.buyStruct = buyStruct == null ? null : buyStruct.trim();
    }

    /**
     * 取得 管理方式 01 主动（自主管理）/02被动（委托管理）/99其他
     * @return 管理方式 01 主动（自主管理）/02被动（委托管理）/99其他
     */
    public String getManageType() {
        return manageType;
    }

    /**
     * 设置 管理方式 01 主动（自主管理）/02被动（委托管理）/99其他
     * @param manageType 管理方式 01 主动（自主管理）/02被动（委托管理）/99其他
     */
    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    /**
     * 取得 是否掌控计划底层资产详情 01 是/ 02 否
     * @return 是否掌控计划底层资产详情 01 是/ 02 否
     */
    public String getIsCtrlBottom() {
        return isCtrlBottom;
    }


    public String getIsConstracteBusi() {
        return isConstracteBusi;
    }

    public void setIsConstracteBusi(String isConstracteBusi) {
        this.isConstracteBusi = isConstracteBusi;
    }

    /**
     * 设置 是否掌控计划底层资产详情 01 是/ 02 否
     * @param isCtrlBottom 是否掌控计划底层资产详情 01 是/ 02 否
     */
    public void setIsCtrlBottom(String isCtrlBottom) {
        this.isCtrlBottom = isCtrlBottom == null ? null : isCtrlBottom.trim();
    }

    /**
     * 取得 管理费率
     * @return 管理费率
     */
    public BigDecimal getMgrFeeRate() {
        return mgrFeeRate;
    }

    /**
     * 设置 管理费率
     * @param mgrFeeRate 管理费率
     */
    public void setMgrFeeRate(BigDecimal mgrFeeRate) {
        this.mgrFeeRate = mgrFeeRate;
    }

    /**
     * 取得 托管费率
     * @return 托管费率
     */
    public BigDecimal getTruFeeRate() {
        return truFeeRate;
    }

    /**
     * 设置 托管费率
     * @param truFeeRate 托管费率
     */
    public void setTruFeeRate(BigDecimal truFeeRate) {
        this.truFeeRate = truFeeRate;
    }

    /**
     * 取得 交易相关合计费率
     * @return 交易相关合计费率
     */
    public BigDecimal getTradeFeeRate() {
        return tradeFeeRate;
    }

    /**
     * 设置 交易相关合计费率
     * @param tradeFeeRate 交易相关合计费率
     */
    public void setTradeFeeRate(BigDecimal tradeFeeRate) {
        this.tradeFeeRate = tradeFeeRate;
    }

    /**
     * 取得 中介服务机构合计费率
     * @return 中介服务机构合计费率
     */
    public BigDecimal getAgencyFeeRate() {
        return agencyFeeRate;
    }

    /**
     * 设置 中介服务机构合计费率
     * @param agencyFeeRate 中介服务机构合计费率
     */
    public void setAgencyFeeRate(BigDecimal agencyFeeRate) {
        this.agencyFeeRate = agencyFeeRate;
    }

    /**
     * 取得 其他合计费率
     * @return 其他合计费率
     */
    public BigDecimal getOtherFeeRate() {
        return otherFeeRate;
    }

    /**
     * 设置 其他合计费率
     * @param otherFeeRate 其他合计费率
     */
    public void setOtherFeeRate(BigDecimal otherFeeRate) {
        this.otherFeeRate = otherFeeRate;
    }

    /**
     * 取得 资产价值
     * @return 资产价值
     */
    public BigDecimal getAssetValue() {
        return assetValue;
    }

    /**
     * 设置 资产价值
     * @param assetValue 资产价值
     */
    public void setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
    }

    /**
     * 取得 登记备案机构 01 中央国债登记结算有限责任公司/ 02 上海清算所 / 03 中国证券登记结算有限责任公司 / 04 中国证券投资基金业协会 /99 其他
     * @return 登记备案机构 01 中央国债登记结算有限责任公司/ 02 上海清算所 / 03 中国证券登记结算有限责任公司 / 04 中国证券投资基金业协会 /99 其他
     */
    public String getRegistOrg1() {
        return registOrg1;
    }

    /**
     * 设置 登记备案机构 01 中央国债登记结算有限责任公司/ 02 上海清算所 / 03 中国证券登记结算有限责任公司 / 04 中国证券投资基金业协会 /99 其他
     * @param registOrg1 登记备案机构 01 中央国债登记结算有限责任公司/ 02 上海清算所 / 03 中国证券登记结算有限责任公司 / 04 中国证券投资基金业协会 /99 其他
     */
    public void setRegistOrg1(String registOrg1) {
        this.registOrg1 = registOrg1 == null ? null : registOrg1.trim();
    }

    /**
     * 取得 基金管理机构名称
     * @return 基金管理机构名称
     */
    public String getMngOrgName() {
        return mngOrgName;
    }

    /**
     * 设置 基金管理机构名称
     * @param mngOrgName 基金管理机构名称
     */
    public void setMngOrgName(String mngOrgName) {
        this.mngOrgName = mngOrgName == null ? null : mngOrgName.trim();
    }

    /**
     * 取得 基金托管机构名称
     * @return 基金托管机构名称
     */
    public String getTruOrgName() {
        return truOrgName;
    }

    /**
     * 设置 基金托管机构名称
     * @param truOrgName 基金托管机构名称
     */
    public void setTruOrgName(String truOrgName) {
        this.truOrgName = truOrgName == null ? null : truOrgName.trim();
    }

    /**
     * 取得 基金投资资产
     * @return 基金投资资产
     */
    public String getFundInvestAsset() {
        return fundInvestAsset;
    }

    /**
     * 设置 基金投资资产
     * @param fundInvestAsset 基金投资资产
     */
    public void setFundInvestAsset(String fundInvestAsset) {
        this.fundInvestAsset = fundInvestAsset == null ? null : fundInvestAsset.trim();
    }

    /**
     * 取得 行内资产类别 01 场内股票质押回购/02 场外股票质押融资/03 融资租赁/99 其他
     * @return 行内资产类别 01 场内股票质押回购/02 场外股票质押融资/03 融资租赁/99 其他
     */
    public String getInAssetType() {
        return inAssetType;
    }

    /**
     * 设置 行内资产类别 01 场内股票质押回购/02 场外股票质押融资/03 融资租赁/99 其他
     * @param inAssetType 行内资产类别 01 场内股票质押回购/02 场外股票质押融资/03 融资租赁/99 其他
     */
    public void setInAssetType(String inAssetType) {
        this.inAssetType = inAssetType == null ? null : inAssetType.trim();
    }

    /**
     * 取得 行内资产类别说明
     * @return 行内资产类别说明
     */
    public String getInAssetTypeCmm() {
        return inAssetTypeCmm;
    }

    /**
     * 设置 行内资产类别说明
     * @param inAssetTypeCmm 行内资产类别说明
     */
    public void setInAssetTypeCmm(String inAssetTypeCmm) {
        this.inAssetTypeCmm = inAssetTypeCmm == null ? null : inAssetTypeCmm.trim();
    }

    /**
     * 取得 备注
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 取得 是否底层资产 01 是/ 02 否
     * @return 是否底层资产 01 是/ 02 否
     */
    public String getIsUnderAsset() {
        return isUnderAsset;
    }

    /**
     * 设置 是否底层资产 01 是/ 02 否
     * @param isUnderAsset 是否底层资产 01 是/ 02 否
     */
    public void setIsUnderAsset(String isUnderAsset) {
        this.isUnderAsset = isUnderAsset == null ? null : isUnderAsset.trim();
    }

    /**
     * 取得 是否属于政府投资基金 01 是/ 02 否
     * @return 是否属于政府投资基金 01 是/ 02 否
     */
    public String getIsGovernFund() {
        return isGovernFund;
    }

    /**
     * 设置 是否属于政府投资基金 01 是/ 02 否
     * @param isGovernFund 是否属于政府投资基金 01 是/ 02 否
     */
    public void setIsGovernFund(String isGovernFund) {
        this.isGovernFund = isGovernFund == null ? null : isGovernFund.trim();
    }

    /**
     * 取得 政府投资基金投向 01 投向创业早期企业/ 02 投向中小微企业/ 03 投向产业转型升级和发展/ 04 投向基础设施和公共服务领域/99 其他
     * @return 政府投资基金投向 01 投向创业早期企业/ 02 投向中小微企业/ 03 投向产业转型升级和发展/ 04 投向基础设施和公共服务领域/99 其他
     */
    public String getGovernInvestFund() {
        return governInvestFund;
    }

    /**
     * 设置 政府投资基金投向 01 投向创业早期企业/ 02 投向中小微企业/ 03 投向产业转型升级和发展/ 04 投向基础设施和公共服务领域/99 其他
     * @param governInvestFund 政府投资基金投向 01 投向创业早期企业/ 02 投向中小微企业/ 03 投向产业转型升级和发展/ 04 投向基础设施和公共服务领域/99 其他
     */
    public void setGovernInvestFund(String governInvestFund) {
        this.governInvestFund = governInvestFund == null ? null : governInvestFund.trim();
    }

    /**
     * 取得 基金公司名称
     * @return 基金公司名称
     */
    public String getFundEnpeName() {
        return fundEnpeName;
    }

    /**
     * 设置 基金公司名称
     * @param fundEnpeName 基金公司名称
     */
    public void setFundEnpeName(String fundEnpeName) {
        this.fundEnpeName = fundEnpeName == null ? null : fundEnpeName.trim();
    }

    /**
     * 取得 受托人
     * @return 受托人
     */
    public String getTrustee() {
        return trustee;
    }

    /**
     * 设置 受托人
     * @param trustee 受托人
     */
    public void setTrustee(String trustee) {
        this.trustee = trustee == null ? null : trustee.trim();
    }

    /**
     * 取得 部门代码
     * @return 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 取得 机构代码
     * @return 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 取得 数据来源
     * @return 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 设置 数据来源
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    /**
     * 取得 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     * @return 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    public String getPrivatefundFlag() {
        return privatefundFlag;
    }

    /**
     * 设置 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     * @param privatefundFlag 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    public void setPrivatefundFlag(String privatefundFlag) {
        this.privatefundFlag = privatefundFlag == null ? null : privatefundFlag.trim();
    }

    /**
     * 取得 是否投向金融资产投资公司发行的私募资产管理产品
     * @return 是否投向金融资产投资公司发行的私募资产管理产品
     */
    public String getPrivateprdFlag() {
        return privateprdFlag;
    }

    /**
     * 设置 是否投向金融资产投资公司发行的私募资产管理产品
     * @param privateprdFlag 是否投向金融资产投资公司发行的私募资产管理产品
     */
    public void setPrivateprdFlag(String privateprdFlag) {
        this.privateprdFlag = privateprdFlag == null ? null : privateprdFlag.trim();
    }

    /**
     * 取得 是否投向“市场化债转股相关产品的部分”
     * @return 是否投向“市场化债转股相关产品的部分”
     */
    public String getBondtostockFlag() {
        return bondtostockFlag;
    }

    /**
     * 设置 是否投向“市场化债转股相关产品的部分”
     * @param bondtostockFlag 是否投向“市场化债转股相关产品的部分”
     */
    public void setBondtostockFlag(String bondtostockFlag) {
        this.bondtostockFlag = bondtostockFlag == null ? null : bondtostockFlag.trim();
    }

    /**
     * 取得 公共PPP
     * @return 公共PPP
     */
    public String getPppflag() {
        return pppflag;
    }

    /**
     * 设置 公共PPP
     * @param pppflag 公共PPP
     */
    public void setPppflag(String pppflag) {
        this.pppflag = pppflag == null ? null : pppflag.trim();
    }

    /**
     * 取得 数据日期
     * @return 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 设置 数据日期
     * @param statusDate 数据日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 取得 资金运用行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @return 资金运用行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public String getCapUseInduLevel2() {
        return capUseInduLevel2;
    }

    /**
     * 设置 资金运用行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     * @param capUseInduLevel2 资金运用行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    public void setCapUseInduLevel2(String capUseInduLevel2) {
        this.capUseInduLevel2 = capUseInduLevel2 == null ? null : capUseInduLevel2.trim();
    }

    /**
     * 取得 发行机构
     * @return 发行机构
     */
    public String getIssuePartyName() {
        return issuePartyName;
    }

    /**
     * 设置 发行机构
     * @param issuePartyName 发行机构
     */
    public void setIssuePartyName(String issuePartyName) {
        this.issuePartyName = issuePartyName == null ? null : issuePartyName.trim();
    }

    /**
     * 取得 债券信用评级
     * @return 债券信用评级
     */
    public String getSecCreditGradeType() {
        return secCreditGradeType;
    }

    /**
     * 设置 债券信用评级
     * @param secCreditGradeType 债券信用评级
     */
    public void setSecCreditGradeType(String secCreditGradeType) {
        this.secCreditGradeType = secCreditGradeType == null ? null : secCreditGradeType.trim();
    }

    /**
     * 取得 债券主体评级
     * @return 债券主体评级
     */
    public String getSecMainGrade() {
        return secMainGrade;
    }

    /**
     * 设置 债券主体评级
     * @param secMainGrade 债券主体评级
     */
    public void setSecMainGrade(String secMainGrade) {
        this.secMainGrade = secMainGrade == null ? null : secMainGrade.trim();
    }

    /**
     * 取得 境内外
     * @return 境内外
     */
    public String getBlnArea() {
        return blnArea;
    }

    /**
     * 设置 境内外
     * @param blnArea 境内外
     */
    public void setBlnArea(String blnArea) {
        this.blnArea = blnArea == null ? null : blnArea.trim();
    }

    /**
     * 取得 融资人所属行业一级分类
     * @return 融资人所属行业一级分类
     */
    public String getIndustryPbc() {
        return industryPbc;
    }

    /**
     * 设置 融资人所属行业一级分类
     * @param industryPbc 融资人所属行业一级分类
     */
    public void setIndustryPbc(String industryPbc) {
        this.industryPbc = industryPbc == null ? null : industryPbc.trim();
    }

    /**
     * 取得 客户主体类型,数据字典:SVR_CUSTOMER_PRIN_TYPE
     * @return 客户主体类型,数据字典:SVR_CUSTOMER_PRIN_TYPE
     */
    public String getCustomerObjType() {
        return customerObjType;
    }

    /**
     * 设置 客户主体类型,数据字典:SVR_CUSTOMER_PRIN_TYPE
     * @param customerObjType 客户主体类型,数据字典:SVR_CUSTOMER_PRIN_TYPE
     */
    public void setCustomerObjType(String customerObjType) {
        this.customerObjType = customerObjType == null ? null : customerObjType.trim();
    }

    /**
     * 取得 上游资产一级分类
     * @return 上游资产一级分类
     */
    public String getOrgAssetType1() {
        return orgAssetType1;
    }

    /**
     * 设置 上游资产一级分类
     * @param orgAssetType1 上游资产一级分类
     */
    public void setOrgAssetType1(String orgAssetType1) {
        this.orgAssetType1 = orgAssetType1 == null ? null : orgAssetType1.trim();
    }

    /**
     * 取得 上游资产二级分类
     * @return 上游资产二级分类
     */
    public String getOrgAssetType2() {
        return orgAssetType2;
    }

    /**
     * 设置 上游资产二级分类
     * @param orgAssetType2 上游资产二级分类
     */
    public void setOrgAssetType2(String orgAssetType2) {
        this.orgAssetType2 = orgAssetType2 == null ? null : orgAssetType2.trim();
    }

    /**
     * 取得 上游资产三级分类
     * @return 上游资产三级分类
     */
    public String getOrgAssetType3() {
        return orgAssetType3;
    }

    /**
     * 设置 上游资产三级分类
     * @param orgAssetType3 上游资产三级分类
     */
    public void setOrgAssetType3(String orgAssetType3) {
        this.orgAssetType3 = orgAssetType3 == null ? null : orgAssetType3.trim();
    }

    /**
     * 取得 发行人/融资人所属地区
     * @return 发行人/融资人所属地区
     */
    public String getBuyPlace() {
        return buyPlace;
    }

    /**
     * 设置 发行人/融资人所属地区
     * @param buyPlace 发行人/融资人所属地区
     */
    public void setBuyPlace(String buyPlace) {
        this.buyPlace = buyPlace == null ? null : buyPlace.trim();
    }

    /**
     * 取得 历史资产代码，数据移植时刷入
     * @return 历史资产代码，数据移植时刷入
     */
    public String getAssetCodeOld() {
        return assetCodeOld;
    }

    /**
     * 设置 历史资产代码，数据移植时刷入
     * @param assetCodeOld 历史资产代码，数据移植时刷入
     */
    public void setAssetCodeOld(String assetCodeOld) {
        this.assetCodeOld = assetCodeOld == null ? null : assetCodeOld.trim();
    }

    /**
     * 取得 二级资产细分类:资产/负债二级类别=2201 信贷资产流转项目时必填，枚举值：01 小微企业信贷资产流转和收益权转让/02 信贷资产收益权转让
     * @return 二级资产细分类:资产/负债二级类别=2201 信贷资产流转项目时必填，枚举值：01 小微企业信贷资产流转和收益权转让/02 信贷资产收益权转让
     */
    public String getAssetType3() {
        return assetType3;
    }

    /**
     * 设置 二级资产细分类:资产/负债二级类别=2201 信贷资产流转项目时必填，枚举值：01 小微企业信贷资产流转和收益权转让/02 信贷资产收益权转让
     * @param assetType3 二级资产细分类:资产/负债二级类别=2201 信贷资产流转项目时必填，枚举值：01 小微企业信贷资产流转和收益权转让/02 信贷资产收益权转让
     */
    public void setAssetType3(String assetType3) {
        this.assetType3 = assetType3 == null ? null : assetType3.trim();
    }

    /**
     * 取得 人行资产一级分类
     * @return 人行资产一级分类
     */
    public String getPbcAssetType1() {
        return pbcAssetType1;
    }

    /**
     * 设置 人行资产一级分类
     * @param pbcAssetType1 人行资产一级分类
     */
    public void setPbcAssetType1(String pbcAssetType1) {
        this.pbcAssetType1 = pbcAssetType1 == null ? null : pbcAssetType1.trim();
    }

    /**
     * 取得 人行资产二级分类
     * @return 人行资产二级分类
     */
    public String getPbcAssetType2() {
        return pbcAssetType2;
    }

    /**
     * 设置 人行资产二级分类
     * @param pbcAssetType2 人行资产二级分类
     */
    public void setPbcAssetType2(String pbcAssetType2) {
        this.pbcAssetType2 = pbcAssetType2 == null ? null : pbcAssetType2.trim();
    }

    /**
     * 取得 人行资产三级分类
     * @return 人行资产三级分类
     */
    public String getPbcAssetType3() {
        return pbcAssetType3;
    }

    /**
     * 设置 人行资产三级分类
     * @param pbcAssetType3 人行资产三级分类
     */
    public void setPbcAssetType3(String pbcAssetType3) {
        this.pbcAssetType3 = pbcAssetType3 == null ? null : pbcAssetType3.trim();
    }

    /**
     * 取得 上游资产四级分类
     * @return 上游资产四级分类
     */
    public String getOrgAssetType4() {
        return orgAssetType4;
    }

    /**
     * 设置 上游资产四级分类
     * @param orgAssetType4 上游资产四级分类
     */
    public void setOrgAssetType4(String orgAssetType4) {
        this.orgAssetType4 = orgAssetType4 == null ? null : orgAssetType4.trim();
    }

    /**
     * 取得 上游资产五级分类
     * @return 上游资产五级分类
     */
    public String getOrgAssetType5() {
        return orgAssetType5;
    }

    /**
     * 设置 上游资产五级分类
     * @param orgAssetType5 上游资产五级分类
     */
    public void setOrgAssetType5(String orgAssetType5) {
        this.orgAssetType5 = orgAssetType5 == null ? null : orgAssetType5.trim();
    }

    /**
     * 取得 是否投向地方政府融资平台
     * @return 是否投向地方政府融资平台
     */
    public String getPrivateFlag() {
        return privateFlag;
    }

    /**
     * 设置 是否投向地方政府融资平台
     * @param privateFlag 是否投向地方政府融资平台
     */
    public void setPrivateFlag(String privateFlag) {
        this.privateFlag = privateFlag == null ? null : privateFlag.trim();
    }

    /**
     * 取得 人行机构规模
     * @return 人行机构规模
     */
    public String getInstitSize() {
        return institSize;
    }

    /**
     * 设置 人行机构规模
     * @param institSize 人行机构规模
     */
    public void setInstitSize(String institSize) {
        this.institSize = institSize == null ? null : institSize.trim();
    }

    /**
     * 取得 交易所基金类型
     * @return 交易所基金类型
     */
    public String getTrmFundType() {
        return trmFundType;
    }

    /**
     * 设置 交易所基金类型
     * @param trmFundType 交易所基金类型
     */
    public void setTrmFundType(String trmFundType) {
        this.trmFundType = trmFundType == null ? null : trmFundType.trim();
    }

    /**
     * 取得 商业银行二级资本债
     * @return 商业银行二级资本债
     */
    public String getBankSecCapital() {
        return bankSecCapital;
    }

    /**
     * 设置 商业银行二级资本债
     * @param bankSecCapital 商业银行二级资本债
     */
    public void setBankSecCapital(String bankSecCapital) {
        this.bankSecCapital = bankSecCapital == null ? null : bankSecCapital.trim();
    }

    /**
     * 取得 商业银行优先股
     * @return 商业银行优先股
     */
    public String getBankSecPrefer() {
        return bankSecPrefer;
    }

    /**
     * 设置 商业银行优先股
     * @param bankSecPrefer 商业银行优先股
     */
    public void setBankSecPrefer(String bankSecPrefer) {
        this.bankSecPrefer = bankSecPrefer == null ? null : bankSecPrefer.trim();
    }

    /**
     * 取得 商业银行永续债
     * @return 商业银行永续债
     */
    public String getBankSecPerpetual() {
        return bankSecPerpetual;
    }

    /**
     * 设置 商业银行永续债
     * @param bankSecPerpetual 商业银行永续债
     */
    public void setBankSecPerpetual(String bankSecPerpetual) {
        this.bankSecPerpetual = bankSecPerpetual == null ? null : bankSecPerpetual.trim();
    }

    /**
     * 取得 存款类型
     * @return 存款类型
     */
    public String getDepoType() {
        return depoType;
    }

    /**
     * 设置 存款类型
     * @param depoType 存款类型
     */
    public void setDepoType(String depoType) {
        this.depoType = depoType == null ? null : depoType.trim();
    }

    /**
     * 取得 对手方类型
     * @return 对手方类型
     */
    public String getRivalPartyType() {
        return rivalPartyType;
    }

    /**
     * 设置 对手方类型
     * @param rivalPartyType 对手方类型
     */
    public void setRivalPartyType(String rivalPartyType) {
        this.rivalPartyType = rivalPartyType == null ? null : rivalPartyType.trim();
    }

    /**
     * 取得 份额面值
     * @return 份额面值
     */
    public BigDecimal getPrinBaseamt() {
        return prinBaseamt;
    }

    /**
     * 设置 份额面值
     * @param prinBaseamt 份额面值
     */
    public void setPrinBaseamt(BigDecimal prinBaseamt) {
        this.prinBaseamt = prinBaseamt;
    }

    /**
     * 取得 法定到期日
     * @return 法定到期日
     */
    public Date getFmdate() {
        return fmdate;
    }

    /**
     * 设置 法定到期日
     * @param fmdate 法定到期日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setFmdate(Date fmdate) {
        this.fmdate = fmdate;
    }

    /**
     * 取得 计息类型
     * @return 计息类型
     */
    public String getCouponSpecies() {
        return couponSpecies;
    }

    /**
     * 设置 计息类型
     * @param couponSpecies 计息类型
     */
    public void setCouponSpecies(String couponSpecies) {
        this.couponSpecies = couponSpecies == null ? null : couponSpecies.trim();
    }

    /**
     * 取得 规则付息标识
     * @return 规则付息标识
     */
    public String getInterestRuleFlag() {
        return interestRuleFlag;
    }

    /**
     * 设置 规则付息标识
     * @param interestRuleFlag 规则付息标识
     */
    public void setInterestRuleFlag(String interestRuleFlag) {
        this.interestRuleFlag = interestRuleFlag == null ? null : interestRuleFlag.trim();
    }

    /**
     * 取得 利息分布方式
     * @return 利息分布方式
     */
    public String getIntpayRule() {
        return intpayRule;
    }

    /**
     * 设置 利息分布方式
     * @param intpayRule 利息分布方式
     */
    public void setIntpayRule(String intpayRule) {
        this.intpayRule = intpayRule == null ? null : intpayRule.trim();
    }

    /**
     * 取得 是否有浮动因子
     * @return 是否有浮动因子
     */
    public String getFloatFactorFlag() {
        return floatFactorFlag;
    }

    /**
     * 设置 是否有浮动因子
     * @param floatFactorFlag 是否有浮动因子
     */
    public void setFloatFactorFlag(String floatFactorFlag) {
        this.floatFactorFlag = floatFactorFlag == null ? null : floatFactorFlag.trim();
    }

    /**
     * 取得 结构档次
     * @return 结构档次
     */
    public String getStructGrade() {
        return structGrade;
    }

    /**
     * 设置 结构档次
     * @param structGrade 结构档次
     */
    public void setStructGrade(String structGrade) {
        this.structGrade = structGrade == null ? null : structGrade.trim();
    }

    /**
     * 取得 还本方式
     * @return 还本方式
     */
    public String getRepaymentType() {
        return repaymentType;
    }

    /**
     * 设置 还本方式
     * @param repaymentType 还本方式
     */
    public void setRepaymentType(String repaymentType) {
        this.repaymentType = repaymentType == null ? null : repaymentType.trim();
    }

    /**
     * 取得 分期还本条款标识
     * @return 分期还本条款标识
     */
    public String getPeriodRepaymentFlag() {
        return periodRepaymentFlag;
    }

    /**
     * 设置 分期还本条款标识
     * @param periodRepaymentFlag 分期还本条款标识
     */
    public void setPeriodRepaymentFlag(String periodRepaymentFlag) {
        this.periodRepaymentFlag = periodRepaymentFlag == null ? null : periodRepaymentFlag.trim();
    }

    /**
     * 取得 基础资产类型
     * @return 基础资产类型
     */
    public String getBasicAssetType() {
        return basicAssetType;
    }

    /**
     * 设置 基础资产类型
     * @param basicAssetType 基础资产类型
     */
    public void setBasicAssetType(String basicAssetType) {
        this.basicAssetType = basicAssetType == null ? null : basicAssetType.trim();
    }

    /**
     * 取得 超额收益分配比例%
     * @return 超额收益分配比例%
     */
    public BigDecimal getExcessProfitRate() {
        return excessProfitRate;
    }

    /**
     * 设置 超额收益分配比例%
     * @param excessProfitRate 超额收益分配比例%
     */
    public void setExcessProfitRate(BigDecimal excessProfitRate) {
        this.excessProfitRate = excessProfitRate;
    }

    /**
     * 取得 抵质押物类型
     * @return 抵质押物类型
     */
    public String getPledgeType() {
        return pledgeType;
    }

    /**
     * 设置 抵质押物类型
     * @param pledgeType 抵质押物类型
     */
    public void setPledgeType(String pledgeType) {
        this.pledgeType = pledgeType == null ? null : pledgeType.trim();
    }

    /**
     * 取得 抵质押物价值（元）
     * @return 抵质押物价值（元）
     */
    public BigDecimal getPledgeValue() {
        return pledgeValue;
    }

    /**
     * 设置 抵质押物价值（元）
     * @param pledgeValue 抵质押物价值（元）
     */
    public void setPledgeValue(BigDecimal pledgeValue) {
        this.pledgeValue = pledgeValue;
    }

    /**
     * 取得 担保性质
     * @return 担保性质
     */
    public String getNatureGuarantee() {
        return natureGuarantee;
    }

    /**
     * 设置 担保性质
     * @param natureGuarantee 担保性质
     */
    public void setNatureGuarantee(String natureGuarantee) {
        this.natureGuarantee = natureGuarantee == null ? null : natureGuarantee.trim();
    }

    /**
     * 取得 担保人与融资人关系
     * @return 担保人与融资人关系
     */
    public String getRelationFinan() {
        return relationFinan;
    }

    /**
     * 设置 担保人与融资人关系
     * @param relationFinan 担保人与融资人关系
     */
    public void setRelationFinan(String relationFinan) {
        this.relationFinan = relationFinan == null ? null : relationFinan.trim();
    }

    /**
     * 取得 含权类型
     * @return 含权类型
     */
    public String getContainType() {
        return containType;
    }

    /**
     * 设置 含权类型
     * @param containType 含权类型
     */
    public void setContainType(String containType) {
        this.containType = containType == null ? null : containType.trim();
    }

    /**
     * 取得 行权方式
     * @return 行权方式
     */
    public String getExerciseType() {
        return exerciseType;
    }

    /**
     * 设置 行权方式
     * @param exerciseType 行权方式
     */
    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType == null ? null : exerciseType.trim();
    }

    /**
     * 取得 固定行权日
     * @return 固定行权日
     */
    public Date getExerciseDate() {
        return exerciseDate;
    }

    /**
     * 设置 固定行权日
     * @param exerciseDate 固定行权日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setExerciseDate(Date exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    /**
     * 取得 首次行权日期
     * @return 首次行权日期
     */
    public Date getFirstExerciseDate() {
        return firstExerciseDate;
    }

    /**
     * 设置 首次行权日期
     * @param firstExerciseDate 首次行权日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setFirstExerciseDate(Date firstExerciseDate) {
        this.firstExerciseDate = firstExerciseDate;
    }

    /**
     * 取得 行权周期
     * @return 行权周期
     */
    public Integer getExercisePeriod() {
        return exercisePeriod;
    }

    /**
     * 设置 行权周期
     * @param exercisePeriod 行权周期
     */
    public void setExercisePeriod(Integer exercisePeriod) {
        this.exercisePeriod = exercisePeriod;
    }

    /**
     * 取得 行权价格
     * @return 行权价格
     */
    public BigDecimal getExercisePrice() {
        return exercisePrice;
    }

    /**
     * 设置 行权价格
     * @param exercisePrice 行权价格
     */
    public void setExercisePrice(BigDecimal exercisePrice) {
        this.exercisePrice = exercisePrice;
    }

    /**
     * 取得 永续条款类型
     * @return 永续条款类型
     */
    public String getPerpetualClauseType() {
        return perpetualClauseType;
    }


    public String getIsBankCap() {
        return isBankCap;
    }

    public void setIsBankCap(String isBankCap) {
        this.isBankCap = isBankCap;
    }

    /**
     * 设置 永续条款类型
     * @param perpetualClauseType 永续条款类型
     */
    public void setPerpetualClauseType(String perpetualClauseType) {
        this.perpetualClauseType = perpetualClauseType == null ? null : perpetualClauseType.trim();
    }

    /**
     * 取得 利息递延条款类型
     * @return 利息递延条款类型
     */
    public String getDeferredInterestType() {
        return deferredInterestType;
    }

    /**
     * 设置 利息递延条款类型
     * @param deferredInterestType 利息递延条款类型
     */
    public void setDeferredInterestType(String deferredInterestType) {
        this.deferredInterestType = deferredInterestType == null ? null : deferredInterestType.trim();
    }

    /**
     * 取得 递延利息是否计息
     * @return 递延利息是否计息
     */
    public String getDeferredInterestInt() {
        return deferredInterestInt;
    }

    /**
     * 设置 递延利息是否计息
     * @param deferredInterestInt 递延利息是否计息
     */
    public void setDeferredInterestInt(String deferredInterestInt) {
        this.deferredInterestInt = deferredInterestInt == null ? null : deferredInterestInt.trim();
    }

    /**
     * 取得 首次重定价日期
     * @return 首次重定价日期
     */
    public Date getFirstReprceDate() {
        return firstReprceDate;
    }

    /**
     * 设置 首次重定价日期
     * @param firstReprceDate 首次重定价日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setFirstReprceDate(Date firstReprceDate) {
        this.firstReprceDate = firstReprceDate;
    }

    /**
     * 取得 重定价周期
     * @return 重定价周期
     */
    public Integer getReprceCycle() {
        return reprceCycle;
    }

    /**
     * 设置 重定价周期
     * @param reprceCycle 重定价周期
     */
    public void setReprceCycle(Integer reprceCycle) {
        this.reprceCycle = reprceCycle;
    }

    /**
     * 取得 部分赎回标识
     * @return 部分赎回标识
     */
    public String getPartRedemFlag() {
        return partRedemFlag;
    }

    /**
     * 设置 部分赎回标识
     * @param partRedemFlag 部分赎回标识
     */
    public void setPartRedemFlag(String partRedemFlag) {
        this.partRedemFlag = partRedemFlag == null ? null : partRedemFlag.trim();
    }

    /**
     * 取得 部分赎回比例%
     * @return 部分赎回比例%
     */
    public BigDecimal getPartRedemRate() {
        return partRedemRate;
    }

    /**
     * 设置 部分赎回比例%
     * @param partRedemRate 部分赎回比例%
     */
    public void setPartRedemRate(BigDecimal partRedemRate) {
        this.partRedemRate = partRedemRate;
    }

    /**
     * 取得 增信机构代码
     * @return 增信机构代码
     */
    public String getCreditOrgCode() {
        return creditOrgCode;
    }

    /**
     * 设置 增信机构代码
     * @param creditOrgCode 增信机构代码
     */
    public void setCreditOrgCode(String creditOrgCode) {
        this.creditOrgCode = creditOrgCode == null ? null : creditOrgCode.trim();
    }

    /**
     * 取得 增信机构名称
     * @return 增信机构名称
     */
    public String getCreditOrgName() {
        return creditOrgName;
    }

    /**
     * 设置 增信机构名称
     * @param creditOrgName 增信机构名称
     */
    public void setCreditOrgName(String creditOrgName) {
        this.creditOrgName = creditOrgName == null ? null : creditOrgName.trim();
    }

    /**
     * 取得 股权退出安排
     * @return 股权退出安排
     */
    public Date getEquityQuit() {
        return equityQuit;
    }

    /**
     * 设置 股权退出安排
     * @param equityQuit 股权退出安排
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setEquityQuit(Date equityQuit) {
        this.equityQuit = equityQuit;
    }

    /**
     * 取得 是否为债转股
     * @return 是否为债转股
     */
    public String getDeptEquity() {
        return deptEquity;
    }

    /**
     * 设置 是否为债转股
     * @param deptEquity 是否为债转股
     */
    public void setDeptEquity(String deptEquity) {
        this.deptEquity = deptEquity == null ? null : deptEquity.trim();
    }

    /**
     * 取得 持有目的
     * @return 持有目的
     */
    public String getHoldPurpose() {
        return holdPurpose;
    }

    /**
     * 设置 持有目的
     * @param holdPurpose 持有目的
     */
    public void setHoldPurpose(String holdPurpose) {
        this.holdPurpose = holdPurpose == null ? null : holdPurpose.trim();
    }

    /**
     * 取得 是否由金融资产投资公司发行
     * @return 是否由金融资产投资公司发行
     */
    public String getFinancialComissue() {
        return financialComissue;
    }

    /**
     * 设置 是否由金融资产投资公司发行
     * @param financialComissue 是否由金融资产投资公司发行
     */
    public void setFinancialComissue(String financialComissue) {
        this.financialComissue = financialComissue == null ? null : financialComissue.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getIsChl() {
        return isChl;
    }

    /**
     * 设置 null
     * @param isChl null
     */
    public void setIsChl(String isChl) {
        this.isChl = isChl == null ? null : isChl.trim();
    }

    /**
     * 取得 发行人代码
     * @return 发行人代码
     */
    public String getChbIssuerId() {
        return chbIssuerId;
    }

    /**
     * 设置 发行人代码
     * @param chbIssuerId 发行人代码
     */
    public void setChbIssuerId(String chbIssuerId) {
        this.chbIssuerId = chbIssuerId == null ? null : chbIssuerId.trim();
    }

    /**
     * 取得 管理人代码
     * @return 管理人代码
     */
    public String getChbManagerId() {
        return chbManagerId;
    }

    /**
     * 设置 管理人代码
     * @param chbManagerId 管理人代码
     */
    public void setChbManagerId(String chbManagerId) {
        this.chbManagerId = chbManagerId == null ? null : chbManagerId.trim();
    }

    /**
     * 取得 融资人代码
     * @return 融资人代码
     */
    public String getChbFinancierId() {
        return chbFinancierId;
    }

    /**
     * 设置 融资人代码
     * @param chbFinancierId 融资人代码
     */
    public void setChbFinancierId(String chbFinancierId) {
        this.chbFinancierId = chbFinancierId == null ? null : chbFinancierId.trim();
    }

    /**
     * 取得 是否永续债
     * @return 是否永续债
     */
    public String getPerpetualFlag() {
        return perpetualFlag;
    }

    /**
     * 设置 是否永续债
     * @param perpetualFlag 是否永续债
     */
    public void setPerpetualFlag(String perpetualFlag) {
        this.perpetualFlag = perpetualFlag == null ? null : perpetualFlag.trim();
    }

    /**
     * 取得 是否含权债
     * @return 是否含权债
     */
    public String getOptionFlag() {
        return optionFlag;
    }

    /**
     * 设置 是否含权债
     * @param optionFlag 是否含权债
     */
    public void setOptionFlag(String optionFlag) {
        this.optionFlag = optionFlag == null ? null : optionFlag.trim();
    }

    /**
     * 取得 是否资产四级分类为“北金所债权”
     * @return 是否资产四级分类为“北金所债权”
     */
    public String getIsBjszq() {
        return isBjszq;
    }

    /**
     * 设置 是否资产四级分类为“北金所债权”
     * @param isBjszq 是否资产四级分类为“北金所债权”
     */
    public void setIsBjszq(String isBjszq) {
        this.isBjszq = isBjszq == null ? null : isBjszq.trim();
    }

    /**
     * 取得 是否商业银行可转债
     * @return 是否商业银行可转债
     */
    public String getBankSecKzzq() {
        return bankSecKzzq;
    }

    /**
     * 设置 是否商业银行可转债
     * @param bankSecKzzq 是否商业银行可转债
     */
    public void setBankSecKzzq(String bankSecKzzq) {
        this.bankSecKzzq = bankSecKzzq == null ? null : bankSecKzzq.trim();
    }

    /**
     * 取得 是否专项债券
     * @return 是否专项债券
     */
    public String getBankSecZxzq() {
        return bankSecZxzq;
    }

    /**
     * 设置 是否专项债券
     * @param bankSecZxzq 是否专项债券
     */
    public void setBankSecZxzq(String bankSecZxzq) {
        this.bankSecZxzq = bankSecZxzq == null ? null : bankSecZxzq.trim();
    }

    /**
     * 取得 是否政府出资产业投资基金
     * @return 是否政府出资产业投资基金
     */
    public String getIsunlisted() {
        return isunlisted;
    }

    /**
     * 设置 是否政府出资产业投资基金
     * @param isunlisted 是否政府出资产业投资基金
     */
    public void setIsunlisted(String isunlisted) {
        this.isunlisted = isunlisted == null ? null : isunlisted.trim();
    }

    /**
     * 取得 是否标准化票据
     * @return 是否标准化票据
     */
    public String getIsBzhpj() {
        return isBzhpj;
    }

    /**
     * 设置 是否标准化票据
     * @param isBzhpj 是否标准化票据
     */
    public void setIsBzhpj(String isBzhpj) {
        this.isBzhpj = isBzhpj == null ? null : isBzhpj.trim();
    }

    /**
     * 取得 永续债属性（债性/股性）
     * @return 永续债属性（债性/股性）
     */
    public String getIsPerpetual() {
        return isPerpetual;
    }

    /**
     * 设置 永续债属性（债性/股性）
     * @param isPerpetual 永续债属性（债性/股性）
     */
    public void setIsPerpetual(String isPerpetual) {
        this.isPerpetual = isPerpetual == null ? null : isPerpetual.trim();
    }

    /**
     * 取得 是否非标转标资产
     * @return 是否非标转标资产
     */
    public String getIsFbzb() {
        return isFbzb;
    }

    /**
     * 设置 是否非标转标资产
     * @param isFbzb 是否非标转标资产
     */
    public void setIsFbzb(String isFbzb) {
        this.isFbzb = isFbzb == null ? null : isFbzb.trim();
    }

    /**
     * 取得 结构化业务类型
     * @return 结构化业务类型
     */
    public String getConstracteBusiType() {
        return constracteBusiType;
    }

    /**
     * 设置 结构化业务类型
     * @param constracteBusiType 结构化业务类型
     */
    public void setConstracteBusiType(String constracteBusiType) {
        this.constracteBusiType = constracteBusiType == null ? null : constracteBusiType.trim();
    }

    /**
     * 取得 结构化层级
     * @return 结构化层级
     */
    public String getConstracteGrade() {
        return constracteGrade;
    }

    /**
     * 设置 结构化层级
     * @param constracteGrade 结构化层级
     */
    public void setConstracteGrade(String constracteGrade) {
        this.constracteGrade = constracteGrade == null ? null : constracteGrade.trim();
    }

    /**
     * 取得 结构化杠杆倍数
     * @return 结构化杠杆倍数
     */
    public BigDecimal getConstracteHeaverTimes() {
        return constracteHeaverTimes;
    }

    /**
     * 设置 结构化杠杆倍数
     * @param constracteHeaverTimes 结构化杠杆倍数
     */
    public void setConstracteHeaverTimes(BigDecimal constracteHeaverTimes) {
        this.constracteHeaverTimes = constracteHeaverTimes;
    }

    /**
     * 取得 银行资本补充工具
     * @return 银行资本补充工具
     */
    public String getBankCapital() {
        return bankCapital;
    }

    /**
     * 设置 银行资本补充工具
     * @param bankCapital 银行资本补充工具
     */
    public void setBankCapital(String bankCapital) {
        this.bankCapital = bankCapital == null ? null : bankCapital.trim();
    }

    /**
     * 取得 是否PPN等低流动性债券
     * @return 是否PPN等低流动性债券
     */
    public String getIsPpn() {
        return isPpn;
    }

    /**
     * 设置 是否PPN等低流动性债券
     * @param isPpn 是否PPN等低流动性债券
     */
    public void setIsPpn(String isPpn) {
        this.isPpn = isPpn == null ? null : isPpn.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getOrgAssetTypeEx() {
        return orgAssetTypeEx;
    }

    /**
     * 设置 null
     * @param orgAssetTypeEx null
     */
    public void setOrgAssetTypeEx(String orgAssetTypeEx) {
        this.orgAssetTypeEx = orgAssetTypeEx == null ? null : orgAssetTypeEx.trim();
    }

    /**
     * 取得 是否持有金融牌照
     * @return 是否持有金融牌照
     */
    public String getIsFinLicence() {
        return isFinLicence;
    }

    /**
     * 设置 是否持有金融牌照
     * @param isFinLicence 是否持有金融牌照
     */
    public void setIsFinLicence(String isFinLicence) {
        this.isFinLicence = isFinLicence == null ? null : isFinLicence.trim();
    }

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