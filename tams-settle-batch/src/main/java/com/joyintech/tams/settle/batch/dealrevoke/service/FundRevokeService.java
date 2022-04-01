package com.joyintech.tams.settle.batch.dealrevoke.service;

import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.settle.batch.datagather.service.StlDataGatherService;
import com.joyintech.tams.settle.batch.dealrevoke.dao.InvestDealRevokeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 何阳阳
 * @date 2020/8/4
 * @see FundRevokeService
 * @since 1.0
 */
@Service("02" + BaseRevokeService.REVOKE_BEAN_NAME)
@Lazy
public class FundRevokeService implements BaseRevokeService {

    @Autowired
    private InvestDealRevokeService investDealRevokeService;

    @Autowired
    private StlDataGatherService stlDataGatherService;

    // modify by etliu 20220227 start
    // 广农商二阶段 GNS-890:【广农商场内清算】场内债券存续期交易重复跑批时，未对原交易数据进行撤销操作
    @Autowired
    private InvestDealRevokeMapper investDealRevokeMapper;
    // modify by etliu 20220227 end


    /**
     * 基金交易撤销
     * @param tradeIdMap 交易编号 交易方向
     * @param totalTradeIdMap 汇总交易编号 交易方向
     */
    @Override
    public void doRevoke(Map<String,String> tradeIdMap, Map<String,String> totalTradeIdMap) {
        // 参考基金交易撤销逻辑，生成已生效的撤销交易数据，同时反向更新头寸信息、持仓信息以及委托完成信息。注意不要调用对外发送逻辑。
        if(tradeIdMap != null && !tradeIdMap.isEmpty() && tradeIdMap.size()>0) {
            List<String> tradeIdList =  tradeIdMap.entrySet().stream()
                    .map(item->item.getKey()).collect(Collectors.toList());
            Map<String, Object> param = new HashMap<>();
            param.put("tradeIdList", tradeIdList);
            param.put("status", DictCodeListExt1.交易流程状态_撤销.getValue());
            param.put("busiType", DictCodeList.共通业务类型_基金类资产交易.getValue());
            stlDataGatherService.excutePosition(param);
            investDealRevokeService.revokeDeal(tradeIdList);
        }
        // 参考基金分红交易撤销逻辑，生成已生效的撤销交易数据，同时反向更新头寸信息、持仓信息以及委托完成信息。注意不要调用对外发送逻辑。
        if(totalTradeIdMap != null && !totalTradeIdMap.isEmpty() && totalTradeIdMap.size()>0) {
            List<String> totalTradeIdList =  totalTradeIdMap.entrySet().stream()
                    .map(item->item.getKey()).collect(Collectors.toList());
            Map<String, Object> param = new HashMap<>();
            param.put("totalTradeIdList", totalTradeIdList);
            param.put("status", DictCodeListExt1.交易流程状态_撤销.getValue());
            param.put("busiType", DictCodeList.共通业务类型_基金类资产交易.getValue());

            stlDataGatherService.excutePosition(param);
            investDealRevokeService.revokeDealTotal(totalTradeIdList);
        }
    }

    @Override
    public String getBusiType() {
        return DictCodeListExt1.证券品种_基金.getValue();
    }
}
