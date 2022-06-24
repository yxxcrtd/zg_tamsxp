package com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.errodata.dao;

import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.errodata.dto.ChbPrdBaseinfoErrodataWithBLOBsDto;
import org.springframework.stereotype.Repository;

/**
 * @author wubingbing
 * @date 2022-05-26 12:44
 */
@Repository
public interface ChbPrdBaseinfoErrodataDao {
    void deleteByPrimaryKey(String produuid);

    ChbPrdBaseinfoErrodataWithBLOBsDto selectByPrimaryKey(String produuid);
}
