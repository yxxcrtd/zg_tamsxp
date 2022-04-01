package com.joyintech.tams.exchange.datacommon.db.dao;

import com.joyintech.tams.exchange.common.db.domain.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionDetailHistoryMapper extends IMybatisMapper<InstructionDetailHistory, String, InstructionDetailHistoryExample> {
    int countByExample(InstructionDetailHistoryExample example);

    int deleteByExample(InstructionDetailHistoryExample example);

    int deleteByPrimaryKey(String id);

    int insert(InstructionDetailHistory record);

    int insertSelective(InstructionDetailHistory record);
    
    int updateOrInsertSelective(InstructionDetailHistory record);

    int insertBasicByBasicInfo(InstructionDetailHistory record);

    List<InstructionDetailHistory> selectByExample(InstructionDetailHistoryExample example);

    InstructionDetailHistory selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InstructionDetailHistory record, @Param("example") InstructionDetailHistoryExample example);

    int updateByExample(@Param("record") InstructionDetailHistory record, @Param("example") InstructionDetailHistoryExample example);

    int updateByPrimaryKeySelective(InstructionDetailHistory record);

    int updateByPrimaryKey(InstructionDetailHistory record);

}