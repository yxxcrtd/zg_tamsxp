package com.joyintech.service.chb3.submitmanagement.salestransactionmaintenanceregist.controller;

import com.joyintech.framework.annotation.DTXTransaction;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.ChbBatchParaMapper;
import com.joyintech.tams.common.base.entity.ChbBatchPara;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.dto.SalesTransactionMaintenanceRegist001Dto;
import com.joyintech.tams.report.submitmanagement.salestransactionmaintenanceregist.service.SalesTransactionMaintenanceRegist001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 *销售资金交易信息登记
 */
@RestController
@RequestMapping("/SalesTransactionMaintenanceRegist")
public class SalesTransactionMaintenanceRegistController {
    /**
     * 001Service
     */
    @Autowired
    private SalesTransactionMaintenanceRegist001Service salesTransactionMaintenanceRegist001Service;

    @Autowired
    private ChbBatchParaMapper chbBatchParaMapper;
    /**
     *
     * 查询功能
     * <功能详细描述>
     * @return 存储了查询结果的list
     * @see
     * @since 1.0
     */
    @PostMapping("findPage/{page}/{length}")
    public Page<SalesTransactionMaintenanceRegist001Dto> search(@RequestBody Map<String, Object> param){
        return salesTransactionMaintenanceRegist001Service.searchForPage(param);
    }

    /**
     * 手工处理功能
     *
     * @return 提示信息
     * @param map 条件map
     * @seea
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("updateSumStaus")
    public void updateSumStaus(@RequestBody Map<String, Object> map) {
        salesTransactionMaintenanceRegist001Service.updateSumStaus(map);
    }

    /**
     * 取消手工处理功能
     *
     * @return 提示信息
     * @param map 条件map
     * @seea
     * @since 1.0
     */
    @Transactional
    @DTXTransaction
    @PostMapping("cancelMannul")
    public void cancelMannul(@RequestBody Map<String, Object> map) {
        salesTransactionMaintenanceRegist001Service.cancelMannul(map);
    }

    /**
     * checkIsNotSubmit
     * @return 提示信息
     * @see
     * @since 1.0
     */
    @PostMapping("checkIsNotSubmit")
    public List<ValidMessage> checkIsNotSubmit(@RequestBody String upKey) {
    	return salesTransactionMaintenanceRegist001Service.checkIsNotSubmit(upKey);
    }

    @PostMapping("queryForInfoRegist")
    public List<SalesTransactionMaintenanceRegist001Dto> queryForInfoRegist(@RequestBody Map<String,Object> param){
    	return salesTransactionMaintenanceRegist001Service.queryForInfoRegist(param);
    }

    /**
     * 报送检查
     * @return 检查结果
     * @see
     * @since 1.0
     */
    @PostMapping("submitBeforeCheck")
    public List<ExcelValidDto> submitBeforeCheck(@RequestBody Map<String,Object> map) {
        return salesTransactionMaintenanceRegist001Service.submitBeforeCheck(map);
    }
}
