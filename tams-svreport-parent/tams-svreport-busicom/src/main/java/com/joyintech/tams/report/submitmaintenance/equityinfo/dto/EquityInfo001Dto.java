/*
 * 文件名：EquityInfo001Dto.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年9月16日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitmaintenance.equityinfo.dto;

import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.constant.DictCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.common.base.TableRow;
import com.joyintech.tams.common.base.entity.ChbSysUser;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author Administrator
 * @version 1.0 2017年9月16日
 * @see EquityInfo001Dto
 * @since 1.0
 */
public class EquityInfo001Dto extends TableRow {
    /**
     * 是否是识别标识变更
     */
    private String isChangeId;
    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 维护表中是否有数据
     */
    private String isNull;
    /**
     * 维护表中的数据状态
     */
    private String editStatus;

    /**
     * 获取 orgCode
     *
     * @return orgCode.
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 orgCode
     *
     * @param orgCode orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    private String status;// 数据状态

    private String bankCode;// 登记银行代码

    private String indentifyMark;// 识别标识

    private String orgIndentifyMark;// 原识别标识

    private String isBelongBank;// 该权益人是否属于本行

    private String theBankName;// 权益人所属银行名称

    private String theBankCode;// 权益人所属银行代码

    private String holderMark;// 权益人境内外标识

    private String holderRegion;// 权益人所属国家或地区

    private String holderType;// 权益人类别

    private String holderClassify;// 权益人分类

    private String personalIdentifyType;// 个人证件类型

    private String institutionIdentifyType;// 机构证件类型

    private String inentifyType;// 证件类别

    private String identifyNumber;// 证件号码

    private String spvCapAccBank;// SPV资金托管户开户行

    private String otherCapAccBank;// 其他资金托管账户开户行

    private String spvRegistCode;// SPV登记编码

    private String holderName;// 权益人名称

    private String holderSex;// 性别

    private String holderRiskPrefer;// 风险偏好

    private String holderCellphone;// 手机号码

    private String holderTelephone;// 固定电话

    private String holderEmail;// 电子邮箱

    private String remark;// 备注
    
    private String interfCheckResult;//接口接入数据检查结果


    private String fundAccPlace;// 账号开户所在地

    public String getFundAccPlace() {
        return fundAccPlace;
    }

    public void setFundAccPlace(String fundAccPlace) {
        this.fundAccPlace = fundAccPlace;
    }

    /**
     * 经办人
     */
    private String agent;
    
    private String repFailedInfo;//中债报送信息反馈
    
    
    

	/**
     * 是否报送
     */
    private String isSubmit;

    /**
     * 导入数据状态
     */
    private String importStatus;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 其他证件名称
     */
    private String otherIdentifyName;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 权益人登记日期
     */
    private Date holderDate;

    /**
     * 流程ID
     */
    private String workflowId;

    /**
     * 报送数据状态
     */
    private String submitStatus;

    /**
     * 删除list
     */
    private List<EquityInfo001Dto> deleteList;

    /**
     * 报送流水
     */
    private String sumitSeq;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     *
     */
    private String deptCode;
    /**
     * excel 第几行
     */
    private String excelNum;

    /**
     * 检查序列号
     */
    private String checkSeqno;

    /**
     * 创建部门
     */
    private String createDept;
    /**
     * 创建部门
     */
    private String datatype;
    
    private String regCode;
    
    private Map<String,String> tempMap;
    
    public String getRegCode() {
		return regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
    
    public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	/**
     * 获取 status
     *
     * @return status.
     */
    public String getStatus() {
        return status;
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
     * 获取 bankCode
     *
     * @return bankCode.
     */
    public String getBankCode() {
        return bankCode;
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
        return indentifyMark;
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
        return orgIndentifyMark;
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
        return isBelongBank;
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
        return theBankName;
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
        return theBankCode;
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
        return holderMark;
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
        return holderRegion;
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
        return holderType;
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

    /**
     * 获取 inentifyType
     *
     * @return inentifyType.
     */
    public String getInentifyType() {
        return inentifyType;
    }

    /**
     * 设置 inentifyType
     *
     * @param inentifyType inentifyType
     */
    public void setInentifyType(String inentifyType) {
        this.inentifyType = inentifyType;
    }

    /**
     * 获取 inentifyNumber
     *
     * @return inentifyNumber.
     */
    public String getIdentifyNumber() {
        return identifyNumber;
    }

    /**
     * 设置 inentifyNumber
     *
     * @param inentifyNumber inentifyNumber
     */
    public void setIdentifyNumber(String identifyNumber) {
        this.identifyNumber = identifyNumber;
    }

    /**
     * 获取 spvCapAccBank
     *
     * @return spvCapAccBank.
     */
    public String getSpvCapAccBank() {
        return spvCapAccBank;
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
        return otherCapAccBank;
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
        return spvRegistCode;
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
        return holderName;
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
        return holderSex;
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
        return holderRiskPrefer;
    }

    public String getHolderRiskPreferStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.投资风险偏好,
                getHolderRiskPrefer());
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
        return holderCellphone;
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
     * 获取 holderTelephone
     *
     * @return holderTelephone.
     */
    public String getHolderTelephone() {
        return holderTelephone;
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
     * 获取 holderEmail
     *
     * @return holderEmail.
     */
    public String getHolderEmail() {
        return holderEmail;
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
        return remark;
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
        return personalIdentifyType;
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
        return institutionIdentifyType;
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
     * 获取 type
     *
     * @return type.
     */
    public String getType() {
        return type;
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
     * 获取 deleteList
     *
     * @return deleteList.
     */
    public List<EquityInfo001Dto> getDeleteList() {
        return deleteList;
    }

    /**
     * 设置 deleteList
     *
     * @param deleteList deleteList
     */
    public void setDeleteList(List<EquityInfo001Dto> deleteList) {
        this.deleteList = deleteList;
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
     * 获取 importStatus
     *
     * @return importStatus.
     */
    public String getImportStatus() {
        return importStatus;
    }

    /**
     * 设置 importStatus
     *
     * @param importStatus importStatus
     */
    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    /**
     * 获取 otherIdentifyName
     *
     * @return otherIdentifyName.
     */
    public String getOtherIdentifyName() {
        return otherIdentifyName;
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
     * 获取 isSubmit
     *
     * @return isSubmit.
     */
    public String getIsSubmit() {
        return isSubmit;
    }

    /**
     * 设置 isSubmit
     *
     * @param isSubmit isSubmit
     */
    public void setIsSubmit(String isSubmit) {
        this.isSubmit = isSubmit;
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
     * 获取 workflowId
     *
     * @return workflowId.
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * 设置 workflowId
     *
     * @param workflowId workflowId
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    /**
     * 获取 dataSource
     *
     * @return dataSource.
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 设置 dataSource
     *
     * @param dataSource dataSource
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取 submitStatus
     *
     * @return submitStatus.
     */
    public String getSubmitStatus() {
        return submitStatus;
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
     * 获取 sumitSeq
     *
     * @return sumitSeq.
     */
    public String getSumitSeq() {
        return sumitSeq;
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
     * 获取 agent
     * @return agent.
     */
    public String getAgent() {
        return agent;
    }


    /**
     * 设置 agent
     * @param agent agent
     */
    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    /**
     * 获取 excelNum
     * @return excelNum.
     */
    public String getExcelNum() {
        return excelNum;
    }


    /**
     * 设置 excelNum
     * @param excelNum excelNum
     */
    public void setExcelNum(String excelNum) {
        this.excelNum = excelNum;
    }

    public EquityInfo001Dto() {
        try {
            Session session = SecurityUtils.getSubject().getSession(false);
            if (session.getAttribute(GlobalConstant.CURRENT_USER) != null) {
                ChbSysUser user = (ChbSysUser)session.getAttribute(GlobalConstant.CURRENT_USER);
                this.createUser = user.getUserId();
                this.updateUser = user.getUserId();
            }
            else {
                this.createUser = GlobalConstant.SYSTEM_USER;
                this.updateUser = GlobalConstant.SYSTEM_USER;
                this.createTime = new Date();
                this.updateTime = new Date();
            }
            this.createTime = new Date();
            this.updateTime = new Date();
        }
        catch (Exception e) {
            this.createUser = GlobalConstant.SYSTEM_USER;
            this.updateUser = GlobalConstant.SYSTEM_USER;
            this.createTime = new Date();
            this.updateTime = new Date();
        }
    }


    /**
     * 获取 isChangeId
     * @return isChangeId.
     */
    public String getIsChangeId() {
        return isChangeId;
    }


    /**
     * 设置 isChangeId
     * @param isChangeId isChangeId
     */
    public void setIsChangeId(String isChangeId) {
        this.isChangeId = isChangeId;
    }

    /**
     * 获取 createDept
     *
     * @return createDept.
     */
    @Override
    public String getCreateDept() {
        if (StringUtil.isEmpty(this.createDept)) {
            if (StringUtil.isNotEmpty(getDeptCode())) {
                createDept = getDeptCode();
            }
            else {
                createDept = getOrgCode();
            }
        }
        return createDept;
    }

    /**
     * 设置 createDept
     *
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

	public String getRepFailedInfo() {
		return repFailedInfo;
	}

	public void setRepFailedInfo(String repFailedInfo) {
		this.repFailedInfo = repFailedInfo;
	}
    
    

	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	public String getEditStatus() {
		return editStatus;
	}

	public void setEditStatus(String editStatus) {
		this.editStatus = editStatus;
	}

	public String getInterfCheckResult() {
		return interfCheckResult;
	}

	public void setInterfCheckResult(String interfCheckResult) {
		this.interfCheckResult = interfCheckResult;
	}

	public Map<String,String> getTempMap() {
		return tempMap;
	}

	public void setTempMap(Map<String,String> tempMap) {
		this.tempMap = tempMap;
	}
    
}
