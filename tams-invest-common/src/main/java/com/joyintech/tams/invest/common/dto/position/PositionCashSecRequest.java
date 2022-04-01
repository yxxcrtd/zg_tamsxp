package com.joyintech.tams.invest.common.dto.position;

import java.util.List;

/**
 * 组合数据集
 */
public class PositionCashSecRequest {

    private String flowType; // etr 指令 trd 交易

    List<PositionCashSecDto>  ptlList;

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public List<PositionCashSecDto> getPtlList() {
        return ptlList;
    }

    public void setPtlList(List<PositionCashSecDto> ptlList) {
        this.ptlList = ptlList;
    }
}
