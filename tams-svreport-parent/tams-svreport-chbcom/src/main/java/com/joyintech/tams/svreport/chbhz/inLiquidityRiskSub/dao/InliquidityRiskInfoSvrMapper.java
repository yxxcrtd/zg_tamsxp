package com.joyintech.tams.svreport.chbhz.inLiquidityRiskSub.dao;

import com.joyintech.tams.common.base.entity.LiquidityRiskSub;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InliquidityRiskInfoSvrMapper {

	/**
	 * 检查是否已报送
	 * @param itemId
	 * @return
	 */
	int checkSubmission(@Param("itemId")String itemId);
	
	/**
	 * 查询流动性风险list
	 * @param batchDateStart
	 * @param batchDateEnd
	 * @return
	 */
	List<LiquidityRiskSub>  genLiquidityRiskForSubList(@Param("batchDateStart")String batchDateStart, @Param("batchDateEnd")String batchDateEnd);

	/**
	 * 删除流动性风险登记明细表
	 * @param itemId
	 * @return
	 */
	int deleteLiquidityRiskSubDetail(@Param("itemId")String itemId);

	/**
	 * 删除流动性风险登记主表
	 * @param itemId
	 * @return
	 */
	int deleteLiquidityRiskSub(@Param("itemId")String itemId);

	/**
	 * 插入流动性风险登记主表
	 * @param dataList
	 * @return
	 */
	int insertLiquidityRiskSub(@Param("dataList")List<LiquidityRiskSub> dataList);

	/**
	 * 插入流动性风险登记明细表
	 * @param dataList
	 * @return
	 */
	int insertLiquidityRiskSubDetail(@Param("dataList")List<LiquidityRiskSub> dataList);
}