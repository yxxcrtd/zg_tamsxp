package com.joyintech.tams.exchange.entrust.etrexchange.common.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="查询债券对应的最新面值",description="查询债券对应的最新面值")
public class SecFaceValueRequest {

    @ApiModelProperty(name="finprodId",value="金融产品代码",example="A1")
    private String finprodId;


    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }
}
