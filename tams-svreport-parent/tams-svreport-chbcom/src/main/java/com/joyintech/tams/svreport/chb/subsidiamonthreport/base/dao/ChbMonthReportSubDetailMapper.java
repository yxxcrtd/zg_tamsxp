package com.joyintech.tams.svreport.chb.subsidiamonthreport.base.dao;

import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSubDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbMonthReportSubDetailMapper {
    int deleteByPrimaryKey(String detailuuid);

    int insert(ChbMonthReportSubDetail record);

    int insertSelective(ChbMonthReportSubDetail record);

    int insertBasicByBasicInfo(ChbMonthReportSubDetail record);

    ChbMonthReportSubDetail selectByPrimaryKey(String detailuuid);

    int updateByPrimaryKeySelective(ChbMonthReportSubDetail record);

    int updateByPrimaryKey(ChbMonthReportSubDetail record);
}