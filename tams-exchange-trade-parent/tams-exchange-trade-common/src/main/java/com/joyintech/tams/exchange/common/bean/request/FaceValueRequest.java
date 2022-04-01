package com.joyintech.tams.exchange.common.bean.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel(value="最新面值接口",description="最新面值接口请求参数")
public class FaceValueRequest {

    @ApiModelProperty(name="finprodId",value="资产代码",example="F01_100303")
    @NotBlank(message = "资产代码不可为空")
    private String finprodId;

    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }
}
