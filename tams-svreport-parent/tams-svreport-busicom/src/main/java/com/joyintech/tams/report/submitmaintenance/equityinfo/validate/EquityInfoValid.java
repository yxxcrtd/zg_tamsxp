/*
 * 文件名：EquityInfoValid.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年9月22日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitmaintenance.equityinfo.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.CheckUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.EquityInfoCheckUtil;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.ChbTransformRuleObjDto;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.TransformRule001Dto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.common.EquityInfoRegistCommonUtil;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.service.EquityInfoMaintenanceService;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 *
 * @author Administrator
 * @version 1.0 2017年9月22日
 * @see EquityInfoValid
 * @since 1.0
 */
@Component
public class EquityInfoValid {

    /**
     * 验证登记银行编码
     */
//    public static final String PATTERN_REGIST_BANK_CODE = "^C[0-9]{5}$";

    /**
     * 验证登记银行编码新规则，只能是6位的英文或者数字组合
     */
    public static final String PATTERN_REGIST_BANK_CODE_NEW = "^[0-9a-zA-Z]{6}$";

    /**
     * 总行机构代码验证
     */
    public static final String PATTERN_PBC_BANK_CODE_NEW = "^[0-9a-zA-Z]{14}$";

    /**
     * 不含有汉子验证
     */
    public static final String PATTERN_IS_CHINA = "^[^\u4e00-\u9fa5]{0,}$";

    /**
     * 15位英文数字验证验证
     */
    public static final String PATTERN_NUMBER_FIFTEEN = "^[0-9a-zA_Z]{15}+$";

    /**
     * 18位英文数字验证验证
     */
    public static final String PATTERN_NUMBER_EIGHTEEN = "^[0-9a-zA_Z]{18}+$";

    /**
     * 9位英文数字验证验证
     */
    public static final String PATTERN_NUMBER_NINE = "^[0-9a-zA_Z]{9}+$";

    /**
     * 手机号码验证
     */
    public static final String PATTERN_MOBILE_PHONE = "^[1][3,4,5,7,8][0-9]{9}$";

    /**
     * 带区号电话号码验证
     */
    public static final String PATTERN_TEPHONE = "^[0][0-9]{2,3}-[0-9]{5,10}$";

    /**
     * 不带区号电话号码验证
     */
    public static final String PATTERN_NO_TEPHONE = "^[1-9]{1}[0-9]{5,8}$";

    /**
     * 座机号码检查
     */
    //public static final String PATTERN_OFFICE_PHONE = "^(0[0-9]{2,3})+(\\-[0-9]{7,8})?(\\-[0-9]{1,8})?$";
    public static final String PATTERN_OFFICE_PHONE = "^(0[0-9]{2,3}-)?([0-9]{7,8}){1}(-[0-9]{1,8})?$";
    		//^([0-9]{3,4}-)?([0-9]{7,8}){1}(-[0-9]{1,8})?$
    /**
     * 白名单验证
     */
    public static final String PATTERN_ALL = "^[\\u4e00-\\u9fef\\u3400-\\u4db5\\x{00020000}-\\x{0002a6d6}\\x{0002a700}-\\x{0002b734}\\x{0002b740}-\\x{0002b81d}\\x{0002b820}-\\x{0002cea1}\\x{0002ceb0}-\\x{0002ebe0}\\uf900-\\ufad9\\ue815-\\ue86f\\u00b7\\u0020-\\u0026\\u0028-\\u007B\\u007D\\u007E\\u2000-\\u2040\\u2160-\\u216B\\u3000-\\u301F\\uFF00-\\uFF65\\uFFE0-\\uFFEF]{0,}$";

    /**
     * 邮箱验证
     */
    //public static final String PATTERN_MAILBOX = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
//    public static final String PATTERN_MAILBOX = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]+(\\.[a-zA-Z0-9_.-]+)*\\.[a-zA-Z0-9]{2,6}$";
    public static final String PATTERN_MAILBOX = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 投资者身份信息Mapper
     */
    @Autowired
    private EquityInfoRegist001Mapper equityInfoRegist001Mapper;

    private String getMessage(String key, String... args) {
        return SpringContextUtil.getTextValue(key, args);
    }

    /**
     * 检查excel是否为空或者传入列是否正确
     *
     * @param i 第几行
     * @param list 每一列解析后的List<String>
     * @return 检查结构
     * @see
     * @since 1.0
     */
    private ExcelValidDto checkExcelCow(int i, List<String> list) {
        //删除数据列 SPV 登记编码
        //列数变为25
//        if (null == list || list.size() != 25) {
        if (null == list || list.size() < 23) {
            return new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
                    this.getMessage("REPORT_COMMON_FILE_E_0021", "投资者信息维护导入"));
        }
        return null;
    }

    /**
     * 投资者识别标识验证
     *
     * @param message 检查消息
     * @param indentifyMark 投资者识别标识
     * @return message 检查消息
     * @see
     * @since 1.0
     */
    private List<String> checkIndentifyMark(List<String> message, String indentifyMark,
                                            Map<String,TransformRule001Dto> spzMap,
                                            Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                            EquityInfo001Dto equityInfo001Dto) {
        List<ChbTransformRuleObjDto> objFileds=null;
        if(objFiledsMap!=null){
            objFileds=objFiledsMap.get("indentifyMark");
        }
        if (EquityInfoCheckUtil.isEmpty(indentifyMark)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_001", "识别标识"));
        }
        else if (EquityInfoCheckUtil.findPatern(PATTERN_IS_CHINA, indentifyMark)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_012", "识别标识"));
        }
//        else if (EquityInfoCheckUtil.isExceedLength(indentifyMark, 30)) {
        else if (EquityInfoCheckUtil.checkByteLength(indentifyMark, 30)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_010", "识别标识", "30个字节"));
        } else if (StringUtil.contains(indentifyMark, "'")) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_012", "识别标识"));
        }
        // add by etliu 20180912 start
        else if (EquityInfoCheckUtil.whiteListCheck(indentifyMark,objFileds,spzMap,equityInfo001Dto)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_035", "识别标识"));
        }
        // add by etliu 20180912 end
        return message;
    }

    /**
     * 登记银行编码验证
     *
     * @param message 检查消息
     * @param bankCode 登记银行编码
     * @return message 检查消息
     * @see
     * @since 1.0
     */
    public List<String> checkBankCode(List<String> message, String bankCode,
                                      Map<String,TransformRule001Dto> spzMap,
                                      Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                      EquityInfo001Dto equityInfo001Dto) {
        List<ChbTransformRuleObjDto> objFileds=null;
        if(objFiledsMap!=null){
            objFileds=objFiledsMap.get("bankCode");
        }
        if (EquityInfoCheckUtil.isEmpty(bankCode)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_001", "登记银行代码"));
        }
        else if (bankCode.length() != 6) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_002", "登记银行代码", "6"));
        }
        else if (EquityInfoCheckUtil.findPatern(PATTERN_REGIST_BANK_CODE_NEW, bankCode)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_003", "登记银行代码"));
        }
        // add by etliu 20180912 start
        else if (EquityInfoCheckUtil.whiteListCheck( bankCode,objFileds,spzMap,equityInfo001Dto)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_035", "登记银行代码"));
        }
        // add by etliu 20180912 end
        return message;
    }
    /**
     * 登记银行编码验证(新规则)
     *
     * @param message 检查消息
     * @param bankCode 登记银行编码
     * @return message 检查消息
     * @see
     * @since 1.0
     */
    public List<String> checkBankCodeNew(List<String> message, String bankCode,
                                         Map<String,TransformRule001Dto> spzMap,
                                         Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                         EquityInfo001Dto equityInfo001Dto) {
        List<ChbTransformRuleObjDto> objFileds=null;
        if(objFiledsMap!=null){
            objFileds=objFiledsMap.get("bankCode");
        }
        if (EquityInfoCheckUtil.isEmpty(bankCode)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_001", "登记银行代码"));
        }
        else if (bankCode.length() != 6) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_002", "登记银行代码", "6"));
        }
        else if (EquityInfoCheckUtil.findPatern(PATTERN_REGIST_BANK_CODE_NEW, bankCode)) {
            message.add(this.getMessage("CHB_EQUITYINFO_P_036", "登记银行代码"));
        }
        // add by etliu 20180912 start
        else if (EquityInfoCheckUtil.whiteListCheck( bankCode,objFileds,spzMap,equityInfo001Dto)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_035", "登记银行代码"));
        }
        // add by etliu 20180912 end
        return message;
    }


    /**
     * 总行机构代码验证
     *
     * @param message 检查消息
     * @return message 检查消息
     * @see
     * @since 1.0
     */
    public List<String> checkPbcBankCode(List<String> message, String pbcBankCode) {
        if (EquityInfoCheckUtil.isEmpty(pbcBankCode)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_001", "总行机构代码"));
        }
        else if (pbcBankCode.length() != 14) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_002", "总行机构代码", "14"));
        }
        else if (EquityInfoCheckUtil.findPatern(PATTERN_PBC_BANK_CODE_NEW, pbcBankCode)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_037", "总行机构代码"));
        }
        return message;
    }

    /**
     * 当识别标识存在时，需要判断识别标识在维护表中不存在有效或待处理的数据
     * @param message
     * @param indentifyMark 识别 标识
     * @return
     */
    public List<String> checkExistsIndentifyMark(List<String> message,String indentifyMark){
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("IndentifyMark", indentifyMark);
        int existCount = 0;
        try {
            existCount = equityInfoRegist001Mapper.selectExistsByIndentifyMark(param);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        if(existCount != 0){
            message.add(this.getMessage("CHB_EQUITY_CHECKINDENTIFYMARK"));
        }
        return message;
    }

    /**
     * 获取数据类型 <一句话功能简述> <功能详细描述>
     *
     * @param equityInfo001Dto 接口数据
     * @param equityRegist
     * @return
     * @see
     * @since 1.0
     */
    private String getTypeByDto(EquityInfo001Dto equityInfo001Dto,
                                EquityInfoRegist001Dto equityRegist) {
        String type = "";
        // 当登记表中无数据，则数据味蕾为新建状态
        if (null == equityRegist) {
            type = "01";
        }
        // 当投资者原识别标识不能为空，并且原识别标识与识别标识不相等
        else if (!EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getOrgIndentifyMark())
                && !equityRegist.getIndentifyMark().equals(
                equityInfo001Dto.getOrgIndentifyMark())) {
            type = "02";
        }
        // 投资者名称”、“证件类型”、“证件号码”改变为03
        else if (!equityRegist.getHolderName().equals(equityInfo001Dto.getHolderName())
                || !equityRegist.getPersonalIdentifyType().equals(
                equityInfo001Dto.getPersonalIdentifyType())
                || !equityRegist.getInstitutionIdentifyType().equals(
                equityInfo001Dto.getInstitutionIdentifyType())
                || !equityRegist.getIdentifyNumber().equals(
                equityInfo001Dto.getIdentifyNumber())) {
            type = "03";
        }
        else if (!equityRegist.getHolderRiskPrefer().equals(equityInfo001Dto.getHolderRiskPrefer())
                || !equityRegist.getHolderCellphone().equals(
                equityInfo001Dto.getHolderCellphone())) {
            type = "04";
        }
        else {
            type = "01";
        }
        return type;
    }

    /**
     * 与已经报送的数据进行对比，返回变更的数据
     *
     * @param equityInfo001Dto 单个dto
     * @param registList 报送信息
     * @param map 存储的值
     * @param flag 是否接口报送
     * @see
     * @since 1.0
     */
    private void getEditInterf(EquityInfo001Dto equityInfo001Dto,
                               List<EquityInfoRegist001Dto> registList, Map<String, String> map,
                               boolean flag) {
        List<EquityInfoRegist001Dto> tmpList = registList.stream().filter(item -> {
            return StringUtil.equals(equityInfo001Dto.getBankCode() + GlobalConstant.UNDER_LINE
                            + equityInfo001Dto.getIndentifyMark(),
                    item.getBankCode() + GlobalConstant.UNDER_LINE + item.getIndentifyMark());
        }).collect(Collectors.toList());
        EquityInfoRegist001Dto equityRegist = null;
        if (tmpList != null && tmpList.size() > 0) {
            equityRegist = tmpList.get(0);
        }
        if (StringUtil.isNotEmpty(equityInfo001Dto.getOrgIndentifyMark())
                && !equityInfo001Dto.getIndentifyMark().equals(
                equityInfo001Dto.getOrgIndentifyMark())) {
            if (equityRegist == null||DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())) {
                // 若通过识别标识查不到已报送数据，并且为识别标识变更，则通过原识别标识查询

//                equityRegist = equityInfoRegist001Mapper.queryBatchForEquityInfoSingle(
//                    equityInfo001Dto.getBankCode(), equityInfo001Dto.getOrgIndentifyMark());
                map.put("type", "02");
                map.put("REMARK", map.get("REMARK"));
                //获取通过原识别标识获得最已报送数据，并过滤删除的信息
                List<EquityInfoRegist001Dto> submitList = new ArrayList<EquityInfoRegist001Dto>();
                getIsSubmitList(equityInfo001Dto.getBankCode(), equityInfo001Dto.getOrgIndentifyMark(),submitList);

                if (CollectionUtils.isNotEmpty(submitList)&&submitList.size()==1
                        &&!DictCodeListExt2.报送数据状态_删除.getValue().equals(submitList.get(0).getStatus())) {
                    equityRegist = submitList.get(0);
                    this.getDiffMark(equityInfo001Dto, equityRegist, map);
                }
                else {
                    map.put("type02", "02");
                }

            }
            else if(!DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())&&
                    StringUtils.isEmpty(equityRegist.getOrgIndentifyMark())){
                map.put("type02", "03");
            }
            else if(!DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())){
                // EquityInfoRegistCommonUtil.formatDto(equityRegist, StringUtil.SPACE);
                this.getDiffDto(equityInfo001Dto, equityRegist, map, flag);
                // EquityInfoRegistCommonUtil.formatDto(equityRegist, StringUtil.SPACE);
            }
            else{
                map.put("type02", "02");
            }
        }
        else {
            if (equityRegist == null||
                    DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())) {
                map.put("type", "01");
            }
            else {
                // EquityInfoRegistCommonUtil.formatDto(equityRegist, StringUtil.SPACE);
                this.getDiffDto(equityInfo001Dto, equityRegist, map , flag);
                // EquityInfoRegistCommonUtil.formatDto(equityRegist, StringUtil.SPACE);
            }
        }
    }

    /**
     * 与已经报送的数据进行对比，返回变更的数据
     * type 说明   01 新增/02 识别标识变更/03 重要信息变更/04 其他信息变更
     * @param list excel 解析数据
     * @param map 结果map
     * @see
     * @since 1.0
     */
    private void getEditMap(List<String> list, Map<String, String> map, List<Map<String,EquityInfoRegist001Dto>> bankCodeMarkList) {
//        EquityInfoRegist001Mapper equityInfoRegist001Mapper1 = SpringContextUtil.getBean(
//            EquityInfoRegist001Mapper.class);
        //根据识别标识查询登记表的数据
//        EquityInfoRegist001Dto equityRegist = equityInfoRegist001Mapper1.queryForEquityInfoRegist(
//            list.get(0), list.get(1));
        EquityInfoRegist001Dto equityRegist = null;

        try {
            if (StringUtil.isNotBlank(list.get(1)) && list.get(1).length() <= 30) {
//                equityRegist = equityInfoRegist001Mapper.queryBatchForEquityInfoSingle(list.get(0),
//                        list.get(1));
                // 优化 避免多次查询数据库
                if (bankCodeMarkList != null && bankCodeMarkList.size() >0) {
                    equityRegist = bankCodeMarkList.get(0).get((list.get(0) + "_" + list.get(1)));
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }

        //原识别标识不为空，且与现识别标识不相同
        logger.info("equityRegist"+list.get(0));
        if (StringUtil.isNotEmpty(list.get(2)) && !list.get(1).equals(list.get(2))) {
            if (equityRegist == null||DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())) {
                //识别标识变更，
                map.put("type", "02");
                map.put("REMARK", map.get("REMARK"));
                // 若为空，则检查原识别标识是否已报送过
//                equityRegist = equityInfoRegist001Mapper1.queryForEquityInfoRegist(list.get(0),
//                    list.get(2));
//                equityRegist = equityInfoRegist001Mapper.queryBatchForEquityInfoSingle(list.get(0),
//                		list.get(2));
//                List<EquityInfoRegist001Dto> submitList = equityInfoRegist001Mapper.queryEquityInfoByOrg(
//                		list.get(0), list.get(2));
                //通过原识别标识获取已报送数据处理
                List<EquityInfoRegist001Dto> submitList = new ArrayList<EquityInfoRegist001Dto>();
                getIsSubmitList(list.get(0), list.get(2),submitList);

                if (CollectionUtils.isNotEmpty(submitList)&&submitList.size()==1&&
                        !DictCodeListExt2.报送数据状态_删除.getValue().equals(submitList.get(0).getStatus())) {
                    equityRegist = submitList.get(0);
//                    EquityInfoRegistCommonUtil.formatDto(equityRegist, StringUtil.SPACE);
                    EquityInfoRegistCommonUtil.formatDtoAll(equityRegist);
                    this.getDiffMapMark(list, equityRegist, map);
                }
                else {
                    map.put("type02", "02");
                }
                map.put("REMARK", list.get(21));
            }
            else if(!DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())&&
                    StringUtils.isEmpty(equityRegist.getOrgIndentifyMark())){
                map.put("type02", "03");
            }
            else if(!DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())){
//                EquityInfoRegistCommonUtil.formatDto(equityRegist, StringUtil.SPACE);
                EquityInfoRegistCommonUtil.formatDtoAll(equityRegist);
                this.getDiffMap(list, equityRegist, map);
            }
            else{
                map.put("type02", "02");
            }
        }
        else {
            //数据为新建模式
            if (equityRegist == null||DictCodeListExt2.报送数据状态_删除.getValue().equals(equityRegist.getStatus())) {
               //type1=01,后续判断会认为是一条新接入的数据。
                map.put("type", "01");
            }
            else {
//                EquityInfoRegistCommonUtil.formatDto(equityRegist, StringUtil.SPACE);
                //转换实体类中的数据字典
                EquityInfoRegistCommonUtil.formatDtoAll(equityRegist);
                //与已报送数据对比
                this.getDiffMap(list, equityRegist, map);
            }
        }
    }

    /**
     * 当数据类型为识别标识变更时，对比其他数据是否有改变
     *
     * @param equityInfo001Dto dto
     * @param equityInfoRegist001Dto 登记表中数据
     * @param map 返回的map值
     * @see
     * @since 1.0
     */
    private void getDiffMark(EquityInfo001Dto equityInfo001Dto,
                             EquityInfoRegist001Dto equityInfoRegist001Dto,
                             Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        // 投资者是否属于本行比较
        if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getIsBelongBank(),
                equityInfoRegist001Dto.getIsBelongBank())) {
            map.put("IS_BELONG_BANK", equityInfo001Dto.getIsBelongBank());
            sb.append("01");
        }
        // 投资者所属银行名称
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getTheBankName(),
                equityInfoRegist001Dto.getTheBankName())) {
            map.put("THE_BANK_NAME", equityInfo001Dto.getTheBankName());
            sb.append("01");
        }
        // 投资者所属银行代码
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getTheBankCode(),
                equityInfoRegist001Dto.getTheBankCode())) {
            map.put("THE_BANK_CODE", equityInfo001Dto.getTheBankCode());
            sb.append("01");
        }
        // 投资者境内外标识
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderMark(),
                equityInfoRegist001Dto.getHolderMark())) {
            map.put("HOLDER_MARK", equityInfo001Dto.getHolderMark());
            sb.append("01");
        }

        // 投资者所属国家或地区
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderRegion(),
                equityInfoRegist001Dto.getHolderRegion())) {
            map.put("HOLDER_REGION", equityInfo001Dto.getHolderRegion());
            sb.append("01");
        }
        // 投资者类别
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderType(),
                equityInfoRegist001Dto.getHolderType())) {
            map.put("HOLDER_TYPE", equityInfo001Dto.getHolderType());
            sb.append("01");
        }
//        // 投资者分类
//        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderClassify(),
//                equityInfoRegist001Dto.getHolderClassify())) {
//            map.put("HOLDER_CLASSIFY", equityInfo001Dto.getHolderClassify());
//            sb.append("01");
//        }
//        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getFundAccPlace(),
//                equityInfoRegist001Dto.getFundAccPlace())) {
//            map.put("FUND_ACC_PLACE", equityInfo001Dto.getFundAccPlace());
//            sb.append("01");
//        }
        // 个人证件类别
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getPersonalIdentifyType(),
                equityInfoRegist001Dto.getPersonalIdentifyType())) {
            map.put("PERSONAL_IDENTIFY_TYPE", equityInfo001Dto.getPersonalIdentifyType());
            sb.append("01");
        }

        // 机构证件类别
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getInstitutionIdentifyType(),
                equityInfoRegist001Dto.getInstitutionIdentifyType())) {
            map.put("INSTITUTION_IDENTIFY_TYPE", equityInfo001Dto.getInstitutionIdentifyType());
            sb.append("01");
        }
        // 其他证件名称
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getOtherIdentifyName(),
                equityInfoRegist001Dto.getOtherIdentifyName())) {
            map.put("OTHER_IDENTIFY_NAME", equityInfo001Dto.getOtherIdentifyName());
            sb.append("01");
        }
        // 证件号码
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getIdentifyNumber(),
                equityInfoRegist001Dto.getIdentifyNumber())) {
            map.put("IDENTIFY_NUMBER", equityInfo001Dto.getIdentifyNumber());
            sb.append("01");
        }
        // SPV资金托管账户开户行
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getSpvCapAccBank(),
                equityInfoRegist001Dto.getSpvCapAccBank())) {
            map.put("SPV_CAP_ACC_BANK", equityInfo001Dto.getSpvCapAccBank());
            sb.append("01");
        }
        // 其他资金托管账户开户行
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getOtherCapAccBank(),
                equityInfoRegist001Dto.getOtherCapAccBank())) {
            map.put("OTHER_CAP_ACC_BANK", equityInfo001Dto.getOtherCapAccBank());
            sb.append("01");
        }
        // SPV登记编码
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getSpvRegistCode(),
                equityInfoRegist001Dto.getSpvRegistCode())) {
            map.put("SPV_REGIST_CODE", equityInfo001Dto.getSpvRegistCode());
            sb.append("01");
        }
        // 投资者名称
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderName(),
                equityInfoRegist001Dto.getHolderName())) {
            map.put("HOLDER_NAME", equityInfo001Dto.getHolderName());
            sb.append("01");
        }
        // 性别
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderSex(),
                equityInfoRegist001Dto.getHolderSex())) {
            map.put("HOLDER_SEX", equityInfo001Dto.getHolderSex());
            sb.append("01");
        }

        // 风险偏好
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderRiskPrefer(),
                equityInfoRegist001Dto.getHolderRiskPrefer())) {
            map.put("HOLDER_RISK_PREFER", equityInfo001Dto.getHolderRiskPrefer());
            sb.append("01");
        }
        // 手机号码
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderCellphone(),
                equityInfoRegist001Dto.getHolderCellphone())) {
            map.put("HOLDER_CELLPHONE", equityInfo001Dto.getHolderCellphone());
            sb.append("01");
        }
        // 固定电话
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderTelephone(),
                equityInfoRegist001Dto.getHolderTelephone())) {
            map.put("HOLDER_TELEPHONE", equityInfo001Dto.getHolderTelephone());
            sb.append("01");
        }
        // 电子邮箱
        else if (!CommonUtil.equalsIgnoreNull(equityInfo001Dto.getHolderEmail(),
                equityInfoRegist001Dto.getHolderEmail())) {
            map.put("HOLDER_EMAIL", equityInfo001Dto.getHolderEmail());
            sb.append("01");
        }
        if (StringUtil.isNotEmpty(sb)) {
            map.put("type02", sb.toString());
        }
    }

    /**
     * 上游接口与报送信息对比
     *
     * @param equityInfo001Dto dto
     * @param equityInfoRegist001Dto 登记表中数据
     * @param map 返回的map值
     * @param flag 是否接口数据
     * @see
     * @since 1.0
     */
    private void getDiffDto(EquityInfo001Dto equityInfo001Dto,
                            EquityInfoRegist001Dto equityInfoRegist001Dto,
                            Map<String, String> map ,boolean flag) {
    	String updateFlg = SvrDataUtil.getSysParam("SVR_EQU_REPAIR_BATCH_FLAG");
		    	
        // 投资者是否属于本行比较
        String type = "03";
        if (StringUtil.isNotEmpty(equityInfo001Dto.getIsBelongBank())
                && !equityInfo001Dto.getIsBelongBank().equals(
                equityInfoRegist001Dto.getIsBelongBank())) {
            map.put("IS_BELONG_BANK", equityInfo001Dto.getIsBelongBank());
        }
        // 投资者所属银行名称
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getTheBankName())
                && !equityInfo001Dto.getTheBankName().equals(
                equityInfoRegist001Dto.getTheBankName())) {
            map.put("THE_BANK_NAME", equityInfo001Dto.getTheBankName());
        }
        // 投资者所属银行代码
        else if (!"01".equals(updateFlg) && StringUtil.isNotEmpty(equityInfo001Dto.getTheBankCode())
                && !equityInfo001Dto.getTheBankCode().equals(
                equityInfoRegist001Dto.getTheBankCode())) {        	
    		map.put("THE_BANK_CODE", equityInfo001Dto.getTheBankCode());
        }
        // 投资者境内外标识
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderMark())
                && !equityInfo001Dto.getHolderMark().equals(
                equityInfoRegist001Dto.getHolderMark())) {
            map.put("HOLDER_MARK", equityInfo001Dto.getHolderMark());
        }

        // 投资者所属国家或地区
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderRegion())
                && !equityInfo001Dto.getHolderRegion().equals(
                equityInfoRegist001Dto.getHolderRegion())) {
            map.put("HOLDER_REGION", equityInfo001Dto.getHolderRegion());
        }
        // 投资者类别
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderType())
                && !equityInfo001Dto.getHolderType().equals(
                equityInfoRegist001Dto.getHolderType())) {
            map.put("HOLDER_TYPE", equityInfo001Dto.getHolderType());
        }
        // 个人证件类别
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getPersonalIdentifyType())
                && !equityInfo001Dto.getPersonalIdentifyType().equals(
                equityInfoRegist001Dto.getPersonalIdentifyType())) {
            map.put("PERSONAL_IDENTIFY_TYPE", equityInfo001Dto.getPersonalIdentifyType());
        }

        // 机构证件类别
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getInstitutionIdentifyType())
                && !equityInfo001Dto.getInstitutionIdentifyType().equals(
                equityInfoRegist001Dto.getInstitutionIdentifyType())) {
            map.put("INSTITUTION_IDENTIFY_TYPE", equityInfo001Dto.getInstitutionIdentifyType());
        }
        // 其他证件名称
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getOtherIdentifyName())
                && !equityInfo001Dto.getOtherIdentifyName().equals(
                equityInfoRegist001Dto.getOtherIdentifyName())) {
            map.put("OTHER_IDENTIFY_NAME", equityInfo001Dto.getOtherIdentifyName());
        }
        // 证件号码
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getIdentifyNumber())
                && !equityInfo001Dto.getIdentifyNumber().equals(
                equityInfoRegist001Dto.getIdentifyNumber())) {
            map.put("IDENTIFY_NUMBER", equityInfo001Dto.getIdentifyNumber());
        }
        // SPV资金托管账户开户行
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getSpvCapAccBank())
                && !equityInfo001Dto.getSpvCapAccBank().equals(
                equityInfoRegist001Dto.getSpvCapAccBank())) {
            map.put("SPV_CAP_ACC_BANK", equityInfo001Dto.getSpvCapAccBank());
        }
        // 其他资金托管账户开户行
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getTheBankName())
                && !equityInfo001Dto.getTheBankName().equals(
                equityInfoRegist001Dto.getTheBankName())) {
            map.put("OTHER_CAP_ACC_BANK", equityInfo001Dto.getOtherCapAccBank());
        }
        // SPV登记编码
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getSpvRegistCode())
                && !equityInfo001Dto.getSpvRegistCode().equals(
                equityInfoRegist001Dto.getSpvRegistCode())) {
            map.put("SPV_REGIST_CODE", equityInfo001Dto.getSpvRegistCode());
        }
        // 投资者名称
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderName())
                && !equityInfo001Dto.getHolderName().equals(
                equityInfoRegist001Dto.getHolderName())) {
            map.put("HOLDER_NAME", equityInfo001Dto.getHolderName());
        }
        // 性别
        else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderSex())
                && !equityInfo001Dto.getHolderSex().equals(
                equityInfoRegist001Dto.getHolderSex())) {
            map.put("HOLDER_SEX", equityInfo001Dto.getHolderSex());
        }
        else {
        	//若固话不合规，则赋默认值对比
        	this.getDefaultTelphone(equityInfo001Dto,flag);
            // 备注
            if (StringUtil.isNotEmpty(equityInfo001Dto.getRemark())
                    && !equityInfo001Dto.getRemark().equals(equityInfoRegist001Dto.getRemark())) {
                map.put("REMARK", equityInfo001Dto.getRemark());
            } 
                // 风险偏好
            else if (!equityInfo001Dto.getHolderRiskPrefer().equals(
                        equityInfoRegist001Dto.getHolderRiskPrefer())) {
                    map.put("HOLDER_RISK_PREFER", equityInfo001Dto.getHolderRiskPrefer());
                    type = "04";
                }
                // 手机号码
            else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderCellphone())
                        && !equityInfo001Dto.getHolderCellphone().equals(
                        equityInfoRegist001Dto.getHolderCellphone())) {
                    map.put("HOLDER_CELLPHONE", equityInfo001Dto.getHolderCellphone());
                    type = "04";
                }
                // 固定电话
            else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderTelephone())
                        && !equityInfo001Dto.getHolderTelephone().equals(
                        equityInfoRegist001Dto.getHolderTelephone())) {
                    map.put("HOLDER_TELEPHONE", equityInfo001Dto.getHolderTelephone());
                    type = "04";
                }
                // 电子邮箱
            else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderEmail())
                        && !equityInfo001Dto.getHolderEmail().equals(
                        equityInfoRegist001Dto.getHolderEmail())) {
                    map.put("HOLDER_EMAIL", equityInfo001Dto.getHolderEmail());
                    type = "04";
                }
                // 电子邮箱
            else if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderEmail())
                        && !equityInfo001Dto.getHolderEmail().equals(
                        equityInfoRegist001Dto.getHolderEmail())) {
                    map.put("HOLDER_EMAIL", equityInfo001Dto.getHolderEmail());
                    type = "04";
                                           
            } else {
            	// 投资者分类 - 仅供四期使用字段
            	if (StringUtil.isNotEmpty(equityInfo001Dto.getHolderClassify())
                        && !equityInfo001Dto.getHolderClassify().equals(
                        equityInfoRegist001Dto.getHolderClassify())) {
                    map.put("HOLDER_CLASSIFY", equityInfo001Dto.getHolderClassify());
                    type = "05";
                }
                // 开户行所在地- 仅供四期使用字段
                if (StringUtil.isNotEmpty(equityInfo001Dto.getFundAccPlace())
                        && !equityInfo001Dto.getFundAccPlace().equals(
                        equityInfoRegist001Dto.getFundAccPlace())) {
                    map.put("FUND_ACC_PLACE", equityInfo001Dto.getFundAccPlace());
                    type = "05";
                }
            } 
        }
        if (map.isEmpty()) {
            map.put("flag", "01");
            type = "01";
        }
        map.put("type", type);
    }

    private void getDefaultTelphone(EquityInfo001Dto equityInfo001Dto, boolean isInterf){
        if (!StringUtil.equals(equityInfo001Dto.getHolderTelephone(), ReportGlobalConstant.DEFAULTTEL)
                &&!EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getHolderTelephone())
                && EquityInfoCheckUtil.findPatern(PATTERN_OFFICE_PHONE, equityInfo001Dto.getHolderTelephone())
            // && EquityInfoCheckUtil.findPatern(PATTERN_NO_TEPHONE, holderTelephone)
            /*&& !"无".contains(holderTelephone)*/) {
            //符合要求标志
            boolean flag = false;
            if(equityInfo001Dto.getHolderTelephone().length()<7 || equityInfo001Dto.getHolderTelephone().length()>30){
                flag = true;
            }
            else if(equityInfo001Dto.getHolderTelephone().contains("+") && !equityInfo001Dto.getHolderTelephone().startsWith("+")){
                flag = true;
            }
            else if(equityInfo001Dto.getHolderTelephone().endsWith("-")){
                flag = true;
            }
            else{
                int countNum = 0;
                for (int i=0;i<equityInfo001Dto.getHolderTelephone().length();i++) {
                    if(Pattern.compile("^[0-9]$").matcher(String.valueOf(equityInfo001Dto.getHolderTelephone().charAt(i))).matches()){
                        countNum++;
                    }
                }
                if(countNum<equityInfo001Dto.getHolderTelephone().length()){
                    flag = true;
                }
            }
            if(StringUtils.equals(DictCodeList.是否_中债_是.getValue(), SvrDataUtil.getSysParam(
                    ReportGlobalConstant.IS_DEFAUL_TELPHONE))&&flag&&isInterf){
                equityInfo001Dto.setHolderTelephone(ReportGlobalConstant.DEFAULTTEL);
            }
        }
    }
    // /**
    // * excel导入与报送信息对比
    // *
    // * @param list excel解析后的list
    // * @param equityInfoRegist001Dto 登记表中数据
    // * @param map 改变的数据
    // * @param type 数据类型
    // * @see
    // * @since 1.0
    // */
    // private void getDiffMap(List<String> list, EquityInfoRegist001Dto equityInfoRegist001Dto,
    // Map<String, String> map) {
    // // 投资者是否属于本行比较
    // String type = "03";
    // if (StringUtil.isNotEmpty(list.get(3))
    // && !list.get(3).equals(equityInfoRegist001Dto.getIsBelongBank())) {
    // map.put("IS_BELONG_BANK", list.get(3));
    // }
    // // 投资者所属银行名称
    // else if (StringUtil.isNotEmpty(list.get(4))
    // && !list.get(4).equals(equityInfoRegist001Dto.getTheBankName())) {
    // map.put("THE_BANK_NAME", list.get(4));
    // }
    // // 投资者所属银行代码
    // else if (StringUtil.isNotEmpty(list.get(5))
    // && !list.get(5).equals(equityInfoRegist001Dto.getTheBankCode())) {
    // map.put("THE_BANK_CODE", list.get(5));
    // }
    // // 投资者境内外标识
    // else if (StringUtil.isNotEmpty(list.get(6))
    // && !list.get(6).equals(equityInfoRegist001Dto.getHolderMark())) {
    // map.put("HOLDER_MARK", list.get(6));
    // }
    //
    // // 投资者所属国家或地区
    // else if (StringUtil.isNotEmpty(list.get(7))
    // && !list.get(7).equals(equityInfoRegist001Dto.getHolderRegion())) {
    // map.put("HOLDER_REGION", list.get(7));
    // }
    // // 投资者类别
    // else if (StringUtil.isNotEmpty(list.get(8))
    // && !list.get(8).equals(equityInfoRegist001Dto.getHolderType())) {
    // map.put("HOLDER_TYPE", list.get(8));
    // }
    // // 个人证件类别
    // else if (StringUtil.isNotEmpty(list.get(9))
    // && !list.get(9).equals(equityInfoRegist001Dto.getPersonalIdentifyType())) {
    // map.put("PERSONAL_IDENTIFY_TYPE", list.get(9));
    // }
    //
    // // 机构证件类别
    // else if (StringUtil.isNotEmpty(list.get(10))
    // && !list.get(10).equals(equityInfoRegist001Dto.getInstitutionIdentifyType())) {
    // map.put("INSTITUTION_IDENTIFY_TYPE", list.get(10));
    // }
    // // 其他证件名称
    // else if (StringUtil.isNotEmpty(list.get(11))
    // && !list.get(11).equals(equityInfoRegist001Dto.getOtherIdentifyName())) {
    // map.put("OTHER_IDENTIFY_NAME", list.get(11));
    // }
    // // 证件号码
    // else if (StringUtil.isNotEmpty(list.get(12))
    // && !list.get(12).equals(equityInfoRegist001Dto.getIdentifyNumber())) {
    // map.put("IDENTIFY_NUMBER", list.get(12));
    // }
    // // SPV资金托管账户开户行
    // else if (StringUtil.isNotEmpty(list.get(13))
    // && !list.get(13).equals(equityInfoRegist001Dto.getSpvCapAccBank())) {
    // map.put("SPV_CAP_ACC_BANK", list.get(13));
    // }
    // // 其他资金托管账户开户行
    // else if (StringUtil.isNotEmpty(list.get(14))
    // && !list.get(14).equals(equityInfoRegist001Dto.getTheBankName())) {
    // map.put("OTHER_CAP_ACC_BANK", list.get(14));
    // }
    // // SPV登记编码
    // else if (StringUtil.isNotEmpty(list.get(15))
    // && !list.get(15).equals(equityInfoRegist001Dto.getSpvRegistCode())) {
    // map.put("SPV_REGIST_CODE", list.get(15));
    // }
    // // 投资者名称
    // else if (StringUtil.isNotEmpty(list.get(16))
    // && !list.get(16).equals(equityInfoRegist001Dto.getHolderName())) {
    // map.put("HOLDER_NAME", list.get(16));
    // }
    // // 性别
    // else if (StringUtil.isNotEmpty(list.get(17))
    // && !list.get(17).equals(equityInfoRegist001Dto.getHolderSex())) {
    // map.put("HOLDER_SEX", list.get(17));
    // }
    // else {
    // // 备注
    // if (StringUtil.isNotEmpty(list.get(22))
    // && !list.get(22).equals(equityInfoRegist001Dto.getRemark())) {
    // map.put("REMARK", list.get(22));
    // }
    // else {
    // // 风险偏好
    // if (StringUtil.isNotEmpty(list.get(18))
    // && !list.get(18).equals(equityInfoRegist001Dto.getHolderRiskPrefer())) {
    // map.put("HOLDER_RISK_PREFER", list.get(18));
    // type = "04";
    // }
    // // 手机号码
    // if (StringUtil.isNotEmpty(list.get(19))
    // && !list.get(19).equals(equityInfoRegist001Dto.getHolderCellphone())) {
    // map.put("HOLDER_CELLPHONE", list.get(19));
    // type = "04";
    // }
    // // 固定电话
    // if (StringUtil.isNotEmpty(list.get(20))
    // && !list.get(20).equals(equityInfoRegist001Dto.getHolderTelephone())) {
    // map.put("HOLDER_TELEPHONE", list.get(20));
    // type = "04";
    // }
    // // 电子邮箱
    // if (StringUtil.isNotEmpty(list.get(21))
    // && !list.get(21).equals(equityInfoRegist001Dto.getHolderEmail())) {
    // map.put("HOLDER_EMAIL", list.get(21));
    // type = "04";
    // }
    // }
    // }
    // if (map.isEmpty()) {
    // map.put("flag", "01");
    // type = "01";
    // }
    // map.put("type", type);
    // }
    /**
     * excel导入与报送信息对比
     *
     * @param list excel解析后的list
     * @param equityInfoRegist001Dto 登记表中数据
     * @param map 改变的数据
     * @see
     * @since 1.0
     */
    private void getDiffMapMark(List<String> list, EquityInfoRegist001Dto equityInfoRegist001Dto,
                                Map<String, String> map) {
        // 投资者是否属于本行比较
        StringBuffer sb = new StringBuffer();
        if (!CommonUtil.equalsIgnoreNull(list.get(3), equityInfoRegist001Dto.getIsBelongBank())) {
            map.put("IS_BELONG_BANK", list.get(3));
            sb.append("01");
        }
        // 投资者所属银行名称
        else if (!CommonUtil.equalsIgnoreNull(list.get(4),
                equityInfoRegist001Dto.getTheBankName())) {
            map.put("THE_BANK_NAME", list.get(4));
            sb.append("01");
        }
        // 投资者所属银行代码
        else if (!CommonUtil.equalsIgnoreNull(list.get(5),
                equityInfoRegist001Dto.getTheBankCode())) {
            map.put("THE_BANK_CODE", list.get(5));
            sb.append("01");
        }
        // 投资者境内外标识
        else if (!CommonUtil.equalsIgnoreNull(list.get(6),
                equityInfoRegist001Dto.getHolderMark())) {
            map.put("HOLDER_MARK", list.get(6));
            sb.append("01");
        }

        // 投资者所属国家或地区
        else if (!CommonUtil.equalsIgnoreNull(list.get(7),
                equityInfoRegist001Dto.getHolderRegion())) {
            map.put("HOLDER_REGION", list.get(7));
            sb.append("01");
        }
        // 投资者类别
        else if (!CommonUtil.equalsIgnoreNull(list.get(8),
                equityInfoRegist001Dto.getHolderType())) {
            map.put("HOLDER_TYPE", list.get(8));
            sb.append("01");
        }
        // 个人证件类别
        else if (!CommonUtil.equalsIgnoreNull(list.get(9),
                equityInfoRegist001Dto.getPersonalIdentifyType())) {
            map.put("PERSONAL_IDENTIFY_TYPE", list.get(9));
            sb.append("01");
        }

        // 机构证件类别
        else if (!CommonUtil.equalsIgnoreNull(list.get(10),
                equityInfoRegist001Dto.getInstitutionIdentifyType())) {
            map.put("INSTITUTION_IDENTIFY_TYPE", list.get(10));
            sb.append("01");
        }
        // 其他证件名称
        else if (!CommonUtil.equalsIgnoreNull(list.get(11),
                equityInfoRegist001Dto.getOtherIdentifyName())) {
            map.put("OTHER_IDENTIFY_NAME", list.get(11));
            sb.append("01");
        }
        // 证件号码
        else if (!CommonUtil.equalsIgnoreNull(list.get(12),
                equityInfoRegist001Dto.getIdentifyNumber())) {
            map.put("IDENTIFY_NUMBER", list.get(12));
            sb.append("01");
        }
        // SPV资金托管账户开户行
        else if (!CommonUtil.equalsIgnoreNull(list.get(13),
                equityInfoRegist001Dto.getSpvCapAccBank())) {
            map.put("SPV_CAP_ACC_BANK", list.get(13));
            sb.append("01");
        }
        // 其他资金托管账户开户行
        else if (!CommonUtil.equalsIgnoreNull(list.get(14),
                equityInfoRegist001Dto.getOtherCapAccBank())) {
            map.put("OTHER_CAP_ACC_BANK", list.get(14));
            sb.append("01");
        }
        //删除该字段
        // SPV登记编码
//        else if (!CommonUtil.equalsIgnoreNull(list.get(15),
//            equityInfoRegist001Dto.getSpvRegistCode())) {
//            map.put("SPV_REGIST_CODE", list.get(15));
//            sb.append("01");
//        }
        // 投资者名称
        else if (!CommonUtil.equalsIgnoreNull(list.get(15),
                equityInfoRegist001Dto.getHolderName())) {
            map.put("HOLDER_NAME", list.get(15));
            sb.append("01");
        }
        // 性别
        else if (!CommonUtil.equalsIgnoreNull(list.get(16),
                equityInfoRegist001Dto.getHolderSex())) {
            map.put("HOLDER_SEX", list.get(16));
            sb.append("01");
        }
        // 风险偏好
        else if (!CommonUtil.equalsIgnoreNull(list.get(17),
                equityInfoRegist001Dto.getHolderRiskPrefer())) {
            map.put("HOLDER_RISK_PREFER", list.get(17));
            sb.append("01");
        }
        // 手机号码
        else if (!CommonUtil.equalsIgnoreNull(list.get(18),
                equityInfoRegist001Dto.getHolderCellphone())) {
            map.put("HOLDER_CELLPHONE", list.get(18));
            sb.append("01");
        }
        // 固定电话
        else if (!CommonUtil.equalsIgnoreNull(list.get(19),
                equityInfoRegist001Dto.getHolderTelephone())) {
            map.put("HOLDER_TELEPHONE", list.get(19));
            sb.append("01");
        }
        // 电子邮箱
        else if (!CommonUtil.equalsIgnoreNull(list.get(20),
                equityInfoRegist001Dto.getHolderEmail())) {
            map.put("HOLDER_EMAIL", list.get(20));
            sb.append("01");
        }
//        //投资者分类
//        else if (!CommonUtil.equalsIgnoreNull(list.get(22),
//                equityInfoRegist001Dto.getHolderClassify())) {
//            map.put("HOLDER_CLASSIFY", list.get(22));
//            sb.append("01");
//        }
        //投资者分类
//        else if (!CommonUtil.equalsIgnoreNull(list.get(23),
//                equityInfoRegist001Dto.getFundAccPlace())) {
//            map.put("FUND_ACC_PLACE", list.get(23));
//            sb.append("01");
//        }
        if (StringUtil.isNotEmpty(sb)) {
            map.put("type02", sb.toString());
        }
    }

    /**
     * excel导入与报送信息对比
     *
     * @param list excel解析后的list
     * @param equityInfoRegist001Dto 登记表中数据
     * @param map 改变的数据
     * @see
     * @since 1.0
     */
    private void getDiffMap(List<String> list, EquityInfoRegist001Dto equityInfoRegist001Dto,
                            Map<String, String> map) {
    	
    	String updateFlg = SvrDataUtil.getSysParam("SVR_EQU_REPAIR_BATCH_FLAG");
    	String templateFlag = SvrDataUtil.getSysParam("SVR_EQU_INFO_TEMPLATE_FLAG");
        //需要做变更类型的分析，然后返回变更类型
        //03 重要信息变更   04 其他信息变更
        // 投资者是否属于本行比较
        String type = "03";
        if (!CommonUtil.equalsIgnoreNull(list.get(3), equityInfoRegist001Dto.getIsBelongBank())) {
            map.put("IS_BELONG_BANK", list.get(3));
        }
        // 投资者所属银行名称
        else if (!CommonUtil.equalsIgnoreNull(list.get(4),
                equityInfoRegist001Dto.getTheBankName())) {
            map.put("THE_BANK_NAME", list.get(4));
        }
        // 投资者所属银行代码
        else if (!"01".equals(updateFlg) && !CommonUtil.equalsIgnoreNull(list.get(5),
                equityInfoRegist001Dto.getTheBankCode())) {
        	map.put("THE_BANK_CODE", list.get(5));
        }
        // 投资者境内外标识
        else if (!CommonUtil.equalsIgnoreNull(list.get(6),
                equityInfoRegist001Dto.getHolderMark())) {
            map.put("HOLDER_MARK", list.get(6));
        }

        // 投资者所属国家或地区
        else if (!CommonUtil.equalsIgnoreNull(list.get(7),
                equityInfoRegist001Dto.getHolderRegion())) {
            map.put("HOLDER_REGION", list.get(7));
        }
        // 投资者类别
        else if (!CommonUtil.equalsIgnoreNull(list.get(8),
                equityInfoRegist001Dto.getHolderType())) {
            map.put("HOLDER_TYPE", list.get(8));
        }
        // 个人证件类别
        else if (!CommonUtil.equalsIgnoreNull(list.get(9),
                equityInfoRegist001Dto.getPersonalIdentifyType())) {
            map.put("PERSONAL_IDENTIFY_TYPE", list.get(9));
        }

        // 机构证件类别
        else if (!CommonUtil.equalsIgnoreNull(list.get(10),
                equityInfoRegist001Dto.getInstitutionIdentifyType())) {
            map.put("INSTITUTION_IDENTIFY_TYPE", list.get(10));
        }
        // 其他证件名称
        else if (!CommonUtil.equalsIgnoreNull(list.get(11),
                equityInfoRegist001Dto.getOtherIdentifyName())) {
            map.put("OTHER_IDENTIFY_NAME", list.get(11));
        }
        // 证件号码
        else if (!CommonUtil.equalsIgnoreNull(list.get(12),
                equityInfoRegist001Dto.getIdentifyNumber())) {
            map.put("IDENTIFY_NUMBER", list.get(12));
        }
        // SPV资金托管账户开户行
        else if (!CommonUtil.equalsIgnoreNull(list.get(13),
                equityInfoRegist001Dto.getSpvCapAccBank())) {
            map.put("SPV_CAP_ACC_BANK", list.get(13));
        }
        // 其他资金托管账户开户行
        else if (!CommonUtil.equalsIgnoreNull(list.get(14),
                equityInfoRegist001Dto.getOtherCapAccBank())) {
            map.put("OTHER_CAP_ACC_BANK", list.get(14));
        }
        //edit 删除登记编码字段 其他字段依次前移一位
        // SPV登记编码
//        else if (!CommonUtil.equalsIgnoreNull(list.get(15),
//            equityInfoRegist001Dto.getSpvRegistCode())) {
//            map.put("SPV_REGIST_CODE", list.get(15));
//        }
        // 投资者名称
        else if (!CommonUtil.equalsIgnoreNull(list.get(15),
                equityInfoRegist001Dto.getHolderName())) {
            map.put("HOLDER_NAME", list.get(15));
        }
        // 性别
        else if (list.get(16)!=null&&(!CommonUtil.equalsIgnoreNull(list.get(16).trim(),
                equityInfoRegist001Dto.getHolderSex()))) {
            map.put("HOLDER_SEX", list.get(16));
        }
        // 备注
        else if (!CommonUtil.equalsIgnoreNull(list.get(21), equityInfoRegist001Dto.getRemark())) {
            map.put("REMARK", list.get(21));
        }
//        //投资者分类
//        else if (!CommonUtil.equalsIgnoreNull(list.get(22),
//                equityInfoRegist001Dto.getHolderClassify())) {
//            map.put("HOLDER_CLASSIFY", list.get(22));
//        }
//        //TODO  销售区域
//        else if (!CommonUtil.equalsIgnoreNull(list.get(23),
//                equityInfoRegist001Dto.getFundAccPlace())) {
//            map.put("FUND_ACC_PLACE", list.get(23));
//        }
        else {
            // 风险偏好
            if (!CommonUtil.equalsIgnoreNull(list.get(17),
                    equityInfoRegist001Dto.getHolderRiskPrefer())) {
                map.put("HOLDER_RISK_PREFER", list.get(17));
                type = "04";
            }
            // 手机号码
            else if (!CommonUtil.equalsIgnoreNull(list.get(18),
                    equityInfoRegist001Dto.getHolderCellphone())) {
                map.put("HOLDER_CELLPHONE", list.get(18));
                type = "04";
            }
            // 固定电话
            else if (!CommonUtil.equalsIgnoreNull(list.get(19),
                    equityInfoRegist001Dto.getHolderTelephone())) {
                map.put("HOLDER_TELEPHONE", list.get(19));
                type = "04";
            }
            // 电子邮箱
            else if (!CommonUtil.equalsIgnoreNull(list.get(20),
                    equityInfoRegist001Dto.getHolderEmail())) {
                map.put("HOLDER_EMAIL", list.get(20));
                type = "04";
            }else {
            	if("01".equals(updateFlg)) {
            		// 所属银行代码                    
                    if (StringUtil.isNotEmpty(list.get(5))
                            && !list.get(5).equals(
                            equityInfoRegist001Dto.getTheBankCode())) {
                    	map.put("THE_BANK_CODE", list.get(5));
                        type = "05";
                    }
            	}
        		// 投资者分类 - 仅供四期使用字段
            	if (StringUtil.isNotEmpty(list.get(22))
                        && !list.get(22).equals(
                        equityInfoRegist001Dto.getHolderClassifyStr())) {
                    map.put("HOLDER_CLASSIFY", list.get(22));
                    type = "05";
                }
                // 开户行所在地- 仅供四期使用字段
            	if("02".equals(templateFlag)) {
            		if (StringUtil.isNotEmpty(list.get(26))
                            && !list.get(26).equals(
                            equityInfoRegist001Dto.getFundAccPlace())) {
                        map.put("FUND_ACC_PLACE", list.get(26));
                        type = "05";
                    }
            	}                          	            	
            }
//            if (!StringUtil.equals(type, "04")) {
//                // 备注
//                if (!CommonUtil.equalsIgnoreNull(list.get(21), equityInfoRegist001Dto.getRemark())) {
//                    map.put("REMARK", list.get(21));
//                }
//            }
        }
        //type=01,且flag=01。则认为与已报送数据没有任何变换。不会生成未报送数据。
        if (map.isEmpty()) {
            map.put("flag", "01");
            type = "01";
        }
        map.put("type", type);
    }

    /**
     * 获取数据类型
     *
     * @param list 每行解析后的List
     * @param equityRegist 登记表中已经报送的数据
     * @return 01新增 02识别标识变更03重要信息变更04其他信息变更
     * @see
     * @since 1.0
     */
    public String getType(List<String> list, EquityInfoRegist001Dto equityRegist) {
        String type = "";
        // 当登记表中无数据，则数据类型为新建状态
        if (null == equityRegist) {
            type = "01";
        }
        // 当投资者原识别标识不能为空，并且原始别标识与识别标识不相等
        else if (!EquityInfoCheckUtil.isEmpty(list.get(2))
                && !equityRegist.getIndentifyMark().equals(list.get(2))) {
            type = "02";
        }
        // 投资者名称”、“证件类型”、“证件号码”改变为03
        else if (!equityRegist.getHolderName().equals(list.get(16))
                || (StringUtil.isNotEmpty(equityRegist.getPersonalIdentifyType())
                && !equityRegist.getPersonalIdentifyType().equals(list.get(9)))
                || (StringUtil.isNotEmpty(equityRegist.getInstitutionIdentifyType())
                && !equityRegist.getInstitutionIdentifyType().equals(list.get(10)))
                || !equityRegist.getIdentifyNumber().equals(list.get(12))) {
            type = "03";
        }
        // else if (!equityRegist.getHolderRiskPrefer().equals(list.get(18))
        // || !equityRegist.getHolderCellphone().equals(list.get(19))) {
        // type = "04";
        // }
        else {
            type = "04";
        }
        return type;
    }

    /**
     * 投资者是否归属本行验证
     *
     * @param message 验证消息
     * @param isBelongBank 是否属于本行
     * @param type 数据类型
     * @return message 验证消息
     * @see
     * @since 1.0
     */
    private List<String> checkIsBelongBank(List<String> message, String isBelongBank,
                                           String type,
                                           Map<String,TransformRule001Dto> spzMap,
                                           Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                           EquityInfo001Dto equityInfo001Dto) {
        if ("01".equals(type) || "03".equals(type)) {
            //当“数据类型”选择“新增”和“重要信息变更”时，该要素登记要求由必填变更为选填。
            /*if (EquityInfoCheckUtil.isEmpty(isBelongBank)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "投资者是否属于本行"));
            }
            else */
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("isBelongBank");
            }
            if (StringUtil.isNotEmpty(isBelongBank)&&EquityInfoCheckUtil.isDictCodeExist(DictCode.是否_中债, isBelongBank)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_004", "投资者是否属于本行"));
            }

            // add by etliu 20180912 start
            else if (StringUtil.isNotEmpty(isBelongBank) &&EquityInfoCheckUtil.whiteListCheck(isBelongBank,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者是否属于本行"));
            }
            // add by etliu 20180912 end
        }
        return message;
    }

    /**
     * 投资者境内外标识
     *
     * @param message 验证消息
     * @param type 数据类型
     * @return message 验证消息
     * @see
     * @since 1.0
     */
    private List<String> checkHolderMark(List<String> message, String holderMark, String type,
                                         Map<String,TransformRule001Dto> spzMap,
                                         Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                         EquityInfo001Dto equityInfo001Dto) {
        if ("01".equals(type) || "03".equals(type)) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("holderMark");
            }
            if (EquityInfoCheckUtil.isEmpty(holderMark)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "投资者境内外标识"));
            }
            else if (EquityInfoCheckUtil.isDictCodeExist(DictCode.境内外, holderMark)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_004", "投资者境内外标识"));
            }
            // add by etliu 20180912 start
            else if (EquityInfoCheckUtil.whiteListCheck( holderMark,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者境内外标识"));
            }
            // add by etliu 20180912 end
        }
        return message;
    }

    /**
     * 归属银行代码验证
     *
     * @param message 验证信息
     * @param theBankCode 归属银行代码
     * @param type 数据类型
     * @param isBelongBank 是否属于本行
     * @param bankCode 登记银行代码
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkTheBankCode(List<String> message, String theBankCode, String type,
                                          String isBelongBank, String bankCode,
                                          Map<String,TransformRule001Dto> spzMap,
                                          Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                          EquityInfo001Dto equityInfo001Dto) {
//        String isBelongBankKey = EquityInfoCheckUtil.subDictcode(isBelongBank, StringUtil.SPACE);
    	String isBelongBankStr = null;
    	if(StringUtil.isNotEmpty(isBelongBank) && isBelongBank.length() > 2) {
    		isBelongBankStr = isBelongBank.substring(0,2);
        }
        if ("01".equals(type) || "03".equals(type)) {
            //当“数据类型”选择“新增”和“重要信息变更”时，该要素登记要求由必填变更为选填。
            if(StringUtil.isNotEmpty(theBankCode)){
                List<ChbTransformRuleObjDto> objFileds=null;
                if(objFiledsMap!=null){
                    objFileds=objFiledsMap.get("theBankCode");
                }
                if (theBankCode.length() != 6) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_002", "投资者所属银行代码", "6"));
                }
                else if (EquityInfoCheckUtil.findPatern(PATTERN_REGIST_BANK_CODE_NEW,
                        theBankCode)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_003", "投资者所属银行代码"));
                }
                // add by etliu 20180912 start
                else if (EquityInfoCheckUtil.whiteListCheck( theBankCode,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者所属银行代码"));
                }
                // add by etliu 20180912 end
            }else {                
            	if(DictCodeList.是否_中债_否.getValue().equals(isBelongBankStr)) {
            		message.add(this.getMessage("CHB_EQUITYINFO_E_036", "投资者所属银行代码"));
            	}
            }
        }

        return message;
    }

    /**
     * 投资者所属银行名称验证
     *
     * @param message 验证消息
     * @param theBankName 投资者归属银行名称
     * @param type 数据类型
     * @param isBelongBank 是否属于本行
     * @return message 验证消息
     * @see
     * @since 1.0
     */
    private List<String> checkTheBankName(List<String> message, String theBankName, String type,
                                          String isBelongBank,
                                          Map<String,TransformRule001Dto> spzMap,
                                          Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                          EquityInfo001Dto equityInfo001Dto) {
//        String isBelongBankKey = EquityInfoCheckUtil.subDictcode(isBelongBank, StringUtil.SPACE);
    	String isBelongBankStr = null;
    	if(StringUtil.isNotEmpty(isBelongBank) && isBelongBank.length() > 2) {
    		isBelongBankStr = isBelongBank.substring(0,2);
        }
        if ("01".equals(type) || "03".equals(type)) {
//            当“数据类型”选择“新增”和“重要信息变更”时，该要素登记要求由必填变更为选填。
//            if (StringUtil.isNotEmpty(theBankName) && theBankName.getBytes().length>90) {
//                message.add(this.getMessage("CHB_EQUITYINFO_E_010", "投资者所属银行名称", "60个字符"));
//            }
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("theBankName");
            }
            if (StringUtil.isNotEmpty(theBankName) && EquityInfoCheckUtil.checkByteLength(theBankName, 60)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_010", "投资者所属银行名称", "60个字节"));
            }

            else if(StringUtil.isNotEmpty(theBankName) &&EquityInfoCheckUtil.whiteListCheck( theBankName,objFileds,spzMap,equityInfo001Dto) ){
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者所属银行名称"));
            }else if(StringUtil.isEmpty(theBankName)) {
            	if(DictCodeList.是否_中债_否.getValue().equals(isBelongBankStr)) {
            		message.add(this.getMessage("CHB_EQUITYINFO_E_036", "投资者所属银行名称"));
            	}
            }
            /*if (!EquityInfoCheckUtil.isEmpty(isBelongBankKey)) {
                if ("02".equals(isBelongBankKey)) {
                    if (EquityInfoCheckUtil.isEmpty(theBankName)) {
                        message.add(
                            this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "投资者所属银行名称"));
                    }
                    else if (EquityInfoCheckUtil.isExceedLength(theBankName, 60)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_010", "投资者所属银行名称", "60"));
                    }
                }
                else {
                    // 投资者所属银行名称
                    if (!EquityInfoCheckUtil.isEmpty(theBankName)) {
                        message.add(
                            this.getMessage("CHB_EQUITYINFO_E_006", "新增或重要信息变更", "投资者所属银行名称"));
                    }
                }
            }
            else {
                // 投资者所属银行名称
                if (!EquityInfoCheckUtil.isEmpty(theBankName)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_006", "新增或重要信息变更", "投资者所属银行名称"));
                }
            }*/
        }
        return message;
    }

    /**
     * 原识别标识验证
     *
     * @param message 验证消息
     * @param orgIndentifyMark 原识别标识
     * @param type 数据类型
     * @param indentifyMark 识别标识
     * @return message 验证消息
     * @see
     * @since 1.0
     */
    private List<String> checkOrgIndentifyMark(List<String> message, String orgIndentifyMark,
                                               String type, String indentifyMark,
                                               Map<String,TransformRule001Dto> spzMap,
                                               Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                               EquityInfo001Dto equityInfo001Dto) {
        List<ChbTransformRuleObjDto> objFileds=null;
        if(objFiledsMap!=null){
            objFileds=objFiledsMap.get("orgIndentifyMark");
        }
        if (EquityInfoCheckUtil.isEmpty(orgIndentifyMark)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_005", "识别标识变更", "原识别标识"));
        }
        else if (orgIndentifyMark.equals(indentifyMark)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_011", "识别标识变更", "原识别标识", "识别标识"));
        }
        else if (EquityInfoCheckUtil.findPatern(PATTERN_IS_CHINA, orgIndentifyMark)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_012", "原识别标识"));
        }
//        else if (EquityInfoCheckUtil.isExceedLength(orgIndentifyMark, 30)) {
        else if (EquityInfoCheckUtil.checkByteLength(orgIndentifyMark, 30)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_010", "原识别标识", "30个字节"));
        }else if (StringUtil.contains(orgIndentifyMark, "'")) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_012", "原识别标识"));
        }
        // add by etliu 20180912 start
        else if (EquityInfoCheckUtil.whiteListCheck( orgIndentifyMark,objFileds,spzMap,equityInfo001Dto)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_035", "原识别标识"));
        }
        // add by etliu 20180912 end
        return message;
    }

    /**
     * 投资者类别验证
     *
     * @param message 验证信息
     * @param holderType 投资者类别
     * @param type 数据类型
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkHolderType(List<String> message, String holderType, String type,
                                         Map<String,TransformRule001Dto> spzMap,
                                         Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                         EquityInfo001Dto equityInfo001Dto) {
        List<ChbTransformRuleObjDto> objFileds=null;
        if(objFiledsMap!=null){
            objFileds=objFiledsMap.get("holderType");
        }
        if ("01".equals(type) || "03".equals(type)) {
            if (EquityInfoCheckUtil.isEmpty(holderType)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "投资者类别"));
            }
            else if (EquityInfoCheckUtil.isDictCodeExist(DictCode.投资者类别, holderType)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_004", "投资者类别"));
            }
            // add by etliu 20180912 start
            else if (EquityInfoCheckUtil.whiteListCheck( holderType,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者类别"));
            }
            // add by etliu 20180912 end
        }
        return message;
    }

    /**
     * 投资者分类验证
     *
     * @param message 验证信息
     * @param holderClassify 投资者类别
     * @param type 数据类型
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkHolderClassify(List<String> message, String holderClassify, String type,
                                         Map<String,TransformRule001Dto> spzMap,
                                         Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                         EquityInfo001Dto equityInfo001Dto) {
        List<ChbTransformRuleObjDto> objFileds=null;

        //添加系统参数三期是否校验投资者分类字段.01 验证字段,02不验证字段
        String HolderClassify = CacheUtil.getSysParam("HAS_HOLDER_CLASSIFY");
        if(StringUtil.isNotEmpty(HolderClassify)&&DictCodeList.是否_中债_是.getValue().equals(HolderClassify)) {
            if (objFiledsMap != null) {
                objFileds = objFiledsMap.get("holderClassify");
            }
            if (EquityInfoCheckUtil.isEmpty(holderClassify)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "投资者分类"));
            } else if (EquityInfoCheckUtil.isDictCodeExist(DictCode.投资者分类, holderClassify)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_004", "投资者分类"));
            } else if (EquityInfoCheckUtil.whiteListCheck(holderClassify, objFileds, spzMap, equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者分类"));
            }
        }
        return message;
    }

    /**
     * 投资者所属国家和地区
     *
     * @param message 验证消息
     * @param type 数据类型
     * @param holderRegion 权益人所属国家或地区
     * @param holderMark 境内外标识
     * @return 验证结构
     * @see
     * @since 1.0
     */
    private List<String> checkHolderRegion(List<String> message, String type, String holderRegion,
                                           String holderMark,
                                           Map<String,TransformRule001Dto> spzMap,
                                           Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                           EquityInfo001Dto equityInfo001Dto) {
        holderMark = EquityInfoCheckUtil.subDictcode(holderMark, StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            if ("02".equals(holderMark)) {
                List<ChbTransformRuleObjDto> objFileds=null;
                if(objFiledsMap!=null){
                    objFileds=objFiledsMap.get("holderRegion");
                }
                //当“数据类型”选择“新增”和“重要信息变更”，且“投资者境内外标识”选择“境外”时，该要素登记要求由必填变更为选填。
                /*if (EquityInfoCheckUtil.isEmpty(holderRegion)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更,并且投资者境内外标识选择境外",
                        "投资者所属国家或地区"));
                }
                else */if (StringUtil.isNotEmpty(holderRegion)&&EquityInfoCheckUtil.isDictCodeExist(DictCode.所属国家或地区, holderRegion)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_004", "投资者所属国家或地区"));
                }
                // add by etliu 20180912 start
                else if (StringUtil.isNotEmpty(holderRegion) && EquityInfoCheckUtil.whiteListCheck( holderRegion,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者所属国家或地区"));
                }
                // add by etliu 20180912 end
                // add by baojunjie 20210514 start
                if (StringUtil.isNotEmpty(holderRegion) && holderRegion.contains(DictCodeListExt2.所属国家或地区_中国.getValue())){
                    message.add(this.getMessage("投资者境内外标识选择“境外”时，投资者所属国家或地区不可以选择“CHN：中国”"));
                }
                // add by baojunjie 20210514 end
            }
            else if ("01".equals(holderMark)) {
                if (StringUtil.isNotEmpty(holderRegion)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_006", "新增或重要信息变更,并且投资者境内外标识选择境内",
                            "投资者所属国家或地区"));
                }
            }

        }
        else {
            if (StringUtil.isNotEmpty(holderRegion)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_006", "识别标识或其他信息变更", "投资者所属国家或地区"));
            }
        }
        return message;
    }

    /**
     * 个人证件类别验证
     *
     * @param message 验证消息
     * @param personalIdentifyType 个人证件类别
     * @param type 数据类型
     * @param holderType 投资者类别
     * @param holderMark 投资者境内外标识
     * @return message 验证消息
     * @see
     * @since 1.0
     */
    private List<String> checkPersonalIdentifyType(List<String> message,
                                                   String personalIdentifyType, String type,
                                                   String holderType, String holderMark,
                                                   Map<String,TransformRule001Dto> spzMap,
                                                   Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                                   EquityInfo001Dto equityInfo001Dto) {
        holderType = EquityInfoCheckUtil.subDictcode(holderType, StringUtil.SPACE);
        holderMark = EquityInfoCheckUtil.subDictcode(holderMark, StringUtil.SPACE);
        String personalIdentifyTypeKey = EquityInfoCheckUtil.subDictcode(personalIdentifyType,
                StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("personalIdentifyTypeKey");
            }
            if (("01".equals(holderType) || "02".equals(holderType) || "03".equals(holderType))
                    && "01".equals(holderMark)) {
                if (EquityInfoCheckUtil.isEmpty(personalIdentifyType)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "个人证件类别"));
                }
                else if (EquityInfoCheckUtil.isDictCodeExist(DictCode.个人证件类别,
                        personalIdentifyType)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_004", "个人证件类别"));
                }
                else if ("21".equals(personalIdentifyTypeKey)
                        || "22".equals(personalIdentifyTypeKey)
                        || "23".equals(personalIdentifyTypeKey)
                        || "24".equals(personalIdentifyTypeKey)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_017"));
                }
                // add by etliu 20180912 start
                else if (EquityInfoCheckUtil.whiteListCheck( personalIdentifyTypeKey,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "个人证件类别"));
                }
                // add by etliu 20180912 end
            }
            else if (("01".equals(holderType) || "02".equals(holderType)
                    || "03".equals(holderType))
                    && "02".equals(holderMark)) {
                if (EquityInfoCheckUtil.isEmpty(personalIdentifyType)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "个人证件类别"));
                }
                else if (EquityInfoCheckUtil.isDictCodeExist(DictCode.个人证件类别,
                        personalIdentifyType)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_004", "个人证件类别"));
                }
                else if (!("21".equals(personalIdentifyTypeKey)
                        || "22".equals(personalIdentifyTypeKey)
                        || "23".equals(personalIdentifyTypeKey)
                        || "24".equals(personalIdentifyTypeKey)
                        || "99".equals(personalIdentifyTypeKey))) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_018"));
                }// add by etliu 20180912 start
                else if (EquityInfoCheckUtil.whiteListCheck( personalIdentifyTypeKey,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "个人证件类别"));
                }
                // add by etliu 20180912 end
            }
            else {
                if (StringUtil.isNotEmpty(personalIdentifyType)
                        && !("01".equals(holderType) || "02".equals(holderType)
                        || "03".equals(holderType))) {
                    message.add(
                            this.getMessage("CHB_EQUITYINFO_E_006", "新增或重要信息变更，投资者不为个人类别", "个人证件类别"));
                }
            }
        }
        return message;
    }

    /**
     * 机构证件类别验证
     *
     * @param message 验证信息
     * @param institutionInentifyType 机构证件类别
     * @param type 数据类型
     * @param holderType 投资者类别
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkInstitutionInentifyType(List<String> message,
                                                      String institutionInentifyType, String type,
                                                      String holderType,
                                                      Map<String,TransformRule001Dto> spzMap,
                                                      Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                                      EquityInfo001Dto equityInfo001Dto) {
        holderType = EquityInfoCheckUtil.subDictcode(holderType, StringUtil.SPACE);
        String institutionInentifyTypeKey = EquityInfoCheckUtil.subDictcode(
                institutionInentifyType, StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            //"要求当 投资者类别为 SPV（对应投资者类别选项为“17-23”）时，机构证件类别应选择“ 时，机构证件类别应选择“ 时，机构证件类别应选择“ 34 SPV 登记编码”SPV"
            //经检查不用变动
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("institutionInentifyTypeKey");
            }
            if (!("01".equals(holderType) || "02".equals(holderType) || "03".equals(holderType))) {
                if (EquityInfoCheckUtil.isEmpty(institutionInentifyType)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "机构证件类别"));
                }
                else if (EquityInfoCheckUtil.isDictCodeExist(DictCode.机构证件类别,
                        institutionInentifyType)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_004", "机构证件类别"));
                }
                else if ("17".equals(holderType) || "18".equals(holderType)
                        || "19".equals(holderType) || "20".equals(holderType)
                        || "21".equals(holderType) || "22".equals(holderType)
                        || "23".equals(holderType)) {
                    if (!"34".equals(institutionInentifyTypeKey)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_019"));
                    }
                }
                // add by etliu 20180912 start
                else if (EquityInfoCheckUtil.whiteListCheck( institutionInentifyTypeKey,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "机构证件类别"));
                }
                // add by etliu 20180912 end
                else {
                    if ("34".equals(institutionInentifyTypeKey)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_030", "新增或重要信息变更", "17~23"));
                    }
                }
            }
            else {
                if (StringUtil.isNotEmpty(institutionInentifyTypeKey)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_006", "新增或重要信息变更，并且投资者类别为01-03",
                            "机构证件类别"));
                }
            }
        }
        return message;
    }

    /**
     * 其他证件名称验证
     *
     * @param message 验证信息
     * @param otherIdentifyName 其他证件名称
     * @param type 数据类型
     * @param holderType 投资者类别
     * @param personalIdentifyType 个人证件类型
     * @param institutionInentifyType 机构证件类型
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkOtherIdentifyName(List<String> message, String otherIdentifyName,
                                                String type, String holderType,
                                                String personalIdentifyType,
                                                String institutionInentifyType,
                                                Map<String,TransformRule001Dto> spzMap,
                                                Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                                EquityInfo001Dto equityInfo001Dto) {
        holderType = EquityInfoCheckUtil.subDictcode(holderType, StringUtil.SPACE);
        String personalIdentifyTypeKey = EquityInfoCheckUtil.subDictcode(personalIdentifyType,
                StringUtil.SPACE);
        String institutionInentifyTypeKey = EquityInfoCheckUtil.subDictcode(
                institutionInentifyType, StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("otherIdentifyName");
            }
            if ("01".equals(holderType) || "02".equals(holderType) || "03".equals(holderType)) {
                if ("99".equals(personalIdentifyTypeKey)) {
                    if (EquityInfoCheckUtil.isEmpty(otherIdentifyName)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_005",
                                "新增或重要信息变更，投资者类别选择01 普通个人或者02 高净值客户或者03 私人银行客户，个人证件类别为99 其他",
                                "其他证件名称"));
                    }
//                    else if (EquityInfoCheckUtil.isExceedLength(otherIdentifyName, 60)) {
//                        message.add(this.getMessage("CHB_EQUITYINFO_E_010", "其他证件名称", "60"));
//                    }
                    else if (EquityInfoCheckUtil.checkByteLength(otherIdentifyName, 60)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_010", "其他证件名称", "60个字节"));
                    }
                    // add by etliu 20180912 start
                    else if (EquityInfoCheckUtil.whiteListCheck( otherIdentifyName,objFileds,spzMap,equityInfo001Dto)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_035", "其他证件名称"));
                    }
                    // add by etliu 20180912 end
                }
                else {
                    if (StringUtil.isNotEmpty(otherIdentifyName)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_006",
                                "新增或重要信息变更，投资者类别选择01 普通个人或者02 高净值客户或者03 私人银行客户，个人证件类别不选择其他",
                                "其他证件名称"));
                    }
                }

            }
            else {
                if ("17".equals(holderType) || "18".equals(holderType) || "19".equals(holderType)
                        || "20".equals(holderType) || "21".equals(holderType)
                        || "22".equals(holderType) || "23".equals(holderType)) {
                    if (StringUtil.isNotEmpty(otherIdentifyName)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_006",
                                "新增或重要信息变更、投资者类别为特定目的载体（SPV）", "其他证件名称"));
                    }

                }
                else if ("99".equals(institutionInentifyTypeKey)) {
                    if (EquityInfoCheckUtil.isEmpty(otherIdentifyName)) {
                        message.add(
                                this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "其他证件名称"));
                    }
//                    else if (EquityInfoCheckUtil.isExceedLength(otherIdentifyName, 60)) {
                    else if (EquityInfoCheckUtil.checkByteLength(otherIdentifyName, 60)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_010", "其他证件名称", "60个字节"));
                    }
                    // add by etliu 20180912 start
                    else if (EquityInfoCheckUtil.whiteListCheck( otherIdentifyName,objFileds,spzMap,equityInfo001Dto)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_035", "其他证件名称"));
                    }
                    // add by etliu 20180912 end
                }
                else {
                    if (StringUtil.isNotEmpty(otherIdentifyName)) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_006",
                                "新增或重要信息变更，投资者类别为04-26，证件类型不为其他", "其他证件名称"));
                    }
                }
            }
        }
        return message;
    }

    /**
     * 证件号码验证
     *
     * @param message 验证信息
     * @param inentifyNumber 证件号码
     * @param type 数据类型
     * @param personalIdentifyType 个人证件类型
     * @param institutionInentifyType 机构证件类型
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkInentifyNumber(List<String> message, String inentifyNumber,
                                             String type, String personalIdentifyType,
                                             String institutionInentifyType,
                                             Map<String,TransformRule001Dto> spzMap,
                                             Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                             EquityInfo001Dto equityInfo001Dto) {
        // holderType = EquityInfoCheckUtil.subDictcode(holderType, " ");
        String personalIdentifyTypeKey = EquityInfoCheckUtil.subDictcode(personalIdentifyType,
                StringUtil.SPACE);
        String institutionInentifyTypeKey = EquityInfoCheckUtil.subDictcode(
                institutionInentifyType, StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("inentifyNumber");
            }
            if (EquityInfoCheckUtil.isEmpty(inentifyNumber)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "证件号码"));
            }
            else if ("00".equals(personalIdentifyTypeKey)
                    || "01".equals(personalIdentifyTypeKey)) {
//                if (inentifyNumber.length() != 18 && inentifyNumber.length() != 15) {
// 修改证件号校验 by caozhenming 20210923 start
//                if (EquityInfoCheckUtil.getByteLength(inentifyNumber) != 18 && EquityInfoCheckUtil.getByteLength(inentifyNumber) != 15) {
//                    message.add(this.getMessage("CHB_EQUITYINFO_E_002", "证件号码", "15或18"));
//                }
                // 身份证号校验
                Map<String,String> result = CheckUtil.idCardValidator(inentifyNumber);
                if (!Boolean.valueOf(result.get("resultCode"))){
                    message.add(result.get("resultMessage"));
                }
                // 修改证件号校验 by caozhenming 20210923 end
            }
            else if ("01".equals(institutionInentifyTypeKey)) {
//                if (inentifyNumber.length() != 18) {
                if (EquityInfoCheckUtil.getByteLength(inentifyNumber) != 18) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_002", "证件号码", "18"));
                }
            }
            else if ("02".equals(institutionInentifyTypeKey)) {
//                if (inentifyNumber.length() != 9) {
                if (EquityInfoCheckUtil.getByteLength(inentifyNumber) != 9) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_002", "证件号码", "9"));
                }
            }
//            else if ("03".equals(institutionInentifyTypeKey)
//                     || "24".equals(institutionInentifyTypeKey)
//                     || "25".equals(institutionInentifyTypeKey)) {
//                if (inentifyNumber.length() != 15) {
//                    message.add(this.getMessage("CHB_EQUITYINFO_E_002", "证件号码", "15"));
//                }
//            }

//            // add by etliu 20180912 start
//            else if (EquityInfoCheckUtil.whiteListCheck( inentifyNumber)) {
//                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "证件号码"));
//            }
//            // add by etliu 20180912 end
            else {
//                if (EquityInfoCheckUtil.isExceedLength(inentifyNumber, 30)) {
                if (EquityInfoCheckUtil.checkByteLength(inentifyNumber, 30)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_010", "证件号码", "30个字节"));
                }
            }

            // add by etliu 20180914 start
            if (StringUtil.isNotBlank(inentifyNumber)
                    && EquityInfoCheckUtil.whiteListCheck( inentifyNumber,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "证件号码"));
            }
            // add by etliu 20180914 end
        }
        return message;
    }

    /**
     * SPV资金托管账户开户行验证
     *
     * @param message 验证信息
     * @param spvCapAccBank SPV资金托管账户开户行验证
     * @param holderType 投资者类别
     * @param type 数据类型
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkSpvCapAccBank(List<String> message, String spvCapAccBank,
                                            String holderType, String type,
                                            Map<String,TransformRule001Dto> spzMap,
                                            Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                            EquityInfo001Dto equityInfo001Dto) {
        //当“数据类型”选择“新增”和“重要信息变更”，且“投资者类别”为SPV 时，该要素登记要求由必填变更为选填。
        holderType = EquityInfoCheckUtil.subDictcode(holderType, StringUtil.SPACE);
        // String spvCapAccBankKey = EquityInfoCheckUtil.subDictcode(spvCapAccBank, " ");
        if ("01".equals(type) || "03".equals(type)) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("spvCapAccBank");
            }
            if ("17".equals(holderType) || "18".equals(holderType) || "19".equals(holderType)
                    || "20".equals(holderType) || "21".equals(holderType) || "22".equals(holderType)
                    || "23".equals(holderType)) {
                /*if (EquityInfoCheckUtil.isEmpty(spvCapAccBank)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更，投资者类别为17-23",
                        "SPV资金托管账户开户行"));
                }
                else */if (StringUtil.isNotEmpty(spvCapAccBank)&&EquityInfoCheckUtil.isDictCodeExist(DictCode.SPV资金托管账户开户行,
                        spvCapAccBank)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_004", "SPV资金托管账户开户行"));
                }
                // add by etliu 20180912 start
                else if (StringUtil.isNotEmpty(spvCapAccBank) && EquityInfoCheckUtil.whiteListCheck( spvCapAccBank,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "SPV资金托管账户开户行"));
                }
                // add by etliu 20180912 end
            }
            else {
                if (StringUtil.isNotEmpty(spvCapAccBank)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_006", "新增或重要信息变更，投资者类别为17-23以外",
                            "SPV资金托管账户开户行"));
                }
            }
        }
        return message;
    }

    /**
     * 其他资金托管账户开户行
     *
     * @param message 验证信息
     * @param otherCapAccBank 其他资金托管账户开户行
     * @param holderType 投资者类别
     * @param type 数据类型
     * @param spvCapAccBank SPV资金托管账户开户行
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkOtherCapAccBank(List<String> message, String otherCapAccBank,
                                              String holderType, String type,
                                              String spvCapAccBank,
                                              Map<String,TransformRule001Dto> spzMap,
                                              Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                              EquityInfo001Dto equityInfo001Dto) {
        holderType = EquityInfoCheckUtil.subDictcode(holderType, StringUtil.SPACE);
        //当“数据类型”选择“新增”和“重要信息变更”，且“投资者类别”为SPV 时,且“SPV开户行”选择“其他”时，该要素登记要求由必填变更为选填。
        String spvCapAccBankKey = EquityInfoCheckUtil.subDictcode(spvCapAccBank, StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            if (("17".equals(holderType) || "18".equals(holderType) || "19".equals(holderType)
                    || "20".equals(holderType) || "21".equals(holderType) || "22".equals(holderType)
                    || "23".equals(holderType))
                    && "999999".equals(spvCapAccBankKey)) {
                List<ChbTransformRuleObjDto> objFileds=null;
                if(objFiledsMap!=null){
                    objFileds=objFiledsMap.get("otherCapAccBank");
                }
                /*if (EquityInfoCheckUtil.isEmpty(otherCapAccBank)) {
                    message.add(
                        this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "其他资金托管账户开户行"));
                }
//                else */
//                if (StringUtil.isNotEmpty(otherCapAccBank)&&EquityInfoCheckUtil.isExceedLength(otherCapAccBank, 60)) {
                if (StringUtil.isNotEmpty(otherCapAccBank) && EquityInfoCheckUtil.checkByteLength(otherCapAccBank, 60)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_010", "其他资金托管账户开户行", "60个字节"));
                }
                // add by etliu 20180912 start
                else if (StringUtil.isNotEmpty(otherCapAccBank) && EquityInfoCheckUtil.whiteListCheck( otherCapAccBank,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "其他资金托管账户开户行"));
                }
                // add by etliu 20180912 end
            }
            else {
                if (StringUtil.isNotEmpty(otherCapAccBank)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_020"));
                }
            }
        }
        return message;
    }

    /**
     * SPV登记编码
     *
     * @param message 验证信息
     * @param spvRegistCode SPV登记编码
     * @param holderType 投资者类别
     * @param type 数据类型
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkSpvRegistCode(List<String> message, String spvRegistCode,
                                            String holderType, String type) {
        holderType = EquityInfoCheckUtil.subDictcode(holderType, StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            if (("17".equals(holderType) || "18".equals(holderType) || "19".equals(holderType)
                    || "20".equals(holderType) || "21".equals(holderType) || "22".equals(holderType)
                    || "23".equals(holderType))) {
                if (EquityInfoCheckUtil.isEmpty(spvRegistCode)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "SPV登记编码"));
                }
                else if (EquityInfoCheckUtil.isExceedLength(spvRegistCode, 100)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_010", "SPV登记编码", "100"));
                }
            }
            else {
                if (StringUtil.isNotEmpty(spvRegistCode)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_021"));
                }
            }
        }
        return message;
    }

    /**
     * 投资者名称
     *
     * @param message 验证信息
     * @param holderName 投资者名称
     * @param type 数据类型
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkHolderName(List<String> message, String holderName, String type, String holderMark,
                                         Map<String,TransformRule001Dto> spzMap,
                                         Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                         EquityInfo001Dto equityInfo001Dto) {
        if ("01".equals(type) || "03".equals(type)) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("holderName");
            }
            if (EquityInfoCheckUtil.isEmpty(holderName)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "投资者名称"));
            }
//            else if (holderName.getBytes().length>300) {
            else if (EquityInfoCheckUtil.checkByteLength(holderName, 200)) {
//                message.add(this.getMessage("CHB_EQUITYINFO_E_010", "投资者名称", "200个字符"));
                message.add(this.getMessage("CHB_EQUITYINFO_E_010", "投资者名称", "200个字节"));
            }
            // add by etliu 20180912 start
            else if (EquityInfoCheckUtil.whiteListCheck( holderName,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者名称"));
            }
            // add by etliu 20180912 end
            // 境内投资者名称包含空格，则报错，数据标记为待处理状态
//            else if (StringUtil.equals(DictCodeList.境内外_境内.getValue(), holderMark)
//                     && holderName.indexOf(StringUtil.SPACE) != -1) {
//                message.add(this.getMessage("CHB_EQUITYINFO_E_029", "境内投资者名称"));
//            }
        }
        return message;
    }

    /**
     * 性别验证
     *
     * @param message 验证信息
     * @param holderSex 性别
     * @param type 数据类型
     * @param holderType 投资者类别
     * @return List<String>
     * @see
     * @since 1.0
     */
    private List<String> checkHolderSex(List<String> message, String holderSex, String type,
                                        String holderType,
                                        Map<String,TransformRule001Dto> spzMap,
                                        Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                        EquityInfo001Dto equityInfo001Dto) {
        holderType = EquityInfoCheckUtil.subDictcode(holderType, StringUtil.SPACE);
        if ("01".equals(type) || "03".equals(type)) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("holderSex");
            }
            if ("01".equals(holderType) || "02".equals(holderType) || "03".equals(holderType)) {
                if (EquityInfoCheckUtil.isEmpty(holderSex)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "投资者性别"));
                }
                else if (EquityInfoCheckUtil.isDictCodeExist(DictCode.性别_中债, holderSex)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_004", "投资者性别"));
                }
                // add by etliu 20180912 start
                else if (EquityInfoCheckUtil.whiteListCheck( holderSex,objFileds,spzMap,equityInfo001Dto)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_035", "投资者性别"));
                }
                // add by etliu 20180912 end
            }
            else {
                if (StringUtil.isNotEmpty(holderSex)) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_006", "新增或重要信息变更,并且投资者类别不为个人类型",
                            "投资者性别"));
                }
            }
        }
        return message;
    }

    /**
     * 风险验证
     *
     * @param message 验证信息
     * @param type 数据类型
     * @return List<String>
     * @see
     * @since 1.0
     */
    private List<String> checkHolderRiskPrefer(List<String> message, String holderRiskPrefer,
                                               String type,
                                               Map<String,TransformRule001Dto> spzMap,
                                               Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                               EquityInfo001Dto equityInfo001Dto) {
        List<ChbTransformRuleObjDto> objFileds=null;
        if(objFiledsMap!=null){
            objFileds=objFiledsMap.get("holderRiskPrefer");
        }
        if ("01".equals(type) || "03".equals(type)) {
            //当“数据类型”选择“新增”和“重要信息变更”时，该要素登记要求由必填变更为选填。
            /*if (EquityInfoCheckUtil.isEmpty(holderRiskPrefer)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "风险偏好"));
            }
            else */if (StringUtil.isNotEmpty(holderRiskPrefer)&&EquityInfoCheckUtil.isDictCodeExist(DictCode.投资风险偏好, holderRiskPrefer)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_004", "风险偏好"));
            }
            // add by etliu 20180912 start
            else if (StringUtil.isNotEmpty(holderRiskPrefer) && EquityInfoCheckUtil.whiteListCheck( holderRiskPrefer,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "风险偏好"));
            }
            // add by etliu 20180912 end
        }
        //其他信息变更
        else if ("04".equals(type) && StringUtil.isNotEmpty(holderRiskPrefer)) {
            if (StringUtil.isNotEmpty(holderRiskPrefer)&&EquityInfoCheckUtil.isDictCodeExist(DictCode.投资风险偏好, holderRiskPrefer)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_004", "风险偏好"));
            }
            // add by etliu 20180912 start
            else if (StringUtil.isNotEmpty(holderRiskPrefer) && EquityInfoCheckUtil.whiteListCheck( holderRiskPrefer,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "风险偏好"));
            }
            // add by etliu 20180912 end
        }
        return message;
    }

    /**
     * 联系方式验证（增加 为 04 其他信息变更时做相关性必填验证）
     *
     * @param message 验证信息
     * @param holderCellphone 手机号码
     * @param holderTelephone 固定电话
     * @param holderEmail 电子邮箱
     * @param type 数据类型
     * @return 返回检查结果
     * @see
     * @since 1.0
     */
    private List<String> checkContactInfo(List<String> message, String holderCellphone,
                                          String holderTelephone, String holderEmail,
                                          String type,String holderRiskPrefer,
                                          Map<String,TransformRule001Dto> spzMap,
                                          Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                          EquityInfo001Dto equityInfo001Dto) {
        if (StringUtil.equals("01", type) || StringUtil.equals("03", type) || StringUtil.equals("04", type) ) {
            List<ChbTransformRuleObjDto> objFileds=null;
            if(objFiledsMap!=null){
                objFileds=objFiledsMap.get("holderCellphone");
            }
            List<ChbTransformRuleObjDto> objFileds1=null;
            if(objFiledsMap!=null){
                objFileds1=objFiledsMap.get("holderTelephone");
            }
            List<ChbTransformRuleObjDto> objFileds2=null;
            if(objFiledsMap!=null){
                objFileds2=objFiledsMap.get("holderEmail");
            }
            /*if (EquityInfoCheckUtil.isEmpty(holderCellphone)
            // && EquityInfoCheckUtil.isEmpty(holderTelephone)
            // && EquityInfoCheckUtil.isEmpty(holderEmail)
            ) {
                // message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "联系方式"));
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "手机号码"));
            }
            if (EquityInfoCheckUtil.isEmpty(holderTelephone)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "固定电话"));
            }
            if (EquityInfoCheckUtil.isEmpty(holderEmail)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "电子邮箱"));
            }*/
            /*if (!EquityInfoCheckUtil.isEmpty(holderCellphone)
                && !EquityInfoCheckUtil.isEmpty(holderTelephone)
                && !EquityInfoCheckUtil.isEmpty(holderEmail) && "无".contains(holderCellphone)
                && "无".contains(holderTelephone) && "无".contains(holderEmail)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_022"));
            }*/
            if (!EquityInfoCheckUtil.isEmpty(holderCellphone)
                    && !holderCellphone.matches("[0-9]{11}")) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_016"));
            }
            // add by etliu 20180912 start
            else if (!EquityInfoCheckUtil.isEmpty(holderCellphone)
                    && EquityInfoCheckUtil.whiteListCheck( holderCellphone,objFileds,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "手机号码"));
            }

            if (!EquityInfoCheckUtil.isEmpty(holderTelephone)
                    && EquityInfoCheckUtil.whiteListCheck( holderTelephone,objFileds1,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "固定电话"));
            }

            if (!EquityInfoCheckUtil.isEmpty(holderEmail)
                    && EquityInfoCheckUtil.whiteListCheck( holderEmail,objFileds2,spzMap,equityInfo001Dto)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_035", "电子邮箱"));
            }
            // add by etliu 20180912 end
            if (!StringUtil.equals(holderTelephone, ReportGlobalConstant.DEFAULTTEL)&&!EquityInfoCheckUtil.isEmpty(holderTelephone)
                    && EquityInfoCheckUtil.findPatern(PATTERN_OFFICE_PHONE, holderTelephone)
                // && EquityInfoCheckUtil.findPatern(PATTERN_NO_TEPHONE, holderTelephone)
                /*&& !"无".contains(holderTelephone)*/) {
                //符合要求标志
                boolean flag = false;
                if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
                    if(holderTelephone.length()<7 || holderTelephone.length()>30){
                        flag = true;
                    }
                }else {
                    if(holderTelephone.length()<7 || holderTelephone.length()>30){
                        flag = true;
                    }
                    else if(holderTelephone.contains("+") && !holderTelephone.startsWith("+")){
                        flag = true;
                    }
                    else if(holderTelephone.endsWith("-")){
                        flag = true;
                    }
                    else{
                        int countNum = 0;
                        for (int i=0;i<holderTelephone.length();i++) {
                            if(Pattern.compile("^[0-9]$").matcher(String.valueOf(holderTelephone.charAt(i))).matches()){
                                countNum++;
                            }
                        }
                        if(countNum<holderTelephone.length()){
                            flag = true;
                        }
                    }
                }
                if(flag){
                    message.add(this.getMessage("REPORT_E_TEL_001"));
                }
            }
            if (!EquityInfoCheckUtil.isEmpty(holderEmail)
                    && EquityInfoCheckUtil.findPatern(PATTERN_MAILBOX, holderEmail)
                /*&& !"无".contains(holderEmail)*/) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_015"));
            }
//            else if (EquityInfoCheckUtil.isExceedLength(holderEmail, 50)) {
            else if (EquityInfoCheckUtil.checkByteLength(holderEmail, 50)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_010", "电子邮箱", "50个字节"));
            }
        }
        // 其他信息变更的情况
        if (StringUtil.equals("04", type)) {
            //若风险偏好为空 且联系方式为空 则报出message
            if (StringUtil.isEmpty(holderRiskPrefer)&&((EquityInfoCheckUtil.isEmpty(holderCellphone)
                    && EquityInfoCheckUtil.isEmpty(holderTelephone)
                    && EquityInfoCheckUtil.isEmpty(holderEmail))
                /*|| ("无".contains(holderCellphone) && "无".contains(holderTelephone)
                    && "无".contains(holderEmail))*/)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_023"));
            }
        }
        return message;
    }
    /**
     * 联系方式验证（增加 为 04 其他信息变更时做相关性必填验证）
     *
     * @param message 验证信息
     * @param equityInfo001Dto 验证dto
     * @param type 数据类型
     * @param isInterf 是否接口验证
     * @return 返回检查结果
     * @see
     * @since 1.0
     */
    private List<String> checkContactInfoByInterf(List<String> message,EquityInfo001Dto equityInfo001Dto,
                                                  String type, boolean isInterf) {
        if (StringUtil.equals("01", type) || StringUtil.equals("03", type) || StringUtil.equals("04", type) ) {
            /*if (EquityInfoCheckUtil.isEmpty(holderCellphone)
            // && EquityInfoCheckUtil.isEmpty(holderTelephone)
            // && EquityInfoCheckUtil.isEmpty(holderEmail)
            ) {
                // message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "联系方式"));
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "手机号码"));
            }
            if (EquityInfoCheckUtil.isEmpty(holderTelephone)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "固定电话"));
            }
            if (EquityInfoCheckUtil.isEmpty(holderEmail)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_005", "新增或重要信息变更", "电子邮箱"));
            }*/
            /*if (!EquityInfoCheckUtil.isEmpty(holderCellphone)
                && !EquityInfoCheckUtil.isEmpty(holderTelephone)
                && !EquityInfoCheckUtil.isEmpty(holderEmail) && "无".contains(holderCellphone)
                && "无".contains(holderTelephone) && "无".contains(holderEmail)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_022"));
            }*/
            if (!EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getHolderCellphone())
                    && !equityInfo001Dto.getHolderCellphone().matches("[0-9]{11}")) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_016"));
            }
            if (!StringUtil.equals(equityInfo001Dto.getHolderTelephone(), ReportGlobalConstant.DEFAULTTEL)
                    &&!EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getHolderTelephone())
                    && EquityInfoCheckUtil.findPatern(PATTERN_OFFICE_PHONE, equityInfo001Dto.getHolderTelephone())
                // && EquityInfoCheckUtil.findPatern(PATTERN_NO_TEPHONE, holderTelephone)
                /*&& !"无".contains(holderTelephone)*/) {
                //符合要求标志
                boolean flag = false;
                if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
                    if(equityInfo001Dto.getHolderTelephone().length()<7 || equityInfo001Dto.getHolderTelephone().length()>30){
                        flag = true;
                    }
                }else {
                    if(equityInfo001Dto.getHolderTelephone().length()<7 || equityInfo001Dto.getHolderTelephone().length()>30){
                        flag = true;
                    }
                    else if(equityInfo001Dto.getHolderTelephone().contains("+") && !equityInfo001Dto.getHolderTelephone().startsWith("+")){
                        flag = true;
                    }
                    else if(equityInfo001Dto.getHolderTelephone().endsWith("-")){
                        flag = true;
                    }
                    else{
                        int countNum = 0;
                        for (int i=0;i<equityInfo001Dto.getHolderTelephone().length();i++) {
                            if(Pattern.compile("^[0-9]$").matcher(String.valueOf(equityInfo001Dto.getHolderTelephone().charAt(i))).matches()){
                                countNum++;
                            }
                        }
                        if(countNum<equityInfo001Dto.getHolderTelephone().length()){
                            flag = true;
                        }
                    }
                }
                if(StringUtils.equals(DictCodeList.是否_中债_是.getValue(), SvrDataUtil.getSysParam(
                        ReportGlobalConstant.IS_DEFAUL_TELPHONE))&&flag&&isInterf){
                    equityInfo001Dto.setHolderTelephone(ReportGlobalConstant.DEFAULTTEL);
                }
                else if(flag){
                    message.add(this.getMessage("REPORT_E_TEL_001"));
                }
            }
            if (!EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getHolderEmail())
                    && EquityInfoCheckUtil.findPatern(PATTERN_MAILBOX, equityInfo001Dto.getHolderEmail())
                /*&& !"无".contains(holderEmail)*/) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_015"));
            }
            else if (EquityInfoCheckUtil.isExceedLength(equityInfo001Dto.getHolderEmail(), 50)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_010", "电子邮箱", "50"));
            }
        }
        // 其他信息变更的情况
        if (StringUtil.equals("04", type)) {
            //若风险偏好为空 且联系方式为空 则报出message
            if (StringUtil.isEmpty(equityInfo001Dto.getHolderRiskPrefer())
                    &&((EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getHolderCellphone())
                    && EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getHolderCellphone())
                    && EquityInfoCheckUtil.isEmpty(equityInfo001Dto.getHolderEmail()))
                /*|| ("无".contains(holderCellphone) && "无".contains(holderTelephone)
                    && "无".contains(holderEmail))*/)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_023"));
            }
        }
        return message;
    }
    /**
     * 备注验证
     *
     * @param message 验证信息
     * @param remark 备注
     * @return message 验证信息
     * @see
     * @since 1.0
     */
    private List<String> checkRemark(List<String> message, String remark, String type,
                                     Map<String,TransformRule001Dto> spzMap,
                                     Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                     EquityInfo001Dto equityInfo001Dto) {
//        if (StringUtil.isNotEmpty(remark)&&remark.getBytes().length>384) {
        List<ChbTransformRuleObjDto> objFileds=null;
        if(objFiledsMap!=null){
            objFileds=objFiledsMap.get("remark");
        }
        if (StringUtil.isNotEmpty(remark) && EquityInfoCheckUtil.checkByteLength(remark, 256)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_010", "备注", "256个字节"));
        }
        // add by etliu 20180912 start
        else if (StringUtil.isNotEmpty(remark) && EquityInfoCheckUtil.whiteListCheck( remark,objFileds,spzMap,equityInfo001Dto)) {
            message.add(this.getMessage("CHB_EQUITYINFO_E_035", "备注"));
        }
        // add by etliu 20180912 end
        return message;
    }

    /**
     * 验证excel导入验证
     *
     * @param dataList 解析后的excel
     * @param listType 数据类型list
     * @return 校验结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> checkExcelEquity(List<List<String>> dataList,
                                                List<Map<String, String>> listType, List<List<String>> dataTypeList,
                                                Map<String,TransformRule001Dto> spzMap,
                                                Map<String,List<ChbTransformRuleObjDto>> objFiledsMap,
                                                String orgCode) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        Map<String, String> map = null;
        // List<EquityCheckValue> checkDto = new ArrayList<>();
        String bankCode = CommonUtil.getUserBankCode(orgCode);

        //判断导入的数据是否存在重复的主键
        Map<String, Integer> keyMap = new HashMap<>();
        Map<String, Integer> orgkeyMap = new HashMap<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> message = new ArrayList<>();
            List<String> messageOrg = new ArrayList<>();
            List<String> list = dataList.get(i);
            String keyStr = list.get(1);
            String keyStrOrg = list.get(2);

            if (StringUtil.isNotBlank(keyStr)) {
                if (keyMap.containsKey(keyStr)) {
                    int j = keyMap.get(keyStr);
                    message.add(this.getMessage("REPORT_COMMON_FILE_E_0027", String.valueOf(j)+","+String.valueOf(i + 2)));
                    if (!message.isEmpty()) {
                        checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(j),
                                StringUtil.joinString(message, GlobalConstant.COMMA)));
                    }
                }
                else
                {
                    keyMap.put(keyStr, i + 2);
                }
            }

            //modified 判断导入的数据是否存在重复的原识别标识 by liupengfei 2018/10/19 start
            if (StringUtil.isNotBlank(keyStrOrg)) {
                if (orgkeyMap.containsKey(keyStrOrg)) {
                    int j = orgkeyMap.get(keyStrOrg);
                    messageOrg.add(this.getMessage("REPORT_COMMON_FILE_E_0027", String.valueOf(j)+","+String.valueOf(i + 2)) + "。[原识别标识]");
                    if (!messageOrg.isEmpty()) {
                        checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(j),
                                StringUtil.joinString(messageOrg, GlobalConstant.COMMA)));
                    }
                }
                else
                {
                    orgkeyMap.put(keyStrOrg, i + 2);
                }
            }
        }
        if(CollectionUtils.isNotEmpty(checkResult)){
            return checkResult;
        }
        //modified 判断导入的数据是否存在重复的原始标识 by liupengfei 2018/10/19 start

        // add by yangxianjiu 2021-12-24 start 优化投资者身份导入check end
        List<EquityInfoRegist001Dto> equityInfoRegist001DtoList = getAllForEquityInfo(dataList);

        // 获取所有
        // List<EquityInfoRegist001Dto> equityInfoRegist001DtoList = equityInfoRegist001Mapper.queryAllForEquityInfo(paramMap);
        // BankCode 拼接 IndentifyMark
        Map<String, EquityInfoRegist001Dto> bankCodeIndentifyMarkMap = equityInfoRegist001DtoList.stream().collect(JoyinCollectors.toMap(x -> x.getBankCode() + "_" + x.getIndentifyMark(), part -> part));
        // BankCode 拼接 OrgIndentifyMark
//        Map<String, EquityInfoRegist001Dto> bankCodeOrgIndentifyMarkMap = equityInfoRegist001DtoList.stream().collect(JoyinCollectors.toMap(x -> x.getBankCode() + "_" + x.getOrgIndentifyMark(), part -> part));

        List<Map<String,EquityInfoRegist001Dto>> bankCodeMarkList = new ArrayList<>();
        bankCodeMarkList.add(bankCodeIndentifyMarkMap);
//        bankCodeMarkList.add(bankCodeOrgIndentifyMarkMap);

        for (int i = 0; i < dataList.size(); i++ ) {
            map = new HashMap<String, String>();
            List<String> list = dataList.get(i);
            EquityInfo001Dto equityInfo001Dto=new EquityInfo001Dto();
            // 检查excel改列是否为空或者是否符合模板规定的列数
            // checkResult = checkExcelCow(i, list, checkResult);
            if (checkExcelCow(i, list) != null) {
                checkResult.add(checkExcelCow(i, list));
                continue;
            }
            // 验证消息
            List<String> message = new ArrayList<>();

            //判断是否为导入代销数据，判断数据类型
            if(null != dataTypeList) {
                //数据类型不为空
                if(dataTypeList.get(i).get(8) != null) {
                    //当数据类型为识别标识变更时，原识别标识必填。
                    //识别类型
//            		String dataTypeTemp = dataTypeList.get(i).get(8);
//            		//判断数据类型不符合规范后进行校验
//            		if(!"01 新增".equals(dataTypeTemp) && !"02 识别标识变更".equals(dataTypeTemp) && !"03 重要信息变更".equals(dataTypeTemp) && !"04 其他信息变更".equals(dataTypeTemp)){
//            			message.add(this.getMessage("CHB_EQUHOLDIMPORT_DATATYPECHECK"));
//            		}
                    String dataType = EquityInfoCheckUtil.subDictcode(dataTypeList.get(i).get(8), " ");
                    if (DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(dataType)) {
                        if(dataTypeList.get(i).get(2).isEmpty()) {
                            message.add(this.getMessage("CHB_EQUITYINFO_E_031"));
                        }
                    }
                    //当数据类型为识别标识变更以外时，原识别标识不可填。
                    else if(!dataTypeList.get(i).get(2).isEmpty()) {
                        message.add(this.getMessage("CHB_EQUITYINFO_E_032"));
                    }
                }
            }

            // 识别标识验证
            message = checkIndentifyMark(message, list.get(1),spzMap,objFiledsMap,equityInfo001Dto);
            // 登记银行编码
            message = checkBankCode(message, list.get(0),spzMap,objFiledsMap,equityInfo001Dto);
            if(StringUtil.equals(list.get(1), list.get(2))){
                message.add(this.getMessage("CHB_EQUITYINFO_E_033"));
            }
            //判断原识别标识是否已存在
//            String orgIndentifyMark = list.get(2);
//            if(orgIndentifyMark!=null && !StringUtil.isEmpty(orgIndentifyMark)){
//            	checkOrgIndentifyMark(message,orgIndentifyMark,list.get(1));
//            }
//            if (!message.isEmpty()) {
//                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i + 2),
//                    StringUtil.joinString(message, GlobalConstant.COMMA)));
//                continue;
//            }
            // excel中的登记银行代码必须与当前导入人的登记银行代码一致!
            if (!StringUtil.equals(bankCode, list.get(0))) {
                message.add(this.getMessage("REPORT_COMMON_I_0019"));
            }

            //处理默认联系方式的前置操作
            if(StringUtil.isEmpty(list.get(18))){
                list.set(18, ReportGlobalConstant.DEFAULTPHONE);
            }
            if(StringUtil.isEmpty(list.get(19))){
                list.set(19, ReportGlobalConstant.DEFAULTTEL);
            }
            if(StringUtil.isEmpty(list.get(20))){
                list.set(20, ReportGlobalConstant.DEFAULTEMAIL);
            }
            // EquityInfoRegist001Dto equityRegist =
            // equityInfoRegist001Mapper.queryForEquityInfoRegist(
            // list.get(0), list.get(1));
            try {

                /***核心方法添加备注------------------------------！！！！！！*/
                //与已经报送的数据进行对比，返回变更的数据
                this.getEditMap(list, map, bankCodeMarkList);
                /***核心方法添加备注------------------------------！！！！！！*/

//                this.getEditMap(list, map);
            }
            catch (Exception e) {
//                System.out.println("getEditMap出异常，异常信息：" + e.getMessage());
                logger.error("getEditMap出异常，异常信息：" + e.getMessage());
            }
            //01 新增/02 识别标识变更/03 重要信息变更/04 其他信息变更
            String type = map.get("type");
            //如果为新建但是
//            if ("01".equals(type) && "01".equals(map.get("flag"))) {
//                message.add(this.getMessage("CHB_EQUITYINFO_E_024"));
//            }
//            else
            if ("02".equals(map.get("type02"))) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_027"));
            }
            else if("03".equals(map.get("type02"))){
                message.add(this.getMessage("CHB_EQUITYINFO_E_034",list.get(1)));
                EquityInfoRegist001Dto equityRegist = null;
                if (StringUtil.isNotBlank(list.get(2)) && list.get(2).length() <= 30) {
                    equityRegist = equityInfoRegist001Mapper.queryBatchForEquityInfoSingle(list.get(0),
                            list.get(2));
                }
                if(equityRegist==null){
                    message.add(this.getMessage("原识别标识为{0}的投资者信息未登记！",list.get(2)));
                }
            }
            if ("01".equals(type) || "03".equals(type)) {
                // 投资者是否属于本行
                message = checkIsBelongBank(message, list.get(3), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 投资者归属银行代码
                message = checkTheBankCode(message, list.get(5), type, list.get(3), list.get(0),spzMap,objFiledsMap,equityInfo001Dto);
                // 投资者归属银行名称
                message = checkTheBankName(message, list.get(4), type, list.get(3),spzMap,objFiledsMap,equityInfo001Dto);
                // 权益人境内外标识
                message = checkHolderMark(message, list.get(6), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 投资者类别
                message = checkHolderType(message, list.get(8), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 投资者分类
                message = checkHolderClassify(message, list.get(22), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 个人证件类别验证
                message = checkPersonalIdentifyType(message, list.get(9), type, list.get(8),
                        list.get(6),spzMap,objFiledsMap,equityInfo001Dto);
                // 机构证件类别验证
                message = checkInstitutionInentifyType(message, list.get(10), type, list.get(8),spzMap,objFiledsMap,equityInfo001Dto);
                // 其他证件名称
                message = checkOtherIdentifyName(message, list.get(11), type, list.get(8),
                        list.get(9), list.get(10),spzMap,objFiledsMap,equityInfo001Dto);
                // 证件号码验证
                message = checkInentifyNumber(message, list.get(12), type, list.get(9),
                        list.get(10),spzMap,objFiledsMap,equityInfo001Dto);
                // SPV资金托管账户开户行
                message = checkSpvCapAccBank(message, list.get(13), list.get(8), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 其他资金账户开户行
                message = checkOtherCapAccBank(message, list.get(14), list.get(8), type,
                        list.get(13),spzMap,objFiledsMap,equityInfo001Dto);
                // SPV登记编码 删除该字段
//                message = checkSpvRegistCode(message, list.get(15), list.get(8), type);
                // 投资者名称
                message = checkHolderName(message, list.get(15), type, EquityInfoCheckUtil.subDictcode(list.get(6), " "),spzMap,objFiledsMap,equityInfo001Dto);
                // 性别
                message = checkHolderSex(message, list.get(16), type, list.get(8),spzMap,objFiledsMap,equityInfo001Dto);
                // 风险偏好
                message = checkHolderRiskPrefer(message, list.get(17), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 联系方式   -- 其他信息变更时联系方式及风险偏好不可同时为空
                message = checkContactInfo(message, list.get(18), list.get(19), list.get(20),
                        type,list.get(17),spzMap,objFiledsMap,equityInfo001Dto);
                // 备注
                message = checkRemark(message, list.get(21), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 所属国家或地区
                message = checkHolderRegion(message, type, list.get(7), list.get(6),spzMap,objFiledsMap,equityInfo001Dto);

                //投资者类别校验
                //-----add by lingxq on 2021-05-25 for 投资者类别校验----------->>>>>>
                ChbTypeMappingExMapper chbTypeMappingExMapper = SpringContextUtil.getBean(ChbTypeMappingExMapper.class);
                List<ChbTypeMappingEx>  HoldTypeMappingList = chbTypeMappingExMapper.selectByMappingcode("HOLDTYPE");
                if(!StringUtil.isBlank(list.get(8))){
                    equityInfo001Dto.setHolderType(list.get(8).split(" ")[0]);
                }
                if(!StringUtil.isBlank(list.get(15))) {
                    equityInfo001Dto.setHolderName(list.get(15));
                }
                EquityInfoMaintenanceService.checkHoldType(equityInfo001Dto,HoldTypeMappingList,message);
                //-----add by lingxq on 2021-05-25 for 投资者类别校验 -----------<<<<<<<
            }
            else if ("04".equals(type)) {
                // 风险偏好
                if (StringUtil.isNotEmpty(map.get("HOLDER_RISK_PREFER"))) {
                    message = checkHolderRiskPrefer(message, list.get(17), type,spzMap,objFiledsMap,equityInfo001Dto);
                }
                if (StringUtil.isNotEmpty(map.get("HOLDER_CELLPHONE"))
                        || StringUtil.isNotEmpty(map.get("HOLDER_TELEPHONE"))
                        || StringUtil.isNotEmpty(map.get("HOLDER_EMAIL"))) {
                    message = checkContactInfo(message, list.get(18), list.get(19), list.get(20),
                            type,list.get(17),spzMap,objFiledsMap,equityInfo001Dto);
                }
            }
            else if ("02".equals(type)) {
                // 原识别标识
                message = checkOrgIndentifyMark(message, list.get(2), type, list.get(1),spzMap,objFiledsMap,equityInfo001Dto);
                message = checkExistsIndentifyMark(message,list.get(1));
                if ("01".equals(map.get("type02"))) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_028"));
                }
            }
            try {
                //反馈状态是发送成功不能修改
                List<String> subListUp = new ArrayList<>();
                subListUp.add(list.get(1));
                EquityInfoRegist001Mapper equityInfoRegist001Mapper = SpringContextUtil.getBean(
                        EquityInfoRegist001Mapper.class);
                List<String> submitFeedbackStatusList = equityInfoRegist001Mapper.getSubmitFeedbackStatusList(subListUp);
                if (CollectionUtils.isNotEmpty(submitFeedbackStatusList)) {
                    for (int k = 0; k < submitFeedbackStatusList.size(); k++) {
                        if ("01".equals(submitFeedbackStatusList.get(k))
                                || "02".equals(submitFeedbackStatusList.get(k))
                                || "05".equals(submitFeedbackStatusList.get(k))
                                || StringUtil.isBlank(submitFeedbackStatusList.get(k))) {
                            message.add(this.getMessage("该数据存在中债报送流程中的数据，不允许修改，请稍后再试！"));
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

            // 增加数据类型list
            /** 添加识别标识 */
            map.put("indentify_mark", list.get(1));
            listType.add(map);
            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(list.get(1), String.valueOf(i + 2),
                        StringUtil.joinString(message, "<br>")));
                // listType.add(type);
                // checkDto.add(new EquityCheckValue(type,checkResult));
            }

        }
        return checkResult;
    }


    /**
     * 登记页面数据检查
     *
     * @param equityList 选择的数据
     * @param flag 1 数据检查 2 报送前检查
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public List<ExcelValidDto> checkDataEquity(List<EquityInfoRegist001Dto> equityList,
                                               String flag,
                                               Map<String,TransformRule001Dto> spzMap,
                                               Map<String,List<ChbTransformRuleObjDto>> objFiledsMap) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        // List<EquityCheckValue> checkDto = new ArrayList<>();
        EquityInfoRegist001Dto equityInfoRegist001Dto = null;
        for (int i = 0; i < equityList.size(); i++ ) {
            equityInfoRegist001Dto = equityList.get(i);
            // 检查excel改列是否为空或者是否符合模板规定的列数
            // checkResult = checkExcelCow(i, list, checkResult);
            // 验证消息
            List<String> message = new ArrayList<>();
            // 识别标识验证
            message = checkIndentifyMark(message, equityInfoRegist001Dto.getIndentifyMark(),spzMap,objFiledsMap,null);
            // 登记银行编码
            message = checkBankCode(message, equityInfoRegist001Dto.getBankCode(),spzMap,objFiledsMap,null);
            if (!message.isEmpty()) {
                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            }
            // EquityInfoRegist001Dto equityRegist =
            // equityInfoRegist001Mapper.queryForEquityInfoRegist(
            // list.get(0), list.get(1));
            String type = EquityInfoCheckUtil.subDictcode(equityInfoRegist001Dto.getType(),
                    StringUtil.SPACE);
            if (EquityInfoCheckUtil.isEmpty(type)) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_001", "数据类型"));
            }
            else if (type.length() != 2) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_002", "数据类型", "2"));
            }
            else if (EquityInfoCheckUtil.isDictCodeExist(
                    DictCode.数据类型_中债, type + StringUtil.SPACE
                            + SysDataUtil.getNameFromDataDict(DictCode.数据类型_中债, type))) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_004", "数据类型"));
            }
            else if (DictCodeListExt2.数据类型_中债_新增.getValue().equals(type)
                    || DictCodeListExt2.数据类型_中债_重要信息变更.getValue().equals(type)) {
                // 投资者是否属于本行
                message = checkIsBelongBank(message, equityInfoRegist001Dto.getIsBelongBank(),
                        type,spzMap,objFiledsMap,null);
                // 投资者归属银行代码
                message = checkTheBankCode(message, equityInfoRegist001Dto.getTheBankCode(), type,
                        equityInfoRegist001Dto.getIsBelongBank(),
                        equityInfoRegist001Dto.getBankCode(),spzMap,objFiledsMap,null);
                // 投资者归属银行名称
                message = checkTheBankName(message, equityInfoRegist001Dto.getTheBankName(), type,
                        equityInfoRegist001Dto.getIsBelongBank(),spzMap,objFiledsMap,null);
                // 权益人境内外标识
                message = checkHolderMark(message, equityInfoRegist001Dto.getHolderMark(), type,spzMap,objFiledsMap,null);
                // 投资者类别
                message = checkHolderType(message, equityInfoRegist001Dto.getHolderType(), type,spzMap,objFiledsMap,null);
                // 投资者分类
//                message = checkHolderClassify(message,equityInfoRegist001Dto.getHolderClassify(), type,spzMap,objFiledsMap,null);
                // 个人证件类别验证
                message = checkPersonalIdentifyType(message,
                        equityInfoRegist001Dto.getPersonalIdentifyType(), type,
                        equityInfoRegist001Dto.getHolderType(),
                        equityInfoRegist001Dto.getHolderMark(),spzMap,objFiledsMap,null);
                // 机构证件类别验证
                message = checkInstitutionInentifyType(message,
                        equityInfoRegist001Dto.getInstitutionIdentifyType(), type,
                        equityInfoRegist001Dto.getHolderType(),spzMap,objFiledsMap,null);
                // 其他证件名称
                message = checkOtherIdentifyName(message,
                        equityInfoRegist001Dto.getOtherIdentifyName(), type,
                        equityInfoRegist001Dto.getHolderType(),
                        equityInfoRegist001Dto.getPersonalIdentifyType(),
                        equityInfoRegist001Dto.getInstitutionIdentifyType(),spzMap,objFiledsMap,null);
                // 证件号码验证
                message = checkInentifyNumber(message, equityInfoRegist001Dto.getIdentifyNumber(),
                        type, equityInfoRegist001Dto.getPersonalIdentifyType(),
                        equityInfoRegist001Dto.getInstitutionIdentifyType(),spzMap,objFiledsMap,null);
                // SPV资金托管账户开户行
                message = checkSpvCapAccBank(message, equityInfoRegist001Dto.getSpvCapAccBank(),
                        equityInfoRegist001Dto.getHolderType(), type,spzMap,objFiledsMap,null);
                // 其他资金账户开户行
                message = checkOtherCapAccBank(message,
                        equityInfoRegist001Dto.getOtherCapAccBank(),
                        equityInfoRegist001Dto.getHolderType(), type,
                        equityInfoRegist001Dto.getSpvCapAccBank(),spzMap,objFiledsMap,null);
                // SPV登记编码 删除该字段
//                message = checkSpvRegistCode(message, equityInfoRegist001Dto.getSpvRegistCode(),
//                    equityInfoRegist001Dto.getHolderType(), type);
                // 投资者名称
                message = checkHolderName(message, equityInfoRegist001Dto.getHolderName(), type, equityInfoRegist001Dto.getHolderMark(),spzMap,objFiledsMap,null);
                // 性别
                message = checkHolderSex(message, equityInfoRegist001Dto.getHolderSex(), type,
                        equityInfoRegist001Dto.getHolderType(),spzMap,objFiledsMap,null);
                // 风险偏好
                message = checkHolderRiskPrefer(message,
                        equityInfoRegist001Dto.getHolderRiskPrefer(), type,spzMap,objFiledsMap,null);
                // 联系方式 变更点
                message = checkContactInfo(message, equityInfoRegist001Dto.getHolderCellphone(),
                        equityInfoRegist001Dto.getHolderTelephone(),
                        equityInfoRegist001Dto.getHolderEmail(), type,equityInfoRegist001Dto.getHolderRiskPrefer(),spzMap,objFiledsMap,null);
                // 备注
                message = checkRemark(message, equityInfoRegist001Dto.getRemark(), type,spzMap,objFiledsMap,null);
                // 所属国家或地区
                message = checkHolderRegion(message, type,
                        equityInfoRegist001Dto.getHolderRegion(),
                        equityInfoRegist001Dto.getHolderMark(),spzMap,objFiledsMap,null);
            }
            else if (DictCodeListExt2.数据类型_中债_其他信息变更.getValue().equals(type)) {
                // 风险偏好 变更点
                if (StringUtil.isNotEmpty(equityInfoRegist001Dto.getHolderRiskPrefer())) {
                    message = checkHolderRiskPrefer(message,
                            equityInfoRegist001Dto.getHolderRiskPrefer(), type,spzMap,objFiledsMap,null);
                }
                message = checkContactInfo(message, equityInfoRegist001Dto.getHolderCellphone(),
                        equityInfoRegist001Dto.getHolderTelephone(),
                        equityInfoRegist001Dto.getHolderEmail(), type ,equityInfoRegist001Dto.getHolderRiskPrefer(),spzMap,objFiledsMap,null);
            }
            else if (DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(type)) {
                // 原识别标识
                message = checkOrgIndentifyMark(message,
                        equityInfoRegist001Dto.getOrgIndentifyMark(), type,
                        equityInfoRegist001Dto.getIndentifyMark(),spzMap,objFiledsMap,null);
            }
            if (CollectionUtils.isNotEmpty(message)) {
                ExcelValidDto excelValidDto = new ExcelValidDto(equityInfoRegist001Dto.getIndentifyMark(),
                        String.valueOf(i), StringUtil.joinString(message, "<br>"));
                excelValidDto.setDataTime(equityInfoRegist001Dto.getUpdateTime());
                excelValidDto.setOrgCode(equityInfoRegist001Dto.getOrgCode());
                excelValidDto.setCreateDept(equityInfoRegist001Dto.getCreateDept());
                checkResult.add(excelValidDto);
            }
            else if (ReportGlobalConstant.IS_NUMBER_FLAG.equals(flag)) {
                ExcelValidDto excelValidDto = new ExcelValidDto(equityInfoRegist001Dto.getIndentifyMark(),String.valueOf(i), null);
                excelValidDto.setDataTime(equityInfoRegist001Dto.getUpdateTime());
                excelValidDto.setOrgCode(equityInfoRegist001Dto.getOrgCode());
                excelValidDto.setCreateDept(equityInfoRegist001Dto.getCreateDept());
                checkResult.add(excelValidDto);
            }
        }
        return checkResult;
    }

    /**
     * 批量驗證接口導入
     *
     * @param datalist 解析后的excel
     * @param listType 数据类型list
     * @see
     * @since 1.0
     */
//    public void checkInterfEquity(List<EquityInfo001Dto> datalist, List<String> listType) {
//        for (int i = 0; i < datalist.size(); i++ ) {
//            EquityInfo001Dto equityInfo001Dto = datalist.get(i);
//            // 检查excel改列是否为空或者是否符合模板规定的列数
//            // checkResult = checkExcelCow(i, list, checkResult);
//            // 验证消息
//            List<String> message = new ArrayList<>();
//            // 识别标识验证
//            message = checkIndentifyMark(message, equityInfo001Dto.getIndentifyMark());
//            // 登记银行编码
//            message = checkBankCode(message, equityInfo001Dto.getBankCode());
//            if(StringUtils.equals(equityInfo001Dto.getIndentifyMark(), equityInfo001Dto.getOrgIndentifyMark())){
//                message.add(this.getMessage("CHB_EQUITYINFO_E_033"));
//            }
//            // EquityInfoRegist001Dto equityRegist =
//            // equityInfoRegist001Mapper.queryForEquityInfoRegist(
//            // list.get(0), list.get(1));
////            EquityInfoRegist001Mapper equityInfoRegist001Mapper1 = SpringContextUtil.getBean(
////                EquityInfoRegist001Mapper.class);
//            EquityInfoRegist001Dto equityRegist = equityInfoRegist001Mapper.queryForEquityInfoRegist(
//                equityInfo001Dto.getBankCode(), equityInfo001Dto.getIndentifyMark());
//            String type = getTypeByDto(equityInfo001Dto, equityRegist);
//            // 投资者是否属于本行
//            message = checkIsBelongBank(message, equityInfo001Dto.getIsBelongBank(), type);
//            // 投资者归属银行代码
//            message = checkTheBankCode(message, equityInfo001Dto.getTheBankCode(), type,
//                equityInfo001Dto.getIsBelongBank(), equityInfo001Dto.getBankCode());
//            // 投资者归属银行名称
//            message = checkTheBankName(message, equityInfo001Dto.getTheBankName(), type,
//                equityInfo001Dto.getIsBelongBank());
//            // 原识别标识
//            message = checkOrgIndentifyMark(message, equityInfo001Dto.getOrgIndentifyMark(), type,
//                equityInfo001Dto.getIndentifyMark());
//            // 投资者类别
//            message = checkHolderType(message, equityInfo001Dto.getHolderType(), type);
//            // 权益人境内外标识
//            message = checkHolderMark(message, equityInfo001Dto.getHolderMark(), type);
//            // 个人证件类别验证
//            message = checkPersonalIdentifyType(message,
//                equityInfo001Dto.getPersonalIdentifyType(), type, equityInfo001Dto.getHolderType(),
//                equityInfo001Dto.getHolderMark());
//            // 机构证件类别验证
//            message = checkInstitutionInentifyType(message,
//                equityInfo001Dto.getInstitutionIdentifyType(), type,
//                equityInfo001Dto.getHolderType());
//            // 其他证件名称
//            message = checkOtherIdentifyName(message, equityInfo001Dto.getOtherIdentifyName(),
//                type, equityInfo001Dto.getHolderType(), equityInfo001Dto.getPersonalIdentifyType(),
//                equityInfo001Dto.getInstitutionIdentifyType());
//            // 证件号码验证
//            message = checkInentifyNumber(message, equityInfo001Dto.getIdentifyNumber(), type,
//                equityInfo001Dto.getPersonalIdentifyType(),
//                equityInfo001Dto.getInstitutionIdentifyType());
//            // SPV资金托管账户开户行
//            message = checkSpvCapAccBank(message, equityInfo001Dto.getSpvCapAccBank(),
//                equityInfo001Dto.getHolderType(), type);
//            // 其他资金账户开户行
//            message = checkOtherCapAccBank(message, equityInfo001Dto.getOtherCapAccBank(),
//                equityInfo001Dto.getHolderType(), type, equityInfo001Dto.getSpvCapAccBank());
//            // SPV登记编码
//            message = checkSpvRegistCode(message, equityInfo001Dto.getSpvRegistCode(),
//                equityInfo001Dto.getHolderType(), type);
//            // 投资者名称
//            message = checkHolderName(message, equityInfo001Dto.getHolderName(), type, equityInfo001Dto.getHolderMark());
//            // 性别
//            message = checkHolderSex(message, equityInfo001Dto.getHolderSex(), type,
//                equityInfo001Dto.getHolderType());
//            // 联系方式 变更点
//            message = checkContactInfo(message, equityInfo001Dto.getHolderCellphone(),
//                equityInfo001Dto.getHolderTelephone(), equityInfo001Dto.getHolderEmail(), type ,equityInfo001Dto.getHolderRiskPrefer());
//            // 备注
//            message = checkRemark(message, equityInfo001Dto.getRemark(), type);
//            // 权益人所属国家或地区
//            message = checkHolderRegion(message, type, equityInfo001Dto.getHolderRegion(),
//                equityInfo001Dto.getHolderMark());
//            listType.add(type);
//            if (CollectionUtils.isNotEmpty(message)) {
//                equityInfo001Dto.setStatus("A");
//            }
//            else {
//                equityInfo001Dto.setStatus("E");
//            }
//        }
//    }

    /**
     * 單個驗證接口導入
     *
     * @param equityInfo001Dto 解析后的excel
     * @param registList 报送数据list
     * @param map 集合
     * @param flag 是否接口验证  true 为接口验证
     * @see
     * @since 1.0
     */
    public List<String> checkInterfEquitySingle(EquityInfo001Dto equityInfo001Dto,
                                                List<EquityInfoRegist001Dto> registList,
                                                Map<String, String> map,boolean flag,StringBuffer messageInfo,
                                                Map<String,TransformRule001Dto> spzMap,
                                                Map<String,List<ChbTransformRuleObjDto>> objFiledsMap) {
        // 检查excel改列是否为空或者是否符合模板规定的列数
        // checkResult = checkExcelCow(i, list, checkResult);
        // 验证消息
        List<String> message = new ArrayList<>();
        // 识别标识验证
        message = checkIndentifyMark(message, equityInfo001Dto.getIndentifyMark(),spzMap,objFiledsMap,equityInfo001Dto);
        //存放校验信息
        String checkMess="";
        // 登记银行编码
        message = checkBankCode(message, equityInfo001Dto.getBankCode(),spzMap,objFiledsMap,equityInfo001Dto);
        //add by liubin start 若校验登记银行代码和识别标识有错误，则输出到messageInfo中
        if(message.size() !=0 ){
            for(String tmp : message){
                messageInfo.append(tmp).append("\n");
            }
        }
        //add by liubin end
        if (message.isEmpty()) {
            this.getEditInterf(equityInfo001Dto, registList, map, flag);
            String type = map.get("type");
            //去除该投资者信息未发生改变，无需重新导入！的校验
//            if ("01".equals(type) && "01".equals(map.get("flag"))) {
//                message.add(this.getMessage("CHB_EQUITYINFO_E_024"));
//            }
            if ("02".equals(map.get("type02"))) {
                message.add(this.getMessage("CHB_EQUITYINFO_E_027"));
            }
            else if("03".equals(map.get("type02"))){
                message.add(this.getMessage("CHB_EQUITYINFO_E_034",equityInfo001Dto.getIndentifyMark()));
            }
            if ("01".equals(type) || "03".equals(type)) {
                // 投资者是否属于本行
                message = checkIsBelongBank(message, SysDataUtil.getZZDictCodeAndName(
                        DictCode.是否_中债, equityInfo001Dto.getIsBelongBank()), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 投资者归属银行代码
                message = checkTheBankCode(message, equityInfo001Dto.getTheBankCode(), type,
                        SysDataUtil.getZZDictCodeAndName(DictCode.是否_中债,
                                equityInfo001Dto.getIsBelongBank()),
                        equityInfo001Dto.getBankCode(),spzMap,objFiledsMap,equityInfo001Dto);
                // 投资者归属银行名称
                message = checkTheBankName(message, equityInfo001Dto.getTheBankName(), type,
                        SysDataUtil.getZZDictCodeAndName(DictCode.是否_中债,
                                equityInfo001Dto.getIsBelongBank()),spzMap,objFiledsMap,equityInfo001Dto);
                // 权益人境内外标识
                message = checkHolderMark(message, SysDataUtil.getZZDictCodeAndName(
                        DictCode.境内外, equityInfo001Dto.getHolderMark()), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 投资者类别
                message = checkHolderType(message, SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                        equityInfo001Dto.getHolderType()), type,spzMap,objFiledsMap,equityInfo001Dto);

                    // 投资者分类
                message = checkHolderClassify(message, SysDataUtil.getZZDictCodeAndName(DictCode.投资者分类,
                        equityInfo001Dto.getHolderClassify()), type,spzMap,objFiledsMap,equityInfo001Dto);

                // 个人证件类别验证
                message = checkPersonalIdentifyType(message,
                        SysDataUtil.getZZDictCodeAndName(DictCode.个人证件类别,
                                equityInfo001Dto.getPersonalIdentifyType()),
                        type,
                        SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                                equityInfo001Dto.getHolderType()),
                        SysDataUtil.getZZDictCodeAndName(DictCode.境内外,
                                equityInfo001Dto.getHolderMark()),spzMap,objFiledsMap,equityInfo001Dto);
                // 机构证件类别验证
                message = checkInstitutionInentifyType(message,
                        SysDataUtil.getZZDictCodeAndName(DictCode.机构证件类别,
                                equityInfo001Dto.getInstitutionIdentifyType()),
                        type, SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                                equityInfo001Dto.getHolderType()),spzMap,objFiledsMap,equityInfo001Dto);
                // 其他证件名称
                message = checkOtherIdentifyName(message, equityInfo001Dto.getOtherIdentifyName(),
                        type,
                        SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                                equityInfo001Dto.getHolderType()),
                        SysDataUtil.getZZDictCodeAndName(DictCode.个人证件类别,
                                equityInfo001Dto.getPersonalIdentifyType()),
                        SysDataUtil.getZZDictCodeAndName(DictCode.机构证件类别,
                                equityInfo001Dto.getInstitutionIdentifyType()),spzMap,objFiledsMap,equityInfo001Dto);
                // 证件号码验证
                message = checkInentifyNumber(message, equityInfo001Dto.getIdentifyNumber(), type,
                        SysDataUtil.getZZDictCodeAndName(DictCode.个人证件类别,
                                equityInfo001Dto.getPersonalIdentifyType()),
                        SysDataUtil.getZZDictCodeAndName(DictCode.机构证件类别,
                                equityInfo001Dto.getInstitutionIdentifyType()),spzMap,objFiledsMap,equityInfo001Dto);
                // SPV资金托管账户开户行
                message = checkSpvCapAccBank(message,
                        SysDataUtil.getZZDictCodeAndName(DictCode.SPV资金托管账户开户行,
                                equityInfo001Dto.getSpvCapAccBank()),
                        SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                                equityInfo001Dto.getHolderType()),
                        type,spzMap,objFiledsMap,equityInfo001Dto);
                // 其他资金账户开户行
                message = checkOtherCapAccBank(message, equityInfo001Dto.getOtherCapAccBank(),
                        SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                                equityInfo001Dto.getHolderType()),
                        type, equityInfo001Dto.getSpvCapAccBank(),spzMap,objFiledsMap,equityInfo001Dto);
                // SPV登记编码
                //修改去除SPV登记编码
                /*message = checkSpvRegistCode(message, equityInfo001Dto.getSpvRegistCode(),
                    SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                        equityInfo001Dto.getHolderType()),
                    type);*/
                // 投资者名称
                message = checkHolderName(message, equityInfo001Dto.getHolderName(), type, equityInfo001Dto.getHolderMark(),spzMap,objFiledsMap,equityInfo001Dto);
                // message = checkSpvRegistCode(message, equityInfo001Dto.getSpvRegistCode(),
                // SysDataUtil.getZZDictCodeAndName(DictCode.权益人类别,
                // equityInfo001Dto.getHolderType()),
                // type);
                // 性别
                message = checkHolderSex(message,
                        SysDataUtil.getZZDictCodeAndName(DictCode.性别_中债, equityInfo001Dto.getHolderSex()),
                        type, SysDataUtil.getZZDictCodeAndName(DictCode.投资者类别,
                                equityInfo001Dto.getHolderType()),spzMap,objFiledsMap,equityInfo001Dto);
                // 风险偏好
                message = checkHolderRiskPrefer(message, SysDataUtil.getZZDictCodeAndName(
                        DictCode.投资风险偏好, equityInfo001Dto.getHolderRiskPrefer()), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 联系方式 变更点
//                message = checkContactInfo(message, equityInfo001Dto.getHolderCellphone(),
//                    equityInfo001Dto.getHolderTelephone(), equityInfo001Dto.getHolderEmail(),
//                    type,equityInfo001Dto.getHolderCellphone());
                message = checkContactInfoByInterf(message, equityInfo001Dto, type, flag);
                // 备注
                message = checkRemark(message, equityInfo001Dto.getRemark(), type,spzMap,objFiledsMap,equityInfo001Dto);
                // 所属国家或地区
                message = checkHolderRegion(message, type,
                        SysDataUtil.getZZDictCodeAndName(DictCode.所属国家或地区,
                                equityInfo001Dto.getHolderRegion()),
                        SysDataUtil.getZZDictCodeAndName(DictCode.境内外,
                                equityInfo001Dto.getHolderMark()),spzMap,objFiledsMap,equityInfo001Dto);

            }
            else if ("04".equals(type)) {
                // 风险偏好
                if (StringUtil.isNotEmpty(map.get("HOLDER_RISK_PREFER"))) {
                    message = checkHolderRiskPrefer(message, SysDataUtil.getZZDictCodeAndName(
                            DictCode.投资风险偏好, equityInfo001Dto.getHolderRiskPrefer()), type,spzMap,objFiledsMap,equityInfo001Dto);
                }
                if (StringUtil.isNotEmpty(map.get("HOLDER_CELLPHONE"))
                        || StringUtil.isNotEmpty(map.get("HOLDER_TELEPHONE"))
                        || StringUtil.isNotEmpty(map.get("HOLDER_EMAIL"))) {
//                    message = checkContactInfo(message, equityInfo001Dto.getHolderCellphone(),
//                        equityInfo001Dto.getHolderTelephone(), equityInfo001Dto.getHolderEmail(),
//                        type,equityInfo001Dto.getHolderCellphone());
                    message = checkContactInfoByInterf(message, equityInfo001Dto, type, flag);
                }
            }
            else if ("02".equals(type)) {
                // 原识别标识
                message = checkOrgIndentifyMark(message, equityInfo001Dto.getOrgIndentifyMark(),
                        type, equityInfo001Dto.getIndentifyMark(),spzMap,objFiledsMap,equityInfo001Dto);
                message = checkExistsIndentifyMark(message,equityInfo001Dto.getIndentifyMark());
                if ("01".equals(map.get("type02"))) {
                    message.add(this.getMessage("CHB_EQUITYINFO_E_028"));
                }
            }
            //投资者类别校验
            //-----add by lingxq on 2021-05-25 for 投资者类别校验----------->>>>>>
            ChbTypeMappingExMapper chbTypeMappingExMapper = SpringContextUtil.getBean(ChbTypeMappingExMapper.class);
            List<ChbTypeMappingEx>  HoldTypeMappingList = chbTypeMappingExMapper.selectByMappingcode("HOLDTYPE");
            EquityInfoMaintenanceService.checkHoldType(equityInfo001Dto,HoldTypeMappingList,message);
            //-----add by lingxq on 2021-05-25 for 投资者类别校验 -----------<<<<<<<
            equityInfo001Dto.setType(type);
        }
        else {
            equityInfo001Dto.setType("03");
        }
        if (CollectionUtils.isNotEmpty(message)) {
            equityInfo001Dto.setStatus("A");
            //打印识别标识，以及meassge内容
//            System.out.println("识别标识为："+ equityInfo001Dto.getIndentifyMark());
            logger.error("识别标识为：{}" , equityInfo001Dto.getIndentifyMark());
            for (String mes:message) {

                checkMess+=mes;


//                System.out.println("message 信息如下："+mes);
                logger.info("message 信息如下："+mes);
                logger.error("message 信息如下：{}" , mes);
            }
        }
        else {
            equityInfo001Dto.setStatus("E");
        }
        //验证消息存入数据库
        if(checkMess.length()==0) {
            if("001".equals(CacheUtil.getSysParam("CUSTOMER_BANK_CODE"))){
                checkMess="";
            }else{
                checkMess="数据完整";
            }
        }
        else if (checkMess.getBytes().length>600) {
            checkMess=checkMess.substring(0, 200);
        }

        equityInfo001Dto.setInterfCheckResult(checkMess);

        return message;
    }
    /**
     * 获取最新状态的不是删除状态的已中债通过数据
     * @param bankCode 登记银行代码
     * @param indentifyMark 识别标识
     * @param list          结果list
     */
    private void getIsSubmitList(String bankCode,String indentifyMark,List<EquityInfoRegist001Dto> list){
        List<EquityInfoRegist001Dto> submitList = equityInfoRegist001Mapper.queryEquityInfoByOrg(
                bankCode, indentifyMark);
        for (EquityInfoRegist001Dto equityInfoRegist001Dto : submitList) {
            if(!StringUtil.equals(DictCodeListExt2.报送数据状态_删除.getValue(), equityInfoRegist001Dto.getStatus())){
                list.add(equityInfoRegist001Dto);
            }
        }
    }

    /**
     * 查询 导入文件中 bankCode and indentifyMark 在数据库中存在的Dto
     *
     * @param dataList
     * @return
     */
    private List<EquityInfoRegist001Dto> getAllForEquityInfo(List<List<String>> dataList) {
        // 银行编码 列表
        List<String> bankCodeList = new ArrayList<>();
        // 识别标识 列表
        List<String> indentifyMarkList = new ArrayList<>();
        // 查询参数
        Map<String, Object> paramMap = new HashMap<>();

        List<EquityInfoRegist001Dto> equityInfoRegist001DtoList = null;
        List<EquityInfoRegist001Dto> equityInfoRegist001DtoListTemp = null;
        // 所有 bankCode indentifyMark 列表
        for (int i = 0; i < dataList.size(); i++) {
            bankCodeList.add(dataList.get(i).get(0));
            indentifyMarkList.add(dataList.get(i).get(1));
        }

        int dataCount = dataList.size();
        // 是否需要分批
        if (dataCount < 1000) {
            paramMap.put("bankCodeList", bankCodeList);
            paramMap.put("indentifyMarkList", indentifyMarkList);
            equityInfoRegist001DtoList = equityInfoRegist001Mapper.queryAllForEquityInfo(paramMap);
            paramMap = null;
        } else {
            // 分批处理
            int part = dataCount / 1000;
            // 分批数
            for (int i = 0; i < part; i++) {
                paramMap.put("bankCodeList", bankCodeList.subList(0, 1000));
                paramMap.put("indentifyMarkList", indentifyMarkList.subList(0, 1000));
                equityInfoRegist001DtoListTemp = equityInfoRegist001Mapper.queryAllForEquityInfo(paramMap);
                bankCodeList.subList(0, 1000).clear();
                indentifyMarkList.subList(0, 1000).clear();
                paramMap = null;
                // 追加临时列表的数据
                equityInfoRegist001DtoList.addAll(equityInfoRegist001DtoListTemp);
            }
            // 剩下的数据
            if (CollectionUtils.isNotEmpty(bankCodeList) && CollectionUtils.isNotEmpty(indentifyMarkList)) {
                paramMap.put("bankCodeList", bankCodeList);
                paramMap.put("indentifyMarkList", indentifyMarkList);
                equityInfoRegist001DtoListTemp = equityInfoRegist001Mapper.queryAllForEquityInfo(paramMap);
                // 追加临时列表的数据
                equityInfoRegist001DtoList.addAll(equityInfoRegist001DtoListTemp);
            }
        }
        return equityInfoRegist001DtoList;
    }

}