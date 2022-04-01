package com.joyintech.tams.exchange.service.trade.controller;

import com.joyintech.tams.exchange.common.ResponseResultBody;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.bean.investment.CapitalAccountBean;
import com.joyintech.tams.exchange.common.db.domain.AccountRiskParameter;
import com.joyintech.tams.exchange.common.domain.Message;
import com.joyintech.tams.exchange.common.request.*;
import com.joyintech.tams.exchange.service.trade.management.service.AccountManagement;
import com.joyintech.tams.exchange.service.trade.management.service.AccountRiskParameterManagement;
import com.joyintech.tams.exchange.util.MasterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trade/joyin")
@ResponseResultBody
@Api(tags = "场内交易-账号管理操作接口")
public class AccountController {
    @Autowired
    private AccountManagement accountManagement;

    @Autowired
    private AccountRiskParameterManagement accountRiskParameterManagement;

    /**
     * 账号管理：查询所有账号
     */
    @ApiOperation(value = "账号查询", notes = "账号查询")
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public Message<List<CapitalAccountBean>> queryAccount(@RequestBody QueryAccountRequest queryAccountRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(queryAccountRequest);
    	}
        return new Message<List<CapitalAccountBean>>(accountManagement.queryAccount(queryAccountRequest.getAccountId()));
    }

    /**
     * 账号连接
     */
    @ApiOperation(value = "账号连接", notes = "账号连接")
    @RequestMapping(value = "/account/connect", method = RequestMethod.POST)
    public Message<Object> connectAccount(@RequestBody ConnectAccountRequest connectAccountRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(connectAccountRequest);
    	}
        return new Message<Object>(accountManagement.connectAccount(connectAccountRequest.getAccountId(), connectAccountRequest.getConnectStatus()));
    }

    /**
     * 账号下委托和成交数据处理（处理类型（0：对比数据，1：全量覆盖，2：委托覆盖，3：成交覆盖）
     */
    @ApiOperation(value = "账号数据比对", notes = "账号下委托和成交数据处理（处理类型（0：对比数据，1：全量覆盖，2：委托覆盖，3：成交覆盖）")
    @RequestMapping(value = "/account/compare", method = RequestMethod.POST)
    public Message<Object> compareAccount(@RequestBody CompareAccountRequest compareAccountRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(compareAccountRequest);
    	}
        return new Message<Object>(accountManagement.compareAccount(compareAccountRequest.getAccountId(), compareAccountRequest.getCompareType()));
    }

    /**
     * 查询账户资金
     */
    @ApiOperation(value = "查询账户资金", notes = "查询账户资金")
    @RequestMapping(value = "/account/fund", method = RequestMethod.POST)
    public Message<Object> queryAccountFund(@RequestBody QueryAccountRequest queryAccountRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(queryAccountRequest);
    	}
        return new Message<Object>(accountManagement.queryAccountFund(queryAccountRequest.getAccountId()));
    }

////////////////////////////////////////////////////暂未发现使用
//    @RequestMapping(value = "/account/position", method = RequestMethod.GET)
//    public ResultBean queryAccountPosition(@RequestParam(value = "account_id") String accountId,
//                                           @RequestParam(value = "stock_code", required = false) String stockCode,
//                                           @RequestParam(value = "page_size", required = false, defaultValue = "0") int pageSize,
//                                           @RequestParam(value = "position_str", required = false, defaultValue = "") String positionStr) {
//        return accountManagement.queryAccountPosition(accountId, stockCode, pageSize, positionStr);
//    }

////////////////////////////////////////////////////暂未发现使用
//    /**
//     * 查询质押券折算率
//     *
//     * @param accountId 账户编号
//     * @param market    市场
//     * @param stockCode 合约代码
//     * @return ResultBean
//     */
//    @RequestMapping(value = "/account/discount", method = RequestMethod.GET)
//    public ResultBean queryAccountDiscount(@RequestParam(value = "account_id") String accountId,
//                                           @RequestParam(value = "market", required = false) String market,
//                                           @RequestParam(value = "stock_code", required = false) String stockCode) {
//        return accountManagement.queryAccountDiscount(accountId, market, stockCode);
//    }

////////////////////////////////////////////////////暂未发现使用
//    /**
//     * 资金账户下标准券明细查询
//     *
//     * @param accountId 账户id
//     * @param market    市场
//     * @param stockCode 合约代码
//     * @return ResultBean
//     */
//    @RequestMapping(value = "/account/standard/bonds", method = RequestMethod.GET)
//    public ResultBean queryAccountStandardBonds(@RequestParam(value = "account_id") String accountId,
//                                                @RequestParam(value = "market", required = false) String market,
//                                                @RequestParam(value = "stock_code", required = false) String stockCode) {
//        return accountManagement.queryAccountStandardBonds(accountId, market, stockCode);
//    }

    /**
     * 查询资金账户下深证或上海可用标准券总数
     */
    @ApiOperation(value = "查询资金账户下深证或上海可用标准券总数", notes = "查询资金账户下深证或上海可用标准券总数")
    @RequestMapping(value = "/account/standard/bond/available", method = RequestMethod.POST)
    public Message<Object> queryAccountStandardBonds(@RequestBody QueryAccountStandardBondsRequest queryAccountStandardBondsRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(queryAccountStandardBondsRequest);
    	}
        return new Message<Object>(accountManagement.queryAccountAvailableStandardBonds(queryAccountStandardBondsRequest.getAccountId(), queryAccountStandardBondsRequest.getMarket()));
    }

    /**
     * 账号管理：查询账号风控参数
     */
    @ApiOperation(value = "查询账号风控参数", notes = "查询账号风控参数")
    @RequestMapping(value = "/account/risk", method = RequestMethod.GET)
    public Message<Object> queryAccountRiskParam(@RequestBody QueryAccountRequest queryAccountRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(queryAccountRequest);
    	}
        return new Message<Object>(accountRiskParameterManagement.queryAccountRiskParam(queryAccountRequest.getAccountId()));
    }

    /**
     * 账号管理：添加账号风控参数
     */
    @ApiOperation(value = "添加账号风控参数", notes = "添加账号风控参数")
    @RequestMapping(value = "/account/risk/add", method = RequestMethod.POST)
    public Message<Object> addAccountRiskParam(@RequestBody AccountRiskParameter accountRiskParameter) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(accountRiskParameter);
    	}
        return new Message<Object>(accountRiskParameterManagement.addAccountRiskParam(accountRiskParameter));
    }

    /**
     * 账号管理：修改账号风控参数
     */
    @ApiOperation(value = "修改账号风控参数", notes = "修改账号风控参数")
    @RequestMapping(value = "/account/risk/update", method = RequestMethod.POST)
    public Message<Object> updateAccountRiskParam(@RequestBody AccountRiskParameter accountRiskParameter) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(accountRiskParameter);
    	}
        return new Message<Object>(accountRiskParameterManagement.updateAccountRiskParam(accountRiskParameter));
    }

    /**
     * 账号管理：删除账号风控参数
     */
    @ApiOperation(value = "删除账号风控参数", notes = "删除账号风控参数")
    @RequestMapping(value = "/account/risk/delete", method = RequestMethod.POST)
    public Message<Object> deleteAccountRiskParam(DeleteAccountRiskParamRequest deleteAccountRiskParamRequest) {
    	if(!MasterUtil.isMaster()) {
    		return MasterUtil.postForObject(deleteAccountRiskParamRequest);
    	}
        return new Message<Object>(accountRiskParameterManagement.deleteAccountRiskParam(deleteAccountRiskParamRequest.getAccountId()));
    }


////////////////////////////////////////////////////暂未发现使用    
//    @RequestMapping(value = "/account/futuresPosition", method = RequestMethod.GET)
//    public ResultBean queryAccountFuturesPosition(@RequestParam(value = "account_id") String accountId,
//                                                  @RequestParam(value = "stock_code", required = false) String stockCode,
//                                                  @RequestParam(value = "page_size", required = false, defaultValue = "0") int pageSize,
//                                                  @RequestParam(value = "position_str", required = false, defaultValue = "") String positionStr) {
//        return accountManagement.queryAccountFuturesPosition(accountId, stockCode, pageSize, positionStr);
//    }


////////////////////////////////////////////////////暂未发现使用    
//    @RequestMapping(value = "/account/futuresPositionDetail", method = RequestMethod.GET)
//    public ResultBean queryAccountFuturesPositionDetail(@RequestParam(value = "account_id") String accountId,
//                                                        @RequestParam(value = "stock_code", required = false) String stockCode,
//                                                        @RequestParam(value = "page_size", required = false, defaultValue = "0") int pageSize,
//                                                        @RequestParam(value = "position_str", required = false, defaultValue = "") String positionStr) {
//        return accountManagement.queryAccountFuturesPositionDetail(accountId, stockCode, pageSize, positionStr);
//    }
}
