/*
 * 文件名：EtrTradePlace001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月1日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.entrust.etragrereptrade.controller;

import java.util.Map;

import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.entrust.etragrereptrade.dto.EtrAgreRepTrade002Dto;
import com.joyintech.tams.invest.entrust.etragrereptrade.dto.OnWayRep002Dto;
import com.joyintech.tams.invest.entrust.etragrereptrade.dto.UnOpenQuota002Dto;
import com.joyintech.tams.invest.entrust.etragrereptrade.service.EtrAgreRepTrade002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.invest.common.constant.BussinessDictCode;
import com.joyintech.tams.invest.entrust.etrexchangetrade.dto.EtrExchangeTrade002Dto;
import com.joyintech.tams.invest.entrust.etrexchangetrade.service.EtrExchangeTrade002Service;

/**
 * 交易所指令交易（债券股票回购基金场内指令交易）
 * @author chendijun
 * @version 1.0 2020年7月1日
 * @see EtrAgreRepTrade002Controller
 * @since 1.0
 */
@Controller
@PageId("EtrAgreRepTrade002")
@RequestMapping("/invest/entrust/etragrereptrade/EtrAgreRepTrade002")
public class EtrAgreRepTrade002Controller extends BaseController{

    /**
     * 可转债委托处理详细
     */
    @Autowired
    private EtrExchangeTrade002Service etrExchangeTrade002Service;

    @Autowired
    private EtrAgreRepTrade002Service etrAgreRepTrade002Service;


    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model,BussinessDictCode.交易场所);
        EtrExchangeTrade002Dto exchangeTradeDto002 = new EtrExchangeTrade002Dto();
        model.addAttribute("entity",exchangeTradeDto002);
        model.addAttribute("FINPROD_TYPE_TG_F01", DictCodeListExt1.金融产品类型_投管用_债券.getValue());
        model.addAttribute("DECLA_TYPE_PRM05", DictCodeListExt3.申报类型_不指定.getValue());
        model.addAttribute("DECLA_TYPE_PRM14", DictCodeListExt3.申报类型_指定对手方成交申报.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_15", DictCodeListExt3.上农商_指令业务类型_债券质押式回购竞价指令.getValue());
        model.addAttribute("PS_T02", DictCodeList.交易方向_卖出.getValue());
        model.addAttribute("PS_T69", DictCodeListExt3.交易方向_质押券出库.getValue());
        model.addAttribute("TARGET_CLASS_01", DictCodeListExt3.目标类型_数量.getValue());
        model.addAttribute("PRICE_MODEL_PRM01", DictCodeListExt2.价格模式_指定价格.getValue());
        model.addAttribute("UNOPENQUOTA_REFRESH_PERIOD", CacheUtil.getSysParam(BussinessConstant.UNOPENQUOTA_REFRESH_PERIOD));
        model.addAttribute("POSTABLE_REFRESH_PERIOD", CacheUtil.getSysParam(BussinessConstant.POSTABLE_REFRESH_PERIOD));
        model.addAttribute("ONWAYREP_REFRESH_PERIOD", CacheUtil.getSysParam(BussinessConstant.ONWAYREP_REFRESH_PERIOD));
        model.addAttribute("BACK_LEND_DIRECTION_04", DictCodeList.回购拆借方向_逆回购.getValue());
        model.addAttribute("TRADE_MARKET_03", DictCodeList.交易流通场所_上海证券交易所.getValue());
        return super.init(model);
    }
    
    /**
     * 查询持仓
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPositionsPage")
    public Page<EtrAgreRepTrade002Dto> findPositionsPage(){
        try {
            return etrAgreRepTrade002Service.findPositionsPage(RequestUtil.getPageParam());
        }
        catch (Exception e) {
            return new Page<>();
        }
    }

    /**
     * 查询债券质押持仓
     *
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findZqzyPositionsPage")
    public Page<EtrExchangeTrade002Dto> findZqzyPositionsPage(){
        return etrExchangeTrade002Service.findZqzyPositionsPage(RequestUtil.getPageParam());
    }

    /**
     * 查询证券代码联想框文本框
     *
     * @param params 画面参数
     * @return 证券代码联想框文本框
     * @see
     * @since 1.0
     */
    @RequestMapping("getSecAcctId")
    @ResponseBody
    public Page<SelectOptionDto> getSecAcctId(@RequestParam Map<String, Object> params) {
        return etrExchangeTrade002Service.getSecAcctId(params);
    }

    /**
     * 查询上交所和深交所标准券数量
     *
     * @return 上交所和深交所标准券数量
     * @see
     * @since 1.0
     */
    @RequestMapping("loadSecNum")
    @ResponseBody
    public Map<String, String> loadSecNum(Map<String, Object> params) {
        return etrExchangeTrade002Service.loadSecNum(RequestUtil.getPageParam());
    }
    /**
     * 查询在途回购
     *
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findOnWayRepPage")
    public Page<OnWayRep002Dto> findOnWayRepPage(){
        try {
            return etrAgreRepTrade002Service.findOnWayRepPage(RequestUtil.getPageParam());
        }
        catch (Exception e) {
            return new Page<>();
        }
    }

    /**
     * 查询非公开行情列表
     * @return
     */
    @ResponseBody
    @RequestMapping("findUnOpenQuotaPage")
    public Page<UnOpenQuota002Dto> findUnOpenQuotaPage(){
//        try {
        return etrAgreRepTrade002Service.findUnOpenQuotaPage(RequestUtil.getPageParam());
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return new Page<>();
//        }
    }
}
