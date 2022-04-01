package com.joyintech.tams.settle.batch.datadown.runner;

import com.joyintech.batch.batchbase.baseclass.BaseProcessor;
import com.joyintech.batch.batchbase.baseclass.BaseWriter;
import com.joyintech.batch.batchbase.baseclass.BatchBaseService;
import com.joyintech.batch.batchbase.baseclass.BatchBuilder;
import com.joyintech.batch.batchbase.baseclass.entity.NeedOperateEntity;
import com.joyintech.batch.batchbase.baseclass.entity.NeedToWriteEntity;
import com.joyintech.batch.batchbase.exception.BatchException;
import com.joyintech.framework.base.entity.SysBatchExecInfo;
import com.joyintech.framework.util.SpringContextUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.settle.batch.datadown.dto.DataToWriteEntity;
import com.joyintech.tams.settle.batch.datadown.listener.StlDataDownStepListener;
import com.joyintech.tams.settle.batch.datadown.processor.*;
import com.joyintech.tams.settle.batch.datadown.reader.*;
import com.joyintech.tams.settle.batch.datadown.writer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 何阳阳
 * @date 2020/7/2
 * @see StlDataDownTask
 * @since 1.0
 */
@Service("stlDataDownTask")
public class StlDataDownTask extends BatchBaseService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * BatchBuilder
     */
    @Autowired
    private BatchBuilder<StlDataDownReader, BaseProcessor<NeedOperateEntity, DataToWriteEntity>, BaseWriter<NeedToWriteEntity>> batchBuilder;

    @Override
    public JobExecution execute() throws BatchException {
        SysBatchExecInfo batchExecuteInfo = this.getBatchExecuteInfo();
        if (batchExecuteInfo == null) {
            logger.info("无启动参数，批处理退出。");
            return null;
        }
        /*
         * 此处的命名需要注意，同一个step的Reader、Processor、Writer必须要用这三种后缀名结尾，同时前半部分名称必须和stepName一致
         * 如：stepName：SzdJsmx，reader：SzdJsmxReader，processor：SzdJsmxProcessor，writer：SzdJsmxWriter
         */
        return batchBuilder.init(this)
                .batch("SzdAbcsj", getBean(SzdAbcsjReader.class), getBean(SzdAbcsjProcessor.class), getBean(SzdAbcsjWriter.class), new StlDataDownStepListener())
                .batch("SzdJsmx", getBean(SzdJsmxReader.class), getBean(SzdJsmxProcessor.class), getBean(SzdJsmxWriter.class), new StlDataDownStepListener())
                .batch("SzdSjsfx", getBean(SzdSjsfxReader.class), getBean(SzdSjsfxProcessor.class), getBean(SzdSjsfxWriter.class), new StlDataDownStepListener())
                .batch("SzdSjsjg", getBean(SzdSjsjgReader.class), getBean(SzdSjsjgProcessor.class), getBean(SzdSjsjgWriter.class), new StlDataDownStepListener())
                .batch("SzdSjsmx", getBean(SzdSjsmxReader.class), getBean(SzdSjsmxProcessor.class), getBean(SzdSjsmxWriter.class), new StlDataDownStepListener())
                .batch("SzdZqbd", getBean(SzdZqbdReader.class), getBean(SzdZqbdProcessor.class), getBean(SzdZqbdWriter.class), new StlDataDownStepListener())
                .batch("SzdZsmx", getBean(SzdZsmxReader.class), getBean(SzdZsmxProcessor.class), getBean(SzdZsmxWriter.class), new StlDataDownStepListener())
                .batch("SzdZqy", getBean(SzdZqyReader.class), getBean(SzdZqyProcessor.class), getBean(SzdZqyWriter.class), new StlDataDownStepListener())
                .batch("SzdSjsks", getBean(SzdSjsksReader.class), getBean(SzdSjsksProcessor.class), getBean(SzdSjsksWriter.class), new StlDataDownStepListener())
                .buildFlow("上中登股息红利税补缴数据导入", "SzdAbcsj")
                .buildFlow("上中登JSMX导入", "SzdJsmx")
                .buildFlow("深中登发行信息库导入", "SzdSjsfx")
                .buildFlow("深中登SJSJG导入", "SzdSjsjg")
                .buildFlow("深中登SJSMX导入", "SzdSjsmx")
                .buildFlow("上中登ZQBD导入", "SzdZqbd")
                .buildFlow("深中登征税明细信息库数据导入", "SzdZsmx")
                .buildFlow("上中登证券权益数据导入", "SzdZqy")
                .buildFlow("深中登发行可申购额度信息数据导入", "SzdSjsks")
                .parallelBuilder(this.getBatchTask().getBeanName() + StringUtil.SPACE + this.getBatchTask().getTaskId(),
                        "上中登股息红利税补缴数据导入","上中登JSMX导入","深中登发行信息库导入","深中登SJSJG导入","深中登SJSMX导入","上中登ZQBD导入","深中登征税明细信息库数据导入","上中登证券权益数据导入","深中登发行可申购额度信息数据导入","上中登权益登记通知信息TZXX接口")
//                .builder(this.getBatchTask().getBeanName() + StringUtil.SPACE + this.getBatchTask().getTaskId())
                .run();
    }

    private <T> T getBean(Class<T> beanType) {
        return SpringContextUtil.getBean(beanType);
    }
}