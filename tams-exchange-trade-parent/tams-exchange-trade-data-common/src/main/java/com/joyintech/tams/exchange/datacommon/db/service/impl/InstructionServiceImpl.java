package com.joyintech.tams.exchange.datacommon.db.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.joyintech.tams.exchange.common.constant.InstructionStatus;
import com.joyintech.tams.exchange.common.db.domain.Instruction;
import com.joyintech.tams.exchange.common.db.domain.InstructionExample;
import com.joyintech.tams.exchange.common.utils.JsonUtils;
import com.joyintech.tams.exchange.datacommon.cache.impl.AsyncServiceImpl;
import com.joyintech.tams.exchange.datacommon.db.dao.IMybatisMapper;
import com.joyintech.tams.exchange.datacommon.db.dao.InstructionMapper;
import com.joyintech.tams.exchange.datacommon.db.service.InstructionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InstructionServiceImpl extends AsyncServiceImpl<String, Instruction> implements InstructionService {
    private static final Logger logger = LoggerFactory.getLogger(InstructionServiceImpl.class);

    @Autowired
    private InstructionMapper instructionMapper;

    private Map<String, Map<String, Map<String, Instruction>>> userMap = Maps.newConcurrentMap();

    @Override
    public void coverCache(boolean fromRedis) {
        clearCache();
        List<Instruction> instructionList = fromRedis ? getAllFromRedis() : instructionMapper.selectByExample(null);
        List<Instruction> instructionCache = new ArrayList<>();
        if (Objects.nonNull(instructionList)) {
            instructionList.forEach(instruction -> {
            	logger.info("origin cache instructionId:{},tradeId:{}",instruction.getEntrustId(),instruction.getTradeId());
            	logger.info("origin cache instruction:{}",com.joyintech.framework.util.JsonUtils.object2Json(instruction));
                if (!Strings.isNullOrEmpty(instruction.getTradersInfo())) {
                    List<String> traderSet = JsonUtils.getObjectListFromJson(instruction.getTradersInfo(), String.class);
                    if(InstructionStatus.WAIT_RECEIVE.value.equals(instruction.getEntrustStatus())) {//未接收缓存到每个用户
                    	if (Objects.nonNull(traderSet) && traderSet.size() > 0) {
                    		traderSet.forEach(s -> {
                    			if (!s.equals(instruction.getTradeId())) {
                                	Instruction i = instruction.cloneInstruction();
                                    i.setTradeId(s);
                                    instructionCache.add(i);
                                    logger.info("new cache instruction:{}",com.joyintech.framework.util.JsonUtils.object2Json(i));
                                    logger.info("new cache instructionId:{},tradeId:{}",i.getEntrustId(),i.getTradeId());
                                }
                    		});
                    	}
                    }
                }
            });
            if (instructionCache.size() > 0) updateCache(instructionCache);
            if (instructionList.size() > 0) updateCache(instructionList);
        }
    }

    @Override
    public void clearCache() {
        memory_cache.clear();
        userMap.clear();
    }

    @Override
    protected void update(Instruction instruction) {
        if (!Strings.isNullOrEmpty(instruction.getTradeId())) {
            Map<String, Map<String, Instruction>> stockMap = userMap.get(instruction.getTradeId());
            if (Objects.isNull(stockMap)) {
                stockMap = Maps.newConcurrentMap();
                userMap.put(instruction.getTradeId(), stockMap);
            }
            if (Strings.isNullOrEmpty(instruction.getFinprodMarketId())) {
                instruction.setFinprodMarketId("");
            }
            Map<String, Instruction> instructionBeanMap = stockMap.get(instruction.getFinprodMarketId());
            if (Objects.isNull(instructionBeanMap)) {
                instructionBeanMap = Maps.newConcurrentMap();
                stockMap.put(instruction.getFinprodMarketId(), instructionBeanMap);
            }
            instructionBeanMap.put(instruction.getEntrustId(), instruction);

        } else {
            logger.info("instruction {} has no trader !!!", instruction.getEntrustId());
        }
    }

    @Override
    protected void save(Instruction instruction) {
        update(instruction);
    }

    @Override
    protected void delete(String PK) {
        Instruction instruction = findById(PK);
        if (Objects.nonNull(instruction)) {
            if (Objects.nonNull(instruction.getTradeId())) {
                Map<String, Map<String, Instruction>> stockMap = userMap.get(instruction.getTradeId());
                if (Objects.nonNull(stockMap)) {
                    Map<String, Instruction> instructionBeanMap = stockMap.get(instruction.getFinprodMarketId());
                    if (Objects.nonNull(instructionBeanMap)) instructionBeanMap.remove(PK);
                }
            }
        }
    }

    public void deleteTraderCache(String tradeId, String finprodMarketId, String entrustId) {
        Map<String, Map<String, Instruction>> stockMap = userMap.get(tradeId);
        if (Objects.nonNull(stockMap)) {
            Map<String, Instruction> instructionBeanMap = stockMap.get(finprodMarketId);
            if (Objects.nonNull(instructionBeanMap)) {
                instructionBeanMap.remove(entrustId);
                logger.info("删除缓存 [交易员编号: {}, 委托编号: {}]", tradeId, entrustId);
            }
        }
        memory_cache.remove(entrustId);
    }

    @Override
    public List<Instruction> queryInstructionByUserIdAndStockCode(String userId, String stockCode) {
        Map<String, Map<String, Instruction>> stockMap = userMap.get(userId);
        if (Objects.isNull(stockMap)) return new ArrayList<>();
        if (Strings.isNullOrEmpty(stockCode)) {
            List<Instruction> instructionList = new ArrayList<>();
            stockMap.values().forEach(stringInstructionBeanMap -> instructionList.addAll(stringInstructionBeanMap.values()));
            return instructionList;
        } else {
            return new ArrayList<>(stockMap.getOrDefault(stockCode, new HashMap<>()).values());
        }
    }

    protected String genDbId() {
        return null;
    }

    @Override
    public IMybatisMapper<Instruction, String, InstructionExample> getMapper() {
        return instructionMapper;
    }
}
