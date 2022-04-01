package com.joyintech.tams.report.investormerger.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.base.entity.ChbEquTmp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbEquSub;
import com.joyintech.tams.report.investormerger.dto.ChbEquHoldCheckDto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;

@Repository
public interface ChbEquHoldCheckDtoMapper {
    int insert(ChbEquHoldCheckDto record);
    
    int insertByBatch(@Param("List") List<ChbEquHoldCheckDto> keyList);
    
     /**
     * @Description 分页查询
     * @param pageParam
     * @return List<ChbEquHoldCheckDto>
     */
    List<ChbEquHoldCheckDto> selectAllData(Map<String, Object> pageParam);
    List<ChbEquHoldCheckDto> selectAllDataForHold();
    List<ChbEquHoldCheckDto> queryAllCheckData(@Param("keys")List<String> keyList);
    
    List<ChbEquHoldCheckDto> selectBymodel(ChbEquHoldCheckDto record);

    /**
     * @Description Excel导出查询
     * @param keyList
     * @return List<ChbEquHoldCheckDto>
     */
	Collection<ChbEquHoldCheckDto> selectAllDataByList(@Param("keyList")List<ChbEquHoldCheckDto> subList);
	
	/**
	 * @Description 数据入正式表
	 * @param record
	 * @return int
	 */
	int insertToChbEquHold(ChbEquHoldCheckDto record);
	
	/**
	 * @Description 从正式表删除数据
	 * @param record
	 * @return int
	 */
	int deleteChbEquHold(ChbEquHoldCheckDto record);
	
	void delCheckDataBatch(@Param("key") List<EquityInfo001Dto> key);
	
	void updateMergerIndentifyMarkByBatch(@Param("keyList") List<ChbEquHoldCheckDto> keyList);
	
	void updateMergerIndentifyMark(@Param("key")String key,@Param("indentifyMark")String indentifyMark);
	
	List<ChbEquHoldCheckDto> mergerData(@Param("keys")List<String> keys);
	
	void deleteEquityInfoBatch(List<ChbEquTmp> list);
	
	void deleteEquitySubInfoBatch(List<ChbEquTmp> list);

	void deleteEquityInfoBatchTmp();

	void deleteEquitySubBatchTmp();

	void truncateEquityTmp();

	// 性能优化
	List<ChbEquHoldCheckDto> selectChekForRepairMax();
	
	List<ChbEquHoldCheckDto> selectChekForRepair(List<ChbEquTmp> list);
	
	/**
	 * @Description 根据三要素查询身份信息
	 * @param record
	 * @return List<ChbEquHoldCheckDto>
	 */
	List<ChbEquHoldCheckDto> selectBythreeInfo(ChbEquHoldCheckDto record);
	
	/**
	 * 三要素重复,根据合并规则取识别标识,则需要对之前的交易以及产品头寸的识别标识进行刷新
	 * @return
	 */
	int refreshChangeIndentifyMark(@Param("list")List<EquityInfo001Dto> list);
	
//	int refreshMergerIndentifyMark(@Param("list")List<EquityInfo001Dto> list);

	/**
	 * @Description 更新合并后识别标识和处理状态
	 * @param record
	 * @return int
	 */
	int updatechbEquHoldCheck(ChbEquHoldCheckDto record);
	

    int updateForRepair(ChbEquHoldCheckDto record);

	/**
	 * @Description 根据主键查询
	 * @param indentifyMark
	 * @return ChbEquHoldCheckDto
	 */
	ChbEquHoldCheckDto selectByPrimarykey(@Param("indentifyMark") String indentifyMark);
	
	/**
	 * @Description 更新投资者身份登记表
	 * @return int
	 */
	int updateChbEquSub(@Param("indentifyMark")String indentifyMark,@Param("mergerindentifymark")String mergerindentifymark);
	
	/**
	 * @Description 更新产品交易表
	 * @return int
	 */
//	int updateChbPrdDeal(@Param("indentifyMark")String indentifyMark,@Param("mergerindentifymark")String mergerindentifymark);
	
	
	/**
	 * @Description 更新产品头寸表
	 * @return int
	 */
//	int updateChbPrdPosition(@Param("indentifyMark")String indentifyMark,@Param("mergerindentifymark")String mergerindentifymark);

	int deleteChbEquSub(ChbEquHoldCheckDto chbEquHoldCheckDto);

	int insertChbEquSub(ChbEquSub infosub);
	int deleteCheckDataByPrimarykey(ChbEquHoldCheckDto chbEquHoldCheckDto);
}