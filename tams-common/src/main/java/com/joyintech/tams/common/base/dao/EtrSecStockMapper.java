package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.EtrSecStock;
import org.springframework.stereotype.Repository;

@Repository
public interface EtrSecStockMapper {
    int deleteByPrimaryKey(String pledgeStockId);

    int insert(EtrSecStock record);

    int insertSelective(EtrSecStock record);

    int insertBasicByBasicInfo(EtrSecStock record);

    EtrSecStock selectByPrimaryKey(String pledgeStockId);

    int updateByPrimaryKeySelective(EtrSecStock record);

    int updateByPrimaryKey(EtrSecStock record);

    int updateIsDefStockUser(EtrSecStock record);
}