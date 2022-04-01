/*
 * 文件名：EquityInfo002Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：天津滨海农商行理财资管系统
 * 修改人：dingzhipeng
 * 修改时间：2018年5月17日
 * 修改内容：新建
 * 系统名称：天津滨海农商行理财资管系统
 */
 
package com.joyintech.tams.report.submitmaintenance.equityinfo.controller;

import java.util.Map;

import com.joyintech.tams.systemtools.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.service.EquityInfoMService;


/**
 * 投资者信息新建
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2018年5月17日
 * @see EquityInfo002Controller
 * @since 1.0
 */
@Controller
@PageId("EquityInfo002")
@RequestMapping("/svreport/submitmaintenance/equityinfo/EquityInfo002")
public class EquityInfo002Controller extends BaseController {
    
    /**
     * 业务entity
     */
    @Autowired
    private EquityInfoMService equityInfoMService;
    /**
    *
    * {@inheritDoc}
    */
   @Override
   @RequestMapping(PAGE_INIT)
   public String init(Model model) {
       super.setSelectOptionToModel(model, DictCode.是否_中债,DictCode.性别_中债,
           DictCode.境内外,DictCode.投资者类别,DictCode.投资者分类,
           DictCode.投资者风险偏好,DictCode.权益人类别,DictCode.个人证件类别,DictCode.销售区域_中债,
           DictCode.机构证件类别,DictCode.SPV资金托管账户开户行,DictCode.所属国家或地区);
           Map<String,Object>map=RequestUtil.getPageParam();
           if(map.containsKey("indentifyMark")){
               EquityInfo001Dto equityInfo001Dto = equityInfoMService.getEntity(map);
               if(equityInfo001Dto!=null){
                   model.addAttribute("entity", equityInfo001Dto);
                   String isSubmit = equityInfoMService.confirmSubmit(equityInfo001Dto);
                   model.addAttribute("isSubmit", isSubmit);
               }
           }
       model.addAttribute("registBankCode", CommonUtil.getUserBankCode(null));
       model.addAttribute("no", DictCodeList.是否_中债_否.getValue());
       return super.init(model);
   }

   /**
    * 
    * check
    * <功能详细描述>
    * @param dto 待校验数据
    * @return jiaoyanjieguo
    * @see
    * @since 1.0
    */
  @ResponseBody
  @RequestMapping("checkSave")
  public AjaxResult checkSave(@RequestBody EquityInfo001Dto dto){
      AjaxResult result = equityInfoMService.checkOrSave(dto,false);
      if(result.getErrorList().isEmpty()){
          result.setData(this.getMessage("REPORT_COMMON_C_0001", this.getMessage("SysCommon.save")));
      }
      return  result;
  }
  /**
   * 
   * check
   * <功能详细描述>
   * @param dto 待校验数据
   * @return jiaoyanjieguo
   * @see
   * @since 1.0
   */
 @ResponseBody
 @RequestMapping("doSave")
 public AjaxResult doSave(@RequestBody EquityInfo001Dto dto){
     AjaxResult result = equityInfoMService.checkOrSave(dto,true);
     if (StringUtil.isEmptyList(result.getErrorList())) {
         result.setMessage(getMessage("REPORT_COMMON_I_0006"));
     }
     return  result;
 }
}
