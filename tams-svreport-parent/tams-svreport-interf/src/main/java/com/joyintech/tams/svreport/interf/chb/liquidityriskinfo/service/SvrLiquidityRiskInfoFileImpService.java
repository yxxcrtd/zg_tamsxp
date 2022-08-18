package com.joyintech.tams.svreport.interf.chb.liquidityriskinfo.service;

import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.SysDataUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.BaseMapper;
import com.joyintech.tams.common.base.entity.LiquidityRiskInfo;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.interf.chb.liquidityriskinfo.dao.LiquidityRiskInfoInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

@Service
public class SvrLiquidityRiskInfoFileImpService extends FileImpService {


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 数据批量一次性insert条数(单条数据字段较多)
     */
    @Value("${application.reportInsertLongCount}")
    private int reportInsertLongCount;
    /**
     * 上游接口文件编码格式
     */
    @Value("${application.reportInterfFileEncoding}")
    public String reportInterfFileEncoding;
    /**
     * mybatis的sqlsession管理核心
     */
    @Autowired
    private SqlSessionTemplate template;


    @Autowired
    private BaseMapper baseMapper;

    private boolean excuteInfoByStep(List<Map<String, String>> listData, String saveFlag, String dataSource) {
        List<String> listColumn = baseMapper.getTableColumnList(InterfDefine.流动性风险应对情况信息接口.getCode());
        List<LiquidityRiskInfo> liquidityRiskInfoList = this.convertMapToEntity(listData, dataSource, listColumn);
        if (CollectionUtils.isNotEmpty(liquidityRiskInfoList)) {
            insertDataMerge(liquidityRiskInfoList, saveFlag);
        }
        return true;
    }

    @Override
    public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
        //解析成功或失败的标识
        boolean success = excuteInfoByStep(listData, saveFlag, dataSource);
        if (success) {
            return "success";
        }
        return "fail";
    }


    @Override
    public String importDB(List<Map<String, String>> listData, InterfFile interfFile,String date) {

        //数据校验
        checkDataCodeCheck(listData, interfFile.getFilename(), ChbCheckTableInfo.流动性风险应对情况信息表INFO);
        //待入库为空则直接返回
        if (CollectionUtils.isEmpty(listData)) {
            return "success";
        }
        String saveResult = saveData(listData, null, "I");
        // 更新系统配置时间
        ChbInterfUtil.updateInterfTimeBySysdate(InterfDefine.流动性风险应对情况信息接口.getCode());
        return saveResult;
    }


    public void insertDataMerge(List<LiquidityRiskInfo> liquidityRiskInfoList, String saveFlag) {
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            int count = 0;
            logger.debug("流动性风险应对情况信息插入开始--------------" + new Date());
            LiquidityRiskInfoInterfMapper batchMapper = session.getMapper(LiquidityRiskInfoInterfMapper.class);
            int batchCount = reportInsertLongCount == 0 ? 50 : reportInsertLongCount;
            int batchLastIndex = batchCount;
            for (int index = 0; index < liquidityRiskInfoList.size(); ) {
                if (batchLastIndex > liquidityRiskInfoList.size() - 1) {
                    batchLastIndex = liquidityRiskInfoList.size();
                    batchMapper.mergeInfoList(liquidityRiskInfoList.subList(index, batchLastIndex));
                    break;
                } else {
                    batchMapper.mergeInfoList(liquidityRiskInfoList.subList(index, batchLastIndex));
                    index = batchLastIndex;
                    batchLastIndex = index + batchCount;
                }
                count++;
                if (count % 20 == 0) {
                    session.commit();
                    session.clearCache();
                }
            }
            logger.debug("流动性风险应对情况信息插入结束--------------" + new Date());
            session.commit();
            session.clearCache();
        } finally {
            session.close();
        }
        logger.debug("流动性风险应对情况信息插入结束--------------" + new Date());
    }

    private List<LiquidityRiskInfo> convertMapToEntity(List<Map<String, String>> listData, String dataSource, List<String> listColumn) {

        List<LiquidityRiskInfo> entities = new LinkedList<>();
        for (Map<String, String> map : listData) {
            LiquidityRiskInfo liquidityRiskInfo = new LiquidityRiskInfo();
            for (String key : listColumn) {
                String value = map.get(key);
                Field field = ReflectionUtils.findField(liquidityRiskInfo.getClass(),
                        this.subBlank(StringUtil.lowerCase(key)));
                field.setAccessible(true);
                Type type = field.getGenericType();
                if ("java.math.BigDecimal".equals(type.getTypeName())) {
                    try {
                        field.set(liquidityRiskInfo, (value == null || "".equals(value)) ? BigDecimal.ZERO : new BigDecimal(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        return entities;
                    }
                } else if ("java.lang.Integer".equals(type.getTypeName())
                        || "int".equals(type.getTypeName())) {
                    try {
                        field.set(liquidityRiskInfo, (value == null || "".equals(value)) ? 0 : Integer.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        return entities;
                    }
                } else if ("java.lang.Long".equals(type.getTypeName())) {
                    try {
                        field.set(liquidityRiskInfo, (value == null || "".equals(value)) ? 0 : Long.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        return entities;
                    }
                } else if ("java.lang.Short".equals(type.getTypeName())) {
                    try {
                        field.set(liquidityRiskInfo, (value == null || "".equals(value)) ? 0 : Short.valueOf(value));
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        return entities;
                    }
                } else if ("java.util.Date".equals(type.getTypeName())) {
                    try {
                        if (value != null && "" != value) {
                            field.set(liquidityRiskInfo, DateUtil.parse(value));
                        }
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        return entities;
                    }

                } else {
                    try {
                        field.set(liquidityRiskInfo, (value == null || "".equals(value)) ? "" : value);
                    } catch (IllegalAccessException e) {
                        logger.error(e.getMessage(), e);
                        return entities;
                    }
                }
            }
            if (StringUtil.isNotBlank(map.get("ERRMSG"))) {
                liquidityRiskInfo.setStatus("A");
                liquidityRiskInfo.setErrMsg(map.get("ERRMSG"));
            } else {
                liquidityRiskInfo.setStatus("E");
            }
            liquidityRiskInfo.setRegCode(StringUtil.isBlank(liquidityRiskInfo.getRegCode())?" ":liquidityRiskInfo.getRegCode());
            liquidityRiskInfo.setDescribed(StringUtil.isBlank(liquidityRiskInfo.getDescribed())?" ":liquidityRiskInfo.getDescribed());
            liquidityRiskInfo.setDisposeResult(StringUtil.isBlank(liquidityRiskInfo.getDisposeResult())?" ":liquidityRiskInfo.getDisposeResult());
            liquidityRiskInfo.setDataSource(dataSource);
            liquidityRiskInfo.setSeqno(IDGenerator.generate(36));

            liquidityRiskInfo.setCreateUser("interf");
            liquidityRiskInfo.setUpdateUser("interf");
            entities.add(liquidityRiskInfo);

        }

        return entities;
    }
}
