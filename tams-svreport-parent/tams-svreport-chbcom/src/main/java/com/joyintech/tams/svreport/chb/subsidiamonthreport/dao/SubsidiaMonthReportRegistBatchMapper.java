package com.joyintech.tams.svreport.chb.subsidiamonthreport.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthAssetPosition;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSub;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.base.entity.ChbMonthReportSubTemplate;

/**
 * 理财子公司月度报表批处理Mapper
 *
 * @author 杜保军
 * @version 1.0 2020年6月4日
 * @since 1.0
 */
@Repository
public interface SubsidiaMonthReportRegistBatchMapper {

    /**
     * 根据参数删除理财子公司报表主表数据
     * 
     * @param chbMonthReportSub 删除参数
     * @return 删除数据条数
     */
    int deleteByModel(ChbMonthReportSub chbMonthReportSub);

    /**
     * 根据期限代码查询已报送的数据
     * 
     * @param termid 期限代码
     * @return 已报送的数据
     */
    List<ChbMonthReportSub> selectSendDataByTermId(@Param("termid") String termid);

    /**
     * 根据期限代码查询未报送的数据
     * 
     * @param termid 期限代码
     * @return 未报送的数据
     */
    List<ChbMonthReportSub> selectUnSendDataByTermId(@Param("termid") String termid);

    /**
     * 根据配置的报表类型查询报表模板
     * 
     * @param reporttype 报表类型
     * @return 报表模板
     */
    List<ChbMonthReportSubTemplate> selectSubTemplatesByReporttype(@Param("reporttype") String reporttype);

    /**
     * 删除理财子公司月度报表明细数据
     * 
     * @param termid 期限
     * @param reporttype 报表类型
     * @param wbsreportuuid 元报送ID
     */
    void deleteChbMonthReportSubDetail(@Param("termid") String termid, @Param("reporttype") String reporttype, @Param("reportuuid") String wbsreportuuid);

    /**
     * 根据报送日期取得资产头寸数据
     * 
     * @param firstday 月初
     * @param lastday 月末
     * @return 资产头寸
     */
    List<ChbMonthAssetPosition> selectAssetPositionsByMonthDate(@Param("firstday") Date firstday, @Param("lastday") Date lastday);

}