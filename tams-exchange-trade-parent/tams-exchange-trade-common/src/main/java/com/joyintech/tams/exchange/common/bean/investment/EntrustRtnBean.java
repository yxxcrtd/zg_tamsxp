package com.joyintech.tams.exchange.common.bean.investment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "指令接收|放弃接口返回报文", description = "指令接收|放弃接口返回报文")
public class EntrustRtnBean {

    @ApiModelProperty(name = "entrustId", value = "指令ID")
    private String entrustId;

    @ApiModelProperty(name = "status", value = "处理状态，0-成功；1-失败")
    private String status;

    @ApiModelProperty(name = "remark", value = "失败原因")
    private String remark;

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
