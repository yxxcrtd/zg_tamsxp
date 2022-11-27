package com.joyintech.tams.invest.risk.valtblimport.dto;

import java.math.BigDecimal;
import java.util.Date;

public class RskValtblDataDto {
    /**
     * 实体类含义: 估值数据表Dto
     */

    /**
     * 流水号
     */
    private String seqNo;

    /**
     * 估值文件ID
     */
    private String fileInfoId;

    /**
     * 科目代码
     */
    private String subCode;

    /**
     * 科目名称
     */
    private String subName;

    /**
     * 一级类别
     */
    private String subOneCat;

    /**
     * 二级类别
     */
    private String subTwoCat;

    /**
     * 数量
     */
    private BigDecimal quantity;

    /**
     * 单位成本
     */
    private BigDecimal unitCost;

    /**
     * 本币成本
     */
    private BigDecimal securityCost;

    /**
     * 成本占比
     */
    private BigDecimal ctnwr;

    /**
     * 市值（本币）
     */
    private BigDecimal smv;

    /**
     * 市值占比
     */
    private BigDecimal mcr;

    /**
     * 估值增值（本币）
     */
    private BigDecimal valueAdded;

    /**
     * 利息（本币）
     */
    private BigDecimal interest;

    /**
     * 股利/红利（本币）
     */
    private BigDecimal dividend;

    /**
     * 利息调整（本币）
     */
    private BigDecimal intAdj;

    /**
     * 减值（本币）
     */
    private BigDecimal devalue;

    /**
     * 估值增值（本币）
     */
    private BigDecimal valInc;

    /**
     * 逾期利息（本币）
     */
    private BigDecimal lateInt;

    /**
     * 影子价格估值（本币）
     */
    private BigDecimal shadowPriVal;

    /**
     * 币种
     */
    private String ccy;

    /**
     * 异常标识
     */
    private int expFlag;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 跟新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(String fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubOneCat() {
        return subOneCat;
    }

    public void setSubOneCat(String subOneCat) {
        this.subOneCat = subOneCat;
    }

    public String getSubTwoCat() {
        return subTwoCat;
    }

    public void setSubTwoCat(String subTwoCat) {
        this.subTwoCat = subTwoCat;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getSecurityCost() {
        return securityCost;
    }

    public void setSecurityCost(BigDecimal securityCost) {
        this.securityCost = securityCost;
    }

    public BigDecimal getCtnwr() {
        return ctnwr;
    }

    public void setCtnwr(BigDecimal ctnwr) {
        this.ctnwr = ctnwr;
    }

    public BigDecimal getSmv() {
        return smv;
    }

    public void setSmv(BigDecimal smv) {
        this.smv = smv;
    }

    public BigDecimal getMcr() {
        return mcr;
    }

    public void setMcr(BigDecimal mcr) {
        this.mcr = mcr;
    }

    public BigDecimal getValueAdded() {
        return valueAdded;
    }

    public void setValueAdded(BigDecimal valueAdded) {
        this.valueAdded = valueAdded;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getDividend() {
        return dividend;
    }

    public void setDividend(BigDecimal dividend) {
        this.dividend = dividend;
    }

    public BigDecimal getIntAdj() {
        return intAdj;
    }

    public void setIntAdj(BigDecimal intAdj) {
        this.intAdj = intAdj;
    }

    public BigDecimal getDevalue() {
        return devalue;
    }

    public void setDevalue(BigDecimal devalue) {
        this.devalue = devalue;
    }

    public BigDecimal getValInc() {
        return valInc;
    }

    public void setValInc(BigDecimal valInc) {
        this.valInc = valInc;
    }

    public BigDecimal getLateInt() {
        return lateInt;
    }

    public void setLateInt(BigDecimal lateInt) {
        this.lateInt = lateInt;
    }

    public BigDecimal getShadowPriVal() {
        return shadowPriVal;
    }

    public void setShadowPriVal(BigDecimal shadowPriVal) {
        this.shadowPriVal = shadowPriVal;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public int getExpFlag() {
        return expFlag;
    }

    public void setExpFlag(int dataExpFlag) {
        this.expFlag = expFlag;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
