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

import com.joyintech.framework.base.BaseController;
import com.joyintech.service.basicdata.multiportfoliomaintenance.service.MultiPortfolioMaintenance001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 增加父级
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author huwei
 * @version 1.0 2018年11月6日
 * @see MultiPortfolioMaintenance003Controller
 * @since 1.0
 */
@RestController(value="serviceMultiPortfolioMaintenance003Controller")
@RequestMapping("/MultiPortfolioMaintenance003")
public class MultiPortfolioMaintenance003Controller extends BaseController{
    
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
    @PostMapping("/addParentNode")
    public void addParentNode(@RequestBody Map<String, Object> param){
        multiPortfolioMaintenancea001Service.addParentNode(param);
    }
}
