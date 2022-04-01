/*
 * 文件名：EtrBoutRepoService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2019年1月10日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */

package com.joyintech.tams.invest.entrust.etrboutrepo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import com.joyintech.system.base.entity.SysUser;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.entity.ProcessMark;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.export.ExportParam;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.SecurityUtil;
import com.joyintech.framework.security.User;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.tams.component.business.common.service.BasRiskMathCommService;
import com.joyintech.tams.component.business.common.service.CommonBusinessService;
import com.joyintech.tams.component.business.common.service.DateCalService;
import com.joyintech.tams.component.business.trade.BussRepoSecService;
import com.joyintech.tams.component.business.trade.dto.BussEtrInvestPersonInfoDto;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.invest.common.constant.BussinessDictCodeList;
import com.joyintech.tams.invest.common.constant.ServiceApplication;
import com.joyintech.tams.invest.common.trade.dto.EtrSecPositionDto;
import com.joyintech.tams.invest.common.util.ExcelUtil;
import com.joyintech.tams.invest.entrust.common.service.EtrCommonService;
import com.joyintech.tams.invest.entrust.etrboutrepo.dto.EtrBoutRepoDto;
import com.joyintech.tams.invest.entrust.etrinvestbondinfo.service.EtrInvestBondInfoService;
import com.joyintech.tams.invest.entrust.etrplanassetn.service.EtrPlanAssetNService;
import com.joyintech.tams.invest.entrust.etrstock.service.EtrStockService;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.exception.WorkFlowException;
import com.joyintech.workflow.engine.service.BaseProcessService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author xiexinting
 * @version 1.0 2019年1月10日
 * @see EtrBoutRepoService
 * @since 1.0
 */
@Service
public class EtrBoutRepoService extends CommonExportService<EtrBoutRepoDto> implements BaseProcessService<EtrBoutRepoDto> {
    /**
     * 风险试算组件Service
     */
    @Autowired(required = false)
    private BasRiskMathCommService basRiskMathCommService;
    /**
     * 日期相关
     */
    @Autowired
    private DateCalService dateCalService;
    /**
     * 质押券组件
     */
    @Autowired(required = false)
    private BussRepoSecService bussRepoSecService;

    /**
     * 共通组件
     */
    @Autowired
    private CommonBusinessService commonBusinessService;
    /**
     * 债券指令Service
     */
    @Autowired
    private EtrInvestBondInfoService etrInvestBondInfoService;
    /**
     * 股票指令Service
     */
    @Autowired
    private EtrStockService etrStockService;
    /**
     * 计划净值型指令Service
     */
    @Autowired
    private EtrPlanAssetNService etrPlanAssetNService;
    /**
     * 委托多指令共同Service
     */
    @Autowired
    private EtrCommonService etrCommonService;
    /**
     * 项目类委托分页查询 <功能详细描述>
     *
     * @param pageParam
     * @return
     * @see
     * @since 1.0
     */
    public Page<EtrBoutRepoDto> selectForPage(Map<String, Object> param) {
        if (StringUtil.isNotEmpty((String)param.get("entrustDate"))) {
            List<String> dateList = StringUtil.splitToList(" ", (String)param.get("entrustDate"));
            param.put("startDate", dateList.get(0));
            param.put("endDate", dateList.get(2));
        }
//        if(StringUtil.isNotEmpty((String)param.get("ccy"))){
//            List<String> dateList = StringUtil.splitToList(",", (String)param.get("ccy"));
//            param.put("ccyList", dateList);
//        }
        param.put("userId", SecurityUtil.getLoginUser().getUserName());
        //配置单位 edit by yeming 20210802 start
        List<SysUser> userList = restTemplate.postForList(ServiceApplication.ENTRUST_SERVICE_NAME + "/EntrustCommon/getAllUserList",
                param, SysUser.class);
        Page<EtrBoutRepoDto> page = restTemplate.postForPage(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/findPage", param,
                EtrBoutRepoDto.class);
        for(EtrBoutRepoDto dto:page.getRoot()){
            if(dto.getEntrustAmt() != null) {
                dto.setEntrustAmt(dto.getEntrustAmt().divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP));//转成万元
                System.out.println(dto.getEntrustAmt());
            }
            if(StringUtil.isNotEmpty(dto.getNextUser())){
                String sysUsers = userList.stream().filter(x-> Arrays.asList(dto.getNextUser().split(",")).contains(x.getUserName()))
                        .map(SysUser::getRealName)
                        .collect(Collectors.joining(",","",""));
                dto.setNextUser(sysUsers);
            }
        }

        return page;
        //配置单位 edit by yeming 20210802 end
//        return restTemplate.postForPage(
//            ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/findPage", param,
//            EtrBoutRepoDto.class);
    }

    /**
     * 组合代码联想文本框 <功能详细描述>
     *
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    public List<SuggestDto> getentrustId(String param) {
        User user = SecurityUtil.getLoginUser();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getUserName());
        map.put("param", param);
        return restTemplate.postForList(ServiceApplication.ENTRUST_SERVICE_NAME
                        + "/EtrBoutRepo/getentrustId" , map,
                SuggestDto.class);
    }
    /**
     * 关闭委托 <功能详细描述>
     *
     * @param entrustId
     * @param closeWay
     * @see
     * @since 1.0
     */
    public boolean closeDelegate(String entrustId, String closeWay) {
        return restTemplate.getForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/close?entrustId=" + entrustId
                        + "&closeWay=" + closeWay,
                boolean.class);
    }

    /**
     * 撤回委托 <功能详细描述>
     *
     * @param entity
     * @see
     * @since 1.0
     */
    public boolean recallDelegate(EtrBoutRepoDto dto) {
        return restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/recall", dto, boolean.class);
    }
    /**
     * 校验方法
     *
     * @see
     * @since 1.0
     */
    public List<ValidMessage> check(EtrBoutRepoDto dto) {
        dto.setCloseWay(DictCodeListExt1.委托关闭方式_人工.getValue());
        return restTemplate.postForList(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/check", dto,
                ValidMessage.class);
    }
    /**
     *
     * 查询授权额度
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    public BigDecimal selectLimitAmt(EtrBoutRepoDto dto){
        Map<String, Object> map = new HashMap<>();
        map.put("userName", SecurityUtil.getLoginUser().getUserName());
        map.put("assetType", DictCodeList.平安资产类型_拆借.getValue());
        map.put("assetSecType", dto.getAssetType1());
        map.put("tradeMarket", dto.getTradeMarket());
        map.put("ps",dto.getPs());
        return restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/common/SelectLimitAmt/selectLimitAmt", map,
                BigDecimal.class);
    }
    /**
     * 导出
     *
     * @param exportParam 导出信息
     * @see
     * @since 1.0
     */
    @Override
    protected void buildExportParam(ExportParam exportParam) {
        exportParam.config("/com/joyintech/tams/invest/entrust/etrboutrepo/conf/export_conf.xml").dataSource(
                DataSourceType.MASERT);
    }
    @Override
    public Page<EtrBoutRepoDto> selectPageData(Map<String, Object> selectParam) {
        return selectForPage(selectParam);
    }
    @Override
    public void setCellValue(String beanName, Cell cell, String propName, Object value) {
        if(StringUtil.equals(propName, "transactioncom")){
            cell.setCellValue(value+"%");
        }
    }

    /**
     * 工作流完成
     */
    @Override
    public void doComplete(EtrBoutRepoDto dto, OperationType operationType) {
        dto.setOperateType(operationType.toString());
        if(bussRepoSecService != null){
            bussRepoSecService.insert(dto.getBussRepoSec(),dto.getEntrustId());
        }
        Date sysDate =  CacheUtil.getAppCache(GlobalConstant.NOW_SYS_DATE, Date.class);
        dto.setEntrustDate(sysDate);
        basRiskMathCommService.insert(dto.getRiskTrialRetDto(), dto.getEntrustId());
        restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/doComplete", dto,
                Integer.class);
    }
    /**
     *
     * 流程发起时调用
     */
    @Override
    public void doInsert(EtrBoutRepoDto dto, OperationType type){
        restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/doInsert",dto,
                Integer.class);
    }

    @Override
    public void doDelete(String workflowId) {
        restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/doDelete",workflowId,
                Integer.class);
    }

    /**
     *
     * 流程修改时调用
     */
    @Override
    public void doUpdate(EtrBoutRepoDto dto){
        restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/doUpdate",dto,
                Integer.class);
    }

    @Override
    public void doBack(EtrBoutRepoDto dto) {
        restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/doBack",dto,
                Integer.class);
    }

    /**
     * 新建页面组合代码联想下拉框 <功能详细描述>
     *
     * @param search
     * @return
     * @see
     * @since 1.0
     */
    public Page<SelectOptionDto> getentrustIdAdd(Map<String, Object> map) {
        map.put("userId", SecurityUtil.getLoginUser().getUserName());
        return restTemplate.postForPage(ServiceApplication.ENTRUST_SERVICE_NAME
                        + "/EtrBoutRepo/getentrustIdAdd" , map,
                SelectOptionDto.class);
    }

    /**
     * 查询修改/查看返显数据 <功能详细描述>
     *
     * @param entrustId
     * @return
     * @see
     * @since 1.0
     */
    public EtrBoutRepoDto selectByKey(String entrustId) {
        return restTemplate.getForObject(ServiceApplication.ENTRUST_SERVICE_NAME
                        + "/EtrBoutRepo/selectByKey?entrustId=" + entrustId,
                EtrBoutRepoDto.class);
    }
    /**
     * 查询证券托管户：联想框文本框
     *
     * @param parameters 画面参数
     * @return 证券托管户：联想框文本框
     * @see
     * @since 1.0
     */
    public List<SelectOptionDto> getSecManageAcctId(Map<String, Object> parameters) {
        List<SuggestDto> tempList = restTemplate.postForList(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/getSecManageAcctId",
                parameters, SuggestDto.class);

        List<SelectOptionDto> resultList = new ArrayList<SelectOptionDto>();
        if (!StringUtil.isEmptyList(tempList)) {
            for (SuggestDto suggestDto : tempList) {
                SelectOptionDto selectOptionDto = new SelectOptionDto();
                selectOptionDto.setId(suggestDto.getCode());
                selectOptionDto.setText(suggestDto.getName());
                if (StringUtil.isNotBlank((String) parameters.get("portfolioId"))) {
                    selectOptionDto.setParentId((String) parameters.get("portfolioId"));
                }
                resultList.add(selectOptionDto);
            }
        }

        return resultList;
    }
    /**
     * 组合可用现金查询
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public Map<String, Object> getProAvailableCash(Map<String, Object> param) {
        Date vdate = DateUtil.parse((String)param.get("vdate"));
        String calId = (String)param.get("calId");
        if(StringUtil.isNotEmpty(calId)){
            List<String> holidayList = dateCalService.getDealCalendarHoliday(DateUtil.parse(DateUtil.format(vdate)),DateUtil.addDay(DateUtil.parse(DateUtil.format(vdate)), 15),calId);
            Date tradeDay = dateCalService.calculationTradingDay(DateUtil.parse(DateUtil.format(vdate)), 1, holidayList, "1");
            param.put("vdate1",DateUtil.format(tradeDay));
        }
        return restTemplate.postForObject(
                ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/getProAvailableCash",param,
                Map.class);
    }
    /**
     *
     * 判断是否为节假日
     * <功能详细描述>
     * @param param
     * @return
     * @see
     * @since 1.0
     */
    public Map<String, Object> judgeIsHday(Map<String, Object> param){
        String mdateAdd = (String)param.get("mdateAdd");
        String calId = (String)param.get("calId");
        boolean result = dateCalService.isDealCalendarHoliday(mdateAdd,calId);
//         AjaxResult msg =new AjaxResult();
//         msg.setMessage("到期日为节假日");
//         msg.setMessageType(MessageType.错误消息);
        if(result == true){
            List<String> holidayList = dateCalService.getDealCalendarHoliday(DateUtil.parse(mdateAdd),DateUtil.addDay(DateUtil.parse(mdateAdd), 15),calId);
            Date tradeDay = dateCalService.calculationTradingDay(DateUtil.parse(mdateAdd), 1, holidayList, "1");
            param.put("mdateAdd2",DateUtil.format(tradeDay));
            param.put("msg", "到期日为节假日");
        }
        return param;
    }
    /**
     *
     * 查询标准券额度
     * <功能详细描述>
     * @param map
     * @return
     * @see
     * @since 1.0
     */
    public BigDecimal selectStandardAmt(Map<String, Object> map){
        BigDecimal standardAmt = new BigDecimal(0);
        BigDecimal amt = restTemplate.postForObject(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/selectAmt",map,
                BigDecimal.class);
        BigDecimal wflamt = restTemplate.postForObject(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/selectWflAmt",map,
                BigDecimal.class);
        Map<String, Object> prinAmt = restTemplate.postForObject(ServiceApplication.ENTRUST_SERVICE_NAME + "/EtrBoutRepo/selectStandardAmt",map,
                Map.class);
        if(amt!=null){
            standardAmt.add(amt);
        }
        if(wflamt!=null){
            standardAmt.add(wflamt);
        }
        if(prinAmt!=null && prinAmt.get("PRIN_AMT") != null){
            standardAmt.add(new BigDecimal((String)prinAmt.get("PRIN_AMT")));
        }
        return standardAmt;
    }

    // add by renping 授权增加 2019-08-22 start
    @Override
    public Map<String, Object> getRuntimeVariables(ProcessMark entity, String taskId) throws WorkFlowException {
        EtrBoutRepoDto dto = this.getBussinessEntity(entity);
        if (taskId != null) {
            try {
                BigDecimal amount = dto.getEntrustAmt();
                if (amount == null) {
                    amount = dto.getEntrustNum();
                }
                Map<String, Object> params = CommonBusinessService.setParams(dto.getCounterId(), amount, LoginUserUtil.getUserName(),
                        BussinessDictCodeList.平安资产类型_买断式回购.getValue(), dto.getFinprodType2(), dto.getTradeMarket(), dto.getPs()
                        , null, entity.getWorkflowId()
                );
                params.put("term", new BigDecimal(dto.getRepoTerm()));
                return commonBusinessService.是否需要上级审批_委托用(params);
            }
            catch (WorkFlowException e) {
                // 未设置授权或设置成不授权时，抛出异常，当前用户只能退回
                throw new WorkFlowException(e);
            }
        }
        else {
            return null;
        }
    }
    // add by renping 授权增加 2019-08-22 end


    /**
     * 查询交易对手名称
     *
     * @return
     */
    public String getCounterName(String counterId) {
        return restTemplate.getForObject(ServiceApplication.ENTRUST_SERVICE_NAME
                        + "/EtrBondFirstLBidding/getCounterName?counterId="
                        + counterId,
                String.class);
    }

    /**
     * 获取委托列表数据
     *
     * @return
     */
    public List<EtrBoutRepoDto> getEntrustListData(String tempList) {
        List<EtrBoutRepoDto> EtrBoutRepoDtoList = new ArrayList<>();
        JSONArray objects = JSONArray.parseArray(tempList);
        for (int i = 0; i < objects.size(); i++ ) {
            EtrBoutRepoDto dto = new EtrBoutRepoDto();
            JSONArray data = JSONArray.parseArray(((JSONArray)objects.get(i)).toJSONString());
            // 根据模板中固定的下标来复制
            dto.setIndexId(data.get(0).toString().trim());
            // 组合代码
            dto.setPortfolioId(data.get(3).toString().trim());
            // 委托开始日
            dto.setVdate(DateUtil.parse(data.get(1).toString().trim()));
            // 委托结束日
            dto.setMdate(DateUtil.parse(data.get(2).toString().trim()));
            // 资产代码
            dto.setFinprodMarketId(data.get(4).toString().trim());
            if (StringUtil.isNotBlank(data.get(5).toString())) {
                // 分割交易方向，获取code
                String[] s = data.get(5).toString().split(":");
                dto.setPs(s[0]);
            }
            // 交易场所
            if (StringUtil.isNotBlank(data.get(6).toString())) {
                // 分割交易方向，获取code
                String[] s = data.get(6).toString().split(":");
                dto.setTradeMarket(s[0]);
            }
            // 券面总额

            dto.setEntrustAmt(BigDecimal.valueOf(Double.valueOf(data.get(7).toString().trim())));

            // 回购利率
            if (StringUtil.isNotBlank(data.get(8).toString())) {
                dto.setPrice(BigDecimal.valueOf(Double.valueOf(data.get(8).toString().trim())));
            }
            // 价格模式
            if (StringUtil.isNotBlank(data.get(9).toString())) {
                // 分割价格模式，获取code
                String[] s = data.get(9).toString().split(":");
                dto.setPriceModel(s[0]);
            }
            // 首次净价
            if (StringUtil.isNotBlank(data.get(10).toString())) {
                dto.setFirstprice(
                        BigDecimal.valueOf(Double.valueOf(data.get(10).toString().trim())));
            }
            if (StringUtil.isNotBlank(data.get(11).toString())) {
                dto.setFirstprice2(
                        BigDecimal.valueOf(Double.valueOf(data.get(11).toString().trim())));
            }
            // 币种
            if (StringUtil.isNotBlank(data.get(12).toString())) {
                // 分割币种，获取code
                String[] s = data.get(12).toString().split(":");
                dto.setCcy(s[0]);
            }
            // 清算速度
            if (StringUtil.isNotBlank(data.get(13).toString())) {
                // 分割清算速度，获取code
                String[] s = data.get(13).toString().split(":");
                dto.setSettleSpreed(s[0]);
            }
            // 当期限品种不为空，回购期限为空时，根据期限品种带出回购期限
            if (StringUtil.isNotBlank(data.get(16).toString())
                    && StringUtil.isBlank(data.get(14).toString())
                    && StringUtil.isBlank(data.get(15).toString())) {
                String[] s = data.get(16).toString().split(":");
                if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R001.getValue())) {
                    dto.setRepoTerm("1");
                    dto.setRepoTerm2("1");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R007.getValue())) {
                    dto.setRepoTerm("7");
                    dto.setRepoTerm2("7");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R014.getValue())) {
                    dto.setRepoTerm("14");
                    dto.setRepoTerm2("14");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R021.getValue())) {
                    dto.setRepoTerm("21");
                    dto.setRepoTerm2("21");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R1M.getValue())) {
                    dto.setRepoTerm("30");
                    dto.setRepoTerm2("30");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R2M.getValue())) {
                    dto.setRepoTerm("60");
                    dto.setRepoTerm2("60");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R3M.getValue())) {
                    dto.setRepoTerm("90");
                    dto.setRepoTerm2("90");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R4M.getValue())) {
                    dto.setRepoTerm("120");
                    dto.setRepoTerm2("120");
                }
                else if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R6M.getValue())) {
                    dto.setRepoTerm("180");
                    dto.setRepoTerm2("180");
                }
            }
            else {
                // 当回购期限不为空时，无论期限品种有没有值，都取回购期限自己的值
                // 回购期限（最小）
                dto.setRepoTerm(data.get(14).toString().trim());
                // 回购期限（最大）
                dto.setRepoTerm2(data.get(15).toString().trim());
            }
            // 期限品种
            if (StringUtil.isNotBlank(data.get(16).toString())) {
                String[] s = data.get(16).toString().split(":");
                dto.setTermType(s[0]);
            }
            // TISHKS-205:导入模板删除通道列 fuyongsheng 20211105 edit start
            // 通道
//            if (StringUtil.isNotBlank(data.get(18).toString())) {
//                dto.setChlId(data.get(18).toString().trim());
//            }
            // TISHKS-205:导入模板删除通道列 fuyongsheng 20211105 edit end
            // 证券账户
            if (StringUtil.isNotBlank(data.get(18).toString())) {
                dto.setSecuritiesCust(data.get(18).toString().trim());
            }
            // 备注
            dto.setRemark(data.get(19).toString().trim());
            // 证券账户名称查询
            if (StringUtil.isNotEmpty(dto.getSecuritiesCust())) {
                Map<String, Object> map1 = new HashMap<>();
                map1.put("initValue", data.get(18).toString().trim());
                map1.put("page", 1);
                map1.put("length", 20);
                map1.put("portfolioId", data.get(3).toString());
                List<SelectOptionDto> secAcctIds = etrStockService.getSecAcctId(map1);
                // 证券账户名称
                dto.setSecuritiesCustName(secAcctIds.get(0).getText());
            }
            // 通道名称查询
            try {
                if (StringUtil.isNotEmpty(dto.getChlId())) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("initValue", dto.getChlId());
                    param.put("page", 1);
                    param.put("length", 20);
                    Page<SelectOptionDto> selectOptionDtoPage = restTemplate.postForPage(
                            ServiceApplication.COMMON_SERVICE_NAME + "/common/BussChannelCommonService/getChannelId",
                            param, SelectOptionDto.class);
                    // 通道名称
                    dto.setChlIdName(selectOptionDtoPage.getRoot().get(0).getText());
                }
            }
            catch (Exception e) {
                dto.setChlIdName(null);
            }
            // 根据资产代码查出资产名称和FinprodId
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("initValue", dto.getFinprodMarketId());
            map.put("page", 1);
            map.put("length", 20);
            Page<SelectOptionDto> page = etrInvestBondInfoService.getfinprodIdForAdd(map);
            if (StringUtil.isNotEmpty(page) && StringUtil.isNotEmpty(page.getRoot())) {
                // 若查出一条直接获取资产名称和资产代码
                if (page.getRoot().size() == 1) {
                    SelectOptionDto d = page.getRoot().get(0);
                    dto.setFinprodName(d.getText());
                    if (StringUtil.isNotEmpty(d.getObject())) {
                        Map<String, Object> m = (Map<String, Object>)d.getObject();
                        dto.setFinprodId((String)m.get("finprodId"));
                    }
                }
                // 若查出多条，遍历出想要的那条，再获取资产名称和资产代码
                else if (page.getRoot().size() > 1) {
                    for (SelectOptionDto d : page.getRoot()) {
                        if (StringUtil.equals(d.getId(), dto.getFinprodMarketId())) {
                            dto.setFinprodName(d.getText());
                            if (StringUtil.isNotEmpty(d.getObject())) {
                                Map<String, Object> m = (Map<String, Object>)d.getObject();
                                dto.setFinprodId((String)m.get("finprodId"));
                            }
                        }
                    }
                }
            }
            // 交易日历名称
            dto.setCalendarId(data.get(17).toString());
            if(dto.getCalendarId().contains(":")){
                dto.setCalendarId( dto.getCalendarId().split(":")[0]);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("initValue", dto.getCalendarId());
            param.put("page", 1);
            param.put("length", 20);
            Page<SelectOptionDto> selectOptionDtoPage = restTemplate.postForPage(
                    ServiceApplication.COMMON_SERVICE_NAME + "/CommonSelect/selectCalendarId", param,
                    SelectOptionDto.class);
            if (!selectOptionDtoPage.getRoot().isEmpty()) {
                dto.setCalendarIdName(selectOptionDtoPage.getRoot().get(0).getText());
            }
            // 交易对手
            if (StringUtil.isNotBlank(data.get(20).toString())) {
                dto.setCounterId(data.get(20).toString());
                String counterName = getCounterName(data.get(20).toString().trim());
                dto.setCounterIdName(counterName);
            }
            // 指令可用现金
            if (StringUtil.equals(dto.getPs(), DictCodeList.回购拆借方向_逆回购.getValue())) {
                String vdate = DateUtil.format(dto.getVdate(), "yyyyMMdd");
                String calId = null;
                map.put("vdate", vdate);
                map.put("calId", calId);
                map.put("portfolioId", dto.getPortfolioId());
                map.put("ccy", dto.getCcy());
                Map<String, Object> map2 = getProAvailableCash(map);
                if (StringUtil.isNotEmpty(map2.get("cashT"))) {
                    dto.setProAvailableCash(
                            BigDecimal.valueOf(Double.valueOf(map2.get("cashT").toString())));
                }
                else {
                    dto.setProAvailableCash(BigDecimal.ZERO);
                }
            }
            else {
                dto.setProAvailableCash(null);
            }
            // 指令可用持仓
            if (StringUtil.equals(dto.getPs(), DictCodeList.回购拆借方向_正回购.getValue())) {
                map.put("finprodId", dto.getFinprodMarketId());
                EtrSecPositionDto etrSecPositionDto = new EtrSecPositionDto();
                etrSecPositionDto.setCcy(dto.getCcy());
                etrSecPositionDto.setFinprodId(dto.getFinprodId());
                if(StringUtil.isNotEmpty(dto.getSecuritiesCust())) {
                    etrSecPositionDto.setSecAcctId(dto.getSecuritiesCust());
                }else {
                    etrSecPositionDto.setSecAcctId(dto.getChlId());
                }
                etrSecPositionDto.setPortfolioId(dto.getPortfolioId());
                dto.setAssetAvailablePosition(
                        etrPlanAssetNService.getAssetAvailablePosition(etrSecPositionDto));
            }
            else {
                dto.setAssetAvailablePosition(null);
            }
            dto.setEtrInvestPersonInfo(new BussEtrInvestPersonInfoDto());
            // 投资经理
            dto.getEtrInvestPersonInfo().setInvManagerId(dto.getCreateUser());
            // 所属部门
            dto.getEtrInvestPersonInfo().setRoom(dto.getCreateDept());
            // 交易对手
            if (StringUtil.isNotEmpty(dto.getCounterId())) {
                dto.getEtrInvestPersonInfo().setCounterId(dto.getCounterId());
            }
            else {
                dto.getEtrInvestPersonInfo().setCounterId("");
            }
            EtrBoutRepoDtoList.add(dto);
        }
        return EtrBoutRepoDtoList;
    }

    /**
     * 验证导入的数据
     *
     * @param tempList 导入的数据
     * @return wr 报错信息与正确的数据
     */
    public AjaxResult checkImportData(String fileName) {
        List<List<String>> objects = ExcelUtil.getTempList(fileName);
        int totalData = 0;
        for (int i = 1; i < objects.size(); i++ ) {
            List<String> rowData = objects.get(i);
            totalData++ ;
        }
        AjaxResult wr = new AjaxResult();
        // check导入数据是否大于100条
        if (totalData > 100) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("errorMsg", getMessage("ETR_E_0021"));
            wr.setData(resultMap);
        }
        if (!StringUtil.isNotEmpty(wr.getData())) {
            String totalMsg = "";
            List<List<String>> jsonArrays = new ArrayList<>();
            // 失败条数
            int errorNum = 0;
            for (int i = 1; i < objects.size(); i++ ) {
                String confirmMsg = "";
                // excel的行号
                String lineNo = String.valueOf(i + 1);
                List<String> rowData = objects.get(i);
                boolean isEntrust = true;
                if (isEntrust) {
                    // 委托开始日非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(1).toString(),
                                    "EtrBoutRepo001.vdate", lineNo));
                    // 委托开始日格式check
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsDate(rowData.get(1).toString(),
                                    "EtrBoutRepo001.vdate", lineNo));
                    // 委托终止日非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(2).toString(),
                                    "EtrBoutRepo001.mdate", lineNo));
                    // 委托终止日格式check
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsDate(rowData.get(2).toString(),
                                    "EtrBoutRepo001.mdate", lineNo));
                    // 委托开始、终止日是否是平台日check
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            EtrCommonService.checkIsPlateDate(rowData.get(1).toString(), rowData.get(2).toString(),lineNo));
                    // 组合代码非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(3).toString(),
                                    "EtrBoutRepo001.portfolioId", lineNo));
                    // 组合代码check
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkPortfolioIdExist(rowData.get(3).toString(),
                                    "EtrBoutRepo001.portfolioId", lineNo));
                    // 资产代码非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(4).toString(),
                                    "EtrBoutRepo001.finprodId", lineNo));
                    // 资产代码是否存在
                    if (StringUtil.isNotBlank(rowData.get(4).toString())) {
                        Map<String, Object> map1 = new HashMap<String, Object>();
                        map1.put("initValue", rowData.get(4).toString());
                        map1.put("page", 1);
                        map1.put("length", 20);
                        Page<SelectOptionDto> page = etrInvestBondInfoService.getfinprodIdForAdd(
                                map1);
                        if (page.getRoot().isEmpty()) {
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    getMessage("ETR_BR_004", lineNo));
                        }
                    }
                    // 交易方向非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(5).toString(),
                                    "EtrBoutRepo001.ps", lineNo));
                    // 交易场所非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(6).toString(),
                                    "EtrFund002.tradePlat", lineNo));
                    // 券面总额非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(7).toString(),
                                    "EtrBoutRepo001.entrustAmt", lineNo));
                    // 当价格模式是市价指令时回购利率不可填，其他的价格模式时，回购利率必填
                    if (StringUtil.isNotBlank(rowData.get(9).toString())) {
                        String[] s = rowData.get(9).toString().trim().split(":");
                        if (StringUtil.equals(s[0], DictCodeListExt2.价格模式_市价指令.getValue())) {
                            if (StringUtil.isNotBlank(rowData.get(8).toString())) {
                                confirmMsg = etrCommonService.StringAddByBr(confirmMsg, getMessage(
                                        "ETR_BR_001", getMessage("EtrBoutRepo001.price"), lineNo));
                            }
                        }
                        else {
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    etrCommonService.checkIsBlank(rowData.get(8).toString(),
                                            "EtrBoutRepo001.price", lineNo));
                        }
                    }
                    // 价格模式非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(9).toString(),
                                    "EtrStock001.priceModel", lineNo));
                    // 当价格模式是市价指令时首次净价不可填，其他的价格模式时，首次净价必填
                    if (StringUtil.isNotBlank(rowData.get(9).toString())) {
                        String[] s = rowData.get(9).toString().trim().split(":");
                        if (StringUtil.equals(s[0], DictCodeListExt2.价格模式_市价指令.getValue())) {
                            if (StringUtil.isNotBlank(rowData.get(10).toString())) {
                                confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                        getMessage("ETR_BR_001",
                                                getMessage("EtrBoutRepo003.firstprice"), lineNo));
                            }
                            if (StringUtil.isNotBlank(rowData.get(11).toString())) {
                                confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                        getMessage("ETR_BR_001",
                                                getMessage("EtrBoutRepo003.firstprice2"), lineNo));
                            }
                        }
                        else {
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    etrCommonService.checkIsBlank(rowData.get(10).toString(),
                                            "EtrBoutRepo003.firstprice", lineNo));
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    etrCommonService.checkIsBlank(rowData.get(11).toString(),
                                            "EtrBoutRepo003.firstprice2", lineNo));
                        }
                    }
                    // 首次净价（最小）不能大于首次净价（最大）
                    if (StringUtil.isNotBlank(rowData.get(10).toString().trim())
                            && StringUtil.isNotBlank(rowData.get(11).toString().trim())) {
                        if(!isNotNumeric(rowData.get(10)) && !isNotNumeric(rowData.get(11))){
                            BigDecimal a = BigDecimal.valueOf(
                                    Double.valueOf(rowData.get(10).toString().trim()));
                            BigDecimal b = BigDecimal.valueOf(
                                    Double.valueOf(rowData.get(11).toString().trim()));
                            if (a.compareTo(b) > 0) {
                                confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                        getMessage("ETR_BR_002", getMessage("EtrBoutRepo003.firstprice"),
                                                getMessage("EtrBoutRepo003.firstprice2"), lineNo));
                            }
                        }
                    }
                    // 币种非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(12).toString(),
                                    "EtrBoutRepo001.ccy", lineNo));
                    // 清算速度非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(13).toString(),
                                    "EtrBoutRepo001.ccy", lineNo));
                    // 当期限品种为空时，回购期限不能为空
                    if (StringUtil.isBlank(rowData.get(16).toString())) {
                        confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                etrCommonService.checkIsBlank(rowData.get(14).toString(),
                                        "EtrBoutRepo003.repoTerm", lineNo));
                        confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                etrCommonService.checkIsBlank(rowData.get(15).toString(),
                                        "EtrBoutRepo003.repoTerm2", lineNo));
                    }
                    // 当回购期限不为空时
                    else if (StringUtil.isNotBlank(rowData.get(16).toString())) {
                        // 回购期限不能一个有值一个无值
                        if ((StringUtil.isBlank(rowData.get(14).toString())
                                && StringUtil.isNotBlank(rowData.get(15).toString()))
                                || (StringUtil.isBlank(rowData.get(15).toString())
                                && StringUtil.isNotBlank(rowData.get(14).toString()))) {
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    getMessage("ETR_BR_003", lineNo));
                        }
                        // 期限品种为R9M和R1Y时，回购期限不能为空
                        String[] s = rowData.get(16).toString().trim().split(":");
                        if (StringUtil.equals(s[0], DictCodeList.回购期限品种_R9M.getValue())
                                || StringUtil.equals(s[0], DictCodeList.回购期限品种_R1Y.getValue())) {
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    etrCommonService.checkIsBlank(rowData.get(14).toString(),
                                            "EtrBoutRepo003.repoTerm", lineNo));
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    etrCommonService.checkIsBlank(rowData.get(15).toString(),
                                            "EtrBoutRepo003.repoTerm2", lineNo));
                        }
                    }
                    // 回购期限（最小）不能大于回购期限（最大）
                    if (StringUtil.isNotBlank(rowData.get(14).toString().trim())
                            && StringUtil.isNotBlank(rowData.get(15).toString().trim())) {
                        float f = Float.parseFloat(rowData.get(14).toString().trim());
                        float f1 = Float.parseFloat(rowData.get(15).toString().trim());
                        if (f > f1) {
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                                    getMessage("ETR_BR_002", getMessage("EtrBoutRepo003.repoTerm"),
                                            getMessage("EtrBoutRepo003.repoTerm2"), lineNo));
                        }
                    }
                    // 交易日历非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(17).toString(), "EtrBoutRepo002.calendarId", lineNo));
                    // 交易日历check
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkCalendarIdExist(rowData.get(17).toString(), "EtrBoutRepo002.calendarId", lineNo));
                    // TISHKS-205:买断式回购指令导入模板文件删除通道字段 fuyongsheng 20211105 edit start
                    // 通道
//                    if ((StringUtil.isBlank(rowData.get(18).toString())
//                         && StringUtil.isBlank(rowData.get(19).toString()))
//                        || (StringUtil.isNotBlank(rowData.get(18).toString())
//                            && StringUtil.isNotBlank(rowData.get(19).toString()))) {
//                        confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
//                            getMessage("ETR_E_0020", getMessage("invest.channelId"),
//                                getMessage("EtrStock002.secAcctId"), lineNo));
//                    }
                    // 证券账户非空
                    confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
                            etrCommonService.checkIsBlank(rowData.get(18).toString(), "EtrStock002.secAcctId", lineNo));
                    // 证券账户存在
                    if (StringUtil.isNotBlank(rowData.get(18).toString())
                            && StringUtil.isNotBlank(rowData.get(3).toString())) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("initValue", rowData.get(18).toString());
                        map.put("page", 1);
                        map.put("length", 20);
                        map.put("portfolioId", rowData.get(3).toString());
                        List<SelectOptionDto> secAcctIds = etrStockService.getSecAcctId(map);
                        if (secAcctIds.isEmpty()) {
                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg, getMessage(
                                    "ETR_E_0017", getMessage("EtrStock002.secAcctId"), lineNo));
                        }
                    }
                    // 通道存在
//                    if (StringUtil.isNotBlank(rowData.get(18).toString())) {
//                        Map<String, Object> param = new HashMap<>();
//                        param.put("initValue", rowData.get(18).toString());
//                        param.put("page", 1);
//                        param.put("length", 20);
//                        Page<SelectOptionDto> selectOptionDtoPage = restTemplate.postForPage(
//                            ServiceApplication.COMMON_SERVICE_NAME + "/common/BussChannelCommonService/getChannelId",
//                            param, SelectOptionDto.class);
//                        if (selectOptionDtoPage.getRoot().isEmpty()) {
//                            confirmMsg = etrCommonService.StringAddByBr(confirmMsg,
//                                getMessage("ETR_E_0017", getMessage("EtrFund003.chlId"), lineNo));
//                        }
//                    }
                    // TISHKS-205:买断式回购指令导入模板文件删除通道字段 fuyongsheng 20211105 edit end
                }
                // 交易对手是否存在check
                if (StringUtil.isNotBlank(rowData.get(20).toString())) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("initValue", rowData.get(20).toString());
                    map.put("page", 1);
                    map.put("length", 20);
                    map.put("counterType", null);
                    map.put("innerTrade", false);
                    Page<SelectOptionDto> selectOptionDtoPage = restTemplate.postForPage(
                            ServiceApplication.ENTRUST_SERVICE_NAME + "/common/EntrustCommonPersonSelect/getCounterId",
                            map, SelectOptionDto.class);
                    if (selectOptionDtoPage.getRoot().isEmpty()) {
                        confirmMsg = etrCommonService.StringAddByBr(confirmMsg, getMessage(
                                "ETR_E_0017", getMessage("etrInvestPersonInfo.counterId"), lineNo));
                    }
                }
                if (StringUtil.isBlank(confirmMsg)) {
                    jsonArrays.add(rowData);
                }
                else {
                    // 导入失败条数统计
                    totalMsg = etrCommonService.StringAddByBr(totalMsg, confirmMsg);
                    if (isEntrust) {
                        errorNum++ ;
                    }
                }
            }
            totalMsg = etrCommonService.StringAddByBr(totalMsg,
                    getMessage("ETR_C_0001", String.valueOf(errorNum)));
            Map<String, Object> resultMap = new HashMap<>();
            if (StringUtil.isNotBlank(totalMsg)) {
                resultMap.put("confirmMsg", totalMsg);
            }
            resultMap.put("correctData", JSONArray.toJSONString(jsonArrays));
            wr.setData(resultMap);
        }
        return wr;
    }

    /**
     * check是否是数值
     *
     * @param param 金融产品类型
     * @return check结果
     */
    public static boolean isNotNumeric(Object param) {
        if (StringUtil.isNotEmpty(param)) {
            try {
                Double.parseDouble((String)param);
            }
            catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }
}
