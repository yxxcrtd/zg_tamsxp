package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.TrdProductTempDeal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrdProductTempDealMapper {
    int deleteByPrimaryKey(String tradeId);

    int insert(TrdProductTempDeal record);

    int insertSelective(TrdProductTempDeal record);

    int insertBasicByBasicInfo(TrdProductTempDeal record);

    int insertToDeal(@Param("totalTradeId") String totalTradeId);

    TrdProductTempDeal selectByPrimaryKey(String tradeId);

    int updateByPrimaryKeySelective(TrdProductTempDeal record);

    int updateByPrimaryKey(TrdProductTempDeal record);
}