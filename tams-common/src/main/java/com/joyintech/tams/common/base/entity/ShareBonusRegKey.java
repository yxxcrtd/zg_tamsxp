package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

import java.util.Date;

public class ShareBonusRegKey extends BaseEntity {
    /**
     * 债券代码，市场代码
     */
    private String sharesId;

    /**
     * 权益登记日
     */
    private Date regDate;

    public String getSharesId() {
        return sharesId;
    }

    public void setSharesId(String sharesId) {
        this.sharesId = sharesId;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
}