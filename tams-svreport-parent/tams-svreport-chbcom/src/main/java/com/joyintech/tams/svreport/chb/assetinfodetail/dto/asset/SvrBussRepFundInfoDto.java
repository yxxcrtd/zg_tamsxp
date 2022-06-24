/*
 * 文件名：BussRepFundInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年9月18日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.annotation.Length;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RepAssetDept;
import com.joyintech.tams.common.base.entity.RepFund;
import com.joyintech.tams.constant.DictCodeList;

/**
 * 基金资产中债报送信息组件Dto
 * 〈功能详细描述〉
 * @author wangzehui
 * @version 1.0 2018年9月18日
 * @see SvrBussRepFundInfoDto
 * @since 1.0
 */
public class SvrBussRepFundInfoDto extends RepFund {
    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 资产代码
     */
    private String assetId;

    /**
     * 资产名称
     */
    private String assetName;
    /**
     * 是否由金融资产投资公司发行
     */
    private String financialComissue;
    /**
     * 行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    private String industryShares;

    /**
     * 行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String industryShares2;
    /**
     * 登记备案机构 01 中央国债登记结算有限责任公司/ 02 上海清算所 / 03 中国证券登记结算有限责任公司 / 04 中国证券投资基金业协会 /99 其他
     */
    private String registOrg1;


    /**
     * 管理人
     */
    private String managerId;

    /**
     * 托管人
     */
    private String trusteeId;


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
     * 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    private String privatefundFlag;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 管理人代码
     */
    private String chbManagerId;
    
    /**
     * 是否政府出资产业投资基金
     */
    private String isUnlisted;    

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
     * 是否为固定收益类，01：是，02：否， 中债新增
     */
    private String profitFlag;
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

    public String getFinancialComissue() {
        return financialComissue;
    }

    public void setFinancialComissue(String financialComissue) {
        this.financialComissue = financialComissue;
    }

    public String getIndustryShares() {
        return industryShares;
    }

    public void setIndustryShares(String industryShares) {
        this.industryShares = industryShares;
    }

    public String getIndustryShares2() {
        return industryShares2;
    }

    public void setIndustryShares2(String industryShares2) {
        this.industryShares2 = industryShares2;
    }

    public String getRegistOrg1() {
        return registOrg1;
    }

    public void setRegistOrg1(String registOrg1) {
        this.registOrg1 = registOrg1;
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

    public String getIsGovernFund() {
        return isGovernFund;
    }

    public void setIsGovernFund(String isGovernFund) {
        this.isGovernFund = isGovernFund;
    }

    public String getGovernInvestFund() {
        return governInvestFund;
    }

    public void setGovernInvestFund(String governInvestFund) {
        this.governInvestFund = governInvestFund;
    }

    public String getFundEnpeName() {
        return fundEnpeName;
    }

    public void setFundEnpeName(String fundEnpeName) {
        this.fundEnpeName = fundEnpeName;
    }

    public String getInvestStage() {
        return investStage;
    }

    public void setInvestStage(String investStage) {
        this.investStage = investStage;
    }

    public String getSharesType1() {
        return sharesType1;
    }

    public void setSharesType1(String sharesType1) {
        this.sharesType1 = sharesType1;
    }

    public String getSharesType2() {
        return sharesType2;
    }

    public void setSharesType2(String sharesType2) {
        this.sharesType2 = sharesType2;
    }

    public String getSharesType3() {
        return sharesType3;
    }

    public void setSharesType3(String sharesType3) {
        this.sharesType3 = sharesType3;
    }

    public String getPrivatefundFlag() {
        return privatefundFlag;
    }

    public void setPrivatefundFlag(String privatefundFlag) {
        this.privatefundFlag = privatefundFlag;
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

	public String getIsUnlisted() {
		return isUnlisted;
	}

	public void setIsUnlisted(String isUnlisted) {
		this.isUnlisted = isUnlisted;
	}
	
	public String getProfitFlag() {
        return profitFlag;
    }

    public void setProfitFlag(String profitFlag) {
        this.profitFlag = profitFlag;
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
}
