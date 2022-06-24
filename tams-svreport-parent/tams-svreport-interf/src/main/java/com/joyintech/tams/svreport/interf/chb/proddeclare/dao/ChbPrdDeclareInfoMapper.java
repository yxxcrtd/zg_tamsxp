package com.joyintech.tams.svreport.interf.chb.proddeclare.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.select.SelectOptionDto;
import org.apache.ibatis.annotations.Param;

import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import org.springframework.stereotype.Repository;

@Repository
public interface ChbPrdDeclareInfoMapper {
    int deleteByPrimaryKey(String declareUuid);

    int insert(ChbPrdDeclareInfoDto record);

    int insertSelective(ChbPrdDeclareInfoDto record);

    ChbPrdDeclareInfoDto selectByPrimaryKey(String declareUuid);

    int updateByPrimaryKeySelective(ChbPrdDeclareInfoDto record);

    int updateByPrimaryKey(ChbPrdDeclareInfoDto record);

    List<ChbPrdDeclareInfoDto> selectRepDeclare(@Param("updatetime") Date updatetime);
    int mergeInfoList(@Param("newDataList")List<ChbPrdDeclareInfoDto> insertList);

    ChbPrdDeclareInfoDto selectByProdInsId(@Param("prodInsId") String prodInsId);

    List<ChbPrdDeclareInfoDto> selectByChbPrdDeclareInfoList(@Param("list") List<String> list);

    List<SelectOptionDto> getProdInsId(Map<String, Object> param);

    String getProdNameByProdInsId(String prodInsId);

    void deleteChbPrdDeclareInfoByKey(@Param("list") List<ChbPrdDeclareInfoDto> list);

    void deleteChbPrdDeclareInfoErroDataByKey(@Param("list") List<ChbPrdDeclareInfoDto> list);
}