package com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

/**
 * 描述:PBC_PRD_ACCOUNT_SUB表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-06-12
 */
@TableName("PBC_PRD_ACCOUNT_SUB")
public class PbcPrdAccountSub extends BaseEntity{
    /**
     * 主键uuid
     */
    private String registSeqno;

    /**
     * 资产池代码
     */
    @BusinessKey("PBC_ACCOUNT_ID")
    private String pbcAccountId;
    /**
     * 产品代码
     */
    private String accountId;

    /**
     * 理财产品的发行机构给代码
     */
    private String issuePartyId;

    /**
     * 资产池名称
     */
    private String accountName;

    /**
     * 资产池起始日期
     */
    private Date vdate;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 报送反馈状态
     */
    @DictFormat(DictCode.人行报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 人行报送截止日
     */
    private Date pbcSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 数据状态
     */
    private String status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 机构代码
     */
    private String orgCode;

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

    private List<String> prodList;
    
    /**
     *  人行代码
     */
    private String pbcCode;
    
    /**
     *  资产池代码（内部）（页面展示用）
     */
    private String pbcAccountIdShow;
    
    
    /** 资产池代码（内部）（页面展示用）
     * @return PBC_ACCOUNT_ID
     */
    public String getPbcAccountIdShow() {
		return pbcAccountIdShow;
	}

	/**
	 * @param pbcAccountIdShow
	 */
	public void setPbcAccountIdShow(String pbcAccountIdShow) {
		this.pbcAccountIdShow = pbcAccountIdShow;
	}

	/** 人行代码
     * @return PBC_CODE 人行代码
     */
    public String getPbcCode() {
		return pbcCode;
	}

	/**
	 * @param pbcCode
	 */
	public void setPbcCode(String pbcCode) {
		this.pbcCode = pbcCode;
	}

    /**
     * 主键uuid
     * @return REGIST_SEQNO 主键uuid
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 主键uuid
     * @param registSeqno 主键uuid
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 资产池代码
     * @return PBC_ACCOUNT_ID 资产池代码
     */
    public String getPbcAccountId() {
        return pbcAccountId;
    }

    /**
     * 资产池代码
     * @param pbcAccountId 资产池代码
     */
    public void setPbcAccountId(String pbcAccountId) {
        this.pbcAccountId = pbcAccountId == null ? null : pbcAccountId.trim();
    }

    /**
     * 理财产品的发行机构给代码
     * @return ISSUE_PARTY_ID 理财产品的发行机构给代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 理财产品的发行机构给代码
     * @param issuePartyId 理财产品的发行机构给代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 资产池名称
     * @return ACCOUNT_NAME 资产池名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 资产池名称
     * @param accountName 资产池名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    /**
     * 资产池起始日期
     * @return VDATE 资产池起始日期
     */
    public Date getVdate() {
        return vdate;
    }
    public String getVdate01() {
        return DateUtil.format(vdate,"yyyyMMdd");
    }
    public String getVdate02() {
        return DateUtil.format(vdate,"yyyy-MM-dd");
    }
    public String getVdate03() {return DateUtil.format(vdate,"yyyy/M/d");
    }
    /**
     * 资产池起始日期
     * @param vdate 资产池起始日期
     */
    public void setVdate(Date vdate) {
        this.vdate = vdate;
    }

    /**
     * 报送状态
     * @return SEND_STATUS 报送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 报送状态
     * @param sendStatus 报送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 报送日期
     * @return SUBMIT_TIME 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }
    public String getSubmitTime01() {
        return DateUtil.format(submitTime,"yyyyMMdd");
    }
    /**
     * 报送日期
     * @param submitTime 报送日期
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 报送反馈状态
     * @return SUBMIT_FEEDBACK_STATUS 报送反馈状态
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 报送反馈状态
     * @param submitFeedbackStatus 报送反馈状态
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 报送流水号
     * @return SUMIT_SEQ 报送流水号
     */
    public String getSumitSeq() {
        return sumitSeq;
    }

    /**
     * 报送流水号
     * @param sumitSeq 报送流水号
     */
    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq == null ? null : sumitSeq.trim();
    }

    /**
     * 数据日期
     * @return STATUS_DATE 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }
    public String getStatusDate01() {
    	return DateUtil.format(statusDate,"yyyyMMdd");
    }
    /**
     * 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 人行报送截止日
     * @return PBC_SUBMIT_DEADLINE 人行报送截止日
     */
    public Date getPbcSubmitDeadline() {
        return pbcSubmitDeadline;
    }
    public String getPbcSubmitDeadline01() {
    	return DateUtil.format(pbcSubmitDeadline,"yyyyMMdd");
    }
    /**
     * 人行报送截止日
     * @param pbcSubmitDeadline 人行报送截止日
     */
    public void setPbcSubmitDeadline(Date pbcSubmitDeadline) {
        this.pbcSubmitDeadline = pbcSubmitDeadline;
    }

    /**
     * 行内报送截止日
     * @return SUBMIT_DEADLINE 行内报送截止日
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 行内报送截止日
     * @param submitDeadline 行内报送截止日
     */
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
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

	public List<String> getProdList() {
		return prodList;
	}

	public void setProdList(List<String> prodList) {
		this.prodList = prodList;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
	}
	
	public String getSendStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
	}
	
	public String getSubmitFeedbackStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, this.getSubmitFeedbackStatus());
	}
    public String getStatusDateStr(){
        return null !=statusDate?DateUtil.formatDateTime(statusDate):null;
    }
    public String getSubmitTimeStr(){
        return null !=submitTime?DateUtil.formatDateTime(submitTime):null;
    }
    public String getPbcSubmitDeadlineStr(){
        return null !=pbcSubmitDeadline?DateUtil.format(pbcSubmitDeadline):null;
    }
}