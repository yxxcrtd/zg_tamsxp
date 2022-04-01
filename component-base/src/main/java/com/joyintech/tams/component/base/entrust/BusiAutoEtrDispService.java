/*
 * 文件名：BusiAutoEtrDispService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：huwenbin
 * 修改时间：2018年12月14日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 

package com.joyintech.tams.component.base.entrust;

import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.EtrEntrustMapper;
import com.joyintech.tams.common.base.entity.EtrDistribute;
import com.joyintech.tams.common.base.entity.EtrEntrust;
import com.joyintech.tams.common.base.entity.EtrEntrustAdd;
import com.joyintech.tams.component.base.entrust.impl.dao.BusiAutoEtrDispMapper;
import com.joyintech.tams.component.base.entrust.impl.dto.TrdCommandDispatcher001Dto;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.systemtools.interf.IPlatformDate;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 委托自动分发 
 * 〈功能详细描述〉
 * @author huwenbin
 * @version 1.0 2018年12月14日
 * @see BusiAutoEtrDispService
 * @since 1.0
 */
@Service
public class BusiAutoEtrDispService {
    private static final String distributeRule_one = "01";//分发给所有有权限的交易员
    private static final String distributeRule_two = "02";//每笔指令按序列顺序逐笔发送给相应的交易员
    private static final String distributePara_one = "01";//同一证券上次发给谁，这次还发给谁
    private static final String distributePara_two = "02";//指令退回修改后发给原先交易员
    private static final String distributePara_three = "03";//回购拆借指令发给所有有权限交易员
    @Autowired
    private BusiAutoEtrDispMapper busiAutoEtrDispMapper;

    @Autowired(required = false)
    private IPlatformDate iPlatformDate;
    @Autowired
    private EtrEntrustMapper etrEntrustMapper;
    
    // 如果投资经理已指定交易员,则跳出  自动分发方法
    public boolean checkHaveTrader(List<EtrDistribute> etrDistributeList){
        if(etrDistributeList.size() == 0){
            return true;
        }else{
            return false;
        }
    }
    
    //查询委托指令下达日期，如果在自动分发区间内则改指令需自动分发
    public boolean checkEntrustTime(EtrEntrust etrEntrust){
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("entrustDate", etrEntrust.getEntrustDate());
        int i = busiAutoEtrDispMapper.checkEntrustTimeByAuto(param);
        if(i != 0){
            return true;
        }
        else{
            return false;
        }
    }

    /** @MethodName: 根据分发规则获取分发人员
    * @Description: TODO
    * @Param: [etrEntrust, etrEntrustAdd]
    * @Return: java.util.List<com.joyintech.tams.common.base.entity.EtrDistribute>
    * @Author: wangxuelei
    * @Date: 2020/1/3 0003
    **/
    public List<EtrDistribute> selectTrader(EtrEntrust etrEntrust,EtrEntrustAdd etrEntrustAdd){
        EtrEntrust etrEntrustById = etrEntrustMapper.selectByPrimaryKey(etrEntrust.getEntrustId());//判断是否退回的委托
        List<EtrDistribute> etrDistributes = new ArrayList<>();
        String[] split = new String[3];//分发参数
        List<TrdCommandDispatcher001Dto> trdCommandDispatcher001Dtos =selectEtrAutoRuleAndDetail();
        if (CollectionUtils.isNotEmpty(trdCommandDispatcher001Dtos)) {
            if (StringUtil.isNotBlank(trdCommandDispatcher001Dtos.get(0).getDistributeParameter())) {
                split = trdCommandDispatcher001Dtos.get(0).getDistributeParameter().split(",");
            }
            //分发规则为分发给所有有权限的交易员
            if (StringUtil.equals(trdCommandDispatcher001Dtos.get(0).getDistributeRule(), distributeRule_one)) {
                //分发参数存在
                if (split.length > 0 &&StringUtil.isNotEmpty(trdCommandDispatcher001Dtos.get(0).getDistributeParameter())) {
                    for (String str : split) {
                        if (StringUtil.equals(str, distributePara_one)&&etrEntrustById==null) {//分发参数同一证券上次发给谁，这次还发给谁，非退回
                            if(StringUtil.isNotBlank(etrEntrust.getFinprodId())){//非回购拆借指令
                                etrDistributes = addByRuleOne(etrEntrust,etrEntrustAdd,false);
                            }
                        } else if (StringUtil.equals(str, distributePara_two)&&etrEntrustById!=null) {//分发参数指令退回修改后发给原先交易员
                            if(StringUtil.isNotBlank(etrEntrust.getFinprodId())) {//非回购拆借指令
                                etrDistributes = addByRuleOne(etrEntrust,etrEntrustAdd,true);
                            }
                        } else if (StringUtil.equals(str, distributePara_three)) {//分发参数回购拆借指令发给所有有权限交易员
//                            if(StringUtil.isBlank(etrEntrust.getFinprodId())){
                                etrDistributes =  getEtrDistributes(etrEntrust,etrEntrustAdd,distributeRule_one);
//                            }
                        }
                    }
                }else{//分发参数不存在
                    etrDistributes =   getEtrDistributes(etrEntrust,etrEntrustAdd,distributeRule_one);
                }
                //分发规则为每笔指令按序列顺序逐笔发送给相应的交易员
            }else if(StringUtil.equals(trdCommandDispatcher001Dtos.get(0).getDistributeRule(), distributeRule_two)){
                if (split.length > 0 &&StringUtil.isNotEmpty(trdCommandDispatcher001Dtos.get(0).getDistributeParameter())) {
                    for (String str : split) {
                        if (StringUtil.equals(str, distributePara_one)&&etrEntrustById==null) {//分发参数同一证券上次发给谁，这次还发给谁
                            if(StringUtil.isNotBlank(etrEntrust.getFinprodId())){//非回购拆借指令
                                etrDistributes = addByRuleTwo(etrEntrust,etrEntrustAdd,false);
                            }
                        } else if (StringUtil.equals(str, distributePara_two)&&etrEntrustById!=null) {//分发参数指令退回修改后发给原先交易员
                            if(StringUtil.isNotBlank(etrEntrust.getFinprodId())) {//非回购拆借指令
                                etrDistributes = addByRuleTwo(etrEntrust,etrEntrustAdd,true);
                            }
                        } else if (StringUtil.equals(str, distributePara_three)) {//分发参数回购拆借指令发给所有有权限交易员
                            if(StringUtil.isBlank(etrEntrust.getFinprodId())){
                                etrDistributes =  getEtrDistributes(etrEntrust,etrEntrustAdd,distributeRule_two);
                            }
                        }
                    }
                }else{//分发参数不存在
                    etrDistributes =   getEtrDistributes(etrEntrust,etrEntrustAdd,distributeRule_two);
                }
            }
        }
        return etrDistributes;
    }
    /** @MethodName: 设置分发人员,分发给所有有权限的交易员
    * @Description: TODO
    * @Param: []
    * @Return: java.util.List<com.joyintech.tams.common.base.entity.EtrDistribute>
    * @Author: wangxuelei
    * @Date: 2020/1/3 0003
    **/
     private List<EtrDistribute> addByRuleOne(EtrEntrust etrEntrust, EtrEntrustAdd etrEntrustAdd,Boolean back){
         List<EtrDistribute> etrDistributes = new ArrayList<>();
         //back为false获取列表上一次发给人和指令退回修改后发给原先交易员,back为true是退回
         List<TrdCommandDispatcher001Dto> trdCommandDispatcher001Dtos = selectAlreadyDistribute(etrEntrust, etrEntrustAdd,back);
         if(CollectionUtils.isNotEmpty(trdCommandDispatcher001Dtos)){
             String trader = trdCommandDispatcher001Dtos.get(0).getTrader();//获取所有的用户
             if(StringUtil.isNotBlank(trader)){
                 String[] split = trader.split(",");
                 for (String str:split) {
                     EtrDistribute etrDistribute = new EtrDistribute();
                     etrDistribute.setRecStatus(DictCodeList.委托状态_待接收.getValue());
                     etrDistribute.setTrader(str);
                     etrDistribute.setEntrustId(etrEntrust.getEntrustId());
                     etrDistributes.add(etrDistribute);
                 }
             }
         }
         return etrDistributes;
     }

     /** @MethodName: 设置分发人员,每笔指令按序列顺序逐笔发送给相应的交易员
     * @Description: TODO
     * @Param: [etrEntrust, etrEntrustAdd, back]
     * @Return: java.util.List<com.joyintech.tams.common.base.entity.EtrDistribute>
     * @Author: wangxuelei
     * @Date: 2020/1/3 0003
     **/
    private List<EtrDistribute> addByRuleTwo(EtrEntrust etrEntrust, EtrEntrustAdd etrEntrustAdd,Boolean back){
        List<EtrDistribute> etrDistributes = new ArrayList<>();
        //back为false获取列表上一次发给人和指令退回修改后发给原先交易员,back为true是退回
        List<TrdCommandDispatcher001Dto> trdCommandDispatcherDtos = selectTradeSeqbByEntrust(etrEntrust, etrEntrustAdd,back);
        if(!StringUtil.isEmptyList(trdCommandDispatcherDtos) &&trdCommandDispatcherDtos.size()>0 ){
            String traders = trdCommandDispatcherDtos.get(0).getTrader();//获取当前指令对应的交易员信息
            // 判断当前交易有没有分配相应的权限，如果有则分发指令没有不发
            Map<String,Object> param = new HashMap<>();
            param.put("trader",trdCommandDispatcherDtos.get(0).getTrader().contains(",")==true?trdCommandDispatcherDtos.get(0).getTrader().split(",")[0]:trdCommandDispatcherDtos.get(0).getTrader());

            List<String> traderList=new ArrayList<>();
            List<TrdCommandDispatcher001Dto> authTypelist = busiAutoEtrDispMapper.getAuthType(param);
            if(!StringUtil.isEmptyList(authTypelist)&&authTypelist.size()>0)
            {
                authTypelist.forEach(item->{
                    traderList.add(item.getTrader());
                });
            }
            if(StringUtil.isNotBlank(traders) &&traderList.contains(etrEntrust.getFinprodType2())){
                String[] split = traders.split(",");
                for (String str:split) {
                    EtrDistribute etrDistribute = new EtrDistribute();
                    etrDistribute.setRecStatus(DictCodeList.委托状态_待接收.getValue());
                    etrDistribute.setTrader(str);
                    etrDistribute.setEntrustId(etrEntrust.getEntrustId());
                    etrDistributes.add(etrDistribute);
                }
            }
        }
        return etrDistributes;
    }
    //根据委托指令、授权检索有权限交易员
    private List<EtrDistribute> getEtrDistributes(EtrEntrust etrEntrust, EtrEntrustAdd etrEntrustAdd,String type) {
        List<EtrDistribute> etrDistributeList = new ArrayList<EtrDistribute>();
        Map<String,Object> param = new HashMap<String,Object>();
        //资产类型
        param.put("assetType", etrEntrustAdd.getAssetType());
        List<String> traderList = busiAutoEtrDispMapper.selectTrader(param);
        for(String trader:traderList){
            EtrDistribute etrDistribute = new EtrDistribute();
            //设置接收状态
            etrDistribute.setRecStatus(DictCodeList.委托状态_待接收.getValue());
            //设置指定交易员指定交易员 = 指定交易员
            etrDistribute.setTrader(trader);
            //设置委托代码
            etrDistribute.setEntrustId(etrEntrust.getEntrustId());
            //
            etrDistributeList.add(etrDistribute);
        }
        return etrDistributeList;
    }
    /** @MethodName: 获取配置的分发规则
    * @Description: TODO
    * @Param: []
    * @Return: java.util.List<com.joyintech.tams.component.base.entrust.impl.dto.TrdCommandDispatcher001Dto>
    * @Author: wangxuelei
    * @Date: 2020/1/3 0003
    **/
    public  List<TrdCommandDispatcher001Dto> selectEtrAutoRuleAndDetail(){
        List<TrdCommandDispatcher001Dto> trdCommandDispatcher001Dtos = busiAutoEtrDispMapper.selectEtrAutoRuleAndDetail(DateUtil.format(iPlatformDate.getSysDate()));
        return  trdCommandDispatcher001Dtos;
    }
    /** @MethodName: 已分发查询
    * @Description: TODO
    * @Param: [etrEntrust, etrEntrustAdd]
    * @Return: java.util.List<com.joyintech.tams.component.base.entrust.impl.dto.TrdCommandDispatcher001Dto>
    * @Author: wangxuelei
    * @Date: 2020/1/3 0003
    **/
    public  List<TrdCommandDispatcher001Dto> selectAlreadyDistribute(EtrEntrust etrEntrust, EtrEntrustAdd etrEntrustAdd,Boolean back){
        Map<String,Object> param = new HashMap<>();
        param.put("finprodId",etrEntrust.getFinprodId());

        if(back){
            param.put("entrustDate",DateUtil.format(iPlatformDate.getSysDate()));
            param.put("entrustId",etrEntrust.getEntrustId());
            param.put("entrustStatus",DictCodeList.委托状态_已退回.getValue());
        }else{
            param.put("entrustStatusBack",DictCodeList.委托状态_已退回.getValue());
        }
        List<TrdCommandDispatcher001Dto> trdCommandDispatcher001Dtos = busiAutoEtrDispMapper.selectAlreadyDistribute(param);
        return trdCommandDispatcher001Dtos;
    }
    /** @MethodName: 已分发查询关联交易员序列
    * @Description: TODO
    * @Param: [etrEntrust, etrEntrustAdd, back]
    * @Return: java.util.List<com.joyintech.tams.component.base.entrust.impl.dto.TrdCommandDispatcher001Dto>
    * @Author: wangxuelei
    * @Date: 2020/1/3 0003
    **/
    public  List<TrdCommandDispatcher001Dto> selectTradeSeqbByEntrust(EtrEntrust etrEntrust, EtrEntrustAdd etrEntrustAdd,Boolean back){
        Map<String,Object> param = new HashMap<>();
        param.put("finprodId",etrEntrust.getFinprodId());

        if(back){
            param.put("entrustDate",DateUtil.format(iPlatformDate.getSysDate()));
            param.put("entrustId",etrEntrust.getEntrustId());
            param.put("entrustStatus",DictCodeList.委托状态_已退回.getValue());
        }else{
            param.put("entrustStatusBack",DictCodeList.委托状态_已退回.getValue());
        }
        List<TrdCommandDispatcher001Dto> trdCommandDispatcher001Dtos = busiAutoEtrDispMapper.selectTradeSeqbByEntrust(param);
        return trdCommandDispatcher001Dtos;
    }


}
