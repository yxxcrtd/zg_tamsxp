/*
 * 文件名：MarketCalendarInfoController001.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：
 * 修改时间：2018年7月16日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */
package com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.*;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto.ChbCalendarInfo001Dto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto.ChbCalendarInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.service.ChbCalendarInfo001Service;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 市场日历信息
 *
 * @author zhoubin
 * @version 2022年3月28日
 */

@Controller
@PageId("ChbCalendarInfo001")
@RequestMapping("/chb/businessdatamaintain/chbcalendarInfo/ChbCalendarInfo001")
public class ChbCalendarInfo001Controller extends BaseController {
    /**
     *
     */
    @Resource
    private ChbCalendarInfo001Service chbTradingCalendarInfo001Service;

    /**
     * 初始化页面信息
     * @return 处理结果
     */
    @Override
    public String init(Model model) {
        //初始化是否节假日下拉框
        this.setSelectOptionToModel(model, DictCode.是否节假日);
        //数据的状态
        this.setSelectOptionToModel(model, DictCode.维护数据状态);
        //节假日信息
        this.setSelectOptionToModel(model, DictCode.节假日信息);
        Date sysDate = DateUtil.platDateFromCache();
        model.addAttribute("sysDate", sysDate);
        model.addAttribute("_yes", 1);
        model.addAttribute("_no", 0);
        return super.init(model);
    }

    /**
     * 市场日历信息查询
     *
     * @return 处理结果
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("queryList")
    public Page<ChbCalendarInfo001Dto> selectInfo() {
        Map<String, Object> param = RequestUtil.getPageParam();
        return chbTradingCalendarInfo001Service.selectInfo(param);
    }

    /**
     * 节假日和工作日更新
     * @param dto 实体类集合
     * @return 返回处理信息
     */
    @ResponseBody
    @RequestMapping("updateIsHoliday")
    public AjaxResult updateIsHoliday(@RequestBody ChbCalendarInfoDto dto) {
        return chbTradingCalendarInfo001Service.updateIsHoliday(dto);
    }


    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForImportData(String fileName)  {
        return chbTradingCalendarInfo001Service.checkConfirmMsgForImportData(fileName);
    }
    /**
     * 导入
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("importFile")
    public List<ExcelValidDto> importData(String fileName) throws FileNotFoundException, ParseException {
        return chbTradingCalendarInfo001Service.importData(fileName);
    }

    /**
     * 导出
     * <功能详细描述>
     * @return 返回导出报错信息
     */
    @RequestMapping("export")
    public ResponseEntity<byte[]> export() {
        return chbTradingCalendarInfo001Service.export(this.getPageId(), RequestUtil.getParameters(getRequest()));
    }

}
