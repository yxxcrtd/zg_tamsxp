package com.joyintech.tams.common.svreport.base.dao;


import com.joyintech.tams.common.svreport.datacheck.dto.ChbInterfErrodataWithBLOBs;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckCondition;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckRule;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 基础共通mapper
 *
 * @author
 * @version
 * @see BaseCommonCheckMapper
 * @since
 */
@Repository
public interface BaseCommonCheckMapper {

    //DB   check不通过的入CHB_INTERF_ERRODATA表
    int insertIntoErro(ChbInterfErrodataWithBLOBs record);

    //查询check规则
    List<CheckCondition> selectByRuleid(String checkruleid);
    List<CheckRule> selectByDatatype(String datatype);

}