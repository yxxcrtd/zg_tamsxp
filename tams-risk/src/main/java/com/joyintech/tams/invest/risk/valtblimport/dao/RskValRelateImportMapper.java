package com.joyintech.tams.invest.risk.valtblimport.dao;

import com.joyintech.tams.common.base.entity.RskValtblPrtImpMap;
import com.joyintech.tams.invest.risk.valtblimport.dto.RskValtblDataDto;
import org.springframework.stereotype.Repository;

@Repository
public interface RskValRelateImportMapper {

    int insert(RskValtblDataDto record);

    RskValtblDataDto selectByPrimaryKey(String SEQ_NO);

    int updateByPrimaryKey(RskValtblDataDto record);

}
