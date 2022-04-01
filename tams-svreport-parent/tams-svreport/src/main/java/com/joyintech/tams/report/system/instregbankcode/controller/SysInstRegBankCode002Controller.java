/*
 * 文件名：SysInstRegBankCode002Controller.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：刘二通
 * 修改时间：2018年5月22日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.system.instregbankcode.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.system.institution.service.InstitutionService;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.system.instregbankcode.dto.SysInstRegBankCode002Dto;
import com.joyintech.tams.report.system.instregbankcode.service.SysInstRegBankCodeMService;

/**
 * 机构登记银行代码新建修改画面
 *
 * @author 刘二通
 * @version 1.0 2018年5月17日
 * @see SysInstRegBankCode002Controller
 * @since 1.0
 */
@Controller
@RequestMapping("/svreport/system/instregbankcode/SysInstRegBankCode002")
@PageId("SysInstRegBankCode002")
public class SysInstRegBankCode002Controller extends BaseController {

    /**
     * SysInstRegBankCode002Service
     */
    @Autowired
    private SysInstRegBankCodeMService sysInstRegBankCodemService;

    /**
     * 机构管理service
     */
    @Autowired(required = false)
    private InstitutionService institutionService;

    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        String pageModel = this.getParam("pageModel");
        String isFinSubCompany = null;
        if (!StringUtil.equals(GlobalConstant.DETAIL_PAGE_MODEL_NEW, pageModel)) {
            // 编辑
            Map<String, Object> parm = RequestUtil.getPageParam();
            SysInstRegBankCode002Dto sysInstRegBankCode002Dto = sysInstRegBankCodemService.getInfoData(parm);
            model.addAttribute(GlobalConstant.ENTITY, sysInstRegBankCode002Dto);
            isFinSubCompany = sysInstRegBankCode002Dto.getIsFinSubCompany();
        }

        // 机构代码下拉框
        List<SuggestDto> list = sysInstRegBankCodemService.getInstitutionCode(null);
        List<SelectOptionDto> resultList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(list)){
            for (SuggestDto suggestDto : list) {
                SelectOptionDto resultDto = new SelectOptionDto();
                resultDto.setId(suggestDto.getCode());
                resultDto.setText(suggestDto.getShortName());
                resultList.add(resultDto);
            }
        }
        model.addAttribute("orgCodeDataList", JsonUtils.objectArray2Json(resultList.toArray()));
        if (null != isFinSubCompany) {
        	model.addAttribute("isFinSubCompany", isFinSubCompany);
        } else {
        	model.addAttribute("isFinSubCompany", DictCodeListExt2.是否理财子公司_是.getValue());
        }
        super.setSelectOptionToModel(model, DictCode.是否理财子公司,DictCode.监管机构);
        return super.init(model);
    }

    /**
     * 画面check<br>
     *
     * @param dto dto
     * @param result BindingResult
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("checkData")
    public AjaxResult checkData(@Valid SysInstRegBankCode002Dto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
    }

    /**
     * 保存数据
     *
     * @param inputDto dto
     * @return check结果
     * @see
     * @since 1.0
     */
        @ResponseBody
        @RequestMapping("saveData")
        public AjaxResult saveData(SysInstRegBankCode002Dto inputDto) {
            String msg = this.getMessage("REPORT_COMMON_I_0006");
            sysInstRegBankCodemService.saveData(inputDto);
            return new AjaxResult(msg);
        }
}
