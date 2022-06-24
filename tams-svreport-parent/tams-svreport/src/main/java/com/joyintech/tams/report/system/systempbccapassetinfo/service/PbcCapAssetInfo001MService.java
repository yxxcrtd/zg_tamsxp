package com.joyintech.tams.report.system.systempbccapassetinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.dto.SvrAjaxResult;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.system.systempbccapassetinfo.dto.PbcCapAssetInfo001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 人行资金信托代码管理Service
 *
 * @author kx
 *
 * @create 2019/12/24
 */
@Service
public class PbcCapAssetInfo001MService extends BaseService<PbcCapAssetInfo001Dto> {

    /**
     * 查询列表
     *
     * @return DtPage
     */
    public Page<PbcCapAssetInfo001Dto> searchForPage(Map<String, Object> paramMap) {
        return restTemplate.postForPage(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/findPage", paramMap,
                PbcCapAssetInfo001Dto.class);
    }


    /**
     * 取得资产代码下拉值
     *
     * @param param
     * @return
     */
    public List<SelectOptionDto> queryInsideProdIdList(Map<String, Object> param) {
        return restTemplate.postForList(SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/getAssetCodeList",
                param, SelectOptionDto.class);
    }

    /**
     * 人行资金信托代码下拉框取得值
     *
     * @param param
     * @return
     */
    public List<SelectOptionDto> queryPobCasSeTidList(Map<String, Object> param) {
        return restTemplate.postForList(SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/setPobCasSeTidList",
                param, SelectOptionDto.class);
    }

    /**
     * 删除
     *
     * @param map
     */
    public void deletePbcCapAssetInfo(Map<String, Object> map) {
        restTemplate.postForObject(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/deletePbcCapAssetInfoData", map,
                Integer.class);
    }

    /**
     * 导出excel查询
     *
     * @return 结果数据
     */
    public List<PbcCapAssetInfo001Dto> queryReportList(Map<String, Object> paramMap) {
        return restTemplate.postForList(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/querySearchPageList", paramMap,
                PbcCapAssetInfo001Dto.class);
    }

    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importData(String fileName) {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("tempList", ExcelUtil.getTempList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        return restTemplate.postForList(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/importData",
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
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        SvrAjaxResult svrAjaxResult = restTemplate.postForObject(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/checkConfirmMsgForImportData",
                param,
                SvrAjaxResult.class);
        if(StringUtils.isNotBlank(svrAjaxResult.getMessage())){
            svrAjaxResult.setMessageType(MessageType.确认消息);
        }
        return svrAjaxResult;
    }

    /**
     * 导入数据文件之前检查资金信托代码有没有对应多个资产代码(Excel)
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportDataByExcel(String fileName)  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        return restTemplate.postForObject(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/checkConfirmMsgForImportDataByExcel",
                param,
                SvrAjaxResult.class);
    }

    /**
     * 导入数据文件之前检查资金信托代码有没有对应多个资产代码(数据库)
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportDataByDB(String fileName)  {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("dataList", ExcelUtil.getDataList(fileName));
        param.put("orgCode", CommonUtil.getOrgCode());
        param.put("deptCode", CommonUtil.getDeptCode());
        return restTemplate.postForObject(
                SvrServiceApplication.COMMON_SERVICE_NAME + "/PbcCapAssetInfo/checkConfirmMsgForImportDataByDB",
                param,
                SvrAjaxResult.class);
    }

}
