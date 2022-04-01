package com.joyintech.tams.exchange.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@ApiModel(value = "查询组合计算请求对象", description = "查询组合计算请求对象")
public class PortfolioCalculateRequest extends BaseRequest {

    @JsonProperty("instructionId")
    private String instructionId;

    @JsonProperty("entrustProp")
    private String entrustProp;

    @JsonProperty("entrustPrice")
    private String entrustPrice;

    @JsonProperty("countNum")
    private String countNum;

    @JsonProperty("discount")
    private String discount;

    @JsonProperty("entrustVolume")
    private String entrustVolume;

    public PortfolioCalculateRequest instructionId(String instructionId) {
        this.instructionId = instructionId;
        return this;
    }

    /**
     * 指令代码
     *
     * @return instructionId
     */
    @ApiModelProperty(required = true, value = "指令代码")
    @NotNull


    public String getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(String instructionId) {
        this.instructionId = instructionId;
    }

    public PortfolioCalculateRequest entrustProp(String entrustProp) {
        this.entrustProp = entrustProp;
        return this;
    }

    /**
     * 价格类型
     *
     * @return entrustProp
     */
    @ApiModelProperty(required = true, value = "价格类型")
    @NotNull


    public String getEntrustProp() {
        return entrustProp;
    }

    public void setEntrustProp(String entrustProp) {
        this.entrustProp = entrustProp;
    }

    public PortfolioCalculateRequest entrustPrice(String entrustPrice) {
        this.entrustPrice = entrustPrice;
        return this;
    }

    /**
     * 委托价格
     *
     * @return entrustPrice
     */
    @ApiModelProperty(required = true, value = "委托价格")
    @NotNull


    public String getEntrustPrice() {
        return entrustPrice;
    }

    public void setEntrustPrice(String entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public PortfolioCalculateRequest countNum(String countNum) {
        this.countNum = countNum;
        return this;
    }

    /**
     * 全价
     *
     * @return countNum
     */
    @ApiModelProperty(required = true, value = "全价")
    @NotNull


    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public PortfolioCalculateRequest discount(String discount) {
        this.discount = discount;
        return this;
    }

    /**
     * 折算率
     *
     * @return discount
     */
    @ApiModelProperty(required = true, value = "折算率")
    @NotNull


    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public PortfolioCalculateRequest entrustVolume(String entrustVolume) {
        this.entrustVolume = entrustVolume;
        return this;
    }

    /**
     * 委托额度
     *
     * @return entrustVolume
     */
    @ApiModelProperty(required = true, value = "委托额度")
    @NotNull


    public String getEntrustVolume() {
        return entrustVolume;
    }

    public void setEntrustVolume(String entrustVolume) {
        this.entrustVolume = entrustVolume;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PortfolioCalculateRequest portfolioCalculateRequest = (PortfolioCalculateRequest) o;
        return Objects.equals(this.instructionId, portfolioCalculateRequest.instructionId) &&
                Objects.equals(this.entrustProp, portfolioCalculateRequest.entrustProp) &&
                Objects.equals(this.entrustPrice, portfolioCalculateRequest.entrustPrice) &&
                Objects.equals(this.countNum, portfolioCalculateRequest.countNum) &&
                Objects.equals(this.discount, portfolioCalculateRequest.discount) &&
                Objects.equals(this.entrustVolume, portfolioCalculateRequest.entrustVolume);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructionId, entrustProp, entrustPrice, countNum, discount, entrustVolume);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PortfolioCalculateRequest {\n");

        sb.append("    instructionId: ").append(toIndentedString(instructionId)).append("\n");
        sb.append("    entrustProp: ").append(toIndentedString(entrustProp)).append("\n");
        sb.append("    entrustPrice: ").append(toIndentedString(entrustPrice)).append("\n");
        sb.append("    countNum: ").append(toIndentedString(countNum)).append("\n");
        sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
        sb.append("    entrustVolume: ").append(toIndentedString(entrustVolume)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}