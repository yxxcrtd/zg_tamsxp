/*
 * 文件名：GetInvestorsHoldInfoRegistAutoSendCBMapper.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：wangmin
 * 修改时间：2018年9月25日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */
 
package com.joyintech.tams.svreport.chb.botastpositionregist.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbBotAstPositionSub;



/**
 * 底层资产持仓登记自动生成批处理mapper
 * @author 张忠良
 * @version 1.0 2019年1月28日
 * @see ChbBotAstPosCBMapper
 * @since 1.0
 */
@Repository
public interface ChbBotAstPosCBMapper {
	/**
	 * 查询出所有需要生成持仓登记的数据
	 * @param generateDate
	 * @return
	 */
	List<ChbBotAstPositionSub> queryGenerateList(@Param("generateDate")String generateDate,@Param("ischbbtm")String ischbbtm);

	/**
	 * 查询出所有需要生成持仓登记的数据
	 * @param generateDate
	 * @return
	 */
    // modify by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 start
//	int queryGenerateListAndInsert(@Param("generateDate")String generateDate,@Param("ischbbtm")String ischbbtm,@Param("statusDate")Date statusDate,
//			@Param("chbSubmitDeadline")Date chbSubmitDeadline,@Param("submitDeadline")Date submitDeadline);
	List<ChbBotAstPositionSub> queryInsertGenerateList(@Param("generateDate")String generateDate,@Param("ischbbtm")String ischbbtm,@Param("statusDate")Date statusDate,
			@Param("chbSubmitDeadline")Date chbSubmitDeadline,@Param("submitDeadline")Date submitDeadline,@Param("InstitutionCode")String InstitutionCode,
													   @Param("subjectNoList")List<String> subjectNoList);

	
	/**
	 * 查询出删除修改状态持仓登记的数据
	 * @param generateDate
	 * @return
	 */
	List<ChbBotAstPositionSub> queryUpdateOrDelList(@Param("generateDate")String generateDate,@Param("ischbbtm")String ischbbtm,@Param("InstitutionCode")String InstitutionCode);

	/**
	 * 查询已登记的数据
	 * @param generateDate
	 * @return
	 */
	List<ChbBotAstPositionSub> queryRegistedList(@Param("generateDate")String generateDate);

	
	/**
	 * 批量插入维护登记表
	 * @param investHoldList
	 * @return
	 */
	int insertRegistBatchList(@Param("chbBotAstPositionSubList") List<ChbBotAstPositionSub> chbBotAstPositionSubList);
	
	/**
	 * 删除登记表数据
	 * @param investHoldList
	 * @return
	 */
	int delete(Map<String,Object> map);
	
	
	/**
	 * 查询需要重新生成登记的日期
	 * @param generateDate
	 * @return
	 */
	List<Date> queryUpdateDate(@Param("generateDate")String generateDate);
}
