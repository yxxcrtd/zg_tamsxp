package com.joyintech.background.component.order.exchange.database.dao;

import org.springframework.stereotype.Repository;

import com.joyintech.background.component.order.exchange.database.entity.EtrPledgeComponent;

@Repository
public interface EtrPledgeComponentMapper {
    int deleteByPrimaryKey(EtrPledgeComponent record);

    int insert(EtrPledgeComponent record);

    int insertSelective(EtrPledgeComponent record);

    int insertBasicByBasicInfo(EtrPledgeComponent record);

    EtrPledgeComponent selectByPrimaryKey(EtrPledgeComponent key);

    int updateByPrimaryKeySelective(EtrPledgeComponent record);

    int updateByPrimaryKey(EtrPledgeComponent record);

    int deleteByEntrustId(EtrPledgeComponent record);
}