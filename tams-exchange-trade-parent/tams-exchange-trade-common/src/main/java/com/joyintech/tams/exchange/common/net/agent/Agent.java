package com.joyintech.tams.exchange.common.net.agent;


import com.google.common.collect.Maps;
import com.joyintech.tams.exchange.common.net.protocol.FunctionCode;
import com.joyintech.tams.exchange.common.net.protocol.ProtocolMessage;
import com.joyintech.tams.exchange.common.net.protocol.ProtocolMessageFactory;
import com.joyintech.tams.exchange.common.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Agent {

    private static final Logger logger = LoggerFactory.getLogger(Agent.class);

    protected Channel channel = null;
    protected AtomicLong requestID = new AtomicLong(1);
    protected Map<Long, ProtocolMessage> response = Maps.newConcurrentMap();
    protected Map<Long, ProtocolMessage> syncRequestInfo = Maps.newConcurrentMap();

    protected volatile AgentStatus agentStatus = AgentStatus.DEFAULT;

    public void setAgentStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
    }

    public long generateReqId() {
        long requestId = requestID.incrementAndGet();
        if (requestId < 1000000000) {
            return requestId;
        }
        return requestID.getAndSet(1);
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public AgentStatus getAgentStatus() {
        return agentStatus;
    }

    public String remoteAddress() {
        return this.channel.remoteAddress().toString();
    }


    private void syncWrite(ProtocolMessage message, long timeout) throws InterruptedException {
        syncRequestInfo.put(message.getReqId(), message);
        channel.writeAndFlush(message);
        synchronized (message) {
            message.wait(timeout);
        }
    }

    public Object syncWrite(FunctionCode functionCode, Object object, int dateType, long timeout) throws Exception {
        long requestId = generateReqId();
        ProtocolMessage msg = ProtocolMessageFactory.newMessage(functionCode.value, requestId, dateType, object);
        try {
            syncWrite(msg, timeout);
            ProtocolMessage rp = response.remove(requestId);
            if (Objects.isNull(rp)) throw new TimeoutException("time out");
            return getResponseObjectFromMessage(rp);
        } finally {
            response.remove(requestId);
        }
    }

    /**
     * 交易端同步
     */
    public Object syncWrite(FunctionCode functionCode, Object object) throws Exception {
        return syncWrite(functionCode, object, 0, 10000);
    }

    public Object syncWrite(FunctionCode functionCode, int dataType, Object object) throws Exception {
        return syncWrite(functionCode, object, dataType, 10000);
    }

    public ChannelFuture asyncWrite(FunctionCode functionCode, long requestId, int dateType, Object object) {
        ChannelFuture channelFuture = channel.writeAndFlush(ProtocolMessageFactory.newMessage(functionCode.value, requestId, dateType, object));
        if (functionCode != FunctionCode.HEARTBEAT && functionCode != FunctionCode.ACK) {
            logger.debug("asyncWrite: functionCode:{} reqId:{} data:{}", functionCode, requestId, JsonUtils.getJsonFromObject(object));
        }
        return channelFuture;
    }

    public void asyncWrite(FunctionCode functionCode, long requestId, int dateType, byte[] object) {
        channel.writeAndFlush(ProtocolMessageFactory.newMessage(functionCode.value, requestId, dateType, object));
        if (functionCode != FunctionCode.HEARTBEAT && functionCode != FunctionCode.ACK) {
            logger.debug("asyncWrite: functionCode:{} reqId:{}", functionCode, requestId);
        }
    }

    public void asyncWrite(FunctionCode functionCode, long requestId, int dateType, String object) {
        channel.writeAndFlush(ProtocolMessageFactory.newMessage(functionCode.value, requestId, dateType, object));
        if (functionCode != FunctionCode.HEARTBEAT && functionCode != FunctionCode.ACK) {
            logger.debug("asyncWrite: functionCode:{} reqId:{} data:{}", functionCode, requestId, object);
        }
    }

    public ChannelFuture asyncWrite(FunctionCode functionCode, Object object) {
        return asyncWrite(functionCode, generateReqId(), 0, object);
    }

    /**
     * 终端异步请求
     */
    public ChannelFuture asyncWrite(FunctionCode functionCode, long requestId, Object object) {
        return asyncWrite(functionCode, requestId, 0, object);
    }

    public void asyncWrite(ProtocolMessage message) {
        channel.writeAndFlush(message);
        logger.debug("asyncWrite: functionCode:{} reqId:{} data:{}", message.getFc(), message.getReqId(), JsonUtils.getJsonFromObject(message.getBody()));
    }

    public void handleMessage(ProtocolMessage message) {
        try {
            if (message.getFc() != FunctionCode.HEARTBEAT.value && message.getFc() != FunctionCode.ACK.value) {
                logger.info("Received message: functionCode={}，requestId={}，message={}", message.getFc(), message.getReqId(), message.getBody());
            }
            //如果是需要同步处理的消息
            if (syncRequestInfo.containsKey(message.getReqId())) {
                logger.info("同步消息:functionCode={}，requestId={}，message={}", message.getFc(), message.getReqId(), message.getBody());
                ProtocolMessage req = syncRequestInfo.remove(message.getReqId());
                response.put(message.getReqId(), message);
                synchronized (req) {
                    req.notify();
                }
            } else {
                Object response = getResponseObjectFromMessage(message);
                logger.info("异步消息:functionCode={}，requestId={}，message={}", message.getFc(), message.getReqId(), JsonUtils.getJsonFromObject(response));
                handleAsyncMessage(message.getReqId(), message.getFc(), response);
            }
        } catch (Exception e) {
            logger.warn("handle message exception:", e);
        }
    }

    public abstract void connEstablished(Channel channel);

    public abstract void connClosed();

    protected abstract void handleAsyncMessage(long requestId, int functionCode, Object message);

    protected abstract Object getResponseObjectFromMessage(ProtocolMessage message) throws Exception;
}
