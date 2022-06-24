package com.joyintech.tams.report.submitmaintenance.salerideninfo.service;

import com.google.common.collect.Lists;
import com.ibm.db2.jcc.t4.e;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.common.svreport.base.dto.BaseCheckOracleDto;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dao.ChbSalerIdenInfoMapper;
import com.joyintech.tams.report.submitmaintenance.salerideninfo.dto.SalerIdenInfoDto;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.DVALRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service("SalerIdenInfo001Service")
public class SalerIdenInfo001Service extends CommonExportService<SalerIdenInfoDto> {
    @Autowired
    private ChbSalerIdenInfoMapper chbSalerIdenInfoMapper;
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
    public Page<SalerIdenInfoDto> selectForPage(int page, int length, Map<String, Object> pageParam)
    {
        Page<SalerIdenInfoDto> pageList = super.queryForPage(page,length,() -> chbSalerIdenInfoMapper.selectForPage(pageParam));
        this.formatByType(pageList.getRoot());
        return pageList;
    }

    /**
     * 导出查询
     * @param params
     * @return
     * @throws Exception
     */
    public List<SalerIdenInfoDto> queryAllList(Map<String, Object> params) throws Exception{
        List<SalerIdenInfoDto> dataList = chbSalerIdenInfoMapper.selectForPage(params);
        this.formatByType(dataList);
        return dataList;
    }

    /**
     * 删除
     */
    public boolean deleteByPrimaryKey(SalerIdenInfoDto dto){
        List<SalerIdenInfoDto> dtolist = dto.getSelectedItem();
        //查询奖惩记录
        List<SalerIdenInfoDto> rewPenaInfoList = chbSalerIdenInfoMapper.selectRewPenaInfo();
        Map<String,SalerIdenInfoDto> rewPenaInfoMap = new HashMap<String,SalerIdenInfoDto>();
        if(rewPenaInfoList.size() != 0) {
            for(SalerIdenInfoDto info : rewPenaInfoList) {
                rewPenaInfoMap.put(info.getSalerInsNo(),info);
            }
        }
        if (!dtolist.isEmpty() && dtolist.size() != 0) {
            for (int i = 0 ; i < dtolist.size() ; i++) {
                String salerInsNo = dtolist.get(i).getSalerInsNo();
                String erroOrInfo = dtolist.get(i).getErroOrInfo();
                if(erroOrInfo.equals("erro")){
                    String workflowId = dtolist.get(i).getWorkflowId();
                    //erro表的直接物理删除
                    chbSalerIdenInfoMapper.deleteErroByPrimaryKey(workflowId);
                }else{
                    if(rewPenaInfoMap.get(salerInsNo) != null){
                            chbSalerIdenInfoMapper.deleteRewPenaInfo(salerInsNo);
                    }
                    //info表内容逻辑删除
                    chbSalerIdenInfoMapper.deleteInfo(salerInsNo);
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
    public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList,String fileName) {
        AjaxResult webResult = new AjaxResult();
        List<ValidMessage> errorList = new ArrayList<>();
        if (!fileName.contains("销售人员身份信息")) {
            errorList.add( new ValidMessage("导入模板不一致, 请重新选择文件!"));
            webResult.setErrorList(errorList);
            return webResult;
        }
        for (int i = 0; i < dataList.size(); i++ ) {
            for (int j = i+1; j < dataList.size(); j++ ) {
                if(StringUtils.equals(dataList.get(i).get(7), dataList.get(j).get(7))){
                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"内部编号与"+"第"+(j+2)+"内部编号不能重复");
                    errorList.add(validMessage);
                }
                if(dataList.get(i).get(5)!=null&&dataList.get(j).get(5)!=null&&!(dataList.get(i).get(5).isEmpty()) && !(dataList.get(j).get(5).isEmpty())) {
                    if (this.subDictcode(dataList.get(i).get(4), " ").equals("99") && this.subDictcode(dataList.get(j).get(4), " ").equals("99")) {
                        if (StringUtils.equals(dataList.get(i).get(4), dataList.get(j).get(4))) {
                            if (StringUtils.equals(dataList.get(i).get(5), dataList.get(j).get(5))) {
                                if (StringUtils.equals(dataList.get(i).get(6), dataList.get(j).get(6))) {
                                    ValidMessage validMessage = new ValidMessage("当证件类型都为其他时" + "第" + (i + 2) + "第" + (j + 2) + "其他证件名称相同时，证件号码不能相同");
                                    errorList.add(validMessage);
                                }
                            }
                        }
                    }
                }
                if(!(this.subDictcode(dataList.get(i).get(4), " ").equals("99")) && !(this.subDictcode(dataList.get(j).get(4), " ").equals("99"))){
                    if(StringUtils.equals(dataList.get(i).get(4), dataList.get(j).get(4))){
                            if(StringUtils.equals(dataList.get(i).get(6), dataList.get(j).get(6))){
                                ValidMessage validMessage = new ValidMessage("第"+(i+2) +"第"+(j+2)+"证件类型与证件号码不能同时相等");
                                errorList.add(validMessage);
                            }
                    }
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
    @Transactional(rollbackFor = Exception.class)
    public List<ExcelValidDto> importData(List<List<String>> dataList, List<List<String>> tempList, String userName, String orgCode, String deptCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<String>();
            headList.add("销售机构代码");
            headList.add("姓名");
            headList.add("性别");
            headList.add("出生日期");
            headList.add("证件类别");
            headList.add("其他证件名称");
            headList.add("证件号码");
            headList.add("内部编号");
            headList.add("所属区域");
            headList.add("所属总部或分支机构编码");
            headList.add("所属部门");
            headList.add("职位");
            headList.add("学历");
            headList.add("学位");
            headList.add("首次参加工作时间");
            headList.add("首次从事金融工作时间");
            headList.add("首次从事理财销售时间");
            headList.add("专业技术职称");
            headList.add("理财专业证书");
            headList.add("理财登记培训证书编号");
            headList.add("可销售产品风险等级");
            headList.add("销售资质");
            headList.add("办公电话");
            headList.add("移动电话");
            headList.add("电子邮箱");
            headList.add("当前状态");
            headList.add("备注");

            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<SalerIdenInfoDto> entities = new ArrayList<>(dataList.size());
                List<ExcelValidDto> checkResult= checkExcelSales(dataList, entities,userName,deptCode);
                if (checkResult.isEmpty()) {
                    entities = convertToEntity(dataList, orgCode);
                    //校验是否已存在逻辑删除的数据
                   // List<SalerIdenInfoDto> SalerIdenInfoDImportList =  chbSalerIdenInfoMapper.checkSalerIdentInfoDImport();
                    //Map<String,SalerIdenInfoDto> salerIdenInfoDImportMap = SalerIdenInfoDImportList.stream().collect(Collectors.toMap(SalerIdenInfoDto::getSalerInsNo, Function.identity()));;
                    //得到主键集合
                    List<String> list = entities.stream().map(SalerIdenInfoDto::getSalerInsNo).collect(Collectors.toList());
                    //分页
                    List<List<String>> partition = Lists.partition(list, 500);
                   //批量删除
                    for (List<String> ids:partition) {
                        chbSalerIdenInfoMapper.deleteBatchByPrimaryKey(ids);
                    }
                    //落库
                    for(SalerIdenInfoDto dto : entities){
                        chbSalerIdenInfoMapper.insert(dto);
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
    private List<ExcelValidDto> checkExcelSales(List<List<String>> dataList, List<SalerIdenInfoDto> entities, String userName, String deptCode) {
        String orgCode = CommonUtil.getOrgCode();
        String issuePartyId = chbSalerIdenInfoMapper.selectIssuePartyId(orgCode);
        //导入重复校验
        List<SalerIdenInfoDto> SalerIdenInfoList = chbSalerIdenInfoMapper.checkSalerIdenInfoImport();
        //内部编号map
        Map<String,SalerIdenInfoDto> salerInsNoMap = new HashMap<String,SalerIdenInfoDto>();
        //证件类别+证件号码map
        Map<String,SalerIdenInfoDto> salerCredentTypeMap = new HashMap<String,SalerIdenInfoDto>();
        //其他证件名称+证件号码map
        Map<String,SalerIdenInfoDto> salerOtherCredentNameMap = new HashMap<String,SalerIdenInfoDto>();
        //校验所属总部或分支机构是否已被维护
        List<SalerIdenInfoDto> SalerOrgCodeList = chbSalerIdenInfoMapper.checkSalerOrgCodeImport();
        Map<String,SalerIdenInfoDto> SalerOrgCodeMap = new HashMap<String,SalerIdenInfoDto>();
        if(SalerOrgCodeList.size() != 0){
            for(SalerIdenInfoDto info : SalerOrgCodeList){
                SalerOrgCodeMap.put(info.getSalerOrgCode(),info);
            }
        }
        if(SalerIdenInfoList.size() != 0){
            for(SalerIdenInfoDto info : SalerIdenInfoList){
                salerInsNoMap.put(info.getSalerInsNo(),info);
                if(!(info.getSalerCredentType().equals("99"))){
                    String salerCredentTypeFlag = info.getSalerCredentType() + " " + info.getSalerCredentNo();
                    salerCredentTypeMap.put(salerCredentTypeFlag,info);
                }else {
                    String salerOtherCredentNameFlag = info.getSalerOtherCredentName() + " " + info.getSalerCredentNo();
                    salerOtherCredentNameMap.put(salerOtherCredentNameFlag,info);
                }
            }
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
            //姓名
            if(list.get(1) == null || list.get(1).trim().equals("")){
                flag = false;
                message.add("姓名不能为空");
            }else if(getWordCount(list.get(1)) > 200){
                flag = false;
                message.add("姓名不能超过200字节");
            }
            //性别
            if(list.get(2) == null || list.get(2).trim().equals("")){
                flag = false;
                message.add("性别不能为空");
            }
            //出生日期
            if(list.get(3) == null || list.get(3).trim().equals("")){
                flag = false;
                message.add("出生日期不能为空");
            }else{
                if (!checkDate(list.get(3))) {
                    flag = false;
                    message.add("出生日期格式不正确");
                }
            }
            //证件类别
            if(list.get(4) == null || list.get(4).trim().equals("")){
                flag = false;
                message.add("证件类别不能为空");
            }
            //其他证件名称
            if(list.get(4) != null && !(list.get(4).trim().equals(""))){
                if(this.subDictcode(list.get(4)," ").equals("99")){
                    if(list.get(5) == null || list.get(5).trim().equals("")){
                        flag = false;
                        message.add("证件类别为其他时，其他证件名称不能为空");
                    }else if(getWordCount(list.get(5)) > 60){
                        flag = false;
                        message.add("其他证件名称不能超过60字节");
                    }
                }else {
                    if(list.get(5) != null && !(list.get(5).trim().equals(""))){
                        flag = false;
                        message.add("证件类别为非其他时，其他证件名称不能填写");
                    }
                }
            }
            //证件号码
            if(list.get(6) == null || list.get(6).trim().equals("")){
                flag = false;
                message.add("证件号码不能为空");
            }else{
                if(getWordCount(list.get(6)) > 30){
                    flag = false;
                    message.add("证件号码不能超过30字节");
                }
            }
            //内部编号
            if(list.get(7) == null || list.get(7).trim().equals("")){
                flag = false;
                message.add("内部编号不能为空");
            }else{
                if(getWordCount(list.get(7)) > 60){
                    flag = false;
                    message.add("内部编号不能超过60字节");
                }
            }
            //所属区域
            if(list.get(8) == null || list.get(8).trim().equals("")){
                flag = false;
                message.add("所属区域不能为空");
            }
            //所属总部或分支机构编码
            if(list.get(9) == null || list.get(9).trim().equals("")){
                flag = false;
                message.add("所属总部或分支机构编码不能为空");
            }else{
                if(SalerOrgCodeMap.get(list.get(9)) == null){
                    flag = false;
                    message.add("所属总部或分支机构编码未被维护");
                }
            }
            //所属部门
            if(list.get(10) == null || list.get(10).trim().equals("")){
                flag = false;
                message.add("所属部门不能为空");
            }else if(getWordCount(list.get(10)) > 40){
                flag = false;
                message.add("所属部门不能超过40字节");
            }
            //职位
            if(list.get(11) == null || list.get(11).trim().equals("")){
                flag = false;
                message.add("职位不能为空");
            }else if(getWordCount(list.get(11)) > 40){
                flag = false;
                message.add("职位不能超过40字节");
            }
            //学历
            if(list.get(12) == null || list.get(12).trim().equals("")){
                flag = false;
                message.add("学历不能为空");
            }
            //学位
            if(list.get(13) == null || list.get(13).trim().equals("")){
                flag = false;
                message.add("学位不能为空");
            }
            //首次参加工作时间
            if(list.get(14) == null || list.get(14).trim().equals("")){
                flag = false;
                message.add("首次参加工作时间不能为空");
            }else{
                if (!checkMonthDate(list.get(14))) {
                    flag = false;
                    message.add("首次参加工作时间格式不正确");
                }
            }
            //首次从事金融工作时间
            if(list.get(15) == null || list.get(15).trim().equals("")){
                flag = false;
                message.add("首次从事金融工作时间能为空");
            }else{
                if (!checkMonthDate(list.get(15))) {
                    flag = false;
                    message.add("首次从事金融工作时间格式不正确");
                }
            }
            //首次从事理财销售时间
            if(list.get(16) == null || list.get(16).trim().equals("")){
                flag = false;
                message.add("首次从事理财销售时间不能为空");
            }else{
                if (!checkMonthDate(list.get(16))) {
                    flag = false;
                    message.add("首次从事理财销售时间格式不正确");
                }
            }
            //专业技术职称
            if(list.get(17) == null || list.get(17).trim().equals("")){
                flag = false;
                message.add("专业技术职称不能为空");
            }
            //理财专业证书
            if(list.get(18) == null || list.get(18).trim().equals("")){
                flag = false;
                message.add("理财专业证书不能为空");
            }else{
                if(!checkSalerType(list.get(18),DictCode.销售人员理财专业证书)){
                    flag = false;
                    message.add("理财专业证书输入格式错误，请严格按照规则输入！");
                }else {
                    //判断是否有重复数据
                    String value = this.subDictcodeMultiple(list.get(18), ",");
                    if (StringUtils.isNotBlank(value)) {
                        List<String> split= Arrays.asList(value.split(","));
                        long count = split.stream().distinct().count();
                        if (split.size()>count) {
                            flag = false;
                            message.add("理财专业证书输入重复，请重新输入！");
                        }
                    }

                }
            }
            //理财登记培训证书编号（LC+12位数字）
            if(list.get(19) != null && !(list.get(19).trim().equals(""))){
                    String regex = "^([L][C][0-9]{12})$";
                    if(!list.get(19).matches(regex)){
                        flag = false;
                        message.add("理财登记培训证书编号格式不对");
                    }
            }
            //可销售产品风险等级
            if(list.get(20) == null || list.get(20).trim().equals("")){
                flag = false;
                message.add("可销售产品风险等级不能为空");
            }else{
                if(!checkSalerType(list.get(20),DictCode.可销售产品风险等级)){
                    flag = false;
                    message.add("可销售产品风险等级输入格式错误，请严格按照规则输入！");
                }else {
                    //判断是否有重复数据
                    String value = this.subDictcodeMultiple(list.get(20), ",");
                    if (StringUtils.isNotBlank(value)) {
                        List<String> split= Arrays.asList(value.split(","));
                        long count = split.stream().distinct().count();
                        if (split.size()>count) {
                            flag = false;
                            message.add("可销售产品风险等级输入重复，请重新输入！");
                        }
                    }

                }
            }
            //销售资质
            if(list.get(21) == null || list.get(21).trim().equals("")){
                flag = false;
                message.add("销售资质不能为空");
            }else if(getWordCount(list.get(21)) > 400){
                flag = false;
                message.add("销售资质不能超过400字节");
            }
            //办公电话
            if(list.get(22) == null || list.get(22).trim().equals("")){
                flag = false;
                message.add("办公电话不能为空");
            }else{
                String regex = "^\\d{3,4}-\\d{7,8}(-\\d{1,8})?";
                if(!list.get(22).matches(regex)){
                    flag = false;
                    message.add("办公电话格式不对");
                }
            }
            //移动电话
            if(list.get(23) == null || list.get(23).trim().equals("")){
                flag = false;
                message.add("移动电话不能为空");
            }else{
                String regex = "^1(3|4|5|6|7|8|9)\\d{9}$";
                if(!list.get(23).matches(regex)){
                    flag = false;
                    message.add("移动电话格式不对");
                }
            }
            //电子邮箱
            if(list.get(24) == null || list.get(24).trim().equals("")){
                flag = false;
                message.add("电子邮箱不能为空");
            }else{
                String regex = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
                if(!list.get(24).matches(regex)){
                    flag = false;
                    message.add("电子邮箱格式不对");
                }
            }
            //当前状态
            if(list.get(25) == null || list.get(25).trim().equals("")){
                flag = false;
                message.add("当前状态不能为空");
            }

            //备注(所属区域，学位，学历，专业技术职称，理财专业证书为“其他”时备注必填)
            if(list.get(8) != null && list.get(12) != null && list.get(13) != null && list.get(17) != null && list.get(18) != null) {
                if (this.subDictcode(list.get(8), " ").equals("900000") || this.subDictcode(list.get(12), " ").equals("99")
                        || this.subDictcode(list.get(13), " ").equals("99") || this.subDictcode(list.get(17), " ").equals("99")
                        || this.inclodeNine(list.get(18))) {
                    if (list.get(26) == null || list.get(26).trim().equals("")) {
                        flag = false;
                        message.add("所属区域，学位，学历，专业技术职称或理财专业证书选择其他时，备注不能为空");
                    }else if(getWordCount(list.get(26)) > 256){
                        flag = false;
                        message.add("备注不能超过256字节");
                    }
                }
            }
            //备注
            if(list.get(26) != null && !(list.get(26).trim().equals(""))) {
                if (getWordCount(list.get(26)) > 256) {
                    flag = false;
                    message.add("备注不能超过256字节");
                }
            }

            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(list.get(7), String.valueOf(i+2),
                        StringUtil.joinString(message, GlobalConstant.SEMICOLON)));
            }
        }
        return checkResult;
    }

    /**
     * excel数据转为java对象
     * @param dataList excel导入的数据
     * @return 验证信息
     */
    private List<SalerIdenInfoDto> convertToEntity(List<List<String>> dataList,String orgCode) {
        // 返回的list定义
        List<SalerIdenInfoDto> returnInfoList = new ArrayList<SalerIdenInfoDto>();
        SalerIdenInfoDto salerIdenInfoDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            salerIdenInfoDto = new SalerIdenInfoDto();
            //销售机构代码
            salerIdenInfoDto.setSalerInstCode(list.get(0));

            //姓名
            salerIdenInfoDto.setSalerName(list.get(1));

            //性别
            salerIdenInfoDto.setSalerSex(this.subDictcode(list.get(2), " "));

            //出生日期
            salerIdenInfoDto.setSalerBirthday(DateUtil.parse(list.get(3)));

            //证件类别
            salerIdenInfoDto.setSalerCredentType(this.subDictcode(list.get(4), " "));

            //其他证件名称
            if(list.get(5) != null && list.get(5) != "") {
                salerIdenInfoDto.setSalerOtherCredentName(list.get(5));
            }

            //证件号码
            salerIdenInfoDto.setSalerCredentNo(list.get(6));

            //内部编号
            salerIdenInfoDto.setSalerInsNo(list.get(7));

            //所属区域
            salerIdenInfoDto.setSalerAreaType(this.subDictcode(list.get(8), " "));

            //所属总部或分支机构编码
            salerIdenInfoDto.setSalerOrgCode(list.get(9));

            //所属部门
            salerIdenInfoDto.setSalerDept(list.get(10));

            //职位
            salerIdenInfoDto.setSalerPosition(list.get(11));

            //学历
            salerIdenInfoDto.setSalerEduLevel(this.subDictcode(list.get(12), " "));

            //学位
            salerIdenInfoDto.setSalerDegree(this.subDictcode(list.get(13), " "));

            //首次参加工作时间
            salerIdenInfoDto.setSalerWorkStartYmd(list.get(14).replace("-", "").replace("/", ""));

            //首次从事金融工作
            salerIdenInfoDto.setSalerFinanceStartYmd(list.get(15).replace("-", "").replace("/", ""));

            //首次从事理财销售时间
            salerIdenInfoDto.setSalerStartYmd(list.get(16).replace("-", "").replace("/", ""));

            //专业技术职称
            salerIdenInfoDto.setSalerLevle(this.subDictcode(list.get(17), " "));

            //理财专业证书
            salerIdenInfoDto.setSalerCertificateType(this.subDictcodeMultiple(list.get(18), ","));

            //理财登记培训证书编号
            if(list.get(19) != null && list.get(19) != "") {
                salerIdenInfoDto.setSalerChbTrainNo(list.get(19));
            }

            //可销售产品风险等级
            salerIdenInfoDto.setSalerRiskLevel(this.subDictcodeMultiple(list.get(20), ","));

            //销售资质
            salerIdenInfoDto.setSalerQualification(list.get(21));

            //办公电话
            salerIdenInfoDto.setSalerTel(list.get(22));

            //移动电话
            salerIdenInfoDto.setSalerMobile(list.get(23));

            //电子邮箱
            salerIdenInfoDto.setSalerMail(list.get(24));

            //当前状态
            salerIdenInfoDto.setSalerStatus(this.subDictcode(list.get(25), " "));

            //备注
            if(list.get(26) != null && list.get(26) != "") {
                salerIdenInfoDto.setRemark(list.get(26));
            }

            //数据状态
            salerIdenInfoDto.setStatus("E");
            salerIdenInfoDto.setOrgCode(CommonUtil.getOrgCode());
            salerIdenInfoDto.setCreateDept(CommonUtil.getDeptCode());
            returnInfoList.add(salerIdenInfoDto);
        }
        return returnInfoList;
    }


    /**
     * 理财销售人员姓名联想框
     */
    public List<SuggestDto> getSalerName(Map<String, Object> map) {
        return chbSalerIdenInfoMapper.getSalerName(map);
    }

    /**
     * 总部或分支机构编码联想框
     */
    public List<SuggestDto> getSalerOrgCode(Map<String, Object> map) {
        return chbSalerIdenInfoMapper.getSalerOrgCode(map);
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

    public  String subDictcodeMultiple(String value, String sign) {
        StringBuffer sb= new StringBuffer();
        if (StringUtils.isNotBlank(value)) {
            //分割成数组
            String[] str = value.split(sign);
            for (int i = 0; i <str.length; i++) {
                //按空格截取
                String s = subDictcode(str[i], " ");
                sb.append(s).append(",");

            }
            //去掉最后一个字符
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
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

    public static boolean inclodeNine(String value){
        boolean status = false;
        if (!isEmpty(value)) {
            status = value.contains("99");
        }
        return status;
    }

    //校验可销售产品风险等级多选导入
    //校验理财专业证书多选导入
    public static boolean checkSalerType(String value,DictCode dictCode){
        boolean status = true;
        if (!isEmpty(value)) {
            //逗号分割
            String[] riskLevels = value.split(",");
            for (int i=0;i<riskLevels.length;i++) {
                String[] riskLevel = riskLevels[i].split(" ");
                if (riskLevel.length<2) {
                    status = false;
                    break;
                }
                String riskLevel1 = riskLevel[0];
                String riskLevel2 = riskLevel[1];
                //得到枚举值
                DictCodeListExt2 dict = DictCodeListExt2.from(dictCode, riskLevel1);
                //判断内容是否相等
                //\s 可以匹配空格、制表符、换页符等空白字符的其中任意一个
                if (!Objects.equals(dict.getCodeName().replaceAll("\\s*", ""),
                        riskLevel2.replaceAll("\\s*", ""))){
                    status = false;
                    break;
                }
            }
        }
        return status;
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

    /**
     * 月期格式校验
     *
     * @param
     * @return 返回boolean
     */
    private boolean checkMonthDate(String date) {
        boolean flag = true;
        if(!DateUtil.isDateStr(date, DateUtil.MONTH_OTHER_FORMAT,
                DateUtil.MONTH_DATE_FORMAT_EN)){
            flag = false;
        }else{
            date = date.replace("-", "").replace("/", "");
            //年
            int year = Integer.valueOf(date.substring(0, 4));
            //月
            int month = Integer.valueOf(date.substring(4));
            if(month < 1 || month > 12) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 导入销售人员登记编码之前检查数据 导入的excel是否有重复数据
     *
     * param fileName 导入的文件名名
     * @return 检查结果
     * @see
     * @since 1.0
     */
    public AjaxResult checkConfirmMsgForImportCodeData(List<List<String>> dataList,String fileName) {
        AjaxResult webResult = new AjaxResult();
        List<ValidMessage> errorList = new ArrayList<>();
        if (!fileName.contains("销售人员编码信息")) {
            errorList.add( new ValidMessage("导入模板不一致, 请重新选择文件!"));
            webResult.setErrorList(errorList);
            return webResult;
        }

        for (int i = 0; i < dataList.size(); i++ ) {
            for (int j = i+1; j < dataList.size(); j++ ) {
                if(StringUtils.equals(dataList.get(i).get(0), dataList.get(j).get(0))){
                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"内部编号与"+"第"+(j+2)+"内部编号不能重复");
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
     * 导入销售人员登记编码
     * <p>
     * 解析excel文件并进行验证
     * </p>
     * @return 导入结果
     */
    public List<ExcelValidDto> importCode(List<List<String>> dataList, List<List<String>> tempList, String userName, String orgCode, String deptCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<String>();
            headList.add("内部编号");
            headList.add("销售人员登记编码");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<SalerIdenInfoDto> entities = new ArrayList<>(dataList.size());
                List<ExcelValidDto> checkResult= checkCodeExcelSales(dataList, entities,userName,deptCode);
                if (checkResult.isEmpty()) {
                    entities = convertToCodeEntity(dataList, orgCode);
                    for(SalerIdenInfoDto dto : entities){
                        chbSalerIdenInfoMapper.updateSalerRegistNo(dto);
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
     * 导入销售人员登记编码check
     * @param dataList
     * @param entities
     * @param userName
     * @param deptCode
     * @return
     */
    private List<ExcelValidDto> checkCodeExcelSales(List<List<String>> dataList, List<SalerIdenInfoDto> entities, String userName, String deptCode) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            //是否正常标志
            boolean flag = true;
            List<String> list = dataList.get(i);
            List<String> message = new ArrayList<>();
            //内部编码
            if(list.get(0) == null || list.get(0).trim().equals("")){
                flag = false;
                message.add("内部编码不能为空");
            }
            //销售人员登记编码
            if(list.get(1) == null || list.get(1).trim().equals("")){
                flag = false;
                message.add("销售人员登记编码不能为空");
            }
            if (CollectionUtils.isNotEmpty(message)) {
                checkResult.add(new ExcelValidDto(list.get(0), String.valueOf(i+2),
                        StringUtil.joinString(message, GlobalConstant.SEMICOLON)));
            }
        }
        return checkResult;
    }

    /**
     * excel数据转为java对象 销售人员登记编码
     * <p> 将excel导入的数据，按行转为ProRegistCodeManage001Dto  FinProductValuation对象，同时验证各字段值</p>
     * @param dataList excel导入的数据
     * @return 验证信息
     */
    private List<SalerIdenInfoDto> convertToCodeEntity(List<List<String>> dataList, String orgCode) {
        // 返回的list定义
        List<SalerIdenInfoDto> returnInfoList = new ArrayList<SalerIdenInfoDto>();
        SalerIdenInfoDto salerIdenInfoDto = null;
        for (int i = 0; i < dataList.size(); i++ ) {
            List<String> list = dataList.get(i);
            salerIdenInfoDto = new SalerIdenInfoDto();
            //内部编码
            salerIdenInfoDto.setSalerInsNo(list.get(0));

            //销售人员登记编码
            salerIdenInfoDto.setSalerRegistNo(list.get(1));


            returnInfoList.add(salerIdenInfoDto);
        }
        return returnInfoList;
    }

    /**
     * 判断str1中包含str2的个数
     * @param str1
     * @param str2
     * @return counter
     */
    public static int ciShu(String str1,String str2){
        int counter = 0;
        for(int i = 0;i < str1.length() ;i++) {
            int index = str1.indexOf(str2, i);
            if (i == index) {
                counter++;
            }
        }
        return counter;
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

    public static List<SalerIdenInfoDto> formatByType(List<SalerIdenInfoDto> dataList) {
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(item -> {
                item.setSalerSex(getDictCodeAndName(DictCode.性别_中债, item.getSalerSex()));
                item.setSalerCredentType(getDictCodeAndName(DictCode.销售人员证件类型, item.getSalerCredentType()));
                item.setSalerAreaType(getDictCodeAndName(DictCode.销售区域_中债, item.getSalerAreaType()));
                item.setSalerEduLevel(getDictCodeAndName(DictCode.销售人员学历, item.getSalerEduLevel()));
                item.setSalerStatus(getDictCodeAndName(DictCode.销售人员当前状态, item.getSalerStatus()));
                item.setSalerDegree(getDictCodeAndName(DictCode.销售人员学位, item.getSalerDegree()));
                item.setSalerLevle(getDictCodeAndName(DictCode.销售人员专业技术职称, item.getSalerLevle()));
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
