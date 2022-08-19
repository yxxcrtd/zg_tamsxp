/*
 * 文件名：InterfConstants.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Administrator
 * 修改时间：2019年5月8日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.common;


/**
 * 接口类型枚举
 * @author chenxc
 * @version 1.7 2019年5月8日
 * @see InterfDefine
 * @since 1.0
 */
public enum InterfDefine {

    投资者信息接口数据("CHB_EQU_HOLD","投资者信息接口数据"),
    投资者持有信息接口数据("CHB_INVESTHOLD_INFO","投资者持有信息接口数据"),
    投资者交易信息接口数据("CHB_PRD_DEAL","投资者交易信息接口数据"),
    交易对手接口数据("CHB_COUNTER_PARTY","交易对手接口数据"),
    产品基本信息接口数据("CHB_PRD_BASEINFO","产品基本信息接口数据"),
    产品申报信息接口数据("CHB_PRD_DECLARE","产品申报信息接口数据"),
    产品开放计划接口数据("CHB_PRD_OPENSCH","产品开放计划接口数据"),
    产品收益率净值接口数据("CHB_PRD_PERIODYIELD","产品收益率净值接口数据"),
    产品费用交易接口数据("CHB_PRD_FEE_DEAL","产品费用交易接口数据"),
    资产要素信息接口数据("CHB_ASSET_INFO","资产要素信息接口数据"),
    投资交易信息接口数据("CHB_INVEST_TRADE","投资交易信息接口数据"),
    资管计划底层资产持仓接口数据("CHB_BOT_AST_POS","资管计划底层资产持仓接口数据"),
    周期型产品余额接口数据("CHB_CPROD_BALANCE","周期型产品余额接口数据"),
    产品分红登记("DIS_PRD_BONUS","产品分红登记"),
    产品费率数据("DIS_PRD_FEE_RATE","产品费率数据"),
    产品资产调整数据("DIS_PRD_ADJUST","产品资产调整数据"),
    产品资产种类投资比例调整数据("DIS_ASSET_INVEST_RATE","产品资产种类投资比例调整数据"),
    产品基础指标数据("DIS_PRD_INDEX","产品基础指标数据"),
    资产基础指标数据("CHB_ASSET_INDEX","资产基础指标数据"),
    资产基础指标不穿透数据("CHB_ASSETS_BASIS_DATA_NOBTM","资产基础指标不穿透数据"),
    科目余额数据("CHB_SUBJECT_BALANCE","科目余额数据"),
    关联机构信息("DIS_REL_ORG","关联机构信息"),
    基础日历信息数据("DIS_CALENDAR_INFO","基础日历信息数据"),
    日历管理信息数据("DIS_CALENDAR_MGR","日历管理信息数据"),
    债券评级数据("CHB_SEC_RATING","债券评级数据"),
    节假日数据("MST_HOLIDAY","节假日数据"),
    汇率信息数据("MST_EXCHARGE_QUOTE","汇率信息数据"),
    市场机构数据("MST_CUSTOMER_INFO","市场机构数据"),
    科目数据("DIS_BOK_SUBJECT","科目数据"),
    每日日终接取收益信息接口数据("CHB_PRD_PERIODYIELD_END_DAY","每日日终接取收益信息接口数据"),
    母子资产基础指标数据("CHB_CASSET_INDEX","母子资产基础指标数据"),
    投资者明细信息接口数据("CHB_EQUDET","投资者明细信息接口数据"),
    //增加信批数据校验类型 start
    数据检查类型_产品基本信息接口("DIS_CK_PRD_BASEINFO","产品基本信息接口数据检查类型"),
    数据检查类型_产品收益率净值接口("DIS_CK_PRD_PERIODYIELD","产品收益率净值接口数据检查类型"),
    数据检查类型_产品费用交易接口("DIS_CK_PRD_FEE_DEAL","产品费用交易接口数据检查类型"),
    数据检查类型_资产要素信息接口("DIS_CK_ASSET_INFO","资产要素信息接口数据检查类型"),
    数据检查类型_投资交易信息接口("DIS_CK_INVEST_TRADE","投资交易信息接口数据检查类型"),
    数据检查类型_资产基础指标("DIS_CK_ASSET_INDEX","资产基础指标数据检查类型"),
    数据检查类型_科目余额("DIS_CK_SUBJECT_BALANCE","科目余额数据检查类型"),
    //增加信批数据校验类型 end
    贷款明细信息接口数据("CHB_LOAN_DETAIL_INFO","贷款明细信息接口数据"),
    贷款明细原始合同信息接口("CHB_LOAN_DETAIL_CONTRACT","贷款明细原始合同信息接口"),
    产品费用头寸接口("CHB_PRDFEE_BASIS_DATA","产品费用头寸接口"),
    产品分红交易接口数据("CHB_PRD_BONUS_DEAL","产品分红交易接口数据"),
    监管区域客户组别接口数据("SALE_CUST_SVRREPORT","监管区域客户组别接口数据"),
    销售结算资金交易信息表报送数据接入("CHB_SALER_STL_DEAL_INFO","销售结算资金交易信息表报送数据接入"),

    产品利率基本信息接口数据("CHB_PRD_RATE","产品利率基本信息接口数据"),
    资产利率基本信息接口数据("CHB_ASSET_RATE","资产利率基本信息接口数据"),

    产品汇总交易接口数据("CHB_PRD_DEAL_TOTAL","产品汇总交易接口数据"),
    流动性风险应对情况信息接口("LIQUIDITY_RISK_INFO","流动性风险应对情况信息接口"),
    //add by wm 20201113 增加一体化报送页面参数 start
    产品申报登记信息("CHB_PRD_DECLARE_SUB","产品申报登记信息"),
    产品发行登记信息("CHB_PRD_ISSUE_SUB","产品发行登记信息"),
    产品募集总量登记信息("CHB_PRD_RAISETOT_SUB","产品募集总量登记信息"),
    产品存续期登记信息("CHB_PRD_DEAL_SUB","产品存续期登记信息"),
    产品终止登记信息("CHB_PRD_END_SUB","产品终止登记信息"),
    资产要素登记信息("CHB_ASSET_INFO_SUB","资产要素登记信息"),
    资产交易登记信息("CHB_INVEST_TRADE_SUB","资产交易登记信息"),
    底层资产持仓登记("CHB_BOT_AST_POS_SUB","底层资产持仓登记"),
    资产估值登记信息("CHB_AST_VAL_SUB","资产估值登记信息"),
    总部或分支机构信息接口数据("CHB_SALER_INST_INFO","总部或分支机构信息接口数据"),
    销售人员身份信息接口数据("CHB_SALER_IDEN_INFO","销售人员身份信息接口数据"),
    销售人员奖惩投诉记录信息接口数据("CHB_SALER_REW_PENA_INFO","销售人员奖惩投诉记录信息接口数据")

    ;

    /*两个属性*/
    private String code;
    private String name;

    /*构造函数*/
    private InterfDefine(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

}
