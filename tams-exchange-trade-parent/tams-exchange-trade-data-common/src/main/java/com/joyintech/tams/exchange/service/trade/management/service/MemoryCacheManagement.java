package com.joyintech.tams.exchange.service.trade.management.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.joyintech.tams.exchange.common.bean.ResultBean;
import com.joyintech.tams.exchange.common.cache.impl.QuoteTickServiceImpl;
import com.joyintech.tams.exchange.common.cache.impl.RedisCacheServiceImpl;
import com.joyintech.tams.exchange.common.constant.CacheClassMappingEnum;
import com.joyintech.tams.exchange.common.constant.ErrorCode;
import com.joyintech.tams.exchange.common.constant.ExchangeConstant;
import com.joyintech.tams.exchange.common.db.domain.KeyMethodInterface;
import com.joyintech.tams.exchange.common.utils.ConvertListUtils;
import com.joyintech.tams.exchange.common.utils.JsonUtils;
import com.joyintech.tams.exchange.common.utils.SpringContextUtils;
import com.joyintech.tams.exchange.datacommon.cache.AsyncService;
import com.joyintech.tams.exchange.datacommon.cache.impl.AsyncServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class MemoryCacheManagement {
    private static final Logger logger = LoggerFactory.getLogger(MemoryCacheManagement.class);

    @Autowired
    private QuoteTickServiceImpl quoteTickService;

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

    public ResultBean getData(String serviceName, int page, int perPage) {
        return getPageData(serviceName, page, perPage);
    }

    public ResultBean recoverData(String serviceName, String id, int coverType) {
        try {
            String classPath = CacheClassMappingEnum.getClassPathByName(serviceName);
            AsyncServiceImpl<String, KeyMethodInterface<String>> asyncService = (AsyncServiceImpl) SpringContextUtils.getBean(Class.forName(classPath));
            if (Objects.isNull(asyncService))
                return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
            if (ExchangeConstant.RECOVER_DB2MEMORY == coverType) {//从DB加载到内存
                if (Strings.isNullOrEmpty(id)) {
                    asyncService.coverCache(false);
                    logger.info("recover memoryCache db to memory: {}", serviceName);
                    return new ResultBean(ErrorCode.SUCCESS);
                } else {
                    Object o = asyncService.getMapper().selectByPrimaryKey(id);
                    if (Objects.nonNull(o)) {
                        asyncService.asyncUpdate((KeyMethodInterface) o);
                        logger.info("recover memoryCache db to memory: {}, id:[{}]", serviceName, id);
                        return new ResultBean(ErrorCode.SUCCESS);
                    } else {
                        logger.info("recover memoryCache db to memory failed, not found data in db: {}, id:[{}]", serviceName, id);
                    }
                }
            } else {//从内存写到DB
                if (Strings.isNullOrEmpty(id)) {
                    if (ExchangeConstant.RECOVER_MEMORY2DB == coverType) {
                        asyncService.asyncUpdate(asyncService.findAll());
                        logger.info("recover memoryCache memory to db: {}, not delete db data", serviceName);
                    } else if (ExchangeConstant.RECOVER_MEMORY2DB_WITH_DELETE == coverType) {//该模式先删除DB数据
                        List list = asyncService.findAll();
                        asyncService.asyncDel(list);
                        asyncService.asyncUpdate(asyncService.findAll());
                        logger.info("recover memoryCache memory to db: {}, delete db data", serviceName);
                    }
                    return new ResultBean(ErrorCode.SUCCESS);
                } else {
                    Object o = asyncService.findById(id);
                    if (Objects.nonNull(o)) {
                        asyncService.asyncUpdate((KeyMethodInterface) o);
                        logger.info("recover memoryCache memory to db: {}, id[{}]", serviceName, id);
                        return new ResultBean(ErrorCode.SUCCESS);
                    } else {
                        logger.info("recover memoryCache db to memory failed, not found data in db: {}, id:[{}]", serviceName, id);
                    }
                }
            }
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR, "not found data");
        } catch (Exception e) {
            logger.info("recover memoryCache fail {}", e.getMessage(), e);
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR, e.getMessage());
        }
    }

    public ResultBean clearData(String serviceName) {
        try {
            String classPath = CacheClassMappingEnum.getClassPathByName(serviceName);
            AsyncServiceImpl<String, KeyMethodInterface<String>> asyncService = (AsyncServiceImpl) SpringContextUtils.getBean(Class.forName(classPath));
            if (Objects.isNull(asyncService))
                return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
            asyncService.clearCache();
            logger.info("{} clearCache", serviceName);
            return new ResultBean(ErrorCode.SUCCESS);
        } catch (Exception e) {
            logger.info("clean memoryCache fail {}", e.getMessage(), e);
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR, e.getMessage());
        }
    }

    private ResultBean getPageData(String serviceName, int page, int perPage) {
        try {
            String classPath = CacheClassMappingEnum.getClassPathByName(serviceName);
            AsyncServiceImpl genericPersistenceService = (AsyncServiceImpl) SpringContextUtils.getBean(Class.forName(classPath));
            List memoryCache = genericPersistenceService.findAll();
            return new ResultBean(ErrorCode.SUCCESS, ConvertListUtils.convertPage(page, perPage, memoryCache, genericPersistenceService.getTClass()));
        } catch (Exception e) {
            logger.error("MemoryCacheServiceImpl getPageData {}", e.getMessage(), e);
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR, e);
        }
    }

    public ResultBean addData(String serviceName, String beanInfoList) {
        try {
            String classPath = CacheClassMappingEnum.getClassPathByName(serviceName);
            String beanPath = CacheClassMappingEnum.getBeanPathByName(serviceName);
            AsyncServiceImpl genericPersistenceService = (AsyncServiceImpl) SpringContextUtils.getBean(Class.forName(classPath));
            if (Objects.isNull(genericPersistenceService))
                return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
            if (Objects.nonNull(beanInfoList)) {
                Class<?> aClass = Class.forName(beanPath);
                List<?> objectList = JsonUtils.getObjectListFromJson(beanInfoList, aClass);
                objectList.forEach(obj -> {
                    if (Objects.nonNull(((KeyMethodInterface) obj).getId())) {
                        Object object = genericPersistenceService.findById(((KeyMethodInterface) obj).getId());
                        if (Objects.nonNull(object)) {
                            logger.debug("{} before addData,  obj:{}", serviceName, JSONObject.toJSONString(object));
                        }
                    }
                });
                genericPersistenceService.asyncUpdate(objectList);
                logger.debug("{} after addData,  list:{}", serviceName, JSONArray.toJSONString(objectList));
            }
            return new ResultBean(ErrorCode.SUCCESS);
        } catch (Exception e) {
            logger.info("add memoryCache fail {}", e.getMessage(), e);
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR, e.getMessage());
        }
    }

    public ResultBean deleteData(String serviceName, List<String> list) {
        try {
            Class tClass = Class.forName(CacheClassMappingEnum.getClassPathByName(serviceName));
            AsyncServiceImpl genericPersistenceService = (AsyncServiceImpl) SpringContextUtils.getBean(tClass);
            if (Objects.isNull(genericPersistenceService))
                return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR);
            list.forEach(id -> {
                genericPersistenceService.asyncDel(id);
            });
            logger.debug("{} deleteCache ids:{}", serviceName, JSONArray.toJSONString(list));
            return new ResultBean(ErrorCode.SUCCESS);
        } catch (Exception e) {
            logger.info("deleteData memoryCache fail {}", e.getMessage(), e);
            return new ResultBean(ErrorCode.REQ_PARAMETER_ERROR, e.getMessage());
        }
    }

    public ResultBean recoverQuoteData() {
        logger.info("重新写入行情信息...");
        quoteTickService.coverCache();
        logger.info("行情信息写入成功");
        return new ResultBean(ErrorCode.SUCCESS);
    }
}
