package com.joyintech.tams.invest.batch.extrdautosettle.processor;

import com.joyintech.batch.batchbase.baseclass.BaseProcessor;
import com.joyintech.batch.batchbase.baseclass.entity.NeedOperateEntity;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.invest.batch.extrdautosettle.dto.*;
import com.joyintech.tams.invest.batch.extrdautosettle.service.ManualSettleService;
import com.joyintech.tams.invest.batch.extrdautosettle.service.RtgsStatusRetService;
import com.joyintech.workflow.engine.constant.OperationType;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope("prototype")
public class ExTrdAutoSettleInfoProcessor extends BaseProcessor<NeedOperateEntity, ManualSettleDtoResult> {

    private static Logger logger = LoggerFactory.getLogger(ExTrdAutoSettleInfoProcessor.class);


    /**
     * 查询交收状态服务
     */
    @Autowired
    private RtgsStatusRetService rtgsStatusRetService;


    @Override
    public ManualSettleDtoResult process(NeedOperateEntity item) throws Exception {
        // 获取reader方法的返回值
        ExTrdAutoSettleInfoReaderResult readerResult = (ExTrdAutoSettleInfoReaderResult) item;
        //取得自动交收待处理数据
        List<ExTrdAutoSettleInfoDto> autoSettleInfoDtos = readerResult.getAutoSettleInfoDtos();

        //map<成交编号, List<手工交收数据>>
        Map<String, List<ExTrdAutoSettleInfoDto>> dealNoMap = autoSettleInfoDtos.stream().collect(
                Collectors.toMap(ExTrdAutoSettleInfoDto::getDealNo,
                        s -> {
                            List<ExTrdAutoSettleInfoDto> dtos = new ArrayList<>();
                            dtos.add(s);
                            return dtos;
                        },
                        (List<ExTrdAutoSettleInfoDto> v1, List<ExTrdAutoSettleInfoDto> v2) -> {
                            v1.addAll(v2);
                            return v1;
                        }));
        //定义成交编号set
        Set<String> dealNoSet = autoSettleInfoDtos.stream().map(ExTrdAutoSettleInfoDto::getDealNo).collect(Collectors.toSet());

        //调用交易端交收状态查询接口 TODO 待接口联调测试
        List<RtgsStatusRet> RtgsStatusRetList = (List<RtgsStatusRet>)rtgsStatusRetService.getRtgsStatusRetList(dealNoSet);

        //返回结果集
        List<ManualSettleDto> manualSettleDtoList =new ArrayList<>();

        if(RtgsStatusRetList != null){
            for (int i = 0; i < RtgsStatusRetList.size(); i++) {
                //手工交收数据
                List<ExTrdAutoSettleInfoDto> tmp_ExTrdAutoSettleInfoDtoList = dealNoMap.get(RtgsStatusRetList.get(i).getDealNo());
                if(tmp_ExTrdAutoSettleInfoDtoList.size()>0){
                    //组装手工交收参数
                    ManualSettleDto manualSettleDto = buildManualSettleDtoByList(tmp_ExTrdAutoSettleInfoDtoList);

                    if(StringUtil.equals(RtgsStatusRetList.get(i).getStatus(), DictCodeListExt3.交收状态_已交收.getValue())){
                        manualSettleDto.setPageModel(DictCodeListExt3.交收状态_已交收.getValue());

                    }else if(StringUtil.equals(RtgsStatusRetList.get(i).getStatus(), DictCodeListExt3.交收状态_交收失败.getValue())){
                        manualSettleDto.setPageModel(DictCodeListExt3.交收状态_交收失败.getValue());

                    }else{
                        logger.info("交收状态不是已交收或交收失败，不处理");
                    }
                    manualSettleDtoList.add(manualSettleDto);
                }
            }
        }


        ManualSettleDtoResult result = new ManualSettleDtoResult();
        result.setManualSettleDtoList(manualSettleDtoList);
        return result;
    }

    /**
     * 组装手工交收参数
     * @return
     */
    private ManualSettleDto buildManualSettleDtoByList(List<ExTrdAutoSettleInfoDto> exTrdAutoSettleInfoDtoList){
        ManualSettleDto manualSettleDto = new ManualSettleDto();
        List<ManualSettleDto> selectedItem = new ArrayList<>();
        for (ExTrdAutoSettleInfoDto exTrdAutoSettleInfoDto:exTrdAutoSettleInfoDtoList) {
            ManualSettleDto temp = new ManualSettleDto();
            temp.setSettleSeqNo(exTrdAutoSettleInfoDto.getSettleSeqNo());
            temp.setSettleStatus(exTrdAutoSettleInfoDto.getSettleStatus());
            temp.setOrderId(exTrdAutoSettleInfoDto.getOrderId());
            temp.setTradeId(exTrdAutoSettleInfoDto.getTradeId());
            temp.setTradeDate(exTrdAutoSettleInfoDto.getTradeDate());
            temp.setSettleDate(exTrdAutoSettleInfoDto.getSettleDate());
            temp.setPortfolioId(exTrdAutoSettleInfoDto.getPortfolioId());
            temp.setAcctNo(exTrdAutoSettleInfoDto.getAcctNo());
            temp.setSecAcctId(exTrdAutoSettleInfoDto.getSecAcctId());
            temp.setTradeMarket(exTrdAutoSettleInfoDto.getTradeMarket());
            temp.setTradePlat(exTrdAutoSettleInfoDto.getTradePlat());
            temp.setFinprodId(exTrdAutoSettleInfoDto.getFinprodId());
            temp.setSettleNum(exTrdAutoSettleInfoDto.getSettleNum());
            temp.setSettleAmt(exTrdAutoSettleInfoDto.getSettleAmt());
            temp.setPrin(exTrdAutoSettleInfoDto.getPrin());
            temp.setmTradeAmt(exTrdAutoSettleInfoDto.getmTradeAmt());
            temp.setPledgeNumber(exTrdAutoSettleInfoDto.getPledgeNumber());
            temp.setPledgeId(exTrdAutoSettleInfoDto.getPledgeId());
            temp.setbPledgeId(exTrdAutoSettleInfoDto.getbPledgeId());
            temp.setPledgeName(exTrdAutoSettleInfoDto.getPledgeName());
            temp.setBusiType(exTrdAutoSettleInfoDto.getBusiType());
            temp.setCounterId(exTrdAutoSettleInfoDto.getCounterId());
            temp.setEntrustNo(exTrdAutoSettleInfoDto.getEntrustNo());
            temp.setFinprodName(exTrdAutoSettleInfoDto.getFinprodName());
            temp.setFinprodMarketId(exTrdAutoSettleInfoDto.getFinprodMarketId());
            temp.setPortfolioName(exTrdAutoSettleInfoDto.getPortfolioName());
            temp.setCounterName(exTrdAutoSettleInfoDto.getCounterName());
            selectedItem.add(temp);
        }
        manualSettleDto.setSelectedItem(selectedItem);
        return  manualSettleDto;
    }


}
