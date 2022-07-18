package com.joyintech.tams.svreport.chb.investtrade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.joyintech.tams.svreport.chb.investtrade.dto.MstCounterPartyDto;
import org.springframework.stereotype.Repository;

@Repository
public interface MstCounterPartyDtoMapper {
    int deleteByPrimaryKey(String counterId);

    int insert(MstCounterPartyDto record);

    int insertSelective(MstCounterPartyDto record);

    MstCounterPartyDto selectByPrimaryKey(String counterId);

    int updateByPrimaryKeySelective(MstCounterPartyDto record);

    int updateByPrimaryKey(MstCounterPartyDto record);	
    
	/**
	 * 功能:根据交易对手查询交易对手名称
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月19日
	 * @param counterCodeList
	 * @return 
	 * List<String>
	 */
	List<String> selectTradeCounterNameByTradeCounterId(@Param("counterCodeList")List<String> counterCodeList);
    
	/**
	 * 功能:查询交易对手名称
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月19日
	 * @return 
	 * List<String>
	 */
	List<MstCounterPartyDto> selectAllTradeCounter();

	List<MstCounterPartyDto> selectOneDate(Map<String, Object> param);
}