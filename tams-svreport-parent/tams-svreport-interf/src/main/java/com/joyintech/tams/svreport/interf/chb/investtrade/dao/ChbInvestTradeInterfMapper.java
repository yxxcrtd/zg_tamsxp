package com.joyintech.tams.svreport.interf.chb.investtrade.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbAssetPosition;
import com.joyintech.tams.common.base.entity.ChbInvestTrade;
import com.joyintech.tams.common.base.entity.ChbInvestTradeEx;
import com.joyintech.tams.common.base.entity.ChbOrgRegistBankCode;
import com.joyintech.tams.svreport.interf.chb.investtrade.dto.MstCounterPartyDto;

@Repository
public interface ChbInvestTradeInterfMapper {
	
	/**
	 * 功能:批量更新投资交易记录
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月29日
	 */
	int updateBatch(@Param("updateList")List<ChbInvestTrade> updateList);

	/**
	 * 功能:批量插入投资交易记录
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月29日
	 */
	int insertBatch(@Param("insertList")List<ChbInvestTrade> insertList);

	/**
	 * 功能:根据交易编号删除的投资交易信息
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月20日
	 * @param tradeCode
	 * @return 
	 * int
	 */
	int deleteByTradeCode(@Param("insertList")List<ChbInvestTrade> insertList);
	
	/**
	 * 功能:通过交易编码查询交易是否存在
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月18日
	 * @param tradeCode
	 * @return 
	 * int
	 */
	int selectIsExistInvestDealByTradeCode(@Param("tradeCode")String tradeCode);
	
    /**
     * 查询资产持仓份额
     * @param pos
     * @return
     */
    BigDecimal selectAccuqtyByModel(ChbAssetPosition pos);
	
	/**
	 * 功能:根据接入日期查询需要接入的投资交易信息
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月18日
	 * @param interfDate 接入日期
	 * @return 
	 * List<ChbAssetInfoDto>
	 */
	void selectInvestTradeByInterfdateBatch(@Param("interfDate")Date interfDate ,
			@Param("prodList")List<String> prodList);
	
	
	/**
	 * 功能:根据接入日期查询需要接入的投资交易信息
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月18日
	 * @param interfDate 接入日期
	 * @return 
	 * List<ChbAssetInfoDto>
	 * update by leipeng 20200707 多法人数据修改
	 */
	void selectInvestTradeByInterfdateBatchEx(@Param("interfDate")Date interfDate ,
			@Param("prodList")List<String> prodList);

	/**
	 * 功能:插入投资交易记录
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月29日
	 */
	int insert(ChbInvestTrade investTrade);

    /**
     * 功能:清空投资交易
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月7日
     * @return 
     * int
     */
    int deleteAllData();
    
    /**
     * 功能:根据更新日期清除数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月7日
     * @param isToday 是否当天
     * @return 
     * int
     */
    int deleteBySysTime(@Param("isToday")boolean isToday);

    /**
     * 功能:批量插入/更新投资交易记录
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年1月29日
     * @param newDataList 新增数据
     */
    int mergeInfoList(@Param("newDataList")List<ChbInvestTrade> insertList);
    
    /**
     * 上游接口数据入库
     * @param insertList
     * @return
     */
    int mergeInfoListEx(@Param("newDataList")List<ChbInvestTrade> insertList);
    
    /**
     * 功能:根据创建日期查询库中资产交易编号
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年2月20日
     * @param isToday 是否是今天创建的数据
     * @return 
     * List<String>
     */
    List<String> selectTradeCodeBySysdate(@Param("isToday")boolean isToday);
    
    /**
        *  功能:根据交易编号查找对应投资交易额外信息
     * @since 1.7.0
     * @version 1.7.0
     * @author：gaojian 2019年07月23日
     * @param tradeId 交易编号
     * @return 
     */
    ChbInvestTradeEx queryChbInvestTradeEx(@Param("tradeId") String tradeId);

    List<ChbInvestTrade> selectChlTrans4TradeInfo(HashMap<String, Object> map);
    
    /**
     * 查询特殊资产标的挂的通道代码（项目类，计划类利率型，存款类）
     * @return
     */
    List<ChbInvestTrade> selectSpAssetChl(@Param("isCalLoadRate")String isCalLoadRate);
    
    /**
	 * 功能:查询交易对手名称
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月19日
	 * @return 
	 * List<String>
	 */
	List<MstCounterPartyDto> selectAllTradeCounter();

	ChbOrgRegistBankCode selectByRegistBankCode(String registBankCode);

    void deleteChbInvestTrade(@Param("list") List<ChbInvestTrade> list);

	void deleteChbInvestTradeErroData(@Param("list") List<ChbInvestTrade> list);
}
