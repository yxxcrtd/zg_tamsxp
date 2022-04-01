package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel(value = "删除账户终端映射信息请求对象", description = "删除账户终端映射信息请求对象")
public class DeleteTerminalMappingRequest extends BaseRequest{
    private static final long serialVersionUID = 2895126585869543241L;

    @ApiModelProperty(required = true, name = "id", value = "主键ID", example = "1234")
    @NotBlank(message = "账户终端映射信息ID不可为空")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
