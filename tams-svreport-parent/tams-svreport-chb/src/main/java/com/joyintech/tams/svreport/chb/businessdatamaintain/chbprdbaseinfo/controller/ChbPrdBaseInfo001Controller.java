package com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.*;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.service.ChbPrdBaseInfoService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wubingbing
 * @date 2022-05-09 11:14
 */
@Controller
@PageId("CchbPrdBaseInfo001")
@RequestMapping("/chb/businessdatamaintain/chbprdbaseinfo/chbprdbaseinfo001")
public class ChbPrdBaseInfo001Controller extends BaseController {
    @Autowired
    private ChbPrdBaseInfoService chbPrdBaseInfoService;
    /**
     * 产品名称
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProdIdAndProdName")
    public List<SuggestDto> getProdIdAndProdName()
    {
        return chbPrdBaseInfoService.getProdIdAndProdName(RequestUtil.getPageParam());
    }
    @Override
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.人行客户类型_SVR);
        this.setSelectOptionToModel(model, DictCode.报送产品发行状态);
        return super.init(model);
    }

    /**
     * 交易日历下拉框查询
     *
     * @return 交易日历
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("queryCalendarInfoList")
    public Page<SelectOptionDto> queryCalendarInfoList(){
        return chbPrdBaseInfoService.queryCalendarInfoList(RequestUtil.getPageParam());
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
    public Page<ChbPrdBaseInfoDto> selectAll()
    {

        return chbPrdBaseInfoService.selectForPage(RequestUtil.getPageParam());
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
    public AjaxResult delete(@RequestBody ChbPrdBaseInfoDto dto, BindingResult br)
            throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        ProcessParameter<ChbPrdBaseInfoDto> processParameter = new ProcessParameter<ChbPrdBaseInfoDto>();
        WorkFlowParameter<ChbPrdBaseInfoDto> params = new WorkFlowParameter<ChbPrdBaseInfoDto>();
        params.setPageId("CchbPrdBaseInfo002");
        params.setElementId("CchbPrdBaseInfo001_btn_delete");
        params.setBusinessCode(item -> item.getProdId());
        params.setBusinessType(DictCodeList.共通业务类型_产品基本信息.getValue());
        params.setComment(entity -> this.getMessage("CchbPrdBaseInfo001.prodId") + "("
                + entity.getProdId() + ")");
        processParameter.setWorkFlowParameter(params);
        dto.getSelectedItem().forEach(e->{
            e.setCreateUser(CommonUtil.getUserName());
            e.setCreateTime(DateUtil.now());
            e.setUpdateUser(CommonUtil.getUserName());
            e.setUpdateTime(DateUtil.now());
        });
        String msg = chbPrdBaseInfoService.start(processParameter,dto.getSelectedItem().toArray(new ChbPrdBaseInfoDto[dto.getSelectedItem().size()]));
        return new AjaxResult(msg);

    }
    /**
     * 修改、删除相关check
     *
     * @param br                            验证信息
     * @return 验证结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({
            ValidateGroupEdit.class}) ChbPrdBaseInfoDto dto, BindingResult br) {
        if (br.hasErrors()) {
            AjaxResult ar = new AjaxResult(br);
            if (StringUtil.equals(dto.getOperateType(), "issueStatus")) {
                List<ValidMessage> errorList = ar.getErrorList();
                // 流程中数据判断
                for (ValidMessage validMessage : errorList) {
                    if (StringUtil.indexOf(validMessage.getMessage(), this.getMessage("COMMON_E_0012").substring(0,
                            this.getMessage("COMMON_E_0012").length() - 3)) >= 0) {
                        // 由于PK值的设定，此处特殊处理
                        validMessage.setMessage(StringUtil.splitToList(",", validMessage.getMessage()).get(0));
                    }
                }
            }
            return ar;
        }else {
            return new AjaxResult();
        }
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

        Map<String, Object> params= RequestUtil.getPageParam();
        List<ChbPrdBaseInfoDto> list = Lists.newArrayList();
        if(StringUtils.isNotEmpty((String)params.get("selectData"))) {
            List<Map> selectData = JSONArray.parseArray((String) params.get("selectData"), Map.class);
            List<String> produuids = selectData.stream().map(e -> (String)e.get("produuid")).collect(Collectors.toList());
            list = chbPrdBaseInfoService.selectByKeys(produuids);
        } {
            list = chbPrdBaseInfoService.selectAll(params);
        }
        List<ChbPrdBaseInfoDto> dtoList = Lists.newArrayList();
        if(list.size()>0){
            for (int k=0;k<list.size();k++){
                if(list.get(k).getStatusValid().equals("A")){
                    ChbPrdBaseInfoDto dto = ErrdataConvertUtil.errdataConvert(ChbPrdBaseInfoDto.class, list.get(k).getOrgData());
                    dto.setStatusValid("A");
                    dto.setErrMsg(list.get(k).getErrMsg());
                    dtoList.add(dto);
                }else{
                    dtoList.add(list.get(k));
                }
            }
        }
        File outFile = chbPrdBaseInfoService.makeFile(
                "/com/joyintech/tams/svreport/chb/businessdatamaintain/chbprdbaseinfo/conf/export_conf.xml",
                dtoList);
        return ExportUtil.export(
                this.getMessage(this.getPageId()) + SystemGlobalConstant.OPEN_PARENTHESIS
                        + DateUtil.format(new Date())
                        + SystemGlobalConstant.CLOSE_PARENTHESIS
                        + SystemGlobalConstant.POINT
                        + FileNameUtil.getFileExtName(outFile),
                outFile, true);

    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForImportData(String fileName) throws Exception  {
        return chbPrdBaseInfoService.checkConfirmMsgForImportData(fileName);
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

        return chbPrdBaseInfoService.importFileSave(fileName);
    }
}
