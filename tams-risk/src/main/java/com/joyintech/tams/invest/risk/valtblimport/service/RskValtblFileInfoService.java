package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.valtblimport.dao.RskValtblFileInfoMapper;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblFileInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RskValtblFileInfoService extends CommonExportService {
    @Autowired
    private RskValtblFileInfoMapper rskValtblFileInfoMapper;
    /**
     * 插入操作
     */
    public int insert(RskValtblFileInfoDto rskValtblFileInfoDto){
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/insert",rskValtblFileInfoDto,Integer.class);
    }
    /**
     * 查询操作
     */
    RskValtblFileInfoDto selectByPK(String seqNo){
        return rskValtblFileInfoMapper.selectByPK(seqNo);
    }
    /**
     *查询是否有同估值表名
     */
    public List<String> selectName(String name){
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/selectName",name,List.class);

    }
    public int deleteName(String name){
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/deleteName",name,Integer.class);

    }
    /**
     * 查询是否有同种估值表名和估值日期
     */
    public List<String> selectNameDate(String name,String date){
        Map<String,Object> map=new HashMap<>();
        map.put("fileName",name);
        map.put("fileDate",date);
        return restTemplate.postForObject(ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/selectNameDate"
        ,map,List.class);

    }
    public int deleteNameDate(String name,String date){
        Map<String,Object> map=new HashMap<>();
        map.put("fileName",name);
        map.put("fileDate",date);
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/deleteNameDate",map,Integer.class);

    }

}
