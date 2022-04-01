package com.joyintech.tams.exchange.common.bean;

import java.io.Serializable;
import java.util.Objects;

public class ServerExtendInfo implements Serializable {

    private static final long serialVersionUID = 5026121323673513280L;
    private String brockerId;
    private String brockerName;
    private String szAddress;
    private String nPort;
    private String szAddressBack;
    private String nPortBack;
    private String orderUnit;
    private String netPriceReturn;
    private String specialBuss;
    private String agentType;
    private int nProtocal;

    private String szSendQName;
    private String szServerName;
    private String szReceiveQName;

    private String oppositeLiaisonTel; //对手方交易员代码
    private String liaisonTel; // 本方交易员代码

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

    public String getBrockerId() {
        return brockerId;
    }

    public void setBrockerId(String brockerId) {
        this.brockerId = brockerId;
    }

    public String getBrockerName() {
        return brockerName;
    }

    public void setBrockerName(String brockerName) {
        this.brockerName = brockerName;
    }

    public String getSzAddress() {
        return szAddress;
    }

    public void setSzAddress(String szAddress) {
        this.szAddress = szAddress;
    }

    public String getnPort() {
        return nPort;
    }

    public void setnPort(String nPort) {
        this.nPort = nPort;
    }

    public String getSzAddressBack() {
        return szAddressBack;
    }

    public void setSzAddressBack(String szAddressBack) {
        this.szAddressBack = szAddressBack;
    }

    public String getnPortBack() {
        return nPortBack;
    }

    public void setnPortBack(String nPortBack) {
        this.nPortBack = nPortBack;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public String getNetPriceReturn() {
        return netPriceReturn;
    }

    public void setNetPriceReturn(String netPriceReturn) {
        this.netPriceReturn = netPriceReturn;
    }

    public String getSpecialBuss() {
        return specialBuss;
    }

    public void setSpecialBuss(String specialBuss) {
        this.specialBuss = specialBuss;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public int getnProtocal() {
        return nProtocal;
    }

    public void setnProtocal(int nProtocal) {
        this.nProtocal = nProtocal;
    }

    public String getSzSendQName() {
        return szSendQName;
    }

    public void setSzSendQName(String szSendQName) {
        this.szSendQName = szSendQName;
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

    public String getDspUrl() {
        return dspUrl;
    }

    public void setDspUrl(String dspUrl) {
        this.dspUrl = dspUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerExtendInfo that = (ServerExtendInfo) o;
        return nProtocal == that.nProtocal &&
                Objects.equals(brockerId, that.brockerId) &&
                Objects.equals(brockerName, that.brockerName) &&
                Objects.equals(szAddress, that.szAddress) &&
                Objects.equals(nPort, that.nPort) &&
                Objects.equals(szAddressBack, that.szAddressBack) &&
                Objects.equals(nPortBack, that.nPortBack) &&
                Objects.equals(orderUnit, that.orderUnit) &&
                Objects.equals(netPriceReturn, that.netPriceReturn) &&
                Objects.equals(specialBuss, that.specialBuss) &&
                Objects.equals(agentType, that.agentType) &&
                Objects.equals(szSendQName, that.szSendQName) &&
                Objects.equals(szServerName, that.szServerName) &&
                Objects.equals(szReceiveQName, that.szReceiveQName) &&
                Objects.equals(oppositeLiaisonTel, that.oppositeLiaisonTel) &&
                Objects.equals(liaisonTel, that.liaisonTel) &&
                Objects.equals(authCode, that.authCode) &&
                Objects.equals(appId, that.appId) &&
                Objects.equals(userProductInfo, that.userProductInfo) &&
                Objects.equals(opEntrustWay, that.opEntrustWay) &&
                Objects.equals(entrustTopicName, that.entrustTopicName) &&
                Objects.equals(tradeTopicName, that.tradeTopicName) &&
                Objects.equals(ufxTradeClientName, that.ufxTradeClientName) &&
                Objects.equals(ufxSubClientName, that.ufxSubClientName) &&
                Objects.equals(ufxBrokerType, that.ufxBrokerType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brockerId, brockerName, szAddress, nPort, szAddressBack, nPortBack, orderUnit, netPriceReturn, specialBuss, agentType, nProtocal, szSendQName, szServerName, szReceiveQName, oppositeLiaisonTel, liaisonTel, authCode, appId, userProductInfo, opEntrustWay, entrustTopicName, tradeTopicName, incomeTradeTopic, ufxTradeClientName, ufxSubClientName, ufxBrokerType);
    }

    @Override
    public String toString() {
        return "ServerExtendInfo{" +
                "brockerId='" + brockerId + '\'' +
                ", brockerName='" + brockerName + '\'' +
                '}';
    }

    public String getOpEntrustWay() {
        return opEntrustWay;
    }

    public void setOpEntrustWay(String opEntrustWay) {
        this.opEntrustWay = opEntrustWay;
    }

    public String getUfxSubClientName() {
        return ufxSubClientName;
    }

    public void setUfxSubClientName(String ufxSubClientName) {
        this.ufxSubClientName = ufxSubClientName;
    }

    public String getUfxTradeClientName() {
        return ufxTradeClientName;
    }

    public void setUfxTradeClientName(String ufxTradeClientName) {
        this.ufxTradeClientName = ufxTradeClientName;
    }

    public String getUfxBrokerType() {
        return ufxBrokerType;
    }

    public void setUfxBrokerType(String ufxBrokerType) {
        this.ufxBrokerType = ufxBrokerType;
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
}
