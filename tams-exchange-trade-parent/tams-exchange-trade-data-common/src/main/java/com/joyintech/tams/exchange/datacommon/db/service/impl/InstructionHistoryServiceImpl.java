package com.joyintech.tams.exchange.datacommon.db.service.impl;

import com.joyintech.tams.exchange.common.db.domain.InstructionHistory;
import com.joyintech.tams.exchange.datacommon.db.dao.InstructionHistoryMapper;
import com.joyintech.tams.exchange.datacommon.db.dao.OrderHistoryMapper;
import com.joyintech.tams.exchange.datacommon.db.service.InstructionHistoryService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InstructionHistoryServiceImpl implements InstructionHistoryService {
    private static final Logger logger = LoggerFactory.getLogger(InstructionHistoryServiceImpl.class);

    @Autowired
    private InstructionHistoryMapper instructionHistoryMapper;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<InstructionHistory> queryAll() {
        return instructionHistoryMapper.selectByExample(null);
    }

    @Override
    public void saveInstructionHistory(List<InstructionHistory> historyList) {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        InstructionHistoryMapper mapper = sqlSession.getMapper(InstructionHistoryMapper.class);
        AtomicInteger index = new AtomicInteger(0);
        historyList.forEach(history -> {
            index.getAndIncrement();
            mapper.updateOrInsertSelective(history);
            if (index.get() % 500 == 0) {
                sqlSession.flushStatements();
            }
        });
        sqlSession.commit();
        sqlSession.close();
    }

}
