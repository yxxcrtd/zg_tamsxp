package com.joyintech.tams.svreport.chb.investtrade.dto;

import java.util.Date;

/**
 * 描述:CHB_MST_COUNTER_PARTY表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-02-19
 */
public class MstCounterPartyDto {
    /**
     * 交易对手代码
     */
    private String counterId;

    /**
     * 交易对手类型，市场机构、资管产品、通道
     */
    private String counterType;

    /**
     * 交易对手名称，市场机构取机构名称，通道取金融产品简称，资管产品由页面输入。
     */
    private String counterName;

    /**
     * 关联代码，市场机构关联机构信息、资管产品无，通道关联标的类金融产品
     */
    private String linkId;

    /**
     * 管理人，资管产品时录入
     */
    private String managerId;

    /**
     * 所属总行，对应客户信息表的客户代码
     */
    private String headBank;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系方式
     */
    private String contactWay;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建部门
     */
    private String createDept;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 交易对手一级分类
     */
    private String counterType1;
    /**
     * 交易对手二级分类
     */
    private String counterType2;
    /**
     * 是否为中央交易对手方
     */
    private String isCentralCounter;

    /**
     * 关联交易情况
     */
    private String relatedTradeStatus;

    public String getCounterType1() {
		return counterType1;
	}

	public void setCounterType1(String counterType1) {
		this.counterType1 = counterType1;
	}

	public String getCounterType2() {
		return counterType2;
	}

	public void setCounterType2(String counterType2) {
		this.counterType2 = counterType2;
	}

	public String getIsCentralCounter() {
		return isCentralCounter;
	}

	public void setIsCentralCounter(String isCentralCounter) {
		this.isCentralCounter = isCentralCounter;
	}

	/**
     * 交易对手代码
     * @return COUNTER_ID 交易对手代码
     */
    public String getCounterId() {
        return counterId;
    }

    /**
     * 交易对手代码
     * @param counterId 交易对手代码
     */
    public void setCounterId(String counterId) {
        this.counterId = counterId == null ? null : counterId.trim();
    }

    /**
     * 交易对手类型，市场机构、资管产品、通道
     * @return COUNTER_TYPE 交易对手类型，市场机构、资管产品、通道
     */
    public String getCounterType() {
        return counterType;
    }

    /**
     * 交易对手类型，市场机构、资管产品、通道
     * @param counterType 交易对手类型，市场机构、资管产品、通道
     */
    public void setCounterType(String counterType) {
        this.counterType = counterType == null ? null : counterType.trim();
    }

    /**
     * 交易对手名称，市场机构取机构名称，通道取金融产品简称，资管产品由页面输入。
     * @return COUNTER_NAME 交易对手名称，市场机构取机构名称，通道取金融产品简称，资管产品由页面输入。
     */
    public String getCounterName() {
        return counterName;
    }

    /**
     * 交易对手名称，市场机构取机构名称，通道取金融产品简称，资管产品由页面输入。
     * @param counterName 交易对手名称，市场机构取机构名称，通道取金融产品简称，资管产品由页面输入。
     */
    public void setCounterName(String counterName) {
        this.counterName = counterName == null ? null : counterName.trim();
    }

    /**
     * 关联代码，市场机构关联机构信息、资管产品无，通道关联标的类金融产品
     * @return LINK_ID 关联代码，市场机构关联机构信息、资管产品无，通道关联标的类金融产品
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * 关联代码，市场机构关联机构信息、资管产品无，通道关联标的类金融产品
     * @param linkId 关联代码，市场机构关联机构信息、资管产品无，通道关联标的类金融产品
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId == null ? null : linkId.trim();
    }

    /**
     * 管理人，资管产品时录入
     * @return MANAGER_ID 管理人，资管产品时录入
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * 管理人，资管产品时录入
     * @param managerId 管理人，资管产品时录入
     */
    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    /**
     * 所属总行，对应客户信息表的客户代码
     * @return HEAD_BANK 所属总行，对应客户信息表的客户代码
     */
    public String getHeadBank() {
        return headBank;
    }

    /**
     * 所属总行，对应客户信息表的客户代码
     * @param headBank 所属总行，对应客户信息表的客户代码
     */
    public void setHeadBank(String headBank) {
        this.headBank = headBank == null ? null : headBank.trim();
    }

    /**
     * 联系人
     * @return CONTACT 联系人
     */
    public String getContact() {
        return contact;
    }

    /**
     * 联系人
     * @param contact 联系人
     */
    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    /**
     * 联系方式
     * @return CONTACT_WAY 联系方式
     */
    public String getContactWay() {
        return contactWay;
    }

    /**
     * 联系方式
     * @param contactWay 联系方式
     */
    public void setContactWay(String contactWay) {
        this.contactWay = contactWay == null ? null : contactWay.trim();
    }

    /**
     * 备注
     * @return REMARK 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 创建人
     * @return CREATE_USER 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 创建部门
     * @return CREATE_DEPT 创建部门
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * 创建部门
     * @param createDept 创建部门
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    /**
     * 创建时间
     * @return CREATE_TIME 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新人
     * @return UPDATE_USER 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新人
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 更新时间
     * @return UPDATE_TIME 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRelatedTradeStatus() {
        return relatedTradeStatus;
    }

    public void setRelatedTradeStatus(String relatedTradeStatus) {
        this.relatedTradeStatus = relatedTradeStatus;
    }
}