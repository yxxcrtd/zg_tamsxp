package com.joyintech.tams.svreport.chb.subsidiamonthreport.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDetailDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDto;

/**
 * 理财子公司月度报表登记Mapper
 *
 * @author 杜保军
 * @version 1.0 2020年6月4日
 * @since 1.0
 */
@Repository
public interface ChbSubsidiaMonthReportMapper {

    /**
     * 根据画面查询条件查询符合条件的记录
     *
     * @param pageParam 查询参数
     * @return 理财子公司月度报表集合
     */
    List<ChbMonthReportSubDto> searchPage(Map<String, Object> pageParam);

    /**
     * 将原表数据插入到履历表中
     *
     * @param registList 需要处理的数据
     * @return 插入条数
     */
    int insertReportHis(@Param("registList") List<ChbMonthReportSubDto> registList);

    /**
     * 删除原表数据
     *
     * @param registList 需要处理的数据
     * @return 删除的条数
     */
    int deleteLastSubmitRegist(@Param("registList") List<ChbMonthReportSubDto> registList);

    /**
     * 取消手工处理时将履历表中的数据移回登记表
     *
     * @param registList 需要处理的数据
     * @see
     * @since 1.0
     */
    void insertReportDataFromHis(@Param("registList") List<ChbMonthReportSubDto> registList);

    /**
     * 取消手工处理时将履历表中的数据移回登记表
     *
     * @param registList 需要处理的数据
     * @see
     * @since 1.0
     */
    void deleteReportDataHis(@Param("registList") List<ChbMonthReportSubDto> registList);

    /**
     * 批量更新发送状态
     *
     * @param sendStatus 发送状态
     * @param registList 需要更新的数据
     */
    void updateInterfSubmitAndSeqBatch(@Param("sendStatus") String sendStatus, @Param("registList") List<ChbMonthReportSubDto> registList);

    /**
     * 取消手工处理，将发送状态改为未报送
     *
     * @param list 需要处理的数据
     * @return 更新条数
     */
    Integer cancelMannultRegist(List<ChbMonthReportSubDto> list);

    /**
     * 根据主键查询理财子公司月度报表数据.
     *
     * @param keyList 主键集合
     * @return 报表数据
     */
    List<ChbMonthReportSubDto> searchByKeys(List<String> keyList);

    /**
     * 根据主键查询理财子公司月度报表数据
     * 
     * @param reportuuid
     * @return
     */
    ChbMonthReportSubDto selectMonthReportSubByPrimaryKey(String reportuuid);

    /**
     * 查询子公司月度统计表明细数据
     * 
     * @param selectParam 检索参数
     * @return 子公司月度统计表明细
     */
    List<ChbMonthReportSubDetailDto> selectMonthReportSubDetail(Map<String, Object> selectParam);

    /**
     * 查询所有的余额模板
     * 
     * @return 余额模板
     */
    List<String> selectAllBalanceTemplate();
}