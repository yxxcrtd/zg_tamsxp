package com.joyintech.tams.report.submitmaintenance.transformrule.valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.report.submitmaintenance.equityinfo.util.EquityInfoCheckUtil;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.TransformRule001Dto;
import com.joyintech.tams.report.submitmaintenance.transformrule.service.TransformRule002Service;

@Transactional
@Component
public class TransformruleValidator implements BeanValidator {
	/**
	 * bean
	 */
	@Autowired
	private TransformRule002Service transformRule002Service;
	
	
	/**
     * 白名单验证
     */
    public static final String PATTERN_ALL = "^[\\u4e00-\\u9fef-\\u0020-\\u0026-\\u0028-\\u007B-\\u007D-\\u007E-\\u2000-\\u2040-\\u2160-\\u216B-\\u3000-\\u301F-\\u3400-\\u4DB5-\\uE815-\\uE86F-\\uF900-\\uFAD9-\\uFF00-\\uFF65-\\uFFE0-\\uFFEF-\\u00B7]{0,}$";


	@Override
	public List<ValidMessage> valid(Object value) {
		TransformRule001Dto dto = (TransformRule001Dto) value;
		ArrayList<ValidMessage> errorList = new ArrayList<>();

		if(StringUtil.equals(dto.getOperateType(), "edit")){
			//判断有没有修改，根据传过来的seqno查原来的值，和传过来的值比较
			TransformRule001Dto oldDto=transformRule002Service.getDto(dto.getSeqno());
			if(!Objects.equals(dto.getTargetField(),oldDto.getTargetField())){
				if (StringUtil.isNotEmpty(dto.getTargetField())) {
					int count = transformRule002Service.checkData(dto.getOrgField());
					if (count > 1) {
						errorList.add(new ValidMessage(this.getMessage("CHB_TRANS_ORGFIELD")));
					}
					if(EquityInfoCheckUtil.findPatern(PATTERN_ALL, dto.getTargetField())){
						errorList.add(new ValidMessage(this.getMessage("CHB_TRANS_TARGETFIELD")));
					}
				}
			}

		}else if(StringUtil.equals(dto.getOperateType(), "save")){
			if (StringUtil.isNotEmpty(dto.getTargetField())) {
				int count = transformRule002Service.checkData(dto.getOrgField());
				if (count > 0) {
					errorList.add(new ValidMessage(this.getMessage("CHB_TRANS_ORGFIELD")));
				}
				if(EquityInfoCheckUtil.findPatern(PATTERN_ALL, dto.getTargetField())){
					errorList.add(new ValidMessage(this.getMessage("CHB_TRANS_TARGETFIELD")));
				}
			}
		}
		return errorList;
	}
}
