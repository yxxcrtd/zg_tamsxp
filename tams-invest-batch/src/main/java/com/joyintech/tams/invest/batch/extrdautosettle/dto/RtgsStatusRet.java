package com.joyintech.tams.invest.batch.extrdautosettle.dto;

import java.io.Serializable;

/**
 * 查询RTGS交收状态结果
 *
 * @author swwan
 */
public class RtgsStatusRet implements Serializable, Cloneable {
    private static final long serialVersionUID = -1555309679760299579L;

    /**
     * 成交编号
     */
    private String dealNo;
    /**
     * 状态  DELIVERY_STATUS
     */
    private String status;

    public RtgsStatusRet() {
    }

    public RtgsStatusRet(String dealNo, String status) {
        this.dealNo = dealNo;
        this.status = status;
    }

    public String getDealNo() {
        return dealNo;
    }

    public void setDealNo(String dealNo) {
        this.dealNo = dealNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RtgsStatusRet{" +
                "dealNo='" + dealNo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public RtgsStatusRet clone() {
        RtgsStatusRet ret = null;
        try {
            ret = (RtgsStatusRet) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ret;
    }
}