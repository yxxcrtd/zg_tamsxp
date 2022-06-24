package com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.service;


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
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.dao.PbcPrdBaseinfoSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dao.PbcPrdBaseinfoSubDatMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dto.PbcPrdBaseinfoSubDat;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosubdat.dto.PbcPrdBaseinfoSubDatDto;
import com.joyintech.tams.svreport.pbc.common.enums.PbcDictCodeEnums;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class PbcPrdBaseinfoSubDatService extends BaseService<PbcPrdBaseinfoSubDat> {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(PbcPrdBaseinfoSubDatService.class);

    private static final String TIPS_SUFFIX_ERROR = "只支持2003版(文件后缀为.xls)的excel导入，请修改文件重新导入";


    private static final String NOTFILE_ERROR = "导入模板不一致，请重新选择文件！";


    @Autowired
    private PbcPrdBaseinfoSubDatMapper pbcPrdBaseinfoSubDatMapper;

    @Autowired
    private PbcSendZipDatFileMapper pbcSendZipDatFileMapper;

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
        /***TISHKS-1010 CLONE - 【产品问题】人行报送文件管理（资产管理 ），点击导入资管产品信息，导入模板不一致，也可导入并弹出相关校验*/

        List<List<String>> topList = ExcelUtil.getDataList1top(fileName);
        if(StringUtil.isEmptyList(topList)||topList.get(0).size()!=34){
            ValidMessage message = new ValidMessage();
            message.setMessage(NOTFILE_ERROR);
            ajaxResult.setErrorList(Arrays.asList(message));
            return ajaxResult;
        }
        /***TISHKS-1010 CLONE - 【产品问题】人行报送文件管理（资产管理 ），点击导入资管产品信息，导入模板不一致，也可导入并弹出相关校验*/

        List<List<String>> dataList = ExcelUtil.getDataList1(fileName);
        if(CollectionUtil.isNotEmpty(dataList)){
            List<PbcPrdBaseinfoSubDatDto> pbcPrdBaseinfoSubDatDtos = new ArrayList<>();
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
            for (PbcPrdBaseinfoSubDatDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoSubDatDtos) {
                String prodId = pbcPrdBaseinfoSubDatDto.getProdId();
                if(StringUtil.isNotEmpty(prodId)){
                    if(!prodIds.contains(prodId)){
                        prodIds.add(prodId);
                    }
                }
            }
            List<PbcPrdBaseinfoSubDat> pbcPrdBaseinfoSubDats = new ArrayList<>();
            //根据理财产品代码查询数据
            if(CollectionUtil.isNotEmpty(prodIds)){
                boolean flag = false;
                pbcPrdBaseinfoSubDats = pbcPrdBaseinfoSubDatMapper.queryPbcPrdBaseInfoByprodId(prodIds);
                List<ValidMessage> validMessages = new ArrayList<>();
                for (PbcPrdBaseinfoSubDatDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoSubDatDtos) {
                    Optional<PbcPrdBaseinfoSubDat> any = pbcPrdBaseinfoSubDats.stream().filter(baseinfoDat -> baseinfoDat.getProdId().equals(pbcPrdBaseinfoSubDatDto.getProdId())).findAny();
                    if(any.isPresent()){
                        PbcPrdBaseinfoSubDat pbcPrdBaseinfoSubDat = any.get();
                        String sendStatus = pbcPrdBaseinfoSubDat.getSendStatus();
                        if(!DictCodeListExt2.报送状态_未报送.getValue().equals(sendStatus)){
                            flag = true;
                            ValidMessage message = new ValidMessage();
                            message.setMessage(pbcPrdBaseinfoSubDat.getProdId());
                            validMessages.add(message);
                        }
                    }
                }
                if(flag){
                    ajaxResult.setData("CONFIRM_MSG");
                    ajaxResult.setErrorList(validMessages);
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

    public List<ExcelValidDto> importBaseInfoSubDat(String fileName) {
        List<ExcelValidDto> errorList = new ArrayList<>();
        List<List<String>> dataList = ExcelUtil.getDataList1(fileName);
        if(!StringUtil.isEmptyList(dataList)){
            List<PbcPrdBaseinfoSubDatDto> pbcPrdBaseinfoSubDatDtos = new ArrayList<>();
            //将excel中的行数据转换为实体
            List<ExcelValidDto> excelValidDtos = convertToEntity(dataList, pbcPrdBaseinfoSubDatDtos);
            if(!excelValidDtos.isEmpty()){
                return excelValidDtos;
            }
            //组装查询条件理财产品代码
            List<String> prodIds = new ArrayList<>();
            for (PbcPrdBaseinfoSubDatDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoSubDatDtos) {
                String prodId = pbcPrdBaseinfoSubDatDto.getProdId();
                if(StringUtil.isNotEmpty(prodId)){
                    if(!prodIds.contains(prodId)){
                        prodIds.add(prodId);
                    }
                }
            }
            List<PbcPrdBaseinfoSubDat> pbcPrdBaseinfoSubDats = new ArrayList<>();
            //根据理财产品代码查询数据
            if(CollectionUtil.isNotEmpty(prodIds)){
                pbcPrdBaseinfoSubDats = pbcPrdBaseinfoSubDatMapper.queryPbcPrdBaseInfoByprodId(prodIds);
            }
            //未报送、数据需要删除的id
            List<PbcPrdBaseinfoSubDat> baseinfoSubDatsNoSendProdIds = new ArrayList<>();
            //未报送、数据需要更新的id
            List<PbcPrdBaseinfoSubDat> baseinfoSubDatsNoSendUpdate = new ArrayList<>();
            //已报送 数据有修改
            //List<PbcPrdBaseinfoSubDat> baseinfoSubDatsSend = new ArrayList<>();
            //新建数据
            List<PbcPrdBaseinfoSubDat> baseinfoSubDatsNew = new ArrayList<>();
            for (PbcPrdBaseinfoSubDatDto pbcPrdBaseinfoSubDatDto : pbcPrdBaseinfoSubDatDtos) {
                Optional<PbcPrdBaseinfoSubDat> any = pbcPrdBaseinfoSubDats.stream().filter(baseinfoDat -> baseinfoDat.getProdId().equals(pbcPrdBaseinfoSubDatDto.getProdId())).findAny();
                if(any.isPresent()){
                    PbcPrdBaseinfoSubDat pbcPrdBaseinfoSubDat = any.get();
                    PbcPrdBaseinfoSubDat baseinfoSubDat = new PbcPrdBaseinfoSubDat();
                    try {
                        baseinfoSubDat = updatePbcPrdBaseinfoSubDat(pbcPrdBaseinfoSubDatDto, pbcPrdBaseinfoSubDat);
                    }catch (Exception e){
                        logger.error("实体类转换异常",e);
                        ExcelValidDto excelValidDto = new ExcelValidDto();
                        String errorMsg = "实体类转换异常！";
                        excelValidDto.setErrorInfo(errorMsg);
                        errorList.add(excelValidDto);
                        return errorList;
                    }
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
                    PbcPrdBaseinfoSubDat baseinfoSubDat = new PbcPrdBaseinfoSubDat();
                    try {
                        baseinfoSubDat = updatePbcPrdBaseinfoSubDat(pbcPrdBaseinfoSubDatDto, new PbcPrdBaseinfoSubDat());
                    }catch (Exception e){
                        logger.error("实体类转换异常",e);
                        ExcelValidDto excelValidDto = new ExcelValidDto();
                        String errorMsg = "实体类转换异常！";
                        excelValidDto.setErrorInfo(errorMsg);
                        errorList.add(excelValidDto);
                        return errorList;
                    }
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
                for (PbcPrdBaseinfoSubDat subDat : baseinfoSubDatsNoSendUpdate) {
                    subDat.setRegistSeqno(UUID.randomUUID().toString());
                    subDat.setProfitType("03");
                    subDat.setBusiModel("3");
                    pbcPrdBaseinfoSubDatMapper.updateByDatZipCodeAndProdId(subDat);
                }
            }
            List<PbcPrdBaseinfoSubDat> prdListAll = new ArrayList<>();
            //加入新建的数据
            prdListAll.addAll(baseinfoSubDatsNew);
            if(CollectionUtil.isNotEmpty(prdListAll)){
                //1:分配不生成压缩文件
                String zipType = "1";
                //调用来源是否批处理   01：是批处理调用   ； 02：报送文件管理页面调用
                String srcModel = "02";
                //获取系统参数 1:按照业务日期进行压缩；  2：整体压缩
                String pbc_send_zip_model = CacheUtil.getSysParam("PBC_SEND_ZIP_MODEL");
                String zipModel = StrUtil.isEmpty(pbc_send_zip_model) ? "2" : pbc_send_zip_model;
                //登录用户的机构代码
                String orgCode = SecurityUtil.getLoginUser().getOrgCode();
                Map<String,ArrayList> resultMap = new HashMap<>();
                try {
                    resultMap = PbcDatUtil.genDatZipList(zipType,zipModel,srcModel,orgCode,prdListAll,new ArrayList<>());
                }catch (Exception e){
                    logger.error("生成dat文件异常",e);
                    ExcelValidDto dto = new ExcelValidDto();
                    dto.setErrorInfo("生成dat文件异常");
                    errorList.add(dto);
                    return errorList;
                }
                List<PbcPrdBaseinfoSubDat> prdList = resultMap.get("prdList");
                List<PbcSendZipDatFile> zipList = resultMap.get("zipList");
                List<String> reqIds = new ArrayList<>();
                for (PbcPrdBaseinfoSubDat subDat : prdList) {
                    if(StrUtil.isNotEmpty(subDat.getProdId())){
                        baseinfoSubDatsNoSendProdIds.add(subDat);
                    }else{
                        reqIds.add(subDat.getRegistSeqno());
                    }

                }
                //删除未报送有修改的记录
                if(CollectionUtil.isNotEmpty(baseinfoSubDatsNoSendProdIds)){
                    pbcPrdBaseinfoSubDatMapper.deleteByProdid(baseinfoSubDatsNoSendProdIds);
                }
                if(CollectionUtil.isNotEmpty(reqIds)){
                    for (String reqId : reqIds) {
                        pbcPrdBaseinfoSubDatMapper.deleteByPrimaryKey(reqId);
                    }
                }
                //插入新的数据
                if(CollectionUtil.isNotEmpty(prdList)){
                    for (PbcPrdBaseinfoSubDat pbcPrdBaseinfoSubDat : prdList) {
                        pbcPrdBaseinfoSubDat.setRegistSeqno(UUID.randomUUID().toString());
                        //默认为非保本
                        pbcPrdBaseinfoSubDat.setProfitType("03");
                        pbcPrdBaseinfoSubDat.setBusiModel("3");
                        pbcPrdBaseinfoSubDat.setOrgCode(orgCode);
                        pbcPrdBaseinfoSubDat.setCreateUser(SecurityUtil.getLoginUser().getUserName());
                        pbcPrdBaseinfoSubDat.setCreateTime(new Date());
                        pbcPrdBaseinfoSubDat.setUpdateUser(SecurityUtil.getLoginUser().getUserName());
                        pbcPrdBaseinfoSubDat.setUpdateTime(new Date());
                        pbcPrdBaseinfoSubDatMapper.insert(pbcPrdBaseinfoSubDat);
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
                        /*String datZipPath = CacheUtil.getSysParam("SVR_PBC_DAT_FILEPATH");
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


    private PbcPrdBaseinfoSubDat updatePbcPrdBaseinfoSubDat(PbcPrdBaseinfoSubDatDto pbcPrdBaseinfoSubDatDto,PbcPrdBaseinfoSubDat pbcPrdBaseinfoSubDat) throws InvocationTargetException, IllegalAccessException {
        BeanUtil.copyProperties(pbcPrdBaseinfoSubDat,pbcPrdBaseinfoSubDatDto);
        pbcPrdBaseinfoSubDat.setRaiseVdate(pbcPrdBaseinfoSubDatDto.getRaiseVdate01());
        pbcPrdBaseinfoSubDat.setRaiseEnddate(pbcPrdBaseinfoSubDatDto.getRaiseEnddate01());
        pbcPrdBaseinfoSubDat.setInDepCode(pbcPrdBaseinfoSubDatDto.getInDepCode());
        pbcPrdBaseinfoSubDat.setOutDepName(pbcPrdBaseinfoSubDatDto.getInDepName());
        pbcPrdBaseinfoSubDat.setOutDepCtry(pbcPrdBaseinfoSubDatDto.getOutDepCtry());
        pbcPrdBaseinfoSubDat.setIsMarketFund(pbcPrdBaseinfoSubDatDto.getTransferType1());
        pbcPrdBaseinfoSubDat.setPbcProdType(pbcPrdBaseinfoSubDatDto.getProdType());
        pbcPrdBaseinfoSubDat.setRegistSeqno(cn.hutool.core.lang.UUID.randomUUID().toString());
        return pbcPrdBaseinfoSubDat;
    }

    private List<ExcelValidDto> convertToEntity(List<List<String>> dataList,List<PbcPrdBaseinfoSubDatDto> pbcPrdBaseinfoSubDatDtos){
        List<ExcelValidDto> checkResult = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(dataList)){
            for (int i = 0; i < dataList.size(); i++ ) {
                int rowNumber = i + 1;
                // 验证消息
                List<ExcelValidDto> errorMessage = new ArrayList<>();
                List<String> rowsData = dataList.get(i);
                PbcPrdBaseinfoSubDatDto dto = new PbcPrdBaseinfoSubDatDto();
                int row = 0;
                //序号
                String number = rowsData.get(row++);
                dto.setNumber(StrUtil.isEmpty(number) ? null : number);
                //产品代码
                String pbcProdId = rowsData.get(row++);
                dto.setPbcProdId(StrUtil.isEmpty(pbcProdId) ? null : pbcProdId);
                //发行机构代码
                String issuePartyId = rowsData.get(row++);
                if(checkIsNotEmpty(issuePartyId,rowNumber,"发行机构代码",errorMessage)){
                    String pbcBankCode = CommonUtil.selectPbcBankCode(null);
                    if(StrUtil.isNotEmpty(pbcBankCode) && pbcBankCode.equals(issuePartyId)){
                        dto.setIssuePartyId(issuePartyId);
                    }else{
                        errorMessage.add(new ExcelValidDto("发行机构代码",String.valueOf(rowNumber),"第"+rowNumber+"行:发行机构代码与系统内机构代码不一致！"));
                    }
                }
                //产品名称
                String prodName = rowsData.get(row++);
                if(checkIsNotEmpty(prodName,rowNumber,"产品名称",errorMessage)){
                    dto.setProdName(prodName);
                }
                //产品品种
                String prodType = rowsData.get(row++);
                if(checkIsNotEmpty(prodType,rowNumber,"产品品种",errorMessage)){
                    dto.setProdType(PbcDictCodeEnums.getCodeByName(prodType));
                }
                //产品类型
                String ivstorientation = rowsData.get(row++);
                if(checkIsNotEmpty(ivstorientation,rowNumber,"产品类型",errorMessage)){
                    dto.setIvstorientation(PbcDictCodeEnums.getCodeByName(ivstorientation));
                }
                //产品品牌
                String prodBrand = rowsData.get(row++);
                dto.setProdBrand(StrUtil.isEmpty(prodBrand) ? null : prodBrand);
                //产品期次
                String prodStage = rowsData.get(row++);
                if(StrUtil.isNotEmpty(prodStage)){
                    if(checkFigure(prodStage)){
                        dto.setProdStage(new BigDecimal(prodStage));
                    }else{
                        errorMessage.add(new ExcelValidDto("产品期次",String.valueOf(rowNumber),"第"+rowNumber+"行:产品期次非数字！"));
                    }
                }
                //发行机构内部产品代码
                String prodId = rowsData.get(row++);
                if(checkIsNotEmpty(prodId,rowNumber,"发行机构内部产品代码",errorMessage)){
                    dto.setProdId(prodId);
                }
                //募集方式
                String raiseType = rowsData.get(row++);
                if(checkIsNotEmpty(raiseType,rowNumber,"募集方式",errorMessage)){
                    dto.setRaiseType(PbcDictCodeEnums.getCodeByName(raiseType));
                }
                //管理方式
                String manageType = rowsData.get(row++);
                if(checkIsNotEmpty(manageType,rowNumber,"管理方式",errorMessage)){
                    dto.setManageType(PbcDictCodeEnums.getCodeByName(manageType));
                }
                //运行方式
                String operateMode = rowsData.get(row++);
                if(checkIsNotEmpty(operateMode,rowNumber,"运行方式",errorMessage)){
                    dto.setOperateMode(PbcDictCodeEnums.getCodeByName(operateMode));
                }
                //募集起始日期
                String raiseVdate01 = rowsData.get(row++);
                if(checkIsNotEmpty(raiseVdate01,rowNumber,"募集起始日期",errorMessage)){
                    if(checkDate(raiseVdate01)){
                        dto.setRaiseVdate01(DateUtil.parse(raiseVdate01));
                    }else{
                        errorMessage.add(new ExcelValidDto("募集起始日期",String.valueOf(rowNumber),"第"+rowNumber+"行:募集起始日期格式不正确！"));
                    }
                }

                //募集结束日期
                String raiseEnddate01 = rowsData.get(row++);
                if(checkIsNotEmpty(raiseEnddate01,rowNumber,"募集结束日期",errorMessage)){
                    if(checkDate(raiseEnddate01)){
                        dto.setRaiseEnddate01(DateUtil.parse(raiseEnddate01));
                    }else{
                        errorMessage.add(new ExcelValidDto("募集结束日期",String.valueOf(rowNumber),"第"+rowNumber+"行:募集结束日期格式不正确！"));
                    }
                }
                //发行机构提前终止权标识
                String termRightFlag = rowsData.get(row++);
                if(checkIsNotEmpty(termRightFlag,rowNumber,"发行机构提前终止权标识",errorMessage)){
                    dto.setTermRightFlag(PbcDictCodeEnums.getCodeByName(termRightFlag));
                }

                //客户赎回权标识
                String cusRedeemFlag = rowsData.get(row++);
                if(checkIsNotEmpty(cusRedeemFlag,rowNumber,"客户赎回权标识",errorMessage)){
                    dto.setCusRedeemFlag(PbcDictCodeEnums.getCodeByName(cusRedeemFlag));
                }
                //产品增信标识
                String creditFlag = rowsData.get(row++);
                if(checkIsNotEmpty(creditFlag,rowNumber,"产品增信标识",errorMessage)){
                    dto.setCreditFlag(PbcDictCodeEnums.getCodeByName(creditFlag));
                }
                //境内托管机构代码
                String inDepCode = rowsData.get(row++);
                dto.setInDepCode(StrUtil.isEmpty(inDepCode) ? null : inDepCode);
                //境外托管机构国别
                String outDepCtry = rowsData.get(row++);
                dto.setOutDepCtry(StrUtil.isEmpty(outDepCtry) ? null : outDepCtry);
                //境外托管机构名称
                String inDepName = rowsData.get(row++);
                dto.setInDepName(StrUtil.isEmpty(inDepName) ? null : inDepName);
                //产品起始日期
                String vdate = rowsData.get(row++);
                if(checkIsNotEmpty(vdate,rowNumber,"产品起始日期",errorMessage)){
                    if(checkDate(vdate)){
                        dto.setVdate(DateUtil.parse(vdate));
                    }else{
                        errorMessage.add(new ExcelValidDto("产品起始日期",String.valueOf(rowNumber),"第"+rowNumber+"行:产品起始日期格式不正确！"));
                    }
                }
                //产品预计终止日期
                String mdate = rowsData.get(row++);
                if(StrUtil.isNotEmpty(mdate)){
                    if(checkDate(mdate)){
                        dto.setMdate(DateUtil.parse(mdate));
                    }else{
                        errorMessage.add(new ExcelValidDto("产品预计终止日期",String.valueOf(rowNumber),"第"+rowNumber+"行:产品预计终止日期格式不正确！"));
                    }
                }
                //受托职责
                String fiduciaryDuty = rowsData.get(row++);
                if(checkIsNotEmpty(fiduciaryDuty,rowNumber,"受托职责",errorMessage)){
                    dto.setFiduciaryDuty(PbcDictCodeEnums.getCodeByName(fiduciaryDuty));
                }
                //分级产品标识
                String isConstracte = rowsData.get(row++);
                if(checkIsNotEmpty(isConstracte,rowNumber,"分级产品标识",errorMessage)){
                    dto.setIsConstracte(PbcDictCodeEnums.getCodeByName(isConstracte));
                }
                //收益权转让产品标识
                String transferType = rowsData.get(row++);
                if(checkIsNotEmpty(transferType,rowNumber,"收益权转让产品标识",errorMessage)){
                    dto.setTransferType(PbcDictCodeEnums.getCodeByName(transferType));
                }
                //货币市场基金标识
                String transferType1 = rowsData.get(row++);
                if(checkIsNotEmpty(transferType1,rowNumber,"货币市场基金标识",errorMessage)){
                    dto.setTransferType1(PbcDictCodeEnums.getCodeByName(transferType1));
                }
                //跨境理财通标识
                String crossBorderFlag = rowsData.get(row++);
                if(checkIsNotEmpty(crossBorderFlag,rowNumber,"跨境理财通标识",errorMessage)){
                    dto.setCrossBorderFlag(PbcDictCodeEnums.getCodeByName(crossBorderFlag));
                }

                //以财产权信托名义开展的资金信托标识
                String zjxtFlag = rowsData.get(row++);
                if(checkIsNotEmpty(zjxtFlag,rowNumber,"以财产权信托名义开展的资金信托标识",errorMessage)){
                    dto.setZjxtFlag(PbcDictCodeEnums.getCodeByName(zjxtFlag));
                }
                //其他产品标识3
                String transferType4 = rowsData.get(row++);
//                dto.setTransferType1(StrUtil.isEmpty(transferType4) ? null : transferType4);
                //募集资金币种
                String raiseCcy = rowsData.get(row++);
                if(checkIsNotEmpty(raiseCcy,rowNumber,"募集资金币种",errorMessage)){
                    dto.setRaiseCcy(dealCcyInfo(raiseCcy));
                }
                //兑付本金币种
                String cashPrinCcy = rowsData.get(row++);
                if(checkIsNotEmpty(cashPrinCcy,rowNumber,"兑付本金币种",errorMessage)){
                    dto.setCashPrinCcy(dealCcyInfo(cashPrinCcy));
                }
                //兑付收益币种
                String cashProfCcy = rowsData.get(row++);
                if(checkIsNotEmpty(cashProfCcy,rowNumber,"兑付收益币种",errorMessage)){
                    dto.setCashProfCcy(dealCcyInfo(cashProfCcy));
                }
                //增信机构类型
                String creditType = rowsData.get(row++);
                if("1-是".equals(creditFlag)){
                    if(checkIsNotEmpty(creditType,rowNumber,"增信机构类型",errorMessage)){
                        StringBuilder builder = new StringBuilder();
                        String[] split = creditType.split(",");
                        if(split.length > 0){
                            for (int j = 0;j<split.length;j++) {
                                if(j == split.length - 1){
                                    builder.append(PbcDictCodeEnums.getCodeByName(split[j]));
                                }else{
                                    builder.append(PbcDictCodeEnums.getCodeByName(split[j])).append(",");
                                }
                            }
                        }else{
                            builder.append(PbcDictCodeEnums.getCodeByName(creditType));
                        }
                        dto.setCreditType(builder.toString());
                    }
                }
                //增信形式
                String creditMode = rowsData.get(row++);
                if("1-是".equals(creditFlag)){
                    if(checkIsNotEmpty(creditMode,rowNumber,"增信形式",errorMessage)){
                        dto.setCreditMode(PbcDictCodeEnums.getCodeByName(creditMode));
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
