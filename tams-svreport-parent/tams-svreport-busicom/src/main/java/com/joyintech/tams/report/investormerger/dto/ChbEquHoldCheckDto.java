package com.joyintech.tams.report.investormerger.dto;

import java.util.Date;
import java.util.List;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;

/**
 * 描述:CHB_EQU_HOLD_CHECK表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-03-18
 */
public class ChbEquHoldCheckDto extends BaseEntity implements ITableRow {
    /**
     * 识别标识
     */
    private String indentifyMark;

    /**
     * 原始识别标识
     */
    private String orgIndentifyMark;

    /**
     * 登记银行代码
     */
    private String registBankCode;

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
     * 是否报送
     */
    private String isSubmit;

    /**
     * 电子邮箱
     */
    private String holderEmail;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 工作流代码
     */
    private String workflowId;

    /**
     * 部门代码
     */
    private String deptCode;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 数据日期
     */
    private Date dataDate;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 经办人
     */
    private String agent;

    /**
     * 创建部门
     */
    private String createDept;

    /**
     * 接入检查结果
     */
    private String interfCheckResult;
    /**
     * 处理状态 01未处理 02已处理
     */
    private String operatestatus;
    /**
     * 合并后识别标识
     */
    private String mergerindentifymark;
    
    /**
     * 投资者持有信息
     */
    private List<ChbInvestholdInfoCheckDto> holdlist;
    
    /**
     * 是否已报送
     */
    private String isOrNoSubmit;


    private String holderClassify;



    private String fundAccPlace;// 账号开户所在地

    public String getFundAccPlace() {
        return fundAccPlace;
    }

    public void setFundAccPlace(String fundAccPlace) {
        this.fundAccPlace = fundAccPlace;
    }


    public String getHolderClassify() {
        return holderClassify;
    }

    public void setHolderClassify(String holderClassify) {
        this.holderClassify = holderClassify;
    }

    public String getIsOrNoSubmit() {
		return isOrNoSubmit;
	}

	public void setIsOrNoSubmit(String isOrNoSubmit) {
		this.isOrNoSubmit = isOrNoSubmit;
	}

	public List<ChbInvestholdInfoCheckDto> getHoldlist() {
		return holdlist;
	}

	public void setHoldlist(List<ChbInvestholdInfoCheckDto> holdlist) {
		this.holdlist = holdlist;
	}

	/**
     * 识别标识
     * @return INDENTIFY_MARK 识别标识
     */
    public String getIndentifyMark() {
        return this.indentifyMark;
    }

    /**
     * 识别标识
     * @param indentifyMark 识别标识
     */
    public void setIndentifyMark(String indentifyMark) {
        this.indentifyMark = indentifyMark == null ? null : indentifyMark.trim();
    }

    /**
     * 原始识别标识
     * @return ORG_INDENTIFY_MARK 原始识别标识
     */
    public String getOrgIndentifyMark() {
        return orgIndentifyMark;
    }

    /**
     * 原始识别标识
     * @param orgIndentifyMark 原始识别标识
     */
    public void setOrgIndentifyMark(String orgIndentifyMark) {
        this.orgIndentifyMark = orgIndentifyMark == null ? null : orgIndentifyMark.trim();
    }

    /**
     * 登记银行代码
     * @return REGIST_BANK_CODE 登记银行代码
     */
    public String getRegistBankCode() {
        return registBankCode;
    }

    /**
     * 登记银行代码
     * @param registBankCode 登记银行代码
     */
    public void setRegistBankCode(String registBankCode) {
        this.registBankCode = registBankCode == null ? null : registBankCode.trim();
    }

    /**
     * 该权益人是否属于本行
     * @return IS_BELONG_BANK 该权益人是否属于本行
     */
    public String getIsBelongBank() {
        return isBelongBank;
    }

    /**
     * 该权益人是否属于本行
     * @param isBelongBank 该权益人是否属于本行
     */
    public void setIsBelongBank(String isBelongBank) {
        this.isBelongBank = isBelongBank == null ? null : isBelongBank.trim();
    }

    /**
     * 权益人所属银行名称
     * @return THE_BANK_NAME 权益人所属银行名称
     */
    public String getTheBankName() {
        return theBankName;
    }

    /**
     * 权益人所属银行名称
     * @param theBankName 权益人所属银行名称
     */
    public void setTheBankName(String theBankName) {
        this.theBankName = theBankName == null ? null : theBankName.trim();
    }

    /**
     * 权益人所属银行代码
     * @return THE_BANK_CODE 权益人所属银行代码
     */
    public String getTheBankCode() {
        return theBankCode;
    }

    /**
     * 权益人所属银行代码
     * @param theBankCode 权益人所属银行代码
     */
    public void setTheBankCode(String theBankCode) {
        this.theBankCode = theBankCode == null ? null : theBankCode.trim();
    }

    /**
     * 权益人境内外标识
     * @return HOLDER_MARK 权益人境内外标识
     */
    public String getHolderMark() {
        return holderMark;
    }

    /**
     * 权益人境内外标识
     * @param holderMark 权益人境内外标识
     */
    public void setHolderMark(String holderMark) {
        this.holderMark = holderMark == null ? null : holderMark.trim();
    }

    /**
     * 权益人所属国家或地区
     * @return HOLDER_REGION 权益人所属国家或地区
     */
    public String getHolderRegion() {
        return holderRegion;
    }

    /**
     * 权益人所属国家或地区
     * @param holderRegion 权益人所属国家或地区
     */
    public void setHolderRegion(String holderRegion) {
        this.holderRegion = holderRegion == null ? null : holderRegion.trim();
    }

    /**
     * 权益人类别
     * @return HOLDER_TYPE 权益人类别
     */
    public String getHolderType() {
        return holderType;
    }

    /**
     * 权益人类别
     * @param holderType 权益人类别
     */
    public void setHolderType(String holderType) {
        this.holderType = holderType == null ? null : holderType.trim();
    }

    /**
     * 个人证件类别
     * @return PERSONAL_IDENTIFY_TYPE 个人证件类别
     */
    public String getPersonalIdentifyType() {
        return personalIdentifyType;
    }

    /**
     * 个人证件类别
     * @param personalIdentifyType 个人证件类别
     */
    public void setPersonalIdentifyType(String personalIdentifyType) {
        this.personalIdentifyType = personalIdentifyType == null ? null : personalIdentifyType.trim();
    }

    /**
     * 机构证件类别
     * @return INSTITUTION_IDENTIFY_TYPE 机构证件类别
     */
    public String getInstitutionIdentifyType() {
        return institutionIdentifyType;
    }

    /**
     * 机构证件类别
     * @param institutionIdentifyType 机构证件类别
     */
    public void setInstitutionIdentifyType(String institutionIdentifyType) {
        this.institutionIdentifyType = institutionIdentifyType == null ? null : institutionIdentifyType.trim();
    }

    /**
     * 其他证件名称
     * @return OTHER_IDENTIFY_NAME 其他证件名称
     */
    public String getOtherIdentifyName() {
        return otherIdentifyName;
    }

    /**
     * 其他证件名称
     * @param otherIdentifyName 其他证件名称
     */
    public void setOtherIdentifyName(String otherIdentifyName) {
        this.otherIdentifyName = otherIdentifyName == null ? null : otherIdentifyName.trim();
    }

    /**
     * 证件号码
     * @return IDENTIFY_NUMBER 证件号码
     */
    public String getIdentifyNumber() {
        return identifyNumber;
    }

    /**
     * 证件号码
     * @param identifyNumber 证件号码
     */
    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber == null ? null : identifyNumber.trim();
    }

    /**
     * SPV资金托管账户开户行
     * @return SPV_CAP_ACC_BANK SPV资金托管账户开户行
     */
    public String getSpvCapAccBank() {
        return spvCapAccBank;
    }

    /**
     * SPV资金托管账户开户行
     * @param spvCapAccBank SPV资金托管账户开户行
     */
    public void setSpvCapAccBank(String spvCapAccBank) {
        this.spvCapAccBank = spvCapAccBank == null ? null : spvCapAccBank.trim();
    }

    /**
     * 其他资金托管账户开户行
     * @return OTHER_CAP_ACC_BANK 其他资金托管账户开户行
     */
    public String getOtherCapAccBank() {
        return otherCapAccBank;
    }

    /**
     * 其他资金托管账户开户行
     * @param otherCapAccBank 其他资金托管账户开户行
     */
    public void setOtherCapAccBank(String otherCapAccBank) {
        this.otherCapAccBank = otherCapAccBank == null ? null : otherCapAccBank.trim();
    }

    /**
     * SPV登记编码
     * @return SPV_REGIST_CODE SPV登记编码
     */
    public String getSpvRegistCode() {
        return spvRegistCode;
    }

    /**
     * SPV登记编码
     * @param spvRegistCode SPV登记编码
     */
    public void setSpvRegistCode(String spvRegistCode) {
        this.spvRegistCode = spvRegistCode == null ? null : spvRegistCode.trim();
    }

    /**
     * 权益人登记日期
     * @return HOLDER_DATE 权益人登记日期
     */
    public Date getHolderDate() {
        return holderDate;
    }

    /**
     * 权益人登记日期
     * @param holderDate 权益人登记日期
     */
    public void setHolderDate(Date holderDate) {
        this.holderDate = holderDate;
    }

    /**
     * 权益人名称
     * @return HOLDER_NAME 权益人名称
     */
    public String getHolderName() {
        return holderName;
    }

    /**
     * 权益人名称
     * @param holderName 权益人名称
     */
    public void setHolderName(String holderName) {
        this.holderName = holderName == null ? null : holderName.trim();
    }

    /**
     * 性别
     * @return HOLDER_SEX 性别
     */
    public String getHolderSex() {
        return holderSex;
    }

    /**
     * 性别
     * @param holderSex 性别
     */
    public void setHolderSex(String holderSex) {
        this.holderSex = holderSex == null ? null : holderSex.trim();
    }

    /**
     * 风险偏好
     * @return HOLDER_RISK_PREFER 风险偏好
     */
    public String getHolderRiskPrefer() {
        return holderRiskPrefer;
    }

    /**
     * 风险偏好
     * @param holderRiskPrefer 风险偏好
     */
    public void setHolderRiskPrefer(String holderRiskPrefer) {
        this.holderRiskPrefer = holderRiskPrefer == null ? null : holderRiskPrefer.trim();
    }

    /**
     * 手机号码
     * @return HOLDER_CELLPHONE 手机号码
     */
    public String getHolderCellphone() {
        return holderCellphone;
    }

    /**
     * 手机号码
     * @param holderCellphone 手机号码
     */
    public void setHolderCellphone(String holderCellphone) {
        this.holderCellphone = holderCellphone == null ? null : holderCellphone.trim();
    }

    /**
     * 固定电话
     * @return HOLDER_TELEPHONE 固定电话
     */
    public String getHolderTelephone() {
        return holderTelephone;
    }

    /**
     * 固定电话
     * @param holderTelephone 固定电话
     */
    public void setHolderTelephone(String holderTelephone) {
        this.holderTelephone = holderTelephone == null ? null : holderTelephone.trim();
    }

    /**
     * 是否报送
     * @return IS_SUBMIT 是否报送
     */
    public String getIsSubmit() {
        return isSubmit;
    }

    /**
     * 是否报送
     * @param isSubmit 是否报送
     */
    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit == null ? null : isSubmit.trim();
    }

    /**
     * 电子邮箱
     * @return HOLDER_EMAIL 电子邮箱
     */
    public String getHolderEmail() {
        return holderEmail;
    }

    /**
     * 电子邮箱
     * @param holderEmail 电子邮箱
     */
    public void setHolderEmail(String holderEmail) {
        this.holderEmail = holderEmail == null ? null : holderEmail.trim();
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
     * 数据状态
     * @return STATUS 数据状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 数据状态
     * @param status 数据状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 工作流代码
     * @return WORKFLOW_ID 工作流代码
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 工作流代码
     * @param workflowId 工作流代码
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 部门代码
     * @return DEPT_CODE 部门代码
     */
    public String getDeptCode() {
        return deptCode;
    }

    /**
     * 部门代码
     * @param deptCode 部门代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
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

    public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	/**
     * 数据来源
     * @return DATA_SOURCE 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 数据来源
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    /**
     * 机构代码
     * @return ORG_CODE 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码
     * @param orgCode 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 经办人
     * @return AGENT 经办人
     */
    public String getAgent() {
        return agent;
    }

    /**
     * 经办人
     * @param agent 经办人
     */
    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
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
     * 接入检查结果
     * @return INTERF_CHECK_RESULT 接入检查结果
     */
    public String getInterfCheckResult() {
        return interfCheckResult;
    }

    /**
     * 接入检查结果
     * @param interfCheckResult 接入检查结果
     */
    public void setInterfCheckResult(String interfCheckResult) {
        this.interfCheckResult = interfCheckResult == null ? null : interfCheckResult.trim();
    }

	public String getOperatestatus() {
		return operatestatus;
	}

	public void setOperatestatus(String operatestatus) {
		this.operatestatus = operatestatus== null ? null : operatestatus.trim();
	}

	public String getMergerindentifymark() {
		return this.mergerindentifymark;
	}

	public void setMergerindentifymark(String mergerindentifymark) {
		this.mergerindentifymark = mergerindentifymark== null ? null : mergerindentifymark.trim();
	}
	
	//处理状态
    public String getOperatestatusStr() {
		return getOperatestatus()==null?"":(getOperatestatus()+" "+SysDataUtil.getNameFromDataDict(DictCode.处理状态_中债,getOperatestatus()));
	}
    //是否属于本行
    public String getIsBelongBankStr() {
    	return getIsBelongBank()==null?"":(getIsBelongBank()+" "+SysDataUtil.getNameFromDataDict(DictCode.是否_中债,getIsBelongBank()));
    }
    //投资者境内外标识
    public String getHolderMarkStr() {
    	return getHolderMark()==null?"":(getHolderMark()+" "+SysDataUtil.getNameFromDataDict(DictCode.境内外,getHolderMark()));
    }
    //权益人类别
    public String getHolderTypeStr() {
    	return getHolderType()==null?"":(getHolderType()+" "+SysDataUtil.getNameFromDataDict(DictCode.投资者类别,getHolderType()));
    }
    //个人证件类别
    public String getPersonalIdentifyTypeStr(){
    	return getPersonalIdentifyType()==null?"":(getPersonalIdentifyType()+" "+SysDataUtil.getNameFromDataDict(DictCode.个人证件类别,getPersonalIdentifyType()));
    }
    //机构证件类别
    public String getInstitutionIdentifyTypeStr(){
    	return getInstitutionIdentifyType()==null?"":(getInstitutionIdentifyType()+" "+SysDataUtil.getNameFromDataDict(DictCode.机构证件类别,getInstitutionIdentifyType()));
    }
    //性别
    public String getHolderSexStr(){
    	return getHolderSex()==null?"":(getHolderSex()+" "+SysDataUtil.getNameFromDataDict(DictCode.性别_中债,getHolderSex()));
    }
    //风险偏好
    public String getHolderRiskPreferStr(){
    	return getHolderRiskPrefer()==null?"":(getHolderRiskPrefer()+" "+SysDataUtil.getNameFromDataDict(DictCode.投资风险偏好,getHolderRiskPrefer()));
    }
    //证件类别
    public String getDocumentIdentifyTypeStr(){
    	return getPersonalIdentifyTypeStr()==null?getInstitutionIdentifyTypeStr():getPersonalIdentifyTypeStr();
    }
    //权益人所属国家或地区
    public String getHolderRegionStr(){
    	return getHolderRegion()==null?"":(getHolderRegion()+" "+SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区,getHolderRegion()));
    }
    public String getInterfCheckResultStr(){
    	return getInterfCheckResult()==null?"":(getInterfCheckResult()+" "+SysDataUtil.getNameFromDataDict(DictCode.检查结果,getInterfCheckResult()));
    }
    /****ZGBSSE-842 【中债三期】识别标识合并导出数据数据中合并后识别标识选择值错误***/
    public String getChooseMergerStr(){
        return !getIndentifyMark().equals(getMergerindentifymark())?"OFF":"ON";
    }

    public String getUpdateTimeStr() {
        return DateUtil.formatDateTime(this.getUpdateTime());
    }

    public String getDataDateStr() {
        return DateUtil.formatDateTime(this.getDataDate());
    }
}