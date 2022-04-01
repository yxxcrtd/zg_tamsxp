package com.joyintech.service.basicdata.multiportfoliomaintenance.dao;

import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MutiptlPortfolioDetailMapper {
    int deleteByPrimaryKey(String seqNo);

    int insert(MutiptlPortfolioDetailDto record);

    int insertSelective(MutiptlPortfolioDetailDto record);

    MutiptlPortfolioDetailDto selectByPrimaryKey(String seqNo);

    int updateByPrimaryKeySelective(MutiptlPortfolioDetailDto record);

    int updateByPrimaryKey(MutiptlPortfolioDetailDto record);

    List<MutiptlPortfolioDetailDto> selectForPage(Map<String, Object> param);

    List<MutiptlPortfolioDetailDto> getMutiptlPortfolioAcctnoTree(Map<String, Object> param);

    int isExistPortfolio(MutiptlPortfolioDetailDto dto);
}