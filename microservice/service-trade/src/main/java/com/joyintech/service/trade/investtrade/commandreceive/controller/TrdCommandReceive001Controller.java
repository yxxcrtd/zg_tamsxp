/*
 * 文件名：TrdCommandReceive001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：wangkang
 * 修改时间：2018年9月26日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.trade.investtrade.commandreceive.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.joyintech.background.component.common.pack.common.util.HtmlEncode;
import com.joyintech.tams.constant.DictCodeListExt2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.service.trade.investtrade.commandreceive.dto.TrdCommandReceive001Dto;
import com.joyintech.service.trade.investtrade.commandreceive.service.TrdCommandReceive001Service;
import com.joyintech.tams.common.base.entity.User;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;

/**
 * 指令接收页面
 * 〈功能详细描述〉
 * @author wangkang
 * @version 1.0 2018年9月26日
 * @see TrdCommandReceive001Controller
 * @since 1.0
 */
@RestController("TrdCommandReceive001Ctr_dtx")
@RequestMapping("/TrdCommandReceive001")
public class TrdCommandReceive001Controller{
	/**
     * 微服务指令接收service注入
     */
	@Autowired
	private TrdCommandReceive001Service trdCommandReceive001Service;
	
	
	
	/**
	 * 指令已接收分页查询所有
	 * @param param
	 * @param page
	 * @param length
	 * @return
	 * @throws Exception
	 */
	@PostMapping("findPage/{page}/{length}")
	public Page<TrdCommandReceive001Dto> selectAllCommandReceive(@RequestBody Map<String, Object> param, @PathVariable("page") int page, @PathVariable("length") int length) {
        String cstpType = (String)param.get("CstpType");
        if(StringUtils.isNotBlank(cstpType)){
            param.put("cstpType","cstp");
        }
        Page<TrdCommandReceive001Dto> pageLimit = trdCommandReceive001Service.selectForPage(page, length, param);
	    //将交易员转为真实姓名
        List<TrdCommandReceive001Dto> list=pageLimit.getRoot();
        if(!list.isEmpty()){
          for(TrdCommandReceive001Dto dto:list){
              if(StringUtil.isNotEmpty(dto.getRemark())) {
                  dto.setRemark(HtmlEncode.htmlEncode(HtmlEncode.htmlEncode(dto.getRemark())));
              }
              if(StringUtil.isNotEmpty(dto.getTrader())){
                  User user=trdCommandReceive001Service.getRealNameByUserName(dto.getTrader());
                  if(StringUtil.isNotEmpty(user)){
                      dto.setTrader(user.getRealName());
                  }
              }
              if(StringUtil.equals(DictCodeList.平安资产类型_买断式回购.getValue(), dto.getFinprodType2())){
//            	  dto.setEntrustNum(ArithmeticUtil.multiply(dto.getEntrustAmt(), new BigDecimal(100)));
//            	  dto.setFinshAmt(ArithmeticUtil.multiply(dto.getFinshAmt(), new BigDecimal(100)));
               }
              if(StringUtil.isNotEmpty(dto.getBusiPs())){
                  //委托指令附属表中取出回购/拆借方向
                  if(StringUtil.equals(DictCodeList.平安资产类型_质押式回购.getValue(), dto.getFinprodType2())
                         || StringUtil.equals(DictCodeList.平安资产类型_买断式回购.getValue(), dto.getFinprodType2())
                         || StringUtil.equals(DictCodeList.平安资产类型_拆借.getValue(), dto.getFinprodType2()) ){
                      dto.setPs(dto.getBusiPs());
                  }
              }
              if(StringUtil.equals(dto.getFinprodType2(), DictCodeList.平安资产类型_债券.getValue())){
//            	  dto.setFinshAmt(ArithmeticUtil.multiply(dto.getFinshAmt(),BigDecimal.valueOf(100)));
            	  //dto.setProAvailableCash(ArithmeticUtil.multiply(ArithmeticUtil.subtract(dto.getEntrustAmt(), dto.getFinshAmt()),BigDecimal.valueOf(100)));
              }
              if(StringUtil.equals(dto.getFinprodType2(), DictCodeListExt2.平安资产类型_一级市场债.getValue())){
                  //债券一级投标交易方向的查询结果显示为空；
                  dto.setPs(null);
                  TrdCommandReceive001Dto  dto2=trdCommandReceive001Service.getEtrBidInfo(dto.getEntrustId());
                  if(StringUtil.isNotEmpty(dto2)){
                      //列表项中委托额度后增加字段 投标标位
                      dto.setBidPrice(dto2.getBidPrice());
                      // 委托额度显示的是投标委托的投标券面总额汇总；
                      dto.setEntrustAmt(dto2.getBidPrinAmt());
                      //投标标号
                      dto.setBidId(dto2.getBidId());
                      //已完成额度*100
                      dto.setFinshAmt(ArithmeticUtil.multiply(dto.getFinshAmt(),BigDecimal.valueOf(100)));
                      //可交易额度显示的是中标后维护的中标券面总额，未中标时显示为空；
                      if(StringUtil.equals(dto2.getIsWin(), BussinessDictCodeList.是否_是.getValue())){
                          dto.setProAvailableCash(dto2.getWinPrinAmt());
                      }
                      else {
                          dto.setProAvailableCash(null);
                      }
                  }
              }
          }
        }    
        return pageLimit;
	}
	/**
	 * 指令未接收查询
	 * @param param
	 * @param page
	 * @param length
	 * @return
	 * @throws Exception
	 */
	@PostMapping("otherFindPage/{page}/{length}")
	public Page<TrdCommandReceive001Dto> otherSelectAllCommandReceive(@RequestBody Map<String, Object> param, @PathVariable("page") int page, @PathVariable("length") int length) {
	    Page<TrdCommandReceive001Dto> pageLimit = trdCommandReceive001Service.otherSelectForPage(page, length, param);
        //将交易员转为真实姓名
        List<TrdCommandReceive001Dto> list=pageLimit.getRoot();
        if(!list.isEmpty()){
          for(TrdCommandReceive001Dto dto:list){
              if(StringUtil.isNotEmpty(dto.getRemark())) {
                    dto.setRemark(HtmlEncode.htmlEncode(HtmlEncode.htmlEncode(dto.getRemark())));
              }
              if(StringUtil.isNotEmpty(dto.getTrader())){
                  User user=trdCommandReceive001Service.getRealNameByUserName(dto.getTrader());
                  if(StringUtil.isNotEmpty(user)){
                      dto.setTrader(user.getRealName());
                  }
              }
              if(StringUtil.isNotEmpty(dto.getBusiPs())){
                  //委托指令附属表中取出回购/拆借方向
                  if(StringUtil.equals(DictCodeList.平安资产类型_质押式回购.getValue(), dto.getFinprodType2())
                          || StringUtil.equals(DictCodeList.平安资产类型_买断式回购.getValue(), dto.getFinprodType2())
                          || StringUtil.equals(DictCodeList.平安资产类型_拆借.getValue(), dto.getFinprodType2()) ){
                      dto.setPs(dto.getBusiPs());
                  }
              }
              if(StringUtil.equals(dto.getFinprodType2(), DictCodeList.平安资产类型_债券.getValue())){
            	  dto.setFinshAmt(ArithmeticUtil.multiply(dto.getFinshAmt(),BigDecimal.valueOf(100)));
            	  //dto.setProAvailableCash(ArithmeticUtil.multiply(ArithmeticUtil.subtract(dto.getEntrustAmt(), dto.getFinshAmt()),BigDecimal.valueOf(100)));
              }
              if(StringUtil.equals(dto.getFinprodType2(), DictCodeListExt2.平安资产类型_一级市场债.getValue())){
                  //债券一级投标交易方向的查询结果显示为空；
                  dto.setPs(null);
                  TrdCommandReceive001Dto  dto2=trdCommandReceive001Service.getEtrBidInfo(dto.getEntrustId());
                  if(StringUtil.isNotEmpty(dto2)){
                      //列表项中委托额度后增加字段 投标标位
                      dto.setBidPrice(dto2.getBidPrice());
                      // 委托额度显示的是投标委托的投标券面总额汇总；
                      dto.setEntrustAmt(dto2.getBidPrinAmt());
                      //投标标号
                      dto.setBidId(dto2.getBidId());
                      //可交易额度显示的是中标后维护的中标券面总额，未中标时显示为空；
                      if(StringUtil.equals(dto2.getIsWin(), BussinessDictCodeList.是否_是.getValue())){
                          dto.setProAvailableCash(dto2.getWinPrinAmt());
                      }
                      else{
                          dto.setProAvailableCash(null);
                      }
                  }
              }
          }
        }    
        return pageLimit;
	}
	
	/**
     * 指令未接收查询
     * @param map
     * @return
     */
	@RequestMapping("/otherSelectList")
    public List<TrdCommandReceive001Dto> otherSelectList(@RequestBody Map<String, Object> map){
        return trdCommandReceive001Service.otherSelectList(map);
    }
	
	/**
	 * 根据委托代码查询投资委托
	 * @param entrustId
	 * @return
	 * @throws Exception
	 */
    @GetMapping("/getEtrInvest")
    public List<TrdCommandReceive001Dto> getEtrInvest(String entrustId) {
    	return trdCommandReceive001Service.selectEtrInvest(entrustId);
    }
    /**
     * 市场代码联想框查询
     * @param finprodMarketId
     * @return
     */
    @GetMapping("/getFinprodMarketIds")
    public List<SuggestDto> selectFinprodMarketIds(String finprodMarketId){
    	return trdCommandReceive001Service.getFinprodMarketIds(finprodMarketId);
    }
   
    /**
	 * 接收委托
	 */
    @Transactional
    @DTXTransaction
	@PostMapping("/receiveEntrust") 
	public List<ValidMessage> receiveEntrust(@RequestBody Map<String,Object> param){
		return trdCommandReceive001Service.receiveEntrust(param);
	}
	
	/**
     * 退回委托
	*/
    @Transactional
    @DTXTransaction
	@PostMapping("/refuseEntrust")
	public List<ValidMessage> returnEntrust(@RequestBody Map<String,Object> param){
		return trdCommandReceive001Service.returnEntrust(param);
	}

    @PostMapping("selectForPageFromCstp/{page}/{length}")
    public Page<TrdCommandReceive001Dto> selectForPageFromCstp(@RequestBody Map<String, Object> param, @PathVariable("page") int page, @PathVariable("length") int length) {
        String cstpType = (String)param.get("CstpType");
        if(StringUtils.isNotBlank(cstpType)){
            param.put("cstpType","cstp");
        }
        Page<TrdCommandReceive001Dto> pageLimit = trdCommandReceive001Service.selectForPageFromCstp(page, length, param);
        //将交易员转为真实姓名
        List<TrdCommandReceive001Dto> list=pageLimit.getRoot();
        if(!list.isEmpty()){
            for(TrdCommandReceive001Dto dto:list){
                if(StringUtil.isNotEmpty(dto.getTrader())){
                    User user=trdCommandReceive001Service.getRealNameByUserName(dto.getTrader());
                    if(StringUtil.isNotEmpty(user)){
                        dto.setTrader(user.getUserName());
                    }
                }
                if(StringUtil.equals(DictCodeList.平安资产类型_买断式回购.getValue(), dto.getFinprodType2())){
//            	  dto.setEntrustNum(ArithmeticUtil.multiply(dto.getEntrustAmt(), new BigDecimal(100)));
//            	  dto.setFinshAmt(ArithmeticUtil.multiply(dto.getFinshAmt(), new BigDecimal(100)));
                }
                if(StringUtil.isNotEmpty(dto.getBusiPs())){
                    //委托指令附属表中取出回购/拆借方向
                    if(StringUtil.equals(DictCodeList.平安资产类型_质押式回购.getValue(), dto.getFinprodType2())
                            || StringUtil.equals(DictCodeList.平安资产类型_买断式回购.getValue(), dto.getFinprodType2())
                            || StringUtil.equals(DictCodeList.平安资产类型_拆借.getValue(), dto.getFinprodType2()) ){
                        dto.setPs(dto.getBusiPs());
                    }
                }
                if(StringUtil.equals(dto.getFinprodType2(), DictCodeList.平安资产类型_债券.getValue())){
//            	  dto.setFinshAmt(ArithmeticUtil.multiply(dto.getFinshAmt(),BigDecimal.valueOf(100)));
                    //dto.setProAvailableCash(ArithmeticUtil.multiply(ArithmeticUtil.subtract(dto.getEntrustAmt(), dto.getFinshAmt()),BigDecimal.valueOf(100)));
                }
                if(StringUtil.equals(dto.getFinprodType2(), DictCodeListExt2.平安资产类型_一级市场债.getValue())){
                    //债券一级投标交易方向的查询结果显示为空；
                    dto.setPs(null);
                    TrdCommandReceive001Dto  dto2=trdCommandReceive001Service.getEtrBidInfo(dto.getEntrustId());
                    if(StringUtil.isNotEmpty(dto2)){
                        //列表项中委托额度后增加字段 投标标位
                        dto.setBidPrice(dto2.getBidPrice());
                        // 委托额度显示的是投标委托的投标券面总额汇总；
                        dto.setEntrustAmt(dto2.getBidPrinAmt());
                        //投标标号
                        dto.setBidId(dto2.getBidId());
                        //已完成额度*100
                        dto.setFinshAmt(ArithmeticUtil.multiply(dto.getFinshAmt(),BigDecimal.valueOf(100)));
                        //可交易额度显示的是中标后维护的中标券面总额，未中标时显示为空；
                        if(StringUtil.equals(dto2.getIsWin(), BussinessDictCodeList.是否_是.getValue())){
                            dto.setProAvailableCash(dto2.getWinPrinAmt());
                        }
                        else {
                            dto.setProAvailableCash(null);
                        }
                    }
                }
            }
        }
        return pageLimit;
    }
}
