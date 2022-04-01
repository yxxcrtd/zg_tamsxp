package com.joyintech.tams.exchange.common.db.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.json.BigDecimalSerializer;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class InstructionDetailHistory implements Serializable, KeyMethodInterface<String> {

    @NotNull
    @Size(max = 64)
    @Id
    private String id;

    @NotNull
    @Size(max = 32)
    private String entrustId = "";// 委托代码

    @Size(max = 32)
    private String portfolioId = "";// 组合代码

    @Size(max = 200)
    private String portfolioName = "";//组合名称

    @Digits(integer = 30, fraction = 2)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal disRate;//分配比例

    @Digits(integer = 30, fraction = 2)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal entrustAmt;//委托金额

    @Digits(integer = 30, fraction = 4)
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal entrustNum;//委托数量

    private Integer count;//委托数量

    @Size(max = 50)
    private String targetType = "";//目标类型


    @Id
    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public BigDecimal getDisRate() {
        return disRate;
    }

    public void setDisRate(BigDecimal disRate) {
        this.disRate = disRate;
    }

    public BigDecimal getEntrustAmt() {
        return entrustAmt;
    }

    public void setEntrustAmt(BigDecimal entrustAmt) {
        this.entrustAmt = entrustAmt;
    }

    public BigDecimal getEntrustNum() {
        return entrustNum;
    }

    public void setEntrustNum(BigDecimal entrustNum) {
        this.entrustNum = entrustNum;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
