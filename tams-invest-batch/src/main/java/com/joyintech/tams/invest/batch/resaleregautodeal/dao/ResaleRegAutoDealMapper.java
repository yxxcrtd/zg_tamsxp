package com.joyintech.tams.invest.batch.resaleregautodeal.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ResaleRegAutoDealMapper {

    List<String> listTotleTradeId(Map<String,Object> param);

    List<String> getTotleTradeId(@Param("totleTradeId")List<String> totleTradeId);

    int deleteMatchInfoByPreTradeId(@Param("totleTradeId")List<String> totleTradeId);

    int deleteDealTotal(@Param("totleTradeId") List<String> totleTradeId);

    int deletePaDealTotal(@Param("totleTradeId") List<String> totleTradeId);

    int deleteDeal(@Param("totleTradeId") List<String> totleTradeId);

    int deletePadeal(@Param("totleTradeId") List<String> totleTradeId);

    int deleteDealAdd(@Param("totleTradeId") List<String> totleTradeId);
}
