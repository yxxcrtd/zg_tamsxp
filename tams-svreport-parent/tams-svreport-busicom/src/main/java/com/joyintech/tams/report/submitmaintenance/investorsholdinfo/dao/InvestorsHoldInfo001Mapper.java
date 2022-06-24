/*
 * 文件名：InvestorsHoldInfo001Mapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：天津滨海农商行理财资管系统
 * 修改人：dingzhipeng
 * 修改时间：2018年5月7日
 * 修改内容：新建
 * 系统名称：天津滨海农商行理财资管系统
 */

package com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.joyintech.tams.common.base.entity.ChbInvestholdInfo;
import com.joyintech.tams.common.base.entity.ChbInvestholdInfoSub;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001Dto;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dto.InvestorsHoldInfoRegist001Dto;
import org.springframework.stereotype.Repository;

/**
 * 投资者持有信息
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2018年5月7日
 * @see InvestorsHoldInfo001Mapper
 * @since 1.0
 */
@Mapper
@Repository
public interface InvestorsHoldInfo001Mapper {

    /**
     * 查询所有的待处理数据
     * <功能详细描述>
     * @param pageParam
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfo001Dto>  selectAllData(Map<String, Object> pageParam);

    /**
     * 批量逻辑删除
     * <功能详细描述>
     * @param map 存储删除参数
     * @see
     * @since 1.0
     */
    void updateByKeyList(@Param("stautsD")String stautsD, @Param("keyList")List<InvestorsHoldInfo001Dto> keyList);

    /**
     * 批量物理删除
     * <功能详细描述>
     * @param map 存储删除参数
     * @see
     * @since 1.0
     */
    void deleteByKeyList(@Param("keyList")List<InvestorsHoldInfo001Dto> keyList);

    /**
     * 获取所有的登记银行编码和机构代码的集合
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    List<Map<String,String>> getAllRegistBankCodeSet();
    /**
     * 获取所有的产品登记编码与产品代码的集合
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    List<Map<String,String>> getAllRegistProdCodeSet();
    /**
     * 获取所有维护表的关联信息
     * <功能详细描述>
     * @param newKeyList keyList
     * @return 关联信息
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfo001Dto> selectAllHoldInfoData(@Param("newKeyList")List<InvestorsHoldInfo001Dto> newKeyList);

    List<InvestorsHoldInfo001Dto> selectAllHoldInfoDataNew();



    /**
     * 获取所有登记表的关联信息
     * <功能详细描述>
     * @param newKeyList keyList
     * @return 关联信息
     * @see
     * @since 1.0
     */
    List<ChbInvestholdInfoSub> selectAllSubHoldInfoData(@Param("newKeyList")List<InvestorsHoldInfo001Dto> newKeyList);

    List<ChbInvestholdInfoSub> selectAllSubHoldInfoDataNew();


    /**
     * 批量插入维护表
     * <功能详细描述>
     * @param newDataList 数据集合
     * @see
     * @since 1.0
     */
    void insertChbHoldInfoData(@Param("newDataList")List<InvestorsHoldInfo001Dto> newDataList);

    void insertChbHoldInfoDataMerge(@Param("newDataList")List<InvestorsHoldInfo001Dto> newDataList);
    /**
     * 批量插入登记表数据
     * <功能详细描述>
     * @param subList 数据集合
     * @see
     * @since 1.0
     */
    void insertChbSubHoldInfoData(@Param("subList")List<ChbInvestholdInfoSub> subList);

    void insertChbSubHoldInfoDataMerge(@Param("subList")List<ChbInvestholdInfoSub> subList);

    /**
     * 删除中间表
     * <功能详细描述>
     * @see
     * @since 1.0
     */
    void deleteTmpTable();

    /**
     * 查询最新的汇率
     * <功能详细描述>
     * @param temp 参数
     * @return 最新的结果
     * @see
     * @since 1.0
     */
    BigDecimal getRateNearByDate(Map<String, Object> temp);

    /**
     * 删除登记表的未报送的数据
     * <功能详细描述>
     * @param map 参数map
     * @see
     * @since 1.0
     */
    void deleteUnSubByKeyList(Map<String, Object> map);

    /**
     * 统计产品登记编码是否存在
     * <功能详细描述>
     * @param prdRegistCode 产品登记编码
     * @return
     * @see
     * @since 1.0
     */
    int countProdReg(String prdRegistCode);

    /**
     * 获取所有的有效的投资者的集合
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    List<Map<String,String>> getAllInvestorSet();

    /**
     * 统计投资者数量
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    int countInvestor(Map<String, String> map);

    /**
     * 统计主键重复数量
     * @param map
     * @return
     */
    int countPrimaryKey(Map<String, String> map);

    /**
     * 批量入产品登记编码表数据
     * @param prodRegInfoList 产品信息
     * @see
     * @since 1.0
     */
    void insertProdRegInfo(@Param("prodRegInfoList")List<ChbPrdresistCode> prodRegInfoList);

    /**
     * 根据产品登记编码获取产品代码
     *
     * @param prdRegistCode 产品登记编码
     * @return 产品代码
     * @see
     * @since 1.0
     */
    String getProdidByPrdRegistCode(String prdRegistCode);

    /**
     * 根据产品代码和机构代码，查询持有者信息待处理数据（待处理，且产品登记编码为空）
     *
     * @param paramMap 参数
     * @return 持有者信息待处理数据（待处理，且产品登记编码为空）
     * @see
     * @since 1.0
     */
    List<ChbInvestholdInfo> selectStatusAInvestholdInfoData(Map<String, Object> paramMap);

    /**
     * 更新投资者持有信息维护表的产品登记编码和数据状态字段
     *
     * @param dataList 数据集合
     * @see
     * @since 1.0
     */
    void updateChbInvestholdInfoData(@Param("dataList") List<InvestorsHoldInfo001Dto> dataList,
                                     @Param("prdRegistCode") String prdRegistCode,
                                     @Param("statusToUpdate") String statusToUpdate);


	List<ChbInvestholdInfoSub> querySumbitInvestHoldByCode(ChbInvestholdInfo chbInvestholdInfo);

	int insertDeleteInvestReg(ChbInvestholdInfoSub chbInvesthold);

    /**
     *
     * 批量删除登记表中由于修改造成修改未报送的的脏数据
     * @param deleteDataList 数据源
     * @see
     * @since 1.0
     */
    void deleteInvestRegist(List<ChbInvestholdInfoSub> deleteDataList);

	int deleteByPrimary(ChbInvestholdInfoSub chbInvestholdInfoSub);

	int updateByPrimaryKeySelected(ChbInvestholdInfoSub chbInvesthold);

    /**
     * 查询检查序列号
     * @param investList 主键信息
     * @return 检查序列号集合
     * @see
     * @since 1.0
     */
    List<String> selectCheckSeqno(List<ChbInvestholdInfoSub> investList);

    /**
     *
     * 查询登记表是否存在已报送的与修改数据相同的数据
     * @param chbInvestholdInfoSub 查询条件
     * @return 结果信息
     * @see
     * @since 1.0
     */
    ChbInvestholdInfoSub selectSendDataToCompare(@Param("dto")ChbInvestholdInfoSub dto);

    /**
     *
     * 更新有效状态的投资者持有信息维护表的产品登记编码字段
     * @param dataList 数据集合
     * @return 结果信息
     * @see
     * @since 1.0
     */
    void updateChbInvestholdInfoData2(@Param("dataList") List<InvestorsHoldInfo001Dto> dataList,
                                      @Param("prdRegistCode") String prdRegistCode);

    /**
     *
     * 更新有效状态的投资者持有信息登记表的产品登记编码字段
     * @param dataList 数据集合
     * @return 结果信息
     * @see
     * @since 1.0
     */
    void updateChbInvestholdInfoSubData(@Param("dataList") List<InvestorsHoldInfo001Dto> dataList,
                                      @Param("prdRegistCode") String prdRegistCode);

    /**
     * 根据产品代码和机构代码，查询持有者信息有效的数据
     *
     * @param paramMap 参数
     * @return 持有者信息有效的数据
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfo001Dto> selectStatusEInvestholdInfoData(Map<String, Object> paramMap);

   /**
    * 根据产品代码和机构代码查询来查询所有状态的持有信息数据
    */
    List<InvestorsHoldInfo001Dto> selectAllInvestholdInfoData(Map<String, Object> paramMap);
    /**
     * 查询所有的待处理数据
     * <功能详细描述>
     * @param pageParam
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfo001Dto>  selectAllDataByList(@Param("keyList") List<InvestorsHoldInfo001Dto> dataList);

    List<InvestorsHoldInfo001Dto> selectInvestHoldInfoFeedBackData(Map<String, Object> map);

    List<InvestorsHoldInfo001Dto> selectAllInvestHoldInfoFeedBackData(Map<String, Object> map);

    /**
     * 持有信息修改之前查询反馈状态
     * <功能详细描述>
     * @param pageParam
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfoRegist001Dto>  getSubmitFeedbackStatusList(List<ChbInvestholdInfoSub> subListUp);

    List<InvestorsHoldInfoRegist001Dto>  getSubmitFeedbackStatusListNew();

    /**
     * 查询当天持有信息条数
     *
     * @param map 参数
     * @return 当天持有信息条数
     * @see
     * @since 1.0
     */
    int selectInvestHoldInfoDataCount(Date date);
    /**
     * 通过主键批量获取登记表中的数据
     * @param keyList 传入参数
     * @return
     */
	List<ChbInvestholdInfoSub> querySumbitInvestHoldByCodeBatch(@Param("keyList") List<InvestorsHoldInfo001Dto> keyList);
	/**
	 * 批量删除登记表中未报送数据
	 * @param subList list
	 * @return
	 */
	int deleteByPrimaryBatch(@Param("subList") List<ChbInvestholdInfoSub> subList);
	/**
	 * 批量插入删除数据
	 * @param subList list
	 * @return
	 */
	int insertDeleteInvestRegBatch(@Param("subList") List<ChbInvestholdInfoSub> subList);
	/**
	 * 批量更新删除数据
	 * @param subList list
	 * @return
	 */
	int updateByPrimaryKeySelectedBatch(@Param("status") String status, @Param("subList") List<ChbInvestholdInfoSub> subList);

	/**
	 *
     * 更新持有信息维护表中数据状态为删除的数据，状态修改为有效
     *
     * @param dataList 处理数据对象
     * @return 删除数据件数
	 * @see
	 * @since 1.0
	 */
	int handleInfoDeleteData(@Param("dataList") List<InvestorsHoldInfo001Dto> dataList);

    int handleInfoDeleteDataNew();


    /**
	 * 删除持有信息登记表中数据状态为删除的数据
	 *
	 * @param dataList 处理数据对象
	 * @return 删除数据件数
	 * @see
	 * @since 1.0
	 */
	int handleSubDeleteData(@Param("dataList") List<InvestorsHoldInfo001Dto> dataList);

    int handleSubDeleteDataNew();

    /**
     * 更新有效数据切产品登记编码不相同的数据
     *
     * @param paramMap 参数
     * @return 持有者信息有效的数据
     * @see
     * @since 1.0
     */
    int updateInfoRegCodeByProdid(Map<String, Object> paramMap);
    /**
     * 更新登记表
     *
     * @param paramMap 参数
     * @return 持有者信息有效的数据
     * @see
     * @since 1.0
     */
    int updateRegistRegCodeByProdid(Map<String, Object> paramMap);
    
    int countInvestorByIndentifyMark(@Param("indentifyMark")String indentifyMark);

    List<String> selectAllInvestor();
}
