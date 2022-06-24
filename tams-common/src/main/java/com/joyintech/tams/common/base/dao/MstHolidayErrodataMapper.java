package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.MstHolidayErrodata;
import com.joyintech.tams.common.base.entity.MstHolidayErrodataWithBLOBs;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface MstHolidayErrodataMapper {
    int deleteByPrimaryKey(String datauuid);

    int deleteByWorkflowId(String workflowId);

    int updateByWorkflowIdSelective(MstHolidayErrodataWithBLOBs record);

    int updateByWorkflowId(MstHolidayErrodataWithBLOBs record);

    int insertBasicByWorkflowId(Map paraMap);

    int insert(MstHolidayErrodataWithBLOBs record);

    int insertSelective(MstHolidayErrodataWithBLOBs record);

    int insertBasicByBasicInfo(MstHolidayErrodataWithBLOBs record);

    MstHolidayErrodataWithBLOBs selectByPrimaryKey(String datauuid);

    int updateByPrimaryKeySelective(MstHolidayErrodataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MstHolidayErrodataWithBLOBs record);

    int updateByPrimaryKey(MstHolidayErrodata record);
}