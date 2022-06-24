package com.joyintech.tams.svreport.chb.subsidiamonthreport.base.dao;

import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSubHis;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbMonthReportSubHisMapper {
    int deleteByPrimaryKey(String reportuuid);

    int insert(ChbMonthReportSubHis record);

    int insertSelective(ChbMonthReportSubHis record);

    int insertHisByBasicInfo(ChbMonthReportSubHis record);

    ChbMonthReportSubHis selectByPrimaryKey(String reportuuid);

    int updateByPrimaryKeySelective(ChbMonthReportSubHis record);

    int updateByPrimaryKey(ChbMonthReportSubHis record);
}