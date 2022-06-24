package com.joyintech.tams.svreport.interf.chb.assetbasis.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.BaseInterfService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.base.dao.ChbAssetsBasisDataMapper;
import com.joyintech.tams.common.base.entity.ChbAssetsBasisData;
import com.joyintech.tams.common.base.entity.ChbAstValue;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.constant.ChbInterfConfig;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.assetbasis.dao.ChbAssetBasisInterfMapper;
import com.joyintech.tams.svreport.interf.chb.assetbasis.dao.ChbAstValInterfMapper;
import com.joyintech.tams.svreport.interf.chb.conf.DataSourceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * 资产基础指标数据接口
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrAssetBasisInterf extends BaseInterfService {
	@Autowired
	private ChbAssetBasisInterfMapper chbAssetBasisInterfMapper;
	@Autowired
	private ChbAstValInterfMapper chbAstValInterfMapper;
	
	@Autowired
	private SqlSessionTemplate template;

	@Autowired
	private BatchInsertService batchInsertService;
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	public List<Date> generateData(Date batchTime) {
		logger.info("资产基础指标数据接口导入批处理开始");

		long startTime=System.currentTimeMillis();

		// 查询数据
		List<ChbAssetsBasisData> listChbAssetsBasisData = getBusEntity(null,batchTime, null, "D");
		// 入库
		if(!StringUtil.isEmptyList(listChbAssetsBasisData)){
			insertData2(listChbAssetsBasisData);
		}
		logger.info("资产基础指标数据接口导入批处理结束。用时："+(System.currentTimeMillis()-startTime)/1000+"s");
		return new ArrayList<Date>();
	}

	@Override
	public List<ChbAssetsBasisData> getBusEntity(List<String> prodIdlist, Date batchDate, String institutionCode, String source) {
		long startTime=System.currentTimeMillis();
		//通道投资资产管理产品是否穿透报送
		String isChlBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
		String isChlBtmPbc = SvrDataUtil.getSysParam(SVReportGlobalConstant.PBC_IS_CHL_BTM);
//    	String isCalLoadRate =  CacheUtil.getSysParam("IS_CAL_LOADRATE");
		SvrAssetBasisInterf svrAssetBasisInterf2 =  SpringContextUtil.getBean(SvrAssetBasisInterf.class);
		Date lastUpdatetime = ChbInterfUtil.queryUpdateTime(ChbInterfConfig.资产基础指标信息接口.getInterId());
		List<Date> isUpdated = svrAssetBasisInterf2.isUpdated(lastUpdatetime);
		String channelAssetsMdateConf = SvrDataUtil.getSysParam(SVReportGlobalConstant.CHANNEL_ASSETS_MDATE_CONF);
		Integer channelAssetsMdates = Integer.parseInt(SvrDataUtil.getSysParam(SVReportGlobalConstant.CHANNEL_ASSETS_MDATES) == null ?
				"0" : SvrDataUtil.getSysParam(SVReportGlobalConstant.CHANNEL_ASSETS_MDATES));
		String imfAssetsMdateConf = SvrDataUtil.getSysParam(SVReportGlobalConstant.IMF_ASSETS_MDATE_CONF);
		Integer imfAssetsMdates = Integer.parseInt(SvrDataUtil.getSysParam(SVReportGlobalConstant.IMF_ASSETS_MDATES) == null ?
				"0" : SvrDataUtil.getSysParam(SVReportGlobalConstant.IMF_ASSETS_MDATES));
		List<String> imfAssetIdList =  chbAssetBasisInterfMapper.queryAllImfAssetIds();

		//债券资产资产到期日配置参数
		String seAssetsMdateConf = SvrDataUtil.getSysParam(SVReportGlobalConstant.SEC_ASSETS_MDATE_CONF);
		//查询所有有效的行权债代码
		List<String> secXQAssetIdList =  chbAssetBasisInterfMapper.queryAllSecXQAssetIds();
		List<ChbAssetsBasisData> listChbAssetsBasisDataAll = new ArrayList<ChbAssetsBasisData>();


			int count = 0;
			for(Date date :isUpdated){
				//查询信息
				List<ChbAssetsBasisData> listChbAssetsBasisData = new ArrayList<ChbAssetsBasisData>();
//    			List<ChbAssetsBasisData> listChbAssetsBasisDataNotBtm = new ArrayList<ChbAssetsBasisData>();
//    			List<ChbAssetsBasisData> listChbAssetsBasisDataNotBtmSp = new ArrayList<ChbAssetsBasisData>();
//    			Map<String,ChbAssetsBasisData> spAssetsBasisDataMap=new HashMap<String,ChbAssetsBasisData>();
//    			Map<String,ChbAssetsBasisData> listChbAssetsBasisDataNotBtmMap=new HashMap<String,ChbAssetsBasisData>();
				if("01".equals(isChlBtm)||"01".equals(isChlBtmPbc)){
					listChbAssetsBasisData = svrAssetBasisInterf2.getDataFromInterf(DateUtil.format(date));
				}
//    			if("02".equals(isChlBtm)||"02".equals(isChlBtmPbc)){
//    				listChbAssetsBasisDataNotBtm = svrAssetBasisInterf2.getChlBtmDataFromInterf(DateUtil.format(date));
//    				listChbAssetsBasisDataNotBtmMap=listChbAssetsBasisDataNotBtm.stream().collect(
//    		                JoyinCollectors.toMap(
//    		                        x -> x.getProdId() + GlobalConstant.UNDER_LINE + x.getAssetId(), x -> x));
//    				listChbAssetsBasisDataNotBtmSp=svrAssetBasisInterf2.getChlBtmDataFromInterfSp(DateUtil.format(date),isCalLoadRate);
//    				spAssetsBasisDataMap=listChbAssetsBasisDataNotBtmSp.stream().collect(
//    		                JoyinCollectors.toMap(
//    		                        x -> x.getProdId() + GlobalConstant.UNDER_LINE + x.getAssetId(), x -> x));
//
//    			}
//				listChbAssetsBasisData = svrAssetBasisInterf2.getDataFromInterf(DateUtil.format(date));
//				List<ChbAssetsBasisData> listIntadjexpAdd = chbAssetBasisInterfMapper.selectIntadjexpAdd(DateUtil.format(date));

				// 生成日期为空防止要素（维护）变动更新日期
				for(ChbAssetsBasisData data : listChbAssetsBasisData){

//					// 增加利息调整字段
//					listIntadjexpAdd.stream().forEach(item ->{
//						if(data.getAssetId().equals(item.getAssetId()) &&  data.getProdId().equals(item.getProdId())){
//							data.setTdyIntadjexpAdd(item.getTdyIntadjexpAdd());
//						}
//					});

					data.setValId(IDGenerator.generate(36));
					data.setCreateUser("system");
					data.setUpdateUser("system");
					data.setStatus("E");
					//数据来源
					if("D".equals(source)){
						data.setDataSource("16");
					}
					// 【资产基础指标数据接入】时，若通道不穿透报送，在接入通道时，MDATE的取值根据参数判断
					if ("02".equals(isChlBtm) && "02".equals(channelAssetsMdateConf)) {
						Date valueDate = data.getValueDate();
						if (null != valueDate) {
							Date mDate = DateUtil.addDay(valueDate, channelAssetsMdates);
							data.setMdate(mDate);
						}
					}
					// 【资产基础指标数据接入】时，判断若资产是货币基金资产, MDATE的取值根据参数判断
					if ("02".equals(imfAssetsMdateConf) && imfAssetIdList.contains(data.getAssetId())) {
						Date valueDate = data.getValueDate();
						if (null != valueDate) {
							Date mDate = DateUtil.addDay(valueDate, imfAssetsMdates);
							data.setMdate(mDate);
						}
					}

					// 【资产基础指标数据接入】时，判断资产是否行权债, MDATE的取值根据参数判断
					if ("02".equals(seAssetsMdateConf) && secXQAssetIdList.contains(data.getAssetId())) {
						//取最近的行权日，取不到为原来资产的到期日
						String secid = "F01_"+data.getAssetId();
						Date valueDate = data.getValueDate();
						Date exerciseDate =  chbAssetBasisInterfMapper.queryExerciseDate(secid,valueDate);
						if (null != exerciseDate) {
							data.setMdate(exerciseDate);
						}
					}

					//上市流通交易，上市标的修改成发行标的资产代码
					String pulicAssetId = chbAssetBasisInterfMapper.getPulicAssetId(data.getAssetId());
					if(StringUtil.isNotEmpty(pulicAssetId)){
						data.setAssetId(pulicAssetId);
					}
//
					StringBuffer keyVFinal = new StringBuffer();
					keyVFinal.append(data.getProdId()).append(data.getAssetId()).append(data.getValueDate());
					String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
					data.setMd5primarykey(md5Key);
					if(data.getUpdateTime().compareTo(lastUpdatetime)>0)
						lastUpdatetime =data.getUpdateTime();
				}
//	        	Map<String,ChbAssetsBasisData> listChbAssetsBasisDataNotBtmMapResult=new HashMap<String,ChbAssetsBasisData>();
//	        	for(String key:listChbAssetsBasisDataNotBtmMap.keySet()){
//	        		ChbAssetsBasisData data=listChbAssetsBasisDataNotBtmMap.get(key);
////	        		String key=data.getProdId() + GlobalConstant.UNDER_LINE + data.getAssetId();
//	        		data.setValId(IDGenerator.generate(36));
//	        		data.setCreateUser("system");
//	        		data.setUpdateUser("system");
//	        		data.setStatus("E");
//	        		data.setDataSource("16");
//	        		// 【资产基础指标数据接入】时，若通道不穿透报送，在接入通道时，MDATE的取值根据参数判断
//	        		if ("02".equals(isChlBtm) && "02".equals(channelAssetsMdateConf)) {
//	        			Date valueDate = data.getValueDate();
//	        			if (null != valueDate) {
//	        				Date mDate = DateUtil.addDay(valueDate, channelAssetsMdates);
//	        				data.setMdate(mDate);
//	        			}
//	        		}
//	        		// 【资产基础指标数据接入】时，判断若资产是货币基金资产, MDATE的取值根据参数判断
//	        		if ("02".equals(imfAssetsMdateConf) && imfAssetIdList.contains(data.getAssetId())) {
//	        			Date valueDate = data.getValueDate();
//	        			if (null != valueDate) {
//	        				Date mDate = DateUtil.addDay(valueDate, imfAssetsMdates);
//	        				data.setMdate(mDate);
//	        			}
//	        		}
//
//	        		// 【资产基础指标数据接入】时，判断资产是否行权债, MDATE的取值根据参数判断
//	        		if ("02".equals(seAssetsMdateConf) && secXQAssetIdList.contains(data.getAssetId())) {
//	        			//取最近的行权日，取不到为原来资产的到期日
//	        			String secid = "F01_"+data.getAssetId();
//	        			Date valueDate = data.getValueDate();
//	        			Date exerciseDate =  chbAssetBasisInterfMapper.queryExerciseDate(secid,valueDate);
//	        			if (null != exerciseDate) {
//	        				data.setMdate(exerciseDate);
//	        			}
//	        		}
//
//	        		StringBuffer keyVFinal = new StringBuffer();
//	            	keyVFinal.append(data.getProdId()).append(data.getAssetId()).append(data.getValueDate());
//	            	String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
//	            	data.setMd5primarykey(md5Key);
//	            	if(data.getUpdateTime().compareTo(lastUpdatetime)>0)
//	            		lastUpdatetime =data.getUpdateTime();
//	            	if(spAssetsBasisDataMap.containsKey(key)){
//	            		data.setAssetId(spAssetsBasisDataMap.get(key).getChlId());
//	            		String key1=data.getProdId() + GlobalConstant.UNDER_LINE + data.getAssetId();
//	            		if(listChbAssetsBasisDataNotBtmMap.containsKey(key1)){
//	            			ChbAssetsBasisData dataOld=listChbAssetsBasisDataNotBtmMap.get(key1);
//	            			dataOld.setPosAmt((dataOld.getPosAmt()==null?BigDecimal.ZERO:dataOld.getPosAmt()).add(data.getPosAmt()==null?BigDecimal.ZERO:data.getPosAmt()));
//	            			dataOld.setTdyCostAmt((dataOld.getTdyCostAmt()==null?BigDecimal.ZERO:dataOld.getTdyCostAmt()).add(data.getTdyCostAmt()==null?BigDecimal.ZERO:data.getTdyCostAmt()));
//	            			dataOld.setTdyFloatIngpl((dataOld.getTdyFloatIngpl()==null?BigDecimal.ZERO:dataOld.getTdyFloatIngpl()).add(data.getTdyFloatIngpl()==null?BigDecimal.ZERO:data.getTdyFloatIngpl()));
//	            			dataOld.setTdyIntincexp((dataOld.getTdyIntincexp()==null?BigDecimal.ZERO:dataOld.getTdyIntincexp()).add(data.getTdyIntincexp()==null?BigDecimal.ZERO:data.getTdyIntincexp()));
//	            			dataOld.setDelayPayAmt((dataOld.getDelayPayAmt()==null?BigDecimal.ZERO:dataOld.getDelayPayAmt()).add(data.getDelayPayAmt()==null?BigDecimal.ZERO:data.getDelayPayAmt()));
//	            			listChbAssetsBasisDataNotBtmMapResult.put(key1, data);
//	            		}else{
//	            			listChbAssetsBasisDataNotBtmMapResult.put(key1, data);
//	            		}
//	        		}else{
//	        			listChbAssetsBasisDataNotBtmMapResult.put(key,data);
//	        		}
//	        	}
				//先删后插
				for(ChbAssetsBasisData data : listChbAssetsBasisData){
	        		/* 当前持仓、当日成本、当日公允价值变动、当日累计应计利息余额、当日累计应计利息余额
	        		/全为空或者为0时不入库 */
					if (isNullAmt(data.getPosAmt())
							&& isNullAmt(data.getTdyCostAmt())
							&& isNullAmt(data.getTdyFloatIngpl())
							&& isNullAmt(data.getTdyIntincexp())
							&& isNullAmt(data.getDelayPayAmt())) {
						continue;
					}
					listChbAssetsBasisDataAll.add(data);
				}
//	        	for(String key : listChbAssetsBasisDataNotBtmMapResult.keySet()){
//	        		ChbAssetsBasisData data=listChbAssetsBasisDataNotBtmMapResult.get(key);
//	        		/* 当前持仓、当日成本、当日公允价值变动、当日累计应计利息余额、当日累计应计利息余额
//	        		/全为空或者为0时不入库 */
//	        		if (isNullAmt(data.getPosAmt())
//	        				&& isNullAmt(data.getTdyCostAmt())
//	        				&& isNullAmt(data.getTdyFloatIngpl())
//	        				&& isNullAmt(data.getTdyIntincexp())
//	        				&& isNullAmt(data.getDelayPayAmt())) {
//	        			continue;
//	        		}
//	        		batchMapper.insertNoBtm(data);
//	        		count++;
//	        		if(count%1000==0){
//	        			logger.debug("已插入"+count+"条"+DateUtil.getCurrentDateTime());
//	            		session.commit();
//	            		session.clearCache();
//	        		}
//	        	}
			}

//    	for(Date date :isUpdated){
//        	//生成估值信息
//        	genAssetValueInfo(date);
//    	}
		//新的最后更新时间
		if(lastUpdatetime!=null){
			ChbInterfUtil.updateLastupdatetime(ChbInterfConfig.资产基础指标信息接口.getInterId(), lastUpdatetime);
		}
		logger.info("资产基础指标数据接口导入批处理结束。用时："+(System.currentTimeMillis()-startTime)/1000+"s");

		return listChbAssetsBasisDataAll;
	}

		@Override
	public Class getEntityClass() {
		return ChbAssetsBasisData.class;
	}

	@Override
	public String getFileName() {
		return InterfDefine.资产基础指标数据.getCode();
	}


    
	/**
	 * 根据资产基础指标生成资产估值信息
	 */
    public void genAssetValueInfo(Date date,String InstitutionCode){
    	String isChlBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
    	try (SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)){
			//批量插入mapper
			ChbAstValInterfMapper batchMapper =  session.getMapper(ChbAstValInterfMapper.class);
			// 先删后插
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("valDate", DateUtil.format(date));
			para.put("InstitutionCode", InstitutionCode);
			chbAstValInterfMapper.deleteInfo(para);
			// 处理估值表数据
			List<ChbAstValue> infoList = chbAstValInterfMapper.queryAssetValueInfoList(DateUtil.format(date),SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE),isChlBtm,InstitutionCode);
			int count = 0;
			for (ChbAstValue info : infoList) {
				info.setValId(IDGenerator.generate(36));
				info.setIssuePartyId(CommonUtil.selectBankCode());
				info.setCreateUser("system");
				info.setUpdateUser("system");
				info.setStatus("E");

				batchMapper.insertInfo(info);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		} 
    }
    
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbAssetsBasisData> getDataFromInterf(String batchTime){
		List<ChbAssetsBasisData> rs = new ArrayList<ChbAssetsBasisData>();
		template.select("com.joyintech.tams.svreport.interf.chb.assetbasis.dao.ChbAssetBasisInterfMapper.selectByValuedate",batchTime,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbAssetsBasisData pos = (ChbAssetsBasisData) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<Date> isUpdated(Date batchTime){
    	return chbAssetBasisInterfMapper.isUpdated(batchTime);
    }
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbAssetsBasisData> getChlBtmDataFromInterf(String batchTime){
		List<ChbAssetsBasisData> rs = new ArrayList<ChbAssetsBasisData>();
		template.select("com.joyintech.tams.svreport.interf.chb.assetbasis.dao.ChbAssetBasisInterfMapper.selectChlBtmByValuedate",batchTime,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbAssetsBasisData pos = (ChbAssetsBasisData) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbAssetsBasisData> getChlBtmDataFromInterfSp(String batchTime,String isCalLoadRate){
		List<ChbAssetsBasisData> rs = new ArrayList<ChbAssetsBasisData>();
		Map<String,String> params = new HashMap<String,String>();
		params.put("batchTime", batchTime);
		params.put("isCalLoadRate", isCalLoadRate);
		template.select("com.joyintech.tams.svreport.interf.chb.assetbasis.dao.ChbAssetBasisInterfMapper.selectChlBtmByValuedateSp",params,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbAssetsBasisData pos = (ChbAssetsBasisData) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
	
	/**  
	 * @Description 判断传入的值是否为空或者为0  
	 * @Author      高健  
	 * @Date        2019年9月16日 下午4:06:18  
	 * @param @param amt
	 * @param @return 是否为空或者为0  
	 * @return boolean 返回类型   
	 * @throws  
	 */  
	public boolean isNullAmt(BigDecimal amt) {
		boolean flag = false;
		if (null == amt || amt.compareTo(BigDecimal.ZERO) == 0) {
			flag = true;
		} 
		return flag;
	}

	private void insertData2(List<ChbAssetsBasisData> listChbAssetsBasisData) {

		if (listChbAssetsBasisData.size()<=5000) {
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			//批量插入mapper
			ChbAssetsBasisDataMapper batchMapper =  session.getMapper(ChbAssetsBasisDataMapper.class);

			Map<Date, List<ChbAssetsBasisData>> listChbAssetsBasisDataMap =
					listChbAssetsBasisData.stream().collect(JoyinCollectors.groupingBy(chbAssetsBasisData -> chbAssetsBasisData.getValueDate()));
			int count = 0;
			try {
				for (Date date : listChbAssetsBasisDataMap.keySet()) {

					String dateStr = DateUtil.format(date);
					logger.info("删除" + dateStr + "日的资产基础指标数据，开始");
					ChbAssetsBasisData param = new ChbAssetsBasisData();
					param.setValueDate(date);
					chbAssetBasisInterfMapper.deleteData(param);
//	        	chbAssetBasisInterfMapper.deleteDataNoBtm(param);
					logger.info("删除" + dateStr + "日的资产基础指标数据，结束");
					logger.info("插入" + dateStr + "日的资产基础指标数据，开始");
					for (ChbAssetsBasisData data : listChbAssetsBasisDataMap.get(date)) {
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
			ImpTable table = new ImpTable("CHB_ASSETS_BASIS_DATA");
			table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
			// 临时文件存放目录
			String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

			batchInsertService.exeBatchldr(tmpPath, listChbAssetsBasisData, table);
		}


	}
}
