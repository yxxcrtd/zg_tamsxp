/*
 * 文件名：EtrTradePlace001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2021年12月20日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.exchange.entrust.etrexchange.agreementbond.controller;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.business.common.dto.CfetsExportRequestDto;
import com.joyintech.tams.business.common.pojo.Message;
import com.joyintech.tams.business.common.util.ExcelUitl;
import com.joyintech.tams.exchange.entrust.etrexchange.agreementbond.dto.EtrExchangeFixedIncomeResponse;
import com.joyintech.tams.exchange.entrust.etrexchange.agreementbond.service.EtrExchangeTradeGd002Service;
import com.joyintech.tams.exchange.entrust.etrexchange.common.dto.EtrExchangeTradeExportCommonRequest;
import com.joyintech.tams.exchange.entrust.etrexchange.hqjj.dto.EtrExchangeTradeQuotationRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 交易所指令交易（债券股票回购基金场内指令交易）
 * @author meifaxin
 * @version 1.0 2021年12月20日
 * @see EtrExchangeAgreeBond002Controller
 * @since 1.0
 */
@Controller
@PageId("EtrExchangeAgreeBond002")
@RequestMapping("/etrexchange/agreementbond/EtrExchangeAgreeBond002")
@Api(tags = "协议现券持仓指令")
public class EtrExchangeAgreeBond002Controller extends BaseController{

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());



	@Autowired
	private EtrExchangeTradeGd002Service etrExchangeTradeGd002Service;




	/**
	 * 查询固定收益行情数据
	 * <一句话功能简述>
	 * @return
	 * @see
	 * @since 1.0
	 */
	@ApiOperation(value="查询固定收益行情数据",notes = "查询固定收益行情数据")
	@ResponseBody
	@PostMapping("findFixedIncomePage")
	public Message<Page<EtrExchangeFixedIncomeResponse>> findFixedIncomePage(@RequestBody EtrExchangeTradeQuotationRequest request){
		try {
			Page<EtrExchangeFixedIncomeResponse> page = etrExchangeTradeGd002Service.findFixedIncomePage(request);
			return new Message<Page<EtrExchangeFixedIncomeResponse>>().success().setData(page);
		}catch (Exception e) {
			logger.error("查询指令管理分页数据异常",e);
			return new Message<Page<EtrExchangeFixedIncomeResponse>>().fail().setMessage("查询固定收益行情数据异常");
		}
	}


	/**
	 * 查询交易行情信息
	 * <一句话功能简述>
	 * @return
	 * @see
	 * @since 1.0
	 */
	@ApiOperation(value="导出交易行情信息",notes = "导出交易行情信息")
	@ResponseBody
	@PostMapping("exportFixedIncomePage")
	public void exportFixedIncomePage(@RequestBody CfetsExportRequestDto request, HttpServletResponse response){
		// 导出
		ExcelUitl.doExport(request, response, "交易行情", () -> {
			EtrExchangeTradeQuotationRequest dto = request.getDto(EtrExchangeTradeQuotationRequest.class);
			List dataList = etrExchangeTradeGd002Service.findFixedIncomePage(dto).getRoot();
			return dataList;
		});

	}




}
	
