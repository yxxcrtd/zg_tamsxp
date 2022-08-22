package com.joyintech.tams.common;

import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.report.framework.thread.JoyinThreadPoolExecutor;
import com.joyintech.tams.svreport.interf.chb.liquidityriskinfo.service.SvrLiquidityRiskInfoFileImpService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class LiquidityRiskInfoFileImpService extends AbstractFileImpService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String[] CONTAINSFILE = {InterfDefine.流动性风险应对情况信息接口.getCode()};

    @Autowired
    private SvrLiquidityRiskInfoFileImpService svrLiquidityRiskInfoFileImpService;


    @Override
    public String getInfoByInterf(String currentDate) {
        Boolean success = true;

        List<InterfFile> fileLists = svrLiquidityRiskInfoFileImpService.queryAndDownloadFiles(currentDate, Arrays.asList(CONTAINSFILE));
        if (CollectionUtils.isEmpty(fileLists)) {
            logger.error("找不到接口文件");
            return "success";
        }

        // 分线程处理中债与信批(若该接口只有一个系统使用，则不需要分线程)
        ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.流动性风险应对情况信息接口.getCode());
        List<FileImpService> serviceList = new ArrayList<>();
        if (interfConfig.getSysScope() != null && interfConfig.getSysScope().length() == 3 && interfConfig.getSysScope().substring(0, 2).contains("1")) {
            serviceList.add(svrLiquidityRiskInfoFileImpService);
        }
        String result = "success";
        for (InterfFile interfFile : fileLists) {
            Map<String, Object> parseResult;
            try {
                interfFile.setFileType(interfConfig.getContentSrc());
                parseResult = svrLiquidityRiskInfoFileImpService.parseFile(interfFile);
                String parseFailResult = (String) parseResult.get("remoteDir");
                if ("fail".equals(parseFailResult)) {
                    interfFile.setMoveRemovePath("fail");
                    svrLiquidityRiskInfoFileImpService.clearFile(interfFile);
                    result = "fail";
                    continue;
                }
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
                result = "fail";
                continue;
            }
            List<Map<String, String>> dataList = (List<Map<String, String>>) parseResult.get("dataList");

            //创建线程池
            ExecutorService taskpool = new JoyinThreadPoolExecutor(serviceList.size());
            List<Callable<Boolean>> tasks = new ArrayList<>();
            //创建任务
            for (int i = 0; i < serviceList.size(); i++) {
                FileImpService service = serviceList.get(i);
                tasks.add(() -> {
                    Boolean res = true;
                    if ("fail".equals(service.importDB(service.copyDataList(dataList), interfFile,currentDate))) {
                        res = false;
                    }
                    return res;
                });
            }
            List<Future<Boolean>> taskResult;
            try {
                taskResult = taskpool.invokeAll(tasks);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                interfFile.setMoveRemovePath("fail");
                svrLiquidityRiskInfoFileImpService.clearFile(interfFile);
                result = "fail";
                Thread.currentThread().interrupt();
                continue;
            }
            //获取任务执行结果
            for (Future<Boolean> res : taskResult) {
                try {
                    success = success && res.get();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error(e.getMessage(), e);
                    //清除文件
                    interfFile.setMoveRemovePath("fail");
                    svrLiquidityRiskInfoFileImpService.clearFile(interfFile);
                    result = "fail";
                    Thread.currentThread().interrupt();
                    continue;
                }
            }
            taskpool.shutdown();
            String isSuccess = success ? "success" : "fail";
            interfFile.setMoveRemovePath(isSuccess);
            svrLiquidityRiskInfoFileImpService.clearFile(interfFile);
            success = true;
        }
        if ("fail".equals(result)) {
            return "fail";
        }
        return success ? "success" : "fail";
    }
}
