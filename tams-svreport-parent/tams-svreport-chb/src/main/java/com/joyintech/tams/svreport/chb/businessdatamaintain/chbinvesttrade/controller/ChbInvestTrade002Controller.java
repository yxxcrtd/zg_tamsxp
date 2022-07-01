package com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto.ChbBokSubjectDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.service.ChbInvestTradeService;
import com.joyintech.tams.systemtools.util.CommonUtil;
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
import java.util.Date;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-04-27 14:21
 */
@Controller
@PageId("CchbInvestTrade002")
@RequestMapping("/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade002")
public class ChbInvestTrade002Controller extends BaseController {
    @Autowired
    private ChbInvestTradeService chbInvestTradeService;
    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.币种_中债);
        this.setSelectOptionToModel(model, DictCode.资产计量方式);
        this.setSelectOptionToModel(model, DictCode.资金流动类型);
        this.setSelectOptionToModel(model, DictCode.对手方类型);
        String workflowId = getParam(GlobalConstant.WORKFLOW_ID);
        String tradeCode = getParam("tradeCode");
        String prodId = getParam("prodId");
        String assetCode = getParam("assetCode");
        String status = getParam("statusValid");
        String tradeSeqno = getParam("tradeSeqno");
        ChbInvestTradeDto dto=null;
        if (!WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(workflowId)) {
            dto = chbInvestTradeService.getBussinessEntityByWorkflowId(
                    workflowId);
            model.addAttribute("pageModel",dto.getPageModel());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        else if (WorkFlowUtil.isNotFromToDoList() &&StringUtil.isNotEmpty(tradeSeqno) ) {
            //通过主键查询数据

            try {
                dto = chbInvestTradeService.selectByKey(tradeSeqno,status);
            } catch (Exception e) {
                e.printStackTrace();
            }

            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        if("detail".equals(getParam("operateType"))){
            model.addAttribute("detail","detail");
        }
        if(null !=dto){
            if(dto.getUnitPriceNet()!=null){
                dto.setUnitPriceNet(dto.getUnitPriceNet().setScale(4, BigDecimal.ROUND_HALF_UP));
            }
            if(dto.getUnitPriceFull()!=null){
                dto.setUnitPriceFull(dto.getUnitPriceFull().setScale(4,BigDecimal.ROUND_HALF_UP));
            }
            if(dto.getEndRate()!=null){
                dto.setEndRate(dto.getEndRate().setScale(5,BigDecimal.ROUND_HALF_DOWN));
            }
        }
        model.addAttribute("tradeCode_New",IDGenerator.generate(32));
        //获取本行登记代码
        model.addAttribute("registBankCode_New",CommonUtil.selectBankCode());
        return super.init(model);
    }
    @ResponseBody
    @RequestMapping("/selectProdIdAndRegCodeByProdName")
    public  Map<String, String> selectProdIdAndRegCodeByProdName(String prodName){
        return chbInvestTradeService.selectProdIdAndRegCodeByProdName(prodName);
    }
    @ResponseBody
    @RequestMapping("/selectAssetNameByAssetCode")
    public Map<String,String> selectAssetNameByAssetCode(String assetCode){
        return chbInvestTradeService.selectAssetNameByAssetCode(assetCode);
    }
    @ResponseBody
    @RequestMapping("/selectRivalPartyType")
    public Map<String,String> selectRivalPartyType(String tradeCounter){
        return chbInvestTradeService.selectRivalPartyType(tradeCounter);
    }

    /**
     * 保存按钮点击时的check
     *
     * @param dto dto
     * @param result BindingResult
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({ValidateGroupEdit.class}) ChbInvestTradeDto dto,
                            BindingResult result) {

        return commonCheck(result, dto.getOperateType());
    }
    /**
     * 数据保存操作
     * @param dto 请求实体类
     */
    @ResponseBody
    @RequestMapping(OPERATE_TYPE_SAVE)
    public AjaxResult save(@RequestBody ChbInvestTradeDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        // 新建留痕表参数和工作流参数
        ProcessParameter<ChbInvestTradeDto> processParameter = new ProcessParameter<ChbInvestTradeDto>();
        WorkFlowParameter<ChbInvestTradeDto> params = new WorkFlowParameter<ChbInvestTradeDto>();
        params.setPageId("CchbInvestTrade002");
        params.setElementId("CchbInvestTrade002_btn_submit");
        params.setBusinessCode(item -> item.getTradeCode()+item.getProdId()+item.getRegistBankCode()+item.getAssetCode());
        params.setBusinessType(DictCodeList.共通业务类型_投资交易信息.getValue());
        params.setComment(entity -> this.getMessage("ChbInvestTrade001.tradeCode") + "("
                + entity.getTradeCode() + ")"
                + this.getMessage("ChbInvestTrade001.prodId")
                + "(" + entity.getProdId() + ")"
                + this.getMessage("ChbInvestTrade001.registBankCode")
                + "(" + entity.getRegistBankCode() + ")"
                + this.getMessage("ChbInvestTrade001.assetCode")
                + "(" + entity.getAssetCode() + ")");
        // 塞入工作流参数
        processParameter.setWorkFlowParameter(params);

        dto.setCreateTime(DateUtil.now());
        dto.setCreateUser(CommonUtil.getUserName());
        dto.setUpdateTime(DateUtil.now());
        dto.setUpdateUser(CommonUtil.getUserName());
        // 流程开始
        String msg = chbInvestTradeService.start(processParameter, dto);
        return new AjaxResult(msg);
    }

    @ResponseBody
    @RequestMapping("getCnyAmt")
    public AjaxResult dealCnyAmt(){
        AjaxResult result  = new AjaxResult();
        result.setData(chbInvestTradeService.dealCnyAmt(RequestUtil.getPageParam()));
        return result;
    }
}
