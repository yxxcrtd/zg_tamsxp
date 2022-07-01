package com.joyintech.tams.report.home.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.home.dao.BusinessHomeMapper;
import com.joyintech.tams.report.home.dto.BusinessHomeDto;
import com.joyintech.tams.report.home.dto.HomeCellDto;
import com.joyintech.tams.report.home.dto.VbusinessHomeDto;

/**
 * @Description 业务首页
 * @author jiangxiao
 * @version 1.7 2019年5月16日
 * @since 1.7
 */
@Service
public class BusinessHomeService {
	@Autowired
	private BusinessHomeMapper businessHomeMapper;

	private static final String 产品登记编码_PAGEID = "ProRegistCodeManage001";
	private static final String 识别标识合并_PAGEID = "SvrInvestorMerger001";

	private static final List<String> INTERF_TYPE_EQU = Arrays.asList("CHB_EQUDET","CHB_EQU_HOLD","CHB_INVESTHOLD_INFO");

	public List<VbusinessHomeDto> selectBusinessHomeInfo(String searchDate, List<String> list){
		//查询系统配置的接口
		List<String> interflist = businessHomeMapper.selectInterfType();
		//add by wm 投资者明细不正常显示问题修复 20201113 start
		interflist.add("CHB_EQUDET");
		//add by wm 投资者明细不正常显示问题修复 20201113 end
		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("searchDate", searchDate);
//        String isDeptControl = SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_IS_DEPTCODE_CONTROL);
//        if(StringUtil.equals(isDeptControl, DictCodeList.是否_中债_否.getValue())) {
//            parmMap.put("orgCodeList", CommonUtil.getAuthorityOnlyOrgListByUserName(null));
//        } else {
//            parmMap.put("createDeptList", CommonUtil.getAuthorityDeptByUserName(null));
//        }
		//查询接口监控表数据
		List<BusinessHomeDto> businessinfolist = businessHomeMapper.selectBusinessHomeInfo(parmMap);
		//查询数据检查的数据
		parmMap.put("businessinfolist",businessinfolist);
		List<BusinessHomeDto> checkdatalist = businessHomeMapper.selectDateCheckInfo(parmMap);
		//查询产品登记编码统计数
		int prodRegistnum = 0;
		//查询识别标识合并统计数
		int mergenum = 0;
		if(list.contains(产品登记编码_PAGEID)){
			prodRegistnum = businessHomeMapper.selectRegistnum(parmMap);
		} else {
			prodRegistnum = -1;
		}
		if(list.contains(识别标识合并_PAGEID)){
			mergenum = businessHomeMapper.selectMergenum(parmMap);
		} else {
			mergenum = -1;
		}
		//接口文件总数
		int accessFiletotal = 0;
		//数据检查总数
		int checkDatatotal = 0;
		//数据维护总数
		int processedDatatotal = 0;
		int chbEquHold = businessHomeMapper.selectChbEquHold();
		int chbInvest = businessHomeMapper.selectChbInvestTrade(searchDate);
		int chbEqudet = businessHomeMapper.selectChbEqudet(searchDate);
		businessHomeMapper.selectChbEquHold();
		for (BusinessHomeDto businessinfo : businessinfolist) {
			// 如果系统存在该接口
			if (businessinfo.getInterfFileType() != null && interflist.contains(businessinfo.getInterfFileType())) {
				for (BusinessHomeDto checkdata : checkdatalist) {
					if (businessinfo.getInterfFileType().equals(checkdata.getInterfFileType())) {
						// businessinfo.setDatanum(checkdata.getDatanum());
						// 检查通过c
						businessinfo.setCheckSuccessnum(businessinfo.getDatanum() - checkdata.getCheckfailnum());
						// 检查不通过
						businessinfo.setCheckfailnum(checkdata.getCheckfailnum());
						// 待处理
						businessinfo.setProcessednum(checkdata.getCheckfailnum());
					}
				}
				checkDatatotal = checkDatatotal + businessinfo.getCheckfailnum();
				accessFiletotal = accessFiletotal + businessinfo.getExceptionnum();
			}
		}
		for (BusinessHomeDto checkdata : checkdatalist) {
			if(checkdata.getInterfFileType() != null && interflist.contains(checkdata.getInterfFileType())){
				processedDatatotal = processedDatatotal + checkdata.getCheckfailnum();
			}
		}
		processedDatatotal = processedDatatotal+chbEquHold+chbInvest+chbEqudet;
		List<VbusinessHomeDto> vlist = dealVdto(businessinfolist,interflist,chbEquHold,chbInvest,chbEqudet);
		VbusinessHomeDto divdto = new VbusinessHomeDto();
		divdto.setReportCode("TOTAL");
		divdto.setProdRegistnum(prodRegistnum);
		divdto.setMergenum(mergenum);
		divdto.setAccessFiletotal(accessFiletotal);
		divdto.setCheckDatatotal(checkDatatotal);
		divdto.setProcessedDatatotal(processedDatatotal);
		vlist.add(divdto);
		return vlist;
	}
	/**
	 * 一体化业务首页
	 * @param searchDate 查询日期
	 * @param list   菜单list
	 * @return
	 */
	public List<VbusinessHomeDto> selectBusinessHomeInfoAll(String searchDate, List<String> list){
		//查询系统配置的接口

		List<String> interflist = SvrDataUtil.getSysDictDataList(DictCode.校验接口类型);
		//增加三期接口
		interflist.addAll(INTERF_TYPE_EQU);

		Map<String, Object> parmMap = new HashMap<>();
		parmMap.put("searchDate", searchDate);
//        String isDeptControl = SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_IS_DEPTCODE_CONTROL);
//        if(StringUtil.equals(isDeptControl, DictCodeList.是否_中债_否.getValue())) {
//            parmMap.put("orgCodeList", CommonUtil.getAuthorityOnlyOrgListByUserName(null));
//        } else {
//            parmMap.put("createDeptList", CommonUtil.getAuthorityDeptByUserName(null));
//        }
		//查询接口监控表数据
		List<BusinessHomeDto> businessinfolist = businessHomeMapper.selectBusinessHomeInfoAll(parmMap);
		//查询数据检查的数据
		List<BusinessHomeDto> checkdatalist = businessHomeMapper.selectDateCheckInfo(parmMap);
		//查询产品登记编码统计数
		int prodRegistnum = 0;
		//查询识别标识合并统计数
		int mergenum = 0;
		if(list.contains(产品登记编码_PAGEID)){
			prodRegistnum = businessHomeMapper.selectRegistnum(parmMap);
		} else {
			prodRegistnum = -1;
		}
		if(list.contains(识别标识合并_PAGEID)){
			mergenum = businessHomeMapper.selectMergenum(parmMap);
		} else {
			mergenum = -1;
		}
		//接口文件总数
		int accessFiletotal = 0;
		//数据检查总数
		int checkDatatotal = 0;
		//数据维护总数
		int processedDatatotal = 0;
		int chbEquHold = businessHomeMapper.selectChbEquHold();
		int chbInvest = businessHomeMapper.selectChbInvestTrade(searchDate);
		int chbEqudet = businessHomeMapper.selectChbEqudet(searchDate);
		businessHomeMapper.selectChbEquHold();
		for (BusinessHomeDto businessinfo : businessinfolist) {
			// 如果系统存在该接口
			if (businessinfo.getInterfFileType() != null && interflist.contains(businessinfo.getInterfFileType())) {
				for (BusinessHomeDto checkdata : checkdatalist) {
					if (businessinfo.getInterfFileType().equals(checkdata.getInterfFileType())) {
						// businessinfo.setDatanum(checkdata.getDatanum());
						// 检查通过c
						if(!INTERF_TYPE_EQU.contains(businessinfo.getInterfFileType())){
							businessinfo.setDatanum(checkdata.getCheckfailnum());
						}
						businessinfo.setCheckSuccessnum(businessinfo.getDatanum() - checkdata.getCheckfailnum());
						// 检查不通过
						businessinfo.setCheckfailnum(checkdata.getCheckfailnum());
						// 待处理
						businessinfo.setProcessednum(checkdata.getCheckfailnum());
					}
				}
				checkDatatotal = checkDatatotal + businessinfo.getDatanum();
				accessFiletotal = accessFiletotal + businessinfo.getAccessFilenum();
			}
		}
		for (BusinessHomeDto checkdata : checkdatalist) {
			if(checkdata.getInterfFileType() != null && interflist.contains(checkdata.getInterfFileType())){
				processedDatatotal = processedDatatotal + checkdata.getCheckfailnum();
			}
		}
		processedDatatotal = processedDatatotal+chbEquHold+chbInvest+chbEqudet;
		List<VbusinessHomeDto> vlist = dealVdtoAll(businessinfolist,interflist,chbEquHold,chbInvest,chbEqudet);
		VbusinessHomeDto divdto = new VbusinessHomeDto();
		divdto.setReportCode("TOTAL");
		divdto.setProdRegistnum(prodRegistnum);
		divdto.setMergenum(mergenum);
		divdto.setAccessFiletotal(accessFiletotal);
		divdto.setCheckDatatotal(checkDatatotal);
		divdto.setProcessedDatatotal(processedDatatotal);
		vlist.add(divdto);
		//add by  排序
		for(VbusinessHomeDto  dto1:vlist){
			if(StringUtil.equals("TOTAL", dto1.getReportCode())){
				continue;
			}
			else if(StringUtil.equals("投资者信息接口数据", dto1.getReportCode())){
				dto1.setOrderNo(1);;
			}
			else if(StringUtil.equals("投资者持有信息接口数据", dto1.getReportCode())){
				dto1.setOrderNo(2);;
			}
			else if(StringUtil.equals("投资者明细信息接口数据", dto1.getReportCode())){
				dto1.setOrderNo(3);;
			}
			else if(StringUtil.equals("产品申报登记信息", dto1.getReportCode())){
				dto1.setOrderNo(4);;
			}
			else if(StringUtil.equals("产品发行登记信息", dto1.getReportCode())){
				dto1.setOrderNo(5);;
			}
			else if(StringUtil.equals("产品募集总量登记信息", dto1.getReportCode())){
				dto1.setOrderNo(6);;
			}
			else if(StringUtil.equals("产品存续期登记信息", dto1.getReportCode())){
				dto1.setOrderNo(7);;
			}
			else if(StringUtil.equals("产品终止登记信息", dto1.getReportCode())){
				dto1.setOrderNo(8);;
			}
			else if(StringUtil.equals("资产要素登记信息", dto1.getReportCode())){
				dto1.setOrderNo(9);;
			}
			else if(StringUtil.equals("资产交易登记信息", dto1.getReportCode())){
				dto1.setOrderNo(10);;
			}
			else if(StringUtil.equals("底层资产持仓登记", dto1.getReportCode())){
				dto1.setOrderNo(11);;
			}
			else if(StringUtil.equals("资产估值登记信息", dto1.getReportCode())){
				dto1.setOrderNo(12);;
			}
		}
		List<VbusinessHomeDto>  resut= vlist.stream().sorted(Comparator.comparing(VbusinessHomeDto::getOrderNo))
				.collect(Collectors.toList());
		return resut;
	}

	public List<VbusinessHomeDto> dealVdto(List<BusinessHomeDto> list,List<String> interflist,int chbEquHold,int chbInvest,int chbEqudet){
		List<VbusinessHomeDto> vdtolist = new ArrayList<VbusinessHomeDto>();
		for (BusinessHomeDto homedto : list) {
			VbusinessHomeDto vdto = new VbusinessHomeDto();
			if (homedto.getInterfFileType() != null && interflist.contains(homedto.getInterfFileType())) {
				if(InterfDefine.投资者信息接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.投资者信息接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("td-debt-link-disabled",homedto.getCheckfailnum(),"EquityInfo001","REPORT_002004","svreport/submitmaintenance/equityinfo/EquityInfo001/init","6",InterfDefine.投资者信息接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",chbEquHold,"EquityInfo001","REPORT_001003001","svreport/submitmaintenance/equityinfo/EquityInfo001/init","7",InterfDefine.投资者信息接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.投资者持有信息接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.投资者持有信息接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("td-debt-link-disabled",homedto.getCheckfailnum(),"InvestorsHoldInfo001","REPORT_002004","svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001/init","6",InterfDefine.投资者持有信息接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",chbInvest,"InvestorsHoldInfo001","REPORT_001003004","svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001/init","7",InterfDefine.投资者持有信息接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.投资者交易信息接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.投资者交易信息接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.投资者交易信息接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.投资者交易信息接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.交易对手接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.交易对手接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_交易对手信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_交易对手信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_交易对手信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_交易对手信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.交易对手接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.交易对手接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.产品基本信息接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品基本信息接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_产品基本要素信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_产品基本要素信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_产品基本要素信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_产品基本要素信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品基本信息接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品基本信息接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.产品开放计划接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品开放计划接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_产品开放计划信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_产品开放计划信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_产品开放计划信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_产品开放计划信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品开放计划接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品开放计划接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.产品收益率净值接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品收益率净值接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_产品收益率净值信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_产品收益率净值信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_产品收益率净值信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_产品收益率净值信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品收益率净值接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品收益率净值接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.产品费用交易接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品费用交易接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_产品费用交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_产品费用交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_产品费用交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_产品费用交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品费用交易接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品费用交易接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.资产要素信息接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.资产要素信息接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_资产要素信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_资产要素信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_资产要素信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_资产要素信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.资产要素信息接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.资产要素信息接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.投资交易信息接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.投资交易信息接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.投资交易信息接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.投资交易信息接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.资管计划底层资产持仓接口数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.资管计划底层资产持仓接口数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_资管计划底层资产持仓信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_资管计划底层资产持仓信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_资管计划底层资产持仓信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_资管计划底层资产持仓信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.资管计划底层资产持仓接口数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.资管计划底层资产持仓接口数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.资产基础指标数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.资产基础指标数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_资产基础指标信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_资产基础指标信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_资产基础指标信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_资产基础指标信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.资产基础指标数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.资产基础指标数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.科目余额数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.科目余额数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_科目余额信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_科目余额信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_科目余额信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_科目余额信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.科目余额数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.科目余额数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.债券评级数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.债券评级数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_债券评级信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_债券评级信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_债券评级信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_债券评级信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.债券评级数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.债券评级数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.节假日数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.节假日数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_节假日信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_节假日信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_节假日信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_节假日信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.节假日数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.节假日数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.汇率信息数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.汇率信息数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_汇率行情信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_汇率行情信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_汇率行情信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_汇率行情信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.汇率信息数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.汇率信息数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.市场机构数据.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.市场机构数据.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_市场机构信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_市场机构信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_市场机构信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_市场机构信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.市场机构数据.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.市场机构数据.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
			}
			if(InterfDefine.投资者明细信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.投资者明细信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("td-debt-link-disabled",homedto.getCheckfailnum(),"SvrEquityBusinessDetails001","REPORT_002004","svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails001/init","6",InterfDefine.投资者明细信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",chbEqudet,"SvrEquityBusinessDetails001","REPORT_001003001","svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails001/init","7",InterfDefine.投资者明细信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
			if(InterfDefine.产品申报信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.产品申报信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_产品申报登记信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_产品申报登记信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_产品申报登记信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_产品申报登记信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品申报信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品申报信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
			if(InterfDefine.产品分红交易接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.产品分红交易接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_产品分红登记信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_产品分红登记信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_产品分红登记信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_产品分红登记信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品分红交易接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品分红交易接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
			if(InterfDefine.周期型产品余额接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.周期型产品余额接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_周期型产品余额.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_周期型产品余额.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_周期型产品余额.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_周期型产品余额.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.周期型产品余额接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.周期型产品余额接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}

			if(InterfDefine.监管区域客户组别接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.监管区域客户组别接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_监管区域客户组别信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_监管区域客户组别信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_监管区域客户组别信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_监管区域客户组别信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.监管区域客户组别接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.监管区域客户组别接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
			if(InterfDefine.总部或分支机构信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.总部或分支机构信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_总部或分支机构信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_总部或分支机构信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_总部或分支机构信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_总部或分支机构信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.总部或分支机构信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.总部或分支机构信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}

			if(InterfDefine.销售人员身份信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.销售人员身份信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_销售人员身份信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_销售人员身份信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_销售人员身份信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_销售人员身份信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.销售人员身份信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.销售人员身份信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
			if(InterfDefine.销售人员奖惩投诉记录信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.销售人员奖惩投诉记录信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_销售人员奖惩投诉记录信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_销售人员奖惩投诉记录信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_销售人员奖惩投诉记录信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_销售人员奖惩投诉记录信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.销售人员奖惩投诉记录信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.销售人员奖惩投诉记录信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}




		}
		return vdtolist;
	}
	/**
	 * 一体化展示
	 * @param list 展示数据
	 * @param interflist 需要展示的接口类型
	 * @param chbEquHold 投资者身份数据
	 * @param chbInvest  投资者持有数据
	 * @param chbEqudet  投资者明细数据
	 * @return
	 */
	public List<VbusinessHomeDto> dealVdtoAll(List<BusinessHomeDto> list,List<String> interflist,int chbEquHold,int chbInvest,int chbEqudet){
		List<VbusinessHomeDto> vdtolist = new ArrayList<VbusinessHomeDto>();
		for (BusinessHomeDto homedto : list) {
			VbusinessHomeDto vdto = new VbusinessHomeDto();
			if(InterfDefine.投资者信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.投资者信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者身份信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("td-debt-link-disabled",homedto.getCheckfailnum(),"EquityInfo001","REPORT_002004","svreport/submitmaintenance/equityinfo/EquityInfo001/init","6",InterfDefine.投资者信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",chbEquHold,"EquityInfo001","REPORT_001003001","svreport/submitmaintenance/equityinfo/EquityInfo001/init","7",InterfDefine.投资者信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
			if(InterfDefine.投资者持有信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.投资者持有信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者持有信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("td-debt-link-disabled",homedto.getCheckfailnum(),"InvestorsHoldInfo001","REPORT_002004","svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001/init","6",InterfDefine.投资者持有信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",chbInvest,"InvestorsHoldInfo001","REPORT_001003004","svreport/submitmaintenance/investorsholdinfo/InvestorsHoldInfo001/init","7",InterfDefine.投资者持有信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
			if (homedto.getInterfFileType() != null && interflist.contains(homedto.getInterfFileType())) {
				if(InterfDefine.产品申报登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品申报登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品申报登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品申报登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.产品发行登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品发行登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品发行登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品发行登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.产品募集总量登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品募集总量登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品募集总量登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品募集总量登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}

				if(InterfDefine. 产品存续期登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品存续期登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品存续期登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品存续期登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.产品终止登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.产品终止登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.产品终止登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.产品终止登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}

				if(InterfDefine.资产要素登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.资产要素登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.资产要素登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.资产要素登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.资产交易登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.资产交易登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.资产交易登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.资产交易登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}

				if(InterfDefine.底层资产持仓登记.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.底层资产持仓登记.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.底层资产持仓登记.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.底层资产持仓登记.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
				if(InterfDefine.资产估值登记信息.getCode().equals(homedto.getInterfFileType())){
					vdto.setReportCode(InterfDefine.资产估值登记信息.getName());
					List<HomeCellDto> cells = new ArrayList<>();
					HomeCellDto cell1= dealCellDto("td-debt-link-disabled",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell1);
					HomeCellDto cell2= dealCellDto("td-debt-link-disabled",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell2);
					HomeCellDto cell3= dealCellDto("td-debt-link-disabled",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell3);
					HomeCellDto cell4= dealCellDto("td-debt-link-disabled",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者交易信息.getValue(),"1");
					cells.add(cell4);
					HomeCellDto cell5= dealCellDto("td-debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
					cells.add(cell5);
					HomeCellDto cell6= dealCellDto("debt-info-link",homedto.getCheckfailnum(),"ErrorMessage001","REPORT_002004","chb/errormessage/ErrorMessage001/init","6",InterfDefine.资产估值登记信息.getCode(),"1");
					cells.add(cell6);
					HomeCellDto cell7= dealCellDto("td-debt-link-disabled",homedto.getProcessednum(),"SvrOperationalInfo001","REPORT_001005","common/infomanagement/SvrOperationalInfo001/init","7",InterfDefine.资产估值登记信息.getCode(),"1");
					cells.add(cell7);
					vdto.setCells(cells);
					vdtolist.add(vdto);
				}
			}

			if(InterfDefine.投资者明细信息接口数据.getCode().equals(homedto.getInterfFileType())){
				vdto.setReportCode(InterfDefine.投资者明细信息接口数据.getName());
				List<HomeCellDto> cells = new ArrayList<>();
				HomeCellDto cell1= dealCellDto("debt-info-link",homedto.getAccessFilenum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","1",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell1);
				HomeCellDto cell2= dealCellDto("debt-info-link",homedto.getExceptionnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","2",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell2);
				HomeCellDto cell3= dealCellDto("debt-info-link",homedto.getNormalnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","3",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell3);
				HomeCellDto cell4= dealCellDto("debt-info-link",homedto.getDatanum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","4",DictCodeListExt2.上游接口文件类型_投资者明细信息.getValue(),"1");
				cells.add(cell4);
				HomeCellDto cell5= dealCellDto("debt-link-disabled",homedto.getCheckSuccessnum(),"ReportUpInterfMonitor001","REPORT_004007","svreport/system/upinterfmonitor/ReportUpInterfMonitor001/init","5","1","1");
				cells.add(cell5);
				HomeCellDto cell6= dealCellDto("td-debt-link-disabled",homedto.getCheckfailnum(),"SvrEquityBusinessDetails001","REPORT_002004","svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails001/init","6",InterfDefine.投资者明细信息接口数据.getCode(),"1");
				cells.add(cell6);
				HomeCellDto cell7= dealCellDto("debt-info-link",chbEqudet,"SvrEquityBusinessDetails001","REPORT_001003001","svreport/submitmaintenance/businessdetailed/SvrEquityBusinessDetails001/init","7",InterfDefine.投资者明细信息接口数据.getCode(),"1");
				cells.add(cell7);
				vdto.setCells(cells);
				vdtolist.add(vdto);
			}
		}
		return vdtolist;
	}
	private HomeCellDto dealCellDto(String cellClass,int cellText, String cellPageid, String layId, String dataType,
									String dataSubtype, String dataNum, String dataRepCode) {
		HomeCellDto dto = new HomeCellDto();
		dto.setCellClass(cellClass);
		dto.setCellText(cellText+"");
		dto.setCellPageid(cellPageid);
		dto.setLayId(layId);
		dto.setDataType(dataType);
		dto.setDataSubtype(dataSubtype);
		dto.setDataNum(dataNum);
		dto.setDataRepCode(dataRepCode);
		return dto;
	}

	@Transactional
	@DataSource(DataSourceType.OTHER)
	public List<String> selectMenulist() {
		List<String> menulist = Stream.of(产品登记编码_PAGEID,识别标识合并_PAGEID).collect(toList());
		return businessHomeMapper.selectMenulist(menulist);
	}
}
