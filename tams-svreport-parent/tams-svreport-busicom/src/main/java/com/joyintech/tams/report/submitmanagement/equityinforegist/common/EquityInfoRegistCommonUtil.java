/*
 * 文件名：EquityInfoRegistCommonUtil.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：Administrator
 * 修改时间：2017年10月26日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitmanagement.equityinforegist.common;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author Administrator
 * @version 1.0 2017年10月26日
 * @see EquityInfoRegistCommonUtil
 * @since 1.0
 */
public class EquityInfoRegistCommonUtil {
    /**
     * 格式数据字典
     * <p>
     * 将数据字典的值转化为名称，用于画面显示
     * </p>
     *
     * @param dataList 数据列表
     */
    public static void format(List<EquityInfoRegist001Dto> dataList) {
        if (null != dataList) {
            dataList.forEach(item -> {
                //给隐藏列赋值
                //item.setSubmitStatusKey(item.getSubmitStatus());
                //将数据字典转换为value
                item.setStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
                item.setSubmitStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送状态,item.getSubmitStatus()));
//                item.setSubmitFeedbackStatus(SysDataUtil.getNameFromDataDict(DictCode.报送反馈状态, item.getSubmitFeedbackStatus()));
                item.setType(SysDataUtil.getNameFromDataDict(DictCode.数据类型_中债 , item.getType()));
                item.setHolderRegion(SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, item.getHolderRegion()));
                item.setIsBelongBank(SysDataUtil.getNameFromDataDict(DictCode.是否_中债, item.getIsBelongBank()));
                item.setSpvCapAccBank(SysDataUtil.getNameFromDataDict(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));
                item.setHolderMark(SysDataUtil.getNameFromDataDict(DictCode.境内外, item.getHolderMark()));
                //         equityInfoRegist001Dto.setHolderRegion(SysDataUtil.getNameFromDataDict(dictCode.权益人, itemCode));
                item.setHolderType(SysDataUtil.getNameFromDataDict(DictCode.投资者类别, item.getHolderType()));
                if(StringUtil.isNotEmpty(item.getPersonalIdentifyType())){
                    item.setIdentifyType(SysDataUtil.getNameFromDataDict(DictCode.个人证件类别, item.getPersonalIdentifyType()));
                }
                else if(StringUtil.isNotEmpty(item.getInstitutionIdentifyType())){
                    item.setIdentifyType(SysDataUtil.getNameFromDataDict(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
                }
                item.setHolderSex(SysDataUtil.getNameFromDataDict(DictCode.性别_中债, item.getHolderSex()));
//                item.setHolderRiskPrefer(SysDataUtil.getNameFromDataDict(DictCode.风险偏好, item.getHolderRiskPrefer()));
            });
        }
    }

    /**
     * 格式数据字典
     * <p>
     * 将数据字典的值转化为字典 + +名称，用于excel显示
     * </p>
     *
     * @param dataList 数据列表
     */
    public static void format(List<EquityInfoRegist001Dto> dataList,String style) {
        if (null != dataList) {
            dataList.forEach(item -> {
                //item.setSubmitStatusKey(item.getSubmitStatus());
                item.setStatusName(getDictCodeAndName(DictCode.报送数据状态, item.getStatus()));
                item.setSubmitStatusName(getDictCodeAndName(DictCode.报送状态,item.getSubmitStatus()));
                item.setSubmitFeedbackStatus(getDictCodeAndName(DictCode.报送反馈状态, item.getSubmitFeedbackStatus()));
                item.setHolderRegion(getDictCodeAndName(DictCode.所属国家或地区, item.getHolderRegion()));
                item.setType(getDictCodeAndName(DictCode.数据类型_中债 , item.getType()));
                item.setIsBelongBank(getDictCodeAndName(DictCode.是否_中债, item.getIsBelongBank()));
                item.setHolderMark(getDictCodeAndName(DictCode.境内外, item.getHolderMark()));
                //         equityInfoRegist001Dto.setHolderRegion(SysDataUtil.getNameFromDataDict(dictCode.权益人, itemCode));
                item.setHolderType(getDictCodeAndName(DictCode.投资者类别, item.getHolderType()));
                item.setPersonalIdentifyType(getDictCodeAndName(DictCode.个人证件类别, item.getPersonalIdentifyType()));
                item.setInstitutionIdentifyType(getDictCodeAndName(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
                item.setHolderSex(getDictCodeAndName(DictCode.性别_中债, item.getHolderSex()));
                item.setHolderRiskPrefer(getDictCodeAndName(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
                item.setSpvCapAccBank(getDictCodeAndName(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));

                if (StringUtil.isNotBlank(item.getPersonalIdentifyType())) {
                    item.setIdentifyType(item.getPersonalIdentifyType());
                }
                else {
                    item.setIdentifyType(item.getInstitutionIdentifyType());
                }
            });
        }
    }
    /**
     * 格式数据字典
     * <p>
     * 将数据字典的值转化为字典 + +名称，用于excel显示
     * </p>
     *
     * @param dataList 数据列表
     */
    public static void formatDto(EquityInfoRegist001Dto item,String style) {
        item.setStatusName(getDictCodeAndName(DictCode.报送数据状态, item.getStatus()));
        item.setSubmitStatusName(getDictCodeAndName(DictCode.报送状态,item.getSubmitStatus()));
        item.setSubmitFeedbackStatusCode(item.getSubmitFeedbackStatus());
        item.setSubmitFeedbackStatus(getDictCodeAndName(DictCode.报送反馈状态, item.getSubmitFeedbackStatus()));
        if(DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(item.getType())||
                DictCodeListExt2.数据类型_中债_其他信息变更.getValue().equals(item.getType())){
            setDtoNull(item,true);
        }
        else{
            item.setHolderRegion(getDictCodeAndName(DictCode.所属国家或地区, item.getHolderRegion()));
            item.setIsBelongBank(getDictCodeAndName(DictCode.是否_中债, item.getIsBelongBank()));
            item.setHolderMark(getDictCodeAndName(DictCode.境内外, item.getHolderMark()));
            item.setHolderType(getDictCodeAndName(DictCode.投资者类别, item.getHolderType()));
            item.setPersonalIdentifyType(getDictCodeAndName(DictCode.个人证件类别, item.getPersonalIdentifyType()));
            item.setInstitutionIdentifyType(getDictCodeAndName(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
            item.setHolderSex(getDictCodeAndName(DictCode.性别_中债, item.getHolderSex()));
            item.setHolderRiskPrefer(getDictCodeAndName(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
            item.setSpvCapAccBank(getDictCodeAndName(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));

            //当不为识别标识变更时，原识别标识必须为空
            item.setOrgIndentifyMark("");
        }
        item.setType(getDictCodeAndName(DictCode.数据类型_中债 , item.getType()));
    }

    /**
     * 转换成key+value形式
     * @param dictCode  字典表中数据类型
     * @param value  需要换换的值
     * @return  key+value
     * @see
     * @since 1.0
     */
    public static String getDictCodeAndName(DictCode dictCode,String value){
        if(StringUtil.isNotEmpty(value)){
            String tempName = SysDataUtil.getNameFromDataDict(dictCode, value);
            if (StringUtil.isEmpty(tempName)) {
                return value;
            } else {
                return value + " " + SysDataUtil.getNameFromDataDict(dictCode, value);
            }
        }
        return "";
    }
    public static List<EquityInfoRegist001Dto> formatByType(List<EquityInfoRegist001Dto> dataList,boolean isChb) {
        //当按照中债需要转换时
        if(CollectionUtils.isNotEmpty(dataList)){
            if(isChb){
                dataList.forEach(item -> {
                    item.setTypeCode(item.getType());
                    item.setStatusName(getDictCodeAndName(DictCode.报送数据状态, item.getStatus()));
                    item.setSubmitStatusName(getDictCodeAndName(DictCode.报送状态,item.getSubmitStatus()));
                    item.setSubmitFeedbackStatusCode(item.getSubmitFeedbackStatus());
                    item.setSubmitFeedbackStatus(getDictCodeAndName(DictCode.报送反馈状态, item.getSubmitFeedbackStatus()));
                    if(DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(item.getType())||
                            DictCodeListExt2.数据类型_中债_其他信息变更.getValue().equals(item.getType())){
                        setDtoNull(item,isChb);
                    }
                    else{
                        item.setHolderRegion(getDictCodeAndName(DictCode.所属国家或地区, item.getHolderRegion()));
                        item.setIsBelongBank(getDictCodeAndName(DictCode.是否_中债, item.getIsBelongBank()));
                        item.setHolderMark(getDictCodeAndName(DictCode.境内外, item.getHolderMark()));
                        item.setHolderType(getDictCodeAndName(DictCode.投资者类别, item.getHolderType()));
                        item.setPersonalIdentifyType(getDictCodeAndName(DictCode.个人证件类别, item.getPersonalIdentifyType()));
                        item.setInstitutionIdentifyType(getDictCodeAndName(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
                        item.setHolderSex(getDictCodeAndName(DictCode.性别_中债, item.getHolderSex()));
                        item.setHolderRiskPrefer(getDictCodeAndName(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
                        item.setSpvCapAccBank(getDictCodeAndName(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));

                        if (StringUtil.isNotBlank(item.getPersonalIdentifyType())) {
                            item.setIdentifyType(item.getPersonalIdentifyType());
                        }
                        else {
                            item.setIdentifyType(item.getInstitutionIdentifyType());
                        }
                        //当不为识别标识变更时，原识别标识必须为空
                        item.setOrgIndentifyMark("");
                    }
                    item.setType(getDictCodeAndName(DictCode.数据类型_中债 , item.getType()));
                });
            }
            else{
                dataList.forEach(item -> {
                    item.setTypeCode(item.getType());
                    item.setStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
                    item.setSubmitStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送状态,item.getSubmitStatus()));
                    if(DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(item.getType())||
                            DictCodeListExt2.数据类型_中债_其他信息变更.getValue().equals(item.getType())){
                        setDtoNull(item,isChb);
                    }
                    else{
                        item.setHolderRegion(SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, item.getHolderRegion()));
                        item.setIsBelongBank(SysDataUtil.getNameFromDataDict(DictCode.是否_中债, item.getIsBelongBank()));
                        item.setSpvCapAccBank(SysDataUtil.getNameFromDataDict(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));
                        item.setHolderMark(SysDataUtil.getNameFromDataDict(DictCode.境内外, item.getHolderMark()));
                        item.setHolderType(SysDataUtil.getNameFromDataDict(DictCode.投资者类别, item.getHolderType()));
                        if(StringUtil.isNotEmpty(item.getPersonalIdentifyType())){
                            item.setIdentifyType(SysDataUtil.getNameFromDataDict(DictCode.个人证件类别, item.getPersonalIdentifyType()));
                        }
                        else if(StringUtil.isNotEmpty(item.getInstitutionIdentifyType())){
                            item.setIdentifyType(SysDataUtil.getNameFromDataDict(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
                        }
                        item.setHolderSex(SysDataUtil.getNameFromDataDict(DictCode.性别_中债, item.getHolderSex()));
                        item.setOrgIndentifyMark("");
                    }
                    item.setType(SysDataUtil.getNameFromDataDict(DictCode.数据类型_中债 , item.getType()));
                    item.setHolderRiskPrefer(SysDataUtil.getNameFromDataDict(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
                });
            }
        }
        return dataList;
    }


    public static List<EquityInfoRegist001Dto> formatForEquHoldSearchByType(List<EquityInfoRegist001Dto> dataList,boolean isChb) {
        //当按照中债需要转换时
        if(CollectionUtils.isNotEmpty(dataList)){
            if(isChb){
                dataList.forEach(item -> {
                    item.setTypeCode(item.getType());
                    item.setStatusName(getDictCodeAndName(DictCode.报送数据状态, item.getStatus()));
                    item.setSubmitStatusName(getDictCodeAndName(DictCode.报送状态,item.getSubmitStatus()));
                    item.setSubmitFeedbackStatusCode(item.getSubmitFeedbackStatus());
                    item.setSubmitFeedbackStatus(getDictCodeAndName(DictCode.报送反馈状态, item.getSubmitFeedbackStatus()));
//                    if(DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(item.getType())||
//                            DictCodeListExt2.数据类型_中债_其他信息变更.getValue().equals(item.getType())){
//                        setDtoNull(item,isChb);
//                    }
//                    else{
                        item.setHolderRegion(getDictCodeAndName(DictCode.所属国家或地区, item.getHolderRegion()));
                        item.setIsBelongBank(getDictCodeAndName(DictCode.是否_中债, item.getIsBelongBank()));
                        item.setHolderMark(getDictCodeAndName(DictCode.境内外, item.getHolderMark()));
                        item.setHolderType(getDictCodeAndName(DictCode.投资者类别, item.getHolderType()));
                        item.setPersonalIdentifyType(getDictCodeAndName(DictCode.个人证件类别, item.getPersonalIdentifyType()));
                        item.setInstitutionIdentifyType(getDictCodeAndName(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
                        item.setHolderSex(getDictCodeAndName(DictCode.性别_中债, item.getHolderSex()));
                        item.setHolderRiskPrefer(getDictCodeAndName(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
                        item.setSpvCapAccBank(getDictCodeAndName(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));

                        if (StringUtil.isNotBlank(item.getPersonalIdentifyType())) {
                            item.setIdentifyType(item.getPersonalIdentifyType());
                        }
                        else {
                            item.setIdentifyType(item.getInstitutionIdentifyType());
                        }
                        //当不为识别标识变更时，原识别标识必须为空
                        item.setOrgIndentifyMark("");
//                    }
                    item.setType(getDictCodeAndName(DictCode.数据类型_中债 , item.getType()));
                });
            }
            else{
                dataList.forEach(item -> {
                    item.setTypeCode(item.getType());
                    item.setStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送数据状态, item.getStatus()));
                    item.setSubmitStatusName(SysDataUtil.getNameFromDataDict(DictCode.报送状态,item.getSubmitStatus()));
//                    if(DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(item.getType())||
//                            DictCodeListExt2.数据类型_中债_其他信息变更.getValue().equals(item.getType())){
//                        setDtoNull(item,isChb);
//                    }
//                    else{
                        item.setHolderRegion(SysDataUtil.getNameFromDataDict(DictCode.所属国家或地区, item.getHolderRegion()));
                        item.setIsBelongBank(SysDataUtil.getNameFromDataDict(DictCode.是否_中债, item.getIsBelongBank()));
                        item.setSpvCapAccBank(SysDataUtil.getNameFromDataDict(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));
                        item.setHolderMark(SysDataUtil.getNameFromDataDict(DictCode.境内外, item.getHolderMark()));
                        item.setHolderType(SysDataUtil.getNameFromDataDict(DictCode.投资者类别, item.getHolderType()));
                        if(StringUtil.isNotEmpty(item.getPersonalIdentifyType())){
                            item.setIdentifyType(SysDataUtil.getNameFromDataDict(DictCode.个人证件类别, item.getPersonalIdentifyType()));
                        }
                        else if(StringUtil.isNotEmpty(item.getInstitutionIdentifyType())){
                            item.setIdentifyType(SysDataUtil.getNameFromDataDict(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
                        }
                        item.setHolderSex(SysDataUtil.getNameFromDataDict(DictCode.性别_中债, item.getHolderSex()));
                        item.setOrgIndentifyMark("");
//                    }
                    item.setType(SysDataUtil.getNameFromDataDict(DictCode.数据类型_中债 , item.getType()));
                    item.setHolderRiskPrefer(SysDataUtil.getNameFromDataDict(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
                });
            }
        }
        return dataList;
    }
    /**
     * 投资者身份登记页面处理
     * @param item 投资者身份登记信息
     */
    private static void  setDtoNull(EquityInfoRegist001Dto item,boolean isChb){
        if(DictCodeListExt2.数据类型_中债_识别标识变更.getValue().equals(item.getType())){
            item.setHolderCellphone("");
            item.setHolderEmail("");
            item.setHolderRiskPrefer("");
            item.setHolderTelephone("");
        }
        else if(isChb){
            item.setHolderRiskPrefer(getDictCodeAndName(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
            item.setOrgIndentifyMark("");
        }
        else if(DictCodeListExt2.数据类型_中债_其他信息变更.getValue().equals(item.getType())){
            item.setOrgIndentifyMark("");
        }
        item.setHolderMark("");
        item.setHolderName("");
        item.setHolderRegion("");
        item.setHolderSex("");
        item.setHolderType("");
        item.setIdentifyNumber("");
        item.setInstitutionIdentifyType("");
        item.setIsBelongBank("");
        item.setOtherCapAccBank("");
        item.setOtherIdentifyName("");
        item.setPersonalIdentifyType("");
        item.setSpvCapAccBank("");
        item.setSpvRegistCode("");
        item.setTheBankCode("");
        item.setTheBankName("");
        item.setIdentifyType("");
        item.setRemark("");
    }
    /**
     * 格式数据字典
     * <p>
     * 将数据字典的值转化为字典 + +名称，用于excel显示
     * </p>
     *
     * @param dataList 数据列表
     */
    public static void formatDtoAll(EquityInfoRegist001Dto item) {
        item.setStatusName(getDictCodeAndName(DictCode.报送数据状态, item.getStatus()));
        item.setSubmitStatusName(getDictCodeAndName(DictCode.报送状态,item.getSubmitStatus()));
        item.setSubmitFeedbackStatusCode(item.getSubmitFeedbackStatus());
        item.setSubmitFeedbackStatus(getDictCodeAndName(DictCode.报送反馈状态, item.getSubmitFeedbackStatus()));
        item.setHolderRegion(getDictCodeAndName(DictCode.所属国家或地区, item.getHolderRegion()));
        item.setIsBelongBank(getDictCodeAndName(DictCode.是否_中债, item.getIsBelongBank()));
        item.setHolderMark(getDictCodeAndName(DictCode.境内外, item.getHolderMark()));
        item.setHolderType(getDictCodeAndName(DictCode.投资者类别, item.getHolderType()));
        item.setPersonalIdentifyType(getDictCodeAndName(DictCode.个人证件类别, item.getPersonalIdentifyType()));
        item.setInstitutionIdentifyType(getDictCodeAndName(DictCode.机构证件类别, item.getInstitutionIdentifyType()));
        item.setHolderSex(getDictCodeAndName(DictCode.性别_中债, item.getHolderSex()));
        item.setHolderRiskPrefer(getDictCodeAndName(DictCode.投资风险偏好, item.getHolderRiskPrefer()));
        item.setSpvCapAccBank(getDictCodeAndName(DictCode.SPV资金托管账户开户行, item.getSpvCapAccBank()));
        item.setType(getDictCodeAndName(DictCode.数据类型_中债 , item.getType()));
    }
}
