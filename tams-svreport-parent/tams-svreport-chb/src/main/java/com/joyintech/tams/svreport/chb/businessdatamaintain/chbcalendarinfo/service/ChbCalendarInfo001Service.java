/*
 * 文件名：mstMarketCalendarInfo001Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2018年8月3日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.service;

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
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dao.ChbCalendarInfoMapper;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto.ChbCalendarInfo001Dto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbcalendarinfo.dto.ChbCalendarInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.svreport.chb.common.util.DuplicateDataUtil;
import com.joyintech.tams.svreport.chb.common.util.ExcelUtil;
import com.joyintech.tams.svreport.interf.chb.baseinfo.service.SvrHolidayFileImpService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;


/**
 * 市场日历信息service层 〈功能详细描述〉
 *
 * @author xiexinting
 * @version 1.0 2018年8月20日
 * @see ChbCalendarInfo001Service
 * @since 1.0
 */
@Service
public class ChbCalendarInfo001Service extends CommonExportService<ChbCalendarInfo001Dto> {

    public static String CONFIG_FILE = "/com/joyintech/tams/svreport/chb/businessdatamaintain/chbcalendarinfo/conf/export_conf.xml";
    private final Logger logger = LoggerFactory.getLogger(ChbCalendarInfo001Service.class);
    @Autowired
    private SvrHolidayFileImpService svrHolidayFileImpService;

    /**
     * 表名
     */
    public static String TABLE_NAME = "MST_HOLIDAY";

    /**
     * 接口类型
     */
    public static final String CHECK_FLAG = "D";

    /**
     * 分页大小
     */
    public static final int PAGE_SIZE = 200;

    @Resource
    private ChbCalendarInfoMapper chbCalendarInfoMapper;
    @Resource
    private BaseCommonCheckService baseCommonCheckService;
    @Resource
    private BatchInsertService batchInsertService;

    /**
     * 市场日历信息查询
     *
     * @param param 查询参数
     * @return 查询结果
     */
    public Page<ChbCalendarInfo001Dto> selectInfo(Map<String, Object> param) {
        if (Objects.equals(param.get("ishDay"), DictCodeList.是否_是.getValue())) {
            param.put("ishDay", 1);
        } else if (StringUtil.equals(param.get("ishDay"), DictCodeList.是否_否.getValue())) {
            param.put("ishDay", 0);
        }
        String calendarType = (String) param.get("calendarType");
        if (StringUtil.isNotEmpty(calendarType)) {
            param.put("calendarTypeList", StringUtil.split(calendarType, GlobalConstant.COMMA));
        }
        Page<ChbCalendarInfo001Dto> pageList = super.queryForPage(Integer.parseInt(param.get("page").toString()), Integer.parseInt(param.get("length").toString()), () -> chbCalendarInfoMapper.selectInfo(param));
        return pageList;
    }

    /**
     * 市场日历类型下拉框查询
     *
     * @param map 参数
     * @return 返回结果
     */
    public List<ChbCalendarInfoDto> selectCalendarInfo(Map<String, Object> map) {
        map.put("no", DictCodeList.是否_否.getValue());
        return chbCalendarInfoMapper.selectCalendarInfo(map);
    }

    /**
     * 节假日和工作日更新
     *
     * @param dto
     * @return
     */
    public AjaxResult updateIsHoliday(ChbCalendarInfoDto dto) {
        Date sysDate = DateUtil.platDateFromCache();
        List<ChbCalendarInfo001Dto> list = dto.getList();
        //判断日期是否小于平台日
        for (ChbCalendarInfo001Dto cDto : list) {
            if (cDto.getCdate().before(sysDate)) {
                AjaxResult aj = new AjaxResult();
                List<ValidMessage> errorList = new ArrayList<>();
                errorList.add(new ValidMessage(getMessage("MST_INFO_E_0003")));
                aj.setErrorList(errorList);
                return aj;
            }
            if (Objects.equals(dto.getIsHoliday(),"0")) {
                cDto.setIsHoliday(0L);
            }else {
                cDto.setIsHoliday(1L);
            }
            if (Objects.equals(dto.getStatus(),DictCodeListExt2.数据状态_待处理.getValue())) {
                cDto.setStatus(DictCodeListExt2.数据状态_有效.getValue());
            }

        }
        try {
            chbCalendarInfoMapper.updateBatchCalendarInfo(list);
        } catch (Exception e) {
            logger.error("数据更新异常:{}", e.getMessage());
            throw new RuntimeException("操作异常!");
        }

        return new AjaxResult(getMessage("COMMON_I_0011"));
    }


    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName)  {

        AjaxResult webResult = new AjaxResult();
        // 检查文件名是否包含汇率行情信息
        if (!fileName.contains("节假日信息")) {
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
        int i = chbCalendarInfoMapper.countDuplicatedData(list);
        if(i>0){
            String[] checkEuplicatedKeys ={"CALENDAR_ID","CDATE"};
            int dup = DuplicateDataUtil.countDuplicatedData(list, checkEuplicatedKeys);
            webResult.setMessage(this.getMessage("SvrDuplicateMessage",dup,i), MessageType.确认消息);
        }
        return webResult;
    }


    /**
     * excel导入功能
     *
     * @param fileName 文件名称
     * @return 检验list
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ExcelValidDto> importData(String fileName) throws FileNotFoundException, ParseException {
        List<Map<String, String>> list = Lists.newArrayList();
        try {
            list = dataMapAnalysis(fileName);
        } catch (Exception e) {
            logger.error("解析导入文件异常:{}", e.getMessage());
        }
        List<ExcelValidDto> listExcel = new ArrayList<ExcelValidDto>();
        svrHolidayFileImpService.importDB(listExcel,list,new InterfFile(),null);
        return listExcel;
    }


    /**
     * 增强check list转换为Bean
     *
     * @return 返回检查结果
     */
    private List<ChbCalendarInfo001Dto> convertToBean(List<Map<String, String>> mapList) {


        List<ChbCalendarInfo001Dto> returnList = new LinkedList<>();
        ChbCalendarInfo001Dto dto = null;
        for (Map<String, String> map : mapList) {
            dto = new ChbCalendarInfo001Dto();

            if (map.containsKey("CALENDAR_ID")) {
                dto.setCalendarId(map.get("CALENDAR_ID"));
            }
            if (map.containsKey("CDATE")) {
                dto.setCdate(DateUtil.parse(map.get("CDATE").replaceAll("/", "-")));
            }
            if (map.containsKey("IS_HOLIDAY")) {
                dto.setIsHoliday(StringUtils.isNotBlank(map.get("IS_HOLIDAY")) ? Long.parseLong(map.get("IS_HOLIDAY")) : null);
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
            Map<String, String> map = new LinkedHashMap<>(3);
            map.put("CDATE", list.get(0));
            map.put("CALENDAR_ID", StringUtil.isNotBlank(list.get(1)) ? list.get(1).split(" ")[0] : "");
            map.put("IS_HOLIDAY", StringUtil.isNotBlank(list.get(2)) ? list.get(2).split(" ")[0] : "");
            mapList.add(map);
        }
        return mapList;

    }


    /**
     * 导出
     */
    @Override
    protected void buildExportParam(ExportParam exportParam) {
        exportParam.config(CONFIG_FILE).dataSource(DataSourceType.MASERT);
    }

    /**
     * 给单元格设定值
     */
    @Override
    public void setCellValue(String beanName, Cell cell, String propName, Object value) {
        if (StringUtil.equals(propName, "cdate")) {
            cell.setCellValue(DateUtil.format((Date) value));
        }
    }

    /**
     * 导出 {@inheritDoc}
     */
    @Override
    public Page<ChbCalendarInfo001Dto> selectPageData(Map<String, Object> selectParam) {
        return selectInfo(selectParam);
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
            Map<String,String> map = new HashMap<>();
            map.put("CDATE",item.get(0));
            map.put("CALENDAR_ID",item.get(1).split(" ")[0]);
            map.put("IS_HOLIDAY",item.get(2).split(" ")[0]);
            list.add(map);
        });

        return list;
    }

    public List<ChbCalendarInfo001Dto> selectByKeys(List<ChbCalendarInfo001Dto> dtoList) {
        return chbCalendarInfoMapper.selectByKeys(dtoList);
    }

    public List<ChbCalendarInfo001Dto> selectAll(Map<String, Object> params) {
        return chbCalendarInfoMapper.selectInfo(params);
    }
}
