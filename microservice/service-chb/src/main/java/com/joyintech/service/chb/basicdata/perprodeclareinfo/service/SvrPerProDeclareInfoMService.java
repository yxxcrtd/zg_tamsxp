package com.joyintech.service.chb.basicdata.perprodeclareinfo.service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.service.chb.basicdata.perprodeclareinfo.dao.SvrPerProDeclareInfoMapper;
import com.joyintech.tams.basicdata.perprodeclareinfo.dto.SvrPerProDeclareInfo001Dto;
import com.joyintech.tams.report.common.dto.ExcelValidDto;
import com.joyintech.tams.report.common.util.ExcelUtil;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * 产品申报信息登记维护 Service
 * @author yxj
 * @date 2022/03/07
 */
@Service("SvrPerProDeclareInfoMService")
public class SvrPerProDeclareInfoMService extends CommonExportService<SvrPerProDeclareInfo001Dto> {

    //
    @Autowired
    private SvrPerProDeclareInfoMapper svrPerProDeclareInfoMapper;

    /**
     * 分页查询
     * @param page
     * @param length
     * @param pageParam
     * @return
     */
    public Page<SvrPerProDeclareInfo001Dto> selectForPage(int page, int length, Map<String, Object> pageParam) {
        return super.queryForPage(page,length,() -> svrPerProDeclareInfoMapper.selectForPage(pageParam));
    }

    /**
     * 保存方法
     * @param dto
     */
    public void save(SvrPerProDeclareInfo001Dto dto) {
        Date sysDate = DateUtil.platDateFromCache();// 获取平台日期
        dto.setCreateTime(sysDate);
        dto.setUpdateTime(sysDate);
        dto.setCreateUser(CommonUtil.getUserName());
        dto.setUpdateUser(CommonUtil.getUserName());

        dto.setDeclareUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase() + (new Random().nextInt(8998)+1000) + "");
        dto.setStatus("E");

        int num = svrPerProDeclareInfoMapper.insertSvrPerProDeclareInfo(dto);
    }

    /**
     * 修改
     * @param dto
     */
    public void update(SvrPerProDeclareInfo001Dto dto) {
        dto.setStatus("E");
        int num = svrPerProDeclareInfoMapper.updateSvrPerProDeclareInfo(dto);
    }

    /**
     * 获取详情
     * @param param
     * @return
     */
    public SvrPerProDeclareInfo001Dto selectDetail(Map<String, Object> param) {
        return svrPerProDeclareInfoMapper.selectDetail(param);
    }

    /**
     * 删除
     * @param dto
     */
    public void deleteByPrimaryKey(SvrPerProDeclareInfo001Dto dto){
        svrPerProDeclareInfoMapper.deleteByPrimaryKey(dto.getDeclareUuid());
    }

    /**
     *
     * @param param
     * @return
     */
    public List<SuggestDto> queryProdInfo(Map<String, Object> param) {
        return svrPerProDeclareInfoMapper.queryProdInfo(param);
    }

    /**
     * 获取所有信息
     * @param param
     * @return
     */
    public List<SvrPerProDeclareInfo001Dto> queryAllList(Map<String, Object> param) {
        return svrPerProDeclareInfoMapper.selectForPage(param);
    }

    /**
     * 批量删除
     * @param param
     */
    public void deleteList(Map<String, Object> param) {
        String keys = param.get("datas").toString();
        List<String> split = StringUtil.splitToList(GlobalConstant.COMMA, keys);
        param.put("deleteKeys",split);
        svrPerProDeclareInfoMapper.deleteList(param);
    }

    /**
     * 导入数据前检查
     * @param dataList
     * @return
     */
    public AjaxResult checkConfirmMsgForImportData(List<List<String>> dataList) {
        AjaxResult webResult = new AjaxResult();
        List<ValidMessage> errorList = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++ ) {
            for (int j = i+1; j < dataList.size(); j++ ) {
//                if(StringUtils.equals(dataList.get(i).get(0), dataList.get(j).get(0)) &&
//                        StringUtils.equals(dataList.get(i).get(4), dataList.get(j).get(4))){
//                    ValidMessage validMessage = new ValidMessage("第"+(i+2)+"与"+"第"+(j+2)+"相同");
//                    errorList.add(validMessage);
//                }
            }
        }
        if(errorList.size() == 0){
            webResult.setData("noInfoShowOut");
        }else{
            webResult.setErrorList(errorList);
        }
        return webResult;

    }

    /**
     * 导入数据
     * @param dataList
     * @param tempList
     * @param userName
     * @param deptCode
     * @return
     */
    public List<ExcelValidDto> importData(List<List<String>> dataList, List<List<String>> tempList, String userName, String deptCode) {
        if (!StringUtil.isEmptyList(dataList)) {
            List<String> headList = new ArrayList<String>();
            headList.add("产品名称");
            headList.add("行内识别码");
            headList.add("产品品牌");
            headList.add("产品特殊属性");
            headList.add("产品期次");
            headList.add("发行机构代码");
            headList.add("产品审批人姓名");
            headList.add("产品审批人身份证号");
            headList.add("产品设计人姓名");
            headList.add("产品设计人身份证号");
            headList.add("投资经理姓名");
            headList.add("投资经理身份证号");
            headList.add("业务联络人姓名");
            headList.add("业务联络人座机");
            headList.add("业务联络人手机");
            headList.add("业务联络人邮箱");
            headList.add("募集方式");
            headList.add("收益类型");
            headList.add("新老产品标记");
            headList.add("产品期限");
            headList.add("是否金融同业专属");
            headList.add("资金投向地区");
            headList.add("产品投资国家或地区");
            headList.add("理财业务服务模式");
            headList.add("产品运作模式");
            headList.add("是否设置最短持有期限");
            headList.add("最短持有期限（天）");
            headList.add("最短持有期后是否自由赎回");
            headList.add("是否现金管理类");
            headList.add("产品资产配置方式");
            headList.add("产品管理模式");
            headList.add("实际管理人名称");
            headList.add("产品定价方式");
            headList.add("产品投资性质");
            headList.add("合作模式");
            headList.add("合作机构名称");
            headList.add("产品销售区域");
            headList.add("募集币种");
            headList.add("兑付本金币种");
            headList.add("兑付收益币种");
            headList.add("起点销售金额");
            headList.add("计划募集金额");
            headList.add("募集起始日(从)");
            headList.add("募集起始日(到)");
            headList.add("投资本金到账日");
            headList.add("投资收益到账日");
            headList.add("销售手续费率%");
            headList.add("投资管理费率%");
            headList.add("境内托管机构名称");
            headList.add("境内托管机构代码");
            headList.add("境外托管机构国别");
            headList.add("境外托管机构名称");
            headList.add("托管费率%");
            headList.add("产品风险等级");
            headList.add("投资者风险偏好");
            headList.add("发行机构提前终止权标识");
            headList.add("投资者赎回权标识");
            headList.add("产品增信标识");
            headList.add("产品增信机构类型");
            headList.add("产品增信形式");
            headList.add("投资资产种类及比例");
            headList.add("业绩比较基准");
            headList.add("数据状态");
            headList.add("备注");
            List<ExcelValidDto> tempCheckResult = ExcelUtil.checkExcelTemp(tempList, headList);
            if (!StringUtil.isEmptyList(tempCheckResult)) {
                return tempCheckResult;
            }
            if (null != dataList) {
                List<SvrPerProDeclareInfo001Dto> entities = new ArrayList<>(dataList.size());
                Map <String,String> map= new HashMap<String, String>();
                List<ExcelValidDto> checkResult= checkData(dataList, entities,userName,deptCode);
                if (checkResult.isEmpty()) {
                    entities = convertToEntity(dataList, deptCode);
                    for(SvrPerProDeclareInfo001Dto dto : entities){
                        svrPerProDeclareInfoMapper.insertSvrPerProDeclareInfo(dto);
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
     * 检查数据
     * @param dataList
     * @param entities
     * @param userName
     * @param deptCode
     * @return
     */
    private List<ExcelValidDto> checkData(List<List<String>> dataList, List<SvrPerProDeclareInfo001Dto> entities, String userName, String deptCode) {
        List<ExcelValidDto> checkResult = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            // 是否正常标志
            boolean flag = true;
            List<String> list = dataList.get(i);

            Map<String,Object> param = new HashMap<String,Object>();
            List<String> message = new ArrayList<>();
            // 产品名称0
            if (list.get(0) == null || list.get(0).trim().equals("")){
                flag = false;
                message.add("产品名称不能为空");
            }
            // 行内识别码1
            if (list.get(1) == null || list.get(1).trim().equals("")){
                flag = false;
                message.add("行内标识码不能为空");
            }
            // 产品品牌2

            // 产品特殊属性3

            // 产品期次4
            if (list.get(4) == null || list.get(4).trim().equals("")){
                flag = false;
                message.add("产品期次不能为空");
            } else {
                if(!checkFigure(list.get(4))){
                    flag = false;
                    message.add("产品期次不是数字");
                }
                else{
                    BigDecimal amt=new BigDecimal(list.get(4));
                    BigDecimal Zero = BigDecimal.ZERO;
                    if(amt.compareTo(Zero) == -1 || amt.compareTo(Zero) == 0){
                        flag = false;
                        message.add("产品期次要大于0");
                    }
                }
            }

            // 发行机构代码5

            // 产品审批人姓名6
            if (list.get(6) == null || list.get(6).trim().equals("")){
                flag = false;
                message.add("产品审批人姓名不能为空");
            }
            // 产品审批人身份证号7
            if (list.get(7) == null || list.get(7).trim().equals("")){
                flag = false;
                message.add("产品审批人身份证号不能为空");
            } else if (list.get(7) != null || !list.get(7).trim().equals("")){
                String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
                if (!list.get(7).matches(regex)){
                    flag = false;
                    message.add("产品审批人身份证号格式不对");
                }
            }
            // 产品设计人姓名8
            if (list.get(8) == null || list.get(8).trim().equals("")){
                flag = false;
                message.add("产品设计人姓名不能为空");
            }
            // 产品设计人身份证号9
            if (list.get(9) == null || list.get(9).trim().equals("")){
                flag = false;
                message.add("产品设计人身份证号不能为空");
            } else if (list.get(9) != null || !list.get(9).trim().equals("")){
                String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
                if (!list.get(9).matches(regex)){
                    flag = false;
                    message.add("产品设计人身份证号格式不对");
                }
            }
            // 投资经理姓名10
            if (list.get(10) == null || list.get(10).trim().equals("")){
                flag = false;
                message.add("投资经理姓名不能为空");
            }
            // 投资经理身份证号11
            if (list.get(11) == null || list.get(11).trim().equals("")){
                flag = false;
                message.add("投资经理身份证号不能为空");
            } else if (list.get(11) != null || !list.get(11).trim().equals("")){
                String regex = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
                if (!list.get(11).matches(regex)){
                    flag = false;
                    message.add("投资经理身份证号格式不对");
                }
            }
            // 业务联络人姓名12
            if (list.get(12) == null || list.get(12).trim().equals("")){
                flag = false;
                message.add("业务联络人姓名不能为空");
            }
            // 业务联络人座机13
            if (list.get(13) == null || list.get(13).trim().equals("")){
                flag = false;
                message.add("业务联络人座机不能为空");
            } else if (list.get(13) != null || !list.get(13).trim().equals("")){
                String regex = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";
                if (!list.get(13).matches(regex)){
                    flag = false;
                    message.add("业务联络人座机格式不对");
                }
            }
            // 业务联络人手机14
            if (list.get(14) == null || list.get(14).trim().equals("")){
                flag = false;
                message.add("业务联络人手机不能为空");
            }
            // 业务联络人邮箱15
            if (list.get(15) == null || list.get(15).trim().equals("")){
                flag = false;
                message.add("业务联络人邮箱不能为空");
            } else if (list.get(15) != null || !list.get(15).trim().equals("")){
                String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
                if (!list.get(15).matches(regex)){
                    flag = false;
                    message.add("业务联络人邮箱格式不对");
                }
            }
            // 募集方式16
            if (list.get(16) == null || list.get(16).trim().equals("")){
                flag = false;
                message.add("募集方式不能为空");
            }
            // 收益类型17
            if (list.get(17) == null || list.get(17).trim().equals("")){
                flag = false;
                message.add("收益类型不能为空");
            }
            // 新老产品标记18
            if (CacheUtil.getSysParam("SVR_IS_SUBSIDIARY") != null && !CacheUtil.getSysParam("SVR_IS_SUBSIDIARY").equals("")) {
                if (!CacheUtil.getSysParam("SVR_IS_SUBSIDIARY").equals("01")) {
                    if (list.get(18) == null || list.get(18).trim().equals("")) {
                        flag = false;
                        message.add("新老产品标记不能为空");
                    }
                }
            }
            // 产品期限19
            if (list.get(19) == null || list.get(19).trim().equals("")){
                flag = false;
                message.add("产品期限不能为空");
            }
            // 是否金融同业专属20
            if (list.get(20) == null || list.get(20).trim().equals("")){
                flag = false;
                message.add("是否金融同业专属不能为空");
            }
            // 资金投向地区21
            if (list.get(21) == null || list.get(21).trim().equals("")){
                flag = false;
                message.add("资金投向地区不能为空");
            }
            // 产品投资国家或地区22
            // 投向地区不为空
            if (!(list.get(21) == null || list.get(21).trim().equals(""))) {
                // 境内和境外
                if (list.get(21).trim().equals("03")){
                    if (list.get(22) == null || list.get(22).trim().equals("")){
                        flag = false;
                        message.add("当资金投向地区要素选择“境内和境外”时,产品投资国家或地区不能为空");
                    }
                } else if (list.get(21).trim().equals("02")) {
                    // 境外
                    if (list.get(22) == null || list.get(22).trim().equals("")) {
                        flag = false;
                        message.add("当资金投向地区要素选择“境外”时，产品投资国家或地区不能为空");
                    } else if (list.get(22).trim().toString().contains("CHN")){
                        flag = false;
                        message.add("当资金投向地区要素选择“境外”时，产品投资国家或地区不能为CHN");
                    }
                } else if (list.get(21).trim().equals("01")){
                    // 境内
                    // 必须为空
                    if (!(list.get(22) == null || list.get(22).trim().equals(""))){
                        flag = false;
                        message.add("当资金投向地区要素选择“境内”时，产品投资国家或地区必须为空");
                    }

                }
            }
            // 理财业务服务模式23
            // 投向地区不为空
            if (!(list.get(21) == null || list.get(21).trim().equals(""))) {
                if (list.get(21).trim().equals("02")){
                    if (!(list.get(23) == null || list.get(23).trim().equals(""))){
                        flag = false;
                        message.add("资金投向地区为境外时，理财业务服务模式必须为空");
                    } else if (list.get(23) == null || list.get(23).trim().equals("")){
                        flag = false;
                        message.add("理财业务服务模式不能为空");
                    }
                }
            }
            // 产品运作模式24
            if (list.get(24) == null || list.get(24).trim().equals("")){
                flag = false;
                message.add("产品运作模式不能为空");
            }
            // 是否设计最短持有期限25
            //
            if (!(list.get(24) == null || list.get(24).trim().equals(""))){
                if (list.get(24).trim().equals("03") || list.get(24).trim().equals("04")) {
                    if (list.get(25) == null || list.get(25).trim().equals("")) {
                        flag = false;
                        message.add("产品运作模式选择开放式净值型和开放式非净值型的产品，是否设计最短持有期限必填");
                    }
                } else if (list.get(24).trim().equals("01") || list.get(24).trim().equals("02")){
                    if (list.get(25) != null || !list.get(25).trim().equals("")) {
                        flag = false;
                        message.add("产品运作模式选择封闭式净值型和封闭式非净值型的产品，是否设计最短持有期限必须为空");
                    }
                }
            }
            // 最持有期限（天）26
            if (!(list.get(25) == null || list.get(25).trim().equals(""))){
                if (list.get(25).trim().equals("01")){
                    if (list.get(26) == null || list.get(26).trim().equals("")){
                        flag = false;
                        message.add("是否设置最短持有期限的选项为是时,可编辑，否则不可填");
                    }
                }
            }
            // 最短持有期后是否自由赎回27
            if (!(list.get(25) == null || list.get(25).trim().equals(""))){
                if (list.get(25).trim().equals("01")){
                    if (list.get(27) == null || list.get(27).trim().equals("")){
                        flag = false;
                        message.add("是否设置最短持有期限的选项为是时,可编辑，否则不可填");
                    }
                }
            }
            // 是否现金管理类28
            if (!(list.get(24) == null || list.get(24).trim().equals(""))){
                if (list.get(24).trim().equals("03") || list.get(24).trim().equals("04")) {
                    if (list.get(28) == null || list.get(28).trim().equals("")) {
                        flag = false;
                        message.add("产品运作模式选择开放式净值型和开放式非净值型的产品，是否现金管理类必填");
                    }
                } else if (list.get(24).trim().equals("01") || list.get(24).trim().equals("02")){
                    if (list.get(28) != null || !list.get(28).trim().equals("")) {
                        flag = false;
                        message.add("产品运作模式选择封闭式净值型和封闭式非净值型的产品，是否现金管理类必须为空");
                    }
                }
            }
            // 产品资产配置方式29
            if (list.get(29) == null || list.get(29).trim().equals("")){
                flag = false;
                message.add("产品资产配置方式不能为空");
            }
            // 产品管理模式30
            if (list.get(30) == null || list.get(30).trim().equals("")){
                flag = false;
                message.add("产品管理模式不能为空");
            }

            // 实际管理人姓名31
            if (!(list.get(30) == null || list.get(30).trim().equals(""))){
                if (list.get(30).trim().equals("02") || list.get(30).trim().equals("03")){
                    if (list.get(31) == null || list.get(31).trim().equals("")){
                        flag = false;
                        message.add("当产品管理模式要素选择资产管理公司或其他机构为实际管理人时，实际管理人姓名必填");
                    }
                } else if (list.get(30).trim().equals("01") || list.get(30).trim().equals("04")){
                    if (!(list.get(31) == null || list.get(31).trim().equals(""))){
                        flag = false;
                        message.add("当产品管理模式要素选择银行为实际管理人或特殊目的载体（SPV）时，实际管理人姓名必须为空");
                    }
                }
            }
            // 产品定价方式32
            if (list.get(32) == null || list.get(32).trim().equals("")){
                flag = false;
                message.add("产品定价方式不能为空");
            }
            // 产品投资性质33
            if (list.get(33) == null || list.get(33).trim().equals("")){
                flag = false;
                message.add("产品投资性质不能为空");
            }
            // 合作模式34
            if (list.get(34) == null || list.get(34).trim().equals("")){
                flag = false;
                message.add("合作模式不能为空");
            }
            // 合作机构名称35
            if (!(list.get(34) == null || list.get(34).trim().equals(""))){
                if (!list.get(34).trim().equals("01")){
                    if (list.get(35) == null || list.get(35).trim().equals("")){
                        flag = false;
                        message.add("当合作模式要素选择除独立运作以外的其他选项时，合作机构名称必填");
                    }
                } else {
                    if (!(list.get(35) == null || list.get(35).trim().equals(""))){
                        flag = false;
                        message.add("当合作模式要素选择除独立运作以外的其他选项时，合作机构名称必须为空");
                    }
                }
            }
            // 产品销售区域36
            if (list.get(36) == null || list.get(36).trim().equals("")){
                flag = false;
                message.add("产品销售区域不能为空");
            }
            // 募集币种37
            if (list.get(37) == null || list.get(37).trim().equals("")){
                flag = false;
                message.add("募集币种不能为空");
            }
            // 兑付本金币种38
            if (list.get(38) == null || list.get(38).trim().equals("")){
                flag = false;
                message.add("兑付本金币种不能为空");
            }
            // 兑付收益币种39
            if (list.get(39) == null || list.get(39).trim().equals("")){
                flag = false;
                message.add("兑付本金币种不能为空");
            }
            // 起点销售金额40
            if (list.get(40) == null || list.get(40).trim().equals("")){
                flag = false;
                message.add("起点销售金额不能为空");
            } else {
                if(!checkFigure(list.get(40))){
                    flag = false;
                    message.add("起点销售金额不是数字");
                }
                else{
                    BigDecimal amt=new BigDecimal(list.get(40));
                    BigDecimal Zero = BigDecimal.ZERO;
                    if(amt.compareTo(Zero) == -1 || amt.compareTo(Zero) == 0){
                        flag = false;
                        message.add("起点销售金额要大于0");
                    }
                }
            }
            // 计划募集金额41
            if (list.get(41) == null || list.get(41).trim().equals("")){
                flag = false;
                message.add("计划募集金额不能为空");
            } else {
                if(!checkFigure(list.get(41))){
                    flag = false;
                    message.add("计划募集金额不是数字");
                }
                else{
                    BigDecimal amt=new BigDecimal(list.get(41));
                    BigDecimal Zero = BigDecimal.ZERO;
                    if(amt.compareTo(Zero) == -1 || amt.compareTo(Zero) == 0){
                        flag = false;
                        message.add("计划募集金额要大于0");
                    }
                }
            }
            // 募集起始日（从）42
            if(list.get(42) == null || list.get(42).trim().equals("")){
                flag = false;
                message.add("募集起始日（从）不能为空");
            }else{
                if (!checkDate(list.get(42))) {
                    flag = false;
                    message.add("募集起始日（从）格式不正确");
                }
            }
            // 募集起始日（到）43
            if(list.get(43) == null || list.get(43).trim().equals("")){
                flag = false;
                message.add("募集起始日（到）不能为空");
            }else{
                if (!checkDate(list.get(43))) {
                    flag = false;
                    message.add("募集起始日（到）格式不正确");
                } else {
                    if (DateUtil.compareDate(list.get(42).trim(),list.get(43).trim()) > 0){
                        flag = false;
                        message.add("募集起始日（到）应大于等于募集起始日（从）");
                    }
                }
                // 募集起始日（到） 大于 募集起始日（从）
            }
            // 投资本金到账日 44
            if (list.get(44) == null || list.get(44).trim().equals("")){
                flag = false;
                message.add("投资本金到账日不能为空");
            }
            // 投资收益到账日 45
            if (list.get(45) == null || list.get(45).trim().equals("")){
                flag = false;
                message.add("投资收益到账日不能为空");
            }
            // 销售手续费率%46
            if (list.get(46) == null || list.get(46).trim().equals("")){
                flag = false;
                message.add("销售手续费率%不能为空");
            } else {
                if(!checkFigure(list.get(46))){
                    flag = false;
                    message.add("销售手续费率%不是数字");
                }
                else{
                    BigDecimal amt=new BigDecimal(list.get(46));
                    BigDecimal Zero = BigDecimal.ZERO;
                    if(amt.compareTo(Zero) == -1 || amt.compareTo(Zero) == 0){
                        flag = false;
                        message.add("销售手续费率%要大于0");
                    }
                }
            }
            // 投资管理费率%47
            if (list.get(47) == null || list.get(47).trim().equals("")){
                flag = false;
                message.add("投资管理费率%不能为空");
            } else {
                if(!checkFigure(list.get(47))){
                    flag = false;
                    message.add("投资管理费率%不是数字");
                }
                else{
                    BigDecimal amt=new BigDecimal(list.get(47));
                    BigDecimal Zero = BigDecimal.ZERO;
                    if(amt.compareTo(Zero) == -1 || amt.compareTo(Zero) == 0){
                        flag = false;
                        message.add("投资管理费率%要大于0");
                    }
                }
            }
            // 境内托管机构名称48
            if (!(list.get(21) == null || list.get(21).trim().equals(""))) {
                // 非单境外
                if (!(list.get(21).trim().equals("02"))) {
                    if (list.get(48) == null || list.get(48).trim().equals("")) {
                        flag = false;
                        message.add("当资金投向地区要素选择境内或境内和境外时，境内托管机构名称不能为空");
                    }
                } else {
                    if (!(list.get(48) == null || list.get(48).trim().equals(""))) {
                        flag = false;
                        message.add("当资金投向地区要素选择境外时，境内托管机构名称必须为空");
                    }
                }
            }
            // 境内托管机构代码49
            if (!(list.get(21) == null || list.get(21).trim().equals(""))) {
                // 境外
                if (list.get(21).trim().equals("02")) {
                    if (!(list.get(49) == null || list.get(49).trim().equals(""))){
                        flag = false;
                        message.add("当资金投向地区要素选择境外时，境内托管机构代码必须为空");
                    }

                }
            }
            // 境外托管机构国别50
            if (!(list.get(21) == null || list.get(21).trim().equals(""))) {
                // 境外
                if (list.get(21).trim().equals("02")) {
                    if (list.get(50) == null || list.get(50).trim().equals("")) {
                        flag = false;
                        message.add("当资金投向地区要素选择境外时，须填写境外托管机构国别且不得选择CHN");
                    } else {
                        if (list.get(50).trim().equals("CHN")) {
                            flag = false;
                            message.add("当资金投向地区要素选择境外时，须填写境外托管机构国别且不得选择CHN");
                        }
                    }

                } else if (list.get(21).trim().equals("01")){
                    if (!(list.get(50) == null || list.get(50).trim().equals(""))){
                        flag = false;
                        message.add("当资金投向地区要素选择境内时，境外托管机构国别必须为空");
                    }
                }
            }
            // 境外托管机构名称51
            if (!(list.get(21) == null || list.get(21).trim().equals(""))) {
                // 境内
                if (list.get(21).trim().equals("01")) {
                    if (!(list.get(51) == null || list.get(51).trim().equals(""))){
                        flag = false;
                        message.add("当资金投向地区要素选择境内时，境外托管机构名称必须为空");
                    }
                } else {
                    if (list.get(51) == null || list.get(51).trim().equals("")){
                        flag = false;
                        message.add("当资金投向地区要素选择境外或境内和境外时，境外托管机构名称必填");
                    }

                }
            }
            // 托管费率%52
            if (list.get(52) == null || list.get(52).trim().equals("")){
                flag = false;
                message.add("托管费率%不能为空");
            } else {
                if(!checkFigure(list.get(52))){
                    flag = false;
                    message.add("托管费率%不是数字");
                }
                else{
                    BigDecimal amt=new BigDecimal(list.get(52));
                    BigDecimal Zero = BigDecimal.ZERO;
                    if(amt.compareTo(Zero) == -1 || amt.compareTo(Zero) == 0){
                        flag = false;
                        message.add("托管费率%要大于0");
                    }
                }
            }
            // 产品风险等级53
            if (list.get(53) == null || list.get(53).trim().equals("")){
                flag = false;
                message.add("产品风险等级不能为空");
            }
            // 投资者风险偏好54
            if (list.get(54) == null || list.get(54).trim().equals("")){
                flag = false;
                message.add("投资者风险偏好不能为空");
                // 条件限制
            } else {
                if (!(list.get(53) == null || list.get(53).trim().equals(""))){
                    // 保守型
                    if (list.get(54).trim().toString().contains("01")){
                        if (!list.get(53).trim().equals("01")){
                            flag = false;
                            message.add("投资者风险偏好 01保守型投资者只能投资风险等级为一级的产品");
                        }
                    } else if (list.get(54).trim().toString().contains("02")){
                        // 稳健型
                        if (!(list.get(53).trim().equals("01") || list.get(53).trim().equals("02"))){
                            flag = false;
                            message.add("投资者风险偏好 02稳健型投资者只能投资风险等级为一级或二级的产品");
                        }
                    } else if (list.get(54).trim().toString().contains("03")){
                        // 平衡型
                        if (!(list.get(53).trim().equals("01") || list.get(53).trim().equals("02")
                                || list.get(53).trim().equals("03"))) {
                            flag = false;
                            message.add("投资者风险偏好 03平衡型投资者只能投资风险等级为一级、二级或三级的产品");
                        }
                    } else if (list.get(54).trim().toString().contains("04")){
                        // 投资者
                        if (!(list.get(53).trim().equals("01") || list.get(53).trim().equals("02")
                                || list.get(53).trim().equals("03") || list.get(53).trim().equals("04"))) {
                            flag = false;
                            message.add("投资者风险偏好 04成长型投资者只能投资风险等级为一级、二级、三级或四级的产品");
                        }
                    } else if (list.get(54).trim().toString().contains("05")){
                        ;
                    }
                }
            }

            // 发行机构提前终止标识55
            if (list.get(55) == null || list.get(55).trim().equals("")){
                flag = false;
                message.add("发行机构提前终止标识不能为空");
            }
            // 投资者赎回权标识56
            if (list.get(56) == null || list.get(56).trim().equals("")){
                flag = false;
                message.add("投资者赎回权标识不能为空");
            }
            // 产品增信标识57
            if (list.get(57) == null || list.get(57).trim().equals("")){
                flag = false;
                message.add("产品增信标识不能为空");
            }
            // 产品增信机构类型58
            if (!(list.get(57) == null || list.get(57).trim().equals(""))){
                if (list.get(57).trim().equals("01")){
                    if (list.get(58) == null || list.get(58).trim().equals("")){
                        flag = false;
                        message.add("当产品增信标识选择有时，产品增信机构类型必填");
                    }
                } else {
                    if (!(list.get(58) == null || list.get(58).trim().equals(""))){
                        flag = false;
                        message.add("当产品增信标识选择有时，产品增信机构类型必须为空");
                    }
                }
            }
            // 产品增信形式59
            if (!(list.get(57) == null || list.get(57).trim().equals(""))){
                if (list.get(57).trim().equals("01")){
                    if (list.get(59) == null || list.get(59).trim().equals("")){
                        flag = false;
                        message.add("当产品增信标识选择有时，产品增信机构类型必填");
                    }
                } else {
                    if (!(list.get(59) == null || list.get(59).trim().equals(""))){
                        flag = false;
                        message.add("当产品增信标识选择有时，产品增信机构类型必须为空");
                    }
                }
            }
            // 投资资产种类及比例60
            if (list.get(60) == null || list.get(60).trim().equals("")){
                flag = false;
                message.add("投资资产种类及比例不能为空");
            } if (list.get(60) != null || !list.get(60).trim().equals("")) {
                String regex = "(((\\d{1,3}(.\\d{1,2})?%)|(\\d{1,3}(.\\d{1,2})?%-\\d{1,3}(.\\d{1,2})?%)):[\\u4e00-\\u9fa5_a-zA-Z0-9()_]+;)*(((\\d{1,3}(.\\d{1,2})?%)|(\\d{1,3}(.\\d{1,2})?%-\\d{1,3}(.\\d{1,2})?%)):[\\u4e00-\\u9fa5_a-zA-Z0-9()_]+)";
                if (!list.get(60).matches(regex)) {
                    flag = false;
                    message.add("投资资产种类及比例格式不对");
                }
            }
            // 业绩比较基准61
            if (list.get(61) == null || list.get(61).trim().equals("")){
                flag = false;
                message.add("业绩比较基准不能为空");
            } else {
                if(!checkFigure(list.get(61))){
                    flag = false;
                    message.add("业绩比较基准不是数字");
                }
                else{
                    BigDecimal amt=new BigDecimal(list.get(61));
                    BigDecimal Zero = BigDecimal.ZERO;
                    if(amt.compareTo(Zero) == -1 || amt.compareTo(Zero) == 0){
                        flag = false;
                        message.add("业绩比较基准要大于0");
                    }
                }
            }
            // 数据状态62
            // 备注63
            // 最短持有期后是否自由赎回27
            if (!(list.get(27) == null || list.get(27).trim().equals(""))) {
                if (list.get(27).trim().equals("99")) {
                    if (list.get(63) == null || list.get(63).trim().equals("")) {
                        flag = false;
                        message.add("当是否设置最短持有期限选择“99 其他”时,备注必填");
                    }
                }
            }
            // 境内托管机构名称48
            if (!(list.get(48) == null || list.get(48).trim().equals(""))) {
                if (list.get(48).trim().equals("99")) {
                    if (list.get(63) == null || list.get(63).trim().equals("")) {
                        flag = false;
                        message.add("当境内托管机构名称选择“99 其他”时,备注必填");
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
     *  数据装 DTO
     * @param dataList
     * @param deptCode
     * @return
     */
    private List<SvrPerProDeclareInfo001Dto> convertToEntity(List<List<String>> dataList, String deptCode) {
        Date sysDate = DateUtil.platDateFromCache();
        // 返回的list定义
        List<SvrPerProDeclareInfo001Dto> returnInfoList = new ArrayList<SvrPerProDeclareInfo001Dto>();
        SvrPerProDeclareInfo001Dto dto = null;
        for (int i = 0; i < dataList.size(); i++) {
            List<String> list = dataList.get(i);
            dto = new SvrPerProDeclareInfo001Dto();
            // 产品名称
            dto.setProdName(list.get(0));
            // 行内识别码
            dto.setProdInsId(list.get(1));
            // 产品品牌
            dto.setProdBrand(list.get(2));
            // 产品特殊属性
            dto.setPrdSpecProperty(list.get(3));
            // 产品期次
            dto.setProdStage(new BigDecimal(list.get(4)));
            // 发行机构代码
            dto.setIssuePartyId(CommonUtil.selectBankCode());
            // 产品审批人姓名
            dto.setApproverName(list.get(6));
            // 产品审批人身份证号
            dto.setApproverId(list.get(7));
            // 产品设计人姓名
            dto.setDesignerName(list.get(8));
            // 产品设计人身份证号
            dto.setDesignerId(list.get(9));
            // 投资经理姓名
            dto.setManagerName(list.get(10));
            // 投资经理身份证号
            dto.setManagerId(list.get(11));
            // 业务联络人姓名
            dto.setLiaisonName(list.get(12));
            // 业务联络人座机
            dto.setLiaisonTel(list.get(13));
            // 业务联络人手机
            dto.setLiaisonMobile(list.get(14));
            // 业务联络人邮箱
            dto.setLiaisonEmail(list.get(15));
            // 募集方式
            dto.setRaiseType(list.get(16));
            // 收益类型
            dto.setProfitType(list.get(17));
            // 新老产品标记
            String isFhxgcp = list.get(18);
            dto.setIsFhxgcp(isFhxgcp == null ? null : isFhxgcp);
            // 产品期限
            dto.setTermtype(list.get(19));
            // 是否金融同业专属
            dto.setExcFinSector(list.get(20));
            // 资金投向地区
            dto.setInvestArae(list.get(21));
            // 产品投资国家或地区
            String investCountry = list.get(22);
            dto.setInvestCountry(investCountry == null ? null : investCountry);
            // 理财业务服务模式
            String finanSerModel = list.get(23);
            dto.setFinanSerModel(finanSerModel == null ? null : finanSerModel);
            // 产品运作模式
            dto.setOperateMode(list.get(24));
            // 是否设计最短持有期限
            String isShortTerm = list.get(25);
            dto.setIsShortTerm(isShortTerm == null ? null : isShortTerm);
            // 最短持有期限（天）
            String shortTerm = list.get(26);
            dto.setShortTerm(shortTerm == null ? null : new BigDecimal(shortTerm));
            // 最短持有期后是否自由赎回
            String isFreeRedem = list.get(27);
            dto.setIsFreeRedem(isFreeRedem == null ? null : isFreeRedem);
            // 是否现金管理类
            dto.setCashManaCate(list.get(28));
            // 产品资产配置方式
            dto.setCmmode(list.get(29));
            // 产品管理模式
            dto.setPmmode(list.get(30));
            // 实际管理人名称
            String mname = list.get(31);
            dto.setMname(mname == null ? null : mname);
            // 产品定价方式
            dto.setPricedMode(list.get(32));
            // 产品投资性质
            dto.setIvstorientation(list.get(33));
            // 合作模式
            dto.setContMode(list.get(34));
            // 合作机构名称
            String cooperationName = list.get(35);
            dto.setCooperationName(cooperationName == null ? null : cooperationName);
            // 产品销售区域
            dto.setSaleArea(list.get(36));
            // 募集币种
            dto.setRaiseCcy(list.get(37));
            // 兑付本金币种
            dto.setCashPrinCcy(list.get(38));
            // 兑付收益币种
            dto.setCashProfCcy(list.get(39));
            // 起点销售金额
            String saleAmtStr = list.get(40);
            dto.setSaleAmtStr(new BigDecimal(saleAmtStr));
            // 计划募集金额
            String raiseAmtPlan = list.get(41);
            dto.setRaiseAmtPlan(new BigDecimal(raiseAmtPlan));
            // 募集起始日（从）
            String raisePstrdate = DateUtil.formatDate(list.get(42));
            try {
                dto.setRaisePstrdate(DateUtil.parseDate(raisePstrdate,DateUtil.DEFAULT_ANOTHER_FORMAT));
            } catch (ParseException e) {
                e.printStackTrace();
                dto.setRaisePstrdate(DateUtil.platDateFromCache());
            }
            // 募集起始日（到）
            String raisePenddate = list.get(43);
            try {
                dto.setRaisePenddate(DateUtil.parseDate(raisePenddate,DateUtil.DEFAULT_ANOTHER_FORMAT));
            } catch (ParseException e) {
                e.printStackTrace();
                dto.setRaisePenddate(DateUtil.platDateFromCache());
            }
            // 投资本金到账日
            dto.setInvestPrinDate(list.get(44));
            // 投资收益到账日
            dto.setInvestProfDate(list.get(45));
            // 销售手续费率%
            dto.setSaleFeeRate(new BigDecimal(list.get(46)));
            // 投资管理费率%
            dto.setInvestFeeRate(new BigDecimal(list.get(47)));
            // 境内托管机构名称
            String inDepName = list.get(48);
            dto.setInDepName(inDepName == null ? null : inDepName);
            // 境内托管机构代码
            String inDepCode = list.get(49);
            dto.setInDepCode(inDepCode == null ? null : inDepCode);
            // 境外托管机构国别
            String outDepCtry = list.get(50);
            dto.setOutDepCtry(outDepCtry == null ? null : outDepCtry);
            // 境外托管机构名称
            String outDepName = list.get(51);
            dto.setOutDepName(outDepName == null ? null :outDepName);
            // 托管费率%
            dto.setTruFeeRate(new BigDecimal(list.get(52)));
            // 产品风险等级
            dto.setRiskLevel(list.get(53));
            // 投资者风险偏好
            dto.setRiskType(list.get(54));
            // 发行机构提前终止权标识
            dto.setTermRightFlag(list.get(55));
            // 投资者赎回权标识
            dto.setCusRedeemFlag(list.get(56));
            // 产品增信标识
            dto.setCreditFlag(list.get(57));
            // 产品增信机构类型
            String creditType = list.get(58);
            dto.setCreditType(creditType == null ? null : creditType);
            // 产品增信形式
            String creditMode = list.get(59);
            dto.setCreditMode(creditMode == null ? null : creditMode);
            // 投资资产种类及比例
            dto.setInvTypeRat(list.get(60));
            // 业绩比较基准
            dto.setYield(new BigDecimal(list.get(61)));
            // 数据状态
            dto.setStatus("E");
            // 备注
            dto.setRemark(list.get(63));
            // 主键
            dto.setDeclareUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase() + (new Random().nextInt(8998)+1000) + "");
            returnInfoList.add(dto);

        }
        return returnInfoList;
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
     * 日期转换
     * @param date
     * @return
     */
    private boolean checkDate(String date) {
        boolean flag = true;
        if(!DateUtil.isDateStr(date,DateUtil.DEFAULT_ANOTHER_FORMAT)){
            flag = false;
        }else{
            date = date.replace("/", "");
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
