package com.joyintech.tams.svreport.interf.chb.loandetailinfo.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import org.apache.commons.collections.CollectionUtils;
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
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.base.dao.ChbLoanDetailContractDtoMapper;
import com.joyintech.tams.common.base.entity.ChbLoanDetailContractDto;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.svreport.interf.chb.conf.DataSourceType;
import com.joyintech.tams.svreport.interf.chb.loandetailinfo.dao.SvrLoanDetailContractMapper;

/**
 * 功能:资产要素表接口
 * @since 2.1.2
 * @version 2.1.2
 * @作者：yuluan
 * @创建时间：2020年2月14日
 * @修改记录：
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrLoanDetailContractInterf  extends BaseService{
	
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private SvrLoanDetailContractMapper svrLoanDetailContractMapper;
	
	@Autowired
	private SqlSessionTemplate template;

	@Autowired
	private BatchInsertService batchInsertService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	private SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	

    /**
     * 功能:接入投资交易信息
     * @since 2.1.2
     * @version 2.1.2
     * @author：yuluan 2020年2月14日
     * @param batchDate 跑批日
     * @param isSynchronizeTask 是否从轮训进入
     * @prodList 产品代码
     * void
     */
    public Map<String,List<String>> generateInvestTradeData(Date batchDate,List<String> prodList,boolean isSynchronizeTask) {	
		logger.debug("上游贷款明细合同信息入库:----开始------" +  df.format(new Date()));	
		Date lastUpdatetime = ChbInterfUtil.queryUpdateTime(InterfDefine.贷款明细原始合同信息接口.getCode());
    	// 用于返回的数据
    	Map<String,List<String>> mapPara = new HashMap<String,List<String>>();    	
    	// 切换数据源查询信息
    	//查询信息
    	SvrLoanDetailContractInterf chbInvestTradeInterf =  SpringContextUtil.getBean(SvrLoanDetailContractInterf.class);
		logger.debug("上游贷款明细合同信息查询:----开始------" +  df.format(new Date()));
    	// 查询当日新增/更改交易信息
		List<String> assetcodeList=svrLoanDetailContractMapper.selectAllLoanAssetCode();
		
    	List<ChbLoanDetailContractDto> listChbInvestTrade = chbInvestTradeInterf.queryInvestTradeByInterfdateBatch(assetcodeList);
		logger.debug("上游贷款明细合同信息查询:----结束------" +  df.format(new Date()));
		
		logger.debug("贷款明细合同信息插入:----开始------" +  df.format(new Date()));	
		svrLoanDetailContractMapper.deleteAllData();
		if (CollectionUtils.isNotEmpty(listChbInvestTrade)) {
			insertLoanDetailContract(listChbInvestTrade);
		}

//		insertDataMerge(listChbInvestTrade,null);
		logger.debug("贷款明细合同信息插入:----结束------" +  df.format(new Date()));	
    	if(lastUpdatetime!=null){
        	ChbInterfUtil.updateLastupdatetime(InterfDefine.贷款明细原始合同信息接口.getCode(), lastUpdatetime);;
    	}
		logger.debug("上游贷款明细合同信息入库:----结束------" +  df.format(new Date()));	
		return mapPara;
	}
    
    /**
     * 功能:查询需要接入的贷款明细合同信息
     * @since 2.1.2
     * @version 2.1.2
     * @author：yuluan 2020年2月14日
     * @prodList 资产代码
     * @return 
     * List<ChbLoanDetailContractDto>
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    // 切换数据源
    @DataSource(DataSourceType.INTERF)
    public List<ChbLoanDetailContractDto> queryInvestTradeByInterfdateBatch(List<String> assetcodeList){
		List<ChbLoanDetailContractDto> tradeList = new ArrayList<ChbLoanDetailContractDto>();
		List<ChbLoanDetailContractDto> tradeListNoCny = new ArrayList<ChbLoanDetailContractDto>();
		Map<String, BigDecimal> rateMap = new HashMap<>();
		String exchangeSource = CacheUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE_PBC);
//		Map<String,Object> param = new HashMap<String,Object>();
	  int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"50" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));//每批commit的个数
        int batchLastIndex = batchCount;//每批最后一个的下标
        for(int index = 0; index < assetcodeList.size();){
            if(batchLastIndex > assetcodeList.size()-1){
                batchLastIndex = assetcodeList.size();
                tradeList.addAll(svrLoanDetailContractMapper.selectLoanDetailContractByAssetCodeList(assetcodeList.subList(index, batchLastIndex)));
                tradeListNoCny.addAll(svrLoanDetailContractMapper.selectLoanDetailContractByAssetCodeListNoCny(assetcodeList.subList(index, batchLastIndex)));
                break;//数据插入完毕,退出循环
            }else{
            	tradeList.addAll(svrLoanDetailContractMapper.selectLoanDetailContractByAssetCodeList(assetcodeList.subList(index, batchLastIndex)));
            	tradeListNoCny.addAll(svrLoanDetailContractMapper.selectLoanDetailContractByAssetCodeListNoCny(assetcodeList.subList(index, batchLastIndex)));
                index = batchLastIndex;//设置下一批下标
                batchLastIndex = index + batchCount;                     
            }
        }
        if(!tradeListNoCny.isEmpty()){
        	for(ChbLoanDetailContractDto single:tradeListNoCny){
//        		investDeal.setCnyAmt(ChbCcyConvertUtil.changeCnyMoney(investDeal.getCcy(), investDeal.getAmt(), investDeal.getDealdate(), rateMap));
        		BigDecimal zcny = ChbCcyConvertUtil.
        							exchangeMoney(single.getOrigCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), 
        								single.getSettleDate(), single.getOrigContractAmount(), rateMap, exchangeSource);
        		single.setOrigContractAmountCny(zcny);
        	}
        	tradeList.addAll(tradeListNoCny);
        }
//		svrLoanDetailContractMapper.selectLoanDetailContractByAssetCodeList(assetcodeList);
//		param.put("assetcodeList", assetcodeList);
//        template.select("com.joyintech.tams.svreport.interf.chb.loandetailinfo.dao.SvrLoanDetailContractMapper.selectLoanDetailContractByAssetCodeList",
//        		param,new ResultHandler() {
//            @Override
//            public void handleResult(ResultContext resultContext) {
//            	tradeList.add((ChbLoanDetailContractDto) resultContext.getResultObject());
//            }
//        });
    	return tradeList;
    }
    

    /**
     * 功能:数据入库
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月5日 
     * @param investTradeList 投资交易信息
     * @param saveFlag:F－全量，I－增量
     */
    public void insertDataMerge(List<ChbLoanDetailContractDto> investTradeList,String saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("投资交易插入开始--------------" + new Date());
            SvrLoanDetailContractMapper batchMapper = session.getMapper(SvrLoanDetailContractMapper.class);
            int batchCount = Integer.parseInt( null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)?"50" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));//每批commit的个数
            int batchLastIndex = batchCount;//每批最后一个的下标
            for(int index = 0; index < investTradeList.size();){
                if(batchLastIndex > investTradeList.size()-1){
                    batchLastIndex = investTradeList.size();
                    batchMapper.mergeLoanDetailContract(investTradeList.subList(index, batchLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    batchMapper.mergeLoanDetailContract(investTradeList.subList(index, batchLastIndex));
                    index = batchLastIndex;//设置下一批下标
                    batchLastIndex = index + batchCount;                     
                }
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            logger.debug("投资交易插入结束--------------" + new Date());
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        finally {
            session.close();
        }
        logger.debug("投资交易插入结束--------------" + new Date());
    }
    private void insertLoanDetailContract(List<ChbLoanDetailContractDto> listNE) {
		logger.debug("批量插入贷款明细原始合同信息----开始-----:" +  df.format(new Date()));
		if (listNE.size()<=5000) {
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			ChbLoanDetailContractDtoMapper mstBondCreditRatingMapper =  session.getMapper(ChbLoanDetailContractDtoMapper.class);
			try{
				int count = 0;
				for(ChbLoanDetailContractDto sch : listNE){
					sch.setStatus("E");
					sch.setDataSource("16");
					mstBondCreditRatingMapper.insert(sch);
					count++;
					if(count % 1000==0){
						session.commit();
						session.clearCache();
					}
				}
				session.commit();
				session.clearCache();
			} finally {
				session.close();
			}
		} else {
			//sqlloader插入
			ImpTable table = new ImpTable("CHB_LOAN_DETAIL_CONTRACT");
			table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
			// 临时文件存放目录
			String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
			listNE.forEach(e->{
				e.setStatus("E");
				e.setDataSource("16");
			});
			batchInsertService.exeBatchldr(tmpPath, listNE, table);
		}

		logger.debug("批量插入贷款明细原始合同信息----结束-----:" +  df.format(new Date()));	
	}
	
}
