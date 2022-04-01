package com.joyintech.service.basicdata.multiportfoliomaintenance.service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dao.MutiptlPortfolioDetailMapper;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.tams.constant.DictCodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对账参数设置
 */
@Service("backMultiPortfolioMaintenance002Service")
public class MultiPortfolioMaintenance002Service extends BaseService<MutiptlPortfolioDetailDto>{


    @Autowired
    private MutiptlPortfolioDetailMapper mutiptlPortfolioDetailMapper;

    /**
     * 根据名称查询账户树
     *
     * @param paramMap
     * @return
     * @since 1.0
     */
    public List<TreeNode> getMutiptlPortfolioAcctnoTree(Map<String, Object> paramMap) {
        paramMap.put("username", SecurityUtil.getLoginUser().getUserName());
        List<MutiptlPortfolioDetailDto> portfolioAcctnoList=mutiptlPortfolioDetailMapper.getMutiptlPortfolioAcctnoTree(paramMap);
        Map<String,List<MutiptlPortfolioDetailDto>> mapList=portfolioAcctnoList
                .stream()
                .collect(Collectors.groupingBy(dto ->
                        dto.getPortfolioId() + "_" + dto.getPortfolioName()));
        List<TreeNode> treeNodes=new ArrayList<>();
        mapList.forEach((key, list) -> {
            TreeNode treeNode=new TreeNode();
            treeNode.setId(key.split("_")[0]);
            treeNode.setName(key.split("_")[1]);
            treeNode.setOpen(true);
            treeNodes.add(treeNode);
            list.forEach(detailDto -> {
                TreeNode node=new TreeNode();
                node.setId(detailDto.getAcctNo());
                node.setName(detailDto.getAcctName());
                node.setpId(key.split("_")[0]); //父节点是组合
                node.setObject(detailDto.getPortfolioName());
                node.setOpen(true);
                treeNodes.add(node);
            });
        });
        return treeNodes;
    }

    /**
     * 根据名称查询账户树
     *
     * @param dto
     * @return
     * @since 1.0
     */
    public Integer isExistPortfolio(MutiptlPortfolioDetailDto dto) {
        Integer count =mutiptlPortfolioDetailMapper.isExistPortfolio(dto);
        return count;
    }
    /**
     * 插入记录
     * @param dto
     * @return
     */
    public Integer insert(MutiptlPortfolioDetailDto dto) {
        for(MutiptlPortfolioDetailDto temp:dto.getSelectedItem()){
            MutiptlPortfolioDetailDto insertDto = new MutiptlPortfolioDetailDto();
            BeanUtil.copyPropertiesIgnoreNull(insertDto, temp);
            insertDto.setSeqNo(IDGenerator.generate());
            insertDto.setCcy(DictCodeList.币种_人民币_CNY.getValue());
            insertDto.setCreateUser(LoginUserUtil.getUserName());
            insertDto.setUpdateUser(LoginUserUtil.getUserName());
            Date date= DateUtil.now();
            insertDto.setCreateTime(date);
            insertDto.setUpdateTime(date);
            insertDto.setCreateDept(LoginUserUtil.getDeptCode());
            mutiptlPortfolioDetailMapper.insert(insertDto);
        }
        return 1;
    }

    /**
     * 删除记录
     * @param dto
     * @return
     */
    public Integer delete(MutiptlPortfolioDetailDto dto) {
        for(MutiptlPortfolioDetailDto temp:dto.getSelectedItem()){
            MutiptlPortfolioDetailDto deleteDto = new MutiptlPortfolioDetailDto();
            BeanUtil.copyPropertiesIgnoreNull(deleteDto, temp);
            mutiptlPortfolioDetailMapper.deleteByPrimaryKey(deleteDto.getSeqNo());
        }
        return 1;
    }
}
