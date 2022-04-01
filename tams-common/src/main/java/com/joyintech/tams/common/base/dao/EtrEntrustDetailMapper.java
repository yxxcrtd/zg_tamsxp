package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.EtrEntrustDetail;
import com.joyintech.tams.common.base.entity.EtrEntrustDetailKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtrEntrustDetailMapper {
    int deleteByPrimaryKey(EtrEntrustDetailKey key);

    int insert(EtrEntrustDetail record);

    int insertSelective(EtrEntrustDetail record);

    int insertBasicByBasicInfo(EtrEntrustDetail record);

    EtrEntrustDetail selectByPrimaryKey(EtrEntrustDetailKey key);

    int updateByPrimaryKeySelective(EtrEntrustDetail record);

    int updateByPrimaryKey(EtrEntrustDetail record);

    List<EtrEntrustDetail> selectList(String entrustId);

    int deleteByEntrustId(String entrustId);
}