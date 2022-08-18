package com.joyintech.tams.svreport.chb.liquidityriskinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.dto.SvrAjaxResult;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dto.LiquidityRiskInfo001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 流动性风险应对情况信息维护Service
 *
 * @author zhoubin
 *
 * @create 2022/08/16
 */
@Service
public class LiquidityRiskInfo001MService extends BaseService<LiquidityRiskInfo001Dto> {

    /**
     * 查询列表
     *
     * @return DtPage
     */
    public Page<LiquidityRiskInfo001Dto> searchForPage(Map<String, Object> paramMap) {
        return restTemplate.postForPage(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/LiquidityRiskInfo/findPage", paramMap,
                LiquidityRiskInfo001Dto.class);
    }

    /**
     * 新增
     *
     * @param dto
     */
    public void insert(LiquidityRiskInfo001Dto dto) {
        restTemplate.postForObject(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/LiquidityRiskInfo/insert", dto,
                LiquidityRiskInfo001Dto.class);
    }


    /**
     * 导出excel查询
     *
     * @return 结果数据
     */
    public List<LiquidityRiskInfo001Dto> queryReportList(Map<String, Object> paramMap) {
        return restTemplate.postForList(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/LiquidityRiskInfo/querySearchPageList", paramMap,
                LiquidityRiskInfo001Dto.class);
    }

    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importData(String fileName) {
        Map<String,Object> param = new HashMap<>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("tempList", ExcelUtil.getTempList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        return restTemplate.postForList(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/LiquidityRiskInfo/importData",
                param,
                ExcelValidDto.class);
    }


    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName)  {
        Map<String,Object> param = new HashMap<>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        param.put("fileName", fileName);
        return restTemplate.postForObject(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/LiquidityRiskInfo/checkConfirmMsgForImportData",
                param,
                SvrAjaxResult.class);
    }
}
