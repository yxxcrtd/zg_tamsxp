package com.joyintech.tams.basicdata.perprodeclareinfo.service;

import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.exception.BusinessException;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.system.common.listener.UploadListener;
import com.joyintech.tams.basicdata.perprodeclareinfo.dao.SvrPerProDeclareInfo001Dao;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.ChbPrdDeclareInfoFileCode;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import com.joyintech.tams.basicdata.perprodeclareinfo.util.ErrdataConvertUtil;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.ChbPrdDeclareInfoErrodataMapper;
import com.joyintech.tams.common.base.entity.ChbInfoMgrResultConfig;
import com.joyintech.tams.common.base.entity.ChbInvestTrade;
import com.joyintech.tams.common.base.entity.ChbPrdDeclareInfoErrodataWithBLOBs;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.dao.ChbInfoMgrResultConfigMapper;
import com.joyintech.tams.report.common.dto.SvrAjaxResult;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dao.ChbPrdDeclareInfoMapper;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import com.joyintech.tams.svreport.interf.chb.proddeclare.service.ChbProdDeclareFileImpService;
import com.joyintech.tams.systemtools.dto.CommonAjaxResult;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 产品申报登记信息 Service
 *
 * @author yxj
 * @date 1.0 2022年03月07日
 * @since 1.0
 */
@Service
public class SvrPerProDeclareInfoService001 extends CommonExportService<SvrPerProDeclareInfo001Dto> implements BaseProcessService<SvrPerProDeclareInfo001Dto> {
    @Autowired
    private SvrPerProDeclareInfo001Dao svrPerProDeclareInfo001Dao;
    @Autowired
    private ChbInfoMgrResultConfigMapper chbInfoMgrResultConfigMapper;
    @Autowired
    private ChbProdDeclareFileImpService chbProdDeclareFileImpService;
    @Autowired
    private ChbPrdDeclareInfoMapper chbPrdDeclareInfoMapper;
    @Autowired
    private ChbPrdDeclareInfoErrodataMapper chbPrdDeclareInfoErrodataMapper;
    @Autowired
    private UploadListener uploadListener;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 白名单校验正则表达式
     */
    private static final String whitePattern = "^[\\u4e00-\\u9fa5\\ue815-\\ue864\\uff01-\\uff5e\\uffe0-\\uffe5\\u3000-\\u3003\\u3005-\\u3017\\u301d-\\u301e\\u2010\\u2013-\\u2016\\u2018-\\u2019\\u201c-\\u201d\\u2025-\\u2026\\u2030\\u2032-\\u2033\\u2035\\u203b\\u00b7\\u0030-\\u0039\\u0041-\\u005a\\u0061-\\u007a\\u2160-\\u216b\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E]{0,}$";
    /**
     * 分页查询
     * @param map
     * @return
     */
    public Page<SvrPerProDeclareInfo001Dto> selectForPage(Map<String, Object> map){
//        if (StringUtil.isNotEmpty(map.get("repFinprodName"))) {
//            map.put("repFinprodName", StringUtil.splitToList(null, map.get("repFinprodName").toString()));
//        }
//        if (StringUtil.isNotEmpty(map.get("repFinprodInsId"))) {
//            map.put("repFinprodInsId", StringUtil.splitToList(null, map.get("repFinprodInsId").toString()));
//        }
        Page<SvrPerProDeclareInfo001Dto> pageDto = restTemplate.postForPage(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/selectForPage", map,
                SvrPerProDeclareInfo001Dto.class);
        return pageDto;
    }

    /**
     * 保存
     * @param dto
     * @return
     */
    public AjaxResult save(SvrPerProDeclareInfo001Dto dto) {
        return restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/save", dto,
                SvrAjaxResult.class);
    }

    /**
     * 修改
     * @param dto
     * @return
     */
    public AjaxResult update(SvrPerProDeclareInfo001Dto dto) {
        return restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/update", dto,
                SvrAjaxResult.class);
    }

    /**
     * 删除
     * @param dto
     * @return
     */
    public AjaxResult deleteByPrimaryKey(SvrPerProDeclareInfo001Dto dto) {
        return restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/delete", dto,
                SvrAjaxResult.class);
    }

    /**
     * 双击查看详情
     * @param declareUuid
     * @return
     */
    public SvrPerProDeclareInfo001Dto selectDetail(String declareUuid,String status) {
        SvrPerProDeclareInfo001Dto dto = new  SvrPerProDeclareInfo001Dto();
        if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),status)){
            ChbPrdDeclareInfoErrodataWithBLOBs chbPrdDeclareInfoErrodataWithBLOBs = chbPrdDeclareInfoErrodataMapper.selectByPrimaryKey(declareUuid);
            if(chbPrdDeclareInfoErrodataWithBLOBs!=null){
                dto = ErrdataConvertUtil.errdataConvert(dto.getClass(),chbPrdDeclareInfoErrodataWithBLOBs.getOrgData());
                dto.setStatusValid(DictCodeListExt3.中债数据状态_待处理.getValue());
                dto.setDeclareUuid(chbPrdDeclareInfoErrodataWithBLOBs.getDatauuid());
            }
        }else{
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("declareUuid",declareUuid);
             dto = restTemplate.postForObject(
                    SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/selectDetail", param,
                    SvrPerProDeclareInfo001Dto.class);
        }
        return dto;
    }

    /**
     * 联想下拉框
     * @param param
     * @return
     */
    public List<SuggestDto> queryProdInfo(Map<String, Object> param) {
        return restTemplate.postForList(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/queryProdInfo", param,
                SuggestDto.class);
    }

    /**
     * 查询所有数据
     * @param params
     * @return
     */
    public List<SvrPerProDeclareInfo001Dto> queryAllList(Map<String, Object> params) {
        return restTemplate.postForList(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/queryAllList", params,
                SvrPerProDeclareInfo001Dto.class);
    }

    /**
     * 导入前的数据检查
     * @param fileName
     * @return
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName) throws Exception {
        AjaxResult webResult = new AjaxResult();
        if (fileName.indexOf("产品申报") == -1){
            webResult.setMessage(this.getMessage("ImportFileName"), MessageType.错误消息);
            return webResult;
        }
        List<Map<String, String>> list = dataMapAnalysis(fileName);
        if(list.size()<1){
            webResult.setMessage("请勿上传空文件", MessageType.错误消息);
            return webResult;
        }
        int i = svrPerProDeclareInfo001Dao.countDuplicatedData(list);
        if(i>0){
            String[] checkEuplicatedKeys = ChbCheckTableInfo.个人产品申报登记INFO.getCheckEuplicatedKeys();
            int dup = countDuplicatedData(list, checkEuplicatedKeys);
            webResult.setMessage(this.getMessage("SvrDuplicateMessage",dup,i), MessageType.确认消息);
        }
        return webResult;
    }


    /**
     * 导入文件
     * @param fileName
     * @return
     */
    public List<ExcelValidDto> importData(String fileName) throws Exception {
        List<Map<String, String>> list = dataMapAnalysis(fileName);
        List<com.joyintech.tams.systemtools.dto.ExcelValidDto> listExcel = new ArrayList<com.joyintech.tams.systemtools.dto.ExcelValidDto>();
        chbProdDeclareFileImpService.importDB(listExcel,list,new InterfFile(),null);
        return listExcel;
    }

    /**
     * 批量删除
     * @param param
     * @return
     */
    public AjaxResult deleteList(Map<String, Object> param) {
        return restTemplate.postForObject(
                SvrServiceApplication.CHB_SERVICE_NAME + "/SvrPerProDeclareInfo/deleteList", param,
                SvrAjaxResult.class);
    }

    @Override
    public void doComplete(SvrPerProDeclareInfo001Dto entity, OperationType type) {
        if (OperationType.DELETE.equals(type)) {
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),entity.getStatusValid())){
                chbPrdDeclareInfoErrodataMapper.deleteByPrimaryKey(entity.getDeclareUuid());
            }else{
                entity.setStatus(DictCodeListExt1.维护数据状态_删除.getValue());
                svrPerProDeclareInfo001Dao.delete(entity);
            }

        }
        else if (OperationType.NEW.equals(type)) {
            entity.setDatasource(DictCodeListExt2.数据来源_新建导入.getValue());
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            entity.setDeclareUuid(IDGenerator.generate(32));
            ChbPrdDeclareInfoDto dto = new ChbPrdDeclareInfoDto();
            dto.setProdInsId(entity.getProdInsId());
            chbPrdDeclareInfoMapper.deleteChbPrdDeclareInfoErroDataByKey(Lists.newArrayList(dto));
            svrPerProDeclareInfo001Dao.insert(entity);
        }
        else if(OperationType.MODIFY.equals(type)){
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),entity.getStatusValid())){
                //删除errodata记录
                chbPrdDeclareInfoErrodataMapper.deleteByPrimaryKey(entity.getDeclareUuid());
                entity.setDeclareUuid(IDGenerator.generate(36));
            }
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            svrPerProDeclareInfo001Dao.insert(entity);
        }
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
        com.joyintech.framework.util.ExcelUtil excelUtil = com.joyintech.framework.util.ExcelUtil.build(flieName, null);

        List<List<String>> dataList = excelUtil.readToList(0);
        List<String> headList = dataList.get(0);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableId", "CHB_PRD_DECLARE_INFO");
        param.put("flag", false);
        List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryCloumnMapping(param);
        Map<String,String> map = new HashMap<>();
        chbInfoMgrResultConfigList.forEach(ele->map.put(ele.getColumnName().trim().replace("（","(").replace("）",")"),ele.getRealColumnId()));
        List<String> columnList = new ArrayList<>();
        for(int i=0;i<headList.size();++i){
            String columnName = headList.get(i);
            String temp = columnName.trim().replace("（","(").replace("）",")");
            columnName= map.get(temp);
            columnList.add(i,columnName);
        }
        ArrayList<Object> arrayList = Lists.newArrayList();
        Set<String> set = map.keySet();
        for (String s : headList) {
            if(!set.contains(s.trim().replace("（","(").replace("）",")"))){
                arrayList.add(s);
            }
        }
        List<Map<String,String>> list = new ArrayList<>();
        for(int i=1;i<dataList.size();++i){
            Map<String,String> subMap = new HashMap<>();
            List<String> subList = dataList.get(i);
            for(int j=0;j<subList.size();++j){
                if(StringUtil.isEmpty(subMap.get(columnList.get(j)))){
                    subMap.put(columnList.get(j),subList.get(j));
                }
            }
            subMap.put("DECLARE_UUID",IDGenerator.generate(36));

            list.add(subMap);
        }
        for (ChbInfoMgrResultConfig config : chbInfoMgrResultConfigList) {
            if(!StringUtils.isEmpty(config.getResultdictCode())){
                for (Map<String, String> m : list) {
                    if(m.get(config.getRealColumnId())!=null){
                        if(config.getRealColumnId().equals("PRD_SPEC_PROPERTY")
                                ||config.getRealColumnId().equals("SALE_AREA")
                                ||config.getRealColumnId().equals("OUT_DEP_CTRY")
                                ||config.getRealColumnId().equals("INVEST_COUNTRY")){
                            try {
                                StringBuffer sb= new StringBuffer();
                                String[] split = m.get(config.getRealColumnId()).split(",");
                                Stream.of(split).forEach(e->sb.append(e.split(" ")[0]).append(","));
                                sb.deleteCharAt(sb.length() - 1);
                                m.put(config.getRealColumnId(),sb.toString());
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                        }else{
                            m.put(config.getRealColumnId(),m.get(config.getRealColumnId()).split(" ")[0]);
                        }
                    }
                }
            }
        }
        return list;
    }

    private int countDuplicatedData(List<Map<String, String>> list, String... duplicateKey) {
        if(list==null || list.size()<1){
            return 0;
        }
        Set<String> set = new HashSet<>();

        if(duplicateKey!=null && duplicateKey.length>1){
            list.forEach(e->{
                StringBuilder sb = new StringBuilder();
                Stream.of(duplicateKey).forEach(ele->{
                    sb.append(e.get(ele));
                });
                set.add(sb.toString());
            });
            return list.size()-set.size();
        }else{
            //duplicateKey 为空则查询所有列
            list.forEach(e->{
                StringBuilder sb = new StringBuilder();
                e.keySet().forEach(ele->{
                    sb.append(e.get(ele));
                });
                set.add(sb.toString());
            });
            return list.size()-set.size();
        }
    }

    public AjaxResult tenFileUploadBatch(List<Map<String, Object>> fileList) throws Exception {
        StringBuffer fileCheckMsg = new StringBuffer();
        for(Map<String, Object> map:fileList){
            String srcName=(String) map.get("srcName");
            String fileNo=(String) map.get("fileNo");
            String fileProdId=srcName.substring(0, srcName.lastIndexOf("."));
            String targetFileSTR=(String) map.get("targetFileSTR");
            List<String> prodidList=(List<String>) map.get("prodInsIdList");

            File file=new File(targetFileSTR);
            StringBuffer fileCheckMsg1 = new StringBuffer();
            //校验文件名与所选产品代码是否匹配
            Boolean flag=false;
            for(String prodid :prodidList){
                if(fileProdId.equals(prodid)){
                    flag=true;
                }
            }
            if(!flag){
                fileCheckMsg1.append("文件名"+srcName+"与所选行内标识码不匹配！");
                fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
            }
            int fileNum=0;
            // 记录文件信息
            Map<String, List<String>> fileExistsMap = new HashMap<>();
            try (
                    ZipFile zipFile=new ZipFile(new File(targetFileSTR), Charset.forName("GBK"));
            ){
                List<String> folderNoList = ChbPrdDeclareInfoFileCode.genFolderNoList();
                for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
                    ZipEntry entry=e.nextElement();
                    //    aaa.doc:表示文件
                    if (entry.getName().indexOf("/") < 0){
                        fileCheckMsg1.append("包含不必要的文件或文件夹"+ entry.getName() + "！");
                        fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                        continue;
                    }
                    //    09/bbb/aaa.doc:表示文件夹下存在文件夹
                    if (entry.getName().indexOf("/") != entry.getName().lastIndexOf("/")){
                        fileCheckMsg1.append("包含不必要的文件或文件夹"+ entry.getName() + "！");
                        fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                        continue;
                    }

                    if (entry.getName().indexOf(",") > 0){
                        fileCheckMsg1.append("文件中不得包含,字符"+ entry.getName() + "！");
                        fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                        continue;
                    }
                    //    09/:表示文件夹
                    String folderName = entry.getName().substring(0, entry.getName().indexOf("/"));
                    if (entry.getName().indexOf("/") + 1== entry.getName().length()){
                        if (!folderNoList.contains(folderName)){
                            fileCheckMsg1.append("包含不必要的文件夹"+ folderName + "！");
                            fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                        }
                        continue;
                    }
                    //    09/aaa.doc:表示文件夹下文件
                    if (entry.getName().indexOf("/") == entry.getName().lastIndexOf("/")){
                        ChbPrdDeclareInfoFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareInfoFileCode.genInfoByFolderNo(folderName);
                        if (chbPrdDeclareRegFileCode != null &&(
                                entry.getSize() > chbPrdDeclareRegFileCode.getFileSize() * 1024 * 1024||  entry.getSize() == 0)){
                            fileCheckMsg1.append("文件（" + entry.getName() + "）大小超过" + chbPrdDeclareRegFileCode.getFileSize() + "M或为0字节文件");
                            fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                        }
                        if(entry.getName().lastIndexOf(".")>=0){
                            String fileType = entry.getName().substring(entry.getName().lastIndexOf(".") + 1, entry.getName().length());
                            if (chbPrdDeclareRegFileCode != null && chbPrdDeclareRegFileCode.getFileType().indexOf(fileType) < 0){
                                fileCheckMsg1.append("文件（" + entry.getName() + "）扩展名不对。必须为以下的一种：" + chbPrdDeclareRegFileCode.getFileType() + "。" );
                                fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                            }
                        }else{
                            fileCheckMsg1.append("文件（" + entry.getName() + "）扩展名不对。必须为以下的一种：" + chbPrdDeclareRegFileCode.getFileType() + "。" );
                            fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                            continue;
                        }
                        // add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 start
                        String fileName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1, entry.getName().lastIndexOf("."));
                        if (!fileName.matches(whitePattern)){
                            fileCheckMsg1.append("文件（" + fileName + "）必须填写白名单中的文字。");
                            fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                        }
                        // add by zhangliang 20210315 上传九大附件时，需要对每个附件的名称进行白名单检查 end
                        if (fileExistsMap.containsKey(folderName)){
                            List<String> folderNameList = new ArrayList<>(fileExistsMap.get(folderName));
                            folderNameList.add(entry.getName().substring(folderName.length() + 1));
                            fileExistsMap.put(folderName, folderNameList);
                        }
                        else{
                            fileExistsMap.put(folderName, Arrays.asList(entry.getName().substring(folderName.length() + 1)));
                        }
                    }
                    // 统计文件个数
                    if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
                        fileNum++;
                    }
                }
                for (String folderNo : folderNoList){
                    if (!fileExistsMap.containsKey(folderNo) || StringUtil.isEmptyList(fileExistsMap.get(folderNo))){
                        ChbPrdDeclareInfoFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareInfoFileCode.genInfoByFolderNo(folderNo);
                        if (chbPrdDeclareRegFileCode.isNend()){
                            fileCheckMsg1.append(chbPrdDeclareRegFileCode.getFolderName() + "不可为空。");
                            fileCheckMsg1.append(GlobalConstant.HTML_NEWLINE);
                        }
                    }
                }

            } catch (Exception e) {
                throw new BusinessException(e);
            }
            if(fileCheckMsg1.length() > 0){
                if(file.exists()){
                    file.delete();
                }
                uploadListener.remove(fileNo);
                fileCheckMsg.append(fileCheckMsg1);
            }
        }
        if(fileCheckMsg.length()>0){
            fileCheckMsg.append("上传失败！");
            return new AjaxResult(fileCheckMsg.toString(), MessageType.错误消息);
        }
        //校验通过，保存文件
        for(Map<String, Object> filemap:fileList){
            String srcName=(String) filemap.get("srcName");

            String fileNo=(String) filemap.get("fileNo");
            String fileProdId=srcName.substring(0, srcName.lastIndexOf("."));
            String targetFileSTR=(String) filemap.get("targetFileSTR");
//			String registSeqno=(String) map.get("registSeqno");
//			String prodId=(String) map.get("prodId");
            int fileNum=0;
            // 记录文件信息
            Map<String, List<String>> fileExistsMap1 = new HashMap<>();
            try (
                    ZipFile zipFile=new ZipFile(new File(targetFileSTR),Charset.forName("GBK"));
            ){
                List<String> folderNoList1 = ChbPrdDeclareInfoFileCode.genFolderNoList();
                for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
                    ZipEntry entry=e.nextElement();
                    //    09/:表示文件夹
                    String folderName = entry.getName().substring(0, entry.getName().indexOf("/"));
                    //    09/aaa.doc:表示文件夹下文件
                    if (entry.getName().indexOf("/") == entry.getName().lastIndexOf("/")){
                        ChbPrdDeclareInfoFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareInfoFileCode.genInfoByFolderNo(folderName);
                        if (fileExistsMap1.containsKey(folderName)){
                            List<String> folderNameList = new ArrayList<>(fileExistsMap1.get(folderName));
                            folderNameList.add(entry.getName().substring(folderName.length() + 1));
                            fileExistsMap1.put(folderName, folderNameList);
                        }
                        else{
                            fileExistsMap1.put(folderName, Arrays.asList(entry.getName().substring(folderName.length() + 1)));
                        }
                    }
                    // 统计文件个数
                    //修改为统计压缩包中文件夹个数  add by tuqirong 2022-01-26 start
                    if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()==0){
                        fileNum++;
                    }
                    //修改为统计压缩包中文件夹个数  add by tuqirong 2022-01-26 end
                }
                for (String folderNo : folderNoList1){
                    if (!fileExistsMap1.containsKey(folderNo) || StringUtil.isEmptyList(fileExistsMap1.get(folderNo))){
                        ChbPrdDeclareInfoFileCode chbPrdDeclareRegFileCode = ChbPrdDeclareInfoFileCode.genInfoByFolderNo(folderNo);
                        if (chbPrdDeclareRegFileCode.isNend()){
                            fileCheckMsg.append(chbPrdDeclareRegFileCode.getFolderName() + "不可为空。");
                            fileCheckMsg.append(GlobalConstant.HTML_NEWLINE);
                        }
                    }
                }

            } catch (Exception e) {
                throw new BusinessException(e);
            }
            StringBuffer fileSb = new StringBuffer();
            for (String key : fileExistsMap1.keySet()){
                fileSb.append(key);
                fileSb.append(GlobalConstant.COMMA);
                List<String> fileInfoList = fileExistsMap1.get(key);
                for (String fileInfo : fileInfoList){
                    /**ZGBSSE-854  【中债直连】：申报登记，9大附件上传入库信息多余逗号导致直连报文有误***/
                    if(StringUtil.isNotEmpty(fileInfo)){
                        fileSb.append(fileInfo);
                        fileSb.append(GlobalConstant.COMMA);
                    }
                }
                fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.COMMA.length()));
                fileSb.append(GlobalConstant.SEMICOLON);
            }
            fileSb = new StringBuffer(fileSb.substring(0, fileSb.length() - GlobalConstant.SEMICOLON.length()));
            SvrPerProDeclareInfo001Dto  perProDeclareRegDto=new SvrPerProDeclareInfo001Dto();
            perProDeclareRegDto.setProdInsId(fileProdId);
            perProDeclareRegDto.setFileNo(fileNo);
            perProDeclareRegDto.setFileNum(new BigDecimal(fileNum));
            perProDeclareRegDto.setTenFile(fileSb.toString());

            // 先查历史数据文件  将 历史文件删除
            String path = "";
            if(StringUtils.isNotEmpty(fileProdId)) {
                path = svrPerProDeclareInfo001Dao.selectFileInfo(perProDeclareRegDto);
            }
            svrPerProDeclareInfo001Dao.updateFileNo(perProDeclareRegDto);

            // 如果存在历史文件  将文件删除
            if(StringUtils.isNotEmpty(fileProdId) && StringUtil.isNotEmpty(path)){
                File file = new SafelyFile(path);
                if(file.exists()){
                    file.delete();
                }
            }
        }
        return new AjaxResult("附件上传成功！");
    }

    public void deltFile(Map<String, Object> map) {
        String fileNo=(String) map.get("fileNo");
        String declareUuid=(String) map.get("declareUuid");
        UploadFile file=uploadListener.fetch(fileNo);
        uploadListener.remove(fileNo);
        if(file!=null && file.getTargetFile().exists()){
            file.getTargetFile().delete();
        }
        SvrPerProDeclareInfo001Dto dto=new SvrPerProDeclareInfo001Dto();
        dto.setDeclareUuid(declareUuid);
        dto.setFileNo("");
        dto.setFileNum(BigDecimal.ZERO);
        dto.setTenFile("");
        svrPerProDeclareInfo001Dao.updateFileNo(dto);
    }

    public String getFileList(Map<String, Object> map) throws IOException {
        StringBuffer res=new StringBuffer();
        if(map.get("fileNo")==null){
            return URLEncoder.encode("附件不存在", "UTF-8").replace("+","%20");
        }
        String fileNo=(String) map.get("fileNo");

        UploadFile file=uploadListener.fetch(fileNo);
        if(file==null||file.getTargetFile()==null){
            return URLEncoder.encode("附件丢失", "UTF-8").replace("+","%20");
        }
        res.append(file.getFileName()+":   "+file.getSize()/1000+"K<br><br>");
        try(ZipFile zipFile=new ZipFile(file.getTargetFile(),Charset.forName("GBK"))){
            for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();){
                ZipEntry entry=e.nextElement();
                if(entry.getName().substring(entry.getName().lastIndexOf("/")+1).length()>0){
                    res.append(entry.getName().substring(entry.getName().lastIndexOf("/")+1)+"<br>");
                }
            }
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e);
            return URLEncoder.encode("文件未找到", "UTF-8").replace("+","%20");
        }
        String msg = URLEncoder.encode(res.toString(), "UTF-8").replace("+","%20");
        return msg;
    }

    public Integer selectChbPrdDeclareByProdInsId(String prodInsId) {
        return svrPerProDeclareInfo001Dao.selectChbPrdDeclareByProdInsId(prodInsId);
    }

    public Integer selectChbPrdDeclareByProdStageAndProdName(String prodStage,String prodName) {
        return svrPerProDeclareInfo001Dao.selectChbPrdDeclareByProdStageAndProdName(prodStage,prodName);
    }

    public List<SvrPerProDeclareInfo001Dto> selectByKeys(List<String> declareUuids) {
        return svrPerProDeclareInfo001Dao.selectByKeys(declareUuids);
    }
}
