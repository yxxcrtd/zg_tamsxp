package com.joyintech.tams.common.svreport.chb.constant;

/**
 * 中债子系统用GlobalConstant
 *
 * @author 刘二通
 * @version 1.0 2018年5月10日
 * @see SVReportGlobalConstant
 * @since 1.0
 */
public class SVReportGlobalConstant {

//    /**
//     * 共通Property属性--DB类型
//     */
//    public static final String COM_PROP_DATABASE_TYPE = "DATABASE_TYPE";

    /**
     * 中债报送单文件最大数据件数
     */
    public static final String SYS_PARA_SENDCB_MAX_COUNT = "SEND_CB_MAX_DATA_COUNT";

    /**
     * 持有信息每批次处理数据条数
     */
    public static final String SYS_PARA_SEND_CB_DATA_COUNT_PER_TIME = "SEND_CB_DATA_COUNT_PER_TIME";

    /**
     * 报送检查出现警告信息时是否继续报送（01：是，02：否）
     */
    public static final String SYS_PARA_IS_SEND_WHEN_WARN = "IS_SEND_WHEN_WARN";

    /**
     * 中债子系统接口是否导入开关
     */
    public static final String DICT_INTERF_IMPORT_SWITCH = "INTERF_IMPORT_SWITCH";

    /**
     * FTP类型（01：FTP，02：SFTP，03：本地文件）
     */
    public static final String SYS_PARA_FTP_TYPE = "FTP_TYPE";

    /**
     * 销售系统接口FTP地址
     */
    public static final String SYS_PARA_SALES_FTP_IP = "SALES_FTP_IP";

    /**
     * 销售系统接口FTP端口
     */
    public static final String SYS_PARA_SALES_FTP_PROT = "SALES_FTP_PROT";

    /**
     * 销售系统接口FTP编码
     */
    public static final String SYS_PARA_SALES_FTP_ENCODE = "SALES_FTP_ENCODE";

    /**
     * 销售系统接口FTP用户名
     */
    public static final String SYS_PARA_SALES_FTP_USER = "SALES_FTP_USER";

    /**
     * 销售系统接口FTP密码
     */
    public static final String SYS_PARA_SALES_FTP_PASSWORD = "SALES_FTP_PASSWORD";

    /**
     * 销售系统接口文件编码
     */
    public static final String SYS_PARA_SALES_FILE_ENCODE = "SALES_FILE_ENCODE";

    /**
     * 持有信息数据来源：01：业务明细、02：持有信息
     */
    public static final String SYS_PARA_HOLD_INFO_SOURCE = "HOLD_INFO_SOURCE";

    /**
     * 是否过滤部门权限  01 过滤   02 不过滤
     */
    public static final String SYS_PARA_IS_DEPTCODE_CONTROL = "IS_DEPTCODE_CONTROL";

    /**
     * 颜色mid
     */
    public static final String SYS_PARA_COLOR_CONFIG_MID = "COLOR_CONFIG_mid";

    /**
     * 颜色top
     */
    public static final String SYS_PARA_COLOR_CONFIG_TOP = "COLOR_CONFIG_top";

    /**
     * db2数据库服务器FTP地址
     */
    public static final String SYS_PARA_REP_DB2_SERVER_FTP_IP = "REP_DB2_SERVER_FTP_IP";

    /**
     * db2数据库服务器FTP用户名
     */
    public static final String SYS_PARA_REP_DB2_SERVER_FTP_USER = "REP_DB2_SERVER_FTP_USER";

    /**
     * db2数据库服务器FTP密码
     */
    public static final String SYS_PARA_REP_DB2_SERVER_FTP_PASSWORD = "REP_DB2_SERVER_FTP_PASSWORD";

    /**
     * db2数据库服务器FTP密码
     */
    public static final String SYS_PARA_REP_DB2_SERVER_FTP_PROT = "REP_DB2_SERVER_FTP_PROT";

    /**
     * db2数据库服务器FTP文件路径
     */
    public static final String SYS_PARA_REP_DB2_SERVER_FTP_FILEPATH = "REP_DB2_SERVER_FTP_FILEPATH";

    /**
     * 报送中债出现超时时是否抛异常回滚（01:是,02:否）
     */
    public static final String SYS_PARA_REP_IS_EXCEPTION_WHEN_SENDCB_TIMEOUT = "REP_IS_EXCEPTION_WHEN_SENDCB_TIMEOUT";

    /**
     * 调用中债API获取反馈消息开始时间
     */
    public static final String SYS_PARA_REP_CB_GETMESSAGE_STARTTIME = "REP_CB_GETMESSAGE_STARTTIME";

    /**
     * 调用中债API获取反馈消息结束时间
     */
    public static final String SYS_PARA_REP_CB_GETMESSAGE_ENDTIME = "REP_CB_GETMESSAGE_ENDTIME";

    /**
     * 中债报送自检规则数据缓存key
     */
    public static final String SUBCHECK_BASICRULE_CACHE_KEY = "subCheckBasicRuleData";

    /**
     * 中债报送项目自检规则明细数据缓存key
     */
    public static final String SUBCHECK_ITEMRULE_CACHE_KEY = "subCheckItemRuleData";

    /**
     * 中债报送自检项目编号转换数据缓存key
     */
    public static final String SUBCHECK_ITEMCONV_CACHE_KEY = "subCheckItemConvertData";

    /**
     * 是否当日报送延迟接入数据
     */
    public static final String IS_JOIN_DELAYDATA_TODAY = "IS_JOIN_DELAYDATA_TODAY";

    /**
     * 投资者持有信息统计截止时点
     */
    public static final String INVESTORS_COUNT_END_TIME_POINT = "INVESTORS_COUNT_END_TIME_POINT";

    /**
     * &
     */
    public static final String AMPERS_AND = "&";

    /**
     * !
     */
    public static final String AMPERS_NOT = "!";

    /**
     * 等号
     */
    public static final String EQUALITY_SIGN = "=";

    /**
     * 当在做导入前的check就是判断业务数据是否需要覆盖的时候在webresult的data中塞入这个值可以不会弹框两次
     */
    public static final String NO_INFO_SHOWOUT = "noInfoShowOut";

    /**
     * 更新
     */
    public static final String PAGE_UPDATE = "/update";

    public static final String SEARCH_PAGE = "searchPage";

    /**
     * 报送时刻配置
     */
    public static final String SUBMIT_TIME = "SUBMIT_TIME";

    /**
     * 默认电话
     */
    public static final String DEFAULTPHONE = "11111111111";
    /**
     * 无
     */
    public static final String NAUGHT="无";
    /**
     * 默认电话
     */
    public static final String DEFAULTTEL="11111111";
    /**
     * 默认邮箱
     */
    public static final String DEFAULTEMAIL="11@11.com";

    /**
     * 上游接口解析读取文件处理中，是否需要.ok传输完成标志文件 1：需要
     */
    public static String reportTinterfIsneedokfileYes = "1";

    /**
     * 修改产品登记编码是否覆盖
     */
    public static final String SYS_PARA_REP_IS_COVER = "REP_IS_COVER";
    /**
     * 未进行校验标志
     */
    public static final String IS_UN_SUBMIT_CHECK = "0";
    /**
     * 数据检查标志位
     */
    public static final String IS_NUMBER_FLAG = "1";
    /**
     * 竖线
     */
    public static final String VERTICAL_LINE = "|";
    /**
     *
     */
    public static final String MANAY_SPACE="    ";
    /**
     * 电话号码是否给予默认值
     */
    public static final String IS_DEFAUL_TELPHONE = "IS_DEFAUL_TELPHONE";
    /**
     * 是否当日报送延迟接入数据
     */
    public static final String IS_GENERATE_INVESTOR = "IS_GENERATE_INVESTOR";
    /**
     * 数据归档中数据保留天数
     */
    public static final String DATA_BACKUP_DAYS = "DATA_BACKUP_DAYS";

    /**
     * 通道投资资产管理产品是否穿透报送(01：是，02：否) add by weiyuqiang 
     */
    public static final String SVR_IS_CHL_BTM = "SVR_IS_CHL_BTM";



    /**
     * 中债投资接入费用交易是否包含本行  01 包含 02 不含
     */
    public static final String SVR_FEE_ORG = "SVR_FEE_ORG";

    /**
     * 余额计算时单位净值是否要保留5位小数），01：是，02：否
     */
    public static final String SVR_QDBK_IS_PRECISION = "SVR_QDBK_IS_PRECISION";

    /**
     * 募集兑付是否统计红利再投），01：是，02：否
     */
    public static final String SVR_RAC_HAS_BOUNS = "SVR_RAC_HAS_BOUNS";


    /**
     * 发行机构名称add by weiyuqiang 
     */
    public static final String SVR_ISSUE_PARTY_NAME = "SVR_ISSUE_PARTY_NAME";

    /**
     * 发行机构代码 add by weiyuqiang 
     */
    public static final String SVR_ISSUE_PARTY_ID = "SVR_ISSUE_PARTY_ID";

    /**
     * 发行上游资产交易数据接入是否自动生成产品买通道交易(01：自动生成 02：不自动生成) add by weiyuqiang 
     */
    public static final String SVR_IS_AUTOGENE_CHLDEAL = "SVR_IS_AUTOGENE_CHLDEAL";
    
    /**
     * 人行月度增加开关：是按照产品到期日还是期次到期日出3-3报表（Y:期次到期日；N:产品到期日）
     */
    public static final String SVR_IS_PERIOD = "SVR_IS_PERIOD";
    
    /**
     * 中债节假日表取上流系统的哪个日历编号（03-银行间）
     */
    public static final String SVR_CALENDAR_ID = "SVR_CALENDAR_ID";
    /**
     * 单位净值取值来源(01:估值核算系统，02:信息批露系统，03:第三方)
     */
//    public static final String SVR_NET_UNIT_VALUE_SOURCE = "SVR_NET_UNIT_VALUE_SOURCE";
    /**
     * 中债汇率取值来源(01:开盘价，02:收盘价，03:最高价，04:最低价，05:中间价)
     */
    public static final String SVR_EXCHANGE_SOURCE = "SVR_EXCHANGE_SOURCE";
    
    /**
     * 净募集金额取值参数(01:净值型产品赎回有收益,02:净值型产品赎回无收益)
     */
    public static final String SVR_CLEANAMT_SOURCE = "SVR_CLEANAMT_SOURCE";
    
    /**
     * 单次批量插入最大数量(正整数)
     */
    public static final String SVR_INSERT_COUNT = "SVR_INSERT_COUNT";

    /**
     * 人行报表-填报机构名称add by weiyuqiang 
     */
    public static final String SVR_REPORT_ORG_NAME = "SVR_REPORT_ORG_NAME";

    /**
     * 人行报表-金融机构编码add by weiyuqiang 
     */
    //public static final String SVR_FIN_INSTITUTION_CODE = "SVR_FIN_INSTITUTION_CODE";

    /**
     * 人行报表-社会信用代码add by weiyuqiang 
     */
   // public static final String SVR_SOCIAL_CREDIT_CODE = "SVR_SOCIAL_CREDIT_CODE";
    /**
     * 人行汇率取值来源(01:开盘价，02:收盘价，03:最高价，04:最低价，05:中间价)
     */
    public static final String SVR_EXCHANGE_SOURCE_PBC = "SVR_EXCHANGE_SOURCE_PBC";
    /**
     * 人行报表-报送时点配置时分秒
     */
    public static final String SVR_SUBMIT_TIME_PBC = "SVR_SUBMIT_TIME_PBC";

    /**
     * 总体核查颜色mid
     */
    public static final String SVR_TOTALCHECK_COLOR_CONFIG_MID = "SVR_TOTALCHECK_COLOR_CONFIG_MID";

    /**
     * 总体核查颜色top
     */
    public static final String SVR_TOTALCHECK_COLOR_CONFIG_TOP = "SVR_TOTALCHECK_COLOR_CONFIG_TOP";
    
    /**
     * 产品交易文件接口类型：01规范文件
     */
    public static final String CHB_PRD_DEAL_FILE_TYPE_01 = "01";
    /**
     * 产品交易文件接口类型：02阜新恒生文件
     */
    public static final String CHB_PRD_DEAL_FILE_TYPE_02 = "02";
    /**
     * 文件接入是否需要.ok文件 01 需要
     */
    public static final String IS_NEED_OK_01 = "01";
    /**
     * 文件接入是否需要.ok文件 02 不需要
     */
    public static final String IS_NEED_OK_02 = "02";
    /**
     * 评级取值规则(01:推荐评级 02:最大评级 03:最小评级)
     */
    public static final String SVR_SEC_RATING_FLAG = "SVR_SEC_RATING_FLAG";
    /**
     * 系统参数 人行报送通道是否穿透
     */
    public static final String PBC_IS_CHL_BTM="PBC_IS_CHL_BTM";
    /**
     * 系统参数:是否中债子系统
     */
    public static final String SVR_IS_SUB_SYSTEM = "SVR_IS_SUB_SYSTEM";

    /**
     * 系统参数  监管报送 模块的 menu_id 类似于 REPORT_001 各项目配置不一样 按实际配置
     */
    public static final String SVR_FIRST_MENU_ID = "SVR_FIRST_MENU_ID";
    /**
     * 系统参数:是否子公司
     */
    public static final String SVR_IS_SUBSIDIARY = "SVR_IS_SUBSIDIARY";

    /**
     * 系统参数:是否生成修改\删除未报送（Y:生成，N:不生成）
     */
    public static final String SVR_SUBMIT_ONCHANGE = "SVR_SUBMIT_ONCHANGE";
    
    /**
     * 资产头寸期末余额计算方式
     */
    public static final String SVR_ASSETPOSITION_CALWAY = "SVR_ASSETPOSITION_CALWAY";
    /**
     * 资产头寸剩余现金取值方式
     */
    public static final String SVR_ASSETPOSITION_CASHCALWAY = "SVR_ASSETPOSITION_CASHCALWAY";
    
    /**
     * 资产月报期末余额计算方式-市价法
     */
    public final static String ASSETPOSITION_CALWAY_MARKETPRICE = "01";

    /**
     * 资产月报期末余额计算方式-移动平均法
     */
    public final static String ASSETPOSITION_CALWAY_AVERAGE = "02";
    /**
     * 系统参数:导出excel或预览时，值为0的单元格显示成0还是显示成空，系统实现可配置
     */
    public static final String SVR_IS_SHOW_ZERO = "SVR_IS_SHOW_ZERO";
    
    /**
     * 系统参数:中债子系统 银行个性化开关配置
     */
    public static final String CUSTOMER_BANK_CODE  = "CUSTOMER_BANK_CODE"; 
     /**
     * 系统参数:中债子系统 江苏银行个性化开关配置
     */
    //public static final String CUSTOMER_JSBANK_CODE  = "CUSTOMER_JSBANK_CODE";
    /**
     * 【中债交易对手取值】1：虚拟交易对手，参数配置， 0：真实交易对手，即交易表存的交易对手
     */
    public static final String SVR_IF_REAL_COUNTER = "SVR_IF_REAL_COUNTER";
    /**
     * 虚拟交易对手 配置
     */
    public static final String SVR_VIRTUAL_COUNTER = "SVR_VIRTUAL_COUNTER";
    
    /**
     * 是否跑分层（01:是,02:否）
     */
    public static final String SVR_IS_LAYERED = "SVR_IS_LAYERED";
    /**
     * 系统类别：01中债一二期,02人行,03中债三期，04信批（可以填多个以，隔开）
     */
    public static final String SVR_SYSTEM_TYPE="SVR_SYSTEM_TYPE";

    /**
     * 产品登记编码取值主表：01-CHB_PRDRESIST_CODE,02-CHB_PRD_DECLARE_INFO
     */
    public static final String SVR_PRDRESIST_CODE="SVR_PRDRESIST_CODE";
    
    /**
     * 主体评级维度: 01 资产，02 主体
     */
    public static final String PRINCIPAL_RATING_DIMENSION = "PRINCIPAL_RATING_DIMENSION";
    /**
     * 子资管计划资产基础指标数据表筛选开关: Y 开,N 关
     */
    public static final String CHB_ASSETS_BASIS_DATA_CHILD = "CHB_ASSETS_BASIS_DATA_CHILD";

    /**
     * 非净值型产品赎回交易取值（01 赎回金额=金额，赎回份额=份额；02 赎回金额=份额，赎回份额=份额；03 赎回金额=金额，赎回份额=金额)
     */
    public static final String PBC_PROD_RAISING = "PBC_PROD_RAISING";

    /**
     * 收益率单位净值接口数据确认（01:已下发,02:已确认）
     */
    public static final String SVR_VALSTATUS_FLAG = "SVR_VALSTATUS_FLAG";
    
    /**
     * 工作日天数
     */
    public static final String SVR_PAY_STATUS = "SVR_PAY_STATUS";

    /**
     * 通道资产到期日配置参数(人行表3-3,01：通道资产的到期日，02：具体的天数)
     */
    public static final String CHANNEL_ASSETS_MDATE_CONF = "CHANNEL_ASSETS_MDATE_CONF";
    
    /**
     * 通道资产到期日配置参数具体天数
     */
    public static final String CHANNEL_ASSETS_MDATES = "CHANNEL_ASSETS_MDATES";
    
    /**
     * 货币基金资产资产到期日配置参数(人行表3-3,01：通道资产的到期日，02：具体的天数)
     */
    public static final String IMF_ASSETS_MDATE_CONF = "IMF_ASSETS_MDATE_CONF";
    
    /**
     * 货币基金资产资产到期日配置参数具体天数
     */
    public static final String IMF_ASSETS_MDATES = "IMF_ASSETS_MDATES";
    
    /**
     * 红利再投交易是否统计到兑付金额标识
     */
    public static final String DIVIDEND_REINVESTMENT_FLAG = "DIVIDEND_REINVESTMENT_FLAG";

    /**
     * 人行月报2-1 倒减逻辑：01 其他负债，02 未分配利润
     */
    public static final String SVR_MONTH_SUBTRACTION_LOGIC = "SVR_MONTH_SUBTRACTION_LOGIC";
    
    /**
     * 开放式产品的申购赎回交易，业务结束日取值配置（01 确认日、02 开放结束日）
     */
    public static final String OPEN_PROD_MDATE_CONF = "OPEN_PROD_MDATE_CONF";
    
    /**
     * 银行月报金额份额控制 01：金额，02：份额
     */
    public static final String SVR_CHBMONTH_REPORT = "SVR_CHBMONTH_REPORT";
   
    /**
     * 资本公积个性化系统参数
     */
    public static final String SVR_ZBGJ_PERSON_LOGIC = "SVR_ZBGJ_PERSON_LOGIC";
    

    /**
     * 江苏银行保留小数位截取个性化参数
     */
    public static final String SVR_JSBK_IS_ROUND = "SVR_JSBK_IS_ROUND";
    
    /**
     * 江苏银行关于净值型产品存报送产品代码开关
     */
    public static final String SVR_JSBK_IS_SUBPROD = "SVR_JSBK_IS_SUBPROD";
    
    /**
     * 01：债券资产的到期日，02：行权债取最近的行权日;无行权日或其他债券时,取资产到期日)
     */
    public static final String SEC_ASSETS_MDATE_CONF = "SEC_ASSETS_MDATE_CONF";
    /**
     * 贷款明细维度 ： 01 资产代码维度；02 产品代码+ 资产代码维度（江苏银行用）
     */
    public static final String LOAN_DETAIL_VEIDOO = "LOAN_DETAIL_VEIDOO";

    /**
     * 中债错误信息单次导出条数
     */
    public static final String SVR_ERROR_INSERT_COUNT = "SVR_ERROR_INSERT_COUNT";
    
    /**
     * 业务确认日期范围选择控制
     */
    public static final String SVR_EQUITY_CONF_DATE_SELECT_RANGE = "EQUITY_CONF_DATE_SELECT_RANGE";

    /**
     * 中债报送批处理导出数据量
     */
	public static final String SVR_SUBINFO_BATCH_EXPORT_NUM = "SVR_SUBINFO_BATCH_EXPORT_NUM";

    /**
     * 中债批处理导出最大线程数
     */
    public static final String SVR_MAX_CORE_POOL_SIZE = "MAX_CORE_POOL_SIZE";

    /**
     * 中债导出临时中转路径
     */
    public static final String SVR_TMP_PATH = "SVR_TMP_PATH";
    
    /**
     * 中债投资者明细信息是否直连 01是 02 否*/
    public static final String SVR_IS_SEND_EQUDET = "IS_SEND_EQUDET";
    
	 
    /**
     * 中债系统参数:导出excel或预览时，值为0的单元格显示成0还是显示成空，系统实现可配置 add by zhouqiang
     */
    public static final String CHB_IS_SHOW_ZERO = "CHB_IS_SHOW_ZERO";
	
    /**
     * 产品汇总信息是否从投资者交易表中汇总 01 是   02 否
     */
    public static final String SVR_IS_FROM_PRD_DEAL = "SVR_IS_FROM_PRD_DEAL";
    
    /**
     * 是否走产品汇总交易那套逻辑
     */
    public static final String SVR_IS_TOATL_YES_NO = "SVR_IS_TOATL_YES_NO";

    /**
     * 募集登记客户数是否走投资者明细中取(01:是  02:否)
     */
    public static final String SVR_IS_EQUDET_CONUT = "SVR_IS_EQUDET_CONUT";
    
    /**
     * 存续期产品余额计算方式(01:投资端持有汇总  02:交易汇总)
     */
    public static final String DEALSUB_BALANCEAMT_SUM_TYPE = "DEALSUB_BALANCEAMT_SUM_TYPE";
    

    /**统一数据维护删除逻辑***/
    public static final String SVR_OPER_DELETE_FLAG = "SVR_OPER_DELETE_FLAG";
    
}
