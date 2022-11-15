package com.joyintech.tams.invest.risk.valtblimport.dao;

import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblAnlsLogDto;
import org.springframework.stereotype.Repository;

@Repository
public interface RskValtblAnlsLogMapper {
    /**
     * 估值解析日志表操作，插入和查询
     */
    int insert(RskValtblAnlsLogDto rskValtblAnlsLogDto);
    RskValtblAnlsLogDto selectByPK(String seqNo);
}
