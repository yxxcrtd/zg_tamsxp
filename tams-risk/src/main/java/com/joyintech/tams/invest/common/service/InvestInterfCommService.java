/*
 * 文件名：BussCommonMapperDelegateService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：renping
 * 修改时间：2020年2月5日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
package com.joyintech.tams.invest.common.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.FinProductMapper;
import com.joyintech.tams.common.base.dao.PtlPortfolioMapper;
import com.joyintech.tams.common.base.entity.FinProduct;
import com.joyintech.tams.common.base.entity.PtlPortfolio;
import com.joyintech.tams.common.base.entity.StlPayOrders;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.dto.common.InvestInterfCommDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 投管接口用共通Service
 * 
 * @author renping
 * @version 1.0 2020年2月5日
 * @see InvestInterfCommService
 * @since 1.0
 */
@Service
public class InvestInterfCommService extends BaseService<InvestInterfCommDto> {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 组合表Mapper
     */
    @Autowired(required = false)
    private PtlPortfolioMapper ptlPortfolioMapper;

    /**
     * 标的表
     */
    @Autowired(required = false)
    private FinProductMapper finProductMapper;

    /**
     * 投管接口调用
     * 
     * @param investInterfCommDto 参数
     * @return 接口返回结果
     * @see
     * @since 1.0
     */
    public Map<String, Object> doInvestInterf(InvestInterfCommDto investInterfCommDto) {
        // 系统参数配置接口开关
        if (!StringUtil.equals(DictCodeListExt1.是或否_是.getValue(),
            CacheUtil.getSysParam(BussinessConstant.IS_INVEST_INTERF_OPEN))) {
            return null;
        }
        try {
            // 交易时标的的市场代码取得
            if (investInterfCommDto.getTrdProductDeal() != null) {
                investInterfCommDto.setFinProduct(finProductMapper.selectByPrimaryKey(
                    investInterfCommDto.getTrdProductDeal().getFinprodId()));
            }
            // 参数转换
            Map<String, Object> map = JsonUtils.object2Map(investInterfCommDto);
            // 本方交易员名称与联系方式取得
            map.put("traderName", LoginUserUtil.getUserName());
            // if (investInterfCommDto.getTrdProductDeal() != null
            // && StringUtil.isNotEmpty(investInterfCommDto.getTrdProductDeal().getTrader())) {
            // SysUser sysUser=
            // sysUserMapper.selectByPrimaryKey(investInterfCommDto.getTrdProductDeal().getTrader());
            // String traderContactInfo = sysUser.getMobNum();
            // if(StringUtil.isEmpty(traderContactInfo)) {
            // traderContactInfo = sysUser.getPhoneNum();
            // }
            // if(StringUtil.isEmpty(traderContactInfo)) {
            // traderContactInfo = sysUser.getMailAddr();
            // }
            // map.put("traderName", sysUser.getRealName());
            // map.put("traderContactInfo", traderContactInfo);
            // }
            // 指令信息账户转换:从本方账户和对手方账户设置出款账户和收款账户
            StlPayOrders stlPayOrders = investInterfCommDto.getStlPayOrders();
            if (stlPayOrders != null) {
                Map<String, Object> stlPayOrdersMap = (Map<String, Object>)map.get("stlPayOrders");
                if (StringUtil.equals(stlPayOrders.getPs(), DictCodeListExt1.收支方向_收入.getValue())) {
                    // 收款账户
                    stlPayOrdersMap.put("recAcctNo", stlPayOrdersMap.get("myAcctNo"));
                    // 出款账户
                    stlPayOrdersMap.put("payAcctNo", stlPayOrdersMap.get("counterAcctNo"));
                }
                else {
                    // 收款账户
                    stlPayOrdersMap.put("recAcctNo", stlPayOrdersMap.get("counterAcctNo"));
                    // 出款账户
                    stlPayOrdersMap.put("payAcctNo", stlPayOrdersMap.get("myAcctNo"));
                }
            }
            if (!StringUtil.isEmptyList(investInterfCommDto.getStlPayOrdersList())) {
                List<Map<String, Object>> stlPayOrdersMapList = (List<Map<String, Object>>)map.get(
                    "stlPayOrdersList");
                for (Map<String, Object> stlPayOrdersMap : stlPayOrdersMapList) {
                    if (StringUtil.equals(stlPayOrdersMap.get("ps"),
                        DictCodeListExt1.收支方向_收入.getValue())) {
                        // 收款账户
                        stlPayOrdersMap.put("recAcctNo", stlPayOrdersMap.get("myAcctNo"));
                        // 出款账户
                        stlPayOrdersMap.put("payAcctNo", stlPayOrdersMap.get("counterAcctNo"));
                    }
                    else {
                        // 收款账户
                        stlPayOrdersMap.put("recAcctNo", stlPayOrdersMap.get("counterAcctNo"));
                        // 出款账户
                        stlPayOrdersMap.put("payAcctNo", stlPayOrdersMap.get("myAcctNo"));
                    }
                }
            }
            if (investInterfCommDto.getTrdProductDeal() != null) {
                // 费用收付方向默认为支出
                map.put("feePs", DictCodeListExt1.收付方向_支出.getValue());
                // 是否内部交易
                if (StringUtil.isNotEmpty(
                    investInterfCommDto.getTrdProductDeal().getCounterId())) {
                    PtlPortfolio mcp = ptlPortfolioMapper.selectByPrimaryKey(
                        investInterfCommDto.getTrdProductDeal().getCounterId());
                    if (mcp != null) { // 内部交易对手
                        map.put("isInnerTrade", DictCodeList.是否_是.getValue());
                        // 费用收付方向（内部交易卖出时费用支付方向为收入）
                        if (StringUtil.equals(investInterfCommDto.getTrdProductDeal().getPs(),
                            DictCodeList.交易方向_卖出.getValue())) {
                            map.put("feePs", DictCodeList.收付方向_收入.getValue());
                        }
                    }
                    else {
                        map.put("isInnerTrade", null);
                    }
                }
            }
            // 可转债转股转换市场代码取得
            if (investInterfCommDto.getTrdFinProductBarter() != null && StringUtil.isNotEmpty(
                investInterfCommDto.getTrdFinProductBarter().getFinprodId())) {
                FinProduct finProduct = finProductMapper.selectByPrimaryKey(
                    investInterfCommDto.getTrdFinProductBarter().getFinprodId());
                if (finProduct != null) {
                    Map<String, Object> trdFinProductBarter = (Map<String, Object>)map.get(
                        "trdFinProductBarter");
                    trdFinProductBarter.put("finprodMarketId", finProduct.getFinprodMarketId());
                    trdFinProductBarter.put("finprodName", finProduct.getFinprodName());
                }
            }
            // 平台日期
            map.put("platDate", DateUtil.platDateFromCache());
            // 调用接口
            return restTemplate.postForObject(
                ServiceApplication.INVEST_INTERF_SERVICE_NAME + "/invest/interf/send", map,
                Map.class);
        }
        catch (Exception e) {
            // 目前接口报错先不处理
            logger.error("调投管接口异常，不影响交易", e);
            return null;
        }
    }

    public Map<String, Object> doInvestInterf(Map<String, Object> mapParm) {
        // 系统参数配置接口开关
        if (!StringUtil.equals(DictCodeListExt1.是或否_是.getValue(),
            CacheUtil.getSysParam(BussinessConstant.IS_INVEST_INTERF_OPEN))) {
            return null;
        }
        try {
            // 调用接口
            mapParm.put("platDate", DateUtil.platDateFromCache());
            return restTemplate.postForObject(
                ServiceApplication.INVEST_INTERF_SERVICE_NAME + "/invest/interf/send", mapParm,
                Map.class);
        }
        catch (Exception e) {
            // 目前接口报错先不处理
            return null;
        }
    }
}
