package com.joyintech.tams.svreport.pbc.prod.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.pbc.common.annotation.TemplateName;
import com.joyintech.tams.svreport.pbc.common.constants.PbcReportProperycode;
import com.joyintech.tams.svreport.pbc.common.constants.PbcReportType;
import com.joyintech.tams.svreport.pbc.common.constants.PbcTemplateName;
import com.joyintech.tams.svreport.pbc.prod.dao.PbcMonthPrdPostionMapper;
import com.joyintech.tams.svreport.pbc.prod.dto.PbcMonthPrdPostionDto;
import com.joyintech.tams.svreport.pbc.prod.dto.PbcReportProdDetailDto;
import com.joyintech.tams.svreport.pbc.report.dto.PbcMonthReport;


/**
 * 功能:表1-3只数情况
 * @since 1.6.0
 * @version 1.6.0
 * @作者：lingxq
 * @创建时间：2019年3月4日
 * @修改记录：
 */
@Service
@TemplateName(PbcTemplateName.PBC_REPORT_1_3)
public class PbcReportProd001Sub003Service extends PbcReportProdTotalService  {
	@Autowired
	private PbcMonthPrdPostionMapper pbcMonthPrdPostionMapper;
	
	
	public void generateReport(Date generateDate,Date reportStartDate,Date reportEndDate,List<PbcMonthPrdPostionDto> posList,boolean isEdit){
		//主表信息
		PbcMonthReport pbcMonthReport = getReportModel(generateDate,reportStartDate,reportEndDate,PbcReportType.PBC_REPORT_1_3,isEdit);
		String reportuuid = pbcMonthReport.getReportuuid();

		//明细
		Map<String,PbcReportProdDetailDto> resultTMP = getReportDetailList(reportuuid,PbcReportType.PBC_REPORT_1_3,DictCodeListExt1.维护数据状态_有效.getValue());

		
		//update by leipeng 20200319  汇总数据提取全量模板
		Map<String,PbcReportProdDetailDto> result = new HashMap<>();
		result.putAll(resultTMP);
		Map<String,Map<String,PbcReportProdDetailDto>> deptMap = new HashMap();

		 if(posList==null||posList.isEmpty()){
			 //没有头寸，不需要插入
			 insertReportAndDetails(reportStartDate,reportEndDate,pbcMonthReport,null,result,PbcReportType.PBC_REPORT_1_3,false);
			 return;
		 }
//		 PbcReportProdDetailDto line300000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_300000_ZJ); //   产品募集余额总计
////		 PbcReportProdDetailDto line320000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_320000_YXFS); //   运行方式
//		 PbcReportProdDetailDto line321000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_321000_KFSJZX); //   开放式净值型
//		 PbcReportProdDetailDto line322000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_322000_KFSFJZX); //   开放式非净值型
//		 PbcReportProdDetailDto line323000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_323000_FBSJZX); //   封闭式净值型
//		 PbcReportProdDetailDto line324000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_324000_FBSFJZX); //   封闭式非净值型
////		 PbcReportProdDetailDto line330000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_330000_MJFS); //   募集方式
//		 PbcReportProdDetailDto line331000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_331000_GKMJ); //   公开募集
//		 PbcReportProdDetailDto line332000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_332000_FGKMJ); //   非公开募集
////		 PbcReportProdDetailDto line340000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_340000_GLFS); //   管理方式
//		 PbcReportProdDetailDto line341000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_341000_JHGL); //   集合管理
//		 PbcReportProdDetailDto line342000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_342000_DDGL); //   单独管理
////		 PbcReportProdDetailDto line350000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_350000_STZZ); //   受托职责
//		 PbcReportProdDetailDto line351000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_351000_ZDGL); //   主动管理
//		 PbcReportProdDetailDto line352000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_352000_BDGL); //   被动管理
////		 PbcReportProdDetailDto line360000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_360000_CPLX); //   产品类型
//		 PbcReportProdDetailDto line361000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_361000_GDSYL); //   固定收益类
//		 PbcReportProdDetailDto line362000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_362000_QYL); //   权益类
//		 PbcReportProdDetailDto line363000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_363000_SPJJRYSPL); //   商品及金融衍生品类
//		 PbcReportProdDetailDto line364000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_364000_HHL); //   混合类
////		 PbcReportProdDetailDto line390000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_390000_F); //   附:
//		 PbcReportProdDetailDto line391000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_391000_QZFJCP); //   其中：分级产品
//		 PbcReportProdDetailDto line392000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_392000_QZSYQZRCP); //   其中：收益权转让产品
//		 PbcReportProdDetailDto line393000 = result.get(PbcReportProperycode.ZSQK_QMCX_AMT_393000_QZHBSCJJ); //   其中：货币市场基金
//		 
//		 PbcReportProdDetailDto line300000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_300000_ZJ); //   产品募集余额总计
////		 PbcReportProdDetailDto line320000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_320000_YXFS); //   运行方式
//		 PbcReportProdDetailDto line321000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_321000_KFSJZX); //   开放式净值型
//		 PbcReportProdDetailDto line322000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_322000_KFSFJZX); //   开放式非净值型
//		 PbcReportProdDetailDto line323000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_323000_FBSJZX); //   封闭式净值型
//		 PbcReportProdDetailDto line324000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_324000_FBSFJZX); //   封闭式非净值型
////		 PbcReportProdDetailDto line330000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_330000_MJFS); //   募集方式
//		 PbcReportProdDetailDto line331000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_331000_GKMJ); //   公开募集
//		 PbcReportProdDetailDto line332000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_332000_FGKMJ); //   非公开募集
////		 PbcReportProdDetailDto line340000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_340000_GLFS); //   管理方式
//		 PbcReportProdDetailDto line341000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_341000_JHGL); //   集合管理
//		 PbcReportProdDetailDto line342000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_342000_DDGL); //   单独管理
////		 PbcReportProdDetailDto line350000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_350000_STZZ); //   受托职责
//		 PbcReportProdDetailDto line351000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_351000_ZDGL); //   主动管理
//		 PbcReportProdDetailDto line352000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_352000_BDGL); //   被动管理
////		 PbcReportProdDetailDto line360000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_360000_CPLX); //   产品类型
//		 PbcReportProdDetailDto line361000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_361000_GDSYL); //   固定收益类
//		 PbcReportProdDetailDto line362000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_362000_QYL); //   权益类
//		 PbcReportProdDetailDto line363000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_363000_SPJJRYSPL); //   商品及金融衍生品类
//		 PbcReportProdDetailDto line364000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_364000_HHL); //   混合类
////		 PbcReportProdDetailDto line390000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_390000_F); //   附:
//		 PbcReportProdDetailDto line391000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_391000_QZFJCP); //   其中：分级产品
//		 PbcReportProdDetailDto line392000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_392000_QZSYQZRCP); //   其中：收益权转让产品
//		 PbcReportProdDetailDto line393000Sub2 = result.get(PbcReportProperycode.ZSQK_DQXF_AMT_393000_QZHBSCJJ); //   其中：货币市场基金
//		 
//		 PbcReportProdDetailDto line300000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_300000_ZJ); //   产品募集余额总计
////		 PbcReportProdDetailDto line320000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_320000_YXFS); //   运行方式
//		 PbcReportProdDetailDto line321000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_321000_KFSJZX); //   开放式净值型
//		 PbcReportProdDetailDto line322000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_322000_KFSFJZX); //   开放式非净值型
//		 PbcReportProdDetailDto line323000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_323000_FBSJZX); //   封闭式净值型
//		 PbcReportProdDetailDto line324000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_324000_FBSFJZX); //   封闭式非净值型
////		 PbcReportProdDetailDto line330000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_330000_MJFS); //   募集方式
//		 PbcReportProdDetailDto line331000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_331000_GKMJ); //   公开募集
//		 PbcReportProdDetailDto line332000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_332000_FGKMJ); //   非公开募集
////		 PbcReportProdDetailDto line340000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_340000_GLFS); //   管理方式
//		 PbcReportProdDetailDto line341000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_341000_JHGL); //   集合管理
//		 PbcReportProdDetailDto line342000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_342000_DDGL); //   单独管理
////		 PbcReportProdDetailDto line350000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_350000_STZZ); //   受托职责
//		 PbcReportProdDetailDto line351000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_351000_ZDGL); //   主动管理
//		 PbcReportProdDetailDto line352000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_352000_BDGL); //   被动管理
////		 PbcReportProdDetailDto line360000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_360000_CPLX); //   产品类型
//		 PbcReportProdDetailDto line361000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_361000_GDSYL); //   固定收益类
//		 PbcReportProdDetailDto line362000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_362000_QYL); //   权益类
//		 PbcReportProdDetailDto line363000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_363000_SPJJRYSPL); //   商品及金融衍生品类
//		 PbcReportProdDetailDto line364000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_364000_HHL); //   混合类
////		 PbcReportProdDetailDto line390000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_390000_F); //   附:
//		 PbcReportProdDetailDto line391000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_391000_QZFJCP); //   其中：分级产品
//		 PbcReportProdDetailDto line392000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_392000_QZSYQZRCP); //   其中：收益权转让产品
//		 PbcReportProdDetailDto line393000Sub3 = result.get(PbcReportProperycode.ZSQK_DQZZ_AMT_393000_QZHBSCJJ); //   其中：货币市场基金
		 //产品去重 算只数时
		 Map<String,String> prodMap=new HashMap<String,String>();
		 for(PbcMonthPrdPostionDto pos :posList){
			 if(prodMap.containsKey(pos.getProdid()+"_"+pos.getCcy())) {
				 continue;
			 }else {
				 prodMap.put(pos.getProdid()+"_"+pos.getCcy(), pos.getProdid()+"_"+pos.getCcy());
			 }
		     
		  // add by leipeng 20200319 获取所有部门对应信息,并列出单独的表结构
			 if(null!=pos.getInstitutionCode()&&null==deptMap.get(pos.getInstitutionCode())){
				 Map<String,PbcReportProdDetailDto> singDeptMap = getReportDetailList(reportuuid,PbcReportType.PBC_REPORT_1_3,DictCodeListExt1.维护数据状态_有效.getValue());
				 deptMap.put(pos.getInstitutionCode(), singDeptMap);
			 }
		     
		     
			 if(DictCodeListExt2.存续状态_存续.getValue().equals(pos.getDurationflag())){
				 //合计
//				 setProdNum(line300000,pos.getCcy());

				 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_300000_ZJ, pos.getCcy());
				 //按分类
				 if(DictCodeListExt2.产品运作模式_开放式净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line321000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_321000_KFSJZX, pos.getCcy());

				 }
				 if(DictCodeListExt2.产品运作模式_开放式非净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line322000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_322000_KFSFJZX, pos.getCcy());

				 }
				 if(DictCodeListExt2.产品运作模式_封闭式净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line323000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_323000_FBSJZX, pos.getCcy());

				 }
				 if(DictCodeListExt2.产品运作模式_封闭式非净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line324000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_324000_FBSFJZX, pos.getCcy());

				 }
				 if(DictCodeListExt2.募集方式_中债_公募.getValue().equals(pos.getRaisemode())){
//					 setProdNum(line331000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_331000_GKMJ, pos.getCcy());

				 }
				 if(DictCodeListExt2.募集方式_中债_私募.getValue().equals(pos.getRaisemode())){
//					 setProdNum(line332000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_332000_FGKMJ, pos.getCcy());

				 }
				 if(DictCodeList.排期发行方式_集合.getValue().equals(pos.getManageType())){
//					 setProdNum(line341000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_341000_JHGL, pos.getCcy());

				 }
				 if(DictCodeList.排期发行方式_单一.getValue().equals(pos.getManageType())){
//					 setProdNum(line342000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_342000_DDGL, pos.getCcy());

				 }
				 if(DictCodeListExt2.产品受托职责_中债_主动管理.getValue().equals(pos.getFiduciaryDuty())){
//					 setProdNum(line351000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_351000_ZDGL, pos.getCcy());

				 }
				 if(DictCodeListExt2.产品受托职责_中债_被动管理.getValue().equals(pos.getFiduciaryDuty())){
//					 setProdNum(line352000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_352000_BDGL, pos.getCcy());

				 }
				 if(DictCodeList.投资性质_固定收益类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line361000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_361000_GDSYL, pos.getCcy());

				 }
				 if(DictCodeList.投资性质_权益类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line362000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_362000_QYL, pos.getCcy());

				 }
				 if(DictCodeList.投资性质_商品及金融衍生品类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line363000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_363000_SPJJRYSPL, pos.getCcy());

				 }
				 if(DictCodeList.投资性质_混合类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line364000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_364000_HHL, pos.getCcy());

				 }
				 if(DictCodeList.是否_中债_是.getValue().equals(pos.getIsConstracte())){
//					 setProdNum(line391000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_391000_QZFJCP, pos.getCcy());

				 }
				 if(DictCodeListExt2.产品转让类型_中债_收益权转让.getValue().equals(pos.getTransferType())){
//					 setProdNum(line392000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_392000_QZSYQZRCP, pos.getCcy());

				 }
				 if(DictCodeList.是否_中债_是.getValue().equals(pos.getIsMarketFund())){
//					 setProdNum(line393000,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_QMCX_AMT_393000_QZHBSCJJ, pos.getCcy());

				 }
			 }
			 
			 if(pos.getVdate().compareTo(DateUtil.parse(DateUtil.format(reportStartDate))) >= 0
					 && pos.getVdate().compareTo(DateUtil.parse(DateUtil.format(reportEndDate))) <= 0
					 &&!DictCodeListExt2.存续状态_未起息.getValue().equals(pos.getDurationflag())){
				 //合计
//				 setProdNum(line300000Sub2,pos.getCcy());
				 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_300000_ZJ, pos.getCcy());

				 //按分类
				 if(DictCodeListExt2.产品运作模式_开放式净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line321000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_321000_KFSJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品运作模式_开放式非净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line322000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_322000_KFSFJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品运作模式_封闭式净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line323000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_323000_FBSJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品运作模式_封闭式非净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line324000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_324000_FBSFJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.募集方式_中债_公募.getValue().equals(pos.getRaisemode())){
//					 setProdNum(line331000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_331000_GKMJ, pos.getCcy());
				 }
				 if(DictCodeListExt2.募集方式_中债_私募.getValue().equals(pos.getRaisemode())){
//					 setProdNum(line332000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_332000_FGKMJ, pos.getCcy());
				 }
				 if(DictCodeList.排期发行方式_集合.getValue().equals(pos.getManageType())){
//					 setProdNum(line341000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_341000_JHGL, pos.getCcy());
				 }
				 if(DictCodeList.排期发行方式_单一.getValue().equals(pos.getManageType())){
//					 setProdNum(line342000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_342000_DDGL, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品受托职责_中债_主动管理.getValue().equals(pos.getFiduciaryDuty())){
//					 setProdNum(line351000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_351000_ZDGL, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品受托职责_中债_被动管理.getValue().equals(pos.getFiduciaryDuty())){
//					 setProdNum(line352000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_352000_BDGL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_固定收益类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line361000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_361000_GDSYL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_权益类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line362000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_362000_QYL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_商品及金融衍生品类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line363000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_363000_SPJJRYSPL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_混合类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line364000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_364000_HHL, pos.getCcy());
				 }
				 if(DictCodeList.是否_中债_是.getValue().equals(pos.getIsConstracte())){
//					 setProdNum(line391000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_391000_QZFJCP, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品转让类型_中债_收益权转让.getValue().equals(pos.getTransferType())){
//					 setProdNum(line392000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_392000_QZSYQZRCP, pos.getCcy());
				 }
				 if(DictCodeList.是否_中债_是.getValue().equals(pos.getIsMarketFund())){
//					 setProdNum(line393000Sub2,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQXF_AMT_393000_QZHBSCJJ, pos.getCcy());
				 }
			 }
			 
			 if(DictCodeListExt2.存续状态_终止.getValue().equals(pos.getDurationflag())){
				 //合计
//				 setProdNum(line300000Sub3,pos.getCcy());
				 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_300000_ZJ, pos.getCcy());
				 //按分类
				 if(DictCodeListExt2.产品运作模式_开放式净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line321000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_321000_KFSJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品运作模式_开放式非净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line322000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_322000_KFSFJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品运作模式_封闭式净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line323000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_323000_FBSJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品运作模式_封闭式非净值型.getValue().equals(pos.getOperateMode())){
//					 setProdNum(line324000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_324000_FBSFJZX, pos.getCcy());
				 }
				 if(DictCodeListExt2.募集方式_中债_公募.getValue().equals(pos.getRaisemode())){
//					 setProdNum(line331000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_331000_GKMJ, pos.getCcy());
				 }
				 if(DictCodeListExt2.募集方式_中债_私募.getValue().equals(pos.getRaisemode())){
//					 setProdNum(line332000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_332000_FGKMJ, pos.getCcy());
				 }
				 if(DictCodeList.排期发行方式_集合.getValue().equals(pos.getManageType())){
//					 setProdNum(line341000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_341000_JHGL, pos.getCcy());
				 }
				 if(DictCodeList.排期发行方式_单一.getValue().equals(pos.getManageType())){
//					 setProdNum(line342000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_342000_DDGL, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品受托职责_中债_主动管理.getValue().equals(pos.getFiduciaryDuty())){
//					 setProdNum(line351000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_351000_ZDGL, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品受托职责_中债_被动管理.getValue().equals(pos.getFiduciaryDuty())){
//					 setProdNum(line352000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_352000_BDGL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_固定收益类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line361000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_361000_GDSYL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_权益类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line362000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_362000_QYL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_商品及金融衍生品类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line363000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_363000_SPJJRYSPL, pos.getCcy());
				 }
				 if(DictCodeList.投资性质_混合类.getValue().equals(pos.getIvstorientation())){
//					 setProdNum(line364000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_364000_HHL, pos.getCcy());
				 }
				 if(DictCodeList.是否_中债_是.getValue().equals(pos.getIsConstracte())){
//					 setProdNum(line391000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_391000_QZFJCP, pos.getCcy());
				 }
				 if(DictCodeListExt2.产品转让类型_中债_收益权转让.getValue().equals(pos.getTransferType())){
//					 setProdNum(line392000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_392000_QZSYQZRCP, pos.getCcy());
				 }
				 if(DictCodeList.是否_中债_是.getValue().equals(pos.getIsMarketFund())){
//					 setProdNum(line393000Sub3,pos.getCcy());
					 setProdNumMap(result,deptMap.get(pos.getInstitutionCode()), PbcReportProperycode.ZSQK_DQZZ_AMT_393000_QZHBSCJJ, pos.getCcy());
				 }			 
			 }	
		 }
		 
		// 数据先删后插
		insertReportAndDetails(reportStartDate,reportEndDate,pbcMonthReport,null,result,PbcReportType.PBC_REPORT_1_3,true);
		
		
		//add by leipeng 20200319  各法人生成数据
		for (Map.Entry<String,Map<String,PbcReportProdDetailDto>> map : deptMap.entrySet()) {
			// 各法人数据先删后插
			insertReportAndDetails(reportStartDate,reportEndDate,pbcMonthReport,map.getKey(),map.getValue(),PbcReportType.PBC_REPORT_1_3,true);
		}
	
	}
	   
    public void setProdNum(PbcReportProdDetailDto dto,String ccy){
        if(DictCodeListExt1.币种_中债_人民币.getValue().equals(ccy)){//人民币
            dto.setCny(dto.getCny().add(BigDecimal.ONE));
        }else{ //其他币种
            dto.setOthToCny((dto.getOthToCny().add(BigDecimal.ONE)));
        }
        //合计
        dto.setTotal(dto.getTotal().add(BigDecimal.ONE));
    }
    
    
    
    public void setProdNumMap(Map<String,PbcReportProdDetailDto> dto,Map<String,PbcReportProdDetailDto> deptDto,String code,String ccy){
    	//汇总数据计算
    	setProdNum(dto.get(code), ccy);
    	//部门数据计算
    	if(null!=deptDto&&null!=deptDto.get(code)){
    		setProdNum(deptDto.get(code), ccy);
    	}
    }
}
