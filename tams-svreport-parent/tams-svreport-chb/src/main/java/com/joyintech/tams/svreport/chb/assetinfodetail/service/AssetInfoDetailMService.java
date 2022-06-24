package com.joyintech.tams.svreport.chb.assetinfodetail.service;

import com.google.common.collect.Lists;
import com.joyintech.component.svrreport.chb.dto.ChbAssetInfoDto;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.JoyinMessage;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.*;
import com.joyintech.system.base.entity.SysDatadictListLink;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dao.ChbInfoMgrResultConfigMapper;
import com.joyintech.tams.svreport.chb.assetinfodetail.asset.DtoChangeUtil;
import com.joyintech.tams.svreport.chb.assetinfodetail.dao.AssetInfoDetailMapper;
import com.joyintech.tams.svreport.chb.assetinfodetail.dto.AssetInfoDetailDto;
import com.joyintech.tams.svreport.chb.assetinfodetail.util.AssetInfoDetailUtil;
import com.joyintech.tams.svreport.chb.assetinfodetail.util.ServerMenu;
import com.joyintech.tams.svreport.chb.common.util.DuplicateDataUtil;
import com.joyintech.tams.svreport.chb.common.util.asset.RechbAassetInfoUtilService;
import com.joyintech.tams.svreport.interf.chb.assetinfo.service.ChbAssetInfoInterf;
import com.joyintech.tams.svreport.interf.chb.assetinfo.service.SvrAssetInfoFileImpService;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstCustomerInfoAddImpMapper;
import com.joyintech.tams.svreport.interf.chb.baseinfo.dao.SvrMstCustomerInfoImpMapper;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 资产要素登记服务
 *
 * @author zhangzhongliang
 * @version 1.6.0 2019年04月24日
 * @see AssetInfoDetailMService
 * @since 1.0
 */
@Service
public class AssetInfoDetailMService extends CommonExportService<AssetInfoDetailDto> implements BaseProcessService<AssetInfoDetailDto> {

	protected static final Logger logger = LoggerFactory.getLogger(AssetInfoDetailMService.class);

	@Autowired
	private AssetInfoDetailMapper assetInfoDetailMapper;

	@Autowired
	private RechbAassetInfoUtilService rechbAassetInfoUtilService;

	@Autowired
	private SvrMstCustomerInfoAddImpMapper svrMstCustomerInfoAddImpMapper;

	@Autowired
	private SvrAssetInfoFileImpService svrAssetInfoFileImpService;

	@Autowired
	private SvrMstCustomerInfoImpMapper svrMstCustomerInfoImpMapper;

	@Autowired
	private AssetInfoDetailUtil assetInfoDetailUtil;
	@Autowired
	private ChbInfoMgrResultConfigMapper chbInfoMgrResultConfigMapper;
	@Autowired
	private BaseCommonCheckService baseCommonCheckService;


	static Map<String,Set<String>> map = null;

	static Map<String,Set<String>> maptwo = null;

	private Map<String,List<String>> propertiesMap = new HashMap<>();
	{
		propertiesMap.put("AssetInfoDetail001_3",Lists.newArrayList("资产要素导入"));
		propertiesMap.put("AssetInfoDetail001_2",Lists.newArrayList("QDII","存款","非标","各类资产其他项","回购","基金","金融衍生品","票据及其他","权益类","债券","资产管理产品"));
	}
	/**
	 *
	 * 业务画面查询事件
	 * <功能详细描述>
	 * @return
	 * @see
	 * @since 1.0
	 */
	public Page<AssetInfoDetailDto> searchForPage(Map<String, Object> param) {

		//获取当前登录用户的机构编码
		param.put("orgCode", CommonUtil.getTreeInstCode());

		param.put("assetCodes", StringUtil.isNotEmpty(param.get("assetCode"))?(param.get("assetCode").toString().split(",")):null);
		param.put("assetTypes", StringUtil.isNotEmpty(param.get("assetType"))?(param.get("assetType").toString().split(",")):null);
		param.put("assetType2s", StringUtil.isNotEmpty(param.get("assetType2"))?(param.get("assetType2").toString().split(",")):null);
		param.put("orgAssetType1s", StringUtil.isNotEmpty(param.get("orgAssetType1"))?(param.get("orgAssetType1").toString().split(",")):null);
		param.put("orgAssetType2s", StringUtil.isNotEmpty(param.get("orgAssetType2"))?(param.get("orgAssetType2").toString().split(",")):null);

		return queryForPage(Integer.valueOf(param.get("page").toString()), Integer.valueOf(param.get("length").toString()),
				() -> assetInfoDetailMapper.selectChbInfoByParam(param));
	}

	/**
	 *
	 * 业务画面查询事件
	 * <功能详细描述>
	 * @return
	 * @see
	 * @since 1.0
	 */
	public AssetInfoDetailDto getAssetInfoDetail(String  Assetuuid,String dataStatus) {
		//界面共同方法编写  error表和INFO表
		AssetInfoDetailDto dto = new AssetInfoDetailDto();
		ChbAssetInfo  cinfo=rechbAassetInfoUtilService.BackChbAssetInfoMethod(dataStatus,Assetuuid);
		if(cinfo!=null){
			BeanUtil.copyPropertiesIgnoreNull(dto, cinfo);
		}
		//共同方法无dataStatus数据,需要赋值
		dto.setDataStatus(dataStatus);
		return dto;
	}

	/**
	 * 根据uuid获取资产信息
	 * @param UUid
	 * @return
	 */
	public ChbAssetInfo getChbAssetInfoByUUid(String UUid){
		return assetInfoDetailMapper.getChbAssetInfoByUUid(UUid);
	}

	/**
	 * 资产代码下拉框
	 * @return 产品名称列表
	 */
	public List<SelectOptionDto> queryAssetCodeList(Map<String, Object> map) {
		String orgCode = (String)map.get("orgCode");
		List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
		if (StringUtil.isBlank(orgCode)) {
			orgCodeList = CommonUtil.getOrgCodeListForSearch();
		}
		map.put("orgCodeList", orgCodeList);
		List<SelectOptionDto> dtoList = new ArrayList<SelectOptionDto>();
		//在所选的机构list不为空或者是用户为管理员时
		dtoList = assetInfoDetailMapper.selectAssetCodeList(map);
		return dtoList;
	}

	/**
	 * 设置流程业务说明
	 */
	@Override
	public String generateTitle(AssetInfoDetailDto entity, OperationType type) {
		return  "资产代码" + "(" + entity.getAssetCode() + ")" + "-" + "资产名称" + "("
				+ entity.getAssetName() + ")";
	}
	/**
	 * 工作流（留痕操作结束的时候执行的方法）
	 */
	@Override
	public void doComplete(AssetInfoDetailDto dto, OperationType type) {
		dto.setOperateType(type.toString());
		//分别调用各自的setEntity方法
		this.getDtoByPageMessgae(dto);
		//获取市场机构规模数据

		//赋值相关数据
		dto.setIssuePartyId(CommonUtil.selectBankCode());
		if(StringUtil.isEmpty(dto.getAssetUuid())){
			dto.setAssetUuid(IDGenerator.generate(36));
		}

		/***当资产为资管计划或者委外时,需要保存资管计划开始结束日**/
		if(DictCodeListExt2.资产_负债类别_资产管理产品.getValue().equals(dto.getAssetType())
		||DictCodeListExt2.资产_负债类别_委外投资_协议方式.getValue().equals(dto.getAssetType())){
			dto.setPlanSdate(dto.getVdate());
			dto.setPlanEdate(dto.getMdate());
		}
		/***【资产基本信息维护】-新建或修改页面（债券类资产），“具体类别”字段未保存至CHB_ASSET_INFO表**/
		if(DictCodeListExt2.资产_负债类别_债券类资产_理财直接融资工具_同业存单.getValue().equals(dto.getAssetType())){
			if(dto.getSvrBussFinBondRepInfoDto()!=null) {
				dto.setChbSectype(dto.getSvrBussFinBondRepInfoDto().getRepSectype());
			}
		}

		List<MstCustomerInfoAdd> institList = svrMstCustomerInfoAddImpMapper.getAllMstCustomerInfoInstit();
		//获取市场机构规模数据
		Map<String,MstCustomerInfoAdd> orgListMap = institList.stream().collect(
				Collectors.toMap(MstCustomerInfoAdd::getCustomerId,(p)->p,(key1, key2)->key1));

		//delete by wyn GDLCJGBS-722 start
//		List<MstCustomerInfo> customerInfoList = svrMstCustomerInfoAddImpMapper.getAllMstCustomerInfoList();
//		//获取市场机构名称
//		Map<String,MstCustomerInfo> customerInfoMap = new HashMap<>();
//		for(MstCustomerInfo customerInfo:customerInfoList){
//			String customerId = customerInfo.getCustomerId();
//			customerInfoMap.put(customerId,customerInfo);
//		}
//		if(StringUtil.isNotEmpty(dto.getIssuePartyName())){
//			if(customerInfoMap.get(dto.getIssuePartyName()) != null){
//				dto.setIssuePartyName(customerInfoMap.get(dto.getIssuePartyName()).getCustomerName());
//			}
//		}
  //delete by wyn GDLCJGBS-722 end

		/*** 基金类资产数据接入会同步保存mngOrgName字段 **/
		if(DictCodeListExt2.资产_负债类别_私募基金.getValue().equals(dto.getAssetType())
			||DictCodeListExt2.资产_负债类别_公募基金.getValue().equals(dto.getAssetType())){
			if(dto.getMngOrgName()!=null) {
				dto.setMngOrgName(null!=orgListMap.get(dto.getChbManagerId())?
						orgListMap.get(dto.getChbManagerId()).getCompanyName():null);
			}
			if(dto.getTruOrgName()!=null) {
				dto.setTruOrgName(null!=orgListMap.get(dto.getChbFinancierId())?
						orgListMap.get(dto.getChbManagerId()).getCompanyName():null);
			}
		}


		//获取市场机构规模数据
		Map<String,String> institMap = institList.stream().collect(HashMap<String,String>::new,
				(map, item)->map.put(item.getCustomerId(), item.getPartyType1()), (m, n)->m.putAll(n));

		//2-5企业债券分行业、企业规模统计的列统计维度调整为根据市场机构管理中“机构类型（规模）”字段来取值。
		ChbAssetInfoInterf.convertInstitSize(dto,institMap);
		restTemplate.postForObject(
				SvrServiceApplication.CHB_SERVICE_NAME + "/AssetInfoDetail/doComplete", dto, Integer.class);
	}


	public void doDelete(AssetInfoDetailDto dto) {

		restTemplate.postForObject(
				SvrServiceApplication.CHB_SERVICE_NAME + "/AssetInfoDetail/doDelete", dto, Integer.class);


	}



	/**
	 * 获取资产及二级类型的关系
	 * @return
	 */
	public Map<String, Set<String>> getDataListLink(){

		if(map==null||map.isEmpty()){
			map = new HashMap<>();
			Map<String,Object> params = new HashMap<>();
			params.put("linkDictCode", DictCode.资产_负债类别.getValue());
//		params.put("linkItemCode", itemCode);
			List<SysDatadictListLink> listLink = assetInfoDetailMapper.getDataListLinkData(params);
			for (SysDatadictListLink link:listLink) {
				if(null!=map.get(link.getLinkedItemCode())){
					map.get(link.getLinkedItemCode()).add(link.getItemCode());
				}else{
					map.put(link.getLinkedItemCode(),new HashSet<>(Arrays.asList(link.getItemCode())));
				}
			}
		}
		return map;
	}


	/**
	 * 获取资产及二级类型的关系
	 * @return
	 */
	public Map<String, Set<String>> getDataListLink(String DictCode){

		if(maptwo==null||maptwo.isEmpty()){
			maptwo = new HashMap<>();
			//界面配置,根据SYS_DATADICT_LIST_LINK
			Map<String,Object> params = new HashMap<>();
			params.put("linkDictCode", DictCode);
//		params.put("linkItemCode", itemCode);
			List<SysDatadictListLink> listLink = assetInfoDetailMapper.getDataListLinkData(params);
			for (SysDatadictListLink link:listLink) {
				if(null!=maptwo.get(link.getLinkedItemCode())){
					maptwo.get(link.getLinkedItemCode()).add(link.getItemCode());
				}else{
					maptwo.put(link.getLinkedItemCode(),new HashSet<>(Arrays.asList(link.getItemCode())));
				}
			}
		}
		return maptwo;
	}

	/**
	 * 共同数据塞入方法
	 * @param dto 获取资产类型数据
	 * @return
	 */
	public AssetInfoDetailDto getDtoByPageMessgae(AssetInfoDetailDto dto)  {
		try {
			//遍历资产类型数据
			for (ServerMenu menu : ServerMenu.getAllAsset()) {
				//判断数据字典中是否有当前资产类型的实体类
				if (getDataListLink().get(menu.getAssetType()).contains(dto.getAssetType2())) {
					//获取实体类中对应名称的资产方法
					PropertyDescriptor pd = new PropertyDescriptor(menu.getServerId(),
							dto.getClass());
					Method getMethod =pd.getReadMethod();
					//获取对应的实体类字段
					Object obj1 = getMethod.invoke(dto);
					//执行数据转换方法
					if(null!=obj1){
						DtoChangeUtil.SetChbToRep(obj1, dto, menu.getFlagStr(), true);
					}
					//资产类型赋值
					dto.setAssetType(menu.getAssetType());
				}
			}
			return dto;
		}catch (Exception e){
			logger.info("实体转换错误,请检查数据类型",e);
		}finally {
//			return null;
		}
		return null;
	}

	/**
	 * 查询回购交易的金额
	 */
   public BigDecimal  getTargetAmt(Map<String, Object> map){
   	String  assetUUid=(String)map.get("assetuuid");
     return assetInfoDetailMapper.getTargetAmt(assetUUid);
   }

	/**
	 * 根据界面传入数据获取二级资产类型
	 * @param resultMap
	 * @return
	 */
	public List<SelectOptionDto> getAssetTye2(Map<String,Object> resultMap){
		List<SelectOptionDto> resultOpt= assetInfoDetailMapper.getAssetTye2(resultMap);
		return  resultOpt;
	}
	/**
	 * 获取交易对手列表
	 * @param resultMap
	 * @return
	 */
	public List<SelectOptionDto> selectCounterId(Map<String,Object> resultMap){
		List<SelectOptionDto> resultOpt= assetInfoDetailMapper.selectCounterId(resultMap);
		return  resultOpt;
	}



	/**
	 * 设置导出文件参数
	 */
	@Override
	protected void buildExportParam(ExportParam exportParam) {
		exportParam.config(
				"/com/joyintech/tams/svreport/chb/assetinfodetail/conf/export_conf.xml")
				.dataSource(DataSourceType.MASERT);
	}
	/**
	 * 查询要导出的数据
	 */
	@Override
	public Page<AssetInfoDetailDto> selectPageData(Map<String, Object> param) {
		return searchForPage(param);
	}

	public List<AssetInfoDetailDto> setDataList(Map<String, Object> param) {
		List<AssetInfoDetailDto> resultList = new ArrayList<>();
		String keyList = (String) param.get("keyList");
		if (StringUtil.isNotEmpty(keyList)) {
			try {
				resultList = JsonUtils.json2List(keyList, AssetInfoDetailDto.class);
				ArrayList<Object> list = Lists.newArrayList();
				resultList.forEach(e->list.add(e.getAssetCode()));
				param.put("assetCodes",list);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		} else {
			param.put("orgCode", CommonUtil.getTreeInstCode());
			param.put("assetCodes", StringUtil.isNotEmpty(param.get("assetCode"))?(param.get("assetCode").toString().split(",")):null);
			param.put("assetTypes", StringUtil.isNotEmpty(param.get("assetType"))?(param.get("assetType").toString().split(",")):null);
			param.put("assetType2s", StringUtil.isNotEmpty(param.get("assetType2"))?(param.get("assetType2").toString().split(",")):null);
			param.put("orgAssetType1s", StringUtil.isNotEmpty(param.get("orgAssetType1"))?(param.get("orgAssetType1").toString().split(",")):null);
			param.put("orgAssetType2s", StringUtil.isNotEmpty(param.get("orgAssetType2"))?(param.get("orgAssetType2").toString().split(",")):null);
			param.put("status", StringUtil.isNotEmpty(param.get("status"))?param.get("status"):null);

		}
		resultList = assetInfoDetailMapper.selectList(param);
		return resultList;
	}
	
	/**
	 * 导入数据文件之前检查数据
	 *
	 * @param fileName 导入的文件名
	 * @return 处理结果
	 */
	public AjaxResult checkConfirmMsgForImportData(String fileName) throws Exception {
		AjaxResult webResult = new AjaxResult();
		String pageId = "AssetInfoDetail001_3";
		if(StringUtil.equals(CacheUtil.getSysParam("CHB_ASSET_INFO_TEMPLATE_FILE"), "01")){
			pageId = "AssetInfoDetail001_2";
		}
		if (!DuplicateDataUtil.checkFileName(fileName,propertiesMap.get(pageId))){
			webResult.setMessage(this.getMessage("ImportFileName"), MessageType.错误消息);
			return webResult;
		}
		List<Map<String, String>> list = dataMapAnalysis(fileName,ChbCheckTableInfo.资产要素登记表INFO.getTableName());
		if(list.size()<1){
			webResult.setMessage("请勿上传空文件", MessageType.错误消息);
			return webResult;
		}
		int i = assetInfoDetailMapper.selectValidRecords(list);
		if(i>0){
			String[] checkEuplicatedKeys = ChbCheckTableInfo.资产要素登记表INFO.getCheckEuplicatedKeys();
			int dup = DuplicateDataUtil.countDuplicatedData(list, checkEuplicatedKeys);
			webResult.setMessage(this.getMessage("SvrDuplicateMessage",dup,i), MessageType.确认消息);
		}
		return webResult;
	}

	public List<ExcelValidDto> importData(String fileName) throws Exception{
		List<Map<String, String>> list = dataMapAnalysis(fileName,ChbCheckTableInfo.资产要素登记表INFO.getTableName());
		List<ExcelValidDto> listExcel = new ArrayList<ExcelValidDto>();
		svrAssetInfoFileImpService.importDB(listExcel,list,new InterfFile(),null);
		return listExcel;

	}

	/**
	 * excel导入数据整理
	 *
	 * @param flieName 文件名称
	 * @return 检验list
	 * @throws Exception 异常
	 * @see
	 * @since 1.0
	 */
	public List<Map<String,String>> dataMapAnalysis(String flieName,String tableName)
			throws Exception {
		String prefix = CacheUtil.getSysParam(GlobalConstant.COM_PROP_UPLOAD_PATH);
		if(!StringUtil.startsWith(flieName, prefix)
				&& !StringUtil.startsWith(flieName, prefix.replace("/", File.separator))) {
			flieName = prefix + File.separator + flieName;
		}
		com.joyintech.framework.util.ExcelUtil excelUtil = com.joyintech.framework.util.ExcelUtil.build(flieName, null);

		List<List<String>> dataList = excelUtil.readToList(0);
		List<String> headList = dataList.get(1);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableId", tableName);
		param.put("flag", false);
		List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryCloumnMapping(param);
		Map<String,String> map = new HashMap<>();
		chbInfoMgrResultConfigList.forEach(ele->map.put(ele.getColumnName().trim().replace("（","(").replace("）",")"),ele.getRealColumnId()));
		List<String> columnList = new ArrayList<>();
		for(int i=0;i<headList.size();++i){
			String columnName = headList.get(i);
			String temp = columnName.trim().replace("（","(").replace("）",")");
			columnName= map.get(temp);
			columnList.add(i,columnName);
		}
		List<Map<String,String>> list = new ArrayList<>();
		// 根据资产负债二级类别找一级类别
		Map<String,String> dictAssetType = new HashMap<>();
		Map<String,Object> params = new HashMap<>();
		params.put("linkDictCode", DictCode.资产_负债类别.getValue());
		List<SysDatadictListLink> listLink = assetInfoDetailMapper.getDataListLinkData(params);
		listLink.forEach(sysDatadictListLink -> {
			if(!((String)sysDatadictListLink.getLinkedItemCode()).equals("L01")){
				dictAssetType.put(sysDatadictListLink.getItemCode(),sysDatadictListLink.getLinkedItemCode());
			}
		});

		// 根据机构所属行业二级类别找一级类别
		Map<String,String> dictIndustry = new HashMap<>();
		params.put("linkDictCode", DictCode.机构所属行业一级分类.getValue());
		List<SysDatadictListLink> industryListLink = assetInfoDetailMapper.getDataListLinkData(params);
		industryListLink.forEach(sysDatadictListLink -> {
			dictIndustry.put(sysDatadictListLink.getItemCode(),sysDatadictListLink.getLinkedItemCode());
		});
		for(int i=2;i<dataList.size();++i){
			Map<String,String> subMap = new HashMap<>();
			List<String> subList = dataList.get(i);
			for(int j=0;j<subList.size();++j){
				if(StringUtil.isEmpty(subMap.get(columnList.get(j)))){
					subMap.put(columnList.get(j),subList.get(j));
				}
			}
			subMap.put("ASSET_UUID",IDGenerator.generate(36));
			list.add(subMap);
		}
		for (ChbInfoMgrResultConfig config : chbInfoMgrResultConfigList) {
			if(!StringUtils.isEmpty(config.getResultdictCode())){
				for (Map<String, String> m : list) {
					if(m.get(config.getRealColumnId())!=null){
						if(config.getRealColumnId().equals("TARGET_TYPE3")){
							try {
								StringBuffer sb= new StringBuffer();
								String[] split = m.get(config.getRealColumnId()).split(",");
								Stream.of(split).forEach(e->sb.append(e.split(" ")[0]).append(","));
								sb.deleteCharAt(sb.length() - 1);
								m.put(config.getRealColumnId(),sb.toString());
							} catch (Exception e) {
								logger.error(e.getMessage(), e);
							}

						}else{
							m.put(config.getRealColumnId(),m.get(config.getRealColumnId()).split(" ")[0]);
						}
					}

				}
			}
		}
		list.forEach(subMap->{
			subMap.put("ASSET_TYPE",dictAssetType.get(subMap.get("ASSET_TYPE2")));
			subMap.put("CAP_USE_INDU",dictIndustry.get(subMap.get("CAP_USE_INDU_LEVEL2")));
			subMap.put("INDUSTRY_SHARES",dictIndustry.get(subMap.get("INDUSTRY_SHARES2")));
			subMap.put("PRO_INDUSTRY",dictAssetType.get(subMap.get("PRO_INDUSTRY_LEVEL2")));
			subMap.put("INDUSTRY",dictAssetType.get(subMap.get("INDUSTRY_LEVEL2")));
			subMap.put("INDUSTRY1",dictAssetType.get(subMap.get("INDUSTRY_LEVEL2_TYPE")));
		});
		return list;
	}
	public List<AssetInfoDetailDto> converToBean(List<Map<String,String>> dataList)
			throws Exception {
		// 返回的list定义
		List<AssetInfoDetailDto> assetInfoDetailDtoList = new ArrayList<AssetInfoDetailDto>();
		Class<AssetInfoDetailDto> clazz = AssetInfoDetailDto.class;
		Map<String, Method> toMap = methodToMap(clazz);
		Map<String, String> filedToMap = filedToMap(clazz);
		Object var = null;
		for (Map<String, String> map : dataList) {
			Map<String,String> temp = new HashMap<>();
			Set<String> keys = map.keySet();
			for(String key :keys){
				if(null !=key){
					temp.put(key.toLowerCase().replace("_", ""),map.get(key));
				}
			}

			AssetInfoDetailDto assetInfoDetailDto = clazz.newInstance();
				for(Map.Entry e:temp.entrySet()){
					for(Map.Entry el:toMap.entrySet()){
						if(null !=e.getKey()){
							if(((String)el.getKey()).substring(3).equalsIgnoreCase((String) e.getKey())){
								if(null !=filedToMap.get(e.getKey())&&filedToMap.get(e.getKey()).equals(String.class.getName())){
									var =StringUtil.isEmpty((String)e.getValue())?null:(String)e.getValue();
								}else if(null !=filedToMap.get(e.getKey())&&filedToMap.get(e.getKey()).equals(Date.class.getName())){
									var = StringUtil.isEmpty((String)e.getValue())?null:DateUtil.parse((String) e.getValue());
								}else if(null !=filedToMap.get(e.getKey())&&filedToMap.get(e.getKey()).equals(Long.class.getName())){
									var = StringUtil.isEmpty((String)e.getValue())?null:Long.valueOf((String) e.getValue()) ;
								}else if(null !=filedToMap.get(e.getKey())&&filedToMap.get(e.getKey()).equals(Integer.class.getName())){
									var = StringUtil.isEmpty((String)e.getValue())?null:Integer.valueOf((String) e.getValue()) ;
								}else if(null !=filedToMap.get(e.getKey())&&filedToMap.get(e.getKey()).equals(BigDecimal.class.getName())){
									var = StringUtil.isEmpty((String)e.getValue())?null:new BigDecimal((String) e.getValue());
								}
								((Method)el.getValue()).invoke(assetInfoDetailDto,var);
								break;
							}
						}
					}
				}
			assetInfoDetailDto.setErrMsg(null!=map.get("ERRMSG")?map.get("ERRMSG"):"");
			assetInfoDetailDto.setCreateUser(CommonUtil.getUserName());
			assetInfoDetailDto.setCreateDept(CommonUtil.getDeptCode()==null?"":CommonUtil.getDeptCode());
			assetInfoDetailDto.setCreateTime(DateUtil.now());
			assetInfoDetailDto.setUpdateUser(CommonUtil.getUserName());
			assetInfoDetailDto.setUpdateTime(DateUtil.now());
			assetInfoDetailDto.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
			assetInfoDetailDto.setStatus(null!=map.get("STATUS") && map.get("STATUS").equals("A")? DictCodeListExt1.维护数据状态_待处理.getValue():DictCodeListExt1.维护数据状态_有效.getValue());
			assetInfoDetailDtoList.add(assetInfoDetailDto);
		}
		return  assetInfoDetailDtoList;
	}
	// 获取类的set方法
	public Map<String,Method> methodToMap(Class clazz){
		return Arrays.stream(clazz.getMethods()).filter(m -> m.getName().startsWith("set")).collect(Collectors.toMap(m -> m.getName(), m -> m));
	}
	// 获取类的属性类型
	public Map<String,String> filedToMap(Class clazz){
		Map<String, String> var1 = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toMap(f -> f.getName().toLowerCase(), f -> f.getGenericType().getTypeName()));
		Map<String, String> var2 = Arrays.stream(clazz.getSuperclass().getDeclaredFields()).collect(Collectors.toMap(f -> f.getName().toLowerCase(), f -> f.getGenericType().getTypeName()));
		var2.putAll(var1);
		return var2;
	}


	public List<AssetInfoDetailDto> selectAll(Map<String, Object> param) {
		//获取当前登录用户的机构编码
		param.put("orgCode", CommonUtil.getTreeInstCode());

		param.put("assetCodes", StringUtil.isNotEmpty(param.get("assetCode"))?(param.get("assetCode").toString().split(",")):null);
		param.put("assetTypes", StringUtil.isNotEmpty(param.get("assetType"))?(param.get("assetType").toString().split(",")):null);
		param.put("assetType2s", StringUtil.isNotEmpty(param.get("assetType2"))?(param.get("assetType2").toString().split(",")):null);
		param.put("orgAssetType1s", StringUtil.isNotEmpty(param.get("orgAssetType1"))?(param.get("orgAssetType1").toString().split(",")):null);
		param.put("orgAssetType2s", StringUtil.isNotEmpty(param.get("orgAssetType2"))?(param.get("orgAssetType2").toString().split(",")):null);
		List<AssetInfoDetailDto> assetInfoList = assetInfoDetailMapper.selectList(param);
		this.dealAfterSearch(assetInfoList);
		return assetInfoList;
	}

	public List<AssetInfoDetailDto> selectBykeys(List<String> assetUuids) {
		List<AssetInfoDetailDto> assetInfoList = assetInfoDetailMapper.selectBykeys(assetUuids);
		this.dealAfterSearch(assetInfoList);
		return assetInfoList;
	}

	/**
	 * 查询之后处理查询到的数据
	 * @param assetInfoList
	 * @return
	 */
	public static List<AssetInfoDetailDto> dealAfterSearch(List<AssetInfoDetailDto> assetInfoList) {
		if (CollectionUtils.isNotEmpty(assetInfoList)) {
			assetInfoList.forEach(item -> {
				if(StringUtil.isNotEmpty(item.getInAssetType())){
					item.setInAssetType(item.getInAssetType() + " " + SysDataUtil.getNameFromDataDict(DictCode.行内资产类别, item.getInAssetType()));
				}
			});
		}
		return assetInfoList;
	}
}
