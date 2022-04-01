package com.joyintech.service.basicdata.multiportfoliomaintenance.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.validation.groups.ValidateGroupEdit;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.service.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioTreeDto;
import com.joyintech.service.basicdata.multiportfoliomaintenance.service.MultiPortfolioMaintenance001Service;
import com.joyintech.service.basicdata.multiportfoliomaintenance.service.MultiPortfolioMaintenance002Service;
import com.joyintech.service.basicdata.stlsecreconpara.dto.StlSecReconParaDto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RestController("MultiPortfolioMaintenance002_trx")
@RequestMapping("/MultiPortfolioMaintenance002")
public class MultiPortfolioMaintenance002Controller extends BaseController {

    /**
     *  - service
     */
    @Autowired
    private MultiPortfolioMaintenance002Service multiPortfolioMaintenancea002Service;

    /**
     * 多组合树
     * @param paramMap
     * @return
     */
    @PostMapping("/getMutiptlPortfolioAcctnoTree")
    public List<TreeNode> getMutiptlPortfolioAcctnoTree(@RequestBody Map<String, Object> paramMap) {
        return multiPortfolioMaintenancea002Service.getMutiptlPortfolioAcctnoTree(paramMap);
    }
    /**
     * 多组合树
     * @param dto
     * @return
     */
    @PostMapping("/isExistPortfolio")
    public Integer isExistPortfolio(@RequestBody MutiptlPortfolioDetailDto dto) {
        return multiPortfolioMaintenancea002Service.isExistPortfolio(dto);
    }

    /**
     * 插入记录
     * @param dto
     * @return
     */
    @Transactional
    @DTXTransaction
    @PostMapping("insert")
    public Integer insert(@RequestBody MutiptlPortfolioDetailDto dto) {
        return multiPortfolioMaintenancea002Service.insert(dto);
    }
    /**
     * 删除记录
     * @param dto
     * @return
     */
    @Transactional
    @DTXTransaction
    @PostMapping("delete")
    public Integer delete(@RequestBody MutiptlPortfolioDetailDto dto) {
        return multiPortfolioMaintenancea002Service.delete(dto);
    }






}
