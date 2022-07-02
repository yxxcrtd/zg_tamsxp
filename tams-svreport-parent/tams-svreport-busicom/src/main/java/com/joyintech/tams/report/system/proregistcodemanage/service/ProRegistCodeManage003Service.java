package com.joyintech.tams.report.system.proregistcodemanage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.joyintech.background.component.common.pack.common.util.BeanUtil;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.entity.ChbPrdresistCodeKey;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dao.ChbPrdDeclareInfoMapper;
import com.joyintech.tams.svreport.interf.chb.proddeclare.dto.ChbPrdDeclareInfoDto;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.ChbPrdresistCodeMapper;
import com.joyintech.tams.common.base.entity.ChbPrdresistCode;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.submitmaintenance.investorsholdinfo.service.InvestorsHoldInfo001Service;
import com.joyintech.tams.report.system.proregistcodemanage.dao.ProRegistCodeManage001Mapper;
import com.joyintech.tams.report.system.proregistcodemanage.dto.ProRegistCodeManage001Dto;
import com.joyintech.tams.report.system.proregistcodemanage.dto.ProRegistCodeManage002Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;

/**
 * 产品登记编码管理
 * 〈功能详细描述〉
 * @author tangliang
 * @version 1.0 2017年9月20日
 * @see ProRegistCodeManage001Service
 * @since 1.0
 */
@Service
public class ProRegistCodeManage003Service extends CommonExportService<ProRegistCodeManage002Dto> implements BaseProcessService<ProRegistCodeManage002Dto>{

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

    @Autowired
    private ChbPrdDeclareInfoMapper chbPrdDeclareInfoMapper;
    /**
     * InvestorsHoldInfo001Service
     */
    @Autowired
    private InvestorsHoldInfo001Service investorsHoldInfo001Service;
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 实现工作流doComplete方法
     */
    public void doComplete(ProRegistCodeManage002Dto entity, OperationType type) {
    	    entity.setOperateType(type.toString());
    	    ChbPrdresistCode record = new ChbPrdresistCode();
    	    record.setProdid(entity.getProdId());
    	    record.setProdname(entity.getProdName());
    	    record.setRegCode(entity.getRegCode());    	    
    	    record.setVdate(entity.getVdate());
    	    record.setMdate(entity.getMdate());
    	    record.setOperateMode(entity.getOperateMode());
    	    record.setProdInsId(entity.getProdInsId());
    	   /* if(StringUtil.equals(entity.getOperateMode(), "封闭式净值型")) {   	    	
    	    }else if(StringUtil.equals(entity.getOperateMode(), "封闭式非净值型")) {
    	    	record.setOperateMode("02");
    	    }else if(StringUtil.equals(entity.getOperateMode(), "开放式净值型")) {
    	    	record.setOperateMode("03");
    	    }else if(StringUtil.equals(entity.getOperateMode(), "开放式非净值型")) {
    	    	record.setOperateMode("04");
    	    }*/
    	    record.setOrgCode(CommonUtil.getOrgCode());
        	record.setDeptCode(CommonUtil.getDeptCode());
    		if (StringUtil.equals(entity.getOperateType(), "NEW")) {
    			record.setCreateUser(SecurityUtil.getLoginUser().getUserName());
    			record.setCreateTime(new Date());
    			//
    			 record.setFlag("2");
    			 record.setReg_code(record.getRegCode());
    			 //产品要素维护管理中产品代码不存在 存在则更新数据              
                ChbPrdresistCode dto = chbPrdresistCodeMapper.selectByPrimaryKey(record);
                if (dto != null){
                    chbPrdresistCodeMapper.updateByPrimaryKeySelective(record);
                }
                else{
                    //不存在插入新数据
                    chbPrdresistCodeMapper.insertSelective(record);
                }
    			//
    			
            }
    		else if(StringUtil.equals(entity.getOperateType(), "MODIFY")) {
    			record.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
    			record.setUpdateTime(new Date());
    			chbPrdresistCodeMapper.updateByPrimaryKeySelective(record);
    		}
    		
    }
  
    public ProRegistCodeManage002Dto selectInfo(String prodId) {
    	String svrSystemType=SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_PRDRESIST_CODE);
    	if(svrSystemType!=null&&svrSystemType.equals("01")){
    		return proRegistCodeManage001Mapper.selectInfo(prodId);
		}else{
			return proRegistCodeManage001Mapper.selectInfoN(prodId);
		}
    }
    
    @SuppressWarnings("unchecked")
   	public Map<String,Object> selectId(Map<String, Object> pageParam) {
           return proRegistCodeManage001Mapper.selectId(pageParam);
       }
    
    public Map<String,Object> selectCode(Map<String, Object> pageParam) {
           return proRegistCodeManage001Mapper.selectCode(pageParam);
       }
    
    
    public AjaxResult checkInsertData(String prodInsId,String regCode ) {
    	AjaxResult webResult = new AjaxResult();    
    	ChbPrdresistCode record = new ChbPrdresistCode();
    	record.setProdInsId(prodInsId);
    	record.setFlag("2");
		record.setReg_code(regCode);
		ChbPrdresistCode dto = chbPrdresistCodeMapper.selectByPrimaryKey(record);
		if(dto != null) {			
                    webResult.setMessage(
                        "产品登记编码已维护，请确认是否覆盖？",
                        MessageType.确认消息);
                }
               /* else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }*/            
           /* else{
             // 防止在进行导入前check时进行了两次的弹框
             webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
            }*/
        return webResult;
    }
    
    public AjaxResult checkInsertData1(String prodInsId,String regCode ) {
    	AjaxResult webResult = new AjaxResult();
        List<ValidMessage> errList = new ArrayList<ValidMessage>();

        //从产品申报信息中查询数据
        ChbPrdDeclareInfoDto dto = proRegistCodeManage001Mapper.selectInfoByProdId(prodInsId);
        if(null!=dto){
            if(StringUtil.equals(dto.getRaiseType(), DictCodeListExt2.募集方式_中债_公募.getValue())){
                //公募产品
                if(!Pattern.compile("^[0-9a-zA-Z]{6}[0-9]{2}[0-9]{6}$").matcher(regCode).matches()){
                    errList.add(new ValidMessage("公募产品产品登记编码格式不符合规范。"));

                }
            }else if(StringUtil.equals(dto.getRaiseType(), DictCodeListExt2.募集方式_中债_私募.getValue())){
                //私募产品
                if(!Pattern.compile("^[0-9a-zA-Z]{6}[0-9]{2}[A]{1}[0-9]{6}$").matcher(regCode).matches()){
                    errList.add(new ValidMessage("私募产品产品登记编码格式不符合规范。"));

                }
            }
        }
        /*if(!Pattern.compile("^[0-9a-zA-Z]{6}[0-9]{2}[ABC]?[0-9]{6}$").matcher(regCode).matches()){
    		 webResult.setMessage(
                     "产品登记编码格式不符合规范，请确认是否继续操作？",
                     MessageType.确认消息);
        }  */
        webResult.setData(errList);
        return webResult;
    }

    public Page<SelectOptionDto> getProdInsId(Map<String, Object> param) {
        return queryForPageForSelectReturn(Integer.parseInt(null == param.get("page")?"1":(String) param.get("page")), Integer.parseInt(null == (String) param.get("length")?"1":(String) param.get("length")),()->chbPrdDeclareInfoMapper.getProdInsId(param));
    }

    public String getProdNameByProdInsId(String prodInsId) {
        String result ="";
        try {
            result= chbPrdDeclareInfoMapper.getProdNameByProdInsId(prodInsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ProRegistCodeManage002Dto selectByProdInsId(String prodInsId) {
        ChbPrdresistCodeKey key = new ChbPrdresistCodeKey();
        key.setProdInsId(prodInsId);
        key.setFlag("2");
        ChbPrdresistCode  chbPrdresistCode  = chbPrdresistCodeMapper.selectByPrimaryKey(key);
        ProRegistCodeManage002Dto dto = new ProRegistCodeManage002Dto();
        if(chbPrdresistCode!=null){
            dto.setProdId(chbPrdresistCode.getProdid());
            dto.setProdName(chbPrdresistCode.getProdname());
            dto.setProdInsId(chbPrdresistCode.getProdInsId());
            dto.setRegCode(chbPrdresistCode.getRegCode());
            dto.setOrgCode(chbPrdresistCode.getOrgCode());
            dto.setOperateMode(chbPrdresistCode.getOperateMode());
        }
        return dto;
    }

    public int checkRegCode(String regCode) {
        return proRegistCodeManage001Mapper.checkRegCode(regCode);
    }

    public int selectRegCode(String prodInsId,String regCode) {
        return proRegistCodeManage001Mapper.selectRegCode(prodInsId,regCode);
    }
}