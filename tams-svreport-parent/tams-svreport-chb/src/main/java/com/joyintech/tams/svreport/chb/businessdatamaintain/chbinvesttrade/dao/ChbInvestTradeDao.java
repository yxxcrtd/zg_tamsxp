package com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto.ChbBokSubjectDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-04-27 16:37
 */
@Repository
public interface ChbInvestTradeDao {
    List<SelectOptionDto> getProdName(Map<String, Object> param);

    List<SelectOptionDto> getRegCode(Map<String, Object> param);

    List<SelectOptionDto> getCustomerName(Map<String, Object> param);

    List<SelectOptionDto> getAssetCode(Map<String, Object> param);

    Map<String,String> selectProdIdAndRegCodeByProdName(String prodName);

    ChbInvestTradeDto selectByKey(@Param("tradeSeqno") String tradeSeqno);

    Map<String,String> selectAssetNameByAssetCode(String assetCode);

    void update(ChbInvestTradeDto entity);

    void insert(ChbInvestTradeDto entity);

    List<ChbInvestTradeDto> selectAll(Map<String, Object> pageParam);

    Map<String, String> selectRivalPartyType(String tradeCounter);

    void mergeData(@Param("list") List<ChbInvestTradeDto> subList);

    void deleteData();

    int countDuplicatedData(@Param("list") List<Map<String, String>> list);

    int selectByBusinessKey(@Param("tradeCode") String tradeCode);

    List<ChbInvestTradeDto> selectByKeys(@Param("list") List<String> list);

    BigDecimal getRateNearByDate(Map<String, Object> temp);


    Map<String,Object> queryCounterType(@Param("counterId") String counterId);

    /**
     * 交易对手ID
     * @return
     */
    List<SelectOptionDto> queryCounterIdList(Map<String, Object> param);


    /**
     * 交易对手二级市场
     * @return
     */
    List<SelectOptionDto> queryCounterType2List(Map<String, Object> param);
}
