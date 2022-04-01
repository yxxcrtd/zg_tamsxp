package com.joyintech.tams.valuation.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 表达式工具 依赖的Jar --> aviator-3.1.1.jar
 * 
 * @author Zim
 * @time 2018年2月27日 下午2:39:42
 */
public class ExpressionUtil {

	public static final String DIVIDE_BY_ZERO = "DIVIDE_BY_ZERO";

	private static final Log LOG = LogFactory.getLog(ExpressionUtil.class);

	private static final Set<Character> set = new TreeSet<Character>();

	private static final Set<String> sets = new TreeSet<String>();

	static {

		set.add('&');
		set.add('|');
		set.add('!');
		set.add('+');
		set.add('-');
		set.add('*');
		set.add('/');
		set.add('%');
		set.add('>');
		set.add('=');
		set.add('<');
		set.add(' ');
		set.add('(');
		set.add(')');

		// &&和|| 转换为单字符@和#替代 方便判断
		sets.add("@");
		sets.add("#");
		sets.add("!");
		sets.add("+");
		sets.add("-");
		sets.add("*");
		sets.add("/");
		sets.add("%");
		sets.add(">");
		//sets.add("=");
		sets.add("<");
		sets.add(" ");
		sets.add("(");
		sets.add(")");
	}

	/**
	 * 将表达式转换为数组
	 * 
	 * @param expression
	 * @return
	 */
	public static List<String> split(String expression) {
		char[] chars = expression.toCharArray();
		List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if (set.contains(c)) {
				if (sb.length() > 0) {
					list.add(sb.toString());
				}
				list.add(String.valueOf(c));
				sb = new StringBuilder();
			} else {
				sb.append(c);
			}
		}
		list.add(sb.toString());
		return list;
	}

	/**
	 * 将表达式转换为数组
	 * 
	 * @param expression
	 * @return
	 */
	public static List<String> splits(String expression) {
		expression = expression.replace(" && ", "@").replace(" || ", "#");
		char[] chars = expression.toCharArray();
		List<String> list = new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			if (sets.contains(String.valueOf(c))) {
				if (sb.length() > 0) {
					list.add(sb.toString());
				}
				list.add(String.valueOf(c));
				sb = new StringBuilder();
			} else {
				sb.append(c);
			}
		}
		list.add(sb.toString());
		for (String s : list) {
			s = s.replace("@", " && ").replace("#", " || ");
			result.add(s);
		}
		return result;
	}





	/**
	 * 注意:以类路径和方法名为key,即同个类中方法名不能重复.
	 * 
	 * @author Zim
	 * @time 2018年2月23日 上午11:49:13
	 */
	public static class CallMethod {

		private static final Map<Integer, CallMethod> METHODS = new ConcurrentHashMap<Integer, CallMethod>();

		private Class<?> clazz;
		private Object object;
		private Method method;

		private CallMethod(Class<?> clazz, Method method) {
			this.clazz = clazz;
			this.method = method;
		}






		/**
		 * 调用方法
		 * 
		 * @param args
		 * @return
		 * @throws Throwable
		 */
		public Object call(Object... args) throws Throwable {
			try {
				Class<?>[] classes = this.method.getParameterTypes();
				if (args != null) {
					if (classes.length != args.length) {
						throw new RuntimeException("传入参数和目标参数不一致");
					}
				}else{
					if (classes.length != 0) {
						throw new RuntimeException("传入参数和目标参数不一致");
					}
					
				}
				for (int i = 0; i < classes.length; i++) {
					Class<? extends Object> temp = args[i].getClass();
					if (classes[i].isAssignableFrom(temp)) {
						continue;
					}
					if (classes[i].equals(java.sql.Date.class)
							&& temp.equals(java.util.Date.class)) {
						java.util.Date d = (java.util.Date) args[i];
						args[i] = new java.sql.Date(d.getTime());
						continue;
					}
					throw new RuntimeException("传入参数和目标参数不一致");
				}
				return this.method.invoke(this.object, args);
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
	}

	public static void main(String[] args) {
		String a = getKeywordByContent("正回购 && !回购期限5年以上");
		System.out.println(a);

	}

	private static String getKeywordByContent(String content) {
		StringBuffer sbAllKwyword = new StringBuffer();
		content = content.replaceAll(" ", "");// 去掉所有空格
		if (content.length() > 10) {
			String regex = "[\\u4E00-\u9FA5\\s\\d\\w]{10}";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(content);
			int i = 0;
			while (m.find() && i < 10) {
				sbAllKwyword.append(m.group());
				sbAllKwyword.append("^");
				i++;
			}

			if (sbAllKwyword.length() > 1) {
				sbAllKwyword.deleteCharAt(sbAllKwyword.length() - 1);
			}
		} else {
			sbAllKwyword.append(content);
		}
		return sbAllKwyword.toString();
	}

}
