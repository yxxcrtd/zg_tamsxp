/*
 * 文件名：FinProductBuild002Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangliang
 * 修改时间：2018年9月19日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.product.productelement.netvalueproduct.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.base.entity.ProcessMark;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.component.business.common.service.DateCalService;
import com.joyintech.tams.component.business.core.service.BaseGetBusinessId;
import com.joyintech.tams.component.business.prod.dto.BussFinProductFeeInfoDto;
import com.joyintech.tams.component.business.prod.dto.BussFinProductFundAccountInfoDto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import com.joyintech.tams.invest.common.dto.common.BussinessCodeParameter;
import com.joyintech.tams.invest.product.productelement.module.service.FinProductModule001Service;
import com.joyintech.tams.invest.product.productelement.netvalueproduct.dto.FinNetValueProductBuild002Dto;
import com.joyintech.tams.invest.product.productelement.netvalueproduct.service.FinNetValueProductBuild002Service;
import com.joyintech.tams.systemtools.dto.PageElementConfigDto;
import com.joyintech.tams.systemtools.util.PageElementUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.exception.WorkFlowException;

//import org.json.JSONObject;

/**
 * 产品信息新建
 * 
 * @author zhangliang
 * @version 1.0 2018年9月19日
 * @see FinNetValueProductBuild002Controller
 * @since 1.0
 */
@Controller
@PageId(value = "FinNetValueProductBuild002")
@RequestMapping("/invest/product/productelement/netvalueproduct/FinNetValueProductBuild002")
public class FinNetValueProductBuild002Controller extends BaseController {

    /**
     * {@linkplain}
     */
    @Autowired
    private FinNetValueProductBuild002Service finNetValueProductBuild002Service;
    

    /**
     * {@linkplain}
     */
    @Autowired
    private DateCalService dateCalService;

    /**
     * {@linkplain}
     */
    @Autowired(required = false)
    private BaseGetBusinessId bussGetBussinessIdService;
    
    @Autowired(required = false)
    private FinProductModule001Service finProductModule001Service;

    /**
     * {@inheritDoc}
     */
    @Override
    public String init(Model model) {
    	
        this.setSelectOptionToModel(model, DictCode.处理模式, DictCode.收益类型,
            DictCode.开放类型, DictCode.利率类型, DictCode.分层类型,
            DictCode.产品收益标识,
            DictCode.排期发行方式, DictCode.计息基础,
            DictCode.币种, DictCode.销售部门, DictCode.募集方式,
            DictCode.人行客户类型,DictCode.期限类型,
            DictCode.投资性质, DictCode.销售模式,DictCode.是否_中债,DictCode.是否,DictCode.分红方式,DictCode.产品风险等级,
            DictCode.产品系列, DictCode.产品特点, DictCode.销售类型,DictCode.产品数据来源/*, DictCode.目标客户一级分类, DictCode.目标客户二级分类*/,
            DictCode.产品分类     
        );
        model.addAttribute("YES", DictCodeList.是否_是.getValue());
        model.addAttribute("NO", DictCodeList.是否_否.getValue());
        model.addAttribute("BASIS_A_365", DictCodeList.计息基础_A_365.getValue());
//        model.addAttribute("RAISETYPE_01", DictCodeListExt1.募集方式_公募.getValue());
        model.addAttribute("INVEST_TYPE_01", DictCodeList.投资性质_固定收益类.getValue());
        model.addAttribute("DISPOSE_PATTERN_01", DictCodeList.处理模式_普通.getValue());
        model.addAttribute("DISPOSE_PATTERN_03", BussinessDictCodeList.处理模式_滚动N天.getValue());
        model.addAttribute("DISPOSE_PATTERN_04", BussinessDictCodeList.处理模式_普通N天.getValue());
        model.addAttribute("DISPOSE_PATTERN_05", BussinessDictCodeList.处理模式_开放阶梯.getValue());
        model.addAttribute("DISPOSE_PATTERN_08", BussinessDictCodeList.处理模式_货币型.getValue());
        model.addAttribute("DISPOSE_PATTERN_09", BussinessDictCodeList.处理模式_净值型.getValue());
        //add by wm 2019-10-09 start 增加产品处理模式-周期型
        model.addAttribute("DISPOSE_PATTERN_10", DictCodeList.处理模式_周期型.getValue());
        //add by wm 2019-10-09 end 增加产品处理模式-周期型
        model.addAttribute("DISPOSE_PATTERN_11", BussinessDictCodeList.处理模式_分档.getValue());
        model.addAttribute("PROFIT_TYPE_01", DictCodeList.收益类型_净值型.getValue());
        model.addAttribute("PROFIT_TYPE_02", DictCodeList.收益类型_收益型.getValue());
        model.addAttribute("PROFIT_TYPE_03", DictCodeList.收益类型_货币型.getValue());
        model.addAttribute("TERM_TYPE_01", DictCodeListExt2.期限类型_单一固定期限.getValue());
        model.addAttribute("TERM_TYPE_02", DictCodeListExt2.期限类型_多固定期限.getValue());
        model.addAttribute("TERM_TYPE_03", DictCodeListExt2.期限类型_无固定期限.getValue());
        model.addAttribute("ISSUE_MODE_01", DictCodeList.开放类型_封闭式.getValue());
        model.addAttribute("ISSUE_MODE_02", DictCodeList.开放类型_开放式.getValue());
        model.addAttribute("finprodTypeProd", DictCodeListExt1.金融产品类型_投管用_理财产品.getValue());
        model.addAttribute("finprodTypeTmpl", DictCodeListExt1.金融产品类型_投管用_系列模板.getValue());
        model.addAttribute("SCHEDUAL_ISSUE_02", DictCodeList.排期发行方式_单一.getValue());
        model.addAttribute("PROFIT_FLAG_03", DictCodeList.产品收益标识_非保本浮动收益.getValue());
        
        model.addAttribute("DISPOSE_PATTERN_10", BussinessDictCodeList.处理模式_周期型.getValue());
        //add yzg 20191020 start
        model.addAttribute("BASIC_BILL_04", DictCodeList.计费基数_费用品种_存续本金.getValue());
        model.addAttribute("BASE_DATE_TYPE_01", DictCodeList.基数日期类型_当日.getValue());
        model.addAttribute("BASE_DATE_TYPE_02", DictCodeList.基数日期类型_前一自然日.getValue());
        model.addAttribute("BASE_DATE_TYPE_03", DictCodeList.基数日期类型_前一工作日.getValue());
        model.addAttribute("BILL_BASE_FEE_KIND_03", DictCodeList.计费基数_费用品种_资产规模.getValue());
        //add yzg 20191020 end
        //数据来源
        model.addAttribute("PROD_DATA_SOURCE_02", DictCodeListExt3.产品数据来源_三方分销TA.getValue());
        model.addAttribute(GlobalConstant.DETAIL_PAGE_MODEL_KEY,
            this.getParam((GlobalConstant.DETAIL_PAGE_MODEL_KEY)));

        model.addAttribute(GlobalConstant.WORKFLOW_ID, "");
        model.addAttribute("finprodId", "");
        model.addAttribute("SVR_HAVE_OR_NOT_02", DictCodeListExt2.有无_中债_无.getValue());
        // 待处理模式时根据金融产品代码查询排期基本信息
        model.addAttribute("confirmFlag", DictCodeList.是否_否.getValue());
        //产品信息与申报信息是否合并
        model.addAttribute("productIsMerger", CacheUtil.getSysParam(BussinessConstant.PRODUCTT_IS_MERGER));
        
        //add by wangyang1 [吉林个性化] 2019年9月26日 start 
        model.addAttribute("bankCode", CacheUtil.getSysParam("CUSTOMER_BANK_CODE"));
        //add by wangyang1 [吉林个性化] end
        
        //获取系统参数 20191125 chenxiaoqiu add
        model.addAttribute("isDisplayDate",CacheUtil.getSysParam(BussinessConstant.IsDisplayDate));
        //获取系统参数 20191125 chenxiaoqiu end
        if (StringUtil.isNotEmpty(this.getParam("confirmFlag"))) {
            // 根据排期金融产品代码查询排期信息
            FinNetValueProductBuild002Dto finProductBuild002Dto = finNetValueProductBuild002Service.queryProdScheduleByFinprodId(
                this.getParam("finprodId"));
            model.addAttribute(GlobalConstant.ENTITY, finProductBuild002Dto);

            model.addAttribute("confirmFlag", DictCodeList.是否_是.getValue());
            model.addAttribute("finprodId", this.getParam("finprodId"));
            model.addAttribute("effDate", DateUtil.format(finProductBuild002Dto.getVdate()));
            // 采番生成费用新建时缓存key值
            BussinessCodeParameter parameter = new BussinessCodeParameter();
            parameter.setSeqNo("9995");
            model.addAttribute("feeNewAddId", bussGetBussinessIdService != null ? bussGetBussinessIdService.buildBussinessCode(
                parameter) : IDGenerator.generate());
        }
        else {
            // 流程中数据
            if (StringUtil.equals(this.getParam("isTodoList"), "true")) {
                ProcessMark pro = finNetValueProductBuild002Service.getBusinessInfo(
                    this.getParam(GlobalConstant.WORKFLOW_ID));
                //add by niuqianyu 2019-07-18 start 根据工作流与留痕处理提供的共通方法获取业务实体
                FinNetValueProductBuild002Dto finNetValueProductBuild002Dto = finNetValueProductBuild002Service.getBussinessEntityByWorkflowId(getParam(GlobalConstant.WORKFLOW_ID));
                //add by niuqianyu 2019-07-18 end

                if (pro != null) {
                	FinNetValueProductBuild002Dto entity = (FinNetValueProductBuild002Dto)JsonUtils.json2Object(
                        pro.getBusinessData(), FinNetValueProductBuild002Dto.class);

                    this.setViewSelectOptionToModel(entity.getCreateUser(),model,DictCode.产品系列);

                    //add by niuqianyu 2019-07-18 start
                    entity.setFjId(finNetValueProductBuild002Dto.getFjId());
                    //add by niuqianyu 2019-07-18 end
                    model.addAttribute(GlobalConstant.ENTITY, entity);
                    if(entity.getBussFinProductBonusPlanDto()!=null)//add by zhangweiqi 2019-3-14 改成非必填
                    entity.setPayType(entity.getBussFinProductBonusPlanDto().getPayType());//add by zhangweiqi 代办事项分红类型不展示问题
                    model.addAttribute("funDto",entity.getBussFinProductFundAccountInfoDto());
                    model.addAttribute("finprodId", entity.getFinprodId());
                    model.addAttribute("feeNewAddId", entity.getFeeNewAddId());
                    model.addAttribute(GlobalConstant.WORKFLOW_ID,
                        this.getParam(GlobalConstant.WORKFLOW_ID));
                    model.addAttribute("LayeredDto", entity.getBussFinProductLayeredInfoDto());
                }
            }
            // 编辑查看数据
            else if (StringUtil.isNotEmpty(this.getParam("finprodId"))) {
                String finprodId = this.getParam("finprodId");
                model.addAttribute("finprodId", finprodId);
                String effectStatus = this.getParam("effectStatus");
                // 区分是排期数据还是新建数据
                if (StringUtil.equals(effectStatus, DictCodeListExt1.有效状态_待处理.getCodeName())){
                    // 根据排期金融产品代码查询排期信息
                	FinNetValueProductBuild002Dto finProductBuild002Dto = finNetValueProductBuild002Service.queryProdScheduleByFinprodId(
                        this.getParam("finprodId"));

                    this.setViewSelectOptionToModel(finProductBuild002Dto.getCreateUser(),model,DictCode.产品系列);

                    model.addAttribute(GlobalConstant.ENTITY, finProductBuild002Dto);

                    model.addAttribute("confirmFlag", DictCodeList.是否_是.getValue());
                    model.addAttribute("finprodId", this.getParam("finprodId"));
                    model.addAttribute("effDate", DateUtil.format(finProductBuild002Dto.getVdate()));
                }
                else{
                	FinNetValueProductBuild002Dto finProductBuild002Dto = finNetValueProductBuild002Service.queryProdInfoByFinprodId(
                    finprodId);
                    this.setViewSelectOptionToModel(finProductBuild002Dto.getCreateUser(),model,DictCode.产品系列);

                    model.addAttribute("funDto",finProductBuild002Dto.getBussFinProductFundAccountInfoDto());
                	 //add by niuqianyu 2019-07-18 start
                     finProductBuild002Dto.setFjId(finProductBuild002Dto.getFinprodId());
                     //add by niuqianyu 2019-07-18 end
                model.addAttribute(GlobalConstant.ENTITY, finProductBuild002Dto);
                model.addAttribute("effDate", DateUtil.format(finProductBuild002Dto.getVdate()));
                model.addAttribute("LayeredDto", finProductBuild002Dto.getBussFinProductLayeredInfoDto());
                    if (StringUtil.equals(this.getParam("pageModel"), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){
                        // 采番生成费用新建时缓存key值
                        BussinessCodeParameter parameter = new BussinessCodeParameter();
                        parameter.setSeqNo("9995");
                        model.addAttribute("feeNewAddId", bussGetBussinessIdService != null ? bussGetBussinessIdService.buildBussinessCode(
                            parameter) : IDGenerator.generate());
                    }
                }
            }
            else if (StringUtil.equals(this.getParam("pageModel"), GlobalConstant.DETAIL_PAGE_MODEL_NEW)){
                // 采番生成费用新建时缓存key值
                BussinessCodeParameter parameter = new BussinessCodeParameter();
                parameter.setSeqNo("9995");
                model.addAttribute("feeNewAddId", bussGetBussinessIdService != null ? bussGetBussinessIdService.buildBussinessCode(
                    parameter) : IDGenerator.generate());
                //add by niuqianyu 2019-07-18 start
                FinNetValueProductBuild002Dto dto =new FinNetValueProductBuild002Dto();
                dto.setFjId("fj" + System.currentTimeMillis());
                model.addAttribute(GlobalConstant.ENTITY, dto);
                //add by niuqianyu 2019-07-18 end
            }
        }
        //画面元素是否显示
        PageElementConfigDto pageElementConfigDto = new PageElementConfigDto();
        pageElementConfigDto.setPageId(this.getPageId());
        Map<String, String> isEleShow = PageElementUtil.isPageElementShow(pageElementConfigDto);
        model.addAttribute("isEleShow", isEleShow);
        //添加004权限控制 add by liupeilin 2020-02-26 begin
        if("004".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
            model.addAttribute("BANK_CODE", "004");
        }
        //添加004权限控制 add by liupeilin 2020-02-26 end
        return super.init(model);
    }

    /**
     * 画面check
     * 
     * @param dto 验证对象
     * @param br 验证信息
     * @return 验证结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@Valid @RequestBody FinNetValueProductBuild002Dto dto, BindingResult br) {
        if (br.hasErrors()) {
            AjaxResult ar = new AjaxResult(br);
            List<ValidMessage> errorList = ar.getErrorList();
            // 流程中数据判断
            for (ValidMessage validMessage : errorList) {
                if (StringUtil.indexOf(validMessage.getMessage(),
                    this.getMessage("COMMON_E_0012").substring(0,
                        this.getMessage("COMMON_E_0012").length() - 3)) >= 0) {
                    validMessage.setMessage(this.getMessage(
                        "COMMON_E_0012", dto.getFinprodMarketId() + "," + dto.getFinprodName()
                                         + "," + dto.getFinprodAbbr()));
                }
            }
            return ar;
        }

        AjaxResult ar = new AjaxResult();

        //开放式 的产品 是否设置最短持有期限 和 最短持有期限（天） 必输 yjm start 20190807
        List<ValidMessage> errorList = new ArrayList<>();
//        BussRepFinProductInfoDto repFinProductInfoDto = dto.getBussRepFinProductInfoDto();
//        if (StringUtil.isNotEmpty(repFinProductInfoDto)){
//            if (StringUtil.equals(dto.getOperationType(), DictCodeList.开放类型_开放式.getValue())){
//                if (StringUtil.isEmpty(repFinProductInfoDto.getIsShortTerm())){
//                    errorList.add(new ValidMessage("bussRepFinProductInfoDto.isShortTerm", this.getMessage("BUSS_COM_E_0038",
//                            this.getMessage("BussRepFinProductInfo.isShortTerm"))));
//                }else {
//                    //modify by wm 增加只有当是否设置最短持有期限为是时，最短持有期限(天)才必输
//                    if (!StringUtil.isNotEmpty(repFinProductInfoDto.getShortTerm())&&StringUtil.equals(repFinProductInfoDto.getIsShortTerm(), DictCodeList.是否_中债_是.getValue())){
//                        errorList.add(new ValidMessage("bussRepFinProductInfoDto.shortTerm", this.getMessage("BUSS_COM_E_0038",
//                                this.getMessage("BussRepFinProductInfo.shortTerm"))));
//                    }
//                    if (!StringUtil.isNotEmpty(repFinProductInfoDto.getIsFreeRedem()) && StringUtil.equals(repFinProductInfoDto.getIsShortTerm(), DictCodeList.是否_中债_是.getValue())){
//                        errorList.add(new ValidMessage("bussRepFinProductInfoDto.isFreeRedem", this.getMessage("BUSS_COM_E_0038",
//                                this.getMessage("BussRepFinProductInfo.isFreeRedem"))));
//                    }
//                }
//
//            }
//
//            if(StringUtil.isNotEmpty(dto.getProfitType()) && StringUtil.isNotEmpty(dto.getOperationType()) && StringUtil.isNotEmpty(repFinProductInfoDto.getRepOperateMode())){
//                if (StringUtil.equals(dto.getOperationType(), DictCodeList.开放类型_开放式.getValue())){
////                    if (StringUtil.equals(dto.getProfitType(), DictCodeList.收益类型_收益型.getValue())){
////                        if (!StringUtil.equals(repFinProductInfoDto.getRepOperateMode(), DictCodeListExt2.产品运作模式_开放式非净值型.getValue())){
////                            errorList.add(new ValidMessage("bussRepFinProductInfoDto.operationType",this.getMessage("FIN_PROD_E_0301")));
////                        }
////                    }
//                    if (StringUtil.equals(dto.getProfitType(), DictCodeList.收益类型_净值型.getValue()) ||
//                            StringUtil.equals(dto.getProfitType(), DictCodeList.收益类型_货币型.getValue())){
//                        if (!StringUtil.equals(repFinProductInfoDto.getRepOperateMode(), DictCodeListExt2.产品运作模式_开放式净值型.getValue())){
//                            errorList.add(new ValidMessage("bussRepFinProductInfoDto.operationType",this.getMessage("FIN_PROD_E_0301")));
//                        }
//                    }
//                }
//                if (StringUtil.equals(dto.getOperationType(), DictCodeList.开放类型_封闭式.getValue())){
////                    if (StringUtil.equals(dto.getProfitType(), DictCodeList.收益类型_收益型.getValue())){
////                        if (!StringUtil.equals(repFinProductInfoDto.getRepOperateMode(), DictCodeListExt2.产品运作模式_封闭式非净值型.getValue())){
////                            errorList.add(new ValidMessage("bussRepFinProductInfoDto.operationType",this.getMessage("FIN_PROD_E_0301")));
////                        }
////                    }
//                    if (StringUtil.equals(dto.getProfitType(), DictCodeList.收益类型_净值型.getValue()) ||
//                            StringUtil.equals(dto.getProfitType(), DictCodeList.收益类型_货币型.getValue())){
//                        if (!StringUtil.equals(repFinProductInfoDto.getRepOperateMode(), DictCodeListExt2.产品运作模式_封闭式净值型.getValue())){
//                            errorList.add(new ValidMessage("bussRepFinProductInfoDto.operationType",this.getMessage("FIN_PROD_E_0301")));
//                        }
//                    }
//                }
//            }
//        }
        
        // add by zhangJin 20191022 start 校验：封闭式产品 浮动管理费 首次计提日 只能 产品到期日 或 产品到期日-1
        if (dto.getBussFinProductFloatFeeDto() != null
            && StringUtil.isNotEmpty(dto.getBussFinProductFloatFeeDto().getProvisionFirstDay())) {
            if (StringUtil.isNotEmpty(dto.getOperationType())
                && StringUtil.equals(dto.getOperationType(), DictCodeList.开放类型_封闭式.getValue())
                && StringUtil.isNotEmpty(dto.getMdate())) {
                long days = DateUtil.getBetweenDayNumber(
                    dto.getBussFinProductFloatFeeDto().getProvisionFirstDay(), dto.getMdate());
                if (days > 1 || days < 0) {
                    errorList.add(new ValidMessage("封闭式产品 浮动管理费 首次计提日 只能 产品到期日 或 产品到期日-1"));
                }
            }
            // add by zhangJin 20191022 end 
        }
        
        ar.setErrorList(errorList);
        //开放式 的产品 是否设置最短持有期限 和 最短持有期限（天） 必输 yjm start 20190807


        StringBuffer sb = new StringBuffer();
        // 判断产品起息日和到期日是否为节假日,给出提示信息
        if (dateCalService.isDealCalendarHoliday(dto.getVdate(), dto.getCalendarId())) {
            sb.append(
                this.getMessage("BUSS_COM_E_0039", this.getMessage("FinProductBuild002.vdate"))
                      + GlobalConstant.HTML_NEWLINE);
        }
        if (dateCalService.isDealCalendarHoliday(dto.getMdate(), dto.getCalendarId())) {
            sb.append(
                this.getMessage("BUSS_COM_E_0039", this.getMessage("FinProductBuild002.mdate"))
                      + GlobalConstant.HTML_NEWLINE);
        }
        // 付费计划首次计提日未节假日
        if (dto.getBussFinProductFeeInfoDto() != null) {
            List<BussFinProductFeeInfoDto> bussFinProductFeeInfoDtoList = dto.getBussFinProductFeeInfoDto().getBussFinProductFeeInfoDtoList();
            if (!StringUtil.isEmptyList(bussFinProductFeeInfoDtoList)) {
                for (BussFinProductFeeInfoDto bussFinProductFeeInfoDto : bussFinProductFeeInfoDtoList) {
                    if (StringUtil.isNotEmpty(bussFinProductFeeInfoDto.getProvisionFirstDay())
                        && dateCalService.isDealCalendarHoliday(
                            bussFinProductFeeInfoDto.getProvisionFirstDay(),
                            dto.getCalendarId())) {
                        sb.append(
                            this.getMessage("BUSS_COM_E_0039",
                                this.getMessage("BussFinProductFeeInfo.provisionFirstDay"))
                                  + GlobalConstant.HTML_NEWLINE);
                        break;
                    }
                }
            }
        }

        // 浮动管理费 首次计提日是否为节假日,给出提示信息
        if (dto.getBussFinProductFloatFeeDto() != null
            && StringUtil.isNotEmpty(dto.getBussFinProductFloatFeeDto().getProvisionFirstDay())) {
            if (dateCalService.isDealCalendarHoliday(
                dto.getBussFinProductFloatFeeDto().getProvisionFirstDay(), dto.getCalendarId())) {
                sb.append(
                    this.getMessage("BUSS_COM_E_0039",
                        this.getMessage("BussFinProductFloatFee.provisionFirstDay"))
                          + GlobalConstant.HTML_NEWLINE);
            }
        }
       
        // 分红信息 首次分红日是否为节假日,给出提示信息
        
        if (dto.getBussFinProductBonusPlanDto() != null
            && StringUtil.isNotEmpty(dto.getBussFinProductBonusPlanDto().getFirstPayDate())) {
            if (dateCalService.isDealCalendarHoliday(
                dto.getBussFinProductBonusPlanDto().getFirstPayDate(), dto.getCalendarId())) {
                sb.append(
                    this.getMessage("BUSS_COM_E_0039",
                        this.getMessage("BussFinProductBonusPlan.firstPayDate"))
                          + GlobalConstant.HTML_NEWLINE);
            }
        }
        // 开放信息 首次开放日是否为节假日,给出提示信息
        if (dto.getBussFinProductOpenPlanDto() != null
            && StringUtil.isNotEmpty(dto.getBussFinProductOpenPlanDto().getFirstOpenDate())) {
            if (dateCalService.isDealCalendarHoliday(
                dto.getBussFinProductOpenPlanDto().getFirstOpenDate(), dto.getCalendarId())) {
                sb.append(
                    this.getMessage("BUSS_COM_E_0039",
                        this.getMessage("FinProductOpenRule.firstOpenDate"))
                          + GlobalConstant.HTML_NEWLINE);
            }
        }
        // 付息信息 首次付息日是否为节假日,给出提示信息
        if (dto.getBussFinProductPayPlanDto() != null
            && StringUtil.isNotEmpty(dto.getBussFinProductPayPlanDto().getFirstPayDate())) {
            if (dateCalService.isDealCalendarHoliday(
                dto.getBussFinProductPayPlanDto().getFirstPayDate(), dto.getCalendarId())) {
                sb.append(
                    this.getMessage("BUSS_COM_E_0039",
                        this.getMessage("BussFinProductPayPlan.firstPayDate"))
                          + GlobalConstant.HTML_NEWLINE);
            }
        }
        //【是否轧差】选择是，保存、提交时验证是否挂了募集户，提示信息：“产品需要轧差生成指令，但是未维护募集户，是否继续？”
        if(StringUtil.isNotEmpty(dto.getLiquidationYesno()) && dto.getLiquidationYesno().equals("Y")){
            boolean isMjh=false;
               if(null!=dto.getBussFinProductFundAccountInfoDto() && dto.getBussFinProductFundAccountInfoDto().size()>0 ){
                    for(BussFinProductFundAccountInfoDto fundAccountInfoDto:dto.getBussFinProductFundAccountInfoDto()){
                              if("B15".equals(fundAccountInfoDto.getAcctType())){
                                  isMjh=true;
                              }
                    }
               }
            if(!isMjh){
                sb.append(
                        this.getMessage("产品需要轧差生成指令，但是未维护募集户，是否继续？", this.getMessage("FinNetValueProductBuild002_liquidationYesno"))
                                + GlobalConstant.HTML_NEWLINE);
            }
        }
        if (StringUtil.isNotEmpty(sb)) {
            ar.setData(sb.substring(0, sb.length() - GlobalConstant.HTML_NEWLINE.length()));
        }
        else {
            String btnName = "";
            if (StringUtil.equalsIgnoreCase(OPERATE_TYPE_SAVE, dto.getOperateType())) {
                btnName = "SysCommon.save";
            }
            else if (StringUtil.equalsIgnoreCase(OPERATE_TYPE_SUBMIT, dto.getOperateType())) {
                btnName = "SysCommon.submit";
            }
            ar.setData(this.getMessage("COMMON_C_0001", this.getMessage(btnName)));
        }

        return ar;
    }

    /**
     * 保存操作
     *
     * @param dto dto
     * @return 结果
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("save")
    public AjaxResult save(@RequestBody FinNetValueProductBuild002Dto dto) throws WorkFlowException {
        ProcessParameter<FinNetValueProductBuild002Dto> processParameter = new ProcessParameter<FinNetValueProductBuild002Dto>();
        WorkFlowParameter<FinNetValueProductBuild002Dto> params = new WorkFlowParameter<FinNetValueProductBuild002Dto>();
        params.setElementId(dto.getElementId());
        params.setPageId(this.getPageId());
        params.setBusinessCode(dto.getFinprodMarketId());
        params.setBusinessType(DictCodeList.共通业务类型_产品要素管理.getValue());
        //dingjunfeng修改
        if(dto.getBussRepFinProductInfoDto() != null){
        	dto.setEntrustType(dto.getBussRepFinProductInfoDto().getRepManageMode());
        }
        
        // 设置工作流注解
        params.setComment(
            entity -> this.getMessage("FinProductBuild002.finprodMarketId") + "("
                      + entity.getFinprodMarketId() + ")"
                      + this.getMessage("FinProductBuild002.finprodAbbr") + "("
                      + entity.getFinprodAbbr() + ")" + this.getMessage("FinProductBuild002.vdate")
                      + "(" + DateUtil.format(entity.getVdate()) + ")"
                      + this.getMessage("FinProductBuild002.mdate") + "("
                      + DateUtil.format(entity.getMdate()) + ")");
        processParameter.setWorkFlowParameter(params);
        
        // 调用工作流事件
        String msg = finNetValueProductBuild002Service.start(processParameter, dto);
        // 返回工作流错误信息
        return new AjaxResult(msg);
    }
    
    /**
     * 产品设计系列查询
     * @return
     */
    @ResponseBody
    @RequestMapping("queryPrdDesign")
    public List<SelectOptionDto> queryPrdDesign(){
    	return finNetValueProductBuild002Service.queryPrdDesign(RequestUtil.getPageParam());
    }
    /**
     * 查询机构
     *
     * @param dto dto
     * @return 结果
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("queryTrusteeId")
    public Page<SelectOptionDto> queryTrusteeId() {
    	Map<String, Object> map = RequestUtil.getPageParam();
    	String pageModel = (String) map.get("pageModel");
    	if(pageModel != null && StringUtil.equals(pageModel, GlobalConstant.DETAIL_PAGE_MODEL_REFERENCE)) {
    		map.put("search", map.get("initValue"));
    	}
    	return finNetValueProductBuild002Service.queryTrusteeId(map);
    }
    /**
     * 查询下一个工作日
     *
     * @param dto dto
     * @return 结果
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("getNextWorkDay")
    public Date getNextWorkDay(@RequestBody FinNetValueProductBuild002Dto finNetValueProductBuild002Dto) { 
    	
    	Date a = finNetValueProductBuild002Dto.getMdate();
    	boolean flag = false;
    	while(!flag){
    		a = DateUtil.addDay(a, 1);
    		//判断是否为节假日	
    		if(!dateCalService.isDealCalendarHoliday(a, finNetValueProductBuild002Dto.getCalendarId())){
    			flag = true;
    		}
    	}
    	return a;
    }
    /**
     * 查询最近工作日
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("getNearWorkDay")
    public Date getNearWorkDay(@RequestBody FinNetValueProductBuild002Dto finNetValueProductBuild002Dto) { 
    	
    	Date a = finNetValueProductBuild002Dto.getMdate();
    	boolean flag = false;
    	while(!flag){
    		//判断是否为节假日	
    		if(!dateCalService.isDealCalendarHoliday(a, finNetValueProductBuild002Dto.getCalendarId())){
    			flag = true;
    		}
    		else {
    			a = DateUtil.addDay(a, 1);
    		}
    	}
    	return a;
    }
    /**
     * 判断是否为节假日
     *
     * @param dto dto
     * @return 结果
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("isHoliday")
    public boolean isHoliday(@RequestBody FinNetValueProductBuild002Dto finNetValueProductBuild002Dto) {
    	boolean flages = false;
    	Date vdate = finNetValueProductBuild002Dto.getVdate();
    	Date mdate = finNetValueProductBuild002Dto.getMdate();
    	if(vdate != null){
			//判断是否为节假日	
			if(dateCalService.isDealCalendarHoliday(vdate, finNetValueProductBuild002Dto.getCalendarId())){
				flages = true;
			}
    	}
    	if(mdate != null){
			//判断是否为节假日	
			if(dateCalService.isDealCalendarHoliday(mdate, finNetValueProductBuild002Dto.getCalendarId())){
				flages = true;
			}
    	}
    	return flages;
    }
    
    /**
     * 获取模板名称的联想下拉框
     * <一句话功能简述>
     * <功能详细描述>
     * @param  param param
     * @return SelectOptionDto
     * @throws Exception Exception
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("/getTempletNameList")
    public Page<SelectOptionDto> getTempletNameList(){
        Map<String , Object> map = RequestUtil.getPageParam();
        List<String> profitTypeList = new ArrayList<String>();
        profitTypeList.add(DictCodeList.收益类型_净值型.getValue());
        profitTypeList.add(DictCodeList.收益类型_货币型.getValue());
        map.put("profitTypeList", profitTypeList);
        return finProductModule001Service.getTempletNameList(map);
    }

    //add by mashukai BS-1995草稿箱同业机构字段反显没有值问题 20201113 start
    @Override
    public Object after(String draftobj, Model model) {
        FinNetValueProductBuild002Dto dto = JsonUtils.json2Object(draftobj,FinNetValueProductBuild002Dto.class);
        if(StringUtil.isNotEmpty(dto.getSameOrg())){
            model.addAttribute("sameOrg",dto.getSameOrg());
        }
        return super.after(draftobj, model);
    }
    //add by mashukai BS-1995草稿箱同业机构字段反显没有值问题 20201113 end

}
