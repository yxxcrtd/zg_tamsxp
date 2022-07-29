/*
 * 文件名：InvestorsHoldInfoRegist001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管业务监管报送平台
 * 修改人：zuoyinyi
 * 修改时间：2018年5月8日
 * 修改内容：新建
 * 系统名称：理财资管业务监管报送平台
 */

package com.joyintech.tams.report.submitmanagement.equitybusiinforegist.service;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbBatchParaMapper;
import com.joyintech.tams.common.base.entity.ChbBatchPara;
import com.joyintech.tams.common.base.entity.ChbSubmitStatus;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.cnbond.dto.CnBondInterfItem;
import com.joyintech.tams.report.interf.cnbond.dto.InterfCnBondSubmitPara;
import com.joyintech.tams.report.interf.cnbond.service.CnBondInterfService;
import com.joyintech.tams.report.queryanalysis.equitysharecheck.dao.EquityShareCheck001Mapper;
import com.joyintech.tams.report.queryanalysis.equitysharecheck.dto.EquityShareCheck001Dto;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dto.EquityBusiInfoRegist001Dto;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 投资者明细信息登记service
 *
 * @author yuluan
 * @version 1.0 2019年11月11日
 * @see EquityBusiInfoRegist001Service
 * @since 1.0
 */
@Service("svrEquityBusiInfoRegist001Service")
@Transactional(rollbackFor = Exception.class)
public class EquityBusiInfoRegist001Service extends CommonExportService<EquityBusiInfoRegist001Dto> {

    /**
     * 投资者明细信息登记mapper
     */
    @Autowired
    private EquityBusiInfoRegist001Mapper equityBusiInfoRegist001Mapper;

    /**
     * 报送前检查mapper
     */
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;

    @Autowired
    private ChbBatchParaMapper chbBatchParaMapper;

    @Autowired
    private SqlSessionTemplate template;
    @Autowired
    private CnBondInterfService cnBondInterfService;
    @Autowired
    private EquityShareCheck001Mapper equityShareCheck001Mapper;

    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public String reportInsertLongCount;

    /**
     * sql最大一次最大删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public String reportSelectOrDeleteCount;

    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportInsertCount}")
    public String reportInsertCount;

    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportSelectOrDeleteCountForShenFen}")
    public int reportSelectOrDeleteCountForShenFen;

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
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrDataCount}")
    private int reportInterfOneTimeSqlLdrDataCount;

    /**
     * 上游接口解析读取文件处理,单次sqlldr处理最大数据条数
     */
    @Value("${application.reportInterfOneTimeSqlLdrCommitCount}")
    private int reportInterfOneTimeSqlLdrCommitCount;

    /**
     * sqlldr用数据库连接
     */
    @Value("${spring.datasource.reportSqlldrDatabaseName}")
    private String reportSqlldrDatabaseName;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询页面数据
     *
     * @param pageParam 查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    public Page<EquityBusiInfoRegist001Dto> selectForPage(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        // 将发送状态改为空
        pageParam.put("sendStatus", "");
        int pagenum = StringUtil.integerValueOf(""+pageParam.get(GlobalConstant.PARAM_PAGE), 1);
        int length = StringUtil.integerValueOf(""+pageParam.get(GlobalConstant.PARAM_LENGTH), GlobalConstant.DEFAULT_PAGE_SIZE);
        Page<EquityBusiInfoRegist001Dto> page = queryForPage(pagenum,length,()->equityBusiInfoRegist001Mapper.selectAllData(pageParam));
        dealDataList(page.getRoot(), false);
        return page;
    }
    /**
     * 查询数据(无分页)
     *
     * @param pageParam 查询条件
     * @return 结果列表
     * @see
     * @since 1.0
     */
    public List<EquityBusiInfoRegist001Dto> queryEquityBusiInfoRegist(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        // 将发送状态改为空
        List<EquityBusiInfoRegist001Dto> list =  equityBusiInfoRegist001Mapper.selectAllData(pageParam);
        dealDataList(list, false);
        return list;
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
                List<EquityBusiInfoRegist001Dto> investList = JsonUtils.json2List(keyList,
                        EquityBusiInfoRegist001Dto.class);
                pageParam.put("investList", investList);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        else {
            // 查询参数条件
            // 数据状态
            String status = (String)pageParam.get("status");
            // 报送状态
            String sendStatus = (String)pageParam.get("sendStatus");
            // 报送状态
            String orgCode = (String)pageParam.get("orgCode");
            // 反馈状态
            String submitFeedbackStatus = (String)pageParam.get("submitFeedbackStatus");
            // 产品
            String prdCode = (String)pageParam.get("prdCode");
            // 产品登记编码
            String prdRegistCode = (String)pageParam.get("prdRegistCode");
            // 发送状态
            String submitSendStatus = (String)pageParam.get("submitSendStatus");
            String busiTypes = (String)pageParam.get("busiTypes");

            // 渠道
            String channel = (String)pageParam.get("channel");

            // 将数据状态当做多选下拉框处理
            if (StringUtil.isNotBlank(status)) {
                pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            }
            // 将报送状态当做多选下拉框处理
            if (StringUtil.isNotBlank(sendStatus)) {
                pageParam.put("sendStatusList",
                        StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
            }
            // 将反馈状态当做多选下拉框处理
            if (StringUtil.isNotBlank(submitFeedbackStatus)) {
                pageParam.put("submitFeedbackStatusList",
                        StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
            }
            // 将机构代码当做多选下拉框处理
            if (StringUtil.isNotBlank(orgCode)) {
                pageParam.put("orgCodeList",
                        StringUtil.splitToList(GlobalConstant.COMMA, orgCode));
            }
            if (StringUtil.isNotBlank(busiTypes)) {
                pageParam.put("busiTypeList",
                        StringUtil.splitToList(GlobalConstant.COMMA, busiTypes));
            }
            // 将产品当做多选下拉框处理
            if (StringUtil.isNotBlank(prdCode)) {
                pageParam.put("prdCodeList",
                        StringUtil.splitToList(GlobalConstant.COMMA, prdCode));
            }
            // 将产品登记编码当做多选下拉框处理
            if (StringUtil.isNotBlank(prdRegistCode)) {
                pageParam.put("prdRegistCodeList",
                        StringUtil.splitToList(GlobalConstant.COMMA, prdRegistCode));
            }
            // 将发送状态当做多选下拉框处理
            if (StringUtil.isNotBlank(submitSendStatus)) {
                pageParam.put("submitSendStatusList",
                        StringUtil.splitToList(GlobalConstant.COMMA, submitSendStatus));
            }

            // 将渠道当做多选下拉框处理
            if (StringUtil.isNotBlank(channel)) {
                pageParam.put("channelList",
                        StringUtil.splitToList(GlobalConstant.COMMA, channel));
            }
        }
    }

    /**
     * 处理查询结果 <功能详细描述>
     *
     * @param list
     *            待处理数据
     * @param isExport
     *            是否是导出
     * @see
     * @since 1.0
     */
    public void dealDataList(List<EquityBusiInfoRegist001Dto> list, boolean isExport) {
        list.forEach(item -> {
//			item.setSendStatusCode(item.getSubmitStatus());
//			item.setStatusCode(item.getStatus());
//			item.setCcyCode(item.getCcy());
//			item.setStatus(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
            item.setSubmitStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送状态, item.getSubmitStatus()));

            item.setBusiType(SysDataUtil.getZZDictCodeAndName(DictCode.业务种类, item.getBusiType()));
            item.setFundAccPlace(SysDataUtil.getZZDictCodeAndName(DictCode.销售区域_中债, item.getFundAccPlace()));
            item.setStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
            item.setChannel(SysDataUtil.getZZDictCodeAndName(DictCode.投资者明细渠道,item.getChannel()));
            item.setIsConsign(
                    SysDataUtil.getZZDictCodeAndName(DictCode.是否_中债, item.getIsConsign()));
            item.setAgencyFsa(SysDataUtil.getZZDictCodeAndName(DictCode.监管机构,item.getAgencyFsa()));
            item.setBusiFsa(SysDataUtil.getZZDictCodeAndName(DictCode.监管机构2,item.getBusiFsa()));
            item.setEquityConfDateStr(DateUtil.format(item.getEquityConfDate(), "yyyyMMdd"));
        });
    }

    /**
     * 处理查询结果 <功能详细描述>
     *
     * @param list
     *            待处理数据
     * @param isExport
     *            是否是导出
     * @see
     * @since 1.0
     */
    private void dealZZDataList(List<EquityBusiInfoRegist001Dto> list, boolean isExport) {
        list.forEach(item -> {
            item.setFundAccPlace(SysDataUtil.getZZDictCodeAndName(DictCode.销售区域_中债, item.getFundAccPlace()));
            item.setIsConsign(
                    SysDataUtil.getZZDictCodeAndName(DictCode.是否_中债, item.getIsConsign()));
            item.setAgencyFsa(SysDataUtil.getZZDictCodeAndName(DictCode.监管机构,item.getAgencyFsa()));
            item.setBusiFsa(SysDataUtil.getZZDictCodeAndName(DictCode.监管机构2,item.getBusiFsa()));
            item.setBusiType(SysDataUtil.getZZDictCodeAndName(DictCode.业务种类, item.getBusiType()));
            item.setChannel(SysDataUtil.getZZDictCodeAndName(DictCode.投资者明细渠道,item.getChannel()));
            item.setEquityConfDateStr(DateUtil.format(item.getEquityConfDate(), "yyyyMMdd"));
        });
    }
    private void dealBigDecmailData(List<EquityBusiInfoRegist001Dto> list) {
        list.forEach(item -> {
            item.setAmountExportExcel((item.getAmount()==null?null:item.getAmount().setScale(2,RoundingMode.HALF_UP).toString()));
            item.setAmountCnyExportExcel((item.getAmountCny()==null?null:item.getAmountCny().setScale(2,RoundingMode.HALF_UP).toString()));
            item.setFeeAmtExportExcel((item.getFeeAmt()==null?null:item.getFeeAmt().setScale(2,RoundingMode.HALF_UP).toString()));
            item.setProdValueExportExcel((item.getProdValue()==null?null:item.getProdValue().setScale(5,RoundingMode.HALF_UP).toString()));
            item.setSharesExportExcel((item.getShares()==null?null:item.getShares().setScale(5,RoundingMode.HALF_UP).toString()));
        });
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildExportParam(ExportParam exportParam) {
        Map<String, Object> pageParam = exportParam.getSelectParam();
        String pageIds = (String)pageParam.get("pageIds");
        if (StringUtil.isNotEmpty(pageIds)) {
            List<String> pageNames = Arrays.asList(pageIds.split(GlobalConstant.COMMA));
            pageParam.replace("pageIds", pageIds, pageNames);
        }
        else {
            pageParam.replace("pageIds", pageIds, null);
        }

        // 设置查询参数
        dealSearchParam(pageParam);

        String flag = (String)pageParam.get("flag");
        String configFile = null;
        // 导出中债excel
        if (StringUtil.equals("0", flag)) {
            configFile = "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf.xml";
        }
        else if (StringUtil.equals("1", flag)) {
            configFile = "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf_all.xml";
        }
        else if (StringUtil.equals("2", flag)) {
            configFile = "/com/joyintech/tams/report/submitmanagement/equitybusiinforegist/conf/export_conf_del.xml";
        }

        exportParam.config(configFile).select(
                () -> equityBusiInfoRegist001Mapper.selectAllData(pageParam));

    }

    @Override
    protected void beforeCreateExcel(List<EquityBusiInfoRegist001Dto> contents) {
        String flag = (String) RequestUtil.getPageParam().get("flag");
        if (StringUtil.isNotEmpty(flag) && !StringUtil.equals(flag, "0") & !StringUtil.equals(flag, "2")) {
            dealDataList(contents, true);
        }
        if (StringUtil.isNotEmpty(flag) && StringUtil.equals(flag, "0") ) {
            dealZZDataList(contents, true);
            dealBigDecmailData(contents);
        }
        //投资者明细删除模板升级---增加业务时间字段
        if (StringUtil.isNotEmpty(flag) && StringUtil.equals(flag, "2") ) {
            contents.forEach(item -> {
                item.setEquityConfDateStr(DateUtil.format(item.getEquityConfDate(), "yyyyMMdd"));
            });
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

    @SuppressWarnings("unchecked")
    public List<EquityBusiInfoRegist001Dto> selectAllList(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        List<EquityBusiInfoRegist001Dto> dataList = (List<EquityBusiInfoRegist001Dto>)pageParam.get(
                "investList");// 查询参数列表

        List<EquityBusiInfoRegist001Dto> dtoList = new ArrayList<EquityBusiInfoRegist001Dto>();// 存放查询结果集
        if (CollectionUtils.isNotEmpty(dataList)) {
            dtoList = equityBusiInfoRegist001Mapper.selectAllDataByKey(dataList);
        }
        else {
            dtoList = equityBusiInfoRegist001Mapper.selectAllData(pageParam);
        }

        // if (StringUtil.isNotEmpty(pageParam.get("flag"))
        // && !StringUtil.equals(pageParam.get("flag"), "0") &
        // !StringUtil.equals(pageParam.get("flag"), "2")) {
        // dealDataList(dtoList, true);
        // }
        return dtoList;
    }
    /**
     * 导出功能查询
     *
     * @param pageParam 查询参数
     * @return 结果集
     * @see
     * @since 1.0
     */

    @SuppressWarnings({"rawtypes"})
    public List<EquityBusiInfoRegist001Dto> selectAllListEx(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        List<EquityBusiInfoRegist001Dto> dtoList =new ArrayList<EquityBusiInfoRegist001Dto>();
        template.select("com.joyintech.tams.report.submitmanagement.equitybusiinforegist.dao.EquityBusiInfoRegist001Mapper.selectAllData",pageParam,
                new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        EquityBusiInfoRegist001Dto pos = (EquityBusiInfoRegist001Dto) resultContext.getResultObject();
                        dtoList.add(pos);
                    }
                });
//            dtoList = equityBusiInfoRegist001Mapper.selectAllData(pageParam);
        return dtoList;
    }
    /**
     * 通过主键查询要导出的登记数据
     *
     * @param expKeys 登记表主键
     * @return 权益人登记信息List
     * @see
     * @since 1.0
     */
    public List<EquityBusiInfoRegist001Dto> queryExportSelectRegistList(String expKeys) {
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, expKeys);
        List<EquityBusiInfoRegist001Dto> equityBusiInfoRegistList = new ArrayList<EquityBusiInfoRegist001Dto>();
        EquityBusiInfoRegist001Dto EquityBusiInfoRegist001Dto = null;
        for (String string : keys) {
            EquityBusiInfoRegist001Dto = new EquityBusiInfoRegist001Dto();
            String[] key = StringUtil.split(string, "|");
            EquityBusiInfoRegist001Dto.setBusiSeqno(key[0]);
            EquityBusiInfoRegist001Dto.setRegCode(key[1]);
            EquityBusiInfoRegist001Dto.setRegistBankCode(key[2]);
            EquityBusiInfoRegist001Dto.setIndentifyMark(key[3]);
            EquityBusiInfoRegist001Dto.setSubmitStatus(key[4]);
            equityBusiInfoRegistList.add(EquityBusiInfoRegist001Dto);
        }
        List<EquityBusiInfoRegist001Dto> pageList = new ArrayList<EquityBusiInfoRegist001Dto>();
        int maxDataCountOneTime = 500;
        int dataCount = equityBusiInfoRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            pageList.addAll(
                    equityBusiInfoRegist001Mapper.selectAllDataByKey(equityBusiInfoRegistList));
            return pageList;
        }

        List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
        newDataList.addAll(equityBusiInfoRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            pageList.addAll(equityBusiInfoRegist001Mapper.selectAllDataByKey(
                    newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            pageList.addAll(equityBusiInfoRegist001Mapper.selectAllDataByKey(newDataList));
        }
        // EquityInfoRegistCommonUtil.format(pageList," ");
        return pageList;
    }

    /**
     * 查询所有数据状态为删除且报送状态为未报送的数据
     *
     * @return 导出数据List
     * @see
     * @since 1.0
     */
    public Map<String, Object> selectDeletedAndUnsubmittedData() {
        List<EquityBusiInfoRegist001Dto> dataList = equityBusiInfoRegist001Mapper.selectDeletedAndUnsubmittedData();
        String ss = JsonUtils.object2Json(dataList);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("flag", "02");

        map.put("keyList", ss);

        return map;
    }

    /**
     * 报送前检查
     *
     * @param checkKeys 检查数据的关键字
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> sendBeforeCheck(Map<String, Object> map) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        // 查询出所有已报送的投资者身份信息
        Map<String, String> indentifyMarkMap = equityBusiInfoRegist001Mapper.getAllRegistBankCodeSet().stream().collect(
                JoyinCollectors.toMap(x -> x.get("INDENTIFY_MARK"), x -> x.get("ORG_CODE")));
        dealSearchParam(map);
        // 权益人报送表主键
        @SuppressWarnings("unchecked")
        List<EquityBusiInfoRegist001Dto> investorsHoldInfoRegistList = (List<EquityBusiInfoRegist001Dto>)map.get(
                "investList");
        if (investorsHoldInfoRegistList == null || investorsHoldInfoRegistList.isEmpty()) {
            investorsHoldInfoRegistList = equityBusiInfoRegist001Mapper.selectAllData(map);
        }
        // 将检查结果入库
        List<CheckReport001Dto> resultList = new ArrayList<CheckReport001Dto>();
        List<EquityBusiInfoRegist001Dto> egist001DtoList = new ArrayList<EquityBusiInfoRegist001Dto>();
        List<String> logicCheckResult = new ArrayList<>();
        Integer i = 1;
        for (EquityBusiInfoRegist001Dto needCheck : investorsHoldInfoRegistList) {
            ExcelValidDto excelValidDto = new ExcelValidDto();
            String validKey = "核心交易流水号:" + needCheck.getBusiSeqno() + GlobalConstant.COMMA
                    + "产品登记编码:" + needCheck.getRegCode() + GlobalConstant.COMMA
                    + "登记银行代码:" + needCheck.getRegistBankCode() + GlobalConstant.COMMA
                    + "识别标识:" + needCheck.getIndentifyMark() + GlobalConstant.COMMA;

            excelValidDto.setValidKey(validKey);
            excelValidDto.setRowNum(String.valueOf(i));
            excelValidDto.setDataTime(needCheck.getUpdateTime());
            excelValidDto.setIsLogicCheck(DictCodeList.是否_是.getValue());
            excelValidDto.setOrgCode(needCheck.getOrgCode());
            excelValidDto.setCreateDept(needCheck.getCreateDept());
            if (indentifyMarkMap.containsKey(needCheck.getIndentifyMark())) {
                excelValidDto.setCheckResult("01");
            }
            else {
                excelValidDto.setCheckResult("02");
                excelValidDto.setErrorInfo("没有对应已报送的身份信息！");
            }
            i++ ;
            checkResult.add(excelValidDto);

            CheckReport001Dto checkReport001Dto = new CheckReport001Dto();
            if (needCheck.getCheckSeqno() != null && !"0".equals(needCheck.getCheckSeqno())) {
                checkReport001Dto.setCheckSeqno(needCheck.getCheckSeqno());
                logicCheckResult.add(needCheck.getCheckSeqno());
            }
            else {
                checkReport001Dto.setCheckSeqno(IDGenerator.generate());
            }
            BeanUtil.copyPropertiesIgnoreNull(checkReport001Dto, excelValidDto);
            checkReport001Dto.setOrgCode(null);
            checkReport001Dto.setRepCode(DictCodeListExt2.报表名称_投资者明细信息登记.getValue());
            checkReport001Dto.setRepName(DictCodeListExt2.报表名称_投资者明细信息登记.getCodeName());
            checkReport001Dto.setCheckResult(SysDataUtil.getCodeFromDataDictByName(
                    DictCode.检查结果, excelValidDto.getCheckResult()));
            resultList.add(checkReport001Dto);
            // 更新检查序列号对应的数据
            EquityBusiInfoRegist001Dto equityBusiInfoRegist001Dto = new EquityBusiInfoRegist001Dto();
            List<String> keyList = StringUtil.splitToList(GlobalConstant.COMMA,
                    excelValidDto.getValidKey());
            equityBusiInfoRegist001Dto.setBusiSeqno(
                    StringUtil.splitToList(":", keyList.get(0)).get(1));
            equityBusiInfoRegist001Dto.setRegCode(
                    StringUtil.splitToList(":", keyList.get(1)).get(1));
            equityBusiInfoRegist001Dto.setRegistBankCode(
                    StringUtil.splitToList(":", keyList.get(2)).get(1));
            equityBusiInfoRegist001Dto.setIndentifyMark(
                    StringUtil.splitToList(":", keyList.get(3)).get(1));
            equityBusiInfoRegist001Dto.setCheckSeqno(checkReport001Dto.getCheckSeqno());
            egist001DtoList.add(equityBusiInfoRegist001Dto);

        }

        int maxCount = this.parsToInt(reportInsertCount, "3");
        logger.info(
                "进行数据存储开始=====" + resultList.size() + "====时间====" + DateUtil.getCurrentDate());
        // 删除报送前检查的数据
        if (CollectionUtils.isNotEmpty(logicCheckResult)) {
            if (logicCheckResult.size() < maxCount) {
                checkReport001Mapper.delReportCheckDatas(logicCheckResult);
            }
            else {
                int part = logicCheckResult.size() / maxCount;
                for (int k = 0; k < part; k++ ) {
                    checkReport001Mapper.delReportCheckDatas(
                            logicCheckResult.subList(0, maxCount));
                    logicCheckResult.subList(0, maxCount).clear();
                }
                if (CollectionUtils.isNotEmpty(logicCheckResult)) {
                    checkReport001Mapper.delReportCheckDatas(logicCheckResult);
                }
            }
        }
        // 由于resultList与equityInfoRegistUpdateList的size相同，故size只判断一个
        if (CollectionUtils.isNotEmpty(resultList)) {
            // 查询分批处理（每次最多处理maxCount条）
            if (resultList.size() < maxCount) {
                // 将新生成的检查信息插入报送前检查表
                checkReport001Mapper.batchInsert(resultList);
                // 批量更新投资者身份信息检查序列号
                equityBusiInfoRegist001Mapper.updateInvestRegistCheckSeqno(egist001DtoList);
            }
            else {
                int part = resultList.size() / maxCount;
                for (int j = 0; j < part; j++ ) {
                    // 将新生成的检查信息插入报送前检查表
                    checkReport001Mapper.batchInsert(resultList.subList(0, maxCount));
                    // 批量更新投资者身份信息检查序列号
                    equityBusiInfoRegist001Mapper.updateInvestRegistCheckSeqno(
                            egist001DtoList.subList(0, maxCount));
                    resultList.subList(0, maxCount).clear();
                    egist001DtoList.subList(0, maxCount).clear();
                }
                if (CollectionUtils.isNotEmpty(resultList)) {
                    // 将新生成的检查信息插入报送前检查表
                    checkReport001Mapper.batchInsert(resultList);
                    // 批量更新投资者身份信息检查序列号
                    equityBusiInfoRegist001Mapper.updateInvestRegistCheckSeqno(egist001DtoList);
                }
            }
        }
        logger.info(
                "进行数据存储结束=====" + resultList.size() + "====时间====" + DateUtil.getCurrentDate());
        return checkResult;
    }

    public List<ExcelValidDto> submitBeforeCheck(Map<String, Object> param) {
        List<ExcelValidDto> result=new ArrayList<ExcelValidDto>();
        dealSearchParam(param);
        // 权益人报送表主键
        @SuppressWarnings("unchecked")
        List<EquityBusiInfoRegist001Dto> investorsHoldInfoRegistList = (List<EquityBusiInfoRegist001Dto>)param.get(
                "investList");
        if (investorsHoldInfoRegistList == null || investorsHoldInfoRegistList.isEmpty()) {
            investorsHoldInfoRegistList = equityBusiInfoRegist001Mapper.selectAllData(param);
        }
        if(!StringUtil.isEmptyList(result)){
            return result;
        }
//add zhangjinyang 2020-12-21
//------del by lingxiaoqing on 2020-05-22 for 按照业务需要(检查出的不合格的数据强制报送出去)---->>>>>
//        String paraCodes = CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, ReportGlobalConstant.SYS_PARA_IS_SEND_EQUDET_WHEN_DEVIATION);
//        //投资者份额检查不通过的是否报送 01 是  02 否
//        if("01".equals(paraCodes)){
//            return result;
//        }
        //------del by lingxiaoqing on 2020-05-22 for 按照业务需要(检查出的不合格的数据强制报送出去)----<<<<<
        Map<String,Object> map=new HashMap<String,Object>();
        if(!StringUtil.isEmptyList(investorsHoldInfoRegistList)){
            int i=result.size();
            for(EquityBusiInfoRegist001Dto single:investorsHoldInfoRegistList){
                map.put("checkResult", "01");
                map.put("date", DateUtil.format(single.getEquityConfDate()));
                map.put("prdRegistCodeList", Arrays.asList(single.getRegCode()));
                map.put("indentifyMark", single.getIndentifyMark());
                List<EquityShareCheck001Dto> resultList = equityShareCheck001Mapper.searchPage(map);
                //查询对应的身份信息
                map.put("bankcode", single.getRegistBankCode());
                String chbequinfo = equityBusiInfoRegist001Mapper.getChbEquSubSend(map);
                ExcelValidDto excelValidDto = new ExcelValidDto();
                String validKey = "核心交易流水号:" + single.getBusiSeqno() + GlobalConstant.COMMA
                        + "产品登记编码:" + single.getRegCode() + GlobalConstant.COMMA
                        + "登记银行代码:" + single.getRegistBankCode() + GlobalConstant.COMMA
                        + "识别标识:" + single.getIndentifyMark() + GlobalConstant.COMMA;

                excelValidDto.setValidKey(validKey);
                excelValidDto.setRowNum(String.valueOf(i));
                excelValidDto.setDataTime(single.getUpdateTime());
                excelValidDto.setIsLogicCheck(DictCodeList.是否_是.getValue());
                excelValidDto.setOrgCode(single.getOrgCode());
                excelValidDto.setCreateDept(single.getCreateDept());
                excelValidDto.setCheckResult("02");
                String erro="";
                if(chbequinfo==null){
                    erro+="没有对应已报送的身份信息！";
                }
                if(!StringUtil.isEmptyList(resultList)){
                    erro+="昨日持仓+当日变动份额不等于今日持仓！";
                }
                if(!erro.equals("")){
                    excelValidDto.setErrorInfo(erro);
                    result.add(excelValidDto);
                }
                i++;
            }
        }

        return result;
    }
    /**
     * 查询报送的数据 调用报送的方法
     * @param map
     */
    public void dealData(Map<String, Object> map){
        //查询报送的数据
        Map<String, List<EquityBusiInfoRegist001Dto>> registMap = getSubmitList(map);

        Iterator<Map.Entry<String, List<EquityBusiInfoRegist001Dto>>> entries = registMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, List<EquityBusiInfoRegist001Dto>> entry = entries.next();
            List<EquityBusiInfoRegist001Dto> registList = entry.getValue();
            //根据 业务确认日期 分组报送
            Map<Date, List<EquityBusiInfoRegist001Dto>> groupByDateMap = registList.stream()
                    .collect(Collectors.groupingBy(EquityBusiInfoRegist001Dto::getEquityConfDate));
            for(Map.Entry<Date, List<EquityBusiInfoRegist001Dto>> ey : groupByDateMap.entrySet()){
                List<EquityBusiInfoRegist001Dto> value = ey.getValue();
                dealDataList(value, false);
                sendBusiInfoRegistCHB(value,false);
            }
        }
    }
    /**
     * 转换成key+value形式
     *
     * @param DictCode 字典表中数据类型
     * @param value 需要换换的值
     * @return key+value
     * @see
     * @since 1.0
     */
    public static String getDictCodeAndName(DictCode dictCode, String value) {
        if (StringUtil.isNotEmpty(value)) {
            return value + " " + SysDataUtil.getNameFromDataDict(dictCode, value);
        }
        return "";
    }

    /**
     * 分批次执行updateInterfSubmitAndSeq
     *
     * @param investList 数据
     * @see
     * @since 1.0
     */
    private void updateInterfSubmitAndSeq(String sendStatus,
                                          List<EquityBusiInfoRegist001Dto> investList,
                                          boolean isSendAllData) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }

        int maxDataCountOneTime = reportSelectOrDeleteCountForShenFen;
        int dataCount = investList.size();
        if (dataCount <= maxDataCountOneTime) {
            // modify by etliu 20180818 start
            equityBusiInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, investList);
            // modify by etliu 20180818 end
            return;
        }

        // 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
        if (isSendAllData) {
            logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表start===");
            equityBusiInfoRegist001Mapper.updateAllInterfSubmitAndSeqBatch(sendStatus);
            logger.info("updateAllInterfSubmitAndSeqBatch全量更新登记表end===");

            return;
        }

        List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
        newDataList.addAll(investList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            // modify by etliu 20180818 start
            logger.info("单次更新登记表start===");
            equityBusiInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus,
                    newDataList.subList(0, maxDataCountOneTime));
            // modify by etliu 20180818 end
            newDataList.subList(0, maxDataCountOneTime).clear();
            logger.info("单次更新登记表end===");
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            // modify by etliu 20180818 start
            logger.info("单次更新登记表start===");
            equityBusiInfoRegist001Mapper.updateInterfSubmitAndSeqBatch(sendStatus, newDataList);
            logger.info("单次更新登记表end===");

            // modify by etliu 20180818 end
        }
    }

    /**
     * 获取报送列表
     *
     * @param equiytRegistList 条件
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void doUpdateSubmtitStatus(List<EquityBusiInfoRegist001Dto> investList,
                                      boolean isSendAllData,
                                      List<EquityBusiInfoRegist001Dto> unSeqList) {

        if (CollectionUtils.isNotEmpty(investList)&&!CollectionUtils.isNotEmpty(unSeqList)) {
            // 讲第一次报送的数据数据履历表
            equityBusiInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(investList);
            // 删除已报送数据
            equityBusiInfoRegist001Mapper.deleteSubmitRegist(investList);
        }
        this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_接口报送.getValue(),
                investList, false);
        this.updateInterfSeqBatch(unSeqList);
    }
    /**
     * 分批次执行updateInterfSeq
     *
     * @param investList
     *            数据
     * @see
     * @since 1.0
     */
    private void updateInterfSeqBatch(List<EquityBusiInfoRegist001Dto> investList) {
        if (StringUtil.isEmptyList(investList)) {
            return;
        }
        int maxDataCountOneTime = Integer.parseInt(reportInsertCount);
        int dataCount = investList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityBusiInfoRegist001Mapper.updateInterfSeq(investList);
            return;
        }


        List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
        newDataList.addAll(investList);
        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++) {
            logger.info("单次更新登记表start===");
            equityBusiInfoRegist001Mapper.updateInterfSeq(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
            logger.info("单次更新登记表end===");
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            logger.info("单次更新登记表start===");
            equityBusiInfoRegist001Mapper.updateInterfSeq(newDataList);
            logger.info("单次更新登记表end===");

        }
    }
    /**
     * <一句话功能简述> <功能详细描述>
     *
     * @param upKey
     * @see
     * @since 1.0
     */
    public void updateSumStaus(Map<String, Object> map) {
        dealSearchParam(map);
        // 权益人报送表主键
        @SuppressWarnings("unchecked")
        List<EquityBusiInfoRegist001Dto> investorsHoldInfoRegistList = (List<EquityBusiInfoRegist001Dto>)map.get(
                "investList");
        // 报送状态表主键
        ChbSubmitStatus chbSubmitStatus = null;
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> sumitSeqList = new ArrayList<String>();
//        List<EquityBusiInfoRegist001Dto> alreadySendList = new ArrayList<EquityBusiInfoRegist001Dto>();
        List<EquityBusiInfoRegist001Dto> notSendList = new ArrayList<EquityBusiInfoRegist001Dto>();

        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, investorsHoldInfoRegistList.size());
        int index = 0;
        
        
        for (EquityBusiInfoRegist001Dto dto : investorsHoldInfoRegistList) {
            // 报送状态表插入组装
            chbSubmitStatus = new ChbSubmitStatus();
            if (StringUtil.isEmpty(dto.getSumitSeq())) {
                chbSubmitStatus.setSeqno(
                   // CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
                		CommonUtil.getSequenceStr(sequenceList, index)
                		);
                dto.setSumitSeq(chbSubmitStatus.getSeqno());
                
                index++;
            }
            
            else {
                chbSubmitStatus.setSeqno(dto.getSumitSeq());
            }
            chbSubmitStatus.setSubmitFeedbackStatus(DictCodeListExt2.报送状态_手工处理.getValue());
            chbSubmitStatus.setSubmitType(DictCodeListExt2.报表名称_投资者明细信息登记.getValue());
            chbSubmitStatus.setEquityConfDate(dto.getEquityConfDate());

            if (StringUtil.equals(DictCodeListExt2.报送状态_未报送.getValue(), dto.getSubmitStatus())) {
                listStatus.add(chbSubmitStatus);
                notSendList.add(dto);
            }
            else {
                sumitSeqList.add(dto.getSumitSeq());
//                alreadySendList.add(dto);
            }
        }
        // 检查报送的数据中是否存在已报送数据
        map.put("checkFlag", true);
        // 未报送的数据 插入报送状态表
        if (!StringUtil.isEmptyList(listStatus)) {
            cnBondInterfService.insertChbEquDetSubmitStatusListByPart(listStatus);
        }

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新反馈状态
        if (!StringUtil.isEmptyList(sumitSeqList)) {
            // 分批处理
            int maxDataCountOneTime = 300;
            int dataCount = sumitSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.updateSubmitStatusToOk(sumitSeqList);
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(sumitSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityBusiInfoRegist001Mapper.updateSubmitStatusToOk(
                            newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityBusiInfoRegist001Mapper.updateSubmitStatusToOk(newDataList);
                }
            }
        }
        /* 手工处理增加留痕处理以及已报送数据登记表删除 add by yuluan 2019-03-29 begin */
//        List<String> keys1 = new ArrayList<>();
//        for (EquityBusiInfoRegist001Dto single : investorsHoldInfoRegistList) {
//            String key = single.getBusiSeqno() + GlobalConstant.UNDER_LINE + single.getRegCode()
//                         + GlobalConstant.UNDER_LINE + single.getRegistBankCode()
//                         + GlobalConstant.UNDER_LINE + single.getIndentifyMark();
//            keys1.add(key);
//        }
        if (CollectionUtils.isNotEmpty(notSendList)) {
            // 讲第一次报送的数据数据履历表
            equityBusiInfoRegist001Mapper.insertInvestorsHoldInfoRegistHis(notSendList);
            // 删除已报送数据
            equityBusiInfoRegist001Mapper.deleteSubmitRegist(notSendList);
        }
        /* 手工处理增加留痕处理以及已报送数据登记表删除 add by yuluan 2019-03-29 end */
        this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(),
                investorsHoldInfoRegistList, false);
    }

    /**
     * 手工处理符合查询条件的全部数据
     *
     * @param map
     * @author liubin
     */
    @SuppressWarnings("unchecked")
    public void updateSumStausAll(Map<String, Object> map) {
        //先查询出参数表的参数
        List<ChbBatchPara> chbBatchParas=chbBatchParaMapper.selectAllData();
        if(!StringUtil.isEmptyList(chbBatchParas)){
            for(ChbBatchPara single:chbBatchParas){
                if(single.getTaskId().equals(BatchBeanName.投资者明细信息登记手工处理.getTaskId())){
//                    List<String> paras=StringUtil.splitToList(GlobalConstant.COMMA, single.getParas());
                    Map<String,Object> ssmap=(Map<String,Object>)JSON.parse(single.getParas());
                    map=ssmap;
                    map.put("sendStatus", "01");
                }
            }
        }
        dealSearchParam(map);

        List<EquityBusiInfoRegist001Dto> investorsHoldInfoRegistList = new ArrayList<EquityBusiInfoRegist001Dto>();
        investorsHoldInfoRegistList = equityBusiInfoRegist001Mapper.selectAllData(map);
        // 报送状态表主键
        ChbSubmitStatus chbSubmitStatus = null;
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> sumitSeqList = new ArrayList<String>();
        List<EquityBusiInfoRegist001Dto> alreadySendList = new ArrayList<EquityBusiInfoRegist001Dto>();
        List<EquityBusiInfoRegist001Dto> notSendList = new ArrayList<EquityBusiInfoRegist001Dto>();

        
        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, investorsHoldInfoRegistList.size());
        int index = 0;
        for (EquityBusiInfoRegist001Dto dto : investorsHoldInfoRegistList) {
            // 报送状态表插入组装
            chbSubmitStatus = new ChbSubmitStatus();
            if (StringUtil.isEmpty(dto.getSumitSeq())) {
                chbSubmitStatus.setSeqno(
                    //CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
                    CommonUtil.getSequenceStr(sequenceList, index)
                    );
                dto.setSumitSeq(chbSubmitStatus.getSeqno());
                index ++;
            }
            else {
                chbSubmitStatus.setSeqno(dto.getSumitSeq());
            }
            chbSubmitStatus.setSubmitFeedbackStatus(DictCodeListExt2.报送状态_手工处理.getValue());
            chbSubmitStatus.setSubmitType(DictCodeListExt2.报表名称_投资者明细信息登记.getValue());
            chbSubmitStatus.setEquityConfDate(dto.getEquityConfDate());

            if (StringUtil.equals(DictCodeListExt2.报送状态_未报送.getValue(), dto.getSubmitStatus())) {
                listStatus.add(chbSubmitStatus);
                notSendList.add(dto);
            }
            else {
                sumitSeqList.add(dto.getSumitSeq());
                alreadySendList.add(dto);
            }
        }
        // 检查报送的数据中是否存在已报送数据
        map.put("checkFlag", true);

        // 未报送的数据 插入报送状态表
        if (!StringUtil.isEmptyList(listStatus)) {
            cnBondInterfService.insertChbEquDetSubmitStatusListByPart(listStatus);
        }

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新反馈状态
        if (!StringUtil.isEmptyList(sumitSeqList)) {
            // 分批处理
            int maxDataCountOneTime = 300;
            int dataCount = sumitSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityBusiInfoRegist001Mapper.updateSubmitStatusToOk(sumitSeqList);
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(sumitSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityBusiInfoRegist001Mapper.updateSubmitStatusToOk(
                            newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityBusiInfoRegist001Mapper.updateSubmitStatusToOk(newDataList);
                }
            }
        }
        //查询本次处理的数据中是否有需要留痕处理的数据
        map.put("sendStatus", null);
        map.put("sendStatusList", null);
        map.put("holdDateStart", null);
        map.put("holdDateEnd", null);
        int count=equityBusiInfoRegist001Mapper.selectCountSizeWithSameKey(map);
        if(count>0){
            equityBusiInfoRegist001Mapper.insertEquDetRegistHis(map);
            equityBusiInfoRegist001Mapper.deleteEquDetSubmitRegist(map);
        }
        /* 手工处理增加留痕处理以及已报送数据登记表删除 add by yuluan 2019-03-29 end */
        this.updateInterfSubmitAndSeq(DictCodeListExt2.报送状态_手工处理.getValue(),
                investorsHoldInfoRegistList, false);
        if(!StringUtil.isEmptyList(chbBatchParas)){
            for(ChbBatchPara single:chbBatchParas){
                chbBatchParaMapper.deleteByPrimaryKey(single.getParaId());
            }
        }
    }

    /**
     * 校验是否所有的数据都符合 <功能详细描述>
     *
     * @param upKey 参数
     * @return error 列表
     * @see
     * @since 1.0
     */
    public List<ValidMessage> checkIsNotSubmit(Map<String, Object> map) {
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();
        dealSearchParam(map);
        if (equityBusiInfoRegist001Mapper.isNotSubmitP(map) != 0) {
            errorList.add(new ValidMessage("存在未报送的数据。"));
        }
        return errorList;
    }

    /**
     * 已手工处理的数据将报送状态改为未报送
     *
     * @param upKey 传入主键
     * @see
     * @since 1.0
     */
    public void cancelMannul(Map<String, Object> map) {
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
        dealSearchParam(map);
        @SuppressWarnings("unchecked")
        List<EquityBusiInfoRegist001Dto> list = (List<EquityBusiInfoRegist001Dto>)map.get(
                "investList");
        for (EquityBusiInfoRegist001Dto dto : list) {
            if (StringUtil.isNotEmpty(dto.getSumitSeq())) {
                ChbSubmitStatus chbSubmitStatus = new ChbSubmitStatus();
                chbSubmitStatus.setSeqno(dto.getSumitSeq());
                listStatus.add(chbSubmitStatus);
            }
        }
        equityBusiInfoRegist001Mapper.updateInvestRegist(list);
        // 将履历表中履历号最大的数据挪回登记表
        equityBusiInfoRegist001Mapper.insertInvestorsHoldInfoRegist(list);
        equityBusiInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
    }

    /**
     * 默认取消手工处理全部
     *
     * @param map
     */
    public void cancelMannulAll(Map<String, Object> map) {
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
        map.put("sendStatus", "03");
        dealSearchParam(map);
        List<EquityBusiInfoRegist001Dto> investorsHoldInfoRegistList = equityBusiInfoRegist001Mapper.selectAllData(
                map);
        for (EquityBusiInfoRegist001Dto dto : investorsHoldInfoRegistList) {
            if (StringUtil.isNotEmpty(dto.getSumitSeq())) {
                ChbSubmitStatus chbSubmitStatus = new ChbSubmitStatus();
                chbSubmitStatus.setSeqno(dto.getSumitSeq());
                listStatus.add(chbSubmitStatus);
            }
        }
        // 分批次处理

        int maxDataCountOneTime = 50;
        int dataCount = listStatus.size();
        int dataInvestorsHoldInfoCount = investorsHoldInfoRegistList.size();
        if (dataInvestorsHoldInfoCount <= maxDataCountOneTime) {
            equityBusiInfoRegist001Mapper.updateInvestRegist(investorsHoldInfoRegistList);
            // 将履历表中履历号最大的数据挪回登记表
            equityBusiInfoRegist001Mapper.insertInvestorsHoldInfoRegist(
                    investorsHoldInfoRegistList);
        }
        else {
            List<EquityBusiInfoRegist001Dto> newDataInvestorsHoldInfoList = new ArrayList<EquityBusiInfoRegist001Dto>();
            newDataInvestorsHoldInfoList.addAll(investorsHoldInfoRegistList);
            // 分批处理
            int part = dataInvestorsHoldInfoCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.updateInvestRegist(
                        newDataInvestorsHoldInfoList.subList(0, maxDataCountOneTime));
                equityBusiInfoRegist001Mapper.insertInvestorsHoldInfoRegist(
                        newDataInvestorsHoldInfoList.subList(0, maxDataCountOneTime));
                newDataInvestorsHoldInfoList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataInvestorsHoldInfoList)) {
                equityBusiInfoRegist001Mapper.updateInvestRegist(newDataInvestorsHoldInfoList);
                equityBusiInfoRegist001Mapper.insertInvestorsHoldInfoRegist(
                        newDataInvestorsHoldInfoList);
            }
        }
        if (dataCount <= maxDataCountOneTime) {
            equityBusiInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
        }
        else {
            List<ChbSubmitStatus> newDataList = new ArrayList<ChbSubmitStatus>();
            newDataList.addAll(listStatus);

            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityBusiInfoRegist001Mapper.deleteMannulSubmitRegist(
                        newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityBusiInfoRegist001Mapper.deleteMannulSubmitRegist(newDataList);
            }
        }
        // equityBusiInfoRegist001Mapper.updateInvestRegist(investorsHoldInfoRegistList);
        // // 将履历表中履历号最大的数据挪回登记表
        // equityBusiInfoRegist001Mapper.insertInvestorsHoldInfoRegist(investorsHoldInfoRegistList);
        // equityBusiInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
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
            else if ("2".equals(flag)) {
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
     * 检查对应的task是否有未执行或正在执行中的批处理任务 <功能详细描述>
     *
     * @param beanname
     * @return false未执行或正在执行中的任务 true 未检查到对应的未执行或正在执行中的任务
     */
    public boolean checkIfInBatchByTaskBeanName(String beanname) {
        EquityBusiInfoRegist001Service chbAssetInfoInterf = SpringContextUtil.getBean(
                EquityBusiInfoRegist001Service.class);
        int count = chbAssetInfoInterf.getNoendCountByTaskBeanName(beanname);
        if (count > 0) {
            return false;
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DataSource(DataSourceType.OTHER)
    public int getNoendCountByTaskBeanName(String beanname) {
        return equityBusiInfoRegist001Mapper.selectNoendCountByTaskBeanName(beanname);
    }


    /**
     * 导出功能查询
     *
     * @param pageParam 查询参数
     * @return 结果集
     * @see
     * @since 1.0
     */
    public int countExportNum(Map<String, Object> pageParam) {
        dealSearchParam(pageParam);
        int dataNum = equityBusiInfoRegist001Mapper.countExportNum(pageParam);
        return dataNum;
    }
    public Date getLastWeekDay(String marketCode,String date){
        return equityBusiInfoRegist001Mapper.getLastWeekDay(marketCode, date);
    }
    public void sendBusiInfoRegistCHB(List<EquityBusiInfoRegist001Dto> registList,boolean isSendAllData){
        if (StringUtil.isEmptyList(registList)) {
            return;
        }
        int sendCbMaxDataCount = Integer.valueOf(
                CacheUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SENDCB_MAX_COUNT));

        int dataCount = registList.size();
        if (dataCount <= sendCbMaxDataCount) {
            this.sendInvestorsHoldCHBOneFile(registList, isSendAllData);
            return;
        }

        // 分批处理
        List<EquityBusiInfoRegist001Dto> newDataList = new ArrayList<EquityBusiInfoRegist001Dto>();
        newDataList.addAll(registList);

        int part = dataCount / sendCbMaxDataCount; // 分批数
        for (int i = 0; i < part; i++ ) {
            this.sendInvestorsHoldCHBOneFile(newDataList.subList(0, sendCbMaxDataCount), isSendAllData);
            newDataList.subList(0, sendCbMaxDataCount).clear();
        }
        // 最后剩下的数据
        if (!StringUtil.isEmptyList(newDataList)) {
            this.sendInvestorsHoldCHBOneFile(newDataList, isSendAllData);
        }
    }
    /**
     * 发送某登记银行代码的数据(单文件最大1000000条数据)
     *
     * @param registList
     *            发送的某个机构的投资者身份信息
     * @see
     * @since 1.0
     */
    private void sendInvestorsHoldCHBOneFile(List<EquityBusiInfoRegist001Dto> registList, boolean isSendAllData) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        //是否升级投资者明细  01是  02否
        String isUpgrade = SvrDataUtil.getSysParam("SVR_HX_ISUPGRADE");
        String title="";
        if(StringUtil.isNotEmpty(isUpgrade)&&StringUtil.equals("01",isUpgrade)){
            // 元素名称
            title = "wemax_IssuerIdentificationCode|wemax_SalesContractNumber|wemax_FundamentalTransactionSerialNumber|wemax_WealthManagementAccountNumber|wemax_ClientUnifiedCode|wemax_DiscriminationId|wemax_ClientName|wemax_TransactionSequenceNumber|wemax_AssociatedCurrentAccountNumber|wemax_BankIdentificationCodeOfAssociatedCurrentAccount|wemax_BankNameOfAssociatedCurrentAccount|wemax_BranchLocationOfAssociatedCurrentAccount|wemax_OtherDistributionAgentsPleaseSelect|wemax_DistributionAgentIdentificationCode|wemax_DistributionAgentName|wemax_RegulatorOfDistributionAgent|wemax_ProductRegistrationCode|wemax_BusinessType|wemax_RegulatorOfBusinessLocation|wemax_SettlementDate|wemax_SettlementTime|wemax_Currency|wemax_BusinessDetailsAmount|wemax_BusinessDetailsAmountCNY|wemax_SettlementUnitValue|wemax_BusinessDetailsUnits|wemax_BusinessDetailsFees|wemax_Channel|wemax_TransactionTellerNumber|wemax_InvestorsBusinessDetailsInformationDetails|wemax_RegistrationSerialNumber";
        }else {
            title = "wemax_IssuerIdentificationCode|wemax_SalesContractNumber|wemax_FundamentalTransactionSerialNumber|wemax_WealthManagementAccountNumber|wemax_ClientUnifiedCode|wemax_DiscriminationId|wemax_ClientName|wemax_AssociatedCurrentAccountNumber|wemax_BranchLocationOfAssociatedCurrentAccount|wemax_OtherDistributionAgentsPleaseSelect|wemax_DistributionAgentIdentificationCode|wemax_DistributionAgentName|wemax_RegulatorOfDistributionAgent|wemax_ProductRegistrationCode|wemax_BusinessType|wemax_RegulatorOfBusinessLocation|wemax_SettlementDate|wemax_SettlementTime|wemax_Currency|wemax_BusinessDetailsAmount|wemax_BusinessDetailsAmountCNY|wemax_SettlementUnitValue|wemax_BusinessDetailsUnits|wemax_BusinessDetailsFees|wemax_Channel|wemax_TransactionTellerNumber|wemax_InvestorsBusinessDetailsInformationDetails|wemax_RegistrationSerialNumber";
        }
        // 元素字符串结果集
        StringBuffer info = new StringBuffer("\n" + "    <![CDATA[" + '\n');
        info.append(title);
        info.append('\n');
        List<String> registSeq = new ArrayList<String>();
        Map<String, String> registSeqKeyMap = new HashMap<String, String>();
        List<EquityBusiInfoRegist001Dto> unSubmitSeqList = new ArrayList<EquityBusiInfoRegist001Dto>();
        if (CollectionUtils.isNotEmpty(registList)) {
        	
        	   List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, registList.size());
	            int index = 0;
	            
            for (EquityBusiInfoRegist001Dto dto : registList) {
                // 针对取消手工处理再报送
                String registSeqNo = "";
                String currRegistSeq = "";
                if(DictCodeListExt2.报送反馈状态_发送失败.getValue().equals(dto.getSubmitFeedbackStatus())
                        ||DictCodeListExt2.报送反馈状态_中债退回.getValue().equals(dto.getSubmitFeedbackStatus())){
//                    registSeqNo = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
                	 registSeqNo = CommonUtil.getSequenceStr(sequenceList, index);
                	 index++;
                    //将报送流水为空的放入list
                    currRegistSeq = registSeqNo;
                    dto.setSumitSeq(currRegistSeq);
                    unSubmitSeqList.add(dto);
                }
                else if (StringUtil.isEmpty(dto.getSumitSeq())) {
//                    registSeqNo = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
                    registSeqNo = CommonUtil.getSequenceStr(sequenceList, index);
                    index++;
                    dto.setSumitSeq(registSeqNo);
                    currRegistSeq = registSeqNo;
                    unSubmitSeqList.add(dto);
                } else {
                    registSeqNo = dto.getSumitSeq();
                    currRegistSeq = registSeqNo;
                }
                registSeq.add(registSeqNo);
                registSeqKeyMap.put(currRegistSeq, dto.getBusiSeqno() + "," + dto.getRegCode() + ","
                        + dto.getRegistBankCode() + "," +dto.getIndentifyMark());
                String  prdvalue=dto.getProdValue()==null?"":dto.getProdValue().setScale(5,RoundingMode.HALF_UP).toString();
                String value ="";
                if(StringUtil.isNotEmpty(isUpgrade)&&StringUtil.equals("01",isUpgrade)){
                    value = dto.getRegistBankCode()+"|"+dto.getContractNo()+"|"+dto.getBusiSeqno()+"|"+dto.getFinAcc()
                            + "|" + dto.getCustContractNo()+ "|"+ dto.getIndentifyMark()+ "|"+ dto.getCustName()+ "|"+  dto.getTradeSeq()+ "|"+dto.getFundAcc()
                            + "|" +dto.getFundAccCode()+ "|" +dto.getFundAccName() + "|" +dto.getFundAccPlace()+ "|"+ dto.getIsConsign()+ "|"+ (dto.getAgencyCode()==null?"":dto.getAgencyCode())+ "|"+ (dto.getAgencyName()==null?"":dto.getAgencyName())
                            + "|" + (dto.getAgencyFsa()==null?"":dto.getAgencyFsa())+ "|"+ dto.getRegCode()+ "|"+ dto.getBusiType()+ "|"+ dto.getBusiFsa()
                            + "|" + sdf.format(dto.getEquityConfDate())+ "|"+ dto.getShareConfDatetime()+ "|"+ dto.getCcy()+ "|"+ dto.getAmount().setScale(2,RoundingMode.HALF_UP)
                            + "|"+ dto.getAmountCny().setScale(2,RoundingMode.HALF_UP) + "|" + prdvalue+ "|"+ dto.getShares().setScale(5,RoundingMode.HALF_UP)+ "|"+ dto.getFeeAmt().setScale(2,RoundingMode.HALF_UP)
                            + "|"+ dto.getChannel()+ "|"+ dto.getTranTellName() + "|" + (dto.getRemark()==null?"":dto.getRemark()) + "|" + registSeqNo;

                }else{
                    value = dto.getRegistBankCode()+"|"+dto.getContractNo()+"|"+dto.getBusiSeqno()+"|"+dto.getFinAcc()
                            + "|" + dto.getCustContractNo()+ "|"+ dto.getIndentifyMark()+ "|"+ dto.getCustName()+ "|"+ dto.getFundAcc()
                            + "|" + dto.getFundAccPlace()+ "|"+ dto.getIsConsign()+ "|"+ (dto.getAgencyCode()==null?"":dto.getAgencyCode())+ "|"+ (dto.getAgencyName()==null?"":dto.getAgencyName())
                            + "|" + (dto.getAgencyFsa()==null?"":dto.getAgencyFsa())+ "|"+ dto.getRegCode()+ "|"+ dto.getBusiType()+ "|"+ dto.getBusiFsa()
                            + "|" + sdf.format(dto.getEquityConfDate())+ "|"+ dto.getShareConfDatetime()+ "|"+ dto.getCcy()+ "|"+ dto.getAmount().setScale(2,RoundingMode.HALF_UP)
                            + "|"+ dto.getAmountCny().setScale(2,RoundingMode.HALF_UP) + "|" + prdvalue+ "|"+ dto.getShares().setScale(5,RoundingMode.HALF_UP)+ "|"+ dto.getFeeAmt().setScale(2,RoundingMode.HALF_UP)
                            + "|"+ dto.getChannel()+ "|"+ dto.getTranTellName() + "|" + (dto.getRemark()==null?"":dto.getRemark()) + "|" + registSeqNo;

                }
                info.append(value);
                info.append('\n');
            }
        }
        info.append("    ]]>");
        info.append('\n');
        // 更新报送状态表
        // modify by etliu 20181011 start
        if (!isSendAllData) {
            this.doUpdateSubmtitStatus(registList, isSendAllData, unSubmitSeqList);
        }
        // modify by etliu 20181011 end
        // 将格式转换为中债报送的固定格式
        List<InterfCnBondSubmitPara> paras = new ArrayList<InterfCnBondSubmitPara>();
        InterfCnBondSubmitPara it = new InterfCnBondSubmitPara();
        List<Map<String, CnBondInterfItem>> listMap = new ArrayList<Map<String, CnBondInterfItem>>();
        CnBondInterfItem cnBondInterfItem = new CnBondInterfItem();
        cnBondInterfItem.setValue(info.toString());
        Map<String, CnBondInterfItem> maps = new HashMap<String, CnBondInterfItem>();
        maps.put("InvestorsBusinessDetailsInformationRegistrationCsvDataExplanatory", cnBondInterfItem);
        listMap.add(maps);
        it.setDealType(DictCodeListExt2.报表名称_投资者明细信息登记.getValue());
        if (CollectionUtils.isNotEmpty(registList)) {
            it.setEquityConfDate(registList.get(0).getEquityConfDate());
        }
        it.setInfos(listMap);
        it.setSysDate(DateUtil.now());
        it.setRegistSeq(registSeq);
        it.setRegistBankCode(registList.get(0).getRegistBankCode());
        it.setRegistSeqKeyMap(registSeqKeyMap);
        paras.add(it);
        cnBondInterfService.sendCnBond(paras);

    }

    /**
     * 获取报送列表
     *
     * @param map
     *            界面查询条件
     * @return map
     * @see
     * @since 1.0
     */
    public Map<String, List<EquityBusiInfoRegist001Dto>> getSubmitList(Map<String, Object> map) {
        List<EquityBusiInfoRegist001Dto> investorsHoldInfoRegistList = new ArrayList<>();
        investorsHoldInfoRegistList= this.selectAllList(map);

        Map<String, List<EquityBusiInfoRegist001Dto>> resultMap = new HashMap<>();
        List<EquityBusiInfoRegist001Dto> resultList = null;
        if (CollectionUtils.isNotEmpty(investorsHoldInfoRegistList)) {
            format(investorsHoldInfoRegistList);
            for (EquityBusiInfoRegist001Dto item : investorsHoldInfoRegistList) {
                if (CollectionUtils.isEmpty(resultMap.get(item.getRegistBankCode()))) {
                    resultList = new ArrayList<>();
                } else {
                    resultList = resultMap.get(item.getRegistBankCode());
                }
                resultList.add(item);
                resultMap.put(item.getRegistBankCode(), resultList);
            }
        }
        return resultMap;
    }

    /**
     * 数据字典信息格式化
     *
     * @param investorsHoldInfoRegistList
     *            数据来源
     * @see
     * @since 1.0
     */
    private void format(List<EquityBusiInfoRegist001Dto> investorsHoldInfoRegistList) {
        investorsHoldInfoRegistList.forEach(item -> {
            item.setSubmitStatus(getDictCodeAndName(DictCode.报送状态, item.getSubmitStatus()));
        });
    }
}
