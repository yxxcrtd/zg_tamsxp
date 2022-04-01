package com.joyintech.service.entrust.common.service;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.dao.EtrEntrustMapper;
import com.joyintech.tams.common.base.dao.EtrEntrustReMapper;
import com.joyintech.tams.common.base.entity.EtrEntrustRe;
import com.joyintech.tams.invest.common.component.base.entrust.impl.BaseEtrInvestInfoServiceImpl;
import com.joyintech.tams.invest.common.dto.BaseEtrInvestInfoDto;
import com.joyintech.tams.invest.common.entrust.dto.EntrustCommonDto;
import com.joyintech.tams.invest.common.trade.service.BaseEtrCashPositionCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装添加流程中数据需求的增删改
 */
@Component
@Lazy(false)
public class WFlowCommonService {
    private static BaseEtrInvestInfoServiceImpl baseEtrInvestInfoServiceImpl; // 流程数据相关修改
    private static BaseEtrCashPositionCommService baseEtrCashPositionCommService; // 持仓处理
    private static EtrEntrustMapper etrEntrustMapper; // 指令

    private static EtrEntrustReMapper etrEntrustReMapper; // 指令

    /**
     * 操作类型为submit时执行新增流程数据 & 持仓处理
     * @param operateType
     * @param dto
     */
    public static void doInsertFlow(String operateType, BaseEtrInvestInfoDto dto){
        if ("submit".equals(operateType)) {
            if(StringUtil.isEmpty(dto.getWorkFlowId())){
                throw new RuntimeException("WorkFlowId初始化不能为空!");
            }
            insertFlow(dto);

            if(baseEtrCashPositionCommService != null){
                EntrustCommonDto entrustCommonDto = new EntrustCommonDto();
                entrustCommonDto.setEtrEntrust(dto.getEtrEntrust());
                entrustCommonDto.setEtrEntrustAdd(dto.getEtrEntrustAdd());
                baseEtrCashPositionCommService.entrustStartExecute(entrustCommonDto);
            }
        }
    }

    /**
     * 操作类型不为revoke、delete时执行删除 & 新增 & 持仓处理
     * @param operateType
     * @param dto
     */
    public static void doUpdateFlow(String operateType, BaseEtrInvestInfoDto dto){
        if (!StringUtil.equals("revoke", operateType) && !StringUtil.equals("delete", operateType)) {
            deleteFlow(dto.getWorkFlowId());
            insertFlow(dto);

            if(baseEtrCashPositionCommService != null){
                EntrustCommonDto entrustCommonDto = new EntrustCommonDto();
                entrustCommonDto.setEtrEntrust(dto.getEtrEntrust());
                entrustCommonDto.setEtrEntrustAdd(dto.getEtrEntrustAdd());
                // 持仓处理
                baseEtrCashPositionCommService.entrustStartExecute(entrustCommonDto);
            }
        }
    }

    public void entrustStartExecute(BaseEtrInvestInfoDto dto){
        if(baseEtrCashPositionCommService != null){
            EntrustCommonDto entrustCommonDto = new EntrustCommonDto();
            entrustCommonDto.setEtrEntrust(dto.getEtrEntrust());
            entrustCommonDto.setEtrEntrustAdd(dto.getEtrEntrustAdd());
            // 持仓处理
            baseEtrCashPositionCommService.entrustStartExecute(entrustCommonDto);
        }
    }
    
    /**
     * 修改状态为已退回 & 持仓关闭
     * @param workflowId
     * @param entrustId
     */
    public static void doBackFlow(String workflowId, String entrustId){
        if (baseEtrCashPositionCommService != null) {
            // 持仓关闭
            baseEtrCashPositionCommService.entrustCloseExecute(entrustId);
        }
        // ZGZGST-1472:先持仓关闭再删除流程记录 fuyongsheng 20210825 edit start
        deleteFlow(workflowId);
        // ZGZGST-1472 fuyongsheng 20210825 edit end
    }
    
    public static void doCompleteFlow(String workflowId){
        deleteFlow(workflowId);
    }

    public static boolean insertFlow(BaseEtrInvestInfoDto dto){
        return baseEtrInvestInfoServiceImpl.insertWithQuery(dto);
    }

    public static boolean deleteFlow(String workflowId) {
        Map<String, Object> param = new HashMap<>();
        param.put("workflowId", workflowId);
        return baseEtrInvestInfoServiceImpl.deleteDelegateFlow(param);
    }

    @Autowired
    public void setBaseEtrInvestInfoServiceImpl(BaseEtrInvestInfoServiceImpl baseEtrInvestInfoServiceImpl){
        WFlowCommonService.baseEtrInvestInfoServiceImpl=baseEtrInvestInfoServiceImpl;
    }
    
    @Autowired(required = false)
    public void setBaseEtrCashPositionCommService(BaseEtrCashPositionCommService baseEtrCashPositionCommService){
        WFlowCommonService.baseEtrCashPositionCommService=baseEtrCashPositionCommService;
    }

    @Autowired
    public void setEtrEntrustMapper(EtrEntrustMapper etrEntrustMapper){
        WFlowCommonService.etrEntrustMapper=etrEntrustMapper;
    }

    @Autowired
    public void setEtrEntrustReMapper(EtrEntrustReMapper etrEntrustReMapper){
        WFlowCommonService.etrEntrustReMapper=etrEntrustReMapper;
    }

    /**
     * 修改状态为已退回 & 持仓关闭 & 不删除流程
     * @param workflowId
     * @param entrustId
     */
    public static void doBackFlowExecute(String workflowId, String entrustId){
        if (baseEtrCashPositionCommService != null) {
            // 持仓关闭
            baseEtrCashPositionCommService.entrustCloseExecute(entrustId);
        }
    }

    /**
     * 修改状态为已退回 & 持仓关闭 & 不删除流程
     * @param entrustId
     * @param totalEntrustId
     */
    public static void insertEtrEntrustRe(String entrustId, String totalEntrustId){
        if(StringUtil.isNotEmpty(entrustId)){
            EtrEntrustRe etrEntrustRe = new EtrEntrustRe();
            etrEntrustRe.setEntrustId(entrustId);
            etrEntrustRe.setTotalEntrustId(totalEntrustId);
            etrEntrustReMapper.insert(etrEntrustRe);
        }
    }

    public static void inserBackuptWithQuery(BaseEtrInvestInfoDto dto){
        baseEtrInvestInfoServiceImpl.inserBackuptWithQuery(dto);
    }



}
