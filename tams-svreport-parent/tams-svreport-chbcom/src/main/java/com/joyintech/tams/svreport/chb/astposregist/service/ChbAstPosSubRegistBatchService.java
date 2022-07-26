package com.joyintech.tams.svreport.chb.astposregist.service;

import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.batch.report.autosendcb.common.service.SvrCBBaseService;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbAstPosSub;
import com.joyintech.tams.common.base.entity.ChbPrdStatusSub;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.util.SvrMaptoEntityUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.report.interf.common.constant.InterfMappingConstants;
import com.joyintech.tams.svreport.chb.astposregist.dao.ChbAstPosRegist001Mapper;
import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosBatchResultDto;
import com.joyintech.tams.svreport.chb.datacheck.beforeinsert.service.ChbCommonCheckService;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubErrorCheck;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能:资产持仓登记批处理
 * @作者：dxy
 * @创建时间：20220530
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbAstPosSubRegistBatchService extends SvrCBBaseService{

	/**
	 * 长sql最大一次插入或者更新条数
	 */
	@Value("${application.reportInsertLongCount}")
	public String reportInsertLongCount;

	@Autowired
	private SvrMaptoEntityUtil svrMaptoEntityUtil;

	/**
	 * 中债基础数据mapper
	 */
	@Autowired
	private BaseMapper baseMapper;

    @Autowired
    private ChbAstPosRegist001Mapper chbAstPosRegist001Mapper;
	/**
	 * 映射配置mapper
	 */
	@Autowired
	private ChbTypeMappingExMapper chbTypeMappingExMapper;

	@Autowired
	private BeforeSubErrorCheck beforeSubErrorCheck;

	/**
	 * 入库前校验用Service
	 */
	@Autowired
	private ChbCommonCheckService chbCommonCheckService;


	private Logger logger = LoggerFactory.getLogger(getClass());

	public void createRegist(Date batchTime){
        // 设置批处理bean
        setBatchBeanName(BatchBeanName.资产持仓信息登记报送);

        logInfoBatchStart();

        createRegistAction(batchTime);
		logInfoBatchEnd();
	}

	/**
	 * 资产持仓登记批处理生成逻辑
	 * @param batchTime
	 */
	private void createRegistAction(Date batchTime){

		//1.判断跑批日期，如果不是1号，批处理不执行。
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(batchTime);
		if ( calendar.get(Calendar.DATE)!=1 ) {  //每月1日生成上月的数据
			return;
		}
        //否则从估值表和余额表获取对象日期：较小值（指标日期，余额日期）
		Date  objecDate=chbAstPosRegist001Mapper.genExcuteDate(DateUtil.format(batchTime));

		//2.a 获取直投的科目配置
		List<ChbTypeMappingEx>  subjectNoMappingList = chbTypeMappingExMapper
				.selectByMappingcode(InterfMappingConstants.SUBJECT_INVEST_TYPE_NAME);
		Map<String,ChbTypeMappingEx>  mapsubject=subjectNoMappingList.stream().collect(
				JoyinCollectors.toMap(x -> x.getSrcfield1(), x -> x));
		//2.b 获取底层的科目配置
		List<ChbTypeMappingEx>  boSubjectNoMappingList = chbTypeMappingExMapper
				.selectByMappingcode(InterfMappingConstants.BOTTON_SUBJECT_INVEST_TYPE_NAME);
		Map<String,ChbTypeMappingEx>  mapbosubject=boSubjectNoMappingList.stream().collect(
				JoyinCollectors.toMap(x -> x.getSrcfield1(), x -> x));
		//2.c 获取资产类型和品种配置
		List<ChbTypeMappingEx>  assetTypeMappingList = chbTypeMappingExMapper
				.selectByMappingcode(InterfMappingConstants.ASSET_TYPE2_CATA_TYPE);
		Map<String,ChbTypeMappingEx>  mapAssetType=assetTypeMappingList.stream().collect(
				JoyinCollectors.toMap(x -> x.getSrcfield1(), x -> x));

		//3.1 判断是否有已报送的月报
		if(CollectionUtils.isNotEmpty(chbAstPosRegist001Mapper.selectsubmitRecodeList(DateUtil.format(objecDate)))){
			logger.info("【已报送的月报，不再生成报送数据】");
			return;
		}
		//3.2 清除历史数据
		deleteMonthReport(objecDate);

        /**【4】.基础数据获得  start*/
        //4.1 从资产基础指标数据
		List<ChbAstPosBatchResultDto> listbasisdata=chbAstPosRegist001Mapper.selectAssetBasiscData(DateUtil.format(objecDate));

		//4.2 从科目余额取值
		List<ChbAstPosBatchResultDto> listbalancedata=chbAstPosRegist001Mapper.selectBalanceData(DateUtil.format(objecDate));

		//4.3 从底层资产取值
		List<ChbAstPosBatchResultDto> listbotdata=chbAstPosRegist001Mapper.selectBotAstData(DateUtil.format(objecDate));

		/**华夏个性化 新增查询还本计划逻辑  其他版本看情况合并 start*/
		//债券还本信息
//		List<ChbSecRepayInfoDto> secRepayInfoList = chbAstPosRegist001Mapper.selectSecRepayInfo(DateUtil.format(objecDate));
//		Map<String, ChbSecRepayInfoDto> secRepayInfoMap=new HashMap<>();
//		if(CollectionUtils.isNotEmpty(secRepayInfoList)){
//		 secRepayInfoMap = secRepayInfoList.stream().collect(
//					JoyinCollectors.toMap(x -> x.getSecId(), x -> x));
//		}
		/**华夏个性化 新增查询还本计划逻辑  其他版本看情况合并 end*/

		/**【4】.基础数据获得  end*/

		/**【5】.生成资产持仓登记  start*/
		//资产持仓登记
		List<ChbAstPosSub>       listsposub=new ArrayList<>();
		//产品状态登记
		List<ChbPrdStatusSub>    listprdsub=new ArrayList<>();
		//5.1循环资产基础指标数据
		Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
		String exchangeSource = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE);
		//穿透的资产基础指标基础数据，下面要用。
		List<ChbAstPosBatchResultDto> listbasisdataCt=new ArrayList<>();
		if(CollectionUtils.isNotEmpty(listbasisdata)){
		for(ChbAstPosBatchResultDto  dto:listbasisdata){
			ChbAstPosSub  sub=new ChbAstPosSub();
			//登记序列
			sub.setRegistSeqno(IDGenerator.generate(36));
			//期数
			sub.setTermid(DateUtil.format(objecDate,DateUtil.MONTH_OTHER_FORMAT));
			//产品登记编码
			sub.setPrdRegistCode(dto.getPrdRegistCode());
			//产品代码
			sub.setProdId(dto.getProdId());
			//持仓类别
			sub.setPosType(DictCodeListExt3.中债持仓类型_登记系统资产.getValue());
			//行内资产/负债编码
			sub.setAssetId(dto.getAssetId());
            //中间层层数
			sub.setInterLeveNo("0");
            //金额=（当日成本+当日估值增值+当日累计应计利息余额-减值准备）
			sub.setAmt(ArithmeticUtil.subtract(ArithmeticUtil.doAdd(dto.getTdyCostAmt(),dto.getTdyFloatIngpl(),
					dto.getTdyIntincexp()),dto.getTdyLossAmt()).setScale(2, RoundingMode.HALF_UP));
			//公允价值
			sub.setFloatIngpl(ArithmeticUtil.subtract(ArithmeticUtil.doAdd(dto.getTdyCostAmt(),dto.getTdyFloatIngpl(),
					dto.getTdyIntincexp(),dto.getShadowAmt()),dto.getTdyLossAmt()).setScale(2, RoundingMode.HALF_UP));
			//折算人名币金额
			BigDecimal chaAmt = ChbCcyConvertUtil.exchangeMoney(dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, sub.getAmt(), rateMap, exchangeSource);
			sub.setCnyAmt(chaAmt.setScale(2, RoundingMode.HALF_UP));
			//折算人民币公允价值
			BigDecimal chfAmt = ChbCcyConvertUtil.exchangeMoney(dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, sub.getFloatIngpl(), rateMap, exchangeSource);
			sub.setCnyFloatIngpl(chfAmt.setScale(2, RoundingMode.HALF_UP));

			if(mapAssetType.containsKey(dto.getAssetType2())){
				if("15".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())
						||"16".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())){
                    //持仓类别= 03 其它系统负债
					sub.setPosType(DictCodeListExt3.中债持仓类型_登记系统负债.getValue());
				}
				if("12".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())
						||"13".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())
						||"14".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())){
					//资产穿透情况
					sub.setInterAssetType(DictCodeListExt3.资产穿透情况_中间层资产首层.getValue());
					listbasisdataCt.add(dto);
				}
				if(!"12".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())
						&&!"13".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())
						&&!"14".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())){
					sub.setInterAssetType(DictCodeListExt3.资产穿透情况_最底层资产.getValue());
				}
//				//华夏个性化
//				if("4".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())){
//					//华夏个性化，如果资产是债券的话，且有还本计划，则数量=当前持仓*面值/100
//					if(secRepayInfoMap!=null&&secRepayInfoMap.containsKey(dto.getAssetId())){
//						sub.setQty(dto.getPosAmt().multiply(ArithmeticUtil.divide(secRepayInfoMap.get(dto.getAssetId()).getRepayNet(),new BigDecimal(100),14)).setScale(5, RoundingMode.HALF_UP));
//					}else{
//						sub.setQty(dto.getPosAmt().multiply(ArithmeticUtil.divide(new BigDecimal(100),new BigDecimal(100),14)).setScale(5, RoundingMode.HALF_UP));
//					}
//				}else{
						//数量=当前持仓
						sub.setQty(dto.getPosAmt().setScale(5, RoundingMode.HALF_UP));
//				}
				if(DictCodeListExt3.资产持仓品种_金融衍生品.getValue().equals(mapAssetType.get(dto.getAssetType2()).getObjfield2())){
					//单位净价: 当日估值增值
					sub.setUnitValueCprice(dto.getTdyFloatIngpl());
					//单位全价:当日估值增值
					sub.setUnitValueFprice(dto.getTdyFloatIngpl());

				}
				if(DictCodeListExt3.资产持仓品种_金融衍生品2.getValue().equals(mapAssetType.get(dto.getAssetType2()).getObjfield2())){
					//单位净价: 累计应计利息
					sub.setUnitValueCprice(dto.getTdyFloatIngpl());
					//单位全价: 累计应计利息
					sub.setUnitValueFprice(dto.getTdyFloatIngpl());
				}
				if(DictCodeListExt3.资产持仓品种_净值型资产.getValue().equals(mapAssetType.get(dto.getAssetType2()).getObjfield2())||
						("12".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_否.getValue().equals(dto.getRateFlag()))||
						("13".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_否.getValue().equals(dto.getRateFlag()))){
					//单位净价=(（当日成本+当日估值增值）/当前持仓)
					sub.setUnitValueCprice(ArithmeticUtil.divide(ArithmeticUtil.doAdd(dto.getTdyCostAmt(),dto.getTdyFloatIngpl()),dto.getPosAmt(),14));
					//单位全价=(（（当日成本+当日估值增值+当日累计应计利息余额）/当前持仓)
					sub.setUnitValueFprice(ArithmeticUtil.divide(ArithmeticUtil.doAdd(dto.getTdyCostAmt(),dto.getTdyFloatIngpl(),dto.getTdyIntincexp()),dto.getPosAmt(),14));

				}
				if(DictCodeListExt3.资产持仓品种_预期收益型资产.getValue().equals(mapAssetType.get(dto.getAssetType2()).getObjfield2())||
						("12".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_是.getValue().equals(dto.getRateFlag()))||
						("13".equals(mapAssetType.get(dto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_是.getValue().equals(dto.getRateFlag()))){
						//单位净价=100 *（当日成本+当日估值增值 - 减值准备）/当前持仓
						sub.setUnitValueCprice(ArithmeticUtil.divide(ArithmeticUtil.subtract(ArithmeticUtil.doAdd(dto.getTdyCostAmt(),dto.getTdyFloatIngpl()),dto.getTdyLossAmt()),dto.getPosAmt(),14).multiply(new BigDecimal(100)));
						//单位全价=100 * （当日成本+当日估值增值+当日累计应计利息余额  - 减值准备）/当前持仓
						sub.setUnitValueFprice(ArithmeticUtil.divide(ArithmeticUtil.subtract(ArithmeticUtil.doAdd(dto.getTdyCostAmt(),dto.getTdyFloatIngpl(),dto.getTdyIntincexp()),dto.getTdyLossAmt()),dto.getPosAmt(),14).multiply(new BigDecimal(100)));
				}
			}
			//计算折算人名币值
			BigDecimal chaUnitValueCprice = ChbCcyConvertUtil.exchangeMoney(dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, sub.getUnitValueCprice(), rateMap, exchangeSource);
			BigDecimal chaUnitValueFprice = ChbCcyConvertUtil.exchangeMoney(dto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, sub.getUnitValueFprice(), rateMap, exchangeSource);
			sub.setUnitValueCprice(chaUnitValueCprice.setScale(4, RoundingMode.HALF_UP));
			sub.setUnitValueFprice(chaUnitValueFprice.setScale(4, RoundingMode.HALF_UP));



			//币种
			sub.setCcy(dto.getCcy());
			//共通赋值
			initVlueBody(sub,objecDate);
			listsposub.add(sub);
		}
		}

		//5.2循环科目余额数据
		if(CollectionUtils.isNotEmpty(listbalancedata)){
			//过滤掉不在CHB_TYPE_MAPPING_EX配置表的科目
			listbalancedata=listbalancedata.stream().filter(s -> mapsubject.containsKey(s.getSubjectNo())).collect(Collectors.toList());
			for(ChbAstPosBatchResultDto  bato:listbalancedata){
				ChbAstPosSub  sub=new ChbAstPosSub();
				//登记序列
				sub.setRegistSeqno(IDGenerator.generate(36));
				//期数
				sub.setTermid(DateUtil.format(objecDate,DateUtil.MONTH_OTHER_FORMAT));
				//产品登记编码
				sub.setPrdRegistCode(bato.getPrdRegistCode());
				//产品代码
				sub.setProdId(bato.getProdId());
				//持仓类别=配置记录的持仓类别
				sub.setPosType(mapsubject.get(bato.getSubjectNo()).getObjfield2());
				//中间层层数
				sub.setInterLeveNo("0");
				//资产穿透情况=03 ： 最底层资产
				sub.setInterAssetType(DictCodeListExt3.资产穿透情况_最底层资产.getValue());
                //会计科目名称
				sub.setSubjectName(mapsubject.get(bato.getSubjectNo()).getObjfield3());
				//金额=原币余额
				sub.setAmt(bato.getoAmt().setScale(2, RoundingMode.HALF_UP));
                //折算人名币金额
				BigDecimal choAmt = ChbCcyConvertUtil.exchangeMoney(bato.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, sub.getAmt(), rateMap, exchangeSource);
				sub.setCnyAmt(choAmt.setScale(2, RoundingMode.HALF_UP));
				//币种
				sub.setCcy(bato.getCcy());
				//共通赋值
				initVlueBody(sub,objecDate);
				listsposub.add(sub);
			}
		}
		//5.3 底层资产持仓信息来源
       if(CollectionUtils.isNotEmpty(listbasisdataCt)&&
			   CollectionUtils.isNotEmpty(listbotdata)){
       	//a. 对资产基础指标数据按照资管代码进行分组
	    Map<String,List<ChbAstPosBatchResultDto>>  botMap=listbasisdataCt.stream().collect(
				JoyinCollectors.groupingBy(ChbAstPosBatchResultDto::getAssetId));
	    //b. 对底层资产持仓数据进行分组
		   Map<String,List<ChbAstPosBatchResultDto>>  dbotMap=listbotdata.stream().collect(
				   JoyinCollectors.groupingBy(ChbAstPosBatchResultDto::getPlanAssetId));
		//循环a的map
        for(String key:botMap.keySet()){
        List<ChbAstPosBatchResultDto> prdList=botMap.get(key);
		//循环各产品记录
        for(ChbAstPosBatchResultDto sdto:prdList){
            //获取底层资产持仓信息首条记录
			List<ChbAstPosBatchResultDto> byprdList=dbotMap.get(key);
			//如果没取到底层数据，则直接跳过
			if(CollectionUtils.isEmpty(byprdList)){
				continue;
			}
			ChbAstPosBatchResultDto sbodto=byprdList.get(0);
			//折算率=明细记录的.当前持仓 /首条记录的总发行数量
			BigDecimal  crate=ArithmeticUtil.divide(sdto.getPosAmt(),sbodto.getTotalNum(),14);
             //针对未投资头寸，做成登记记录
		     ChbAstPosSub  sub=new ChbAstPosSub();
			//登记序列
			sub.setRegistSeqno(IDGenerator.generate(36));
			//期数
			sub.setTermid(DateUtil.format(objecDate,DateUtil.MONTH_OTHER_FORMAT));
			//产品登记编码
			sub.setPrdRegistCode(sdto.getPrdRegistCode());
			//产品代码
			sub.setProdId(sdto.getProdId());
			//持仓类别=01：现金及活期存款
			sub.setPosType(DictCodeListExt3.中债持仓类型_现金及活期存款.getValue());
			//资产穿透情况=03 ： 最底层资产
			sub.setInterAssetType(DictCodeListExt3.资产穿透情况_最底层资产.getValue());
			//中间层层数
			sub.setInterLeveNo("1");
			//中间层行内资产/负债编码=首条记录的资管计划代码
			sub.setInterAssetCode(key);
			//会计科目名称
			sub.setSubjectName("银行存款-活期存款");
			//金额=首条记录的.未投资头寸 *折算率
			sub.setAmt(ArithmeticUtil.multiply(sbodto.getoUninvestCash(),crate).setScale(2, RoundingMode.HALF_UP));
			//折算人民币金额
			BigDecimal chaAmt = ChbCcyConvertUtil.exchangeMoney(sdto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, sub.getAmt(), rateMap, exchangeSource);
			sub.setCnyAmt(chaAmt.setScale(2, RoundingMode.HALF_UP));
			//币种
			sub.setCcy(sdto.getCcy());
			//共通赋值
			initVlueBody(sub,objecDate);
			listsposub.add(sub);

			//循环底层持仓明细
			for(ChbAstPosBatchResultDto botdto:byprdList){
				ChbAstPosSub  dsub=new ChbAstPosSub();
				//登记序列
				dsub.setRegistSeqno(IDGenerator.generate(36));
				//期数
				dsub.setTermid(DateUtil.format(objecDate,DateUtil.MONTH_OTHER_FORMAT));
				//产品登记编码=上方资产基础表的产品登记编码
				dsub.setPrdRegistCode(sdto.getPrdRegistCode());
				//产品代码
				dsub.setProdId(sdto.getProdId());
				//共通赋值
				initVlueBody(dsub,objecDate);
				//此循环明细的.底层资产代码为空的话
				if(StringUtil.isNotEmpty(botdto.getAssetId())){
				//持仓类别=02 ： 登记系统资产
				dsub.setPosType(DictCodeListExt3.中债持仓类型_登记系统资产.getValue());
				if(mapAssetType.containsKey(botdto.getAssetType2())) {
						if ("15".equals(mapAssetType.get(botdto.getAssetType2()).getObjfield1())
								|| "16".equals(mapAssetType.get(botdto.getAssetType2()).getObjfield1())) {
				//持仓类别= 03 其它系统负债
				dsub.setPosType(DictCodeListExt3.中债持仓类型_登记系统负债.getValue());
				}
				}
                //行内资产/负债编码=此循环明细的.底层资产代码
				dsub.setAssetId(botdto.getAssetId());
				//资产穿透情况=03 ： 最底层资产
				dsub.setInterAssetType(DictCodeListExt3.资产穿透情况_最底层资产.getValue());
				//中间层层数
				dsub.setInterLeveNo("1");
				//中间层行内资产/负债编码=此循环明细的.资管计划代码
				dsub.setInterAssetCode(botdto.getPlanAssetId());
				//金额=此循环明细的.底层资产市值 * 折算率
				dsub.setAmt(ArithmeticUtil.multiply(botdto.getoPosAmt(),crate).setScale(2, RoundingMode.HALF_UP));
				//公允价值=此明细的.（底层资产市值+底层资产影子价格-减值准备） *折算率
				dsub.setFloatIngpl(ArithmeticUtil.multiply(ArithmeticUtil.subtract(ArithmeticUtil.doAdd(botdto.getoPosAmt(),botdto.getoPosShadowAmt()),
						botdto.getTdyLossAmt()),crate).setScale(2, RoundingMode.HALF_UP));
				//折算人名币金额
				BigDecimal chzAmt = ChbCcyConvertUtil.exchangeMoney(botdto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, dsub.getAmt(), rateMap, exchangeSource);
				dsub.setCnyAmt(chzAmt.setScale(2, RoundingMode.HALF_UP));
				//折算公允价值
				BigDecimal chzfAmt = ChbCcyConvertUtil.exchangeMoney(botdto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, dsub.getFloatIngpl(), rateMap, exchangeSource);
				dsub.setCnyFloatIngpl(chzfAmt.setScale(2, RoundingMode.HALF_UP));
				if(mapAssetType.containsKey(botdto.getAssetType2())){
					if(DictCodeListExt3.资产持仓品种_金融衍生品.getValue().equals(mapAssetType.get(botdto.getAssetType2()).getObjfield2())){
						//单位净价: null
						dsub.setUnitValueCprice(null);
						//单位全价: null
						dsub.setUnitValueFprice(null);

					}
					if(DictCodeListExt3.资产持仓品种_净值型资产.getValue().equals(mapAssetType.get(botdto.getAssetType2()).getObjfield2())||
							("12".equals(mapAssetType.get(botdto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_否.getValue().equals(botdto.getRateFlag()))||
							("13".equals(mapAssetType.get(botdto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_否.getValue().equals(botdto.getRateFlag()))){
						//单位净价=底层资产净价市值/持仓数量
						dsub.setUnitValueCprice(ArithmeticUtil.divide(botdto.getoPosCleanPricEmarketValue(),botdto.getPosNum(),4));
						//单位全价=底层资产全价市值/持仓数量
						dsub.setUnitValueFprice(ArithmeticUtil.divide(botdto.getoPosAmt(),botdto.getPosNum(),4));

					}
					if(DictCodeListExt3.资产持仓品种_预期收益型资产.getValue().equals(mapAssetType.get(botdto.getAssetType2()).getObjfield2())||
							("12".equals(mapAssetType.get(botdto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_是.getValue().equals(botdto.getRateFlag()))||
							("13".equals(mapAssetType.get(botdto.getAssetType2()).getObjfield1())&& DictCodeList.是否_中债_是.getValue().equals(botdto.getRateFlag()))){

						//华夏个性化  有还本计划记录
//					if(secRepayInfoMap!=null&&secRepayInfoMap.containsKey(botdto.getAssetId())){
//						//单位净价=还本后面值* 底层资产净价市值/持仓数量
//						dsub.setUnitValueCprice(ArithmeticUtil.divide(botdto.getoPosCleanPricEmarketValue(),botdto.getPosNum(),14).multiply(secRepayInfoMap.get(botdto.getAssetId()).getRepayNet()==null?new BigDecimal("100"):secRepayInfoMap.get(botdto.getAssetId()).getRepayNet())
//								.setScale(4, RoundingMode.HALF_UP));
//						//单位全价=还本后面值* 底层资产全价市值/持仓数量
//						dsub.setUnitValueFprice(ArithmeticUtil.divide(botdto.getoPosAmt(),botdto.getPosNum(),14).multiply(secRepayInfoMap.get(botdto.getAssetId()).getRepayNet()==null?new BigDecimal("100"):secRepayInfoMap.get(botdto.getAssetId()).getRepayNet())
//								.setScale(4, RoundingMode.HALF_UP));
//					}else{
						//单位净价=100* 底层资产净价市值/持仓数量
						dsub.setUnitValueCprice(ArithmeticUtil.divide(botdto.getoPosCleanPricEmarketValue(),botdto.getPosNum(),14).multiply(new BigDecimal("100"))
								.setScale(4, RoundingMode.HALF_UP));
						//单位全价=100* 底层资产全价市值/持仓数量
						dsub.setUnitValueFprice(ArithmeticUtil.divide(botdto.getoPosAmt(),botdto.getPosNum(),14).multiply(new BigDecimal("100"))
								.setScale(4, RoundingMode.HALF_UP));
//					}
					}
				}
				//数量= 此循环明细的.持仓数量 *折算率
				dsub.setQty(ArithmeticUtil.multiply(botdto.getPosNum(),crate).setScale(5, RoundingMode.HALF_UP));
				}
				//底层资产代码为底层科目的处理
				else {
						//底层映射不为空的时候
					    if(mapbosubject.containsKey(botdto.getSubjectNo())){
							//持仓类别=配置记录的持仓类别
							dsub.setPosType(mapbosubject.get(botdto.getSubjectNo()).getObjfield2());
							//会计科目名称
							dsub.setSubjectName(mapbosubject.get(botdto.getSubjectNo()).getObjfield3());
						}else {
					    	logger.info("此底层科目未配置："+botdto.getSubjectNo());
					    	continue;
						}
						//资产穿透情况=03 ： 最底层资产
					    dsub.setInterAssetType(DictCodeListExt3.资产穿透情况_最底层资产.getValue());
						//中间层层数
						dsub.setInterLeveNo("1");
						//中间层行内资产/负债编码=此循环明细的.资管计划代码
						dsub.setInterAssetCode(botdto.getPlanAssetId());
						if(DictCodeListExt3.中债持仓类型_其他资产.getValue().equals(dsub.getPosType())||
								DictCodeListExt3.中债持仓类型_其他负债.getValue().equals(dsub.getPosType())){
							//金额 =此循环明细的.底层资产市值 * 折算率
							dsub.setAmt(ArithmeticUtil.multiply(botdto.getoPosAmt(),crate).setScale(2, RoundingMode.HALF_UP));
						}else if(DictCodeListExt3.中债持仓类型_现金及活期存款.getValue().equals(dsub.getPosType())){
							//金额 = 此循环明细的.底层资产市值 * 折算率
							dsub.setAmt(ArithmeticUtil.multiply(botdto.getoPosAmt(),crate).setScale(2, RoundingMode.HALF_UP));
						}
						//折算人民币金额
					    BigDecimal chbAmt = ChbCcyConvertUtil.exchangeMoney(sdto.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, dsub.getAmt(), rateMap, exchangeSource);
					    dsub.setCnyAmt(chbAmt.setScale(2, RoundingMode.HALF_UP));
				}
				//币种 = 此循环明细的.币种
				dsub.setCcy(sdto.getCcy());
				//计算折算人名币值
				BigDecimal chaUnitValueCprice = ChbCcyConvertUtil.exchangeMoney(dsub.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, dsub.getUnitValueCprice(), rateMap, exchangeSource);
				BigDecimal chaUnitValueFprice = ChbCcyConvertUtil.exchangeMoney(dsub.getCcy(), DictCodeListExt1.币种_中债_人民币.getValue(), objecDate, dsub.getUnitValueFprice(), rateMap, exchangeSource);
				dsub.setUnitValueCprice(chaUnitValueCprice.setScale(4, RoundingMode.HALF_UP));
				dsub.setUnitValueFprice(chaUnitValueFprice.setScale(4, RoundingMode.HALF_UP));
				listsposub.add(dsub);
			}
		}
		}
	   }

		/**【5】.生成资产持仓登记   end */

		/**【6】.生成产品状态登记   */
		genPrdStatusSubData(listsposub,listprdsub,objecDate);

		/**【7】.进行数据有效性校验   */
		listsposub = chbCommonCheckService.checkData(listsposub, ChbCheckTableInfo.资产持仓登记SUB);

//		List<ChbAstPosSub> insertList=checkAstPosSubList(listsposub,objecDate);

		/**【8】.登记数据批量入库   */
		if(CollectionUtils.isNotEmpty(listsposub)){
			insertChbAstPosBtach(listsposub);
		}
		if(CollectionUtils.isNotEmpty(listprdsub)){
			insertChbPrdStatusBatch(listprdsub);
		}

	}


	@Override
	public void execute(Date executeDate, List<String> prodList, List<String> assetList, Map<String, List<String>> param) throws BusinessException {
		createRegist(executeDate);
	}


	/**
	 *删除月表主表数据
	 * @param objecDate 对象日期
	 */
    public void   deleteMonthReport(Date  objecDate){

    	//删除资产持仓登记数据
		chbAstPosRegist001Mapper.deleteAstPosSubByTerm(DateUtil.format(objecDate,DateUtil.MONTH_OTHER_FORMAT));

		//删除产品状态登记数据
		chbAstPosRegist001Mapper.deletePrdStatusByTerm(DateUtil.format(objecDate,DateUtil.MONTH_OTHER_FORMAT));

	};


	/**
	 * 根据资产持仓登记生成产品状态登记
	 * @param listastSub  资产持仓登记
	 * @param listprdSub  产品状态登记
	 */
	public  void  genPrdStatusSubData(List<ChbAstPosSub>  listastSub,List<ChbPrdStatusSub> listprdSub,Date objectDate){
         Map<String, List<Date>> dateMap  =new HashMap<>();
		//对【5】生成的数据，以 产品登记编码 维度进行分组，并生成产品登记编码维度的登记数据
		Map<String,List<ChbAstPosSub>>  dbotMap=listastSub.stream().collect(
				JoyinCollectors.groupingBy(ChbAstPosSub::getPrdRegistCode));

		//6.a 筛选满足条件的数据:投资类别 in  ('01','02','04') && 资产穿透情况='03'
		List<ChbAstPosSub>   listasta=listastSub.stream().filter(s ->(StringUtil.equals(DictCodeListExt3.中债持仓类型_现金及活期存款.getValue(),s.getPosType())||
				StringUtil.equals(DictCodeListExt3.中债持仓类型_登记系统资产.getValue(),s.getPosType())||
				StringUtil.equals(DictCodeListExt3.中债持仓类型_其他资产.getValue(),s.getPosType()))&&
				StringUtil.equals(DictCodeListExt3.资产穿透情况_最底层资产.getValue(),s.getInterAssetType())).collect(Collectors.toList());
		//汇总，生成变量 【理财产品总资产金额（元）】：产品等级编码  汇总列：折算人民币金额
		Map<String,BigDecimal> toamap=new HashMap<>();
		for(ChbAstPosSub  su:listasta){
			if(toamap.containsKey(su.getPrdRegistCode())){
				toamap.put(su.getPrdRegistCode(),ArithmeticUtil.doAdd(toamap.get(su.getPrdRegistCode()),su.getCnyAmt()));
			}else{
				toamap.put(su.getPrdRegistCode(),su.getCnyAmt());
			}
		}
		//6.b 筛选满足条件的数据:投资类别 in  ('03','05') && 资产穿透情况='03'
		List<ChbAstPosSub>   listastb=listastSub.stream().filter(s ->(StringUtil.equals(DictCodeListExt3.中债持仓类型_登记系统负债.getValue(),s.getPosType())||
				StringUtil.equals(DictCodeListExt3.中债持仓类型_其他负债.getValue(),s.getPosType()))&&
				StringUtil.equals(DictCodeListExt3.资产穿透情况_最底层资产.getValue(),s.getInterAssetType())).collect(Collectors.toList());
		//汇总，并生成变量 【理财产品总负债金额（元）】：产品等级编码  汇总列：折算人民币金额
		Map<String,BigDecimal> tobmap=new HashMap<>();
		for(ChbAstPosSub  su:listastb){
			if(toamap.containsKey(su.getPrdRegistCode())){
				tobmap.put(su.getPrdRegistCode(),ArithmeticUtil.doAdd(tobmap.get(su.getPrdRegistCode()),su.getCnyAmt()));
			}else{
				tobmap.put(su.getPrdRegistCode(),su.getCnyAmt());
			}
		}
		//循环处理
		for(String  key:dbotMap.keySet()){
			 //产品状态登记
			ChbPrdStatusSub sub=new ChbPrdStatusSub();
			//期数
			sub.setTermid(DateUtil.format(objectDate,DateUtil.MONTH_OTHER_FORMAT));
			//登记序列
			sub.setRegistSeqno(IDGenerator.generate(36));
			//发行机构代码
			String issuePartyId = CommonUtil.selectBankCode();
			sub.setIssuePartyId(issuePartyId);
			//产品等级编码
			sub.setPrdRegistCode(key);
			//产品代码
			sub.setProdId(dbotMap.get(key).get(0).getProdId());
			//理财产品总资产金额（元）=【a】生成的.理财产品总资产金额（元）
			if(toamap.containsKey(key)){
				sub.setPrdTotalAmt(toamap.get(key).setScale(2, RoundingMode.HALF_UP));
			}
			//理财产品杠杆率=【a】生成的.理财产品总资产金额（元） /(【a】生成的.理财产品总资产金额（元） - 【b】生成的.理财产品总负债金额（元）)
			if(toamap.containsKey(key)&&tobmap.containsKey(key)){
				sub.setPrdLeverage(ArithmeticUtil.divide(toamap.get(key),ArithmeticUtil.subtract(toamap.get(key),tobmap.get(key)),5));
			}
			//产品状态统计日
			sub.setPrdStatusDate(objectDate);
			//备注=默认值【理财产品总资产金额=资产持仓登记表中最底层资产金额合计 理财产品杠杆率（%）=理财产品总资产金额/(理财产品总资产金额-理财产品总负债合计）；】
			sub.setRemark("理财产品总资产金额=资产持仓登记表中最底层资产金额合计 理财产品杠杆率（%）=理财产品总资产金额/(理财产品总资产金额-理财产品总负债合计）；");
			//数据状态
			sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
			// 报送流水号
			sub.setSubmitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
			//数据日期
			sub.setStatusDate(DateUtil.now());
			//报送状态=01 未报送
			sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
			//报送截止日
			List<Date> dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产持仓登记.getValue(),
					DictCodeListExt2.报送数据状态_新建.getValue(), dateMap,
					objectDate);
			if (CollectionUtils.isNotEmpty(dateList)) {
				sub.setChbSubmitDeadline(dateList.get(0));
				sub.setSubmitDeadline(dateList.get(1));
			}
			listprdSub.add(sub);
		}
	}

	public  List<ChbAstPosSub>   checkAstPosSubList(List<ChbAstPosSub>  listsposub,Date objectDate){
		List<ChbAstPosSub> insertList=new ArrayList<>();
		// 查询数据库表字段
		List<String> listCloumn = baseMapper.getTableColumnList("CHB_AST_POS_SUB");
		//调用共通 将list<接口表DTO>转换成List<Map<Key, Object>>格式
		List<Map<String, String>> listMap = svrMaptoEntityUtil.convertEntityToMap(listsposub, listCloumn);
		//调用check共同方法
		beforeSubErrorCheck.checkChbAstPosReg(listMap,objectDate,null,null);
		//反射回来
		insertList=svrMaptoEntityUtil.convertMapToEntity(listMap, listCloumn,
				ChbAstPosSub.class);
		return insertList;


	}



	/**
	 * 分批次执行insertChbAstPosBatchList
	 *
	 * @param dataList 数据
	 * @see
	 * @since 1.0
	 */
	public void insertChbAstPosBtach(List<ChbAstPosSub>  dataList){
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}

		int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			chbAstPosRegist001Mapper.insertChbAstPosBatchList(dataList);
			return;
		}

		List<ChbAstPosSub> newDataList = new ArrayList<ChbAstPosSub>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			chbAstPosRegist001Mapper.insertChbAstPosBatchList(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbAstPosRegist001Mapper.insertChbAstPosBatchList(newDataList);
		}
	}

	/**
	 * 分批次执行insertChbPrdStatusBatchList
	 *
	 * @param dataList 数据
	 * @see
	 * @since 1.0
	 */
	public void insertChbPrdStatusBatch(List<ChbPrdStatusSub>  dataList){
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}

		int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			chbAstPosRegist001Mapper.insertChbPrdStatusBatchList(dataList);
			return;
		}

		List<ChbPrdStatusSub> newDataList = new ArrayList<ChbPrdStatusSub>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			chbAstPosRegist001Mapper.insertChbPrdStatusBatchList(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbAstPosRegist001Mapper.insertChbPrdStatusBatchList(newDataList);
		}
	}



	public int parsToInt(String count,String flag){
		if(StringUtil.isNotEmpty(count)&&Integer.parseInt(count)<=0){
			if("1".equals(flag)){
				return 20;
			}
			else{
				return 500;
			}
		}
		else{
			return Integer.parseInt(count);
		}
	}

	private   void  initVlueBody(ChbAstPosSub sub,Date objecDate){
		// 当前批次报送时点集合
		Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
		//持仓日期
		sub.setValueDate(objecDate);
		//发行机构代码
		String issuePartyId = CommonUtil.selectBankCode();
		sub.setIssuePartyId(issuePartyId);
		//数据状态
		sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
		// 报送流水号
		sub.setSubmitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
		//数据日期
		sub.setStatusDate(DateUtil.now());
		//报送状态=01 未报送
		sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
		//报送截止日
		List<Date> dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_资产持仓登记.getValue(),
				DictCodeListExt2.报送数据状态_新建.getValue(), dateMap,
				objecDate);
		if (CollectionUtils.isNotEmpty(dateList)) {
			sub.setChbSubmitDeadline(dateList.get(0));
			sub.setSubmitDeadline(dateList.get(1));
		}
	}

}