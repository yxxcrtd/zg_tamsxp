package com.joyintech.tams.report.interf.common.constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.joyintech.framework.exception.BusinessException;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.joyintech.framework.convert.DateConverter;
import com.joyintech.framework.util.StringUtil;


/**
 * 日期处理工具类
 *
 * @author renjian
 * @version 1.0
 * @since 2015-8-6
 */
public class DateUtil extends DateUtils {

    /**
     * 中文日期格式 :"yyyy年MM月dd日"
     */
    public static final String DEFAULT_DATE_FORMAT_CN = "yyyy年MM月dd日";

    /**
     * 标准的日期格式 :"yyyy-MM-dd"
     */
    public static final String DEFAULT_DATE_FORMAT_EN = "yyyy-MM-dd";

    /**
     * 标准的日期格式 :"yyyy-MM"
     */
    public static final String MONTH_DATE_FORMAT_EN = "yyyy-MM";

    /**
     * 到毫秒日期格式 :"yyyyMMddHHmmssSSS"
     */
    public static final String DATETIME_FORMAT_WITH_NO_SLIP = "yyyyMMddHHmmssSSS";

    /**
     * 到毫秒日期格式 :"yyyy-MM-dd HH:mm:ss.SSS"
     */
    public static final String DATETIME_FORMAT_WITH_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * "yyyy-MM-dd HH:mm:ss"
     */
    public static final String DEFAULT_DATETIME_FORMAT = DateConverter.DATETIME_PATTERN;

    /**
     * "yyyyMMdd"
     */
    public static final String DEFAULT_OTHER_FORMAT = "yyyyMMdd";

    /**
     * "yyyy_MM_dd"
     */
    public static final String DEFAULT_OTHER_FORMA = "HHmmssSSS";

    /**
     * "yyyy/MM/dd"
     */
    public static final String DEFAULT_ANOTHER_FORMAT = "yyyy/MM/dd";

    /**
     * 期间类型
     */
    public enum DurationType {
        /**
         *
         */
        秒,
        /**
         *
         */
        分钟,
        /**
         *
         */
        小时,
        /**
         *
         */
        天,
        /**
         *
         */
        月,
        /**
         *
         */
        年;
    }

    /**
     * 将日期字符串转为日期形式
     *
     * @param dateString 日期字符串
     * @return 日期
     */
    public static Date parse(String dateString) {
        // 外围系统有用C开发的，他们的日期如果是空的话给的是0
        if (StringUtil.isEmpty(dateString) || "0".equals(dateString) || "00000000".equals(dateString)) {
            return null;
        }

        try {
            return parseDate(dateString,
                    new String[] {DEFAULT_DATE_FORMAT_CN, DEFAULT_DATE_FORMAT_EN,DEFAULT_ANOTHER_FORMAT,
                            DATETIME_FORMAT_WITH_NO_SLIP, DEFAULT_DATETIME_FORMAT, DEFAULT_OTHER_FORMAT,
                            "yyyy-MM","yyyy/MM/dd HH:mm","yyyy-MM-dd HH:mm","yyyyMMdd HH:mm","yyyy-MM-dd HH:mm:ss","yyyyMMdd HH:mm:ss","yyyyMMddHHmm"});
        }
        catch (ParseException e) {
            throw new BusinessException("Date ParseException");
        }
    }

    /**
     * 将日期字符串转为日期形式
     *
     * @param dateString 日期字符串
     * @return 日期
     */
    public static Date parse(String dateString,String parsePatterns) {
        // 外围系统有用C开发的，他们的日期如果是空的话给的是0
        if (StringUtil.isEmpty(dateString) || "0".equals(dateString) || "00000000".equals(dateString)) {
            return null;
        }

        try {
            return parseDate(dateString,parsePatterns);
        }
        catch (ParseException e) {
            throw new BusinessException("Date ParseException");
        }
    }

    /**
     * 根据格式化字符串格式化日期
     *
     * @param date 格式化前日期
     * @return 格式化后日期字符串（yyyy-MM-dd）
     */
    public static String format(Date date) {
        String s = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT_EN);
            s = sdf.format(date);
        }
        return s;
    }

    /**
     * 根据格式化字符串格式化日期
     *
     * @param date 格式化前日期
     * @return 格式化后日期字符串（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        String s = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            s = sdf.format(date);
        }
        return s;
    }

    /**
     * 取得当前日期
     *
     * @return java.util.Date
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 取得当前日期
     *
     * @return yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return format(new Date());
    }

    /**
     * 取得昨日日期
     *
     * @return 昨日日期
     * @see
     * @since 1.0
     */
    public static String getYesterDayDate() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();

        return format(date);
    }

    /**
     * 取得昨日日期
     * @param date
     * @return 昨日日期
     * @see
     * @since 1.0
     */
    public static String getYesterDayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();

        return format(date);
    }

    /**
     * 根据格式化字符串格式化日期
     *
     * @param date 格式化前日期
     * @param pattern 格式化字符串,如：yyyy-MM-dd
     * @return 格式化后日期字符串
     */
    public static String format(Date date, String pattern) {
        String s = "";
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            s = sdf.format(date);
        }
        return s;
    }

    /**
     * 根据两个日期，取得相隔的天数
     *
     * @param d1 开始日期
     * @param d2 结束日期
     * @return 天数
     */
    public static int getBetweenDayNumber(Date d1, Date d2) {
        LocalDate start = new LocalDate(d1);
        LocalDate end = new LocalDate(d2);
        return Days.daysBetween(start, end).getDays();
    }

    /**
     * 比较两个日期的大小，精确到天数
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return 比较结果
     */
    public static int compareTo(Date d1, Date d2) {
        String date1 = format(d1);
        String date2 = format(d2);
        return date1.compareTo(date2);
    }

    /**
     * 根据两个日期，取得相隔的天数
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 相隔的天数
     */
    public static int getBetweenDayNumber(String date1, String date2) {
        return getBetweenDate(date1, date2, DurationType.天);
    }

    /**
     * 得到两个日期的间隔时间，可以是年月日，时分秒
     *
     * @param begin 开始日期
     * @param end 结束日期
     * @param type 年月日，时分秒
     * @return 间隔
     */
    public static int getBetweenDate(String begin, String end, DurationType type) {
        // 根据兩個个日期，取得相隔的天数
        DateTime beginDate = new DateTime(begin);
        DateTime endDate = new DateTime(end);

        // 计算区间天数
        Period p = new Period(beginDate, endDate, PeriodType.days());
        int result = -1;
        switch (type) {
            case 秒:
                result = p.getSeconds();
                break;
            case 分钟:
                result = p.getMinutes();
                break;
            case 小时:
                result = p.getHours();
                break;
            case 天:
                result = p.getDays();
                break;
            case 月:
                result = p.getMonths();
                break;
            case 年:
                result = p.getYears();
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 说明：比较两个日期大小,Calendar格式<br>
     * 备注：cal1 &gt; cal2 返回1, cal1 = cal2 返回0, cal1 &lt; cal2 返回 -1<br>
     *
     * @param cal1 日期1
     * @param cal2 日期2
     * @return 比较结果
     */
    public static int compareDate(Calendar cal1, Calendar cal2) {
        if (cal1.getTimeInMillis() > cal2.getTimeInMillis()) {
            return 1;
        }
        else if (cal1.getTimeInMillis() == cal2.getTimeInMillis()) {
            return 0;
        }
        else {
            return -1;
        }
    }

    /**
     * 说明：比较两个日期大小,Date格式<br>
     * 备注：date1 &gt; date2 返回1 ,date1 = date2 返回0 , date1 &lt; date2 返回 -1<br>
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果
     */
    public static int compareDate(Date date1, Date date2) {
        int result;
        if (date1 == null && date2 == null) {
            result = 0;
        }
        else if (date1 != null && date2 == null) {
            result = 1;
        }
        else if (date1 == null && date2 != null) {
            result = -1;
        }
        else {
            String date1Change = DateUtil.format(date1);
            String date2Change = DateUtil.format(date2);
            date1 = DateUtil.parse(date1Change);
            date2 = DateUtil.parse(date2Change);
            result = date1 == null?0:date1.compareTo(date2);
        }

        return result;
    }

    /**
     * 说明：比较两个日期大小<br>
     * 备注：d1 &gt; d2 返回1 ,d1 = d2 返回0 , d1 &lt; d2 返回 -1<br>
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return 比较结果
     */
    public static int compareDate(String d1, String d2) {
        Date date1 = null;
        Date date2 = null;
        date1 = parse(d1);
        date2 = parse(d2);
        return compareDate(date1, date2);
    }

    /**
     * 说明：比较两个日期大小,YYYY-MM-DD格式<br>
     * 备注：d1 &gt; d2 返回1 ,d1 = d2 返回0 , d1 &lt; d2 返回 -1<br>
     *
     * @param t1 时间1
     * @param t2 时间2
     * @return 比较结果
     * @throws ParseException 格式转化异常
     */
    public static int compareTime(String t1, String t2)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        Date date1 = sdf.parse(t1);
        Date date2 = sdf.parse(t2);
        return compareDate(date1, date2);
    }

    /**
     * 判断两个日期是否相等，如果都是null，则返回true。
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 比较结果
     */
    public static boolean isSameDate(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            return date1.equals(date2);
        }
        return date1 == date2;
    }

    /**
     * 主要功能:获取月份 注意事项:无
     *
     * @param date 格式：yyyy-MM-dd
     * @return 月份
     */
    public static int getMonth(String date) {
        DateTimeFormatter format = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT_EN);
        DateTime dateTime = DateTime.parse(date, format);
        return dateTime.getMonthOfYear();
    }

    /**
     * 说明：获取年 备注：
     *
     * @param date 格式：yyyy-MM-dd
     * @return 年份
     */
    public static int getYear(String date) {
        DateTimeFormatter format = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT_EN);
        DateTime dateTime = DateTime.parse(date, format);
        return dateTime.getYear();
    }

    /**
     * 说明：获取年字符串 备注：
     *
     * @param date 格式：yyyy-MM-dd
     * @return 年份
     */
    public static String getYearStr(String date) {
        int year = getYear(date);
        return String.valueOf(year);
    }

    /**
     * 说明：获取日 备注：
     *
     * @param date 格式：yyyy-MM-dd
     * @return 天数
     */
    public static int getDay(String date) {
        DateTimeFormatter format = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT_EN);
        DateTime dateTime = DateTime.parse(date, format);
        return dateTime.getDayOfMonth();
    }

    /**
     * 主要功能:计算一个日期加上day天后的日期 date为“yyyy-MM-dd”的形式 注意事项:无
     *
     * @param date 初始日期
     * @param day 天数
     * @return 日期
     */
    public static String addDayLocal(String date, int day) {
        DateTimeFormatter format = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT_EN);
        LocalDate dateTime = LocalDate.parse(date, format);
        dateTime = dateTime.plusDays(day);
        return dateTime.toString(format);
    }

    /**
     * 主要功能:计算一个日期加上day天后的日期 date为“yyyy-MM-dd”的形式 注意事项:无
     *
     * @param date 初始日期
     * @param day 天数
     * @return 日期
     */
    public static String addDay(String date, int day) {
        DateTimeFormatter format = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT_EN);
        DateTime dateTime = DateTime.parse(date, format);
        dateTime = dateTime.plusDays(day);
        return dateTime.toString(format);
    }
    /**
     * 主要功能:计算一个日期加上day天后的日期 date
     *
     * @param date 初始日期
     * @param day 天数
     * @return 日期
     */
    public static Date addDay(Date date, int day) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusDays(day);
        return dateTime.toDate();
    }

    /**
     * 指定格式时间，加年、月、日后得到的日期。
     *
     * @param date 起始日期
     * @param year 年
     * @param month 月
     * @param day 天
     * @param formatter 格式
     * @return 计算结果
     */
    public static String addDate(String date, int year, int month, int day, String formatter) {
        DateTimeFormatter format = DateTimeFormat.forPattern(formatter);
        DateTime dateTime = DateTime.parse(date, format);
        dateTime = dateTime.plusYears(year).plusMonths(month).plusDays(day);
        return dateTime.toString(format);
    }

    /**
     * 计算日期，加年、月、日后得到的日期。
     *
     * @param date 起始日期
     * @param year 年
     * @param month 月
     * @param day 天
     * @return 计算结果
     */
    public static Date addDate(Date date, int year, int month, int day) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(year).plusMonths(month).plusDays(day).toDate();
    }

    /**
     * 主要功能:计算一个日期加上year年后的日期
     *
     * @param date 初始日期
     * @param year 年数
     * @return 日期
     */
    public static Date addYear(Date date, int year) {
        DateTime dateTime = new DateTime(date);
        dateTime = dateTime.plusYears(year);
        return dateTime.toDate();
    }

    /**
     * 主要功能:获取当前系统时间 注意事项:无
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getSysDate() {
        DateTimeFormatter format = DateTimeFormat.forPattern(DEFAULT_DATETIME_FORMAT);
        DateTime dateTime = new DateTime(new Date());
        return dateTime.toString(format);
    }

    /**
     * 主要功能:获取当前系统时间 注意事项:无
     *
     * @return yyyyMMddHHmmss
     */
    public static String getNewSysDate() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    /**
     * 主要功能: 获取月末日期 注意事项:无
     *
     * @param todayDate 今天日期
     * @return 月末日期
     */
    public static Date getLastDay(Date todayDate) {
        Calendar calendar = Calendar.getInstance();
        // 把平台日期赋值给Calendar
        calendar.setTime(todayDate);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        int endday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, endday);
        return calendar.getTime();
    }

    /**
     * 主要功能:获取月初日期 注意事项:无
     *
     * @param todayDate 今天日期
     * @return 月初日期
     */
    public static Date getFirstDay(Date todayDate) {
        Calendar calendar = Calendar.getInstance();
        // 把平台日期赋值给Calendar
        calendar.setTime(todayDate);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 主要功能:获取年初日期 注意事项:无
     *
     * @param todayDate 今天日期
     * @return 月初日期
     */
    public static Date getFirstDayOfYear(Date todayDate) {
        Calendar calendar = Calendar.getInstance();
        // 把平台日期赋值给Calendar
        calendar.setTime(todayDate);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的年月 + 指定日 ※超过当月最大天数时，按当月月末处理
     *
     * @param date 日期
     * @param dayIndex 指定日
     * @return 指定日期的年月 + 指定日
     * @since 1.0
     */
    public static Date getDateOfMonthYearByDay(Date date, int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));

        int endday = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (dayIndex > endday) {
            dayIndex = endday;
        }
        calendar.set(Calendar.DAY_OF_MONTH, dayIndex);

        return calendar.getTime();
    }

    /**
     * 主要功能:判断字符串是否为日期 注意事项:无
     *
     * @param str 日期字符串 yyyy-MM-dd
     * @return 是否
     */
    public static boolean isDateStr(String str) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_EN);
        try {
            format.parse(str);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * 主要功能:判断字符串是否为日期 注意事项:无
     *
     * @param str 日期字符串 yyyy-MM-dd
     * @return 是否
     */
    public static boolean isDateStr(String str,boolean lenient) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT_EN);
        try {
            format.setLenient(lenient);
            format.parse(str);
            String str2 = format.format(format.parse(str));
            if (StringUtil.equals(str, str2)) {
                return true;
            }else{
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isDateStrE(String str,boolean lenient) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_OTHER_FORMAT);
        try {
            format.setLenient(lenient);
            format.parse(str);
            String str2 = format.format(format.parse(str));
            if (StringUtil.equals(str, str2)) {
                return true;
            }else{
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 主要功能:判断字符串是否为日期 注意事项:无
     *
     * @param str 日期字符串 yyyy-MM-dd
     * @param parsePatterns 格式
     * @return 是否
     */
    public static boolean isDateStr(String str, String... parsePatterns) {
        try {
            parseDate(str, parsePatterns);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 日期按月相加
     *
     * @param date 原日期
     * @param num 相加月数，正数为加，负数为减
     * @return Date
     */
    public static Date addMonthDate(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);// 日期减月
        return calendar.getTime();
    }

    /**
     * 日期按月相加，
     *
     * @param str 原日期
     * @param num 相加月数，正数为加，负数为减
     * @return Date
     * @throws ParseException 异常
     */
    public static Date addMonthDateStr(String str, Integer num)
            throws ParseException {
        return addMonthDate(DateUtil.parse(str), num);
    }

    /**
     * 日期按月相加，
     *
     * @param str 原日期
     * @param num 相加月数，正数为加，负数为减
     * @return Date
     * @throws ParseException 异常
     */
    public static Date addMonthDateStrM(String str, Integer num) {
        return addMonthDate(DateUtil.parse(str), num);
    }

    /**
     * 主要功能: 获取上个月 注意事项:无
     *
     * @param selMonth yyyy-MM
     * @return 上个月字符串
     * @throws ParseException 异常
     */
    public static String getLastMonth(String selMonth)
            throws ParseException {
        Date d = DateUtil.parse(selMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, -1);
        return format(calendar.getTime(), "yyyy-MM");
    }

    /**
     * 返回指定日期的季的第一天
     *
     * @param date 日期
     * @return 日期字符串
     * @throws ParseException 异常
     */
    public static Date getFirstDayOfQuarter(Date date)
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int quarterOfYear = calendar.get(Calendar.MONTH) / 3 + 1;
        int month = 0;
        switch (quarterOfYear) {
            case 1:
                month = 0;
                break;
            case 2:
                month = 3;
                break;
            case 3:
                month = 6;
                break;
            case 4:
                month = 9;
                break;
            default:
                month = calendar.get(Calendar.MONTH);
        }
        calendar.set(calendar.get(Calendar.YEAR), month, 1);
        return calendar.getTime();
    }

    /**
     * 获取指定日期所在的下一周中输入.固定周所对应的日期
     *
     * @param date 日期
     * @param dayOfWeek 周几
     * @return 周几所在日期
     * @throws ParseException 异常
     */
    public static Date getWeekDayOfNextWeekByDate(Date date, String dayOfWeek)
            throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, StringUtil.toInt(dayOfWeek) - 1);
        return cal.getTime();
    }

    /**
     * 获取指定日期之前最迟的【周几】所对应的日期
     *
     * @param date 日期
     * @param dayOfWeek 周几
     * @return 周几所在日期
     * @throws ParseException 异常
     */
    public static Date getFirstWeekDayByDate(Date date, String dayOfWeek)
            throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, StringUtil.toInt(dayOfWeek) - 1);
        Date result = cal.getTime();
        if (result.after(date)) {
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            result = cal.getTime();
        }
        return result;
    }

    /**
     * 判断给定日期是否为月末的一天
     *
     * @param date 日期
     * @return true:是|false:不是
     */
    public static boolean isLastDayOfMonth(Date date) {
        if(date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定日期是否为年末的一天
     *
     * @param date 日期
     * @return true:是|false:不是
     */
    public static boolean isLastDayOfYear(Date date) {
        if(date == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_YEAR) == 1) {
            return true;
        }
        return false;
    }

    /**
     * 功能：获取上一个月的最后一天
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年4月22日
     * @param date
     * @return 
     * Date
     */
    public static String getLastDayOfLayMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取上月的最后一天
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date endDate = calendar.getTime();
        return DateUtil.format(endDate);
    }


    /**
     * 功能：获取上一个月的最后一天
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年4月22日
     * @param date
     * @return
     * Date
     */
    public static String getLastDayOfLayMonth(Date date,String format){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取上月的最后一天
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date endDate = calendar.getTime();
        return DateUtil.format(endDate,format);
    }

    /**
     * 功能：获取本月的最后一天
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2021年4月22日
     * @param date
     * @return
     * Date
     */
    public static String getLastDayOfThisMonth(String  dateStr){
        if(StringUtil.isNotEmpty(dateStr)){
            if(6==dateStr.length()){  //202111
                dateStr=dateStr.substring(0,4)+"-"+dateStr.substring(5,6);
            }
        }else{
            dateStr=DateUtil.getCurrentDate();
        }
        Calendar calendar = Calendar.getInstance();
        Date date=DateUtil.parse(dateStr);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = calendar.getTime();
        return DateUtil.format(endDate);
    }
}
