/*
 * 文件名：ChbMonthReportConstants.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Administrator
 * 修改时间：2019年1月24日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.pbc.common.constants;


/**
 * 人行报表常量类
 * @author weiyuqiang
 * @version 1.6.0 2019年3月12日
 * @see
 * @since 1.6.0
 */
public class PbcMonthReportConstants {
    // 报表频率
    // M-月
    public final static String REPORT_FREQU_M = "M";

    /**
     * 资产一级类型:资本公积金
     */
    public static final String PBC_ASSETTYPE1_ZBGJJ = "ZBGJJ";

    /**
     * 资产一级类型:其他综合收益
     */
    public static final String PBC_ASSETTYPE1_QTZHSY = "QTZHSY";

    /**
     * 资产一级类型:未分配理论
     */
    public static final String PBC_ASSETTYPE1_WFPLR = "WFPLR";

    /**
     * 头寸类型_资产：01
     */
    public static final String POSITION_TYPE_ZC = "01";

    /**
     * 头寸类型_负债：02
     */
    public static final String POSITION_TYPE_FZ = "02";

    /**
     * 头寸类型_科目余额：03
     */
    public static final String POSITION_TYPE_KMYE = "03";

    /**
     * 映射类型-科目号 add by weiyuqiang 2019-03-12
     */
    public static final String MAPPINGCODE_SUBJECTNO = "MAPPINGCODE_SUBJECTNO";

    public static final String PBC_ASSETTYPE1_P01= "P01";//现金

    public static final String PBC_ASSETTYPE1_P02= "P02";//存款

    public static final String PBC_ASSETTYPE1_P03= "P03";//可转让存单

    public static final String PBC_ASSETTYPE1_P04= "P04";//债务证券

    public static final String PBC_ASSETTYPE1_P05= "P05";//贷款

    public static final String PBC_ASSETTYPE1_P06= "P06";//资产收益权

    public static final String PBC_ASSETTYPE1_P07= "P07";//股权及特定目的载体份额

    public static final String PBC_ASSETTYPE1_P08= "P08";//金融衍生品

    public static final String PBC_ASSETTYPE1_P09= "P09";//黄金

    public static final String PBC_ASSETTYPE1_P10= "P10";//非金融资产

    public static final String PBC_ASSETTYPE1_P11= "P11";//应收账款

    public static final String PBC_ASSETTYPE1_P12= "P12";//应付账款

    public static final String PBC_ASSETTYPE1_P13= "P13";//其他负债

    public static final String PBC_ASSETTYPE2_X01= "X01";//现金

    public static final String PBC_ASSETTYPE2_C01= "C01";//境内存款

    public static final String PBC_ASSETTYPE2_C02= "C02";//境外存款

    public static final String PBC_ASSETTYPE2_K01= "K01";//同业存单

    public static final String PBC_ASSETTYPE2_K02= "K02";//大额存单

    public static final String PBC_ASSETTYPE2_S01= "S01";//政府债券

    public static final String PBC_ASSETTYPE2_S02= "S02";//中央银行债券

    public static final String PBC_ASSETTYPE2_S03= "S03";//政策性金融债券

    public static final String PBC_ASSETTYPE2_S04= "S04";//其他金融债券

    public static final String PBC_ASSETTYPE2_S05= "S05";//企业债券

    public static final String PBC_ASSETTYPE2_S06= "S06";//票据

    public static final String PBC_ASSETTYPE2_S07= "S07";//资产支持证券

    public static final String PBC_ASSETTYPE2_S08= "S08";//境外债券

    public static final String PBC_ASSETTYPE2_R01= "R01";//拆借和回购

    public static final String PBC_ASSETTYPE2_R02= "R02";//其他

    public static final String PBC_ASSETTYPE2_R03= "R03";//1108买入返售

    public static final String PBC_ASSETTYPE2_Q01= "Q01";//贷款收益权

    public static final String PBC_ASSETTYPE2_Q02= "Q02";//债务证券收益权

    public static final String PBC_ASSETTYPE2_Q03= "Q03";//应收账款收益权

    public static final String PBC_ASSETTYPE2_Q04= "Q04";//其他债权收益权

    public static final String PBC_ASSETTYPE2_Q05= "Q05";//股票收益权

    public static final String PBC_ASSETTYPE2_Q06= "Q06";//其他股权收益权

    public static final String PBC_ASSETTYPE2_Q07= "Q07";//物权资产收益权

    public static final String PBC_ASSETTYPE2_T01= "T01";//股票

    public static final String PBC_ASSETTYPE2_T02= "T02";//特定目的载体份额

    public static final String PBC_ASSETTYPE2_T03= "T03";//其他股权

    public static final String PBC_ASSETTYPE2_T04= "T04";//境外股权

    public static final String PBC_ASSETTYPE2_J01= "J01";//境内

    public static final String PBC_ASSETTYPE2_J02= "J02";//境外

    public static final String PBC_ASSETTYPE2_H01= "H01";//黄金

    public static final String PBC_ASSETTYPE2_F01= "F01";//非金融资产

    public static final String PBC_ASSETTYPE2_P11= "P11";//应收账款

    public static final String PBC_ASSETTYPE2_P12= "P12";//应付账款

    public static final String PBC_ASSETTYPE2_P13= "P13";//其他负债

    public static final String PBC_ASSETTYPE3_X01= "X01";//现金

    public static final String PBC_ASSETTYPE3_C01= "C01";//境内存款

    public static final String PBC_ASSETTYPE3_C02= "C02";//境外存款

    public static final String PBC_ASSETTYPE3_D01= "D01";//境内 活期存款

    public static final String PBC_ASSETTYPE3_D02= "D02";//境内 定期和其他存款

    public static final String PBC_ASSETTYPE3_D03= "D03";//境外 活期存款

    public static final String PBC_ASSETTYPE3_D04= "D04";//境外 定期和其他存款

    public static final String PBC_ASSETTYPE3_K01= "K01";//同业存单

    public static final String PBC_ASSETTYPE3_K02= "K02";//大额存单

    public static final String PBC_ASSETTYPE3_S01= "S01";//政府债券

    public static final String PBC_ASSETTYPE3_S02= "S02";//中央银行债券

    public static final String PBC_ASSETTYPE3_S03= "S03";//政策性金融债券

    public static final String PBC_ASSETTYPE3_S04= "S04";//其他金融债券

    public static final String PBC_ASSETTYPE3_S05= "S05";//企业债券

    public static final String PBC_ASSETTYPE3_S06= "S06";//票据

    public static final String PBC_ASSETTYPE3_S07= "S07";//资产支持证券

    public static final String PBC_ASSETTYPE3_S08= "S08";//境外债券

    public static final String PBC_ASSETTYPE3_L01= "L01";//住户

    public static final String PBC_ASSETTYPE3_L02= "L02";//广义政府

    public static final String PBC_ASSETTYPE3_L03= "L03";//非金融企业

    public static final String PBC_ASSETTYPE3_L04= "L04";//银行业存款类金融机构

    public static final String PBC_ASSETTYPE3_L05= "L05";//银行业非存款类金融机构

    public static final String PBC_ASSETTYPE3_L06= "L06";//非银行业金融机构

    public static final String PBC_ASSETTYPE3_L07= "L07";//非银行业金融机构-中央交易对手

    public static final String PBC_ASSETTYPE3_L08= "L08";//银行非保本理财

    public static final String PBC_ASSETTYPE3_L09= "L09";//信托公司资管产品

    public static final String PBC_ASSETTYPE3_L10= "L10";//证券公司及其子公司资管产品

    public static final String PBC_ASSETTYPE3_L11= "L11";//基金管理公司及其子公司专户

    public static final String PBC_ASSETTYPE3_L12= "L12";//期货公司及其子公司资管产品

    public static final String PBC_ASSETTYPE3_L13= "L13";//保险资管产品

    public static final String PBC_ASSETTYPE3_L14= "L14";//金融资产投资公司资管产品

    public static final String PBC_ASSETTYPE3_L15= "L15";//公募基金

    public static final String PBC_ASSETTYPE3_L16= "L16";//私募机构私募基金

    public static final String PBC_ASSETTYPE3_L99= "L99";//其他特定目的载体

    public static final String PBC_ASSETTYPE3_L98= "L98";//境外

    public static final String PBC_ASSETTYPE3_R02= "R02";//其他

    public static final String PBC_ASSETTYPE3_Q01= "Q01";//贷款收益权

    public static final String PBC_ASSETTYPE3_Q02= "Q02";//债务证券收益权

    public static final String PBC_ASSETTYPE3_Q03= "Q03";//应收账款收益权

    public static final String PBC_ASSETTYPE3_Q04= "Q04";//其他债权收益权

    public static final String PBC_ASSETTYPE3_Q05= "Q05";//股票收益权

    public static final String PBC_ASSETTYPE3_Q06= "Q06";//其他股权收益权

    public static final String PBC_ASSETTYPE3_Q07= "Q07";//物权资产收益权

    public static final String PBC_ASSETTYPE3_G01= "G01";//非金融企业股票

    public static final String PBC_ASSETTYPE3_G02= "G02";//金融机构股票

    public static final String PBC_ASSETTYPE3_M01= "M01";//银行非保本理财

    public static final String PBC_ASSETTYPE3_M02= "M02";//信托公司资管产品

    public static final String PBC_ASSETTYPE3_M03= "M03";//证券公司及其子公司资管产品

    public static final String PBC_ASSETTYPE3_M04= "M04";//基金管理公司及其子公司专户

    public static final String PBC_ASSETTYPE3_M05= "M05";//期货公司及其子公司资管产品

    public static final String PBC_ASSETTYPE3_M06= "M06";//保险资管产品

    public static final String PBC_ASSETTYPE3_M07= "M07";//金融资产投资公司资管产品

    public static final String PBC_ASSETTYPE3_M08= "M08";//公募基金

    public static final String PBC_ASSETTYPE3_M09= "M09";//私募机构私募基金

    public static final String PBC_ASSETTYPE3_M99= "M99";//其他特定目的载体

    public static final String PBC_ASSETTYPE3_O01= "O01";//非金融企业股权

    public static final String PBC_ASSETTYPE3_O02= "O02";//金融机构股权

    public static final String PBC_ASSETTYPE3_T04= "T04";//境外股权

    public static final String PBC_ASSETTYPE3_Y01= "Y01";//企业债券

    public static final String PBC_ASSETTYPE3_Y02= "Y02";//公司债

    public static final String PBC_ASSETTYPE3_Y03= "Y03";//非金融企业债务融资工具

    public static final String PBC_ASSETTYPE3_J01= "J01";//境内资产

    public static final String PBC_ASSETTYPE3_J02= "J02";//境外资产

    /* 负债金融衍生品境内外枚举值新增 add by gaojian 2019-07-22 begin*/
    public static final String PBC_ASSETTYPE3_J03= "J03";//境内负债

    public static final String PBC_ASSETTYPE3_J04= "J04";//境外负债
    /* 负债金融衍生品境内外枚举值新增 add by gaojian 2019-07-22 end*/

    public static final String PBC_ASSETTYPE3_H01= "H01";//黄金

    public static final String PBC_ASSETTYPE3_F01= "F01";//非金融资产

    public static final String PBC_ASSETTYPE3_P11= "P11";//应收账款

    public static final String PBC_ASSETTYPE3_P12= "P12";//应付账款

    public static final String PBC_ASSETTYPE3_P13= "P13";//其他负债

    public static final String PBC_ASSETTYPE1_P14= "P14";//减值

    public static final String PBC_ASSETTYPE3_Z01= "Z01";//存款

    public static final String PBC_ASSETTYPE3_Z02= "Z02";//可转让存单

    public static final String PBC_ASSETTYPE3_Z03= "Z03";//债务证券

    public static final String PBC_ASSETTYPE3_Z04= "Z04";//除回购和拆借外贷款

    public static final String PBC_ASSETTYPE3_Z05= "Z05";//回购和拆借

    public static final String PBC_ASSETTYPE3_Z06= "Z06";//资产收益权

    public static final String PBC_ASSETTYPE3_Z07= "Z07";//股权及特定目的载体份额

    public static final String PBC_ASSETTYPE3_Z08= "Z08";//金融衍生品

    public static final String PBC_ASSETTYPE3_Z09= "Z09";//应收账款

    public static final String PBC_ASSETTYPE3_Z10= "Z10";//其他

    public static final String PBC_INDUSTRY_TYPE1_01= "01";//农、林、牧、渔业
    public static final String PBC_INDUSTRY_TYPE1_02= "02";//采矿业
    public static final String PBC_INDUSTRY_TYPE1_03= "03";//制造业
    public static final String PBC_INDUSTRY_TYPE1_04= "04";//电力、热力、燃气及水生产和供应业
    public static final String PBC_INDUSTRY_TYPE1_05= "05";//建筑业
    public static final String PBC_INDUSTRY_TYPE1_06= "06";//批发和零售业
    public static final String PBC_INDUSTRY_TYPE1_07= "07";//交通运输、仓储和邮政业
    public static final String PBC_INDUSTRY_TYPE1_08= "08";//住宿和餐饮业
    public static final String PBC_INDUSTRY_TYPE1_09= "09";//信息传输、软件和信息技术服务业
    public static final String PBC_INDUSTRY_TYPE1_10= "10";//金融业
    public static final String PBC_INDUSTRY_TYPE1_11= "11";//房地产业
    public static final String PBC_INDUSTRY_TYPE1_12= "12";//租赁和商务服务业
    public static final String PBC_INDUSTRY_TYPE1_13= "13";//科学研究和技术服务业
    public static final String PBC_INDUSTRY_TYPE1_14= "14";//水利、环境和公共设施管理业
    public static final String PBC_INDUSTRY_TYPE1_15= "15";//居民服务、修理和其他服务业
    public static final String PBC_INDUSTRY_TYPE1_16= "16";//教育
    public static final String PBC_INDUSTRY_TYPE1_17= "17";//卫生和社会工作
    public static final String PBC_INDUSTRY_TYPE1_18= "18";//文化、体育和娱乐业
    public static final String PBC_INDUSTRY_TYPE1_19= "19";//公共管理、社会保障和社会组织
    public static final String PBC_INDUSTRY_TYPE1_20= "20";//国际组织

    public static final String PBC_PROD_VARIETY_01= "01";//产品品种-01银行非保本理财

    public static final String PBC_GROUPTYPE_3_3_01="ASSET";
    public static final String PBC_GROUPTYPE_3_3_02="DEBT";

    /**
     * 映射类型-地区add by ZHANGZHONGLIANG 2019-03-26
     */
    public static final String MAPPINGCODE_BUYPLACE = "MAPPINGCODE_BUYPLACE";

    /**
     * 企业规模  01 中小微企业/02 大型企业/99 其他 add by weiyuqiang 2019-05-11
     */
    public static final String COMPANY_SIZE_01 = "01";

    /**
     * 企业规模  01 中小微企业/02 大型企业/99 其他 add by weiyuqiang 2019-05-11
     */
    public static final String COMPANY_SIZE_02 = "02";

    /**
     * 企业规模  01 中小微企业/02 大型企业/99 其他 add by weiyuqiang 2019-05-11
     */
    public static final String COMPANY_SIZE_99 = "99";

    /**
     * 企业规模  (数据规范化变更)01 中型企业/02 大型企业/03 小型企业/04 微型企业/99 其他     add by xuqihang 2019-11-18
     */
    public static final String SVR_INSTIT_SIZE_01 = "01";

    /**
     * 企业规模  (数据规范化变更)01 中型企业/02 大型企业/03 小型企业/04 微型企业/99 其他     add by xuqihang 2019-11-18
     */
    public static final String SVR_INSTIT_SIZE_02 = "02";

    /**
     * 企业规模  (数据规范化变更)01 中型企业/02 大型企业/03 小型企业/04 微型企业/99 其他     add by xuqihang 2019-11-18
     */
    public static final String SVR_INSTIT_SIZE_03 = "03";

    /**
     * 企业规模  (数据规范化变更)01 中型企业/02 大型企业/03 小型企业/04 微型企业/99 其他     add by xuqihang 2019-11-18
     */
    public static final String SVR_INSTIT_SIZE_04 = "04";

    /**
     * 企业规模  (数据规范化变更)01 中型企业/02 大型企业/03 小型企业/04 微型企业/99 其他     add by xuqihang 2019-11-18
     */
    public static final String SVR_INSTIT_SIZE_99 = "99";

    // 按地区划分
    public static final String PBC_PROVINCE_ZJa00000 = "ZJ";//总计
    public static final String PBC_PROVINCE_BJa01000 = "110000";//北京
    public static final String PBC_PROVINCE_TJa02000 = "120000";//天津
    public static final String PBC_PROVINCE_HBa03000 = "130000";//河北
    public static final String PBC_PROVINCE_SXa04000 = "140000";//山西
    public static final String PBC_PROVINCE_NMGa05000 = "150000";//内蒙古
    public static final String PBC_PROVINCE_LNa06000 = "210000";//辽宁
    public static final String PBC_PROVINCE_QZDLa06001 = "210200";//其中：大连
    public static final String PBC_PROVINCE_JLa07000 = "220000";//吉林
    public static final String PBC_PROVINCE_HLJa08000 = "230000";//黑龙江
    public static final String PBC_PROVINCE_SHa09000 = "310000";//上海
    public static final String PBC_PROVINCE_JSa10000 = "320000";//江苏
    public static final String PBC_PROVINCE_ZJa11000 = "330000";//浙江
    public static final String PBC_PROVINCE_QZNBa11001 = "330200";//其中：宁波
    public static final String PBC_PROVINCE_AHa12000 = "340000";//安徽
    public static final String PBC_PROVINCE_FJa13000 = "350000";//福建
    public static final String PBC_PROVINCE_QZSMa13001 = "350200";//其中：厦门
    public static final String PBC_PROVINCE_JXa14000 = "360000";//江西
    public static final String PBC_PROVINCE_SDa15000 = "370000";//山东
    public static final String PBC_PROVINCE_QZQDa15001 = "370200";//其中：青岛
    public static final String PBC_PROVINCE_HNa16000 = "410000";//河南
    public static final String PBC_PROVINCE_HBa17000 = "420000";//湖北
    public static final String PBC_PROVINCE_HNa18000 = "430000";//湖南
    public static final String PBC_PROVINCE_GDa19000 = "440000";//广东
    public static final String PBC_PROVINCE_QZSZa19001 = "440300";//其中：深圳
    public static final String PBC_PROVINCE_GXa20000 = "450000";//广西
    public static final String PBC_PROVINCE_HNa21000 = "460000";//海南
    public static final String PBC_PROVINCE_ZQa22000 = "500000";//重庆
    public static final String PBC_PROVINCE_SCa23000 = "510000";//四川
    public static final String PBC_PROVINCE_GZa24000 = "520000";//贵州
    public static final String PBC_PROVINCE_YNa25000 = "530000";//云南
    public static final String PBC_PROVINCE_XZa26000 = "540000";//西藏
    public static final String PBC_PROVINCE_SXa27000 = "610000";//陕西
    public static final String PBC_PROVINCE_GSa28000 = "620000";//甘肃
    public static final String PBC_PROVINCE_QHa29000 = "630000";//青海
    public static final String PBC_PROVINCE_NXa30000 = "640000";//宁夏
    public static final String PBC_PROVINCE_XJa31000 = "650000";//新疆


    // 农、林、牧、渔业
    public static final String SVR_INDUSTRY_DOOR_CODE_A = "A";
    // 采矿业
    public static final String SVR_INDUSTRY_DOOR_CODE_B = "B";
    // 制造业
    public static final String SVR_INDUSTRY_DOOR_CODE_C = "C";
    // 电力、热力、燃气及水生产和供应业
    public static final String SVR_INDUSTRY_DOOR_CODE_D = "D";
    // 建筑业
    public static final String SVR_INDUSTRY_DOOR_CODE_E = "E";
    // 批发和零售业
    public static final String SVR_INDUSTRY_DOOR_CODE_F = "F";
    // 交通运输、仓储和邮政业
    public static final String SVR_INDUSTRY_DOOR_CODE_G = "G";
    // 住宿和餐饮业
    public static final String SVR_INDUSTRY_DOOR_CODE_H = "H";
    // 信息传输、软件和信息技术服务业
    public static final String SVR_INDUSTRY_DOOR_CODE_I = "I";
    // 金融业
    public static final String SVR_INDUSTRY_DOOR_CODE_J = "J";
    // 房地产
    public static final String SVR_INDUSTRY_DOOR_CODE_K = "K";
    // 租赁和商务服务业
    public static final String SVR_INDUSTRY_DOOR_CODE_L = "L";
    // 科学研究和技术服务业
    public static final String SVR_INDUSTRY_DOOR_CODE_M = "M";
    // 水利、环境和公共设施管理业
    public static final String SVR_INDUSTRY_DOOR_CODE_N = "N";
    // 居民服务、修理和其他服务业
    public static final String SVR_INDUSTRY_DOOR_CODE_O = "O";
    // 教育
    public static final String SVR_INDUSTRY_DOOR_CODE_P = "P";
    // 卫生和社会工作
    public static final String SVR_INDUSTRY_DOOR_CODE_Q = "Q";
    // 文化、体育和娱乐业
    public static final String SVR_INDUSTRY_DOOR_CODE_R = "R";
    // 公共管理、社会保障和社会组织
    public static final String SVR_INDUSTRY_DOOR_CODE_S = "S";
    // 国际组织
    public static final String SVR_INDUSTRY_DOOR_CODE_T = "T";
    // 住户
    public static final String SVR_DEPT_TYPE_1 = "1";
    // 广义政府
    public static final String SVR_DEPT_TYPE_2 = "2";
    // 非金融企业
    public static final String SVR_DEPT_TYPE_3 = "3";
    // 金融机构（实体）
    public static final String SVR_DEPT_TYPE_4 = "4";
    // 特定目的载体
    public static final String SVR_DEPT_TYPE_5 = "5";
    // 境外
    public static final String SVR_DEPT_TYPE_6 = "6";

}
