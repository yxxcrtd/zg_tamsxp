package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;

import java.math.BigDecimal;
import java.util.Date;

public class ShareBonusReg extends ShareBonusRegKey {
    /**
     * 债券代码，市场代码
     */
    private String secAcctId;

    /**
     * 除权日
     */
    private Date delDate;

    /**
     * 派息日
     */
    private Date devidentDate;

    /**
     * 红股上市日
     */
    private Date redShareListingDate;
    /**
     * 每股送转
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal perDevidentAmt;

    /**
     * 每股派息
     */
    private BigDecimal perDevidentCash;


    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }

    public Date getDelDate() {
        return delDate;
    }

    public void setDelDate(Date delDate) {
        this.delDate = delDate;
    }

    public Date getDevidentDate() {
        return devidentDate;
    }

    public void setDevidentDate(Date devidentDate) {
        this.devidentDate = devidentDate;
    }

    public Date getRedShareListingDate() {
        return redShareListingDate;
    }

    public void setRedShareListingDate(Date redShareListingDate) {
        this.redShareListingDate = redShareListingDate;
    }

    public BigDecimal getPerDevidentAmt() {
        return perDevidentAmt;
    }

    public void setPerDevidentAmt(BigDecimal perDevidentAmt) {
        this.perDevidentAmt = perDevidentAmt;
    }

    public BigDecimal getPerDevidentCash() {
        return perDevidentCash;
    }

    public void setPerDevidentCash(BigDecimal perDevidentCash) {
        this.perDevidentCash = perDevidentCash;
    }
}