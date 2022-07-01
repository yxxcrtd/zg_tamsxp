package com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.AssetInfoDetailDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto.ChbBokSubjectDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.service.ChbInvestTradeService;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto.ChbMstBondCreditRatingDto;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wubingbing
 * @date 2022-04-27 14:11
 */
@Controller
@PageId("CchbInvestTrade001")
@RequestMapping("/chb/businessdatamaintain/chbinvesttrade/chbinvesttrade001")
public class ChbInvestTrade001Controller extends BaseController {
    @Autowired
    private ChbInvestTradeService chbInvestTradeService;
    @Override
    public String init(Model model) {
        Date date = DateUtil.platDateFromCache();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        model.addAttribute("sysDate",DateUtil.format(calendar.getTime()));
        this.setSelectOptionToModel(model, DictCode.维护数据状态);
        return super.init(model);
    }

    /**
     * 产品名称
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProdName")
    public Page<SelectOptionDto> getProdName()
    {
        return chbInvestTradeService.getProdName(RequestUtil.getPageParam());
    }
    /**
     * 产品登记编码
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRegCode")
    public Page<SelectOptionDto> getRegCode()
    {
        return chbInvestTradeService.getRegCode(RequestUtil.getPageParam());
    }

    /**
     * 行内资产/负债编码
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAssetCode")
    public Page<SelectOptionDto> getAssetCode()
    {
        return chbInvestTradeService.getAssetCode(RequestUtil.getPageParam());
    }

    /**
     * 交易对手
     * @return
     */
    @ResponseBody
    @RequestMapping("/getCustomerName")
    public Page<SelectOptionDto> getCustomerName()
    {
        return chbInvestTradeService.getCustomerName(RequestUtil.getPageParam());
    }
    /**
     * 检索页面列表
     *
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<ChbInvestTradeDto> selectAll()
    {

        return chbInvestTradeService.selectForPage(RequestUtil.getPageParam());
    }
    /**
     *  删除
     * @param dto 页面选中的数据
     * @param br br
     * @return 结果
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping(ACTION_DELETE)
    public AjaxResult delete(@RequestBody  ChbInvestTradeDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        ProcessParameter<ChbInvestTradeDto> processParameter = new ProcessParameter<ChbInvestTradeDto>();
        WorkFlowParameter<ChbInvestTradeDto> params = new WorkFlowParameter<ChbInvestTradeDto>();
        params.setPageId("CchbInvestTrade002");
        params.setElementId("CchbInvestTrade001_btn_delete");
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
        processParameter.setWorkFlowParameter(params);
        dto.getSelectedItem().forEach(e->{
            e.setCreateUser(CommonUtil.getUserName());
            e.setCreateTime(DateUtil.now());
            e.setUpdateUser(CommonUtil.getUserName());
            e.setUpdateTime(DateUtil.now());
        });
        String msg = chbInvestTradeService.start(processParameter,dto.getSelectedItem().toArray(new ChbInvestTradeDto[dto.getSelectedItem().size()]));
        return new AjaxResult(msg);

    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForImportData(String fileName) throws Exception {
        return chbInvestTradeService.checkConfirmMsgForImportData(fileName);
    }


    /**
     * 导入（数据保存）
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("importFileSave")
    public List<ExcelValidDto> importFileSave(String fileName)
            throws Exception {

        return chbInvestTradeService.importFileSave(fileName);
    }

    /**
     * 导出信息excel
     *
     * @return excel
     * @throws Exception 异常
     * @see
     * @since 1.0
     */

    @RequestMapping("export")
    public ResponseEntity<byte[]> export()
            throws Exception {
        /** modify by yanya on 20210209 增加按照选择数据进行导出 start**/
        Map<String, Object> params= RequestUtil.getPageParam();
        List<ChbInvestTradeDto> list = Lists.newArrayList();
        if(StringUtils.isNotEmpty((String)params.get("selectData"))){
            List<Map> selectData = JSONArray.parseArray((String) params.get("selectData"), Map.class);
            List<String> tradeSeqnos = selectData.stream().map(e -> (String)e.get("tradeSeqno")).collect(Collectors.toList());
            list = chbInvestTradeService.selectByKeys(tradeSeqnos);
        }else {
            params.put("prodNames", StringUtil.isNotEmpty(params.get("prodName"))?(params.get("prodName").toString().split(",")):null);
            params.put("regCodes", StringUtil.isNotEmpty(params.get("regCode"))?(params.get("regCode").toString().split(",")):null);
            params.put("assetCodes", StringUtil.isNotEmpty(params.get("assetCode"))?(params.get("assetCode").toString().split(",")):null);
            list = chbInvestTradeService.selectAll(params);
        }
        List<ChbInvestTradeDto> dtoList = Lists.newArrayList();
        if(list.size()>0){
            for (int k=0;k<list.size();k++){
                if(list.get(k).getStatus().equals("A")){
                    ChbInvestTradeDto chbInvestTradeDto = ErrdataConvertUtil.errdataConvert(ChbInvestTradeDto.class, list.get(k).getOrgData());
                    chbInvestTradeDto.setStatus("A");
                    chbInvestTradeDto.setErrMsg(list.get(k).getErrMsg());
                    dtoList.add(chbInvestTradeDto);
                }else{
                    dtoList.add(list.get(k));
                }
            }
        }
        File outFile = chbInvestTradeService.makeFile(
                "/com/joyintech/tams/svreport/chb/businessdatamaintain/chbinvesttrade/conf/export_conf.xml",
                list);
        return ExportUtil.export(
                this.getMessage(this.getPageId()) + SystemGlobalConstant.OPEN_PARENTHESIS
                        + DateUtil.format(new Date())
                        + SystemGlobalConstant.CLOSE_PARENTHESIS
                        + SystemGlobalConstant.POINT
                        + FileNameUtil.getFileExtName(outFile),
                outFile, true);

    }

}
