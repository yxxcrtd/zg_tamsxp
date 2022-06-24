/*
 * 文件名：ViewCompareDetailController.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：zuoyinyi
 * 修改时间：2017年12月6日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.common.controller;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.annotation.PageId;
import com.joyintech.framework.base.BaseController;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbPrdBaseinfoMapper;
import com.joyintech.tams.common.base.dao.ChbPrdresistCodeMapper;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.base.entity.ChbPrdresistCodeKey;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.base.annotate.BusinessKey;
import com.joyintech.tams.report.common.base.annotate.DictFormat;
import com.joyintech.tams.report.common.base.annotate.TableName;
import com.joyintech.tams.report.common.dao.ViewCompareDetailMapper;
import com.joyintech.tams.report.common.dto.RegistDetailDto;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.systemtools.util.CommonUtil;

import oracle.sql.TIMESTAMP;

import javax.annotation.Resource;

/**
 * 查看对比详情共同画面
 *
 * @author zuoyinyi
 * @version 1.0 2017年12月6日
 * @see ViewCompareDetailController
 * @since 1.0
 */
@Controller
@PageId("ViewCompareDetail")
@RequestMapping("/common/detail")
public class ViewCompareDetailController extends BaseController {

    /**
     * 界面特殊处理字段
     */
    enum PageFiledList {

        产品资金募集信息("PbcPrdCashRaiseSub", new String[] { "PROD_VALUE_CNY", "UNIT_VALUE_TOTAL_CNY" }),

        产品发行登记("SvrProdPersonIssue001", new String[] { "YIELD_LOW", "YIELD_HIGH" });


        /**
         * 界面id
         */
        private String pageId;

        /**
         * 特殊处理字段
         */
        private String[] filedArr;

        public String getPageId() {
            return pageId;
        }

        public void setPageId(String pageId) {
            this.pageId = pageId;
        }

        public String[] getFiledArr() {
            return filedArr;
        }

        public void setFiledArr(String[] filedArr) {
            this.filedArr = filedArr;
        }

        PageFiledList(String pageId, String[] filedArr) {
            this.pageId = pageId;
            this.filedArr = filedArr;
        }
    }
    enum CCYDictCodeList{
        中债币种_人民币( "CNY", "人民币"),
        /**
         * 中债币种_美元
         */
        中债币种_美元("USD", "美元"),
        /**
         * 中债币种_欧元
         */
        中债币种_欧元( "EUR", "欧元"),
        /**
         * 中债币种_英镑
         */
        中债币种_英镑( "GBP", "英镑"),
        /**
         * 中债币种_日元
         */
        中债币种_日元( "JPY", "日元"),
        ;
        /**
         * 对应于数据字典code
         */
        private String value;

        /**
         * 明细名称
         */
        private String codeName;
        /**
         * 获取 value
         *
         * @return value.
         */
        public String getValue() {
            return value;
        }
        /**
         * 设置 value
         *
         * @param value value
         */
        public void setValue(String value) {
            this.value = value;
        }
        /**
         * 获取 codeName
         *
         * @return codeName.
         */
        public String getCodeName() {
            return codeName;
        }

        /**
         * 设置 codeName
         *
         * @param codeName codeName
         */
        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
        CCYDictCodeList( String value, String codeName) {
            this.value = value;
            this.codeName=codeName;
        }
        static CCYDictCodeList from( String name) {
            CCYDictCodeList[] values = CCYDictCodeList.values();
            for (CCYDictCodeList code : values) {
                if (StringUtil.equals(name, code.getCodeName())) {
                    return code;
                }
            }
            return null;
        }
    }
    /**
     * ViewCompareDetailMapper
     */
    @Autowired
    private ViewCompareDetailMapper mapper;

    /**
     * 产品登记编码
     */
    @Autowired
    private ChbPrdresistCodeMapper chbPrdresistCodeMapper;

    /**
     * 产品要素
     */
    @Autowired
    private ChbPrdBaseinfoMapper chbPrdBaseinfoMapper;

    /**
     * 日志
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * {@inheritDoc}
     */
    @Override
    @RequestMapping(PAGE_INIT)
    public String init(Model model) {
        return "report/common/ViewCompareDetail";
    }

    /**
     * 查询明细信息对比
     * @return 登记明细信息
     * @throws IOException
     */
    @RequestMapping("search")
    @ResponseBody
    public AjaxResult searchDetail() throws IOException {
        AjaxResult result = new AjaxResult();
        String pageId = this.getParam(GlobalConstant.PAGE_ID);
        String registSeqNo = this.getParam("registSeqNo");
        String content = this.getParam("fields");
        List<RegistDetailDto> detailList = createRegistDetail(pageId, registSeqNo, content);
        result.setData(detailList);
        return result;
    }

    /**
     *
     * 解析画面参数中的表格列定义
     * @param content 列定义JSON字符串
     * @return 明细列表
     * @throws IOException
     */
    private List<RegistDetailDto> createDetailList(String content) throws IOException {
        if (StringUtil.isNotEmpty(content)) {
            boolean isShowBelongInstitutionNameflag = MultilevelDataAuthorityUtil.isEnabled();
            List<RegistDetailDto> detailList = JsonUtils.json2List(content, RegistDetailDto.class);
            // 如果有反馈状态为feedbackStr则改为feedbackstatus\
//            detailList.forEach(item->{
//                if ("submitFeedbackStatusStr".equals(item.getData())){
//                    item.setData("submitFeedbackStatus");
//                }
//                if(isShowBelongInstitutionNameflag && "belongInstitutionName".equals(item.getData())){
//                    detailList.remove(item);
//                }
//
//            });
            Iterator<RegistDetailDto> iterator = detailList.iterator();
            while(iterator.hasNext()){
                RegistDetailDto item = iterator.next();
                // 如果有反馈状态为feedbackStr则改为feedbackstatus\
                if ("submitFeedbackStatusStr".equals(item.getData())){
                    item.setData("submitFeedbackStatus");
                }
                if (Objects.equals("pbcAccountIdShow",item.getData())){
                    item.setData("pbcAccountId");
                }
                // 银行模式不需要所属机构
                if(!isShowBelongInstitutionNameflag && "belongInstitutionName".equals(item.getData())){
                    iterator.remove();
                }
            }


            return detailList;
        }
        return null;
    }

    /**
     * 创建明细信息
     * @param pageId 画面ID
     * @param registSeqNo 登记序号
     * @param content 表格列定义
     * @return 明细信息列表
     * @throws IOException
     */
    private List<RegistDetailDto> createRegistDetail(String pageId, String registSeqNo,
                                                     String content) throws IOException {
        if (StringUtil.isNotEmpty(pageId)) {
            final List<RegistDetailDto> detailList = createDetailList(content);
            String serviceName = CommonUtil.firstLowerCase(pageId) + "Service";
            Object service = SpringContextUtil.getBean(serviceName);
            Class<?> targetService = service.getClass().getSuperclass();
            Type[] types = ((ParameterizedType)targetService.getGenericSuperclass()).getActualTypeArguments();
            if (types != null && types.length > 0) {
                try {
                    Class<?> targetClass = Class.forName(types[0].getTypeName());
                    TableName table = targetClass.getAnnotation(TableName.class);
                    String tableName = table.value();

                    if(CommonUtil.isBAmodel() && StringUtil.equals("SvrChbAstValueRegist001",pageId) && StringUtil.equals("CHB_AST_VAL_SUB",tableName) ){
                        tableName = "CHB_ASSET_SUB_STATUS";
                    }else if(CommonUtil.isBAmodel() && StringUtil.equals("SvrAssetInfoRegist001",pageId) && StringUtil.equals("CHB_ASSET_INFO_SUB",tableName) ){
                        tableName = "CHB_ASSET_SUB_STATUS";
                    }

                    final StringBuilder sb = new StringBuilder();
                    sb.append("current");
                    // 设置当前值
                    setValue(detailList, tableName, registSeqNo, targetClass,sb,pageId);
                    // 设置修改前值
                    String oldTableName = getTableName(detailList, tableName);
                    if(null != oldTableName) {
                        sb.setLength(0);
                        sb.append("old");
                        setValue(detailList, oldTableName, null, targetClass,sb,pageId);
                    }
                }
                catch (ClassNotFoundException e) {
                    this.getLogger().error("ViewCompareDetailController", e);
                }
            }
            //过滤掉隐藏字段
            List<RegistDetailDto> returndetailList =new ArrayList<RegistDetailDto>();
            for(RegistDetailDto registDetailDto : detailList){
                if(!StringUtil.equals("false",registDetailDto.getVisibleFlag())){
                    returndetailList.add(registDetailDto);
                }
                if(StringUtil.isEmpty(registDetailDto.getCurrentValue())){
                    registDetailDto.setCurrentValue("");
                }
                if(StringUtil.isEmpty(registDetailDto.getOldValue())){
                    registDetailDto.setOldValue("");
                }
            }
            return returndetailList;
        }
        return null;
    }

    /**
     * 设置属性值
     * @param detailList 明细信息列表
     * @param tableName 表名
     * @param registSeqNo 登记序列号
     * @param targetClass 目标class
     * @param sb 修改前后标志
     */
    private void setValue(List<RegistDetailDto> detailList, String tableName, String registSeqNo,
                          Class<?> targetClass,StringBuilder sb,String pageId) {
        Field[] fields = targetClass.getDeclaredFields();
        Map<String, Object> searchParam = new HashMap<>();
        //新人行表名称过长导致需要修改
        if("PBC_PRD_BASEINFO_END_SUB_NEW_HIS".equals(tableName)){
            tableName =  "PBC_PRD_BASEEND_SUB_NEW_HIS";
        }
        searchParam.put("tableName", tableName);
        List<Map<String, Object>> where = new ArrayList<>();
        searchParam.put("condition", where);
        //数据状态
        String status = null;
        //oder语句
        String oder = null;
        //报送状态
        String sendStatus = null;
        // 数据类型
        String type = null;
        // 原识别标识
        String orgIndentifyMark = null;
        if (null != registSeqNo) {
            Map<String, Object> registSeqNoMap = new HashMap<>(2);
            if(searchParam.get("tableName").equals("CHB_EQU_SUB") || searchParam.get("tableName").equals("CHB_EQUDET_SUB")
                    || searchParam.get("tableName").equals("CHB_EMPLOYEE_INFO_SUB")){
                registSeqNoMap.put("field", "SUMIT_SEQ");
            }
            else{
                registSeqNoMap.put("field", "REGIST_SEQNO");
            }
            registSeqNoMap.put("value", "'" + registSeqNo + "'");
            where.add(registSeqNoMap);

//            Map<String, Object> instMap = new HashMap<>(2);
//            if(CommonUtil.isBAmodel() && StringUtil.equals("CHB_ASSET_SUB_STATUS",tableName)){
//                instMap.put("field", "INSTITUTION_CODE");
//                instMap.put("value", "'$$$$$$'");
//                where.add(instMap);
//            }

        }
        else {
            for (Field field : fields) {
                BusinessKey busKey = field.getAnnotation(BusinessKey.class);
                if (null != busKey) {
                    String keyField = busKey.value();
                    String tmpKeyField = CommonUtil.dealString(StringUtil.lowerCase(keyField), false);
                    if(StringUtil.equals("registBankCode", tmpKeyField)){
                        tmpKeyField = "bankCode";
                    }
                    for (RegistDetailDto detail : detailList) {
                        if (StringUtil.equalsIgnoreCase(detail.getData(), tmpKeyField)) {
                            Map<String, Object> conditionMap = new HashMap<>(2);
                            field.setAccessible(true);
                            conditionMap.put("field", keyField);
                            // 如果业务主键存在联合主键一个键值为空时，则查不出数据
                            if (StringUtil.isEmpty(detail.getCurrentValue())){
                                continue;
                            }
                            if(tmpKeyField.equals("ccy")){
                                if(CCYDictCodeList.from(detail.getCurrentValue())!=null){
                                    detail.setCurrentValue(CCYDictCodeList.from(detail.getCurrentValue()).getValue());
                                }
                            }
                            if (StringUtil.equals(detail.getType(), "date")) {
                                conditionMap.put("value", "DATE '" + DateUtil.format(
                                        new Date(Long.valueOf(detail.getCurrentValue()))) + "'");
                            }
                            else if (StringUtil.equals(detail.getType(), "datetime")) {
                                conditionMap.put("value", "TIMESTAMP '"
                                        +
                                        DateUtil.format(
                                                new Date(Long.valueOf(detail.getCurrentValue())),
                                                DateUtil.DEFAULT_DATETIME_FORMAT) + "'");
                            }
                            else {
                                conditionMap.put("value", "'" + detail.getCurrentValue() + "'");
                                // add by zhangliang 20210324 从业人员登记双击查看特殊处理 start
                                if (StringUtil.equals(pageId, "SvrEmployeeInfoReg001") && StringUtil.equals(tmpKeyField, "employeeType")){
                                    conditionMap.put("value", "'" + StringUtil.splitToList(" ", detail.getCurrentValue()).get(0) + "'");
                                }
                                // add by zhangliang 20210324 从业人员登记双击查看特殊处理 end
                            }
                            where.add(conditionMap);
                        }
                        if(StringUtil.equalsIgnoreCase(detail.getData(), "status")){
                            status = detail.getCurrentValue();
                        }
                        if(StringUtil.equalsIgnoreCase(detail.getData(), "sendStatus")||StringUtil.equalsIgnoreCase(detail.getData(), "submitStatus")){
                            sendStatus = detail.getCurrentValue();
                        }
                        if(StringUtil.equalsIgnoreCase(detail.getData(), "submitStatus")){
                            oder = " ORDER BY STATUS DESC,SUBMIT_STATUS";
                        }
                        else if(StringUtil.equalsIgnoreCase(detail.getData(), "sendStatus")){
                            oder = " ORDER BY STATUS DESC,SEND_STATUS";
                        }

                        // 数据类型
                        if(StringUtil.equalsIgnoreCase(detail.getData(), "type")){
                            type = detail.getCurrentValue();
                        }
                        // 原识别标识
                        if(StringUtil.equalsIgnoreCase(detail.getData(), "orgIndentifyMark")){
                            orgIndentifyMark = detail.getCurrentValue();
                        }
                    }
                }
            }

            // 如果是履历表
            if(StringUtil.endsWith(tableName, "_HIS")) {
                //判断是mysql还是oracle数据库
                if (BaseCommonCheckService.isMYSQLDataBase()) {
                    searchParam.put("order", " ORDER BY IFNULL(UPDATE_TIME,CREATE_TIME) DESC");
                }else{
                    searchParam.put("order", " ORDER BY NVL(UPDATE_TIME,CREATE_TIME) DESC");
                }


            }
            else {
                if(searchParam.get("tableName").equals("CHB_SALER_STL_DEAL_SUB")){
                    oder = " ORDER BY DATA_STATUS DESC,SEND_STATUS";
                }
                searchParam.put("order", oder);
            }
        }
        List<Map<String, Object>> dataResultList = mapper.selectDetail(searchParam);

        // 产品资金募集信息特殊处理：为0时候显示空
        if (StringUtil.equals(pageId, PageFiledList.产品资金募集信息.getPageId())) {
            dataResultList.forEach(map->{
                map.forEach((key, value)->{
                    if(Arrays.asList(PageFiledList.产品资金募集信息.getFiledArr()).contains(key) && StringUtil.isNotEmpty(value)
                            && BigDecimal.ZERO.compareTo((BigDecimal)value)==0) {
                        map.put(key, null);
                    }
                });
            });
        }


        // 识别标识变更的情况，用原识别标识查询比较数据
        try {
            if (sb.toString().equals("old")) {
                if (StringUtil.isNotBlank(orgIndentifyMark)) {
                    for (Map<String, Object> dataMap : where) {
                        if (StringUtil.equalsIgnoreCase((String) dataMap.get("field"), "INDENTIFY_MARK")) {
                            dataMap.put("value", "'" + orgIndentifyMark + "'");
                        }
                    }

//                    List<Map<String, Object>> tempDataResultList = mapper.selectDetail(searchParam);
//                    dataResultList.addAll(0, tempDataResultList);

                    dataResultList = mapper.selectDetail(searchParam);
                    if(CollectionUtils.isEmpty(dataResultList)){
                        searchParam.put("tableName", tableName.replace("_HIS", ""));
                        searchParam.put("order", oder);
                        dataResultList = mapper.selectDetail(searchParam);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        if (CollectionUtils.isNotEmpty(dataResultList)) {
            Map<String, Object> dataResult = dataResultList.get(0);
            if(!StringUtil.equals(sendStatus, DictCodeListExt2.报送状态_未报送.getValue()) || StringUtil.equals(status, DictCodeListExt2.报送数据状态_新建.getValue()) || sb.toString().equals("old")){
                dataResult = dataResultList.get(dataResultList.size()-1);
            }

            //add by leipeng 20200708 多法人模式下界面中的发行机构代码字段由界面数字填充
            if(CommonUtil.isBAmodel()){
                String issue_party_id = "";
                String REGIST_BANK_CODE = "";
                String prodid = "";
                for (RegistDetailDto data : detailList) {
                    //获取前台界面传入的登记编码
                    if(data.getData().equals("issuePartyId")){
                        issue_party_id = data.getCurrentValue();
                    }
                    //前台传入的登记编码修改
                    if(data.getData().equals("registBankCode")){
                        REGIST_BANK_CODE = data.getCurrentValue();
                    }
                    //获取前台界面传入的产品编号
                    if(data.getData().equals("prodId")){
                        prodid = data.getCurrentValue();
                    }
                }
                if(StringUtil.isEmpty(issue_party_id)&&StringUtil.isEmpty(REGIST_BANK_CODE)
                        &&StringUtil.isNotEmpty(prodid)){
                    issue_party_id = mapper.selectBankCodeByOrgCode(prodid);
                    REGIST_BANK_CODE = issue_party_id;
                }
                dataResult.put("ISSUE_PARTY_ID", issue_party_id);
                dataResult.put("REGIST_BANK_CODE", REGIST_BANK_CODE);
            }

            for (Map<String, Object> map : dataResultList) {
                if(!StringUtil.equals(DictCodeListExt2.数据类型_中债_识别标识变更.getValue(), map.get("TYPE"))){
                    map.put("ORG_INDENTIFY_MARK", "");
                }
            }
            // 添加产品登记编码
            if (dataResult.containsKey("PROD_ID")){
                ChbPrdresistCodeKey key = new ChbPrdresistCodeKey();
                if (dataResult.get("PROD_ID") != null){
                    key.setProdInsId((String)dataResult.get("PROD_ID"));
                    key.setFlag("2");
                    if (dataResult.get("PROD_REG_CODE") == null){
                        // 查询产品登记编码
                        ChbPrdresistCode value = chbPrdresistCodeMapper.selectByPrimaryKey(key);
                        if (value != null){
                            dataResult.put("PROD_REG_CODE",value.getRegCode());
                        }
                    }
                    if (dataResult.get("PROD_NAME") == null){
                        // 查询产品名称
                        ChbPrdBaseinfo prdinfo = chbPrdBaseinfoMapper.selectByProdid((String)dataResult.get("PROD_ID"));
                        if (prdinfo != null){
                            dataResult.put("PROD_NAME",prdinfo.getProdName());
                        }
                    }
                }
            }
            dataResult.forEach((key, value) -> {
                String field = CommonUtil.dealString(StringUtil.lowerCase(key), false);
                detailList.forEach(detail -> {
                    if ((StringUtil.equalsIgnoreCase(detail.getData(), field)  &&StringUtil.isNotEmpty(value))|| (StringUtil.equals("registBankCode", field) && StringUtil.equals("bankCode", detail.getData()))
                            ||(StringUtil.equals("identifyType", detail.getData())&&(StringUtil.equals("personalIdentifyType", field)||StringUtil.equals("institutionIdentifyType", field))
                            &&StringUtil.isNotEmpty(value))
                            // modify by zhangliang 20201215 资产要素登记 委外投资属性特殊处理
                            || (StringUtil.equals("proProperty1", detail.getData()) && StringUtil.equals("proProperty", field))
                    ) {
                        String v ="";
                        if(StringUtil.equals("personalIdentifyType", field)||StringUtil.equals("institutionIdentifyType", field)){
                            v = getValue(value, field, fields);
                        }
                        else{
                            v = getValue(value, detail.getData(), fields);
                        }
                        if (sb.toString().equals("current")) {
                            detail.setCurrentValue(v);
                        }
                        else {
                            detail.setHasOldValue(true);
                            detail.setOldValue(v);
                        }
                    }
                });
            });
        }
    }

    /**
     * 获取属性值
     * @param value Object类型的值
     * @param field 属性名
     * @param fields Field[]
     * @return 转换后的值
     */
    private String getValue(Object value, String field, Field[] fields) {
        String v = StringUtil.EMPTY;
        try {
            DictFormat dict = null;
            for(Field ff : fields) {
                if(StringUtil.equalsIgnoreCase(ff.getName(), field)) {
                    dict = ff.getAnnotation(DictFormat.class);
                    break;
                }
            }
            // 数据字典
            if (null != dict) {
                DictCode code = dict.value();
                if(value instanceof String) {
                    // 只显示value不显示 code value的格式添加
                    if (DictCode.报送反馈状态.getValue().equals(code.getValue())
                            || DictCode.人行报送反馈状态.getValue().equals(code.getValue())
                            || DictCode.资产_负债类别.getValue().equals(code.getValue())){
                        v = SysDataUtil.getNameFromDataDict(code, (String)value);
                    } else {
                        v = SysDataUtil.getZZDictCodeAndName(code, (String)value);
                        //update zhangweiqi 2021-5-12 HYXXDJZL-101  【资产要素登记】项目类标的--非标资产/新增可投资，双击打开时，融资人所属地区字段为空 begin
                        if(DictCode.行政区划_省市县.getValue().equals(code.getValue())&&"finArea".equals(field)&&!StringUtil.isNotEmpty(v)){
                            if(StringUtil.isNotEmpty(SvrDataUtil.getNameFromDataDict(DictCode.行政区划_省市县,(String)value))){

                                v = (String)value+" "+SysDataUtil.getNameFromDataDict(DictCode.行政区划_省市县, (String)value);
                            }
                            else if(StringUtil.isNotEmpty(SvrDataUtil.getNameFromDataDict(DictCode.行政区划_省,(String)value))){

                                v = (String)value+" "+SysDataUtil.getNameFromDataDict(DictCode.行政区划_省, (String)value);

                            }else if(StringUtil.isNotEmpty(SvrDataUtil.getNameFromDataDict(DictCode.行政区划_市,(String)value))){

                                v = (String)value+" "+SysDataUtil.getNameFromDataDict(DictCode.行政区划_市, (String)value);

                            }else if(StringUtil.isNotEmpty(SvrDataUtil.getNameFromDataDict(DictCode.行政区划_县,(String)value))){

                                v = (String)value+" "+SysDataUtil.getNameFromDataDict(DictCode.行政区划_县, (String)value);
                            }
                        }
                        //update zhangweiqi 2021-5-12 HYXXDJZL-101  【资产要素登记】项目类标的--非标资产/新增可投资，双击打开时，融资人所属地区字段为空 end
                    }
                }
            }
            // 日期类型
            else if (value instanceof TIMESTAMP) {
                try {
                    TIMESTAMP time = (TIMESTAMP)value;
                    Date date = DateUtil.parse(time.dateValue() + " " + time.timeValue());
                    v = String.valueOf(date.getTime());
                }
                catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            else if (value instanceof Timestamp) {
                Timestamp time = (Timestamp)value;
                v = String.valueOf(time.getTime());
            }
            // 数值类型
            else if (value instanceof Number) {
                v = StringUtil.numberToString((BigDecimal)value);
            }
            else if(value instanceof String){
                v = value.toString();
            }
            //add by zcw mysql date类型特殊处理 20220427
            else if(value instanceof Date){
                Date time = (Date)value;
                v = String.valueOf(time.getTime());
            }
            //add by zcw mysql date类型特殊处理 20220427
        }
        catch (SecurityException e) {
            this.getLogger().error("ViewCompareDetailController.getValue", e);
        }
        return v;
    }

    /**
     * 获取对比数据来源的表名
     * @param detailList 明细列表
     * @param tableName 主表名
     * @return 对比数据表名
     */
    private String getTableName(List<RegistDetailDto> detailList, String tableName) {
        // 数据状态
        String status = StringUtil.EMPTY;
        // 报送状态
        String sendStatus = StringUtil.EMPTY;
        for(RegistDetailDto detail :detailList) {
            if(StringUtil.equals(detail.getData(), "status")) {
                status = detail.getCurrentValue();
            }
            else if(StringUtil.equals(detail.getData(), "sendStatus")||StringUtil.equals(detail.getData(), "submitStatus")) {
                sendStatus = detail.getCurrentValue();
            }
        }
        // 新建未报送和新建已报送的，不存在比较数据
        if(StringUtil.equals(status, DictCodeListExt2.报送数据状态_新建.getValue())||StringUtil.equals(status, DictCodeListExt2.报送数据状态_新建.getCodeName())) {
            return null;
        }
        // 修改状态的数据
        if(StringUtil.equals(status, DictCodeListExt2.报送数据状态_修改.getValue())||StringUtil.equals(status, DictCodeListExt2.报送数据状态_修改.getCodeName())) {
            // 报送状态为已报送的和履历表最新数据比较
            if(StringUtil.equals(sendStatus, DictCodeListExt2.报送状态_手工处理.getValue())
                    || StringUtil.equals(sendStatus, DictCodeListExt2.报送状态_接口报送.getValue())) {
                return tableName + "_HIS";
            }
            // 报送状态为未报送的和当前表前一条数据（已报送）比较
            if(StringUtil.equals(sendStatus, DictCodeListExt2.报送状态_未报送.getValue())) {
                return tableName;
            }
        }
        // 删除状态的数据同修改已报送
        if(StringUtil.equals(status, DictCodeListExt2.报送数据状态_删除.getValue())) {
            return tableName + "_HIS";
        }
        return null;
    }
}
