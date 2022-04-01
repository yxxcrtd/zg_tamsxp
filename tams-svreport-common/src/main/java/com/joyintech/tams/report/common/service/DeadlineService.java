/*
 * 文件名：ChbDeadlineService.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：Administrator
 * 修改时间：2017年12月16日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.report.common.dao.ChbDeadLineMapper;
import com.joyintech.tams.report.common.dto.ChbDeadLine001Dto;
import com.joyintech.tams.report.common.dto.ChbDeadLineDto;
import com.joyintech.tams.report.common.util.DateCalcUtil;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.constant.DictCodeListExt2;

/**
 * 报送时点
 * @author weiyuqiang
 * @version 1.0 2017年12月16日
 * @see DeadlineService
 * @since 1.0
 */
@Service
public class DeadlineService extends BaseService<ChbDeadLine001Dto>{
    /**
     * 报送时点mapper
     */
    @Autowired
    private ChbDeadLineMapper chbDeadLineMapper;
    
    /**
     * 获取报送时点
     * @param date                 传入日期
     * @param regTimePoints001Dto  报送时点配置
     * @param map                  返回参数
     * @param flag                  01 新建、02 修改 、03 删除
     * @param timeConfig
     *            时间单位（时分秒）配置项，取自系统参数,如果取不到则取timeConfi为HH:MM:SS
     * @return List<Date>           返回报送截止日
     * @see
     * @since 1.6.0
     * @author add by weiyuqiang
     */
    public  List<Date> getChbSubmitDeadlineByMark(Date date,ChbDeadLineDto regTimePoints001Dto,Map<String,List<Date>> map
                                                  ,String flag, String timeConfig){
        String dates = DateUtil.format(date);
        String key = dates+ "|" + flag + "|" + regTimePoints001Dto.getReportCode() ;
        //中债报送截止日
        ChbDeadLine001Dto holidaymarket001DtoChb = new ChbDeadLine001Dto();
        //行内报送截止日
        ChbDeadLine001Dto holidaymarket001Dto = new ChbDeadLine001Dto();
        if(DictCodeListExt2.报送数据状态_新建.getValue().equals(flag)){
            if(DictCodeListExt2.报送时点类型_每周.getValue().equals(regTimePoints001Dto.getSubmitTimetype())){//中债报送截止日
//                holidaymarket001DtoChb = new Holidaymarket001Dto();
//                //行内报送截止日
//                holidaymarket001Dto = new Holidaymarket001Dto();
                //传入日期为周几
                int weekDay = DateUtil.getWeekDay(date);
                //若中债配置的时间大于当前，则直接取差值;反之则取差值+7
                if(regTimePoints001Dto.getSubmitPointBank().intValue()>=weekDay){
                    holidaymarket001DtoChb.setBusiDate(DateUtil.addDay(date,regTimePoints001Dto.getSubmitPointBank().intValue()-weekDay ));
                    holidaymarket001Dto.setBusiDate(DateUtil.addDay(date,regTimePoints001Dto.getSubmitPointBond().intValue()-weekDay ));
                }
                else{
                    holidaymarket001DtoChb.setBusiDate(DateUtil.addDay(date,regTimePoints001Dto.getSubmitPointBank().intValue()+7-weekDay ));
                    holidaymarket001Dto.setBusiDate(DateUtil.addDay(date,regTimePoints001Dto.getSubmitPointBond().intValue()+7-weekDay ));
                }
            }
            else if(DictCodeListExt2.报送时点类型_每月.getValue().equals(regTimePoints001Dto.getSubmitTimetype())){
            	 holidaymarket001DtoChb.setBusiDate(DateUtil.addDay(date,regTimePoints001Dto.getSubmitPointBank().intValue() ));
                 holidaymarket001Dto.setBusiDate(DateUtil.addDay(date,regTimePoints001Dto.getSubmitPointBond().intValue() ));
            }
            else{
                String type;
                if(DictCodeListExt2.推送方式_前.getValue().equals(regTimePoints001Dto.getSubmitRequire2())){
                    type = "01";
                }
                else {
                    type = "02";
                }
                //当中债配置时间为0时
                if(regTimePoints001Dto.getSubmitPointBank().compareTo(BigDecimal.ZERO)==0){
                    holidaymarket001DtoChb = chbDeadLineMapper.getChbDeadlineZero(CacheUtil.getSysParam(SVReportGlobalConstant.SVR_CALENDAR_ID),dates,type);
                    if(DictCodeListExt2.推送方式_后.getValue().equals(regTimePoints001Dto.getSubmitRequire2())&&
                        regTimePoints001Dto.getSubmitPointBond().compareTo(BigDecimal.ZERO)<0){
                        type = "01";
                    }
                    holidaymarket001Dto =  chbDeadLineMapper.getChbDeadline(CacheUtil.getSysParam(SVReportGlobalConstant.SVR_CALENDAR_ID),
                        dates, type, regTimePoints001Dto.getSubmitPointBond().intValue());
                }
                //当不为0时
                else{
                    holidaymarket001DtoChb =  chbDeadLineMapper.getChbDeadline(CacheUtil.getSysParam(SVReportGlobalConstant.SVR_CALENDAR_ID),
                        dates, type, regTimePoints001Dto.getSubmitPointBank().intValue());
                    holidaymarket001Dto =  chbDeadLineMapper.getChbDeadline(CacheUtil.getSysParam(SVReportGlobalConstant.SVR_CALENDAR_ID),
                        dates, type, regTimePoints001Dto.getSubmitPointBond().intValue());
                }

            }
            //中债报送截止日非空处理
            holidaymarket001DtoChb = this.getNullDeadline(holidaymarket001DtoChb,date,regTimePoints001Dto.getSubmitPointBank().intValue(),
                regTimePoints001Dto.getSubmitRequire2());
            //行内报送截止日非空处理
            holidaymarket001Dto = this.getNullDeadline(holidaymarket001Dto,date,regTimePoints001Dto.getSubmitPointBond().intValue(),
                regTimePoints001Dto.getSubmitRequire2());
        }
        //修改与删除时中债报送截止日与行内报送截止日相同
        else if(DictCodeListExt2.报送数据状态_修改.getValue().equals(flag)){
            holidaymarket001DtoChb =  chbDeadLineMapper.getChbDeadline(CacheUtil.getSysParam(SVReportGlobalConstant.SVR_CALENDAR_ID),
                dates, "02", regTimePoints001Dto.getUpdateSubmitPoint().intValue());
            //中债报送截止日非空处理
            holidaymarket001DtoChb = this.getNullDeadline(holidaymarket001DtoChb,date,regTimePoints001Dto.getUpdateSubmitPoint().intValue(),
                regTimePoints001Dto.getSubmitRequire2());
            BeanUtil.copyPropertiesIgnoreNull(holidaymarket001Dto, holidaymarket001DtoChb);
        }
        else if(DictCodeListExt2.报送数据状态_删除.getValue().equals(flag)){
            holidaymarket001DtoChb =  chbDeadLineMapper.getChbDeadline(CacheUtil.getSysParam(SVReportGlobalConstant.SVR_CALENDAR_ID),
                dates, "02", regTimePoints001Dto.getDeleteSubmingPoint().intValue());
            //中债报送截止日非空处理
            holidaymarket001DtoChb = this.getNullDeadline(holidaymarket001DtoChb,date,regTimePoints001Dto.getDeleteSubmingPoint().intValue(),
                regTimePoints001Dto.getSubmitRequire2());
            BeanUtil.copyPropertiesIgnoreNull(holidaymarket001Dto, holidaymarket001DtoChb);
        }
        //处理报送截止日
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(DateCalcUtil.getEndOfDay(holidaymarket001DtoChb.getBusiDate(),timeConfig));
        //行内与中债报送截止日相差
        dateList.add(DateCalcUtil.getEndOfDay(holidaymarket001Dto.getBusiDate(),timeConfig));
        map.put(key, dateList);
        return dateList;
    }
    /**
     * 节假日非空判断
     * @param holidaymarket001Dto  节假日
     * @param date                 传入日期
     * @param days                 改变天数
     * @param flag                 前后
     * @return holidaymarket001Dto  返回
     * @see
     * @since 1.0
     */
    public ChbDeadLine001Dto getNullDeadline(ChbDeadLine001Dto holidaymarket001Dto,Date date,int days,String flag){
        if(flag.equals(DictCodeListExt2.推送方式_前.getValue())){
            days = -1*days;
        }
        if(holidaymarket001Dto==null){
            holidaymarket001Dto = new ChbDeadLine001Dto();
            holidaymarket001Dto.setBusiDate(DateUtil.addDay(date, days));
        }
        return holidaymarket001Dto;
    }
}
