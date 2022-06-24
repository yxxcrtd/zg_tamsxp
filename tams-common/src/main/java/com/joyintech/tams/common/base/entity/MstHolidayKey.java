package com.joyintech.tams.common.base.entity;

import java.util.Date;

public class MstHolidayKey extends BaseChbEntity {
    /**
     * 日历编号
     */
    private String calendarId;

    /**
     * 日期
     */
    private Date cdate;

    /**
     * 取得 日历编号
     * @return 日历编号
     */
    public String getCalendarId() {
        return calendarId;
    }

    /**
     * 设置 日历编号
     * @param calendarId 日历编号
     */
    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId == null ? null : calendarId.trim();
    }

    /**
     * 取得 日期
     * @return 日期
     */
    public Date getCdate() {
        return cdate;
    }

    /**
     * 设置 日期
     * @param cdate 日期
     */
    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }
}