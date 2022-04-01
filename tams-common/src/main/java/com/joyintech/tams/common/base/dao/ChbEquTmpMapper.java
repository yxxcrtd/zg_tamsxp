package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.ChbEquTmp;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbEquTmpMapper {
    int insert(ChbEquTmp record);

    int insertSelective(ChbEquTmp record);

    int insertBasicByBasicInfo(ChbEquTmp record);
}