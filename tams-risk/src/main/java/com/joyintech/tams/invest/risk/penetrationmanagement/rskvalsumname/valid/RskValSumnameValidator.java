/*
 * 文件名：RskValSumnameValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月17日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.valid;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.dto.RskValSumnameDto;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.service.RskValSumname001Service;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.service.RskValSumname002Service;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalsumname.service.RskValSumname003Service;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author qiangchengxi
 * @version 1.0 2018年12月17日
 * @see RskValSumnameValidator
 * @since 1.0
 */
@Component
public class RskValSumnameValidator implements BeanValidator{
    @Autowired
    private RskValSumname001Service rskValSumname001Service;
    @Autowired
    private RskValSumname002Service rskValSumname002Service;
    @Autowired
    private RskValSumname003Service rskValSumname003Service;
    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        RskValSumnameDto dto = (RskValSumnameDto)value;
        ArrayList<ValidMessage> errorList = new ArrayList<>();
        if (WorkFlowUtil.isNotFromToDoList()) {
            if (StringUtil.equals(dto.getOperateType(), "delete")) {
                //判断是映射删除还是映射明细删除明细
                if(dto.getSumCode() != null && !StringUtil.equals(dto.getSumCode(), "")){
                    //查询映射明细
                    RskValSumnameDto entity = rskValSumname003Service.selectByKey(dto);
                    if(entity == null || entity.getId() == null){
                        errorList.add(new ValidMessage(getMessage("BUSS_COM_E_0022")));
                    }
                }
                else{
                    //查询映射
                    RskValSumnameDto entity = rskValSumname002Service.selectById(dto);
                    if(entity == null || entity.getId() == null){
                        errorList.add(new ValidMessage(getMessage("BUSS_COM_E_0022")));
                    }
                }
              //选中记录在业务表中已关联组合时
                if(rskValSumname001Service.getCountById(dto)){
                    errorList.add(new ValidMessage(this.getMessage("RSK_W_0001",getMessage("RskValSumname001.id"),dto.getId())));
                }
                if (rskValSumname001Service.getAssetSumInfoIsDefault(dto)) {
                    errorList.add(new ValidMessage(this.getMessage("RSK_W_00775", getMessage("RskValStructure001.id"), dto.getId())));
                }
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)&&!StringUtil.equals(dto.getOperateType(), "save")) {
                if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)&&StringUtil.equals(dto.getOperateType(), "submit")){
                    if(dto.getSumName() == null || "".equals(dto.getSumName())){
                        errorList.add(new ValidMessage(getMessage("FW_E_0006",getMessage("RskValSumname003.sumName"))));
                    }
                    if(dto.getSumCode() == null || "".equals(dto.getSumCode())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname003.sumCode"))));
                    }
                    if(dto.getHasNextIndex() == null || "".equals(dto.getHasNextIndex())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname003.hasNextIndex"))));
                    }
                    else{
                        if(DictCodeList.是否_是.getValue().equals(dto.getHasNextIndex()) &&
                            (dto.getNextIndexType() == null || "".equals(dto.getNextIndexType()))){
                            errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname003.nextIndexType"))));
                        }
                    }
                    /*if(dto.getTgtIndex() == null || "".equals(dto.getTgtIndex())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname003.tgtIndex"))));
                    }*/
                  //查询映射明细,目标汇总科目代码不能重复
                    RskValSumnameDto entity = rskValSumname003Service.selectByKey(dto);
                    if(entity != null && entity.getId() != null){
                        errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValSumname003.sumCode"))));
                    }
                  //查询映射明细,源汇总科目名称不能重复
                    RskValSumnameDto e = rskValSumname003Service.selectByName(dto);
                    if(e != null && e.getId() != null){
                        errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValSumname003.sumName"))));
                    }
                }
                else{
                    if (!StringUtil.isNotEmpty(dto.getId())) {
                        errorList.add(new ValidMessage(getMessage("RSK_W_0037")));
                    }
                }
                
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)&&StringUtil.equals(dto.getOperateType(), "save")) {
                if(dto.getName() == null || "".equals(dto.getName())){
                    errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname002.name"))));
                }
                List<RskValSumnameDto> listDto = rskValSumname002Service.selectByName(dto.getName());
                if(listDto != null && listDto.size()>0){
                    errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValSumname002.name"))));
                }
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)&&!StringUtil.equals(dto.getOperateType(), "save")) {
                if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)&&StringUtil.equals(dto.getOperateType(), "submit")){
                    if(dto.getSumName() == null || "".equals(dto.getSumName())){
                        errorList.add(new ValidMessage(getMessage("FW_E_0006",getMessage("RskValSumname003.sumCode"))));
                    }
                    if(dto.getSumCode() == null || "".equals(dto.getSumCode())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname003.sumCode"))));
                    }
                    if(dto.getHasNextIndex() == null || "".equals(dto.getHasNextIndex())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname003.hasNextIndex"))));
                    }
                    else{
                        if(DictCodeList.是否_是.getValue().equals(dto.getHasNextIndex()) &&
                            (dto.getNextIndexType() == null || "".equals(dto.getNextIndexType()))){
                            errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname003.nextIndexType"))));
                        }
                    }
                    RskValSumnameDto entity = rskValSumname003Service.selectByName(dto);
                    if(entity != null && entity.getId() != null){
                        if(!StringUtil.equals(entity.getSumCode(), dto.getSumCode())){
                            errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValSumname003.sumName"))));
                        }
                    }
                }
                else{
                    RskValSumnameDto entity = rskValSumname003Service.selectByKey(dto);
                    if(entity == null || entity.getId() == null){
                        errorList.add(new ValidMessage(getMessage("BUSS_COM_E_0022")));
                    }
                }
                
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)&&StringUtil.equals(dto.getOperateType(), "save")) {
                if(dto.getName() == null || "".equals(dto.getName())){
                    errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValSumname002.name"))));
                }
                List<RskValSumnameDto> listDto = rskValSumname002Service.selectByName(dto.getName());
                if(listDto != null && listDto.size() >0){
                    if(listDto.size() != 1 || !StringUtil.equals(listDto.get(0).getId(), dto.getId())){
                        errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValSumname002.name"))));
                    }
                }
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)) {
                //选中记录在业务表中已关联组合时
                if(rskValSumname001Service.getCountById(dto)){
                    errorList.add(new ValidMessage(this.getMessage("RSK_W_00773",getMessage("RskValSumname001.id"),dto.getId())));
                }
                if (rskValSumname001Service.getAssetSumInfoIsDefault(dto)) {
                    errorList.add(new ValidMessage(this.getMessage("RSK_W_00774",getMessage("RskValSumname001.id"),dto.getId())));
                }
            }
        }
        return errorList;
    }

}
