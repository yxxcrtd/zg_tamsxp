package com.joyintech.tams.svreport.chb.monthcollect.dto;

import com.joyintech.framework.base.BaseEntity;

import java.math.BigDecimal;

public class PrdPeriodyieldForMonthCollect extends BaseEntity {
   //产品代码
   String prodId;
   //净值
   BigDecimal prodValue;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public BigDecimal getProdValue() {
        return prodValue;
    }

    public void setProdValue(BigDecimal prodValue) {
        this.prodValue = prodValue;
    }
}