package com.joyintech.tams.svreport.pbc.report.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.system.base.entity.SysInstitution;
import com.joyintech.system.department.dto.SysDepartmentDto;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;

/**
 * 描述:PBC_REPORT表的实体类
 * @version
 * @author:  Administrator
 * @创建时间: 2019-01-26
 */
@TableName("PBC_REPORT")
public class PbcMonthReport extends BaseEntity implements Comparable<PbcMonthReport>{
	/**
     * 主键
     */
    private String reportuuid;

    /**
     * 期数
     */
    private String termid;

    /**
     * 报表类型:
     */
    private String reporttype;

    /**
     * 开始日期
     */
    private Date startdate;

    /**
     * 结束日期
     */
    private Date enddate;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 发行机构名称
     */
    private String issuePartyName;

    /**
     * 报表频率：D-日，WE-周报，M-月报，S-季，Y-年
     */
    private String reportfrequ;

    /**
     * 填报机构名称
     */
    private String reportOrgName;

    /**
     * 社会信用代码
     */
    private String socialCreditCode;

    /**
     * 金融机构编码
     */
    private String finInstitutionCode;

    /**
     * 产品品种
     */
    private String prodVariety;

    /**
     * 责任人
     */
    private String personLiable;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 制表日期
     */
    private Date reportDate;

    /**
     * 报送日期
     */
    private Date submitTime;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 返回信息
     */
    private String submitFeedbackStatus;

    /**
     * 有效状态
     */
    private String status;

    /**
     * 有效状态
     */
    private String statusStr;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 报送状态
     */
    private String sendStatusStr;

    /**
     * 数据日期
     */
    private Date statusDate;

    /**
     * 创建人
     */
    private String createUser;

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
     * 机构代码
     */
    private String orgCode;

    /**
     * 部门代码
     */
    private String deptCode;
    
    /**
     * 部门名称
     */
    private String deptName;
    
    public String getDeptName() {

    	SysInstitution dtp = (SysInstitution) CommonUtil.queryCode(GlobalConstant.SYS_INSTITUTION_KEY,deptCode);
		
		return null!=dtp?dtp.getInstitutionName():(null==deptCode?"广州银行":"未知机构");
	}


	/**
     * 创建部门
     */
    private String createDept;

    /**
     * 产品id
     */
    private String prodid;
    /**
     * 产品id
     */
    private List<String> prodids;
    
    /**
     * 是否生成报表
     */
    private boolean isGen;
    
    /**
     * 资产池代码
     */
    private String pbcAccountId;
    
    /**
     * 人行资产池登记主键uuid
     */
    private String  registSeqno;
    
    /**
     *  人行代码
     */
    private String pbcCode;
    
    private String accountId;
    
    
    /**
     * 结束日期string 
     */
    private String enddate01;
    
    public String getEnddate01() {
		return DateUtil.format(enddate,"yyyy/MM/dd");
	}

	public void setEnddate01(String enddate01) {
		this.enddate01 = enddate01;
	}

	/**  
	 * @Description TODO  
	 * @Author      高健  
	 * @Date        2019年12月12日 上午11:15:48  
	 * @return accountId  
	 */
	
	public String getAccountId() {
		return accountId;
	}

	/**  
	 * @Description TODO  
	 * @Author      高健  
	 * @Date        2019年12月12日 上午11:15:48  
	 * @param accountId accountId  
	 */
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

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
     * null
     * @return REPORTUUID null
     */
    public String getReportuuid() {
        return reportuuid;
    }

    /**
     * null
     * @param reportuuid null
     */
    public void setReportuuid(String reportuuid) {
        this.reportuuid = reportuuid == null ? null : reportuuid.trim();
    }

    /**
     * 期数
     * @return TERMID 期数
     */
    public String getTermid() {
        return termid;
    }

    /**
     * 期数
     * @return TERMID 期数
     */
    public String getTermReport() {
        return termid+reporttype;
    }

    /**
     * 期数
     * @param termid 期数
     */
    public void setTermid(String termid) {
        this.termid = termid == null ? null : termid.trim();
    }

    /**
     * 报表类型：PROD-产品，BALANCE-资产负债表，DEPOSIT-结构性存款
     * @return REPORTTYPE 报表类型：PROD-产品，BALANCE-资产负债表，DEPOSIT-结构性存款
     */
    public String getReporttype() {
        return reporttype;
    }

    /**
     * 报表类型：PROD-产品，BALANCE-资产负债表，DEPOSIT-结构性存款
     * @param reporttype 报表类型：PROD-产品，BALANCE-资产负债表，DEPOSIT-结构性存款
     */
    public void setReporttype(String reporttype) {
        this.reporttype = reporttype == null ? null : reporttype.trim();
    }

    /**
     * 开始日期
     * @return STARTDATE 开始日期
     */
    public Date getStartdate() {
        return startdate;
    }

    /**
     * 开始日期
     * @param startdate 开始日期
     */
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    /**
     * 结束日期
     * @return ENDDATE 结束日期
     */
    public Date getEnddate() {
        return enddate;
    }

    /**
     * 结束日期
     * @param enddate 结束日期
     */
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    /**
     * 发行机构代码
     * @return ISSUE_PARTY_ID 发行机构代码
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 发行机构代码
     * @param issuePartyId 发行机构代码
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

    /**
     * 发行机构名称
     * @return ISSUE_PARTY_NAME 发行机构名称
     */
    public String getIssuePartyName() {
        return issuePartyName;
    }

    /**
     * 发行机构名称
     * @param issuePartyName 发行机构名称
     */
    public void setIssuePartyName(String issuePartyName) {
        this.issuePartyName = issuePartyName == null ? null : issuePartyName.trim();
    }

    /**
     * 报表频率
     * @return REPORTFREQU 报表频率
     */
    public String getReportfrequ() {
        return reportfrequ;
    }

    /**
     * 报表频率
     * @param reportfrequ 报表频率
     */
    public void setReportfrequ(String reportfrequ) {
        this.reportfrequ = reportfrequ == null ? null : reportfrequ.trim();
    }

    /**
     * 报送日期
     * @return SUBMIT_TIME 报送日期
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 报送日期
     * @param submitTime 报送日期
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 中债报送截止日
     * @return CHB_SUBMIT_DEADLINE 中债报送截止日
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 中债报送截止日
     * @param chbSubmitDeadline 中债报送截止日
     */
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
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
     * 返回信息
     * @return SUBMIT_FEEDBACK_STATUS 返回信息
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 返回信息
     * @param submitFeedbackStatus 返回信息
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 有效状态
     * @return STATUS 有效状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 有效状态
     * @param status 有效状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
     * 数据日期
     * @return STATUS_DATE 数据日期
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 数据日期
     * @param statusDate 数据日期
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
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
     * 机构代码
     * @return ORD_CODE 机构代码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 机构代码
     * @param ORG_CODE 机构代码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
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
    
	public String getStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
	}
	
	public String getSendStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
	}
	
	public String getSubmitFeedbackStatusStr(){
		return SvrDataUtil.getNameFromDataDict(DictCode.人行报送反馈状态, this.getSubmitFeedbackStatus());
	}
	
	public String getTermname(){
		if(termid==null) return null;
		return termid.substring(0, 4)+"年"+termid.substring(4, 6)+"月";
	}
	
	public String getReportname(){
	   
		return "表"+reporttype+":"+SvrDataUtil.getNameFromDataDict(DictCode.人行报表名称,reporttype)+"_"+this.getTermname();
//		return PbcDictCodeListExt2.报表名称_银行月度统计表.getCodeName()+"_"+this.getTermname();
		
	}

    public String getProdid() {
        return prodid;
    }
    
    public void setProdid(String prodid) {
        this.prodid = prodid;
    }

	public List<String> getProdids() {
		return prodids;
	}

	public void setProdids(List<String> prodids) {
		this.prodids = prodids;
	}

	public String getReportOrgName() {
		return reportOrgName;
	}

	public void setReportOrgName(String reportOrgName) {
		this.reportOrgName = reportOrgName;
	}

	public String getSocialCreditCode() {
		return socialCreditCode;
	}

	public void setSocialCreditCode(String socialCreditCode) {
		this.socialCreditCode = socialCreditCode;
	}

	public String getFinInstitutionCode() {
		return finInstitutionCode;
	}

	public void setFinInstitutionCode(String finInstitutionCode) {
		this.finInstitutionCode = finInstitutionCode;
	}

	public String getProdVariety() {
		return prodVariety;
	}

	public void setProdVariety(String prodVariety) {
		this.prodVariety = prodVariety;
	}

	public String getPersonLiable() {
		return personLiable;
	}

	public void setPersonLiable(String personLiable) {
		this.personLiable = personLiable;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

    public boolean isGen() {
        return isGen;
    }

    public void setGen(boolean isGen) {
        this.isGen = isGen;
    }

    public String getPbcAccountId() {
        return pbcAccountId;
    }

    public void setPbcAccountId(String pbcAccountId) {
        this.pbcAccountId = pbcAccountId;
    }

    /**
     * 产品代码+机构代码
     * @return  品代码+机构代码
     */
    public String getPbcProdidAndOrgCode() {
        return pbcAccountId + GlobalConstant.VERTICAL_LINE + orgCode;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public void setSendStatusStr(String sendStatusStr) {
        this.sendStatusStr = sendStatusStr;
    }

    public String getRegistSeqno() {
		return registSeqno;
	}

	public void setRegistSeqno(String registSeqno) {
		this.registSeqno = registSeqno;
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(PbcMonthReport o) {
        if(o.getTermid() !=null&&this.getTermid()!=null) {
            if(this.getTermid().compareTo(o.getTermid())==0) {
                return this.getReportname().compareTo(o.getReportname());
            }else {
                return   this.getTermid().compareTo(o.getTermid());
            }
        }else {
            return 0  ;
        }
    }

    public String getStatusDateStr(){
        return null !=statusDate?DateUtil.formatDateTime(statusDate):null;
    }
    public String getSubmitTimeStr(){
        return null !=submitTime?DateUtil.formatDateTime(submitTime):null;
    }
    public String getChbSubmitDeadlineStr(){
        return null !=chbSubmitDeadline?DateUtil.format(chbSubmitDeadline):null;
    }

}