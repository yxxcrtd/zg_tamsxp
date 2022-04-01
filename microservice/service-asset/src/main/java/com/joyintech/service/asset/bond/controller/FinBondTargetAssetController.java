/*
 * 文件名：FinBondTargetAssetController.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：zhangnian
 * 修改时间：2018年9月20日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.asset.bond.controller;

import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.service.asset.bond.dto.FinBondTargetAssetDto;
import com.joyintech.service.asset.bond.service.FinBondTargetAssetService;
import com.joyintech.service.asset.common.dto.FinPayInterestStdInfoDto;
import com.joyintech.tams.common.base.entity.FinProduct;
import com.joyintech.tams.component.base.core.dto.BaseFinProductBaseInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 债券标的信息
 * @author zhangnian
 * @version 1.0 2018年9月20日
 * @see FinBondTargetAssetController
 * @since 1.0
 */
@RestController("FinBondTargetAssetController_dtx")
@RequestMapping("/FinBondTargetAsset")
public class FinBondTargetAssetController {

    @Autowired
    private FinBondTargetAssetService finBondTargetAssetService;
    
    /**
     * 查询
     * @param param
     * @param page
     * @param length
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<FinBondTargetAssetDto> selectAll(@RequestBody Map<String, Object> param,
            @PathVariable("page") int page, @PathVariable("length") int length){
        return finBondTargetAssetService.selectForPage(page, length, param);
    }
    /**
     * 插入
     * @param dto
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("insert")
    public void insert(@RequestBody FinBondTargetAssetDto dto) {
        finBondTargetAssetService.insert(dto);
    }
    
    /**
     * 修改
     * @param dto
     * @throws Exception
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("update")
    public void update(@RequestBody FinBondTargetAssetDto dto){
        finBondTargetAssetService.update(dto);
    }
    
    /**
     * 删除
     * @param dto
     * @throws Exception
     * @see
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("delete")
    public void delete(@RequestBody FinBondTargetAssetDto dto){
        finBondTargetAssetService.delete(dto);
    }
    
    /**
     * 获取基本信息--修改/详细
     * @param secId
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @GetMapping("getBasicInfo")
    public FinBondTargetAssetDto getBasicInfo(String finprodId, String finprodType) {
        return finBondTargetAssetService.getBasicInfo(finprodId, finprodType);
    }
    // 债券新建新增快捷录入功能 add by renping 20191010 start
    /**
     * 获取基本信息--查询各个组件业务表数据
     * @param entity FinBondTargetAssetDto
     * @return entity
     * @see
     * @since 1.0
     */
    @PostMapping("searchData")
    public FinBondTargetAssetDto searchData(@RequestBody FinBondTargetAssetDto entity){
        finBondTargetAssetService.searchData(entity);
        return entity;
    }
    // 债券新建新增快捷录入功能 add by renping 20191010 end
    /**
     * 市场代码是否重复
     * <功能详细描述>
     * @param finprodId
     * @return
     * @see
     * @since 1.0
     */
    @GetMapping("selectCountByfinprodMarketId")
    public int selectCountByfinprodMarketId(String finprodMarketId) {
        return finBondTargetAssetService.selectCountByfinprodMarketId(finprodMarketId);
    }

    /**
     * 获取新建界面的以及市场投标债券代码联想下拉框
     * 
     * @author wangguiqi
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    @PostMapping("getfinprodIdForAdd/{page}/{length}")
    public Page<SelectOptionDto> getfinprodIdForAdd(@RequestBody Map<String, Object> param,@PathVariable("page") int page,
                                                    @PathVariable("length") int length)
    {
        return finBondTargetAssetService.getfinprodIdForAdd(page,length,param);
    }
    
    @GetMapping("isHqTrade")
    public int isHqTrade(String finprodId) {
		return finBondTargetAssetService.isHqTrade(finprodId);
	}
    /**
     * 金融产品代码是否重复
     * <功能详细描述>
     * @param finprodId
     * @return
     * @see
     * @since 1.0
     */
    @GetMapping("selectCountByfinprodId")
    public int selectCountByfinprodId(String finprodId) {
        return finBondTargetAssetService.selectCountByfinprodId(finprodId);
    }

    /**
     * 市场投标债券 是否重复
     * <功能详细描述>
     * @param oFinprodId
     * @return
     * @see
     * @since 1.0
     */
    @GetMapping("selectCountByOFinprodId")
    public int selectCountByOFinprodId(String oFinprodId,String finprodId) {
        return finBondTargetAssetService.selectCountByOFinprodId(oFinprodId,finprodId);
    }

    /**
     * 根据资产代码查询是否是上市标的
     *
     * @param finprodId
     * @return 是否是上市标的，大于0则为是
     * @see
     * @since 1.0
     */
    @GetMapping("selectIsIssueAsset")
    public int selectIsIssueAsset(String finprodId) {
        return finBondTargetAssetService.selectIsIssueAsset(finprodId);
    }
    /**
     * 查询资产相关行权计划
     * <功能详细描述>
     * @param finprodId
     * @return 资产相关计划
     * @see
     * @since 1.0
     */
    @GetMapping("getFinPlanList")
    public FinBondTargetAssetDto getFinPlanList(String finprodId) {
        return finBondTargetAssetService.getFinPlanList(finprodId);
    }


    /**
     * add by xxg 20210721
     * 根据产品代码得到起息日和到期日
     * @param oFinprodId 产品代码
     * @return
     */
    @GetMapping("getMdateAndVdate")
    public FinBondTargetAssetDto getMdateAndVdate(String oFinprodId){
        FinBondTargetAssetDto dto = new FinBondTargetAssetDto();
        FinProduct finProduct = finBondTargetAssetService.getMdateAndVdate(oFinprodId);
        if(finProduct != null){
            dto.setMdate(finProduct.getMdate());
            dto.setVdate(finProduct.getVdate());
            dto.setoFinprodId(finProduct.getoFinprodId());
        }
        return dto;
    }



}
