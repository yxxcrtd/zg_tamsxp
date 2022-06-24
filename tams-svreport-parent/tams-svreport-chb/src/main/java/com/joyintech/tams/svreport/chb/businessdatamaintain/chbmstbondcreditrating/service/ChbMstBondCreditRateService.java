package com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.service;

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
import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dao.ChbMstBondCreditRatingDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbmstbondcreditrating.dto.ChbMstBondCreditRatingDto;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wubingbing
 * @date 2022-04-01 14:59
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbMstBondCreditRateService extends CommonExportService<ChbMstBondCreditRatingDto> implements BaseProcessService<ChbMstBondCreditRatingDto> {
    @Autowired
    private ChbMstBondCreditRatingDao mstBondCreditRatingDao;
    @Autowired
    private BaseCommonCheckService baseCommonCheckService;
    @Override
    public void doComplete(ChbMstBondCreditRatingDto entity, OperationType type) {
        if (OperationType.DELETE.equals(type)) {
            entity.setStatus(DictCodeListExt1.维护数据状态_删除.getValue());
            mstBondCreditRatingDao.update(entity);

        }
        else if (OperationType.NEW.equals(type)) {
            entity.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            entity.setErrMsg("");
            mstBondCreditRatingDao.insert(entity);
        }
        else if(OperationType.MODIFY.equals(type)){
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            mstBondCreditRatingDao.update(entity);
        }

    }


    /**
     * 资产代码下拉框
     * @return 产品名称列表
     */
    public List<SuggestDto> getFinProdAbbr(String param) {


        return mstBondCreditRatingDao.getFinProdAbbr(param);
    }
    public ChbMstBondCreditRatingDto selcetByKey(String oCode,  String gradeOrgId, Date gradeDate,String oType) {
        return mstBondCreditRatingDao.selcetByKey(oCode,gradeOrgId,gradeDate,oType);
    }

    public Page<ChbMstBondCreditRatingDto> selectForPage(Map<String, Object> pageParam) {
        pageParam.put("oCodes", StringUtil.isNotEmpty(pageParam.get("oCode"))?(pageParam.get("oCode").toString().split(",")):null);

        Page<ChbMstBondCreditRatingDto> page = queryForPage(Integer.parseInt((String) pageParam.get("page")), Integer.parseInt((String) pageParam.get("length")), () -> mstBondCreditRatingDao.selectAll(pageParam));
        return page;
    }
    @Transactional(rollbackFor = Exception.class)
    public List<ExcelValidDto> importData(String fileName) throws Exception{
        // 检查文件名是否为空
        if (StringUtil.isNotEmpty(fileName)) {
            if (fileName.indexOf("债券评级") != -1) {
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
                }else{
                    List<ExcelValidDto> checkResult = new ArrayList<>();
                    checkResult.add(new ExcelValidDto(StringUtil.EMPTY, StringUtil.EMPTY,
                            "文件为空"));
                    return checkResult;
                }
            }
            else {
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
        List<ChbMstBondCreditRatingDto> equityList = converToBean(dataList,null);
        ChbMstBondCreditRatingDto mstBondCreditRatingDto = null;
        List<Integer> duplicateDataList=new ArrayList<Integer>();

        if (equityList.size() >= 1) {
            for (int i = 0; i < equityList.size(); i++ ) {
                mstBondCreditRatingDto = equityList.get(i);
                // 检查表内数据是否有主键重复
                if (equityList.size() >= 2) {
                    for (int k = i + 1; k < equityList.size(); k++ ) {
                        if (equityList.get(k).getoCode().equals(mstBondCreditRatingDto.getoCode())
                                && equityList.get(k).getGradeOrgId().equals(mstBondCreditRatingDto.getGradeOrgId())
                                && equityList.get(k).getGradeDate().equals(mstBondCreditRatingDto.getGradeDate())
                                && equityList.get(k).getoType().equals(mstBondCreditRatingDto.getoType())
                        ) {
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
        if (null == list || list.size() != 7) {
            ExcelValidDto dto = new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i), "数据列数错误");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(0))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "债券代码为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(1))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "评级对象为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(2))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "评级机构为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(3))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "评级类型为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(4))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "评级结果为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(5))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "评级来源为空");
            excelValidDtolist.add(dto);
        }
        if (StringUtil.isEmpty(list.get(6))) {
            ExcelValidDto dto = new ExcelValidDto(list.get(1), String.valueOf(i+2), "评级日期为空");
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
    public List<ChbMstBondCreditRatingDto> converToBean(List<List<String>> dataList, List<Integer> checkResult)
            throws ParseException {
        // 返回的list定义
        List<ChbMstBondCreditRatingDto> returnInfoList = new ArrayList<ChbMstBondCreditRatingDto>();
        ChbMstBondCreditRatingDto mstBondCreditRatingDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            if(null !=checkResult && checkResult.contains(i)){
                continue;
            }
            List<String> list = dataList.get(i);
            mstBondCreditRatingDto = new ChbMstBondCreditRatingDto();
            if (StringUtil.isNotEmpty(list.get(0))) {
                mstBondCreditRatingDto.setoCode(list.get(0).trim());
            }

            if (StringUtil.isNotEmpty(list.get(1))) {
                mstBondCreditRatingDto.setoType(list.get(1).trim());
            }
            if (StringUtil.isNotEmpty(list.get(2))) {
                mstBondCreditRatingDto.setGradeOrgId(list.get(2).trim());
            }
            if (StringUtil.isNotEmpty(list.get(3))) {
                mstBondCreditRatingDto.setShortLongTerm(list.get(3).trim());
            }
            if (StringUtil.isNotEmpty(list.get(4))) {
                mstBondCreditRatingDto.setGradeResult(list.get(4).trim());
            }
            if (StringUtil.isNotEmpty(list.get(5))) {
                mstBondCreditRatingDto.setSource(list.get(5).trim());
            }
            if (StringUtil.isNotEmpty(list.get(6))) {
                mstBondCreditRatingDto.setGradeDate(DateUtil.parse(list.get(6).trim()));
            }

            mstBondCreditRatingDto.setCreateUser(CommonUtil.getUserName());
            mstBondCreditRatingDto.setCreateDept(CommonUtil.getDeptCode()==null?"":CommonUtil.getDeptCode());
            mstBondCreditRatingDto.setCreateTime(DateUtil.now());
            mstBondCreditRatingDto.setUpdateUser(CommonUtil.getUserName());
            mstBondCreditRatingDto.setUpdateTime(DateUtil.now());
            mstBondCreditRatingDto.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            mstBondCreditRatingDto.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            returnInfoList.add(mstBondCreditRatingDto);
        }
        return returnInfoList;
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
        List<ChbMstBondCreditRatingDto> equityList = converToBean(dataList,null);
        List<Integer> duplicateData=new ArrayList<Integer>();
        for (int i = 0; i < dataList.size(); i++ ) {
            ChbMstBondCreditRatingDto dto = equityList.get(i);
            if(dto.getoCode() == null
                    || dto.getoType() == null
                    || dto.getGradeOrgId() == null
                    || dto.getGradeResult()== null
                    || dto.getShortLongTerm()== null
                    || dto.getSource()== null
                    || duplicateData.contains(i)){
                continue;
            }
            // 检查表内数据是否有主键重复
            for (int k = i + 1; k < dataList.size(); k++ ) {
                if(equityList.get(k).getoCode() == null){
                    continue;
                }
                if (equityList.get(k).getoCode().equals(dto.getoCode())
                    && equityList.get(k).getGradeOrgId().equals(dto.getGradeOrgId())
                    && equityList.get(k).getGradeDate().equals(dto.getGradeDate())
                    && equityList.get(k).getoType().equals(dto.getoType())
                    ) {
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
     * check后的保存
     *
     * @param list 需要保存的数据
     * @see
     * @since 1.0
     */
    public void dealAfterCheck(List<ChbMstBondCreditRatingDto> list) {



        list.forEach(ele->{
            ele.setGradeOrgId(ele.getGradeOrgId().substring(0,2));
            ele.setShortLongTerm(ele.getShortLongTerm().substring(0,2));
            ele.setGradeResult(ele.getGradeResult().substring(0,2));
            ele.setSource(ele.getSource().substring(0,2));
            ele.setoType(ele.getoType().substring(0,2));
        });

        mstBondCreditRatingDao.batchInsert(list);
    }

    public List<ChbMstBondCreditRatingDto> selectAll(Map<String, Object> params) {
        List<ChbMstBondCreditRatingDto> list = mstBondCreditRatingDao.selectAll(params);
        return list;
    }

    public List<SelectOptionDto> getMutliCode(Map<String, Object> map) {
        return mstBondCreditRatingDao.getMutliCode(map);
    }
    /**
     * 导入数据文件之前检查数据
     *
     * @param fileName 导入的文件名
     * @return 处理结果
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName, String tableName) throws Exception  {
        AjaxResult webResult = new AjaxResult();
        if (fileName.indexOf("债券评级") == -1){
            webResult.setMessage(this.getMessage("ImportFileName"),MessageType.错误消息);
            return webResult;
        }
        List<Map<String, String>> mapList = dataMapAnalysis(fileName);
        int size = mapList.size();
        List<ExcelValidDto> checkResult = baseCommonCheckService.checkDataAll("D", mapList, tableName, fileName, tableName, tableName, null, null);
        long count = mapList.stream().filter(ele ->null!= ele.get("STATUS")&&ele.get("STATUS").equals("A")).count();
        webResult.setMessage(this.getMessage("ChbImportMessage",mapList.size()-count,size-mapList.size(),count),MessageType.确认消息);
        //查数据库记录
        int duplicate = 0;
        if(!CollectionUtils.isEmpty(mapList)){
            duplicate = mstBondCreditRatingDao.selectValidRecords(mapList);
        }
        List<JoyinMessage> joyinMessages = new ArrayList<>();
        JoyinMessage ms = new JoyinMessage(this.getMessage("ChbImportDuplicateMessage",duplicate), MessageType.确认消息);
        joyinMessages.add(ms);
        webResult.setJoyinMessages(joyinMessages);
        return webResult;
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
            map.put("O_CODE",item.get(0));
            map.put("O_TYPE",null!=item.get(1)&&item.get(1).length()>1?item.get(1).substring(0,2):"");
            map.put("GRADE_ORG_ID",null!=item.get(2)&&item.get(2).length()>1?item.get(2).substring(0,2):"");
            map.put("SHORT_LONG_TERM",null!=item.get(3)&&item.get(3).length()>1?item.get(3).substring(0,2):"");
            map.put("GRADE_RESULT",null!=item.get(4)&&item.get(4).length()>1?item.get(4).substring(0,2):"");
            map.put("SOURCE",null!=item.get(5)&&item.get(5).length()>1?item.get(5).substring(0,2):"");
            map.put("GRADE_DATE",item.get(6));
            list.add(map);
        });

        return list;
    }
    public List<ChbMstBondCreditRatingDto> converToBean(List<Map<String,String>> list){
        // 返回的list定义
        List<ChbMstBondCreditRatingDto> returnInfoList = new ArrayList<ChbMstBondCreditRatingDto>();
        for (Map<String,String> map:list) {

            ChbMstBondCreditRatingDto mstBondCreditRatingDto = new ChbMstBondCreditRatingDto();
            if (StringUtil.isNotEmpty(map.get("O_CODE"))) {
                mstBondCreditRatingDto.setoCode(map.get("O_CODE"));
            }
            if (StringUtil.isNotEmpty(map.get("O_TYPE"))) {
                mstBondCreditRatingDto.setoType(map.get("O_TYPE"));
            }
            if (StringUtil.isNotEmpty(map.get("GRADE_ORG_ID"))) {
                mstBondCreditRatingDto.setGradeOrgId(map.get("GRADE_ORG_ID"));
            }
            if (StringUtil.isNotEmpty(map.get("SHORT_LONG_TERM"))) {
                mstBondCreditRatingDto.setShortLongTerm(map.get("SHORT_LONG_TERM"));
            }
            if (StringUtil.isNotEmpty(map.get("GRADE_RESULT"))) {
                mstBondCreditRatingDto.setGradeResult(map.get("GRADE_RESULT"));
            }
            if (StringUtil.isNotEmpty(map.get("SOURCE"))) {
                mstBondCreditRatingDto.setSource(map.get("SOURCE"));
            }
            if (StringUtil.isNotEmpty(map.get("GRADE_DATE"))) {
                mstBondCreditRatingDto.setGradeDate(DateUtil.parse(map.get("GRADE_DATE")));
            }

            mstBondCreditRatingDto.setErrMsg(null !=map.get("ERRMSG")?map.get("ERRMSG"):"");
            mstBondCreditRatingDto.setCreateUser(CommonUtil.getUserName());
            mstBondCreditRatingDto.setCreateDept(CommonUtil.getDeptCode()==null?"":CommonUtil.getDeptCode());
            mstBondCreditRatingDto.setCreateTime(DateUtil.now());
            mstBondCreditRatingDto.setUpdateUser(CommonUtil.getUserName());
            mstBondCreditRatingDto.setUpdateTime(DateUtil.now());

            mstBondCreditRatingDto.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            mstBondCreditRatingDto.setStatus(null !=map.get("STATUS")&&map.get("STATUS").equals("A")?DictCodeListExt1.维护数据状态_待处理.getValue():DictCodeListExt1.维护数据状态_有效.getValue());
            returnInfoList.add(mstBondCreditRatingDto);
        }
        return returnInfoList;
    }

    public List<ExcelValidDto> importFileSave(String fileName,String tableName) throws Exception {
        List<Map<String, String>> list = dataMapAnalysis(fileName);
        List<ExcelValidDto> checkResult = baseCommonCheckService.checkDataAll("D", list, tableName, fileName, tableName, tableName, null, null);
        if(!CollectionUtils.isEmpty(list)) {
            List<ChbMstBondCreditRatingDto> chbMstBondCreditRatingDtos = converToBean(list);
            // mysql load data
           /* StringBuilder sb = new StringBuilder();
            BatchInsertUtil batchInsertUtil = new BatchInsertUtil();
            for (ChbMstBondCreditRatingDto dto : chbMstBondCreditRatingDtos) {
                batchInsertUtil.builderAppend(sb,dto.getoCode());
                batchInsertUtil.builderAppend(sb,dto.getGradeOrgId());
                batchInsertUtil.builderAppend(sb,DateUtil.format(dto.getGradeDate()));
                batchInsertUtil.builderAppend(sb,dto.getoType());
                batchInsertUtil.builderAppend(sb,dto.getShortLongTerm());
                batchInsertUtil.builderAppend(sb,null==dto.getGradeResult()?"":dto.getGradeResult());
                batchInsertUtil.builderAppend(sb,"");
                batchInsertUtil.builderAppend(sb,dto.getCreateUser());
                batchInsertUtil.builderAppend(sb,dto.getCreateDept());
                batchInsertUtil.builderAppend(sb,DateUtil.format(dto.getCreateTime()));
                batchInsertUtil.builderAppend(sb,dto.getUpdateUser());
                batchInsertUtil.builderAppend(sb,DateUtil.format(dto.getUpdateTime()));
                batchInsertUtil.builderAppend(sb,"");
                batchInsertUtil.builderAppend(sb,"");
                batchInsertUtil.builderAppend(sb,"");
                batchInsertUtil.builderAppend(sb,"");
                batchInsertUtil.builderAppend(sb,"");
                batchInsertUtil.builderAppend(sb,dto.getSource());
                batchInsertUtil.builderAppend(sb,dto.getDataSource());
                batchInsertUtil.builderAppend(sb,dto.getStatus());
                batchInsertUtil.builderEnd(sb,dto.getErrMsg());
            }
            byte[] bytes = sb.toString().getBytes();
            InputStream in = new ByteArrayInputStream(bytes);
            batchInsertUtil.bulkLoadFromInputStream(tableName,in);*/
            //按500条分批保存
            List<List<ChbMstBondCreditRatingDto>> partitionList = Lists.partition(chbMstBondCreditRatingDtos, 500);
            for (List<ChbMstBondCreditRatingDto> dtos : partitionList) {
                mstBondCreditRatingDao.batchInsert(dtos);
            }
        }
        return checkResult.stream().filter(ele ->null != ele.getValidKey() && ele.getValidKey().equals("YW")).collect(Collectors.toList());
    }
}
