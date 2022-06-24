package com.joyintech.tams.svreport.chb.basecheck.dao;

import com.joyintech.tams.svreport.chb.basecheck.dto.Column;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wubingbing
 * @date 2022-03-31 15:00
 */
@Repository
public interface ColumnSelector {
    @Select("select column_name,ordinal_position,is_nullable,data_type,character_maximum_length,column_type,column_comment,numeric_precision,numeric_scale,column_key,privileges from information_schema.columns where table_schema = 'gd_test' and table_name = #{tableName}")
    List<Column> select(@Param("tableName") String tableName);
}
