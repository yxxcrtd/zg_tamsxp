// ******************************************************************************
// Copyright (C) 2016 Joyintech, All Rights Reserved.
// ******************************************************************************

package com.joyintech.tams.report.system.regtimepoints.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import com.joyintech.framework.security.SecurityUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.system.regtimepoints.dto.RegTimePoints001Dto;
import com.joyintech.tams.report.system.regtimepoints.service.RegTimePointsMService;


/**
 * 〈一句话功能简述〉
 * 报送时点管理查询Controller
 * @author tangliang
 * @version 1.0 2017年9月12日
 * @see RegTimePoints001Controller
 * @since 1.0
 */
@Controller
@RequestMapping("/svreport/system/regtimepoints/RegTimePoints001")
@PageId("RegTimePoints001")
public class RegTimePoints001Controller extends BaseController {

    /**
     * 报送时点管理Service
     */
    @Autowired
    private RegTimePointsMService regTimePointsMService;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RegTimePoints001Controller.class);

    /**
     * 页面加载
     *
     * @param model ModelMap
     * @return pageUrl
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(final Model model) {
//        List<SelectOptionDto> list = regTimePoints001Service.searchReportCode(DictCodeList.数据状态_有效.getValue());
//        model.addAttribute("reportCodeList", JSONUtil.toJSONString(list.toArray()));
        List<RegTimePoints001Dto> dtoList=regTimePointsMService.selectPoint();
        if(CollectionUtils.isNotEmpty(dtoList)){
            model.addAttribute("entity", dtoList.get(0));
        }
        super.setSelectOptionToModel(model, DictCode.报表名称);
        return super.init(model);
    }

    /**
     * 查询按钮
     * @return 查询结果list
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(ReportGlobalConstant.SEARCH_PAGE)
    public Page<RegTimePoints001Dto> searchPage() {
        Map<String, Object> map = RequestUtil.getPageParam();
//        map.put("orgCode",this.getLoginUser().getOrgCode());
        String reportCode = (String)map.get("reportCode");
        map.put("reportCodeList", StringUtil.splitToList(GlobalConstant.COMMA, reportCode));
        Page<RegTimePoints001Dto> pageList= regTimePointsMService.searchPage(map);
        for (RegTimePoints001Dto regTimePoints001Dto : pageList.getRoot()) {
            regTimePoints001Dto.setReportCode(SysDataUtil.getNameFromDataDict(DictCode.报表名称, regTimePoints001Dto.getReportCode()));
            //净值变动
            if (Objects.equals(regTimePoints001Dto.getTypeDetail(),DictCodeListExt2.变动类型_净值变动.getValue())) {
                regTimePoints001Dto.setTypeDetail(DictCodeListExt2.变动类型_净值变动.getCodeName());
            }else {
                regTimePoints001Dto.setTypeDetail(SysDataUtil.getNameFromDataDict(DictCode.细分类别, regTimePoints001Dto.getTypeDetail()));
            }
        }
        return pageList;
    }
    
    /**
     * 查询check
     *
     * @return 初始化路径
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(value = "check")
    public AjaxResult check(){
        try {
        	AjaxResult wr = new AjaxResult();
            Map<String, Object> paraMap = RequestUtil.getPageParam();
//            paraMap.put("orgCode",this.getLoginUser().getOrgCode());
            if (regTimePointsMService.findRegTimePointsCount(paraMap) == 0) {
                wr.setMessage(this.getMessage("REPORT_COMMON_E_0015"), MessageType.错误消息);
            }
            return wr;
        }
        catch (Exception ex) {
//            ex.printStackTrace();
            logger.error(ex.getMessage(),ex);
            return null;
//            throw ex;
        }
    }
    
    /**
     * 保存前做的check
     *
     * @param dto 报送时点dto
     * @param result check结果
     * @return 验证结果
     */
    @ResponseBody
    @RequestMapping("checkSave")
    public AjaxResult checkSave(@Valid @RequestBody RegTimePoints001Dto dto,
                               BindingResult result) {
    	AjaxResult webResult = new AjaxResult(result);
        return webResult;
    }

    /**
     * 保存编辑后的参数
     *
     * @param dto 报送时点dto
     * @return 
     */
    @ResponseBody
    @RequestMapping(value = PAGE_SAVE, method = RequestMethod.POST)
    public  void doSave(RegTimePoints001Dto dto) {
//        dto.setOrgCode(this.getLoginUser().getOrgCode());
    	regTimePointsMService.updateRegTimePoints(dto);
    }
    /**
     * 保存编辑后的参数
     *
     * @param dto 报送时点dto
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "saveData", method = RequestMethod.POST)
    public  AjaxResult saveData() {
//        dto.setOrgCode(this.getLoginUser().getOrgCode());
        Map<String, Object> paraMap = RequestUtil.getPageParam();
        paraMap.put("updateUser", SecurityUtil.getLoginUser().getUserName());//add by lingxq on 2021-06-23 for 微服务获取Session必须前台传参
        regTimePointsMService.updateAllRegTimePoints(paraMap);
        return commonCheck(null, "save");
    }
    
    /**
     * 功能:获取报表code
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月16日
     * @return 
     * List<SelectOptionDto>
     */
    @ResponseBody
    @RequestMapping("/getReportCode")
    public Page<SelectOptionDto> getReportCode(){
        Map<String, String> reportCodeMap = SvrDataUtil.getSysDictDataMap(DictCode.报表名称);
        String search = this.getParam("search");//页面传递过来的搜索内容
        List<SelectOptionDto> resultList = new ArrayList<>();
        if(MapUtils.isNotEmpty(reportCodeMap)){
            for (String key : reportCodeMap.keySet()) {
                if (DictCodeListExt2.报表名称_业务明细信息登记.getValue().equals(key)
                		||DictCodeListExt2.报表名称_机构和私人银行产品发行登记.getValue().equals(key)
                		||DictCodeListExt2.报表名称_从业人员登记.getValue().equals(key)
                		||DictCodeListExt2.报表名称_分类总量信息登记.getValue().equals(key)){
                	continue;
                }
                SelectOptionDto resultDto = new SelectOptionDto();
                resultDto.setId(key);
                resultDto.setText(reportCodeMap.get(key));
                //modify by yanya on 20210526 增加模糊匹配的功能 start
                if(StringUtil.isNotEmpty(search)) {
                    if(key.toLowerCase().contains(search.toLowerCase())
                            || reportCodeMap.get(key).toLowerCase().contains(search.toLowerCase())) {
                        resultList.add(resultDto);
                    }
                }
                else {
                    resultList.add(resultDto);
                }
                //modify by yanya on 20210526 增加模糊匹配的功能 end
            }
        }
        
        Page<SelectOptionDto> page = new Page();
        page.setRoot(resultList);
        return page;
    }
}
