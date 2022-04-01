package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.EtrEntrustDetailBackUp;
import com.joyintech.tams.common.base.entity.EtrEntrustDetailBackUpKey;
import com.joyintech.tams.common.base.entity.EtrEntrustDetailKey;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtrEntrustDetailBackUpMapper {
    int deleteByPrimaryKey(EtrEntrustDetailBackUpKey key);

    int insert(EtrEntrustDetailBackUp record);

    int insertSelective(EtrEntrustDetailBackUp record);

    int insertBasicByBasicInfo(EtrEntrustDetailBackUp record);

    EtrEntrustDetailBackUp selectByPrimaryKey(EtrEntrustDetailBackUpKey key);

    int updateByPrimaryKeySelective(EtrEntrustDetailBackUp record);

    int updateByPrimaryKey(EtrEntrustDetailBackUp record);

    List<EtrEntrustDetailBackUp> selectList(String entrustId);

    int deleteByEntrustId(String entrustId);
}