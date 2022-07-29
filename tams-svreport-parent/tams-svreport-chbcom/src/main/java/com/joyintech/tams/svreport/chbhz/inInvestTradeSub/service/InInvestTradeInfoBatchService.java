package com.joyintech.tams.svreport.chbhz.inInvestTradeSub.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.util.SvrMaptoEntityUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.chbreport.codecheck.service.CommonCodeCheckTmpBatchService;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.ChbInvestTradeSubTmp;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.chb.investtradereg.dao.InvestTradeRegist001Mapper;
import com.joyintech.tams.svreport.chb.investtradereg.dto.InvestTradeRegist001Dto;
import com.joyintech.tams.svreport.chbhz.inInvestTradeSub.dao.InInvestTradeInfoSvrMapper;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;

/**
 * 功能:资产交易登记生成批处理主service
 * 
 * @since 1.0.0
 * @version 1.0.0
 * @作者：zhaoyongsheng @创建时间： @修改记录：
 */
@Service
@Scope("prototype")
@Transactional(rollbackFor = Exception.class)
public class InInvestTradeInfoBatchService extends FileImpService {

	// add by dubaojun 2020-10-12 修改重复性检查的内容 start
	/**
	 * 重复性检查字段
	 */
	private static final String[] DUPLICATIED_LIST = { "PROD_ID", "TRADE_CODE", "ASSET_CODE", "CUR_ASSET_TYPE" };

	/**
	 * 业务主键
	 */
	private static final String BUSSINESSKEY = "TRADE_CODE";
	// add by dubaojun 2020-10-12 修改重复性检查的内容 end
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private SvrMaptoEntityUtil svrMaptoEntityUtil;

	@Autowired
	private InvestTradeRegist001Mapper investTradeRegist001Mapper;

	@Autowired
	private InInvestTradeInfoSvrMapper inInvestTradeInfoSvrMapper;

	@Autowired
	private SqlSessionTemplate template;
	/**
	 * 中债基础数据mapper
	 */
	@Autowired
	private BaseMapper baseMapper;

	// add by dubaojun 2020-10-12 配置无法实现，代码实现的check start
	@Autowired
	private CommonCodeCheckBatchService commonCodeCheckBatchService;
	// add by dubaojun 2020-10-12 配置无法实现，代码实现的check end

	/**
	 * @param
	 * @see
	 * @since 1.0
	 */
	public void generateInvestTradeInfoData(Date batchDate, List<String> prodIdList) {
		logger.debug("资产交易信息登记生成批处理处理开始------" + df.format(new Date()));
		// 1、取得业务数据 新建list<产品交易登记接口表DTO>
		List<ChbInvestTradeSubTmp> listTmp = inInvestTradeInfoSvrMapper
				.genChbInvestTradeSubTmpList(DateUtil.format(batchDate), prodIdList);

		// 2 、校验、生成错误数据、正确数据的数据结构
		// 查询数据库表字段
		List<String> listCloumn = baseMapper.getTableColumnList(investTradeTableName);
		//为后面交易TRADE_TIME
		listCloumn.add("TRADE_DATE");
		// 2.1调用共通 将list<接口表DTO>转换成List<Map<Key, Object>>格式
		List<Map<String, String>> listMap = svrMaptoEntityUtil.convertEntityToMap(listTmp, listCloumn);
		if (CollectionUtils.isNotEmpty(listMap)) {
			listMap.forEach(e->{
				if (e.containsKey("TRADE_TIME")) {
					e.put("TRADE_DATE",e.get("TRADE_TIME"));
					Date tradeTime = DateUtil.parse(e.get("TRADE_TIME"));
				    e.put("TRADE_TIME",DateUtil.format(tradeTime,"yyyyMMddHHmmss"));

				}
			});
		}
		// 2.2调用FileImpService.checkData方法
		// add by dubaojun 2020-10-12 修复文件名为null异常的问题 start
		checkData(true, listMap, investTradeTableName, investTradeTableName, investTradeTableName, BUSSINESSKEY,
				DUPLICATIED_LIST);
		// add by dubaojun 2020-10-12 修复文件名为null异常的问题 end

		// add by dubaojun 2020-10-12 配置无法实现，代码实现的check start
		// commonCodeCheckTmpBatchService.codeCheckTmpCommon(listMap, investTradeTableName, BUSSINESSKEY);
		commonCodeCheckBatchService.codeCheckCommon(listMap, ChbCheckTableInfo.genChbCheckTableInfo(investTradeTableName), null);
		// add by dubaojun 2020-10-12 配置无法实现，代码实现的check end

		// 3、正确数据处理
		// 3.1 对[2]清理掉错误数据的List，调用以下共通，进行数据类型转化处理。将检查后的listMap 转为List<业务表登记表Dto>
		List<InvestTradeRegist001Dto> chbInvestTradeSubList = svrMaptoEntityUtil.convertMapToEntity(listMap, listCloumn,
				InvestTradeRegist001Dto.class);
		listCloumn.remove("TRADE_DATE");

		if (CollectionUtils.isNotEmpty(chbInvestTradeSubList)) {
			chbInvestTradeSubList.forEach(e->{
				e.setTradeTime(DateUtil.parse(e.getTradeDate()));
			});
		}

		// 拼接查询主keylist
		// modify by dubaojun 2020-10-17 业务主键设置不正确修改 start
		List<String> keylist = chbInvestTradeSubList.stream().map(dto -> getBusinessKeyStr(dto))
				.collect(Collectors.toList());
		// modify by dubaojun 2020-10-17 业务主键设置不正确修改 end

		// 3.2.1查询已经在登记表中存在的数据
		// 定义已经在登记表存在的数据
		List<InvestTradeRegist001Dto> chbInvestTradeSubListin = new ArrayList<>();
		chbInvestTradeSubListin = inInvestTradeInfoSvrMapper.genChbInvestTradeSubList(keylist);

		// 3.2.2 将已存在报送中的数据放入map中
		// modify by dubaojun 2020-10-17 业务主键设置不正确修改 start
		Map<String, InvestTradeRegist001Dto> mapSub = chbInvestTradeSubListin.stream().collect(
				Collectors.toMap(dto -> getBusinessKeyStrEX(dto), investTradeRegist001Dto -> investTradeRegist001Dto, (v1, v2) -> v1));
		// modify by dubaojun 2020-10-17 业务主键设置不正确修改 end

		// 报送时点
		Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();

		// 删除数据list
		List<InvestTradeRegist001Dto> deleteList = new ArrayList<InvestTradeRegist001Dto>();
		// 新建数据list
		List<InvestTradeRegist001Dto> insertList = new ArrayList<InvestTradeRegist001Dto>();

		// ]清理掉错误数据的List进行循环处理
		for (InvestTradeRegist001Dto sub : chbInvestTradeSubList) {
			// 登记序列
			sub.setRegistSeqno(IDGenerator.generate(36));
			// 数据日期
			sub.setStatusDate(batchDate);
			// 创建人
			sub.setCreateUser(CommonUtil.getUserName());
			// 更信任
			sub.setUpdateUser(CommonUtil.getUserName());
			// 创建部门
			sub.setCreateDept(CommonUtil.getDeptCode());
			// 创建机构
			sub.setOrgCode(CommonUtil.getOrgCode());
			// 发行机构代码取值
			// sub.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
			// 报送流水号
			sub.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
			//根据数据源规范要求，新增的交易员与交易审批人身份证号字段后6位数据需脱敏为“******”  add by tuqirong 20211223 start
			if (StringUtil.isNotEmpty(sub.getInvestApproverId()) && sub.getInvestApproverId().length() > 6) {
				sub.setInvestApproverId(sub.getInvestTraderId().substring(0, sub.getInvestApproverId().length()-6) + "******");
			}else if (StringUtil.isEmpty(sub.getInvestTraderId())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_资产买入.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_资产卖出或到期.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_存入银行存款.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_银行存款到期.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_拆放同业和买入返售首期.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_同业拆入和卖出回购首期.getValue())) {
				sub.setInvestApproverId("000000");
			}
			if (StringUtil.isNotEmpty(sub.getInvestTraderId()) && sub.getInvestApproverId().length() > 6) {
				sub.setInvestTraderId(sub.getInvestTraderId().substring(0,sub.getInvestApproverId().length()-6) + "******");
			}else if (StringUtil.isEmpty(sub.getInvestTraderId())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_资产买入.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_资产卖出或到期.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_存入银行存款.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_银行存款到期.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_拆放同业和买入返售首期.getValue())
					&&!StringUtil.equals(sub.getCurAssetType(), DictCodeListExt2.资金流动类型_同业拆入和卖出回购首期.getValue())) {
				sub.setInvestTraderId("000000");
			}
			//根据数据源规范要求，新增的交易员与交易审批人身份证号字段后6位数据需脱敏为“******”  add by tuqirong 20211223 end
			// 业务表不存在数据：
			if (!mapSub.containsKey(getBusinessKeyStr(sub))) {
				deleteList.add(sub);
				// 如果当前数据状不等于删除
				if (!StringUtil.equals(DictCodeListExt2.数据状态_删除.getValue(), sub.getStatus())) {
					// 报送状态
					sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
					// 数据状态
					sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
					// 新建数据List.add(此条记录）
					insertList.add(sub);
				}
				// 数据状态等于删除，则不需要插入
			} else if (mapSub.containsKey(getBusinessKeyStr(sub))) {
				// 临时表存在、业务表也存在 的情况下
				InvestTradeRegist001Dto subYw = mapSub.get(getBusinessKeyStr(sub));
				// （业务表中此条记录的.【报送状态】 == “02：接口报送”|| 业务表中此条记录的.【报送状态】 ==
				// “03：手工处理” ）
				if ((StringUtil.equals(DictCodeListExt2.报送状态_接口报送.getValue(), subYw.getSendStatus())
						|| StringUtil.equals(DictCodeListExt2.报送状态_手工处理.getValue(), subYw.getSendStatus()))) {
					// 当已经报送的数据状态为删除时
					if (StringUtil.equals(subYw.getStatus(), DictCodeListExt2.报送数据状态_删除.getValue())) {
						// tmp表数据为删除时
						if (StringUtil.equals(sub.getStatus(), DictCodeListExt2.数据状态_删除.getValue())) {
							// 删除数据List.add(此条记录）
							deleteList.add(sub);
						} else {
							// 删除数据List.add(此条记录）
							deleteList.add(sub);
							sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
							// 报送状态
							sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
							insertList.add(sub);
						}
					} else {
						// 反馈状态为发送失败和中债退回时候
						if (StringUtil.equals(subYw.getSubmitFeedbackStatus(), DictCodeListExt2.报送反馈状态_中债退回.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_发送失败.getValue())) {
							// tmp表数据为删除时
							if (StringUtil.equals(sub.getStatus(), DictCodeListExt2.数据状态_删除.getValue())) {
								// 删除未报送
								deleteList.add(sub);
							}
							// tmp表数据不为删除时
							else {
								// 删除数据List.add(此条记录）删除的是未报送数据
								deleteList.add(sub);
								// 内容不一致进行处理
								if (!compareInvestTradeRegData(subYw, sub)) {
									// 此条记录的【数据状态】= 原来的【数据状态】
									sub.setStatus(subYw.getStatus());
									// 报送状态
									sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
									insertList.add(sub);
								}
							}
						} // 反馈状态为发送成功或中债通过或审核文件下载失败
						else if (StringUtil.equals(subYw.getSubmitFeedbackStatus(),
								DictCodeListExt2.报送反馈状态_发送成功.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_中债通过.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_审核文件下载失败.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_发送中.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_发送异常.getValue())) {
							// tmp表数据为删除时
							if (StringUtil.equals(sub.getStatus(), DictCodeListExt2.数据状态_删除.getValue())) {
								// 删除未报送
								deleteList.add(sub);
								// 新建 数据状态为删除数据状态，未报送数据
								sub.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
								// 报送状态
								sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
								insertList.add(sub);
							}
							// tmp表数据不为删除时
							else {
								// 删除数据List.add(此条记录）删除的是未报送数据
								deleteList.add(sub);
								// 两条记录的所有申报字段比较，存在不一致的情况下，进行以下处理
								if (!compareInvestTradeRegData(subYw, sub)) {
									// 此条记录的【数据状态】= '02:修改'
									sub.setStatus(DictCodeListExt2.报送数据状态_修改.getValue());
									// 报送状态
									sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
									insertList.add(sub);
								}
							}
						}
					}
				} else {
					// 此条记录的【数据状态】= '02:新建'
					sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
					// 报送状态
					sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
					// 删除数据List.add(此条记录）
					// 新建数据List.add(此条记录）
					deleteList.add(sub);
					insertList.add(sub);
				}
			}
			//资产交易登记报送时点:交易登记日期
			/*if (sub.getDealdate() != null) {
				List<Date> dateListUn = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产交易信息登记.getValue(),
						DictCodeListExt2.细分类别_非净值型.getValue(), DictCodeListExt2.报送数据状态_新建.getValue(), dateMap,
						sub.getDealdate());
				Map<String, List<Date>> dateMap1 = new HashMap<String, List<Date>>();
				List<Date> dateListN = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产交易信息登记.getValue(),
						DictCodeListExt2.细分类别_净值型.getValue(), DictCodeListExt2.报送数据状态_新建.getValue(), dateMap1,
						sub.getDealdate());

				// 判斷是否是淨值型產品，设置报送时点
				if (StringUtil.isNotEmpty(sub.getProdType())
						&& (sub.getProdType().equals(DictCodeListExt2.产品类型_中债_净值型产品.getValue())
								|| sub.getProdType().equals(DictCodeListExt2.产品类型_中债_货币型产品.getValue()))) {
					if (CollectionUtils.isNotEmpty(dateListN)) {
						sub.setChbSubmitDeadline(dateListN.get(0));
						sub.setSubmitDeadline(dateListN.get(1));
					}
				} else {
					if (CollectionUtils.isNotEmpty(dateListUn)) {
						sub.setChbSubmitDeadline(dateListUn.get(0));
						sub.setSubmitDeadline(dateListUn.get(1));
					}
				}
			}*/
			if(sub.getDealdate()!=null){ 
                List<Date> dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产交易信息登记.getValue(),
																			sub.getStatus(), dateMap,
													                     sub.getDealdate());
                if (CollectionUtils.isNotEmpty(dateList)) {
                    sub.setChbSubmitDeadline(dateList.get(0));
                    sub.setSubmitDeadline(dateList.get(1));
                }

            }

		}
		logger.debug("资产交易登记生成批处理处理结束------" + df.format(new Date()));
		// 进行入库处理
		logger.debug("资产交易登记入库开始------" + df.format(new Date()));
		dealResultInvestTradeCommitSingle(deleteList, insertList);
		logger.debug("资产交易登记成功入库：" + insertList.size() + "条");
		logger.debug("资产交易登记入库结束------" + df.format(new Date()));

	}

	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dateSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Description 判断要素表中发行登记数据是否有变
	 * @param dto
	 * @param regdto
	 * @return boolean
	 * @version 1.7 2019年5月22日
	 */
	public boolean compareInvestTradeRegData(InvestTradeRegist001Dto dto, InvestTradeRegist001Dto regdto) {
		InvestTradeRegist001Dto compare1 = genInvestTradeDto(dto);
		InvestTradeRegist001Dto compare2 = genInvestTradeDto(regdto);
		if (JSON.toJSONString(compare1,SerializerFeature.WriteNullStringAsEmpty).equals(JSON.toJSONString(compare2,SerializerFeature.WriteNullStringAsEmpty))) {
			return true;
		} else {
			return false;
		}
	}

	private InvestTradeRegist001Dto genInvestTradeDto(InvestTradeRegist001Dto dto) {
		InvestTradeRegist001Dto gendto = new InvestTradeRegist001Dto();
		//行内交易编码
		gendto.setTradeCode(dto.getTradeCode());
		//产品代码
		gendto.setProdId(dto.getProdId());
		//产品登记编码
		gendto.setProdRegistCode(dto.getProdRegistCode());
		//发行机构代码
		gendto.setRegistBankCode(dto.getRegistBankCode());
		//行内资产/负债编码
		gendto.setAssetCode(dto.getAssetCode());
		//币种 参考数据字典：CURRENCY_CHB
		gendto.setCcy(dto.getCcy());
		//折算人民币金额
		gendto.setCnyAmt(
				(dto.getCnyAmt() == null ? BigDecimal.ZERO : dto.getCnyAmt()).setScale(6, BigDecimal.ROUND_HALF_UP));
		//发生金额
		gendto.setAmt((dto.getAmt() == null ? BigDecimal.ZERO : dto.getAmt()).setScale(6, BigDecimal.ROUND_HALF_UP));
		//资金流动类型 01 资产买入/ 02 资产卖出或到期/ 03 存入银行存款/ 04 银行存款到期/ 05拆放同业和买入返售首期/ 06同业拆入和卖出回购首期/ 07拆放同业和买入返售到期/ 08同业拆入和卖出回购到期/09投资资产所得收益/ 10 投资资产支出/ 11 其他收入/ 12 其他支出
		gendto.setCurAssetType(dto.getCurAssetType());
		//资金流动类型说明
		gendto.setCurAssetdetType(dto.getCurAssetdetType());
		//交易日
		gendto.setDealdate(dto.getDealdate());
		//交易对手
		gendto.setTradeCounter(dto.getTradeCounter());
		//单位成交价格（全价）
		gendto.setUnitPriceFull((dto.getUnitPriceFull() == null ? BigDecimal.ZERO : dto.getUnitPriceFull()).setScale(6,
				BigDecimal.ROUND_HALF_UP));
		//单位成交价格（净价）
		gendto.setUnitPriceNet((dto.getUnitPriceNet() == null ? BigDecimal.ZERO : dto.getUnitPriceNet()).setScale(6,
				BigDecimal.ROUND_HALF_UP));
		//到期收益率
		gendto.setEndRate((dto.getEndRate() == null ? BigDecimal.ZERO : dto.getEndRate()).setScale(6,
				BigDecimal.ROUND_HALF_UP));
		//成交编号/合同号
		gendto.setContractCode(dto.getContractCode());
		//资产计量方式
		gendto.setAssetMethod(dto.getAssetMethod());
		//交易对手方类型
		gendto.setRivalPartyType(dto.getRivalPartyType());
		//交易审批人身份证号
		gendto.setInvestApproverId(dto.getInvestApproverId());
		//交易审批人姓名
		gendto.setInvestApproverName(dto.getInvestApproverName());
		//交易员身份证号
		gendto.setInvestTraderId(dto.getInvestTraderId());
		//交易员姓名
		gendto.setInvestTraderName(dto.getInvestTraderName());
		return gendto;
	}

	/**
	 * 功能:带提交的方式单条处理数据结果，入库
	 * 
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年3月7日
	 * @param assetList
	 *            void
	 */
	private void dealResultInvestTradeCommitSingle(List<InvestTradeRegist001Dto> deleteDataList,
			List<InvestTradeRegist001Dto> insertDataList) {
		if (CollectionUtils.isNotEmpty(deleteDataList)) {
			int batchCount = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)
					? "50" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
			int batchLastIndex = batchCount;// 每批最后一个的下标
			for (int index = 0; index < deleteDataList.size();) {
				if (batchLastIndex > deleteDataList.size() - 1) {
					batchLastIndex = deleteDataList.size();
					investTradeRegist001Mapper.deleteByProdid(deleteDataList.subList(index, batchLastIndex));
					break;// 数据插入完毕,退出循环
				} else {
					investTradeRegist001Mapper.deleteByProdid(deleteDataList.subList(index, batchLastIndex));
					index = batchLastIndex;// 设置下一批下标
					batchLastIndex = index + batchCount;
				}
			}
		}
		if (CollectionUtils.isNotEmpty(insertDataList)) {
			int count = 0;
			SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
			try {
				InvestTradeRegist001Mapper batchMapper = session.getMapper(InvestTradeRegist001Mapper.class);
				for (InvestTradeRegist001Dto asset : insertDataList) {
					batchMapper.insert(asset);
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
				logger.error(e.getMessage(), e);
			} finally {
				session.close();
			}
		}
	}

	// add by dubaojun 2020-10-17 业务主键设置不正确修改 start
	/**
	 * 获取业务主键拼接的字符串
	 * 
	 * @param sub
	 *            数据
	 * @return 业务主键
	 */
	private String getBusinessKeyStr(InvestTradeRegist001Dto sub) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(sub.getProdId());
		buffer.append("|");
		buffer.append(sub.getTradeCode());
		buffer.append("|");
		buffer.append(sub.getAssetCode());
		buffer.append("|");
		buffer.append(sub.getCurAssetType());
		return buffer.toString();
	}
	// add by dubaojun 2020-10-17 业务主键设置不正确修改 start
	
	/**
	 * 获取业务主键拼接的字符串[资产代码为空的数据，赋值为null]
	 * add by zhouqiang
	 * @param sub
	 *            数据
	 * @return 业务主键
	 */
	
	private String getBusinessKeyStrEX(InvestTradeRegist001Dto sub) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(sub.getProdId());
		buffer.append("|");
		buffer.append(sub.getTradeCode()==null?"":sub.getTradeCode());
		buffer.append("|");
		if(sub.getAssetCode()==null){
			buffer.append("");
		}else{
			buffer.append(sub.getAssetCode());
		}
		buffer.append("|");
		buffer.append(sub.getCurAssetType());
		return buffer.toString();
	}

}
