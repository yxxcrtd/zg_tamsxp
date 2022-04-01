/*
 * 文件名：FinProductTradeService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：huwenbin
 * 修改时间：2018年8月30日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.common.component.base.trade;

import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.invest.common.dto.BaseTradeProductDealDto;
import com.joyintech.tams.invest.common.dto.BaseTradeProductDealRevokeDto;

import java.util.List;

/**
 * 金融产品交易
 * 〈功能详细描述〉
 * @author huwenbin
 * @version 1.0 2018年8月30日
 * @see BaseTradeProductDealService
 * @since 1.0
 */
public interface BaseTradeProductDealService {
    
    /**
     * 
     * 金融产品交易新建
     * <功能详细描述>
     * @param baseTradeProductDealList List<BaseTradeProductDealDto>
     * @return boolean
     * @see
     * @since 1.0
     */
    boolean insert(List<BaseTradeProductDealDto> baseTradeProductDealList);
    
    /**
     * 
     * 金融产品交易撤销
     * <功能详细描述>
     * @param baseTradeProductDealRevokeList List<BaseTradeProductDealRevokeDto>
     * @return boolean
     * @see
     * @since 1.0
     */
    boolean revoke(List<BaseTradeProductDealRevokeDto> baseTradeProductDealRevokeList);
    
    /**
     * 
     * 金融产品交易查询
     * <功能详细描述>
     * @param tradeIdList List<String>
     * @return List<BaseTradeProductDealDto>
     * @see
     * @since 1.0
     */
    List<BaseTradeProductDealDto> select(List<String> tradeIdList);

    /**
     *
     * 金融产品汇总交易查询
     * <功能详细描述>
     * @param tradeIdList List<String>
     * @return List<BaseTradeProductDealDto>
     * @see
     * @since 1.0
     */
    List<BaseTradeProductDealDto> selectTotalDeal(List<String> tradeIdList);

    /**
     *
     * 根据汇总交易ID查询金融产品交易信息
     * <功能详细描述>
     * @param totalTradeId
     * @return List<TrdProductDeal>
     * @see
     * @since 1.0
     */
    List<TrdProductDeal> selectTrdProductDealsByTotalTradeId(String totalTradeId);


    /**
     *
     * 根据汇总交易ID查询金融产品通道交易信息
     * <功能详细描述>
     * @param totalTradeId
     * @return List<TrdProductDeal>
     * @see
     * @since 1.0
     */
    List<TrdProductDeal> selectTrdProductChlDealsByTotalTradeId(String totalTradeId);

}
