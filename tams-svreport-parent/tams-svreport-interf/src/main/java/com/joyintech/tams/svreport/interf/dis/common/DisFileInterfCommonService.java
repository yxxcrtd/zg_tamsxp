/*
 * 文件名：DisFileInterfCommonService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：信息披露系统V1.0
 * 修改人：ZhangYangFei
 * 修改时间：2019年5月8日
 * 修改内容：新建
 * 系统名称：信息披露系统V1.0
 */
 
package com.joyintech.tams.svreport.interf.dis.common;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.svreport.interf.chb.prodinfo.service.SvrProdOpenschFileImpService;

/**
 * 信批读取文件、数据入库共通servcie
 * 
 * @author ZhangYangFei
 * @version 1.0 2019年5月8日
 * @see DisFileInterfCommonService
 * @since 1.0
 */
@Service
public class DisFileInterfCommonService {
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
	private SvrProdOpenschFileImpService svrProdOpenschFileImpService;
    
    /**
     * 信批读取文件、数据入库
     * 
     * @param currentDate 处理日期
     * @param fileImpService 数据入库实现类
     * @param interfConstants 接口类型枚举
     * @return 成功or失败标识
     * @see
     * @since 1.0
     */
    public String readFileAndImportDb(String currentDate,FileImpService fileImpService,InterfDefine interfConstants,DictCodeListExt2 dictCodeListExt2) {
        logger.info("----"+ interfConstants.getName()+"[读取文件、数据入库]，开始-----");
        String success = "success";
        // 1.获得文件
        String containsfile = interfConstants.getCode();//文件名规范
        List<InterfFile> fileLists = fileImpService.queryAndDownloadFiles(currentDate,Arrays.asList(containsfile));
        if (CollectionUtils.isEmpty(fileLists)) {
            logger.info("readFileAndImportDb方法，获得文件=空，currentDate=" + currentDate);
            success =  "fail";
        } else {
            String impDbResult = "success";
            ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(interfConstants.getCode());
            for (InterfFile interfFile : fileLists) {
                Map<String, Object> parseResult = new HashMap<String, Object>();
                
                // 添加文件类型和接口类型 20190528 start 
                if(null != interfConfig ){
                    // 文件类型
                    interfFile.setFileType(interfConfig.getContentSrc());
                }else{
                    logger.info("CHB_INTERF_CONFIG缺少配置："+interfConstants.getCode());
                }
                // 接口类型
                interfFile.setInterfType(dictCodeListExt2.getValue());
                
                // 添加文件类型和接口类型 20190528 end
                try {
                    // 2.解析文件数据
                    parseResult = fileImpService.parseFile(interfFile);
                    if(null == parseResult.get("remoteDir") || "fail".equals(parseResult.get("remoteDir")+"")){
                        success = "fail";
                    }
                } catch (FileNotFoundException e) {
                	logger.error(e.getMessage(),e);
                    success = "fail";
                }
                if(!StringUtil.equals("fail", success)) {
                	 @SuppressWarnings("unchecked")
                     List<Map<String, String>> dataList = (List<Map<String, String>>) parseResult.get("dataList");
                     // 3.数据入库
                     impDbResult = fileImpService.importDB(null,dataList,interfFile, currentDate);
                     success = impDbResult;
                }
                // 移除文件
                interfFile.setMoveRemovePath(success);
                svrProdOpenschFileImpService.clearFile(interfFile);
            }
        }
        logger.info("----"+ interfConstants.getName()+"[读取文件、数据入库]，结束-----");
        return success;
    }
}
