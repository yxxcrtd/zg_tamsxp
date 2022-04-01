package com.joyintech.tams.exchange.entrust.etrexchange.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="获得权益额度共通请求参数",description="获得权益额度共通请求参数")
public class FinProductQutoRequest {

    /**
     * 资产代码
     */
    @ApiModelProperty(name="finprodId",value="资产代码")
    private String finprodId;

    /**
     * 组合代码
     */
    @ApiModelProperty(name="portfolioId",value="组合代码")
    private String portfolioId;

    /**
     * 证券账户
     */
    @ApiModelProperty(name="secAcctId",value="证券账户")
    private String secAcctId;


    /**
     * 业务类型
     */
    @ApiModelProperty(name="othType",value="业务类型")
    private String  othType;


    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public String getOthType() {
        return othType;
    }

    public void setOthType(String othType) {
        this.othType = othType;
    }
}
