package com.joyintech.tams.exchange.entrust.etrexchange.common.dto;

import com.joyintech.tams.common.base.entity.EtrEntrust;

/**
 * @Classname SubEqutityDto
 * @Description 配股配债已认购权益Dto
 * @author zhuhanlu 
 * @date 2020/12/12 16:17 
 * @version 1.0 
 */
public class SubEqutityDto extends EtrEntrust {

    /**
     * 证券账户
     */
    private String secAcctId;

    public String getSecAcctId() {
        return secAcctId;
    }

    public void setSecAcctId(String secAcctId) {
        this.secAcctId = secAcctId;
    }
}
