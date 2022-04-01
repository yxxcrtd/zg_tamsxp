/*
 * 文件名：DictCodeList.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangrui1
 * 修改时间：2020年7月8日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.background.component.order.exchange.common.constant;

import com.joyintech.background.component.common.pack.common.base.BaseDictCode;
import com.joyintech.background.component.common.pack.common.base.BaseDictCodeList;
import com.joyintech.background.component.common.pack.common.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典明细
 * 
 * @author zhangrui1
 * @version 1.0 2020年7月8日
 * @see DictCodeList
 * @since 1.0
 */
public enum DictCodeList implements BaseDictCodeList {

    /**
     * 指令类型_单组合单资产投资指令
     */
    指令类型_单组合单资产投资指令(
        DictCode.指令类型, "01", "单组合单资产投资指令"
    ),
    /**
     * 指令业务类型_股票竞价
     */
    指令业务类型_股票竞价(
        DictCode.指令业务类型, "01", "股票竞价指令"
    ),
    /**
     * 指令业务类型_配股
     */
    指令业务类型_配股(
        DictCode.指令业务类型, "10", "配股"
    ),
    /**
     * 指令业务类型_新股
     */
    指令业务类型_新股(
        DictCode.指令业务类型, "11", "新股"
    ),
    /**
     * 指令业务类型_债券竞价
     */
    指令业务类型_债券竞价(
        DictCode.指令业务类型, "07", "债券竞价指令"
    ),
    /**
     * 指令业务类型_大宗债券质押回购指令
     */
    指令业务类型_大宗债券质押回购(
        DictCode.指令业务类型, "03", "大宗债券质押回购指令"
    ),
    /**
     * 指令业务类型_大宗债券指令
     */
    指令业务类型_大宗债券(
        DictCode.指令业务类型, "06", "大宗债券指令"
    ),
    /**
     * 指令业务类型_大宗基金指令
     */
    指令业务类型_大宗基金(
        DictCode.指令业务类型, "05", "大宗基金指令"
    ),
    /**
     * 指令业务类型_大宗股票指令
     */
    指令业务类型_大宗股票(
        DictCode.指令业务类型, "04", "大宗股票指令"
    ),
    /**
     * 指令业务类型_回购竞价
     */
    指令业务类型_回购竞价(
        DictCode.指令业务类型, "ET15", "回购竞价"
    ),
    /**
     * 指令业务类型_上海固收平台现券指令
     */
    指令业务类型_上海固收平台现券(
        DictCode.指令业务类型, "08", "上交所固定收益平台现券交易指令"
    ),
    /**
     * 指令业务类型_标准券入出库
     */
    指令业务类型_标准券入出库(
        DictCode.指令业务类型, "ET17", "标准券入出库"
    ),
    /**
     * 指令业务类型_基金竞价
     */
    指令业务类型_基金竞价(
        DictCode.指令业务类型, "02", "基金竞价指令"
    ),
    /**
     * 指令业务类型_债券回售
     */
    指令业务类型_债券回售(
        DictCode.指令业务类型, "09", "债券回售"
    ),
    /**
     * 指令业务类型_转债申购
     */
    指令业务类型_转债申购(
        DictCode.指令业务类型, "13", "转债申购"
    ),
    /**
     * 指令业务类型_配债
     */
    指令业务类型_配债(
        DictCode.指令业务类型, "12", "配债"
    ),
    /**
     * 指令业务类型_债转股
     */
    指令业务类型_债转股(
        DictCode.指令业务类型, "ET22", "债转股"
    ),
    /**
     * 指令业务类型_质押回购竞价
     */
    指令业务类型_质押回购竞价(
        DictCode.指令业务类型, "ET23", "质押回购竞价"
    ),
    /**
     * 指令业务类型_协议回购首期指令
     */
    指令业务类型_协议回购首期指令(
        DictCode.指令业务类型, "17", "协议回购首期指令"
    ),
    /**
     * 指令业务类型_协议回购提前购回指令
     */
    指令业务类型_协议回购提前购回指令(
        DictCode.指令业务类型, "18", "协议回购提前购回指令"
    ),
    /**
     * 指令业务类型_协议回购到期购回指令
     */
    指令业务类型_协议回购到期购回指令(
        DictCode.指令业务类型, "19", "协议回购到期购回指令"
    ),
    /**
     * 指令业务类型_协议回购到期续做指令
     */
    指令业务类型_协议回购到期续做指令(
        DictCode.指令业务类型, "20", "协议回购到期续做指令"
    ),
    /**
     * 指令业务类型_协议回购中途换券指令
     */
    指令业务类型_协议回购中途换券指令(
        DictCode.指令业务类型, "21", "协议回购中途换券指令"
    ),
    /**
     * 指令业务类型_协议回购解除质押指令
     */
    指令业务类型_协议回购解除质押指令(
        DictCode.指令业务类型, "22", "协议回购解除质押指令"
    ),
    /**
     * 指令业务类型_协议回购解除质押指令
     */
    指令业务类型_期货指令(
        DictCode.指令业务类型, "23", "期货指令"
    ),
    /**
     * 指令状态_新建
     */
    指令状态_新建(
        DictCode.指令状态, "01", "新建"
    ),
    /**
     * 指令状态_待接收
     */
    指令状态_待接收(
        DictCode.指令状态, "02", "待接收"
    ),
    /**
     * 指令状态_已接收
     */
    指令状态_已接收(
        DictCode.指令状态, "03", "已接收"
    ),
    /**
     * 指令状态_已完成
     */
    指令状态_已完成(
        DictCode.指令状态, "04", "已完成"
    ),
    /**
     * 指令状态_已退回
     */
    指令状态_已退回(
        DictCode.指令状态, "05", "已退回"
    ),
    /**
     * 指令状态_已关闭
     */
    指令状态_已关闭(
        DictCode.指令状态, "06", "已关闭"
    ),
    /**
     * 指令状态_已撤回
     */
    指令状态_已撤回(
        DictCode.指令状态, "07", "已撤回"
    ),
    /**
     * 指令状态_待分发
     */
    指令状态_待分发(
        DictCode.指令状态, "09", "待分发"
    ),
    /**
     * 币种_人民币
     */
    币种_人民币(
        DictCode.指令状态, "CNY", "人民币"
    ),
    /**
     * 价格模式_指定价格
     */
    价格模式_指定价格(
        DictCode.价格模式, "PRM01", "指定价格"
    ),
    /**
     * 价格模式_限价指令
     */
    价格模式_限价指令(
        DictCode.价格模式, "PRM02", "限价指令"
    ),
    /**
     * 价格模式_市价指令
     */
    价格模式_市价指令(
        DictCode.价格模式, "PRM03", "市价指令"
    ),
    /**
     * 价格模式_加权价格+BP
     */
    价格模式_加权价格_BP(
        DictCode.价格模式, "PRM04", "加权价格+BP"
    ),
    /**
     * 价格模式_不指定
     */
    价格模式_不指定(
        DictCode.价格模式, "PRM05", "不指定"
    ),
    /**
     * 价格模式_意向申报
     */
    价格模式_意向申报(
        DictCode.价格模式, "PRM06", "意向申报"
    ),
    /**
     * 价格模式_定价申报
     */
    价格模式_定价申报(
        DictCode.价格模式, "PRM07", "定价申报"
    ),
    /**
     * 价格模式_点击成交申报
     */
    价格模式_点击成交申报(
        DictCode.价格模式, "PRM08", "点击成交申报"
    ),
    /**
     * 价格模式_成交申报
     */
    价格模式_成交申报(
        DictCode.价格模式, "PRM09", "成交申报"
    ),
    /**
     * 价格模式_
     */
    价格模式_收盘价固定价格(
        DictCode.价格模式, "PRM10", "收盘价固定价格"
    ),
    /**
     * 价格模式_日均价固定价格申报
     */
    价格模式_日均价固定价格申报(
        DictCode.价格模式, "PRM11", "日均价固定价格申报"
    ),
    回购拆借方向_正回购(DictCode.回购拆借方向,"03","正回购"),
	回购拆借方向_逆回购(DictCode.回购拆借方向,"04","逆回购"),

    // add by wanshiwei 20210922：指令创建来源 start
    指令创建来源_场内簿记(DictCode.指令创建来源, "01", "场内簿记"),
    指令创建来源_场内实时(DictCode.指令创建来源, "02", "场内实时"),
    指令创建来源_银行间簿记(DictCode.指令创建来源, "03", "银行间簿记"),
    指令创建来源_银行间实时(DictCode.指令创建来源, "04", "银行间实时"),
    // add by wanshiwei 20210922：指令创建来源 end
    ;

    /**
     * 对应于数据字典code
     */
    private String value;

    /**
     * 数据字典枚举
     */
    private BaseDictCode dictCode;

    /**
     * 明细名称
     */
    private String codeName;

    DictCodeList(BaseDictCode dictCode, String value, String codeName) {
        this.dictCode = dictCode;
        this.value = value;
        this.setCodeName(codeName);
    }

    /**
     * 获得数据字典详细类
     *
     * @param value 数据字典值
     * @param dictCode 数据字典DictCode
     * @return DictCodeList
     * @see
     * @since 1.0
     */
    public static DictCodeList from(BaseDictCode dictCode, String value) {
        DictCodeList[] values = DictCodeList.values();
        for (DictCodeList code : values) {
            if (StringUtil.equals(value, code.getValue()) && code.getDictCode().equals(dictCode)) {
                return code;
            }
        }
        return null;
    }

    /**
     * 获取数据字典明细valueList
     *
     * @param dictCode DictCode
     * @return List&lt;SelectOptionDto&gt;
     * @see
     * @since 1.0
     */
    public static List<String> valuesfromDictCode(BaseDictCode dictCode) {
        List<String> result = new ArrayList<>();
        DictCodeList[] values = DictCodeList.values();
        for (DictCodeList code : values) {
            if (code.getDictCode() == dictCode) {
                result.add(code.getValue());
            }
        }
        return result;
    }

    /**
     * 获取 DictCode
     *
     * @return value.
     */
    @Override
    public BaseDictCode getDictCode() {
        return dictCode;
    }

    /**
     * 获取 value
     *
     * @return value.
     */
    @Override
    public String getValue() {
        return value;
    }
    /**
     * 设置 value
     *
     * @param value value
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取 codeName
     *
     * @return codeName.
     */
    @Override
    public String getCodeName() {
        return codeName;
    }

    /**
     * 获取 带有引号的名称
     *
     * @return 带有引号的名称.
     */
    public String getQuotesCodeName() {
        return "“" + codeName + "”";
    }

    /**
     * 获取Integer value
     *
     * @return Integer value
     */
    public Integer getIntegerCodeName() {
        Integer result = null;
        try {
            result = Integer.parseInt(codeName);
        }
        catch (Exception e) {
            // nothing to do
        }
        return result;
    }

    /**
     * 获取Integer value
     *
     * @return Integer value
     */
    public BigDecimal getBigDecimalCodeName() {
        BigDecimal result = null;
        try {
            result = new BigDecimal(codeName);
        }
        catch (Exception e) {
            // nothing to do
        }
        return result;
    }

    /**
     * 设置 codeName
     *
     * @param codeName codeName
     */
    @Override
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
