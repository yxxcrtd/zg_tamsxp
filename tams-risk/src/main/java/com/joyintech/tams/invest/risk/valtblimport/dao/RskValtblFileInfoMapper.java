package com.joyintech.tams.invest.risk.valtblimport.dao;

import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblFileInfoDto;
import org.springframework.stereotype.Repository;

@Repository
public interface RskValtblFileInfoMapper {
    /**
     * 估值文件表操作，查询与插入
     */


    int insert(RskValtblFileInfoDto rskValtblFileInfoDto);
    RskValtblFileInfoDto selectByPK(String seqNo);
}
