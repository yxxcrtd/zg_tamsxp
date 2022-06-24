package com.joyintech.service.chb.basicdata.perprodeclareinfo.dao;

import com.joyintech.framework.select.SuggestDto;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SvrPerProDeclareInfoMapper {
    int deleteByPrimaryKey(String declareUuid);

    int deleteByWorkflowId(String workflowId);

    int updateByWorkflowIdSelective(SvrPerProDeclareInfo001Dto record);

    int updateByWorkflowId(SvrPerProDeclareInfo001Dto record);

    int insertBasicByWorkflowId(Map paraMap);

    int insert(SvrPerProDeclareInfo001Dto record);

    int insertSelective(SvrPerProDeclareInfo001Dto record);

    int insertBasicByBasicInfo(SvrPerProDeclareInfo001Dto record);

    SvrPerProDeclareInfo001Dto selectByPrimaryKey(String declareUuid);

    int updateByPrimaryKeySelective(SvrPerProDeclareInfo001Dto record);

    int updateByPrimaryKey(SvrPerProDeclareInfo001Dto record);

    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    List<SvrPerProDeclareInfo001Dto> selectForPage(Map<String, Object> pageParam);

    /**
     * 新建插入
     * @param dto
     * @return
     */
    int insertSvrPerProDeclareInfo(SvrPerProDeclareInfo001Dto dto);

    /**
     * 修改
     * @param dto
     * @return
     */
    int updateSvrPerProDeclareInfo(SvrPerProDeclareInfo001Dto dto);

    /**
     * 双击详情
     * @param param
     * @return
     */
    SvrPerProDeclareInfo001Dto selectDetail(Map<String, Object> param);

    /**
     * 联想下拉框
     * @param param
     * @return
     */
    List<SuggestDto> queryProdInfo(Map<String, Object> param);

    /**
     * 批量删除
     * @param param
     * @return
     */
    int deleteList(Map<String, Object> param);
}