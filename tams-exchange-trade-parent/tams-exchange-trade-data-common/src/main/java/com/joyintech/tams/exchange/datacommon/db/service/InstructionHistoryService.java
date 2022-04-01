package com.joyintech.tams.exchange.datacommon.db.service;

import com.joyintech.tams.exchange.common.db.domain.InstructionHistory;
import com.joyintech.tams.exchange.common.db.domain.OrderHistory;
import com.joyintech.tams.exchange.datacommon.cache.AsyncService;

import java.util.List;

public interface InstructionHistoryService {
    List<InstructionHistory> queryAll();

    void saveInstructionHistory(List<InstructionHistory> historyList);

}
