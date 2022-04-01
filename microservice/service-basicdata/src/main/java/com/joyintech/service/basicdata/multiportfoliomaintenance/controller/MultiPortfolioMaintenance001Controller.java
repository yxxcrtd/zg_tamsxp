package com.joyintech.service.basicdata.multiportfoliomaintenance.controller;

import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioTreeDto;
import com.joyintech.service.basicdata.multiportfoliomaintenance.service.MultiPortfolioMaintenance001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 多组合维护- 查询画面
 */
@Controller
@RestController("MultiPortfolioMaintenance001_trx")
@RequestMapping("/MultiPortfolioMaintenance001")
public class MultiPortfolioMaintenance001Controller extends BaseController {

    /**
     *  - service
     */
    @Autowired
    private MultiPortfolioMaintenance001Service multiPortfolioMaintenancea001Service;
    /**
     * 分页查询
     * @param pageParam 分页查询条件
     * @return page实体类
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    @PostMapping("/selectForPage/{page}/{length}")
    public Page<MutiptlPortfolioDetailDto> selectForPage(@RequestBody Map<String, Object> pageParam, @PathVariable("page") int page, @PathVariable("length") int length)
    {
        Page<MutiptlPortfolioDetailDto> dto = multiPortfolioMaintenancea001Service.selectForPage(page,length,pageParam);
        return dto;
    }
    /**
     * 多组合树
     * @param treeName
     * @return
     */
    @PostMapping("/getMutiptlPortfolioTree")
    public List<TreeNode> getMutiptlPortfolioTree(@RequestBody Map<String, Object> paramMap) {
        return multiPortfolioMaintenancea001Service.getMutiptlPortfolioTree(paramMap);
    }
    @PostMapping("selectByKey")
    public MutiptlPortfolioTreeDto selectByKey(@RequestBody Map<String, Object> param){
        return multiPortfolioMaintenancea001Service.selectByKey(param);
    }
    @GetMapping("getDelete")
    public void getDelete(String treeId){
        multiPortfolioMaintenancea001Service.getDelete(treeId);
    }

}
