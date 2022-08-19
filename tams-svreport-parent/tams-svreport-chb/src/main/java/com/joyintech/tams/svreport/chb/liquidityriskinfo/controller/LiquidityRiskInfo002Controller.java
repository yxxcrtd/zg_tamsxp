package com.joyintech.tams.svreport.chb.liquidityriskinfo.controller;


import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dto.LiquidityRiskInfo001Dto;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.service.LiquidityRiskInfo001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 流动性风险应对情况信息维护Controller
 *
 * @author zhoubin
 * @create 2022/08/16
 */
@Controller
@PageId("LiquidityRiskInfo002")
@RequestMapping("/chb/liquidityriskinfo/LiquidityRiskInfo002")
public class LiquidityRiskInfo002Controller extends BaseController {

    @Autowired
    private LiquidityRiskInfo001Service liquidityRiskInfo001Service;


    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        super.setSelectOptionToModel(model, DictCode.流动性风险应对措施);
        String seqno = this.getParam("seqno");
        if (StringUtil.isNotBlank(seqno)) {
            //查询详情
            LiquidityRiskInfo001Dto entity = liquidityRiskInfo001Service.selectByPrimaryKey(seqno);
            model.addAttribute("entity", entity);
        }
        return super.init(model);
    }


    /**
     * 新增
     *
     * @param Dto
     * @return
     */
    @ResponseBody
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody @Validated({ValidateGroupEdit.class}) LiquidityRiskInfo001Dto Dto, BindingResult br) {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        this.liquidityRiskInfo001Service.insert(Dto);
        AjaxResult wb = new AjaxResult(this.getMessage("REPORT_COMMON_I_0006"));
        return wb;
    }

}
