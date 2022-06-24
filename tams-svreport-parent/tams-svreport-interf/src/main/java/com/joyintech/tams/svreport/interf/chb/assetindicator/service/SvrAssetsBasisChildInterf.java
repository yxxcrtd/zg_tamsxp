package com.joyintech.tams.svreport.interf.chb.assetindicator.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.BaseInterfService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.base.dao.ChbAssetsBasisDataChildMapper;
import com.joyintech.tams.common.base.entity.ChbAssetsBasisDataChild;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.constant.ChbInterfConfig;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.assetbasis.dao.ChbAstValInterfMapper;
import com.joyintech.tams.svreport.interf.chb.assetindicator.dao.ChbAssetBasisChildInterfMapper;
import com.joyintech.tams.svreport.interf.chb.conf.DataSourceType;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 子资管计划资产指标信息数据接口
 * @author zhangzhao
 *
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrAssetsBasisChildInterf extends BaseInterfService {
	@Autowired
	private ChbAssetBasisChildInterfMapper chbAssetBasisChildInterfMapper;
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
		List<ChbAssetsBasisDataChild> listChbAssetsBasisDataChild = getBusEntity(null, batchTime, null, "D");
		// 入库
		if(!StringUtil.isEmptyList(listChbAssetsBasisDataChild)){
			insertData2(listChbAssetsBasisDataChild);
		}
		logger.info("资产基础指标数据接口导入批处理结束。用时："+(System.currentTimeMillis()-startTime)/1000+"s");
		return new ArrayList<Date>();
	}


	@Override
	public List<ChbAssetsBasisDataChild> getBusEntity(List<String> prodIdlist, Date batchDate, String institutionCode, String source) {
		List<Date> isUpdated = new ArrayList<Date>();
		//通道投资资产管理产品是否穿透报送
		String isChlBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.PBC_IS_CHL_BTM);
		// 不穿透报送，不用接入子资管计划指标
//    	if (!StringUtil.equals("01", isChlBtm)) {
//    		return isUpdated;
//    	}
		SvrAssetsBasisChildInterf svrAssetBasisInterf2 =  SpringContextUtil.getBean(SvrAssetsBasisChildInterf.class);
		Date lastUpdatetime = ChbInterfUtil.queryUpdateTime(ChbInterfConfig.母子资产基础指标数据.getInterId());
		isUpdated = svrAssetBasisInterf2.isUpdated(lastUpdatetime);
		List<ChbAssetsBasisDataChild> listChbAssetsBasisDataChildAll = new ArrayList<ChbAssetsBasisDataChild>();
			for(Date date :isUpdated){
				//查询信息
				List<ChbAssetsBasisDataChild> listChbAssetsBasisDataChild = new ArrayList<ChbAssetsBasisDataChild>();
				if("01".equals(isChlBtm)){
					listChbAssetsBasisDataChild = svrAssetBasisInterf2.getDataFromInterf(DateUtil.format(date));
				}else{
					listChbAssetsBasisDataChild = svrAssetBasisInterf2.getChlBtmDataFromInterf(DateUtil.format(date));
				}
				// 生成日期为空防止要素（维护）变动更新日期
				for(ChbAssetsBasisDataChild data : listChbAssetsBasisDataChild){
					data.setValId(IDGenerator.generate(36));
					data.setCreateUser("system");
					data.setUpdateUser("system");
					data.setStatus("E");
					//数据来源
					if("D".equals(source)){
						data.setDataSource("16");
					}
					StringBuffer keyVFinal = new StringBuffer();
					keyVFinal.append(data.getProdId()).append(data.getAssetId()).append(data.getValueDate());
					String md5Key = DigestUtils.md5Hex(keyVFinal.toString());
					data.setMd5primarykey(md5Key);
					if(data.getUpdateTime().compareTo(lastUpdatetime)>0)
						lastUpdatetime =data.getUpdateTime();
				}
				for(ChbAssetsBasisDataChild data : listChbAssetsBasisDataChild){
	        		/* 当前持仓、当日成本、当日公允价值变动、当日累计应计利息余额、当日累计应计利息余额
	        		/全为空或者为0时不入库 */
					if (isNullAmt(data.getPosAmt())
							&& isNullAmt(data.getTdyCostAmt())
							&& isNullAmt(data.getTdyFloatIngpl())
							&& isNullAmt(data.getTdyIntincexp())
							&& isNullAmt(data.getDelayPayAmt())) {
						continue;
					}
					listChbAssetsBasisDataChildAll.add(data);
				}
			}

//    	for(Date date :isUpdated){
//        	//生成估值信息
//        	genAssetValueInfo(date);
//    	}
		//新的最后更新时间
		if(lastUpdatetime!=null){
			ChbInterfUtil.updateLastupdatetime(ChbInterfConfig.母子资产基础指标数据.getInterId(), lastUpdatetime);
		}

		return listChbAssetsBasisDataChildAll;
	}

	@Override
	public Class getEntityClass() {
		return ChbAssetsBasisDataChild.class;
	}

	@Override
	public String getFileName() {
		return InterfDefine.母子资产基础指标数据.getCode();
	}


	/**
	 * 根据资产基础指标生成资产估值信息
	 */
//    public void genAssetValueInfo(Date date){
//    	try (SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)){
//			//批量插入mapper
//			ChbAstValInterfMapper batchMapper =  session.getMapper(ChbAstValInterfMapper.class);
//			// 先删后插
//			Map<String, Object> para = new HashMap<String, Object>();
//			para.put("valDate", DateUtil.format(date));
//			chbAstValInterfMapper.deleteInfo(para);
//			// 处理估值表数据
//			List<ChbAstValue> infoList = chbAstValInterfMapper.queryAssetValueInfoList(DateUtil.format(date),SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE));
//			int count = 0;
//			for (ChbAstValue info : infoList) {
//				info.setValId(IDGenerator.generate(36));
//				info.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
//				info.setCreateUser("system");
//				info.setUpdateUser("system");
//				info.setStatus("E");
//
//				batchMapper.insertInfo(info);
//				count++;
//				if (count % 1000 == 0) {
//					session.commit();
//					session.clearCache();
//				}
//			}
//			session.commit();
//			session.clearCache();
//		} 
//    }
    
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbAssetsBasisDataChild> getDataFromInterf(String batchTime){
		List<ChbAssetsBasisDataChild> rs = new ArrayList<ChbAssetsBasisDataChild>();
		template.select("com.joyintech.tams.svreport.interf.chb.assetindicator.dao.ChbAssetBasisChildInterfMapper.selectByValuedate",batchTime,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbAssetsBasisDataChild pos = (ChbAssetsBasisDataChild) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<Date> isUpdated(Date batchTime){
    	return chbAssetBasisChildInterfMapper.isUpdated(batchTime);
    }
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbAssetsBasisDataChild> getChlBtmDataFromInterf(String batchTime){
		List<ChbAssetsBasisDataChild> rs = new ArrayList<ChbAssetsBasisDataChild>();
		template.select("com.joyintech.tams.svreport.interf.chb.assetindicator.dao.ChbAssetBasisChildInterfMapper.selectChlBtmByValuedate",batchTime,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbAssetsBasisDataChild pos = (ChbAssetsBasisDataChild) resultContext.getResultObject();
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


	private void insertData2(List<ChbAssetsBasisDataChild> listChbAssetsBasisDataChild) {
		if (listChbAssetsBasisDataChild.size()<=5000) {
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			//批量插入mapper
			ChbAssetsBasisDataChildMapper batchMapper =  session.getMapper(ChbAssetsBasisDataChildMapper.class);

			Map<Date, List<ChbAssetsBasisDataChild>> listChbAssetsBasisDataChildMap =
					listChbAssetsBasisDataChild.stream().collect(JoyinCollectors.groupingBy(chbAssetsBasisDataChild -> chbAssetsBasisDataChild.getValueDate()));
			int count = 0;
			try {
				for (Date date : listChbAssetsBasisDataChildMap.keySet()) {

					String dateStr = DateUtil.format(date);
					logger.info("删除" + dateStr + "日的子资产基础指标数据，开始");
					ChbAssetsBasisDataChild param = new ChbAssetsBasisDataChild();
					param.setValueDate(date);
					chbAssetBasisChildInterfMapper.deleteData(param);
					logger.info("删除" + dateStr + "日的子资产基础指标数据，结束");
					logger.info("插入" + dateStr + "日的子资产基础指标数据，开始");
					for (ChbAssetsBasisDataChild data : listChbAssetsBasisDataChildMap.get(date)) {
						batchMapper.insert(data);
						count++;
						if (count % 1000 == 0) {
							logger.debug("已插入" + count + "条" + DateUtil.getCurrentDateTime());
							session.commit();
							session.clearCache();
						}
					}
					logger.info("插入" + dateStr + "日的子资产基础指标数据，结束");
				}
				session.commit();
				session.clearCache();
			}finally {
				session.close();
			}
		} else {
			//sqlloader插入
			ImpTable table = new ImpTable("CHB_ASSETS_BASIS_DATA_CHILD");
			table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
			// 临时文件存放目录
			String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

			batchInsertService.exeBatchldr(tmpPath, listChbAssetsBasisDataChild, table);
		}

	}
}
