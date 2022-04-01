/*
 * 文件名：PageModel.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangrui1
 * 修改时间：2020年9月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.component.business.riskrds.dto;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.framework.util.DateUtil;
import org.springframework.util.CollectionUtils;

import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.component.business.riskrds.constant.ModelConstant;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.CommonUtil;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import com.joyintech.tams.invest.common.constantrds.BussinessDictCodeListRds;

/**
 * 画面接口对应实体
 * 
 * @author zhangrui1
 * @version 1.0 2020年9月19日
 * @see PageModel
 * @since 1.0
 */
public class PageModel {

    /**
     * 画面ID
     */
    private String pageId;

    /**
     * 需要生成反向交易的交易方向（如转换申请、债转股）
     */
    private Set<String> needRvsPs;

    /**
     * 正交易方向
     */
    private String ps;

    /**
     * 反交易方向
     */
    private String reversePs;

    /**
     * 画面接口映射
     */
    private Map<String, String> pageInfMap;

    /**
     * 适用于内转、基金转换、债转股等交易的反向交易映射
     */
    private Map<String, String> pageInfMapReverse;

    /**
     * 字段联动
     */
    private List<Reactor> reactors;

    /**
     * 是否需要进行资产转换
     */
    private boolean isNeedChangeAssetType = true;

    /**
     * 不需要处理的资产类型
     */
    private Set<String> noNeedAsset;

    public PageModel(String pageId, Map<String, String> pageInfMap) {
        this.pageId = pageId;
        this.pageInfMap = pageInfMap;
    }

    public PageModel(String pageId, Map<String, String> pageInfMap, //
                     String ps, String reversePs, Set<String> needRvsPs,
                     Map<String, String> pageInfMapReverse) {
        this(pageId, pageInfMap);
        this.ps = ps;
        this.reversePs = reversePs;
        this.needRvsPs = needRvsPs;
        this.pageInfMapReverse = pageInfMapReverse;
    }

    /**
     * 添加正交易字段映射
     * 
     * @param pageKey 画面字段
     * @param infKey 接口字段
     */
    public void add(String pageKey, String infKey) {
        if (pageInfMap == null) {
            pageInfMap = new HashMap<>();
        }
        pageInfMap.put(pageKey, infKey);
    }

    /**
     * 添加反交易字段映射
     * 
     * @param pageKey 画面字段
     * @param infKey 接口字段
     */
    public void addReverse(String pageKey, String infKey) {
        if (pageInfMapReverse == null) {
            pageInfMapReverse = new HashMap<>();
        }
        pageInfMapReverse.put(pageKey, infKey);
    }

    /**
     * 取得画面ID
     * 
     * @return 画面ID
     */
    public String getPageId() {
        return pageId;
    }

    /**
     * 生成报文
     * 
     * @param dto 原数据
     * @return 报文
     */
    @SuppressWarnings("unchecked")
    public <T> List<Map<String, Object>> filter(T dto) {
        List<Map<String, Object>> resList = new ArrayList<>();
        // 内容为空返回空结果
        if (null == dto) {
            return resList;
        }
        Map<String, Object> map = null;
        if (dto instanceof Map) {
            map = (Map<String, Object>)dto;
        }
        else {
            map = BeanUtil.object2Map(dto);
        }
        // 转换的结果为空或者是不处理的投资品种返回空结果
        if (CollectionUtils.isEmpty(map) || isNoNeedAsset(map)) {
            return resList;
        }
        // 字段联动
        if (!CollectionUtils.isEmpty(reactors)) {
            for (Reactor reactor : reactors) {
                reactor.execute(map);
            }
        }
        // 生成正交易报文
        resList.add(filter(pageInfMap, map, ps));
        // 生成反交易报文
        if (isReverse(map)) {
            resList.add(filter(pageInfMapReverse, map, reversePs));
        }
        return resList;
    }

    public Map<String, Object> filter(Map<String, String> filter, Map<String, Object> dto,
                                      String ps) {
        Map<String, Object> resMap = new HashMap<>();
        // 移除内容为空的字段
        CommonUtil.removeNull(dto);
        // 移除内容为空的配置
        CommonUtil.removeNull(filter);
        if (!CollectionUtils.isEmpty(filter)) {
            // 字段copy
            filter.forEach((key, val) -> resMap.put(val, dto.get(key)));
            // 生成反方向特殊处理
            reverseCommon(resMap, dto, ps);
            // 资产类型转换（金融产品类型_投管用 -> 平安资产类型）
            changeAssetType(resMap, dto);
            // 特殊资产处理
            doSpecialAsset(resMap, dto);
            // 交易方向转换
            changePs(resMap, dto);
        }
        return resMap;
    }



    /**
     * 需要生成方向交易的
     * 
     * @param resMap 报文体
     * @param dto 画面数据
     * @param ps 正反交易方向
     */
    private void reverseCommon(Map<String, Object> resMap, Map<String, Object> dto, String ps) {
        // 判断是否需要生成方向交易以及是否满足生成方向交易的条件
        if (isReverse(dto)) {
            resMap.put(ModelConstant.INF_PS, ps);
            // 事件编号+交易方向生成新的事件编号
            Object eventId = resMap.get(ModelConstant.INF_EVENTID);
            if (StringUtil.isNotEmpty(eventId)) {
                resMap.put(ModelConstant.INF_EVENTID, eventId + ps);
            }
            // 预占编号+交易方向生成新的预占编号
            Object preOccupyCode = resMap.get(ModelConstant.INF_PREOCCUPYCODE);
            if (StringUtil.isNotEmpty(preOccupyCode)) {
                resMap.put(ModelConstant.INF_PREOCCUPYCODE, preOccupyCode + ps);
            }
            // 非内部卖出交易时需要设置同步交易标识（同一组合内不同资产交换）
            if (!isInnerTrd(dto)) {
                resMap.put(ModelConstant.INF_IS_SYNC, BussinessDictCodeListRds.是否_是.getValue());
                resMap.put(ModelConstant.INF_GROUP_ID, eventId);
            }
            if (StringUtil.equals(ps, reversePs) && StringUtil.equals(
                dto.get(ModelConstant.INF_PS), DictCodeListExt1.交易方向_债转股.getValue())) {
                // 金额=转股数*转股价格
                BigDecimal num = CommonUtil.getBigDecimal(dto, ModelConstant.ENTRUST_NUM, null);
                BigDecimal pri = CommonUtil.getBigDecimal(dto, ModelConstant.ENTRUST_PRICE, null);
                if (num != null && pri != null) {
                    resMap.put(ModelConstant.INF_AMOUNT, num.multiply(pri));
                }
            }
        }
    }

    /**
     * 是否需要生成反向交易
     */
    public boolean isNeedReverse() {
        return !CollectionUtils.isEmpty(needRvsPs);
    }

    /**
     * 存在反向交易时需要设置的信息
     * 
     * @param ps 存在反向交易时的正交易方向
     * @param reversePs 存在反向交易时反交易方向
     * @param needRvsPs 需要生成方向交易的交易方向
     */
    public void setReverse(String ps, String reversePs, Collection<String> needRvsPs) {
        this.ps = ps;
        this.reversePs = reversePs;
        this.needRvsPs = CollectionUtils.isEmpty(
            needRvsPs) ? new HashSet<>() : needRvsPs.stream().collect(Collectors.toSet());
    }

    /**
     * 命中反向交易条件
     * 
     * @param ps 交易方向
     * @return true : 命中
     */
    public boolean isReverse(Map<String, Object> map) {
        // 原交易方向
        String myPs = (String)map.get(ModelConstant.INF_PS);
        return isNeedReverse()
               && ((!CollectionUtils.isEmpty(needRvsPs) && needRvsPs.contains(myPs))
                   || isInnerTrd(map));
    }

    /**
     * 是否内部交易
     * 
     * @param map 报文信息
     * @return true : 是内部交易
     */
    public boolean isInnerTrd(Map<String, Object> map) {
        // 交易方向
        String myPs = (String)map.get(ModelConstant.INF_PS);
        // 是否内部交易对手
        String isInnerTrd = (String)map.get(ModelConstant.INF_ISINNERTRD);
        return CommonUtil.isType(myPs, BussinessDictCodeList.交易方向_卖出)
               && CommonUtil.isType(isInnerTrd, BussinessDictCodeListRds.是否_是);
    }

    protected void doSpecialAsset(Map<String, Object> resMap,
                                  Map<String, Object> dto) {
        Object assetType = resMap.get(ModelConstant.INF_ASSETTYPE);
        if (StringUtil.equals(DictCodeList.平安资产类型_质押式回购.getValue(), assetType)) {
            resMap.put(ModelConstant.INF_PS, DictCodeList.交易方向_买入.getValue());
            resMap.put(ModelConstant.INF_ASSETTYPE, assetType);
            resMap.put(ModelConstant.INF_ASSETTYPE2, dto.get(ModelConstant.INF_PS));
            resMap.put(ModelConstant.INF_ASSETRATE, dto.get(ModelConstant.ENTRUST_PRICE));
            resMap.remove(ModelConstant.INF_ASSETCODE);
            resMap.remove(ModelConstant.INF_MARKETCODE);
            if (StringUtil.equals(dto.get("tradePlace"),
                DictCodeListExt1.交易场所_上海证券交易所.getValue())) {
                resMap.put(ModelConstant.INF_CALENDARCODE, "04");
            }
            else {
                resMap.put(ModelConstant.INF_CALENDARCODE, "05");
            }
        }
        if (StringUtil.equals(DictCodeList.平安资产类型_债券.getValue(), assetType)) {
            // 将到期收益率除以100
            Object obj = CommonUtil.findValue(dto, "yield");
            if (obj != null && StringUtil.isNotEmpty(obj)) {
                // 到期收益率
                resMap.put(ModelConstant.INF_YIELDENDRATE,
                    ArithmeticUtil.divide(new BigDecimal(obj.toString()), new BigDecimal(100), 4));
            }
        }
    }

    public void addReactor(Reactor reactor) {
        if (null == reactors) {
            reactors = new ArrayList<>();
        }
        reactors.add(reactor);
    }

    /**
     * 获取 isNeedChangeAssetType
     * 
     * @return isNeedChangeAssetType.
     */
    public boolean isNeedChangeAssetType() {
        return isNeedChangeAssetType;
    }

    /**
     * 资产类型转换（金融产品类型_投管用 -> 平安资产类型）
     * 
     * @param resMap 报文体
     * @param dto 画面数据
     * @return 平安资产类型
     */
    protected void changeAssetType(Map<String, Object> resMap, Map<String, Object> dto) {
        if (!isNeedChangeAssetType()) {
            return;
        }
        Object finprodType2 = dto.get(ModelConstant.FINPRODTYPE2);
        resMap.put(ModelConstant.INF_ASSETTYPE, ModelConstant.assetType((String)finprodType2));
    }

    /**
     * 交易方向转换(配债/配股->买入)
     * 
     * @param resMap 报文体
     * @param dto 画面数据
     * @return 平安资产类型
     */
    protected void changePs(Map<String, Object> resMap, Map<String, Object> dto) {
        Object oldPs = dto.get(ModelConstant.INF_PS);
        String newPs = ModelConstant.toPs((String)oldPs);
        if (StringUtil.isNotEmpty(newPs)) {
            resMap.put(ModelConstant.INF_PS, ModelConstant.toPs((String)oldPs));
        }
    }

    /**
     * 判断是否不需要处理的投资品种
     * 
     * @param dto 画面数据
     * @return true : 不需要处理
     */
    protected boolean isNoNeedAsset(Map<String, Object> dto) {
        return !CollectionUtils.isEmpty(noNeedAsset)
               && noNeedAsset.contains(dto.get(ModelConstant.INF_PS));
    }

    /**
     * 添加不需要处理的交易品种
     * 
     * @param assetType 交易品种
     */
    public void addNoNeedAsset(String... assetType) {
        if (null == noNeedAsset) {
            noNeedAsset = new HashSet<>();
        }
        noNeedAsset.addAll(Arrays.asList(assetType));
    }
}
