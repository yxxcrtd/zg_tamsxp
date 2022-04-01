package com.joyintech.tams.common.base.entity;

import com.joyintech.framework.base.BaseEntity;

public class ChbEquTmp extends BaseEntity {
    /**
     * null
     */
    private String registBankCode;

    /**
     * null
     */
    private String personalIdentifyType;

    /**
     * null
     */
    private String institutionIdentifyType;

    /**
     * null
     */
    private String otherIdentifyName;

    /**
     * null
     */
    private String identifyNumber;

    /**
     * null
     */
    private String holderName;

    /**
     * 取得 null
     * @return null
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 设置 null
     * @param registBankCode null
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getPersonalIdentifyType() {
        return personalIdentifyType;
    }

    /**
     * 设置 null
     * @param personalIdentifyType null
     */
    public void setPersonalIdentifyType(String personalIdentifyType) {
        this.personalIdentifyType = personalIdentifyType == null ? null : personalIdentifyType.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getInstitutionIdentifyType() {
        return institutionIdentifyType;
    }

    /**
     * 设置 null
     * @param institutionIdentifyType null
     */
    public void setInstitutionIdentifyType(String institutionIdentifyType) {
        this.institutionIdentifyType = institutionIdentifyType == null ? null : institutionIdentifyType.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getOtherIdentifyName() {
        return otherIdentifyName;
    }

    /**
     * 设置 null
     * @param otherIdentifyName null
     */
    public void setOtherIdentifyName(String otherIdentifyName) {
        this.otherIdentifyName = otherIdentifyName == null ? null : otherIdentifyName.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getIdentifyNumber() {
        return identifyNumber;
    }

    /**
     * 设置 null
     * @param identifyNumber null
     */
    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber == null ? null : identifyNumber.trim();
    }

    /**
     * 取得 null
     * @return null
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * 设置 null
     * @param holderName null
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName == null ? null : holderName.trim();
    }
}