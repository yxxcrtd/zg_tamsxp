package com.joyintech.service.report.regulatoryreporting.investmentsinfo.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dto.InvestmentBusinessDetailsDto;

/**  
 * @Description 大型银行报表 表二 表内外投资业务明细表
 * @ClassName   InvestmentBusinessDetailsMapper  
 * @Date        2019年8月7日 
 * @Author      yuluan  
 * Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.  
 */
@Repository
public interface InvestmentBusinessDetailsMapper {
	List<InvestmentBusinessDetailsDto> selectAllMonthDetailAssetByTermid(@Param("termid")String termid,@Param("lastTermid")String lastTermid);
	List<InvestmentBusinessDetailsDto> selectOtherData(@Param("startDate")Date startDate,@Param("lastStartDate")Date lastStartDate,
			@Param("termid")String termid,@Param("lastTermid")String lastTermid);
	/**
	 * 查询属于资管计划的资产代码及穿透前资产价值
	 * @param startDate
	 * @param lastStartDate
	 * @param termid
	 * @param lastTermid
	 * @return
	 */
	List<InvestmentBusinessDetailsDto> selectAssetsManagementPlanData(@Param("endDate")Date endDate);
	/**
	 * 查询属于资管计划的资产代码及穿透前资产价值
	 * @param startDate
	 * @param lastStartDate
	 * @param termid
	 * @param lastTermid
	 * @return
	 */
	List<InvestmentBusinessDetailsDto> selectBottomAssetsData(@Param("endDate")Date endDate,@Param("assetcode")String assetcode);
			
	
	/**  
	 * @Description 根据时间查询金融债券信息  
	 * @Author      高健  
	 * @Date        2019年8月9日 下午1:46:30  
	 * @param @param startDate
	 * @param @return 参数  
	 * @return List<InvestmentBusinessDetailsDto> 返回类型   
	 * @throws  
	 */  
	List<InvestmentBusinessDetailsDto> queryBankDebentureAmount(@Param("startDate")Date startDate, @Param("lastStartDate")Date lastStartDate);
	
	/**  
	 * @Description 统计难以实时统计基础资产的产品信息  
	 * @Author      高健  
	 * @Date        2019年8月10日 下午3:51:20  
	 * @param @param startDate
	 * @param @param lastStartDate
	 * @param @return 参数  
	 * @return InvestmentBusinessDetailsDto 返回类型   
	 * @throws  
	 */  
	InvestmentBusinessDetailsDto queryDiffAsset(@Param("startDate")Date startDate, @Param("lastStartDate")Date lastStartDate);
	/**
	 * @Description 统计难以实时统计基础资产的产品信息(穿透后)
	 * @Author      高健
	 * @Date        2019年8月10日 下午3:51:20
	 * @param @param startDate
	 * @param @param lastStartDate
	 * @param @return 参数
	 * @return InvestmentBusinessDetailsDto 返回类型
	 * @throws
	 */
	InvestmentBusinessDetailsDto queryBottomDiffAsset(Map<String,Object> map);

	/**
	 * 重点关注领域
	 * @param startDate
	 * @param lastStartDate
	 * @return
	 */
	InvestmentBusinessDetailsDto queryBottomInfoAmountByModeType(Map<String,Object> map);
	InvestmentBusinessDetailsDto queryBottomTable3AddXj(Map<String,Object> map);
	InvestmentBusinessDetailsDto queryBottomTable3AddSum(Map<String,Object> map);

	/**
	 * 表3中非金融企业债券
	 * @param map
	 * @return
	 */
	InvestmentBusinessDetailsDto queryBottomInfoAmountFjr(Map<String,Object> map);

	/**
	 *附表银保监局	城商行 字段
	 * @return
	 */
	InvestmentBusinessDetailsDto queryOrgName();
}
