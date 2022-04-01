package com.joyintech.tams.invest.batch.entrust.dto;



import com.joyintech.tams.invest.batch.cfetsentrust.dto.CfetsEntrustClosePosEtrReqDto;

import java.util.List;

/**
 * 头寸信息查询，请求参数
 */
public class EntrustClosePositionReqDto {
    /**
     * 用户名
     */
    private String userId;
    /**
     * 汇总交易编号
     */
    private String totalTradeId;
    /**
     * 指令或交易标识
     */
    private String modelType;
    /**
     * 指令数据集
     */
    private List<EntrustClosePosEtrReqDto> data;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalTradeId() {
        return totalTradeId;
    }

    public void setTotalTradeId(String totalTradeId) {
        this.totalTradeId = totalTradeId;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public List<EntrustClosePosEtrReqDto> getData() {
        return data;
    }

    public void setData(List<EntrustClosePosEtrReqDto> data) {
        this.data = data;
    }
}
