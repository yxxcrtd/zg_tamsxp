/*
 * 文件名：EquityInfo001Mapper.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年9月16日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */
 
package com.joyintech.tams.report.submitmaintenance.equityinfo.dao;

import java.util.List;
import java.util.Map;

import com.joyintech.tams.common.base.entity.ChbEquTmp;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.dto.InvestorsHoldInfo001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.report.interf.cnbond.dto.InvestorsHoldInfo001InterfDto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;

/**
 * 三要素去重mapper
 * @author Administrator
 * @version 1.0 2019年3月21日
 * @see EquityInfoDistinctMapper
 * @since 1.0
 */
@Repository
public interface EquityInfoDistinctMapper {
	 /**
     * 根据三要素查询已报送数据
     * @param list
     * @return
     */
    List<EquityInfoRegist001Dto> queryAllEquityInfoConStrastYbs(List<Map<String, String>> list);
    EquityInfoRegist001Dto queryAllEquityInfoConStrastYbsSingle(String keys);
    /**
     * 查询所有已报送数据
     * @return
     */
    List<EquityInfoRegist001Dto> queryAllEquityInfo(List<ChbEquTmp> list);

    /**
     * 查询所有已报送数据优化
     * @return
     */
    List<EquityInfoRegist001Dto> queryAllEquityInfoMax();

    /**
     * 根据三要素查询维护表数据
     * @param list
     * @return
     */
    
    List<EquityInfo001Dto> queryEquityInfoList(List<ChbEquTmp> list);

    /**
     * 根据三要素查询维护表数据优化
     * @param list
     * @return
     */

    List<EquityInfo001Dto> queryEquityInfoListMax();
    
    EquityInfoRegist001Dto queryEquityInfoByIndentifymarkAndBankCode(EquityInfoRegist001Dto qmodel);
    
    List<EquityInfo001Dto> queryRepeatEquityInfoUnSubmit(@Param("keys")List<String> keys);
    /**
     * 查询登记表数据 出检查结果用
     * @return
     */
    List<EquityInfoRegist001Dto> queryEquityInfoRegistByPkUnsubmit(@Param("newKeyList")List<EquityInfo001Dto> newKeyList );
    
}
