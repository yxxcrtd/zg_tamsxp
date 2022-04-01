package com.joyintech.tams.exchange.service.trade.server.agent;

import com.google.common.collect.Maps;
import com.joyintech.tams.exchange.common.db.domain.Account;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: think
 * @Description: 管理已登录的账户
 * @Date: Created in 13:53 2020/11/19
 */
@Component
public class AccountManager {
    //记录已经登录成功的所有账户信息
    private Map<String, Account> accountMaps = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        accountMaps.clear();
    }

    public void addAccounts(Account account) {
        accountMaps.putIfAbsent(account.getId(), account);
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accountMaps.values());
    }

    public Map<String, Account> getBrokerAccountMap() {
        return getAccounts().stream().collect(Collectors.toMap(Account::getBrokerId, account -> account, (oldValue, newValue) -> oldValue));
    }

    public Set<String> getAccountIds() {
        return accountMaps.keySet();
    }

    public Account getAccountById(String accountId) {
        if(accountId != null){
            return accountMaps.get(accountId);
        }else{
            return null;
        }
    }

    public void clearAccountMaps(Collection<String> accountIds) {
        accountIds.forEach(accountId -> accountMaps.remove(accountId));
    }
}
