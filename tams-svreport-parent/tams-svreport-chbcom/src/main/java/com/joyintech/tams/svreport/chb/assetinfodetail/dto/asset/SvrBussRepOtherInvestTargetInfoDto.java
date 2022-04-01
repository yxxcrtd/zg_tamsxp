/*
 * 文件名：BussRepOtherInvestTargetInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年11月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.tams.common.base.entity.RepOtherInvestTarget;

import java.math.BigDecimal;

/**
 * 其他资产报送信息Dto
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年11月2日
 * @see SvrBussRepOtherInvestTargetInfoDto
 * @since 1.0
 */
public class SvrBussRepOtherInvestTargetInfoDto extends RepOtherInvestTarget{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;
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
     * 资产价值
     */
    private BigDecimal assetValue;
    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 所属国家或地区 参考数据字典：COUNTRY_AREA
     */
    private String countryArea;

    /**
     * 是否有预期收益率 01是/02否
     */
    private String rateFlag;

    /**
     * 付息频率（个月/次）
     */
    private Long intpayCycle;

    /**
     * 收益率/年利率
     */
    private BigDecimal assetYield;
    /**
     * 融资人
     */
    private String financie;

    /**
     * 融资人组织机构代码
     */
    private String finOrgCode;
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
     * 资产外部评级
     */
    private String outGrade;

    /**
     * 担保方式说明
     */
    private String guaTypeRemark;

    /**
     * 抵质押物类型
     */
    private String pledgeType;

    /**
     * 抵质押物价值（元）
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal pledgeValue;
    /**
     * 担保性质
     */
    private String natureGuarantee;

    /**
     * 担保人与融资人关系
     */
    /**
     * 是否为债转股
     */
    private String deptEquity;

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
    private BigDecimal constracteHeaverTimes;

    /**
     * 公共PPP
     */
    private String pppflag;

    /**
     * 是否投向市场化债转股相关产品的部分
     */
    private String bondtostockFlag;

    private String privateprdFlag;

    public String getPrivateprdFlag() {
        return privateprdFlag;
    }

    public void setPrivateprdFlag(String privateprdFlag) {
        this.privateprdFlag = privateprdFlag;
    }

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

    public String getConstracteBusiType() {
        return constracteBusiType;
    }

    public void setConstracteBusiType(String constracteBusiType) {
        this.constracteBusiType = constracteBusiType;
    }

    public String getConstracteGrade() {
        return constracteGrade;
    }

    public void setConstracteGrade(String constracteGrade) {
        this.constracteGrade = constracteGrade;
    }

    public BigDecimal getConstracteHeaverTimes() {
        return constracteHeaverTimes;
    }

    public void setConstracteHeaverTimes(BigDecimal constracteHeaverTimes) {
        this.constracteHeaverTimes = constracteHeaverTimes;
    }

    public String getDeptEquity() {
        return deptEquity;
    }

    public void setDeptEquity(String deptEquity) {
        this.deptEquity = deptEquity;
    }

    private String relationFinan;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getInAssetType() {
        return inAssetType;
    }

    public void setInAssetType(String inAssetType) {
        this.inAssetType = inAssetType;
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

    public BigDecimal getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
    }

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea;
    }

    public String getRateFlag() {
        return rateFlag;
    }

    public void setRateFlag(String rateFlag) {
        this.rateFlag = rateFlag;
    }

    public Long getIntpayCycle() {
        return intpayCycle;
    }

    public void setIntpayCycle(Long intpayCycle) {
        this.intpayCycle = intpayCycle;
    }

    public BigDecimal getAssetYield() {
        return assetYield;
    }

    public void setAssetYield(BigDecimal assetYield) {
        this.assetYield = assetYield;
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

    public String getFinancierType2() {
        return financierType2;
    }

    public void setFinancierType2(String financierType2) {
        this.financierType2 = financierType2;
    }

    public String getFinancierType3() {
        return financierType3;
    }

    public void setFinancierType3(String financierType3) {
        this.financierType3 = financierType3;
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

    public String getIndustryLevel2Type() {
        return industryLevel2Type;
    }

    public void setIndustryLevel2Type(String industryLevel2Type) {
        this.industryLevel2Type = industryLevel2Type;
    }

    public String getProIndustry() {
        return proIndustry;
    }

    public void setProIndustry(String proIndustry) {
        this.proIndustry = proIndustry;
    }

    public String getProIndustryLevel2() {
        return proIndustryLevel2;
    }

    public void setProIndustryLevel2(String proIndustryLevel2) {
        this.proIndustryLevel2 = proIndustryLevel2;
    }

    public String getIndustryFlag() {
        return industryFlag;
    }

    public void setIndustryFlag(String industryFlag) {
        this.industryFlag = industryFlag;
    }

    public String getMonitType() {
        return monitType;
    }

    public void setMonitType(String monitType) {
        this.monitType = monitType;
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

    public String getOutGrade() {
        return outGrade;
    }

    public void setOutGrade(String outGrade) {
        this.outGrade = outGrade;
    }

    public String getGuaTypeRemark() {
        return guaTypeRemark;
    }

    public void setGuaTypeRemark(String guaTypeRemark) {
        this.guaTypeRemark = guaTypeRemark;
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

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
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
}
