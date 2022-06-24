package com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.controller;

import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.JoyinMessage;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.base.entity.ProcessMark;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.disclosure.common.common.dto.WflValidInputParameter;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.service.ChbPrdBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wubingbing
 * @date 2022-05-09 11:15
 */
@Controller
@PageId("CchbPrdBaseInfo003")
@RequestMapping("/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo003")
public class ChbPrdBaseInfo003Contreoller extends BaseController {
    @Autowired
    private ChbPrdBaseInfoService chbPrdBaseInfoService;

    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.报送产品发行状态);
        model.addAttribute("prodIdStr", this.getParam("prodIdStr"));
        return super.init(model);
    }
    /**
     * 发行状态管理查询
     *
     * @return 发行状态管理列表
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("queryForList")
    public List<ChbPrdBaseInfoDto> queryForList() {
        return chbPrdBaseInfoService.queryForList(this.getParam("prodIdStr"));
    }
    /**
     * 发行状态逻辑check
     *
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody ChbPrdBaseInfoDto chbPrdBaseInfoDto) {
        AjaxResult ar = new AjaxResult();
        List<ValidMessage> validList = new ArrayList<>();
        if (!StringUtil.isEmptyList(chbPrdBaseInfoDto.getSelectedItem())) {
            for (ChbPrdBaseInfoDto entity : chbPrdBaseInfoDto.getSelectedItem()) {
                if (!StringUtil.isNotEmpty(entity.getIssuingStatusNew())) {
                    validList.add(new ValidMessage(this.getMessage("FIN_PROD_E_0040")));
                }
                if (StringUtil.isNotEmpty(entity.getIssueStatusRemark())) {
                    if (entity.getIssueStatusRemark().length() > 300) {
                        validList.add(new ValidMessage(this.getMessage("FW_E_0007",
                                this.getMessage("FinProductBuild003.table.issueStatusRemark"), 300)));
                    }
                }

            }

        }

        if (StringUtil.isEmptyList(validList)){
            String btnName = "";
            if (StringUtil.equalsIgnoreCase(OPERATE_TYPE_SAVE, chbPrdBaseInfoDto.getOperateType())) {
                btnName = "SysCommon.save";
            }
            else if (StringUtil.equalsIgnoreCase(OPERATE_TYPE_SUBMIT, chbPrdBaseInfoDto.getOperateType())) {
                btnName = "SysCommon.submit";
            }
            ar.setData(this.getMessage("COMMON_C_0001", this.getMessage(btnName)));
        }
        else{
            ar.setErrorList(validList);
        }

        return ar;
    }

    @ResponseBody
    @RequestMapping("updateIssuingStatus")
    @Transactional
    public AjaxResult updateIssuingStatus(@RequestBody ChbPrdBaseInfoDto dto) {
        AjaxResult result = new AjaxResult();
        try {
            JoyinMessage joyinMessage = new JoyinMessage("修改成功",MessageType.提示消息);
            dto.getSelectedItem().forEach(e->{
                chbPrdBaseInfoService.updateIssuingStatus(e);
            });
            result.setJoyinMessages( Lists.newArrayList(joyinMessage));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
