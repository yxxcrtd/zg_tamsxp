package com.joyintech.tams.exchange.service.trade.server.agent;

import com.joyintech.tams.exchange.common.constant.AccountLoginStatus;
import com.joyintech.tams.exchange.common.db.domain.Account;

import java.util.Map;

public interface TradeAgent extends IAgentCommand {
    Map<String, Account> getAccountMap();

    String getTerminalName();

    default void setStatus(String accountId, AccountLoginStatus status) {

    }

    /**
     * 删除账号
     *
     * @param accountId 账号id
     */
    default void removeAccount(String accountId) {

    }

    /**
     * 报价查询、成交查询
     */
    default void queryNextTrade() {

    }
}
