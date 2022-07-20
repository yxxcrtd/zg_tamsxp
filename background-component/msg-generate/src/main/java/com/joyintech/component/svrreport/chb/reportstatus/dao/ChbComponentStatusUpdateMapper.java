/*
 * 文件名：ChbAssetInfoSubComponentMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年10月12日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.component.svrreport.chb.reportstatus.dao;

import com.joyintech.component.svrreport.chb.dto.ChbSubmitStatusDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资产要素登记 状态更新mapper
 * @description 
 * @author 王波
 * @version 1.0 2020年10月12日
 * @see ChbComponentStatusUpdateMapper
 * @since 1.0
 */
@Repository
public interface ChbComponentStatusUpdateMapper {
	/**
	 * 更新资产估值登记发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbAssetValSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新资产估值登记反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbAssetValSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 更新产品申报登记 发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbPrdIssueSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新产品申报登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbPrdIssueSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 更新产品发行登记 发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbPrdDeclareSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新产品申报登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbPrdDeclareSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);
	/**
	 * 更新产品募集总量登记 发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbPrdRaisetotSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新产品募集总量登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbPrdRaisetotSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 更新产品存续期登记 发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbPrdDealSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新产品存续期登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbPrdDealSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);


	/**
	 * 更新产品终止登记 发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbPrdEndSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新产品终止登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbPrdEndSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);


	/**
	 * 更新底层资产持仓登记 发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbBottomAstPosSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新底层资产持仓登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbBottomAstPosSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 更新资产交易登记 发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbInvestTradeSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新资产交易登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbInvestTradeSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 更新资产要素登记  发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbAssetInfoSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/**
	 * 更新资产要素登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbAssetInfoSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 从业人员登记  发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbEmployeeInfoSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
			@Param("feedbackStatus") String feedbackStatus);

	/***0722升级**/
	/**
	 * 报表名称_产品状态登记  发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbPrdStatusSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
												  @Param("feedbackStatus") String feedbackStatus);

	/**
	 * 报表名称_资产持仓登记发送状态
	 * @param sendStatus
	 * @param feedbackStatus
	 * @return
	 */
	public int updateChbAstPosSubSendStatus(@Param("registNo") String registNo, @Param("sendStatus") String sendStatus,
												  @Param("feedbackStatus") String feedbackStatus);

	/**
	 * 报表名称_产品状态登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbPrdStatusSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 报表名称_资产持仓登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbAstPosSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/***0722升级**/
	/**
	 * 从业人员登记 反馈状态
	 * @param status
	 * @return
	 */
	public int updateChbEmployeeInfoSubSubmitStatus(@Param("registNo") String registNo, @Param("status") String status);

	/**
	 * 查询SUB最新状态
	 * @param tableName 表名
	 * @param registNo 报送流水号
	 * @return sub基本信息
	 */
	List<ChbSubmitStatusDto> querySubDataStatusBySumitSeq(@Param("tableName") String tableName, @Param("registNo") List<String> registNo);
}
