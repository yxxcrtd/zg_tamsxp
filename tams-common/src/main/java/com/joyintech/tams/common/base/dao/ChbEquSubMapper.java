package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.ChbEquSub;
import com.joyintech.tams.common.base.entity.ChbEquSubKey;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbEquSubMapper {
    int deleteByPrimaryKey(ChbEquSubKey key);

    int insert(ChbEquSub record);

    int insertSelective(ChbEquSub record);

    int insertBasicByBasicInfo(ChbEquSub record);

    ChbEquSub selectByPrimaryKey(ChbEquSubKey key);

    int updateByPrimaryKeySelective(ChbEquSub record);

    int updateByPrimaryKey(ChbEquSub record);
}