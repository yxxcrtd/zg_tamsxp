/*
 * 文件名：ChbDataCheckCommonUtil.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管系统V2.0
 * 修改人：zhangliang
 * 修改时间：2020年11月20日
 * 修改内容：新建
 * 系统名称：理财资管系统V2.0
 */

package com.joyintech.component.svrreport.chb.chbcommoncheck;

import com.alibaba.fastjson.JSON;
import com.joyintech.background.component.common.pack.common.util.BeanUtil;
import com.joyintech.background.component.common.pack.common.util.DateUtil;
import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.background.component.common.pack.common.validation.ValidMessage;
import com.joyintech.component.svrreport.chb.chbcommoncheck.dao.ChbDataCheckCommonMapper;
import com.joyintech.component.svrreport.chb.chbcommoncheck.dto.ChbCheckRepType;
import com.joyintech.component.svrreport.chb.chbcommoncheck.dto.ChbCheckRule;
import com.joyintech.component.svrreport.chb.chbcommoncheck.dto.CheckCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ChbDataCheckCommonUtil {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(ChbDataCheckCommonUtil.class);

    /**
     *  ChbDataCheckCommonMapper
     */
    @Autowired
    private ChbDataCheckCommonMapper chbDataCheckCommonMapper;

    /**
     *  中债check共通调用入口
     * @param pageId 前台传入的pagecd
     * @param checkResultList 校验信息
     * @param entity 投组对象
     * @return 校验结果
     */
    public <T>List<T> chbCheckCommon(Object checkResultList, Object entity, String pageId, String repType, Class<T> cla){
        List<ValidMessage> resultList = objToList(checkResultList, ValidMessage.class);

        try{
            // 根据传入的entity对象找pageId
            if(StringUtil.isEmpty(pageId)){
                Annotation[] annoArra = entity.getClass().getAnnotations();
                if (annoArra.length > 0){
                    for (Annotation anno : annoArra){
                        if (StringUtil.equals(anno.annotationType().getName(), "com.joyintech.framework.annotation.PageId")){
                            pageId = anno.toString().substring(anno.toString().lastIndexOf("=") + 1, anno.toString().length() - 1);
                            break;
                        }
                    }
                }
            }

            if (null != pageId) {
                // add by zhangliang 20210520 关于接口接入标的信息触发校验需要全量校验的逻辑调整 start
                String pageFlag = "01";
                if (StringUtil.equals(pageId, "FinBondTargetAsset002Interf")){
                    pageId = "FinBondTargetAsset002";
                    pageFlag = null;
                }
                else if (StringUtil.equals(pageId, "FinFundTarget002Interf")){
                    pageId = "FinFundTarget002";
                    pageFlag = null;
                }
                else if (StringUtil.equals(pageId, "FinStockInfo002Interf")){
                    pageId = "FinStockInfo002";
                    pageFlag = null;
                }

                Map<String, String> entityMap = new HashMap<>();


                if(entity instanceof Map){
                    entityMap = (Map)entity;
                }else{
                    // add by zhangliang 20210520 关于接口接入标的信息触发校验需要全量校验的逻辑调整 end
                    Object object = repTypeHandle(entity, repType, pageId);

                    // 将对象转成Map对象
                    entityMap = convertEntityToMap(object, null,"");
                }



                // 根据pageId查询对应的配置信息
                List<ChbCheckRule> listRules = this.queryCheckRules(pageId, pageFlag);


                // 调用check验证处理
                Map<String, String> errorMap = this.checkDataList(entityMap,listRules);

                if (errorMap != null && errorMap.size() > 0){
                    for (String key : errorMap.keySet()){
                        resultList.add(new ValidMessage(key, errorMap.get(key)));
                    }
                }
            }
        }catch(Exception e){
            // 异常时不能影响投组原有逻辑
            logger.error(e.getMessage(), e);
        }

        return objToList(resultList, cla);
    }

    /**
     *  需要特殊处理一些类似于项目类资产页面有多个中债对象，但是只需要保留待校验的对象
     * @param entityObj 数据对象
     * @param repType 中债匹配类型
     * @param pageId 画面Id
     * @return
     */
    private Object repTypeHandle(Object entityObj, String repType, String pageId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 防止对传入对象的操作变化
        Object object = BeanUtil.cloneBean(entityObj);
//        if (StringUtil.isNotEmpty(repType)){
            List<ChbCheckRepType> repTypeList = chbDataCheckCommonMapper.queryChbCheckRepTypeList(pageId, repType);
            if (!StringUtil.isEmptyList(repTypeList)){
                for (ChbCheckRepType chbCheckRepType : repTypeList){
                    Field repDtoField = ReflectionUtils.findField(object.getClass(), chbCheckRepType.getSrcproperty());
                    repDtoField.setAccessible(true);
                    ReflectionUtils.setField(repDtoField, object, null);
                }
            }
//        }

        return object;
    }


    /**
     *  需要特殊处理一些类似于项目类资产页面有多个中债对象，但是只需要保留待校验的对象
     * @param entityObj 数据对象
     * @param repType 中债匹配类型
     * @param pageId 画面Id
     * @return
     */
    public static Object repTypeHandle(Object entityObj, String repType, String pageId,ChbDataCheckCommonMapper mapper) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // 防止对传入对象的操作变化
        Object object = BeanUtil.cloneBean(entityObj);
//        if (StringUtil.isNotEmpty(repType)){
        List<ChbCheckRepType> repTypeList = mapper.queryChbCheckRepTypeList(pageId, repType);
        if (!StringUtil.isEmptyList(repTypeList)){
            for (ChbCheckRepType chbCheckRepType : repTypeList){
                Field repDtoField = ReflectionUtils.findField(object.getClass(), chbCheckRepType.getSrcproperty());
                repDtoField.setAccessible(true);
                ReflectionUtils.setField(repDtoField, object, null);
            }
        }
//        }
        return object;
    }

    /**
     *  用于组件外层调用 入参和返回值的转换
     * @param obj 调用对象
     * @param cla 组件对象
     * @param <T>
     * @return
     */
    public static <T> List<T> objToList(Object obj, Class<T> cla) {
        List<T> list = new ArrayList<T>();
        if (obj != null && obj instanceof ArrayList<?>) {
            for (Object o : (List<?>) obj) {
                list.add(JSON.parseObject(JSON.toJSONString(o), cla));
            }
        }
        return list;
    }

    private static String converPreferStr(String preferStr){
        if (StringUtil.isNotEmpty(preferStr)){
            return preferStr + ".";
        }
        return preferStr;
    }

    public static Map<String, String> convertEntityToMap(Object entity, Map<String, String> map, String preferStr) throws IllegalAccessException {
        if (map == null){
            map = new HashMap<String, String>();
        }

        // 获取类、父类所有属性
        List<Field> fields = new ArrayList<>();
        Class objectClass = entity.getClass();
        while(objectClass != null && !StringUtil.equals(objectClass.getName(), "com.joyintech.framework.base.BaseEntity")){
            fields.addAll(Arrays.asList(objectClass.getDeclaredFields()));
            objectClass = objectClass.getSuperclass();
        }

        for (Field field : fields){
            field.setAccessible(true);
            if (StringUtil.startsWith(field.getType().getName(), "com.joyintech")){
                if (field.get(entity) != null){
                    convertEntityToMap(field.get(entity), map, StringUtil.isEmpty(preferStr) ? field.getName() : preferStr);
                }
            }else{
                // 此行判断是因为父类有些属性在子类重新定义导致值被覆盖
                String validKey = converPreferStr(preferStr) + field.getName();
                if (!map.containsKey(validKey)){

                    // 日期格式需要特殊处理
                    if ("java.util.Date".equals(field.getGenericType().getTypeName())) {
                        map.put(validKey, DateUtil.format((Date)field.get(entity)));
                    }else {
                        //其它类型直接toString可以实现转为字符串
                        if(field.get(entity)!=null){
                            map.put(validKey, field.get(entity).toString());
                        }else {
                            map.put(validKey, "");
                        }

                    }
                }
            }
        }

        return map;
    }

    /**
     * 根据数据类型，读取检查规则
     * @param datatype 数据类型
     * @return 检查规则列表
     * @see
     * @since 1.0
     */
    private List<ChbCheckRule> queryCheckRules(String datatype, String pageFlag) {
//        String chbBaseCheckLib = "com.joyintech.tams.invest.common.ChbBaseCheckLib";
//
//        Map<String, Method> mapMethod = new HashMap<String,Method>();
//        try {
//            Method[] rm = Class.forName(chbBaseCheckLib).getDeclaredMethods();
//            for (Method m1 : rm) {
//                mapMethod.put(m1.getName(), m1);
//            }
//        }
//        catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        Map<String,List<CheckCondition>> mapConditions = new HashMap<String,List<CheckCondition>>();
        HashSet<String> setCompareto = new HashSet<String>();
        setCompareto.add(">");
        setCompareto.add("<");
        setCompareto.add(">=");
        setCompareto.add("<=");
        setCompareto.add("==");
        setCompareto.add("!=");

        List<CheckCondition> listConditions = chbDataCheckCommonMapper.queryChbCheckCondition(null, pageFlag);
        for ( CheckCondition r:listConditions ) {
            if ( r.getCheckmethod()==null ) {
                logger.error("检查规则错误：检查条件中方法名称不能为空!["+r.getCheckconditionid()+"]");
                continue;
            }
//            if ( !mapMethod.containsKey(r.getCheckmethod()) ) { //不合法的方法
//                logger.error("检查规则错误：检查条件中无效的方法名称!["+r.getCheckconditionid()+"]");
//                continue;
//            }
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
                    if ( !this.isNumeric(r.getObjvalue()) )  {
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
                    Pattern.compile(format);
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

        List<ChbCheckRule> listResult = new ArrayList<ChbCheckRule>();

        Map<String, Map<String, Method>> mapClassMethod = new HashMap<String, Map<String, Method>>();

        List<ChbCheckRule> listRules = chbDataCheckCommonMapper.queryChbCheckRule(datatype, pageFlag);
        for ( ChbCheckRule r:listRules ) {
            if ( r.getCheckmethod()==null ) {
                logger.error("检查规则错误：方法名称不能为空!["+r.getCheckruleid()+"]");
                continue;
            }

            Map<String, Method> mapMethod = mapClassMethod.get(r.getCheckclass());
            if (mapMethod == null){
                mapMethod = new HashMap<String,Method>();
                try {
                    Method[] rm = Class.forName(r.getCheckclass()).getDeclaredMethods();
                    for (Method m1 : rm) {
                        mapMethod.put(m1.getName(), m1);
                    }
                }
                catch (Exception e) {
                    logger.error("检查规则错误：基础计算类不存在或者拒绝访问!["+r.getCheckclass()+"]");
                    continue;
                }
            }

            if ( !mapMethod.containsKey(r.getCheckmethod()) ) { //不合法的方法
                logger.error("检查规则错误：无效的方法名称!["+r.getCheckruleid()+"]");
                continue;
            }
            if ( r.getSrcproperty()==null ||r.getSrcproperty().length()<1) {
                logger.error("检查规则错误：被检查的属性不能为空!["+r.getCheckruleid()+"]");
                continue;
            }

            if ( !"isNotEmpty".equals(r.getCheckmethod()) && !"isNumber".equals(r.getCheckmethod()) && !"投资资产种类及比例".equals(r.getCheckmethod())) {
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
                    if ( !this.isNumeric(r.getObjvalue()) )  {
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
                    Pattern.compile(format);
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
     * 根据指定的检查规则，检查数据列表，对于某条数据有一个规则不满足，继续检查其它规则，即每条数据都进行全部规则检查
     * @param info 被检查的数据
     * @param listRules 检查规则
     * @return 返回错误的数据
     * @see
     * @since 1.0
     */
    private static Map<String, String> checkDataList(Map<String, String> info,List<ChbCheckRule> listRules) throws Exception{
        Map<String, String> errorMap = new HashMap<>();

//        String chbBaseCheckLib = "com.joyintech.tams.invest.common.ChbBaseCheckLib";
//        //定义检查类的方法集合：Map<检查类,Map<方法名称,方法>>
//        Map<String, Method> mapMethod = new HashMap<String,Method>();
//        try {
//            Method[] rm = Class.forName(chbBaseCheckLib).getDeclaredMethods();
//            for (Method m1 : rm) {
//                mapMethod.put(m1.getName(), m1);
//            }
//        }
//        catch (SecurityException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        //定义检查类的方法集合：Map<检查类,Map<方法名称,方法>>
        Map<String,Map<String,Method>> mapClassMethod = new HashMap<String,Map<String,Method>>();
        //循环每个规则
        for ( ChbCheckRule r : listRules ) {
            Map<String,Method> mapMethod = mapClassMethod.get(r.getCheckclass());
            if(mapMethod == null){
                mapMethod = new HashMap<String,Method>();
                Method[] rm = Class.forName(r.getCheckclass()).getDeclaredMethods();
                for (Method m1 : rm) {
                    mapMethod.put(m1.getName(), m1);
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
                // sbCon.append("条件:");
                for (CheckCondition c : listConditions) {
                    conCheck = conCheck && checkCondition(info, c, mapMethod);
                    if (StringUtil.isNotEmpty(c.getRemark()) && !StringUtil.equals(c.getRemark(), "null")){
                        sbCon.append(c.getRemark()+",");
                    }
                    if (conCheck == false) { // 条件不满足，不做数据检查
                        break;
                    }
                }
                if (conCheck == true) { // 条件满足，做数据检查
                    // sbCon.append("-->");
                    boolean b= checkData(info, r, mapMethod);
                    if (b == false) {
                        if (errorMap.containsKey(r.getSrcproperty())){
                            errorMap.put(r.getSrcproperty(), errorMap.get(r.getSrcproperty()) + ";" + sbCon.toString()+ r.getRemark());
                        }
                        else{
                            errorMap.put(r.getSrcproperty(), sbCon.toString()+ r.getRemark());
                        }
                    }
                }
            } else { // 无前置条件，做数据检查
                boolean b=checkData(info, r, mapMethod);
                if (b == false) {
                    if (errorMap.containsKey(r.getSrcproperty())){
                        errorMap.put(r.getSrcproperty(), errorMap.get(r.getSrcproperty()) + ";" + r.getRemark());
                    }
                    else{
                        errorMap.put(r.getSrcproperty(), r.getRemark());
                    }
                }
            }
        }

        // 样式处理，用于区分中债check和投组本身的check
        if(errorMap.size() > 0){
            for (String key : errorMap.keySet()){
                errorMap.put(key, errorMap.get(key));
            }
        }

        return errorMap;
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
    private static boolean checkCondition (final Map<String,String> data , CheckCondition condition, Map<String,Method> mapMethod ) throws Exception{
        Method method = mapMethod.get(condition.getCheckmethod());
        return (boolean)method.invoke(null,data, condition);

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
    private static boolean checkData (final Map<String,String> data , ChbCheckRule rule,Map<String,Method> mapMethod ) throws Exception {
        Method method = mapMethod.get(rule.getCheckmethod());
        return (boolean)method.invoke(null,data, rule);

    }

    /**
     * 判断指定的字符串是否是数字
     * @param str
     * @return
     * @see
     * @since 1.0
     */
    private static boolean isNumeric(String str) {
        if (str == null || str.trim().equals(""))
            return false;
        if (str.endsWith("."))  //不想调换复杂的正则表达式，对于[3.]这种数据也认为是合法的数字
            str = str.substring(0,str.length()-1);
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
}
