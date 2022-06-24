package com.joyintech.tams.common.svreport.base.dto;

import com.joyintech.framework.base.BaseEntity;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;

import java.util.Map;

/**
 * check共通dto
 */
public class BaseCommonCheckDto extends BaseEntity {

    //错误信息
    private String errMsg;

    //数据来源
    private String dataSource;

    //check不通过类型  I数据库check不通过标识  Y业务check不通过标识
    private String checkErrCode;

    private ExcelValidDto excelValidDto;

    private Map<String,String> errMap;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getCheckErrCode() {
        return checkErrCode;
    }

    public void setCheckErrCode(String checkErrCode) {
        this.checkErrCode = checkErrCode;
    }
    
    public ExcelValidDto getExcelValidDto() {
        return excelValidDto;
    }

    public void setExcelValidDto(ExcelValidDto excelValidDto) {
        this.excelValidDto = excelValidDto;
    }

    public Map<String, String> getErrMap() {
        return errMap;
    }

    public void setErrMap(Map<String, String> errMap) {
        this.errMap = errMap;
    }
}