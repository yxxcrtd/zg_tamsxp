package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author swwan
 * @date 2021/12/22 - 15:47
 */
@ApiModel(value = "添加场内交易终端请求对象", description = "添加场内交易终端请求对象")
public class TerminalInsertRequest extends BaseRequest {

    @ApiModelProperty(required = true, name = "terminalName", value = "终端名称", example = "terminal-01")
    private String terminalName;

    @ApiModelProperty(required = true, name = "terminalType", value = "终端类型", example = "1")
    private int terminalType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName == null ? null : terminalName.trim();
    }

    public int getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(int terminalType) {
        this.terminalType = terminalType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
