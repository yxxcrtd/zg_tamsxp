/*
 * 文件名：InvestorsHoldInfoRegist001Mapper.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月9日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbSubmitStatus;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dto.InvestorsHoldInfoRegist001Dto;

/**
 * 投资者持有信息登记mapper
 * @author zuoyinyi
 * @version 1.0 2018年5月9日
 * @see InvestorsHoldInfoRegist001Mapper
 * @since 1.0
 */
@Repository
public interface InvestorsHoldInfoRegist001Mapper {
    /**
     *
     * 查询页面数据
     * @param map 查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfoRegist001Dto> selectAllData(Map<String,Object> map);
    // add by zcw 优化start
    /**
     *
     * 查询页面数据不加拍下一
     * @param map 查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfoRegist001Dto> selectAllDataNoOrder(Map<String,Object> map);

    /**
     * 优化批量插入履历表
     * @param list 数据集
     * @see
     * @since 1.0
     */
    void insertInvestorsHoldInfoRegistHisBetter(@Param("dtos")List<InvestorsHoldInfoRegist001Dto> dtos);

    /**
     * 删除履历表中已报送数据
     * @param list 数据集
     * @see
     * @since 1.0
     */
    void deleteSubmitRegistBetter(@Param("dtos")List<InvestorsHoldInfoRegist001Dto> dtos);
    // add by zcw 优化end

    /**
     * 批量插入履历表
     * @param list 数据集
     * @see
     * @since 1.0
     */
    void insertInvestorsHoldInfoRegistHis(@Param("keys")List<String> keys);

    /**
     * 删除履历表中已报送数据
     * @param list 数据集
     * @see
     * @since 1.0
     */
    void deleteSubmitRegist(@Param("keys")List<String> keys);

    /**
     * 删除报送表中已报送数据(按照主键)
     * @param list 数据集
     * @see
     * @since 1.0
     */
    void deleteSubmitRegistByPK(List<InvestorsHoldInfoRegist001Dto> list);

    /**
     * 删除报送表中已报送数据(按照主键)
     * @param list 数据集
     * @see
     * @since 1.0
     */
    void deleteAllAlreadySubmitRegistData();

    /**
     * 接口报送更新
     * @param sendStatus 报送状态
     * @param investList 数据集
     * @see
     * @since 1.0
     */
    void updateInterfSubmit(@Param("sendStatus")String sendStatus, @Param("investList")List<InvestorsHoldInfoRegist001Dto> investList);
    /**
     * 接口报送更新
     * @param sendStatus 报送状态
     * @param investList 数据集
     * @see
     * @since 1.0
     */
    void updateInterfSubmitAndSeq(@Param("sendStatus")String sendStatus, @Param("investList")List<InvestorsHoldInfoRegist001Dto> investList);

    // add by etliu 20180818 start
    /**
     * 接口报送更新
     * @param sendStatus 报送状态
     * @param investList 数据集
     * @see
     * @since 1.0
     */
    void updateInterfSubmitAndSeqBatch(@Param("sendStatus")String sendStatus, @Param("investList")List<InvestorsHoldInfoRegist001Dto> investList);
    // add by etliu 20180818 end

    /**
     * 接口报送更新(更新所有检查通过的数据)
     * @param sendStatus 报送状态
     * @param investList 数据集
     * @see
     * @since 1.0
     */
    void updateAllInterfSubmitAndSeqBatch(@Param("sendStatus")String sendStatus);

    /**
     * 批量插入报送状态表
     * @param listStatus 报送状态
     * @return 插入条数
     * @see
     * @since 1.0
     */
    Integer insertSubmitStatus(@Param("list")List<ChbSubmitStatus> list);

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
     * @param map 条件
     * @return 数量
     * @see
     * @since 1.0
     */
    int isNotSubmitP(Map<String, Object> map);

    /**
     * 取消手工报送更新登记表
     * @param list EquityInfoRegist001Dto
     * @return 更新条数
     * @see
     * @since 1.0
     */
    Integer updateInvestRegist(List<InvestorsHoldInfoRegist001Dto> list);

    /**
     *
     * 取消手工报送删除已报送数据
     * @param list 主键
     * @return 删除条数
     * @see
     * @since 1.0
     */
    Integer deleteMannulSubmitRegist(@Param("list")List<ChbSubmitStatus> list);

    /**
     * 查询业务主键数据是否存在
     * @param map 查询条件
     * @return 条数
     * @see
     * @since 1.0
     */
    Integer selectIsExsit(Map<String, Object> map);

    /**
     * 识别标识在投资者信息表中是否已存在
     * @return 结果集
     * @see
     * @since 1.0
     */
    List<Map<String,String>> getAllRegistBankCodeSet();

    /**
     * 查询产品登记编码
     * @return 结果集
     * @see
     * @since 1.0
     */
    List<Map<String,String>> getAllRegistProdCodeSet();

    /**
     * 取消手工处理时将履历表中的数据移回登记表
     * @param list
     * @see
     * @since 1.0
     */
    void insertInvestorsHoldInfoRegist(List<InvestorsHoldInfoRegist001Dto> list);

    /**
     * 分批量查询导出数据
     * @param checkFlag
     * @param list
     * @see
     * @since 1.0
     */
	List<InvestorsHoldInfoRegist001Dto> selectAllDataByList(@Param("checkFlag")boolean checkFlag,
			@Param("investList")List<InvestorsHoldInfoRegist001Dto> dataList);

	/**
     * 分批量查询导出数据
     * @param checkFlag
     * @param list
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfoRegist001Dto> selectAllDataKeyByList(@Param("investList")List<InvestorsHoldInfoRegist001Dto> dataList);

	/**
     * 查询每一天已经报送的持有数据
     * @param paramMap paramMap
     * @param list
     * @see
     * @since 1.0
     */
    int selectAlreadySubmitDataCount(Map<String, Object> paramMap);

    /**
     * 查询每一天已经报送的所有持有数据
     * @param paramMap paramMap
     * @param list
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfoRegist001Dto> selectAlreadySubmitData(Map<String, Object> paramMap);
    /**
     * 接口报送更新报送流水号
     * @param sendStatus 报送状态
     * @param investList 数据集
     * @see
     * @since 1.0
     */
    void updateInterfSeq(@Param("investList")List<InvestorsHoldInfoRegist001Dto> investList);
    
    /**
     * 查询投资者持有信息待检查数据
     * 
     * @param map 查询条件
     * @return 数据结果
     * @see
     * @since 1.0
     */
    List<InvestorsHoldInfoRegist001Dto> selectInvestRegistData(Map<String, Object> map);
    
    /**
     * 批量更新投资者持有信息检查序列号
     * 
     * @param investRegistUpdateList
     * @see
     * @since 1.0
     */
    void updateInvestRegistCheckSeqno(List<InvestorsHoldInfoRegist001Dto> investRegistUpdateList);
    
    /**
     * 批量更新投资者持有信息发送状态
     * 
     * @param investRegistUpdateList
     * @see
     * @since 1.0
     */
    void updateInvestorsHoldInfoRegistSendStatus(List<InvestorsHoldInfoRegist001Dto> investRegistUpdateList);
    
    int selectNoendCountByTaskBeanName(String beanname);

    int countExportNum(Map<String,Object> map);
    
    
    int insertSubmitStatusDto(ChbSubmitStatus dto);
    
    
    int updateSubmitStatusToSuccess(@Param("seqno")String seqno);
    
    List<InvestorsHoldInfoRegist001Dto>  selectAllNeedToHisData(List<InvestorsHoldInfoRegist001Dto> subList);
    
    int insertInvestorsHoldInfoRegistHisBatch(InvestorsHoldInfoRegist001Dto model);
}
