package com.joyintech.tams.exchange.datacommon.db.dao;

import com.joyintech.tams.exchange.common.db.domain.InstructionHistory;
import com.joyintech.tams.exchange.common.db.domain.InstructionHistoryExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionHistoryMapper extends IMybatisMapper<InstructionHistory, String, InstructionHistoryExample> {
    int countByExample(InstructionHistoryExample example);

    int deleteByExample(InstructionHistoryExample example);

    int deleteByPrimaryKey(String entrustId);

    int deleteBatchByPrimaryKey(List<String> ids);

    int insert(InstructionHistory record);

    int insertSelective(InstructionHistory record);

    int updateOrInsertSelective(InstructionHistory record);

    List<InstructionHistory> selectByExample(InstructionHistoryExample example);

    InstructionHistory selectByPrimaryKey(String entrustId);

    int updateByExampleSelective(@Param("record") InstructionHistory record, @Param("example") InstructionHistoryExample example);

    int updateByExample(@Param("record") InstructionHistory record, @Param("example") InstructionHistoryExample example);

    int updateByPrimaryKeySelective(InstructionHistory record);

    int updateByPrimaryKey(InstructionHistory record);

}