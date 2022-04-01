package com.joyintech.tams.invest.common.util;

import com.alibaba.fastjson.JSON;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.common.NumberUtil;
import com.greenpineyu.fel.context.MapContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.joyintech.framework.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * java表达式工具(强类型)
 * 
 * @author Zim
 * @time 2016年6月21日 下午1:13:36
 */
public class ExpressionUtil {
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExpressionUtil.class);

	protected final static FelEngine FEL = new FelEngineImpl();
	
	protected ExpressionUtil(){}
	
	/**
	 * 表达式计算
	 * @param expression
	 * @param env 无参数赋值null
	 * @return
	 */
	public static Object eval(String expression,Map<String, Object> env) {
		try {
			if (null==env) return FEL.eval(expression);
			return FEL.eval(expression, new MapContext(env));
		} catch (Exception e) {
			logger.error("expression:" + expression+";" + "env:" + JSON.toJSONString(env));
			throw e;
		}
	}
	
	/**
	 * 调用静态函数或实例化对象的函数
	 * @param expression
	 * @param env 无参数赋值null
	 * @return
	 */
	public static Object evalMethod(String expression,Map<String, Object> env) {
		try {
			if (null==env) return FelEngine.instance.eval(expression);
			return FelEngine.instance.eval(expression, new MapContext(env));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static int intValue(String expression,Map<String, Object> env){
		return NumberUtil.toInteger(eval(expression,env));
	}
	
	public static long longValue(String expression,Map<String, Object> env){
		return NumberUtil.toLong(eval(expression,env));
	}
	
	public static double doubleValue(String expression,Map<String, Object> env){
		return NumberUtil.toDouble(eval(expression,env));
	}
	
	public static boolean booleanValue(String expression,Map<String, Object> env){
		return (Boolean)eval(expression,env);
	}
	
	public static String stringValue(String expression,Map<String, Object> env){
		return (String)eval(expression,env);
	}
	
	public static Object[] objectValue(String expression,Map<String, Object> env){
		return (Object[]) eval(expression,env);
	}
	
	public static void registerFunction(CommonFunction function) {
		FEL.addFun(function);
	}
	
	static{
		
		//invoke
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "invoke";
			}
			public Object call(Object[] v) {
				if(v.length!=3){
					throw new RuntimeException("invoke参数格式错误，格式：invoke('SpringBeanId','methodName','param')");
				}
				String beanid = v[0].toString();
				Object object = SpringContextUtil.getBean(beanid);
				if(object== null){
					throw new RuntimeException("invoke参数错误，未找到SpringBeanId");
				}
				String methodName = v[1].toString();
				if(methodName== null){
					throw new RuntimeException("invoke参数错误，未找到methodName");
				}
				Method [] methods = object.getClass().getMethods();
				Method invokeMethod = null;
				for (Method method : methods) {
	                if (method.getName().equals(methodName)) {
	                	invokeMethod = method;
	                    break;
	                }
	            }
				if(invokeMethod== null){
					throw new RuntimeException("invoke参数错误，未找到methodName");
				}
				Object param =v[2];
				try {
					invokeMethod.invoke(object,param );
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					throw new RuntimeException("invoke执行错误："+e.getMessage());
				}
				return true;
			}
		});
		
		//array(xxx,xxx,xxx,....)
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "array";
			}
			public Object call(Object[] v) {
				if (v==null || v.length<=0) {
					return new Object[] {};
				}
				return v;
			}
		});
		//tojson(object)
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "tojson";
			}
			
			public Object call(Object[] v) {
				if (v==null || v.length<=0) {
					throw new RuntimeException("tojson函数参数不能为空!");
				}
				return JSON.toJSONString(v[0]);
			}
		});
		//json2map(str)
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "json2map";
			}
			public Object call(Object[] v) {
				if (v==null || v.length<=0) {
					throw new RuntimeException("json2map函数参数不能为空!");
				}
				return JSON.parseObject(v[0].toString(), Map.class);
			}
		});
		//json(str,'com.xxx.ZZZ')
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "json";
			}
			public Object call(Object[] v) {
				if (v==null || v.length!=2) {
					throw new RuntimeException("json函数参数错误!");
				}
				try {
					return JSON.parseObject(v[0].toString(), Class.forName(v[1].toString()));
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(v[1]+"参数的类没有找到!",e);
				}
			}
		});
		//int(xx)
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "int";
			}
			public Object call(Object[] v) {
				return NumberUtil.toInteger(v[0]);
			}
		});
		//long(xx)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "long";
	    	}
	    	public Object call(Object[] v) {
	    		return NumberUtil.toLong(v[0]);
	    	}
	    });
		//double(xx)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "double";
	    	}
	    	public Object call(Object[] v) {
	    		return NumberUtil.toDouble(v[0]);
	    	}
	    });
	    //string(xx)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "string";
	    	}
	    	public Object call(Object[] v) {
	    		return String.valueOf(v[0]);
	    	}
	    });
	    //boolean(xx)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "boolean";
	    	}
	    	public Object call(Object[] v) {
				return Boolean.valueOf(v[0].toString());
	    	}
	    });
	    //trim(xx)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "trim";
	    	}
	    	public Object call(Object[] v) {
				return v[0].toString().trim();
	    	}
	    });
	    //substr(str,startIndex,endIndex)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "substr";
	    	}
	    	public Object call(Object[] v) {
				return v[0].toString().substring((Integer)v[1], (Integer)v[2]);
	    	}
	    });
	    //indexOf(str,patt)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "indexOf";
	    	}
	    	public Object call(Object[] v) {
				return v[0].toString().indexOf(v[1].toString());
	    	}
	    });
	    //lastIndexOf(str,patt)
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "lastIndexOf";
	    	}
	    	public Object call(Object[] v) {
				return v[0].toString().lastIndexOf(v[1].toString());
	    	}
	    });
	    //date
	    FEL.addFun(new CommonFunction() {
	    	public String getName() {
	    		return "date";
	    	}
	    	public Object call(Object[] v) {
				return new Date();
	    	}
	    });
	    //date2str(date,format) 或 date2str(format)
//	    FEL.addFun(new CommonFunction() {
//	    	public String getName() {
//	    		return "date2str";
//	    	}
//	    	public Object call(Object[] v) {
//	    		Date d = null;
//	    		String format = null;
//	    		if (v.length==1) {
//					d = new Date();
//					format = (String) v[0];
//				} else {
//					d = (Date)v[0];
//					format = (String) v[1];
//				}
//	    		return DateEnum.parseDate(d, format);
//	    	}
//	    });
	    //ex: round(A*0.123567) 或 round(A*0.123567,3)
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "round";
			}
			public Object call(Object[] v) {
				Double value = NumberUtil.toDouble(v[0]);
				int scale = 2;
				if (v.length==2) {
					scale = (Integer)v[1];
				}
				return NumberUtil.toBigDecimal(value).setScale(scale,RoundingMode.HALF_UP).doubleValue();
			}
		});
		//ex: empty(str)
		FEL.addFun(new CommonFunction() {
			public String getName() {
				return "empty";
			}
			public Object call(Object[] v) {
				if (v.length!=1) {
					throw new RuntimeException("empty函数参数错误!");
				}
				return StringUtils.isEmpty(v[0]);
			}
		});
	}
	
}
