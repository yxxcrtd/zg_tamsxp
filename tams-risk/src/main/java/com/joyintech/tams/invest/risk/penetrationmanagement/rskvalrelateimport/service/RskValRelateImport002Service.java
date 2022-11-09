/*
 * 文件名：RskValRelateImport002Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：dongzhiling
 * 修改时间：2018年12月21日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.constant.util.ExcelUtilForRisk;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.dto.RskValRelateImport001Dto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author dongzhiling
 * @version 1.0 2018年12月21日
 * @see RskValRelateImport002Service
 * @since 1.0
 */
@Service
public class RskValRelateImport002Service extends CommonExportService<RskValRelateImport001Dto>{

    /**
     * <一句话功能简述> 指标结构映射联想下拉框
     *
     * @param parameters
     * @return 下拉框信息
     * @see
     * @since 1.0
     */
    public List<SelectOptionDto> queryStructMapId(Map<String, Object> parameters) {
        return restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/queryStructMapId",
                parameters, SelectOptionDto.class);
    }

    /**
     * <一句话功能简述> 汇总科目映射联想下拉框
     *
     * @param pageParam
     * @return
     * @see
     * @since 1.0
     */
    public List<SelectOptionDto> querySumsubMapId(Map<String, Object> pageParam) {
        return restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/querySumsubMapId",
                pageParam, SelectOptionDto.class);
    }

    /**
     * <一句话功能简述> 资产科目映射联想下拉框
     *
     * @param pageParam
     * @return
     * @see
     * @since 1.0
     */
    public List<SelectOptionDto> queryAssetSubMapId(Map<String, Object> pageParam) {
        return restTemplate.postForList(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/queryAssetSubMapId",
                pageParam, SelectOptionDto.class);
    }

    /**
     * <一句话功能简述> 插入操作
     *
     * @param dto
     * @see
     * @since 1.0
     */
    public int insert(RskValRelateImport001Dto dto)  {
        dto.setSubjectCodeCol(Long.parseLong(String.valueOf(ExcelUtilForRisk.columnToIndex(dto.getSubjectCodeColLetter()))));
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/insert", dto,
                Integer.class);
    }

    /**
     * <一句话功能简述> 编辑更新操作
     *
     * @param dto
     * @see
     * @since 1.0
     */
    public int update(RskValRelateImport001Dto dto) {
        dto.setSubjectCodeCol(Long.parseLong(String.valueOf(ExcelUtilForRisk.columnToIndex(dto.getSubjectCodeColLetter()))));
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/update", dto,
                Integer.class);
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto
     * @see
     * @since 1.0
     */
    public int delete(RskValRelateImport001Dto dto) {
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/delete", dto,
                Integer.class);
    }

    /**
     * <一句话功能简述>
     * 按下编辑按钮，查询相应信息
     * @param ptlId
     * @return
     * @see
     * @since 1.0
     */
    public RskValRelateImport001Dto selectInfoById(String ptlId) {
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/selectInfoById", ptlId,
                RskValRelateImport001Dto.class);
    }

    /**
     * <一句话功能简述>
     * 校验组合名称是否重复
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public int checkGroupName(RskValRelateImport001Dto dto) {
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValRelateImport/checkGroupName", dto,
                Integer.class);
    }

}
