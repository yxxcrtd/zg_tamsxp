package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查询、删除场内交易服务节点请求对象", description = "查询、删除场内交易服务节点请求对象")
public class QueryServiceNodeRequest extends BaseRequest {
    private static final long serialVersionUID = -405692280259964838L;

    @ApiModelProperty(required = true, name = "nodeName", value = "服务节点名称", example = "terminal-01")
    private String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
