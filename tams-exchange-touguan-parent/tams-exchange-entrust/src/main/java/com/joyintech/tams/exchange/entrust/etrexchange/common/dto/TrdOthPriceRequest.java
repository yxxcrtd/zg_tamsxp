package com.joyintech.tams.exchange.entrust.etrexchange.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="配债、配股价格请求参数",description="配股价格请求参数")
public class TrdOthPriceRequest {

    @ApiModelProperty(name="finprodMarketId",value="市场代码",example="12")
    private String  finprodMarketId;

    @ApiModelProperty(name="othCodeType",value="其他代码类型",example="12")
    private String   othCodeType;


    public String getFinprodMarketId() {
        return finprodMarketId;
    }

    public void setFinprodMarketId(String finprodMarketId) {
        this.finprodMarketId = finprodMarketId;
    }

    public String getOthCodeType() {
        return othCodeType;
    }

    public void setOthCodeType(String othCodeType) {
        this.othCodeType = othCodeType;
    }
}
