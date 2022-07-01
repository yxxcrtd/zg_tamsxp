/*
 * 文件名：MstYieldCurveInfoService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年8月8日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.service;

import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.JoyinMessage;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.ChbMstExchangeInfoMapper;
import com.joyintech.tams.common.base.dao.MstExchargeQuoteErrodataMapper;
import com.joyintech.tams.common.base.entity.MstExchargeQuoteErrodataWithBLOBs;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dao.ChbExchangeQuotationInfoMapper;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.dto.ChbExchangeQuotationInfoDto;

import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import com.joyintech.tams.svreport.chb.common.service.CheckExcelImportService;
import com.joyintech.tams.svreport.chb.common.util.DuplicateDataUtil;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import com.joyintech.tams.svreport.chb.common.util.ExcelUtil;
import com.joyintech.tams.svreport.interf.chb.baseinfo.service.SvrExchangFileImpService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.dto.ProcessParameter;
import com.joyintech.workflow.engine.dto.WorkFlowParameter;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 汇率行情
 *
 * @author zhoubin
 * @version 1.0 2022年03月30日
 * @see ChbExchangeQuotationInfoService
 * @since 1.0
 */

@Service
public class ChbExchangeQuotationInfoService extends CommonExportService<ChbExchangeQuotationInfoDto> implements BaseProcessService<ChbExchangeQuotationInfoDto> {

    private final Logger logger = LoggerFactory.getLogger(ChbExchangeQuotationInfoService.class);

    public static final String CONFIG_FILE = "/com/joyintech/tams/svreport/chb/businessdatamaintain/chbexchangeinfo/conf/export_conf.xml";
    /**
     * 表名
     */
    public static final String TABLE_NAME = "MST_EXCHARGE_QUOTE";
    /**
     * 接口类型
     */
    public static final String CHECK_FLAG = "D";

    /**
     * 删除
     */
    public static final String DELETE = "DELETE";
    /**
     * 新建
     */
    public static final String NEW = "NEW";

    /**
     * 分页大小
     */
    public static final int PAGE_SIZE = 200;


    @Resource
    private ChbExchangeQuotationInfoMapper chbExchangeQuotationInfoMapper;
    @Resource
    private BaseCommonCheckService baseCommonCheckService;
    @Resource
    private BatchInsertService batchInsertService;
    @Autowired
    private SvrExchangFileImpService svrExchangFileImpService;
    @Autowired
    private MstExchargeQuoteErrodataMapper mstExchargeQuoteErrodataMapper;
    @Autowired
    private CommonCodeCheckBatchService commonCodeCheckBatchService;


    /**
     * 汇率行情分页查询
     *
     * @param param 查询参数
     * @return 返回查询结果
     */
    public Page<ChbExchangeQuotationInfoDto> selectForPage(Map<String, Object> param) {
        String exchargeCode = (String) param.get("exchargeCode");
        if (StringUtil.isNotEmpty(exchargeCode)) {
            param.put("exchargeCodeList", StringUtil.splitToList(GlobalConstant.COMMA, exchargeCode));
        }
        return super.queryForPage(Integer.parseInt(param.get("page").toString()), Integer.parseInt(param.get("length").toString()), () -> chbExchangeQuotationInfoMapper.select(param));
    }

    /**
     * 查询所有数据验证删除是否存在
     *
     * @param pageParam 查询参数
     * @return 查询结果
     */
    public List<ChbExchangeQuotationInfoDto> selectAllList(Map<String, Object> pageParam) {
        ChbExchangeQuotationInfoDto dto = new ChbExchangeQuotationInfoDto();
        if(pageParam.get("status").equals(DictCodeListExt3.中债数据状态_待处理.getValue())){
            MstExchargeQuoteErrodataWithBLOBs mstExchargeQuoteErrodataWithBLOBs = mstExchargeQuoteErrodataMapper.selectByPrimaryKey((String) pageParam.get("datauuid"));
            if(mstExchargeQuoteErrodataWithBLOBs!=null){
                dto = ErrdataConvertUtil.errdataConvert(dto.getClass(),mstExchargeQuoteErrodataWithBLOBs.getOrgData());
                dto.setDatauuid(mstExchargeQuoteErrodataWithBLOBs.getDatauuid());
                dto.setStatusValid(DictCodeListExt3.中债数据状态_待处理.getValue());
            }
            return  Lists.newArrayList(dto);
        }else{

            return chbExchangeQuotationInfoMapper.select(pageParam);
        }
    }


    /**
     * 导出
     */
    @Override
    public void buildExportParam(ExportParam exportParam) {
        exportParam.config(CONFIG_FILE).dataSource(DataSourceType.MASERT);
    }

    @Override
    public Page<ChbExchangeQuotationInfoDto> selectPageData(Map<String, Object> selectParam) {
        return selectForPage(selectParam);
    }

    /**
     * 设置导出单元格
     */
    @Override
    public void setCellValue(String beanName, Cell cell, String propName, Object value) {
        if (StringUtil.equals(propName, "tradeDate")) {
            cell.setCellValue(DateUtil.format((Date) value));
        }
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName) {

        AjaxResult webResult = new AjaxResult();
        // 检查文件名是否包含汇率行情信息
        if (!fileName.contains("汇率行情")) {
            webResult.setMessage(this.getMessage("ImportFileName"), MessageType.错误消息);
            return webResult;
        }
        List<Map<String, String>> list = Lists.newArrayList();
        try {
            list = dataMapAnalysis(fileName);
        } catch (Exception e) {
            logger.error("解析导入文件异常:{}", e.getMessage());
        }
        if(list.size()<1){
            webResult.setMessage("请勿上传空文件", MessageType.错误消息);
            return webResult;
        }
        int i = chbExchangeQuotationInfoMapper.countDuplicatedData(list);
        if(i>0){
            String[] checkEuplicatedKeys ={"EXCHARGE_CODE","TRADE_DATE","DATASOURCE_TYPE"};
            int dup = DuplicateDataUtil.countDuplicatedData(list, checkEuplicatedKeys);
            webResult.setMessage(this.getMessage("SvrDuplicateMessage",dup,i), MessageType.确认消息);
        }
        return webResult;

    }


    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importData(String fileName) {

        List<Map<String, String>> list = Lists.newArrayList();
        try {
            list = dataMapAnalysis(fileName);
        } catch (Exception e) {
            logger.error("解析导入文件异常:{}", e.getMessage());
        }
        List<ExcelValidDto> listExcel = new ArrayList<ExcelValidDto>();
        svrExchangFileImpService.importDB(listExcel,list,new InterfFile(),null);
        return listExcel;
    }

    /**
     * 增强check list转换为Bean
     *
     * @return 返回检查结果
     */
    private List<ChbExchangeQuotationInfoDto> convertToBean(List<Map<String, String>> mapList) {


        List<ChbExchangeQuotationInfoDto> returnList = new LinkedList<>();
        ChbExchangeQuotationInfoDto dto = null;
        for (Map<String, String> map : mapList) {
            dto = new ChbExchangeQuotationInfoDto();
            if (map.containsKey("EXCHARGE_CODE")) {
                String exchargeCode = map.get("EXCHARGE_CODE");
                dto.setExchargeCode(exchargeCode);
                dto.setBasisCyy(exchargeCode.split("/")[0]);
                dto.setQuotCyy(exchargeCode.split("/")[1]);
            }
            if (map.containsKey("TRADE_DATE")) {
                dto.setTradeDate(DateUtil.parse(map.get("TRADE_DATE")));
            }
            if (map.containsKey("DATASOURCE_TYPE")) {
                dto.setDatasourceType(map.get("DATASOURCE_TYPE"));
            }
            if (map.containsKey("OPEN_PRICE")) {
                String openPrice = map.get("OPEN_PRICE");
                dto.setOpenPrice(StringUtil.isBlank(openPrice) ? null : new BigDecimal(openPrice));
            }
            if (map.containsKey("CLOSE_PRICE")) {
                String closePrice = map.get("CLOSE_PRICE");
                dto.setClosePrice(StringUtil.isBlank(closePrice) ? null: new BigDecimal(closePrice));
            }
            if (map.containsKey("MID_PRICE")) {
                String midPrice = map.get("MID_PRICE");
                dto.setMidPrice(StringUtil.isBlank(midPrice)? null : new BigDecimal(midPrice));
            }
            if (map.containsKey("HIGH_PRICE")) {
                String highPrice = map.get("HIGH_PRICE");
                dto.setHighPrice(StringUtil.isBlank(highPrice)? null : new BigDecimal(highPrice));
            }
            if (map.containsKey("LOW_PRICE")) {
                String lowPrice = map.get("LOW_PRICE");
                dto.setLowPrice(StringUtil.isBlank(lowPrice)? null : new BigDecimal(lowPrice));
            }
            if (map.containsKey("QUOT_UNIT")) {
                dto.setQuotUnit(Long.parseLong(map.get("QUOT_UNIT")));
            }
            if (Objects.equals("A", map.get("STATUS"))) {
                dto.setStatus(DictCodeListExt2.数据状态_待处理.getValue());
                dto.setErrMsg(map.get("ERRMSG"));
            } else {
                dto.setStatus(DictCodeListExt2.数据状态_有效.getValue());
            }
            dto.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            returnList.add(dto);
        }
        return returnList;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doComplete(ChbExchangeQuotationInfoDto dto, OperationType operationType) {
        dto.setOperateType(operationType.toString());
        List<ChbExchangeQuotationInfoDto> list = new ArrayList<>(1);
        dto.setStatus(DictCodeListExt2.数据状态_有效.getValue());
        if (StringUtil.equals(dto.getOperateType(), DELETE)) {
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),dto.getStatusValid())){
                mstExchargeQuoteErrodataMapper.deleteByPrimaryKey(dto.getDatauuid());
            }else{
                List<ChbExchangeQuotationInfoDto> listDto = new ArrayList<>(1);
                dto.setStatus(DictCodeListExt2.数据状态_删除.getValue());
                listDto.add(dto);
                chbExchangeQuotationInfoMapper.updateExchargeQuoteInfo(listDto);
            }
        } else if (StringUtil.equals(dto.getOperateType(), NEW)) {
            MstExchargeQuoteErrodataWithBLOBs bloBs = new MstExchargeQuoteErrodataWithBLOBs();
            bloBs.setExchargeCode(dto.getExchargeCode());
            bloBs.setTradeDate(dto.getTradeDate());
            bloBs.setDatasourceType(dto.getDatasourceType());
            commonCodeCheckBatchService.deleteMstExchargeQuoteErrodata(Lists.newArrayList(bloBs));
            //删除状态是已删除的数据
            chbExchangeQuotationInfoMapper.deleteChbExchargeQuoteByKey(dto.getExchargeCode(), dto.getTradeDate(), dto.getDatasourceType(), DictCodeListExt2.数据状态_删除.getValue());
            //设置基本币种，单位币种等
            String exchargeCode = dto.getExchargeCode();
            if (exchargeCode != null) {
                List<String> splitToList = StringUtil.splitToList("/", exchargeCode);
                dto.setBasisCyy(splitToList.get(0));
                dto.setQuotCyy(splitToList.get(1));
            }
            dto.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            list.add(dto);

            chbExchangeQuotationInfoMapper.insertExchargeQuoteInfo(list);


        } else {
            list.add(dto);
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),dto.getStatusValid())){
                mstExchargeQuoteErrodataMapper.deleteByPrimaryKey(dto.getDatauuid());
                String exchargeCode = dto.getExchargeCode();
                if (exchargeCode != null) {
                    List<String> splitToList = StringUtil.splitToList("/", exchargeCode);
                    dto.setBasisCyy(splitToList.get(0));
                    dto.setQuotCyy(splitToList.get(1));
                }
                chbExchangeQuotationInfoMapper.insertExchargeQuoteInfo(list);
            }else{
                chbExchangeQuotationInfoMapper.updateExchargeQuoteInfo(list);
            }

        }
    }


    public Integer getChbExchargeQuoteByKey(ChbExchangeQuotationInfoDto dto) {
        dto.setStatus(DictCodeListExt2.数据状态_有效.getValue());
        return chbExchangeQuotationInfoMapper.getChbExchargeQuoteByKey(dto);
    }


    /**
     * List<List<String>>转成List<Map<String, String>>
     *
     * @param dataList
     * @return
     */
    private List<Map<String, String>> convertMapList(List<List<String>> dataList) {

        List<Map<String, String>> mapList = new LinkedList<>();
        for (int i = 0; i < dataList.size(); i++) {
            List<String> list = dataList.get(i);
            Map<String, String> map = new LinkedHashMap<>(9);
            map.put("EXCHARGE_CODE", list.get(0));
            map.put("TRADE_DATE", StringUtils.isNotBlank(list.get(1))?list.get(1).replaceAll("/","-"):null);
            map.put("OPEN_PRICE", list.get(2));
            map.put("CLOSE_PRICE", list.get(3));
            map.put("MID_PRICE", list.get(4));
            map.put("HIGH_PRICE", list.get(5));
            map.put("LOW_PRICE", list.get(6));
            map.put("QUOT_UNIT", list.get(7));
            map.put("DATASOURCE_TYPE", StringUtils.isNotBlank(list.get(8))?list.get(8).split(":")[0]:null);
            mapList.add(map);
        }

        return mapList;

    }

    public List<Map<String,String>> dataMapAnalysis(String flieName)
            throws Exception {
        String prefix = CacheUtil.getSysParam(GlobalConstant.COM_PROP_UPLOAD_PATH);
        if(!StringUtil.startsWith(flieName, prefix)
                && !StringUtil.startsWith(flieName, prefix.replace("/", File.separator))) {
            flieName = prefix + File.separator + flieName;
        }
        com.joyintech.framework.util.ExcelUtil excelUtil = com.joyintech.framework.util.ExcelUtil.build(flieName, null);
        List<List<String>> dataList = excelUtil.readToList(0, 1, 0);
        List<Map<String,String>> list = new ArrayList<>();
        dataList.forEach(item->{
            Map<String,String> map = new HashMap<>(14);
            map.put("EXCHARGE_CODE", item.get(0));
            map.put("TRADE_DATE",item.get(1));
            map.put("OPEN_PRICE", item.get(2));
            map.put("CLOSE_PRICE", item.get(3));
            map.put("MID_PRICE", item.get(4));
            map.put("HIGH_PRICE", item.get(5));
            map.put("LOW_PRICE", item.get(6));
            map.put("QUOT_UNIT", item.get(7));
            map.put("DATASOURCE_TYPE", item.get(8).split(" ")[0]);
            try {
                map.put("BASIS_CYY",item.get(0).split("/")[0]);
                map.put("QUOT_CYY",item.get(0).split("/")[1]);
            } catch (Exception e) {
                logger.error("解析导入文件异常:{}", e.getMessage());
            }
            map.put("INPUT_TYPE", "");
            map.put("ERR_MSG", "");
            map.put("DATA_SOURCE", "D");
            list.add(map);
        });

        return list;
    }

    public List<ChbExchangeQuotationInfoDto> selectByKeys(List<ChbExchangeQuotationInfoDto> dtoList) {
        return chbExchangeQuotationInfoMapper.selectByKeys(dtoList);
    }

    public List<ChbExchangeQuotationInfoDto> selectAll(Map<String, Object> param) {
        return chbExchangeQuotationInfoMapper.select(param);
    }
}
