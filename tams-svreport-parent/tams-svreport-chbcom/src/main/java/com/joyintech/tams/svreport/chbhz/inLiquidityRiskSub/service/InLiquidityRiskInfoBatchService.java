package com.joyintech.tams.svreport.chbhz.inLiquidityRiskSub.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.LiquidityRiskSub;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.svreport.chbhz.inLiquidityRiskSub.dao.InliquidityRiskInfoSvrMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能:流动性风险信息登记生成批处理主service
 * 
 * @since
 * @version
 * @作者：wangyaning @创建时间： @修改记录：
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class InLiquidityRiskInfoBatchService extends BaseService {

	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Value("${application.reportInsertCount}")
	public int reportInsertCount;
	/**
	 * 中债基础数据mapper
	 */
	@Autowired
	private BaseMapper baseMapper;

	@Autowired
	private InliquidityRiskInfoSvrMapper inliquidityRiskInfoSvrMapper;

	@Autowired
	private SqlSessionTemplate template;

	/**
	 * @param
	 * @see
	 * @since 1.0
	 */
	public void generateDeclareInfoData(Date batchDate, List<String> prodIdList) {
		String itemId = null;
		Date batchDateStart = new Date();
		Date batchDateEnd = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
		logger.debug("处理跑批日期--------开始---------");
         //先判断是否为月末
		if(isLastDayOfMonth(batchDate)){
			//是月末
			Calendar cale = Calendar.getInstance();
			cale.setTime(batchDate);
            //获取月初
			cale.add(Calendar.MONTH, 0);
			cale.set(Calendar.DAY_OF_MONTH, 1);
			batchDateStart = cale.getTime();
			//获取月末
			cale.add(Calendar.MONTH, 1);
			cale.set(Calendar.DAY_OF_MONTH, 0);
			batchDateEnd = cale.getTime();
			//获取期数
			itemId = formatter.format(cale.getTime());
		}else{
			//不是月末
			Calendar cale = Calendar.getInstance();
			cale.setTime(batchDate);
			// 获取上月的第一天
			cale.add(Calendar.MONTH, -1);
			cale.set(Calendar.DAY_OF_MONTH, 1);
			batchDateStart = cale.getTime();
			// 获取上月的最后一天
			cale.add(Calendar.MONTH, 1);
			cale.set(Calendar.DAY_OF_MONTH, 0);
			batchDateEnd = cale.getTime();
			//获取期数
			itemId = formatter.format(cale.getTime());
		}
		logger.debug("处理跑批日期--------结束---------");

		//判断跑批的日期是否已经进行登记
		if(StringUtil.isNotEmpty(itemId)) {
			if (inliquidityRiskInfoSvrMapper.checkSubmission(itemId) != 0) {
				logger.info("已报送的月报，不再生成报送数据！");
				return;
			}
		}

		logger.debug("流动性风险信息获取登记信息处理开始------" + df.format(new Date()));
		List<LiquidityRiskSub> liquidityRiskForSubList = this.getList(itemId,batchDateStart,batchDateEnd);
		logger.debug("流动性风险信息获取登记信息处理结束------" + df.format(new Date()));

		if(CollectionUtils.isEmpty(liquidityRiskForSubList)){
		   logger.debug("未查询到生成登记数据");
           return;
		}

		logger.debug("流动性风险信息登记数据插入--------开始---------");
		dbData(itemId,liquidityRiskForSubList,batchDateStart);
		logger.debug("流动性风险信息登记数据插入--------结束---------");
	}
	/**
	 * 检索并处理数据
	 * @param itemId
	 * @param batchDateStart
	 * @param batchDateEnd
	 * @return
	 */
	public List<LiquidityRiskSub> getList(String itemId, Date batchDateStart , Date batchDateEnd) {
		if(StringUtil.isEmpty(itemId)){
			return null;
		}
		//获取需要登记的流动性风险信息
		List<LiquidityRiskSub> liquidityRiskForSubList = inliquidityRiskInfoSvrMapper.genLiquidityRiskForSubList(DateUtil.format(batchDateStart),DateUtil.format(batchDateEnd));
		//塞入期数相关信息
		if(CollectionUtils.isNotEmpty(liquidityRiskForSubList)){
			for(LiquidityRiskSub sub:liquidityRiskForSubList){
				sub.setItemId(itemId);
				sub.setOrgCode(CommonUtil.selectBankCode());
			}
		}
		return liquidityRiskForSubList;
	}
	/**
	 * 先删后插
	 */
	private void dbData(String itemId,List<LiquidityRiskSub> liquidityRiskForSubList,Date BatchDayStar){
		logger.debug("流动性风险信息登记数据删除--------开始---------");
		inliquidityRiskInfoSvrMapper.deleteLiquidityRiskSubDetail(itemId);
		inliquidityRiskInfoSvrMapper.deleteLiquidityRiskSub(itemId);
		logger.debug("流动性风险信息登记数据删除--------结束---------");
		//插入主表
		LiquidityRiskSub liquidityRiskSub = new LiquidityRiskSub();
		liquidityRiskSub.setItemId(itemId);
		String month = itemId.substring(5);
		String year = itemId.substring(0,4);
		liquidityRiskSub.setItemName("理财产品流动性风险应对情况月度统计报表_" + year + "年" + month + "月");
		liquidityRiskSub.setOrgCode(CommonUtil.selectBankCode());
		liquidityRiskSub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
		liquidityRiskSub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
		liquidityRiskSub.setStatusDate(new Date());
		String registSeqno = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		liquidityRiskSub.setRegistSeqno(registSeqno);

		//获取下个月的第一天
		Date nextMonFirDay = getFirstDayOfNextMonth(DateUtil.format(BatchDayStar));
		//中债报送截止日
		Date chbSubmitDeadline = new Date();
		Date submitDeadline = new Date();
		List<Date> dateList = null;
		Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
		dateList = ChbDeadlineUtil.getChbEndDate(
				DictCodeListExt2.报表名称_理财产品流动性风险应对情况月度统计表.getValue(), "01", dateMap, nextMonFirDay);
		if (!org.springframework.util.CollectionUtils.isEmpty(dateList)) {
			//获取中债截止日
			chbSubmitDeadline = dateList.get(0);
			submitDeadline = dateList.get(1);
		}
		liquidityRiskSub.setChbSubmitDeadline(chbSubmitDeadline);
		liquidityRiskSub.setSubmitDeadline(submitDeadline);
		//插入主表
		List<LiquidityRiskSub> liquidityRiskSubzhubiao = new ArrayList<>();
		liquidityRiskSubzhubiao.add(liquidityRiskSub);
		inliquidityRiskInfoSvrMapper.insertLiquidityRiskSub(liquidityRiskSubzhubiao);
		//插入明细表
		this.insertLiquidityRiskSubDeatil(liquidityRiskForSubList);
	}

	/**
	 * 插入明细表
	 * @param dataList
	 */
	private void insertLiquidityRiskSubDeatil(List<LiquidityRiskSub> dataList){
		if (CollectionUtils.isNotEmpty(dataList)) {
			int count = 0;
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			try {
				InliquidityRiskInfoSvrMapper batchMapper = session.getMapper(InliquidityRiskInfoSvrMapper.class);
				List<LiquidityRiskSub> dataListTemp = new ArrayList<>();
				if (dataList.size() >= 500 ){
					for (LiquidityRiskSub entity : dataList) {
						dataListTemp.add(entity);
						if (dataListTemp.size() == 500) {
							batchMapper.insertLiquidityRiskSubDetail(dataListTemp);
							dataListTemp.clear();
						}
						count++;
						if (count % 10000 == 0) {
							session.commit();
							// 清理缓存，防止溢出
							session.clearCache();
						}
					}
					// 剩下的数据
					if (dataListTemp.size() > 0){
						// 提交前面的插入
						session.commit();
						batchMapper.insertLiquidityRiskSubDetail(dataListTemp);
						dataListTemp.clear();
					}
				} else {
					// 数据不足500条
					dataListTemp.addAll(dataList);
					batchMapper.insertLiquidityRiskSubDetail(dataListTemp);
				}
				session.commit();
				// 清理缓存，防止溢出
				session.clearCache();
			} catch (Exception e) {
				session.rollback();
				logger.error(e.getMessage(), e);
			} finally {
				session.close();
			}
		}
	}

	/**
	 * 判断日期是否为月末
	 * @param date
	 * @return
	 */
	public static Boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 获取指定日期下个月的第一天
	 * @param dateStr
	 * @return
	 */
	public static Date getFirstDayOfNextMonth(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH,1);
			calendar.add(Calendar.MONTH, 1);
			return calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}