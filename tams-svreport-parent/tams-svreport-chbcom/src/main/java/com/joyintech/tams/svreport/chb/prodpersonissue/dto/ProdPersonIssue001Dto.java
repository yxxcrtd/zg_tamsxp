package com.joyintech.tams.svreport.chb.prodpersonissue.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.csii.dc.client.util.StringUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.json.BigDecimalSerializer;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;

@TableName("CHB_PRD_ISSUE_SUB")
public class  ProdPersonIssue001Dto extends BaseEntity implements ITableRow{
	private DecimalFormat df5 = new DecimalFormat("0.00000");
	private DecimalFormat df2 = new DecimalFormat("0.00");
	
	private static final long serialVersionUID = 8664492633593217854L;
	
	private String prodRegCode;
	/**
     * 报送流水号
     */
    private String sumitSeq;
	/**
	 * 产品名称
	 */
	private String prodName;
	
    /**
     * 	登记序列																		
     */
    private String registSeqno;

    /**
     * 	产品登记编码																		
     */
    private String prodRegistCode;

    /**
     * 	理财产品代码																		
     */
    @BusinessKey("PROD_ID")
    private String prodId;

    /**
     * 	募集起始日期																		
     */
    private Date raiseVdate;

    /**
     * 	募集结束日期																		
     */
    private Date raiseEnddate;

    /**
     * 	产品起始日期																		
     */
    private Date prdVdate;

    /**
     * 	产品终止日期																		
     */
    private Date prdMdate;

    /**
     * 	是否为结构化（分级）产品																		
     */
    @DictFormat(DictCode.是否_中债)
    private String isConstracte;

    /**
     * 	层级类别			11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五															
     */
    @DictFormat(DictCode.层级类别)
    private String gradeType;

    /**
     * 	预计客户最低年收益率 %																		
     */
    private BigDecimal expMinYield;

    /**
     * 	预计客户最高年收益率 %																		
     */
    private BigDecimal expMaxYield;

    /**
     * 	平均预计客户年收益率%																		
     */
    private BigDecimal expAvgYield;

    /**
     * 	收益率分档情况说明																		
     */
    private String yieldComment;

    /**
     * 	开放模式			01 有规律开放/02 无规律开放															
     */
    @DictFormat(DictCode.开放模式_中债)
    private String openMode;

    /**
     * 	规律开放周期																		
     */
    @DictFormat(DictCode.规律开放周期)
    private String openCircle;

    /**
     * 	其他规律开放周期 (天)																		
     */
    private BigDecimal openCircleDays;

    /**
     * 	无规律开放说明																		
     */
    private String ruleComment;

    /**
     * 	首次开放周期起始日																		
     */
    private Date firstOpenDay;

    /**
     * 	节假日是否开放						01 是/02 否												
     */
    @DictFormat(DictCode.是否_中债)
    private String holidayOnFlag;

    /**
     * 	平均开放次数（年化）																		
     */
    private BigDecimal avgOpenCount;

    
    private  String  avgOpenCountStr;
    /**
     * 	开放期业务																		
     */
    @DictFormat(DictCode.开放期业务)
    private String openBuss;

    /**
     * 	开放期业务说明																		
     */
    private String openBussRemark;

    /**
     * 	工作流代码																		
     */
    private String workflowId;

    /**
     * 	反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败																		
     */
    @DictFormat(DictCode.报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 	报送状态			未报送/接口报送/手工处理															
     */
    private String sendStatus;

    /**
     * 	数据日期																		
     */
    private Date statusDate;

    /**
     * 	数据状态，			新建/修改/删除															
     */
    private String status;

    /**
     * 	机构代码																		
     */
    private String orgCode;

    /**
     * 	创建人																		
     */
    private String createUser;

    /**
     * 	创建部门																		
     */
    private String createDept;

    /**
     * 	创建时间																		
     */
    private Date createTime;

    /**
     * 	更新人																		
     */
    private String updateUser;

    /**
     * 	更新时间																		
     */
    private Date updateTime;

    /**
     * 	报送日期																		
     */
    private Date submitTime;

    /**
     * 	中债报送截止日																		
     */
    private Date chbSubmitDeadline;

    /**
     * 	报送截止日																		
     */
    private Date submitDeadline;

    /**
     * 	发行机构代码																		
     */
    private String issuePartyId;
    
    private String issuesendstatus;
    
    /**
	 * 开放模式申
	 */
	public String opentypep;
	
	/**
     * 管理方式
     */
    @DictFormat(DictCode.管理方式_发行登记)
    private String manageType;

    /**
     * 业绩比较基准下限
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal yieldLow;

    private  String  yieldLowStr;

    /**
     * 业绩比较基准上限
     */
    @JsonSerialize(using=BigDecimalSerializer.class)
    private BigDecimal yieldHigh;

    private  String  yieldHighStr;
    /**
     * 业绩比较基准说明
     */
    private String yieldRemark;

    /**
     * 资金托管账号
     */
    private String busiAcctNo;

    /**
     * 资金托管账户
     */
    private String acctName;

    /**
     * 分级比例
     */
	private   BigDecimal gradProportion;

    public BigDecimal getGradProportion() {
        return gradProportion;
    }

    public void setGradProportion(BigDecimal gradProportion) {
        this.gradProportion = gradProportion;
    }

    //	private String saletarget;
	
//    public String getSaletarget() {
//		return saletarget;
//	}

//	public void setSaletarget(String saletarget) {
//		this.saletarget = saletarget;
//	}

	public String getIssuesendstatus() {
		return issuesendstatus;
	}

	public void setIssuesendstatus(String issuesendstatus) {
		this.issuesendstatus = issuesendstatus;
	}

	public String getOpentypep() {
		return opentypep;
	}

	public void setOpentypep(String opentypep) {
		this.opentypep = opentypep;
	}

	/**
     * 	登记序列																		
     * @return REGIST_SEQNO 	登记序列																		
     */
    public String getRegistSeqno() {
        return registSeqno;
    }

    /**
     * 	登记序列																		
     * @param registSeqno 	登记序列																		
     */
    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno == null ? null : registSeqno.trim();
    }

    /**
     * 	产品登记编码																		
     * @return PROD_REGIST_CODE 	产品登记编码																		
     */
    public String getProdRegistCode() {
        return prodRegistCode;
    }

    /**
     * 	产品登记编码																		
     * @param prodRegistCode 	产品登记编码																		
     */
    public void setProdRegistCode(String prodRegistCode) {
        this.prodRegistCode = prodRegistCode == null ? null : prodRegistCode.trim();
    }

    /**
     * 	理财产品代码																		
     * @return PROD_ID 	理财产品代码																		
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * 	理财产品代码																		
     * @param prodId 	理财产品代码																		
     */
    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    /**
     * 	募集起始日期																		
     * @return RAISE_VDATE 	募集起始日期																		
     */
    public Date getRaiseVdate() {
        return raiseVdate;
    }

    /**
     * 	募集起始日期																		
     * @param raiseVdate 	募集起始日期																		
     */
    public void setRaiseVdate(Date raiseVdate) {
        this.raiseVdate = raiseVdate;
    }

    /**
     * 	募集结束日期																		
     * @return RAISE_ENDDATE 	募集结束日期																		
     */
    public Date getRaiseEnddate() {
        return raiseEnddate;
    }

    /**
     * 	募集结束日期																		
     * @param raiseEnddate 	募集结束日期																		
     */
    public void setRaiseEnddate(Date raiseEnddate) {
        this.raiseEnddate = raiseEnddate;
    }

    /**
     * 	产品起始日期																		
     * @return PRD_VDATE 	产品起始日期																		
     */
    public Date getPrdVdate() {
        return prdVdate;
    }

    /**
     * 	产品起始日期																		
     * @param prdVdate 	产品起始日期																		
     */
    public void setPrdVdate(Date prdVdate) {
        this.prdVdate = prdVdate;
    }

    /**
     * 	产品终止日期																		
     * @return PRD_MDATE 	产品终止日期																		
     */
    public Date getPrdMdate() {
        return prdMdate;
    }

    /**
     * 	产品终止日期																		
     * @param prdMdate 	产品终止日期																		
     */
    public void setPrdMdate(Date prdMdate) {
        this.prdMdate = prdMdate;
    }

    /**
     * 	是否为结构化（分级）产品																		
     * @return IS_CONSTRACTE 	是否为结构化（分级）产品																		
     */
    public String getIsConstracte() {
        return isConstracte;
    }

    /**
     * 	是否为结构化（分级）产品																		
     * @param isConstracte 	是否为结构化（分级）产品																		
     */
    public void setIsConstracte(String isConstracte) {
        this.isConstracte = isConstracte == null ? null : isConstracte.trim();
    }

    /**
     * 	层级类别			11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五															
     * @return GRADE_TYPE 	层级类别			11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五															
     */
    public String getGradeType() {
        return gradeType;
    }

    /**
     * 	层级类别			11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五															
     * @param gradeType 	层级类别			11 优先层级一/12 优先层级二/13 优先层级三/14 优先层级四/15 优先层级五/21 进取层级一/22 进取层级二/23 进取层级三/24 进取层级四/25 进取层级五															
     */
    public void setGradeType(String gradeType) {
        this.gradeType = gradeType == null ? null : gradeType.trim();
    }

    /**
     * 	预计客户最低年收益率 %																		
     * @return EXP_MIN_YIELD 	预计客户最低年收益率 %																		
     */
    public BigDecimal getExpMinYield() {
        return expMinYield;
    }

    /**
     * 	预计客户最低年收益率 %																		
     * @param expMinYield 	预计客户最低年收益率 %																		
     */
    public void setExpMinYield(BigDecimal expMinYield) {
        this.expMinYield = expMinYield;
    }

    /**
     * 	预计客户最高年收益率 %																		
     * @return EXP_MAX_YIELD 	预计客户最高年收益率 %																		
     */
    public BigDecimal getExpMaxYield() {
        return expMaxYield;
    }

    /**
     * 	预计客户最高年收益率 %																		
     * @param expMaxYield 	预计客户最高年收益率 %																		
     */
    public void setExpMaxYield(BigDecimal expMaxYield) {
        this.expMaxYield = expMaxYield;
    }

    /**
     * 	平均预计客户年收益率%																		
     * @return EXP_AVG_YIELD 	平均预计客户年收益率%																		
     */
    public BigDecimal getExpAvgYield() {
        return expAvgYield;
    }

    /**
     * 	平均预计客户年收益率%																		
     * @param expAvgYield 	平均预计客户年收益率%																		
     */
    public void setExpAvgYield(BigDecimal expAvgYield) {
        this.expAvgYield = expAvgYield;
    }

    /**
     * 	收益率分档情况说明																		
     * @return YIELD_COMMENT 	收益率分档情况说明																		
     */
    public String getYieldComment() {
        return yieldComment;
    }

    /**
     * 	收益率分档情况说明																		
     * @param yieldComment 	收益率分档情况说明																		
     */
    public void setYieldComment(String yieldComment) {
        this.yieldComment = yieldComment == null ? null : yieldComment.trim();
    }

    /**
     * 	开放模式			01 有规律开放/02 无规律开放															
     * @return OPEN_MODE 	开放模式			01 有规律开放/02 无规律开放															
     */
    public String getOpenMode() {
        return openMode;
    }

    /**
     * 	开放模式			01 有规律开放/02 无规律开放															
     * @param openMode 	开放模式			01 有规律开放/02 无规律开放															
     */
    public void setOpenMode(String openMode) {
        this.openMode = openMode == null ? null : openMode.trim();
    }

    /**
     * 	规律开放周期																		
     * @return OPEN_CIRCLE 	规律开放周期																		
     */
    public String getOpenCircle() {
        return openCircle;
    }

    /**
     * 	规律开放周期																		
     * @param openCircle 	规律开放周期																		
     */
    public void setOpenCircle(String openCircle) {
        this.openCircle = openCircle == null ? null : openCircle.trim();
    }

    /**
     * 	其他规律开放周期 (天)																		
     * @return OPEN_CIRCLE_DAYS 	其他规律开放周期 (天)																		
     */
    public BigDecimal getOpenCircleDays() {
        return openCircleDays;
    }

    /**
     * 	其他规律开放周期 (天)																		
     * @param openCircleDays 	其他规律开放周期 (天)																		
     */
    public void setOpenCircleDays(BigDecimal openCircleDays) {
        this.openCircleDays = openCircleDays;
    }

    /**
     * 	无规律开放说明																		
     * @return RULE_COMMENT 	无规律开放说明																		
     */
    public String getRuleComment() {
        return ruleComment;
    }

    /**
     * 	无规律开放说明																		
     * @param ruleComment 	无规律开放说明																		
     */
    public void setRuleComment(String ruleComment) {
        this.ruleComment = ruleComment == null ? null : ruleComment.trim();
    }

    /**
     * 	首次开放周期起始日																		
     * @return FIRST_OPEN_DAY 	首次开放周期起始日																		
     */
    public Date getFirstOpenDay() {
        return firstOpenDay;
    }

    /**
     * 	首次开放周期起始日																		
     * @param firstOpenDay 	首次开放周期起始日																		
     */
    public void setFirstOpenDay(Date firstOpenDay) {
        this.firstOpenDay = firstOpenDay;
    }

    /**
     * 	节假日是否开放						01 是/02 否												
     * @return HOLIDAY_ON_FLAG 	节假日是否开放						01 是/02 否												
     */
    public String getHolidayOnFlag() {
        return holidayOnFlag;
    }

    /**
     * 	节假日是否开放						01 是/02 否												
     * @param holidayOnFlag 	节假日是否开放						01 是/02 否												
     */
    public void setHolidayOnFlag(String holidayOnFlag) {
        this.holidayOnFlag = holidayOnFlag == null ? null : holidayOnFlag.trim();
    }

    /**
     * 	平均开放次数（年化）																		
     * @return AVG_OPEN_COUNT 	平均开放次数（年化）																		
     */
    public BigDecimal getAvgOpenCount() {
        return avgOpenCount;
    }

    /**
     * 	平均开放次数（年化）																		
     * @param avgOpenCount 	平均开放次数（年化）																		
     */
    public void setAvgOpenCount(BigDecimal avgOpenCount) {
        this.avgOpenCount = avgOpenCount;
    }

    /**
     * 	开放期业务																		
     * @return OPEN_BUSS 	开放期业务																		
     */
    public String getOpenBuss() {
        return openBuss;
    }

    /**
     * 	开放期业务																		
     * @param openBuss 	开放期业务																		
     */
    public void setOpenBuss(String openBuss) {
        this.openBuss = openBuss == null ? null : openBuss.trim();
    }

    /**
     * 	开放期业务说明																		
     * @return OPEN_BUSS_REMARK 	开放期业务说明																		
     */
    public String getOpenBussRemark() {
        return openBussRemark;
    }

    /**
     * 	开放期业务说明																		
     * @param openBussRemark 	开放期业务说明																		
     */
    public void setOpenBussRemark(String openBussRemark) {
        this.openBussRemark = openBussRemark == null ? null : openBussRemark.trim();
    }

    /**
     * 	工作流代码																		
     * @return WORKFLOW_ID 	工作流代码																		
     */
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * @param workflowId 	工作流代码																		
     * 	工作流代码																		
     */
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId == null ? null : workflowId.trim();
    }

    /**
     * 	反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败																		
     * @return SUBMIT_FEEDBACK_STATUS 	反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败																		
     */
    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    /**
     * 	反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败																		
     * @param submitFeedbackStatus 	反馈状态 未反馈/中债通过/中债退回/发送成功/发送失败																		
     */
    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus == null ? null : submitFeedbackStatus.trim();
    }

    /**
     * 	报送状态			未报送/接口报送/手工处理															
     * @return SEND_STATUS 	报送状态			未报送/接口报送/手工处理															
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 	报送状态			未报送/接口报送/手工处理															
     * @param sendStatus 	报送状态			未报送/接口报送/手工处理															
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * 	数据日期																		
     * @return STATUS_DATE 	数据日期																		
     */
    public Date getStatusDate() {
        return statusDate;
    }

    /**
     * 	数据日期																		
     * @param statusDate 	数据日期																		
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * 	数据状态，			新建/修改/删除															
     * @return STATUS 	数据状态，			新建/修改/删除															
     */
    public String getStatus() {
        return status;
    }

    /**
     * 	数据状态，			新建/修改/删除															
     * @param status 	数据状态，			新建/修改/删除															
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 	机构代码																		
     * @return ORG_CODE 	机构代码																		
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 	机构代码																		
     * @param orgCode 	机构代码																		
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /**
     * 	创建人																		
     * @return CREATE_USER 	创建人																		
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 	创建人																		
     * @param createUser 	创建人																		
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * 	创建部门																		
     * @return CREATE_DEPT 	创建部门																		
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * 	创建部门																		
     * @param createDept 	创建部门																		
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    /**
     * 	创建时间																		
     * @return CREATE_TIME 	创建时间																		
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 	创建时间																		
     * @param createTime 	创建时间																		
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 	更新人																		
     * @return UPDATE_USER 	更新人																		
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 	更新人																		
     * @param updateUser 	更新人																		
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * 	更新时间																		
     * @return UPDATE_TIME 	更新时间																		
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 	更新时间																		
     * @param updateTime 	更新时间																		
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 	报送日期																		
     * @return SUBMIT_TIME 	报送日期																		
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 	报送日期																		
     * @param submitTime 	报送日期																		
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 	中债报送截止日																		
     * @return CHB_SUBMIT_DEADLINE 	中债报送截止日																		
     */
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    /**
     * 	中债报送截止日																		
     * @param chbSubmitDeadline 	中债报送截止日																		
     */
    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    /**
     * 	报送截止日																		
     * @return SUBMIT_DEADLINE 	报送截止日																		
     */
    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    /**
     * 	报送截止日																		
     * @param submitDeadline 	报送截止日																		
     */
    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    /**
     * 	发行机构代码																		
     * @return ISSUE_PARTY_ID 	发行机构代码																		
     */
    public String getIssuePartyId() {
        return issuePartyId;
    }

    /**
     * 	发行机构代码																		
     * @param issuePartyId 	发行机构代码																		
     */
    public void setIssuePartyId(String issuePartyId) {
        this.issuePartyId = issuePartyId == null ? null : issuePartyId.trim();
    }

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
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

	public String getStatusStr(){
	    if (!StringUtil.isEmpty(this.getStatus())){
	        return SvrDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
	    }
	    return this.getStatus();
	}
	
	public String getSendStatusStr(){
        if (!StringUtil.isEmpty(this.getSendStatus())){
            return SvrDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
        }
        return this.getSendStatus();
	}
	
	public String getSubmitFeedbackStatusStr(){
        if (!StringUtil.isEmpty(this.getSubmitFeedbackStatus())){
            return SvrDataUtil.getNameFromDataDict(DictCode.报送反馈状态, this.getSubmitFeedbackStatus());
        }
        return this.getSubmitFeedbackStatus();
	}
	/**
	 * 管理方式
	 */
    private String manageTypeStr;
	public String getManageTypeStr() {
        if (!StringUtil.isEmpty(this.getManageType())){
            return this.getManageType().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.管理方式_发行登记, this.getManageType());        }
        return this.getManageType();
	}
	/**
	 * 是否为结构化（分级）产品
	 */
    private String isConstracteStr;
	public String getIsConstracteStr() {
        if (!StringUtil.isEmpty(this.getIsConstracte())){
            return this.getIsConstracte().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getIsConstracte());
        }
        return this.getIsConstracte();
	}

	/**
	 * 层级类别
	 */
	private String gradeTypeStr;
	public String getGradeTypeStr() {
        if (!StringUtil.isEmpty(this.getGradeType())){
            return this.getGradeType().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.层级类别, this.getGradeType());
        }
        return this.getGradeType();
	}
	/**
	 * 规律开放周期----->1
	 */
	private String openCircleStr;
	public String getOpenCircleStr() {
        if (!StringUtil.isEmpty(this.getOpenCircle())){
            return this.getOpenCircle().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.规律开放周期, this.getOpenCircle());
        }
        return this.getOpenCircle();
	}

	/**
	 * 节假日是否开放
	 */
	private String holidayOnFlagStr;
	public String getHolidayOnFlagStr() {
        if (!StringUtil.isEmpty(this.getHolidayOnFlag())){
            return this.getHolidayOnFlag().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.是否_中债, this.getHolidayOnFlag());
        }
        return this.getHolidayOnFlag();
	}

	/**
	 * 开放期业务-------->1
	 */
	private String openBussStr;

	public void setIsConstracteStr(String isConstracteStr) {
		this.isConstracteStr = isConstracteStr;
	}

	public void setGradeTypeStr(String gradeTypeStr) {
		this.gradeTypeStr = gradeTypeStr;
	}

	public void setOpenCircleStr(String openCircleStr) {
		this.openCircleStr = openCircleStr;
	}

	public void setHolidayOnFlagStr(String holidayOnFlagStr) {
		this.holidayOnFlagStr = holidayOnFlagStr;
	}

	public void setOpenBussStr(String openBussStr) {
		this.openBussStr = openBussStr;
	}

	public String getOpenBussStr() {
        if (!StringUtil.isEmpty(this.getOpenBuss())){
            return this.getOpenBuss().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.开放期业务, this.getOpenBuss());
        }
        return this.getOpenBuss();
	}
	
	public String getOpenModeStr() {
        if (!StringUtil.isEmpty(this.getOpenMode())){
            return this.getOpenMode().trim()+" "+SvrDataUtil.getNameFromDataDict(DictCode.开放模式_中债,getOpenMode());
        }
        return this.getOpenMode();
	}
	
	//募集起始日期
	private String raiseVdateStr;
	public String getRaiseVdateStr(){
        if (!StringUtil.isEmpty(this.getRaiseVdate())){
            return DateUtil.format(this.getRaiseVdate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	//募集结束日期
	private String raiseEnddateStr;
	public String getRaiseEnddateStr(){
        if (!StringUtil.isEmpty(this.getRaiseEnddate())){
            return DateUtil.format(this.getRaiseEnddate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	
	//产品起始日期
	private String prdVdateStr;
	public String getPrdVdateStr(){
        if (!StringUtil.isEmpty(this.getPrdVdate())){
            return DateUtil.format(this.getPrdVdate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
	//产品终止日期
	private String prdMdateStr;
	public String getPrdMdateStr(){
        if (!StringUtil.isEmpty(this.getPrdMdate())){
            return DateUtil.format(this.getPrdMdate(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
	}
    //产品终止日期
    public String getFirstOpenDayStr(){
        if (!StringUtil.isEmpty(this.getFirstOpenDay())){
            return DateUtil.format(this.getFirstOpenDay(), DateUtil.DEFAULT_OTHER_FORMAT);
        }
        return "";
    }

	public void setRaiseVdateStr(String raiseVdateStr) {
		this.raiseVdateStr = raiseVdateStr;
	}

	public void setRaiseEnddateStr(String raiseEnddateStr) {
		this.raiseEnddateStr = raiseEnddateStr;
	}

	public void setPrdVdateStr(String prdVdateStr) {
		this.prdVdateStr = prdVdateStr;
	}

	public void setPrdMdateStr(String prdMdateStr) {
		this.prdMdateStr = prdMdateStr;
	}

	public String getProdRegCode() {
		return prodRegCode;
	}

	public void setProdRegCode(String prodRegCode) {
		this.prodRegCode = prodRegCode;
	}
	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public BigDecimal getYieldLow() {
		return yieldLow;
	}

	public void setYieldLow(BigDecimal yieldLow) {
		this.yieldLow = yieldLow;
	}

	public BigDecimal getYieldHigh() {
		return yieldHigh;
	}

	public void setYieldHigh(BigDecimal yieldHigh) {
		this.yieldHigh = yieldHigh;
	}

	public String getYieldRemark() {
		return yieldRemark;
	}

	public void setYieldRemark(String yieldRemark) {
		this.yieldRemark = yieldRemark;
	}

	public String getBusiAcctNo() {
		return busiAcctNo;
	}

	public void setBusiAcctNo(String busiAcctNo) {
		this.busiAcctNo = busiAcctNo;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAvgOpenCountStr() {
		return avgOpenCount==null?"":df2.format(avgOpenCount);
	}
	public String getYieldLowStr() {
		return yieldLow==null?"":df5.format(yieldLow);
	}
	public String getYieldHighStr() {
		return yieldHigh==null?"":df5.format(yieldHigh);
	}

    /**
     * 定期开放周期
     */
    private  BigDecimal   periodicIntervalDays;

    public BigDecimal getPeriodicIntervalDays() {
        return periodicIntervalDays;
    }

    public void setPeriodicIntervalDays(BigDecimal periodicIntervalDays) {
        this.periodicIntervalDays = periodicIntervalDays;
    }
}
