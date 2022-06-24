package com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

/**
 * 描述:PBC_PRD_BASEINFO_SUB表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-06-05
 */
@TableName("PBC_PRD_BASEINFO_SUB")
public class PbcPrdBaseinfoSub extends BaseEntity{
    /**
     * 主键uuid
     */
    private String registSeqno;

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


    private String pbcPoolCode;

    private String prodProfitType;

    public String getProdProfitType() {
        return prodProfitType;
    }

    public void setProdProfitType(String prodProfitType) {
        this.prodProfitType = prodProfitType;
    }

    public String getPbcPoolCode() {
        return pbcPoolCode;
    }

    public void setPbcPoolCode(String pbcPoolCode) {
        this.pbcPoolCode = pbcPoolCode;
    }

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
     * 定义人行的枚举值
     * 是否现金管理类
     *
     */
    @DictFormat(DictCode.人行是否)
    private String  isMarketFund;

    /**
     * 以财产权信托名义开展的资金信托标识
     *
     */
    @DictFormat(DictCode.人行是否)
    private  String zjxtFlag;

    /**
     * 跨境理财通标识
     *
     */
    @DictFormat(DictCode.人行是否)
    private  String crossBorderFlag;

    @DictFormat(DictCode.人行是否)
    private  String cashManaCate;

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

	public String getRaiseVdate02() {
		return DateUtil.format(raiseVdate,"yyyy-MM-dd");
	}

	public void setRaiseVdate02(String raiseVdate02) {
		this.raiseVdate02 = raiseVdate02;
	}

	public String getRaiseEnddate02() {
		return DateUtil.format(raiseEnddate,"yyyy-MM-dd");
	}

	public void setRaiseEnddate02(String raiseEnddate02) {
		this.raiseEnddate02 = raiseEnddate02;
	}

	public String getVdate02() {
		return DateUtil.format(vdate,"yyyy-MM-dd");
	}

	public void setVdate02(String vdate02) {
		this.vdate02 = vdate02;
	}

	public String getMdate02() {
		return DateUtil.format(mdate,"yyyy-MM-dd");
	}

	public void setMdate02(String mdate02) {
		this.mdate02 = mdate02;
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
     * 产品类型，007-固定收益类/008-权益类/009-商品及金融衍生品类/010-混合类
     * @return IVSTORIENTATION 产品类型，007-固定收益类/008-权益类/009-商品及金融衍生品类/010-混合类
     */
    public String getIvstorientation() {
        return ivstorientation;
    }

    /**
     * 产品类型，007-固定收益类/008-权益类/009-商品及金融衍生品类/010-混合类
     * @param ivstorientation 产品类型，007-固定收益类/008-权益类/009-商品及金融衍生品类/010-混合类
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
    public String getRaiseVdate01() {
    	return DateUtil.format(raiseVdate,"yyyyMMdd");
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
    public String getRaiseEnddate01() {
    	return DateUtil.format(raiseEnddate,"yyyyMMdd");
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
    public String getVdate01() {
        return DateUtil.format(vdate,"yyyyMMdd");
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
    
    public String getMdate01() {
        return DateUtil.format(mdate,"yyyyMMdd");
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
        return DateUtil.format(submitTime,"yyyyMMdd");
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
    	return DateUtil.format(statusDate,"yyyyMMdd");
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
    	return DateUtil.format(pbcSubmitDeadline,"yyyyMMdd");
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

	public void setRaiseVdate01(String raiseVdate) {
		this.raiseVdate01 = raiseVdate;
	}

	public void setRaiseEnddate01(String raiseEnddate) {
		this.raiseEnddate01 = raiseEnddate;
	}

	public void setVdate01(String vdate) {
		this.vdate01 = vdate;
	}

	public void setMdate01(String mdate) {
		this.mdate01 = mdate;
	}

	public String getIssuePartyName() {
		return issuePartyName;
	}

	public void setIssuePartyName(String issuePartyName) {
		this.issuePartyName = issuePartyName;
	}

    /**
     * 产品期次（导出人行excel用）
     */
    private String prodStageToString;

    public String getProdStageToString() {
        return prodStageToString;
    }

    public void setProdStageToString(String prodStageToString) {
        this.prodStageToString = prodStageToString;
    }

    public String getCrossBorderFlag() {
        return crossBorderFlag;
    }

    public void setCrossBorderFlag(String crossBorderFlag) {
        this.crossBorderFlag = crossBorderFlag;
    }

    public String getZjxtFlag() {
        return zjxtFlag;
    }

    public void setZjxtFlag(String zjxtFlag) {
        this.zjxtFlag = zjxtFlag;
    }

    public String getCashManaCate() {
        return cashManaCate;
    }

    public void setCashManaCate(String cashManaCate) {
        this.cashManaCate = cashManaCate;
    }

    public String getStatusDateStr(){
        return null !=statusDate?DateUtil.formatDateTime(statusDate):null;
    }
    public String getSubmitTimeStr(){
        return null !=submitTime?DateUtil.formatDateTime(submitTime):null;
    }
    public String getPbcSubmitDeadlineStr(){
        return null!=pbcSubmitDeadline?DateUtil.format(pbcSubmitDeadline):null;
    }
}