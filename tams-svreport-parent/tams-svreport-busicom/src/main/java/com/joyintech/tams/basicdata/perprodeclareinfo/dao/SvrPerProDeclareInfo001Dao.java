package com.joyintech.tams.basicdata.perprodeclareinfo.dao;

import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author wubingbing
 * @date 2022-06-09 1:05
 */
@Repository
public interface SvrPerProDeclareInfo001Dao {
    void delete(SvrPerProDeclareInfo001Dto entity);

    void insert(SvrPerProDeclareInfo001Dto entity);

    int countDuplicatedData(@Param("list") List<Map<String, String>> list);

    String selectFileInfo(SvrPerProDeclareInfo001Dto dto);

    void updateFileNo(SvrPerProDeclareInfo001Dto perProDeclareRegDto);

    Integer selectChbPrdDeclareByProdInsId(String prodInsId);

    Integer selectChbPrdDeclareByProdStageAndProdName(@Param("prodStage") String prodStage,@Param("prodName") String prodName);

    List<SvrPerProDeclareInfo001Dto> selectByKeys(@Param("list") List<String> list);
}
