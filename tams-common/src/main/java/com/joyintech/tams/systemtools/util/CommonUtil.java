/*
 * 文件名：CommonUtil.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：王波
 * 修改时间：2019年3月1日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.tams.systemtools.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joyintech.framework.util.*;
import com.joyintech.tams.common.base.entity.ChbOrgRegistBankCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.joyintech.framework.constant.DictCodeList;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.jdbc.datasource.DatabaseInfo;
import com.joyintech.framework.jdbc.datasource.DatabaseType;
import com.joyintech.framework.jdbc.datasource.DatabaseTypeHelper;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.security.User;
import com.joyintech.framework.validation.ValidationConstant;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.dao.TamsBaseMapper;
import com.joyintech.tams.systemtools.dto.SystemActorRoleDto;
import com.joyintech.tams.systemtools.listener.TamsAuthorizationListener;
import com.joyintech.tams.systemtools.service.SystemToolService;
import com.joyintech.tams.systemtools.service.TamsBaseCommonService;

@Lazy(false)
@Component
public class CommonUtil {
	
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);
	/**
	 * ENGLISH_PATTERN
	 */
	private static Pattern ENGLISH_PATTERN = Pattern.compile(ValidationConstant.PATTERN_EN_NUMBER);

	/**
	 * FLOAT_NUMERIC_PATTERN
	 */
	private static Pattern FLOAT_NUMERIC_PATTERN = Pattern.compile("^[0-9\\-\\.]+$");

	/**
	 * BaseMapper
	 */
	private static TamsBaseMapper baseMapper;

	/**
	 * 角色权限service
	 */
	private static SystemToolService systemActorRoleService;

	/**
	 * 角色权限service
	 */
	private static TamsBaseCommonService baseCommonService;
	
	
	
	private static LoginUserUtil loginUserUtil;
	
	/**
	 * 联社默认机构代码
	 */
	private static String ASSOCIATION_INSTITUTION_CODE = "ALL";

	/**
	 * 注入角色权限service
	 *
	 * @param systemActorRoleService
	 *            角色权限service
	 */
	@Autowired
	public void setBaseMapper(SystemToolService systemActorRoleService) {
		CommonUtil.systemActorRoleService = systemActorRoleService;
	}

	/**
	 * 注入BaseMapper
	 *
	 * @param baseMapper
	 *            BaseMapper
	 */
	@Autowired
	public void setBaseMapper(TamsBaseMapper baseMapper) {
		CommonUtil.baseMapper = baseMapper;
	}

	/**
	 * 注入BaseCommonService
	 *
	 * @param baseCommonService
	 *            BaseCommonService
	 */
	@Autowired
	public void setBaseCommonService(TamsBaseCommonService baseCommonService) {
		CommonUtil.baseCommonService = baseCommonService;
	}

	/**
	 * 数据权限用所属机构取得
	 *
	 * @return 所属机构
	 * @see
	 * @since 1.0
	 */
	public static List<String> getOrgCodeListForSearch() {
		return new ArrayList<String>();
	}

	/**
	 * 获取对象的非空字符串<br>
	 * 如果该对象为空，则返回空字符串
	 *
	 * @param value
	 *            对象
	 * @return 字符串
	 */
	public static final String getNotNullString(Object value) {
		return StringUtil.getOrElse(value, StringUtil.EMPTY);
	}

	/**
	 * 获取当前登录用户的ORG_CODE
	 *
	 * @return
	 */
	public static final String getOrgCode() {
		try {
			return SecurityUtil.getLoginUser().getOrgCode();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取当前登录用户的DEPT_CODE
	 *
	 * @return
	 */
	public static final String getDeptCode() {
		try {
			User user = SecurityUtil.getLoginUser();
			return user.getDeptCode();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 取得字符串的实际长度（考虑了汉字的情况）
	 *
	 * @param srcStr
	 *            源字符串
	 * @return 字符串的实际长度
	 */
	public static final int getStringLen(String srcStr) {
		int returnValue = 0;
		if (srcStr != null) {
			byte[] byteArr = srcStr.getBytes(Charset.forName("UTF-8"));
			returnValue = byteArr.length;
		}
		return returnValue;
	}

	/**
	 * 判断是否数字字母组合
	 *
	 * @param src
	 *            源字符串
	 * @return 是否数字字母组合的标志
	 */
	public static boolean isEnglish(String src) {
		boolean returnValue = false;
		if (src != null && src.length() > 0) {
			Matcher m = ENGLISH_PATTERN.matcher(src);
			if (m.find()) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	/**
	 * 查询登记银行代码（获取当前用户所属机构的登记银行代码）
	 * 
	 * @return 结果list
	 * @see
	 * @since 1.0
	 */
	public static String getUserBankCode(String orgCode) {
		if(StringUtil.isBlank(orgCode)){
			User userInfo = SecurityUtil.getLoginUser();
			if (StringUtil.isEmpty(userInfo.getOrgCode())) {
				return null;
			}
			orgCode = userInfo.getOrgCode();
		}
		String bankCode = baseMapper.selectBankCode(orgCode);
		return bankCode;
	}





	public static ChbOrgRegistBankCode getUserBank() {
		User userInfo = SecurityUtil.getLoginUser();
		String OrgCode = null;
		// if(userInfo.isAdmin()){
		// return null;
		// }
		if (null!=userInfo&&StringUtil.isEmpty(userInfo.getOrgCode())) {
			OrgCode = userInfo.getOrgCode();
		}
		List<ChbOrgRegistBankCode> orgCode = baseMapper.selectBank(OrgCode);
		return orgCode.get(0);
	}

	/**
	 * 判断是否浮点数字表示
	 *
	 * @param src
	 *            源字符串
	 * @return 是否数字的标志
	 */
	public static boolean isFloatNumeric(String src) {
		boolean returnValue = false;
		if (src != null && src.length() > 0) {
			Matcher m = FLOAT_NUMERIC_PATTERN.matcher(src);
			if (m.find()) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	/**
	 * 根据採番名採番
	 *
	 * @param sequenceType
	 *            採番序列类型
	 * @return 採番值
	 * @see
	 */
	public static String getSequenceIndexData(DataIndexSequenceType sequenceType) {
		if(isMYSQLDataBase()&&StringUtil.equals("CHB_FILE_READ_MONITOR_SEQNO",sequenceType.getSequenceName())){
			String sequenceResult = IDGenerator.generate();
			return sequenceResult;
		}else{
			Map<String, String> param = new HashMap<>();
			String databaseType = CacheUtil.getAppParamCache(GlobalConstant.SYS_DEPARTMENT_KEY, "database_type_code");

			databaseType = StringUtil.defaultString(databaseType, "Oracle");

			param.put("databaseType", databaseType);
			param.put("sequenceName", sequenceType.getSequenceName());
			Long result = baseMapper.getSequenceIndexData(param);

			String sequenceResult = String.format("%018d", result);

			// 加在採番后结果的前缀，以保证各个sequence的採番结果不会出现重复的情况
			String shortName = sequenceType.getShortName();
			sequenceResult = shortName + sequenceResult;

			return sequenceResult;
		}

	}
	
	/**
	 * 获取批量序列值
	 * @param sequenceType
	 * @param size
	 * @return
	 */
	public  static  List<String> getBatchSequenceIndexData(DataIndexSequenceType sequenceType, int size) {
		if(size <= 0) {
			return new ArrayList<>();
		}

		List<String> list = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		
		String databaseType = CacheUtil.getAppParamCache(GlobalConstant.SYS_DEPARTMENT_KEY, "database_type_code");

		databaseType = StringUtil.defaultString(databaseType, "Oracle");
		
		param.put("databaseType", databaseType);
		param.put("sequenceName", sequenceType.getSequenceName());
		
		synchronized(CommonUtil.class){
     	Long result = baseMapper.getSequenceIndexData(param);
		// 加在採番后结果的前缀，以保证各个sequence的採番结果不会出现重复的情况
		String shortName = sequenceType.getShortName();
		for(int i = 0;i<size;i++) {
			Long data = result + i;//i*步长，默认步长为1
			String sequenceResult = String.format("%018d", data);
			sequenceResult = shortName + sequenceResult;
			list.add(sequenceResult);
		}
		param.put("incrNum", size+"");
		baseMapper.updateSequenceIndexData(param);
	}
		return list;
	}
	
	
    /**
     * 获取序列
     * @param list
     * @param index
     * @return
     */
    public  static String getSequenceStr(List<String> list, int index) {
        if(StringUtil.isEmptyList(list) || index >= list.size()) {
            return null;
        }
        else {
            return list.get(index);
        }
    }
	/**
	 * 根据採番名採番
	 *
	 * @param sequenceType
	 *            採番序列类型
	 * @return 採番值
	 * @see
	 */
	public static String getSequenceIndexData(DataIndexSequenceType sequenceType , String format) {
		if(isMYSQLDataBase()){
			String sequenceResult = IDGenerator.generate();
			return sequenceResult;
		}{
			Map<String, String> param = new HashMap<>();
			String databaseType = CacheUtil.getAppParamCache(GlobalConstant.SYS_DEPARTMENT_KEY, "database_type_code");
			databaseType = StringUtil.defaultString(databaseType, "Oracle");

			param.put("databaseType", databaseType);
			param.put("sequenceName", sequenceType.getSequenceName());
			Long result = baseMapper.getSequenceIndexData(param);

			String sequenceResult = String.format(format, result);

			// 加在採番后结果的前缀，以保证各个sequence的採番结果不会出现重复的情况
			String shortName = sequenceType.getShortName();
			sequenceResult = shortName + sequenceResult;

			return sequenceResult;
		}
	}

	/**
	 *
	 * 根据採番名採番
	 * @param sequenceType
	 * @return
	 * @see
	 * @since 1.0
	 */
	public static long getSequenceLong(DataIndexSequenceType sequenceType) {
		Map<String, String> param = new HashMap<>();
		String databaseType = CacheUtil.getAppParamCache(GlobalConstant.SYS_DEPARTMENT_KEY, "database_type_code");

		databaseType = StringUtil.defaultString(databaseType, "Oracle");

		param.put("databaseType", databaseType);
		param.put("sequenceName", sequenceType.getSequenceName());
		Long result = baseMapper.getSequenceIndexData(param);
		return result;
	}

	/**
	 * 是否联社模式
	 * @return
	 */
	public static boolean isBAmodel(){
		 return StringUtil.equals(DictCodeList.权限模式_联社模式.getValue(), CacheUtil.getSysParam(GlobalConstant.AUTHORITY_TYPE))
	                && StringUtil.equals(DictCodeList.是否_是.getValue(), CacheUtil.getSysParam(GlobalConstant.DATA_AUTHORITY));
	}
	
	/**
	 * 主要功能:判断字符串是否为日期 注意事项:无
	 *
	 * @param str
	 *            日期字符串 yyyyMMdd
	 * @return 是否
	 */
	public static boolean isDateStrE(String str) {
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.DEFAULT_OTHER_FORMAT);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
	 *
	 * @param s1
	 *            参数1
	 * @param s2
	 *            参数2
	 * @return 是否
	 */
	public static boolean equalsIgnoreNull(String s1, String s2) {
		if (StringUtil.isBlank(s1) && StringUtil.isBlank(s2)) {
			return true;
		}

		return StringUtil.equals(s1, s2);
	}

	/**
	 * 去掉下划线，并把下划线后一个字母大写
	 *
	 * @param value
	 *            String
	 * @param needCase
	 *            是否需要转小写
	 * @return String
	 */
	public static String dealString(final String value, boolean needCase) {
		String temp = value;
		if (StringUtil.isNotEmpty(temp) && needCase) {
			temp = temp.toLowerCase();
		}
		StringBuilder result = new StringBuilder();
		if (temp.contains("_")) {
			result.append(temp.substring(0, temp.indexOf("_")));
			if (temp.indexOf("_") + 1 <= temp.length() && temp.indexOf("_") + 2 <= temp.length()) {
				result.append(temp.substring(temp.indexOf("_") + 1, temp.indexOf("_") + 2).toUpperCase());
			}
			if (temp.indexOf("_") + 2 <= temp.length()) {
				result.append(temp.substring(temp.indexOf("_") + 2));
			}
			temp = dealString(result.toString(), false);
		} else {
			return temp;
		}

		return temp;
	}

	/**
	 * 数据权限用所属部门取得
	 * 
	 * @author add by wm 2018-03-14
	 * @return 所属部门
	 * @see
	 * @since 1.0
	 */
	public static List<String> getDeptCodeListForSearch() {
		List<String> deptCodeList = new ArrayList<String>();

		// User userInfo = SecurityUtil.getLoginUser();
		// if (userInfo == null || userInfo.isAdmin()) {
		// return deptCodeList;
		// }
		//
		// String myDeptCode = userInfo.getDeptCode();
		// if (StringUtil.isBlank(myDeptCode)) {
		// return deptCodeList;
		// }
		// deptCodeList = baseMapper.selectDeptCode(myDeptCode);

		return deptCodeList;
	}

	/**
	 * 首字母小写
	 *
	 * @param realName
	 *            源字符串
	 * @return 首字母小写
	 */
	public static String firstLowerCase(String realName) {
		return StringUtil.replaceOnce(realName, realName.substring(0, 1), realName.substring(0, 1).toLowerCase());
	}

	/**
	 * 获取用户名下的所有的有权限的机构和部门
	 * 
	 * @param userName
	 *            想要获取的用户的用户名
	 * @return 所属部门
	 * @see
	 * @since 1.0
	 */
	public static List<String> getAuthorityDeptByUserName(String userName) {
		@SuppressWarnings("unchecked")
		List<String> deptCodeList = (List<String>) SecurityUtil
				.getFromSession(TamsAuthorizationListener.USER_AUTHORITY_DEPT_CODE);
		if (null != deptCodeList) {
			return deptCodeList;
		}

		if (StringUtil.isEmpty(userName)) {
			userName = SecurityUtil.getLoginUser().getUserName();
		}
		// 权限控制
		List<SystemActorRoleDto> tempList = systemActorRoleService.selectDataModuleByUserName(userName);
		Set<String> createDeptList = new HashSet<>();
		tempList.forEach(action -> {
			if (StringUtil.equals(action.getDeptCode(), "99")) {
				createDeptList.addAll(baseCommonService.selectDeptCode(action.getInstitutionCode()));
			} else {
				createDeptList.add(action.getDeptCode());
			}
			createDeptList.add(action.getInstitutionCode());
		});
		if (StringUtil.isNotEmpty(SecurityUtil.getLoginUser().getDeptCode())) {
			createDeptList.add(SecurityUtil.getLoginUser().getDeptCode());
		}

		List<String> resultList = new ArrayList<String>(createDeptList);
		// 放入Session
		SecurityUtil.addSession(TamsAuthorizationListener.USER_AUTHORITY_DEPT_CODE, resultList);

		return resultList;
	}

	/**
	 * 获取用户名下的所有的有权限的机构
	 * 
	 * @param userName
	 *            想要获取的用户的用户名
	 * @return 用户名下的所有的有权限的机构
	 * @see
	 * @since 1.0
	 */
	public static List<String> getAuthorityOnlyOrgListByUserName(String userName) {
		if (StringUtil.isEmpty(userName)) {
			userName = SecurityUtil.getLoginUser().getUserName();
		}
		// 权限控制
		List<SystemActorRoleDto> tempList = systemActorRoleService.selectDataModuleByUserName(userName);
		List<String> resultList = new ArrayList<>();
		tempList.forEach(action -> {
			if (StringUtil.isNotEmpty(action.getInstitutionCode())
					&& !resultList.contains(action.getInstitutionCode())) {
				resultList.add(action.getInstitutionCode());
			}
		});
		return resultList;
	}

	/**
	 * 获取用户名下的所有的有权限的部门
	 * 
	 * @param userName
	 *            想要获取的用户的用户名
	 * @return 用户名下的所有的有权限的机构
	 * @see
	 * @since 1.0
	 */
	public static List<String> getAuthorityOnlyDeptListByUserName(String userName) {
		if (StringUtil.isEmpty(userName)) {
			userName = SecurityUtil.getLoginUser().getUserName();
		}
		// 权限控制
		List<SystemActorRoleDto> tempList = systemActorRoleService.selectDataModuleByUserName(userName);
		List<String> resultList = new ArrayList<>();
		tempList.forEach(action -> {
			if (StringUtil.isNotEmpty(action.getDeptCode()) && !resultList.contains(action.getDeptCode())) {
				resultList.add(action.getDeptCode());
			}
		});
		return resultList;
	}

	/**
	 * 判断系统使用的数据库是否是db2
	 *
	 * @return 系统使用的数据库是否是db2
	 * @see
	 * @since 1.0
	 */
	public static boolean isDB2DataBase() {
		try {
			Map<String, List<DatabaseInfo>> databaseType = DatabaseTypeHelper.getDatabaseType();

			List<DatabaseInfo> dsList = databaseType.get(DataSourceType.MASERT);
			if (dsList != null && !dsList.isEmpty()) {
				for (int i = 0; i < dsList.size(); i++) {
					DatabaseInfo dbInfo = dsList.get(i);
					if (StringUtil.equalsIgnoreCase(DatabaseType.DATABASE_TYPE_DB2.getValue(),
							dbInfo.getDbType().getValue())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return false;
	}

	public static MultipartFile getFileFromRequest(MultipartHttpServletRequest multiRequest, String filePath) {
		if (multiRequest == null || StringUtil.isBlank(filePath)) {
			return null;
		}

		return multiRequest.getFile(filePath);
	}

	/**
	 * 
	 * 获取class中指定字段名称的值
	 * 
	 * @param data
	 *            实体类
	 * @param fieldName
	 *            字段名称
	 * @return
	 * @see
	 * @since 1.0
	 */
	public static Object getValueByClassNameObject(Object data, String fieldName, Class T)
			throws IntrospectionException, NoSuchFieldException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Field field = data.getClass().getDeclaredField(fieldName);
		PropertyDescriptor pd = new PropertyDescriptor(field.getName(), T);
		Method getMethod = pd.getReadMethod();// 获得get方法
		Object o = getMethod.invoke(data);// 执行get方法返回一个Object
		return o;
	}

	/**
     * 
     * 获取class中指定字段名称的值
     * 如果返回数据时Date类型则转换成格式为"yyyy-MM-dd"的String
     * @param data
     *            实体类
     * @param fieldName
     *            字段名称
     * @return
     * @see
     * @since 1.0
     */
    public static Object getValueByClassName(Object data, String fieldName, Class<?> T)
            throws IntrospectionException, NoSuchFieldException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Field field = data.getClass().getDeclaredField(fieldName);
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), T);
        Method getMethod = pd.getReadMethod();// 获得get方法
        Object o = getMethod.invoke(data);// 执行get方法返回一个Object
        if(o instanceof Date){
            o=DateUtil.format((Date)o, DateUtil.DEFAULT_DATE_FORMAT_EN);
        }
        return o;
    }
    
    
    
    /**
     * 获取当前登录用户的U_ID
     *
     * @return
     */
    public static final String getUid() {
        try {
            User user = SecurityUtil.getLoginUser();
            if (user != null){
                return user.getUid();
            } else {
                // 默认null
                logger.debug("获取uid失败：当前登录用户不存在！");
                return null;
            }
        }
        catch (Exception e) {
            logger.error("获取uid失败:"+e.getMessage(),e);
            return null;
        }
    }
    
    
    /**
     * 根据key获取对应缓存信息
     * @param key 缓存key值(如:机构-GlobalConstant.SYS_INSTITUTION_KEY)
     * @param code
     * @return
     */
    public static Object queryCode(String key,String code){		
		Map Map = CacheUtil.getAppCache(key, Map.class);
		return Map.get(code);
	}
   
    /**
     * 获取当前登录用户选择查看的机构列表
     */
    public static List<String> getTreeInstCode(){
    	return loginUserUtil.getTreeInstCode();
    }
    
    /**
     * 获取当前登录用户的名称
     *
     * @return
     */
    public static final String getUserName() {
        try {
            User user = SecurityUtil.getLoginUser();
            if (user != null){
                return user.getUserName();
            } else {
                // 默认system
                return GlobalConstant.SYSTEM_USER;
            }
        }
        catch (Exception e) {
            // 默认system
            return GlobalConstant.SYSTEM_USER;
        }
    }
    
    /**
     * 判断是否含有汉字
     *
     * @param value 判断对象
     * @return 含汉字true,否则false
     * @since 1.0
     */
    public static boolean hasChinese(String value) {
        if (StringUtil.isEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[^\u4e00-\u9fa5]+");
        Matcher matcher = pattern.matcher(value);
        return !matcher.matches();
    }
    
    
//    /**
//	 * 设置下拉框联社关联数据
//	 * @param objectMap 参数
//	 */
//	public static void setInstitutionInfo(Map<String,Object> objectMap) {
//		objectMap.put("modelType", "01");
//		objectMap.put("modelTypeUD", "02");
//		objectMap.put("institution",SecurityUtil.getFromSession("_sk_institutioncode_"));
//	}
//	
//
//	public static String getInstitutionCode() {
//		String institutionCode = "ALL";
//		String institutionType = baseCommonService.getInstitutionType((String)SecurityUtil.getFromSession("_sk_institutioncode_"));
//		if(!StringUtil.equals(institutionType,"01")){
//			institutionCode = (String)SecurityUtil.getFromSession("_sk_institutioncode_");
//		}
//		return institutionCode;
//	}
//
//	/**
//	 * 添加二级法人查询参数
//	 * 
//	 * @param param
//	 */
//	public static void setInstitutionMap(Map<String,Object> param){
//    	param.put("modelType", DictCodeListExt3.联社数据权限模式_查询.getValue());
//    	param.put("modelTypeUD", DictCodeListExt3.联社数据权限模式_修改及删除.getValue());
//    	param.put("institution", SecurityUtil.getLoginUser().getOrgCode());//机构代码
//	}
//
//	/**
//	 * 根据登录用户的机构类型组合查询参数
//	 * 
//	 * @param pageParam
//	 */
//	public static void setInstitutionMapByType(Map<String,Object> pageParam){
//		String insitutionType = baseCommonService.getInsitutionType();
//		if(StringUtil.equals(DictCodeListExt3.联社机构类型_省级机构.getValue(), insitutionType)){
//    		pageParam.put("institutionType", insitutionType);
//		}else{
//    		pageParam.put("institutionType", insitutionType);
//    		pageParam.put("institution", SecurityUtil.getLoginUser().getOrgCode());
//    	}
//	}
//
//	/**
//	 * 根据登录用户获取机构代码
//	 *
//	 * @return
//	 */
//	public static String getInstitutionCodeByLoginUser(){
//		if(!StringUtil.equals(DictCodeListExt3.联社机构类型_省级机构.getValue(), baseCommonService.getInsitutionType())){
//			return SecurityUtil.getLoginUser().getOrgCode();
//		}
//		return ASSOCIATION_INSTITUTION_CODE;
//	}

	
	
	
	
	// 微服务版本获取登录用户的机构
	/**
	 * 根据登录用户获取机构代码
	 *
	 * @return
	 */
	public static String getInstitutionCodeByLoginUser_ms(){
		if(!StringUtil.equals(DictCodeListExt3.联社机构类型_省级机构.getValue(), baseCommonService.getInsitutionType_ms())){
			return baseCommonService.getInsitutionCode_ms();
		}
		return ASSOCIATION_INSTITUTION_CODE;
	}
	
	/**
	 * 根据用户获取所属机构
	 * @param userName
	 * @return
	 */
	public static String getInsitutionCodeByUserName(String userName){
		return baseCommonService.getInsitutionCodeByUserName(userName);
	}
	
	/**
	 * 根据用户获取所属机构类型
	 * @param userName
	 * @return
	 */
	public static String getInsitutionTypeByUserName(String userName){
		return baseCommonService.getInsitutionTypeByUserName(userName);
	}
	
	
	/**
	 * 根据登录用户获取当前机构的登记编码
	 * “机构登记银行代码管理”模块没有维护当前机构机构代码时，也为空，提交时提醒，现在这种情况还是展示默认值只C1111； update by leipeng 20200727
	 * @return
	 */
	public static String selectBankCode(){

		String orgCode = "";
		if(MultilevelDataAuthorityUtil.isEnabled()){
			orgCode = getInstitutionCodeByLoginUser_ms();
		}else{
			// 银行模式下 只有一个总行  直接查总行对应的 code
			orgCode = baseCommonService.getYHGeneralBankCode();
		}

		String code = baseCommonService.selectBankCode(orgCode);
		if(MultilevelDataAuthorityUtil.isEnabled() && StringUtil.isEmpty(code)){
			code=CacheUtil.getSysParam("SVR_ISSUE_PARTY_ID");
		}
		return null!=code?code:"";
	}
	public static String selectBankCodeName(){

		String orgCode = "";
		if(MultilevelDataAuthorityUtil.isEnabled()){
			orgCode = getInstitutionCodeByLoginUser_ms();
		}else{
			// 银行模式下 只有一个总行  直接查总行对应的 code
			orgCode = baseCommonService.getYHGeneralBankCode();
		}
		String codeName = baseCommonService.selectBankCodeName(orgCode);
		if(MultilevelDataAuthorityUtil.isEnabled() && StringUtil.isEmpty(codeName)){
			codeName=CacheUtil.getSysParam("SVR_ISSUE_PARTY_NAME");
		}
		return null!=codeName?codeName:"";
	}

	/**
	 * 根据登录用户获取当前机构的登记编码
	 * “机构登记银行代码管理”模块没有维护当前机构机构代码时，也为空，提交时提醒，现在这种情况还是展示默认值只C1111； update by leipeng 20200727
	 * @return
	 */
	public static String selectPbcBankCode(String orgCode){
		if(StringUtil.isEmpty(orgCode)){
			if(MultilevelDataAuthorityUtil.isEnabled()){
				orgCode = getInstitutionCodeByLoginUser_ms();
			}else{
				// 银行模式下 只有一个总行  直接查总行对应的 code
				orgCode = baseCommonService.getYHGeneralBankCode();
			}
		}
		String code = baseCommonService.selectPbcBankCode(orgCode);
		return null!=code?code:"";
	}

	/**
	 * 根据登录用户获取当前机构的登记编码
	 * “机构登记银行代码管理”模块没有维护当前机构机构代码时，也为空，提交时提醒，现在这种情况还是展示默认值只C1111； update by leipeng 20200727
	 * @return
	 */
	public static String selectPbcBankCodeBack(String pbcBankCode){
		String code = baseCommonService.selectPbcBankCodeBack(pbcBankCode);
		return null!=code?code:"";
	}
	
	
	// 微服务版本获取登录用户的机构类型
	/**
	 * 根据登录用户获取机构类型
	 *
	 * @return
	 */
	public static String getInstitutionTypeByLoginUser_ms(){
		if(!StringUtil.equals(DictCodeListExt3.联社机构类型_省级机构.getValue(), baseCommonService.getInsitutionType_ms())){
			return baseCommonService.getInsitutionCode_ms();
		}
		return ASSOCIATION_INSTITUTION_CODE;
	}
	
	/**
	 * 微服务 中组装查询参数
	 * 根据登录用户的机构类型组合查询参数
	 * 
	 * @param pageParam
	 */
	public static void setInstitutionMapByType_ms(Map<String,Object> pageParam){
		
		if(null == pageParam){
			pageParam = new HashMap<String,Object>(16);
		}
		//判断开关
		if(MultilevelDataAuthorityUtil.isEnabled()){
			String insitutionType = baseCommonService.getInsitutionType_ms();
			if(StringUtil.equals(DictCodeListExt3.联社机构类型_省级机构.getValue(), insitutionType)){
	    		pageParam.put("institutionType", insitutionType);
			}else{
	    		pageParam.put("institutionType", insitutionType);
	    		pageParam.put("institution", baseCommonService.getInsitutionCode_ms());
	    	}
		}
	}
	
	
	/**
	 * 微服务 中获取所有父级机构
	 * 获取所有父级机构
	 * 
	 * @param pageParam
	 */
	public static List<String> getParentInstitution(String institutionCode){
		return baseCommonService.getParentInstitution(institutionCode);
	}

	/**
	 * 通过 业务id dataTYpe 获取创建机构
	 * 02的数据仅有一条
	 * @param businessId
	 * @param dataType
	 * @return
	 */
	public static String getCreateInstByID(String businessId,String dataType){
		return baseCommonService.getCreateInstByID(businessId,dataType);
	}


	/**
	 * 获取省联社机构 联社模式仅有一个省联社
	 * @return
	 */
	public static String getYHGeneralBankCode(){
		return baseCommonService.getYHGeneralBankCode();
	}
	
	/**
	 * 主要功能:判断字符串是否为日期 注意事项:无
	 *
	 * @param str
	 *            日期字符串 yyyy-MM-dd
	 * @return 是否
	 */
	public static boolean isEnDateStr(String str) {
		SimpleDateFormat format = new SimpleDateFormat(DateUtil.DEFAULT_DATE_FORMAT_EN);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 现金流代码生成
	 * @param busiNo 业务代码
	 * @param busiType 业务类型
	 * @param branch 分支序号
	 * @param busiType2 现金流二级类型
	 * @return 现金流代码
	 */
	public static String generateCashFlowCode(String busiNo, String busiType, long branch,
											  String busiType2) {
		final String sepChar = String.valueOf((char)256);

		String tmpStr = StringUtil.joinString(
				Arrays.asList(StringUtil.defaultString(busiNo), StringUtil.defaultString(busiType),
						String.valueOf(branch), StringUtil.defaultString(busiType2)),
				sepChar);

		return EncryptUtil.getInstance().md5(tmpStr);
	}

	/**
	 * 判断指定的字符串是否是数字
	 * @param str
	 * @return
	 * @see
	 * @since 1.0
	 */
	public static boolean isNumeric(String str) {
		if (str == null || str.trim().equals(""))
			return false;
		if (str.endsWith("."))  //不想调换复杂的正则表达式，对于[3.]这种数据也认为是合法的数字
			str = str.substring(0,str.length()-1);
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}


	/**
	 * 判断系统使用的数据库是否是mysql
	 *
	 * @return 系统使用的数据库是否是mysql
	 * @see
	 * @since 1.0
	 */
	public static boolean isMYSQLDataBase() {
		try {
			Map<String, List<DatabaseInfo>> databaseType = DatabaseTypeHelper.getDatabaseType();

			List<DatabaseInfo> dsList = databaseType.get(DataSourceType.MASERT);
			if (dsList != null && !dsList.isEmpty()) {
				for (int i = 0; i < dsList.size(); i++) {
					DatabaseInfo dbInfo = dsList.get(i);
					if (StringUtil.equalsIgnoreCase(DatabaseType.DATABASE_TYPE_MYSQL.getValue(),
							dbInfo.getDbType().getValue())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return false;
	}
}
