package com.joyintech.tams.svreport.chb.assetinforeg.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.svreport.chb.assetinforeg.dao.AssetInfoRegist001Mapper;
import com.joyintech.tams.svreport.chb.assetinforeg.dto.ChbAssetInfoSub001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforeinsert.service.ChbCommonCheckService;
import com.joyintech.tams.svreport.chb.investtrade.dao.MstCounterPartyDtoMapper;
import com.joyintech.tams.svreport.chb.investtrade.dto.MstCounterPartyDto;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:资产要素登记批处理
 * @since 1.7.0
 * @version 1.7.0
 * @作者：weiyuqiang
 * @创建时间：2019年5月30日
 * @修改记录：
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AssetInfoRegistBatchService extends SvrCBBaseService {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AssetInfoRegist001Mapper assetInfoRegist001Mapper;
    /**
     * 交易对手mapper
     */
    @Autowired
    private MstCounterPartyDtoMapper mstCounterPartyDtoMapper;
	
	@Autowired
	private SqlSessionTemplate template;

    /**
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;

    /**
     * 数据批量一次查询或者删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    private int reportSelectOrDeleteCount;

    /**
     * 入库前校验用Service
     */
    @Autowired
    private ChbCommonCheckService chbCommonCheckService;
	

    /**
     * 功能:自动生成资产要素登记信息
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年2月21日
     * @param executeDate 跑批日
     * @param prodList 产品代码
     * @param assetCodeList 资产代码
     * void
     */
	public void autoRegistAssetInfo(Date executeDate,List<String> prodList,List<String> assetCodeList,String InstitutionCode){
		// 设置批处理bean
		setBatchBeanName(BatchBeanName.资产要素登记报送);
		// 批处理开始
		logInfoBatchStart();
		// 通道交易是否穿透報送 01 是 02 否
        String isChlBtm = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_CHL_BTM);
		// 获取数据日期为跑批日的资产要素信息
		String registDate = DateUtil.format(executeDate);
//		List<ChbAssetInfoSub001Dto> assetInfoRegList = assetInfoRegist001Mapper.selectAssetInfoForRegist(registDate,assetCodeList);
		Map<String,Object>  paramMap=new  HashMap<String,Object>();
		paramMap.put("registDate", registDate);
		paramMap.put("assetList", assetCodeList);
		paramMap.put("isChlBtm", isChlBtm);
        paramMap.put("InstitutionCode",InstitutionCode);
        //通道投资资产管理产品是否穿透报送
        paramMap.put("isChlBtmStr",isChlBtm);
		List<ChbAssetInfoSub001Dto> assetInfoRegList = assetInfoRegist001Mapper.selectAssetInfoForRegist(paramMap);
		// 登记插入列表
		List<ChbAssetInfoSub001Dto> insertDataList = new ArrayList<ChbAssetInfoSub001Dto>();
        // 登记删除列表
        List<ChbAssetInfoSub001Dto> deleteDataList = new ArrayList<ChbAssetInfoSub001Dto>();
		// 报送时点通用方法
		// 当前批次报送时点集合
		Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();

		if (CollectionUtils.isNotEmpty(assetInfoRegList)) {
	        // 查询所有已登记的资产要素信息
	        // 分批处理数据,100个资产查询一次
            List<ChbAssetInfoSub001Dto> assetInfoAlreadyRegList = new ArrayList<>();
            for (int i = 0;i < assetInfoRegList.size();){
                int end = i + 100;
                if (end > assetInfoRegList.size()){
                    end = assetInfoRegList.size();
                }
                assetInfoAlreadyRegList.addAll(assetInfoRegist001Mapper.selectAllAssetReg(assetInfoRegList.subList(i, end)));
                i = end;
            }
	        // 存放已登记的资产要素
            Map<String,ChbAssetInfoSub001Dto> assetCodeRegMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(assetInfoAlreadyRegList)){
                assetCodeRegMap = assetInfoAlreadyRegList.stream().collect(Collectors.toMap(p->p.getAssetCode(), p->p,(key1,key2)->key2));
            }
	        // 查询所有交易对手
	        List<MstCounterPartyDto> counterList = mstCounterPartyDtoMapper.selectAllTradeCounter();
	        Map<String,String> counterMap = counterList.stream().collect(Collectors.toMap(MstCounterPartyDto::getCounterId, 
	                MstCounterPartyDto::getCounterName,(key1,key2)->key2));
			for (ChbAssetInfoSub001Dto dto : assetInfoRegList) {
	            // 交易对手
	            if (StringUtil.isNotEmpty(dto.getRivalParty())){
	                String counterid = dto.getRivalParty();
	                // 存在交易对手code则取名称
	                if (counterMap.get(counterid) != null){
	                    String counterName = counterMap.get(counterid);
	                    dto.setRivalParty(counterName);
	                } else {
	                    dto.setRivalParty(dto.getRivalParty());
	                }
	            }
	            //现金及银行存款类资产信息，是否为结构性存款不存值
	            if(DictCodeListExt2.资产_负债类别_本行_他行存款_大额存单.getValue().equals(dto.getAssetType())){
	            	dto.setIsStruct(null);
	            }
	            //资产管理产品信息,是否掌控计划底层资产详情不存值
	            else if(DictCodeListExt2.资产_负债类别_资产管理产品.getValue().equals(dto.getAssetType())){
	            	dto.setIsCtrlBottom(null);
                    dto.setMngOrgName(null);
                    dto.setTruOrgName(null);
	            }
	            
				// 登记编码
				dto.setRegistSeqno(IDGenerator.generate(36));
                // 报送流水号
                dto.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
				// 数据日期
				dto.setStatusDate(executeDate);
				// 机构代码
				dto.setOrgCode(CommonUtil.getOrgCode());
				// 创建人
				dto.setCreateUser(CommonUtil.getUserName());
				// 创建时间
                dto.setCreateTime(DateUtil.now());
                // 更新人
                dto.setUpdateUser(CommonUtil.getUserName());
                // 更新时间
                dto.setUpdateTime(DateUtil.now());
                // 创建部门
                dto.setCreateDept(CommonUtil.getDeptCode());
                // 生成的登记都为未报送
                dto.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
                // 设置小数位数
                setScale(dto);
                // 如果资产二级类型为拆放同业或者1107 同业拆入1109时回购标的类别和回购标的金额字段应该为空
                if (DictCodeListExt2.资产_负债二级类别_拆放同业.getValue()
                        .equals(dto.getAssetType2())
                        || DictCodeListExt2.资产_负债二级类别_同业拆入.getValue()
                        .equals(dto.getAssetType2())){
                    dto.setTargetType1(null);
                    dto.setTargetAmt(null);
                }
				// 根据基础数据状态设置报送数据状态
                // 库里有已报送才生成删除修改
                if (assetCodeRegMap.get(dto.getAssetCode())!= null){
                    // 已修改的数据则生成删除未报送、修改未报送
    				if (DictCodeListExt1.维护数据状态_删除.getValue().equals(dto.getStatus())){
    				    // 如果有删除已报送则不生成删除未报送，否则生成删除未报送
    				    if (DictCodeListExt2.报送数据状态_删除.getValue().
    				            equals(assetCodeRegMap.get(dto.getAssetCode()).getStatus())){
                            deleteDataList.add(dto);
                            continue;
    				    } else {
                            dto.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
                            deleteDataList.add(dto);
    				    }
    				} else if (DictCodeListExt1.维护数据状态_修改.getValue().equals(dto.getStatus())){
                        // 已登记数据是删除状态，业务数据是修改或者新建状态，则生成修改未报送
    				    if (DictCodeListExt2.报送数据状态_删除.getValue().
                                equals(assetCodeRegMap.get(dto.getAssetCode()).getStatus())){
                            dto.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
                            deleteDataList.add(dto);
                        } else if (!compareAssetInfoReg(dto,assetCodeRegMap.get(dto.getAssetCode()))){
                            dto.setStatus(DictCodeListExt2.报送数据状态_修改.getValue());
                            deleteDataList.add(dto);
                        } else {
                            deleteDataList.add(dto);
                            continue;
                        }
                    } else if (DictCodeListExt1.维护数据状态_有效.getValue().equals(dto.getStatus())){
                        // 已登记数据是删除状态，业务数据是修改或者新建状态，则生成修改未报送
                        if (DictCodeListExt2.报送数据状态_删除.getValue().
                                equals(assetCodeRegMap.get(dto.getAssetCode()).getStatus())){
                            dto.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
                            deleteDataList.add(dto);
                        } else if (!compareAssetInfoReg(dto,assetCodeRegMap.get(dto.getAssetCode()))){
                            dto.setStatus(DictCodeListExt2.报送数据状态_修改.getValue());
                            deleteDataList.add(dto);
                        } else{
                            deleteDataList.add(dto);
                            continue;
                        }
                    } else {
                        logger.info("数据维护状态有误，应为：D|A|M!");
                        continue;
                    }
                } else{
                    if (DictCodeListExt1.维护数据状态_删除.getValue().equals(dto.getStatus())){
                        deleteDataList.add(dto);
                        continue;
                    } else {
                        dto.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
                        deleteDataList.add(dto);
                    }
                }

                List<Date> dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产要素信息登记.getValue(),
                        dto.getStatus(), dateMap, executeDate);
                // 报送时点设置
                if (CollectionUtils.isNotEmpty(dateList)) {
                    dto.setChbSubmitDeadline(dateList.get(0));
                    dto.setSubmitDeadline(dateList.get(1));
                }
                // 准备插入处理修改未报送的数据
                insertDataList.add(dto);
			}
		}
		// add by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 start
		insertDataList = chbCommonCheckService.checkData(insertDataList, ChbCheckTableInfo.资产要素登记表SUB);
		// add by dubaojun 2020-10-13 入库前需要进行数据检验，检验错误的数据不入库 end
		// 数据入库
		insertDB(deleteDataList,insertDataList);
		// 记录批处理结束
		logInfoBatchEnd();
	}
	
	/**
	 * 功能:带提交的方式单条处理数据结果，入库
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年3月7日
	 * @param deleteDataList
     * @param insertDataList
	 * void
	 */
	private void insertDB(List<ChbAssetInfoSub001Dto> deleteDataList,List<ChbAssetInfoSub001Dto> insertDataList){
		if (CollectionUtils.isNotEmpty(deleteDataList)) {
		    // 插入前先删除未报送的登记数据
		    logger.debug("资产要素信息登记删除未报送数据：删除开始----------");
		    // 数据批量一次删除条数,如果参数设置过大则强制定位50
            int batchDeleteCount = reportSelectOrDeleteCount;
            // 每批最后一个的下标
            int batchDeleteLastIndex = batchDeleteCount;            
            for(int index = 0; index < deleteDataList.size();){
                if(batchDeleteLastIndex > deleteDataList.size()-1){
                    batchDeleteLastIndex = deleteDataList.size();
                    assetInfoRegist001Mapper.deleteByAssetCodeBatch(deleteDataList.subList(index, batchDeleteLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                    assetInfoRegist001Mapper.deleteByAssetCodeBatch(deleteDataList.subList(index, batchDeleteLastIndex));
                    index = batchDeleteLastIndex;//设置下一批下标
                    batchDeleteLastIndex = index + batchDeleteCount;                     
                }
            }
            logger.debug("资产要素信息登记删除未报送数据：删除结束----------");
            logger.debug("资产要素信息登记插入数据：插入开始----------");
			int count = 0;
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	    	try{
	    		AssetInfoRegist001Mapper batchMapper =  session.getMapper(AssetInfoRegist001Mapper.class);
	    		for(ChbAssetInfoSub001Dto asset : insertDataList){
	    			batchMapper.insertBatch(asset);
	    			count++;
	    			if(count%1000==0){
	    				session.commit();
	    	            // 清理缓存，防止溢出
	    	            session.clearCache();
	    			}
	    		}
	    		session.commit();
	            // 清理缓存，防止溢出
	            session.clearCache();
	    	}
	    	catch(Exception e){
	    		logger.error(e.getMessage(),e);
	    		session.rollback();
	    		throw new BusinessException(e);
	    	}
	    	finally {
	    		session.close();
	    	}
            logger.debug("资产要素信息登记插入数据：插入结束----------");
		}
	}

    /**
     * 功能:数据变更接口统一调度入口
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年5月9日
     * @param executeDate
     * @param prodList
     * @param assetList
     * @throws BusinessException 
     * @see com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService#execute(java.util.Date, java.util.List, java.util.List)
     */
    @Override
    public void execute(Date executeDate, List<String> prodList, List<String> assetList,Map<String,List<String>> param) throws BusinessException {
        autoRegistAssetInfo(executeDate, prodList, assetList,null);
    }

    /**
     * 功能:用于比较是否生成修改数据
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年6月17日
     * @param assetInfo1
     * @param assetInfo2
     * @return 
     * boolean
     */
    private boolean compareAssetInfoReg(ChbAssetInfoSub001Dto assetInfo1,ChbAssetInfoSub001Dto assetInfo2){
        ChbAssetInfoSub001Dto assetInfoCompare = getAssetInfoRegForCompare(assetInfo1);
        ChbAssetInfoSub001Dto assetInfoToCompare = getAssetInfoRegForCompare(assetInfo2);
        if (assetInfoCompare == null || assetInfoToCompare == null){
            return false;
        } else {
            String info = JSON.toJSONString(assetInfoCompare);
            String sub = JSON.toJSONString(assetInfoToCompare);
            logger.info("CHB_ASSET_INFO对应数据为：" + info);
            logger.info("CHB_ASSET_INFO_SUB对应数据为：" + sub);
            return info.equals(sub);
        }
    }
    
    /**
     * 功能:获取用于比较的dto，去除某些属性
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年6月17日
     * @param assetInfo
     * @return 
     * ChbAssetInfoSub001Dto
     */
    private ChbAssetInfoSub001Dto getAssetInfoRegForCompare(ChbAssetInfoSub001Dto assetInfo){
        ChbAssetInfoSub001Dto result = null;
        try {
            result = (ChbAssetInfoSub001Dto) BeanUtil.cloneBean(assetInfo);
        } catch (IllegalAccessException e) {
            logger.error("获取不到指定类型，{}",e.getMessage(),e);
            return result;
        } catch (InstantiationException e) {
            logger.error("获取不到指定类型，{}",e.getMessage(),e);
            return result;
        } catch (InvocationTargetException e) {
            logger.error("获取不到指定类型，{}",e.getMessage(),e);
            return result;
        } catch (NoSuchMethodException e) {
            logger.error("获取不到指定方法，{}",e.getMessage(),e);
            return result;
        }
        result.setRegistSeqno(null);
        result.setSubmitDeadline(null);
        result.setChbSubmitDeadline(null);
        result.setSubmitTime(null);
        result.setStatusDate(null);
        result.setStatus(null);
        result.setCreateUser(null);
        result.setCreateTime(null);
        result.setUpdateUser(null);
        result.setUpdateTime(null);
        result.setOrgCode(null);
        result.setDeptCode(null);
        result.setSendStatus(null);
        // add by dubaojun 2020-10-31 字段对比时删除该字段 start
        result.setSumitSeq(null);
        // add by dubaojun 2020-10-31 字段对比时删除该字段 end
        // add by zhangliang 行业一级分类不需要报中债  不需要记录都sub表 也不需要比较  start
        result.setIndustryShares(null);
        // add by zhangliang 行业一级分类不需要报中债  不需要记录都sub表 也不需要比较  end
        result.setSubmitFeedbackStatus(null);
        result.setCreateDept(null);
        result.setDataSource(null);
        result.setDeleteRes(null);
        // 设置小数位
        setScale(result);
        return result;
    }
    
    /**
     * 功能:设置小数位
     * @since 1.7.0
     * @version 1.7.0
     * @author：weiyuqiang 2019年6月25日
     * @param dto
     * @return 
     * ChbAssetInfoSub001Dto
     */
    private void setScale(ChbAssetInfoSub001Dto dto){
        // 设置小数位
        if (dto.getAssetYield() != null){
            dto.setAssetYield(dto.getAssetYield().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getFloatFactor() != null){
            dto.setFloatFactor(dto.getFloatFactor().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getTotalFeeRate() != null){
            dto.setTotalFeeRate(dto.getTotalFeeRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getIncomeRatio() != null){
            dto.setIncomeRatio(dto.getIncomeRatio().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getDerInstrRatio() != null){
            dto.setDerInstrRatio(dto.getDerInstrRatio().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getMaxYield() != null){
            dto.setMaxYield(dto.getMaxYield().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getMinYield() != null){
            dto.setMinYield(dto.getMinYield().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getMaxRate() != null){
            dto.setMaxRate(dto.getMaxRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getMinRate() != null){
            dto.setMinRate(dto.getMinRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getTruFeeRate() != null){
            dto.setTruFeeRate(dto.getTruFeeRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getTradeFeeRate() != null){
            dto.setTradeFeeRate(dto.getTradeFeeRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getMgrFeeRate() != null){
            dto.setMgrFeeRate(dto.getMgrFeeRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getAgencyFeeRate() != null){
            dto.setAgencyFeeRate(dto.getAgencyFeeRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getOtherFeeRate() != null){
            dto.setOtherFeeRate(dto.getOtherFeeRate().setScale(5, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getAmt() != null){
            dto.setAmt(dto.getAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getTargetAmt() != null){
            dto.setTargetAmt(dto.getTargetAmt().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (dto.getRemainTerm() != null){
            dto.setRemainTerm(dto.getRemainTerm().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
    }
}
