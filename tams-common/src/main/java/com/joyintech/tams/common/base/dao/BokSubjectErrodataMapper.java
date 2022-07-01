package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.BokSubjectErrodata;
import com.joyintech.tams.common.base.entity.BokSubjectErrodataWithBLOBs;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface BokSubjectErrodataMapper {
    int deleteByPrimaryKey(String datauuid);

    int deleteByWorkflowId(String workflowId);

    int updateByWorkflowIdSelective(BokSubjectErrodataWithBLOBs record);

    int updateByWorkflowId(BokSubjectErrodataWithBLOBs record);

    int insertBasicByWorkflowId(Map paraMap);

    int insert(BokSubjectErrodataWithBLOBs record);

    int insertSelective(BokSubjectErrodataWithBLOBs record);

    int insertBasicByBasicInfo(BokSubjectErrodataWithBLOBs record);

    BokSubjectErrodataWithBLOBs selectByPrimaryKey(String datauuid);

    int updateByPrimaryKeySelective(BokSubjectErrodataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BokSubjectErrodataWithBLOBs record);

    int updateByPrimaryKey(BokSubjectErrodata record);
}