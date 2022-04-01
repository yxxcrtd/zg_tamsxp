/*
 * 文件名：TrdCommandDispatcher001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：wangkang
 * 修改时间：2018年9月12日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.trade.investtrade.commanddispatcher.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.util.*;
import com.joyintech.tams.constant.*;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectDto;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.trade.investtrade.commanddispatcher.dto.TrdCommandDispatcher001Dto;
import com.joyintech.tams.invest.trade.investtrade.commanddispatcher.service.TrdCommandDispatcher001Service;

/**
 * 指令分发页面
 * 〈功能详细描述〉
 * @author wangkang
 * @version 1.0 2018年9月12日
 * @see TrdCommandDispatcher001Controller
 * @since 1.0
 */
@Controller
@PageId("TrdCommandDispatcher001")
@RequestMapping("/invest/trade/investtrade/commanddispatcher/TrdCommandDispatcher001")
public class TrdCommandDispatcher001Controller extends BaseController{

	/**
     * 指令分发service注入
     */
	@Autowired
	private TrdCommandDispatcher001Service trdCommandDispatcher001Service;
	
	@Override
	@RequestMapping(PAGE_INIT)
	public String init(Model model) {
		// TODO Auto-generated method stub
	    // modify by dubaojun 2020-06-29 上海农商行增加是否进入审批流程查询条件 start
		setSelectOptionToModel(model,DictCode.币种,DictCode.委托状态,DictCode.平安资产类型,DictCode.交易场所,DictCode.交易方向,
		        DictCode.是否);
        // modify by dubaojun 2020-06-29 上海农商行增加是否进入审批流程查询条件 end
		
		List<SelectOptionDto> options = new ArrayList<>();
        SelectDto selectDto1 = new SelectDto();
        SelectDto selectDto2 = new SelectDto();
        SelectDto selectDto3 = new SelectDto();
        SelectDto selectDto4 = new SelectDto();
        SelectDto selectDto5 = new SelectDto();
        SelectDto selectDto6 = new SelectDto();
        SelectDto selectDto7 = new SelectDto();
		if (StringUtil.equals(CacheUtil.getSysParam("IS_SHOW_TYPE"),"01")){
            selectDto2 = this.getSelectDataFromDataDic(DictCode.行内资产二级分类, null);
            options.addAll(selectDto2.getOptions());
        }else{
             selectDto1 = this.getSelectDataFromDataDic(DictCode.WIND一级分类_含债券一级分类等, null);
             selectDto2 = this.getSelectDataFromDataDic(DictCode.存款类资产类型, null);
             selectDto3 = this.getSelectDataFromDataDic(DictCode.基金类资产类型, null);
             selectDto4 = this.getSelectDataFromDataDic(DictCode.项目类一级分类, null);
             selectDto5 = this.getSelectDataFromDataDic(DictCode.计划类资产一级分类, null);
             selectDto6 = this.getSelectDataFromDataDic(DictCode.拆借类型, null);
             selectDto7 = this.getSelectDataFromDataDic(DictCode.回购类型, null);
            options.addAll(selectDto1.getOptions());
            options.addAll(selectDto2.getOptions());
            options.addAll(selectDto3.getOptions());
            options.addAll(selectDto4.getOptions());
            options.addAll(selectDto5.getOptions());
            options.addAll(selectDto6.getOptions());
            options.addAll(selectDto7.getOptions());
        }
        List<SelectOptionDto> psList = makeAllFloatRate();
        model.addAttribute("psList", JsonUtils.objectArray2Json(psList.toArray()));
        model.addAttribute("ALL_RESULT", JsonUtils.objectArray2Json(options.toArray()));
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
        model.addAttribute("PINAN_ASSET_TYPE_F13", DictCodeListExt2.平安资产类型_外汇即期.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F14", DictCodeListExt2.平安资产类型_外汇远期.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F15", DictCodeListExt2.平安资产类型_外汇掉期.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F12", DictCodeListExt2.平安资产类型_债券借贷.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F16", DictCodeListExt2.平安资产类型_利率互换.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F36", DictCodeListExt1.金融产品类型_投管用_立项指令.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F19", DictCodeListExt3.平安资产类型_权益类资产.getValue());
        model.addAttribute("PINAN_ASSET_TYPE_F20", DictCodeListExt3.平安资产类型_期货.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_01", DictCodeListExt3.上农商_指令业务类型_股票竞价指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_02", DictCodeListExt3.上农商_指令业务类型_基金竞价指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_03", DictCodeListExt3.上农商_指令业务类型_大宗债券质押式回购指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_04", DictCodeListExt3.上农商_指令业务类型_大宗股票指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_05", DictCodeListExt3.上农商_指令业务类型_大宗基金指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_06", DictCodeListExt3.上农商_指令业务类型_大宗债券指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_17", DictCodeListExt3.上农商_指令业务类型_协议回购首期指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_18", DictCodeListExt3.上农商_指令业务类型_协议回购提前购回指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_19", DictCodeListExt3.上农商_指令业务类型_协议回购到期购回指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_20", DictCodeListExt3.上农商_指令业务类型_协议回购到期续做指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_21", DictCodeListExt3.上农商_指令业务类型_协议回购中途换券指令.getValue());
        model.addAttribute("ENTRUST_BUSS_TYPE_22", DictCodeListExt3.上农商_指令业务类型_协议回购解除质押指令.getValue());

        model.addAttribute("TRADE_PLACE_03", DictCodeListExt1.交易场所_上海证券交易所.getValue());
        model.addAttribute("TRADE_PLACE_04", DictCodeListExt1.交易场所_深圳证券交易所.getValue());
        
        //add by gejiaao start
        model.addAttribute("PINAN_ASSET_TYPE_F17", DictCodeListExt2.平安资产类型_一级市场债.getValue());
        model.addAttribute("PS_T04", DictCodeList.交易方向_申购.getValue());
        //add by gaopeng 20191125 start
        model.addAttribute("PS_T49", DictCodeListExt3.交易方向_债转股.getValue());
        //add by gaopeng 20191125 end
        //add by gejiaao end
        //add by wangpeng start
        model.addAttribute("IRS_TYPE", DictCode.互换方式.getValue());
        //add by wangpeng end
        model.addAttribute("entrustStatus", DictCodeListExt3.委托状态_待分发.getValue());
		List<TrdCommandDispatcher001Dto> dto = trdCommandDispatcher001Service.selectEtrAutoTime();
		if(dto.size() > 0){
		    model.addAttribute("VDATE", dto.get(0).getVdateStr());
		    model.addAttribute("MDATE", dto.get(0).getMdateStr());
		}
		else{
		    model.addAttribute("VDATE", "");
            model.addAttribute("MDATE", "");
		}
		model.addAttribute("SYS_DATE", DateUtil.getSysDate());
        model.addAttribute("ENTRUST_CLASS_01", DictCodeListExt3.投资指令类型_单组合单资产指令.getValue());
        model.addAttribute("ENTRUST_CLASS_02", DictCodeListExt3.投资指令类型_多组合单资产指令.getValue());
		return super.init(model);
	}
	
	 /**
	  * 查询
	  * @return
	  * @throws Exception
	  */
	@ResponseBody
	@RequestMapping("findPage")
	public Page<TrdCommandDispatcher001Dto> selectAllCommonChar() {
		Map<String,Object> param = RequestUtil.getParameters(getRequest());
		return trdCommandDispatcher001Service.selectForPage(param);
	}
	
	/**
    *
    * 组交易方向下拉框值
    * <功能详细描述>
    * @return
    * @see
    * @since 1.0
    */
   private List<SelectOptionDto> makeAllFloatRate() {
       List<SelectOptionDto> listFloatRate = new ArrayList<SelectOptionDto>();

       SelectDto selectDto1 = this.getSelectDataFromDataDic(DictCode.交易方向, null);
       for (SelectOptionDto dto : selectDto1.getOptions()) {
           listFloatRate.add(dto);
       }
       SelectDto selectDto2 = this.getSelectDataFromDataDic(DictCode.回购拆借方向, null);
       for (SelectOptionDto dto : selectDto2.getOptions()) {
           listFloatRate.add(dto);
       }
       SelectDto selectDto3 = this.getSelectDataFromDataDic(DictCode.互换方式, null);
       for (SelectOptionDto dto : selectDto3.getOptions()) {
           listFloatRate.add(dto);
       }
       return listFloatRate;
   }
   
	/**
	 * 委托代码联想文本框
	 * @return
	 * @throws Exception
	 */
    @ResponseBody
    @RequestMapping("/getEntIds")
    public List<SuggestDto> getEntrustId() {
    	Map<String, Object> param = new HashMap<String,Object>();
        param.put("entId", super.getRequest().getParameter("param"));
        List<SuggestDto> list = trdCommandDispatcher001Service.getEntrustId(param);
    	return list;
    }
    /**
     * 组合代码联想文本框
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getPortfolioIds")
    public List<SuggestDto> getPortfolioIds() {
    	String portfolioId=super.getRequest().getParameter("param");
    	return trdCommandDispatcher001Service.getPortfolioIds(portfolioId);
    }
    /**
     * 市场代码联想文本框
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/getFinprodIds")
    public List<SuggestDto> getFinprodIds() {
    	String finprodId=super.getRequest().getParameter("param");
    	List<SuggestDto> list = trdCommandDispatcher001Service.getFinprodIds(finprodId);
    	return list;
    }
    /**
     * 投资经理联想框
     * <功能详细描述>
     * @param search
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("getManagers")
    public List<SelectOptionDto> getmanagers(String search){
        return trdCommandDispatcher001Service.getManagers(search);
    }
    /**
     * 校验
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult checkEtr(@RequestBody TrdCommandDispatcher001Dto dto) {
        return trdCommandDispatcher001Service.validEtr(dto);
    }
    /**
     *  画面导出
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("export")
    public ResponseEntity<byte[]> export() {
        return trdCommandDispatcher001Service.export(this.getPageId(),RequestUtil.getParameters(getRequest()));
    }
}
