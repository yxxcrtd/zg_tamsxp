package com.joyintech.tams.exchange.common.bean.investment;

import com.google.common.base.Strings;
import com.joyintech.tams.exchange.common.bean.ServerExtendInfo;
import com.joyintech.tams.exchange.common.bean.ServerInfoBean;
import com.joyintech.tams.exchange.common.utils.AccountUtils;
import com.joyintech.tams.exchange.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 投管系统营业部查询返回bean
 */
public class BusinessDepartmentBean implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(BusinessDepartmentBean.class);
    private static final long serialVersionUID = 3719878179570814070L;
    /**
     * 所属券商ID
     */
    private String brockerId;

    /**
     * 所属券商名称
     */
    private String brockerName;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 端口
     */
    private String mac;

    /**
     * 备用IP地址
     */
    private String ipBack;

    /**
     * 备用端口
     */
    private String macBack;

    /**
     * 申报单位
     */
    private String orderUnit;

    /**
     * 净价回报
     */
    private String netPriceReturn;

    /**
     * 柜台接口类型
     */
    private String agentType;

    /**
     * 特殊处理业务
     */
    private String specialBuss;

    private String serverInfo;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIpBack() {
        return ipBack;
    }

    public void setIpBack(String ipBack) {
        this.ipBack = ipBack;
    }

    public String getMacBack() {
        return macBack;
    }

    public void setMacBack(String macBack) {
        this.macBack = macBack;
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

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getSpecialBuss() {
        return specialBuss;
    }

    public void setSpecialBuss(String specialBuss) {
        this.specialBuss = specialBuss;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    public ServerExtendInfo createServerExtendInfo() {
        try {
            ServerExtendInfo serverExtendInfo = new ServerExtendInfo();
            if (Strings.isNullOrEmpty(this.agentType)
                    || Strings.isNullOrEmpty(this.brockerId)
                    || Strings.isNullOrEmpty(this.serverInfo)) {
                return null;
            }
            serverExtendInfo.setAgentType(AccountUtils.convertAccountTypeAndAgentType(this.agentType));
            serverExtendInfo.setBrockerId(this.brockerId);
            if (!Strings.isNullOrEmpty(brockerName))
                serverExtendInfo.setBrockerName(this.brockerName);
            else
                serverExtendInfo.setBrockerName(this.brockerId);
            serverExtendInfo.setNetPriceReturn(this.netPriceReturn);
            serverExtendInfo.setnPort(this.mac);
            serverExtendInfo.setnPortBack(this.macBack);
            serverExtendInfo.setSzAddress(this.ip);
            serverExtendInfo.setSzAddressBack(this.ipBack);
            serverExtendInfo.setSpecialBuss(this.specialBuss);
            serverExtendInfo.setOrderUnit(this.orderUnit);
            ServerInfoBean objectFromJson = JsonUtils.getObjectListFromJson(this.getServerInfo(), ServerInfoBean.class).iterator().next();
            serverExtendInfo.setOpEntrustWay(objectFromJson.getOpEntrustWay());
            serverExtendInfo.setSzServerName(objectFromJson.getSzServerName());
            serverExtendInfo.setSzReceiveQName(objectFromJson.getSzReceiveQName());
            serverExtendInfo.setSzSendQName(objectFromJson.getSzSendQName());
            serverExtendInfo.setnProtocal(objectFromJson.getnProtocal());
            serverExtendInfo.setOppositeLiaisonTel(objectFromJson.getOppositeLiaisonTel());
            serverExtendInfo.setLiaisonTel(objectFromJson.getLiaisonTel());
            serverExtendInfo.setBrockerId(objectFromJson.getBrokerId());
            serverExtendInfo.setAuthCode(objectFromJson.getAuthCode());
            serverExtendInfo.setAppId(objectFromJson.getAppId());
            serverExtendInfo.setUserProductInfo(objectFromJson.getUserProductInfo());

            serverExtendInfo.setUfxSubClientName(objectFromJson.getUfxSubClientName());
            serverExtendInfo.setUfxBrokerType(objectFromJson.getUfxBrokerType());
            serverExtendInfo.setUfxTradeClientName(objectFromJson.getUfxTradeClientName());
            serverExtendInfo.setTradeTopicName(objectFromJson.getTradeTopicName());
            serverExtendInfo.setEntrustTopicName(objectFromJson.getEntrustTopicName());
            serverExtendInfo.setIncomeTradeTopic(objectFromJson.getIncomeTradeTopic());
            serverExtendInfo.setDspUrl(objectFromJson.getDspUrl());
            return serverExtendInfo;
        } catch (Exception e) {
            logger.warn("createServerExtendInfo fail {}", e);
            return null;
        }
    }
}
