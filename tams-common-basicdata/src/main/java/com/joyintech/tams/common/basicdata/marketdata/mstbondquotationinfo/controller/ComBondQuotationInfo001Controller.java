/*
 * 文件名：MarketInformationController001.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：wangzehui
 * 修改时间：2018年7月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.common.basicdata.marketdata.mstbondquotationinfo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.basicdata.marketdata.mstbondquotationinfo.dto.ComBondQuotationInfoDto;
import com.joyintech.tams.common.basicdata.marketdata.mstbondquotationinfo.service.ComBondQuotationInfoService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.system.constant.SystemGlobalConstant;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;

/**
 * 债券行情信息 债券行情信息查询
 * 
 * @author wangzehui
 * @version 1.0 2018年7月17日
 * @see ComBondQuotationInfo001Controller
 * @since 1.0
 */
@Controller
@PageId("ComBondQuotationInfo001")
@RequestMapping("/common/basicdata/marketdata/mstbondquotationinfo/ComBondQuotationInfo001")
public class ComBondQuotationInfo001Controller extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ComBondQuotationInfo001Controller.class);

    /**
     * 
     */
    @Autowired
    private ComBondQuotationInfoService comBondQuotationInfoService;

    /**
     * 入口
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        this.setSelectOptionToModel(model, DictCode.估值来源);
        return super.init(model);
    }

    /**
     * 债券代码下拉选择框
     * @param param 参数
     * @return list结果集
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("getSecId")
    public List<SuggestDto> getSecIdOption(String param)
        throws Exception {
        List<SuggestDto> list = comBondQuotationInfoService.getSecIdOption(param);
        return list;
    }

    /**
     * 债券简称的下拉框
     * 
     * @param param 参数
     * @return 查询结果集
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("getFinProdAbbr")
    public List<SuggestDto> getFinProdAbbr(String param)
        throws Exception {
        return comBondQuotationInfoService.getFinProdAbbr(param);
    }

    /**
     * 画面查询方法
     * @return 查询结果集
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<ComBondQuotationInfoDto> selectAll()
    {
        try {
            Page<ComBondQuotationInfoDto> page = comBondQuotationInfoService.selectForPage(
                RequestUtil.getParameters(getRequest()));
            return page;
        } catch (Exception e) {
            logger.error("查询功能异常", e);
            return null;
        }
    }

    /**
     * 删除按钮点击时的check
     *
     * @param dto dto
     * @param result BindingResult
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({
        ValidateGroupDelete.class}) ComBondQuotationInfoDto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
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
    public AjaxResult delete(@RequestBody @Validated({
        ValidateGroupDelete.class}) ComBondQuotationInfoDto dto, BindingResult br)
        throws Exception {
        if (br.hasErrors()) {
            return new AjaxResult(br);
        }
        ProcessParameter<ComBondQuotationInfoDto> processParameter = new ProcessParameter<ComBondQuotationInfoDto>();
        WorkFlowParameter<ComBondQuotationInfoDto> params = new WorkFlowParameter<ComBondQuotationInfoDto>();
        params.setPageId("ComBondQuotationInfo002");
        params.setElementId(dto.getElementId());
        params.setBusinessCode(item -> item.getSecId());
        params.setBusinessType(DictCodeList.共通业务类型_债券估值行情信息.getValue());
        // 设置流程业务说明, entity即传入的dto
        // 业务说明：债券代码 + "(" + 债券代码 + ")" + "-" + "估值日" + "-" + "估值日"
        params.setComment(entity -> SpringContextUtil.getTextValue("ComBondQuotationInfo001.secId") + "(" + entity.getSecId() + ")" + "-"
            + SpringContextUtil.getTextValue("ComBondQuotationInfo001.valueDate") + "("
            + DateUtil.format(entity.getValueDate()) + ")"+ this.getMessage("ComBondQuotationInfo001.valueSource") + "("
            + entity.getValueSourceStr() + ")");
        processParameter.setWorkFlowParameter(params);
        String msg = comBondQuotationInfoService.start(processParameter,
            dto.getSelectedItem().toArray(
                new ComBondQuotationInfoDto[dto.getSelectedItem().size()]));
        return new AjaxResult(msg);
        
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
        String datas = this.getParam("datas");
        Map<String, Object> params= RequestUtil.getPageParam();
        List<ComBondQuotationInfoDto> list = null;
        if (StringUtil.isNotEmpty(datas)) {
            list = JsonUtils.json2List(datas, ComBondQuotationInfoDto.class);
        }
        else {
            list = comBondQuotationInfoService.selectAllList(params);
        }
//        return comBondQuotationInfoService.export(this.getPageId(),
//            RequestUtil.getParameters(getRequest()));
        File outFile = comBondQuotationInfoService.makeFile(
                "/com/joyintech/tams/common/basicdata/marketdata/mstbondquotationinfo/conf/export_conf.xml",
                list);
        return ExportUtil.export(
                this.getMessage(this.getPageId()) + SystemGlobalConstant.OPEN_PARENTHESIS
                        + DateUtil.format(new Date())
                        + SystemGlobalConstant.CLOSE_PARENTHESIS
                        + SystemGlobalConstant.POINT
                        + FileNameUtil.getFileExtName(outFile),
                outFile, true);
        /** modify by yanya on 20210209 增加按照选择数据进行导出 end**/

    }
    
 // hsgexinghua   add by zhanngxing1 20200220 start
    /**
     * 导入（必填项校验）
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     * @throws Exception 异常
     */
    @ResponseBody
    @RequestMapping("importFile")
    public List<ExcelValidDto> importData(String fileName)
            throws Exception{
        return this.comBondQuotationInfoService.importData(fileName);
    }
    
    
    /**
     * 导入（重复数据获取）
     * 
     * @param fileName 导入的文件名
     * @return checkResult
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("duplicateCheck")
    public List<Integer> duplicateCheck(String fileName)
        throws Exception {
         List<List<String>> dataList = comBondQuotationInfoService.dataAnalysis(fileName);
         List<Integer> checkResult = new ArrayList<>();
         if (null != dataList && dataList.size() > 0) {
             // 导入文件重复数据检查
        	 checkResult = comBondQuotationInfoService.duplicateCheck(dataList);
         }
        return checkResult;
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
    public List<Integer> importFileSave(String fileName)
        throws Exception {
        List<List<String>> dataList = comBondQuotationInfoService.dataAnalysis(fileName);
        List<Integer> checkResult = new ArrayList<>();
        if (null != dataList && dataList.size() > 0) {
            // 导入文件重复数据检查
       	 checkResult = comBondQuotationInfoService.duplicateCheck(dataList);
       	 List<ComBondQuotationInfoDto> equityList = comBondQuotationInfoService.converToBean(dataList,checkResult);
       	 // 保存，已存在则更新
       	 comBondQuotationInfoService.dealAfterCheck(equityList);
        }
        return checkResult;
    }

}
