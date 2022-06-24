package com.joyintech.tams.common.svreport.datacheck.beforeinsert.dao;

import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.base.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCodeCheckMapper {
	
    
    /**
     * 不同产品之间，产品名称与产品期次不得同时重复
     */
    
    List<String>  genChbPrdDeclareSub1ByProdInsId(@Param("tableName")String tableName, @Param("prodInsId")String  prodInsId, @Param("prodName")String  prodName, @Param("prodStage")String prodStage);

    /**
     * 不同产品之间，产品名称与产品期次不得同时重复
     */
    
    List<String>  genChbPrdDeclareSub2ByProdInsId(@Param("prodInsId")String  prodInsId, @Param("prodName")String  prodName, @Param("prodStage")String prodStage);

    /**
     *  根据产品代码查询产品申报登记信息
     * @param map 参数
     * @return 产品申报登记信息
     */
    List<ChbPrdDeclareSub> queryChbPrdDeclareSubByRegCode(Map<String, Object> map);

    /**
     *  根据产品登记编码查询产品登记编号信息
     * @param regCode 产品登记编码
     * @return 产品登记编号信息
     */
    List<ChbPrdresistCode> queryChbPrdresistCodeByRegCode(@Param("regCode")String  regCode);

    /**
     *  同一“对应资管及委外资产行内资产/负债编码”、同一“持仓日期”下，对于不同“底层资产行内资产/负债编码”的记录，该要素须保持一致
     * @param chbBotAstPosSub 查询对象
     * @return 符合条件的个数
     */
    List<ChbBotAstPositionSub> queryChbBotAstPosByInfo(ChbBotAstPositionSub chbBotAstPosSub);

    /**
     *  根据行内资产负债编码查询是否存在
     * @param assetCode 行内资产负债编码
     * @return 记录个数
     */
    int queryChbAssetInfoSubByAssetCode(@Param("assetCode")String  assetCode);

    // add by dubaojun 2020-10-12 配置无法实现，代码实现的check start
    /**
     * 根据查询参数获取对应的产品交易登记信息中行内资产/负债编码
     * 
     * @param paraMap 查询参数
     * @return 行内资产/负债编码
     */
    List<String> selectChbInvestTradeSubByBussKey(Map<String, String> paraMap);

    /**
     * 根据行内资产/负债编码获取资产要素登记的条数
     * 
     * @param assetCode 行内资产/负债编码
     * @return 条数
     */
    int countChbAssetInfoSubByAssetCode(@Param("assetCode")String assetCode);
    // add by dubaojun 2020-10-12 配置无法实现，代码实现的check end
    
    /**
     * 根据产品代码查询产品发行信息
     */
    List<ChbPrdIssueSub>   getChbPrdIssueSubByProdId(@Param("prodId")String  prodId);
    
	/**
	 * 从业人员已登记
	 * @param key
	 * @return
	 */
	int findEmployeeSub(@Param("key") String key);

	int deleteAssetInfoErrorDataByAssetCode(@Param("assetCode") String assetCode);

    int deleteChbSalerStlDeaErrorDataByTradeNo(@Param("tradeNo") String tradeNo);

    int deleteChbSalerIdenErrorDataByTradeNo(@Param("salerInsNo") String salerInsNo);

    int deleteChbSalerInstErrorDataByTradeNo(@Param("salerOrgCode") String salerOrgCode);

    int deleteChbSalerRewErrorDataByTradeNo(@Param("salerRewPenaNo") String salerRewPenaNo);
    //证件号码重复校验(修改)
    int checkSalerCredentNo(@Param("salerCredentType") String salerCredentType,@Param("salerOtherCredentName") String salerOtherCredentName,@Param("salerCredentNo") String salerCredentNo);

    /**
     * check所属总部或分支机构校验有效性
     * @param
     * @return
     */
    int checkSalerOrgCode(@Param("salerOrgCode") String salerOrgCode);

    //总部或分支机构名称重复校验（新建）
    int checkSalerOrgName(String salerOrgName);

    int checkSalerInsNo (String salerInsNo);

    void deleteChbInvestTradeErrorData(@Param("prodId") String prodId,@Param("assetCode") String assetCode, @Param("tradeCode") String tradeCode);

    void deleteChbPrdBaseInfoErrorData(String prodId);


    List<Map<String, String>> selectChbInvestTradeBusKey(@Param("list") List<ChbInvestTradeErrodataWithBLOBs> list);

    List<Map<String, String>> selectChbPrdBaseInfoBusKey(@Param("list") List<ChbPrdBaseinfoErrodataWithBLOBs> list);

    List<Map<String, String>> selectChbAssetInfoBusKey(@Param("list") List<ChbAssetInfoErrodataWithBLOBs> list);

    void deleteChbPrdBaseInfo(@Param("list") List<ChbPrdBaseinfoErrodataWithBLOBs> list);

    void deleteChbInvestTrade(@Param("list") List<ChbInvestTradeErrodataWithBLOBs> list);

    void deleteChbAssetInfo(@Param("list") List<ChbAssetInfoErrodataWithBLOBs> list);

    List<Map<String, String>> selectChbPrdDeclareInfoBusKey(@Param("list") List<ChbPrdDeclareInfoErrodataWithBLOBs> list);

    void deleteChbPrdDeclareInfoErrorData(String prodInsId);

    void deleteChbPrdDeclareInfo(@Param("list") List<ChbPrdDeclareInfoErrodataWithBLOBs> list);

    int checkExistsAssetCode(String assetCode);

    int checkInvestTradeCodeOther(Map<String, Object> pageParam);

    int checkInvestTradeCode(Map<String, Object> pageParam);

    void deleteMstHolidayErrodata(@Param("calendarId") String calendarId,@Param("cdate") String cdate);

    void deleteMstHoliday(@Param("list") List<MstHolidayErrodataWithBLOBs> list);

    void deleteMstExchargeQuoteErrodata(@Param("exchargeCode") String exchargeCode, @Param("tradeDate") String tradeDate,@Param("datasourceType") String datasourceType);

    void deleteMstExchargeQuote(@Param("list") List<MstExchargeQuoteErrodataWithBLOBs> list);
}