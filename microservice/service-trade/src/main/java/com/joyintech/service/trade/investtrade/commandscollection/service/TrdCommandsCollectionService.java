/*
 * 文件名：TrdCommandsCollectionService.java
 * 版权：Copyright 2018-2019 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.1.2
 * 修改人：王贵奇
 * 修改时间：2019年12月31日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.1.2
 */

package com.joyintech.service.trade.investtrade.commandscollection.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joyintech.system.base.entity.SysPageDictlist;
import com.joyintech.tams.constant.DictCodeListExt3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.listener.FileAttachmentProcess;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.service.trade.investtrade.commandscollection.dao.TrdCommandsCollectionMapper;
import com.joyintech.service.trade.investtrade.commandscollection.dto.TrdCommandsCollection001Dto;
import com.joyintech.service.trade.investtrade.commandscollection.dto.TrdCommandsCollection002Dto;
import com.joyintech.service.trade.investtrade.commandscollection.dto.TrdCommandsCollection003Dto;

/**
 *指令汇总微服务端Service
 * @author 王贵奇
 * @version 1.0 2019年12月31日
 * @see TrdCommandsCollectionService
 * @since 1.0
 */
@Service
public class TrdCommandsCollectionService extends BaseService<TrdCommandsCollection001Dto> {

    /**
     * 文件附件处理接口注入
     */
    @Autowired(required = false)
    private FileAttachmentProcess fileAttachmentProcess;

    /**
     * 指令汇总Mapper
     */
	@Autowired
	private TrdCommandsCollectionMapper trdCommandsCollectionMapper;

	/**
     * 查询汇总指令：除 债券，基金，股票
     * @param param 包括资产大类和输入的查询条件
     * @param page page
     * @param length length
     * @return Page<TrdCommandsCollection001Dto> 汇总指令列表
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
	public Page<TrdCommandsCollection001Dto>  selectForPage(int page, int length,Map<String, Object> pageParam)
	{
		String assetType = pageParam.get("assetType").toString();
		if (MultilevelDataAuthorityUtil.isEnabled()) {
            List<String> institutionList = LoginUserUtil.getTreeInstCode(); 
            pageParam.put("institutionList",institutionList); 
        }
		pageParam.put("ENTRUST_CLASS_01", DictCodeListExt3.投资指令类型_单组合单资产指令.getValue());
    	if("F01".equals(assetType) || "F06".equals(assetType) || "F10".equals(assetType) ) {
    		return super.queryForPage(page, length, () -> trdCommandsCollectionMapper.selectForCommonPage(pageParam));
    	}else {
    		return super.queryForPage(page, length, () -> trdCommandsCollectionMapper.selectForPage(pageParam));
    	}
	}
	
    /**
     * 查询委托详情列表
     * @param param 查询参数
     * @return List<TrdCommandsCollection002Dto> 委托详情列表
     * @see
     * @since 1.0
     */
	public List<TrdCommandsCollection002Dto> selectEntrustList(Map<String, Object> param) {
		List<TrdCommandsCollection002Dto> entrustList = new ArrayList<>();
		String assetType = param.get("assetType").toString();
    	if("F01".equals(assetType) || "F06".equals(assetType) || "F10".equals(assetType) ) {
    		entrustList = trdCommandsCollectionMapper.selectEntrustCommonList(param);
    	}else {
    		entrustList = trdCommandsCollectionMapper.selectEntrustList(param);
    	}
		//添加附件
		for(TrdCommandsCollection002Dto dto: entrustList) {
			dto.setFiles(fileAttachmentProcess.fetchByBusinessCode(dto.getEntrustId()));
		}
		return entrustList;
	}
	
	/**
     * 委托质押券详情查询
     * @param param 包括委托代码
     * @return List<TrdCommandsCollection003Dto> 委托质押券详情
     * @see
     * @since 1.0
     */
    public List<TrdCommandsCollection003Dto> selectPledgeList(Map<String, Object> param) {
        return trdCommandsCollectionMapper.selectPledgeList(param);
    }

    /**
	 list.add(dto);
     * 资产代码联想查询各类资产对应的资产代码
     * @param param 包括资产大类和输入的资产名称
     * @return List<SuggestDto> 联想框下拉选项
     * @see
     * @since 1.0
     */
	public List<SuggestDto> getFinprodMarketIds(Map<String, Object> param) {
		List<SuggestDto> list = trdCommandsCollectionMapper.getFinprodMarketIds(param);
		List<SuggestDto> list1 = trdCommandsCollectionMapper.getCommomFinprodMarketIds(param);
		if(list1!=null && list1.size()>0) {
			for(SuggestDto dto : list1) {
			}
		}
		return list;
	}
	/**
	 * 根据SysPageDictlist表来塞交易方向的值
	 * @param subPageId 子页面id
	 * @return List<SysPageDictlist> 字典控制显示详情
	 * @see
	 * @since 1.0
	 */
	public List<SysPageDictlist> getPSControl(String subPageId) {
		List<SysPageDictlist> sysPageDictlist = trdCommandsCollectionMapper.getPSControl(subPageId);
		return sysPageDictlist;
	}
}
