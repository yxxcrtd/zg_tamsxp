package com.joyintech.service.chb.liquidityriskinfo.controller;


import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dto.LiquidityRiskInfo001Dto;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.service.LiquidityRiskInfo001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 流动性风险应对情况信息维护Controller
 *
 * @author zhoubin
 *
 * @create 2022/08/16
 */
@RestController
@RequestMapping("/LiquidityRiskInfo")
public class LiquidityRiskInfoController {

    @Autowired
    private LiquidityRiskInfo001Service liquidityRiskInfo001Service;


    /**
     * 查询列表
     *
     * @return DtPage
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<LiquidityRiskInfo001Dto> search(@RequestBody Map<String, Object> paramMap) {
        return liquidityRiskInfo001Service.searchForPage(paramMap);
    }


    /**
     * 导出excel查询
     *
     * @return 结果数据
     */
    @PostMapping("querySearchPageList")
    public List<LiquidityRiskInfo001Dto> querySearchPageList(@RequestBody Map<String, Object> paramMap) {
        return liquidityRiskInfo001Service.querySearchPageList(paramMap);

    }

    /**
     * 导入数据文件
     *
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("importData")
    public List<ExcelValidDto> importData(@RequestBody Map<String, Object> param) {
        List<List<String>> dataList = (List<List<String>>) param.get("dataList");
        List<List<String>> tempList = (List<List<String>>) param.get("tempList");
        String orgCode = param.get("orgCode") == null ? null : param.get("orgCode").toString();
        String deptCode = param.get("deptCode") == null ? null : param.get("deptCode").toString();
        return this.liquidityRiskInfo001Service.importData(dataList, tempList, orgCode, deptCode);
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
    @PostMapping("checkConfirmMsgForImportData")
    public AjaxResult checkConfirmMsgForImportData(@RequestBody Map<String, Object> param) {
        String fileName = (String)param.get("fileName");
        return liquidityRiskInfo001Service.checkConfirmMsgForImportData(fileName);
    }


}
