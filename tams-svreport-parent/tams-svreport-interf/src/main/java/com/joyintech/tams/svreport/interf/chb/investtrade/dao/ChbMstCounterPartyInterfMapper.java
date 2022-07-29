package com.joyintech.tams.svreport.interf.chb.investtrade.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbMstCounterParty;



@Repository
public interface ChbMstCounterPartyInterfMapper {
	
	/**
	 * 功能:根据接口日期查询交易对手
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月21日
	 * @param interfDate
	 * @return 
	 * List<ChbMstCounterParty>
	 */
	List<ChbMstCounterParty> selectCounterPartyByInterfdate(@Param("interfDate")Date interfDate ,
			@Param("prodList")List<String> prodList);
	
	/**
	 * 功能:批量更新交易对手记录
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月29日
	 */
	int updateBatch(@Param("updateList")List<ChbMstCounterParty> updateList);

	/**
	 * 
	* 根据数据状态批量更新.
	* @author liuyu
	* @time 2019年5月18日 下午2:07:53
	* @param updateList
	* @return
	 */
    int updateBachByStatus(@Param("updateList")List<ChbMstCounterParty> updateList);

	/**
	 * 功能:批量插入交易对手记录
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月29日
	 */
	int insertBatch(@Param("insertList")List<ChbMstCounterParty> insertList);
	
	/**
	 * 
	*  返回传入list已存在的部分.
	* @author liuyu
	* @time 2019年5月18日 下午4:20:50
	* @param infoList
	* @return
	 */
	List<String>getExists(List<ChbMstCounterParty> infoList);
	
	/**
	 * 功能:根据交易对手id删除交易对手信息
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月21日
	 * @param insertList
	 * @return 
	 * int
	 */
	int deleteByCounterId(@Param("insertList")List<ChbMstCounterParty> insertList);

	/**
	 * 功能:批量插入交易对手记录
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月29日
	 */
	int insert(ChbMstCounterParty counterParty);
	
	/**
	 * 功能:根据接口日期查询交易对手
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月21日
	 * @param interfDate
	 * @return 
	 * List<ChbMstCounterParty>
	 */
	void selectCounterPartyByInterfdateBatch(@Param("interfDate")Date interfDate ,
			@Param("prodList")List<String> prodList);
	/**
	 * 根据交易对手主键查询交易对手信息 
	 * @return
	 */
	ChbMstCounterParty selectByPrimaryKey(String counterId);
	/**
	 * 查询所有交易对手 信息 
	 * @return
	 */
	List<ChbMstCounterParty> selectAllCounterParty();
	
	/**
	 * 功能：采用mergeinto方法入库
	 * @param infoList 
	 * 
	 */
	int mergeInto(List<ChbMstCounterParty> infoList);
	
	/**
     * 功能:清空交易对手信息
     * @since 1.7.0
     * @version 1.7.0
     * liuyu 2019年5月8日
     * @return 
     * int
     */
    int deleteAllData();
    
    /**
     * 功能:根据更新日期清除数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：liuyu 2019年5月8日
     * @param isToday 是否当天
     * @return 
     * int
     */
    int deleteByUpdateTime(@Param("isToday")boolean isToday);

	List<ChbMstCounterParty> selectOneDate(@Param("status") String status);

}