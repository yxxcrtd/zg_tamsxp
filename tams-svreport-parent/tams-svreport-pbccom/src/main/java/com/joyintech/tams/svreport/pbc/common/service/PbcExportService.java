/*
 * 文件名：InvestorsHoldInfoRegist001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.svreport.pbc.common.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.joyintech.framework.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.export.paginator.domain.ITableRow;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.system.instregbankcode.dao.SysInstRegBankCode001Mapper;
import com.joyintech.tams.report.system.instregbankcode.dto.SysInstRegBankCode001Dto;
import com.joyintech.tams.svreport.pbc.common.annotation.TemplateName;
import com.joyintech.tams.svreport.pbc.common.util.PbcExcelUtil;
import com.joyintech.tams.svreport.pbc.common.util.XlsxToHtml;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;

/**
 * 人行报表导出预览共通service
 *
 * @author zhangzhongliang
 * @version 1.0 2019年3月05日
 * @since 1.0
 */
@Service
public class PbcExportService extends BaseService<ITableRow>{
	private String templateName;
	
	@Autowired
    private SysInstRegBankCode001Mapper sysInstRegBankCode001Mapper;
	
    /**
     * 设置公共参数
     *
     * @see
     * @since 1.0
     */
	@PostConstruct
	protected void initCommonParams() {
		if (StringUtils.isEmpty(templateName)) {
			TemplateName anno = this.getClass().getAnnotation(TemplateName.class);
			if (null != anno) {
				// 从注解中获取pageId
				templateName = anno.value();
			}
		}
	}

	public File export(Map<String,Object> param) {
		//生成报表数据map
		Map<Object, Object> data = createDataMS(param);
		String fileName =null;
		if(data.get("report") instanceof PbcMonthReport){
			PbcMonthReport pbcReport = (PbcMonthReport) data.get("report");
			fileName = pbcReport.getReportname() +".xls";
		}else{
			Map pbcReport = (Map)data.get("report");
			fileName = pbcReport.get("reportname") +".xls";
			if(null!=pbcReport.get("enddate")){
				Date enddate = new Date(Long.parseLong(pbcReport.get("enddate").toString()));
				pbcReport.put("enddate",enddate);
			}
		}
		//生成文件
//		File depositfile = PbcExcelUtil.createFile(data, templateName, fileName);
		File depositfile = TisUtil.createFile(PbcExcelUtil.convertMapKey2String(data), "pbc/"+templateName, fileName);
		return depositfile;
//		return ExportUtil.export(fileName, depositfile, true);
	}

	public String priview(Map<String,Object> param) {
		String template = templateName;
		if(param.get("templateChange")!=null){
			template = (String)param.get("templateChange");
		}
		// 生成报表数据map
		Map<Object, Object> data = createDataMS(param);
		// 查询不到数据
		if (data.get("report") == null){
		    return "fail";
		}
		if(data.get("report") instanceof Map){
			Map pbcReport = (Map)data.get("report");
			if(null!=pbcReport.get("enddate")){
				Date enddate = new Date(Long.parseLong(pbcReport.get("enddate").toString()));
				pbcReport.put("enddate", DateUtil.format(enddate));
			}
			if(!MultilevelDataAuthorityUtil.isEnabled()) {
        		//银行模式
                SysInstRegBankCode001Dto bankDto=sysInstRegBankCode001Mapper.queryForPageWithMode(null);
            	if(null!=bankDto){
            		
            		if(!StringUtil.isNotEmpty(pbcReport.get("socialCreditCode"))){
        				pbcReport.put("socialCreditCode",bankDto.getInsitituteCode());
        			}
            		
            		if(!StringUtil.isNotEmpty(pbcReport.get("finInstitutionCode"))){
        				pbcReport.put("finInstitutionCode",bankDto.getRegistBankCode());
        			}
            	}
            }
			
		}
		// 表格宽度默认100%
		int tableWidth = 100;
		if (param.get("tableWidth") != null){
		    tableWidth = (int) param.get("tableWidth");
		}
        // sheet页数默认1
        int sheetNum = 1;
        if (param.get("sheetNum") != null){
            sheetNum = (int) param.get("sheetNum");
        }
		// 生成临时文件
		String tempFileName = template + new Date().getTime();
//		File depositfile = PbcExcelUtil.createFile(data, template, tempFileName);
		File depositfile = TisUtil.createFile(PbcExcelUtil.convertMapKey2String(data), "pbc/"+template, tempFileName);
		
		return XlsxToHtml.readExcelToHtml(depositfile, true, true,sheetNum,tableWidth);
	}

	public Map<Object, Object> createData(Map<String,Object> param) {
		return new HashMap<Object, Object>();
	}
	
	public Map<Object, Object> createDataMS(Map<String,Object> param) {
		return new HashMap<Object, Object>();
	}

}
