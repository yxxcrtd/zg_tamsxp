package com.joyintech.tams.report.interf.common.constant;

public final class InterfMappingConstants {
	/**
	 * 资产二级分类映射中债系统数据类型
	 */
	public static final String MAAPING_ASSETTYPETWO_CHB = "CHB_ASSETTYPE_TWO_CHB";
	/**
	 * 资产二级分类映射源数据类型
	 */
	public static final String MAAPING_ASSETTYPETWO_ZG = "CHB_ASSETTYPE_TWO_ZG";
	/**
	 * 客户组别映射（客户组别与客户二级类型对应关系-阜新银行）
	 */
	public static final String MAAPING_CUSTOMER_GROUP_ID = "CUSTOMER_GROUP_ID";
	/** 客户组别映射（境外客户组别与客户二级类型对应关系-阜新银行）
	 */
	public static final String MAAPING_CUSTOMER_GROUP_ID_JW = "CUSTOMER_GROUP_ID_JW";
	/**
	 * 币种映射（币种映射-阜新银行）
	 */
	public static final String MAAPING_CCY = "XS_CCY";
	/**
	 * 有无标识映射
	 */
	public static final String MAAPING_HAVE_OR_NOT = "CHB_HAVE_OR_NOT";
	/**
	 * CHB_PROD_DEAL 购买地映射中债系统数据类型
	 */
	public static final String CHB_PROD_DEAL_BUYPLACE = "CHB_PROD_DEAL_BUYPLACE";
    /**
     * 开放周期映射源数据类型
     */
    public static final String MAAPING_OPEN_CYCLE_UNIT_ZG = "CHB_OPEN_CYCLE_UNIT_ZG";
    /**
    * BS2.0对人行资产类型映射
    */
   public static final String MAPPINGCODE_PBCASSETTYPE = "MAPPINGCODE_PBCASSETTYPE";
   /**
    * BS2.0对人行资产类型映射-融资人/发行人属性
    */
   public static final String PBCASSETTYPE_FAIN = "PBCASSETTYPE_FAIN";
   /**
    * BS2.0对人行资产类型映射-收受益权标识
    */
   public static final String PBCASSETTYPE_REPP = "PBCASSETTYPE_REPP";
   /**
    * BS2.0对人行资产类型映射-交易对手二级分类
    */
   public static final String PBCASSETTYPE_COUNTER = "PBCASSETTYPE_COUNTER";



	/**
	 * BS2.0对人行资产类型映射-交易对手二级分类
	 */
	public static final String PBCASSETTYPE_DSFLX = "PBCASSETTYPE_DSFLX";

   /**
    * BS2.0对人行资产类型映射-是否中央交易对手
    */
   public static final String PBCASSETTYPE_CENTER = "PBCASSETTYPE_CENTER";
   /**
   * BS2.0对人行资产类型映射-境内\境外
   */
  public static final String PBCASSETTYPE_COUNTRY = "PBCASSETTYPE_COUNTRY";
  /**
   * 剩余现金科目配置市值法
   */
  public static final String MAPPINGCODE_CASH_SUBJECTNO_MKT="CASH_SUBJECTNO_MKT";
  /**
   * 剩余现金科目配置移动平均法
   */
  public static final String MAPPINGCODE_CASH_SUBJECTNO_AVG="CASH_SUBJECTNO_AVG";
  /**
   * 交易对手二级类型  非银行业金融机构
   */
  public static final String BS_ASSETTYPE3_C0402="C0402";

	// 理财子新增 新增理财子公司月报资产头寸字段设置 start
	/**
	 * 对理财子公司资产类型映射关系KEY
	 */
	public static final String MAPPINGCODE_SUBASSETTYPE = "MAPPINGCODE_SUBASSETTYPE";
	/**
	 * 理财子公司资产类型映射关系-商业银行二级资本债
	 */
	public static final String SUBASSETTYPE_SECONDBOND = "SUBASSETTYPE_SECONDBOND";
	/**
	 * 理财子公司资产类型映射关系-商业银行永续债
	 */
	public static final String SUBASSETTYPE_CAPSEC = "SUBASSETTYPE_CAPSEC";
	/**
	 * 理财子公司资产类型映射关系-资产四级分类
	 */
	public static final String SUBASSETTYPE_ASSET = "SUBASSETTYPE_ASSET";
	/**
	 * 理财子公司资产类型映射关系-首层资产二级类型
	 */
	public static final String SUBASSETTYPE_SECONDASSET = "SUBASSETTYPE_SECONDASSET";

	/**
	 * 商业银行优先股
	 */
	public static final String SUBASSETTYPE_ORSTOCK = "SUBASSETTYPE_ORSTOCK";
	// 理财子新增 新增理财子公司月报资产头寸字段设置 end

	/**
	 * 资产持仓登记直投科目体系
	 */
	public static final String SUBJECT_INVEST_TYPE_NAME = "SUBJECT_INVEST_TYPE_NAME";
	/**
	 * 资产持仓登记底层科目体系
	 */
	public static final String BOTTON_SUBJECT_INVEST_TYPE_NAME = "BOTTON_SUBJECT_INVEST_TYPE_NAME";
	/**
	 * 资产类型和品种映射配置
	 */
	public static final String ASSET_TYPE2_CATA_TYPE = "ASSET_TYPE2_CATA_TYPE";
}
