package com.joyintech.tams.invest.risk.valtblimport.service;

import com.joyintech.tams.invest.risk.valtblimport.dao.RskValtblDataMapper;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RskValtblDataService {

    @Autowired
    private RskValtblDataMapper rskValtblDataMapper;

    /**
     *插入数据
     */
    public int insert(RskValtblDataDto record){
        return rskValtblDataMapper.insert(record);
    }

    /**
     * 查询数据
     */
    RskValtblDataDto select(){
        return rskValtblDataMapper.select();
    }

    /**
     * 更新数据
     */
    public int updateByPrimaryKey(String seqNo){
        return rskValtblDataMapper.updateByPrimaryKey(seqNo);
    }


}