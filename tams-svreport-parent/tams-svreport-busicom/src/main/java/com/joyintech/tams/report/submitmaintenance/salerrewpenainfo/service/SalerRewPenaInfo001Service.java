package com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.service;

import com.joyintech.background.component.common.pack.common.util.StringUtil;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dto.SalerIdenInfoDto;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dao.ChbSalerRewPenaInfoMapper;
import com.joyintech.tams.report.submitmaintenance.salerrewpenainfo.dto.SalerRewPenaInfoDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("SalerRewPenaInfo001Service")
public class SalerRewPenaInfo001Service extends CommonExportService<SalerRewPenaInfoDto> {
    @Autowired
    private ChbSalerRewPenaInfoMapper chbSalerRewPenaInfoMapper;
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
    public Page<SalerRewPenaInfoDto> selectForPage(int page, int length, Map<String, Object> pageParam)
    {
        Page<SalerRewPenaInfoDto> pageList = super.queryForPage(page,length,() -> chbSalerRewPenaInfoMapper.selectForPage(pageParam));
        this.formatByType(pageList.getRoot());
        return pageList;
    }

    /**
     * 导出查询
     * @param params
     * @return
     * @throws Exception
     */
    public List<SalerRewPenaInfoDto> queryAllList(Map<String, Object> params) throws Exception{
        return chbSalerRewPenaInfoMapper.selectForPage(params);
    }

    /**
     * 删除
     */
    public boolean deleteByPrimaryKey(SalerRewPenaInfoDto dto){
        List<SalerRewPenaInfoDto> dtolist = dto.getSelectedItem();
        if (!dtolist.isEmpty() && dtolist.size() != 0) {
            for (int i = 0 ; i < dtolist.size() ; i++) {
                String salerRewPenaNo = dtolist.get(i).getSalerRewPenaNo();
                String erroOrInfo = dtolist.get(i).getErroOrInfo();
                if(erroOrInfo.equals("erro")){
                    //erro表的直接物理删除
                    chbSalerRewPenaInfoMapper.deleteErroByPrimaryKey(salerRewPenaNo);
                }else{
                    //info表内容逻辑删除
                    chbSalerRewPenaInfoMapper.deleteInfo(salerRewPenaNo);
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
//        for (int i = 0; i < dataList.size(); i++ ) {
//            for (int j = i+1; j < dataList.size(); j++ ) {
//            }
//        }
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
            headList.add("内部编号");
            headList.add("记录分类");
            headList.add("奖惩名称");
            headList.add("奖惩说明");
            headList.add("奖惩单位");
            headList.add("奖惩单位具体名称");
            headList.add("奖惩起始日期");
            headList.add("奖惩结束日期");
            headList.add("投诉详情");
            headList.add("投诉处理结果");
            headList.add("投诉日期");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<SalerRewPenaInfoDto> entities = new ArrayList<>(dataList.size());
                List<ExcelValidDto> checkResult= checkExcelSales(dataList, entities,userName,deptCode);
                if (checkResult.isEmpty()) {
                    entities = convertToEntity(dataList, orgCode);
                    for(SalerRewPenaInfoDto dto : entities){
                        chbSalerRewPenaInfoMapper.insert(dto);
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
    private List<ExcelValidDto> checkExcelSales(List<List<String>> dataList, List<SalerRewPenaInfoDto> entities, String userName, String deptCode) {
        String orgCode = CommonUtil.getOrgCode();
        String issuePartyId = chbSalerRewPenaInfoMapper.selectIssuePartyId(orgCode);
        //查询已维护的销售人员内部编码
        List<SalerRewPenaInfoDto> salerInsNoList = chbSalerRewPenaInfoMapper.checkSalerInsNoImport();
        Map<String,SalerRewPenaInfoDto> salerInsNoMap = new HashMap<String,SalerRewPenaInfoDto>();
        for(SalerRewPenaInfoDto info :salerInsNoList){
            salerInsNoMap.put(info.getSalerInsNo(),info);
        }
        List<ExcelValidDto> checkResult = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            //是否正常标志
            boolean flag = true;
            List<String> list = dataList.get(i);

            Map<String,Object> param = new HashMap<String,Object>();
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
            //内部编号
            if(list.get(1) == null || list.get(1).trim().equals("")){
                flag = false;
                message.add("内部编号不能为空");
            }else{
                   if(salerInsNoMap.get(list.get(1)) == null){
                      flag = false;
                      message.add("内部编号未被维护");
                   }
            }
            //记录分类
            if(list.get(2) == null || list.get(2).trim().equals("")){
                flag = false;
                message.add("记录分类不能为空");
            }

            //奖惩名称
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(3) != null && !(list.get(3).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择投诉时，奖惩名称不可填");
                    }
                }else {
                    if (list.get(3) == null || list.get(3).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，奖惩名称不能为空");
                    }else if(getWordCount(list.get(3)) > 100){
                        flag = false;
                        message.add("奖惩名称不能超过100字节");
                    }
                }
            }
            //奖惩说明
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(4) != null && !(list.get(4).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择投诉时，奖惩说明不可填");
                    }
                }else {
                    if (list.get(4) == null || list.get(4).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，奖惩说明不能为空");
                    }else if(getWordCount(list.get(4)) > 400){
                        flag = false;
                        message.add("奖惩说明不能超过400字节");
                    }
                }
            }
            //奖惩单位
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(5) != null && !(list.get(5).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择投诉时，奖惩单位不可填");
                    }
                }else {
                    if (list.get(5) == null || list.get(5).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，奖惩单位不能为空");
                    }
                }
            }
            //奖惩单位具体名称
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(list.get(5) != null && !(list.get(5).trim().equals(""))) {
                    if(((this.subDictcode(list.get(2)," ").equals("01")) || (this.subDictcode(list.get(2)," ").equals("02")))
                      && this.subDictcode(list.get(5)," ").equals("99")){
                        if (list.get(6) == null || list.get(6).trim().equals("")) {
                            flag = false;
                            message.add("记录分类选择奖励或惩处时且奖惩单位选择其他时，奖惩单位具体名称不能为空");
                        }else if(getWordCount(list.get(6)) > 200){
                            flag = false;
                            message.add("奖惩单位具体名称不能超过200字节");
                        }
                    }else{
                        if(list.get(6) != null && !list.get(6).trim().equals("")) {
                            flag = false;
                            message.add("奖惩单位具体名称不可填");
                        }
                    }
                }
            }
            //奖惩起始日期
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(7) != null && !(list.get(7).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择投诉时，奖惩起始日期不可填");
                    }
                }else {
                    if (list.get(7) == null || list.get(7).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，奖惩起始日期不能为空");
                    }else if(!checkDate(list.get(7))){
                        flag = false;
                        message.add("奖惩起始日期格式不正确");
                    }
                }
            }
            //奖惩结束日期
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(8) != null && !(list.get(8).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择投诉时，奖惩结束日期不可填");
                    }
                }else {
                    if (list.get(8) == null || list.get(8).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，奖惩结束日期不能为空");
                    }else if(!checkDate(list.get(8))){
                        flag = false;
                        message.add("奖惩结束日期格式不正确");
                    }else{
                        Integer datecheck = list.get(7).compareTo(list.get(8));
                        if(datecheck > 0){
                            flag = false;
                            message.add("奖惩结束日期不可小于奖惩开始日期。");
                        }
                    }
                }
            }
            //投诉详情
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(9) == null || list.get(9).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择投诉时，投诉详情不可为空");
                    }else if(getWordCount(list.get(9)) > 400){
                        flag = false;
                        message.add("投诉详情不能超过400字节");
                    }
                }else {
                    if (list.get(9) != null && !(list.get(9).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，投诉详情不可填");
                    }
                }
            }
            //投诉处理结果
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(10) == null || list.get(10).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择投诉时，投诉处理结果不可为空");
                    }else if(getWordCount(list.get(10)) > 400){
                        flag = false;
                        message.add("投诉处理结果不能超过400字节");
                    }
                }else {
                    if (list.get(10) != null && !(list.get(10).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，投诉处理结果不可填");
                    }
                }
            }
            //投诉日期
            if(list.get(2) != null && !(list.get(2).trim().equals(""))) {
                if(this.subDictcode(list.get(2)," ").equals("03")) {
                    if (list.get(11) == null || list.get(11).trim().equals("")) {
                        flag = false;
                        message.add("记录分类选择投诉时，投诉日期不可为空");
                    }else if(!checkDate(list.get(11))){
                        flag = false;
                        message.add("投诉日期格式不正确");
                    }
                }else {
                    if (list.get(11) != null && !(list.get(11).trim().equals(""))) {
                        flag = false;
                        message.add("记录分类选择奖励或惩处时，投诉日期不可填");
                    }
                }
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
    private List<SalerRewPenaInfoDto> convertToEntity(List<List<String>> dataList,String orgCode) {
        // 返回的list定义
        List<SalerRewPenaInfoDto> returnInfoList = new ArrayList<SalerRewPenaInfoDto>();
        SalerRewPenaInfoDto salerRewPenaInfoDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            salerRewPenaInfoDto = new SalerRewPenaInfoDto();
            //销售机构代码
            salerRewPenaInfoDto.setSalerInstCode(list.get(0));

            //内部编码
            salerRewPenaInfoDto.setSalerInsNo(list.get(1));

            //记录分类
            salerRewPenaInfoDto.setSalerRewPenaTyep(this.subDictcode(list.get(2)," "));

            //奖惩名称
            if(list.get(3) != null && !(list.get(3).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerRewPenaName(list.get(3));
            }

            //奖惩说明
            if(list.get(4) != null && !(list.get(4).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerRewPenaComments(list.get(4));
            }

            //奖惩单位
            if(list.get(5) != null && !(list.get(5).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerRewPenaInst(this.subDictcode(list.get(5)," "));
            }

            //奖惩单位具体名称
            if(list.get(6) != null && !(list.get(6).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerRewPenaInstName(list.get(6));
            }

            //奖惩起始日期
            if(list.get(7) != null && !(list.get(7).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerRewPenaStart(DateUtil.parse(list.get(7)));
            }

            //奖惩结束日期
            if(list.get(8) != null && !(list.get(8).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerRewPenaEnd(DateUtil.parse(list.get(8)));
            }

            //投诉详情
            if(list.get(9) != null && !(list.get(9).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerComplainDetail(list.get(9));
            }

            //投诉处理结果
            if(list.get(10) != null && !(list.get(10).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerComplainResult(list.get(10));
            }

            //投诉日期
            if(list.get(11) != null && !(list.get(11).trim().equals(""))) {
                salerRewPenaInfoDto.setSalerComplainDate(DateUtil.parse(list.get(11)));
            }

            //数据状态
            salerRewPenaInfoDto.setStatus("E");
            salerRewPenaInfoDto.setSalerRewPenaNo(UUID.randomUUID().toString().replace("-","").toUpperCase());
            salerRewPenaInfoDto.setOrgCode(CommonUtil.getOrgCode());
            salerRewPenaInfoDto.setCreateDept(CommonUtil.getDeptCode());
            returnInfoList.add(salerRewPenaInfoDto);
        }
        return returnInfoList;
    }


    /**
     * 内部编号联想框
     */
    public List<SuggestDto> getSalerInsNo(Map<String, Object> map) {
        return chbSalerRewPenaInfoMapper.getSalerInsNo(map);
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
        if(com.joyintech.framework.util.StringUtil.isEmpty(value)){
            flag = true;
        }
        else if(com.joyintech.framework.util.StringUtil.isEmpty(value.trim())){
            flag = true;
        }
        return flag;
    }

    /**
     * 日期格式校验
     *
     * @param
     * @return 返回boolean
     */
    private boolean checkDate(String date) {
        boolean flag = true;
        if(!DateUtil.isDateStr(date, DateUtil.DEFAULT_OTHER_FORMAT,
                DateUtil.DEFAULT_ANOTHER_FORMAT,DateUtil.DEFAULT_DATE_FORMAT_EN)){
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

    public static List<SalerRewPenaInfoDto> formatByType(List<SalerRewPenaInfoDto> dataList) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(item -> {
                item.setSalerRewPenaTyep(getDictCodeAndName(DictCode.记录分类, item.getSalerRewPenaTyep()));
                item.setSalerRewPenaInst(getDictCodeAndName(DictCode.奖惩单位, item.getSalerRewPenaInst()));
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
