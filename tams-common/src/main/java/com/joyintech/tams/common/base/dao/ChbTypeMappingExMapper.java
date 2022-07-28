package com.joyintech.tams.common.base.dao;

import java.util.List;
import java.util.Map;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public interface ChbTypeMappingExMapper {
    int deleteByPrimaryKey(String mappingid);

    int insert(ChbTypeMappingEx record);

    int insertSelective(ChbTypeMappingEx record);

    ChbTypeMappingEx selectByPrimaryKey(String mappingid);

    int updateByPrimaryKeySelective(ChbTypeMappingEx record);

    int updateByPrimaryKey(ChbTypeMappingEx record);
    
    List<ChbTypeMappingEx> select();
    
    List<ChbTypeMappingEx> selectByMappingcode(String mappingcode);

    List<ChbTypeMappingEx> selectByMappingname(String mappingname);

    ChbTypeMappingEx selectBySrcfieldSrcfield1(@Param("srcfield") String srcfield,@Param("srcfield1")String srcfield1);

    List<SelectOptionDto> getSubjectNameList(Map<String, Object> map);

}