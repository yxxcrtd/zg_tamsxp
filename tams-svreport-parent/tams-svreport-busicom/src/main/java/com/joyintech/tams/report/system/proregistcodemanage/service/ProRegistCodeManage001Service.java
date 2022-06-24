/*
 * 文件名：ProRegistCodeManage001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：tangliang
 * 修改时间：2017年9月20日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.system.proregistcodemanage.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbPrdresistCodeMapper;
import com.joyintech.tams.common.base.entity.ChbFileReadMonitorInfo;
import com.joyintech.tams.common.base.entity.ChbPrdBaseinfo;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.base.entity.ChbPrdresistCodeKey;
import com.joyintech.tams.report.common.TxtParseUtil;
import com.joyintech.tams.report.common.XmlParseUtil;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.submitmaintenance.businessdetailed.service.EquityBusiDetails001Service;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service.InvestorsHoldInfo001Service;
import com.joyintech.tams.report.system.proregistcodemanage.dao.ProRegistCodeManage001Mapper;
import com.joyintech.tams.report.system.proregistcodemanage.dto.ProRegistCodeManage001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;

/**
 * 产品登记编码管理
 * 〈功能详细描述〉
 * @author tangliang
 * @version 1.0 2017年9月20日
 * @see ProRegistCodeManage001Service
 * @since 1.0
 */
@Service
public class ProRegistCodeManage001Service extends CommonExportService<ProRegistCodeManage001Dto> {

    /**
     * ProRegistCodeManage001Mapper
     */
    @Autowired
    private ProRegistCodeManage001Mapper proRegistCodeManage001Mapper;
    /**
     * ChbPrdresistCodeMapper
     */
    @Autowired
    private ChbPrdresistCodeMapper chbPrdresistCodeMapper;

    /**
     * InvestorsHoldInfo001Service
     */
    @Autowired
    private InvestorsHoldInfo001Service investorsHoldInfo001Service;
    /**
     * EquityBusiDetails001Service
     */
    @Autowired
    private EquityBusiDetails001Service equityBusiDetails001Service;
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 上游接口解析分割符
     */
    @Value("${application.reportInterfSplitCharacter}")
    private String reportInterfSplitCharacter;

    /**
     * 上游接口文件编码格式
     */
    @Value("${application.reportInterfFileEncoding}")
    public String reportInterfFileEncoding;

    /**
     * 产品登记编码信息列表
     * @param param 查询参数
     * @return 产品登记编码信息列表
     */
    public Page<ProRegistCodeManage001Dto> searchForPage(Map<String, Object> param){
//        String orgCode = (String)param.get("orgCode");
//        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
//        if (StringUtil.isBlank(orgCode)) {
//            orgCodeList = CommonUtil.getOrgCodeListForSearch();
//        }
        param.put("orgCodeList", LoginUserUtil.getTreeInstCode());
        String prodname = (String)param.get("prodname");
        String investor = (String)param.get("investor");
        param.put("prodList", StringUtil.splitToList(GlobalConstant.COMMA, prodname));
        param.put("investorList", StringUtil.splitToList(GlobalConstant.COMMA, investor));
        String regCode = (String)param.get("regCode");
        param.put("regCodeList", StringUtil.splitToList(GlobalConstant.COMMA, regCode));

        param.put("sendStatus03", DictCodeList.发送状态_未发送.getValue());
        int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
        Page<ProRegistCodeManage001Dto> page;
        if(param.get("type").equals("N")){
            page=  super.queryForPage(
                    pagenum,legth,()->proRegistCodeManage001Mapper.queryForPage(param));
        }else{
            page =  super.queryForPage(
                    pagenum,legth,()->proRegistCodeManage001Mapper.queryForPageOld(param));
        }
//        page.getData().forEach(item->{
//            item.setInvestor(SysDataUtil.getNameFromDataDict(DictCode.投资者类型, item.getInvestor()));
//        });
        dealDataList(page.getRoot());
        return page;
    }
    /**
     * 处理查询结果 <功能详细描述>
     *
     * @param list
     *            待处理数据
     * @see
     * @since 1.0
     */
    private void dealDataList(List<ProRegistCodeManage001Dto> list) {
        list.forEach(item -> {
            item.setOperateMode(SysDataUtil.getZZDictCodeAndName(
                    DictCode.产品运作模式, item.getOperateMode()));
        });
    }
    /**
     * 删除产品登记编码
     * <p>
     * 按照主键删除产品登记编码
     * </p>
     *
     * @param keys 主键字符串
     */
    public void deteteRegCode(String keys){
        List<String> listKey = StringUtil.splitToList(GlobalConstant.COMMA, keys);
        //删除主键  CHBPRDRESISTCODEke
        ChbPrdresistCodeKey chbPrdresistCodeKey = null;
        for (String string : listKey) {
            chbPrdresistCodeKey  = new ChbPrdresistCodeKey();
            String[] key = StringUtil.split(string, "|");
            chbPrdresistCodeKey.setProdInsId(key[0]);
            chbPrdresistCodeKey.setOrgCode(key[1]);
            chbPrdresistCodeMapper.deleteByPrimaryKey(chbPrdresistCodeKey);
        }
    }
    /**
     * 查询所有内容，不分页
     * <p>
     * 导出功能需要导出查询条件的所有内容
     * </p>
     *
     * @param param 画面查询参数
     * @return 查询结果列表
     */
    public List<ProRegistCodeManage001Dto> queryReportList(Map<String, Object> param) {
        boolean isNotNull = Boolean.parseBoolean((String)param.get("isNotNull"));
        param.put("isNotNull", isNotNull);
        String keys = (String)param.get("keys");
        String prodname = (String)param.get("prodname");
        String investor = (String)param.get("investor");
        String orgCode = (String)param.get("orgCode");
        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
        if (StringUtil.isBlank(orgCode)) {
            orgCodeList = CommonUtil.getOrgCodeListForSearch();
        }
        param.put("orgCodeList", orgCodeList);
        //add by yujiaping 2021-01-22 ZGBSS-301 未选中时导出与查询结果不一致start
        param.put("orgCodeList", LoginUserUtil.getTreeInstCode());
        //add by yujiaping 2021-01-22 ZGBSS-301 未选中时导出与查询结果不一致end
        param.put("keys", StringUtil.splitToList(GlobalConstant.COMMA, keys));
        param.put("prodList", StringUtil.splitToList(GlobalConstant.COMMA, prodname));
        param.put("investorList", StringUtil.splitToList(GlobalConstant.COMMA, investor));
        String regCode = (String)param.get("regCode");
        param.put("regCodeList", StringUtil.splitToList(GlobalConstant.COMMA, regCode));
//      List<ProRegistCodeManage001Dto> dataList = proRegistCodeManage001Mapper.queryForPage(param);
        List<ProRegistCodeManage001Dto> dataList;
        if(param.get("type").equals("N")){
            dataList = proRegistCodeManage001Mapper.queryForPage(param);
        }else{
            dataList = proRegistCodeManage001Mapper.queryForPageOld(param);
        }
//        dataList.forEach(item->{
//            item.setInvestor(SysDataUtil.getZZDictCodeAndName(DictCode.投资者类型, item.getInvestor()));
//        });
        dataList.forEach(item -> {
            item.setOperateMode(SysDataUtil.getZZDictCodeAndName(
                    DictCode.产品运作模式, item.getOperateMode()));
            item.setInvestor(SysDataUtil.getZZDictCodeAndName(
                    DictCode.目标客户类型, item.getInvestor()));
        });
        return dataList;
    }


    /**
     * 导入文件
     * <p>
     * 解析excel文件并进行验证
     * </p>
     *
     * @param fileName 导入的文件名名
     * @return 导入结果
     */
    public List<ExcelValidDto> importData(List<List<String>> dataList,List<List<String>> tempList,String bankCode,String orgCode,String deptCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<String>();
            headList.add("发行机构名称");
            headList.add("产品名称");
            headList.add("产品登记编码");
            headList.add("行内标识码");
            headList.add("理财产品代码");
            headList.add("产品运作模式");
            headList.add("目标客户类型");
            headList.add("产品状态");
            headList.add("产品登记编码生成日");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<ChbPrdresistCode> entities = new ArrayList<>(dataList.size());
                Map <String,String> box= new HashMap<String, String>();
                List<ExcelValidDto> checkResult = convertToEntity(dataList, entities,box,orgCode);
                if(checkResult.isEmpty()) {
                    entities.forEach(item -> {
                        ChbPrdresistCode record = new ChbPrdresistCode();
                        BeanUtil.copyPropertiesIgnoreNull(record, item);
                        //机构代码
                        record.setOrgCode(orgCode);
                        //部门代码
                        record.setDeptCode(deptCode);
//                        record.setOrgCode(this.getLogger().getOrgCode());
//                        record.setDeptCode(this.getLogger().getDeptCode());

//                        // createDept字段设值
//                        record.setCreateDept(record.getDeptCode());
//                        if (StringUtil.isEmpty(record.getCreateDept())) {
//                            record.setCreateDept(record.getOrgCode());
//                        }
                        // 不做权限控制
                        record.setCreateDept(null);
                        record.setFlag("2");

                        //产品要素维护管理中产品代码不存在 存在则更新数据
                        record.setReg_code(record.getRegCode());//修复bug add by zhangzhognliang 2019-03-29
                        ChbPrdresistCode dto = chbPrdresistCodeMapper.selectByPrimaryKey(record);
                        if (dto != null){
                            chbPrdresistCodeMapper.updateByPrimaryKeySelective(record);
                        }
                        else{
                            //不存在插入新数据
                            chbPrdresistCodeMapper.insertSelective(record);
                        }

//                        chbPrdresistCodeMapper.updateByPrimaryKeySelective(record);
                    });

                    // 根据最新导入的有效的产品登记编码数据，联动处理持有者信息待处理数据
                    investorsHoldInfo001Service.refreshStatusAData(entities, bankCode,deptCode,orgCode);
                 //add by dengyu监管检查投资者明细报送修改20201022---》根据最新导入的有效的产品登记编码数据，联动处理投资者明细信息待处理数据 begin
                    equityBusiDetails001Service.refreshStatusAData(entities,bankCode,deptCode,orgCode);
                  //add by dengyu监管检查投资者明细报送修改20201022---》根据最新导入的有效的产品登记编码数据，联动处理投资者明细信息待处理数据 end
                }
                return checkResult;
            }
        }
        return null;
    }

    /**
     * excel数据转为java对象
     * <p> 将excel导入的数据，按行转为ProRegistCodeManage001Dto对象，同时验证各字段值</p>
     *
     * @param dataList excel导入的数据
     * @param entities 转化后的列表
     * @param box
     * @return 验证信息
     */
    private List<ExcelValidDto> convertToEntity(List<List<String>> dataList,
                                                List<ChbPrdresistCode> entities, Map<String, String> box,String orgCode) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            if (null == list || list.size() != 9) {
                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
                        this.getMessage("REPORT_COMMON_FILE_E_0001", "文件内容")));
                continue;
            }
            ChbPrdresistCode entity = new ChbPrdresistCode();
            // 验证消息
            List<String> message = new ArrayList<>();
            //系统中所有产品代码对应产品登记编码信息
            Map<String,String> prodIdToRegCodeMap = proRegistCodeManage001Mapper.selectProdInfo().stream().collect(JoyinCollectors.toMap(x->x.get("prodId"),x->x.get("regCode")));
            //产品代码            	//update by leipeng 产品登记编码导入完成后，导入模板理财产品代码为cp002 但是导入库是行内标识码
            /****ZGBSSE-907  产品登记编码管理画面，导入时，理财产品代码长度为100时，报错【产品代码输入的内容超过最大长度限制，最大长度为30】**/
            String error0 =checkCode2(list.get(3), "行内标识码", 100, true);
            if (StringUtil.isNotEmpty(error0)) {
                message.add(error0);
            }else {
                if(proRegistCodeManage001Mapper.selectInfoByProdId(list.get(3)) == null){
                    message.add("行内标识码不存在产品申报表中！");
                }else{
                    entity.setProdInsId(list.get(3));
                }
                //update by leipeng 产品登记编码导入完成后，导入模板理财产品代码为cp002 但是导入库是行内标识码

//                Map<String , Object> map =new HashMap<>();
//                map.put("prodInsId",entity.getProdInsId());
//                map.put("orgCode", orgCode);
//                if(proRegistCodeManage001Mapper.isExist(map)>0||box.containsKey(entity.getProdid())){
//                    message.add("同一机构中已存在此产品代码");
//                }
//                else{

//                }
            }
            if(StringUtils.isNotEmpty(list.get(4))){
                entity.setProdid(list.get(4));
            }
            if(StringUtils.isNotEmpty(list.get(5))){
                entity.setOperateMode(list.get(5).split(" ")[0]);
            }
            //产品名称
            String error1 =checkCode2(list.get(1), "产品名称", 200, false);
            if (StringUtil.isNotEmpty(error1)) {
                message.add(error1);
            }
            else {
                entity.setProdname(list.get(1));
            }
            //产品登记编码
//            if (StringUtil.isEmpty(list.get(2))) {
//                message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "产品登记编码"));
//            }
//            else {
//                entity.setRegCode(list.get(2));
//            }

            //重复的产品登记编码
            String error2 =checkCode(list.get(2), "产品登记编码", 200, true);
            if(StringUtil.isNotEmpty(error2)){
                message.add(error2);
            }
            else if(CommonUtil.getStringLen(list.get(2))!=14&&CommonUtil.getStringLen(list.get(2))!=15){
//                message.add(this.getMessage("REPORT_COMMON_FILE_E_0004", "产品登记编码"));
                message.add(this.getMessage("REPORT_COMMON_FILE_E_0026", "产品登记编码", "14", "15"));
            }
            //delete by  wangyaning 2022-03-15 导入check应该与新建check保持一致 Start
//            else if(!Pattern.compile("^[0-9a-zA-Z]{6}[0-9]{2}[A]?[0-9]{6}$").matcher(list.get(2)).matches()){
//                message.add(this.getMessage("REPORT_COMMON_E_0067", "产品登记编码"));
//            }
            //delete by  wangyaning 2022-03-15 导入check应该与新建check保持一致 End
            else{
                Boolean isRight=true;
                //从产品申报信息中查询数据
                ChbPrdDeclareInfoDto dtoDeclare = proRegistCodeManage001Mapper.selectInfoByProdId(list.get(4));
                if(null!=dtoDeclare){
                    if(StringUtil.equals(dtoDeclare.getRaiseType(), DictCodeListExt2.募集方式_中债_公募.getValue())){
                        //公募产品
                        if(!Pattern.compile("^[0-9a-zA-Z]{6}[0-9]{2}[0-9]{6}$").matcher(list.get(2)).matches()){
                            message.add(this.getMessage("REPORT_COMMON_E_0067", "产品登记编码"));
                            isRight=false;
                        }
                    }else if(StringUtil.equals(dtoDeclare.getRaiseType(), DictCodeListExt2.募集方式_中债_私募.getValue())){
                        //私募产品
                        if(!Pattern.compile("^[0-9a-zA-Z]{6}[0-9]{2}[A]{1}[0-9]{6}$").matcher(list.get(2)).matches()){
                            message.add(this.getMessage("REPORT_COMMON_E_0067", "产品登记编码"));
                            isRight=false;
                        }
                    }
                }
                if(isRight){
                    entity.setRegCode(list.get(2));
                    if(prodIdToRegCodeMap.values().contains(list.get(2))){
                        if(!StringUtil.equals(this.getMapKeyByValue(prodIdToRegCodeMap,list.get(2)),list.get(4))){
                            message.add("不同的产品代码不能对应同一产品登记编码");
                        }
                    }
                    else if(box.values().contains(list.get(2))){
                        if(!StringUtil.equals(this.getMapKeyByValue(box,list.get(2)),list.get(4))){
                            message.add("不同的产品代码不能对应同一产品登记编码");
                        }
                    }
                    box.put(entity.getProdid(),list.get(2));
                }
//                Map<String , Object> map =new HashMap<>();
//                map.put("regCode",entity.getRegCode());
//                map.put("orgCode",this.getUserFromSession().getOrgCode());
//                ProRegistCodeManage001Dto dto = proRegistCodeManage001Mapper.queryRegCode(map);
//                if (dto!=null){
//                    message.add(this.getMessage("REPORT_COMMON_E_0028", "产品登记编码"));
//                }
            }
//            String vdate=list.get(3);
//            String mdate=list.get(4);
//            //起息日
//            if(StringUtil.isNotEmpty(vdate)){
//                if(!DateUtil.isDateStr(vdate, DateUtil.DEFAULT_OTHER_FORMAT,
//                    DateUtil.DEFAULT_ANOTHER_FORMAT,DateUtil.DEFAULT_DATE_FORMAT_EN)){
//                    message.add(this.getMessage("REPORT_COMMON_FILE_E_0013", "起息日"));
//                }
//                else {
//                    entity.setVdate(DateUtil.parse(vdate));
//                }
//            }
//            //到期日
//            if(StringUtil.isNotEmpty(mdate)){
//                if(!DateUtil.isDateStr(mdate, DateUtil.DEFAULT_OTHER_FORMAT,
//                    DateUtil.DEFAULT_ANOTHER_FORMAT,DateUtil.DEFAULT_DATE_FORMAT_EN)){
//                    message.add(this.getMessage("REPORT_COMMON_FILE_E_0013", "到期日"));
//                }
//                else {
//                    entity.setMdate(DateUtil.parse(mdate));
//                }
//            }
//            if(entity.getVdate()!=null&&entity.getMdate()!=null){
//                if(DateUtil.compareDate(entity.getVdate(), entity.getMdate())>0){
//                    message.add("起息日不能大于到期日！");
//                }
//            }
            List<ProRegistCodeManage001Dto> list1 = proRegistCodeManage001Mapper.queryProdid(list.get(4));
            if (list1.size() != 0 && StringUtil.isEmpty(list.get(2))){
//                message.add(this.getMessage("系统中存在对应数据，更新数据请输入产品登记编码！"));
                message.add("系统中存在对应数据，更新数据请输入产品登记编码！");
            }
            /*String error3 =checkCode2(list.get(5), "产品运作模式", 50, false);
            if (StringUtil.isNotEmpty(error3)) {
                message.add(error3);
            }
            else {
                //产品运作模式
                if(StringUtil.isNotEmpty(list.get(5))){
                    String operateMode=list.get(5).substring(0,2);
                    entity.setOperateMode(operateMode);
                }
            }*/
            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(entity.getProdid(), String.valueOf(i+2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
            }
            else {
                entities.add(entity);
            }
        }
        return checkResult;
    }

    /**
     *
     * 实现map中通过value值获取key值
     * @param map 基础map
     * @param value 指定value值
     * @return 对应key值
     * @see
     * @since 1.0
     */
    private String getMapKeyByValue(Map<String,String> map,String value){
        if(map!=null){
            for (String key : map.keySet()) {
                if(StringUtil.equals(value, map.get(key))){
                    return key;
                }
            }
        }
        return null;
    }
    /**
     * 判断代码是否合法，返回错误信息
     *
     * @param code 代码
     * @param length 长度
     * @param cloumnTitle 列名
     * @param isRequired 是否必须
     * @return 错误信息
     */
    private String checkCode(String code, String cloumnTitle,int length, boolean isRequired) {
        // 必须验证
        if (StringUtil.isEmpty(code)) {
            if (isRequired) {
                return this.getMessage("REPORT_COMMON_E_0001", cloumnTitle);
            }
            return null;
        }
        // 属性验证
        if (!CommonUtil.isEnglish(code)) {
            return this.getMessage("REPORT_COMMON_E_0004", cloumnTitle);
        }
        // 长度验证
        if (code.length() > length) {
            return this.getMessage("REPORT_COMMON_E_0002", cloumnTitle, length);
        }
        return null;
    }
    /**
     * 判断代码是否合法，返回错误信息
     *
     * @param code 代码
     * @param length 长度
     * @param cloumnTitle 列名
     * @param isRequired 是否必须
     * @return 错误信息
     */
    private String checkCode2(String code, String cloumnTitle,int length, boolean isRequired) {
        // 必须验证
        if (StringUtil.isEmpty(code)) {
            if (isRequired) {
                return this.getMessage("REPORT_COMMON_E_0001", cloumnTitle);
            }
            return null;
        }
        // 长度验证
        if (CommonUtil.getStringLen(code)> length) {
            return this.getMessage("REPORT_COMMON_E_0002", cloumnTitle, length);
        }
        return null;
    }

    /**
     * 查询产品登记代码
     * @param pageParam 参数
     * @return 结果list
     * @see
     * @since 1.0
     */
    public List<SelectOptionDto> queryPrdRegList(Map<String, Object> pageParam) {
//        Page<SelectOptionDto> result = new Page<SelectOptionDto>();
        String orgCode = (String)pageParam.get("orgCode");
        List<String> orgCodeList = StringUtil.splitToList(GlobalConstant.COMMA, orgCode);
        if (StringUtil.isBlank(orgCode)) {
            orgCodeList = CommonUtil.getOrgCodeListForSearch();
        }
        pageParam.put("orgCodeList", orgCodeList);
        List<SelectOptionDto> dtoList = new ArrayList<SelectOptionDto>();
        //在所选的机构list不为空或者是用户为管理员时
        dtoList = proRegistCodeManage001Mapper.queryPrdRegList(pageParam);
//        result.setRoot(dtoList);
//        return result;

        return dtoList;
    }

    public List<SelectOptionDto> queryAllRegistCode(){
        return proRegistCodeManage001Mapper.queryAllRegistCode();
    }
    /**
     * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
     *
     * @param fileName 导入的文件名名
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList,String orgCode,String deptCode) {
        AjaxResult webResult = new AjaxResult();
        if (!StringUtil.isEmptyList(dataList)) {
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<ProRegistCodeManage001Dto> entities = convertToBean(dataList);
                List<String> data = new ArrayList<String>();
                if(entities.size()>100){
                    List<ProRegistCodeManage001Dto> newDataList = new ArrayList<ProRegistCodeManage001Dto>();
                    newDataList.addAll(entities);
                    int part = entities.size() / 1000; // 分批数
                    for (int i = 0; i < part; i++ ) {
                        data.addAll(proRegistCodeManage001Mapper.getRegistExcelList(newDataList.subList(0, 1000),orgCode));
                        newDataList.subList(0, 1000).clear();
                    }
                    // 最后剩下的数据
                    if (CollectionUtils.isNotEmpty(newDataList)) {
                        data.addAll(proRegistCodeManage001Mapper.getRegistExcelList(newDataList,orgCode));
                    }
                }
                else{
                    data = proRegistCodeManage001Mapper.getRegistExcelList(entities,orgCode);
                }
                if (data != null && data.size() > 0) {
                    Map<String, List<ProRegistCodeManage001Dto>> equityInfo001Map = entities.stream().collect(
                            JoyinCollectors.groupingBy(
                                    x -> x.getProdId()));
                    // Collections.sort(data, Comparator.comparing(EquityInfo001Dto::getExcelNum));
                    // 将序号放入list中
                    List<String> excelNumList = new ArrayList<String>();
                    List<Integer> excelNumIntList = new ArrayList<Integer>();
                    for (String string : data) {
                        List<ProRegistCodeManage001Dto> listTemp = equityInfo001Map.get(string);
//                        excelNumList.add(listTemp.get(0).getExcelNum());
                        excelNumIntList.add(Integer.valueOf(listTemp.get(0).getExcelNum()));
                    }
                    // 将list进行排序，并转化为string
//                    Collections.sort(excelNumList);
                    Collections.sort(excelNumIntList);
                    for (Integer rowNo : excelNumIntList) {
                        excelNumList.add(String.valueOf(rowNo));
                    }
                    // String messages = StringUtil.joinString(excelNumList, GlobalConstant.COMMA);
                    webResult.setMessage(
                            this.getMessage("CHB_ISCOVER_EQU",
                                    StringUtil.joinString(excelNumList, GlobalConstant.COMMA)),
                            MessageType.确认消息);
                }
                else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }
            }
            else{
                // 防止在进行导入前check时进行了两次的弹框
                webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
            }
        }
        return webResult;
    }
    /**
     * excel数据转为java对象
     * <p> 将excel导入的数据，按行转为ProRegistCodeManage001Dto对象，同时验证各字段值</p>
     *
     * @param dataList excel导入的数据
     * @return 返回list
     */
    private List<ProRegistCodeManage001Dto> convertToBean(List<List<String>> dataList) {
        List<ProRegistCodeManage001Dto> entities = new ArrayList<ProRegistCodeManage001Dto>();
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            ProRegistCodeManage001Dto entity = new ProRegistCodeManage001Dto();
//            entity.setProdId(list.get(3));
            entity.setProdId(list.get(4));
            entity.setProdName(list.get(1));
            entity.setRegCode(list.get(2));
            entity.setExcelNum(String.valueOf(i+2));
            entities.add(entity);
        }
        return entities;
    }
    /**
     * 校验产品代码是否已经被使用
     * @param map 产品代码
     * @return 结果条数
     * @see
     * @since 1.0
     */
    public Integer selectProdIsUsed(Map<String, Object> map) {
        return proRegistCodeManage001Mapper.selectProdIsUsed(map);
    }
    public List<ChbPrdBaseinfo> getProdinfo( Map<String, Object> param) {
        return proRegistCodeManage001Mapper.getProdinfo( param);
    }

    public String getOperateModeByInterf(InputStream in, String interType, String tmpPath, String suffix,
                                         ChbFileReadMonitorInfo monitorInfoDto) {

        List<Map<String, String>> infoList = null;
        if ("xml".equals(suffix)) {
            try {
                infoList = XmlParseUtil.parse(in);
                
                if(StringUtil.isEmptyList(infoList)){
                	return "success";
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(),e);
                return "fail";
            }
        }
        else if ("txt".equals(suffix)) {
            try {
                infoList = TxtParseUtil.parse(in, reportInterfFileEncoding, reportInterfSplitCharacter);
                
                if(StringUtil.isEmptyList(infoList)){
                	return "success";
                }
            } catch (ParserConfigurationException | SAXException | IOException e) {
                logger.error(e.getMessage(),e);
                return "fail";
            }
        }

        boolean success = true;

        // 解析成功或失败的标识
        if (!StringUtil.isEmptyList(infoList)) {
            monitorInfoDto.setFileDataCount(BigDecimal.valueOf(infoList.size()));
            if(infoList.size() <= 1000){
                //
                success=excuteOperateModeByStep(infoList);
            }else{
                // 创建一个线程池
                ExecutorService service = new JoyinThreadPoolExecutor(20);
                List<Callable<Boolean>> tasks = new ArrayList<>();
                if (!StringUtil.isEmptyList(infoList)) {
                    int start = 0;
                    int end = 0;
                    int size = infoList.size();
                    int max = (int)Math.ceil((double)size / 1000);
                    for (int i = 0; i < max; i++ ) {
                        start = end;
                        end = Math.min(size, 1000 * (i + 1));
                        List<Map<String, String>> subList = infoList.subList(start, end);
                        tasks.add(new Callable<Boolean>() {

                            @Override
                            public Boolean call() {
                                return excuteOperateModeByStep(subList);
                            }
                        });
                    }
                    // 为线程池添加任务
                    List<Future<Boolean>> taskResult;
                    try {
                        taskResult = service.invokeAll(tasks);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(),e);
                        return "fail";
                    }
                    for (Future<Boolean> res : taskResult) {
                        try {
                            success = success && res.get();
                        } catch (InterruptedException | ExecutionException e) {
                            logger.error(e.getMessage(),e);
                            return "fail";
                        }
                    }
                    // 任务结束，停止线程池的所有线程
                    service.shutdown();
                }

            }
        }

        if (success) {
            return "success";
        }
        return "fail";
    }


    public boolean excuteOperateModeByStep(List<Map<String, String>> infoList){

        if (!StringUtil.isEmptyList(infoList)) {
            List<ProRegistCodeManage001Dto> entities = new ArrayList<>(infoList.size());

            convertMapToEntity(infoList,entities);

            proRegistCodeManage001Mapper.updateOperateModeByProdId(entities);
            return true;
        }

        return false;
    }

    private void convertMapToEntity(List<Map<String, String>> infoList,
                                    List<ProRegistCodeManage001Dto> entities) {
        for (Map<String, String> map : infoList) {
            ProRegistCodeManage001Dto proRegistCodeManage001Dto =new ProRegistCodeManage001Dto();
            proRegistCodeManage001Dto.setProdId(map.get("ACCOUNT"));
            proRegistCodeManage001Dto.setOperateMode(map.get("OPERATE_MODE"));
            entities.add(proRegistCodeManage001Dto);
        }
    }
    
    public AjaxResult checkConfirmMsgForImportChbData(List<List<String>> dataList, String orgCode, String deptCode) {
		AjaxResult webResult = new AjaxResult();
		if (!StringUtil.isEmptyList(dataList)) {
			if (CollectionUtils.isNotEmpty(dataList)) {
				List<ProRegistCodeManage001Dto> entities = convertChbDataToBean(dataList);
				List<String> data = new ArrayList<String>();
				if (entities.size() > 100) {
					List<ProRegistCodeManage001Dto> newDataList = new ArrayList<ProRegistCodeManage001Dto>();
					newDataList.addAll(entities);
					int part = entities.size() / 1000; // 分批数
					for (int i = 0; i < part; i++) {
						data.addAll(
								proRegistCodeManage001Mapper.getRegistExcelList(newDataList.subList(0, 1000), orgCode));
						newDataList.subList(0, 1000).clear();
					}
					if (CollectionUtils.isNotEmpty(newDataList)) {
						data.addAll(proRegistCodeManage001Mapper.getRegistExcelList(newDataList, orgCode));
					}
				} else {
					data = proRegistCodeManage001Mapper.getRegistExcelList(entities, orgCode);
				}

				if (data != null && data.size() > 0) {
					Map<String, List<ProRegistCodeManage001Dto>> equityInfo001Map = entities.stream()
							.collect(JoyinCollectors.groupingBy(x -> x.getProdId()));
					List<String> excelNumList = new ArrayList<String>();
					List<Integer> excelNumIntList = new ArrayList<Integer>();
					for (String string : data) {
						List<ProRegistCodeManage001Dto> listTemp = equityInfo001Map.get(string);
						excelNumIntList.add(Integer.valueOf(listTemp.get(0).getExcelNum()));
					}
					Collections.sort(excelNumIntList);
					for (Integer rowNo : excelNumIntList) {
						excelNumList.add(String.valueOf(rowNo));
					}
					webResult.setMessage(this.getMessage("CHB_ISCOVER_EQU",
							StringUtil.joinString(excelNumList, GlobalConstant.COMMA)), MessageType.确认消息);
				} else {
					// 防止在进行导入前check时进行了两次的弹框
					webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
				}
			} else {
				webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
			}
		}
		return webResult;
	}

	private List<ProRegistCodeManage001Dto> convertChbDataToBean(List<List<String>> dataList) {
		List<ProRegistCodeManage001Dto> entities = new ArrayList<ProRegistCodeManage001Dto>();
		for (int i = 0; i < dataList.size(); i++) {
			List<String> list = dataList.get(i);
			ProRegistCodeManage001Dto entity = new ProRegistCodeManage001Dto();
			String prodName = list.get(1);
			String regCode = list.get(2);
			String identification = list.get(3);
			String prodId = list.get(4);
			if (!StringUtil.isNotEmpty(prodId)) {
				prodId = identification;
			}

			entity.setProdId(prodId);
			entity.setProdName(prodName);
			entity.setRegCode(regCode);
			entity.setExcelNum(String.valueOf(i + 2));
			entities.add(entity);
		}
		return entities;
	}

	public List<ExcelValidDto> importChbData(List<List<String>> dataList, List<List<String>> tempList, String bankCode,
			String orgCode, String deptCode) {
		if (!StringUtil.isEmptyList(dataList)) {
			List<String> headList = new ArrayList<String>();
			headList.add("发行机构名称");
			headList.add("产品名称");
			headList.add("产品登记编码");
			headList.add("行内标识码");
			headList.add("理财产品代码");
			headList.add("产品运作模式");
			headList.add("目标客户类型");
			headList.add("产品状态");
			headList.add("产品登记编码生成日");
			List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
			if (!StringUtil.isEmptyList(tempCheckResult)) {
				return tempCheckResult;
			}
			if (null != dataList) {
				List<ChbPrdresistCode> entities = new ArrayList<>(dataList.size());
				Map<String, String> box = new HashMap<String, String>();
				List<ExcelValidDto> checkResult = convertToChbEntity(dataList, entities, box, orgCode);
				if (checkResult.isEmpty()) {
				    entities.forEach(item -> {
				        ChbPrdresistCode record = new ChbPrdresistCode();
				        BeanUtil.copyPropertiesIgnoreNull(record, item);
				        // 机构代码
				        record.setOrgCode(orgCode);
				        // 部门代码
				        record.setDeptCode(deptCode);
				        record.setCreateDept(null);
				        record.setFlag("2");

				        // 产品要素维护管理中产品代码不存在 存在则更新数据
				        record.setReg_code(record.getRegCode());

				        ChbPrdresistCode dto = chbPrdresistCodeMapper.selectByPrimaryKey(record);
				        if (dto != null) {
				            chbPrdresistCodeMapper.updateByPrimaryKeySelective(record);
				        } else {
				            // 不存在插入新数据
				            chbPrdresistCodeMapper.insertSelective(record);
				        }
				    });
				}
				return checkResult; 
			}
		}
		return null;
	}

	private List<ExcelValidDto> convertToChbEntity(List<List<String>> dataList, List<ChbPrdresistCode> entities,
			Map<String, String> box, String orgCode) {
		List<ExcelValidDto> checkResult = new ArrayList<>();
		for (int i = 0; i < dataList.size(); i++) {
			List<String> list = dataList.get(i);
			if (null == list || list.size() != 9) {
				checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
						this.getMessage("REPORT_COMMON_FILE_E_0001", "文件内容")));
				continue;
			}
			ChbPrdresistCode entity = new ChbPrdresistCode();
			// 验证消息
			List<String> message = new ArrayList<>();
			// 系统中所有产品代码对应产品登记编码信息
			Map<String, String> prodIdToRegCodeMap = proRegistCodeManage001Mapper.selectProdInfo().stream()
					.collect(JoyinCollectors.toMap(x -> x.get("PRODID"), x -> x.get("REGCODE")));

			String prodName = list.get(1);
			String regCode = list.get(2);
			String identification = list.get(3);
			String prodId = list.get(4);
			if (!StringUtil.isNotEmpty(prodId)) {
				prodId = identification;
			}
			entity.setProdid(prodId);
			Map<String, Object> map = new HashMap<>();
			map.put("prodid", entity.getProdid());
			map.put("orgCode", orgCode);

			// 产品名称
			String error1 = checkCode2(prodName, "产品名称", 200, false);
			if (StringUtil.isNotEmpty(error1)) {
				message.add(error1);
			} else {
				entity.setProdname(prodName);
			}
			// 重复的产品登记编码
			String error2 = checkCode(regCode, "产品登记编码", 200, true);
			if (StringUtil.isNotEmpty(error2)) {
				message.add(error2);
			} else if (CommonUtil.getStringLen(regCode) != 14 && CommonUtil.getStringLen(regCode) != 15) {
				message.add(this.getMessage("REPORT_COMMON_FILE_E_0026", "产品登记编码", "14", "15"));
			} else if (!Pattern.compile("^[0-9a-zA-Z]{6}[0-9]{2}[ABC]?[0-9]{6}$").matcher(regCode).matches()) {
				message.add(this.getMessage("REPORT_COMMON_E_0067", "产品登记编码"));
			} else {
				entity.setRegCode(regCode);
				if (prodIdToRegCodeMap.values().contains(regCode)) {
					if (!StringUtil.equals(this.getMapKeyByValue(prodIdToRegCodeMap, regCode), prodId)) {
						message.add("不同的产品代码不能对应同一产品登记编码");
					}
				} else if (box.values().contains(regCode)) {
					if (!StringUtil.equals(this.getMapKeyByValue(box, regCode), prodId)) {
						message.add("不同的产品代码不能对应同一产品登记编码");
					}
				}
				box.put(entity.getProdid(), regCode);
			}

			List<ProRegistCodeManage001Dto> list1 = proRegistCodeManage001Mapper.queryProdid(prodId);
			if (list1.size() != 0 && StringUtil.isEmpty(regCode)) {
				message.add("系统中存在对应数据，更新数据请输入产品登记编码！");
			}
			if (CollectionUtils.isNotEmpty(message)) {
				checkResult.add(new ExcelValidDto(entity.getProdid(), String.valueOf(i + 1),
						StringUtil.joinString(message, GlobalConstant.COMMA)));
			} else {
				entities.add(entity);
			}
		}
		return checkResult;
	}

    @Override
    public void setCellValue(String beanName, Cell cell, String propName, Object value) {
        if (cell.getColumnIndex()==5) {
            CellStyle cStyle = cell.getRow().getSheet().getWorkbook().createCellStyle();
            cStyle.cloneStyleFrom(cell.getRow().getSheet().getRow(1).getCell(4).getCellStyle());
            cell.setCellStyle(cStyle);
        }

    }
}