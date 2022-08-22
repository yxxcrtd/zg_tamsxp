package com.joyintech.tams.svreport.chb.liquidityriskinfo.service;

import com.alibaba.fastjson.JSONArray;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.common.svreport.datacheck.dto.CheckRule;
import com.joyintech.tams.common.svreport.datacheck.service.DataCheckService;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dao.LiquidityRiskInfo001Mapper;
import com.joyintech.tams.svreport.chb.liquidityriskinfo.dto.LiquidityRiskInfo001Dto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 流动性风险应对情况信息维护Service
 *
 * @author zhoubin
 * @create 2022/08/15
 */
@Service
public class LiquidityRiskInfo001Service extends CommonExportService<LiquidityRiskInfo001Dto> {

    @Resource
    private LiquidityRiskInfo001Mapper liquidityRiskInfo001Mapper;

    @Resource
    private CommonCodeCheckBatchService commonCodeCheckBatchService;
    @Resource
    private DataCheckService dataCheckService;

    /**
     * 查询列表
     *
     * @param param
     * @return
     */
    public Page<LiquidityRiskInfo001Dto> searchForPage(Map<String, Object> param) {
        //产品代码
        String prodIdList = (String) param.get("prodIdList");
        //产品登记编码
        String regCodeList = (String) param.get("regCodeList");
        //流动性风险应对措施
        String solutionsList = (String) param.get("solutionsList");
        //数据状态
        String statusList = (String) param.get("statusList");

        param.put("prodIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodIdList));
        param.put("regCodeList", StringUtil.splitToList(GlobalConstant.COMMA, regCodeList));
        param.put("solutionsList", StringUtil.splitToList(GlobalConstant.COMMA, solutionsList));
        param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, statusList));
        int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
        Page<LiquidityRiskInfo001Dto> page = super.queryForPage(pagenum, legth,
                () -> liquidityRiskInfo001Mapper.queryForPage(param));
        return page;
    }


    /**
     * 导出功能需要导出查询条件的所有内容
     *
     * @param param 画面查询参数
     * @return 查询结果列表
     */
    public List<LiquidityRiskInfo001Dto> querySearchPageList(Map<String, Object> param) {
        //产品代码
        List<String> keyList = null;
        //产品代码
        String prodIdList = (String) param.get("prodIdList");
        //产品登记编码
        String regCodeList = (String) param.get("regCodeList");
        //流动性风险应对措施
        String solutionsList = (String) param.get("solutionsList");
        //数据状态
        String statusList = (String) param.get("statusList");

        if (StringUtils.isNotEmpty((String) param.get("selectData"))) {
            List<Map> selectData = JSONArray.parseArray((String) param.get("selectData"), Map.class);
            keyList = selectData.stream().map(e -> (String) e.get("seqno")).collect(Collectors.toList());
        }

        if (CollectionUtils.isEmpty(keyList)) {
            param.put("prodIdList", StringUtil.splitToList(GlobalConstant.COMMA, prodIdList));
            param.put("regCodeList", StringUtil.splitToList(GlobalConstant.COMMA, regCodeList));
            param.put("solutionsList", StringUtil.splitToList(GlobalConstant.COMMA, solutionsList));
            param.put("statusList", StringUtil.splitToList(GlobalConstant.COMMA, statusList));
        } else {
            param.put("keyList", keyList);
        }
        return liquidityRiskInfo001Mapper.querySearchPageList(param);
    }


    /**
     * 新增修改
     *
     * @param dto
     */
    public void insert(LiquidityRiskInfo001Dto dto) {
        String seqno = dto.getSeqno();
        dto.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
        dto.setDataSource("DB");
        dto.setDescribed(StringUtil.isBlank(dto.getDescribed()) ? " " : dto.getDescribed());
        dto.setDisposeResult(StringUtil.isBlank(dto.getDisposeResult()) ? " " : dto.getDisposeResult());
        dto.setCreateUser(LoginUserUtil.getUserName());
        dto.setUpdateUser(LoginUserUtil.getUserName());
        if (StringUtil.isBlank(seqno)) {
            dto.setSeqno(IDGenerator.generate(36));
            liquidityRiskInfo001Mapper.insert(dto);
            return;
        }
        liquidityRiskInfo001Mapper.updateByPrimaryKey(dto);

    }

    /**
     * 删除
     *
     * @param keys
     */
    public void delete(String keys) {
        List<String> listKey = StringUtil.splitToList(GlobalConstant.COMMA, keys);
        listKey.forEach(seqno -> {
            liquidityRiskInfo001Mapper.updateStatusByPrimaryKey(seqno);
        });


    }

    /**
     * 主键查询
     *
     * @param seqno
     */
    public LiquidityRiskInfo001Dto selectByPrimaryKey(String seqno) {

        return liquidityRiskInfo001Mapper.selectByPrimaryKey(seqno);

    }


    /**
     * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
     *
     * @param fileName
     * @return
     */
    public AjaxResult checkConfirmMsgForImportData(String fileName) {
        AjaxResult webResult = new AjaxResult();
        if (fileName.indexOf("流动性风险应对情况信息") == -1) {
            webResult.setMessage("文件名称错误，请下载模板后上传", MessageType.错误消息);
            return webResult;
        }
        List<List<String>> dataList=ExcelUtil.getDataList(fileName);

        if(CollectionUtils.isEmpty(dataList)){
            webResult.setMessage("请勿上传空文件", MessageType.错误消息);
            return webResult;
        }
        List<Map<String, String>> listToMap = this.listToMap(dataList);

        List<ExcelValidDto> result = new ArrayList<>();

        List<ValidMessage> errorList = new ArrayList<>();

        // 将序号放入list中
        List<String> excelNumList = new ArrayList<>();

        //查询是否有重复的数据
        List<String> countList = liquidityRiskInfo001Mapper.selectLiquidityRiskInfoCount(listToMap);

        for (int i = 0; i < listToMap.size(); i++) {
            Map<String, String> mapI = listToMap.get(i);
            String prodId = mapI.get("PROD_ID");
            String solutions = mapI.get("SOLUTIONS");
            String occurDate = mapI.get("OCCUR_DATE");
            String replyDate = mapI.get("REPLY_DATE");
            String described = mapI.get("DESCRIBED");
            String disposeResult = mapI.get("DISPOSE_RESULT");
            String keyI = prodId + solutions + occurDate + replyDate + described + disposeResult;
            boolean occur_date = DateUtil.isDateStr(occurDate);
            if (!occur_date) {
                result.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i + 2), "风险发生日期必须为日期格式(YYYY-MM-DD)[OCCUR_DATE=" + occurDate + "]"));
            }
            boolean reply_date = DateUtil.isDateStr(replyDate);
            if (!reply_date) {
                result.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i + 2), "风险应对必须为日期格式YYYY-MM-DD)[REPLY_DATE=" + replyDate + "]"));
            }
            for (int j = i + 1; j < listToMap.size(); j++) {
                Map<String, String> mapJ = listToMap.get(j);
                String keyJ = mapJ.get("PROD_ID") + mapJ.get("SOLUTIONS") + mapJ.get("OCCUR_DATE") + mapJ.get("REPLY_DATE")
                        + mapJ.get("DESCRIBED") + mapJ.get("DISPOSE_RESULT");
                if (StringUtil.equals(keyI, keyJ)) {
                    result.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(j + 2), this.getMessage("REPORT_COMMON_FILE_E_0027", String.valueOf(i + 2) + "," + String.valueOf(j + 2))));
                }
            }

            if (countList.contains(keyI)) {
                excelNumList.add(String.valueOf(i + 2));
            }

        }

        for (ExcelValidDto excelValidDto : result) {
            errorList.add(new ValidMessage(excelValidDto.getErrorInfo()));
            webResult.setErrorList(errorList);
            return webResult;
        }

        if (CollectionUtils.isNotEmpty(excelNumList)) {
            webResult.setMessage(this.getMessage("CHB_ISCOVER_EQU",StringUtil.joinString(excelNumList, GlobalConstant.COMMA)),MessageType.确认消息);
        } else {
            webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
        }
        return webResult;
    }


    /**
     * 导入文件
     *
     * @param dataList
     * @param tempList
     * @param orgCode
     * @param deptCode
     * @return
     */
    public List<ExcelValidDto> importData(List<List<String>> dataList, List<List<String>> tempList, String orgCode,
                                          String deptCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<>(7);
            headList.add("产品代码");
            headList.add("流动性风险应对措施");
            headList.add("风险发生日期");
            headList.add("风险应对日期");
            headList.add("风险事件描述");
            headList.add("风险处理结果");
            headList.add("备注");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<Map<String, String>> listToMap = this.listToMap(dataList);

                //脚本校验
                List<CheckRule> listRules = dataCheckService.queryCheckRules(ChbCheckTableInfo.genDataType("LIQUIDITY_RISK_INFO"));
                DataCheckService.checkDataList(listToMap, listRules, false);
                // 代码校验
                commonCodeCheckBatchService.codeCheckCommon(listToMap, ChbCheckTableInfo.流动性风险应对情况信息表INFO, null);
                List<LiquidityRiskInfo001Dto> list = new LinkedList<>();
                listToMap.forEach(map -> {
                    LiquidityRiskInfo001Dto dto = new LiquidityRiskInfo001Dto();
                    dto.setSeqno(IDGenerator.generate(36));
                    dto.setOrgCode(orgCode);
                    dto.setDataSource("DB");
                    dto.setProdId(map.get("PROD_ID"));
                    dto.setRegCode(StringUtil.isBlank(map.get("REG_CODE")) ? " " : map.get("REG_CODE"));
                    dto.setSolutions(map.get("SOLUTIONS"));
                    dto.setDescribed(map.get("DESCRIBED"));
                    dto.setDisposeResult(map.get("DISPOSE_RESULT"));
                    dto.setOccurDate(DateUtil.parse(map.get("OCCUR_DATE")));
                    dto.setReplyDate(DateUtil.parse(map.get("REPLY_DATE")));
                    dto.setRemark(map.get("REMARK"));
                    dto.setCreateUser(LoginUserUtil.getUserName());
                    dto.setCreateTime(new Date());
                    dto.setUpdateUser(LoginUserUtil.getUserName());
                    dto.setUpdateTime(new Date());
                    if (StringUtil.isBlank(map.get("ERRMSG"))) {
                        dto.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
                    } else {
                        dto.setStatus(DictCodeListExt1.维护数据状态_待处理.getValue());
                        dto.setErrMsg(map.get("ERRMSG"));
                    }
                    list.add(dto);
                });
                liquidityRiskInfo001Mapper.mergeInfoList(list);
                return Collections.EMPTY_LIST;
            }
        }
        return null;
    }


    public List<Map<String, String>> listToMap(List<List<String>> dataList) {
        String[] fieldName = {"PROD_ID", "SOLUTIONS", "OCCUR_DATE", "REPLY_DATE", "DESCRIBED", "DISPOSE_RESULT", "REMARK"};
        List<Map<String, String>> result = new ArrayList<>(7);
        dataList.forEach(data -> {
            Map<String, String> temp = new HashMap<>();
            for (int i = 0; i < 7; i++) {
                if (StringUtil.equals("SOLUTIONS", fieldName[i])) {
                    String solutions = data.get(i);
                    if (StringUtil.isNotBlank(solutions)) {
                        solutions = solutions.split(" ")[0];
                    }
                    temp.put(fieldName[i], solutions);
                } else if (StringUtil.equals("DESCRIBED", fieldName[i]) || StringUtil.equals("DISPOSE_RESULT", fieldName[i])) {
                    String value = data.get(i);
                    if (StringUtil.isBlank(value)) {
                        value =" ";
                    }
                    temp.put(fieldName[i], value);
                } else {
                    temp.put(fieldName[i], data.get(i));
                }

            }
            result.add(temp);
        });
        return result;
    }

}
