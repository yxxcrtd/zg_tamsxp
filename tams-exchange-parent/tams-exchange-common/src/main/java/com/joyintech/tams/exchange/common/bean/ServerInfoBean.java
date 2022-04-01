package com.joyintech.tams.exchange.common.bean;

public class ServerInfoBean {
    private String szServerName;
    private String szReceiveQName;
    private String szSendQName;
    private short nProtocal;

    private String oppositeLiaisonTel; //对手方交易员代码
    private String liaisonTel; // 本方交易员代码

    private String brokerId;//期货柜台Id

    private String authCode;//期货账户看穿式验证码

    private String appId;//期货账户客户端id

    private String userProductInfo;//期货账户客户端信息
    private String opEntrustWay; //委托方式
    /**
     * ufx主推消息订阅主题名
     */
    private String entrustTopicName;//委托主题名称
    private String tradeTopicName;//成交主题名称
    private String incomeTradeTopic;//固收协议回购成交主推主题名

    private String ufxTradeClientName; //ufx配置文件交易节点
    private String ufxSubClientName; //ufx配置文件订阅节点
    private String ufxBrokerType; //ufx 券商类型

    /**
     * 接口平台地址：为柜台指定接口平台的访问地址
     */
    private String dspUrl;

    public String getOppositeLiaisonTel() {
        return oppositeLiaisonTel;
    }

    public void setOppositeLiaisonTel(String oppositeLiaisonTel) {
        this.oppositeLiaisonTel = oppositeLiaisonTel;
    }

    public String getLiaisonTel() {
        return liaisonTel;
    }

    public void setLiaisonTel(String liaisonTel) {
        this.liaisonTel = liaisonTel;
    }

    public String getSzServerName() {
        return szServerName;
    }

    public void setSzServerName(String szServerName) {
        this.szServerName = szServerName;
    }

    public String getSzReceiveQName() {
        return szReceiveQName;
    }

    public void setSzReceiveQName(String szReceiveQName) {
        this.szReceiveQName = szReceiveQName;
    }

    public String getSzSendQName() {
        return szSendQName;
    }

    public void setSzSendQName(String szSendQName) {
        this.szSendQName = szSendQName;
    }

    public short getnProtocal() {
        return nProtocal;
    }

    public void setnProtocal(short nProtocal) {
        this.nProtocal = nProtocal;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserProductInfo() {
        return userProductInfo;
    }

    public void setUserProductInfo(String userProductInfo) {
        this.userProductInfo = userProductInfo;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(String brokerId) {
        this.brokerId = brokerId;
    }

    public String getOpEntrustWay() {
        return opEntrustWay;
    }

    public void setOpEntrustWay(String opEntrustWay) {
        this.opEntrustWay = opEntrustWay;
    }

    public String getEntrustTopicName() {
        return entrustTopicName;
    }

    public void setEntrustTopicName(String entrustTopicName) {
        this.entrustTopicName = entrustTopicName;
    }

    public String getTradeTopicName() {
        return tradeTopicName;
    }

    public void setTradeTopicName(String tradeTopicName) {
        this.tradeTopicName = tradeTopicName;
    }

    public String getIncomeTradeTopic() {
        return incomeTradeTopic;
    }

    public void setIncomeTradeTopic(String incomeTradeTopic) {
        this.incomeTradeTopic = incomeTradeTopic;
    }

    public String getUfxTradeClientName() {
        return ufxTradeClientName;
    }

    public void setUfxTradeClientName(String ufxTradeClientName) {
        this.ufxTradeClientName = ufxTradeClientName;
    }

    public String getUfxSubClientName() {
        return ufxSubClientName;
    }

    public void setUfxSubClientName(String ufxSubClientName) {
        this.ufxSubClientName = ufxSubClientName;
    }

    public String getUfxBrokerType() {
        return ufxBrokerType;
    }

    public void setUfxBrokerType(String ufxBrokerType) {
        this.ufxBrokerType = ufxBrokerType;
    }

    public String getDspUrl() {
        return dspUrl;
    }

    public void setDspUrl(String dspUrl) {
        this.dspUrl = dspUrl;
    }
}
