package com.joyintech.tams.exchange.datacommon.db.service;

import com.joyintech.tams.exchange.common.db.domain.InstructionDetailHistory;

import java.util.List;

public interface InstructionDetailHistoryService {

    List<InstructionDetailHistory> queryAll();

    void saveInstructionDetailHistory(List<InstructionDetailHistory> historyList);

}
