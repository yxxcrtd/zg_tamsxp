package com.joyintech.tams.common.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.PtlPortfolio;

@Repository
public interface PtlPortfolioMapper {
    int deleteByPrimaryKey(String portfolioId);

    int insert(PtlPortfolio record);

    int insertSelective(PtlPortfolio record);

    int insertBasicByBasicInfo(PtlPortfolio record);

    PtlPortfolio selectByPrimaryKey(String portfolioId);

    int updateByPrimaryKeySelective(PtlPortfolio record);

    int updateByPrimaryKey(PtlPortfolio record);
    
	int updateRaiseType(Map<String,Object> param);
	
	String selectportfolioId(@Param("finprodId") String finprodId);

	List<Map<String,Object>> getRiskInfoByPortfolioId(Map<String,Object> param);

    List<PtlPortfolio> selectPortfolioByIds(@Param("portfolioIds") List<String> portfolioIds);

}