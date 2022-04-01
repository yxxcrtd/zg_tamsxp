package com.joyintech.tams.exchange.datacommon.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.exchange.common.db.domain.InstructionDetail;
import com.joyintech.tams.exchange.common.db.domain.InstructionDetailExample;
import com.joyintech.tams.exchange.common.db.domain.InstructionPledge;

@Repository
public interface InstructionDetailMapper extends IMybatisMapper<InstructionDetail, String, InstructionDetailExample> {
    int countByExample(InstructionDetailExample example);

    int deleteByExample(InstructionDetailExample example);

    int deleteByPrimaryKey(String id);

    int deleteBatchByPrimaryKey(List<String> ids);

    int insert(InstructionDetail record);

    int insertSelective(InstructionDetail record);
    
    int updateOrInsertSelective(InstructionDetail record);

    int insertBasicByBasicInfo(InstructionDetail record);

    List<InstructionDetail> selectByExample(InstructionDetailExample example);

    InstructionDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") InstructionDetail record, @Param("example") InstructionDetailExample example);

    int updateByExample(@Param("record") InstructionDetail record, @Param("example") InstructionDetailExample example);

    int updateByPrimaryKeySelective(InstructionDetail record);

    int updateByPrimaryKey(InstructionDetail record);
}