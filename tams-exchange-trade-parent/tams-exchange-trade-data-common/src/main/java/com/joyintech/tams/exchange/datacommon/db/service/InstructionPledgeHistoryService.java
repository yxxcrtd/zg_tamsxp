package com.joyintech.tams.exchange.datacommon.db.service;

import com.joyintech.tams.exchange.common.db.domain.InstructionPledgeHistory;

import java.util.List;

public interface InstructionPledgeHistoryService {
    List<InstructionPledgeHistory> queryAll();

    void saveInstructionPledgeHistory(List<InstructionPledgeHistory> historyList);
}
