/**  
 * @Project     tams-app  
 * @File        InvestmentsInfoService.java  
 * @Package     com.joyintech.tams.invest.report.regulatoryreporting.investmentsinfo.service  
 * @Version     V1.0  
 * @Date        2019年8月7日 上午10:00:51  
 * @Author      高健  
 * Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.  
 */  
    
package com.joyintech.tams.invest.report.regulatoryreporting.investmentsinfo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.disclosure.common.common.util.ServiceApplication;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dto.InvestmentBusinessCounterDetailsDto;
import com.joyintech.service.report.regulatoryreporting.investmentsinfo.dto.InvestmentBusinessDetailsDto;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.invest.report.regulatoryreporting.investmentsinfo.dto.ChbBankMonthReportModelDto;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.svreport.chb.monthreport.service.ChbMonthReport001Service;


import net.sf.jxls.transformer.XLSTransformer;

/**  
 * @Description 表内外投资业务情况统计表业务逻辑层    
 * @ClassName   InvestmentsInfoService  
 * @Date        2019年8月7日 上午10:00:51  
 * @Author      高健  
 * Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.  
 */
@Service("ValInvestmentsInfo001Service")
public class InvestmentsInfo001Service extends CommonExportService<ChbBankMonthReportModelDto>{
	/**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(InvestmentsInfo001Service.class);
    /**
     * 针对weblogic部署无法获取模版问题
     */
    private static final String TMP_DIR = "/tpl/";
	
	/**
	 * @Description 导出EXCEL文件  
	 * @Author      高健  
	 * @Date        2019年8月3日 下午3:13:04  
	 * @param @return
	 * @param @throws Exception 参数  
	 * @return ResponseEntity<byte[]> 返回类型   
	 * @throws  
	 */  
	public ResponseEntity<byte[]> export(Map<String, Object> pageParam) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> data = restTemplate.postForObject(ServiceApplication.REPORT_SERVICE_NAME + "/InvestmentsInfo/createData",
				pageParam, Map.class);
		// 导出年份
		String exportYear = (String) pageParam.get("exportYear");
		// 导出季度
		String exportQuarter = (String) pageParam.get("exportQuarter");

		//String exceltemplate = "InvestmentsInfo001";
		  //String fileName = "表内外投资业务情况统计表（共6张）大型银行报表(" + exportYear+"年" +exportQuarter+ "月" + ").xls";
		// File depositfile = createFile(data,exceltemplate);
		// File depositfile = TisUtil.createFile(data, exceltemplate, fileName);

        //表7-2
        List<InvestmentBusinessCounterDetailsDto> listCounter=restTemplate.postForList(ServiceApplication.REPORT_SERVICE_NAME + "/InvestmentsInfo/createTable7OtherInfo",
                pageParam, InvestmentBusinessCounterDetailsDto.class);
        data.put("listCounter",listCounter);

        //表3附表
		pageParam.put("rule","local");
		List<InvestmentBusinessDetailsDto> listLocal=restTemplate.postForList(ServiceApplication.REPORT_SERVICE_NAME + "/InvestmentsInfo/createTable3AddData",
		pageParam, InvestmentBusinessDetailsDto.class);
		pageParam.put("rule","otherPlace");
		List<InvestmentBusinessDetailsDto> listOtherPlace=restTemplate.postForList(ServiceApplication.REPORT_SERVICE_NAME + "/InvestmentsInfo/createTable3AddData",
		pageParam, InvestmentBusinessDetailsDto.class);
		data.put("listLocal",listLocal);
		data.put("listOtherPlace",listOtherPlace);


        List<File> sourceFiles = new ArrayList<File>();
		//表内外投资业务情况统计表
		data.put("fileName", "表内外投资业务情况统计表（共6张）大型银行报表(" + exportYear+"年" +exportQuarter+ "月" + ").xls");
		File assetsFile = TisUtil.createFile(data, "InvestmentsInfo001",  "表内外投资业务情况统计表（共6张）大型银行报表(" + exportYear+"年" +exportQuarter+ "月" + ").xls");

		sourceFiles.add(assetsFile);
		//表3附表
		data.put("fileName", "表3附表(" + exportYear+"年" +exportQuarter+ "月" + ").xls");
		File table3AddFile = TisUtil.createFile(data, "InvestmentsInfot3Add", "表3附表(" + exportYear+"年" +exportQuarter+ "月" + ").xls");


		//表３附表单元格合并
		InputStream inputStream = new FileInputStream(table3AddFile);
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		Workbook wb = new HSSFWorkbook(fs);
			Sheet sheet = wb.getSheetAt(0);
			int index1 = 3;
			int index2 = index1+listLocal.size()-1;
			int index3=index2+1;
			int index4=index3+listOtherPlace.size()-1;
			if(index1!=index2){
				sheet.addMergedRegion(new CellRangeAddress(index1, index2, 0, 0));
			}
			if(index3!=index4){
				sheet.addMergedRegion(new CellRangeAddress(index3, index4, 0, 0));
			}
			for(int j=1;j<12;j++){
				sheet.getRow(index2).getCell(j).
						setCellStyle(sheet.getRow(0).getCell(0).getCellStyle());
				sheet.getRow(index4).getCell(j).
						setCellStyle(sheet.getRow(0).getCell(0).getCellStyle());
			}
		wb.write(new FileOutputStream(table3AddFile));

		sourceFiles.add(table3AddFile);

		// 如果生成excel文件个数大于1，生成压缩文件
		File outFile = null;
		String exportFilePath = SvrDataUtil.getSysParam(com.joyintech.framework.constant.GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

		String zipFileName = "表内外投资业务情况统计表"+"_"+exportYear+"年" +exportQuarter+ "月"+".zip";
		outFile = new File(exportFilePath + File.separator + zipFileName);
		// 导出路径不存在则重新生成
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		try {
			ZipFileUtil.compressZip(outFile, true, sourceFiles.toArray(new File[] {}));
		}
		catch (Exception e) {
			// 压缩文件失败后，删除所有临时文件
			for (File file : sourceFiles) {
				if (file.exists()) {
					file.delete();
				}
			}
			logger.error(e.getMessage(),e);
		}
		//删掉xls文件
		for(File single : sourceFiles){
			single.delete();
		}
		return ExportUtil.export(zipFileName, outFile, true);

	}
	/**  
	 * @Description 生产EXCEL文件  
	 * @Author      高健  
	 * @Date        2019年8月6日 下午6:08:15  
	 * @param @param pageParam
	 * @param @return
	 * @param @throws ParseException 参数  
	 * @return ExportFileDto 返回类型   
	 * @throws  
	 */  
//	private ExportFileDto createExcel(Map<String, Object> pageParam) throws ParseException {
//		// 导出年份
//		String exportYear = (String) pageParam.get("exportYear");
//		// 导出季度
//		String exportQuarter = (String) pageParam.get("exportQuarter");
//		
//		@SuppressWarnings("unchecked")
//		Map<String, Object> data = restTemplate.postForObject(ServiceApplication.REPORT_SERVICE_NAME + "/InvestmentsInfo/createData",
//				pageParam, Map.class);
//
//		
//		String exportFilePath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
//        String fileName = "表内外投资业务情况统计表（共6张）大型银行报表(" + exportYear+"年" +exportQuarter+ "月" + ").xls";
//        String tempName = fileName;
//        String tplFlodPath = "/regulatoryreporting/investmentsinfo/InvestmentsInfo001.xls";
//        String tplPath = FileNameUtil.getTplFile(tplFlodPath);
//        String outPath = exportFilePath + File.separator + fileName;
//        ExportFileDto exportFileDto = new ExportFileDto();
//        InputStream stream = null;
//        exportFileDto.setFileName(fileName);
//        exportFileDto.setFilePath(outPath);
//        try {
//            
//            //针对weblogic环境部署，FileNameUtil.getTplFile无法获取tpl文件夹下模版。
//            File templeteFile = new File(tplPath);
//            //文件存在情况,直接该路径获取文件流
//            if (templeteFile.exists()) {
//                stream = new FileInputStream(templeteFile);
//            }
//            //文件不存在情况，即weblogic部署情况，需要换方式获取文件流。
//            else {
//                stream = this.getClass().getResourceAsStream(TMP_DIR + tplFlodPath);
//            }
//            
//            ExcelTools.writeExcel(stream, tempName, data, outPath, new ExcelCellValueFunction() {
//                
//                @Override
//                public void execute(String tplKey, Object value, Cell cell, Row row, Sheet sheet, Workbook workbook) {
//                    
//                    CellStyle newStyle = workbook.createCellStyle();
//                    newStyle.cloneStyleFrom(cell.getCellStyle());
//                }
//            });
//        } catch (IOException e) {
//        	logger.error("",e);
//        } finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                	logger.error("",e);
//                }
//            }
//        }
//        return exportFileDto;
//	
//	}
	
	public File createFile(Map<String,Object> data,String exceltemplate){
//		String termname = data.get("termname").toString();
	    String flieName = exceltemplate+".xls";
	    File depositfile = null;
	    FileOutputStream fos=null;
		try{		
			
		// 关联模板
        XLSTransformer transformer = new XLSTransformer();
        
		String template = FileNameUtil.getTplFile(exceltemplate + ".xls");;
		//传放地址
		String depositfilePath = SvrDataUtil.getSysParam(
                GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
		
		depositfile = new File(depositfilePath + File.separator + flieName);
		if (!depositfile.getParentFile().exists()) {
			depositfile.getParentFile().mkdirs();
		}
		
		// 缓存区，一次性读取1024字节
		byte[] buffer = new byte[1024];
		String temp = depositfilePath + "ptl.xls";
		// 原文件
		File srcFile = new File(template);
		// 目标文件
		File destFile = new File(temp);
		InputStream fis = null;
		if (srcFile.exists()) {
			fis = new FileInputStream(srcFile);
		} else {
			fis = ChbMonthReport001Service.class.getResourceAsStream("/tpl/" + exceltemplate + ".xls");
		}
		 fos = new FileOutputStream(destFile);
		while (true) {
			// 实际读取的长度是 actuallyReaded,有可能小于1024
			int actuallyReaded = fis.read(buffer);
			// -1表示没有可读的内容了
			if (-1 == actuallyReaded)
				break;
			fos.write(buffer, 0, actuallyReaded);
			fos.flush();
		}
		transformer.transformXLS(temp, data, depositfilePath + File.separator +flieName);
	    }catch(Exception e){
	    	logger.info(exceltemplate+"生成失败！");
	    	throw new RuntimeException(e);
	    }finally{
	    	if(fos !=null){
	    		try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
		 return depositfile;
	}
}
