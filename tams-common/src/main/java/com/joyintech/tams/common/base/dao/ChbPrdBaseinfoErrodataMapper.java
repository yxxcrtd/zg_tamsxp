package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.ChbPrdBaseinfoErrodata;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfoErrodataWithBLOBs;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbPrdBaseinfoErrodataMapper {
    int deleteByPrimaryKey(String datauuid);

    int deleteByWorkflowId(String workflowId);

    int updateByWorkflowIdSelective(ChbPrdBaseinfoErrodataWithBLOBs record);

    int updateByWorkflowId(ChbPrdBaseinfoErrodataWithBLOBs record);

    int insertBasicByWorkflowId(Map paraMap);

    int insert(ChbPrdBaseinfoErrodataWithBLOBs record);

    int insertSelective(ChbPrdBaseinfoErrodataWithBLOBs record);

    int insertBasicByBasicInfo(ChbPrdBaseinfoErrodataWithBLOBs record);

    ChbPrdBaseinfoErrodataWithBLOBs selectByPrimaryKey(String datauuid);

    int updateByPrimaryKeySelective(ChbPrdBaseinfoErrodataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ChbPrdBaseinfoErrodataWithBLOBs record);

    int updateByPrimaryKey(ChbPrdBaseinfoErrodata record);
}