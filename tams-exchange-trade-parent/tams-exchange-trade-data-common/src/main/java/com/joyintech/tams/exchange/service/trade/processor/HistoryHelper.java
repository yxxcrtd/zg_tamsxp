package com.joyintech.tams.exchange.service.trade.processor;

import com.joyintech.tams.exchange.common.constant.BusinessFlag;
import com.joyintech.tams.exchange.common.db.domain.*;
import com.joyintech.tams.exchange.common.utils.EntrustUtils;
import com.joyintech.tams.exchange.datacommon.db.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class HistoryHelper {
    private static final Logger logger = LoggerFactory.getLogger(HistoryHelper.class);
    @Autowired
    private EntrustService entrustService;

    @Autowired
    private EntrustHistoryService entrustHistoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private TradeHistoryService tradeHistoryService;

    @Autowired
    private IntegratedEntrustHistoryService integratedEntrustHistoryService;

    @Autowired
    private IntegratedEntrustService integratedEntrustService;

    @Autowired
    private BatchInfoService batchInfoService;

    @Autowired
    private BatchInfoHistoryService batchInfoHistoryService;

    @Autowired
    private InstructionService instructionService;

    @Autowired
    private InstructionPledgeService instructionPledgeService;

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private AlgorithmHistoryService algorithmHistoryService;

    @Autowired
    private InstructionDetailService instructionDetailService;
    @Autowired
    private InstructionHistoryService instructionHistoryService;
    @Autowired
    private InstructionDetailHistoryService instructionDetailHistoryService;
    @Autowired
    private InstructionPledgeHistoryService instructionPledgeHistoryService;

    private SimpleDateFormat tradingDayFormat = new SimpleDateFormat("yyyyMMdd");

    public void handleEntrustHistory(Date date, String nextDate) {
        String lastClearDate = entrustHistoryService.queryLastClearDate();
        logger.info("开始清算委托，查询到上次清算日：{}", lastClearDate);
        String platDate = tradingDayFormat.format(date);
        logger.info("当前系统平台日：{}", platDate);
        List<Entrust> entrusts = entrustService.findAll();
        List<EntrustHistory> entrustHistories = new ArrayList<>();
        if (Objects.nonNull(entrusts) && entrusts.size() > 0) {
            Set<Entrust> deleteEntrusts = new HashSet<>(entrusts);
            entrusts.forEach(entrust -> {
                //只归档当日历史，可能有老数据因为指令未到结束日而留存，不重复归历史
                if (entrust.getTradingDay().compareTo(lastClearDate) > 0 && entrust.getTradingDay().compareTo(platDate) <= 0) {
                    EntrustHistory entrustHistory = new EntrustHistory();
                    BeanUtils.copyProperties(entrust, entrustHistory, "id");
                    entrustHistory.setEntrustId(entrust.getId());
                    entrustHistories.add(entrustHistory);
                }
                if (Objects.nonNull(instructionService.findById(entrust.getInstructionId()))) {
                    //指令未到结束日不删除
                    deleteEntrusts.remove(entrust);
                }
            });
            if (entrustHistories.size() > 0) entrustHistoryService.saveEntrustHistory(entrustHistories);
            if (deleteEntrusts.size() > 0) entrustService.asyncDel(deleteEntrusts);
            entrustService.clearCacheBySettle(nextDate);
            logger.info("委托清算完成,归历史委托数量 {}", deleteEntrusts.size());
        }
    }

    public void handleOrderHistory(Date date) {
        String lastClearDate = orderHistoryService.queryLastClearDate();
        logger.info("开始清算订单，查询到上次清算日：{}", lastClearDate);
        String platDate = tradingDayFormat.format(date);
        logger.info("当前系统平台日： {}", platDate);
        List<Order> orders = orderService.findAll();
        List<OrderHistory> orderHistories = new ArrayList<>();
        if (Objects.nonNull(orders) && orders.size() > 0) {
            Set<Order> deleteOrders = new HashSet<>(orders);
            orders.forEach(order -> {
                // 公平订单---直接归历史
                if (BusinessFlag.FAIR.value.equals(order.getBusinessFlag())) {
                    OrderHistory orderHistory = new OrderHistory();
                    BeanUtils.copyProperties(order, orderHistory, "id");
                    orderHistory.setOrderId(order.getId());
                    orderHistories.add(orderHistory);
                } else {
                    // 批量订单----只归档当日历史，可能有老数据因为指令未到结束日而留存，不重复归历史
                    if (order.getTradingDay().compareTo(lastClearDate) > 0 && order.getTradingDay().compareTo(platDate) <= 0) {
                        OrderHistory orderHistory = new OrderHistory();
                        BeanUtils.copyProperties(order, orderHistory, "id");
                        orderHistory.setOrderId(order.getId());
                        orderHistories.add(orderHistory);
                    }
                }
                String instructionId = order.getInstructionId();
                if (BusinessFlag.SPLIT.value.equals(order.getBusinessFlag())) {
                    instructionId = EntrustUtils.convertRemarkToInstructionId(order.getRemark());
                }
                if (Objects.nonNull(instructionService.findById(instructionId))) {
                    //指令未到结束日不删除
                    deleteOrders.remove(order);
                }
            });
            if (orderHistories.size() > 0) orderHistoryService.saveOrderHistory(orderHistories);
            if (deleteOrders.size() > 0) orderService.asyncDel(deleteOrders);
            orderService.clearSet();
            logger.info("订单清算完成,归历史订单数量 {}", deleteOrders.size());
        }
    }

    public void handleTradeHistory(Date date, String nextDate) {
        String lastClearDate = tradeHistoryService.queryLastClearDate();
        logger.info("开始清算成交，查询到上次清算日：{}", lastClearDate);
        String platDate = tradingDayFormat.format(date);
        logger.info("当前系统平台日 {}", platDate);
        List<Trade> trades = tradeService.findAll();
        List<TradeHistory> tradeHistories = new ArrayList<>();
        if (Objects.nonNull(trades) && trades.size() > 0) {
            Set<Trade> deleteTrades = new HashSet<>(trades);
            trades.forEach(trade -> {
                if (trade.getTradingDay().compareTo(lastClearDate) > 0 && trade.getTradingDay().compareTo(platDate) <= 0) {
                    TradeHistory tradeHistory = new TradeHistory();
                    BeanUtils.copyProperties(trade, tradeHistory, "id");
                    tradeHistory.setTradeId(trade.getId());
                    tradeHistories.add(tradeHistory);
                }
                if (Objects.nonNull(instructionService.findById(trade.getInstructionId()))) {
                    //指令未到结束日不删除
                    deleteTrades.remove(trade);
                }
            });
            if (tradeHistories.size() > 0) tradeHistoryService.saveTradeHistory(tradeHistories);
            if (deleteTrades.size() > 0) tradeService.asyncDel(deleteTrades);
            tradeService.clearSet(nextDate);
            logger.info("成交清算完成,归历史成交数量 {}", deleteTrades.size());
        }
    }

    public void handleIntegratedEntrustHistory(Date date, String nextDate) {
        String lastClearDate = integratedEntrustHistoryService.queryLastClearDate();
        logger.info("开始清算综合业务委托，查询到上次清算日：{}", lastClearDate);
        String platDate = tradingDayFormat.format(date);
        logger.info("当前系统平台日 {}", platDate);
        List<IntegratedEntrust> integratedEntrusts = integratedEntrustService.findAll();
        List<IntegratedEntrustHistory> integratedEntrustsHistories = new ArrayList<>();
        if (Objects.nonNull(integratedEntrusts) && integratedEntrusts.size() > 0) {
            Set<IntegratedEntrust> deleteEntrusts = new HashSet<>(integratedEntrusts);
            integratedEntrusts.forEach(integratedEntrust -> {
                if (integratedEntrust.getTradingDay().compareTo(lastClearDate) > 0 && integratedEntrust.getTradingDay().compareTo(platDate) <= 0) {
                    IntegratedEntrustHistory integratedEntrustHistory = new IntegratedEntrustHistory();
                    BeanUtils.copyProperties(integratedEntrust, integratedEntrustHistory, "id");
                    integratedEntrustHistory.setIntegratedEntrustId(integratedEntrust.getId());
                    integratedEntrustsHistories.add(integratedEntrustHistory);
                }
                if (Objects.nonNull(instructionService.findById(integratedEntrust.getInstructionId()))) {
                    //指令未到结束日不删除
                    deleteEntrusts.remove(integratedEntrust);
                }
            });
            if (integratedEntrustsHistories.size() > 0)
                integratedEntrustHistoryService.saveIntegratedEntrustHistory(integratedEntrustsHistories);
            if (integratedEntrustsHistories.size() > 0) integratedEntrustService.asyncDel(deleteEntrusts);
            integratedEntrustService.clearCacheBySettle(nextDate);
            logger.info("综合委托清算完成,归历史综合委托数量 {}", deleteEntrusts.size());
        }
    }

    public void handleBatchInfoHistory(Date date) {
        String platDate = tradingDayFormat.format(date);
        logger.info("开始清算批量信息，当前系统平台日 {}", platDate);
        List<BatchInfo> batchInfos = batchInfoService.findAll();
        List<BatchInfoHistory> batchInfoHistories = new ArrayList<>();
        if (Objects.nonNull(batchInfos) && batchInfos.size() > 0) {
            batchInfos.forEach(batchInfo -> {
                BatchInfoHistory batchInfoHistory = new BatchInfoHistory();
                BeanUtils.copyProperties(batchInfo, batchInfoHistory);
                batchInfoHistory.setBatchinfoId(batchInfo.getId());
                batchInfoHistory.setBatchinfoDay(platDate);
                batchInfoHistories.add(batchInfoHistory);
            });
            if (batchInfoHistories.size() > 0) batchInfoHistoryService.saveBatchInfoHistories(batchInfoHistories);
            if (batchInfos.size() > 0) batchInfoService.asyncDel(batchInfos);
            logger.info("批量信息清算完成,归历史批量信息数量 {}", batchInfos.size());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSysDateAfterInstructionDate(Date date) {
        logger.info("开始清算指令数据，当前系统平台日： {}", tradingDayFormat.format(date));
        List<Instruction> instructionList = instructionService.findAll();
        if (Objects.nonNull(instructionList)) {
            List<InstructionPledge> pledgeList = instructionPledgeService.findAll();
            List<InstructionDetail> detailList = instructionDetailService.findAll();
            //同步指令数据
            handleInstructionHistory(instructionList, pledgeList, detailList);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            Date mDate = cal.getTime();
            List<Instruction> instructions = new ArrayList<>();
            Set<String> stringSet = new HashSet<>();
            // 清除过期指令
            instructionList.forEach(instruction -> {
                if (mDate.after(instruction.getMdate())) {
                    instructions.add(instruction);
                    stringSet.add(instruction.getEntrustId());
                }
            });

            List<InstructionPledge> pledges = pledgeList.stream()
                    .filter(o -> stringSet.contains(o.getEntrustId())).collect(Collectors.toList());
            logger.info("清算抵押券信息，清算抵押券数量：  {}", pledges.size());
            if (pledges.size() > 0) instructionPledgeService.asyncDel(pledges);

            List<InstructionDetail> details = detailList.stream()
                    .filter(o -> stringSet.contains(o.getEntrustId())).collect(Collectors.toList());
            logger.info("清算组合明细信息，组合明细数量：  {}", details.size());
            if (details.size() > 0) instructionDetailService.asyncDel(details);

//            instructionPledgeService.deleteInstructionPledgesByInstructionsIds(stringSet);
//            instructionDetailService.deleteInstructionDetailsByInstructionsIds(stringSet);

            logger.info("指令清算完成，清算指令数量：  {}", instructions.size());
            if (instructions.size() > 0) instructionService.asyncDel(instructions);
        }
    }

    public void handleInstructionHistory(List<Instruction> instructionList, List<InstructionPledge> pledgeList,
                                         List<InstructionDetail> detailList) {
        logger.info("开始同步指令、质押券、组合信息数据到历史表...");
        Set<String> instructionIdSet = instructionList.stream().map(Instruction::getEntrustId).collect(Collectors.toSet());
        List<InstructionHistory> historyList = new ArrayList<>();
        instructionList.forEach(instruction -> {
            InstructionHistory instructionHistory = new InstructionHistory();
            BeanUtils.copyProperties(instruction, instructionHistory);
            historyList.add(instructionHistory);
        });

        List<InstructionPledgeHistory> pledgeHistoryList = pledgeList.stream()
                .filter(o -> instructionIdSet.contains(o.getEntrustId()))
                .map(pledge -> {
                    InstructionPledgeHistory pledgeHistory = new InstructionPledgeHistory();
                    BeanUtils.copyProperties(pledge, pledgeHistory);
                    return pledgeHistory;
                }).collect(Collectors.toList());

        List<InstructionDetailHistory> detailHistoryList = detailList.stream()
                .filter(o -> instructionIdSet.contains(o.getEntrustId()))
                .map(pledge -> {
                    InstructionDetailHistory detailHistory = new InstructionDetailHistory();
                    BeanUtils.copyProperties(pledge, detailHistory);
                    return detailHistory;
                }).collect(Collectors.toList());

        instructionHistoryService.saveInstructionHistory(historyList);
        instructionPledgeHistoryService.saveInstructionPledgeHistory(pledgeHistoryList);
        instructionDetailHistoryService.saveInstructionDetailHistory(detailHistoryList);
        logger.info("完成同步指令、质押券、组合信息数据到历史表...");
    }

    public void handleAlgorithmHistory(Date date) {
        String platDate = tradingDayFormat.format(date);
        String lastClearDate = algorithmHistoryService.queryLastClearDate();
        logger.info("开始清算算法单数据，查询到上次清算日：{}", lastClearDate);
        Map<String, Algorithm> algorithmMap = algorithmService.getMap();
        List<AlgorithmHistory> algorithmHistories = new ArrayList<>();
        if (algorithmMap.size() > 0) {
            Set<Algorithm> deleteAlgorithms = new HashSet<>(algorithmMap.values());
            algorithmMap.values().forEach(algorithm -> {
                if (algorithm.getTradingDay().compareTo(lastClearDate) > 0 && algorithm.getTradingDay().compareTo(platDate) <= 0) {
                    AlgorithmHistory algorithmHistory = new AlgorithmHistory();
                    BeanUtils.copyProperties(algorithm, algorithmHistory, "id");
                    algorithmHistory.setAlgorithmId(algorithm.getId());
                    algorithmHistories.add(algorithmHistory);
                }
                if (Objects.nonNull(instructionService.findById(algorithm.getInstructionId()))) {
                    //指令未到结束日不删除
                    deleteAlgorithms.remove(algorithm);
                }
            });
            if (algorithmHistories.size() > 0) algorithmHistoryService.save(algorithmHistories);
            if (algorithmHistories.size() > 0) algorithmService.asyncDel(deleteAlgorithms);
            logger.info("算法单清算完成,归历史综合委托数量 {}", deleteAlgorithms.size());
        }
    }
}
