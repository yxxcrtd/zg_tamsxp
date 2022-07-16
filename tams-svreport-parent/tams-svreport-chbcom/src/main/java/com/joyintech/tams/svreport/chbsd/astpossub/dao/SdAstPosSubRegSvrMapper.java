package com.joyintech.tams.svreport.chbsd.astpossub.dao;

import com.joyintech.tams.svreport.chb.astposregist.dto.ChbAstPosRegist001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SdAstPosSubRegSvrMapper {
	
	/**
	 * 查询产品终止sub表list
	 * @param batchdate
	 * @param prodIdList
	 * @return
	 */
	List<ChbAstPosRegist001Dto>  selcteDataByIds(@Param("batchdate")String  batchdate, @Param("prodIdList") List<String> prodIdList);
 
}
