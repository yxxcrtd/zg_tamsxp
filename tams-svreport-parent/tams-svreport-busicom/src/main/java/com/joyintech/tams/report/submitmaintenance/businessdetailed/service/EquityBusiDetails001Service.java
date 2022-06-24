/*
 * 文件名：EquityBaseInfo001Service.java
 * 版权：Copyright 2017-2019 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中债报送子系统
 * 修改人：何阳阳
 * 修改时间：2017年9月14日
 * 修改内容：新建
 * 系统名称：中债报送子系统
 */

package com.joyintech.tams.report.submitmaintenance.businessdetailed.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.export.handler.JoyinBookConfig;
import com.joyintech.framework.export.handler.JoyinCellConfig;
import com.joyintech.framework.export.handler.JoyinSheetConfig;
import com.joyintech.framework.export.handler.ReportCfgHandler;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.listener.FileAttachmentProcess;
import com.joyintech.framework.progressbar.ProgressBarService;
import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.ChbEqudet;
import com.joyintech.tams.common.base.entity.ChbFileReadMonitorInfo;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckRule;
import com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib;
import com.joyintech.tams.common.svreport.datacheck.service.DataCheckService;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.TxtParseUtil;
import com.joyintech.tams.report.common.XmlParseUtil;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.report.common.util.ChbSensitiveInfoUtil;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.IBDDataCheckUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpColumn;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.report.investormerger.dto.ChbEquHoldCheckDto;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.dao.EquityBusiDetails001Mapper;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.dto.EquiteBusiDetails001Dto;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.entity.InterestsBusinessDetailed;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.util.BusinessDetailInfoCheckUtil;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfo001Mapper;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.EquityInfoCheckUtil;
import com.joyintech.tams.report.submitmaintenance.exchangerate.dao.Exchangerate001Mapper;
import com.joyintech.tams.report.submitmaintenance.transformrule.dao.ChbTransformRuleObjDtoMapper;
import com.joyintech.tams.report.submitmaintenance.transformrule.dao.TransformRule001Mapper;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.ChbTransformRuleObjDto;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.TransformRule001Dto;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dto.EquityBusiInfoRegist001Dto;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;


/**
 * 〈一句话功能简述〉
 *
 * @author zyf
 * @version 1.0 2017年9月18日
 * @see EquityBusiDetails001Service
 * @since 1.0
 */
@Service("equityBusiDetails001Service")
@Transactional(rollbackFor = Exception.class)
public class EquityBusiDetails001Service extends CommonExportService<EquiteBusiDetails001Dto> {

    /**
     * sql最大一次最大删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public String reportSelectOrDeleteCount;

    @Value("${application.reportInterfOneTimeSqlLdrDataCount}")
    private int reportInterfOneTimeSqlLdrDataCount;

    /**
     * 上游接口解析读取文件处理,单线程处理数据条数
     */
    @Value("${application.reportInterfOneTimeDataCount}")
    private int reportInterfOneTimeDataCount;


    /**
     * sqlldr用数据库连接
     */
    @Value("${spring.datasource.reportSqlldrDatabaseName}")
    private String reportSqlldrDatabaseName;

    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
    private int reportInterfOneTimeSqlLdrCommitCount;

    /**
     * CHB_EQUDET_NAME
     */
    private static final String CHB_EQUDET_NAME = "CHB_EQUDET";
    
    /**
     * CHB_EQUDET
     */
    private static final String CHB_EQUDET = "CHB_EQUDET";

    /**
     * CHB_EQUDET_SUB_NAME
     */
    private static final String CHB_EQUDET_SUB_NAME = "CHB_EQUDET_SUB";

    /**
     * EquityBusiDetails001Mapper
     */
    @Autowired
    private EquityBusiDetails001Mapper equityBusiDetails001Mapper;

    @Autowired
    private TransformRule001Mapper transformRule001Mapper;
    
    @Autowired
    private ChbTransformRuleObjDtoMapper chbTransformRuleObjDtoMapper;
    
    /**
     * 查询对应币种的汇率
     */
    @Autowired
    private Exchangerate001Mapper exchangerate001Mapper;

    /**
     * 投资者明细信息登记mapper
     */
    @Autowired
    private EquityBusiInfoRegist001Mapper equityBusiInfoRegist001Mapper;
    /**
     * 投资者身份信息Mapper
     */
    @Autowired
    private EquityInfo001Mapper equityInfo001Mapper;



    @Autowired
    FileAttachmentProcess fileAttachmentProcess;

    @Autowired
    private BatchInsertService batchInsertService;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(EquityBusiDetails001Service.class);

    /**
     * 验证登记银行编码
     */
//    public static final String PATTERN_REGIST_BANK_CODE = "^C[0-9]{5}$";

    /**
     * 不含有汉子验证
     */
    public static final String PATTERN_IS_CHINA = "^[^\u4e00-\u9fa5]{0,}$";

    /**
     * 数字15,2验证
     */
    public static final String PATTERN_FIFTEEN_TWO = "([0-9]{0,13})(\\.(\\d){0,2})?$";

    /**
     * 数字18.5
     */
    public static final String PATTERN_EIGTEEN_FIFVE = "([0-9]{0,13})(\\.(\\d){0,5})?$";
    /**
     * YYYYMMDD 时间格式校验，正则表达式
     */
    public static final String DATE_FORMAT_YYYYMMDD = "((\\d{3}[1-9]|\\d{2}[1-9]\\d|\\d[1-9]\\d{2}|[1-9]\\d{3})(((0[13578]|1[02])(0[1-9]|[12]\\d|3[01]))|((0[469]|11)(0[1-9]|[12]\\d|30))|(02(0[1-9]|[1]\\d|2[0-8]))))|(((\\d{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";

    /**
     * 上游接口解析读取文件处理最大线程数
     */
    @Value("${application.interfMaxCorePoolSize}")
    private int interfMaxCorePoolSize;

    /**
     * 上游接口文件编码格式
     */
    @Value("${application.reportInterfFileEncoding}")
    public String reportInterfFileEncoding;

    /**
     * 上游接口解析分割符
     */
    @Value("${application.reportInterfSplitCharacter}")
    private String reportInterfSplitCharacter;

    /**
     * 进度条ID
     */
    private static final String PROCESS_KEY = "importProgressBar";

    /**
     * 进度条接口
     */
    @Autowired
    private ProgressBarService progressBarService;


    @Autowired
    private BaseMapper baseMapper;


    /**
     * 查询权益业务明细信息列表
     *
     * @param param 查询参数
     * @return 权益基期信息列表
     */
    public Page<EquiteBusiDetails001Dto> searchForPage(Map<String, Object> param) {
        // 参数处理
        dealParamMap(param);
        int pagenum = StringUtil.integerValueOf(""+param.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+param.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
        Page<EquiteBusiDetails001Dto> pageList = queryForPage(pagenum,length,()->equityBusiDetails001Mapper.queryForPage(param));
        // 返回结果处理
        dealReturnInfo(pageList.getRoot());
        return pageList;
    }

    /**
     * 共通方法：查询界面选择的参数处理
     *
     * @param param
     */
    private void dealParamMap(Map<String, Object> param) {
        String regCode = (String)param.get("regCode");
        List<String> regCodeList = StringUtil.splitToList(GlobalConstant.COMMA, regCode);
        param.put("regCodeList", regCodeList);
        String account = (String)param.get("account");
        param.put("accountList", StringUtil.splitToList(GlobalConstant.COMMA, account));
        String busiType = (String)param.get("busiType");
        param.put("busiTypeList", StringUtil.splitToList(GlobalConstant.COMMA, busiType));
        String channel = (String)param.get("channel");
        param.put("channelList", StringUtil.splitToList(GlobalConstant.COMMA, channel));
        String dataStatus = (String)param.get("dataStatus");
        param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, dataStatus));
    }

    /**
     * 共通方法：查询返回结果处理
     *
     * @param pageList
     */
    private void dealReturnInfo(List<EquiteBusiDetails001Dto> pageList) {
        for (EquiteBusiDetails001Dto equiteBusiDetails001Dto : pageList) {
            equiteBusiDetails001Dto.setStatusStr(SysDataUtil.getNameFromDataDict(
                    DictCode.维护数据状态, equiteBusiDetails001Dto.getDataStatus()));
            equiteBusiDetails001Dto.setBusiType(SysDataUtil.getZZDictCodeAndName(
                    DictCode.业务种类, equiteBusiDetails001Dto.getBusiType()));
            // equiteBusiDetails001Dto.setCcy(SysDataUtil.getZZDictCodeAndName(DictCode.中债币种,
            // equiteBusiDetails001Dto.getCcy()));
            equiteBusiDetails001Dto.setChannel(SysDataUtil.getZZDictCodeAndName(
                    DictCode.投资者明细渠道, equiteBusiDetails001Dto.getChannel()));
            equiteBusiDetails001Dto.setIsConsign(SysDataUtil.getZZDictCodeAndName(
                    DictCode.是否_中债, equiteBusiDetails001Dto.getIsConsign()));
            equiteBusiDetails001Dto.setAgencyFsa(SysDataUtil.getZZDictCodeAndName(
                    DictCode.监管机构, equiteBusiDetails001Dto.getAgencyFsa()));
            equiteBusiDetails001Dto.setBusiFsa(SysDataUtil.getZZDictCodeAndName(
                    DictCode.监管机构2, equiteBusiDetails001Dto.getBusiFsa()));
            equiteBusiDetails001Dto.setFundAccPlace(SysDataUtil.getZZDictCodeAndName(
                    DictCode.销售区域_中债, equiteBusiDetails001Dto.getFundAccPlace()));
            equiteBusiDetails001Dto.seteQuityConfDateStr(
                    DateUtil.format(equiteBusiDetails001Dto.getEquityConfDate(), "yyyyMMdd"));
        }
    }

    /**
     * 删除数据 --逻辑删除 更新状态
     *
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void doDelete(EquiteBusiDetails001Dto dto) {
        List<EquiteBusiDetails001Dto> keyList = dto.getSelectedItem();

        if (StringUtil.equals(dto.getSelectedItem().get(0).getDataStatus(),
                DictCodeListExt1.维护数据状态_有效.getValue())) {
            List<Map<String, String>> equityList = new ArrayList<Map<String, String>>();
            dtoToMap(keyList,equityList);
            // 删除导入用的临时的数据
            deleteImportTmpData();
            // 插入导入用的临时数据
            equityBusiDetails001Mapper.insertIntoImportTable(equityList);

            //更新前将数据插入历史表进行备份
            List<EquityBusiInfoRegist001Dto> registListnew = new ArrayList<>();
            for (EquiteBusiDetails001Dto detail : keyList) {
                //购买地
                if(StringUtil.isNotBlank(detail.getFundAccPlace())){
                    detail.setFundAccPlace(StringUtil.split(detail.getFundAccPlace(), StringUtil.SPACE)[0]);
                }
                if(StringUtil.isNotBlank(detail.getAgencyFsa())){
                    //监管机构
                    detail.setAgencyFsa(StringUtil.split(detail.getAgencyFsa(),StringUtil.SPACE)[0]);
                }
                //监管机构2
                if(StringUtil.isNotBlank(detail.getBusiFsa())){
                    detail.setBusiFsa(StringUtil.split(detail.getBusiFsa(),StringUtil.SPACE)[0]);
                }
                //是否代销
                if(StringUtil.isNotBlank(detail.getIsConsign())){
                    detail.setIsConsign(StringUtil.split(detail.getIsConsign(),StringUtil.SPACE)[0]);
                }
                //投资者明细渠道
                if(StringUtil.isNotBlank(detail.getChannel())){
                    detail.setChannel(StringUtil.split(detail.getChannel(),StringUtil.SPACE)[0]);
                }
                //业务种类
                if(StringUtil.isNotBlank(detail.getBusiType())){
                    detail.setBusiType(StringUtil.split(detail.getBusiType(),StringUtil.SPACE)[0]);
                }
                EquityBusiInfoRegist001Dto tempdto = new EquityBusiInfoRegist001Dto();
                BeanUtil.copyPropertiesIgnoreNull(tempdto, detail);
                registListnew.add(tempdto);
            }
            insertEquityBusiHisByPart(registListnew);
            // 分批次将投资者明细信息维护表中的数据更新为D
            logger.info("=======更新投资者明细信息维护表开始======" + DateUtil.getCurrentDateTime());
            this.updateByKeyListBatch(DictCodeListExt1.维护数据状态_删除.getValue(), keyList);
            logger.info("=======更新投资者明细信息维护表结束======" + DateUtil.getCurrentDateTime());

            // 根据list查询库中所有满足条件的列表
            List<EquityBusiInfoRegist001Dto> registList = new ArrayList<>();
            this.selectAllChbEqudetSubData(keyList, registList);
            Map<String, List<EquityBusiInfoRegist001Dto>> registMap = registList.stream().collect(
                    JoyinCollectors.groupingBy(
                            x -> x.getBusiSeqno() + GlobalConstant.UNDER_LINE + x.getRegCode()
                                    + GlobalConstant.UNDER_LINE + x.getRegistBankCode() + GlobalConstant.UNDER_LINE + x.getIndentifyMark()));
            // 删除list
            List<EquityBusiInfoRegist001Dto> delList = new ArrayList<EquityBusiInfoRegist001Dto>();
            // 更新list
            List<EquityBusiInfoRegist001Dto> updateList = new ArrayList<EquityBusiInfoRegist001Dto>();
            // 插入list
            List<EquiteBusiDetails001Dto> insertList = new ArrayList<EquiteBusiDetails001Dto>();
            // 当前批次报送时点集合
            Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
            List<Date> dateList = null;
            // 查询出所有身份信息
            List<EquityInfo001Dto> allInfoList = new ArrayList<EquityInfo001Dto>();
//            template.select(
//                "com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper.selectEquityInfoAll",
//                new ResultHandler() {
//                    @Override
//                    public void handleResult(ResultContext resultContext) {
//                        allInfoList.add((EquityInfo001Dto)resultContext.getResultObject());
//                    }
//                });
//            Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
//                JoyinCollectors.groupingBy(
//                    x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
//                         + x.getIndentifyMark()));

            allInfoList=equityBusiDetails001Mapper.getAllChbEquInfoOneTime();
            Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
                    JoyinCollectors.groupingBy(
                            x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
                                    + x.getIndentifyMark()));

            for (EquiteBusiDetails001Dto equitydto : keyList) {
                // 更新dto
                EquityBusiInfoRegist001Dto equityRegist1 = new EquityBusiInfoRegist001Dto();
                // 删除dto
                EquityBusiInfoRegist001Dto equityRegist2 = new EquityBusiInfoRegist001Dto();
                String key = equitydto.getBusiSeqno() + GlobalConstant.UNDER_LINE
                        + equitydto.getRegCode() + GlobalConstant.UNDER_LINE
                        + equitydto.getRegistBankCode() + GlobalConstant.UNDER_LINE
                        + equitydto.getIndentifyMark();
                List<EquityBusiInfoRegist001Dto> regist001Dtos = registMap.get(key);
                if (CollectionUtils.isNotEmpty(regist001Dtos)) {
                    // 报送时点通用方法
                    dateList = ChbDeadlineUtil.getChbEndDate(
                            DictCodeListExt2.报表名称_投资者明细信息登记.getValue(),
                            DictCodeListExt2.报送数据状态_删除.getValue(), dateMap, equitydto.getEquityConfDate());
                    if (CollectionUtils.isNotEmpty(dateList)) {
                        equityRegist1.setChbSubmitDeadline(dateList.get(0));
                        equityRegist1.setSubmitDeadline(dateList.get(1));
                    }
                    if (regist001Dtos != null && regist001Dtos.size() == 1) {
                        EquityBusiInfoRegist001Dto regist001Dto = regist001Dtos.get(0);
                        // 当登记表中只有一条数据，并且是未报送状态的，直接删除该条数据
                        if (DictCodeListExt2.报送状态_未报送.getValue().equals(
                                regist001Dto.getSubmitStatus())) {
                            equityRegist2 = new EquityBusiInfoRegist001Dto();
                            equityRegist2.setBusiSeqno(equitydto.getBusiSeqno());
                            equityRegist2.setRegCode(equitydto.getRegCode());
                            equityRegist2.setRegistBankCode(equitydto.getRegistBankCode());
                            equityRegist2.setIndentifyMark(equitydto.getIndentifyMark());
                            equityRegist2.setSubmitStatus(regist001Dto.getSubmitStatus());
                            delList.add(equityRegist2);
                        }
                        // 当登记表中只有一条数据，报送状态为手工处理或者接口报送时，重新插入一条删除状态的数据
                        else if (DictCodeListExt2.报送状态_接口报送.getValue().equals(
                                regist001Dto.getSubmitStatus())
                                || DictCodeListExt2.报送状态_手工处理.getValue().equals(
                                regist001Dto.getSubmitStatus())) {
                            // equityRegist1.setStatus(DictCodeList.报送数据状态_删除.getValue());
                            // equityRegist1.setBusiSeqno(equitydto.getBusiSeqno());
                            // equityRegist1.setRegCode(equitydto.getRegCode());
                            // equityRegist1.setRegistBankCode(equitydto.getRegistBankCode());
                            // equityRegist1.setIndentifyMark(equitydto.getIndentifyMark());
                            // equityRegist1.setSubmitStatus(DictCodeList.报送状态_未报送.getValue());
                            equitydto.setDataStatus(DictCodeListExt2.报送数据状态_删除.getValue());
                            insertList.add(equitydto);
                        }
                    }
                    // 当登记表中存两条数据时，直接将未报送一条数据的数据状态更新为更新为数据删除
                    else if (regist001Dtos != null && regist001Dtos.size() == 2) {
                        equityRegist1.setBusiSeqno(equitydto.getBusiSeqno());
                        equityRegist1.setRegCode(equitydto.getRegCode());
                        equityRegist1.setRegistBankCode(equitydto.getRegistBankCode());
                        equityRegist1.setIndentifyMark(equitydto.getIndentifyMark());
                        equityRegist1.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                        updateList.add(equityRegist1);
                    }
                }
            }
            // 批量执行delete
            logger.info(
                    "=======执行deleteByPrimaryBatch(投资者明细信息登记表)开始====" + DateUtil.getCurrentDateTime());
            this.deleteByPrimaryBatch(delList);
            logger.info(
                    "=======执行deleteByPrimaryBatch(投资者明细信息登记表)结束====" + DateUtil.getCurrentDateTime());
            // 批量执行insert
            logger.info("=======执行insertDeleteInvestRegBatch(投资者明细信息登记表)开始===="
                    + DateUtil.getCurrentDateTime());
            this.doInsertSubDataWithInfoDto(insertList,equityInfo001Map);
            logger.info("=======执行insertDeleteInvestRegBatch(投资者明细信息登记表)结束===="
                    + DateUtil.getCurrentDateTime());
            // 批量执行update
            logger.info("=======执行updateByPrimaryKeySelectedBatch(投资者明细信息登记表)开始===="
                    + DateUtil.getCurrentDateTime());
            this.updateByPrimaryKeySelectedBatch(DictCodeListExt2.报送数据状态_删除.getValue(),
                    updateList);
            logger.info("=======执行updateByPrimaryKeySelectedBatch(投资者明细信息登记表)结束===="
                    + DateUtil.getCurrentDateTime());
        }
        else if (StringUtil.equals(dto.getSelectedItem().get(0).getDataStatus(),
                DictCodeListExt1.维护数据状态_待处理.getValue())) {
            // 分批次逻辑删除持有者明细信息维护表
            this.updateByKeyListBatch(DictCodeListExt1.维护数据状态_删除.getValue(), keyList);
        }
        // 删除导入用的临时的数据
        deleteImportTmpData();
    }

    private void dtoToMap(List<EquiteBusiDetails001Dto> keyList,List<Map<String, String>> equityList){
        for(EquiteBusiDetails001Dto key:keyList){
            Map<String,String>  map=new HashMap<String,String>();
            map.put("BUSI_SEQNO", key.getBusiSeqno());
            map.put("REGIST_BANK_CODE", key.getRegistBankCode());
            map.put("ACCOUNT", key.getAccount());
            map.put("INDENTIFY_MARK", key.getIndentifyMark());
            equityList.add(map);
        }
    }


    /**
     * 通过主键批量获取投资者明细信息登记表中数据
     *
     * @param keyList 主键
     * @param chbInvestList 结果集
     */
    private void selectAllChbEqudetSubData(List<EquiteBusiDetails001Dto> keyList,
                                           List<EquityBusiInfoRegist001Dto> registList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
        int dataCount = keyList.size();

        if (dataCount <= maxDataCountOneTime) {
            registList.addAll(equityBusiDetails001Mapper.selectAllChbEqudetSubData(keyList));
            return;
        }

        List<EquiteBusiDetails001Dto> newDataList = new ArrayList<EquiteBusiDetails001Dto>();
        newDataList.addAll(keyList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            registList.addAll(equityBusiDetails001Mapper.selectAllChbEqudetSubData(
                    newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            registList.addAll(equityBusiDetails001Mapper.selectAllChbEqudetSubData(newDataList));
        }
    }

    /**
     * 批量物理删除投资者明细信息登记信息表
     *
     * @param delList 主键
     */
    private void deleteByPrimaryBatch(List<EquityBusiInfoRegist001Dto> delList) {
        if (CollectionUtils.isEmpty(delList)) {
            return;
        }
        int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
        int dataCount = delList.size();

        if (dataCount <= maxDataCountOneTime) {
            equityBusiDetails001Mapper.deleteByPrimaryBatch(delList);
            return;
        }

        List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
        newDataList.addAll(delList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityBusiDetails001Mapper.deleteByPrimaryBatch(
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityBusiDetails001Mapper.deleteByPrimaryBatch(newDataList);
        }

    }

    /**
     * 批量更新投资者明细信息登记信息表
     *
     * @param updateList 主键
     */
    private void updateByPrimaryKeySelectedBatch(String status,
                                                 List<EquityBusiInfoRegist001Dto> updateList) {
        if (CollectionUtils.isEmpty(updateList)) {
            return;
        }
        int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
        int dataCount = updateList.size();

        if (dataCount <= maxDataCountOneTime) {
            equityBusiDetails001Mapper.updateByPrimaryKeySelectedBatch(status, updateList);
            return;
        }

        List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
        newDataList.addAll(updateList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityBusiDetails001Mapper.updateByPrimaryKeySelectedBatch(status,
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityBusiDetails001Mapper.updateByPrimaryKeySelectedBatch(status, newDataList);
        }

    }

    /**
     * 批量逻辑更新维护投资者明细信息表
     *
     * @param stausD 删除的数据状态
     * @param keyList 主键
     */
    private void updateByKeyListBatch(String stausD, List<EquiteBusiDetails001Dto> keyList) {
        if (CollectionUtils.isEmpty(keyList)) {
            return;
        }
        int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount, "2");
        int dataCount = keyList.size();

        if (dataCount <= maxDataCountOneTime) {
            equityBusiDetails001Mapper.updateByKeyList(stausD, keyList);
            return;
        }

        List<EquiteBusiDetails001Dto> newDataList = new ArrayList<EquiteBusiDetails001Dto>();
        newDataList.addAll(keyList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityBusiDetails001Mapper.updateByKeyList(stausD,
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityBusiDetails001Mapper.updateByKeyList(stausD, newDataList);
        }

    }

    /**
     * 配置项中非空处理
     *
     * @param Count
     * @param flag
     * @return
     */
    public int parsToInt(String count, String flag) {
        if (StringUtil.isNotEmpty(count) && Integer.parseInt(count) <= 0) {
            if ("1".equals(flag)) {
                return 20;
            }
            else if ("1".equals(flag)) {
                return 500;
            }
            else {
                return 100;
            }
        }
        else {
            return Integer.parseInt(count);
        }
    }

    /**
     * 查询所有内容，不分页 --导出功能需要导出查询条件的所有内容
     *
     * @param param 画面查询参数
     * @return 查询结果列表
     */
    public List<EquiteBusiDetails001Dto> queryReportList(Map<String, Object> param) {
        List<EquiteBusiDetails001Dto> pageList = new ArrayList<>();
        String keyList = (String)param.get("keyList");
        if (StringUtil.isNotEmpty(keyList)) {
            try {
                pageList = JsonUtils.json2List(keyList, EquiteBusiDetails001Dto.class);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
        else {
            // 参数处理
            dealParamMap(param);
            pageList = equityBusiDetails001Mapper.queryForPage(param);
            // 返回结果处理
            dealReturnInfo(pageList);
        }
        return pageList;
    }

    /**
     * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
     *
     * @param fileName 导入的文件名名
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForImportData(Map<String,Object> param) {
        @SuppressWarnings("unchecked")
        List<List<String>> dataList=(List<List<String>>)param.get("dataList");
        String bankCode = (String)param.get("bankCode");
        String deptCode = (String)param.get("deptCode");
        String orgCode = (String)param.get("orgCode");
        String userName = (String) param.get("userName");

        AjaxResult webResult = new AjaxResult();
       
            try {
               
                if (CollectionUtils.isNotEmpty(dataList)) {
                	//add by dengyu 20210430 begin 
                	//用于接收  checkDataInfo(mapList, queryList,entitiesList,bankCode,deptCode,orgCode,userName);返回值
                    List<EquiteBusiDetails001Dto> entitiesList = new ArrayList<>();
                  //add by dengyu 20210430 end
                    
                    List<Map<String, String>> mapList = listToMap(dataList);
                  //add by dengyu 20210430 begin 
                	//用于构造查询条件
                    List<EquiteBusiDetails001Dto> queryList =   listToChbDto(dataList) ;
                    //add by dengyu 20210430 end
                    
                    //add by zhengxiaolian 2019-12-24
                    List<Map<String, String>> equityList = new ArrayList<>();
                   // List<Map<String, String>> mapListEx = listToMapEx(dataList);
                    equityList.addAll( listToMapEx(dataList));
                    // 删除导入用的临时的数据
                    deleteImportTmpData();
                    // 插入导入用的临时数据
                    equityBusiDetails001Mapper.insertIntoImportTable(equityList);
                  //add by zhengxiaolian 2019-12-24
                    
                    List<ExcelValidDto> checkResult = checkDataInfo(null,mapList, queryList,entitiesList,bankCode,deptCode,orgCode,userName);
                    if (checkResult.size() > 0) {
                        webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                        return webResult;
                    }

                    // 查询导入文件中的数据在数据库中已经存在的数据
                    Map<String, EquiteBusiDetails001Dto> allChbEqudetDataMap = this.selectAllChbEqudetData(
                    		queryList);
                    if (allChbEqudetDataMap != null && allChbEqudetDataMap.size() > 0) {
                        // 将序号放入list中
                        List<String> excelNumList = new ArrayList<String>();
                        List<Integer> excelNumIntList = new ArrayList<Integer>();
                        for (EquiteBusiDetails001Dto equite001Dto : entitiesList) {
                            String dataKey = equite001Dto.getBusiSeqno()
                                             + GlobalConstant.UNDER_LINE
                                             + equite001Dto.getAccount()
                                             + GlobalConstant.UNDER_LINE
                                             + equite001Dto.getRegistBankCode();
                            if (allChbEqudetDataMap.containsKey(dataKey)) {
                                excelNumIntList.add(Integer.valueOf(equite001Dto.getExcelNum()));
                            }
                        }
                        // 将list进行排序，并转化为string
                        Collections.sort(excelNumIntList);
                        // 若有产品未维护产品登记编码，则直接返回
                        for (Integer rowNo : excelNumIntList) {
                            excelNumList.add(String.valueOf(rowNo));
                        }
                        if (CollectionUtils.isNotEmpty(excelNumIntList)) {
                            webResult.setMessage(
                                this.getMessage("CHB_ISCOVER_EQU_2",
                                    StringUtil.joinString(excelNumList, GlobalConstant.COMMA)),
                                MessageType.确认消息);
                        }
                        else {
                            // 防止在进行导入前check时进行了两次的弹框
                            webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                        }
                    }
                    else {
                        // 防止在进行导入前check时进行了两次的弹框
                        webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                    }
                }
                else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }
            }
            catch (Exception e) {
                // 防止在进行导入前check时进行了两次的弹框
                logger.error(e.getMessage(), e);
                webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
            }
        
        return webResult;
    }

    /**
     * 导入文件 --解析excel文件并进行验证
     *
     * @param fileName 导入的文件名名
     * @return 导入结果
     */
    @SuppressWarnings("unchecked")
    public List<ExcelValidDto> importData(Map<String,Object> param) {
        String pageId= (String)param.get("pageId");
        String bankCode = (String)param.get("bankCode");
        String deptCode = (String)param.get("deptCode");
        String orgCode = (String)param.get("orgCode");
        String userName = (String) param.get("userName");
        List<List<String>> dataList=(List<List<String>>)param.get("dataList");
        if (null != dataList) {
            	List<EquiteBusiDetails001Dto> entitiesList =new ArrayList<>();
                List<EquiteBusiDetails001Dto> queryList = listToChbDto(dataList);
            // 根据数据规则检查数据是否合法
            List<Map<String, String>> mapList = listToMap(dataList);

            // 检查数据是否合法
            List<ExcelValidDto> checkResult = checkDataInfo(dataList,mapList,queryList, entitiesList,bankCode,deptCode,orgCode,userName);

            if (checkResult.isEmpty()) {
                try {
                    // 判断当前画面的导出进度是否正在处理
                    if (progressBarService.isProcessing(pageId, PROCESS_KEY)) {
                        // throw new BusinessException("当前画面正在执行导入操作，请耐心等待！");
                        checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                                this.getMessage("CHB_EQUITY_REPORT_FILE_0001")));
                        return checkResult;
                    }
                    // 设置当前画面进度为0
                    progressBarService.updatePercent(pageId, PROCESS_KEY, 0D, false);
                    // 查询出所有身份信息
                    List<EquityInfo001Dto> allInfoList = new ArrayList<EquityInfo001Dto>();
//                        template.select(
//                            "com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper.selectEquityInfoAll",
//                            new ResultHandler() {
//                                @Override
//                                public void handleResult(ResultContext resultContext) {
//                                    allInfoList.add((EquityInfo001Dto)resultContext.getResultObject());
//                                }
//                            });
//                        Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
//                            JoyinCollectors.groupingBy(
//                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
//                                     + x.getIndentifyMark()));
                        
                        allInfoList=equityBusiDetails001Mapper.getAllChbEquInfoOneTime();
                        Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
                            JoyinCollectors.groupingBy(
                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
                                     + x.getIndentifyMark()));
                        
                        
                        List<EquiteBusiDetails001Dto> insertDataList = new ArrayList<EquiteBusiDetails001Dto>();
                        // 查询导入文件中的数据在数据库中已经存在的数据
                        final Map<String, EquiteBusiDetails001Dto> allChbEqudetDataMap = this.selectAllChbEqudetData(
                        		queryList);

                    // 更新导入的进度条（以0.3d-0.6d为范围，跟新进度）
                    progressBarService.updatePercent(pageId, PROCESS_KEY, 0.3d, false);
                    // 查询出所有有三要素合并身份信息
                    List<ChbEquHoldCheckDto> allcheckList = equityBusiInfoRegist001Mapper.selectAllCheckEquHoldDto();
                    Map<String, ChbEquHoldCheckDto> equityInfocheckMap = allcheckList.stream().collect(
                            Collectors.toMap(ChbEquHoldCheckDto::getIndentifyMark, a -> a, (k1, k2) -> k1));
                    entitiesList.forEach(item -> {
                        item.setDataStatus(DictCodeListExt1.维护数据状态_有效.getValue());

                        //主键改为产品代码
                        String dataKey = item.getBusiSeqno() + GlobalConstant.UNDER_LINE
                                + item.getAccount() + GlobalConstant.UNDER_LINE
                                + item.getRegistBankCode();
                        EquiteBusiDetails001Dto chbEqudet = allChbEqudetDataMap.get(dataKey);

                        // item.setDataStatus(DictCodeList.维护数据状态_有效.getValue());
                        item.setDataSource(DictCodeListExt2.数据来源_EXCEL导入.getValue());
                        item.setDeptCode(deptCode);
                        //不管是否有变更都需要赋值
                        item.setOrgIndentifyMark(item.getIndentifyMark());
                        setOrgIndentifyMark(item,equityInfocheckMap);
                        // 设值createDept
                        String createDept = item.getDeptCode();
                        if (StringUtil.isEmpty(createDept)) {
                            createDept = item.getOrgCode();
                        }
                        item.setCreateDept(createDept);

                        if (chbEqudet != null) {
//                            	 EquiteBusiDetails001Dto result= equityBusiDetails001Mapper.selectByPrimaryKey(chbEqudet);
//                               //对比数据相同则不继续
//                               if(docheckIfchangeEx(item,result)){
//                                   return;
//                               }
                            // 明细表中存在相同数据且数据状态为待处理
                            List<EquityBusiInfoRegist001Dto> listOA = new ArrayList<>();
                            EquityBusiInfoRegist001Dto dto = new EquityBusiInfoRegist001Dto();
                            BeanUtil.copyPropertiesIgnoreNull(dto, item);
                            listOA.add(dto);
                            EquiteBusiDetails001Dto result= equityBusiDetails001Mapper.selectByPrimaryKey(item);
                            //批量插入投资者明细HIS数据
                            insertEquityBusiHisByPart(listOA);
                            // 更新维护表数据
                            equityBusiDetails001Mapper.updateByPrimaryKeySelective(item);
                            // 更新登记表数据
                            doUpdateSub(item,result);
                        }
                        else {
                            insertDataList.add(item);
                        }
                    });
                    this.insertChbEqudetDataByPart(insertDataList);
                    // 插入登记表数据
                    doInsertSubDataWithInfoDto(insertDataList,equityInfo001Map);
                    // 更新导入的进度条
                    progressBarService.updatePercent(pageId, PROCESS_KEY, 0.6d, false);
                    // 删除导入用的临时的数据
                    deleteImportTmpData();
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    // 从内存中移除当前画面的导出进度
                    progressBarService.removePercent(pageId, PROCESS_KEY);
                    logger.error(e.getMessage(),e);
                }
            }
            // 从内存中移除当前画面的导出进度
            progressBarService.removePercent(pageId, PROCESS_KEY);
            BaseCheckLib.objFiledsMap=null;
            return checkResult;
        }

        return null;
    }

    /**
     * 将excel导入的数据处理为list统一验证 <功能详细描述>
     *
     * @param dataList 数据源
     * @return 转换结果
     */
    public List<Map<String, String>> listToMap(List<List<String>> dataList) {
        String[] fieldName = {"registBankCode", "contractNo", "busiSeqno", "finAcc",
                "custContractNo", "indentifyMark", "custName", "fundAcc", "fundAccPlace", "isConsign",
                "agencyCode", "agencyName", "agencyFsa", "account", "busiType", "busiFsa",
                "equityConfDate", "shareConfDatetime", "ccy", "amount", "amountCny", "prodValue",
                "shares", "feeAmt", "channel", "tranTellName", "remark"};
        Map<String, String> dictMap=new HashMap<String,String>(){
            {
                put("fundAccPlace", "");
                put("isConsign", "");
                put("agencyFsa", "");
                put("busiType", "");
                put("busiFsa", "");
                put("ccy", "");
                put("channel", "");
            }
        };
        List<Map<String, String>> result = new ArrayList<>();
        // 获取所有的产品登记编码
        List<Map<String, String>> regList = equityBusiDetails001Mapper.queryAllRegCode();
        Map<String, String> allProdIdMap = regList.stream().collect(
                JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("OPERATE_MODE")));

        dataList.forEach(action -> {
            Map<String, String> temp = new HashMap<>();
            for (int i = 0; i < fieldName.length; i++ ) {
                // modify by NullPoint yangxianjiu 20220413 start
                String cell = action.get(i) == null ? null : action.get(i).trim();
                String name=fieldName[i];
                if(dictMap.containsKey(name)){
                    //是枚举值
                    if (cell != null && cell.contains(StringUtil.SPACE)) {
                        // modify by NullPoint yangxianjiu 20220413 end
                        String[] value = StringUtil.split(cell, StringUtil.SPACE);
                        cell = value[0];
                    }
                }
                temp.put(fieldName[i], cell);
                if (fieldName[i].equals("account")) {
                    temp.put("operateMode", allProdIdMap.get(cell));
                }
            }
            /** modify by yanya on 20210525 增加是否代销导入模板判定**/
            temp.put("dxTplFlag", DictCodeList.是否_否.getValue());
            result.add(temp);
        });
        return result;
    }

    public List<Map<String, String>> listToMapEx(List<List<String>> dataList) {
        String[] fieldName = {"REGIST_BANK_CODE", "CONTRACT_NO", "BUSI_SEQNO", "FIN_ACC",
                "CUST_CONTRACT_NO", "INDENTIFY_MARK", "CUST_NAME", "FUND_ACC", "FUND_ACC_PLACE", "IS_CONSIGN",
                "AGENCY_CODE", "AGENCY_NAME", "AGENCY_FSA", "ACCOUNT", "BUSI_TYPE", "BUSI_FSA",
                "EQUITY_CONF_DATE", "SHARE_CONF_DATETIME", "CCY", "AMOUNT", "AMOUNT_CNY", "PROD_VALUE",
                "SHARES", "FEE_AMT", "CHANNEL", "TRAN_TELL_NAME", "REMARK"};
        Map<String, String> dictMap=new HashMap<String,String>(){
            {
                put("fundAccPlace", "");
                put("isConsign", "");
                put("agencyFsa", "");
                put("busiType", "");
                put("busiFsa", "");
                put("ccy", "");
                put("channel", "");
            }
        };
        List<Map<String, String>> result = new ArrayList<>();
        dataList.forEach(action -> {
            Map<String, String> temp = new HashMap<>();
            for (int i = 0; i < fieldName.length; i++ ) {
                String cell = action.get(i) == null ? null : action.get(i).trim();
                String name = fieldName[i];
                if (dictMap.containsKey(name)) {
                    //是枚举值
                    if (cell!=null && cell.contains(StringUtil.SPACE)) {
                        String[] value = StringUtil.split(cell, StringUtil.SPACE);
                        cell = value[0];
                    }
                }
                temp.put(fieldName[i], cell);
            }
            /** modify by yanya on 20210525 增加是否代销导入模板判定**/
            temp.put("dxTplFlag", DictCodeList.是否_否.getValue());
            result.add(temp);
        });
        return result;
    }

    /**
     * 导入代销文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
     *
     * @param fileName 导入的文件名名
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForChbImportData(Map<String,Object> param) {
        String fileName = (String)param.get("fileName");
        String bankCode = (String)param.get("bankCode");
        String deptCode = (String)param.get("deptCode");
        String orgCode = (String)param.get("orgCode");
        String userName = (String) param.get("userName");
        AjaxResult webResult = new AjaxResult();
        if (StringUtil.isNotEmpty(fileName)) {
            try {
                List<List<String>> dataList = ExcelUtil.readToList(
                     FileNameUtil.getUploadFilePath(fileName) , null, null, 0, 1, 0);
                if (CollectionUtils.isNotEmpty(dataList)) {
                	
                	List<EquiteBusiDetails001Dto>  entitiesList=  new ArrayList<>();
                    List<EquiteBusiDetails001Dto>  queryList=  listToChbDto(dataList);
 
                    List<Map<String, String>> mapList = listToMapChb(dataList);

                    List<Map<String, String>> equityList = new ArrayList<>();
                    List<Map<String, String>> mapListEx = listToMapChbEx(dataList);
                    equityList.addAll(mapListEx);
                    // 删除导入用的临时的数据
                    deleteImportTmpData();
                    // 插入导入用的临时数据
                    equityBusiDetails001Mapper.insertIntoImportTable(equityList);
                    
                    List<ExcelValidDto> checkResult = checkDataInfo(null,mapList, queryList,entitiesList,bankCode,deptCode,orgCode,userName);
                    if (checkResult.size() > 0) {
                        webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                        return webResult;
                    }

                    // 查询导入文件中的数据在数据库中已经存在的数据
                    Map<String, EquiteBusiDetails001Dto> allChbEqudetDataMap = this.selectAllChbEqudetData(
                    		queryList);
                    if (allChbEqudetDataMap != null && allChbEqudetDataMap.size() > 0) {
                        // 将序号放入list中
                        List<String> excelNumList = new ArrayList<String>();
                        List<Integer> excelNumIntList = new ArrayList<Integer>();
                        for (EquiteBusiDetails001Dto equite001Dto : entitiesList) {
                            String dataKey = equite001Dto.getBusiSeqno()
                                    + GlobalConstant.UNDER_LINE
                                    + equite001Dto.getAccount()
                                    + GlobalConstant.UNDER_LINE
                                    + equite001Dto.getRegistBankCode();
                            if (allChbEqudetDataMap.containsKey(dataKey)) {
                                excelNumIntList.add(Integer.valueOf(equite001Dto.getExcelNum()));
                            }
                        }
                        // 将list进行排序，并转化为string
                        Collections.sort(excelNumIntList);
                        // 若有产品未维护产品登记编码，则直接返回
                        for (Integer rowNo : excelNumIntList) {
                            excelNumList.add(String.valueOf(rowNo));
                        }
                        if (CollectionUtils.isNotEmpty(excelNumIntList)) {
                            webResult.setMessage(
                                    this.getMessage("CHB_ISCOVER_EQU",
                                            StringUtil.joinString(excelNumList, GlobalConstant.COMMA)),
                                    MessageType.确认消息);
                        }
                    }
                    else {
                        // 防止在进行导入前check时进行了两次的弹框
                        webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                    }
                }
                else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }
            }
            catch (Exception e) {
                // 防止在进行导入前check时进行了两次的弹框
                webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
            }
        }
        return webResult;
    }

    /**
     * 导入代销数据文件
     * <p>
     * 解析excel文件并进行验证
     * </p>
     *
     * @param fileName 导入的文件名名
     * @return 导入结果
     */
    @SuppressWarnings("unchecked")
    public List<ExcelValidDto> importChbData(Map<String,Object> param) {
        String pageId= (String)param.get("pageId");
        String bankCode = (String)param.get("bankCode");
        String deptCode = (String)param.get("deptCode");
        String orgCode = (String)param.get("orgCode");
        String userName = (String) param.get("userName");
        List<List<String>> dataList=(List<List<String>>)param.get("dataList");
        if (null != dataList) {
                List<EquiteBusiDetails001Dto> queryList =listToChbDto(dataList);
                List<EquiteBusiDetails001Dto> entitiesList  = new ArrayList<>();
                // 根据数据规则检查数据是否合法
                List<Map<String, String>> mapList = listToMapChb(dataList);

                // 检查数据是否合法
                List<ExcelValidDto> checkResult = checkDataInfo(dataList,mapList, queryList,entitiesList,bankCode,deptCode,orgCode,userName);
                // List<ExcelValidDto> checkResult = convertToEntity(dataList, entitiesList,
                // mapList);

            if (checkResult.isEmpty()) {
                try {
                    // 判断当前画面的导出进度是否正在处理
                    if (progressBarService.isProcessing(pageId, PROCESS_KEY)) {
                        // throw new BusinessException("当前画面正在执行导入操作，请耐心等待！");
                        checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                                this.getMessage("CHB_EQUITY_REPORT_FILE_0001")));
                        return checkResult;
                    }
                    // 设置当前画面进度为0
                    progressBarService.updatePercent(pageId, PROCESS_KEY, 0D, false);
                    // 查询出所有身份信息
                    List<EquityInfo001Dto> allInfoList = new ArrayList<EquityInfo001Dto>();
//                        template.select(
//                            "com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper.selectEquityInfoAll",
//                            new ResultHandler() {
//                                @Override
//                                public void handleResult(ResultContext resultContext) {
//                                    allInfoList.add((EquityInfo001Dto)resultContext.getResultObject());
//                                }
//                            });
//                        Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
//                            JoyinCollectors.groupingBy(
//                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
//                                     + x.getIndentifyMark()));

                    allInfoList=equityBusiDetails001Mapper.getAllChbEquInfoOneTime();
                    Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
                            JoyinCollectors.groupingBy(
                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
                                     + x.getIndentifyMark()));
                        List<EquiteBusiDetails001Dto> insertDataList = new ArrayList<EquiteBusiDetails001Dto>();
                        // 查询导入文件中的数据在数据库中已经存在的数据
                        final Map<String, EquiteBusiDetails001Dto> allChbEqudetDataMap = this.selectAllChbEqudetData(
                        		queryList);

                    // 更新导入的进度条（以0.3d-0.6d为范围，跟新进度）
                    progressBarService.updatePercent(pageId, PROCESS_KEY, 0.3d, false);
                    // 查询出所有有三要素合并身份信息
                    List<ChbEquHoldCheckDto> allcheckList = equityBusiInfoRegist001Mapper.selectAllCheckEquHoldDto();
                    Map<String, ChbEquHoldCheckDto> equityInfocheckMap = allcheckList.stream().collect(
                            Collectors.toMap(ChbEquHoldCheckDto::getIndentifyMark, a -> a, (k1, k2) -> k1));
                    entitiesList.forEach(item -> {
                        item.setDataStatus(DictCodeListExt1.维护数据状态_有效.getValue());

                        //主键修改为产品代码
                        String dataKey = item.getBusiSeqno() + GlobalConstant.UNDER_LINE
                                + item.getAccount() + GlobalConstant.UNDER_LINE
                                + item.getRegistBankCode();
                        EquiteBusiDetails001Dto chbEqudet = allChbEqudetDataMap.get(dataKey);

                        item.setDataStatus(DictCodeListExt1.维护数据状态_有效.getValue());
                        item.setDataSource(DictCodeListExt2.数据来源_EXCEL导入.getValue());
                        item.setDeptCode(deptCode);
                        item.setOrgIndentifyMark(item.getIndentifyMark());
                        setOrgIndentifyMark(item,equityInfocheckMap);
                        // 设值createDept
                        String createDept = item.getDeptCode();
                        if (StringUtil.isEmpty(createDept)) {
                            createDept = item.getOrgCode();
                        }
                        item.setCreateDept(createDept);

                        if (chbEqudet != null) {
                            // 明细表中存在相同数据且数据状态为待处理
                            List<EquityBusiInfoRegist001Dto> listOA = new ArrayList<>();
                            EquityBusiInfoRegist001Dto dto = new EquityBusiInfoRegist001Dto();
                            BeanUtil.copyPropertiesIgnoreNull(dto, item);
                            listOA.add(dto);
                            EquiteBusiDetails001Dto result= equityBusiDetails001Mapper.selectByPrimaryKey(item);
                            insertEquityBusiHisByPart(listOA);
                            equityBusiDetails001Mapper.updateByPrimaryKeySelective(item);
                            // 更新登记表数据
                            doUpdateSub(item,result);
                        }
                        else {
                            insertDataList.add(item);
                        }
                    });
                    this.insertChbEqudetDataByPart(insertDataList);
                    // 插入登记表数据
                    doInsertSubDataWithInfoDto(insertDataList,equityInfo001Map);

                    // 更新导入的进度条
                    progressBarService.updatePercent(pageId, PROCESS_KEY, 0.6d, false);

                    // 删除导入用的临时的数据
                    deleteImportTmpData();
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    // 从内存中移除当前画面的导出进度
                    progressBarService.removePercent(pageId, PROCESS_KEY);
                }
            }
            // 从内存中移除当前画面的导出进度
            progressBarService.removePercent(pageId, PROCESS_KEY);
            BaseCheckLib.objFiledsMap=null;
                checkResult = checkResult.stream().sorted((a,b)->{
                    if(Integer.valueOf(a.getRowNum()) > Integer.valueOf(b.getRowNum())){
                        return 1;
                    }else{
                        return -1;
                    }
                }).collect(Collectors.toList());
                return checkResult;
            }
        
        return null;
    }

    /**
     * 将excel导入的数据处理为list统一验证 <功能详细描述>
     *
     * @param dataList 数据源
     * @return 转换结果
     */
    public List<Map<String, String>> listToMapChb(List<List<String>> dataList) {
        String[] fieldName = {"registBankCode", "contractNo", "busiSeqno", "finAcc",
                "custContractNo", "indentifyMark", "custName", "fundAcc", "fundAccPlace", "isConsign",
                "agencyCode", "agencyName", "agencyFsa", "regCode", "busiType", "busiFsa",
                "equityConfDate", "shareConfDatetime", "ccy", "amount", "amountCny", "prodValue",
                "shares", "feeAmt", "channel", "tranTellName", "remark"};
        Map<String, String> dictMap=new HashMap<String,String>(){
            {
                put("fundAccPlace", "");
                put("isConsign", "");
                put("agencyFsa", "");
                put("busiType", "");
                put("busiFsa", "");
                put("ccy", "");
                put("channel", "");
            }
        };
        List<Map<String, String>> result = new ArrayList<>();

        List<Map<String, String>> regList = equityBusiDetails001Mapper.queryAllRegCode();
        // 获取所有的产品登记编码
        Map<String, String> allRegCodeMap = regList.stream().collect(
                JoyinCollectors.toMap(x -> x.get("REG_CODE"), x -> x.get("OPERATE_MODE")));

        dataList.forEach(action -> {
            Map<String, String> temp = new HashMap<>();
            for (int i = 0; i < fieldName.length; i++ ) {
                    String cell = action.get(i) == null ? null : action.get(i).trim();
                    String name=fieldName[i];
                    if(dictMap.containsKey(name)){
                        //是枚举值
                        if (cell != null && cell.contains(StringUtil.SPACE)) {
                            String[] value = StringUtil.split(cell, StringUtil.SPACE);
                            cell = value[0];
                        }
                    }
                    temp.put(fieldName[i], cell);
                    if (fieldName[i].equals("regCode")) {
                        temp.put("operateMode", allRegCodeMap.get(cell));
                    }
            }
            /** modify by yanya on 20210525 增加是否代销导入模板判定**/
            temp.put("dxTplFlag", DictCodeList.是否_是.getValue());
            result.add(temp);
        });
        return result;
    }
	
	  public List<EquiteBusiDetails001Dto> listToChbDto(List<List<String>> dataList) {
        String[] fieldName = {"registBankCode",  "busiSeqno","account"};
         
        List<EquiteBusiDetails001Dto> result = new ArrayList<>();

        dataList.forEach(action -> {
        	EquiteBusiDetails001Dto  temp = new EquiteBusiDetails001Dto();
            for (int i = 0; i < fieldName.length; i++ ) {
                String registBankCode = action.get(0).trim();
                temp.setRegistBankCode(registBankCode);
                String busiSeqno=action.get(2).trim();
                temp.setBusiSeqno(busiSeqno);
                String account=action.get(13).trim();
                temp.setAccount(account);
            }
            result.add(temp);
        });
        return result;
    }


    public List<Map<String, String>> listToMapChbEx(List<List<String>> dataList) {
        String[] fieldName = {"REGIST_BANK_CODE", "CONTRACT_NO", "BUSI_SEQNO", "FIN_ACC",
                "CUST_CONTRACT_NO", "INDENTIFY_MARK", "CUST_NAME", "FUND_ACC", "FUND_ACC_PLACE", "IS_CONSIGN",
                "AGENCY_CODE", "AGENCY_NAME", "AGENCY_FSA", "REG_CODE", "BUSI_TYPE", "BUSI_FSA",
                "EQUITY_CONF_DATE", "SHARE_CONF_DATETIME", "CCY", "AMOUNT", "AMOUNT_CNY", "PROD_VALUE",
                "SHARES", "FEE_AMT", "CHANNEL", "TRAN_TELL_NAME", "REMARK"};
        Map<String, String> dictMap=new HashMap<String,String>(){
            {
                put("fundAccPlace", "");
                put("isConsign", "");
                put("agencyFsa", "");
                put("busiType", "");
                put("busiFsa", "");
                put("ccy", "");
                put("channel", "");
            }
        };
        List<Map<String, String>> result = new ArrayList<>();
        dataList.forEach(action -> {
            Map<String, String> temp = new HashMap<>();
            for (int i = 0; i < fieldName.length; i++ ) {
                String cell = action.get(i) == null ? null : action.get(i).trim();
                String name=fieldName[i];
                if(dictMap.containsKey(name)){
                    //是枚举值
                    if (cell!= null && cell.contains(StringUtil.SPACE)) {
                        String[] value = StringUtil.split(cell, StringUtil.SPACE);
                        cell = value[0];
                    }
                }
                temp.put(fieldName[i], cell);
            }
            /** modify by yanya on 20210525 增加是否代销导入模板判定**/
            temp.put("dxTplFlag", DictCodeList.是否_是.getValue());
            result.add(temp);
        });
        return result;
    }

    public List<ExcelValidDto> checkDataInfo(List<List<String>>dataList, List<Map<String, String>> mapList,
            List<EquiteBusiDetails001Dto> queryList,   List<EquiteBusiDetails001Dto> resultList,
            String bankCode,String deptCode,String orgCode,String userName) {

        logger.info("checkDataInfo处理开始 " + DateUtil.getCurrentDateTime());

        List<ExcelValidDto> result = new ArrayList<>();

        if (dataList!=null && CollectionUtils.isNotEmpty(dataList)) {
            for(int i = 0; i < dataList.size(); i++ ) {
                List<String> item =dataList.get(i);
                if (CollectionUtils.isNotEmpty(item) && item.size()>26 && item.get(24)!=null) {
                    if(!item.get(24).equals(item.get(24).toUpperCase())){
                        result.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i + 2),
                                this.getMessage("CHB_EQUITY_CHANNEL")+" "+"[channel="+item.get(24)+"]"));
                    }
                }
            }
        }
        if (mapList.isEmpty()) {
            result.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                    this.getMessage("REPORT_COMMON_FILE_E_0016")));
        }
        else {
            // 1、根据check规则检查
            // 主键字段列表
            List<String> keyItemNameList = new ArrayList<>();
            keyItemNameList.add("busiSeqno");
            List<ExcelValidDto> rulecheckres = IBDDataCheckUtil.checkDataList(mapList,
                    ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS, keyItemNameList);
            result.addAll(rulecheckres);

            // 2、循环判断
            for (int i = 0; i < mapList.size(); i++ ) {
                Map<String, String> checkMapI = mapList.get(i);
//                if (checkMapI.containsKey("account")) {
//                    checkMapI.put("regCode", checkMapI.get("account"));
//                }
                //主键修改为产品代码
                if (checkMapI.containsKey("regCode")) {
                    checkMapI.put("account", checkMapI.get("regCode"));
                }
                if (StringUtil.isEmpty(checkMapI.get("registBankCode"))
                        || StringUtil.isEmpty(checkMapI.get("busiSeqno"))
                        || StringUtil.isEmpty(checkMapI.get("account"))) {
                    continue;
                }
                // 判断导入的数据是否存在重复的主键
                for (int j = i + 1; j < mapList.size(); j++ ) {
                    Map<String, String> checkMapJ = mapList.get(j);
//                    if (checkMapJ.containsKey("account")) {
//                        checkMapJ.put("regCode", checkMapJ.get("account"));
//                    }
                    //主键修改为产品代码
                    if (checkMapJ.containsKey("regCode")) {
                        checkMapJ.put("account", checkMapJ.get("regCode"));
                    }
                    if (checkMapI.get("registBankCode").equals(checkMapJ.get("registBankCode"))
                            && checkMapI.get("busiSeqno").equals(checkMapJ.get("busiSeqno"))
                            && checkMapI.get("account").equals(checkMapJ.get("account"))) {
                        result.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(j + 2),
                                this.getMessage("REPORT_COMMON_FILE_E_0027",
                                        String.valueOf(i + 2) + "," + String.valueOf(j + 2))));
                        //updateBy zhangjinyang 数据重复后继续判断 start
                        //return result;
                        //updateBy zhangjinyang 数据重复后继续判断 end
                    }
                }
            }
            
          //判断导入数据是否存在已导入的数据
            //查询已经在数据库中存在的数据记录
            List<EquiteBusiDetails001Dto> listExistDto = equityBusiDetails001Mapper.selectTMPExistInInfo(
            		queryList);           
            Map<String, EquiteBusiDetails001Dto> exitsChbEqudetDataMap = StringUtil.isEmptyList(
            		listExistDto) ? new HashMap<String, EquiteBusiDetails001Dto>() : listExistDto.stream().collect(
                    JoyinCollectors.toMap(
                            x -> x.getBusiSeqno() + GlobalConstant.UNDER_LINE + x.getRegistBankCode(),
                            x -> x));
            
            for (int i = 0; i < mapList.size(); i++ ) {
            	String compreKey = mapList.get(i).get("busiSeqno") + GlobalConstant.UNDER_LINE + mapList.get(i).get("registBankCode");
            	if (exitsChbEqudetDataMap.get(compreKey) != null) {
                    result.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i+1),
                            this.getMessage("REPORT_COMMON_FILE_E_0028",
                            		mapList.get(i).get("busiSeqno"))));                    
                    return result;
                }
            }
            
            

            //获取所有的身份信息
            Map<String, String> indentifyMap = getAllIndentifyInfo();
            // 汇率mapper
            Map<String, BigDecimal> rateMap = new HashMap<>();
//            String bankCode = CommonUtil.getUserBankCode();
            // Excel中的行
            int rowId = 1;
            // 获取所有的产品登记编码
            Map<String, String> allRegCodeMap = new HashMap<String, String>();
            List<Map<String, String>> regList = equityBusiDetails001Mapper.queryAllRegCode();
            allRegCodeMap = regList.stream().collect(
                    JoyinCollectors.toMap(x -> x.get("REG_CODE"), x -> x.get("PRODID")));
            // 获取所有的产品代码
            Map<String, String> allProdIdMap = new HashMap<String, String>();
            allProdIdMap = regList.stream().collect(
                    JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("REG_CODE")));

            /**
             * 查询导入文件中的数据在数据库中已经存在的数据
             * 存在重复数据，更新时"业务确认日"若跨区间则不能更新
             */
            Map<String, EquiteBusiDetails001Dto> allChbEqudetDataMap = this.selectAllChbEqudetData(
            		queryList);
             
            for (Map<String, String> item : mapList) {
                // 验证消息
                List<String> message = new ArrayList<>();
                EquiteBusiDetails001Dto entity = new EquiteBusiDetails001Dto();
                for(String key:item.keySet()){
                    if(key.charAt(0)>= 'A' && key.charAt(0) <= 'Z'){
                        if(entity.getTempMap()!=null){
                            entity.getTempMap().put( CommonUtil.dealString(key,true), item.get(key));
                        }else{
                            Map<String, String> tempMap=new HashMap<String, String>();
                            tempMap.put( CommonUtil.dealString(key,true), item.get(key));
                            entity.setTempMap(tempMap);
                        }
                    }
                }
                String row = String.valueOf(rowId + 1);
                // 增加业务确认日期格式校验 by caozhenming 20210922 start
                boolean equityConfDateFlag = false;
                String equityConfDate =  item.get("equityConfDate");
                Pattern pattern = Pattern.compile(DATE_FORMAT_YYYYMMDD);
                Matcher matcher = pattern.matcher(equityConfDate);
                if (matcher.matches()) {
                    equityConfDateFlag = true;
                } else {
                    message.add(this.getMessage("CHB_EQUITY_EQUITYCONFDATE_CHECK"));
                }
                // 增加业务确认日期格式校验 by caozhenming 20210922 end


                // 判断已存在数据中 "业务确认日"是否跨区间，是则不能更新
                if (equityConfDateFlag && allChbEqudetDataMap != null && allChbEqudetDataMap.size() > 0) {
                    String account =item.get("account");
                    if (item.containsKey("regCode")) {
                        account =allRegCodeMap.get(item.get("regCode"));
                    }
                    String dataKey = item.get("busiSeqno") + GlobalConstant.UNDER_LINE
                            + account + GlobalConstant.UNDER_LINE
                            + item.get("registBankCode");
                	if (allChbEqudetDataMap.containsKey(dataKey)) {
                		EquiteBusiDetails001Dto chbEqudet = allChbEqudetDataMap.get(dataKey);
                		String dateConfNew =DateUtil.formatDateAddSplit(item.get("equityConfDate"));
                		String equityConfDateOld = DateUtil.format(chbEqudet.getEquityConfDate()).substring(0, 7);
                		if(dateConfNew!=null && dateConfNew.length()>=7){
                		String equityConDateNew = dateConfNew.substring(0, 7);
                		
                		Date sysDate =  CacheUtil.getAppCache(GlobalConstant.NOW_SYS_DATE, Date.class);
                			Date confDate =DateUtil.parse(dateConfNew);
                			if(sysDate !=null && sysDate.compareTo(confDate)<0){
                				message.add(this.getMessage("CHB_EQUITY_EQUITYCONFDATE_002"));
                			}
                    	if(!StringUtil.equals(equityConDateNew, equityConfDateOld)){
                    		message.add(this.getMessage("CHB_EQUITY_EQUITYCONFDATE"));
                    	}
                     }
                	}
                }

                // 0 登记银行代码（即发行机构代码）
                String registBankCode = item.get("registBankCode");
                // excel中的登记银行代码必须与当前导入人的登记银行代码一致!
                if (!StringUtil.equals(bankCode, registBankCode)) {
                    message.add(this.getMessage("REPORT_COMMON_I_0019"));
                }
                else {
                    entity.setRegistBankCode(registBankCode);
                    entity.setOrgCode(orgCode);// 机构代码
                }

                // 1 销售合同号
                entity.setContractNo(item.get("contractNo"));

                // 2 业务流水号（核心交易流水号）
                entity.setBusiSeqno(item.get("busiSeqno"));

                // 3 理财账号
                entity.setFinAcc(item.get("finAcc"));

                // 4 客户统一编号
                entity.setCustContractNo(item.get("custContractNo"));

                // 5 识别标识
                String indentifyMark = item.get("indentifyMark");
                String merge = registBankCode + GlobalConstant.VERTICAL_LINE + indentifyMark;
                if(StringUtil.isNotEmpty(indentifyMark) && StringUtil.isNotEmpty(registBankCode)
                        && !indentifyMap.containsKey(merge)){
                    message.add(this.getMessage("CHB_EQUITY_INDENTIFYMARK"));
                }
                entity.setIndentifyMark(indentifyMark);
                // entity.setOrgIndentifyMark(item.get("indentifyMark"));

                // 6 客户姓名
                entity.setCustName(item.get("custName"));

                // 7 关联活期存款账号
                entity.setFundAcc(item.get("fundAcc"));

                // 8 关联账号开户所在地
                entity.setFundAccPlace(item.get("fundAccPlace"));

                // 9 是否代销
                String isConsign = item.get("isConsign");
                entity.setIsConsign(isConsign);

                // 10 代销机构代码
                String agencyCode = item.get("agencyCode");
                entity.setAgencyCode(agencyCode);

                // 11 代销机构名称
                String agencyName = item.get("agencyName");
                entity.setAgencyName(agencyName);

                // 12 代销机构所属监管机构
                String agencyFsa = item.get("agencyFsa");
                entity.setAgencyFsa(agencyFsa);

                if (item.containsKey("regCode")) {
                    // 13 产品登记编码
                    String regCode = item.get("regCode");
                    // 必填判断
                    String isNotEmpty = IBDDataCheckUtil.checkOneItem(regCode,
                            ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS, "regCode",
                            IBDDataCheckUtil.COMMON_CHECK_METHOD_ISNOTEMPTY);
                    String lengths = IBDDataCheckUtil.checkOneItem(regCode,
                            ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS, "regCode",
                            IBDDataCheckUtil.COMMON_CHECK_METHOD_FIXEDLENGTH);
                    String inWhitList = IBDDataCheckUtil.checkOneItem(regCode,
                            ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS, "regCode",
                            IBDDataCheckUtil.COMMON_CHECK_METHOD_INWHITELISTALL);
                    if (isNotEmpty != null && isNotEmpty.length() > 0) {
                        message.add(isNotEmpty);
                    }
                    else if (lengths != null && lengths.length() > 0) {
                        message.add(lengths);
                    }
                    else if (inWhitList != null && inWhitList.length() > 0) {
                        message.add(inWhitList);
                    }
                    else if (!allRegCodeMap.containsKey(regCode)) {
                        message.add(this.getMessage("CHB_EQUITY_CHECKREGCODE"));
                    }
                    else if (!regCode.startsWith(registBankCode)) {
                        message.add(this.getMessage("CHB_EQUITY_CHECKREGCODEMATCH"));
                    }
                    else {
                        entity.setRegCode(regCode);
                        entity.setAccount(allRegCodeMap.get(regCode));// 产品代码
                    }
                }
                else {
                    // 产品代码
                    String account = item.get("account");
                    if (!allProdIdMap.containsKey(account)) {
                        message.add(this.getMessage("CHB_EQUITY_CHECKACCOUNT"));
                    }
                    else if (StringUtil.isNotEmpty(allProdIdMap) && StringUtil.isNotEmpty(allProdIdMap.get(account)) && !allProdIdMap.get(account).startsWith(registBankCode)) {
                        message.add(this.getMessage("CHB_EQUITY_CHECKREGCODEMATCH"));
                    }
                    else {
                        entity.setRegCode(allProdIdMap.get(account));// 产品登记编码
                        entity.setAccount(account);// 产品代码
                    }
                }

                // 14 业务种类
                entity.setBusiType(item.get("busiType"));

                // 15 业务发生地所属监管
                entity.setBusiFsa(item.get("busiFsa"));

                try {
                    // 16 业务确认日期
                    if (equityConfDateFlag) {
                        entity.setEquityConfDate(DateUtil.parse(item.get("equityConfDate")));
                    }
                }
                catch (Exception e) {
                    // TODO: handle exception
                }

                // 17 业务确认时间
                entity.setShareConfDatetime(item.get("shareConfDatetime"));

                // 18 币种
                String ccy = item.get("ccy");
                entity.setCcy(ccy);

                // 19 金额
                try {
                    entity.setAmount(new BigDecimal(item.get("amount")));
                }
                catch (Exception e) {
                    // TODO: handle exception
                }

                // 20 折算人民币金额
                try {
                    entity.setAmountCny(new BigDecimal(item.get("amountCny")));
                }
                catch (Exception e) {
                    /**add BY leipeng 20200910  折算人民币金额不能为空 begin **/
                    if(StringUtil.isEmpty(item.get("amountCny"))){
                        message.add(this.getMessage("REPORT_COMMON_FILE_E_0021","折算人民币"));
                    }
                    /**add BY leipeng 20200910  折算人民币金额不能为空 end**/
                }

                // 折算人民币处理
                String amountCny = item.get("amountCny");
                if (StringUtil.isNotEmpty(ccy) && StringUtil.isNotEmpty(entity.getEquityConfDate())
                        && StringUtil.isNotEmpty(entity.getAmount())) {
                    if (!StringUtil.isNotEmpty(amountCny)
                            && !StringUtil.equals(ccy, DictCodeListExt1.币种_中债_人民币.getValue())) {
                        String key = DateUtil.format(entity.getEquityConfDate()) + ccy;
                        if (rateMap.containsKey(key)) {
                            entity.setAmountCny(ArithmeticUtil.multiply(rateMap.get(key),
                                    entity.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
                        }
                        else {
                            Map<String, Object> mapObj = new HashMap<String, Object>();
                            // 原币种
                            mapObj.put("CCY_ORIGINAL", entity.getCcy());
                            // 持有日期
                            mapObj.put("HOLDDATE", entity.getEquityConfDate());
                            // 折算币种
                            mapObj.put("CCY_CONVERT", DictCodeListExt1.币种_中债_人民币.getValue());
                            // 汇率计算规则
                            mapObj.put("pricetype",SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE));
                            BigDecimal rate = exchangerate001Mapper.selectNewRate(mapObj);
                            if (rate != null) {
                                entity.setAmountCny(
                                        ArithmeticUtil.multiply(rate, entity.getAmount()).setScale(2,
                                                BigDecimal.ROUND_HALF_UP));
                                rateMap.put(key, rate);
                            }
                            else {
                                message.add(this.getMessage("CHB_EQUITY_RATE"));
                            }
                        }
                    }
                    else if (!StringUtil.isNotEmpty(amountCny)
                            && StringUtil.equals(ccy, DictCodeListExt1.币种_中债_人民币.getValue())) {
                        entity.setAmountCny(entity.getAmount());
                    }
                }

                // 21 确认净值-空判断
                try {
                    entity.setProdValue(new BigDecimal(item.get("prodValue")));
                }
                catch (Exception e) {
                    // TODO: handle exception
                }

                // 22 份额
                try {
                    entity.setShares(new BigDecimal(item.get("shares")));
                }
                catch (Exception e) {
                    // TODO: handle exception
                }

                // 23 费用
                try {
                    entity.setFeeAmt(new BigDecimal(item.get("feeAmt")));
                }
                catch (Exception e) {
                    // TODO: handle exception
                }

                // 24 渠道
                entity.setChannel(item.get("channel"));

                // 25 交易柜员号
                entity.setTranTellName(item.get("tranTellName"));

                // 26 备注
                entity.setRemark(item.get("remark"));

                //String userName=LoginUserUtil.getUserName();
                //String deptCode=LoginUserUtil.getDeptCode();
                //entity.setCreateUser(SecurityUtil.getLoginUser().getUserName());
                //entity.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
                entity.setCreateUser(userName);
                entity.setUpdateUser(userName);
                entity.setCreateTime(DateUtil.now());
                entity.setUpdateTime(DateUtil.now());
                entity.setDeptCode(deptCode);// 部门代码
                String createDept = deptCode != null ? deptCode : orgCode;
                entity.setCreateDept(createDept);// 创建部门
                entity.setExcelNum(row);// excel行号

                // 判断为空check校验通过
                if (CollectionUtils.isNotEmpty(message)) {
                  result.add(new ExcelValidDto(entity.getBusiSeqno(), String.valueOf(rowId + 1),
                  StringUtil.joinString(message, "<br>")));
                } else {
                	if(resultList!=null){
                	resultList.add(entity);
                	}
                }
                rowId++ ;
            }
        }
        logger.info("checkDataInfo处理结束 " + DateUtil.getCurrentDateTime());

        //add by dengyu 根据行号合并错误信息；begin
        HashSet<String>rowNumSet =new HashSet<>();
        List<ExcelValidDto> resultAll = new ArrayList<>();
        
        for (ExcelValidDto excelValidDto : result) {
        	if(excelValidDto.getRowNum()!=null){//防止报错
        		if(rowNumSet.contains(excelValidDto.getRowNum())){
        			for (ExcelValidDto expected : resultAll) {
						if(expected.getRowNum().equals(excelValidDto.getRowNum())){
							expected.setErrorInfo(expected.getErrorInfo()+"<br> "+excelValidDto.getErrorInfo());
						}
					}
        		}else{
        			rowNumSet.add(excelValidDto.getRowNum());
        			resultAll.add(excelValidDto);
            	}
        	}
		}
        // add by dengyu 根据行号合并错误信息； end 
        return resultAll;
    }

    /**
     * 查询导入文件中的数据在数据库中已经存在的数据
     *
     * @param keyList 主键list
     * @return 数据库中已经存在的数据
     */
    private Map<String, EquiteBusiDetails001Dto> selectAllChbEqudetData(List<EquiteBusiDetails001Dto> keyList) {
        // 查询导入文件中的数据在数据库中已经存在的数据
        List<EquiteBusiDetails001Dto> allChbEqudetDataList = new ArrayList<EquiteBusiDetails001Dto>();

        int maxDataCountOneTime = 1000;
        int dataCount = keyList.size();
        if (dataCount <= maxDataCountOneTime) {
            allChbEqudetDataList = equityBusiDetails001Mapper.selectAllChbEqudetData(keyList);
        }
        else {
            List<EquiteBusiDetails001Dto> newKeyList = new ArrayList<EquiteBusiDetails001Dto>();
            newKeyList.addAll(keyList);

            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                allChbEqudetDataList.addAll(equityBusiDetails001Mapper.selectAllChbEqudetData(
                        newKeyList.subList(0, maxDataCountOneTime)));
                newKeyList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newKeyList)) {
                allChbEqudetDataList.addAll(
                        equityBusiDetails001Mapper.selectAllChbEqudetData(newKeyList));
            }
        }

        //投资者明细信息维护表主键改为产品代码
        Map<String, EquiteBusiDetails001Dto> allChbEqudetDataMap = StringUtil.isEmptyList(
                allChbEqudetDataList) ? new HashMap<String, EquiteBusiDetails001Dto>() : allChbEqudetDataList.stream().collect(
                JoyinCollectors.toMap(
                        x -> x.getBusiSeqno() + GlobalConstant.UNDER_LINE + x.getAccount()
                                + GlobalConstant.UNDER_LINE + x.getRegistBankCode(),
                        x -> x));

        return allChbEqudetDataMap;
    }

    /**
     * 批量插入投资者明细数据
     *
     * @param dataList 业务明细数据
     */
    private void insertChbEqudetDataByPart(List<EquiteBusiDetails001Dto> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int maxDataCountOneTime = 50;
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiDetails001Mapper.insertChbEqudetData(dataList);
                return;
            }

            List<EquiteBusiDetails001Dto> newDataList = new ArrayList<EquiteBusiDetails001Dto>();
            newDataList.addAll(dataList);

            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiDetails001Mapper.insertChbEqudetData(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiDetails001Mapper.insertChbEqudetData(newDataList);
            }
        }
    }

    /**
     * 将xml读取的Map<String,String>转化为Dto格式 全量转换
     *
     * @param map 参数
     * @param ibd InterestsBusinessDetailed
     * @param result 结果
     */
    public static void maptoSetDto(Map<String, String> map, InterestsBusinessDetailed ibd,
                                   String[] result) {
        for (int i = 0; i < result.length; i++ ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if ("BUSI_SEQNO".equals(result[i])) {
                ibd.setIndentifyMark(map.get("BUSI_SEQNO"));
            }
            else if ("REGIST_BANK_CODE".equals(result[i])) {
                ibd.setRegistBankCode(map.get("REGIST_BANK_CODE"));
            }
            else if ("ACCOUNT".equals(result[i])) {
                ibd.setAccount(map.get("ACCOUNT"));
            }
            else if ("EQUITY_CONF_DATE".equals(result[i])) {
                try {
                    ibd.seteQuityConfDate(sdf.parse(map.get("EQUITY_CONF_DATE")));
                }
                catch (ParseException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            else if ("INDENTIFY_MARK".equals(result[i])) {
                ibd.setIndentifyMark(map.get("INDENTIFY_MARK"));
            }
            // else if ("BUY_PLACE".equals(result[i])) {
            // ibd.setBuyPlace(map.get("BUY_PLACE"));
            // }
            else if ("BUSI_TYPE".equals(result[i])) {
                ibd.setBusiType(map.get("BUSI_TYPE"));
            }
            else if ("CCY".equals(result[i])) {
                ibd.setCcy(map.get("CCY"));
            }
            else if ("AMOUNT".equals(result[i])) {
                ibd.setAmount(Double.valueOf(map.get("AMOUNT")));
            }
            else if ("SHARE".equals(result[i])) {
                ibd.setShares(Double.valueOf(map.get("SHARE")));
            }
            else if ("CHANNEL".equals(result[i])) {
                ibd.setChannel(map.get("CHANNEL"));
            }
            else if ("FUND_ACC".equals(result[i])) {
                ibd.setFundAcc(map.get("FUND_ACC"));
            }
            else if ("FUND_ACC_BANK".equals(result[i])) {
                ibd.setFundAccBank(map.get("FUND_ACC_BANK"));
            }
            else if ("REMARK".equals(result[i])) {
                ibd.setRemark(map.get("REMARK"));
            }
            else if ("AGENT".equals(result[i])) {
                ibd.setAgent(map.get("AGENT"));
            }
            if (StringUtil.isNotEmpty(map.get("DEPT_CODE"))) {
                ibd.setDeptCode(map.get("DEPT_CODE"));
            }
        }
    }

    /**
     * 从接口中获取权益人业务明细 1.调用共通方法解析传入的接口文件 2.遍历解析后的数据，逐条插入验证（暂定） 3.验证中债需要验证的模块，通过则赋有效状态；不通过，则显示待处理状态
     * 4.验证通过后，判断是否新增数据 4。1 若为新增数据，则直接insert 4.2 若非新增数据，则需读取是否覆盖开关 4.2.1 若开关打开，只更新数据配置项中的字段 4.2.2
     * 若开关关闭，且数据库中为接口维护，则全量更新；若数据库中为手动维护，则不更新
     *
     * @param in 传入输入流
     * @param type 数据来源
     * @param tmpPath 临时路径
     * @param suffix 文件后缀名
     * @return string 处理成功失败
     * @see
     * @since 1.0
     */
//    @SuppressWarnings("rawtypes")
    public String getEquityBusiDetailsByInterf(InputStream in, String type, String tmpPath,
                                               String suffix, ChbFileReadMonitorInfo monitorInfoDto) {
        // 如果接口尚未处理完成，则不处理
        if (equityBusiDetails001Mapper.selectTmpCount() > 0) {
            return null;
        }
        List<Map<String, String>> equityList = null;
        if ("xml".equals(suffix)) {
            try {
                equityList = XmlParseUtil.parse(in);

                if(StringUtil.isEmptyList(equityList)){
                    return "success";
                }
            }
            catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(), e);
                return "fail";
            }
        }
        else if ("txt".equals(suffix)) {
            try {
                equityList = TxtParseUtil.parse(in, reportInterfFileEncoding,
                        reportInterfSplitCharacter);

                if(StringUtil.isEmptyList(equityList)){
                    return "success";
                }
            }
            catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(), e);
                return "fail";
            }
        }
        logger.info("解析xml文件存入List完成后equityList长度："+equityList.size());
        DataCheckService dataCheckService = SpringContextUtil.getBean(DataCheckService.class);
        //查询对应的配置检查规则
        List<CheckRule> listRules = dataCheckService.queryCheckRules(ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS);
        // 查询出所有身份信息
//        List<EquityInfo001Dto> allInfoList = new ArrayList<EquityInfo001Dto>();

        //edit by zhengxiaolian 2019-12-24 start
//        template.select(
//            "com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper.selectEquityInfoAll",
//            new ResultHandler() {
//                @Override
//                public void handleResult(ResultContext resultContext) {
//                    allInfoList.add((EquityInfo001Dto)resultContext.getResultObject());
//                }
//            });
//        Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
//            JoyinCollectors.groupingBy(
//                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
//                     + x.getIndentifyMark()));
        //edit by zhengxiaolian 2019-12-24 end

        // 查询出所有有三要素合并身份信息
        List<ChbEquHoldCheckDto> allcheckList = equityBusiInfoRegist001Mapper.selectAllCheckEquHoldDto();
        Map<String, ChbEquHoldCheckDto> equityInfocheckMap = allcheckList.stream().collect(
                Collectors.toMap(ChbEquHoldCheckDto::getIndentifyMark, a -> a, (k1, k2) -> k1));
        //update zhangjinyang 2020-12-21 防止内存溢出
        allcheckList.clear();
        allcheckList=null;
        //update zhangjinyang 2020-12-21 防止内存溢出
        // 查询产品登记编码
        List<Map<String, String>> regList = equityBusiDetails001Mapper.queryAllRegCode();
        // 解析成功或失败的标识
        boolean success = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate=new Date();
        Long bt=bdate.getTime();
        logger.info("投资者明细信息入库处理开始："+sdf.format(bdate));
        if (equityList != null) {
            monitorInfoDto.setFileDataCount(BigDecimal.valueOf(equityList.size()));
            // 过滤掉主键为空的数据
            Iterator<Map<String, String>> it = equityList.iterator();
            // add by zhangmingming 2019-12-30 记录错误信息 start
            List<Map<String,String>> listData = new ArrayList<>();
            List<String> seqNoList = new ArrayList<>();
            int count = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ERROR_INSERT_COUNT)?"10000":SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ERROR_INSERT_COUNT));
            // add by zhangmingming 2019-12-30 记录错误信息 end
            while (it.hasNext()) {
                Map<String, String> x = it.next();
                if (StringUtil.isEmpty(x.get("BUSI_SEQNO"))
                        || StringUtil.isEmpty(x.get("REGIST_BANK_CODE"))
                        || StringUtil.isEmpty(x.get("ACCOUNT"))
                        ||"".equals(x.get("BUSI_SEQNO"))
                        ||"".equals(x.get("REGIST_BANK_CODE"))
                        ||"".equals(x.get("ACCOUNT"))) {
                    it.remove();
                    listData.add(x);
                    if(listData.size()==count){
                        //主键为空数据达到COUNT值导出
                        seqNoList.add(export(listData));
                        listData.clear();
                    }
                }
            }
            //导出剩余数据
            if(listData != null && listData.size()!=0) {
                seqNoList.add(export(listData));
                listData.clear();
            }
            // 错误文件流水号存入monitor表
            monitorInfoDto.setErrorFileSeqNo(StringUtil.joinString(seqNoList,GlobalConstant.COMMA));

            if (equityList.size() <= 1000) {
                List<EquityBusiInfoRegist001Dto> listNASave = new ArrayList<EquityBusiInfoRegist001Dto>();
                List<EquityBusiInfoRegist001Dto> listNESave = new ArrayList<EquityBusiInfoRegist001Dto>();
                List<EquityBusiInfoRegist001Dto> listNESUBSave = new ArrayList<EquityBusiInfoRegist001Dto>();

                success = excuteQuityBusiDetailsByStep(equityList, type,  listNASave, listNESave, listNESUBSave,
                        equityInfocheckMap,regList,listRules);
                if(listNASave != null && listNASave.size() > 0){
                    insertChbEqudetDataByPartRegist(listNASave);                // 插入维护信息表
                }
                if(listNESave != null && listNESave.size() > 0){
                    insertChbEqudetDataByPartRegist(listNESave);                // 插入维护信息表
                }
                if(listNESUBSave != null && listNESUBSave.size() > 0){
                    insertChbEqudetSubDataByPart(listNESUBSave);                // 插入登记表
                }
                //update zhangjinyang 2020-12-21 防止内存溢出
                equityInfocheckMap.clear();
                //update zhangjinyang 2020-12-21 防止内存溢出
                //edit by zhengxiaolian 2019-12-24 end
            }
            else {

                List<EquityBusiInfoRegist001Dto> listNASave = new CopyOnWriteArrayList<EquityBusiInfoRegist001Dto>();
                List<EquityBusiInfoRegist001Dto> listNESave = new CopyOnWriteArrayList<EquityBusiInfoRegist001Dto>();
                List<EquityBusiInfoRegist001Dto> listNESUBSave = new CopyOnWriteArrayList<EquityBusiInfoRegist001Dto>();

                // 创建一个线程池
                if (interfMaxCorePoolSize < 1) {
                    interfMaxCorePoolSize = 20;
                }
                ExecutorService service = new JoyinThreadPoolExecutor(interfMaxCorePoolSize);
                List<Callable<Boolean>> tasks = new ArrayList<>();
                if (null != equityList) {
                    int start = 0;
                    int end = 0;
                    int size = equityList.size();
                    int max = (int)Math.ceil((double)size /  reportInterfOneTimeDataCount);
                    for (int i = 0; i < max; i++ ) {
                        start = end;
                        end = Math.min(size,  reportInterfOneTimeDataCount * (i + 1));
                        List<Map<String, String>> subList = equityList.subList(start, end);
                        logger.info("循环截取equityList处理：i="+i+";start="+start+";end="+end+" subList.size="+subList.size());
                        tasks.add(new Callable<Boolean>() {

                            @Override
                            public Boolean call() {
                                return excuteQuityBusiDetailsByStep(subList, type, listNASave,
                                        listNESave,listNESUBSave,equityInfocheckMap,regList,listRules);
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
                    Thread.currentThread().interrupt();
                    return "fail";
                }
                for (Future<Boolean> res : taskResult) {
                    try {
                        success = success && res.get();
                    }
                    catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(), e);
                        Thread.currentThread().interrupt();
                        return "fail";
                    }
                }
                // 任务结束，停止线程池的所有线程
                service.shutdown();
                //update zhangjinyang 2020-12-21 防止内存溢出
                equityInfocheckMap.clear();
                //update zhangjinyang 2020-12-21 防止内存溢出
                // 创建表
                ImpTable table = new ImpTable(CHB_EQUDET_NAME);
                table.setColumns(ImportGenerator.createImpTableColumns(CHB_EQUDET_NAME));
                ImpTable subtable = new ImpTable(CHB_EQUDET_SUB_NAME);
                subtable.setColumns(ImportGenerator.createImpTableColumns(CHB_EQUDET_SUB_NAME));
                subtable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));

                if(listNASave != null && listNASave.size() > 0){
                    batchInsertService.exeBatchldr(tmpPath, listNASave, table);
                }
                if(listNESave != null && listNESave.size() > 0){
                    batchInsertService.exeBatchldr(tmpPath, listNESave, table);
                }
                if(listNESUBSave != null && listNESUBSave.size() > 0){
                    batchInsertService.exeBatchldr(tmpPath, listNESUBSave, subtable);
                }

            }
        }
        BaseCheckLib.objFiledsMap=null;
        Date edate=new Date();
        Long et=edate.getTime();
        Long time=(et-bt)/1000;
        logger.info("投资者明细信息入库处理结束:"+sdf.format(edate)+"耗时"+time+"秒");
//	}
        // 数据解析完成，并导入数据库成功后，将ftp文件修改名称
        if (success) {
            return "success";
        }
        //add by liubin start 当无投资者身份信息供转换时更新监控表异常描述
        monitorInfoDto.setFileHandleResultMsg(this.getMessage("REPORT_FILE_MONITORINFO_NODATA"));
        //add by liubin end
        return "fail";
    }

    /**
     * @param equityList 投资者列表
     * @param type 数据来源
     * @param result 参数
     * @param tmpPath 生成的临时文件存放路径
     * @return 此次是否成功插入
     */
    private boolean excuteQuityBusiDetailsByStep(List<Map<String, String>> fileEquityList, String type,
                                                 List<EquityBusiInfoRegist001Dto> listNASave, List<EquityBusiInfoRegist001Dto> listNESave,
                                                 List<EquityBusiInfoRegist001Dto> listNESUBSave,
                                                 Map<String, ChbEquHoldCheckDto> equityInfocheckMap,
                                                 List<Map<String, String>> regList,List<CheckRule> listRules
    ) {
        List<Map<String, String>> equityList = new ArrayList<>();
        equityList.addAll(fileEquityList);
        //addBy zhangjinyang 及时清空防止内存溢出 start
        fileEquityList=null;
        //addBy zhangjinyang 及时清空防止内存溢出 end
        // 投资者信息接口信息不为null处理
        if (equityList != null && equityList.size() > 0) {
            logger.info("逻辑处理前的equityList.size: " + equityList.size());
            // 定义循环投资者信息变量
            EquityBusiInfoRegist001Dto ibd = null;
            // 定义新增数据list-验证不通过数据
            List<EquityBusiInfoRegist001Dto> listNA = new ArrayList<>();
            // 定义新增数据list-验证通过数据
            List<EquityBusiInfoRegist001Dto> listNE = new ArrayList<>();
            List<EquityBusiInfoRegist001Dto> listNESub = new ArrayList<>();
            // 定义已存在数据list-原数据“待处理”，新接入数据是“待处理”
            List<EquityBusiInfoRegist001Dto> listAA = new ArrayList<>();
            // 定义已存在数据list-原数据“待处理”，新接入数据是“有效”
            List<EquityBusiInfoRegist001Dto> listAE = new ArrayList<>();
            // 定义已存在数据list-原数据“有效”，新接入数据还是“待处理”
            List<EquityBusiInfoRegist001Dto> listOA = new ArrayList<>();
            // 定义已存在数据list-原数据“有效”，新接入数据还是“有效”
            List<EquityBusiInfoRegist001Dto> listOE = new ArrayList<>();

            // 插入导入用的临时数据
            equityBusiDetails001Mapper.insertIntoImportTable(equityList);
            //add by zhengxiaolian 2019-12-24 start
            List<EquityInfo001Dto> allInfoList = new ArrayList<EquityInfo001Dto>();
            allInfoList=equityBusiDetails001Mapper.getAllChbEquInfoOneTime();
            Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
                    JoyinCollectors.groupingBy(
                            x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
                                    + x.getIndentifyMark()));
            //add by zhengxiaolian 2019-12-24 end
            //addBy zhangjinyang 及时清空防止内存溢出 start
            allInfoList=null;
            //addBy zhangjinyang 及时清空防止内存溢出 end
            // 获取所有的产品代码
            Map<String, String> proRegistCodeManage001Map = new HashMap<String, String>();
            proRegistCodeManage001Map = regList.stream().collect(
                    JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("REG_CODE")));
            Map<String, String> allProdIdMap = regList.stream().collect(
                    JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("OPERATE_MODE")));
            // 查询库中已存在的投资者明细维护信息
            List<ChbEqudet> equdetList = equityBusiDetails001Mapper.selectChbEqudetByKey();
            // 查询库中已存在的投资者明细登记信息
            List<ChbEqudet> equdetRegistList=new ArrayList<ChbEqudet>();
            if(!StringUtil.isEmptyList(equdetList)){
                equdetRegistList = equityBusiInfoRegist001Mapper.selectChbEqudetSubByKey();
            }
            Map<String, ChbEqudet> equdetMap = equdetList.stream().collect(
                    Collectors.toMap(x -> x.getBusiSeqno() + GlobalConstant.UNDER_LINE + x.getRegistBankCode()
                            + GlobalConstant.UNDER_LINE + x.getAccount(), k1-> k1));
            //addBy zhangjinyang 及时清空防止内存溢出 start 2020-12-21
            equdetList=null;
            //addBy zhangjinyang 及时清空防止内存溢出 end
            Map<String, List<ChbEqudet>> equdetRegistMap = equdetRegistList.stream().collect(
                    JoyinCollectors.groupingBy(
                            x -> x.getBusiSeqno() + GlobalConstant.UNDER_LINE + x.getRegistBankCode()
                                    + GlobalConstant.UNDER_LINE + x.getAccount()));
            //addBy zhangjinyang 及时清空防止内存溢出 start 2020-12-21
            equdetRegistList=null;
            //addBy zhangjinyang 及时清空防止内存溢出 end
            List<String> keyItemNameList = new ArrayList<>();
            keyItemNameList.add("busiSeqno");
            keyItemNameList.add("account");
            keyItemNameList.add("registBankCode");
            //加入OPERATE_MODE字段用于检查确认净值
            for (Map<String, String> x :equityList) {
           	 if (allProdIdMap.containsKey(x.get("ACCOUNT"))) {
                  x.put("OPERATE_MODE", allProdIdMap.get(x.get("ACCOUNT")));
              }
            }
            // 过滤掉主键为空的数据
//            Iterator<Map<String, String>> it = equityList.iterator();
//            // add by zhangmingming 2019-12-30 记录错误信息 start
//            List<Map<String,String>> listData = new ArrayList<>();
//            List<String> seqNoList = new ArrayList<>();
//            int count = Integer.parseInt(null == SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ERROR_INSERT_COUNT)?"10000":SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ERROR_INSERT_COUNT));
//            // 更改回来
//            count = 10;
//            // add by zhangmingming 2019-12-30 记录错误信息 end
//            while (it.hasNext()) {
//                Map<String, String> x = it.next();
//                if (StringUtil.isEmpty(x.get("BUSI_SEQNO"))
//                    || StringUtil.isEmpty(x.get("REGIST_BANK_CODE"))
//                    || StringUtil.isEmpty(x.get("ACCOUNT"))
//                    ||"".equals(x.get("BUSI_SEQNO"))
//                    ||"".equals(x.get("REGIST_BANK_CODE"))
//                    ||"".equals(x.get("ACCOUNT"))) {
//                    it.remove();
//                    listData.add(x);
//                    if(listData.size()==count){
//                        //主键为空数据达到COUNT值导出
//                        seqNoList.add(export(listData));
//                        listData.clear();
//                    }
//                }
//
//                if (allProdIdMap.containsKey(x.get("ACCOUNT"))) {
//                    x.put("OPERATE_MODE", allProdIdMap.get(x.get("ACCOUNT")));
//                }
//            }
//            //导出剩余数据
//            if(listData != null && listData.size()!=0) {
//                seqNoList.add(export(listData));
//                listData.clear();
//            }
//            // 错误文件流水号存入monitor表
//            monitorInfoDto.setErrorFileSeqNo(StringUtil.joinString(seqNoList,GlobalConstant.COMMA));
            if (equityList.isEmpty()) {
                return true;
            }
            // 过滤主键重复的数据
            DataCheckService.checkDuplicatedData(equityList, "BUSI_SEQNO", "ACCOUNT", "REGIST_BANK_CODE");
            List<Map<String, String>> checkMapList = new ArrayList<Map<String, String>>();
            for (Map<String, String> map : equityList) {
                Map<String, String> checkmap = new HashMap<String, String>();
                for (String key : map.keySet()) {
                    if (key != null)
                        checkmap.put(CommonUtil.dealString(key, true), map.get(key));
                }
                checkMapList.add(checkmap);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date bdate=new Date();
            Long bt=bdate.getTime();
            logger.info("明细信息检查规则开始："+sdf.format(bdate));
            //check判断返回每一行的错误信息
            DataCheckService.checkDataList3(checkMapList, listRules);
            Date edate=new Date();
            Long et = edate.getTime();
            Long time = (et-bt)/1000;
            logger.info("明细信息检查规则结束:"+sdf.format(edate)+"耗时"+time+"秒");
//          List<ExcelValidDto> rulecheckres = IBDDataCheckUtil.checkDataList(checkMapList,
//                  ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS, keyItemNameList);
//          Map<String, String> errMap = new HashMap<String, String>();
//          for (int i = 0; i < rulecheckres.size(); i++ ) {
//          	errMap.put(rulecheckres.get(i).getRowNum(), rulecheckres.get(i).getErrorInfo());
//          }
//            IBDDataCheckUtil.checkDataList(checkMapList,
//                ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS, keyItemNameList);
            // 当前批次报送时点集合
            Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
            Map<String, BigDecimal> rateMap = new HashMap<>();
            List<Date> dateList = null;
            logger.info("逻辑处理后的equityList.size: " + equityList.size());
            List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, equityList.size());
            int index =0;
            for (int i = 0; i < equityList.size(); i++ ) {
//            	String errorMessage = null;
//            	if(errMap.containsKey(String.valueOf(i+2))){
//            		errorMessage=errMap.get(String.valueOf(i+2));
//            	}
                Map<String, String> map = equityList.get(i);
                String regcode = null;
                if (StringUtil.isNotEmpty(map.get("ACCOUNT"))) {
                    regcode = proRegistCodeManage001Map.get(map.get("ACCOUNT"));
                }
                // 投资者信息数据验证及赋值
                ibd = dataConversionEntity(map, regcode,checkMapList.get(i).get("ERRMSG"),
                        rateMap);
                for(String key:checkMapList.get(i).keySet()){
                    if(key.charAt(0)>= 'A' && key.charAt(0) <= 'Z'){
                        if(ibd.getTempMap()!=null){
                            ibd.getTempMap().put( CommonUtil.dealString(key,true), checkMapList.get(i).get(key));
                        }else{
                            Map<String, String> tempMap=new HashMap<String, String>();
                            tempMap.put( CommonUtil.dealString(key,true), checkMapList.get(i).get(key));
                            ibd.setTempMap(tempMap);
                        }
                    }
                }

                // 根据汇率获取折算人民币
                // getConverCnyAmount(ibd);
                if (equityInfocheckMap.containsKey(ibd.getIndentifyMark())) {
                    ibd.setOrgIndentifyMark(ibd.getIndentifyMark());
                    ibd.setIndentifyMark(
                            equityInfocheckMap.get(ibd.getIndentifyMark()).getMergerindentifymark());
                }else{
                    //不管是否有变更都需要赋值
                    ibd.setOrgIndentifyMark(ibd.getIndentifyMark());
                }
                /*edit by xuyang for业务确认日期为空时，防止获取报送时点报错 徽商个性化 20200822*/
                if(ibd.getEquityConfDate()!=null) {
                    // 报送时点通用方法
                    dateList = ChbDeadlineUtil.getChbEndDate(
                            DictCodeListExt2.报表名称_投资者明细信息登记.getValue(),
                            DictCodeListExt2.报送数据状态_新建.getValue(), dateMap, ibd.getEquityConfDate());
                }

                if (CollectionUtils.isNotEmpty(dateList)) {
                    ibd.setChbSubmitDeadline(dateList.get(0));
                    ibd.setSubmitDeadline(dateList.get(1));
                }
                ibd.setDataSource(type);
                ibd.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                ibd.setStatusDate(DateUtil.now());
                ibd.setCreateTime(DateUtil.now());
                ibd.setUpdateTime(DateUtil.now());
                // 根据主键查询投资人信息
                ChbEqudet chbEqudet = equdetMap.get(
                        ibd.getBusiSeqno() + GlobalConstant.UNDER_LINE + ibd.getRegistBankCode()
                                + GlobalConstant.UNDER_LINE
                                + ibd.getAccount());
                //跟库中维护数据对比
                if(chbEqudet!=null&&docheckIfchange(ibd,chbEqudet)){
                    continue;
                }
//                ChbEqudet chbEqudet = null;
//                if (tmpList != null) {
//                    chbEqudet = tmpList.get(0);
//                }

                // 原数据没有，新接入数据是“待处理”的处理
                if (chbEqudet == null && "A".equals(ibd.getStatus())) {
                    listNA.add(ibd);
                } // 原数据没有，新接入数据是“有效”的处理
                else if (chbEqudet == null && "E".equals(ibd.getStatus())) {
                    ibd.setSumitSeq(
                            //CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
                            CommonUtil.getSequenceStr(sequenceList,index)
                    );
                    listNE.add(ibd);
                } // 原数据“待处理”，新接入数据是“待处理”的处理
                else if ("A".equals(chbEqudet.getStatus()) && "A".equals(ibd.getStatus())) {
                    listAA.add(ibd);
                } // 原数据“待处理”，新接入数据是“有效”的处理
                else if ("A".equals(chbEqudet.getStatus()) && "E".equals(ibd.getStatus())) {
                        ibd.setSumitSeq(
                            //CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
                            CommonUtil.getSequenceStr(sequenceList,index)
                            );
                    listAE.add(ibd);
                } // 原数据“有效”，新接入数据是“待处理”的处理
                else if ("E".equals(chbEqudet.getStatus()) && "A".equals(ibd.getStatus())) {
                    listOA.add(ibd);
                } // 原数据“有效”，新接入数据是“有效”的处理
                else if ("E".equals(chbEqudet.getStatus()) && "E".equals(ibd.getStatus())) {
                    ibd.setSumitSeq(
                        //CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
                        CommonUtil.getSequenceStr(sequenceList,index)
                        );
                    listOE.add(ibd);
                }
                index++;
            }
            // 原数据为空时,并且数据状态为待维护，先插入维护表
            if (listNA != null && listNA.size() > 0) {
                listNASave.addAll(listNA);
            }
            // 原数据为空时,并且数据状态为有效，先插入维护表，再插入登记表
            if (listNE != null && listNE.size() > 0) {
                listNESave.addAll(listNE);
                listNE.stream().forEach(i->{
                    EquityBusiInfoRegist001Dto copy = new EquityBusiInfoRegist001Dto();
                    BeanUtil.copyPropertiesIgnoreNull(copy,i);
                    listNESub.add(copy);
                });
                changeStatusValue(listNESub, false, null, equityInfo001Map);
                listNESUBSave.addAll(listNESub);
            }
            // 原数据为待处理,并且新数据状态为待处理，直接更新维护表
            if (listAA != null && listAA.size() > 0) {
                logger.info("listAA处理开始  listAA = " + listAA.size());
                // 插入维护信息表
                updateBatchEquityBusiByPart(listAA);
                logger.info("listAA处理结束 ");
            }
            // 原数据为待处理,并且新数据状态为有效，直接更新维护表，并入登记表
            if (listAE != null && listAE.size() > 0) {
                logger.info("listAE处理开始  listAE = " + listAE.size());
                // 插入维护信息表
                updateBatchEquityBusiByPart(listAE);
                // 插入登记表
                changeStatusValue(listAE, false, null, equityInfo001Map);
                insertChbEqudetSubDataByPart(listAE);
                logger.info("listAE处理结束 ");
            }
            // 原数据为有效,并且新数据状态为待处理，将原维护表数据插入历史表，更新维护表，并入删除登记表中未报送数据
            if (listOA != null && listOA.size() > 0) {
                logger.info("listOA处理开始  listOA = " + listOA.size());
                // 首先将权益人信息表插入履历表中
                insertEquityBusiHisByPart(listOA);
                // 插入维护信息表
                updateBatchEquityBusiByPart(listOA);
                // 删除登记表中未报送数据
                deleteBatchEquityBusiByPart(listOA);
                logger.info("listOA处理结束 ");
            }
            // 原数据为有效,并且新数据状态为有效，将原维护表数据插入历史表，更新维护表，并入删除登记表中未报送数据
            if (listOE != null && listOE.size() > 0) {
                logger.info("listOE处理开始  listOE = " + listOE.size());
                // 首先将权益人信息表插入履历表中
                insertEquityBusiHisByPart(listOE);
                // 插入维护信息表
                updateBatchEquityBusiByPart(listOE);
                // 删除登记表中未报送数据
                deleteBatchEquityBusiByPart(listOE);
                // 插入登记表
                changeStatusValue(listOE, true, equdetRegistMap, equityInfo001Map);

                insertChbEqudetSubDataByPart(listOE);
                logger.info("listOE处理结束 ");
            }
            //addBy zhangjinyang 及时清空防止内存溢出 start 2020-12-21
            equdetRegistMap=null;
            equdetMap=null;
            equityInfo001Map=null;
            equityList=null;
            //addBy zhangjinyang 及时清空防止内存溢出 end

            return (CollectionUtils.isNotEmpty(listNA) || CollectionUtils.isNotEmpty(listNE)
                    || CollectionUtils.isNotEmpty(listAA) || CollectionUtils.isNotEmpty(listAE)
                    || CollectionUtils.isNotEmpty(listOA) || CollectionUtils.isNotEmpty(listOE));
        }
        return false;
    }

    private File batchMakeFile(String configFile,List<EquiteBusiDetails001Dto> contents){
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

        try {
            while(contents.size() > count) {
                List<?> subList = contents.subList(0, count);
                contents = contents.subList(count, contents.size());
                fileList.add(this.makeSingleFile(wb, reportConfig, (String)null, fileType, subList, new LongAdder(), 0L, count));
            }

            fileList.add(this.makeSingleFile(wb, reportConfig, (String)null, fileType, contents, new LongAdder(), 0L, count));
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
        try{
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
            return (Workbook)wb;
        }finally {
            if(stream != null){
                stream.close();
            }
        }
    }

    private File makeSingleFile(Workbook wb, JoyinBookConfig reportConfig, String pageId, String fileType, List<?> contents, LongAdder current, long total, int sheetMaxRowCount) throws IOException {
        ReportCfgHandler handler = new ReportCfgHandler();
        wb.close();
        wb = this.getWorkbook(reportConfig);
        List<JoyinSheetConfig> sheetList = reportConfig.getSheetConfList();

        for(int i = 0; i < sheetList.size(); ++i) {
            JoyinSheetConfig sheetCfg = (JoyinSheetConfig)sheetList.get(i);
            String beanName = sheetCfg.getJavaBean();
            List<JoyinCellConfig> cellList = sheetCfg.getCellConfList();
            Sheet sheet = wb.getSheetAt(i);
            int index = sheetCfg.getRowStart();
            Row lastRow = sheet.getRow(sheet.getLastRowNum());
            Iterator var19 = contents.iterator();

            while(var19.hasNext()) {
                Object dto = var19.next();
                Map<String, Object> map = BeanUtil.object2Map(dto);
                Row row = sheet.getRow(index++);
                if (row == null) {
                    handler.copyRow(wb, lastRow, sheet.createRow(index - 1), false);
                    row = sheet.getRow(index - 1);
                }

                for(int ci = 0; ci < cellList.size(); ++ci) {
                    JoyinCellConfig cfg = (JoyinCellConfig)cellList.get(ci);
                    Cell cell = row.getCell(ci, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String propName = cfg.getBeanPropName();
                    if (StringUtil.isNotEmpty(propName)) {
                        Cell hCell = lastRow.getCell(ci);
                        if (null == cell && null != hCell) {
                            cell = row.createCell(ci);
                            handler.copyCell(wb, hCell, cell, false);
                            this.setCellStyle(wb, cell.getCellStyle());
                        }

                        Object value = map.get(propName);
                        if (StringUtil.isNotEmpty(value)) {
                            if (value instanceof Date) {
                                cell.setCellValue(DateUtil.formatDateTime((Date)value));
                            } else if (value instanceof String) {
                                if (StringUtil.isNotEmpty(value)) {
                                    value = StringUtil.getLimitLengthString(value.toString(), 16383, "");
                                }

                                cell.setCellValue((String)value);
                            } else if (value instanceof Boolean) {
                                cell.setCellValue((Boolean)value);
                            } else if (value instanceof Calendar) {
                                cell.setCellValue((Calendar)value);
                            } else if (value instanceof RichTextString) {
                                cell.setCellValue((RichTextString)value);
                            } else if (value instanceof Double) {
                                cell.setCellValue((Double)value);
                            } else if (value instanceof BigDecimal) {
                                BigDecimal cellValue = (BigDecimal)value;
                                cell.setCellValue(cellValue.doubleValue());
                            } else if (value instanceof Long) {
                                Long cellValue = (Long)value;
                                cell.setCellValue(cellValue.doubleValue());
                            } else if (value instanceof Integer) {
                                Integer cellValue = (Integer)value;
                                cell.setCellValue(cellValue.doubleValue());
                            }

                            this.setCellValue(beanName, cell, propName, value);
                        }
                    }
                }

            }
        }
        String outFileName = CHB_EQUDET_NAME + GlobalConstant.UNDER_LINE +
                "error" + GlobalConstant.UNDER_LINE + DateUtil.getCurrentDateTime().replaceAll(":", "") +fileType;
        String exportFilePath = FileNameUtil.createUploadFilePath("");
        File file = new File(exportFilePath + File.separator + outFileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            return file;
        }finally {
            if(fileOutputStream != null){
                fileOutputStream.close();
            }
        }
    }

    private List<EquiteBusiDetails001Dto> convertMapToEntity(List<Map<String,String>> listData,List<EquiteBusiDetails001Dto> entities){
        List<String> listColumn =  baseMapper.getTableColumnList(CHB_EQUDET_NAME);
        for (Map<String, String> data : listData) {
            EquiteBusiDetails001Dto dto = new EquiteBusiDetails001Dto();
            for (String column : listColumn) {
                String value = data.get(column);
                if("STATUS".equals(column)) column = "STATUS_STR";
                Field field = ReflectionUtils.findField(dto.getClass(),
                        this.subBlank(StringUtil.lowerCase(column)));
                field.setAccessible(true);
                Type type = field.getGenericType();
                if ("java.math.BigDecimal".equals(type.getTypeName())){
                    try {
                        field.set(dto,(value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(value));
                    } catch ( IllegalAccessException e) {
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }else if("java.lang.Integer".equals(type.getTypeName())){
                    try {
                        field.set(dto,(value == null || "".equals(value)) ? 0 : Integer.parseInt(value));
                    } catch (IllegalAccessException e){
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }else if ("java.lang.Short".equals(type.getTypeName())){
                    try {
                        field.set(dto,(value == null || "".equals(value)) ? 0 : Short.valueOf(value));
                    } catch (IllegalAccessException e){
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                } else if ("java.util.Date".equals(type.getTypeName())){
                    if(value != null && !"".equals(value)){
                        try {
                            field.set(dto, com.joyintech.tams.report.interf.common.constant.DateUtil.parse(value));
                        } catch (IllegalAccessException e){
                            logger.error(e.getMessage(),e);
                            return entities;
                        }
                    }
                } else {
                    try {
                        field.set(dto,(value == null || "".equals(value)) ? "" : value);
                    } catch (IllegalAccessException e){
                        logger.error(e.getMessage(),e);
                        return entities;
                    }
                }
            }
            entities.add(dto);
        }
        return entities;
    }

    /**
     * 功能:将表字段转换为属性字段
     * @param str
     * @return
     */
    private String subBlank(String str) {
        if(str==null){
            return null;
        }
        String[] strarray = str.split("_");
        if(strarray.length == 0) return null;
        String result = strarray[0];
        for (int i = 1; i < strarray.length; i++) {
            result = result + (new StringBuilder()).append(Character.toUpperCase(strarray[i].charAt(0))).append(strarray[i].substring(1)).toString();
        }
        return result;
    }


    private String export(List<Map<String,String>> listData){
        List<EquiteBusiDetails001Dto> entiteis = new ArrayList<>();
        List<EquiteBusiDetails001Dto> listDto = convertMapToEntity(listData,entiteis);
        dealReturnInfo(listDto);
        File exportFile = batchMakeFile("/com/joyintech/tams/report/submitmaintenance/businessdetailed/conf/export_conf.xml",listDto);
        String fileName = exportFile.getName();
        logger.info("UploadFile fileName= "  + exportFile);
        String seqNo = IDGenerator.generate();
        UploadFile uploadFile = new UploadFile(fileName,seqNo,exportFile);

        // 上传sftp,入库upload_info表
        if(null!= fileAttachmentProcess) {
            fileAttachmentProcess.upload(uploadFile);
        }
        // 流水号提供给monitor表记录错误文件信息
        return seqNo;
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
        DictCodeList dictCodeList = DictCodeList.from(dictCode, dataObj);
        DictCodeListExt1 dictCodeListExt1 = DictCodeListExt1.from(dictCode, dataObj);
        DictCodeListExt2 dictCodeListExt2 = DictCodeListExt2.from(dictCode, dataObj);
        DictCodeListExt3 dictCodeListExt3 = DictCodeListExt3.from(dictCode, dataObj);
        String value = dataObj;
        if (null != dictCodeList) {
            value = dataObj + ":" + dictCodeList.getCodeName();
        }
        if (null != dictCodeListExt1) {
            value = dataObj + ":" + dictCodeListExt1.getCodeName();
        }
        if (null != dictCodeListExt2) {
            value = dataObj + ":" + dictCodeListExt2.getCodeName();
        }
        if (null != dictCodeListExt3) {
            value = dataObj + ":" + dictCodeListExt3.getCodeName();
        }
        return value;
    }

    /**
     * 更新登记表所需字段 初始登记 <一句话功能简述> <功能详细描述>
     *
     * @param list
     */
    void changeStatusValue(List<EquityBusiInfoRegist001Dto> list, boolean ifOld,
                           Map<String, List<ChbEqudet>> equdetRegistMap,
                           Map<String, List<EquityInfo001Dto>> equityInfo001Map) {
        for (EquityBusiInfoRegist001Dto single : list) {
            single.setCustName(ChbSensitiveInfoUtil.handle汉字变形(single.getCustName()));
            if(single.getTempMap()!=null){
                for(String key1:single.getTempMap().keySet()){
                    EquityInfoCheckUtil.updateChcekDto(single,key1,single.getTempMap().get(key1));
                }
            }
            String key = single.getRegistBankCode() + GlobalConstant.VERTICAL_LINE
                    + GlobalConstant.VERTICAL_LINE + single.getIndentifyMark();
            if (equityInfo001Map.containsKey(key)) {
                EquityInfo001Dto eInfo001Dto = equityInfo001Map.get(key).get(0);
                if(StringUtil.equals(DictCodeListExt2.个人证件类别_居民身份证.getValue(), eInfo001Dto.getPersonalIdentifyType())
                        || StringUtil.equals(DictCodeListExt2.个人证件类别_临时居民身份证.getValue(), eInfo001Dto.getPersonalIdentifyType())){
                        String tuominStr = ChbSensitiveInfoUtil.replaceIdNoToMd5(
                                single.getCustContractNo(),
                                eInfo001Dto.getIdentifyNumber());
                        single.setCustContractNo(tuominStr);
                }
            }
            if (single.getStatus() != null && single.getStatus().equals("E")) {
                single.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
            }
            if (ifOld) {
                // 根据主键查询投资人信息
                List<ChbEqudet> tmpList = equdetRegistMap.get(
                        single.getBusiSeqno() + GlobalConstant.UNDER_LINE + single.getRegistBankCode()
                                + GlobalConstant.UNDER_LINE
                                + single.getAccount());
                ChbEqudet chbEqudet = null;
                if (tmpList != null && tmpList.size() > 1) {
                    single.setStatus(DictCodeListExt2.报送数据状态_修改.getValue());
                }
                else if ((tmpList != null && tmpList.size() == 1)) {
                    chbEqudet = tmpList.get(0);
                    if (!chbEqudet.getStatus().equals(DictCodeListExt2.报送状态_未报送.getValue())) {
                        single.setStatus(DictCodeListExt2.报送数据状态_修改.getValue());
                    }
                    else {
                        single.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
                    }
                }
            }
        }
    }

   public void setSubProperty(EquityBusiInfoRegist001Dto dto, String status,
                        Map<String, List<Date>> dateMap,int index,List<String> sequenceList) {
        List<Date> dateList = ChbDeadlineUtil.getChbEndDate(
            DictCodeListExt2.报表名称_投资者明细信息登记.getValue(), status, dateMap, dto.getEquityConfDate());
        if (CollectionUtils.isNotEmpty(dateList)) {
            dto.setChbSubmitDeadline(dateList.get(0));
            dto.setSubmitDeadline(dateList.get(1));
        }
        dto.setSumitSeq(
        		//CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
        		CommonUtil.getSequenceStr(sequenceList,index)
        		);
        dto.setStatus(status);
        dto.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
    }
    /**
     * 检查数据相对于库中存在的数据是否变更
     *
     * @param ibd
     * @param chbEqudet
     * @return
     */
    boolean docheckIfchange(EquityBusiInfoRegist001Dto ibd,ChbEqudet chbEqudet){
        EquityBusiInfoRegist001Dto dto = new EquityBusiInfoRegist001Dto();
        BeanUtil.copyPropertiesIgnoreNull(dto, chbEqudet);
        return compareIssueRegData(ibd,dto);
    }
    /**
     * 检查数据相对于库中存在的数据是否变更
     *
     * @param ibd
     * @param chbEqudet
     * @return
     */
    boolean docheckIfchangeEx(EquiteBusiDetails001Dto ibd,EquiteBusiDetails001Dto chbEqudet){
        EquityBusiInfoRegist001Dto dto = new EquityBusiInfoRegist001Dto();
        BeanUtil.copyPropertiesIgnoreNull(dto, chbEqudet);
        ibd = genIssueDtoEx(ibd);
        EquityBusiInfoRegist001Dto dto2 = new EquityBusiInfoRegist001Dto();
        BeanUtil.copyPropertiesIgnoreNull(dto2, ibd);
        return compareIssueRegData(dto2,dto);
    }

    /**
     * 修改要素对登记数据处理 <一句话功能简述> <功能详细描述>
     *
     * @param detail
     * @return
     */
    public int doUpdateSub(EquiteBusiDetails001Dto detail,EquiteBusiDetails001Dto olddetail) {
        int i = 0;
        List<EquiteBusiDetails001Dto> keyList = new ArrayList<EquiteBusiDetails001Dto>();
        keyList.add(detail);
        List<EquityBusiInfoRegist001Dto> registList = equityBusiDetails001Mapper.selectAllChbEqudetSubData(
                keyList);
        EquityBusiInfoRegist001Dto dto = new EquityBusiInfoRegist001Dto();
        BeanUtil.copyPropertiesIgnoreNull(dto, detail);
        if(dto.getTempMap()!=null){
            for(String key1:dto.getTempMap().keySet()){
                EquityInfoCheckUtil.updateChcekDto(dto,key1,dto.getTempMap().get(key1));
            }
        }
        dto.setCustName(ChbSensitiveInfoUtil.handle汉字变形(dto.getCustName()));
        //查询对应的身份信息
        EquityInfo001Dto equityInfo001Dto = new EquityInfo001Dto();
        equityInfo001Dto.setIndentifyMark(dto.getIndentifyMark());
        equityInfo001Dto.setBankCode(dto.getRegistBankCode());
        equityInfo001Dto.setType("01");
        EquityInfo001Dto equityInfoCheck = equityInfo001Mapper.getEquityInfo001DtoByKey(
                equityInfo001Dto);
        if(equityInfoCheck!=null){
            if(StringUtil.equals(DictCodeListExt2.个人证件类别_居民身份证.getValue(), equityInfoCheck.getPersonalIdentifyType())
                    || StringUtil.equals(DictCodeListExt2.个人证件类别_临时居民身份证.getValue(), equityInfoCheck.getPersonalIdentifyType())){
                String tuominStr = ChbSensitiveInfoUtil.replaceIdNoToMd5(
                        dto.getCustContractNo(),
                        equityInfoCheck.getIdentifyNumber());
                dto.setCustContractNo(tuominStr);
            }
        }

        // 数据日期
        dto.setStatusDate(new Date());
        Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
        List<EquityBusiInfoRegist001Dto> registListnew = new ArrayList<EquityBusiInfoRegist001Dto>();
        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, 1);
        
        if (registList == null || registList.isEmpty()) {
            setSubProperty(dto, DictCodeListExt2.报送数据状态_新建.getValue(), dateMap,0,sequenceList);
            registListnew.add(dto);
            i = equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusiSub(registListnew);
        }
        else if (registList.size() == 1) {
            if (registList.get(0).getSubmitStatus().equals(
            		DictCodeListExt2.报送状态_未报送.getValue())) {
                setSubProperty(dto, DictCodeListExt2.报送数据状态_新建.getValue(), dateMap,0,sequenceList);
                registListnew.add(dto);
                equityBusiInfoRegist001Mapper.deleteBatchEquityBusi(registListnew);
                i = equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusiSub(registListnew);
            }
            else {
                setSubProperty(dto, DictCodeListExt2.报送数据状态_修改.getValue(), dateMap,0,sequenceList);
                EquiteBusiDetails001Dto dto2 = new EquiteBusiDetails001Dto();
                BeanUtil.copyPropertiesIgnoreNull(dto2, registList.get(0));
              //对比数据相同则不继续
                if(docheckIfchangeEx(olddetail,detail)){
                    return 0;
                }
                registListnew.add(dto);
                i = equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusiSub(registListnew);
            }
        }
        else if (registList.size() == 2) {
            setSubProperty(dto, DictCodeListExt2.报送数据状态_修改.getValue(), dateMap,0,sequenceList);
            registListnew.add(dto);
            equityBusiInfoRegist001Mapper.deleteBatchEquityBusi(registListnew);
            i = equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusiSub(registListnew);

        }
        return i;
    }


    void doInsertSubDataWithInfoDto(List<EquiteBusiDetails001Dto> details,Map<String, List<EquityInfo001Dto>> equityInfo001Map) {
        Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
        List<EquityBusiInfoRegist001Dto> registListnew = new ArrayList<EquityBusiInfoRegist001Dto>();
         
		   List<String> sequenceList = null;
				   
		   if(details!=null){
			   sequenceList=  CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, details.size());
		   }
           int index = 0;
        
           //生僻字转化处理   
           //生僻字按照已经维护的规则进行转化
             Map<String, Object> paramap=new HashMap<String, Object>();
             paramap.put("statusList", Arrays.asList("E"));
             List<TransformRule001Dto> allList=transformRule001Mapper.selectAllData(paramap);
             Map<String,TransformRule001Dto> spzMap=allList.stream().collect(
                     Collectors.toMap(x -> x.getTransType()+x.getOrgField(),part->part));
             List<ChbTransformRuleObjDto> allRuleObjList=chbTransformRuleObjDtoMapper.selectAllDataByTableId(CHB_EQUDET);
             Map<String,List<ChbTransformRuleObjDto>> objFiledsMap=allRuleObjList.stream().collect(
                     JoyinCollectors.groupingBy(
                             x -> x.getInfoProperty() ));
             
             //设置数据结构的转化规则字段内容
             BusinessDetailInfoCheckUtil.replaceByExchangeRuleForDtl(allRuleObjList, spzMap, details);   
             
             
          //生僻字转化后设置数据结构              
        for (EquiteBusiDetails001Dto detail : details) {
            EquityBusiInfoRegist001Dto dto = new EquityBusiInfoRegist001Dto();
            
            //用规则转换后的字段替换转化前的字段
            if(detail.getTempMap()!=null){
      	       for(String key:detail.getTempMap().keySet()){
      	           EquityInfoCheckUtil.updateChcekDto(detail,key,detail.getTempMap().get(key));
      	       }
      	    }
            
            BeanUtil.copyPropertiesIgnoreNull(dto, detail);
            dto.setCustName(ChbSensitiveInfoUtil.handle汉字变形(dto.getCustName()));
            //证件类型是身份证或者临时身份证，客户编号脱敏
            String key = dto.getRegistBankCode() + GlobalConstant.VERTICAL_LINE
                    + GlobalConstant.VERTICAL_LINE + dto.getIndentifyMark();
            if (equityInfo001Map.containsKey(key)) {
                EquityInfo001Dto eInfo001Dto = equityInfo001Map.get(key).get(0);
                if(StringUtil.equals(DictCodeListExt2.个人证件类别_居民身份证.getValue(), eInfo001Dto.getPersonalIdentifyType())
                        || StringUtil.equals(DictCodeListExt2.个人证件类别_临时居民身份证.getValue(), eInfo001Dto.getPersonalIdentifyType())){
                    String tuominStr = ChbSensitiveInfoUtil.replaceIdNoToMd5(
                            dto.getCustContractNo(),
                            eInfo001Dto.getIdentifyNumber());
                    dto.setCustContractNo(tuominStr);
                }
            }
            // 数据日期
            dto.setStatusDate(new Date());
            if (detail.getDataStatus().equals(DictCodeListExt2.报送数据状态_删除.getValue())) {
                setSubProperty(dto, DictCodeListExt2.报送数据状态_删除.getValue(), dateMap,index,sequenceList);
            }
            else {
                setSubProperty(dto, DictCodeListExt2.报送数据状态_新建.getValue(), dateMap,index,sequenceList);
            }
            index++;
            if(dto.getTempMap()!=null){
                for(String key1:dto.getTempMap().keySet()){
                    EquityInfoCheckUtil.updateChcekDto(dto,key1,dto.getTempMap().get(key1));
                }
            }
            registListnew.add(dto);
        }
        this.insertChbEqudetSubDataByPart(registListnew);
    }

    /**
     * 接口map数据转换为实体 全量转换
     *
     * @param map 参数
     * @param proRegistCodeManage001Dto 产品登记编码表相关信息
     * @return InterestsBusinessDetailed
     */
    public EquityBusiInfoRegist001Dto dataConversionEntity(Map<String, String> item,
                                                           String sysregcode, String errmsg,
                                                           Map<String, BigDecimal> rateMap) {

        EquityBusiInfoRegist001Dto entity = new EquityBusiInfoRegist001Dto();
        StringBuffer sbf = new StringBuffer();
        // 0 登记银行代码（即发行机构代码）
        String registBankCode = item.get("REGIST_BANK_CODE");
        // excel中的登记银行代码必须与当前导入人的登记银行代码一致!
        entity.setRegistBankCode(registBankCode);
        entity.setOrgCode(item.get("ORG_CODE"));//机构代码

        // 1 销售合同号
        entity.setContractNo(item.get("CONTRACT_NO"));

        // 2 业务流水号（核心交易流水号）
        entity.setBusiSeqno(item.get("BUSI_SEQNO"));

        // 3 理财账号
        entity.setFinAcc(item.get("FIN_ACC"));

        // 4 客户统一编号
        entity.setCustContractNo(item.get("CUST_CONTRACT_NO"));

        // 5 识别标识
        entity.setIndentifyMark(item.get("INDENTIFY_MARK"));

        // 6 客户姓名
        entity.setCustName(item.get("CUST_NAME"));

        // 7 关联活期存款账号
        entity.setFundAcc(item.get("FUND_ACC"));

        // 8 关联账号开户所在地
        entity.setFundAccPlace(item.get("FUND_ACC_PLACE"));

        // 9 是否代销
        String isConsign = item.get("IS_CONSIGN");
        entity.setIsConsign(isConsign);

        // 10 代销机构代码
        String agencyCode = item.get("AGENCY_CODE");
        entity.setAgencyCode(agencyCode);

        // 11 代销机构名称
        String agencyName = item.get("AGENCY_NAME");
        entity.setAgencyName(agencyName);

        // 12 代销机构所属监管机构
        String agencyFsa = item.get("AGENCY_FSA");
        entity.setAgencyFsa(agencyFsa);

        // 13 产品登记编码
        String regCode = item.get("REG_CODE");
        if (StringUtil.isNotEmpty(regCode)) {
            if (sysregcode != null) {
                entity.setRegCode(sysregcode);
            }else{
                entity.setRegCode(regCode);
            }
        }else {
            if (sysregcode != null) {
                entity.setRegCode(sysregcode);
            }else{
                sbf.append(this.getMessage("CHB_EQUITY_CHECKREGCODE1"));
            }
        }

        entity.setAccount(item.get("ACCOUNT"));// 产品代码

        // 14 业务种类
        entity.setBusiType(item.get("BUSI_TYPE"));

        // 15 业务发生地所属监管
        entity.setBusiFsa(item.get("BUSI_FSA"));

        // 16 业务确认日期
        try {
            entity.setEquityConfDate(DateUtil.parse(item.get("EQUITY_CONF_DATE")));
        }
        catch (Exception e) {
            entity.setEquityConfDate(null);
        }
        // 17 业务确认时间
        entity.setShareConfDatetime(item.get("SHARE_CONF_DATETIME"));

        // 18 币种
        String ccy = item.get("CCY");
        entity.setCcy(ccy);

        // 19 金额
        try {
            entity.setAmount(new BigDecimal(item.get("AMOUNT")));
        }
        catch (Exception e) {
            entity.setAmount(null);
        }
        // 20 折算人民币金额
        try {
            entity.setAmountCny(new BigDecimal(item.get("AMOUNT_CNY")));
        }
        catch (Exception e) {
            entity.setAmountCny(null);
        }
        // 折算人民币处理
        String amountCny = item.get("AMOUNT_CNY");
        if (StringUtil.isNotEmpty(ccy) && StringUtil.isNotEmpty(entity.getEquityConfDate())
                && StringUtil.isNotEmpty(entity.getAmount())) {
            if (!StringUtil.isNotEmpty(amountCny)
                    && !StringUtil.equals(ccy, DictCodeListExt1.币种_中债_人民币.getValue())) {
                String key = DateUtil.format(entity.getEquityConfDate()) + ccy;
                if (rateMap.containsKey(key)) {
                    entity.setAmountCny(ArithmeticUtil.multiply(rateMap.get(key),
                            entity.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                else {
                    Map<String, Object> mapObj = new HashMap<String, Object>();
                    // 原币种
                    mapObj.put("CCY_ORIGINAL", entity.getCcy());
                    // 持有日期
                    mapObj.put("HOLDDATE", entity.getEquityConfDate());
                    // 折算币种
                    mapObj.put("CCY_CONVERT", DictCodeListExt1.币种_中债_人民币.getValue());
                    // 汇率计算规则
                    mapObj.put("pricetype",SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE));
                    BigDecimal rate = exchangerate001Mapper.selectNewRate(mapObj);
                    if (rate != null) {
                        entity.setAmountCny(
                                ArithmeticUtil.multiply(rate, entity.getAmount()).setScale(2,
                                        BigDecimal.ROUND_HALF_UP));
                        rateMap.put(key, rate);
                    }
                    else {
                        sbf.append(this.getMessage("CHB_EQUITY_RATE"));
                    }
                }
            }
            else if (!StringUtil.isNotEmpty(amountCny)
                    && StringUtil.equals(ccy, DictCodeListExt1.币种_中债_人民币.getValue())) {
                entity.setAmountCny(entity.getAmount());
            }
        }

        // 21 确认净值
        try {
            entity.setProdValue(new BigDecimal(item.get("PROD_VALUE")));
        }
        catch (Exception e) {
            entity.setProdValue(null);
        }

        // 22 份额
        try {
            entity.setShares(new BigDecimal(item.get("SHARES")));
        }
        catch (Exception e) {
            entity.setShares(null);
        }

        // 23 费用
        try {
            entity.setFeeAmt(new BigDecimal(item.get("FEE_AMT")));
        }
        catch (Exception e) {
            entity.setFeeAmt(null);
        }
        // 24 渠道
        entity.setChannel(item.get("CHANNEL"));

        // 25 交易柜员号
        entity.setTranTellName(item.get("TRAN_TELL_NAME"));

        // 26 备注
        entity.setRemark(item.get("REMARK"));
        entity.setDeptCode(item.get("DEPT_CODE"));// 部门代码
        entity.setCreateDept(item.get("CREATE_DEPT"));// 创建部门
        if (errmsg != null) {
            entity.setInterfCheckResult(errmsg);
        }
        if (sbf.length() > 0) {
            if (entity.getInterfCheckResult() == null) {
                entity.setInterfCheckResult(sbf.toString());
            }
            else {
                entity.setInterfCheckResult(entity.getInterfCheckResult() + sbf.toString());
            }
        }
        if (entity.getInterfCheckResult() == null) {
            entity.setStatus("E");
        }
        else {
            entity.setStatus("A");
        }
//        entity.setCustName(ChbSensitiveInfoUtil.handle生僻字(entity.getCustName()));
        return entity;

    }

    /**
     * 批量插入投资者明细数据
     *
     * @param dataList 业务明细数据
     */
    private void insertChbEqudetDataByPartRegist(List<EquityBusiInfoRegist001Dto> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int maxDataCountOneTime = 50;
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusi(dataList);
                return;
            }
            List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
            newDataList.addAll(dataList);
            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusi(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusi(newDataList);
            }
        }
    }

    /**
     * 批量插入投资者明细数据
     *
     * @param dataList 业务明细数据
     */
    public void insertChbEqudetSubDataByPart(List<EquityBusiInfoRegist001Dto> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int maxDataCountOneTime = 50;
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusiSub(dataList);
                return;
            }
            List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
            newDataList.addAll(dataList);
            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusiSub(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiInfoRegist001Mapper.insertBtachInterfEquityBusiSub(newDataList);
            }
        }
    }

    /**
     * 批量更新投资者明细数据
     *
     * @param dataList 业务明细数据
     */
    private void updateBatchEquityBusiByPart(List<EquityBusiInfoRegist001Dto> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int maxDataCountOneTime = 50;
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.updateBatchEquityBusi(dataList);
                return;
            }
            List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
            newDataList.addAll(dataList);
            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.updateBatchEquityBusi(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiInfoRegist001Mapper.updateBatchEquityBusi(newDataList);
            }
        }
    }

    /**
     * 批量插入投资者明细HIS数据
     *
     * @param dataList 业务明细数据
     */
    protected void insertEquityBusiHisByPart(List<EquityBusiInfoRegist001Dto> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int maxDataCountOneTime = 50;
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.insertEquityBusiHis(dataList);
                return;
            }
            List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
            newDataList.addAll(dataList);
            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.insertEquityBusiHis(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiInfoRegist001Mapper.insertEquityBusiHis(newDataList);
            }
        }
    }

    /**
     * 批量删除未报送的投资者明细登记数据
     *
     * @param dataList 业务明细数据
     */
    private void deleteBatchEquityBusiByPart(List<EquityBusiInfoRegist001Dto> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int maxDataCountOneTime = 50;
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.deleteBatchEquityBusi(dataList);
                return;
            }
            List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
            newDataList.addAll(dataList);
            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.deleteBatchEquityBusi(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiInfoRegist001Mapper.deleteBatchEquityBusi(newDataList);
            }
        }
    }

    public void deleteImportTmpData() {
        equityBusiDetails001Mapper.deleteTmpTable();
    }

    /**
     * 根据所有有三要素合并身份信息获取合并后识别标识
     * @param item
     * @param equityInfocheckMap
     */
    public void setOrgIndentifyMark(EquiteBusiDetails001Dto item, Map<String, ChbEquHoldCheckDto> equityInfocheckMap) {
        if (equityInfocheckMap.containsKey(item.getIndentifyMark())) {
            item.setOrgIndentifyMark(item.getIndentifyMark());
            item.setIndentifyMark(
                    equityInfocheckMap.get(item.getIndentifyMark()).getMergerindentifymark());
        }
    }

    public Map<String, String> getAllIndentifyInfo(){
        Map<String, String> resMap = new HashMap<String, String>();
        // 查询出所有身份信息
        List<EquityInfo001Dto> allInfoList = new ArrayList<EquityInfo001Dto>();
        allInfoList=equityBusiDetails001Mapper.getAllChbEquInfoOneTime();
        Map<String, String> equityInfo001Map = allInfoList.stream().collect(
                Collectors.toMap(x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE +
                        x.getIndentifyMark(), x -> x.getIndentifyMark(),(k1, k2) -> k1));
//            JoyinCollectors.groupingBy(x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + x.getIndentifyMark()));
        resMap.putAll(equityInfo001Map);
        // 查询出所有有三要素合并身份信息
        List<ChbEquHoldCheckDto> allcheckList = equityBusiInfoRegist001Mapper.selectAllCheckEquHoldDto();
        Map<String, String> equityInfocheckMap = allcheckList.stream().collect(
                Collectors.toMap(x -> x.getRegistBankCode() + GlobalConstant.VERTICAL_LINE +
                        x.getIndentifyMark(), x -> x.getMergerindentifymark(),(k1, k2) -> k1));
//            Collectors.toMap(ChbEquHoldCheckDto::getIndentifyMark, ChbEquHoldCheckDto::getMergerindentifymark, (k1, k2) -> k1));
        resMap.putAll(equityInfocheckMap);
        return resMap;
    }
    public boolean compareIssueRegData(EquityBusiInfoRegist001Dto dto,EquityBusiInfoRegist001Dto regdto){
        EquityBusiInfoRegist001Dto compare1 = genIssueDto(dto);
        EquityBusiInfoRegist001Dto compare2 = genIssueDto(regdto);
        if(JSON.toJSONString(compare1).equals(JSON.toJSONString(compare2))){
            return true;
        }else{
            return false;
        }
    }
    private EquityBusiInfoRegist001Dto genIssueDto(EquityBusiInfoRegist001Dto dto){
        EquityBusiInfoRegist001Dto gendto=new EquityBusiInfoRegist001Dto();
        gendto.setEquityConfDate(dto.getEquityConfDate());
        gendto.setShareConfDatetime(lengthZeroString(dto.getShareConfDatetime()));
        gendto.setFundAccPlace(lengthZeroString(dto.getFundAccPlace()));
        gendto.setBusiType(lengthZeroString(dto.getBusiType()));
        gendto.setCcy(dto.getCcy());
        gendto.setAmount(dto.getAmount());
        gendto.setAmountCny(dto.getAmountCny());
        gendto.setShares(dto.getShares());
        gendto.setChannel(lengthZeroString(dto.getChannel()));
        gendto.setFundAcc(lengthZeroString(dto.getFundAcc()));
        gendto.setIsConsign(lengthZeroString(dto.getIsConsign()));
        gendto.setRemark(lengthZeroString(dto.getRemark()));
        gendto.setContractNo(lengthZeroString(dto.getContractNo()));
        gendto.setFinAcc(lengthZeroString(dto.getFinAcc()));
        gendto.setCustContractNo(lengthZeroString(dto.getCustContractNo()));
        gendto.setCustName(lengthZeroString(dto.getCustName()));
        gendto.setAgencyCode(lengthZeroString(dto.getAgencyCode()));
        gendto.setAgencyName(lengthZeroString(dto.getAgencyName()));
        gendto.setAgencyFsa(lengthZeroString(dto.getAgencyFsa()));
        gendto.setBusiFsa(lengthZeroString(dto.getBusiFsa()));
        gendto.setProdValue(dto.getProdValue());
        gendto.setFeeAmt(dto.getFeeAmt());
        gendto.setTranTellName(lengthZeroString(dto.getTranTellName()));
        gendto.setCreateTime(null);
        gendto.setUpdateTime(null);
        return gendto;
    }
    private EquiteBusiDetails001Dto genIssueDtoEx(EquiteBusiDetails001Dto dto){
        dto.setShareConfDatetime(lengthZeroString(dto.getShareConfDatetime()));
        dto.setFundAccPlace(lengthZeroString(dto.getFundAccPlace()));
        dto.setBusiType(lengthZeroString(dto.getBusiType()));
        dto.setChannel(lengthZeroString(dto.getChannel()));
        dto.setFundAcc(lengthZeroString(dto.getFundAcc()));
        dto.setIsConsign(lengthZeroString(dto.getIsConsign()));
        dto.setRemark(lengthZeroString(dto.getRemark()));
        dto.setContractNo(lengthZeroString(dto.getContractNo()));
        dto.setFinAcc(lengthZeroString(dto.getFinAcc()));
        dto.setCustContractNo(lengthZeroString(dto.getCustContractNo()));
        dto.setCustName(lengthZeroString(dto.getCustName()));
        dto.setAgencyCode(lengthZeroString(dto.getAgencyCode()));
        dto.setAgencyName(lengthZeroString(dto.getAgencyName()));
        dto.setAgencyFsa(lengthZeroString(dto.getAgencyFsa()));
        dto.setBusiFsa(lengthZeroString(dto.getBusiFsa()));
        dto.setTranTellName(lengthZeroString(dto.getTranTellName()));
        return dto;
    }
    private String lengthZeroString(String org){
        if(org!=null){
            if(org.length()==0){
                return null;
            }else{
                return org.trim();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildExportParam(ExportParam exportParam) {
        Map<String, Object> pageParam = exportParam.getSelectParam();

        // 设置查询参数
        dealParamMap(pageParam);

        String flag = (String)pageParam.get("flag");
        String configFile = "/com/joyintech/tams/report/submitmaintenance/businessdetailed/conf/export_conf.xml";

        exportParam.config(configFile).select(
                () -> equityBusiDetails001Mapper.queryForPage(pageParam));

    }

    @Override
    protected void beforeCreateExcel(List<EquiteBusiDetails001Dto> contents) {
        String flag = (String) RequestUtil.getPageParam().get("flag");
        if (StringUtil.isNotEmpty(flag) && StringUtil.equals(flag, "0") ) {
            dealReturnInfo(contents);
        }
    }
    public List<EquiteBusiDetails001Dto> selectBusiFeedBackData(Map<String, Object> map){
        List<EquiteBusiDetails001Dto> result= equityBusiDetails001Mapper.selectBusiFeedBackData(map);
        dealReturnInfo(result);
        return result;
    }
    public List<EquiteBusiDetails001Dto> selectAllBusiFeedBackData(Map<String, Object> map){
        List<EquiteBusiDetails001Dto> result= equityBusiDetails001Mapper.selectAllBusiFeedBackData(map);
        dealReturnInfo(result);
        return result;
    }
    //add by dengyu 20201022 监管要求投资者明细修改 begin
    /**
     * 根据最新导入的有效的产品登记编码数据，联动处理投资者明细信息
     *
     * @param prdresistCodeList
     *            最新导入的有效的产品登记编码数据
     * @see
     * @since 1.0
     */
    public void refreshStatusAData(List<ChbPrdresistCode> prdresistCodeList, String bankCode, String deptCode, String orgCode) {
        if (StringUtil.isEmptyList(prdresistCodeList)) {
            return;
        }

        for (ChbPrdresistCode chbPrdresistCode : prdresistCodeList) {
            logger.info("checkDataInfo处理"+chbPrdresistCode.getProdid()+"开始 " + DateUtil.getCurrentDateTime());
            // 根据产品代码和机构代码，查询持有者信息有效数据
            Map<String, Object> paramMap1 = new HashMap<String, Object>();
            paramMap1.put("prodId", chbPrdresistCode.getProdid());
            paramMap1.put("orgCode", orgCode);
            paramMap1.put("regCode", chbPrdresistCode.getRegCode());
            //修改重新更新有效的产品登记编码代码 start
            if ("01".equals(CacheUtil.getSysParam(ReportGlobalConstant.SYS_PARA_REP_IS_COVER))) {
                equityBusiDetails001Mapper.updateInfoRegCodeByProdid(paramMap1);
                equityBusiDetails001Mapper.updateRegistRegCodeByProdid(paramMap1);
            }
            //修改重新更新有效的产品登记编码代码 end
            // 根据产品代码和机构代码，查询持有者信息待处理数据（待处理，且产品登记编码为空）
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("prodId", chbPrdresistCode.getProdid());
            paramMap.put("orgCode", orgCode);
            paramMap.put("isCover", CacheUtil.getSysParam(ReportGlobalConstant.SYS_PARA_REP_IS_COVER));

            List<EquiteBusiDetails001Dto>statusAEqudetData=equityBusiDetails001Mapper.selectStatusAEqudetData(paramMap);

            DataCheckService dataCheckService = SpringContextUtil.getBean(DataCheckService.class);
            List<CheckRule> listRules = dataCheckService.queryCheckRules(ReportGlobalConstant.COMMON_CHECK_DATA_TYPE_EQUITY_DETAILS);
            if (!StringUtil.isEmptyList(statusAEqudetData)) {

                Map<String, String>dataMap=new HashMap<String, String>();

                // 定义已存在数据list-原数据“待处理”，新接入数据是“待处理”
                List<EquityBusiInfoRegist001Dto> listAA = new ArrayList<>();
                // 定义已存在数据list-原数据“待处理”，新接入数据是“有效”
                List<EquityBusiInfoRegist001Dto> listAE = new ArrayList<>();

                EquityBusiInfoRegist001Dto ibd = null;

                List<EquityInfo001Dto> allInfoList = new ArrayList<EquityInfo001Dto>();
                allInfoList=equityBusiDetails001Mapper.getAllChbEquInfoOneTime();
                Map<String, List<EquityInfo001Dto>> equityInfo001Map = allInfoList.stream().collect(
                        JoyinCollectors.groupingBy(
                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE + GlobalConstant.VERTICAL_LINE
                                        + x.getIndentifyMark()));
                Map<String, BigDecimal> rateMap = new HashMap<>();
//                //modify by yanya on 20210521 批量获取索引，避免死锁
//                List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, statusAEqudetData.size());
//                int index = 0;
                for(EquiteBusiDetails001Dto equiteBusiDetails001Dto:statusAEqudetData){
                    List<Map<String, String>> dataMapList = new ArrayList<Map<String, String>>();
                    equiteBusiDetails001Dto.setRegCode(chbPrdresistCode.getRegCode());
                    dataMap=this.dtoToMap(equiteBusiDetails001Dto);

                    Map<String, String> checkmap = new HashMap<String, String>();
                    for (String key : dataMap.keySet()) {
                        if (key != null)
                            checkmap.put(CommonUtil.dealString(key, true), dataMap.get(key));
                    }


                    dataMapList.add(checkmap);

                    DataCheckService.checkDataList3(dataMapList, listRules);

                    String regcode=chbPrdresistCode.getRegCode();

                    // 投资者信息数据验证及赋值
                    ibd = dataConversionEntity(dataMap, regcode,dataMapList.get(0).get("ERRMSG"),
                            rateMap);

                    ibd.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                    ibd.setUpdateTime(DateUtil.now());

                    if ("A".equals(ibd.getStatus())) {
                        listAA.add(ibd);
                    } // 原数据“待处理”，新接入数据是“有效”的处理
                    else if ("E".equals(ibd.getStatus())) {
                       ibd.setSumitSeq(
                               CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                        ibd.setSumitSeq(getSequenceStr(sequenceList, index));
//                        index++;
                       listAE.add(ibd);
                    }
                }
                if (listAA != null && listAA.size() > 0) {
                    // 插入维护信息表
                    updateBatchEquityBusiRegCodeByPart(listAA);
                }
                // 原数据为待处理,并且新数据状态为有效，直接更新维护表，并入登记表
                if (listAE != null && listAE.size() > 0) {
                    // 插入维护信息表
                    updateBatchEquityBusiRegCodeByPart(listAE);
                    // 插入登记表
                    changeStatusValue(listAE, false, null, equityInfo001Map);
                    insertChbEqudetSubDataByPart(listAE);
                }

            }

            logger.info("checkDataInfo处理"+chbPrdresistCode.getProdid()+"结束 " + DateUtil.getCurrentDateTime());
        }
    }


    private Map<String, String> dtoToMap(EquiteBusiDetails001Dto equiteBusiDetails001Dto){
        Map<String, String> dataMap = new HashMap<String, String>();
        if (equiteBusiDetails001Dto == null) {
            return dataMap;
        }
        dataMap.put("BUSI_SEQNO",equiteBusiDetails001Dto.getBusiSeqno());
        dataMap.put("CONTRACT_NO",equiteBusiDetails001Dto.getContractNo());
        dataMap.put("REGIST_BANK_CODE",equiteBusiDetails001Dto.getRegistBankCode());
        dataMap.put("ORG_CODE",equiteBusiDetails001Dto.getOrgCode());
        dataMap.put("DEPT_CODE",equiteBusiDetails001Dto.getDeptCode());
        dataMap.put("CREATE_DEPT", equiteBusiDetails001Dto.getCreateDept());
        dataMap.put("FIN_ACC",equiteBusiDetails001Dto.getFinAcc());
        dataMap.put("CUST_CONTRACT_NO",equiteBusiDetails001Dto.getCustContractNo());
        dataMap.put("INDENTIFY_MARK",equiteBusiDetails001Dto.getIndentifyMark());
        dataMap.put("CUST_NAME",equiteBusiDetails001Dto.getCustName());
        dataMap.put("FUND_ACC",equiteBusiDetails001Dto.getFundAcc());
        dataMap.put("FUND_ACC_PLACE",equiteBusiDetails001Dto.getFundAccPlace());
        dataMap.put("IS_CONSIGN",equiteBusiDetails001Dto.getIsConsign());
        dataMap.put("AGENCY_CODE",equiteBusiDetails001Dto.getAgencyCode());
        dataMap.put("AGENCY_NAME",equiteBusiDetails001Dto.getAgencyName());
        dataMap.put("AGENCY_FSA",equiteBusiDetails001Dto.getAgencyFsa());
        dataMap.put("ACCOUNT",equiteBusiDetails001Dto.getAccount());
        dataMap.put("REG_CODE",equiteBusiDetails001Dto.getRegCode());
        dataMap.put("BUSI_FSA",equiteBusiDetails001Dto.getBusiFsa());
        dataMap.put("BUSI_TYPE",equiteBusiDetails001Dto.getBusiType());
        dataMap.put("EQUITY_CONF_DATE",DateUtil.format(equiteBusiDetails001Dto.getEquityConfDate(),"yyyyMMdd"));
        dataMap.put("SHARE_CONF_DATETIME",equiteBusiDetails001Dto.getShareConfDatetime());
        dataMap.put("CCY",equiteBusiDetails001Dto.getCcy());
        if(equiteBusiDetails001Dto.getAmount()!=null){
            dataMap.put("AMOUNT",equiteBusiDetails001Dto.getAmount().toString());
        }
        if(equiteBusiDetails001Dto.getAmountCny()!=null){
            dataMap.put("AMOUNT_CNY",equiteBusiDetails001Dto.getAmountCny().toString());
        }
        if(equiteBusiDetails001Dto.getProdValue()!=null){
            dataMap.put("PROD_VALUE",equiteBusiDetails001Dto.getProdValue().toString());
        }
        if(equiteBusiDetails001Dto.getShares()!=null){
            dataMap.put("SHARES",equiteBusiDetails001Dto.getShares().toString());
        }
        if(equiteBusiDetails001Dto.getFeeAmt()!=null){
            dataMap.put("FEE_AMT",equiteBusiDetails001Dto.getFeeAmt().toString());
        }
        dataMap.put("CHANNEL",equiteBusiDetails001Dto.getChannel());
        dataMap.put("TRAN_TELL_NAME",equiteBusiDetails001Dto.getTranTellName());
        return dataMap;
    }


    private void updateBatchEquityBusiRegCodeByPart(List<EquityBusiInfoRegist001Dto> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int maxDataCountOneTime = 50;
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.updateBatchEquityBusiRegCodeByPart(dataList);
                return;
            }
            List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
            newDataList.addAll(dataList);
            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.updateBatchEquityBusiRegCodeByPart(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiInfoRegist001Mapper.updateBatchEquityBusiRegCodeByPart(newDataList);
            }
        }
    }


    //add by dengyu 20201022 监管要求投资者明细修改 end

    /**
     * 获取序列
     * @param list
     * @param index
     * @return
     */
    public String getSequenceStr(List<String> list, int index) {
        if(StringUtil.isEmptyList(list) || index >= list.size()) {
            return null;
        }
        else {
            return list.get(index);
        }
    }
}