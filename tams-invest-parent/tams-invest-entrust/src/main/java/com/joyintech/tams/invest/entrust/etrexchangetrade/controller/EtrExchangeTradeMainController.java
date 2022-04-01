/*
 * 文件名：EtrTradePlace001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月1日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.entrust.etrexchangetrade.controller;

import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.entrust.etrexchangetrade.dto.EtrExchangeTrade001Dto;
import com.joyintech.tams.invest.entrust.etrexchangetrade.service.EtrExchangeTrade001Service;
import com.joyintech.workflow.engine.util.WorkFlowUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;

/**
 * 交易所指令交易（债券股票回购基金场内指令交易）
 * @author chendijun
 * @version 1.0 2020年7月1日
 * @see EtrExchangeTradeMainController
 * @since 1.0
 */
@Controller
@PageId("EtrExchangeTradeMain")
@RequestMapping("/invest/entrust/etrexchangetrade/EtrExchangeTradeMain")
public class EtrExchangeTradeMainController extends BaseController{

    /**
     * 存在自定义流程节点处理service时，
     * 指令提交起流程会根据pageId获取controller里的BusinessService,勿删
     */
    @Autowired
    private EtrExchangeTrade001Service etrExchangeTrade001Service;

    @Override
    public String init(Model model) {
        model.addAttribute("ENTRUST_STATUS_05", DictCodeList.委托状态_已退回.getValue());
        model.addAttribute("ENTRUST_STATUS_07", DictCodeList.委托状态_已撤回.getValue());
        
        //流程id
        String workflowId = getParam(GlobalConstant.WORKFLOW_ID);
        model.addAttribute("workflowId", workflowId);
        String entrustId = getParam("entrustId");

        //场内交易指令是否开启多组合指令。01:是,02:否
        String isNeedMutiPtl = CacheUtil.getSysParam(BussinessConstant.IS_NEED_EXCHANGE_MULTI_PTL);
        
        if (!WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(workflowId)) {
       	 //流程代办
           model.addAttribute("isFromToDoList", !WorkFlowUtil.isNotFromToDoList());
       }
        model.addAttribute("FINPROD_TYPE_TG_F01", DictCodeListExt1.金融产品类型_投管用_债券.getValue());
        model.addAttribute("FINPROD_TYPE_TG_F02", DictCodeListExt1.金融产品类型_投管用_债券质押式正回购.getValue());
        model.addAttribute("FINPROD_TYPE_TG_F03", DictCodeListExt1.金融产品类型_投管用_债券质押式逆回购.getValue());
        model.addAttribute("FINPROD_TYPE_TG_F06", DictCodeListExt1.金融产品类型_投管用_基金.getValue());
        model.addAttribute("FINPROD_TYPE_TG_F10", DictCodeListExt1.金融产品类型_投管用_股票.getValue());
        model.addAttribute("FINPROD_TYPE_TG_ZYSHG", "ZYSHG");// 质押式回购

        // 交易平台
        model.addAttribute("SHARES_TRADE_PLATFORM_02", DictCodeList.交易平台_竞价撮合平台.getValue());
        model.addAttribute("SHARES_TRADE_PLATFORM_03", DictCodeList.交易平台_固定收益平台.getValue());
        model.addAttribute("SHARES_TRADE_PLATFORM_04", DictCodeList.交易平台_大宗交易平台.getValue());
        model.addAttribute("SHARES_TRADE_PLATFORM_05", DictCodeList.交易平台_综合协议交易平台.getValue());
        model.addAttribute("entrustId", entrustId);

        model.addAttribute("isNeedMutiPtl", isNeedMutiPtl);

        if (StringUtil.isNotEmpty(workflowId)) {
            //流程代办checkImportData
            EtrExchangeTrade001Dto entity = etrExchangeTrade001Service.getBussinessEntityByWorkflowId(workflowId);
            if(StringUtil.isNotEmpty(entity)) {
                model.addAttribute("isMulti", StringUtil.isNotEmpty(entity.getIsMulti())?entity.getIsMulti():DictCodeList.是否_否.getValue());
            }
        }
        return super.init(model);
    }
}
