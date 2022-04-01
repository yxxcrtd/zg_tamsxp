package com.joyintech.tams.report.submitmaintenance.salerinstinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dao.ChbSalerInstInfoMapper;
import com.joyintech.tams.report.submitmaintenance.salerinstinfo.dto.SalerInstInfoDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("SalerInstInfo001Service")
public class SalerInstInfo001Service extends CommonExportService<SalerInstInfoDto> {
    @Autowired
    private ChbSalerInstInfoMapper chbSalerInstInfoMapper;
    /**
     * 当在做导入前的check就是判断业务数据是否需要覆盖的时候在webresult的data中塞入这个值可以不会弹框两次
     */
    private static final String NO_INFO_SHOWOUT = "noInfoShowOut";

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 分页查询
     */
    public Page<SalerInstInfoDto> selectForPage(int page, int length, Map<String, Object> pageParam)
    {
        Page<SalerInstInfoDto> pageList = super.queryForPage(page,length,() -> chbSalerInstInfoMapper.selectForPage(pageParam));
        this.formatByType(pageList.getRoot());
        return pageList;
    }

    /**
     * 导出查询
     * @param params
     * @return
     * @throws Exception
     */
    public List<SalerInstInfoDto> queryAllList(Map<String, Object> params) throws Exception{
        return chbSalerInstInfoMapper.selectForPage(params);
    }

    /**
     * 删除
     */
    public boolean deleteByPrimaryKey(SalerInstInfoDto dto){
        List<SalerInstInfoDto> dtolist = dto.getSelectedItem();
        //        查询销售人员
        List<SalerInstInfoDto> idenInfoList = chbSalerInstInfoMapper.selectIdenInfo();
        Map<String,SalerInstInfoDto> idenInfoMap = new HashMap<String,SalerInstInfoDto>();
        if(idenInfoList.size() != 0) {
            for(SalerInstInfoDto info : idenInfoList) {
                idenInfoMap.put(info.getSalerOrgCode(),info);
            }
        }
        //        查询奖惩记录
        List<SalerInstInfoDto> rewPenaInfoList = chbSalerInstInfoMapper.selectRewPenaInfo();
        Map<String,SalerInstInfoDto> rewPenaInfoMap = new HashMap<String,SalerInstInfoDto>();
        if(idenInfoList.size() != 0) {
            for(SalerInstInfoDto info : rewPenaInfoList) {
                rewPenaInfoMap.put(info.getSalerInsNo(),info);
            }
        }
        if (!dtolist.isEmpty() && dtolist.size() != 0) {
            for (int i = 0 ; i < dtolist.size() ; i++) {
                String salerOrgCode = dtolist.get(i).getSalerOrgCode();
                String erroOrInfo = dtolist.get(i).getErroOrInfo();
                if(erroOrInfo.equals("erro")){
                    String workflowId = dtolist.get(i).getWorkflowId();
                    //erro表的直接物理删除
                    chbSalerInstInfoMapper.deleteErroByPrimaryKey(workflowId);
                }else{
                    if(idenInfoMap.get(salerOrgCode) != null){
                        chbSalerInstInfoMapper.deleteIdenInfo(salerOrgCode);
                        if(rewPenaInfoMap.get(idenInfoMap.get(salerOrgCode).getSalerInsNo()) != null){
                            chbSalerInstInfoMapper.deleteRewPenaInfo(idenInfoMap.get(salerOrgCode).getSalerInsNo());
                        }
                    }
                    //info表内容逻辑删除
                    chbSalerInstInfoMapper.deleteInfo(salerOrgCode);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 导入文件之前检查文件 导入的excel是否有重复数据
     *
     * param fileName 导入的文件名名
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList) {
        AjaxResult webResult = new AjaxResult();
        List<ValidMessage> errorList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            for (int j = i+1; j < dataList.size(); j++ ) {
                if(StringUtils.equals(dataList.get(i).get(1), dataList.get(j).get(1))){
                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"总部或分支机构编码与"+"第"+(j+2)+"总部或分支机构编码");
                    errorList.add(validMessage);
                }
                if(StringUtils.equals(dataList.get(i).get(2), dataList.get(j).get(2))){
                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"总部或分支机构名称与"+"第"+(j+2)+"总部或分支机构名称");
                    errorList.add(validMessage);
                }
            }
        }
        if(errorList.size() == 0){
            webResult.setData(NO_INFO_SHOWOUT);
        }else{
            webResult.setErrorList(errorList);
        }
        return webResult;
    }

    /**
     * 导入文件
     * <p>
     * 解析excel文件并进行验证
     * </p>
     * @return 导入结果
     */
    public List<ExcelValidDto> importData(List<List<String>> dataList, List<List<String>> tempList, String userName, String orgCode, String deptCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<String>();
            headList.add("销售机构代码");
            headList.add("总部或分支机构编码");
            headList.add("总部或分支机构名称");
            headList.add("机构层级");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<SalerInstInfoDto> entities = new ArrayList<>(dataList.size());
                List<ExcelValidDto> checkResult= checkExcelSales(dataList, entities,userName,deptCode);
                if (checkResult.isEmpty()) {
                    entities = convertToEntity(dataList, orgCode);
                    //校验是否已存在逻辑删除的数据
                    List<SalerInstInfoDto> SalerInstInfoDImportList =  chbSalerInstInfoMapper.checkSalerInstInfoDImport();
                    Map<String,SalerInstInfoDto> SalerInstInfoDImportMap = new HashMap<String,SalerInstInfoDto>();
                    for(SalerInstInfoDto info :SalerInstInfoDImportList){
                        SalerInstInfoDImportMap.put(info.getSalerOrgCode(),info);
                    }
                    for(SalerInstInfoDto dto : entities){
                        //check表中是否存在已逻辑删除的数据
                        if(SalerInstInfoDImportMap.get(dto.getSalerOrgCode()) != null){
                            //如果存在就先 删除表中删除状态数据再 新增插入
                            chbSalerInstInfoMapper.deleteByPrimaryKey(dto.getSalerOrgCode());
                        }
                        chbSalerInstInfoMapper.insert(dto);
                    }
                }
                return checkResult;
            }
        }else{
            List<ExcelValidDto> checkResult = new ArrayList<>();
            ExcelValidDto excelValidDto = new ExcelValidDto();
            String errorMsg = "模板为空，请输入数据！";
            excelValidDto.setErrorInfo(errorMsg);
            checkResult.add(excelValidDto);
            return checkResult;
        }
        return null;
    }

    /**
     * 导入数据check
     * @param dataList
     * @param entities
     * @param userName
     * @param deptCode
     * @return
     */
    private List<ExcelValidDto> checkExcelSales(List<List<String>> dataList, List<SalerInstInfoDto> entities, String userName, String deptCode) {
        String orgCode = CommonUtil.getOrgCode();
        String issuePartyId = chbSalerInstInfoMapper.selectIssuePartyId(orgCode);
        //导入重复校验
        List<SalerInstInfoDto> SalerInstInfoList = chbSalerInstInfoMapper.checkSalerInstInfoImport();
        Map<String,SalerInstInfoDto> salerOrgCodeMap = new HashMap<String,SalerInstInfoDto>();
        Map<String,SalerInstInfoDto> salerOrgNameMap = new HashMap<String,SalerInstInfoDto>();
        for(SalerInstInfoDto info :SalerInstInfoList){
            salerOrgCodeMap.put(info.getSalerOrgCode(),info);
        }
        for(SalerInstInfoDto info :SalerInstInfoList){
            salerOrgNameMap.put(info.getSalerOrgName(),info);
        }
        List<ExcelValidDto> checkResult = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            //是否正常标志
            boolean flag = true;
            List<String> list = dataList.get(i);
            List<String> message = new ArrayList<>();
            //销售机构代码
            if(list.get(0) == null || list.get(0).trim().equals("")){
                flag = false;
                message.add("销售机构代码不能为空");
            }else if(list.get(0) != null && !list.get(0).trim().equals("")){
                if(!issuePartyId.equals(list.get(0))){
                    flag = false;
                    message.add("销售机构代码不是本行机构代码");
                }
            }
            //总部或分支机构编码
            if(list.get(1) == null || list.get(1).trim().equals("")){
                flag = false;
                message.add("总部或分支机构编码不能为空");
            }else{
                String regex = "^[a-z0-9A-Z]+$";
                if (!list.get(1).matches(regex)) {
                    flag = false;
                    message.add("总部或分支机构编码只能填写字母或数字");
                }else {
                        if(list.get(1).length() > 20){
                            flag = false;
                            message.add("总部或分支机构编码不能超过20字符");
                        } else {
                            if (salerOrgCodeMap.get(list.get(1)) != null) {
                                flag = false;
                                message.add("总部或分支机构编码已存在");
                            }
                        }
                    }
            }
            //总部或分支机构名称
            if(list.get(2) == null || list.get(2).trim().equals("")){
                flag = false;
                message.add("总部或分支机构名称不能为空");
            }else{
                if(getWordCount(list.get(2)) > 200){
                    flag = false;
                    message.add("总部或分支机构名称不能超过200字节");
                }
                if(salerOrgNameMap.get(list.get(2)) != null){
                    flag = false;
                    message.add("总部或分支机构名称已存在");
                }
            }
            //机构层级
            if(list.get(3) == null || list.get(3).trim().equals("")){
                flag = false;
                message.add("机构层级不能为空");
            }

            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(list.get(0), String.valueOf(i+2),
                        StringUtil.joinString(message, GlobalConstant.SEMICOLON)));
            }
        }
        return checkResult;
    }

    /**
     * excel数据转为java对象
     * <p> 将excel导入的数据，按行转为ProRegistCodeManage001Dto  FinProductValuation对象，同时验证各字段值</p>
     * @param dataList excel导入的数据
     * @return 验证信息
     */
    private List<SalerInstInfoDto> convertToEntity(List<List<String>> dataList,String orgCode) {
        // 返回的list定义
        List<SalerInstInfoDto> returnInfoList = new ArrayList<SalerInstInfoDto>();
        SalerInstInfoDto SalerInstInfoDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            SalerInstInfoDto = new SalerInstInfoDto();
            //销售机构代码
            SalerInstInfoDto.setSalerInstCode(list.get(0));

            //总部或分支机构编码
            SalerInstInfoDto.setSalerOrgCode(list.get(1));

            //总部或分支机构名称
            SalerInstInfoDto.setSalerOrgName(list.get(2));

            //机构层级
            SalerInstInfoDto.setSalerOrgLevel(this.subDictcode(list.get(3), " "));

            //数据状态
            SalerInstInfoDto.setStatus("E");
            SalerInstInfoDto.setOrgCode(CommonUtil.getOrgCode());
            SalerInstInfoDto.setCreateDept(CommonUtil.getDeptCode());
            returnInfoList.add(SalerInstInfoDto);
        }
        return returnInfoList;
    }


    /**
     * 总部或分支机构编码联想框
     */
    public List<SuggestDto> getSalerOrgCode(Map<String, Object> map) {
        return chbSalerInstInfoMapper.getSalerOrgCode(map);
    }


    public static String subDictcode(String value, String sign) {
        if (!isEmpty(value)) {
            int i = value.indexOf(sign);
            if (i > -1) {
                value = value.substring(0, i);
            }
        }
        return value;
    }

    public static boolean isEmpty(String value) {
        boolean flag = false;
        if(StringUtil.isEmpty(value)){
            flag = true;
        }
        else if(StringUtil.isEmpty(value.trim())){
            flag = true;
        }
        return flag;
    }

    //UTF-8获取字符串字节长度
    public int getWordCount(String s)
    {
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)
                length++;
            else
                length += 2;

        }
        return length;

    }

    public static List<SalerInstInfoDto> formatByType(List<SalerInstInfoDto> dataList) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(item -> {
                item.setSalerOrgLevel(getDictCodeAndName(DictCode.机构层级, item.getSalerOrgLevel()));
            });
        }
        return dataList;
    }

    /**
     * 转换成key+value形式
     * @param dictCode  字典表中数据类型
     * @param value  需要换换的值
     * @return  key+value
     * @see
     * @since 1.0
     */
    public static String getDictCodeAndName(DictCode dictCode,String value){
        if(com.joyintech.framework.util.StringUtil.isNotEmpty(value)){
            String tempName = SysDataUtil.getNameFromDataDict(dictCode, value);
            if (com.joyintech.framework.util.StringUtil.isEmpty(tempName)) {
                return value;
            } else {
                return value + " " + SysDataUtil.getNameFromDataDict(dictCode, value);
            }
        }
        return "";
    }

}
