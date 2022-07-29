package com.joyintech.tams.svreport.interf.chb.bottomassetposition.dao;

import java.util.Date;
import java.util.List;

import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import org.apache.ibatis.annotations.Param;

import com.joyintech.tams.common.base.entity.ChbBotAstPosition;
import com.joyintech.tams.common.base.entity.ChbPrdFeeDeal;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbBotAstPosInterfMapper {
	void selectByPosdate(Date batchTime);
	
	int deleteByPosdate(Date batchTime);
	
	int deleteByUpdateTime();
	
	int insertBatchList(@Param("listChbBotAstPosition")List<ChbBotAstPosition> listChbBotAstPosition);
	
	int insert(ChbBotAstPosition chbBotAstPosition);
	
	List<Date> isUpdated(Date batchTime);
	
	int deleteBatchList(@Param("listChbBotAstPosition")List<ChbBotAstPosition> listChbBotAstPosition);
	
	void mergeData(@Param("newDataList")List<ChbBotAstPosition> newDataList);

	List<Date> isUpdatedChl(Date batchTime);
	/**
	 * 获取跑批日所有资管计划的未投资头寸
	 * @param batchTime
	 * @return
	 */
	List<ChbBotAstPosition> getUnInvestBotListFromRsk(Date batchTime);
	/**
	 * 获取跑批日所有资管计划的资产价值以及实收资本
	 * @param batchTime
	 * @return
	 */
	List<ChbBotAstPosition> getBotTotalNumFromRsk(Date batchTime);

	/**
	 * 根据跑批日查询估值表导入的底层资产
	 * @param batchTime
	 * @return
	 */
	List<ChbBotAstPosition> getOtherBotDataFromRsk(Date batchTime);
	/**
	 * 查询回购底层资产
	 * @param batchTime
	 * @return
	 */
	List<ChbBotAstPosition> getRepBotDataFromRsk(Date batchTime);
	/**
	 * 查询跑批日所有未到期的回购资产
	 * @param batchTime
	 * @return
	 */
	List<ChbAssetInfo> selectRepAstByBatchDate(Date batchTime);


	/**
	 *根据底层未投资任何资产来虚拟一笔剩余现金底层资产
	 * @param batchTime
	 * @return
	 */
	List<ChbBotAstPosition> getCashBotDataFromRsk(@Param("batchTime")Date batchTime,@Param("monthReport")String monthReport);
	/**
	 * 排除是否无底层资产只有剩余现金
	 * @param batchTime
	 * @return
	 */
	List<ChbBotAstPosition> getNoBotAstCashDataFromRsk(Date batchTime);

	/**
	 * 查询所有资管计划资产
	 * @param
	 * @return
	 */
	List<ChbAssetInfo> selectChbAstInfoBotInfo();

	/**
	 * 根据配置科目号，获取底层资产持仓信息
	 * @param batchTime
	 * @return
	 */
	List<ChbBotAstPosition> getBotDataFromBalance(@Param("batchTime")Date batchTime,@Param("subjectNoList")List<String> subjectNoList);

}