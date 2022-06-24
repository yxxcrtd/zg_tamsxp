/*
 * 文件名：InvestorsHoldInfoRegist001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.svreport.chb.subsidiamonthreport.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.constant.CheckConfConstants;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dao.ChbCheckRuleoutMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbCheckRuleout;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrCommonExportService;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.report.interf.common.util.TisUtil;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforesub.service.BeforeSubCheck;
import com.joyintech.tams.svreport.chb.monthreport.service.XlsxToHtml;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dao.ChbSubsidiaMonthReportMapper;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDetailDto;
import com.joyintech.tams.svreport.chb.subsidiamonthreport.dto.ChbMonthReportSubDto;
import com.joyintech.tools.collectors.JoyinCollectors;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * 理财子公司月度报表登记service
 *
 * @author 杜保军
 * @version 1.0 2020年6月3日
 * @since 1.0
 */
@Service
public class ChbSubsidiaMonthReportService extends SvrCommonExportService<ChbMonthReportSubDto> {
    @Autowired
    private ChbSubsidiaMonthReportMapper chbSubsidiaMonthReportMapper;
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;
    @Autowired
    private ChbCheckRuleoutMapper chbCheckRuleoutMapper;
    @Autowired
    private BeforeSubCheck beforeSubCheck;
    @Autowired
    private SubsidiaMonthReportRegistBatchService subsidiaMonthReportRegistBatchService;

    /**
     * 1000
     */
    private static final BigDecimal TENTHOUSAND = new BigDecimal(10000);

    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportSelectOrDeleteCountForShenFen}")
    public int reportSelectOrDeleteCountForShenFen;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询页面数据
     *
     * @param pageParam 查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    public Page<ChbMonthReportSubDto> searchPage(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        // 将发送状态改为空
        pageParam.put("sendStatus", "");
        int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
        Page<ChbMonthReportSubDto> page = queryForPage(pagenum, legth,
                () -> chbSubsidiaMonthReportMapper.searchPage(pageParam));
        return page;
    }

    /**
     * 处理查询前的参数 <功能详细描述>
     *
     * @param pageParam
     * @see
     * @since 1.0
     */
    public void dealSearchParam(Map<String, Object> pageParam) {
        // 选择行数据时
        if (pageParam.containsKey("keyList")) {
            String keyList = pageParam.get("keyList").toString();
            try {
                List<ChbMonthReportSubDto> astValueList = JsonUtils.json2List(keyList, ChbMonthReportSubDto.class);
                pageParam.put("reportList", astValueList);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            // 查询参数条件
            // 数据状态
            String status = (String) pageParam.get("status");
            // 报送状态
            String sendStatus = (String) pageParam.get("sendStatus");
            // 反馈状态
            String submitFeedbackStatus = (String) pageParam.get("submitFeedbackStatus");

            // 将数据状态当做多选下拉框处理
            if (StringUtil.isNotBlank(status)) {
                pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            }
            // 将报送状态当做多选下拉框处理
            if (StringUtil.isNotBlank(sendStatus)) {
                pageParam.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
            }
            // 将反馈状态当做多选下拉框处理
            if (StringUtil.isNotBlank(submitFeedbackStatus)) {
                pageParam.put("submitFeedbackStatusList",
                        StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
            }

        }
    }

    /**
     * 导出功能查询
     *
     * @param pageParam 查询参数
     * @return 结果集
     * @see
     * @since 1.0
     */

    public List<ChbMonthReportSubDto> selectAllList(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        @SuppressWarnings("unchecked")
        List<ChbMonthReportSubDto> dataList = (List<ChbMonthReportSubDto>) pageParam.get("astValueList");// 查询参数列表
        // 存放查询结果集
        List<ChbMonthReportSubDto> dtoList = new ArrayList<ChbMonthReportSubDto>();
        if (CollectionUtils.isEmpty(dataList)) {
            dtoList = chbSubsidiaMonthReportMapper.searchPage(pageParam);
        }
        return dtoList;
    }

    /**
     * 查询页面数据
     *
     * @param pageParam 查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    public ChbMonthReportSubDto searchPageBysubmitDate(Map<String, Object> pageParam) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (pageParam.get("enddate") != null) {
            String end = pageParam.get("enddate").toString().replace("-", "");
            param.put("termid", end);
        }
        List<ChbMonthReportSubDto> pbcMonthReportList = chbSubsidiaMonthReportMapper.searchPage(param);
        ChbMonthReportSubDto repos = new ChbMonthReportSubDto();
        for (ChbMonthReportSubDto report : pbcMonthReportList) {
            if (repos.getStartdate() == null) {
                repos.setStartdate(report.getStartdate());
            }
            // 取最小的开始日期
            if (repos.getStartdate().compareTo(report.getStartdate()) > 0) {
                repos.setStartdate(report.getStartdate());
            }
            // 有未报送
            if ("01".equals(report.getSendStatus())) {
                repos.setSendStatus(report.getSendStatus());
            } else {// 有已报送
                repos.setStatus("02");
            }
        }
        return repos;
    }

    /**
     * 重新生成报表
     *
     * @param map 生成日期
     * @return 生成结果
     */
    @Transactional(rollbackFor = Exception.class)
    public String recalReport(Map<String, Object> map) {
        String submitDateEnd = (String) map.get("enddate");
        Date endDate = DateUtil.parse(submitDateEnd + "-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        subsidiaMonthReportRegistBatchService.genReport(calendar.getTime(), null, true);
        return "success";
    }

    /**
     * 更新状态
     *
     * @param map 参数
     * @see
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public void updateSumStaus(Map<String, Object> map) {
        dealSearchParam(map);
        List<ChbMonthReportSubDto> chbBotAstPositionRegistList = new ArrayList<ChbMonthReportSubDto>();
        if (map.get("flag") != null && "01".equals(map.get("flag"))) {
            // 按条件查询全部
            chbBotAstPositionRegistList = chbSubsidiaMonthReportMapper.searchPage(map);
        } else {
            chbBotAstPositionRegistList = (List<ChbMonthReportSubDto>) map.get("reportList");
        }
        if (CollectionUtils.isNotEmpty(chbBotAstPositionRegistList)) {
            chbBotAstPositionRegistList = chbBotAstPositionRegistList.stream().filter(item -> {
                return "01".equals(item.getSendStatus());
            }).collect(Collectors.toList());
        }
        this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(), chbBotAstPositionRegistList);

    }

    /**
     * 分批次执行updateInterfSubmitAndSeq
     *
     * @param investList 数据
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateInterfSubmitAndSeq(String sendStatus, List<ChbMonthReportSubDto> investList) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }
        int maxDataCountOneTime = reportSelectOrDeleteCountForShenFen;
        int dataCount = investList.size();
        if (dataCount <= maxDataCountOneTime) {
            // 已报送数据进留痕表
            chbSubsidiaMonthReportMapper.insertReportHis(investList);
            // 删除正式表已报送数据
            chbSubsidiaMonthReportMapper.deleteLastSubmitRegist(investList);
            chbSubsidiaMonthReportMapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
            return;
        }
        List<ChbMonthReportSubDto> newDataList = new ArrayList<ChbMonthReportSubDto>();
        newDataList.addAll(investList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++) {
            logger.info("单次更新登记表start===");
            // 已报送数据进留痕表
            chbSubsidiaMonthReportMapper.insertReportHis(investList);
            // 删除正式表已报送数据
            chbSubsidiaMonthReportMapper.deleteLastSubmitRegist(investList);
            chbSubsidiaMonthReportMapper.updateInterfSubmitAndSeqBatch(sendStatus,
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
            logger.info("单次更新登记表end===");
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            logger.info("单次更新登记表start===");
            // 已报送数据进留痕表
            chbSubsidiaMonthReportMapper.insertReportHis(investList);
            // 删除正式表已报送数据
            chbSubsidiaMonthReportMapper.deleteLastSubmitRegist(investList);
            chbSubsidiaMonthReportMapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
            logger.info("单次更新登记表end===");
        }
    }

    /**
     * 已手工处理的数据将报送状态改为未报送
     *
     * @param map 传入主键
     * @see
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public void cancelMannul(Map<String, Object> map) {
        dealSearchParam(map);
        List<ChbMonthReportSubDto> list = new ArrayList<ChbMonthReportSubDto>();
        if (map.get("flag") != null && "01".equals(map.get("flag"))) {
            list = chbSubsidiaMonthReportMapper.searchPage(map);
        } else {
            list = (List<ChbMonthReportSubDto>) map.get("reportList");
        }
        chbSubsidiaMonthReportMapper.cancelMannultRegist(list);
        // 将履历表中最后创建时间的数据挪回登记表
        chbSubsidiaMonthReportMapper.insertReportDataFromHis(list);
        // 将挪回数据从履历表中删除
        chbSubsidiaMonthReportMapper.deleteReportDataHis(list);
    }

    /**
     * 导出理财子公司月度报表数据
     *
     * @param pageId 画面ID
     * @param selectParam 检索参数
     * @return 导出结果
     */
    @Override
    public ResponseEntity<byte[]> export(String pageId, Map<String, Object> selectParam) {
        @SuppressWarnings("unchecked")
        Map<String, Object> data = restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/ChbSubsidiaMonthReport/createData", selectParam, Map.class);
        String termname = data.get("termname").toString();
        String exceltemplate = "SubsidiaMonthReport";
        String fileName = "理财子公司月度报表" + "（" + termname + "）.xls";

        try {
            return exportMonthReportExcel(data, exceltemplate, fileName);
        } catch (IOException e) {
            logger.error("导出银行月度报表错误:" + e.getMessage(), e);
            return null;
        }

    }

    /**
     * 根据查询参数获取对应的数据，做成Map供画面查看或导出。
     *
     * @param selectParam 查询参数
     * @return 报表数据
     */
    public Map<Object, Object> createData(Map<String, Object> selectParam) {
        dealSearchParam(selectParam);
        Map<Object, Object> data = new HashMap<Object, Object>();
        @SuppressWarnings("unchecked")
        List<ChbMonthReportSubDto> dataList = (List<ChbMonthReportSubDto>) selectParam.get("reportList");// 查询参数列表
        String reportuuid = "";
        if (dataList != null && dataList.size() > 0) {
            reportuuid = dataList.get(0).getReportuuid();
        } else {
            reportuuid = selectParam.get("reportuuid").toString();
        }
        // 导出excel或预览时，值为0的单元格显示成0还是显示成空，读取系统参数
        String isShowZero = SvrDataUtil.getSysParam(SVReportGlobalConstant.CHB_IS_SHOW_ZERO);
        ChbMonthReportSubDto monthreport = chbSubsidiaMonthReportMapper.selectMonthReportSubByPrimaryKey(reportuuid);
        data.put("report", monthreport);
    	/**青岛月报日期修改  start  by dongxiangyang  start*/
    	SimpleDateFormat  formate=new  SimpleDateFormat("yyyy-MM-dd");
    	String  termname=formate.format(monthreport.getEnddate());
    	/**青岛月报日期修改  start  by dongxiangyang  end*/
    	//data.put("termname", monthreport.getTermname());
    	data.put("termname", termname);
        selectParam.put("termid", monthreport.getTermid());
        selectParam.put("reportuuid", reportuuid);
        List<ChbMonthReportSubDetailDto> detailList = chbSubsidiaMonthReportMapper
                .selectMonthReportSubDetail(selectParam);
        List<String> propertyCodeList = chbSubsidiaMonthReportMapper.selectAllBalanceTemplate();
        Map<String, String> map = new HashMap<String, String>();
        for (String propertyCode : propertyCodeList) {
            data.put(propertyCode, map);
        }
        for (ChbMonthReportSubDetailDto detail : detailList) {
            dealProdData(detail, isShowZero);
            data.put(detail.getPropertycode(), divide10000(detail));
        }
        return data;
    }

    public ResponseEntity<byte[]> exportMonthReportExcel(Map<String, Object> data, String exceltemplate,
            String fileName) throws IOException {
        File depositfile = TisUtil.createFile(data, exceltemplate, fileName);
        return ExportUtil.export(fileName, depositfile, true);
    }

    /**
     * 预览理财子公司月度统计报表
     *
     * @param pageid 画面ID
     * @param selectParam 检索参数
     * @return 数据
     */
    public String priview(String pageid, Map<String, Object> selectParam) {
        String exceltemplate = "SubsidiaMonthReport";
        @SuppressWarnings("unchecked")
        Map<String, Object> data = restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/ChbSubsidiaMonthReport/createData", selectParam, Map.class);
        data.put("termname", data.get("termname").toString());
        String termname = data.get("termname").toString();
        String flieName = exceltemplate + "（" + termname + "）.xls";
        File depositfile = TisUtil.createFile(data, exceltemplate, flieName);
        return this.priView(depositfile, true);
    }

    /**
     * 导出文件
     *
     * @param downloadFile 需要被下载的文件
     * @param deleteAfterExport 下载后是否删除当前原文件
     * @return 返回responseEntity
     * @throws IOException 异常
     * @see
     * @since 1.0
     */
    public String priView(File downloadFile, boolean deleteAfterExport) {
        String rs = XlsxToHtml.readExcelToHtml(downloadFile, true);
        if (deleteAfterExport) {
            downloadFile.delete();
        }
        return rs;
    }

    public List<String> getCheckRepList(Map<String, Object> param) {
        List<String> keylist = new ArrayList<String>();
        dealSearchParam(param);
        List<ChbMonthReportSubDto> list = chbSubsidiaMonthReportMapper.searchPage(param);
        if (CollectionUtils.isNotEmpty(list)) {
            for (ChbMonthReportSubDto dto : list) {
                keylist.add(dto.getReportuuid());
            }
        }
        return keylist;
    }

    public File createFile(Map<String, Object> data, String exceltemplate) {
        String termname = data.get("termname").toString();
        String flieName = exceltemplate + "_" + termname + ".xls";
        // G06需要将文件按命名规则生成并打包成zip
        if (null != data.get("fileName")) {
            flieName = data.get("fileName").toString();
        }
        File depositfile = null;
        FileOutputStream fos = null;
        InputStream fis = null;
        try {

            // 关联模板
            XLSTransformer transformer = new XLSTransformer();

            String template = FileNameUtil.getTplFile(exceltemplate + ".xls");
            // 传放地址
            String depositfilePath = SvrDataUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

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
            if (srcFile.exists()) {
                fis = new FileInputStream(srcFile);
            } else {
                fis = ChbSubsidiaMonthReportService.class.getResourceAsStream("/tpl/" + exceltemplate + ".xls");
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
            transformer.transformXLS(temp, data, depositfilePath + File.separator + flieName);
        } catch (Exception e) {
            logger.info(exceltemplate + "生成失败！");
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    e.printStackTrace();
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                        e.printStackTrace();
                    }
                }
            }
        }
        return depositfile;
    }

    public static String getXmlPath(String filename) {
        String path = ChbSubsidiaMonthReportService.class.getClassLoader().getResource("").getPath();
        path = path.replace('/', '\\'); // 将/换成\
        path = path.replace("file:", ""); // 去掉file:
        path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
        path += "tpl\\" + filename;
        path = path.replace("report-batch", "report-parent\\report");
        return path;
    }

    public static String getXmlPathLinux(String filename) {
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            path += "tpl/" + filename;
            path = path.replace("file:", ""); // 去掉file:
            path = path.replace("report-batch", "report-parent/report");
            return path;
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public ChbMonthReportSubDetailDto divide10000(ChbMonthReportSubDetailDto detail) {
        // 期末余额
        if (detail.getBalance() != null) {
            detail.setBalance(detail.getBalance().divide(TENTHOUSAND, 9, BigDecimal.ROUND_HALF_UP));
        }
        // 完全符合通知产品期末余额
        if (detail.getBalanceAcc() != null) {
            detail.setBalanceAcc(detail.getBalanceAcc().divide(TENTHOUSAND, 9, BigDecimal.ROUND_HALF_UP));
        }
        // 未完全符合通知产品期末余额
        if (detail.getBalanceInc() != null) {
            detail.setBalanceInc(detail.getBalanceInc().divide(TENTHOUSAND, 9, BigDecimal.ROUND_HALF_UP));
        }
        // 母行期末余额
        if (detail.getBalanceBan() != null) {
            detail.setBalanceBan(detail.getBalanceBan().divide(TENTHOUSAND, 9, BigDecimal.ROUND_HALF_UP));
        }
        // 子公司期末余额
        if (detail.getBalanceSub() != null) {
            detail.setBalanceSub(detail.getBalanceSub().divide(TENTHOUSAND, 9, BigDecimal.ROUND_HALF_UP));
        }
        return detail;
    }

    /**
     * 校验数据
     *
     * @param checkKeys 校验的KEY
     * @return 校验结果
     */
    public List<ExcelValidDto> checkData(List<String> checkKeys) {
        Map<String, CheckReport001Dto> checkmap = new HashMap<String, CheckReport001Dto>();
        Map<String, ChbMonthReportSubDto> repmap = new HashMap<String, ChbMonthReportSubDto>();
        List<ChbMonthReportSubDto> issuelist = chbSubsidiaMonthReportMapper.searchByKeys(checkKeys);
        repmap = issuelist.stream()
                .collect(Collectors.toMap(ChbMonthReportSubDto::getReportuuid, (p) -> p, (key1, key2) -> key1));
        List<ExcelValidDto> result = new ArrayList<ExcelValidDto>();
        List<CheckReport001Dto> resultlist = new ArrayList<CheckReport001Dto>();
        // 检查登记表中小于今天所有未报送的数据
        if (CollectionUtils.isNotEmpty(checkKeys)) {
            // 调用检查方法
            resultlist.addAll(check());
            checkmap = resultlist.stream()
                    .collect(Collectors.toMap(CheckReport001Dto::getRefseqno, (p) -> p, (key1, key2) -> key1));
            for (String key : checkKeys) {
                if (checkmap.get(key) != null) {
                    ExcelValidDto exc = new ExcelValidDto();
                    exc.setCheckResult(checkmap.get(key).getCheckResult());
                    exc.setErrorInfo(checkmap.get(key).getErrorInfo());
                    exc.setWarnInfo(checkmap.get(key).getWarnInfo());
                    exc.setValidKey(checkmap.get(key).getValidKey());
                    result.add(exc);
                } else {
                    ExcelValidDto exc = new ExcelValidDto();
                    exc.setValidKey("月报期数:" + repmap.get(key).getTermid());
                    exc.setCheckResult("01");
                    result.add(exc);
                }
            }
        }
        return result;
    }

    /**
     * @return List<CheckReport001Dto>
     * @Description 检查登记表中小于今天所有未报送的数据
     * @version 1.7 2019年6月24日
     */
    private List<CheckReport001Dto> check() {
        // TODO:校验逻辑需要等设计出来再开发
        // 数据日期为系统时间
        Date checkdate = DateUtil.parse(DateUtil.getSysDate());
        // 删除今天所有已检查的数据
        checkReport001Mapper.delReportDatasByCodeAndDate(DictCodeListExt2.报表名称_理财子公司月度统计表.getValue(), checkdate,null);
        // 增加例外情况过滤
        List<ChbCheckRuleout> listRuleout = chbCheckRuleoutMapper.selectDataByCheckTypeAndReprotCode(
                CheckConfConstants.CHB_CHECK_TYPE_B, CheckConfConstants.CHB_PRD_ISSUE_SUB);
        Map<String, List<ChbCheckRuleout>> mapRuleout = listRuleout.stream()
                .collect(JoyinCollectors.groupingBy(x -> x.getProdId()));
        // 检查数据
        List<CheckReport001Dto> resultList = beforeSubCheck.checkProdSubsidiaMonthReport(checkdate, mapRuleout);
        // 报错检查异常数据
        int batchCount = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT)
                ? "100" : SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_INSERT_COUNT));// 每批commit的个数
        int batchLastIndex = batchCount;// 每批最后一个的下标
        for (int index = 0; index < resultList.size();) {
            if (batchLastIndex > resultList.size() - 1) {
                batchLastIndex = resultList.size();
                checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
                break;// 数据删除完毕,退出循环
            } else {
                checkReport001Mapper.batchInsert(resultList.subList(index, batchLastIndex));
                index = batchLastIndex;// 设置下一批下标
                batchLastIndex = index + batchCount;
            }
        }
        return resultList;
    }

    /**
     * @param detail
     * @param isShowZero 是否显示为0
     * @Description 导出预览为零的数据显示空还是显示零
     * @version 1.7 2019年7月4日
     */
    private void dealProdData(ChbMonthReportSubDetailDto detail, String isShowZero) {
        // 显示成零
        if ("01".equals(isShowZero)) {
            if (detail.getBalance() == null) {
                detail.setBalance(BigDecimal.ZERO);
            }
            if (detail.getTerms() == null) {
                detail.setTerms(BigDecimal.ZERO);
            }
            if (detail.getBalanceAcc() == null) {
                detail.setBalanceAcc(BigDecimal.ZERO);
            }
            if (detail.getTermsAcc() == null) {
                detail.setTermsAcc(BigDecimal.ZERO);
            }
            if (detail.getBalanceInc() == null) {
                detail.setBalanceInc(BigDecimal.ZERO);
            }
            if (detail.getTermsInc() == null) {
                detail.setTermsInc(BigDecimal.ZERO);
            }
            if (detail.getBalanceBan() == null) {
                detail.setBalanceBan(BigDecimal.ZERO);
            }
            if (detail.getProdcountsBan() == null) {
                detail.setProdcountsBan(0L);
            }
            if (detail.getTermsBan() == null) {
                detail.setTermsBan(BigDecimal.ZERO);
            }
            if (detail.getBalanceSub() == null) {
                detail.setBalanceSub(BigDecimal.ZERO);
            }
            if (detail.getProdcountsSub() == null) {
                detail.setProdcountsSub(0L);
            }
            if (detail.getTermsSub() == null) {
                detail.setTermsSub(BigDecimal.ZERO);
            }
        } else if ("02".equals(isShowZero)) {
            if (BigDecimal.ZERO.equals(detail.getBalance())) {
                detail.setBalance(null);
            }
            if (BigDecimal.ZERO.equals(detail.getTerms())) {
                detail.setTerms(null);
            }
            if (BigDecimal.ZERO.equals(detail.getBalanceAcc())) {
                detail.setBalanceAcc(null);
            }
            if (BigDecimal.ZERO.equals(detail.getTermsAcc())) {
                detail.setTermsAcc(null);
            }
            if (BigDecimal.ZERO.equals(detail.getBalanceInc())) {
                detail.setBalanceInc(null);
            }
            if (BigDecimal.ZERO.equals(detail.getTermsInc())) {
                detail.setTermsInc(null);
            }
            if (BigDecimal.ZERO.equals(detail.getBalanceBan())) {
                detail.setBalanceBan(null);
            }
            if (Long.valueOf(0).equals(detail.getProdcountsBan())) {
                detail.setProdcountsBan(null);
            }
            if (BigDecimal.ZERO.equals(detail.getTermsBan())) {
                detail.setTermsBan(null);
            }
            if (BigDecimal.ZERO.equals(detail.getBalanceSub())) {
                detail.setBalanceSub(null);
            }
            if (Long.valueOf(0).equals(detail.getProdcountsSub())) {
                detail.setProdcountsSub(null);
            }
            if (BigDecimal.ZERO.equals(detail.getTermsSub())) {
                detail.setTermsSub(null);
            }
        }
    }

}
