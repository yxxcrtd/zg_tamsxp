package com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dao;

import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2017年9月19日
 * @since 1.0
 */
@Repository
public interface SalesTransactionMaintenanceRegist001Mapper {
   /**
    * 查询界面上条件
    * @param param
    * @return
    * @see
    * @since 1.0
    */
    List<SalesTransactionMaintenanceRegist001Dto> queryForRegistPage(Map<String,Object> param);
    List<SalesTransactionMaintenanceRegist001Dto> searchPage(Map<String,Object> map);

    int insertIntoHis(@Param("registList")List<SalesTransactionMaintenanceRegist001Dto> registList);
    Integer cancelMannultRegist(List<SalesTransactionMaintenanceRegist001Dto> list);
    void updateInterfSubmitAndSeqBatch(@Param("sendStatus")String sendStatus, @Param("registList")List<SalesTransactionMaintenanceRegist001Dto> registList);

   int deleteLastSubmitRegist(@Param("registList")List<SalesTransactionMaintenanceRegist001Dto> registList);
   /**
    * 取消手工处理时将履历表中的数据移回登记表
    * @see
    * @since 1.0
    */
   void insertReportDataFromHis(@Param("registList")List<SalesTransactionMaintenanceRegist001Dto> registList);
   /**
    * 取消手工处理时将履历表中的数据移回登记表
    * @see
    * @since 1.0
    */
   void deleteReportDataHis(@Param("registList")List<SalesTransactionMaintenanceRegist001Dto> registList);

    /**
     * 通过主键查询选择的数据
     * @param registList
     * @see
     * @since 1.0
     */
    List<SalesTransactionMaintenanceRegist001Dto> queryExportListByKeys(@Param("registList") List<SalesTransactionMaintenanceRegist001Dto> registList);

    /**
     * 批量更新报送状态表为中债通过
     * @param list 报送状态
     * @return 插入条数
     * @see
     * @since 1.0
     */
    int updateSubmitStatusToOk(List<String> list);

    /**
     * 是否是已报送
     * <功能详细描述>
     * @param map 条件
     * @return 数量
     * @see
     * @since 1.0
     */
    int isNotSubmitP(Map<String, Object> map);


 /**
  * 查询
  * @param param
  * @return
  */
 List<SalesTransactionMaintenanceRegist001Dto> setDataList(Map<String, Object> param);

    /**
     * 查询登记表中所有已报送的数据
     * @param equityRegistList
     * @return 销售结算资金交易登记数据
     * @see
     * @since 1.0
     */
    List<SalesTransactionMaintenanceRegist001Dto> queryAllRegistSubmitList();

    /**
     * 通过主键查询选择的数据
     * @param equityRegistList
     * @return 权益人登记信息列表
     * @see
     * @since 1.0
     */
    List<SalesTransactionMaintenanceRegist001Dto> queryRegistSubmitListByKeys(@Param("equityRegistList") List<SalesTransactionMaintenanceRegist001Dto> equityRegistList);

    /**
     * 接口报送更新
     * @param salesTransactionMaintenanceRegistList 输入条件
     * @return 更新条数
     * @see
     * @since 1.0
     */
    int updateInterfSubmit(@Param("submitStatus")String submitStatus,@Param("salesTransactionMaintenanceRegistList") List<SalesTransactionMaintenanceRegist001Dto> salesTransactionMaintenanceRegistList);

    /**
     * 接口报送更新(更新所有检查通过的数据)
     * @return 更新条数
     * @see
     * @since 1.0
     */
    int updateAllInterfSubmit(@Param("submitStatus")String submitStatus);

    /**
     * 接口报送更新报送流水号（为解决报送过程中出现空流程号）
     * @param salesTransactionMaintenanceRegistList 输入条件
     * @return 更新条数
     * @see
     * @since 1.0
     */
    int updateInterfSubmitSeq(@Param("salesTransactionMaintenanceRegistList") List<SalesTransactionMaintenanceRegist001Dto> salesTransactionMaintenanceRegistList);

    String selectRegistBankCode();
}
