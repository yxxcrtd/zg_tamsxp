/*
 * 文件名：EquityInfoRegist.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年9月19日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.service.chb3.submitmanagement.equityinforegist.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.RequestUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbBatchParaMapper;
import com.joyintech.tams.common.base.entity.ChbBatchPara;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.service.EquityInfoRegist001Service;



/**
 * 投资者身份信息登记服务
 *
 * @author zhangzhongliang
 * @version 1.0 2019年4月19日
 * @see EquityInfoRegist
 * @since 1.0
 */
@RestController
@RequestMapping("/EquityInfoRegist")
public class EquityInfoRegistController{
    /**
     * 001Service
     */
    @Autowired
    private EquityInfoRegist001Service equityInfoRegist001Service;

    @Autowired
    private ChbBatchParaMapper chbBatchParaMapper;
    /**
     *
     * 查询功能
     * <功能详细描述>
     * @return 存储了查询结果的list
     * @see
     * @since 1.0
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<EquityInfoRegist001Dto> search(@RequestBody Map<String, Object> param){
        return equityInfoRegist001Service.searchForPage(param);
    }

    /**
     *
     * 导入
     * <功能详细描述>
     * @param fileName 文件
     * @return 导入数据
     * @see
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @PostMapping("importData")
    public List<ExcelValidDto> importData(@RequestBody Map<String, Object> param){
    	List<List<String>> dataList = (List<List<String>>) param.get("dataList");
    	String deptCode =  null!=param.get("deptCode")?param.get("deptCode").toString():"";
    	String orgCode =  null!=param.get("orgCode")?param.get("orgCode").toString():"";
        return this.equityInfoRegist001Service.importData(dataList,deptCode,orgCode);
    }
    /**
     *手工处理功能
     * @return 提示信息
     * @param map 条件map
     * @seea
     * @since 1.0
     */
    @PostMapping("doUpdate")
    public void doUpdate(@RequestBody Map<String,Object> map){
    	String upKey = (String)map.get("updateKeys");//删除的主键
    	equityInfoRegist001Service.updateSumStaus(upKey);
    }

    /**
     *取消手工处理功能
     * @return 提示信息
     * @param map 条件map
     * @see
     * @since 1.0
     */
    @PostMapping("cancelMannulAll")
    public List<ValidMessage> cancelMannulAll(@RequestBody Map<String,Object> map) {
        return equityInfoRegist001Service.cancelMannulAll(map);
    	
    }
    
    /**
     *取消手工处理功能
     * @return 提示信息
     * @param map 条件map
     * @see
     * @since 1.0
     */
    @PostMapping("cancelMannul")
    public void cancelMannul(@RequestBody String upKey) {
    		equityInfoRegist001Service.cancelMannul(upKey);
    	
    }
    /**
     * checkIsNotSubmit
     * @return 提示信息
     * @param map 条件map
     * @see
     * @since 1.0
     */
    @PostMapping("checkIsNotSubmit")
    public List<ValidMessage> checkIsNotSubmit(@RequestBody String upKey) {
    	return equityInfoRegist001Service.checkIsNotSubmit(upKey);
    }

    /**
     * 报送检查
     * @param map 查询条件
     * @return 检查结果
     * @see
     * @since 1.0
     */
    @PostMapping("reportCheck")
    public List<ExcelValidDto> reportCheck(@RequestBody Map<String,Object> map) {
        String keyList;
        Map<String, Object> param = map;
        if(!param.containsKey("checkKeys")){
            keyList=equityInfoRegist001Service.getKeyList(param);
        }
        else{
            keyList=(String)param.get("checkKeys");
        }
        if (StringUtil.isEmpty(keyList)) {
            List<ExcelValidDto> checkResult = new ArrayList<ExcelValidDto>();
            checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(0), "无报送数据",null));
            return checkResult;
        }
        return equityInfoRegist001Service.checkData(keyList);
    }
    /**
     * 报送检查
     * @return 检查结果
     * @see
     * @since 1.0
     */
    @PostMapping("sendBeforeCheck")
    public List<ExcelValidDto> sendBeforeCheck(@RequestBody Map<String,Object> map) {
        String keyList;
        if(!map.containsKey("checkKeys")){
            keyList=equityInfoRegist001Service.getKeyList(map);
        }
        else{
            keyList=(String)map.get("checkKeys");
        }
        return equityInfoRegist001Service.sendBeforeCheck(keyList);
    }
    /**
     * 中债直连报送
     * @param map 传入需要报送的主键
     * @return 反馈信息
     * @see
     * @since 1.0
     */
    @PostMapping("submitChb" )
    public void doSubmit(@RequestBody Map<String,Object> map){
        Map<String,List<EquityInfoRegist001Dto>> registMap = equityInfoRegist001Service.getSubmitList(map);
        Iterator<Map.Entry<String, List<EquityInfoRegist001Dto>>> entries = registMap.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, List<EquityInfoRegist001Dto>> entry = entries.next();
            List<EquityInfoRegist001Dto> registList = entry.getValue();
            equityInfoRegist001Service.sendEquityHoldCHB(registList, false);
        }
    }
    
    /**
     * 将选择的数据导出成excel
     * 查询数据
     * @return 查询结果
     * @see
     * @since 1.0
     */
    @PostMapping("queryExportSelectRegistList")
    public List<EquityInfoRegist001Dto> queryExportSelectRegistList(@RequestBody String exportKeys){
        return equityInfoRegist001Service.queryExportSelectRegistList(exportKeys);
    }

    /**
     * 将选择的数据导出成excel
     * 查询数据
     * @return 查询结果
     * @see
     * @since 1.0
     */
    @PostMapping("queryExportRegistDelList")
    public List<EquityInfoRegist001Dto> queryExportRegistDelList(@RequestBody Map<String,Object> paramMap){
        return equityInfoRegist001Service.queryExportRegistDelList(paramMap);
    }
 
    /**
     * 中债直连报送 二次报送
     * @param paramMap 传入需要报送的主键
     * @return 反馈信息
     * @see
     * @since 1.0
     */
    @PostMapping("submitCbAgain")
    public void submitCbAgain(@RequestBody Map<String,Object> paramMap){
        Map<String,List<EquityInfoRegist001Dto>> registMap = equityInfoRegist001Service.getSubmitList(paramMap);
        Iterator<Map.Entry<String, List<EquityInfoRegist001Dto>>> entries = registMap.entrySet().iterator();
        while(entries.hasNext()){
            Map.Entry<String, List<EquityInfoRegist001Dto>> entry = entries.next();
            List<EquityInfoRegist001Dto> registList = entry.getValue();
            equityInfoRegist001Service.sendEquityHoldCHB(registList, false);
        }
    }
    
    @PostMapping("queryForEquityInfoRegistPage")
    public List<EquityInfoRegist001Dto> queryForEquityInfoRegistPage(@RequestBody Map<String,Object> param){
    	return equityInfoRegist001Service.queryForEquityInfoRegistPage(param);
    }
    @PostMapping("queryExportListByKeys")
    public List<EquityInfoRegist001Dto> queryExportListByKeys(@RequestBody List<EquityInfoRegist001Dto> equityRegistList){
    	return equityInfoRegist001Service.queryExportListByKeys(equityRegistList);
    }
    
    @PostMapping("checkIfInBatchByTaskBeanName")
    public boolean checkIfInBatchByTaskBeanName(@RequestBody String beanname) {
    	return equityInfoRegist001Service.checkIfInBatchByTaskBeanName(beanname);
    }
    
    @PostMapping("insert")
    public void insert(@RequestBody ChbBatchPara record){
    	chbBatchParaMapper.insert(record);
    }
    
}
