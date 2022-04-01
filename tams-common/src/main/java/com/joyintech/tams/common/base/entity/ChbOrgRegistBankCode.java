package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

public class ChbOrgRegistBankCode extends BaseEntity {
    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 登记银行代码
     */
    private String registBankCode;
    /**
     * 总行机构代码
     */
    private String pbcBankCode;
    
    /**
     * 是否理财子公司
     */
    private String isFinSubCompany;


    /**
     * 所属银保监局
     */
    private String belogCodeCBRC;
    
    /**
     * 社会信用代码
     */
    private String insitituteCode;
    

    public String getPbcBankCode() {
		return pbcBankCode;
	}

	public void setPbcBankCode(String pbcBankCode) {
		this.pbcBankCode = pbcBankCode;
	}

	public String getIsFinSubCompany() {
		return isFinSubCompany;
	}

	public void setIsFinSubCompany(String isFinSubCompany) {
		this.isFinSubCompany = isFinSubCompany;
	}

	public String getInsitituteCode() {
		return insitituteCode;
	}

	public void setInsitituteCode(String insitituteCode) {
		this.insitituteCode = insitituteCode;
	}

    public String getBelogCodeCBRC() {
        return belogCodeCBRC;
    }

    public void setBelogCodeCBRC(String belogCodeCBRC) {
        this.belogCodeCBRC = belogCodeCBRC;
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

    /**
     * 取得 登记银行代码
     * @return 登记银行代码
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 设置 登记银行代码
     * @param registBankCode 登记银行代码
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
    }
}