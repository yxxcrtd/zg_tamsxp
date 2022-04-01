/*
 * 文件名：EtrLendSpecInfoService.java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：xiexinting
 * 修改时间：2018年9月15日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.service.entrust.etrlendspecinfo.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.util.ArithmeticUtil;
import com.joyintech.tams.constant.DictCodeListExt3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.select.SuggestDto;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.validation.ValidMessage;
import com.joyintech.service.entrust.common.dao.EntrustCommonPersonSelectMapper;
import com.joyintech.service.entrust.etrlendspecinfo.dao.EtrLendSpecInfoMapper;
import com.joyintech.service.entrust.etrlendspecinfo.dto.EtrLendSpecInfoDto;
import com.joyintech.tams.common.base.entity.EtrDistribute;
import com.joyintech.tams.common.base.entity.EtrEntrust;
import com.joyintech.tams.common.base.entity.EtrEntrustAdd;
import com.joyintech.tams.common.base.entity.SysUploadFileInfo;
import com.joyintech.tams.component.base.entrust.BusiAutoEtrDispService;
import com.joyintech.tams.constant.DictCodeList;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.invest.common.component.base.core.MstFileCommonService;
import com.joyintech.tams.invest.common.component.base.entrust.BaseEtrInvestInfoService;
import com.joyintech.tams.invest.common.component.base.group.BasePtlCashPositionInfoService;
import com.joyintech.tams.invest.common.dto.BaseEtrInvestInfoDto;
import com.joyintech.tams.invest.common.dto.MstFileInputParameter;
import com.joyintech.tams.invest.common.dto.MstFileOutputParameter;
import com.joyintech.tams.invest.common.dto.PtlCashPositionFlowDto;
import com.joyintech.tams.invest.common.entrust.dto.EntrustCommonDto;
import com.joyintech.tams.invest.common.pagemenusplit.PageMenuSplitUtil;
import com.joyintech.tams.invest.common.trade.service.BaseEtrCashPositionCommService;

/**
 * 拆借投资委托微服务service
 * 〈功能详细描述〉
 * @author xiexinting
 * @version 1.0 2018年9月15日
 * @see EtrLendSpecInfoService
 * @since 1.0
 */
@Service("backEtrLendSpecInfoService")
public class EtrLendSpecInfoService extends BaseService<EtrLendSpecInfoDto> {

    /**
     * 委托人员信息
     */
    @Autowired
    private EntrustCommonPersonSelectMapper entrustCommonPersonSelectMapper;
    /**
     * 附件信息共通Service
     */
    @Autowired(required=false)
    private MstFileCommonService mstFileCommonService;
    /**
     * 组合可用现金和实际现金查询更新组件
     */
    @Autowired(required=false)
    private BasePtlCashPositionInfoService basePtlCashPositionInfoService;
    
    /**
     * 拆借投资委托自定义mapper
     */
    @Autowired
    private EtrLendSpecInfoMapper etrLendSpecInfoMapper;
    /**
     * 投资委托基础组件
     */
    @Autowired
    private BaseEtrInvestInfoService baseEtrInvestInfoService;
    
    /**
     * 委托自动分发
     */
    @Autowired(required=false)
    private BusiAutoEtrDispService busiAutoEtrDispService;

    @Autowired(required=false)
    private BaseEtrCashPositionCommService baseEtrCashPositionCommService;
    
    /**
     * 
     * 拆借投资委托分页查询
     * <功能详细描述>
     * @param page
     * @param length
     * @param param
     * @return
     * @throws Exception
     * @see
     * @since 1.0
     */
    public Page<EtrLendSpecInfoDto> selectForPage(int page,int length,Map<String, Object> param)
    {
        String portfolioId = (String)param.get("portfolioId");
        if(StringUtil.isNotEmpty(portfolioId)){
            param.put("portfolioIdList", StringUtil.splitToList(GlobalConstant.COMMA,portfolioId));
        }
        // add by wangpeng 20210525 银行间场内场外拆分 start
        param.put("defaultTradeMarketList", PageMenuSplitUtil.getTradeMarketList((String) param.get("pageId")));
        // add by wangpeng 20210525 银行间场内场外拆分 end
        param.put("ENTRUST_STATUS_08", DictCodeListExt3.委托状态_流程中.getValue());
        return queryForPage(page,length,()->etrLendSpecInfoMapper.selectForPage(param));
    }
    /**
     * 
     * 查询页面组合代码联想文本框
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public List<SuggestDto> getentrustId(Map<String, Object> map){
        map.put("portType", DictCodeListExt1.组合类型_投资类组合.getValue());
        map.put("portType1", DictCodeListExt1.组合类型_子组合.getValue());
        return etrLendSpecInfoMapper.getentrustId(map);
    }
    /**
     * 
     * 新建页面组合代码联想下拉框
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public Page<SelectOptionDto> getentrustIdAdd(int page, int length,Map<String, String> map){
        map.put("portType", DictCodeListExt1.组合类型_投资类组合.getValue());
        map.put("portType1", DictCodeListExt1.组合类型_子组合.getValue());
        return this.queryForPageForSelectReturn(page, length,()->etrLendSpecInfoMapper.getentrustIdAdd(map));
    }
    /**
     * 校验
     * <功能详细描述>
     * @param dto
     * @return
     * @see
     * @since 1.0
     */
    public List<ValidMessage> check(EtrLendSpecInfoDto dto) {
        String entrustId = dto.getEntrustId();
        String delegateOperationType = dto.getOperateType();
        String delegateCloseWay = dto.getCloseWay();
        List<String> traderList = new ArrayList<>();
        if(dto.getEtrInvestPersonInfo()!=null && StringUtil.isNotEmpty(dto.getEtrInvestPersonInfo().getTrader())){
            traderList.add(dto.getEtrInvestPersonInfo().getTrader());
        }
        return baseEtrInvestInfoService.checkDelegateOperation(entrustId, delegateOperationType,
            traderList, delegateCloseWay);
    }
    /**
     * 撤回委托
     * <功能详细描述>
     * @param dto
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see
     * @since 1.0
     */
    public boolean recallDelegate(EtrLendSpecInfoDto dto) throws IllegalAccessException, InvocationTargetException {
        BaseEtrInvestInfoDto entity = new BaseEtrInvestInfoDto();
        EtrEntrust etrEntrust = new EtrEntrust();
        BeanUtil.copyProperties(etrEntrust,dto);
//        BeanUtil.copyProperties(etrEntrust,dto.getEtrInvestPersonInfo());
        entity.setEtrEntrust(etrEntrust);
        return baseEtrInvestInfoService.recallDelegate(entity);
    }
    /**
     * 关闭委托
     * <功能详细描述>
     * @param entrustId
     * @param closeWay
     * @return
     * @see
     * @since 1.0
     */
    public boolean closeDelegate(String entrustId, String closeWay) {
         return baseEtrInvestInfoService.closeDelegate(entrustId, closeWay);
    }
    /**
     * 
     * 修改/查看信息查询
     * <功能详细描述>
     * @param entrustId
     * @return
     * @see
     * @since 1.0
     */
    public EtrLendSpecInfoDto selectByKey (String entrustId){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("entrustId", entrustId);
        List<String> traderList = entrustCommonPersonSelectMapper.getTraderList(map);
        EtrLendSpecInfoDto etrLendSpecInfoDto = etrLendSpecInfoMapper.selectByKey(entrustId);
        String trader = "";
        for(int i=0; i<traderList.size(); i++){
            trader = trader +","+ traderList.get(i);
        }
        etrLendSpecInfoDto.getEtrInvestPersonInfo().setTrader(trader);
        return etrLendSpecInfoDto;
    }
    /**
     * 删除委托
     * <功能详细描述>
     * @param entrustId
     * @return
     * @see
     * @since 1.0
     */
    public boolean deleteDelegate(String entrustId) {
        return baseEtrInvestInfoService.deleteDelegate(entrustId);
    }
    /**
     * 
     * 工作流完成方法
     * <功能详细描述>
     * @param dto
     * @param operationType
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see
     * @since 1.0
     */
    public void doComplete(EtrLendSpecInfoDto dto, String operationType) throws IllegalAccessException, InvocationTargetException {
        BaseEtrInvestInfoDto entity = new BaseEtrInvestInfoDto();
        EtrEntrust etrEntrust = new EtrEntrust();
        EtrEntrustAdd etrEntrustAdd = new EtrEntrustAdd();
        EntrustCommonDto entrustCommonDto = new EntrustCommonDto();
        if(StringUtil.equals(operationType, "NEW")){
            BeanUtil.copyProperties(etrEntrust,dto);
            BeanUtil.copyProperties(etrEntrust,dto.getEtrInvestPersonInfo());
            etrEntrust.setPs(DictCodeList.交易方向_买入.getValue());
            etrEntrust.setIsOutTrade(DictCodeList.是否_是.getValue());
            etrEntrust.setEntrustStatus(DictCodeList.委托状态_待接收.getValue());
            etrEntrust.setFinprodType2(StringUtil.equals(dto.getPs(),DictCodeList.回购拆借方向_拆入.getValue())?DictCodeListExt1.金融产品类型_投管用_拆入.getValue():DictCodeListExt1.金融产品类型_投管用_拆出.getValue());
            if(dto.getPrice()!=null){
                BigDecimal num = new BigDecimal(100);
                etrEntrust.setPrice(dto.getPrice().divide(num));
            }
            BeanUtil.copyProperties(etrEntrustAdd,dto);
            etrEntrustAdd.setBusiPs(dto.getPs());
            etrEntrustAdd.setMinAmt(dto.getMinAmt());
            etrEntrustAdd.setVdate(dto.getVdateAdd());
            etrEntrustAdd.setMdate(dto.getMdateAdd());
            etrEntrustAdd.setAssetType(DictCodeList.平安资产类型_拆借.getValue());
            etrEntrustAdd.setTermType (dto.getTermType ());
            etrEntrustAdd.setSettleSpreed (dto.getSettleSpreed ());
            //委托指令分发
            List<EtrDistribute> etrDistributeList = new ArrayList<>();
            if(StringUtil.isNotEmpty(dto.getEtrInvestPersonInfo().getTrader())){
                
                List<String> traderList = StringUtil.splitToList(GlobalConstant.COMMA,dto.getEtrInvestPersonInfo().getTrader());
                for(String trader:traderList){
                    EtrDistribute etrDistribute = new EtrDistribute();
                    //设置接收状态
                    etrDistribute.setRecStatus(DictCodeList.委托状态_待接收.getValue());
                    //设置指定交易员指定交易员 = 指定交易员       
                    etrDistribute.setTrader(trader);
                    //设置委托代码
                    etrDistribute.setEntrustId(dto.getEntrustId());
                    //
                    etrDistributeList.add(etrDistribute);
                }
                //entity.setEtrDistributeList(etrDistributeList);
            }
            //自动分发
            if(busiAutoEtrDispService != null){
                //检测是否设置交易员
                if(!busiAutoEtrDispService.checkHaveTrader(etrDistributeList)){
                    //跳出自动分发
                    entity.setEtrDistributeList(etrDistributeList);
                }
                else{
                    //查询委托指令下达日期，如果在自动分发区间内则改指令需自动分发
                    if(busiAutoEtrDispService.checkEntrustTime(etrEntrust)){
                        //自动分发
                        entity.setEtrDistributeList(busiAutoEtrDispService.selectTrader(etrEntrust, etrEntrustAdd));
                    }
                }
            }
            entity.setEtrEntrust(etrEntrust);
            entity.setEtrEntrustAdd(etrEntrustAdd);
            
            baseEtrInvestInfoService.newDelegate(entity);
            //文件
            MstFileInputParameter mstFileInputParameter = new MstFileInputParameter();
            mstFileInputParameter.setFileList(dto.getFileArray());
            mstFileInputParameter.setBussinessId(dto.getEntrustId());
            if(mstFileCommonService != null){
                mstFileCommonService.updateBusinessId(mstFileInputParameter);
            }
            entrustCommonDto.setEtrEntrust(etrEntrust);
            entrustCommonDto.setEtrEntrustAdd(etrEntrustAdd);
        }else{
            BeanUtil.copyProperties(etrEntrust,dto);
            BeanUtil.copyProperties(etrEntrust,dto.getEtrInvestPersonInfo());
            etrEntrust.setPs(DictCodeList.交易方向_买入.getValue());
            etrEntrust.setIsOutTrade(DictCodeList.是否_是.getValue());
            etrEntrust.setEntrustStatus(DictCodeList.委托状态_待接收.getValue());
            etrEntrust.setFinprodType2(StringUtil.equals(dto.getPs(),DictCodeList.回购拆借方向_拆入.getValue())?DictCodeListExt1.金融产品类型_投管用_拆入.getValue():DictCodeListExt1.金融产品类型_投管用_拆出.getValue());
            if(dto.getPrice()!=null){
                BigDecimal num = new BigDecimal(100);
                etrEntrust.setPrice(dto.getPrice().divide(num));
            }
            BeanUtil.copyProperties(etrEntrustAdd,dto);
            etrEntrustAdd.setBusiPs(dto.getPs());
            etrEntrustAdd.setMinAmt(dto.getMinAmt());
            etrEntrustAdd.setVdate(dto.getVdateAdd());
            etrEntrustAdd.setMdate(dto.getMdateAdd());
            etrEntrustAdd.setAssetType(DictCodeList.平安资产类型_拆借.getValue());
            etrEntrustAdd.setTermType (dto.getTermType ());
            etrEntrustAdd.setSettleSpreed (dto.getSettleSpreed ());
            //委托指令分发
            List<EtrDistribute> etrDistributeList = new ArrayList<>();
            if(StringUtil.isNotEmpty(dto.getEtrInvestPersonInfo().getTrader())){
                
                List<String> traderList = StringUtil.splitToList(GlobalConstant.COMMA,dto.getEtrInvestPersonInfo().getTrader());
                for(String trader:traderList){
                    EtrDistribute etrDistribute = new EtrDistribute();
                    //设置接收状态
                    etrDistribute.setRecStatus(DictCodeList.委托状态_待接收.getValue());
                    //设置指定交易员指定交易员 = 指定交易员       
                    etrDistribute.setTrader(trader);
                    //设置委托代码
                    etrDistribute.setEntrustId(dto.getEntrustId());
                    //
                    etrDistributeList.add(etrDistribute);
                }
                //entity.setEtrDistributeList(etrDistributeList);
            }
            //自动分发
            if(busiAutoEtrDispService != null){
                //检测是否设置交易员
                if(!busiAutoEtrDispService.checkHaveTrader(etrDistributeList)){
                    //跳出自动分发
                    entity.setEtrDistributeList(etrDistributeList);
                }
                else{
                    //查询委托指令下达日期，如果在自动分发区间内则改指令需自动分发
                    if(busiAutoEtrDispService.checkEntrustTime(etrEntrust)){
                        //自动分发
                        entity.setEtrDistributeList(busiAutoEtrDispService.selectTrader(etrEntrust, etrEntrustAdd));
                    }
                }
            }
            entity.setEtrEntrust(etrEntrust);
            entity.setEtrEntrustAdd(etrEntrustAdd);
            
            baseEtrInvestInfoService.updateDelegate(entity);
            //上传文件修改更新
            MstFileInputParameter mstFileInputParameter = new MstFileInputParameter();
            mstFileInputParameter.setBussinessId(dto.getEntrustId());
            mstFileInputParameter.setFileList(dto.getFileArray());
            if(mstFileCommonService != null){
                mstFileCommonService.updateBusinessId(mstFileInputParameter);
            }
            entrustCommonDto.setEtrEntrust(etrEntrust);
            entrustCommonDto.setEtrEntrustAdd(etrEntrustAdd);
        }
      //产品化_所有投资指令预占需求_拆借类资产指令 add by longpeng 20201119 start
        //if (baseEtrCashPositionCommService != null) {
          //  baseEtrCashPositionCommService.entrustStartExecute(entrustCommonDto);
       // }
      //产品化_所有投资指令预占需求_拆借类资产指令 add by longpeng 20201119 start
    }
    /**
     * 组合可用现金查询
     * <功能详细描述>
     * @return
     * @see
     * @since 1.0
     */
    public Map<String, Object> getProAvailableCash(Map<String, Object> param) {
        Map<String, Object> result = new HashMap<>();
        PtlCashPositionFlowDto dto = new PtlCashPositionFlowDto();
        Date sysdate =DateUtil.parse((String)param.get("sysDate")) ;
        dto.setCdate(sysdate);
        List<String> acctTypeList = new ArrayList<>();
        acctTypeList.add(DictCodeListExt1.资金账户类型_产品托管户.getValue());
        dto.setAcctType(acctTypeList);
        dto.setPortfolioId((String)param.get("portfolioId"));
        dto.setCcy((String)param.get("ccy"));
        BigDecimal cashTG = basePtlCashPositionInfoService.queryUsableCashPosition(dto);
        result.put("cashTG", cashTG);

        acctTypeList.add(DictCodeListExt1.资金账户类型_券商资金账户.getValue());
        BigDecimal cashT = BigDecimal.ZERO;
        if (baseEtrCashPositionCommService != null) {
            cashT = baseEtrCashPositionCommService.qryCommandAvailableCash(dto);
        } else {
            cashT = basePtlCashPositionInfoService.queryUsableCashPosition(dto);
        }
        result.put("cashT", cashT);
        if(StringUtil.isNotEmpty(param.get("calId"))){
            PtlCashPositionFlowDto dto2 = new PtlCashPositionFlowDto();
            Date sysdateT1 =DateUtil.parse((String)param.get("sysDateT1"));
            dto2.setCdate(sysdateT1);
            dto2.setAcctType(acctTypeList);
            dto2.setPortfolioId((String)param.get("portfolioId"));
            dto2.setCcy((String)param.get("ccy"));
            BigDecimal cashT1 = BigDecimal.ZERO;
            if (baseEtrCashPositionCommService != null) {
                cashT1 = baseEtrCashPositionCommService.qryCommandAvailableCash(dto2);
            } else {
                cashT1 = basePtlCashPositionInfoService.queryUsableCashPosition(dto2);
            }
            result.put("cashT1", cashT1);
        }

        //查询新表可用现金
        PtlCashPositionFlowDto dtoNew = new PtlCashPositionFlowDto();
        dtoNew.setHodingType(DictCodeListExt3.资产类型_汇总可用现金.getValue());
        dtoNew.setCdate(dto.getCdate());
        dtoNew.setPortfolioId((String)param.get("portfolioId"));
        dtoNew.setCcy((String)param.get("ccy"));
        dtoNew.setDateType(0);
        BigDecimal cashT0_new = baseEtrCashPositionCommService.queryEtrAvailableCashNew(dtoNew);
        BigDecimal cashT0_old= new BigDecimal(result.get("cashT").toString());
        if(StringUtil.isNotEmpty(cashT0_new)){
            result.put("cashT",  ArithmeticUtil.doAdd(cashT0_old, cashT0_new));// 组合T日可用
        }
        dtoNew.setDateType(1);
        BigDecimal cashT1_new = baseEtrCashPositionCommService.queryEtrAvailableCashNew(dtoNew);
        if(null!=result.get("cashT1")){
            BigDecimal cashT1_old= new BigDecimal(result.get("cashT1").toString());
            if(StringUtil.isNotEmpty(cashT1_new)){
                result.put("cashT1",  ArithmeticUtil.doAdd(cashT1_old, cashT1_new));// 组合T+1日可用
            }
        }
        return result;
    }
    
    /**
     * 
     * 查询上传文件
     * <功能详细描述>
     * @param businessId businessId
     * @return List<FileArray>
     * @see
     * @since 1.0
     */
    public List<SysUploadFileInfo> selectFileByBusinessId(String businessId){
        MstFileInputParameter mstFileInputParameter = new MstFileInputParameter();
        mstFileInputParameter.setBussinessId(businessId);
        if(mstFileCommonService != null){
            MstFileOutputParameter output = mstFileCommonService.getFileInfoByBusiId(businessId);
            return output.getUploadFileList();
        }
        else{
            return null;
        }
    }
    /**
     * 流程启动时为附件绑定工作流代码
     * <功能详细描述>
     * @param dto
     * @param operateType
     * @see
     * @since 1.0
     */
    public void doInsert(EtrLendSpecInfoDto dto, String operateType) {
      //上传文件修改更新
        MstFileInputParameter mstFileInputParameter = new MstFileInputParameter();
        mstFileInputParameter.setBussinessId(dto.getWorkflowId());
        mstFileInputParameter.setFileList(dto.getFileArray());
        if(mstFileCommonService != null){
            mstFileCommonService.updateBusinessId(mstFileInputParameter);
        }
          //产品化_所有投资指令预占需求_拆借资产指令 add by longpeng 20201119 start
        //产品化_SNSTZJY-2384_拆借，交易方向是拆出,指令金额没有改变 add by zhangpei 20201209 start
        //if (baseEtrCashPositionCommService != null && StringUtil.equals(operateType,"EtrLendSpecInfo002_btn_submit")) {
        if (baseEtrCashPositionCommService != null && StringUtil.equals(dto.getElementId(),"EtrLendSpecInfo002_btn_submit")) {
        //产品化_SNSTZJY-2384_拆借，交易方向是拆出,指令金额没有改变 add by zhangpei 20201209 end
        	 EntrustCommonDto entrustCommonDto = new EntrustCommonDto();
        	 EtrEntrust etrEntrust = new EtrEntrust();
        	 BeanUtil.copyPropertiesIgnoreNull(etrEntrust,dto);
             BeanUtil.copyPropertiesIgnoreNull(etrEntrust,dto.getEtrInvestPersonInfo());
        	 etrEntrust.setPs(DictCodeList.交易方向_买入.getValue());
             etrEntrust.setIsOutTrade(DictCodeList.是否_是.getValue());
             etrEntrust.setEntrustStatus(DictCodeList.委托状态_待接收.getValue());
             etrEntrust.setFinprodType2(StringUtil.equals(dto.getPs(),DictCodeList.回购拆借方向_拆入.getValue())?DictCodeListExt1.金融产品类型_投管用_拆入.getValue():DictCodeListExt1.金融产品类型_投管用_拆出.getValue());
             if(dto.getPrice()!=null){
                 BigDecimal num = new BigDecimal(100);
                 etrEntrust.setPrice(dto.getPrice().divide(num));
             }
             entrustCommonDto.setEtrEntrust(etrEntrust);
             
             EtrEntrustAdd etrEntrustAdd = new EtrEntrustAdd();
             BeanUtil.copyPropertiesIgnoreNull(etrEntrustAdd,dto);
             etrEntrustAdd.setBusiPs(dto.getPs());
             etrEntrustAdd.setMinAmt(dto.getMinAmt());
             etrEntrustAdd.setVdate(dto.getVdateAdd());
             etrEntrustAdd.setMdate(dto.getMdateAdd());
             etrEntrustAdd.setAssetType(DictCodeList.平安资产类型_拆借.getValue());
             etrEntrustAdd.setTermType (dto.getTermType ());
             etrEntrustAdd.setSettleSpreed (dto.getSettleSpreed ());
             entrustCommonDto.setEtrEntrustAdd(etrEntrustAdd);
             baseEtrCashPositionCommService.entrustStartExecute(entrustCommonDto);
             
        	 }
            //产品化_所有投资指令预占需求_拆借类资产指令 add by longpeng 20201119 start
    }
}
