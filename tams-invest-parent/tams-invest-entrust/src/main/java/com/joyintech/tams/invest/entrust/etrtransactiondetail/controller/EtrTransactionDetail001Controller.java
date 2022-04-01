package com.joyintech.tams.invest.entrust.etrtransactiondetail.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectDto;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.entrust.etrinvestallinfo.dto.EtrInvestAllInfoDto;
import com.joyintech.tams.invest.entrust.etrinvestallinfo.service.EtrInvestAllInfoService;
import com.joyintech.tams.invest.entrust.etrtransactiondetail.dto.EtrTransactionDetailDto;
import com.joyintech.tams.invest.entrust.etrtransactiondetail.dto.TrdProductDealDetailDto;
import com.joyintech.tams.invest.entrust.etrtransactiondetail.service.EtrTransactionDetailService;

@Controller
@PageId("EtrTransactionDetail001")
@RequestMapping("/invest/entrust/etrtransactiondetail/EtrTransactionDetail001")
public class EtrTransactionDetail001Controller  extends BaseController{
	   /**
     * 所有投资委托信息service
     */
    @Autowired
    private EtrTransactionDetailService etrTransactionDetailService;

    /**
     * 页面初始化
     */
    @Override
    public String init(Model model) {
        this.setEntrustStatusSelectOptions(model);
        this.setAssetTypeSelectOptions(model);
        this.setTradeMarketSelectOptions(model);
        this.setPsSelectOptions(model);
        model.addAttribute("F01", DictCodeList.平安资产类型_债券.getValue());
        model.addAttribute("F07", DictCodeList.平安资产类型_质押式回购.getValue());
        model.addAttribute("F02", DictCodeList.平安资产类型_基金.getValue());
        model.addAttribute("F05", DictCodeList.平安资产类型_存款.getValue());
        model.addAttribute("F08", DictCodeList.平安资产类型_拆借.getValue());
        model.addAttribute("F04", DictCodeList.平安资产类型_计划类资产_净值型.getValue());
        model.addAttribute("F03", DictCodeList.平安资产类型_计划类资产_利率型.getValue());
        model.addAttribute("F06", DictCodeList.平安资产类型_项目类资产.getValue());
        model.addAttribute("F09", DictCodeList.平安资产类型_计划类资产_货币型.getValue());
        model.addAttribute("F10", DictCodeList.平安资产类型_买断式回购.getValue());
        model.addAttribute("F11", DictCodeListExt2.平安资产类型_股票.getValue());
        model.addAttribute("F12", DictCodeListExt2.平安资产类型_债券借贷.getValue());
        model.addAttribute("F13", DictCodeListExt2.平安资产类型_外汇即期.getValue());
        model.addAttribute("F14", DictCodeListExt2.平安资产类型_外汇远期.getValue());
        model.addAttribute("F15", DictCodeListExt2.平安资产类型_外汇掉期.getValue());
        model.addAttribute("F16", DictCodeListExt2.平安资产类型_利率互换.getValue());
        model.addAttribute("F17", DictCodeListExt2.平安资产类型_一级市场债.getValue());
        model.addAttribute("F36", DictCodeListExt1.金融产品类型_投管用_立项指令.getValue());
        model.addAttribute("F20", DictCodeListExt3.平安资产类型_期货.getValue());
        model.addAttribute("IRS_TYPE", DictCode.互换方式.getValue());
        model.addAttribute("PS_T04", DictCodeList.交易方向_申购.getValue());
        model.addAttribute("PS_T49", DictCodeListExt3.交易方向_债转股.getValue());
        model.addAttribute("TRADE_PLACE_03", DictCodeListExt1.交易场所_上海证券交易所.getValue());
        model.addAttribute("TRADE_PLACE_04", DictCodeListExt1.交易场所_深圳证券交易所.getValue());
        model.addAttribute("TARGET_BACK_TYPE_H01", DictCodeList.回购类型_质押式协议回购.getValue());

        model.addAttribute("entrustStatus", DictCodeList.委托状态_已接收.getValue());
        model.addAttribute("ENTRUST_CLASS_01", DictCodeListExt3.投资指令类型_单组合单资产指令.getValue());
        model.addAttribute("ENTRUST_CLASS_02", DictCodeListExt3.投资指令类型_多组合单资产指令.getValue());
        return super.init(model);
    }
    
    /**
     * 场内交易资产类型
     * 
     * @param model Model
     */
	private void setEntrustStatusSelectOptions(Model model) {
		SelectDto selectDto = this.getSelectDataFromDataDic(DictCode.委托状态, null);
		List<SelectOptionDto> entrustStatus = selectDto.getOptions().stream().filter(option -> {
			return StringUtil.equals(option.getId(), DictCodeList.委托状态_已接收.getValue())
					|| StringUtil.equals(option.getId(), DictCodeList.委托状态_已完成.getValue())
					|| StringUtil.equals(option.getId(), DictCodeList.委托状态_已关闭.getValue())
//	                || StringUtil.equals(option.getId(), DictCodeList.委托状态_已撤回.getValue())
                    ;
		}).collect(Collectors.toList());
		model.addAttribute("ENTRUST_STATUS", JsonUtils.objectArray2Json(entrustStatus.toArray()));
	}
	
    /**
     * 场内交易资产类型
     * 
     * @param model Model
     */
	private void setAssetTypeSelectOptions(Model model) {
		SelectDto selectDto = this.getSelectDataFromDataDic(DictCode.平安资产类型, null);
		List<SelectOptionDto> assetType = selectDto.getOptions().stream().filter(option -> {
			return StringUtil.equals(option.getId(), DictCodeList.平安资产类型_债券.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt2.平安资产类型_股票.getValue())
					|| StringUtil.equals(option.getId(), DictCodeList.平安资产类型_基金.getValue())
					|| StringUtil.equals(option.getId(), DictCodeList.平安资产类型_质押式回购.getValue());
//					|| StringUtil.equals(option.getId(), DictCodeListExt3.平安资产类型_期货.getValue());
		}).collect(Collectors.toList());
		model.addAttribute("PINAN_ASSET_TYPE", JsonUtils.objectArray2Json(assetType.toArray()));
	}
	
    /**
     * 场内交易场所-上交所、深交所
     * 
     * @param model Model
     */
	private void setTradeMarketSelectOptions(Model model) {
		SelectDto selectDto = this.getSelectDataFromDataDic(DictCode.交易场所, null);
		List<SelectOptionDto> tradePlace = selectDto.getOptions().stream().filter(option -> {
			return StringUtil.equals(option.getId(), DictCodeListExt1.交易场所_上海证券交易所.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt1.交易场所_深圳证券交易所.getValue());
		}).collect(Collectors.toList());
		model.addAttribute("TRADE_PLACE", JsonUtils.objectArray2Json(tradePlace.toArray()));
	}
    
	/**
	 * 由于交易平台不同，设置不同交易平台对应的委托方向
	 * 
	 * @param  model
	 */
	private void setPsSelectOptions(Model model) {
		SelectDto selectDto = this.getSelectDataFromDataDic(DictCode.交易方向, null);
		// 竞价平台
		List<SelectOptionDto> ps = selectDto.getOptions().stream().filter(option -> {
			return StringUtil.equals(option.getId(), DictCodeList.交易方向_买入.getValue())
					|| StringUtil.equals(option.getId(), DictCodeList.交易方向_卖出.getValue())
					|| StringUtil.equals(option.getId(), DictCodeList.交易方向_行权_回售.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt2.交易方向_配股.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_配债.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_债转股.getValue())
					|| StringUtil.equals(option.getId(), DictCodeList.交易方向_申购.getValue())
					/*|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_质押券入库.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_质押券出库.getValue())*/
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_正回购.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_逆回购.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_买入开仓.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_卖出平仓.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_卖出开仓.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_买入平仓.getValue())
					|| StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_提前购回.getValue())
                    || StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_到期购回.getValue())
                    || StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_中途换券.getValue())
                    || StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_解除质押.getValue())
                    || StringUtil.equals(option.getId(), DictCodeListExt3.交易方向_到期续做.getValue());
		}).collect(Collectors.toList());
		model.addAttribute("PS", JsonUtils.objectArray2Json(ps.toArray()));
	}
    /**
     * 
     * 组交易方向下拉框值
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    private List<SelectOptionDto> makeAllFloatRate() {
        List<SelectOptionDto> listFloatRate = new ArrayList<SelectOptionDto>();

        SelectDto selectDto1 = this.getSelectDataFromDataDic(DictCode.交易方向, null);
        for (SelectOptionDto dto : selectDto1.getOptions()) {
            listFloatRate.add(dto);
        }
        SelectDto selectDto2 = this.getSelectDataFromDataDic(DictCode.回购拆借方向, null);
        for (SelectOptionDto dto : selectDto2.getOptions()) {
            listFloatRate.add(dto);
        }
        SelectDto selectDto3 = this.getSelectDataFromDataDic(DictCode.互换方式, null);
        for (SelectOptionDto dto : selectDto3.getOptions()) {
            listFloatRate.add(dto);
        }
        return listFloatRate;
    }
    /**
     * 
     * 获取组合代码联想文本框信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/getentrustId")
    public List<SuggestDto> getentrustId(String param)
        throws Exception {
        List<SuggestDto> temp = etrTransactionDetailService.getentrustId(param);
        return temp;
    }
    /**
     * 
     * 获取资产代码联想文本框信息
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/getfinprodId")
    public List<SuggestDto> getfinprodId(String param)
        throws Exception {
        List<SuggestDto> temp = etrTransactionDetailService.getfinprodId(param);
        return temp;
    }
    /**
     * 
     * 所有投资委托信息分页查询
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<EtrTransactionDetailDto> select()
        throws Exception {
        Page<EtrTransactionDetailDto> page = etrTransactionDetailService.selectForPage(RequestUtil.getPageParam());
        return page;
    }
    /**
     * 文件导出 <功能详细描述>
     * 
     * @return 导出返回参数
     * @throws Exception 抛出异常
     * @see
     * @since 1.0
     */
    @RequestMapping("export")
    public ResponseEntity<byte[]> export()
        throws Exception {
        return etrTransactionDetailService.export(this.getPageId(),RequestUtil.getParameters(getRequest()));
    }
    
	/**
	 * 
	 * 成交明细分页查询 <功能详细描述>
	 * 
	 * @return
	 * @throws Exception
	 * @see
	 * @since 1.0
	 */
    @ResponseBody
	@RequestMapping("selectTransDetailPage")
	public Page<TrdProductDealDetailDto> selectTransDetailPage() throws Exception {
		Page<TrdProductDealDetailDto> page = etrTransactionDetailService.selectTransDetail(RequestUtil.getPageParam());
		return page;
	}
}
    
    
