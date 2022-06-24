/*
 * 文件名：MarketCalendarInfo001Dto.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2018年8月3日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto;

import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.base.entity.MstHoliday;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.workflow.engine.annotation.PrimaryKey;
import com.joyintech.workflow.engine.annotation.PrimaryKey.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 节假日表dto 〈功能详细描述〉
 * 
 * @author xiexinting
 * @version 1.0 2018年8月3日
 * @see ChbCalendarInfo001Dto
 * @since 1.0
 */
@PageId("ChbCalendarInfo001")
public class ChbCalendarInfo001Dto extends MstHoliday{

    /**
     * <意义，目的和功能，以及可能被用到的地方>
     */
    private static final long serialVersionUID = -5811084101048852454L;
    /**
     * 日历名
     */
    private String calendarName;

    private String orgData;


    public String getUpdateTimeStr() {
        return com.joyintech.background.component.common.pack.common.util.DateUtil.formatDateTime(super.getUpdateTime());
    }




    /**
     * 获取 calendarName
     * @return calendarName.
     */
    public String getCalendarNameStr() {
        return super.getCalendarId()+" "+calendarName;
    }
    
    /**
     * 获取 calendarName
     * @return calendarName.
     */
    public String getCalendarName() {
        return calendarName;
    }

    
    /**
     * 设置 calendarName
     * @param calendarName calendarName
     */
    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }





    /**
     * 取得 日历编号
     * 
     * @return 日历编号
     */
    @Override
    @PrimaryKey(Field.PK_1)
    public String getCalendarId() {
        return super.getCalendarId();
    }

    /**
     * 取得 日期
     * 
     * @return 日期
     */
    @PrimaryKey(Field.PK_2)
    public String getCdateStr() {
        return DateUtil.format(super.getCdate());
    }

    /**
     * 选中的数据
     */
    private transient List<ChbCalendarInfo001Dto> selectedItem;

    /**
     * 获取 selectedItem
     * 
     * @return selectedItem.
     */
    public List<ChbCalendarInfo001Dto> getSelectedItem() {
        return selectedItem;
    }

    /**
     * 设置 selectedItem
     * 
     * @param selectedItem selectedItem
     */
    public void setSelectedItem(List<ChbCalendarInfo001Dto> selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getIsHolidayStr() {
        return super.getIsHoliday()==null?null:SysDataUtil.getNameFromDataDict(DictCode.是否节假日,
                super.getIsHoliday().toString());
    }

    public String getStatusStr() {
        return Objects.equals("N",super.getStatus()) || StringUtils.isBlank(super.getStatus())?"有效":SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,
                super.getStatus());
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }
}
