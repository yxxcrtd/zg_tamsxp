/*
 * 文件名：TrdBondInvestTradeMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Zane.Y
 * 修改时间：2018年9月7日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.trade.conbond.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.service.trade.bond.dao.TrdBondInvestTradeMapper;
import com.joyintech.service.trade.conbond.dto.TrdConBondDto;
import com.joyintech.tams.common.base.entity.PtlTrade;
import com.joyintech.tams.common.base.entity.StlPayOrders;
import com.joyintech.tams.common.base.entity.TrdProductDeal;
import com.joyintech.tams.common.base.entity.TrdProductDealAdd;
import com.joyintech.tams.common.base.entity.WflTrdProductDeal;

/**
 * 债券交易Mapper
 *
 * @author Zane.Y
 * @version 1.0 2018年9月7日
 * @see TrdBondInvestTradeMapper
 * @since 1.0
 */
@Repository
public interface TrdConBondMapper {

	/**
	 * 检索债券交易列表
	 *
	 * @param paraMap 参数
	 * @return 债券交易列表
	 * @see
	 * @since 1.0
	 */
	List<TrdConBondDto> selectDeal(Map<String, Object> paraMap);

	List<SuggestDto> getSecManageAcctId(Map<String, Object> param);


	List<SelectOptionDto> getFinprodId(Map<String, Object> param);

	TrdConBondDto getTrdConBondDto(Map<String, Object> params);

	/**
	 * a.当前债券面额取得（根据还本计划计算）
	 *
	 * @param param 参数
	 * @return a.当前债券面额取得（根据还本计划计算）
	 * @see
	 * @since 1.0
	 */
	BigDecimal getCurSecAmt(Map<String, Object> param);


	/**
	 * 取得证券托管户的登记托管机构
	 *
	 * @param param Map<String, Object>
	 * @return 证券托管户的登记托管机构
	 * @see
	 * @since 1.0
	 */
	String getRegistOrg(Map<String, Object> param);

	BigDecimal getTongDaoCash(Map<String, Object> param);

	List<TrdProductDeal> getTrdProductDealByFTradeId(@Param("fTradeId") String fTradeId,@Param("finprodType2") String finprodType2);

	List<WflTrdProductDeal>  getWftTrdProductDealByFTradeId(@Param("fTradeId") String fTradeId,@Param("finprodType2") String finprodType2,@Param("workflowId") String workflowId);

	void insertRevokePtlTrade(@Param("deal") PtlTrade deal, @Param("updateUser")String updateUser);

	void insertRevokePaTrdProductDeal(@Param("bizCode") String totleTradeId, @Param("updateUser") String updateUser);

	void insertRevokeTrdProductDealAdd(@Param("bizCode")String oldTradeId, @Param("updateUser")String updateUser);

	List<TrdProductDeal> findDealByTotalId(Map<String, Object> map);

	List<StlPayOrders> findOrdersByOrderId(String bizCode);

	List<TrdProductDealAdd> findDealAddByTradeIds(List<TrdProductDeal> trdProductDeals1);

	List<TrdProductDeal> findDealByTotalIdForInterf(Map<String, Object> map);

	void insertWflTotle(Map<String, Object> params);

	void insertWflPaTotal(Map<String, Object> params);

	void insertWflTradeTotalAdd(Map<String, Object> params);

	void insertWflPayOrders(Map<String, Object> params);

	void insertWflTrade(Map<String, Object> params);

	void insertWflPaTrade(Map<String, Object> params);

	void insertWflTradeAdd(Map<String, Object> params);

	void insertWflPayOrdersOfDeal(Map<String, Object> params);

	/**
	 * 查询是否存在生效后续交易
	 * @param param
	 * @return
	 */
	int selectConfirmInfo(Map<String, Object> param);

	int selectWflConfirmInfo(@Param("bTradeId") String bTradeId);

	List<PtlTrade> findPtlTradeByTradeIds(List<TrdProductDeal> trdProductDeals1);

	/**
	 *
	 * 查询交易确认流程中信息
	 * <功能详细描述>
	 * @param bTradeId
	 * @return
	 * @see
	 * @since 1.0
	 */
	Integer selectConfirmInfoWfl(Map<String, Object> param);

	/**
	 *
	 * 查询交易确认流程中信息
	 * <功能详细描述>
	 * @param bTradeId
	 * @return
	 * @see
	 * @since 1.0
	 */
	Integer revokeCheckWfl(Map<String, Object> param);

	TrdConBondDto getTrdConBondDtoForAccount(Map<String, Object> params);

	Map<String, Object> getBusicode(Map<String, Object> param);

	TrdConBondDto getTrdConBondDtoForTradeId(Map<String, Object> params);

	/**
	 * 获取生效的到账交易编号
	 * @param bTradeId
	 * @return
	 */
	String getTradeId(@Param("bTradeId") String bTradeId);

	List<StlPayOrders> getTrdProductDeals(Map<String, Object> param);

	String getAccNo(Map<String,String> param);
}
