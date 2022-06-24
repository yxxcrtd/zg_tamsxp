package com.joyintech.tams.svreport.chb.subsidiamonthreport.base.dao;

import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSubTemplate;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbMonthReportSubTemplateMapper {
    int deleteByPrimaryKey(String tempuuid);

    int insert(ChbMonthReportSubTemplate record);

    int insertSelective(ChbMonthReportSubTemplate record);

    int insertBasicByBasicInfo(ChbMonthReportSubTemplate record);

    ChbMonthReportSubTemplate selectByPrimaryKey(String tempuuid);

    int updateByPrimaryKeySelective(ChbMonthReportSubTemplate record);

    int updateByPrimaryKey(ChbMonthReportSubTemplate record);
}