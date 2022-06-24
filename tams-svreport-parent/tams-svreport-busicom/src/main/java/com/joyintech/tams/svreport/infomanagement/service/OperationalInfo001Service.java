/*
 * 文件名：OperationalInfo001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：
 * 修改时间：2019年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */
package com.joyintech.tams.svreport.infomanagement.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.intf.DataDict;
import com.joyintech.framework.base.intf.DataDictItem;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.FilterInformationDetailMapper;
import com.joyintech.tams.common.base.dao.FilterInformationMapper;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckRule;
import com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib;
import com.joyintech.tams.common.svreport.datacheck.service.DataCheckService;
import com.joyintech.tams.constant.*;
import com.joyintech.tams.report.common.dao.BaseInformationMapper;
import com.joyintech.tams.report.common.dao.ChbInfoMgrResultConfigMapper;
import com.joyintech.tams.report.common.dao.ChbMgrTableInfoMapper;
import com.joyintech.tams.report.common.dao.ColumnTypeMapper;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.systemtools.dao.TamsBaseMapper;
import com.joyintech.tams.systemtools.dto.SysDataDict;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;



/**
 * @Description 业务数据维护业务逻辑层
 * @author gaojian
 * @version 1.7 2019年5月8日
 * @since 1.7
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperationalInfo001Service extends CommonExportService<FilterInformationDto>{
	/**
	 * 日志管理
	 */
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// excel表格最大列数
	private static final int MAX_SHEET_ROWS = 1 << 20;
	
	// 错误信息表名
	private static final String CHB_INTERF_ERRODATA = "CHB_INTERF_ERRODATA";

	// 资产要素表名
	private static final List<String> CHB_ASSET_INFO_LIST = Arrays.asList("CHB_ASSET_INFO", "CHB_ASSET_INFO_TMP");
	//业务数据维护表
	private static final List<String> CHB_INFO_LIST = Arrays.asList("CHB_PRD_BASEINFO","CHB_INVEST_TRADE");

	// 资产要素字段数据临界值（该字段为备注所在的索引值）
	private static final Integer CAI_BASE_COUNT = 282;
	
	@Autowired
	private CommonCodeCheckBatchService commonCodeCheckBatchService;

	@Autowired
	private BaseInformationMapper baseInformationMapper;

	@Autowired
	private FilterInformationMapper filterInformationMapper;

	@Autowired
	private FilterInformationDetailMapper filterInformationDetailMapper;

	@Autowired
	private ColumnTypeMapper columnTypeMapper;
	
	@Autowired
    private DataCheckService dataCheckService;
	
	@Autowired
	private SvrReportGenetorService svrReportGenetorService;
	
	@Autowired
	private ChbInfoMgrResultConfigMapper chbInfoMgrResultConfigMapper;

	@Autowired
	private TamsBaseMapper tamsBaseMapper;
	
	@Autowired
	private ChbMgrTableInfoMapper chbMgrTableInfoMapper;
	
	@Autowired
	private OperationalLoanInfo001CheckService operationalLoanInfo001CheckService;
	// 需要存入数据来源的接口 
	private static final String[] INTERF_DATASOURCE = new String[] {"CHB_ASSET_INFO","CHB_BOT_AST_POS"};
	
	private static final List<String> CHB_PRD_LIST = Arrays.asList("CHB_PRD_ISSUE_SUB_TMP","CHB_PRD_RAISETOT_SUB_TMP","CHB_PRD_DEAL_SUB_TMP"
			,"CHB_PRD_END_SUB_TMP","CHB_INVEST_TRADE_SUB_TMP");
	private static final String[] INTERF_PRDDATA = new String[] {"CHB_PRD_ISSUE_SUB_TMP","CHB_PRD_RAISETOT_SUB_TMP","CHB_PRD_DEAL_SUB_TMP"
			,"CHB_PRD_END_SUB_TMP","CHB_INVEST_TRADE_SUB_TMP"};

	//投资者交易
	private static final String CHB_PRD_DEAL = "CHB_PRD_DEAL";

	//贷款明细维护
	private static final String CHB_LOAN_DETAIL_INFO = "CHB_LOAN_DETAIL_INFO";
	
	private static final String CHB_PRD_DECLARE_SUB_TMP = "CHB_PRD_DECLARE_SUB_TMP";
	private static final String CHB_EMPLOYEE_INFO = "CHB_EMPLOYEE_INFO";
	
	// 资产要素表表头合并单元格数据key(起始下标-结束下标) value(存值)
	private static final Map<String, String> headerMap = new HashMap<String, String>();

	static {
		headerMap.put("2-6", "00:公共信息（必填）");
		headerMap.put("7-16", "01:本行/他行存款||大额存单");
		headerMap.put("17-27", "03:债券类资产/理财直接融资工具/同业存单");
		headerMap.put("28-35", "02:拆放同业及债券买入返售/同业拆入及卖出回购");
		headerMap.put("36-102", "04:非标准化债权类资产/新增可投资资产");
		headerMap.put("103-116", "05:票据类/信用证类资产");
		headerMap.put("117-127", "06:权益类资产");
		headerMap.put("128-131", "07:金融衍生品");
		headerMap.put("132-145", "08:QDII债券资产");
		headerMap.put("146-151", "09:QDII拆出/逆回购资产");
		headerMap.put("152-156", "10:QDII股票/基金类资产");
		headerMap.put("157-173", "11:QDII结构性票据类资产");
		headerMap.put("174-198", "12:资产管理产品");
		headerMap.put("199-203", "13:贵金属/商品");
		headerMap.put("204-233", "14:非标准化债权类/权益类/代客境外理财投资QDII/新增可投资资产");
		headerMap.put("234-239", "18:另类资产");
		headerMap.put("240-255", "15:公募基金/私募基金");
		headerMap.put("256-275", "16:委外投资——协议方式");
		headerMap.put("276-281", "17:其他（负债）类资产");
	}

	private static final Map<String, String> nameMap = new HashMap<String, String>();
	static{
		nameMap.put("本行_他行存款_大额存单","01");
		nameMap.put("拆放同业买入返售（资产）_同业拆入卖出回购（负债）","02");
		nameMap.put("债券类资产_理财直接融资工具_同业存单","03");
		nameMap.put("非标准化债权类资产_信贷资产流转项目","04");
		nameMap.put("票据类资产_信用证类资产","05");
		nameMap.put("权益类资产","06");
		nameMap.put("金融衍生品","07");
		nameMap.put("QDII债券资产","08");
		nameMap.put("QDII拆出_逆回购资产","09");
		nameMap.put("QDII股票_基金类资产","10");
		nameMap.put("QDII结构性票据类资产","11");
		nameMap.put("资产管理产品","12");
		nameMap.put("商品类资产","13");
		nameMap.put("各类资产的其他项","14");
		nameMap.put("公募基金_私募基金","15");
		nameMap.put("委外投资-协议方式","16");
		nameMap.put("其他（负债）类资产","17");
		nameMap.put("另类资产","18");
		//nameMap.put("私募基金","19");
	}
	
	private static final Map<String,String> assetMap=new HashMap<String,String>();
	static{
		assetMap.put("1002","01:7-16");
		assetMap.put("1003","01:7-16");
		assetMap.put("1004","01:7-16");
		assetMap.put("1005","01:7-16");
		assetMap.put("1107","02:28-35");
		assetMap.put("1108","02:28-35");
		assetMap.put("1901","02:28-35");
		assetMap.put("1902","02:28-35");
		assetMap.put("1101","03:17-27");
		assetMap.put("1110","03:17-27");
		assetMap.put("1109","03:17-27");
		assetMap.put("1102","03:17-27");
		assetMap.put("1111","03:17-27");
		assetMap.put("1112","03:17-27");
		assetMap.put("1113","03:17-27");
		assetMap.put("1114","03:17-27");
		assetMap.put("1115","03:17-27");
		assetMap.put("1116","03:17-27");
		assetMap.put("1117","03:17-27");
		assetMap.put("2101","03:17-27");
		assetMap.put("2202","04:36-102");
		assetMap.put("2501","03:17-27");
		assetMap.put("2502","03:17-27");
		assetMap.put("1202","04:36-102");
		assetMap.put("1203","04:36-102");
		assetMap.put("1204","04:36-102");
		assetMap.put("1205","04:36-102");
		assetMap.put("1206","04:36-102");
		assetMap.put("1207","04:36-102");
		assetMap.put("1208","04:36-102");
		assetMap.put("1209","04:36-102");
		assetMap.put("1211","04:36-102");
		assetMap.put("1212","04:36-102");
		assetMap.put("1213","04:36-102");
		assetMap.put("2201","04:36-102");
		assetMap.put("1201","05:103-116");
		assetMap.put("1210","05:103-116");
		assetMap.put("1301","06:117-127");
		assetMap.put("1305","06:117-127");
		assetMap.put("1302","06:117-127");
		assetMap.put("1401","07:128-131");
		assetMap.put("1402","07:128-131");
		assetMap.put("1403","07:128-131");
		assetMap.put("1404","07:128-131");
		assetMap.put("1405","07:128-131");
		assetMap.put("1406","07:128-131");
		assetMap.put("1499","07:128-131");
		assetMap.put("1501","08:132-145");
		assetMap.put("1504","09:146-151");
		assetMap.put("1506","09:146-151");
		assetMap.put("1502","10:152-156");
		assetMap.put("1505","10:152-156");
		assetMap.put("1507","10:152-156");
		assetMap.put("1508","10:152-156");
		assetMap.put("1509","10:152-156");
		assetMap.put("1503","11:157-173");
		assetMap.put("1601","13:199-203");
		assetMap.put("1602","13:199-203");
		assetMap.put("1699","13:199-203");
		assetMap.put("1701","12:174-198");
		assetMap.put("1702","12:174-198");
		assetMap.put("1703","12:174-198");
		assetMap.put("1704","12:174-198");
		assetMap.put("1705","12:174-198");
		assetMap.put("1706","12:174-198");
		assetMap.put("1299","14:204-233");
		assetMap.put("1399","14:204-233");
		assetMap.put("1599","14:204-233");
		assetMap.put("2299","14:204-233");
		assetMap.put("2301","18:234-239");
		assetMap.put("2302","18:234-239");
		assetMap.put("2303","18:234-239");
		assetMap.put("2304","18:234-239");
		assetMap.put("2305","18:234-239");
		assetMap.put("1603","18:234-239");
		assetMap.put("1106","15:240-255");
		assetMap.put("2401","15:240-255");
		assetMap.put("1303","15:240-255");
		assetMap.put("2403","15:240-255");
		assetMap.put("2402","15:240-255");
		assetMap.put("2499","15:240-255");
		assetMap.put("2601","19:240-255");
		assetMap.put("2602","19:240-255");
		assetMap.put("2604","19:240-255");
		assetMap.put("2605","19:240-255");
		assetMap.put("2606","19:240-255");
		assetMap.put("2607","19:240-255");
		assetMap.put("2610","19:240-255");
		assetMap.put("2611","19:240-255");
		assetMap.put("2612","19:240-255");
		assetMap.put("2617","19:240-255");
		assetMap.put("2613","19:240-255");
		assetMap.put("2614","19:240-255");
		assetMap.put("2616","19:240-255");
		assetMap.put("2618","19:240-255");
		assetMap.put("2619","19:240-255");
		assetMap.put("2620","19:240-255");
		assetMap.put("2699","19:240-255");
		assetMap.put("2801","16:256-275");
		assetMap.put("2802","16:256-275");
		assetMap.put("2803","16:256-275");
		assetMap.put("2804","16:256-275");
		assetMap.put("2805","16:256-275");
		assetMap.put("2806","16:256-275");
		assetMap.put("2899","16:256-275");
		assetMap.put("2000","17:276-281");
	}
	
	
	
	
	/**
	 * 获取所有基础表信息
	 * 
	 * @return 所有基础表信息
	 */
	public List<BaseInformationDto> queryAllTableInfo() {
		List<BaseInformationDto> baseInfomationList = new ArrayList<BaseInformationDto>();
		baseInfomationList = baseInformationMapper.queryAllTableInfo();
		return baseInfomationList;
	}
	
	/**
	* @Title: queryAllBaseInformation
	* @Description: 查询所有待维护表查询条件集合
	* @param @return    参数
	* @return List<BaseInformationDto> 查询条件集合
	* @throws
	*/
	    
	public List<BaseInformationDto> queryAllBaseInformation() {
		List<BaseInformationDto> baseInfomationList = new ArrayList<BaseInformationDto>();
		baseInfomationList = baseInformationMapper.queryAllBaseInformation();
		return baseInfomationList;
	}

	/**
	 * 根据基础表代码获取字段代码和名字
	 * 
	 * @param tableId 基础表代码
	 * @return 字段代码和名字组成的Json字符串
	 */
	public String queryColumnByTableId(String tableId) {
		List<BaseInformationDto> baseInfomationList = new ArrayList<BaseInformationDto>();
		Map<String, String> data = new HashMap<String, String>();
		baseInfomationList = baseInformationMapper.queryColumnByTableId(tableId);
		baseInfomationList.forEach(info -> {
			data.put(info.getBaseColumnId(), info.getBaseColumnName());
		});
		return JSON.toJSONString(data);
	}
	
	public String getTableNameByTableId(String tableId){
		String tableName=chbInfoMgrResultConfigMapper.getTableNameByTableId(tableId);
		return tableName;
	}
	

	/**
	 * 根据基础表代码获取字段代码和类型
	 * 
	 * @param tableId 基础表代码
	 * @return 字段代码和类型组成的Json字符串
	 */
	public String queryColumnTypeByTableId(String tableId) {
		List<BaseInformationDto> baseInfomationList = new ArrayList<BaseInformationDto>();
		Map<String, String> data = new HashMap<String, String>();
		baseInfomationList = baseInformationMapper.queryColumnByTableId(tableId);
		baseInfomationList.forEach(info -> {
			data.put(info.getBaseColumnId() + "," + info.getBaseColumnName(), String.valueOf(info.getBaseColumnType()));
		});
		return JSON.toJSONString(data);
	}

	/**
	 * 根据前台传入的条件动态查询结果
	 * 
	 * @param param 前台条件组成的map
	 * @return 动态结果
	 */
	@SuppressWarnings("unchecked")
	public String queryData(Map<String, Object> param) {
		int page = Integer.valueOf((String) param.get("page"));
		int limit = Integer.valueOf((String) param.get("limit"));
		int beginIndex = (page - 1) * limit;
		int endIndex = page * limit;
		String tableId = (String) param.get("tableId");
		//获取表名
		ChbMgrTableInfo  chbMgrTableInfo=chbMgrTableInfoMapper.getTableInfobyTableId(tableId);
		
		String tableName=chbMgrTableInfo.getMgrTableName();
		String mgrTableChiName=chbMgrTableInfo.getMgrTableChiName();
		String headerNum=chbMgrTableInfo.getHeaderNum();
		
		if(CHB_ASSET_INFO_LIST.contains(tableName)&&StringUtil.equals("1", headerNum)){
			//是资产基本信息并且是配置的单表头
			//DictCodeListExt2 dictCodeListExt2=DictCodeListExt2.from(DictCode.资产_负债类别, mgrTableChiName);
			//String assetType=SysDataUtil.getCodeFromDataDictByName(DictCode.资产_负债类别, mgrTableChiName);
			String assetType=nameMap.get(mgrTableChiName);
			param.put("assetType", assetType);
		}
		
		param.put("tableName", tableName);
		
		List<String> dateColumnList = new ArrayList<String>();
		List<String> timeColumnList = new ArrayList<String>();
		dateColumnList = baseInformationMapper.queryDateColumnList(tableName);
		timeColumnList = baseInformationMapper.queryTimeColumnList(tableName);
		param.put("flag", false);
		List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(param);
		// 创建Map, key值为字段名称，value为数据字典
		Map<String, String> resultMap = new HashMap<String, String>(16);
		for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
			resultMap.put(chbInfoMgrResultConfig.getColumnId(), chbInfoMgrResultConfig.getResultdictCode());
		}
		List<Object> list = queryDataList(param);
		int count = list.size();
		if (endIndex > count) {
			endIndex = count;
		}
		list = list.subList(beginIndex, endIndex);
		List<Object> mapList = new ArrayList<Object>();
		for (Object obj : list) {
			Map<String, Object> map = (Map<String, Object>) obj;
			Map<String, Object> newMap = new HashMap<String, Object>();
			//公募基金与私募基金asset_type在excel中定义的type都为15，故此处做转换 start 2020-11-03
			String assetType = map.get("ASSET_TYPE") == null ? "" : map.get("ASSET_TYPE").toString();
			if(StringUtil.equals("19", assetType)){
				assetType = "15";
			}
			//公募基金与私募基金asset_type在excel中定义的type都为15，故此处做转换 end 2020-11-03
			// 资产要素表特殊处理
			if (CHB_ASSET_INFO_LIST.contains(tableName)) {
				for (String str : map.keySet()) {
					newMap.put(str, map.get(str));
					newMap.put(str + assetType, map.get(str));
				}
			} else {
				newMap = map;
			}

			for (String str : newMap.keySet()) {
				String realStr = "";
				if (CHB_ASSET_INFO_LIST.contains(tableName)) {
				//代码合并删除此行代码
				//	String assetType = map.get("ASSET_TYPE") == null ? "" : map.get("ASSET_TYPE").toString();
					if (str.endsWith(assetType)) {
						realStr = str.replace(assetType, "");
					}
				}
				// 查询结果为日期类型字段处理
				if (dateColumnList.contains(str) || dateColumnList.contains(realStr)) {
					Object dateObj = newMap.get(str);
					if (null != dateObj) {
						newMap.put(str, DateUtil.format((Date) dateObj));
					}
					
				}
				// 查询结果为时间类型字段处理
				if (timeColumnList.contains(str) || timeColumnList.contains(realStr)) {
					Object dateObj = newMap.get(str);
					if (null != dateObj) {
						newMap.put(str, dateObj.toString());
					}
				}
			if(StringUtil.equals("CHB_PRD_DECLARE_SUB_TMP",tableName)){
					if (StringUtil.equals("PRD_SPEC_PROPERTY",str)){
						if (StringUtil.isNotEmpty(map.get(str))){
							if (map.get(str).toString().contains(GlobalConstant.SEMICOLON)){
								List<String> targetTypeList = StringUtil.splitToList(GlobalConstant.SEMICOLON, map.get(str).toString());
								StringBuffer sb = new StringBuffer();
								for(String targetType : targetTypeList) {
									if (targetType.split(" ")[0].length() > 0){
										sb.append(";");
										SvrDataUtil.getNameFromDataDict(DictCode.产品特殊属性,targetType.split(" ")[0]);
										sb.append(targetType.split(" ")[0]+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品特殊属性,targetType.split(" ")[0]));
									}
								}
								if (sb.length() > 0){
									//map.get(str).toString() = sb.substring(1, sb.length());
									map.put(str,sb.substring(1, sb.length()));
								}
							}else{
								if (StringUtil.isNotEmpty(map.get(str))){
									map.put(str,map.get(str).toString().split(" ")[0]+" "+SvrDataUtil.getNameFromDataDict(DictCode.产品特殊属性,map.get(str).toString().split(" ")[0]));
								}
							}
						}
					}
				}
				// 查询结果为枚举类型字段处理
				String resultDictCode = resultMap.get(str);
				if (StringUtil.isNotEmpty(resultDictCode) && null != newMap.get(str) ) {
					String dateObj = newMap.get(str).toString();
					String value = queryValueByDictAndKeyCache(resultDictCode, dateObj);
					newMap.put(str, value);
				}
				//Bigdecimal 转换为string 解决当数据过长被前台截取问题修复 start 20201026
				if(StringUtil.isNotEmpty(newMap.get(str))){
					Object dateObj = newMap.get(str);
					if(dateObj instanceof BigDecimal){
						newMap.put(str, dateObj.toString());
					}
				}

				if ((StringUtil.equals("UPDATE_TIME",str)||StringUtil.equals("CREATE_TIME",str))&&StringUtil.isNotEmpty(newMap.get(str))&&!"CHB_EMPLOYEE_INFO".equals(tableName)){
					try{
						newMap.put(str, com.joyintech.framework.util.DateUtil.format((Date)newMap.get(str),"yyyy-MM-dd hh:mm:ss"));
					}catch (Exception e){
						logger.error("日期转换失败！");
					}
				}
				//Bigdecimal 转换为string 解决当数据过长被前台截取问题修复 end 20201026
			}
			mapList.add(newMap);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		JSONArray array = new JSONArray(mapList);
		data.put("code", 0);
		data.put("msg", "");
		data.put("count", count);
		data.put("data", array);
		return JSON.toJSONString(data);
	}

	/**
	 * 根据页面参数查询结果
	 * @param param 页面参数
	 * @return 指定表数据集合
	 */
	private List<Object> queryDataList(Map<String, Object> param) {
		List<Object> list = new ArrayList<Object>();
		//String tableId = (String) param.get("tableId");
		String tableName=(String) param.get("tableName");
		String assetType=(String) param.get("assetType");
		String columnIds = (String) param.get("columnIds");
		if (null == columnIds) {
			columnIds = "";
		}
		List<BaseInformationDto> baseInformationList = new ArrayList<BaseInformationDto>();
		// 拆分字段参数
		String[] columnDatas = columnIds.split(GlobalConstant.SEMICOLON);
		for (String columnData : columnDatas) {
			String[] columns = columnData.split("\\+");
			// 如果选择的字段不带参数,就不需要设置该字段查询条件
			if (columns.length < 3) {
				continue;
			}
			switch (columns[1]) {
			// 文本类型
			case "T":
				BaseInformationDto baseInfo1 = new BaseInformationDto();
				baseInfo1.setBaseColumnId(columns[0]);
				baseInfo1.setBaseColumnType("T");
				baseInfo1.setColumnText(columns[2]);
				baseInformationList.add(baseInfo1);
				break;
			// 多选框类型
			case "S":
				BaseInformationDto baseInfo2 = new BaseInformationDto();
				baseInfo2.setBaseColumnId(columns[0]);
				baseInfo2.setBaseColumnType("S");
				baseInfo2.setColumnSeq(StringUtil.splitToList(GlobalConstant.COMMA, columns[2]));
				baseInformationList.add(baseInfo2);
				break;
			// 日期类型
			case "D":
				BaseInformationDto baseInfo3 = new BaseInformationDto();
				baseInfo3.setBaseColumnId(columns[0]);
				baseInfo3.setBaseColumnType("D");
				String[] dateList = columns[2].split(GlobalConstant.COMMA);
				baseInfo3.setStartDate(dateList[0]);
				baseInfo3.setEndDate(dateList[1]);
				baseInformationList.add(baseInfo3);
				break;
			default:
			}
		}
		boolean flag = isHaveStatus(tableName);
		if(MultilevelDataAuthorityUtil.isEnabled() && StringUtil.equals(tableName,"CHB_LOAN_DETAIL_INFO")){
			list = baseInformationMapper.queryChbLoanDetailInfoByParams(tableName, baseInformationList, flag,assetType);
		}else{
		    //展示删除数据
		//	if(StringUtil.equals(tableName,"CHB_LOAN_DETAIL_INFO")){
				flag = false;//删除数据查询无数据
			//}
			list = baseInformationMapper.queryBaseInfoByParams(tableName, baseInformationList, flag,assetType);

			//----------- add by lingxq on 2021-06-25 for 错误数据只允许导出基础数据----->>>>>
			if("CHB_INTERF_ERRODATA".equals(tableName)) {
				list = list.stream().filter(x -> (!StringUtil.endsWith((String) ((Map) x).get("INTERF_TYPE"), "SUB"))).collect(Collectors.toList());
			}
			//----------- add by lingxq on 2021-06-25 for 错误数据只允许导出基础数据-----<<<<<<


		}
		return list;
	}

	/**
	 * 查询出基础信息表按表名分组的所有信息
	 * 
	 * @return 基础信息表按表名分组的所有信息
	 */
	public Map<String, List<BaseInformationDto>> queryAllBaseInfo() {
		int capacity = 1 << 5;
		Map<String, List<BaseInformationDto>> mapList = new HashMap<String, List<BaseInformationDto>>(capacity);
		List<BaseInformationDto> baseInformationList = baseInformationMapper.queryAllBaseInformation();
		// 获取所有表名集合
		List<String> tableNameList = new ArrayList<String>();
		for (BaseInformationDto baseInformationDto : baseInformationList) {
			if (!tableNameList.contains(baseInformationDto.getBaseTableName())) {
				tableNameList.add(baseInformationDto.getBaseTableName());
			}
		}
		// 按表名分组存入Map
		for (String tableName : tableNameList) {
			List<BaseInformationDto> baseInformations = baseInformationList.stream()
					.filter(baseInfo -> tableName.equals(baseInfo.getBaseTableName())).collect(Collectors.toList());
			mapList.put(tableName, baseInformations);
		}
		return mapList;
	}

	/**
	 * 查询多选框参数
	 * 
	 * @param param 表名和字段名
	 * @return 多选框参数
	 */
	public String queryColumnInfoList(Map<String, Object> param) {
		String tableId = (String) param.get("tableId");
		String columnId = (String) param.get("columnId");
		Map<String, String> data = new HashMap<String, String>();
		String dictCode = baseInformationMapper.queryDictCode(tableId, columnId);
		DictCode reportDictCode = DictCode.get(dictCode);
		List<SelectOptionDto> selectOptionDtoList = new ArrayList<SelectOptionDto>();
		selectOptionDtoList = DictCodeList.fromDictCode(reportDictCode);
		if (null == selectOptionDtoList || selectOptionDtoList.size() < 1) {
			selectOptionDtoList = DictCodeListExt1.fromDictCode(reportDictCode);
		}
		if (null == selectOptionDtoList || selectOptionDtoList.size() < 1) {
			selectOptionDtoList = DictCodeListExt2.fromDictCode(reportDictCode);
		}
		selectOptionDtoList.forEach(selectOptionDto -> {
			data.put(selectOptionDto.getId(), selectOptionDto.getText());
		});
		return JSON.toJSONString(data);
	}

	/**
	 * 查询多选框参数
	 *
	 * @param param 表名和字段名
	 * @return 多选框参数
	 */
	public String queryColumnInfoListCache(Map<String, Object> param) {
		String tableId = (String) param.get("tableId");
		String columnId = (String) param.get("columnId");
		Map<String, String> data = new HashMap<String, String>();
		String dictCode = baseInformationMapper.queryDictCode(tableId, columnId);
		if (StringUtil.isNotEmpty(dictCode)){
			DataDict reportDataDict = CacheUtil.getDictCode(dictCode);
			if (reportDataDict != null && !StringUtil.isEmptyList(reportDataDict.getItems())){
				List<DataDictItem> dictItemList = (List<DataDictItem>) reportDataDict.getItems();
				dictItemList.forEach(selectOptionDto -> {
					data.put(selectOptionDto.getItemCode(), selectOptionDto.getItemValue());
				});
			}
		}
		return JSON.toJSONString(data);
	}

	/**
	 * 获取字段信息
	 * 
	 * @param param 页面传入信息
	 * @return 字段信息
	 */
	public String queryColmnInfo(Map<String, Object> param) {
		List<ChbInfoMgrResultConfig> list = new ArrayList<ChbInfoMgrResultConfig>();
		String tableId = (String) param.get("tableId");
		Map<String, String> data = new LinkedHashMap<String, String>();
		Map<String, String> resultData = new LinkedHashMap<String, String>();
		param.put("flag", true);
		String headerNum="";
		String tableName="";
		if (null !=  tableId && !("").equals(tableId)) {
			list = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(param);
			ChbMgrTableInfo  chbMgrTableInfo=chbMgrTableInfoMapper.getTableInfobyTableId(tableId);
			
			tableName=chbMgrTableInfo.getMgrTableName();
			headerNum=chbMgrTableInfo.getHeaderNum();
			
			if(!StringUtil.isNotEmpty(headerNum)){
				headerNum="0";
			}
		}
		

		int size = list.size();
		for (int i = 0; i < size; i ++) {
			ChbInfoMgrResultConfig info = list.get(i);
			data.put(info.getColumnId(),
					(info.getColumnName() == null || "".equals(info.getColumnName())) ? info.getColumnId()
							: info.getColumnName());
		}
		//return JSON.toJSONString(data);
		resultData.put("tableName", tableName);
		resultData.put("headerNum", headerNum);
		resultData.put("data", JSON.toJSONString(data));
		//针对单独维护页面，无需tableName,headerNum 故增加判断 add by wm 2020-06-29 start
		if(StringUtil.isNotEmpty(param.get("isSingleEdit"))&&StringUtil.isNotEmpty(tableId)){
			return JSON.toJSONString(data);
		}
		//针对单独维护页面，无需tableName,headerNum 故增加判断 add by wm 2020-06-29 end
		return JSON.toJSONString(resultData);
		
//		if("CHB_ASSET_INFO".equals(tableName)){
//			//List<Map<String, Integer>> title1StrList = new ArrayList<Map<String, Integer>>();
//			//List<Map<String, String>> title2StrList = new ArrayList<Map<String, String>>();
//			
//			Map<String,Integer>title1Map=new HashMap<String,Integer>();
//			Map<String,String>title2Map=new HashMap<String,String>();
//			
//			int[] titleList = new int[titleMap.size()];
//			Arrays.fill(titleList, 0);
//			
//			for(int i = 0; i < size; i ++){
//				ChbInfoMgrResultConfig info = list.get(i);
//				String columnName=info.getColumnName() == null || "".equals(info.getColumnName()) ? info.getColumnId()
//						: info.getColumnName();
//				data.put(info.getColumnId(),columnName);
//				
//				title2Map.put(info.getColumnId(),columnName);
//								
//				String titleName=mappingMap.get(info.getColumnId());
//				String indexStr=titleMap.get(titleName);
//				if(StringUtil.isNotEmpty(indexStr)){
//					int index=Integer.parseInt(indexStr);
//					
//					titleList[index]++;
//				}
//
//			}
//			for (String key : titleMap.keySet()) {
//				//找出一级表头
//				Integer index=Integer.parseInt(titleMap.get(key));
//				if(titleList[index]>0){
//					title1Map.put(key, titleList[index]);
//				}
//				
//			}
//			resultData.put("title1", JSON.toJSONString(title1Map));
//			resultData.put("title2", JSON.toJSONString(title2Map));
//			return JSON.toJSONString(resultData);
//		}else{
//			for (int i = 0; i < size; i ++) {
//				ChbInfoMgrResultConfig info = list.get(i);
//				data.put(info.getColumnId(),
//						(info.getColumnName() == null || "".equals(info.getColumnName())) ? info.getColumnId()
//								: info.getColumnName());
//			}
//			return JSON.toJSONString(data);
//		}

	}
	
	
	
	/**
	 * 查询方案名是否重复
	 * 
	 * @param param 页面数据
	 * @return 方案名是否重复
	 */
	public boolean checkSave(Map<String, Object> param) {
		String userId = (String) param.get("userId");
		String planName = (String) param.get("planName");
		boolean isExists = false;
		int count = filterInformationMapper.checkSave(userId, planName);
		if (count >= 1) {
			isExists = true;
		}
		return isExists;
	}

	/**
	 * 保存方案
	 * 
	 * @param param 页面参数
	 * @return 是否保存成功
	 */
	public boolean savePlan(Map<String, Object> param) {
		int count = 0;
		//String userId = CommonUtil.getUid();
		String userId=(String) param.get("userId");
		String planName = (String) param.get("planName");
		String tableId = (String) param.get("tableId");
		@SuppressWarnings("unchecked")
		List<String> columns = (List<String>) param.get("columns");
		List<String> baseInfoIds = baseInformationMapper.queryBaseInfoIdList(tableId, columns);
		FilterInformationDto filterInformationDto = new FilterInformationDto();
		String filterId = UUID.randomUUID().toString();
		filterInformationDto.setFilterId(filterId);
		filterInformationDto.setFilterUserId(userId);
		filterInformationDto.setFilterName(planName);
		count += filterInformationMapper.insertFilterInfo(filterInformationDto);
		baseInfoIds.forEach(baseInfoId -> {
			FilterInformationDetailDto filterInformationDetailDto = new FilterInformationDetailDto();
			String filterInfoDetailId = UUID.randomUUID().toString();
			filterInformationDetailDto.setFilterDetailId(filterInfoDetailId);
			filterInformationDetailDto.setFilterId(filterId);
			filterInformationDetailDto.setBaseInformationId(baseInfoId);
			filterInformationDetailMapper.insertFilterInfoDetail(filterInformationDetailDto);
		});
		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * 修改方案名称
	 * 
	 * @param param 页面参数
	 * @return 是否修改成功
	 */
	public boolean updatePlanName(Map<String, Object> param) {
		int count = 0;
		String planName = (String) param.get("planName");
		String filterId = (String) param.get("filterId");
		FilterInformationDto filterInformationDto = new FilterInformationDto();
		filterInformationDto.setFilterName(planName);
		filterInformationDto.setFilterId(filterId);
		count += filterInformationMapper.updateFilterInfo(filterInformationDto);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据用户ID获取已保存方案信息
	 * 
	 * @return 已保存方案信息
	 */
	public Page<FilterInformationDto> queryFilterInfoByUserId(Map<String, Object> pageParam) {
//		String userId = CommonUtil.getUid();
		String searchKey = (String) pageParam.get("searchKey");
//		pageParam.put("userId", pageParam.get("userId"));
		pageParam.put("searchCondition","%" + searchKey + "%");
        int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
		Page<FilterInformationDto> page = queryForPage(pagenum,legth,()->filterInformationMapper.queryFilterInfoByUserId(pageParam));
		return page;
	}

	/**
	 * 根据用户ID获取已保存方案
	 * 
	 * @param param 页面参数
	 * @return 已保存方案
	 */
	public String queryPlanByfilterId(Map<String, Object> param) {
		String filterId = (String) param.get("filterId");
		List<BaseInformationDto> baseInformationList = baseInformationMapper.queryPlanByfilterId(filterId);
		Map<String, String> data = new HashMap<String, String>();
		baseInformationList.forEach(baseInformation -> {
			if (null == data.get("tableName")) {
				data.put("tableName", baseInformation.getBaseTableId());
			}
			data.put(baseInformation.getBaseColumnId(), baseInformation.getBaseColumnName());
		});
		return JSON.toJSONString(data);
	}

	/**
	 * 数据导出
	 * @param param 页面参数
	 * @return 导出结果
	 */
	public ResponseEntity<byte[]> export(Map<String, Object> param) {
		String headerNum = (String) param.get("headerNum");
		String tableName=(String) param.get("tableName");
		if (CHB_INTERF_ERRODATA.equals(tableName)) {
			// 错误信息表数据导出
			return exportErrorInfo(param);
		}else if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
			//当tableId与tableName不相等，将tableName赋值给tableId
			param.put("tableId", tableName);
			// 资产要素表数据导出
			return exportChbAssetInfo(param);
		}
		else {
			// 正常业务表数据导出
			return exportInfo(param);
		}
	}

	/**
	 * 资产要素表数据导出
	 * @Description TODO
	 * @Author      高健
	 * @Date        2019年12月5日 下午2:13:16
	 * @param @param param
	 * @param @return 参数
	 * @return ResponseEntity<byte[]> 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private ResponseEntity<byte[]> exportChbAssetInfo(Map<String, Object> param) {
		List<HashMap<String, Object>> listMap = new ArrayList<>();
		ResponseEntity<byte[]> responseEntity= null;
		String tableId = (String) param.get("tableId");
		String tableName = (String) param.get("tableName");
		String dataObj = (String) param.get("jasonData");
		param.put("flag", false);
		List<SysDataDict> dictList = tamsBaseMapper.selectSysDatadictValueList("SVR_ASSETDEBT_TYPE");
		List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfoEx(param);
		// 资产要素表导出需要合并单元格的列号索引
		List<Integer> mergeIndexList = new ArrayList<Integer>();
		mergeIndexList.add(0);
		mergeIndexList.add(1);
		int columnSize = chbInfoMgrResultConfigList.size();
		if (columnSize >= CAI_BASE_COUNT) {
			for (int i = CAI_BASE_COUNT; i <=  columnSize; i ++) {
				mergeIndexList.add(i);
			}
		}
		// 针对有数据字典的情况，创建Map, key值为字段名称（字段名称+资产负债类型），value为数据字典
		Map<String, String> resultMap = new HashMap<String, String>(16);
		for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
			if (StringUtil.isNotEmpty(chbInfoMgrResultConfig.getResultdictCode())) {
				resultMap.put(chbInfoMgrResultConfig.getColumnId(), chbInfoMgrResultConfig.getResultdictCode());
				for (SysDataDict sysDataDict : dictList) {
					resultMap.put(chbInfoMgrResultConfig.getColumnId() + sysDataDict.getItemCode(), chbInfoMgrResultConfig.getResultdictCode());
				}
			}

		}
		if (dataObj != null && dataObj.length() > 0 && !"[]".equals(dataObj)) {
			List<JSONObject> jsonList = JSONArray.parseArray(dataObj).toJavaList(JSONObject.class);
			for (JSONObject jsonObj : jsonList) {
				Map<String, Object> jsonMap = JSONObject.toJavaObject(jsonObj, Map.class);
				Map<String, Object> itemMap = new HashMap<String, Object>();
				for (String key : jsonMap.keySet()) {
					itemMap.put(key, jsonMap.get(key));
				}
				listMap.add((HashMap<String, Object>) itemMap);
			}

		}
		//否则直接根据条件获取 资产要素导出--根据选择条件导出
		else { 
			List<Object> list = queryDataList(param);
			List<String> dateColumnList =  baseInformationMapper.queryDateColumnList(tableName);
			List<String> timeColumnList =  baseInformationMapper.queryTimeColumnList(tableName);
			for (Object obj : list) {
				Map<String, Object> map = (Map<String, Object>) obj;
				if (StringUtil.isNotEmpty(map.get("ASSET_TYPE"))) {
					String assetType = map.get("ASSET_TYPE").toString();
					if(StringUtil.equals("19", assetType)){
						assetType = "15";
					}
					HashMap<String, Object> newMap = new HashMap<String, Object>();
					// 资产要素表特殊处理
					for (String str : map.keySet()) {
						newMap.put(str, map.get(str));
						newMap.put(str + assetType, map.get(str));
					}
					for (String str : newMap.keySet()) {
						String realStr = "";
						if (str.endsWith(assetType)) {
							realStr = str.replace(assetType, "");
						}
						
						//查询结果为日期类型字段处理
						if (dateColumnList.contains(str) || dateColumnList.contains(realStr)) {
							Object dateObj = newMap.get(str);
							if (null != dateObj) {
								newMap.put(str, DateUtil.format((Date) dateObj));
							}
							
						}
						// 查询结果为时间类型字段处理
						if (timeColumnList.contains(str) || timeColumnList.contains(realStr)) {
							Object dateObj = newMap.get(str);
							if (null != dateObj) {
								newMap.put(str, dateObj.toString());
							}
						}
						// 查询结果为枚举类型字段处理
						String resultDictCode = resultMap.get(str);
						if (StringUtil.isNotEmpty(resultDictCode) && null != newMap.get(str) ) {
							String dateObj = newMap.get(str).toString();
							String value = queryValueByDictAndKeyCache(resultDictCode, dateObj);
							newMap.put(str, value);
						}
					}
					listMap.add(newMap);
				}
				
			}
		 }
		/*
		 * else { list = queryDataList(param); for (Object obj : list) { HashMap<String,
		 * Object> map = (HashMap<String, Object>) obj; String assetType =
		 * map.get("ASSET_TYPE").toString(); for (String str : map.keySet()) { //
		 * 查询结果为枚举类型字段处理 String resultDictCode = resultMap.get(str); if
		 * (StringUtil.isNotEmpty(resultDictCode) && null != map.get(str) ) { String
		 * dateObj = map.get(str).toString(); String value =
		 * queryValueByDictAndKey(resultDictCode, dateObj); map.put(str, value);
		 * map.put(str + assetType, value); } else { map.put(str + assetType,
		 * map.get(str)); } } listMap.add(map); } }
		 */

		int size = chbInfoMgrResultConfigList.size();
		String[] title = new String[size + 1];
		String[] titleCode = new String[size + 1];
		title[0] = "操作类型";
		titleCode[0] = "操作类型";
		int i = 1;
		for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
			title[i] = chbInfoMgrResultConfig.getColumnName();
			titleCode[i] = chbInfoMgrResultConfig.getColumnId();
			i++;
		}
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = new Object[size + 1];
		int mapSize = listMap.size();
		// 存储结果为数据字典的字段情况,key值为数据字典类型字段对应的索引，value为数据字典code
		Map<Integer, String> enumIndexMap = new HashMap<Integer, String>(16);
		//modify by wm 当无数据时，excel里需要由枚举值 start 20201029
		if(mapSize>0){
			for (int j = 0; j < mapSize; j++) {
				HashMap<String, Object> data = listMap.get(j);
				objs = new Object[size + 1];
				objs[0] = "导出";
				for (int k = 1; k < size + 1; k++) {
					objs[k] = data.get(titleCode[k]);
					// 查询结果为枚举类型字段处理
					String resultDictCode = resultMap.get(titleCode[k]);
					if (StringUtil.isNotEmpty(resultDictCode)) {
						enumIndexMap.put(k, resultDictCode);
					}
				}
				dataList.add(objs);
			}
		}
		else{
			for (int k = 1; k < size + 1; k++) {
				// 查询结果为枚举类型字段处理
				String resultDictCode = resultMap.get(titleCode[k]);
				if (StringUtil.isNotEmpty(resultDictCode)) {
					enumIndexMap.put(k, resultDictCode);
				}
			}
		}
		//modify by wm 当无数据时，excel里需要由枚举值 start end
		try {
			responseEntity = chbAssetListToExcel(tableId,tableName, title, dataList, enumIndexMap, mergeIndexList);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return responseEntity;
	}
	/**
	 * 根据页面参数导出查询结果
	 * 
	 * @param param 页面参数
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private ResponseEntity<byte[]> exportInfo(Map<String, Object> param) {
		List<HashMap<String, Object>> listMap = new ArrayList<>();
		ResponseEntity<byte[]> responseEntity= null;
//		String tableId = (String) param.get("tableId");
		String tableId = (String) param.get("tableName");
		//获取表名
		ChbMgrTableInfo  chbMgrTableInfo=chbMgrTableInfoMapper.getTableInfobyTableId(tableId);
		String tableName=chbMgrTableInfo.getMgrTableName();
		String headerNum=chbMgrTableInfo.getHeaderNum();
		String mgrTableChiName=chbMgrTableInfo.getMgrTableChiName();
		
		if(CHB_ASSET_INFO_LIST.contains(tableName)&&StringUtil.equals("1", headerNum)){
			//是资产基本信息并且是配置的单表头
			//DictCodeListExt2 dictCodeListExt2=DictCodeListExt2.from(DictCode.资产_负债类别, mgrTableChiName);
			//String assetType=SysDataUtil.getCodeFromDataDictByName(DictCode.资产_负债类别, mgrTableChiName);
			String assetType=nameMap.get(mgrTableChiName);
			param.put("assetType", assetType);
		}
		
		String dataObj = (String) param.get("jasonData");
		param.put("tableName", tableName);
		param.put("flag", false);
		List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(param);
		List<Object> list = new ArrayList<Object>();
		// 创建Map, key值为字段名称，value为数据字典
		Map<String, String> resultMap = new HashMap<String, String>(16);
		for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
			resultMap.put(chbInfoMgrResultConfig.getColumnId(), chbInfoMgrResultConfig.getResultdictCode());
		}
		if (dataObj != null && dataObj.length() > 0 && !"[]".equals(dataObj)) {
			List<JSONObject> jsonList = JSONArray.parseArray(dataObj).toJavaList(JSONObject.class);
			for (JSONObject jsonObj : jsonList) {
				Map<String, Object> jsonMap = JSONObject.toJavaObject(jsonObj, Map.class);
				Map<String, Object> itemMap = new HashMap<String, Object>();
				for (String key : jsonMap.keySet()) {
					itemMap.put(key, jsonMap.get(key));
				}
				listMap.add((HashMap<String, Object>) itemMap);
			}
			
		} else {
			list = queryDataList(param);
			List<String> dateColumnList =  baseInformationMapper.queryDateColumnList(tableName);
			for (Object obj : list) {
				HashMap<String, Object> map = (HashMap<String, Object>) obj;
				for (String str : map.keySet()) {
					// 查询结果为枚举类型字段处理
					String resultDictCode = resultMap.get(str);
					if (StringUtil.isNotEmpty(resultDictCode) && null != map.get(str) ) {
						String dateObj = map.get(str).toString();
						String value = queryValueByDictAndKeyCache(resultDictCode, dateObj);
						map.put(str, value);
					}
					/**添加日期格式的字段处理  ZZOTZL-114  add  by  dongxiangyang  start 20201021*/
					//如果为日期类型
					if(dateColumnList.contains(str)){
	            		Object dateObj = map.get(str);
						if (null != dateObj) {
							map.put(str, DateUtil.format((Date) dateObj));
						}
	            	}
					/**添加日期格式的字段处理  ZZOTZL-114   add  by  dongxiangyang  start 20201021*/
					
				}
				listMap.add(map);
			}
		}
		int size = chbInfoMgrResultConfigList.size();
		String[] title = new String[size + 1];
		String[] titleCode = new String[size + 1];
		title[0] = "操作类型";
		titleCode[0] = "操作类型";
		int i = 1;
		for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
			title[i] = chbInfoMgrResultConfig.getColumnName();
			titleCode[i] = chbInfoMgrResultConfig.getColumnId();
			i++;
		}
		List<Object[]> dataList = new ArrayList<Object[]>();
		Object[] objs = new Object[size + 1];
		int mapSize = listMap.size();
		// 存储结果为数据字典的字段情况,key值为数据字典类型字段对应的索引，value为数据字典code
		Map<Integer, String> enumIndexMap = new HashMap<Integer, String>(16);
		//modify by wm 当无数据时，excel里需要由枚举值 start 20201029
		if(mapSize>0){
			for (int j = 0; j < mapSize; j++) {
				HashMap<String, Object> data = listMap.get(j);
				objs = new Object[size + 1];
				objs[0] = "导出";
				for (int k = 1; k < size + 1; k++) {
//					objs[k] = data.get(title[k]);
					objs[k] = data.get(titleCode[k]);
					// 查询结果为枚举类型字段处理
					String resultDictCode = resultMap.get(titleCode[k]);
					if (StringUtil.isNotEmpty(resultDictCode)) {
						enumIndexMap.put(k, resultDictCode);
					}
				}
				dataList.add(objs);
			}
		}
		else{
			for (int k = 1; k < size + 1; k++) {
				// 查询结果为枚举类型字段处理
				String resultDictCode = resultMap.get(titleCode[k]);
				if (StringUtil.isNotEmpty(resultDictCode)) {
					enumIndexMap.put(k, resultDictCode);
				}
			}
		}
		//modify by wm 当无数据时，excel里需要由枚举值 end 20201029
		try {
			responseEntity = objListToExcel(tableId,tableName, title, dataList, false, enumIndexMap);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return responseEntity;
	}
	/**
	* 替换一个字符串中的某些指定字符
	* @param strData String 原始字符串
	* @param regex String 要替换的字符串
	* @param replacement String 替代字符串
	* @return String 替换后的字符串
	*/
	public static String replaceString(String strData, String regex, String replacement) {
	    if (strData == null) {
	        return null;
	    }
	    int index;
	    index = strData.indexOf(regex);
	    String strNew = "";
	    if (index >= 0) {
	        while (index >= 0) {
	            strNew += strData.substring(0, index) + replacement;
	            strData = strData.substring(index + regex.length());
	            index = strData.indexOf(regex);
	        }
	        strNew += strData;
	        return strNew;
	    }
	    return strData;
	}

	/**
	 * 根据页面传输信息导出错误信息
	 * @param param 页面信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ResponseEntity<byte[]> exportErrorInfo(Map<String, Object> param) {
		ResponseEntity<byte[]> responseEntity= null;
		List<Map<String, String>> listMap = new ArrayList<>();
		String dataObj = (String) param.get("jasonData");
		List<Map<String, Map<String, String>>> mapList = new ArrayList<Map<String,Map<String, String>>>();
		if(dataObj != null && dataObj.length() > 0 && !"[]".equals(dataObj)){
			List<JSONObject> jsonList = JSONArray.parseArray(dataObj).toJavaList(JSONObject.class);
			mapList = convertJasonToList(jsonList);
		}
		else{
			param.put("flag", false);
			List<Object> list = queryDataList(param);
			for (Object object : list) {
				Map<String, Object> map = (Map<String, Object>) object;
				Map<String, Map<String, String>> tableMap = new HashMap<String, Map<String, String>>(1 << 1);
				// 发生错误的业务表表名
				String tableName = (String) map.get("INTERF_TYPE");
				// 错误数据
				String errorData = this.clobToString((Clob) map.get("ORG_DATA"));
				/**关于ORG_DATA中值会出现逗号的情况，导致分割错误，ZZOTZL-125     add by  dongxiangyang  20201023*/
			    Map<String,String> errorMap=JsonUtils.json2Object(errorData, HashMap.class);
//				String[] errorDataList = errorData.split(GlobalConstant.COMMA);
//				Map<String, String> errorMap = new HashMap<String, String>(1 << 4);
//				int dataSize = errorDataList.length;
//				for (int i = 0; i < dataSize; i ++) {
//					String[] errorArray = errorDataList[i].split(GlobalConstant.COLON);
//					if (errorArray.length > 1) {
//						errorMap.put(errorArray[0], errorArray[1]);
//					} else {
//						errorMap.put(errorArray[0], "");
//					}
//					 
//				}
				/**关于ORG_DATA中值会出现逗号的情况，导致分割错误， ZZOTZL-125   add by  dongxiangyang  20201023*/
				tableMap.put(tableName, errorMap);
				mapList.add(tableMap);
			}
		}

		Map<String, List<Map<String, String>>> groupMap = convertListToMap(mapList);
		Iterator<Entry<String, List<Map<String, String>>>> iterator = groupMap.entrySet().iterator();
		if (iterator.hasNext()) {
			Entry<String, List<Map<String, String>>> entity = iterator.next();
			// 根据接口类型找到表名
			List<String> tableIds = baseInformationMapper.queryTableIdByDataObject(entity.getKey());

			Map<String,Object> selectParam = new HashMap<>();
			selectParam.put("tableId", tableIds.get(0));
            List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(selectParam);
            Map<String, String> resultMap = new HashMap<String, String>(16);
//            List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = new ArrayList<ChbInfoMgrResultConfig>();
            // 资产要素表导出需要合并单元格的列号索引
    		List<Integer> mergeIndexList = new ArrayList<Integer>();
    		if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
    			List<SysDataDict> dictList = tamsBaseMapper.selectSysDatadictValueList("SVR_ASSETDEBT_TYPE");
    			chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfoEx(selectParam);
    			mergeIndexList.add(0);
        		mergeIndexList.add(1);
        		int columnSize = chbInfoMgrResultConfigList.size();
        		if (columnSize >= CAI_BASE_COUNT) {
        			for (int i = CAI_BASE_COUNT; i <=  columnSize; i ++) {
        				mergeIndexList.add(i);
        			}
        		}
        		for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
        			if (StringUtil.isNotEmpty(chbInfoMgrResultConfig.getResultdictCode())) {
        				resultMap.put(chbInfoMgrResultConfig.getRealColumnId(), chbInfoMgrResultConfig.getResultdictCode());
        				for (SysDataDict sysDataDict : dictList) {
        					resultMap.put(chbInfoMgrResultConfig.getRealColumnId() + sysDataDict.getItemCode(), chbInfoMgrResultConfig.getResultdictCode());
        				}
        			}

        		}

        		for (Map<String, String> map : entity.getValue()) {
        			if (StringUtil.isNotEmpty(map.get("ASSET_TYPE"))) {
        				if(StringUtil.equals("ASSET_TYPE", map.get("ASSET_TYPE"))){
        					continue;
        				}
        				List<String> dateColumnList =  baseInformationMapper.queryDateColumnList(tableIds.get(0));
        				List<String> timeColumnList =  baseInformationMapper.queryTimeColumnList(tableIds.get(0));
    					String assetType = map.get("ASSET_TYPE").toString();
    					if(StringUtil.equals("19", assetType)){
    						assetType = "15";
    					}
    					HashMap<String, String> newMap = new HashMap<String, String>();
    					// 资产要素表特殊处理
    					for (String str : map.keySet()) {
    						newMap.put(str, map.get(str));
    						newMap.put(str + assetType, map.get(str));
    					}
    					for (String str : newMap.keySet()) {
    						String realStr = "";
    						if (str.endsWith(assetType)) {
    							realStr = str.replace(assetType, "");
    						}

    						//查询结果为日期类型字段处理
    						if (dateColumnList.contains(str) || dateColumnList.contains(realStr)) {
    							Object dateObj = newMap.get(str);
    							if (null != dateObj) {
    								newMap.put(str, (String) dateObj);
    							}

    						}
    						// 查询结果为时间类型字段处理
    						if (timeColumnList.contains(str) || timeColumnList.contains(realStr)) {
    							Object dateObj = newMap.get(str);
    							if (null != dateObj) {
    								newMap.put(str, dateObj.toString());
    							}
    						}
    						// 查询结果为枚举类型字段处理
    						String resultDictCode = resultMap.get(str);
    						if (StringUtil.isNotEmpty(resultDictCode) && null != newMap.get(str) ) {
    							String dateObj = newMap.get(str).toString();
    							String value = queryValueByDictAndKeyCache(resultDictCode, dateObj);
    							newMap.put(str, value);
    						}
    					}
    					listMap.add(newMap);
    				}
        			else{
        				for (String str : map.keySet()) {
        					String resultDictCode = resultMap.get(str);
        					if (StringUtil.isNotEmpty(resultDictCode) && StringUtil.isNotEmpty(map.get(str)) ) {
        						String dateObj = map.get(str).toString();
        						String value = queryValueByDictAndKeyCache(resultDictCode, dateObj);
        						map.put(str, value);
        					}
        				}
        				listMap.add(map);
        			}
    			}
    		}
    		else{
                chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(selectParam);
                for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
                    resultMap.put(chbInfoMgrResultConfig.getColumnId(), chbInfoMgrResultConfig.getResultdictCode());
                }
    			for (Map<String, String> map : entity.getValue()) {
    				for (String str : map.keySet()) {
    					String resultDictCode = resultMap.get(str);
    					if (StringUtil.isNotEmpty(resultDictCode) && StringUtil.isNotEmpty(map.get(str)) ) {
    						String dateObj = map.get(str).toString();
    						String value = queryValueByDictAndKeyCache(resultDictCode, dateObj);
    						map.put(str, value);
    					}
    				}
    				listMap.add(map);
    			}
    		}


            int size = chbInfoMgrResultConfigList.size();
			String[] title = new String[size + 1];
			String[] titleCode = new String[size + 1];
			title[0] = "操作类型";
			if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
				titleCode[0] = "操作类型";
			}
			int i = 1;
            for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
                title[i] = chbInfoMgrResultConfig.getColumnName();
                // modify by dubaojun 2020-10-31 在上面已经设置了带有ASSET_TYPE的字段值，此处直接取即可 start
//                if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
//                    titleCode[i] = chbInfoMgrResultConfig.getRealColumnId();
//                }
//                else{
                titleCode[i] = chbInfoMgrResultConfig.getColumnId();
//                }
                // modify by dubaojun 2020-10-31 在上面已经设置了带有ASSET_TYPE的字段值，此处直接取即可 end
                i++;
            }
			List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = new Object[size + 1];
            Map<Integer, String> enumIndexMap = new HashMap<Integer, String>(16);
            if(listMap.size()>0){
            	for (int j = 0; j < listMap.size(); j++) {
                    Map<String,String> data = listMap.get(j);
    				objs = new Object[size + 1];
                    objs[0] = "导出";
                    for (int k = 1; k < size + 1; k++) {
                        objs[k] = data.get(titleCode[k]);
                        String resultDictCode = resultMap.get(titleCode[k]);
                        if (StringUtil.isNotEmpty(resultDictCode)) {
                            enumIndexMap.put(k,resultDictCode);
                        }
                    }
                    dataList.add(objs);
                }
            }
            else{
            	for (int k = 1; k < size + 1; k++) {
                    String resultDictCode = resultMap.get(titleCode[k]);
                    if (StringUtil.isNotEmpty(resultDictCode)) {
                        enumIndexMap.put(k,resultDictCode);
                    }
                }
            }
			try {
			if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
				responseEntity = chbAssetListToExcel(tableIds.get(0),tableIds.get(0), title, dataList, enumIndexMap, mergeIndexList);;
			}
			else{
				responseEntity = objListToExcel(tableIds.get(0),tableIds.get(0), title, dataList, false, enumIndexMap);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
			return responseEntity;

		}  else {  // update by zoujiamin TISHKBS-2318  当错误信息查询结果为空的时候，完成导出空文件功能  start
			// 根据传参接口类型找到表名
			List<String> tableIds = baseInformationMapper.queryTableIdByDataObject((String) param.get("serachInterf"));

			Map<String,Object> selectParam = new HashMap<>();
			selectParam.put("tableId", tableIds.get(0));
			List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(selectParam);
			Map<String, String> resultMap = new HashMap<String, String>(16);
			// 资产要素表导出需要合并单元格的列号索引
			List<Integer> mergeIndexList = new ArrayList<Integer>();
			if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
				List<SysDataDict> dictList = tamsBaseMapper.selectSysDatadictValueList("SVR_ASSETDEBT_TYPE");
				chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfoEx(selectParam);
				mergeIndexList.add(0);
				mergeIndexList.add(1);
				int columnSize = chbInfoMgrResultConfigList.size();
				if (columnSize >= CAI_BASE_COUNT) {
					for (int i = CAI_BASE_COUNT; i <=  columnSize; i ++) {
						mergeIndexList.add(i);
					}
				}
				for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
					if (StringUtil.isNotEmpty(chbInfoMgrResultConfig.getResultdictCode())) {
						resultMap.put(chbInfoMgrResultConfig.getRealColumnId(), chbInfoMgrResultConfig.getResultdictCode());
						for (SysDataDict sysDataDict : dictList) {
							resultMap.put(chbInfoMgrResultConfig.getRealColumnId() + sysDataDict.getItemCode(), chbInfoMgrResultConfig.getResultdictCode());
						}
					}
				}
			}
			else{
				chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(selectParam);
				for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
					resultMap.put(chbInfoMgrResultConfig.getColumnId(), chbInfoMgrResultConfig.getResultdictCode());
				}
			}


			int size = chbInfoMgrResultConfigList.size();
			String[] title = new String[size + 1];
			String[] titleCode = new String[size + 1];
			title[0] = "操作类型";
			if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
				titleCode[0] = "操作类型";
			}
			int i = 1;
			for (ChbInfoMgrResultConfig chbInfoMgrResultConfig : chbInfoMgrResultConfigList) {
				title[i] = chbInfoMgrResultConfig.getColumnName();
				// modify by dubaojun 2020-10-31 在上面已经设置了带有ASSET_TYPE的字段值，此处直接取即可 start
//                if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
//                    titleCode[i] = chbInfoMgrResultConfig.getRealColumnId();
//                }
//                else{
				titleCode[i] = chbInfoMgrResultConfig.getColumnId();
//                }
				// modify by dubaojun 2020-10-31 在上面已经设置了带有ASSET_TYPE的字段值，此处直接取即可 end
				i++;
			}
			List<Object[]> dataList = new ArrayList<Object[]>();
			Object[] objs = new Object[size + 1];
			Map<Integer, String> enumIndexMap = new HashMap<Integer, String>(16);
			if(listMap.size()>0){
				for (int j = 0; j < listMap.size(); j++) {
					Map<String,String> data = listMap.get(j);
					objs = new Object[size + 1];
					objs[0] = "导出";
					for (int k = 1; k < size + 1; k++) {
						objs[k] = data.get(titleCode[k]);
						String resultDictCode = resultMap.get(titleCode[k]);
						if (StringUtil.isNotEmpty(resultDictCode)) {
							enumIndexMap.put(k,resultDictCode);
						}
					}
					dataList.add(objs);
				}
			}
			else{
				for (int k = 1; k < size + 1; k++) {
					String resultDictCode = resultMap.get(titleCode[k]);
					if (StringUtil.isNotEmpty(resultDictCode)) {
						enumIndexMap.put(k,resultDictCode);
					}
				}
			}
			try {
				if(CHB_ASSET_INFO_LIST.contains(tableIds.get(0))){
					responseEntity = chbAssetListToExcel(tableIds.get(0),tableIds.get(0), title, dataList, enumIndexMap, mergeIndexList);;
				}
				else{
					responseEntity = objListToExcel(tableIds.get(0),tableIds.get(0), title, dataList, false, enumIndexMap);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				e.printStackTrace();
			}
			return responseEntity;
		}
		// update by zoujiamin TISHKBS-2318  当错误信息查询结果为空的时候，完成导出空文件功能  end

	}
	
	/**
	 * 查找指定元素在数组中的下标
	 * @param strArr 数组
	 * @param str 待查元素
	 * @return 数组下标
	 */
	private int searchIndex(String[] strArr, String str) {
		int index  = -1;
		int length = strArr.length;
		for (int i = 0; i < length; i ++) {
			if (str.equals(strArr[i])) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * Json数组转为List
	 * @param jsonList Json数组
	 * @return List
	 */
	private List<Map<String, Map<String, String>>> convertJasonToList(List<JSONObject> jsonList) {
		List<Map<String, Map<String, String>>> mapList = new ArrayList<Map<String,Map<String, String>>>();
		for (JSONObject jsonObj : jsonList) {
			logger.info("jsonObj数据：---------------"+jsonObj);
			Map<String, Map<String, String>> tableMap = new HashMap<String, Map<String, String>>(1 << 1);
			// 发生错误的业务表表名
			String tableName = (String) jsonObj.get("INTERF_TYPE");
			// 错误数据
			String errorData = (String) jsonObj.get("ORG_DATA");
			//add by wm 针对特殊字符处理  jason 识别“'”字符时转换会有问题，因为在js里进行了转换，故这里给转换回来 start
			errorData = errorData.replaceAll("&apos;", "'");
			//add by wm 针对特殊字符处理  jason 识别“'”字符时转换会有问题，因为在js里进行了转换，故这里给转换回来 end
			/**关于ORG_DATA中值会出现逗号的情况，导致分割错误，  ZZOTZL-125  add by  dongxiangyang  20201023*/
		    Map<String,String> errorMap=JsonUtils.json2Object(errorData, HashMap.class);
//			String[] errorDataList = errorData.split(GlobalConstant.COMMA);
//			Map<String, String> errorMap = new HashMap<String, String>(1 << 4);
//			int dataSize = errorDataList.length;
//			for (int i = 0; i < dataSize; i ++) {
//				String[] errorArray = errorDataList[i].split(GlobalConstant.COLON);
//				if (errorArray.length > 1) {
//					errorMap.put(errorArray[0], errorArray[1]);
//				} else {
//					errorMap.put(errorArray[0], "");
//				}
//				 
//			}
			/**关于ORG_DATA中值会出现逗号的情况，导致分割错误，  ZZOTZL-125   add by  dongxiangyang  20201023*/
			tableMap.put(tableName, errorMap);
			mapList.add(tableMap);
		}
		return mapList;
	}
	
	/**
	 * List<Map> 按 Key值分组转为Map
	 * @param mapList Map集合
	 * @return 按Key值分组的Map
	 */
	private Map<String, List<Map<String, String>>> convertListToMap(List<Map<String, Map<String, String>>> mapList) {
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String,String>>>();
		for (Map<String, Map<String, String>> temp : mapList) {
			Iterator<Entry<String, Map<String, String>>> iterator = temp.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Map<String, String>> entity = iterator.next();
				if (map.containsKey(entity.getKey())) {
					List<Map<String, String>> lm = map.get(entity.getKey());
					lm.add(entity.getValue());
                    map.replace(entity.getKey(), lm);
				} else {
					List<Map<String, String>> listAndMap = new ArrayList<>();
					listAndMap.add(entity.getValue());
					map.put(entity.getKey(), listAndMap);
				}
			}
		}
		return map;
	}

	/**
	 *
	 * @Description 导出资产要素表数据
	 * @Author      高健
	 * @Date        2019年12月6日 下午3:38:44
	 * @param @param tableName
	 * @param @param title
	 * @param @param objList
	 * @param @param enumIndexMap
	 * @param @param mergeIndexList
	 * @param @return 参数
	 * @return ResponseEntity<byte[]> 返回类型
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	private ResponseEntity<byte[]> chbAssetListToExcel(String tableId,String tableName, String[] title, List<Object[]> objList, Map<Integer, String> enumIndexMap, List<Integer> mergeIndexList) {
		ResponseEntity<byte[]> responseEntity= null;
		String createTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String exportFilePath = SvrDataUtil.getSysParam("access_exportfilepath");
		List<BaseInformationDto> baseInformationDtoList = baseInformationMapper.queryBaseInfoByTableId(tableId);
		// modify by dubaojun 2020-10-15 业务数据维护导出报错问题修改，原因是导出文件时路径未拼接分割符，导致路径不正确 start
		String fileName = exportFilePath + File.separator + tableName + createTime + ".xlsx";
		// modify by dubaojun 2020-10-15 业务数据维护导出报错问题修改，原因是导出文件时路径未拼接分割符，导致路径不正确 end
		String chiName = baseInformationDtoList.get(0).getBaseTableName();
		String exportFileName = chiName + createTime + ".xlsx";
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			// 主表格
			XSSFSheet sheet = workbook.createSheet(chiName);
			// 枚举值表格
			XSSFSheet sheet1 = workbook.createSheet(tableName + "_ENUM");

			XSSFRow firstRowRowName = sheet.createRow(0); // 在索引0的位置创建行(最顶端的行开始的第一行)

			firstRowRowName.setHeight((short) (25 * 15)); // 设置高度
			// 第一列设置
			for (Integer index : mergeIndexList) {
				XSSFCell cell = firstRowRowName.createCell(index);
				cell.setCellStyle(style);
				cell.setCellValue(title[index]);
			}
			for (String key : headerMap.keySet()) {
				XSSFCell cell = firstRowRowName.createCell(Integer.parseInt(key.split("-")[0]));
				cell.setCellStyle(style);
				cell.setCellValue(headerMap.get(key).split(":")[1]);
			}

			// 定义所需列数
			int columnNum = title.length;
			XSSFRow rowRowName = sheet.createRow(1); // 在索引1的位置创建行(最顶端的行开始的第二行)

			rowRowName.setHeight((short) (25 * 15)); // 设置高度



			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
				XSSFRichTextString text = new XSSFRichTextString(title[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				sheet.autoSizeColumn(n, true);
			}

			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < objList.size(); i++) {

				Object[] obj = objList.get(i);// 遍历每个对象
				XSSFRow row = sheet.createRow(i + 2);// 创建所需的行数

				row.setHeight((short) (25 * 20)); // 设置高度

				for (int j = 0; j < obj.length; j++) {
					XSSFCell cell = null; // 设置单元格的数据类型
					cell = row.createCell(j, CellType.STRING);
					if (obj[j] != null && !"".equals(obj[j])) {
						cell.setCellValue(obj[j].toString()); // 设置单元格的值
					}
				}
			}
			// 指定索引列引用的数据位置,key值为需要设置数据有效新的索引，value为数据有效性值的引用位置
			Map<Integer, String> refAddressMap = new HashMap<Integer, String>();
			// 数据字典枚举值设置
			for (Integer index : enumIndexMap.keySet()) {
				// 创建所需的行数
				XSSFRow row = sheet1.createRow(index);
				// 设置高度
				row.setHeight((short) (25 * 20));
				// 此行所存入的数据字典枚举值
				String dictCode = enumIndexMap.get(index);
				// 需要存入枚举列表的值
				String[] dictArr = getDictCodeListCache(dictCode);
				if(dictArr==null) {
					continue;
				}
				int length = dictArr.length;
				// 向表格中写入值
				for (int j = 0; j < length; j++) {
					XSSFCell cell = null; // 设置单元格的数据类型
					cell = row.createCell(j, CellType.STRING);
					if (dictArr[j] != null && !"".equals(dictArr[j])) {
						cell.setCellValue(dictArr[j]); // 设置单元格的值
					}
				}
				String selectBoxRef = getSelectBoxRef(index + 1, length, tableName + "_ENUM");
				refAddressMap.put(index, selectBoxRef);
			}
//			XSSFDataValidation validate = setBoxs(sheet, refAddressMap);
			setBoxs(sheet, refAddressMap, 2);
//			sheet.addValidationData(validate);
			// 必须在单元格设值以后进行
			// 设置为根据内容自动调整列宽
			for (int k = 0; k < columnNum; k++) {
				sheet.autoSizeColumn(k);
			}
			// 处理中文不能自动调整列宽的问题
			this.setSizeColumn(sheet, columnNum);

			// 合并单元格
			for (Integer index : mergeIndexList) {
				CellRangeAddress region = new CellRangeAddress(0, 1, index, index);
				sheet.addMergedRegion(region);
			}

			for (String key : headerMap.keySet()) {
				String[] indexArr = key.split("-");
				int beginIndex = Integer.parseInt(indexArr[0]);
				int endIndex = Integer.parseInt(indexArr[1]);
				CellRangeAddress region = new CellRangeAddress(0, 0, beginIndex, endIndex);
				sheet.addMergedRegion(region);
			}

			if (workbook != null) {
				FileOutputStream out = null;
				try {
					File exportFile = new SafelyFile(fileName);
					out = new FileOutputStream(exportFile);
					workbook.write(out);
					out.close();
					responseEntity = ExportUtil.export(exportFileName, exportFile, true);
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}finally {
					if(out != null){
						out.close();
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return responseEntity;
	}
	
	/**
	 * 导出excel
	 * @param tableName 表名
	 * @param title 列名
	 * @param objList 数据名
	 * @param flag 是否为错误信息表数据
	 * @param enumIndexMap 枚举类型字段情况
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private ResponseEntity<byte[]> objListToExcel(String tableId,String tableName, String[] title, List<Object[]> objList, boolean flag,Map<Integer, String> enumIndexMap) {
		ResponseEntity<byte[]> responseEntity= null;
		String createTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String exportFilePath = SvrDataUtil.getSysParam("access_exportfilepath");
		String fileName = "";
		String exportFileName = "";
		String chiName="";
		if(CHB_ASSET_INFO_LIST.contains(tableName)){
			//如果是资产要素表,资产大类做
			//chiName="资产基本信息";
			ChbMgrTableInfo  chbMgrTableInfo=chbMgrTableInfoMapper.getTableInfobyTableId(tableId);
			chiName=chbMgrTableInfo.getMgrTableChiName();
		}else{
			List<BaseInformationDto> baseInformationDtoList = baseInformationMapper.queryBaseInfoByTableId(tableName);
			chiName = baseInformationDtoList.get(0).getBaseTableName();
		}
		if (flag) {
	        // modify by dubaojun 2020-10-15 业务数据维护导出报错问题修改，原因是导出文件时路径未拼接分割符，导致路径不正确 start
			fileName = exportFilePath + File.separator + tableName + "错误信息" + createTime + ".xlsx";
	        // modify by dubaojun 2020-10-15 业务数据维护导出报错问题修改，原因是导出文件时路径未拼接分割符，导致路径不正确 end
			exportFileName = chiName + "错误信息" + createTime + ".xlsx";
		} else {
	        // modify by dubaojun 2020-10-15 业务数据维护导出报错问题修改，原因是导出文件时路径未拼接分割符，导致路径不正确 start
			fileName = exportFilePath + File.separator + tableName + createTime + ".xlsx";
	        // modify by dubaojun 2020-10-15 业务数据维护导出报错问题修改，原因是导出文件时路径未拼接分割符，导致路径不正确 end
			exportFileName = chiName + createTime + ".xlsx";
		}
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
			// 主表格
			XSSFSheet sheet = workbook.createSheet(chiName);
			// 枚举值表格
			XSSFSheet sheet1 = workbook.createSheet(tableName + "_ENUM");
			// 定义所需列数
			int columnNum = title.length;
			XSSFRow rowRowName = sheet.createRow(0); // 在索引1的位置创建行(最顶端的行开始的第一行)

			rowRowName.setHeight((short) (25 * 25)); // 设置高度
			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				XSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
				XSSFRichTextString text = new XSSFRichTextString(title[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				sheet.autoSizeColumn(n, true); 
			}

			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < objList.size(); i++) {

				Object[] obj = objList.get(i);// 遍历每个对象
				XSSFRow row = sheet.createRow(i + 1);// 创建所需的行数

				row.setHeight((short) (25 * 20)); // 设置高度

				for (int j = 0; j < obj.length; j++) {
					XSSFCell cell = null; // 设置单元格的数据类型
					cell = row.createCell(j, CellType.STRING);
					if (obj[j] != null && !"".equals(obj[j])) {
						cell.setCellValue(obj[j].toString()); // 设置单元格的值
					}
				}
			}
			// 指定索引列引用的数据位置,key值为需要设置数据有效新的索引，value为数据有效性值的引用位置
			Map<Integer, String> refAddressMap = new HashMap<Integer, String>();
			// 数据字典枚举值设置
			if (enumIndexMap != null){
				for (Integer index : enumIndexMap.keySet()) {
					// 创建所需的行数
					XSSFRow row = sheet1.createRow(index);
					// 设置高度
					row.setHeight((short) (25 * 20));
					// 此行所存入的数据字典枚举值
					String dictCode = enumIndexMap.get(index);
					// 需要存入枚举列表的值
					String[] dictArr = getDictCodeListCache(dictCode);
					if(dictArr==null) {
						continue;
					}
					int length = dictArr.length;
					// 向表格中写入值
					for (int j = 0; j < length; j++) {
						XSSFCell cell = null; // 设置单元格的数据类型
						cell = row.createCell(j, CellType.STRING);
						if (dictArr[j] != null && !"".equals(dictArr[j])) {
							cell.setCellValue(dictArr[j]); // 设置单元格的值
						}
					}
					String selectBoxRef = getSelectBoxRef(index + 1, length, tableName + "_ENUM");
					refAddressMap.put(index, selectBoxRef);
				}
			}

//			XSSFDataValidation validate = setBoxs(sheet, refAddressMap);
			setBoxs(sheet, refAddressMap,1);
//			sheet.addValidationData(validate);
			// 必须在单元格设值以后进行
            // 设置为根据内容自动调整列宽    
            for (int k = 0; k < columnNum; k++) {
                sheet.autoSizeColumn(k);
            }
            // 处理中文不能自动调整列宽的问题
            this.setSizeColumn(sheet, columnNum);
			if (workbook != null) {
				FileOutputStream out = null;
				try {
					File exportFile = new SafelyFile(fileName);
					// 本地文件夹不存在先创建文件夹 add by zcw
					File exportFilePaths = new SafelyFile(exportFilePath);
					if (!exportFilePaths.exists()) {
						exportFilePaths.mkdirs();
					}
					// 当配置参数为服务器路径时从新获取本地路径 add by zcw
					String saveFileDirPath = exportFile.getAbsolutePath();

					out = new FileOutputStream(saveFileDirPath);
					workbook.write(out);
					responseEntity = ExportUtil.export(exportFileName, exportFile, true);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} finally {
					if (out != null) {
						out.close();
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return responseEntity;
	}
	
	/**
	 * 设置操作类型一列的枚举值
	 * @return 枚举值方案
	 */
	private void setBoxs(Sheet sheet, Map<Integer, String> refAddressMap, int beginIndex) {
		CellRangeAddressList addressList = new CellRangeAddressList(beginIndex, MAX_SHEET_ROWS - 1, 0, 0);
		String[] dataList = new String[] {"导出", "新增", "修改", "删除"};
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);
		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
				.createExplicitListConstraint(dataList);
		XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
		validation.setSuppressDropDownArrow(true);
		validation.setShowPromptBox(true);
		sheet.addValidationData(validation);
		for (Integer index : refAddressMap.keySet()) {
			String formula = refAddressMap.get(index);
			CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(beginIndex, MAX_SHEET_ROWS - 1, index, index);
			XSSFDataValidationConstraint dvConstraint1 = (XSSFDataValidationConstraint) dvHelper.createFormulaListConstraint(formula);
			XSSFDataValidation validation1 = (XSSFDataValidation) dvHelper.createValidation(dvConstraint1, cellRangeAddressList);
			validation1.setSuppressDropDownArrow(true);
			validation1.setShowPromptBox(true);
			sheet.addValidationData(validation1);
		}
	}
	 /**
     * 导入文件之前检查
     *
     * @param fileName 导入的文件绝对路径
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> checkConfirmMsgForImportData(String tableName, String fileName, List<List<String>> originalDataList) {
    	String sheetName=tableName;
    	List<ExcelValidDto> checkResult = new ArrayList<>();
    	//先通过tableName获取CHB_MGRTABLE_INFO中对应的MGRTABLE_ID
    	ChbMgrTableInfo chbMgrTableInfo=chbMgrTableInfoMapper.getTableInfobyChiName(tableName);
    	if(null==chbMgrTableInfo){
    		checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "导入文件sheet名为："+ tableName +", 在配置表中定义未定义或者重复定义了！"));
    		return checkResult;
    	}
    	
//    	List<BaseInformationDto> tableInfoList = baseInformationMapper.queryTableInfoByTableName(tableName);
//    	if (StringUtil.isEmptyList(tableInfoList) || tableInfoList.size() > 1) {
//    		checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "导入文件sheet名为："+ tableName +", 在配置表中定义未定义或者重复定义了！"));
//    		return checkResult;
//    	}
    	// 导入文件相关表信息
    	//BaseInformationDto baseInformationDto = tableInfoList.get(0);
    	String tableId = chbMgrTableInfo.getMgrTableName();
    	String headerNum=chbMgrTableInfo.getHeaderNum();
    	tableName=chbMgrTableInfo.getMgrTableName();

//    	AjaxResult webResult = new AjaxResult();
    	if (!StringUtil.isEmptyList(originalDataList)) {
    		if (!fileName.toLowerCase().endsWith("xlsx")) {
    			checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "请导入2007以上版本的excel文件！"));
    			return checkResult;
//    			webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//	           	webResult.setMessage("请导入2007以上版本的excel文件！");
//	            return webResult;
    		}
//    		File file = new File(fileName);
//    		XSSFWorkbook xssfWorkBook = null;
//    		String tableId = null;
//    		// 获取导入表名
//			try {
//				xssfWorkBook = new XSSFWorkbook(new FileInputStream(file));
//				XSSFSheet xssfSheet = xssfWorkBook.getSheetAt(0);
//				tableId = xssfSheet.getSheetName();
//			} catch (FileNotFoundException e1) {
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
			if(CHB_PRD_DECLARE_SUB_TMP.equals(tableName)||
    				CHB_EMPLOYEE_INFO.equals(tableName)||
    					CHB_ASSET_INFO_LIST.contains(tableName)){
    			for(int i = 1; i < originalDataList.size(); i ++){
    				List<String> strList = originalDataList.get(i);
    				if (StringUtil.equals(CHB_PRD_DECLARE_SUB_TMP, tableName)){
    					String saleArea = strList.get(33) ;
    					//产品销售区域多选校验
    					if(!BaseCheckLib.inDatadict3(saleArea,DictCode.销售区域_中债.getValue(),true,true)){
    						checkResult.add(new ExcelValidDto(strList.get(2)+"|"+strList.get(64), String.valueOf(i + 1), "产品销售区域不在值域范围内并且多选选项不可重复。"
    								+ "[SALE_AREA="+saleArea+"]",
    								"各产品销售区域"));
    					}
    					String rskType = strList.get(47) ;
    					//投资者风险偏好多选校验
    					if(!BaseCheckLib.inDatadict3(rskType,DictCode.投资者风险偏好.getValue(),true,true)){
    						checkResult.add(new ExcelValidDto(strList.get(2)+"|"+strList.get(64), String.valueOf(i + 1), "投资者风险偏好不在值域范围内并且多选选项不可重复。"
    								+ "[RISK_TYPE="+rskType+"]",
    								"投资者风险偏好"));
    					}
    					String creditType = strList.get(60) ;
    					//产品增信机构类型多选校验
    					if(!BaseCheckLib.inDatadict3(creditType,DictCode.产品增信机构类型.getValue(),true,true)){
    						checkResult.add(new ExcelValidDto(strList.get(2)+"|"+strList.get(64), String.valueOf(i + 1), "产品增信机构类型不在值域范围内并且多选选项不可重复。"
    								+ "[CREDIT_TYPE="+creditType+"] ",
    								"产品增信机构类型"));
    					}
    					//境外托管机构国别多选校验
						String outDepCity=strList.get(45) ;
    					if(StringUtil.isNotEmpty(outDepCity)){
							List<String> outDepCitys = Arrays.asList(outDepCity.split(","));
							String investArae=strList.get(18) ;//资金投向地区
							if(outDepCitys.size()>1 && StringUtil.equals(investArae,DictCodeListExt2.资金投向地区_境外.getValue()+" "+DictCodeListExt2.资金投向地区_境外.getCodeName())){
								if(outDepCitys.contains("CHN")){
									checkResult.add(new ExcelValidDto(strList.get(2)+"|"+strList.get(64), String.valueOf(i + 1), "当“资金投向地区”要素选择“02 境外”时,境外托管机构国别不能填写中国。"
											+ "[OUT_DEP_CTRY="+"CHN"+"] ",
											"境外托管机构国别"));
								}
							}
						}
    				}else if (StringUtil.equals(CHB_EMPLOYEE_INFO, tableName)){
    					String FinProfessionalCrtf = strList.get(16) ;
    					//理财专业证书
    					if(!BaseCheckLib.inDatadict3(FinProfessionalCrtf,DictCode.理财专业证书.getValue(),true,true)){
    						checkResult.add(new ExcelValidDto(strList.get(1)+"|"+strList.get(5), String.valueOf(i + 1), "理财专业证书分类不在值域范围内且多选选项不可重复。"
    								+ "[REG_BUSI_TYPE="+FinProfessionalCrtf+"]",
    								"理财专业证书"));
    					}
    					String REGBUSITYPE = strList.get(22) ;
    					//登记业务分类多选校验
    					if(!BaseCheckLib.inDatadict3(REGBUSITYPE,DictCode.登记业务分类.getValue(),true,true)){
    						checkResult.add(new ExcelValidDto(strList.get(1)+"|"+strList.get(5), String.valueOf(i + 1), "登记业务分类不在值域范围内且多选选项不可重复。"
    								+ "[REG_BUSI_TYPE="+REGBUSITYPE+"]",
    								"登记业务分类"));
    					}
    					String REGEMPLOYEETYPE = strList.get(23);
    					//登记人员类别
    					if(!BaseCheckLib.inDatadict3(REGEMPLOYEETYPE,DictCode.登记人员类别.getValue(),true,true)){
    						checkResult.add(new ExcelValidDto(strList.get(1)+"|"+strList.get(5), String.valueOf(i + 1), "登记人员类别不在值域范围内且多选选项不可重复。"
    								+ "[REG_EMPLOYEE_TYPE="+REGEMPLOYEETYPE+"]",
    								"登记人员类别"));
    					}
    				}
//    				else if (CHB_ASSET_INFO_LIST.contains(tableName)){
//    					String TARGETTYPE = strList.get(14) ;
//    					//产品销售区域多选校验
//    					if(!BaseCheckLib.inDatadict3(TARGETTYPE,DictCode.标的类别.getValue(),true,true)){
//    						checkResult.add(new ExcelValidDto(strList.get(2)+"|"+strList.get(275), String.valueOf(i + 2), "结构性存款挂钩标的类别不在值域范围内并且多选选项不可重复。"
//    								+ "["+TARGETTYPE+"]",
//    								"结构性存款挂钩标的类别"));
//    					}
//    					String TARGETTYPE3 = strList.get(126) ;
//    					//投资者风险偏好多选校验
//    					if(!BaseCheckLib.inDatadict3(TARGETTYPE3,DictCode.标的类别.getValue(),true,true)){
//    						checkResult.add(new ExcelValidDto(strList.get(2)+"|"+strList.get(275), String.valueOf(i + 2), "标的类别不在值域范围内并且标的类别的多选选项不可重复。"
//    								+ "["+TARGETTYPE3+"]",
//    								"标的类别"));
//    					}
//    					
//    					String CHBSECTYPE03 = strList.get(18) ;
//    					//具体类别校验
//    					if(!BaseCheckLib.inDatadict3(CHBSECTYPE03,DictCode.具体类别.getValue(),true,false)){
//    						checkResult.add(new ExcelValidDto(strList.get(2)+"|"+strList.get(275), String.valueOf(i + 2), "具体类别不在值域范围内并且具体类别的不可多选。"
//    								+ "["+CHBSECTYPE03+"]",
//    								"具体类别"));
//    					}
//    				}
    			}
    		}
            // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 start
    		// 所有字段集合
            List<String> allColumnList = baseInformationMapper.queryAllColumns(tableId);
            // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 end
    		// 业务主键集合
        	List<String> bisinessPkList = baseInformationMapper.queryBisinessPkListByTable(tableId);
        	 // 对应表日期类型字段集合
            List<String> dateColumnList =  baseInformationMapper.queryDateColumnList(tableName);
    		List<List<String>> dataList = new ArrayList<List<String>>();
    		if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
    			dataList = handleAssetData(originalDataList, tableId, bisinessPkList, dateColumnList);
    		} else {
    			dataList = handleData(originalDataList, tableId,true);
    		}

			List<BaseInformationDto> baseInformationList = baseInformationMapper.queryBaseInfoByTableId(tableId);

			// 检查导入表是否定义在配置表中
			if (StringUtil.isEmptyList(baseInformationList)) {
				checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "导入文件指定的表"+ tableId +"未在配置表中定义！"));
				return checkResult;
//				webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//	           	webResult.setMessage("导入文件指定的表"+ tableId +"未在配置表中定义！");
//	            return webResult;
			}
            if (null != dataList && dataList.size() > 0) {
            	// 错误列名集合
            	List<String> errorLists = new LinkedList<String>();
            	// 文件列名集合
            	List<String> excelHeadList = dataList.get(0);
            	// 文件第一行没有数据
            	if(StringUtil.isEmptyList(excelHeadList)) {
            		checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "文件没有列名！"));
            		return checkResult;
//            		webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	           	webResult.setMessage("文件没有列名！");
//    	            return webResult;
            	}
            	// 数据库中表的列集合
            	List<String> columnTypeList = columnTypeMapper.queryAllColumnByTableId(tableName);
            	for (String excelHead : excelHeadList) {
            		if ("操作类型".equals(excelHead)) {
            			continue;
            		}
            		if (!columnTypeList.contains(excelHead)) {
            			errorLists.add(excelHead);
            		}
            	}
            	// 文件中存在数据库中没有的字段
            	if (errorLists.size() > 0) {
            		checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0),
            				"表" + tableName + "中无以下字段：" + StringUtil.joinString(errorLists, GlobalConstant.COMMA)));
            		return checkResult;
//            		webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	           	webResult.setMessage("表" + tableId + "中无以下字段：" + StringUtil.joinString(errorLists, GlobalConstant.COMMA)
//    	           		+ ", 请检查导入文件");
//    	            return webResult;
            	}

            	if (null != bisinessPkList && bisinessPkList.size() > 0) {
            		bisinessPkList.forEach(bisinessPk -> {
            			if (!excelHeadList.contains(bisinessPk)) {
            				errorLists.add(bisinessPk);
            			}
            		});
				}
            	// 文件缺少业务主键
            	if (errorLists.size() > 0) {
            		checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "导入文件中缺少以下业务主键：" + StringUtil.joinString(errorLists, GlobalConstant.COMMA)));
            		return checkResult;
//            		webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	           	webResult.setMessage("导入文件中缺少以下业务主键：" + StringUtil.joinString(errorLists, GlobalConstant.COMMA));
//    	            return webResult;
            	}
            	List<Map<String, String>> listDupError = new ArrayList<Map<String,String>>();
//            	List<String> dupErrorStrList = new LinkedList<String>();
            	// 解析出的文件转换为Map集合
            	List<Map<String, String>> dataMapList = new ArrayList<Map<String,String>>();
            	List<Map<String, String>> pkdataMapList = new ArrayList<Map<String,String>>();
            	// 对应表日期类型字段集合
//                List<String> dateColumnList =  baseInformationMapper.queryDateColumnList(tableId);
            	// 主键相关数据集合,用来检查新增数据是否和数据库数据有重复
//            	List<Map<String, String>> pkDataMapList = new ArrayList<Map<String,String>>();
            	List<String> columnList = dataList.get(0);
            	int size = columnList.size();
            	// 存储业务主键对应的索引
            	List<Integer> pkIndex = new ArrayList<Integer>();
            	for (int i = 1; i < size; i ++) {
            		if (bisinessPkList.contains(columnList.get(i))) {
            			pkIndex.add(i);
            		}
            	}
//            	// 存储有空值的业务主键
//            	List<String> noDataPkList = new LinkedList<String>();
//            	// 跳出循环标志
//            	boolean breakFlag = true;
//            	for (int i = 0; i < dataList.size() && breakFlag; i ++) {
//            		List<String> strList = dataList.get(i);
//            		for (int j = 0; j < pkIndex.size() && breakFlag; j ++) {
//            			String data = strList.get(pkIndex.get(j));
//            			// 有业务主键字段没存值,结束循环并且提示用户
//            			if (StringUtils.isEmpty(data)) {
//            				noDataPkList.add(columnList.get(j));
//            				breakFlag = false;
//            			}
//            		}
//            	}
//            	// 业务主键有空值
//            	if (noDataPkList.size() > 0) {
//            		webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	           	webResult.setMessage("导入文件中以下业务主键存在空值：" + StringUtil.joinString(noDataPkList, GlobalConstant.COMMA));
//    	            return webResult;
//            	}

            	List<SysDataDict> secondIndustryList = tamsBaseMapper.queryFirstIndustryBySecondIndustry();
            	Map<String, String> secondIndustryMap = new HashMap<String, String>();
            	for (SysDataDict sysDataDict : secondIndustryList) {
            		secondIndustryMap.put(sysDataDict.getItemCode(), sysDataDict.getLinkedItemCode());
            	}
            	for (int j = 0; j < dataList.size(); j ++) {
					/**定义当有新增修改和删除的数据时 的标识  add  by dongxiangyang  20201028  start*/
					boolean   isContans=false;
					/**定义当有新增修改和删除的数据时 的标识  add  by dongxiangyang  20201028  end*/
//            	for (List<String> strList : dataList) {
            		List<String> strList = dataList.get(j);
            		if ("操作类型".equals(strList.get(0))) {
            			continue;
            		}
            		Map<String, String> dataMap = new HashMap<String, String>();
            		// 新增状态数据需要和数据库数据核对是否有主键冲突
					// add by zhangliang 20201026 修改删除的时候需要判断是否是否存在 start
            		if ("新增".equals(strList.get(0)) || StringUtil.equals(strList.get(0), "修改") || StringUtil.equals(strList.get(0), "删除")|| StringUtil.equals(strList.get(0), "导出")) {
            			Map<String, String> pkDataMap = new HashMap<String, String>();
            			Map<String, String> pkData = new HashMap<String, String>();

            			pkIndex.forEach(index -> {
            				String key = columnList.get(index);
            				String value = "";
            				// 日期类型字段处理
            				if (dateColumnList.contains(key)) {
            					String dateStr = formatDateStr(strList.get(index));
            					if (null != dateStr) {
            						value = "DATE'" + dateStr + "'";
            					} else {
            						value = "";
            					}
            				} else {
            					value = "'" + strList.get(index) + "'";
            					pkData.put(key, strList.get(index));
            				}
            				pkDataMap.put(key, value);
            			});
            			pkdataMapList.add(pkData);
            			if (pkDataMap.size() > 0) {
                    		/** start 添加模板导入A列为导出时,应该提示“不要使用“操作类型”为“导出”的进行导入 add by  dongxiangyang 20201028*/
            				if(StringUtil.equals(strList.get(0), "导出")){
            					int rowNum = j + 1;
            					if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
            						rowNum = j + 2;
            					}
            					List<String> strList1 = new ArrayList<String>();
            					for (String key : pkDataMap.keySet()) {
            						strList1.add(pkDataMap.get(key));
            					}
            					checkResult.add(new ExcelValidDto(StringUtil.joinString(strList1, GlobalConstant.COMMA), String.valueOf(rowNum), "不要使用“操作类型”为“导出”的进行导入"));
            					isContans=true;
            				}
                    		/** end 添加模板导入A列为导出时,应该提示“不要使用“操作类型”为“导出”的进行导入 add by  dongxiangyang 20201028*/
            				boolean flag = checkSingleData(pkDataMap, tableName);
            				if (flag && StringUtil.equals(strList.get(0), "新增")) {
            					int rowNum = j + 1;
            					if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
            						rowNum = j + 2;
            					}
            					List<String> strList1 = new ArrayList<String>();
            					for (String key : pkDataMap.keySet()) {
            						strList1.add(pkDataMap.get(key));
            					}
            					checkResult.add(new ExcelValidDto(StringUtil.joinString(strList1, GlobalConstant.COMMA), String.valueOf(rowNum), "新增数据已存在"));
//            				    return  checkResult;
            					isContans=true;
            				}
            				else if (!flag && (StringUtil.equals(strList.get(0), "修改") || StringUtil.equals(strList.get(0), "删除"))){
								int rowNum = j + 1;
								if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
									rowNum = j + 2;
								}
								List<String> strList1 = new ArrayList<String>();
								for (String key : pkDataMap.keySet()) {
									strList1.add(pkDataMap.get(key));
								}
								checkResult.add(new ExcelValidDto(StringUtil.joinString(strList1, GlobalConstant.COMMA), String.valueOf(rowNum), strList.get(0) + "数据不存在"));
//            				    return  checkResult;
            					isContans=true;
            				}
//            				pkDataMapList.add(pkDataMap);
            			}
            		}
					// add by zhangliang 20201026 修改删除的时候需要判断是否是否存在 end
            		// 资产要素导入数据不全标识
//        			boolean assetInfoFlag = true;


        			int beginIndex = 0;
            		int endIndex = 0;
            		String assetType = "";
            		// 资产要素表数据处理
            		if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
//            			String dataPara = "";
//            			for (int i = 7; i <= 274; i ++) {
//                			String str = strList.get(i);
//                			if (StringUtil.isNotEmpty(str)) {
//                				dataPara = queryAssetType(i);
//                				break;
//                			}
//                		}
//            			if (StringUtil.isEmpty(dataPara)) {
//            				checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(j + 2), "资产要素导入需要填写资产/负债类别相关信息"));
////            				webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
////            	           	webResult.setMessage("资产要素导入需要填写资产/负债类别相关信息");
////            	            return webResult;
//                		} else {
//                			String[] dataParaArr = dataPara.split(":");
//                			beginIndex = Integer.parseInt(dataParaArr[0].split("-")[0]);
//                			endIndex = Integer.parseInt(dataParaArr[0].split("-")[1]);
//                			assetType = dataParaArr[1];
//                		}
            			
            			//更换获取assetType的方法，根据导入文件的资产/负债类别来获取
            			String assetType2=strList.get(4);
            			if(assetMap.get(assetType2)!=null){
            				String[] dataParaArr = assetMap.get(assetType2).split(":");
                			beginIndex = Integer.parseInt(dataParaArr[1].split("-")[0]);
                			endIndex = Integer.parseInt(dataParaArr[1].split("-")[1]);
                			assetType = dataParaArr[0];
            			}
            		}else if(CHB_ASSET_INFO_LIST.contains(tableName)&&"1".equals(headerNum)){
            			//单表头的资产基本信息
            			assetType=nameMap.get(sheetName);
            		}else if (StringUtil.equals(CHB_LOAN_DETAIL_INFO, tableName)){
						String loantype = strList.get(2) ;
						if(StringUtil.isNotEmpty(loantype) && !StringUtil.equals(loantype, DictCodeListExt3.中债_中债贷款分类_除回购或拆借外贷款.getValue())){
							try {
								int each = 0;
								String eachProportion = strList.get(4) ;
								for(String code : eachProportion.split(":")){
									each = each + Integer.parseInt(code);
								}
								if(each!=100){
									checkResult.add(new ExcelValidDto(strList.get(1), String.valueOf(j + 1), "各比例分割后合计必须等于100",
											"各比例"));
								}
							}catch(Exception e){
								checkResult.add(new ExcelValidDto(strList.get(1), String.valueOf(j + 1),"各比例分割后合计必须等于100",
										"各比例"));
							}
						}
					//申报登记多选数据处理
					}
            		List<Integer> indexList = new ArrayList<Integer>();
            		if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
            			for (int i = 1; i < 7; i ++) {
            				indexList.add(i);
            			}
            			for (int i = beginIndex; i <= endIndex; i ++) {
            				indexList.add(i);
            			}
            			for (int i = 283; i < size; i ++) {
            				indexList.add(i);
            			}
            		} else {
            			for (int i = 1; i < size; i ++) {
            				indexList.add(i);
            			}
            		}

        			for (Integer i : indexList) {
//            		for (int i = 1; i < size; i ++) {
            			if (StringUtil.isEmpty(dataMap.get(columnList.get(i)))) {
            				dataMap.put(columnList.get(i), strList.get(i));
            			}
//            			if (CHB_ASSET_INFO.equals(tableId) && i > 6 && i < 232 && StringUtil.isNotEmpty(strList.get(i))) {
//            				assetInfoFlag = false;
//            			}
            		}
        			if (CHB_ASSET_INFO_LIST.contains(tableName)) {
        				dataMap.put("ASSET_TYPE", assetType);
        				dataMap.put("STATUS", "E");
        				// 行业二级分类
        				String industryShares2 = dataMap.get("INDUSTRY_SHARES2");
        				if (StringUtil.isNotEmpty(industryShares2)) {
        					String industryShares1 = secondIndustryMap.get(industryShares2);
        					dataMap.put("INDUSTRY_SHARES", industryShares1);
        				}
        				String industryLevel2 = dataMap.get("INDUSTRY_LEVEL2");
        				if (StringUtil.isNotEmpty(industryLevel2)) {
        					String industry = secondIndustryMap.get(industryLevel2);
        					dataMap.put("INDUSTRY", industry);
        				}
        				String industryLevel2Type = dataMap.get("INDUSTRY_LEVEL2_TYPE");
        				if (StringUtil.isNotEmpty(industryLevel2Type)) {
        					String industry1 = secondIndustryMap.get(industryLevel2Type);
        					dataMap.put("INDUSTRY1", industry1);
        					dataMap.put("INDUSTRY_PBC", industry1);
        				}
        				String proIndustryLevel2 = dataMap.get("PRO_INDUSTRY_LEVEL2");
        				if (StringUtil.isNotEmpty(proIndustryLevel2)) {
        					String proIndustry = secondIndustryMap.get(proIndustryLevel2);
        					dataMap.put("PRO_INDUSTRY", proIndustry);
        				}
        				String capUseInduLevel2 = dataMap.get("CAP_USE_INDU_LEVEL2");
        				if (StringUtil.isNotEmpty(capUseInduLevel2)) {
        					String capUseIndu = secondIndustryMap.get(capUseInduLevel2);
        					dataMap.put("CAP_USE_INDU", capUseIndu);
        				}
        				//獲取remark
            			String remark=strList.get(CAI_BASE_COUNT);
    					dataMap.put("REMARK", remark);
        			}
//            		if (assetInfoFlag) {
//            			webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//        	           	webResult.setMessage("资产要素导入需要填写资产/负债类别相关信息");
//        	            return webResult;
//            		}
        			// add by dubaojun 2020-10-15 删除不存在的字段，防止报错 start
        			Set<String> keySet = new HashSet<>(dataMap.keySet());
        			for (String key : keySet) {
        			    if(!allColumnList.contains(key)) {
        			        dataMap.remove(key);
        			    }
                    }
        			// add by dubaojun 2020-10-15 删除不存在的字段，防止报错 end

					dataMap.put("ERRMSG_ROW", String.valueOf(j));

					/**A列为删除列的  不进行规则代码check了  add by dongxiangyang  20201029*/
					//当有新增和修改或者删除的时候已经存在时,返回检查结果,不进行下面的检查
					if(!StringUtil.equals(strList.get(0), "删除") || isContans){
	            		dataMapList.add(dataMap);
					}
					/**A列为删除列的  不进行规则check了       add by  dongxiangyang  20201029 */
            	}
            	/*add by xuyang 底层持仓数据导入，新增资产编码是否存在资产要素校验，不存在，不允许导入 start*/
				if(tableId.equals("CHB_BOT_AST_POS")){
					int rowNum=0;
					if(dataList.get(0).get(4).equals("ASSET_CODE")){
						checkResult.addAll(operationalLoanInfo001CheckService.checkImportBotAstPosData(dataMapList,bisinessPkList));
					}else{
						checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(rowNum),"导入底层资产持仓数据模板不正确"));
					}
				}else if (CHB_PRD_LIST.contains(tableName)){
					//导入到TMP表，产品发行登记、产品募集总量登记、产品存续期登记、产品终止登记、交易信息登记导入时，增加代码检查，判断产品登记编码是否存在（关联产品登记编码表），若不存在则不允许导入并给出提示：产品登记编码不存在！
					checkResult.addAll(operationalLoanInfo001CheckService.checkImportPrdData(pkdataMapList,bisinessPkList,tableName));
				}
				/*add by xuyang 底层持仓数据导入，新增资产编码是否存在资产要素校验，不存在，不允许导入 end*/
            	// 数据重复性检查
            	// 新增数据是否和数据库已有数据重复
//            	List<String> existDataList = checkDataIsExist(pkDataMapList, tableId);
//            	if (null != existDataList && existDataList.size() > 0) {
//            		webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	           	webResult.setMessage("导入文件中以下业务主键数据和数据库重复：" + StringUtil.joinString(existDataList, GlobalConstant.COMMA));
//    	            return webResult;
//            	}
            	List<String> dataObjectList = baseInformationMapper.querydataObjectByTable(tableName);
            	String dataObject = dataObjectList.get(0);
            	if (null != bisinessPkList && bisinessPkList.size() > 0) {
            		// 业务主键数组
            		String[] bisinessPkArray = (String[]) bisinessPkList.toArray(new String[0]);
            		listDupError = DataCheckService.checkDuplicatedData(dataMapList, bisinessPkArray);
//            		listDupError.forEach(dupError -> {
//            			bisinessPkList.forEach(bisinessPk -> {
//            				dupErrorStrList.add(bisinessPk + "=" + dupError.get(bisinessPk));
//            			});
//            		});
            		// 没有定义业务主键,检查所有数据
            	} else {
            		listDupError = DataCheckService.checkDuplicatedData(dataMapList);
//            		listDupError.forEach(dupError -> {
//            			for (String key : dupError.keySet()) {
//            				dupErrorStrList.add(key + "=" + dupError.get(key));
//            			}
//            		});
            	}
            	// 业务主键重复
            	if (listDupError.size() > 0) {
            		for (Map<String, String> map : listDupError) {
            			/**start    添加错误提示"标识码"字段     add by  dongxiangyang 20201026 */
            			String  vailkey=getBusinessKeyStr(bisinessPkList, map);
            			/**end    添加错误提示"标识码"字段     add by  dongxiangyang 20201026 */
            			String indexStr = map.get("ERRMSG_ROW");
            			int index = 0;
            			if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
            				index = Integer.parseInt(indexStr) + 2;
            			} else {
            				index = Integer.parseInt(indexStr) + 1;
            			}
            			checkResult.add(new ExcelValidDto(vailkey, String.valueOf(index), "主键数据重复"));
            		}
//            		webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	           	webResult.setMessage("导入文件中以下业务主键数据重复：" + StringUtil.joinString(dupErrorStrList, GlobalConstant.COMMA));
//    	            return webResult;
            	}
            	List<String>checkColumnList=chbInfoMgrResultConfigMapper.getAllColumnByTableId(tableId);

            	// 已配置规则检查
            	//List<CheckRule> listRules = dataCheckService.queryCheckRules(dataObject);
				// add by zhangliang 20210418 TMP调用SUB校验配置 start
            	List<CheckRule> listRules = dataCheckService.queryCheckRulesByDataTypeAndColumns(ChbCheckTableInfo.genDataType(dataObject));
				// add by zhangliang 20210418 TMP调用SUB校验配置 end
                DataCheckService.checkDataList(dataMapList,listRules, false);

                // add by zhangliang 20210127 添加代码校验 start
				ChbCheckTableInfo chbCheckTableInfo = ChbCheckTableInfo.genChbCheckTableInfo(tableId);
				if (chbCheckTableInfo != null){
					commonCodeCheckBatchService.codeCheckCommonOnly(dataMapList, chbCheckTableInfo);
				}
				// add by zhangliang 20210127 添加代码校验 end
//                listError.forEach(errorMap -> {
//                	errorLists.add(errorMap.get("ERRMSG"));
//                });
                // 文件中有不符合规则配置的数据
            	if (dataMapList.size() > 0) {
            		for (Map<String, String> map : dataMapList) {
            			if(StringUtil.isEmpty(map.get("ERRMSG"))){
            				continue;
						}

//业务主键凭借  List<String> bisinessPkList
            			/**start    添加错误提示"标识码"字段     add by  dongxiangyang 20201026 */
            			String  vailkey=getBusinessKeyStr(bisinessPkList, map);
            			/**end    添加错误提示"标识码"字段     add by  dongxiangyang 20201026 */
            			String indexStr = map.get("ERRMSG_ROW");
            			int index = 0;
            			if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
            				index = Integer.parseInt(indexStr) + 2;
            			} else {
            				index = Integer.parseInt(indexStr) + 1;
            			}
            			checkResult.add(new ExcelValidDto(vailkey, String.valueOf(index), map.get("ERRMSG")));
            		}
//            		webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	           	webResult.setMessage("以下数据不满足已配置规则：" + StringUtil.joinString(errorLists, GlobalConstant.COMMA)
//    	           		+ ", 请检查导入文件");
//    	            return webResult;
            	}
            }
    	}
//    	webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
//    	return webResult;
//    	checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "请导入2007以上版本的excel文件！"));
    	// add by zhangliang 20201024 合并显示错误信息  start
    		List<ExcelValidDto> resResult = new ArrayList<>();
    		if (!StringUtil.isEmptyList(checkResult)){
    				Map<String, List<ExcelValidDto>> rowNumListMap = checkResult.stream().collect(JoyinCollectors.groupingBy(x -> x.getRowNum()));
    				for (String rowNum : rowNumListMap.keySet()){
    					List<ExcelValidDto> rowNumList= rowNumListMap.get(rowNum);
    					String errorMsg = "";
    					String validKey = StringUtil.EMPTY;
    					StringBuffer sb = new StringBuffer();
    					for (ExcelValidDto validDto : rowNumList){
    						sb.append(validDto.getErrorInfo());
    						sb.append(GlobalConstant.VERTICAL_LINE);

    						if (StringUtil.isNotEmpty(validDto.getValidKey()) && validDto.getValidKey().length() > validKey.length()){
    							validKey = validDto.getValidKey();
    						}
    					}
    					if (sb.length() > 0){
    						errorMsg = sb.substring(0, sb.length() - GlobalConstant.VERTICAL_LINE.length());
    					}
    					resResult.add(new ExcelValidDto(validKey, rowNum, errorMsg));
    				}

    				resResult = resResult.stream().sorted((validDto1, validDto2) ->
    						{if(Integer.parseInt(validDto1.getRowNum()) < Integer.parseInt(validDto2.getRowNum())){
    							return -1;
    						}
    							return 1;
    						}).collect(Collectors.toList());
    			}
    			// add by zhangliang 20201024 合并显示错误信息  end
    	return resResult;
    }


    /**
     * 根据索引位置判断资产要素表ASSET_TYPE
     * @Description TODO
     * @Author      高健
     * @Date        2019年12月7日 下午4:50:11
     * @param @param index
     * @param @return 参数
     * @return String 返回类型
     * @throws
     */
    private String queryAssetType(int index) {
    	String assetType = "";
    	for (String key : headerMap.keySet()) {
    		String[] indexArr = key.split("-");
    		int beginIndex = Integer.parseInt(indexArr[0]);
    		int endIndex = Integer.parseInt(indexArr[1]);
    		if (index >= beginIndex && index <= endIndex) {
    			String assetTypeName = headerMap.get(key);
    			assetType = key + ":" + assetTypeName;
				break;
    		}
    	}
    	return assetType;
    }

    private boolean checkSingleData(Map<String, String> pkDataMap, String tableId) {
    	boolean flag = false;
    	/**添加异常捕获， start by dongxiangyang  20201026 */
    	try
    	{int count = baseInformationMapper.checkDataIsExist(pkDataMap, tableId);
    	if (count > 0) {
    		flag = true;
    	}
    	}catch(Exception e){
    		return flag;
    	}
    	/**添加异常捕获， end by dongxiangyang  20201026 */

    	return flag;
    }
    
    /**
     * 检查传入数据和指定表的数据是否主键冲突并返回主键冲突数据
     * @param pkDataMapList 传入数据集合
     * @param tableId 待检查表
     * @return 主键冲突数据集合
     */
    private List<String> checkDataIsExist(List<Map<String, String>> pkDataMapList, String tableId) {
    	List<String> existDataList = new ArrayList<String>();
    	pkDataMapList.forEach(pkDataMap -> {
    		int count = baseInformationMapper.checkDataIsExist(pkDataMap, tableId);
    		if (count > 0) {
    			StringBuilder stringBuilder = new StringBuilder();
    			for (String key : pkDataMap.keySet()) {
    				stringBuilder.append(key + "=" + pkDataMap.get(key) + ",");
    			}
    			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    			if (!existDataList.contains(stringBuilder.toString())) {
    				existDataList.add(stringBuilder.toString());
    			}
    			
    		}
    		
    	});
    	return existDataList;
    }
    
    /**
     * 数据导入
     * @param fileName 文件名
     * @return 导入成功条数
     */
    public AjaxResult importData(String tableName,String userName,List<List<String>> originalDataList) {
    	String sheetName=tableName;
    	int count = 0;
    	AjaxResult webResult = new AjaxResult();
    	//List<BaseInformationDto> tableInfoList = baseInformationMapper.queryTableInfoByTableName(tableName);
    	// 导入文件相关表信息
    	//BaseInformationDto baseInformationDto = tableInfoList.get(0);
    	//String tableId = baseInformationDto.getBaseTableId();
    	
    	ChbMgrTableInfo chbMgrTableInfo=chbMgrTableInfoMapper.getTableInfobyChiName(tableName);
    	String tableId = chbMgrTableInfo.getMgrTableName();
    	String headerNum=chbMgrTableInfo.getHeaderNum();
    	tableName=chbMgrTableInfo.getMgrTableName();
    	
    	if (originalDataList!=null&&!originalDataList.isEmpty()) {
            // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 start
            // 所有字段集合
            List<String> allColumnList = baseInformationMapper.queryAllColumns(tableId);
            // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 end
    		// 导入表业务主键集合
            List<String> bisinessPkList = baseInformationMapper.queryBisinessPkListByTable(tableId);
            // 对应表日期类型字段集合
            List<String> dateColumnList =  baseInformationMapper.queryDateColumnList(tableName);
    		List<List<String>> dataList = new ArrayList<List<String>>();
    		if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
    			dataList = handleAssetData(originalDataList, tableId, bisinessPkList, dateColumnList);
    		} else {
    			dataList = handleData(originalDataList, tableId,true);
    		}
//			List<List<String>> dataList = handleData(originalDataList, tableId);
            // 导入表主键集合
            List<String> primarykeyList = baseInformationMapper.queryPrimarykeyByTableName(tableId);

            // 需要修改的数据集合
            List<HashMap<String, String>> dataInsertList = new ArrayList<HashMap<String,String>>();
            // 需要删除的数据集合
            List<HashMap<String, String>> dataDeleteList = new ArrayList<HashMap<String,String>>();
            // 需要修改的数据集合
            List<HashMap<String, String>> dataUpdateList = new ArrayList<HashMap<String,String>>();
            // 对应表时间类型字段集合
            List<String> timeColumnList =  baseInformationMapper.queryTimeColumnList(tableName);
            List<SysDataDict> secondIndustryList = tamsBaseMapper.queryFirstIndustryBySecondIndustry();
        	Map<String, String> secondIndustryMap = new HashMap<String, String>();
        	for (SysDataDict sysDataDict : secondIndustryList) {
        		secondIndustryMap.put(sysDataDict.getItemCode(), sysDataDict.getLinkedItemCode());
        	}
            if (null != dataList && dataList.size() > 1) {
            	List<String> columnList = dataList.get(0);
            	//----add by lxq on 2020-07-15 for 导入后更新人以及时间未更新(QDXPJG-229) ----->>>>>
            	if(!columnList.contains("CREATE_USER")){
                	columnList.add("CREATE_USER");
            	}
            	if(!columnList.contains("UPDATE_USER")){
            		columnList.add("UPDATE_USER");
            	}
            	if(!columnList.contains("UPDATE_TIME")){
            		columnList.add("UPDATE_TIME");
            	}
            	//----add by lxq on 2020-07-15 for 导入后更新人以及时间未更新(QDXPJG-229) -----<<<<<
            	int size = columnList.size();
            	for(int  j=1;j<dataList.size();j++){
            		List<String>	strList = dataList.get(j);
                	//----add by lxq on 2020-07-15 for 导入后更新人以及时间未更新(QDXPJG-229) ----->>>>>
            		strList.add("admin");//创建人
            		strList.add("admin");//更新人
                    if(BaseCommonCheckService.isMYSQLDataBase()){
                        strList.add(DateUtil.getCurrentDate());//更新时间
                    }else{
                        strList.add("SYSDATE");
                    }
                	//----add by lxq on 2020-07-15 for 导入后更新人以及时间未更新(QDXPJG-229) -----<<<<<
            		int beginIndex = 0;
            		int endIndex = 0;
            		String assetType = "";
            		// 资产要素表数据处理
            		if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
//            			String dataPara = "";
//            			for (int i = 7; i <= 274; i ++) {
//                			String str = strList.get(i);
//                			if (StringUtil.isNotEmpty(str)) {
//                				dataPara = queryAssetType(i);
//                				break;
//                			}
//                		}
//            			if (StringUtil.isEmpty(dataPara)) {
//                			continue;
//                		}
//            			String[] dataParaArr = dataPara.split(":");
//                		beginIndex = Integer.parseInt(dataParaArr[0].split("-")[0]);
//                		endIndex = Integer.parseInt(dataParaArr[0].split("-")[1]);
//                		assetType = dataParaArr[1];
            			
            			String assetType2=strList.get(4);
            			String[] dataParaArr = assetMap.get(assetType2).split(":");
            			beginIndex = Integer.parseInt(dataParaArr[1].split("-")[0]);
            			endIndex = Integer.parseInt(dataParaArr[1].split("-")[1]);
            			assetType = dataParaArr[0];
            		
            		}else if(CHB_ASSET_INFO_LIST.contains(tableName)&&"1".equals(headerNum)){
            			//单表头的资产基本信息
            			assetType=nameMap.get(sheetName);
            		}
            		List<Integer> indexList = new ArrayList<Integer>();
            		if (CHB_ASSET_INFO_LIST.contains(tableName)&&"2".equals(headerNum)) {
            			for (int i = 1; i < 7; i ++) {
            				indexList.add(i);
            			}
            			for (int i = beginIndex; i <= endIndex; i ++) {
            				indexList.add(i);
            			}
            			for (int i = 283; i < size; i ++) {
            				indexList.add(i);
            			}
            		} else {
            			for (int i = 1; i < size; i ++) {
            				indexList.add(i);
            			}
            		}

            		// 新增数据Map
            		HashMap<String, String> insertMap = new HashMap<String, String>();
            		// 删除数据Map
            		HashMap<String, String> deleteMap = new HashMap<String, String>();
            		// 修改数据Map
            		HashMap<String, String> updateMap = new HashMap<String, String>();
            		switch(strList.get(0)) {
            		case "新增" :
//            			for (int i = 1; i < size; i ++) {
            			for (Integer i : indexList) {
            				String column = columnList.get(i);
            				String columnData = strList.get(i);
            				//资产要素放置后续空覆盖前有有值选项
            				if(i>=CAI_BASE_COUNT&&StringUtil.isNotEmpty(insertMap.get(column))
            						&&StringUtil.isEmpty(columnData)
            						&&CHB_ASSET_INFO_LIST.contains(tableName)){
            					continue;
            				}
            				//资产名称覆盖问题修复，防止被覆盖
            				if("ASSET_NAME".equals(column)&&StringUtil.isEmpty(columnData)){
            					continue;
            				}
            				// 数据状态定为E
            				if ("STATUS".equals(column)) {
            					columnData = "'" + "E" + "'";
            					// 新增操作主键需要单独生成(非组合主键)
            				} else if (null != primarykeyList && primarykeyList.contains(column)) {
            					columnData = "'" + UUID.randomUUID().toString()+ "'";
            					// 日期类型字段处理
            				} else if (dateColumnList.contains(column) && !("").equals(columnData)) {
            					// 特殊情况,CREATE_TIME和UPDATE_TIME为DATE类型
            					if ("CREATE_TIME".equals(column) || "UPDATE_TIME".equals(column)) {
                                    if(BaseCommonCheckService.isMYSQLDataBase()){
                                        columnData =  "'" + DateUtil.formatDateTime(DateUtil.now())+ "'";
                                    }else{
                                        columnData = "SYSDATE";
                                    }
            					} else {
            					    if(columnData==null||("").equals(columnData)){
                                        columnData = null;
                                    }else {
                                        //excel中输入了时间戳数据处理
                                        if (columnData.length() > 10) {
                                            columnData = columnData.substring(0, 10);
                                        }
                                        String dateStr = formatDateStr(columnData);
                                        if (null != dateStr) {
                                            columnData = "DATE'" + dateStr + "'";
                                        } else {
                                            columnData = null;
                                        }
                                    }
//            						columnData = "DATE'" + columnData.substring(0, 10) +"'"; 
            					}
            					// 时间类型字段全存入当前时间
            				} else if (timeColumnList.contains(column)) {
                                if(BaseCommonCheckService.isMYSQLDataBase()){
                                    columnData =  "'" + DateUtil.formatDateTime(DateUtil.now())+ "'";
                                }else{
                                    columnData = "SYSDATE";
                                }
            					// 创建者和修改者均设置为当前用户
            				} else if ("CREATE_USER".equals(column) || "UPDATE_USER".equals(column)) {
            					columnData = "'" + userName + "'";
            				}  else if(StringUtil.isNotEmpty(columnData)&&columnData.contains("'")){
            					columnData = "'" +columnData.replace("'", "''")+ "'";
            				} else {
            					columnData = null==columnData?columnData:"'" + columnData + "'";
            				}
            				insertMap.put(column, columnData);
                		}
            			//增加状态字段的设置
            			insertMap.put("STATUS", "'" + "E" + "'");  
            			
            			//包含定义的接口名称的时候，这些接口的数据来源导入默认增加datasource为手工处理 2020-07-17 start
            			if(StringUtil.isEmpty(insertMap.get("DATA_SOURCE"))&&
            					Arrays.asList(INTERF_DATASOURCE).contains(tableName)){
            				insertMap.put("DATA_SOURCE", "'" + DictCodeListExt3.数据来源_手工维护.getValue()+ "'");
            			}
            			//包含定义的接口名称的时候，这些接口的数据来源导入默认增加datasource为手工处理 2020-07-17 end
            			if (CHB_ASSET_INFO_LIST.contains(tableName)) {
                           String SYSDATE=  "'" + DateUtil.formatDateTime(DateUtil.now())+ "'";
            				insertMap.put("ASSET_TYPE", "'" + assetType + "'");
            				insertMap.put("STATUS", "'" + "E" + "'");
                            if(BaseCommonCheckService.isMYSQLDataBase()){
                                insertMap.put("CREATE_TIME", SYSDATE);
                                insertMap.put("UPDATE_TIME", SYSDATE);
                            }else{
                                insertMap.put("CREATE_TIME", "SYSDATE");
                                insertMap.put("UPDATE_TIME", "SYSDATE");
                            }

            				insertMap.put("CREATE_USER", "'" + userName + "'");
            				insertMap.put("UPDATE_USER", "'" + userName + "'");
            				String industryShares2 = insertMap.get("INDUSTRY_SHARES2");
            				if (StringUtil.isNotEmpty(industryShares2)) {
            					String industryShares1 = secondIndustryMap.get(industryShares2.replace("'", ""));
            					insertMap.put("INDUSTRY_SHARES", "'" + industryShares1 + "'");
            				}
            				String industryLevel2 = insertMap.get("INDUSTRY_LEVEL2");
            				if (StringUtil.isNotEmpty(industryLevel2)) {
            					String industry = secondIndustryMap.get(industryLevel2.replace("'", ""));
            					insertMap.put("INDUSTRY", "'" + industry + "'");
            				}
            				String industryLevel2Type = insertMap.get("INDUSTRY_LEVEL2_TYPE");
            				if (StringUtil.isNotEmpty(industryLevel2Type)) {
            					String industry1 = secondIndustryMap.get(industryLevel2Type.replace("'", ""));
            					insertMap.put("INDUSTRY1", "'" + industry1 + "'");
            					insertMap.put("INDUSTRY_PBC", "'" + industry1 + "'");
            				}
            				String proIndustryLevel2 = insertMap.get("PRO_INDUSTRY_LEVEL2");
            				if (StringUtil.isNotEmpty(proIndustryLevel2)) {
            					String proIndustry = secondIndustryMap.get(proIndustryLevel2.replace("'", ""));
            					insertMap.put("PRO_INDUSTRY", "'" + proIndustry + "'");
            				}
            				String capUseInduLevel2 = insertMap.get("CAP_USE_INDU_LEVEL2");
            				if (StringUtil.isNotEmpty(capUseInduLevel2)) {
            					String capUseIndu = secondIndustryMap.get(capUseInduLevel2.replace("'", ""));
            					insertMap.put("CAP_USE_INDU", "'" + capUseIndu + "'");
            				}
                            // add by dubaojun 2020-10-31 增加备注字段的取值 start
                            // 获取remark
                            String remark=strList.get(CAI_BASE_COUNT);
                            insertMap.put("REMARK", "'" + remark + "'");
                            // add by dubaojun 2020-10-31 增加备注字段的取值 end
            			}
					else if(StringUtil.equals(CHB_PRD_DEAL, tableName)){
            				//当模板中办含了识别标识，但是未填写或者模板中不包含识别标识
            				if((StringUtil.isNotEmpty(insertMap.get("INDENTIFY_MARK"))&&
            						StringUtil.equals(insertMap.get("INDENTIFY_MARK"), "''"))||
            						StringUtil.isEmpty(insertMap.get("INDENTIFY_MARK"))){
            					String indentifyMark = insertMap.get("PROD_ID")+"="
            							+insertMap.get("CUSTOMER_TYPE1")+"="+insertMap.get("CUSTOMER_TYPE2");
            					if(StringUtil.isNotEmpty(indentifyMark)){
            						indentifyMark = "'"+indentifyMark.replace("'", "")+"'";
            					}
            					insertMap.put("INDENTIFY_MARK", indentifyMark);
            				}
            			}
                        // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 start
                        Set<String> keySet = new HashSet<>(insertMap.keySet());
                        for (String key : keySet) {
                            if(!allColumnList.contains(key)) {
                                insertMap.remove(key);
                            }
                        }
                        // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 end
            			dataInsertList.add(insertMap);
            			break;
            		case "删除" :
//            			for (int i = 1; i < size; i ++) {
            			for (Integer i : indexList) {
            				String column = columnList.get(i);
            				String columnData = strList.get(i);
            				if ((dateColumnList.contains(column) && !("").equals(columnData)) ||
            						(timeColumnList.contains(column) && !("").equals(columnData))) {
            					// add by zhangliang 20201026 删除的数据不需要解析CREATE_TIME和UPDATE_TIME为DATE类型  start
								if ("CREATE_TIME".equals(column) || "UPDATE_TIME".equals(column)) {

                                    if(BaseCommonCheckService.isMYSQLDataBase()){
                                        columnData =  "'" + DateUtil.formatDateTime(DateUtil.now())+ "'";
                                    }else{
                                        columnData = "SYSDATE";
                                    }
								// add by zhangliang 20201026 删除的数据不需要解析CREATE_TIME和UPDATE_TIME为DATE类型  end
								}else{
									String dateStr = formatDateStr(columnData);
									if (null != dateStr) {
										columnData = "DATE'" + dateStr + "'";
									} else {
										columnData = null;
									}
//            					columnData = "DATE'" + columnData.substring(0, 10) +"'";
								}
            				} else {
            					columnData = "'" + columnData + "'";
            				}
            				deleteMap.put(column, columnData);
                		}
            			if (CHB_ASSET_INFO_LIST.contains(tableName)) {
            				deleteMap.put("ASSET_TYPE", "'" + assetType + "'");
//            				String industryShares2 = deleteMap.get("INDUSTRY_SHARES2");
//            				if (StringUtil.isNotEmpty(industryShares2)) {
//            					String industryShares1 = tamsBaseMapper.queryFirstIndustryBySecondIndustry(industryShares2.replace("'", ""));
//            					deleteMap.put("INDUSTRY_SHARES", "'" + industryShares1 + "'");
//            				}
            			}
            			dataDeleteList.add(deleteMap);
            			break;
            		case "修改" :
//            			for (int i = 1; i < size; i ++) {
            			for (Integer i : indexList) {
            				String column = columnList.get(i);
            				String columnData = strList.get(i);
            				//资产要素放置后续空覆盖前有有值选项
            				if(i>=CAI_BASE_COUNT&&StringUtil.isNotEmpty(updateMap.get(column))
            						&&StringUtil.isEmpty(columnData)
            						&&CHB_ASSET_INFO_LIST.contains(tableName)){
            					continue;
            				}
            				// 修改操作主键不修改UUID(非组合主键)
            				if (null != primarykeyList && primarykeyList.size() == 1 && primarykeyList.get(0).equals(column)) {
            					continue;
            				}
            				//创建人以及时间无需修改
            				if ("CREATE_USER".equals(column)|| "CREATE_TIME".equals(column)) {
            					continue;
            				}
            				//资产名称覆盖问题修复，防止被覆盖
            				else if("ASSET_NAME".equals(column)&&StringUtil.isEmpty(columnData)){
            					continue;
            				}
            				// 日期类型字段处理
            				if (dateColumnList.contains(column) && !("").equals(columnData)) {
            					// 特殊情况,UPDATE_TIME为DATE类型
            					if ("UPDATE_TIME".equals(column)) {
                                    if(BaseCommonCheckService.isMYSQLDataBase()){
                                        columnData =  "'" + DateUtil.formatDateTime(DateUtil.now())+ "'";
                                    }else{
                                        columnData = "SYSDATE";
                                    }

            					} else {
                                    if (columnData == null || ("").equals(columnData)) {
                                        columnData= "'" + columnData + "'";
                                    } else {
                                        //excel中输入了时间戳数据处理
                                        if (columnData.length() > 10) {
                                            columnData = columnData.substring(0, 10);
                                        }
                                        String dateStr = formatDateStr(columnData);
                                        if (null != dateStr) {
                                            columnData = "DATE'" + dateStr + "'";
                                        } else {
                                            columnData = null;
                                        }
//            						columnData = "DATE'" + columnData.substring(0, 10) +"'"; 
                                    }
                                }
            				// 时间类型字段
            				} else if (timeColumnList.contains(column)) {
            					// 修改时间变动,其余时间不变
            					if(column.indexOf("UPDATE") > -1) {
                                    if(BaseCommonCheckService.isMYSQLDataBase()){
                                        columnData =  "'" + DateUtil.formatDateTime(DateUtil.now())+ "'";
                                    }else{
                                        columnData = "SYSDATE";
                                    }
            					} else {
            						continue;
            					}
            					// 修改者改为当前用户
            				} else if ("UPDATE_USER".equals(column)) {
            					columnData = "'" + userName + "'";
            					// 数据状态改为 M
            				} else if ("STATUS".equals(column)){
								if (CHB_INFO_LIST.contains(tableName)) {
									columnData = "'" + "E" + "'";
								} else {
									columnData = "'" + "M" + "'";
								}

            				} else if(StringUtil.isNotEmpty(columnData)&&columnData.contains("'")){
            					columnData = "'" +columnData.replace("'", "''")+ "'";
            				} else {
            					columnData = "'" + columnData + "'";
            				}
            				updateMap.put(column, columnData);
                		}
            			
            			//增加状态字段的设置
						if (CHB_INFO_LIST.contains(tableName)) {
							updateMap.put("STATUS", "'" + "E" + "'");
						} else {
							updateMap.put("STATUS", "'" + "M" + "'");
						}

            			
            			//包含定义的接口名称的时候，这些接口的数据来源导入默认增加datasource为手工处理 2020-07-17 start
            			if(StringUtil.isEmpty(insertMap.get("DATA_SOURCE"))&&
            					Arrays.asList(INTERF_DATASOURCE).contains(tableName)){
            				updateMap.put("DATA_SOURCE","'" + DictCodeListExt3.数据来源_手工维护.getValue()+ "'");
            			}
            			//包含定义的接口名称的时候，这些接口的数据来源导入默认增加datasource为手工处理 2020-07-17 end
            			if (CHB_ASSET_INFO_LIST.contains(tableName)) {
            			    String  SYSDATE="'" + DateUtil.formatDateTime(DateUtil.now())+ "'";
            				updateMap.put("ASSET_TYPE", "'" + assetType + "'");
            				updateMap.put("STATUS", "'" + "M" + "'");

                            if(BaseCommonCheckService.isMYSQLDataBase()){
                                updateMap.put("UPDATE_TIME", SYSDATE);
                            }else{
                                updateMap.put("UPDATE_TIME", "SYSDATE");
                            }
            				updateMap.put("UPDATE_USER", "'" + userName + "'");
            				String industryShares2 = updateMap.get("INDUSTRY_SHARES2");
            				if (StringUtil.isNotEmpty(industryShares2)) {
            					String industryShares1 = secondIndustryMap.get(industryShares2.replace("'", ""));
            					updateMap.put("INDUSTRY_SHARES", "'" + industryShares1 + "'");
            				}
            				String industryLevel2 = updateMap.get("INDUSTRY_LEVEL2");
            				if (StringUtil.isNotEmpty(industryLevel2)) {
            					String industry = secondIndustryMap.get(industryLevel2.replace("'", ""));
            					updateMap.put("INDUSTRY", "'" + industry + "'");
            				}
            				String industryLevel2Type = updateMap.get("INDUSTRY_LEVEL2_TYPE");
            				if (StringUtil.isNotEmpty(industryLevel2Type)) {
            					String industry1 = secondIndustryMap.get(industryLevel2Type.replace("'", ""));
            					updateMap.put("INDUSTRY1", "'" + industry1 + "'");
            					updateMap.put("INDUSTRY_PBC", "'" + industry1 + "'");
            				}
            				String proIndustryLevel2 = updateMap.get("PRO_INDUSTRY_LEVEL2");
            				if (StringUtil.isNotEmpty(proIndustryLevel2)) {
            					String proIndustry = secondIndustryMap.get(proIndustryLevel2.replace("'", ""));
            					updateMap.put("PRO_INDUSTRY", "'" + proIndustry + "'");
            				}
            				String capUseInduLevel2 = updateMap.get("CAP_USE_INDU_LEVEL2");
            				if (StringUtil.isNotEmpty(capUseInduLevel2)) {
            					String capUseIndu = secondIndustryMap.get(capUseInduLevel2.replace("'", ""));
            					updateMap.put("CAP_USE_INDU", "'" + capUseIndu + "'");
            				}
            				// add by dubaojun 2020-10-31 增加备注字段的取值 start
                            // 获取remark
                            String remark=strList.get(CAI_BASE_COUNT);
                            updateMap.put("REMARK", "'" + remark + "'");
                            // add by dubaojun 2020-10-31 增加备注字段的取值 end
            			}
					else if(StringUtil.equals(CHB_PRD_DEAL, tableName)){
            				//当模板中办含了识别标识，但是未填写或者模板中不包含识别标识
            				if((StringUtil.isNotEmpty(updateMap.get("INDENTIFY_MARK"))&&
            						StringUtil.equals(updateMap.get("INDENTIFY_MARK"), "''"))||
            						StringUtil.isEmpty(updateMap.get("INDENTIFY_MARK"))){
            					String indentifyMark = updateMap.get("PROD_ID")+"="
            							+updateMap.get("CUSTOMER_TYPE1")+"="+updateMap.get("CUSTOMER_TYPE2");
            					if(StringUtil.isNotEmpty(indentifyMark)){
            						indentifyMark = "'"+indentifyMark.replace("'", "")+"'";
            					}
            					updateMap.put("INDENTIFY_MARK", indentifyMark);
            				}
            			}
                        // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 start
                        keySet = new HashSet<>(updateMap.keySet());
                        for (String key : keySet) {
                            if(!allColumnList.contains(key)) {
                                updateMap.remove(key);
                            }
                        }
                        // add by dubaojun 2020-10-15 删除不存在的字段，防止报错 end
            			dataUpdateList.add(updateMap);
            			break;
            			default:
            		}
            		
            		
            	}
            	
            	// 按主键删除数据
            	boolean flag = isHaveStatus(tableName);
            	for (HashMap<String, String> dataMap : dataDeleteList) {
            		Map<String, String> itemMap = new HashMap<String, String>();
            		for (String key : dataMap.keySet()) {
        				if (null != bisinessPkList && bisinessPkList.contains(key)) {
        					itemMap.put(key, (String) dataMap.get(key));
        				}
        			}
            		if (flag) {
            			count += baseInformationMapper.deleteData(tableName, itemMap);
            		} else {
            			count += baseInformationMapper.deleteDataWithOutStatus(tableName, itemMap);
            		}
            	}
            	//按主键修改数据
            	for (HashMap<String, String> dataMap : dataUpdateList) {
            		Map<String, String> itemMap = new HashMap<String, String>();
            		for (String key : dataMap.keySet()) {
        				if (null != bisinessPkList && bisinessPkList.contains(key)) {
        					itemMap.put(key, (String) dataMap.get(key));
        				}
        			}
            		count += baseInformationMapper.updateData(tableName, dataMap, itemMap);
            	}
            	//向数据库写入数据
            	for (HashMap<String, String> dataMap : dataInsertList) {
            		count += baseInformationMapper.insertData(tableName, dataMap);
            	}
            }
           
            
    	}
    	webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
    	return webResult;
    }
    
    /**
     * 连接报表生成器
     * @param tableId
	 * @param originalDataList
     */
    public int linkSvrReport(String tableId,List<List<String>> originalDataList) {
    	int count = 0;
    	if (CHB_ASSET_INFO_LIST.contains(tableId)) {
    		return count;
    	}
    	if (!StringUtil.isEmptyList(originalDataList)) {
			List<String> dataObjectList = baseInformationMapper.querydataObjectByTable(tableId);
			if (StringUtil.isEmptyList(dataObjectList)) {
				return count;
			}
	        String dataObject = dataObjectList.get(0);
	        if (StringUtil.isEmpty(dataObject)) {
	        	return count;
	        }
	        String date = DateUtil.getCurrentDate();
	        DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
            // 产品列表
            List<String> prodList = new LinkedList<String>();
            // 资产列表
            List<String> assetList = new LinkedList<String>();
            List<List<String>> dataList = handleData(originalDataList, tableId,false);
            if (null != dataList && dataList.size() > 1) {
            	List<String> columnList = dataList.get(0);
            	// "PROD_ID" 字段对应的下标
            	Integer prodIndex = columnList.indexOf("PROD_ID");
            	// "ASSET_CODE" 字段对应的下标
            	Integer assetIndex = columnList.indexOf("ASSET_CODE");
            	for (List<String> strList : dataList) {
            		String operType = strList.get(0);
            		if ("新增".equals(operType) || "修改".equals(operType) || "删除".equals(operType)) {
            			// 存在"PROD_ID"字段
            			if (prodIndex > -1) {
            				prodList.add(strList.get(prodIndex));
            			}
            			// 存在"ASSET_CODE"字段
            			if (assetIndex > -1) {
            				assetList.add(strList.get(assetIndex));
            			}
            		}
            	}
            }
			try {
				Date businessDate = format.parse(date);
				svrReportGenetorService.generateReport(businessDate, dataObject, prodList, assetList);
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
			}  
    	}
    	return count;
    }
    
    /**
     * 获取具体表所有日期类型的字段
     * @param param 页面参数
     * @return 所有日期类型字段
     */
    public String queryDateColumnList(Map<String, Object> param) {
    	String tableId = (String) param.get("tableId");
		List<String> dateColumnList = baseInformationMapper.queryDateColumnList(tableId);
		Map<String, String> data = new HashMap<String, String>();
		dateColumnList.forEach(dateColumn -> {
			data.put(dateColumn, dateColumn);
		});
		return JSON.toJSONString(data);
    }
    
    
    /**
     * 获取具体表所有时间类型的字段
     * @param param 页面参数
     * @return 所有时间类型字段
     */
    public String queryTimeColumnList(Map<String, Object> param) {
    	String tableId = (String) param.get("tableId");
		List<String> timeColumnList = baseInformationMapper.queryTimeColumnList(tableId);
		Map<String, String> data = new HashMap<String, String>();
		timeColumnList.forEach(dateColumn -> {
			data.put(dateColumn, dateColumn);
		});
		return JSON.toJSONString(data);
    }
    
    /**
     * 根据页面传来的条件删除数据
     * @return 是否删除成功
     */
    @SuppressWarnings("unchecked")
	public int deleteData(Map<String, Object> param) {
    	int count = 0;
    	String tableId = (String) param.get("tableId");
//    	String tableName = (String) param.get("tableName");
		String dataObj = (String) param.get("jasonData");
		List<String> primarykeyList = baseInformationMapper.queryBisinessPkListByTable(tableId);
		List<JSONObject> jsonList = JSONArray.parseArray(dataObj).toJavaList(JSONObject.class);
		List<String> timeColumnList =  baseInformationMapper.queryTimeColumnList(tableId);
		List<String> dateColumnList =  baseInformationMapper.queryDateColumnList(tableId);
		boolean flag = isHaveStatus(tableId);
		for (JSONObject jsonObj : jsonList) {
			Map<String, Object> jsonMap = JSONObject.toJavaObject(jsonObj, Map.class);
			Map<String, String> itemMap = new HashMap<String, String>();
			for (String key : jsonMap.keySet()) {
				if (null != primarykeyList && primarykeyList.contains(key)) {
					if ((dateColumnList.contains(key) && !("").equals(key)) ||
    						(timeColumnList.contains(key) && !("").equals(key))) {
						String dateStr = formatDateStr((String) jsonMap.get(key));
    					if (null != dateStr) {
    						itemMap.put(key, "DATE'" + dateStr +"'");
    					} else {
    						itemMap.put(key, null);
    					}
//    					itemMap.put(key, "DATE'" + ((String) jsonMap.get(key)).substring(0, 10) +"'");
    				} else {
    					if(StringUtil.isNotEmpty(jsonMap.get(key))){
    						String keyTemp = (String) jsonMap.get(key);
    						if(keyTemp.contains(StringUtil.SPACE)){
            					itemMap.put(key, "'" + keyTemp.substring(0,keyTemp.indexOf(StringUtil.SPACE)) + "'");
    						}
    						else{
            					itemMap.put(key, "'" + keyTemp + "'");
    						}
    					}
    					else{
        					itemMap.put(key, "");
    					}
    				}
				}
			}
			if (flag) {
    			count += baseInformationMapper.deleteData(tableId, itemMap);
    		} else {
    			count += baseInformationMapper.deleteDataWithOutStatus(tableId, itemMap);
    		}
		}
		return count;
    }
    
    /**
     *  数据删除成功之后触发报表连接器
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
	public int linkSvrReportAfterDelete(Map<String, Object> param) {
    	int count = 0;
    	String tableName = (String) param.get("tableId");
		String dataObj = (String) param.get("jasonData");
		List<JSONObject> jsonList = JSONArray.parseArray(dataObj).toJavaList(JSONObject.class);
		List<String> dataObjectList = baseInformationMapper.querydataObjectByTable(tableName);
		if (StringUtil.isEmptyList(dataObjectList)) {
			return count;
		}
        String dataObject = dataObjectList.get(0);
        if (StringUtil.isEmpty(dataObject)) {
        	return count;
        }
        String date = DateUtil.getCurrentDate();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		// 产品列表
        List<String> prodList = new LinkedList<String>();
        // 资产列表
        List<String> assetList = new LinkedList<String>();
		for (JSONObject jsonObj : jsonList) {
			Map<String, Object> jsonMap = JSONObject.toJavaObject(jsonObj, Map.class);
			for (String key : jsonMap.keySet()) {
				// 存在"PROD_ID"字段
    			if ("PROD_ID".equals(key)) {
    				prodList.add((String) jsonMap.get(key));
    			}
    			// 存在"ASSET_CODE"字段
    			if ("ASSET_CODE".equals(key)) {
    				assetList.add((String) jsonMap.get(key));
    			}
			}
		}
		try {
			Date businessDate = format.parse(date);
			svrReportGenetorService.generateReport(businessDate, dataObject, prodList, assetList);
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}  
    	return count;
    }
    
    /**
     * 删除指定方案
     * @param param
     * @return  删除方案的名称
     */
    public String deleteFilter(Map<String, Object> param) {
    	String filterName = "";
    	String filterId = (String) param.get("filterId");
    	filterName = filterInformationMapper.queryFilterNameById(filterId);
    	filterInformationMapper.deleteFilterInfo(filterId);
    	filterInformationDetailMapper.deleteFilterInfoDetail(filterId);
    	return filterName;
    }
    
    /**
	 * 检查传入的参数是不是一张表的，作为错误信息表导出前检查
	 * @return 是否是同一张表
	 */
    public boolean checkIsSingleTable(Map<String, Object> param) {
    	boolean flag = false;
    	String dataObj = (String) param.get("jasonData");
    	if (dataObj != null && dataObj.length() > 0 && !"[]".equals(dataObj)) {
    		List<JSONObject> jsonList = JSONArray.parseArray(dataObj).toJavaList(JSONObject.class);
        	List<Map<String, Map<String, String>>> mapList = convertJasonToList(jsonList);
    		Map<String, List<Map<String, String>>> groupMap = convertListToMap(mapList);
    		if (groupMap.size() > 1) {
    			flag = true;
    		}
    	}
    	else{
    		String tableName=chbInfoMgrResultConfigMapper.getTableNameByTableId((String) param.get("serachInterf"));

    		if(StringUtil.isEmpty(tableName)){
    			flag = true;
    		}
    	}
    	return flag;
    }
    
    /**
     * 根据页面条件获取对应字段类型
     * @param param 页面参数
     * @return 字段类型
     */
    public String queryColumnTypeByCondition(Map<String, Object> param) {
    	String tableId = (String) param.get("tableId");
    	String curColumn = (String) param.get("curColumn");
    	return baseInformationMapper.queryColumnTypeByCondition(tableId, curColumn);
    }
    
    
    /**
     * 根据页面条件获取对应字段名称
     * @param param 页面参数
     * @return 字段名称
     */
    public String queryColumnNameByCondition(Map<String, Object> param) {
    	String tableId = (String) param.get("tableId");
    	String curColumn = (String) param.get("curColumn");
    	return baseInformationMapper.queryColumnNameByCondition(tableId, curColumn);
    }
    
    /**
     * 根据数据类型查询基础表信息
     * @param interfType 数据类型
     * @return 基础表信息
     */
    public List<BaseInformationDto> queryTableIdByInterfType(String interfType) {
    	return baseInformationMapper.queryTableIdByInterfType(interfType);
    }
    
    /**
     * 判断指定表是否含有STATUS字段
     * @param tableId 表名
     * @return 是否含有STATUS字段
     */
    private boolean isHaveStatus(String tableId) {
    	boolean flag = false;
    	List<String> columnList = baseInformationMapper.queryAllColumns(tableId);
    	if (columnList.contains("STATUS")) {
    		flag = true;
    	}
    	return flag;
    }
    
    /**
    * @Title: formatDateStr
    * @Description: (将日期类型字段转换为 yyyy-MM-dd 类型)
    * @param @param dateStr
    * @param @return    参数
    * @return String    返回类型
    * @throws
    */
    private String formatDateStr(String dateStr) {
    	
    	String formatDateStr = null;
    	
    	try {
    		Date date = DateUtil.parse(dateStr);
        	if (null !=  date) {
        		formatDateStr = DateUtil.format(date);
        	} 
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(),e);
		}
    	return formatDateStr;
    }

	/**
	 * @Title: queryValueByDictAndKeyCache
	 * @Description: (根据数据字典code和key值获取value)
	 * @param @param dictCode 数据字典code
	 * @param @param key key值
	 * @param @return    参数 value值
	 * @return String    返回类型
	 */
	private String queryValueByDictAndKeyCache(String dictCode, String dataObj) {
		String value = dataObj;
		if(StringUtil.isNotEmpty(dictCode)&&!value.contains(StringUtil.SPACE)){
			DataDict dataDict = CacheUtil.getDictCode(dictCode);
			if(value.contains(GlobalConstant.COMMA)) {
				List<String> targetTypeList = StringUtil.splitToList(GlobalConstant.COMMA, value);
				if (!StringUtil.isEmptyList(targetTypeList)){
					List<String> targetTypeNameList = new ArrayList<String>();
					for(String targetType : targetTypeList) {
						List<DataDictItem> dictItemList = dataDict.getItems().stream().filter(item -> {
							return StringUtil.equals(StringUtil.trim(targetType), item.getItemCode());
						}).collect(Collectors.toList());
						if (!StringUtil.isEmptyList(dictItemList)){
							targetTypeNameList.add(targetType + " " + dictItemList.get(0).getItemValue());
						}
					}
					value = StringUtil.joinString(targetTypeNameList, GlobalConstant.COMMA);
				}
			}else {
				if (dataDict != null && !StringUtil.isEmptyList(dataDict.getItems())){
					List<DataDictItem> dictItemList = dataDict.getItems().stream().filter(item -> {
						return StringUtil.equals(StringUtil.trim(dataObj), item.getItemCode());
					}).collect(Collectors.toList());
					if (!StringUtil.isEmptyList(dictItemList)){
						value = dataObj + " " + dictItemList.get(0).getItemValue();
					}
				}
			}
		}
		return value;
	}
    
    /**
    * @Title: queryValueByDictAndKey
    * @Description: (根据数据字典code和key值获取value)
    * @param @param dictCode 数据字典code
    * @param @param key key值
    * @param @return    参数 value值
    * @return String    返回类型
    * @throws
    */
    private String queryValueByDictAndKey(String resultDictCode, String dataObj) {
    	DictCode dictCode = DictCode.get(resultDictCode);
    	//modify by wm code值直接从缓存中获取 start 20201031
//		DictCodeList dictCodeList = DictCodeList.from(dictCode, dataObj);
//		DictCodeListExt1 dictCodeListExt1 = DictCodeListExt1.from(dictCode, dataObj);
//		DictCodeListExt2 dictCodeListExt2 = DictCodeListExt2.from(dictCode, dataObj);
//		DictCodeListExt3 dictCodeListExt3 = DictCodeListExt3.from(dictCode, dataObj);
		String value = dataObj;
//		if (null != dictCodeList) {
//			value = dataObj + " " + dictCodeList.getCodeName();
//		}
//		if (null != dictCodeListExt1) {
//			value = dataObj + " " + dictCodeListExt1.getCodeName();
//		}
//		if (null != dictCodeListExt2) {
//			value = dataObj + " " + dictCodeListExt2.getCodeName();
//		}
//		if (null != dictCodeListExt3) {
//			value = dataObj + " " + dictCodeListExt3.getCodeName();
//		}
		if(StringUtil.isNotEmpty(dictCode)&&!value.contains(StringUtil.SPACE)){
			// add by zhangliang 20201110 多选数据字典展示处理  start
			if(value.contains(GlobalConstant.COMMA)) {
				List<String> targetTypeList = StringUtil.splitToList(GlobalConstant.COMMA, value);
				if (!StringUtil.isEmptyList(targetTypeList)){
					List<String> targetTypeNameList = new ArrayList<String>();
					for(String targetType : targetTypeList) {
						targetTypeNameList.add(targetType+" "+SvrDataUtil.getNameFromDataDict(dictCode,targetType));
					}
					value = StringUtil.joinString(targetTypeNameList, GlobalConstant.COMMA);
				}
			}else {
				// add by zhangliang 20201110 多选数据字典展示处理  end
				value = dataObj + " " + SysDataUtil.getNameFromDataDict(dictCode, dataObj);
			}
		}

		//modify by wm code值直接从缓存中获取 end 20201031
		// add by  zhangliang 20201030 画面展示需要把itemCode_转成itemCode_itemValue  start
//		if(value.contains(StringUtil.SPACE)){
//			value = dataObj + " " + SysDataUtil.getNameFromDataDict(dictCode, StringUtil.trim(dataObj));
//		}
		// add by  zhangliang 20201030 画面展示需要把itemCode_转成itemCode_itemValue  end
		return value;
    }
    
    /**
    * @Title: setSizeColumn
    * @Description: (列宽适应中文)
    * @param @param sheet
    * @param @param size    参数
    * @return void    返回类型
    * @throws
    */
    private void setSizeColumn(XSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                XSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
 
                if (currentRow.getCell(columnNum) != null) {
                    XSSFCell currentCell = currentRow.getCell(columnNum);
                    if (CellType.STRING.equals(currentCell.getCellType())) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            
            if(columnWidth<255){
            	sheet.setColumnWidth(columnNum, columnWidth * 256);
            }else{
                sheet.setColumnWidth(columnNum,6000 );
            }
        }
    }
    
    /**
    * @Title: getDictCodeList
    * @Description: (根据数据字典名称获取所有枚举值列表)
    * @param @param dictCode 数据字典名称
    * @param @return    参数 枚举值列表
    * @return String[]    返回类型 字符串数组
    * @throws
    */
    private String[] getDictCodeList(String dictCode) {
    	DictCode dict = DictCode.get(dictCode);
    	Map<String, String> dictMap = SvrDataUtil.getSysDictDataMap(dict);
    	if (dictMap!=null && !dictMap.isEmpty()) {
    		String[] dictCodeArr = new String[dictMap.size()];
    		Set set = dictMap.keySet();
            Object[] arr = set.toArray();
            Arrays.sort(arr);
            int i = 0;
            for(Object key : arr) {
            	dictCodeArr[i] = key + " " + dictMap.get(key);
				i++;
            }
            return dictCodeArr;
    	} else {
    		return null;
    	}
    }

	/**
	 * @Title: getDictCodeList
	 * @Description: (根据数据字典名称获取所有枚举值列表)
	 * @param @param dictCode 数据字典名称
	 * @param @return    参数 枚举值列表
	 * @return String[]    返回类型 字符串数组
	 * @throws
	 */
	private String[] getDictCodeListCache(String dictCode) {
		DataDict dataDict = CacheUtil.getDictCode(dictCode);
		if (dataDict != null && !StringUtil.isEmptyList(dataDict.getItems())){
			String[] dictCodeArr = new String[dataDict.getItems().size()];
			int i = 0;
			for (DataDictItem dataDictItem : dataDict.getItems()){
				dictCodeArr[i] = dataDictItem.getItemCode() + " " + dataDictItem.getItemValue();
				i++;
			}
			return dictCodeArr;
		} else {
			return null;
		}
	}
    
    /**
    * @Title: getSelectBoxRef
    * @Description: (获取时间有效性引用位置)
    * @param @param row 行数
    * @param @param length 引用数据长度 
    * @param @param sheetName 引用表格Sheet名称
    * @param @return    参数 数据有效性引用位置
    * @return String    返回类型
    * @throws
    */
    private String getSelectBoxRef(int row, int length, String sheetName) {
    	int col = length;
    	StringBuilder colRef = new StringBuilder(2);
    	while(length > 0) {
            int thisPart = length % 26;
            if(thisPart == 0) { thisPart = 26; }
            length = (length - thisPart) / 26;

            // The letter A is at 65
            char colChar = (char)(thisPart+64);
            colRef.insert(0, colChar);
        }
        String colName = colRef.toString();
        StringBuilder formula = new StringBuilder();
        formula.append(sheetName)
        .append("!$A$")
        .append(row)
        .append(":$")
        .append(colName)
        .append("$")
        .append(row);
        return formula.toString();
    }
    
    /**
    * @Title: handleData
    * @Description: (对从excel中解析出来的数据进行处理，使其可以正常写入数据库)
    * @param @param originalDataList 原始数据
    * @param @param isCheck 是否为check检查(数据检查可能会验证 枚举值的name)
    * @param @return    参数 处理之后数据集合
    * @return List<List<String>>    返回类型 数据集合
    * @throws
    */
    private List<List<String>> handleData(List<List<String>> originalDataList, String tableId,boolean isCheck) {
    	List<List<String>> dataList = new ArrayList<List<String>>();
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("tableId", tableId);
    	param.put("flag", false);
    	List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfo(param);
    	// 导出字段情况，key值为字段中文名，value为字段名称
    	Map<String, String> columnMap = new HashMap<String, String>();
    	chbInfoMgrResultConfigList.forEach((info) -> {
    		columnMap.put(info.getColumnName(), info.getColumnId());
    	}); 
    	// 导出字段情况，key值为字段名，value为数据字典值
    	Map<String, String> dictCodeMap = new HashMap<String, String>();
    	chbInfoMgrResultConfigList.forEach((info) -> {
    		dictCodeMap.put(info.getColumnId(), info.getResultdictCode());
    	});
    	
    	// 第一行数据，需要把字段中文名转成字段名称,同时记录下那些列的数据是数据字典枚举值(第一列不动)
    	List<Integer> dictIndex = new ArrayList<Integer>();
    	// 原始第一行数据集合
    	List<String> firstOriginalData = originalDataList.get(0);
    	int firstOriginalDataSize = firstOriginalData.size();
    	// 处理后第一行数据集合
    	List<String> firstData = new ArrayList<String>();
    	for (int i = 0; i < firstOriginalDataSize; i ++) {
    		String value = firstOriginalData.get(i);
    		if (i > 0) {
    			value = columnMap.get(value);
    		} 
    		firstData.add(i, value);
    		String dictCode = dictCodeMap.get(value);
    		// 数据字典不为空，则改字段对应的数据需要独立处理
    		if(isCheck){
    			if (StringUtil.isNotEmpty(dictCode)){
        			dictIndex.add(i);
        		}
    		}else{
    			dictIndex.add(i);
    		}
    	}
    	dataList.add(0, firstData);
    	// 后续数据处理
    	int size = originalDataList.size();
    	for (int i = 1; i < size; i ++) {
    		List<String> originalData = originalDataList.get(i);
    		int dataSize = originalData.size();
    		List<String> data = new ArrayList<String>();
    		for (int j = 0; j < dataSize; j ++) {
    			// 实际需要的数据
    			String realData = "";
    			// 原始数据
    			String columnName = originalData.get(j);
    			// 数据字典类型数据处理
    			if (dictIndex.contains(j) && StringUtil.isNotEmpty(columnName)
    					&&!"产品销售区域及募集金额".equals(originalDataList.get(0).get(j))) {
    				// add by zhangliang 20201110 多选数据字典展示处理  start
					if(columnName.contains(GlobalConstant.COMMA)) {
						List<String> targetTypeList = StringUtil.splitToList(GlobalConstant.COMMA, columnName);
						StringBuffer sb = new StringBuffer();
						for(String targetType : targetTypeList) {
							if (targetType.split(" ")[0].length() > 0){
								sb.append(",");
								sb.append(targetType.split(" ")[0]);
							}
						}
						if (sb.length() > 0){
							realData = sb.substring(1, sb.length());
						}
					}else {
						// add by zhangliang 20201110 多选数据字典展示处理  end
						String[] dataArr = columnName.split(" ");
						realData = dataArr[0];
					}
    			} else {
    				realData = columnName;
    			}
    			data.add(j, realData);
    		}
    		dataList.add(i, data);
    	}
    	return dataList;
    }


    /**
     *
     * @Description 资产要素表导入时数据处理
     * @Author      高健
     * @Date        2019年12月7日 下午2:54:26
     * @param @param originalDataList
     * @param @param tableId
     * @param @return 参数
     * @return List<List<String>> 返回类型
     * @throws
     */
    private List<List<String>> handleAssetData(List<List<String>> originalDataList, String tableId, List<String> bisinessPkList, List<String> dateColumnList) {
    	List<List<String>> dataList = new ArrayList<List<String>>();
    	Map<String, Object> param = new HashMap<String, Object>();
    	String tableName=chbInfoMgrResultConfigMapper.getTableNameByTableId(tableId);
    	param.put("tableId", tableId);
    	List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryAllchbMgrTableInfoEx(param);
    	// 导出字段情况，key值为字段序号，value为字段名称
    	Map<Integer, String> columnMap = new HashMap<Integer, String>();
    	chbInfoMgrResultConfigList.forEach((info) -> {
    		columnMap.put(info.getOrderNo(), info.getRealColumnId());
    	});
    	// 导出字段情况，key值为字段名，value为数据字典值
    	Map<String, String> dictCodeMap = new HashMap<String, String>();
    	chbInfoMgrResultConfigList.forEach((info) -> {
    		dictCodeMap.put(info.getRealColumnId(), info.getResultdictCode());
    	});
    	// 第一行数据，需要把字段中文名转成字段名称,同时记录下那些列的数据是数据字典枚举值(第一列不动)
    	List<Integer> dictIndex = new ArrayList<Integer>();
    	// 原始第一行数据集合
    	List<String> firstOriginalData = originalDataList.get(0);
    	int firstOriginalDataSize = firstOriginalData.size();
    	// 处理后第一行数据集合
    	List<String> firstData = new ArrayList<String>();
    	// 是否中债标准模板标识
    	boolean flag = false;
    	if (!StringUtil.equals("操作类型", firstOriginalData.get(0))) {
    		flag = true;
    		firstData.add(0, "操作类型");
    		firstData.add(1, "ASSET_UUID");
    	}
    	// 主键对应位置索引集合
    	List<Integer> pkIndexList = new ArrayList<Integer>();
    	for (int i = 0; i < firstOriginalDataSize; i ++) {
    		String value = firstOriginalData.get(i);
    		if (i > 0 &&  !flag) {
    			value = columnMap.get(i);
    		}
    		if (flag) {
    			value = columnMap.get(i + 2);
    		}
    		if (bisinessPkList.contains(value)) {
    			pkIndexList.add(i);
    		}
    		if (flag) {
    			firstData.add(i + 2, value);
    		} else {
    			firstData.add(i, value);
    		}
    		String dictCode = dictCodeMap.get(value);
    		// 数据字典不为空，则改字段对应的数据需要独立处理
    		if (StringUtil.isNotEmpty(dictCode)) {
    			dictIndex.add(i);
    		}
    	}
    	dataList.add(0, firstData);
    	// 后续数据处理
    	int size = originalDataList.size();
    	for (int i = 1; i < size; i ++) {
    		List<String> originalData = originalDataList.get(i);
    		int dataSize = originalData.size();
    		List<String> data = new ArrayList<String>();
    		Map<String, String> pkDataMap = new HashMap<String, String>();
    		if (flag) {
        		data.add(0, "新增");
        		data.add(1, UUID.randomUUID().toString());
    		}
    		for (int j = 0; j < dataSize; j ++) {
    			// 实际需要的数据
    			String realData = "";
    			// 原始数据
    			String columnName = originalData.get(j);
    			// 数据字典类型数据处理
    			if (dictIndex.contains(j) && StringUtil.isNotEmpty(columnName)) {
					// add by zhangliang 20201110 多选数据字典展示处理  start
					if(columnName.contains(GlobalConstant.COMMA)) {
						List<String> targetTypeList = StringUtil.splitToList(GlobalConstant.COMMA, columnName);
						StringBuffer sb = new StringBuffer();
						for(String targetType : targetTypeList) {
							sb.append(",");
							sb.append(targetType.split(" ")[0]);
						}
						realData = sb.substring(1, sb.length());
					}else {
						// add by zhangliang 20201110 多选数据字典展示处理  end
						String[] dataArr = columnName.split(" ");
						realData = dataArr[0];
					}
    			} else {
    				realData = columnName;
    			}
    			if (flag) {
    				data.add(j + 2, realData);
    			} else {
    				data.add(j, realData);
    			}
    			if (pkIndexList.contains(j)) {
    				String key = "";
    				if (flag) {
    					key = firstData.get(j + 2);
    	    		} else {
    	    			key = firstData.get(j);
    	    		}
    				String value = realData;
    				if (dateColumnList.contains(key) && StringUtil.isNotEmpty(realData)) {
    					String dateStr = formatDateStr(value);
    					if (null != dateStr) {
    						value = "DATE'" + dateStr + "'";
    					} else {
    						value = null;
    					}
    				} else {
    					value = "'" + value + "'";
    				}
    				pkDataMap.put(key, value);
    			}
    		}
    		if (flag) {
    			int count = baseInformationMapper.checkDataIsExist(pkDataMap, tableName);
        		if (count > 0) {
        			data.remove(0);
        			data.add(0, "修改");
        		}
    		}
    		dataList.add(i, data);
    	}
    	return dataList;
    }
    
    public Integer selectHeaderNumByChiName(String chiName){
        Integer headerNum = baseInformationMapper.selectHeaderNumByChiName(chiName);
    	return headerNum == null ? 1 : headerNum;
	}
    //clob转换为string方法
    public  String clobToString(Clob clob){
    	if(clob == null) {   
            return null;   
        }   
		Reader inStreamDoc = null;
        try{   
			inStreamDoc = clob.getCharacterStream();
            char[] tempDoc = new char[(int) clob.length()];
            if(inStreamDoc!=null) {
				inStreamDoc.read(tempDoc);
			}
            inStreamDoc.close();   
            return new String(tempDoc);   
        }   
        catch (IOException e)   
        {
			logger.error(e.getMessage(),e);
        }   
        catch (SQLException es)   
        {   
        	logger.error(es.getMessage(),es);
	  	}finally {
        	if (inStreamDoc != null){
				try {
					inStreamDoc.close();
				} catch (IOException e) {
					throw new BusinessException(e);
          }   
			}
		}
          return null;   
      }
    
    
    /**
     * 获取业务主键拼接的字符串
     * 
     * @param sub 数据
     * @return 业务主键
     */
    private String getBusinessKeyStr(List<String>  bussinesKey,Map<String,String> map) {
        StringBuffer buffer = new StringBuffer();
        for(String str:bussinesKey){
        	 buffer.append(map.get(str));
        	 buffer.append("|");
        }
        if (buffer.length() > 0){
			return buffer.substring(0, buffer.length() - 1);
		}
        return buffer.toString();
    }
}
