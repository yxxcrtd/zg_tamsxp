/*
 * 文件名：ChbDeadlineUtil.java
 * 版权：Copyright 2019 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：weiyuqiang
 * 修改时间：2019年3月22日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */
 
package com.joyintech.tams.report.common.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.tams.report.common.dao.ChbDeadLineMapper;
import com.joyintech.tams.report.common.dto.ChbDeadLineDto;
import com.joyintech.tams.report.common.service.DeadlineService;
import com.joyintech.tams.constant.DictCodeListExt2;

/**
 * 报送时点工具类
 * @author weiyuqiang
 * @version 1.0 2019年3月22日
 * @see DeadlineUtil
 * @since 1.0
 */
public class DeadlineUtil {
    /**
     * 权益人通过报送时点配置与数据类型生成行内报送截止日与人行报送截止日
     * 
     * @param reportCode 报表类型
     * @param type 数据类型 01 新增、 02 修改、03 删除
     * @param map 当前批次map集合
     * @param reportDate 数据日期
     * @param timeConfig
     *            时间单位（时分秒）配置项，取自系统参数,如果取不到则取timeConfi为HH:MM:SS
     * @return  List<Date>
     * @see
     * @since 1.0
     */
    public static List<Date> getChbEndDate(String reportCode, String type,
                                                 Map<String,List<Date>> map,Date reportDate,String timeConfig) {
        // 获取当前日期
        Date date;
        if(DictCodeListExt2.报送数据状态_新建.getValue().equals(type)){
            date = reportDate;
        }
        else{
            date = DateUtil.now();
        }
        String key = DateUtil.format(date)+ "|" + type + "|" + reportCode ;
        if(map!= null&&CollectionUtils.isNotEmpty(map.get(key))){
            return map.get(key);
        }
        ChbDeadLineDto regTimePoints001Dto =  SpringContextUtil.getBean(
            ChbDeadLineMapper.class).findByReportCode(reportCode,null);
        DeadlineService deadlineService =  SpringContextUtil.getBean(
                DeadlineService.class);
        //节假日通用方法
        List<Date> list =  deadlineService.getChbSubmitDeadlineByMark(date, regTimePoints001Dto, map,type,timeConfig);
        return list;
    }
    /**
     * 通过报送时点配置与数据类型生成行内报送截止日与中债报送截止日
     * 
     * @param reportCode 报表类型
     * @param reportTypeDetail 报表細節类型
     * @param type 数据类型 01 新增、 02 修改、03 删除
     * @param map 当前批次map集合
     * @param reportDate 数据日期
     * @param timeConfig
     *            时间单位（时分秒）配置项，取自系统参数,如果取不到则取timeConfi为HH:MM:SS
     * @return  List<Date>
     * @see
     * @since 1.0
     */
    public static List<Date> getChbEndDate(String reportCode,String reportTypeDetail, String type,
                                                 Map<String,List<Date>> map,Date reportDate,String timeConfig) {
        // 获取当前日期
        Date date;
        if(DictCodeListExt2.报送数据状态_新建.getValue().equals(type)){
            date = reportDate;
        }
        else{
            date = DateUtil.now();
        }
        String key = DateUtil.format(date)+ "|" + reportCode ;
        if(map!= null&&CollectionUtils.isNotEmpty(map.get(key))){
            return map.get(key);
        }
        ChbDeadLineDto regTimePoints001Dto =  SpringContextUtil.getBean(
            ChbDeadLineMapper.class).findByReportCode(reportCode,reportTypeDetail);
        DeadlineService deadlineService =  SpringContextUtil.getBean(
                DeadlineService.class);
        //节假日通用方法
        List<Date> list =  deadlineService.getChbSubmitDeadlineByMark(date, regTimePoints001Dto, map,type,timeConfig);
        return list;
    }
}
