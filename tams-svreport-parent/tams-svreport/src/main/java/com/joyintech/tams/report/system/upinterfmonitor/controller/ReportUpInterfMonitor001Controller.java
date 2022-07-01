/*
 * 文件名：ReportUpInterfMonitor001Controller.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统
 * 修改人：hujing
 * 修改时间：2017年9月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统
 */
package com.joyintech.tams.report.system.upinterfmonitor.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.system.upinterfmonitor.dto.ReportUpInterfMonitor001Dto;
import com.joyintech.tams.report.system.upinterfmonitor.service.ReportUpInterfMonitorMService;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 上游接口文件读取处理监控 〈功能详细描述〉
 * 
 * @author hujing
 * @version 1.0 2018年8月10日
 * @see ReportUpInterfMonitor001Controller
 * @since 1.0
 */
@Controller
@PageId("ReportUpInterfMonitor001")
@RequestMapping("/svreport/system/upinterfmonitor/ReportUpInterfMonitor001")
public class ReportUpInterfMonitor001Controller extends BaseController {

	@Autowired
	private ReportUpInterfMonitorMService  reportUpInterfMonitorMService;
	/**
	 * 页面初始化 {@inheritDoc}
	 */
	@Override
	public String init(Model model) {
		Map<String, Object> map = RequestUtil.getPageParam();
		model.addAttribute("isFromMain", "02");
		String myOrgCode = CommonUtil.getOrgCode();
		if (StringUtil.isNotEmpty(myOrgCode)) {
			model.addAttribute("MY_AGENCY_CODE", myOrgCode);
		}
		if (StringUtil.isNotEmpty(map.get("fileType"))) {
			model.addAttribute("fileType", map.get("fileType").toString().trim());
			if (StringUtil.isNotEmpty(map.get("fileHandleStatus"))) {
				model.addAttribute("fileHandleStatus", map.get("fileHandleStatus").toString().trim());
			}
			model.addAttribute("searchDataStart", map.get("searchDataStart"));
			model.addAttribute("searchDataEnd", map.get("searchDataEnd"));
			model.addAttribute("isFromMain", "01");
		}
		if(StringUtil.isNotEmpty(map.get("fileHandleStatus"))){
			model.addAttribute("fileHandleStatus", map.get("fileHandleStatus").toString().trim());
			model.addAttribute("searchDataStart", map.get("searchDataStart"));
			model.addAttribute("searchDataEnd", map.get("searchDataEnd"));
			model.addAttribute("isFromMain", "01");
		}
		super.setSelectOptionToModel(model, DictCode.上游接口文件类型,DictCode.上游接口文件读取处理状态);
		return super.init(model);
	}
	
	/**
     * 数据文件名下拉框
     * @return 数据文件名列表
     */
    @RequestMapping("/gethandleFileName")
    @ResponseBody
    public List<SelectOptionDto> gethandleFileName() {
    	
        return reportUpInterfMonitorMService.queryHandleFileNameList();
    } 
    
    /**
     * 查询列表
     *
     * @return DtPage
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<ReportUpInterfMonitor001Dto> search() {
    	Map<String, Object> param = RequestUtil.getPageParam();
        return reportUpInterfMonitorMService.searchForPage(param);
    }
    
    @RequestMapping("export")
	@ResponseBody
	public ResponseEntity<byte[]> export() {
    	String seqNos = RequestUtil.getParam("errorFileSeqNo");
    	return reportUpInterfMonitorMService.export(seqNos);
	}

}
