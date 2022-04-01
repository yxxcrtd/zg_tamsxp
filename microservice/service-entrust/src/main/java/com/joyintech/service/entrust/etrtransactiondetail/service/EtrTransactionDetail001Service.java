package com.joyintech.service.entrust.etrtransactiondetail.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.service.entrust.common.dao.EntrustCommonPersonSelectMapper;
import com.joyintech.service.entrust.etrinvestallinfo.dto.EtrInvestAllInfoDto;
import com.joyintech.service.entrust.etrtransactiondetail.dao.EtrTransactionDetailMapper;
import com.joyintech.service.entrust.etrtransactiondetail.dto.EtrTransactionDetailDto;
import com.joyintech.service.entrust.etrtransactiondetail.dto.TrdProductDealDetailDto;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;

@Service
public class EtrTransactionDetail001Service extends BaseService{
    /**
     * 自定义mapper
     */
    @Autowired
    private EtrTransactionDetailMapper etrTransactionDetailMapper;
    
    /**
     * 查询mapper
     */
    @Autowired
    private EntrustCommonPersonSelectMapper entrustCommonPersonSelectMapper;
    
    /**
     * 
     * 获取组合代码的联想文本框
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    public List<SuggestDto> getentrustId(String entrustId){
        return etrTransactionDetailMapper.getentrustId(entrustId);
    }
    /**
     * 
     * 获取资产编号的联想文本框
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    public List<SuggestDto> getfinprodId(String finprodId){
        return etrTransactionDetailMapper.getfinprodId(finprodId);
        
    }
    /**
     * 
     * 所有投资委托信息查询
     * <功能详细描述>
     * @param param
     * @return
     * @throws Exception 
     * @see
     * @since 1.0
     */
    public Page<EtrTransactionDetailDto> selectForPage(int page, int length,Map<String, Object> param) throws Exception{
    	param.put("TRADE_PLACE_03", DictCodeListExt1.交易场所_上海证券交易所.getValue());
    	param.put("TRADE_PLACE_04", DictCodeListExt1.交易场所_深圳证券交易所.getValue());
    	
    	param.put("ASSET_TYPE_01", DictCodeList.平安资产类型_债券.getValue());
    	param.put("ASSET_TYPE_02", DictCodeList.平安资产类型_基金.getValue());
    	param.put("ASSET_TYPE_07", DictCodeList.平安资产类型_质押式回购.getValue());
    	param.put("ASSET_TYPE_11", DictCodeListExt2.平安资产类型_股票.getValue());
    	param.put("ASSET_TYPE_20", DictCodeListExt3.平安资产类型_期货.getValue());
    	
    	param.put("ENTRUST_STATUS_03", DictCodeList.委托状态_已接收.getValue());
    	param.put("ENTRUST_STATUS_04", DictCodeList.委托状态_已完成.getValue());
    	param.put("ENTRUST_STATUS_06", DictCodeList.委托状态_已关闭.getValue());
//    	param.put("ENTRUST_STATUS_07", DictCodeList.委托状态_已撤回.getValue());
    	
    	param.put("PS_T68", DictCodeListExt3.交易方向_质押券入库.getValue());
        param.put("PS_T69", DictCodeListExt3.交易方向_质押券出库.getValue());
    	
    	param.put("ASSET_TYPE_08", DictCodeList.平安资产类型_拆借.getValue());
        param.put("ASSET_TYPE_10", DictCodeList.平安资产类型_买断式回购.getValue());
        param.put("ASSET_TYPE_12", DictCodeListExt2.平安资产类型_债券借贷.getValue());
        param.put("ASSET_TYPE_16", DictCodeListExt2.平安资产类型_利率互换.getValue());
        param.put("PS_T01", DictCodeList.交易方向_买入.getValue());
        param.put("assetBond", DictCodeList.平安资产类型_债券.getValue());
        param.put("ENTRUST_CLASS_02", DictCodeListExt3.投资指令类型_多组合单资产指令.getValue());
        String portfolioId = (String)param.get("portfolioId");
        if(StringUtil.isNotEmpty(portfolioId)){
            param.put("portfolioIdList", StringUtil.splitToList(GlobalConstant.COMMA,portfolioId));
        }
         //勾选导出
        if (StringUtil.isNotEmpty(param.get("selectedItem"))){     
            List<String> selectedItem = StringUtil.splitToList(",",(String)param.get("selectedItem"));           
            if(selectedItem.size() > 0){
                param.put("etridList",selectedItem);
            }
        }
        Page<EtrTransactionDetailDto> pageDto = super.queryForPage(page, length, () -> etrTransactionDetailMapper.select(param));
        List<EtrTransactionDetailDto> dtoList = pageDto.getRoot();
        for(EtrTransactionDetailDto dto : dtoList){
            if(StringUtil.isNotEmpty(dto.getInvManagerId())) {
                String realName = entrustCommonPersonSelectMapper.getRealName(dto.getInvManagerId());
                dto.setInvManagerId(realName); 
            }
        }
        pageDto.setRoot(dtoList);
        return pageDto;
    }
    
    /**
     * 
     * @param page
     * @param length
     * @param param
     * @return
     * @throws Exception
     */
    public Page<TrdProductDealDetailDto> selectTransDetailPage(int page, int length, Map<String, Object> param)
			throws Exception {
    	param.put("TRADE_PLACE_03", DictCodeListExt1.交易场所_上海证券交易所.getValue());
    	param.put("TRADE_PLACE_04", DictCodeListExt1.交易场所_深圳证券交易所.getValue());
        param.put("ENTRUST_CLASS_02", DictCodeListExt3.投资指令类型_多组合单资产指令.getValue());
        Page<TrdProductDealDetailDto> pageDto = super.queryForPage(page, length,() -> etrTransactionDetailMapper.selectTransDetailPage(param));
		return pageDto;
	}
}
