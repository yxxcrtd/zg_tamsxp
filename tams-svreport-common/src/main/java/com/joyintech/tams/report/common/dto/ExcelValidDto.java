/*
 * 文件名：ExcelValidDto.java
 * 版权：Copyright 2017-2019 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中债报送子系统
 * 修改人：何阳阳
 * 修改时间：2017年9月20日
 * 修改内容：新建
 * 系统名称：中债报送子系统
 */

package com.joyintech.tams.report.common.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbInterfErrodataWithBLOBs;
import com.joyintech.tams.constant.DictCodeListExt2;

/**
 * excel验证结果封装
 *
 * @author 何阳阳
 * @version 1.0 2017年9月20日
 * @see ExcelValidDto
 * @since 1.0
 */
public class ExcelValidDto {

    /**
     * 检查通过MSG
     */
    private static final String CHECK_OK_MSG = DictCodeListExt2.检查结果_检查通过.getCodeName();

    /**
     * 检查不通过MSG
     */
    private static final String CHECK_NG_MSG = DictCodeListExt2.检查结果_检查不通过.getCodeName();

    /**
     * 存在警示信息项MSG
     */
    private static final String CHECK_HAVA_WARN_MSG = "存在警示信息项";

    /**
     * 验证标识符（具有代表性的字段值）
     */
    private String validKey;

    /**
     * excel的行号（导入验证用）
     */
    private String rowNum;

    /**
     * 检查结果
     */
    private String checkResult;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 警告信息
     */
    private String warnInfo;

    /**
     * 数据日期
     */
    private Date dataTime;

    /**
     * 机构代码
     */
    private String orgCode;

    /**
     * 创建部门
     */
    private String createDept;

    /**
     * 是否有逻辑检查
     */
    private String isLogicCheck;

    /**
     * 获取 dataTime
     * 
     * @return dataTime.
     */
    public Date getDataTime() {
        return dataTime;
    }

    /**
     * 设置 dataTime
     * 
     * @param dataTime dataTime
     */
    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    /**
     * 获取 orgCode
     * 
     * @return orgCode.
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置 orgCode
     * 
     * @param orgCode orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取 createDept
     * 
     * @return createDept.
     */
    public String getCreateDept() {
        return createDept;
    }

    /**
     * 设置 createDept
     * 
     * @param createDept createDept
     */
    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    public ExcelValidDto() {
        super();
    }

    public ExcelValidDto(String validKey, String rowNum, String errorInfo) {
        super();
        this.validKey = validKey;
        this.rowNum = rowNum;
        this.errorInfo = errorInfo;
    }

    public ExcelValidDto(String validKey, String rowNum, String errorInfo, String warnInfo) {
        super();
        this.validKey = validKey;
        this.rowNum = rowNum;
        this.errorInfo = errorInfo;
        this.warnInfo = warnInfo;
    }

    public String getValidKey() {
        return validKey;
    }

    public void setValidKey(String validKey) {
        this.validKey = validKey;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getWarnInfo() {
        return warnInfo;
    }

    public void setWarnInfo(String warnInfo) {
        this.warnInfo = warnInfo;
    }

    /**
     * 获取检查结果
     *
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public String getCheckResult() {
        if (StringUtil.isNotBlank(errorInfo)) {
            checkResult = CHECK_NG_MSG;
        }
        else if (StringUtil.isNotBlank(warnInfo)) {
            checkResult = CHECK_HAVA_WARN_MSG;
        }
        else {
            checkResult = CHECK_OK_MSG;
        }

        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    /**
     * 获取 isLogicCheck
     * 
     * @return isLogicCheck.
     */
    public String getIsLogicCheck() {
        return isLogicCheck;
    }

    /**
     * 设置 isLogicCheck
     * 
     * @param isLogicCheck isLogicCheck
     */
    public void setIsLogicCheck(String isLogicCheck) {
        this.isLogicCheck = isLogicCheck;
    }
}
