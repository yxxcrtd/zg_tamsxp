package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.SzdSjsgb;
import org.springframework.stereotype.Repository;

@Repository
public interface SzdSjsgbMapper {
    int deleteByPrimaryKey(String seqno);

    int insert(SzdSjsgb record);

    int insertSelective(SzdSjsgb record);

    int insertBasicByBasicInfo(SzdSjsgb record);

    SzdSjsgb selectByPrimaryKey(String seqno);

    int updateByPrimaryKeySelective(SzdSjsgb record);

    int updateByPrimaryKey(SzdSjsgb record);
}