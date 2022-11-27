package com.joyintech.tams.constant;

import com.joyintech.framework.base.BaseDictCodeList;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典明细枚举
 *
 * @author chenzhuo
 * @version 1.0 2016年11月12日
 * @see DictCodeListExt4
 * @since 1.0
 */
public enum DictCodeListExt4 implements BaseDictCodeList {

    会计分录类型_按科目分录(DictCode.会计分录类型,"01","按科目分录"),
    会计分录类型_按内部科目编码分录(DictCode.会计分录类型,"02","按内部科目编码分录"),

    /**
     * 流动性风险应对措施_
     */
    流动性风险应对措施_设定单一投资者认购金额上限(DictCode.流动性风险应对措施,"01","设定单一投资者认购金额上限"),
    流动性风险应对措施_设定理财产品单日净认购比例上限(DictCode.流动性风险应对措施,"02","设定理财产品单日净认购比例上限"),
    流动性风险应对措施_拒绝大额认购(DictCode.流动性风险应对措施,"03","拒绝大额认购"),
    流动性风险应对措施_暂停认购(DictCode.流动性风险应对措施,"04","暂停认购"),
    流动性风险应对措施_设置赎回上限(DictCode.流动性风险应对措施,"11","设置赎回上限"),
    流动性风险应对措施_延期办理巨额赎回申请(DictCode.流动性风险应对措施,"12","延期办理巨额赎回申请"),
    流动性风险应对措施_暂停接受赎回申请(DictCode.流动性风险应对措施,"13","暂停接受赎回申请"),
    流动性风险应对措施_延缓支付赎回款项(DictCode.流动性风险应对措施,"14","延缓支付赎回款项"),
    流动性风险应对措施_收取短期赎回费(DictCode.流动性风险应对措施,"15","收取短期赎回费"),
    流动性风险应对措施_暂停理财产品估(DictCode.流动性风险应对措施,"16","暂停理财产品估"),
    流动性风险应对措施_摆动定价(DictCode.流动性风险应对措施,"17","摆动定价"),
    流动性风险应对措施_其他措施(DictCode.流动性风险应对措施,"99","其他措施"),

    /**
     *
     */
    资产_负债_一级类别_资产(DictCode.资产_负债_一级类别,"01","资产"),
    资产_负债_一级类别_负债(DictCode.资产_负债_一级类别,"02","负债"),

    资产_负债_二级类别_现金及银行存款(DictCode.资产_负债_二级类别,"01","现金及银行存款"),
    资产_负债_二级类别_同业存单(DictCode.资产_负债_二级类别,"02","同业存单"),
    资产_负债_二级类别_拆放同业及债券买入返售(DictCode.资产_负债_二级类别,"03","拆放同业及债券买入返售"),
    资产_负债_二级类别_债券(DictCode.资产_负债_二级类别,"04","债券"),
    资产_负债_二级类别_非标准化债权类资产(DictCode.资产_负债_二级类别,"05","非标准化债权类资产"),
    资产_负债_二级类别_权益类资产(DictCode.资产_负债_二级类别,"06","权益类资产"),
    资产_负债_二级类别_金融衍生品(DictCode.资产_负债_二级类别,"07","金融衍生品 "),
    资产_负债_二级类别_代客境外理财投资QDII(DictCode.资产_负债_二级类别,"08","代客境外理财投资QDII"),
    资产_负债_二级类别_商品类资产(DictCode.资产_负债_二级类别,"09","商品类资产"),
    资产_负债_二级类别_另类资产(DictCode.资产_负债_二级类别,"10","另类资产"),
    资产_负债_二级类别_公募基金(DictCode.资产_负债_二级类别,"11","公募基金"),
    资产_负债_二级类别_私募基金(DictCode.资产_负债_二级类别,"12","私募基金"),
    资产_负债_二级类别_资产管理产品(DictCode.资产_负债_二级类别,"13","资产管理产品"),
    资产_负债_二级类别_委外投资协议方式(DictCode.资产_负债_二级类别,"14","委外投资——协议方式"),
    资产_负债_二级类别_其他资产(DictCode.资产_负债_二级类别,"15","其他（资产）"),
    资产_负债_二级类别_同业拆入及卖出回购(DictCode.资产_负债_二级类别,"16","同业拆入及卖出回购"),
    资产_负债_二级类别_其他负债(DictCode.资产_负债_二级类别,"17","其他（负债）"),

    异常标识_正常(DictCode.异常标识,"0","正常"),
    异常标识_异常(DictCode.异常标识,"1","异常"),


    ;
    /**
     * 对应于数据字典code
     */
    private String value;

    /**
     * 数据字典枚举
     */
    private DictCode dictCode;

    /**
     * 明细名称
     */
    private String codeName;

    DictCodeListExt4(DictCode dictCode, String value, String codeName) {
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
    public static DictCodeListExt4 from(DictCode dictCode, String value) {
        DictCodeListExt4[] values = DictCodeListExt4.values();
        for (DictCodeListExt4 code : values) {
            if (StringUtil.equals(value, code.getValue()) && code.getDictCode() == dictCode) {
                return code;
            }
        }
        return null;
    }

    /**
     * 转换成DICTCODELIST
     * @param dictCodeListExt2
     * @return
     */
    public static Map<String,Object> fromDictCodeList(DictCodeListExt4 dictCodeListExt2){
        Map<String,Object> retMap=new HashMap<String,Object>();
        retMap.put("codeName",dictCodeListExt2.codeName);
        retMap.put("value",dictCodeListExt2.value);
        retMap.put("dictCode",dictCodeListExt2.dictCode);
        return retMap;
    }
    /**
     * 获取数据字典明细List
     *
     * @param dictCode DictCode
     * @return List&lt;SelectOptionDto&gt;
     * @see
     * @since 1.0
     */
    public static List<SelectOptionDto> fromDictCode(DictCode dictCode) {
        List<SelectOptionDto> result = new ArrayList<>();
        DictCodeListExt4[] values = DictCodeListExt4.values();
        for (DictCodeListExt4 code : values) {
            if (code.getDictCode() == dictCode) {
                SelectOptionDto option = new SelectOptionDto();
                option.setId(code.getValue());
                option.setText(code.getCodeName());
                result.add(option);
            }
        }
        return result;
    }

    /**
     * 获取数据字典明细valueList
     *
     * @param dictCode DictCode
     * @return List&lt;SelectOptionDto&gt;
     * @see
     * @since 1.0
     */
    public static List<String> valuesfromDictCode(DictCode dictCode) {
        List<String> result = new ArrayList<>();
        DictCodeListExt4[] values = DictCodeListExt4.values();
        for (DictCodeListExt4 code : values) {
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
    public DictCode getDictCode() {
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
