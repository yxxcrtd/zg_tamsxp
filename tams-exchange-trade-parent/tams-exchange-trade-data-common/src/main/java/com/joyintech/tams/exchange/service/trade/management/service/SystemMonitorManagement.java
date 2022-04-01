package com.joyintech.tams.exchange.service.trade.management.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Strings;
import com.joyintech.tams.exchange.common.bean.*;
import com.joyintech.tams.exchange.common.cache.impl.RedisCacheServiceImpl;
import com.joyintech.tams.exchange.common.constant.CacheClassMappingEnum;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.common.constant.MonitorType;
import com.joyintech.tams.exchange.common.db.domain.*;
import com.joyintech.tams.exchange.common.utils.JsonUtils;
import com.joyintech.tams.exchange.common.utils.SpringContextUtils;
import com.joyintech.tams.exchange.datacommon.async.AsyncPersistenceWorker;
import com.joyintech.tams.exchange.datacommon.cache.AsyncService;
import com.joyintech.tams.exchange.datacommon.cache.RedisOperateService;
import com.joyintech.tams.exchange.datacommon.db.service.MonitorCategoryService;
import com.joyintech.tams.exchange.datacommon.db.service.MonitorService;
import com.joyintech.tams.exchange.service.trade.processor.msg.TradeMsgReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SystemMonitorManagement
 * @Description TODO
 * @Author swwan
 * @Date 2020/8/25 16:40
 * @Version 1.0
 **/
@Component
public class SystemMonitorManagement {

    private static final Logger logger = LoggerFactory.getLogger(SystemMonitorManagement.class);

    @Autowired
    private RedisOperateService redisOperateService;

    @Autowired
    private MonitorCategoryService monitorCategoryService;

    @Autowired
    private MonitorService monitorService;

    @Value("${scheduled.monitor.fixedRate:60000}")
    private int monitorRate;

    /**
     * 获得某天0点时间
     *
     * @return
     */
    private static Date getTimesmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public ResultBean queryExchangeMonitor() {
        ExchangeMonitorBean exchangeMonitorBean = new ExchangeMonitorBean();
        exchangeMonitorBean.setProductTradeMsgCount(TradeMsgReceiver.productOffset.intValue());
        exchangeMonitorBean.setConsumerTradeMsgCount(TradeMsgReceiver.consumerOffset.intValue());
        exchangeMonitorBean.setExceptionTradeMsgCount(TradeMsgReceiver.exceptionOffset.intValue());
        exchangeMonitorBean.setProductPersistenceCount(AsyncPersistenceWorker.productOffset.intValue());
        exchangeMonitorBean.setConsumerPersistenceCount(AsyncPersistenceWorker.consumerOffset.intValue());
        exchangeMonitorBean.setExceptionPersistenceCount(AsyncPersistenceWorker.exceptionOffset.intValue());
        exchangeMonitorBean.setEntrustRAMCount(RedisCacheServiceImpl.entrustRAMCount.intValue());
        exchangeMonitorBean.setIntegratedEntrustRAMCount(RedisCacheServiceImpl.integratedEntrustRAMCount.intValue());
        exchangeMonitorBean.setOrderRAMCount(RedisCacheServiceImpl.orderRAMCount.intValue());
        exchangeMonitorBean.setTradeRAMCount(RedisCacheServiceImpl.tradeRAMCount.intValue());
        Long orderRedisCount = redisOperateService.getHashLen(Order.class.getName());
        exchangeMonitorBean.setOrderRedisCount(Math.toIntExact(orderRedisCount));
        Long tradeRedisCount = redisOperateService.getHashLen(Trade.class.getName());
        exchangeMonitorBean.setTradeRedisCount(Math.toIntExact(tradeRedisCount));
        Long entrustRedisCount = redisOperateService.getHashLen(Entrust.class.getName());
        exchangeMonitorBean.setEntrustRedisCount(Math.toIntExact(entrustRedisCount));
        Long integratedEntrustRedisCount = redisOperateService.getHashLen(IntegratedEntrust.class.getName());
        exchangeMonitorBean.setIntegratedEntrustRedisCount(Math.toIntExact(integratedEntrustRedisCount));
        logger.debug("exchange monitor: {}", JsonUtils.getJsonFromObject(exchangeMonitorBean));
        return new ResultBean(ErrorCode.SUCCESS, exchangeMonitorBean);
    }

    private List<MonitorBean> monitorListConvert2MonitorBeanList(List<Monitor> monitors) {
        List<MonitorBean> monitorBeans = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(monitors)) {
            monitors.forEach(
                    monitor -> {
                        MonitorBean monitorBean = new MonitorBean(monitor.getRecordTime(), monitor.getRecordValue());
                        monitorBeans.add(monitorBean);
                    });
        }
        return monitorBeans;
    }

    public ResultBean queryExchangeMonitorDynamic(DynamicMonitorDTO dto) {
        logger.debug("queryExchangeMonitorDynamic params: {}", JSONUtil.toJsonStr(dto));
        String categoryName = dto.getCategoryName();
        Long snapshotTimestamp = dto.getSnapshotTime();
        Date snapshotTime = getTimesmorning(new Date());
        List<String> recordNameList = null;
        List<MonitorIndicator> indicatorList = new ArrayList<>();
        List<String> categoryList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(categoryName)) {
            categoryList.add(categoryName);
            recordNameList = monitorCategoryService.queryRecordNameByCategoryInCache(categoryName);
        } else {
            categoryList = monitorCategoryService.queryAllCategoryNameInCache();
            recordNameList = monitorCategoryService.queryAllRecordNameInCache();
        }
        if (ObjectUtil.isNotEmpty(snapshotTimestamp)) {
            snapshotTime = new Date(snapshotTimestamp);
        }
        List<Monitor> monitorList = monitorService.queryByRecordNameAndSnapShotTime(recordNameList, snapshotTime);
        Map<String, List<Monitor>> monitorGroupByRecordMap = monitorList.stream().collect(Collectors.groupingBy(Monitor::getRecordName));
        Map<String, List<MonitorBean>> recordMonitorBeanMap = new HashMap<>();
        recordNameList.forEach(record -> {
            List<MonitorBean> beanList = monitorListConvert2MonitorBeanList(monitorGroupByRecordMap.get(record));
            recordMonitorBeanMap.put(record, beanList);
        });
        Date finalSnapshotTime = snapshotTime;
        categoryList.forEach(category -> {
            MonitorIndicator indicator = new MonitorIndicator();
            indicator.setCategoryName(category);
            indicator.setScheduleTime(monitorRate / 1000);
            if (monitorList.isEmpty()) {
                indicator.setSnapshotTime(finalSnapshotTime);
            } else {
                indicator.setSnapshotTime(monitorList.get(monitorList.size() - 1).getCreateTime());
            }
            List<String> records = monitorCategoryService.queryRecordNameByCategoryInCache(category);
            Map<String, List<MonitorBean>> recordMonitorBeans = new HashMap<>();
            records.forEach(record -> recordMonitorBeans.put(MonitorType.getName(record), recordMonitorBeanMap.get(record)));
            indicator.setRecordListMap(recordMonitorBeans);
            indicatorList.add(indicator);
        });
        return new ResultBean(ErrorCode.SUCCESS, indicatorList);
    }

    public ResultBean queryCacheMonitor() {
        Map<String, AsyncService> asyncServiceMap = SpringContextUtils.getApplicationContext().getBeansOfType(AsyncService.class);
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        if (Objects.nonNull(asyncServiceMap)) {
            asyncServiceMap.values().forEach(asyncService -> {
                try {
                    Class tClass = Class.forName(asyncService.getClass().getName());
                    RedisCacheServiceImpl genericPersistenceService = (RedisCacheServiceImpl) SpringContextUtils.getBean(tClass);
                    int num = genericPersistenceService.getMap().values().size();
                    if (!Strings.isNullOrEmpty(CacheClassMappingEnum.getNameByClassPath(asyncService.getClass().getName()))) {
                        countMap.put(CacheClassMappingEnum.getNameByClassPath(asyncService.getClass().getName()), num);
                    }
                } catch (Exception e) {
                    logger.error("queryCacheMonitor error:", e);
                }
            });
        }
        return new ResultBean(ErrorCode.SUCCESS, countMap);
    }
}
