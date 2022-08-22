package com.joyintech.tams.svreport.chb.liquidityriskinfo.controller;


import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.*;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dto.LiquidityRiskInfo001Dto;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.service.LiquidityRiskInfo001MService;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.service.LiquidityRiskInfo001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 流动性风险应对情况信息维护Controller
 *
 * @author zhoubin
 * @create 2022/08/16
 */
@Controller
@PageId("LiquidityRiskInfo001")
@RequestMapping("/chb/liquidityriskinfo/LiquidityRiskInfo001")
public class LiquidityRiskInfo001Controller extends BaseController {

    @Autowired
    private LiquidityRiskInfo001MService liquidityRiskInfo001MService;

    @Autowired
    private LiquidityRiskInfo001Service liquidityRiskInfo001Service;


    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        super.setSelectOptionToModel(model, DictCode.流动性风险应对措施, DictCode.维护数据状态);
        return super.init(model);
    }


    /**
     * 查询列表
     *
     * @return DtPage
     */
    @ResponseBody
    @RequestMapping("searchPage")
    public Page<LiquidityRiskInfo001Dto> search() {
        Map<String, Object> param = RequestUtil.getPageParam();
        return liquidityRiskInfo001MService.searchForPage(param);
    }


    /**
     * 删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public AjaxResult delete(@RequestParam("keys") String keys) {
        this.liquidityRiskInfo001Service.delete(keys);
        AjaxResult wb = new AjaxResult(this.getMessage("REPORT_COMMON_I_0007"));
        return wb;
    }


    /**
     * 导出excel
     *
     * @return 导出
     */
    @RequestMapping(value = ACTION_EXPORT)
    public ResponseEntity<byte[]> export() {
        List<LiquidityRiskInfo001Dto> dtoList = this.liquidityRiskInfo001MService.queryReportList(RequestUtil.getPageParam());
        File outFile = liquidityRiskInfo001Service.makeFile(
                "/com/joyintech/tams/svreport/chb/liquidityriskinfo/conf/export_conf.xml", dtoList);

        return ExportUtil.export("流动性风险应对情况信息" + GlobalConstant.OPEN_PARENTHESIS
                + DateUtil.format(new Date()) + GlobalConstant.CLOSE_PARENTHESIS + GlobalConstant.POINT
                + FileNameUtil.getFileExtName(outFile), outFile, true);
    }


    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    @ResponseBody
    @RequestMapping(value = "checkConfirmMsgForImportData", method = RequestMethod.POST)
    public AjaxResult checkConfirmMsgForImportData(String fileName) {
        return liquidityRiskInfo001Service.checkConfirmMsgForImportData(fileName);
    }


    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    @ResponseBody
    @RequestMapping("/importFile")
    public List<ExcelValidDto> importData(String fileName) {
        return this.liquidityRiskInfo001MService.importData(fileName);
    }


}
