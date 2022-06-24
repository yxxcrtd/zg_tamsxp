package com.joyintech.tams.svreport.interf.chb.cprdbalance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.BaseInterfService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.base.dao.ChbAssetsBasisDataMapper;
import com.joyintech.tams.common.base.entity.ChbAssetsBasisData;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.interf.chb.investmentsinfo.dto.ChbAssetInfoRate;
import com.joyintech.tams.svreport.interf.chb.investmentsinfo.service.ChbAssetInfoRateInterf;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
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

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.tams.common.base.entity.ChbCprodBalanceDto;
import com.joyintech.tams.common.svreport.chb.constant.ChbInterfConfig;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.svreport.interf.chb.conf.DataSourceType;
import com.joyintech.tams.svreport.interf.chb.cprdbalance.dao.ChbCprodBalanceDtoMapper;


/**
 * 周期型产品余额表接口
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbCprodBalanceInterf extends BaseInterfService {
	@Autowired
	private ChbCprodBalanceDtoMapper chbCprodBalanceDtoMapper;
	@Autowired
	private SqlSessionTemplate template;
	@Autowired
	private BatchInsertService batchInsertService;
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    public List<Date> generateData(Date batchTime) {
		logger.debug("周期型产品余额接口导入批处理开始");
		long startTime=System.currentTimeMillis();
		// 查询数据
		List<ChbCprodBalanceDto> listChbCprodBalanceDto = getBusEntity(null,batchTime, null, "D");
		// 入库
		if(!StringUtil.isEmptyList(listChbCprodBalanceDto)){
			insertData2(listChbCprodBalanceDto);
		}
		logger.debug("周期型产品余额接口导入批处理结束。用时："+(System.currentTimeMillis()-startTime)/1000+"s");
		return new ArrayList<Date>();

	}

	@Override
	public List<ChbCprodBalanceDto> getBusEntity(List<String> prodIdlist, Date batchDate, String institutionCode, String source) {

		ChbCprodBalanceInterf chbBotAstPosInterf2 =  SpringContextUtil.getBean(ChbCprodBalanceInterf.class);
		Date lastUpdatetime = ChbInterfUtil.queryUpdateTime(ChbInterfConfig.周期型产品余额接口.getInterId());
		List<Date> isUpdated = chbBotAstPosInterf2.isUpdated(lastUpdatetime);
		List<ChbCprodBalanceDto> listChbCprodBalanceDtoAll = new ArrayList<ChbCprodBalanceDto>();
		int count = 0;
		for(Date date :isUpdated) {
			//查询信息
			List<ChbCprodBalanceDto> listChbCprodBalanceDto = chbBotAstPosInterf2.getDataFromInterf(date);
			// 生成日期为空防止要素（维护）变动更新日期
			for (ChbCprodBalanceDto position : listChbCprodBalanceDto) {
				position.setBalId(IDGenerator.generate(36));
				//人民币发行金额 = 总发行数量*币种汇率
				if (position.getUpdateTime().compareTo(lastUpdatetime) > 0)
					lastUpdatetime = position.getUpdateTime();
			}
			listChbCprodBalanceDtoAll.addAll(listChbCprodBalanceDto);

		}
		//新的最后更新时间
		if(lastUpdatetime!=null){
			ChbInterfUtil.updateLastupdatetime(ChbInterfConfig.周期型产品余额接口.getInterId(), lastUpdatetime);
		}
		return listChbCprodBalanceDtoAll;
	}

	@Override
	public Class getEntityClass() {
		return ChbCprodBalanceDto.class;
	}

	@Override
	public String getFileName() {
		return InterfDefine.周期型产品余额接口数据.getCode();
	}


	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<ChbCprodBalanceDto> getDataFromInterf(Date batchTime){
		List<ChbCprodBalanceDto> rs = new ArrayList<ChbCprodBalanceDto>();
		template.select("com.joyintech.tams.svreport.interf.chb.cprdbalance.dao.ChbCprodBalanceDtoMapper.selectByPosdate",batchTime,
                new ResultHandler() {
            @Override
            public void handleResult(ResultContext resultContext) {
            	ChbCprodBalanceDto pos = (ChbCprodBalanceDto) resultContext.getResultObject();
                rs.add(pos);
            }
        });
    	return rs;
    }
    
	@Transactional(propagation=Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.INTERF)
	public List<Date> isUpdated(Date batchTime){
    	return chbCprodBalanceDtoMapper.isUpdated(batchTime);
    }


	private void insertData2(List<ChbCprodBalanceDto> listChbCprodBalanceDto) {

		if (listChbCprodBalanceDto.size()<=5000) {
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			//批量插入mapper
			ChbCprodBalanceDtoMapper batchMapper =  session.getMapper(ChbCprodBalanceDtoMapper.class);

			Map<Date, List<ChbCprodBalanceDto>> listChbCprodBalanceDtoMap =
					listChbCprodBalanceDto.stream().collect(JoyinCollectors.groupingBy(chbCprodBalanceDto -> chbCprodBalanceDto.getValueDate()));
			int count = 0;
			try {
				for (Date date : listChbCprodBalanceDtoMap.keySet()) {

					//先删后插
					String dateStr = DateUtil.format(date);
					logger.debug("删除"+dateStr+"日的周期型产品余额，开始");
					chbCprodBalanceDtoMapper.deleteByPosdate(date);
					logger.debug("删除"+dateStr+"日的周期型产品余额，结束");
					logger.debug("插入"+dateStr+"日的周期型产品余额，开始");
					for(ChbCprodBalanceDto position : listChbCprodBalanceDtoMap.get(date)){
						batchMapper.insert(position);
						count++;
						if(count%1000==0){
							logger.debug("已插入"+count+"条"+DateUtil.getCurrentDateTime());
							session.commit();
							session.clearCache();
						}

					}
					logger.debug("插入"+dateStr+"日的周期型产品余额，结束");
				}
				session.commit();
				session.clearCache();
			}finally {
				session.close();
			}
		} else {
			//sqlloader插入
			ImpTable table = new ImpTable("CHB_CPROD_BALANCE");
			table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
			// 临时文件存放目录
			String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

			batchInsertService.exeBatchldr(tmpPath, listChbCprodBalanceDto, table);
		}

	}
}
