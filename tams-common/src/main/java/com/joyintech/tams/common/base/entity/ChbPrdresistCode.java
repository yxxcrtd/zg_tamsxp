package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.util.Date;

public class ChbPrdresistCode extends ChbPrdresistCodeKey {
	
	
	private static final long serialVersionUID = -2597395756141282004L;
	
    /**
     * 产品名称
     */
    private String prodname;

    /**
     * 产品登记编码
     */
    private String regCode;

    /**
     * 起息日
     */
    private Date vdate;

    /**
     * 到期日
     */
    private Date mdate;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 投资者类型
     */
    private String investor;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 产品运作模式
     */
    private String operateMode;

    private String interfCheckResult;
    
    private String newProdId;
    
    private String prodInsId;

	public String getNewProdId() {
		return newProdId;
	}

	public void setNewProdId(String newProdId) {
		this.newProdId = newProdId == null ? null : newProdId.trim();
	}

	/**
     * 取得 产品名称
     * @return 产品名称
     */
    public String getProdname() {
        return prodname;
    }

    /**
     * 设置 产品名称
     * @param prodname 产品名称
     */
    public void setProdname(String prodname) {
        this.prodname = prodname == null ? null : prodname.trim();
    }

    /**
     * 取得 产品登记编码
     * @return 产品登记编码
     */
    public String getRegCode() {
        return regCode;
    }

    /**
     * 设置 产品登记编码
     * @param regCode 产品登记编码
     */
    public void setRegCode(String regCode) {
        this.regCode = regCode == null ? null : regCode.trim();
    }

    /**
     * 取得 起息日
     * @return 起息日
     */
    public Date getVdate() {
        return vdate;
    }

    /**
     * 设置 起息日
     * @param vdate 起息日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    /**
     * 取得 到期日
     * @return 到期日
     */
    public Date getMdate() {
        return mdate;
    }

    /**
     * 设置 到期日
     * @param mdate 到期日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    /**
     * 取得 部门代码
     * @return 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    /**
     * 取得 投资者类型
     * @return 投资者类型
     */
    public String getInvestor() {
        return investor;
    }

    /**
     * 设置 投资者类型
     * @param investor 投资者类型
     */
    public void setInvestor(String investor) {
        this.investor = investor == null ? null : investor.trim();
    }

    /**
     * 取得 备注
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getInterfCheckResult() {
		return interfCheckResult;
	}

	public void setInterfCheckResult(String interfCheckResult) {
		this.interfCheckResult = interfCheckResult;
	}
	
    /**
     * 取得 产品运作模式
     * @return 产品运作模式
     */
	public String getOperateMode() {
		return operateMode;
	}

    /**
     * 设置 产品运作模式
     * @param operateMode 产品运作模式
     */
	public void setOperateMode(String operateMode) {
		this.operateMode = operateMode;
	}

    public String getProdInsId() {
        return prodInsId;
    }

    public void setProdInsId(String prodInsId) {
        this.prodInsId = prodInsId;
    }
}