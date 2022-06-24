package com.joyintech.tams.common.base.entity;

public class MstExchargeQuoteErrodataWithBLOBs extends MstExchargeQuoteErrodata {
    /**
     * 原始数据
     */
    private String orgData;

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 取得 原始数据
     * @return 原始数据
     */
    public String getOrgData() {
        return orgData;
    }

    /**
     * 设置 原始数据
     * @param orgData 原始数据
     */
    public void setOrgData(String orgData) {
        this.orgData = orgData == null ? null : orgData.trim();
    }

    /**
     * 取得 错误信息
     * @return 错误信息
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * 设置 错误信息
     * @param errMsg 错误信息
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }
}