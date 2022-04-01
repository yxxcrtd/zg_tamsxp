/*
 * 文件名：BaseTradeProductDealMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：huwenbin
 * 修改时间：2018年9月5日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.common.component.base.trade.impl.dao;

import com.joyintech.tams.common.base.entity.StlPayOrders;
import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.invest.common.dto.BaseTradeProductDealDto;
import com.joyintech.tams.invest.common.dto.StlPayOrdersDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 金融产品交易组件
 * 〈功能详细描述〉
 * @author huwenbin
 * @version 1.0 2018年9月5日
 * @see BaseTradeProductDealMapper
 * @since 1.0
 */
@Repository
public interface BaseTradeProductDealMapper {
    /**
     * 
     * 金融产品交易查询
     * <功能详细描述>
     * @param tradeId
     * @return
     * @see
     * @since 1.0
     */
    List<BaseTradeProductDealDto> selectByTradeId(@Param("tradeId") String tradeId);

    /**
     *
     * 更新金融产品交易表是否已撤销：是
     * <功能详细描述>
     * @param map Map<String,Object>
     * @return map
     * @see
     * @since 1.0
     */
    int updateTrdProDeal(Map<String, Object> map);
    /**
     *
     * 金融产品交易查询(撤销用)
     * <功能详细描述>
     * @param tradeId
     * @return
     * @see
     * @since 1.0
     */
    List<BaseTradeProductDealDto> selectByTradeIdForRevoke(@Param("tradeId") String tradeId);

    /**
     *
     * 金融产品汇总交易查询
     * <功能详细描述>
     * @param tradeId
     * @return
     * @see
     * @since 1.0
     */
    // modify by etliu 20220227 start
    // 广农商二阶段 GNS-890：【广农商场内清算】场内债券存续期交易重复跑批时，未对原交易数据进行撤销操作
//    List<BaseTradeProductDealDto> selectByTotalTradeId(@Param("tradeId") String tradeId);
    List<BaseTradeProductDealDto> selectByTotalTradeId(@Param("tradeId") String tradeId, @Param("needPsCondition") String needPsCondition);
    // modify by etliu 20220227 end
    /**
     *
     * 金融产品交易查询
     * <功能详细描述>
     * @param totalTradeId
     * @return
     * @see
     * @since 1.0
     */
    List<TrdProductDeal> selectTrdProductDealsByTotalTradeId(@Param("totalTradeId") String totalTradeId);

    /**
     *
     * 更新金融产品交易汇总表是否已撤销：是
     * <功能详细描述>
     * @param map Map<String,Object>
     * @return map
     * @see
     * @since 1.0
     */
    int updateTrdProdTotalDeal(Map<String, Object> map);
    /**
     *
     * 金融产品交易查询
     * <功能详细描述>
     * @param totalTradeId
     * @return
     * @see
     * @since 1.0
     */
    List<TrdProductDeal> selectTrdProductChlDealsByTotalTradeId(@Param("totalTradeId") String totalTradeId);
    /**
     *
     * 更新金融产品交易表是否已撤销：是
     * <功能详细描述>
     * @param map Map<String,Object>
     * @return map
     * @see
     * @since 1.0
     */
    int updateChlTrdProDeal(Map<String, Object> map);
    /**
     *
     * 更新指令交易表是否已撤销：是
     * <功能详细描述>
     * @param map Map<String,Object>
     * @return map
     * @see
     * @since 1.0
     */
    int updateStlTrdProDeal(Map<String, Object> map);
    
    /**
     * 检索回购类型
     * @param map Map<String, Object>
     * @return 回购类型
     */
    List<String> selectRepoAssetType2(Map<String, Object> map);

    /**
     * 根据交易编号查询对应划款指令
     * @param map Map<String, Object>
     * @return 划款指令
     */
    List<StlPayOrdersDto> selectStlPayOrders(Map<String, Object> map);

    // add by etliu 20220219 start
    /**
     * 查询划款指令
     * @param map Map<String, Object>
     * @return 划款指令
     */
    List<StlPayOrdersDto> selectStlPayOrdersList(Map<String, Object> map);
    // add by etliu 20220219 end

    List<StlPayOrdersDto> selectStlPayOrdersList1(Map<String, Object> map);

    /**
     *
     * 金融产品汇总交易查询
     * <功能详细描述>
     * @param tradeId
     * @return
     * @see
     * @since 1.0
     */
    List<BaseTradeProductDealDto> selectByTotalTradeIdTRD14(@Param("tradeId") String tradeId);
}
