
package com.joyintech.tams.report.submitmanagement.agentinstitutionregist.dto;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.base.entity.ChbAgentInstitutionSub;
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
 * @see AgentInstitutionRegistDto
 * @since 1.0
 */
@TableName("CHB_AGENT_INSTITUTION_SUB")
public class AgentInstitutionRegistDto extends ChbAgentInstitutionSub implements ProcessEntity {

    @BusinessKey("AGENT_INSTITUTION_CODE")
    public String agentInstitutionCode;//代销机构代码业务主键
    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 代销机构名称
     */
    private String agentInstitutionName;

    /**
     * 发行机构代码
     */
    private String issuePartyId;

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
     * 附件代码
     */
    private String fileNo;

    /**
     * 附件个数
     */
    private String enclosureNum;

    /**
     * 附件存放目录（包括文件名称）
     */
    private String enclosureAddress;

    /**
     * 附件修改标志
     */
    private String enclosureChangeFlag;

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
}
