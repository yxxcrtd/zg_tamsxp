package com.joyintech.tams.exchange.datacommon.db.service.impl;

import com.joyintech.tams.exchange.common.db.domain.InstructionDetailHistory;
import com.joyintech.tams.exchange.datacommon.db.dao.InstructionDetailHistoryMapper;
import com.joyintech.tams.exchange.datacommon.db.service.InstructionDetailHistoryService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InstructionDetailHistoryServiceImpl implements InstructionDetailHistoryService {

    @Autowired
    private InstructionDetailHistoryMapper instructionDetailHistoryMapper;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<InstructionDetailHistory> queryAll() {
        return instructionDetailHistoryMapper.selectByExample(null);
    }

    @Override
    public void saveInstructionDetailHistory(List<InstructionDetailHistory> historyList) {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        InstructionDetailHistoryMapper mapper = sqlSession.getMapper(InstructionDetailHistoryMapper.class);
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
