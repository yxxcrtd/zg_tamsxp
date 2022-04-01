package com.joyintech.tams.exchange.service.trade.server.agent;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.MoreObjects;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.common.db.domain.Account;
import com.joyintech.tams.exchange.common.utils.JsonUtils;
import com.joyintech.tams.exchange.service.trade.dao.domain.TerminalAgentInfo;
import com.joyintech.tams.exchange.service.trade.util.http.HttpClientUtil;
import com.joyintech.tams.exchange.service.trade.util.http.HttpParameter;
import com.joyintech.tams.exchange.service.trade.util.http.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lifangpeng
 * @date 2021/10/9
 */
@Component
public class TradeAgentHelper {
    private static final Logger logger = LoggerFactory.getLogger(TradeAgentHelper.class);

    @Autowired
    protected TradeAgentManager tradeAgentManager;
    @Autowired
    protected AccountManager accountManager;

    /**
     * 访问接口平台的地址
     */
    @Value("${agent.terminal.host}")
    private String host;
    @Value("${agent.terminal.url}")
    private String url;

    protected AtomicLong requestID = new AtomicLong(1);

    public long generateReqId() {
        return requestID.incrementAndGet();
    }

    private String getDspUrl(TerminalAgentInfo agentInfo) {
        String dspUrl1 = agentInfo.getDspUrl();
        if (StringUtils.isEmpty(dspUrl1)) {
            Account account = accountManager.getAccountById(agentInfo.getAccountId());
            dspUrl1 = account != null ? account.getDspUrl() : null;
        }
        return MoreObjects.firstNonNull(dspUrl1, host + url);
    }

    public synchronized ResultBean doRequest(TerminalAgentInfo agentInfo, Object message) {
        ResultBean resultBean;
        String accountId = agentInfo.getAccountId();
        boolean loginStatus = tradeAgentManager.getAgentLoginStatus(accountId);
        if (!loginStatus && agentInfo.getFunctionCode() != 4) {
            return new ResultBean(ErrorCode.ACCOUNT_NOT_LOGIN);
        }

        String dspUrl = getDspUrl(agentInfo);
        logger.info("访问接口平台dspUrl:[{}]", dspUrl);
        try {
            HttpResult result = doRequest(agentInfo.getGpCode(), agentInfo.getTriggerCode(), dspUrl, message);
            AgentResult agentResult = JsonUtils.getObjectFromJson(result.getResult(), AgentResult.class);
            JSONObject jsonObject = (JSONObject) agentResult.getData();
            if (null == jsonObject) {
                resultBean = new ResultBean();
                resultBean.setErrorCode(ErrorCode.TERMINAL_API_ERROR.value);
                resultBean.setErrorMsg(agentResult.getMessage());
            } else {
                String data = JsonUtils.getJsonFromObject(jsonObject.get("data"));
                resultBean = JsonUtils.getObjectFromJson(data, ResultBean.class);
            }

            int errorCode = resultBean.getErrorCode();
            if (errorCode == ErrorCode.ACCOUNT_NOT_LOGIN.value || StringUtils.contains(resultBean.getErrorMsg(), "证书已经过期")) {
                tradeAgentManager.addAgentLoginStatus(accountId, false);
                logger.error("{}终端登录状态异常：{}，{}", agentInfo.getAgentType(), accountId, JsonUtils.getJsonFromObject(resultBean));
            }
        } catch (Exception e) {
            logger.error("调用http接口异常：errorMsg={}", e.getMessage(), e);
            resultBean = new ResultBean(ErrorCode.TERMINAL_API_ERROR.value, e.getMessage());
        }

        return resultBean;
    }

    public HttpResult doRequest(String gpCode, String triggerCode, String dspUrl, Object message) throws Exception {
        HttpClientUtil clientUtil = HttpClientUtil.create();
        clientUtil.url(dspUrl);
        HttpParameter parameter = buildHttpParameter(gpCode, triggerCode, message);
        clientUtil.context(JsonUtils.getJsonFromObject(parameter));
        return clientUtil.send();
    }

    private HttpParameter buildHttpParameter(String gpCode, String triggerCode, Object message) {
        HttpParameter parameter = new HttpParameter();
        parameter.setParameter(message);
        parameter.setTrigger(triggerCode);
        parameter.setGpcode(gpCode);
        parameter.setPreseqno(String.valueOf(generateReqId()));
        parameter.setPredate(DateUtil.format(LocalDateTime.now(), "yyyy-mm-dd"));
        parameter.setAsync(false);
        return parameter;
    }
}
