package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.valtblimport.dao.RskValtblDataMapper;
import com.joyintech.tams.invest.risk.valtblimport.dao.RskValtblFileInfoMapper;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RskValtblDataService extends CommonExportService {

    @Autowired
    private RskValtblDataMapper rskValtblDataMapper;

    /**
     *插入数据
     */
    public int insert(List<RskValtblDataDto> rskValtblDataDtoList) {
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/insertForData",rskValtblDataDtoList,Integer.class);
    }

    /**
     * 查询数据
     */
    public List<RskValtblDataDto> select(){
        return rskValtblDataMapper.select();
    }

    /**
     * 更新数据
     */
    public int updateByPrimaryKey(String seqNo){
        return rskValtblDataMapper.updateByPrimaryKey(seqNo);
    }

    public int deleteNameDateData(String name,String date){
        Map<String,Object> map=new HashMap<>();
        map.put("fileName",name);
        map.put("fileDate",date);
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/deleteNameDateData",map,Integer.class);

    }
    public int deleteNameData(String name){
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/deleteNameDateData",name,Integer.class);

    }


}