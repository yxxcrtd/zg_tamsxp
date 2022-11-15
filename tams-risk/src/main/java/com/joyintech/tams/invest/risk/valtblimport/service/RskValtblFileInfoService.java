package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.tams.invest.risk.valtblimport.dao.RskValtblFileInfoMapper;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblFileInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RskValtblFileInfoService {
    @Autowired
    private RskValtblFileInfoMapper rskValtblFileInfoMapper;
    /**
     * 插入操作
     */
    public int insert(RskValtblFileInfoDto rskValtblFileInfoDto){
        return rskValtblFileInfoMapper.insert(rskValtblFileInfoDto);
    }
    /**
     * 查询操作
     */
    RskValtblFileInfoDto selectByPK(String seqNo){
        return rskValtblFileInfoMapper.selectByPK(seqNo);
    }
}
