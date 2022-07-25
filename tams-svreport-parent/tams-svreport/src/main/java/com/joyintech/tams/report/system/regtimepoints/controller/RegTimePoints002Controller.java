// ******************************************************************************
// Copyright (C) 2016 Joyintech, All Rights Reserved.
// ******************************************************************************

package com.joyintech.tams.report.system.regtimepoints.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.common.base.entity.ChbRegTimePoints;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.system.regtimepoints.dto.RegTimePoints001Dto;
import com.joyintech.tams.report.system.regtimepoints.service.RegTimePointsMService;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 〈一句话功能简述〉
 * 报送时点管理查询Controller
 * @author tangliang
 * @version 1.0 2017年9月12日
 * @see RegTimePoints002Controller
 * @since 1.0
 */
@Controller
@RequestMapping("/svreport/system/regtimepoints/RegTimePoints002")
@PageId("RegTimePoints002")
public class RegTimePoints002Controller extends BaseController {

    /**
     * 报送时点管理Service
     */
    @Autowired
    private RegTimePointsMService regTimePointsMService;

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(RegTimePoints002Controller.class);

    /**
     * 页面加载
     *
     * @param model ModelMap
     * @return pageUrl
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(final Model model) {
        String seqno = this.getParam("seqno");
            //流程中的查看
        ChbRegTimePoints entity = regTimePointsMService.selectByPrimaryKey(seqno);
        model.addAttribute("entity",entity);
        super.setSelectOptionToModel(model, DictCode.报表名称);

        return super.init(model);
    }

    /**
     * 修改方法
     * @param dto
     * @return
     */
    @ResponseBody
    @RequestMapping(PAGE_SAVE)
    public AjaxResult savDto(@RequestBody RegTimePoints001Dto dto){
        String  message = "修改成功";
            try{
                regTimePointsMService.updateRegTimePoints(dto);
            }catch(Exception e){
                logger.error("修改失败:",e);
                message = "修改成功";
            }
         return new AjaxResult(message);
    }


}
