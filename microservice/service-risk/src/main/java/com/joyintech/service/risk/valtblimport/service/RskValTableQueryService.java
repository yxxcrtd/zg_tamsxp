package com.joyintech.service.risk.valtblimport.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.service.risk.valtblimport.dao.RskValTableQueryMapper;
import com.joyintech.service.risk.valtblimport.dto.*;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("backRskValTableQueryService")
public class RskValTableQueryService extends BaseService {

    @Autowired
    private RskValTableQueryMapper rskValTableQueryMapper;
    //获取平台日
    @Autowired(required = false)
    private IPlatformDate iPlatformDate;

    public List<SelectOptionDto> getFileName(String fileName) {
        return rskValTableQueryMapper.getFileName(fileName);
    }
    public List<SelectOptionDto> getFileHeader(String fileHeader) {
        return rskValTableQueryMapper.getFileHeader(fileHeader);
    }
    public List<SelectOptionDto> getSubCode(String subCode) {
        return rskValTableQueryMapper.getSubCode(subCode);
    }
    public List<String> selectName(String name){
        return rskValTableQueryMapper.selectName(name);
    }
    public int deleteName(String name){
        return rskValTableQueryMapper.deleteName(name);
    }
    public int deleteNameData(String name){
        return rskValTableQueryMapper.deleteNameData(name);
    }
    public List<String> selectNameDate(Map<String,Object> map){
        return rskValTableQueryMapper.selectNameDate(map);
    }
    public int deleteNameDate(Map<String,Object> map){
        return rskValTableQueryMapper.deleteNameDate(map);
    }
    public int deleteNameDateData(Map<String,Object> map){
        return rskValTableQueryMapper.deleteNameDateData(map);
    }



    public Page<RskValtblFileInfoDto> selectForPage(int page, int length, Map<String, Object> pageParam){
        if(StringUtil.isNotEmpty((String)pageParam.get("fileName"))){
            pageParam.replace("fileName", Arrays.asList(pageParam.get("fileName").toString().split(",")));
        }else{
            pageParam.replace("fileName", null);
        }
        if(StringUtil.isNotEmpty((String)pageParam.get("fileHeader"))){
            pageParam.replace("fileHeader", Arrays.asList(pageParam.get("fileHeader").toString().split(",")));
        }else{
            pageParam.replace("fileHeader", null);
        }
        if(StringUtil.isNotEmpty((String)pageParam.get("subOneCat"))){
            pageParam.replace("subOneCat", Arrays.asList(pageParam.get("subOneCat").toString().split(",")));
        }else{
            pageParam.replace("subOneCat", null);
        }
        if(StringUtil.isNotEmpty((String)pageParam.get("subTwoCat"))){
            pageParam.replace("subTwoCat", Arrays.asList(pageParam.get("subTwoCat").toString().split(",")));
        }else{
            pageParam.replace("subTwoCat", null);
        }
        if(StringUtil.isNotEmpty((String)pageParam.get("subCode"))){
            pageParam.replace("subCode", Arrays.asList(pageParam.get("subCode").toString().split(",")));
        }else{
            pageParam.replace("subCode", null);
        }
        return super.queryForPage(page, length, () -> rskValTableQueryMapper.queryForPage(pageParam));
    }

    public int insert(RskValtblFileInfoDto rskValtblFileInfoDto) {
        /*info.replace("createTime",iPlatformDate.getSysDate());
        info.replace("updateTime",iPlatformDate.getSysDate());*/
        return rskValTableQueryMapper.insert(rskValtblFileInfoDto);
    }

    public int insertForLog(RskValtblAnlsLogDto rskValtblAnlsLogDto) {
        return rskValTableQueryMapper.insertForLog(rskValtblAnlsLogDto);
    }



    public int insertForData(List<RskValtblDataDto> rskValtblDataDtoList) {
        for(RskValtblDataDto rskValtblDataDto:rskValtblDataDtoList){
            rskValTableQueryMapper.insertForData(rskValtblDataDto);
        }
        return 1;
    }

    public List<RskValtblAnlsLogDto> selectForLog(String info){
        return rskValTableQueryMapper.selectLog(info);
    }

    /*public List<RskValtblAssetSubDto> selectRskValTableDetaile(Map<String,Object> param){
        return rskValTableQueryMapper.selectRskValTableDetaile(param);
    }*/
}
