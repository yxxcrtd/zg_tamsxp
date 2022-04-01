/*
 * 文件名：EquityInfoRegist001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年9月19日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitmanagement.equityinforegist.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.datasource.DataSource;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.system.batch.batchstart.service.SysBatchStart001Service;
import com.joyintech.tams.common.base.dao.ChbBatchParaMapper;
import com.joyintech.tams.common.base.entity.ChbBatchPara;
import com.joyintech.tams.common.base.entity.ChbSubmitStatus;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.BatchBeanName;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.service.SvrBaseCommonService;
import com.joyintech.tams.report.common.util.DateCalcUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.interf.cnbond.dto.CnBondInterfItem;
import com.joyintech.tams.report.interf.cnbond.dto.InterfCnBondSubmitPara;
import com.joyintech.tams.report.interf.cnbond.service.CnBondInterfService;
import com.joyintech.tams.report.submitmaintenance.equityinfo.service.EquityInfoDistinctService;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.EquityInfoCheckUtil;
import com.joyintech.tams.report.submitmaintenance.equityinfo.validate.EquityInfoValid;
import com.joyintech.tams.report.submitmanagement.checkreport.dao.CheckReport001Mapper;
import com.joyintech.tams.report.submitmanagement.checkreport.dto.CheckReport001Dto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.common.EquityInfoRegistCommonUtil;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;


/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2017年9月19日
 * @see EquityInfoRegist001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EquityInfoRegist001Service extends CommonExportService<EquityInfoRegist001Dto>{

//    @Autowired
//    private DbCommonService dbCommonService;

    /**
    * 权益人报送Mapper
    */
    @Autowired
    private EquityInfoRegist001Mapper equityInfoRegist001Mapper;
    @Autowired
    private EquityInfoDistinctService equityInfoDistinctService;

    /**
     * {@link SvrBaseCommonService}
     */
    @Autowired
    private EquityInfoValid equityInfoValid;
    
    @Autowired
    private CnBondInterfService cnBondInterfService;

    /**
     * CheckReport001Mapper
     */
    @Autowired
    private CheckReport001Mapper checkReport001Mapper;
    
    @Autowired
    private ChbBatchParaMapper chbBatchParaMapper;
    
    @Autowired
    private SysBatchStart001Service sysBatchStart001Service;
    
    /**
     * 长sql最大一次插入或者更新条数
     */
    @Value("${application.reportInsertLongCount}")
    public  int reportInsertLongCount;
    /**
     * sql最大一次最大删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    public  int reportSelectOrDeleteCount;
    /**
     * 短sql最大一次最大插入条数
     */
    @Value("${application.reportInsertCount}")
    public  int reportInsertCount;
    /**
     * CheckReport001Mapper
     */

    private static final String IS_CHECK_SUCESS="01";

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 分页查询权益人登记信息
     * @param param 查询条件
     * @return 返回分页信息
     * @see
     * @since 1.0
     */
    public Page<EquityInfoRegist001Dto> searchForPage(Map<String, Object> param){
        String orgCode = (String)param.get("orgCode");
        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
        if (StringUtil.isBlank(orgCode)) {
            orgCodeList = CommonUtil.getOrgCodeListForSearch();
        }
        param.put("orgCodeList", orgCodeList);
        String personalIdentifyType = (String)param.get("personalIdentifyType");
        String institutionIdentifyType = (String)param.get("institutionIdentifyType");
        String status = (String)param.get("status");
        String submitStatus = (String)param.get("submitStatus");
        String submitFeedbackStatus = (String)param.get("submitFeedbackStatus");
        String holderType = (String)param.get("holderType");
        // 增加发送状态
        String sendStatus = (String)param.get("sendStatus");
        param.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
        param.put("holderTypeList", StringUtil.splitToList(GlobalConstant.COMMA, holderType));
        param.put("personalIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, personalIdentifyType));
        param.put("institutionIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, institutionIdentifyType));
        param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
        param.put("submitStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitStatus));
        param.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
//        param.put("submitDeadLineStart", DateCalcUtil.getEndOfDay((String)param.get("submitDeadLineStart")));
        param.put("submitDeadLineStart", param.get("submitDeadLineStart"));
        param.put("submitDeadLineEnd", DateCalcUtil.getEndOfDay((String)param.get("submitDeadLineEnd")));
        int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
	    int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
        Page<EquityInfoRegist001Dto> pageList = super.queryForPage(
        		pagenum,legth,()->equityInfoRegist001Mapper.queryForEquityInfoRegistPage(param));
 //       EquityInfoRegistCommonUtil.format(pageList.getRoot());
        EquityInfoRegistCommonUtil.formatForEquHoldSearchByType(pageList.getRoot(), false);
        return pageList;
    }
    /**
     * 根据条件查询出需要导出的
     * @param param 输入条件
     * @return 查询得到的列表
     * @see
     * @since 1.0
     */
    public List<EquityInfoRegist001Dto> queryExportRegistList(Map<String, Object> param){
        String orgCode = (String)param.get("orgCode");
        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
        if (StringUtil.isBlank(orgCode)) {
            orgCodeList = CommonUtil.getOrgCodeListForSearch();
        }
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
//        EquityInfoRegistCommonUtil.format(pageList," ");
    	EquityInfoRegistCommonUtil.formatByType(pageList,true);
        return pageList;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void buildExportParam(ExportParam exportParam) {
        Map<String, Object> pageParam = exportParam.getSelectParam();
        String pageIds = (String)pageParam.get("pageIds");
        if(StringUtil.isNotEmpty(pageIds)) {
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
            String orgCode = (String)pageParam.get("orgCode");
            List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
            if (StringUtil.isBlank(orgCode)) {
                orgCodeList = CommonUtil.getOrgCodeListForSearch();
            }
            pageParam.put("orgCodeList", orgCodeList);
            String personalIdentifyType = (String)pageParam.get("personalIdentifyType");
            String institutionIdentifyType = (String)pageParam.get("institutionIdentifyType");
            String status = (String)pageParam.get("status");
            String submitStatus = (String)pageParam.get("submitStatus");
            String submitFeedbackStatus = (String)pageParam.get("submitFeedbackStatus");
            pageParam.put("personalIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, personalIdentifyType));
            pageParam.put("institutionIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, institutionIdentifyType));
            pageParam.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
            pageParam.put("submitStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitStatus));
            pageParam.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
            pageParam.put("submitDeadLineStart", DateCalcUtil.getEndOfDay((String)pageParam.get("submitDeadLineStart")));
            pageParam.put("submitDeadLineEnd", DateCalcUtil.getEndOfDay((String)pageParam.get("submitDeadLineEnd")));

            String flag = (String)pageParam.get("flag");
            String configFile = null;
            // 导出中债excel
            if (StringUtil.equals("0", flag)) {
                configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf.xml";
            }
            else if(StringUtil.equals("1", flag)) {
                configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf_all.xml";
            }
            else if(StringUtil.equals("2", flag)) {
            	configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf_del.xml";
            }

            exportParam.config(configFile).select(() -> equityInfoRegist001Mapper.queryForEquityInfoRegistPage(pageParam));
        }
        // 将选择的数据导出成excel
        else if (StringUtil.equals(exportMethodFlg, "exportSelect")) {
            String exportKeys =(String) pageParam.get("expKeys");
            List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, exportKeys);
            List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
            EquityInfoRegist001Dto equityInfoRegist001Dto = null;
            for (String string : keys) {
                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
                String[] key=StringUtil.split(string, ReportGlobalConstant.VERTICAL_LINE);
                equityInfoRegist001Dto.setBankCode(key[0]);
                equityInfoRegist001Dto.setIndentifyMark(key[1]);
                equityInfoRegist001Dto.setSubmitStatus(key[2]);
                equiytRegistList.add(equityInfoRegist001Dto);
            }

            String flag = (String)pageParam.get("flag");
            String configFile = null;
            // 导出中债excel
            if (StringUtil.equals("0", flag)) {
                configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf.xml";
            }
            else {
                configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf_all.xml";
            }

            exportParam.config(configFile).select(() -> equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
        }

     // 将选择的数据导出成excel删除模板
        else if (StringUtil.equals(exportMethodFlg, "exportDel")) {
            String exportKeys =(String) pageParam.get("expKeys");
            List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, exportKeys);
            List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
            EquityInfoRegist001Dto equityInfoRegist001Dto = null;
            for (String string : keys) {
                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
                String[] key=StringUtil.split(string, "|");
                equityInfoRegist001Dto.setBankCode(key[0]);
                equityInfoRegist001Dto.setIndentifyMark(key[1]);
                equityInfoRegist001Dto.setSubmitStatus(key[2]);
                equiytRegistList.add(equityInfoRegist001Dto);
            }

            String flag = (String)pageParam.get("flag");
            String configFile = null;
           /* // 导出中债excel
            if (StringUtil.equals("0", flag)) {
                configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf.xml";
            }
            else {
                configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf_all.xml";
            }*/

            if (StringUtil.equals("2", flag)) {
                configFile = "/com/joyintech/tams/report/submitmanagement/equityinforegist/conf/export_conf_del.xml";
            }

            exportParam.config(configFile).select(() -> equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
        }
    }

    @Override
    public com.joyintech.framework.grid.Page<EquityInfoRegist001Dto> selectPageData(Map<String, Object> pageParam) {
        List<EquityInfoRegist001Dto> data = null;
//    	Page<EquityInfoRegist001Dto> page = null;
        String exportMethodFlg = (String)pageParam.get("export_method_flg");
        Page<EquityInfoRegist001Dto> pageList = new Page<EquityInfoRegist001Dto>();
        if (StringUtil.equals(exportMethodFlg, "export")) {
        	//20210827 画面未选择数据的情况下，走批处理导出，不会走这里
            data =  restTemplate.postForList(
                    SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityInfoRegist/queryForEquityInfoRegistPage",pageParam,
                    EquityInfoRegist001Dto.class);
        }  else {
        	List<EquityInfoRegist001Dto> eList = new ArrayList<EquityInfoRegist001Dto>();
        	List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, (String)pageParam.get("expKeys"));
        	EquityInfoRegist001Dto equityInfoRegist001Dto = null;
            for (String string : keys) {
                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
                String[] key=StringUtil.split(string, "|");
                equityInfoRegist001Dto.setBankCode(key[0]);
                equityInfoRegist001Dto.setIndentifyMark(key[1]);
                equityInfoRegist001Dto.setSubmitStatus(key[2]);
                eList.add(equityInfoRegist001Dto);
            }          	       
            
            int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
    	    int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
            pageList = super.queryForPage(
            		pagenum,legth,()->equityInfoRegist001Mapper.queryExportListByKeys(eList));

        }
        com.joyintech.framework.grid.Page<EquityInfoRegist001Dto> page = new com.joyintech.framework.grid.Page<EquityInfoRegist001Dto>();
        page.setRoot(pageList.getRoot());
//        page.setTotal(data.size());
        return  page;
    }
    
    public List<EquityInfoRegist001Dto> queryForEquityInfoRegistPage(Map<String,Object> param){
        String orgCode = (String)param.get("orgCode");
        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
        if (StringUtil.isBlank(orgCode)) {
            orgCodeList = CommonUtil.getOrgCodeListForSearch();
        }
        param.put("orgCodeList", orgCodeList);
        String personalIdentifyType = (String)param.get("personalIdentifyType");
        String institutionIdentifyType = (String)param.get("institutionIdentifyType");
        String status = (String)param.get("status");
        String submitStatus = (String)param.get("submitStatus");
        String submitFeedbackStatus = (String)param.get("submitFeedbackStatus");
        String holderType = (String)param.get("holderType");
        // 增加发送状态
        String sendStatus = (String)param.get("sendStatus");
        param.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
        param.put("holderTypeList", StringUtil.splitToList(GlobalConstant.COMMA, holderType));
        param.put("personalIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, personalIdentifyType));
        param.put("institutionIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, institutionIdentifyType));
        param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
        param.put("submitStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitStatus));
        param.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
        param.put("submitDeadLineStart", param.get("submitDeadLineStart"));
        param.put("submitDeadLineEnd", DateCalcUtil.getEndOfDay((String)param.get("submitDeadLineEnd")));
        return equityInfoRegist001Mapper.queryForEquityInfoRegistPage(param);
    }
    public List<EquityInfoRegist001Dto> queryExportListByKeys(List<EquityInfoRegist001Dto> equityRegistList){
        return equityInfoRegist001Mapper.queryExportListByKeys(equityRegistList);
    }
    @Override
    protected void beforeCreateExcel(List<EquityInfoRegist001Dto> contents) {
        //EquityInfoRegistCommonUtil.format(contents, " ");
    	EquityInfoRegistCommonUtil.formatByType(contents,true);
        String flag = (String)RequestUtil.getPageParam().get("flag");
        if (!StringUtil.equals("0", flag)) {
            String blank = " ";
            for (EquityInfoRegist001Dto equityInfoRegist001Dto : contents) {
                if (StringUtil.contains(equityInfoRegist001Dto.getSubmitFeedbackStatus(), blank)) {
                    equityInfoRegist001Dto.setSubmitFeedbackStatus(StringUtil.split(
                        equityInfoRegist001Dto.getSubmitFeedbackStatus(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getSubmitStatusName(), blank)) {
                    equityInfoRegist001Dto.setSubmitStatus(
                        StringUtil.split(equityInfoRegist001Dto.getSubmitStatusName(), " ")[1]);
                }
                else {
                    equityInfoRegist001Dto.setSubmitStatus(
                        equityInfoRegist001Dto.getSubmitStatusName());
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getType(), blank)) {
                    equityInfoRegist001Dto.setType(
                        StringUtil.split(equityInfoRegist001Dto.getType(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getStatusName(), blank)) {
                    equityInfoRegist001Dto.setStatus(
                        StringUtil.split(equityInfoRegist001Dto.getStatusName(), " ")[1]);
                }
                else {
                    equityInfoRegist001Dto.setStatus(equityInfoRegist001Dto.getStatusName());
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getIsBelongBank(), blank)) {
                    equityInfoRegist001Dto.setIsBelongBank(
                        StringUtil.split(equityInfoRegist001Dto.getIsBelongBank(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getHolderMark(), blank)) {
                    equityInfoRegist001Dto.setHolderMark(
                        StringUtil.split(equityInfoRegist001Dto.getHolderMark(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getIsBelongBank(), blank)) {
                    equityInfoRegist001Dto.setIsBelongBank(
                        StringUtil.split(equityInfoRegist001Dto.getIsBelongBank(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getHolderType(), blank)) {
                    equityInfoRegist001Dto.setHolderType(
                        StringUtil.split(equityInfoRegist001Dto.getHolderType(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getHolderSex(), blank)) {
                    equityInfoRegist001Dto.setHolderSex(
                        StringUtil.split(equityInfoRegist001Dto.getHolderSex(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getHolderRiskPrefer(), blank)) {
                    equityInfoRegist001Dto.setHolderRiskPrefer(
                        StringUtil.split(equityInfoRegist001Dto.getHolderRiskPrefer(), " ")[1]);
                }
                if (StringUtil.contains(equityInfoRegist001Dto.getIdentifyType(), blank)) {
                    equityInfoRegist001Dto.setIdentifyType(
                        StringUtil.split(equityInfoRegist001Dto.getIdentifyType(), " ")[1]);
                }
            }
        }
    }

    /**
     *
     * @param param 查询参数
     * @return 投资者登记信息列表
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public List<EquityInfoRegist001Dto> queryExportRegistDelList(Map<String, Object> param){
        String delKey = (String)param.get("deleteKeys");//删除的主键
 //       String flag = (String)param.get("flag");//是否设置为手工报送 01 设置 02 不设置
        String remark = (String)param.get("remark");//导出删除模板原因
        List<EquityInfoRegist001Dto> equityInfoRegistList = new ArrayList<EquityInfoRegist001Dto>();
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, delKey);
        List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
        EquityInfoRegist001Dto equityInfoRegist001Dto = null;
        //报送状态表主键
        ChbSubmitStatus chbSubmitStatus = null;
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
        for (String string : keys) {
            equityInfoRegist001Dto = new EquityInfoRegist001Dto();
            String[] key=StringUtil.split(string, ReportGlobalConstant.VERTICAL_LINE);
            equityInfoRegist001Dto.setBankCode(key[0]);
            equityInfoRegist001Dto.setIndentifyMark(key[1]);
            equityInfoRegist001Dto.setSubmitStatus(key[2]);
            equiytRegistList.add(equityInfoRegist001Dto);
            //报送状态表插入组装
            chbSubmitStatus = new ChbSubmitStatus();
            chbSubmitStatus.setSeqno(key[3]);
            chbSubmitStatus.setSubmitFeedbackStatus("03");
            listStatus.add(chbSubmitStatus);
        }
        if(CollectionUtils.isNotEmpty(equiytRegistList)){
            String submitStatus="";
            if("01".equals(param.get("flag"))){
                submitStatus = "03";
                //检查报送的数据中是否存在已报送数据
                List<EquityInfoRegist001Dto> list = equityInfoRegist001Mapper.queryRegistSubmitListByKeys(equiytRegistList);
                if(CollectionUtils.isNotEmpty(list)){
                    //讲第一次报送的数据数据履历表
                    equityInfoRegist001Mapper.insertEquityRegistHis(list);
                    //删除已报送数据
                    equityInfoRegist001Mapper.deleteSubmitRegist(list);
                }
                /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
                equityInfoRegist001Mapper.updateChbEquSubSubmitStatus(listStatus);
                /***三期CHB_SUBMIT_STATUS去除改造  20220310**/
            }

            if(StringUtil.isNotEmpty(submitStatus)||StringUtil.isNotEmpty(remark)){
                //先更新报送表，再将报送表的数据返回
                equityInfoRegist001Mapper.updateExportRegDel(submitStatus,remark,equiytRegistList);
            }
            equityInfoRegistList = equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList);
            //将数据字典的值转换成如01 男这种格式
//            EquityInfoRegistCommonUtil.format(equityInfoRegistList," ");
        	EquityInfoRegistCommonUtil.formatByType(equityInfoRegistList,true);
        }
        return equityInfoRegistList;
    }
    /**
     * excel导入功能
     * @param fileName 文件名
     * @return 错误信息
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> importData(List<List<String>> dataList,String deptCode,String orgCode){
        List<ExcelValidDto> checkResult = new ArrayList<ExcelValidDto>();
        if(!StringUtil.isEmptyList(dataList)){
            if(CollectionUtils.isNotEmpty(dataList)){
                List<EquityInfoRegist001Dto> entities = new ArrayList<>(dataList.size());
                checkResult = convertToEntity(dataList, entities,deptCode,orgCode);
                if(StringUtil.isEmptyList(checkResult)) {
                    equityInfoRegist001Mapper.insertExcelEquityRegist(entities);
                }
                return checkResult;
            }
        }
        checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0),
            this.getMessage("REPORT_COMMON_FILE_E_0016")));
        return       checkResult;
    }
    /**
     * 将报送状态改为手工处理
     * @param upKey 传入主键
     * @see
     * @since 1.0
     */
    public void updateSumStaus(String upKey){
        List<String> list = StringUtil.splitToList(GlobalConstant.COMMA, upKey);
        //权益人报送表主键
        List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
        EquityInfoRegist001Dto equityInfoRegist001Dto = null;
        //报送状态表主键
        ChbSubmitStatus chbSubmitStatus = null;
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> sumitSeqList = new ArrayList<String>();
        List<EquityInfoRegist001Dto> equiytRegistAlreadySendList = new ArrayList<EquityInfoRegist001Dto>();

        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, list.size());
        int index = 0;
        for (String data : list) {
            //投资者报送表主键组装
            equityInfoRegist001Dto = new EquityInfoRegist001Dto();
//            String[] key=StringUtil.split(string, "|");
            String[] keyArr=data.split("\\|",-1);
            equityInfoRegist001Dto.setBankCode(keyArr[0]);
            equityInfoRegist001Dto.setIndentifyMark(keyArr[1]);
            equityInfoRegist001Dto.setSubmitStatus(keyArr[2]);
            //报送状态表插入组装
            chbSubmitStatus = new ChbSubmitStatus();
            String seqno = "";
            if(StringUtil.isEmpty(keyArr[3])){
//            	seqno = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
            	seqno =CommonUtil.getSequenceStr(sequenceList, index);
            	index++;
                chbSubmitStatus.setSeqno(seqno);
                equityInfoRegist001Dto.setSumitSeq(seqno);
            }
            else {
                chbSubmitStatus.setSeqno(keyArr[3]);
                equityInfoRegist001Dto.setSumitSeq(keyArr[3]);
            }

            if (StringUtil.equals(DictCodeListExt2.报送状态_未报送.getValue(), equityInfoRegist001Dto.getSubmitStatus())) {
                equiytRegistList.add(equityInfoRegist001Dto);
                chbSubmitStatus.setSubmitFeedbackStatus("03");
                chbSubmitStatus.setSubmitType(DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
                listStatus.add(chbSubmitStatus);
            }
            else {
                sumitSeqList.add(equityInfoRegist001Dto.getSumitSeq());
                equiytRegistAlreadySendList.add(equityInfoRegist001Dto);
            }
        }

        // 未报送的数据
        if (!StringUtil.isEmptyList(equiytRegistList)) {
            // 分批处理
            int maxDataCountOneTime = 200;
            int dataCount = equiytRegistList.size();
            if (dataCount <= maxDataCountOneTime) {
                this.doMannulForNotSubmitData(equiytRegistList);
            }
            else {
                List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
                newDataList.addAll(equiytRegistList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    this.doMannulForNotSubmitData(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    this.doMannulForNotSubmitData(newDataList);
                }
            }
        }
        /***三期CHB_SUBMIT_STATUS去除改造  20220310**/

        // 未报送的数据 插入报送状态表
        if (!StringUtil.isEmptyList(listStatus)) {
            // 分批处理
            int maxDataCountOneTime = 50;
            int dataCount = listStatus.size();
            if (dataCount <= maxDataCountOneTime) {

                equityInfoRegist001Mapper.updateChbEquSubSubmitStatus(listStatus);
            }
            else {
                List<ChbSubmitStatus> newDataList = new ArrayList<ChbSubmitStatus>();
                newDataList.addAll(listStatus);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityInfoRegist001Mapper.updateChbEquSubSubmitStatus(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityInfoRegist001Mapper.updateChbEquSubSubmitStatus(newDataList);
                }
            }
        }
        /***三期CHB_SUBMIT_STATUS去除改造  20220310**/

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新报送状态
        if (!StringUtil.isEmptyList(equiytRegistAlreadySendList)) {
            // 分批处理
            int maxDataCountOneTime = 200;
            int dataCount = equiytRegistAlreadySendList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityInfoRegist001Mapper.updateInterfSubmitForAlreadySendData("03", equiytRegistAlreadySendList);
            }
            else {
                List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
                newDataList.addAll(equiytRegistAlreadySendList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityInfoRegist001Mapper.updateInterfSubmitForAlreadySendData("03", newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityInfoRegist001Mapper.updateInterfSubmitForAlreadySendData("03", newDataList);
                }
            }
        }

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新反馈状态
        if (!StringUtil.isEmptyList(sumitSeqList)) {
            // 分批处理
            int maxDataCountOneTime = 300;
            int dataCount = sumitSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityInfoRegist001Mapper.updateSubmitStatusToOk(sumitSeqList);
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(sumitSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityInfoRegist001Mapper.updateSubmitStatusToOk(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityInfoRegist001Mapper.updateSubmitStatusToOk(newDataList);
                }
            }
        }

//        //检查报送的数据中是否存在已报送数据
//        List<EquityInfoRegist001Dto> list = equityInfoRegist001Mapper.queryRegistSubmitListByKeys(equiytRegistList);
//        if(CollectionUtils.isNotEmpty(list)){
//            //讲第一次报送的数据数据履历表
//            equityInfoRegist001Mapper.insertEquityRegistHis(list);
//            //删除已报送数据
//            equityInfoRegist001Mapper.deleteSubmitRegist(list);
//        }
//        equityInfoRegist001Mapper.updateInterfSubmit("03",equiytRegistList);
//        this.updateInterfSubmitSeq(equiytRegistList);
//        //插入报送状态表
//        equityInfoRegist001Mapper.insertSubmitStatus(listStatus);
    }
    
    /**
     * add by liubin
     * 将报送状态置为手工处理
     * @param param
     */
    @SuppressWarnings("unchecked")
    public void updateSumStausAll(Map<String, Object> param){
    	//查询符合条件的数据
//    	param.put("submitStatus", DictCodeListExt2.报送状态_未报送.getValue());
    	
        //先查询出参数表的参数
        List<ChbBatchPara> chbBatchParas=chbBatchParaMapper.selectAllData();
        if(!StringUtil.isEmptyList(chbBatchParas)){
            for(ChbBatchPara single:chbBatchParas){
                if(single.getTaskId().equals(BatchBeanName.投资者身份信息登记手工处理.getTaskId())){
                    Map<String,Object> ssmap=(Map<String,Object>)JSON.parse(single.getParas());
                    param=ssmap;
                }
            }
        }
    	
    	
        param.put("deptCode", CommonUtil.getDeptCode());
        List<EquityInfoRegist001Dto> list= this.queryExportRegistList(param);
        //权益信息登记
        List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
        //报送状态表主键
        ChbSubmitStatus chbSubmitStatus = null;
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 该部分数据的报送流水号列表
        List<String> sumitSeqList = new ArrayList<String>();
        List<EquityInfoRegist001Dto> equiytRegistAlreadySendList = new ArrayList<EquityInfoRegist001Dto>();
        
        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, list.size());
        int index = 0;
        for(EquityInfoRegist001Dto registDto : list){
        	chbSubmitStatus = new ChbSubmitStatus();
        	String seqno = "";
        	if(StringUtil.isEmpty(registDto.getSumitSeq())){
//        		seqno = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
        		seqno =CommonUtil.getSequenceStr(sequenceList, index);
            	index++;
        		chbSubmitStatus.setSeqno(seqno);
        		registDto.setSumitSeq(seqno);
        	}else{
        		chbSubmitStatus.setSeqno(registDto.getSumitSeq());
        	}
        	if(StringUtil.equals(DictCodeListExt2.报送状态_未报送.getValue(),registDto.getSubmitStatus())){
        		equiytRegistList.add(registDto);
        		chbSubmitStatus.setSubmitFeedbackStatus("03");
        		chbSubmitStatus.setSubmitType(DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
        		listStatus.add(chbSubmitStatus);
        	}else{
        		sumitSeqList.add(registDto.getSumitSeq());
        		equiytRegistAlreadySendList.add(registDto);
        	}
        }
        
        //处理未报送数据
        if(!StringUtil.isEmptyList(equiytRegistList)){
        	//分批处理
        	int maxDataCountOneTime = 200;
        	int dataCount = equiytRegistList.size();
        	if (dataCount <= maxDataCountOneTime) {
                this.doMannulForNotSubmitData(equiytRegistList);
            }
            else {
                List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
                newDataList.addAll(equiytRegistList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    this.doMannulForNotSubmitData(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    this.doMannulForNotSubmitData(newDataList);
                }
            }
        }
        
        //未报送的数据，插入报送状态表
        if (!StringUtil.isEmptyList(listStatus)) {
            // 分批处理
            /***三期CHB_SUBMIT_STATUS去除改造  20220310**/

            int maxDataCountOneTime = 50;
            int dataCount = listStatus.size();
            if (dataCount <= maxDataCountOneTime) {
                equityInfoRegist001Mapper.updateChbEquSubSubmitStatus(listStatus);
            }
            else {
                List<ChbSubmitStatus> newDataList = new ArrayList<ChbSubmitStatus>();
                newDataList.addAll(listStatus);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityInfoRegist001Mapper.updateChbEquSubSubmitStatus(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityInfoRegist001Mapper.updateChbEquSubSubmitStatus(newDataList);
                }
                /***三期CHB_SUBMIT_STATUS去除改造  20220310**/

            }
        }
        
     // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新报送状态
        if (!StringUtil.isEmptyList(equiytRegistAlreadySendList)) {
            // 分批处理
            int maxDataCountOneTime = 200;
            int dataCount = equiytRegistAlreadySendList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityInfoRegist001Mapper.updateInterfSubmitForAlreadySendData("03", equiytRegistAlreadySendList);
            }
            else {
                List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
                newDataList.addAll(equiytRegistAlreadySendList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityInfoRegist001Mapper.updateInterfSubmitForAlreadySendData("03", newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityInfoRegist001Mapper.updateInterfSubmitForAlreadySendData("03", newDataList);
                }
            }
        }

        // 报送状态为接口报送且反馈状态为发送失败和中债退回的数据 更新反馈状态
        if (!StringUtil.isEmptyList(sumitSeqList)) {
            // 分批处理
            int maxDataCountOneTime = 300;
            int dataCount = sumitSeqList.size();
            if (dataCount <= maxDataCountOneTime) {
                equityInfoRegist001Mapper.updateSubmitStatusToOk(sumitSeqList);
            }
            else {
                List<String> newDataList = new ArrayList<String>();
                newDataList.addAll(sumitSeqList);

                // 分批处理
                int part = dataCount / maxDataCountOneTime; // 分批数
                for (int i = 0; i < part; i++ ) {
                    equityInfoRegist001Mapper.updateSubmitStatusToOk(newDataList.subList(0, maxDataCountOneTime));
                    newDataList.subList(0, maxDataCountOneTime).clear();
                }
                // 最后剩下的数据
                if (CollectionUtils.isNotEmpty(newDataList)) {
                    equityInfoRegist001Mapper.updateSubmitStatusToOk(newDataList);
                }
            }
        }
        
        if(!StringUtil.isEmptyList(chbBatchParas)){
            for(ChbBatchPara single:chbBatchParas){
                    chbBatchParaMapper.deleteByPrimaryKey(single.getParaId());
            }
        }
    }

    /**
     * 对于未报送的数据进行手工处理数据库处理
     *
     * @param equiytRegistList 未报送的数据
     * @see
     * @since 1.0
     */
    private void doMannulForNotSubmitData(List<EquityInfoRegist001Dto> equiytRegistList) {
      //检查报送的数据中是否存在已报送数据
        List<EquityInfoRegist001Dto> list = equityInfoRegist001Mapper.queryRegistSubmitListByKeys(equiytRegistList);
        if(CollectionUtils.isNotEmpty(list)){
            //讲第一次报送的数据数据履历表
            equityInfoRegist001Mapper.insertEquityRegistHis(list);
            //删除已报送数据
            equityInfoRegist001Mapper.deleteSubmitRegist(list);
        }
        equityInfoRegist001Mapper.updateInterfSubmit("03",equiytRegistList);
        this.updateInterfSubmitSeq(equiytRegistList);
    }

    /**
     * 已手工处理的数据将报送状态改为未报送
     * @param upKey 传入主键
     * @see
     * @since 1.0
     */
    public void cancelMannul(String upKey){
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, upKey);
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
        List<EquityInfoRegist001Dto> list = new ArrayList<EquityInfoRegist001Dto>();;
        for (String string : keys) {
            //投资者报送表主键组装
            String[] key=StringUtil.split(string, "|");
            EquityInfoRegist001Dto equityInfoRegist001Dto = new EquityInfoRegist001Dto();
            equityInfoRegist001Dto.setSumitSeq(key[0]);
            equityInfoRegist001Dto.setBankCode(key[1]);
            equityInfoRegist001Dto.setIndentifyMark(key[2]);
            list.add(equityInfoRegist001Dto);
            ChbSubmitStatus chbSubmitStatus = new ChbSubmitStatus();
            chbSubmitStatus.setSeqno(key[0]);
            listStatus.add(chbSubmitStatus);
        }
        equityInfoRegist001Mapper.updateEquityRegist(list);
        //将履历表中履历号最大的数据挪回登记表
        equityInfoRegist001Mapper.insertEquityRegistFromHis(list);
        equityInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
    }
    
    /**
     * 手工处理取消所有
     * @param map
     */
    public void cancelMannulAll(Map<String, Object> param){
    	param.put("submitStatus", "03");
//        param.put("deptCode", CommonUtil.getDeptCode());
        List<EquityInfoRegist001Dto> list= this.queryExportRegistList(param);
        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
        for(EquityInfoRegist001Dto registDto: list){
        	ChbSubmitStatus chbSubmitStatus = new ChbSubmitStatus();
        	chbSubmitStatus.setSeqno(registDto.getSumitSeq());
//        	registDto.setSumitSeq(null);
             listStatus.add(chbSubmitStatus);
        	
        }
        equityInfoRegist001Mapper.updateEquityRegist(list);
        //将履历表中履历号最大的数据挪回登记表
        equityInfoRegist001Mapper.insertEquityRegistFromHis(list);
        equityInfoRegist001Mapper.deleteMannulSubmitRegist(listStatus);
    }
    /**
     * 获取报送列表
     * @param param 界面查询条件
     * @return map
     * @see
     * @since 1.0
     */
    public Map<String,List<EquityInfoRegist001Dto>> getSubmitList(Map<String,Object> param){
        List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
        EquityInfoRegist001Dto equityInfoRegist001Dto = null;
        //报送状态表主键
        String submitKey = (String)param.get("submitKeys");
        String keyList = (String)param.get("keyList");
        List<EquityInfoRegist001Dto> equityInfoRegistList = new ArrayList<EquityInfoRegist001Dto>();
        if(StringUtil.isNotEmpty(keyList)){
            List<String> keysList = StringUtil.splitToList(GlobalConstant.COMMA, keyList);
            for (String string : keysList) {
                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
                equityInfoRegist001Dto.setSumitSeq(string);
                equiytRegistList.add(equityInfoRegist001Dto);
			}
            //equityInfoRegistList = equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList);
            logger.info("queryExportListByKeys处理开始：" + DateUtil.getCurrentDateTime());
            this.queryExportListByKeys(equiytRegistList, equityInfoRegistList);
            logger.info("queryExportListByKeys处理结束：" + DateUtil.getCurrentDateTime());
//            EquityInfoRegistCommonUtil.format(equityInfoRegistList," ");
        	EquityInfoRegistCommonUtil.formatByType(equityInfoRegistList,true);
        }
        else if(StringUtil.isNotEmpty(submitKey)){
        	List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, submitKey);
            for (String string : keys) {
                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
                String[] key=StringUtil.split(string, ReportGlobalConstant.VERTICAL_LINE);
                equityInfoRegist001Dto.setBankCode(key[0]);
                equityInfoRegist001Dto.setIndentifyMark(key[1]);
                equityInfoRegist001Dto.setSubmitStatus(key[2]);
                equiytRegistList.add(equityInfoRegist001Dto);
                //报送状态表插入组装
//                chbSubmitStatus = new ChbSubmitStatus();
//                chbSubmitStatus.setSeqno(key[3]);
//                chbSubmitStatus.setSubmitFeedbackStatus("01");
//                listStatus.add(chbSubmitStatus);
            }
            //equityInfoRegistList = equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList);
            logger.info("queryExportListByKeys处理开始：" + DateUtil.getCurrentDateTime());
            this.queryExportListByKeys(equiytRegistList, equityInfoRegistList);
            logger.info("queryExportListByKeys处理结束：" + DateUtil.getCurrentDateTime());
//            EquityInfoRegistCommonUtil.format(equityInfoRegistList," ");
        	EquityInfoRegistCommonUtil.formatByType(equityInfoRegistList,true);
        }
        else {
            equityInfoRegistList = this.queryExportRegistList(param);
        }
//        else{
//            List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, submitKey);
//            for (String string : keys) {
//                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
//                String[] key=StringUtil.split(string, "|");
//                equityInfoRegist001Dto.setBankCode(key[0]);
//                equityInfoRegist001Dto.setIndentifyMark(key[1]);
//                equityInfoRegist001Dto.setSubmitStatus(key[2]);
//                equiytRegistList.add(equityInfoRegist001Dto);
//                //报送状态表插入组装
////                chbSubmitStatus = new ChbSubmitStatus();
////                chbSubmitStatus.setSeqno(key[3]);
////                chbSubmitStatus.setSubmitFeedbackStatus("01");
////                listStatus.add(chbSubmitStatus);
//            }
//            //equityInfoRegistList = equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList);
//            logger.info("queryExportListByKeys处理开始：" + DateUtil.getCurrentDateTime());
//            this.queryExportListByKeys(equiytRegistList, equityInfoRegistList);
//            logger.info("queryExportListByKeys处理结束：" + DateUtil.getCurrentDateTime());
////            EquityInfoRegistCommonUtil.format(equityInfoRegistList," ");
//        	EquityInfoRegistCommonUtil.formatByType(equityInfoRegistList,true);
//        }
//        ChbSubmitStatus chbSubmitStatus = null;
//        List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
       //将数据字典的值转换成如01 男这种格式
        Map<String,List<EquityInfoRegist001Dto>> map = new HashMap<String,List<EquityInfoRegist001Dto>>();
        List<EquityInfoRegist001Dto> equList = null;
        for (EquityInfoRegist001Dto equityInfoRegist001Dto2 : equityInfoRegistList) {
            if(StringUtil.isEmptyList(map.get(equityInfoRegist001Dto2.getBankCode()))){
                equList = new ArrayList<EquityInfoRegist001Dto>();
            }
            else{
                equList = map.get(equityInfoRegist001Dto2.getBankCode());
            }
            equList.add(equityInfoRegist001Dto2);
            map.put(equityInfoRegist001Dto2.getBankCode(), equList);
        }
        return map;
    }





    /**
     * 获取报送列表
     * @param equiytRegistList 条件
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void doUpdateSubmtitStatus(List<EquityInfoRegist001Dto> equiytRegistList,List<EquityInfoRegist001Dto>  unSubmitSeqList, boolean isSendAllData){
        //检查报送的数据中是否存在已报送数据
//        List<EquityInfoRegist001Dto> list = equityInfoRegist001Mapper.queryRegistSubmitListByKeys(equiytRegistList);
        List<EquityInfoRegist001Dto> unSub = new ArrayList<EquityInfoRegist001Dto>();
        for (EquityInfoRegist001Dto equityInfoRegist001Dto : equiytRegistList) {
			if(DictCodeListExt2.报送状态_未报送.getValue().equals(equityInfoRegist001Dto.getSubmitStatus())){
				unSub.add(equityInfoRegist001Dto);
			}
		}
//    	List<EquityInfoRegist001Dto> list = this.queryRegistSubmitListByKeysBatch(unSub);
//        List<EquityInfoRegist001Dto> list = this.queryAlreadyRegistSubmitList(unSub);
//    	logger.info("queryAlreadyRegistSubmitList处理结束：" + DateUtil.getCurrentDateTime());
//        if(CollectionUtils.isNotEmpty(list)){
//            //讲第一次报送的数据数据履历表
//           // equityInfoRegist001Mapper.insertEquityRegistHis(list);
//            logger.info("insertEquityRegistHisBatch处理开始：" + DateUtil.getCurrentDateTime());
//        	this.insertEquityRegistHisBatch(list);
//        	logger.info("insertEquityRegistHisBatch处理结束：" + DateUtil.getCurrentDateTime());
//            //删除已报送数据
//            //equityInfoRegist001Mapper.deleteSubmitRegist(list);
//        	logger.info("deleteSubmitRegistBatch处理开始：" + DateUtil.getCurrentDateTime());
//        	this.deleteSubmitRegistBatch(list);
//        	logger.info("deleteSubmitRegistBatch处理结束：" + DateUtil.getCurrentDateTime());
//        }
        //批量更新报送状态
//        equityInfoRegist001Mapper.updateInterfSubmit("02",equiytRegistList);
        logger.info("updateInterfSubmitBatch处理开始：" + DateUtil.getCurrentDateTime());
        this.updateInterfSubmitBatch(DictCodeListExt2.报送状态_接口报送.getValue(), unSub, isSendAllData);
        logger.info("updateInterfSubmitBatch处理结束：" + DateUtil.getCurrentDateTime());
//        equityInfoRegist001Mapper.insertSubmitStatus(listStatus);
        logger.info("updateInterfSubmitSeq处理开始：" + DateUtil.getCurrentDateTime());
        this.updateInterfSubmitSeq(unSubmitSeqList);
        logger.info("updateInterfSubmitSeq处理结束：" + DateUtil.getCurrentDateTime());
    }

    /**
     * 处理已经报送的数据（将已报送数据移至履历表，并删除登记表数据）
     *
     * @param equiytRegistList 数据列表
     * @see
     * @since 1.0
     */
    public void handleAlreadySubmitData(List<EquityInfoRegist001Dto> equiytRegistList) {
        logger.info("queryAlreadyRegistSubmitList处理开始：" + DateUtil.getCurrentDateTime());
        List<EquityInfoRegist001Dto> unSub = new ArrayList<EquityInfoRegist001Dto>();
        for (EquityInfoRegist001Dto equityInfoRegist001Dto : equiytRegistList) {
            if(DictCodeListExt2.报送状态_未报送.getValue().equals(equityInfoRegist001Dto.getSubmitStatus())){
                unSub.add(equityInfoRegist001Dto);
            }
        }
        List<EquityInfoRegist001Dto> list = this.queryAlreadyRegistSubmitList(unSub);
        logger.info("queryAlreadyRegistSubmitList处理结束：" + DateUtil.getCurrentDateTime());
        if(CollectionUtils.isNotEmpty(list)){
            //讲第一次报送的数据数据履历表
            logger.info("insertEquityRegistHisBatch处理开始：" + DateUtil.getCurrentDateTime());
            this.insertEquityRegistHisBatch(list);
            logger.info("insertEquityRegistHisBatch处理结束：" + DateUtil.getCurrentDateTime());
            //删除已报送数据
            logger.info("deleteSubmitRegistBatch处理开始：" + DateUtil.getCurrentDateTime());
            this.deleteSubmitRegistBatch(list);
            logger.info("deleteSubmitRegistBatch处理结束：" + DateUtil.getCurrentDateTime());
        }
    }

    /**
     * 分批次执行deleteSubmitRegist
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private void deleteSubmitRegistBatch(List<EquityInfoRegist001Dto> equiytRegistList) {
        if (StringUtil.isEmptyList(equiytRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
        	equityInfoRegist001Mapper.deleteSubmitRegist(equiytRegistList);;
            return;
        }

        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	equityInfoRegist001Mapper.deleteSubmitRegist(newDataList.subList(0, maxDataCountOneTime));;
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	equityInfoRegist001Mapper.deleteSubmitRegist(newDataList);;
        }
    }
    /**
     * 分批次执行insertEquityRegistHis
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private void insertEquityRegistHisBatch(List<EquityInfoRegist001Dto> equiytRegistList) {
        if (StringUtil.isEmptyList(equiytRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportInsertLongCount;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
        	equityInfoRegist001Mapper.insertEquityRegistHis(equiytRegistList);;
            return;
        }

        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	equityInfoRegist001Mapper.insertEquityRegistHis(newDataList.subList(0, maxDataCountOneTime));;
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	equityInfoRegist001Mapper.insertEquityRegistHis(newDataList);;
        }
    }
    /**
     * 分批次执行queryRegistSubmitListByKeys
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private List<EquityInfoRegist001Dto> queryRegistSubmitListByKeysBatch(List<EquityInfoRegist001Dto> equiytRegistList) {
        List<EquityInfoRegist001Dto> equiytRegistResultList = new ArrayList<EquityInfoRegist001Dto>();
    	if (StringUtil.isEmptyList(equiytRegistList)) {
            return equiytRegistResultList;
        }
        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
        	equiytRegistResultList.addAll(equityInfoRegist001Mapper.queryRegistSubmitListByKeys(equiytRegistList));
            return equiytRegistResultList;
        }

        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("queryRegistSubmitListByKeys单次查询处理开始：" + DateUtil.getCurrentDateTime());
        	equiytRegistResultList.addAll(equityInfoRegist001Mapper.queryRegistSubmitListByKeys(newDataList.subList(0, maxDataCountOneTime)));
        	logger.info("queryRegistSubmitListByKeys单次查询处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	equiytRegistResultList.addAll(equityInfoRegist001Mapper.queryRegistSubmitListByKeys(newDataList));
        }
        return equiytRegistResultList;
    }

    /**
     * 查询已报送数据
     *
     * @param equiytRegistList equiytRegistList
     * @return 已报送数据
     * @see
     * @since 1.0
     */
    private List<EquityInfoRegist001Dto> queryAlreadyRegistSubmitList(List<EquityInfoRegist001Dto> equiytRegistList) {
        List<EquityInfoRegist001Dto> equiytRegistResultList = new ArrayList<EquityInfoRegist001Dto>();

        if (StringUtil.isEmptyList(equiytRegistList)) {
            return equiytRegistResultList;
        }

        if (equiytRegistList.size() <= 100000) {
            return this.queryRegistSubmitListByKeysBatch(equiytRegistList);
        }

        Map<String, String> dataKeyMap = equiytRegistList.stream().collect(
            JoyinCollectors.toMap(
                x -> x.getBankCode() + GlobalConstant.UNDER_LINE + x.getIndentifyMark(),
                x -> x.getBankCode() + GlobalConstant.UNDER_LINE + x.getIndentifyMark()));

        logger.info("queryAllRegistSubmitList处理开始：" + DateUtil.getCurrentDateTime());
        List<EquityInfoRegist001Dto> allRegistDataList = equityInfoRegist001Mapper.queryAllRegistSubmitList();
        logger.info("queryAllRegistSubmitList处理结束：" + DateUtil.getCurrentDateTime());

        List<EquityInfoRegist001Dto> resultList = new ArrayList<EquityInfoRegist001Dto>();
        for (EquityInfoRegist001Dto tempDataDto : allRegistDataList) {
            String dataKey = tempDataDto.getBankCode() + GlobalConstant.UNDER_LINE + tempDataDto.getIndentifyMark();
            if (dataKeyMap.containsKey(dataKey)) {
                resultList.add(tempDataDto);
            }
        }

        return resultList;
    }

    /**
     * 分批次执行updateInterfSubmitBatch
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private void updateInterfSubmitBatch(String sendStatus,List<EquityInfoRegist001Dto> equiytRegistList, boolean isSendAllData) {
        if (StringUtil.isEmptyList(equiytRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            logger.info("updateInterfSubmit单次处理开始：" + DateUtil.getCurrentDateTime());
        	equityInfoRegist001Mapper.updateInterfSubmit(sendStatus,equiytRegistList);
        	logger.info("updateInterfSubmit单次处理结束：" + DateUtil.getCurrentDateTime());
            return;
        }

        // 对于批处理报送全量的数据，直接更新所有检查通过的未报送数据，状态修改为接口报送
        if (isSendAllData) {
            logger.info("updateAllInterfSubmit处理开始："
                        + DateUtil.getCurrentDateTime());
            equityInfoRegist001Mapper.updateAllInterfSubmit(sendStatus);
            logger.info("updateAllInterfSubmit处理结束："
                        + DateUtil.getCurrentDateTime());

            return;
        }

        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("updateInterfSubmit单次处理开始：" + DateUtil.getCurrentDateTime());
        	equityInfoRegist001Mapper.updateInterfSubmit(sendStatus,newDataList.subList(0, maxDataCountOneTime));;
        	logger.info("updateInterfSubmit单次处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            logger.info("updateInterfSubmit单次处理开始：" + DateUtil.getCurrentDateTime());
        	equityInfoRegist001Mapper.updateInterfSubmit(sendStatus,newDataList);
        	logger.info("updateInterfSubmit单次处理结束：" + DateUtil.getCurrentDateTime());
        }

//        List<String> sqlList = this.generateUpdateInterfSubmitSql(sendStatus, equiytRegistList);
//        dbCommonService.exeSqlBatch("chb_equ_sub", sqlList);
    }

//    /**
//     *
//     * 生成批量执行sql
//     * @param sendStatus sendStatus
//     * @param equiytRegistList equiytRegistList
//     * @return 批量执行sql
//     * @see
//     * @since 1.0
//     */
//    private List<String> generateUpdateInterfSubmitSql(String sendStatus, List<EquityInfoRegist001Dto> equiytRegistList) {
//        List<String> sqlList = new ArrayList<>();
//
//        for (EquityInfoRegist001Dto dataDto : equiytRegistList) {
//            StringBuffer sqlSb = new StringBuffer();
//            sqlSb.append(" update chb_equ_sub sub set ");
//            sqlSb.append("   sub.SUBMIT_STATUS = '").append(sendStatus).append("',");
//            sqlSb.append("   sub.submit_time = sysdate, ");
//            sqlSb.append(" sub.UPDATE_TIME = sysdate ");
//            sqlSb.append(" where ");
//            sqlSb.append("   sub.REGIST_BANK_CODE = '").append(dataDto.getBankCode()).append("' ");
//            sqlSb.append(" and sub.INDENTIFY_MARK = '").append(dataDto.getIndentifyMark()).append("' ");
//            sqlSb.append(" and sub.SUBMIT_STATUS = '01'");
//            sqlList.add(sqlSb.toString());
//        }
//
//        return sqlList;
//    }

    /**
     * 分批次执行updateInterfSubmitSeq
     *
     * @param equiytRegistList 数据
     * @see
     * @since 1.0
     */
    private void updateInterfSubmitSeq(List<EquityInfoRegist001Dto> equiytRegistList) {
        if (StringUtil.isEmptyList(equiytRegistList)) {
            return;
        }

        int maxDataCountOneTime = reportInsertCount;
		boolean isDB2DataBase = CommonUtil.isDB2DataBase();
        if (isDB2DataBase) {
            maxDataCountOneTime = 1;
        }
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
            logger.info("updateInterfSubmitSeq单次处理开始：" + DateUtil.getCurrentDateTime());
        	equityInfoRegist001Mapper.updateInterfSubmitSeq(equiytRegistList);
        	logger.info("updateInterfSubmitSeq单次处理结束：" + DateUtil.getCurrentDateTime());
            return;
        }

        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("updateInterfSubmitSeq单次处理开始：" + DateUtil.getCurrentDateTime());
        	equityInfoRegist001Mapper.updateInterfSubmitSeq(newDataList.subList(0, maxDataCountOneTime));;
        	logger.info("updateInterfSubmitSeq单次处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
            logger.info("updateInterfSubmitSeq单次处理开始：" + DateUtil.getCurrentDateTime());
        	equityInfoRegist001Mapper.updateInterfSubmitSeq(newDataList);
        	logger.info("updateInterfSubmitSeq单次处理结束：" + DateUtil.getCurrentDateTime());
        }
    }
    /**
     * 数据检查
     * @param checkKeys 检查数据的关键字
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> checkData(String checkKeys){
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, checkKeys);
        keys.remove(StringUtil.EMPTY);
        List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
        List<EquityInfoRegist001Dto> equityInfoRegistList = new ArrayList<EquityInfoRegist001Dto>();
        EquityInfoRegist001Dto equityInfoRegist001Dto = null;
        if(CollectionUtils.isNotEmpty(keys)){
            for (String string : keys) {
                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
                String[] key=StringUtil.split(string, "|");
                equityInfoRegist001Dto.setBankCode(key[0]);
                equityInfoRegist001Dto.setIndentifyMark(key[1]);
                equityInfoRegist001Dto.setSubmitStatus(key[2]);
                equiytRegistList.add(equityInfoRegist001Dto);}
        }
        //List<EquityInfoRegist001Dto> equityInfoRegistList = equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList);
        this.queryExportListByKeys(equiytRegistList,equityInfoRegistList);
        List<ExcelValidDto> checkResult = new ArrayList<>();
        if (CollectionUtils.isEmpty(equityInfoRegistList)) {
            checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据",null));
            return checkResult;
        }
        //将数据字典的值转换成如01 男这种格式
        //EquityInfoRegistCommonUtil.format(equityInfoRegistList," ");
    	EquityInfoRegistCommonUtil.formatByType(equityInfoRegistList,true);
        checkResult = this.dealReportCheckData(equityInfoRegistList, checkResult, false);
        //三要素重复检查
        List<EquityInfoRegist001Dto> list2=equityInfoDistinctService.checkBeforeSubmit(null,"01");
        if(list2!=null){
        for(EquityInfoRegist001Dto single:list2){
        	for(Iterator<ExcelValidDto> it=checkResult.iterator();it.hasNext();){
        		ExcelValidDto excelValidDto=it.next();
        		if(excelValidDto.getValidKey().equals(single.getIndentifyMark())){
        			it.remove();
        		}
        	}
        }
        for(EquityInfoRegist001Dto single:list2){
        	ExcelValidDto exc=new ExcelValidDto();
        	exc.setValidKey(single.getIndentifyMark());
        	if(!single.getRemark().equals("检查通过！")){
        		exc.setCheckResult("02");
        		exc.setErrorInfo(single.getRemark());
        	}else{
        		exc.setCheckResult("01");
        	}
        	checkResult.add(exc);
        }
        }
        return checkResult;
    }
    /**
     * 报送前检查
     * @param checkKeys 检查数据的关键字
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> sendBeforeCheck(String checkKeys){
        List<ExcelValidDto>  checkResult = new ArrayList<ExcelValidDto>();
        if(StringUtil.isEmpty(checkKeys)){
            checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0),"无报送数据"));
        }
        else{
            List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, checkKeys);
            List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
            EquityInfoRegist001Dto equityInfoRegist001Dto = null;
            for (String string : keys) {
                if(StringUtil.isEmpty(string)){
                    continue;
                }
                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
                String[] key=StringUtil.splitByWholeSeparator(string, "|");
                equityInfoRegist001Dto.setBankCode(key[0]);
                equityInfoRegist001Dto.setIndentifyMark(key[1]);
                equityInfoRegist001Dto.setSubmitStatus(key[2]);
                equityInfoRegist001Dto.setSumitSeq(key[3]);
                equiytRegistList.add(equityInfoRegist001Dto);
            }
            //修改为分批查询
           // int maxCount = 500;
            List<EquityInfoRegist001Dto> equityInfoRegistList = new ArrayList<EquityInfoRegist001Dto>();
            this.queryExportListByKeys(equiytRegistList,equityInfoRegistList);
//            if(CollectionUtils.isNotEmpty(equiytRegistList)){
//                //查询分批处理（每次最多处理maxCount条）
//                  if(equiytRegistList.size()<maxCount){
//                	  equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
//                  }
//                  else{
//                      int part = equiytRegistList.size()/maxCount;
//                      for(int j=0; j<part; j++){
//                    	  equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
//                          equiytRegistList.subList(0, maxCount).clear();
//                      }
//                      if(CollectionUtils.isNotEmpty(equiytRegistList)){
//                    	  equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
//                      }
//                  }
//              }
            //将数据字典的值转换成如01 男这种格式
//            EquityInfoRegistCommonUtil.format(equityInfoRegistList," ");
        	EquityInfoRegistCommonUtil.formatByType(equityInfoRegistList,true);
            checkResult = this.dealReportCheckData(equityInfoRegistList, checkResult, false);
         }
            checkResult = checkResult.parallelStream().filter(dto -> {
                return StringUtil.equals(dto.getCheckResult(), DictCodeListExt2.检查结果_检查不通过.getCodeName());
            }).collect(Collectors.toList());
        return checkResult;
    }

    /**
     *
     * 处理报送前检查和数据检查的数据
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> dealReportCheckData(List<EquityInfoRegist001Dto> equityInfoRegistList,List<ExcelValidDto> checkResult
    		,boolean flag){
        int maxCount =reportInsertCount;
        boolean isDB2DataBase = CommonUtil.isDB2DataBase();

    	//存储已经批量进行过数据检查的数据
        List<String> isCheckedDataList = new ArrayList<>();
        //存储没有进行数据检查的数据
        List<EquityInfoRegist001Dto> isNeedCheckDataList = new ArrayList<>();
        logger.info("检查报送流水号是否校验开始start");

        equityInfoRegistList.forEach(dto -> {
        	if(!StringUtil.equals(ReportGlobalConstant.IS_UN_SUBMIT_CHECK,dto.getCheckSeqno())
        			&& StringUtil.isNotEmpty(dto.getCheckSeqno())){
                isCheckedDataList.add(dto.getCheckSeqno());
            }
            else{
                isNeedCheckDataList.add(dto);
            }
        });
        logger.info("检查报送流水号是否校验结束end");
//        for (EquityInfoRegist001Dto dto : equityInfoRegistList) {
//            if(!StringUtil.equals("0",dto.getCheckSeqno()) && StringUtil.isNotEmpty(dto.getCheckSeqno())){
//                isCheckedDataList.add(dto.getCheckSeqno());
//            }
//            else{
//                isNeedCheckDataList.add(dto);
//            }
//        }
        logger.info("已检查数据查询start");
        if(CollectionUtils.isNotEmpty(isCheckedDataList)){
          //查询分批处理（每次最多处理maxCount条）
            if(isCheckedDataList.size()<maxCount){
                checkResult = checkReport001Mapper.selectByCheckSeqno(isCheckedDataList);
            }
            else{
                int part = isCheckedDataList.size()/maxCount;
                for(int j=0; j<part; j++){
                    checkResult.addAll(checkReport001Mapper.selectByCheckSeqno(isCheckedDataList.subList(0, maxCount)));
                    isCheckedDataList.subList(0, maxCount).clear();
                }
                if(CollectionUtils.isNotEmpty(isCheckedDataList)){
                    checkResult.addAll(checkReport001Mapper.selectByCheckSeqno(isCheckedDataList));
                }
            }
        }
        logger.info("已检查送数据查询end");
        if(isDB2DataBase){
        	maxCount = 1;
        }
        if(CollectionUtils.isNotEmpty(isNeedCheckDataList)){
        	logger.info("数据check开始");
            List<ExcelValidDto> newCheckedInfo = equityInfoValid.checkDataEquity(equityInfoRegistList,"1",null,null);
            logger.info("数据check结束");
            checkResult.addAll(newCheckedInfo);
            //组装结果集
            List<CheckReport001Dto> resultList = new ArrayList<>();
            //投资者身份信息
            List<EquityInfoRegist001Dto> equityInfoRegistUpdateList = new ArrayList<>();
            logger.info("初次检查赋值start");
            newCheckedInfo.forEach(item->{
            	if(flag&&!IS_CHECK_SUCESS.equals(SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()))){
                    CheckReport001Dto chbReportCheck = new CheckReport001Dto();
                    BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
//                  chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
                    chbReportCheck.setCheckSeqno(IDGenerator.generate());
                    chbReportCheck.setRepCode(DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
                    chbReportCheck.setRepName(DictCodeListExt2.报表名称_投资者身份信息登记.getCodeName());
                    chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()));
                    resultList.add(chbReportCheck);

                    //更新检查序列号对应的数据
                    EquityInfoRegist001Dto dto = new EquityInfoRegist001Dto();
                    dto.setIndentifyMark(item.getValidKey());
                    dto.setCheckSeqno(chbReportCheck.getCheckSeqno());
                    equityInfoRegistUpdateList.add(dto);
            	}
            	else if(!flag){
                    CheckReport001Dto chbReportCheck = new CheckReport001Dto();
                    BeanUtil.copyPropertiesIgnoreNull(chbReportCheck, item);
//                  chbReportCheck.setCheckSeqno(CommonUtil.getSequenceIndexData(DataIndexSequenceType.检查序列号));
                    chbReportCheck.setCheckSeqno(IDGenerator.generate());
                    chbReportCheck.setRepCode(DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
                    chbReportCheck.setRepName(DictCodeListExt2.报表名称_投资者身份信息登记.getCodeName());
                    chbReportCheck.setCheckResult(SysDataUtil.getCodeFromDataDictByName(DictCode.检查结果, item.getCheckResult()));
                    resultList.add(chbReportCheck);

                    //更新检查序列号对应的数据
                    EquityInfoRegist001Dto dto = new EquityInfoRegist001Dto();
                    dto.setIndentifyMark(item.getValidKey());
                    dto.setCheckSeqno(chbReportCheck.getCheckSeqno());
                    equityInfoRegistUpdateList.add(dto);
            	}
            });
            logger.info("初次检查赋值end");
            //由于resultList与equityInfoRegistUpdateList的size相同，故size只判断一个
            if(CollectionUtils.isNotEmpty(resultList)){
            	 //查询分批处理（每次最多处理maxCount条）
                if(resultList.size()<maxCount){
//                	//将新生成的检查信息插入报送前检查表
//                    checkReport001Mapper.batchInsert(resultList);
//                    List<String> indentifyMarkList = new ArrayList<String>();
//                    //组装检查通过的识别标识结果集
//                    newCheckedInfo.parallelStream().forEach(item->{
//                    	if(IS_CHECK_SUCESS.equals(item.getCheckResult())){
//                    		indentifyMarkList.add(item.getValidKey());
//                    	}
//                    });
//                    if(CollectionUtils.isNotEmpty(indentifyMarkList)){
//                    	//同一条数据，如果报送检查通过，则将之前检查不通过的删除
//                    	checkReport001Mapper.delReportUnCheckDatas(indentifyMarkList);
//                    }
//                  //批量更新投资者身份信息检查序列号
//                    checkReport001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
                	this.doCheckInfo(resultList,equityInfoRegistUpdateList);
                }
                else{
                    int part = resultList.size()/maxCount;
                    for(int j=0; j<part; j++){
                    	//将新生成的检查信息插入报送前检查表
//                        checkReport001Mapper.batchInsert(resultList.subList(0, maxCount));
//                        List<String> indentifyMarkList = new ArrayList<String>();
//                        //组装检查通过的识别标识结果集
//                        for(int i=0;i<resultList.subList(0, maxCount).size();i++){
//                        	if("01".equals(resultList.subList(0, maxCount).get(i).getCheckResult())){
//                        		indentifyMarkList.add(resultList.subList(0, maxCount).get(i).getValidKey());
//                        	}
//                        }
//                        if(CollectionUtils.isNotEmpty(indentifyMarkList)){
//                        	//同一条数据，如果报送检查通过，则将之前检查不通过的删除
//                        	checkReport001Mapper.delReportUnCheckDatas(indentifyMarkList);
//                        }
//                      //批量更新投资者身份信息检查序列号
//                        checkReport001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList.subList(0, maxCount));
                        this.doCheckInfo(resultList.subList(0, maxCount),equityInfoRegistUpdateList.subList(0, maxCount));
                    	resultList.subList(0, maxCount).clear();
                        equityInfoRegistUpdateList.subList(0, maxCount).clear();
                    }
                    if(CollectionUtils.isNotEmpty(resultList)){
//                    	//将新生成的检查信息插入报送前检查表
//                        checkReport001Mapper.batchInsert(resultList);
//                        List<String> indentifyMarkList = new ArrayList<String>();
//                        //组装检查通过的识别标识结果集
//                        for(int i=0;i<resultList.size();i++){
//                        	if("01".equals(resultList.get(i).getCheckResult())){
//                        		indentifyMarkList.add(resultList.get(i).getValidKey());
//                        	}
//                        }
//                        if(CollectionUtils.isNotEmpty(indentifyMarkList)){
//                        	//同一条数据，如果报送检查通过，则将之前检查不通过的删除
//                        	checkReport001Mapper.delReportUnCheckDatas(indentifyMarkList);
//                        }
//                      //批量更新投资者身份信息检查序列号
//                        checkReport001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
                    	this.doCheckInfo(resultList,equityInfoRegistUpdateList);
                    }
                }
            }
//          //将新生成的检查信息插入报送前检查表
//            checkReport001Mapper.batchInsert(resultList);
//          //批量更新投资者身份信息检查序列号
//            checkReport001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
        }
        return checkResult;
    }

    /**
     * excel数据转为java对象
     * <p> 将excel导入的数据，按行转为EquityInfoRegist001Dto对象，同时验证各字段值</p>
     *
     * @param dataList excel导入的数据
     * @param entities 转化后的列表
     * @return 验证信息
     */
    private List<ExcelValidDto> convertToEntity(List<List<String>> dataList,
    		List<EquityInfoRegist001Dto> entities,String deptCode,String orgCode) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        
        List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, dataList.size());
        
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            if (null == list || list.size() !=23) {
                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
                    this.getMessage("REPORT_COMMON_FILE_E_0021", "投资者信息登记导入")));
                continue;
            }
            EquityInfoRegist001Dto entity = new EquityInfoRegist001Dto();
            entity.setBankCode(list.get(0));
            if(!EquityInfoCheckUtil.isEmpty(list.get(6))){
                entity.setType(EquityInfoCheckUtil.subDictcode(list.get(6), " "));
            }
            entity.setIndentifyMark(list.get(8));
            entity.setOrgIndentifyMark(list.get(7));
            if(!EquityInfoCheckUtil.isEmpty(list.get(1))){
                entity.setIsBelongBank(EquityInfoCheckUtil.subDictcode(list.get(1), " "));
            }
            entity.setSubmitStatus("03");
            entity.setTheBankName(list.get(2));
            entity.setTheBankCode(list.get(3));
            if(!EquityInfoCheckUtil.isEmpty(list.get(4))){
                entity.setHolderMark(EquityInfoCheckUtil.subDictcode(list.get(4)," "));
            }
            //待数据字典添加之后改回来
            if(!EquityInfoCheckUtil.isEmpty(list.get(5))){
                entity.setHolderRegion(EquityInfoCheckUtil.subDictcode(list.get(5)," "));
            }
            if(!EquityInfoCheckUtil.isEmpty(list.get(9))){
                entity.setHolderType(EquityInfoCheckUtil.subDictcode(list.get(9)," "));
            }
            if(!EquityInfoCheckUtil.isEmpty(list.get(10))){
                entity.setPersonalIdentifyType(EquityInfoCheckUtil.subDictcode(list.get(10)," "));
            }
            if(!EquityInfoCheckUtil.isEmpty(list.get(11))){
                entity.setInstitutionIdentifyType(EquityInfoCheckUtil.subDictcode(list.get(11)," "));
            }
            entity.setOtherIdentifyName(list.get(12));
            entity.setIdentifyNumber(list.get(13));
            if(!EquityInfoCheckUtil.isEmpty(list.get(14))){
                entity.setSpvCapAccBank(EquityInfoCheckUtil.subDictcode(list.get(14)," "));
            }
            entity.setOtherCapAccBank(list.get(15));
            //modify by wangmin 因中债删除了spv登记编码字段。故先将spv这一列取值删除，在修改list对应编码 start
            //entity.setSpvRegistCode(list.get(16));
            //modify by wangmin 因中债删除了spv登记编码字段。故先将spv这一列取值删除，在修改list对应编码  end
            entity.setHolderName(list.get(16));
            if(!EquityInfoCheckUtil.isEmpty(list.get(17))){
                entity.setHolderSex(EquityInfoCheckUtil.subDictcode(list.get(17)," "));
            }
            if(!EquityInfoCheckUtil.isEmpty(list.get(18))){
                entity.setHolderRiskPrefer(EquityInfoCheckUtil.subDictcode(list.get(18)," "));
            }
            entity.setSumitSeq(//CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理)
            		CommonUtil.getSequenceStr(sequenceList, i)
            		);
            entity.setHolderCellphone(list.get(19));
            entity.setHolderTelephone(list.get(20));
            entity.setHolderEmail(list.get(21));
            entity.setRemark(list.get(22));
            entity.setCreateUser("Batch");
            entity.setUpdateUser("Batch");
            entity.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
            entity.setOrgCode(orgCode);
            entity.setDeptCode(deptCode);
            entities.add(entity);
        }
        return checkResult;
    }
    /**
     * 通过主键查询要导出的登记数据
     * @param expKeys 登记表主键
     * @return 权益人登记信息List
     * @see
     * @since 1.0
     */
    public List<EquityInfoRegist001Dto> queryExportSelectRegistList(String expKeys){
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, expKeys);
        List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
        EquityInfoRegist001Dto equityInfoRegist001Dto = null;
        for (String string : keys) {
            equityInfoRegist001Dto = new EquityInfoRegist001Dto();
            String[] key=StringUtil.split(string, "|");
            equityInfoRegist001Dto.setBankCode(key[0]);
            equityInfoRegist001Dto.setIndentifyMark(key[1]);
            equityInfoRegist001Dto.setSubmitStatus(key[2]);
            equiytRegistList.add(equityInfoRegist001Dto);
        }
        //List<EquityInfoRegist001Dto> pageList = equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList);
        List<EquityInfoRegist001Dto> pageList = new ArrayList<EquityInfoRegist001Dto>();
        int maxDataCountOneTime = 500;
        int dataCount = equiytRegistList.size();
        if (dataCount <= maxDataCountOneTime) {
        	pageList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
//        	EquityInfoRegistCommonUtil.format(pageList," ");
            return pageList;
        }

        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(equiytRegistList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	pageList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	pageList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(newDataList));
        }
//        EquityInfoRegistCommonUtil.format(pageList," ");
        return pageList;
    }
    /**
     * 根据查询条件获取keyList
     * @param param 查询条件
     * @return 字符串结果
     * @see
     * @since 1.0
     */
    public String getKeyList(Map<String, Object> param) {
        StringBuffer sb=new StringBuffer();
        param.put("submitStatus", DictCodeListExt2.报送状态_未报送.getValue());
        param.put("deptCode", CommonUtil.getDeptCode());
        List<EquityInfoRegist001Dto> list= this.queryExportRegistList(param);
        if(CollectionUtils.isNotEmpty(list)){
            for(EquityInfoRegist001Dto dto : list){
                sb.append(dto.getBankCode()+"|"+dto.getIndentifyMark()+"|"+dto.getSubmitStatus()+"|"
                    +dto.getSumitSeq()+GlobalConstant.COMMA);
            }
        }
        return sb.toString();
    }
    /**
     * 根据查询条件获取所有未报送数据
     * @param param 查询条件
     * @return list 未报送数据
     * @see
     * @since 1.0
     */
    public List<EquityInfoRegist001Dto> getAllUnSubmit(Map<String, Object> param) {
        param.put("submitStatus", DictCodeListExt2.报送状态_未报送.getValue());
        param.put("deptCode", CommonUtil.getDeptCode());
        List<EquityInfoRegist001Dto> list= this.queryExportRegistList(param);
        return list;
    }
    /**
     * 报送前检查
     * @param list 报送前检查list
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> sendAllDataBeforeCheck(List<EquityInfoRegist001Dto> list){
        List<ExcelValidDto>  checkResult = new ArrayList<ExcelValidDto>();
        if(CollectionUtils.isEmpty(list)){
            checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0),"无报送数据"));
        }
        else{
//            List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, checkKeys);
//            List<EquityInfoRegist001Dto> equiytRegistList = new ArrayList<EquityInfoRegist001Dto>();
//            EquityInfoRegist001Dto equityInfoRegist001Dto = null;
//            for (String string : keys) {
//                if(StringUtil.isEmpty(string)){
//                    continue;
//                }
//                equityInfoRegist001Dto = new EquityInfoRegist001Dto();
//                String[] key=StringUtil.splitByWholeSeparator(string, "|");
//                equityInfoRegist001Dto.setBankCode(key[0]);
//                equityInfoRegist001Dto.setIndentifyMark(key[1]);
//                equityInfoRegist001Dto.setSubmitStatus(key[2]);
//                equityInfoRegist001Dto.setSumitSeq(key[3]);
//                equiytRegistList.add(equityInfoRegist001Dto);
//            }
//            //修改为分批查询
//           // int maxCount = 500;
//            List<EquityInfoRegist001Dto> equityInfoRegistList = new ArrayList<EquityInfoRegist001Dto>();
//            this.queryExportListByKeys(equiytRegistList,equityInfoRegistList);
//            if(CollectionUtils.isNotEmpty(equiytRegistList)){
//                //查询分批处理（每次最多处理maxCount条）
//                  if(equiytRegistList.size()<maxCount){
//                	  equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
//                  }
//                  else{
//                      int part = equiytRegistList.size()/maxCount;
//                      for(int j=0; j<part; j++){
//                    	  equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
//                          equiytRegistList.subList(0, maxCount).clear();
//                      }
//                      if(CollectionUtils.isNotEmpty(equiytRegistList)){
//                    	  equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(equiytRegistList));
//                      }
//                  }
//              }
            //将数据字典的值转换成如01 男这种格式
//            EquityInfoRegistCommonUtil.format(equityInfoRegistList," ");
//        	EquityInfoRegistCommonUtil.formatByType(equityInfoRegistList,true);
            checkResult = this.dealReportCheckData(list, checkResult ,true);
         }
            checkResult = checkResult.parallelStream().filter(dto -> {
                return StringUtil.equals(dto.getCheckResult(), DictCodeListExt2.检查结果_检查不通过.getCodeName());
            }).collect(Collectors.toList());
        return checkResult;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCellValue(String beanName, Cell cell, String propName, Object value) {
        if(StringUtil.startsWith(propName, "updateTime")) {
            Date cellValue = (Date)value;
            cell.setCellValue(DateUtil.formatDateTime(cellValue));
        }
        if(StringUtil.startsWith(propName, "submitTime")) {
            Date cellValue = (Date)value;
            cell.setCellValue(DateUtil.formatDateTime(cellValue));
        }
        if(StringUtil.startsWith(propName, "chbSubmitDeadline")) {
            Date cellValue = (Date)value;
            cell.setCellValue(DateUtil.formatDateTime(cellValue));
        }
    }

    /**
     * 校验是否所有的数据都符合
     * <功能详细描述>
     * @param upKey 参数
     * @return error 列表
     * @see
     * @since 1.0
     */
    public List<ValidMessage> checkIsNotSubmit(String upKey) {
        List<ValidMessage> errorList =new ArrayList<ValidMessage>();
        List<String> keys = StringUtil.splitToList(GlobalConstant.COMMA, upKey);
        for (String key : keys) {
            List<String> list =StringUtil.splitToList("|", key);
            Map<String , Object>map =new HashMap<String , Object>();
            map.put("registBankCode", list.get(1));
            map.put("indentifyMark", list.get(2));
            map.put("noSubmit", DictCodeListExt2.报送状态_未报送.getValue());
            if(equityInfoRegist001Mapper.isNotSubmitP(map)!= 0){
                errorList.add(new ValidMessage("存在未报送的数据。"));
            }
        }
        return errorList;
    }
    /**
     * 分批次执行queryExportListByKeys
     *
     * @param dataList 条件几盒
     * @param equityInfoRegistList list结果集
     * @see
     * @since 1.0
     */
    private void queryExportListByKeys(List<EquityInfoRegist001Dto> dataList,List<EquityInfoRegist001Dto> equityInfoRegistList ) {
        if (StringUtil.isEmptyList(dataList)) {
            return;
        }
        int maxDataCountOneTime = reportSelectOrDeleteCount;
        int dataCount = dataList.size();
        if (dataCount <= maxDataCountOneTime) {
        	equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(dataList));
            return;
        }

        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(dataList);

        // 分批处理
        int part = dataCount / maxDataCountOneTime; // 分批数
        for (int i = 0; i < part; i++ ) {
        	equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(newDataList.subList(0, maxDataCountOneTime)));
            newDataList.subList(0, maxDataCountOneTime).clear();
        }
        // 最后剩下的数据
        if (CollectionUtils.isNotEmpty(newDataList)) {
        	equityInfoRegistList.addAll(equityInfoRegist001Mapper.queryExportListByKeys(newDataList));
        }
    }
    /**
     * 配置项中非空处理
     * @param Count
     * @param flag
     * @return
     */
//    public int parsToInt(String Count,String flag){
//   	 if(StringUtil.isNotEmpty(Count)&&Integer.parseInt(Count)<=0){
//   		 if("1".equals(flag)){
//   	         	return 20;
//   		 }
//   		 else if("1".equals(flag)){
//	         	return 500;
//   		 }
//   		 else {
//	         	return 100;
//   		 }
//        }
//        else{
//        	return Integer.parseInt(Count);
//        }
//   }
    private void doCheckInfo(List<CheckReport001Dto> resultList,List<EquityInfoRegist001Dto> equityInfoRegistUpdateList){
    	//将新生成的检查信息插入报送前检查表
    	logger.info("单次处理报送前检查表start===============");
        checkReport001Mapper.batchInsert(resultList);

        List<String> indentifyMarkList = new ArrayList<String>();
        //组装检查通过的识别标识结果集
        resultList.parallelStream().forEach(item->{
        	if(IS_CHECK_SUCESS.equals(item.getCheckResult())){
        		indentifyMarkList.add(item.getValidKey());
        	}
        });
        if(CollectionUtils.isNotEmpty(indentifyMarkList)){
        	//同一条数据，如果报送检查通过，则将之前检查不通过的删除
        	checkReport001Mapper.delReportUnCheckDatas(indentifyMarkList);
        }
      //批量更新投资者身份信息检查序列号
        equityInfoRegist001Mapper.updateEquityRegistCheckSeqno(equityInfoRegistUpdateList);
        logger.info("单次处理报送前检查表end===============");
    }
//    /**
//     * 针对发送失败、中债退回的数据，直接进行二次报送
//     *
//     * @param paramMap 画面参数
//     * @see
//     * @since 1.0
//     */
//    public void submitCbAgain(Map<String,Object> paramMap) {
//        // 待报送数据列表
//        List<EquityInfoRegist001Dto> equityInfoRegistList = new ArrayList<EquityInfoRegist001Dto>();
//
//        String keyListStr = (String)paramMap.get("keyList");
//        if (StringUtil.isEmpty(keyListStr)) {
//            equityInfoRegistList = this.queryExportRegistList(paramMap);
//        }
//        else {
//            List<String> keyList = StringUtil.splitToList(GlobalConstant.COMMA, keyListStr);
//
//        }
//    }
    
    /**
     * 发送某登记银行代码的数据
     *
     * @param registList 发送的某个机构的投资者身份信息
     * @see
     * @since 1.0
     */
    public void sendEquityHoldCHB(List<EquityInfoRegist001Dto> registList, boolean isSendAllData) {
        if (StringUtil.isEmptyList(registList)) {
            return;
        }
        // 处理已经报送的数据（将已报送数据移至履历表，并删除登记表数据）
        logger.info("handleAlreadySubmitData处理开始：" + DateUtil.getCurrentDateTime());
        this.handleAlreadySubmitData(registList);
        logger.info("handleAlreadySubmitData处理结束：" + DateUtil.getCurrentDateTime());

        int sendCbMaxDataCount = Integer.valueOf(
            SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_SENDCB_MAX_COUNT));

        int dataCount = registList.size();
        if (dataCount <= sendCbMaxDataCount) {
            logger.info("单次sendEquityHoldCHBOneFile处理开始：" + DateUtil.getCurrentDateTime());
            this.sendEquityHoldCHBOneFile(registList, isSendAllData);
            logger.info("单次sendEquityHoldCHBOneFile处理结束：" + DateUtil.getCurrentDateTime());
            return;
        }

        // 分批处理
        List<EquityInfoRegist001Dto> newDataList = new ArrayList<EquityInfoRegist001Dto>();
        newDataList.addAll(registList);

        int part = dataCount / sendCbMaxDataCount; // 分批数
        for (int i = 0; i < part; i++ ) {
            logger.info("单次sendEquityHoldCHBOneFile处理开始：" + DateUtil.getCurrentDateTime());
            this.sendEquityHoldCHBOneFile(newDataList.subList(0, sendCbMaxDataCount), isSendAllData);
            logger.info("单次sendEquityHoldCHBOneFile处理结束：" + DateUtil.getCurrentDateTime());
            newDataList.subList(0, sendCbMaxDataCount).clear();
        }
        // 最后剩下的数据
        if (!StringUtil.isEmptyList(newDataList)) {
            logger.info("单次sendEquityHoldCHBOneFile处理开始：" + DateUtil.getCurrentDateTime());
            this.sendEquityHoldCHBOneFile(newDataList, isSendAllData);
            logger.info("单次sendEquityHoldCHBOneFile处理结束：" + DateUtil.getCurrentDateTime());
        }
    }
	
    /**
    *
   * 发送某登记银行代码的数据(单文件最大1000000条数据)
    * @param registList 发送的某个机构的投资者身份信息
    * @see
    * @since 1.0
    */
   private void sendEquityHoldCHBOneFile(List<EquityInfoRegist001Dto> registList, boolean isSendAllData) {
       logger.info("拼装报送对象处理开始：" + DateUtil.getCurrentDateTime());

       String defaultPhone = "11111111111";
       String empt="无";
       String defaultTel="11111111";
       String defaultEmail="11@11.com";
       //元素名称
       String title = "    wemax_RegistrationBankCode|wemax_InvestorAffiliatedWithIssuingBankPleaseSelect|wemax_InvestorsBankName"
           + "|wemax_InvestorsBankCode|wemax_InvestorAffiliatedWithRegion|wemax_InvestorsCountryOrRegion|wemax_DataType"
           + "|wemax_OriginalDiscriminationId|wemax_DiscriminationId|wemax_InvestorClassification|wemax_PersonalIdType"
           + "|wemax_InstitutionalIdType|wemax_OtherIdName|wemax_IdNumber|wemax_SpvFundCustodianBank|wemax_OtherFundCustodianBank"
           //去掉spv登记编码
           /*+ "|wemax_SpvRegistrationCode"*/
           + "|wemax_InvestorName|wemax_Gender|wemax_RiskPreference|wemax_MobilePhoneNumber"
           + "|wemax_WorkPhoneNumber|wemax_EmailAddress|wemax_InvestorsInformationRegistrationDetails|wemax_RegistrationSerialNumber";
       //元素字符串结果集
       StringBuffer info = new StringBuffer("\n"+"    <![CDATA["+'\n');
       info.append(title);
       info.append('\n');
       List<String> registSeq = new ArrayList<String>();
       Map<String, String> registSeqKeyMap = new HashMap<String, String>();
       //后期去除
//       ChbSubmitStatus chbSubmitStatus = null;
//       List<ChbSubmitStatus> listStatus = new ArrayList<ChbSubmitStatus>();
       //报送表中无报送流水号list
       List<EquityInfoRegist001Dto> unSubmitSeqList = new ArrayList<EquityInfoRegist001Dto>();
       if(CollectionUtils.isNotEmpty(registList)){
           //仅支持录入11 位定长数字，不得填写“无”。如无手机号码，应统一填写“11111111111”（11个 1）。
           //            不得填写“无”，如无固定电话，应统一填写“11111111”（8 个1）。
           //            不得填写“无”，如无固定电话，应统一填写“11@11.com”。
    	   
    	   List<String> sequenceList = CommonUtil.getBatchSequenceIndexData(DataIndexSequenceType.中债登记管理, registList.size());
           int index = 0;
           for (EquityInfoRegist001Dto dto : registList) {
               //针对取消手工处理再报送
               String currRegistSeq = "";
               if(DictCodeListExt2.报送反馈状态_发送失败.getValue().equals(dto.getSubmitFeedbackStatusCode())
                       ||DictCodeListExt2.报送反馈状态_中债退回.getValue().equals(dto.getSubmitFeedbackStatusCode())){
//                   currRegistSeq = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
            	   currRegistSeq = CommonUtil.getSequenceStr(sequenceList, index);
            	   index++;
                   registSeq.add(currRegistSeq);
                   //将报送流水为空的放入list
                   dto.setSumitSeq(currRegistSeq);
                   unSubmitSeqList.add(dto);
               }
               else if(StringUtil.isEmpty(dto.getSumitSeq())){
//                   currRegistSeq = CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理);
            	   currRegistSeq = CommonUtil.getSequenceStr(sequenceList, index);
            	   index++;
                   registSeq.add(currRegistSeq);
                   //将报送流水为空的放入list
                   dto.setSumitSeq(currRegistSeq);
                   unSubmitSeqList.add(dto);
               }
               else{
                   registSeq.add(dto.getSumitSeq());
                   currRegistSeq = dto.getSumitSeq();
               }
               String  value = "";
               //当为识别标识变更时
               if(dto.getType().equals(
                       EquityInfoRegistCommonUtil.getDictCodeAndName(DictCode.数据类型_中债, DictCodeListExt2.数据类型_中债_识别标识变更.getValue()))){
            	   value = ReportGlobalConstant.MANAY_SPACE+dto.getBankCode()+ReportGlobalConstant.VERTICAL_LINE+dto.getIsBelongBank()
                   +ReportGlobalConstant.VERTICAL_LINE+dto.getTheBankName()+ReportGlobalConstant.VERTICAL_LINE+dto.getTheBankCode()
                   +ReportGlobalConstant.VERTICAL_LINE+dto.getHolderMark()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderRegion()
                   +ReportGlobalConstant.VERTICAL_LINE+dto.getType()+ReportGlobalConstant.VERTICAL_LINE+dto.getOrgIndentifyMark()+ReportGlobalConstant.VERTICAL_LINE
                   +dto.getIndentifyMark()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderType()+ReportGlobalConstant.VERTICAL_LINE
                   +dto.getPersonalIdentifyType()+ReportGlobalConstant.VERTICAL_LINE+dto.getInstitutionIdentifyType()+ReportGlobalConstant.VERTICAL_LINE
                   +dto.getOtherIdentifyName()+ReportGlobalConstant.VERTICAL_LINE+dto.getIdentifyNumber()+ReportGlobalConstant.VERTICAL_LINE+dto.getSpvCapAccBank()
                   +ReportGlobalConstant.VERTICAL_LINE
                           +dto.getOtherCapAccBank()+ReportGlobalConstant.VERTICAL_LINE/*+dto.getSpvRegistCode()+"|"*/
                           //要求原则上境内投资者名称中不得含有空格。
                           +(dto.getHolderName().replaceAll(Pattern.quote(StringUtil.EMPTY), ""))+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getHolderSex()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderRiskPrefer()+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getHolderCellphone()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderTelephone()+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getHolderEmail()+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getRemark()+ReportGlobalConstant.VERTICAL_LINE+dto.getSumitSeq();
               }
               else {
                   value = ReportGlobalConstant.MANAY_SPACE+dto.getBankCode()+ReportGlobalConstant.VERTICAL_LINE+dto.getIsBelongBank()
                   +ReportGlobalConstant.VERTICAL_LINE+dto.getTheBankName()+ReportGlobalConstant.VERTICAL_LINE
                   +dto.getTheBankCode()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderMark()+ReportGlobalConstant.VERTICAL_LINE
                   +dto.getHolderRegion()+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getType()+ReportGlobalConstant.VERTICAL_LINE+ReportGlobalConstant.VERTICAL_LINE+dto.getIndentifyMark()
                           +ReportGlobalConstant.VERTICAL_LINE+dto.getHolderType()+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getPersonalIdentifyType()+ReportGlobalConstant.VERTICAL_LINE+dto.getInstitutionIdentifyType()
                           +ReportGlobalConstant.VERTICAL_LINE+dto.getOtherIdentifyName()+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getIdentifyNumber()+ReportGlobalConstant.VERTICAL_LINE+dto.getSpvCapAccBank()+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getOtherCapAccBank()+ReportGlobalConstant.VERTICAL_LINE/*+dto.getSpvRegistCode()+"|"*/
                           //要求原则上境内投资者名称中不得含有空格。
                           +(dto.getHolderName().replaceAll(Pattern.quote(StringUtil.EMPTY), ""))+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getHolderSex()+ReportGlobalConstant.VERTICAL_LINE+dto.getHolderRiskPrefer()+ReportGlobalConstant.VERTICAL_LINE
                           +(StringUtil.equals(dto.getHolderCellphone(), empt)||StringUtil.isEmpty(dto.getHolderCellphone())?defaultPhone:dto.getHolderCellphone())+ReportGlobalConstant.VERTICAL_LINE
                           +(StringUtil.equals(dto.getHolderTelephone(), empt)||StringUtil.isEmpty(dto.getHolderTelephone())?defaultTel:dto.getHolderTelephone())+ReportGlobalConstant.VERTICAL_LINE
                            +(StringUtil.equals(dto.getHolderEmail(), empt)||StringUtil.isEmpty(dto.getHolderEmail())?defaultEmail:dto.getHolderEmail())+ReportGlobalConstant.VERTICAL_LINE
                           +dto.getRemark()+ReportGlobalConstant.VERTICAL_LINE+dto.getSumitSeq();
               }
//               value = "    "+dto.getBankCode()+"|"+dto.getIsBelongBank()+"|"+dto.getTheBankName()+"|"
//                       +dto.getTheBankCode()+"|"+dto.getHolderMark()+"|"+dto.getHolderRegion()+"|"
//                       +dto.getType()+"|"+dto.getOrgIndentifyMark()+"|"+dto.getIndentifyMark()+"|"
//                       +dto.getHolderType()+"|"+dto.getPersonalIdentifyType()+"|"+dto.getInstitutionIdentifyType()+"|"
//                       +dto.getOtherIdentifyName()+"|"+dto.getIdentifyNumber()+"|"+dto.getSpvCapAccBank()+"|"
//                       +dto.getOtherCapAccBank()+"|"/*+dto.getSpvRegistCode()+"|"*/
//                       //要求原则上境内投资者名称中不得含有空格。
//                       +(dto.getHolderName().replaceAll(StringUtil.EMPTY, ""))+"|"
//                       +dto.getHolderSex()+"|"+dto.getHolderRiskPrefer()+"|";
               info.append(value);
//               String value1="";
//               if(dto.getType().equals(
//                     EquityInfoRegistCommonUtil.getDictCodeAndName(DictCode.权益人数据类型, DictCodeListExt2.数据类型_中债_识别标识变更.getValue()))){
//                 value1 = dto.getHolderCellphone()+"|"+dto.getHolderTelephone()+"|"+dto.getHolderEmail()+"|";
//                 }
//               else{
//                 value1 = (StringUtil.equals(dto.getHolderCellphone(), empt)||StringUtil.isEmpty(dto.getHolderCellphone())?defaultPhone:dto.getHolderCellphone())+"|"
//                 +(StringUtil.equals(dto.getHolderTelephone(), empt)||StringUtil.isEmpty(dto.getHolderTelephone())?defaultTel:dto.getHolderTelephone())+"|"
//                    +(StringUtil.equals(dto.getHolderEmail(), empt)||StringUtil.isEmpty(dto.getHolderEmail())?defaultEmail:dto.getHolderEmail())+"|";
//                 }
//               info.append(value1).append(dto.getRemark()).append("|").append(dto.getSumitSeq());
               info.append('\n');

               registSeqKeyMap.put(currRegistSeq, dto.getIndentifyMark());

               //报送状态表插入组装
//               chbSubmitStatus = new ChbSubmitStatus();
//               chbSubmitStatus.setSeqno(dto.getSumitSeq());
//               chbSubmitStatus.setSubmitFeedbackStatus("01");
//               chbSubmitStatus.setSubmitType(DictCodeList.报表名称_投资者身份信息登记.getValue());
//               listStatus.add(chbSubmitStatus);
           }
       }
       info.append("    ]]>");
       info.append('\n');
       logger.info("拼装报送对象处理结束：" + DateUtil.getCurrentDateTime());
       //更新报送状态表
//       this.doUpdateSubmtitStatus(registList,listStatus);
       logger.info("doUpdateSubmtitStatus处理开始：" + DateUtil.getCurrentDateTime());
       this.doUpdateSubmtitStatus(registList,unSubmitSeqList, isSendAllData);
       logger.info("doUpdateSubmtitStatus处理结束：" + DateUtil.getCurrentDateTime());
       //将格式转换为中债报送的固定格式
       List<InterfCnBondSubmitPara> paras = new ArrayList<InterfCnBondSubmitPara>();
       InterfCnBondSubmitPara it = new InterfCnBondSubmitPara();
       List<Map<String, CnBondInterfItem>> listMap = new ArrayList<Map<String, CnBondInterfItem>>();
       CnBondInterfItem cnBondInterfItem = new CnBondInterfItem();
       cnBondInterfItem.setValue(info.toString());
       Map<String, CnBondInterfItem> maps =  new HashMap<String,CnBondInterfItem>();
       maps.put("InvestorsInformationRegistrationCSVDataExplanatory", cnBondInterfItem);
       listMap.add(maps);
       it.setDealType(DictCodeListExt2.报表名称_投资者身份信息登记.getValue());
       it.setInfos(listMap);
       it.setSysDate(DateUtil.now());
       it.setRegistSeq(registSeq);
       it.setRegistBankCode(registList.get(0).getBankCode());

       //将流水号和识别标识键值对存入InterfCnBondSubmitPara
       it.setRegistSeqKeyMap(registSeqKeyMap);

       paras.add(it);
       logger.info("sendCnBond处理开始：" + DateUtil.getCurrentDateTime());
       cnBondInterfService.sendCnBond(paras);
       logger.info("sendCnBond处理结束：" + DateUtil.getCurrentDateTime());
   }
   
   /**
    * 检查对应的task是否有未执行或正在执行中的批处理任务 <功能详细描述>
    * 
    * @param beanname
    * @return false未执行或正在执行中的任务 true 未检查到对应的未执行或正在执行中的任务
    */
   public boolean checkIfInBatchByTaskBeanName(String beanname) {
	   EquityInfoRegist001Service chbAssetInfoInterf = SpringContextUtil.getBean(
			   EquityInfoRegist001Service.class);
       int count = chbAssetInfoInterf.getNoendCountByTaskBeanName(beanname);
       if (count > 0) {
           return false;
       }
       return true;
   }
   
   @Transactional(propagation = Propagation.REQUIRES_NEW)
   @DataSource(DataSourceType.OTHER)
   public int getNoendCountByTaskBeanName(String beanname) {
       return equityInfoRegist001Mapper.selectNoendCountByTaskBeanName(beanname);
   }
   
   public void dealSearchParam(Map<String, Object> param){
	   String orgCode = (String)param.get("orgCode");
       List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
       if (StringUtil.isBlank(orgCode)) {
           orgCodeList = CommonUtil.getOrgCodeListForSearch();
       }
       param.put("orgCodeList", orgCodeList);
       String personalIdentifyType = (String)param.get("personalIdentifyType");
       String institutionIdentifyType = (String)param.get("institutionIdentifyType");
       String status = (String)param.get("status");
       String submitStatus = (String)param.get("submitStatus");
       String submitFeedbackStatus = (String)param.get("submitFeedbackStatus");
       String holderType = (String)param.get("holderType");
       // 增加发送状态
       String sendStatus = (String)param.get("sendStatus");
       param.put("sendStatusList", StringUtil.splitToList(GlobalConstant.COMMA, sendStatus));
       param.put("holderTypeList", StringUtil.splitToList(GlobalConstant.COMMA, holderType));
       param.put("personalIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, personalIdentifyType));
       param.put("institutionIdentifyTypeList", StringUtil.splitToList(GlobalConstant.COMMA, institutionIdentifyType));
       param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, status));
       param.put("submitStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitStatus));
       param.put("submitFeedbackStatusList", StringUtil.splitToList(GlobalConstant.COMMA, submitFeedbackStatus));
//       param.put("submitDeadLineStart", DateCalcUtil.getEndOfDay((String)param.get("submitDeadLineStart")));
       param.put("submitDeadLineStart", param.get("submitDeadLineStart"));
       param.put("submitDeadLineEnd", DateCalcUtil.getEndOfDay((String)param.get("submitDeadLineEnd")));
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
       int dataNum = equityInfoRegist001Mapper.countExportNum(pageParam);
       return dataNum;
   }
   
   /**
    * 启动批处理导出
    * @param param
    */
	public AjaxResult batchExport(Map<String, Object> param) {
		AjaxResult ajaxResult = new AjaxResult();
		// 是否未执行或正在执行中
		if (!checkIfInBatchByTaskBeanName(
           BatchBeanName.投资者身份信息登记导出.getTaskId())) {
           ajaxResult.setMessage("投资者身份信息登记导出批处理正在执行中或待执行！", MessageType.错误消息);
           return ajaxResult;
       }
		ChbSubinfoBatchLog log = new ChbSubinfoBatchLog();
		log.setBatchExeId(IDGenerator.generate());
		log.setTaskId(BatchBeanName.投资者身份信息登记导出.getTaskId());
		log.setBusiConfirmDate(param.get("busiConfirmDate")==null?"":param.get("busiConfirmDate").toString());
		log.setOperator(LoginUserUtil.getUserName());
		log.setFileType(DictCodeListExt3.报送文件类别_投资者身份信息登记.getValue());
		log.setDealStatus(DictCodeListExt3.报送处理状态_未处理.getValue());
		log.setGenCondition(param.get("key")==null?"":param.get("key").toString());
		log.setCreateUser(LoginUserUtil.getUserName());
		log.setCreateTime(new Date());
		log.setUpdateUser(LoginUserUtil.getUserName());
		log.setUpdateTime(new Date());
		// 日志存表
		simpleRestTemplate.postForObject(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/insert", log,
				ChbSubinfoBatchLog.class);
		// 启动批处理
		sysBatchStart001Service.executeBatch();
		// 批处理启动成功，可在批处理监控页面查看进度
//		return new AjaxResult("导出excel文件批处理启动成功，可在批处理监控页面查看进度");
		ajaxResult.setData(true);
		return ajaxResult;
	}
	
	
	 /**
	    * 启动批处理导出
	    * @param param
	    */
		public AjaxResult batchExportAndDoManual(Map<String, Object> param) {
			AjaxResult ajaxResult = new AjaxResult();
			// 是否未执行或正在执行中
			if (!checkIfInBatchByTaskBeanName(
	           BatchBeanName.投资者身份信息登记导出.getTaskId())) {
	           ajaxResult.setMessage("投资者身份信息登记导出批处理正在执行中或待执行！", MessageType.错误消息);
	           return ajaxResult;
	       }
			if (!checkIfInBatchByTaskBeanName(
			           BatchBeanName.投资者身份信息登记手工处理.getTaskId())) {
			           ajaxResult.setMessage("投资者身份信息登记手工处理批处理正在执行中或待执行！", MessageType.错误消息);
			           return ajaxResult;
			       }
			ChbSubinfoBatchLog log = new ChbSubinfoBatchLog();
			log.setBatchExeId(IDGenerator.generate());
			log.setTaskId(BatchBeanName.投资者身份信息登记导出.getTaskId());
			log.setBusiConfirmDate(param.get("busiConfirmDate")==null?"":param.get("busiConfirmDate").toString());
			log.setOperator(LoginUserUtil.getUserName());
			log.setFileType(DictCodeListExt3.报送文件类别_投资者身份信息登记.getValue());
			log.setDealStatus(DictCodeListExt3.报送处理状态_未处理.getValue());
			log.setGenCondition(param.get("key")==null?"":param.get("key").toString());
			log.setCreateUser(LoginUserUtil.getUserName());
			log.setCreateTime(new Date());
			log.setUpdateUser(LoginUserUtil.getUserName());
			log.setUpdateTime(new Date());
			// 日志存表
			simpleRestTemplate.postForObject(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/insert", log,
					ChbSubinfoBatchLog.class);
			
			//手工处理
			ChbBatchPara record = new ChbBatchPara();
		    record.setTaskId(BatchBeanName.投资者身份信息登记手工处理.getTaskId());
		    record.setParaId(IDGenerator.generate());
		    if (param.get("key") != null) {
		        record.setParas(param.get("key").toString());
		        simpleRestTemplate.postForObject(
         	       		SvrServiceApplication.CHB3_SERVICE_NAME + "/EquityInfoRegist/insert", record,
         	       	ChbBatchPara.class);
		    }
			// 启动批处理
			sysBatchStart001Service.executeBatch();
			// 批处理启动成功，可在批处理监控页面查看进度
//			return new AjaxResult("导出excel文件批处理启动成功，可在批处理监控页面查看进度");
			ajaxResult.setData(true);
			return ajaxResult;
		}
}
