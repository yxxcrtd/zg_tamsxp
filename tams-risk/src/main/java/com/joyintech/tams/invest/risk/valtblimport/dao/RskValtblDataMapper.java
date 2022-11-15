package com.joyintech.tams.invest.risk.valtblimport.dao;

import com.joyintech.tams.common.base.entity.RskValtblPrtImpMap;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblDataDto;
import org.springframework.stereotype.Repository;

@Repository
public interface RskValtblDataMapper {

    /**
     * 估值数据表操作，增删改
     * @param rskValtblDataDto
     * @return
     */

    int insert(RskValtblDataDto rskValtblDataDto);

    RskValtblDataDto select();

    int updateByPrimaryKey(String seqNo);

}
