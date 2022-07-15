package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.ChbPrdStatusSub;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbPrdStatusSubMapper {
    int deleteByPrimaryKey(String registSeqno);

    int insert(ChbPrdStatusSub record);

    int insertSelective(ChbPrdStatusSub record);

    int insertBasicByBasicInfo(ChbPrdStatusSub record);

    ChbPrdStatusSub selectByPrimaryKey(String registSeqno);

    int updateByPrimaryKeySelective(ChbPrdStatusSub record);

    int updateByPrimaryKey(ChbPrdStatusSub record);
}