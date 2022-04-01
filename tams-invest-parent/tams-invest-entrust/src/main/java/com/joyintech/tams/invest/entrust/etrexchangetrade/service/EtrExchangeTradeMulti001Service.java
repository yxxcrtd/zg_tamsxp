/*
 * 文件名：EtrTradePlaceService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：chendijun
 * 修改时间：2020年7月1日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.entrust.etrexchangetrade.service;

import com.joyintech.framework.base.entity.ProcessMark;
import com.joyintech.framework.export.CommonExportService;

import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.base.dao.FinProductMapper;
import com.joyintech.tams.common.base.entity.FinProduct;
import com.joyintech.tams.common.base.entity.MutiptlPortfolioDetail;
import com.joyintech.tams.component.business.common.service.CommonBusinessService;
import com.joyintech.tams.component.business.common.service.DateCalService;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.dto.BaseEtrInvestInfoDto;
import com.joyintech.tams.invest.common.trade.AllTradeCommonUtil;
import com.joyintech.tams.invest.common.trade.dto.EtrSecPositionDto;
import com.joyintech.tams.invest.entrust.common.dto.EtrFinProductCommonDto;
import com.joyintech.tams.invest.entrust.common.service.EtrCommonService;
import com.joyintech.tams.invest.entrust.etrexchangetrade.dto.*;

import com.joyintech.tams.systemtools.interf.IPlatformDate;
import com.joyintech.workflow.engine.WorkflowContext;
import com.joyintech.workflow.engine.constant.OperationType;

import com.joyintech.workflow.engine.exception.WorkFlowException;
import com.joyintech.workflow.engine.service.BaseProcessService;

import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.util.LoggingUtils;

import java.math.BigDecimal;
import java.util.*;


/**
 * 交易所多组合指令交易
 *
 * @author chendijun
 * @version 1.0 2020年7月1日
 * @see EtrExchangeTradeMulti001Service
 * @since 1.0
 */
@Service
public class EtrExchangeTradeMulti001Service extends CommonExportService<EtrExchangeTradeMulti001Dto>
		implements BaseProcessService<EtrExchangeTradeMulti001Dto> {

	@Autowired
	private EtrCommonService etrCommonService;

	/**
	 * 日期相关
	 */
	@Autowired
	private DateCalService dateCalService;

	/**
	 * 平台日获取接口
	 */
	@Autowired(required=false)
	private IPlatformDate iPlatformDate;

	@Autowired
	private FinProductMapper finProductMapper;

	@Override
	public void doComplete(EtrExchangeTradeMulti001Dto entity, OperationType operationType) {
		restTemplate.postForObject(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/doCompleteMulti", entity,
				Integer.class);
		doDeleteFlow(entity.getWorkflowId());
	}

	@Override
	public void doBack(EtrExchangeTradeMulti001Dto entity) {
		//产品化_所有投资指令预占需求_场内交易  add by zhangpei 20201128 start
		AllTradeCommonUtil.deletePosition(null, entity.getEntrustId());
		//产品化_所有投资指令预占需求_场内交易  add by zhangpei 20201128 end
	}

	@Override
	public void doUpdate(EtrExchangeTradeMulti001Dto entity){
		Map<String,Object> map = new HashMap<>();
		// 新增交易对手席位
		if (StringUtil.isNotEmpty(entity.getCounterId()) && StringUtil.isNotEmpty(entity.getSeatId())){
			map.put("counterId",entity.getCounterId());
			map.put("counterSeatId",entity.getSeatId());
			restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME + "/InterfSysTrdConfig/insertCounterSeatId", map,
					Object.class);
		}
		// 新增交易对手交易员
		if (StringUtil.isNotEmpty(entity.getCounterId()) && StringUtil.isNotEmpty(entity.getCounterTrader())){
			map.put("counterId",entity.getCounterId());
			map.put("counterTraderId",entity.getCounterTrader());
			restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME + "/InterfSysTrdConfig/insertCounterTraderId", map,
					Object.class);
		}
		if (!StringUtil.equals("revoke", entity.getOperateType()) && !StringUtil.equals("delete", entity.getOperateType())) {
			// 先删除流程中数据
			doDeleteFlow(entity.getWorkflowId());
			// 重新insert
			doInsertFlow(entity);
			//保存不需要处理头寸
			if(!StringUtil.equals(entity.getOperateType(),"save")){
				//产品化_所有投资指令预占需求_场内交易
				doInsetOrUpdateForEtrPositionHold(entity);
				//产品化_所有投资指令预占需求_场内交易
			}
			ProcessMark newEntity;
			try {
				newEntity = WorkFlowUtil.getBusinessObjectFromRequest(EtrExchangeTrade001Dto.class, true);
				newEntity.setWorkflowId(entity.getWorkflowId());
				newEntity.setStatus(entity.getStatus());
				newEntity.setBusinessData(JsonUtils.object2Json(entity));
				WorkflowContext.getProcessMarkService().insertIntoProcessMark(newEntity);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void doDelete(String workflowId) {
		doDeleteFlow(workflowId);
	}

	@Override
	public void doInsert(EtrExchangeTradeMulti001Dto entity,OperationType type){
		// nothing to do just for aspect
		Map<String,Object> map = new HashMap<>();
		// 新增交易对手席位
		if (StringUtil.isNotEmpty(entity.getCounterId()) && StringUtil.isNotEmpty(entity.getSeatId())){
			map.put("counterId",entity.getCounterId());
			map.put("counterSeatId",entity.getSeatId());
			restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME + "/InterfSysTrdConfig/insertCounterSeatId", map,
					Object.class);
		}
		// 新增交易对手交易员
		if (StringUtil.isNotEmpty(entity.getCounterId()) && StringUtil.isNotEmpty(entity.getCounterTrader())){
			map.put("counterId",entity.getCounterId());
			map.put("counterTraderId",entity.getCounterTrader());
			restTemplate.postForObject(ServiceApplication.BASICDATA_SERVICE_NAME + "/InterfSysTrdConfig/insertCounterTraderId", map,
					Object.class);
		}
		if ("save".equals(entity.getOperateType())) {
			return;
		}
		doInsertFlow(entity);
		//产品化_所有投资指令预占需求_场内交易  add by zhangpei 20201128 start
		doInsetOrUpdateForEtrPositionHold(entity);
		//产品化_所有投资指令预占需求_场内交易  add by zhangpei 20201128 end
	}
	/**
	 * 流程判断
	 */
	@Override
	public Map<String, Object> getRuntimeVariables(ProcessMark entity, String taskId) throws WorkFlowException {
		EtrExchangeTrade001Dto dto = this.getBussinessEntity(entity);
		CommonBusinessService commonBusinessService = SpringContextUtil.getBean(CommonBusinessService.class);
		String 平安资产类型 = null;
		String finprodId = dto.getFinprodId();
		if(StringUtil.equals(dto.getFinprodType2(), DictCodeListExt1.金融产品类型_投管用_债券.getValue())) {
			平安资产类型 = DictCodeList.平安资产类型_债券.getValue();
		}
		else if(StringUtil.equals(dto.getFinprodType2(), DictCodeListExt1.金融产品类型_投管用_基金.getValue())) {
			平安资产类型 = DictCodeList.平安资产类型_基金.getValue();
		}
		else if(StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_股票.getValue())) {
			平安资产类型 = DictCodeListExt2.平安资产类型_股票.getValue();
		}
		else {
			平安资产类型 = DictCodeList.平安资产类型_质押式回购.getValue();
			finprodId = null;
		}

		if(taskId != null) {
			try {
				Map<String, Object> params = CommonBusinessService.setParams(dto.getCounterId(), dto.getEntrustAmt(), LoginUserUtil.getUserName(),
						平安资产类型, "", dto.getTradeMarket(), dto.getPs()
						, finprodId, entity.getWorkflowId()
				);
				params.put("riskTrialRetDto", dto.getRiskTrialRetDto());
				return commonBusinessService.是否需要上级审批_委托用(params);
			}catch (WorkFlowException e) {
				// 未设置授权或设置成不授权时，抛出异常，当前用户只能退回
				throw new WorkFlowException(e);
			}
		}
		return null;
	}

	private void doDeleteFlow(String workflowId) {
		Map<String,Object> param = new HashMap<>();
		param.put("workflowId",workflowId);
		restTemplate.postForObject(
				ServiceApplication.ENTRUST_SERVICE_NAME + "/common/entrustcommonbase/doDeleteEtrFlow", param,
				Boolean.class);
	}

	private void doInsertFlow(EtrExchangeTradeMulti001Dto dto) {
		BaseEtrInvestInfoDto entity = restTemplate.postForObject(
				ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/buildMultiEtrFlow", dto,
				BaseEtrInvestInfoDto.class);
		// 设置指令创建来源
		entity.getEtrEntrustAdd().setEntrustCreateSource(DictCodeListExt3.指令创建来源_场内实时.getValue());
		entity.getEtrEntrust().setEntrustStatus(DictCodeListExt3.委托状态_流程中.getValue());
		entity.setWorkFlowId(dto.getWorkflowId());
		restTemplate.postForObject(
				ServiceApplication.ENTRUST_SERVICE_NAME + "/common/entrustcommonbase/doInsertEtrFlow", entity,
				Boolean.class);
	}

	/**
	 * 获取多组合数
	 * @param map
	 * @return
	 */
	public Page<SelectOptionDto> selectPortfolioTreeId(Map<String,Object> map) {
		return restTemplate.postForPage(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/selectPortfolioTreeId", map,
				SelectOptionDto.class);
	}

	public List<SelectOptionDto> selectPortfolioId(Map<String,Object> map){
		return restTemplate.postForList(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/selectPortfolioId", map,
				SelectOptionDto.class);
	}

	/**
	 * 查询多组合明细列表
	 * @param param
	 * @return
	 */
	public List<EtrExchangeTradeMultiPtl001Dto> selectPortfolioTreeDetail(Map<String, Object> param) {
		List<EtrExchangeTradeMultiPtl001Dto> list = new ArrayList<>();
		if(StringUtil.isNotEmpty(param.get("workflowId"))) {
			EtrExchangeTradeMulti001Dto entity = this.getBussinessEntityByWorkflowId((String)param.get("workflowId"));
			//如果是单组合的流程数据则，多组合置空
			if(StringUtil.isNotEmpty(entity) && StringUtil.equals(entity.getIsMulti(),DictCodeList.是否_是.getValue())) {
				list = entity.getTradeMultiPtl001Dtos();
			}
		}
		else if(StringUtil.isNotEmpty(param.get("portfolioTreeId"))) {
			Date platformDate = DateUtil.platDateFromCache();
			param.put("ptlDate",DateUtil.format(platformDate));
			param.put("userId", LoginUserUtil.getUserName());

			String tradeMarket = (String) param.get("tradeMarket");

			String calId = StringUtil.equals(tradeMarket,DictCodeListExt1.交易场所_上海证券交易所.getValue())?DictCodeList.交易日历_上海交易所日历.getValue():DictCodeList.交易日历_深圳交易所日历.getValue();
			list = restTemplate.postForList(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/selectPortfolioTreeDetail", param,
					EtrExchangeTradeMultiPtl001Dto.class);
			if(!StringUtil.isEmptyList(list)) {
				list.stream().forEach(item->{
					Map<String, Object> paramMap = new HashMap<String,Object>();
					paramMap.put("portfolioId",item.getPortfolioId());
					paramMap.put("vdate",DateUtil.format(platformDate));
					paramMap.put("acctNo",item.getAcctNo());
					paramMap.put("calId",calId);
					paramMap.put("ccy",StringUtil.isNotEmpty(item.getCcy())? item.getCcy():DictCodeList.币种_人民币_CNY.getValue());

					//可用现金
					Map<String, Object> cash = etrCommonService.getProAvailableCash(paramMap);
					if(StringUtil.isNotEmpty(cash)) {
						//组合T日可用现金
						item.setAvailableCashT(StringUtil.isNotEmpty(cash.get("cashT"))?(BigDecimal)cash.get("cashT"): BigDecimal.ZERO);
						//组合T+1日可用现金
						item.setAvailableCashT1(StringUtil.isNotEmpty(cash.get("cashT1"))?(BigDecimal)cash.get("cashT1"): BigDecimal.ZERO);
					}
					if(StringUtil.isNotEmpty(param.get("finprodId"))) {
						EtrSecPositionDto etrSecPositionDto = new EtrSecPositionDto();
						etrSecPositionDto.setFinprodId((String)paramMap.get("finprodId"));
						etrSecPositionDto.setPortfolioId((String)paramMap.get("portfolioId"));
						etrSecPositionDto.setCcy(item.getCcy());

						//组合T日可用持仓
						etrSecPositionDto.setCdate(platformDate);
						item.setAvailablePositionsT(etrCommonService.getAssetAvailablePosition(etrSecPositionDto));

						if(StringUtil.isNotEmpty(calId)){
							List<String> holidayList = dateCalService.getDealCalendarHoliday(DateUtil.parse(DateUtil.format(platformDate)),DateUtil.addDay(DateUtil.parse(DateUtil.format(platformDate)), 15),calId);
							Date tradeDay = dateCalService.calculationTradingDay(DateUtil.parse(DateUtil.format(platformDate)), 1, holidayList, "1");
							etrSecPositionDto.setCdate(tradeDay);
						}
						//组合T+1日可用持仓
						item.setAvailablePositionsT1(etrCommonService.getAssetAvailablePosition(etrSecPositionDto));
					}

				});
			}
		}
		return list != null?list:new ArrayList<EtrExchangeTradeMultiPtl001Dto>();
	}
	/**
	 * 修改/双击查看详情查询信息 <功能详细描述>
	 *
	 * @param entrustId
	 * @return
	 * @see
	 * @since 1.0
	 */
	public EtrExchangeTradeMulti001Dto getEtrExchangeTradeMultiInfo(String entrustId) {
		return restTemplate.postForObject(
				ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/getEtrExchangeTradeMultiInfo",entrustId,
				EtrExchangeTradeMulti001Dto.class);
	}

	/**
	 * 获取多组合分配资金账户
	 * @param paraMap
	 * @return
	 */
	public Page<SelectOptionDto> selectMultiAcctNo(Map<String,Object> paraMap){
		return restTemplate.postForPage(
				ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/selectMultiAcctNo",paraMap,
				SelectOptionDto.class);
	}
	/**
	 * 查询组合+资金账户+交易场所对应的证券账户（多组合）
	 * @param paraMap
	 * @return
	 */
	public String selectMultiSecAcctId(Map<String,Object> paraMap) {
		return restTemplate.postForObject(
				ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/selectMultiSecAcctId",paraMap,
				String.class);
	}
	/**
	 * 计算逻辑
	 * @param dto
	 * @return
	 */
	public EtrExchangeTradeMulti001Dto calc(EtrExchangeTradeMulti001Dto dto) {
		if(StringUtil.isNotEmpty(dto.getTradeMultiPtl001Dtos())) {
			BigDecimal price = dto.getPrice();
			//汇总权重比例
			BigDecimal totalProportion = BigDecimal.ZERO;
			//所有组合昨净资产
			BigDecimal totalProductNetValue = dto.getTradeMultiPtl001Dtos().stream().map(EtrExchangeTradeMultiPtl001Dto::getProductNetValue).reduce(BigDecimal::add).get();
			//所有组合可用金额
			BigDecimal totalAvailableCashT = dto.getTradeMultiPtl001Dtos().stream().map(EtrExchangeTradeMultiPtl001Dto::getAvailableCashT).reduce(BigDecimal::add).get();
			//所有组合持仓数量
			BigDecimal totalAvailablePositionsT = dto.getTradeMultiPtl001Dtos().stream().map(EtrExchangeTradeMultiPtl001Dto::getAvailablePositionsT).reduce(BigDecimal::add).get();
			//所有行原分配比例
			BigDecimal totalWeightProportion =  dto.getTradeMultiPtl001Dtos().stream().map(EtrExchangeTradeMultiPtl001Dto::getProportion).reduce(BigDecimal::add).get();
			for(EtrExchangeTradeMultiPtl001Dto itemDto : dto.getTradeMultiPtl001Dtos()) {
				//涉及债券、股票和基金的买入和卖出
				if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_单笔数量.getValue())) {
					itemDto.setEntrustNum(dto.getTargetValue());
					//资产名称对应的资产类型为股票和基金时，组合分配列表.指令金额=该行指令数量*组合分配列表.指令价格
					if(StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_基金.getValue())
					|| StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_股票.getValue())) {
						itemDto.setEntrustAmt(ArithmeticUtil.multiply(itemDto.getEntrustNum(),itemDto.getEntrustPrice()));
					}
					//资产名称对应的资产类型为债券时，组合分配列表.指令金额=该行指令数量*组合分配列表.净价
					else if (StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券.getValue())){
						itemDto.setEntrustAmt(ArithmeticUtil.multiply(itemDto.getEntrustNum(),itemDto.getPrice()));
					}
					dto.setTargetType(DictCodeListExt3.目标类型_数量.getValue());
				}
				//涉及债券、股票和基金的买入和卖出
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_单笔金额.getValue())) {

					itemDto.setEntrustAmt(dto.getTargetValue());
					//资产名称对应的资产类型为股票和基金时，组合分配列表.指令金额=该行指令数量*组合分配列表.指令价格
					if(StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_基金.getValue())
							|| StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_股票.getValue())) {
						itemDto.setEntrustAmt(ArithmeticUtil.divide(itemDto.getEntrustAmt(),itemDto.getEntrustPrice(),4));
					}
					//资产名称对应的资产类型为债券时，组合分配列表.指令金额=该行指令数量*组合分配列表.净价
					else if (StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券.getValue())){
						itemDto.setEntrustAmt(ArithmeticUtil.divide(itemDto.getEntrustAmt(),itemDto.getPrice(),4));
					}
					dto.setTargetType(DictCodeListExt3.目标类型_金额.getValue());
				}
				//涉及债券、股票和基金的买入和卖出
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_总数量.getValue())) {
					//每个组合分配比例值
					BigDecimal proportion = BigDecimal.ZERO;
					//先计算分配比例（这里计算是分配比例展示值，都是*100后的值）
					if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_平均分配.getValue())) {
						//平均分配比例值
						proportion = ArithmeticUtil.divide(new BigDecimal(100),dto.getTradeMultiPtl001Dtos().size(),4);

					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_组合净资产比例.getValue())) {
						proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getProductNetValue(),totalProductNetValue,6),new BigDecimal(100));
					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_组合可用金额比例.getValue())) {
						proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getAvailableCashT(),totalAvailableCashT,6),new BigDecimal(100));
					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_组合持仓数量比例.getValue())) {
						proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getAvailablePositionsT(),totalAvailablePositionsT,6),new BigDecimal(100));
					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_自定义.getValue())) {
						if(new BigDecimal(100).compareTo(totalWeightProportion) == 0) {
							proportion = itemDto.getProportion();
						}
						else {
							proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getProportion(),totalProportion,6),new BigDecimal(100));
						}
					}

					if(new BigDecimal(100).compareTo(ArithmeticUtil.doAdd(totalProportion,proportion))>=0) {
						itemDto.setProportion(proportion);
						totalProportion = ArithmeticUtil.doAdd(totalProportion,proportion);
					}
					else {
						itemDto.setProportion(ArithmeticUtil.subtract(new BigDecimal(100),totalProportion));
					}

					itemDto.setEntrustNum(ArithmeticUtil.divide(ArithmeticUtil.multiply(dto.getTargetValue(),itemDto.getProportion()),new BigDecimal(100),4));

					//资产名称对应的资产类型为股票和基金时，组合分配列表.指令金额=该行指令数量*组合分配列表.指令价格
					if(StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_基金.getValue())
							|| StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_股票.getValue())) {
						itemDto.setEntrustAmt(ArithmeticUtil.multiply(itemDto.getEntrustNum(),itemDto.getEntrustPrice()));
					}
					//资产名称对应的资产类型为债券时，组合分配列表.指令金额=该行指令数量*组合分配列表.净价
					else if (StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券.getValue())){
						itemDto.setEntrustAmt(ArithmeticUtil.multiply(itemDto.getEntrustNum(),itemDto.getPrice()));
					}

					dto.setTargetType(DictCodeListExt3.目标类型_数量.getValue());
				}
				//涉及债券、股票和基金的买入和卖出
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_总金额.getValue())) {

					//每个组合分配比例值
					BigDecimal proportion = BigDecimal.ZERO;
					//先计算分配比例（这里计算是分配比例展示值，都是*100后的值）
					if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_平均分配.getValue())) {
						//平均分配比例值
						proportion = ArithmeticUtil.divide(new BigDecimal(100),dto.getTradeMultiPtl001Dtos().size(),4);

					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_组合净资产比例.getValue())) {
						proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getProductNetValue(),totalProductNetValue,6),new BigDecimal(100));
					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_组合可用金额比例.getValue())) {
						proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getAvailableCashT(),totalAvailableCashT,6),new BigDecimal(100));
					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_组合持仓数量比例.getValue())) {
						proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getAvailablePositionsT(),totalAvailablePositionsT,6),new BigDecimal(100));
					}
					else if(StringUtil.equals(dto.getWeightType(),DictCodeListExt3.权重方式_自定义.getValue())) {
						if(new BigDecimal(100).compareTo(totalWeightProportion) == 0) {
							proportion = itemDto.getProportion();
						}
						else {
							proportion = ArithmeticUtil.multiply(ArithmeticUtil.divide(itemDto.getProportion(),totalProportion,6),new BigDecimal(100));
						}
					}

					if(new BigDecimal(100).compareTo(ArithmeticUtil.doAdd(totalProportion,proportion))>=0) {
						itemDto.setProportion(proportion);
						totalProportion = ArithmeticUtil.doAdd(totalProportion,proportion);
					}
					else {
						itemDto.setProportion(ArithmeticUtil.subtract(new BigDecimal(100),totalProportion));
					}

					itemDto.setEntrustAmt(ArithmeticUtil.divide(ArithmeticUtil.multiply(dto.getTargetValue(),itemDto.getProportion()),new BigDecimal(100),4));

					//资产名称对应的资产类型为股票和基金时，组合分配列表.指令金额=该行指令数量*组合分配列表.指令价格
					if(StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_基金.getValue())
							|| StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_股票.getValue())) {
						itemDto.setEntrustNum(ArithmeticUtil.divide(itemDto.getEntrustAmt(),itemDto.getEntrustPrice(),4));
					}
					//资产名称对应的资产类型为债券时，组合分配列表.指令金额=该行指令数量*组合分配列表.净价
					else if (StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券.getValue())){
						itemDto.setEntrustNum(ArithmeticUtil.divide(itemDto.getEntrustAmt(),itemDto.getPrice(),4));
					}

					dto.setTargetType(DictCodeListExt3.目标类型_金额.getValue());
				}
				//涉及债券、股票和基金的买入和卖出
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_持仓数量比例.getValue())) {
					itemDto.setEntrustNum(ArithmeticUtil.divide(ArithmeticUtil.multiply(itemDto.getAvailablePositionsT(),dto.getTargetValue()),new BigDecimal(100),4));
					//资产名称对应的资产类型为股票和基金时，组合分配列表.指令金额=该行指令数量*组合分配列表.指令价格
					if(StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_基金.getValue())
							|| StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_股票.getValue())) {
						itemDto.setEntrustAmt(ArithmeticUtil.multiply(itemDto.getEntrustNum(),itemDto.getEntrustPrice()));
					}
					//资产名称对应的资产类型为债券时，组合分配列表.指令金额=该行指令数量*组合分配列表.净价
					else if (StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券.getValue())){
						itemDto.setEntrustAmt(ArithmeticUtil.multiply(itemDto.getEntrustNum(),itemDto.getPrice()));
					}
					dto.setTargetType(DictCodeListExt3.目标类型_数量.getValue());
				}
				//涉及股票买入和逆回购
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_可用金额比例.getValue())) {
					itemDto.setEntrustAmt(ArithmeticUtil.divide(ArithmeticUtil.multiply(itemDto.getAvailableCashT(),dto.getTargetValue()),new BigDecimal(100),4));

					if(StringUtil.equals(dto.getPs(),DictCodeList.交易方向_买入.getValue())) {
						itemDto.setEntrustNum(ArithmeticUtil.divide(itemDto.getEntrustAmt(),itemDto.getEntrustPrice(),4));
					}
					else if (StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_逆回购.getValue())) {
						itemDto.setEntrustNum(ArithmeticUtil.divide(itemDto.getEntrustAmt(),new BigDecimal(100),4));
					}

					dto.setTargetType(DictCodeListExt3.目标类型_金额.getValue());
				}
				//只有正回购
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_可用标准券比例.getValue())) {
					Map<String, Object> pageParam = new HashMap<>();
					pageParam.put("portfolioId",itemDto.getPortfolioId());
					pageParam.put("tradeMarket",dto.getTradeMarket());
					pageParam.put("userId", SecurityUtil.getLoginUser().getUserName());
					pageParam.put("date", iPlatformDate.getSysDate());

					List<EtrExchangeTrade002Dto> findZqzyPositionsList = restTemplate.postForList(
							ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/findZqzyPositionsList",pageParam,EtrExchangeTrade002Dto.class);
					BigDecimal secNum = BigDecimal.ZERO;
					if(StringUtil.isEmptyList(findZqzyPositionsList)) {
						secNum = findZqzyPositionsList.stream().map(EtrExchangeTrade002Dto::getCanUseSecNum).reduce(BigDecimal::add).get();
					}

					itemDto.setEntrustNum(ArithmeticUtil.divide(ArithmeticUtil.multiply(secNum,dto.getTargetValue()),new BigDecimal(100),4));
					itemDto.setEntrustAmt(ArithmeticUtil.multiply(itemDto.getEntrustNum(),new BigDecimal(100)));
					dto.setTargetType(DictCodeListExt3.目标类型_数量.getValue());
				}
				//涉及债券、股票和基金的买入、卖出和调整到
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_产品净资产比例.getValue())) {
					if(StringUtil.equals(dto.getPs(),DictCodeList.交易方向_买入.getValue())
							|| StringUtil.equals(dto.getPs(),DictCodeList.交易方向_卖出.getValue())) {

						itemDto.setEntrustAmt(ArithmeticUtil.divide(ArithmeticUtil.multiply(itemDto.getAvailableCashT(),dto.getTargetValue()),new BigDecimal(100),4));
						//资产名称对应的资产类型为股票和基金时，组合分配列表.指令金额=该行指令数量*组合分配列表.指令价格
						if(StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_基金.getValue())
								|| StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_股票.getValue())) {
							itemDto.setEntrustNum(ArithmeticUtil.divide(itemDto.getEntrustAmt(),itemDto.getEntrustPrice(),4));
						}
						//资产名称对应的资产类型为债券时，组合分配列表.指令金额=该行指令数量*组合分配列表.净价
						else if (StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券.getValue())){
							itemDto.setEntrustNum(ArithmeticUtil.divide(itemDto.getEntrustAmt(),itemDto.getPrice(),4));
						}
					}
					else if (StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_调整到.getValue())) {

					}

					dto.setTargetType(DictCodeListExt3.目标类型_金额.getValue());
				}
				//涉及回购的正回购、逆回购和调整到
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_目标可用金额.getValue())) {
					BigDecimal amt = BigDecimal.ZERO;
					if(StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_正回购.getValue())) {
						amt = itemDto.getAvailableCashT().compareTo(dto.getTargetValue())>0?BigDecimal.ZERO:ArithmeticUtil.subtract(dto.getTargetValue(),itemDto.getAvailableCashT());
					}
					else if(StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_逆回购.getValue())) {
						amt = itemDto.getAvailableCashT().compareTo(dto.getTargetValue())<0?BigDecimal.ZERO:ArithmeticUtil.subtract(itemDto.getAvailableCashT(),dto.getTargetValue());
					}
					else if (StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_调整到.getValue())) {
						amt = ArithmeticUtil.subtract(dto.getTargetValue(),itemDto.getAvailableCashT()).abs();
						if(itemDto.getAvailableCashT().compareTo(dto.getTargetValue())>0) {
							itemDto.setPs(DictCodeListExt3.交易方向_逆回购.getValue());
						}
						else {
							itemDto.setPs(DictCodeListExt3.交易方向_正回购.getValue());
						}
					}
					itemDto.setEntrustAmt(amt);
					itemDto.setEntrustNum(ArithmeticUtil.divide(itemDto.getEntrustAmt(),new BigDecimal(100),4));
					dto.setTargetType(DictCodeListExt3.目标类型_金额.getValue());
				}
				//涉及质押券入库、质押券出库和出入库的调整到
				else if(StringUtil.equals(dto.getMulDistriClass(), DictCodeListExt3.场内多组合分配方式_目标标准券使用率.getValue())) {
					Map<String, Object> pageParam = new HashMap<>();
					pageParam.put("portfolioId",itemDto.getPortfolioId());
					pageParam.put("tradeMarket",dto.getTradeMarket());
					pageParam.put("userId", SecurityUtil.getLoginUser().getUserName());
					pageParam.put("date", iPlatformDate.getSysDateStr());

					List<EtrExchangeTrade002Dto> findZqzyPositionsList = restTemplate.postForList(
							ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/findZqzyPositionsList",pageParam,EtrExchangeTrade002Dto.class);
					//标准券总数量
					BigDecimal secNum = BigDecimal.ZERO;
					if(!StringUtil.isEmptyList(findZqzyPositionsList)) {
						secNum = findZqzyPositionsList.stream().map(EtrExchangeTrade002Dto::getCanUseSecNum).reduce(BigDecimal::add).get();
					}

					Map<String, String> map = (Map<String, String>)restTemplate.postForObject(
							ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/loadSecNum",
							pageParam, Map.class);
					//标准券使用率
					BigDecimal useRate = BigDecimal.ZERO;
					if(StringUtil.equals(dto.getTradeMarket(),DictCodeListExt1.交易场所_上海证券交易所.getValue())) {
						//已使用标准券数量-上交所
						BigDecimal useNum_sh = StringUtil.isNotEmpty(map.get("secNum_sh"))?new BigDecimal(map.get("secNum_sh")):BigDecimal.ZERO;
						useRate = ArithmeticUtil.divide(useNum_sh,secNum,6);
					}
					else {
						//已使用标准券数量-深交所
						BigDecimal useNum_sz = StringUtil.isNotEmpty(map.get("secNum_sz"))?new BigDecimal(map.get("secNum_sz")):BigDecimal.ZERO;;
						useRate = ArithmeticUtil.divide(useNum_sz,secNum,6);
					}

					BigDecimal faceValue = BigDecimal.ZERO;

					if(StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_质押券入库.getValue())) {
						if(useRate.compareTo(ArithmeticUtil.divide(dto.getTargetValue(),new BigDecimal(100),6))<0) {
							itemDto.setEntrustAmt(ArithmeticUtil.multiply(ArithmeticUtil.subtract(ArithmeticUtil.divide(dto.getTargetValue(),new BigDecimal(100),6),useRate),secNum));
							itemDto.setEntrustNum(ArithmeticUtil.divide(ArithmeticUtil.divide(ArithmeticUtil.multiply(itemDto.getEntrustAmt(),new BigDecimal(100)),faceValue,6),itemDto.getEntrustPrice(),6));
						}
						else {
							itemDto.setEntrustAmt(BigDecimal.ZERO);
							itemDto.setEntrustNum(BigDecimal.ZERO);
						}
					}
					else if(StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_质押券出库.getValue())) {
						if(useRate.compareTo(ArithmeticUtil.divide(dto.getTargetValue(),new BigDecimal(100),6))>0) {
							itemDto.setEntrustAmt(ArithmeticUtil.multiply(ArithmeticUtil.subtract(useRate,ArithmeticUtil.divide(dto.getTargetValue(),new BigDecimal(100),6)),secNum));
							itemDto.setEntrustNum(ArithmeticUtil.divide(ArithmeticUtil.divide(ArithmeticUtil.multiply(itemDto.getEntrustAmt(),new BigDecimal(100)),faceValue,6),itemDto.getEntrustPrice(),6));
						}
						else {
							itemDto.setEntrustAmt(BigDecimal.ZERO);
							itemDto.setEntrustNum(BigDecimal.ZERO);
						}
					}
					else if (StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_调整到.getValue())) {
						if(useRate.compareTo(ArithmeticUtil.divide(dto.getTargetValue(),new BigDecimal(100),6))>0) {
							itemDto.setEntrustAmt(ArithmeticUtil.multiply(ArithmeticUtil.subtract(useRate,ArithmeticUtil.divide(dto.getTargetValue(),new BigDecimal(100),6)),secNum));
							itemDto.setEntrustNum(ArithmeticUtil.divide(ArithmeticUtil.divide(ArithmeticUtil.multiply(itemDto.getEntrustAmt(),new BigDecimal(100)),faceValue,6),itemDto.getEntrustPrice(),6));
							itemDto.setPs(DictCodeListExt3.交易方向_质押券出库.getValue());
						}
						else {
							itemDto.setEntrustAmt(ArithmeticUtil.multiply(ArithmeticUtil.subtract(ArithmeticUtil.divide(dto.getTargetValue(),new BigDecimal(100),6),useRate),secNum));
							itemDto.setEntrustNum(ArithmeticUtil.divide(ArithmeticUtil.divide(ArithmeticUtil.multiply(itemDto.getEntrustAmt(),new BigDecimal(100)),faceValue,6),itemDto.getEntrustPrice(),6));
							itemDto.setPs(DictCodeListExt3.交易方向_质押券入库.getValue());
						}
					}
					dto.setTargetType(DictCodeListExt3.目标类型_数量.getValue());
				}
			}
		}

		return dto;
	}

    /**
     * 新建行
     * @param dto
     * @return
     */
    public EtrExchangeTradeMultiPtl001Dto addRow(EtrExchangeTradeMulti001Dto dto) {
	    EtrExchangeTradeMultiPtl001Dto newRow = new EtrExchangeTradeMultiPtl001Dto();
	    newRow.setFinprodId(dto.getFinprodId());
        newRow.setFinprodMarketId(StringUtil.isNotEmpty(dto.getFinprodMarketId())?dto.getFinprodMarketId():dto.getStockId());
        newRow.setFinprodType2(dto.getFinprodType2());
        if(!StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券质押式正回购.getValue())
		&& !StringUtil.equals(dto.getFinprodType2(),DictCodeListExt1.金融产品类型_投管用_债券质押式逆回购.getValue())) {
        	FinProduct finProduct = finProductMapper.selectByPrimaryKey(newRow.getFinprodId());
        	if(StringUtil.isNotEmpty(finProduct)) {
				newRow.setFinprodName(finProduct.getFinprodAbbr());
			}
		}

        if(!StringUtil.equals(dto.getPs(),DictCodeListExt3.交易方向_调整到.getValue())) {
            newRow.setPs(dto.getPs());
        }

	    return newRow;
    }

    /**
     *  组合分配实体转换成
     * @param dto
     * @param multiPtl001Dto
     * @return
     */
    public EtrExchangeTrade001Dto genEtrExchangeTrade001Dto(EtrExchangeTradeMulti001Dto dto,EtrExchangeTradeMultiPtl001Dto multiPtl001Dto) {
        EtrExchangeTrade001Dto etrExchangeTrade001Dto = new EtrExchangeTrade001Dto();
        BeanUtil.copyPropertiesIgnoreNull(etrExchangeTrade001Dto,dto);
        etrExchangeTrade001Dto.setPortfolioId(multiPtl001Dto.getPortfolioId());
        etrExchangeTrade001Dto.setAcctNo(multiPtl001Dto.getAcctNo());
        etrExchangeTrade001Dto.setFinprodId(multiPtl001Dto.getFinprodId());
        etrExchangeTrade001Dto.setFinprodName(multiPtl001Dto.getFinprodName());
        etrExchangeTrade001Dto.setFinprodType2(multiPtl001Dto.getFinprodType2());
        etrExchangeTrade001Dto.setAccAvailableCash(multiPtl001Dto.getAvailableCashT());
        etrExchangeTrade001Dto.setAccAvailablePos(multiPtl001Dto.getAvailablePositionsT());
        etrExchangeTrade001Dto.setPs(multiPtl001Dto.getPs());
        etrExchangeTrade001Dto.setPrice(multiPtl001Dto.getPrice());
        etrExchangeTrade001Dto.setFprice(multiPtl001Dto.getFprice());
        etrExchangeTrade001Dto.setYield(multiPtl001Dto.getYield());
        etrExchangeTrade001Dto.setEntrustPrice(multiPtl001Dto.getEntrustPrice());
        etrExchangeTrade001Dto.setEntrustAmt(multiPtl001Dto.getEntrustAmt());
        etrExchangeTrade001Dto.setEntrustNum(multiPtl001Dto.getEntrustNum());
		etrExchangeTrade001Dto.setTargetType(dto.getTargetType());

        return etrExchangeTrade001Dto;
    }

	/**
	 * 插入和修改时，指令持仓流水 、现金流水处理
	 * add by zhangpei
	 * 20201128
	 * @param dto
	 */
	public void doInsetOrUpdateForEtrPositionHold(EtrExchangeTradeMulti001Dto dto) {
		List<EtrExchangeTrade001Dto> EtrExchangeTrade001DtoList = new ArrayList<>();
		for (EtrExchangeTradeMultiPtl001Dto item:dto.getTradeMultiPtl001Dtos()) {
			EtrExchangeTrade001Dto etrExchangeTrade001Dto = this.genEtrExchangeTrade001Dto(dto,item);
			EtrExchangeTrade001DtoList.add(etrExchangeTrade001Dto);
		}
		restTemplate.postForObject(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/doInsetOrUpdateForEtrPositionHoldMulti", EtrExchangeTrade001DtoList, Integer.class);
	}

	/** 多组合详情
	 *
	 * @param pageParam 页面参数
	 * @return 多组合详情
	 * @see
	 * @since 1.0
	 **/
	public EtrExchangeTradeMulti001Dto selectMultDetail(Map<String, Object> pageParam) {
		EtrExchangeTradeMulti001Dto dto = restTemplate.postForObject(
				ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrExchangeTrade/selectMultDetail",
				pageParam, EtrExchangeTradeMulti001Dto.class);
		if(!StringUtil.isEmptyList(dto.getTradeMultiPtl001Dtos())) {
			Date platformDate = DateUtil.platDateFromCache();
			String calId = StringUtil.equals(dto.getTradeMarket(),DictCodeListExt1.交易场所_上海证券交易所.getValue())?DictCodeList.交易日历_上海交易所日历.getValue():DictCodeList.交易日历_深圳交易所日历.getValue();
			dto.getTradeMultiPtl001Dtos().stream().forEach(item->{
				Map<String, Object> paramMap = new HashMap<String,Object>();
				paramMap.put("portfolioId",item.getPortfolioId());
				paramMap.put("vdate",DateUtil.format(platformDate));
				paramMap.put("acctNo",item.getAcctNo());
				paramMap.put("calId",calId);
				paramMap.put("ccy",StringUtil.isNotEmpty(item.getCcy())? item.getCcy():DictCodeList.币种_人民币_CNY.getValue());

				//可用现金
				Map<String, Object> cash = etrCommonService.getProAvailableCash(paramMap);
				if(StringUtil.isNotEmpty(cash)) {
					//组合T日可用现金
					item.setAvailableCashT(StringUtil.isNotEmpty(cash.get("cashT"))?(BigDecimal)cash.get("cashT"): BigDecimal.ZERO);
					//组合T+1日可用现金
					item.setAvailableCashT1(StringUtil.isNotEmpty(cash.get("cashT1"))?(BigDecimal)cash.get("cashT1"): BigDecimal.ZERO);
				}
				if(StringUtil.isNotEmpty(dto.getFinprodId())) {
					EtrSecPositionDto etrSecPositionDto = new EtrSecPositionDto();
					etrSecPositionDto.setFinprodId((String)paramMap.get("finprodId"));
					etrSecPositionDto.setPortfolioId(item.getPortfolioId());
					etrSecPositionDto.setCcy(item.getCcy());

					//组合T日可用持仓
					etrSecPositionDto.setCdate(platformDate);
					item.setAvailablePositionsT(etrCommonService.getAssetAvailablePosition(etrSecPositionDto));

					if(StringUtil.isNotEmpty(calId)){
						List<String> holidayList = dateCalService.getDealCalendarHoliday(DateUtil.parse(DateUtil.format(platformDate)),DateUtil.addDay(DateUtil.parse(DateUtil.format(platformDate)), 15),calId);
						Date tradeDay = dateCalService.calculationTradingDay(DateUtil.parse(DateUtil.format(platformDate)), 1, holidayList, "1");
						etrSecPositionDto.setCdate(tradeDay);
					}
					//组合T+1日可用持仓
					item.setAvailablePositionsT1(etrCommonService.getAssetAvailablePosition(etrSecPositionDto));
				}

			});
		}
		return dto;
	}
}
