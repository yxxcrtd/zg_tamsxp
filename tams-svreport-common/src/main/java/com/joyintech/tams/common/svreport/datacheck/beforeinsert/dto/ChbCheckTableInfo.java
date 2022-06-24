/**
 * 
 */
package com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto;

import com.joyintech.framework.util.StringUtil;

/**
 * 中债校验用的表名
 * 
 * @author dubaojun
 * @since 2020-10-13
 */
public enum ChbCheckTableInfo {
    总部或分支机构信息登记表SUB("CHB_SALER_INST_SUB", true, "", new String[] {"SALER_ORG_CODE","SALER_ORG_NAME"}, ""),
    销售人员身份信息登记表SUB("CHB_SALER_IDEN_SUB", true, "", new String[] {"SALER_INS_NO"}, ""),
    销售人员奖惩投诉记录登记表SUB("CHB_SALER_REW_PENA_SUB", true, "", new String[] {"SALER_REW_PENA_NO"}, ""),
    销售结算资金交易登记表SUB("CHB_SALER_STL_DEAL_SUB", true, "", new String[] {"INSTITUTION_CODE","TRADE_NO"}, ""),
	从业人员登记表SUB("CHB_EMPLOYEE_INFO_SUB", true, "", new String[] {"EMPLOYEE_TYPE","IDENTIFY_NUMBER"}, ""),
    个人发行登记表SUB("CHB_PRD_ISSUE_SUB", true, "PROD_ID", new String[] {"PROD_ID"}, ""),
    募集总量登记SUB("CHB_PRD_RAISETOT_SUB", true, "PROD_ID", new String[] {"PROD_ID"}, ""),
    投资交易表登记SUB("CHB_INVEST_TRADE_SUB", true, null, new String[] {"PROD_ID", "TRADE_CODE", "ASSET_CODE", "CUR_ASSET_TYPE"}, ""),
    资产要素登记表SUB("CHB_ASSET_INFO_SUB", true, "ASSET_CODE", new String[] {"ASSET_CODE"}, ""),
    资产估值信息登记表SUB("CHB_AST_VAL_SUB", true, null, new String[] {"ASSET_CODE", "VAL_DATE"}, ""),
    个人产品申报登记SUB("CHB_PRD_DECLARE_SUB", true, "PROD_INS_ID", new String[] {"PROD_INS_ID"}, ""),
    产品存续登记表SUB("CHB_PRD_DEAL_SUB", true, null, new String[] {"PROD_ID", "MDATE"}, ""),
    底层资产持仓信息登记表SUB("CHB_BOT_AST_POS_SUB", true, null, new String[] {"POS_DATE", "ASSET_CODE", "PLAN_ASSET_ID"}, ""),
    终止登记SUB("CHB_PRD_END_SUB", true, "PROD_ID", new String[] {"PROD_ID"}, ""),

    /*文件接入*/
    资产要素登记表INFO("CHB_ASSET_INFO", true, "ASSET_CODE", new String[] {"ASSET_CODE"}, ""),
    个人发行登记表INFO("CHB_PRD_BASEINFO", true, "PROD_ID", new String[] {"PROD_ID"}, ""),
    产品存续登记表("CHB_PRD_DEAL", true, null, new String[] {"PROD_ID", "MDATE"}, ""),
    个人产品申报登记INFO("CHB_PRD_DECLARE_INFO", true, "PROD_INS_ID", new String[] {"PROD_INS_ID"}, ""),
    底层资产持仓信息登记INFO("CHB_BOT_AST_POS", true, null, new String[] {"POS_DATE","ASSET_CODE","PLAN_ASSET_ID"}, ""),
	从业人员登记表INFO("CHB_EMPLOYEE_INFO", true, "", new String[] {"EMPLOYEE_TYPE","IDENTIFY_NUMBER"}, ""),
    销售结算资金交易信息表INFO("CHB_SALER_STL_DEAL_INFO", true, "TRADE_NO", new String[] {"INSTITUTION_CODE","TRADE_NO","ACCOUNT_NO","PRD_REGIST_CODE"}, ""),

    总部或分支机构信息表INFO("CHB_SALER_INST_INFO", true, "SALER_ORG_CODE", new String[] {"SALER_ORG_CODE","SALER_ORG_NAME"}, ""),
    销售人员身份信息表INFO("CHB_SALER_IDEN_INFO", true, "SALER_INS_NO", new String[] {"SALER_INS_NO"}, ""),
    销售人员奖惩投诉记录信息表INFO("CHB_SALER_REW_PENA_INFO", true, "SALER_REW_PENA_NO", new String[] {"SALER_REW_PENA_NO"}, ""),



    /*杭州TMP*/
    募集总量登记TMP("CHB_PRD_RAISETOT_SUB_TMP", true, "PROD_ID", new String[] {"PROD_REGIST_CODE"}, "CHB_PRD_RAISETOT_SUB"),
    个人发行登记TMP("CHB_PRD_ISSUE_SUB_TMP", true, "PROD_ID", new String[] {"PROD_ID"}, "CHB_PRD_ISSUE_SUB"),
    底层资产持仓信息登记TMP("CHB_BOT_AST_POS_SUB_TMP", true, null, new String[] {"PLAN_ASSET_ID", "ASSET_CODE", "POS_DATE"}, "CHB_BOT_AST_POS_SUB"),
    投资交易表登记TMP("CHB_INVEST_TRADE_SUB_TMP", true, "TRADE_CODE", new String[] {"PROD_REGIST_CODE", "TRADE_CODE", "ASSET_CODE", "CUR_ASSET_TYPE"}, "CHB_INVEST_TRADE_SUB"),
    资产估值信息登记TMP("CHB_AST_VAL_SUB_TMP", true, "ASSET_CODE", new String[] {"ASSET_CODE","VAL_DATE"}, "CHB_AST_VAL_SUB"),
    产品存续登记TMP("CHB_PRD_DEAL_SUB_TMP", true, "PROD_ID", new String[] {"PROD_REGIST_CODE", "MDATE"}, "CHB_PRD_DEAL_SUB"),
    个人产品申报登记TMP("CHB_PRD_DECLARE_SUB_TMP", true, "PROD_INS_ID", new String[] {"PROD_INS_ID"}, "CHB_PRD_DECLARE_SUB"),
    终止登记TMP("CHB_PRD_END_SUB_TMP", true, "PROD_ID", new String[] {"PROD_REGIST_CODE"}, "CHB_PRD_END_SUB"),
    资产要素登记TMP("CHB_ASSET_INFO_TMP", true, "ASSET_CODE", new String[] {"ASSET_CODE"}, "CHB_ASSET_INFO_SUB"),
    投资交易信息表("CHB_INVEST_TRADE", true, "TRADE_SEQNO", new String[] {"PROD_ID","ASSET_CODE","TRADE_CODE"}, ""),
    节假日("MST_HOLIDAY", true, "", new String[] {"CALENDAR_ID","CDATE"}, ""),
    汇率行情信息("MST_EXCHARGE_QUOTE", true, "", new String[] {"EXCHARGE_CODE","TRADE_DATE","DATASOURCE_TYPE"}, "");
    /**
     * 根据tableName获取枚举对象
     * @param tableName 表名
     * @return 枚举对象
     */
    public static ChbCheckTableInfo genChbCheckTableInfo(String tableName){
        ChbCheckTableInfo[] values = ChbCheckTableInfo.values();
        for (ChbCheckTableInfo code : values) {
            if (StringUtil.equals(tableName, code.getTableName())) {
                return code;
            }
        }
        return null;
    }

    /**
     * 根据tableName获取枚举对象
     * @param tableName 表名
     * @return 校验对象
     */
    public static String genDataType(String tableName){
    	/***【统一数据维护】监管区域客户组别导入时，报空指针异常 leiepeng 20210511***/
        ChbCheckTableInfo tableInfo = genChbCheckTableInfo(tableName);
        return null!=tableInfo? 
        		(StringUtil.isNotEmpty(tableInfo.getDataType()) ? tableInfo.getDataType() : tableInfo.getTableName()):tableName;
    }

    /**
     * 表名
     */
    private final String tableName;
    /**
     * 是否需要代码校验
     */
    private final boolean needCodeCheck;
    /**
     * 业务主键
     */
    private final String bussinessKey;
    /**
     * 校验数据重复的字段
     */
    private final String[] checkEuplicatedKeys;

    /**
     * TMP使用，指向SUB校验
     */
    private String dataType;

    /**
     * 私有化构造器
     * 
     * @param tableName 表名
     * @param needCodeCheck 是否需要代码校验
     * @param bussinessKey 业务主键
     * @param checkEuplicatedKeys 校验数据重复的字段
     */
    private ChbCheckTableInfo(String tableName, boolean needCodeCheck, String bussinessKey, String[] checkEuplicatedKeys, String dataType) {
        this.tableName = tableName;
        this.needCodeCheck = needCodeCheck;
        this.bussinessKey = bussinessKey;
        this.checkEuplicatedKeys = checkEuplicatedKeys;
        this.dataType = dataType;
    }

    /**
     * 获取 tableName
     * 
     * @return tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 获取 needCodeCheck
     * 
     * @return needCodeCheck
     */
    public boolean isNeedCodeCheck() {
        return needCodeCheck;
    }

    /**
     * 获取 bussinessKey
     * 
     * @return bussinessKey
     */
    public String getBussinessKey() {
        return bussinessKey;
    }

    /**
     * 获取 checkEuplicatedKeys
     * 
     * @return checkEuplicatedKeys
     */
    public String[] getCheckEuplicatedKeys() {
        return checkEuplicatedKeys;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
