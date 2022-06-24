package com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.validate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joyintech.background.component.common.check.DataCheckCommonUtil;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.BeanValidator;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.dto.SalesTransactionMaintenanceDto;
import com.joyintech.tams.report.submitmaintenance.salestransactionmaintenance.service.SalesTransactionMaintenance002Service;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.ChbTransformRuleObjDto;
import com.joyintech.tams.report.submitmaintenance.transformrule.dto.TransformRule001Dto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;


@Component
public class SalesTransactionMaintenanceValid implements BeanValidator {
    /**
     * 获取service对象
     */
    @Autowired
    private SalesTransactionMaintenance002Service salestransactionmaintenance002Service;

    @Autowired
    private DataCheckCommonUtil chbDataCheckCommonUtil;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ValidMessage> valid(Object value) {
        SalesTransactionMaintenanceDto dto = (SalesTransactionMaintenanceDto)value;
        List<ValidMessage> errorList = new ArrayList<ValidMessage>();
        
        //调用数据库配置脚本check方法
        errorList = chbDataCheckCommonUtil.chbCheckCommon(errorList, dto, "SalesTransactionMaintenance002", null, ValidMessage.class);

            if (dto.getTradeTime() != null && dto.getTradeTime() != "") {
                if (dto.getTradeTime().length() != 6) {
                    errorList.add(new ValidMessage("", "交易时间填写错误（例23:59:59填写235959）"));
                }else if (!checkFigure(dto.getTradeTime())){
                    errorList.add(new ValidMessage("", "交易时间填写错误（例23:59:59填写235959）"));
                }
                if(dto.getTradeTime().length() == 6 && checkFigure(dto.getTradeTime())) {
                    if(!checkFigure(dto.getTradeTime().substring(0, 1))){
                        errorList.add(new ValidMessage("", "交易时间填写错误（例23:59:59填写235959）"));
                    }
                    else if (Integer.parseInt(dto.getTradeTime().substring(0, 2)) > 23
                            || Integer.parseInt(dto.getTradeTime().substring(2, 3)) > 5 || Integer.parseInt(dto.getTradeTime().substring(4, 5)) > 5) {
                        errorList.add(new ValidMessage("", "交易时间填写错误（例23:59:59填写235959）"));
                    }
                }
            }

            if("0".equals(dto.getPageModel()) || ("1".equals(dto.getPageModel()) && "01".equals(dto.getMaintainStatus())))
            if(dto.getInstitutionCode() != null && dto.getInstitutionCode() != "" && dto.getPrdRegistCode() != null && dto.getPrdRegistCode() != ""
               && dto.getAccountNo() != null && dto.getAccountNo() != "" && dto.getTradeNo() != null && dto.getTradeNo() != ""){
                if(salestransactionmaintenance002Service.selectTradeNo(dto.getInstitutionCode(),dto.getPrdRegistCode(),dto.getAccountNo(),dto.getTradeNo()) != 0){
                    errorList.add(new ValidMessage("", "提供销售结算资金服务机构代码、产品登记编码与销售结算资金账号相同时，销售结算资金交易流水号不能重复。"));
                }
            }
        return errorList;
    }

    /**
     * 登记页面数据检查
     *
     * @param registList 选择的数据
     * @param flag 1 数据检查 2 报送前检查
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public static List<ExcelValidDto> checkData(List<SalesTransactionMaintenanceRegist001Dto> registList,
                                                String flag,
                                                Map<String, TransformRule001Dto> spzMap,
                                                Map<String, List<ChbTransformRuleObjDto>> objFiledsMap) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        SalesTransactionMaintenanceRegist001Dto salesTransactionMaintenanceRegist001Dto = null;
        for (int i = 0; i < registList.size(); i++ ) {
            salesTransactionMaintenanceRegist001Dto = registList.get(i);
            // 验证消息
            List<String> message = new ArrayList<>();
            if (!message.isEmpty()) {
                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            }
            if (CollectionUtils.isNotEmpty(message)) {
                ExcelValidDto excelValidDto = new ExcelValidDto(salesTransactionMaintenanceRegist001Dto.getTradeNo(),
                        String.valueOf(i), StringUtil.joinString(message, "<br>"));
                excelValidDto.setDataTime(salesTransactionMaintenanceRegist001Dto.getUpdateTime());
                excelValidDto.setOrgCode(salesTransactionMaintenanceRegist001Dto.getOrgCode());
                excelValidDto.setCreateDept(salesTransactionMaintenanceRegist001Dto.getCreateDept());
                checkResult.add(excelValidDto);
            }
            else if (StringUtil.equals(ReportGlobalConstant.IS_NUMBER_FLAG,flag)) {
                ExcelValidDto excelValidDto = new ExcelValidDto(salesTransactionMaintenanceRegist001Dto.getTradeNo(),String.valueOf(i), null);
                excelValidDto.setDataTime(salesTransactionMaintenanceRegist001Dto.getUpdateTime());
                excelValidDto.setOrgCode(salesTransactionMaintenanceRegist001Dto.getOrgCode());
                excelValidDto.setCreateDept(salesTransactionMaintenanceRegist001Dto.getCreateDept());
                checkResult.add(excelValidDto);
            }
        }
        return checkResult;
    }

    /**
     * 判断是否为数字
     *
     * @param
     * @return 返回boolean
     */
    private boolean checkFigure(String figure) {
        try{
            new BigDecimal(figure);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
