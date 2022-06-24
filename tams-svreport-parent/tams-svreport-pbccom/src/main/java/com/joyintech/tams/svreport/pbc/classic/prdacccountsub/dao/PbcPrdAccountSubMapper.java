package com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dto.PbcPrdAccountSub;
import org.springframework.stereotype.Repository;

@Repository
public interface PbcPrdAccountSubMapper {
    int deleteByPrimaryKey(String registSeqno);

    int insert(PbcPrdAccountSub record);
    
    int updateBaseInfoAccountId(PbcPrdAccountSub record);

    int insertSelective(PbcPrdAccountSub record);

    PbcPrdAccountSub selectByPrimaryKey(String registSeqno);

    int updateByPrimaryKeySelective(PbcPrdAccountSub record);

    int updateByPrimaryKey(PbcPrdAccountSub record);
    
    List<PbcPrdAccountSub> selectNeedRegisteData(PbcPrdAccountSub qmodel);
    
    List<PbcPrdAccountSub> selectAlReadyRegisteData(@Param("reglist")List<PbcPrdAccountSub> reglist);
    
    void deleteByProdid(@Param("deleteList")List<PbcPrdAccountSub> subList);
    
    List<PbcPrdAccountSub> selectAllRegisteData(@Param("reglist")List<PbcPrdAccountSub> reglist);

	List<PbcPrdAccountSub> searchPage(Map<String, Object> searchParam);
	
	int cancelManul(List<PbcPrdAccountSub> updateList);
	
	int doManul(@Param("list")List<PbcPrdAccountSub> updateList,@Param("submitadd")String submitadd);
	
	int insetBatchSubFromHisByUpdateTime(List<PbcPrdAccountSub> insertList);
	
	int deleteBatchSubFromHisByUpdateTime(List<PbcPrdAccountSub> insertList);	
	
	int insertHisBatchBySendStatus(List<PbcPrdAccountSub> insertList);
	
	void deleteBatchBySendStatus(List<PbcPrdAccountSub> deleteList);
    
    /**
     * 查询所有不是删除状态的人行资产池信息
     * @param param 
     * @return 所有不是删除状态的人行资产池信息
     */
    List<PbcPrdAccountSub> queryAllPbcAssetBaseInfo(Map<String, Object> param);
    
    List<String> selectRegistSeqnoList(@Param("list")List<PbcPrdAccountSub> registList,@Param("flag")String flag);

    List<Map<String,String>> queryInsideProdId(Map<String, Object> param);
}