/*
 * 文件名：DataCheck.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Administrator
 * 修改时间：2019年3月29日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.common.svreport.datacheck.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.tams.common.svreport.datacheck.dao.CheckConditionMapper;
import com.joyintech.tams.common.svreport.datacheck.dao.CheckRuleMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckCondition;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckRule;



/**
 * 通过配置规则，进行对象检查
 *
 * @author chenxc
 * @version 1.0 2019年4月24日
 * @see DataCheckService
 * @since 1.0
 */
@Service
public class DataCheckService {
    private static Logger logger = LoggerFactory.getLogger(DataCheckService.class);
    
    @Autowired
    private CheckRuleMapper checkRuleMapper;
    
    @Autowired
    private CheckConditionMapper checkConditionMapper;
    // add by dubaojun 2020-10-23 增加支持校验出错数据不移除的逻辑 start
    /**
     * 根据指定的检查规则，检查数据列表，对于某条数据有一个规则不满足，继续检查其它规则，即每条数据都进行全部规则检查
     * @param listData 被检查的数据列表，错误的数据被移出
     * @param listRules 检查规则
     * @param removeFlag 校验出错的数据是否移除
     * @return 返回错误的数据
     * @see
     * @since 1.0
     */
    public static List<Map<String, String>> checkDataList(List<Map<String, String>> listData,List<CheckRule> listRules) {
        return checkDataList(listData, listRules, true);
    }
    // add by dubaojun 2020-10-23 增加支持校验出错数据不移除的逻辑 end

    /**
     * 根据指定的检查规则，检查数据列表，对于某条数据有一个规则不满足，继续检查其它规则，即每条数据都进行全部规则检查
     * @param listData 被检查的数据列表，错误的数据根据参数removeFlag确定是否移除
     * @param listRules 检查规则
     * @param removeFlag 校验出错的数据是否移除
     * @return 返回错误的数据
     * @see
     * @since 1.0
     */
    public static List<Map<String, String>> checkDataList(List<Map<String, String>> listData,List<CheckRule> listRules, boolean removeFlag) {

        List<Map<String, String>> listError = new ArrayList<Map<String, String>>();

        //定义检查类的方法集合：Map<检查类,Map<方法名称,方法>>
        Map<String,Map<String,Method>> mapClassMethod = new HashMap<String,Map<String,Method>>();

        Iterator<Map<String, String>> it = listData.iterator();

        int i = 1;
        while (it.hasNext()) {         //循环检查指定的数据列表
            boolean legal=true;  //检查结果
            StringBuilder sb = new StringBuilder();  //记录错误信息
            Map<String, String> x = it.next();

            for ( CheckRule r : listRules ) {  //循环每个规则
                Map<String,Method> mapMethod = mapClassMethod.get(r.getCheckclass());
                if ( mapMethod==null ) {
                    mapMethod = new HashMap<String,Method>();
                    try {
                        Method[] rm = Class.forName(r.getCheckclass()).getDeclaredMethods();
                        for (Method m1 : rm) {
                            mapMethod.put(m1.getName(), m1);
                        }
                    }
                    catch (SecurityException e) {
                        logger.error(e.getMessage(),e);
                    }
                    catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(),e);
                    }
                    mapClassMethod.put(r.getCheckclass(),mapMethod);
                }

                // 检查逻辑，如果有前置条件，先检查条件是否满足，如果条件满足，执行数据检查，否则不执行数据检查
                // 如果没有前置条件，检查数据
                // 如果有多个前置条件，循环每个条件检查，各条件之间是与的关系
                List<CheckCondition> listConditions = r.getListConditions();
                if (listConditions != null) { // 有前置条件，则先执行前置条件是否满足
                    boolean conCheck = true;
                    StringBuilder sbCon = new StringBuilder();
                    sbCon.append("条件:");
                    for (CheckCondition c : listConditions) {
                        conCheck = conCheck && checkCondition(x, c, mapMethod);
                        if (StringUtil.isNotEmpty(c.getRemark()) && !StringUtil.equals(c.getRemark(), "null")){
                            sbCon.append(c.getRemark()+";");
                        }
                        if (conCheck == false) { // 条件不满足，不做数据检查
                            break;
                        }
                    }
                    if (conCheck == true) { // 条件满足，做数据检查
                        if (!StringUtil.equals(sbCon.toString(), "条件:")){
                            sbCon.append("-->");
                        }
                        boolean b= checkData(x, r, mapMethod);
                        if (b == false) {
                            sb.append(sbCon.toString()+r.getRemark() + "[" + r.getSrcproperty() + "="+x.get(r.getSrcproperty())+"];");
                        }
                        legal &=b;
                    }
                } else { // 无前置条件，做数据检查
                    boolean b=checkData(x, r, mapMethod);
                    if (b == false) {
                        sb.append(r.getRemark() + "[" + r.getSrcproperty() + "="+x.get(r.getSrcproperty())+"];");
                    }
                    legal &=b;
                }
            }  //循环规则－结束
            if ( legal==false ) {
                x.put("ERRMSG", sb.toString());
                x.put("ERRMSG_ROW",String.valueOf(i));
                listError.add(x);
                if(removeFlag) {
                    it.remove();
                }
            }
            i++;
        } //循环数据结束

        return listError;
    }
    /**
     * 根据指定的检查规则，检查数据列表，对于某条数据有一个规则不满足，继续检查其它规则，即每条数据都进行全部规则检查
     * @param listData 被检查的数据列表，错误的数据根据参数removeFlag确定是否移除
     * @param listRules 检查规则
     * @param removeFlag 校验出错的数据是否移除
     * @return 返回错误的数据
     * @see
     * @since 1.0
     */
    public static List<Map<String, String>> checkDataList(List<ExcelValidDto> list,List<Map<String, String>> listData, List<CheckRule> listRules, boolean removeFlag,String dataType) {

        List<Map<String, String>> listError = new ArrayList<Map<String, String>>();

        //定义检查类的方法集合：Map<检查类,Map<方法名称,方法>>
        Map<String,Map<String,Method>> mapClassMethod = new HashMap<String,Map<String,Method>>();

        Iterator<Map<String, String>> it = listData.iterator();

        int i = 1;
        while (it.hasNext()) {         //循环检查指定的数据列表
            boolean legal=true;  //检查结果
            StringBuilder sb = new StringBuilder();  //记录错误信息
            Map<String, String> x = it.next();

            for ( CheckRule r : listRules ) {  //循环每个规则
                Map<String,Method> mapMethod = mapClassMethod.get(r.getCheckclass());
                if ( mapMethod==null ) {
                    mapMethod = new HashMap<String,Method>();
                    try {
                        Method[] rm = Class.forName(r.getCheckclass()).getDeclaredMethods();
                        for (Method m1 : rm) {
                            mapMethod.put(m1.getName(), m1);
                        }
                    }
                    catch (SecurityException e) {
                        logger.error(e.getMessage(),e);
                    }
                    catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(),e);
                    }
                    mapClassMethod.put(r.getCheckclass(),mapMethod);
                }

                // 检查逻辑，如果有前置条件，先检查条件是否满足，如果条件满足，执行数据检查，否则不执行数据检查
                // 如果没有前置条件，检查数据
                // 如果有多个前置条件，循环每个条件检查，各条件之间是与的关系
                List<CheckCondition> listConditions = r.getListConditions();
                if (listConditions != null) { // 有前置条件，则先执行前置条件是否满足
                    boolean conCheck = true;
                    StringBuilder sbCon = new StringBuilder();
                    sbCon.append("条件:");
                    for (CheckCondition c : listConditions) {
                        conCheck = conCheck && checkCondition(x, c, mapMethod);
                        if (StringUtil.isNotEmpty(c.getRemark()) && !StringUtil.equals(c.getRemark(), "null")){
                            sbCon.append(c.getRemark()+";");
                        }
                        if (conCheck == false) { // 条件不满足，不做数据检查
                            break;
                        }
                    }
                    if (conCheck == true) { // 条件满足，做数据检查
                        if (!StringUtil.equals(sbCon.toString(), "条件:")){
                            sbCon.append("-->");
                        }
                        boolean b= checkData(x, r, mapMethod);
                        if (b == false) {
                            sb.append(sbCon.toString()+r.getRemark() + "[" + r.getSrcproperty() + "="+x.get(r.getSrcproperty())+"];");
                        }
                        legal &=b;
                    }
                } else { // 无前置条件，做数据检查
                    boolean b=checkData(x, r, mapMethod);
                    if (b == false) {
                        sb.append(r.getRemark() + "[" + r.getSrcproperty() + "="+x.get(r.getSrcproperty())+"];");
                    }
                    legal &=b;
                }
            }  //循环规则－结束
            if ( legal==false ) {

                String errmsg = x.get("ERRMSG");
                if(StringUtils.isEmpty(errmsg)){
                    x.put("ERRMSG", sb.toString());
                    if(list!=null){
                        if(StringUtil.equals(dataType,"CHB_ASSET_INFO")){
                                list.add(new ExcelValidDto(" 导入成功",String.valueOf(i+2),sb.toString()));
                        }else  {
                                list.add(new ExcelValidDto(" 导入成功", String.valueOf(i + 1), sb.toString()));
                        }
                    }
                }else if(errmsg.equals("产品不存在对应的申报信息！")){
                    errmsg +=sb.toString();
                    x.put("ERRMSG", errmsg);
                    if(list !=null){
                        if(list.size()>0){
                            for (ExcelValidDto excelValidDto : list) {
                                if(excelValidDto.getRowNum().equals(String.valueOf(i+1))){
                                    excelValidDto.setErrorInfo(errmsg);
                                    break;
                                }
                            }
                        }
                    }

                }
                x.put("ERRMSG_ROW",String.valueOf(i));
                listError.add(x);
                if(removeFlag) {
                    it.remove();
                }
            }
            i++;
        } //循环数据结束

        return listError;
    }

    
    /**
     * 根据指定的检查规则，检查数据列表，对于某条数据有一个规则不满足，则本条数据不再检查，开始检查下一条
     * @param listData 被检查的数据列表，错误的数据被移出
     * @param listRules 检查规则
     * @return 返回错误的数据
     * @see
     * @since 1.0
     */
    public static List<Map<String, String>> checkDataList2(List<Map<String, String>> listData,List<CheckRule> listRules) {

        List<Map<String, String>> listError = new ArrayList<Map<String, String>>();

        Map<String,Map<String,Method>> mapClassMethod = new HashMap<String,Map<String,Method>>();
        
        for (CheckRule r : listRules) {
            Map<String,Method> mapMethod = mapClassMethod.get(r.getCheckclass());
            if ( mapMethod==null ) {
                mapMethod = new HashMap<String,Method>();
                try {
                    Method[] rm = Class.forName(r.getCheckclass()).getDeclaredMethods();
                    for (Method m1 : rm) {
                        mapMethod.put(m1.getName(), m1);
                    }
                }
                catch (SecurityException e) {
                    logger.error(e.getMessage(),e);
                }
                catch (ClassNotFoundException e) {
                    logger.error(e.getMessage(),e);
                }
                mapClassMethod.put(r.getCheckclass(),mapMethod);
            }
            
            Iterator<Map<String, String>> it = listData.iterator();
            while (it.hasNext()) {
                Map<String, String> x = it.next();

                // 检查逻辑，如果有前置条件，先检查条件是否满足，如果条件满足，执行数据检查，否则不执行数据检查
                // 如果没有前置条件，检查数据
                // 如果有多个前置条件，循环每个条件检查，各条件之间是与的关系
                List<CheckCondition> listConditions = r.getListConditions();
                if (listConditions != null) { // 有前置条件，则先执行前置条件是否满足
                    boolean conCheck = true;
                    for (CheckCondition c : listConditions) {
                        conCheck = conCheck && checkCondition(x, c, mapMethod);
                        if (conCheck == false) { // 条件不满足，不做数据检查
                            break;
                        }
                    }
                    if (conCheck == true) { // 条件满足，做数据检查
                        boolean legal = checkData(x, r, mapMethod);
                        if (legal == false) {
                            x.put("ERRMSG", r.getRemark() + "[" + r.getSrcproperty() + "]");
                            listError.add(x);
                            it.remove();
//                            break;
                        }
                    }
                } else { // 无前置条件，做数据检查
                    boolean legal = checkData(x, r, mapMethod);
                    if (legal == false) {
                        x.put("ERRMSG", r.getRemark() + "[" + r.getSrcproperty() + "]");
                        listError.add(x);
                        it.remove();
                    }
                }
            }
        }
        return listError;
    }
    /**
     * 根据指定的检查规则，检查数据列表，对于某条数据有一个规则不满足，继续检查其它规则，即每条数据都进行全部规则检查
     * @param listData 被检查的数据列表，错误的数据不被移出
     * @param listRules 检查规则
     * @return 返回错误的数据
     * @see
     * @since 1.0
     */
    public static List<Map<String, String>> checkDataList3(List<Map<String, String>> listData,List<CheckRule> listRules) {

        List<Map<String, String>> listError = new ArrayList<Map<String, String>>();

        //定义检查类的方法集合：Map<检查类,Map<方法名称,方法>>
        Map<String,Map<String,Method>> mapClassMethod = new HashMap<String,Map<String,Method>>();
            
        Iterator<Map<String, String>> it = listData.iterator();

        while (it.hasNext()) {         //循环检查指定的数据列表
            boolean legal=true;  //检查结果
            StringBuilder sb = new StringBuilder();  //记录错误信息
            Map<String, String> x = it.next();
            
            for ( CheckRule r : listRules ) {  //循环每个规则
                Map<String,Method> mapMethod = mapClassMethod.get(r.getCheckclass());
                if ( mapMethod==null ) {
                    mapMethod = new HashMap<String,Method>();
                    try {
                        Method[] rm = Class.forName(r.getCheckclass()).getDeclaredMethods();
                        for (Method m1 : rm) {
                            mapMethod.put(m1.getName(), m1);
                        }
                    }
                    catch (SecurityException e) {
                        logger.error(e.getMessage(),e);
                    }
                    catch (ClassNotFoundException e) {
                        logger.error(e.getMessage(),e);
                    }
                    mapClassMethod.put(r.getCheckclass(),mapMethod);
                }
                
                // 检查逻辑，如果有前置条件，先检查条件是否满足，如果条件满足，执行数据检查，否则不执行数据检查
                // 如果没有前置条件，检查数据
                // 如果有多个前置条件，循环每个条件检查，各条件之间是与的关系
                List<CheckCondition> listConditions = r.getListConditions();
                if (listConditions != null) { // 有前置条件，则先执行前置条件是否满足
                    boolean conCheck = true;
                    StringBuilder sbCon = new StringBuilder();
                    sbCon.append("条件:");
                    for (CheckCondition c : listConditions) {
                        conCheck = conCheck && checkCondition(x, c, mapMethod);
                        sbCon.append(c.getRemark()+";");
                        if (conCheck == false) { // 条件不满足，不做数据检查
                            break;
                        }
                    }
                    if (conCheck == true) { // 条件满足，做数据检查
                        sbCon.append("-->");
                        boolean b= checkData(x, r, mapMethod);
                        if (b == false) {
                            sb.append(sbCon.toString()+r.getRemark() + "[" + r.getSrcproperty() + "="+x.get(r.getSrcproperty())+"];");
                        }
                        legal &=b;
                    }
                } else { // 无前置条件，做数据检查
                    boolean b=checkData(x, r, mapMethod);
                    if (b == false) {
                        sb.append(r.getRemark() + "[" + r.getSrcproperty() + "="+x.get(r.getSrcproperty())+"];");
                    }
                    legal &=b;
                }
            }  //循环规则－结束
            if ( legal==false ) {
                x.put("ERRMSG", sb.toString());
                listError.add(x);
            }
        } //循环数据结束

        return listError;
    }
    /**
     * 条件检查，判断是否符合条件
     * @param data 被检查的数据
     * @param condition 条件实体
     * @param mapMethod 检查库实现的方法
     * @return
     * @see
     * @since 1.0
     */
    public static boolean checkCondition (final Map<String,String> data , CheckCondition condition,Map<String,Method> mapMethod ) {
        boolean ret=false;
        Method method = mapMethod.get(condition.getCheckmethod());
        try {
            ret = (boolean)method.invoke(null,data, condition);
        }
        catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        }
        catch (IllegalArgumentException e) {
            logger.error(e.getMessage(),e);
        }
        catch (InvocationTargetException e) {
            logger.error(e.getMessage(),e);
        }
        return ret;

    }
    
    /**
     * 检查单个实体数据
     * @param data 被检查的数据
     * @param rule 检查规则
     * @param mapMethod 检查库实现的检查方法
     * @return
     * @see
     * @since 1.0
     */
    public static boolean checkData (final Map<String,String> data , CheckRule rule,Map<String,Method> mapMethod ) {
        boolean ret=false;
        Method method = mapMethod.get(rule.getCheckmethod());
        try {
            ret = (boolean)method.invoke(null,data, rule);
        }
        catch (IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        }
        catch (IllegalArgumentException e) {
            logger.error(e.getMessage(),e);
        }
        catch (InvocationTargetException e) {
            logger.error(e.getMessage(),e);
        }
        return ret;

    }
        
    /**
     * 
     * 检查数据是否重复，有则删除
     * @param listData 待检查的原始数据列表，处理后，删除了重复的数据
     * @param keys，需要比较的属性集合，如果不传入，则比较所有的属性
     * @return 返回重复的数据列表
     * @see
     * @since 1.0
     */
    public static List<Map<String,String>> checkDuplicatedData(List<Map<String,String>> listData,String... keys) {
        
        List<Map<String,String>> listDup = new ArrayList<Map<String,String>>();
        
        String key = "joyin";
        // 重复的键
//        HashSet<String> setDup = new HashSet<String>();
        HashSet<String> set = new HashSet<String>();
        // modify by dubaojun 2020-10-15 将Iterator修改为ListIterator，支持取出index start
        ListIterator<Map<String,String>> it = listData.listIterator();
        // modify by dubaojun 2020-10-15 将Iterator修改为ListIterator，支持取出index end
        while(it.hasNext()){
            // modify by dubaojun 2020-10-30 当数据重复时，增加重复数据的字段值 start
            StringBuilder msgBuilder = new StringBuilder("[");
            Map<String,String> x = it.next();
            String keyString="";
            if ( keys.length>0 ) {
                StringBuilder sb = new StringBuilder();
                for (String s : keys) {
                    if (!StringUtil.isEmpty(s)){
                        sb.append(x.get(s)+"|");
                        msgBuilder.append(s).append("=").append(x.get(s)).append(";");
                    }
                }
                keyString = sb.toString();
            }else {
                    JSONObject json=new JSONObject(x);
                    keyString = json.toString();
                    msgBuilder.append("所有字段重复");
             }
            msgBuilder.append("]");
            // modify by dubaojun 2020-10-30 当数据重复时，增加重复数据的字段值 end
            String encodeStr=DigestUtils.md5Hex( keyString + key);
            if ( set.contains(encodeStr)) {
                // 重复项出现多次只展示一条最新出现的重复项
//                if (!setDup.contains(encodeStr)){
                    // modify by dubaojun 2020-10-30 当数据重复时，增加重复数据的字段值 start
                    x.put("ERRMSG","数据重复！" + msgBuilder.toString());
                    // modify by dubaojun 2020-10-30 当数据重复时，增加重复数据的字段值 end
                    // add by dubaojun 2020-10-15 将Iterator修改为ListIterator，支持取出index start
                    // x.put("ERRMSG_ROW","" + it.previousIndex());
                    // add by dubaojun 2020-10-15 将Iterator修改为ListIterator，支持取出index end
                    listDup.add(x);
//                    setDup.add(encodeStr);
//                }
                it.remove();
            } else {
                set.add(encodeStr);
            }
        }
        return listDup;
        
    }


    public static List<Map<String, String>> checkDuplicatedData(List<com.joyintech.tams.systemtools.dto.ExcelValidDto> excelValidDtoList, List<Map<String,String>> list, String... duplicateKey) {
        List<Map<String, String>> result =  new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i+1; j < list.size(); j++) {
                Map var1 = list.get(i);
                Map var2  =list.get(j);
                boolean flag = true;
                for (String s : duplicateKey) {
                    if(!var1.get(s).equals(var2.get(s))){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    if(StringUtils.isEmpty((String)var1.get("ERRMSG"))){
                        var1.put("ERRMSG","重复数据;");
                    }
                    result.add(var1);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 根据数据类型，读取检查规则
     * @param datatype 数据类型
     * @return 检查规则列表
     * @see
     * @since 1.0
     */
    public List<CheckRule> queryCheckRules(String datatype) {
        
        String baseCheckLib = "com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib";
        Map<String,Method> mapMethod = new HashMap<String,Method>();
        try {
            Method[] rm = Class.forName(baseCheckLib).getDeclaredMethods();
            for (Method m1 : rm) {
                mapMethod.put(m1.getName(), m1);
            }
        }
        catch (SecurityException e) {
            logger.error(e.getMessage(),e);
        }
        catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        }
        
        Map<String,List<CheckCondition>> mapConditions = new HashMap<String,List<CheckCondition>>();
        HashSet<String> setCompareto = new HashSet<String>();
        setCompareto.add(">");
        setCompareto.add("<");
        setCompareto.add(">=");
        setCompareto.add("<=");
        setCompareto.add("==");
        setCompareto.add("!=");
        
        List<CheckCondition> listConditions = checkConditionMapper.selectByRuleid(null);
        for ( CheckCondition r:listConditions ) {
            if ( r.getCheckmethod()==null ) {
                logger.error("检查规则错误：检查条件中方法名称不能为空!["+r.getCheckconditionid()+"]");
                continue;
            }
            if ( !mapMethod.containsKey(r.getCheckmethod()) ) { //不合法的方法
                logger.error("检查规则错误：检查条件中无效的方法名称!["+r.getCheckconditionid()+"]");
                continue;
            }
            if ( r.getSrcproperty()==null ||r.getSrcproperty().length()<1) {
                logger.error("检查规则错误：检查条件中被检查的属性不能为空!["+r.getCheckconditionid()+"]");
                continue;                
            }

            if ( !"isNotEmpty".equals(r.getCheckmethod()) && !"isNumber".equals(r.getCheckmethod())  ) {
                if ( r.getObjvalue()==null ||r.getObjvalue().length()<1 ) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标值不能为空!["+r.getCheckconditionid()+"]");
                    continue; 
                } else if ( "lessThanLength".equals(r.getCheckmethod()) ) {
                    try {
                        Integer.parseInt(r.getObjvalue());
                    } catch(Exception e ) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式错误!["+r.getCheckruleid()+"]");
                    }
                }
            }

            if ( "compareValue".equals(r.getCheckmethod()) || "compareProperty".equals(r.getCheckmethod()) 
                ||"compareValueAsNumber".equals(r.getCheckmethod()) || "comparePropertyAsNumber".equals(r.getCheckmethod())
                || "compareValueSysPara".equals(r.getCheckmethod())
                ) {
                if ( r.getCompareto()==null ||r.getCompareto().length()<1 ) {
                    logger.error("检查规则错误：检查条件中数据比较方法逻辑运算符号不能为空!["+r.getCheckconditionid()+"]");
                    continue;                     
                }
                if ( !setCompareto.contains(r.getCompareto()) ) {
                    logger.error("检查规则错误：检查条件中数据比较方法逻辑运算符号不合法!["+r.getCheckconditionid()+"]");
                    continue;                     
                }
                if ( "compareValueAsNumber".equals(r.getCheckmethod())) {
                    if ( !BaseCheckLib.isNumeric(r.getObjvalue()) )  {
                        logger.error("检查规则错误：检查条件中数据目标值非数字!["+r.getCheckconditionid()+"]");
                        continue;                                             
                    }
                }
            }
            
            if ( "isNumber".equals(r.getCheckmethod()) ) {
                if ( r.getObjvalue()!=null && r.getObjvalue().length()>0 ) { //目标值不空，则必须符合"数字1,数字2"格式，且数字1>0数字2
                    String [] objFormat = r.getObjvalue().split(",");
                    try {
                        int allbit = Integer.parseInt(objFormat[0]);
                        int decbit = Integer.parseInt(objFormat[1]);
                        if ( allbit<=decbit ) {
                            logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式数字1>数字2!["+r.getCheckconditionid()+"]");
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式错误!["+r.getCheckconditionid()+"]");
                        continue;
                    }
                }
            }

            if ( "isCustomizedString".equals(r.getCheckmethod()) ) {
                String format = r.getObjvalue();
                try {
                    Pattern.compile(Pattern.quote(format));
                } catch (Exception e) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式错误!["+r.getCheckconditionid()+"]");
                    continue;
                }
            }

            List<CheckCondition> listRuleCon = mapConditions.get(r.getCheckruleid());
            if ( listRuleCon == null ) {
                listRuleCon = new ArrayList<CheckCondition>();
                listRuleCon.add(r);
                mapConditions.put(r.getCheckruleid(),listRuleCon);
            } else {
                listRuleCon.add(r);
            }
            
        }
        
        List<CheckRule> listResult = new ArrayList<CheckRule>();

        
        List<CheckRule> listRules = checkRuleMapper.selectByDatatype(datatype);
        for ( CheckRule r:listRules ) {
            if ( r.getCheckclass()==null ) {
                logger.error("检查规则错误：检查类不能为空!["+r.getCheckruleid()+"]");
                continue;
            }
            if ( !baseCheckLib.equals(r.getCheckclass()) ) {  //非基本库的方法不做检查
                listResult.add(r);
                continue;
            }
            if ( r.getCheckmethod()==null ) {
                logger.error("检查规则错误：方法名称不能为空!["+r.getCheckruleid()+"]");
                continue;
            }
            if ( !mapMethod.containsKey(r.getCheckmethod()) ) { //不合法的方法
                logger.error("检查规则错误：无效的方法名称!["+r.getCheckruleid()+"]");
                continue;
            }
            if ( r.getSrcproperty()==null ||r.getSrcproperty().length()<1) {
                logger.error("检查规则错误：被检查的属性不能为空!["+r.getCheckruleid()+"]");
                continue;                
            }
            
            if ( !"isNotEmpty".equals(r.getCheckmethod()) && !"isNumber".equals(r.getCheckmethod())  ) {
                if ( r.getObjvalue()==null ||r.getObjvalue().length()<1 ) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"方法的目标值不能为空!["+r.getCheckruleid()+"]");
                    continue; 
                } else if ( "lessThanLength".equals(r.getCheckmethod()) ) {
                    try {
                        Integer.parseInt(r.getObjvalue());
                    } catch(Exception e ) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"方法的目标格式错误!["+r.getCheckruleid()+"]");
                    }
                }
            }            
            
            if ( "compareValue".equals(r.getCheckmethod()) || "compareProperty".equals(r.getCheckmethod())
                ||"compareValueAsNumber".equals(r.getCheckmethod()) || "comparePropertyAsNumber".equals(r.getCheckmethod())
                || "compareValueSysPara".equals(r.getCheckmethod())
                ) {
                if ( r.getCompareto()==null ||r.getCompareto().length()<1 ) {
                    logger.error("检查规则错误：数据比较方法逻辑运算符号不能为空!["+r.getCheckruleid()+"]");
                    continue;                     
                }
                if ( !setCompareto.contains(r.getCompareto()) ) {
                    logger.error("检查规则错误：数据比较方法逻辑运算符号不合法!["+r.getCheckruleid()+"]");
                    continue;                     
                }  
                if ( "compareValueAsNumber".equals(r.getCheckmethod()) ) {
                    if ( !BaseCheckLib.isNumeric(r.getObjvalue()) )  {
                        logger.error("检查规则错误：数据比较方法目标值非数字!["+r.getCheckruleid()+"]");
                        continue;                                             
                    }
                }
            }
            
            if ( "isNumber".equals(r.getCheckmethod()) ) {
                if ( r.getObjvalue()!=null && r.getObjvalue().length()>0 ) { //目标值不空，则必须符合"数字1,数字2"格式，且数字1>0数字2
                    String [] objFormat = r.getObjvalue().split(",");
                    try {
                        int allbit = Integer.parseInt(objFormat[0]);
                        int decbit = Integer.parseInt(objFormat[1]);
                        if ( allbit<decbit ) {
                            logger.error("检查规则错误："+r.getCheckmethod()+"isNumber方法的目标格式数字1>数字2!["+r.getCheckruleid()+"]");
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"isNumber方法的目标格式错误!["+r.getCheckruleid()+"]");
                        continue;
                    }
                    
                }
                    
            }
            
            if ( "isCustomizedString".equals(r.getCheckmethod()) ) {
                String format = r.getObjvalue();
                try {
                    Pattern.compile(Pattern.quote(format));
                } catch (Exception e) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"方法的目标格式错误!["+r.getCheckruleid()+"]");
                    continue;
                }
            }            
            r.setListConditions(mapConditions.get(r.getCheckruleid()));
            
            listResult.add(r);
            
        }
        
        return listResult;
    }
    
    /**
     * 根据数据类型，读取检查规则
     * @param datatype 数据类型
     * @return 检查规则列表
     * @see
     * @since 1.0
     */
    public List<CheckRule> queryCheckRulesByDataTypeAndColumns(String datatype) {
        
        String baseCheckLib = "com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib";
        Map<String,Method> mapMethod = new HashMap<String,Method>();
        try {
            Method[] rm = Class.forName(baseCheckLib).getDeclaredMethods();
            for (Method m1 : rm) {
                mapMethod.put(m1.getName(), m1);
            }
        }
        catch (SecurityException e) {
            logger.error(e.getMessage(),e);
        }
        catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        }
        
        Map<String,List<CheckCondition>> mapConditions = new HashMap<String,List<CheckCondition>>();
        HashSet<String> setCompareto = new HashSet<String>();
        setCompareto.add(">");
        setCompareto.add("<");
        setCompareto.add(">=");
        setCompareto.add("<=");
        setCompareto.add("==");
        setCompareto.add("!=");
        
        List<CheckCondition> listConditions = checkConditionMapper.selectByRuleid(null);
        for ( CheckCondition r:listConditions ) {
            if ( r.getCheckmethod()==null ) {
                logger.error("检查规则错误：检查条件中方法名称不能为空!["+r.getCheckconditionid()+"]");
                continue;
            }
            if ( !mapMethod.containsKey(r.getCheckmethod()) ) { //不合法的方法
                logger.error("检查规则错误：检查条件中无效的方法名称!["+r.getCheckconditionid()+"]");
                continue;
            }
            if ( r.getSrcproperty()==null ||r.getSrcproperty().length()<1) {
                logger.error("检查规则错误：检查条件中被检查的属性不能为空!["+r.getCheckconditionid()+"]");
                continue;                
            }

            if ( !"isNotEmpty".equals(r.getCheckmethod()) && !"isNumber".equals(r.getCheckmethod())  ) {
                if ( r.getObjvalue()==null ||r.getObjvalue().length()<1 ) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标值不能为空!["+r.getCheckconditionid()+"]");
                    continue; 
                } else if ( "lessThanLength".equals(r.getCheckmethod()) ) {
                    try {
                        Integer.parseInt(r.getObjvalue());
                    } catch(Exception e ) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式错误!["+r.getCheckruleid()+"]");
                    }
                }
            }

            if ( "compareValue".equals(r.getCheckmethod()) || "compareProperty".equals(r.getCheckmethod()) 
                ||"compareValueAsNumber".equals(r.getCheckmethod()) || "comparePropertyAsNumber".equals(r.getCheckmethod())
                || "compareValueSysPara".equals(r.getCheckmethod())
                ) {
                if ( r.getCompareto()==null ||r.getCompareto().length()<1 ) {
                    logger.error("检查规则错误：检查条件中数据比较方法逻辑运算符号不能为空!["+r.getCheckconditionid()+"]");
                    continue;                     
                }
                if ( !setCompareto.contains(r.getCompareto()) ) {
                    logger.error("检查规则错误：检查条件中数据比较方法逻辑运算符号不合法!["+r.getCheckconditionid()+"]");
                    continue;                     
                }
                if ( "compareValueAsNumber".equals(r.getCheckmethod())) {
                    if ( !BaseCheckLib.isNumeric(r.getObjvalue()) )  {
                        logger.error("检查规则错误：检查条件中数据目标值非数字!["+r.getCheckconditionid()+"]");
                        continue;                                             
                    }
                }
            }
            
            if ( "isNumber".equals(r.getCheckmethod()) ) {
                if ( r.getObjvalue()!=null && r.getObjvalue().length()>0 ) { //目标值不空，则必须符合"数字1,数字2"格式，且数字1>0数字2
                    String [] objFormat = r.getObjvalue().split(",");
                    try {
                        int allbit = Integer.parseInt(objFormat[0]);
                        int decbit = Integer.parseInt(objFormat[1]);
                        if ( allbit<=decbit ) {
                            logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式数字1>数字2!["+r.getCheckconditionid()+"]");
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式错误!["+r.getCheckconditionid()+"]");
                        continue;
                    }
                }
            }

            if ( "isCustomizedString".equals(r.getCheckmethod()) ) {
                String format = r.getObjvalue();
                try {
                    Pattern.compile(Pattern.quote(format));
                } catch (Exception e) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"检查条件中方法的目标格式错误!["+r.getCheckconditionid()+"]");
                    continue;
                }
            }

            List<CheckCondition> listRuleCon = mapConditions.get(r.getCheckruleid());
            if ( listRuleCon == null ) {
                listRuleCon = new ArrayList<CheckCondition>();
                listRuleCon.add(r);
                mapConditions.put(r.getCheckruleid(),listRuleCon);
            } else {
                listRuleCon.add(r);
            }
            
        }
        
        List<CheckRule> listResult = new ArrayList<CheckRule>();

        /**业务数据维护   资产要素查询规则  start  by  dongxiangyang   20201024 */
        List<CheckRule> listRules = checkRuleMapper.selectByDatatype(datatype);
        /**业务数据维护  资产要素查询规则  end  by  dongxiangyang   20201024 */
//        List<CheckRule> listRules = checkRuleMapper.selectByDatatypeByTypeAndColumns(datatype, columns);
        for ( CheckRule r:listRules ) {
            if ( r.getCheckclass()==null ) {
                logger.error("检查规则错误：检查类不能为空!["+r.getCheckruleid()+"]");
                continue;
            }
            if ( !baseCheckLib.equals(r.getCheckclass()) ) {  //非基本库的方法不做检查
                listResult.add(r);
                continue;
            }
            if ( r.getCheckmethod()==null ) {
                logger.error("检查规则错误：方法名称不能为空!["+r.getCheckruleid()+"]");
                continue;
            }
            if ( !mapMethod.containsKey(r.getCheckmethod()) ) { //不合法的方法
                logger.error("检查规则错误：无效的方法名称!["+r.getCheckruleid()+"]");
                continue;
            }
            if ( r.getSrcproperty()==null ||r.getSrcproperty().length()<1) {
                logger.error("检查规则错误：被检查的属性不能为空!["+r.getCheckruleid()+"]");
                continue;                
            }
            
            if ( !"isNotEmpty".equals(r.getCheckmethod()) && !"isNumber".equals(r.getCheckmethod())  ) {
                if ( r.getObjvalue()==null ||r.getObjvalue().length()<1 ) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"方法的目标值不能为空!["+r.getCheckruleid()+"]");
                    continue; 
                } else if ( "lessThanLength".equals(r.getCheckmethod()) ) {
                    try {
                        Integer.parseInt(r.getObjvalue());
                    } catch(Exception e ) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"方法的目标格式错误!["+r.getCheckruleid()+"]");
                    }
                }
            }            
            
            if ( "compareValue".equals(r.getCheckmethod()) || "compareProperty".equals(r.getCheckmethod())
                ||"compareValueAsNumber".equals(r.getCheckmethod()) || "comparePropertyAsNumber".equals(r.getCheckmethod())
                || "compareValueSysPara".equals(r.getCheckmethod())
                ) {
                if ( r.getCompareto()==null ||r.getCompareto().length()<1 ) {
                    logger.error("检查规则错误：数据比较方法逻辑运算符号不能为空!["+r.getCheckruleid()+"]");
                    continue;                     
                }
                if ( !setCompareto.contains(r.getCompareto()) ) {
                    logger.error("检查规则错误：数据比较方法逻辑运算符号不合法!["+r.getCheckruleid()+"]");
                    continue;                     
                }  
                if ( "compareValueAsNumber".equals(r.getCheckmethod()) ) {
                    if ( !BaseCheckLib.isNumeric(r.getObjvalue()) )  {
                        logger.error("检查规则错误：数据比较方法目标值非数字!["+r.getCheckruleid()+"]");
                        continue;                                             
                    }
                }
            }
            
            if ( "isNumber".equals(r.getCheckmethod()) ) {
                if ( r.getObjvalue()!=null && r.getObjvalue().length()>0 ) { //目标值不空，则必须符合"数字1,数字2"格式，且数字1>0数字2
                    String [] objFormat = r.getObjvalue().split(",");
                    try {
                        int allbit = Integer.parseInt(objFormat[0]);
                        int decbit = Integer.parseInt(objFormat[1]);
                        if ( allbit<decbit ) {
                            logger.error("检查规则错误："+r.getCheckmethod()+"isNumber方法的目标格式数字1>数字2!["+r.getCheckruleid()+"]");
                            continue;
                        }
                    } catch (Exception e) {
                        logger.error("检查规则错误："+r.getCheckmethod()+"isNumber方法的目标格式错误!["+r.getCheckruleid()+"]");
                        continue;
                    }
                    
                }
                    
            }
            
            if ( "isCustomizedString".equals(r.getCheckmethod()) ) {
                String format = r.getObjvalue();
                try {
                    Pattern.compile(Pattern.quote(format));
                } catch (Exception e) {
                    logger.error("检查规则错误："+r.getCheckmethod()+"方法的目标格式错误!["+r.getCheckruleid()+"]");
                    continue;
                }
            }            
            r.setListConditions(mapConditions.get(r.getCheckruleid()));
            
            listResult.add(r);
            
        }
        
        return listResult;
    }
}