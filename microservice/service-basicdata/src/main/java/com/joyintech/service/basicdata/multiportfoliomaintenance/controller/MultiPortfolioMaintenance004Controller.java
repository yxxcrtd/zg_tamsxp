/*
 * 文件名：ReportTreeMenuConfiguration001Controller.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：估值核算系统V1.0
 * 修改人：huwei
 * 修改时间：2018年11月6日
 * 修改内容：新建
 * 系统名称：估值核算系统V1.0
 */
 
package com.joyintech.service.basicdata.multiportfoliomaintenance.controller;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.service.basicdata.multiportfoliomaintenance.service.MultiPortfolioMaintenance001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 增加子级
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author huwei
 * @version 1.0 2018年11月6日
 * @see MultiPortfolioMaintenance004Controller
 * @since 1.0
 */
@RestController(value="serviceMultiPortfolioMaintenance004Controller")
@RequestMapping("/MultiPortfolioMaintenance004")
public class MultiPortfolioMaintenance004Controller extends BaseController{
    
    @Autowired
    private MultiPortfolioMaintenance001Service multiPortfolioMaintenancea001Service;
   
    
    /**
     * 增加父节点
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    @RequestMapping("/addSonNode")
    @ResponseBody
    public AjaxResult addSonNode(@RequestBody Map<String, Object> param){
        AjaxResult ajaxResult=
                multiPortfolioMaintenancea001Service.addSonNode(param);
        return ajaxResult;
    }
}
