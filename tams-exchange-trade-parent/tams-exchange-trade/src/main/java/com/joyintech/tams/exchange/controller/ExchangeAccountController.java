package com.joyintech.tams.exchange.controller;

import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.db.domain.AccountRiskParameter;
import com.joyintech.tams.exchange.service.trade.management.service.AccountManagement;
import com.joyintech.tams.exchange.service.trade.management.service.AccountRiskParameterManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/joyin")
public class ExchangeAccountController {

//    @Autowired
//    private ExchangeAccountService accountService;

    @Autowired
    private AccountManagement accountManagement;

    @Autowired
    private AccountRiskParameterManagement accountRiskParameterManagement;

    /**
     * 账号查询
     *
     * @param userId    用户Id
     * @param accountId 账户Id
     * @return ResultBean
     */
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public ResultBean queryAccount(@RequestParam(value = "user_id") String userId,
                                   @RequestParam(value = "account_id", required = false) String accountId) {
        String param = "?user_id=" + userId;
        if (Objects.nonNull(accountId)) param += "&account_id=" + accountId;
//        return accountService.queryAccount(param);
//////////////////////////兼容新UI后端
        return accountManagement.queryAccount(accountId);
    }

    /**
     * 账号连接
     *
     * @param userId
     * @param accountId
     * @param connectStatus
     * @return
     */
    @RequestMapping(value = "/account/connect", method = RequestMethod.PUT)
    public ResultBean connectAccount(@RequestParam(value = "user_id") String userId,
                                     @RequestParam(value = "account_id") String accountId,
                                     @RequestParam(value = "status") int connectStatus) {
//        String param = "?user_id="+userId+"&account_id="+accountId+"&status="+connectStatus;
//        return accountService.connectAccount(param);
//////////////////////////兼容新UI后端
        return accountManagement.connectAccount(accountId, connectStatus);
    }

    /**
     * 账号连接
     *
     * @param userId
     * @param accountId
     * @param compareType
     * @return
     */
    @RequestMapping(value = "/account/compare", method = RequestMethod.PUT)
    public ResultBean compareAccount(@RequestParam(value = "user_id") String userId,
                                     @RequestParam(value = "account_id") String accountId,
                                     @RequestParam(value = "type") int compareType) {
//        String param = "?user_id="+userId+"&account_id="+accountId+"&type="+compareType;
//        return accountService.compareAccount(param);
//////////////////////////兼容新UI后端
        return accountManagement.compareAccount(accountId, compareType);
    }

    /**
     * 从缓存中查询账户资金
     *
     * @param userId
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/account/fund", method = RequestMethod.GET)
    public ResultBean queryAccountFund(@RequestParam(value = "user_id") String userId,
                                       @RequestParam(value = "account_id", required = false) String accountId) {
//        String param = "?user_id=" + userId;
//        if (Objects.nonNull(accountId)) param += "&account_id=" + accountId;
//        return accountService.queryAccountFund(param);
//////////////////////////兼容新UI后端
        return accountManagement.queryAccountFund(accountId);
    }


    /**
     * 查询资金账户下深证或上海可用标准券总数
     *
     * @param accountId 账户id
     * @param market    市场
     * @return ResultBean
     */
    @RequestMapping(value = "/account/standard/bond/available", method = RequestMethod.GET)
    public ResultBean queryAccountStandardBonds(@RequestParam(value = "account_id") String accountId,
                                                @RequestParam(value = "market", required = false) String market) {
//        String param = "?account_id=" + accountId;
//        if (Objects.nonNull(market)) param += "&market=" + market;
//        return accountService.queryAccountAvailableStandardBonds(param);
//////////////////////////兼容新UI后端
        return accountManagement.queryAccountAvailableStandardBonds(accountId, market);
    }

    /**
     *  ---------账号风控管理接口----------
     */

    /**
     * 查询账号风控参数
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/account/risk", method = RequestMethod.GET)
    public ResultBean queryAccountRiskParam(@RequestParam(value = "account_id", required = false) String accountId) {
        String param = null;
        if (Objects.nonNull(accountId)) param = "?account_id=" + accountId;
//        return accountService.queryAccountRiskParam(param);
//////////////////////////兼容新UI后端
        return accountRiskParameterManagement.queryAccountRiskParam(accountId);
    }

    /**
     * 添加账号风控参数
     *
     * @param userId
     * @param accountRiskParameter
     * @return
     */
    @RequestMapping(value = "/account/risk/add", method = RequestMethod.POST)
    public ResultBean addAccountRiskParam(@RequestParam(value = "user_id") String userId,
                                          @RequestBody AccountRiskParameter accountRiskParameter) {
        String param = "?user_id=" + userId;
        HttpEntity<AccountRiskParameter> request = new HttpEntity<>(accountRiskParameter);
//        return accountService.addAccountRiskParam(param, request);
//////////////////////////兼容新UI后端
        return accountRiskParameterManagement.addAccountRiskParam(accountRiskParameter);
    }

    /**
     * 修改账号风控参数
     *
     * @param userId
     * @param accountRiskParameter
     * @return
     */
    @RequestMapping(value = "/account/risk/update", method = RequestMethod.PUT)
    public ResultBean updateAccountRiskParam(@RequestParam(value = "user_id") String userId,
                                             @RequestBody AccountRiskParameter accountRiskParameter) {
        String param = "?user_id=" + userId;
        HttpEntity<AccountRiskParameter> request = new HttpEntity<>(accountRiskParameter);
//        return accountService.updateAccountRiskParam(param, request);
//////////////////////////兼容新UI后端
        return accountRiskParameterManagement.updateAccountRiskParam(accountRiskParameter);
    }

    /**
     * 删除账号风控参数
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/account/risk/delete", method = RequestMethod.DELETE)
    public ResultBean deleteAccountRiskParam(@RequestParam(value = "account_id") String accountId) {
        String param = null;
        if (Objects.nonNull(accountId)) param = "?account_id=" + accountId;
//        return accountService.deleteAccountRiskParam(param);
//////////////////////////兼容新UI后端
        return accountRiskParameterManagement.deleteAccountRiskParam(accountId);
    }
}
