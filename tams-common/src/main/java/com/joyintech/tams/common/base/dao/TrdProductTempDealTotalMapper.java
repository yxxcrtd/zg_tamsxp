package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.TrdFinProductDealTotal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrdProductTempDealTotalMapper {
    int deleteByPrimaryKey(String totleTradeId);

    int insert(TrdFinProductDealTotal record);

    int insertSelective(TrdFinProductDealTotal record);

    int insertBasicByBasicInfo(TrdFinProductDealTotal record);

    int insertToDealTotal(@Param("totalTradeId") String totalTradeId);

    TrdFinProductDealTotal selectByPrimaryKey(String totleTradeId);

    int updateByPrimaryKeySelective(TrdFinProductDealTotal record);

    int updateByPrimaryKey(TrdFinProductDealTotal record);

}