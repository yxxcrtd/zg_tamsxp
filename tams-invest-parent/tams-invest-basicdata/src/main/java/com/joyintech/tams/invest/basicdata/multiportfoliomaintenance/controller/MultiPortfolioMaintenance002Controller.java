package com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.select.TreeNode;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.dto.MutiptlPortfolioDetailDto;
import com.joyintech.tams.invest.basicdata.multiportfoliomaintenance.service.MultiPortfolioMaintenance002Service;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@PageId("MultiPortfolioMaintenance002")
@RequestMapping("/invest/basicdata/multiportfoliomaintenance/MultiPortfolioMaintenance002")
public class MultiPortfolioMaintenance002Controller extends BaseController {
    /**
     * 对账参数设置 - service
     */
    @Autowired
    private MultiPortfolioMaintenance002Service multiPortfolioMaintenance002Service;

    // 平台日期接口
    @Autowired
    private IPlatformDate iPlatformDate;

    /**
     * 查询页面初始化
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {

        String treeId = this.getParam("treeId");
        // 平台日
        model.addAttribute("iPlatformDate", iPlatformDate.getSysDate());
        model.addAttribute("treeId", treeId);
        return super.init(model);
    }

    /**
     *
     * 查询按钮查询事件
     * <功能详细描述>
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @RequestMapping("getMutiptlPortfolioAcctnoTree")
    @ResponseBody
    public List<TreeNode> getMutiptlPortfolioAcctnoTree(){
        return multiPortfolioMaintenance002Service.getMutiptlPortfolioAcctnoTree(RequestUtil.getPageParam());
    }


    /**
     * 保存时check数据
     * @return
     */
    @ResponseBody
    @RequestMapping("check")
    public AjaxResult check(@RequestBody  MutiptlPortfolioDetailDto dto) {
        return multiPortfolioMaintenance002Service.check(dto);
    }

    /**
     * 提交按钮点击
     *
     * @param dto dto
     * @throws Exception Exception
     * @return check结果
     * @see
     * @since 1.0
     */
    @SuppressWarnings("deprecation")
    @ResponseBody
    @RequestMapping("insert")
    public AjaxResult insert(@RequestBody MutiptlPortfolioDetailDto dto)
            throws Exception {
        multiPortfolioMaintenance002Service.isnertOrDelete(dto);
        return new AjaxResult();
    }
    /**
     * 提交按钮点击
     *
     * @param dto dto
     * @throws Exception Exception
     * @return check结果
     * @see
     * @since 1.0
     */
    @SuppressWarnings("deprecation")
    @ResponseBody
    @RequestMapping("delete")
    public AjaxResult delete(@RequestBody MutiptlPortfolioDetailDto dto)
            throws Exception {
        multiPortfolioMaintenance002Service.isnertOrDelete(dto);
        return new AjaxResult();
    }



}
