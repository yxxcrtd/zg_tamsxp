/*
 * 文件名：EquityInfo002Service.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：天津滨海农商行理财资管系统
 * 修改人：dingzhipeng
 * 修改时间：2018年5月17日
 * 修改内容：新建
 * 系统名称：天津滨海农商行理财资管系统
 */

package com.joyintech.tams.report.submitmaintenance.equityinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbEquSubMapper;
import com.joyintech.tams.common.base.dao.ChbTypeMappingExMapper;
import com.joyintech.tams.common.base.entity.ChbTypeMappingEx;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dao.EquityInfo001Mapper;
import com.joyintech.tams.report.submitmaintenance.equityinfo.dto.EquityInfo001Dto;
import com.joyintech.tams.report.submitmaintenance.equityinfo.validate.EquityInfoValid;
import com.joyintech.tams.report.submitmaintenance.transformrule.dao.ChbTransformRuleObjDtoMapper;
import com.joyintech.tams.report.submitmaintenance.transformrule.dao.TransformRule001Mapper;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.ChbTransformRuleObjDto;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.TransformRule001Dto;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dao.EquityInfoRegist001Mapper;
import com.joyintech.tams.report.submitmanagement.equityinforegist.dto.EquityInfoRegist001Dto;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2018年5月17日
 * @see EquityInfo002Service
 * @since 1.0
 */
@Service
public class EquityInfo002Service extends BaseService{
    /**
     * check工具类
     */
    @Autowired
    private EquityInfoValid equityInfoValid;
    /**
     * 001
     */
    @Autowired
    private EquityInfo001Service equityInfo001Service;

    /**
     * 001
     */
    @Autowired
    private EquityInfoRegist001Mapper equityInfoRegist001Mapper;
    
    @Autowired
    private EquityInfo001Mapper equityInfo001Mapper;
    @Autowired
    private TransformRule001Mapper transformRule001Mapper;
    @Autowired
    private ChbTransformRuleObjDtoMapper chbTransformRuleObjDtoMapper;

    /**
     * subDao
     */
    @SuppressWarnings("unused")
    @Autowired
    private ChbEquSubMapper chbEquSubMapper;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     *
     * <一句话功能简述>
     * <功能详细描述>
     * @param dto 待验证数据
     * @param isSave 是否保存
     * @return
     * @see
     * @since 1.0
     */
    public AjaxResult checkOrSave(EquityInfo001Dto dto,boolean isSave) {
        if(StringUtil.isEmpty(dto.getBankCode())){
            // dto.setBankCode(CommonUtil.getUserBankCode());//注释，微服务不能获取登录session
        }
        if(StringUtil.isEmpty(dto.getHolderEmail())){
            dto.setHolderEmail(ReportGlobalConstant.DEFAULTEMAIL);
        }
        if(StringUtil.isEmpty(dto.getHolderCellphone())){
            dto.setHolderCellphone(ReportGlobalConstant.DEFAULTPHONE);
        }
        if(StringUtil.isEmpty(dto.getHolderTelephone())){
            dto.setHolderTelephone(ReportGlobalConstant.DEFAULTTEL);
        }
        List<Map<String, String>> list =new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("REGIST_BANK_CODE", dto.getBankCode());
        param.put("INDENTIFY_MARK", dto.getIndentifyMark());
        list.add(param);
//        List<EquityInfoRegist001Dto> registList = equityInfoRegist001Mapper.queryBatchForEquityInfoRegist(
//            list);
        List<EquityInfoRegist001Dto> registList = equityInfoRegist001Mapper.queryBatchForEquityInfoConStrast(
              list);
        Map<String,String>map=new HashMap<>();
        if(StringUtil.equals(DictCodeList.境内外_境内.getValue(), dto.getHolderMark())
        		&&StringUtil.isNotEmpty(dto.getHolderName())){
        	dto.setHolderName(dto.getHolderName().replace(StringUtil.SPACE, StringUtil.EMPTY));
        }
        //add by liubin start
        StringBuffer messageInfo = new StringBuffer();
        //add by liubin end
        Map<String, Object> paramap=new HashMap<String, Object>();
        paramap.put("statusList", Arrays.asList("E"));
        List<TransformRule001Dto> allList=transformRule001Mapper.selectAllData(paramap);
        Map<String,TransformRule001Dto> spzMap=allList.stream().collect(
            Collectors.toMap(x ->x.getTransType()+x.getOrgField(),part->part,(k1,k2)->k1));
        List<ChbTransformRuleObjDto> allRuleObjList=chbTransformRuleObjDtoMapper.selectAllDataByTableId("CHB_EQU_HOLD");
        Map<String,List<ChbTransformRuleObjDto>> objFiledsMap=allRuleObjList.stream().collect(
            JoyinCollectors.groupingBy(
                x -> x.getInfoProperty() ));
        List<String> message = equityInfoValid.checkInterfEquitySingle(dto,registList,map, false,messageInfo,spzMap,objFiledsMap);
        if(StringUtil.equals(dto.getIsChangeId(),"01")){
         // check识别标识是否已经存在
            Map<String,String>temp=new HashMap<>();
            temp.put("REGIST_BANK_CODE",dto.getBankCode());
            temp.put("INDENTIFY_MARK",dto.getIndentifyMark());
            try {
                if(equityInfoRegist001Mapper.countIndentifyMarkAll(temp)>0){
                    message.add(this.getMessage("REPORT_E_0016"));
                }
            }
            catch (Exception e) {
//                System.out.println("equityInfoRegist001Mapper.countIndentifyMark出异常，异常信息：" + e.getMessage());
                logger.error("equityInfoRegist001Mapper.countIndentifyMark出异常，异常信息：{}",e.getMessage());
            }
        }
        else if(!StringUtil.equals(dto.getIsChangeId(),"03")){
        	Map<String,String>temp=new HashMap<>();
            temp.put("REGIST_BANK_CODE",dto.getBankCode());
            temp.put("INDENTIFY_MARK",dto.getIndentifyMark());
            try {
                if(equityInfoRegist001Mapper.countIndentifyMark(temp) > 0){
                    message.add(this.getMessage("REPORT_E_0017", dto.getIndentifyMark()));
                }
            }
            catch (Exception e) {
//                System.out.println("equityInfoRegist001Mapper.countIndentifyMark出异常，异常信息：" + e.getMessage());
                logger.error("equityInfoRegist001Mapper.countIndentifyMark出异常，异常信息：{}",e.getMessage());
            }
        }
        EquityInfoDistinctService equityInfoDistinctService = 
                SpringContextUtil.getBean(EquityInfoDistinctService.class);
        List<EquityInfo001Dto> equityList1 =new ArrayList<EquityInfo001Dto>();
        equityList1.add(dto) ;
        List<String> keys = equityInfoDistinctService.getKeys(equityList1);
        equityInfoDistinctService.checkSendedData(equityList1,keys,"02");
        equityInfoDistinctService.checkAllData(equityList1,keys,"02");
        if(equityList1.isEmpty()){
        	 message.add("识别标识不可用！该投资者存在多个识别标识。");
        }
        AjaxResult result = new AjaxResult();
        if(CollectionUtils.isNotEmpty(message)){
            List<ValidMessage> errorList = new ArrayList<>();
            message.forEach(action->{
                errorList.add(new ValidMessage(action));
                });
//            errorList.add(new ValidMessage("识别标识重复"));
            result.setErrorList(errorList);
            //当新建的的身份信息主键一样，提示是否覆盖
//            result.setMessage("识别标识重复", MessageType.错误消息);
        }
        else if(isSave){
            List<Map<String, String>> listType = new ArrayList<>();
            listType.add(map);
            List<EquityInfo001Dto> equityList = new ArrayList<>();
            //【中债三期优化--投资者身份信息--列表】数据检查结果自动填充错误
            dto.setInterfCheckResult(null);
            equityList.add(dto);
            //modify by wangmin 合DB2版本代码，添加数据源 begin 2018-07-23
            //equityInfo001Service.dealAfterCheck(listType, equityList);
            equityInfo001Service.dealAfterCheck(listType, equityList, DictCodeListExt2.数据来源_系统新建.getValue(),dto.getDeptCode(),dto.getOrgCode());
            //modify by wangmin 合DB2版本代码，添加数据源 end 2018-07-23
        }
        return result;
    }
    /**
     * 获取entity实例
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    public EquityInfo001Dto getEntity(Map<String, Object> map) {
        String key = map.get("bankCode").toString()+GlobalConstant.VERTICAL_LINE+map.get("indentifyMark").toString();
        List<EquityInfo001Dto> dtoList = equityInfo001Service.selectInfoByKey(key,false);
        if(dtoList.isEmpty()){
            return null;
        }
        else{
            return dtoList.get(0);
        }
    }
    /**
     * 判断该数据是否已经报送完成
     * <功能详细描述>
     * @param dto 待校验数据
     * @return 标记
     * @see
     * @since 1.0
     */
    public String confirmSubmit(EquityInfo001Dto dto) {
        EquityInfoRegist001Dto regist = equityInfoRegist001Mapper.queryForEquityInfoRegist(dto.getBankCode(), dto.getIndentifyMark());
        if(!(regist==null)){
            return DictCodeList.是否_中债_是.getValue();
        }
        return DictCodeList.是否_中债_否.getValue();
    }
    
    
    public void excuteEquityInfoRefresh(){
    	//找出所有不通过的身份信息
    	List<EquityInfo001Dto> equityInfoList=equityInfo001Mapper.selectEquityInfoForTrans();
    	if(!StringUtil.isEmptyList(equityInfoList)){
    		//遍历待处理数据
    		for(EquityInfo001Dto item:equityInfoList){
    			item.setIsChangeId("03");
    			this.checkOrSave(item,true);
    		}
    	}
    }

}