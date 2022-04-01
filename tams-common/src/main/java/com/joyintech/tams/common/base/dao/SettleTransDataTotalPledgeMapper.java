package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.SettleTransDataTotalPledge;
import com.joyintech.tams.common.base.entity.SettleTransDataTotalPledgeKey;
import org.springframework.stereotype.Repository;

@Repository
public interface SettleTransDataTotalPledgeMapper {
    int deleteByPrimaryKey(SettleTransDataTotalPledgeKey key);

    int insert(SettleTransDataTotalPledge record);

    int insertSelective(SettleTransDataTotalPledge record);

    int insertBasicByBasicInfo(SettleTransDataTotalPledge record);

    SettleTransDataTotalPledge selectByPrimaryKey(SettleTransDataTotalPledgeKey key);

    int updateByPrimaryKeySelective(SettleTransDataTotalPledge record);

    int updateByPrimaryKey(SettleTransDataTotalPledge record);
}