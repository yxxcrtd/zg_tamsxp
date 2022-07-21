/*
 * 文件名：BussRepAssetAstBillDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年11月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.assetinfodetail.dto.asset;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import com.joyintech.framework.validation.annotation.Amount;
import com.joyintech.framework.validation.annotation.Length;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.RepAssetAstBill;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 非标资产(票据或信用)报送信息
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年11月2日
 * @see SvrBussRepAssetAstBillInfoDto
 * @since 1.0
 */
public class SvrBussRepAssetAstBillInfoDto extends RepAssetAstBill{

    /**
     * 金融产品代码
     */
    private String repFinprodId;



    /**
     * 类型
     */
    private String repAstBillType;

    /**
     * 数量
     */
    private Integer repNum;

    /**
     * 合计金额
     */
    @JsonSerialize(using= BigDecimalSerializer.class)
    private BigDecimal repAmtSum;




    /**
     * 行业
     */
    private String repIndustry;

    /**
     * 行业二级分类
     */
    private String repIndustryLevel2;

    /**
     * 贴现利率
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal repDiscRate;

    /**
     * 备注
     */
    private String repRemark;

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
     * 取得 类型
     * @return 类型
     */
    public String getRepAstBillType() {
        return repAstBillType;
    }

    /**
     * 设置 类型
     * @param repAstBillType 类型
     */
    public void setRepAstBillType(String repAstBillType) {
        this.repAstBillType = repAstBillType == null ? null : repAstBillType.trim();
    }

    /**
     * 取得 数量
     * @return 数量
     */
    public Integer getRepNum() {
        return repNum;
    }

    /**
     * 设置 数量
     * @param repNum 数量
     */
    public void setRepNum(Integer repNum) {
        this.repNum = repNum;
    }

    /**
     * 取得 合计金额
     * @return 合计金额
     */
    public BigDecimal getRepAmtSum() {
        return repAmtSum;
    }

    /**
     * 设置 合计金额
     * @param repAmtSum 合计金额
     */
    public void setRepAmtSum(BigDecimal repAmtSum) {
        this.repAmtSum = repAmtSum;
    }


    /**
     * 取得 行业
     * @return 行业
     */
    public String getRepIndustry() {
        return repIndustry;
    }

    /**
     * 设置 行业
     * @param repIndustry 行业
     */
    public void setRepIndustry(String repIndustry) {
        this.repIndustry = repIndustry == null ? null : repIndustry.trim();
    }

    /**
     * 取得 行业二级分类
     * @return 行业二级分类
     */
    public String getRepIndustryLevel2() {
        return repIndustryLevel2;
    }

    /**
     * 设置 行业二级分类
     * @param repIndustryLevel2 行业二级分类
     */
    public void setRepIndustryLevel2(String repIndustryLevel2) {
        this.repIndustryLevel2 = repIndustryLevel2 == null ? null : repIndustryLevel2.trim();
    }

    /**
     * 取得 贴现利率
     * @return 贴现利率
     */
    public BigDecimal getRepDiscRate() {
        return repDiscRate;
    }

    /**
     * 设置 贴现利率
     * @param repDiscRate 贴现利率
     */
    public void setRepDiscRate(BigDecimal repDiscRate) {
        this.repDiscRate = repDiscRate;
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
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 是否为收/受益权 01是/02否
     */
    private String isRight;

    /**
     * 是否属于买入返售 01是/02否
     */
    private String isResale;

    /**
     * 非标准化债券类资产类型 01 商业承兑汇票/ 02 银行承兑汇票/ 03 其他票据/ 04 混合类票据/ 05 信用证
     */
    private String notstandSecType;

    /**
     * 非标准化债券类资产数量
     */
    private Long notstandSecNum;

    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 加权剩余期限(天)
     */
    private BigDecimal remainTerm;

    /**
     * 最长剩余期限(天)
     */
    private BigDecimal maxRemainTerm;

    /**
     * 最短剩余期限(天)
     */
    private BigDecimal minRemainTerm;

    /**
     * 收益率/年利率
     */
    private BigDecimal assetYield;

    /**
     * 行业一级分类 枚举值数据字典：INSTITUTION_INDUSTRY_FIRST_LEVEL
     */
    private String industryShares;

    /**
     * 行业二级分类 枚举值数据字典：INSTITUTION_INDUSTRY_SECOND_LEVEL
     */
    private String industryShares2;

    /**
     * 是否投向地方政府融资平台
     */
    private String privateFlag;
    /**
     * 备注
     */
    private String remark;

    /**
     * 是否标准化票据
     */
    private String isBzhpj;

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
     * 公共PPP
     */
    private String pppflag;

    /**
     * 是否投向“市场化债转股相关产品的部分”
     */
    private String bondtostockFlag;

    /**
     * 票据代码
     */
    private String billCode;

    /**
     * 融资人
     */
    private String financie;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public String getIsResale() {
        return isResale;
    }

    public void setIsResale(String isResale) {
        this.isResale = isResale;
    }

    public String getNotstandSecType() {
        return notstandSecType;
    }

    public void setNotstandSecType(String notstandSecType) {
        this.notstandSecType = notstandSecType;
    }

    public Long getNotstandSecNum() {
        return notstandSecNum;
    }

    public void setNotstandSecNum(Long notstandSecNum) {
        this.notstandSecNum = notstandSecNum;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getRemainTerm() {
        return remainTerm;
    }

    public void setRemainTerm(BigDecimal remainTerm) {
        this.remainTerm = remainTerm;
    }

    public BigDecimal getMaxRemainTerm() {
        return maxRemainTerm;
    }

    public void setMaxRemainTerm(BigDecimal maxRemainTerm) {
        this.maxRemainTerm = maxRemainTerm;
    }

    public BigDecimal getMinRemainTerm() {
        return minRemainTerm;
    }

    public void setMinRemainTerm(BigDecimal minRemainTerm) {
        this.minRemainTerm = minRemainTerm;
    }

    public BigDecimal getAssetYield() {
        return assetYield;
    }

    public void setAssetYield(BigDecimal assetYield) {
        this.assetYield = assetYield;
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

    public String getPrivateFlag() {
        return privateFlag;
    }

    public void setPrivateFlag(String privateFlag) {
        this.privateFlag = privateFlag;
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

    public String getFinancie() {
        return financie;
    }

    public void setFinancie(String financie) {
        this.financie = financie;
    }
}
