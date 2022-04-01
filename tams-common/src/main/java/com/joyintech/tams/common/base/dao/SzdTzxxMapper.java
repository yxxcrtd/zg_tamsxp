package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.SzdTzxx;
import org.springframework.stereotype.Repository;

@Repository
public interface SzdTzxxMapper {
    int deleteByPrimaryKey(String seqno);

    int insert(SzdTzxx record);

    int insertSelective(SzdTzxx record);

    int insertBasicByBasicInfo(SzdTzxx record);

    SzdTzxx selectByPrimaryKey(String seqno);

    int updateByPrimaryKeySelective(SzdTzxx record);

    int updateByPrimaryKey(SzdTzxx record);
}