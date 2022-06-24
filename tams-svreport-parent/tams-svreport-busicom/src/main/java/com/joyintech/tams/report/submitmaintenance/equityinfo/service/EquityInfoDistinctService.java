package com.joyintech.tams.report.submitmaintenance.equityinfo.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.base.entity.ChbEquTmp;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpColumn;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.report.investormerger.dao.ChbEquHoldCheckDtoMapper;
import com.joyintech.tams.report.investormerger.dao.ChbInvestholdInfoCheckDtoMapper;
import com.joyintech.tams.report.investormerger.dto.ChbEquHoldCheckDto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfo001Mapper;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.formula.functions.T;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.*;
import java.util.Map.Entry;


@Service
public class EquityInfoDistinctService {
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CheckReport001Mapper chbReportCheckMapper;
	@Autowired
	private EquityInfoRegist001Mapper equityInfoRegist001Mapper;
	@Autowired
	private ChbEquHoldCheckDtoMapper chbEquHoldCheckDtoMapper;

	@Autowired
	private EquityInfoDistinctMapper equityInfoDistinctMapper;

	@Autowired
	private ChbInvestholdInfoCheckDtoMapper chbInvestholdInfoCheckDtoMapper;

	@Autowired
	private SqlSessionTemplate template;
	@Autowired
	private BatchInsertService batchInsertService;

	/**
	 * 查询或者删除一次最大sql数
	 */
	@Value("${application.reportSelectOrDeleteCount}")
	public int reportSelectOrDeleteCount;

	/**
	 * 查询或者删除一次最大sql数
	 */
	@Value("${application.reportSelectOrDeleteCount}")
	public String reportSelectOrDeleteCountString;

	/**
	 * check表 单次更新条数（合并后识别标识）
	 */
	@Value("${application.reportUpdateCountForShenFen}")
	public String reportUpdateCountForShenFen;

	/**
	 * 长sql最大一次插入或者更新条数
	 */
	@Value("${application.reportSelectOrDeleteCountForShenFen}")
	public String reportSelectOrDeleteCountForShenFen;







	/**
	 * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
	 */
	@Value("${application.reportInterfOneTimeSqlLdrDataCount}")
	private int reportInterfOneTimeSqlLdrDataCount;
	/**
	 * 短sql最大一次最大插入条数
	 */
	@Value("${application.reportInsertCount}")
	public  int reportInsertCount;

	/**
	 * 跟已报送的数据进行对比,有重复则按照已报送的
	 * @param equityList  xml解析出来的身份信息
	 * @param intype 01 为进检查表 02 为不进检查表
	 */
	@SuppressWarnings("unchecked")
	public void checkSendedData(@SuppressWarnings("rawtypes") List equityList,List<String> keys,String intype){
		if(StringUtil.isEmptyList(equityList)){
			return;
		}
		List<ChbEquTmp> list = new ArrayList<ChbEquTmp>();
		keys.stream().forEach(item->{
			String[] strs = item.split(GlobalConstant.UNDER_LINE, -1);
			ChbEquTmp dto = new ChbEquTmp();
			dto.setRegistBankCode(strs[0]);
			dto.setIdentifyNumber(strs[1]);
			dto.setHolderName(strs[2]);
			dto.setPersonalIdentifyType(strs[3]);
			dto.setInstitutionIdentifyType(strs[4]);
			dto.setOtherIdentifyName(strs[5]);
			list.add(dto);
		});

		if(keys!= null && keys.size()>=1000){
			// add by zcw 三期性能优化 start
			// 删除临时表数据
			chbEquHoldCheckDtoMapper.truncateEquityTmp();
			// 六要素入临时表。sql对临时表操作
			saveKeys(keys);
			// add by zcw 三期性能优化 end
		}

		List<EquityInfoRegist001Dto> ybs = getEquInfoSubListYbs(list);
 
		if(StringUtil.isEmptyList(ybs)){
			return;
		}
		
		//取得check表源数据
		Map<String,List<ChbEquHoldCheckDto>> checkMap = new HashMap<String,List<ChbEquHoldCheckDto>>();
		String updateFlg = SvrDataUtil.getSysParam("SVR_EQU_REPAIR_BATCH_FLAG");
		//只有当系统参数为01：使用修复批的情况下，才取以前保存在库中的check表数据
		if("01".equals(updateFlg)) {
			//取得本次导入txt记录，对应的CHECK表记录
			List<ChbEquHoldCheckDto> allList = this.selectChekForRepairByList(list);

			checkMap = allList.stream().collect(JoyinCollectors.groupingBy(
					x -> getKey(x)));	
		}
		
		
//		String key = "";
//        String key1 = "";	
//        Object tmpMap = null;

        /**
        Map<String,EquityInfoRegist001Dto> mapYbs = new HashMap<String,EquityInfoRegist001Dto>();
		if ( ybs!=null ) {
    		for ( EquityInfoRegist001Dto v:ybs ) {
    		    String k = getKey(v);
    		    mapYbs.put(k, v);
    		}
		}
		 **/
	  //add by dengyu 
		Map<String, EquityInfoRegist001Dto> mapYbs = ybs.stream().collect(JoyinCollectors.toMap(x -> getKey(x), x -> x));
         
		ybs = null;
		System.gc();
		
		 HashSet<String> existList = new HashSet<String>();
        //step2、跟已报送的数据进行对比,有重复则按照已报送的识别标识更新源数据的合并识别标识并入库
		
		// logger.info("step2跟已报送的数据进行对比合并入库开始");
		 
		 List<String> keyList=new ArrayList<String>();
		
		 String indentifyMark ="";
		for (Iterator<T> iterator = equityList.iterator();iterator.hasNext();) {
			Object tmpMap = iterator.next();
			 
			 //当前不校验【原识别标识】有值的数据,(后续【重要信息变更】会对这种场景check)
			 if(tmpMap  instanceof Map) {
				 if(StringUtil.isNotEmpty(((Map<String,String>)tmpMap).get("ORG_INDENTIFY_MARK"))){
					 continue;
				 }
				 indentifyMark= ((Map<String,String>)tmpMap).get("INDENTIFY_MARK");
			 }else if(tmpMap instanceof EquityInfo001Dto){
				 if(StringUtil.isNotEmpty(((EquityInfo001Dto) tmpMap).getOrgIndentifyMark())){
					 continue;
				 }
				 indentifyMark= ((EquityInfo001Dto) tmpMap).getIndentifyMark();
			 }
			 String key = getKey(tmpMap);
			 EquityInfoRegist001Dto  yibaosongModel = mapYbs.get(key);
			 
			 if(yibaosongModel!=null && !indentifyMark.equals(yibaosongModel.getIndentifyMark())){
				String yibaosongkey = getKey(yibaosongModel);

				 if(key!=null){
					 //五要素一致，且识别标识不一致的情况
					 if(key.equals(yibaosongkey) && !indentifyMark.equals(yibaosongModel.getIndentifyMark())){
						 //插入CHECK表
						 EquityInfo001Dto model = new EquityInfo001Dto();
						 if(tmpMap  instanceof Map){
							 EquityInfoMaintenanceService.maptoDto((Map<String,String>)tmpMap, model);
						 }else if(tmpMap  instanceof EquityInfo001Dto){
							 model = (EquityInfo001Dto)tmpMap;
						 }
						 if("01".equals(intype)){//是否进check表
							 ChbEquHoldCheckDto checkModel = transformModelToCheck(model);
							 //修复批启用的情况下，如果接入的txt对应的信息为空，则用之前check表的内容替换
							 if("01".equals(updateFlg)) {
								 if(checkMap.containsKey(yibaosongkey) &&  checkMap.get(yibaosongkey) != null) { 
								for(ChbEquHoldCheckDto hdlDto : checkMap.get(yibaosongkey)) {
									if(indentifyMark.equals(hdlDto.getIndentifyMark())) {
										//设置 账号开户所在地
										if(StringUtil.isEmpty(checkModel.getFundAccPlace())) {
											checkModel.setFundAccPlace(hdlDto.getFundAccPlace());
										}
										//设置 权益人所属银行代码
										if(StringUtil.isEmpty(checkModel.getTheBankCode())) {
											checkModel.setTheBankCode(hdlDto.getTheBankCode());
										}
										
									}
								} 
								 }
							 }
							 checkModel.setMergerindentifymark(yibaosongModel.getIndentifyMark());
							 checkModel.setOperatestatus(DictCodeListExt2.处理状态_中债_已合并.getValue());
							 chbEquHoldCheckDtoMapper.deleteCheckDataByPrimarykey(checkModel);
							 chbEquHoldCheckDtoMapper.insert(checkModel);
							 if(!existList.contains(yibaosongModel.getIndentifyMark())){
								 if(null == chbEquHoldCheckDtoMapper.selectByPrimarykey(yibaosongModel.getIndentifyMark())){
									 existList.add(yibaosongModel.getIndentifyMark());
									 ChbEquHoldCheckDto regDto = transformRegModelToCheck(yibaosongModel);
									 regDto.setMergerindentifymark(yibaosongModel.getIndentifyMark());
									 regDto.setOperatestatus(DictCodeListExt2.处理状态_中债_已合并.getValue());
//									//修复批启用的情况下，如果接入的txt对应的信息为空，则用之前check表的内容替换
//									 if("01".equals(updateFlg)) {
//										for(ChbEquHoldCheckDto hdlDto : checkMap.get(yibaosongkey)) {
//											if(yibaosongModel.getIndentifyMark().equals(hdlDto.getIndentifyMark())) {
//												//设置 账号开户所在地
//												if(StringUtil.isEmpty(checkModel.getFundAccPlace())) {
//													checkModel.setFundAccPlace(hdlDto.getFundAccPlace());
//												}
//												//设置 权益人所属银行代码
//												if(StringUtil.isEmpty(checkModel.getTheBankCode())) {
//													checkModel.setTheBankCode(hdlDto.getTheBankCode());
//												}
//												
//											}
//										} 
//									 }
									 chbEquHoldCheckDtoMapper.insert(regDto);
								 }
							 }
						 }
						 //此数据不入维护表
						 keyList.add(key);
						 //从equityList中删除，不做后续处理。
						 iterator.remove();
						 //continue;
					 }
				 }
			 }else if(yibaosongModel!=null && indentifyMark.equals(yibaosongModel.getIndentifyMark())){
				//修复批启用的情况下，如果接入的txt对应的信息为空，则用之前check表的内容替换
				 if("01".equals(updateFlg)) {
					 ChbEquHoldCheckDto checkModel = chbEquHoldCheckDtoMapper.selectByPrimarykey(yibaosongModel.getIndentifyMark());
					 if(StringUtil.isNotEmpty(checkModel)){
						 boolean updFlg = false;
						 //更新CHECK表
						 EquityInfo001Dto model = new EquityInfo001Dto();
						 if(tmpMap  instanceof Map){
							 EquityInfoMaintenanceService.maptoDto((Map<String,String>)tmpMap, model);
						 }else if(tmpMap  instanceof EquityInfo001Dto){
							 model = (EquityInfo001Dto)tmpMap;
						 }
						 ChbEquHoldCheckDto regDto = transformModelToCheck(model);
						 regDto.setMergerindentifymark(yibaosongModel.getIndentifyMark());
						 regDto.setOperatestatus(DictCodeListExt2.处理状态_中债_已合并.getValue());
						 //设置 账号开户所在地
						 if(StringUtil.isEmpty(regDto.getFundAccPlace())) {
							 regDto.setFundAccPlace(checkModel.getFundAccPlace());
						 }
						 //设置 权益人所属银行代码
						 if(StringUtil.isEmpty(regDto.getTheBankCode())) {
							 regDto.setTheBankCode(checkModel.getTheBankCode());
						 }
						 chbEquHoldCheckDtoMapper.updateForRepair(regDto);
						
					 }
				 }
				 
			 }
		}
		keys.removeAll(keyList);
		//
		//logger.info("step2跟已报送的数据进行对比合并入库结束");
		
		/**
		 * 
		 * 此处可以改造，如果后续出现卡慢的情况，可以按如下代码设计处理：
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		list.clear();
	}


	/**
	 * 根据三要素(姓名+证件类型+证件号)来检查重复数据,如有重复则直接插入CHB_EQU_HOLD_CHECK
	 * @param equityList xml解析出来的身份信息
	 * @param intype 01 为进检查表 02 为不进检查表
	 */
	@SuppressWarnings("unchecked")
	public List<String> checkAllData(@SuppressWarnings("rawtypes") List equityList,List<String> keys,String intype){
		List<String> mergerkeys=new ArrayList<String>();
		if(StringUtil.isEmptyList(equityList)){
			return null;
		}

//		List<String> keys = getKeys(equityList);

		//step1、查询维护表以及CHB_EQU_HOLD_CHECK表中的含三要素的数据(去重)
		/**
		 * 场景：
		 *      上午接进一笔【张三+XXXXXXXX+001】的数据,此数据不重复未进入CHECK表;
		 *      下午又接进一笔【张三+XXXXXXXX+002】的数据,此时不查找维护表,无法判断是否重复;
		 */
		logger.info("查询库中未报送数据及check表数据开始");
		// 查询所有未报送即反馈状态不等于成功的且三要素等于传入的key数据
		List<EquityInfo001Dto> infoList = getEquInfoList(keys,0);
		logger.info("查询库中未报送数据及check表数据结束");
		//step2、equityList、infoList去重合并
		logger.info("equityList、infoList去重合并开始");
		Map<String,List<EquityInfo001Dto>> allMap = getTotalMap(infoList,equityList);
		logger.info("equityList、infoList去重合并结束");
		String type="dto";
		if(equityList.get(0) instanceof Map){
			type="map";
		}
		logger.info("过滤识别标识变更数据开始");
		//原始别标识不为空的数据不能删除
		Object tmpMap = null;
		for (Iterator<T> iterator = equityList.iterator();iterator.hasNext();) {
			tmpMap = iterator.next();
			EquityInfo001Dto tmp = new EquityInfo001Dto();
			if(tmpMap instanceof Map){
				EquityInfoMaintenanceService.maptoDto((Map<String, String>)tmpMap, tmp);
				if(StringUtil.isNotEmpty(((Map<String,String>)tmpMap).get("ORG_INDENTIFY_MARK"))){
					continue;
				}
				iterator.remove();
			}else if(tmpMap instanceof  EquityInfo001Dto){
				tmp = (EquityInfo001Dto)tmpMap;
				if(StringUtil.isNotEmpty(((EquityInfo001Dto)tmp).getOrgIndentifyMark())){
					continue;
				}
				iterator.remove();
			}
		}
		logger.info("过滤识别标识变更数据结束");
		logger.info("数据比较入check表全删全插开始");
		List<EquityInfo001Dto> listForDelete = new ArrayList<EquityInfo001Dto>(); 
		List<ChbEquHoldCheckDto> listForInsert = new ArrayList<ChbEquHoldCheckDto>();
		
		for(Map.Entry<String, List<EquityInfo001Dto>> entry: allMap.entrySet()){
			if(entry.getValue().size() > 1){
				if("01".equals(intype)){
					//重复数据,CHECK表全删全插
					mergerkeys.add(entry.getKey());

					//删除check表数据列表
					List<String> strLsit = new ArrayList<String>();
					strLsit.add(entry.getKey());					
					listForDelete.addAll(tranStringToDtoForKey(strLsit));
									
					//插入身份check表数据列表
					for(EquityInfo001Dto hdlDto : entry.getValue()) {
						ChbEquHoldCheckDto model = transformModelToCheck(hdlDto);
						model.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
						if(!DictCodeListExt2.处理状态_中债_已合并.getValue().equals(model.getOperatestatus())){
							model.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
						}
						listForInsert.add(model);
					}
				}
			}else{
				if(type.equals("map")){
					equityList.add(modelToMapByPrpperties(entry.getValue().get(0)));
				}else{
					equityList.add(entry.getValue().get(0));
				}
			}
		}
		
		//对check表数据进行处理  删除check表对应的记录
		if(listForDelete != null && listForDelete.size() > 0) {
			this.delCheckByBatch(listForDelete);
		}
		
		//对check表数据进行处理  插入check表信息
		if(listForInsert != null && listForInsert.size() > 0) {
			this.insertCheckByBatch(listForInsert);
		}	
		
		logger.info("数据比较入check表全删全插结束");
		return mergerkeys;
	}

	/**
	 * 根据规则合并数据
	 * @param equityList
	 * @param keys
	 * @param type 调用方传参是map 还是dto
	 */
	@SuppressWarnings("unchecked")
	public void merger(@SuppressWarnings("rawtypes") List equityList,String type){
		//查询系统参数 是否自动处理 同身份信息多个有效识别标识
		String mergerFlag = SvrDataUtil.getSysParam("SVR_EQU_HOLD_MERGER");

//		List<String> keys = getKeys(equityList);
		logger.info("查询check表中所有未合并记录 开始");
		List<ChbEquHoldCheckDto>  checkDataList = chbEquHoldCheckDtoMapper.mergerData(null);
		logger.info("查询check表中所有未合并记录  结束");

		if("0".equals(mergerFlag)){
			manualMerger(checkDataList);
			return;
		}


		HashMap<String,ChbEquHoldCheckDto> lastMergerMap = new HashMap<String,ChbEquHoldCheckDto>();
		logger.info("根据规则获取主识别标识处理 开始");
		if("1".equals(mergerFlag)){
			//按照识别标识小的合并
			lastMergerMap = getMinMark(checkDataList);
		}else{
			//按照识别标识大的合并
			lastMergerMap = getMaxMark(checkDataList);
		}
		logger.info("根据规则获取主识别标识处理 结束");

		//CHECK表中合并识别标识按照规则刷新
		logger.info("入库、返回数据结构构件处理 开始");
		List<ChbEquHoldCheckDto> listForUpdate = new ArrayList<ChbEquHoldCheckDto>();
		for(Map.Entry<String, ChbEquHoldCheckDto> entry: lastMergerMap.entrySet()){
//			chbEquHoldCheckDtoMapper.updateMergerIndentifyMark(entry.getKey(),entry.getValue().getIndentifyMark());

			listForUpdate.add(entry.getValue());
			//返回规则合并后的数据给原逻辑处理
			if(type.equals("map")){
				equityList.add(modelToMapByPrpperties(transformModelToEquHold(entry.getValue())));
			}else{
				equityList.add(transformModelToEquHold(entry.getValue()));
			}
		}
		logger.info("入库、返回数据结构构件处理 结束");
		
		logger.info("check表主识别标识刷新处理 开始");
		this.updateMergerIndentifyMarkByBatch(listForUpdate);
		logger.info("check表主识别标识刷新处理 结束");
		
		
	}

	/**
	 * 手动合并时，重复数据进入维护表，数据状态为待处理，且错误原因：识别标识不可用！该投资者存在多个识别标识
	 * @param equityList
	 */
	public void manualMerger(List<ChbEquHoldCheckDto>  checkDataList){
		List<EquityInfo001Dto> listN = new ArrayList<EquityInfo001Dto>();
		List<String> keys = new ArrayList<String>();
		for(ChbEquHoldCheckDto dto : checkDataList){
			EquityInfo001Dto info = transformModelToEquHold(dto);
			info.setStatus("A");
//			部门代码
//			info.setDeptCode(dto.getDeptCode());
//			机构代码
//			info.setOrgCode(dto.getOrgCode());
			info.setInterfCheckResult("识别标识不可用！该投资者存在多个识别标识");
			listN.add(info);
			keys.add(getKey(info));
		}
		deleteEquitInfo(keys);
		insertBtachInterfEquityInfoByPart(listN);
	}

	/**
	 * 分批次执行insertBtachInterfEquityInfo
	 *
	 * @param dataList 数据
	 * @see
	 * @since 1.0
	 */
	private void insertBtachInterfEquityInfoByPart(List<EquityInfo001Dto> dataList) {
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}

		EquityInfo001Mapper equityInfo001Mapper = SpringContextUtil.getBean(EquityInfo001Mapper.class);
		int maxDataCountOneTime = parsToInt(reportSelectOrDeleteCountForShenFen,"1");
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			equityInfo001Mapper.insertBtachInterfEquityInfo(dataList);
			return;
		}

		List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			equityInfo001Mapper.insertBtachInterfEquityInfo(
					newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			equityInfo001Mapper.insertBtachInterfEquityInfo(newDataList);
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

	/**
	 * 三要素重复,根据合并规则取识别标识,则需要对之前的交易以及产品头寸的识别标识进行刷新
	 * @param equityList
	 * @param type 0:识别标识合并 1:识别标识变更
	 */
	public void refreshIndentifyMark(List<EquityInfo001Dto> list,int type){
		List<EquityInfo001Dto> markChangeList  = new ArrayList<EquityInfo001Dto>();
		if(1 == type){
			for(EquityInfo001Dto dto : list){
				if(StringUtil.isNotEmpty(dto.getOrgIndentifyMark())){
					markChangeList.add(dto);
				}
			}
			chbEquHoldCheckDtoMapper.refreshChangeIndentifyMark(markChangeList);
		}else{
//			chbEquHoldCheckDtoMapper.refreshMergerIndentifyMark(list);
		}
	}

	/**
	 * 取最大识别标识的记录
	 * @param checkDataList
	 * @param equityList
	 * @return
	 */
	private HashMap<String,ChbEquHoldCheckDto> getMaxMark(List<ChbEquHoldCheckDto> checkDataList) {
		HashMap<String,ChbEquHoldCheckDto> resultMap = new HashMap<String,ChbEquHoldCheckDto>();

		Map<String, List<ChbEquHoldCheckDto>> infoMap = checkDataList.stream().collect(JoyinCollectors.groupingBy(
				x -> getKey(x)));

		String key = "";
		for(Entry<String, List<ChbEquHoldCheckDto>> map : infoMap.entrySet()){
			key = map.getKey();
			ChbEquHoldCheckDto max = Collections.max(map.getValue(), new Comparator<ChbEquHoldCheckDto>(){

				@Override
				public int compare(ChbEquHoldCheckDto arg0, ChbEquHoldCheckDto arg1) {
					String iKey = getKey(arg0);
					String jKey = getKey(arg1);
					if(iKey!=null && iKey.equals(jKey)){
						if(("01".equals(arg0.getIsBelongBank()) && "01".equals(arg1.getIsBelongBank()))
								|| (!"01".equals(arg0.getIsBelongBank()) && !"01".equals(arg1.getIsBelongBank()))){
							return arg0.getIndentifyMark().compareTo(arg1.getIndentifyMark());
						}else{
							if("01".equals(arg0.getIsBelongBank())){
								return -1;
							}else if("01".equals(arg1.getIsBelongBank())){
								return -1;
							}else{
								return 0;
							}
						}
					}
					return 0;
				}
			});
			resultMap.put(key, max);
		}


		return resultMap;
	}


	/**
	 * 取最小识别标识的记录
	 * @param checkDataList
	 * @param equityList
	 * @return
	 */
	private HashMap<String,ChbEquHoldCheckDto> getMinMark(List<ChbEquHoldCheckDto> checkDataList) {

		HashMap<String,ChbEquHoldCheckDto> resultMap = new HashMap<String,ChbEquHoldCheckDto>();

		Map<String, List<ChbEquHoldCheckDto>> infoMap = checkDataList.stream().collect(JoyinCollectors.groupingBy(
				x -> getKey(x)));

		String key = "";
		for(Entry<String, List<ChbEquHoldCheckDto>> map : infoMap.entrySet()){
			key = map.getKey();
			ChbEquHoldCheckDto min = Collections.min(map.getValue(), new Comparator<ChbEquHoldCheckDto>(){

				@Override
				public int compare(ChbEquHoldCheckDto arg0, ChbEquHoldCheckDto arg1) {
					String iKey = getKey(arg0);
					String jKey = getKey(arg1);
					if(iKey!=null && iKey.equals(jKey)){
						if(("01".equals(arg0.getIsBelongBank()) && "01".equals(arg1.getIsBelongBank()))
								|| (!"01".equals(arg0.getIsBelongBank()) && !"01".equals(arg1.getIsBelongBank()))){
							return arg0.getIndentifyMark().compareTo(arg1.getIndentifyMark());
						}else{
							if("01".equals(arg0.getIsBelongBank())){
								return 1;
							}else if("01".equals(arg1.getIsBelongBank())){
								return 1;
							}else{
								return 0;
							}
						}
					}
					return 0;
				}
			});
			resultMap.put(key, min);
		}

		return resultMap;
	}

	/**
	 * 删除此次三要素中未报送的身份信息以及未报送的登记信息
	 * @param equityList
	 */
	public void deleteEquitInfoAndSub(List<String> keys){
		if(StringUtil.isEmptyList(keys)){
			return;
		}
		String mergerFlag = SvrDataUtil.getSysParam("SVR_EQU_HOLD_MERGER");
		if(!"0".equals(mergerFlag)){
			/*** add by zcw 三期性能优化 start **/
			// 如果需要删除数量大于1000条，使用sqldr入临时表关联临时表删除
			if(keys!= null && keys.size()>=1000){
				// 如果需要删除数量大于1000条，使用sqldr入临时表关联临时表删除
				// 删除临时表数据
				chbEquHoldCheckDtoMapper.truncateEquityTmp();
				// 六要素入临时表。sql对临时表操作
				saveKeys(keys);
				// 根据临时表关联删除
				chbEquHoldCheckDtoMapper.deleteEquityInfoBatchTmp();
                chbEquHoldCheckDtoMapper.deleteEquitySubBatchTmp();
				// 删除临时表数据
				chbEquHoldCheckDtoMapper.truncateEquityTmp();
                return;
				/*** add by zcw 三期性能优化 end **/
			}else{
				deleteEquitInfo(keys);
				deleteEquitInfoSub(keys);
			}
		}
	}

	/**
	 * 分批获取已报送成功数据
	 * @param equityList
	 * @param keys
	 */
//	@SuppressWarnings("rawtypes")
	private List<EquityInfoRegist001Dto> getEquInfoSubList(List<EquityInfo001Dto> list,int type){
		List<EquityInfoRegist001Dto> resultList = new ArrayList<EquityInfoRegist001Dto>();
//		int maxDataCountOneTime =(null == reportSelectOrDeleteCountForShenFen ? 100:Integer.parseInt(reportSelectOrDeleteCountForShenFen));
//        int dataCount = 0;
//        if(keys != null){
//        	dataCount = keys.size();
//        }
//        if(0 == type){
//        	for(String key:keys){
//        		template.select("com.joyintech.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper.queryAllEquityInfoConStrastYbsSingle",
//        				key,new ResultHandler() {
//        			@Override
//        			public void handleResult(ResultContext resultContext) {
//        				resultList.add((EquityInfoRegist001Dto) resultContext.getResultObject());
//        			}
//        		});
//        	}
//        }else{
//        	return equityInfoDistinctMapper.queryEquityInfoRegistByPkUnsubmit(keys);
//        }
//        if (dataCount <= maxDataCountOneTime) {
//        	if(0 == type){
//        		resultList = equityInfoDistinctMapper.queryAllEquityInfoConStrastYbs(keys);
//        	}else{
//		resultList = equityInfoDistinctMapper.queryEquityInfoRegistByPkUnsubmit(keys);
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCountString, "2");
		int dataCount = list.size();
		if (dataCount <= maxDataCountOneTime) {
			resultList.addAll(equityInfoDistinctMapper.queryEquityInfoRegistByPkUnsubmit(list));
		} else {
			List<EquityInfo001Dto> newKeyList = new ArrayList<EquityInfo001Dto>();
			newKeyList.addAll(list);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				resultList.addAll(
						equityInfoDistinctMapper.queryEquityInfoRegistByPkUnsubmit(newKeyList.subList(0, maxDataCountOneTime)));
				//更新正确的识别标识
				newKeyList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newKeyList)) {
				resultList.addAll(equityInfoDistinctMapper.queryEquityInfoRegistByPkUnsubmit(newKeyList));
			}
		}
//        	}
//            return resultList;
//        }
//
//        List<String> newDataList = new ArrayList<String>();
//        newDataList.addAll(keys);
//
//        // 分批处理
//        int part = dataCount / maxDataCountOneTime; // 分批数
//        for (int i = 0; i < part; i++ ) {
//        	if(0 == type){
//        		resultList.addAll(equityInfoDistinctMapper.queryAllEquityInfoConStrastYbs(newDataList.subList(0, maxDataCountOneTime)));
//        	}else{
//        		resultList.addAll(equityInfoDistinctMapper.queryEquityInfoRegistByPkUnsubmit(newDataList.subList(0, maxDataCountOneTime)));
//        	}
//        	newDataList.subList(0, maxDataCountOneTime).clear();
//        }
//        // 最后剩下的数据
//        if (CollectionUtils.isNotEmpty(newDataList)) {
//        	if(0 == type){
//        		resultList.addAll(equityInfoDistinctMapper.queryAllEquityInfoConStrastYbs(newDataList));
//        	}else{
//        		resultList.addAll(equityInfoDistinctMapper.queryEquityInfoRegistByPkUnsubmit(newDataList));
//        	}
//        }
		return resultList;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<EquityInfoRegist001Dto> getEquInfoSubListYbs(List<ChbEquTmp> list){
		List<EquityInfoRegist001Dto> resultList = new LinkedList<>();
//		int maxDataCountOneTime =(null == reportSelectOrDeleteCountForShenFen ? 100:Integer.parseInt(reportSelectOrDeleteCountForShenFen));
//        int dataCount = 0;
//     if(equityList!=null){
//    	 dataCount=equityList.size();
//     }
		// add by zcw 三期性能优化 start
		// 六要素入临时表。sql对临时表操作
		if(list!=null && list.size()>=1000){
			template.select("com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper.queryAllEquityInfoMax",
				new ResultHandler() {
					@Override
					public void handleResult(ResultContext resultContext) {
						resultList.add((EquityInfoRegist001Dto) resultContext.getResultObject());
					}
			});
		}else{
			Map<String,Object> params = new HashMap<>();

//		 List<String> keys = getKeys(equityList);

			params.put("list", list);
			template.select("com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper.queryAllEquityInfo",params,
				new ResultHandler() {
					@Override
					public void handleResult(ResultContext resultContext) {
						resultList.add((EquityInfoRegist001Dto) resultContext.getResultObject());
					}
			});
		}
		// add by zcw 三期性能优化 end

//        template.select("com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper.queryAllEquityInfo",keys,
//        		new ResultHandler() {
//        	@Override
//        	public void handleResult(ResultContext resultContext) {
//        		resultList.add((EquityInfoRegist001Dto) resultContext.getResultObject());
//        	}
//        });

//        	
//        if (dataCount <= maxDataCountOneTime) {
//        	
//        		resultList = equityInfoDistinctMapper.queryAllEquityInfoConStrastYbs(equityList);
//        	
//            return resultList;
//        }
//
//        List newDataList = new ArrayList();
//        newDataList.addAll(equityList);
//
//        // 分批处理
//        int part = dataCount / maxDataCountOneTime; // 分批数
//        for (int i = 0; i < part; i++ ) {
//        	
//        		resultList.addAll(equityInfoDistinctMapper.queryAllEquityInfoConStrastYbs(newDataList.subList(0, maxDataCountOneTime)));
//        	
//        	newDataList.subList(0, maxDataCountOneTime).clear();
//        }
//        // 最后剩下的数据
//        if (CollectionUtils.isNotEmpty(newDataList)) {
//        		resultList.addAll(equityInfoDistinctMapper.queryAllEquityInfoConStrastYbs(newDataList));
//        }

		return resultList;
	}
	/**
	 * 分批获取未报送数据
	 * @param equityList
	 * @param keys
	 */
	@SuppressWarnings("rawtypes")
	private List<EquityInfo001Dto> getEquInfoList(List<String> keys,int type){
		List<EquityInfo001Dto> resultList = new ArrayList<EquityInfo001Dto>();
//		int maxDataCountOneTime =(null == reportSelectOrDeleteCountForShenFen ? 100:Integer.parseInt(reportSelectOrDeleteCountForShenFen));
//		int dataCount=0;
//		if(keys != null){
//			dataCount = keys.size();
//        }
//        if (dataCount <= maxDataCountOneTime) {
		if(0 == type){
			List<EquityInfo001Dto>	resultListAll=new  ArrayList<EquityInfo001Dto>();
			logger.info("查询库中未报送数据及check表数据SQL流式查询开始");

			// add by zcw 三期性能优化 start

			if(keys!=null && keys.size()>=1000){
				// 删除临时表数据
				chbEquHoldCheckDtoMapper.truncateEquityTmp();
				// 六要素入临时表。sql对临时表操作
				saveKeys(keys);
				template.select("com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper.queryEquityInfoListMax",
						new ResultHandler() {
							@Override
							public void handleResult(ResultContext resultContext) {
								EquityInfo001Dto pos = (EquityInfo001Dto) resultContext.getResultObject();
								resultListAll.add(pos);
							}
						});
			}else{

				List<ChbEquTmp> list = keysToList(keys);

				Map<String,Object> params = new HashMap<String, Object>();
				params.put("list", list);
				template.select("com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper.queryEquityInfoList",params,
						new ResultHandler() {
							@Override
							public void handleResult(ResultContext resultContext) {
								EquityInfo001Dto pos = (EquityInfo001Dto) resultContext.getResultObject();
								resultListAll.add(pos);
							}
						});
				list.clear();
			}
			// add by zcw 三期性能优化 end

			logger.info("查询库中未报送数据及check表数据SQL流式查询结束");
//        		List<EquityInfo001Dto>	resultListAll = equityInfoDistinctMapper.queryEquityInfoList(keys);
			if(resultListAll==null||resultListAll.isEmpty()){
				return resultList;
			}
			logger.info("resultListAll数据转换成Map开始");
			Map<String, List<EquityInfo001Dto>> allListMap=resultListAll.stream().collect(
					JoyinCollectors.groupingBy(
							single ->
									(single.getBankCode()==null?"":single.getBankCode())+GlobalConstant.UNDER_LINE +(single.getIdentifyNumber()==null?"":single.getIdentifyNumber())
											+GlobalConstant.UNDER_LINE+(single.getHolderName()==null?"":single.getHolderName())+GlobalConstant.UNDER_LINE
											+(single.getPersonalIdentifyType()==null?"":single.getPersonalIdentifyType())+GlobalConstant.UNDER_LINE+(single.getInstitutionIdentifyType()==null?"":single.getInstitutionIdentifyType())
											+GlobalConstant.UNDER_LINE+(single.getOtherIdentifyName()==null?"":single.getOtherIdentifyName())));
			logger.info("resultListAll数据转换成Map结束");
			// 释放集合，防止内存溢出开始 2021-08-26 by caozhenming
			resultListAll.clear();
			// 释放集合，防止内存溢出结束 2021-08-26 by caozhenming
			logger.info("循环keys取存在于kes中的未报送数据开始");
			for(String key:keys){
//        			String key= single.getBankCode()==null?"":single.getBankCode()+GlobalConstant.UNDER_LINE +single.getIdentifyNumber()==null?"":single.getIdentifyNumber()
//        			+GlobalConstant.UNDER_LINE+single.getHolderName()==null?"":single.getHolderName()+GlobalConstant.UNDER_LINE
//        			+single.getPersonalIdentifyType()==null?"":single.getPersonalIdentifyType()+GlobalConstant.UNDER_LINE+single.getInstitutionIdentifyType()==null?"":single.getInstitutionIdentifyType()
//        			+GlobalConstant.UNDER_LINE+single.getOtherIdentifyName()==null?"":single.getOtherIdentifyName();
				if(allListMap.containsKey(key)){
					resultList.addAll(allListMap.get(key));
				}
			}
			logger.info("循环keys取存在于kes中的未报送数据结束");
		}else{
			resultList = equityInfoDistinctMapper.queryRepeatEquityInfoUnSubmit(keys);
		}
		return resultList;
//        }
//
//        List<String> newDataList = new ArrayList<String>();
//        newDataList.addAll(keys);
//
//        // 分批处理
//        int part = dataCount / maxDataCountOneTime; // 分批数
//        for (int i = 0; i < part; i++ ) {
//        	if(0 == type){
//        		resultList.addAll(equityInfoDistinctMapper.queryEquityInfoList(newDataList.subList(0, maxDataCountOneTime)));
//        	}else{
//        		resultList.addAll(equityInfoDistinctMapper.queryRepeatEquityInfoUnSubmit(newDataList.subList(0, maxDataCountOneTime)));
//        	}
//        	newDataList.subList(0, maxDataCountOneTime).clear();
//        }
//        // 最后剩下的数据
//        if (CollectionUtils.isNotEmpty(newDataList)) {
//        	if(0 == type){
//        		resultList.addAll(equityInfoDistinctMapper.queryEquityInfoList(newDataList));
//        	}else{
//        		resultList.addAll(equityInfoDistinctMapper.queryRepeatEquityInfoUnSubmit(newDataList));
//        	}
//        }
//        return resultList;
	}
	
	/**
	 * 维护表删除
	 * @param equityList
	 * @param keys
	 */
	private void deleteEquitInfo(List<String> keys){
		int maxDataCountOneTime =(null == reportSelectOrDeleteCountForShenFen ? 100:Integer.parseInt(reportSelectOrDeleteCountForShenFen));
		int dataCount=0;
		if(CollectionUtils.isEmpty(keys)){
			return;
		}

		if(keys !=null){
			dataCount = keys.size();
		}
		if (CollectionUtils.isNotEmpty(keys)&&dataCount <= maxDataCountOneTime) {
			List<ChbEquTmp> list = keysToList(keys);
			chbEquHoldCheckDtoMapper.deleteEquityInfoBatch(list);
			list.clear();
			return;
		}

		List<String> newDataList = new ArrayList<String>();
		newDataList.addAll(keys);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			List<ChbEquTmp> list = keysToList(newDataList.subList(0, maxDataCountOneTime));
			chbEquHoldCheckDtoMapper.deleteEquityInfoBatch(list);
			newDataList.subList(0, maxDataCountOneTime).clear();
			list.clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			List<ChbEquTmp> list = keysToList(newDataList);
			chbEquHoldCheckDtoMapper.deleteEquityInfoBatch(list);
			list.clear();
		}
	}

	/**
	 * 登记表删除
	 * @param equityList
	 * @param keys
	 */
	private void deleteEquitInfoSub(List<String> keys){

		int maxDataCountOneTime =(null == reportSelectOrDeleteCountForShenFen ? 100:Integer.parseInt(reportSelectOrDeleteCountForShenFen));
		int dataCount = 0;
		if(CollectionUtils.isEmpty(keys)){
			return;
		}
		if(keys != null){
			dataCount = keys.size();
		}
		if (CollectionUtils.isNotEmpty(keys)&&dataCount <= maxDataCountOneTime) {
			List<ChbEquTmp> list = keysToList(keys);
			chbEquHoldCheckDtoMapper.deleteEquitySubInfoBatch(list);
			list.clear();
			return;
		}

		List<String> newDataList = new ArrayList<String>();
		newDataList.addAll(keys);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			List<ChbEquTmp> list = keysToList(newDataList.subList(0, maxDataCountOneTime));
			chbEquHoldCheckDtoMapper.deleteEquitySubInfoBatch(list);
			newDataList.subList(0, maxDataCountOneTime).clear();
			list.clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			List<ChbEquTmp> list = keysToList(newDataList);
			chbEquHoldCheckDtoMapper.deleteEquitySubInfoBatch(list);
			list.clear();
		}
	}

	/**
	 * 持久化数据库
	 * @param list
	 */
	private void insertCheckData(List<EquityInfo001Dto> list) {
		SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		//批量插入mapper
		ChbEquHoldCheckDtoMapper mapper =  session.getMapper(ChbEquHoldCheckDtoMapper.class);
		try{
			int count = 0;
			ChbEquHoldCheckDto model = null;
			for(EquityInfo001Dto dto : list){
				model = transformModelToCheck(dto);
				model.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
				if(!DictCodeListExt2.处理状态_中债_已合并.getValue().equals(model.getOperatestatus())){
					model.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
				}
				mapper.insert(model);
				count++;
				if(count % 1000==0){
					session.commit();
					session.clearCache();
				}
			}
			session.commit();
			session.clearCache();
		}
		finally {
			session.close();
		}

	}

	/**
	 * 返回数据类型：
	 *    key:三要素  value:身份信息List
	 * 比如：
	 *   key: 张三+身份证+340202021111
	 * value: 张三+身份证+340202021111+001的身份信息  
	 * 		     张三+身份证+340202021111+002的身份信息
	 * 		     张三+身份证+340202021111+003的身份信息
	 * @param infoList
	 * @param equityList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,List<EquityInfo001Dto>> getTotalMap(List<EquityInfo001Dto> infoList,
														   @SuppressWarnings("rawtypes") List equityList){
		Map<String,EquityInfo001Dto> dictictMap = new HashMap<String,EquityInfo001Dto>();

		String key = "";
		for(EquityInfo001Dto dto : infoList){
			key = getKey(dto) + GlobalConstant.UNDER_LINE + dto.getIndentifyMark();

			if(!dictictMap.containsKey(key)){
				dictictMap.put(key, dto);
			}
		}

		for (Object map : equityList) {
			EquityInfo001Dto tmp = new EquityInfo001Dto();
			if(map instanceof Map){
				EquityInfoMaintenanceService.maptoDto((Map<String, String>)map, tmp);
				if(StringUtil.isNotEmpty(((Map<String,String>)map).get("ORG_INDENTIFY_MARK"))){
					continue;
				}
			}else if(map instanceof  EquityInfo001Dto){
				tmp = (EquityInfo001Dto)map;
				if(StringUtil.isNotEmpty(((EquityInfo001Dto)tmp).getOrgIndentifyMark())){
					continue;
				}
			}
			key = getKey(tmp) + GlobalConstant.UNDER_LINE + tmp.getIndentifyMark();
			String updateFlg = SvrDataUtil.getSysParam("SVR_EQU_REPAIR_BATCH_FLAG");
			//只有当系统参数为01：使用修复批的情况下，才取以前保存在库中的check表数据
			if("01".equals(updateFlg)) {
				//取得导入前已经在check表存在的数据
				EquityInfo001Dto handleDto = dictictMap.get(key);
				
				//已经在check表中存在的情况下，进行修复字段的处理
				if(StringUtil.isNotEmpty(handleDto)) {
					//设置 账号开户所在地
					if(StringUtil.isEmpty(tmp.getFundAccPlace())) {
						tmp.setFundAccPlace(handleDto.getFundAccPlace());
					}
					//设置 权益人所属银行代码
					if(StringUtil.isEmpty(tmp.getTheBankCode())) {
						tmp.setTheBankCode(handleDto.getTheBankCode());
					}
				}	
			}
			
			dictictMap.put(key, tmp);
		}

		List<EquityInfo001Dto> allList = new ArrayList<EquityInfo001Dto>();
		for(Map.Entry<String, EquityInfo001Dto> entry: dictictMap.entrySet()){
			allList.add(entry.getValue());
		}

		Map<String, List<EquityInfo001Dto>> infoMap = allList.stream().collect(JoyinCollectors.groupingBy(
				x -> getKey(x)));


//		//文件中的数据进行分组
//		Map<String, List<EquityInfo001Dto>> fileMap = tmpList.stream().collect(JoyinCollectors.groupingBy(
//											                x -> getKey(x)));// 其他证件名称

//		//【三要素+识别标识】相同的数据，以文件中的最新数据为准
//		if(!infoMap.isEmpty()){
//			resultMap.putAll(infoMap);
//		}
//		if(!fileMap.isEmpty()){
//			String key = "";
//			String key1 = "";
//			//对比文件中的数据，三要素相同且识别标识一致，则以文件中数据为准
//			List<EquityInfo001Dto> newList = null;
//			
//			for(Map.Entry<String, List<EquityInfo001Dto>> entry: fileMap.entrySet()){
//				
//				if(resultMap.containsKey(entry.getKey())){
//					newList = new ArrayList<EquityInfo001Dto>();
//					
//					List<EquityInfo001Dto> list = resultMap.get(entry.getKey());
//					
//					//找重复数据,以文件最新为准
//					label:
//					for(Iterator<EquityInfo001Dto> iterator = list.iterator();iterator.hasNext();){
//						EquityInfo001Dto dto = iterator.next();
//						key = getKey(dto);
//						
//						for(EquityInfo001Dto model : entry.getValue()){
//							key1 =  getKey(model);
//							if((key + GlobalConstant.UNDER_LINE + dto.getIndentifyMark())
//							   .equals(key1 + GlobalConstant.UNDER_LINE + model.getIndentifyMark())){
//								//以文件中最新的为准
//								newList.add(model);
//								continue label;
//							}else{
//								newList.add(model);
//							}
//						}
//						newList.add(dto);
//					}
//					resultMap.put(entry.getKey(), newList);
//				}else{
//					resultMap.put(entry.getKey(), entry.getValue());
//				}
//			}
//		}

		return infoMap;
	}

	/**
	 *
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getKey(Object dto) {
		if(dto instanceof EquityInfo001Dto){
			return  (null == ((EquityInfo001Dto) dto).getBankCode()?"":((EquityInfo001Dto) dto).getBankCode())//注册银行
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfo001Dto) dto).getIdentifyNumber()?"":((EquityInfo001Dto) dto).getIdentifyNumber())//证件号码
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfo001Dto) dto).getHolderName()?"":((EquityInfo001Dto) dto).getHolderName())//权益人名称
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfo001Dto) dto).getPersonalIdentifyType()?"":((EquityInfo001Dto) dto).getPersonalIdentifyType())//个人证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfo001Dto) dto).getInstitutionIdentifyType()?"":((EquityInfo001Dto) dto).getInstitutionIdentifyType())//机构证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfo001Dto) dto).getOtherIdentifyName()?"":((EquityInfo001Dto) dto).getOtherIdentifyName());// 其他证件名称

		}else if(dto instanceof EquityInfoRegist001Dto){
			return (null == ((EquityInfoRegist001Dto) dto).getBankCode()?"":((EquityInfoRegist001Dto) dto).getBankCode())//注册银行
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfoRegist001Dto) dto).getIdentifyNumber()?"":((EquityInfoRegist001Dto) dto).getIdentifyNumber())//证件号码
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfoRegist001Dto) dto).getHolderName()?"":((EquityInfoRegist001Dto) dto).getHolderName())//权益人名称
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfoRegist001Dto) dto).getPersonalIdentifyType()?"":((EquityInfoRegist001Dto) dto).getPersonalIdentifyType())//个人证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfoRegist001Dto) dto).getInstitutionIdentifyType()?"":((EquityInfoRegist001Dto) dto).getInstitutionIdentifyType())//机构证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((EquityInfoRegist001Dto) dto).getOtherIdentifyName()?"":((EquityInfoRegist001Dto) dto).getOtherIdentifyName());// 其他证件名称
		}else if(dto instanceof ChbEquHoldCheckDto){
			return (null == ((ChbEquHoldCheckDto) dto).getRegistBankCode()?"":((ChbEquHoldCheckDto) dto).getRegistBankCode())//注册银行
					+ GlobalConstant.UNDER_LINE + (null == ((ChbEquHoldCheckDto) dto).getIdentifyNumber()?"":((ChbEquHoldCheckDto) dto).getIdentifyNumber())//证件号码
					+ GlobalConstant.UNDER_LINE + (null == ((ChbEquHoldCheckDto) dto).getHolderName()?"":((ChbEquHoldCheckDto) dto).getHolderName())//权益人名称
					+ GlobalConstant.UNDER_LINE + (null == ((ChbEquHoldCheckDto) dto).getPersonalIdentifyType()?"":((ChbEquHoldCheckDto) dto).getPersonalIdentifyType())//个人证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((ChbEquHoldCheckDto) dto).getInstitutionIdentifyType()?"":((ChbEquHoldCheckDto) dto).getInstitutionIdentifyType())//机构证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((ChbEquHoldCheckDto) dto).getOtherIdentifyName()?"":((ChbEquHoldCheckDto) dto).getOtherIdentifyName());// 其他证件名称
		}else if(dto instanceof Map){
			return (null == ((Map<String, String>)dto).get("REGIST_BANK_CODE") ? "" : ((Map<String, String>) dto).get("REGIST_BANK_CODE"))//注册银行
					+ GlobalConstant.UNDER_LINE + (null == ((Map<String, String>) dto).get("IDENTIFY_NUMBER") ? "" : ((Map<String, String>) dto).get("IDENTIFY_NUMBER"))//证件号码
					+ GlobalConstant.UNDER_LINE + (null == ((Map<String, String>) dto).get("HOLDER_NAME") ? "" : ((Map<String, String>) dto).get("HOLDER_NAME"))//权益人名称
					+ GlobalConstant.UNDER_LINE + (null == ((Map<String, String>) dto).get("PERSONAL_IDENTIFY_TYPE") ? "" : ((Map<String, String>) dto).get("PERSONAL_IDENTIFY_TYPE"))//个人证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((Map<String, String>) dto).get("INSTITUTION_IDENTIFY_TYPE") ? "" : ((Map<String, String>) dto).get("INSTITUTION_IDENTIFY_TYPE"))//机构证件类别
					+ GlobalConstant.UNDER_LINE + (null == ((Map<String, String>) dto).get("OTHER_IDENTIFY_NAME") ? "" : ((Map<String, String>) dto).get("OTHER_IDENTIFY_NAME"));
		}else{

		}
		return null;
	}

	/**
	 *
	 * @param equityList
	 * @return
	 */
	public List<String> getKeys(@SuppressWarnings("rawtypes") List equityList) {
		List<String> keys = new ArrayList<String>();
		Set<String> skeys = new HashSet<String>();
		String key = "";
		for(Object map : equityList ){
			key = getKey(map);
			skeys.add(key);
		}
		for(String sk:skeys){
			keys.add(sk);
		}
		return keys;
	}

	/**
	 *
	 * @param equityInfo001Dto
	 * @return
	 */
	private Map<String,String> modelToMapByPrpperties(EquityInfo001Dto equityInfo001Dto){
		Map<String,String> map = new HashMap<String,String>();
		if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
			map.put("INDENTIFY_MARK", equityInfo001Dto.getIndentifyMark()== null ? "" : equityInfo001Dto.getIndentifyMark());
			map.put("ORG_INDENTIFY_MARK",equityInfo001Dto.getOrgIndentifyMark()== null ? "" : equityInfo001Dto.getOrgIndentifyMark());
			map.put("REGIST_BANK_CODE",equityInfo001Dto.getBankCode()== null ? "" : equityInfo001Dto.getBankCode());
			map.put("IS_BELONG_BANK",equityInfo001Dto.getIsBelongBank()== null ? "" : equityInfo001Dto.getIsBelongBank());
			map.put("THE_BANK_NAME",equityInfo001Dto.getTheBankName()== null ? "" : equityInfo001Dto.getTheBankName());
			map.put("THE_BANK_CODE",equityInfo001Dto.getTheBankCode()== null ? "" : equityInfo001Dto.getTheBankCode());
			map.put("HOLDER_MARK",equityInfo001Dto.getHolderMark()== null ? "" : equityInfo001Dto.getHolderMark());
			map.put("HOLDER_REGION",equityInfo001Dto.getHolderRegion()== null ? "" : equityInfo001Dto.getHolderRegion());
			map.put("IDENTIFY_NUMBER",equityInfo001Dto.getIdentifyNumber()== null ? "" : equityInfo001Dto.getIdentifyNumber());
			map.put("HOLDER_TYPE",equityInfo001Dto.getHolderType()== null ? "" : equityInfo001Dto.getHolderType());
			map.put("PERSONAL_IDENTIFY_TYPE",equityInfo001Dto.getPersonalIdentifyType()== null ? "" : equityInfo001Dto.getPersonalIdentifyType());
			map.put("INSTITUTION_IDENTIFY_TYPE",equityInfo001Dto.getInstitutionIdentifyType()== null ? "" : equityInfo001Dto.getInstitutionIdentifyType());
			map.put("OTHER_IDENTIFY_NAME",equityInfo001Dto.getOtherIdentifyName()== null ? "" : equityInfo001Dto.getOtherIdentifyName());
			map.put("SPV_CAP_ACC_BANK",equityInfo001Dto.getSpvCapAccBank()== null ? "" :equityInfo001Dto.getSpvCapAccBank());
			map.put("OTHER_CAP_ACC_BANK",equityInfo001Dto.getOtherCapAccBank()== null ? "" : equityInfo001Dto.getOtherCapAccBank());
			map.put("SPV_REGIST_CODE",equityInfo001Dto.getSpvRegistCode()== null ? "" : equityInfo001Dto.getSpvRegistCode());
			map.put("HOLDER_DATE",null == equityInfo001Dto.getHolderDate() ? "" : equityInfo001Dto.getHolderDate().toString());
			map.put("HOLDER_NAME", equityInfo001Dto.getHolderName()== null ? "" : equityInfo001Dto.getHolderName());
			map.put("HOLDER_SEX",equityInfo001Dto.getHolderSex()== null ? "" : equityInfo001Dto.getHolderSex());
			map.put("HOLDER_RISK_PREFER",equityInfo001Dto.getHolderRiskPrefer()== null ? "" : equityInfo001Dto.getHolderRiskPrefer());
			map.put("HOLDER_CELLPHONE",equityInfo001Dto.getHolderCellphone()== null ? "" : equityInfo001Dto.getHolderCellphone());
			map.put("HOLDER_TELEPHONE",equityInfo001Dto.getHolderTelephone()== null ? "" : equityInfo001Dto.getHolderTelephone());
			map.put("HOLDER_EMAIL", equityInfo001Dto.getHolderEmail()== null ? "" : equityInfo001Dto.getHolderEmail());
			map.put("REMARK",equityInfo001Dto.getRemark()== null ? "" : equityInfo001Dto.getRemark());
			map.put("ORG_CODE",equityInfo001Dto.getOrgCode()== null ? "C0403" : equityInfo001Dto.getOrgCode() );
			map.put("AGENT",equityInfo001Dto.getAgent()== null ? "" : equityInfo001Dto.getAgent());
			map.put("DEPT_CODE",equityInfo001Dto.getDeptCode()== null ? "" : equityInfo001Dto.getDeptCode());
			map.put("DATATYPE",equityInfo001Dto.getDatatype()== null ? "01" : equityInfo001Dto.getDatatype());
			map.put("HOLDER_CLASSIFY",equityInfo001Dto.getHolderClassify()== null ? "" : equityInfo001Dto.getHolderClassify());
			map.put("FUND_ACC_PLACE",equityInfo001Dto.getFundAccPlace()== null ? "" : equityInfo001Dto.getFundAccPlace());

		}else{
			map.put("INDENTIFY_MARK", equityInfo001Dto.getIndentifyMark());
			map.put("ORG_INDENTIFY_MARK",equityInfo001Dto.getOrgIndentifyMark());
			map.put("REGIST_BANK_CODE",equityInfo001Dto.getBankCode());
			map.put("IS_BELONG_BANK",equityInfo001Dto.getIsBelongBank());
			map.put("THE_BANK_NAME",equityInfo001Dto.getTheBankName());
			map.put("THE_BANK_CODE",equityInfo001Dto.getTheBankCode());
			map.put("HOLDER_MARK",equityInfo001Dto.getHolderMark());
			map.put("HOLDER_REGION",equityInfo001Dto.getHolderRegion());
			map.put("IDENTIFY_NUMBER",equityInfo001Dto.getIdentifyNumber());
			map.put("HOLDER_TYPE",equityInfo001Dto.getHolderType());
			map.put("PERSONAL_IDENTIFY_TYPE",equityInfo001Dto.getPersonalIdentifyType());
			map.put("INSTITUTION_IDENTIFY_TYPE",equityInfo001Dto.getInstitutionIdentifyType());
			map.put("OTHER_IDENTIFY_NAME",equityInfo001Dto.getOtherIdentifyName());
			map.put("SPV_CAP_ACC_BANK",equityInfo001Dto.getSpvCapAccBank());
			map.put("OTHER_CAP_ACC_BANK",equityInfo001Dto.getOtherCapAccBank());
			map.put("SPV_REGIST_CODE",equityInfo001Dto.getSpvRegistCode());
			map.put("HOLDER_DATE",null == equityInfo001Dto.getHolderDate() ? "" : equityInfo001Dto.getHolderDate().toString());
			map.put("HOLDER_NAME", equityInfo001Dto.getHolderName());
			map.put("HOLDER_SEX",equityInfo001Dto.getHolderSex());
			map.put("HOLDER_RISK_PREFER",equityInfo001Dto.getHolderRiskPrefer());
			map.put("HOLDER_CELLPHONE",equityInfo001Dto.getHolderCellphone());
			map.put("HOLDER_TELEPHONE",equityInfo001Dto.getHolderTelephone());
			map.put("HOLDER_EMAIL", equityInfo001Dto.getHolderEmail());
			map.put("REMARK",equityInfo001Dto.getRemark());
			map.put("ORG_CODE",equityInfo001Dto.getOrgCode());
			map.put("AGENT",equityInfo001Dto.getAgent());
			map.put("DEPT_CODE",equityInfo001Dto.getDeptCode());
			map.put("HOLDER_CLASSIFY",equityInfo001Dto.getHolderClassify()== null ? "" : equityInfo001Dto.getHolderClassify());
			map.put("FUND_ACC_PLACE",equityInfo001Dto.getFundAccPlace()== null ? "" : equityInfo001Dto.getFundAccPlace());

		}
		return map;
	}

	/**
	 *
	 * @param dto
	 * @return
	 */
	private EquityInfo001Dto transformModelToEquHold(ChbEquHoldCheckDto dto){
		EquityInfo001Dto temp = new EquityInfo001Dto();
		BeanUtils.copyProperties(dto,temp);
		temp.setBankCode(dto.getRegistBankCode());
		return temp;

	}

	/**
	 * 
	 * @param equityInfo001Dto
	 * @return
	 */
	private ChbEquHoldCheckDto transformModelToCheck(EquityInfo001Dto equityInfo001Dto){
		ChbEquHoldCheckDto temp=new ChbEquHoldCheckDto();
		BeanUtils.copyProperties(equityInfo001Dto,temp);
		temp.setRegistBankCode(equityInfo001Dto.getBankCode());
		return temp;
		
	}
	
	/**
	 *
	 * @param equityInfo001Dto
	 * @return
	 */
	private ChbEquHoldCheckDto transformRegModelToCheck(EquityInfoRegist001Dto equityInfo001Dto){
		ChbEquHoldCheckDto temp=new ChbEquHoldCheckDto();
		BeanUtils.copyProperties(equityInfo001Dto,temp);
		temp.setRegistBankCode(equityInfo001Dto.getBankCode());
		return temp;
	}
	public List<EquityInfoRegist001Dto> checkBeforeSubmit(){
		return checkBeforeSubmit(null,"02");
	}
	/**
	 * 查询出未报送且在维护表中有重复的身份信息
	 * @param type 01 数据检查 02自动检查报送
	 * @return
	 */
	public List<EquityInfoRegist001Dto> checkBeforeSubmit(List<EquityInfoRegist001Dto> registList,String type){
		List<String> keyst = null;
		if(registList!=null){
			keyst = getKeys(registList);
		}
		List<EquityInfo001Dto> listR= getEquInfoList(keyst,1);
		if(listR!=null&&!listR.isEmpty()){

			String mergerFlag = SvrDataUtil.getSysParam("SVR_EQU_HOLD_MERGER");
			if("0".equals(mergerFlag)){//如果是手动
				//插入检查表
//				ChbEquHoldCheckDto model = null;
//				for(EquityInfo001Dto regDto: listR){
//					model = transformModelToTemp(regDto);
//					model.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
//					 if(null == chbEquHoldCheckDtoMapper.selectByPrimarykey(model.getIndentifyMark())){
//						 chbEquHoldCheckDtoMapper.insert(model);
//					 }
//				}
				//删除维护表和登记表
				List<String> keys = new ArrayList<>();
				for(EquityInfo001Dto single:listR){
					String key=single.getBankCode()+GlobalConstant.UNDER_LINE +single.getIndentifyMark();
					keys.add(key);
				}
//				chbEquHoldCheckDtoMapper.deleteEquityInfoBatch(keys);
//	        	chbEquHoldCheckDtoMapper.deleteEquitySubInfoBatch(keys);
				//查询出对应的未报送登记信息
				List<EquityInfoRegist001Dto> djlist= getEquInfoSubList(listR,1);
				checkAllData(listR, getKeys(listR),"01");
				//更新原检查表和登记表检查检查状态
				dochecksafe(djlist,"02");
				if("01".equals(type)){
					for(EquityInfoRegist001Dto single:djlist){
						single.setRemark("检查不通过，该投资者存在多个识别标识！");
					}
					return djlist;
				}
			}else{
				//插入检查表
//				ChbEquHoldCheckDto model = null;
//				for(EquityInfo001Dto regDto: listR){
//					model = transformModelToTemp(regDto);
//					model.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
//					 if(null == chbEquHoldCheckDtoMapper.selectByPrimarykey(model.getIndentifyMark())){
//						 chbEquHoldCheckDtoMapper.insert(model);
//					 }
//				}
				//删除维护表和登记表
				//删除维护表和登记表
				List<String> keys = getKeys(listR);
//				chbEquHoldCheckDtoMapper.deleteEquityInfoBatch(keys);
				deleteEquitInfo(keys);
//	        	chbEquHoldCheckDtoMapper.deleteEquitySubInfoBatch(keys);
				deleteEquitInfoSub(keys);
				checkAllData(listR, keys,"01");
				//调用合并处理
				List<EquityInfo001Dto> listN=new ArrayList<EquityInfo001Dto>();
				merger(listN,"dto");
				List<Map<String, String>> listType = new ArrayList<Map<String, String>>();
				for(EquityInfo001Dto single:listN){
					Map<String,String > map=new HashMap<String,String>();
					if (StringUtil.isNotEmpty(single.getOrgIndentifyMark())
							&& !single.getIndentifyMark().equals(
							single.getOrgIndentifyMark())) {
						map.put("type", "02");
					} else {
						map.put("type", "01");

					}
					listType.add(map);
				}
				//合并后的结果调用原来接口入库逻辑
				EquityInfo001Service equityInfoDistinctService =
						SpringContextUtil.getBean(EquityInfo001Service.class);
				equityInfoDistinctService.dealAfterCheck(listType, listN,DictCodeListExt2.数据来源_EXCEL导入.getValue(),null, null);
				List<String> keys1 = new ArrayList<>();
				for(EquityInfo001Dto single:listN){
					String key=single.getBankCode()+GlobalConstant.UNDER_LINE +single.getIndentifyMark();
					keys1.add(key);
				}
//				chbEquHoldCheckDtoMapper.deleteEquityInfoBatch(keys);
//	        	chbEquHoldCheckDtoMapper.deleteEquitySubInfoBatch(keys);
				//查询出对应的未报送登记信息
				List<EquityInfoRegist001Dto> djlist = getEquInfoSubList(listN,1);
				//更新原检查表和登记表检查检查状态
				dochecksafe(djlist,"01");
				if("01".equals(type)){
					for(EquityInfoRegist001Dto single:djlist){
						single.setRemark("检查通过！");
					}
				}
				return djlist;
			}
		}else{
			if("02".equals(type))
				return registList;
		}
		return null;
	}
	void dochecksafe(List<EquityInfoRegist001Dto> equityInfoRegistRstList,String type){
		List<EquityInfoRegist001Dto> equityInfoRegistUpdateList = new ArrayList<>();
		List<CheckReport001Dto> resultList = new ArrayList<>();
		List<String> checkseqnos= new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(equityInfoRegistRstList)){
			equityInfoRegistRstList.forEach(item->{
				if(item.getCheckSeqno()!=null&&!item.getCheckSeqno().equals("")&&!item.getCheckSeqno().equals("0")){
					checkseqnos.add(item.getCheckSeqno());
				}
				//检查结果
				CheckReport001Dto chbReportCheck = new CheckReport001Dto();
				BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
				chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
				chbReportCheck.setRepCode(DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
				chbReportCheck.setRepName(DictCodeListExt2.报表名称_投资者身份信息登记.getCodeName());
				if(type.equals("01")){
					chbReportCheck.setCheckResult("01");
				}else{
					chbReportCheck.setCheckResult("02");
					chbReportCheck.setErrorInfo("检查不通过，该投资者存在多个识别标识！");
				}
				resultList.add(chbReportCheck);

				//更新检查序列号对应的数据
				EquityInfoRegist001Dto dto = new EquityInfoRegist001Dto();
				dto.setIndentifyMark(item.getIndentifyMark());
				dto.setCheckSeqno(chbReportCheck.getCheckSeqno());
				equityInfoRegistUpdateList.add(dto);
			});
		}

		//批量删除之前的检查数据
		chbReportCheckMapper.delReportCheckDatas(checkseqnos);
		if(CollectionUtils.isNotEmpty(resultList)){
			//批量插入报送前检查表数据
			chbReportCheckMapper.batchInsert(resultList);
			//批量更新投资者身份信息检查序列号
			if(CollectionUtils.isNotEmpty(equityInfoRegistUpdateList)){
				equityInfoRegist001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
			}
		}
	}
	/**
	 * @Title: rejectUnqualifiedData
	 * @Description: (投资者类别、证件号码、投资者姓名、证件类型有一项为空，则改该数据进入维护表，且数据状态为待处理)
	 * @param @param equityList    上游文件解析出的结果
	 * @return void    返回类型
	 * @throws
	 */
	public List<Map<String, String>> rejectUnqualifiedData(List<Map<String, String>> equityList) {
		// 待处理数据集合
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		Iterator<Map<String, String>> it = equityList.iterator();
		while (it.hasNext()) {
			Map<String, String> mapStr = it.next();
			/**机构和部门代码 为空时，给默认值  add by 徽商个性化 chaowei 2019-12-03 begin**/
			if (StringUtil.isEmpty(mapStr.get("ORG_CODE"))) {
				mapStr.put("ORG_CODE", SvrDataUtil.getSysParam("CHB_ORG_CODE"));
			}
			if (StringUtil.isEmpty(mapStr.get("DEPT_CODE"))) {
				mapStr.put("DEPT_CODE", SvrDataUtil.getSysParam("CHB_DEPT_CODE"));
			}
			/**机构和部门代码 为空时，给默认值  add by 徽商个性化 chaowei 2019-12-03 end**/


			/**投资者属于境内时，所属国家和地区要为空  add by 徽商个性化  chaowei 2019-12-03 begin**/
			if (!StringUtil.isEmpty(mapStr.get("HOLDER_REGION")) && "CHN".equals(mapStr.get("HOLDER_REGION")) ) {
				mapStr.put("HOLDER_REGION", "");
			}
			/**投资者属于境内时，所属国家和地区要为空  add by 徽商个性化 chaowei 2019-12-03 end**/

			// 投资者类别
			if (StringUtil.isEmpty(mapStr.get("HOLDER_TYPE")) ||
					// 证件号码
					StringUtil.isEmpty(mapStr.get("IDENTIFY_NUMBER")) ||
					// 投资者姓名
					StringUtil.isEmpty(mapStr.get("HOLDER_NAME")) ) {
				// 存入待处理数据
				dataList.add(mapStr);
				// 移除错误数据
				it.remove();
			}else if(
				// 个人证件类型
					StringUtil.isEmpty(mapStr.get("PERSONAL_IDENTIFY_TYPE"))&&
							// 机构证件类型
							StringUtil.isEmpty(mapStr.get("INSTITUTION_IDENTIFY_TYPE"))){
				// 存入待处理数据
				dataList.add(mapStr);
				// 移除错误数据
				it.remove();
			}
		}
		// 待处理数据批量插入维护表
		return dataList;
	}
	
	/**
	 * @Title: tranStringToDtoForKey
	 * @Description: 五要素String拆分为字段，并设置到DTO中
	 * @param @param keys    拼接的五要素信息
	 * @return List<EquityInfo001Dto>    返回类型
	 * @throws
	 */
	public List<EquityInfo001Dto>  tranStringToDtoForKey(List<String> keys) {
		//对五要素主键进行重组
		List<EquityInfo001Dto> keyList = new ArrayList<EquityInfo001Dto>();
		
		for(String str : keys) {
			EquityInfo001Dto hdlDto = new EquityInfo001Dto();
			String strList[] = str.split(GlobalConstant.UNDER_LINE , -1);
			//银行代码	
			if(StringUtil.isNotEmpty(strList[0])) {
				hdlDto.setBankCode(strList[0]);
			} 
			//证件号码	
			if(StringUtil.isNotEmpty(strList[1])) {
				hdlDto.setIdentifyNumber(strList[1]);
			}
			//权益人名称
			if(StringUtil.isNotEmpty(strList[2])) {
				hdlDto.setHolderName(strList[2]);
			}
			//个人证件类别
			if(StringUtil.isNotEmpty(strList[3])) {
				hdlDto.setPersonalIdentifyType(strList[3]);
			}
			//机构证件类别		
			if(StringUtil.isNotEmpty(strList[4])) {
				hdlDto.setInstitutionIdentifyType(strList[4]);
			}
			//其他证件名称
			if(StringUtil.isNotEmpty(strList[5])) {
				hdlDto.setOtherIdentifyName(strList[5]);
			}
			
			keyList.add(hdlDto);
		}
		
		//返回五要素的DTO
		return keyList;
	}
		
	/**
	 * insertCheckByBatch
	 *分批执行
	 * @param insert 数据
	 * @see
	 * @since 1.0
	 */
	private void insertCheckByBatch(List<ChbEquHoldCheckDto> insertList) {
		if (StringUtil.isEmptyList(insertList)) {
			return;
		}
		int maxDataCountOneTime = reportInsertCount;
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
		if (isDB2DataBase) {
			maxDataCountOneTime = 1;
		}
		int dataCount = insertList.size();
		if (dataCount <= maxDataCountOneTime) {
			logger.info("insertByBatch单次处理开始：" + DateUtil.getCurrentDateTime());
			chbEquHoldCheckDtoMapper.insertByBatch(insertList);
			logger.info("insertByBatch单次处理结束：" + DateUtil.getCurrentDateTime());
			return;
		}
		/*** add by zcw 三期性能优化 start **/

		// 分批处理
		logger.info("insertByBatch批量处理开始：" + DateUtil.getCurrentDateTime());
		// 临时文件存放目录
		if(!StringUtil.isEmptyList(insertList) && insertList.size()>1000){
			String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
			ImpTable table = new ImpTable("CHB_EQU_HOLD_CHECK");
			table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
			for (ImpColumn column : table.getColumns()) {
				if (StringUtil.equals(column.getName(), "OPERATE_STATUS")) {
					column.setValueField("operatestatus");
				}
				if (StringUtil.equals(column.getName(), "MERGER_INDENTIFY_MARK")) {
					column.setValueField("mergerindentifymark");
				}
			}
			batchInsertService.exeBatchldr(tmpPath, insertList, table);
		}else{
			List<ChbEquHoldCheckDto> newDataList = new ArrayList<ChbEquHoldCheckDto>();
			newDataList.addAll(insertList);

			// 分批处理
			logger.info("insertByBatch批量处理开始：" + DateUtil.getCurrentDateTime());
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++ ) {
				chbEquHoldCheckDtoMapper.insertByBatch(newDataList.subList(0, maxDataCountOneTime));
				newDataList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newDataList)) {
				chbEquHoldCheckDtoMapper.insertByBatch(newDataList);
			}
		}
		/*** add by zcw 三期性能优化 end **/
		logger.info("insertByBatch批量处理结束：" + DateUtil.getCurrentDateTime());
	}
	
	/**
	 * delCheckByBatch
	 *分批执行
	 * @param deleteList 数据
	 * @see
	 * @since 1.0
	 */
	// TODO check表数据量一般不多暂不优化后续check表数据量超过百万可考虑优化
	private void delCheckByBatch(List<EquityInfo001Dto> key) {
		if (StringUtil.isEmptyList(key)) {
			return;
		}
		
		int maxDataCountOneTime = reportSelectOrDeleteCount;
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
		if (isDB2DataBase) {
			maxDataCountOneTime = 1;
		}
		int dataCount = key.size();
		if (dataCount <= maxDataCountOneTime) {
			logger.info("delCheckDataBatch单次处理开始：" + DateUtil.getCurrentDateTime());
			chbEquHoldCheckDtoMapper.delCheckDataBatch(key);
			logger.info("delCheckDataBatch单次处理结束：" + DateUtil.getCurrentDateTime());

			return;
		}
		List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
		newDataList.addAll(key);

		// 分批处理
		logger.info("delCheckDataBatch批量处理开始：" + DateUtil.getCurrentDateTime());
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {			
			chbEquHoldCheckDtoMapper.delCheckDataBatch(newDataList.subList(0, maxDataCountOneTime));			
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbEquHoldCheckDtoMapper.delCheckDataBatch(newDataList);			
		}
		logger.info("delCheckDataBatch批量处理结束：" + DateUtil.getCurrentDateTime());
	}
	
	/**
	 * updateMergerIndentifyMarkByBatch
	 *分批执行
	 * @param deleteList 数据
	 * @see
	 * @since 1.0
	 */
	// TODO check表数据量一般不多暂不优化后续check表数据量超过百万可考虑优化
	private void updateMergerIndentifyMarkByBatch(List<ChbEquHoldCheckDto> key) {
		if (StringUtil.isEmptyList(key)) {
			return;
		}
		int maxDataCountOneTime = parsToInt(reportUpdateCountForShenFen,"1");
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
		if (isDB2DataBase) {
			maxDataCountOneTime = 1;
		}
		int dataCount = key.size();
		if (dataCount <= maxDataCountOneTime) {
			logger.info("updateMergerIndentifyMarkByBatch单次处理开始：" + DateUtil.getCurrentDateTime());
			chbEquHoldCheckDtoMapper.updateMergerIndentifyMarkByBatch(key);
			logger.info("updateMergerIndentifyMarkByBatch单次处理结束：" + DateUtil.getCurrentDateTime());
			return;
		}
		List<ChbEquHoldCheckDto> newDataList = new ArrayList<ChbEquHoldCheckDto>();
		newDataList.addAll(key);

		// 分批处理
		logger.info("updateMergerIndentifyMarkByBatch批量处理开始：" + DateUtil.getCurrentDateTime());
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			chbEquHoldCheckDtoMapper.updateMergerIndentifyMarkByBatch(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			logger.info("最后一次批量处理开始：" + DateUtil.getCurrentDateTime());
			chbEquHoldCheckDtoMapper.updateMergerIndentifyMarkByBatch(newDataList);
			logger.info("最后一次批量处理结束：" + DateUtil.getCurrentDateTime());
			
		}
		logger.info("updateMergerIndentifyMarkByBatch批量处理结束：" + DateUtil.getCurrentDateTime());
	}
	
	/**
	 * 
	 *分批执行查询 放置IN 超长
	 * @param selectChekForRepairByList 数据
	 * @see   
	 * @since 1.0
	 */
	private List<ChbEquHoldCheckDto> selectChekForRepairByList(List<ChbEquTmp> list) {
//		List<ChbEquHoldCheckDto> returnList = new ArrayList<ChbEquHoldCheckDto>();
		
		if (StringUtil.isEmptyList(list)) {
			return null;
		}
		// add by zcw 三期性能优化 start
		if(list!= null && list.size()>=1000){
			return chbEquHoldCheckDtoMapper.selectChekForRepairMax();

		}else{
			return chbEquHoldCheckDtoMapper.selectChekForRepair(list);
		}
		/*** add by zcw 三期性能优化 end **/

//
//		int maxDataCountOneTime = 1000;
//
//		int dataCount = key.size();
//		if (dataCount < maxDataCountOneTime) {
//			return chbEquHoldCheckDtoMapper.selectChekForRepair(key);
//		}
//		List<String> newDataList = new ArrayList<String>();
//		newDataList.addAll(key);
//
//		// 分批处理
//		int part = dataCount / maxDataCountOneTime; // 分批数
//		for (int i = 0; i < part; i++ ) {
//			List<ChbEquHoldCheckDto> hdlList = new ArrayList<ChbEquHoldCheckDto>();
//			hdlList = chbEquHoldCheckDtoMapper.selectChekForRepair(newDataList.subList(0, maxDataCountOneTime));
//			if(CollectionUtils.isNotEmpty(hdlList)) {
//				returnList.addAll(hdlList);
//			}
//			newDataList.subList(0, maxDataCountOneTime).clear();
//		}
//		// 最后剩下的数据
//		if (CollectionUtils.isNotEmpty(newDataList)) {
//			List<ChbEquHoldCheckDto> hdlList1 = new ArrayList<ChbEquHoldCheckDto>();
//			hdlList1 = chbEquHoldCheckDtoMapper.selectChekForRepair(newDataList);
//			if(CollectionUtils.isNotEmpty(hdlList1)) {
//				returnList.addAll(hdlList1);
//			}
//		}
//		return returnList;
	}

	public List<ChbEquTmp> keysToList(List<String> keys) {
		List<ChbEquTmp> list = new ArrayList<ChbEquTmp>();
		keys.stream().forEach(item->{
			String[] strs = item.split(GlobalConstant.UNDER_LINE, -1);
			ChbEquTmp dto = new ChbEquTmp();
			dto.setRegistBankCode(strs[0]);
			dto.setIdentifyNumber(strs[1]);
			dto.setHolderName(strs[2]);
			dto.setPersonalIdentifyType(strs[3]);
			dto.setInstitutionIdentifyType(strs[4]);
			dto.setOtherIdentifyName(strs[5]);
			list.add(dto);
		});
		return list;
	}

	/**
	 * 保存key到临时表
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	public void saveKeys(List<String> keys) {
		List<ChbEquTmp> list = keysToList(keys);
		ImpTable table = new ImpTable("CHB_EQU_TMP");
		table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
		// 临时文件存放目录
		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		batchInsertService.exeBatchldr(tmpPath, list, table);
		list.clear();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void deleteBymodelAfterDealNew() {
		chbInvestholdInfoCheckDtoMapper.deleteBymodelAfterDealNew();
	}
}
