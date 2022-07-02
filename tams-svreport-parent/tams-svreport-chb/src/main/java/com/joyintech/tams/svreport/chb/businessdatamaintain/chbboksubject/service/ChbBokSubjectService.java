package com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.service;

import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.JoyinMessage;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExcelUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BokSubjectErrodataMapper;
import com.joyintech.tams.common.base.entity.BokSubjectErrodataWithBLOBs;
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dao.ChbBokSubjectDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbboksubject.dto.ChbBokSubjectDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbexchangeinfo.service.ChbExchangeQuotationInfoService;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import com.joyintech.tams.svreport.chb.common.util.DuplicateDataUtil;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import com.joyintech.tams.svreport.interf.dis.boksubject.service.BokSubjectInterfService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author wubingbing
 * @date 2022-03-28 14:39
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbBokSubjectService extends CommonExportService<ChbBokSubjectDto> implements BaseProcessService<ChbBokSubjectDto> {
    @Autowired
    private ChbBokSubjectDao bokSubjectDao;
    @Autowired
    private BaseCommonCheckService baseCommonCheckService;
    @Autowired
    private BokSubjectInterfService bokSubjectInterfService;
    @Autowired
    private BokSubjectErrodataMapper bokSubjectErrodataMapper;
    @Autowired
    private CommonCodeCheckBatchService commonCodeCheckBatchService;
    private final Logger logger = LoggerFactory.getLogger(ChbBokSubjectService.class);
    /**
     * 检索上级科目代码列表
     *
     * @param param 过滤参数
     * @return 上级科目代码列表
     * @see
     * @since 1.0
     */
    public List<SuggestDto> getFSubjectId(String param) {
        return queryForSuggest(bokSubjectDao::getFSubjectId, null, param);
    }

    /**
     * 获取科目文本输入框模糊查询 和 联动
     *
     * @return 上级科目代码列表
     * @throws Exception
     * @see
     * @since 1.0
     */
    public List<SelectOptionDto> getSubjectId2(Map<String, Object> map) {
        List<SelectOptionDto> list = bokSubjectDao.getSubjectId2(map);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(item -> {
                item.setId(item.getId());
                item.setText(item.getId() + "（" + item.getText() + "）");
            });
        }
        return list;
    }

    /**
     * 获取科目文本输入框模糊查询 和 联动
     *
     * @return 上级科目代码列表
     * @throws Exception
     * @see
     * @since 1.0
     */
    public List<SelectOptionDto> getSubjectLevel(Map<String, Object> map)  {
        return bokSubjectDao.getSubjectLevel(map);
    }



    /**
     * 检索科目列表
     *
     * @param pageParam 过滤参数
     * @return 科目列表
     * @throws Exception
     * @see
     * @since 1.0
     */
    public Page<ChbBokSubjectDto> selectForPage(Map<String, Object> pageParam) {
        Page<ChbBokSubjectDto> page = queryForPage(Integer.parseInt((String) pageParam.get("page")), Integer.parseInt((String) pageParam.get("length")), () -> bokSubjectDao.selectAll(pageParam));
        return page;
    }



    public List<SelectOptionDto> getSubjectName(Map<String, Object> pageParam) {
        List<SelectOptionDto> list = bokSubjectDao.getSubjectName(pageParam);
        return list;
    }
    protected List<SuggestDto> queryForSuggest(Supplier<List<SuggestDto>> selector, String code, String name) {
        return this.queryForSuggest(selector, (item) -> {
            if (StringUtil.isNotEmpty(code) && item.getCode().toLowerCase().indexOf(code.trim().toLowerCase()) < 0) {
                return false;
            } else {
                return !StringUtil.isNotEmpty(name) || item.getCode().toLowerCase().indexOf(name) >= 0 || item.getCode().toLowerCase().indexOf(name.trim().toLowerCase()) >= 0;
            }
        });
    }

    /**
     *判断科目号是否存在
     * @param subjectNo
     * @return
     */
    public int isExists(String booksetId,String subjectNo) {
        return bokSubjectDao.isExists(booksetId,subjectNo);
    }


    /**
     * 工作流（留痕操作结束的时候执行的方法）
     */
    @Override
    public void doComplete(ChbBokSubjectDto entity, OperationType type) {
        if (OperationType.DELETE.equals(type)) {
            if(entity.getStatusValid().equals(DictCodeListExt1.维护数据状态_待处理.getValue())){
                bokSubjectErrodataMapper.deleteByPrimaryKey(entity.getDatauuid());
            }else{
                entity.setStatus(DictCodeListExt1.维护数据状态_删除.getValue());
                bokSubjectDao.update(entity);
            }

        }
        else if (OperationType.NEW.equals(type)) {
            entity.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            entity.setBooksetId("00000000000000000002");
            BokSubjectErrodataWithBLOBs bokSubjectErrodataWithBLOBs = new BokSubjectErrodataWithBLOBs();
            bokSubjectErrodataWithBLOBs.setBooksetId(entity.getBooksetId());
            bokSubjectErrodataWithBLOBs.setSubjectNo(entity.getSubjectNo());
            commonCodeCheckBatchService.deleteBokSubjectErrodata( Lists.newArrayList(bokSubjectErrodataWithBLOBs));
            bokSubjectDao.insert(entity);
        }
        else if(OperationType.MODIFY.equals(type)){
            if(entity.getStatusValid().equals(DictCodeListExt1.维护数据状态_待处理.getValue())){
                bokSubjectErrodataMapper.deleteByPrimaryKey(entity.getDatauuid());
                entity.setDataSource(DictCodeListExt2.数据来源_接口.getValue());
            }
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            bokSubjectDao.insert(entity);
        }
    }


    public ChbBokSubjectDto selcetByKey(String subjectNo,String booksetId,String statusValid,String datauuid){
        ChbBokSubjectDto dto = new ChbBokSubjectDto();
        if(StringUtils.isNotEmpty(statusValid)&&statusValid.equals(DictCodeListExt1.维护数据状态_待处理.getValue())){
            BokSubjectErrodataWithBLOBs bokSubjectErrodataWithBLOBs = bokSubjectErrodataMapper.selectByPrimaryKey(datauuid);
            if(bokSubjectErrodataWithBLOBs!=null){
                dto = ErrdataConvertUtil.errdataConvert(dto.getClass(),bokSubjectErrodataWithBLOBs.getOrgData());
                dto.setStatusValid(DictCodeListExt3.中债数据状态_待处理.getValue());
                dto.setDatauuid(datauuid);
            }
        }else{
            dto = bokSubjectDao.selcetByKey(subjectNo,booksetId);
        }
        return dto;
    }

    /**
     * excel导入功能
     *
     * @param fileName 文件名称
     * @return 检验list
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public List<ExcelValidDto> importData(String fileName,String tableName)
            throws Exception {
        // 检查文件名是否为空
        if (StringUtil.isNotEmpty(fileName)) {
            if (fileName.indexOf("科目定义导入") != -1) {
                List<List<String>> dataList = dataAnalysis(fileName);
                if (null != dataList && dataList.size() > 0) {
                    // 将excel表格中的数据转化为dto
                    List<ExcelValidDto> checkResult = checkExcelData(dataList);
//                    if (checkResult.isEmpty()) {
//                        List<ComBondQuotationInfoDto> equityList = converToBean(dataList);
//                        // 保存，已存在则更新
//                        dealAfterCheck(equityList);
//                    }
                    return checkResult;
                }
                 /* List<Map<String, String>> mapList = dataMapAnalysis(fileName);
                if (null != mapList && mapList.size() > 0) {
                    List<ExcelValidDto> checkResult = baseCommonCheckService.checkDataDB("import", mapList, tableName, fileName, tableName, tableName, null, null);
                    ExcelValidDto dto = new ExcelValidDto(StringUtil.EMPTY, "3", "" + "内容为空");
                    checkResult.add(dto);
                    return checkResult;
                }*/else{
                    List<ExcelValidDto> checkResult = new ArrayList<>();
                    checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                            "文件为空"));
                    return checkResult;
                }
            }else {
                List<ExcelValidDto> checkResult = new ArrayList<>();
                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                        "文件名称错误，请下载模板后上传"));
                return checkResult;
            }
        }
        return null;
    }


    /**
     * excel导入数据整理
     *
     * @param flieName 文件名称
     * @return 检验list
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    public List<List<String>> dataAnalysis(String flieName)
            throws Exception {
        String prefix = CacheUtil.getSysParam(GlobalConstant.COM_PROP_UPLOAD_PATH);
        if(!StringUtil.startsWith(flieName, prefix)
                && !StringUtil.startsWith(flieName, prefix.replace("/", File.separator))) {
            flieName = prefix + File.separator + flieName;
        }
        ExcelUtil excelUtil = ExcelUtil.build(flieName, null);
        List<List<String>> dataList = excelUtil.readToList(0, 1, 0);
        return dataList;
    }
    /**
     * excel导入数据整理
     *
     * @param flieName 文件名称
     * @return 检验list
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    public List<Map<String,String>> dataMapAnalysis(String flieName)
            throws Exception {
        String prefix = CacheUtil.getSysParam(GlobalConstant.COM_PROP_UPLOAD_PATH);
        if(!StringUtil.startsWith(flieName, prefix)
                && !StringUtil.startsWith(flieName, prefix.replace("/", File.separator))) {
            flieName = prefix + File.separator + flieName;
        }
        ExcelUtil excelUtil = ExcelUtil.build(flieName, null);
        List<List<String>> dataList = excelUtil.readToList(0, 1, 0);
        List<Map<String,String>> list = new ArrayList<>();
        dataList.forEach(item->{
            Map<String,String> map = new HashMap<>();
            map.put("BOOKSET_ID","00000000000000000002");
            map.put("SUBJECT_NO",item.get(0));
            map.put("SUBJECT_NAME",item.get(1));
            map.put("FSUBJECT_ID",item.get(2));
            map.put("SUBJECT_TYPE",item.get(3).split(" ")[0]);
            map.put("SUBJECT_LEVEL",item.get(4).split(" ")[0]);
            map.put("BAL_FLAG",item.get(5).split(" ")[0]);
            map.put("REMARK",item.get(6));
            list.add(map);
        });

        return list;
    }
    /**
     * 导入文件重复数据检查 <功能详细描述>
     *
     * @param dataList 导入数据
     * @return 返回检查结果
     * @throws ParseException 抛出异常
     * @see
     * @since 1.0
     */
    public List<Integer> duplicateCheck(List<List<String>> dataList)
            throws ParseException {
        List<ExcelValidDto> checkResult = checkExcelData(dataList);
        if(CollectionUtils.isNotEmpty(checkResult)){
            return new ArrayList<>();
        }
        List<ChbBokSubjectDto> equityList = converToBean(dataList,null);
        List<Integer> duplicateData=new ArrayList<Integer>();
        for (int i = 0; i < dataList.size(); i++ ) {
            ChbBokSubjectDto dto = equityList.get(i);
            if(dto.getSubjectNo() == null
                    || dto.getSubjectName() == null
                    || dto.getFsubjectId() == null
                    || dto.getSubjectType() == null
                    || dto.getSubjectLevel() == null
                    || duplicateData.contains(i)){
                continue;
            }
            // 检查表内数据是否有主键重复
            for (int k = i + 1; k < dataList.size(); k++ ) {
                if(equityList.get(k).getSubjectNo() == null){
                    continue;
                }
                if (equityList.get(k).getSubjectNo().equals(dto.getSubjectNo())) {
                    if(!duplicateData.contains(k)){
                        duplicateData.add(k);
                    }
                }
            }
        }
        if(duplicateData.isEmpty()){
            duplicateData.add(-1);
        }
        return duplicateData;
    }

    /**
     * 导入文件内容检查 <功能详细描述>
     *
     * @param dataList 导入数据
     * @return 返回检查结果
     * @throws ParseException 抛出异常
     * @see
     * @since 1.0
     */
    private List<ExcelValidDto> checkExcelData(List<List<String>> dataList)
            throws ParseException {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (int i = 0; i < dataList.size(); i++ ) {
                List<ExcelValidDto> result = checkExcelCow(i, dataList.get(i));
                // 检查表内数据每行是否合法
                if (CollectionUtils.isNotEmpty(result)) {
                    checkResult.addAll(result);
                }
            }
        }
        if (checkResult.size()>0){
            return checkResult;
        }
        List<ChbBokSubjectDto> equityList = converToBean(dataList,null);
        ChbBokSubjectDto bokSubjectDto = null;
        List<Integer> duplicateDataList=new ArrayList<Integer>();

        if (equityList.size() >= 1) {
            for (int i = 0; i < equityList.size(); i++ ) {
                bokSubjectDto = equityList.get(i);
                // 检查表内数据是否有主键重复
                if (equityList.size() >= 2) {
                    for (int k = i + 1; k < equityList.size(); k++ ) {
                        if (equityList.get(k).getSubjectNo().equals(bokSubjectDto.getSubjectNo())) {
                            if(!duplicateDataList.contains(k)){
                                duplicateDataList.add(k);
                                checkResult.add(new ExcelValidDto(dataList.get(k).get(1),
                                        String.valueOf(k + 2), "表中数据重复"));
                            }
                        }
                    }
                }
            }
        }
        return checkResult;
    }
    /**
     * 检查excel是否为空或者传入列是否正确
     *
     * @param i 第几行
     * @param list 每一列解析后的List
     * @return 检查结构
     * @see
     * @since 1.0
     */
    private List<ExcelValidDto> checkExcelCow(int i, List<String> list) {
        List<ExcelValidDto> excelValidDtolist = new ArrayList<ExcelValidDto>();
        // 列数为15
        if (null == list || list.size() != 6) {
            ExcelValidDto dto = new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i), "数据列数错误");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(0))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "科目号为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(1))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "科目名称为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(2))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "上级科目号为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(3))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "科目类型为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(4))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "科目级别为空");
            excelValidDtolist.add(dto);
        }
        return excelValidDtolist;
    }
    /**
     * list转换为Bean
     *
     * @param dataList excel解析后的list
     * @return List<mstMarketCalendarInfo001Dto>
     * @throws ParseException 抛出异常
     * @see
     * @since 1.0
     */
    public List<ChbBokSubjectDto> converToBean(List<List<String>> dataList, List<Integer> checkResult)
            throws ParseException {
        // 返回的list定义
        List<ChbBokSubjectDto> returnInfoList = new ArrayList<ChbBokSubjectDto>();
        ChbBokSubjectDto bokSubjectDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            if(null !=checkResult && checkResult.contains(i)){
                continue;
            }
            List<String> list = dataList.get(i);
            bokSubjectDto = new ChbBokSubjectDto();
            if (StringUtil.isNotEmpty(list.get(0))) {
                bokSubjectDto.setSubjectNo(list.get(0).trim());
            }
            if (StringUtil.isNotEmpty(list.get(1))) {
                bokSubjectDto.setSubjectName(list.get(1).trim());
            }
            if (StringUtil.isNotEmpty(list.get(2))) {
                bokSubjectDto.setFsubjectId(list.get(2).trim());
            }
            if (StringUtil.isNotEmpty(list.get(3))) {
                bokSubjectDto.setSubjectType(list.get(3).trim());
            }
            if (StringUtil.isNotEmpty(list.get(4))) {
                bokSubjectDto.setSubjectLevel(1L);
            }
            if (StringUtil.isNotEmpty(list.get(5))) {
                bokSubjectDto.setRemark(list.get(5).trim());
            }
            bokSubjectDto.setCreateUser(CommonUtil.getUserName());
            bokSubjectDto.setCreateDept(CommonUtil.getDeptCode());
            bokSubjectDto.setCreateTime(DateUtil.now());
            bokSubjectDto.setUpdateUser(CommonUtil.getUserName());
            bokSubjectDto.setUpdateTime(DateUtil.now());
            bokSubjectDto.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            bokSubjectDto.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            returnInfoList.add(bokSubjectDto);
        }
        return returnInfoList;
    }
    /**
     * check后的保存
     *
     * @param list 需要保存的数据
     * @see
     * @since 1.0
     */
    public void dealAfterCheck(List<ChbBokSubjectDto> list) {
        list.forEach(item->{
            item.setBooksetId("00000000000000000002");
            item.setSubjectType("0"+item.getSubjectType().substring(0,1));
            item.setCreateDept(null==item.getCreateDept()?"":item.getCreateDept());
        });
        bokSubjectDao.batchInsert(list);
    }

    public List<ChbBokSubjectDto> selectAllList(Map<String, Object> params) {
        List<ChbBokSubjectDto> list = bokSubjectDao.selectAll(params);
        return list;
    }

    public List<ExcelValidDto> checkFile(String fileName) throws Exception{
        // 检查文件名是否为空
        if (StringUtil.isNotEmpty(fileName)) {
            if (fileName.indexOf("科目定义导入") != -1) {
                List<Map<String, String>> dataList = dataMapAnalysis(fileName);
                if (null != dataList && dataList.size() > 0) {
                  //todo 共通check

                }else{
                    List<ExcelValidDto> checkResult = new ArrayList<>();
                    checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                            "文件为空"));
                    return checkResult;
                }
            }else {
                List<ExcelValidDto> checkResult = new ArrayList<>();
                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                        "文件名称错误，请下载模板后上传"));
                return checkResult;
            }
        }
        return null;
    }

    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName,String tableName) throws Exception  {

        AjaxResult webResult = new AjaxResult();
        // 检查文件名是否包含汇率行情信息
        if (!fileName.contains("科目定义")) {
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
        int i = bokSubjectDao.countDuplicatedData(list);
        if(list.size()>0){
            String[] checkEuplicatedKeys ={"BOOKSET_ID","SUBJECT_NO"};
            int dup = DuplicateDataUtil.countDuplicatedData(list, checkEuplicatedKeys);
            webResult.setMessage(this.getMessage("SvrDuplicateMessage",dup,i), MessageType.确认消息);
        }
        return webResult;
    }

    /**
     *
     * 导入数据与数据库主键校验
     * @param fileName
     * @return
     * @throws Exception
     */
    public AjaxResult checkDuplicateData(String fileName) throws Exception {
        AjaxResult webResult = new AjaxResult();
        List<Map<String, String>> mapList = dataMapAnalysis(fileName);
//        bokSubjectDao.checkDuplicateData(mapList);
        return webResult;
    }


    public List<ExcelValidDto> importFileSave(String fileName,String tableName) throws Exception {

        List<Map<String, String>> list = Lists.newArrayList();
        try {
            list = dataMapAnalysis(fileName);
        } catch (Exception e) {
            logger.error("解析导入文件异常:{}", e.getMessage());
        }
        List<ExcelValidDto> listExcel = new ArrayList<ExcelValidDto>();
        bokSubjectInterfService.importDB(listExcel,list,new InterfFile(),null);
        return listExcel;
    }
    public List<ChbBokSubjectDto> converToBean(List<Map<String,String>> dataList)
            throws ParseException {
        // 返回的list定义
        List<ChbBokSubjectDto> returnInfoList = new ArrayList<ChbBokSubjectDto>();
        for (Map<String, String> map : dataList) {
            ChbBokSubjectDto bokSubjectDto = new ChbBokSubjectDto();
            bokSubjectDto.setBooksetId(map.get("BOOKSET_ID"));
            if (StringUtil.isNotEmpty(map.get("SUBJECT_NO"))) {
                bokSubjectDto.setSubjectNo(map.get("SUBJECT_NO"));
            }
            if (StringUtil.isNotEmpty(map.get("SUBJECT_NAME"))) {
                bokSubjectDto.setSubjectName(map.get("SUBJECT_NAME"));
            }
            if (StringUtil.isNotEmpty(map.get("FSUBJECT_ID"))) {
                bokSubjectDto.setFsubjectId(map.get("FSUBJECT_ID"));
            }
            if (StringUtil.isNotEmpty(map.get("SUBJECT_TYPE"))) {
                bokSubjectDto.setSubjectType(map.get("SUBJECT_TYPE"));
            }
            if (StringUtil.isNotEmpty(map.get("SUBJECT_LEVEL"))) {
                bokSubjectDto.setSubjectLevel(Long.valueOf(map.get("SUBJECT_LEVEL")));
            }
            if (StringUtil.isNotEmpty(map.get("BAL_FLAG"))) {
                bokSubjectDto.setBalFlag(map.get("BAL_FLAG"));
            }
            if (StringUtil.isNotEmpty(map.get("REMARK"))) {
                bokSubjectDto.setRemark(map.get("REMARK"));
            }

            bokSubjectDto.setErrMsg(null!=map.get("ERRMSG")?map.get("ERRMSG"):"");
            bokSubjectDto.setCreateUser(CommonUtil.getUserName());
            bokSubjectDto.setCreateDept(CommonUtil.getDeptCode()==null?"":CommonUtil.getDeptCode());
            bokSubjectDto.setCreateTime(DateUtil.now());
            bokSubjectDto.setUpdateUser(CommonUtil.getUserName());
            bokSubjectDto.setUpdateTime(DateUtil.now());
            bokSubjectDto.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            bokSubjectDto.setStatus(null!=map.get("STATUS") && map.get("STATUS").equals("A")?DictCodeListExt1.维护数据状态_待处理.getValue():DictCodeListExt1.维护数据状态_有效.getValue());
            returnInfoList.add(bokSubjectDto);

        }
        return returnInfoList;
    }

    public List<ChbBokSubjectDto> selectByKeys(List<ChbBokSubjectDto> dtoList) {
        return bokSubjectDao.selectByKeys(dtoList);
    }

    public List<ChbBokSubjectDto> selectAll(Map<String, Object> params) {
        return bokSubjectDao.selectAll(params);
    }
}
