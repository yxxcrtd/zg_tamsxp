package com.joyintech.tams.exchange.entrust.etrexchange.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 下拉框请求requesyt
 * @author wangyang1
 * @version 1.0 2021年12月29日
 */
@ApiModel(value="证券代码列表查询请求参数",description="证券代码列表查询请求参数")
public class StdsecConverseRatioRequest {

    @ApiModelProperty(name="finprodId",value="资产代码",example="12")
    private String finprodId;


    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }
}
