package com.joyintech.service.basicdata.multiportfoliomaintenance.dao;

import com.joyintech.framework.select.TreeNode;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioTreeDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MutiptlPortfolioTreeMapper {
    int deleteByPrimaryKey(String treeId);

    int insert(MutiptlPortfolioTreeDto record);

    int insertSelective(MutiptlPortfolioTreeDto record);

    MutiptlPortfolioTreeDto selectByPrimaryKey(String treeId);

    int updateByPrimaryKeySelective(MutiptlPortfolioTreeDto record);

    int updateByPrimaryKey(MutiptlPortfolioTreeDto record);

    List<TreeNode> getMutiptlPortfolioTree(Map<String, Object> paramMap);
    /**
     * 增加节点方法
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @see
     * @since 1.0
     */
    void addNode(Map<String, Object> map);
    /**
     * 根据treeId，PId查询
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    MutiptlPortfolioTreeDto selectByKey(Map<String, Object> map);

    /**
     * 修改方法
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @see
     * @since 1.0
     */
    void getEdit(Map<String, Object> map);
    /**
     *
     * @param Id
     * @param PId
     * @return
     */
    int  deleteByPid(@Param ("parTreeId") String parTreeId);

}