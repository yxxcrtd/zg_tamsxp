/*
 * 文件名：OperationalInfo001Controller.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：
 * 修改时间：2019年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.svreport.submanagement.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.report.common.dto.ExcelValidDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.BaseInformationDto;
import com.joyintech.tams.common.base.entity.FilterInformationDto;
import com.joyintech.tams.svreport.infomanagement.service.OperationalInfoMService;
import com.joyintech.tams.svreport.submanagement.service.SubOperationalInfoMService;


/**
 * @Description 业务数据维护控制层
 * @author gaojian
 * @version 1.7 2019年5月8日
 * @since 1.7
 */

@Controller
@PageId("SvrSubOperationalInfo001")
@RequestMapping("/common/submanagement/SvrSubOperationalInfo001")
public class SubOperationalInfo001Controller extends BaseController {
	
	@Autowired
	private SubOperationalInfoMService operationalInfoMService;

	private static final List<String> LIST_TABLE_NAME = Arrays.asList("CHB_PRD_DECLARE_SUB_TMP","CHB_PRD_ISSUE_SUB_TMP", "CHB_PRD_RAISETOT_SUB_TMP","CHB_PRD_DEAL_SUB_TMP","CHB_PRD_END_SUB_TMP",
			"CHB_ASSET_INFO_TMP","CHB_BOT_AST_POS_SUB_TMP","CHB_INVEST_TRADE_SUB_TMP","CHB_AST_VAL_SUB_TMP" ,"CHB_EMPLOYEE_INFO","CHB_PRD_STATUS_SUB","CHB_AST_POS_SUB" );
	
	/**
	 * 页面初始化
	 */
	@Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
		Map<String, Object> map = RequestUtil.getPageParam();
		// 获取接口类型
		String interfType = null;
		if (null != map.get("interfType")) {
			interfType = map.get("interfType").toString().trim();
		}
		List<BaseInformationDto> baseInfomationList = operationalInfoMService.queryAllTableInfo();
		if(StringUtil.isNotEmpty(interfType)) {
		    // 保存interfType
            model.addAttribute("interfType",interfType);
			List<BaseInformationDto> baseInformationDtoList = operationalInfoMService.queryTableIdByInterfType(interfType);
			if (null != baseInformationDtoList && baseInformationDtoList.size() > 0) {
				String tableId = baseInformationDtoList.get(0).getBaseTableId();
				model.addAttribute("tableId", tableId);
			}
		}
		List<SelectOptionDto> listTableName = new ArrayList<SelectOptionDto>();
		baseInfomationList.forEach(baseInformationDto -> {
			if (LIST_TABLE_NAME.contains(baseInformationDto.getBaseTableId())) {
				SelectOptionDto selectOptionDto = new SelectOptionDto();
				selectOptionDto.setId(baseInformationDto.getBaseTableId());
				selectOptionDto.setText(baseInformationDto.getBaseTableName());
				selectOptionDto.setCol("");
				listTableName.add(selectOptionDto);
			}
		});
        model.addAttribute("tableNames", JsonUtils.objectArray2Json(listTableName.toArray())); 
        // 首页点入设置默认查询条件
        if (null != map.get("isFromMain")) {
            String isFromMain = map.get("isFromMain").toString().trim();
            model.addAttribute("isFromMain",isFromMain);
            model.addAttribute("tableId","CHB_INTERF_ERRODATA");
         }
        if (null != map.get("searchDataStart")) {
            model.addAttribute("searchDataStart",map.get("searchDataStart").toString().trim());
         }
        if (null != map.get("searchDataEnd")) {
            model.addAttribute("searchDataEnd",map.get("searchDataEnd").toString().trim());
         }
        
        return super.init(model);
	}
	
	/**
	 * 根据用户查找方案信息
	 * @return 已保存方案信息
	 */
	@ResponseBody
   	@RequestMapping("queryFilterInfoByUserId")
	public Page<FilterInformationDto> queryFilterInfoByUserId() {
		return operationalInfoMService.queryFilterInfoByUserId(RequestUtil.getPageParam());
	}
	
	/**
	 * 根据表名查询对应的字段名
	 * @param tableId 表名
	 * @return 字段名
	 */
	@ResponseBody
    @RequestMapping("queryColumnByTableId")
	public String queryColumnByTableId(String tableId) {
		return operationalInfoMService.queryColumnByTableId(tableId);
	}
	
	/**
	 * 根据表名查询其字段的类型
	 * @param tableId 表名
	 * @return 字段类型
	 */
	@ResponseBody
    @RequestMapping("queryColumnTypeByTableId")
	public String queryColumnTypeByTableId(String tableId) {
		return operationalInfoMService.queryColumnTypeByTableId(tableId);
	}
	
	/**
	 * 分页查询
	 * @return 业务数据维护查询结果
	 */
	@ResponseBody
    @RequestMapping("findPage")
    public String queryData() {
		Map<String, Object> param = RequestUtil.getPageParam();
         String map =operationalInfoMService.queryData(param);
         return map;
    }
	
	/**
	 * 查询字段列表，用于显示在前台字段多选框中
	 * @return 字段列表
	 */ 
	@ResponseBody
    @RequestMapping("queryColumnInfoList")
    public String queryColumnInfoList() {
		Map<String, Object> param = RequestUtil.getPageParam();
        return operationalInfoMService.queryColumnInfoList(param);
    }
	
	/**
	 * 获取字段信息
	 * @return 字段信息
	 */
	@ResponseBody
    @RequestMapping("queryColmnInfo")
	public String queryColmnInfo() {
		Map<String, Object> param = RequestUtil.getPageParam();
		return operationalInfoMService.queryColmnInfo(param);
	}
	
	/**
	 * 保存过滤方案前检查
	 * @param param 页面参数
	 * @return 检查是否通过
	 */
	@ResponseBody
    @RequestMapping(value = "/checkSave", method = RequestMethod.POST)
	public boolean checkSave(@RequestBody Map<String, Object> param) {
		return operationalInfoMService.checkSave(param);
	}
	
	/**
	 * 保存方案
	 * @param param 页面参数
	 * @return 方案是否保存成功
	 */
	@ResponseBody
    @RequestMapping(value = "/savePlan", method = RequestMethod.POST)
	public boolean savePlan(@RequestBody Map<String, Object> param) {
		return operationalInfoMService.savePlan(param);
	}
	
	/**
	 * 修改方案名称
	 * @param param 页面参数
	 * @return 方案名称是否修改成功
	 */
	@ResponseBody
    @RequestMapping(value = "/updatePlanName", method = RequestMethod.POST)
	public boolean updatePlanName(@RequestBody Map<String, Object> param) {
		return operationalInfoMService.updatePlanName(param);
	}
	
	/**
	 * 根据方案ID获取方案信息
	 * @return 方案信息
	 */
	@ResponseBody
    @RequestMapping("queryPlanByfilterId")
	public String queryPlanByfilterId() {
		Map<String, Object> param = RequestUtil.getPageParam();
		return operationalInfoMService.queryPlanByfilterId(param);
	}
	
	/**
	 * 获取指定表所有日期类型字段集合
	 * @return 指定表所有日期类型字段集合
	 */
	@ResponseBody
    @RequestMapping("queryDateColumnList")
	public String queryDateColumnList() {
		Map<String, Object> param = RequestUtil.getPageParam();
		return operationalInfoMService.queryDateColumnList(param);
	}
	
	/**
	 * 获取指定表所有时间类型字段集合
	 * @return 指定表所有时间类型字段集合
	 */
	@ResponseBody
    @RequestMapping("queryTimeColumnList")
	public String queryTimeColumnList() {
		Map<String, Object> param = RequestUtil.getPageParam();
		return operationalInfoMService.queryTimeColumnList(param);
	}
	
	/**
	 * 检查传入的参数是不是一张表的，作为错误信息表导出前检查
	 * @return 是否是同一张表
	 */
	@ResponseBody
    @RequestMapping("checkIsSingleTable")
	public boolean checkIsSingleTable() {
		Map<String, Object> param = RequestUtil.getPageParam();
		return operationalInfoMService.checkIsSingleTable(param);
	}
	
    /**
     * 导出excel
     *
     * @return 导出
     */
	@ResponseBody
	@RequestMapping("export")
    public ResponseEntity<byte[]> export() {
    	Map<String, Object> param = RequestUtil.getPageParam();
    	param.put("tableId", (String) param.get("tableName"));
    	return operationalInfoMService.export(param);
    }
	
    /**
     * 删除数据
     *
     * @return 删除条数
     */
	@ResponseBody
	@RequestMapping("deleteData")
	public int deleteData() {
		Map<String, Object> param = RequestUtil.getPageParam();
    	return operationalInfoMService.deleteData(param);
	}
	
	/**
	 * 数据删除成功之后触发报表连接器
	 * @return
	 */
	@ResponseBody
	@RequestMapping("linkSvrReportAfterDelete")
	public int linkSvrReportAfterDelete() {
		Map<String, Object> param = RequestUtil.getPageParam();
    	return operationalInfoMService.linkSvrReportAfterDelete(param);
	}
	
    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public List<ExcelValidDto> checkConfirmMsgForImportData(String fileName)  {
        return operationalInfoMService.checkConfirmMsgForImportData(fileName);
    }
    
    
    /**
     * 导入数据文件
     * 
     * @param fileName 导入的文件名
     * @return 修改条数
     */
    @ResponseBody
    @RequestMapping("/importData")
    public AjaxResult importData(String fileName) {
    	return operationalInfoMService.importData(fileName);
    }
    
    
    /**
     * 通知报表生成器
     * 
     * @param fileName 导入的文件名
     * @return 修改条数
     */
    @ResponseBody
    @RequestMapping("/linkSvrReport")
    public int linkSvrReport(String fileName) {
    	return operationalInfoMService.linkSvrReport(fileName);
    }
    
    /**
     * 删除方案
     * @return 删除方案名称
     */
    @ResponseBody
	@RequestMapping("deleteFilter")
	public String deleteFilter() {
		Map<String, Object> param = RequestUtil.getPageParam();
    	return operationalInfoMService.deleteFilter(param);
	}
    
    /**
     * 查询字段类型
     * @return 字段类型
     */
    @ResponseBody
   	@RequestMapping("queryColumnTypeByCondition")
   	public String queryColumnTypeByCondition() {
   		Map<String, Object> param = RequestUtil.getPageParam();
       	return operationalInfoMService.queryColumnTypeByCondition(param);
   	}
    
    /**
     * 查询字段名称
     * @return 字段名称
     */
    @ResponseBody
   	@RequestMapping("queryColumnNameByCondition")
   	public String queryColumnNameByCondition() {
   		Map<String, Object> param = RequestUtil.getPageParam();
       	return operationalInfoMService.queryColumnNameByCondition(param);
   	}
}
