package com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.service;

import com.google.common.collect.Lists;
import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.ajax.MessageType;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.tams.common.base.dao.ChbInvestTradeErrodataMapper;
import com.joyintech.tams.common.base.entity.ChbInfoMgrResultConfig;
import com.joyintech.tams.common.base.entity.ChbInvestTrade;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.constant.DictCodeListExt3;
import com.joyintech.tams.report.common.dao.ChbInfoMgrResultConfigMapper;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dao.ChbInvestTradeDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.dto.ChbInvestTradeDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.errodata.dao.ChbInvestTradeErrodataDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbinvesttrade.errodata.dto.ChbInvestTradeErrodataWithBLOBsDto;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dao.ChbPrdBaseInfoDao;
import com.joyintech.tams.svreport.chb.businessdatamaintain.chbprdbaseinfo.dto.ChbPrdBaseInfoDto;
import com.joyintech.tams.svreport.chb.common.util.DuplicateDataUtil;
import com.joyintech.tams.svreport.chb.common.util.ErrdataConvertUtil;
import com.joyintech.tams.svreport.interf.chb.assetinfo.dao.ChbAssetInfoInterfMapper;
import com.joyintech.tams.svreport.interf.chb.investtrade.dao.ChbInvestTradeInterfMapper;
import com.joyintech.tams.svreport.interf.chb.investtrade.service.SvrInvestTradeFileImpService;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import com.joyintech.workflow.engine.constant.OperationType;
import com.joyintech.workflow.engine.service.BaseProcessService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wubingbing
 * @date 2022-04-27 14:17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChbInvestTradeService extends CommonExportService<ChbInvestTradeDto> implements BaseProcessService<ChbInvestTradeDto> {
    @Autowired
    private ChbInvestTradeDao chbInvestTradeDao;
    @Autowired
    private ChbInvestTradeErrodataDao chbInvestTradeErrodataDao;
    @Autowired
    private ChbPrdBaseInfoDao chbPrdBaseInfoDao;
    @Autowired
    private SvrInvestTradeFileImpService svrInvestTradeFileImpService;
    @Autowired
    private ChbInfoMgrResultConfigMapper chbInfoMgrResultConfigMapper;
    @Autowired
    private ChbInvestTradeInterfMapper chbInvestTradeInterfMapper;
    @Autowired
    private ChbInvestTradeErrodataMapper chbInvestTradeErrodataMapper;
    /**
     * 投资交易接入mapper
     */
    @Autowired
    private ChbAssetInfoInterfMapper chbAssetInfoInterfMapper;
    @Override
    public void doComplete(ChbInvestTradeDto entity, OperationType type) {
        //得到交易类型
        this.setTradeType(entity);
        if (OperationType.DELETE.equals(type)) {
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),entity.getStatus())){
                chbInvestTradeErrodataMapper.deleteByPrimaryKey(entity.getTradeSeqno());
            }else{
                entity.setStatus(DictCodeListExt1.维护数据状态_删除.getValue());
                chbInvestTradeDao.update(entity);
            }

        }
        else if (OperationType.NEW.equals(type)) {
            entity.setDataSource(DictCodeListExt2.数据来源_新建导入.getValue());
            entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
            entity.setTradeSeqno(IDGenerator.generate(36));
            List<ChbInvestTrade> chbInvestTrades = Lists.newArrayList((ChbInvestTrade) entity);
            chbInvestTradeInterfMapper.deleteChbInvestTradeErroData(chbInvestTrades);
            chbInvestTradeDao.insert(entity);
        }
        else if(OperationType.MODIFY.equals(type)){
            if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),entity.getStatus())){
                //删除errodata记录
                chbInvestTradeErrodataDao.deleteByPrimaryKey(entity.getTradeSeqno());
                entity.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());
                entity.setDataSource(DictCodeListExt2.数据来源_接口.getValue());
            }
            chbInvestTradeDao.insert(entity);
        }

    }

    /**
     * 得到交易类型
     * @param dto
     */
    private void setTradeType(ChbInvestTradeDto dto){
        // 根据资产代码和资产分类：资管计划去中债要素表中查询
        List<String> assetTypeList = new ArrayList<String>(3);
        assetTypeList.add(DictCodeListExt2.资产_负债类别_资产管理产品.getValue());
        assetTypeList.add(DictCodeListExt2.资产_负债类别_私募基金.getValue());
        assetTypeList.add(DictCodeListExt2.资产_负债类别_委外投资_协议方式.getValue());
        List<String> assetPlanLList = chbAssetInfoInterfMapper.selectAssetCodeByAssetType(assetTypeList, null);
        Map<String,String> assetPlanLMap = assetPlanLList.stream().collect(Collectors.toMap(p -> p, p -> p,(key1, key2) -> key2));
        // TRADE_TYPE资产交易默认'01'，判断是否为资管计划交易,即资产一级分类为资管计划
        if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(assetPlanLList)
                && assetPlanLMap.get(dto.getAssetCode()) != null){
            dto.setTradeType(DictCodeListExt2.投资端交易类型_资管计划交易.getValue());
        }else{
            dto.setTradeType("01");
        }
    }

    public Page<SelectOptionDto> getProdName(Map<String, Object> param) {
        return queryForPageForSelectReturn(Integer.parseInt(null == param.get("page")?"1":(String) param.get("page")), Integer.parseInt(null == (String) param.get("length")?"1":(String) param.get("length")),()->chbInvestTradeDao.getProdName(param));
    }

    public Page<SelectOptionDto> getRegCode(Map<String, Object> param) {
        return queryForPageForSelectReturn(Integer.parseInt(null == param.get("page")?"1":(String) param.get("page")), Integer.parseInt(null == (String) param.get("length")?"1":(String) param.get("length")),()->chbInvestTradeDao.getRegCode(param));
    }

    public Page<SelectOptionDto> getCustomerName(Map<String, Object> param) {
        return queryForPageForSelectReturn(Integer.parseInt(null == param.get("page")?"1":(String) param.get("page")), Integer.parseInt(null == (String) param.get("length")?"1":(String) param.get("length")),()->chbInvestTradeDao.getCustomerName(param));
    }

    public Page<SelectOptionDto> getAssetCode(Map<String, Object> param) {
        Page<SelectOptionDto> page = queryForPageForSelectReturn(Integer.parseInt(null == param.get("page")?"1":(String) param.get("page")), Integer.parseInt(null == (String) param.get("length")?"1":(String) param.get("length")), () -> chbInvestTradeDao.getAssetCode(param));
        if (CollectionUtils.isNotEmpty(page.getRoot())) {
            page.getRoot().forEach(item -> {
                item.setId(item.getId());
                item.setText(item.getId());
            });
        }
        return page;
    }

    public Map<String,String> selectProdIdAndRegCodeByProdName(String prodName) {
        return chbInvestTradeDao.selectProdIdAndRegCodeByProdName(prodName);
    }

    public ChbInvestTradeDto selectByKey(String tradeSeqno,String status) {
        ChbInvestTradeDto dto = new  ChbInvestTradeDto();
        if(StringUtil.equals(DictCodeListExt3.中债数据状态_待处理.getValue(),status)){
            ChbInvestTradeErrodataWithBLOBsDto chbInvestTradeErrodataWithBLOBsDto = chbInvestTradeErrodataDao.selectByPrimaryKey(tradeSeqno);
            if(chbInvestTradeErrodataWithBLOBsDto!=null){
                dto = ErrdataConvertUtil.errdataConvert(dto.getClass(),chbInvestTradeErrodataWithBLOBsDto.getOrgData());
                dto.setStatus(DictCodeListExt3.中债数据状态_待处理.getValue());
                dto.setTradeSeqno(chbInvestTradeErrodataWithBLOBsDto.getDatauuid());
                // 获取产品名称和产品登记编码
                if(!StringUtils.isEmpty(dto.getProdId())){
                    ChbPrdBaseInfoDto chbPrdBaseInfoDto = chbPrdBaseInfoDao.selectByProdId(dto.getProdId());
                    if(chbPrdBaseInfoDto!=null){
                        dto.setProdName(chbPrdBaseInfoDto.getProdName()==null?null:chbPrdBaseInfoDto.getProdName());
                        dto.setRegCode(chbPrdBaseInfoDto.getRegCode()==null?null:chbPrdBaseInfoDto.getRegCode());
                    }
                }
            }
        }else{
            dto = chbInvestTradeDao.selectByKey(tradeSeqno);
        }
        return dto;
    }

    public Map<String,String> selectAssetNameByAssetCode(String assetCode) {
        return chbInvestTradeDao.selectAssetNameByAssetCode(assetCode);
    }
    public Page<ChbInvestTradeDto> selectForPage(Map<String, Object> pageParam) {
        pageParam.put("prodNames", StringUtil.isNotEmpty(pageParam.get("prodName"))?(pageParam.get("prodName").toString().split(",")):null);
        pageParam.put("regCodes", StringUtil.isNotEmpty(pageParam.get("regCode"))?(pageParam.get("regCode").toString().split(",")):null);
        pageParam.put("assetCodes", StringUtil.isNotEmpty(pageParam.get("assetCode"))?(pageParam.get("assetCode").toString().split(",")):null);
        return queryForPage(Integer.parseInt((String) pageParam.get("page")), Integer.parseInt((String) pageParam.get("length")), () -> chbInvestTradeDao.selectAll(pageParam));
    }

    public List<ChbInvestTradeDto> selectAll(Map<String, Object> params) {
        List<ChbInvestTradeDto> list = chbInvestTradeDao.selectAll(params);
        return list;
    }

    public Map<String, String> selectRivalPartyType(String tradeCounter) {
        Map<String, String> map = chbInvestTradeDao.selectRivalPartyType(tradeCounter);
        if(null==map){
            map = new HashMap<>();
        }
        map.put("COUNTER_TYPE", this.counterType2RivalPartyType(map.get("COUNTER_TYPE2")));
        return map;
    }

    public AjaxResult checkConfirmMsgForImportData(String fileName) throws Exception{
        AjaxResult webResult = new AjaxResult();
        if (fileName.indexOf("投资交易") == -1){
            webResult.setMessage(this.getMessage("ImportFileName"), MessageType.错误消息);
            return webResult;
        }
        List<Map<String, String>> list = dataMapAnalysis(fileName);
        if(list.size()<1){
            webResult.setMessage("请勿上传空文件", MessageType.错误消息);
            return webResult;
        }
        int i = chbInvestTradeDao.countDuplicatedData(list);
        if(i>0){
            String[] checkEuplicatedKeys = ChbCheckTableInfo.投资交易信息表.getCheckEuplicatedKeys();
            int dup = DuplicateDataUtil.countDuplicatedData(list, checkEuplicatedKeys);
            webResult.setMessage(this.getMessage("SvrDuplicateMessage",dup,i), MessageType.确认消息);
        }
        return webResult;
    }
    /**
     * excel导入数据整理
     *
     * @param flieName 文件名称
     * @return 检验list
     * @throws Exception 异常
     * @see
     * @since 1.0
     */
    public List<Map<String,String>> dataMapAnalysis(String flieName)
            throws Exception {
        String prefix = CacheUtil.getSysParam(GlobalConstant.COM_PROP_UPLOAD_PATH);
        if(!StringUtil.startsWith(flieName, prefix)
                && !StringUtil.startsWith(flieName, prefix.replace("/", File.separator))) {
            flieName = prefix + File.separator + flieName;
        }
        com.joyintech.framework.util.ExcelUtil excelUtil = com.joyintech.framework.util.ExcelUtil.build(flieName, null);

        List<List<String>> dataList = excelUtil.readToList(0);
        List<String> headList = dataList.get(0);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tableId", "CHB_INVEST_TRADE");
        param.put("flag", false);
        List<ChbInfoMgrResultConfig> chbInfoMgrResultConfigList = chbInfoMgrResultConfigMapper.queryCloumnMapping(param);
        Map<String,String> map = new HashMap<>();
        chbInfoMgrResultConfigList.forEach(ele->map.put(ele.getColumnName().trim().replace("（","(").replace("）",")"),ele.getRealColumnId()));
        List<String> columnList = new ArrayList<>();
        for(int i=0;i<headList.size();++i){
            String columnName = headList.get(i);
            String temp = columnName.trim().replace("（","(").replace("）",")");
            columnName= map.get(temp);
            columnList.add(i,columnName);
        }

        List<Map<String,String>> list = new ArrayList<>();
        for(int i=1;i<dataList.size();++i){
            Map<String,String> subMap = new HashMap<>();
            List<String> subList = dataList.get(i);
            for(int j=0;j<subList.size();++j){
                if(StringUtil.isEmpty(subMap.get(columnList.get(j)))){
                    subMap.put(columnList.get(j),subList.get(j));
                }
            }
            subMap.put("TRADE_SEQNO",IDGenerator.generate(36));

            list.add(subMap);
        }
        for (ChbInfoMgrResultConfig config : chbInfoMgrResultConfigList) {
            if(!StringUtils.isEmpty(config.getResultdictCode())){
                for (Map<String, String> m : list) {
                    if(m.get(config.getRealColumnId())!=null){
                        m.put(config.getRealColumnId(),m.get(config.getRealColumnId()).split(" ")[0]);
                    }
                }
            }
        }
        return list;
    }

    public List<ExcelValidDto> importFileSave(String fileName) throws Exception {
        List<Map<String, String>> list = dataMapAnalysis(fileName);
        List<ExcelValidDto> listExcel = new ArrayList<ExcelValidDto>();
        svrInvestTradeFileImpService.importDB(listExcel,list,new InterfFile(),null);
        return listExcel;
    }

    public List<ChbInvestTradeDto> selectByKeys(List<String> tradeSeqnos) {
        return chbInvestTradeDao.selectByKeys(tradeSeqnos);
    }

    public BigDecimal dealCnyAmt(Map<String, Object> pageParam) {
        pageParam.put("HOLDDATE",DateUtil.parse(pageParam.get("HOLDDATE").toString()));
        pageParam.put("CCY_ORIGINAL", pageParam.get("CCY"));
        pageParam.put("CCY_CONVERT", DictCodeListExt1.币种_中债_人民币.getValue());
        BigDecimal amount = new BigDecimal(pageParam.get("amount").toString());
        // 汇率计算规则
        pageParam.put("pricetype", SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_EXCHANGE_SOURCE));
        BigDecimal rate = chbInvestTradeDao.getRateNearByDate(pageParam);
        rate = rate==null?BigDecimal.ZERO:rate;
        return ArithmeticUtil.multiply(rate,amount);
    }

    public  String counterType2RivalPartyType(String counterType) {
        if (StringUtil.isEmpty(counterType)) {
            return null;
        }
        String rivalPartyType = null;
        switch (counterType) {
            case "C0100":
                rivalPartyType = "01";
                break;
            case "C0200":
                rivalPartyType = "02";
                break;
            case "C0300":
                rivalPartyType = "03";
                break;
            case "C0400":
                rivalPartyType = "04";
                break;
            case "C0401":
                rivalPartyType = "05";
                break;
            case "C0402":
                rivalPartyType = "06";
                break;
            case "C0500":
                rivalPartyType = "07";
                break;
            case "C0501":
                rivalPartyType = "08";
                break;
            case "C0502":
                rivalPartyType = "09";
                break;
            case "C0503":
                rivalPartyType = "10";
                break;
            case "C0504":
                rivalPartyType = "11";
                break;
            case "C0505":
                rivalPartyType = "12";
                break;
            case "C0506":
                rivalPartyType = "13";
                break;
            case "C0507":
                rivalPartyType = "14";
                break;
            case "C0508":
                rivalPartyType = "15";
                break;
            case "C0509":
                rivalPartyType = "16";
                break;
            case "C0600":
                rivalPartyType = "17";
                break;
            default:
                break;
        }
        return rivalPartyType;
    }
}
