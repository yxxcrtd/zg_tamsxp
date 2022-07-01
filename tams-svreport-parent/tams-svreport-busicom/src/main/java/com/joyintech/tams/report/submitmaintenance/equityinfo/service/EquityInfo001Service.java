/*
 * 文件名：EquityInfo001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年9月16日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitmaintenance.equityinfo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.executor.ExecutorException;
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
import org.xml.sax.SAXException;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbEquHoldMapper;
import com.joyintech.tams.common.base.dao.ChbEquSubMapper;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.chencknum.dao.CheckNumMapper;
import com.joyintech.tams.report.chencknum.dto.TotalAmountInfoDto;
import com.joyintech.tams.report.common.ReadConfigurationUtil;
import com.joyintech.tams.report.common.TxtParseUtil;
import com.joyintech.tams.report.common.XmlParseUtil;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.report.common.util.DateCalcUtil;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.report.interf.common.constant.InterfConstant;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpColumn;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.report.investormerger.dao.ChbEquHoldCheckDtoMapper;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfo001Mapper;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfoDistinctMapper;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfoDto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.EquityInfoCheckUtil;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.FileIO;
import com.joyintech.tams.report.submitmaintenance.equityinfo.validate.EquityInfoValid;
import com.joyintech.tams.report.submitmaintenance.transformrule.dao.ChbTransformRuleObjDtoMapper;
import com.joyintech.tams.report.submitmaintenance.transformrule.dao.TransformRule001Mapper;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.ChbTransformRuleObjDto;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.TransformRule001Dto;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.equityinforegist.common.EquityInfoRegistCommonUtil;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 投资者信息维护Service
 * <p>
 * 投资者信息维护业务逻辑处理
 * </p>
 *
 * @author 汪敏
 * @version 1.0 2017年9月16日
 * @see EquityInfo001Service
 * @since 1.0
 */
@Service("equityInfo001Service")

public class EquityInfo001Service extends CommonExportService<EquityInfo001Dto> {

    /**
     * 上游接口解析读取文件处理最大线程数
     */
    @Value("${application.interfMaxCorePoolSize}")
    private int interfMaxCorePoolSize;

    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public String reportInsertLongCount;
    /**
     * 数据批量一次查询或者删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public String reportSelectOrDeleteCount;
    /**
     * 数据批量一次查询或者删除条数(身份信息用)
     */
    @Value("${application.reportSelectOrDeleteCountForShenFen}")
    public int reportSelectOrDeleteCountForShenFen;
    /**
     * 数据批量一次性insert条数
     */
    @Value("${application.reportInsertCount}")
    public int reportInsertCount;
    /**
     * 上游接口文件编码格式
     */
    @Value("${application.reportInterfFileEncoding}")
    public String reportInterfFileEncoding;

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
    /**
     * 上游接口解析分割符
     */
    @Value("${application.reportInterfSplitCharacter}")
    private String reportInterfSplitCharacter;
    @Autowired
    private BatchInsertService batchInsertService;

    /**
     * 投资者报送表常量
     */
    private static final String CHB_EQU_SUB = "CHB_EQU_SUB";
    /**
     * 投资者身份维护表常量
     */
    private static final String CHB_EQU_HOLD = "CHB_EQU_HOLD";

    /**
     * 投资者身份信息Mapper
     */
    @Autowired
    private EquityInfo001Mapper equityInfo001Mapper;

    /**
     * 共通权益人信息mapper
     */
    @Autowired
    private ChbEquHoldMapper chbEquHoldMapper;

    /**
     * 共通信息登记mapper
     */
    @Autowired
    private ChbEquSubMapper chbEquSubMapper;

    /**
     * check
     */
    @Autowired
    private EquityInfoValid equityInfoValid;
    // /**
    // * 报送时间配置mapper
    // */
    // @Autowired
    // private RegTimePointsDtoMapper regTimePointsDtoMapper;

    /**
     * 报送登记mapper
     */
    @Autowired
    private EquityInfoRegist001Mapper equityInfoRegist001Mapper;

    /**
     * 报送前检查mapper
     */
    @Autowired
    private CheckReport001Mapper chbReportCheckMapper;

    /**
     * 投资者身份去重检查
     */
    @Autowired
    private EquityInfoDistinctService equityInfoDistinctService;
    /**
     * 投资者身份去重检查
     */
    @SuppressWarnings("unused")
    @Autowired
    private EquityInfoDistinctMapper equityInfoDistinctMapper;
    /**
     * 投资者身份去重检查mapper
     */
    @SuppressWarnings("unused")
    @Autowired
    private ChbEquHoldCheckDtoMapper chbEquHoldCheckDtoMapper;

    @Autowired
    private CheckNumMapper checkNumMapper;
    @Autowired
    private TransformRule001Mapper transformRule001Mapper;
    @Autowired
    private ChbTransformRuleObjDtoMapper chbTransformRuleObjDtoMapper;

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SqlSessionTemplate template;
    /**
     * 查询权益人信息列表
     *
     * @param param 查询参数
     * @return 权益人信息列表
     * @see
     * @since 1.0
     */
    public Page<EquityInfo001Dto> searchForPage(Map<String, Object> param) {
        // 设置查询参数
        setSearchParam(param);
        // 查询数据
        int page = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
        Page<EquityInfo001Dto> pageList = super.queryForPage(
                page,legth,()->equityInfo001Mapper.queryForEquityInfoPage(param));
//        cnBondUpInterfTaskService.execute();
        // 数据处理
        format(pageList.getRoot(), true);
        return pageList;
    }

    /**
     * 导出选择数据
     *
     * @param expKeys 权益人信息主键
     * @return 权益人信息List
     * @see
     * @since 1.0
     */
    public List<EquityInfo001Dto> queryExportSelectList(String expKeys) {
        // 返回处理后的数据列表
        return selectInfoByKey(expKeys,true);
    }
    /**
     *
     * 根据key查询结果
     * <功能详细描述>
     * @param expKeys
     * @param isExport
     * @return
     * @see
     * @since 1.0
     */
    public List<EquityInfo001Dto> selectInfoByKey(String expKeys ,boolean isExport){
        // 处理画面参数
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, expKeys);
        List<EquityInfo001Dto> eList = new ArrayList<EquityInfo001Dto>();
        for (String string : keys) {
            EquityInfo001Dto equityInfo001Dto = new EquityInfo001Dto();
            String[] key = StringUtil.split(string, "|");
            equityInfo001Dto.setBankCode(key[0]);
            equityInfo001Dto.setIndentifyMark(key[1]);
            eList.add(equityInfo001Dto);
        }
        // 查询数据
        //List<EquityInfo001Dto> equityList = equityInfo001Mapper.queryExpSelectEquityInfo(eList);
        List<EquityInfo001Dto> equityList = new ArrayList<EquityInfo001Dto>();
        int maxDataCountOneTime = 500;
        int dataCount = eList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityList.addAll(equityInfo001Mapper.queryExpSelectEquityInfo(eList));
        	/*if(isExport){
                format(equityList, false);
            }*/
            return equityList;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(eList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityList.addAll(equityInfo001Mapper.queryExpSelectEquityInfo(newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityList.addAll(equityInfo001Mapper.queryExpSelectEquityInfo(newDataList));
        }
        /*if(isExport){
            format(equityList3, false);
        }*/
        return equityList;
    }
    /**
     * 导出excel功能
     * <p>
     * 该方法对导入数据进行验证。验证失败则返回错误消息；验证成功则直接入库，同时返回的验证消息为空。
     * </p>
     *
     * @param param 导入参数
     * @return 导入数据的验证结果
     * @see
     * @since 1.0
     */
    public List<EquityInfo001Dto> queryExportList(Map<String, Object> param) {
        // 设置参数
        setSearchParam(param);
        // 查询数据
        List<EquityInfo001Dto> pageList = equityInfo001Mapper.queryForEquityInfoPage(param);
        // 格式化数据字典值
        format(pageList, false);
        // 返回结果
        return pageList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildExportParam(ExportParam exportParam) {
        Map<String, Object> pageParam = exportParam.getSelectParam();
        String pageIds = (String)pageParam.get("pageIds");
        if(StringUtil.isNotEmpty(pageIds)){
            List<String> pageNames =  Arrays.asList(pageIds.split(GlobalConstant.COMMA));
            pageParam.replace("pageIds", pageIds, pageNames);
        }
        else{
            pageParam.replace("pageIds", pageIds, null);
        }

        // 导出方式flg
        String exportMethodFlg = (String)pageParam.get("export_method_flg");

        // 导出（未选中数据）
        if (StringUtil.equals(exportMethodFlg, "export")) {
            // 设置参数
            setSearchParam(pageParam);

            exportParam.config("/com/joyintech/tams/report/submitmaintenance/equityinfo/conf/export_conf.xml").select(() -> equityInfo001Mapper.queryForEquityInfoPage(pageParam));
        }
        // 将选择的数据导出成excel
        else if (StringUtil.equals(exportMethodFlg, "exportSelect")) {
            String exportKeys =(String) pageParam.get("expKeys");
            // 处理画面参数
            List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, exportKeys);
            List<EquityInfo001Dto> eList = new ArrayList<EquityInfo001Dto>();
            for (String string : keys) {
                EquityInfo001Dto equityInfo001Dto = new EquityInfo001Dto();
                String[] key = StringUtil.split(string, "|");
                equityInfo001Dto.setBankCode(key[0]);
                equityInfo001Dto.setIndentifyMark(key[1]);
                eList.add(equityInfo001Dto);
            }

            exportParam.config("/com/joyintech/tams/report/submitmaintenance/equityinfo/conf/export_conf.xml").select(() -> equityInfo001Mapper.queryExpSelectEquityInfo(eList));
        }

    }


    public List<EquityInfo001Dto> queryForEquityInfoPage(Map<String, Object> param){
        return equityInfo001Mapper.queryForEquityInfoPage(param);
    }

    public List<EquityInfo001Dto> queryExpSelectEquityInfo(List<EquityInfo001Dto> eList){
        return equityInfo001Mapper.queryExpSelectEquityInfo(eList);
    }
    @Override
    public com.joyintech.framework.grid.Page<EquityInfo001Dto> selectPageData(Map<String, Object> pageParam) {
        List<EquityInfo001Dto> data = null;
        String exportMethodFlg = (String)pageParam.get("export_method_flg");
        if (StringUtil.equals(exportMethodFlg, "export")) {
            data =  restTemplate.postForList(
                    SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityInfo/queryForEquityInfoPage",pageParam,
                    EquityInfo001Dto.class);
        }  else if (StringUtil.equals(exportMethodFlg, "exportSelect")) {
            String exportKeys =(String) pageParam.get("expKeys");
            // 处理画面参数
            List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, exportKeys);
            List<EquityInfo001Dto> eList = new ArrayList<EquityInfo001Dto>();
            for (String string : keys) {
                EquityInfo001Dto equityInfo001Dto = new EquityInfo001Dto();
                String[] key = StringUtil.split(string, "|");
                equityInfo001Dto.setBankCode(key[0]);
                equityInfo001Dto.setIndentifyMark(key[1]);
                eList.add(equityInfo001Dto);
            }
            data =  restTemplate.postForList(
                    SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityInfo/queryExpSelectEquityInfo",eList,
                    EquityInfo001Dto.class);
        }
        com.joyintech.framework.grid.Page<EquityInfo001Dto> page = new com.joyintech.framework.grid.Page<EquityInfo001Dto>();
        page.setRoot(data);
//        if(data!=null)
//        {
//            page.setTotal(data.size());
//        }

        return  page;
    }
    @Override
    protected void beforeCreateExcel(List<EquityInfo001Dto> contents) {
        // 格式化数据字典值
        format(contents, false);
    }

    /**
     * 删除数据
     *
     * @param equityList 需要被删除的投资者数据列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void doDelete(List<EquityInfo001Dto> equityList) {
        ChbEquHold chbEquHold = null;
        ChbEquSub chbEquSub = null;
        // 查询报送时间配置表
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("reportCode", DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
        // 当前批次报送时点集合
        Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
        List<Date> dateList = null;

        // List<RegTimePoints001Dto> listTime = regTimePointsDtoMapper.searchPage(map);
        //modify by yanya on 20210521 批量获取索引，避免死锁
        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, equityList.size());
        int index = 0;
        for (EquityInfo001Dto equityInfo001Dto : equityList) {
            chbEquHold = new ChbEquHold();
            chbEquHold.setStatus(DictCodeListExt1.维护数据状态_删除.getValue());
            chbEquHold.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
            chbEquHold.setRegistBankCode(equityInfo001Dto.getBankCode());
            chbEquHoldMapper.updateByPrimaryKeySelective(chbEquHold);
            // 通过识别标识与登记银行编码查询登记表中是否存在登记记录
            List<EquityInfoDto> equityInfoDto = equityInfo001Mapper.querySumbitEquityByCode(
                    chbEquHold);
            // 报送时点通用方法
            dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者身份信息登记.getValue(),
                    DictCodeListExt2.报送数据状态_删除.getValue(), dateMap, DateUtil.now());
            if (CollectionUtils.isNotEmpty(dateList)) {
                equityInfo001Dto.setChbSubmitDeadline(dateList.get(0));
                equityInfo001Dto.setSubmitDeadline(dateList.get(1));
            }
            if (equityInfoDto != null && equityInfoDto.size() == 1) {
                EquityInfoDto equityInfo = equityInfoDto.get(0);
                // 当登记表中只有一条数据，并且是未报送状态的，直接删除该条数据
                if (DictCodeListExt2.报送状态_未报送.getValue().equals(equityInfo.getSubmitStatus())) {
                    chbEquSub = new ChbEquSub();
                    chbEquSub.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
                    chbEquSub.setRegistBankCode(equityInfo001Dto.getBankCode());
                    chbEquSub.setSubmitStatus(equityInfo.getSubmitStatus());
                    chbEquSubMapper.deleteByPrimaryKey(chbEquSub);
                }
                // 当登记表中只有一条数据，且报送状态为手工处理或者接口报送时，重新插入一条数据
                else if ((DictCodeListExt2.报送状态_接口报送.getValue().equals(equityInfo.getSubmitStatus())
                        || DictCodeListExt2.报送状态_手工处理.getValue().equals(
                        equityInfo.getSubmitStatus()))
                        &&!DictCodeListExt2.报送数据状态_删除.getValue().equals(equityInfo.getStatus())) {
                    equityInfo001Dto.setType(equityInfo.getType());
                    equityInfo001Dto.setSumitSeq(
                            // CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
                            CommonUtil.getSequenceStr(sequenceList, index)
                    );
                    index ++;
                    equityInfo001Dto.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
                    equityInfoRegist001Mapper.insertDeleteEquityReg(equityInfo001Dto);
                }
            }
            // 当登记表中存两条数据时，若有一条数据为已删除的手工处理的数据，则直接将未报送数据删除；若一条数据为已报送的未删除的数据，则将未报送数据更新为删除状态
            else if (equityInfoDto != null && equityInfoDto.size() == 2) {
                for (EquityInfoDto equityInfo2 : equityInfoDto) {
                    if((DictCodeListExt2.报送状态_接口报送.getValue().equals(equityInfo2.getSubmitStatus())
                            || DictCodeListExt2.报送状态_手工处理.getValue().equals(
                            equityInfo2.getSubmitStatus()))
                            &&!DictCodeListExt2.报送数据状态_删除.getValue().equals(equityInfo2.getStatus())){
                        chbEquSub = new ChbEquSub();
                        chbEquSub.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
                        chbEquSub.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                        chbEquSub.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
                        chbEquSub.setRegistBankCode(equityInfo001Dto.getBankCode());
                        chbEquSub.setChbSubmitDeadline(equityInfo001Dto.getChbSubmitDeadline());
                        chbEquSub.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
                        chbEquSubMapper.updateByPrimaryKeySelective(chbEquSub);
                    }
                    else if(DictCodeListExt2.报送状态_手工处理.getValue().equals(
                            equityInfo2.getSubmitStatus())
                            &&DictCodeListExt2.报送数据状态_删除.getValue().equals(equityInfo2.getStatus())){
                        chbEquSub = new ChbEquSub();
                        chbEquSub.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
                        chbEquSub.setRegistBankCode(equityInfo001Dto.getBankCode());
                        chbEquSub.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                        chbEquSubMapper.deleteByPrimaryKey(chbEquSub);
                    }
                }
            }
        }
    }

    /**
     * excel导入功能
     *
     * @return 检验list
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ExcelValidDto> importData(List<List<String>> dataList,List<List<String>> tempList,String bankCode,String deptCode,String orgCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            String templateFlag = SvrDataUtil.getSysParam("SVR_EQU_INFO_TEMPLATE_FLAG");

            List<String> headList = new ArrayList<String>();
            headList.add("登记银行代码");
            headList.add("识别标识");
            headList.add("原识别标识");
            headList.add("该投资者是否属于本行");
            headList.add("投资者所属银行名称");
            headList.add("投资者所属银行代码");
            headList.add("投资者境内外标识");
            headList.add("投资者所属国家或地区");
            headList.add("投资者类别");
            headList.add("个人证件类别");
            headList.add("机构证件类别");
            headList.add("其他证件名称");
            headList.add("证件号码");
            headList.add("SPV资金托管账户开户行");
            headList.add("其他资金托管账户开户行");
            headList.add("投资者名称");
            headList.add("性别");
            headList.add("风险偏好");
            headList.add("手机号码");
            headList.add("固定电话");
            headList.add("电子邮箱");
            headList.add("备注");
            headList.add("投资者分类");
            headList.add("经办人");
            headList.add("接入检查结果");
            headList.add("中债反馈消息");
            if("02".equals(templateFlag)) {
                headList.add("账号开户所在地");
            }

            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }

            if (null != dataList) {
                //查询出所有的已处理过的生僻字 map
                Map<String, Object> paramap=new HashMap<String, Object>();
                paramap.put("statusList", Arrays.asList("E"));
                List<TransformRule001Dto> allList=transformRule001Mapper.selectAllData(paramap);
                Map<String,TransformRule001Dto> spzMap=allList.stream().collect(
                        Collectors.toMap(x ->x.getTransType()+x.getOrgField(),part->part));
                List<ChbTransformRuleObjDto> allRuleObjList=chbTransformRuleObjDtoMapper.selectAllDataByTableId(CHB_EQU_HOLD);
                Map<String,List<ChbTransformRuleObjDto>> objFiledsMap=allRuleObjList.stream().collect(
                        JoyinCollectors.groupingBy(
                                x -> x.getInfoProperty() ));
                // 将excel表格中的数据转化为权益人dto
                List<Map<String, String>> listType = new ArrayList<Map<String, String>>();
                List<ExcelValidDto> checkResult = equityInfoValid.checkExcelEquity(dataList,
                        listType, null,spzMap,objFiledsMap,orgCode);
                if (checkResult.isEmpty()) {
                    List<EquityInfo001Dto> equityList = converToBean(dataList,orgCode);
                    EquityInfoDistinctService equityInfoDistinctService =SpringContextUtil.getBean(EquityInfoDistinctService.class);
                    List<String> keys = equityInfoDistinctService.getKeys(equityList);
                    equityInfoDistinctService.checkSendedData(equityList,keys,"01");
                    List<String> mergerKeys=equityInfoDistinctService.checkAllData(equityList,keys,"01");
                    equityInfoDistinctService.merger(equityList,"dto");
                    equityInfoDistinctService.deleteEquitInfoAndSub(mergerKeys);
//						equityInfoDistinctService.refreshIndentifyMark();
                    //modify by wangmin 合DB2版本代码，添加数据源 begin 2018-07-23
                    //dealAfterCheck(listType, equityList);
                    dealAfterCheck(listType, equityList,DictCodeListExt2.数据来源_EXCEL导入.getValue(),deptCode,orgCode);
                    //modify by wangmin 合DB2版本代码，添加数据源 end 2018-07-23
                }
                return checkResult;
            }
        }
        return null;
    }

    /**
     * check后的保存
     * <功能详细描述>
     * @param listType
     * @param equityList
     * @param dataSource 数据来源
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void dealAfterCheck(List<Map<String, String>> listType,
                               List<EquityInfo001Dto> equityList , String dataSource
            ,String deptCode,String orgCode) {
        List<EquityInfo001Dto> listN = new ArrayList<EquityInfo001Dto>();
        List<EquityInfo001Dto> listA = new ArrayList<EquityInfo001Dto>();
        List<EquityInfo001Dto> listO = new ArrayList<EquityInfo001Dto>();
        List<EquityInfo001Dto> listU = new ArrayList<EquityInfo001Dto>();
        //识别标识变更专用list
        List<EquityInfo001Dto> listI = new ArrayList<EquityInfo001Dto>();
        // 当前批次报送时点集合
        Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
        //只有四期数据变化的情况下，用来更新INFO表的数据结构
        List<EquityInfo001Dto> listUpForInfo = new ArrayList<EquityInfo001Dto>();
        //只有四期数据变化的情况下，用来更新SUB表的数据结构
        List<EquityInfo001Dto> listUpForSub = new ArrayList<EquityInfo001Dto>();
        //
        List<EquityInfoRegist001Dto> registList=new ArrayList<EquityInfoRegist001Dto>();
        template.select("com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper.queryBatchForEquityInfoConStrast",
                new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        registList.add((EquityInfoRegist001Dto) resultContext.getResultObject());
                    }
                });
        Map<String, List<EquityInfoRegist001Dto>> mapRegist = registList.stream()
                .collect(
                        JoyinCollectors.groupingBy(
                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE
                                        + GlobalConstant.VERTICAL_LINE + x.getIndentifyMark()));
        List<Date> dateList = null;

        //  EquityInfo001Dto equityInfo001Dto = null;
//        EquityInfo001Dto equityInfo001DtoTemp = null;
        Map<String, String> map = new HashMap<String, String>();
        // RegTimePoints001Dto regTimePoints001Dto =
        // regTimePointsDtoMapper.findByReportCode(DictCodeList.报表名称_投资者身份信息登记.getValue(),null);

        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, equityList.size());

        //生僻字按照已经维护的规则进行转化
        Map<String, Object> paramap=new HashMap<String, Object>();
        paramap.put("statusList", Arrays.asList("E"));
        List<TransformRule001Dto> allList=transformRule001Mapper.selectAllData(paramap);
        Map<String,TransformRule001Dto> spzMap=allList.stream().collect(
                Collectors.toMap(x -> x.getTransType()+x.getOrgField(),part->part));
        List<ChbTransformRuleObjDto> allRuleObjList=chbTransformRuleObjDtoMapper.selectAllDataByTableId(CHB_EQU_HOLD);
        Map<String,List<ChbTransformRuleObjDto>> objFiledsMap=allRuleObjList.stream().collect(
                JoyinCollectors.groupingBy(
                        x -> x.getInfoProperty() ));

        //设置数据结构的转化规则字段内容
        EquityInfoCheckUtil.replaceByExchangeRule(allRuleObjList,spzMap,equityList);
        for(EquityInfo001Dto equityInfo001Dto : equityList){
            if(equityInfo001Dto.getTempMap() != null&&null!=equityInfo001Dto.getTempMap().get("holderName")){
                //投资者姓名转换
                equityInfo001Dto.setHolderName(equityInfo001Dto.getTempMap().get("holderName"));
            }
        }

        // add by yangxianjiu 2021-12-23 end
        // equityInfo001Mapper.getEquityInfo001DtoByKey(equityInfo001Dto); 优化导入
        // 组合 indentifyMark bankCode
        List<EquityInfo001Dto> equityInfo001DtoList = equityInfo001Mapper.selectAllEquityInfo();
        Map<String, EquityInfo001Dto> equityInfoMap = equityInfo001DtoList.stream().collect(
                JoyinCollectors.toMap(x -> x.getIndentifyMark() + "_" + x.getBankCode(),part -> part));
//        for (EquityInfo001Dto equityInfo001Dto : equityInfo001DtoList) {
//            equityInfoMap.put(equityInfo001Dto.getIndentifyMark()+"_"+equityInfo001Dto.getBankCode(),
//                    equityInfo001Dto);
//        }
        Map<String, List<Map<String, String>>>  mst= new HashMap<>();
        boolean nullFalg = true;
        if(!CollectionUtils.isNotEmpty(listType)){
            nullFalg = false;
        }
        for(Map<String,String> key :listType) {
            String nullFalgKey = key.get("indentify_mark");
            if (!StringUtil.isNotEmpty(nullFalgKey)) {
                nullFalg = false;
            }
        }
        if(nullFalg) {
            mst = listType.stream().collect(Collectors.groupingBy(e -> e.get("indentify_mark").toString()));
        }
        for (int i = 0; i < equityList.size(); i++ ) {
            EquityInfo001Dto  equityInfo001Dto = equityList.get(i);
            equityInfo001Dto.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            if(nullFalg){
                if(mst.containsKey(equityInfo001Dto.getIndentifyMark())){
                    map=mst.get(equityInfo001Dto.getIndentifyMark()).get(0);
                }
            }else{
                    map = listType.get(i);

            }
            List<EquityInfoRegist001Dto> listRegistDto = mapRegist.get(equityInfo001Dto.getBankCode() + GlobalConstant.VERTICAL_LINE
                    + GlobalConstant.VERTICAL_LINE + equityInfo001Dto.getIndentifyMark());
            if(null == listRegistDto || listRegistDto.size() == 0 ) {
                listRegistDto = mapRegist.get(equityInfo001Dto.getBankCode() + GlobalConstant.VERTICAL_LINE
                        + GlobalConstant.VERTICAL_LINE + equityInfo001Dto.getOrgIndentifyMark());
            }
            String statusType = map.get("type");
            // 数据未发生改变，不进行数据更新
//            if ("01".equals(statusType) && "01".equals(map.get("flag"))) {
//                continue;
//            }
            //四期涉及到字段，放置重新导入覆盖修复内容。
            if(null != listRegistDto && null != listRegistDto.get(0)) {
                if(StringUtil.isEmpty(equityInfo001Dto.getTheBankCode())) {
                    equityInfo001Dto.setTheBankCode(listRegistDto.get(0).getTheBankCode());
                }
                if(StringUtil.isEmpty(equityInfo001Dto.getFundAccPlace())) {
                    equityInfo001Dto.setFundAccPlace(listRegistDto.get(0).getFundAccPlace());
                }
                if(StringUtil.isEmpty(equityInfo001Dto.getHolderClassify())) {
                    equityInfo001Dto.setHolderClassify(listRegistDto.get(0).getHolderClassify());
                }
            }

            String type = StringUtil.EMPTY;
            if (DictCodeListExt2.报送数据状态_新建.getValue().equals(statusType)) {
                type = DictCodeListExt2.报送数据状态_新建.getValue();
            }
            else {
                type = DictCodeListExt2.报送数据状态_修改.getValue();
            }
            equityInfo001Dto.setSubmitStatus(type);
            // 报送时点通用方法
            dateList = ChbDeadlineUtil.getChbEndDate(
                    DictCodeListExt2.报表名称_投资者身份信息登记.getValue(), type, dateMap, DateUtil.now());
            if (CollectionUtils.isNotEmpty(dateList)) {
                equityInfo001Dto.setChbSubmitDeadline(dateList.get(0));
                equityInfo001Dto.setSubmitDeadline(dateList.get(1));
            }

            equityInfo001Dto.setSumitSeq(
                    //CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
                    CommonUtil.getSequenceStr(sequenceList, i)
            );
            equityInfo001Dto.setType(statusType);
            equityInfo001Dto.setDeptCode(deptCode);
            equityInfo001Dto.setDataSource(DictCodeListExt2.数据来源_EXCEL导入.getValue());
            equityInfo001Dto.setOrgCode(orgCode);

            EquityInfo001Dto equityCheck = equityInfoMap.get(
                    equityInfo001Dto.getIndentifyMark() + "_" + equityInfo001Dto.getBankCode());
            EquityInfo001Dto equityCheckByOrg = equityInfoMap.get(
                    equityInfo001Dto.getOrgIndentifyMark() + "_" + equityInfo001Dto.getBankCode());
            // 插入前维护表中无数据，将dto放入listN
            if (equityCheck == null && equityCheckByOrg == null) {
                listN.add(equityInfo001Dto);
            }
            // if(EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getImportStatus())){
//            EquityInfo001Dto equityInfoCheck = equityInfo001Mapper.getEquityInfo001DtoByKey(
//                    equityInfo001Dto);
            // 插入前维护表中无数据，将dto放入listN
//            if (equityInfoCheck == null) {
//                listN.add(equityInfo001Dto);
//            }
            //识别标识变更
            else if(DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(statusType)){
                EquityInfo001Dto equityInfo001DtoI = new EquityInfo001Dto();
                equityInfo001DtoI.setBankCode(equityInfo001Dto.getBankCode());
                equityInfo001DtoI.setRemark(equityInfo001Dto.getRemark());
                equityInfo001DtoI.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
                equityInfo001DtoI.setOrgIndentifyMark(equityInfo001Dto.getOrgIndentifyMark());
                equityInfo001DtoI.setDataSource(equityInfo001Dto.getDataSource());
                equityInfo001DtoI.setDeptCode(deptCode);
                equityInfo001DtoI.setOrgCode(equityInfo001Dto.getOrgCode());
                equityInfo001DtoI.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
                equityInfo001DtoI.setUpdateTime(DateUtil.now());
                equityInfo001DtoI.setType(statusType);
                //添加  账号开户所在地/投资者类型 入库
                equityInfo001DtoI.setHolderClassify(equityInfo001Dto.getHolderClassify());
                equityInfo001DtoI.setFundAccPlace(equityInfo001Dto.getFundAccPlace());
                listI.add(equityInfo001DtoI);
            }
            // 插入前维护表有待处理，将dto放入listN
            else if ("A".equals(equityCheck.getStatus())) {
                listA.add(equityInfo001Dto);
            }
            // 插入前维护表有除待处理，将dto放入listN
            else {
                if(DictCodeListExt2.数据类型_中债_四期信息变更.getValue().equals(statusType)) {
                    listUpForInfo.add(equityInfo001Dto);
                }else {
                    listO.add(equityInfo001Dto);
                }
            }

            if(DictCodeListExt2.数据类型_中债_四期信息变更.getValue().equals(statusType)) {
                listUpForSub.add(equityInfo001Dto);
            }else if (!("01".equals(statusType) &&"01".equals(map.get("flag")))) {
                EquityInfo001Dto  equityInfo001DtoTemp = new EquityInfo001Dto();
                BeanUtil.copyPropertiesIgnoreNull(equityInfo001DtoTemp, equityInfo001Dto);
                equityInfo001DtoTemp.setCheckSeqno("0");
                equityInfo001DtoTemp.setStatus(type);
                equityInfo001DtoTemp.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                if(equityInfo001DtoTemp.getTempMap()!=null){
                    for(String key:equityInfo001DtoTemp.getTempMap().keySet()){
                        EquityInfoCheckUtil.updateChcekDto(equityInfo001DtoTemp,key,equityInfo001DtoTemp.getTempMap().get(key));
                    }
                }
                listU.add(equityInfo001DtoTemp);
            }
//            if (("01".equals(statusType) && !"01".equals(map.get("flag")))
//                || "03".equals(statusType)) {
//                equityInfo001Dto.setCheckSeqno("0");
//                listU.add(equityInfo001Dto);
//            }
//            else if ("02".equals(statusType)) {
//                EquityInfo001Dto equityInfo001Dto1 = new EquityInfo001Dto();
//                equityInfo001Dto1.setBankCode(equityInfo001Dto.getBankCode());
//                equityInfo001Dto1.setIndentifyMark(
//                    equityInfo001Dto.getIndentifyMark());
//                equityInfo001Dto1.setOrgIndentifyMark(
//                    equityInfo001Dto.getOrgIndentifyMark());
//                equityInfo001Dto1.setType(statusType);
//                equityInfo001Dto1.setOrgCode(CommonUtil.getOrgCode());
//                equityInfo001Dto1.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                equityInfo001Dto1.setRemark(map.get("REMARK"));
//                equityInfo001Dto1.setSumitSeq(equityInfo001Dto.getSumitSeq());
//                equityInfo001Dto1.setSubmitDeadline(
//                    equityInfo001Dto.getSubmitDeadline());
//                equityInfo001Dto1.setChbSubmitDeadline(
//                    equityInfo001Dto.getChbSubmitDeadline());
//                equityInfo001Dto1.setDeptCode(CommonUtil.getDeptCode());
//                equityInfo001Dto1.setDataSource("02");
//                equityInfo001Dto1.setCheckSeqno("0");
//                listU.add(equityInfo001Dto1);
//            }
//            else if ("04".equals(statusType)) {
//                EquityInfo001Dto equityInfo001Dto1 = new EquityInfo001Dto();
//                equityInfo001Dto1.setBankCode(equityInfo001Dto.getBankCode());
//                equityInfo001Dto1.setIndentifyMark(
//                    equityInfo001Dto.getIndentifyMark());
//                equityInfo001Dto1.setType(statusType);
//                equityInfo001Dto1.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                equityInfo001Dto1.setRemark(map.get("REMARK"));
//                equityInfo001Dto1.setHolderRiskPrefer(EquityInfoCheckUtil.subDictcode(
//                		equityInfo001Dto.getHolderRiskPrefer(), " "));
//                equityInfo001Dto1.setHolderCellphone(equityInfo001Dto.getHolderCellphone());
//                equityInfo001Dto1.setHolderTelephone(equityInfo001Dto.getHolderTelephone());
//                equityInfo001Dto1.setHolderEmail(equityInfo001Dto.getHolderEmail());
//                equityInfo001Dto1.setSumitSeq(equityInfo001Dto.getSumitSeq());
//                equityInfo001Dto1.setOrgCode(CommonUtil.getOrgCode());
//                equityInfo001Dto1.setDeptCode(CommonUtil.getDeptCode());
//                equityInfo001Dto1.setSubmitDeadline(
//                    equityInfo001Dto.getSubmitDeadline());
//                equityInfo001Dto1.setDataSource("02");
//                equityInfo001Dto1.setChbSubmitDeadline(
//                    equityInfo001Dto.getChbSubmitDeadline());
//                equityInfo001Dto1.setCheckSeqno("0");
//                listU.add(equityInfo001Dto1);
//            }
        }
        // 插入的权益人信息为空时,先插入登记维护表，再插入信息登记表
        if (listN != null && listN.size() > 0) {
            // 插入维护信息表
            // equityInfo001Mapper.insertBtachEquityInfo(listN);
            this.insertBtachEquityInfoByPart(listN);
            equityInfoDistinctService.refreshIndentifyMark(listN,0);
        }
        // 插入信息前为待处理时，先跟新维护表，在插入更新表
        if (listA != null && listA.size() > 0) {
            // 插入维护信息表
            //modify by wangmin 将一次性、删除修改为分批次的操作 start 2018-07-25
            //equityInfo001Mapper.updateBatchEquityInfoExcel(listA);
            this.updateBtachEquityInfoExcelByPart(listA);
            // 删除登记表中未报送数据(若数据由有效转为待处理后，又插入一条有效数据)
            //equityInfoRegist001Mapper.deleteBatchEquityRegist(listA);
            this.deleteBatchEquityRegist(listA);
            //modify by wangmin 将一次性、删除修改为分批次的操作 end 2018-07-25
        }
        // 当插入信息为有效或者删除是，先将信息迁移至履历表，再更新信息维护表，之后先统一删除登记表中报送中的数据，插入登记表
        if (listO != null && listO.size() > 0) {
            // 首先将权益人信息表插入履历表中
            //modify by wangmin 将一次性的插入、删除、修改为分批次的操作 start 2018-07-25
            //equityInfo001Mapper.insertEquityHis(listO);
            this.insertEquityHis(listO);
            // 插入维护信息表
            //equityInfo001Mapper.updateBatchEquityInfoExcel(listO);
            this.updateBtachEquityInfoExcelByPart(listO);
            // 删除登记表中未报送数据
            //equityInfoRegist001Mapper.deleteBatchEquityRegist(listO);
            this.deleteBatchEquityRegist(listO);
            //modify by wangmin 将一次性的插入、删除、修改为分批次的操作 end 2018-07-25
        }
        //识别标识变更
        if(CollectionUtils.isNotEmpty(listI)){
            // 首先将权益人信息表插入履历表中
            equityInfo001Mapper.insertEquityIdHis(listI);
            //更新主表信息
            equityInfo001Mapper.updateBatchEquityInfo(listI);
        }
        if (CollectionUtils.isNotEmpty(listU)) {
            // equityInfoRegist001Mapper.insertBatchEquityRegist(listU);
            //删除报送前检查的数据
            //检查序列号list(更新登记表数据时对应删除报送前检查已有的信息)
            //List<String> checkSeqnoList = equityInfo001Mapper.selectCheckSeqno(listU);
            List<String> checkSeqnoList = this.selectBatchCheckSeqno(listU);
            if(CollectionUtils.isNotEmpty(checkSeqnoList)){
                //每一百条处理一次
                if(checkSeqnoList.size()<100){
                    chbReportCheckMapper.delReportCheckDatas(checkSeqnoList);
                }
                else{
                    int part = checkSeqnoList.size()/100;
                    for(int i =0; i<part; i++){
                        chbReportCheckMapper.delReportCheckDatas(checkSeqnoList.subList(0, 100));
                        checkSeqnoList.subList(0, 100).clear();
                    }
                    if(CollectionUtils.isNotEmpty(checkSeqnoList)){
                        chbReportCheckMapper.delReportCheckDatas(checkSeqnoList);
                    }
                }
            }
            this.deleteBatchEquityRegist(listU);
            this.insertBatchEquityRegistByPart(listU);
            equityInfoDistinctService.refreshIndentifyMark(listU,1);
        }

        //只有四期信息发生变更的情况下，对INFO表进行更新变更字段
        if (CollectionUtils.isNotEmpty(listUpForInfo)) {
            logger.info("EquityInfo001Service listUpForInfo 四期INFO更新开始 " + DateUtil.getCurrentDateTime());

            // 更新身份信息INFO表
            this.updateEuqHoldInfoByBatch(listUpForInfo);
            logger.info("EquityInfo001Service listUpForInfo 四期INFO更新结束 " + DateUtil.getCurrentDateTime());
        }

        //只有四期信息发生变更的情况下，对SUB表进行更新变更字段
        if (CollectionUtils.isNotEmpty(listUpForSub)) {
            logger.info("EquityInfo001Service listUpForSub 四期SUB更新开始 " + DateUtil.getCurrentDateTime());

            // 更新身份信息SUB表
            this.updateEuqHoldSubByBatch(listUpForSub);

            logger.info("EquityInfo001Service listUpForSub 四期SUB更新结束 " + DateUtil.getCurrentDateTime());
        }
    }

    /**
     * 分配查询序列号
     *
     * @param listU 传入条件
     * @return 返回查询结果
     * @see
     * @since 1.0
     */
    private List<String> selectBatchCheckSeqno(List<EquityInfo001Dto> list) {
//
//        int maxDataCountOneTime = 200;
//        int dataCount = listU.size();
//        if (dataCount <= maxDataCountOneTime) {
//            return equityInfo001Mapper.selectCheckSeqno(listU);
//        }
//        //临时list
//    	List<EquityInfo001Dto> list = new ArrayList<EquityInfo001Dto>();
//        list.addAll(listU);
//        // 导入大于200时，返回值resultList
//        List<String> resultList = new ArrayList<String>();
//        // 分批处理
//        int part = dataCount / maxDataCountOneTime; // 分批数
//        //临时存储list
//        List<String> listTemp = null;
//        for (int i = 0; i < part; i++ ) {
//            listTemp = equityInfo001Mapper.selectCheckSeqno(
//                list.subList(0, maxDataCountOneTime));
//            // 不为空，放入返回list中
//            if (CollectionUtils.isNotEmpty(listTemp)) {
//                resultList.addAll(listTemp);
//            }
//            list.subList(0, maxDataCountOneTime).clear();
//        }
//        // 最后剩下的数据
//        if (CollectionUtils.isNotEmpty(list)) {
//            listTemp = equityInfo001Mapper.selectCheckSeqno(list);
//            // 不为空，放入返回list中
//            if (CollectionUtils.isNotEmpty(listTemp)) {
//                resultList.addAll(listTemp);
//            }
//        }
//        return resultList;
        //ADD BY DENGYU 多线程改造

        int maxDataCountOneTime = 200;

        List<Callable<Boolean>> tasks = new ArrayList<>();

        List<String> result = Collections.synchronizedList(new ArrayList<String>());
        if (null != list) {
            ExecutorService service = new JoyinThreadPoolExecutor(10);
            int start = 0;
            int end = 0;
            int size = list.size();
            int max = (int) Math.ceil((double) size / maxDataCountOneTime);
            // 信号量
            CountDownLatch countDown = new CountDownLatch(max);

            for (int i = 0; i < max; i++) {
                start = end;
                end = Math.min(size, maxDataCountOneTime * (i + 1));
                List<EquityInfo001Dto> subList = list.subList(start, end);

                // 提交任务
                tasks.add(new Callable<Boolean>() {

                    @Override
                    public Boolean call() throws Exception {
                        result.addAll(equityInfo001Mapper.selectCheckSeqno(subList));
                        countDown.countDown();// 个数-1
                        return true;
                    }
                });
            }
            // 添加线程等待转换线执行
            try {
                service.invokeAll(tasks);
                countDown.await();
                service.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 分批次执行insertBtachEquityInfo
     *List<? extends Object> dataList
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void insertBtachEquityInfoByPart(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }
        //获取配置项内容
        int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
//        if(StringUtil.isNotEmpty(reportInsertLongCount)&&Integer.parseInt(reportInsertLongCount)<=0){
//        	maxDataCountOneTime = 20;
//        }
//        else{
//        	maxDataCountOneTime = Integer.parseInt(reportInsertLongCount);
//        }
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfo001Mapper.insertBtachEquityInfo(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfo001Mapper.insertBtachEquityInfo(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfo001Mapper.insertBtachEquityInfo(newDataList);
        }
    }
    /**
     * 分批次执行insertEquityHis
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void insertEquityHis(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }

        int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfo001Mapper.insertEquityHis(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfo001Mapper.insertEquityHis(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfo001Mapper.insertEquityHis(newDataList);
        }
    }
    /**
     * 分批次执行deleteBatchEquityRegist
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void deleteBatchEquityRegist(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }
        int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount,"2");
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfoRegist001Mapper.deleteBatchEquityRegist(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfoRegist001Mapper.deleteBatchEquityRegist(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfoRegist001Mapper.deleteBatchEquityRegist(newDataList);
        }
    }
    /**
     * 分批次执行updateBatchEquityInfoExcel
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void updateBtachEquityInfoExcelByPart(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }

        int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");

        // db2批量更新报错，一条一条处理
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();
        if (isDB2DataBase) {
            maxDataCountOneTime = 1;
        }

        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfo001Mapper.updateBatchEquityInfoExcel(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfo001Mapper.updateBatchEquityInfoExcel(newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfo001Mapper.updateBatchEquityInfoExcel(newDataList);
        }
    }
    /**
     * 分批次执行updateBatchEquityInfo 接口方法
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void updateBtachEquityInfoByPart(List<EquityInfo001Dto> dataList ,boolean isDB2DataBase) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }
        //db2暂时一条一条写入
        if(isDB2DataBase){
            for (EquityInfo001Dto equityInfo001Dto : dataList) {
                equityInfo001Mapper.updateBatchEquityInfoTemp(equityInfo001Dto);
            }
        }
        else{
            int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
            int dataCount = dataList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityInfo001Mapper.updateBatchEquityInfo(dataList);
                return;
            }

            List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
            newDataList.addAll(dataList);

            // 分批处理
            int part = dataCount / maxDataCountOneTime; // 分批数
            for (int i = 0; i < part; i++ ) {
                equityInfo001Mapper.updateBatchEquityInfo(newDataList.subList(0, maxDataCountOneTime));
                newDataList.subList(0, maxDataCountOneTime).clear();
            }
            // 最后剩下的数据
            if (CollectionUtils.isNotEmpty(newDataList)) {
                equityInfo001Mapper.updateBatchEquityInfo(newDataList);
            }
        }
    }
    /**
     * 分批次执行insertBatchEquityRegist
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void insertBatchEquityRegistByPart(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }

        int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfoRegist001Mapper.insertBatchEquityRegist(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfoRegist001Mapper.insertBatchEquityRegist(
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfoRegist001Mapper.insertBatchEquityRegist(newDataList);
        }
    }

    /**
     * 分批次对INFO表进行更新
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void updateEuqHoldInfoByBatch(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }

        int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfoRegist001Mapper.updateEquityInfoForBatch(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfoRegist001Mapper.updateEquityInfoForBatch(
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfoRegist001Mapper.updateEquityInfoForBatch(newDataList);
        }
    }

    /**
     * 分批次对SUB表进行更新
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void updateEuqHoldSubByBatch(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }

        int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfoRegist001Mapper.updateEquitySubForBatch(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfoRegist001Mapper.updateEquitySubForBatch(
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfoRegist001Mapper.updateEquitySubForBatch(newDataList);
        }
    }

    /**
     * 分批次执行insertBtachInterfEquityInfo
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void insertBtachInterfEquityInfoByPart(List<EquityInfo001Dto> dataList) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }

        int maxDataCountOneTime = this.parsToInt(reportInsertLongCount,"1");
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfo001Mapper.insertBtachInterfEquityInfo(dataList);
            return;
        }

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfo001Mapper.insertBtachInterfEquityInfo(
                    newDataList.subList(0, maxDataCountOneTime));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfo001Mapper.insertBtachInterfEquityInfo(newDataList);
        }
    }
    /**
     * 检查删除数据的合法性
     *
     * @param list 需要验证的数据
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ValidMessage> checkDeleteData(List<EquityInfo001Dto> list) {
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();
        String key = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, ReportGlobalConstant.SYS_PARA_HOLD_INFO_SOURCE);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("dealDate", DateUtil.parse(DateUtil.getCurrentDate()));
        param.put("unSubmit",DictCodeListExt2.报送状态_未报送.getValue());
        param.put("dataList",list);
        param.put("statusD",DictCodeListExt2.报送数据状态_删除.getValue());
        List<Map<String,String>> result = new ArrayList<>();
        if(StringUtil.equals(key, DictCodeListExt1.持有信息数据来源_业务明细.getValue())){
            //当日有效的 业务明细信息是否存在
            List<Map<String,String>> temp = equityInfo001Mapper.checkDetailData(param);
            if(CollectionUtils.isNotEmpty(temp)){
                result.addAll(temp);
            }
        }
        //检查持有数量信息是否有未报送数据
        List<Map<String,String>> tempList = equityInfo001Mapper.checkHoldData(param);
        if(CollectionUtils.isNotEmpty(tempList)){
            result.addAll(tempList);
        }
        //检查持有数量已报送数据是否为零
        List<Map<String,String>> subList = equityInfo001Mapper.checkHoldDataSubmit(param);
        if(CollectionUtils.isNotEmpty(subList)){
            result.addAll(subList);
        }
        if(CollectionUtils.isNotEmpty(result)){
            Set<String> keySet = new HashSet<>();
            result.forEach(action->{
                keySet.add(action.get("INDENTIFY_MARK"));
            });
            StringBuffer sb = new StringBuffer();
            for(String temp:keySet){
                sb.append(temp+", ");
            }
            sb.deleteCharAt(sb.lastIndexOf(", "));
            errorList.add(new ValidMessage(getMessage("REPORT_I_0014", sb.toString())));
        }
        return errorList;
    }

    /**
     * 设置查询参数
     *
     * @param param 参数
     */
    private void setSearchParam(Map<String, Object> param) {
        if (null != param) {
            String orgCode = (String)param.get("orgCode");
            String status = (String)param.get("status");
            String holderType = (String)param.get("holderType");
            List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
            if (StringUtil.isBlank(orgCode)) {
                orgCodeList = CommonUtil.getOrgCodeListForSearch();
            }
            param.put("orgCodeList", orgCodeList);
            param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            param.put("holderTypeList", StringUtil.splitToList(GlobalConstant.COMMA, holderType));
        }
    }

    /**
     * 格式化数据字典值
     * <p>
     * 将数据字典code转化为文字形式，用于画面显示或者导出excel
     * </p>
     *
     * @param dataList 数据列表
     * @param isSearch 是否为查询画面显示
     */
    private void format(List<EquityInfo001Dto> dataList, boolean isSearch) {
        if (null != dataList) {
            for (EquityInfo001Dto equityInfo001Dto : dataList) {
                if (isSearch) {
                    equityInfo001Dto.setStatus(SysDataUtil.getNameFromDataDict(DictCode.维护数据状态,
                            equityInfo001Dto.getStatus()));
                    equityInfo001Dto.setIsBelongBank(SysDataUtil.getNameFromDataDict(
                            DictCode.是否_中债, equityInfo001Dto.getIsBelongBank()));
                    equityInfo001Dto.setHolderMark(SysDataUtil.getNameFromDataDict(
                            DictCode.境内外, equityInfo001Dto.getHolderMark()));
                    equityInfo001Dto.setHolderSex(SysDataUtil.getNameFromDataDict(DictCode.性别_中债,
                            equityInfo001Dto.getHolderSex()));
                    equityInfo001Dto.setHolderRegion(SysDataUtil.getNameFromDataDict(
                            DictCode.所属国家或地区, equityInfo001Dto.getHolderRegion()));
                    equityInfo001Dto.setSpvCapAccBank(SysDataUtil.getNameFromDataDict(
                            DictCode.SPV资金托管账户开户行, equityInfo001Dto.getSpvCapAccBank()));
                    equityInfo001Dto.setHolderType(SysDataUtil.getNameFromDataDict(DictCode.投资者类别,
                            equityInfo001Dto.getHolderType()));
                    equityInfo001Dto.setHolderClassify(SysDataUtil.getNameFromDataDict(DictCode.投资者分类,
                            equityInfo001Dto.getHolderClassify()));
                    equityInfo001Dto.setFundAccPlace(SysDataUtil.getNameFromDataDict(DictCode.销售区域_中债,
                            equityInfo001Dto.getFundAccPlace()));
                    if (StringUtil.isNotEmpty(equityInfo001Dto.getPersonalIdentifyType())) {
                        equityInfo001Dto.setInentifyType(SysDataUtil.getNameFromDataDict(
                                DictCode.个人证件类别, equityInfo001Dto.getPersonalIdentifyType()));
                        if(equityInfo001Dto.getStatus().equals("有效")) {
                            equityInfo001Dto.setInterfCheckResult(" ");
                        }
                    }
                    else {
                        equityInfo001Dto.setInentifyType(SysDataUtil.getNameFromDataDict(
                                DictCode.机构证件类别, equityInfo001Dto.getInstitutionIdentifyType()));
                    }
                }
                else {
                    equityInfo001Dto.setIsBelongBank(SysDataUtil.getZZDictCodeAndName(
                            DictCode.是否_中债, equityInfo001Dto.getIsBelongBank()));
                    equityInfo001Dto.setHolderMark(SysDataUtil.getZZDictCodeAndName(
                            DictCode.境内外, equityInfo001Dto.getHolderMark()));
                    equityInfo001Dto.setHolderSex(SysDataUtil.getZZDictCodeAndName(DictCode.性别_中债,
                            equityInfo001Dto.getHolderSex()));
                    equityInfo001Dto.setHolderRegion(SysDataUtil.getZZDictCodeAndName(
                            DictCode.所属国家或地区, equityInfo001Dto.getHolderRegion()));
                    equityInfo001Dto.setSpvCapAccBank(SysDataUtil.getZZDictCodeAndName(
                            DictCode.SPV资金托管账户开户行, equityInfo001Dto.getSpvCapAccBank()));
                    equityInfo001Dto.setHolderType(SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                            equityInfo001Dto.getHolderType()));
                    equityInfo001Dto.setHolderClassify(SysDataUtil.getZZDictCodeAndName(DictCode.投资者分类,
                            equityInfo001Dto.getHolderClassify()));
                    equityInfo001Dto.setFundAccPlace(SysDataUtil.getZZDictCodeAndName(DictCode.销售区域_中债,
                            equityInfo001Dto.getFundAccPlace()));
                    equityInfo001Dto.setHolderRiskPrefer(SysDataUtil.getZZDictCodeAndName(
                            DictCode.投资风险偏好, equityInfo001Dto.getHolderRiskPrefer()));
                    equityInfo001Dto.setPersonalIdentifyType(SysDataUtil.getZZDictCodeAndName(
                            DictCode.个人证件类别, equityInfo001Dto.getPersonalIdentifyType()));
                    equityInfo001Dto.setInstitutionIdentifyType(SysDataUtil.getZZDictCodeAndName(
                            DictCode.机构证件类别, equityInfo001Dto.getInstitutionIdentifyType()));
                }
            }
        }
    }

    /**
     * list转换为Bean
     *
     * @param dataList excel解析后的list
     * @return List<EquityInfo001Dto>
     * @see
     * @since 1.0
     */
    private List<EquityInfo001Dto> converToBean(List<List<String>> dataList,String orgCode) {
        String templateFlag = SvrDataUtil.getSysParam("SVR_EQU_INFO_TEMPLATE_FLAG");
        // 返回的list定义
        List<EquityInfo001Dto> returnInfoList = new ArrayList<EquityInfo001Dto>();
        EquityInfo001Dto equityInfo001Dto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            // mysql时去除空字符串
            if(BaseCommonCheckService.isMYSQLDataBase()){
                BaseCommonCheckService.listRemoveEmptyString(list);
            }
            equityInfo001Dto = new EquityInfo001Dto();
            equityInfo001Dto.setBankCode(list.get(0));
            equityInfo001Dto.setIndentifyMark(list.get(1));
            equityInfo001Dto.setOrgIndentifyMark(list.get(2));
            if (!EquityInfoCheckUtil.isEmpty(list.get(3))) {
                equityInfo001Dto.setIsBelongBank(
                        EquityInfoCheckUtil.subDictcode(list.get(3), " "));
            }
            equityInfo001Dto.setTheBankName(list.get(4));
            equityInfo001Dto.setTheBankCode(list.get(5));
            if (!EquityInfoCheckUtil.isEmpty(list.get(6))) {
                equityInfo001Dto.setHolderMark(EquityInfoCheckUtil.subDictcode(list.get(6), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(7))) {
                equityInfo001Dto.setHolderRegion(
                        EquityInfoCheckUtil.subDictcode(list.get(7), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(8))) {
                equityInfo001Dto.setHolderType(EquityInfoCheckUtil.subDictcode(list.get(8), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(9))) {
                equityInfo001Dto.setPersonalIdentifyType(
                        EquityInfoCheckUtil.subDictcode(list.get(9), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(10))) {
                equityInfo001Dto.setInstitutionIdentifyType(
                        EquityInfoCheckUtil.subDictcode(list.get(10), " "));
            }
            equityInfo001Dto.setOtherIdentifyName(list.get(11));
            equityInfo001Dto.setIdentifyNumber(list.get(12));
            if (!EquityInfoCheckUtil.isEmpty(list.get(13))) {
                equityInfo001Dto.setSpvCapAccBank(
                        EquityInfoCheckUtil.subDictcode(list.get(13), " "));
            }
            equityInfo001Dto.setOtherCapAccBank(list.get(14));
//            equityInfo001Dto.setSpvRegistCode(list.get(15));
            //去除投资者名称空格
            if(StringUtil.equals(DictCodeList.境内外_境内.getValue(), equityInfo001Dto.getHolderMark())
                    &&StringUtil.isNotEmpty(list.get(15))){
                equityInfo001Dto.setHolderName(list.get(15).replace(StringUtil.SPACE, StringUtil.EMPTY));
            }
            else{
                equityInfo001Dto.setHolderName(list.get(15));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(16))) {
                equityInfo001Dto.setHolderSex(EquityInfoCheckUtil.subDictcode(list.get(16), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(17))) {
                equityInfo001Dto.setHolderRiskPrefer(
                        EquityInfoCheckUtil.subDictcode(list.get(17), " "));
            }
            equityInfo001Dto.setHolderCellphone(list.get(18));
            equityInfo001Dto.setHolderTelephone(list.get(19));
            equityInfo001Dto.setHolderEmail(list.get(20));
            equityInfo001Dto.setRemark(list.get(21));
            equityInfo001Dto.setAgent(list.get(23));
            equityInfo001Dto.setOrgCode(orgCode);
            //投资者分类
            if (!EquityInfoCheckUtil.isEmpty(list.get(22))) {
                equityInfo001Dto.setHolderClassify(
                        EquityInfoCheckUtil.subDictcode(list.get(22), " "));
            }

            if("02".equals(templateFlag)) {
                //开户行所在地
                equityInfo001Dto.setFundAccPlace(list.get(26));
            }
            // 增加行号
            equityInfo001Dto.setExcelNum(String.valueOf(i + 2));
            //检查序列号设置初始值
            equityInfo001Dto.setCheckSeqno("0");
            returnInfoList.add(equityInfo001Dto);
        }
        return returnInfoList;
    }

    /**
     * list转换为Bean
     *
     * @param dataList excel解析后的list
     * @return List<EquityInfo001Dto>
     * @see
     * @since 1.0
     */
    private List<EquityInfo001Dto> converToBeanForChb(List<List<String>> dataList,String orgCode) {
        // 返回的list定义
        List<EquityInfo001Dto> returnInfoList = new ArrayList<EquityInfo001Dto>();
        EquityInfo001Dto equityInfo001Dto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            equityInfo001Dto = new EquityInfo001Dto();
            equityInfo001Dto.setBankCode(list.get(0));
            equityInfo001Dto.setIndentifyMark(list.get(1));
            equityInfo001Dto.setOrgIndentifyMark(list.get(2));
            if (!EquityInfoCheckUtil.isEmpty(list.get(3))) {
                equityInfo001Dto.setIsBelongBank(
                        EquityInfoCheckUtil.subDictcode(list.get(3), " "));
            }
            equityInfo001Dto.setTheBankName(list.get(4));
            equityInfo001Dto.setTheBankCode(list.get(5));
            if (!EquityInfoCheckUtil.isEmpty(list.get(6))) {
                equityInfo001Dto.setHolderMark(EquityInfoCheckUtil.subDictcode(list.get(6), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(7))) {
                equityInfo001Dto.setHolderRegion(
                        EquityInfoCheckUtil.subDictcode(list.get(7), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(8))) {
                equityInfo001Dto.setHolderType(EquityInfoCheckUtil.subDictcode(list.get(8), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(9))) {
                equityInfo001Dto.setPersonalIdentifyType(
                        EquityInfoCheckUtil.subDictcode(list.get(9), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(10))) {
                equityInfo001Dto.setInstitutionIdentifyType(
                        EquityInfoCheckUtil.subDictcode(list.get(10), " "));
            }
            equityInfo001Dto.setOtherIdentifyName(list.get(11));
            equityInfo001Dto.setIdentifyNumber(list.get(12));
            if (!EquityInfoCheckUtil.isEmpty(list.get(13))) {
                equityInfo001Dto.setSpvCapAccBank(
                        EquityInfoCheckUtil.subDictcode(list.get(13), " "));
            }
            equityInfo001Dto.setOtherCapAccBank(list.get(14));
//            equityInfo001Dto.setSpvRegistCode(list.get(15));
            //去除投资者名称空格
            if(StringUtil.equals(DictCodeList.境内外_境内.getValue(), equityInfo001Dto.getHolderMark())
                    &&StringUtil.isNotEmpty(list.get(15))){
                equityInfo001Dto.setHolderName(list.get(15).replace(StringUtil.SPACE, StringUtil.EMPTY));
            }
            else{
                equityInfo001Dto.setHolderName(list.get(15));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(16))) {
                equityInfo001Dto.setHolderSex(EquityInfoCheckUtil.subDictcode(list.get(16), " "));
            }
            if (!EquityInfoCheckUtil.isEmpty(list.get(17))) {
                equityInfo001Dto.setHolderRiskPrefer(
                        EquityInfoCheckUtil.subDictcode(list.get(17), " "));
            }
            equityInfo001Dto.setHolderCellphone(list.get(18));
            equityInfo001Dto.setHolderTelephone(list.get(19));
            equityInfo001Dto.setHolderEmail(list.get(20));
            equityInfo001Dto.setRemark(list.get(21));
            equityInfo001Dto.setAgent(list.get(23));
            equityInfo001Dto.setOrgCode(orgCode);
            //投资者分类
            if (!EquityInfoCheckUtil.isEmpty(list.get(22))) {
                equityInfo001Dto.setHolderClassify(
                        EquityInfoCheckUtil.subDictcode(list.get(22), " "));
            }
            // 增加行号
            equityInfo001Dto.setExcelNum(String.valueOf(i + 2));
            //检查序列号设置初始值
            equityInfo001Dto.setCheckSeqno("0");
            returnInfoList.add(equityInfo001Dto);
        }
        return returnInfoList;
    }

    /**
     * 获取配置文件中的字段
     *
     * @param interType 数据来源
     * @param map 优先级map
     * @return 配置表中数组
     * @see
     * @since 1.0
     */
    public String[] getProPrity(String interType, Map<String, Integer> map) {
        String[] result = ReadConfigurationUtil.readPropertiesByGetResourceAsStream(
                CHB_EQU_SUB + "." + interType).split(GlobalConstant.COMMA);
        map.put(interType, Integer.parseInt(result[0]));
        if (!interType.equals(DictCodeListExt2.数据来源_销售系统.getValue())) {
            String resut1 = ReadConfigurationUtil.readPropertiesByGetResourceAsStream(
                    CHB_EQU_SUB + "." + DictCodeListExt2.数据来源_销售系统.getValue());
            if (StringUtil.isNotEmpty(resut1)) {
                map.put(DictCodeListExt2.数据来源_销售系统.getValue(),
                        Integer.parseInt(resut1.split(GlobalConstant.COMMA)[0]));
            }
        }
        else if (!interType.equals(DictCodeListExt2.数据来源_客户关系管理系统.getValue())) {
            String resut2 = ReadConfigurationUtil.readPropertiesByGetResourceAsStream(
                    CHB_EQU_SUB + "." + DictCodeListExt2.数据来源_客户关系管理系统.getValue());
            if (StringUtil.isNotEmpty(resut2)) {
                map.put(DictCodeListExt2.数据来源_客户关系管理系统.getValue(),
                        Integer.parseInt(resut2.split(GlobalConstant.COMMA)[0]));
            }
        }
        else if (!interType.equals(DictCodeListExt2.数据来源_核心系统.getValue())) {
            String resut3 = ReadConfigurationUtil.readPropertiesByGetResourceAsStream(
                    CHB_EQU_SUB + "." + DictCodeListExt2.数据来源_核心系统.getValue());
            if (StringUtil.isNotEmpty(resut3)) {
                map.put(DictCodeListExt2.数据来源_核心系统.getValue(),
                        Integer.parseInt(resut3.split(GlobalConstant.COMMA)[0]));
            }
        }
        return result;
    }

    /**
     * 通过开关及数据来源判断更新的值
     *
     * @param equityInfoCheck check数据
     * @param equityInfo001Dto 传入元数据
     * @param map 接口中的map
     * @param result 配置文件中字段
     * @param list 存储数据库中list
     * @param interType 系统来源
     * @param param 优先级
     * @see
     * @since 1.0
     */
    public void getEquityInfo001DtoByIsOpen(EquityInfo001Dto equityInfoCheck,
                                            EquityInfo001Dto equityInfo001Dto,
                                            Map<String, String> map, String[] result,
                                            List<EquityInfo001Dto> list, String interType,
                                            Map<String, Integer> param) {
        if ("01".equals(equityInfoCheck.getDataSource())
                || "02".equals(equityInfoCheck.getDataSource())) {
            EquityInfo001Dto equityInfo = new EquityInfo001Dto();
            EquityInfoMaintenanceService.maptoSetDto(map, equityInfo, result);
            equityInfo.setStatus(equityInfo001Dto.getStatus());
            equityInfo.setType(equityInfo001Dto.getType());
            equityInfo.setDataSource("12");
            equityInfo.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
            equityInfo.setChbSubmitDeadline(equityInfo001Dto.getChbSubmitDeadline());
            list.add(equityInfo);
        }
        // 覆盖开关打开，兵器数据来源为接口，则全量覆盖
        else {
            list.add(equityInfo001Dto);
        }
    }


    /**
     * 从接口中获取权益人信息（数据开关关闭不覆盖）
     * <ul>
     * <li>1.调用共通方法解析传入的接口文件</li>
     * <li>2.遍历解析后的数据，逐条插入验证（暂定）</li>
     * <li>3.验证中债需要验证的模块，通过则赋有效状态；不通过，则显示待处理状态</li>
     * <li>4.验证通过后，判断是否新增数据
     * <ul>
     * <li>4.1 若为新增数据，则直接insert</li>
     * <li>4.2 若非新增数据，则需读取是否覆盖开关</li>
     * <li>4.2.1 若开关打开，只更新数据配置项中的字段 4.2.2</li>
     * </ul>
     * </li>
     * <li>若开关关闭，且数据库中为接口维护，则全量更新；若数据库中为手动维护，则不更新</li>此条规则去掉
     * </ul>
     *
     * @param in 输入流
     * @param interType 来源系统
     * @return string 处理成功失败
     * @param tmpPath 临时存放路径
     * @param suffix 文件后缀名
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public List<Map<String, String>> getEquityInfoByINterf1(InputStream in, String interType, String tmpPath,
                                                            String suffix, ChbFileReadMonitorInfo monitorInfoDto){
        // 如果接口尚未处理完成，则不处理
        if (equityInfo001Mapper.selectTmpCount() > 0) {
            return null;
        }

        List<Map<String, String>> equityList = null;
        if ("xml".equals(suffix)) {
            try {
                equityList = XmlParseUtil.parse(in);

                if(StringUtil.isEmptyList(equityList)){
                    return null;
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(),e);
                return null;
            }
        }
        else if ("txt".equals(suffix)) {
            try {
                equityList = TxtParseUtil.parse(in, reportInterfFileEncoding,reportInterfSplitCharacter);

                if(StringUtil.isEmptyList(equityList)){
                    return null;
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(),e);
                return null;
            }
        }

        for (Map<String, String> tmpMap : equityList) {
            if(StringUtil.equals(DictCodeList.境内外_境内.getValue(), tmpMap.get("HOLDER_MARK"))
                    && StringUtil.isNotEmpty(tmpMap.get("HOLDER_NAME"))){
                tmpMap.put("HOLDER_NAME", tmpMap.get("HOLDER_NAME").replace(StringUtil.SPACE, StringUtil.EMPTY)) ;
            }
        }

        //-----add by lingxq on 2019-03-21 for 三要素合并升级------>>>>>
        EquityInfoDistinctService equityInfoDistinctService =   SpringContextUtil.getBean(EquityInfoDistinctService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate=new Date();
        Long bt=bdate.getTime();
        logger.info("身份信息三要素合并开始:"+sdf.format(bdate));
        // 过滤不合理数据,使其进入待维护状态
        List<Map<String, String>> temList=equityInfoDistinctService.rejectUnqualifiedData(equityList);

        List<String> keys = equityInfoDistinctService.getKeys(equityList);
        logger.info("checkSendedData开始");
        // 和已报送数据即反馈状态为成功的数据比较合并
        // 1.有和反馈状态五要素相同且识别标识不同则进check表并直接设置为已合并状态
        // 2.已报送数据合并数据若未进check表则进check表
        // 3.keys中去掉已合并的数据
        equityInfoDistinctService.checkSendedData(equityList,keys,"01");
        logger.info("checkSendedData结束");
        logger.info("checkAllData开始");
        // 查询所有未报送即反馈状态不等于成功的且三要素等于传入的key数据
        // 1.和所有未报送的数据进行判断。不重复的数据放到equityList中。重复的数据放到mergerKeys中
        // 2.对check表全删全插操作。更新check表中本次合并的数据。
        List<String> mergerKeys=equityInfoDistinctService.checkAllData(equityList,keys,"01");
        //add zhangjinyang 2020-12-21
        keys.clear();
        //add zhangjinyang 2020-12-21
        logger.info("checkAllData结束");
        logger.info("merger开始");
        // 识别标识自动合并
        // 根据自动合并规则合并识别标识并且将合并数据添加到equityList中
        equityInfoDistinctService.merger(equityList,"map");
        logger.info("merger结束");
        logger.info("deleteEquitInfoAndSub开始");
        Date bdate1=new Date();
        Long bt1=bdate1.getTime();
        // 删除识别标识合并
        // 从信息表和登记表中 删除checkAllData中五要素重复的数据(未报送的数据：非已报送or手工处理)
        equityInfoDistinctService.deleteEquitInfoAndSub(mergerKeys);
        //add zhangjinyang 2020-12-21
        if(mergerKeys!=null&&mergerKeys.size()>0){
            mergerKeys.clear();
        }
        //add zhangjinyang 2020-12-21
        Date bdate2=new Date();
        Long bt2=bdate2.getTime();
        Long time2=(bt2-bt1)/1000;
        logger.info("deleteEquitInfoAndSub结束"+"耗时"+time2+"秒");
        Date edate=new Date();
        Long et=edate.getTime();
        Long time=(et-bt)/1000;
        logger.info("身份信息三要素合并结束:"+sdf.format(edate)+"耗时"+time+"秒");
        //-----add by lingxq on 2019-03-21 for 三要素合并升级------<<<<<
        equityList.addAll(temList);
        return equityList;
    }


    /**
     * 身份信息方法拆分2、数据初始处理部分
     * @param in
     * @param interType
     * @param tmpPath
     * @param suffix
     * @param monitorInfoDto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public Map<String,Object> getEquityInfoByINterf2(List<Map<String, String>> equityList,ChbFileReadMonitorInfo monitorInfoDto
            ,String interType,String tmpPath) throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        // 解析成功或失败的标识
        //查询全部的已报送数据
//      final Map<String, EquityInfoRegist001Dto> mapRegist = new HashMap<String,EquityInfoRegist001Dto>();
        List<EquityInfoRegist001Dto> registList=new ArrayList<EquityInfoRegist001Dto>();
        template.select("com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper.queryBatchForEquityInfoConStrast",
                new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        registList.add((EquityInfoRegist001Dto) resultContext.getResultObject());
                    }
                });
//      List<EquityInfoRegist001Dto> registList =equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(null);  // 此处的xml要增加if条件判断
        Map<String, List<EquityInfoRegist001Dto>> mapRegist = registList.stream()
                .collect(
                        JoyinCollectors.groupingBy(
                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE
                                        + GlobalConstant.VERTICAL_LINE + x.getIndentifyMark()));
        //add zhangjinyang 2020-12-21
        registList.clear();
        //add zhangjinyang 2020-12-21
        //将registlist改成map结构key=REGIST_BANK_CODE||INDENTIFY_MARK
        //查询出所有的已处理过的生僻字 map
        Map<String, Object> paramap=new HashMap<String, Object>();
        paramap.put("statusList", Arrays.asList("E"));
        List<TransformRule001Dto> allList=transformRule001Mapper.selectAllData(paramap);
        Map<String,TransformRule001Dto> spzMap=allList.stream().collect(
                Collectors.toMap(x ->x.getTransType()+x.getOrgField(),part->part,(k1,k2)->k1));
        List<ChbTransformRuleObjDto> allRuleObjList=chbTransformRuleObjDtoMapper.selectAllDataByTableId(CHB_EQU_HOLD);
        Map<String,List<ChbTransformRuleObjDto>> objFiledsMap=allRuleObjList.stream().collect(
                JoyinCollectors.groupingBy(
                        x -> x.getInfoProperty() ));
        boolean success = true;
        if (CollectionUtils.isNotEmpty(equityList)) {
            monitorInfoDto.setFileDataCount(BigDecimal.valueOf(equityList.size()));

            List<EquityInfo001Dto> listU = new CopyOnWriteArrayList<EquityInfo001Dto>(); // 登记表数据
            // 维护新增list
            List<EquityInfo001Dto> listN = new CopyOnWriteArrayList<EquityInfo001Dto>();

            if (equityList.size() <= 1000) {
                try {
                    success = excuteEquityInfoByStepNew(equityList, interType, listU, listN,mapRegist,spzMap,objFiledsMap);
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    logger.error(e.getMessage(),e);
                    throw new ExecutorException(e);
                }finally {
                }
            }
            else {
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
                    int max = (int)Math.ceil((double)size / 1000);
                    for (int i = 0; i < max; i++ ) {
                        start = end;
                        end = Math.min(size, 1000 * (i + 1));
                        List<Map<String, String>> subList = equityList.subList(start, end);
                        tasks.add(new Callable<Boolean>() {

                            @Override
                            public Boolean call() {
                                try {
                                    return excuteEquityInfoByStepNew(subList, interType, listU, listN,mapRegist,spzMap,objFiledsMap);//修改excuteEquityInfoByStepNew方法中的
                                } catch (ParserConfigurationException | SAXException | IOException e) {
                                    logger.info(e.getMessage(),e);
                                    return false;
                                }
                            }
                        });
                    }
                }

                // 为线程池添加任务
                List<Future<Boolean>> taskResult = null;
                try {
                    taskResult = service.invokeAll(tasks);
                } catch (InterruptedException e1) {
                    logger.error(e1.getMessage(),e1);
                    Thread.currentThread().interrupt();
                    throw new ExecutorException(e1);
                }
                for (Future<Boolean> res : taskResult) {
                    try {
                        success = success && res.get();
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(),e);
                        Thread.currentThread().interrupt();
                        throw new ExecutorException(e);
                    }
                }
                // 任务结束，停止线程池的所有线程
                service.shutdown();
                mapRegist.clear();
            }
            if (listN != null && listN.size() > 0) {
                if(listN.size()<1000){
                    this.insertBtachInterfEquityInfoByPart(listN);
                }else{
                    ImpTable table = new ImpTable("CHB_EQU_HOLD");
                    table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
                    // 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
                    for (ImpColumn column : table.getColumns()) {
                        if (StringUtil.equals(column.getName(), "REGIST_BANK_CODE")) {
                            column.setValueField("bankCode");
                        }
                    }
                    batchInsertService.exeBatchldr(tmpPath, listN, table);
                }
            }

            if (listU != null && listU.size() > 0) {
                if(listU.size()<1000){
                    this.insertBatchEquityRegistByPart(listU);
                }else{
                    ImpTable subTable = new ImpTable(CHB_EQU_SUB);
                    subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
                    subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
                    // 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
                    for (ImpColumn column : subTable.getColumns()) {
                        if (StringUtil.equals(column.getName(), "REGIST_BANK_CODE")) {
                            column.setValueField("bankCode");
                        }
                    }

                    batchInsertService.exeBatchldr(tmpPath, listU, subTable);
                }
            }
        }

        resultMap.put("success", success);

        return resultMap;
    }


    /**
     * 从接口中获取权益人信息（数据开关关闭不覆盖）
     * <ul>
     * <li>1.调用共通方法解析传入的接口文件</li>
     * <li>2.遍历解析后的数据，逐条插入验证（暂定）</li>
     * <li>3.验证中债需要验证的模块，通过则赋有效状态；不通过，则显示待处理状态</li>
     * <li>4.验证通过后，判断是否新增数据
     * <ul>
     * <li>4.1 若为新增数据，则直接insert</li>
     * <li>4.2 若非新增数据，则需读取是否覆盖开关</li>
     * <li>4.2.1 若开关打开，只更新数据配置项中的字段 4.2.2</li>
     * </ul>
     * </li>
     * <li>若开关关闭，且数据库中为接口维护，则全量更新；若数据库中为手动维护，则不更新</li>此条规则去掉
     * </ul>
     *
     * @param in 输入流
     * @param interType 来源系统
     * @return string 处理成功失败
     * @param tmpPath 临时存放路径
     * @param suffix 文件后缀名
     */
    public String getEquityInfoByInterf(InputStream in, String interType, String tmpPath,
                                        String suffix, ChbFileReadMonitorInfo monitorInfoDto) {
        //方法1
        List<Map<String, String>> equityList = getEquityInfoByINterf1(in, interType, tmpPath, suffix, monitorInfoDto);

        boolean success = false;
        boolean needSqldr = false;
        ImpTable table = null;
        ImpTable subTable = null;

        logger.info("根据识别标识去重前:"+equityList.size());
        equityList = equityList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> m.get("INDENTIFY_MARK")))), ArrayList::new));
        logger.info("根据识别标识去重后:"+equityList.size());

        try{
            logger.info("身份信息数据处理开始");
            Map<String,Object> resultmap = getEquityInfoByINterf2(equityList, monitorInfoDto, interType, tmpPath);
            logger.info("身份信息数据处理结束");
            success = (boolean) resultmap.get("success");
        }catch (Exception e) {
            logger.error("身份信息合并处理失败:"+e.getMessage(),e);
            return "fail";
        }
        // 数据解析完成，并导入数据库成功后，将ftp文件修改名称
        if (success) {
            return "success";
        }
        //add by liubin start 当无投资者身份信息供转换时更新监控表异常描述
        monitorInfoDto.setFileHandleResultMsg(this.getMessage("REPORT_FILE_MONITORINFO_NODATA"));
        //add by liubin end
        return "fail";
    }

    public String imptDB(List<String> content,ImpTable table, String tmpPath) {
        // 系统是否使用的db2数据库
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();
        boolean success=true;
        if (isDB2DataBase) {
            // db2数据库处理
            if (CollectionUtils.isNotEmpty(content)) {
//                success = success && ImportGenerator.importToDB(table, tmpPath,
//                    table.getName() + FileNameUtil.createFileName("del"), content);
                try {
                    success = success && ImportGenerator.importToDBByPart(table, tmpPath,
                            content, reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount, "del", reportSqlldrDatabaseName);
                } catch (IOException | InterruptedException e) {
                    logger.error(e.getMessage(),e);
                    Thread.currentThread().interrupt();
                    return "fail";
                }
            }
        }
        else {
            // oracle原逻辑
            if (CollectionUtils.isNotEmpty(content)) {
//                success = success && ImportGenerator.importToDB(table, tmpPath,
//                    table.getName() + FileNameUtil.createFileName("txt"), content);
                try {
                    success = success && ImportGenerator.importToDBByPart(table, tmpPath, content,
                            reportInterfOneTimeSqlLdrDataCount, reportInterfOneTimeSqlLdrCommitCount,
                            "txt", reportSqlldrDatabaseName);
                } catch (IOException | InterruptedException e) {
                    logger.error(e.getMessage(),e);
                    Thread.currentThread().interrupt();
                    return "fail";
                }
            }

        }

        if ( success==true ) {
            return "success";
        } else {
            return "fail";
        }
    }


    /**
     * 删除导入使用的临时文件
     *
     * @see
     * @since 1.0
     */
    public void deleteImportTmpData() {
        equityInfo001Mapper.deleteTmpTable();
    }

//    public boolean excuteEquityInfoByStep(List<Map<String, String>> equityList, String interType,
//                                          String tmpPath, ImpTable table, ImpTable subTable)
//        throws ParserConfigurationException, SAXException, IOException {
//
//        Map<String, Integer> mapPrioty = new HashMap<String, Integer>();
//        // 从配置文件中读取需要覆盖的配置项 01覆盖02不覆盖
//        String[] result = this.getProPrity(interType, mapPrioty);
//        // 新增数据list
//        List<EquityInfo001Dto> listNA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listNE = new ArrayList<EquityInfo001Dto>();
//        // 待处理接口维护数据
//        List<EquityInfo001Dto> listAA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listAE = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listOA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listOE = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listU = new ArrayList<EquityInfo001Dto>(); // 登记表数据
//
////        List<EquityInfoRegist001Dto> registList = equityInfoRegist001Mapper.queryBatchForEquityInfoRegist(
////            equityList);
//        logger.info("EquityInfo001Service queryBatchForEquityInfoRegistByPart处理开始 " + DateUtil.getCurrentDateTime());
//        List<EquityInfoRegist001Dto> registList = this.queryBatchForEquityInfoRegistByPart(equityList);
//        logger.info("EquityInfo001Service queryBatchForEquityInfoRegistByPart处理结束 " + DateUtil.getCurrentDateTime());
//
//        // 系统是否使用的db2数据库
//        boolean isDB2DataBase = CommonUtil.isDB2DataBase();
//        //add by liubin start
//        StringBuffer messageInfo = new StringBuffer();
//        //add by liubin end
//        if (equityList != null && equityList.size() > 0) {
//            logger.info("EquityInfo001Service 首次check处理开始 " + DateUtil.getCurrentDateTime());
//            for (Map<String, String> map : equityList) {
//                EquityInfo001Dto tmp = new EquityInfo001Dto();
//                Map<String, String> map1 = new HashMap<String, String>();
//                EquityInfoMaintenanceService.maptoDto(map, tmp);
//
//                // 转化为key+空格+value格式
//                equityInfoValid.checkInterfEquitySingle(tmp, registList, map1, true,messageInfo);
//                map.put("TYPE", tmp.getType());
//                map.put("ORG_INDENTIFY_MARK",
//                    CommonUtil.getNotNullString(map.get("ORG_INDENTIFY_MARK")));
//            }
//            logger.info("EquityInfo001Service 首次check处理结束 " + DateUtil.getCurrentDateTime());
//            // 将数据插入临时表
//            logger.info("EquityInfo001Service 数据插入临时表处理开始 " + DateUtil.getCurrentDateTime());
////            equityInfo001Mapper.insertIntoImportTable(equityList);
//            this.insertIntoImportTableByPart(equityList);
//            logger.info("EquityInfo001Service 数据插入临时表处理结束 " + DateUtil.getCurrentDateTime());
//            // 批量查询出需要被check的数据
//            logger.info("EquityInfo001Service 批量查询出需要被check的数据处理开始 " + DateUtil.getCurrentDateTime());
//            List<EquityInfo001Dto> equityInfo001List = equityInfo001Mapper.selectEquityInfoForImpCheck();
//            logger.info("EquityInfo001Service 批量查询出需要被check的数据处理结束 " + DateUtil.getCurrentDateTime());
//            Map<String, List<EquityInfo001Dto>> equityInfo001Map = equityInfo001List.stream().collect(
//                JoyinCollectors.groupingBy(
//                    x -> x.getIndentifyMark() + GlobalConstant.UNDER_LINE
//                         + CommonUtil.getNotNullString(
//                             x.getOrgIndentifyMark())
//                         + GlobalConstant.UNDER_LINE + x.getBankCode()));
//            // 当前批次报送时点集合
//            Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
//            List<Date> dateList = null;
//
//            logger.info("EquityInfo001Service 数据检查、整理处理开始 " + DateUtil.getCurrentDateTime());
//            for (Map<String, String> map : equityList) {
//                EquityInfo001Dto equityInfo001Dto = new EquityInfo001Dto();
//                Map<String, String> map1 = new HashMap<String, String>();
//                EquityInfoMaintenanceService.maptoDto(map, equityInfo001Dto);
//                // 转化为key+空格+value格式
//                equityInfoValid.checkInterfEquitySingle(equityInfo001Dto, registList, map1, true,messageInfo);
//                // 若上游接入的数据，与上次报送的数据，未发生改变，则不需要进行入库操作
//                if ("01".equals(equityInfo001Dto.getType()) && "01".equals(map1.get("flag"))) {
//                    continue;
//                }
//                String type = equityInfo001Dto.getType();
//                if (!DictCodeListExt2.报送数据状态_新建.getValue().equals(type)) {
//                    type = DictCodeListExt2.报送数据状态_修改.getValue();
//                }
//                equityInfo001Dto.setSubmitStatus(type);
//                // equityInfo001Dto = EquityInfoCheckUtil.getChbEndDate(listTime, type,
//                // equityInfo001Dto);
//                // 报送时点通用方法
//                dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者身份信息登记.getValue(),
//                    type, dateMap, DateUtil.now());
//                if (CollectionUtils.isNotEmpty(dateList)) {
//                    equityInfo001Dto.setChbSubmitDeadline(dateList.get(0));
//                    equityInfo001Dto.setSubmitDeadline(dateList.get(1));
//                }
//
//                equityInfo001Dto.setDataSource(interType);
//                List<EquityInfo001Dto> equityInfoCheckList = equityInfo001Map.get(map.get(
//                    "INDENTIFY_MARK") + GlobalConstant.UNDER_LINE + CommonUtil.getNotNullString(
//                        map.get("ORG_INDENTIFY_MARK")) + GlobalConstant.UNDER_LINE + map.get(
//                            "REGIST_BANK_CODE"));
//                EquityInfo001Dto equityInfoCheck = null;
//                if (null != equityInfoCheckList && !equityInfoCheckList.isEmpty()) {
//                    equityInfoCheck = equityInfoCheckList.get(0);
//                }
//                if (!("01".equals(equityInfo001Dto.getType()) && "01".equals(map1.get("flag")))) {
//                    if (equityInfoCheck == null && "A".equals(equityInfo001Dto.getStatus())) {
//                        listNA.add(equityInfo001Dto);
//                    }
//                    else if (equityInfoCheck == null && "E".equals(equityInfo001Dto.getStatus())) {
//                        if (tmpPath == null || isDB2DataBase) {
//                            equityInfo001Dto.setSumitSeq(
//                            		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                        }
//                        listNE.add(equityInfo001Dto);
//                    }
//                    else if ("A".equals(equityInfoCheck.getStatus())
//                             && "A".equals(equityInfo001Dto.getStatus())) {
//                        this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                            result, listAA, interType, mapPrioty);
//                    }
//                    else if ("A".equals(equityInfoCheck.getStatus())
//                             && "E".equals(equityInfo001Dto.getStatus())) {
//                        this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                            result, listAE, interType, mapPrioty);
//                    }
//                    else if ("A".equals(equityInfo001Dto.getStatus())) {
//                        this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                            result, listOA, interType, mapPrioty);
//                    }
//                    else if ("E".equals(equityInfo001Dto.getStatus())) {
//                        this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                            result, listOE, interType, mapPrioty);
//                    }
//                }
//                if (("01".equals(equityInfo001Dto.getType()) && !"01".equals(map1.get("flag")))
//                    || "03".equals(equityInfo001Dto.getType())) {
//                    if ("E".equals(equityInfo001Dto.getStatus())) {
//                        if (equityInfoCheck == null) {
//                            if (tmpPath == null || isDB2DataBase) {
//                                equityInfo001Dto.setSumitSeq(
//                                		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                            }
//                            listU.add(equityInfo001Dto);
//                        }
//                        else if ("01".equals(equityInfoCheck.getDataSource())
//                                 || "02".equals(equityInfoCheck.getDataSource())) {
//                            EquityInfo001Dto equityInfo = new EquityInfo001Dto();
//                            EquityInfoMaintenanceService.maptoSetDto(map, equityInfo, result);
//                            equityInfo.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                            equityInfo.setType(equityInfo001Dto.getType());
//                            equityInfo.setOrgCode(equityInfo001Dto.getOrgCode());
//                            equityInfo.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
//                            equityInfo.setChbSubmitDeadline(
//                                equityInfo001Dto.getChbSubmitDeadline());
//                            if (tmpPath == null || isDB2DataBase) {
//                                equityInfo.setSumitSeq(
//                                		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                            }
//                            listU.add(equityInfo);
//                        }
//                        else {
//                            if (tmpPath == null || isDB2DataBase) {
//                                equityInfo001Dto.setSumitSeq(
//                                		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                            }
//                            listU.add(equityInfo001Dto);
//                        }
//                    }
//                }
//                else if ("02".equals(equityInfo001Dto.getType())
//                         && "E".equals(equityInfo001Dto.getStatus())) {
//                    EquityInfo001Dto equityInfo001Dto1 = EquityInfoMaintenanceService.mapToSetProDto(
//                        map, interType, mapPrioty, result, equityInfoCheck,
//                        equityInfo001Dto.getType());
//                    equityInfo001Dto1.setBankCode(equityInfo001Dto.getBankCode());
//                    equityInfo001Dto1.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
//                    equityInfo001Dto1.setOrgIndentifyMark(equityInfo001Dto.getOrgIndentifyMark());
//                    equityInfo001Dto1.setType(equityInfo001Dto.getType());
//                    equityInfo001Dto1.setOrgCode(equityInfo001Dto.getOrgCode());
//                    equityInfo001Dto1.setDeptCode(equityInfo001Dto.getDeptCode());
//                    equityInfo001Dto1.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                    if (tmpPath == null || isDB2DataBase) {
//                        equityInfo001Dto.setSumitSeq(
//                        		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                    }
//                    equityInfo001Dto1.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
//                    equityInfo001Dto1.setChbSubmitDeadline(
//                        equityInfo001Dto.getChbSubmitDeadline());
//                    listU.add(equityInfo001Dto1);
//                }
//                else if ("04".equals(equityInfo001Dto.getType())
//                         && "E".equals(equityInfo001Dto.getStatus())) {
//                    EquityInfo001Dto equityInfo001Dto1 = EquityInfoMaintenanceService.mapToSetProDto(
//                        map, interType, mapPrioty, result, equityInfoCheck,
//                        equityInfo001Dto.getType());
//                    equityInfo001Dto1.setBankCode(equityInfo001Dto.getBankCode());
//                    equityInfo001Dto1.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
//                    equityInfo001Dto1.setType(equityInfo001Dto.getType());
//                    equityInfo001Dto1.setDeptCode(equityInfo001Dto.getDeptCode());
//                    equityInfo001Dto1.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                    if (tmpPath == null || isDB2DataBase) {
//                        equityInfo001Dto.setSumitSeq(
//                        		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                    }
//                    equityInfo001Dto1.setOrgCode(equityInfo001Dto.getOrgCode());
//                    equityInfo001Dto1.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
//                    equityInfo001Dto1.setChbSubmitDeadline(
//                        equityInfo001Dto.getChbSubmitDeadline());
//                    listU.add(equityInfo001Dto1);
//                }
//            }
//            logger.info("EquityInfo001Service 数据检查、整理处理结束 " + DateUtil.getCurrentDateTime());
//            // 插入的权益人信息为空时,先插入登记维护表，再插入信息登记表
//            if (CollectionUtils.isNotEmpty(listNA)) {
//                logger.info("EquityInfo001Service listNA 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
//                if (tmpPath == null) {
//                    // 插入维护信息表
//                	//修改为分批插入
//                    //equityInfo001Mapper.insertBtachInterfEquityInfo(listNA);
//                	this.insertBtachInterfEquityInfoByPart(listNA);
//                }
//                else {
//                    createDataFile(tmpPath + "/CHB_EQU_HOLD", listNA, table.getColumns());
//                }
//                logger.info("EquityInfo001Service listNA 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());
//            }
//            // 插入的权益人信息为空时,先插入登记维护表，再插入信息登记表
//            if (CollectionUtils.isNotEmpty(listNE)) {
//                logger.info("EquityInfo001Service listNE 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
//                if (tmpPath == null) {
//                    // 插入维护信息表
//                	//修改为分批插入
//                    //equityInfo001Mapper.insertBtachInterfEquityInfo(listNA);
//                	this.insertBtachInterfEquityInfoByPart(listNE);
//                }
//                else {
//                    createDataFile(tmpPath + "/CHB_EQU_HOLD", listNE, table.getColumns());
//                }
//                logger.info("EquityInfo001Service listNA 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());
//            }
//            // 插入信息前为待处理时，先跟新维护表，在插入更新表
//            if (CollectionUtils.isNotEmpty(listAA)) {
//                logger.info("EquityInfo001Service listAA 入库处理开始 " + DateUtil.getCurrentDateTime());
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listAA);
//            	this.updateBtachEquityInfoByPart(listAA,isDB2DataBase);
//            	logger.info("EquityInfo001Service listAA 入库处理结束 " + DateUtil.getCurrentDateTime());
//            }
//            if (CollectionUtils.isNotEmpty(listAE)) {
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listAE);
//                logger.info("EquityInfo001Service listAE updateBtachEquityInfoByPart处理开始 " + DateUtil.getCurrentDateTime());
//            	this.updateBtachEquityInfoByPart(listAE, isDB2DataBase);
//            	logger.info("EquityInfo001Service listAE updateBtachEquityInfoByPart处理结束 " + DateUtil.getCurrentDateTime());
//                // 删除登记表中未报送数据(若数据由有效转为待处理后，又插入一条有效数据)
//                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listAE);
//            	logger.info("EquityInfo001Service listAE deleteBatchEquityRegist处理开始 " + DateUtil.getCurrentDateTime());
//            	this.deleteBatchEquityRegist(listAE);
//                logger.info("EquityInfo001Service listAE deleteBatchEquityRegist处理结束 " + DateUtil.getCurrentDateTime());
//            }
//            // 当插入信息为有效或者删除是，先将信息迁移至履历表，再更新信息维护表，之后先统一删除登记表中报送中的数据，插入登记表
//            if (CollectionUtils.isNotEmpty(listOA)) {
//                // 首先将权益人信息表插入履历表中
//                //equityInfo001Mapper.insertEquityHis(listOA);
//                logger.info("EquityInfo001Service listOA insertEquityHis处理开始 " + DateUtil.getCurrentDateTime());
//            	this.insertEquityHis(listOA);
//                logger.info("EquityInfo001Service listOA insertEquityHis处理结束 " + DateUtil.getCurrentDateTime());
//
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listOA);
//                logger.info("EquityInfo001Service listOA updateBtachEquityInfoByPart处理开始 " + DateUtil.getCurrentDateTime());
//            	this.updateBtachEquityInfoByPart(listOA, isDB2DataBase);
//                logger.info("EquityInfo001Service listOA updateBtachEquityInfoByPart处理结束 " + DateUtil.getCurrentDateTime());
//
//                // 删除登记表中未报送数据
//                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listOA);
//                logger.info("EquityInfo001Service listOA deleteBatchEquityRegist处理开始 " + DateUtil.getCurrentDateTime());
//            	this.deleteBatchEquityRegist(listOA);
//                logger.info("EquityInfo001Service listOA deleteBatchEquityRegist处理结束 " + DateUtil.getCurrentDateTime());
//            }
//            if (CollectionUtils.isNotEmpty(listOE)) {
//                // 首先将权益人信息表插入履历表中
//               // equityInfo001Mapper.insertEquityHis(listOE);
//                logger.info("EquityInfo001Service listOE insertEquityHis处理开始 " + DateUtil.getCurrentDateTime());
//            	this.insertEquityHis(listOE);
//                logger.info("EquityInfo001Service listOE insertEquityHis处理结束 " + DateUtil.getCurrentDateTime());
//
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listOE);
//                logger.info("EquityInfo001Service listOE updateBtachEquityInfoByPart处理开始 " + DateUtil.getCurrentDateTime());
//            	this.updateBtachEquityInfoByPart(listOE,isDB2DataBase);
//                logger.info("EquityInfo001Service listOE updateBtachEquityInfoByPart处理结束 " + DateUtil.getCurrentDateTime());
//
//                // 删除登记表中未报送数据
//                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listOE);
//                logger.info("EquityInfo001Service listOE deleteBatchEquityRegist处理开始 " + DateUtil.getCurrentDateTime());
//                this.deleteBatchEquityRegist(listOE);
//                logger.info("EquityInfo001Service listOE deleteBatchEquityRegist处理结束 " + DateUtil.getCurrentDateTime());
//            }
//            if (CollectionUtils.isNotEmpty(listU)) {
//                logger.info("EquityInfo001Service listU 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
//                if (tmpPath == null) {
//                    // 插入登记表
//                    this.insertBatchEquityRegistByPart(listU);
//                }
//                else {
////                    createDataFile(tmpPath + "/CHB_EQU_SUB", listNE, subTable.getColumns());
//                    createDataFile(tmpPath + "/CHB_EQU_SUB", listU, subTable.getColumns());
//                }
//                logger.info("EquityInfo001Service listU 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());
//            }
//            // 数据解析完成，并导入数据库成功后，将ftp文件修改名称
////            return (CollectionUtils.isNotEmpty(listNA) || CollectionUtils.isNotEmpty(listNE)
////                    || CollectionUtils.isNotEmpty(listAA) || CollectionUtils.isNotEmpty(listAE)
////                    || CollectionUtils.isNotEmpty(listOA) || CollectionUtils.isNotEmpty(listOE)
////                    || CollectionUtils.isNotEmpty(listU));
//            return true;
//        }
//        return false;
//    }

    /**
     * 创建数据文件
     *
     * @param path 文件路径
     * @param equityList 对象列表
     * @param columns 数据库表字段定义
     */
    private void createDataFile(String path, List<EquityInfo001Dto> equityList,
                                List<ImpColumn> columns) {
        // 生成txt文件
        List<String> lines = new ArrayList<>(equityList.size());

        // 系统是否使用的db2数据库
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();

        for (EquityInfo001Dto dataDto : equityList) {
            if (path.contains("CHB_EQU_SUB") && isDB2DataBase) {
                dataDto.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
            }

            lines.add(ImportGenerator.buildTxt(dataDto, columns));
        }

//        equityList.forEach(item -> lines.add(ImportGenerator.buildTxt(item, columns)));
        try {
            FileUtils.writeLines(new File(path + "_" + UUID.randomUUID().toString() + ".tmp"),
                    "UTF-8", lines, true);
        }
        catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
     *
     * @param fileName 导入的文件名名
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList) {
        AjaxResult webResult = new AjaxResult();
        if (!StringUtil.isEmptyList(dataList)) {
            try {
                if (CollectionUtils.isNotEmpty(dataList)) {
                    List<EquityInfo001Dto> entities = converToBean(dataList,null);
//                    equityInfoDistinctService.checkSendedData(equityList);
//                    equityInfoDistinctService.checkAllData(equityList);
//                    List<EquityInfo001Dto> data = this.selectEquityByKey(entities);
                    List<EquityInfo001Dto> data = new ArrayList<EquityInfo001Dto>();
                    try {
                        data = this.selectEquityByKey(entities);
                    }
                    catch (Exception e) {
//                        System.out.println("selectEquityByKey出异常：" + e.getMessage());
                        logger.error("selectEquityByKey出异常：{}",e.getMessage());
                    }

                    if (data != null && data.size() > 0) {
                        Map<String, List<EquityInfo001Dto>> equityInfo001Map = entities.stream().collect(
                                JoyinCollectors.groupingBy(
                                        x -> x.getIndentifyMark() + GlobalConstant.UNDER_LINE
                                                + x.getBankCode()));
                        // Collections.sort(data, Comparator.comparing(EquityInfo001Dto::getExcelNum));
                        // 将序号放入list中
                        List<String> excelNumList = new ArrayList<String>();
                        List<Integer> excelNumIntList = new ArrayList<Integer>();
                        for (EquityInfo001Dto equityInfo001Dto : data) {
                            List<EquityInfo001Dto> listTemp = equityInfo001Map.get(
                                    equityInfo001Dto.getIndentifyMark() + GlobalConstant.UNDER_LINE
                                            + equityInfo001Dto.getBankCode());
//                            excelNumList.add(listTemp.get(0).getExcelNum());
                            excelNumIntList.add(Integer.valueOf(listTemp.get(0).getExcelNum()));
                        }
                        // 将list进行排序，并转化为string
//                        Collections.sort(excelNumList);
                        Collections.sort(excelNumIntList);
                        for (Integer rowNo : excelNumIntList) {
                            excelNumList.add(String.valueOf(rowNo));
                        }
                        // String messages = StringUtil.joinString(excelNumList, GlobalConstant.COMMA);
                        webResult.setMessage(
                                this.getMessage("CHB_ISCOVER_EQU",
                                        StringUtil.joinString(excelNumList, GlobalConstant.COMMA)),
                                MessageType.确认消息);
                    }
                    else {
                        // 防止在进行导入前check时进行了两次的弹框
                        webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                    }
                }
                else{
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
     * 分配查询处理
     *
     * @param dataList 传入条件
     * @return 返回查询结果
     * @see
     * @since 1.0
     */
    private List<EquityInfo001Dto> selectEquityByKey(List<EquityInfo001Dto> dataList) {

        int maxDataCountOneTime = 1000;
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            return equityInfo001Mapper.queryEquityInfoList(dataList);
        }
        // 导入大于1000时，返回值
        List<EquityInfo001Dto> equtiyList = new ArrayList<EquityInfo001Dto>();

        List<EquityInfo001Dto> newDataList = new ArrayList<EquityInfo001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        List<EquityInfo001Dto> listTemp = null;
        for (int i = 0; i < part; i++ ) {
            listTemp = equityInfo001Mapper.queryEquityInfoList(
                    newDataList.subList(0, maxDataCountOneTime));
            // 不为空，放入返回list中
            if (CollectionUtils.isNotEmpty(listTemp)) {
                equtiyList.addAll(listTemp);
            }
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            listTemp = equityInfo001Mapper.queryEquityInfoList(newDataList);
            // 不为空，放入返回list中
            if (CollectionUtils.isNotEmpty(listTemp)) {
                equtiyList.addAll(listTemp);
            }
        }
        return equtiyList;
    }

    /**
     * 代销数据导入功能
     *
     * @return 检验list
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ExcelValidDto> importChbData(List<List<String>> dataList,
                                             List<List<String>> tempList,List<List<String>> dataTypeList,String bankCode,String deptCode,String orgCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            String templateFlag = SvrDataUtil.getSysParam("SVR_EQU_INFO_TEMPLATE_FLAG");
            List<String> headList = new ArrayList<String>();
            headList.add("登记银行代码");
            headList.add("该投资者是否属于本行");
            headList.add("投资者所属银行名称");
            headList.add("投资者所属银行代码");
            headList.add("投资者境内外标识");
            headList.add("投资者所属国家或地区");
            headList.add("数据类型");
            headList.add("原识别标识");
            headList.add("识别标识");
            headList.add("投资者类别");
            headList.add("个人证件类别");
            headList.add("机构证件类别");
            headList.add("其他证件名称");
            headList.add("证件号码");
            headList.add("SPV资金托管账户开户行");
            headList.add("其他资金托管账户开户行");
            headList.add("投资者名称");
            headList.add("性别");
            headList.add("风险偏好");
            headList.add("手机号码");
            headList.add("固定电话");
            headList.add("电子邮箱");
            headList.add("备注");
            headList.add("投资者分类");

            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }

            if (null != dataTypeList) {
                //将中债信息模板数据转换成系统模板数据
                toChbDataTypeList(dataTypeList);
            }
            if (null != dataList) {
                //查询出所有的已处理过的生僻字 map
                Map<String, Object> paramap=new HashMap<String, Object>();
                paramap.put("statusList", Arrays.asList("E"));
                List<TransformRule001Dto> allList=transformRule001Mapper.selectAllData(paramap);
                Map<String,TransformRule001Dto> spzMap=allList.stream().collect(
                        Collectors.toMap(x ->x.getTransType()+x.getOrgField(),part->part,(k1,k2)->k1));
                List<ChbTransformRuleObjDto> allRuleObjList=chbTransformRuleObjDtoMapper.selectAllDataByTableId(CHB_EQU_HOLD);
                Map<String,List<ChbTransformRuleObjDto>> objFiledsMap=allRuleObjList.stream().collect(
                        JoyinCollectors.groupingBy(
                                x -> x.getInfoProperty() ));
                //将中债信息模板数据转换成系统模板数据
                toChbList(dataList);
                // 将excel表格中的数据转化为权益人dto
                List<Map<String, String>> listType = new ArrayList<Map<String, String>>();
                List<ExcelValidDto> checkResult = equityInfoValid.checkExcelEquity(dataList,
                        listType,dataTypeList,spzMap,objFiledsMap,orgCode);
                if (checkResult.isEmpty()) {
                    List<EquityInfo001Dto> equityList = converToBeanForChb(dataList,orgCode);
                    EquityInfoDistinctService equityInfoDistinctService =
                            SpringContextUtil.getBean(EquityInfoDistinctService.class);
                    List<String> keys = equityInfoDistinctService.getKeys(equityList);
                    equityInfoDistinctService.checkSendedData(equityList,keys,"01");
                    List<String> mergerKeys=equityInfoDistinctService.checkAllData(equityList,keys,"01");
                    equityInfoDistinctService.merger(equityList,"dto");
                    equityInfoDistinctService.deleteEquitInfoAndSub(mergerKeys);
                    //modify by wangmin 合DB2版本代码，添加数据源 begin 2018-07-23
                    //dealAfterCheck(listType, equityList);
                    dealAfterCheck(listType, equityList,DictCodeListExt2.数据来源_EXCEL导入.getValue(),deptCode,orgCode);
                    //modify by wangmin 合DB2版本代码，添加数据源 end 2018-07-23
                }
                return checkResult;
            }
        }
        return null;
    }
    /**
     * 将数据转换成符合本系统的格式
     *
     * @param dataList 数据
     * @see
     * @since 1.0
     */
    private void toChbList(List<List<String>> dataList) {
        String blankStr = " ";

        List<String> list = null;
        for (int i = 0; i < dataList.size(); i++) {
            if (StringUtil.isEmptyList(list)) {
                list = new ArrayList<String>();
            }
            else {
                list.clear();
            }

            list.add(dataList.get(i).get(0));
            list.add(dataList.get(i).get(8));
            list.add(dataList.get(i).get(7));
            for (int j = 3; j <= 7; j++) {
                list.add(dataList.get(i).get(j - 2));
            }
            for (int j = 9; j < dataList.get(i).size(); j++) {
                list.add(dataList.get(i).get(j));
            }
            list.add(" ");//经办人一栏为空

            // 数据类型
            String dataType = dataList.get(i).get(6);
            if (StringUtil.isNotBlank(dataType) && StringUtil.contains(dataType, blankStr)) {
                dataType = StringUtil.splitToList(blankStr, dataType).get(0);
            }
            // 登记银行代码
            String bankCode = dataList.get(i).get(0);
            // 识别标识
            String indentifyMark = dataList.get(i).get(8);
            // 原识别标识
            String orgIndentifyMark = dataList.get(i).get(7);
            if (StringUtil.equals(DictCodeListExt2.数据类型_中债_识别标识变更.getValue(), dataType)) {
                if (StringUtil.isNotBlank(bankCode) && StringUtil.isNotBlank(indentifyMark)
                        && StringUtil.isNotBlank(orgIndentifyMark)) {
                    this.handleDataItemAdd(bankCode, orgIndentifyMark, list);
                }
            }
            else if (StringUtil.equals(DictCodeListExt2.数据类型_中债_其他信息变更.getValue(), dataType)) {
                if (StringUtil.isNotBlank(bankCode) && StringUtil.isNotBlank(indentifyMark)) {
                    this.handleDataItemAdd(bankCode, indentifyMark, list);
                }
            }

            dataList.get(i).clear();
            dataList.get(i).addAll(list);
        }

    }

    /**
     * 将代销模板中，识别标识变更和其他信息变更的数据，缺失项补齐
     *
     * @param bankCode 登记银行代码
     * @param list 数据
     * @return 处理后数据
     * @see
     * @since 1.0
     */
    private List<String> handleDataItemAdd(String bankCode, String indentifyMark, List<String> list) {
        EquityInfoRegist001Dto regist001Dto = equityInfoRegist001Mapper.queryBatchForEquityInfoSingle(
                bankCode, indentifyMark);

        if (regist001Dto != null) {
            regist001Dto.setType(DictCodeListExt2.数据类型_中债_新增.getValue());
            EquityInfoRegistCommonUtil.formatDto(regist001Dto, null);

            // 该投资者是否属于本行
            list.set(3, regist001Dto.getIsBelongBank());
            // 投资者所属银行名称
            list.set(4, regist001Dto.getTheBankName());
            // 投资者所属银行代码
            list.set(5, regist001Dto.getTheBankCode());
            // 投资者境内外标识
            list.set(6, regist001Dto.getHolderMark());
            // 投资者所属国家或地区
            list.set(7, regist001Dto.getHolderRegion());
            // 投资者类别
            list.set(8, regist001Dto.getHolderType());

            // 个人证件类别
            list.set(9, regist001Dto.getPersonalIdentifyType());
            // 机构证件类别
            list.set(10, regist001Dto.getInstitutionIdentifyType());
            // 其他证件名称
            list.set(11, regist001Dto.getOtherIdentifyName());
            // 证件号码
            list.set(12, regist001Dto.getIdentifyNumber());
            // SPV资金托管账户开户行
            list.set(13, regist001Dto.getSpvCapAccBank());
            // 其他资金托管账户开户行
            list.set(14, regist001Dto.getOtherCapAccBank());
            // 投资者名称
            list.set(15, regist001Dto.getHolderName());
            // 性别
            list.set(16, regist001Dto.getHolderSex());
            // 风险偏好
            if (StringUtil.isBlank(list.get(17))) {
                list.set(17, regist001Dto.getHolderRiskPrefer());
            }
            // 手机号码
            if (StringUtil.isBlank(list.get(18))) {
                list.set(18, regist001Dto.getHolderCellphone());
            }
            // 手机号码
            if (list.size()>=26&&StringUtil.isBlank(list.get(25))) {
                list.set(25, regist001Dto.getHolderClassify());
            }
            // 固定电话
            if (StringUtil.isBlank(list.get(19))) {
                list.set(19, regist001Dto.getHolderTelephone());
            }
            // 电子邮箱
            if (StringUtil.isBlank(list.get(20))) {
                list.set(20, regist001Dto.getHolderEmail());
            }
            // 备注
            list.set(21, regist001Dto.getRemark());
        }

        return list;
    }

    /**
     * 单独取出导入代销模板的数据类型
     *
     * @param dataTypeList 数据
     * @see
     * @since 1.0
     */
    private void toChbDataTypeList(List<List<String>> dataTypeList) {
        List<String> list = null;
        for (int i = 0; i < dataTypeList.size(); i++) {
            if(StringUtil.isEmptyList(list))
                list=new ArrayList<String>();
            else list.clear();
            list.add(dataTypeList.get(i).get(0));
            list.add(dataTypeList.get(i).get(8));
            list.add(dataTypeList.get(i).get(7));
            for (int j = 3; j <= 8; j++) {
                list.add(dataTypeList.get(i).get(j - 2));
            }
            for (int j = 9; j < dataTypeList.get(i).size(); j++) {
                list.add(dataTypeList.get(i).get(j));
            }
            list.add(" ");//经办人一栏为空
            dataTypeList.get(i).clear();
            dataTypeList.get(i).addAll(list);
        }

    }


    /**
     * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
     *
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForChbImportData(List<List<String>> dataList,List<List<String>> tempList) {
        AjaxResult webResult = new AjaxResult();
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<String>();
            headList.add("登记银行代码");
            headList.add("该投资者是否属于本行");
            headList.add("投资者所属银行名称");
            headList.add("投资者所属银行代码");
            headList.add("投资者境内外标识");
            headList.add("投资者所属国家或地区");
            headList.add("数据类型");
            headList.add("原识别标识");
            headList.add("识别标识");
            headList.add("投资者类别");
            headList.add("个人证件类别");
            headList.add("机构证件类别");
            headList.add("其他证件名称");
            headList.add("证件号码");
            headList.add("SPV资金托管账户开户行");
            headList.add("其他资金托管账户开户行");
            headList.add("投资者名称");
            headList.add("性别");
            headList.add("风险偏好");
            headList.add("手机号码");
            headList.add("固定电话");
            headList.add("电子邮箱");
            headList.add("备注");
            headList.add("投资者分类");

            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                webResult.setMessage("导入模板不一致，请重新选择文件！");
                return webResult;
            }

            //将中债的数据格式转换成本系统支持的格式
            toChbList(dataList);
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<EquityInfo001Dto> entities = converToBeanForChb(dataList,null);
                List<EquityInfo001Dto> data = new ArrayList<EquityInfo001Dto>();
                try {
                    data = this.selectEquityByKey(entities);
                }
                catch (Exception e) {
//                    System.out.println("selectEquityByKey出异常：" + e.getMessage());
                    logger.error("selectEquityByKey出异常：{}",e.getMessage());
                }

                if (data != null && data.size() > 0) {
                    Map<String, List<EquityInfo001Dto>> equityInfo001Map = entities.stream().collect(
                            JoyinCollectors.groupingBy(
                                    x -> x.getIndentifyMark() + GlobalConstant.UNDER_LINE
                                            + x.getBankCode()));
                    // Collections.sort(data, Comparator.comparing(EquityInfo001Dto::getExcelNum));
                    // 将序号放入list中
                    List<String> excelNumList = new ArrayList<String>();
                    List<Integer> excelNumIntList = new ArrayList<Integer>();
                    for (EquityInfo001Dto equityInfo001Dto : data) {
                        List<EquityInfo001Dto> listTemp = equityInfo001Map.get(
                                equityInfo001Dto.getIndentifyMark() + GlobalConstant.UNDER_LINE
                                        + equityInfo001Dto.getBankCode());
//                        excelNumList.add(listTemp.get(0).getExcelNum());
                        excelNumIntList.add(Integer.valueOf(listTemp.get(0).getExcelNum()));
                    }
                    // 将list进行排序，并转化为string
//                    Collections.sort(excelNumList);
                    Collections.sort(excelNumIntList);
                    for (Integer rowNo : excelNumIntList) {
                        excelNumList.add(String.valueOf(rowNo));
                    }
                    // String messages = StringUtil.joinString(excelNumList, GlobalConstant.COMMA);
                    webResult.setMessage(
                            this.getMessage("CHB_ISCOVER_EQU",
                                    StringUtil.joinString(excelNumList, GlobalConstant.COMMA)),
                            MessageType.确认消息);
                }
                else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }
            }
        }
        return webResult;
    }

    /**
     * 查询符合条件的中债反馈信息为中债退回的数据
     * @param param
     * @return
     */
    public List<EquityInfo001Dto> selectFeedBackData(Map<String, Object> param){
        return equityInfo001Mapper.selectFeedBackData(param);
    };

    public List<EquityInfo001Dto> selectAllFeedBackData(Map<String, Object> params){
        return equityInfo001Mapper.selectAllFeedBackData(params);
    }
    public int parsToInt(String count,String flag){
        if(StringUtil.isNotEmpty(count)&&Integer.parseInt(count)<=0){
            if("1".equals(flag)){
                return 20;
            }
            else{
                return 500;
            }
        }
        else{
            return Integer.parseInt(count);
        }
    }
//    /**
//     * 分批次执行queryBatchForEquityInfoConStrast
//     *
//     * @param dataList 数据
//     * @param registList 目标数据
//     * @see
//     * @since 1.0
//     */
//    private void queryBatchForEquityInfoConStrastBatch(List<Map<String, String>> dataList ,List<EquityInfoRegist001Dto> registList) {
//        if (StringUtil.isEmptyList(dataList)) {
//            return ;
//        }
//
//        int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount,"2");
//        int dataCount = dataList.size();
//        if (dataCount <= maxDataCountOneTime) {
//        	registList.addAll(equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(dataList));
//            return;
//        }
//
//        List<Map<String, String>> newDataList = new ArrayList<Map<String, String>>();
//        newDataList.addAll(dataList);
//
//        // 分批处理
//        int part = dataCount / maxDataCountOneTime; // 分批数
//        for (int i = 0; i < part; i++ ) {
//        	registList.addAll(equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(newDataList.subList(0, maxDataCountOneTime)));
//            newDataList.subList(0, maxDataCountOneTime).clear();
//        }
//        // 最后剩下的数据
//        if (CollectionUtils.isNotEmpty(newDataList)) {
//        	registList.addAll(equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(newDataList));
//        }
//        dataList =null;
//        newDataList =null;
//    }
    /**
     * 分批次执行selectEquityInfoForCheck
     *
     * @param dataList 数据
     * @param equityInfo001List 目标数据
     * @see
     * @since 1.0
     */
    private void selectEquityInfoForCheckBatch(List<Map<String, String>> dataList ,List<EquityInfo001Dto> equityInfo001List) {
        if (StringUtil.isEmptyList(dataList)) {
            return ;
        }

        int maxDataCountOneTime = this.parsToInt(reportSelectOrDeleteCount,"2");
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
            equityInfo001List.addAll(equityInfo001Mapper.selectEquityInfoForCheck(dataList));
            return;
        }

        List<Map<String, String>> newDataList = new ArrayList<Map<String, String>>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            equityInfo001List.addAll(equityInfo001Mapper.selectEquityInfoForCheck(newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            equityInfo001List.addAll(equityInfo001Mapper.selectEquityInfoForCheck(newDataList));
        }
        dataList =null;
        newDataList =null;
    }
    /**
     * 分批次执行queryBatchForEquityInfoConStrast
     *
     * @param dataList 数据
     * @param registList 目标数据
     * @see
     * @since 1.0
     */
    private void queryBatchForEquityInfoConStrastBatchFromMem(List<Map<String, String>> dataList ,List<EquityInfoRegist001Dto> registList,Map<String, List<EquityInfoRegist001Dto>> mapRegist) {
        if (StringUtil.isEmptyList(dataList)) {
            return ;
        }
        if(mapRegist.isEmpty()){
            return ;
        }
        for ( Map<String,String>map:dataList ) {
            String k1=map.get("REGIST_BANK_CODE");
            if ( k1==null ) k1="";
            String k2 = map.get("INDENTIFY_MARK");
            if ( k2==null ) k2="";
            String key= k1+"||"+k2;  //key=REGIST_BANK_CODE||INDENTIFY_MARK
            if(mapRegist.get(key)!=null){
                registList.add(mapRegist.get(key).get(0));
            }
        }
    }

    public boolean excuteEquityInfoByStepNew(List<Map<String, String>> equityList, String interType,
                                             List<EquityInfo001Dto> listU, List<EquityInfo001Dto> listN,Map<String, List<EquityInfoRegist001Dto>> mapRegist,
                                             Map<String,TransformRule001Dto> spzMap,Map<String,List<ChbTransformRuleObjDto>> objFiledsMap
    )
            throws ParserConfigurationException, SAXException, IOException {

        Map<String, Integer> mapPrioty = new HashMap<String, Integer>();
        // 从配置文件中读取需要覆盖的配置项 01覆盖02不覆盖
        String[] result = this.getProPrity(interType, mapPrioty);
        // 待处理接口维护数据
//        List<EquityInfo001Dto> listAA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listAE = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listOA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listOE = new ArrayList<EquityInfo001Dto>();
        //维护updatelist 维护表中数据状态为待处理
        List<EquityInfo001Dto> listA = new ArrayList<EquityInfo001Dto>();
        //维护updatelist 维护表中数据状态为有效或者删除
        List<EquityInfo001Dto> listO = new ArrayList<EquityInfo001Dto>();
        //只有四期数据变化的情况下，用来更新INFO表的数据结构
        List<EquityInfo001Dto> listUpForInfo = new ArrayList<EquityInfo001Dto>();
        //只有四期数据变化的情况下，用来更新SUB表的数据结构
        List<EquityInfo001Dto> listUpForSub = new ArrayList<EquityInfo001Dto>();

        //修改已报送过的数据对比方式
//        List<EquityInfoRegist001Dto> registList = equityInfoRegist001Mapper.queryBatchForEquityInfoRegist(
//            equityList);
        logger.info("EquityInfo001Service queryBatchForEquityInfoConStrast处理开始 " + DateUtil.getCurrentDateTime());
//        List<EquityInfoRegist001Dto> registList = equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(equityList);
        List<EquityInfoRegist001Dto> registList = new ArrayList<EquityInfoRegist001Dto>();
//        this.queryBatchForEquityInfoConStrastBatch(equityList,registList);
        this.queryBatchForEquityInfoConStrastBatchFromMem(equityList,registList,mapRegist);
        logger.info("EquityInfo001Service queryBatchForEquityInfoConStrast处理结束 " + DateUtil.getCurrentDateTime());
        // 系统是否使用的db2数据库
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();

        if (equityList != null && equityList.size() > 0) {
//            for (Map<String, String> map : equityList) {
//                EquityInfo001Dto tmp = new EquityInfo001Dto();
//                Map<String, String> map1 = new HashMap<String, String>();
//                EquityInfoMaintenanceService.maptoDto(map, tmp);
//                // 转化为key+空格+value格式
//                equityInfoValid.checkInterfEquitySingle(tmp, registList, map1);
//                map.put("TYPE", tmp.getType());
//                map.put("ORG_INDENTIFY_MARK",
//                    CommonUtil.getNotNullString(map.get("ORG_INDENTIFY_MARK")));
//            }
            logger.debug("EquityInfo001Service 数据插入临时表处理开始 " + DateUtil.getCurrentDateTime());
            // 将数据插入临时表
            equityInfo001Mapper.insertIntoImportTable(equityList);
            logger.debug("EquityInfo001Service 数据插入临时表处理结束 " + DateUtil.getCurrentDateTime());
            // 批量查询出需要被check的数据
            //List<EquityInfo001Dto> equityInfo001List = equityInfo001Mapper.selectEquityInfoForImpCheck();
            logger.info("selectEquityInfoForCheckBatch 查询维护表已存在数据处理开始 " + DateUtil.getCurrentDateTime());
//            List<EquityInfo001Dto> equityInfo001List = equityInfo001Mapper.selectEquityInfoForCheck(equityList);
            List<EquityInfo001Dto> equityInfo001List = new ArrayList<EquityInfo001Dto>();
            //查询本次接入的身份信息，在库里已经存在，查询结果到equityInfo001List
            this.selectEquityInfoForCheckBatch(equityList,equityInfo001List);
            logger.info("selectEquityInfoForCheckBatch 查询维护表已存在数据处理结束 " + DateUtil.getCurrentDateTime());
            Map<String, List<EquityInfo001Dto>> equityInfo001Map = equityInfo001List.stream().collect(
                    JoyinCollectors.groupingBy(
                            x -> x.getIndentifyMark() + GlobalConstant.UNDER_LINE + x.getBankCode()));
            // 当前批次报送时点集合
            Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
//            List<Date> dateList = null;
//            EquityInfo001Dto equityInfo001Dto = null;
//            Map<String, String> map1  = null;
//            EquityInfo001Dto equityInfo001DtoTemp = null;
            //add by liubin start 将接口接入的数据输出到日志文件中
            String checkDataLogFilePath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, InterfConstant.SQLLDR_LOG_FILEPATH);
            File file = new File(checkDataLogFilePath+File.separator+"check_data_log.txt");
            if(file.exists()){
                file = new File(checkDataLogFilePath+File.separator+"check_data_log_"+System.currentTimeMillis()+".txt");
            }
            StringBuffer messageInfo = new StringBuffer();
            //add by liubin end

            // tempItem = new TreeMap<String,String>();
            ChbTypeMappingExMapper chbTypeMappingExMapper = SpringContextUtil.getBean(ChbTypeMappingExMapper.class);
            List<ChbTypeMappingEx>  HoldTypeMappingList = chbTypeMappingExMapper.selectByMappingcode("HOLDTYPE");

            //modify by yanya on 20210521 批量获取索引，避免死锁
            List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, equityList.size());
            int index = 0;
            for (Map<String, String> map : equityList) {
                EquityInfo001Dto equityInfo001Dto = new EquityInfo001Dto();
                EquityInfo001Dto equityInfo001DtoTemp =new EquityInfo001Dto();
                Map<String, String> map1 = new HashMap<String, String>();
                EquityInfoMaintenanceService.maptoDtoByPrpperties(map,equityInfo001Dto,equityInfo001Map,result,interType,HoldTypeMappingList);                // 转化为key+空格+value格式
                //modify by liubin start 修改checkInterfEquitySingle，传入StringBuffer参数
                equityInfoValid.checkInterfEquitySingle(equityInfo001Dto, registList, map1, true,messageInfo,spzMap,objFiledsMap);
                //modify by liubin end

                //  tempItem.put(equityInfo001Dto.getIndentifyMark(), equityInfo001Dto.getInterfCheckResult());

                // 若上游接入的数据，与上次报送的数据，未发生改变，则不需要进行入库操作
                if ("01".equals(equityInfo001Dto.getType()) && "01".equals(map1.get("flag"))) {  //未报送：getType=='01' flag=null 已报送:getType='03' flag=null
                    continue;
                }
                String type = equityInfo001Dto.getType();

                if (!DictCodeListExt2.报送数据状态_新建.getValue().equals(type)) {
                    type = DictCodeListExt2.报送数据状态_修改.getValue();
                }
//                equityInfo001Dto.setSubmitStatus(type);
                // equityInfo001Dto = EquityInfoCheckUtil.getChbEndDate(listTime, type,
                // equityInfo001Dto);
                // 报送时点通用方法
                List<Date>  dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者身份信息登记.getValue(),
                        type, dateMap, DateUtil.now());
                if (CollectionUtils.isNotEmpty(dateList)) {
                    equityInfo001Dto.setChbSubmitDeadline(dateList.get(0));
                    equityInfo001Dto.setSubmitDeadline(dateList.get(1));
                }

                equityInfo001Dto.setDataSource(interType);
//                List<EquityInfo001Dto> equityInfoCheckList = equityInfo001Map.get(map.get(
//                    "INDENTIFY_MARK") + GlobalConstant.UNDER_LINE + CommonUtil.getNotNullString(
//                        map.get("ORG_INDENTIFY_MARK")) + GlobalConstant.UNDER_LINE + map.get(
//                            "REGIST_BANK_CODE"));
//                EquityInfo001Dto equityInfoCheck = null;
//                if (null != equityInfoCheckList && !equityInfoCheckList.isEmpty()) {
//                    equityInfoCheck = equityInfoCheckList.get(0);
//                }
                if (DictCodeList.是否_中债_是.getValue().equals(equityInfo001Dto.getIsNull())) {
                    listN.add(equityInfo001Dto);
                }
                else if(DictCodeList.是否_中债_是.getValue().equals(equityInfo001Dto.getIsNull())&&
                        DictCodeListExt1.维护数据状态_待处理.getValue().equals(equityInfo001Dto.getEditStatus())){
                    listA.add(equityInfo001Dto);
                }
                else{
                    if(DictCodeListExt2.数据类型_中债_四期信息变更.getValue().equals(map1.get("type"))) {
                        listUpForInfo.add(equityInfo001Dto);
                    }else {
                        listO.add(equityInfo001Dto);
                    }
                }
                if(DictCodeListExt2.数据类型_中债_四期信息变更.getValue().equals(map1.get("type"))) {
                    listUpForSub.add(equityInfo001Dto);
                }else {
                    if(DictCodeListExt1.维护数据状态_有效.getValue().equals(equityInfo001Dto.getStatus())){
                        BeanUtil.copyPropertiesIgnoreNull(equityInfo001DtoTemp, equityInfo001Dto);
                        equityInfo001DtoTemp.setSumitSeq(getSequenceStr(sequenceList, index));
                        index++;
                        equityInfo001DtoTemp.setCheckSeqno("0");
                        equityInfo001DtoTemp.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                        equityInfo001DtoTemp.setStatus(type);
                        if(equityInfo001DtoTemp.getTempMap()!=null){
                            for(String key:equityInfo001DtoTemp.getTempMap().keySet()){
                                EquityInfoCheckUtil.updateChcekDto(equityInfo001DtoTemp,key,equityInfo001DtoTemp.getTempMap().get(key));
                            }
                        }
                        listU.add(equityInfo001DtoTemp);
                    }
                }
            }
            //add by liubin start 若为接口接入数据，则将错误日志输出到文件
            if(interType.equals(DictCodeListExt2.数据来源_销售系统.getValue()) || interType.equals(DictCodeListExt2.数据来源_资管系统.getValue())
                    || interType.equals(DictCodeListExt2.数据来源_核心系统.getValue()) || interType.equals(DictCodeListExt2.数据来源_客户关系管理系统.getValue())
                    || interType.equals(DictCodeListExt2.数据来源_万得系统.getValue())){
                OutputStreamWriter osw = null;
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(file);
                    osw = new OutputStreamWriter(fout);
                    osw.append(messageInfo.toString()).append("\n");
                    osw.flush();
                } catch (Exception e1) {
//        			e1.printStackTrace();
                    logger.error(e1.getMessage(),e1);
                }
                finally {
                    if(fout !=null ){
                        try {
                            fout.close();
                        } catch (Exception e) {
                            logger.error(e.getMessage(),e);
                        }
                    }

                    if(osw!=null){
                        try {
                            osw.close();
                        } catch (Exception e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                }
            }
            //add by liubin end
            // 插入信息前为待处理时，先跟新维护表，在插入更新表
            if (CollectionUtils.isNotEmpty(listA)) {
                logger.info("EquityInfo001Service listA 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
                // 插入维护信息表
                this.updateBtachEquityInfoByPart(listA,isDB2DataBase);
                // 删除登记表中未报送数据(若数据由有效转为待处理后，又插入一条有效数据)
                this.deleteBatchEquityRegist(listA);
                logger.info("EquityInfo001Service listA 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());

            }
            // 当插入信息为有效或者删除是，先将信息迁移至履历表，再更新信息维护表，之后先统一删除登记表中报送中的数据，插入登记表
            if (CollectionUtils.isNotEmpty(listO)) {
                logger.info("EquityInfo001Service listO 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
                // 首先将权益人信息表插入履历表中
                //equityInfo001Mapper.insertEquityHis(listOA);
                this.insertEquityHis(listO);
                // 插入维护信息表
                //equityInfo001Mapper.updateBatchEquityInfo(listOA);
                this.updateBtachEquityInfoByPart(listO,isDB2DataBase);
                // 删除登记表中未报送数据
                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listOA);
                this.deleteBatchEquityRegist(listO);
                logger.info("EquityInfo001Service listO 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());
            }
            //只有四期信息发生变更的情况下，对INFO表进行更新变更字段
            if (CollectionUtils.isNotEmpty(listUpForInfo)) {
                logger.info("EquityInfo001Service listUpForInfo 四期INFO更新开始 " + DateUtil.getCurrentDateTime());

                // 更新身份信息INFO表
                this.updateEuqHoldInfoByBatch(listUpForInfo);
                logger.info("EquityInfo001Service listUpForInfo 四期INFO更新结束 " + DateUtil.getCurrentDateTime());
            }

            //只有四期信息发生变更的情况下，对SUB表进行更新变更字段
            if (CollectionUtils.isNotEmpty(listUpForSub)) {
                logger.info("EquityInfo001Service listUpForSub 四期SUB更新开始 " + DateUtil.getCurrentDateTime());

                // 更新身份信息SUB表
                this.updateEuqHoldSubByBatch(listUpForSub);

                logger.info("EquityInfo001Service listUpForSub 四期SUB更新结束 " + DateUtil.getCurrentDateTime());
            }
            return true;
        }
        return false;
    }

    public List<EquityInfoRegist001Dto> getNeedSendList(
            Map<String, Object> param) {
        // TODO Auto-generated method stub
        param.put("submitStatus", DictCodeListExt2.报送状态_未报送.getValue());
        param.put("deptCode", CommonUtil.getDeptCode());
        List<EquityInfoRegist001Dto> list= this.queryExportRegistList(param);
        return list;
    }
    /**
     * 根据条件查询出需要导出的
     * @param param 输入条件
     * @return 查询得到的列表
     * @see
     * @since 1.0
     */
    public List<EquityInfoRegist001Dto> queryExportRegistList(Map<String, Object> param){
        String orgCode = CommonUtil.getOrgCode();
        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
        param.put("orgCodeList", orgCodeList);
        String personalIdentifyType = (String)param.get("personalIdentifyType");
        String institutionIdentifyType = (String)param.get("institutionIdentifyType");
        String status = (String)param.get("status");
        String submitStatus = (String)param.get("submitStatus");
        String submitFeedbackStatus = (String)param.get("submitFeedbackStatus");
        param.put("personalIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, personalIdentifyType));
        param.put("institutionIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, institutionIdentifyType));
        param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
        param.put("submitStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitStatus));
        param.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
        param.put("submitDeadLineStart", DateCalcUtil.getEndOfDay((String)param.get("submitDeadLineStart")));
        param.put("submitDeadLineEnd", DateCalcUtil.getEndOfDay((String)param.get("submitDeadLineEnd")));
        List<EquityInfoRegist001Dto> pageList = equityInfoRegist001Mapper.queryForEquityInfoRegistPage(param);
        EquityInfoRegistCommonUtil.format(pageList," ");
        return pageList;

    }

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

    /**
     * 从接口中获取权益人信息（数据开关关闭不覆盖）
     * <ul>
     * <li>1.调用共通方法解析传入的接口文件</li>
     * <li>2.遍历解析后的数据，逐条插入验证（暂定）</li>
     * <li>3.验证中债需要验证的模块，通过则赋有效状态；不通过，则显示待处理状态</li>
     * <li>4.验证通过后，判断是否新增数据
     * <ul>
     * <li>4.1 若为新增数据，则直接insert</li>
     * <li>4.2 若非新增数据，则需读取是否覆盖开关</li>
     * <li>4.2.1 若开关打开，只更新数据配置项中的字段 4.2.2</li>
     * </ul>
     * </li>
     * <li>若开关关闭，且数据库中为接口维护，则全量更新；若数据库中为手动维护，则不更新</li>此条规则去掉
     * </ul>
     *
     * @param in 输入流
     * @param interType 来源系统
     * @return string 处理成功失败
     * @param tmpPath 临时存放路径
     * @param suffix 文件后缀名
     */
    public String getEquityInfoByInterfYC(InputStream in, String interType, String tmpPath,
                                          String suffix, ChbFileReadMonitorInfo monitorInfoDto,String realName) {

        // 如果接口尚未处理完成，则不处理
        if (equityInfo001Mapper.selectTmpCount() > 0) {
            return null;
        }

        List<Map<String, String>> equityList = null;
        if ("xml".equals(suffix)) {
            try {
                equityList = XmlParseUtil.parse(in);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(),e);
                return "fail";
            }
        }
        else if ("txt".equals(suffix)) {
            try {
                equityList = TxtParseUtil.parse(in, reportInterfFileEncoding,reportInterfSplitCharacter);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(),e);
                return "fail";
            }
        }
        TotalAmountInfoDto totalAmountInfoDto = new TotalAmountInfoDto();
        totalAmountInfoDto.setId(UUID.randomUUID().toString());
        totalAmountInfoDto.setReportDate(realName.substring(30,38));
        totalAmountInfoDto.setInterfaceType("1");
        totalAmountInfoDto.setFileName(realName);
        totalAmountInfoDto.setFileCount(equityList.size()+"");

        //-----add by lingxq on 2019-03-21 for 三要素合并升级------>>>>>
        EquityInfoDistinctService equityInfoDistinctService =
                SpringContextUtil.getBean(EquityInfoDistinctService.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate=new Date();
        Long bt=bdate.getTime();
        logger.info("身份信息三要素合并开始:"+sdf.format(bdate));
        // 过滤不合理数据,使其进入待维护状态
        List<Map<String, String>> temList=equityInfoDistinctService.rejectUnqualifiedData(equityList);
        List<String> keys = equityInfoDistinctService.getKeys(equityList);
        logger.info("checkSendedData开始");
        equityInfoDistinctService.checkSendedData(equityList,keys,"01");
        logger.info("checkSendedData结束");
        logger.info("checkAllData开始");
        List<String> mergerKeys=equityInfoDistinctService.checkAllData(equityList,keys,"01");
        logger.info("checkAllData结束");
        logger.info("merger开始");
        equityInfoDistinctService.merger(equityList,"map");
        logger.info("merger结束");
        logger.info("deleteEquitInfoAndSub开始");
        Date bdate1=new Date();
        Long bt1=bdate1.getTime();
        equityInfoDistinctService.deleteEquitInfoAndSub(mergerKeys);
        Date bdate2=new Date();
        Long bt2=bdate2.getTime();
        Long time2=(bt2-bt1)/1000;
        logger.info("deleteEquitInfoAndSub结束"+"耗时"+time2+"秒");
        Date edate=new Date();
        Long et=edate.getTime();
        Long time=(et-bt)/1000;
        logger.info("身份信息三要素合并结束:"+sdf.format(edate)+"耗时"+time+"秒");
        //-----add by lingxq on 2019-03-21 for 三要素合并升级------<<<<<
        equityList.addAll(temList);
        // 解析成功或失败的标识
        //查询全部的已报送数据
//      final Map<String, EquityInfoRegist001Dto> mapRegist = new HashMap<String,EquityInfoRegist001Dto>();
        List<EquityInfoRegist001Dto> registList=new ArrayList<EquityInfoRegist001Dto>();
        template.select("com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper.queryBatchForEquityInfoConStrast",
                new ResultHandler() {
                    @Override
                    public void handleResult(ResultContext resultContext) {
                        registList.add((EquityInfoRegist001Dto) resultContext.getResultObject());
                    }
                });
//      List<EquityInfoRegist001Dto> registList =equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(null);  // 此处的xml要增加if条件判断
        Map<String, List<EquityInfoRegist001Dto>> mapRegist = registList.stream()
                .collect(
                        JoyinCollectors.groupingBy(
                                x -> x.getBankCode() + GlobalConstant.VERTICAL_LINE
                                        + GlobalConstant.VERTICAL_LINE + x.getIndentifyMark()));

        //将registlist改成map结构key=REGIST_B
        //查询出所有的已处理过的生僻字 map
        Map<String, Object> paramap=new HashMap<String, Object>();
        paramap.put("statusList", Arrays.asList("E"));
        List<TransformRule001Dto> allList=transformRule001Mapper.selectAllData(paramap);
        Map<String,TransformRule001Dto> spzMap=allList.stream().collect(
                Collectors.toMap(x ->x.getTransType()+x.getOrgField(),part->part,(k1,k2)->k1));
        List<ChbTransformRuleObjDto> allRuleObjList=chbTransformRuleObjDtoMapper.selectAllDataByTableId(CHB_EQU_HOLD);
        Map<String,List<ChbTransformRuleObjDto>> objFiledsMap=allRuleObjList.stream().collect(
                JoyinCollectors.groupingBy(
                        x -> x.getInfoProperty() ));

        boolean success = true;
        Map<String,String> resultMap = new TreeMap<String,String>();
        if (CollectionUtils.isNotEmpty(equityList)) {
            monitorInfoDto.setFileDataCount(BigDecimal.valueOf(equityList.size()));

            if (equityList.size() <= 1000) {
                try {
                    resultMap = excuteEquityInfoByStepNewYC(equityList, interType, null, null, null,mapRegist,spzMap,objFiledsMap);//修改excuteEquityInfoByStepNew方法中的
                } catch (ParserConfigurationException | SAXException | IOException e) {
                    logger.error(e.getMessage(),e);
                    return "fail";
                }
            }
            else {
                // 创建表
                ImpTable table = new ImpTable("CHB_EQU_HOLD");
                table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
                // 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
                for (ImpColumn column : table.getColumns()) {
                    if (StringUtil.equals(column.getName(), "REGIST_BANK_CODE")) {
                        column.setValueField("bankCode");
                    }
                }
                ImpTable subTable = new ImpTable(CHB_EQU_SUB);
                subTable.setColumns(ImportGenerator.createImpTableColumns(subTable.getName()));
                subTable.setSequences(ImportGenerator.createSequence("SUMIT_SEQ", "CHB_SEQUENCE"));
                // 由于DTO中登记银行代码和数据库字段名不匹配，修改登记银行代码的字段名
                for (ImpColumn column : subTable.getColumns()) {
                    if (StringUtil.equals(column.getName(), "REGIST_BANK_CODE")) {
                        column.setValueField("bankCode");
                    }
//                    else if (StringUtil.equals(column.getName(), "STATUS")) {
//                        column.setConstant(DictCodeListExt2.报送数据状态_新建.getValue());
//                    }
                }

                // 创建一个线程池
                if (interfMaxCorePoolSize < 1) {
                    interfMaxCorePoolSize = 20;
                }
                ExecutorService service = new JoyinThreadPoolExecutor(interfMaxCorePoolSize);
                List<Callable<Map<String, String>>> tasks = new ArrayList<>();
                if (null != equityList) {
                    int start = 0;
                    int end = 0;
                    int size = equityList.size();
                    int max = (int)Math.ceil((double)size / 1000);
                    for (int i = 0; i < max; i++ ) {
                        start = end;
                        end = Math.min(size, 1000 * (i + 1));
                        List<Map<String, String>> subList = equityList.subList(start, end);
                        tasks.add(new Callable<Map<String, String>>() {

                            @Override
                            public Map<String, String> call() {
                                try {
                                    return excuteEquityInfoByStepNewYC(subList, interType, tmpPath, table,
                                            subTable,mapRegist,spzMap,objFiledsMap);//修改excuteEquityInfoByStepNew方法中的
                                } catch (ParserConfigurationException | SAXException | IOException e) {
                                    logger.info(e.getMessage(),e);
//                                    return false;
                                    return null;
                                }
                            }
                        });
                    }
                }

                // 为线程池添加任务
                List<Future<Map<String, String>>> taskResult = null;
                try {
                    taskResult = service.invokeAll(tasks);
                } catch (InterruptedException e1) {
                    logger.error(e1.getMessage(),e1);
                    Thread.currentThread().interrupt();
                    return "fail";
                }
                for (Future<Map<String, String>> res : taskResult) {
                    try {
//                        success = success && res.get();
                        resultMap.putAll(res.get());
                    } catch (InterruptedException | ExecutionException e) {
                        logger.error(e.getMessage(),e);
                        Thread.currentThread().interrupt();
                        return "fail";
                    }
                }
                // 任务结束，停止线程池的所有线程
                service.shutdown();
                Collection<File> files = FileUtils.listFiles(new File(tmpPath),
                        new String[] {"tmp"}, false);
                List<String> content = new ArrayList<>();
                List<String> subContent = new ArrayList<>(equityList.size());
                for (File f : files) {
                    if (f.getName().startsWith("CHB_EQU_HOLD")) {
                        try {
                            content.addAll(FileUtils.readLines(f, Charset.forName("UTF-8")));
                            if ( content.size()>= reportInterfOneTimeSqlLdrDataCount ) {
                                imptDB(content,table,tmpPath);
                                content = null;
                                content = new ArrayList<>();
                            }
                        } catch (IOException e) {
                            logger.error(e.getMessage(),e);
                            return "fail";
                        }
                    }
                    else if (f.getName().startsWith("CHB_EQU_SUB")) {
                        try {
                            subContent.addAll(FileUtils.readLines(f, Charset.forName("UTF-8")));
                            if ( subContent.size()>= reportInterfOneTimeSqlLdrDataCount ) {
                                imptDB(subContent,subTable,tmpPath);
                                subContent = null;
                                subContent = new ArrayList<>();
                            }

                        } catch (IOException e) {
                            logger.error(e.getMessage(),e);
                            return "fail";
                        }
                    }
                    f.delete();
                }

                imptDB(content,table,tmpPath);
                imptDB(subContent,subTable,tmpPath);
            }
        }
        /** 将校验结果与源数据进行拼接 add by xiewei 2017.11.27 start */
        int successCount=0;
        for (Map<String, String> sourceData : equityList) {
            String value = resultMap.get(sourceData.get("INDENTIFY_MARK"));
            if (value != null && !"".equals(value)) {
                sourceData.put(ReportGlobalConstant.RESULT_CODE,
                        ReportGlobalConstant.ERROR_CODE);
                sourceData.put(ReportGlobalConstant.RESULT, value);
            } else {
                sourceData.put(ReportGlobalConstant.RESULT_CODE,
                        ReportGlobalConstant.CORRECT_CODE);
                sourceData.put(ReportGlobalConstant.RESULT,
                        ReportGlobalConstant.CORRECT_INFO);
                successCount++;
            }
        }
        //反馈equityList结果
        FileIO.sendResponseFile(equityList, realName);

        totalAmountInfoDto.setFileInData(successCount+"");

        List<TotalAmountInfoDto> totalAmountInfoDtoList=checkNumMapper.queryTotalAmountInfo(totalAmountInfoDto);
        if(totalAmountInfoDtoList.size()!=0){
            checkNumMapper.deltotalAmountInfoDto(totalAmountInfoDto);
        }
        checkNumMapper.insertTotalAmountInfo(totalAmountInfoDto);
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
     * 分批次处理步骤
     * @param equityList 接口传入的数据
     * @param interType  接口类型
     * @param tmpPath    是否文件
     * @param table      维护表名
     * @param subTable   登记表名
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */

    public Map<String, String> excuteEquityInfoByStepNewYC(List<Map<String, String>> equityList, String interType,
                                                           String tmpPath, ImpTable table, ImpTable subTable,
                                                           Map<String, List<EquityInfoRegist001Dto>> mapRegist,
                                                           Map<String,TransformRule001Dto> spzMap,Map<String,List<ChbTransformRuleObjDto>> objFiledsMap)
            throws ParserConfigurationException, SAXException, IOException {

        Map<String,String> tempItem = null;

        Map<String, Integer> mapPrioty = new HashMap<String, Integer>();
        // 从配置文件中读取需要覆盖的配置项 01覆盖02不覆盖
        String[] result = this.getProPrity(interType, mapPrioty);
        // 新增数据list
//        List<EquityInfo001Dto> listNA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listNE = new ArrayList<EquityInfo001Dto>();
        // 待处理接口维护数据
//        List<EquityInfo001Dto> listAA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listAE = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listOA = new ArrayList<EquityInfo001Dto>();
//        List<EquityInfo001Dto> listOE = new ArrayList<EquityInfo001Dto>();
        List<EquityInfo001Dto> listU = new ArrayList<EquityInfo001Dto>(); // 登记表数据
        // 维护新增list
        List<EquityInfo001Dto> listN = new ArrayList<EquityInfo001Dto>();
        //维护updatelist 维护表中数据状态为待处理
        List<EquityInfo001Dto> listA = new ArrayList<EquityInfo001Dto>();
        //维护updatelist 维护表中数据状态为有效或者删除
        List<EquityInfo001Dto> listO = new ArrayList<EquityInfo001Dto>();
        //修改已报送过的数据对比方式
//        List<EquityInfoRegist001Dto> registList = equityInfoRegist001Mapper.queryBatchForEquityInfoRegist(
//            equityList);
        logger.info("EquityInfo001Service queryBatchForEquityInfoConStrast处理开始 " + DateUtil.getCurrentDateTime());
//        List<EquityInfoRegist001Dto> registList = equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(equityList);
        List<EquityInfoRegist001Dto> registList = new ArrayList<EquityInfoRegist001Dto>();
//        this.queryBatchForEquityInfoConStrastBatch(equityList,registList);
        this.queryBatchForEquityInfoConStrastBatchFromMem(equityList,registList,mapRegist);
        logger.info("EquityInfo001Service queryBatchForEquityInfoConStrast处理结束 " + DateUtil.getCurrentDateTime());
        // 系统是否使用的db2数据库
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();

        if (equityList != null && equityList.size() > 0) {
//            for (Map<String, String> map : equityList) {
//                EquityInfo001Dto tmp = new EquityInfo001Dto();
//                Map<String, String> map1 = new HashMap<String, String>();
//                EquityInfoMaintenanceService.maptoDto(map, tmp);
//                // 转化为key+空格+value格式
//                equityInfoValid.checkInterfEquitySingle(tmp, registList, map1);
//                map.put("TYPE", tmp.getType());
//                map.put("ORG_INDENTIFY_MARK",
//                    CommonUtil.getNotNullString(map.get("ORG_INDENTIFY_MARK")));
//            }
            logger.debug("EquityInfo001Service 数据插入临时表处理开始 " + DateUtil.getCurrentDateTime());
            // 将数据插入临时表
            equityInfo001Mapper.insertIntoImportTable(equityList);
            logger.debug("EquityInfo001Service 数据插入临时表处理结束 " + DateUtil.getCurrentDateTime());
            // 批量查询出需要被check的数据
            //List<EquityInfo001Dto> equityInfo001List = equityInfo001Mapper.selectEquityInfoForImpCheck();
            logger.info("selectEquityInfoForCheckBatch 查询维护表已存在数据处理开始 " + DateUtil.getCurrentDateTime());
//            List<EquityInfo001Dto> equityInfo001List = equityInfo001Mapper.selectEquityInfoForCheck(equityList);
            List<EquityInfo001Dto> equityInfo001List = new ArrayList<EquityInfo001Dto>();
            //查询本次接入的身份信息，在库里已经存在，查询结果到equityInfo001List
            this.selectEquityInfoForCheckBatch(equityList,equityInfo001List);
            logger.info("selectEquityInfoForCheckBatch 查询维护表已存在数据处理结束 " + DateUtil.getCurrentDateTime());
            Map<String, List<EquityInfo001Dto>> equityInfo001Map = equityInfo001List.stream().collect(
                    JoyinCollectors.groupingBy(
                            x -> x.getIndentifyMark() + GlobalConstant.UNDER_LINE + x.getBankCode()));
            // 当前批次报送时点集合
            Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
            List<Date> dateList = null;
            EquityInfo001Dto equityInfo001Dto = null;
            Map<String, String> map1  = null;
            EquityInfo001Dto equityInfo001DtoTemp = null;
            //add by liubin start 将接口接入的数据输出到日志文件中
            String checkDataLogFilePath = SvrDataUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY, InterfConstant.SQLLDR_LOG_FILEPATH);
            File file = new File(checkDataLogFilePath+File.separator+"check_data_log.txt");
            if(file.exists()){
                file = new File(checkDataLogFilePath+File.separator+"check_data_log_"+System.currentTimeMillis()+".txt");
            }
            StringBuffer messageInfo = new StringBuffer();
            //add by liubin end

            tempItem = new TreeMap<String,String>();
            //modify by yanya on 20210521 批量获取索引，避免死锁
            ChbTypeMappingExMapper chbTypeMappingExMapper = SpringContextUtil.getBean(ChbTypeMappingExMapper.class);
            List<ChbTypeMappingEx>  HoldTypeMappingList = chbTypeMappingExMapper.selectByMappingcode("HOLDTYPE");
            int index = 0;
            for (Map<String, String> map : equityList) {
                equityInfo001Dto = new EquityInfo001Dto();
                equityInfo001DtoTemp =new EquityInfo001Dto();
                map1 = new HashMap<String, String>();
                EquityInfoMaintenanceService.maptoDtoByPrpperties(map,equityInfo001Dto,equityInfo001Map,result,interType,HoldTypeMappingList);                // 转化为key+空格+value格式
                //modify by liubin start 修改checkInterfEquitySingle，传入StringBuffer参数
                equityInfoValid.checkInterfEquitySingle(equityInfo001Dto, registList, map1, true,messageInfo,spzMap,objFiledsMap);
                //modify by liubin end

                tempItem.put(equityInfo001Dto.getIndentifyMark(), equityInfo001Dto.getInterfCheckResult());

                // 若上游接入的数据，与上次报送的数据，未发生改变，则不需要进行入库操作
                if ("01".equals(equityInfo001Dto.getType()) && "01".equals(map1.get("flag"))) {  //未报送：getType=='01' flag=null 已报送:getType='03' flag=null
                    continue;
                }
                String type = equityInfo001Dto.getType();

                if (!DictCodeListExt2.报送数据状态_新建.getValue().equals(type)) {
                    type = DictCodeListExt2.报送数据状态_修改.getValue();
                }
//                equityInfo001Dto.setSubmitStatus(type);
                // equityInfo001Dto = EquityInfoCheckUtil.getChbEndDate(listTime, type,
                // equityInfo001Dto);
                // 报送时点通用方法
                dateList = ChbDeadlineUtil.getChbEndDate(DictCodeListExt2.报表名称_投资者身份信息登记.getValue(),
                        type, dateMap, DateUtil.now());
                if (CollectionUtils.isNotEmpty(dateList)) {
                    equityInfo001Dto.setChbSubmitDeadline(dateList.get(0));
                    equityInfo001Dto.setSubmitDeadline(dateList.get(1));
                }

                equityInfo001Dto.setDataSource(interType);
//                List<EquityInfo001Dto> equityInfoCheckList = equityInfo001Map.get(map.get(
//                    "INDENTIFY_MARK") + GlobalConstant.UNDER_LINE + CommonUtil.getNotNullString(
//                        map.get("ORG_INDENTIFY_MARK")) + GlobalConstant.UNDER_LINE + map.get(
//                            "REGIST_BANK_CODE"));
//                EquityInfo001Dto equityInfoCheck = null;
//                if (null != equityInfoCheckList && !equityInfoCheckList.isEmpty()) {
//                    equityInfoCheck = equityInfoCheckList.get(0);
//                }
                if (DictCodeList.是否_中债_是.getValue().equals(equityInfo001Dto.getIsNull())) {
                    listN.add(equityInfo001Dto);
                }
                else if(DictCodeList.是否_中债_是.getValue().equals(equityInfo001Dto.getIsNull())&&
                        DictCodeListExt1.维护数据状态_待处理.getValue().equals(equityInfo001Dto.getEditStatus())){
                    listA.add(equityInfo001Dto);
                }
                else{
                    listO.add(equityInfo001Dto);
                }
//                else if (equityInfoCheck == null && "E".equals(equityInfo001Dto.getStatus())) {
//                	if (tmpPath == null || isDB2DataBase) {
//                		equityInfo001Dto.setSumitSeq(
//                				CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                		}
//                        listNE.add(equityInfo001Dto);
//                }
//                else if ("A".equals(equityInfoCheck.getStatus())&& "A".equals(equityInfo001Dto.getStatus())) {
//                        this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                            result, listAA, interType, mapPrioty);
//                }
//                else if ("A".equals(equityInfoCheck.getStatus()) && "E".equals(equityInfo001Dto.getStatus())) {
//                	this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                			result, listAE, interType, mapPrioty);
//                }
//                else if ("A".equals(equityInfo001Dto.getStatus())) {
//                     this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                            result, listOA, interType, mapPrioty);
//                }
//                else if ("E".equals(equityInfo001Dto.getStatus())) {
//                	this.getEquityInfo001DtoByIsOpen(equityInfoCheck, equityInfo001Dto, map,
//                            result, listOE, interType, mapPrioty);
//                }
                if(DictCodeListExt1.维护数据状态_有效.getValue().equals(equityInfo001Dto.getStatus())){
                    BeanUtil.copyPropertiesIgnoreNull(equityInfo001DtoTemp, equityInfo001Dto);
                    if (tmpPath == null || isDB2DataBase) {
                        equityInfo001DtoTemp.setSumitSeq(
                                CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
                    }
                    equityInfo001DtoTemp.setCheckSeqno("0");
                    equityInfo001DtoTemp.setSubmitStatus(DictCodeListExt2.报送状态_未报送.getValue());
                    equityInfo001DtoTemp.setStatus(type);
                    if(equityInfo001DtoTemp.getTempMap()!=null){
                        for(String key:equityInfo001DtoTemp.getTempMap().keySet()){
                            EquityInfoCheckUtil.updateChcekDto(equityInfo001DtoTemp,key,equityInfo001DtoTemp.getTempMap().get(key));
                        }
                    }
                    listU.add(equityInfo001DtoTemp);
                }
//                if ("01".equals(equityInfo001Dto.getType())
//                    || "03".equals(equityInfo001Dto.getType())) {
//                    if ("E".equals(equityInfo001Dto.getStatus())) {
//                        if (equityInfoCheck == null) {
//                            if (tmpPath == null || isDB2DataBase) {
//                                equityInfo001Dto.setSumitSeq(
//                                		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                            }
//                            listU.add(equityInfo001Dto);
//                        }
//                        else if ("01".equals(equityInfoCheck.getDataSource())
//                                 || "02".equals(equityInfoCheck.getDataSource())) {
//                            EquityInfo001Dto equityInfo = new EquityInfo001Dto();
//                            EquityInfoMaintenanceService.maptoSetDto(map, equityInfo, result);
//                            equityInfo.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                            equityInfo.setType(equityInfo001Dto.getType());
//                            equityInfo.setOrgCode(equityInfo001Dto.getOrgCode());
//                            equityInfo.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
//                            equityInfo.setChbSubmitDeadline(
//                                equityInfo001Dto.getChbSubmitDeadline());
//                            if (tmpPath == null || isDB2DataBase) {
//                                equityInfo.setSumitSeq(
//                                		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                            }
//                            listU.add(equityInfo);
//                        }
//                        else {
//                            if (tmpPath == null || isDB2DataBase) {
//                                equityInfo001Dto.setSumitSeq(
//                                		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                            }
//                            listU.add(equityInfo001Dto);
//                        }
//                    }
//                }
//                else if ("02".equals(equityInfo001Dto.getType())
//                         && "E".equals(equityInfo001Dto.getStatus())) {
//                    EquityInfo001Dto equityInfo001Dto1 = EquityInfoMaintenanceService.mapToSetProDto(
//                        map, interType, mapPrioty, result, equityInfoCheck,
//                        equityInfo001Dto.getType());
//                    equityInfo001Dto1.setBankCode(equityInfo001Dto.getBankCode());
//                    equityInfo001Dto1.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
//                    equityInfo001Dto1.setOrgIndentifyMark(equityInfo001Dto.getOrgIndentifyMark());
//                    equityInfo001Dto1.setType(equityInfo001Dto.getType());
//                    equityInfo001Dto1.setOrgCode(equityInfo001Dto.getOrgCode());
//                    equityInfo001Dto1.setDeptCode(equityInfo001Dto.getDeptCode());
//                    equityInfo001Dto1.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                    if (tmpPath == null || isDB2DataBase) {
//                        equityInfo001Dto.setSumitSeq(
//                        		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                    }
//                    equityInfo001Dto1.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
//                    equityInfo001Dto1.setChbSubmitDeadline(
//                        equityInfo001Dto.getChbSubmitDeadline());
//                    listU.add(equityInfo001Dto1);
//                }
//                else if ("04".equals(equityInfo001Dto.getType())
//                         && "E".equals(equityInfo001Dto.getStatus())) {
//                    EquityInfo001Dto equityInfo001Dto1 = EquityInfoMaintenanceService.mapToSetProDto(
//                        map, interType, mapPrioty, result, equityInfoCheck,
//                        equityInfo001Dto.getType());
//                    equityInfo001Dto1.setBankCode(equityInfo001Dto.getBankCode());
//                    equityInfo001Dto1.setIndentifyMark(equityInfo001Dto.getIndentifyMark());
//                    equityInfo001Dto1.setType(equityInfo001Dto.getType());
//                    equityInfo001Dto1.setDeptCode(equityInfo001Dto.getDeptCode());
//                    equityInfo001Dto1.setSubmitStatus(equityInfo001Dto.getSubmitStatus());
//                    if (tmpPath == null || isDB2DataBase) {
//                        equityInfo001Dto.setSumitSeq(
//                        		CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
//                    }
//                    equityInfo001Dto1.setOrgCode(equityInfo001Dto.getOrgCode());
//                    equityInfo001Dto1.setSubmitDeadline(equityInfo001Dto.getSubmitDeadline());
//                    equityInfo001Dto1.setChbSubmitDeadline(
//                        equityInfo001Dto.getChbSubmitDeadline());
//                    listU.add(equityInfo001Dto1);
//                }
            }
            //add by liubin start 若为接口接入数据，则将错误日志输出到文件
            if(interType.equals(DictCodeListExt2.数据来源_销售系统.getValue()) || interType.equals(DictCodeListExt2.数据来源_资管系统.getValue())
                    || interType.equals(DictCodeListExt2.数据来源_核心系统.getValue()) || interType.equals(DictCodeListExt2.数据来源_客户关系管理系统.getValue())
                    || interType.equals(DictCodeListExt2.数据来源_万得系统.getValue())){
                OutputStreamWriter osw = null;
                FileOutputStream fout = null;
                try {
                    fout = new FileOutputStream(file);
                    osw = new OutputStreamWriter(fout);
                    osw.append(messageInfo.toString()).append("\n");
                    osw.flush();
                } catch (Exception e1) {
//        			e1.printStackTrace();
                    logger.error(e1.getMessage(),e1);
                }
                finally {
                    if(fout !=null ){
                        try {
                            fout.close();
                        } catch (Exception e) {
                            logger.error(e.getMessage(),e);
                        }
                    }

                    if(osw!=null){
                        try {
                            osw.close();
                        } catch (Exception e) {
                            logger.error(e.getMessage(),e);
                        }
                    }
                }
            }
            //add by liubin end
            // 插入的权益人信息为空时,先插入登记维护表，再插入信息登记表
            if (CollectionUtils.isNotEmpty(listN)) {
                logger.info("EquityInfo001Service listN 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
                if (tmpPath == null) {
                    // 插入维护信息表
                    //修改为分批插入
                    //equityInfo001Mapper.insertBtachInterfEquityInfo(listNA);
                    this.insertBtachInterfEquityInfoByPart(listN);
                    equityInfoDistinctService.refreshIndentifyMark(listN,0);
                }
                else {
                    createDataFile(tmpPath + "/CHB_EQU_HOLD", listN, table.getColumns());
                }
                logger.info("EquityInfo001Service listN 入库或生成文件处理结束" + DateUtil.getCurrentDateTime());
            }
            // 插入信息前为待处理时，先跟新维护表，在插入更新表
            if (CollectionUtils.isNotEmpty(listA)) {
                logger.info("EquityInfo001Service listA 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
                // 插入维护信息表
                this.updateBtachEquityInfoByPart(listA,isDB2DataBase);
                // 删除登记表中未报送数据(若数据由有效转为待处理后，又插入一条有效数据)
                this.deleteBatchEquityRegist(listA);
                logger.info("EquityInfo001Service listA 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());

            }
            // 当插入信息为有效或者删除是，先将信息迁移至履历表，再更新信息维护表，之后先统一删除登记表中报送中的数据，插入登记表
            if (CollectionUtils.isNotEmpty(listO)) {
                logger.info("EquityInfo001Service listO 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
                // 首先将权益人信息表插入履历表中
                //equityInfo001Mapper.insertEquityHis(listOA);
                this.insertEquityHis(listO);
                // 插入维护信息表
                //equityInfo001Mapper.updateBatchEquityInfo(listOA);
                this.updateBtachEquityInfoByPart(listO,isDB2DataBase);
                // 删除登记表中未报送数据
                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listOA);
                this.deleteBatchEquityRegist(listO);
                logger.info("EquityInfo001Service listO 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());
            }
//            // 插入的权益人信息为空时,先插入登记维护表，再插入信息登记表
//            if (CollectionUtils.isNotEmpty(listNA)) {
//                if (tmpPath == null) {
//                    // 插入维护信息表
//                	//修改为分批插入
//                    //equityInfo001Mapper.insertBtachInterfEquityInfo(listNA);
//                	this.insertBtachInterfEquityInfoByPart(listNA);
//                }
//                else {
//                    createDataFile(tmpPath + "/CHB_EQU_HOLD", listNA, table.getColumns());
//                }
//            }
//            // 插入的权益人信息为空时,先插入登记维护表，再插入信息登记表
//            if (CollectionUtils.isNotEmpty(listNE)) {
//                if (tmpPath == null) {
//                    // 插入维护信息表
//                	//修改为分批插入
//                    //equityInfo001Mapper.insertBtachInterfEquityInfo(listNA);
//                	this.insertBtachInterfEquityInfoByPart(listNE);
//                }
//                else {
//                    createDataFile(tmpPath + "/CHB_EQU_HOLD", listNE, table.getColumns());
//                }
//            }
//            // 插入信息前为待处理时，先跟新维护表，在插入更新表
//            if (CollectionUtils.isNotEmpty(listAA)) {
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listAA);
//            	this.updateBtachEquityInfoByPart(listAA,isDB2DataBase);
//            }
//            if (CollectionUtils.isNotEmpty(listAE)) {
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listAE);
//            	this.updateBtachEquityInfoByPart(listAE,isDB2DataBase);
//                // 删除登记表中未报送数据(若数据由有效转为待处理后，又插入一条有效数据)
//                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listAE);
//            	this.deleteBatchEquityRegist(listAE);
//
//            }
//            // 当插入信息为有效或者删除是，先将信息迁移至履历表，再更新信息维护表，之后先统一删除登记表中报送中的数据，插入登记表
//            if (CollectionUtils.isNotEmpty(listOA)) {
//                // 首先将权益人信息表插入履历表中
//                //equityInfo001Mapper.insertEquityHis(listOA);
//            	this.insertEquityHis(listOA);
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listOA);
//            	this.updateBtachEquityInfoByPart(listOA,isDB2DataBase);
//                // 删除登记表中未报送数据
//                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listOA);
//            	this.deleteBatchEquityRegist(listOA);
//            }
//            if (CollectionUtils.isNotEmpty(listOE)) {
//                // 首先将权益人信息表插入履历表中
//               // equityInfo001Mapper.insertEquityHis(listOE);
//            	this.insertEquityHis(listOE);
//                // 插入维护信息表
//                //equityInfo001Mapper.updateBatchEquityInfo(listOE);
//            	this.updateBtachEquityInfoByPart(listOE,isDB2DataBase);
//                // 删除登记表中未报送数据
//                //equityInfoRegist001Mapper.deleteBatchEquityRegist(listOE);
//                this.deleteBatchEquityRegist(listOE);
//            }
            if (CollectionUtils.isNotEmpty(listU)) {
                logger.info("EquityInfo001Service listU 入库或生成文件处理开始 " + DateUtil.getCurrentDateTime());
                if (tmpPath == null) {
                    // 插入登记表
                    this.insertBatchEquityRegistByPart(listU);
                    //识别标识变更的数据，需要对交易表、头寸表的识别标识进行更新
                    this.equityInfoDistinctService.refreshIndentifyMark(listU,1);
                }
                else {
//                    createDataFile(tmpPath + "/CHB_EQU_SUB", listNE, subTable.getColumns());
                    createDataFile(tmpPath + "/CHB_EQU_SUB", listU, subTable.getColumns());
                }
                logger.info("EquityInfo001Service listU 入库或生成文件处理结束 " + DateUtil.getCurrentDateTime());
            }
            // 数据解析完成，并导入数据库成功后，将ftp文件修改名称
//            return (CollectionUtils.isNotEmpty(listNA) || CollectionUtils.isNotEmpty(listNE)
//                    || CollectionUtils.isNotEmpty(listAA) || CollectionUtils.isNotEmpty(listAE)
//                    || CollectionUtils.isNotEmpty(listOA) || CollectionUtils.isNotEmpty(listOE)
//                    || CollectionUtils.isNotEmpty(listU));
            return tempItem;
        }
        return tempItem;
    }
}
