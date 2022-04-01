package com.joyintech.tams.exchange.service.trade.processor.msg;

/**
 * Created by yking on 2017/12/8 0008. God bless you!
 */
public class MsgType {
    public final static int REQ_ENTRUST = 1;  //普通下单
    public final static int RESP_ENTRUST = 2; //普通下单响应
    public final static int NOTIFY_ENTRUST_ERROR = 3; //下单异常通知
    public final static int NOTIFY_ENTRUST = 4;     //普通下单结果通知
    public final static int NOTIFY_INTEGRATED = 5;  //综合下单结果通知
    public final static int NOTIFY_TRADE_RECORD = 6; //成交通知
    public final static int REQ_ENTRUST_CANCEL = 7;  //撤单
    public final static int REQ_ORDER_BATCH_CANCEL = 8;  //批量撤单
    public final static int RESP_CANCEL = 9;       //撤单响应

    public final static int REQ_INTEGRATED = 10;       //综合下单
    public final static int RESP_INTEGRATED = 11;       //综合下单响应

    public final static int EXAMINE_ORDER = 12;       //订单审核
    public final static int EXAMINE_ENTRUST = 13;       //委托审核

    public final static int MQ_INSTRUCTION = 14;       //推送指令

    public final static int REQ_BATCH_ORDER = 15;       //批量委托
    public final static int BATCH_ORDER_JOB = 16;       //批量委托
    public final static int FROZEN_PENDING_ENTRUST = 17;       //待审批委托冻结处理
    public final static int FROZEN_PENDING_INTEGRATED = 18;       //待审批综合委托冻结处理
    public final static int EXAMINE_SUCCESS = 19;       //审批通过返回

    public final static int EXAMINE_REVIEW = 20;       //复核审批
    public final static int EXAMINE_REVOKE = 22;        //撤销审批
    public final static int UNFROZEN_INNER = 23;       //撤审内部撤销

    public final static int REQ_CLOSE_ENTRUST = 24;     //强制关闭委托

}
