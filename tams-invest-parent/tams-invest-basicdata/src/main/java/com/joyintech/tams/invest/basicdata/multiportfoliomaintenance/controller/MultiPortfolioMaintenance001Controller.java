package com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.validation.groups.ValidateGroupDelete;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioTreeDto;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.service.MultiPortfolioMaintenance001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 多组合维护- 查询画面
 */
@Controller
@PageId("MultiPortfolioMaintenance001")
@RequestMapping("/invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance001")
public class MultiPortfolioMaintenance001Controller extends BaseController {

    /**
     * 对账参数设置 - service
     */
    @Autowired
    private MultiPortfolioMaintenance001Service multiPortfolioMaintenancea001Service;
    /**
     * 查询页面初始化
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        return super.init(model);
    }

    /**
     * 删除按钮点击时的check
     *
     * @param dto dto
     * @param result BindingResult
     * @return check结果
     * @see
     * @since 1.0
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody @Validated({
            ValidateGroupDelete.class}) MutiptlPortfolioTreeDto dto, BindingResult result) {
        return commonCheck(result, dto.getOperateType());
    }


    /**
     * 分页查询
     * @return
     */
    @ResponseBody
    @RequestMapping("findPage")
    public Page<MutiptlPortfolioDetailDto> findPage() {
        return multiPortfolioMaintenancea001Service.findPage(RequestUtil.getPageParam());
    }

    /**
     * 获取组合树结构数据
     * @return 画面元素
     * @throws Exception Exception
     * @since 1.0
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping("/getMutiptlPortfolioTree")
    public List<TreeNode> getMutiptlPortfolioTree()
    {
        return multiPortfolioMaintenancea001Service.getMutiptlPortfolioTree(RequestUtil.getPageParam());
    }
    /**
     * 删除方法
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    @RequestMapping("getDelete")
    @ResponseBody
    public AjaxResult getDelete(){
        String treeId = this.getParam("treeId");
        AjaxResult aResult =multiPortfolioMaintenancea001Service.getDelete(treeId);
        return aResult;
    }
}
