package com.joyintech.tams.svreport.interf.chb.bottomassetposition.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.framework.base.intf.DataDictItem;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.BaseInterfService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.base.dao.ChbAssetsBasisDataMapper;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.interf.common.constant.InterfConstant;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.report.interf.common.constant.InterfMappingConstants;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.assetinfo.service.ChbAssetInfoInterf;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ConnectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.chb.constant.ChbInterfConfig;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.interf.chb.assetinfo.dao.ChbAssetInfoInterfMapper;
import com.joyintech.tams.svreport.interf.chb.bottomassetposition.dao.ChbBotAstPosInterfMapper;
import com.joyintech.tams.svreport.interf.chb.conf.DataSourceType;
import com.joyintech.tools.collectors.JoyinCollectors;


/**
 * 底层资产持仓表接口
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbBotAstPosInterf extends BaseInterfService {
	@Autowired
	private ChbBotAstPosInterfMapper chbBotAstPosInterfMapper;
	@Autowired
	private ChbAssetInfoInterfMapper chbAssetInfoInterfMapper;
	@Autowired
	private SqlSessionTemplate template;
	@Autowired
	private BatchInsertService batchInsertService;
	//实收资本科目号
	private static String SUB_CODE_601 = "601";
	//资产净值科目号
	private static String SUB_CODE_701 = "701";
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    public List<Date> generatePtlValutionData(Date batchTime) {
		logger.info("底层资产持仓信息数据接口导入批处理开始");

		long startTime=System.currentTimeMillis();

		// 查询数据
		List<ChbBotAstPosition> listChbBotAstPosition = getBusEntity(null,batchTime, null, "D");
		// 入库
		if(!StringUtil.isEmptyList(listChbBotAstPosition)){
			insertData2(listChbBotAstPosition);
		}
		logger.info("底层资产持仓信息数据接口导入批处理结束。用时："+(System.currentTimeMillis()-startTime)/1000+"s");
		return new ArrayList<Date>();
	}


	@Override
	public List<ChbBotAstPosition> getBusEntity(List<String> prodIdlist, Date dateNew, String institutionCode, String sourceNew) {
		String source = CacheUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE);
		//批量插入mapper
		//通道投资资产管理产品是否穿透报送
		String isChlBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
		String isCalLoadRate =  CacheUtil.getSysParam("IS_CAL_LOADRATE");
		ChbBotAstPosInterf chbBotAstPosInterf2 =  SpringContextUtil.getBean(ChbBotAstPosInterf.class);
		Date lastUpdatetime = ChbInterfUtil.queryUpdateTime(ChbInterfConfig.底层持仓信息接口.getInterId());
		List<Date> isUpdated = chbBotAstPosInterf2.isUpdated(lastUpdatetime);
		List<Date> isUpdatedChl = new ArrayList<Date>();
		List<ChbBotAstPosition> listChbBotAstPosition = new ArrayList<ChbBotAstPosition>();
		if("02".equals(isChlBtm)){
			isUpdatedChl = chbBotAstPosInterf2.isUpdatedChl(lastUpdatetime);
		}
		for(Date date :isUpdated){
			//查询信息
			//List<ChbBotAstPosition> listChbBotAstPosition = chbBotAstPosInterf2.getDataFromInterf(date);
			listChbBotAstPosition.addAll(getBotDataFromRsk(date));//估值表升级
//	        	if("02".equals(isChlBtm)){
//					//  用到了PTL_CASH_POSITION和bok_balance和BOK_BOOKSET_RE
//	        		List<ChbBotAstPosition> listBotAstPosition = chbBotAstPosInterf2.getChlBtmDataFromInterf(date, source);
//	        		List<ChbBotAstPosition> listBotAstPositionSp = chbBotAstPosInterf2.getChlBtmDataFromInterfSp(date, source,isCalLoadRate);
//	        		Map<String,ChbBotAstPosition> spAssetsBasisDataMap=new HashMap<String,ChbBotAstPosition>(listBotAstPosition.size());
//	        		spAssetsBasisDataMap=listBotAstPosition.stream().collect(
//    		                JoyinCollectors.toMap(
//    		                        x -> x.getPlanAssetId() + GlobalConstant.UNDER_LINE + x.getAssetCode(), x -> x));
//	        		for(ChbBotAstPosition single:listBotAstPositionSp){
//	        			String key=single.getPlanAssetId() + GlobalConstant.UNDER_LINE + single.getAssetCode();
//	        			spAssetsBasisDataMap.put(key, single);
//	        		}
//
//	        		if(!spAssetsBasisDataMap.isEmpty()){
//	        			for(String key:spAssetsBasisDataMap.keySet()){
//	        				listChbBotAstPosition.add(spAssetsBasisDataMap.get(key));
//	        			}
//	        		}
//	        	}
//	        	List<String> assetTypeList = new ArrayList<String>();
//	    		assetTypeList.add(DictCodeListExt2.资产_负债类别_资产管理产品.getValue());
//	    		assetTypeList.add(DictCodeListExt2.资产_负债类别_私募基金.getValue());
//	    		assetTypeList.add(DictCodeListExt2.资产_负债类别_委外投资_协议方式.getValue());
//	    		// 生成日期为空防止要素（维护）变动更新日期
//	    		List<String> assetPlanLList = chbAssetInfoInterfMapper.selectAssetCodeByAssetType(assetTypeList, null);
//	    		Map<String, BigDecimal> rateMap = new HashMap<>();
//	        	for(ChbBotAstPosition position : listChbBotAstPosition){
//	        		position.setPosId(IDGenerator.generate(36));
//	        		position.setIssuePartyId(CommonUtil.selectBankCode());
//	        		position.setCreateUser("system");
//	        		position.setUpdateUser("system");
//	        		position.setStatus("E");
//	        		position.setIschl("02");
//	        		position.setDatasource("16");
//	        		//人民币发行金额 = 总发行数量*币种汇率
//	    			/*if (position.getTotalNum() != null){
//	    				position.setIssueAmt(ChbCcyConvertUtil.changeCnyMoney(position.getCcy(), position.getTotalNum(), position.getPosDate(), rateMap));
//	    			}*/
//	        		if(assetPlanLList.contains(position.getAssetCode())){
//	        			position.setIsfinaplan("01");
//	        		}else{
//	        			position.setIsfinaplan("02");
//	        		}
//	            	if(position.getUpdateTime().compareTo(lastUpdatetime)>0)
//	            		lastUpdatetime =position.getUpdateTime();
//	        	}
//	        	//先删后插
//	        	String dateStr = DateUtil.format(date);
//	        	logger.debug("删除"+dateStr+"日的底层资产持仓信息，开始");
//	        	chbBotAstPosInterfMapper.deleteByPosdate(date);
//	        	logger.debug("删除"+dateStr+"日的底层资产持仓信息，结束");
//	        	logger.debug("插入"+dateStr+"日的底层资产持仓信息，开始");
//	        	for(ChbBotAstPosition position : listChbBotAstPosition){
//	        		batchMapper.insert(position);
//	        		count++;
//	        		if(count%1000==0){
//	        			logger.debug("已插入"+count+"条"+DateUtil.getCurrentDateTime());
//	            		session.commit();
//	            		session.clearCache();
//	        		}
//
//	        	}
//	        	logger.debug("插入"+dateStr+"日的底层资产持仓信息，结束");
		}

		for (Date date : isUpdatedChl) {
			List<ChbBotAstPosition> listBotAstPosition = chbBotAstPosInterf2.getChlBtmDataFromInterf(date, source);
			List<ChbBotAstPosition> listBotAstPositionSp = chbBotAstPosInterf2.getChlBtmDataFromInterfSp(date, source,isCalLoadRate);
			Map<String,ChbBotAstPosition> spAssetsBasisDataMap=new HashMap<String,ChbBotAstPosition>(listBotAstPosition.size());
			spAssetsBasisDataMap=listBotAstPosition.stream().collect(
					JoyinCollectors.toMap(
							x -> x.getPlanAssetId() + GlobalConstant.UNDER_LINE + x.getAssetCode(), x -> x));
			for(ChbBotAstPosition single:listBotAstPositionSp){
				String key=single.getPlanAssetId() + GlobalConstant.UNDER_LINE + single.getAssetCode();
				spAssetsBasisDataMap.put(key, single);
			}
			if(!spAssetsBasisDataMap.isEmpty()){
				for(String key:spAssetsBasisDataMap.keySet()){
					listChbBotAstPosition.add(spAssetsBasisDataMap.get(key));
				}
			}
		}
		// 生成日期为空防止要素（维护）变动更新日期
		List<String> assetPlanLList = chbAssetInfoInterfMapper.selectAssetCodeByAssetTypePtl();
		for (ChbBotAstPosition position : listChbBotAstPosition) {
			position.setPosId(IDGenerator.generate(36));
			position.setIssuePartyId(CommonUtil.selectBankCode());
			position.setCreateUser("system");
			position.setUpdateUser("system");
			position.setStatus("E");
			//数据来源
			if("D".equals(sourceNew)){
				position.setDatasource("16");
			}
			if (assetPlanLList.contains(position.getAssetCode())) {
				position.setIsfinaplan("01");
			} else {
				position.setIsfinaplan("02");
			}
			if (null != position.getUpdateTime() && position.getUpdateTime().compareTo(lastUpdatetime) > 0)
				lastUpdatetime = position.getUpdateTime();
		}
		if(lastUpdatetime!=null){
			ChbInterfUtil.updateLastupdatetime(ChbInterfConfig.底层持仓信息接口.getInterId(), lastUpdatetime);;
		}
		return listChbBotAstPosition;
	}

	@Override
	public Class getEntityClass() {
		return ChbBotAstPosition.class;
	}

	@Override
	public String getFileName() {
		return InterfDefine.资管计划底层资产持仓接口数据.getCode();
	}



    @Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<Date> isUpdatedChl(Date lastUpdatetime) {
    	return chbBotAstPosInterfMapper.isUpdatedChl(lastUpdatetime);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbBotAstPosition> getDataFromInterf(Date batchTime){
		List<ChbBotAstPosition> rs = new ArrayList<ChbBotAstPosition>();
		template.select("com.joyintech.tams.svreport.interf.chb.bottomassetposition.dao.ChbBotAstPosInterfMapper.selectByPosdate",batchTime,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbBotAstPosition pos = (ChbBotAstPosition) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
    
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<Date> isUpdated(Date batchTime){
    	return chbBotAstPosInterfMapper.isUpdated(batchTime);
    }
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbBotAstPosition> getChlBtmDataFromInterf(Date batchTime,String source){
		List<ChbBotAstPosition> rs = new ArrayList<ChbBotAstPosition>();
		Map<String,String> params = new HashMap<String,String>();
		params.put("batchTime", DateUtil.format(batchTime));
		params.put("status", source);
		template.select("com.joyintech.tams.svreport.interf.chb.bottomassetposition.dao.ChbBotAstPosInterfMapper.selectPtlChlBtmByPosdate",params,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbBotAstPosition pos = (ChbBotAstPosition) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbBotAstPosition> getChlBtmDataFromInterfSp(Date batchTime,String source,String isCalLoadRate){
		List<ChbBotAstPosition> rs = new ArrayList<ChbBotAstPosition>();
		Map<String,String> params = new HashMap<String,String>();
		params.put("batchTime", DateUtil.format(batchTime));
		params.put("status", source);
		params.put("isCalLoadRate", isCalLoadRate);
		template.select("com.joyintech.tams.svreport.interf.chb.bottomassetposition.dao.ChbBotAstPosInterfMapper.selectPtlChlBtmByPosdateSp",params,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbBotAstPosition pos = (ChbBotAstPosition) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
	/**
	 * 根据跑批日查找估值表导入的底层资产
	 * @param date
	 * @return
	 */
	private List<ChbBotAstPosition> getBotDataFromRsk(Date date) {
		logger.info("-----------------获取底层持仓数据开始---------------");
		List<ChbBotAstPosition> allBotList = new ArrayList<>();
		//1、查询未投资头寸
		Map<String,List<ChbBotAstPosition>> botUnInvestBotMap = getUnInvestBotListFromRsk(date);
		//2、查询资管计划的汇总科目(包含资产价值以及实收资本)
		Map<String,List<ChbBotAstPosition>> botTotalNumMap = getBotTotalNumFromRsk(date);
		//3、先查询出除回购之外的底层资产(回购特殊处理,需要进行占比拆分)
		List<ChbBotAstPosition> otherBotList = getOtherBotDataFromRsk(date,botUnInvestBotMap,botTotalNumMap);
		//4、查询回购底层资产,并进行拆分操作
		List<ChbBotAstPosition> repBotList = getRepBotDataFromRsk(date,botUnInvestBotMap,botTotalNumMap);
		//5、虚拟一笔剩余现金底层资产REMAININGCASH(为G06而用)
		List<ChbBotAstPosition> botCashList = getCashBotDataFromRsk(date,botUnInvestBotMap,botTotalNumMap);
		allBotList.addAll(otherBotList);
		allBotList.addAll(repBotList);
		allBotList.addAll(botCashList);
		logger.info("-----------------获取底层持仓数据结束---------------");
		return allBotList;
	}
	/**
	 * 获取跑批日所有资管计划的未投资头寸
	 * @param date
	 * @return
	 */
	private Map<String,List<ChbBotAstPosition>> getUnInvestBotListFromRsk(Date date) {
		//获取所有资管计划的未投资头寸
		List<ChbBotAstPosition> unInvestBotList = chbBotAstPosInterfMapper.getUnInvestBotListFromRsk(date);
		return unInvestBotList.stream().collect(JoyinCollectors.groupingBy(ChbBotAstPosition::getPlanAssetId));
	}
	/**
	 * 获取跑批日所有资管计划的资产价值以及实收资本
	 * @param date
	 * @return
	 */
	private Map<String, List<ChbBotAstPosition>> getBotTotalNumFromRsk(Date date) {
		//获取所有资管计划的资产价值以及实收资本开始
		List<ChbBotAstPosition> botTotalNumList = chbBotAstPosInterfMapper.getBotTotalNumFromRsk(date);
		return botTotalNumList.stream().collect(JoyinCollectors.groupingBy(ChbBotAstPosition::getPlanAssetIdAndSubjectCd));
	}
	/**
	 * 获取除回购外的底层资产
	 * @param date
	 * @return
	 */
	private List<ChbBotAstPosition> getOtherBotDataFromRsk(Date date,
														   Map<String,List<ChbBotAstPosition>> botUnInvestBotMap,
														   Map<String,List<ChbBotAstPosition>> botTotalNumMap) {
		//获取除回购外的底层资产
		List<ChbBotAstPosition> list = chbBotAstPosInterfMapper.getOtherBotDataFromRsk(date);
		list.forEach(pos -> {
			setBotAssetOtherAmt(pos,botUnInvestBotMap,botTotalNumMap,true);
		});
		return list;
	}
	/**
	 * 底层资产的资产净值、实收资本、未投资头寸的赋值
	 * @param pos 底层资产
	 * @param botUnInvestBotMap 未投资头寸
	 * @param botTotalNumMap 汇总科目数值
	 * @param isStats  是否统计未投资头寸  只有剩余现金时不统计(已经在sql中处理该字段)，其他场合统计
	 */
	private void setBotAssetOtherAmt(ChbBotAstPosition pos,
									 Map<String,List<ChbBotAstPosition>> botUnInvestBotMap,
									 Map<String,List<ChbBotAstPosition>> botTotalNumMap,
									 boolean isStats) {
		//未投资头寸
		if(isStats) {
			if (null != botUnInvestBotMap.get(pos.getPlanAssetId())) {
				List<ChbBotAstPosition> tmp = botUnInvestBotMap.get(pos.getPlanAssetId());
				pos.setUninvestCash(tmp.get(0).getPosAmt());
			}
		}
		//总发行数量 = 实收资本
		if(null != botTotalNumMap.get(pos.getPlanAssetId() + SUB_CODE_601)){
			List<ChbBotAstPosition> tmp = botTotalNumMap.get(pos.getPlanAssetId() + SUB_CODE_601);
			pos.setTotalNum(tmp.get(0).getPosAmt());
		}
		//人民币发行金额 = 资产净值
		if(null != botTotalNumMap.get(pos.getPlanAssetId() + SUB_CODE_701)){
			List<ChbBotAstPosition> tmp = botTotalNumMap.get(pos.getPlanAssetId() + SUB_CODE_701);
			pos.setIssueAmt(tmp.get(0).getPosAmt());
		}
	}
	/**
	 * 查询回购底层资产
	 * @param date
	 * @return
	 */
	private List<ChbBotAstPosition> getRepBotDataFromRsk(Date date,
														 Map<String,List<ChbBotAstPosition>> botUnInvestBotMap,
														 Map<String,List<ChbBotAstPosition>> botTotalNumMap) {

		//获取底层回购资产
		List<ChbBotAstPosition> botRepList = chbBotAstPosInterfMapper.getRepBotDataFromRsk(date);
		//根据上述的回购资产查找符合的底层回购要素开始占比拆分
		//Step1、先将跑批日所有未到期的回购资产检索出来
		// 这块回购是信批录入
		List<ChbAssetInfo> repInfoList = chbBotAstPosInterfMapper.selectRepAstByBatchDate(date);
		//Step2、计算利息的占比(根据底层资产的二级科目号映射资产二级类型筛选出同类资产)
		Map<String,BigDecimal> intAmtMap = calBotAstIntAmt(botRepList, repInfoList);
		//Step3、计算成本的占比(根据底层资产的二级科目号映射资产二级类型以及流通场所筛选出同类资产)
		List<ChbBotAstPosition> resultList = calBotAstCostAmt(botRepList, repInfoList,intAmtMap,botUnInvestBotMap,botTotalNumMap);

		return resultList;
	}

	/**
	 * 根据底层未投资任何资产来虚拟一笔剩余现金底层资产
	 * @param date
	 * @return
	 */
	private List<ChbBotAstPosition> getCashBotDataFromRsk(Date date,
														  Map<String,List<ChbBotAstPosition>> botUnInvestBotMap,
														  Map<String,List<ChbBotAstPosition>> botTotalNumMap) {

		//剩余现金
		String monthReport=SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_CHBMONTH_REPORT);
		List<ChbBotAstPosition> cashList = chbBotAstPosInterfMapper.getCashBotDataFromRsk(date,monthReport);
		//排除当前资管计划没有底层资产只有剩余现金
		List<ChbBotAstPosition> noBotAstCashList = chbBotAstPosInterfMapper.getNoBotAstCashDataFromRsk(date);
		Map<String,List<ChbBotAstPosition>> map = noBotAstCashList.stream().collect(JoyinCollectors.groupingBy(ChbBotAstPosition::getPlanAssetId));
		cashList.forEach(pos ->{
			if(null != map.get(pos.getPlanAssetId()) && map.get(pos.getPlanAssetId()).size() > 0){
				//说明此笔资管计划不止有剩余现金，还包含了其他底层资产
				pos.setCashFlag(DictCodeList.是否_中债_否.getValue());
			}else{
				pos.setCashFlag(DictCodeList.是否_中债_是.getValue());
			}
			setBotAssetOtherAmt(pos,botUnInvestBotMap,botTotalNumMap,false);
		});
		return cashList;
	}
	/**
	 *
	 * @param botRepList
	 * @param repInfoList
	 */
	private Map<String,BigDecimal> calBotAstIntAmt(List<ChbBotAstPosition> botRepList, List<ChbAssetInfo> repInfoList) {
		//计算底层回购要素利息数据
		String assets2 = "1108";//买入返售
		Map<String,BigDecimal> resultMap = new HashMap<>();
		for(ChbBotAstPosition bot : botRepList){
			//应收/应付利息
			String assetCode=bot.getAssetCode();
			if("120410".equals(assetCode) || "223101".equals(assetCode)) {
				if ("223101".equals(assetCode)) {
					//卖出回购
					assets2 = "1902";
				}else{
					//买入返售
					assets2 = "1108";
				}
				logger.info("当前资管计划代码:"+ bot.getPlanAssetId() + ",中债二级分类:" + assets2);
				//根据当前bot的资管计划找到对应的回购资产数据
				String finalAssets = assets2;
				List<ChbAssetInfo> intAmtList = repInfoList.stream().filter(repines -> StringUtil.equals(repines.getCheckCode(), bot.getPlanAssetId()) && StringUtil.equals(repines.getAssetType2(), finalAssets)).collect(Collectors.toList());
				if(StringUtil.isEmptyList(intAmtList)){
					logger.info("根据当前资管计划:"+ bot.getPlanAssetId() +"以及科目号："+assetCode +",未找到对应的底层回购要素信息,请检查是否维护底层回购信息!");
					continue;
				}
				//按照金额进行升序排序
				intAmtList = intAmtList.stream().sorted(Comparator.comparing(ChbAssetInfo::getTargetAmt)).collect(Collectors.toList());
				//同一类回购标的汇总金额
				BigDecimal totalTargetAmt = BigDecimal.ZERO;
				for(ChbAssetInfo info : intAmtList){
					totalTargetAmt = totalTargetAmt.add(info.getTargetAmt());
				}
				//最后一笔之前的累计值
				BigDecimal tmpAmt = BigDecimal.ZERO;
				//当前循环光标
				int index = 0;
				//"计算利息->回购占比公式 = 底层回购的市值 * (当前回购的标的金额 / 回购标的总金额)"
				for(ChbAssetInfo info : intAmtList){
					//资管计划代码+底层资产代码作为唯一key
					String key = bot.getPlanAssetId() + GlobalConstant.UNDER_LINE + info.getAssetCode();
					if(index == intAmtList.size() - 1){
						//最后一笔倒减
						resultMap.put(key,bot.getPosAmt().subtract(tmpAmt));
						logger.info("计算利息->最后一笔回购金额倒减,回购底层利息总市值="+totalTargetAmt+",倒减金额="+tmpAmt,"最后一笔利息="+totalTargetAmt.subtract(tmpAmt));
					}else{
						//当前回购市值 = 底层回购的市值 * (当前回购的标的金额 / 回购标的总金额) 保留两位小数
						BigDecimal curAmt = bot.getPosAmt().multiply(info.getTargetAmt().divide(totalTargetAmt,14, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
						tmpAmt = tmpAmt.add(curAmt);
						resultMap.put(key,curAmt);
						logger.info("计算利息->第"+ (index + 1) +"笔回购拆分各项值,底层回购的市值="+bot.getPosAmt()+",当前回购的标的金额="+info.getTargetAmt()+",回购标的总金额"+totalTargetAmt);
					}
					index ++ ;
				}
			}
		}
		return resultMap;
	}
	/**
	 * 根据底层回购查找回购要素信息，并进行成本占比拆分,最后一笔倒减
	 * @param botRepList
	 * @param repInfoList
	 * @param intAmtMap
	 * @return
	 */
	private List<ChbBotAstPosition> calBotAstCostAmt(List<ChbBotAstPosition> botRepList,
													 List<ChbAssetInfo> repInfoList,
													 Map<String, BigDecimal> intAmtMap,
													 Map<String,List<ChbBotAstPosition>> botUnInvestBotMap,
													 Map<String,List<ChbBotAstPosition>> botTotalNumMap
	) {
		//计算底层回购要素成本市值数据
		List<ChbBotAstPosition> resultList = new ArrayList<>();
		for(ChbBotAstPosition bot : botRepList){
			//应收/应付利息
			String assetCode=bot.getAssetCode();
			if("120410".equals(assetCode) || "223101".equals(assetCode)) {
				continue;
			}
			String assets2 = "1108";//买入返售
			String tradeMarket = null;
			if("220201".equals(assetCode) || "220202".equals(assetCode) || "220203".equals(assetCode)){
				assets2 = "1902";
			}else{
				assets2 = "1108";
			}
			tradeMarket = getTradeMark(bot);
			//根据二级分类以及交易场所找到对应的回购要素
			String finalAssets = assets2;
			String finalTradeMkt = tradeMarket;
			List<ChbAssetInfo> costAmtList = repInfoList.stream().filter(repines -> StringUtil.equals(repines.getCheckCode(),bot.getPlanAssetId())
					&& StringUtil.equals(repines.getAssetType2(),finalAssets)
					&& StringUtil.equals(repines.getTradeMarket(),finalTradeMkt)
			).collect(Collectors.toList());
			//按照金额进行升序排序
			costAmtList = costAmtList.stream().sorted(Comparator.comparing(ChbAssetInfo::getTargetAmt)).collect(Collectors.toList());
			if(StringUtil.isEmptyList(costAmtList)){
				logger.info("根据当前资管计划:"+ bot.getPlanAssetId() +"、科目号:"+assetCode +"以及交易市场:"+tradeMarket+",未找到对应的底层回购要素信息,请检查是否维护底层回购信息!");
				continue;
			}
			//同一类回购标的汇总金额
			BigDecimal totalTargetAmt = BigDecimal.ZERO;
			for (ChbAssetInfo x : costAmtList) {
				totalTargetAmt = totalTargetAmt.add(x.getTargetAmt());
			}
			//最后一笔之前的累计值
			BigDecimal tmpAmt = BigDecimal.ZERO;
			//当前循环光标
			int index = 0;
			//"计算成本->回购占比公式 = 底层回购的市值 * (当前回购的标的金额 / 回购标的总金额)"
			for(ChbAssetInfo info : costAmtList){
				ChbBotAstPosition pos = new ChbBotAstPosition();
				//对比上次最大更新日使用
				pos.setUpdateTime(bot.getUpdateTime());
				pos.setPosDate(bot.getPosDate());
				pos.setPlanAssetId(bot.getPlanAssetId());
				pos.setAssetCode(info.getAssetCode());
				//汇总值封装,其他属性后面统一封装
				setBotAssetOtherAmt(pos,botUnInvestBotMap,botTotalNumMap,true);
				//成本数值
				BigDecimal cost = BigDecimal.ZERO;
				if(index == costAmtList.size() - 1){
					//最后一笔倒减
					cost = bot.getPosAmt().subtract(tmpAmt);
					logger.info("计算成本->最后一笔回购金额倒减,回购底层成本总市值="+ bot.getPosAmt() +",倒减金额="+tmpAmt+",最后一笔成本="+cost);
				}else{
					//当前回购市值 = 底层回购的市值 * (当前回购的标的金额 / 回购标的总金额) 保留两位小数
					cost=ArithmeticUtil.multiply(bot.getPosAmt(),ArithmeticUtil.divide(info.getTargetAmt(),totalTargetAmt,14)).setScale(2, RoundingMode.HALF_UP);
					tmpAmt = tmpAmt.add(cost);
					logger.info("计算成本->第"+ (index + 1) +"笔回购拆分各项值：底层回购的市值="+bot.getPosAmt()+",当前回购的标的金额="+info.getTargetAmt()+",回购标的总金额"+ totalTargetAmt);
				}
				pos.setPosAmt(cost);
				String key = bot.getPlanAssetId() + GlobalConstant.UNDER_LINE + info.getAssetCode();
				if(null != intAmtMap.get(key)){
					//市值 = 成本 + 利息
					pos.setPosAmt(cost.add(intAmtMap.get(key)));
					//利息
					pos.setIntAmt(intAmtMap.get(key));
				}
				//持仓数量
				pos.setPosNum(info.getTargetAmt());
				resultList.add(pos);
				index ++ ;
			}
		}
		return resultList;
	}

	/**
	 * 根据科目好映射交易场所
	 * @param bot
	 * @return
	 */
	private String getTradeMark(ChbBotAstPosition bot) {
		String assetCode=bot.getAssetCode();
		if("120201".equals(assetCode) || "220201".equals(assetCode)){
			//上海证券交易所
			return "03";
		}else if("120202".equals(assetCode) || "220202".equals(assetCode)){
			//银行间债券市场
			return "01";
		}else if("120203".equals(assetCode) || "220203".equals(assetCode)){
			//深圳证券交易所
			return "04";
		}else{
			//nothing to do
		}
		return null;
	}


	private void insertData2(List<ChbBotAstPosition> listChbBotAstPosition) {

		if (listChbBotAstPosition.size()<=5000) {
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);

			//批量插入mapper
			ChbBotAstPosInterfMapper batchMapper =  session.getMapper(ChbBotAstPosInterfMapper.class);

			Map<Date, List<ChbBotAstPosition>> listChbBotAstPositionMap =
					listChbBotAstPosition.stream().collect(JoyinCollectors.groupingBy(chbAssetsBasisData -> chbAssetsBasisData.getPosDate()));
			int count = 0;
			try {
				for (Date date : listChbBotAstPositionMap.keySet()) {

					String dateStr = DateUtil.format(date);

					// 先删后插
					logger.debug("删除" + dateStr + "日的底层资产持仓信息，开始");
					chbBotAstPosInterfMapper.deleteByPosdate(date);
					logger.debug("删除" + dateStr + "日的底层资产持仓信息，结束");
					logger.debug("插入" + dateStr + "日的底层资产持仓信息，开始");

					for (ChbBotAstPosition data : listChbBotAstPositionMap.get(date)) {
						batchMapper.insert(data);
						count++;
						if (count % 1000 == 0) {
							logger.debug("已插入" + count + "条" + DateUtil.getCurrentDateTime());
							session.commit();
							session.clearCache();
						}
					}
					logger.info("插入" + dateStr + "日的资产基础指标数据，结束");
				}
				session.commit();
				session.clearCache();
			}finally {
				session.close();
			}
		} else {
			//sqlloader插入
			ImpTable table = new ImpTable("CHB_BOT_AST_POS");
			table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
			// 临时文件存放目录
			String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

			batchInsertService.exeBatchldr(tmpPath, listChbBotAstPosition, table);
		}

	}

}
