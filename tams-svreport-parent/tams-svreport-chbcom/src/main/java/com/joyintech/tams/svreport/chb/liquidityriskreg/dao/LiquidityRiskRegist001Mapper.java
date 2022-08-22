package com.joyintech.tams.svreport.chb.liquidityriskreg.dao;

import com.joyintech.tams.svreport.chb.liquidityriskreg.dto.LiquidityRiskRegist001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LiquidityRiskRegist001Mapper {
	//分页查询
	List<LiquidityRiskRegist001Dto> searchPage(Map<String,Object> map);
	//手工处理
	int updateInterfSubmitAndSeqBatch(@Param("itemId")String itemId);
	//取消手工处理
	int cancelMannultRegist(@Param("itemId")String itemId);
	//查询明细数据
	List<LiquidityRiskRegist001Dto> selectDetailData(@Param("itemId")String itemId);
	//查询机构代码
	String selectOrgCode(@Param("orgCode")String orgCode);
}