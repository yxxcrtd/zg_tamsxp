package com.joyintech.tams.svreport.chb.monthcollect.dao;

import com.joyintech.tams.svreport.chb.monthcollect.dto.ChbMonthCollect;
import com.joyintech.tams.svreport.chb.monthcollect.dto.PrdPeriodyieldForMonthCollect;
import com.joyintech.tams.svreport.chb.monthcollect.dto.ccyExcharge;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface SvrMonthCollectInterfMapper {
	
	/**
	 * 汇总  总、净销售金额
	 * @return
	 */
	List<ChbMonthCollect> selectAmountTotal(@Param("batchDateStart")String batchDateStart, @Param("batchDateEnd")String batchDateEnd);
    //	查询check表
	List<ChbMonthCollect> selectHolderNumCheck(@Param("batchDateEnd")String batchDateEnd);
    //三合一判断后 根据持有数据，生成持有，投资者数量信息
	List<ChbMonthCollect> selectHolderNumExcheck(@Param("notChangeEquHoldList")List<ChbMonthCollect> notChangeEquHoldList);
	//获取正常的三合一与非三合一数据
	List<ChbMonthCollect> selectNotChangeEquHold(@Param("batchDateEnd")String batchDateEnd);
	//获取所有三合一数据 包含持有为非三合一但身份已变成三合一的数据
	List<ChbMonthCollect> selectThreeInOneList(@Param("batchDateEnd")String batchDateEnd);
	//获取持有为非三合一但身份已变成三合一的数据
	List<ChbMonthCollect> selectHolderNumFromCheck(@Param("batchDateEnd")String batchDateEnd);
    //查询需要删除的reportuuid
	List<String> selectReportuuid (@Param("termid")String termid);
    //删除report明细表
	int deleteDetail(List<String> list);
    //删除report主表
	int deleteReport(@Param("termid")String termid);
	int insertMonthTemp(@Param("newList")List<ChbMonthCollect> newList);
	int deleteAllMonthTemp();
    /**
     * 插入数据
     * @param sub
     */
    int insertIntoDetail(ChbMonthCollect sub);

	int insertIntoReport(@Param("reporttype")String reporttype,@Param("submitDeadline")String submitDeadline,@Param("chbSubmitDeadline")String chbSubmitDeadline,@Param("reportuuid")String reportuuid,@Param("termid")String termid,@Param("reportName")String reportName,@Param("batchDateStart")String batchDateStart,@Param("batchDateEnd")String batchDateEnd);

	/**
	 *检查整个月汇总是否有值
	 * @param batchDateStart
	 * @param batchDateEnd
	 * @return
	 */
	int checkAllMonth(@Param("batchDateStart")String batchDateStart,@Param("batchDateEnd")String batchDateEnd);

	/**
	 *检查月末日汇总
	 * @param batchDateEnd
	 * @return
	 */
	int checkLastDay(@Param("batchDateEnd")String batchDateEnd);

	/**
	 *检查月末日持有
	 * @param batchDateEnd
	 * @return
	 */
	int checkHold(@Param("batchDateEnd")String batchDateEnd);

	/**
	 * 检查是否已报送
	 * @param termid
	 * @return
	 */
	int checkSubmission(@Param("termid")String termid);
//	取得银行月报信息
	List<ChbMonthCollect> selectChbPrdPosition(@Param("batchDateStart")String batchDateStart,@Param("batchDateEnd")String batchDateEnd);
//	分投资者类型获得投资者数量
	List<ChbMonthCollect> typeHolderNum(@Param("batchDateEnd")String batchDateEnd);
//	分地区获得投资者数量
	List<ChbMonthCollect> areaHolderNum(@Param("batchDateEnd")String batchDateEnd);
//	分销售渠道获得投资者数量
	List<ChbMonthCollect> channelHolderNum(@Param("batchDateEnd")String batchDateEnd);
//获取人民币对
	List<ccyExcharge> CNYExchange(@Param("exchangeSource")String exchangeSource,@Param("batchDateStart")String batchDateStart,@Param("batchDateEnd")String batchDateEnd);
//获取美元对
	List<ccyExcharge> USDExchange(@Param("exchangeSource")String exchangeSource,@Param("batchDateStart")String batchDateStart,@Param("batchDateEnd")String batchDateEnd);
	//	获取美元转人民币汇率
	ccyExcharge USDExchangeCNY(@Param("exchangeSource")String exchangeSource,@Param("batchDateStart")String batchDateStart,@Param("batchDateEnd")String batchDateEnd);
	//获取最新净值
	List<PrdPeriodyieldForMonthCollect> getPrdPeriodyield(@Param("batchDateStart")String batchDateStart, @Param("batchDateEnd")String batchDateEnd);
}
