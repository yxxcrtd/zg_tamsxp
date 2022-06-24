package com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;

import java.math.BigDecimal;
import java.util.Date;

public class RepAssetCreditNonstdDto extends BaseEntity {
    /**
     * 金融产品代码
     */
    private String repFinprodId;


    /**
     * 收/受益权类型 参照数据字典
     */
    private String repProfitType;

    /**
     * 是否属于买入返售
     */
    private String repIsResale;

    /**
     * 起息日
     */
    private Date repVdate;


    private String  assetId;

    /**
     * 到期日
     */
    private Date repMdate;

    /**
     * 项目期限
     */
    private Integer repTerm;

    /**
     * 是否有预期收益率
     */
    private String repRateFlag;

    /**
     * 项目收益率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal repProjYield;

    /**
     * 付息频率(个月/次)
     */
    private Long repIntpayCycle;

    /**
     * 还本付息情况说明
     */
    private String repRepayCmt;
    /**
     * 融资人
     */
    private String repFinancie;

    /**
     * 融资人内部信用评级
     */
    private String repFcrGradeIn;

    /**
     * 外部评级机构及对融资人评级
     */
    private String repFcrGradeOut;

    /**
     * 融资人类型（按规模划分）
     */
    private String repFinancierType1;

    /**
     * 融资人类型（按经济类型划分）
     */
    private String repFinancierType2;

    /**
     * 融资人类型（按技术领域划分）
     */
    private String repFinancierType3;

    /**
     * 融资项目
     */
    private String repFinanceProj;

    /**
     * 融资人所属行业
     */
    private String repIndustry1;

    /**
     * 融资人所属行业二级分类
     */
    private String repIndustryLevel2;

    /**
     * 融资项目所属行业
     */
    private String repProIndustry;

    /**
     * 融资项目所属行业二级分类
     */
    private String repProIndustryLevel2;

    /**
     * 是否属于重点监控行业和领域
     */
    private String repIndustryFlag;

    /**
     * 重点监控行业和领域类别
     */
    private String repMonitType;

    /**
     * 重点监控行业和领域类别说明
     */
    private String repMonTypeComm;

    /**
     * 担保方式
     */
    private String repGuaType;

    /**
     * 担保方式说明
     */
    private String repGuaTypeRemark;

    /**
     * 融资人主体评级
     */
    private String repFinancierGrade;

    /**
     * 资产内部评级
     */
    private String repInGrade;

    /**
     * 资产外部评级
     */
    private String repOutGrade;

    /**
     * 选择权
     */
    private String repChose;

    /**
     * 行权条件说明：默认“无”
     */
    private String repConditionCmm;

    /**
     * 融资人所属地区
     */
    private String repFinArea;


    /**
     * 融资人组织机构代码
     */
    private String repFinOrgCode;

    /**
     * 备注
     */
    private String repRemark;
    
    /**
     * 法定到期日 中债新增 20200403 start
     */
     private Date repFmdate;

     /**
     * 份额面值
     */
     private BigDecimal repPrinBaseamt;

     /**
     * 计息类型
     */
     private String repCouponSpecies;

     /**
     * 利息分布方式
     */
     private String repIntpayRule;

     /**
     * 结构档次
     */
     private String repStructGrade;

     /**
     * 还本方式
     */
     private String repRepaymentType;

     /**
     * 分期还本条款标识
     */
     private String repPeriodRepaymentFlag;

     /**
     * 超额收益分配比例%
     */
     private BigDecimal repExcessProfitRate;

     /**
     * 含权类型
     */
     private String repContainType;

     /**
     * 行权方式
     */
     private String repExerciseType;

//     /**
//     * 固定行权日
//     */
//     private Date repExerciseDate;

//     /**
//     * 首次行权日期
//     */
//     private Date repFirstExerciseDate;

//     /**
//     * 行权周期
//     */
//     private BigDecimal repExercisePeriod;

     /**
     * 行权价格
     */
     private BigDecimal repExercisePrice;

     /**
     * 永续条款类型
     */
     private String repPerpetualClauseType;

     /**
     * 利息递延条款类型
     */
     private String repDeferredInterestType;

     /**
     * 递延利息是否计息
     */
     private String repDeferredInterestInt;

     /**
     * 首次重定价日期
     */
     private Date repFirstReprceDate;

     /**
     * 重定价周期
     */
     private Integer repReprceCycle;

     /**
     * 部分赎回标识
     */
     private String repPartRedemFlag;

     /**
     * 部分赎回比例%
     */
     private BigDecimal repPartRedemRate;

     /**
     * 抵质押物类型
     */
     private String repPledgeType;

     /**
     * 抵质押物价值（元）
     */
     private BigDecimal repPledgeValue;

     /**
     * 担保性质 
     */
     private String repNatureGuarantee;

     /**
      * 基础资产类型 REP_BASIC_ASSET_TYPE
      */
      private String repBasicAssetType;
     
     /**
     * 担保人与融资人关系  中债新增 20200403 end
     */
     private String repRelationFinan;

    /**
     * 资产代码  中债新增  20211224
     */
    private String repCreditAssetCode;

    /**
     * 取得 金融产品代码
     * @return 金融产品代码
     */
    public String getRepFinprodId() {
        return repFinprodId;
    }

    /**
     * 设置 金融产品代码
     * @param repFinprodId 金融产品代码
     */
    public void setRepFinprodId(String repFinprodId) {
        this.repFinprodId = repFinprodId == null ? null : repFinprodId.trim();
    }


    /**
     * 取得 收/受益权类型 参照数据字典
     * @return 收/受益权类型 参照数据字典
     */
    public String getRepProfitType() {
        return repProfitType;
    }

    /**
     * 设置 收/受益权类型 参照数据字典
     * @param repProfitType 收/受益权类型 参照数据字典
     */
    public void setRepProfitType(String repProfitType) {
        this.repProfitType = repProfitType == null ? null : repProfitType.trim();
    }

    /**
     * 取得 是否属于买入返售
     * @return 是否属于买入返售
     */
    public String getRepIsResale() {
        return repIsResale;
    }

    /**
     * 设置 是否属于买入返售
     * @param repIsResale 是否属于买入返售
     */
    public void setRepIsResale(String repIsResale) {
        this.repIsResale = repIsResale == null ? null : repIsResale.trim();
    }

    /**
     * 取得 起息日
     * @return 起息日
     */
    public Date getRepVdate() {
        return repVdate;
    }

    /**
     * 设置 起息日
     * @param repVdate 起息日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setRepVdate(Date repVdate) {
        this.repVdate = repVdate;
    }

    /**
     * 取得 到期日
     * @return 到期日
     */
    public Date getRepMdate() {
        return repMdate;
    }

    /**
     * 设置 到期日
     * @param repMdate 到期日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setRepMdate(Date repMdate) {
        this.repMdate = repMdate;
    }

    /**
     * 取得 项目期限
     * @return 项目期限
     */
    public Integer getRepTerm() {
        return repTerm;
    }

    /**
     * 设置 项目期限
     * @param repTerm 项目期限
     */
    public void setRepTerm(Integer repTerm) {
        this.repTerm = repTerm;
    }

    /**
     * 取得 是否有预期收益率
     * @return 是否有预期收益率
     */
    public String getRepRateFlag() {
        return repRateFlag;
    }

    /**
     * 设置 是否有预期收益率
     * @param repRateFlag 是否有预期收益率
     */
    public void setRepRateFlag(String repRateFlag) {
        this.repRateFlag = repRateFlag == null ? null : repRateFlag.trim();
    }

    /**
     * 取得 项目收益率
     * @return 项目收益率
     */
    public BigDecimal getRepProjYield() {
        return repProjYield;
    }

    /**
     * 设置 项目收益率
     * @param repProjYield 项目收益率
     */
    public void setRepProjYield(BigDecimal repProjYield) {
        this.repProjYield = repProjYield;
    }

    /**
     * 取得 付息频率(个月/次)
     * @return 付息频率(个月/次)
     */
    public Long getRepIntpayCycle() {
        return repIntpayCycle;
    }

    /**
     * 设置 付息频率(个月/次)
     * @param repIntpayCycle 付息频率(个月/次)
     */
    public void setRepIntpayCycle(Long repIntpayCycle) {
        this.repIntpayCycle = repIntpayCycle;
    }

    /**
     * 取得 还本付息情况说明
     * @return 还本付息情况说明
     */
    public String getRepRepayCmt() {
        return repRepayCmt;
    }

    /**
     * 设置 还本付息情况说明
     * @param repRepayCmt 还本付息情况说明
     */
    public void setRepRepayCmt(String repRepayCmt) {
        this.repRepayCmt = repRepayCmt == null ? null : repRepayCmt.trim();
    }

    public String getRepFinancie() {
        return repFinancie;
    }

    public void setRepFinancie(String repFinancie) {
        this.repFinancie = repFinancie;
    }

    /**
     * 取得 融资人内部信用评级
     * @return 融资人内部信用评级
     */
    public String getRepFcrGradeIn() {
        return repFcrGradeIn;
    }

    /**
     * 设置 融资人内部信用评级
     * @param repFcrGradeIn 融资人内部信用评级
     */
    public void setRepFcrGradeIn(String repFcrGradeIn) {
        this.repFcrGradeIn = repFcrGradeIn == null ? null : repFcrGradeIn.trim();
    }

    /**
     * 取得 外部评级机构及对融资人评级
     * @return 外部评级机构及对融资人评级
     */
    public String getRepFcrGradeOut() {
        return repFcrGradeOut;
    }

    /**
     * 设置 外部评级机构及对融资人评级
     * @param repFcrGradeOut 外部评级机构及对融资人评级
     */
    public void setRepFcrGradeOut(String repFcrGradeOut) {
        this.repFcrGradeOut = repFcrGradeOut == null ? null : repFcrGradeOut.trim();
    }

    /**
     * 取得 融资人类型（按规模划分）
     * @return 融资人类型（按规模划分）
     */
    public String getRepFinancierType1() {
        return repFinancierType1;
    }

    /**
     * 设置 融资人类型（按规模划分）
     * @param repFinancierType1 融资人类型（按规模划分）
     */
    public void setRepFinancierType1(String repFinancierType1) {
        this.repFinancierType1 = repFinancierType1 == null ? null : repFinancierType1.trim();
    }

    /**
     * 取得 融资人类型（按经济类型划分）
     * @return 融资人类型（按经济类型划分）
     */
    public String getRepFinancierType2() {
        return repFinancierType2;
    }

    /**
     * 设置 融资人类型（按经济类型划分）
     * @param repFinancierType2 融资人类型（按经济类型划分）
     */
    public void setRepFinancierType2(String repFinancierType2) {
        this.repFinancierType2 = repFinancierType2 == null ? null : repFinancierType2.trim();
    }

    /**
     * 取得 融资人类型（按技术领域划分）
     * @return 融资人类型（按技术领域划分）
     */
    public String getRepFinancierType3() {
        return repFinancierType3;
    }

    /**
     * 设置 融资人类型（按技术领域划分）
     * @param repFinancierType3 融资人类型（按技术领域划分）
     */
    public void setRepFinancierType3(String repFinancierType3) {
        this.repFinancierType3 = repFinancierType3 == null ? null : repFinancierType3.trim();
    }

    /**
     * 取得 融资项目
     * @return 融资项目
     */
    public String getRepFinanceProj() {
        return repFinanceProj;
    }

    /**
     * 设置 融资项目
     * @param repFinanceProj 融资项目
     */
    public void setRepFinanceProj(String repFinanceProj) {
        this.repFinanceProj = repFinanceProj == null ? null : repFinanceProj.trim();
    }

    /**
     * 取得 融资人所属行业
     * @return 融资人所属行业
     */
    public String getRepIndustry1() {
        return repIndustry1;
    }

    /**
     * 设置 融资人所属行业
     * @param repIndustry1 融资人所属行业
     */
    public void setRepIndustry1(String repIndustry1) {
        this.repIndustry1 = repIndustry1 == null ? null : repIndustry1.trim();
    }

    /**
     * 取得 融资人所属行业二级分类
     * @return 融资人所属行业二级分类
     */
    public String getRepIndustryLevel2() {
        return repIndustryLevel2;
    }

    /**
     * 设置 融资人所属行业二级分类
     * @param repIndustryLevel2 融资人所属行业二级分类
     */
    public void setRepIndustryLevel2(String repIndustryLevel2) {
        this.repIndustryLevel2 = repIndustryLevel2 == null ? null : repIndustryLevel2.trim();
    }

    /**
     * 取得 融资项目所属行业
     * @return 融资项目所属行业
     */
    public String getRepProIndustry() {
        return repProIndustry;
    }

    /**
     * 设置 融资项目所属行业
     * @param repProIndustry 融资项目所属行业
     */
    public void setRepProIndustry(String repProIndustry) {
        this.repProIndustry = repProIndustry == null ? null : repProIndustry.trim();
    }

    /**
     * 取得 融资项目所属行业二级分类
     * @return 融资项目所属行业二级分类
     */
    public String getRepProIndustryLevel2() {
        return repProIndustryLevel2;
    }

    /**
     * 设置 融资项目所属行业二级分类
     * @param repProIndustryLevel2 融资项目所属行业二级分类
     */
    public void setRepProIndustryLevel2(String repProIndustryLevel2) {
        this.repProIndustryLevel2 = repProIndustryLevel2 == null ? null : repProIndustryLevel2.trim();
    }

    /**
     * 取得 是否属于重点监控行业和领域
     * @return 是否属于重点监控行业和领域
     */
    public String getRepIndustryFlag() {
        return repIndustryFlag;
    }

    /**
     * 设置 是否属于重点监控行业和领域
     * @param repIndustryFlag 是否属于重点监控行业和领域
     */
    public void setRepIndustryFlag(String repIndustryFlag) {
        this.repIndustryFlag = repIndustryFlag == null ? null : repIndustryFlag.trim();
    }

    /**
     * 取得 重点监控行业和领域类别
     * @return 重点监控行业和领域类别
     */
    public String getRepMonitType() {
        return repMonitType;
    }

    /**
     * 设置 重点监控行业和领域类别
     * @param repMonitType 重点监控行业和领域类别
     */
    public void setRepMonitType(String repMonitType) {
        this.repMonitType = repMonitType == null ? null : repMonitType.trim();
    }

    /**
     * 取得 重点监控行业和领域类别说明
     * @return 重点监控行业和领域类别说明
     */
    public String getRepMonTypeComm() {
        return repMonTypeComm;
    }

    /**
     * 设置 重点监控行业和领域类别说明
     * @param repMonTypeComm 重点监控行业和领域类别说明
     */
    public void setRepMonTypeComm(String repMonTypeComm) {
        this.repMonTypeComm = repMonTypeComm == null ? null : repMonTypeComm.trim();
    }

    /**
     * 取得 担保方式
     * @return 担保方式
     */
    public String getRepGuaType() {
        return repGuaType;
    }

    /**
     * 设置 担保方式
     * @param repGuaType 担保方式
     */
    public void setRepGuaType(String repGuaType) {
        this.repGuaType = repGuaType == null ? null : repGuaType.trim();
    }

    /**
     * 取得 担保方式说明
     * @return 担保方式说明
     */
    public String getRepGuaTypeRemark() {
        return repGuaTypeRemark;
    }

    /**
     * 设置 担保方式说明
     * @param repGuaTypeRemark 担保方式说明
     */
    public void setRepGuaTypeRemark(String repGuaTypeRemark) {
        this.repGuaTypeRemark = repGuaTypeRemark == null ? null : repGuaTypeRemark.trim();
    }

    /**
     * 取得 融资人主体评级
     * @return 融资人主体评级
     */
    public String getRepFinancierGrade() {
        return repFinancierGrade;
    }

    /**
     * 设置 融资人主体评级
     * @param repFinancierGrade 融资人主体评级
     */
    public void setRepFinancierGrade(String repFinancierGrade) {
        this.repFinancierGrade = repFinancierGrade == null ? null : repFinancierGrade.trim();
    }

    /**
     * 取得 资产内部评级
     * @return 资产内部评级
     */
    public String getRepInGrade() {
        return repInGrade;
    }

    /**
     * 设置 资产内部评级
     * @param repInGrade 资产内部评级
     */
    public void setRepInGrade(String repInGrade) {
        this.repInGrade = repInGrade == null ? null : repInGrade.trim();
    }

    /**
     * 取得 资产外部评级
     * @return 资产外部评级
     */
    public String getRepOutGrade() {
        return repOutGrade;
    }

    /**
     * 设置 资产外部评级
     * @param repOutGrade 资产外部评级
     */
    public void setRepOutGrade(String repOutGrade) {
        this.repOutGrade = repOutGrade == null ? null : repOutGrade.trim();
    }

    /**
     * 取得 选择权
     * @return 选择权
     */
    public String getRepChose() {
        return repChose;
    }

    /**
     * 设置 选择权
     * @param repChose 选择权
     */
    public void setRepChose(String repChose) {
        this.repChose = repChose == null ? null : repChose.trim();
    }

    /**
     * 取得 行权条件说明：默认“无”
     * @return 行权条件说明：默认“无”
     */
    public String getRepConditionCmm() {
        return repConditionCmm;
    }

    /**
     * 设置 行权条件说明：默认“无”
     * @param repConditionCmm 行权条件说明：默认“无”
     */
    public void setRepConditionCmm(String repConditionCmm) {
        this.repConditionCmm = repConditionCmm == null ? null : repConditionCmm.trim();
    }

    /**
     * 取得 融资人所属地区
     * @return 融资人所属地区
     */
    public String getRepFinArea() {
        return repFinArea;
    }

    /**
     * 设置 融资人所属地区
     * @param repFinArea 融资人所属地区
     */
    public void setRepFinArea(String repFinArea) {
        this.repFinArea = repFinArea == null ? null : repFinArea.trim();
    }


    /**
     * 取得 融资人组织机构代码
     * @return 融资人组织机构代码
     */
    public String getRepFinOrgCode() {
        return repFinOrgCode;
    }

    /**
     * 设置 融资人组织机构代码
     * @param repFinOrgCode 融资人组织机构代码
     */
    public void setRepFinOrgCode(String repFinOrgCode) {
        this.repFinOrgCode = repFinOrgCode == null ? null : repFinOrgCode.trim();
    }

    /**
     * 取得 备注
     * @return 备注
     */
    public String getRepRemark() {
        return repRemark;
    }

    /**
     * 设置 备注
     * @param repRemark 备注
     */
    public void setRepRemark(String repRemark) {
        this.repRemark = repRemark == null ? null : repRemark.trim();
    }

	/**
	 * @return the repFmdate
	 */
	public Date getRepFmdate() {
		return repFmdate;
	}

	/**
	 * @param repFmdate the repFmdate to set
	 */
	public void setRepFmdate(Date repFmdate) {
		this.repFmdate = repFmdate;
	}

	/**
	 * @return the repPrinBaseamt
	 */
	public BigDecimal getRepPrinBaseamt() {
		return repPrinBaseamt;
	}

	/**
	 * @param repPrinBaseamt the repPrinBaseamt to set
	 */
	public void setRepPrinBaseamt(BigDecimal repPrinBaseamt) {
		this.repPrinBaseamt = repPrinBaseamt;
	}

	/**
	 * @return the repCouponSpecies
	 */
	public String getRepCouponSpecies() {
		return repCouponSpecies;
	}

	/**
	 * @param repCouponSpecies the repCouponSpecies to set
	 */
	public void setRepCouponSpecies(String repCouponSpecies) {
		this.repCouponSpecies = repCouponSpecies;
	}

	/**
	 * @return the repIntpayRule
	 */
	public String getRepIntpayRule() {
		return repIntpayRule;
	}

	/**
	 * @param repIntpayRule the repIntpayRule to set
	 */
	public void setRepIntpayRule(String repIntpayRule) {
		this.repIntpayRule = repIntpayRule;
	}

	/**
	 * @return the repStructGrade
	 */
	public String getRepStructGrade() {
		return repStructGrade;
	}

	/**
	 * @param repStructGrade the repStructGrade to set
	 */
	public void setRepStructGrade(String repStructGrade) {
		this.repStructGrade = repStructGrade;
	}

	/**
	 * @return the repRepaymentType
	 */
	public String getRepRepaymentType() {
		return repRepaymentType;
	}

	/**
	 * @param repRepaymentType the repRepaymentType to set
	 */
	public void setRepRepaymentType(String repRepaymentType) {
		this.repRepaymentType = repRepaymentType;
	}

	/**
	 * @return the repPeriodRepaymentFlag
	 */
	public String getRepPeriodRepaymentFlag() {
		return repPeriodRepaymentFlag;
	}

	/**
	 * @param repPeriodRepaymentFlag the repPeriodRepaymentFlag to set
	 */
	public void setRepPeriodRepaymentFlag(String repPeriodRepaymentFlag) {
		this.repPeriodRepaymentFlag = repPeriodRepaymentFlag;
	}

	/**
	 * @return the repExcessProfitRate
	 */
	public BigDecimal getRepExcessProfitRate() {
		return repExcessProfitRate;
	}

	/**
	 * @param repExcessProfitRate the repExcessProfitRate to set
	 */
	public void setRepExcessProfitRate(BigDecimal repExcessProfitRate) {
		this.repExcessProfitRate = repExcessProfitRate;
	}

	/**
	 * @return the repContainType
	 */
	public String getRepContainType() {
		return repContainType;
	}

	/**
	 * @param repContainType the repContainType to set
	 */
	public void setRepContainType(String repContainType) {
		this.repContainType = repContainType;
	}

	/**
	 * @return the repExerciseType
	 */
	public String getRepExerciseType() {
		return repExerciseType;
	}

	/**
	 * @param repExerciseType the repExerciseType to set
	 */
	public void setRepExerciseType(String repExerciseType) {
		this.repExerciseType = repExerciseType;
	}

//	/**
//	 * @return the repExerciseDate
//	 */
//	public Date getRepExerciseDate() {
//		return repExerciseDate;
//	}
//
//	/**
//	 * @param repExerciseDate the repExerciseDate to set
//	 */
//	public void setRepExerciseDate(Date repExerciseDate) {
//		this.repExerciseDate = repExerciseDate;
//	}
//
//	/**
//	 * @return the repFirstExerciseDate
//	 */
//	public Date getRepFirstExerciseDate() {
//		return repFirstExerciseDate;
//	}
//
//	/**
//	 * @param repFirstExerciseDate the repFirstExerciseDate to set
//	 */
//	public void setRepFirstExerciseDate(Date repFirstExerciseDate) {
//		this.repFirstExerciseDate = repFirstExerciseDate;
//	}

//	/**
//	 * @return the repExercisePeriod
//	 */
//	public BigDecimal getRepExercisePeriod() {
//		return repExercisePeriod;
//	}
//
//	/**
//	 * @param repExercisePeriod the repExercisePeriod to set
//	 */
//	public void setRepExercisePeriod(BigDecimal repExercisePeriod) {
//		this.repExercisePeriod = repExercisePeriod;
//	}

	/**
	 * @return the repExercisePrice
	 */
	public BigDecimal getRepExercisePrice() {
		return repExercisePrice;
	}

	/**
	 * @param repExercisePrice the repExercisePrice to set
	 */
	public void setRepExercisePrice(BigDecimal repExercisePrice) {
		this.repExercisePrice = repExercisePrice;
	}

	/**
	 * @return the repPerpetualClauseType
	 */
	public String getRepPerpetualClauseType() {
		return repPerpetualClauseType;
	}

	/**
	 * @param repPerpetualClauseType the repPerpetualClauseType to set
	 */
	public void setRepPerpetualClauseType(String repPerpetualClauseType) {
		this.repPerpetualClauseType = repPerpetualClauseType;
	}

	/**
	 * @return the repDeferredInterestType
	 */
	public String getRepDeferredInterestType() {
		return repDeferredInterestType;
	}

	/**
	 * @param repDeferredInterestType the repDeferredInterestType to set
	 */
	public void setRepDeferredInterestType(String repDeferredInterestType) {
		this.repDeferredInterestType = repDeferredInterestType;
	}

	/**
	 * @return the repDeferredInterestInt
	 */
	public String getRepDeferredInterestInt() {
		return repDeferredInterestInt;
	}

	/**
	 * @param repDeferredInterestInt the repDeferredInterestInt to set
	 */
	public void setRepDeferredInterestInt(String repDeferredInterestInt) {
		this.repDeferredInterestInt = repDeferredInterestInt;
	}

	/**
	 * @return the repFirstReprceDate
	 */
	public Date getRepFirstReprceDate() {
		return repFirstReprceDate;
	}

	/**
	 * @param repFirstReprceDate the repFirstReprceDate to set
	 */
	public void setRepFirstReprceDate(Date repFirstReprceDate) {
		this.repFirstReprceDate = repFirstReprceDate;
	}

	/**
	 * @return the repReprceCycle
	 */
	public Integer getRepReprceCycle() {
		return repReprceCycle;
	}

	/**
	 * @param repReprceCycle the repReprceCycle to set
	 */
	public void setRepReprceCycle(Integer repReprceCycle) {
		this.repReprceCycle = repReprceCycle;
	}

	/**
	 * @return the repPartRedemFlag
	 */
	public String getRepPartRedemFlag() {
		return repPartRedemFlag;
	}

	/**
	 * @param repPartRedemFlag the repPartRedemFlag to set
	 */
	public void setRepPartRedemFlag(String repPartRedemFlag) {
		this.repPartRedemFlag = repPartRedemFlag;
	}

	/**
	 * @return the repPartRedemRate
	 */
	public BigDecimal getRepPartRedemRate() {
		return repPartRedemRate;
	}

	/**
	 * @param repPartRedemRate the repPartRedemRate to set
	 */
	public void setRepPartRedemRate(BigDecimal repPartRedemRate) {
		this.repPartRedemRate = repPartRedemRate;
	}

	/**
	 * @return the repPledgeType
	 */
	public String getRepPledgeType() {
		return repPledgeType;
	}

	/**
	 * @param repPledgeType the repPledgeType to set
	 */
	public void setRepPledgeType(String repPledgeType) {
		this.repPledgeType = repPledgeType;
	}

	/**
	 * @return the repPledgeValue
	 */
	public BigDecimal getRepPledgeValue() {
		return repPledgeValue;
	}

	/**
	 * @param repPledgeValue the repPledgeValue to set
	 */
	public void setRepPledgeValue(BigDecimal repPledgeValue) {
		this.repPledgeValue = repPledgeValue;
	}

	/**
	 * @return the repNatureGuarantee
	 */
	public String getRepNatureGuarantee() {
		return repNatureGuarantee;
	}

	/**
	 * @param repNatureGuarantee the repNatureGuarantee to set
	 */
	public void setRepNatureGuarantee(String repNatureGuarantee) {
		this.repNatureGuarantee = repNatureGuarantee;
	}

	/**
	 * @return the repRelationFinan
	 */
	public String getRepRelationFinan() {
		return repRelationFinan;
	}

	/**
	 * @param repRelationFinan the repRelationFinan to set
	 */
	public void setRepRelationFinan(String repRelationFinan) {
		this.repRelationFinan = repRelationFinan;
	}

	/**
	 * @return the repBasicAssetType
	 */
	public String getRepBasicAssetType() {
		return repBasicAssetType;
	}

	/**
	 * @param repBasicAssetType the repBasicAssetType to set
	 */
	public void setRepBasicAssetType(String repBasicAssetType) {
		this.repBasicAssetType = repBasicAssetType;
	}

    public String getRepCreditAssetCode() {
        return repCreditAssetCode;
    }

    public void setRepCreditAssetCode(String repCreditAssetCode) {
        this.repCreditAssetCode = repCreditAssetCode;
    }


    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
}