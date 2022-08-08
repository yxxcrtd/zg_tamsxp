package com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dto;

import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author:  meifaxin
 * @创建时间: 2021年7月29日10:43:50
 * 产品基本信息预览表
 */
public class PbcPrdBaseinfoSubDatDiffDto {

    /**
     * 序号
     */
    private Integer rowIndex;
    /**
     * 主键uuid
     */
    private String registSeqno;
    /**
     * 人行代码（外部）
     */
    private String pbcProdIdExternal;

    /**
     * 人行产品代码
     */
    @BusinessKey("PBC_PROD_ID")
    private String pbcProdId;

    /**
     * 理财产品的发行机构给代码
     */
    private String issuePartyId;

    /**
     * 理财产品的发行机构名称
     */
    private String issuePartyName;

    /**
     * 1-理财产品2-资金信托
     */
    @DictFormat(DictCode.人行产品种类)
    private String pbcProdType;

    /**
     * 产品全称
     */
    private String prodName;

    /**
     * 产品品牌
     */
    private String prodBrand;

    /**
     * 产品期次
     */
    private BigDecimal prodStage;

    /**
     * 理财产品代码
     */
    private String prodId;

    /**
     * 募集币种
     */
    private String raiseCcy;

    /**
     * 兑付本金币种
     */
    private String cashPrinCcy;

    /**
     * 兑付收益币种
     */
    private String cashProfCcy;

    /**
     * 客户类型，3 住户/4 广义政府/5 非金融企业/6 金融机构（实体）/7 特定目的载体/8 境外
     */
    @DictFormat(DictCode.人行客户类型)
    private String customerType;

    /**
     * 募集方式，1-公募，2-私募
     */
    @DictFormat(DictCode.人行募集方式)
    private String raiseType;

    /**
     * 管理方式，1-集合管理/2-单独管理
     */
    @DictFormat(DictCode.人行管理类型)
    private String manageType;

    /**
     * 受托职责，1-主动管理业务/2-被动管理业务
     */
    @DictFormat(DictCode.人行管理方式)
    private String fiduciaryDuty;

    /**
     * 合作模式，11-独立运作/12-银信合作/13-银保合作/14-银基合作/15-银证合作/16-混合类/17-其他/18-不适用
     */
    @DictFormat(DictCode.人行合作模式)
    private String contMode;

    /**
     * 运行方式，2-开放式净值型/3-开放式非净值型/4一封闭式净值型/5-封闭式非净值型
     */
    @DictFormat(DictCode.人行产品运作模式)
    private String operateMode;

    /**
     * 产品类型，007-固定收益类/008-权益类/009-商品及金融衍生品类/010-混合类
     */
    @DictFormat(DictCode.人行产品类型)
    private String ivstorientation;

    /**
     * 业务模式，1 表内存款/2 表内非存款负债/3 表外
     */
    @DictFormat(DictCode.人行业务模式)
    private String busiModel;
    /**
     * PROFIT_TYPE产品收益类型
     */
    private String profitType;

    /**
     * 收益保障标识,1-是，2-否
     */
    @DictFormat(DictCode.人行是否)
    private String incomeGuaranIdent;

    /**
     * 本金保障标识,1-是，2-否
     */
    @DictFormat(DictCode.人行是否)
    private String prinGuaranIdent;

    /**
     * 预计客户最高年收益率
     */
    private BigDecimal highYieldIssue;

    /**
     * 预计客户最低年收益率
     */
    private BigDecimal lowYieldIssue;

    /**
     * 募集起始日期
     */
    private String raiseVdate01;

    /**
     * 募集结束日期
     */
    private String raiseEnddate01;

    /**
     * 产品起始日期
     */
    private String vdate01;

    /**
     * 产品预计终止日期
     */
    private String mdate01;

    /**
     * 发行机构提前终止权标识，1-有，2-无
     */
    @DictFormat(DictCode.人行有无)
    private String termRightFlag;

    /**
     * 客户赎回权标识，1-有，2-无
     */
    @DictFormat(DictCode.人行有无)
    private String cusRedeemFlag;

    /**
     * 产品增信标识，1-是，2-否
     */
    @DictFormat(DictCode.人行是否)
    private String creditFlag;

    /**
     * 增信机构类型，1 广义政府/2 非金融性公司/3 金融性公司/4 住户部门/5 国外部门
     */
    @DictFormat(DictCode.人行信誉机构类型)
    private String creditType;

    /**
     * 增信形式，1-内部增级，2-外部增级
     */
    @DictFormat(DictCode.人行增信形式)
    private String creditMode;

    /**
     * 境内托管机构名称
     */
    @DictFormat(DictCode.境内托管机构名称)
    private String inDepName;

    /**
     * 境内托管机构代码
     */
    private String inDepCode;

    /**
     * 境外托管机构国别
     */
    private String outDepCtry;

    /**
     * 境外托管机构名称
     */
    private String outDepName;

    /**
     * 产品起始日期
     */
    private Date vdate;

    /**
     * 产品预计终止日期
     */
    private Date mdate;

    /**
     * 产品对应资产池代码
     */
    private String accountId;

    /**
     * 分级产品标识，1-是，2-否
     */
    @DictFormat(DictCode.人行是否)
    private String isConstracte;

    /**
     * 收益权转让产品标识，1-是，2-否
     */
    @DictFormat(DictCode.人行是否)
    private String transferType;

    /**
     * 理财产品发起机构标识,2 理财子公司发行/1 商业银行发行
     */
    @DictFormat(DictCode.人行理财产品发起机构标识)
    private String identiFlag;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 报送反馈状态
     */
    @DictFormat(DictCode.人行报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 人行报送截止日
     */
    private Date pbcSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 创建部门
     */
    private String createDept;

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

    private List<String> prodList;

    /**
     * 募集起始日期
     */
    private Date raiseVdate;

    /**
     * 募集结束日期
     */
    private Date raiseEnddate;

    /**
     *  内部产品代码
     */
    private String insideProdId;


    /**
     * 募集起始日期
     */
    private String raiseVdate02;

    /**
     * 募集结束日期
     */
    private String raiseEnddate02;

    /**
     * 产品起始日期
     */
    private String vdate02;

    /**
     * 产品预计终止日期
     */
    private String mdate02;


    /**
     * 预计客户最高年收益率（导出人行excel用）
     */
    private String highYieldIssueToString;

    /**
     * 预计客户最低年收益率（导出人行excel用）
     */
    private String lowYieldIssueToString;


    /**
     * 报送产品代码
     *
     */
    private  String subProdId;

    /**
     * 货币市场基金标识
     */
    private String  isMarketFund;
    /**
     * 压缩包编码
     */
    private String datZipCode;

    /**
     * 跨境理财通标识
     *
     */
    @DictFormat(DictCode.人行是否)
    private  String crossBorderFlag;

    /**
     * 以财产权信托名义开展的资金信托标识
     *
     */
    @DictFormat(DictCode.人行是否)
    private  String zjxtFlag;



    private String pbcProdIdPrd;


    private String prodIdPrd;
    private String issuePartyIdPrd;
    private String prodNamePrd;
    private String pbcProdTypePrd;
    private String ivstorientationPrd;
    private String prodBrandPrd;
    private BigDecimal prodStagePrd;
    private String prodTypePrd;
    private String raiseTypePrd;
    private String manageTypePrd;
    private String operateModePrd;
    private String InsideProdIdPrd;
    private Date 	raiseVdatePrd;
    private String 	raiseVdate01Prd;
    private Date 	raiseEnddatePrd;
    private String 	raiseEnddate01Prd;
    private String 	termRightFlagPrd;
    private String 	cusRedeemFlagPrd;
    private String 	creditFlagPrd;
    private String 	inDepCodePrd;
    private String 	outDepCtryPrd;
    private String 	outDepNamePrd;
    private Date 	vdatePrd;
    private Date 	mdatePrd;
    private String 	vdate01Prd;
    private String 	mdate01Prd;
    private String 	fiduciaryDutyPrd;
    private String 	isConstractePrd;
    private String isMarketFundPrd;
    private String 	transferTypePrd;
    private String 	transferType1Prd;
    private String 	transferType2Prd;
    private String 	transferType3Prd;
    private String 	raiseCcyPrd;
    private String 	cashPrinCcyPrd;
    private String 	cashProfCcyPrd;
    private String 	creditTypePrd;
    private String 	creditModePrd;
    private  String crossBorderFlagPrd;
    private  String zjxtFlagPrd;


    private Integer pbcProdIdDiff=0;
    private Integer prodIdDiff=0;
    private Integer issuePartyIdDiff=0;
    private Integer prodNameDiff=0;
    private Integer pbcProdTypeDiff=0;
    private Integer ivstorientationDiff=0;
    private Integer prodBrandDiff=0;
    private Integer prodStageDiff=0;
    private Integer isMarketFundDiff=0;
    private Integer prodTypeDiff=0;
    private Integer InsideProdIdDiff=0;
    private Integer raiseTypeDiff=0;
    private Integer manageTypeDiff=0;
    private Integer operateModeDiff=0;
    private Integer 	raiseVdate01Diff=0;
    private Integer 	raiseEnddate01Diff=0;
    private Integer 	termRightFlagDiff=0;
    private Integer 	cusRedeemFlagDiff=0;
    private Integer 	creditFlagDiff=0;
    private Integer 	inDepCodeDiff=0;
    private Integer 	outDepCtryDiff=0;
    private Integer 	outDepNameDiff=0;
    private Integer 	vdate01Diff=0;
    private Integer 	mdate01Diff=0;
    private Integer 	fiduciaryDutyDiff=0;
    private Integer 	isConstracteDiff=0;
    private Integer 	transferTypeDiff=0;
    private Integer 	transferType1Diff=0;
    private Integer 	transferType2Diff=0;
    private Integer 	transferType3Diff=0;
    private Integer 	raiseCcyDiff=0;
    private Integer 	cashPrinCcyDiff=0;
    private Integer 	cashProfCcyDiff=0;
    private Integer 	creditTypeDiff=0;
    private Integer 	creditModeDiff=0;
    private Integer 	crossBorderFlagDiff=0;
    private Integer 	zjxtFlagDiff=0;


    public String getProdIdPrd() {
        return prodIdPrd;
    }

    public void setProdIdPrd(String prodIdPrd) {
        this.prodIdPrd = prodIdPrd;
    }

    public Integer getProdIdDiff() {
        return prodIdDiff;
    }

    public void setProdIdDiff(Integer prodIdDiff) {
        this.prodIdDiff = prodIdDiff;
    }

    public Integer getIsMarketFundDiff() {
        return isMarketFundDiff;
    }

    public void setIsMarketFundDiff(Integer isMarketFundDiff) {
        this.isMarketFundDiff = isMarketFundDiff;
    }

    public String getDatZipCode() {
        return datZipCode;
    }

    public void setDatZipCode(String datZipCode) {
        this.datZipCode = datZipCode;
    }

    public String getIsMarketFund() {
        return isMarketFund;
    }

    public void setIsMarketFund(String isMarketFund) {
        this.isMarketFund = isMarketFund;
    }

    public String getSubProdId() {
        return subProdId;
    }

    public void setSubProdId(String subProdId) {
        this.subProdId = subProdId;
    }

    public String getHighYieldIssueToString() {
        return highYieldIssueToString;
    }

    public void setHighYieldIssueToString(String highYieldIssueToString) {
        this.highYieldIssueToString = highYieldIssueToString;
    }

    public String getLowYieldIssueToString() {
        return lowYieldIssueToString;
    }

    public void setLowYieldIssueToString(String lowYieldIssueToString) {
        this.lowYieldIssueToString = lowYieldIssueToString;
    }




    /**
     *  人行代码（外部）
     */
    private String pbcCode;

    /**
     *  人行产品代码（页面展示用）
     */
    private String pbcProdIdShow;

    /** 人行代码（外部）
     * @return PBC_CODE 人行代码（外部）
     */
    public String getPbcCode() {
        return pbcCode;
    }

    /**
     * @param pbcCode
     */
    public void setPbcCode(String pbcCode) {
        this.pbcCode = pbcCode;
    }


    /** 人行产品代码（页面展示用）
     * @return PBC_PROD_ID
     */
    public String getPbcProdIdShow() {
        return pbcProdIdShow;
    }

    /**
     * @param pbcProdIdShow
     */
    public void setPbcProdIdShow(String pbcProdIdShow) {
        this.pbcProdIdShow = pbcProdIdShow;
    }

    /** 内部产品代码
     * @return INSIDE_PROD_ID 内部产品代码
     */
    public String getInsideProdId() {
        return insideProdId;
    }

    /**
     * @param insideProdId
     */
    public void setInsideProdId(String insideProdId) {
        this.insideProdId = insideProdId;
    }

    /**
     * 主键uuid
     * @return REGIST_SEQNO 主键uuid
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 主键uuid
     * @param registSeqno 主键uuid
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 人行产品代码
     * @return PBC_PROD_ID 人行产品代码
     */
    public String getPbcProdId() {
        return pbcProdId;
    }

    /**
     * 人行产品代码
     * @param pbcProdId 人行产品代码
     */
    public void setPbcProdId(String pbcProdId) {
        this.pbcProdId = pbcProdId == null ? null : pbcProdId.trim();
    }

    /**
     * 理财产品的发行机构给代码
     * @return ISSUE_PARTY_ID 理财产品的发行机构给代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 理财产品的发行机构给代码
     * @param issuePartyId 理财产品的发行机构给代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 1-理财产品2-资金信托
     * @return PBC_PROD_TYPE 1-理财产品2-资金信托
     */
    public String getPbcProdType() {
        return pbcProdType;
    }

    /**
     * 1-理财产品2-资金信托
     * @param pbcProdType 1-理财产品2-资金信托
     */
    public void setPbcProdType(String pbcProdType) {
        this.pbcProdType = pbcProdType == null ? null : pbcProdType.trim();
    }

    /**
     * 产品全称
     * @return PROD_NAME 产品全称
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * 产品全称
     * @param prodName 产品全称
     */
    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    /**
     * 产品品牌
     * @return PROD_BRAND 产品品牌
     */
    public String getProdBrand() {
        return prodBrand;
    }

    /**
     * 产品品牌
     * @param prodBrand 产品品牌
     */
    public void setProdBrand(String prodBrand) {
        this.prodBrand = prodBrand == null ? null : prodBrand.trim();
    }

    /**
     * 产品期次
     * @return PROD_STAGE 产品期次
     */
    public BigDecimal getProdStage() {
        return prodStage;
    }

    /**
     * 产品期次
     * @param prodStage 产品期次
     */
    public void setProdStage(BigDecimal prodStage) {
        this.prodStage = prodStage;
    }

    public String getProdStageStr() {
        return getProdStage()==null?null:Integer.toString(getProdStage().intValue());
    }


    /**
     * 理财产品代码
     * @return PROD_ID 理财产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 理财产品代码
     * @param prodId 理财产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 募集币种
     * @return RAISE_CCY 募集币种
     */
    public String getRaiseCcy() {
        return raiseCcy;
    }

    /**
     * 募集币种
     * @param raiseCcy 募集币种
     */
    public void setRaiseCcy(String raiseCcy) {
        this.raiseCcy = raiseCcy == null ? null : raiseCcy.trim();
    }

    /**
     * 兑付本金币种
     * @return CASH_PRIN_CCY 兑付本金币种
     */
    public String getCashPrinCcy() {
        return cashPrinCcy;
    }

    /**
     * 兑付本金币种
     * @param cashPrinCcy 兑付本金币种
     */
    public void setCashPrinCcy(String cashPrinCcy) {
        this.cashPrinCcy = cashPrinCcy == null ? null : cashPrinCcy.trim();
    }

    /**
     * 兑付收益币种
     * @return CASH_PROF_CCY 兑付收益币种
     */
    public String getCashProfCcy() {
        return cashProfCcy;
    }

    /**
     * 兑付收益币种
     * @param cashProfCcy 兑付收益币种
     */
    public void setCashProfCcy(String cashProfCcy) {
        this.cashProfCcy = cashProfCcy == null ? null : cashProfCcy.trim();
    }

    /**
     * 客户类型，3 住户/4 广义政府/5 非金融企业/6 金融机构（实体）/7 特定目的载体/8 境外
     * @return CUSTOMER_TYPE 客户类型，3 住户/4 广义政府/5 非金融企业/6 金融机构（实体）/7 特定目的载体/8 境外
     */
    public String getCustomerType() {
        return customerType;
    }

    /**
     * 客户类型，3 住户/4 广义政府/5 非金融企业/6 金融机构（实体）/7 特定目的载体/8 境外
     * @param customerType 客户类型，3 住户/4 广义政府/5 非金融企业/6 金融机构（实体）/7 特定目的载体/8 境外
     */
    public void setCustomerType(String customerType) {
        this.customerType = customerType == null ? null : customerType.trim();
    }

    /**
     * 募集方式，1-公募，2-私募
     * @return RAISE_TYPE 募集方式，1-公募，2-私募
     */
    public String getRaiseType() {
        return raiseType;
    }

    /**
     * 募集方式，1-公募，2-私募
     * @param raiseType 募集方式，1-公募，2-私募
     */
    public void setRaiseType(String raiseType) {
        this.raiseType = raiseType == null ? null : raiseType.trim();
    }

    /**
     * 管理方式，1-集合管理/2-单独管理
     * @return MANAGE_TYPE 管理方式，1-集合管理/2-单独管理
     */
    public String getManageType() {
        return manageType;
    }

    /**
     * 管理方式，1-集合管理/2-单独管理
     * @param manageType 管理方式，1-集合管理/2-单独管理
     */
    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    /**
     * 受托职责，1-主动管理业务/2-被动管理业务
     * @return FIDUCIARY_DUTY 受托职责，1-主动管理业务/2-被动管理业务
     */
    public String getFiduciaryDuty() {
        return fiduciaryDuty;
    }

    /**
     * 受托职责，1-主动管理业务/2-被动管理业务
     * @param fiduciaryDuty 受托职责，1-主动管理业务/2-被动管理业务
     */
    public void setFiduciaryDuty(String fiduciaryDuty) {
        this.fiduciaryDuty = fiduciaryDuty == null ? null : fiduciaryDuty.trim();
    }

    /**
     * 合作模式，11-独立运作/12-银信合作/13-银保合作/14-银基合作/15-银证合作/16-混合类/17-其他/18-不适用
     * @return CONT_MODE 合作模式，11-独立运作/12-银信合作/13-银保合作/14-银基合作/15-银证合作/16-混合类/17-其他/18-不适用
     */
    public String getContMode() {
        return contMode;
    }

    /**
     * 合作模式，11-独立运作/12-银信合作/13-银保合作/14-银基合作/15-银证合作/16-混合类/17-其他/18-不适用
     * @param contMode 合作模式，11-独立运作/12-银信合作/13-银保合作/14-银基合作/15-银证合作/16-混合类/17-其他/18-不适用
     */
    public void setContMode(String contMode) {
        this.contMode = contMode == null ? null : contMode.trim();
    }

    /**
     * 运行方式，2-开放式净值型/3-开放式非净值型/4一封闭式净值型/5-封闭式非净值型
     * @return OPERATE_MODE 运行方式，2-开放式净值型/3-开放式非净值型/4一封闭式净值型/5-封闭式非净值型
     */
    public String getOperateMode() {
        return operateMode;
    }

    /**
     * 运行方式，2-开放式净值型/3-开放式非净值型/4一封闭式净值型/5-封闭式非净值型
     * @param operateMode 运行方式，2-开放式净值型/3-开放式非净值型/4一封闭式净值型/5-封闭式非净值型
     */
    public void setOperateMode(String operateMode) {
        this.operateMode = operateMode == null ? null : operateMode.trim();
    }

    /**
     * 产品类型，1-固定收益类 2-权益类 3-商品及金融衍生品类4-混合类
     * @return IVSTORIENTATION 产品类型，
     */
    public String getIvstorientation() {
        return ivstorientation;
    }

    /**
     * 产品类型 1-固定收益类 2-权益类 3-商品及金融衍生品类4-混合类
     * @param ivstorientation 产品类型
     */
    public void setIvstorientation(String ivstorientation) {
        this.ivstorientation = ivstorientation == null ? null : ivstorientation.trim();
    }

    /**
     * 业务模式，1 表内存款/2 表内非存款负债/3 表外
     * @return BUSI_MODEL 业务模式，1 表内存款/2 表内非存款负债/3 表外
     */
    public String getBusiModel() {
        return busiModel;
    }

    /**
     * 业务模式，1 表内存款/2 表内非存款负债/3 表外
     * @param busiModel 业务模式，1 表内存款/2 表内非存款负债/3 表外
     */
    public void setBusiModel(String busiModel) {
        this.busiModel = busiModel == null ? null : busiModel.trim();
    }

    /**
     * 收益保障标识,1-是，2-否
     * @return INCOME_GUARAN_IDENT 收益保障标识,1-是，2-否
     */
    public String getIncomeGuaranIdent() {
        return incomeGuaranIdent;
    }

    /**
     * 收益保障标识,1-是，2-否
     * @param incomeGuaranIdent 收益保障标识,1-是，2-否
     */
    public void setIncomeGuaranIdent(String incomeGuaranIdent) {
        this.incomeGuaranIdent = incomeGuaranIdent == null ? null : incomeGuaranIdent.trim();
    }

    /**
     * 本金保障标识,1-是，2-否
     * @return PRIN_GUARAN_IDENT 本金保障标识,1-是，2-否
     */
    public String getPrinGuaranIdent() {
        return prinGuaranIdent;
    }

    /**
     * 本金保障标识,1-是，2-否
     * @param prinGuaranIdent 本金保障标识,1-是，2-否
     */
    public void setPrinGuaranIdent(String prinGuaranIdent) {
        this.prinGuaranIdent = prinGuaranIdent == null ? null : prinGuaranIdent.trim();
    }

    /**
     * 预计客户最高年收益率
     * @return HIGH_YIELD_ISSUE 预计客户最高年收益率
     */
    public BigDecimal getHighYieldIssue() {
        return highYieldIssue;
    }

    /**
     * 预计客户最高年收益率
     * @param highYieldIssue 预计客户最高年收益率
     */
    public void setHighYieldIssue(BigDecimal highYieldIssue) {
        this.highYieldIssue = highYieldIssue;
    }

    /**
     * 预计客户最低年收益率
     * @return LOW_YIELD_ISSUE 预计客户最低年收益率
     */
    public BigDecimal getLowYieldIssue() {
        return lowYieldIssue;
    }

    /**
     * 预计客户最低年收益率
     * @param lowYieldIssue 预计客户最低年收益率
     */
    public void setLowYieldIssue(BigDecimal lowYieldIssue) {
        this.lowYieldIssue = lowYieldIssue;
    }

    /**
     * 募集起始日期
     * @return RAISE_VDATE 募集起始日期
     */
    public Date getRaiseVdate() {
        return raiseVdate;
    }
    /**
     * 募集起始日期
     * @param raiseVdate 募集起始日期
     */
    public void setRaiseVdate(Date raiseVdate) {
        this.raiseVdate = raiseVdate;
    }


    /**
     * 募集结束日期
     * @return RAISE_ENDDATE 募集结束日期
     */
    public Date getRaiseEnddate() {
        return raiseEnddate;
    }

    /**
     * 募集结束日期
     * @param raiseEnddate 募集结束日期
     */
    public void setRaiseEnddate(Date raiseEnddate) {
        this.raiseEnddate = raiseEnddate;
    }
    /**
     * 发行机构提前终止权标识，1-有，2-无
     * @return TERM_RIGHT_FLAG 发行机构提前终止权标识，1-有，2-无
     */
    public String getTermRightFlag() {
        return termRightFlag;
    }

    /**
     * 发行机构提前终止权标识，1-有，2-无
     * @param termRightFlag 发行机构提前终止权标识，1-有，2-无
     */
    public void setTermRightFlag(String termRightFlag) {
        this.termRightFlag = termRightFlag == null ? null : termRightFlag.trim();
    }

    /**
     * 客户赎回权标识，1-有，2-无
     * @return CUS_REDEEM_FLAG 客户赎回权标识，1-有，2-无
     */
    public String getCusRedeemFlag() {
        return cusRedeemFlag;
    }

    /**
     * 客户赎回权标识，1-有，2-无
     * @param cusRedeemFlag 客户赎回权标识，1-有，2-无
     */
    public void setCusRedeemFlag(String cusRedeemFlag) {
        this.cusRedeemFlag = cusRedeemFlag == null ? null : cusRedeemFlag.trim();
    }

    /**
     * 产品增信标识，1-是，2-否
     * @return CREDIT_FLAG 产品增信标识，1-是，2-否
     */
    public String getCreditFlag() {
        return creditFlag;
    }

    /**
     * 产品增信标识，1-是，2-否
     * @param creditFlag 产品增信标识，1-是，2-否
     */
    public void setCreditFlag(String creditFlag) {
        this.creditFlag = creditFlag == null ? null : creditFlag.trim();
    }

    /**
     * 增信机构类型，1 广义政府/2 非金融性公司/3 金融性公司/4 住户部门/5 国外部门
     * @return CREDIT_TYPE 增信机构类型，1 广义政府/2 非金融性公司/3 金融性公司/4 住户部门/5 国外部门
     */
    public String getCreditType() {
        return creditType;
    }

    /**
     * 增信机构类型，1 广义政府/2 非金融性公司/3 金融性公司/4 住户部门/5 国外部门
     * @param creditType 增信机构类型，1 广义政府/2 非金融性公司/3 金融性公司/4 住户部门/5 国外部门
     */
    public void setCreditType(String creditType) {
        this.creditType = creditType == null ? null : creditType.trim();
    }

    /**
     * 增信形式，1-内部增级，2-外部增级
     * @return CREDIT_MODE 增信形式，1-内部增级，2-外部增级
     */
    public String getCreditMode() {
        return creditMode;
    }

    /**
     * 增信形式，1-内部增级，2-外部增级
     * @param creditMode 增信形式，1-内部增级，2-外部增级
     */
    public void setCreditMode(String creditMode) {
        this.creditMode = creditMode == null ? null : creditMode.trim();
    }

    /**
     * 境内托管机构名称
     * @return IN_DEP_NAME 境内托管机构名称
     */
    public String getInDepName() {
        return inDepName;
    }

    /**
     * 境内托管机构名称
     * @param inDepName 境内托管机构名称
     */
    public void setInDepName(String inDepName) {
        this.inDepName = inDepName == null ? null : inDepName.trim();
    }

    /**
     * 境内托管机构代码
     * @return IN_DEP_CODE 境内托管机构代码
     */
    public String getInDepCode() {
        return inDepCode;
    }

    /**
     * 境内托管机构代码
     * @param inDepCode 境内托管机构代码
     */
    public void setInDepCode(String inDepCode) {
        this.inDepCode = inDepCode == null ? null : inDepCode.trim();
    }

    /**
     * 境外托管机构国别
     * @return OUT_DEP_CTRY 境外托管机构国别
     */
    public String getOutDepCtry() {
        return outDepCtry;
    }

    /**
     * 境外托管机构国别
     * @param outDepCtry 境外托管机构国别
     */
    public void setOutDepCtry(String outDepCtry) {
        this.outDepCtry = outDepCtry == null ? null : outDepCtry.trim();
    }

    /**
     * 境外托管机构名称
     * @return OUT_DEP_NAME 境外托管机构名称
     */
    public String getOutDepName() {
        return outDepName;
    }

    /**
     * 境外托管机构名称
     * @param outDepName 境外托管机构名称
     */
    public void setOutDepName(String outDepName) {
        this.outDepName = outDepName == null ? null : outDepName.trim();
    }

    /**
     * 产品起始日期
     * @return VDATE 产品起始日期
     */
    public Date getVdate() {
        return vdate;
    }

    /**
     * 产品起始日期
     * @param vdate 产品起始日期
     */
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    /**
     * 产品预计终止日期
     * @return MDATE 产品预计终止日期
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * 产品预计终止日期
     * @param mdate 产品预计终止日期
     */
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    /**
     * 产品对应资产池代码
     * @return ACCOUNT_ID 产品对应资产池代码
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 产品对应资产池代码
     * @param accountId 产品对应资产池代码
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    /**
     * 分级产品标识，1-是，2-否
     * @return IS_CONSTRACTE 分级产品标识，1-是，2-否
     */
    public String getIsConstracte() {
        return isConstracte;
    }

    /**
     * 分级产品标识，1-是，2-否
     * @param isConstracte 分级产品标识，1-是，2-否
     */
    public void setIsConstracte(String isConstracte) {
        this.isConstracte = isConstracte == null ? null : isConstracte.trim();
    }

    /**
     * 收益权转让产品标识，1-是，2-否
     * @return TRANSFER_TYPE 收益权转让产品标识，1-是，2-否
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     * 收益权转让产品标识，1-是，2-否
     * @param transferType 收益权转让产品标识，1-是，2-否
     */
    public void setTransferType(String transferType) {
        this.transferType = transferType == null ? null : transferType.trim();
    }

    /**
     * 理财产品发起机构标识,2 理财子公司发行/1 商业银行发行
     * @return IDENTI_FLAG 理财产品发起机构标识,2 理财子公司发行/1 商业银行发行
     */
    public String getIdentiFlag() {
        return identiFlag;
    }

    /**
     * 理财产品发起机构标识,2 理财子公司发行/1 商业银行发行
     * @param identiFlag 理财产品发起机构标识,2 理财子公司发行/1 商业银行发行
     */
    public void setIdentiFlag(String identiFlag) {
        this.identiFlag = identiFlag == null ? null : identiFlag.trim();
    }

    /**
     * 报送状态
     * @return SEND_STATUS 报送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 报送状态
     * @param sendStatus 报送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 报送日期
     * @return SUBMIT_TIME 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }
    public String getSubmitTime01() {
        return DateUtil.format(submitTime,"yyyy-MM-dd");
    }
    /**
     * 报送日期
     * @param submitTime 报送日期
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 报送反馈状态
     * @return SUBMIT_FEEDBACK_STATUS 报送反馈状态
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 报送反馈状态
     * @param submitFeedbackStatus 报送反馈状态
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 报送流水号
     * @return SUMIT_SEQ 报送流水号
     */
    public String getSumitSeq() {
        return sumitSeq;
    }

    /**
     * 报送流水号
     * @param sumitSeq 报送流水号
     */
    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq == null ? null : sumitSeq.trim();
    }

    /**
     * 数据日期
     * @return STATUS_DATE 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }
    public String getStatusDate01() {
        return DateUtil.format(statusDate,"yyyy-MM-dd");
    }
    /**
     * 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 人行报送截止日
     * @return PBC_SUBMIT_DEADLINE 人行报送截止日
     */
    public Date getPbcSubmitDeadline() {
        return pbcSubmitDeadline;
    }
    public String getPbcSubmitDeadline01() {
        return DateUtil.format(pbcSubmitDeadline,"yyyy-MM-dd");
    }

    /**
     * 人行报送截止日
     * @param pbcSubmitDeadline 人行报送截止日
     */
    public void setPbcSubmitDeadline(Date pbcSubmitDeadline) {
        this.pbcSubmitDeadline = pbcSubmitDeadline;
    }

    /**
     * 行内报送截止日
     * @return SUBMIT_DEADLINE 行内报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 行内报送截止日
     * @param submitDeadline 行内报送截止日
     */
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    /**
     * 数据状态
     * @return STATUS 数据状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 数据状态
     * @param status 数据状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 机构代码
     * @return ORG_CODE 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 创建部门
     * @return CREATE_DEPT 创建部门
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * 创建部门
     * @param createDept 创建部门
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新人
     * @return UPDATE_USER 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 更新时间
     * @return UPDATE_TIME 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<String> getProdList() {
        return prodList;
    }

    public void setProdList(List<String> prodList) {
        this.prodList = prodList;
    }

    public String getProfitType() {
        return profitType;
    }

    public void setProfitType(String profitType) {
        this.profitType = profitType;
    }
    public String getStatusStr(){
        return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
    }

    public String getSendStatusStr(){
        return SvrDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
    }

    public String getSubmitFeedbackStatusStr(){
        return SvrDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, this.getSubmitFeedbackStatus());
    }

    public String getRaiseVdate01() {
        return DateUtil.format(raiseVdate,"yyyy-MM-dd");
    }

    public String getRaiseEnddate01() {
        return DateUtil.format(raiseEnddate,"yyyy-MM-dd");
    }



    public String getIssuePartyName() {
        return issuePartyName;
    }

    public void setIssuePartyName(String issuePartyName) {
        this.issuePartyName = issuePartyName;
    }


    /***
     *
     * @param split
     * @return
     */
    public String toPbcString(String split){
        if(null==split){
            split = "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(this.getPbcProdId());  //产品代码
        sb.append(split).append(this.getIssuePartyId()); //发行机构代码
        sb.append(split).append(this.getProdName());//产品名称
        sb.append(split).append("1");//产品品种 默认为  1-银行非保本理财
        sb.append(split).append(this.getIvstorientation());//产品类型
        sb.append(split).append(this.getProdBrand());//产品品牌
        sb.append(split).append(this.getProdStage());//产品期次
        sb.append(split).append(this.getInsideProdId());//产品品牌
        sb.append(split).append(this.getRaiseType());//募集方式
        sb.append(split).append(this.getManageType());//管理方式
        sb.append(split).append(this.getOperateMode());//运行方式
        sb.append(split).append(DateUtil.format(this.getRaiseVdate()));//募集起始日期
        sb.append(split).append(DateUtil.format(this.getRaiseEnddate()));//募集结束日期
        sb.append(split).append(this.getTermRightFlag());//发行机构提前终止权标识
        sb.append(split).append(this.getCusRedeemFlag());//客户赎回权标识
        sb.append(split).append(this.getCreditFlag());//产品增信标识
        sb.append(split).append(this.getInDepCode());//境内托管机构代码
        sb.append(split).append(this.getOutDepCtry());//境外托管机构国别
        sb.append(split).append(this.getOutDepName());//境外托管机构名称
        sb.append(split).append(DateUtil.format(this.getVdate()));//产品起始日期
        sb.append(split).append(DateUtil.format(this.getMdate()));//产品预计终止日期
        sb.append(split).append(this.getFiduciaryDuty());//受托职责
        sb.append(split).append(this.getIsConstracte());//分级产品标识
        sb.append(split).append(this.getTransferType());//收益权转让产品标识
        sb.append(split).append(this.getIsMarketFund());//货币市场基金标识
        sb.append(split).append(this.getTransferType());//其他产品标识1
        sb.append(split).append(this.getTransferType());//其他产品标识2
        sb.append(split).append(this.getTransferType());//其他产品标识3
        sb.append(";");//数据分割

        //产品代码  / 币种种类 / 币种代码
        sb.append(this.getPbcProdId()).append(split).append("1").append(split).append(this.getRaiseCcy()).append(";");//募集资金币种
        sb.append(this.getPbcProdId()).append(split).append("2").append(split).append(this.getCashPrinCcy()).append(";");//兑付本金币种
        sb.append(this.getPbcProdId()).append(split).append("3").append(split).append(this.getCashProfCcy()).append(";");//兑付收益币种
        //若“产品增信标识”选择是，则此处必填
        if(DictCodeList.是否_中债_是.getValue().equals(this.getCreditFlag())){
            //产品信息—增信机构类型。
            //产品代码  / 增信机构类型
            for(String  code : this.getCreditType().split(",")){
                sb.append(this.getPbcProdId()).append(split).append(code).append(";");
            }

            //产品信息—增信形式。
            //产品代码  / 增信形式
            for(String  code : this.getCreditMode().split(",")){
                sb.append(this.getPbcProdId()).append(split).append(code).append(";");
            }
        }

        return sb.toString();
    }

    public String getPbcProdIdPrd() {
        return pbcProdIdPrd;
    }

    public void setPbcProdIdPrd(String pbcProdIdPrd) {
        this.pbcProdIdPrd = pbcProdIdPrd;

    }

    public String getIssuePartyIdPrd() {
        return issuePartyIdPrd;
    }

    public void setIssuePartyIdPrd(String issuePartyIdPrd) {
        this.issuePartyIdPrd = issuePartyIdPrd;

    }

    public String getProdNamePrd() {
        return prodNamePrd;
    }

    public void setProdNamePrd(String prodNamePrd) {
        this.prodNamePrd = prodNamePrd;

    }

    public String getPbcProdTypePrd() {
        return pbcProdTypePrd;
    }

    public void setPbcProdTypePrd(String pbcProdTypePrd) {
        this.pbcProdTypePrd = pbcProdTypePrd;

    }

    public String getIvstorientationPrd() {
        return ivstorientationPrd;
    }

    public void setIvstorientationPrd(String ivstorientationPrd) {
        this.ivstorientationPrd = ivstorientationPrd;

    }

    public String getProdBrandPrd() {
        return prodBrandPrd;
    }

    public void setProdBrandPrd(String prodBrandPrd) {
        this.prodBrandPrd = prodBrandPrd;

    }

    public BigDecimal getProdStagePrd() {
        return prodStagePrd;
    }

    public void setProdStagePrd(BigDecimal prodStagePrd) {
        this.prodStagePrd = prodStagePrd;

    }

    public String getProdTypePrd() {
        return prodTypePrd;
    }

    public void setProdTypePrd(String prodTypePrd) {
        this.prodTypePrd = prodTypePrd;

    }

    public String getRaiseTypePrd() {
        return raiseTypePrd;
    }

    public void setRaiseTypePrd(String raiseTypePrd) {
        this.raiseTypePrd = raiseTypePrd;

    }

    public String getManageTypePrd() {
        return manageTypePrd;
    }

    public void setManageTypePrd(String manageTypePrd) {
        this.manageTypePrd = manageTypePrd;

    }

    public String getOperateModePrd() {
        return operateModePrd;
    }

    public void setOperateModePrd(String operateModePrd) {
        this.operateModePrd = operateModePrd;

    }

    public Date getRaiseVdatePrd() {
        return raiseVdatePrd;
    }

    public void setRaiseVdatePrd(Date raiseVdatePrd) {
        this.raiseVdatePrd = raiseVdatePrd;

    }

    public Date getRaiseEnddatePrd() {
        return raiseEnddatePrd;
    }

    public void setRaiseEnddatePrd(Date raiseEnddatePrd) {
        this.raiseEnddatePrd = raiseEnddatePrd;

    }

    public String getTermRightFlagPrd() {
        return termRightFlagPrd;
    }

    public void setTermRightFlagPrd(String termRightFlagPrd) {
        this.termRightFlagPrd = termRightFlagPrd;

    }

    public String getCusRedeemFlagPrd() {
        return cusRedeemFlagPrd;
    }

    public void setCusRedeemFlagPrd(String cusRedeemFlagPrd) {
        this.cusRedeemFlagPrd = cusRedeemFlagPrd;

    }

    public String getCreditFlagPrd() {
        return creditFlagPrd;
    }

    public void setCreditFlagPrd(String creditFlagPrd) {
        this.creditFlagPrd = creditFlagPrd;

    }

    public String getInDepCodePrd() {
        return inDepCodePrd;
    }

    public void setInDepCodePrd(String inDepCodePrd) {
        this.inDepCodePrd = inDepCodePrd;

    }

    public String getOutDepCtryPrd() {
        return outDepCtryPrd;
    }

    public void setOutDepCtryPrd(String outDepCtryPrd) {
        this.outDepCtryPrd = outDepCtryPrd;

    }

    public String getOutDepNamePrd() {
        return outDepNamePrd;
    }

    public void setOutDepNamePrd(String outDepNamePrd) {
        this.outDepNamePrd = outDepNamePrd;

    }

    public Date getVdatePrd() {
        return vdatePrd;
    }

    public void setVdatePrd(Date vdatePrd) {
        this.vdatePrd = vdatePrd;

    }


    public Date getMdatePrd() {
        return mdatePrd;
    }
    public void setMdatePrd(Date mdatePrd) {
        this.mdatePrd = mdatePrd;

    }

    public String getFiduciaryDutyPrd() {
        return fiduciaryDutyPrd;
    }

    public void setFiduciaryDutyPrd(String fiduciaryDutyPrd) {
        this.fiduciaryDutyPrd = fiduciaryDutyPrd;

    }

    public String getIsConstractePrd() {
        return isConstractePrd;
    }

    public void setIsConstractePrd(String isConstractePrd) {
        this.isConstractePrd = isConstractePrd;

    }

    public String getTransferTypePrd() {
        return transferTypePrd;
    }

    public void setTransferTypePrd(String transferTypePrd) {
        this.transferTypePrd = transferTypePrd;

    }

    public String getTransferType1Prd() {
        return transferType1Prd;
    }

    public void setTransferType1Prd(String transferType1Prd) {
        this.transferType1Prd = transferType1Prd;

    }

    public String getTransferType2Prd() {
        return transferType2Prd;
    }

    public void setTransferType2Prd(String transferType2Prd) {
        this.transferType2Prd = transferType2Prd;

    }

    public String getTransferType3Prd() {
        return transferType3Prd;
    }

    public void setTransferType3Prd(String transferType3Prd) {
        this.transferType3Prd = transferType3Prd;

    }

    public String getRaiseCcyPrd() {
        return raiseCcyPrd;
    }

    public void setRaiseCcyPrd(String raiseCcyPrd) {
        this.raiseCcyPrd = raiseCcyPrd;

    }

    public String getCashPrinCcyPrd() {
        return cashPrinCcyPrd;
    }

    public void setCashPrinCcyPrd(String cashPrinCcyPrd) {
        this.cashPrinCcyPrd = cashPrinCcyPrd;

    }

    public String getCashProfCcyPrd() {
        return cashProfCcyPrd;
    }

    public void setCashProfCcyPrd(String cashProfCcyPrd) {
        this.cashProfCcyPrd = cashProfCcyPrd;

    }

    public String getCreditTypePrd() {
        return creditTypePrd;
    }

    public void setCreditTypePrd(String creditTypePrd) {
        this.creditTypePrd = creditTypePrd;

    }

    public String getCreditModePrd() {
        return creditModePrd;
    }

    public void setCreditModePrd(String creditModePrd) {
        this.creditModePrd = creditModePrd;

    }

    public Integer getPbcProdIdDiff() {
        return pbcProdIdDiff;
    }

    public void setPbcProdIdDiff(Integer pbcProdIdDiff) {
        this.pbcProdIdDiff = pbcProdIdDiff;

    }

    public Integer getIssuePartyIdDiff() {
        return issuePartyIdDiff;
    }

    public void setIssuePartyIdDiff(Integer issuePartyIdDiff) {
        this.issuePartyIdDiff = issuePartyIdDiff;

    }

    public Integer getProdNameDiff() {
        return prodNameDiff;
    }

    public void setProdNameDiff(Integer prodNameDiff) {
        this.prodNameDiff = prodNameDiff;

    }

    public Integer getPbcProdTypeDiff() {
        return pbcProdTypeDiff;
    }

    public void setPbcProdTypeDiff(Integer pbcProdTypeDiff) {
        this.pbcProdTypeDiff = pbcProdTypeDiff;

    }

    public Integer getIvstorientationDiff() {
        return ivstorientationDiff;
    }

    public void setIvstorientationDiff(Integer ivstorientationDiff) {
        this.ivstorientationDiff = ivstorientationDiff;

    }

    public Integer getProdBrandDiff() {
        return prodBrandDiff;
    }

    public void setProdBrandDiff(Integer prodBrandDiff) {
        this.prodBrandDiff = prodBrandDiff;

    }

    public Integer getProdStageDiff() {
        return prodStageDiff;
    }

    public void setProdStageDiff(Integer prodStageDiff) {
        this.prodStageDiff = prodStageDiff;

    }

    public Integer getProdTypeDiff() {
        return prodTypeDiff;
    }

    public void setProdTypeDiff(Integer prodTypeDiff) {
        this.prodTypeDiff = prodTypeDiff;

    }

    public Integer getRaiseTypeDiff() {
        return raiseTypeDiff;
    }

    public void setRaiseTypeDiff(Integer raiseTypeDiff) {
        this.raiseTypeDiff = raiseTypeDiff;

    }

    public Integer getManageTypeDiff() {
        return manageTypeDiff;
    }

    public void setManageTypeDiff(Integer manageTypeDiff) {
        this.manageTypeDiff = manageTypeDiff;

    }

    public Integer getOperateModeDiff() {
        return operateModeDiff;
    }

    public void setOperateModeDiff(Integer operateModeDiff) {
        this.operateModeDiff = operateModeDiff;

    }

    public Integer getRaiseVdate01Diff() {
        return raiseVdate01Diff;
    }

    public void setRaiseVdate01Diff(Integer raiseVdate01Diff) {
        this.raiseVdate01Diff = raiseVdate01Diff;

    }

    public Integer getRaiseEnddate01Diff() {
        return raiseEnddate01Diff;
    }

    public void setRaiseEnddate01Diff(Integer raiseEnddate01Diff) {
        this.raiseEnddate01Diff = raiseEnddate01Diff;

    }

    public Integer getTermRightFlagDiff() {
        return termRightFlagDiff;
    }

    public void setTermRightFlagDiff(Integer termRightFlagDiff) {
        this.termRightFlagDiff = termRightFlagDiff;

    }

    public Integer getCusRedeemFlagDiff() {
        return cusRedeemFlagDiff;
    }

    public void setCusRedeemFlagDiff(Integer cusRedeemFlagDiff) {
        this.cusRedeemFlagDiff = cusRedeemFlagDiff;

    }

    public Integer getCreditFlagDiff() {
        return creditFlagDiff;
    }

    public void setCreditFlagDiff(Integer creditFlagDiff) {
        this.creditFlagDiff = creditFlagDiff;

    }

    public Integer getInDepCodeDiff() {
        return inDepCodeDiff;
    }

    public void setInDepCodeDiff(Integer inDepCodeDiff) {
        this.inDepCodeDiff = inDepCodeDiff;

    }

    public Integer getOutDepCtryDiff() {
        return outDepCtryDiff;
    }

    public void setOutDepCtryDiff(Integer outDepCtryDiff) {
        this.outDepCtryDiff = outDepCtryDiff;

    }

    public Integer getOutDepNameDiff() {
        return outDepNameDiff;
    }

    public void setOutDepNameDiff(Integer outDepNameDiff) {
        this.outDepNameDiff = outDepNameDiff;

    }

    public Integer getVdate01Diff() {
        return vdate01Diff;
    }

    public void setVdate01Diff(Integer vdate01Diff) {
        this.vdate01Diff = vdate01Diff;

    }

    public Integer getMdate01Diff() {
        return mdate01Diff;
    }

    public void setMdate01Diff(Integer mdate01Diff) {
        this.mdate01Diff = mdate01Diff;

    }

    public Integer getFiduciaryDutyDiff() {
        return fiduciaryDutyDiff;
    }

    public void setFiduciaryDutyDiff(Integer fiduciaryDutyDiff) {
        this.fiduciaryDutyDiff = fiduciaryDutyDiff;

    }

    public Integer getIsConstracteDiff() {
        return isConstracteDiff;
    }

    public void setIsConstracteDiff(Integer isConstracteDiff) {
        this.isConstracteDiff = isConstracteDiff;

    }

    public Integer getTransferTypeDiff() {
        return transferTypeDiff;
    }

    public void setTransferTypeDiff(Integer transferTypeDiff) {
        this.transferTypeDiff = transferTypeDiff;

    }

    public Integer getTransferType1Diff() {
        return transferType1Diff;
    }

    public void setTransferType1Diff(Integer transferType1Diff) {
        this.transferType1Diff = transferType1Diff;

    }

    public Integer getTransferType2Diff() {
        return transferType2Diff;
    }

    public void setTransferType2Diff(Integer transferType2Diff) {
        this.transferType2Diff = transferType2Diff;

    }

    public Integer getTransferType3Diff() {
        return transferType3Diff;
    }

    public void setTransferType3Diff(Integer transferType3Diff) {
        this.transferType3Diff = transferType3Diff;

    }

    public Integer getRaiseCcyDiff() {
        return raiseCcyDiff;
    }

    public void setRaiseCcyDiff(Integer raiseCcyDiff) {
        this.raiseCcyDiff = raiseCcyDiff;

    }

    public Integer getCashPrinCcyDiff() {
        return cashPrinCcyDiff;
    }

    public void setCashPrinCcyDiff(Integer cashPrinCcyDiff) {
        this.cashPrinCcyDiff = cashPrinCcyDiff;

    }

    public Integer getCashProfCcyDiff() {
        return cashProfCcyDiff;
    }

    public void setCashProfCcyDiff(Integer cashProfCcyDiff) {
        this.cashProfCcyDiff = cashProfCcyDiff;

    }

    public Integer getCreditTypeDiff() {
        return creditTypeDiff;
    }

    public void setCreditTypeDiff(Integer creditTypeDiff) {
        this.creditTypeDiff = creditTypeDiff;

    }

    public Integer getCreditModeDiff() {
        return creditModeDiff;
    }

    public void setCreditModeDiff(Integer creditModeDiff) {
        this.creditModeDiff = creditModeDiff;
    }

    public String getInsideProdIdPrd() {
        return InsideProdIdPrd;
    }

    public void setInsideProdIdPrd(String insideProdIdPrd) {
        InsideProdIdPrd = insideProdIdPrd;
    }

    public Integer getInsideProdIdDiff() {
        return InsideProdIdDiff;
    }

    public void setInsideProdIdDiff(Integer insideProdIdDiff) {
        InsideProdIdDiff = insideProdIdDiff;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getVdate01() {
        return DateUtil.format(vdate,"yyyy-MM-dd");
    }
    public String getMdate01() {
        return DateUtil.format(mdate,"yyyy-MM-dd");
    }


    public String getRaiseVdate01Prd() {
        return DateUtil.format(raiseVdatePrd,"yyyy-MM-dd");
    }
    public String getRaiseEnddate01Prd() {
        return DateUtil.format(raiseEnddatePrd,"yyyy-MM-dd");
    }
    public String getVdate01Prd() {
        return DateUtil.format(vdatePrd,"yyyy-MM-dd");
    }
    public String getMdate01Prd() {
        return DateUtil.format(mdatePrd,"yyyy-MM-dd");
    }

    public String getIsMarketFundPrd() {
        return isMarketFundPrd;
    }

    public void setIsMarketFundPrd(String isMarketFundPrd) {
        this.isMarketFundPrd = isMarketFundPrd;
    }

    public String getPbcProdIdExternal() {
        return pbcProdIdExternal;
    }

    public void setPbcProdIdExternal(String pbcProdIdExternal) {
        this.pbcProdIdExternal = pbcProdIdExternal;
    }

    public Integer getCrossBorderFlagDiff() {
        return crossBorderFlagDiff;
    }

    public void setCrossBorderFlagDiff(Integer crossBorderFlagDiff) {
        this.crossBorderFlagDiff = crossBorderFlagDiff;
    }

    public String getCrossBorderFlagPrd() {
        return crossBorderFlagPrd;
    }

    public void setCrossBorderFlagPrd(String crossBorderFlagPrd) {
        this.crossBorderFlagPrd = crossBorderFlagPrd;
    }

    public String getCrossBorderFlag() {
        return crossBorderFlag;
    }

    public void setCrossBorderFlag(String crossBorderFlag) {
        this.crossBorderFlag = crossBorderFlag;
    }

    public String getZjxtFlagPrd() {
        return zjxtFlagPrd;
    }

    public void setZjxtFlagPrd(String zjxtFlagPrd) {
        this.zjxtFlagPrd = zjxtFlagPrd;
    }

    public String getZjxtFlag() {
        return zjxtFlag;
    }

    public void setZjxtFlag(String zjxtFlag) {
        this.zjxtFlag = zjxtFlag;
    }

    public Integer getZjxtFlagDiff() {
        return zjxtFlagDiff;
    }

    public void setZjxtFlagDiff(Integer zjxtFlagDiff) {
        this.zjxtFlagDiff = zjxtFlagDiff;
    }
}