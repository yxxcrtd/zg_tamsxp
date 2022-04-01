
package com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author Administrator
 * @version 1.0 2017年9月19日
 * @see SalesTransactionMaintenanceRegist001Dto
 * @since 1.0
 */
@TableName("CHB_SALER_STL_DEAL_SUB")
public class SalesTransactionMaintenanceRegist001Dto extends BaseEntity implements ProcessEntity {
    /**
     * 检查序列号
     */
    private String checkSeqno;
    /**
     * 中债报送截止日
     */
    private Date chbSubmitDeadline;

    /**
     *报送截止日
     */
    private Date submitDeadline;

    /**
     *数据日期
     */
    private Date statusDate;

    /**
     *报送时间
     */
    private Date submitTime;

    /**
     *报送流水号
     */
    private String sumitSeq;

    /**
     *报送状态
     */
    private String sendStatus;

    /**
     *反馈状态
     */
    @DictFormat(DictCode.报送反馈状态)
    private String submitFeedbackStatus;

    /**
     *报送反馈消息
     */
    private String submitFeedbackMsg;

    /**
     * UUID主键
     */
    private String registSeqno;

    /**
     * 数据状态
     */
    private String dataStatus;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 提供销售结算资金服务机构代码
     */
    @BusinessKey("INSTITUTION_CODE")
    private String institutionCode;

    /**
     * 产品登记编码
     */
    @BusinessKey("PRD_REGIST_CODE")
    private String prdRegistCode;

    /**
     * 销售结算资金账号
     */
    private String accountNo;

    /**
     * 销售结算资金账户名称
     */
    private String accountName;

    /**
     * 销售结算资金交易流水号
     */
    @BusinessKey("TRADE_NO")
    private String tradeNo;

    /**
     * 销售结算资金流动方向
     */
    @DictFormat(DictCode.销售结算资金流动方向)
    private String tradePs;

    /**
     * 销售结算资金交易对手方账号
     */
    private String tradeCounterAccNo;

    /**
     * 销售结算资金交易对手方账户名称
     */
    private String tradeCounterAccName;

    /**
     * 销售结算资金交易对手方开户行名称
     */
    private String tradeCounterAccBankName;

    /**
     * 销售结算资金交易对手方账户开户行号
     */
    private String tradeCounterAccBankNo;

    /**
     * 销售结算资金交易币种
     */
    @DictFormat(DictCode.币种_中债)
    private String tradeCcy;

    /**
     * 销售结算资金交易金额
     */
    private BigDecimal tradeAmt;

    /**
     * 销售结算资金交易日期
     */
    private Date tradeVdate;

    /**
     * 销售结算资金交易时间
     */
    private String tradeTime;

    /**
     * 销售结算资金交易附言
     */
    private String postscript;

    /**
     * 备注
     */
    private String remark;
    /**
     * 取得 销售结算资金交易日期字符串格式
     * @return 销售结算资金交易日期
     */
    public String getTradeVdateStr() {
        return DateUtil.format(this.getTradeVdate());
    }
    /**
     * 取得 中债报送截止日字符串格式
     * @return
     */
    public String getChbSubmitDeadlineStr() {
        return DateUtil.format(this.getChbSubmitDeadline());
    }
    /**
     * 取得 数据日期字符串格式
     * @return 数据日期
     */
    public String getStatusDateStr() {
        return DateUtil.format(this.getStatusDate());
    }
    public Date getChbSubmitDeadline() {
        return chbSubmitDeadline;
    }

    public void setChbSubmitDeadline(Date chbSubmitDeadline) {
        this.chbSubmitDeadline = chbSubmitDeadline;
    }

    public Date getSubmitDeadline() {
        return submitDeadline;
    }

    public void setSubmitDeadline(Date submitDeadline) {
        this.submitDeadline = submitDeadline;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSumitSeq() {
        return sumitSeq;
    }

    public void setSumitSeq(String sumitSeq) {
        this.sumitSeq = sumitSeq;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSubmitFeedbackStatus() {
        return submitFeedbackStatus;
    }

    public void setSubmitFeedbackStatus(String submitFeedbackStatus) {
        this.submitFeedbackStatus = submitFeedbackStatus;
    }

    public String getSubmitFeedbackMsg() {
        return submitFeedbackMsg;
    }

    public void setSubmitFeedbackMsg(String submitFeedbackMsg) {
        this.submitFeedbackMsg = submitFeedbackMsg;
    }

    public String getRegistSeqno() {
        return registSeqno;
    }

    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getPrdRegistCode() {
        return prdRegistCode;
    }

    public void setPrdRegistCode(String prdRegistCode) {
        this.prdRegistCode = prdRegistCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradePs() {
        return tradePs;
    }

    public void setTradePs(String tradePs) {
        this.tradePs = tradePs;
    }

    public String getTradeCounterAccNo() {
        return tradeCounterAccNo;
    }

    public void setTradeCounterAccNo(String tradeCounterAccNo) {
        this.tradeCounterAccNo = tradeCounterAccNo;
    }

    public String getTradeCounterAccName() {
        return tradeCounterAccName;
    }

    public void setTradeCounterAccName(String tradeCounterAccName) {
        this.tradeCounterAccName = tradeCounterAccName;
    }

    public String getTradeCounterAccBankName() {
        return tradeCounterAccBankName;
    }

    public void setTradeCounterAccBankName(String tradeCounterAccBankName) {
        this.tradeCounterAccBankName = tradeCounterAccBankName;
    }

    public String getTradeCounterAccBankNo() {
        return tradeCounterAccBankNo;
    }

    public void setTradeCounterAccBankNo(String tradeCounterAccBankNo) {
        this.tradeCounterAccBankNo = tradeCounterAccBankNo;
    }

    public String getTradeCcy() {
        return tradeCcy;
    }

    public void setTradeCcy(String tradeCcy) {
        this.tradeCcy = tradeCcy;
    }

    public BigDecimal getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(BigDecimal tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    public Date getTradeVdate() {
        return tradeVdate;
    }

    public void setTradeVdate(Date tradeVdate) {
        this.tradeVdate = tradeVdate;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getPostscript() {
        return postscript;
    }

    public void setPostscript(String postscript) {
        this.postscript = postscript;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradePsName() {
        return this.getTradePs() + " " + SysDataUtil.getNameFromDataDict(DictCode.销售结算资金流动方向, this.getTradePs());
    }
    public String getDataStatusName() {
        return SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getDataStatus());
    }
    public String getTradeCcyName() {
        return SysDataUtil.getNameFromDataDict(DictCode.币种_中债, this.getTradeCcy());
    }
    public String getSendStatusName() {
        return SysDataUtil.getNameFromDataDict(DictCode.报送状态, this.getSendStatus());
    }
    public String getStatus() {
        return SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, this.getDataStatus());
    }

    public String getSubmitTimeStr() {
        if(StringUtil.isNotEmpty(this.getSubmitTime())){
            return DateUtil.formatDateTime(this.getSubmitTime());
        }
        return null;
    }

    public String getSubmitDeadlineStr() {
        if(StringUtil.isNotEmpty(this.getSubmitDeadline())){
            return DateUtil.formatDateTime(this.getSubmitDeadline());
        }
        return null;
    }

    public String getSubmitFeedbackStatusStr() {
        return submitFeedbackStatus==null?null:SysDataUtil.getNameFromDataDict(DictCode.报送反馈状态, this.submitFeedbackStatus);
    }

    public String getCheckSeqno() {
        return checkSeqno;
    }

    public void setCheckSeqno(String checkSeqno) {
        this.checkSeqno = checkSeqno;
    }
    

}
