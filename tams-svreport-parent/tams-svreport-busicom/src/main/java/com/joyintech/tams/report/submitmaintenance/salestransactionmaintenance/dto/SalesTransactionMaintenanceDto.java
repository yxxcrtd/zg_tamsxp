/*
 * 销售结算资金交易信息维护
 */
 
package com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.dto;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.component.svrreport.chb.contant.DictCodeList;
import com.joyintech.framework.base.BaseEntity;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.validation.annotation.ValidLogic;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.validate.SalesTransactionMaintenanceValid;
import com.joyintech.workflow.engine.dto.ProcessEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ValidLogic(targetClass = SalesTransactionMaintenanceValid.class, groups = {ValidateGroupEdit.class,
        ValidateGroupDelete.class})
public class SalesTransactionMaintenanceDto extends BaseEntity  implements ProcessEntity {
    /**
     * 主键uuid
     */
    private String elementId;//页面元素
    private String pageModel;//页面模型
    private String operateType;//操作类型
    private String dataStatus;// 数据状态
    private String statusValid;// 数据状态
    private String bankCode;// 登记银行代码
    private String registSeqno;//主键uuid
    private String maintainStatus;//报送信息维护状态
    private String disabledThing;//置灰控制
    /**
     * 原始数据
     */
    private String orgData;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 机构代码
     */
    private String orgCode;
    /**
     * 提供销售结算资金服务机构代码
     */
    private String institutionCode;

    /**
     * 产品登记编码
     */
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
    private String tradeNo;

    /**
     * 销售结算资金流动方向
     */
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
     * 选中的数据
     */
    private transient List<SalesTransactionMaintenanceDto> selectedItem;

    /**
     * 数据接入日期
     */
    private Date receptDate;

    public String getStatusValid() {
        return statusValid;
    }

    public void setStatusValid(String statusValid) {
        this.statusValid = statusValid;
    }

    // updateFlg 02 不展示  01 显示
    public String getUpdateFlg() {
        if(!StringUtil.isEmpty(statusValid) && StringUtil.equals(DictCodeList.维护数据状态_删除.getValue(), statusValid)){
            return "02";
        }else{
            return "01";
        }
    }
    public String getStatusValidStr() {
        return com.joyintech.framework.util.SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,
                getStatusValid());
    }
    /**
     * 取得 销售结算资金交易日期字符串格式
     * @return 销售结算资金交易日期
     */
    public String getTradeVdateStr() {
        return DateUtil.format(this.getTradeVdate());
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
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

    public Date getTradeVdate() {
        return tradeVdate;
    }

    public void setTradeVdate(Date tradeVdate) {
        this.tradeVdate = tradeVdate;
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

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getPageModel() {
        return pageModel;
    }

    public void setPageModel(String pageModel) {
        this.pageModel = pageModel;
    }

    public BigDecimal getTradeAmt() {
        return tradeAmt;
    }

    public void setTradeAmt(BigDecimal tradeAmt) {
        this.tradeAmt = tradeAmt;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Date getReceptDate() {
        return receptDate;
    }

    public void setReceptDate(Date receptDate) {
        this.receptDate = receptDate;
    }

    public String getRegistSeqno() {
        return registSeqno;
    }

    public void setRegistSeqno(String registSeqno) {
        this.registSeqno = registSeqno;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public List<SalesTransactionMaintenanceDto> getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(List<SalesTransactionMaintenanceDto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getTradePsName() {
        return this.getTradePs() + " " + SysDataUtil.getNameFromDataDict(DictCode.销售结算资金流动方向, this.getTradePs());
    }
    public String getStatusStr() {
        return SysDataUtil.getNameFromDataDict(DictCode.维护数据状态, this.getStatus());
    }
    public String getTradeCcyName() {
        return this.getTradeCcy() + " " + SysDataUtil.getNameFromDataDict(DictCode.币种_中债, this.getTradeCcy());
    }
    public String getMaintainStatusName() {
        return SysDataUtil.getNameFromDataDict(DictCode.中债一二三期文件处理状态, this.getMaintainStatus());
    }

    public String getMaintainStatus() {
        return maintainStatus;
    }

    public void setMaintainStatus(String maintainStatus) {
        this.maintainStatus = maintainStatus;
    }

    /**
     * 取得 原始数据
     * @return 原始数据
     */
    public String getOrgData() {
        return orgData;
    }

    /**
     * 设置 原始数据
     * @param orgData 原始数据
     */
    public void setOrgData(String orgData) {
        this.orgData = orgData == null ? null : orgData.trim();
    }

    /**
     * 取得 错误信息
     * @return 错误信息
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * 设置 错误信息
     * @param errMsg 错误信息
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg == null ? null : errMsg.trim();
    }

    public String getDisabledThing() {
        return disabledThing;
    }

    public void setDisabledThing(String disabledThing) {
        this.disabledThing = disabledThing;
    }
}