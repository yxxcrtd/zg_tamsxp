package com.joyintech.tams.exchange.common.bean.investment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "指令接收|放弃接口请求报文", description = "指令接收|放弃接口请求报文")
public class EntrustOpeBean implements Serializable {
    private static final long serialVersionUID = -7955113659323809773L;

    @ApiModelProperty(name = "entrustId", value = "指令ID")
    private String entrustId;
    @ApiModelProperty(name = "recStatus", value = "接收状态 1-接收；2-放弃")
    private String recStatus;
    @ApiModelProperty(name = "trader", value = "交易员")
    private String trader;
    @ApiModelProperty(name = "errorCode", value = "错误码")
    private int errorCode;
    @ApiModelProperty(name = "errorMsg", value = "错误描述")
    private String errorMsg;

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getRecStatus() {
        return recStatus;
    }

    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
