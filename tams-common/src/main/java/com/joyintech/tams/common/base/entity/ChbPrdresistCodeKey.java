package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

public class ChbPrdresistCodeKey extends BaseEntity {
    /**
     * 产品代码
     */
    private String prodid;

    /**
     * 机构代码
     */
    private String orgCode;
    
    /**
     * add by liubin
     * 产品登记编码
     */
    private String regCode;
    
    /**
     * 判断是否是产品回灌 flag =1 ：产品回灌
     */
    private String flag;

    private String prodInsId;
    
    
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag == null ? null : flag.trim();
	}

    /**
     * 取得 产品代码
     * @return 产品代码
     */
    public String getProdid() {
        return prodid;
    }

    /**
     * 设置 产品代码
     * @param prodid 产品代码
     */
    public void setProdid(String prodid) {
        this.prodid = prodid == null ? null : prodid.trim();
    }

    /**
     * 取得 机构代码
     * @return 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

	public String getReg_code() {
		return regCode;
	}

	public void setReg_code(String regCode) {
		this.regCode = regCode == null ? null : regCode.trim();
	}

    public String getProdInsId() {
        return prodInsId;
    }

    public void setProdInsId(String prodInsId) {
        this.prodInsId = prodInsId;
    }
}