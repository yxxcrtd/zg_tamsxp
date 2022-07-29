package com.joyintech.tams.svreport.pbc.assetposition.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import com.joyintech.tams.common.base.entity.PbcCapitalTrustDetailSub;
import com.joyintech.tams.common.base.entity.PbcLoanDetailInfoSub;
import com.joyintech.tams.svreport.pbc.assetposition.dto.PbcMonthAssetPositionDto;


/**
 * 功能:人行报表-资产头寸
 * @since 1.6.0
 * @version 1.6.0
 * @作者：weiyuqiang
 * @创建时间：2019年3月11日
 * @修改记录：
 */
@Repository
public interface PbcMonthAssetPositionMapper {
    int deleteByPrimaryKey(String posuuid);

    int insert(PbcMonthAssetPositionDto record);

    int insertSelective(PbcMonthAssetPositionDto record);

    PbcMonthAssetPositionDto selectByPrimaryKey(String posuuid);

    int updateByPrimaryKeySelective(PbcMonthAssetPositionDto record);

    int updateByPrimaryKey(PbcMonthAssetPositionDto record);
    
    /**
     * 功能:根据数据状态查询资产要素(仅供中债使用)
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月19日
     * @param statusList
     * @return 
     * List<AssetInfoDto>
     */
    List<ChbAssetInfo> selectAssetByStatus(@Param("statusList")List<String> statusList);
    
    /**
     * 功能:根据产品id和统计结束日期统计科目余额
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param prodList 产品代码
     * @param endDate 统计结束日
     * @param subjectNoList 科目余额号
     * @return 
     * List<PbcMonthAssetPositionDto>
     */
    List<PbcMonthAssetPositionDto> selectBalanceByEndDateAndProd(@Param("reportEndDate")String reportEndDate,
    		@Param("prodList")List<String> prodList,@Param("assetList") List<String> assetList,@Param("subjectNoList") List<String> subjectNoList);

    /**
     * 功能:根据产品id和统计结束日期统计科目余额-中债子系统
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param prodList 产品代码
     * @param endDate 统计结束日
     * @param subjectNoList 科目余额号
     * @return 
     * List<PbcMonthAssetPositionDto>
     */
    List<PbcMonthAssetPositionDto> selectBalanceByEndDateAndProdSubSystem(@Param("reportEndDate")String reportEndDate,
            @Param("prodList")List<String> prodList,@Param("assetList") List<String> assetList,@Param("subjectNoList") List<String> subjectNoList);
    
    /**
     * 功能:根据报表结束日删除
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param reportEndDay
     * @return 
     * int
     */
    int deleteByReportDay(@Param("reportDay")Date reportDay,@Param("prodList")List<String> prodList,
            @Param("assetList")List<String> assetList);
    /**
     * 从组合估值查询资产头寸
     * @param record
     * @return
     */
    List<PbcMonthAssetPositionDto> selectPositionFromValution(PbcMonthAssetPositionDto record);

    /**
     * 从组合估值查询资产头寸-中债子系统专用
     * @param record
     * @return
     */
    List<PbcMonthAssetPositionDto> selectPositionFromValutionSubSystem(PbcMonthAssetPositionDto record);
    /**
     * 从组合估值查询资产头寸-中债子系统专用 子资管计划资产基础指标数据表
     * @param record
     * @return
     */
    List<PbcMonthAssetPositionDto> selectPositionFromValutionSubSystemdtx(PbcMonthAssetPositionDto record);
    /**
     * 从组合估值查询资产头寸 （不穿透） 通道
     * @param record
     * @return
     */
    List<PbcMonthAssetPositionDto> selectPositionFromValutionChl(PbcMonthAssetPositionDto record);
    /**
     * 查询通道剩余现金
     * @param record
     * @return
     */
    List<PbcMonthAssetPositionDto> selectPositionFromCashValutionChl(PbcMonthAssetPositionDto record);
    
    /**
     * 查询头寸资产信息
     * @param param
     * @return
     */
	List<PbcMonthAssetPositionDto> queryMonthAssetPositionInfoByData(Map<String, String> param);

	/**
	 * 查询资产收益权贷款明细基本信息表
	 * @param dto
	 * @return
	 */
	PbcLoanDetailInfoSub queryLoanDetailInfoByAssetCode(PbcMonthAssetPositionDto dto);
	
	/**
	 * 
	 * @Description 查询所有有效的资产收益权贷款明细基本信息  
	 * @Author      高健  
	 * @Date        2019年12月28日 下午2:56:24  
	 * @param @return 参数  
	 * @return List<PbcLoanDetailInfoSub> 返回类型   
	 * @throws
	 */
	List<PbcLoanDetailInfoSub> listAllEffectiveLoanDetailInfo();
	
	/**
	 * 
	 * @Description 资金信托类资产投资明细信息  
	 * @Author      高健  
	 * @Date        2019年12月20日 下午4:30:59  
	 * @param @return 参数  
	 * @return PbcCapitalTrustDetailSub 返回类型   
	 * @throws
	 */
	PbcCapitalTrustDetailSub queryCapitalTrustDetailInfoByAssetCode(PbcMonthAssetPositionDto dto);
	
	/**
	 * 
	 * @Description 查询所有有效资金信托类资产投资明细信息
	 * @Author      高健  
	 * @Date        2019年12月28日 下午3:08:40  
	 * @param @return 参数  
	 * @return List<PbcCapitalTrustDetailSub> 返回类型   
	 * @throws
	 */
	List<PbcCapitalTrustDetailSub> listAllEffectiveCapitalTrustDetailInfo();
	
    /**银行月报费用科目从费用头寸表取
     * @param reportEndDate
     * @param prodList
     * @param assetList
     * @param subjectNoList
     * @return
     */
    List<PbcMonthAssetPositionDto> selectPrdfeeByEndDateAndProd(@Param("reportEndDate")String reportEndDate,
    		@Param("prodList")List<String> prodList,@Param("assetList") List<String> assetList,@Param("subjectNoList") List<String> subjectNoList);

	List<PbcMonthAssetPositionDto> selectJZBalanceByEndDateAndProd(@Param("reportEndDate")String reportEndDate,
																   @Param("prodList")List<String> prodList
			,@Param("assetList") List<String> assetList,@Param("subjectNoList") List<String> subjectNoList,@Param("yqSubjectNoList") List<String> yqSubjectNoList);

	List<PbcMonthAssetPositionDto> selectJZBalanceByEndDateAndProdSubSystem(@Param("reportEndDate")String reportEndDate,
																			@Param("prodList")List<String> prodList,@Param("assetList") List<String> assetList
			,@Param("subjectNoList") List<String> subjectNoList,@Param("yqSubjectNoList") List<String> yqSubjectNoList);

	/**
	 * 功能:从资产基础指标中查询减值数据
	 * @since 1.1.0
	 * @version 1.1.0
	 * @author：zhengxiaolain 2022年3月16日
	 * @param prodList 产品代码
	 * @param reportEndDate 结束日期
	 * @return
	 * List<PbcMonthAssetPositionDto>
	 */
	List<PbcMonthAssetPositionDto> selectJZByEndDateAndProd(@Param("reportEndDate")String reportEndDate,@Param("prodList")List<String> prodList);


}