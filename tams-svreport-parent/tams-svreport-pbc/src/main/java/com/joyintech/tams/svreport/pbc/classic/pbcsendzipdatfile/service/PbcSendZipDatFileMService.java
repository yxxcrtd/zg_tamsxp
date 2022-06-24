package com.joyintech.tams.svreport.pbc.classic.pbcsendzipdatfile.service;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.JoyinMessage;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.FileNameUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.PbcSendZipDatFileMapper;
import com.joyintech.tams.common.base.entity.PbcSendZipDatFile;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.systemtools.dto.CommonAjaxResult;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.*;


/**
 * 人行报表 服务
 * 〈功能详细描述〉
 * @author meifaxin
 * @version 1.0 2021年7月27日
 * @see PbcSendZipDatFileMService
 * @since 1.0
 */
@Service
public class PbcSendZipDatFileMService extends BaseService<PbcSendZipDatFile>{

	private static final String PRODUCT_BASE_INFO_NAME = "产品基本信息";
	private static final String PRODUCT_END_INFO_NAME = "终止信息";
	private static final String TIPS_SHEET_NAME_ERROR = "文件名称不规范，请确认文件名称是否包含产品基本信息或产品终止信息，再重新导入";


    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(PbcSendZipDatFileMService.class);

    private static final String TIPS_FILE_NAME_ERROR = "文件名称不规范，请确认文件名称后，重新导入。";

    @Autowired
    private PbcSendZipDatFileMapper pbcSendZipDatFileMapper;

    /**
     *
     * 业务画面查询事件
     *
     * @return
     * @see
     * @since 1.0
     */
    public Page<PbcSendZipDatFile> searchPage(Map<String, Object> param) {
        return restTemplate.postForPage(
                SvrServiceApplication.PBC_SERVICE_NAME + "/PbcSendZipDatFile/findPage",param,
                PbcSendZipDatFile.class);
    }



    /**
     *
     * 业务导入检查事件
     *
     * @return
     * @see
     * @since 1.0
     */
    public AjaxResult checkBaseInfoSubDat(String fileName) {
        Map<String,Object> param = new HashMap<>();
        param.put("dataList", ExcelUtil.getDataList1(fileName));
        String name = FileNameUtil.getName(fileName);
        List<ValidMessage> errorlistall = new ArrayList<>();
        String[] strName = name.split("_");
        if(strName.length > 0){
            AjaxResult ajaxResult = new AjaxResult();
            if(strName[0].contains(PRODUCT_BASE_INFO_NAME)){
                CommonAjaxResult commonAjaxResult = restTemplate.postForObject(
                        SvrServiceApplication.PBC_SERVICE_NAME + "/PbcPrdBaseInfoSubDat/checkBaseInfoSubDat?fileName=" + fileName, param,
                        CommonAjaxResult.class);
                if(commonAjaxResult.getData() != null && "CONFIRM_MSG".equals(commonAjaxResult.getData().toString())){

                    List<ValidMessage> errorList = commonAjaxResult.getErrorList();
                    if(CollectionUtil.isNotEmpty(errorList)){
                        StringBuilder builder = new StringBuilder();
                        builder.append(PRODUCT_BASE_INFO_NAME+" 已报送的数据，不支持修改。请在人行客户端直接修复！").append("<br>");
                        builder.append("已报送产品代码如下").append("<br>");
                        for (ValidMessage message : errorList) {
                            builder.append(message.getMessage()).append("<br>");
                        }
                        ajaxResult.setMessage(builder.toString());
                    }
                    ajaxResult.setMessageType(MessageType.错误消息);
                    ajaxResult.setStatuCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    return ajaxResult;
                }else {
                    if (null != commonAjaxResult.getErrorList() && commonAjaxResult.getErrorList().size() > 0) {
                        errorlistall.addAll(commonAjaxResult.getErrorList());
                    }
                }
                return commonAjaxResult;
            }
            if(strName[0].contains(PRODUCT_END_INFO_NAME)){
                CommonAjaxResult commonAjaxResult = restTemplate.postForObject(
                        SvrServiceApplication.PBC_SERVICE_NAME + "/PbcPrdBaseInfoEndSubDat/checkBaseInfoSubDat?fileName=" + fileName, param,
                        CommonAjaxResult.class);
                if(commonAjaxResult.getData() != null && "CONFIRM_MSG".equals(commonAjaxResult.getData().toString())){
                    List<ValidMessage> errorList = commonAjaxResult.getErrorList();
                    if(CollectionUtil.isNotEmpty(errorList)){
                        StringBuilder builder = new StringBuilder();
                        builder.append(PRODUCT_END_INFO_NAME+" 已报送的数据，不支持修改。请在人行客户端直接修复！").append("<br>");
                        builder.append("已报送产品代码如下").append("<br>");
                        for (ValidMessage message : errorList) {
                            builder.append(message.getMessage()).append("<br>");
                        }
                        ajaxResult.setMessage(builder.toString());
                    }
                    ajaxResult.setMessageType(MessageType.确认消息);
                    ajaxResult.setStatuCode(HttpStatus.OK.value());
                    return ajaxResult;
                }else{
                    if(null != commonAjaxResult.getErrorList() && commonAjaxResult.getErrorList().size()>0){
                        errorlistall.addAll(commonAjaxResult.getErrorList());
                    }
                }
                commonAjaxResult.setErrorList(errorlistall);
                return commonAjaxResult;
            }
        }
        return new AjaxResult(TIPS_SHEET_NAME_ERROR,MessageType.错误消息);
    }


    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importBaseInfoSubDat(String fileName) {
        Map<String,Object> param = new HashMap<String,Object>();
        String name = FileNameUtil.getName(fileName);
        String[] strName = name.split("_");
        List<ExcelValidDto> errorListall = new ArrayList<>();
        List<ExcelValidDto> retrunlist = new ArrayList<>();
        if(strName.length > 0){
            if(strName[0].contains(PRODUCT_BASE_INFO_NAME)){
                List<ExcelValidDto> errorList1 =  restTemplate.postForList(
                        SvrServiceApplication.PBC_SERVICE_NAME + "/PbcPrdBaseInfoSubDat/importBaseInfoSubDat?fileName="+fileName,param,
                        ExcelValidDto.class);
                retrunlist = errorList1;
                if(null != errorList1 && errorList1.size()>0){
                    errorListall.addAll(errorList1);
                }
            }

            if(strName[0].contains(PRODUCT_END_INFO_NAME)){
                List<ExcelValidDto> errorList2 =  restTemplate.postForList(
                        SvrServiceApplication.PBC_SERVICE_NAME + "/PbcPrdBaseInfoEndSubDat/importBaseInfoSubDat?fileName="+fileName,param,
                        ExcelValidDto.class);
                retrunlist = errorList2;
                if(null != errorList2 && errorList2.size()>0){
                    errorListall.addAll(errorList2);
                }
            }
            if(null != errorListall && errorListall.size()>0){
                return errorListall;
            }else{
                return retrunlist;
            }
        }
        List<ExcelValidDto> errorList = new ArrayList<>();
        ExcelValidDto validDto = new ExcelValidDto();
        validDto.setErrorInfo(TIPS_SHEET_NAME_ERROR);
        errorList.add(validDto);
        return errorList;

    }

    /**
     *
     * 业务导入检查事件
     *
     * @return
     * @see
     * @since 1.0
     */
    public AjaxResult checkMonthReport(String fileName) {
        AjaxResult commonAjaxResult = restTemplate.postForObject(
                SvrServiceApplication.PBC_SERVICE_NAME + "/NewPbcMonthReport/checkMonthReport?fileName=" + fileName, new HashMap<>(),
                CommonAjaxResult.class);
        if(commonAjaxResult.getData() != null && "CONFIRM_MSG".equals(commonAjaxResult.getData().toString())){
            AjaxResult ajaxResult = new AjaxResult();
            List<ValidMessage> errorList = commonAjaxResult.getErrorList();
            if(CollectionUtil.isNotEmpty(errorList)){
                StringBuilder builder = new StringBuilder();
                builder.append("统计模板数据已报送，不支持对数据进行维护!").append("<br>");
                builder.append("已报送模板如下").append("<br>");
                for (ValidMessage message : errorList) {
                    builder.append(message.getMessage()).append("<br>");
                }
                ajaxResult.setMessage(builder.toString());
            }
            ajaxResult.setMessageType(MessageType.错误消息);
            ajaxResult.setStatuCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ajaxResult;
        }
        if(commonAjaxResult.getData() != null && "TEMP_ERROR_MSG".equals(commonAjaxResult.getData().toString())){
            AjaxResult ajaxResult = new AjaxResult();
            List<ValidMessage> errorList = commonAjaxResult.getErrorList();
            StringBuilder builder = new StringBuilder();
            for (ValidMessage message : errorList) {
                builder.append(message.getMessage()).append("<br>");
            }
            ajaxResult.setMessage(builder.toString());
            ajaxResult.setMessageType(MessageType.错误消息);
            ajaxResult.setStatuCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ajaxResult;
        }
        return commonAjaxResult;
    }


    /**
     * 导入数据文件
     *
     * @param fileName 导入的文件名
     * @return 导入结果，包含验证内容，如果验证失败，返回message
     */
    public List<ExcelValidDto> importMonthReport(String fileName) {
        return restTemplate.postForList(
                SvrServiceApplication.PBC_SERVICE_NAME + "/NewPbcMonthReport/importMonthReport?fileName="+fileName,new HashMap<>(),
                ExcelValidDto.class);
    }
    /**
     * 生成压缩文件
     * @return 压缩文件存放地址
     */
    public AjaxResult createZipFile(Map<String, Object> param) {
        CommonAjaxResult ajaxResult = restTemplate.postForObject(
                SvrServiceApplication.PBC_SERVICE_NAME + "/PbcSendZipDatFile/createZipFile", param,
                CommonAjaxResult.class);
        Object data = ajaxResult.getData();
        String url = "";
        String flag = "";
        if(data != null){
            Map<String,String> rstMap = (HashMap)data;
            url = rstMap.get("result");
            flag = rstMap.get("flag");
        }
        ajaxResult.setData(url);
        if(CollectionUtils.isNotEmpty(ajaxResult.getErrorList()) && "NO_PBC_CODE".equals(flag)){
            List<ValidMessage> errorList = ajaxResult.getErrorList();
            StringBuilder builder = new StringBuilder();
            builder.append("以下内部产品代码没有对应的人行产品代码:<br>");
            for (ValidMessage message : errorList) {
                builder.append(message.getMessage() + "<br>");
            }
            builder.append("请等待老人行报送完成!");
            ajaxResult.setMessage(builder.toString());
            ajaxResult.setMessageType(MessageType.错误消息);
            ajaxResult.setStatuCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ajaxResult;
        }
        return ajaxResult;
    }




}
