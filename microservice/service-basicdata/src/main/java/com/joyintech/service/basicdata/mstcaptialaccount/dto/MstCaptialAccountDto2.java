package com.joyintech.service.basicdata.mstcaptialaccount.dto;

/**
 * 交易系统查询资金账户接口返回实体
 */
public class MstCaptialAccountDto2 {

    /**
     * 网点账号
     */
    private String acctNo;
    /**
     * 账户账号
     */
    private String busiAcctNo;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 币种
     */
    private String CCY;
    /**
     * 资金账户类型
     */
    private String acctType;
    /**
     * 券商营业部代码
     */
    private String stockExchangeNo;
    /**
     * 启用状态
     */
    private String status;
    /**
     * 账户密码
     */
    private String acctPwd;
    /**
     * 交易密码
     */
    private String tradePwd;

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getBusiAcctNo() {
        return busiAcctNo;
    }

    public void setBusiAcctNo(String busiAcctNo) {
        this.busiAcctNo = busiAcctNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCCY() {
        return CCY;
    }

    public void setCCY(String cCY) {
        CCY = cCY;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getStockExchangeNo() {
        return stockExchangeNo;
    }

    public void setStockExchangeNo(String stockExchangeNo) {
        this.stockExchangeNo = stockExchangeNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcctPwd() {
        return acctPwd;
    }

    public void setAcctPwd(String acctPwd) {
        this.acctPwd = acctPwd;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }

}
