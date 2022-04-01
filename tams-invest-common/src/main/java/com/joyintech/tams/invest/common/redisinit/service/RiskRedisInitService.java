/*
 * 文件名：RiskRedisInitService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：裴家俊
 * 修改时间：2019年9月2日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.common.redisinit.service;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.joyintech.tams.invest.common.component.base.core.ICond;

/**
 * redis数据初始化接口
 * 
 * @author 裴家俊
 * @version 1.0 2019年9月2日
 * @see RiskRedisInitService
 * @since 1.0
 */
@Service
public interface RiskRedisInitService<T extends ICond> {

    /**
     * 获取待处理数据
     * @param var 传递参数
     */
    public Map<String,Object> execute(T var) ;

    /**
     * 处理风险事件
     */
    public void dealRiskEvent();

    /**
     * 处理风险指标参数
     */
    public void dealRiskIndParam(T var);


    /**
     * 处理风险指标
     * 
     */
    public void dealRiskIndicator(T var) ;


    /**
     * 处理组合关联指标
     * 
     */
    public void dealRiskPtlIndicator(T var) ;
    
    /**
     * 处理资产索引
     * 
     * @param var 参数值
     */
    public void dealAssetIndex(T var);

    /**
     * 初始化平台日
     *
     */
    public void setPlatDate(T var);
    
    /**
     * 处理 组合信息
     * 
     * @param var 组合信息
     */
    void dealPortData(T var);

    /**
     * 处理 资产信息
     * 
     * @param var 资产信息
     */
    void dealAssetData(T var);
    
    /**
     * 处理汇率行情信息
     * 
     * @param var 汇率行情信息
     */
    void dealExchargeInfo(T var);
    
    /**
     * 处理 基金估值信息
     * 
     * @param var 基金估值信息
     */
    void dealFundValuation(T var);
    
    /**
     * 处理 节假日信息
     * 
     * @param var 节假日信息
     */
    void dealCalendarData(T var);

    /**
     * 写日志
     * @param e
     * @param sdate
     */
    public void insertTrialLog(String e, Date sdate, String logLvl, String functionId);

    // add by zhangrui1 start
    /**
     * 处理 资产实时规模信息
     * 
     * @param var 资产实时规模信息
     */
    void dealProdAssetAmount(T var);
    // add by zhangrui1 end

    /**
     * 处理 资产久期相关信息
     *
     * @param var 资产久期相关信息
     */
    void dealAstCashDurationInfo(T var);



    void dealOccupyRelParamData();
}
