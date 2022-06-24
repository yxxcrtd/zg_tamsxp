package com.joyintech.tams.svreport.chb.monthreport.dto;

import com.joyintech.tams.busicommon.productRating.dto.CommonProductRatingDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述:CHB_MONTH_ASSET_POSITION表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-26
 */
public class ChbMonthAssetPosition extends CommonProductRatingDto implements Cloneable {
    /**
     * 头寸编号
     */
    private String posuuid;

    /**
     * 统计日期
     */
    private Date bookdate;

    /**
     * 开始日期
     */
    private Date startdate;

    /**
     * 结束日期
     */
    private Date enddate;

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
     * 资产二级分类
     */
    private String assettype2;

    /**
     * 产品代码
     */
    private String prodId;

    /**
     * 穿透前资产价值
     */
    private BigDecimal assetvalueA;

    /**
     * 穿透后资产价值
     */
    private BigDecimal assetvalueB;

    /**
     * 所属行业
     */
    private String industry1;

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
     * 评级结果
     */
    private String rating;

    /**
     * 是否底层交易，用作传入参数,1-是,0-否
     */
     private String isUnderDeal;
     
     /**
      * 通道标示
      */
     private String chlId;
     
     /**
      * 交易类型：
      * 01-产品直投交易
      * 02-产品购买通道交易
      * 03-产品通过通道购买底层资产交易
      * 04-产品通过通道买通道交易，通道嵌套
      * 05-产品通过通道购买资管计划
      * 06-产品购买资管计划
      */
     private String tradeType;

     private String bankCapital;

     private String sharesType;
     
     /**
      * 查询类型:01-直投,02-通道,03-资管计划
      */
     private String queryType;
     /**
      * 币种（查询剩余现金用）
      */
     private String ccy;
     /**
      * 剩余现金（资产端1.1.1）
      */
     private BigDecimal cashamt;
     /**
      * 评级选择设置(01:推荐评级 02:最大评级 03:最小评级)
      */
     private String sysflag;
     /**
      * 资产二级类型细分类
      */
     private String assetType3;

    /**
     * 主体评级维度
     */
    private String principalRatingDimension;

    /**
     * 调用共通评级接口需要的债项排序sql
     */
    private String getProductRatingOrderSqlByZx;

    /**
     * 调用共通评级接口需要的主体排序sql
     */
    private String getProductRatingOrderSqlByZt;
    
    /**
     * 穿透后资产价值取值控制
     */
    private String monthReport;
    /**
     * 穿透前资产代码（通道代码、资管计划代码）
     */
    private String oldAssetCode;
    
    /**
     * 商业银行二级资本债
     */
    private  String  bankSecCapital;
    
    /**
     * 商业银行永续债
     */
    private  String   bankSecPerpetual;
    
    /**
     * 商业银行优先股
     */
    private  String  bankSecPrefer;
    private List<String> prodids;

    public List<String> getProdids() {
        return prodids;
    }

    public void setProdids(List<String> prodids) {
        this.prodids = prodids;
    }

    /**
     * 是否合规新产品
     * @return
     */
    private   String   isFhxgcp;

    /**
     * 是否商业银行可转债
     */
    private String bankSecKzzq;

    /**
     * 是否专项债券
     */
    private String bankSecZxzq;

    /**
     * 是否未上市企业-政府出资产业投资基金
     */
    private String isunlisted;

    /**
     * 是否标准化票据
     */
    private String isBzhpj;

    private  String  isFbzb;

    /**
     * 转型表资产一级分类
     */
    private String sunassettype;

    /**
     * 转型表资产二级分类
     */
    private String sunassettype2;

    /**
     * 剩余期限
     */
    private BigDecimal terms;


    /**
     * 资管计划科目
     */
    private String subCode;


    /**
     * 是否母行产品
     */
    private String isFprod;

    public String getIsFprod() {
        return isFprod;
    }

    public void setIsFprod(String isFprod) {
        this.isFprod = isFprod;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getIsFbzb() {
        return isFbzb;
    }

    public void setIsFbzb(String isFbzb) {
        this.isFbzb = isFbzb;
    }

    public String getBankSecKzzq() {
        return bankSecKzzq;
    }

    public void setBankSecKzzq(String bankSecKzzq) {
        this.bankSecKzzq = bankSecKzzq;
    }

    public String getBankSecZxzq() {
        return bankSecZxzq;
    }

    public void setBankSecZxzq(String bankSecZxzq) {
        this.bankSecZxzq = bankSecZxzq;
    }

    public String getIsunlisted() {
        return isunlisted;
    }

    public void setIsunlisted(String isunlisted) {
        this.isunlisted = isunlisted;
    }

    public String getIsBzhpj() {
        return isBzhpj;
    }

    public void setIsBzhpj(String isBzhpj) {
        this.isBzhpj = isBzhpj;
    }

    public String getIsFhxgcp() {
        return isFhxgcp;
    }

    public void setIsFhxgcp(String isFhxgcp) {
        this.isFhxgcp = isFhxgcp;
    }





    public String getOldAssetCode() {
		return oldAssetCode;
	}

	public void setOldAssetCode(String oldAssetCode) {
		this.oldAssetCode = oldAssetCode;
	}

	public String getSysflag() {
		return sysflag;
	}

	public void setSysflag(String sysflag) {
		this.sysflag = sysflag;
	}

	/**
     * 头寸编号
     * @return POSUUID 头寸编号
     */
    public String getPosuuid() {
        return posuuid;
    }

    /**
     * 头寸编号
     * @param posuuid 头寸编号
     */
    public void setPosuuid(String posuuid) {
        this.posuuid = posuuid == null ? null : posuuid.trim();
    }

    /**
     * 统计日期
     * @return BOOKDATE 统计日期
     */
    public Date getBookdate() {
        return bookdate;
    }

    /**
     * 统计日期
     * @param bookdate 统计日期
     */
    public void setBookdate(Date bookdate) {
        this.bookdate = bookdate;
    }

    /**
     * 开始日期
     * @return STARTDATE 开始日期
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * 开始日期
     * @param startdate 开始日期
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * 结束日期
     * @return ENDDATE 结束日期
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * 结束日期
     * @param enddate 结束日期
     */
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * 资产类型
     * @return INVEST_VARIETY 资产类型
     */
    public String getInvestVariety() {
        return investVariety;
    }

    /**
     * 资产类型
     * @param investVariety 资产类型
     */
    public void setInvestVariety(String investVariety) {
        this.investVariety = investVariety == null ? null : investVariety.trim();
    }

    /**
     * 资产ID
     * @return ASSET_CODE 资产ID
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 资产ID
     * @param assetCode 资产ID
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 资产属性，统计分类
     * @return ASSETTYPE 资产属性，统计分类
     */
    public String getAssettype() {
        return assettype;
    }

    /**
     * 资产属性，统计分类
     * @param assettype 资产属性，统计分类
     */
    public void setAssettype(String assettype) {
        this.assettype = assettype == null ? null : assettype.trim();
    }

    /**
     * 产品代码
     * @return PROD_ID 产品代码
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 产品代码
     * @param prodId 产品代码
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 穿透前资产价值
     * @return ASSETVALUE_A 穿透前资产价值
     */
    public BigDecimal getAssetvalueA() {
        return assetvalueA;
    }

    /**
     * 穿透前资产价值
     * @param assetvalueA 穿透前资产价值
     */
    public void setAssetvalueA(BigDecimal assetvalueA) {
        this.assetvalueA = assetvalueA;
    }

    /**
     * 穿透后资产价值
     * @return ASSETVALUE_B 穿透后资产价值
     */
    public BigDecimal getAssetvalueB() {
        return assetvalueB;
    }

    /**
     * 穿透后资产价值
     * @param assetvalueB 穿透后资产价值
     */
    public void setAssetvalueB(BigDecimal assetvalueB) {
        this.assetvalueB = assetvalueB;
    }

    /**
     * 所属行业
     * @return INDUSTRY1 所属行业
     */
    public String getIndustry1() {
        return industry1;
    }

    /**
     * 所属行业
     * @param industry1 所属行业
     */
    public void setIndustry1(String industry1) {
        this.industry1 = industry1 == null ? null : industry1.trim();
    }

    /**
     * 行类一级分类
     * @return IN_ASSET_TYPE 行类一级分类
     */
    public String getInAssetType() {
        return inAssetType;
    }

    /**
     * 行类一级分类
     * @param inAssetType 行类一级分类
     */
    public void setInAssetType(String inAssetType) {
        this.inAssetType = inAssetType == null ? null : inAssetType.trim();
    }

    /**
     * 公共PPP
     * @return PPPFLAG 公共PPP
     */
    public String getPppflag() {
        return pppflag;
    }

    /**
     * 公共PPP
     * @param pppflag 公共PPP
     */
    public void setPppflag(String pppflag) {
        this.pppflag = pppflag == null ? null : pppflag.trim();
    }

    /**
     * 是否投向地方政府平台
     * @return MONIT_TYPE 是否投向地方政府平台
     */
    public String getMonitType() {
        return monitType;
    }

    /**
     * 是否投向地方政府平台
     * @param monitType 是否投向地方政府平台
     */
    public void setMonitType(String monitType) {
        this.monitType = monitType == null ? null : monitType.trim();
    }

    /**
     * 管理方式
     * @return MANAGE_TYPE 管理方式
     */
    public String getManageType() {
        return manageType;
    }

    /**
     * 管理方式
     * @param manageType 管理方式
     */
    public void setManageType(String manageType) {
        this.manageType = manageType == null ? null : manageType.trim();
    }

    /**
     * 是否政府投资基金
     * @return IS_GOVERN_FUND1 是否政府投资基金
     */
    public String getIsGovernFund1() {
        return isGovernFund1;
    }

    /**
     * 是否政府投资基金
     * @param isGovernFund1 是否政府投资基金
     */
    public void setIsGovernFund1(String isGovernFund1) {
        this.isGovernFund1 = isGovernFund1 == null ? null : isGovernFund1.trim();
    }

    /**
     * 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     * @return PRIVATEFUND_FLAG 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    public String getPrivatefundFlag() {
        return privatefundFlag;
    }

    /**
     * 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     * @param privatefundFlag 是否投向投向“金融资产投资公司或其附属机构发行的私募股权投资基金
     */
    public void setPrivatefundFlag(String privatefundFlag) {
        this.privatefundFlag = privatefundFlag == null ? null : privatefundFlag.trim();
    }

    /**
     * 是否投向金融资产投资公司发行的私募资产管理产品
     * @return PRIVATEPRD_FLAG 是否投向金融资产投资公司发行的私募资产管理产品
     */
    public String getPrivateprdFlag() {
        return privateprdFlag;
    }

    /**
     * 是否投向金融资产投资公司发行的私募资产管理产品
     * @param privateprdFlag 是否投向金融资产投资公司发行的私募资产管理产品
     */
    public void setPrivateprdFlag(String privateprdFlag) {
        this.privateprdFlag = privateprdFlag == null ? null : privateprdFlag.trim();
    }

    /**
     * 是否投向市场化债转股相关产品的部分
     * @return BONDTOSTOCK_FLAG 是否投向市场化债转股相关产品的部分
     */
    public String getBondtostockFlag() {
        return bondtostockFlag;
    }

    /**
     * 是否投向市场化债转股相关产品的部分
     * @param bondtostockFlag 是否投向市场化债转股相关产品的部分
     */
    public void setBondtostockFlag(String bondtostockFlag) {
        this.bondtostockFlag = bondtostockFlag == null ? null : bondtostockFlag.trim();
    }

    /**
     * 评级结果
     * @return RATING 评级结果
     */
    public String getRating() {
        return rating;
    }

    /**
     * 评级结果
     * @param rating 评级结果
     */
    public void setRating(String rating) {
        this.rating = rating == null ? null : rating.trim();
    }

    public String getAssettype2() {
        return assettype2;
    }
    
    public void setAssettype2(String assettype2) {
        this.assettype2 = assettype2;
    }
    
    public String getIsUnderDeal() {
        return isUnderDeal;
    }
    
    public void setIsUnderDeal(String isUnderDeal) {
        this.isUnderDeal = isUnderDeal;
    }
    
    public String getChlId() {
        return chlId;
    }
    
    public void setChlId(String chlId) {
        this.chlId = chlId;
    }
    
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }
    
    public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getCashamt() {
		return cashamt;
	}

	public void setCashamt(BigDecimal cashamt) {
		this.cashamt = cashamt;
	}

	public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public String getAssetType3() {
        return assetType3;
    }

    public void setAssetType3(String assetType3) {
        this.assetType3 = assetType3;
    }

    public String getPrincipalRatingDimension() {
        return principalRatingDimension;
    }

    public void setPrincipalRatingDimension(String principalRatingDimension) {
        this.principalRatingDimension = principalRatingDimension;
    }

    public String getGetProductRatingOrderSqlByZx() {
        return getProductRatingOrderSqlByZx;
    }

    public void setGetProductRatingOrderSqlByZx(String getProductRatingOrderSqlByZx) {
        this.getProductRatingOrderSqlByZx = getProductRatingOrderSqlByZx;
    }

    public String getGetProductRatingOrderSqlByZt() {
        return getProductRatingOrderSqlByZt;
    }

    public void setGetProductRatingOrderSqlByZt(String getProductRatingOrderSqlByZt) {
        this.getProductRatingOrderSqlByZt = getProductRatingOrderSqlByZt;
    }

	public String getBankSecCapital() {
		return bankSecCapital;
	}

	public void setBankSecCapital(String bankSecCapital) {
		this.bankSecCapital = bankSecCapital;
	}

	public String getBankSecPerpetual() {
		return bankSecPerpetual;
	}

	public void setBankSecPerpetual(String bankSecPerpetual) {
		this.bankSecPerpetual = bankSecPerpetual;
	}

	public String getBankSecPrefer() {
		return bankSecPrefer;
	}

	public void setBankSecPrefer(String bankSecPrefer) {
		this.bankSecPrefer = bankSecPrefer;
	}

    public String getMonthReport() {
        return monthReport;
    }

    public void setMonthReport(String monthReport) {
        this.monthReport = monthReport;
    }

    public String getSunassettype() {
        return sunassettype;
    }

    public void setSunassettype(String sunassettype) {
        this.sunassettype = sunassettype;
    }

    public String getSunassettype2() {
        return sunassettype2;
    }

    public void setSunassettype2(String sunassettype2) {
        this.sunassettype2 = sunassettype2;
    }

    public BigDecimal getTerms() {
        return terms;
    }

    public void setTerms(BigDecimal terms) {
        this.terms = terms;
    }

    public String getBankCapital() {
        return bankCapital;
    }

    public void setBankCapital(String bankCapital) {
        this.bankCapital = bankCapital;
    }

    public String getSharesType() {
        return sharesType;
    }

    public void setSharesType(String sharesType) {
        this.sharesType = sharesType;
    }
}