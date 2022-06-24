package com.joyintech.tams.common.base.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.joyintech.framework.json.CustomDateDeserialize;
import java.util.Date;

public class ChbEquSub extends ChbEquSubKey {
    /**
     * 原始识别标识
     */
    private String orgIndentifyMark;

    /**
     * 该权益人是否属于本行
     */
    private String isBelongBank;

    /**
     * 权益人所属银行名称
     */
    private String theBankName;

    /**
     * 权益人所属银行代码
     */
    private String theBankCode;

    /**
     * 权益人境内外标识
     */
    private String holderMark;

    /**
     * 权益人所属国家或地区
     */
    private String holderRegion;

    /**
     * 权益人类别
     */
    private String holderType;

    /**
     * 个人证件类别
     */
    private String personalIdentifyType;

    /**
     * 机构证件类别
     */
    private String institutionIdentifyType;

    /**
     * 其他证件名称
     */
    private String otherIdentifyName;

    /**
     * 证件号码
     */
    private String identifyNumber;

    /**
     * SPV资金托管账户开户行
     */
    private String spvCapAccBank;

    /**
     * 其他资金托管账户开户行
     */
    private String otherCapAccBank;

    /**
     * SPV登记编码
     */
    private String spvRegistCode;

    /**
     * 权益人登记日期
     */
    private Date holderDate;

    /**
     * 权益人名称
     */
    private String holderName;

    /**
     * 性别
     */
    private String holderSex;

    /**
     * 风险偏好
     */
    private String holderRiskPrefer;

    /**
     * 手机号码
     */
    private String holderCellphone;

    /**
     * 固定电话
     */
    private String holderTelephone;

    /**
     * 电子邮箱
     */
    private String holderEmail;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 报送截止日
     */
    private Date submitDeadline;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 删除原因
     */
    private String deleteRes;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 检查序列号
     */
    private String checkSeqno;

    /**
     * 发送状态
     */
    private String sendStatus;

    /**
     * 账号开户所在地
     */
    private String fundAccPlace;

    /**
     * 投资者类型:01 不特定社会公众; 02 合格投资者
     */
    private String holderClassify;

    /**
     * 报送反馈状态
     */
    private String submitFeedbackStatus;

    /**
     * 报送编码
     */
    private String reportNo;

    /**
     * 报送反馈消息
     */
    private String submitFeedbackMsg;

    /**
     * 取得 原始识别标识
     * @return 原始识别标识
     */
    public String getOrgIndentifyMark() {
        return orgIndentifyMark;
    }

    /**
     * 设置 原始识别标识
     * @param orgIndentifyMark 原始识别标识
     */
    public void setOrgIndentifyMark(String orgIndentifyMark) {
        this.orgIndentifyMark = orgIndentifyMark == null ? null : orgIndentifyMark.trim();
    }

    /**
     * 取得 该权益人是否属于本行
     * @return 该权益人是否属于本行
     */
    public String getIsBelongBank() {
        return isBelongBank;
    }

    /**
     * 设置 该权益人是否属于本行
     * @param isBelongBank 该权益人是否属于本行
     */
    public void setIsBelongBank(String isBelongBank) {
        this.isBelongBank = isBelongBank == null ? null : isBelongBank.trim();
    }

    /**
     * 取得 权益人所属银行名称
     * @return 权益人所属银行名称
     */
    public String getTheBankName() {
        return theBankName;
    }

    /**
     * 设置 权益人所属银行名称
     * @param theBankName 权益人所属银行名称
     */
    public void setTheBankName(String theBankName) {
        this.theBankName = theBankName == null ? null : theBankName.trim();
    }

    /**
     * 取得 权益人所属银行代码
     * @return 权益人所属银行代码
     */
    public String getTheBankCode() {
        return theBankCode;
    }

    /**
     * 设置 权益人所属银行代码
     * @param theBankCode 权益人所属银行代码
     */
    public void setTheBankCode(String theBankCode) {
        this.theBankCode = theBankCode == null ? null : theBankCode.trim();
    }

    /**
     * 取得 权益人境内外标识
     * @return 权益人境内外标识
     */
    public String getHolderMark() {
        return holderMark;
    }

    /**
     * 设置 权益人境内外标识
     * @param holderMark 权益人境内外标识
     */
    public void setHolderMark(String holderMark) {
        this.holderMark = holderMark == null ? null : holderMark.trim();
    }

    /**
     * 取得 权益人所属国家或地区
     * @return 权益人所属国家或地区
     */
    public String getHolderRegion() {
        return holderRegion;
    }

    /**
     * 设置 权益人所属国家或地区
     * @param holderRegion 权益人所属国家或地区
     */
    public void setHolderRegion(String holderRegion) {
        this.holderRegion = holderRegion == null ? null : holderRegion.trim();
    }

    /**
     * 取得 权益人类别
     * @return 权益人类别
     */
    public String getHolderType() {
        return holderType;
    }

    /**
     * 设置 权益人类别
     * @param holderType 权益人类别
     */
    public void setHolderType(String holderType) {
        this.holderType = holderType == null ? null : holderType.trim();
    }

    /**
     * 取得 个人证件类别
     * @return 个人证件类别
     */
    public String getPersonalIdentifyType() {
        return personalIdentifyType;
    }

    /**
     * 设置 个人证件类别
     * @param personalIdentifyType 个人证件类别
     */
    public void setPersonalIdentifyType(String personalIdentifyType) {
        this.personalIdentifyType = personalIdentifyType == null ? null : personalIdentifyType.trim();
    }

    /**
     * 取得 机构证件类别
     * @return 机构证件类别
     */
    public String getInstitutionIdentifyType() {
        return institutionIdentifyType;
    }

    /**
     * 设置 机构证件类别
     * @param institutionIdentifyType 机构证件类别
     */
    public void setInstitutionIdentifyType(String institutionIdentifyType) {
        this.institutionIdentifyType = institutionIdentifyType == null ? null : institutionIdentifyType.trim();
    }

    /**
     * 取得 其他证件名称
     * @return 其他证件名称
     */
    public String getOtherIdentifyName() {
        return otherIdentifyName;
    }

    /**
     * 设置 其他证件名称
     * @param otherIdentifyName 其他证件名称
     */
    public void setOtherIdentifyName(String otherIdentifyName) {
        this.otherIdentifyName = otherIdentifyName == null ? null : otherIdentifyName.trim();
    }

    /**
     * 取得 证件号码
     * @return 证件号码
     */
    public String getIdentifyNumber() {
        return identifyNumber;
    }

    /**
     * 设置 证件号码
     * @param identifyNumber 证件号码
     */
    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber == null ? null : identifyNumber.trim();
    }

    /**
     * 取得 SPV资金托管账户开户行
     * @return SPV资金托管账户开户行
     */
    public String getSpvCapAccBank() {
        return spvCapAccBank;
    }

    /**
     * 设置 SPV资金托管账户开户行
     * @param spvCapAccBank SPV资金托管账户开户行
     */
    public void setSpvCapAccBank(String spvCapAccBank) {
        this.spvCapAccBank = spvCapAccBank == null ? null : spvCapAccBank.trim();
    }

    /**
     * 取得 其他资金托管账户开户行
     * @return 其他资金托管账户开户行
     */
    public String getOtherCapAccBank() {
        return otherCapAccBank;
    }

    /**
     * 设置 其他资金托管账户开户行
     * @param otherCapAccBank 其他资金托管账户开户行
     */
    public void setOtherCapAccBank(String otherCapAccBank) {
        this.otherCapAccBank = otherCapAccBank == null ? null : otherCapAccBank.trim();
    }

    /**
     * 取得 SPV登记编码
     * @return SPV登记编码
     */
    public String getSpvRegistCode() {
        return spvRegistCode;
    }

    /**
     * 设置 SPV登记编码
     * @param spvRegistCode SPV登记编码
     */
    public void setSpvRegistCode(String spvRegistCode) {
        this.spvRegistCode = spvRegistCode == null ? null : spvRegistCode.trim();
    }

    /**
     * 取得 权益人登记日期
     * @return 权益人登记日期
     */
    public Date getHolderDate() {
        return holderDate;
    }

    /**
     * 设置 权益人登记日期
     * @param holderDate 权益人登记日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setHolderDate(Date holderDate) {
        this.holderDate = holderDate;
    }

    /**
     * 取得 权益人名称
     * @return 权益人名称
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * 设置 权益人名称
     * @param holderName 权益人名称
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName == null ? null : holderName.trim();
    }

    /**
     * 取得 性别
     * @return 性别
     */
    public String getHolderSex() {
        return holderSex;
    }

    /**
     * 设置 性别
     * @param holderSex 性别
     */
    public void setHolderSex(String holderSex) {
        this.holderSex = holderSex == null ? null : holderSex.trim();
    }

    /**
     * 取得 风险偏好
     * @return 风险偏好
     */
    public String getHolderRiskPrefer() {
        return holderRiskPrefer;
    }

    /**
     * 设置 风险偏好
     * @param holderRiskPrefer 风险偏好
     */
    public void setHolderRiskPrefer(String holderRiskPrefer) {
        this.holderRiskPrefer = holderRiskPrefer == null ? null : holderRiskPrefer.trim();
    }

    /**
     * 取得 手机号码
     * @return 手机号码
     */
    public String getHolderCellphone() {
        return holderCellphone;
    }

    /**
     * 设置 手机号码
     * @param holderCellphone 手机号码
     */
    public void setHolderCellphone(String holderCellphone) {
        this.holderCellphone = holderCellphone == null ? null : holderCellphone.trim();
    }

    /**
     * 取得 固定电话
     * @return 固定电话
     */
    public String getHolderTelephone() {
        return holderTelephone;
    }

    /**
     * 设置 固定电话
     * @param holderTelephone 固定电话
     */
    public void setHolderTelephone(String holderTelephone) {
        this.holderTelephone = holderTelephone == null ? null : holderTelephone.trim();
    }

    /**
     * 取得 电子邮箱
     * @return 电子邮箱
     */
    public String getHolderEmail() {
        return holderEmail;
    }

    /**
     * 设置 电子邮箱
     * @param holderEmail 电子邮箱
     */
    public void setHolderEmail(String holderEmail) {
        this.holderEmail = holderEmail == null ? null : holderEmail.trim();
    }

    /**
     * 取得 数据类型
     * @return 数据类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置 数据类型
     * @param type 数据类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
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
     * 取得 报送日期
     * @return 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 设置 报送日期
     * @param submitTime 报送日期
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 取得 中债报送截止日
     * @return 中债报送截止日
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 设置 中债报送截止日
     * @param chbSubmitDeadline 中债报送截止日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    /**
     * 取得 报送截止日
     * @return 报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 设置 报送截止日
     * @param submitDeadline 报送截止日
     */
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
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
     * 取得 报送流水号
     * @return 报送流水号
     */
    public String getSumitSeq() {
        return sumitSeq;
    }

    /**
     * 设置 报送流水号
     * @param sumitSeq 报送流水号
     */
    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq == null ? null : sumitSeq.trim();
    }

    /**
     * 取得 删除原因
     * @return 删除原因
     */
    public String getDeleteRes() {
        return deleteRes;
    }

    /**
     * 设置 删除原因
     * @param deleteRes 删除原因
     */
    public void setDeleteRes(String deleteRes) {
        this.deleteRes = deleteRes == null ? null : deleteRes.trim();
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
     * 取得 检查序列号
     * @return 检查序列号
     */
    public String getCheckSeqno() {
        return checkSeqno;
    }

    /**
     * 设置 检查序列号
     * @param checkSeqno 检查序列号
     */
    public void setCheckSeqno(String checkSeqno) {
        this.checkSeqno = checkSeqno == null ? null : checkSeqno.trim();
    }

    /**
     * 取得 发送状态
     * @return 发送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置 发送状态
     * @param sendStatus 发送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 取得 账号开户所在地
     * @return 账号开户所在地
     */
    public String getFundAccPlace() {
        return fundAccPlace;
    }

    /**
     * 设置 账号开户所在地
     * @param fundAccPlace 账号开户所在地
     */
    public void setFundAccPlace(String fundAccPlace) {
        this.fundAccPlace = fundAccPlace == null ? null : fundAccPlace.trim();
    }

    /**
     * 取得 投资者类型:01 不特定社会公众; 02 合格投资者
     * @return 投资者类型:01 不特定社会公众; 02 合格投资者
     */
    public String getHolderClassify() {
        return holderClassify;
    }

    /**
     * 设置 投资者类型:01 不特定社会公众; 02 合格投资者
     * @param holderClassify 投资者类型:01 不特定社会公众; 02 合格投资者
     */
    public void setHolderClassify(String holderClassify) {
        this.holderClassify = holderClassify == null ? null : holderClassify.trim();
    }

    /**
     * 取得 报送反馈状态
     * @return 报送反馈状态
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 设置 报送反馈状态
     * @param submitFeedbackStatus 报送反馈状态
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 取得 报送编码
     * @return 报送编码
     */
    public String getReportNo() {
        return reportNo;
    }

    /**
     * 设置 报送编码
     * @param reportNo 报送编码
     */
    public void setReportNo(String reportNo) {
        this.reportNo = reportNo == null ? null : reportNo.trim();
    }

    /**
     * 取得 报送反馈消息
     * @return 报送反馈消息
     */
    public String getSubmitFeedbackMsg() {
        return submitFeedbackMsg;
    }

    /**
     * 设置 报送反馈消息
     * @param submitFeedbackMsg 报送反馈消息
     */
    public void setSubmitFeedbackMsg(String submitFeedbackMsg) {
        this.submitFeedbackMsg = submitFeedbackMsg == null ? null : submitFeedbackMsg.trim();
    }
}