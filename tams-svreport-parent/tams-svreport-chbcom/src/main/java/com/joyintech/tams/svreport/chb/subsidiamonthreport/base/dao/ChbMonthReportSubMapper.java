package com.joyintech.tams.svreport.chb.subsidiamonthreport.base.dao;

import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSub;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbMonthReportSubMapper {
    int deleteByPrimaryKey(String reportuuid);

    int insert(ChbMonthReportSub record);

    int insertSelective(ChbMonthReportSub record);

    int insertBasicByBasicInfo(ChbMonthReportSub record);

    ChbMonthReportSub selectByPrimaryKey(String reportuuid);

    int updateByPrimaryKeySelective(ChbMonthReportSub record);

    int updateByPrimaryKey(ChbMonthReportSub record);
}