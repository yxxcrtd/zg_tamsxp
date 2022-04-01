package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.json.BigDecimalSerializer;
import java.math.BigDecimal;

public class MstCustomerInfoAdd extends BaseEntity {
    /**
     * 客户代码
     */
    private String customerId;

    /**
     * 机构类型（技术领域），高新技术企业、其他
     */
    private String partyType3;

    /**
     * 机构类型（经济类型），国有企业、集体企业、民营企业、其他企业
     */
    private String partyType2;

    /**
     * 机构类型（规模），大型企业、中小微企业
     */
    private String partyType1;

    /**
     * 国标一级分类
     */
    private String industry;

    /**
     * 国标二级分类
     */
    private String industry2;

    /**
     * 组织机构代码/统一社会信用代码
     */
    private String insitituteCode;

    /**
     * 是否政府融资平台
     */
    private String isGov;

    /**
     * 核心资本（万元），存的是万元
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal mainCapital;

    /**
     * 注册资本（万元），存的是万元
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal registerCapital;

    /**
     * 所属省
     */
    private String province;

    /**
     * 所属市
     */
    private String city;

    /**
     * 所属县
     */
    private String county;

    /**
     * 控股类型
     */
    private String holdingType;

    /**
     * 客户主体类型，我国中央政府、境外主权国家或经济实体区域政府、中国人民银行、境外中央银行等
     */
    private String customerObjType;

    /**
     * 是否上市公司
     */
    private String isListedCompany;

    /**
     * 是否集团客户
     */
    private String isGroupCustomer;

    /**
     * 所属集团客户编号
     */
    private String parentCustomerId;

    /**
     * 所属集团客户名称
     */
    private String parentCustomerName;

    /**
     * 是否民营企业
     */
    private String isPrivate;

    /**
     * 是否为逃废债企业
     */
    private String isEscapeDept;

    /**
     * 授信策略
     */
    private String creditType;

    /**
     * 是否授信类关联交易
     */
    private String isCreditRelatedTrd;

    /**
     * 产业结构调整类型
     */
    private String structAdjustType;

    /**
     * 工业转型升级标识
     */
    private String indusUpgrateType;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证
     */
    private String personId;

    /**
     * 移动电话
     */
    private String mPhone;

    /**
     * 办公电话
     */
    private String phone;

    /**
     * 职位
     */
    private String position;

    /**
     * 工作单位
     */
    private String companyName;

    /**
     * 住址
     */
    private String address;

    /**
     * 电子邮箱
     */
    private String mail;

    /**
     * 财产状况
     */
    private String property;

    /**
     * 企业性质
     */
    private String companyNature;

    /**
     * 外部代码1，存万得代码
     */
    private String linkId1;

    /**
     * 外部代码2，对于平安存客户编号
     */
    private String linkId2;

    /**
     * 外部代码3
     */
    private String linkId3;

    /**
     * 备注
     */
    private String remark;

    /**
     * 核算分类
     */
    private String hsType;

    /**
     * 是否持有金融牌照
     */
    private String isFinLicence;

    /**
     * 行业三级分类
     */
    private String industry3;

    /**
     * 行业四级分类
     */
    private String industry4;

    /**
     * 机构类型(风险)
     */
    private String orgType;

    /**
     * 是否股东及其关联方
     */
    private String isShareholderRelated;

    /**
     * 取得 客户代码
     * @return 客户代码
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 设置 客户代码
     * @param customerId 客户代码
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    /**
     * 取得 机构类型（技术领域），高新技术企业、其他
     * @return 机构类型（技术领域），高新技术企业、其他
     */
    public String getPartyType3() {
        return partyType3;
    }

    /**
     * 设置 机构类型（技术领域），高新技术企业、其他
     * @param partyType3 机构类型（技术领域），高新技术企业、其他
     */
    public void setPartyType3(String partyType3) {
        this.partyType3 = partyType3 == null ? null : partyType3.trim();
    }

    /**
     * 取得 机构类型（经济类型），国有企业、集体企业、民营企业、其他企业
     * @return 机构类型（经济类型），国有企业、集体企业、民营企业、其他企业
     */
    public String getPartyType2() {
        return partyType2;
    }

    /**
     * 设置 机构类型（经济类型），国有企业、集体企业、民营企业、其他企业
     * @param partyType2 机构类型（经济类型），国有企业、集体企业、民营企业、其他企业
     */
    public void setPartyType2(String partyType2) {
        this.partyType2 = partyType2 == null ? null : partyType2.trim();
    }

    /**
     * 取得 机构类型（规模），大型企业、中小微企业
     * @return 机构类型（规模），大型企业、中小微企业
     */
    public String getPartyType1() {
        return partyType1;
    }

    /**
     * 设置 机构类型（规模），大型企业、中小微企业
     * @param partyType1 机构类型（规模），大型企业、中小微企业
     */
    public void setPartyType1(String partyType1) {
        this.partyType1 = partyType1 == null ? null : partyType1.trim();
    }

    /**
     * 取得 国标一级分类
     * @return 国标一级分类
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置 国标一级分类
     * @param industry 国标一级分类
     */
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    /**
     * 取得 国标二级分类
     * @return 国标二级分类
     */
    public String getIndustry2() {
        return industry2;
    }

    /**
     * 设置 国标二级分类
     * @param industry2 国标二级分类
     */
    public void setIndustry2(String industry2) {
        this.industry2 = industry2 == null ? null : industry2.trim();
    }

    /**
     * 取得 组织机构代码/统一社会信用代码
     * @return 组织机构代码/统一社会信用代码
     */
    public String getInsitituteCode() {
        return insitituteCode;
    }

    /**
     * 设置 组织机构代码/统一社会信用代码
     * @param insitituteCode 组织机构代码/统一社会信用代码
     */
    public void setInsitituteCode(String insitituteCode) {
        this.insitituteCode = insitituteCode == null ? null : insitituteCode.trim();
    }

    /**
     * 取得 是否政府融资平台
     * @return 是否政府融资平台
     */
    public String getIsGov() {
        return isGov;
    }

    /**
     * 设置 是否政府融资平台
     * @param isGov 是否政府融资平台
     */
    public void setIsGov(String isGov) {
        this.isGov = isGov == null ? null : isGov.trim();
    }

    /**
     * 取得 核心资本（万元），存的是万元
     * @return 核心资本（万元），存的是万元
     */
    public BigDecimal getMainCapital() {
        return mainCapital;
    }

    /**
     * 设置 核心资本（万元），存的是万元
     * @param mainCapital 核心资本（万元），存的是万元
     */
    public void setMainCapital(BigDecimal mainCapital) {
        this.mainCapital = mainCapital;
    }

    /**
     * 取得 注册资本（万元），存的是万元
     * @return 注册资本（万元），存的是万元
     */
    public BigDecimal getRegisterCapital() {
        return registerCapital;
    }

    /**
     * 设置 注册资本（万元），存的是万元
     * @param registerCapital 注册资本（万元），存的是万元
     */
    public void setRegisterCapital(BigDecimal registerCapital) {
        this.registerCapital = registerCapital;
    }

    /**
     * 取得 所属省
     * @return 所属省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置 所属省
     * @param province 所属省
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 取得 所属市
     * @return 所属市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置 所属市
     * @param city 所属市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 取得 所属县
     * @return 所属县
     */
    public String getCounty() {
        return county;
    }

    /**
     * 设置 所属县
     * @param county 所属县
     */
    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    /**
     * 取得 控股类型
     * @return 控股类型
     */
    public String getHoldingType() {
        return holdingType;
    }

    /**
     * 设置 控股类型
     * @param holdingType 控股类型
     */
    public void setHoldingType(String holdingType) {
        this.holdingType = holdingType == null ? null : holdingType.trim();
    }

    /**
     * 取得 客户主体类型，我国中央政府、境外主权国家或经济实体区域政府、中国人民银行、境外中央银行等
     * @return 客户主体类型，我国中央政府、境外主权国家或经济实体区域政府、中国人民银行、境外中央银行等
     */
    public String getCustomerObjType() {
        return customerObjType;
    }

    /**
     * 设置 客户主体类型，我国中央政府、境外主权国家或经济实体区域政府、中国人民银行、境外中央银行等
     * @param customerObjType 客户主体类型，我国中央政府、境外主权国家或经济实体区域政府、中国人民银行、境外中央银行等
     */
    public void setCustomerObjType(String customerObjType) {
        this.customerObjType = customerObjType == null ? null : customerObjType.trim();
    }

    /**
     * 取得 是否上市公司
     * @return 是否上市公司
     */
    public String getIsListedCompany() {
        return isListedCompany;
    }

    /**
     * 设置 是否上市公司
     * @param isListedCompany 是否上市公司
     */
    public void setIsListedCompany(String isListedCompany) {
        this.isListedCompany = isListedCompany == null ? null : isListedCompany.trim();
    }

    /**
     * 取得 是否集团客户
     * @return 是否集团客户
     */
    public String getIsGroupCustomer() {
        return isGroupCustomer;
    }

    /**
     * 设置 是否集团客户
     * @param isGroupCustomer 是否集团客户
     */
    public void setIsGroupCustomer(String isGroupCustomer) {
        this.isGroupCustomer = isGroupCustomer == null ? null : isGroupCustomer.trim();
    }

    /**
     * 取得 所属集团客户编号
     * @return 所属集团客户编号
     */
    public String getParentCustomerId() {
        return parentCustomerId;
    }

    /**
     * 设置 所属集团客户编号
     * @param parentCustomerId 所属集团客户编号
     */
    public void setParentCustomerId(String parentCustomerId) {
        this.parentCustomerId = parentCustomerId == null ? null : parentCustomerId.trim();
    }

    /**
     * 取得 所属集团客户名称
     * @return 所属集团客户名称
     */
    public String getParentCustomerName() {
        return parentCustomerName;
    }

    /**
     * 设置 所属集团客户名称
     * @param parentCustomerName 所属集团客户名称
     */
    public void setParentCustomerName(String parentCustomerName) {
        this.parentCustomerName = parentCustomerName == null ? null : parentCustomerName.trim();
    }

    /**
     * 取得 是否民营企业
     * @return 是否民营企业
     */
    public String getIsPrivate() {
        return isPrivate;
    }

    /**
     * 设置 是否民营企业
     * @param isPrivate 是否民营企业
     */
    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate == null ? null : isPrivate.trim();
    }

    /**
     * 取得 是否为逃废债企业
     * @return 是否为逃废债企业
     */
    public String getIsEscapeDept() {
        return isEscapeDept;
    }

    /**
     * 设置 是否为逃废债企业
     * @param isEscapeDept 是否为逃废债企业
     */
    public void setIsEscapeDept(String isEscapeDept) {
        this.isEscapeDept = isEscapeDept == null ? null : isEscapeDept.trim();
    }

    /**
     * 取得 授信策略
     * @return 授信策略
     */
    public String getCreditType() {
        return creditType;
    }

    /**
     * 设置 授信策略
     * @param creditType 授信策略
     */
    public void setCreditType(String creditType) {
        this.creditType = creditType == null ? null : creditType.trim();
    }

    /**
     * 取得 是否授信类关联交易
     * @return 是否授信类关联交易
     */
    public String getIsCreditRelatedTrd() {
        return isCreditRelatedTrd;
    }

    /**
     * 设置 是否授信类关联交易
     * @param isCreditRelatedTrd 是否授信类关联交易
     */
    public void setIsCreditRelatedTrd(String isCreditRelatedTrd) {
        this.isCreditRelatedTrd = isCreditRelatedTrd == null ? null : isCreditRelatedTrd.trim();
    }

    /**
     * 取得 产业结构调整类型
     * @return 产业结构调整类型
     */
    public String getStructAdjustType() {
        return structAdjustType;
    }

    /**
     * 设置 产业结构调整类型
     * @param structAdjustType 产业结构调整类型
     */
    public void setStructAdjustType(String structAdjustType) {
        this.structAdjustType = structAdjustType == null ? null : structAdjustType.trim();
    }

    /**
     * 取得 工业转型升级标识
     * @return 工业转型升级标识
     */
    public String getIndusUpgrateType() {
        return indusUpgrateType;
    }

    /**
     * 设置 工业转型升级标识
     * @param indusUpgrateType 工业转型升级标识
     */
    public void setIndusUpgrateType(String indusUpgrateType) {
        this.indusUpgrateType = indusUpgrateType == null ? null : indusUpgrateType.trim();
    }

    /**
     * 取得 性别
     * @return 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置 性别
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * 取得 身份证
     * @return 身份证
     */
    public String getPersonId() {
        return personId;
    }

    /**
     * 设置 身份证
     * @param personId 身份证
     */
    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    /**
     * 取得 移动电话
     * @return 移动电话
     */
    public String getmPhone() {
        return mPhone;
    }

    /**
     * 设置 移动电话
     * @param mPhone 移动电话
     */
    public void setmPhone(String mPhone) {
        this.mPhone = mPhone == null ? null : mPhone.trim();
    }

    /**
     * 取得 办公电话
     * @return 办公电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置 办公电话
     * @param phone 办公电话
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 取得 职位
     * @return 职位
     */
    public String getPosition() {
        return position;
    }

    /**
     * 设置 职位
     * @param position 职位
     */
    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    /**
     * 取得 工作单位
     * @return 工作单位
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置 工作单位
     * @param companyName 工作单位
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * 取得 住址
     * @return 住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置 住址
     * @param address 住址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 取得 电子邮箱
     * @return 电子邮箱
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置 电子邮箱
     * @param mail 电子邮箱
     */
    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    /**
     * 取得 财产状况
     * @return 财产状况
     */
    public String getProperty() {
        return property;
    }

    /**
     * 设置 财产状况
     * @param property 财产状况
     */
    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    /**
     * 取得 企业性质
     * @return 企业性质
     */
    public String getCompanyNature() {
        return companyNature;
    }

    /**
     * 设置 企业性质
     * @param companyNature 企业性质
     */
    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature == null ? null : companyNature.trim();
    }

    /**
     * 取得 外部代码1，存万得代码
     * @return 外部代码1，存万得代码
     */
    public String getLinkId1() {
        return linkId1;
    }

    /**
     * 设置 外部代码1，存万得代码
     * @param linkId1 外部代码1，存万得代码
     */
    public void setLinkId1(String linkId1) {
        this.linkId1 = linkId1 == null ? null : linkId1.trim();
    }

    /**
     * 取得 外部代码2，对于平安存客户编号
     * @return 外部代码2，对于平安存客户编号
     */
    public String getLinkId2() {
        return linkId2;
    }

    /**
     * 设置 外部代码2，对于平安存客户编号
     * @param linkId2 外部代码2，对于平安存客户编号
     */
    public void setLinkId2(String linkId2) {
        this.linkId2 = linkId2 == null ? null : linkId2.trim();
    }

    /**
     * 取得 外部代码3
     * @return 外部代码3
     */
    public String getLinkId3() {
        return linkId3;
    }

    /**
     * 设置 外部代码3
     * @param linkId3 外部代码3
     */
    public void setLinkId3(String linkId3) {
        this.linkId3 = linkId3 == null ? null : linkId3.trim();
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

    /**
     * 取得 核算分类
     * @return 核算分类
     */
    public String getHsType() {
        return hsType;
    }

    /**
     * 设置 核算分类
     * @param hsType 核算分类
     */
    public void setHsType(String hsType) {
        this.hsType = hsType == null ? null : hsType.trim();
    }

    /**
     * 取得 是否持有金融牌照
     * @return 是否持有金融牌照
     */
    public String getIsFinLicence() {
        return isFinLicence;
    }

    /**
     * 设置 是否持有金融牌照
     * @param isFinLicence 是否持有金融牌照
     */
    public void setIsFinLicence(String isFinLicence) {
        this.isFinLicence = isFinLicence == null ? null : isFinLicence.trim();
    }

    /**
     * 取得 行业三级分类
     * @return 行业三级分类
     */
    public String getIndustry3() {
        return industry3;
    }

    /**
     * 设置 行业三级分类
     * @param industry3 行业三级分类
     */
    public void setIndustry3(String industry3) {
        this.industry3 = industry3 == null ? null : industry3.trim();
    }

    /**
     * 取得 行业四级分类
     * @return 行业四级分类
     */
    public String getIndustry4() {
        return industry4;
    }

    /**
     * 设置 行业四级分类
     * @param industry4 行业四级分类
     */
    public void setIndustry4(String industry4) {
        this.industry4 = industry4 == null ? null : industry4.trim();
    }

    /**
     * 取得 机构类型(风险)
     * @return 机构类型(风险)
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * 设置 机构类型(风险)
     * @param orgType 机构类型(风险)
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType == null ? null : orgType.trim();
    }

    /**
     * 取得 是否股东及其关联方
     * @return 是否股东及其关联方
     */
    public String getIsShareholderRelated() {
        return isShareholderRelated;
    }

    /**
     * 设置 是否股东及其关联方
     * @param isShareholderRelated 是否股东及其关联方
     */
    public void setIsShareholderRelated(String isShareholderRelated) {
        this.isShareholderRelated = isShareholderRelated == null ? null : isShareholderRelated.trim();
    }
}