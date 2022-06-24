package com.joyintech.tams.svreport.pbc.classic.prdinfoendsubdat.service;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.base.dao.PbcProdAssetpoolCodeMapper;
import com.joyintech.tams.common.base.dao.PbcSendZipDatFileMapper;
import com.joyintech.tams.common.base.entity.PbcProdAssetpoolCode;
import com.joyintech.tams.common.base.entity.PbcProdAssetpoolCodeKey;
import com.joyintech.tams.common.base.entity.PbcSendZipDatFile;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.svreport.pbc.classic.pbcNewDat.service.PbcDatUtil;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dto.PbcPrdBaseinfoSubDat;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.service.PbcPrdBaseinfoSubDatService;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubdat.dao.PbcPrdBaseinfoEndSubDatMapper;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubdat.dto.PbcPrdBaseinfoEndSubDat;
import com.joyintech.tams.svreport.pbc.classic.prdinfoendsubdat.dto.PbcPrdBaseinfoEndSubDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class PbcPrdBaseinfoEndSubDatService extends BaseService<PbcPrdBaseinfoSubDat> {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(PbcPrdBaseinfoSubDatService.class);

    private static final String TIPS_SUFFIX_ERROR = "只支持2003版的excel导入，请修改文件重新导入";
    private static final String NOTFILE_ERROR = "导入模板不一致，请重新选择文件！";

    private static final String PRODUCT_BASE_INFO_NAME = "产品基本信息";
    private static final String PRODUCT_END_INFO_NAME = "产品终止信息";
    @Autowired
    private PbcPrdBaseinfoEndSubDatMapper pbcPrdBaseinfoEndSubDatMapper;

    @Autowired
    private PbcSendZipDatFileMapper pbcSendZipDatFileMapper;

    @Autowired
    private PbcProdAssetpoolCodeMapper pbcProdAssetpoolCodeMapper;

    /**
     *
     * 业务导入检查事件
     *
     * @return
     * @see
     * @since 1.0
     */
    public AjaxResult checkBaseInfoSubDat(String fileName) {
        AjaxResult ajaxResult = new AjaxResult();
        if(!fileName.endsWith(".xls")){
            ValidMessage message = new ValidMessage();
            message.setMessage(TIPS_SUFFIX_ERROR);
            ajaxResult.setErrorList(Arrays.asList(message));
            return ajaxResult;
        }
        List<List<String>> dataList = null;
        /***TISHKS-1010 CLONE - 【产品问题】人行报送文件管理（资产管理 ），点击导入资管产品信息，导入模板不一致，也可导入并弹出相关校验*/
        List<List<String>> topList = null;
        if(fileName.contains(PRODUCT_BASE_INFO_NAME)){//有两个sheet页的情况
            try {
                dataList = ExcelUtil.readToList(
                        fileName, null, null, 1, 1, 0);
                topList = Arrays.asList(ExcelUtil.readToList(
                        fileName, null, null, 1, 0, 0).get(0));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            dataList = ExcelUtil.getDataList1(fileName);
            topList = ExcelUtil.getDataList1top(fileName);
        }
        if(StringUtil.isEmptyList(topList)||topList.get(0).size()!=8){
            ValidMessage message = new ValidMessage();
            message.setMessage(NOTFILE_ERROR);
            ajaxResult.setErrorList(Arrays.asList(message));
            return ajaxResult;
        }
        /***TISHKS-1010 CLONE - 【产品问题】人行报送文件管理（资产管理 ），点击导入资管产品信息，导入模板不一致，也可导入并弹出相关校验*/

        if(CollectionUtil.isNotEmpty(dataList)){
            List<PbcPrdBaseinfoEndSubDto> pbcPrdBaseinfoSubDatDtos = new ArrayList<>();
            //将excel中的行数据转换为实体
            List<ExcelValidDto> excelValidDtos = convertToEntity(dataList, pbcPrdBaseinfoSubDatDtos);
            if(!excelValidDtos.isEmpty()){
                List<ValidMessage> validMessages = new ArrayList<>();
                for (ExcelValidDto excelValidDto : excelValidDtos) {
                    ValidMessage message = new ValidMessage();
                    message.setMessage(excelValidDto.getErrorInfo());
                    validMessages.add(message);
                }
                ajaxResult.setErrorList(validMessages);
                return ajaxResult;
            }
            //组装查询条件理财产品代码
            List<String> prodIds = new ArrayList<>();
            for (PbcPrdBaseinfoEndSubDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoSubDatDtos) {
                String prodId = pbcPrdBaseinfoSubDatDto.getProdId();
                if(StringUtil.isNotEmpty(prodId)){
                    if(!prodIds.contains(prodId)){
                        prodIds.add(prodId);
                    }
                }
            }
            List<PbcPrdBaseinfoEndSubDat> pbcPrdBaseinfoSubDats = new ArrayList<>();
            //根据理财产品代码查询数据
            if(CollectionUtil.isNotEmpty(prodIds)){
                boolean flag = false;
                pbcPrdBaseinfoSubDats = pbcPrdBaseinfoEndSubDatMapper.queryPbcPrdBaseInfoByprodId(prodIds);
                StringBuilder builder = new StringBuilder();
                builder.append("已报送的数据，不支持修改。请在人行客户端直接修复！").append("\n");
                for (PbcPrdBaseinfoEndSubDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoSubDatDtos) {
                    Optional<PbcPrdBaseinfoEndSubDat> any = pbcPrdBaseinfoSubDats.stream().filter(baseinfoDat -> baseinfoDat.getProdId().equals(pbcPrdBaseinfoSubDatDto.getProdId())).findAny();
                    if(any.isPresent()){
                        PbcPrdBaseinfoEndSubDat pbcPrdBaseinfoSubDat = any.get();
                        String sendStatus = pbcPrdBaseinfoSubDat.getSendStatus();
                        if(!DictCodeListExt2.报送状态_未报送.getValue().equals(sendStatus)){
                            flag = true;
                            builder.append(pbcPrdBaseinfoSubDat.getProdId()).append("\n");
                        }
                    }
                }
                if(flag){
                    ajaxResult.setMessage(builder.toString());
                    ajaxResult.setMessageType(MessageType.确认消息);
                    ajaxResult.setStatuCode(HttpStatus.OK.value());
                    return ajaxResult;
                }
            }
        }else{
            ValidMessage message = new ValidMessage();
            message.setMessage("模板为空，请输入数据！");
            ajaxResult.setErrorList(Arrays.asList(message));
            return ajaxResult;
        }
        ajaxResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
        return ajaxResult;

    }

    public List<ExcelValidDto> importBaseInfoSubDat(String fileName){
        List<ExcelValidDto> errorList = new ArrayList<>();
        List<List<String>> dataList = null;
        if(fileName.contains(PRODUCT_BASE_INFO_NAME)){//有两个sheet页的情况
            try {
                dataList = ExcelUtil.readToList(
                        fileName, null, null, 1, 1, 0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            dataList = ExcelUtil.getDataList1(fileName);
        }
        if(!StringUtil.isEmptyList(dataList)){
            List<PbcPrdBaseinfoEndSubDto> pbcPrdBaseinfoEndSubDtos = new ArrayList<>();
            //将excel中的行数据转换为实体
            List<ExcelValidDto> excelValidDtos = convertToEntity(dataList, pbcPrdBaseinfoEndSubDtos);
            if(!excelValidDtos.isEmpty()){
                return excelValidDtos;
            }
            //组装查询条件理财产品代码
            List<String> prodIds = new ArrayList<>();
            for (PbcPrdBaseinfoEndSubDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoEndSubDtos) {
                String prodId = pbcPrdBaseinfoSubDatDto.getProdId();
                if(StringUtil.isNotEmpty(prodId)){
                    if(!prodIds.contains(prodId)){
                        prodIds.add(prodId);
                    }
                }
            }
            List<PbcPrdBaseinfoEndSubDat> pbcPrdBaseinfoSubDats = new ArrayList<>();
            //根据理财产品代码查询数据
            if(CollectionUtil.isNotEmpty(prodIds)){
                pbcPrdBaseinfoSubDats = pbcPrdBaseinfoEndSubDatMapper.queryPbcPrdBaseInfoByprodId(prodIds);
            }
            //未报送、数据需要删除的id
            List<PbcPrdBaseinfoEndSubDat> baseinfoSubDatsNoSendProdIds = new ArrayList<>();
            //未报送、数据需要更新的id
            List<PbcPrdBaseinfoEndSubDat> baseinfoSubDatsNoSendUpdate = new ArrayList<>();
            //已报送 数据有修改
//            List<PbcPrdBaseinfoEndSubDat> baseinfoSubDatsSend = new ArrayList<>();
            //新建数据
            List<PbcPrdBaseinfoEndSubDat> baseinfoSubDatsNew = new ArrayList<>();
            for (PbcPrdBaseinfoEndSubDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoEndSubDtos) {
                Optional<PbcPrdBaseinfoEndSubDat> any = pbcPrdBaseinfoSubDats.stream()
                        .filter(baseinfoDat -> baseinfoDat.getProdId().equals(pbcPrdBaseinfoSubDatDto.getProdId())).findAny();
                if(any.isPresent()){
                    PbcPrdBaseinfoEndSubDat pbcPrdBaseinfoSubDat = any.get();
                    PbcPrdBaseinfoEndSubDat baseinfoSubDat = updatePbcPrdBaseinfoEndSubDat(pbcPrdBaseinfoSubDatDto, pbcPrdBaseinfoSubDat);
                    //设置人行产品代码
                    baseinfoSubDat.setPbcProdId(pbcPrdBaseinfoSubDat.getPbcProdId());
                    //设置报送状态为未报送
                    baseinfoSubDat.setSendStatus(pbcPrdBaseinfoSubDat.getSendStatus());
                    //设置数据状态为新建
                    baseinfoSubDat.setStatus(pbcPrdBaseinfoSubDat.getStatus());
                    //设置报送流水号为查询的流水号
                    baseinfoSubDat.setSumitSeq(pbcPrdBaseinfoSubDat.getSumitSeq());
                    //设置压缩包编码
                    baseinfoSubDat.setDatZipCode(pbcPrdBaseinfoSubDat.getDatZipCode());
                    baseinfoSubDatsNoSendUpdate.add(baseinfoSubDat);
                }else{
                    //新建数据
                    PbcPrdBaseinfoEndSubDat baseinfoSubDat = updatePbcPrdBaseinfoEndSubDat(pbcPrdBaseinfoSubDatDto, new PbcPrdBaseinfoEndSubDat());
                    //设置人行产品代码
                    baseinfoSubDat.setPbcProdId(null);
                    //设置报送状态为未报送
                    baseinfoSubDat.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
                    //设置数据状态为新建
                    baseinfoSubDat.setStatus(DictCodeList.数据状态_新建.getValue());
                    //设置报送流水号   重新生成报送流水号
                    baseinfoSubDat.setSumitSeq(null);
                    //设置压缩包编码为空
                    baseinfoSubDat.setDatZipCode(null);
                    baseinfoSubDatsNew.add(baseinfoSubDat);
                }
            }
            if(CollectionUtil.isNotEmpty(baseinfoSubDatsNoSendUpdate)){
                for (PbcPrdBaseinfoEndSubDat subDat : baseinfoSubDatsNoSendUpdate) {
                    subDat.setRegistSeqno(UUID.randomUUID().toString());
                    pbcPrdBaseinfoEndSubDatMapper.updateByDatZipCodeAndProdId(subDat);
                }
            }
            List<PbcPrdBaseinfoEndSubDat> endListAll = new ArrayList<>();
            //加入新建的数据
            endListAll.addAll(baseinfoSubDatsNew);
            if(CollectionUtil.isNotEmpty(endListAll)){
                //1:分配不生成压缩文件
                String zipType = "1";
                //调用来源是否批处理   01：是批处理调用   ； 02：报送文件管理页面调用
                String srcModel = "02";
                //获取系统参数 1:按照业务日期进行压缩；  2：整体压缩
                String pbc_send_zip_model = CacheUtil.getSysParam("PBC_SEND_ZIP_MODEL");
                String zipModel = StrUtil.isEmpty(pbc_send_zip_model) ? "2" : pbc_send_zip_model;
                //登录用户的机构代码
                String orgCode = SecurityUtil.getLoginUser().getOrgCode();
                //调用【DAT生成共通处理-产品端】
                Map<String,ArrayList> resultMap = new HashMap<>();
                try {
                    //do not ask me,most of bugs by this method,I can not controll it,sry!
                    resultMap = PbcDatUtil.genDatZipList(zipType,zipModel,srcModel,orgCode,new ArrayList<>(),endListAll);
                }catch (Exception e){
                    logger.error("生成dat文件异常",e);
                    ExcelValidDto dto = new ExcelValidDto();
                    dto.setErrorInfo("生成dat文件异常");
                    errorList.add(dto);
                    return errorList;
                }
                List<PbcPrdBaseinfoEndSubDat> endList = resultMap.get("endList");
                List<PbcSendZipDatFile> zipList = resultMap.get("zipList");
                List<String> reqIds = new ArrayList<>();
                for (PbcPrdBaseinfoEndSubDat subDat : endList) {
                    if(StrUtil.isNotEmpty(subDat.getProdId())){
                        baseinfoSubDatsNoSendProdIds.add(subDat);
                    }else{
                        reqIds.add(subDat.getRegistSeqno());
                    }

                }
                //删除未报送有修改的记录 并重新插入
                if(CollectionUtil.isNotEmpty(baseinfoSubDatsNoSendProdIds)){
                    pbcPrdBaseinfoEndSubDatMapper.deleteBatchByProdId(baseinfoSubDatsNoSendProdIds);
                }
                if(CollectionUtil.isNotEmpty(reqIds)){
                    //god bless it
                    for (String reqId : reqIds) {
                        pbcPrdBaseinfoEndSubDatMapper.deleteByPrimaryKey(reqId);
                    }
                }
                //插入新的数据
                if(CollectionUtil.isNotEmpty(endList)){
                    for (PbcPrdBaseinfoEndSubDat pbcPrdBaseinfoEndSubDat : endList) {
                        pbcPrdBaseinfoEndSubDat.setRegistSeqno(UUID.randomUUID().toString());
                        pbcPrdBaseinfoEndSubDat.setOrgCode(orgCode);
                        pbcPrdBaseinfoEndSubDat.setCreateUser(SecurityUtil.getLoginUser().getUserName());
                        pbcPrdBaseinfoEndSubDat.setCreateTime(new Date());
                        pbcPrdBaseinfoEndSubDat.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
                        pbcPrdBaseinfoEndSubDat.setUpdateTime(new Date());
                        pbcPrdBaseinfoEndSubDatMapper.insert(pbcPrdBaseinfoEndSubDat);
                    }
                }
                //插入新的数据
                if(CollectionUtil.isNotEmpty(zipList)){
                    List<PbcSendZipDatFile> insertList = new ArrayList<>();
                    List<PbcSendZipDatFile> updateList = new ArrayList<>();
                    for (PbcSendZipDatFile pbcSendZipDatFile : zipList) {
                        if(StrUtil.isEmpty(pbcSendZipDatFile.getRegistSeqno())){
                            pbcSendZipDatFile.setRegistSeqno(UUID.randomUUID().toString());
                        }
                       /* String datZipPath = CacheUtil.getSysParam("SVR_PBC_DAT_FILEPATH");
                        pbcSendZipDatFile.setDatZipPath(StrUtil.isEmpty(datZipPath) ? "/app/etliu/pbcdat/" : datZipPath);*/
                        //数据权限模式（01：银行模式，02：联社模式）
                        String authorityType = CacheUtil.getSysParam("AUTHORITY_TYPE");
                        logger.info("数据权限模式:{}",authorityType);
                        PbcSendZipDatFile sendZipDatFile = null;
                        if("02".equals(authorityType)){
                            //机构
                            sendZipDatFile = pbcSendZipDatFileMapper
                                    .selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(), pbcSendZipDatFile.getDatZipDatType(),orgCode);
                        }else{
                            sendZipDatFile = pbcSendZipDatFileMapper
                                    .selectByDatZipCode(pbcSendZipDatFile.getDatZipCode(), pbcSendZipDatFile.getDatZipDatType(),null);
                        }
                        if(sendZipDatFile == null){
                            insertList.add(pbcSendZipDatFile);
                        }else{
                            pbcSendZipDatFile.setRegistSeqno(sendZipDatFile.getRegistSeqno());
                            updateList.add(pbcSendZipDatFile);
                        }
                    }
                    for (PbcSendZipDatFile sendZipDatFile : insertList) {
                        pbcSendZipDatFileMapper.insert(sendZipDatFile);
                    }
                    for (PbcSendZipDatFile sendZipDatFile : updateList) {
                        pbcSendZipDatFileMapper.updateByPrimaryKey(sendZipDatFile);
                    }
                }
            }
            return errorList;
        }else{
            ExcelValidDto excelValidDto = new ExcelValidDto();
            String errorMsg = "模板为空，请输入数据！";
            excelValidDto.setErrorInfo(errorMsg);
            errorList.add(excelValidDto);
            return errorList;
        }
    }


    private PbcPrdBaseinfoEndSubDat updatePbcPrdBaseinfoEndSubDat(PbcPrdBaseinfoEndSubDto pbcPrdBaseinfoSubDatDto,PbcPrdBaseinfoEndSubDat pbcPrdBaseinfoSubDat){
        BeanUtil.copyPropertiesIgnoreNull(pbcPrdBaseinfoSubDat,pbcPrdBaseinfoSubDatDto);
        pbcPrdBaseinfoSubDat.setCustomProfitCny(pbcPrdBaseinfoSubDatDto.getCustomProfitCny());
        return pbcPrdBaseinfoSubDat;
    }

    private List<ExcelValidDto> convertToEntity(List<List<String>> dataList,List<PbcPrdBaseinfoEndSubDto> pbcPrdBaseinfoSubDatDtos){
        List<ExcelValidDto> checkResult = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(dataList)){
            for (int i = 0; i < dataList.size(); i++ ) {
                int rowNumer = i + 1;
                // 验证消息
                List<ExcelValidDto> errorMessage = new ArrayList<>();
                List<String> rowsData = dataList.get(i);
                PbcPrdBaseinfoEndSubDto dto = new PbcPrdBaseinfoEndSubDto();
                int row = 0;
                //序号
                String number = rowsData.get(row++);
                dto.setNumber(number);
                //人行产品代码
                String pbcProdId = rowsData.get(row++);
                dto.setPbcProdId(pbcProdId);
                //发行机构内部产品代码
                String prodId = rowsData.get(row++);
                if(checkIsNotEmpty(prodId,rowNumer,"发行机构内部产品代码",errorMessage)){
                    PbcProdAssetpoolCodeKey codeKey = new PbcProdAssetpoolCodeKey();
                    codeKey.setInsideProdId(prodId);
                    codeKey.setCodeType(DictCodeListExt3.代码类型_产品.getValue());
                    PbcProdAssetpoolCode pbcProdAssetpoolCode = pbcProdAssetpoolCodeMapper.selectByPrimaryKey(codeKey);
                    if(pbcProdAssetpoolCode == null || StrUtil.isEmpty(pbcProdAssetpoolCode.getPbcCode())){
                        errorMessage.add(new ExcelValidDto("发行机构内部产品代码",String.valueOf(rowNumer),"第"+rowNumer+"行:发行机构内部产品代码["+prodId+"]无对应人行代码！"));
                    }else{
                        dto.setProdId(prodId);
                    }
                }
                //产品实际终止日期
                String mdate = rowsData.get(row++);
                if(checkIsNotEmpty(mdate,rowNumer,"产品实际终止日期",errorMessage)){
                    if(checkDate(mdate)){
                        dto.setMdate(DateUtil.parse(mdate));
                    }else{
                        errorMessage.add(new ExcelValidDto("产品实际终止日期",String.valueOf(rowNumer),"第"+rowNumer+"行:产品预计终止日期格式不正确！"));
                    }
                }
                //兑付客户收益率
                String cusYearYield = rowsData.get(row++);
                if(checkIsNotEmpty(cusYearYield,rowNumer,"兑付客户收益率",errorMessage)){
                    if(checkFigure(cusYearYield)){
                        dto.setCusYearYield(new BigDecimal(cusYearYield));
                    }else{
                        errorMessage.add(new ExcelValidDto("兑付客户收益率",String.valueOf(rowNumer),"第"+rowNumer+"行:兑付客户收益率非数字！"));
                    }
                }

                //币种代码
                String ccy = rowsData.get(row++);
                if(checkIsNotEmpty(ccy,rowNumer,"币种代码",errorMessage)){
                    dto.setCcy(dealCcyInfo(ccy));
                }

                //兑付客户收益
                String customProfit = rowsData.get(row++);
                if(checkIsNotEmpty(customProfit,rowNumer,"兑付客户收益",errorMessage)){
                    if(checkFigure(customProfit)){
                        dto.setCustomProfit(new BigDecimal(customProfit));
                    }else{
                        errorMessage.add(new ExcelValidDto("兑付客户收益",String.valueOf(rowNumer),"第"+rowNumer+"行:兑付客户收益非数字！"));
                    }
                }
                //兑付客户收益折人民币
                String customProfitCny = rowsData.get(row++);
                if(checkIsNotEmpty(customProfit,rowNumer,"兑付客户收益折人民币",errorMessage)){
                    if(checkFigure(customProfitCny)){
                        dto.setCustomProfitCny(new BigDecimal(customProfitCny));
                    }else{
                        errorMessage.add(new ExcelValidDto("兑付客户收益折人民币",String.valueOf(rowNumer),"第"+rowNumer+"行:兑付客户收益折人民币非数字！"));
                    }
                }

                if(CollectionUtil.isNotEmpty(errorMessage)){
                    checkResult.addAll(errorMessage);
                }else{
                    pbcPrdBaseinfoSubDatDtos.add(dto);
                }
            }
        }
        return checkResult;

    }

    public String dealCcyInfo(String msg){
        if(StrUtil.isNotEmpty(msg)){
            return msg.substring(0,3);
        }
        return null;
    }

    /**
     * 判断是否为数字
     *
     * @param
     * @return 返回boolean
     */
    private boolean checkFigure(String figure) {
        try{
            new BigDecimal(figure);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    /**
     * 判断是否为空
     * @return 返回boolean
     */
    private boolean checkIsNotEmpty(String value,int i,String desc,List<ExcelValidDto> errorMessage){
        if(StrUtil.isEmpty(value)){
            errorMessage.add(new ExcelValidDto(desc,String.valueOf(i),"第"+i+"行:"+desc+"不能为空！"));
            return false;
        }
        return true;
    }

    /**
     * 日期格式校验
     *
     * @param
     * @return 返回boolean
     */
    private boolean checkDate(String date) {
        boolean flag = true;
        if(!DateUtil.isDateStr(date, DateUtil.DEFAULT_DATE_FORMAT_EN,DateUtil.DEFAULT_ANOTHER_FORMAT)){
            flag = false;
        }else{
            date = date.replace("-", "").replace("/", "");
            //年
            int year = Integer.valueOf(date.substring(0, 4));
            //月
            int month = Integer.valueOf(date.substring(4, 6));
            //日
            int day = Integer.valueOf(date.substring(6));
            if(month < 1 || month > 12){
                flag = false;
            }else{
                if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
                    if(day < 1 || day > 31){
                        flag = false;
                    }
                }else if(month == 4 || month == 6 || month == 9 || month == 11){
                    if(day < 1 || day > 30){
                        flag = false;
                    }
                }else if(month == 2){
                    if((year % 4 == 0 && year % 100 != 0) ||year % 400 == 0){
                        if(day < 1 || day > 29){
                            flag = false;
                        }
                    }else{
                        if(day < 1 || day > 28){
                            flag = false;
                        }
                    }
                }
            }
        }
        return flag;
    }
}
