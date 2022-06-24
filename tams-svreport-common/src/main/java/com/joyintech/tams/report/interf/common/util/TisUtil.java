package com.joyintech.tams.report.interf.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.file.SafelyFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.dto.InterfLog;

public class TisUtil {

	public static BufferedReader bufread;
	// 指定文件路径和名称
	private static String path = "suncity.txt";
	private static File filename = new SafelyFile(path);
	private static String readStr = "";

	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(TisUtil.class);
	/**
	 * 判断类型是否 为空
	 * @param str 判断Object
	 * @return
	 */
	public static boolean isEmpty(Object str){

		if(str == null || "".equals(str.toString().trim())){
			return true;
		}

		return false;
	}

	/** */
	/**
	 * 创建文本文件.
	 *
	 * @throws IOException
	 *
	 */
	public static void creatTxtFile(String path) throws IOException {
		filename = new SafelyFile(path);
		//如果本地已存在同名文件，删除本地重新生成
		if (filename.exists()) {
			filename.delete();
		}
		filename.createNewFile();
	}
	/**
	 * 修改文件的名字
	 * @param path
	 * @throws IOException
	 */
	public static void reTxtFilename(String path) throws IOException {
		File file = new SafelyFile(path);
		if (filename.exists()) {
			filename.renameTo(file);
		}
	}
	/** */
	/**
	 * 读取文本文件.
	 *
	 */
	public static String readTxtFile() {
		readStr="";
		String read;
		FileReader fileread = null;
		try {
			fileread = new FileReader(filename);
			bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read + "\r\n";
				}
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
		}
		finally {
			try {
				if(fileread !=null)
					fileread.close();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}

		return readStr;
	}
	/** */
	/**
	 * 读取文本文件.
	 *
	 */
	public static String readTxtFile(File file) throws UnsupportedEncodingException {
		InterfLog log = new InterfLog();
		readStr="";
		String read;
		FileReader fileread;
		FileInputStream fis=null;
		InputStreamReader read2=null;
		try {
			fis=new FileInputStream(file);
			read2 = new InputStreamReader(fis,"UTF-8");
			bufread=new BufferedReader(read2);
			//bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read + "\r\n";
				}
				log.setTaskStatus(file+"读取文件成功！");
			} catch (IOException e) {
				log.setTaskStatus(file+"读取文件失败！");
				logger.error(e.getMessage(),e);
			}
		} catch (FileNotFoundException e) {
			log.setTaskStatus(file+"读取文件失败！");
			logger.error(e.getMessage(),e);
		}
		finally{
			if(fis!=null){
				try {
					fis.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(read2!=null){
				try {
					read2.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(bufread!=null){
				try {
					bufread.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
		}
		log.setTaskStatus(file+"文件内容是:" + "\r\n" + readStr);
		return readStr;
	}
	/** */
	/**
	 * 写文件.
	 *
	 */
	public static void writeTxtFile(String newStr) throws IOException {
		InterfLog log = new InterfLog();
		// 先读取原有文件内容，然后进行写入操
		String filein = newStr;//+"\r\n"+ readStr;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(filename);
			o.write(filein.getBytes("GBK"));
			o.close();
			log.setTaskStatus("生成文件成功！");
			log.setTaskStatus("文件内容是:" + "\r\n" + filein);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			log.setTaskStatus("生成文件失败！");
		} finally {
			if (o != null) {
				o.close();
			}
		}
	}

	/** */
	/**
	 * 将文件中指定内容的第一行替换为其它内容.
	 *
	 * @param oldStr
	 *            查找内容
	 * @param replaceStr
	 *            替换内容
	 */
	public static void replaceTxtByStr(String oldStr, String replaceStr) {
		String temp = "";
		FileInputStream fis=null;
		InputStreamReader isr=null;
		BufferedReader br=null;
		FileOutputStream fos=null;
		PrintWriter pw =null;
		try {
			File file = new SafelyFile(path);
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// 保存该行前面的内容
			for (int j = 1; (temp = br.readLine()) != null
					&& !temp.equals(oldStr); j++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}

			// 将内容插入
			buf = buf.append(replaceStr);

			// 保存该行后面的内容
			while ((temp = br.readLine()) != null) {
				buf = buf.append(System.getProperty("line.separator"));
				buf = buf.append(temp);
			}
			fis.close();
			isr.close();
			br.close();
			fos = new FileOutputStream(file);
			pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			fos.close();
			pw.close();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		finally{
			if(fis!=null){
				try {
					fis.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(isr!=null){
				try {
					isr.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(br!=null){
				try {
					br.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(fos!=null){
				try {
					fos.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(pw!=null){
				pw.close();
			}
		}
	}
	public static String  getBeanToText(Object o) {
		StringBuffer sb=new StringBuffer();
		try {
			if(o != null ) {
				Field[] fields = o.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++){
					boolean accessFlag = fields[i].isAccessible();
					fields[i].setAccessible(true);
					Object objText = null;
					try {
						objText = fields[i].get(o);
					}catch (Exception e) {
//							logger.error("class反射读取失败:", e);
						logger.error("class反射读取失败:"+e.getMessage(),e);
					}
					if (objText == null){
						sb.append("|");
					}else{
						sb.append(objText.toString()+"|");
					}
					fields[i].setAccessible(accessFlag);
				}
			}
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
//			logger.error("bean转为dom失败:", e);
		}
		return sb.toString()+"\r\n";
	}
	/**
	 * txt生成调用此方法即可
	 * @param list
	 * @param fpath
	 * @throws IOException
	 */
	public static void writeDataToTxtFile(List list,String fpath) throws IOException {
		creatTxtFile(fpath);
		StringBuffer sb = new StringBuffer();
		for(int i =0;i<list.size();i++){
			sb = sb.append(TisUtil.getBeanToText(list.get(i)));
		}
		TisUtil.writeTxtFile(sb.toString().trim());
	}

	/**
	 * txt生成调用此方法即可
	 * @param newStr
	 * @param fpath
	 * @throws IOException
	 */
	public static void writeDataToTxtFile(String newStr,String fpath) {
		try{
			creatTxtFile(fpath);
			TisUtil.writeTxtFile(newStr.trim());
		}catch (Exception e){
			logger.error(String.valueOf(e));
		}

	}

//	/** */
//	/**
//	 * main方法测试
//	 *
//	 * @param s
//	 * @throws IOException
//	 */
//	public static void main(String[] s) throws IOException {
//
//	}
//	public static void main(String[] args) throws MalformedURLException,Exception{
//		Service s=new ObjectServiceFactory().create(IHelloWorldService.class);
//	}

//    public static File createFile(Map<String, Object> data, String exceltemplate, String flieName) {
//        File depositfile = null;
//        InputStream fis = null;
//        FileOutputStream fos = null;
//        try {
//
//            // 关联模板
//            XLSTransformer transformer = new XLSTransformer();
//
//            String template = null;
//            // 模板路径pbc/
//            template = FileNameUtil.getTplFile(exceltemplate + ".xls");
//            // //windows环境地址
//            // if(File.separator.equals("\\")){
//            // template = getXmlPath(exceltemplate+".xls");
//            // }
//            // //linux环境地址
//            // if(File.separator.equals("/")){
//            // template = getXmlPathLinux(exceltemplate+".xls");
//            // }
//            // 传放地址
//            String depositfilePath = SvrDataUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
//            // 文件名称
//            depositfile = new File(depositfilePath + File.separator + flieName);
//            if (!depositfile.getParentFile().exists()) {
//                depositfile.getParentFile().mkdirs();
//            }
//
//            // 缓存区，一次性读取1024字节
//            byte[] buffer = new byte[1024];
//            String temp = depositfilePath + "ptl.xls";
//            // 原文件
//            File srcFile = new File(template);
//            // 目标文件
//            File destFile = new File(temp);
//            if (srcFile.exists()) {
//                fis = new FileInputStream(srcFile);
//            } else {
//                fis = TisUtil.class.getResourceAsStream("/tpl/" + exceltemplate + ".xls");
//            }
//            fos = new FileOutputStream(destFile);
//            while (true) {
//                // 实际读取的长度是 actuallyReaded,有可能小于1024
//                int actuallyReaded = fis.read(buffer);
//                // -1表示没有可读的内容了
//                if (-1 == actuallyReaded)
//                    break;
//                fos.write(buffer, 0, actuallyReaded);
//                fos.flush();
//            }
// //           JxlsHelper.getInstance().processTemplate(new FileInputStream(srcFile), new FileOutputStream(depositfilePath + File.separator + flieName), new Context(data));
//            transformer.transformXLS(temp, data, depositfilePath + File.separator + flieName);
//            if (destFile.exists()) {
//                destFile.delete();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        } finally {
//        	try {
//        		fis.close();
//				fos.close();
//			} catch (IOException e) {
//				logger.error(e.getMessage(),e);
//			}
//		}
//        return depositfile;
//    }


	@SuppressWarnings("unchecked")
	public static File createFile(Map<String, Object> paraMap, String exceltemplate, String flieName) {
		// 模板路径pbc/
		String template = FileNameUtil.getTplFile(exceltemplate + ".xls");
		// 模板文件后缀名
		String fileType = GlobalConstant.POINT + FilenameUtils.getExtension(template);
		// 存放地址
		String depositfilePath = SvrDataUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 文件名称
		String outputFilepath = depositfilePath + File.separator + flieName;

		// 工作表对象
		HSSFWorkbook wb = null;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			// 原文件
			File srcFile = new SafelyFile(template);
			if (srcFile.exists()) {
				inputStream = new FileInputStream(srcFile);
			} else {
				inputStream = TisUtil.class.getResourceAsStream("/tpl/" + exceltemplate + ".xls");
			}
			// 工作表对象初始化
			wb = new HSSFWorkbook(inputStream);

			for (Sheet sheet : wb) {
				List<HSSFRow> listRows = new ArrayList<HSSFRow>();

				int rownum = sheet.getLastRowNum();
				for (int i = 0; i <= rownum; i++ ) {
					HSSFRow row = (HSSFRow)sheet.getRow(i);
					/*
					 * if (row == null||row.getCell(0)==null) { continue; }
					 */
					// 解决第二个或之后的单元格有参数却不替换的问题 modify by liupeilin 20200301 begin
					if (row == null) {
						continue;
					}
					//解决第二个或之后的单元格有参数却不替换的问题 modify by liupeilin 20200301 end
					int cellnum = row.getLastCellNum();

					for (int j = 0; j < cellnum; j++ ) {
						//
						if(row.getCell(j)==null){
							continue;
						}
						CellType cellType = row.getCell(j).getCellType();
						if (cellType != CellType.STRING) {
							continue;
						}
						String cellvalue = row.getCell(j).getStringCellValue();
						if (StringUtil.isNotEmpty(cellvalue)) {
							if (cellvalue.contains("${") && cellvalue.contains("}")) {
								replaceData(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("!{") && cellvalue.contains("}")) {
								replaceData2(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("@{") && cellvalue.contains("}")) {
								replaceData3(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("#{") && cellvalue.contains("}")) {
								listRows.add(0, row);
								break;
							}
						}
					}
				}
				//for(int index=0;index<listRows.size();index++){
				//HSSFRow row=listRows.get(index);
				for (HSSFRow row : listRows) {
					List list = null;
					for(int index=0;index<row.getLastCellNum();index++){
						//for (Cell cell : row) {
						Cell cell=row.getCell(index);
						if(cell==null){
							continue;
						}
						CellType cellType = cell.getCellType();
						if (cellType != CellType.STRING) {
							continue;
						}
						String cellvalue = cell.getStringCellValue();
						if (StringUtil.isNotEmpty(cellvalue)) {
							if (cellvalue.contains("#{") && cellvalue.contains("}")) {
								String key = cellvalue.substring(cellvalue.indexOf("#{") + 2,
										cellvalue.indexOf("."));

								if(paraMap.get(key) instanceof List){
									list = (List)paraMap.get(key);
									break;
								}else if(paraMap.get(key) instanceof Map){
									String valKey = cellvalue.substring(cellvalue.indexOf(".")+1,
											cellvalue.indexOf("}"));
									Map<String,Object> resultMap = new HashMap<>();
									resultMap = (Map)paraMap.get(key);
									String value=null;
									if(resultMap.get(valKey)!=null){
										value=resultMap.get(valKey).toString();
										if(!StringUtil.isNotEmpty(value)||value==""){
											value = "";
										}

									}else{
										value = "";
									}
									HSSFCell baseCell = (HSSFCell) row.getCell(index);
									HSSFCell newcell = (HSSFCell) row.createCell(index);
									copyCell((HSSFWorkbook) wb, baseCell, newcell, true);
									if(exceltemplate.contains("Monthreport")){
										try {
											newcell.setCellValue(Double.parseDouble(value));
											if(!exceltemplate.contains("G06")){
												newcell.setCellType(baseCell.getCellType());
											}
										}catch(Exception e){
											newcell.setCellValue(value);
										}
									}else{
										newcell.setCellValue(value);
									}
								}else if(paraMap.get(key)==null){
									insertRow(row, 1);
									Map<String, Object> value2=new HashMap<String, Object>();
									insertRow(row, 1);

									Row newrow = sheet.createRow(row.getRowNum() + 1);


									for (int i = 0; i < row.getLastCellNum(); i++) {
										HSSFCell cell2 = (HSSFCell) row.getCell(i);
										HSSFCell newcell2 = (HSSFCell) newrow.createCell(i);
										copyNullCell((HSSFWorkbook) wb, cell2, newcell2, true);

										String cellvalue2 = newcell2.getStringCellValue();
										if (StringUtil.isNotEmpty(cellvalue2)) {
											if (cellvalue2.contains("#{") && cellvalue2.contains("}")) {
												replaceListData(newcell2, value2);
											}
										}
									}
									removeRow(row);
									break;
								}

							}
						}
					}

					// 增加新行
					if(list != null) {
						insertRow(row, list.size());

						for (Object obj : list) {
							Map<String, Object> value = transBean2Map(obj);
							// // 获取需要设值的行
							Row newrow = sheet.getRow(row.getRowNum() + list.indexOf(obj) + 1);// sheet.createRow(row.getRowNum()
							// +
							// list.indexOf(obj)
							// + 1);
							if (newrow == null) {
								newrow = sheet.createRow(row.getRowNum() + list.indexOf(obj) + 1);
							}
							for (int i = 0; i < row.getLastCellNum(); i++) {
								HSSFCell cell = (HSSFCell) row.getCell(i);
								HSSFCell newcell = (HSSFCell) newrow.createCell(i);
								copyCell((HSSFWorkbook) wb, cell, newcell, true);

								CellType cellType = newcell.getCellType();
								if (cellType != CellType.STRING) {
									continue;
								}
								String cellvalue = newcell.getStringCellValue();
								if (StringUtil.isNotEmpty(cellvalue)) {
									if (cellvalue.contains("#{") && cellvalue.contains("}")) {
										replaceListData(newcell, value);
									}
								}
							}
						}

						removeRow(row);
					}
				}
				sheet.setForceFormulaRecalculation(true);
			}

			File file = new SafelyFile(outputFilepath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			outputStream = new FileOutputStream(file);
			//add by zhoubin 20220510 start
			//使单元格公式生效。
			FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
			evaluator.evaluateAll();
			//add by zhoubin 20220510 end
			wb.write(outputStream);

			return file;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		finally {
			try {
				if(wb != null) {
					wb.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			}
			catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}




	public static File createFileDetail(Map<String, Object> paraMap, String exceltemplate, String flieName) {
		// 模板路径pbc/
		String template = FileNameUtil.getTplFile(exceltemplate + ".xls");
		// 模板文件后缀名
		String fileType = GlobalConstant.POINT + FilenameUtils.getExtension(template);
		// 存放地址
		String depositfilePath = SvrDataUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 文件名称
		String outputFilepath = depositfilePath + File.separator + flieName;

		// 工作表对象
		HSSFWorkbook wb = null;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			// 原文件
			File srcFile = new SafelyFile(template);
			if (srcFile.exists()) {
				inputStream = new FileInputStream(srcFile);
			} else {
				inputStream = TisUtil.class.getResourceAsStream("/tpl/" + exceltemplate + ".xls");
			}
			// 工作表对象初始化
			wb = new HSSFWorkbook(inputStream);

			for (Sheet sheet : wb) {
				List<HSSFRow> listRows = new ArrayList<HSSFRow>();

				int rownum = sheet.getLastRowNum();
				for (int i = 0; i <= rownum; i++ ) {
					HSSFRow row = (HSSFRow)sheet.getRow(i);
					/*
					 * if (row == null||row.getCell(0)==null) { continue; }
					 */
					// 解决第二个或之后的单元格有参数却不替换的问题 modify by liupeilin 20200301 begin
					if (row == null) {
						continue;
					}
					//解决第二个或之后的单元格有参数却不替换的问题 modify by liupeilin 20200301 end
					int cellnum = row.getLastCellNum();

					for (int j = 0; j < cellnum; j++ ) {
						//
						if(row.getCell(j)==null){
							continue;
						}
						CellType cellType = row.getCell(j).getCellType();
						if (cellType != CellType.STRING) {
							continue;
						}
						String cellvalue = row.getCell(j).getStringCellValue();
						if (StringUtil.isNotEmpty(cellvalue)) {
							if (cellvalue.contains("${") && cellvalue.contains("}")) {
								replaceData(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("!{") && cellvalue.contains("}")) {
								replaceData2(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("@{") && cellvalue.contains("}")) {
								replaceData3(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("#{") && cellvalue.contains("}")) {
								listRows.add(0, row);
								break;
							}
						}
					}
				}
				//for(int index=0;index<listRows.size();index++){
				//HSSFRow row=listRows.get(index);
				for (HSSFRow row : listRows) {
					List list = null;
					for(int index=0;index<row.getLastCellNum();index++){
						//for (Cell cell : row) {
						Cell cell=row.getCell(index);
						if(cell==null){
							continue;
						}
						CellType cellType = cell.getCellType();
						if (cellType != CellType.STRING) {
							continue;
						}
						String cellvalue = cell.getStringCellValue();
						if (StringUtil.isNotEmpty(cellvalue)) {
							if (cellvalue.contains("#{") && cellvalue.contains("}")) {
								String key = cellvalue.substring(cellvalue.indexOf("#{") + 2,
										cellvalue.indexOf("."));

								if(paraMap.get(key) instanceof List){
									list = (List)paraMap.get(key);
									break;
								}else if(paraMap.get(key) instanceof Map){
									String valKey = cellvalue.substring(cellvalue.indexOf(".")+1,
											cellvalue.indexOf("}"));
									Map<String,Object> resultMap = new HashMap<>();
									resultMap = (Map)paraMap.get(key);
									String value=null;
									if(resultMap.get(valKey)!=null){
										value=resultMap.get(valKey).toString();
										if(!StringUtil.isNotEmpty(value)||value==""){
											value = "";
										}

									}else{
										value = "";
									}
									HSSFCell baseCell = (HSSFCell) row.getCell(index);
									HSSFCell newcell = (HSSFCell) row.createCell(index);
									copyCell((HSSFWorkbook) wb, baseCell, newcell, true);
									if(exceltemplate.contains("Monthreport")){
										try {
											newcell.setCellValue(Double.parseDouble(value));
											if(!exceltemplate.contains("G06")){
												newcell.setCellType(baseCell.getCellType());
											}
										}catch(Exception e){
											newcell.setCellValue(value);
										}
									}else{
										newcell.setCellValue(value);
									}
								}else if(paraMap.get(key)==null){
									insertRow(row, 1);
									Map<String, Object> value2=new HashMap<String, Object>();
									insertRow(row, 1);

									Row newrow = sheet.createRow(row.getRowNum() + 1);


									for (int i = 0; i < row.getLastCellNum(); i++) {
										HSSFCell cell2 = (HSSFCell) row.getCell(i);
										HSSFCell newcell2 = (HSSFCell) newrow.createCell(i);
										copyNullCell((HSSFWorkbook) wb, cell2, newcell2, true);

										String cellvalue2 = newcell2.getStringCellValue();
										if (StringUtil.isNotEmpty(cellvalue2)) {
											if (cellvalue2.contains("#{") && cellvalue2.contains("}")) {
												replaceListData(newcell2, value2);
											}
										}
									}
									removeRow(row);
									break;
								}

							}
						}
					}

					// 增加新行
					if(list != null) {
						insertRow(row, list.size());

						for (Object obj : list) {
							Map<String, Object> value = transBean2Map(obj);
							// // 获取需要设值的行
							Row newrow = sheet.getRow(row.getRowNum() + list.indexOf(obj) + 1);// sheet.createRow(row.getRowNum()
							// +
							// list.indexOf(obj)
							// + 1);
							if (newrow == null) {
								newrow = sheet.createRow(row.getRowNum() + list.indexOf(obj) + 1);
							}
							for (int i = 0; i < row.getLastCellNum(); i++) {
								HSSFCell cell = (HSSFCell) row.getCell(i);
								HSSFCell newcell = (HSSFCell) newrow.createCell(i);
								copyCell((HSSFWorkbook) wb, cell, newcell, true);

								CellType cellType = newcell.getCellType();
								if (cellType != CellType.STRING) {
									continue;
								}
								String cellvalue = newcell.getStringCellValue();
								if (StringUtil.isNotEmpty(cellvalue)) {
									if (cellvalue.contains("#{") && cellvalue.contains("}")) {
										replaceListData(newcell, value);
									}
								}
							}
						}

						removeRow(row);
					}
				}
				sheet.setForceFormulaRecalculation(true);
			}

			File file = new SafelyFile(outputFilepath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			outputStream = new FileOutputStream(file);
			wb.write(outputStream);

			return file;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		finally {
			try {
				if(wb != null) {
					wb.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			}
			catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static String getXmlPath(String filename) {
		try {
			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").toURI().getPath();
			path = path.replace('/', '\\'); // 将/换成\
			path = path.replace("file:", ""); // 去掉file:
			path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
			path += "tpl\\" + filename;
			path = path.replace("report-batch", "report-parent\\report");
			return path;
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	public static String getXmlPathLinux(String filename) {
		try {
			String path = Thread.currentThread().getContextClassLoader()
					.getResource("").toURI().getPath();
			path += "tpl/" + filename;
			path = path.replace("file:", ""); // 去掉file:
			path = path.replace("report-batch", "report-parent/report");
			return path;
		} catch (URISyntaxException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	/**
	 * 删除指定的Sheet
	 * @param targetFile  目标文件
	 * @param sheetNameList   Sheet名称
	 */
	public static void deleteSheet(File targetFile,List<String> sheetNameList) {
		try (FileInputStream fis = new FileInputStream(targetFile);
			 HSSFWorkbook wb = new HSSFWorkbook(fis);){
			if(sheetNameList != null && sheetNameList.size() > 0){
				for(String sheetName : sheetNameList){
					//删除Sheet
					wb.removeSheetAt(wb.getSheetIndex(sheetName));
				}
				fileWrite(targetFile, wb);
				fis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * 写隐藏/删除后的Excel文件
	 * @param targetFile  目标文件
	 * @param wb          Excel对象
	 * @throws Exception
	 */
	public static void fileWrite(File targetFile,HSSFWorkbook wb) throws IOException {
		FileOutputStream fileOut = null;
		try {
			new FileOutputStream(targetFile);
			wb.write(fileOut);
		} finally {
			if (fileOut != null){
				fileOut.flush();
				fileOut.close();
			}
		}
	}


	private static void replaceData(Cell cell, Map<String, Object> datas) {
		CellType cellType = cell.getCellType();
		if (cellType != CellType.STRING) {
			return;
		}
		String result = cell.getStringCellValue();

		if (!result.contains("${") || !result.contains("}")) {
			return;
		}
		String key = result.substring(result.indexOf("${") + 2, result.indexOf("}"));
		String replaceString = replaceString(key, datas);
		if (StringUtil.isNotEmpty(replaceString)) {
			result = result.replace("${" + key + "}", replaceString(key, datas));
		}
		else {
			//解决两个参数其中一个为空整个字符串返回空值问题，例${PRODSERIES}${FINPRODNAME}  modify by liupeilin 20200301 begin
			//result = "";
			result = result.replace("${" + key + "}", replaceString(key, datas)==null?"":replaceString(key, datas));
			//解决两个参数其中一个为空整个字符串返回空值问题，例${PRODSERIES}${FINPRODNAME}  modify by liupeilin 20200301 end
		}

		//cell.setCellType(CellType.NUMERIC);
		cell.setCellValue(result);
//
		if (result.contains("${") && result.contains("}")) {
			replaceData(cell, datas);
		}

	}

	private static void replaceData3(Cell cell, Map<String, Object> datas) {
		CellType cellType = cell.getCellType();
		if (cellType != CellType.STRING) {
			return;
		}
		String result = cell.getStringCellValue();

		if (!result.contains("@{") || !result.contains("}")) {
			return;
		}
		String key = result.substring(result.indexOf("@{") + 2, result.indexOf("}"));
		String replaceString = replaceString3(key, datas);
		if (StringUtil.isNotEmpty(replaceString)) {
			result = result.replace("@{" + key + "}", replaceString);
		}
		else {
			//解决两个参数其中一个为空整个字符串返回空值问题，例${PRODSERIES}${FINPRODNAME}  modify by liupeilin 20200301 begin
			//result = "";
			result = result.replace("@{" + key + "}", replaceString3(key, datas)==null?"":replaceString3(key, datas));
			//解决两个参数其中一个为空整个字符串返回空值问题，例${PRODSERIES}${FINPRODNAME}  modify by liupeilin 20200301 end
		}

		//cell.setCellType(CellType.NUMERIC);
		cell.setCellValue(result);

//
		if (result.contains("@{") && result.contains("}")) {
			replaceData(cell, datas);
		}

	}

	@SuppressWarnings("deprecation")
	private static void replaceData2(Cell cell, Map<String, Object> datas) {
		CellType cellType = cell.getCellType();
		if (cellType != CellType.STRING) {
			return;
		}
		String result = cell.getStringCellValue();

		if (!result.contains("!{") || !result.contains("}")) {
			return;
		}
		String key = result.substring(result.indexOf("!{") + 2, result.indexOf("}"));
		String replaceString = replaceDecimal(key, datas);
		if (StringUtil.isNotEmpty(replaceString)) {
			result = result.replace("!{" + key + "}", replaceDecimal(key, datas));
		}
		else {
			//解决两个参数其中一个为空整个字符串返回空值问题，例${PRODSERIES}${FINPRODNAME}  modify by liupeilin 20200301 begin
			//result = "";
			result = result.replace("!{" + key + "}", replaceDecimal(key, datas)==null?"":replaceDecimal(key, datas));
			//解决两个参数其中一个为空整个字符串返回空值问题，例${PRODSERIES}${FINPRODNAME}  modify by liupeilin 20200301 end
		}

		if(StringUtil.isNotEmpty(result)){
			//修改cell的格式，不然公式无法求和
			//修改之前要先将值清空不然会报错
			cell.setBlank();
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(Double.parseDouble(result));
		}else{
			cell.setCellValue(result);
		}

		if (result.contains("!{") && result.contains("}")) {
			replaceData2(cell, datas);
		}

	}

	/**
	 * Remove a row by its index
	 */
	public static void insertRow(Row row, int number) {
		Sheet sheet = row.getSheet();
		int rowIndex = row.getRowNum();
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, number);// 将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
		}
	}

	/**
	 * 复制单元格
	 *
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag true则连同cell的内容一起复制
	 */
	public static void copyCell(HSSFWorkbook wb, HSSFCell srcCell, HSSFCell distCell,
								boolean copyValueFlag) {
		// 样式
		distCell.setCellStyle(srcCell.getCellStyle());

		// 评论
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// 不同数据类型处理
		CellType srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		if (copyValueFlag) {
			if (CellType.NUMERIC.equals(srcCellType)) {
				if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				}
				else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			}
			else if (CellType.STRING.equals(srcCellType)) {
				distCell.setCellValue(srcCell.getRichStringCellValue());
			}
			else if (CellType.BLANK.equals(srcCellType)) {
				// nothing21
			}
			else if (CellType.BOOLEAN.equals(srcCellType)) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			}
			else if (CellType.ERROR.equals(srcCellType)) {
				distCell.setCellErrorValue(srcCell.getErrorCellValue());
			}
			else if (CellType.FORMULA.equals(srcCellType)) {
				distCell.setCellFormula(srcCell.getCellFormula());
			}
			else { // nothing29
			}
		}
	}

	public static void copyNullCell(HSSFWorkbook wb, HSSFCell srcCell, HSSFCell distCell, boolean copyValueFlag) {

		// 评论
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		// 不同数据类型处理
		CellType srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		distCell.setCellValue(srcCell.getRichStringCellValue());
	}


	private static String replaceString(String key, Map<String, Object> datas) {
		if(key.contains(".")) {
			String subKey = key.substring(0, key.indexOf("."));
			Object object = datas.get(subKey);
			if(object != null) {
				Map<String, Object> map = transBean2Map(object);
				return replaceString(key.substring(subKey.length()+1), map);
			}
		}
		if(datas.get(key) != null && datas.get(key) instanceof BigDecimal) {
			DecimalFormat df = new DecimalFormat("#,##0.00#########");
			return df.format((BigDecimal)datas.get(key));
		}
		if(datas.get(key) != null && datas.get(key) instanceof Date) {

			return DateUtil.format((Date)datas.get(key));
		}
		if(datas.get(key) != null && datas.get(key) instanceof Long) {
			return String.valueOf(datas.get(key));
		}
		return (String)datas.get(key);
	}

	private static String replaceString3(String key, Map<String, Object> datas) {
		if(key.contains(".")) {
			String subKey = key.substring(0, key.indexOf("."));
			Object object = datas.get(subKey);
			if(object != null) {
				Map<String, Object> map = transBean2Map(object);
				return replaceString3(key.substring(subKey.length()+1), map);
			}
		}
		if(datas.get(key) != null && datas.get(key) instanceof BigDecimal) {
			return datas.get(key).toString();
		}
		if(datas.get(key) != null && datas.get(key) instanceof Date) {

			return DateUtil.format((Date)datas.get(key));
		}
		if(datas.get(key) != null && datas.get(key) instanceof Long) {
			return String.valueOf(datas.get(key));
		}
		return (String)datas.get(key);
	}

	private static String replaceDecimal(String key, Map<String, Object> datas) {
		if(key.contains(".")) {
			String subKey = key.substring(0, key.indexOf("."));
			Object object = datas.get(subKey);
			if(object != null) {
				Map<String, Object> map = transBean2Map(object);
				return replaceDecimal(key.substring(subKey.length()+1), map);
			}
		}
		if(datas.get(key) != null && datas.get(key) instanceof BigDecimal) {
			DecimalFormat df = new DecimalFormat("###0.00#########");
			return df.format((BigDecimal)datas.get(key));
		}
		if(datas.get(key) != null && datas.get(key) instanceof Long) {
			return String.valueOf(datas.get(key));
		}
		return (String)datas.get(key);
	}

	private static void replaceListData(Cell cell, Map<String, Object> datas) {
		String result = cell.getStringCellValue();

		if (!result.contains("#{") || !result.contains("}")) {
			return;
		}
		String cellvalue = result.substring(result.indexOf("#{") + 2, result.indexOf("}"));
		String[] keys = cellvalue.split("\\.");

		Object value = datas.get(keys[1]);
		if (value == null) {
			cell.setCellValue("");
			return;
		}
		if (value instanceof Date) {
			cell.setCellValue((Date)value);
		}
		else if (value instanceof String) {
			cell.setCellValue((String)value);
		}
		else if (value instanceof Boolean) {
			cell.setCellValue((Boolean)value);
		}
		else if (value instanceof Calendar) {
			cell.setCellValue((Calendar)value);
		}
		else if (value instanceof RichTextString) {
			cell.setCellValue((RichTextString)value);
		}
		else if (value instanceof Double) {
			cell.setCellValue((Double)value);
		}
		else if (value instanceof BigDecimal) {
			BigDecimal cellValue = (BigDecimal)value;
//            DecimalFormat df = new DecimalFormat("#,##0.00#########");
			cell.setCellValue(cellValue.doubleValue());
		}
		else if (value instanceof Integer) {
			Integer cellValue = (Integer)value;
			cell.setCellValue(cellValue);
		}
	}

	// Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		// add by dubaojun 2020-06-18 青岛银行个性化 start
		if(obj instanceof Map) {
			return (Map)obj;
		}
		// add by dubaojun 2020-06-18 青岛银行个性化 end
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return map;

	}

	/**
	 * Remove a row by its index
	 */
	public static void removeRow(Row row) {
		Sheet sheet = row.getSheet();
		int rowIndex = row.getRowNum();
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum)
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);// 将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
		// if (rowIndex == lastRowNum) {
		// sheet.removeRow(row);
		// }
	}


	@SuppressWarnings("unchecked")
	public static File createFileIfCellRed(Map<String, Object> paraMap, String exceltemplate, String flieName) {
		// 模板路径pbc/
		String template = FileNameUtil.getTplFile(exceltemplate + ".xls");
		// 模板文件后缀名
		String fileType = GlobalConstant.POINT + FilenameUtils.getExtension(template);
		// 存放地址
		String depositfilePath = SvrDataUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		// 文件名称
		String outputFilepath = depositfilePath + File.separator + flieName;

		// 工作表对象
		HSSFWorkbook wb = null;
		InputStream inputStream = null;
		try {
			// 原文件
			File srcFile = new SafelyFile(template);
			if (srcFile.exists()) {
				inputStream = new FileInputStream(srcFile);
			} else {
				inputStream = TisUtil.class.getResourceAsStream("/tpl/" + exceltemplate + ".xls");
			}
			// 工作表对象初始化
			wb = new HSSFWorkbook(inputStream);

			for (Sheet sheet : wb) {
				List<HSSFRow> listRows = new ArrayList<HSSFRow>();

				int rownum = sheet.getLastRowNum();
				for (int i = 0; i <= rownum; i++ ) {
					HSSFRow row = (HSSFRow)sheet.getRow(i);
					/*
					 * if (row == null||row.getCell(0)==null) { continue; }
					 */
					// 解决第二个或之后的单元格有参数却不替换的问题 modify by liupeilin 20200301 begin
					if (row == null) {
						continue;
					}
					//解决第二个或之后的单元格有参数却不替换的问题 modify by liupeilin 20200301 end
					int cellnum = row.getLastCellNum();

					for (int j = 0; j < cellnum; j++ ) {
						//
						if(row.getCell(j)==null){
							continue;
						}
						CellType cellType = row.getCell(j).getCellType();
						if (cellType != CellType.STRING) {
							continue;
						}
						String cellvalue = row.getCell(j).getStringCellValue();
						if (StringUtil.isNotEmpty(cellvalue)) {
							if (cellvalue.contains("${") && cellvalue.contains("}")) {
								replaceData(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("!{") && cellvalue.contains("}")) {
								replaceData2(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("@{") && cellvalue.contains("}")) {
								replaceData3(row.getCell(j), paraMap);
							}
							if (cellvalue.contains("#{") && cellvalue.contains("}")) {
								listRows.add(0, row);
								break;
							}
						}
					}
				}
				//for(int index=0;index<listRows.size();index++){
				//HSSFRow row=listRows.get(index);
				for (HSSFRow row : listRows) {
					List list = null;
					for(int index=0;index<row.getLastCellNum();index++){
						//for (Cell cell : row) {
						Cell cell=row.getCell(index);
						if(cell==null){
							continue;
						}
						CellType cellType = cell.getCellType();
						if (cellType != CellType.STRING) {
							continue;
						}
						String cellvalue = cell.getStringCellValue();
						if (StringUtil.isNotEmpty(cellvalue)) {
							if (cellvalue.contains("#{") && cellvalue.contains("}")) {
								String key = cellvalue.substring(cellvalue.indexOf("#{") + 2,
										cellvalue.indexOf("."));

								if(paraMap.get(key) instanceof List){
									list = (List)paraMap.get(key);
									break;
								}else if(paraMap.get(key) instanceof Map){
									String valKey = cellvalue.substring(cellvalue.indexOf(".")+1,
											cellvalue.indexOf("}"));
									Map<String,Object> resultMap = new HashMap<>();
									resultMap = (Map)paraMap.get(key);
									String value=null;
									if(resultMap.get(valKey)!=null){
										value=resultMap.get(valKey).toString();
										if(!StringUtil.isNotEmpty(value)||value==""){
											value = "";
										}

									}else{
										value = "";
									}
									HSSFCell baseCell = (HSSFCell) row.getCell(index);
									HSSFCell newcell = (HSSFCell) row.createCell(index);
									copyCell((HSSFWorkbook) wb, baseCell, newcell, true);
									if(exceltemplate.contains("Monthreport")){
										try {
											newcell.setCellValue(Double.parseDouble(value));
											if(!exceltemplate.contains("G06")){
												newcell.setCellType(baseCell.getCellType());
											}
										}catch(Exception e){
											newcell.setCellValue(value);
										}
									}else{
										if(StringUtil.isNotEmpty(resultMap.get(valKey+"Diff"))){ //【DAT表、登记表】有数据的内容，并且不一致的内容红色标记
											Integer keyDiff=Integer.parseInt(resultMap.get(valKey+"Diff").toString());
											if(1==keyDiff) {
												CellStyle newStyle = wb.createCellStyle();
												newStyle.cloneStyleFrom(newcell.getCellStyle());
												// 修改字体颜色为红色
												Font ztFont1 = wb.createFont();
												ztFont1.setColor(Font.COLOR_RED); // 将字体设置为“红色”
												// 字体颜色设置
												newStyle.setFont(ztFont1);
												newcell.setCellStyle(newStyle);
											}
										}
										newcell.setCellValue(value);
									}
								}else if(paraMap.get(key)==null){
									insertRow(row, 1);
									Map<String, Object> value2=new HashMap<String, Object>();
									insertRow(row, 1);

									Row newrow = sheet.createRow(row.getRowNum() + 1);


									for (int i = 0; i < row.getLastCellNum(); i++) {
										HSSFCell cell2 = (HSSFCell) row.getCell(i);
										HSSFCell newcell2 = (HSSFCell) newrow.createCell(i);
										copyNullCell((HSSFWorkbook) wb, cell2, newcell2, true);

										String cellvalue2 = newcell2.getStringCellValue();
										if (StringUtil.isNotEmpty(cellvalue2)) {
											if (cellvalue2.contains("#{") && cellvalue2.contains("}")) {
												replaceListData(newcell2, value2);
											}
										}
									}
									removeRow(row);
									break;
								}

							}
						}
					}

					// 增加新行
					if(list != null) {
						insertRow(row, list.size());

						for (Object obj : list) {
							Map<String, Object> value = transBean2Map(obj);
							// // 获取需要设值的行
							Row newrow = sheet.getRow(row.getRowNum() + list.indexOf(obj) + 1);// sheet.createRow(row.getRowNum()
							// +
							// list.indexOf(obj)
							// + 1);
							if (newrow == null) {
								newrow = sheet.createRow(row.getRowNum() + list.indexOf(obj) + 1);
							}
							for (int i = 0; i < row.getLastCellNum(); i++) {
								HSSFCell cell = (HSSFCell) row.getCell(i);
								HSSFCell newcell = (HSSFCell) newrow.createCell(i);
								copyCell((HSSFWorkbook) wb, cell, newcell, true);

								CellType cellType = newcell.getCellType();
								if (cellType != CellType.STRING) {
									continue;
								}
								String cellvalue = newcell.getStringCellValue();
								if (StringUtil.isNotEmpty(cellvalue)) {
									if (cellvalue.contains("#{") && cellvalue.contains("}")) {
										replaceListDataDiffRed(wb,newcell, value);
									}
								}
							}
						}

						removeRow(row);
					}
				}
				sheet.setForceFormulaRecalculation(true);
			}

			File file = new SafelyFile(outputFilepath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream outputStream = new FileOutputStream(file);

			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
			return file;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		finally {
			try {
				if(wb != null) {
					wb.close();
				}
				if(inputStream != null) {
					inputStream.close();
				}
			}
			catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	private static void replaceListDataDiffRed(HSSFWorkbook wb,Cell cell, Map<String, Object> datas) {
		String result = cell.getStringCellValue();

		if (!result.contains("#{") || !result.contains("}")) {
			return;
		}
		String cellvalue = result.substring(result.indexOf("#{") + 2, result.indexOf("}"));
		String[] keys = cellvalue.split("\\.");
		String valKey=keys[1];
		Object value = datas.get(valKey);
		if (value == null) {
			cell.setCellValue("");
			return;
		}
		if (value instanceof Date) {
			cell.setCellValue((Date)value);
		}
		else if (value instanceof String) {
			cell.setCellValue((String)value);
		}
		else if (value instanceof Boolean) {
			cell.setCellValue((Boolean)value);
		}
		else if (value instanceof Calendar) {
			cell.setCellValue((Calendar)value);
		}
		else if (value instanceof RichTextString) {
			cell.setCellValue((RichTextString)value);
		}
		else if (value instanceof Double) {
			cell.setCellValue((Double)value);
		}
		else if (value instanceof BigDecimal) {
			//BigDecimal cellValue = (BigDecimal)value;
			cell.setCellValue(value.toString());
		}
		else if (value instanceof Integer) {
			Integer cellValue = (Integer)value;//转为整形会变成.0,转为字符串正常
			cell.setCellValue(value.toString());
		}
		if(StringUtil.isNotEmpty(datas.get(valKey+"Diff"))){ //【DAT表、登记表】有数据的内容，并且不一致的内容红色标记
			Integer keyDiff=Integer.parseInt(datas.get(valKey+"Diff").toString());
			if(1==keyDiff) {
				CellStyle newStyle = wb.createCellStyle();
				newStyle.cloneStyleFrom(newStyle);
				// 修改字体颜色为红色
				Font ztFont1 = wb.createFont();
				ztFont1.setColor(Font.COLOR_RED); // 将字体设置为“红色”
				// 字体颜色设置
				newStyle.setFont(ztFont1);
				newStyle.setAlignment(HorizontalAlignment.CENTER);
				cell.setCellStyle(newStyle);
			}
		}
	}

}