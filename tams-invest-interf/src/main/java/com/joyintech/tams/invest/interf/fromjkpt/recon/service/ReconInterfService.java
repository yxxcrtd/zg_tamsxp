package com.joyintech.tams.invest.interf.fromjkpt.recon.service;

/**
 * @author zznn
 * @date 2021/10/25
 */

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.tams.common.base.dao.StlSecReconDealMapper;
import com.joyintech.tams.common.base.entity.StlSecRecon;
import com.joyintech.tams.common.base.entity.StlSecReconPara;
import com.joyintech.tams.constant.DictCodeListStl;
import com.joyintech.tams.invest.interf.fromjkpt.recon.dao.ReconInterfMapper;
import com.joyintech.tams.invest.interf.fromjkpt.recon.dto.StlCashReconDto;
import com.joyintech.tams.invest.interf.fromjkpt.recon.dto.StlSecReconDealDto;
import com.joyintech.tams.invest.interf.fromjkpt.recon.dto.StlSecpReconDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReconInterfService {

    @Autowired
    private ReconInterfMapper reconInterfMapper;

    @Autowired
    private StlSecReconDealMapper stlSecReconDealMapper;

    public int deleteStlCashRecon(Map<String,Object> param){
       return  reconInterfMapper.deleteStlCashRecon(param);
    }

    public int deleteStlSecpRecon(Map<String,Object> param){
        return  reconInterfMapper.deleteStlSecpRecon(param);
    }
    public List<StlCashReconDto> listStlCashRecon(Map<String,Object> param){
        return reconInterfMapper.listStlCashRecon(param);
    }

    public List<StlSecpReconDto> listStlSecpRecon(Map<String,Object> param){
        return reconInterfMapper.listStlSecpRecon(param);
    }


    public void executerecon(Map<String,Object> param){
        param.put("RECON_PARA_CODE_02", DictCodeListStl.参数代码_对账维度.getValue());

        setParam(param);
        //对于协议回购不管参数配置的是什么，都是委托汇总维度
        if(param.containsKey("collect")||param.containsKey("collectEntrustId")||param.containsKey("collectByMarketId")){
            Map<String,Object> paramdetail = new HashMap<>();
//            paramdetail.put("execDate",param.get("execDate"));
//            List<StlSecReconDealDto> stlSecReconDealDetaileDtos = reconInterfMapper.listStlSecReconDealIntegratedRepDetaile(paramdetail);
//            if(null!=stlSecReconDealDetaileDtos&&!StringUtil.isEmptyList(stlSecReconDealDetaileDtos)){
//                paramdetail.put("tradeIds",stlSecReconDealDetaileDtos);
//                List<StlSecRecon> tradeIds=reconInterfMapper.listStlSecRecon(paramdetail);
//                if(CollectionUtils.isNotEmpty(tradeIds)) {
//                    paramdetail.put("tradeIds",tradeIds);
//                    //怎么登录之前怎么删除
//                    deleteAll(paramdetail);
//                }
//                stlSecReconDealDetaileDtos.forEach(item ->{
//                    item.setSeqNo(IDGenerator.generate());
//                    stlSecReconDealMapper.insertSelective(item);
//                });
//            }
            //委托汇总
            Map<String,Object> paramtotal = new HashMap<>();
            paramtotal.put("execDate",param.get("execDate"));
            List<StlSecRecon> stlSecRecons = reconInterfMapper.listStlSecRecon(paramtotal);
            if(CollectionUtils.isNotEmpty(stlSecRecons)){
                stlSecRecons.removeIf(secRecon -> StringUtil.isEmpty(secRecon.getEntrustTradeId()));
            }
            paramtotal.put("entrustIds", stlSecRecons);
            List<StlSecReconDealDto> stlSecReconDealTotalDtos = reconInterfMapper.listStlSecReconDealIntegratedRepTotal(paramtotal);
            if(null!=stlSecReconDealTotalDtos&&!StringUtil.isEmptyList(stlSecReconDealTotalDtos)){
                //删除对账单及业务数据
                deleteAll(paramtotal);
                stlSecReconDealTotalDtos.forEach(item ->{
                    item.setSeqNo(IDGenerator.generate());
                    stlSecReconDealMapper.insertSelective(item);
                });
            }
        }
        //明细
        if(param.containsKey("collect")){
            param.put("tradeIds",reconInterfMapper.listStlSecRecon(param));
            List<StlSecReconDealDto> stlSecReconDealDtos = reconInterfMapper.listStlSecReconDealDetaile(param);
            if(null!=stlSecReconDealDtos&&!StringUtil.isEmptyList(stlSecReconDealDtos)){
                //删除对账单数据
                deleteAll(param);
                stlSecReconDealDtos.forEach(item ->{
                    item.setSeqNo(IDGenerator.generate());
                    stlSecReconDealMapper.insertSelective(item);
                });
            }
        }

        //委托汇总
        if(param.containsKey("collectEntrustId")){
            Map<String,Object> paramtotal = new HashMap<>();
            paramtotal.put("execDate",param.get("execDate"));
            paramtotal.put("collect",param.get("collectEntrustId"));
            // paramtotal.put("finprodIds",reconInterfMapper.listStlSecRecon(paramtotal));
            List<StlSecRecon> stlSecRecons = reconInterfMapper.listStlSecRecon(paramtotal);
            if(CollectionUtils.isNotEmpty(stlSecRecons)){
                stlSecRecons.removeIf(secRecon -> StringUtil.isEmpty(secRecon.getEntrustTradeId()));
            }
            paramtotal.put("entrustIds",stlSecRecons);
            List<StlSecReconDealDto> stlSecReconDealDtos = reconInterfMapper.listStlSecReconDealTotalByEntrustId(paramtotal);
            if(null!=stlSecReconDealDtos&&!StringUtil.isEmptyList(stlSecReconDealDtos)){
                //删除对账单及相关业务数据
                deleteAll(paramtotal);
                stlSecReconDealDtos.forEach(item ->{
                    item.setSeqNo(IDGenerator.generate());
                    stlSecReconDealMapper.insertSelective(item);
                });
            }
        }

        //证券代码汇总
        if(param.containsKey("collectByMarketId")){
            Map<String,Object> paramtotal = new HashMap<>();
            paramtotal.put("execDate",param.get("execDate"));
            paramtotal.put("collect",param.get("collectByMarketId"));
            List<StlSecRecon> stlSecRecons = reconInterfMapper.listStlSecRecon(paramtotal);
//            if(CollectionUtils.isNotEmpty(stlSecRecons)){
//                stlSecRecons.removeIf(secRecon -> StringUtil.isEmpty(secRecon.getEntrustTradeId()));
//            }
//            paramtotal.put("entrustIds", stlSecRecons);
            paramtotal.put("finprodIds", stlSecRecons);
            List<StlSecReconDealDto> stlSecReconDealDtos = reconInterfMapper.listStlSecReconDealTotalByMarketId(paramtotal);
            if(null!=stlSecReconDealDtos&&!StringUtil.isEmptyList(stlSecReconDealDtos)){
                //删除对账单及业务数据
                deleteAll(paramtotal);
                stlSecReconDealDtos.forEach(item ->{
                    item.setSeqNo(IDGenerator.generate());
                    stlSecReconDealMapper.insertSelective(item);
                });
            }
        }
    }

    /**
     * 构造查询参数
     * @param param
     */
    private void setParam(Map<String,Object> param){
        List<StlSecReconPara> stlSecReconParas = reconInterfMapper.listStlSecReconPara(param);
        if(null!=stlSecReconParas&&!StringUtil.isEmptyList(stlSecReconParas)){
            //证券汇总
            List<StlSecReconPara> collectByMarketId = stlSecReconParas.stream().filter(item -> StringUtil.equals(item.getParaValue(), "01")).collect(Collectors.toList());
            //委托汇总
            List<StlSecReconPara> collectEntrustId = stlSecReconParas.stream().filter(item -> StringUtil.equals(item.getParaValue(), "02")).collect(Collectors.toList());
            //成交明细
            List<StlSecReconPara> collect = stlSecReconParas.stream().filter(item -> StringUtil.equals(item.getParaValue(), "03")).collect(Collectors.toList());
            //处理一致的对账单
            if(!StringUtil.isEmptyList(collectByMarketId)){
                param.put("collectByMarketId",collectByMarketId);
            }
            if(!StringUtil.isEmptyList(collectEntrustId)){
                param.put("collectEntrustId",collectEntrustId);
            }
            if(!StringUtil.isEmptyList(collect)){
                param.put("collect",collect);
            }
        }
    }


    /**
     * 删除对账单、对账单结果及明细
     * @param param
     */
    private void deleteAll(Map<String,Object> param){
        int i = reconInterfMapper.deleteStlSecpReconDeal(param);
        if(i>0){
            reconInterfMapper.deleteStlSecRecon(param);
            reconInterfMapper.deleteStlSecReconDetail(param);
        }
    }

}

