package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.TrdProductTempDealAdd;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrdProductTempDealAddMapper {
    int deleteByPrimaryKey(String tradeId);

    int insert(TrdProductTempDealAdd record);

    int insertSelective(TrdProductTempDealAdd record);

    int insertBasicByBasicInfo(TrdProductTempDealAdd record);

    int insertToDealAdd(@Param("totalTradeId") String totalTradeId);

    TrdProductTempDealAdd selectByPrimaryKey(String tradeId);

    int updateByPrimaryKeySelective(TrdProductTempDealAdd record);

    int updateByPrimaryKey(TrdProductTempDealAdd record);
}