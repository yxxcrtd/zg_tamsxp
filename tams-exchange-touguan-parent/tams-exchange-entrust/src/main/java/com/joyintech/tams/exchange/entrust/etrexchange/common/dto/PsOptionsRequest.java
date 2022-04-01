/*
 * 文件名：EtrExchangeTradeQuotation001Dto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.exchange.entrust.etrexchange.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 〈功能详细描述〉
 * @author meifaxin
 * @version 1.0 2021年12月21日
 * @see PsOptionsRequest
 * @since 1.0
 */
@ApiModel(value="获取交易方向请求参数",description="获取交易方向请求参数")
public class PsOptionsRequest {


    @ApiModelProperty(name="finprodId",value="金融产品代码",example="A1")
    private String finprodId;
    @ApiModelProperty(required = true,name="tradePlat",value="交易平台",example="A1")
    private String tradePlat;

    @ApiModelProperty(name="finprodType2",value="投管资产类型",example="A1")
    private String finprodType2;

    @ApiModelProperty(name="othCodeType",value="投管资产类型",example="A1")
    private String othCodeType;

    @ApiModelProperty(name="ps",value="委托方向",example="A1")
    private String ps;

    public String getTradePlat() {
        return tradePlat;
    }

    public void setTradePlat(String tradePlat) {
        this.tradePlat = tradePlat;
    }

    public String getFinprodType2() {
        return finprodType2;
    }

    public String getOthCodeType() {
        return othCodeType;
    }

    public void setOthCodeType(String othCodeType) {
        this.othCodeType = othCodeType;
    }

    public void setFinprodType2(String finprodType2) {
        this.finprodType2 = finprodType2;
    }

    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }
}
