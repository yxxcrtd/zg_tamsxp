/*
 * 文件名：EquityInfoRegist001Dto.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年9月19日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitmanagement.equityinforegist.dto;

import java.util.Date;
import java.util.List;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.report.common.base.TableRow;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.constant.DictCode;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author Administrator
 * @version 1.0 2017年9月19日
 * @see EquityInfoRegist001Dto
 * @since 1.0
 */
@TableName("CHB_EQU_SUB")
public class EquityInfoRegist001Dto extends TableRow {

    /**
     * 检查序列号
     */
    private String checkSeqno;

    /**
     * 机构代码list
     */
    private List<String> orgCodeList;

    /**
     * 数据日期起始日期
     */
    private String updateTimeStart;

    /**
     * 数据日期结束日期
     */
    private String updateTimeEnd;

    /**
     * 数据类型code
     */
    private String typeCode;
    /**
     * 反馈状态code
     */
    private String submitFeedbackStatusCode;
    /**
     * 报送流水号list
     */
    private String seqList;

    private String  fundAccPlace;

    public String getFundAccPlace() {
        return fundAccPlace;
    }

    public void setFundAccPlace(String fundAccPlace) {
        this.fundAccPlace = fundAccPlace;
    }

    /**
     * 删除原因
     */
    private String deleteReason;
    /**
     * 获取 orgCodeList
     * @return orgCodeList.
     */
    public List<String> getOrgCodeList() {
        return orgCodeList;
    }


    /**
     * 设置 orgCodeList
     * @param orgCodeList orgCodeList
     */
    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }


    /**
     * 获取 updateTimeStart
     * @return updateTimeStart.
     */
    public String getUpdateTimeStart() {
        return updateTimeStart;
    }


    /**
     * 设置 updateTimeStart
     * @param updateTimeStart updateTimeStart
     */
    public void setUpdateTimeStart(String updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }


    /**
     * 获取 updateTimeEnd
     * @return updateTimeEnd.
     */
    public String getUpdateTimeEnd() {
        return updateTimeEnd;
    }


    /**
     * 设置 updateTimeEnd
     * @param updateTimeEnd updateTimeEnd
     */
    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 创建部门
     */
    private String createDept;

    /**
     * 获取 orgCode
     *
     * @return orgCode.
     */
    public String getOrgCode() {
        return orgCode == null ? "" : orgCode;
    }

    /**
     * 设置 orgCode
     *
     * @param orgCode orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 数据状态
     */
    private String status;

    /**
     * 数据状态名
     */
    private String statusName;

    /**
     * 数据日期
     */
    private Date updateTime;

    /**
     * 反馈状态
     */
    @DictFormat(DictCode.报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 报送状态
     */
    private String submitStatus;

    /**
     * 数据类型
     */
    @DictFormat(DictCode.数据类型_中债)
    private String type;

    /**
     * 登记银行代码
     */
    @BusinessKey("REGIST_BANK_CODE")
    private String bankCode;

    /**
     * 识别标识
     */
    @BusinessKey("INDENTIFY_MARK")
    private String indentifyMark;

    /**
     * 原识别标识
     */
    private String orgIndentifyMark;

    /**
     * 该权益人是否属于本行
     */
    @DictFormat(DictCode.是否_中债)
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
    @DictFormat(DictCode.境内外)
    private String holderMark;

    /**
     * 权益人所属国家或地区
     */
    @DictFormat(DictCode.所属国家或地区)
    private String holderRegion;

    /**
     * 权益人类别
     */
    @DictFormat(DictCode.投资者类别)
    private String holderType;

    /**
     * 权益人类别
     */
    @DictFormat(DictCode.投资者分类)
    private String holderClassify;

    /**
     * 证件类别
     */
    @DictFormat(DictCode.是否_中债)
    private String identifyType;

    /**
     * 证件号码
     */
    private String identifyNumber;

    /**
     * SPV资金托管账户及开户行
     */
    @DictFormat(DictCode.SPV资金托管账户开户行)
    private String spvCapAccBank;

    /**
     * 其他资金托管账户及开户行
     */
    private String otherCapAccBank;

    /**
     * SPV登记编码
     */
    private String spvRegistCode;

    /**
     * 权益人名称
     */
    private String holderName;

    /**
     * 性别
     */
    @DictFormat(DictCode.性别_中债)
    private String holderSex;

    /**
     * 风险偏好
     */
    @DictFormat(DictCode.投资风险偏好)
    private String holderRiskPrefer;

    /**
     * 联系号码
     */
    private String holderCellphone;

    /**
     * 固定电话
     */
    private String holderTelephone;

    /**
     * 电子邮件
     */
    private String holderEmail;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 个人证件类型
     */
    @DictFormat(DictCode.个人证件类别)
    private String personalIdentifyType;

    /**
     * 机构证件类型
     */
    @DictFormat(DictCode.机构证件类别)
    private String institutionIdentifyType;

    /**
     * 其他证件名称
     */
    private String otherIdentifyName;

    /**
     * 报送日期
     */
    private Date holderDate;

    /**
     * 登记流水号
     */
    private String regSerialNumber;

    /**
     * 报送状态名
     */
    private String submitStatusName;

    /**
     * 报送流水号（影藏列）
     */
    private String sumitSeq;

    /**
     * 获取 status
     *
     * @return status.
     */
    public String getStatus() {
        return status == null ? "" : status;
    }

    /**
     * 设置 status
     *
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取 updateTime
     *
     * @return updateTime.
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置 updateTime
     *
     * @param updateTime updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        if(StringUtil.isNotEmpty(this.getUpdateTime())){
            return DateUtil.formatDateTime(this.getUpdateTime());
        }
        return null;
    }

    public String getSubmitTimeStr() {
        if(StringUtil.isNotEmpty(this.getSubmitTime())){
            return DateUtil.formatDateTime(this.getSubmitTime());
        }
        return null;
    }

    public String getChbSubmitDeadlineStr() {
        if(StringUtil.isNotEmpty(this.getChbSubmitDeadline())){
            return DateUtil.formatDateTime(this.getChbSubmitDeadline());
        }
        return null;
    }

    /**
     * 获取 submitFeedbackStatus
     *
     * @return submitFeedbackStatus.
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus == null ? "" : submitFeedbackStatus;
    }

    public String getSubmitFeedbackStatusStr() {
        return submitFeedbackStatus==null?null:SysDataUtil.getNameFromDataDict(DictCode.报送反馈状态, this.submitFeedbackStatus);
    }
    /**
     * 设置 submitFeedbackStatus
     *
     * @param submitFeedbackStatus submitFeedbackStatus
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus;
    }

    /**
     * 获取 submitTime
     *
     * @return submitTime.
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 设置 submitTime
     *
     * @param submitTime submitTime
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 获取 submitDeadline
     *
     * @return submitDeadline.
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 设置 submitDeadline
     *
     * @param submitDeadline submitDeadline
     */
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    /**
     * 获取 submitStatus
     *
     * @return submitStatus.
     */
    public String getSubmitStatus() {
        return submitStatus == null ? "" : submitStatus;
    }

    /**
     * 设置 submitStatus
     *
     * @param submitStatus submitStatus
     */
    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    /**
     * 获取 type
     *
     * @return type.
     */
    public String getType() {
        return type == null ? "" : type;
    }

    /**
     * 设置 type
     *
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取 bankCode
     *
     * @return bankCode.
     */
    public String getBankCode() {
        return bankCode == null ? "" : bankCode;
    }

    /**
     * 设置 bankCode
     *
     * @param bankCode bankCode
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 获取 indentifyMark
     *
     * @return indentifyMark.
     */
    public String getIndentifyMark() {
        return indentifyMark == null ? "" : indentifyMark;
    }

    /**
     * 设置 indentifyMark
     *
     * @param indentifyMark indentifyMark
     */
    public void setIndentifyMark(String indentifyMark) {
        this.indentifyMark = indentifyMark;
    }

    /**
     * 获取 orgIndentifyMark
     *
     * @return orgIndentifyMark.
     */
    public String getOrgIndentifyMark() {
        return orgIndentifyMark == null ? "" : orgIndentifyMark;
    }

    /**
     * 设置 orgIndentifyMark
     *
     * @param orgIndentifyMark orgIndentifyMark
     */
    public void setOrgIndentifyMark(String orgIndentifyMark) {
        this.orgIndentifyMark = orgIndentifyMark;
    }

    /**
     * 获取 isBelongBank
     *
     * @return isBelongBank.
     */
    public String getIsBelongBank() {
        return isBelongBank == null ? "" : isBelongBank;
    }

    /**
     * 设置 isBelongBank
     *
     * @param isBelongBank isBelongBank
     */
    public void setIsBelongBank(String isBelongBank) {
        this.isBelongBank = isBelongBank;
    }

    /**
     * 获取 theBankName
     *
     * @return theBankName.
     */
    public String getTheBankName() {
        return theBankName == null ? "" : theBankName;
    }

    /**
     * 设置 theBankName
     *
     * @param theBankName theBankName
     */
    public void setTheBankName(String theBankName) {
        this.theBankName = theBankName;
    }

    /**
     * 获取 theBankCode
     *
     * @return theBankCode.
     */
    public String getTheBankCode() {
        return theBankCode == null ? "" : theBankCode;
    }

    /**
     * 设置 theBankCode
     *
     * @param theBankCode theBankCode
     */
    public void setTheBankCode(String theBankCode) {
        this.theBankCode = theBankCode;
    }

    /**
     * 获取 holderMark
     *
     * @return holderMark.
     */
    public String getHolderMark() {
        return holderMark == null ? "" : holderMark;
    }

    /**
     * 设置 holderMark
     *
     * @param holderMark holderMark
     */
    public void setHolderMark(String holderMark) {
        this.holderMark = holderMark;
    }

    /**
     * 获取 holderRegion
     *
     * @return holderRegion.
     */
    public String getHolderRegion() {
        return holderRegion == null ? "" : holderRegion;
    }

    /**
     * 设置 holderRegion
     *
     * @param holderRegion holderRegion
     */
    public void setHolderRegion(String holderRegion) {
        this.holderRegion = holderRegion;
    }

    /**
     * 获取 holderType
     *
     * @return holderType.
     */
    public String getHolderType() {
        return holderType == null ? "" : holderType;
    }

    /**
     * 设置 holderType
     *
     * @param holderType holderType
     */
    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }

    public String getHolderClassify() {
        return holderClassify;
    }

    public void setHolderClassify(String holderClassify) {
        this.holderClassify = holderClassify;
    }

    public String getHolderClassifyStr() {
        return  holderClassify +' '+SysDataUtil.getNameFromDataDict(DictCode.投资者分类, holderClassify);
    }


    /**
     * 获取 spvCapAccBank
     *
     * @return spvCapAccBank.
     */
    public String getSpvCapAccBank() {
        return spvCapAccBank == null ? "" : spvCapAccBank;
    }

    /**
     * 设置 spvCapAccBank
     *
     * @param spvCapAccBank spvCapAccBank
     */
    public void setSpvCapAccBank(String spvCapAccBank) {
        this.spvCapAccBank = spvCapAccBank;
    }

    /**
     * 获取 otherCapAccBank
     *
     * @return otherCapAccBank.
     */
    public String getOtherCapAccBank() {
        return otherCapAccBank == null ? "" : otherCapAccBank;
    }

    /**
     * 设置 otherCapAccBank
     *
     * @param otherCapAccBank otherCapAccBank
     */
    public void setOtherCapAccBank(String otherCapAccBank) {
        this.otherCapAccBank = otherCapAccBank;
    }

    /**
     * 获取 spvRegistCode
     *
     * @return spvRegistCode.
     */
    public String getSpvRegistCode() {
        return spvRegistCode == null ? "" : spvRegistCode;
    }

    /**
     * 设置 spvRegistCode
     *
     * @param spvRegistCode spvRegistCode
     */
    public void setSpvRegistCode(String spvRegistCode) {
        this.spvRegistCode = spvRegistCode;
    }

    /**
     * 获取 holderName
     *
     * @return holderName.
     */
    public String getHolderName() {
        return holderName == null ? "" : holderName;
    }

    /**
     * 设置 holderName
     *
     * @param holderName holderName
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    /**
     * 获取 holderSex
     *
     * @return holderSex.
     */
    public String getHolderSex() {
        return holderSex == null ? "" : holderSex;
    }

    /**
     * 设置 holderSex
     *
     * @param holderSex holderSex
     */
    public void setHolderSex(String holderSex) {
        this.holderSex = holderSex;
    }

    /**
     * 获取 holderRiskPrefer
     *
     * @return holderRiskPrefer.
     */
    public String getHolderRiskPrefer() {
        return holderRiskPrefer == null ? "" : holderRiskPrefer;
    }

    /**
     * 设置 holderRiskPrefer
     *
     * @param holderRiskPrefer holderRiskPrefer
     */
    public void setHolderRiskPrefer(String holderRiskPrefer) {
        this.holderRiskPrefer = holderRiskPrefer;
    }

    /**
     * 获取 holderCellphone
     *
     * @return holderCellphone.
     */
    public String getHolderCellphone() {
        return holderCellphone == null ? "" : holderCellphone;
    }

    /**
     * 设置 holderCellphone
     *
     * @param holderCellphone holderCellphone
     */
    public void setHolderCellphone(String holderCellphone) {
        this.holderCellphone = holderCellphone;
    }

    /**
     * 获取 holderEmail
     *
     * @return holderEmail.
     */
    public String getHolderEmail() {
        return holderEmail == null ? "" : holderEmail;
    }

    /**
     * 设置 holderEmail
     *
     * @param holderEmail holderEmail
     */
    public void setHolderEmail(String holderEmail) {
        this.holderEmail = holderEmail;
    }

    /**
     * 获取 remark
     *
     * @return remark.
     */
    public String getRemark() {
        return remark == null ? "" : remark;
    }

    /**
     * 设置 remark
     *
     * @param remark remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取 personalIdentifyType
     *
     * @return personalIdentifyType.
     */
    public String getPersonalIdentifyType() {
        return personalIdentifyType == null ? "" : personalIdentifyType;
    }

    /**
     * 设置 personalIdentifyType
     *
     * @param personalIdentifyType personalIdentifyType
     */
    public void setPersonalIdentifyType(String personalIdentifyType) {
        this.personalIdentifyType = personalIdentifyType;
    }

    /**
     * 获取 institutionIdentifyType
     *
     * @return institutionIdentifyType.
     */
    public String getInstitutionIdentifyType() {
        return institutionIdentifyType == null ? "" : institutionIdentifyType;
    }

    /**
     * 设置 institutionIdentifyType
     *
     * @param institutionIdentifyType institutionIdentifyType
     */
    public void setInstitutionIdentifyType(String institutionIdentifyType) {
        this.institutionIdentifyType = institutionIdentifyType;
    }

    /**
     * 获取 identifyType
     *
     * @return identifyType.
     */
    public String getIdentifyType() {
        return identifyType == null ? "" : identifyType;
    }

    /**
     * 设置 identifyType
     *
     * @param identifyType identifyType
     */
    public void setIdentifyType(String identifyType) {
        this.identifyType = identifyType;
    }

    /**
     * 获取 identifyNumber
     *
     * @return identifyNumber.
     */
    public String getIdentifyNumber() {
        return identifyNumber == null ? "" : identifyNumber;
    }

    /**
     * 设置 identifyNumber
     *
     * @param identifyNumber identifyNumber
     */
    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    /**
     * 获取 otherIdentifyName
     *
     * @return otherIdentifyName.
     */
    public String getOtherIdentifyName() {
        return otherIdentifyName == null ? "" : otherIdentifyName;
    }

    /**
     * 设置 otherIdentifyName
     *
     * @param otherIdentifyName otherIdentifyName
     */
    public void setOtherIdentifyName(String otherIdentifyName) {
        this.otherIdentifyName = otherIdentifyName;
    }

    /**
     * 获取 holderTelephone
     *
     * @return holderTelephone.
     */
    public String getHolderTelephone() {
        return holderTelephone == null ? "" : holderTelephone;
    }

    /**
     * 设置 holderTelephone
     *
     * @param holderTelephone holderTelephone
     */
    public void setHolderTelephone(String holderTelephone) {
        this.holderTelephone = holderTelephone;
    }

    /**
     * 获取 createUser
     *
     * @return createUser.
     */
    @Override
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置 createUser
     *
     * @param createUser createUser
     */
    @Override
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取 updateUser
     *
     * @return updateUser.
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置 updateUser
     *
     * @param updateUser updateUser
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取 holderDate
     *
     * @return holderDate.
     */
    public Date getHolderDate() {
        return holderDate;
    }

    /**
     * 设置 holderDate
     *
     * @param holderDate holderDate
     */
    public void setHolderDate(Date holderDate) {
        this.holderDate = holderDate;
    }

    /**
     * 获取 chbSubmitDeadline
     *
     * @return chbSubmitDeadline.
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 设置 chbSubmitDeadline
     *
     * @param chbSubmitDeadline chbSubmitDeadline
     */
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    /**
     * 获取 regSerialNumber
     *
     * @return regSerialNumber.
     */
    public String getRegSerialNumber() {
        return regSerialNumber == null ? "" : regSerialNumber;
    }

    /**
     * 设置 regSerialNumber
     *
     * @param regSerialNumber regSerialNumber
     */
    public void setRegSerialNumber(String regSerialNumber) {
        this.regSerialNumber = regSerialNumber;
    }

    /**
     * 获取 submitStatusName
     *
     * @return submitStatusName.
     */
    public String getSubmitStatusName() {
        return submitStatusName == null ? "" : submitStatusName;
    }

    /**
     * 设置 submitStatusName
     *
     * @param submitStatusName submitStatusName
     */
    public void setSubmitStatusName(String submitStatusName) {
        this.submitStatusName = submitStatusName;
    }

    /**
     * 获取 sumitSeq
     *
     * @return sumitSeq.
     */
    public String getSumitSeq() {
        return sumitSeq == null ? "" : sumitSeq;
    }

    /**
     * 设置 sumitSeq
     *
     * @param sumitSeq sumitSeq
     */
    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq;
    }

    /**
     * 获取 statusName
     *
     * @return statusName.
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 设置 statusName
     *
     * @param statusName statusName
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 获取 deptCode
     *
     * @return deptCode.
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 设置 deptCode
     *
     * @param deptCode deptCode
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }


    /**
     * 获取 createDept
     *
     * @return createDept.
     */
    @Override
    public String getCreateDept() {

        if (StringUtil.isNotBlank(super.getCreateDept())) {
            return super.getCreateDept();
        }

        String tempCreateDept = getDeptCode();
        if (StringUtil.isEmpty(tempCreateDept)) {
            tempCreateDept = getOrgCode();
        }
        return tempCreateDept;

//        if (StringUtil.isEmpty(this.createDept)) {
//            if (StringUtil.isNotEmpty(getDeptCode())) {
//                createDept = getDeptCode();
//            }
//            else {
//                createDept = getOrgCode();
//            }
//        }
//        return createDept;
    }

    /**
     * 设置 createDept
     * @param createDept createDept
     */
    @Override
    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }



    /**
     * 获取 checkSeqno
     * @return checkSeqno.
     */
    public String getCheckSeqno() {
        return checkSeqno;
    }



    /**
     * 设置 checkSeqno
     * @param checkSeqno checkSeqno
     */
    public void setCheckSeqno(String checkSeqno) {
        this.checkSeqno = checkSeqno;
    }


    public String getTypeCode() {
        return typeCode;
    }


    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }


    public String getSeqList() {
        return seqList;
    }


    public void setSeqList(String seqList) {
        this.seqList = seqList;
    }


    public String getSubmitFeedbackStatusCode() {
        return submitFeedbackStatusCode;
    }


    public void setSubmitFeedbackStatusCode(String submitFeedbackStatusCode) {
        this.submitFeedbackStatusCode = submitFeedbackStatusCode;
    }
    /**
     * @Fields sendStatus : (发送状态)
     */
    private String sendStatus;

    public String getSendStatus() {
        return sendStatus;
    }


    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    // 导出报错新添加的 （徽商） njl  start
    /**
     * 获取 updateTime
     *
     * @return updateTime.
     */
    public String getUpdateSTimeStr() {
        return DateUtil.formatDateTime(this.getUpdateTime());
    }


    /**
     * 获取 submitTime
     *
     * @return submitTime.
     */
    public String getSubmitSTimeStr() {
        return DateUtil.formatDateTime(this.getSubmitTime());
    }


    /**
     * 获取 chbSubmitDeadline
     *
     * @return chbSubmitDeadline.
     */
    public String getChbSubmitSDeadlineStr() {
        return DateUtil.formatDateTime(this.getChbSubmitDeadline());
    }

    // 导出报错新添加的 （徽商） njl  end


    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }
}
