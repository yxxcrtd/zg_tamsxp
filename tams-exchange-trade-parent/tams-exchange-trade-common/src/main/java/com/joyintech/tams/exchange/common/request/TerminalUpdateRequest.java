package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author swwan
 * @date 2021/12/22 - 15:47
 */
@ApiModel(value = "更新场内交易终端请求对象", description = "更新场内交易终端请求对象")
public class TerminalUpdateRequest extends BaseRequest {

    @ApiModelProperty(required = true, name = "sourceTerminalName", value = "更新前终端名称", example = "terminal-01")
    private String sourceTerminalName;

    @ApiModelProperty(required = true, name = "destTerminalName", value = "更新后终端名称", example = "terminal-02")
    private String destTerminalName;

    @ApiModelProperty(required = true, name = "terminalType", value = "终端类型", example = "1")
    private int terminalType;

    public String getSourceTerminalName() {
        return sourceTerminalName;
    }

    public void setSourceTerminalName(String sourceTerminalName) {
        this.sourceTerminalName = sourceTerminalName;
    }

    public String getDestTerminalName() {
        return destTerminalName;
    }

    public void setDestTerminalName(String destTerminalName) {
        this.destTerminalName = destTerminalName;
    }

    public int getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(int terminalType) {
        this.terminalType = terminalType;
    }


}
