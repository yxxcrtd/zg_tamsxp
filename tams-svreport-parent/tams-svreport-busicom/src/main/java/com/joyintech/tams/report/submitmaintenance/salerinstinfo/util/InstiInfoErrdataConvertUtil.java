/*
 * 文件名：ErrdataConvertUtil.java
 * 版权：Copyright 2020-2021 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zhangliang
 * 修改时间：2021年1月14日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.submitmaintenance.salerinstinfo.util;

import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dto.SalerInstInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 错误信息表解析共通处理
 *
 * @author zhangliang
 * @version 2021年1月14日
 * @see InstiInfoErrdataConvertUtil
 * @since 1.0
 */
public class InstiInfoErrdataConvertUtil {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(InstiInfoErrdataConvertUtil.class);

    /**
     * 错误信息表解析共通处理
     * @param chbSaleInfo
     * @param errdata 错误信息
     */
    public static SalerInstInfoDto errdataAssetInfoConvert(SalerInstInfoDto chbSaleInfo, SalerInstInfoDto errdata){
        if (chbSaleInfo == null){
            chbSaleInfo = new SalerInstInfoDto();
        }
        if (errdata != null && StringUtil.isNotEmpty(errdata.getOrgData())){
            logger.info("调用ErrdataConvertUtil.errdataAssetInfoConvert，传入的orgData:" + errdata.getOrgData());
            if (errdata != null && StringUtil.isNotEmpty(errdata.getOrgData())){

                Map<String, Object> dataMap = clobStrToMap(errdata.getOrgData());

                for (String column : dataMap.keySet()) {
                    Object value = dataMap.get(column);
                    if (!StringUtil.isNotEmpty(value)) {
                        continue;
                    }
                    if (StringUtil.equals(column, "CREATE_TIME") || StringUtil.equals(column, "UPDATE_TIME")){
                        continue;
                    }
                    //根据数据库字段获取类的属性字段
                    Field field = ReflectionUtils.findField(chbSaleInfo.getClass(), subBlank(StringUtil.lowerCase(column)).replace(" ", ""));
                    //如果字段没有则跳过。
                    if(field==null){
                        logger.warn("该字段在所转换的实体类中没有，对象名：{}，字段名：{}", chbSaleInfo.getClass().getName(), column);
                        continue;
                    }
                    field.setAccessible(true);
                    //获取属性对应类型
                    Type type = field.getGenericType();

                    try {
                        //对于BigDecimal类型处理
                        if ("java.math.BigDecimal".equals(type.getTypeName())) {
                            //对类的实例字段进行赋值
                            field.set(chbSaleInfo, new BigDecimal(value.toString()));
                            //对于Integer类型处理
                        } else if ("java.lang.Integer".equals(type.getTypeName()) || "int".equals(type.getTypeName())) {
                            //对类的实例字段进行赋值
                            field.set(chbSaleInfo, Integer.valueOf(value.toString()));
                            //对于Long类型处理
                        } else if ("java.lang.Long".equals(type.getTypeName())) {
                            //对类的实例字段进行赋值
                            field.set(chbSaleInfo, Long.valueOf(value.toString()));
                            //对于Short类型处理
                        } else if ("java.lang.Short".equals(type.getTypeName())) {
                            //对类的实例字段进行赋值
                            field.set(chbSaleInfo, Short.valueOf(value.toString()));
                            //对于Date类型处理
                        } else if ("java.util.Date".equals(type.getTypeName())) {
                            if(!checkDate(value.toString())){
                                Date newDate = new Date();
                                field.set(chbSaleInfo, newDate);
                            }else {
                                //对类的实例字段进行赋值
                                field.set(chbSaleInfo, DateUtil.parse(value.toString()));
                            }
                        } else {
                            //其它类型处理
                            //对类的实例字段进行赋值
                            field.set(chbSaleInfo, value);
                        }
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        continue;
                    }
                }
            }
        }

        return chbSaleInfo;
    }

    /**
     *  clob字段解析方法
     * @param orgData 待解析数据
     * @return Map<key,value>对象
     */
    private static Map<String, Object> clobStrToMap(String orgData){
        return JsonUtils.stringToMap(orgData);
    }

    /**
     * 功能:将表字段转换成属性字段
     *
     * @since
     * @version
     * @author：
     * @param str
     * @return String
     */
    private static String subBlank(String str) {
        if (str == null) {
            return null;
        }
        String[] strarray = str.split("_");
        if (strarray.length == 0) {
            return null;
        }
        String result = strarray[0];
        for (int i = 1; i < strarray.length; i++)
            result = result + (new StringBuilder()).append(Character.toUpperCase(strarray[i].charAt(0)))
                    .append(strarray[i].substring(1)).toString();
        return result;
    }

    /**
     * 日期格式校验
     *
     * @param
     * @return 返回boolean
     */
    private static boolean checkDate(String date) {
        boolean flag = true;
        if(!com.joyintech.framework.util.DateUtil.isDateStr(date, com.joyintech.framework.util.DateUtil.DEFAULT_OTHER_FORMAT,
                com.joyintech.framework.util.DateUtil.DEFAULT_ANOTHER_FORMAT, com.joyintech.framework.util.DateUtil.DEFAULT_DATE_FORMAT_EN)){
            flag = false;
        }else{
            date = date.replace("-", "").replace("/", "");
            //年
            int year = Integer.valueOf(date.substring(0, 4));
            //月
            int month = Integer.valueOf(date.substring(4, 6));
            //日
            int day = Integer.valueOf(date.substring(6));
            if(month < 1 || month > 12){
                flag = false;
            }else{
                if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                    if(day < 1 || day > 31){
                        flag = false;
                    }
                }else if(month == 4 || month == 6 || month == 9 || month == 11){
                    if(day < 1 || day > 30){
                        flag = false;
                    }
                }else if(month == 2){
                    if((year % 4 == 0 && year % 100 != 0) ||year % 400 == 0){
                        if(day < 1 || day > 29){
                            flag = false;
                        }
                    }else{
                        if(day < 1 || day > 28){
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }
}
