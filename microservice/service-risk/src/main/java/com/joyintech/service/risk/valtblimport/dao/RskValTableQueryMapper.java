package com.joyintech.service.risk.valtblimport.dao;

import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.service.risk.valtblimport.dto.RskValtblAnlsLogDto;
import com.joyintech.service.risk.valtblimport.dto.RskValtblDataDto;
import com.joyintech.service.risk.valtblimport.dto.RskValtblFileInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RskValTableQueryMapper {
    List<SelectOptionDto> getFileName(@Param("fileName") String fileName);
    List<SelectOptionDto> getFileHeader(@Param("fileHeader")String fileHeader);
    List<SelectOptionDto> getSubCode(@Param("subCode")String subCode);
    List<RskValtblFileInfoDto> queryForPage(Map<String, Object> pageParam);
    int insert(RskValtblFileInfoDto rskValtblFileInfoDto);
    int insertForLog(RskValtblAnlsLogDto rskValtblAnlsLogDto);
    int insertForData(RskValtblDataDto rskValtblDataDtoList);
    List<String> selectName(@Param("name")String name);
    int deleteName(@Param("name")String name);
    int deleteNameData(@Param("name")String name);
    List<String> selectNameDate(Map<String,Object> map);
    int deleteNameDate(Map<String,Object> map);
    int deleteNameDateData(Map<String,Object> map);
    String selectLog(@Param("info")String info);

}
