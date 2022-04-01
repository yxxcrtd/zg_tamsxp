package com.joyintech.tams.exchange.datacommon.db.service.impl;

import com.joyintech.tams.exchange.common.db.domain.InstructionPledge;
import com.joyintech.tams.exchange.common.db.domain.InstructionPledgeExample;
import com.joyintech.tams.exchange.datacommon.cache.impl.AsyncServiceImpl;
import com.joyintech.tams.exchange.datacommon.db.dao.IMybatisMapper;
import com.joyintech.tams.exchange.datacommon.db.dao.InstructionPledgeMapper;
import com.joyintech.tams.exchange.datacommon.db.service.InstructionPledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InstructionPledgeServiceImpl extends AsyncServiceImpl<String, InstructionPledge> implements InstructionPledgeService {

    @Autowired
    private InstructionPledgeMapper instructionPledgeMapper;

    private Map<String, Map<String, InstructionPledge>> instructionIdMap = new ConcurrentHashMap<>();

    @Override
    public List<InstructionPledge> queryInstructionPledgesByInstructionId(String instructionId) {
        return new ArrayList<>(instructionIdMap.getOrDefault(instructionId, new HashMap<>()).values());
    }

    @Override
    public void deleteInstructionPledgesByInstructionsIds(Set<String> instructionIds) {
        instructionIds.forEach(s -> {
            List<InstructionPledge> instructionPledges = queryInstructionPledgesByInstructionId(s);
            if (Objects.nonNull(instructionPledges)) {
                List<InstructionPledge> instructionPledges1 = new ArrayList<>(instructionPledges);
                if (instructionPledges1.size() > 0) asyncDel(instructionPledges1);
            }
            instructionIdMap.remove(s);
        });
    }

    @Override
    public InstructionPledge queryInstructionPledgesByInstructionIdAndStockCode(String instructionId, String stockCode) {
        List<InstructionPledge> instructionPledges = queryInstructionPledgesByInstructionId(instructionId);
        if (Objects.nonNull(instructionPledges)) {
            for (InstructionPledge instructionPledge : instructionPledges) {
                if (stockCode.equals(instructionPledge.getFinprodMarketId())) {
                    return instructionPledge;
                }
            }
        }
        return null;
    }

    @Override
    public IMybatisMapper<InstructionPledge, String, InstructionPledgeExample> getMapper() {
        return instructionPledgeMapper;
    }

    @Override
    public void clearCache() {
        memory_cache.clear();
        instructionIdMap.clear();
    }

    @Override
    protected void update(InstructionPledge instructionPledge) {
        Map<String, InstructionPledge> instructionPledgeMap = instructionIdMap.get(instructionPledge.getEntrustId());
        if (Objects.isNull(instructionPledgeMap)) {
            instructionPledgeMap = new ConcurrentHashMap<>();
            instructionIdMap.put(instructionPledge.getEntrustId(), instructionPledgeMap);
        }
        instructionPledgeMap.put(instructionPledge.getId(), instructionPledge);
    }

    @Override
    protected void save(InstructionPledge instructionPledge) {
        update(instructionPledge);
    }

    @Override
    protected void delete(String PK) {
        InstructionPledge instructionPledge = memory_cache.get(PK);
        if (Objects.nonNull(instructionPledge)) {
            List<InstructionPledge> instructionPledges = queryInstructionPledgesByInstructionId(instructionPledge.getEntrustId());
            if (Objects.nonNull(instructionPledges)) {
                instructionPledges.remove(instructionPledge);
                if (instructionPledges.size() == 0)
                    instructionIdMap.remove(instructionPledge.getEntrustId());
            }
        }
    }
}
