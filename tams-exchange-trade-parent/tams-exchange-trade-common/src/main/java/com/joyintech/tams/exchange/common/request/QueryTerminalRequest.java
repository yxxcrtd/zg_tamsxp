package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "查询交易终端请求报文", description = "查询交易终端请求报文")
public class QueryTerminalRequest extends BaseRequest {
    private static final long serialVersionUID = -499241057154050476L;

    private String terminalName;

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }
}
