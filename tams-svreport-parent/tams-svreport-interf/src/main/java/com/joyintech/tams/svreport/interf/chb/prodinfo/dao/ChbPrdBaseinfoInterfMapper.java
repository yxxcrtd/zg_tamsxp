package com.joyintech.tams.svreport.interf.chb.prodinfo.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
@Repository
public interface ChbPrdBaseinfoInterfMapper {
    int insert(List<ChbPrdBaseinfo> record);

	int deleteByDate(Date batchTime);

	int deleteByProdid(@Param("insertList")List<ChbPrdBaseinfo> prodinfos);
	
	int insertBymodel(ChbPrdBaseinfo prod);
	
	int deleteBymodel(ChbPrdBaseinfo prod);
	
	int deleteBySysDate();
	
	int mergeInfoList(@Param("newDataList")List<ChbPrdBaseinfo> insertList);

	String getParentCode(@Param("newFinprodId")String newFinprodId);

	void deleteChbPrdBaseinfoErroData(@Param("list") List<ChbPrdBaseinfo> list);

    void updateChbPrdresistCodes(@Param("list") List<ChbPrdBaseinfo> list);
}