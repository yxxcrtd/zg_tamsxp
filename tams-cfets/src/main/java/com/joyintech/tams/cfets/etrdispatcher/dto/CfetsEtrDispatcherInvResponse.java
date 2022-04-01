package com.joyintech.tams.cfets.etrdispatcher.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 场外指令分发
 * 委托投资人员信息返回数据
 *
 * @author chenkang
 * @version 1.0 2022年02月22日
 * @see CfetsEtrDispatcherInvResponse
 * @since 1.0
 */
@ApiModel(value = "委托投资人员信息返回数据", description = "委托投资人员信息数据")
public class CfetsEtrDispatcherInvResponse implements Serializable {


    private static final long serialVersionUID = -4431008942213774492L;

    @ApiModelProperty(name = "invManagerId", value = "投资经理", example = "字符串1")
    private String invManagerId;

    @ApiModelProperty(name = "invManagerName", value = "投资经理名称", example = "字符串1")
    private String invManagerName;

    @ApiModelProperty(name = "belongsDept", value = "所属部门", example = "字符串1")
    private String belongsDept;

    @ApiModelProperty(name = "trader", value = "本方交易员", example = "字符串1")
    private String trader;

    @ApiModelProperty(name = "counterId", value = "交易对手", example = "字符串1")
    private String counterId;

    @ApiModelProperty(name = "counterName", value = "交易对手名称", example = "字符串1")
    private String counterName;

    @ApiModelProperty(name = "counterTrader", value = "对方交易员", example = "字符串1")
    private String counterTrader;

    @ApiModelProperty(name = "counterTraderName", value = "对方交易员名称", example = "字符串1")
    private String counterTraderName;

    @ApiModelProperty(name = "contactInfo", value = "联系方式", example = "手机号/固话")
    private String contactInfo;


    public String getInvManagerId() {
        return invManagerId;
    }

    public void setInvManagerId(String invManagerId) {
        this.invManagerId = invManagerId;
    }

    public String getBelongsDept() {
        return belongsDept;
    }

    public void setBelongsDept(String belongsDept) {
        this.belongsDept = belongsDept;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getCounterId() {
        return counterId;
    }

    public void setCounterId(String counterId) {
        this.counterId = counterId;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getCounterTrader() {
        return counterTrader;
    }

    public void setCounterTrader(String counterTrader) {
        this.counterTrader = counterTrader;
    }

    public String getCounterTraderName() {
        return counterTraderName;
    }

    public void setCounterTraderName(String counterTraderName) {
        this.counterTraderName = counterTraderName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getInvManagerName() {
        return invManagerName;
    }

    public void setInvManagerName(String invManagerName) {
        this.invManagerName = invManagerName;
    }
}
