/*
 * 文件名：HomePageSeachDataMapper.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：dingzhipeng
 * 修改时间：2017年9月19日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */
 
package com.joyintech.tams.report.home.dao;

import java.util.List;
import java.util.Map;

import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.joyintech.tams.report.home.dto.InfoAreaDto;
import com.joyintech.tams.report.home.dto.SubmitInformationRegistrationDto;


/**
 * 主页查询各种数据所用的Mapper
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2017年9月19日
 * @see ReportHomeMapper
 * @since 1.0
 */
@Repository
public interface ReportHomeMapper {

    /**
     * <一句话功能简述>
     * 查询信息表区域的数据
     * @param parmMap 参数Map
     * @return 返回存储有数据的List
     * @see
     * @since 1.0
     */
    List<InfoAreaDto> searchInfoArea(Map<String, Object> parmMap);

    /**
     * <一句话功能简述>
     * 查询可批量导出模板区域数据
     * @return 返回存储有数据的List
     * @see
     * @since 1.0
     */
    List<InfoAreaDto> searcheBatchxportTemplates(Map<String, Object> parmMap);

    List<SubmitInformationRegistrationDto> searchReportCheckNum(Map<String, Object> parmMap);

	/**
	 * @Description 查询报送信息登记菜单下的所有子菜单
	 * @return List<String> 
	 * @version 1.7 2019年5月27日
	 */
	List<String> selectMenulist(Map<String, Object> param);
	
	/**
	 * @Description 查询投资者身份统计数据
	 * @param parmMap
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbEquSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 查询投资持有信息统计信息
	 * @param parmMap
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbInvestholdInfoSub(Map<String, Object> parmMap);
	
	
	/**
	 * @Description 查询投资者明细信息统计数据
	 * @param parmMap
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbEqudetSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品申报统计信息
	 * @param parmMap
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbPrdDeclareSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品发行统计信息
	 * @param parmMap
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbPrdIssueSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品募集统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbPrdRaisetotSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品交易统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbPrdDealSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品终止统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbPrdEndSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 底层持仓统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbBotAstPosSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 资产要素统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbAssetInfoSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 投资交易统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbInvestTradeSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 资产估值统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbAstValSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品月报统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbMonthReport(Map<String, Object> parmMap);
	
	/**
	 * @Description 人行报表统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcReport(Map<String, Object> parmMap);
	
	/**
	 * @Description 资产池基本信息统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcPrdAccountSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品信息统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcPrdBaseinfoSub(Map<String, Object> parmMap);

	/**
	 * @Description 产品信息统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcPrdBaseinfoSubNew(Map<String, Object> parmMap);
	
	/**
	 * @Description 产品起始募集信息统计
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcInitProdRaisingSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 资金募集信息统计
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcProdRaisingSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 资产池终止信息统计 
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcPrdAccountEndSub(Map<String, Object> parmMap);
	
	/**
	 * @Description 人行产品终止信息统计
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcPrdBaseinfoEndSub(Map<String, Object> parmMap);

	/**
	 * @Description 人行产品终止信息统计
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcPrdBaseinfoEndSubNew(Map<String, Object> parmMap);

	/**
	 * @Description 销售结算资金交易登记
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectSalesTransactionMaintenanceRegist001(Map<String, Object> parmMap);

	/**
	 * @Description 理财业务销售数据月度统计表登记
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectSellMonthReport(Map<String, Object> parmMap);


	
	/**
	 * @Description 资产负债信息统计
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectPbcReportAsset(Map<String, Object> parmMap);
	
	/**
	 * @Description G06报表统计信息
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbG06(Map<String, Object> parmMap);

	/**
	 * @Description
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbInst(Map<String, Object> parmMap);

	/**
	 * @Description
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbIder(Map<String, Object> parmMap);

	/**
	 * @Description
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 * @version 1.7 2019年6月22日
	 */
	List<SubmitInformationRegistrationDto> selectChbIrew(Map<String, Object> parmMap);

	/**
	 * @Description
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 */
	List<SubmitInformationRegistrationDto> selectChbAgentInstitution(Map<String, Object> parmMap);

	/**
	 * @Description
	 * @param parmMap
	 * @return
	 * @return List<SubmitInformationRegistrationDto>
	 */
	List<SubmitInformationRegistrationDto> selectChbAgentPrd(Map<String, Object> parmMap);

	List<SubmitInformationRegistrationDto> selectSubsidiaMonth(Map<String, Object> parmMap);

}
