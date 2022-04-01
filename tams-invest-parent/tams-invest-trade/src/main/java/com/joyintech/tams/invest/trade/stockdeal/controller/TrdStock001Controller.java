/*
 * 文件名：TrdStock001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：wangpeng
 * 修改时间：2019年7月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.trade.stockdeal.controller;

import javax.validation.Valid;

import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.tams.component.business.settle.BussSettleOrderCommService;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.dto.common.BussTrdTransferOrderBase;
import com.joyintech.tams.invest.common.dto.period.common.BussTrdTransferOrderDto;
import com.joyintech.workflow.engine.exception.WorkFlowException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.invest.trade.stockdeal.dto.TrdStockDto;
import com.joyintech.tams.invest.trade.stockdeal.dto.TrdStockRevokeDto;
import com.joyintech.tams.invest.trade.stockdeal.service.TrdStock001Service;
import com.joyintech.tams.invest.trade.stockdeal.service.TrdStock002Service;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.util.WorkFlowUtil;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 股票交易
 *
 * @author wangpeng
 * @version 1.0 2019年7月29日
 * @see TrdStock001Controller
 * @since 1.0
 */
@Controller
@PageId("TrdStock001")
@RequestMapping("/invest/trade/stockdeal/TrdStock001")
public class TrdStock001Controller extends BaseController {

    @Autowired
    private TrdStock001Service trdStock001Service;
    
    @Autowired
    private TrdStock002Service trdStock002Service;

    @Autowired
    private BussSettleOrderCommService bussSettleOrderCommService;

    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.交易方向, DictCode.交易场所,
            DictCode.交易流程状态, DictCode.外汇交易模式,DictCode.上市板);
    	model.addAttribute("BOARD_BD03", DictCodeList.上市板_科创板.getValue());
		//给交易方式配置值
		model.addAttribute("DEAL_MODE_03", DictCodeListExt2.外汇交易模式_盘后定价交易.getValue());
        return super.init(model);
    }
  
    /**
     * 分页查询
     *
     * @return Page<TrdStock001Dto>泛型的page实体类
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @RequestMapping("findPage")
    @ResponseBody
    public Page<TrdStockDto> findPage()
         {
        return trdStock001Service.findPage(RequestUtil.getPageParam());
    }

    /**
     * 导出信息excel
     *
     * @return excel
     * @throws Exception 异常
     * @param parameter 页面参数
     * @see
     * @since 1.0
     */
    @RequestMapping(ACTION_EXPORT)
    public ResponseEntity<byte[]> export()
         {
        return trdStock001Service.export(this.getPageId(), RequestUtil.getPageParam());
    }
    
    /**
     * 撤销前check
     *
     * @param inputDto 画面输入参数dto
     * @param result BindingResult
     * @return check结果
     * @see
     * @since 1.0
     */
    @RequestMapping("checkRevoke")
    @ResponseBody
    public AjaxResult check(@Valid @RequestBody TrdStockRevokeDto inputDto,
                            BindingResult result) {
        return commonCheck(result, inputDto.getOperateType());
    }
    
    /**
     * 撤销
     * @param inputDto 画面输入参数dto
     * @return 撤销结果
     * @throws Exception
     * @see
     * @since 1.0
     */
    @RequestMapping("doRevoke")
    @ResponseBody
    AjaxResult doRevoke(@RequestBody TrdStockDto inputDto) throws WorkFlowException {
        inputDto = trdStock002Service.getTrdProductDealDto(inputDto.getTradeId());
        inputDto.setActPortfolioId(inputDto.getPortfolioId());
        List<BussTrdTransferOrderBase> bussTrdTransferOrderBases = bussSettleOrderCommService.selectOrdersByBizCode(inputDto.getTradeId());
        if(CollectionUtils.isNotEmpty(bussTrdTransferOrderBases)){
            BussTrdTransferOrderDto dto = new BussTrdTransferOrderDto();
            BeanUtil.copyPropertiesIgnoreNull(dto, bussTrdTransferOrderBases.get(0));
            inputDto.setBussTrdTransferOrder(new ArrayList<BussTrdTransferOrderDto>(){{
                add(dto);
            }});
        }

        ProcessParameter<TrdStockDto> processParameter = new ProcessParameter<>();
        WorkFlowParameter<TrdStockDto> workFlowParameter = new WorkFlowParameter<>();
        processParameter.setWorkFlowParameter(workFlowParameter);
        processParameter.setPageId("TrdStock002");
        processParameter.setOperationType(OperationType.REVOKE);

        workFlowParameter.setPageId("TrdStock002");
        workFlowParameter.setElementId(this.getParam("elementId"));
        workFlowParameter.setBusinessCode((entity) -> entity.getTradeId());
        workFlowParameter.setBusinessType(DictCodeListExt2.共通业务类型_股票投资交易.getValue());
        workFlowParameter.setComment((entity) -> WorkFlowUtil.concatComment(
            getMessage("TrdStock002.tradeId") + GlobalConstant.COLON + entity.getTradeId(),
            getMessage("TrdStock002.finprodName") + GlobalConstant.COLON + entity.getFinprodName(),
            getMessage("TrdStock002.tradeDate") + GlobalConstant.COLON + DateUtil.format(entity.getTradeDate())));

        String msg = trdStock002Service.start(processParameter, inputDto);

        return new AjaxResult(msg);
    }

    /**
     * 获取委托代码
     * @return 联想文本框实体类集合
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("getEntrustIdByType")
    public List<SuggestDto> getEntrustIdByType() {
        String entrustId = getParam("param");
        Map<String, Object> map = new HashMap<>();
        map.put("param", entrustId);
        map.put("finProdType", Arrays.asList(DictCodeListExt1.金融产品类型_股票.getValue()));
        map.put("ENTRUST_STATUS_LIST", Arrays.asList(DictCodeList.委托状态_已接收.getValue(),
                DictCodeList.委托状态_已完成.getValue(), DictCodeList.委托状态_已关闭.getValue()));
        return trdStock001Service.getEntrustIdByType(map);
    }
}
