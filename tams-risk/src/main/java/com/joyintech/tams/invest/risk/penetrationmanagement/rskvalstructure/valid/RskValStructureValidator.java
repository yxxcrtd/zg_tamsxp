/*
 * 文件名：RskValStructureValidator.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：qiangchengxi
 * 修改时间：2018年12月10日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.valid;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.invest.common.util.ExcelUtilForRisk;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.dto.RskValStructureDto;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.service.RskValStructure003Service;
import com.joyintech.tams.invest.risk.penetrationmanagement.rskvalstructure.service.RskValStructureService;
import com.joyintech.workflow.engine.util.WorkFlowUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author wangzehui
 * @version 1.0 2018年12月10日
 * @see RskValStructureValidator
 * @since 1.0
 */
@Component
public class RskValStructureValidator implements BeanValidator{
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(RskValStructureValidator.class);

    /**
     * rskValStructureService
     */
    @Autowired
    private RskValStructureService rskValStructureService;
    @Autowired
    private RskValStructure003Service rskValStructure003Service;
    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        RskValStructureDto dto = (RskValStructureDto)value;
        ArrayList<ValidMessage> errorList = new ArrayList<>();
        if (WorkFlowUtil.isNotFromToDoList()) {
            if (StringUtil.equals(dto.getOperateType(), "delete")) {
                //判断是映射删除还是映射明细删除明细
                if(dto.getSrcColIndex() != null && !StringUtil.equals(dto.getSrcColIndex(), "")){
                    //查询映射明细
                    RskValStructureDto entity = rskValStructure003Service.select(dto);
                    if(entity == null || entity.getId() == null){
                        errorList.add(new ValidMessage(getMessage("BUSS_COM_E_0022")));
                    }
                }
                else{
                    //查询映射
                    RskValStructureDto entity = rskValStructureService.selectById(dto);
                    if(entity == null || entity.getId() == null){
                        errorList.add(new ValidMessage(getMessage("BUSS_COM_E_0022")));
                    }
                }
                //选中记录在业务表中已关联组合时
                if(rskValStructureService.getCountById(dto)){
                    errorList.add(new ValidMessage(this.getMessage("RSK_W_0001",getMessage("RskValStructure001.id"),dto.getId())));
                }
                //选中记录是默认配置时
                if (rskValStructureService.getStructInfoIsDefault(dto)) {
                    errorList.add(new ValidMessage(this.getMessage("RSK_W_00775", getMessage("RskValStructure001.id"), dto.getId())));
                }
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)&&!StringUtil.equals(dto.getOperateType(), "save")) {
                if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)&&StringUtil.equals(dto.getOperateType(), "submit")){
                    //add by 张年   2019/07/31
                    try {
                        if(StringUtil.isEmpty(dto.getSrcColIndexLetter())){
                            errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValStructure003.srcColIndex"))));
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                    }
                    //add by 张年   2019/07/31
                    /*if(dto.getSrcColIndex() == null || "".equals(dto.getSrcColIndex())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValStructure003.srcColIndex"))));
                    }*/
                    if(dto.getSrcColDataType() == null || "".equals(dto.getSrcColDataType())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValStructure003.srcColDataType"))));
                    }
                    if(dto.getTgtIndex() == null || "".equals(dto.getTgtIndex())){
                        errorList.add(new ValidMessage(this.getMessage("FW_E_0006",getMessage("RskValStructure003.tgtIndex"))));
                    }
                  //查询映射明细
                    try {
                        dto.setSrcColIndex(String.valueOf(ExcelUtilForRisk.columnToIndex(dto.getSrcColIndexLetter())));
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                    }
                    RskValStructureDto entity = rskValStructure003Service.select(dto);
                    if(entity != null && entity.getId() != null){
                        errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValStructure003.srcColIndex"))));
                    }
                }
                else{
                    if (!StringUtil.isNotEmpty(dto.getId())) {
                        errorList.add(new ValidMessage(getMessage("RSK_W_0024")));
                    }
                }
                
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_NEW)&&StringUtil.equals(dto.getOperateType(), "save")) {
                if(dto.getStructName() == null || "".equals(dto.getStructName())){
                    errorList.add(new ValidMessage(getMessage("FW_E_0006",getMessage("RskValStructure002.structName"))));
                }
                List<RskValStructureDto> listDto = rskValStructureService.selectByName(dto.getStructName());
                if(listDto != null && listDto.size()>0){
                    errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValStructure002.structName"))));
                }
            }
            //add by yangwenjun 控制列长度 start
            try {
				if(StringUtil.isNotEmpty(dto.getSrcColIndexLetter()) && dto.getSrcColIndexLetter().length()>20) {
					errorList.add(new ValidMessage(getMessage("列过长","")));
				}
			} catch (Exception e) {
                logger.error(e.getMessage(),e);
			}
            //add by yangwenjun 控制列长度 end
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)&&!StringUtil.equals(dto.getOperateType(), "save")) {
                if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)&&StringUtil.equals(dto.getOperateType(), "submit")){
                    if(dto.getSrcColDataType() == null || "".equals(dto.getSrcColDataType())){
                        errorList.add(new ValidMessage(getMessage("FW_E_0006",getMessage("RskValStructure003.srcColDataType"))));
                    }
                    if(dto.getTgtIndex() == null || "".equals(dto.getTgtIndex())){
                        errorList.add(new ValidMessage(getMessage("FW_E_0006",getMessage("RskValStructure003.tgtIndex"))));
                    }
                }
                else{
                    RskValStructureDto entity = rskValStructure003Service.select(dto);
                    if(entity == null || entity.getId() == null){
                        errorList.add(new ValidMessage(getMessage("BUSS_COM_E_0022")));
                    }
                }
                
            }
            if (StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)&&StringUtil.equals(dto.getOperateType(), "save")) {
                List<RskValStructureDto> listDto = rskValStructureService.selectByName(dto.getStructName());
                if(listDto != null && listDto.size() >0){
                    if(listDto.size() != 1 || !StringUtil.equals(listDto.get(0).getId(), dto.getId())){
                        errorList.add(new ValidMessage(getMessage("RSK_W_0025",getMessage("RskValStructure002.structName"))));
                    }
                }
                if(dto.getStructName() == null || "".equals(dto.getStructName())){
                    errorList.add(new ValidMessage(getMessage("FW_E_0006",getMessage("RskValStructure002.structName"))));
                }
            }
        }
        if(StringUtil.equals(dto.getPageModel(), GlobalConstant.DETAIL_PAGE_MODEL_EDIT)){
            //选中记录在业务表中已关联组合时
            if(rskValStructureService.getCountById(dto)){
                errorList.add(new ValidMessage(this.getMessage("RSK_W_00773",getMessage("RskValStructure001.id"),dto.getId())));
            }
            if (rskValStructureService.getStructInfoIsDefault(dto)) {
                errorList.add(new ValidMessage(this.getMessage("RSK_W_00774", getMessage("RskValStructure001.id"), dto.getId())));
            }
        }
        return errorList;
    }

}
