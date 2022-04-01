/*
 * 文件名：TrdCommandsCollection001Controller.java
 * 版权：Copyright 2018-2019 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.1.2
 * 修改人：王贵奇
 * 修改时间：2019年12月31日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.1.2
 */

package com.joyintech.tams.invest.trade.investtrade.commandscollection.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.system.base.entity.SysPageDictlist;
import com.joyintech.tams.constant.DictCodeListExt3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.invest.trade.investtrade.commandscollection.dto.TrdCommandsCollection001Dto;
import com.joyintech.tams.invest.trade.investtrade.commandscollection.service.TrdCommandsCollection001Service;

/**
 *  指令汇总Controller
 * @author 王贵奇
 * @version 1.0 2019年12月31日
 * @see TrdCommandsCollection001Controller
 * @since 1.0
 */
@Controller
@PageId("TrdCommandsCollection001")
@RequestMapping("/invest/trade/investtrade/commandscollection/TrdCommandsCollection001")
public class TrdCommandsCollection001Controller extends BaseController{

    /**
     * 指令汇总001service注入
     */
    @Autowired
    private TrdCommandsCollection001Service trdCommandsCollection001Service;
   
    /**
     * 初始化方法
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        setSelectOptionToModel(model,DictCode.WIND一级分类_含债券一级分类等,DictCode.WIND二级分类_含债券二级分类等,DictCode.基金类资产二级类型,
                DictCode.估值交易市场,DictCode.外汇交易模式,DictCode.上市板,DictCode.申购方式,DictCode.申购类型,DictCode.交易平台,
                DictCode.回购类型,DictCode.交易方向,DictCode.回购拆借方向,DictCode.质押标的,DictCode.拆借类型,DictCode.计划类资产一级分类,
                DictCode.存款类资产类型,DictCode.项目类一级分类,DictCode.交易场所);
        model.addAttribute("ENTRUST_STATUS_03", DictCodeList.委托状态_已接收.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F01", DictCodeList.平安资产类型_债券.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F02", DictCodeList.平安资产类型_基金.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F03", DictCodeList.平安资产类型_计划类资产_利率型.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F04", DictCodeList.平安资产类型_计划类资产_净值型.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F05", DictCodeList.平安资产类型_存款.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F06", DictCodeList.平安资产类型_项目类资产.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F07", DictCodeList.平安资产类型_质押式回购.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F08", DictCodeList.平安资产类型_拆借.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F09", DictCodeList.平安资产类型_计划类资产_货币型.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F10", DictCodeList.平安资产类型_买断式回购.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F11", DictCodeListExt2.平安资产类型_股票.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F17", DictCodeListExt2.平安资产类型_一级市场债.getValue());
        List<SysPageDictlist> pinanAssetType;
        pinanAssetType = trdCommandsCollection001Service.getPSControl("PINAN_ASSET_TYPE_F01");
        model.addAttribute("PINAN_ASSET_TYPE_F01_PS",pinanAssetType);
        pinanAssetType = trdCommandsCollection001Service.getPSControl("PINAN_ASSET_TYPE_F03");
        model.addAttribute("PINAN_ASSET_TYPE_F03_PS",pinanAssetType);
        pinanAssetType = trdCommandsCollection001Service.getPSControl("PINAN_ASSET_TYPE_F04");
        model.addAttribute("PINAN_ASSET_TYPE_F04_PS",pinanAssetType);
        pinanAssetType = trdCommandsCollection001Service.getPSControl("PINAN_ASSET_TYPE_F05");
        model.addAttribute("PINAN_ASSET_TYPE_F05_PS",pinanAssetType);
        pinanAssetType = trdCommandsCollection001Service.getPSControl("PINAN_ASSET_TYPE_F06");
        model.addAttribute("PINAN_ASSET_TYPE_F06_PS",pinanAssetType);
        pinanAssetType = trdCommandsCollection001Service.getPSControl("PINAN_ASSET_TYPE_F09");
        model.addAttribute("PINAN_ASSET_TYPE_F09_PS",pinanAssetType);
        pinanAssetType = trdCommandsCollection001Service.getPSControl("PINAN_ASSET_TYPE_F11");
        model.addAttribute("PINAN_ASSET_TYPE_F11_PS",pinanAssetType);

        return super.init(model);
    }

    /**
     * 资产代码联想查询各类资产对应的资产代码
     * @param param 包括资产大类和输入的资产名称
     * @return List<SuggestDto> 联想框下拉选项
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/getFinprodMarketIds")
    public List<SuggestDto> getFinprodMarketIds() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("finprodMarketId", super.getRequest().getParameter("param"));
        param.put("assetType", super.getRequest().getParameter("assetType"));
        List<SuggestDto> list = trdCommandsCollection001Service.getFinprodMarketIds(param);
        return list;
    }

    /**
     * 查询汇总指令
     * @param param 包括资产大类和输入的查询条件
     * @param page page
     * @param length length
     * @return Page<TrdCommandsCollection001Dto> 汇总指令列表
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<TrdCommandsCollection001Dto> findPage() {
        Map<String,Object> param = RequestUtil.getParameters(getRequest());
        Page<TrdCommandsCollection001Dto> trdCommandsCollection001DtoPage = trdCommandsCollection001Service.selectForPage(param);
        return trdCommandsCollection001DtoPage;
    }
    
    /**
     * 导出
     * @return ResponseEntity<byte[]>
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @RequestMapping("export")
    public ResponseEntity<byte[]> export()  {
        return trdCommandsCollection001Service.export(this.getPageId(),RequestUtil.getParameters(getRequest()));
    }
}
