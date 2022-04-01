/*
 * 文件名：EtrBondFirstBiddingController.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：yangjm
 * 修改时间：2019年5月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.service.entrust.etrexchangetrade.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.service.entrust.etrexchangetrade.dto.*;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.invest.common.dto.BaseEtrInvestInfoDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.ReflectUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.service.entrust.etrexchangetrade.service.EtrExchangeTradeService;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.trade.dto.FinCashPlanDto;
import com.joyintech.tams.invest.common.trade.dto.SecInfoModelDto;

/**
 * 场内交易委托
 * 
 * @author zhangnian
 * @version 1.0 2019年10月24日
 * @see EtrExchangeTradeController
 * @since 1.0
 */
@RestController("EtrExchangeTradeCtr_dtx")
@RequestMapping("/EtrExchangeTrade")
public class EtrExchangeTradeController {
    @Autowired
    private EtrExchangeTradeService etrExchangeTradeService;

    @PostMapping("selectEtrExchange/{page}/{length}")
    public Page<EtrExchangeTrade003Dto> selectEtrExchange(@RequestBody Map<String,Object> param,@PathVariable("page") int page, @PathVariable("length") int length){
        return etrExchangeTradeService.selectEtrExchange(page,length,param);
    }
    //add by chenjiaxing 导出全部数据 202100902 start
    @PostMapping("selectEtrExchange2")
    public List<EtrExchangeTrade003Dto> selectEtrExchange2(@RequestBody Map<String,Object> param){
        return etrExchangeTradeService.selectEtrExchange2(param);
    }
    //add by chenjiaxing 导出全部数据 202100902 end

    @PostMapping("buildEtrFlow")
    public BaseEtrInvestInfoDto selectEtrExchange(@RequestBody EtrExchangeTrade001Dto dto){
        return etrExchangeTradeService.buildEtrFlow(dto);
    }

    @PostMapping("buildMultiEtrFlow")
    public BaseEtrInvestInfoDto buildMultiEtrFlow(@RequestBody EtrExchangeTradeMulti001Dto dto){
        return etrExchangeTradeService.buildMultiEtrFlow(dto);
    }
    /**
     * 
     * 工作流完成方法
     * <功能详细描述>
     * @param dto
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("doComplete")
    public void doComplete(@RequestBody EtrExchangeTrade001Dto dto) throws IllegalAccessException, InvocationTargetException{
    	etrExchangeTradeService.doComplete(dto, dto.getOperateType());
    }

    /**
     *
     * 工作流完成方法
     * <功能详细描述>
     * @param dto
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("doCompleteMulti")
    public void doCompleteMulti(@RequestBody EtrExchangeTradeMulti001Dto dto) throws IllegalAccessException, InvocationTargetException{
        etrExchangeTradeService.doCompleteMulti(dto, dto.getOperateType());
    }

    
    /**
     * 检索产品持仓分页数据。 <功能详细描述>
     * 
     * @param pageParam int
     * @param page int
     * @param length int
     * @return Page<EtrExchangeTrade002Dto>
     * @throws Exception Exception
     * @see
     * @since 1.0
     */
    @PostMapping("/findPositionsPage/{page}/{length}")
    public Page<EtrExchangeTrade002Dto> findPositionsPage(@RequestBody Map<String, Object> pageParam,
                                                          @PathVariable("page") int page,
                                                          @PathVariable("length") int length)
        throws Exception {
        return etrExchangeTradeService.findPositionsPage(page, length, pageParam);
    }
    
    /**
     * 检索产品债券质押持仓分页数据。
     * 
     * @param pageParam int
     * @param page int
     * @param length int
     * @return Page<EtrExchangeTrade002Dto>
     * @throws Exception Exception
     * @see
     * @since 1.0
     */
    @PostMapping("/findZqzyPositionsPage/{page}/{length}")
    public Page<EtrExchangeTrade002Dto> findZqzyPositionsPage(@RequestBody Map<String, Object> pageParam,
            @PathVariable("page") int page,
            @PathVariable("length") int length)
                    throws Exception {
        return etrExchangeTradeService.findZqzyPositionsPage(page, length, pageParam);
    }

    /**
     * 检索产品债券质押持仓所有数据。
     * @param pageParam
     * @return
     * @throws Exception
     */
    @PostMapping("/findZqzyPositionsList")
    public List<EtrExchangeTrade002Dto> findZqzyPositionsList(@RequestBody Map<String, Object> pageParam)  throws Exception {
        return etrExchangeTradeService.findZqzyPositionsList(pageParam);
    }

    /**
     * 查询证券代码联想框文本框
     *
     * @param param 画面参数
     * @param page 画面参数
     * @param length 画面参数
     * @return 证券代码联想框文本框
     * @see
     * @since 1.0
     */
    @RequestMapping("getSecAcctId/{page}/{length}")
    public Page<SelectOptionDto> getSecAcctId(@RequestBody Map<String, Object> param,
                                              @PathVariable("page") int page,
                                              @PathVariable("length") int length) {
        return etrExchangeTradeService.getSecAcctId(page, length, param);
    }



    /**
     * 质押券代码下拉框查询
     */
    @RequestMapping("selectZyq/{page}/{length}")
    public Page<SelectOptionDto> selectZyq(@RequestBody Map<String, Object> param,
                                              @PathVariable("page") int page,
                                              @PathVariable("length") int length) {
        return etrExchangeTradeService.selectZyq(page, length, param);
    }
    /**
     * 查询债券利率重置信息
     *
     * @param paramMap
     * @return 债券利率重置信息
     * @see
     * @since 1.0
     */
    @RequestMapping("selectBondsIntResetList")
    public List<FinCashCalcRule> selectBondsIntResetList(@RequestBody Map<String, Object> paramMap) {
        String finprodId = (String) paramMap.get("finprodId");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("finprodId", finprodId);
        param.put("finprodType", DictCodeListExt1.金融产品类型_投管用_债券.getValue());
        return etrExchangeTradeService.selectBondsIntResetList(finprodId);
    }

    /**
     * 查询债券基本信息
     *
     * @param param
     * @return 债券基本信息
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @RequestMapping("getSecInfoModel")
    public SecInfoModelDto getSecInfoModel(@RequestBody Map<String, Object> param)
            throws Exception {
        return etrExchangeTradeService.getSecInfoModel(param);
    }

    /**
     * 获取债券付息计划
     *
     * @param param
     * @return 债券付息计划
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @RequestMapping("getFinCashPlan")
    public List<FinCashPlanDto> getFinCashPlan(@RequestBody Map<String, Object> param)
            throws Exception {
        String finprodId = (String) param.get("finprodId");
        return etrExchangeTradeService.getFinCashPlan(finprodId);
    }

    /**
     * a.当前债券面额取得（根据还本计划计算）
     *
     * @param param 参数
     * @return a.当前债券面额取得（根据还本计划计算）
     * @see
     * @since 1.0
     */
    @RequestMapping("getCurSecAmt")
    public BigDecimal getCurSecAmt(@RequestBody Map<String, Object> param) {
        return etrExchangeTradeService.getCurSecAmt(param);
    }

    /**
     * 委托关闭
     * @param entity
     * @return
     */
    @RequestMapping("entrustClose")
    public void entrustBack(@RequestBody EtrExchangeTrade003Dto entity) {
         etrExchangeTradeService.entrustClose(entity);
    }

    /**
     * 委托撤销
     * @param entity
     * @return
     */
    @RequestMapping("entrustRevoke")
    public void entrustRevoke(@RequestBody EtrExchangeTrade003Dto entity) {
         etrExchangeTradeService.entrustRevoke(entity);
    }
    
    /**
     * 保存check
     * @param entity
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    @RequestMapping("checkSave")
    public AjaxResult checkSave(@RequestBody EtrExchangeTrade001Dto entity, BindingResult result) throws IllegalAccessException, InvocationTargetException {
    	AjaxResult ajax = new AjaxResult(etrExchangeTradeService.checkSave(entity, result));
    	Field[] fields = ReflectUtil.getDeclaredFields(ajax);
    	for(Field field : fields) {
    		if(StringUtil.equals(field.getName(), "messageType")) {
    			field.setAccessible(true);
    	    	ReflectionUtils.setField(field, ajax, MessageType.错误消息.name());
    	    	break;
    		}
    	}
    	return ajax;
   }

    /**
     * 获取投资目的
     * @return
     */
    @RequestMapping("getInvAim")
    public String getInvAim() {
       return etrExchangeTradeService.getInvAim();
    }

    /**
     * 获取投资目的
     * @return
     */
    @RequestMapping("linkInvAim")
    public String linkInvAim(@RequestBody Map<String, Object> param) {
        return etrExchangeTradeService.linkInvAim(param);
    }
    
    /**
     * 查询交易所指令信息
     * @param entrustId
     * @return
     * @see
     * @since 1.0
     */
    @RequestMapping("getEtrExchangeTradeInfo")
    public EtrExchangeTrade001Dto getEtrExchangeTradeInfo(@RequestBody String entrustId) throws Exception{
        return  etrExchangeTradeService.getEtrExchangeTradeInfo(entrustId);
    }

    /**
     * 对手方交易商联想文本框初始化。
     * 
     * @param map 检索参数
     * @return 联想文本框
     */
    @PostMapping("/selectCounterDealer")
    public List<SuggestDto> selectCounterDealer(@RequestBody Map<String, String> map) {
        return etrExchangeTradeService.selectCounterDealer(map);
    }

    /**
     * 对手方交易员联想文本框初始化。
     * 
     * @param map 检索参数
     * @return 联想文本框
     */
    @PostMapping("/selectCounterTrader")
    public List<SuggestDto> selectCounterTrader(@RequestBody Map<String, String> map) {
        return etrExchangeTradeService.selectCounterTrader(map);
    }

    /**
     * 本方交易员联想文本框初始化。
     * 
     * @param map 检索参数
     * @return 联想文本框
     */
    @PostMapping("/selectTrader")
    public List<SuggestDto> selectTrader(@RequestBody Map<String, String> map) {
        return etrExchangeTradeService.selectTrader(map);
    }
	
	/**
	 * 债券回售计划查询
	 *
	 * @param map
	 *            检索参数
	 * @return SelectOptionDto
	 */
    @RequestMapping("/selectBondExecPlan")
	public int selectBondExecPlan(@RequestBody Map<String, Object> map) {
		return etrExchangeTradeService.selectBondExecPlan(map);
	}
    
    /**
	 * 资产二级类型查询
	 *
	 * @param map
	 *            检索参数
	 * @return SelectOptionDto
	 */
    @RequestMapping("/getCustomerTypeTwo")
	public String getCustomerTypeTwo(@RequestBody Map<String, Object> map) {
		return etrExchangeTradeService.getCustomerTypeTwo(map);
	}

    /**
     * 查询上交所和深交所标准券数量
     *
     * @param pageParam 页面参数
     * @return 上交所和深交所标准券数量
     * @see
     * @since 1.0
     */
    @RequestMapping("/loadSecNum")
    public Map<String, String> loadSecNum(@RequestBody Map<String, Object> pageParam) {
        return etrExchangeTradeService.loadSecNum(pageParam);
    }
    
    /**
	 * 获取分发指令列表
	 *
	 * @param map
	 *            检索参数
	 * @return SelectOptionDto
	 */
    @RequestMapping("/getDistributeByEntrustId")
	public EtrDistribute getDistributeByEntrustId(@RequestBody Map<String, Object> map) {
		return etrExchangeTradeService.getDistributeByEntrustId(map);
	}
    
	/**
	 * 折算率查询
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/selectStdsecConverseRatio")
	public String selectStdsecConverseRatio(@RequestBody Map<String, Object> map) {
		return etrExchangeTradeService.selectStdsecConverseRatio(map);
	}

	@RequestMapping("/queryStandandTicketCanUse")
	public BigDecimal queryStandandTicketCanUse(@RequestBody Map<String, Object> map) {
		String result = etrExchangeTradeService.queryStandandTicketCanUse(map);
		if (StringUtils.isNotEmpty(result)) {
			return new BigDecimal(result);
		}
		return BigDecimal.ZERO;
	}
	
	@RequestMapping("/qryMstBondMarketPrice")
	public String qryMstBondMarketPrice(@RequestBody Map<String, Object> map) {
		return etrExchangeTradeService.qryMstBondMarketPrice(map);
	}
	
	@RequestMapping("/qryMstFundMarketPrice")
	public String qryMstFundMarketPrice(@RequestBody Map<String, Object> map) {
		return etrExchangeTradeService.qryMstFundMarketPrice(map);
	}
	
	@RequestMapping("/qryMstShareMarketPrice")
	public String qryMstSharekMarketPrice(@RequestBody Map<String, Object> map) {
		return etrExchangeTradeService.qryMstShareMarketPrice(map);
	}
	
	/**
	 * 插入和修改时，指令持仓流水 、现金流水处理
	 * add by zhangpei 
	 * 20201128
	 * @param dto
	 */
	@Transactional
	@DTXTransaction
	@PostMapping("doInsetOrUpdateForEtrPositionHold")
	public void doInsetOrUpdateForEtrPositionHold(@RequestBody EtrExchangeTrade001Dto dto) {
		etrExchangeTradeService.doInsetOrUpdateForEtrPositionHold(dto);
	}

    /**
     * 插入和修改时，指令持仓流水 、现金流水处理(多组合)
     * add by zhangpei
     * 20201128
     * @param dtoList
     */
    @Transactional
    @DTXTransaction
    @PostMapping("doInsetOrUpdateForEtrPositionHoldMulti")
    public void doInsetOrUpdateForEtrPositionHoldMulti(@RequestBody List<EtrExchangeTrade001Dto> dtoList) {
        for(EtrExchangeTrade001Dto dto:dtoList) {
            etrExchangeTradeService.doInsetOrUpdateForEtrPositionHold(dto);
        }
    }


	/**
	 *
	 * @Description //获得金融产品及其他信息
	 * @Param [map]
	 * @return java.util.List<com.joyintech.service.entrust.etrexchangetrade.dto.FinProductOthCodeDto>
	 * @version 1.0
	 * @see
	**/
    @PostMapping("getFinprodOthCodeInfo")
	public List<FinProductOthCodeDto> getFinprodOthCodeInfo(@RequestBody Map<String,Object> map){
	    return etrExchangeTradeService.getFinprodOthCodeInfo(map);
    }

    /**
     *
     * @Description //获得权益额度
     * @Param [map]
     * @return java.util.List<com.joyintech.tams.common.base.entity.FinProductQuto>
     * @version 1.0
     * @see
    **/
    @PostMapping("getFinProductQutoInfo")
    public List<FinProductQuto> getFinProductQutoInfo(@RequestBody Map<String,Object> map){
        return etrExchangeTradeService.getFinProductQutoInfo(map);
    }


    /**
     *
     * @Description //获得已认购额度
     * @Param [map]
     * @return java.util.List<com.joyintech.tams.common.base.entity.EtrEntrust>
     * @version 1.0
     * @see
    **/
    @PostMapping("getsubEqutity")
    public List<SubEqutityDto> getsubEqutity(@RequestBody Map<String,Object> map){
        return etrExchangeTradeService.getsubEqutity(map);
    }


    /**
     *
     * @Description //查询是否有流程中指令
     * @Param [map]
     * @return java.util.List<com.joyintech.service.entrust.etrexchangetrade.dto.SubEqutityDto>
     * @version 1.0
     * @see
    **/
    @PostMapping("selectWflEntrust")
    public List<WflEtrEntrust> selectWflEntrust(@RequestBody Map<String,Object> map){
        return etrExchangeTradeService.selectWflEntrust(map);
    }

    /**
     *
     * @Description 查询流程及委托表中是否有同一股东下达的可转债申购指令
     * @Param [map]
     * @return java.util.List<java.lang.String>
     * @version 1.0
     * @see
     **/
    @PostMapping("selectWflEntrustAddAndEtrEntrustAdd")
    public List<String> selectWflEntrustAddAndEtrEntrustAdd(@RequestBody Map<String,Object> map){
        return etrExchangeTradeService.selectWflEntrustAddAndEtrEntrustAdd(map);
    }

    //add by chenjiaxing 20210907 债券竞价交易添加偏离度展示 start
    /**
     * 1.6计算偏离度
     *
     * @param param 画面参数
     * @return 偏离度
     * @see
     * @since 1.0
     */
    @RequestMapping("calcDeviationDegree")
    public String calcDeviationDegree(@RequestBody Map<String, Object> param) {
        return etrExchangeTradeService.calcDeviationDegree(param);
    }
    //add by chenjiaxing 20210907 债券竞价交易添加偏离度展示 end

    // add by etliu 20211207 start
    // 交易所债券非交易类业务迁移
    /**
     *
     * @Description //查询行权回售数据
     * @Param [map]
     * @return 行权回售数据
     * @version 1.0
     * @see
     **/
    @PostMapping("selectFinExercisePlanData")
    public List<FinExercisePlan> selectFinExercisePlanData(@RequestBody Map<String, Object> map){
        return etrExchangeTradeService.selectFinExercisePlanData(map);
    }
    // add by etliu 20211207 end

    /**
     * 查询多组合名称下拉框
     * @param map
     * @return
     */
    @PostMapping("selectPortfolioTreeId/{page}/{length}")
    public Page<SelectOptionDto> selectPortfolioTreeId(Map<String,Object> map,@PathVariable("page") int page, @PathVariable("length") int length) {
        return etrExchangeTradeService.selectPortfolioTreeId(map,page,length);
    }

    /**
     * 查询多组合树关联的组合
     * @param map
     * @return
     */
    @PostMapping("selectPortfolioId")
    public List<SelectOptionDto> selectPortfolioId(@RequestBody Map<String,Object> map){
        return etrExchangeTradeService.selectPortfolioId(map);
    }


    /**
     * 查询多组合明细
     * @param map
     * @return
     */
    @RequestMapping("selectPortfolioTreeDetail")
    public List<EtrExchangeTradeMultiPtl001Dto> selectPortfolioTreeDetail(@RequestBody Map<String,Object> map) {
        return etrExchangeTradeService.selectPortfolioTreeDetail(map);
    }
    /**
     * 获取多组合分配资金账户
     * @param paraMap
     * @return
     */
    @RequestMapping("selectMultiAcctNo/{page}/{length}")
    public Page<SelectOptionDto> selectMultiAcctNo(@RequestBody Map<String,Object> paraMap,@PathVariable("page") int page, @PathVariable("length") int length) {
        return etrExchangeTradeService.selectMultiAcctNo(paraMap,page,length);
    }

    /**
     * 查询组合+资金账户+交易场所对应的证券账户（多组合）
     * @param paraMap
     * @return
     */
    @RequestMapping("selectMultiSecAcctId")
    public String selectMultiSecAcctId(@RequestBody Map<String,Object> paraMap) {
        return etrExchangeTradeService.selectMultiSecAcctId(paraMap);
	}
	
    @RequestMapping("selectMultDetail")
    public void selectMultDetail(@RequestBody Map<String,Object> map){
        etrExchangeTradeService.selectMultDetail(map);
    }

}
