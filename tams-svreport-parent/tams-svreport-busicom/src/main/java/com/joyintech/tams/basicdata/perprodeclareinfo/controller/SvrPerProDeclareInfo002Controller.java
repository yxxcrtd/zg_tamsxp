package com.joyintech.tams.basicdata.perprodeclareinfo.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import com.joyintech.tams.basicdata.perprodeclareinfo.service.SvrPerProDeclareInfoService001;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.exception.WorkFlowException;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 产品申报登记信息
 *
 * @author yxj
 * @date 1.0 2022年03月07日
 * @since 1.0
 */
@Controller
@PageId("SvrPerProDeclareInfo002")
@RequestMapping("/basicdata/perprodeclareinfo/SvrPerProDeclareInfo002")
public class SvrPerProDeclareInfo002Controller extends BaseController {
    @Autowired
    private SvrPerProDeclareInfoService001 svrPerProDeclareInfo001Service;

    @Override
    public String init(Model model) {
        setSelectOptionToModel(model, new DictCode[] { DictCode.产品期限, DictCode.所属国家或地区, DictCode.理财业务服务模式,
                DictCode.会计核算方式_中债, DictCode.产品资产配置方式_中债, DictCode.管理模式, DictCode.定价方式, DictCode.资金实际投向,
                DictCode.行政区划_省, DictCode.币种, DictCode.投资者风险偏好, DictCode.产品风险等级, DictCode.合作模式, DictCode.投资本金_收益_到账日,
                DictCode.有无, DictCode.是否_中债, DictCode.产品增信机构类型, DictCode.产品增信形式, DictCode.开放期业务, DictCode.规律开放周期,
                DictCode.资金投向地区, DictCode.产品运作模式, DictCode.目标客户类型, DictCode.排期发行方式, DictCode.开放模式_中债, DictCode.层级类别,
                DictCode.标的类别, DictCode.境内托管机构名称, DictCode.是否_中债, DictCode.新老产品标记,DictCode.是否_中债_期限, DictCode.产品收益类型, DictCode.产品期限,
                DictCode.产品运作模式, DictCode.管理模式, DictCode.募集方式_中债, DictCode.投资性质, DictCode.销售区域_中债,
                DictCode.产品特殊属性
        });
        if (StringUtil.isNotEmpty(this.getParam("SVR_PBC_CUSTTYPE"))) {
            model.addAttribute("SVR_PBC_CUSTTYPE", this.getParam("SVR_PBC_CUSTTYPE"));
        }
        model.addAttribute("SALETARGET_DECLARE_01", DictCodeList.目标客户类型_私人银行客户专属.getValue());
        model.addAttribute("SALETARGET_DECLARE_02", DictCodeList.目标客户类型_机构客户专属.getValue());
        model.addAttribute("YES_OR_NO_Y", DictCodeList.是否_是.getValue());
        model.addAttribute("HAS_YES", DictCodeListExt2.有无_中债_有.getValue());
        model.addAttribute("HAS_NO", DictCodeListExt2.有无_中债_无.getValue());
        model.addAttribute("YES", DictCodeList.是否_是.getValue());
        model.addAttribute("NO", DictCodeList.是否_否.getValue());
        model.addAttribute("isShortTerm_yes", DictCodeList.是否_中债_是.getValue());
        model.addAttribute("isShortTerm_no", DictCodeList.是否_中债_否.getValue());
        model.addAttribute("defalutRepRiskLevel", DictCodeListExt1.产品风险等级_一级_低.getValue());
        model.addAttribute("saleAreaDeclareIn", DictCodeListExt2.资金投向地区_境内.getValue());
        model.addAttribute("saleAreaDeclareOut", DictCodeListExt2.资金投向地区_境外.getValue());
        model.addAttribute("saleAreaDeclareInOut", DictCodeListExt2.资金投向地区_境内和境外.getValue());
        model.addAttribute("prodManageModeActM", DictCodeListExt1.产品管理模式_资产管理公司为实际管理人.getValue());
        model.addAttribute("prodManageModeOthO", DictCodeListExt1.产品管理模式_其他机构为实际管理人.getValue());
        model.addAttribute("prodManageModeBank", DictCodeListExt1.产品管理模式_银行为实际管理人.getValue());
        model.addAttribute("prodManageModeSpv", DictCodeListExt1.产品管理模式_特殊目的载体_SPV.getValue());
        model.addAttribute("copModeSigRun", DictCodeListExt2.合作模式_独立运作.getValue());
        model.addAttribute("prodRunModeCloN", DictCodeListExt2.产品运作模式_封闭式净值型.getValue());
        model.addAttribute("prodRunModeCloNN", DictCodeListExt2.产品运作模式_封闭式非净值型.getValue());
        model.addAttribute("prodRunModeOpeN", DictCodeListExt2.产品运作模式_开放式净值型.getValue());
        model.addAttribute("prodRunModeOpeNN", DictCodeListExt2.产品运作模式_开放式非净值型.getValue());
        model.addAttribute("repRaiseCcyCNY", DictCodeListExt1.币种_中债_人民币.getValue());
        // 期限品种相关
        model.addAttribute("REPTERM_02", DictCodeListExt2.产品期限_7天_含_以内.getValue());
        model.addAttribute("REPTERM_03", DictCodeListExt2.产品期限_7天_1个月_含.getValue());
        model.addAttribute("REPTERM_04", DictCodeListExt2.产品期限_1_3个月_含.getValue());
        model.addAttribute("REPTERM_05", DictCodeListExt2.产品期限_3_6个月_含.getValue());
        model.addAttribute("REPTERM_06", DictCodeListExt2.产品期限_6_12个月_含.getValue());
        model.addAttribute("REPTERM_08", DictCodeListExt2.产品期限_1_3年_含.getValue());
        model.addAttribute("REPTERM_09", DictCodeListExt2.产品期限_3年以上.getValue());

        model.addAttribute("isLayer", this.getParam("isLayer"));
        model.addAttribute("vdateElement", this.getParam("vdateElement"));
        model.addAttribute("mdateElement", this.getParam("mdateElement"));
        model.addAttribute("operationType", this.getParam("operationType"));
        model.addAttribute("openMode", this.getParam("openMode"));
        model.addAttribute("openCycle", this.getParam("openCycle"));
        model.addAttribute("openCycleUnit", this.getParam("openCycleUnit"));
        model.addAttribute("entrustType", this.getParam("entrustType"));
        model.addAttribute("repFinprodStageC", this.getParam("repFinprodStageC"));// 产品期次
        model.addAttribute("riskLevel", this.getParam("riskLevel"));// 产品风险等级
        model.addAttribute("entrustType", this.getParam("entrustType"));
        model.addAttribute("repOpenMode", this.getParam("repOpenMode"));
        model.addAttribute("repManageMode", this.getParam("repManageMode"));

        // 根据登录用户获取发行机构代码
        model.addAttribute("SVR_ISSUE_PARTY_ID", CommonUtil.selectBankCode());

        //是否子公司
        model.addAttribute("svrIsSubsidiary", CacheUtil.getSysParam("SVR_IS_SUBSIDIARY"));
        // 流程
        String workflowId = this.getParam("workflowId");

        // 增加entity
        // 查看模式和修改模式
//        if (StringUtils.equals(this.getParam(GlobalConstant.DETAIL_PAGE_MODEL_KEY), GlobalConstant.DETAIL_PAGE_MODEL_REFERENCE)
//                || StringUtils.equals(this.getParam(GlobalConstant.DETAIL_PAGE_MODEL_KEY), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)) {
//            SvrPerProDeclareInfo001Dto entity = svrPerProDeclareInfo001Service.selectDetail(getParam("declareUuid"));
//            model.addAttribute(GlobalConstant.ENTITY, entity);
//        }
        String declareUuid = getParam("declareUuid");
        String status = getParam("statusValid");
        if (!WorkFlowUtil.isNotFromToDoList() && StringUtil.isNotEmpty(workflowId)) {
            SvrPerProDeclareInfo001Dto dto = svrPerProDeclareInfo001Service.getBussinessEntityByWorkflowId(
                    workflowId);
            model.addAttribute("pageModel",dto.getPageModel());
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        else if (WorkFlowUtil.isNotFromToDoList() &&StringUtil.isNotEmpty(declareUuid) ) {
            //通过主键查询数据
            SvrPerProDeclareInfo001Dto dto = svrPerProDeclareInfo001Service.selectDetail(getParam("declareUuid"),status);
            model.addAttribute(GlobalConstant.ENTITY, dto);
        }
        return super.init(model);
    }

    /**
     * 画面点击保存按钮是时 check
     * @param dto
     * @param br
     * @return
     */
    @ResponseBody
    @RequestMapping("/check")
    public AjaxResult check(@RequestBody @Validated(
            {ValidateGroupEdit.class}) SvrPerProDeclareInfo001Dto dto, BindingResult br) {
        return commonCheck(br, dto.getOperateType());
    }

    /**
     * 保存
     * @param dto
     * @param br
     * @return
     * @throws WorkFlowException
     */
    @ResponseBody
    @RequestMapping(PAGE_SAVE)
    public AjaxResult save(@RequestBody @Validated({
            ValidateGroupEdit.class}) SvrPerProDeclareInfo001Dto dto, BindingResult br) throws WorkFlowException {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        // 新建留痕表参数和工作流参数
        ProcessParameter<SvrPerProDeclareInfo001Dto> processParameter = new ProcessParameter<SvrPerProDeclareInfo001Dto>();
        WorkFlowParameter<SvrPerProDeclareInfo001Dto> params = new WorkFlowParameter<SvrPerProDeclareInfo001Dto>();
        params.setPageId("SvrPerProDeclareInfo002");
        params.setElementId("SvrPerProDeclareInfo002_btn_submit");
        params.setBusinessCode(item -> item.getProdInsId());
        params.setBusinessType(DictCodeList.共通业务类型_产品申报信息.getValue());
        params.setComment(entity -> this.getMessage("SvrPerProDeclareInfo002.prodInsId") + "("
                + entity.getProdInsId() + ")"
           );
        // 塞入工作流参数
        processParameter.setWorkFlowParameter(params);

        dto.setCreateTime(DateUtil.now());
        dto.setCreateUser(CommonUtil.getUserName());
        dto.setUpdateTime(DateUtil.now());
        dto.setUpdateUser(CommonUtil.getUserName());
        // 流程开始
        String msg = svrPerProDeclareInfo001Service.start(processParameter, dto);
        return new AjaxResult(msg);
    }
    @RequestMapping("/selectChbPrdDeclareByProdInsId")
    @ResponseBody
    public Integer selectChbPrdDeclareByProdInsId(String prodInsId){
        return svrPerProDeclareInfo001Service.selectChbPrdDeclareByProdInsId(prodInsId);
    }
    @RequestMapping("/selectChbPrdDeclareByProdStageAndProdName")
    @ResponseBody
    public Integer selectChbPrdDeclareByProdStageAndProdName(String prodStage,String prodName){
        return svrPerProDeclareInfo001Service.selectChbPrdDeclareByProdStageAndProdName(prodStage,prodName);
    }

}
