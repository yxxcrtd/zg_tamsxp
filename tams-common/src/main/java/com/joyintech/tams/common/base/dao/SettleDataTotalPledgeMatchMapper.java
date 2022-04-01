package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.SettleDataTotalPledgeMatch;
import com.joyintech.tams.common.base.entity.SettleDataTotalPledgeMatchKey;
import org.springframework.stereotype.Repository;

@Repository
public interface SettleDataTotalPledgeMatchMapper {
    int deleteByPrimaryKey(SettleDataTotalPledgeMatchKey key);

    int insert(SettleDataTotalPledgeMatch record);

    int insertSelective(SettleDataTotalPledgeMatch record);

    int insertBasicByBasicInfo(SettleDataTotalPledgeMatch record);

    SettleDataTotalPledgeMatch selectByPrimaryKey(SettleDataTotalPledgeMatchKey key);

    int updateByPrimaryKeySelective(SettleDataTotalPledgeMatch record);

    int updateByPrimaryKey(SettleDataTotalPledgeMatch record);
}