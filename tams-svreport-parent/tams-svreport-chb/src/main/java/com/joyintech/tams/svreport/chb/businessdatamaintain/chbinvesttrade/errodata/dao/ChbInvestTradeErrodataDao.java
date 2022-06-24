package com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.errodata.dao;

import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.errodata.dto.ChbInvestTradeErrodataWithBLOBsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wubingbing
 * @date 2022-05-26 13:22
 */
@Repository
public interface ChbInvestTradeErrodataDao {
    void deleteByPrimaryKey(String tradeSeqno);

    ChbInvestTradeErrodataWithBLOBsDto selectByPrimaryKey(String tradeSeqno);

    void deleteChbInvestTradeErrodata(@Param("prodId") String prodId,@Param("tradeCode") String tradeCode, @Param("assetCode") String assetCode);
}
