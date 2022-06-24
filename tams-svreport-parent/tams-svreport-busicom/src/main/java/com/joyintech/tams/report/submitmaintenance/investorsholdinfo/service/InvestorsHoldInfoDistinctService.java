package com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import com.joyintech.tams.common.base.entity.ChbEquTmp;
import com.joyintech.tams.common.base.entity.ChbInvestholdInfoSub;
import com.joyintech.tams.common.base.entity.ChbSubCheckBasicRule;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpColumn;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.report.submitmaintenance.equityinfo.service.EquityInfoDistinctService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.investormerger.dao.ChbEquHoldCheckDtoMapper;
import com.joyintech.tams.report.investormerger.dao.ChbInvestholdInfoCheckDtoMapper;
import com.joyintech.tams.report.investormerger.dto.ChbEquHoldCheckDto;
import com.joyintech.tams.report.investormerger.dto.ChbInvestholdInfoCheckDto;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dao.InvestorsHoldInfo001Mapper;
//import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dao.InvestorsHoldInfo001Mapper;
//import com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001FileReadParaDto;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dao.InvestorsHoldInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.investorsholdinforegist.dto.InvestorsHoldInfoRegist001Dto;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
@Service
public class InvestorsHoldInfoDistinctService {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(InvestorsHoldInfoDistinctService.class);

//	@Autowired
//	private InvestorsHoldInfo001Mapper investorsHoldInfo001Mapper;
	@Autowired
	private ChbEquHoldCheckDtoMapper chbEquHoldCheckDtoMapper;
	@Autowired
	private ChbInvestholdInfoCheckDtoMapper chbInvestholdInfoCheckDtoMapper;
	@Autowired
    private CheckReport001Mapper chbReportCheckMapper;
	@Autowired
    private InvestorsHoldInfoRegist001Mapper investorsHoldInfoRegist001Mapper;
//	@Autowired
//	private EquityInfoDistinctMapper equityInfoDistinctMapper;
	@Autowired
	private InvestorsHoldInfo001Mapper investorsHoldInfo001Mapper;
	@Autowired
	private EquityInfoDistinctService equityInfoDistinctService;
	@Autowired
	private BatchInsertService batchInsertService;
    /**
     * sql最大一次最大删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public  String reportSelectOrDeleteCount;
	/**
	 * 长sql最大一次插入或者更新条数
	 */
	@Value("${application.reportInsertLongCount}")
	public  String reportInsertLongCount;
	/**
	 * 短sql最大一次最大插入条数
	 */
	@Value("${application.reportInsertCount}")
	public  int reportInsertCount;
    
	boolean doCombineIdentifying(InvestorsHoldInfo001Dto action,List<InvestorsHoldInfo001Dto> listE,Map<String,List<ChbEquHoldCheckDto>> holdTmpMap){
//		String autoFlag=SvrDataUtil.getSysParam("SVR_EQU_HOLD_MERGER");
		String key=action.getIndentifyMark()+ GlobalConstant.UNDER_LINE +action.getRegistBankCode();
		//手动
//		if(autoFlag.equals("0")){
			//查询身份信息临时表
//			ChbEquHoldTmpDto qmodel=new ChbEquHoldTmpDto();
//			qmodel.setIndentifyMark(action.getIndentifyMark());
//			qmodel.setRegistBankCode(action.getRegistBankCode());
//			List<ChbEquHoldTmpDto> result=chbEquHoldTmpDtoMapper.selectBymodel(qmodel);
			//如果临时表有值为待处理 该数据插入临时表
//			if(holdTmpMap.containsKey(key)){
//				ChbInvestholdInfoTmpDto temp=transformModelToTemp(action);
//				chbInvestholdInfoTmpDtoMapper.insert(temp);
//				return false;
//			}
//		}else{
//			//查询身份信息正式表 
//			EquityInfoRegist001Dto qmodel=new EquityInfoRegist001Dto();
//			qmodel.setBankCode(action.getRegistBankCode());
//			qmodel.setIndentifyMark(action.getIndentifyMark());
//			EquityInfoRegist001Dto resultModel=equityInfoDistinctMapper.queryEquityInfoByIndentifymarkAndBankCode(qmodel);
//			//如果查不到
//			if(resultModel==null){
//				ChbInvestholdInfoTmpDto temp=transformModelToTemp(action);
//				chbInvestholdInfoTmpDtoMapper.insert(temp);
				//查询身份信息临时表 查出合并后的识别标识
//				ChbEquHoldTmpDto qtmodel=new ChbEquHoldTmpDto();
//				qtmodel.setIndentifyMark(action.getIndentifyMark());
//				qtmodel.setRegistBankCode(action.getRegistBankCode());
//				List<ChbEquHoldTmpDto> result=chbEquHoldTmpDtoMapper.selectBymodel(qtmodel);
				if(holdTmpMap.containsKey(key)){
					ChbEquHoldCheckDto value=holdTmpMap.get(key).get(0);
					if(DictCodeListExt2.处理状态_中债_未合并.getValue().equals(value.getOperatestatus())){
						ChbInvestholdInfoCheckDto temp=transformModelToTemp(action);
						chbInvestholdInfoCheckDtoMapper.insert(temp);
						return false;
					}else{
						ChbInvestholdInfoCheckDto temp=transformModelToTemp(action);
						chbInvestholdInfoCheckDtoMapper.insert(temp);
						String mergerindentifyMark=holdTmpMap.get(key).get(0).getMergerindentifymark();
						if(!mergerindentifyMark.equals(action.getIndentifyMark())){
							return false;
						}
						//查询listE中是否存在 合并后识别标识数据 进行合并
						for(InvestorsHoldInfo001Dto single:listE){
							if(single.getIndentifyMark().equals(action.getIndentifyMark())&&
									single.getCcy().equals(action.getCcy())&&single.getHoldDate().equals(action.getHoldDate())
									&&single.getProdid().equals(action.getProdid())&&single.getStatus().equals(action.getStatus())){
								continue;
							}
							if(!single.getIndentifyMark().equals(mergerindentifyMark)&&
									single.getCcy().equals(action.getCcy())&&single.getHoldDate().equals(action.getHoldDate())
									&&single.getProdid().equals(action.getProdid())&&single.getStatus().equals(action.getStatus())){
								action.setAmount(single.getAmount().add(action.getAmount()));
								action.setQty(single.getQty().add(action.getQty()));
								action.setCnyAmt(single.getCnyAmt().add(action.getCnyAmt()));
							}
						}
						return true;
					}
				}
//			}
//		}
		return false;
	}
	ChbInvestholdInfoCheckDto transformModelToTemp(InvestorsHoldInfo001Dto investorsHoldInfo001Dto){
		ChbInvestholdInfoCheckDto temp=new ChbInvestholdInfoCheckDto();
		BeanUtils.copyProperties(investorsHoldInfo001Dto,temp);
		return temp;
		
	}
	/**
	 * 
	 * @param list
	 * @param type 检查类型 01报送前检查 02普通入库检查
	 */
	@SuppressWarnings("unchecked")
	public void checkInvestorsHoldInfolist(List list,String type){
		//查询到持有信息检查表数据 并以身份标识和登记银行代码分组
		 List<ChbEquHoldCheckDto> allHoldTmpList = chbEquHoldCheckDtoMapper.selectAllData(null);
		 
		 Map<String, List<ChbEquHoldCheckDto>> holdTmpMap=allHoldTmpList.stream().collect(
				 JoyinCollectors.groupingBy(
               x -> x.getRegistBankCode()+GlobalConstant.UNDER_LINE+x.getIndentifyMark()));
		//以合并后身份标识和登记银行代码分组
		 Map<String, List<ChbEquHoldCheckDto>> holdHbTmpMap=allHoldTmpList.stream().collect(
				 JoyinCollectors.groupingBy(
               x -> x.getRegistBankCode()+GlobalConstant.UNDER_LINE+x.getMergerindentifymark()));
		 List<ChbInvestholdInfoCheckDto> insertBatch=new ArrayList<ChbInvestholdInfoCheckDto>();
		 //循环本次入库数据
		 Object single=null;
		 for(Iterator<T> iterator = list.iterator();iterator.hasNext();){
			  single=iterator.next();
			  String key="";
			  if(single instanceof Map){
				   key=((Map<String,String>)single).get("REGIST_BANK_CODE")+
						   GlobalConstant.UNDER_LINE +((Map<String,String>) single).get("INDENTIFY_MARK");
			  }else if(single instanceof InvestorsHoldInfo001Dto){
				  key=((InvestorsHoldInfo001Dto)single).getRegistBankCode()+
						   GlobalConstant.UNDER_LINE +((InvestorsHoldInfo001Dto)single).getIndentifyMark();
			  }
			 //身份检查表中包含本次数据
			 if(holdTmpMap.containsKey(key)){
				 InvestorsHoldInfo001Dto qmodel=null;
				 if(single instanceof Map){
				  qmodel=mapToDto((Map<String,String>)single);
				 }else if(single instanceof InvestorsHoldInfo001Dto){
					 qmodel= (InvestorsHoldInfo001Dto)single;
				 }
				 //已报送剔除
				 if(chbInvestholdInfoCheckDtoMapper.checkInvestholdInfoSub(qmodel) > 0){
					 iterator.remove();
					 continue;
				 }
				 ChbEquHoldCheckDto infoTmp=holdTmpMap.get(key).get(0);
				 ChbInvestholdInfoCheckDto temp=transformModelToTemp(qmodel);
				 temp.setWorkflowId(infoTmp==null?null:infoTmp.getOperatestatus());
				 temp.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
				 insertBatch.add(temp);
				 if(type.equals("01")){
					 continue;
				 }
//				 chbInvestholdInfoTmpDtoMapper.insert(temp);
				 List<ChbEquHoldCheckDto> infoTmpHbList=null;
				 if(infoTmp!=null)
				 {
					 infoTmpHbList=holdHbTmpMap.get(infoTmp.getRegistBankCode()+ GlobalConstant.UNDER_LINE +infoTmp.getMergerindentifymark());
				 }
				 //循环合并后标识一致的 所有数据
				 if(infoTmpHbList!=null)
				 for(ChbEquHoldCheckDto info:infoTmpHbList){
					 //排除本身
//					 if(single instanceof Map){
//						  if(info.getIndentifyMark().equals(((Map<String,String>)single).get("INDENTIFY_MARK"))){
//								 continue;
//							 }
//						 }else if(single instanceof InvestorsHoldInfo001Dto){
//							 qmodel= (InvestorsHoldInfo001Dto)single;
//							 if(info.getIndentifyMark().equals(((InvestorsHoldInfo001Dto)single).getIndentifyMark())){
//								 continue;
//							 }
//						 }
					
					 //根据识别标识查询持有检查表是否有数据
					 ChbInvestholdInfoCheckDto record=new ChbInvestholdInfoCheckDto();
					 record.setRegistBankCode(info.getRegistBankCode());
					 record.setIndentifyMark(info.getIndentifyMark());
					 if (qmodel != null){
						 record.setHoldDate(qmodel.getHoldDate());
					 }
					 List<ChbInvestholdInfoCheckDto> temList=chbInvestholdInfoCheckDtoMapper.selectBymodel(record);
					 if(temList==null||temList.isEmpty()){//持有信息中没有数据 再去维护表找是否有数据 (排除已报送)
						List<ChbInvestholdInfoCheckDto> infoList= chbInvestholdInfoCheckDtoMapper.selectBymodelFromFormer(record);
						if(infoList!=null&&!infoList.isEmpty()) {//维护表中有数据(排除已报送) 将维护表中数据 插入到检查表
							for(ChbInvestholdInfoCheckDto infoIntep:infoList){
								infoIntep.setMergerindentifymark(infoIntep.getIndentifyMark());
								ChbInvestholdInfoCheckDto dmodel=new ChbInvestholdInfoCheckDto();
								 dmodel.setRegistBankCode(infoIntep.getRegistBankCode());
								 dmodel.setIndentifyMark(infoIntep.getIndentifyMark());
								 dmodel.setHoldDate(infoIntep.getHoldDate());
								 dmodel.setCcy(infoIntep.getCcy());
//								 dmodel.setPrdRegistCode(infoIntep.getPrdRegistCode());
								 dmodel.setProdid(infoIntep.getProdid());
								 chbInvestholdInfoCheckDtoMapper.deleteBymodel(dmodel);
								 if(infoIntep.getStatus().equals("N")){
									 infoIntep.setStatus("E");
								 }
								chbInvestholdInfoCheckDtoMapper.insert(infoIntep);
							}
						}
					 }else{
						 for(ChbInvestholdInfoCheckDto tep:temList){
							 //二次合并将原检查表 已处理状态数据变更为未处理
								ChbInvestholdInfoCheckDto dmodel=new ChbInvestholdInfoCheckDto();
								 dmodel.setRegistBankCode(tep.getRegistBankCode());
								 dmodel.setIndentifyMark(tep.getIndentifyMark());
								 dmodel.setHoldDate(tep.getHoldDate());
								 dmodel.setCcy(tep.getCcy());
//								 dmodel.setPrdRegistCode(tep.getPrdRegistCode());
								 dmodel.setProdid(tep.getProdid());
							     chbInvestholdInfoCheckDtoMapper.updateOperatestatus(tep);
						 }
					 }
				 }
				 iterator.remove();
			 }
		 }
		 
		 for(ChbInvestholdInfoCheckDto temp:insertBatch){
			 if(type.equals("01")){
				 ChbInvestholdInfoCheckDto qmodel=new ChbInvestholdInfoCheckDto();
				 qmodel.setRegistBankCode(temp.getRegistBankCode());
				 qmodel.setIndentifyMark(temp.getIndentifyMark());
				 qmodel.setHoldDate(temp.getHoldDate());
				 qmodel.setCcy(temp.getCcy());
				 qmodel.setPrdRegistCode(temp.getPrdRegistCode());
				 qmodel.setProdid(temp.getProdid());
				 chbInvestholdInfoCheckDtoMapper.updateMergerIdAndOperatestatus(qmodel);
			 }else{
				 //考虑重复导入 重复了 先删后插
				 ChbInvestholdInfoCheckDto qmodel=new ChbInvestholdInfoCheckDto();
				 qmodel.setRegistBankCode(temp.getRegistBankCode());
				 qmodel.setIndentifyMark(temp.getIndentifyMark());
				 qmodel.setHoldDate(temp.getHoldDate());
				 qmodel.setCcy(temp.getCcy());
				 qmodel.setPrdRegistCode(temp.getPrdRegistCode());
				 qmodel.setProdid(temp.getProdid());
				 chbInvestholdInfoCheckDtoMapper.deleteBymodel(qmodel);
				 if(temp.getStatus().equals("N")){
					 temp.setStatus("E");
				 }
				 if("01".equals(temp.getWorkflowId())){
					 temp.setWorkflowId(null);
					 temp.setStatus("A");
					 chbInvestholdInfoCheckDtoMapper.insertToChbInvestholdInfo(temp);
				 }
				 temp.setStatus("E");
				 chbInvestholdInfoCheckDtoMapper.insert(temp);
			 }
		 }
		
	}
	 
	/**
	 * 
	 * @param type 只处理普通入库逻辑
	 */
	
	 @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public List<ChbInvestholdInfoCheckDto> checkInvestorsHoldInfolistBack( List<Map<String, String>> list,InvestorsHoldInfo001FileReadParaDto paraDto){
		
		//查询出需要合并的数据(只文件）
		 // 用文件数据关联信息check表，找到文件中需要合并的数据
		 List<ChbInvestholdInfoCheckDto> checkDtoList= this.selectAllNeedCheckDtoList(list);
		 
		//list 先入库   REGIST_BANK_CODE  INDENTIFY_MARK CCY HOLD_DATE PRODID 到临时表
		 //this.insertListTmp(list);
//		 List<ChbInvestholdInfoCheckDto> checkDtoList= chbInvestholdInfoCheckDtoMapper.selectNeedCheckList();
		 
 		 List<ChbInvestholdInfoCheckDto> insertBatch=new ArrayList<ChbInvestholdInfoCheckDto>();
 		 
 		 if(checkDtoList!=null && !checkDtoList.isEmpty()){
 		     
 		    Map<String, String> prdRegistMap = paraDto.getPrdRegistMap();
 		    
 		   
	    for (ChbInvestholdInfoCheckDto temp : checkDtoList) {

			// 根据身份的识别标识合并状态查询设置持有的状态，如果是01的身份，则持有原封不动的插入到info表不做处理。
			temp.setWorkflowId(temp.getOperatestatus());
			// 默认设置成 需要合并
			temp.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
			temp.setPrdRegistCode(prdRegistMap.get(temp.getProdid()) == null ? null : prdRegistMap.get(temp.getProdid()));
			// 文件中需要合并的数据入check表
			insertBatch.add(temp);

			// 是否排除自己待定
			// 查询文件中需要check数据的所有的原身份check的数据
			List<ChbEquHoldCheckDto> mergeList = chbInvestholdInfoCheckDtoMapper.getMergerindentifymark(temp);

			for (ChbEquHoldCheckDto equInfo : mergeList) {

				ChbInvestholdInfoCheckDto queryModel = new ChbInvestholdInfoCheckDto();
				queryModel.setRegistBankCode(equInfo.getRegistBankCode());
				queryModel.setIndentifyMark(equInfo.getIndentifyMark());
				queryModel.setHoldDate(temp.getHoldDate());
				queryModel.setProdid(temp.getProdid());
				// 查询当日该身份信息识别标识是否已经入check表了
				List<ChbInvestholdInfoCheckDto> temList = chbInvestholdInfoCheckDtoMapper.selectBymodel(queryModel);
				// 该身份已经入check表代表已经接入过持有合并，将合并状态置为未合并
				if (temList == null || temList.isEmpty()) {// CHB_INVESTHOLD_INFO_CHECK 查询没有记录
					// CHB_INVESTHOLD_INFO 去查询未报送记录 SEND_STATUS='01
					queryModel.setMergerindentifymark(temp.getMergerindentifymark());
					// 查询该身份是否有已入check表的合并数据
					int count =chbInvestholdInfoCheckDtoMapper.isExistsMergerindentifymark(queryModel);
					if(count==0){//Merge表有就不再进
						// 没有进入check表数据却已经进入信息表和登记表数据
						List<ChbInvestholdInfoCheckDto> infoList = chbInvestholdInfoCheckDtoMapper.selectBymodelFromFormer(queryModel);
						// 此种数据为开始非合并身份数据，后续变为合并身份数据的处理逻辑
						// 需要将此种数据也置为check表中
						if (infoList != null && !infoList.isEmpty()) {// 有数据，且合并后的不再check有记录的情况下，才会进check

							for (ChbInvestholdInfoCheckDto dto : infoList) {
							dto.setMergerindentifymark(dto.getIndentifyMark());
							dto.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
							// check表插入以此识别标识为合并后记录的数据。
							ChbInvestholdInfoCheckDto dmodel = new ChbInvestholdInfoCheckDto();
							dmodel.setRegistBankCode(dto.getRegistBankCode());
							dmodel.setIndentifyMark(dto.getIndentifyMark());
							dmodel.setHoldDate(dto.getHoldDate());
							dmodel.setCcy(dto.getCcy());
							dmodel.setProdid(dto.getProdid());
							dto.setCreateTime(new Date());
							chbInvestholdInfoCheckDtoMapper.deleteBymodel(dmodel);
							// 入check表
							chbInvestholdInfoCheckDtoMapper.insert(dto);
							}
						}
					}
				} else {// 如果有当日已入库的数据，则修改这些状态为待合并。
					chbInvestholdInfoCheckDtoMapper.updateOperatestatusW(temList);
				}
			}
	    }
	}
 		 
 		 //将文件里的数据插入check，如果有待处理的身份合并，则插入为A状态数据到Info里。
		 for(ChbInvestholdInfoCheckDto temp:insertBatch){
				 //考虑重复导入 重复了 先删后插
				 ChbInvestholdInfoCheckDto qmodel=new ChbInvestholdInfoCheckDto();
				 qmodel.setRegistBankCode(temp.getRegistBankCode());
				 qmodel.setIndentifyMark(temp.getIndentifyMark());
				 qmodel.setHoldDate(temp.getHoldDate());
				 qmodel.setCcy(temp.getCcy());
				 qmodel.setPrdRegistCode(temp.getPrdRegistCode());
				 qmodel.setProdid(temp.getProdid());
				 chbInvestholdInfoCheckDtoMapper.deleteBymodel(qmodel);
//				 if(temp.getStatus().equals("N")){
//					 temp.setStatus("E");
//				 }
				 if("01".equals(temp.getWorkflowId())){//如果身份是待处理，则直接插入temp表
					 temp.setWorkflowId(null);
					 temp.setStatus("A");
					 chbInvestholdInfoCheckDtoMapper.insertToChbInvestholdInfo(temp);
				 }
				 temp.setStatus("E");
				 
				 chbInvestholdInfoCheckDtoMapper.insert(temp);
		 }
		 
		 return checkDtoList;
		
	}
	
	
	/**
	 * 对三合一的持有信息进行处理
	 * 20220218处理性能问题 对代码逻辑进行重构
	 */
	
	 @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public List<ChbInvestholdInfoCheckDto> checkInvestorsHoldInfolistBackNew( List<Map<String, String>> list,InvestorsHoldInfo001FileReadParaDto paraDto){
		//取得参数中传入的，产品代码 -- 产品登记编码的对应关系表
		 Map<String, String> prdRegistMap = paraDto.getPrdRegistMap();
		 //查询出 导入记录中属于三合一数据 && 并且中债反馈状态 ！= 03：中债通过的数据
		 List<ChbInvestholdInfoCheckDto> checkDtoList = new ArrayList<ChbInvestholdInfoCheckDto>();
		 logger.info("查询需要入check表数据-start");
		 if(list!=null && list.size()>1000){
			 List<ChbInvestholdInfoCheckDto> tmpList = new ArrayList<ChbInvestholdInfoCheckDto>();
			 for(Map<String, String> tmp : list){
				 tmpList.add(mapToDtoCheck(tmp));
			 }
			 chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();

			 this.saveChbInvestholdInfoCheckTmp(tmpList);

			 checkDtoList = chbInvestholdInfoCheckDtoMapper.selectNeedCheckDtoListNew();
			 if(!StringUtil.isEmptyList(checkDtoList)){
				 Map<String, ChbInvestholdInfoCheckDto> tmpMap= tmpList.stream().collect(JoyinCollectors.toMap(item->
						 item.getRegistBankCode()+"|"+item.getProdid()+"|"+item.getIndentifyMark()+"|"+item.getHoldDate()+"|"+item.getCcy(),item->item));

				 checkDtoList.stream().forEach(
						 item ->{
							 ChbInvestholdInfoCheckDto tmpDto = tmpMap.get(item.getRegistBankCode()+"|"+item.getProdid()+"|"+item.getIndentifyMark()+"|"+item.getHoldDate()+"|"+item.getCcy());
							 item.setAmount(tmpDto.getAmount());
							 item.setQty(tmpDto.getQty());
							 item.setCnyAmt(tmpDto.getCnyAmt());
							 item.setOrgCode(tmpDto.getOrgCode());
							 item.setDeptCode(tmpDto.getDeptCode());
							 item.setAgent(tmpDto.getAgent());
						 }
				 );
			 }
			 tmpList.clear();
		 }else{
			 checkDtoList = this.selectAllNeedCheckDtoList(list);
		 }
		 logger.info("查询需要入check表数据-end");
		//对取得的三合一数据列表进行处理，对相同三合一身份的持有信息，值保留一条数据		 		 
		 List<ChbInvestholdInfoCheckDto> checkDtoListFormegerId = checkDtoList.stream().collect(
				 Collectors.collectingAndThen(
						 Collectors.toCollection(() -> new TreeSet<>(
								 	Comparator.comparing(
								 			x -> x.getRegistBankCode() + x.getProdid() + x.getCcy() + x.getHoldDate() + x.getMergerindentifymark()						 
								 ))), ArrayList::new)
				 );

		 // 查询所有需合并数据合并前身份
		 List<ChbInvestholdInfoCheckDto> allChbEquHoldCheck = new ArrayList<ChbInvestholdInfoCheckDto>();;
		 logger.info("查询所有需合并数据合并前身份-start");

		 if(checkDtoListFormegerId!=null && checkDtoListFormegerId.size()>1000){
			 chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();

			 this.saveChbInvestholdInfoCheckTmp(checkDtoListFormegerId);
			 checkDtoListFormegerId.clear();
			 allChbEquHoldCheck = chbInvestholdInfoCheckDtoMapper.selectAllChbEquHoldCheckNew();

		 }else{
			 allChbEquHoldCheck = this.selectAllChbEquHoldCheck(checkDtoListFormegerId);
		 }

		 logger.info("查询所有需合并数据合并前身份-end");

			 //取得是三合一数据 但是在check表中不存在任何渠道持有信息  但是 在INFO SUB表中存在记录 && 报送状态 = 01：未报送 的数据
		 List<ChbInvestholdInfoCheckDto> dtoList = new ArrayList<ChbInvestholdInfoCheckDto>();
		 // 已进入sub表但是需要合并的数据为进入check表代表之前进sub表的时候无需合并后续该数据变为合并数据需要进入check表
		 logger.info("查询所有需要重新入check表数据-start");
		 if(allChbEquHoldCheck!=null && allChbEquHoldCheck.size()>1000){
			 chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();

			 this.saveChbInvestholdInfoCheckTmp(allChbEquHoldCheck);

			 dtoList = chbInvestholdInfoCheckDtoMapper.selectNeedDealDtoListNew();

		 }else{
			 dtoList= this.selectAllNeedDealDtoList(allChbEquHoldCheck);
		 }
		 logger.info("查询所有需要重新入check表数据-end");

		 //从SUB表取得的，需要进入持有check表的数据中，去除本次导入列表中存在的记录。获得需要进入持有check表的列表
		 List<ChbInvestholdInfoCheckDto> insertBatch = new ArrayList<ChbInvestholdInfoCheckDto>();
		 insertBatch.addAll(checkDtoList);
		 insertBatch.addAll(dtoList);
		 insertBatch = insertBatch.stream().collect(
				 Collectors.collectingAndThen(
						 Collectors.toCollection(() -> new TreeSet<>(
								 	Comparator.comparing(
								 			x -> x.getRegistBankCode() + x.getProdid() + x.getCcy() + x.getHoldDate() + x.getIndentifyMark()						 
								 ))), ArrayList::new)
				 );
 		 //将文件里的数据插入check，如果有待处理的身份合并，则插入为A状态数据到Info里。
		 List<ChbInvestholdInfoCheckDto> deleteForInvestHoldCheckTable = new ArrayList<ChbInvestholdInfoCheckDto>();
		 List<ChbInvestholdInfoCheckDto> insertForInvestholdInfoTable = new ArrayList<ChbInvestholdInfoCheckDto>();
		 List<ChbInvestholdInfoCheckDto> insertForInvestHoldCheckTable = new ArrayList<ChbInvestholdInfoCheckDto>();
		 if(!StringUtil.isEmptyList(insertBatch)){
			 for(ChbInvestholdInfoCheckDto temp:insertBatch){
				 // 根据身份的识别标识合并状态查询设置持有的状态，如果是01的身份，则持有原封不动的插入到info表不做处理。
				 temp.setWorkflowId(temp.getOperatestatus());
				 // 默认设置成 需要合并
				 temp.setOperatestatus(DictCodeListExt2.处理状态_中债_未合并.getValue());
				 temp.setPrdRegistCode(prdRegistMap.get(temp.getProdid()) == null ? null : prdRegistMap.get(temp.getProdid()));

				 //考虑重复导入 重复了 先删后插
				 ChbInvestholdInfoCheckDto qmodel=new ChbInvestholdInfoCheckDto();
				 qmodel.setRegistBankCode(temp.getRegistBankCode());
				 qmodel.setIndentifyMark(temp.getIndentifyMark());
				 qmodel.setHoldDate(temp.getHoldDate());
				 qmodel.setCcy(temp.getCcy());
				 qmodel.setPrdRegistCode(temp.getPrdRegistCode());
				 qmodel.setProdid(temp.getProdid());
				 deleteForInvestHoldCheckTable.add(qmodel);
				 if("01".equals(temp.getWorkflowId())){//如果身份是待处理，则直接插入temp表
					 temp.setWorkflowId(null);
					 temp.setStatus("A");
					 insertForInvestholdInfoTable.add(temp);
				 }
				 temp.setStatus("E");
				 insertForInvestHoldCheckTable.add(temp);
			 }
		 }
		 logger.info("insertToChbInvestholdInfoAfterDeal开始");
		 // 未合并身份进入待处理表
		 this.insertToChbInvestholdInfoAfterDeal(insertForInvestholdInfoTable);
		 logger.info("insertToChbInvestholdInfoAfterDeal结束");
		 logger.info("updataOperateStatus 开始");
		 if(allChbEquHoldCheck!=null && allChbEquHoldCheck.size()>1000){
			 chbInvestholdInfoCheckDtoMapper.updataOperateStatusNew();
		 }else{
			 this.updataOperateStatus(allChbEquHoldCheck);
		 }
		 logger.info("updataOperateStatus 结束");

		 logger.info("check数据处理 开始");
		 if(!StringUtil.isEmptyList(insertForInvestHoldCheckTable) && insertForInvestHoldCheckTable.size()>1000){
			 // 清空临时表数据
			 chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
			 this.saveChbInvestholdInfoCheckTmp(insertForInvestHoldCheckTable);
			 // 删除check表
			 equityInfoDistinctService.deleteBymodelAfterDealNew();
			 // 保存到check表
			 this.saveChbInvestholdInfoCheck(insertForInvestHoldCheckTable);
			 // 清空临时表数据
			 chbInvestholdInfoCheckDtoMapper.truncateInvestholdTmp();
		 }else{
		 	 // 删除check表
			 this.deleteAfterDeal(deleteForInvestHoldCheckTable);
			 // 保存到check表
			 this.insertAfterDeal(insertForInvestHoldCheckTable);
		 }
		 logger.info("check数据处理 结束");
		 return checkDtoList;
	}
	/*
	 * 开启线程池模式查询大数据量，每个线程查100
	 * */
    private List<ChbInvestholdInfoCheckDto> selectAllNeedCheckDtoList(List<Map<String, String>> list) {

	List<Callable<Boolean>> tasks = new ArrayList<>();

	List<ChbInvestholdInfoCheckDto> result = Collections.synchronizedList(new ArrayList<ChbInvestholdInfoCheckDto>());
	if (null != list) {
	    ExecutorService service = new JoyinThreadPoolExecutor(10);
	    int start = 0;
	    int end = 0;
	    int size = list.size();
	    int max = (int) Math.ceil((double) size / 100);
	    // 信号量
	    CountDownLatch countDown = new CountDownLatch(max);

	    for (int i = 0; i < max; i++) {
		start = end;
		end = Math.min(size, 100 * (i + 1));
		List<Map<String, String>> subList = list.subList(start, end);

		// 提交任务
		tasks.add(new Callable<Boolean>() {

		    @Override
		    public Boolean call() throws Exception {
			result.addAll(chbInvestholdInfoCheckDtoMapper.selectNeedCheckDtoList(subList));
			countDown.countDown();// 个数-1
			return true;
		    }
		});
	    }
	    // 添加线程等待转换线执行
	    try {
		service.invokeAll(tasks);
		countDown.await();
		service.shutdown();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	return result;
    }

	/*
	 * 开启线程池模式查询大数据量，每个线程查100
	 * */
	private List<ChbInvestholdInfoCheckDto> selectAllNeedDealDtoList(List<ChbInvestholdInfoCheckDto> list) {

		List<Callable<Boolean>> tasks = new ArrayList<>();

		List<ChbInvestholdInfoCheckDto> result = Collections.synchronizedList(new ArrayList<ChbInvestholdInfoCheckDto>());
		if (null != list) {
			ExecutorService service = new JoyinThreadPoolExecutor(10);
			int start = 0;
			int end = 0;
			int size = list.size();
			int max = (int) Math.ceil((double) size / 100);
			// 信号量
			CountDownLatch countDown = new CountDownLatch(max);

			for (int i = 0; i < max; i++) {
				start = end;
				end = Math.min(size, 100 * (i + 1));
				List<ChbInvestholdInfoCheckDto> subList = list.subList(start, end);

				// 提交任务
				tasks.add(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						result.addAll(chbInvestholdInfoCheckDtoMapper.selectNeedDealDtoList(subList));
						countDown.countDown();// 个数-1
						return true;
					}
				});
			}
			// 添加线程等待转换线执行
			try {
				service.invokeAll(tasks);
				countDown.await();
				service.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	public void insertListTmp(List<Map<String, String>> list) {

//		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		//参数调优
		int maxDataCountOneTime =100;
		
		 List<Callable<Boolean>> tasks = new ArrayList<>();
		 
	        if (null != list) {
	        	  ExecutorService service = new JoyinThreadPoolExecutor(10);
	              int start = 0;
	              int end = 0;
	              int size = list.size();
	              int max = (int)Math.ceil((double)size / maxDataCountOneTime);
	              //信号量
	              CountDownLatch countDown = new CountDownLatch(max);
	              
	              for (int i = 0; i < max; i++ ) {
	                  start = end;
	                  end = Math.min(size, maxDataCountOneTime * (i + 1));
	                  List<Map<String, String>> subList = list.subList(start, end);
	               
	               //提交任务
	               tasks.add(new Callable<Boolean>() {

	  				@Override
	  				public Boolean call() throws Exception {
	  					 chbInvestholdInfoCheckDtoMapper.insertListToTmp(subList);
	  					countDown.countDown();//个数-1
	  					return true;
	  				}
	                });
	              }
	            //添加线程等待转换线执行
	              try {
	        	  service.invokeAll(tasks);
	        	  countDown.await();
	        	  service.shutdown();
	              } catch (InterruptedException e) {
	        	  e.printStackTrace();
	              }
	          }
//		int maxDataCountOneTime = 500;
//		 
//		int dataCount = list.size();
//		if (dataCount <= maxDataCountOneTime) {
//			chbInvestholdInfoCheckDtoMapper.insertListToTmp(list);
//		} else {
//			List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
//			newList.addAll(list);
//
//			// 分批处理
//			int part = dataCount / maxDataCountOneTime; // 分批数
//			for (int i = 0; i < part; i++) {
//				chbInvestholdInfoCheckDtoMapper.insertListToTmp(newList.subList(0, maxDataCountOneTime));
//				newList.subList(0, maxDataCountOneTime).clear();
//			}
//			// 最后剩下的数据
//			if (CollectionUtils.isNotEmpty(newList)) {
//				chbInvestholdInfoCheckDtoMapper.insertListToTmp(newList);
//			}
//		}
	}
	
    /**
     * 合并
     * 
     * @param list
     * @param type
     *            01 map类型 02dto 类型
     */
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void mergeInvestorsHoldInfolist(String type, String interType) {
		//删除info表中存在check表MergeIndentyMark的当日数据
		logger.info("deleteByBankcodeAndIndentifyMarkWH 开始");
		chbInvestholdInfoCheckDtoMapper.deleteByBankcodeAndIndentifyMarkWH();
		logger.info("deleteByBankcodeAndIndentifyMarkWH 结束");
		//删除sub表中存在check表MergeIndentyMark的当日数据
		logger.info("deleteByBankcodeAndIndentifyMarkDJ 开始");
		chbInvestholdInfoCheckDtoMapper.deleteByBankcodeAndIndentifyMarkDJ();
		logger.info("deleteByBankcodeAndIndentifyMarkDJ 结束");

		// 查询需要合并的数据
		logger.info("selectHbhChbInvestholdInfoUnmerged 开始");
		List<InvestorsHoldInfo001Dto> resultList = chbInvestholdInfoCheckDtoMapper.selectHbhChbInvestholdInfoUnmerged();
		logger.info("selectHbhChbInvestholdInfoUnmerged 开始");

		/**** 数据处理,大批量删除导致速度降低,修改逻辑 **/
		if (!resultList.isEmpty()) {
			this.deleteInvestholdInfoSubBatchGroupDto(resultList);
			this.deleteInvestholdInfoBatchGroupDto(resultList);
		}
		// 更新持有检查表数据状态
		logger.info("updateChbInvestholdCheckAftermerged 开始");
		chbInvestholdInfoCheckDtoMapper.updateChbInvestholdCheckAftermerged();
		logger.info("updateChbInvestholdCheckAftermerged 开始");

		// 插入维护及登记表
		InvestorsHoldInfo001Service investorsHoldInfo001Service = SpringContextUtil.getBean(InvestorsHoldInfo001Service.class);
		// 获取所有的产品登记编码
		Map<String, String> prdRegistMap = new HashMap<String, String>();
		List<InvestorsHoldInfo001Dto> listA = new ArrayList<InvestorsHoldInfo001Dto>();
		List<InvestorsHoldInfo001Dto> listE = new ArrayList<InvestorsHoldInfo001Dto>();
		for (InvestorsHoldInfo001Dto single : resultList) {
			if (single.getPrdRegistCode() == null || "".equals(single.getPrdRegistCode())) {
			if (prdRegistMap.isEmpty()) {
				prdRegistMap = investorsHoldInfo001Mapper.getAllRegistProdCodeSet().stream().collect(JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("REG_CODE")));
			}
			if (single.getProdid() != null) {
				single.setPrdRegistCode(prdRegistMap.get(single.getProdid()));
			}
			if (single.getPrdRegistCode() == null || "".equals(single.getPrdRegistCode())) {
				if (interType.equals(DictCodeListExt2.数据来源_资管系统.getValue()))
				listA.add(single);
			} else {
				listE.add(single);
			}
			} else {
			listE.add(single);
			}
		}
		List<InvestorsHoldInfo001Dto> listIn = new ArrayList<>();
		List<ChbInvestholdInfoSub> subListInsert = new ArrayList<>();
		logger.info("---------------------------------afterCheckDeal--------------------开始");
		investorsHoldInfo001Service.afterCheckDeal(listE, listA, interType, listIn, subListInsert, null);

		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 批量插入
		if (listIn != null && listIn.size() > 0) {
			if(listIn.size()<1000){
				insertChbHoldInfoDataByPart(listIn);
			}else{
				// 创建表
				ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO");
				table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));

				batchInsertService.exeBatchldr(tmpPath, listIn, table);
			}
		}

		if (subListInsert != null && subListInsert.size() > 0) {
			if(listIn.size()<1000) {
				investorsHoldInfo001Service.insertChbSubHoldInfoDataByPart(subListInsert);
			}else{
				// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
				ImpTable subTable = new ImpTable("CHB_INVESTHOLD_INFO_SUB");
				subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
				subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
				// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
				for (ImpColumn column : subTable.getColumns()) {
					if (StringUtil.equals(column.getName(), "STATUS")) {
						column.setConstant(DictCodeListExt2.报送数据状态_新建.getValue());
					}
				}
				batchInsertService.exeBatchldr(tmpPath, subListInsert, subTable);
			}
		}
		logger.info("---------------------------------afterCheckDeal--------------------结束");
	}

	/**
     * 合并-前台导入用
     * 
     * @param list
     * @param type
     *            01 map类型 02dto 类型
     */	
    public void mergeInvestorsHoldInfolistNew(String type, String interType) {
		//删除info表中存在check表MergeIndentyMark的当日数据
		chbInvestholdInfoCheckDtoMapper.deleteByBankcodeAndIndentifyMarkWH();
		//删除sub表中存在check表MergeIndentyMark的当日数据
		chbInvestholdInfoCheckDtoMapper.deleteByBankcodeAndIndentifyMarkDJ();

		// 查询需要合并的数据
		List<InvestorsHoldInfo001Dto> resultList = chbInvestholdInfoCheckDtoMapper.selectHbhChbInvestholdInfoUnmerged();

		/**** 数据处理,大批量删除导致速度降低,修改逻辑 **/
		if (!resultList.isEmpty()) {
			this.deleteInvestholdInfoSubBatchGroupDto(resultList);
			this.deleteInvestholdInfoBatchGroupDto(resultList);
		}
		// 更新持有检查表数据状态
		chbInvestholdInfoCheckDtoMapper.updateChbInvestholdCheckAftermerged();
		// 插入维护及登记表
		InvestorsHoldInfo001Service investorsHoldInfo001Service = SpringContextUtil.getBean(InvestorsHoldInfo001Service.class);
		// 获取所有的产品登记编码
		Map<String, String> prdRegistMap = new HashMap<String, String>();
		List<InvestorsHoldInfo001Dto> listA = new ArrayList<InvestorsHoldInfo001Dto>();
		List<InvestorsHoldInfo001Dto> listE = new ArrayList<InvestorsHoldInfo001Dto>();
		for (InvestorsHoldInfo001Dto single : resultList) {
			if (single.getPrdRegistCode() == null || "".equals(single.getPrdRegistCode())) {
			if (prdRegistMap.isEmpty()) {
				prdRegistMap = investorsHoldInfo001Mapper.getAllRegistProdCodeSet().stream().collect(JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("REG_CODE")));
			}
			if (single.getProdid() != null) {
				single.setPrdRegistCode(prdRegistMap.get(single.getProdid()));
			}
			if (single.getPrdRegistCode() == null || "".equals(single.getPrdRegistCode())) {
				if (interType.equals(DictCodeListExt2.数据来源_资管系统.getValue()))
				listA.add(single);
			} else {
				listE.add(single);
			}
			} else {
			listE.add(single);
			}
		}
		List<InvestorsHoldInfo001Dto> listIn = new ArrayList<>();
		List<ChbInvestholdInfoSub> subListInsert = new ArrayList<>();
		investorsHoldInfo001Service.afterCheckDeal(listE, listA, interType, listIn, subListInsert, null);

		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 批量插入
		if (listIn != null && listIn.size() > 0) {
			if(listIn.size()<1000){
				insertChbHoldInfoDataByPart(listIn);
			}else{
				// 创建表
				ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO");
				table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));

				batchInsertService.exeBatchldr(tmpPath, listIn, table);
			}
		}

		if (subListInsert != null && subListInsert.size() > 0) {
			if(listIn.size()<1000) {
				investorsHoldInfo001Service.insertChbSubHoldInfoDataByPart(subListInsert);
			}else{
				// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
				ImpTable subTable = new ImpTable("CHB_INVESTHOLD_INFO_SUB");
				subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
				subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
				// 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
				for (ImpColumn column : subTable.getColumns()) {
					if (StringUtil.equals(column.getName(), "STATUS")) {
						column.setConstant(DictCodeListExt2.报送数据状态_新建.getValue());
					}
				}
				batchInsertService.exeBatchldr(tmpPath, subListInsert, subTable);
			}
		}
    }
	
	/**
	 * @param type "01" 数据检查  02自动报送的检查
	 * @param registList
	 * @returnqueryRepeatEquityInfoHoldUnSubmit
	 */
	public void checkBeforeSubmit(){
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
	 List<InvestorsHoldInfo001Dto> listR=chbInvestholdInfoCheckDtoMapper.queryNotInHoldButIncheckInfoHold(null);
		
		if(listR!=null&&!listR.isEmpty()){
			String mergerFlag = SvrDataUtil.getSysParam("SVR_EQU_HOLD_MERGER");
 
			if(!"0".equals(mergerFlag)){
			 
				List<String> keysList = new ArrayList<>();
				
	        	for(InvestorsHoldInfo001Dto single:listR){
					String key=single.getRegistBankCode()+GlobalConstant.UNDER_LINE +single.getIndentifyMark()
					+GlobalConstant.UNDER_LINE +single.getCcy()+GlobalConstant.UNDER_LINE +sdf.format(single.getHoldDate())
					+GlobalConstant.UNDER_LINE +single.getProdid();
					keysList.add(key);
			   }
	        	
				//查询出对应的未报送登记信息
//	        	List<InvestorsHoldInfoRegist001Dto> list=chbInvestholdInfoCheckDtoMapper.queryInvestorsHoldInfoRegistByPkUnsubmit(keysList);
				List<InvestorsHoldInfoRegist001Dto> list = new ArrayList<InvestorsHoldInfoRegist001Dto>();
				int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
				int dataCount = listR.size();
				if (dataCount <= maxDataCountOneTime) {
					list.addAll(chbInvestholdInfoCheckDtoMapper.queryInvestorsHoldInfoRegistByPkUnsubmit(listR));
					//更新正确的识别标识
					//更新info表
					chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfo(listR);
					//更新sub表
					chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfoSub(listR);
					//更新check表
					chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfoCheck(listR);
				} else {
					List<InvestorsHoldInfo001Dto> newKeyList = new ArrayList<InvestorsHoldInfo001Dto>();
					newKeyList.addAll(listR);

					// 分批处理
					int part = dataCount / maxDataCountOneTime; // 分批数
					for (int i = 0; i < part; i++) {
						list.addAll(
								chbInvestholdInfoCheckDtoMapper.queryInvestorsHoldInfoRegistByPkUnsubmit(newKeyList.subList(0, maxDataCountOneTime)));
						//更新正确的识别标识
						//更新info表
						chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfo(newKeyList.subList(0, maxDataCountOneTime));
						//更新sub表
						chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfoSub(newKeyList.subList(0, maxDataCountOneTime));
						//更新check表
						chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfoCheck(newKeyList.subList(0, maxDataCountOneTime));
						newKeyList.subList(0, maxDataCountOneTime).clear();
					}
					// 最后剩下的数据
					if (CollectionUtils.isNotEmpty(newKeyList)) {
						list.addAll(chbInvestholdInfoCheckDtoMapper.queryInvestorsHoldInfoRegistByPkUnsubmit(newKeyList));
						//更新正确的识别标识
						//更新info表
						chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfo(newKeyList);
						//更新sub表
						chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfoSub(newKeyList);
						//更新check表
						chbInvestholdInfoCheckDtoMapper.updateUnMergeInvestorsHoldInfoCheck(newKeyList);
					}
				}
	        	//更新原检查表和登记表检查检查状态
				dochecksafe(list);
			}
		}
	}
	
	public void dochecksafe(List<InvestorsHoldInfoRegist001Dto> equityInfoRegistRstList){
		 List<InvestorsHoldInfoRegist001Dto> investRegistUpdateList = new ArrayList<>();
		  List<CheckReport001Dto> resultList = new ArrayList<>();
		  List<String> checkseqnos= new ArrayList<String>();
	        if(CollectionUtils.isNotEmpty(equityInfoRegistRstList)){
	            equityInfoRegistRstList.forEach(item->{
	            	if(item.getCheckSeqno()!=null&&!item.getCheckSeqno().equals("")&&!item.getCheckSeqno().equals("0")){
	            		checkseqnos.add(item.getCheckSeqno());
	            	}
	                //检查结果
//	                CheckReport001Dto chbReportCheck = new CheckReport001Dto();
//	                BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
//	                chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
//	                chbReportCheck.setRepCode(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());
//	                chbReportCheck.setRepName(DictCodeListExt2.报表名称_投资者持有信息登记.getCodeName());
//	                if(type.equals("01")){
//	                	chbReportCheck.setCheckResult("检查通过！");
//	                }else{
//	                	chbReportCheck.setCheckResult("检查不通过，该投资者存在多个识别标识！");
//	                }
//	                resultList.add(chbReportCheck);
//
//	                //更新检查序列号对应的数据
//	                InvestorsHoldInfoRegist001Dto dto = new InvestorsHoldInfoRegist001Dto();
//	                dto.setPrdRegistCode(item.getPrdRegistCode());
//	                dto.setIndentifyMark(item.getIndentifyMark());
//	                dto.setHoldDate(item.getHoldDate());
//	                dto.setCcy(item.getCcy());
//	                dto.setRegistBankCode(item.getRegistBankCode());
//	                dto.setCheckSeqno(chbReportCheck.getCheckSeqno().trim());
//	                investRegistUpdateList.add(dto);
	              CheckReport001Dto chbReportCheck = new CheckReport001Dto();
//	                BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
	              String validKey = "产品登记编码 "+item.getPrdRegistCode()+GlobalConstant.COMMA
	                      +"识别标识 "+item.getIndentifyMark()+GlobalConstant.COMMA
	                      +"持有日期 "+DateUtil.format(item.getHoldDate(),"yyyy-MM-dd")+GlobalConstant.COMMA
	                      +"币种  "+item.getCcy()+GlobalConstant.COMMA
	                      +"登记银行代码  "+item.getRegistBankCode();
	              	chbReportCheck.setValidKey(validKey);
	                chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
	                chbReportCheck.setRepCode(DictCodeListExt2.报表名称_投资者持有信息登记.getValue());
	                chbReportCheck.setRepName(DictCodeListExt2.报表名称_投资者持有信息登记.getCodeName());
//	                if(type.equals("01")){
//	                	chbReportCheck.setCheckResult("01");
//	                }else{
	                	chbReportCheck.setCheckResult("02");
	                	chbReportCheck.setErrorInfo("检查不通过，该投资者存在多个识别标识！");
//	                }
	                resultList.add(chbReportCheck);

	                //更新检查序列号对应的数据
	                InvestorsHoldInfoRegist001Dto dto = new InvestorsHoldInfoRegist001Dto();
	                dto.setPrdRegistCode(item.getPrdRegistCode());
	                dto.setIndentifyMark(item.getIndentifyMark());
	                dto.setHoldDate(item.getHoldDate());
	                dto.setCcy(item.getCcy());
	                dto.setRegistBankCode(item.getRegistBankCode());
	                dto.setCheckSeqno(chbReportCheck.getCheckSeqno().trim());
	                investRegistUpdateList.add(dto);
	            });
	        }
	        //批量删除之前的检查数据
	        chbReportCheckMapper.delReportCheckDatas(checkseqnos);
	        if(CollectionUtils.isNotEmpty(resultList)){
	            //批量插入报送前检查表数据
	            chbReportCheckMapper.batchInsert(resultList);
	            //批量更新投资者身份信息检查序列号
	            if(CollectionUtils.isNotEmpty(investRegistUpdateList)){
	            	investorsHoldInfoRegist001Mapper.updateInvestRegistCheckSeqno(investRegistUpdateList);
	            }
	        }
	}
	/**
	 * 查询维护表中存在的相关数据 <功能详细描述>
	 *
	 * @param listE
	 *            数据集合
	 * @return 匹配的数据
	 * @see
	 * @since 1.0
	 */
	public Map<String, InvestorsHoldInfo001Dto> selectAllHoldInfoDataRegCodeKey(List<InvestorsHoldInfo001Dto> listE) {
		// 查询导入文件中的数据在数据库中已经存在的数据
		List<InvestorsHoldInfo001Dto> allDataList = new ArrayList<InvestorsHoldInfo001Dto>();

		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
//		if (maxDataCountOneTime < 1) {
//			maxDataCountOneTime = 1000;
//		}
		int dataCount = listE.size();
		if (dataCount <= maxDataCountOneTime) {
			allDataList.addAll(chbInvestholdInfoCheckDtoMapper.selectAllHoldInfoData(listE));
		} else {
			List<InvestorsHoldInfo001Dto> newKeyList = new ArrayList<InvestorsHoldInfo001Dto>();
			newKeyList.addAll(listE);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				allDataList.addAll(
						chbInvestholdInfoCheckDtoMapper.selectAllHoldInfoData(newKeyList.subList(0, maxDataCountOneTime)));
				newKeyList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newKeyList)) {
				allDataList.addAll(chbInvestholdInfoCheckDtoMapper.selectAllHoldInfoData(newKeyList));
			}
		}
		Map<String, InvestorsHoldInfo001Dto> allHoldInfoDataMap = StringUtil.isEmptyList(allDataList)
				? new HashMap<String, InvestorsHoldInfo001Dto>()
				: allDataList.stream().collect(JoyinCollectors.toMap(x -> x.getRegistBankCode()
						+ GlobalConstant.UNDER_LINE + x.getIndentifyMark() + GlobalConstant.UNDER_LINE
						+ x.getPrdRegistCode()  + GlobalConstant.UNDER_LINE + x.getCcy() + GlobalConstant.UNDER_LINE
						+ DateUtil.format(x.getHoldDate()), x -> x));
		return allHoldInfoDataMap;
	}
	
	/**
	 * 识别标识合并批量删除维护表中数据  <功能详细描述>
	 *
	 * @param keys
	 *            数据集合
	 * @see
	 * @since 1.0
	 */
	private void deleteInvestholdInfoBatchGroupDto(List<InvestorsHoldInfo001Dto> keys) {
		
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
//		if (maxDataCountOneTime < 1) {
//			maxDataCountOneTime = 1000;
//		}
		int dataCount = keys.size();
		if (dataCount <= maxDataCountOneTime) {
			chbInvestholdInfoCheckDtoMapper.deleteInvestholdInfoBatchDto(keys);
		} else {
			List<InvestorsHoldInfo001Dto> newKeyList = new ArrayList<InvestorsHoldInfo001Dto>();
			newKeyList.addAll(keys);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				chbInvestholdInfoCheckDtoMapper.deleteInvestholdInfoBatchDto(newKeyList.subList(0, maxDataCountOneTime));
				newKeyList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newKeyList)) {
				chbInvestholdInfoCheckDtoMapper.deleteInvestholdInfoBatchDto(newKeyList);
			}
		}
	}
	
	/**
	 * 识别标识合并批量删除登记表中数据 <功能详细描述>
	 *
	 * @param keys
	 *            数据集合
	 * @see
	 * @since 1.0
	 */
	private void deleteInvestholdInfoSubBatchGroupDto(List<InvestorsHoldInfo001Dto> keys) {
		
		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
//		if (maxDataCountOneTime < 1) {
//			maxDataCountOneTime = 1000;
//		}
		int dataCount = keys.size();
		if (dataCount <= maxDataCountOneTime) {
			chbInvestholdInfoCheckDtoMapper.deleteInvestholdInfoSubBatchDto(keys);
		} else {
			List<InvestorsHoldInfo001Dto> newKeyList = new ArrayList<InvestorsHoldInfo001Dto>();
			newKeyList.addAll(keys);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				chbInvestholdInfoCheckDtoMapper.deleteInvestholdInfoSubBatchDto(newKeyList.subList(0, maxDataCountOneTime));
				newKeyList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newKeyList)) {
				chbInvestholdInfoCheckDtoMapper.deleteInvestholdInfoSubBatchDto(newKeyList);
			}
		}
	}
    /**
     * 配置项中非空处理
     * @param Count
     * @param flag
     * @return
     */
    public int parsToInt(String count,String flag){
   	 if(StringUtil.isNotEmpty(count)&&Integer.parseInt(count)<=0){
   		 if("1".equals(flag)){
   	         	return 20;
   		 }
   		 else if("1".equals(flag)){
	         	return 500;
   		 }
   		 else {
	         	return 100;
   		 }
        }
        else{
        	return Integer.parseInt(count);
        }
   }
    InvestorsHoldInfo001Dto mapToDto(Map<String,String> map){
    	InvestorsHoldInfo001Dto result=new InvestorsHoldInfo001Dto();
		if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
			result.setAgent(map.get("AGENT")== null ? "" : map.get("AGENT"));
		}else{
	    result.setAgent(map.get("AGENT"));
		}
    	result.setAmount(new BigDecimal(map.get("AMOUNT")));
    	result.setCcy(map.get("CCY"));
    	result.setCnyAmt(new BigDecimal(map.get("CNY_AMT")));
    	result.setDeptCode(map.get("DEPT_CODE"));
    	result.setHoldDate(map.get("HOLD_DATE")==null?null:DateUtil.parse(map.get("HOLD_DATE")));
		if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
			result.setOrgCode(map.get("ORG_CODE")==null ? "C10403":map.get("ORG_CODE"));
		}else{
	    result.setOrgCode(map.get("ORG_CODE"));
		}
    	result.setPrdRegistCode(map.get("REG_CODE"));
    	result.setProdid(map.get("ACCOUNT"));
    	result.setQty(new BigDecimal(map.get("QTY")));
    	result.setRegistBankCode(map.get("REGIST_BANK_CODE"));
    	result.setIndentifyMark(map.get("INDENTIFY_MARK"));
		return result;
		
	}
    Map<String,String> dtoToMap(InvestorsHoldInfo001Dto dto){
    	Map<String,String> map=new HashMap<String,String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	map.put("AGENT",dto.getAgent());
    	map.put("AMOUNT",dto.getAmount()==null?"0":dto.getAmount().toString());
    	map.put("CCY",dto.getCcy());
    	map.put("CNY_AMT",dto.getCnyAmt()==null?"0":dto.getCnyAmt().toString());
    	map.put("DEPT_CODE",dto.getDeptCode());
    	map.put("HOLD_DATE",dto.getHoldDate()==null?"":sdf.format(dto.getHoldDate()));
    	map.put("ORG_CODE",dto.getOrgCode());
    	map.put("REG_CODE",dto.getPrdRegistCode());
    	map.put("ACCOUNT",dto.getProdid());
    	map.put("QTY",dto.getQty()==null?"0":dto.getQty().toString());
    	map.put("REGIST_BANK_CODE",dto.getRegistBankCode());
    	map.put("INDENTIFY_MARK",dto.getIndentifyMark());
		return map;
		
	}

	/**
	 * deleteAfterDeal
	 *分批执行
	 * @param deleteList 数据
	 * @see
	 * @since 1.0
	 */
	private void deleteAfterDeal(List<ChbInvestholdInfoCheckDto> deleteList) {

		logger.info("deleteBymodelAfterDeal处理开始：" + DateUtil.getCurrentDateTime());
		if (StringUtil.isEmptyList(deleteList)) {
			return;
		}
		int maxDataCountOneTime = reportInsertCount;
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
		if (isDB2DataBase) {
			maxDataCountOneTime = 1;
		}
		int dataCount = deleteList.size();
		if (dataCount <= maxDataCountOneTime) {			
			chbInvestholdInfoCheckDtoMapper.deleteBymodelAfterDeal(deleteList);
			return;
		}
		List<ChbInvestholdInfoCheckDto> newDataList = new ArrayList<ChbInvestholdInfoCheckDto>();
		newDataList.addAll(deleteList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			chbInvestholdInfoCheckDtoMapper.deleteBymodelAfterDeal(newDataList.subList(0, maxDataCountOneTime));;
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbInvestholdInfoCheckDtoMapper.deleteBymodelAfterDeal(newDataList);
		}
		logger.info("deleteBymodelAfterDeal处理结束：" + DateUtil.getCurrentDateTime());
	}

	/**
	 * deleteAfterDeal
	 *分批执行
	 * @param insertToChbInvestholdInfoList 数据
	 * @see
	 * @since 1.0
	 */
	private void insertToChbInvestholdInfoAfterDeal(List<ChbInvestholdInfoCheckDto> insertToChbInvestholdInfoList) {

		logger.info("insertToChbInvestholdInfoAfterDeal处理开始：" + DateUtil.getCurrentDateTime());
		if (StringUtil.isEmptyList(insertToChbInvestholdInfoList)) {
			return;
		}
		int maxDataCountOneTime = reportInsertCount;
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
		if (isDB2DataBase) {
			maxDataCountOneTime = 1;
		}
		int dataCount = insertToChbInvestholdInfoList.size();
		if (dataCount <= maxDataCountOneTime) {
			chbInvestholdInfoCheckDtoMapper.insertToChbInvestholdInfoAfterDeal(insertToChbInvestholdInfoList);
			return;
		}
		List<ChbInvestholdInfoCheckDto> newDataList = new ArrayList<ChbInvestholdInfoCheckDto>();
		newDataList.addAll(insertToChbInvestholdInfoList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			chbInvestholdInfoCheckDtoMapper.insertToChbInvestholdInfoAfterDeal(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbInvestholdInfoCheckDtoMapper.insertToChbInvestholdInfoAfterDeal(newDataList);
		}

		logger.info("insertToChbInvestholdInfoAfterDeal处理结束：" + DateUtil.getCurrentDateTime());
	}

	/**
	 * insertAfterDeal
	 *分批执行
	 * @param insert 数据
	 * @see
	 * @since 1.0
	 */
	private void insertAfterDeal(List<ChbInvestholdInfoCheckDto> insert) {
		logger.info("insertAfterDeal处理开始：" + DateUtil.getCurrentDateTime());
		if (StringUtil.isEmptyList(insert)) {
			return;
		}
		int maxDataCountOneTime = reportInsertCount;
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
		if (isDB2DataBase) {
			maxDataCountOneTime = 1;
		}
		int dataCount = insert.size();
		if (dataCount <= maxDataCountOneTime) {
			
			chbInvestholdInfoCheckDtoMapper.insertAfterDeal(insert);
			return;
		}
		List<ChbInvestholdInfoCheckDto> newDataList = new ArrayList<ChbInvestholdInfoCheckDto>();
		newDataList.addAll(insert);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			chbInvestholdInfoCheckDtoMapper.insertAfterDeal(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbInvestholdInfoCheckDtoMapper.insertAfterDeal(newDataList);
		}
		logger.info("insertAfterDeal处理结束：" + DateUtil.getCurrentDateTime());
	}

	/**
	 * insertAfterDeal
	 *分批执行
	 * @param dtoAfterDealList 数据
	 * @see
	 * @since 1.0
	 */
	private void updataOperateStatus(List<ChbInvestholdInfoCheckDto> dtoAfterDealList) {
		logger.info("insertAfterDeal处理开始：" + DateUtil.getCurrentDateTime());
		if (StringUtil.isEmptyList(dtoAfterDealList)) {
			return;
		}
		int maxDataCountOneTime = reportInsertCount;
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
		if (isDB2DataBase) {
			maxDataCountOneTime = 1;
		}
		int dataCount = dtoAfterDealList.size();
		if (dataCount <= maxDataCountOneTime) {			
			chbInvestholdInfoCheckDtoMapper.updataOperateStatus(dtoAfterDealList);
			return;
		}
		List<ChbInvestholdInfoCheckDto> newDataList = new ArrayList<ChbInvestholdInfoCheckDto>();
		newDataList.addAll(dtoAfterDealList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++ ) {
			chbInvestholdInfoCheckDtoMapper.updataOperateStatus(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			chbInvestholdInfoCheckDtoMapper.updataOperateStatus(newDataList);			
		}
		logger.info("insertAfterDeal处理结束：" + DateUtil.getCurrentDateTime());
	}


	/*
	 * 开启线程池模式查询大数据量，每个线程查100
	 * */
	private List<ChbInvestholdInfoCheckDto> selectAllChbEquHoldCheck(List<ChbInvestholdInfoCheckDto> list) {

		List<Callable<Boolean>> tasks = new ArrayList<>();

		List<ChbInvestholdInfoCheckDto> result = Collections.synchronizedList(new ArrayList<ChbInvestholdInfoCheckDto>());
		if (null != list) {
			ExecutorService service = new JoyinThreadPoolExecutor(10);
			int start = 0;
			int end = 0;
			int size = list.size();
			int max = (int) Math.ceil((double) size / 100);
			// 信号量
			CountDownLatch countDown = new CountDownLatch(max);

			for (int i = 0; i < max; i++) {
				start = end;
				end = Math.min(size, 100 * (i + 1));
				List<ChbInvestholdInfoCheckDto> subList = list.subList(start, end);

				// 提交任务
				tasks.add(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						result.addAll(chbInvestholdInfoCheckDtoMapper.selectAllChbEquHoldCheck(subList));
						countDown.countDown();// 个数-1
						return true;
					}
				});
			}
			// 添加线程等待转换线执行
			try {
				service.invokeAll(tasks);
				countDown.await();
				service.shutdown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 持有信息修改之前查询反馈状态
	 * <功能详细描述>
	 * @param pageParam
	 * @see
	 * @since 1.0
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void insertChbSubHoldInfoData(List<ChbInvestholdInfoSub> subList){
		investorsHoldInfo001Mapper.insertChbSubHoldInfoData(subList);
	}

	/**
	 * 分批维护数据插入数据库 <功能详细描述>
	 *
	 * @param dataList
	 *            待处理数据
	 * @see
	 * @since 1.0
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void insertChbHoldInfoDataByPart(List<InvestorsHoldInfo001Dto> dataList) {
		if (CollectionUtils.isNotEmpty(dataList)) {
			int maxDataCountOneTime = this.parsToInt(reportInsertLongCount, "1");
			int dataCount = dataList.size();
			if (dataCount <= maxDataCountOneTime) {
				investorsHoldInfo001Mapper.insertChbHoldInfoData(dataList);
				return;
			}

			List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
			newDataList.addAll(dataList);

			// 分批处理
			int part = dataCount / maxDataCountOneTime; // 分批数
			for (int i = 0; i < part; i++) {
				investorsHoldInfo001Mapper.insertChbHoldInfoData(newDataList.subList(0, maxDataCountOneTime));
				newDataList.subList(0, maxDataCountOneTime).clear();
			}
			// 最后剩下的数据
			if (CollectionUtils.isNotEmpty(newDataList)) {
				investorsHoldInfo001Mapper.insertChbHoldInfoData(newDataList);
			}
		}
	}

	/**
	 * 画面导入及新增修改时，处理数据库中存在的删除数据
	 *
	 * @param listE 数据对象列表
	 * @see
	 * @since 1.0
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void handleStatusDeleteData(List<InvestorsHoldInfo001Dto> dataList) {
		if (StringUtil.isEmptyList(dataList)) {
			return;
		}

		int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
		if (maxDataCountOneTime < 1) {
			maxDataCountOneTime = 100;
		}
		int dataCount = dataList.size();
		if (dataCount <= maxDataCountOneTime) {
			investorsHoldInfo001Mapper.handleInfoDeleteData(dataList);
			investorsHoldInfo001Mapper.handleSubDeleteData(dataList);
			return;
		}

		List<InvestorsHoldInfo001Dto> newDataList = new ArrayList<InvestorsHoldInfo001Dto>();
		newDataList.addAll(dataList);

		// 分批处理
		int part = dataCount / maxDataCountOneTime; // 分批数
		for (int i = 0; i < part; i++) {
			investorsHoldInfo001Mapper.handleInfoDeleteData(newDataList.subList(0, maxDataCountOneTime));
			investorsHoldInfo001Mapper.handleSubDeleteData(newDataList.subList(0, maxDataCountOneTime));
			newDataList.subList(0, maxDataCountOneTime).clear();
		}
		// 最后剩下的数据
		if (CollectionUtils.isNotEmpty(newDataList)) {
			investorsHoldInfo001Mapper.handleInfoDeleteData(newDataList);
			investorsHoldInfo001Mapper.handleSubDeleteData(newDataList);
		}
	}

	/**
	 * 通过临时表删除info表数据
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void deleteChbInvestholdInfoByTmp() {
		chbInvestholdInfoCheckDtoMapper.deleteChbInvestholdInfoByTmp();
	}

	/**
	 * 通过临时表删除sub表数据
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void deleteChbInvestholdInfoSubByTmp() {
		chbInvestholdInfoCheckDtoMapper.deleteChbInvestholdInfoSubByTmp();
	}


	/**
	 * 保存数据到INFO
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveChbInvestholdInfo(List<InvestorsHoldInfo001Dto> list) {
		ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO");
		table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
		// 临时文件存放目录
		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		batchInsertService.exeBatchldr(tmpPath, list, table);
	}

	/**
	 * 保存数据到SUB
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveChbInvestholdInfoSub(List<ChbInvestholdInfoSub> list) {
		ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO_SUB");
		table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
		// 临时文件存放目录
		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		batchInsertService.exeBatchldr(tmpPath, list, table);
	}

	/**
	 * 保存数据到check表
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	public void saveChbInvestholdInfoCheck(List<ChbInvestholdInfoCheckDto> list) {
		ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO_CHECK");
		table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
		for (ImpColumn column : table.getColumns()) {
			if (StringUtil.equals(column.getName(), "OPERATE_STATUS")) {
				column.setValueField("operatestatus");
			}
			if (StringUtil.equals(column.getName(), "MERGER_INDENTIFY_MARK")) {
				column.setValueField("mergerindentifymark");
			}
		}
		// 临时文件存放目录
		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		batchInsertService.exeBatchldr(tmpPath, list, table);
	}

	/**
	 * 保存数据到临时表
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveChbInvestholdInfoCheckTmpByInfo(List<InvestorsHoldInfo001Dto> list) {
		ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO_TMP");
		table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
		// 临时文件存放目录
		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		batchInsertService.exeBatchldr(tmpPath, list, table);
	}

	/**
	 * 保存数据到临时表
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public void saveChbInvestholdInfoCheckTmpBySub(List<ChbInvestholdInfoSub> list) {
		ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO_TMP");
		table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
		// 临时文件存放目录
		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		batchInsertService.exeBatchldr(tmpPath, list, table);
	}

	/**
	 * 保存数据到临时表
	 * @param keys
	 * todo 小数据量不用sqlldr
	 */
	public void saveChbInvestholdInfoCheckTmp(List<ChbInvestholdInfoCheckDto> list) {
		ImpTable table = new ImpTable("CHB_INVESTHOLD_INFO_TMP");
		table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
		for (ImpColumn column : table.getColumns()) {
			if (StringUtil.equals(column.getName(), "OPERATE_STATUS")) {
				column.setValueField("operatestatus");
			}
			if (StringUtil.equals(column.getName(), "MERGER_INDENTIFY_MARK")) {
				column.setValueField("mergerindentifymark");
			}
		}
		// 临时文件存放目录
		String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		batchInsertService.exeBatchldr(tmpPath, list, table);
	}


	ChbInvestholdInfoCheckDto mapToDtoCheck(Map<String,String> map){
		ChbInvestholdInfoCheckDto result=new ChbInvestholdInfoCheckDto();
		if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
			result.setAgent(map.get("AGENT")== null ? "" : map.get("AGENT"));
		}else{
			result.setAgent(map.get("AGENT"));
		}
		result.setAmount(new BigDecimal(map.get("AMOUNT")));
		result.setCcy(map.get("CCY"));
		result.setCnyAmt(new BigDecimal(map.get("CNY_AMT")));
		result.setDeptCode(map.get("DEPT_CODE"));
		result.setHoldDate(map.get("HOLD_DATE")==null?null:DateUtil.parse(map.get("HOLD_DATE")));
		if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
			result.setOrgCode(map.get("ORG_CODE")==null ? "C10403":map.get("ORG_CODE"));
		}else{
			result.setOrgCode(map.get("ORG_CODE"));
		}
		result.setPrdRegistCode(map.get("REG_CODE"));
		result.setProdid(map.get("ACCOUNT"));
		result.setQty(new BigDecimal(map.get("QTY")));
		result.setRegistBankCode(map.get("REGIST_BANK_CODE"));
		result.setIndentifyMark(map.get("INDENTIFY_MARK"));
		result.setMergerindentifymark(map.get("MERGER_INDENTIFY_MARK"));
		result.setRemark(map.get("REMARK"));
		result.setOperatestatus(map.get("OPERATE_STATUS"));
		result.setInterfCheckResult(map.get("INTERF_CHECK_RESULT"));

		return result;
	}
}
