/*
 * 文件名：ChbFeedbackMsgComponentMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2020年10月20日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */
 
package com.joyintech.component.svrreport.chb.feedbackmsg.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.joyintech.component.svrreport.chb.dto.ChbInfoReportDto;
import com.joyintech.component.svrreport.chb.dto.ChbPrdresistCode;
import com.joyintech.component.svrreport.chb.dto.ChbPrdresistCodeKey;
import com.joyintech.component.svrreport.chb.dto.ChbSubmitStatusDto;

/**
 * 中债处理结果处理Mapper
 * @description 
 * @author 王波
 * @version 1.0 2020年10月20日
 * @see ChbFeedbackMsgComponentMapper
 * @since 1.0
 */
@Repository
public interface ChbFeedbackMsgComponentMapper {

	/**
	 * 更新中债一二期报送状态
	 * @param status 反馈状态
	 * @return
	 */
	int updateChbSubmitStatusTwo(ChbSubmitStatusDto status);

	/**
	 * 更新 资产估值登记反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbAssetValSub(ChbSubmitStatusDto status);

	/**
	 * 更新 产品申报登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbPrdDeclareSub(ChbSubmitStatusDto status);


	/**
	 * 更新 产品发行登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbPrdIssueSub(ChbSubmitStatusDto status);


	/**
	 * 更新 募集总量登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbPrdRaisetotSub(ChbSubmitStatusDto status);

	/**
	 * 更新 产品存续期登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbPrdDealSub(ChbSubmitStatusDto status);

	/**
	 * 更新 产品终止登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbPrdEndSub(ChbSubmitStatusDto status);


	/**
	 * 更新 底层资产持仓登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbBottomAstPosSub(ChbSubmitStatusDto status);

	/**
	 * 更新 资产交易信息登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbInvestTradeSub(ChbSubmitStatusDto status);


	/**
	 * 更新资产要素登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbAssetInfoSub(ChbSubmitStatusDto status);

	/**
	 * 更新从业人员登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbAstPosSub(ChbSubmitStatusDto status);

	/****0722升级**/
	/**
	 * 更新产品状态登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbPrdStatusSub(ChbSubmitStatusDto status);

	/**
	 * 更新资产持仓登记 反馈状态
	 * @param status  反馈状态
	 * @return
	 */
	int updateChbEmployeeInfoSub(ChbSubmitStatusDto status);
	/****0722升级**/

	/**
	 * 更新投资者明细 反馈状态
	 * @param status 反馈状态
	 * @return
	 */
	int updateChbEquDetSubmitStatus(ChbSubmitStatusDto status);




	/**
	 * 更新中债三期反馈状态
	 * @param status 反馈状态
	 * @return
	 */
	int updateChbSubmitStatus(ChbSubmitStatusDto status);


	/**
	 * 更新中债三期反馈状态
	 * @param status 反馈状态
	 * @return
	 */
	int updateChbSubmitStatusNew(ChbSubmitStatusDto status);

	/**
	 * 更新 中债直连报送 状态
	 * @param record
	 * @return
	 */
	int updateInfoReportByPrimaryKey(ChbInfoReportDto record);
	
	
	
	/**
     * 根据登记银行代码获取机构代码
     *
     * @param paramMap 查询参数
     * @return 机构代码
     * @see
     * @since 1.0
     */
    String selectOrgCodeByRegistBankCode(Map<String, Object> paramMap);
    
    
    
    ChbPrdresistCode selectByPrimaryKey(ChbPrdresistCodeKey key);
    
    
    int insertSelective(ChbPrdresistCode record);
    
    
    int updateByPrimaryKeySelective(ChbPrdresistCode record);

    List<String> queryChbOrgRegistBankCodeList();
}
