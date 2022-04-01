/*
 * 文件名：EtrBondFirstBiddingMapper.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：yangjm
 * 修改时间：2019年5月6日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.exchange.entrust.etrexchange.fjyywzl.dao;

import com.joyintech.tams.exchange.entrust.etrexchange.fjyywzl.dto.EtrExchangeTradeFjyywzlZqzyDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *  场内交易指令Mapper
 * 〈功能详细描述〉
 * @author wangyang1
 * @version 1.0 2021年12月21日
 * @see EtrExchangeTradeFjyywzl001Mapper
 * @since 1.0
 */
@Repository
public interface EtrExchangeTradeFjyywzl001Mapper {

	List<EtrExchangeTradeFjyywzlZqzyDto> findZqzyPositionsPage(Map<String, Object> pageParam);

	List<EtrExchangeTradeFjyywzlZqzyDto> findZqzyPositionsPageNew(Map<String, Object> pageParam);


}
