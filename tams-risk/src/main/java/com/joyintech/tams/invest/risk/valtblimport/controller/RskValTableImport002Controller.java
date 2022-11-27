/*
 * 文件名：RskValTableImport002Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年12月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.valtblimport.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.util.*;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.constant.BussinessConstant;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.dto.RskValRelateImport001Dto;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalrelateimport.service.RskValRelateImport002Service;
import com.joyintech.tams.invest.risk.valtblimport.dto.*;
import com.joyintech.tams.invest.risk.valtblimport.service.*;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tams.valuation.analysis.ValuationTableTrain;
import com.joyintech.tams.valuation.analysis2.ValuationTableAggregateInfoHander;
import com.joyintech.tams.valuation.analysis2.ValuationTableAnalysis2;
import com.joyintech.tams.valuation.analysis2.ValuationTableListHeaderAfterHander;
import com.joyintech.tams.valuation.analysis2.ValuationTableListHeaderBeforeHander;
import com.joyintech.tams.valuation.analysis2.ValuationTableListHeaderHander;
import com.joyintech.tams.valuation.analysis2.info.ValuationTableHeader2;
import com.joyintech.tams.valuation.analysis2.info.ValuationTableInfo2;
import com.joyintech.tams.valuation.analysis2.info.ValuationTableRow2;
import com.joyintech.tams.valuation.analysis2.info.ValuationTableTotal2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.joyintech.tams.invest.common.util.ExcelUtil;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 估值表导入
 * 〈功能详细描述〉
 * @author zhangnian
 * @version 1.0 2018年12月29日
 * @see RskValTableImport002Controller
 * @since 1.0
 */
@Controller
@PageId("RskValTableImport002")
@RequestMapping("/invest/risk/valtblimport/RskValTableImport002")
public class RskValTableImport002Controller extends BaseController{
    Logger logger = LoggerFactory.getLogger(this.getClass());

    // NLP模型训练库文件存放路径
    @Value("${application.nlpModel:/home/model/}")
    private String applicationNLPModelPath;
    //获取平台日
    @Autowired(required = false)
    private IPlatformDate iPlatformDate;
    @Autowired
    private RskValTableImportService rskValTableImportService;
    @Autowired
    private RskValRelateImport002Service rskValRelateImport002Service;
    @Autowired
    private RskValTableImportCommonService rskValTableImportCommonService;
    @Autowired
    private RskValtblDataService rskValtblDataService;
    @Autowired
    private RskValtblFileInfoService rskValtblFileInfoService;
    @Autowired
    private RskValtblAnlsLogService rskValtblAnlsLogService;
    @Override
    public String init(Model model) {
        model.addAttribute("ptlId", this.getParam("ptlId"));
        model.addAttribute("valTableFileName", this.getParam("valTableFileName"));
        model.addAttribute("valDate", this.getParam("valDate"));
        model.addAttribute("structMapId", this.getParam("structMapId"));
        model.addAttribute("sumsubMapId", this.getParam("sumsubMapId"));
        model.addAttribute("assetSubMapId", this.getParam("assetSubMapId"));
        model.addAttribute("subjectCodeCol", this.getParam("subjectCodeCol"));
        model.addAttribute("startRow", this.getParam("startRow"));
        model.addAttribute("trimChars", this.getParam("trimChars"));
        model.addAttribute("filterId", this.getParam("filterId"));
        model.addAttribute("ptlName", this.getParam("ptlName"));

        // 学习
        logger.info("开始学习");
        String segModelPath = applicationNLPModelPath + "seg.m";
        String headerTrainDataDir = applicationNLPModelPath + "估值表识别/表头";
        String bayesModelPath = applicationNLPModelPath + "估值模型/bayes-valuation-table-header-model.gz";
        logger.info("学习完成");

        ValuationTableTrain train = new ValuationTableTrain(segModelPath, headerTrainDataDir, bayesModelPath);
        try {
            train.train();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return super.init(model);
    }
    
    
    /**
     * 预览初始化
     * <功能详细描述>
     * @param dto
     * @return
     * @throws Exception 
     * @see
     * @since 1.0
     */
    @RequestMapping(value = "/initValTableInfo")
    @ResponseBody
    public List<RskValtblDataDto> initValTableInfo(@RequestBody RskValTableImportParamDto dto) throws Exception {
        //估值表实体类，将来写进数据库
        RskValtblFileInfoDto rskValtblFileInfoDto=new RskValtblFileInfoDto();
        //建立估值表数据实体类
        List<RskValtblDataDto> rskValtblDataDtoList=new ArrayList<>();
        //建立日志表实体类
        RskValtblAnlsLogDto rskValtblAnlsLogDto=new RskValtblAnlsLogDto();
        List<RskValtblAssetSubDto> rskValtblAssetSubList = new ArrayList<>();

        String timeModelPath = applicationNLPModelPath + "time.m";
        logger.info("读取time.m:" + timeModelPath);

        String headerTrainDataDir = applicationNLPModelPath + "估值表识别" + File.separator + "表头";
        logger.info("读取估值表识别的表头文件夹:" + headerTrainDataDir);

        String aggregateTrainDataDir = applicationNLPModelPath + "估值表识别" + File.separator + "汇总";
        logger.info("读取估值表识别的汇总文件夹:" + aggregateTrainDataDir);

        String bayesModelPath = applicationNLPModelPath + "估值模型" + File.separator + "bayes-valuation-table-header-model.gz";
        logger.info("读取学习后生成的估值模型文件:" + bayesModelPath);

        // excel 全路径
        String excel = dto.getValTableFileName();

        // 方法2
        ValuationTableAnalysis2 analysis2 = new ValuationTableAnalysis2();
        ValuationTableListHeaderBeforeHander headerBeforeHandler = new ValuationTableListHeaderBeforeHander(timeModelPath);
        analysis2.setHeaderBeforeHander(headerBeforeHandler);
        ValuationTableListHeaderHander headerHandler = new ValuationTableListHeaderHander(headerTrainDataDir);
        analysis2.setHeaderHander(headerHandler);

        ValuationTableAggregateInfoHander aggregateInfoHandler = new ValuationTableAggregateInfoHander(aggregateTrainDataDir);
        ValuationTableListHeaderAfterHander headerAfterHandler = new ValuationTableListHeaderAfterHander(aggregateInfoHandler);
        analysis2.setHeaderAfterHander(headerAfterHandler);

        ValuationTableInfo2 info2 = analysis2.execute(excel);

        logger.info("估值表名称: " + info2.getTableName());
        logger.info("估值日期: " + info2.getDate());
        logger.info("明细表头:");

        //解析数据入库
        //RskValtblFileInfoDto rskValtblFileInfoDto=new RskValtblFileInfoDto();
        /*rskValtblFileInfoDto.setSeqNo(IDGenerator.generate(32));
        rskValtblFileInfoDto.setFileGrp(aggregateTrainDataDir);*/
       // rskValtblFileInfoService.insert(rskValtblFileInfoDto);

        for (ValuationTableHeader2 name : info2.getTableHeaderInfo().values()) {
            System.out.println("\t" + name);
        }

        // 统计数据
        System.out.println("\n" + info2.getTotalObj());

        //估值表入库
        Date platDate = iPlatformDate.getSysDate();
        String seqNo=IDGenerator.generate(32);
        rskValtblFileInfoDto.setSeqNo(seqNo);//添加流水号
        rskValtblFileInfoDto.setFileDate(info2.getDate());//添加估值日期
           //rskValtblFileInfoDto.setFileGrp("test");
        rskValtblFileInfoDto.setFileName(dto.getPtlId());
        rskValtblFileInfoDto.setFileHeader(info2.getTableName());//添加估值表头
        //判断是否数据库中已经存在同一个估值日期的同一个估值表
        //判断估值日期没有的估值表
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get(info2.getDate()))){
            //判断是否有该种数据
            List<String> nameList= rskValtblFileInfoService.selectName(rskValtblFileInfoDto.getFileName());
            if(nameList.size()!=0){
                //先删除数据表中的数据
                rskValtblDataService.deleteNameData(rskValtblFileInfoDto.getFileName());
                rskValtblFileInfoService.deleteName(rskValtblFileInfoDto.getFileName());
            }
        }else{
            //判断同一个日期下的同一个估值表是否存在
            List<String> nameDateList=rskValtblFileInfoService.selectNameDate(rskValtblFileInfoDto.getFileName(),rskValtblFileInfoDto.getFileDate());
            if(nameDateList.size()!=0){
                //先删除数据表中的数据
                rskValtblDataService.deleteNameDateData(rskValtblFileInfoDto.getFileName(),rskValtblFileInfoDto.getFileDate());
                rskValtblFileInfoService.deleteNameDate(rskValtblFileInfoDto.getFileName(),rskValtblFileInfoDto.getFileDate());
            }
        }



        //日志表入库
        rskValtblAnlsLogDto.setSeqNo(IDGenerator.generate(32));//生成流水号
        rskValtblAnlsLogDto.setFileInfoId(rskValtblFileInfoDto.getSeqNo());//关联数据表的流水号
        rskValtblAnlsLogDto.setFileInfoName(rskValtblFileInfoDto.getFileName());//关联数据表的文件名
        rskValtblAnlsLogDto.setRemark("test");//将来从info2中取出
        rskValtblAnlsLogDto.setCreateUser(CommonUtil.getUserName());
        rskValtblAnlsLogDto.setCreateTime(platDate);
        rskValtblAnlsLogDto.setUpdateUser(CommonUtil.getUserName());
        rskValtblAnlsLogDto.setUpdateTime(platDate);
        rskValtblAnlsLogService.insert(rskValtblAnlsLogDto);



        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("实收资本"))){
            rskValtblFileInfoDto.setCapital(new BigDecimal(info2.getTotalObj().get("实收资本")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("基金资产净值-本币成本"))){
            rskValtblFileInfoDto.setFundAssetValCost(new BigDecimal(info2.getTotalObj().get("基金资产净值-本币成本")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("基金资产净值-本币市值"))){
            rskValtblFileInfoDto.setFundAssetValSmv(new BigDecimal(info2.getTotalObj().get("基金资产净值-本币市值")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("资产合计-本币成本"))){
            rskValtblFileInfoDto.setAssetTotalCost(new BigDecimal(info2.getTotalObj().get("资产合计-本币成本")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("资产合计-本币市值"))){
            rskValtblFileInfoDto.setAssetTotalSmv(new BigDecimal(info2.getTotalObj().get("资产合计-本币市值")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("负债合计-本币成本"))){
            rskValtblFileInfoDto.setLiabilityTotalCost(new BigDecimal(info2.getTotalObj().get("负债合计-本币成本")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("负债合计-本币市值"))){
            rskValtblFileInfoDto.setLiabilityTotalSmv(new BigDecimal(info2.getTotalObj().get("负债合计-本币市值")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("万份收益"))){
            rskValtblFileInfoDto.setPorfit(new BigDecimal(info2.getTotalObj().get("万份收益")));
        }
        if(!ObjectUtils.isEmpty(info2.getTotalObj().get("今日单位净值"))){
            rskValtblFileInfoDto.setTodUnitVal(new BigDecimal(info2.getTotalObj().get("今日单位净值")));
        }
        rskValtblFileInfoDto.setCreateUser(CommonUtil.getUserName());
        rskValtblFileInfoDto.setCreateTime(platDate);
        rskValtblFileInfoDto.setUpdateUser(CommonUtil.getUserName());
        rskValtblFileInfoDto.setUpdateTime(platDate);
        rskValtblFileInfoService.insert(rskValtblFileInfoDto);
        rskValtblDataDtoList=printList(info2.getTableListData(), seqNo,rskValtblDataDtoList);
        rskValtblDataService.insert(rskValtblDataDtoList);

        return rskValtblDataDtoList;
    }

    public List<RskValtblDataDto> printList(List<ValuationTableRow2> list, String seqNo,List<RskValtblDataDto> rskValtblDataDtoList ) {
        //建立估值表数据实体类
        RskValtblDataDto rskValtblDataDto=null;
        for (ValuationTableRow2 row : list) {
            System.out.println(row);
            rskValtblDataDto=new RskValtblDataDto();
            //流水号
            rskValtblDataDto.setSeqNo(IDGenerator.generate(32));
            // 科目代码
            rskValtblDataDto.setFileInfoId(seqNo);
            rskValtblDataDto.setSubCode(row.getSubjectCode());
            rskValtblDataDto.setSubName(row.getData().get("科目名称"));
            if (!ObjectUtils.isEmpty(row.getData().get("数量"))) {
                rskValtblDataDto.setQuantity(new BigDecimal(row.getData().get("数量").replaceAll(",", "")));
            }
            if (!ObjectUtils.isEmpty(row.getData().get("单位成本-n"))) {
                rskValtblDataDto.setUnitCost(new BigDecimal(row.getData().get("单位成本-n").replaceAll(",", "")));
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币成本"))) {
                rskValtblDataDto.setSecurityCost(new BigDecimal(row.getData().get("本币成本").replaceAll(",", "")));
            }
            if (!ObjectUtils.isEmpty(row.getData().get("成本占比"))) {
                rskValtblDataDto.setCtnwr(new BigDecimal(row.getData().get("成本占比").replace("%", "")));
            }
            if (!ObjectUtils.isEmpty(row.getData().get("市价-n"))) {
                rskValtblDataDto.setSmv(new BigDecimal(row.getData().get("市价-n").replaceAll(",", "")));
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币市值"))) {
                rskValtblDataDto.setMcr(new BigDecimal(row.getData().get("本币市值").replace(",", "")));
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币估值增值"))) {
                rskValtblDataDto.setValueAdded(new BigDecimal(row.getData().get("本币估值增值").replace(",", "")));
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币利息"))) {
                rskValtblDataDto.setInterest(new BigDecimal(row.getData().get("本币利息").replace(",", "")));// 利息（本币）
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币股利/红利"))) {
                rskValtblDataDto.setDividend(new BigDecimal(row.getData().get("本币股利/红利").replace(",", "")));// 股利/红利（本币）
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币利息调整"))) {
                rskValtblDataDto.setIntAdj(new BigDecimal(row.getData().get("本币利息调整").replace("%", "")));// 利息调整（本币）
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币减值"))) {
                rskValtblDataDto.setDevalue(new BigDecimal(row.getData().get("本币减值").replace("," +
                        "", "")));// 减值（本币）
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币逾期利息"))) {
                rskValtblDataDto.setLateInt(new BigDecimal(row.getData().get("本币逾期利息").replace(",", "")));//逾期利息（本币）
            }
            if (!ObjectUtils.isEmpty(row.getData().get("本币影子价格"))) {
                rskValtblDataDto.setShadowPriVal(new BigDecimal(row.getData().get("本币影子价格").replace(",", "")));//影子价格估值（本币）
            }
            rskValtblDataDto.setCcy(row.getData().get("币种"));
            rskValtblDataDto.setCreateUser(CommonUtil.getUserName());
            rskValtblDataDto.setCreateTime(iPlatformDate.getSysDate());
            rskValtblDataDto.setUpdateUser(CommonUtil.getUserName());
            rskValtblDataDto.setUpdateTime(iPlatformDate.getSysDate());
            rskValtblDataDto.setSubOneCat(ExcelUtil.getSubOneCat(rskValtblDataDto.getSubCode(),
                    rskValtblDataDto.getSubName(),null == rskValtblDataDto.getSecurityCost() ? new BigDecimal(0) : rskValtblDataDto.getSecurityCost()
                    , null == rskValtblDataDto.getSmv() ? new BigDecimal(0) : rskValtblDataDto.getSmv()));
            //异常标识，如果科目代码和科目名称解析为空，异常标识置1
           /* if(row.getSubjectCode()==null||row.getData().get("科目名称")==null){
                rskValtblDataDto.setExpFlag(1);
            }else{
                rskValtblDataDto.setExpFlag(0);
            }*/
            //rskValtblDataService.insert(rskValtblDataDto);

            // 汇率
            // rskValtblAssetSubDto.setExchangeRate();
            rskValtblDataDtoList.add(rskValtblDataDto);


            List<ValuationTableRow2> child = row.getChild();
            if (child.size()>0) {
                printList(child,seqNo,rskValtblDataDtoList);
            }
        }
        //rskValtblDataService.insert(rskValtblDataDtoList);

        // 返回数据列表
        return rskValtblDataDtoList;
    }
    
    /**
     * 保存check
     * 组合估值日是否已经存在
     * @param dto
     * @param result
     * @return
     * @see
     * @since 1.0
     */
//    @ResponseBody
//    @RequestMapping("check")
//    public Map<String,Object> check(@RequestBody RskValTableImportParamDto dto, BindingResult result) {
//        Map<String,Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("ptlId", dto.getPtlId());
//        paramMap.put("valDate", DateUtil.format(dto.getValDate()));
//        int valCount = rskValTableImportService.selectCount(paramMap);
//        paramMap.put("valCount", valCount);
//        return paramMap;
//    }
    
    /**
     * 保存
     * <功能详细描述>
     * @param dto
     * @return
     * @throws ParseException 
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping(PAGE_SAVE)
    public AjaxResult save(@RequestBody RskValtblAssetSubDto dto) throws ParseException {
        dto.setImpMode(DictCodeListExt1.导入方式_手工.getValue());
        List<String> errorList = rskValTableImportService.insert(dto);
        //处理成功的文件入库估值表导入日期记录表
        if(null!=dto.getSelectedItem()&&!dto.getSelectedItem().isEmpty()){
            String deptCode=(LoginUserUtil.getInstCode()!= null && LoginUserUtil.getInstCode().size()>0) ? LoginUserUtil.getInstCode().get(0):null;
            Date startDate = DateUtil.now();
            StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
            for (String item : errorList) {
                sb.append("[");
                sb.append(item);
                sb.append("]");
                sb.append(GlobalConstant.HTML_NEWLINE);
            }
            //根据组合信息，查询是否有对应的映射信息
            RskValRelateImport001Dto import001Dto = rskValRelateImport002Service.selectInfoById(dto.getPtlId());
            //保存委托估值导入记录表
            String chbValSplitStr = CacheUtil.getSysParam(BussinessConstant.CHB_VAL_SPLIT_STR);
            String fileName=import001Dto.getName()+chbValSplitStr+DateUtil.format(dto.getValDate(),"yyyyMMdd");
            String errorCode=StringUtil.EMPTY;
            if(StringUtil.isNotEmpty(errorList) && errorList.size()>0){
                errorCode=DictCodeListExt1.估值表自动解析错误码_自动加券失败.getValue();
            }
            rskValTableImportCommonService.saveRskOutValtblImportHis(fileName,fileName, dto.getPtlId(), import001Dto.getName(), dto.getValDate(),
                    DictCodeListExt1.估值表自动解析处理状态_正常结束.getValue(),errorCode,startDate,DateUtil.parse(DateUtil.getSysDate()),deptCode,sb.toString());
        }
        String eMsg="";
        if(!StringUtil.isEmptyList(errorList)){
        	for(String errorInfo:errorList){
            	eMsg+=StringUtil.isNotEmpty(errorInfo)?("["+errorInfo+"]"):"";
            }
        	return new AjaxResult(getMessage("BUSS_COM_E_0033")+GlobalConstant.HTML_NEWLINE+"以下资产未通过中债要素校验，自动加券失败，需在对应页面新建资产信息："+eMsg);
        }  
        return new AjaxResult(getMessage("BUSS_COM_E_0033"));
    }

    /**
     * 获取警告信息
     *
     * @param dto
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @RequestMapping(value = "/getTableInfo")
    @ResponseBody
    public AjaxResult getTableInfo(@RequestBody RskValTableImportParamDto dto) throws Exception {
        List<RskValtblAssetSubDto> RskValtblAssetSubList = null; //TODO rskValTableImportService.viewValTable(dto);
        if(dto.getWarnMap().get(dto.getAssetName()) > 0) {
            return new AjaxResult(dto.getAssetName() + "存在" + dto.getWarnMap().get(dto.getAssetName()) + "条科目未配置映射" , MessageType.警告消息);
        }
        else {
            return new AjaxResult();
        }
    }

    @RequestMapping(value="/selectLog")
    @ResponseBody
    public List<String> selectLog(String infoName){
         return  rskValtblAnlsLogService.selectLog(infoName);
    }
}
