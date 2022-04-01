/*
 * 文件名：InvestorsHoldInfoRegist001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.svreport.pbc.reportdetail.service;


import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.pbc.reportdetail.dao.PbcMonthReportDetailAssetMapper;
import com.joyintech.tams.svreport.pbc.reportdetail.dto.PbcMonthReportDetailAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * 银行月度报表登记service
 *
 * @author yuluan
 * @version 1.0 2019年2月20日
 * @since 1.0
 */
@Service
public class PbcMonthReportDetailAsset001Service extends CommonExportService<PbcMonthReportDetailAsset> {
	@Autowired
	private PbcMonthReportDetailAssetMapper pbcMonthReportDetailAssetMapper;


	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 查询页面数据
	 *
	 * @param pageParam
	 *            查询条件
	 * @return 结果列表
	 * @see
	 * @since 1.0
	 */
	public Page<PbcMonthReportDetailAsset> searchPage(Map<String, Object> pageParam) {
		//将发送状态改为空
		int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
		int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
		if(StringUtil.isNotEmpty(pageParam.get("prodId"))){
			pageParam.put("prodIds",pageParam.get("prodId").toString().split(","));
		}
		if(StringUtil.isNotEmpty(pageParam.get("assetCode"))){
			pageParam.put("assetCodes",pageParam.get("assetCode").toString().split(","));
		}
		if(StringUtil.isNotEmpty(pageParam.get("enddate"))){
			pageParam.put("enddateDate",DateUtil.parse(DateUtil.getLastDayOfLayMonth(DateUtil.parse(pageParam.get("enddate")+"01"))));
		}
		Page<PbcMonthReportDetailAsset> page = queryForPage(
				pagenum,legth,()->pbcMonthReportDetailAssetMapper.selectDetailList(pageParam));
		return page;
	}

	public List<PbcMonthReportDetailAsset> selectForExport(Map<String, Object> pageParam) {
		//将发送状态改为空
		if(StringUtil.isNotEmpty(pageParam.get("prodId"))){
			pageParam.put("prodIds",pageParam.get("prodId").toString().split(","));
		}
		if(StringUtil.isNotEmpty(pageParam.get("assetCode"))){
			pageParam.put("assetCodes",pageParam.get("assetCode").toString().split(","));
		}
		if(pageParam.containsKey("keyList")){
			pageParam.put("keysList",
					StringUtil.splitToList(",",(String) pageParam.get("keyList")));
		}
		if(StringUtil.isNotEmpty(pageParam.get("enddate"))){
			pageParam.put("enddateDate", DateUtil.parse(DateUtil.getLastDayOfLayMonth(DateUtil.parse(pageParam.get("enddate")+"01"))));
		}
		List<PbcMonthReportDetailAsset> list = pbcMonthReportDetailAssetMapper.selectDetailList(pageParam);
		return list;
	}

	@PostMapping("getAssetCode")
	public List<SelectOptionDto> getAssetCode( Map<String,Object> paramMap) {
		return pbcMonthReportDetailAssetMapper.getAssetCode(paramMap);
	}


 	
}
