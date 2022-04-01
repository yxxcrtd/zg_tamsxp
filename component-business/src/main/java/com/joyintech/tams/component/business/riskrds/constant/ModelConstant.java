/*
 * 文件名：ModelConstant.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangrui1
 * 修改时间：2020年9月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.component.business.riskrds.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.joyintech.tams.component.business.riskrds.dto.MtdSetting;
import com.joyintech.tams.component.business.riskrds.dto.PageModel;
import com.joyintech.tams.component.business.riskrds.dto.Reactor;
import com.joyintech.tams.component.business.riskrds.dto.Setting;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;

/**
 * 画面对应接口映射
 * 
 * @author zhangrui1
 * @version 1.0 2020年9月19日
 * @see ModelConstant
 * @since 1.0
 */
public final class ModelConstant {

    public static final String ETREXCHANGEIMPORT = "EtrExchangeImport";

    public static final String ETREXCHANGETRADEMULTI = "EtrExchangeTradeMulti001";

    /*************************************************************/
    /** *接口字段区域 * */
    /*************************************************************/
    public static final String INF_EVENTDATE = "eventDate";

    public static final String INF_EVENTID = "eventId";

    public static final String INF_PORTFOLIOCODE = "portfolioCode";

    public static final String INF_ASSETCODE = "assetCode";

    public static final String INF_ASSETTYPE = "assetType";

    public static final String INF_ASSETTYPE2 = "assetType2";

    public static final String INF_PS = "ps";

    public static final String INF_AMOUNT = "amount";

    public static final String INF_PRICEAMT = "priceAmt";

    public static final String INF_TRADENUM = "tradeNum";

    public static final String INF_NETPRICE = "netPrice";

    public static final String INF_ALLPRICE = "allPrice";

    public static final String INF_UNITNET = "unitNet";

    public static final String INF_ASSETRATE = "assetRate";

    public static final String INF_EVENTTYPE = "eventType";

    public static final String INF_ENTRUSTCODE = "entrustCode";

    public static final String INF_TRADER = "trader";

    public static final String INF_MANAGER = "manager";

    public static final String INF_DELIVERYDATE = "deliveryDate";

    public static final String INF_STARTDATE = "startDate";

    public static final String INF_ENDDATE = "endDate";

    public static final String INF_ISWARNNEEDCONFIRM = "isWarnNeedConfirm";

    public static final String INF_ISAPPRNEEDCONFIRM = "isApprNeedConfirm";

    public static final String INF_PREOCCUPYCODE = "preOccupyCode";

    public static final String INF_SOURCESYS = "sourceSys";

    public static final String INF_COUNTERID = "counterId";

    public static final String INF_MARKETCODE = "marketCode";

    public static final String INF_TRADEPLACE = "tradePlace";

    public static final String INF_CCY = "ccy";

    public static final String INF_MDATE = "mdate";

    public static final String INF_RSK_CALC_LIST = "rskCalcList";

    public static final String INF_RSK_LIST = "RSK_LIST";

    public static final String INF_CODE = "infCode";

    public static final String INF_REVOKEFLG = "revokeFlg";

    public static final String INF_ISINNERTRD = "isInnerTrd";

    public static final String INF_OCCUPYDATE = "occupyDate";

    public static final String INF_TOTALFACEAMT = "totalFaceAmt";

    public static final String INF_VARIETYCODE = "varietyCode";

    public static final String INF_CALENDARCODE = "calendarCode";

    public static final String INF_INVAIM = "invAim";

    public static final String INF_GROUP_ID = "groupId";

    public static final String INF_IS_SYNC = "isSync";

    public static final String INF_YIELDENDRATE = "yieldEndRate";

    /*************************************************************/
    /** *△接口字段区域△* */
    /*************************************************************/

    public static final String ENTRUST_ID = "entrustId";

    public static final String ENTRUST_AMT = "entrustAmt";

    public static final String ENTRUST_NUM = "entrustNum";

    public static final String ENTRUST_PRICE = "entrustPrice";

    public static final String PORTFOLIO_ID = "portfolioId";

    public static final String VDATE = "vdate";

    public static final String CALENDARID = "calendarId";

    public static final String FINPRODTYPE2 = "finprodType2";

    public static final String FINPRODID = "finprodId";

    public static final String CONFINPRODID = "conFinprodId";

    private ModelConstant() {
        // private this construct
    }

    private static final List<String> NEED_REVERSE_PS = Arrays.asList(
        DictCodeListExt1.交易方向_债转股.getValue(), //
        DictCodeListExt2.交易方向_转换申请.getValue(), //
        DictCodeListExt2.交易方向_转换赎回申请.getValue());

    private static final Map<String, PageModel> PAGEMODEL = new HashMap<>();
    static {
        Map<String, String> modelMap = new HashMap<>();
        // 事件类型
        modelMap.put(INF_EVENTTYPE, INF_EVENTTYPE);
        // 指令编号
        modelMap.put(ENTRUST_ID, INF_EVENTID);
        // 组合代码
        modelMap.put(PORTFOLIO_ID, INF_PORTFOLIOCODE);
        // 资产代码
        modelMap.put(FINPRODID, INF_ASSETCODE);
        // 资产类型
        modelMap.put(FINPRODTYPE2, INF_ASSETTYPE);
        // 交易方向
        modelMap.put(INF_PS, INF_PS);
        // 指令价格
        modelMap.put(ENTRUST_PRICE, INF_PRICEAMT);
        // 全价
        modelMap.put("fprice", INF_ALLPRICE);
        // 收益率
        modelMap.put("yield", INF_YIELDENDRATE);
        // 指令数量
        modelMap.put(ENTRUST_NUM, INF_TRADENUM);
        // 指令金额
        modelMap.put(ENTRUST_AMT, INF_AMOUNT);
        // 指令开始日期
        modelMap.put(VDATE, INF_EVENTDATE);
        // 指令结束日期
        modelMap.put(INF_MDATE, INF_OCCUPYDATE);
        // 券面总额
        modelMap.put(ENTRUST_AMT, INF_TOTALFACEAMT);
        // 日历代码
        modelMap.put(CALENDARID, INF_CALENDARCODE);
        // 设置方向交易配置
        PAGEMODEL.put(ETREXCHANGEIMPORT, new PageModel(ETREXCHANGEIMPORT, modelMap));
        PAGEMODEL.put(ETREXCHANGETRADEMULTI, new PageModel(ETREXCHANGETRADEMULTI, modelMap));

        for(String key: PAGEMODEL.keySet()) {
            PageModel model = PAGEMODEL.get(key);
            // 反向交易相关配置
            model.setReverse(BussinessDictCodeList.交易方向_卖出.getValue(),
                    BussinessDictCodeList.交易方向_买入.getValue(), NEED_REVERSE_PS);
            // 事件类型
            model.addReverse(INF_EVENTTYPE, INF_EVENTTYPE);
            // 指令编号
            model.addReverse(ENTRUST_ID, INF_EVENTID);
            // 组合代码
            model.addReverse(PORTFOLIO_ID, INF_PORTFOLIOCODE);
            // 资产代码(债转股的股票)
            model.addReverse(CONFINPRODID, INF_ASSETCODE);
            // 资产类型
            modelMap.put(FINPRODTYPE2, INF_ASSETTYPE);
            // 交易方向
            model.addReverse(INF_PS, INF_PS);
            // 指令价格
            model.addReverse(ENTRUST_PRICE, INF_PRICEAMT);
            // 全价
            model.addReverse("fprice", INF_ALLPRICE);
            // 收益率
            model.addReverse("yield", INF_YIELDENDRATE);
            // 指令数量
            model.addReverse(ENTRUST_NUM, INF_TRADENUM);
            // 指令金额
            /** model.addReverse(ENTRUST_AMT, INF_AMOUNT); */
            // 指令开始日期
            model.addReverse(VDATE, INF_EVENTDATE);
            // 指令结束日期
            model.addReverse(INF_MDATE, INF_OCCUPYDATE);
            // 券面总额
            model.addReverse(ENTRUST_AMT, INF_TOTALFACEAMT);
            // 日历代码
            model.addReverse(CALENDARID, INF_CALENDARCODE);
            // 组合代码联动
            Set<Setting<?>> settings = new HashSet<>();
            MtdSetting setting = new MtdSetting(true, null, null);
            setting.setPortfolio(true);
            settings.add(setting);
            Reactor reactor = new Reactor(PORTFOLIO_ID, PORTFOLIO_ID, false, settings);
            model.addReactor(reactor);
            // 不需要处理的投资品种
            model.addNoNeedAsset(DictCodeListExt3.交易方向_质押券入库.getValue(),
                    DictCodeListExt3.交易方向_质押券出库.getValue());
        }
    }

    public static final Map<String, PageModel> modelMap() {
        return PAGEMODEL;
    }

    protected static final Map<String, String> ASSETTYPE = new HashMap<>();
    static {
        // 债券
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券.getValue(),
            DictCodeList.平安资产类型_债券.getValue());
        // 质押式回购
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券质押式正回购.getValue(),
            DictCodeList.平安资产类型_质押式回购.getValue());
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券质押式逆回购.getValue(),
            DictCodeList.平安资产类型_质押式回购.getValue());
        // 买断式回购
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券买断式正回购.getValue(),
            DictCodeList.平安资产类型_买断式回购.getValue());
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券买断式逆回购.getValue(),
            DictCodeList.平安资产类型_买断式回购.getValue());
        // 基金
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_基金.getValue(),
            DictCodeList.平安资产类型_基金.getValue());
        // 存款
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_存款.getValue(),
            DictCodeList.平安资产类型_存款.getValue());
        // 拆借
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_拆入.getValue(),
            DictCodeList.平安资产类型_拆借.getValue());
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_拆出.getValue(),
            DictCodeList.平安资产类型_拆借.getValue());
        // 股票
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_股票.getValue(),
            DictCodeListExt2.平安资产类型_股票.getValue());
        // 即期
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_即期.getValue(),
            DictCodeListExt2.平安资产类型_外汇即期.getValue());
        // 远期
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_远期.getValue(),
            DictCodeListExt2.平安资产类型_外汇远期.getValue());
        // 掉期
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_掉期.getValue(),
            DictCodeListExt2.平安资产类型_外汇掉期.getValue());
        // 利率互换
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_利率互换.getValue(),
            DictCodeListExt2.平安资产类型_利率互换.getValue());
        // 利率互换
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券借贷.getValue(),
            DictCodeListExt2.平安资产类型_债券借贷.getValue());
        // 非标债权资产
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_项目类资产.getValue(),
            DictCodeList.平安资产类型_项目类资产.getValue());
        // 非标债权资产
        ASSETTYPE.put(DictCodeListExt3.金融产品类型_投管用_权益类资产.getValue(),
            DictCodeListExt3.平安资产类型_权益类资产.getValue());
        // 一级投标债券
        ASSETTYPE.put(DictCodeListExt2.金融产品类型_投管用_一级投标债券.getValue(),
            DictCodeListExt2.平安资产类型_一级市场债.getValue());
        // 债券借贷
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券借贷融入.getValue(),
            DictCodeListExt2.平安资产类型_债券借贷.getValue());
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券借贷融出.getValue(),
            DictCodeListExt2.平安资产类型_债券借贷.getValue());
        ASSETTYPE.put(DictCodeListExt1.金融产品类型_投管用_债券借贷.getValue(),
            DictCodeListExt2.平安资产类型_债券借贷.getValue());
    }

    public static final String assetType(String finprodType2) {
        return ASSETTYPE.get(finprodType2);
    }

    protected static final Map<String, String> PSMAP = new HashMap<>();
    static {
        PSMAP.put(DictCodeListExt3.交易方向_配债.getValue(), DictCodeList.交易方向_买入.getValue());
        PSMAP.put(DictCodeListExt2.交易方向_配股.getValue(), DictCodeList.交易方向_买入.getValue());
        PSMAP.put(DictCodeListExt3.交易方向_质押券入库.getValue(), DictCodeList.交易方向_买入.getValue());
        PSMAP.put(DictCodeListExt3.交易方向_质押券出库.getValue(), DictCodeList.交易方向_卖出.getValue());
    }

    public static final String toPs(String ps) {
        return PSMAP.get(ps);
    }
}
