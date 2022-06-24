/**
 * @author wangyaning
 * @date 2022-04-11 14:52
 */
package com.joyintech.tams.common.svreport.base.service;

import com.joyintech.framework.jdbc.datasource.DatabaseInfo;
import com.joyintech.framework.jdbc.datasource.DatabaseType;
import com.joyintech.framework.jdbc.datasource.DatabaseTypeHelper;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.tams.common.svreport.base.dto.BaseCommonCheckDto;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckCondition;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckRule;
import com.joyintech.tams.common.svreport.datacheck.service.BaseCheckLib;
import com.joyintech.tams.common.svreport.datacheck.service.DataCheckService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.base.dao.BaseCommonCheckMapper;
import com.joyintech.tams.common.svreport.datacheck.dto.ChbInterfErrodataWithBLOBs;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;



@Service
public class BaseCommonCheckService {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    @Autowired
    private BaseCommonCheckMapper baseCommonCheckMapper;

    @Autowired
    private SqlSessionTemplate template;

    @Autowired
    private DataCheckService dataCheckService;

    @Autowired
    private BaseCheckMysqlService baseCheckMysqlService;

    @Autowired
    private BaseCheckOracleService baseCheckOracleService;


    /**
     * check包含DB和业务CHECK
     * @param checkFlag 是接口check还是导入check  接口是I  导入是D
     * @param datas 被check数据
     * @param TableName 表名
     * @param FileName 文件名
     * @param interfType 接口类型
     * @param bussinessKeys 业务主键
     * @return
     */
    public  List<ExcelValidDto> checkDataAll(String checkFlag,List<Map<String,String>> datas, String TableName, String FileName,String interfType,String... bussinessKeys) {
        //检查的总结果
        List<ExcelValidDto> checkExcelValid = new ArrayList<>();
        //DB数据库基础check
        List<ExcelValidDto> checkDB = checkDataDB(checkFlag,datas,TableName,FileName,interfType,bussinessKeys);
        //业务check
        List<ExcelValidDto> checkYW = checkDataYW(checkFlag,datas,TableName);

        checkExcelValid.addAll(checkDB);
        checkExcelValid.addAll(checkYW);

        //移除DBcheck不通过的List
        List<Map<String,String>> cleanList = new ArrayList<>();
        for(Map<String,String> cleanMap:datas){
            if(StringUtil.equals(cleanMap.get("STATUS"),"R")){
                cleanList.add(cleanMap);
            }
        }
        datas.removeAll(cleanList);

        return checkExcelValid;
    }

    /**
     *基础数据库check
     * @param checkFlag 是接口check还是导入check  接口是I  导入是D
     * @param datas 被check数据
     * @param TableName  表名
     * @param FileName  文件名
     * @param interfType 接口类型
     * @param bussinessKeys 业务主键
     * @return
     */
    public  List<ExcelValidDto> checkDataDB(String checkFlag,List<Map<String,String>> datas, String TableName, String FileName,String interfType,String... bussinessKeys) {
        // 判断传入的数据是否为空
        if(CollectionUtils.isEmpty(datas)) {
            return null;
        }

        //使用sql或者orcle?
        List<BaseCommonCheckDto> erroList = new ArrayList<>();
        //返回的数据结构
        List<ExcelValidDto> erroListValid = new ArrayList<>();
        if(isMYSQLDataBase()) {
            erroList = baseCheckMysqlService.check4Mysql(datas, TableName);
        }else{
            erroList = baseCheckOracleService.check4Oracle(datas, TableName);
        }

        //如果检查数据中存在错误数据就进行下一步处理 若无就返回空的检查结果
        if(!CollectionUtils.isEmpty(erroList)) {
            for (BaseCommonCheckDto dto : erroList) {
                erroListValid.add(dto.getExcelValidDto());
            }

            //当是接口接入时才入CHB_INTERF_ERRODATA表
            if (!CollectionUtils.isEmpty(erroList) && StringUtil.equals("I", checkFlag)) {
                this.dealErrAndInsert(erroList, FileName, interfType, bussinessKeys);
            }
            return erroListValid;
        }

        return erroListValid;
    }


    /**
     * 数据校验入口  业务
     * @param checkFlag 是接口check还是导入check  接口是I  导入是D
     * @param datas 被check数据
     * @param TableName  表名
     * @return 校验后的数据
     */
    public List<ExcelValidDto> checkDataYW(String checkFlag,List<Map<String,String>> datas, String TableName) {
        // 判断传入的数据是否为空
        if(CollectionUtils.isEmpty(datas)) {
            return null;
        }
        //查询数据库里配置的check规则
        List<CheckRule> listRules = this.queryCheckRules(TableName);
        //开始检查  处理listMap 并且返回listError
        List<ExcelValidDto> listError = this.checkDataList(datas,listRules);

        return listError;
    }


    /**
     * 入CHB_INTERF_ERRODATA表前的处理
     * @param list
     * @param FileName
     * @param interfType
     * @param bussinessKeys
     */
    public void dealErrAndInsert(List<BaseCommonCheckDto> list, String FileName, String interfType,String... bussinessKeys){
        List<Map<String,String>> erroDatas = new ArrayList<>();
        ChbInterfErrodataWithBLOBs data=null;
        List<ChbInterfErrodataWithBLOBs> erroList=new ArrayList<ChbInterfErrodataWithBLOBs>();
        for (BaseCommonCheckDto dto:list){
            dto.getErrMap().put("ERRMSG",dto.getExcelValidDto().getErrorInfo());
            erroDatas.add(dto.getErrMap());
        }
        for(Map<String, String> erro:erroDatas){
            //生成入CHB_INTERF_ERRODATA表的数据结构
            data = this.createChbInterfErrdata(FileName, interfType, erro, bussinessKeys);
            if(data != null) {
                erroList.add(data);
            }
        }
        if(!erroList.isEmpty()){
            //入CHB_INTERF_ERRODATA表
            this.insertErroData(erroList);
        }
    }

    /**
     * 生成入CHB_INTERF_ERRODATA表的数据结构
     * @param FileName
     * @param interfType
     * @param erro
     * @param bussinessKeys 业务主键
     * @return
     */
    public ChbInterfErrodataWithBLOBs createChbInterfErrdata(String FileName,String interfType,Map<String, String> erro,String... bussinessKeys){
        String msg = erro.get("ERRMSG");
        if (StringUtil.isEmpty(msg)) {
            return null;
        }
            //根据bussinessKeys来进行赋值业务主键
            StringBuffer keyVFinal = new StringBuffer();
            for(String key: bussinessKeys){
                keyVFinal.append(erro.get(key));
                keyVFinal.append("|");
            }
            String dataId = keyVFinal.toString();

        // 拼装错误信息
        ChbInterfErrodataWithBLOBs errodata = new ChbInterfErrodataWithBLOBs();
        // 主键uuid
        errodata.setDatauuid(IDGenerator.generate(36));
        // 文件名
        errodata.setFireName(FileName);
        // 业务主键
        errodata.setDataid(dataId);
        // 接口类型
        errodata.setInterfType(interfType);
        // 错误提示信息
        errodata.setErrMsg(msg);
        // 数据状态
        errodata.setStatus("E");
        errodata.setCreateUser("system");
        errodata.setUpdateUser("system");
        errodata.setCreateTime(new Date());
        errodata.setUpdateTime(new Date());
        erro.remove("ERRMSG");
        erro.remove("STATUS");
        errodata.setOrgData(JsonUtils.object2Json(erro));
        return errodata;
    }

    /**
     * 入CHB_INTERF_ERRODATA表
     * @param erroList
     */
    public void insertErroData(List<ChbInterfErrodataWithBLOBs> erroList){
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            BaseCommonCheckMapper batchMapper = session.getMapper(BaseCommonCheckMapper.class);
            for(ChbInterfErrodataWithBLOBs single: erroList){
                batchMapper.insertIntoErro(single);
                count ++;
                // 每20次提交
                if (count % 20 == 0) {
                    session.commit();
                    // 清理缓存，防止溢出
                    session.clearCache();
                }
            }
            session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
        }
        catch (Exception e) {
            session.rollback();
            logger.error("SQL执行异常：", e);
        }
        finally {
            session.close();
        }
    }


    /**
     * 判断系统使用的数据库是否是mysql
     *
     * @return 系统使用的数据库是否是mysql
     * @see
     * @since 1.0
     */
    public static boolean isMYSQLDataBase() {
        try {
            Map<String, List<DatabaseInfo>> databaseType = DatabaseTypeHelper.getDatabaseType();

            List<DatabaseInfo> dsList = databaseType.get(DataSourceType.MASERT);
            if (dsList != null && !dsList.isEmpty()) {
                for (int i = 0; i < dsList.size(); i++) {
                    DatabaseInfo dbInfo = dsList.get(i);
                    if (StringUtil.equalsIgnoreCase(DatabaseType.DATABASE_TYPE_MYSQL.getValue(),
                            dbInfo.getDbType().getValue())) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return false;
    }

    /**
     * 根据指定的检查规则，检查数据列表，对于某条数据有一个规则不满足，继续检查其它规则，即每条数据都进行全部规则检查
     * @param listData 被检查的数据列表，错误的数据根据参数removeFlag确定是否移除
     * @param listRules 检查规则
     * @return 返回错误的数据
     * @see
     * @since 1.0
     */
    public static List<ExcelValidDto> checkDataList(List<Map<String, String>> listData,List<CheckRule> listRules) {

        List<Map<String, String>> listError = new ArrayList<Map<String, String>>();
        List<ExcelValidDto> errorList = new ArrayList<>();

        //定义检查类的方法集合：Map<检查类,Map<方法名称,方法>>
        Map<String,Map<String, Method>> mapClassMethod = new HashMap<String,Map<String,Method>>();

        Iterator<Map<String, String>> it = listData.iterator();

        int i = 1;
        while (it.hasNext()) {         //循环检查指定的数据列表
            boolean legal=true;  //检查结果
            StringBuilder sb = new StringBuilder();  //记录错误信息
            Map<String, String> x = it.next();

            //数据库基础check不通过的不进行检查
            if(!StringUtil.equals(x.get("STATUS"),"R")) {
                for (CheckRule r : listRules) {  //循环每个规则
                    Map<String, Method> mapMethod = mapClassMethod.get(r.getCheckclass());
                    if (mapMethod == null) {
                        mapMethod = new HashMap<String, Method>();
                        try {
                            Method[] rm = Class.forName(r.getCheckclass()).getDeclaredMethods();
                            for (Method m1 : rm) {
                                mapMethod.put(m1.getName(), m1);
                            }
                        } catch (SecurityException e) {
                            logger.error(e.getMessage(), e);
                        } catch (ClassNotFoundException e) {
                            logger.error(e.getMessage(), e);
                        }
                        mapClassMethod.put(r.getCheckclass(), mapMethod);
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
                            if (StringUtil.isNotEmpty(c.getRemark()) && !StringUtil.equals(c.getRemark(), "null")) {
                                sbCon.append(c.getRemark() + ";");
                            }
                            if (conCheck == false) { // 条件不满足，不做数据检查
                                break;
                            }
                        }
                        if (conCheck == true) { // 条件满足，做数据检查
                            if (!StringUtil.equals(sbCon.toString(), "条件:")) {
                                sbCon.append("-->");
                            }
                            boolean b = checkData(x, r, mapMethod);
                            if (b == false) {
                                sb.append(sbCon.toString() + r.getRemark() + "[" + r.getSrcproperty() + "=" + x.get(r.getSrcproperty()) + "];");
                            }
                            legal &= b;
                        }
                    } else { // 无前置条件，做数据检查
                        boolean b = checkData(x, r, mapMethod);
                        if (b == false) {
                            sb.append(r.getRemark() + "[" + r.getSrcproperty() + "=" + x.get(r.getSrcproperty()) + "];");
                        }
                        legal &= b;
                    }
                }  //循环规则－结束
            }

            //检查不通过的数据处理
            if ( legal==false ) {
                ExcelValidDto erroMsg = new ExcelValidDto();
                erroMsg.setErrorInfo(sb.toString());
                x.put("ERRMSG", sb.toString());
                erroMsg.setValidKey("1已导入");
                erroMsg.setRowNum(String.valueOf(i + 1));
                if(!StringUtil.equals(x.get("STATUS"),"D") || x.get("STATUS").isEmpty()){
                    x.put("STATUS","A");
                }
                errorList.add(erroMsg);
                listError.add(x);
            }
            i++;
        } //循环数据结束

        return errorList;
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

        List<CheckCondition> listConditions = baseCommonCheckMapper.selectByRuleid(null);
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


        List<CheckRule> listRules = baseCommonCheckMapper.selectByDatatype(datatype);
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
     * 去除list中空字符串
     * @param list
     */
    public static void listRemoveEmptyString(List<String> list) {
        for(int i=0; i<list.size(); i++){
            if(StringUtil.isEmpty(list.get(i))){
                list.set(i, null);
            }
        }
    }
}
