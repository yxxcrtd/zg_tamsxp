package com.joyintech.tams.invest.common.dto;

import com.joyintech.tams.common.base.entity.PtlSecPositionFlow;

import java.util.List;

/**
 * @program: microservice
 * @description: 组合持仓流水DTO
 * @author: 强成西
 * @create: 2018-09-29 15:48
 **/
public class PtlSecPositionFlowDto extends PtlSecPositionFlow {

    /**
     * 前置交易编号
     */
    private String bTradeId;

    /**
     * 账户类型
     */
    private List<String> acctType;

    /**
     * 是否添加账户类型条件
     */
    private boolean needAcctType;

    /**
     * 是否是委托
     */
    private boolean entrust;

    /**
     * 是否是通道
     */
    private boolean channel;
    /**
     * 交易方向
     */
    private String ps;
    
    /**
     * 非此投资目的字段
     */
    private String notInvAim;
    /**
     *数据已入新头寸，Y：已入
     */
    private String isExistNew;


    /**
     * 获取 channel
     *
     * @return channel
     */
    public boolean isChannel() {
        return channel;
    }

    /**
     * 设置 channel
     *
     * @param channel channel
     */
    public void setChannel(boolean channel) {
        this.channel = channel;
    }

    /**
     * 获取 bTradeId
     *
     * @return bTradeId
     */
    public String getbTradeId() {
        return bTradeId;
    }

    /**
     * 设置 bTradeId
     *
     * @param bTradeId bTradeId
     */
    public void setbTradeId(String bTradeId) {
        this.bTradeId = bTradeId;
    }

    /**
     * 获取 acctType
     *
     * @return acctType
     */
    public List<String> getAcctType() {
        return acctType;
    }

    /**
     * 设置 acctType
     *
     * @param acctType acctType
     */
    public void setAcctType(List<String> acctType) {
        this.acctType = acctType;
    }

    /**
     * 获取 needAcctType
     *
     * @return needAcctType
     */
    public boolean isNeedAcctType() {
        return needAcctType;
    }

    /**
     * 设置 needAcctType
     *
     * @param needAcctType needAcctType
     */
    public void setNeedAcctType(boolean needAcctType) {
        this.needAcctType = needAcctType;
    }

    /**
     * 获取 entrust
     *
     * @return entrust
     */
    public boolean isEntrust() {
        return entrust;
    }

    /**
     * 设置 entrust
     *
     * @param entrust entrust
     */
    public void setEntrust(boolean entrust) {
        this.entrust = entrust;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

	public String getNotInvAim() {
		return notInvAim;
	}

	public void setNotInvAim(String notInvAim) {
		this.notInvAim = notInvAim;
	}

    public String getIsExistNew() {
        return isExistNew;
    }

    public void setIsExistNew(String isExistNew) {
        this.isExistNew = isExistNew;
    }
}
