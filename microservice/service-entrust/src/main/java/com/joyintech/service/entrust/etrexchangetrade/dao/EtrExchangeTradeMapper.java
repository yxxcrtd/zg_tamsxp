/*
 * 文件名：EtrBondFirstBiddingMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：yangjm
 * 修改时间：2019年5月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.entrust.etrexchangetrade.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.joyintech.service.entrust.etrexchangetrade.dto.*;
import com.joyintech.tams.common.base.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.invest.common.trade.dto.FinCashPlanDto;
import com.joyintech.tams.invest.common.trade.dto.SecInfoModelDto;
import org.springframework.stereotype.Repository;

/**
 *  场内交易委托
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2019年11月14日
 * @see EtrExchangeTradeMapper
 * @since 1.0
 */
@Repository
public interface EtrExchangeTradeMapper {

    /**
     * 场内交易委托列表查询
     * @param param
     * @return
     */
    List<EtrExchangeTrade003Dto> selectEtrExchange(Map<String,Object> param);
    /**
     * 产品持仓信息分页查询
     * <功能详细描述>
     * @param pageParam 查询参数
     * @return 分页结果
     * @see
     * @since 1.0
     */
    List<EtrExchangeTrade002Dto> findPositionsPage(Map<String, Object> pageParam);
    /**
     * 产品债券质押持仓信息分页查询
     * 
     * @param pageParam 查询参数
     * @return 分页结果
     * @see
     * @since 1.0
     */
    List<EtrExchangeTrade002Dto> findZqzyPositionsPage(Map<String, Object> pageParam);
    /**
     * 查询证券代码联想框文本框
     *
     * @param param 画面参数
     * @return 证券代码联想框文本框
     * @see
     * @since 1.0
     */
    List<SelectOptionDto> getSecAcctId(Map<String, Object> param);

	/**
	 *质押券代码下拉框查询
	 * @param param
	 * @return
	 */
	List<SelectOptionDto> selectZyq(Map<String, Object> param);

    /**
     * 获取债券基本信息
     * 
     * @param param 查询参数
     * @return 债券基本信息
     */
    SecInfoModelDto getSecInfoModel(Map<String, Object> param);

    /**
     * 获取债券现金流
     * 
     * @param param 查询参数
     * @return 债券现金流
     */
    List<FinCashPlanDto> getFinCashPlan(Map<String, Object> param);

    /**
     * a.当前债券面额取得（根据还本计划计算）
     *
     * @param param 参数
     * @return a.当前债券面额取得（根据还本计划计算）
     * @see
     * @since 1.0
     */
    BigDecimal getCurSecAmt(Map<String, Object> param);

    /**
     * 查询债券利率重置信息
     *
     * @param param 参数
     * @return 债券利率重置信息
     * @see
     * @since 1.0
     */
    List<FinCashCalcRule> selectBondsIntResetList(Map<String, Object> param);

    /***
     * 取更新时间最新记录.投资目的  指令业务类型 = 数据字典.委托类型.大宗债券指令
     * @return
     */
    String getInvAim();

	/**
	 * 联动债券的投资目的
	 * @param map
	 * @return
	 */
	String linkInvAim(Map<String, Object> map);

	/**
	 * 联动债券的投资目的-查询当日委托
	 * @param map
	 * @return
	 */
	String linkInvAimEtr(Map<String, Object> map);

    /**
     * 对手方交易商联想文本框初始化。
     * 
     * @param map 检索参数
     * @return 联想文本框
     */
    List<SuggestDto> selectCounterDealer(Map<String, String> map);

    /**
     * 对手方交易员联想文本框初始化。
     * 
     * @param map 检索参数
     * @return 联想文本框
     */
    List<SuggestDto> selectCounterTrader(Map<String, String> map);

    /**
     * 本方交易员联想文本框初始化。
     * 
     * @param map 检索参数
     * @return 联想文本框
     */
    List<SuggestDto> selectTrader(Map<String, String> map);
    
    /**
     * 债券回售计划查询
     * @param map
     * @return
     */
    int selectBondExecPlan(Map<String, Object> map);
    
    /**
     * 获取资产自定义二级类型
     * @param map
     * @return
     */
    String getCustomerTypeTwo(Map<String, Object> map);
    
	/**
	 * 根据组合和交易场所获取资金账户
	 * 
	 * @param map
	 * @return
	 */
	String getPortfolioAcctNo(Map<String, Object> map);
	/**
     * 查询上交所和深交所标准券数量
     *
     * @param pageParam 页面参数
     * @return 上交所和深交所标准券数量
     * @see
     * @since 1.0
     */
    List<EtrExchangeTrade002Dto> selectSecNum(Map<String, Object> pageParam);
    
    
    /**
	 * 根据指令编号获取分发指令编号
	 * 
	 * @param map
	 * @return
	 */
    EtrDistribute getDistributeByEntrustId(Map<String, Object> map);
    
	/**
	 * 债券质押券入库折算率
	 * 
	 * @param map
	 * @return
	 */
	String selectStdsecConverseRatio(Map<String, Object> map);
	
	/**
	 * 标准券持仓可用查询
	 * @param map
	 * @return
	 */
	String queryStandandTicketCanUse(Map<String, Object> map);

    /**
     * 根据委托id查询委托
     * @param entrustId
     * @return
     */
    EtrEntrust selectEntrustByKey(@Param("entrustId")String entrustId);
    /**
     * 根据委托id查询委托
     * @param entrustId
     * @return
     */
    EtrEntrustAdd selectEntrustAddByKey(@Param("entrustId") String entrustId);
    
	/**
	 * 每日债券行情价格 from 业务表
	 * 
	 * @param map
	 * @return
	 */
	String qryMstBondMarketInfo(Map<String, Object> map);

	/**
	 * 每日债券行情价格 from 接口表
	 * 
	 * @param map
	 * @return
	 */
	String qryInterfBondMarketInfo(Map<String, Object> map);
	
	/**
	 * 每日债券行情价格 from 业务表
	 * 
	 * @param map
	 * @return
	 */
	String qryMstFundMarketInfo(Map<String, Object> map);

	/**
	 * 每日债券行情价格 from 接口表
	 * 
	 * @param map
	 * @return
	 */
	String qryInterfFundMarketInfo(Map<String, Object> map);
	
	/**
	 * 每日债券行情价格 from 业务表
	 * 
	 * @param map
	 * @return
	 */
	String qryMstShareMarketInfo(Map<String, Object> map);

	/**
	 * 每日债券行情价格 from 接口表
	 * 
	 * @param map
	 * @return
	 */
	String qryInterfShareMarketInfo(Map<String, Object> map);

	/**
	 *
	 * @Description //金融产品及其他信息查询
	 * @Param [map]
	 * @return java.util.List<com.joyintech.service.entrust.etrexchangetrade.dto.FinProductOthCode>
	 * @version 1.0
	 * @see
	**/
    List<FinProductOthCodeDto> getFinprodOthCodeInfo(Map<String, Object> map);


    /**
     *
     * @Description //权益额度查询
     * @Param [map]
     * @return java.util.List<com.joyintech.tams.common.base.entity.FinProductQuto>
     * @version 1.0
     * @see
    **/
	List<FinProductQuto> getFinProductQutoInfo(Map<String, Object> map);


	List<SubEqutityDto> getsubEqutity(Map<String, Object> map);

    List<WflEtrEntrust> selectWflEntrust(Map<String, Object> map);

	List<String> selectWflEntrustAddAndEtrEntrustAdd(Map<String, Object> map);

    //add by chenjiaxing 20210907 债券竞价交易添加偏离度 start
	BigDecimal getLastNetPrice(Map<String, Object> param);
	//add by chenjiaxing 20210907 债券竞价交易添加偏离度 end

	// add by etliu 20211207 start
	// 交易所债券非交易类业务迁移
	/**
	 *
	 * @Description //查询行权回售数据
	 * @Param [map]
	 * @return 行权回售数据
	 * @version 1.0
	 * @see
	 **/
	List<FinExercisePlan> selectFinExercisePlanData(Map<String, Object> map);
	// add by etliu 20211207 end

	/**
	 * 查询多组合名称下拉框
	 * @param map
	 * @return
	 */
	List<SelectOptionDto> selectPortfolioTreeId(Map<String,Object> map);

	/**
	 * 查询多组合树关联的组合
	 * @param map
	 * @return
	 */
	List<SelectOptionDto> selectPortfolioId(Map<String,Object> map);

	/**
	 * 查询多组合明细
	 * @param map
	 * @return
	 */
	List<EtrExchangeTradeMultiPtl001Dto> selectPortfolioTreeDetail(Map<String,Object> map);

	/**
	 * 获取多组合分配资金账户
	 * @param paraMap
	 * @return
	 */
	List<SelectOptionDto> selectMultiAcctNo(Map<String,Object> paraMap);

	/**
	 * 查询组合+资金账户+交易场所对应的证券账户（多组合）
	 * @param paraMap
	 * @return
	 */
	public String selectMultiSecAcctId(Map<String,Object> paraMap);
	EtrExchangeTradeMulti001Dto selectMultDetail(Map<String,Object> map);

}
