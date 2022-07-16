package com.joyintech.tams.svreport.chb.datacheck.beforesub.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbBeforeSubErrorCheckMapper {
       
    List<Map<String,Object>> selectProdDealSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);
        
    int updateProdDealSub(String seqno);
    
    List<Map<String,Object>> selectProdIssueSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);
    
    int updateProdIssueSub(String seqno);        
    
    List<Map<String,Object>> selectProdEndSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);
    
    int updateProdEndSub(String seqno);

    List<Map<String,Object>> selectInvestTradeSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);
    
    int updateInvestTradeSub(String seqno);  
    
    List<Map<String,Object>> selectAssetInfoSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);
    
    int updateAssetInfoSub(String seqno);
    List<Map<String,Object>> selectProdDeclareSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);
    
    int updateProdDeclareSub(String seqno);
    List<Map<String,Object>> selectProdRaiseTotalSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);

    int updateProdRaiseTotalSub(String seqno);

    List<Map<String,Object>> selectChbAstValSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);

    int updateChbAstValSub(String seqno);
    Integer isInEmpoyeeSub(@Param("id") String id, @Param("employeeType") String employeeType);
    Integer isInAssetInfoSub(String assetCode);
    Integer isInRaisetotSub(String prodId);
    Integer isInPrdEndSub(String prodId);


    Integer isInPrdIssueSub(String prodId);

    Integer isInProDeclareReg(String prodId);

    String isUploadFile(Map<String, String> dataMap);

    List<Map<String, Object>> selectChbBotAst(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);

    int updateChbBotAstSub(String seqno);

    List<String> isOutsourOrAssetMana(String assetCode);

	List<Map<String, String>> selectChbEmployeeSub(@Param("checkdate") Date checkdate);
	
	int isInEmployeeSub(@Param("key") String key);


    List<Map<String,Object>> selectChbAstPosSub(@Param("checkdate") Date checkdate,@Param("keys") List<String> keys);

    BigDecimal selectPositionAmt(@Param("prodId")String prodId, @Param("checkdate") String checkdate);

    BigDecimal  selectMonthAssetTotalAmt(@Param("termid")String termid);

    BigDecimal  selectMonthAssetTotalBoAmt(@Param("termid")String termid);
}