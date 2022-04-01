package com.joyintech.tams.svreport.pbc.classic.prdinfoendsubnew.serivce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubnew.dao.PbcPrdBaseinfoEndSubNewMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubnew.dto.PbcPrdBaseinfoEndSubNew;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.util.CollectionUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.DeadlineUtil;
import com.joyintech.tams.svreport.interf.chb.conf.DataSourceType;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsub.dao.PbcPrdBaseinfoEndSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsub.dto.PbcPrdBaseinfoEndSub;
import com.joyintech.tams.svreport.pbc.common.service.PbcClassicsSubService;
import com.joyintech.tams.svreport.position.dto.ChbPrdFeePosition;
import com.joyintech.tams.svreport.position.dto.ChbPrdPosition;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:人行单产品报表-产品终止信息
 * @since 1.7.0
 * @version 1.7.0
 * @作者：jiangxiao
 * @创建时间：2019年6月11日
 * @修改记录：
 */
@Service(value="pbcPrdBaseinfoEndSubNewService")
@Transactional(rollbackFor = Exception.class)
public class PbcPrdInfoEndSubNewService extends PbcClassicsSubService<PbcPrdBaseinfoEndSubNew> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PbcPrdBaseinfoEndSubNewMapper pbcPrdBaseinfoEndSubNewMapper;

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

	@Override
	public void genReport(Date executeDate, List<String> prodIdList) {
		generateRegistData(executeDate, prodIdList);
	}

	public void generateRegistData(Date executeDate, List<String> prodIdList) {
		// 根据跑批日和产品id集合查询所有需要生成登记的产品
		logger.debug("人行产品终止登记所需信息：查询开始----------");
		Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
		List<PbcPrdBaseinfoEndSubNew> prodidsublist = new ArrayList<PbcPrdBaseinfoEndSubNew>();
		if (!CollectionUtils.isEmpty(prodIdList)) {
			int end = 0;
			for (int i = 0; i < prodIdList.size();) {
				end = i + 100;
				if (end > prodIdList.size()) {
					end = prodIdList.size();
				}
				prodidsublist.addAll(pbcPrdBaseinfoEndSubNewMapper.getProdIdsByEndSub(DateUtil.format(executeDate),
						DictCodeListExt2.业务种类_到期兑付.getValue(), prodIdList.subList(i, end)));
				i = end;
			}
		} else {
			prodidsublist = pbcPrdBaseinfoEndSubNewMapper.getProdIdsByEndSub(DateUtil.format(executeDate),
					DictCodeListExt2.业务种类_到期兑付.getValue(), prodIdList);
		}
		if (!CollectionUtils.isEmpty(prodidsublist)) {
			List<List<PbcPrdBaseinfoEndSubNew>> endprods = createList(prodidsublist, 50);
			// 产品基础信息
			List<PbcPrdBaseinfoEndSubNew> prodidlist = new ArrayList<PbcPrdBaseinfoEndSubNew>();
			// 费用头寸信息
			List<ChbPrdFeePosition> feeList = new ArrayList<ChbPrdFeePosition>();
			// 产品交易头寸信息
			List<ChbPrdPosition> dealList = new ArrayList<ChbPrdPosition>();
			// 新增数据
			List<PbcPrdBaseinfoEndSubNew> insertlist = new ArrayList<PbcPrdBaseinfoEndSubNew>();
			// 删除数据
			List<PbcPrdBaseinfoEndSubNew> deletelist = new ArrayList<PbcPrdBaseinfoEndSubNew>();
			// 分批查询
			for (int i = 0; i < endprods.size(); i++) {
				// 查询产品基本信息
				List<PbcPrdBaseinfoEndSubNew> prodinfolist = pbcPrdBaseinfoEndSubNewMapper.getProdInfoByEndSub(endprods.get(i));
				// 查询费用头寸
				List<ChbPrdFeePosition> fee = pbcPrdBaseinfoEndSubNewMapper.getPrdFeePositionList(endprods.get(i));
				// 查询交易头寸
				List<ChbPrdPosition> deal = pbcPrdBaseinfoEndSubNewMapper.getPrdDealPositionList(endprods.get(i));
				prodidlist.addAll(prodinfolist);
				feeList.addAll(fee);
				dealList.addAll(deal);
			}
			logger.debug("人行产品终止登记所需信息：查询结束----------");
			if (!CollectionUtils.isEmpty(prodidlist)) {
				Map<String, PbcPrdBaseinfoEndSubNew> prodidmap = new HashMap<String, PbcPrdBaseinfoEndSubNew>();
				// 判断到期交易是否全是D状态
				for (PbcPrdBaseinfoEndSubNew prodForTerminationDto : prodidlist) {
					if (!DictCodeListExt2.数据状态_删除.getValue().equals(prodForTerminationDto.getStatus())) {
						prodidmap.put(prodForTerminationDto.getProdId(), prodForTerminationDto);
					} else if (prodidmap.get(prodForTerminationDto.getProdId()) == null
							&& DictCodeListExt2.数据状态_删除.getValue().equals(prodForTerminationDto.getStatus())) {
						prodidmap.put(prodForTerminationDto.getProdId(), prodForTerminationDto);
					}
				}
				Iterator<Entry<String, PbcPrdBaseinfoEndSubNew>> iterator = prodidmap.entrySet().iterator();
				logger.debug("人行产品终止登记数据处理：处理开始----------");
				while (iterator.hasNext()) {
					PbcPrdBaseinfoEndSubNew sub = iterator.next().getValue();
					// 临时费用list
					List<ChbPrdFeePosition> listFee = new ArrayList<ChbPrdFeePosition>();
					// 临时交易list
					List<ChbPrdPosition> listDeal = new ArrayList<ChbPrdPosition>();
					// 费用处理
					for (ChbPrdFeePosition chbPrdFeeDeal : feeList) {
						if (sub.getProdId().equals(chbPrdFeeDeal.getProdId())) {
							listFee.add(chbPrdFeeDeal);
						}
					}
					// 交易处理
					for (ChbPrdPosition pos : dealList) {
						if (sub.getProdId().equals(pos.getProdId())) {
							listDeal.add(pos);
						}
					}
					sub.setFeePositionList(listFee);
					sub.setDealPositionList(listDeal);
					// 按产品维度查询产品终止信息map(key:币种)
					Map<String,PbcPrdBaseinfoEndSubNew> regMap = new HashMap<String,PbcPrdBaseinfoEndSubNew>();
					List<PbcPrdBaseinfoEndSubNew> regList = pbcPrdBaseinfoEndSubNewMapper.selectRegList(sub);
					String status = "";
					if(!CollectionUtils.isEmpty(regList)){
						//按照产品、币种维度生成map
						for(PbcPrdBaseinfoEndSubNew reg : regList){
							String key = reg.getCcy();
							regMap.put(key, reg);
							status = reg.getStatus();
						}
					}
					Map<String, PbcPrdBaseinfoEndSubNew> result = dataProcess(sub, executeDate,dateMap);
					// 没有已登记的数据且没有头寸，删除未报送数据
					if(MapUtils.isEmpty(regMap)&&MapUtils.isEmpty(result)){
						deletelist.add(sub);
					}
					// 如果有新建或者修改已登记数据，且生成的数据为空，则生成删除未报送的数据
					if(MapUtils.isNotEmpty(regMap)&&MapUtils.isEmpty(result)){
						for(String key : regMap.keySet()){
							if(!DictCodeListExt2.报送数据状态_删除.getValue().equals(regMap.get(key).getStatus())){
								regMap.get(key).setRegistSeqno(IDGenerator.generate(36));
								regMap.get(key).setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
								regMap.get(key).setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
								regMap.get(key).setSubmitFeedbackStatus(null);
								deletelist.add(regMap.get(key));
								insertlist.add(regMap.get(key));
							}
						}
					}
					// 比较数据
					boolean isupdate = compareMap(regMap,result);
					Iterator<Entry<String, PbcPrdBaseinfoEndSubNew>> itera = result.entrySet().iterator();
					// 循环生成的数据
					while (itera.hasNext()) {
						PbcPrdBaseinfoEndSubNew res = itera.next().getValue();
//						String key = res.getCcy();
						boolean iszero = false;
	                	if(!CollectionUtils.isEmpty(listDeal)){
	                		//需求调整为老人行不变.新人行不再判断是否1002科目归零   leipeng 20210805
							iszero = true;
//	                		iszero = isZeroBalance(listDeal.get(0).getCdate(), res.getProdId());
	                	}
						if (CollectionUtils.isEmpty(regMap.keySet())) {// 没有已报送的
							// 若业务数据状态为删除则不生成登记数据
							if (DictCodeListExt2.数据状态_删除.getValue().equals(sub.getStatus())) {
								// 删除数据(未报送的删除)
								deletelist.add(res);
								continue;
							} else {
								// 判断产品持仓,剩余现金是否为零
	                        	if(iszero){
			                        //设置数据状态
	                        		res.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
	                        		insertlist.add(res);
	                        		// 删除数据(未报送的删除)
	                        		deletelist.add(res);
	                        	}
								
							}
						} else {// 已有已报送的
							// 删除数据(未报送的删除)
							deletelist.add(res);
							if(iszero){
								// 业务数据状态为删除时，若报送数据状态为删除则不生成登记，否则生成删除未报送
								if (DictCodeListExt2.数据状态_删除.getValue().equals(sub.getStatus())) {
									if(DictCodeListExt2.报送数据状态_删除.getValue().equals(status)){
										continue;
									}
									res.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
								} else {// 业务数据状态为有效或者修改
									// 若报送数据状态为删除，则生成新建未报送
									if (DictCodeListExt2.报送数据状态_删除.getValue().equals(status)) {
										res.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
									} else if(isupdate){// 若报送数据状态为有效或者修改，判断要素表数据有没有修改，若修改生成修改未报送
										continue;
									}else{
										res.setStatus(DictCodeListExt2.报送数据状态_修改.getValue());
									}
								}
								// 根据数据状态重新生成截止日期
								List<Date> dateList = DeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_新人行产品终止信息.getValue(),
										res.getStatus(), dateMap,
										res.getMdate(),SVReportGlobalConstant.SVR_SUBMIT_TIME_PBC);
								if (!StringUtil.isEmptyList(dateList)) {
									res.setPbcSubmitDeadline(dateList.get(0));
									res.setSubmitDeadline(dateList.get(1));
								}
								insertlist.add(res);
							}
						}
					}
				}
			}
			logger.debug("人行产品终止登记数据处理：处理结束----------");
			// 入库
			intoDatabase(insertlist, deletelist);
		}
	}
	 /**
     * @Description 判断产品持仓是否为零
     * @param mdate
     * @param prodid
     * @return boolean
     * @version 1.7 2019年7月2日
     */
    private boolean isZeroBalance(Date cdate,String prodid){
    	BigDecimal pos = BigDecimal.ZERO;
//    	pos = prodTerminationRegist001Mapper.isAssetPosZero(mdate,prodid);
    	   // 通过系统参数判断是查询一体化还是中债子系统
        String isSubSystem = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM);
        PbcPrdInfoEndSubNewService pbcPrdInfoEndSubService =  SpringContextUtil.getBean(PbcPrdInfoEndSubNewService.class);
        if (DictCodeList.是否_中债_是.getValue().equals(isSubSystem)){
        	pos=pbcPrdInfoEndSubService.queryBookBalanceLevel1SubSystem(cdate,prodid);
        }else if (DictCodeList.是否_中债_否.getValue().equals(isSubSystem)){
        	pos=pbcPrdInfoEndSubService.queryBookBalanceLevel1(cdate,prodid);
        }else{
        	 logger.error("系统参数中：SVR_IS_SUB_SYSTEM配置错误！");
             return false;
        }
    	if(pos!=null&&pos.compareTo(BigDecimal.ZERO)==0){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * 功能:查询接入日当天的资产头寸-科目余额部分
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param interfDate 跑批日
     * @prodList 产品代码
     * @return 
     * List<ChbAssetInfo>
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    // 切换数据源
    @DataSource(DataSourceType.INTERF)
    public BigDecimal queryBookBalanceLevel1(Date cdate,String prodid){
    	return pbcPrdBaseinfoEndSubNewMapper.queryBookBalanceLevel1(cdate,prodid);
    }

    /**
     * 功能:查询接入日当天的资产头寸-科目余额部分-中债子系统
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年3月11日
     * @param interfDate 跑批日
     * @prodList 产品代码
     * @return 
     * List<ChbAssetInfo>
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public BigDecimal queryBookBalanceLevel1SubSystem(Date cdate,String prodid){
    	return pbcPrdBaseinfoEndSubNewMapper.queryBookBalanceLevel1SubSystem(cdate,prodid);
    }
	private void intoDatabase(List<PbcPrdBaseinfoEndSubNew> insertlist, List<PbcPrdBaseinfoEndSubNew> deletelist) {
		// 插入前先删除未报送的登记数据
		logger.debug("人行产品终止登记删除未报送数据：删除开始----------");
		// 数据批量一次删除条数,如果参数设置过大则强制定位50
		int batchDeleteCount = reportSelectOrDeleteCount;
		// 每批最后一个的下标
		int batchDeleteLastIndex = batchDeleteCount;
		for (int index = 0; index < deletelist.size();) {
			if (batchDeleteLastIndex > deletelist.size() - 1) {
				batchDeleteLastIndex = deletelist.size();
				pbcPrdBaseinfoEndSubNewMapper.deleteBatch(deletelist.subList(index, batchDeleteLastIndex));
				break;// 数据插入完毕,退出循环
			} else {
				pbcPrdBaseinfoEndSubNewMapper.deleteBatch(deletelist.subList(index, batchDeleteLastIndex));
				index = batchDeleteLastIndex;// 设置下一批下标
				batchDeleteLastIndex = index + batchDeleteCount;
			}
		}
		logger.debug("人行产品终止登记删除未报送数据：删除结束----------");
		logger.debug("人行产品终止登记插入数据：插入开始----------");
		int count = 0;
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			PbcPrdBaseinfoEndSubNewMapper batchMapper = session.getMapper(PbcPrdBaseinfoEndSubNewMapper.class);
			for (PbcPrdBaseinfoEndSubNew dto : insertlist) {
				batchMapper.insert(dto);
				count++;
				if (count % 1000 == 0) {
					session.commit();
					// 清理缓存，防止溢出
					session.clearCache();
				}
			}
			session.commit();
			// 清理缓存，防止溢出
			session.clearCache();
		} catch (Exception e) {
			session.rollback();
			logger.error("人行产品终止登记入库报错:" + e.getMessage(), e);
		} finally {
			session.close();
		}
		logger.debug("人行产品终止登记插入数据：插入结束----------");
	}

	private Map<String, PbcPrdBaseinfoEndSubNew> dataProcess(PbcPrdBaseinfoEndSubNew arg0, Date executeDate,Map<String, List<Date>> dateMap) {
		// 币种Map
		Map<String, PbcPrdBaseinfoEndSubNew> result = new HashMap<String, PbcPrdBaseinfoEndSubNew>();
		// 产品交易头寸
		List<ChbPrdPosition> dealpos = arg0.getDealPositionList();
		// 产品费用头寸
		List<ChbPrdFeePosition> feepos = arg0.getFeePositionList();
		// 汇率来源
		String exchangeSource = CacheUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE_PBC);
		// 汇率Map
		Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
		// 兑付客户收益
		BigDecimal customProfit = BigDecimal.ZERO;
		// 实际募集金额
		BigDecimal raiseAmt = BigDecimal.ZERO;
		// 取产品头寸的币种Map
		if (!CollectionUtils.isEmpty(dealpos)) {
			for (ChbPrdPosition pos : dealpos) {
				// 兑付客户收益
				customProfit = customProfit
						.add(ChbCcyConvertUtil.exchangeMoney(pos.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(),
								pos.getCdate(), pos.getTotalIntamt(), rateMap, exchangeSource));
				// 实际募集金额
				raiseAmt = raiseAmt
						.add(ChbCcyConvertUtil.exchangeMoney(pos.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(),
								pos.getCdate(), pos.getRaiseamt(), rateMap, exchangeSource));
				if (result.get(pos.getCcy()) == null) {
					PbcPrdBaseinfoEndSubNew res = new PbcPrdBaseinfoEndSubNew();
					List<ChbPrdPosition> deal = new ArrayList<ChbPrdPosition>();
					deal.add(pos);
					res.setDealPositionList(deal);
					result.put(pos.getCcy(), res);
				} else {
					result.get(pos.getCcy()).getDealPositionList().add(pos);
				}
			}
		}
		// 取费用头寸的币种Map
		if (!CollectionUtils.isEmpty(feepos)) {
			for (ChbPrdFeePosition pos : feepos) {
				if (result.get(pos.getCcy()) == null) {
					PbcPrdBaseinfoEndSubNew res = new PbcPrdBaseinfoEndSubNew();
					List<ChbPrdFeePosition> deal = new ArrayList<ChbPrdFeePosition>();
					deal.add(pos);
					res.setFeePositionList(deal);
					result.put(pos.getCcy(), res);
				} else {
					if (!CollectionUtils.isEmpty(result.get(pos.getCcy()).getFeePositionList())) {
						result.get(pos.getCcy()).getFeePositionList().add(pos);
					} else {
						List<ChbPrdFeePosition> deal = new ArrayList<ChbPrdFeePosition>();
						deal.add(pos);
						result.get(pos.getCcy()).setFeePositionList(deal);
					}
				}
			}
		}
		// 计算兑付客户收益率
		BigDecimal cusYearYield = BigDecimal.ZERO;
		if (raiseAmt.compareTo(BigDecimal.ZERO) != 0) {
			/**
			 * 客户实际年化收益率=兑付客户收益÷实际募集金额×全年实际天数÷产品实际运作天数
			 */
			BigDecimal basic = new BigDecimal(365);
			if (customProfit.compareTo(BigDecimal.ZERO) != 0) {
				if (DictCodeList.计息基础_A_360.getValue().equals(arg0.getBasic())) {
					basic = new BigDecimal(360);
				}
				cusYearYield = customProfit.multiply(basic)
						.divide(raiseAmt.multiply(
								new BigDecimal(DateUtil.getBetweenDayNumber(arg0.getVdate(), arg0.getMdate()))), 7,
								BigDecimal.ROUND_HALF_UP);
			}
		}
		// 报送截止日生成
		// 当前批次报送时点集合
		List<Date> dateList = DeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_新人行产品终止信息.getValue(),
                DictCodeListExt2.报送数据状态_新建.getValue(), dateMap,
                arg0.getMdate(),SVReportGlobalConstant.SVR_SUBMIT_TIME_PBC);
		// 循环币种Map(同一产品按币种维度出值)
		Iterator<Entry<String, PbcPrdBaseinfoEndSubNew>> iterator = result.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, PbcPrdBaseinfoEndSubNew> entry = iterator.next();
			PbcPrdBaseinfoEndSubNew sub = entry.getValue();
			String ccy = entry.getKey();
			// 发行机构实现收入
			BigDecimal profit = BigDecimal.ZERO;
			// 发行机构实现收入折人民币
			BigDecimal profitCny = null;
			// 兑付客户收益
			BigDecimal customAmt = BigDecimal.ZERO;
			// 兑付客户收益折人民币
			BigDecimal customAmtCny = null;
			if (!CollectionUtils.isEmpty(sub.getDealPositionList())) {
				for (ChbPrdPosition dto : sub.getDealPositionList()) {
					customAmt = customAmt.add(dto.getTotalIntamt());
				}
			}
			if (!CollectionUtils.isEmpty(sub.getFeePositionList())) {
				for (ChbPrdFeePosition dto : sub.getFeePositionList()) {
					profit = profit.add(dto.getAmt());
				}
			}
			if (!DictCodeListExt1.币种_中债_人民币.getValue().equals(ccy)) {
				profitCny = ChbCcyConvertUtil.exchangeMoney(ccy, DictCodeListExt1.币种_中债_人民币.getValue(), arg0.getMdate(),
						profit, rateMap, exchangeSource);
				customAmtCny = ChbCcyConvertUtil.exchangeMoney(ccy, DictCodeListExt1.币种_中债_人民币.getValue(),
						arg0.getMdate(), customAmt, rateMap, exchangeSource);
			}else{
				//新人行要求折人民币不为空,设置为与人民币相同
				customAmtCny = customAmt;
			}
			// 兑付客户收益
			sub.setCustomProfit(customAmt);
			// 兑付客户收益折人民币
			sub.setCustomProfitCny(customAmtCny);
			// 兑付客户收益率
			sub.setCusYearYield(cusYearYield.multiply(new BigDecimal(100)));
			// 发行机构实现收入
			sub.setProfit(profit);
			// 发行机构实现收入折人民币
			sub.setProfitCny(profitCny);
			// 人行产品代码
			sub.setPbcProdId(arg0.getPbcProdId());
			// 产品代码
			sub.setProdId(arg0.getProdId());
			// 产品实际终止日
			sub.setMdate(arg0.getMdate());
			// 币种
			sub.setCcy(ccy);
			// 报送状态
			sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
			// 主键
			sub.setRegistSeqno(IDGenerator.generate(36));
			// 数据日期
			sub.setStatusDate(executeDate);
			// 创建人
			sub.setCreateUser(CommonUtil.getUserName());
			// 更新人
			sub.setUpdateUser(CommonUtil.getUserName());
			// 创建部门
			sub.setCreateDept(CommonUtil.getDeptCode());
			// 机构代码
			sub.setOrgCode(CommonUtil.getOrgCode());
			if (!StringUtil.isEmptyList(dateList)) {
				sub.setPbcSubmitDeadline(dateList.get(0));
				sub.setSubmitDeadline(dateList.get(1));
			}
		}
		return result;
	}

	private List<List<PbcPrdBaseinfoEndSubNew>> createList(List<PbcPrdBaseinfoEndSubNew> targe, int size) {
		List<List<PbcPrdBaseinfoEndSubNew>> listArr = new ArrayList<List<PbcPrdBaseinfoEndSubNew>>();
		// 获取被拆分的数组个数
		int arrSize = targe.size() % size == 0 ? targe.size() / size : targe.size() / size + 1;
		for (int i = 0; i < arrSize; i++) {
			List<PbcPrdBaseinfoEndSubNew> sub = new ArrayList<PbcPrdBaseinfoEndSubNew>();
			// 把指定索引数据放入到list中
			for (int j = i * size; j <= size * (i + 1) - 1; j++) {
				if (j <= targe.size() - 1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(sub);
		}
		return listArr;
	}
	
	private boolean compareMap(Map<String, PbcPrdBaseinfoEndSubNew> regMap, Map<String, PbcPrdBaseinfoEndSubNew> result) {
		if(MapUtils.isEmpty(regMap)||MapUtils.isEmpty(result)){
			return false;
		}
		if(regMap.size()!=result.size()){
			return false;
		}
		Iterator<Entry<String, PbcPrdBaseinfoEndSubNew>> iterator = regMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, PbcPrdBaseinfoEndSubNew> entry = iterator.next();
			PbcPrdBaseinfoEndSubNew reg = entry.getValue();
			String key = entry.getKey();
			if(result.get(key)==null||!compareData(reg,result.get(key))){
				return false;
			}
		}
		return true;
	}
	
	private boolean compareData(PbcPrdBaseinfoEndSubNew dto,PbcPrdBaseinfoEndSubNew regdto){
		PbcPrdBaseinfoEndSubNew compare1 = genDto(dto);
		PbcPrdBaseinfoEndSubNew compare2 = genDto(regdto);
        if(JSON.toJSONString(compare1).equals(JSON.toJSONString(compare2))){
            return true;
        }else{
            return false;
        }
    }

	private PbcPrdBaseinfoEndSubNew genDto(PbcPrdBaseinfoEndSubNew dto) {
		PbcPrdBaseinfoEndSubNew reg = new PbcPrdBaseinfoEndSubNew();
		reg.setPbcProdId(dto.getPbcProdId());
		reg.setMdate(dto.getMdate());
		reg.setCcy(dto.getCcy());
		reg.setProfit((dto.getProfit()==null?BigDecimal.ZERO:dto.getProfit()).setScale(2, BigDecimal.ROUND_HALF_UP));
		reg.setProfitCny((dto.getProfitCny()==null?BigDecimal.ZERO:dto.getProfitCny()).setScale(2,BigDecimal.ROUND_HALF_UP));
		reg.setCustomProfit((dto.getCustomProfit()==null?BigDecimal.ZERO:dto.getCustomProfit()).setScale(2, BigDecimal.ROUND_HALF_UP));
		reg.setCustomProfitCny((dto.getCustomProfitCny()==null?BigDecimal.ZERO:dto.getCustomProfitCny()).setScale(2, BigDecimal.ROUND_HALF_UP));
		reg.setCusYearYield((dto.getCusYearYield()==null?BigDecimal.ZERO:dto.getCusYearYield()).setScale(12,BigDecimal.ROUND_HALF_UP));
		return reg;
	}
}
