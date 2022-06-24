/*
 * 文件名：MarketCalendarInfo001Mapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2018年8月3日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dao;


import com.joyintech.tams.common.base.entity.MstCalendarInfo;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto.ChbCalendarInfo001Dto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto.ChbCalendarInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 市场日历信息mapper接口
 * 〈功能详细描述〉
 * @author xiexinting
 * @version 1.0 2022年3月30日
 * @see ChbCalendarInfoMapper
 * @since 1.0
 */
@Repository
public interface ChbCalendarInfoMapper {

    /**
     * 
     * 查询
     * <功能详细描述>
     * @param param 查询参数
     * @return 查询结果的实体类集合
     * @see
     * @since 1.0
     */
    List<ChbCalendarInfo001Dto> selectInfo(Map<String, Object> param);
    /**
     * 
     * 日历类型表信息查询
     * <功能详细描述>
     * @param parm 导入数据
     * @return 查询结果集合
     * @see
     * @since 1.0
     */
    List<ChbCalendarInfoDto> selectCalendarInfo(Map<String,Object> parm);

    /**
     * 
     * 导入（新建）
     * <功能详细描述>
     * @param list 导入数据
     * @return 导入结果
     * @see
     * @since 1.0
     */
    int insertBatchMstHoliday(@Param("list")List<ChbCalendarInfo001Dto> list);
    /**
     * 
     * 导入（修改）
     * <功能详细描述>
     * @param list 导入数据
     * @return 导入结果
     * @see
     * @since 1.0
     */
    int updateBatchCalendarInfo(@Param("list")List<ChbCalendarInfo001Dto> list);



    /**
     * 查询记录条数
     * @param list
     * @return
     */
    int  selectMstHoliday(@Param("list")List<Map<String, String>> list);

    int countDuplicatedData(@Param("list") List<Map<String, String>> list);

    List<ChbCalendarInfo001Dto> selectByKeys(@Param("list") List<ChbCalendarInfo001Dto> list);
}
