package com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dto.ChbExchangeQuotationInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.service.ChbExchangeQuotationInfoService;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.exception.WorkFlowException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 汇率行情 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author wangzhao
 * @version 1.0 2018年8月9日
 * @see ChbExchangeQuotationInfo002Controller
 * @since 1.0
 */

@Controller
@PageId("ChbExchangeQuotationInfo002")
@RequestMapping("/chb/businessdatamaintain/chbexchangeinfo/ChbExchangeQuotationInfo002")
public class ChbExchangeQuotationInfo002Controller extends BaseController {

    /**
     * 汇率行情service
     */
    @Resource
    private ChbExchangeQuotationInfoService chbExchangeQuotationInfoService;

    /**
     * 初始化新建页面
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.货币对, DictCode.估值来源);
        String exchargeCode = getParam("exchargeCode");
        String tradeDate = getParam("tradeDate");
        String datasourceType = getParam("datasourceType");
        String status = getParam("status");
        String datauuid = getParam("datauuid");
        String workflowId = getParam("workflowId");
        ChbExchangeQuotationInfoDto mstExchangeQuotationInfoDto = null;
        // 修改时
        if (StringUtil.isNotEmpty(exchargeCode)) {
            Map<String, Object> pageParam = new HashMap<>(4);
            pageParam.put("exchargeCodeList", StringUtil.splitToList(GlobalConstant.COMMA, exchargeCode));
            pageParam.put("tradeDate", tradeDate);
            pageParam.put("datasourceType", datasourceType);
            pageParam.put("status", status);
            pageParam.put("datauuid", datauuid);
            List<ChbExchangeQuotationInfoDto> selectAllList = chbExchangeQuotationInfoService.selectAllList(pageParam);
            if (CollectionUtils.isNotEmpty(selectAllList)) {
                mstExchangeQuotationInfoDto = selectAllList.get(0);
            }
            model.addAttribute("entity", mstExchangeQuotationInfoDto);
        } else if (StringUtil.isNotEmpty(workflowId)) {
            mstExchangeQuotationInfoDto = chbExchangeQuotationInfoService.getBussinessEntityByWorkflowId(workflowId);
            model.addAttribute("entity", mstExchangeQuotationInfoDto);
        } else {
            mstExchangeQuotationInfoDto = new ChbExchangeQuotationInfoDto();
            model.addAttribute("entity", mstExchangeQuotationInfoDto);
        }
        return super.init(model);
    }

    /**
     * 保存时的check
     *
     * @param dto    检验输入参数
     * @param result 错误参数
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({
            ValidateGroupEdit.class}) ChbExchangeQuotationInfoDto dto, BindingResult result) {
        //新建时判断业务之间是否存在
        if (!Objects.equals("edit", dto.getSubmitType())) {
            Integer count = chbExchangeQuotationInfoService.getChbExchargeQuoteByKey(dto);
            if (count > 0) {
                List<ValidMessage> messages = new ArrayList<>(1);
                messages.add(new ValidMessage("数据"+dto.getExchargeCode()+DateUtil.format(dto.getTradeDate())+dto.getDatasourceType()+"已存在,不能提交!"));
                AjaxResult m = new AjaxResult();
                m.setErrorList(messages);
                return m;
            }
        }
        return commonCheck(result, dto.getOperateType());
    }

    /**
     * 保存/修改
     *
     * @param dto 传入参数
     * @return 返回结果
     * @throws WorkFlowException 抛出异常
     */
    @ResponseBody
    @RequestMapping("save")
    public AjaxResult save(@RequestBody  @Validated({
            ValidateGroupEdit.class}) ChbExchangeQuotationInfoDto dto, BindingResult br) throws WorkFlowException {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        WorkFlowParameter<ChbExchangeQuotationInfoDto> params = new WorkFlowParameter<>();
        params.setPageId(this.getPageId());
        params.setElementId(dto.getElementId());
        params.setBusinessCode(dto.getExchargeCode()+DateUtil.format(dto.getTradeDate())+dto.getDatasourceType());
        params.setBusinessType(DictCodeList.共通业务类型_汇率行情信息.getValue());
        // 设置流程业务说明, entity即传入的dto
        params.setComment(
                entity -> "货币对" + "("
                        + SysDataUtil.getNameFromDataDict(DictCode.货币对,
                        entity.getExchargeCode())
                        + ")" + "-" + "生效日" + "(" + DateUtil.format(entity.getTradeDate()) + ")"  + "-" + "数据来源" + "(" +
                        SysDataUtil.getNameFromDataDict(DictCode.估值来源,
                                entity.getDatasourceType())+")");
        // 给参数变成ProcessParameter而非WorkFlowParameter
        ProcessParameter<ChbExchangeQuotationInfoDto> parameter = new ProcessParameter<>();
        parameter.setWorkFlowParameter(params);
        String msg = chbExchangeQuotationInfoService.start(parameter, dto);
        return new AjaxResult(msg);

    }

}
