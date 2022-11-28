package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.framework.export.CommonExportService;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.risk.valtblimport.dao.RskValtblAnlsLogMapper;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblAnlsLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RskValtblAnlsLogService extends CommonExportService {
    @Autowired
    private RskValtblAnlsLogMapper rskValtblAnlsLogMapper;

    /**
     * 插入数据
     */
    public int insert(RskValtblAnlsLogDto rskValtblAnlsLogDto){
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/insertForLog",rskValtblAnlsLogDto,Integer.class);
    }

    /**
     * 通过主键查询数据
     */
    public List<RskValtblAnlsLogDto> selectLog(String infoName){
        return restTemplate.postForObject(
                ServiceApplication.RISK_SERVICE_NAME + "/RskValTableQuery/selectForLog",infoName,List.class);
    }
}
