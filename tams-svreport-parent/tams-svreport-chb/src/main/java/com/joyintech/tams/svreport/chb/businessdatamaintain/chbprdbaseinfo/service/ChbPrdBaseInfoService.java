package com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.InterfFile;

import com.joyintech.tams.common.base.dao.ChbPrdBaseinfoErrodataMapper;
import com.joyintech.tams.common.base.dao.ChbPrdresistCodeMapper;
import com.joyintech.tams.common.base.entity.ChbInfoMgrResultConfig;

import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.dao.ChbInfoMgrResultConfigMapper;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dao.ChbPrdBaseInfoDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.errodata.dao.ChbPrdBaseinfoErrodataDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.errodata.dto.ChbPrdBaseinfoErrodataWithBLOBsDto;
import com.joyintech.tams.svreport.chb.common.util.DuplicateDataUtil;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dao.ChbPrdDeclareInfoMapper;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import com.joyintech.tams.svreport.interf.chb.prodinfo.dao.ChbPrdBaseinfoInterfMapper;
import com.joyintech.tams.svreport.interf.chb.prodinfo.service.SvrProdInfoFileImpService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author wubingbing
 * @date 2022-05-09 11:06
 */
@Service
public class ChbPrdBaseInfoService extends CommonExportService<ChbPrdBaseInfoDto> implements BaseProcessService<ChbPrdBaseInfoDto> {
    @Autowired
    private ChbPrdBaseInfoDao chbPrdBaseInfoDao;
    @Autowired
    private ChbPrdBaseinfoErrodataDao chbPrdBaseinfoErrodataDao;
    @Autowired
    private ChbPrdresistCodeMapper chbPrdresistCodeMapper;
    @Autowired
    private SvrProdInfoFileImpService svrProdInfoFileImpService;
    @Autowired
    private ChbInfoMgrResultConfigMapper chbInfoMgrResultConfigMapper;
    @Autowired
    private ChbPrdDeclareInfoMapper chbPrdDeclareInfoMapper;
    @Autowired
    private ChbPrdBaseinfoInterfMapper chbPrdBaseinfoInterfMapper;
    @Autowired
    private ChbPrdBaseinfoErrodataMapper chbPrdBaseinfoErrodataMapper;
    @Override
    @Transactional
    public void doComplete(ChbPrdBaseInfoDto entity, OperationType type) {
        if (OperationType.DELETE.equals(type)) {
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),entity.getStatusValid())){
                chbPrdBaseinfoErrodataMapper.deleteByPrimaryKey(entity.getProduuid());
            }else{
                entity.setStatus(DictCodeListExt1.维护数据状态_删除.getValue());
                chbPrdBaseInfoDao.update(entity);
            }

        }
        else if (OperationType.NEW.equals(type)) {
            String prodName = entity.getProdName();
            String cashManaCate = entity.getCashManaCate();
            String createUser = entity.getCreateUser();
            Date createTime = entity.getCreateTime();
            String updateUser = entity.getUpdateUser();
            Date updateTime = entity.getUpdateTime();
            ChbPrdDeclareInfoDto chbPrdDeclareInfoDto = chbPrdDeclareInfoMapper.selectByProdInsId(entity.getProdInsId());
            BeanUtils.copyProperties(chbPrdDeclareInfoDto,entity);
            entity.setCreateUser(createUser);
            entity.setCreateTime(createTime);
            entity.setUpdateUser(updateUser);
            entity.setUpdateTime(updateTime);
            entity.setProdName(prodName);
            entity.setCashManaCate(cashManaCate);
            entity.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            entity.setProduuid(IDGenerator.generate(32));
            List<ChbPrdBaseinfo> chbPrdBaseInfoDtos = Lists.newArrayList((ChbPrdBaseinfo) entity);
            chbPrdBaseinfoInterfMapper.deleteChbPrdBaseinfoErroData(chbPrdBaseInfoDtos);
            ChbPrdresistCode chbPrdresistCode = new ChbPrdresistCode();
            chbPrdresistCode.setProdInsId(entity.getProdInsId());
            chbPrdresistCode.setProdid(entity.getProdId());
            List<ChbPrdBaseinfo> chbPrdBaseinfoList = Lists.newArrayList(entity);
                chbPrdBaseinfoInterfMapper.updateChbPrdresistCodes(chbPrdBaseinfoList);

            chbPrdBaseInfoDao.insert(entity);

//            chbPrdresistCodeMapper.updateByPrimaryKeySelective()


        }
        else if(OperationType.MODIFY.equals(type)){
            String prodName = entity.getProdName();
            String createUser = entity.getCreateUser();
            Date createTime = entity.getCreateTime();
            String updateUser = entity.getUpdateUser();
            Date updateTime = entity.getUpdateTime();
            String cashManaCate = entity.getCashManaCate();
            ChbPrdDeclareInfoDto chbPrdDeclareInfoDto = chbPrdDeclareInfoMapper.selectByProdInsId(entity.getProdInsId());
            BeanUtils.copyProperties(chbPrdDeclareInfoDto,entity);
            entity.setProdName(prodName);
            entity.setCreateUser(createUser);
            entity.setCreateTime(createTime);
            entity.setUpdateUser(updateUser);
            entity.setUpdateTime(updateTime);
            entity.setCashManaCate(cashManaCate);
            entity.setDataSource("D");
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),entity.getStatusValid())){
                //删除errodata记录
                chbPrdBaseinfoErrodataDao.deleteByPrimaryKey(entity.getProduuid());
                entity.setProduuid(IDGenerator.generate(32));
            }
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            chbPrdBaseInfoDao.insert(entity);
        }
    }

    public List<SuggestDto> getProdIdAndProdName(Map<String, Object> pageParam) {
        return chbPrdBaseInfoDao.getProdIdAndProdName(pageParam);
    }

    public ChbPrdBaseInfoDto selectByKey(String produuid,String status) {
        ChbPrdBaseInfoDto dto = new ChbPrdBaseInfoDto();
        if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),status)){
            ChbPrdBaseinfoErrodataWithBLOBsDto chbPrdBaseinfoErrodataWithBLOBsDto = chbPrdBaseinfoErrodataDao.selectByPrimaryKey(produuid);
            if(chbPrdBaseinfoErrodataWithBLOBsDto !=null){
                dto = ErrdataConvertUtil.errdataConvert(dto.getClass(),chbPrdBaseinfoErrodataWithBLOBsDto.getOrgData());
                dto.setStatusValid(DictCodeListExt3.中债数据状态_待处理.getValue());
                dto.setProduuid(produuid);
            }
        }else{
             dto = chbPrdBaseInfoDao.selectByKey(produuid);
        }
        return dto;
    }

    public Map<String, String> selectRegCodeByProdId(String prodId) {
        return chbPrdBaseInfoDao.selectRegCodeByProdId(prodId);
    }

    public Page<ChbPrdBaseInfoDto> selectForPage(Map<String, Object> pageParam) {
        pageParam.put("customerTypeList", StringUtil.isNotEmpty(pageParam.get("customerType"))?(pageParam.get("customerType").toString().split(",")):null);
        pageParam.put("issuingStatusList", StringUtil.isNotEmpty(pageParam.get("issuingStatus"))?(pageParam.get("issuingStatus").toString().split(",")):null);
        return queryForPage(Integer.parseInt((String) pageParam.get("page")), Integer.parseInt((String) pageParam.get("length")), () -> chbPrdBaseInfoDao.selectAll(pageParam));
    }

    public List<ChbPrdBaseInfoDto> selectAll(Map<String, Object> params) {
        return chbPrdBaseInfoDao.selectAll(params);
    }

    public Page<SelectOptionDto> queryCalendarInfoList(Map<String, Object> pageParam) {
        return this.queryForPageForSelectReturn(Integer.parseInt(StringUtil.isEmpty((String) pageParam.get("page"))?"1":(String) pageParam.get("page")), Integer.parseInt(StringUtil.isEmpty((String) pageParam.get("length"))?"20":(String) pageParam.get("length")), ()-> chbPrdBaseInfoDao.queryCalendarInfoList(pageParam));
    }

    public List<ChbPrdBaseInfoDto> queryForList(String prodIdStr) {
        return chbPrdBaseInfoDao.queryForList(prodIdStr.split(","));
    }

    public void updateIssuingStatus(ChbPrdBaseInfoDto dto) {
         chbPrdBaseInfoDao.updateIssuingStatus(dto);
    }

    public AjaxResult checkConfirmMsgForImportData(String fileName) throws FileNotFoundException {
        AjaxResult webResult = new AjaxResult();
        if (fileName.indexOf("产品基本信息") == -1){
            webResult.setMessage(this.getMessage("ImportFileName"), MessageType.错误消息);
            return webResult;
        }
        List<Map<String, String>> list = dataMapAnalysis(fileName);
        if(list.size()<1){
            webResult.setMessage("请勿上传空文件", MessageType.错误消息);
            return webResult;
        }
        int i = chbPrdBaseInfoDao.countDuplicatedData(list);
        if(i>0){
            String[] checkEuplicatedKeys = ChbCheckTableInfo.个人发行登记表INFO.getCheckEuplicatedKeys();
            int dup = DuplicateDataUtil.countDuplicatedData(list, checkEuplicatedKeys);
            webResult.setMessage(this.getMessage("SvrDuplicateMessage",dup,i), MessageType.确认消息);
        }
        return webResult;
    }

    private List<Map<String, String>> dataMapAnalysis(String fileName) throws FileNotFoundException {
        String prefix = CacheUtil.getSysParam(GlobalConstant.COM_PROP_UPLOAD_PATH);
        if(!StringUtil.startsWith(fileName, prefix)
                && !StringUtil.startsWith(fileName, prefix.replace("/", File.separator))) {
            fileName = prefix + File.separator + fileName;
        }
        com.joyintech.framework.util.ExcelUtil excelUtil = com.joyintech.framework.util.ExcelUtil.build(fileName, null);

        List<List<String>> dataList = excelUtil.readToList(0);
        List<String> headList = dataList.get(0);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableId", "CHB_PRD_BASEINFO");
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

        List<Map<String,String>> list = new ArrayList<>();
        for(int i=1;i<dataList.size();++i){
            Map<String,String> subMap = new HashMap<>();
            List<String> subList = dataList.get(i);
            for(int j=0;j<subList.size();++j){
                if(StringUtil.isEmpty(subMap.get(columnList.get(j)))){
                    subMap.put(columnList.get(j),subList.get(j));
                }
            }
            subMap.put("PRODUUID",IDGenerator.generate(36));

            list.add(subMap);
        }
        for (ChbInfoMgrResultConfig config : chbInfoMgrResultConfigList) {
            if(!StringUtils.isEmpty(config.getResultdictCode())){
                for (Map<String, String> m : list) {
                    if(m.get(config.getRealColumnId())!=null){
                        m.put(config.getRealColumnId(),m.get(config.getRealColumnId()).split(" ")[0]);
                    }
                }
            }
        }
        return list;
    }
    public Page<SelectOptionDto> getProdInsId(Map<String, Object> param) {
        return queryForPageForSelectReturn(Integer.parseInt(null == param.get("page")?"1":(String) param.get("page")), Integer.parseInt(null == (String) param.get("length")?"1":(String) param.get("length")),()->chbPrdBaseInfoDao.getProdInsId(param));
    }

    public ChbPrdDeclareInfoDto selectChbPrdDeclareByProdInsId(String prodInsId) {
        return chbPrdDeclareInfoMapper.selectByProdInsId(prodInsId);
    }


    public List<ExcelValidDto> importFileSave(String fileName)throws Exception {
        List<Map<String, String>> list = dataMapAnalysis(fileName);
        List<ExcelValidDto> listExcel = new ArrayList<ExcelValidDto>();
        svrProdInfoFileImpService.importDB(listExcel,list,new InterfFile(),null);
        return listExcel;
    }

    public List<Map<String,String>> selectByProdIdKey(String prodInsId) {
        return chbPrdBaseInfoDao.selectByProdIdKey(prodInsId);
    }

    public List<ChbPrdBaseInfoDto> selectByKeys(List<String> produuids) {
        return chbPrdBaseInfoDao.selectByKeys(produuids);
    }
}
