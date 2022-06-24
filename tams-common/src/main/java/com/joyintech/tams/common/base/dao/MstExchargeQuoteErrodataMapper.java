package com.joyintech.tams.common.base.dao;

import com.joyintech.tams.common.base.entity.MstExchargeQuoteErrodata;
import com.joyintech.tams.common.base.entity.MstExchargeQuoteErrodataWithBLOBs;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface MstExchargeQuoteErrodataMapper {
    int deleteByPrimaryKey(String datauuid);

    int deleteByWorkflowId(String workflowId);

    int updateByWorkflowIdSelective(MstExchargeQuoteErrodataWithBLOBs record);

    int updateByWorkflowId(MstExchargeQuoteErrodataWithBLOBs record);

    int insertBasicByWorkflowId(Map paraMap);

    int insert(MstExchargeQuoteErrodataWithBLOBs record);

    int insertSelective(MstExchargeQuoteErrodataWithBLOBs record);

    int insertBasicByBasicInfo(MstExchargeQuoteErrodataWithBLOBs record);

    MstExchargeQuoteErrodataWithBLOBs selectByPrimaryKey(String datauuid);

    int updateByPrimaryKeySelective(MstExchargeQuoteErrodataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MstExchargeQuoteErrodataWithBLOBs record);

    int updateByPrimaryKey(MstExchargeQuoteErrodata record);
}