/*
 * 文件名：DelayVerification001Service.java
 * 版权：Copyright 2016-2017 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：中信银行资管系统
 * 修改人：dingzhipeng
 * 修改时间：2017年9月25日
 * 修改内容：新建
 * 系统名称：中信银行资管系统
 */

package com.joyintech.tams.report.submitexamination.delayverification.service;

import java.util.*;

import com.google.common.collect.Lists;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ClusterCommandExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcraft.jsch.Logger;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCode;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.service.SvrBaseCommonService;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;
import com.joyintech.tams.report.common.util.SysDataUtil;
import com.joyintech.tams.report.submitexamination.delayverification.dao.DelayVerification001Mapper;
import com.joyintech.tams.report.submitexamination.delayverification.dto.DelayVerification001Dto;
import com.joyintech.tams.report.submitexamination.delayverification.dto.DelayVerification002Dto;

/**
 * 延时报送检查Service
 * 〈功能详细描述〉
 * @author dingzhipeng
 * @version 1.0 2017年9月25日
 * @see DelayVerification001Service
 * @since 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DelayVerification001Service extends BaseService<DelayVerification002Dto>{
    /**
     * 延时报送检查mapper
     */
    @Autowired
    private DelayVerification001Mapper delayVerification001Mapper;
    /**
     * 共通Service
     */
    @Autowired
    private SvrBaseCommonService svrBaseCommonService;

    private final List chbList= Lists.newArrayList("CHB_PRD_STATUS_SUB","CHB_AST_POS_SUB");
    
    /**
     * 延时报送检查的查询方法
     * @param param 存储了查询参数的Map
     * @return Map 存储了查询结果的Map
     * @see
     * @since 1.0
     */
    public Map<String,DelayVerification001Dto> searchDate(Map<String, Object> param,String indexType) {
        Map<String,DelayVerification001Dto> resultsMap= new HashMap<String, DelayVerification001Dto>();
        Date searchDateStart=DateUtil.parse((String)param.get("searchDateStart"));
//        searchDateStart=DateUtil.addHours(searchDateStart, -4);
        Date searchDateEnd=DateUtil.parse((String)param.get("searchDateEnd"));
        searchDateEnd=DateUtil.addHours(searchDateEnd, 24);
        param.put("nosubmit", DictCodeListExt2.报送状态_未报送.getValue());
        param.put("manualSub", DictCodeListExt2.报送状态_手工处理.getValue());
        Date now=svrBaseCommonService.getSysDate();
        param.put("sysTime", now);  
        param.put("searchDateStart", searchDateStart);
        param.put("searchDateEnd", searchDateEnd);
        //param.put("isNotCount", DictCodeList.是否_中债_是.getValue());
       //权限控制
//        param.put("createDeptList",CommonUtil.getAuthorityDeptByUserName(null));

        if(MultilevelDataAuthorityUtil.isEnabled()){
            Boolean flag = MultilevelDataAuthorityUtil.isEnabled();
            param.put("flag", flag);
            List<String> listCode = LoginUserUtil.getTreeInstCode(); // 清空  取的用户的所有有权限机构
            param.put("listCode", listCode);
        }
        List<DelayVerification001Dto> dtoList ;
        if("CHB".equals(indexType)){
            dtoList = delayVerification001Mapper.searchDate(param);
        }else{
            dtoList = delayVerification001Mapper.searchDatePbc(param);
        }
        //        String [] tableId={"","","","","","","","","","","","","","","CHB_EQU_SUB","CHB_EQUDET_SUB"};
//
//        for(int index=1;index <tableId.length;index++){
//            if(StringUtil.isNotEmpty(tableId[index])){
//                param.put("tableId",tableId[index]);
//                int total=delayVerification001Mapper.countTotalData(param);
//                int unqua=delayVerification001Mapper.countUnquaData(param);
//                DelayVerification001Dto dto=new DelayVerification001Dto();
//                dto.setUnSubmitNum(unqua+"");
//                //dto.setRate(Integer.d);
//            }
//        }
        for(DelayVerification001Dto dto : dtoList){
            resultsMap.put(dto.getPageType(),dto);
        }
        return resultsMap;
    }

    /**
     * 根据页面的参数查询Table的数据
     * <功能详细描述>
     * @param pageParam 存储了画面参数的MAP
     * @return 存储了查询结果
     * @see
     * @since 1.0
     */
    public Page<DelayVerification002Dto> searchTable(Map<String, Object> pageParam) {
        Page<DelayVerification002Dto> resultPage=new Page<DelayVerification002Dto>();
        //Map<String ,Object>param=new HashMap<String ,Object>();
        String indexTotal=(String)pageParam.get("indexTotal");
        String [] tableListPbc={"PBC_PRD_ACCOUNT_SUB","PBC_PRD_BASEINFO_SUB","PBC_INIT_PROD_RAISING_SUB"
                ,"PBC_PRD_BASEINFO_END_SUB","PBC_PRD_ACCOUNT_END_SUB","PBC_PROD_RAISING_SUB","PBC_REPORT","PBC_REPORT","PBC_PRD_BASEINFO_SUB_NEW","PBC_PRD_BASEINFO_END_SUB_NEW"};
        String [] reportIndexPbc={"18","19","20","21","22","23","24", "17","32","33"};
       
       
        //在这里添加表名可以增加其Table的支持的列 因为现在字段不确定所以待后续更改
        String [] tableList={"CHB_PRD_DECLARE_SUB","CHB_PRD_ISSUE_SUB","CHB_PRD_RAISETOT_SUB","CHB_PRD_DEAL_SUB",
                             "CHB_PRD_END_SUB","CHB_ASSET_INFO_SUB","CHB_INVEST_TRADE_SUB","CHB_AST_VAL_SUB",
                             "CHB_BOT_AST_POS_SUB","CHB_MONTH_REPORT","CHB_SALER_STL_DEAL_SUB","CHB_SALE_MONTH_REPORT","CHB_INVESTHOLD_INFO_SUB","CHB_EQU_SUB","CHB_EQUDET_SUB",
                             "CHB_SALER_INST_SUB","CHB_SALER_IDEN_SUB","CHB_SALER_REW_PENA_SUB","CHB_AGENT_INSTITUTION_SUB","CHB_AGENT_PRD_SUB","CHB_PRD_STATUS_SUB","CHB_AST_POS_SUB"};
        String [] reportIndex={"01","02","04","06","07","10","11",
                                "12","05","08","34","36","16","14","26","42","43","44","40","41","45","46"};
        pageParam.put("nosubmit", DictCodeListExt2.报送状态_未报送.getValue());
        pageParam.put("manualSub", DictCodeListExt2.报送状态_手工处理.getValue());
        Date now=svrBaseCommonService.getSysDate();
        pageParam.put("sysTime",  now);
        Date searchDateStart=DateUtil.parse((String)pageParam.get("searchDateStart"));
//        searchDateStart=DateUtil.addHours(searchDateStart, -4);
        Date searchDateEnd=DateUtil.parse((String)pageParam.get("searchDateEnd"));
        searchDateEnd=DateUtil.addHours(searchDateEnd, 24);
        pageParam.put("searchDateStart",  searchDateStart);
        pageParam.put("searchDateEnd", searchDateEnd);
        String index=(String)pageParam.get("index");
        //param.put("index",index);
        String tableId=tableList[Integer.parseInt(index)-1];
        pageParam.put("tableId", tableId);
        // 所有中债报送对应的查询都需要连接HIS表,故以下两段注释掉,HIS表相关赋值移动到下面分支中  2019-05-31 高健
//        if("12".equals(index) || "11".equals(index))
//          pageParam.put("hisTableId",tableId+"_HIS");
        //指向报表名称
        pageParam.put("reportIndex", reportIndex[Integer.parseInt(index)-1]);
        if(indexTotal.equals("02")){
//            if(!index.equals("8")){
//                return resultPage;
//            }
            tableId=tableListPbc[Integer.parseInt(index)-1];
            pageParam.put("tableId", tableId);
            pageParam.put("reportIndex", reportIndexPbc[Integer.parseInt(index)-1]);
        }
        //权限控制
//        List<SystemActorRole003Dto> tempList=systemActorRoleService.selectDataModuleByUserName(SecurityUtil.getLoginUser().getUserName());
//        List<String>createDeptList= new ArrayList<>();
//        tempList.forEach(action->{
//            createDeptList.add(action.getDeptCode());
//            createDeptList.add(action.getInstitutionCode());
//        });
        //权限控制
//        pageParam.put("createDeptList",CommonUtil.getAuthorityDeptByUserName(null));
        if(StringUtil.isEmpty(tableId)){
            //防止前端画面出现异常
            List<DelayVerification002Dto> list =new ArrayList<DelayVerification002Dto>();
            resultPage.setRoot(list);
            //resultPage.set
        }
        else{ 
            int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
            int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
            if(indexTotal.equals("02")){
                resultPage =super.queryForPage(pagenum,legth,()->delayVerification001Mapper.searchTablePbc(pageParam));
            }else{
                // HIS表赋值  2019-05-31 高健
               // pageParam.put("hisTableId",tableId+"_HIS");
                //CHB_PRD_STATUS_SUB没有对应的HIS表
                if (!chbList.contains(tableId)) {
                    pageParam.put("hisTableId",tableId+"_HIS");
                }
                resultPage =super.queryForPage(pagenum,legth,()->delayVerification001Mapper.searchTable(pageParam));
            }
        }
        formatSearchResult(resultPage.getRoot(),pageParam.get("reportIndex").toString());
        return resultPage;
    }
    /**
     * 格式化查询结果
     *
     * @param resultList 查询结果
     * @param reportIndex 报表id
     * @see
     * @since 1.0
     */
    private void formatSearchResult(List<DelayVerification002Dto> resultList, String reportIndex) {
        if (StringUtil.isEmptyList(resultList)) {
            return;
        }

        for (DelayVerification002Dto delayVerification002Dto : resultList) {
            if("10".equals(reportIndex)){
                String code = delayVerification002Dto.getCode();
                String assetType2 = code.split(",")[0].split(" ")[1];
                int start = code.indexOf(' ');
                int end = code.indexOf(',');
                String codeString = code.substring(0, start+1).concat(SysDataUtil.getNameFromDataDict(DictCode.资产_负债二级类别, assetType2)==null?assetType2:SysDataUtil.getNameFromDataDict(DictCode.资产_负债二级类别, assetType2)).concat(code.substring(end)); 
                delayVerification002Dto.setCode(codeString);
            } else if (DictCodeListExt2.报表名称_人行月度统计报表.getValue().equals(reportIndex)){
                // 人行月报的唯一标识单独处理
//                List<String> codeList = StringUtil.splitToList(GlobalConstant.VERTICAL_LINE, delayVerification002Dto.getCode());
//                String reporttype = codeList.get(0);
//                String termid = codeList.get(1);
//                String termName = termid.substring(0, 4)+"年"+termid.substring(4, 6)+"月";
//                String codeString = SvrDataUtil.getNameFromDataDict(DictCode.人行报表名称,reporttype)+"_"+termName;
//                delayVerification002Dto.setCode(codeString);
            }
            delayVerification002Dto.setRepName(SysDataUtil.getNameFromDataDict(DictCode.报表名称,
                reportIndex));
            delayVerification002Dto.setSubmitStatus(SysDataUtil.getNameFromDataDict(DictCode.报送状态,
                delayVerification002Dto.getSubmitStatus()));
        }
    }

    /**
     * 读取系统中的对于颜色的配置参数
     * <功能详细描述>
     * @return 保存了配置参数的map
     * @see
     * @since 1.0
     */
    public Map<String, String[]> readConfig(String indexTotal) {
//        List<String> list=delayVerification001Mapper.readConfig();
        Map<String, String[]> resultMap =new HashMap<String, String[]>();
//        for(String code :list){
//            String []str=code.split(",");
//            resultMap.put(str[0], str[1]);
//        }
        if("01".equals(indexTotal)){
            resultMap.put("mids", SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_COLOR_CONFIG_MID_LIST).split(","));
            resultMap.put("tops", SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_COLOR_CONFIG_TOP_LIST).split(","));
        }else{
            resultMap.put("mids", SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_COLOR_CONFIG_MID_LIST_PBC).split(","));
            resultMap.put("tops", SvrDataUtil.getSysParam(ReportGlobalConstant.SYS_PARA_COLOR_CONFIG_TOP_LIST_PBC).split(","));
//          String mids[] = {"90","90","90","90","90","90","90","90"};
//          String tops[] = {"95","95","95","95","95","95","95","95"};
//          resultMap.put("mids", mids);
//          resultMap.put("tops", tops);
        }
        
        return resultMap;
    }

    
    /**
     * 中债、人行报送及时合格率查询
     * <功能详细描述>
     * @return 保存了配置参数的map
     * @see
     * @since 1.0
     */
    public Page<DelayVerification002Dto> selectCHBAndPBC(String indexType,Map<String, Object> pageParam) {
    	Page<DelayVerification002Dto> resultPage=new Page<DelayVerification002Dto>();
    	
    	Date searchDateStart=DateUtil.parse((String)pageParam.get("searchDateStart"));
        Date searchDateEnd=DateUtil.parse((String)pageParam.get("searchDateEnd"));
        searchDateEnd=DateUtil.addHours(searchDateEnd, 24);
        pageParam.put("nosubmit", DictCodeListExt2.报送状态_未报送.getValue());
        pageParam.put("manualSub", DictCodeListExt2.报送状态_手工处理.getValue());
        Date now=svrBaseCommonService.getSysDate();
        pageParam.put("sysTime", now);  
        pageParam.put("searchDateStart", searchDateStart);
        pageParam.put("searchDateEnd", searchDateEnd);

        int pagenum = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_PAGE).toString());
        int legth = Integer.parseInt(pageParam.get(GlobalConstant.PARAM_LENGTH).toString());
        
        if("CHB".equals(indexType)){
        	resultPage =super.queryForPage(pagenum,legth,()->delayVerification001Mapper.searchDate(pageParam));
//        	String CHBRegistrations[]={"产品申报登记", "产品发行登记","产品募集期总量登记",
//                    "产品存续期登记", "产品终止登记", "资产要素登记", "交易信息登记", "资产估值登记",
//                    "资管计划底层持仓登记",  "银行月度统计表登记","投资者持有信息登记","投资者信息登记"};
//        	resultMap.put("Registration",CHBRegistrations);
//        	List<DelayVerification001Dto> dtoListCHB= delayVerification001Mapper.searchDate(pageParam);
//        	String [] missNums=new String[12];
//        	String [] passRates=new String[12];
//        	for(DelayVerification001Dto dto:dtoListCHB){
//        		missNums[Integer.parseInt(dto.getPageType())-1]=dto.getUnSubmitNum();
//        		passRates[Integer.parseInt(dto.getPageType())-1]=dto.getRate();
//        	}
//            resultMap.put("missNum", missNums);
//            resultMap.put("passRate", passRates);
        }else{
        	resultPage =super.queryForPage(pagenum,legth,()->delayVerification001Mapper.searchDatePbc(pageParam));
//        	String PBCRegistrations[]={"资产池基本信息", "产品基本信息","产品起始募集信息",
//            "产品终止信息",  "资产池终止信息", "产品募集信息", "资产负债信息","人行月度统计表"};
//        	resultMap.put("Registration",PBCRegistrations);
//        	List<DelayVerification001Dto> dtoListPBC = delayVerification001Mapper.searchDatePbc(pageParam);
//        	String [] missNums=new String[8];
//        	String [] passRates=new String[8];
//        	for(DelayVerification001Dto dto:dtoListPBC){
//        		missNums[Integer.parseInt(dto.getPageType())-1]=dto.getUnSubmitNum();
//        		passRates[Integer.parseInt(dto.getPageType())-1]=dto.getRate();
//        	}
//            resultMap.put("missNum", missNums);
//            resultMap.put("passRate", passRates);
        }
        return resultPage;
    }
    
}