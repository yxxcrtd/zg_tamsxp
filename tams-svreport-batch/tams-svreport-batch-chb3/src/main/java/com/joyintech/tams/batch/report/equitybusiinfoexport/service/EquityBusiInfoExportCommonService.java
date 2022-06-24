package com.joyintech.tams.batch.report.equitybusiinfoexport.service;

import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.handler.JoyinBookConfig;
import com.joyintech.framework.export.handler.JoyinCellConfig;
import com.joyintech.framework.export.handler.JoyinSheetConfig;
import com.joyintech.framework.export.handler.ReportCfgHandler;
import com.joyintech.framework.listener.FileAttachmentProcess;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.*;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.tams.batch.report.equitybusiinfoexport.dao.EquityBusiInfoExportMapper;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dto.EquityBusiInfoRegist001Dto;
import com.joyintech.tams.report.system.subinfobatchlog.dao.ChbSubinfoAutoBatchLogMapper;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.LongAdder;

/**
 * 投资者明细信息登记导出
 *
 * @author zhangmingming
 * @version 1.0 2020年01月13日
 * @see EquityBusiInfoExportCommonService
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EquityBusiInfoExportCommonService extends CommonExportService<EquityBusiInfoRegist001Dto> {

    @Autowired
    private EquityBusiInfoExportMapper equityBusiInfoExportMapper;

    @Autowired
    private FileAttachmentProcess fileAttachmentProcess;


    @Autowired
    private ChbSubinfoAutoBatchLogMapper chbSubinfoAutoBatchLogMapper;

    private static final String CHB_EQUDET_SUB_NAME = "投资者明细信息登记";

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(EquityBusiInfoExportCommonService.class);
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public String export(long count, Map<String,Object> param, ChbSubinfoBatchLog batchLog){
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        int size = StringUtil.integerValueOf(CacheUtil.getSysParam("export_excel_row_count"), 50000);
        long fileCount = (long)Math.ceil((double)count/size);
        int maxCorePoolSize = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_MAX_CORE_POOL_SIZE)?"5":SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_MAX_CORE_POOL_SIZE));
        ExecutorService service = new JoyinThreadPoolExecutor(maxCorePoolSize);
        List<Callable<Boolean>> tasks = new ArrayList<>();
        boolean success = true;
        if(count>0){
            long start = 0;
            long end = 0;
            for (int i = 0; i < fileCount; i++) {
                start = end;
                end = Math.min(count,size * (i + 1));
                int index = i+1;
                Map<String,Object> newParam = new HashMap<>();
                param.put("start",start);
                param.put("end",end);
                param.put("index", index);
                newParam.putAll(param);
                tasks.add(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return exportByPage(newParam);
                    }
                });
            }
        }
        // 为线程池添加任务
        List<Future<Boolean>> taskResult = null;
        try {
            taskResult = service.invokeAll(tasks);
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            batchLog.setDealStatus(DictCodeListExt3.报送处理状态_异常结束.getValue());
            batchLog.setDealMessage("导出发生异常,请联系管理员!");
            chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
            return "fail";
        }
        for (Future<Boolean> res : taskResult) {
            try {
                success = success && res.get();
            }
            catch (InterruptedException | ExecutionException e) {
                logger.error(e.getMessage(), e);
                batchLog.setDealStatus(DictCodeListExt3.报送处理状态_异常结束.getValue());
                batchLog.setDealMessage("导出发生异常,请联系管理员!");
                chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);
                return "fail";
            }
        }

        // 任务结束，停止线程池的所有线程
        service.shutdown();


        File tmpPath = new File(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_TMP_PATH));
        File[] files = tmpPath.listFiles();

        String filePath = CacheUtil.getSysParam("access_exportfilepath") +
                CHB_EQUDET_SUB_NAME + GlobalConstant.UNDER_LINE  + DateUtil.getDateTimestamp().replaceAll(":", "") + ".zip";
        File outFile = new File(filePath);
        try {
            ZipFileUtil.compressZip(outFile,true,files);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        String fileName = outFile.getName();
        String seqNo = IDGenerator.generate();
        UploadFile uploadFile = new UploadFile(fileName,seqNo,outFile);

        if(null != fileAttachmentProcess) {
            fileAttachmentProcess.upload(uploadFile);
        }

        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        long cosumeTime = endTime.getTime() - startTime.getTime();
        batchLog.setCompresFileId(seqNo);
        batchLog.setCompresFileName(fileName);
        batchLog.setCompresFilePath(filePath);
        batchLog.setFileCount(fileCount);
        batchLog.setRecordCount(count);
        batchLog.setConsumeTime(cosumeTime);
        batchLog.setStartTime(startTime);
        batchLog.setEndTime(endTime);
        batchLog.setUpdateUser(LoginUserUtil.getUserName());
        batchLog.setUpdateTime(endTime);
        long finalCount = equityBusiInfoExportMapper.countExportNum(param);
        if(count == finalCount){
            batchLog.setDealStatus(DictCodeListExt3.报送处理状态_正常结束.getValue());
            batchLog.setDealMessage("成功生成文件!");
        }else {
            batchLog.setDealStatus(DictCodeListExt3.报送处理状态_警告.getValue());
            batchLog.setDealMessage("总记录数不符合或者其他异常,请联系管理员!");
        }
        chbSubinfoAutoBatchLogMapper.updateByPrimaryKeySelective(batchLog);

        return "success";
    }




    private boolean exportByPage(Map<String,Object> param){
        logger.info("分线程读取数据开始"+DateUtil.getDateTimestamp());
        List<EquityBusiInfoRegist001Dto> listDto =  equityBusiInfoExportMapper.selectAllDataByPage(param);
        dealDataList(listDto);
        dealBigDecmailData(listDto);
        int index = (int) param.get("index");
        logger.info("分线程导出开始"+DateUtil.getDateTimestamp());
        batchMakeFile("/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf_batch.xml",listDto,index);
        logger.info("分线程导出结束"+DateUtil.getDateTimestamp());
        return true;
    }

    private File batchMakeFile(String configFile,List<EquityBusiInfoRegist001Dto> contents, int index){
        if (null == contents) {
            contents = Collections.emptyList();
        }
        JoyinBookConfig reportConfig = this.getConfig(configFile);
        Workbook wb = null;

        try {
            wb = this.getWorkbook(reportConfig);
        } catch (IOException var22){
            throw new BusinessException(var22);
        }
        String fileType = "." + FileNameUtil.getFileExtName(reportConfig.getTempleteFile());
        int count = StringUtil.integerValueOf(CacheUtil.getSysParam("export_excel_row_count"), 50000);
        File targetFile = null;
        List<File> fileList = new ArrayList(contents.size() / count + 1);

        int sum = 1;
        try {
            while(contents.size() > count) {
                List<?> subList = contents.subList(0, count);
                contents = contents.subList(count, contents.size());
                fileList.add(this.makeSingleFile(wb, reportConfig, (String)null, fileType, subList, new LongAdder(), 0L, count, index, sum));
                sum++;
            }

            fileList.add(this.makeSingleFile(wb, reportConfig, (String)null, fileType, contents, new LongAdder(), 0L, count, index, sum));
        } catch (Exception var24) {          
           logger.error(var24.getMessage(),var24);
        } finally {
            try {
                if (null != wb) {
                    wb.close();
                }
            } catch (Exception var21) {
            }

        }

        if (fileList.size() == 1) {
            targetFile = (File)fileList.get(0);
        } else {
            String exportFilePath = CacheUtil.getSysParam("access_exportfilepath");
            String zipFileName = FileNameUtil.createFileName("zip");
            targetFile = new File(exportFilePath + File.separator + zipFileName);

            try {
                ZipFileUtil.compressZip(targetFile, true, (File[])fileList.toArray(new File[0]));
            } catch (Exception var23) {
                Iterator var12 = fileList.iterator();

                while(var12.hasNext()) {
                    File file = (File)var12.next();
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        }

        return targetFile;
    }
    private JoyinBookConfig getConfig(String configFile) {
        ReportCfgHandler handler = new ReportCfgHandler();
        JoyinBookConfig reportConfig = null;

        try {
            reportConfig = handler.readConfig(configFile);
            return reportConfig;
        } catch (Exception var5) {
            throw new BusinessException(var5);
        }
    }

    private Workbook getWorkbook(JoyinBookConfig reportConfig) throws IOException {
        Workbook wb = null;
        String EXPORT_EXCEL_TYPE = CacheUtil.getSysParam("export_excel_type");
        String fileType = ".xlsx";
        String fileName = (String)StringUtil.splitToList(".", reportConfig.getTempleteFile()).get(0);
        if (StringUtil.equals(EXPORT_EXCEL_TYPE, com.joyintech.framework.constant.DictCodeList.是否_否.getValue())) {
            fileType = ".xls";
        }

        String tempFilePath = fileName + fileType;
        File templeteFile = new File(this.buildTempleteFilePath(tempFilePath));
        reportConfig.setTempleteFile(fileName + fileType);
        InputStream stream = null;
        try {
            if (templeteFile.exists()) {
                stream = new FileInputStream(templeteFile);
            } else {
                stream = CommonExportService.class.getResourceAsStream("/tpl/" + tempFilePath);
            }

            if (StringUtil.equals(fileType, ".xls")) {
                wb = new HSSFWorkbook((InputStream)stream);
            } else {
                if (!StringUtil.equals(fileType, ".xlsx")) {
                    ((InputStream)stream).close();
                    throw new BusinessException("不是一个有效的Excel文件");
                }

                wb = new XSSFWorkbook((InputStream)stream);
            }
        }finally {
            if (stream != null){
                stream.close();
            }
        }
        return (Workbook)wb;
    }

    private File makeSingleFile(Workbook wb, JoyinBookConfig reportConfig, String pageId, String fileType, List<?> contents, LongAdder current, long total, int sheetMaxRowCount, int fileNameIndex, int sum) throws IOException {
//        ReportCfgHandler handler = new ReportCfgHandler();
        wb.close();
        wb = this.getWorkbook(reportConfig);
        // add by zhangmingming删除模板中sheet1的最后一行,要不然SXXSFWorkbook无法create_row 20200122 start
        Sheet sheet1 = wb.getSheetAt(0);
        sheet1.removeRow(sheet1.getRow(sheet1.getLastRowNum()));
        // add by zhangmingming删除模板中sheet1的最后一行,要不然SXXSFWorkbook无法create_row 20200122 end
        SXSSFWorkbook swb = new SXSSFWorkbook((XSSFWorkbook)wb,1000);

        List<JoyinSheetConfig> sheetList = reportConfig.getSheetConfList();

        for(int i = 0; i < sheetList.size(); ++i) {
            JoyinSheetConfig sheetCfg = (JoyinSheetConfig)sheetList.get(i);
            String beanName = sheetCfg.getJavaBean();
            List<JoyinCellConfig> cellList = sheetCfg.getCellConfList();
            Sheet sheet = swb.getSheetAt(i);
            int index = sheetCfg.getRowStart();
//            Row lastRow = sheet.getRow(sheet.getLastRowNum());
            Iterator var19 = contents.iterator();

//            index += 2;
            while(var19.hasNext()) {
                Object dto = var19.next();
                Map<String, Object> map = BeanUtil.object2Map(dto);
//                Row row = sheet.getRow(index++);
//                if (row == null) {
//                    handler.copyRow(swb, lastRow, sheet.createRow(index - 1), false);
//                    row = sheet.getRow(index - 1);
//                }

                Row row = sheet.createRow(index++);
                for(int ci = 0; ci < cellList.size(); ++ci) {
                    JoyinCellConfig cfg = (JoyinCellConfig)cellList.get(ci);
                    Cell cell = row.getCell(ci, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String propName = cfg.getBeanPropName();
                    if (StringUtil.isNotEmpty(propName)) {
//                        Cell hCell = lastRow.getCell(ci);
//                        if (null == cell && null != hCell) {
//                            cell = row.createCell(ci);
//                            handler.copyCell(swb, hCell, cell, false);
//                            this.setCellStyle(swb, cell.getCellStyle());
//                        }

                        Object value = map.get(propName);
                        if (StringUtil.isNotEmpty(value)) {
                            //----add by lingxiaoqing on 2020-06-10 for 投资者明细导出格式按照文本格式，否则中债客户端导入失败----->>>>
                            value = StringUtil.getLimitLengthString(value.toString(), 16383, "");
                            cell.setCellValue((String)value);
                            //----add by lingxiaoqing on 2020-06-10 for 投资者明细导出格式按照文本格式，否则中债客户端导入失败-----<<<<
                            //----del by lingxiaoqing on 2020-06-10 for 投资者明细导出格式按照文本格式，否则中债客户端导入失败----->>>>
//                            if (value instanceof Date) {
//                                cell.setCellValue(DateUtil.formatDateTime((Date)value));
//                            } else if (value instanceof String) {
//                                if (StringUtil.isNotEmpty(value)) {
//                                    value = StringUtil.getLimitLengthString(value.toString(), 16383, "");
//                                }
//
//                                cell.setCellValue((String)value);
//                            } else if (value instanceof Boolean) {
//                                cell.setCellValue((Boolean)value);
//                            } else if (value instanceof Calendar) {
//                                cell.setCellValue((Calendar)value);
//                            } else if (value instanceof RichTextString) {
//                                cell.setCellValue((RichTextString)value);
//                            } else if (value instanceof Double) {
//                                cell.setCellValue((Double)value);
//                            } else if (value instanceof BigDecimal) {
//                                BigDecimal cellValue = (BigDecimal)value;
//                                cell.setCellValue(cellValue.doubleValue());
//                            } else if (value instanceof Long) {
//                                Long cellValue = (Long)value;
//                                cell.setCellValue(cellValue.doubleValue());
//                            } else if (value instanceof Integer) {
//                                Integer cellValue = (Integer)value;
//                                cell.setCellValue(cellValue.doubleValue());
//                            }
                            //----del by lingxiaoqing on 2020-06-10 for 投资者明细导出格式按照文本格式，否则中债客户端导入失败-----<<<<
                            this.setCellValue(beanName, cell, propName, value);
                        }
                    }
                }
            }
        }

        String outFileName = CHB_EQUDET_SUB_NAME + GlobalConstant.UNDER_LINE +sum + GlobalConstant.UNDER_LINE + System.currentTimeMillis() + fileNameIndex +  fileType;
        String tmpPath = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_TMP_PATH);
        File file = new File(tmpPath + File.separator + outFileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            swb.write(fos);
        } catch (IOException e) {
            throw e;
        }
        finally{
            if(null != fos){
                fos.close();
            }
            if(null != swb){
                swb.close();
            }
        }
        return file;
    }

    private void dealDataList(List<EquityBusiInfoRegist001Dto> list) {
        list.forEach(item -> {
            item.setFundAccPlace(com.joyintech.tams.report.common.util.SysDataUtil.getZZDictCodeAndName(DictCode.销售区域_中债, item.getFundAccPlace()));
            item.setIsConsign(
                    com.joyintech.tams.report.common.util.SysDataUtil.getZZDictCodeAndName(DictCode.是否_中债, item.getIsConsign()));
            item.setAgencyFsa(com.joyintech.tams.report.common.util.SysDataUtil.getZZDictCodeAndName(DictCode.监管机构,item.getAgencyFsa()));
            item.setBusiFsa(com.joyintech.tams.report.common.util.SysDataUtil.getZZDictCodeAndName(DictCode.监管机构2,item.getBusiFsa()));
            item.setBusiType(com.joyintech.tams.report.common.util.SysDataUtil.getZZDictCodeAndName(DictCode.业务种类, item.getBusiType()));
            item.setChannel(SysDataUtil.getZZDictCodeAndName(DictCode.投资者明细渠道,item.getChannel()));
            item.setEquityConfDateStr(DateUtil.format(item.getEquityConfDate(), "yyyyMMdd"));
        });
    }
    private void dealBigDecmailData(List<EquityBusiInfoRegist001Dto> list) {
        list.forEach(item -> {
            item.setAmountExportExcel((item.getAmount()==null?null:item.getAmount().setScale(2, RoundingMode.HALF_UP).toString()));
            item.setAmountCnyExportExcel((item.getAmountCny()==null?null:item.getAmountCny().setScale(2,RoundingMode.HALF_UP).toString()));
            item.setFeeAmtExportExcel((item.getFeeAmt()==null?null:item.getFeeAmt().setScale(2,RoundingMode.HALF_UP).toString()));
            item.setProdValueExportExcel((item.getProdValue()==null?null:item.getProdValue().setScale(5,RoundingMode.HALF_UP).toString()));
            item.setSharesExportExcel((item.getShares()==null?null:item.getShares().setScale(5,RoundingMode.HALF_UP).toString()));
        });
    }
}