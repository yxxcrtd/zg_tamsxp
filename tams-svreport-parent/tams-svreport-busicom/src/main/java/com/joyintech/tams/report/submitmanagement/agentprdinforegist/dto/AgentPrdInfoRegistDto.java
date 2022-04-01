
package com.joyintech.tams.report.submitmanagement.agentprdinforegist.dto;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.base.entity.ChbAgentPrdSub;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.util.Date;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author Administrator
 * @version 1.0 2021.12.22
 * @see AgentPrdInfoRegistDto
 * @since 1.0
 */
@TableName("CHB_AGENT_PRD_SUB")
public class AgentPrdInfoRegistDto extends ChbAgentPrdSub implements ProcessEntity {
    @BusinessKey("AGENT_INSTITUTION_CODE")
    public String agentInstitutionCode;//代销机构代码业务主键

    public String agentInstitutionName;//代销机构名称

    @BusinessKey("PROD_REGIST_CODE")
    private String prodRegistCode;//产品登记编码业务主键

    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

    /**
     * 理财产品代码
     */
    private String prodId;

    /**
     * 理财产品名称
     */
    private String prodName;

    /**
     * 报送流水号
     */
    private String sumitSeq;

    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     * 行内报送截止日
     */
    private Date submitDeadline;

    /**
     * 报送状态
     */
    private String sendStatus;

    /**
     * 报送时间
     */
    private Date submitTime;

    /**
     * 反馈状态
     */
    @DictFormat(DictCode.报送反馈状态)
    private String submitFeedbackStatus;

    /**
     * 报送反馈消息
     */
    private String submitFeedbackMsg;

    /**
     * 数据日期
     */
    private Date dataDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 机构代码
     */
    private String orgCode;
    //报送状态字符串化
    public String getStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getStatus());
    }
    //报送状态字符串化
    public String getSendStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
    }
    //报送反馈状态字符串化
    public String getSubmitFeedbackStatusStr() { return SysDataUtil.getNameFromDataDict(DictCode.报送反馈状态, this.getSubmitFeedbackStatus()); }
    //报送时间字符串化
    public String getSubmitTimeStr() {
        if(StringUtil.isNotEmpty(this.getSubmitTime())){
            return DateUtil.formatDateTime(this.getSubmitTime());
        }
        return null;
    }
    //报送截止日字符串化
    public String getSubmitDeadlineStr() {
        if(StringUtil.isNotEmpty(this.getSubmitDeadline())){
            return DateUtil.formatDateTime(this.getSubmitDeadline());
        }
        return null;
    }

    @Override
    public String getAgentInstitutionCode() {
        return agentInstitutionCode;
    }

    @Override
    public void setAgentInstitutionCode(String agentInstitutionCode) {
        this.agentInstitutionCode = agentInstitutionCode;
    }

    @Override
    public String getProdRegistCode() {
        return prodRegistCode;
    }

    @Override
    public void setProdRegistCode(String prodRegistCode) {
        this.prodRegistCode = prodRegistCode;
    }

    public String getAgentInstitutionName() {
        return agentInstitutionName;
    }

    public void setAgentInstitutionName(String agentInstitutionName) {
        this.agentInstitutionName = agentInstitutionName;
    }
}
