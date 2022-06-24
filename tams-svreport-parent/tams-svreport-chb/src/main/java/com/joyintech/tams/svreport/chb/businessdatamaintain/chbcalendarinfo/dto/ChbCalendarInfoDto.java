/*
 * 文件名：MstCalendarInfoDto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2018年8月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto;

import com.joyintech.tams.common.base.entity.MstCalendarInfo;

import java.util.List;

/**
 * 日历信息表dto
 * 〈功能详细描述〉
 * @author zhoubin
 * @version 1.0 2022年3月28日
 * @see ChbCalendarInfoDto
 * @since 1.0
 */
public class ChbCalendarInfoDto extends MstCalendarInfo{

    private static final long serialVersionUID = -2226636630539705742L;


    private List<ChbCalendarInfo001Dto> list;

    private String isHoliday;


    public List<ChbCalendarInfo001Dto> getList() {
        return list;
    }

    public void setList(List<ChbCalendarInfo001Dto> list) {
        this.list = list;
    }

    public String getIsHoliday() {
        return isHoliday;
    }

    public void setIsHoliday(String isHoliday) {
        this.isHoliday = isHoliday;
    }
}
