package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.math.BigDecimal;
import java.util.Date;

public class ChbMonthAssetPositiondetail extends BaseEntity {
    /**
     * 头寸编号
     */
    private String posuuid;

    /**
     * 统计日期
     */
    private String term;

    /**
     * 月报分类
     */
    private String detailno;

    /**
     * 投资品种
     */
    private String investVariety;

    /**
     * 资产ID
     */
    private String assetCode;

    /**
     * 资产一级分类
     */
    private String assettype;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 是否合规新产品
     */
    private String isFhxgcp;

    /**
     * 穿透前资产价值
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal assetvalueA;

    /**
     * 穿透后资产价值
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal assetvalueB;

    /**
     * 资产起息日
     */
    private Date vdate;

    /**
     * 资产到期日
     */
    private Date mdate;

    /**
     * null
     */
    private Date openplan;

    /**
     * null
     */
    private String subjectName;

    /**
     * null
     */
    private String rivalParty;

    /**
     * null
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal rate;

    /**
     * null
     */
    private String ccy;

    /**
     * 所属行业
     */
    private String industry1;

    /**
     * null
     */
    private String industry2;

    /**
     * null
     */
    private String industry3;

    /**
     * null
     */
    private String industry4;

    /**
     * 待清算款
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal delayPayAmt;

    /**
     * 当日成本
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal costAmt;

    /**
     * 当日公允价值变动
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal floatIngpl;

    /**
     * 当日累计应计利息余额
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal intincexp;

    /**
     * null
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal intadjexpAdd;

    /**
     * null
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal lossAmt;

    /**
     * 行类一级分类
     */
    private String inAssetType;

    /**
     * 公共PPP
     */
    private String pppflag;

    /**
     * 是否投向地方政府平台
     */
    private String monitType;

    /**
     * 管理方式
     */
    private String manageType;

    /**
     * 是否政府投资基金
     */
    private String isGovernFund1;

    /**
     * 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    private String privatefundFlag;

    /**
     * 是否投向金融资产投资公司发行的私募资产管理产品
     */
    private String privateprdFlag;

    /**
     * 是否投向市场化债转股相关产品的部分
     */
    private String bondtostockFlag;

    /**
     * null
     */
    private String creditGrade;

    /**
     * null
     */
    private String mainGrade;

    /**
     * null
     */
    private String typeFinal;

    /**
     * null
     */
    private String orgAssetType1;

    /**
     * null
     */
    private String orgAssetType2;

    /**
     * null
     */
    private String orgAssetType3;

    /**
     * 评级结果
     */
    private String rating;

    /**
     * 资产二级分类
     */
    private String assettype2;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 穿透前原始资产代码
     */
    private String oldAssetCode;

    /**
     * 转型表资产一级分类
     */
    private String sunassettype;

    /**
     * 转型表资产二级分类
     */
    private String sunassettype2;

    /**
     * 银行补充工具
     */
    private String isYhbcgj;

    /**
     * 非标债权行业归属
     */
    private String isFbhy;

    /**
     * 取得 头寸编号
     * @return 头寸编号
     */
    public String getPosuuid() {
        return posuuid;
    }

    /**
     * 设置 头寸编号
     * @param posuuid 头寸编号
     */
    public void setPosuuid(String posuuid) {
        this.posuuid = posuuid == null ? null : posuuid.trim();
    }

    /**
     * 取得 统计日期
     * @return 统计日期
     */
    public String getTerm() {
        return term;
    }

    /**
     * 设置 统计日期
     * @param term 统计日期
     */
    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    /**
     * 取得 月报分类
     * @return 月报分类
     */
    public String getDetailno() {
        return detailno;
    }

    /**
     * 设置 月报分类
     * @param detailno 月报分类
     */
    public void setDetailno(String detailno) {
        this.detailno = detailno == null ? null : detailno.trim();
    }

    /**
     * 取得 投资品种
     * @return 投资品种
     */
    public String getInvestVariety() {
        return investVariety;
    }

    /**
     * 设置 投资品种
     * @param investVariety 投资品种
     */
    public void setInvestVariety(String investVariety) {
        this.investVariety = investVariety == null ? null : investVariety.trim();
    }

    /**
     * 取得 资产ID
     * @return 资产ID
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 设置 资产ID
     * @param assetCode 资产ID
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 取得 资产一级分类
     * @return 资产一级分类
     */
    public String getAssettype() {
        return assettype;
    }

    /**
     * 设置 资产一级分类
     * @param assettype 资产一级分类
     */
    public void setAssettype(String assettype) {
        this.assettype = assettype == null ? null : assettype.trim();
    }

    /**
     * 取得 产品代码
     * @return 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 设置 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 取得 是否合规新产品
     * @return 是否合规新产品
     */
    public String getIsFhxgcp() {
        return isFhxgcp;
    }

    /**
     * 设置 是否合规新产品
     * @param isFhxgcp 是否合规新产品
     */
    public void setIsFhxgcp(String isFhxgcp) {
        this.isFhxgcp = isFhxgcp == null ? null : isFhxgcp.trim();
    }

    /**
     * 取得 穿透前资产价值
     * @return 穿透前资产价值
     */
    public BigDecimal getAssetvalueA() {
        return assetvalueA;
    }

    /**
     * 设置 穿透前资产价值
     * @param assetvalueA 穿透前资产价值
     */
    public void setAssetvalueA(BigDecimal assetvalueA) {
        this.assetvalueA = assetvalueA;
    }

    /**
     * 取得 穿透后资产价值
     * @return 穿透后资产价值
     */
    public BigDecimal getAssetvalueB() {
        return assetvalueB;
    }

    /**
     * 设置 穿透后资产价值
     * @param assetvalueB 穿透后资产价值
     */
    public void setAssetvalueB(BigDecimal assetvalueB) {
        this.assetvalueB = assetvalueB;
    }

    /**
     * 取得 资产起息日
     * @return 资产起息日
     */
    public Date getVdate() {
        return vdate;
    }

    /**
     * 设置 资产起息日
     * @param vdate 资产起息日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    /**
     * 取得 资产到期日
     * @return 资产到期日
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * 设置 资产到期日
     * @param mdate 资产到期日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    /**
     * 取得 null
     * @return null
     */
    public Date getOpenplan() {
        return openplan;
    }

    /**
     * 设置 null
     * @param openplan null
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setOpenplan(Date openplan) {
        this.openplan = openplan;
    }

    /**
     * 取得 null
     * @return null
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 设置 null
     * @param subjectName null
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getRivalParty() {
        return rivalParty;
    }

    /**
     * 设置 null
     * @param rivalParty null
     */
    public void setRivalParty(String rivalParty) {
        this.rivalParty = rivalParty == null ? null : rivalParty.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 设置 null
     * @param rate null
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 取得 null
     * @return null
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 设置 null
     * @param ccy null
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 取得 所属行业
     * @return 所属行业
     */
    public String getIndustry1() {
        return industry1;
    }

    /**
     * 设置 所属行业
     * @param industry1 所属行业
     */
    public void setIndustry1(String industry1) {
        this.industry1 = industry1 == null ? null : industry1.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getIndustry2() {
        return industry2;
    }

    /**
     * 设置 null
     * @param industry2 null
     */
    public void setIndustry2(String industry2) {
        this.industry2 = industry2 == null ? null : industry2.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getIndustry3() {
        return industry3;
    }

    /**
     * 设置 null
     * @param industry3 null
     */
    public void setIndustry3(String industry3) {
        this.industry3 = industry3 == null ? null : industry3.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getIndustry4() {
        return industry4;
    }

    /**
     * 设置 null
     * @param industry4 null
     */
    public void setIndustry4(String industry4) {
        this.industry4 = industry4 == null ? null : industry4.trim();
    }

    /**
     * 取得 待清算款
     * @return 待清算款
     */
    public BigDecimal getDelayPayAmt() {
        return delayPayAmt;
    }

    /**
     * 设置 待清算款
     * @param delayPayAmt 待清算款
     */
    public void setDelayPayAmt(BigDecimal delayPayAmt) {
        this.delayPayAmt = delayPayAmt;
    }

    /**
     * 取得 当日成本
     * @return 当日成本
     */
    public BigDecimal getCostAmt() {
        return costAmt;
    }

    /**
     * 设置 当日成本
     * @param costAmt 当日成本
     */
    public void setCostAmt(BigDecimal costAmt) {
        this.costAmt = costAmt;
    }

    /**
     * 取得 当日公允价值变动
     * @return 当日公允价值变动
     */
    public BigDecimal getFloatIngpl() {
        return floatIngpl;
    }

    /**
     * 设置 当日公允价值变动
     * @param floatIngpl 当日公允价值变动
     */
    public void setFloatIngpl(BigDecimal floatIngpl) {
        this.floatIngpl = floatIngpl;
    }

    /**
     * 取得 当日累计应计利息余额
     * @return 当日累计应计利息余额
     */
    public BigDecimal getIntincexp() {
        return intincexp;
    }

    /**
     * 设置 当日累计应计利息余额
     * @param intincexp 当日累计应计利息余额
     */
    public void setIntincexp(BigDecimal intincexp) {
        this.intincexp = intincexp;
    }

    /**
     * 取得 null
     * @return null
     */
    public BigDecimal getIntadjexpAdd() {
        return intadjexpAdd;
    }

    /**
     * 设置 null
     * @param intadjexpAdd null
     */
    public void setIntadjexpAdd(BigDecimal intadjexpAdd) {
        this.intadjexpAdd = intadjexpAdd;
    }

    /**
     * 取得 null
     * @return null
     */
    public BigDecimal getLossAmt() {
        return lossAmt;
    }

    /**
     * 设置 null
     * @param lossAmt null
     */
    public void setLossAmt(BigDecimal lossAmt) {
        this.lossAmt = lossAmt;
    }

    /**
     * 取得 行类一级分类
     * @return 行类一级分类
     */
    public String getInAssetType() {
        return inAssetType;
    }

    /**
     * 设置 行类一级分类
     * @param inAssetType 行类一级分类
     */
    public void setInAssetType(String inAssetType) {
        this.inAssetType = inAssetType == null ? null : inAssetType.trim();
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
     * 取得 是否投向地方政府平台
     * @return 是否投向地方政府平台
     */
    public String getMonitType() {
        return monitType;
    }

    /**
     * 设置 是否投向地方政府平台
     * @param monitType 是否投向地方政府平台
     */
    public void setMonitType(String monitType) {
        this.monitType = monitType == null ? null : monitType.trim();
    }

    /**
     * 取得 管理方式
     * @return 管理方式
     */
    public String getManageType() {
        return manageType;
    }

    /**
     * 设置 管理方式
     * @param manageType 管理方式
     */
    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    /**
     * 取得 是否政府投资基金
     * @return 是否政府投资基金
     */
    public String getIsGovernFund1() {
        return isGovernFund1;
    }

    /**
     * 设置 是否政府投资基金
     * @param isGovernFund1 是否政府投资基金
     */
    public void setIsGovernFund1(String isGovernFund1) {
        this.isGovernFund1 = isGovernFund1 == null ? null : isGovernFund1.trim();
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
     * 取得 是否投向市场化债转股相关产品的部分
     * @return 是否投向市场化债转股相关产品的部分
     */
    public String getBondtostockFlag() {
        return bondtostockFlag;
    }

    /**
     * 设置 是否投向市场化债转股相关产品的部分
     * @param bondtostockFlag 是否投向市场化债转股相关产品的部分
     */
    public void setBondtostockFlag(String bondtostockFlag) {
        this.bondtostockFlag = bondtostockFlag == null ? null : bondtostockFlag.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getCreditGrade() {
        return creditGrade;
    }

    /**
     * 设置 null
     * @param creditGrade null
     */
    public void setCreditGrade(String creditGrade) {
        this.creditGrade = creditGrade == null ? null : creditGrade.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getMainGrade() {
        return mainGrade;
    }

    /**
     * 设置 null
     * @param mainGrade null
     */
    public void setMainGrade(String mainGrade) {
        this.mainGrade = mainGrade == null ? null : mainGrade.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getTypeFinal() {
        return typeFinal;
    }

    /**
     * 设置 null
     * @param typeFinal null
     */
    public void setTypeFinal(String typeFinal) {
        this.typeFinal = typeFinal == null ? null : typeFinal.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getOrgAssetType1() {
        return orgAssetType1;
    }

    /**
     * 设置 null
     * @param orgAssetType1 null
     */
    public void setOrgAssetType1(String orgAssetType1) {
        this.orgAssetType1 = orgAssetType1 == null ? null : orgAssetType1.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getOrgAssetType2() {
        return orgAssetType2;
    }

    /**
     * 设置 null
     * @param orgAssetType2 null
     */
    public void setOrgAssetType2(String orgAssetType2) {
        this.orgAssetType2 = orgAssetType2 == null ? null : orgAssetType2.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getOrgAssetType3() {
        return orgAssetType3;
    }

    /**
     * 设置 null
     * @param orgAssetType3 null
     */
    public void setOrgAssetType3(String orgAssetType3) {
        this.orgAssetType3 = orgAssetType3 == null ? null : orgAssetType3.trim();
    }

    /**
     * 取得 评级结果
     * @return 评级结果
     */
    public String getRating() {
        return rating;
    }

    /**
     * 设置 评级结果
     * @param rating 评级结果
     */
    public void setRating(String rating) {
        this.rating = rating == null ? null : rating.trim();
    }

    /**
     * 取得 资产二级分类
     * @return 资产二级分类
     */
    public String getAssettype2() {
        return assettype2;
    }

    /**
     * 设置 资产二级分类
     * @param assettype2 资产二级分类
     */
    public void setAssettype2(String assettype2) {
        this.assettype2 = assettype2 == null ? null : assettype2.trim();
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
     * 取得 穿透前原始资产代码
     * @return 穿透前原始资产代码
     */
    public String getOldAssetCode() {
        return oldAssetCode;
    }

    /**
     * 设置 穿透前原始资产代码
     * @param oldAssetCode 穿透前原始资产代码
     */
    public void setOldAssetCode(String oldAssetCode) {
        this.oldAssetCode = oldAssetCode == null ? null : oldAssetCode.trim();
    }

    /**
     * 取得 转型表资产一级分类
     * @return 转型表资产一级分类
     */
    public String getSunassettype() {
        return sunassettype;
    }

    /**
     * 设置 转型表资产一级分类
     * @param sunassettype 转型表资产一级分类
     */
    public void setSunassettype(String sunassettype) {
        this.sunassettype = sunassettype == null ? null : sunassettype.trim();
    }

    /**
     * 取得 转型表资产二级分类
     * @return 转型表资产二级分类
     */
    public String getSunassettype2() {
        return sunassettype2;
    }

    /**
     * 设置 转型表资产二级分类
     * @param sunassettype2 转型表资产二级分类
     */
    public void setSunassettype2(String sunassettype2) {
        this.sunassettype2 = sunassettype2 == null ? null : sunassettype2.trim();
    }

    /**
     * 取得 银行补充工具
     * @return 银行补充工具
     */
    public String getIsYhbcgj() {
        return isYhbcgj;
    }

    /**
     * 设置 银行补充工具
     * @param isYhbcgj 银行补充工具
     */
    public void setIsYhbcgj(String isYhbcgj) {
        this.isYhbcgj = isYhbcgj == null ? null : isYhbcgj.trim();
    }

    /**
     * 取得 非标债权行业归属
     * @return 非标债权行业归属
     */
    public String getIsFbhy() {
        return isFbhy;
    }

    /**
     * 设置 非标债权行业归属
     * @param isFbhy 非标债权行业归属
     */
    public void setIsFbhy(String isFbhy) {
        this.isFbhy = isFbhy == null ? null : isFbhy.trim();
    }


    public String getCounterListCode(){
        return this.getProdId()+"_"+this.getAssetCode();
    }

    public String getDetailCode(){
        return this.getProdId()+"_"+this.getAssetCode()+"_"+this.getDetailno()+"_"+this.getOldAssetCode();
    }

}