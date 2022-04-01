/*
 * 文件名：CfetsQuotedCommonMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangliang
 * 修改时间：2020年5月13日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.bankuplink.common.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.background.component.uplink.interbank.database.entity.IbuQuoteBond;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.system.base.entity.SysUserAdd;
import com.joyintech.tams.common.base.entity.EtrDistributeInterfTmp;
import com.joyintech.tams.common.base.entity.EtrQuoteFinishStatus;

/**
 * 银行间上行共通Mapper
 * 
 * @author zhangliang
 * @version 1.0 2020年5月13日
 * @see CfetsQuotedCommonMapper
 * @since 1.0
 */
@Repository
public interface CfetsQuotedCommonMapper {

    /**
     * 查询本方交易员列表
     * 
     * @param map 入参
     * @return 委托列表
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> queryMySideTraderList(Map<String, Object> map);
    
    
    
    /**
     * 查询对手方交易员列表
     * 
     * @param map 入参
     * @return 委托列表
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> queryCounterSideTraderList(Map<String, Object> map);
    
    /**
     * 查询本方机构列表
     * 
     * @param map 入参
     * @return 本方机构列表
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> queryMySideList(Map<String, Object> map);
    
    /**
     * 查询对手方机构列表
     * 
     * @param map 入参
     * @return 本方机构列表
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> queryCounterSideList(Map<String, Object> map);

    /**
     * 组合代码查询（无权限）
     * <功能详细描述>
     * @param param 查询参数
     * @return 集合
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> selectPortfolioId(Map<String, Object> param);
    
    /**
     * 根据市场代码查询金融产品代码
     * 
     * @param param 入参
     * @return 金融产品代码
     * @see
     * @since 1.0
     */
    String queryFinprodIdByFinprodMarketId(Map<String, Object> param);
    
    /**
     * 
     * 获取新建界面的资产代码下拉框
     * 
     * @param param 入参
     * @return
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> queryFinprodIdList(Map<String, Object> param);

    /**
     * 根据当前登录用户查询对应的交易员id<br/>
     * <功能详细描述>
     * @param param 参数
     * @return 对应的交易员id
     * @see
     * @since 1.0
     */
    List<SysUserAdd> queryLoginTraderId(Map<String, Object> param);
    
    /**
     * 查询市场代码列表
     * 
     * @param param 入参
     * @return 市场代码列表
     * @see
     * @since 1.0
     */
    List<String> queryFinprodMarketIdList(Map<String, Object> param);

    /**
     * 根据组合代码和本方机构查询账户来源
     * 
     * @param param 入参
     * @return 账户来源
     */
    String querySecAcctSourceByPtlIdPartyId(Map<String, Object> param);
    
    /**
     * 委托已报额度查询
     * 
     * @param param 入参
     * @return 账户来源
     */
    BigDecimal queryUsedShareAmt(Map<String, Object> param);
    
    /**
     * 查询组合证券账户下的证券代码
     * @param map
     * @return
     */
    String qryPortfolioSecAcctId(Map<String, Object> map);

    /**
     * 查询产品资金账户
     *
     * @param param
     * @return java.lang.String
     * @author jiangpeng
     * @date 2021/12/13 16:14
     */
    String qryPortfolioCashAcctId(Map<String, Object> param);
    
    /**
     * 根据查询查询符合条件的指令编号
     * 
     * @param map 入参
     * @return 报价编号
     * @see
     * @since 1.0
     */
    List<SuggestDto> queryCfetsEntrustIdList(Map<String, Object> map);
    
    // add by etliu 20210122 start
    /**
     * 根据对手银行间机构，21位编号查询系统交易对手id
     * @param map 对手银行间机构，21位编号
     * @return 系统交易对手id
     */
    String selectCounterIdByCpPartyId(Map<String, Object> map);
    // add by etliu 20210122 end

	int checkDataStatus(Map<String, Object> map);



	List<EtrDistributeInterfTmp> selectEtrDistributeInterfTmpList(Map<String, Object> map);



	List<EtrQuoteFinishStatus> selectEtrQuoteFinishStatusList(Map<String, Object> param);
	
	/**
	 * 根据成交编号查询报价信息
	 * @param param 成交编号
	 * @return 报价信息
	 */
	List<IbuQuoteBond> selectQuoteInfoByExecId(Map<String, Object> param);

    List<SelectOptionDto> selectEntrustId(Map<String, Object> map);

    /**
     * 获取资金账户
     * @param portfolioId
     * @param acctType
     * @return
     */
    String getACCTNO(@Param("portfolioId") String portfolioId, @Param("acctType") String acctType);

    /**
     * 获取证券账户
     * @param portfolioId
     * @param finprodId
     * @return
     */
    String getSecAcctId(@Param("portfolioId") String portfolioId,@Param("finprodId") String finprodId,@Param("tradeMarket") String tradeMarket);
}
