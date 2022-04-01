package com.joyintech.tams.svreport.pbc.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.base.entity.PbcSendZipDatFile;
import org.apache.ibatis.annotations.Param;

import com.joyintech.tams.svreport.pbc.classic.prdcashraisesub.dto.PbcProdRaiseSub;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;
import org.springframework.stereotype.Repository;

@Repository
public interface PbcMonthReportMapper {
    int deleteByPrimaryKey(String reportuuid);

    int insert(PbcMonthReport record);

    int insertSelective(PbcMonthReport record);

    PbcMonthReport selectByPrimaryKey(String reportuuid);

    int updateByPrimaryKeySelective(PbcMonthReport record);

    int updateByPrimaryKey(PbcMonthReport record);
    
    List<PbcMonthReport> searchPage(Map<String,Object> map);
    
    void updateInterfSubmitAndSeqBatch(@Param("sendStatus")String sendStatus, @Param("registList")List<PbcMonthReport> registList
    		,@Param("submitadd")String submitadd);

    void updateDoMannultSendZipDatFile(@Param("registList")List<PbcMonthReport> registList);

    void updateCancelMannultSendZipDatFile(@Param("registList")List<PbcMonthReport> registList);

    Integer cancelMannultRegist(List<PbcMonthReport> list);
    
    /**
	 * 功能:批量删除当月银行月度报表资产端数据
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年2月16日
     * @param termid 期数
     * @param reportType 报表类型 PROD-产品，BALANCE-资产负债表，DEPOSIT-结构性存款
     * @param reportFrequ 报表频率 M-月
     * @return 
     * int
     */
    int deleteByTermId(@Param("termid")String termid,@Param("reportType")String reportType,@Param("reportFrequ")String reportFrequ);
    
    int deleteByModel(PbcMonthReport record);
    /**
     * 查询已报送的数据（sendstatus为02和03）
     * @param termid
     * @return
     */
    List<PbcMonthReport> selectSendDataByTermId(String termid);
    
    PbcMonthReport selectByTermId(String termid);

    /**
     * 功能:将已报送数据移入履历表
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月23日
     * @param registList
     * @return 
     * int
     */
    int insertReportHis(@Param("registList")List<PbcMonthReport> registList);
    /**
     * 功能:删除登记表中的已报送数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月23日
     * @param registList
     * @return 
     * int
     */
    int deleteLastSubmitRegist(@Param("registList")List<PbcMonthReport> registList);
    /**
     * 取消手工处理时将履历表中的数据移回登记表
     * @param list
     * @see
     * @since 1.0
     */
    void insertReportDataFromHis(@Param("registList")List<PbcMonthReport> registList);

    /**
     * 功能:删除履历表中的最新已报送数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月23日
     * @param registList
     * @return 
     * int
     */
    int deleteLastSubmitHis(@Param("registList")List<PbcMonthReport> registList);
    /**
     * 根据日期和报表类型查询报表的数据
     * @param termid
     * @return
     */
    List<PbcMonthReport> selectSendDataByDateAndType(@Param("reportEndDate")String reportEndDate,@Param("reportType")String reportType);
    
    /**
     * 功能:人行单产品报表查询-资产负债信息
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年6月13日
     * @param map
     * @return 
     * List<PbcMonthReport>
     */
    List<PbcMonthReport> searchPageForOnePrd(Map<String,Object> map);

    /**
     * 功能:人行单产品报表手工处理-删除sub表数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：liuyu 2019年6月17日
     * @param infoList
     * @return 
     */
	void deleteBatchBySendStatus(List<PbcMonthReport> infoList);

	/**
	 * 人行单产品报表手工处理-删除His表数据
	 * @param infoList
	 */
	void deleteBatchSubFromHisByUpdateTime(List<PbcMonthReport> infoList);
    /**
     * 功能:根据月末日期查询登记的数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年6月12日
     * @param cdate
     * @return 
     * List<PbcMonthReport>
     */
    List<PbcMonthReport> selectRegList(@Param("cdate")String cdate,
            @Param("reportType")String reportType);

    int insertOnePrd(PbcMonthReport record);
    
    List<String> selectRegistSeqnoList(@Param("list")List<PbcMonthReport> registList,@Param("flag")String flag);

    List<PbcMonthReport> selectTermIdList(@Param("list")List<String> statusList,@Param("orgCode")String orgCode);


    List<String> findProductByName(Map<String,Object> map);


    int updatePbcReportSendInfo(@Param("sendStatus")String sendStatus,@Param("submitTime") Date submitTime
            ,@Param("submitFeedbackStatus")String submitFeedbackStatus,@Param("registList")List<PbcSendZipDatFile> datFileList);
    /**
     * 功能:将已报送数据移入履历表
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月23日
     * @param registList
     * @return
     * int
     */
    int insertReportHisForOnePrd(@Param("registList")List<PbcMonthReport> registList);

    void updateInterfSubmitAndSeqBatchForOnePrd(@Param("sendStatus")String sendStatus, @Param("registList")List<PbcMonthReport> registList
            ,@Param("submitadd")String submitadd);

    Integer cancelMannultRegistForOnePrd(List<PbcMonthReport> list);

    /**
     * 取消手工处理时将履历表中的数据移回登记表
     * @param list
     * @see
     * @since 1.0
     */
    void insertReportDataFromHisForOnePrd(@Param("registList")List<PbcMonthReport> registList);

    /**
     * 功能:删除履历表中的最新已报送数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月23日
     * @param registList
     * @return
     * int
     */
    int deleteLastSubmitHisForOnePrd(@Param("registList")List<PbcMonthReport> registList);
}