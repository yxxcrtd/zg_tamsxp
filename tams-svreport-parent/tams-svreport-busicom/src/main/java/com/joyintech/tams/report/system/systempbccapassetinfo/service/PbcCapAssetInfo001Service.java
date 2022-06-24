package com.joyintech.tams.report.system.systempbccapassetinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.system.systempbccapassetinfo.dao.PbcCapAssetInfo001DtoMapper;
import com.joyintech.tams.report.system.systempbccapassetinfo.dto.PbcCapAssetInfo001Dto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import com.joyintech.tools.collectors.JoyinCollectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

/**
 * 人行资金信托代码管理Service
 *
 * @author kx
 *
 * @create 2019/12/24
 */
@Service
public class PbcCapAssetInfo001Service extends CommonExportService<PbcCapAssetInfo001Dto> {

    @Autowired
    private PbcCapAssetInfo001DtoMapper pbcCapAssetInfo001DtoMapper;

    /**
     * 查询列表
     *
     * @param param
     * @return
     */
    public Page<PbcCapAssetInfo001Dto> searchForPage(Map<String, Object> param) {
        String insideId = (String) param.get("assetCode");
        param.put("assetCodeList", StringUtil.splitToList(GlobalConstant.COMMA, insideId));
        String pobcassetid = (String) param.get("pobcassetid");
        param.put("pobcassetidList", StringUtil.splitToList(GlobalConstant.COMMA, pobcassetid));
        int pagenum = Integer.parseInt(param.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(param.get(GlobalConstant.PARAM_LENGTH).toString());
        Page<PbcCapAssetInfo001Dto> page = super.queryForPage(pagenum, legth,
                () -> pbcCapAssetInfo001DtoMapper.queryForPage(param));
        return page;
    }

    /**
     * 资产代码下拉框取得值
     *
     * @param param
     * @return
     */
    public List<SelectOptionDto> getAssetCodeList(@RequestBody Map<String, Object> param) {
        return pbcCapAssetInfo001DtoMapper.getAssetCodeList(param);
    }

    /**
     * 人行资金信托代码下拉框取得值
     *
     * @param param
     * @return
     */
    public List<SelectOptionDto> setPobCasSeTidList(@RequestBody Map<String, Object> param) {
        return pbcCapAssetInfo001DtoMapper.setPobCasSeTidList(param);
    }

    /**
     * 删除人行资金信托代码
     *
     * @param map
     */
    public void deteteRegCode(Map<String, Object> map) {
        String key = (String) map.get("keyList");
        List<String> listKey = StringUtil.splitToList(GlobalConstant.COMMA, key);
        PbcCapAssetInfo001Dto pbcCapAssetInfo001Dto = null;
        for (String string : listKey) {
            pbcCapAssetInfo001Dto = new PbcCapAssetInfo001Dto();
            pbcCapAssetInfo001Dto.setAssetUuid(string);
            pbcCapAssetInfo001Dto.setStatus(DictCodeList.数据状态_删除.getValue());
            pbcCapAssetInfo001Dto.setUpdateTime(new Date());
            String updateUser = LoginUserUtil.getUserName();
            pbcCapAssetInfo001Dto.setUpdateUser(StringUtils.isBlank(pbcCapAssetInfo001Dto.getUpdateUser()) ? "system" : updateUser);
            pbcCapAssetInfo001DtoMapper.updateByPrimaryKeySelective(pbcCapAssetInfo001Dto);
        }
    }

    /**
     * 导出功能需要导出查询条件的所有内容
     *
     * @param param 画面查询参数
     * @return 查询结果列表
     */
    public List<PbcCapAssetInfo001Dto> querySearchPageList(Map<String, Object> param) {
        String insideId = (String) param.get("assetCode");
        param.put("assetCodeList", StringUtil.splitToList(GlobalConstant.COMMA, insideId));
        String pobcassetid = (String) param.get("pobcassetid");
        param.put("pobcassetidList", StringUtil.splitToList(GlobalConstant.COMMA, pobcassetid));
        param.put("keyList",
                StringUtil.splitToList(",", (String) param.get("keyList")));
        return pbcCapAssetInfo001DtoMapper.querySearchPageList(param);
    }


    /**
     * 导入文件之前检查文件中的数据在数据库中是否已经存在 如果已经存在，需要提示用户进行确认是否覆盖数据库
     *
     * @param dataList
     * @param orgCode
     * @param deptCode
     * @return
     */
    public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList, String orgCode, String deptCode) {
        AjaxResult webResult = new AjaxResult();
        String statusEffective = DictCodeListExt1.维护数据状态_删除.getValue();
        // 第一种情况判断Excel文件中资产代码在[人行资金信托代码信息表]中是否存在STATUS！=D
        if (!StringUtil.isEmptyList(dataList)) {
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<String> data = new ArrayList<String>();
                List<PbcCapAssetInfo001Dto> entities = convertToBean(dataList);
                // DB判断
                if (entities.size() > 100) {
                    List<PbcCapAssetInfo001Dto> newDataList = new ArrayList<PbcCapAssetInfo001Dto>();
                    newDataList.addAll(entities);
                    int part = entities.size() / 1000; // 分批数
                    for (int i = 0; i < part; i++) {
                        data.addAll(pbcCapAssetInfo001DtoMapper
                                .getPobCassetId(newDataList.subList(0, 1000), statusEffective));
                        newDataList.subList(0, 1000).clear();
                    }
                    // 最后剩下的数据
                    if (CollectionUtils.isNotEmpty(newDataList)) {
                        data.addAll(pbcCapAssetInfo001DtoMapper.getPobCassetId(newDataList,
                                statusEffective));
                    }
                } else {
                    data = pbcCapAssetInfo001DtoMapper.getPobCassetId(entities, statusEffective);
                }
                if (data != null && data.size() > 0) {
                    Map<String, List<PbcCapAssetInfo001Dto>> equityInfo001Map = entities.stream()
                            .collect(JoyinCollectors.groupingBy(x -> x.getAssetid()));
                    // 将序号放入list中
                    List<String> excelNumList = new ArrayList<String>();
                    List<Integer> excelNumIntList = new ArrayList<Integer>();
                    for (String string : data) {
                        List<PbcCapAssetInfo001Dto> listTemp = new ArrayList<>();
                        listTemp = equityInfo001Map.get(string);
                        excelNumIntList.add(Integer.valueOf(listTemp.get(0).getExcelNum()));
                    }
                    // 将list进行排序，并转化为string
                    Collections.sort(excelNumIntList);
                    for (Integer rowNo : excelNumIntList) {
                        excelNumList.add(String.valueOf(rowNo));
                    }
                    webResult.setMessage("资产代码对应的人行资金信托代码已存在,是否覆盖？", MessageType.确认消息);
                } else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }
            } else {
                // 防止在进行导入前check时进行了两次的弹框
                webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
            }
        }
        return webResult;
    }

    /**
     * 导入数据文件之前检查资金信托代码有没有对应多个资产代码(Excel)
     *
     * @param dataList
     * @param orgCode
     * @param deptCode
     * @return
     */
    public AjaxResult checkConfirmMsgForImportDataByExcel(List<List<String>> dataList, String orgCode, String deptCode) {
        AjaxResult webResult = new AjaxResult();
        List<ValidMessage> list = new ArrayList<>();
        StringBuilder message = new StringBuilder();
        String pobcassetidList = null;
        // 判断Excel文件中同一个人行资金信托代码（人行资金信托代码信息表STATUS！=D）是否对应了多个资产代码
        if (!StringUtil.isEmptyList(dataList)) {
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<PbcCapAssetInfo001Dto> entities = convertToBean(dataList);
                // Excel判断
                HashMap<String, String> hashMap = new HashMap<String, String>();
                for (PbcCapAssetInfo001Dto pbcCapAssetInfo001Dto : entities) {
                    if (StringUtils.isNotBlank(pbcCapAssetInfo001Dto.getPobcassetid())) {
                        // 查询最新人行资金信托代码拼接资产代码的字符串
                        String value = hashMap.get(pbcCapAssetInfo001Dto.getPobcassetid());
                        if (StringUtils.isBlank(value)) {
                            // 如果value是空的 说明是第一次取到
                            hashMap.put(pbcCapAssetInfo001Dto.getPobcassetid(), pbcCapAssetInfo001Dto.getAssetid());
                        } else {
                            if (StringUtils.isBlank(pobcassetidList)) {
                                pobcassetidList = pbcCapAssetInfo001Dto.getPobcassetid()+",";
                            }
                            if (pobcassetidList != null && !pobcassetidList.contains(pbcCapAssetInfo001Dto.getPobcassetid())) {
                                pobcassetidList = pobcassetidList + pbcCapAssetInfo001Dto.getPobcassetid()+",";
                            }
                        }
                    }
                }
                if(StringUtils.isNotBlank(pobcassetidList)) {
                    list.add(new ValidMessage(message.append("人行资金信托代码").append(pobcassetidList).append("在Excel中对应了多个资产代码").toString()));
                    webResult.setErrorList(list);
                }else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }

            }else {
                // 防止在进行导入前check时进行了两次的弹框
                webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
            }
        }
        return webResult;
    }

    /**
     * 导入数据文件之前检查资金信托代码有没有对应多个资产代码(数据库)
     *
     * @param dataList
     * @param orgCode
     * @param deptCode
     * @return
     */
    public AjaxResult checkConfirmMsgForImportDataByDB(List<List<String>> dataList, String orgCode, String deptCode) {
        AjaxResult webResult = new AjaxResult();
        String statusEffective = DictCodeListExt1.维护数据状态_删除.getValue();
        String pobcassetidList = null;
        StringBuilder message = new StringBuilder();
        // 判断DB中同一个人行资金信托代码（人行资金信托代码信息表STATUS！=D）是否对应了多个资产代码
        if (!StringUtil.isEmptyList(dataList)) {
            if (CollectionUtils.isNotEmpty(dataList)) {
                List<PbcCapAssetInfo001Dto> data2 = new ArrayList<PbcCapAssetInfo001Dto>();
                List<PbcCapAssetInfo001Dto> entities = convertToBean(dataList);
                // DB判断
                if (entities.size() > 100) {
                    List<PbcCapAssetInfo001Dto> newDataList2 = new ArrayList<PbcCapAssetInfo001Dto>();
                    newDataList2.addAll(entities);
                    int part = entities.size() / 1000; // 分批数
                    for (int i = 0; i < part; i++) {
                        data2.addAll(pbcCapAssetInfo001DtoMapper
                                .selectPobcassetIdAndAssetId(newDataList2.subList(0, 1000), statusEffective));
                        newDataList2.subList(0, 1000).clear();
                    }
                    // 最后剩下的数据
                    if (CollectionUtils.isNotEmpty(newDataList2)) {
                        data2.addAll(pbcCapAssetInfo001DtoMapper.selectPobcassetIdAndAssetId(newDataList2,
                                statusEffective));
                    }
                } else {
                    data2 = pbcCapAssetInfo001DtoMapper.selectPobcassetIdAndAssetId(entities, statusEffective);
                }
                if (data2 != null && data2.size() > 1) {
                    // 将序号放入list中
                    for (PbcCapAssetInfo001Dto pbcCapAssetInfo001Dto : data2) {
                        if (StringUtils.isBlank(pobcassetidList)) {
                            pobcassetidList = pbcCapAssetInfo001Dto.getPobcassetid() + ",";
                        }
                        if (pobcassetidList != null && !pobcassetidList.contains(pbcCapAssetInfo001Dto.getPobcassetid())) {
                            pobcassetidList = pobcassetidList + pbcCapAssetInfo001Dto.getPobcassetid() + ",";
                        }
                    }
                    webResult.setMessage((message.append("人行资金信托代码").append(pobcassetidList).append("在数据库中对应了多个资产代码,是否继续？")).toString(), MessageType.确认消息);
                } else {
                    // 防止在进行导入前check时进行了两次的弹框
                    webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
                }
            } else {
                // 防止在进行导入前check时进行了两次的弹框
                webResult.setData(ReportGlobalConstant.NO_INFO_SHOWOUT);
            }
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
            List<String> headList = new ArrayList<String>();
            headList.add("行内资产/负债编码*");
            headList.add("资产名称");
            headList.add("资金信托代码*");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<PbcCapAssetInfo001Dto> entities = new ArrayList<>(dataList.size());
                Map<String, String> box = new HashMap<String, String>();
                List<ExcelValidDto> checkResult = convertToEntity(dataList, entities, box, orgCode);
                if (checkResult.isEmpty()) {
                    entities.forEach(item -> {
                        PbcCapAssetInfo001Dto record = new PbcCapAssetInfo001Dto();
                        BeanUtil.copyPropertiesIgnoreNull(record, item);
                        record.setAssetUuid(IDGenerator.generate(36));
                        record.setOrgCode(orgCode);
                        record.setCreateUser(LoginUserUtil.getUserName());
                        record.setCreateDept(null);
                        record.setCreateTime(new Date());
                        record.setUpdateUser(LoginUserUtil.getUserName());
                        record.setUpdateTime(new Date());
                        record.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
                        // 产品要素维护管理中产品代码不存在 存在则更新数据
                        PbcCapAssetInfo001Dto dto = pbcCapAssetInfo001DtoMapper.selectByAssetId(record);
                        if (dto != null) {
                            record.setAssetUuid(dto.getAssetUuid());
                            pbcCapAssetInfo001DtoMapper.updateByPrimaryKeySelective(record);
                        } else {
                            // 不存在插入新数据
                            pbcCapAssetInfo001DtoMapper.insertSelective(record);
                        }
                    });
                }
                return checkResult;
            }
        }
        return null;
    }

    /**
     * excel数据转为java对象
     * <p>
     * 将excel导入的数据，按行转为ProRegistCodeManage001Dto对象，同时验证各字段值
     * </p>
     *
     * @param dataList excel导入的数据
     * @return 返回list
     */
    private List<PbcCapAssetInfo001Dto> convertToBean(List<List<String>> dataList) {
        List<PbcCapAssetInfo001Dto> entities = new ArrayList<PbcCapAssetInfo001Dto>();
        for (int i = 0; i < dataList.size(); i++) {
            List<String> list = dataList.get(i);
            PbcCapAssetInfo001Dto entity = new PbcCapAssetInfo001Dto();
            entity.setAssetid(list.get(0));
            entity.setAssetname(list.get(1));
            entity.setPobcassetid(list.get(2));
            entity.setExcelNum(String.valueOf(i + 2));
            entities.add(entity);
        }
        return entities;
    }

    /**
     * excel数据转为java对象
     * <p>
     * 将excel导入的数据，按行转为ProRegistCodeManage001Dto对象，同时验证各字段值
     * </p>
     *
     * @param dataList excel导入的数据
     * @param entities 转化后的列表
     * @param box
     * @return 验证信息
     */
    private List<ExcelValidDto> convertToEntity(List<List<String>> dataList,
                                                List<PbcCapAssetInfo001Dto> entities, Map<String, String> box, String orgCode) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        HashMap<String, String> poCassetIdMap = new HashMap<String, String>();
        String pobcassetidList = null;
        for (int i = 0; i < dataList.size(); i++) {
            List<String> list = dataList.get(i);
            if (null == list || list.size() != 3) {
                checkResult.add(new ExcelValidDto(StringUtil.EMPTY, String.valueOf(i),
                        this.getMessage("REPORT_COMMON_FILE_E_0001", "文件内容")));
                continue;
            }


            PbcCapAssetInfo001Dto entity = new PbcCapAssetInfo001Dto();
            // 验证消息
            List<String> message = new ArrayList<>();
            // 资产代码
            String error0 = checkCode2(list.get(0), "资产代码", 50, true);

            //获取表中有效的资金信托代码
            List<PbcCapAssetInfo001Dto> cassetIdList = pbcCapAssetInfo001DtoMapper.selectPobcassetIdList();
            Map<String,PbcCapAssetInfo001Dto>cassetIdMap = new HashMap<String,PbcCapAssetInfo001Dto>();
            for(PbcCapAssetInfo001Dto key :cassetIdList){
                cassetIdMap.put(key.getPobcassetid(),key);
            }

            // 为空判断
            if (StringUtil.isEmpty(list.get(0))) {
                message.add(this.getMessage("REPORT_COMMON_FILE_E_0001", "资产代码"));
                checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            } else {
                entity.setAssetid(list.get(0));
            }
            //检查资金信托代码有没有对应多个资产代码
            if (cassetIdMap.get(list.get(2)) != null && !StringUtil.isEmpty(list.get(0))) {
                if(!cassetIdMap.get(list.get(2)).getAssetid().equals(list.get(0))) {
                    message.add("资金信托代码已存在，资金信托代码只能对应一个资产代码");
                    checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                            StringUtil.joinString(message, GlobalConstant.COMMA)));
                    continue;
                }
            }

            /*add by zhoubin 20220326 start*/
            if (StringUtils.isNotBlank(list.get(2))) {
                // 查询最新人行资金信托代码拼接资产代码的字符串
                String value = poCassetIdMap.get(list.get(2));
                if (StringUtils.isBlank(value)) {
                    // 如果value是空的 说明是第一次取到
                    poCassetIdMap.put(list.get(2), list.get(0));
                } else {
                    if (StringUtils.isBlank(pobcassetidList)) {
                        pobcassetidList = list.get(2)+",";
                    }
                    if (pobcassetidList != null && !pobcassetidList.contains(list.get(2))) {
                        pobcassetidList = pobcassetidList + list.get(2)+",";
                    }
                }
            }
            if(StringUtils.isNotBlank(pobcassetidList)) {
                message.add("人行资金信托代码"+pobcassetidList+"在Excel中对应了多个资产代码");
                checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            }
            /*add by zhoubin 20220326 end*/
            // 资产名称
            // 为空判断
            if (StringUtils.isNotBlank(list.get(1))) {
                entity.setAssetname(list.get(1));
            }
            if (StringUtil.isNotEmpty(error0)) {
                message.add(error0);
                checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("assetid", entity.getAssetid());
                map.put("assetname", entity.getAssetname());
            }
            // 资金信托代码
            // 为空判断
            String error1 = checkCode2(list.get(2), "资金信托代码", 50, true);
            if (StringUtil.isNotEmpty(error1)) {
                message.add(error1);
                checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            } else {
                entity.setPobcassetid(list.get(2));
            }
            // 判断Excel文件中是否存在重复的资产代码
            String assetId = entity.getAssetid();
            if (StringUtils.isNotBlank(assetId)) {
                String value = hashMap.get(assetId);
                if (StringUtils.isBlank(value)) {
                    // 如果value是空的 说明是第一次取到
                    hashMap.put(assetId, assetId);
                } else {
                    message.add("资产代码重复");
                    checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                            StringUtil.joinString(message, GlobalConstant.COMMA)));
                    continue;
                }
            }
            // 判断Excel文件中资产代码在资产基本信息表中是否存在
            int statisticalAssetId = pbcCapAssetInfo001DtoMapper.statisticalAssetId(assetId);
            if (!(statisticalAssetId > 0)) {
                message.add("资产代码在资产基本信息表中不存在，请确认");
                checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            }
            // 判断导入的资产是否有不属于信托公司资管产品的情况
            int statisticalCorrectAssetId = pbcCapAssetInfo001DtoMapper.statisticalCorrectAssetId(assetId);
            if (!(statisticalCorrectAssetId > 0)) {
                message.add("资产不属于信托公司资管产品类别，请确认");
                checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
                continue;
            }
            List<PbcCapAssetInfo001Dto> dtoList = pbcCapAssetInfo001DtoMapper.selectAssetInofByAssetId(assetId);
            if (CollectionUtils.isNotEmpty(dtoList)) {
                entity.setAssetname(dtoList.get(0).getAssetname());
            }

//            // 判断Excel中同一个人行资金信托代码是否对应了多个资产代码同时去除重复相同更新的
//            String pobcassetIdAndAssetId = entity.getPobcassetid() + "|"
//                    + entity.getAssetid();
//            // Excel判断
//            if (StringUtils.isNotBlank(entity.getPobcassetid())) {
//                // 查询最新人行资金信托代码拼接资产代码的字符串
//                String value = hashMap.get(entity.getPobcassetid());
//                if (StringUtils.isBlank(value)) {
//                    // 如果value是空的 说明是第一次取到
//                    hashMap.put(entity.getPobcassetid(), entity.getAssetid());
//                } else {
//                    message.add("人行资金信托代码在Excel中对应了多个资产代码，请确认");
//                    checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 1),
//                            StringUtil.joinString(message, GlobalConstant.COMMA)));
//                    continue;
//                }
//            }
//            if (StringUtils.isNotBlank(pobcassetIdAndAssetId)) {
//                // 查询最新人行资金信托代码拼接资产代码的字符串
//                String newPobcassetIdAndAssetId = pbcCapAssetInfo001DtoMapper.selectPobcassetIdAndAssetId(entity.getPobcassetid());
//                String value = hashMap.get(pobcassetIdAndAssetId);
//                if (StringUtils.isBlank(value)) {
//                    // 如果value是空的 说明是第一次取到
//                    hashMap.put(pobcassetIdAndAssetId, pobcassetIdAndAssetId);
//                    // 判断Excel中同一个人行资金信托代码是否对应了多个资产代码同时去除更新的(DB中)
//                    if(StringUtils.isNotBlank(newPobcassetIdAndAssetId)) {
//                        if (!StringUtils.equalsIgnoreCase(pobcassetIdAndAssetId, newPobcassetIdAndAssetId)) {
//                            message.add("人行资金信托代码已经在数据库中对应了不同的资产代码，请确认");
//                        }
//                    }
//                }
//            }
            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(entity.getAssetid(), String.valueOf(i + 2),
                        StringUtil.joinString(message, GlobalConstant.COMMA)));
            } else {
                entities.add(entity);
            }
        }
        return checkResult;
    }

    /**
     * 判断代码是否合法，返回错误信息
     *
     * @param code        代码
     * @param length      长度
     * @param cloumnTitle 列名
     * @param isRequired  是否必须
     * @return 错误信息
     */
    private String checkCode2(String code, String cloumnTitle, int length, boolean isRequired) {
        // 必须验证
        if (StringUtil.isEmpty(code)) {
            if (isRequired) {
                return this.getMessage("REPORT_COMMON_E_0001", cloumnTitle);
            }
            return null;
        }
        // 长度验证
        if (CommonUtil.getStringLen(code) > length) {
            return this.getMessage("REPORT_COMMON_E_0002", cloumnTitle, length);
        }
        return null;
    }

}
