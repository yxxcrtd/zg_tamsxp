package com.joyintech.tams.exchange.datacommon.db.service.impl;

import com.joyintech.tams.exchange.common.db.domain.InstructionPledgeHistory;
import com.joyintech.tams.exchange.datacommon.db.dao.InstructionPledgeHistoryMapper;
import com.joyintech.tams.exchange.datacommon.db.service.InstructionPledgeHistoryService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InstructionPledgeHistoryServiceImpl implements InstructionPledgeHistoryService {

    @Autowired
    private InstructionPledgeHistoryMapper instructionPledgeHistoryMapper;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<InstructionPledgeHistory> queryAll() {
        return instructionPledgeHistoryMapper.selectByExample(null);
    }

    @Override
    public void saveInstructionPledgeHistory(List<InstructionPledgeHistory> historyList) {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        InstructionPledgeHistoryMapper mapper = sqlSession.getMapper(InstructionPledgeHistoryMapper.class);
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
