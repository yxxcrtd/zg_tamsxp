package com.joyintech.tams.chbreport.codecheck.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbBotAstPositionSub;
import com.joyintech.tams.common.base.entity.ChbPrdDeclareSub;
import com.joyintech.tams.common.base.entity.ChbPrdDeclareSubTmp;
import com.joyintech.tams.common.base.entity.ChbPrdIssueSub;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;

@Repository
public interface ComminCodeCheckTmpMapper {
	
    
    /**
     * 不同产品之间，产品名称与产品期次不得同时重复
     */
    
    List<ChbPrdDeclareSubTmp>  genChbPrdDeclareSubTmp1ByProdInsId(@Param("prodInsId")String  prodInsId);

    /**
     * 不同产品之间，产品名称与产品期次不得同时重复
     */
    
    List<ChbPrdDeclareSubTmp>  genChbPrdDeclareSubTmp2ByProdInsId(@Param("prodInsId")String  prodInsId);

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
     * @param chbBotAstPosSubTmp 查询对象
     * @return 符合条件的个数
     */
    List<ChbBotAstPositionSub> queryChbBotAstPosByInfo(ChbBotAstPositionSub chbBotAstPosSubTmp);

    /**
     *  根据行内资产负债编码查询是否存在
     * @param assetCode 行内资产负债编码
     * @return 记录个数
     */
    int queryChbAssetInfoSubByAssetCode(@Param("assetCode")String  assetCode);

    /**
     *  根据行内资产负债编码查询是否存在已报送的数据
     * @param assetCode 行内资产负债编码
     * @return 记录个数
     */
    int queryChbAssetInfoSubByAssetCodeAndSendStatus(@Param("assetCode")String  assetCode);

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
}