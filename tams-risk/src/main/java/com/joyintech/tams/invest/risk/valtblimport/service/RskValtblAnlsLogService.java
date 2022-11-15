package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.tams.invest.risk.valtblimport.dao.RskValtblAnlsLogMapper;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblAnlsLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RskValtblAnlsLogService {
    @Autowired
    private RskValtblAnlsLogMapper rskValtblAnlsLogMapper;

    /**
     * 插入数据
     */
    public int insert(RskValtblAnlsLogDto rskValtblAnlsLogDto){
        return rskValtblAnlsLogMapper.insert(rskValtblAnlsLogDto);
    }

    /**
     * 通过主键查询数据
     */
    RskValtblAnlsLogDto selectByPK(String seqNo){
        return rskValtblAnlsLogMapper.selectByPK(seqNo);
    }
}
