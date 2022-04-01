
package com.joyintech.tams.report.submitmanagement.salerrewpenainforegist.dto;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.base.entity.ChbSalerRewPenaSub;
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
 * @see SalerRewPenaInfoRegistDto
 * @since 1.0
 */
@TableName("CHB_SALER_REW_PENA_SUB")
public class SalerRewPenaInfoRegistDto extends ChbSalerRewPenaSub implements ProcessEntity {

    @BusinessKey("SALER_REW_PENA_NO")
    public String salerRewPenaNo;//奖惩编号业务主键

    /**
     * 登记序列
     */
    private String registSeqno;

    /**
     * 内部编号
     */
    private String salerInsNo;

    /**
     * 销售机构代码
     */
    private String salerInstCode;

    /**
     * 记录分类
     */
    @DictFormat(DictCode.记录分类)
    private String salerRewPenaTyep;

    /**
     * 奖惩名称
     */
    private String salerRewPenaName;

    /**
     * 奖惩说明
     */
    private String salerRewPenaComments;

    /**
     * 奖惩单位
     */
    @DictFormat(DictCode.奖惩单位)
    private String salerRewPenaInst;

    /**
     * 奖惩单位具体名称
     */
    private String salerRewPenaInstName;

    /**
     * 奖惩起始日期
     */
    private Date salerRewPenaStart;

    /**
     * 奖惩结束日期
     */
    private Date salerRewPenaEnd;

    /**
     * 投诉详情
     */
    private String salerComplainDetail;

    /**
     * 投诉处理结果
     */
    private String salerComplainResult;

    /**
     * 投诉日期
     */
    private Date salerComplainDate;

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
    //奖惩起始日期字符串化
    public String getSalerRewPenaStartStr() {
        if(StringUtil.isNotEmpty(this.getSalerRewPenaStart())){
            String time = DateUtil.formatDateTime(this.getSalerRewPenaStart());
            return time.substring(0,10).replace("-", "").replace("/", "");
        }
        return null;
    }
    //奖惩结束日期字符串化
    public String getSalerRewPenaEndStr() {
        if(StringUtil.isNotEmpty(this.getSalerRewPenaEnd())){
            String time = DateUtil.formatDateTime(this.getSalerRewPenaEnd());
            return time.substring(0,10).replace("-", "").replace("/", "");
        }
        return null;
    }
    //投诉日期字符串化
    public String getSalerComplainDateStr() {
        if(StringUtil.isNotEmpty(this.getSalerComplainDate())){
            String time = DateUtil.formatDateTime(this.getSalerComplainDate());
            return time.substring(0,10).replace("-", "").replace("/", "");
        }
        return null;
    }
    //记录分类字符串化
    public String getSalerRewPenaTyepStr() {
        return this.getSalerRewPenaTyep();
    }
    //奖惩单位字符串化
    public String getSalerRewPenaInstStr() {
        return this.getSalerRewPenaInst();
    }
}
