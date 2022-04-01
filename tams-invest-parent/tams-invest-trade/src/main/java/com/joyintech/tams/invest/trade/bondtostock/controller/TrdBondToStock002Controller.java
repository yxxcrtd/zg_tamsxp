package com.joyintech.tams.invest.trade.bondtostock.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.component.business.riskrds.dto.InterfRiskRdsDto;
import com.joyintech.tams.component.business.riskrds.service.InterfRiskRdsService;
import com.joyintech.tams.component.business.trade.dto.TrdInvestPersonInfo002Dto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.CommonUtil;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.common.constantrds.BussinessDictCodeListRds;
import com.joyintech.tams.invest.common.trade.AllTradeCommonUtil;
import com.joyintech.tams.invest.trade.bondtostock.dto.TrdBondToStock001Dto;
import com.joyintech.tams.invest.trade.bondtostock.service.TrdBondToStock002Service;
import com.joyintech.tams.invest.trade.common.aop.TradeRdsCommonAop;
import com.joyintech.tams.invest.trade.common.controller.TrdCommonController;
import com.joyintech.tams.systemtools.dto.PageElementConfigDto;
import com.joyintech.tams.systemtools.util.PageElementUtil;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 可转债转股
 *
 * @author 丁志鹏
 * @version 1.0 2019年11月14日
 * @see TrdBondToStock002Controller 可转债转股管理画面001
 * @since 1.0
 */
@Controller
@PageId("TrdBondToStock002")
@RequestMapping("/invest/trade/bondtostock/TrdBondToStock002")
public class TrdBondToStock002Controller extends BaseController {
    /**
     *  业务service
     */
    @Autowired
    private TrdBondToStock002Service trdBondToStock002Service;
    
    @Autowired
    private TrdCommonController trdCommonController;
	
	//----------add by zhangrui1 20200508 核风险版本 start----------
    @Autowired(required = false)
    private InterfRiskRdsService<InterfRiskRdsDto> interfRiskRdsService;
    //----------add by zhangrui1 20200508 核风险版本 end----------
    
    @Override
    public String init(Model model) {
        String workflowId = getParam(GlobalConstant.WORKFLOW_ID);
        String entrustId = getParam("entrustId");
        String busiCode = getParam("busiCode");
        //配置单位 add by yeming 20210726 start
        String entrustAmtUnit = CacheUtil.getSysParam("entrustAmtUnit");
        model.addAttribute("entrustAmtUnit", entrustAmtUnit);
        //配置单位 add by yeming 20210726 end
        TrdBondToStock001Dto entity;

        //获取成交匹配录单
        String tranRecord = this.getParam("tranRecord");
//        流程中数据
        if(StringUtil.isNotEmpty(workflowId)){
            entity = trdBondToStock002Service.getBussinessEntityByWorkflowId(workflowId);
            //配置单位 add by yeming 20210726 start
            if(StringUtil.equals(entrustAmtUnit,"02")){
                if(entity.getShareAmt()!= null) {
                    entity.setShareAmt(entity.getShareAmt().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getConetrPriceTotal() != null){
                    entity.setConetrPriceTotal(entity.getConetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getHoldetrPriceTotal() != null){
                    entity.setHoldetrPriceTotal(entity.getHoldetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
            }
            //配置单位 add by yeming 20210726 end
            model.addAttribute("riskTrialRetDto", JsonUtils.object2Json(entity.getRiskTrialRetDto()));
        }
        //        有效交易数据
        else if(StringUtil.isNotEmpty(busiCode)){
            entity = trdBondToStock002Service.findEffectData(busiCode);
            //配置单位 add by yeming 20210726 start
            if(StringUtil.equals(entrustAmtUnit,"02")){
                if(entity.getShareAmt()!= null) {
                    entity.setShareAmt(entity.getShareAmt().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getConetrPriceTotal() != null){
                    entity.setConetrPriceTotal(entity.getConetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getHoldetrPriceTotal() != null){
                    entity.setHoldetrPriceTotal(entity.getHoldetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
            }
            //配置单位 add by yeming 20210726 end
        }
//        委托来源数据
        else if(StringUtil.isNotEmpty(entrustId)){
//            获取委托信息
            entity = trdBondToStock002Service.getEntrustInfo(entrustId);
            entity.setTradeDate(DateUtil.platDateFromCache());
            //配置单位 add by yeming 20210726 start
            if(StringUtil.equals(entrustAmtUnit,"02")){
                if(entity.getShareAmt()!= null) {
                    entity.setShareAmt(entity.getShareAmt().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getConetrPriceTotal() != null){
                    entity.setConetrPriceTotal(entity.getConetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getHoldetrPriceTotal() != null){
                    entity.setHoldetrPriceTotal(entity.getHoldetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
            }
            //配置单位 add by yeming 20210726 end
            if(StringUtil.isNotBlank(entity.getRemark())&&StringUtil.contains(entity.getRemark(),"退回原因")){
                entity.setRemark("");
            }
        }
        //成交匹配录单
        else if(StringUtil.isNotEmpty(tranRecord)) {
            model.addAttribute("isTranRecord", DictCodeList.是否_是.getValue());
            entity = JsonUtils.json2Object(tranRecord,TrdBondToStock001Dto.class);
        }
        else{
            entity = new TrdBondToStock001Dto();
            entity.setTradeDate(DateUtil.platDateFromCache());
            //配置单位 add by yeming 20210726 start
            if(StringUtil.equals(entrustAmtUnit,"02")){
                if(entity.getShareAmt()!= null) {
                    entity.setShareAmt(entity.getShareAmt().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getConetrPriceTotal() != null){
                    entity.setConetrPriceTotal(entity.getConetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
                if (entity.getHoldetrPriceTotal() != null){
                    entity.setHoldetrPriceTotal(entity.getHoldetrPriceTotal().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                }
            }
            //配置单位 add by yeming 20210726 end
        }
        //---add by zxjjpei 风险试算结果需要工作流编号 20200325 start---
        if(StringUtil.isNotEmpty(workflowId)){
            model.addAttribute("workflowId",workflowId);
        }
        else{
            model.addAttribute("workflowId",entity.getWorkflowId());
        }
        //---add by zxjjpei 风险试算结果需要工作流编号 20200325 end---
//        画面数据字典
        setSelectOptionToModel(model, DictCode.交易场所,DictCode.币种,DictCode.投资目的);
        model.addAttribute(GlobalConstant.ENTITY,entity);
        model.addAttribute("FINPROD_TYPE_TG_ZQ", DictCodeListExt1.金融产品类型_投管用_债券.getValue());
        model.addAttribute("PS_ZZG",DictCodeListExt3.交易方向_债转股.getValue());
        model.addAttribute("PA_TYPE_ZZG",DictCodeListExt3.平安业务类型_债转股.getValue());
        model.addAttribute("YES_OR_NO_N", DictCodeList.是否_否.getValue());
        model.addAttribute("MY_SUP_TIME_TYPE_02", DictCodeListExt1.监督时分_交易监控.getValue());
        model.addAttribute("ACCT_TYPE_A01", DictCodeListExt1.资金账户类型_产品托管户.getValue());
        model.addAttribute("ACCT_TYPE_A07", DictCodeListExt1.资金账户类型_券商资金账户.getValue());
        model.addAttribute("PS_MC",DictCodeList.交易方向_卖出.getValue());
        model.addAttribute("PA_TYPE_ZQMC",DictCodeListExt1.平安业务类型_债券类资产卖出.getValue());
        TrdInvestPersonInfo002Dto trdInvestPersonInfo = new TrdInvestPersonInfo002Dto();
        BeanUtil.copyPropertiesIgnoreNull(trdInvestPersonInfo,entity.getTrdInvestPersonInfo()==null?entity:entity.getTrdInvestPersonInfo());
        if(StringUtil.isEmpty(trdInvestPersonInfo.getTrader())){
            trdInvestPersonInfo.setTrader(LoginUserUtil.getUserName());
        }
        model.addAttribute("entity_trdInvestPersonInfo",JsonUtils.object2Json(trdInvestPersonInfo));
        model.addAttribute("businessId",entity.getBusiCode());
        // 划款信息
        if (entity != null && !StringUtil.isEmptyList(entity.getBussTrdTransferOrder())) {
            model.addAttribute("entity_bussRepFinProductInfoDto",
                    JsonUtils.object2Json(entity.getBussTrdTransferOrder().get(0)));
        }
        //add by lizhi 是否限制划款指令开关 2020年2月26日  start
        model.addAttribute("GEN_PAY_ORDERS_AUTO",CacheUtil.getSysParam(BussinessConstant.GEN_PAY_ORDERS_AUTO));
        //add by lizhi 是否限制划款指令开关 2020年2月26日  end
        PageElementConfigDto pageElementConfigDto = new PageElementConfigDto();
        pageElementConfigDto.setPageId("BaseRiskMathComm");
        Map<String, String> map = PageElementUtil.isPageElementShow(pageElementConfigDto); // 风险试算页面
        pageElementConfigDto.setPageId(this.getPageId());
        Map<String, String> isEleShow = PageElementUtil.isPageElementShow(pageElementConfigDto);
        isEleShow.putAll(map);
        model.addAttribute("isEleShow", isEleShow);
        return super.init(model);
    }
    /**
     * 保存提交前的数据检查
     *
     * @param dto 画面输入参数
     * @return 检查结果
     * @see
     * @since 1.0
     */
    @RequestMapping("check")
    @ResponseBody
    public AjaxResult check(@Validated({ValidateGroupEdit.class}) @RequestBody TrdBondToStock001Dto dto, BindingResult result) {
        if (result != null && result.hasErrors()) {
            return new AjaxResult(result);
        }
        return commonCheck(result, dto.getOperateType());
    }
    @RequestMapping("getPositionInfo")
    @ResponseBody
    public AjaxResult getPositionInfo(){
        AjaxResult ajaxResult = new AjaxResult();
        Map<String,Object> retMap = trdBondToStock002Service.getPositionInfo(RequestUtil.getPageParam());
        ajaxResult.setData(retMap);
        return ajaxResult;
    }
    /**
     * 保存提交
     *
     * @param dto
     * @return 画面输入参数
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("save")
    AjaxResult save(@RequestBody TrdBondToStock001Dto dto) throws Exception {
        
        
        
        List<TrdBondToStock001Dto> dtoList = new ArrayList<>();
        TrdBondToStock001Dto tempDto = new TrdBondToStock001Dto();
        BeanUtil.copyProperties(tempDto, dto);
//        获取业务编号
        tempDto.setBusiCode(trdBondToStock002Service.gen业务编号());
        ProcessParameter<TrdBondToStock001Dto> processParameter = new ProcessParameter<>();
        WorkFlowParameter<TrdBondToStock001Dto> workFlowParameter = new WorkFlowParameter<>();
        processParameter.setWorkFlowParameter(workFlowParameter);
        processParameter.setPageId(this.getPageId());
        if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)) {
            processParameter.setOperationType(OperationType.NEW);
        }
        else {
            processParameter.setOperationType(OperationType.MODIFY);
        }
        //----------add by zhangrui1 20200508 核风险版本 start----------
        if(dto.getOperateType().equals(BussinessConstant.SUBMIT)&& CommonUtil.isType(CacheUtil.getSysParam("pageRedisFlag"), BussinessDictCodeListRds.是否_是)){
            tempDto.setPs(DictCodeListExt1.交易方向_债转股.getValue());
            AjaxResult ar=interfRiskRdsService.checkRiskRds(new InterfRiskRdsDto(), BussinessDictCodeListRds.事件类型_投资交易.getValue(),
                    BussinessDictCodeListRds.接口系统ID_实占批量接口.getValue(),DictCodeList.平安资产类型_债券.getValue(),
                    en->interfRiskRdsService.sendRiskRdsData(tempDto,en));
            if (StringUtil.isNotEmpty(ar)) {
                return ar;
            }
            // add by zhangrui1 start
            // 将实占信息设置到线程缓存出错时撤销用
            TradeRdsCommonAop.setLocalCache(tempDto);
            // add by zhangrui1 end
        }
        //----------add by zhangrui1 20200508 核风险版本 end----------
        workFlowParameter.setPageId(this.getPageId());
        workFlowParameter.setElementId(this.getParam("elementId"));
        workFlowParameter.setBusinessCode((entity) -> entity.getBusiCode());
        workFlowParameter.setBusinessType(DictCodeListExt3.共通业务类型_可转债转股交易.getValue());
        workFlowParameter.setComment((entity) -> WorkFlowUtil.concatComment(
                getMessage("TrdBondInvestTrade002.tradeId") + GlobalConstant.COLON + entity.getBusiCode(),
                getMessage("TrdBondInvestTrade002.finprodName") + GlobalConstant.COLON + entity.getFinprodName(),
                getMessage("TrdBondInvestTrade002.tradeDate") + GlobalConstant.COLON + DateUtil.format(entity.getTradeDate())));
        tempDto.setActPortfolioId(tempDto.getPortfolioId());
        tempDto.setPortfolioId(AllTradeCommonUtil.exchangePortfolio(tempDto.getPortfolioId()));
        trdBondToStock002Service.prepareEntity(tempDto);
        tempDto.setPortfolioId(tempDto.getActPortfolioId());
        String msg = trdBondToStock002Service.start(processParameter, tempDto);
        
        //授信占用额度
//        trdCommonController.delLinesDeal(dto.getRiskCreditListCommonDto(),tempDto.getBusiCode());
        return new AjaxResult(msg);
    }
}