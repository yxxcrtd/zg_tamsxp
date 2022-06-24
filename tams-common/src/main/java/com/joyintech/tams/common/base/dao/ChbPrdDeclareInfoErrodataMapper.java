package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.ChbPrdDeclareInfoErrodata;
import com.joyintech.tams.common.base.entity.ChbPrdDeclareInfoErrodataWithBLOBs;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbPrdDeclareInfoErrodataMapper {
    int deleteByPrimaryKey(String datauuid);

    int deleteByWorkflowId(String workflowId);

    int updateByWorkflowIdSelective(ChbPrdDeclareInfoErrodataWithBLOBs record);

    int updateByWorkflowId(ChbPrdDeclareInfoErrodataWithBLOBs record);

    int insertBasicByWorkflowId(Map paraMap);

    int insert(ChbPrdDeclareInfoErrodataWithBLOBs record);

    int insertSelective(ChbPrdDeclareInfoErrodataWithBLOBs record);

    int insertBasicByBasicInfo(ChbPrdDeclareInfoErrodataWithBLOBs record);

    ChbPrdDeclareInfoErrodataWithBLOBs selectByPrimaryKey(String datauuid);

    int updateByPrimaryKeySelective(ChbPrdDeclareInfoErrodataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ChbPrdDeclareInfoErrodataWithBLOBs record);

    int updateByPrimaryKey(ChbPrdDeclareInfoErrodata record);
}