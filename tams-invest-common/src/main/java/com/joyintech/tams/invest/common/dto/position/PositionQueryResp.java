package com.joyintech.tams.invest.common.dto.position;

import java.util.List;

/**
 * 组合数据集
 */
public class PositionQueryResp {

    private String  code;

    private String  message;

    private List<PositionQueryDto> data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<PositionQueryDto> getData() {
        return data;
    }

    public void setData(List<PositionQueryDto> data) {
        this.data = data;
    }
}
