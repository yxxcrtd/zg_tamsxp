package com.joyintech.tams.exchange.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "查询组合信息请求对象", description = "查询组合信息请求对象")
public class PortfolioInfoRequest extends BaseRequest{

    @ApiModelProperty(required = true, name = "portfolioId", value = "组合编号,多组合以逗号分隔", example = "A001")
    @NotBlank(message = "组合编号不可为空")
    private String portfolioId;

    @ApiModelProperty(required = true, name = "finprodId", value = "产品编号", example = "A001")
    @NotBlank(message = "产品编号不可为空")
    private String finprodId;

    @ApiModelProperty(required = true, name = "acctNo", value = "资金账户编号", example = "A001:A1001")
    @NotBlank(message = "资金账户编号不可为空")
    private String acctNo;

    @ApiModelProperty(required = true, name = "secAcctId", value = "证券账户编号", example = "A1001")
    @NotBlank(message = "证券账户编号不可为空")
    private String secAcctId;

    @ApiModelProperty(required = true, name = "tradeMarket", value = "交易市场，03 上交所；04 深交所", example = "03")
    @NotBlank(message = "交易市场不可为空")
    private String tradeMarket;

    @ApiModelProperty(required = false, name = "vdate", value = "交易日期", example = "2021-01-01")
    private Date vdate;

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getFinprodId() {
        return finprodId;
    }

    public void setFinprodId(String finprodId) {
        this.finprodId = finprodId;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public String getTradeMarket() {
        return tradeMarket;
    }

    public void setTradeMarket(String tradeMarket) {
        this.tradeMarket = tradeMarket;
    }

    public Date getVdate() {
        return vdate;
    }

    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    @Override
    public String toString() {
        return "PortfolioInfoRequest{" +
                "portfolioId='" + portfolioId + '\'' +
                ", finprodId='" + finprodId + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", secAcctId='" + secAcctId + '\'' +
                ", tradeMarket='" + tradeMarket + '\'' +
                ", vdate=" + vdate +
                '}';
    }
}
