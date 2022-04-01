package com.joyintech.service.basicdata.multiportfoliomaintenance.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dao.MutiptlPortfolioDetailMapper;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dao.MutiptlPortfolioTreeMapper;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioTreeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 对账参数设置
 */
@Service("backMultiPortfolioMaintenance001Service")
public class MultiPortfolioMaintenance001Service extends BaseService<MutiptlPortfolioDetailDto>{

    @Autowired
    private MutiptlPortfolioTreeMapper mutiptlPortfolioTreeMapper;

    @Autowired
    private MutiptlPortfolioDetailMapper mutiptlPortfolioDetailMapper;
    /**
     *
     * 获取分页查询结果（sharesId  dealDateString）
     * <功能详细描述>
     * @param pageParam Map<String, Object>
     * @return Page<MutiptlPortfolioDetailDto>
     * @throws Exception Exception
     * @see
     * @since 1.0
     */
    public Page<MutiptlPortfolioDetailDto> selectForPage(int page, int length, Map<String, Object> pageParam)
    {
        pageParam.put("createUser", SecurityUtil.getLoginUser().getUserName());
        return super.queryForPage(page,length,()-> mutiptlPortfolioDetailMapper.selectForPage(pageParam));
    }
    /**
     * 根据名称查询树
     *
     * @param param
     * @return
     * @since 1.0
     */
    public List<TreeNode> getMutiptlPortfolioTree(Map<String, Object> paramMap) {
        paramMap.put("createUser", SecurityUtil.getLoginUser().getUserName());
        return mutiptlPortfolioTreeMapper.getMutiptlPortfolioTree(paramMap);
    }

    public MutiptlPortfolioTreeDto selectByKey(Map<String, Object> map){
        return mutiptlPortfolioTreeMapper.selectByKey(map);
    }
    /**
     * 增加父节点
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void addParentNode(Map<String, Object> map){
        mutiptlPortfolioTreeMapper.addNode(map);
    }

    /**
     * 增加子节点
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addSonNode(Map<String, Object> map){
        mutiptlPortfolioTreeMapper.addNode(map);
        return null;
    }
    /**
     * 修改方法
     * <一句话功能简述>
     * <功能详细描述>
     * @param map
     * @see
     * @since 1.0
     */
    @Transactional(rollbackFor = Exception.class)
    public void getEdit(Map<String, Object> map){
        mutiptlPortfolioTreeMapper.getEdit(map);
    }

    @Transactional(rollbackFor = Exception.class)
    public void getDelete(String treeId){
        //删除该节点
        mutiptlPortfolioTreeMapper.deleteByPrimaryKey(treeId);
        //删除该节点的子节点
        mutiptlPortfolioTreeMapper.deleteByPid(treeId);
    }

}
