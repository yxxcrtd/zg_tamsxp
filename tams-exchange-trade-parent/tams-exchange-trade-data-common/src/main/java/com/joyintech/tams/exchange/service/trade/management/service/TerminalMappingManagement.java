package com.joyintech.tams.exchange.service.trade.management.service;

import com.google.common.base.Strings;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.common.db.domain.TerminalMapping;
import com.joyintech.tams.exchange.datacommon.db.service.TerminalMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class TerminalMappingManagement {
    @Autowired
    private TerminalMappingService terminalMappingService;

    public ResultBean queryTerminalMappingInfo(String accountId, String terminalName) {
        if (!Strings.isNullOrEmpty(accountId)) {
            return new ResultBean(ErrorCode.SUCCESS, terminalMappingService.findByAccountId(accountId));
        } else if (!Strings.isNullOrEmpty(terminalName)) {
            return new ResultBean(ErrorCode.SUCCESS, terminalMappingService.findByTerminalName(accountId));
        } else {
            return new ResultBean(ErrorCode.SUCCESS, terminalMappingService.findAll());
        }
    }

    public ResultBean addTerminalMappingInfo(List<TerminalMapping> terminalMappings) {
        // 对于映射表而言，增加和删除都是一个接口
        if (Objects.isNull(terminalMappings)) return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
        Set<String> accountIdSet = new HashSet<>();
        terminalMappings.forEach(terminalMapping -> {
            String accountId = terminalMapping.getAccountId();
            if (!accountIdSet.contains(accountId))
                terminalMappingService.deleteTerminalMappingInfoByAccountId(accountId);
            accountIdSet.add(accountId);
        });
        return new ResultBean(ErrorCode.SUCCESS, terminalMappingService.insertTerminalMappingInfo(terminalMappings));
    }

    public ResultBean deleteTerminalMappings(String accountId) {
        return new ResultBean(ErrorCode.SUCCESS, terminalMappingService.deleteTerminalMappingInfoByAccountId(accountId));
    }

    public ResultBean updateTerminalMappingInfo(String id, TerminalMapping terminalMapping) {
        if (Strings.isNullOrEmpty(id) || Objects.isNull(terminalMapping))
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
        return new ResultBean(ErrorCode.SUCCESS, terminalMappingService.updateTerminalMappingInfo(id, terminalMapping));
    }

    public ResultBean deleteTerminalMappingInfo(String id) {
        if (Strings.isNullOrEmpty(id)) return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
        return new ResultBean(ErrorCode.SUCCESS, terminalMappingService.deleteTerminalMappingInfoById(id));
    }
}
