package com.joyintech.tams.svreport.pbc.assetposition.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.busicommon.productRating.dto.CommonProductRatingDto;

/**
 * 描述:PBC_MONTH_ASSET_POSITION表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-03-11
 */
public class PbcMonthAssetPositionDto extends CommonProductRatingDto implements Cloneable{
    /**
     * 主键uuid
     */
    private String posuuid;

    /**
     * 产品代码
     */
    private String prodid;

    /**
     * 机构代码，产品所属的机构代码
     */
    private String orgCode;

    /**
     * 类型，枚举值：01 资产、02 负债、03 科目余额
     */
    private String positionType;

    /**
     * 通道代码
     */
    private String chlId;

    /**
     * 资产代码，回购、拆借存的是原始交易编号；类型=01和02该字段会有值，存的是公开市场代码，类型=03时存科目号
     */
    private String assetCode;

    /**
     * 资产一级分类，人行资产分类
     */
    private String assetType1;

    /**
     * 资产二级分类，人行资产分类
     */
    private String assetType2;

    /**
     * 资产三级分类，人行资产分类
     */
    private String assetType3;

    /**
     * 报表开始日期
     */
    private Date startdate;

    /**
     * 报表结束日期
     */
    private Date enddate;

    /**
     * 统计日期:数据生成日期
     */
    private Date bookdate;

    /**
     * 币种，如CNY等
     */
    private String ccy;

    /**
     * 金额:资产负债余额（成本法估值的资产存的是摊余成本总额，市值法估值的资产存的是市价净价总额）
     */
    private BigDecimal amt;
    /**
     * 本位币币种，如CNY等
     */
    private String bccy;

    /**
     * 本位币金额:资产负债余额（成本法估值的资产存的是摊余成本总额，市值法估值的资产存的是市价净价总额）
     */
    private BigDecimal bamt;
    /**
     * 原币币种，如CNY等
     */
    private String occy;

    /**
     * 原币金额:资产负债余额（成本法估值的资产存的是摊余成本总额，市值法估值的资产存的是市价净价总额）
     */
    private BigDecimal oamt;

    /**
     * 累计利息
     */
    private BigDecimal intAmt;

    /**
     * 剩余期限:0表示无固定期限
     */
    private BigDecimal endDays;

    /**
     * 剩余期限:0表示无固定期限
     */
    private BigDecimal endDaysOne;

    /**
     * 剩余期限:0表示无固定期限
     */
    private BigDecimal endDaysProd;

    /**
     * 剩余期限:0表示无固定期限
     */
    private BigDecimal endDaysNull;

    /**
     * 科目余额方向:类型=03时，字段存值，C：贷方，表示负债，D：借方，表示资产
     */
    private String balFlag;
    /**
     * 交易对手ID
     */
    private String counterid;
    /**
     * 资产到期日
     */
    private Date mdate;

    private Date assetMdate;
    /**
     * 境内、境外
     */
    private String blnarea;
    /**
     * 行业一级类型
     */
    private String industry;
    /**
     * 收受益权
     */
    private String repprofittype;
    /**
     * 产品集合 （查询用）
     */
    private List<String> prodids;	
    /**
     * 清算款
     */
    private BigDecimal delayPayAmt;
    /**
     * 资产池代码
     */
    private String assetPoolCode;
    /**
     * 是否需要映射数据
     */
    private boolean isNeedReflect;
    /**
     * 是否穿透 查询条件
     */
    private String isChlBtm;
    
    /**
     * 下一付费日
     */
    private Date payDate;

    /**
     * 逾期标识 01 逾期，02 未逾期
     */
    private String yqFlag;

    /**
     * 评级选择设置(01:推荐评级 02:最大评级 03:最小评级)
     */
    private String sysflag;

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
     * 评级日期结束
     */
    private String gradeDateEnd;

    /**
     * 评级
     */
    private String rating;

    /**
     * 商业银行优先股
     */
    private  String  bankSecPrefer;

    /**
     * 商业银行永续债
     */
    private  String   bankSecPerpetual;

    /**
     * 商业银行二级资本债
     */
    private  String  bankSecCapital;

    /**
     * 债券性质 01 债性 	02 股性
     */
    private  String  isPerpetual;

    /**
     * 市场机构机构规模
     */
    private String  partyType1;

    public String getPartyType1() {
        return partyType1;
    }

    public void setPartyType1(String partyType1) {
        this.partyType1 = partyType1;
    }

    public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
//  START add by wujian 20200317 多法人改造--单产品报表生成 区分部门


    public Date getAssetMdate() {
        return assetMdate;
    }

    public void setAssetMdate(Date assetMdate) {
        this.assetMdate = assetMdate;
    }

    private String institutionCode;
	    
    public String getInstitutionCode() {
		return institutionCode;
	}

	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	//  END add by wujian 20200317 多法人改造--单产品报表生成 区分部门


    private String bankCapital;

    private String sharesType;

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

    public String getIsChlBtm() {
		return isChlBtm;
	}

	public void setIsChlBtm(String isChlBtm) {
		this.isChlBtm = isChlBtm;
	}
    /**
     * 投资方向
     */
    private String investDirection;

    /**
     * 是否回购拆借 广州银行个性化:01 是/02 否
     */
    private String isRepmnm;

    
    /**  
	 * @Description TODO  
	 * @Author      高健  
	 * @Date        2019年12月18日 下午6:13:25  
	 * @return investDirection  
	 */
	
	public String getInvestDirection() {
		return investDirection;
	}

	/**  
	 * @Description TODO  
	 * @Author      高健  
	 * @Date        2019年12月18日 下午6:13:25  
	 * @param investDirection investDirection  
	 */
	
	public void setInvestDirection(String investDirection) {
		this.investDirection = investDirection;
	}
	
	
	//  START add by wujian 20200317 多法人改造--单产品报表生成 区分部门
	 /**
     * 创建部门 
     */
    private String createDept;
    
	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	//  END add by wujian 20200317 多法人改造--单产品报表生成 区分部门

	
	/**
     * 主键uuid
     * @return POSUUID 主键uuid
     */
    public String getPosuuid() {
        return posuuid;
    }

    /**
     * 主键uuid
     * @param posuuid 主键uuid
     */
    public void setPosuuid(String posuuid) {
        this.posuuid = posuuid == null ? null : posuuid.trim();
    }

    /**
     * 产品代码
     * @return PRODID 产品代码
     */
    public String getProdid() {
        return prodid;
    }

    /**
     * 产品代码
     * @param prodid 产品代码
     */
    public void setProdid(String prodid) {
        this.prodid = prodid == null ? null : prodid.trim();
    }

    /**
     * 机构代码，产品所属的机构代码
     * @return ORG_CODE 机构代码，产品所属的机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码，产品所属的机构代码
     * @param orgCode 机构代码，产品所属的机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 类型，枚举值：01 资产、02 负债、03 科目余额
     * @return POSITION_TYPE 类型，枚举值：01 资产、02 负债、03 科目余额
     */
    public String getPositionType() {
        return positionType;
    }

    /**
     * 类型，枚举值：01 资产、02 负债、03 科目余额
     * @param positionType 类型，枚举值：01 资产、02 负债、03 科目余额
     */
    public void setPositionType(String positionType) {
        this.positionType = positionType == null ? null : positionType.trim();
    }

    /**
     * 通道代码
     * @return CHL_ID 通道代码
     */
    public String getChlId() {
        return chlId;
    }

    /**
     * 通道代码
     * @param chlId 通道代码
     */
    public void setChlId(String chlId) {
        this.chlId = chlId == null ? null : chlId.trim();
    }

    /**
     * 资产代码，回购、拆借存的是原始交易编号；类型=01和02该字段会有值，存的是公开市场代码，类型=03时存科目号
     * @return ASSET_CODE 资产代码，回购、拆借存的是原始交易编号；类型=01和02该字段会有值，存的是公开市场代码，类型=03时存科目号
     */
    public String getAssetCode() {
        return assetCode;
    }

    /**
     * 资产代码，回购、拆借存的是原始交易编号；类型=01和02该字段会有值，存的是公开市场代码，类型=03时存科目号
     * @param assetCode 资产代码，回购、拆借存的是原始交易编号；类型=01和02该字段会有值，存的是公开市场代码，类型=03时存科目号
     */
    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode == null ? null : assetCode.trim();
    }

    /**
     * 资产一级分类，人行资产分类
     * @return ASSET_TYPE1 资产一级分类，人行资产分类
     */
    public String getAssetType1() {
        return assetType1;
    }

    /**
     * 资产一级分类，人行资产分类
     * @param assetType1 资产一级分类，人行资产分类
     */
    public void setAssetType1(String assetType1) {
        this.assetType1 = assetType1 == null ? null : assetType1.trim();
    }

    /**
     * 资产二级分类，人行资产分类
     * @return ASSET_TYPE2 资产二级分类，人行资产分类
     */
    public String getAssetType2() {
        return assetType2;
    }

    /**
     * 资产二级分类，人行资产分类
     * @param assetType2 资产二级分类，人行资产分类
     */
    public void setAssetType2(String assetType2) {
        this.assetType2 = assetType2 == null ? null : assetType2.trim();
    }

    /**
     * 资产三级分类，人行资产分类
     * @return ASSET_TYPE3 资产三级分类，人行资产分类
     */
    public String getAssetType3() {
        return assetType3;
    }

    /**
     * 资产三级分类，人行资产分类
     * @param assetType3 资产三级分类，人行资产分类
     */
    public void setAssetType3(String assetType3) {
        this.assetType3 = assetType3 == null ? null : assetType3.trim();
    }

    /**
     * 报表开始日期
     * @return STARTDATE 报表开始日期
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * 报表开始日期
     * @param startdate 报表开始日期
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * 报表结束日期
     * @return ENDDATE 报表结束日期
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * 报表结束日期
     * @param enddate 报表结束日期
     */
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * 统计日期:数据生成日期
     * @return BOOKDATE 统计日期:数据生成日期
     */
    public Date getBookdate() {
        return bookdate;
    }

    /**
     * 统计日期:数据生成日期
     * @param bookdate 统计日期:数据生成日期
     */
    public void setBookdate(Date bookdate) {
        this.bookdate = bookdate;
    }

    /**
     * 币种，如CNY等
     * @return CCY 币种，如CNY等
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * 币种，如CNY等
     * @param ccy 币种，如CNY等
     */
    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }

    /**
     * 金额:资产负债余额（成本法估值的资产存的是摊余成本总额，市值法估值的资产存的是市价净价总额）
     * @return AMT 金额:资产负债余额（成本法估值的资产存的是摊余成本总额，市值法估值的资产存的是市价净价总额）
     */
    public BigDecimal getAmt() {
        return amt==null?BigDecimal.ZERO:amt;
    }

    /**
     * 金额:资产负债余额（成本法估值的资产存的是摊余成本总额，市值法估值的资产存的是市价净价总额）
     * @param amt 金额:资产负债余额（成本法估值的资产存的是摊余成本总额，市值法估值的资产存的是市价净价总额）
     */
    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    /**
     * 累计利息
     * @return INT_AMT 累计利息
     */
    public BigDecimal getIntAmt() {
        return intAmt==null?BigDecimal.ZERO:intAmt;
    }

    /**
     * 累计利息
     * @param intAmt 累计利息
     */
    public void setIntAmt(BigDecimal intAmt) {
        this.intAmt = intAmt;
    }

    /**
     * 剩余期限:0表示无固定期限
     * @return END_DAYS 剩余期限:0表示无固定期限
     */
    public BigDecimal getEndDays() {
        return endDays;
    }

    /**
     * 剩余期限:0表示无固定期限
     * @param endDays 剩余期限:0表示无固定期限
     */
    public void setEndDays(BigDecimal endDays) {
        this.endDays = endDays;
    }

    /**
     * 科目余额方向:类型=03时，字段存值，C：贷方，表示负债，D：借方，表示资产
     * @return BAL_FLAG 科目余额方向:类型=03时，字段存值，C：贷方，表示负债，D：借方，表示资产
     */
    public String getBalFlag() {
        return balFlag;
    }

    /**
     * 科目余额方向:类型=03时，字段存值，C：贷方，表示负债，D：借方，表示资产
     * @param balFlag 科目余额方向:类型=03时，字段存值，C：贷方，表示负债，D：借方，表示资产
     */
    public void setBalFlag(String balFlag) {
        this.balFlag = balFlag == null ? null : balFlag.trim();
    }

	public BigDecimal getEndDaysOne() {
		return endDaysOne;
	}

	public void setEndDaysOne(BigDecimal endDaysOne) {
		this.endDaysOne = endDaysOne;
	}

	public BigDecimal getEndDaysProd() {
		return endDaysProd;
	}

	public void setEndDaysProd(BigDecimal endDaysProd) {
		this.endDaysProd = endDaysProd;
	}

	public BigDecimal getEndDaysNull() {
		return endDaysNull;
	}

	public void setEndDaysNull(BigDecimal endDaysNull) {
		this.endDaysNull = endDaysNull;
	}

	public String getCounterid() {
		return counterid;
	}

	public void setCounterid(String counterid) {
		this.counterid = counterid;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public List<String> getProdids() {
		return prodids;
	}

	public void setProdids(List<String> prodids) {
		this.prodids = prodids;
	}

	public String getBlnarea() {
		return blnarea;
	}

	public void setBlnarea(String blnarea) {
		this.blnarea = blnarea;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getRepprofittype() {
		return repprofittype;
	}

	public void setRepprofittype(String repprofittype) {
		this.repprofittype = repprofittype;
	}

    public BigDecimal getDelayPayAmt() {
        return delayPayAmt;
    }

    public void setDelayPayAmt(BigDecimal delayPayAmt) {
        this.delayPayAmt = delayPayAmt;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getAssetPoolCode() {
        return assetPoolCode;
    }

    public void setAssetPoolCode(String assetPoolCode) {
        this.assetPoolCode = assetPoolCode;
    }

    /**
     * 产品代码+机构代码
     * @return  品代码+机构代码
     */
    public String getProdidAndOrgCode() {
        return prodid + GlobalConstant.VERTICAL_LINE + orgCode;
    }

    /**
     * 人行产品代码+机构代码
     * @return  品代码+机构代码
     */
    public String getPbcProdidAndOrgCode() {
        return assetPoolCode + GlobalConstant.VERTICAL_LINE + orgCode;
    }

    public boolean isNeedReflect() {
        return isNeedReflect;
    }

    public void setNeedReflect(boolean isNeedReflect) {
        this.isNeedReflect = isNeedReflect;
    }

	public String getBccy() {
		return bccy;
	}

	public void setBccy(String bccy) {
		this.bccy = bccy;
	}

	public BigDecimal getBamt() {
		return bamt;
	}

	public void setBamt(BigDecimal bamt) {
		this.bamt = bamt;
	}

	public String getOccy() {
		return occy;
	}

	public void setOccy(String occy) {
		this.occy = occy;
	}

	public BigDecimal getOamt() {
		return oamt;
	}

	public void setOamt(BigDecimal oamt) {
		this.oamt = oamt;
	}

    public String getYqFlag() {
        return yqFlag;
    }

    public void setYqFlag(String yqFlag) {
        this.yqFlag = yqFlag;
    }

    public String getPrincipalRatingDimension() {
        return principalRatingDimension;
    }

    public void setPrincipalRatingDimension(String principalRatingDimension) {
        this.principalRatingDimension = principalRatingDimension;
    }

    public String getSysflag() {
        return sysflag;
    }

    public void setSysflag(String sysflag) {
        this.sysflag = sysflag;
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

    public String getGradeDateEnd() {
        return gradeDateEnd;
    }

    public void setGradeDateEnd(String gradeDateEnd) {
        this.gradeDateEnd = gradeDateEnd;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBankSecPrefer() {
        return bankSecPrefer;
    }

    public void setBankSecPrefer(String bankSecPrefer) {
        this.bankSecPrefer = bankSecPrefer;
    }

    public String getBankSecPerpetual() {
        return bankSecPerpetual;
    }

    public void setBankSecPerpetual(String bankSecPerpetual) {
        this.bankSecPerpetual = bankSecPerpetual;
    }

    public String getBankSecCapital() {
        return bankSecCapital;
    }

    public void setBankSecCapital(String bankSecCapital) {
        this.bankSecCapital = bankSecCapital;
    }

    public String getIsPerpetual() {
        return isPerpetual;
    }

    public void setIsPerpetual(String isPerpetual) {
        this.isPerpetual = isPerpetual;
    }

    public String getIsRepmnm() {
        return isRepmnm;
    }

    public void setIsRepmnm(String isRepmnm) {
        this.isRepmnm = isRepmnm;
    }
}