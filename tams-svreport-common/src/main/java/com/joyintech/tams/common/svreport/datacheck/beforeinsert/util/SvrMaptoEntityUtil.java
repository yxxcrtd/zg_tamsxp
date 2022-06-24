package com.joyintech.tams.common.svreport.datacheck.beforeinsert.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.jdbc.datasource.DatabaseInfo;
import com.joyintech.framework.jdbc.datasource.DatabaseType;
import com.joyintech.framework.jdbc.datasource.DatabaseTypeHelper;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.ChbAssetInfo;
import com.joyintech.tams.report.interf.common.constant.DateUtil;

/***
 * 中债一二期共通方法
 * @author dubaojun
 * @since 2020-10-13
 *
 */
@Service
public class SvrMaptoEntityUtil {
	
	
	
    private static Logger logger = LoggerFactory.getLogger(SvrMaptoEntityUtil.class);

	/***
	 * List<Map<String, String>>转为dto方法
	 * @param infoList    数据Map集合
	 * @param listCloumn  数据库字段集合
	 * Class<T> tClass    实体类类类型
	 * @return 实体类集合
	 */
	public static <T> List<T> convertMapToEntity(List<Map<String, String>> infoList, List<String> listCloumn
			,Class<T> tClass) {
		    //定义泛型返回值
            List<T> entities=new ArrayList<T>();
            //对于输入数据进行循环处理
			for (Map<String, String> map : infoList) {
				try {
					//根据传入类型新建实例
					T  cla= tClass.newInstance();
					//循环处理数据库字段
					for (String string : listCloumn) {
						String value = map.get(string);
						if (value == null) {
							continue;
						}
						//根据数据库字段获取类的属性字段
						Field field = ReflectionUtils.findField(cla.getClass(), subBlank(StringUtil.lowerCase(string)));
						//如果字段没有则跳过。
						if(field==null){
		                    logger.warn("该字段在所转换的实体类中没有，对象名：{}，字段名：{}", tClass.getName(), string);
							continue;
						}
						field.setAccessible(true);
						//获取属性对应类型
						Type type = field.getGenericType();
						//对于BigDecimal类型处理
						if ("java.math.BigDecimal".equals(type.getTypeName())) {
							try {
								if (StringUtil.isNotEmpty(value)) {
									//对类的实例字段进行赋值
									field.set(cla, new BigDecimal(value));
								}
							} catch (IllegalAccessException e) {
								 logger.error(e.getMessage(),e);
								return entities;
							}
							//对于Integer类型处理
						} else if ("java.lang.Integer".equals(type.getTypeName()) || "int".equals(type.getTypeName())) {
							try {
								if (StringUtil.isNotEmpty(value)) {
									//对类的实例字段进行赋值
									field.set(cla, Integer.valueOf(value));
								}
							} catch (IllegalAccessException e) {
								 logger.error(e.getMessage(),e);
								return entities;
							}
							//对于Long类型处理
						} else if ("java.lang.Long".equals(type.getTypeName())) {
							try {
								if (StringUtil.isNotEmpty(value)) {
									//对类的实例字段进行赋值
									field.set(cla, Long.valueOf(value));
								}
							} catch (IllegalAccessException e) {
								 logger.error(e.getMessage(),e);
								return entities;
							}
							//对于Short类型处理
						} else if ("java.lang.Short".equals(type.getTypeName())) {
							try {
								if (StringUtil.isNotEmpty(value)) {
									//对类的实例字段进行赋值
									field.set(cla, Short.valueOf(value));
								}
							} catch (IllegalAccessException e) {
								 logger.error(e.getMessage(),e);
								return entities;
							}
							//对于Date类型处理
						} else if ("java.util.Date".equals(type.getTypeName())) {
							if (value != null && "" != value) {
								try {
									//对类的实例字段进行赋值
									field.set(cla, DateUtil.parse(value));
								} catch (IllegalAccessException e) {
									 logger.error(e.getMessage(),e);
									return entities;
								}
							}
						} else {
							//其它类型处理
							try {
								//对类的实例字段进行赋值
								if(BaseCommonCheckService.isMYSQLDataBase()) {
									field.set(cla, (value == null || "".equals(value)) ? null : value);
								}else{
									field.set(cla, (value == null || "".equals(value)) ? "" : value);
								}
							} catch (IllegalAccessException e) {
								 logger.error(e.getMessage(),e);
								return entities;
							}
						}
					}
					entities.add(cla);
				} catch (Exception e) {
					 logger.error(e.getMessage(), e);
				}

			}
		return entities;
	}

	
	
	/**
	 * 功能:将表字段转换成属性字段
	 * 
	 * @since 
	 * @version 
	 * @author：
	 * @param str
	 * @return String
	 */
	public static String subBlank(String str) {
		if (str == null) {
			return null;
		}
		String[] strarray = str.split("_");
		if (strarray.length == 0) {
			return null;
		}
		String result = strarray[0];
		for (int i = 1; i < strarray.length; i++)
			result = result + (new StringBuilder()).append(Character.toUpperCase(strarray[i].charAt(0)))
					.append(strarray[i].substring(1)).toString();
		return result;
	}
	
	
	/***
	 * listClass   获取的临时表dto集合
	 * listCloumn  数据库表字段集合
	 * 将所有List<T>类型转为list<map>类型
	 * @param <T>
	 * @return  List<Map<String, String>> 数据格式
	 */
	public static <T> List<Map<String, String>>   convertEntityToMap(List<T> listClass,List<String> listCloumn){
		//定义返回值 list
		List<Map<String, String>> list=  new  ArrayList<>();
		try{
	    //对输入的数据类型进行循环处理
		for(T clazz:listClass){
			Map<String, String>  map=new HashMap<>();
			//对数据库字段进行循环处理
			for(String  str:listCloumn){
				//根据数据库字段映射类属性
				Field field = ReflectionUtils.findField(clazz.getClass(), subBlank(StringUtil.lowerCase(str)));
                //如果字段没有则跳过。
                if(field==null) {
                    logger.warn("该字段在所转换的实体类中没有，对象名：{}，字段名：{}", clazz.getClass().getName(), str);
                    continue;
                }
				field.setAccessible(true);
				//获取字段java类型
				Type type = field.getGenericType();
				//对于date类型的需要特殊转换一下
				if ("java.util.Date".equals(type.getTypeName())) {
					// add by zhangliang 20201104 对于create_time或者update_time需要保留具体的时分秒 start
					// update by zoujiamin  20210628  新增SUBMIT_DEADLINE 、CHB_SUBMIT_DEADLINE 保留时分秒  TISHKBS-2240
					if (StringUtil.equals("CREATE_TIME", str) || StringUtil.equals("UPDATE_TIME", str) || StringUtil.equals("SUBMIT_DEADLINE", str) || StringUtil.equals("CHB_SUBMIT_DEADLINE", str) ){
						map.put(str, DateUtil.formatDateTime((Date)field.get(clazz)));
					}else{
						map.put(str, DateUtil.format((Date)field.get(clazz)));
					}
					// add by zhangliang 20201104 对于create_time或者update_time需要保留具体的时分秒 end
				}else {
					//其它类型直接toString可以实现转为字符串
					if(field.get(clazz)!=null){
						map.put(str, field.get(clazz).toString());
					}else {
						// mysql对空字符串特殊处理
						if(BaseCommonCheckService.isMYSQLDataBase()){
							map.put(str, null);
						}else{
							map.put(str, "");
						}
					}
					
				}
				
			}
		  list.add(map);
		}
		
	} catch (Exception e) {
		 logger.error(e.getMessage(), e);
	}
		
		return list;
	}
	
	
	
	
//	/**
//	 * 测试方法
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		//数据list
//		List<Map<String, String>> infoList=new  ArrayList<>();
//		Map<String, String>  map1=new  HashMap<String, String>();
//		ChbAssetInfo  ChbAssetInfo= new  ChbAssetInfo();
//		map1.put("ASSET_CODE", "dxyceshi111");
//		map1.put("ASSET_TYPE", "02");
//		map1.put("VDATE", "2020-09-15");
//		map1.put("AMT", "100");
//		infoList.add(map1);
//		Map<String, String>  map2=new  HashMap<String, String>();
//		map2.put("ASSET_CODE", "dxyceshi222");
//		map2.put("ASSET_TYPE", "03");
//		map2.put("VDATE", "20200916");
//		map2.put("AMT", "200");
//		infoList.add(map2);
//		Map<String, String>  map3=new  HashMap<String, String>();
//		map3.put("ASSET_CODE", "dxyceshi333");
//		map3.put("ASSET_TYPE", "04");
//		map3.put("VDATE", "2020-09-17");
//		map3.put("AMT", "300");
//		infoList.add(map3);
//
//		//数据库表字段list
//		List<String> listCloumn  =new  ArrayList<>();
//		listCloumn.add("ASSET_CODE1");
//		listCloumn.add("ASSET_TYPE");
//		listCloumn.add("VDATE");
//		listCloumn.add("AMT");
//	    List<ChbAssetInfo>	 list=(List<ChbAssetInfo>) SvrMaptoEntityUtil.convertMapToEntity(infoList, listCloumn,ChbAssetInfo.getClass());
//	    for(ChbAssetInfo  dto: list){
//	    	System.out.println("ASSET_CODE:"+dto.getAssetCode());
//	    	System.out.println("ASSET_TYPE:"+dto.getAssetType());
//	    	System.out.println("VDATE:"+dto.getVdate());
//	    	System.out.println("AMT:"+dto.getAmt());
//	    }
//
//	    //再将 List<ChbAssetInfo>	 list转为List<Map<String, String>>
//	    System.out.println("再将list<T>转为list<map<string,string>>-------");
//
//	    List<Map<String, String>>  listmap= SvrMaptoEntityUtil.convertEntityToMap(list, listCloumn);
//	    if(listmap.size()>0){
//		    System.out.println(listmap.get(0).get("ASSET_CODE"));
//		    System.out.println(listmap.get(0).get("ASSET_TYPE"));
//		    System.out.println(listmap.get(0).get("VDATE"));
//		    System.out.println(listmap.get(0).get("AMT"));
//
//		    System.out.println(listmap.get(1).get("ASSET_CODE"));
//		    System.out.println(listmap.get(1).get("ASSET_TYPE"));
//		    System.out.println(listmap.get(1).get("VDATE"));
//		    System.out.println(listmap.get(1).get("AMT"));
//
//		    System.out.println(listmap.get(2).get("ASSET_CODE"));
//		    System.out.println(listmap.get(2).get("ASSET_TYPE"));
//		    System.out.println(listmap.get(2).get("VDATE"));
//		    System.out.println(listmap.get(2).get("AMT"));
//	    }
//
//	}
}
