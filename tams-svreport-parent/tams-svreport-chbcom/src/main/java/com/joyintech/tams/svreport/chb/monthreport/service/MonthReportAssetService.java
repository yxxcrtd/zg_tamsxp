package com.joyintech.tams.svreport.chb.monthreport.service;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.BeanUtil;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.IDGenerator;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.busicommon.productRating.service.CommonProductRatingService;
import com.joyintech.tams.common.base.dao.ChbAssetInfoMapper;
import com.joyintech.tams.common.base.dao.ChbPrdBaseinfoMapper;
import com.joyintech.tams.common.base.entity.*;
import com.joyintech.tams.common.svreport.base.service.ChbTypeMappingService;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.ChbCcyConvertUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.*;
import com.joyintech.tams.report.interf.common.constant.InterfMappingConstants;
import com.joyintech.tams.svreport.chb.assetinforeg.dto.ChbAssetInfoSub001Dto;
import com.joyintech.tams.svreport.chb.monthreport.dao.ChbMonthAssetPositionMapper;
import com.joyintech.tams.svreport.chb.monthreport.dao.ChbMonthReportDetailMapper;
import com.joyintech.tams.svreport.chb.monthreport.dao.ChbMonthReportTemplateMapper;
import com.joyintech.tams.svreport.chb.monthreport.dto.*;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthAssetPosition;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthAssetRule;
import com.joyintech.tams.svreport.chb.monthreport.dto.ChbMonthPrdPosition;
import com.joyintech.tams.svreport.chb.monthreportdetail.dto.ChbMonthReportDetailAsset;
import com.joyintech.tams.svreport.chb.monthreportdetail.service.ChbMonthReportDetailAssetUtil;
import com.joyintech.tams.svreport.chb.monthreportdetail.service.ChbMonthReportDetailProdUtil;
import com.joyintech.tams.svreport.interf.chb.assetinfo.dao.ChbAssetInfoInterfMapper;
import com.joyintech.tams.systemtools.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 银行月度报表生成资产端数据处理类
 * @author yuluan 
 ** @version 1.0 2019年2月20日
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class MonthReportAssetService {
	@Value("${application.reportSelectOrDeleteCount}")
	public  int reportSelectOrDeleteCount;
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private ChbMonthReportDetailMapper chbMonthReportDetailMapper;

    @Autowired
	private CommonProductRatingService commonProductRatingService;
   
    @Autowired
    private ChbMonthAssetPositionMapper chbMonthAssetPositionMapper;
    @Autowired
	private SqlSessionTemplate template;
    @Autowired
    private MonthReportServiceUtil monthReportServiceUtil;
    
    @Autowired
	private ChbMonthReportTemplateMapper chbMonthReportTemplateMapper;
    @Autowired
    private ChbMonthReportDetailAssetUtil chbMonthReportDetailAssetUtil;


	@Autowired
	private ChbPrdBaseinfoMapper chbPrdBaseinfoMapper;

	/**
	 * 映射service
	 */
	@Autowired
	private ChbTypeMappingService chbTypeMappingService;
    
    public void genReport(ChbMonthReport report,boolean ifTrigger){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(report.getEnddate());

        int year = calendar.get(Calendar.YEAR);//获取年份
        int month = calendar.get(Calendar.MONTH) + 1;//获取月份
        
//        logger.info("生成月报："+year+"年"+month+"月");
//        logger.debug("生成资产头寸：");
//        genAssetPosition(report.getStatusDate());
        logger.info("生成月报资产头寸开始："+year+"年"+month+"月");
        genMonthAssetPosition(report);
        logger.info("生成月报资产头寸结束："+year+"年"+month+"月");
        logger.info("生成月报资产明细数据开始："+year+"年"+month+"月");
		try {

			generateAssetMonthReportDetail(report,ifTrigger);
		} catch (Exception e) {
			logger.error("资产端月报生成错误："+e.getMessage(),e);
		}
		 logger.info("生成月报资产明细数据结束："+year+"年"+month+"月");
        
    }

    /**
     * 
     * 生成月报资产端头寸，
     * 中间数据，对数据进行了产品、资产、头寸对象分组
     * @param year
     * @param month
     * @see
     * @since 1.0
     */
    @SuppressWarnings({ "rawtypes", "deprecation" })
	public void genMonthAssetPosition(ChbMonthReport report) {
    	//月报下个月初统计上个月的
//    	Date cdate=report.getStatusDate();
//    	Date bookdate=cdate;
//    	cdate=DateUtil.addDay(cdate,-1);
		// 获取系统参数主体评级维度，当维度为主体时候，主体评级根据债券资产的FINANCIE关联债券评级表得到评级结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String principalRatingDimension  = SvrDataUtil.getSysParam(SVReportGlobalConstant.PRINCIPAL_RATING_DIMENSION);
        Date firstday=report.getStartdate();
        Date lastday=report.getEnddate();
        //不是月末不处理
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(report.getStatusDate());
//        if ( calendar.get(Calendar.DATE)!=1 ) {  //每月1日生成上月的数据
//            return;
//        }
//        logger.debug("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
        ChbMonthPrdPosition para = new ChbMonthPrdPosition();
//        para.setStartdate(firstday);
        para.setEnddate(lastday);
        //先删除今日头寸 重复跑批
        logger.info("删除今日资产月报头寸开始：");
        chbMonthAssetPositionMapper.deleteByDate(firstday);
        logger.info("删除今日资产月报头寸结束：");
        //1.查询投资交易
        ChbMonthAssetPosition dealpara = new ChbMonthAssetPosition();
        dealpara.setEnddate(lastday);
        dealpara.setBookdate(report.getStatusDate());
		dealpara.setPrincipalRatingDimension(principalRatingDimension);
        // 评级取值规则修改
        dealpara.setSysflag(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_SEC_RATING_FLAG));

		dealpara.setProdids(report.getProdids());
        logger.info("查询今日待处理资产头寸开始：");
        List<ChbMonthAssetPosition> listAssetposition = new ArrayList<ChbMonthAssetPosition>();
		// 获取是否中债子系统(01：是 02：否)
		String isSubSystem = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM);
		// 先定义债项和主体排序sql变量，用以调用共通方法时候使用
		String getProductRatingOrderSqlByZx = null;
		String getProductRatingOrderSqlByZt = null;
		Map<String, Object> productRatingOrderSqlMap = new HashMap<>();
		// 先取债项，再取主体排序sql
		productRatingOrderSqlMap.put("oType","02");
		getProductRatingOrderSqlByZx = commonProductRatingService.getProductRatingOrderSql(productRatingOrderSqlMap);
		productRatingOrderSqlMap.put("oType","01");
		getProductRatingOrderSqlByZt = commonProductRatingService.getProductRatingOrderSql(productRatingOrderSqlMap);
		// 如果是中债子系统取评级逻辑不变，如果不是，就调共通方法
		// 走原有逻辑
		if(isSubSystem != null) {
			if (StringUtils.equalsIgnoreCase(isSubSystem, DictCodeList.是否_中债_是.getValue())) {
				template.select("com.joyintech.tams.svreport.chb.monthreport.dao.ChbMonthAssetPositionMapper.selectAssetposition", dealpara,
						new ResultHandler() {
							@Override
							public void handleResult(ResultContext resultContext) {
								ChbMonthAssetPosition pos = (ChbMonthAssetPosition) resultContext.getResultObject();
								listAssetposition.add(pos);
							}
						});
			}
			// 走共通逻辑
			else {
				dealpara.setGetProductRatingOrderSqlByZx(getProductRatingOrderSqlByZx);
				dealpara.setGetProductRatingOrderSqlByZt(getProductRatingOrderSqlByZt);
				dealpara.setGradeDateEnd(formatter.format(lastday));
				template.select("com.joyintech.tams.svreport.chb.monthreport.dao.ChbMonthAssetPositionMapper.selectAssetpositionByCommon", dealpara,
						new ResultHandler() {
							@Override
							public void handleResult(ResultContext resultContext) {
								ChbMonthAssetPosition pos = (ChbMonthAssetPosition) resultContext.getResultObject();
								listAssetposition.add(pos);
							}
						});
			}
		}
        logger.info("查询今日待处理资产头寸结束：");
        List<ChbMonthAssetPosition> positionList=new ArrayList<ChbMonthAssetPosition>();
        Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
        //FIXME：理财子公司月报处理慢，待优化

		Map<String, ChbMonthAssetPosition> cmapMap = new HashMap<String, ChbMonthAssetPosition>();

		ChbMonthAssetPosition qm = new ChbMonthAssetPosition();

		qm.setBookdate(report.getStatusDate());
		qm.setEnddate(lastday);
		// 评级取值规则
		qm.setSysflag(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_SEC_RATING_FLAG));
		qm.setPrincipalRatingDimension(principalRatingDimension);
		qm.setGradeDateEnd(formatter.format(lastday));
		qm.setGetProductRatingOrderSqlByZx(getProductRatingOrderSqlByZx);
		qm.setGetProductRatingOrderSqlByZt(getProductRatingOrderSqlByZt);

		List<ChbMonthAssetPosition> cmaplist = chbMonthAssetPositionMapper.selectPlanInfoListByCommon(qm);

		if(!StringUtil.isEmptyList(cmaplist)) {
			for(ChbMonthAssetPosition tmp : cmaplist) {
				cmapMap.put(tmp.getAssetCode(),tmp);
			}
		}
        for ( ChbMonthAssetPosition p1:listAssetposition ) {
        	p1.setAssetvalueA(ChbCcyConvertUtil.changeCnyMoney(p1.getCcy(),
        			p1.getAssetvalueA(), lastday, rateMap));
        	p1.setAssetvalueB(ChbCcyConvertUtil.changeCnyMoney(p1.getCcy(),
        			p1.getAssetvalueB(), lastday, rateMap));
        	p1.setAssetvalueA(p1.getAssetvalueA().setScale(2,RoundingMode.HALF_UP));
        	p1.setAssetvalueB(p1.getAssetvalueB().setScale(2,RoundingMode.HALF_UP));
        	// 评级结果 add by jiangxiao start
        	p1.setRating(SvrDataUtil.getNameFromDataDict(DictCode.资产评级,p1.getRating()));
        	// 评级结果 add by jiangxiao end
        	p1.setStartdate(firstday);
            if ( p1.getChlId()!=null &&!"05".equals(p1.getTradeType())) {
                if ( "01".equals(p1.getIsUnderDeal()) )  { //排除非首层交易
                    p1.setAssetvalueB(p1.getAssetvalueA());
                    p1.setAssetvalueA(null);
                }
            } else {
                if ( "01".equals(p1.getTradeType()) ) {    //产品直投交易
                    p1.setAssetvalueB(p1.getAssetvalueA()); 
                    p1.setPosuuid(IDGenerator.generate(36));
                    positionList.add(p1);
//                     chbMonthAssetPositionMapper.insert(p1);
                } else if ( "02".equals(p1.getTradeType()) ) { //产品买通道
                	 p1.setPosuuid(IDGenerator.generate(36));
                	 positionList.add(p1);
//                    chbMonthAssetPositionMapper.insert(p1); 
                    //查询通道底层交易
                    ChbMonthAssetPosition p = new ChbMonthAssetPosition();
                    p.setChlId(p1.getAssetCode());
                    p.setProdId(p1.getProdId());
                    p.setBookdate(p1.getBookdate());
                    p.setEnddate(p1.getEnddate());
                    p.setPrincipalRatingDimension(principalRatingDimension);
                    // 如果不是独立子系统走共通，就赋值排序语句
                    if(isSubSystem != null){
                        if (!StringUtils.equalsIgnoreCase(isSubSystem, DictCodeList.是否_中债_是.getValue())){
                            p.setGetProductRatingOrderSqlByZx(getProductRatingOrderSqlByZx);
                            p.setGetProductRatingOrderSqlByZt(getProductRatingOrderSqlByZt);
							p.setGradeDateEnd(formatter.format(lastday));
                        }
                    }
                    List<ChbMonthAssetPosition> listChlUnderDeal = this.genMonthChlPosition(p);
                    for ( ChbMonthAssetPosition p11:listChlUnderDeal ) {
                    	p11.setStartdate(firstday);
                    	p11.setOldAssetCode(p1.getAssetCode());
                    	p11.setPosuuid(IDGenerator.generate(36));
                    	p11.setAssetvalueB(p11.getAssetvalueB().setScale(2,RoundingMode.HALF_UP));
                    	positionList.add(p11);
//                        chbMonthAssetPositionMapper.insert(p11);
                    }
                }  else if ( "05".equals(p1.getTradeType())) { //产品购买母资管计划
                	p1.setPosuuid(IDGenerator.generate(36));
                	BigDecimal tempValue=p1.getAssetvalueB();
                	p1.setAssetvalueB(null);
                	if(p1.getIsUnderDeal().equals("02"))//不为底层才插A列值
//                    chbMonthAssetPositionMapper.insert(p1);
                	positionList.add(p1);
                    //查询资管计划底层交易
                    ChbBotAstPosition p = new ChbBotAstPosition();
                    p.setPosDate(p1.getEnddate());
                    p.setPlanAssetId(p1.getAssetCode());
                    p.setAccuamt(tempValue);
                    //穿透后资产价值取值控制
                    
                    String monthReport=SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_CHBMONTH_REPORT);
                    p.setMonthReport(monthReport);
                    
                    List<ChbBotAstPosition> listPlanUnderDeal = this.genMonthPlanPosition(p);
                    for ( ChbBotAstPosition p11:listPlanUnderDeal ) {
                        ChbMonthAssetPosition m = new ChbMonthAssetPosition();
//                        ChbMonthAssetPosition qm = new ChbMonthAssetPosition();
//                        qm.setAssetCode(p11.getAssetCode());
//                        qm.setBookdate(p1.getBookdate());
//                        qm.setEnddate(p1.getEnddate());
//                        // 评级取值规则
//                        qm.setSysflag(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_SEC_RATING_FLAG));
//						qm.setPrincipalRatingDimension(principalRatingDimension);
						// 评级结果取值改变
                        // 判断走独立子系统还是走共通
                        if(isSubSystem != null){
//                            if (StringUtils.equalsIgnoreCase(isSubSystem, DictCodeList.是否_中债_是.getValue())){
//                                m=chbMonthAssetPositionMapper.selectPlanInfo(qm);
//                            }else{
////                            	qm.setGradeDateEnd(formatter.format(lastday));
////								qm.setGetProductRatingOrderSqlByZx(getProductRatingOrderSqlByZx);
////								qm.setGetProductRatingOrderSqlByZt(getProductRatingOrderSqlByZt);
////								m = chbMonthAssetPositionMapper.selectPlanInfoByCommon(qm);
//
//                            }

							ChbMonthAssetPosition cmamap =cmapMap.get(p11.getAssetCode());
							if(cmamap!=null) {
								BeanUtil.copyPropertiesIgnoreNull(m, cmamap);
							}
                        }

						if(m == null){
							logger.info("请先导入底层持仓信息后再跑月报资产头寸批处理");
							continue;
						}
						if (m != null) {
							//评级结果 add by jiangxiao start
							if(m.getRating()!=null){
								m.setRating(SvrDataUtil.getNameFromDataDict(DictCode.资产评级,m.getRating()));
							}
							//评级结果 add by jiangxiao end
							m.setProdId(p1.getProdId());
							m.setAssetCode(p11.getAssetCode());
							m.setAssetvalueA(null);
							m.setAssetvalueB(p11.getAccuamt());
							m.setAssetvalueB(m.getAssetvalueB().setScale(2,RoundingMode.HALF_UP));
							m.setPosuuid(IDGenerator.generate(36));
							m.setStartdate(firstday);
//                        chbMonthAssetPositionMapper.insert(m);
							m.setOldAssetCode(p1.getAssetCode());
							//新加字段
							m.setIsFhxgcp(p1.getIsFhxgcp());
							positionList.add(m);
						}
                    }
                }
            }
        }
		// add by dubaojun 2020-06-06 新增理财子公司月报资产头寸字段设置 start
		if (CollectionUtils.isNotEmpty(positionList)) {
			// 存储资产对应的期限和子公司资产分类，防止多次计算
			Map<String, ChbMonthAssetPosition> assetMap = new HashMap<>();
			// 存储产品代码对应的产品期限
			Map<String, BigDecimal> prodTermMap = new HashMap<>();
			for (ChbMonthAssetPosition position : positionList) {
				// modify by wm 之前资产计算过的key需要假如底层资产的code当做key值
				String assetKey = position.getAssetCode();
				if(StringUtil.isNotEmpty(position.getOldAssetCode())){
					assetKey = assetKey + StringUtil.SPACE + position.getOldAssetCode();
				}
				// 如果之前资产已经计算过，则直接设置值即可
				if(assetMap.containsKey(assetKey)) {
					ChbMonthAssetPosition assetInfo = assetMap.get(assetKey);
					position.setTerms(assetInfo.getTerms());
					position.setSunassettype(assetInfo.getSunassettype());
					position.setSunassettype2(assetInfo.getSunassettype2());
					continue;
				}
				if ("REMAININGCASH".equals(position.getAssetCode())) {
					// 资产代码为REMAININGCASH的特殊资产，该字段默认=0
					//position.setTerms(BigDecimal.ONE);
					position.setTerms(BigDecimal.ONE);
					// 资产代码为REMAININGCASH的特殊资产，该字段默认=F06
					position.setSunassettype("F06");
					// 资产代码为REMAININGCASH的特殊资产，该字段默认=F0602
					position.setSunassettype2("F0602");
				}
				else {
					// 如果中债资产类型
					if (StringUtil.isEmpty(position.getAssettype2()) || StringUtil.isEmpty(position.getAssetCode())) {
						continue;
					}
					// 资产信息
					ChbAssetInfo chbAssetInfo = chbMonthAssetPositionMapper.selectChbAssetInfoByAssetCode(position.getAssetCode());
					if (chbAssetInfo == null) {
						continue;
					}
					// 上层资产信息
					ChbAssetInfo supAssetInfo = null;
					if (StringUtil.isNotEmpty(position.getOldAssetCode())) {
						supAssetInfo = chbMonthAssetPositionMapper.selectChbAssetInfoByAssetCode(position.getOldAssetCode());
					}
					// 计算头寸表中的期限
					this.newCalcTerms(report, prodTermMap, position, chbAssetInfo, supAssetInfo);
					/**
					 * 映射月报类型，采用资产负债类别二级进行映射，部分资产再根据上层资产做映射
					 * 银行资本补充工具、是否PPN等低流动性债券、是否商业银行优先股、创业投资基金、是否未上市企业-政府出资产业投资基金 做特殊映射
					 * CHB_TYPE_MAPPING_EX为匹配规则配置表，当前方法用srcfield做匹配，用8个字段配置，样例1002||||||||、||||02||||、2101|||1701|||||
					 **/
					/* srcField中竖线分隔的字段含义如下：
					 * 资产负债二级类别                   （主键1）
					 * 无                               （主键2）
					 * 无                               （主键3）
					 * 首层资产二级类型                   （主键4）
					 * 银行资本补充工具 			        （主键5）
					 * 是否PPN等低流动性债券              （主键6）
					 * 是否商业银行优先股                 （主键7）
					 * 创业投资基金                      （主键8）
					 * 是否未上市企业-政府出资产业投资基金  （主键9）
					 */
					String key1 = position.getAssettype2();//主键1
					String key2 = null;//主键2
					String key3 = null;//主键3
					String key4 = null;//主键4
					String key5 = null;//主键5
					String key6 = null;//主键6
					String key7 = null;//主键7
					String key8 = null;//主键8
					String key9 = null;//主键9
					if(StringUtil.equals(key1, DictCodeListExt2.资产_负债二级类别_公司债券.getValue())){
						key2 = position.getBankSecKzzq();
					}
					else if(StringUtil.equals(key1, DictCodeListExt2.资产_负债二级类别_股票_一级市场.getValue())){
						key2 = chbAssetInfo.getBankSecPrefer();
					}
					ChbTypeMappingEx mapping = chbTypeMappingService.getMappingBySrcField(InterfMappingConstants.MAPPINGCODE_SUBASSETTYPE,
							key1, key2, key3, key4, key5, key6, key7, key8, key9);
					if (mapping != null) {
						position.setSunassettype(mapping.getObjfield1());
						position.setSunassettype2(mapping.getObjfield2());
					}
					//特殊处理：银行资本补充工具、是否PPN等低流动性债券、是否商业银行优先股、创业投资基金、是否未上市企业-政府出资产业投资基金
					//银行资本补充工具 排除05其他银行资本工具
					if(StringUtil.isNotEmpty(chbAssetInfo.getBankCapital()) &&
							!DictCodeListExt3.银行资本补充工具_其他银行资本补充工具.getValue().equals(chbAssetInfo.getBankCapital())) {
						key5 = chbAssetInfo.getBankCapital();
						key1 = null;
						key2 = null;
					}
					//是否PPN等低流动性债券
					else if(StringUtil.isNotEmpty(chbAssetInfo.getIsPpn()) && DictCodeList.是否_中债_是.getValue().equals(chbAssetInfo.getIsPpn())) {
						key6 = chbAssetInfo.getIsPpn();
						key1 = null;
						key2 = null;
					}
					//是否商业银行优先股
					else if(StringUtil.isNotEmpty(chbAssetInfo.getBankSecPrefer()) && DictCodeList.是否_中债_是.getValue().equals(chbAssetInfo.getBankSecPrefer())) {
						key7 = chbAssetInfo.getBankSecPrefer();
						key1 = null;
						key2 = null;
					}
					//创业投资基金
					else if(StringUtil.isNotEmpty(chbAssetInfo.getIsGovernFund()) && DictCodeList.是否_中债_是.getValue().equals(chbAssetInfo.getIsGovernFund())) {
						key8 = chbAssetInfo.getIsGovernFund();
						key1 = null;
						key2 = null;
					}
					//是否未上市企业-政府出资产业投资基金
					else if(StringUtil.isNotEmpty(chbAssetInfo.getIsunlisted()) && DictCodeList.是否_中债_是.getValue().equals(chbAssetInfo.getIsunlisted())) {
						key9 = chbAssetInfo.getIsunlisted();
						key1 = null;
						key2 = null;
					}
					// 如果一条数据符合多个规则，则重复计算
					if(key1 == null && (key5 != null || key6 != null || key7 != null || key8 != null || key9 != null)){
						ChbTypeMappingEx mapping1 = chbTypeMappingService.getMappingBySrcField(InterfMappingConstants.MAPPINGCODE_SUBASSETTYPE,
								key1, key2, key3, key4, key5, key6, key7, key8, key9);
						if (mapping1 != null) {
							position.setSunassettype(position.getSunassettype()+","+mapping1.getObjfield1());
							position.setSunassettype2(position.getSunassettype2()+","+mapping1.getObjfield2());
						}
					}

					if(supAssetInfo != null){
						key4 = supAssetInfo.getAssetType2();
						key2 = null;
					}

					mapping = chbTypeMappingService.getMappingBySrcField(InterfMappingConstants.MAPPINGCODE_SUBASSETTYPE,
							key1, key2, key3, key4, key5, key6, key7, key8, key9);
					if (mapping != null) {
						position.setSunassettype(position.getSunassettype()+","+mapping.getObjfield1());
						position.setSunassettype2(position.getSunassettype2()+","+mapping.getObjfield2());
					}
					assetMap.put(assetKey, position);
				}
			}
		}
		// add by dubaojun 2020-06-06 新增理财子公司月报资产头寸字段设置 end
		cmapMap.clear();
		cmapMap =null;
        SqlSession session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    	try{
    		ChbMonthAssetPositionMapper batchMapper =  session.getMapper(ChbMonthAssetPositionMapper.class);
    		int count = 0;
    		for(ChbMonthAssetPosition position : positionList){
    			batchMapper.insert(position);
    			count++;
        		if(count % 1000==0){
            	   session.commit();
            	   session.clearCache();
        		}
    		}
    		session.commit();
            // 清理缓存，防止溢出
            session.clearCache();
    	} finally {
    		session.close();
    	}
        
       //查询剩余现金
        logger.info("处理剩余现金开始：");
//        List<ChbMonthAssetPosition> prdCashList = chbMonthAssetPositionMapper.selectPrdCashByDate(cdate);
       
//        Map<String,ChbMonthAssetPosition>  cashMap=new HashMap<String,ChbMonthAssetPosition>();
//        for(ChbMonthAssetPosition single:prdCashList){
//        	single.setAssetvalueA(ChbCcyConvertUtil.changeCnyMoney(single.getCcy(),
//        			single.getCashamt(), cdate, rateMap));
//        	single.setAssetvalueB(single.getAssetvalueA());
//        	if(cashMap.containsKey(single.getProdId())){
//        		ChbMonthAssetPosition mapvalue=cashMap.get(single.getProdId());
//        		mapvalue.setAssetvalueA(mapvalue.getAssetvalueA().add(single.getAssetvalueA()));
//        		mapvalue.setAssetvalueB(mapvalue.getAssetvalueA());
//        	}else{
//        		cashMap.put(single.getProdId(), single);
//        	}
//        }
//        //查询资产端剩余现金 穿透前
//        List<ChbMonthAssetPosition> assetCashListA=chbMonthAssetPositionMapper.selectAssetCashByDateA(cdate);
//        for(ChbMonthAssetPosition single:assetCashListA){
//        	single.setCashamt(ChbCcyConvertUtil.changeCnyMoney(single.getCcy(),
//        			single.getCashamt(), cdate, rateMap));
//        	if(cashMap.containsKey(single.getProdId())){
//        		ChbMonthAssetPosition mapvalue=cashMap.get(single.getProdId());
//        		mapvalue.setAssetvalueA(mapvalue.getAssetvalueA().add(single.getCashamt()));
//        	}else{
//        		single.setAssetvalueA(single.getCashamt());
//        		cashMap.put(single.getProdId(), single);
//        	}
//        }
//        //查询资产端剩余现金 穿透后
//        List<ChbMonthAssetPosition> assetCashListB=chbMonthAssetPositionMapper.selectAssetCashByDateB(cdate);
//        for(ChbMonthAssetPosition single:assetCashListB){
//        	single.setCashamt(ChbCcyConvertUtil.changeCnyMoney(single.getCcy(),
//        			single.getCashamt(), cdate, rateMap));
//        	if(cashMap.containsKey(single.getProdId())){
//        		ChbMonthAssetPosition mapvalue=cashMap.get(single.getProdId());
//        		mapvalue.setAssetvalueB((mapvalue.getAssetvalueB()==null?BigDecimal.ZERO:mapvalue.getAssetvalueB()).add(single.getCashamt()));
//        	}
//        }
//        List<ChbMonthAssetPosition> insertList=new ArrayList<ChbMonthAssetPosition>();
//        for(String key:cashMap.keySet()){
//        	ChbMonthAssetPosition cashmodel=	cashMap.get(key);
//        	cashmodel.setAssetCode("REMAININGCASH");
//        	cashmodel.setAssettype("01");
//        	cashmodel.setAssettype2("1001");
//        	cashmodel.setPosuuid(IDGenerator.generate(36));
//        	cashmodel.setStartdate(firstday);
//        	cashmodel.setBookdate(bookdate);
//        	cashmodel.setEnddate(cdate);
//        	insertList.add(cashmodel);
//        }
        //底层剩余
        
//        List<ChbMonthAssetPosition> botCashList=chbMonthAssetPositionMapper.selectBotCashList(lastday);
        //计划类净值型资产底层中剩余现金折算逻辑问题 update by leipeng 20200712
        List<ChbMonthAssetPosition> botCashList=chbMonthAssetPositionMapper.selectBotCashListEx(lastday,report.getProdids());
        if(botCashList!=null&&!botCashList.isEmpty()){
        	for(ChbMonthAssetPosition bot:botCashList){
//        		ChbMonthAssetPosition botcashPosition=new ChbMonthAssetPosition();
        		bot.setAssetCode("REMAININGCASH");
        		bot.setAssetvalueA(BigDecimal.ZERO);
        		bot.setAssetvalueB(ChbCcyConvertUtil.changeCnyMoney(bot.getCcy(),
        				bot.getCashamt(), lastday, rateMap));
        		bot.setAssettype("01");
        		bot.setAssettype2("1001");
        		bot.setPosuuid(IDGenerator.generate(36));
        		bot.setStartdate(firstday);
        		try {
					bot.setBookdate(formatter.parse(formatter.format(report.getStatusDate())));
				} catch (ParseException e) {
					logger.error(e.getMessage(),e);
				}
        		bot.setEnddate(lastday);
        		chbMonthAssetPositionMapper.insert(bot);
        	}
//        	insertList.add(botcashPosition);
        }
//        session = template.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//    	try{
//    		ChbMonthAssetPositionMapper batchMapper =  session.getMapper(ChbMonthAssetPositionMapper.class);
//    		int count = 0;
//    		for(ChbMonthAssetPosition position : insertList){
//    			batchMapper.insert(position);
//    			count++;
//        		if(count % 1000==0){
//            	   session.commit();
//            	   session.clearCache();
//        		}
//    		}
//    		session.commit();
//            // 清理缓存，防止溢出
//            session.clearCache();
//    	} finally {
//    		session.close();
//    	}
//    	chbMonthAssetPositionMapper.insert(cashPosition);
        logger.info("处理剩余现金开始结束：");
    }
    

    
    @SuppressWarnings("deprecation")
	private List<ChbMonthAssetPosition> genMonthChlPosition (ChbMonthAssetPosition pos) {
        List<ChbMonthAssetPosition> list = new ArrayList<ChbMonthAssetPosition>();
        Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
        // 评级取值规则
        pos.setSysflag(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_SEC_RATING_FLAG));
        // 评级取值改变
        // 判断走独立子系统还是走共通
        // 获取是否中债子系统(01：是 02：否)
        List<ChbMonthAssetPosition> listUnderDeal = new ArrayList<ChbMonthAssetPosition>();
        String isSubSystem = SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_IS_SUB_SYSTEM);
        if(isSubSystem != null){
            if (StringUtils.equalsIgnoreCase(isSubSystem, DictCodeList.是否_中债_是.getValue())){
                 listUnderDeal = chbMonthAssetPositionMapper.selectChlDeal(pos);
            }else{
                Map<String, Object> productRatingOrderSqlMap = new HashMap<>();
                if(StringUtils.isBlank(pos.getGetProductRatingOrderSqlByZx())){
                    // 先取债项，再取主体排序sql
                    productRatingOrderSqlMap.put("oType","02");
                    pos.setGetProductRatingOrderSqlByZx(commonProductRatingService.getProductRatingOrderSql(productRatingOrderSqlMap));
                }
                if(StringUtils.isBlank(pos.getGetProductRatingOrderSqlByZt())){
                    productRatingOrderSqlMap.put("oType","01");
                    pos.setGetProductRatingOrderSqlByZt(commonProductRatingService.getProductRatingOrderSql(productRatingOrderSqlMap));
                }
                listUnderDeal = chbMonthAssetPositionMapper.selectChlDealByCommon(pos);
            }
        }
        for (ChbMonthAssetPosition p11:listUnderDeal ) {
			p11.setPrincipalRatingDimension(pos.getPrincipalRatingDimension());
        	//评级结果 add by jiangxiao start
        	if(p11.getRating()!=null){
        		p11.setRating(SvrDataUtil.getNameFromDataDict(DictCode.资产评级,p11.getRating()));
        	}
        	//评级结果 add by jiangxiao end
            if ( "01".equals(p11.getIsUnderDeal()) ) { //底层交易
                p11.setAssetvalueA(null);
                //计算穿透资产价值
                p11.setAssetvalueB(ChbCcyConvertUtil.changeCnyMoney(p11.getCcy(),
                		p11.getAssetvalueB(), pos.getEnddate(), rateMap));
                list.add(p11);
            } else {
            	list.addAll(genMonthChlPosition(p11));
            }
        }
        return list;
    }
    
    private List<ChbBotAstPosition> genMonthPlanPosition ( ChbBotAstPosition pos ) {
        List<ChbBotAstPosition> list = new ArrayList<ChbBotAstPosition>();
        //查询底层持仓
        List<ChbBotAstPosition> listUnderDeal = chbMonthAssetPositionMapper.selectPlanDeal(pos);
        for ( ChbBotAstPosition p11:listUnderDeal ) {
            if (p11.getIsfinaplan()==null|| "02".equals(p11.getIsfinaplan()) ) { //不是资管计划
              //计算穿透资产价值
				if (p11.getTotalNum() != null && p11.getTotalNum().compareTo(BigDecimal.ZERO) != 0){
					if("01".equals(pos.getMonthReport())){
						p11.setAccuamt( p11.getAccuamt().divide(p11.getTotalNum(),18,RoundingMode.HALF_UP).multiply(p11.getPosAmt()) );
					}
					else{
						p11.setAccuamt( p11.getAccuamt().divide(p11.getTotalNum(),18,RoundingMode.HALF_UP).multiply(p11.getPosNum()) );
					}
				}

                p11.setAccuamt(p11.getAccuamt().setScale(6, RoundingMode.HALF_UP));
                list.add(p11);
            } else {
				if (p11.getTotalNum() != null && p11.getTotalNum().compareTo(BigDecimal.ZERO) != 0){
					p11.setAccuamt( p11.getAccuamt().multiply(p11.getPosNum().divide(p11.getTotalNum(),18,RoundingMode.HALF_UP)));
				}
                p11.setPlanAssetId(p11.getAssetCode());
                list.addAll(genMonthPlanPosition(p11));
            }
        }
        return list;
    }
    
    /**
     * 功能:根据资产端银行月度报表头寸生成资产端银行间月度报表数据
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年1月31日
     */
    public void generateAssetMonthReportDetail(ChbMonthReport report,Boolean ifTrigger) {
//    	Date bookdate=report.getStatusDate();
    	//月报下个月初统计上个月的
    	// 月度第一天和月度最后一天
        Date firstday = report.getStartdate() ;
        Date	 lastday = report.getEnddate();
//        // 获取前月的第一天
        // 查询月度日期之间的资产头寸
        logger.info("查询本月月报资产头寸开始：");
//			List<ChbMonthAssetPosition> assetMonthPositionList = chbMonthAssetPositionMapper.selectByMonthDate(DateUtil.
//        		format(firstday),DateUtil.format(lastday),report.getProdids());
		List<ChbMonthAssetPosition> assetMonthPositionList = chbMonthAssetPositionMapper.selectByMonthDateAll(DateUtil.
				format(firstday),DateUtil.format(lastday),report.getProdids());
        logger.info("查询本月月报资产头寸结束：");
        // 用于统计穿透前期末余额 穿透后期末余额,key=propertyCode
        Map<String,ChbMonthReportDetail> assetMonthReportDetailMap = new HashMap<String,ChbMonthReportDetail>();
        if (!CollectionUtils.isEmpty(assetMonthPositionList)){
        	// 根据配置属性自动汇总相应属性值
        	  logger.info("根据配置属性自动汇总相应属性值开始：");
        	assetMonthReportDetailMap = countMonthReportAmtInvest(assetMonthPositionList,firstday,report,ifTrigger);
        	  logger.info("根据配置属性自动汇总相应属性值结束：");
	        // 将处理后的数据放入带插入列表中
//	        if (assetMonthReportDetailMap != null 
//	        		&& assetMonthReportDetailMap.size() != 0){
//	            // 资产端银行间月度报表带插入列表数据
//	            List<ChbMonthReportDetail> assetMonthReportDetailList = new ArrayList<ChbMonthReportDetail>();
//		        for (Map.Entry<String, ChbMonthReportDetail> entry : assetMonthReportDetailMap.entrySet()) {
//		        	ChbMonthReportDetail assetValue=entry.getValue();
//		        	assetValue.setReportuuid(report.getReportuuid());
//		        	assetMonthReportDetailList.add(assetValue);
//		        }
//		        // 插入数据,先删后插
//		        if (!CollectionUtils.isEmpty(assetMonthReportDetailList)){
//		        	// 资产端银行月度报表明细数据
//		        	if(report.getWbsreportuuid()!=null&&!"".equals(report.getWbsreportuuid())){
//		        		chbMonthReportDetailMapper.deleteByTermId(assetMonthReportDetailList.get(0).getTermid(),
//		        				ChbMonthReportPrdConstants.REPORT_TYPE_ASSET,report.getWbsreportuuid());
//		        	}
//		        			
//		        	chbMonthReportDetailMapper.insertBatch(assetMonthReportDetailList);
//		        	// 插入资产端银行月度报表
//		        	ChbMonthReport chbMonthReport = new ChbMonthReport();
//		        	chbMonthReport.setReportuuid(IDGenerator.generate(36));
//		        	chbMonthReport.setTermid(assetMonthReportDetailList.get(0).getTermid());
//		        	chbMonthReport.setReporttype(ChbMonthReportPrdConstants.REPORT_TYPE_ASSET);
//		        	chbMonthReport.setStartdate(firstday);
//		        	chbMonthReport.setEnddate(lastday);
//		        	// 发行机构代码取值
//		        	chbMonthReport.setIssuePartyId(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_ID));
//		        	chbMonthReport.setIssuePartyName(SvrDataUtil.getSysParam(SVReportGlobalConstant.SVR_ISSUE_PARTY_NAME));
//		        	// 月报
//		        	chbMonthReport.setReportfrequ(ChbMonthReportPrdConstants.REPORT_FREQU_M);
//	                // 报送时点通用方法
//	                // 当前批次报送时点集合
//		        }
//	        }
        } 
    }
    
    /**
     * 功能:统计期末资金理财投向情况
     * @since 1.6.0
     * @version 1.6.0
     * @author：weiyuqiang 2019年1月31日
     * @param assetMonthPositionList 
     * @param reportStartDate 月报开始日期 如：201801
     * void
     */
    private Map<String,ChbMonthReportDetail> countMonthReportAmtInvest(List<ChbMonthAssetPosition> assetMonthPositionList
    		,Date reportStartDate,ChbMonthReport report,boolean ifTrigger) {

		 Map<String,ChbPrdBaseinfo> prodMap = new HashMap<>();
		 Map<String,ChbAssetInfo> assetMap = new HashMap<>();
		 List<ChbMonthAssetPositiondetail> assetDetailList = new ArrayList();

    	// 规则项检查
    	if (!checkRuleIsRight(true)){
    		return null;
    	}
    	//非信用债
//    	String fxyz="1101,1110,1109,1111,1102";
    	//信用债
    	String xyz="1112,1113,1114,1115,1116,1117";
        // 用于统计穿透前期末余额 穿透后期末余额,key=propertyCode
        Map<String,ChbMonthReportDetail> assetMonthReportDetailMap = new HashMap<String,ChbMonthReportDetail>();
        
        List<ChbMonthReportTemplate> listKey = chbMonthReportTemplateMapper.select("PROD");
        
        /**
		 * 分支机构汇总类
		 */
		Map<String,Map<String,ChbMonthReportDetail>> deptMap = new HashMap();

		Set<String> assetList = assetMonthPositionList.stream().map(ChbMonthAssetPosition::getAssetCode).collect(Collectors.toSet());
		//清除可能出现的null的科目
		assetList.remove(null);
		/***获取资产信息列表**/
		if(null!=assetList&&assetList.size()>0){
			List<ChbAssetInfo> assetInfoList = chbMonthAssetPositionMapper.selectChbAssetInfoByAssetCodeList(assetList);
			assetMap = null!=assetInfoList?assetInfoList.stream().collect(Collectors.toMap(p->p.getAssetCode(), p-> p,(key1, key2)->key1)):new HashMap<>();
		}
		/***获取产品信息列表**/
		List<ChbPrdBaseinfo> prdBaseinfoList = chbPrdBaseinfoMapper.selectAllProd(null);
		prodMap = null!=prdBaseinfoList?prdBaseinfoList.stream().collect(Collectors.toMap(p->p.getProdId(), p-> p,(key1,key2)->key1)):new HashMap<>();


		// 根据配置规则汇总相应数据
//        int count=0;
        for (ChbMonthAssetPosition position :assetMonthPositionList){
        	
        	
        	/**
			 * 数据是否包含机构并且当前是否是联社模式
			 */
			if(null!=position.getOrgId()&&!deptMap.containsKey(position.getOrgId())&&CommonUtil.isBAmodel()){
				
				
				//各机构信息新建并保存
				//ADD BY LEIPENG 20200708 如果产品未建主表.则资产新建
				ChbMonthReport prdReport = monthReportServiceUtil.queryMonthReport(report.getTermid(), position.getOrgId(),"01");
				
				if(null == prdReport){
					report.setOrgCode(position.getOrgId());
					monthReportServiceUtil.buildNewReport(report, ifTrigger);
				}
//				//为主表填入所有统计项
//				DetailListBuild(listKey, sdf.format(reportStartDate), Instresult);
//				//各机构对应的详情信息保存
//				Map<String, ChbMonthReportDetail> Instresult = new HashMap<String, ChbMonthReportDetail>();
				deptMap.put(position.getOrgId(), new HashMap<String,ChbMonthReportDetail>());
			}
        	
//        	count++;
        	for (ChbMonthAssetRule singleRule :ChbMonthAssetRule.values()){
        		// 合计值单独处理
        		if (singleRule.getPropertyName().contains("合计")){
    				// 判断key值是否已存在，已存在则跳过
    				if(null!=deptMap.get(position.getOrgId())&&deptMap.get(position.getOrgId()).containsKey(singleRule.getPropertyCode()+"合计")){
    					continue;
    				}else {

    					//汇总表记录总数
    					if (!assetMonthReportDetailMap.containsKey(singleRule.getPropertyCode()+"合计")){
        					assetMonthReportDetailMap.put(singleRule.getPropertyCode()+"合计", 
        							buildDetail(position,false,reportStartDate, singleRule.getPropertyCode(), BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO));
        				}
    					//保存分支的合计数据
    					if(null!=deptMap.get(position.getOrgId())){
    						//拷贝一份相同数据并传入当前机构分支数据中
    						deptMap.get(position.getOrgId()).put(singleRule.getPropertyCode()+"合计", 
    								buildDetail(position,false,reportStartDate, singleRule.getPropertyCode(), BigDecimal.ZERO, BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO));

    					}
    				}
        		}
        		// 根据配置规则代码匹配资产头寸中相应属性
        		if (!StringUtil.isEmptyList(singleRule.getRuleCodeList())
        				&& !StringUtil.isEmptyList(singleRule.getRuleValueList())){
        			// 该笔头寸记录是否匹配配置规则
        			boolean isPattern = true;
        			// 判断是否多条件交集处理
	        		for (int i = 0; i < singleRule.getRuleCodeList().size(); i++){
	        			// 重新初始化是否匹配配置规则标记
	        			isPattern = true;
	        			// 债券信用评级
	        			if (singleRule.getRuleCodeList().get(i).toUpperCase().equals("RATINGAA+")){
	        				if (!xyz.contains(position.getAssettype2())||position.getRating() == null||compareRating(position.getRating(),singleRule.getRuleValueList().get(i)) < 0
	        						 ){
	        					isPattern = false;
		        				break;
	        				} 
	        			} else if (singleRule.getRuleCodeList().get(i).toUpperCase().equals("RATINGAA-")){
	        				if (!xyz.contains(position.getAssettype2())||position.getRating() == null||compareRating(position.getRating(),singleRule.getRuleValueList().get(i)) >= 0
	        						 ){
	        					isPattern = false;
		        				break;
	        				} 
	        			} else if (singleRule.getRuleCodeList().get(i).toUpperCase().equals("RATINGNULL")){
	        				if (!xyz.contains(position.getAssettype2())||position.getRating() != null){
	        					isPattern = false;
		        				break;
	        				} 
	        			}  else { // 非债券信用评级
	        				// 判断是否多条件任取其一，即rulecode和value一一对应以“|”隔开，格式为code1|code2,value1|value2
	        				if (singleRule.getRuleCodeList().get(i).contains(GlobalConstant.VERTICAL_LINE)
	        						|| singleRule.getRuleValueList().get(i).contains(GlobalConstant.VERTICAL_LINE)){
	        					
	        					List<String> ruleCodeList = StringUtil.splitToList(GlobalConstant.VERTICAL_LINE,
	        							singleRule.getRuleCodeList().get(i));
	        					List<String> valueList = StringUtil.splitToList(GlobalConstant.VERTICAL_LINE,
	        							singleRule.getRuleValueList().get(i));
	        					isPattern = false;
        						String ruleCode = ruleCodeList.get(0);
	        					for (int k = 0; k < valueList.size() ; k++){
	        						// 如果ruleCode相同但是多条件，则支持只输入一个code，如：code1,value1|value2|value3
	        						// 否则需一一对应以“|”隔开，格式为code1|code2,value1|value2
	        						if (ruleCodeList.size() > 1){
	        							ruleCode = ruleCodeList.get(k);
	        						}
	        						// 支持反向判断！value1
	        						if (valueList.get(k).startsWith("!")){
	        							// 只要有一条满足则该条头寸不计入
					        			if (!valueList.get(k).substring(1,valueList.get(k).length())
					        					.equals((String)getGetMethod(position,ruleCode))){
					        				isPattern = true;
					        				break;
					        			}
	        						} else {
	    		        				// 只有多个条件都不满足时该头寸才不计入
					        			if (valueList.get(k).equals((String)getGetMethod(position,ruleCode))){
					        				isPattern = true;
					        				break;
					        			}
	        						}
	        					}
	        					if (!isPattern){
	        						break;
	        					}
	        				}else {// 多条件同时满足才计入
        						// 支持反向判断！value1
        						if (singleRule.getRuleValueList().get(i).startsWith("!")){
        							// 只要有一条满足则该条头寸不计入
				        			if (singleRule.getRuleValueList().get(i).substring(1,
				        					singleRule.getRuleValueList().get(i).length())
				        					.equals((String)getGetMethod(position,
				        					singleRule.getRuleCodeList().get(i)))){
				        				isPattern = false;
				        				break;
				        			}
        						} else {
			        				// 只要有一条不满足则该条头寸不计入
				        			if (!singleRule.getRuleValueList().get(i)
				        					.equals((String)getGetMethod(position,singleRule.getRuleCodeList()
				        							.get(i)))){
				        				isPattern = false;
				        				break;
				        			}
        						}
	        				}
	        			}
	        		}
	        		
        			// 值域匹配则累计，值域不匹配则计0
    				// 判断key值是否已存在，已存在则累计，未存在则新建
	        		//	汇总值数据组装
    				if (assetMonthReportDetailMap.containsKey(singleRule.getPropertyCode())
    						||  assetMonthReportDetailMap.containsKey(singleRule.getPropertyCode()+"合计")){					
    					// 非合计值
    					if (assetMonthReportDetailMap.containsKey(singleRule.getPropertyCode())){
        					ChbMonthReportDetail detail = assetMonthReportDetailMap.get(singleRule.getPropertyCode());
        					// 已存在计入本次头寸值，否则不计入本次头寸值
                			if (isPattern){
    	    					// 穿透前科目余额 = 穿透前科目余额 + 新增值
    	    					detail.setBalance(detail.getBalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
    	    					// 穿透后科目余额 = 穿透后科目余额 + 新增值
    	    					detail.setBottombalance(detail.getBottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));
                                /**G06资产端改造  add by  dongxiangyang  start*/

                                //合规穿透前科目余额 = 合规穿透前科目余额 + （【是否合规产品】 = 是 【新增穿透前值】 else 【0】）
								if(StringUtil.equals(DictCodeListExt1.新老产品标记_新产品.getValue(),position.getIsFhxgcp())){
									detail.setXgbalance(detail.getXgbalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
									detail.setXgbottombalance(detail.getXgbottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));
								}else{
									//合规穿透后科目余额 = 合规穿透后科目余额 + 【是否合规产品】 = 是 【新增穿透后值】 else 【0】

									detail.setXgbalance(detail.getXgbalance().add(BigDecimal.ZERO));
									detail.setXgbottombalance(detail.getXgbottombalance().add(BigDecimal.ZERO));
								}
								//每次合計數據均需要計入
								ChbMonthAssetPositiondetail assetDetail = ChbMonthReportDetailAssetUtil.buildAssetDetail(position,prodMap.get(position.getProdId()),assetMap.get(position.getAssetCode()),detail);
								if(null!=assetDetail){
									assetDetailList.add(assetDetail);
								}

								/**G06资产端改造  add by  dongxiangyang  end*/

    	    					// 场外(内)股票质押融资（穿透后）计行内一级分类IN_ASSET_TYPE1=01场内股票质押回购的资产价值ASSETVALUE_B+ASSETVALUE_A（除一级分类=资管，委外，私募）
//    	    					if (singleRule.equals(ChbMonthAssetRule.场内股票质押回购)
//    	    							|| singleRule.equals(ChbMonthAssetRule.场外股票质押融资)){
//    	    						if (!position.getAssettype().equals(SVReportDictCodeList.资产负债类别_委外投资协议方式)
//    	    								&&!position.getAssettype().equals(SVReportDictCodeList.资产负债类别_私募基金)
//    	    								&&!position.getAssettype().equals(SVReportDictCodeList.资产负债类别_资产管理产品)){
//    	    							detail.setBottombalance(detail.getBottombalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
//    	    						}
//    	    					}

                			}
    					} else if (assetMonthReportDetailMap.containsKey(singleRule.getPropertyCode() + "合计")){
        					ChbMonthReportDetail detail = assetMonthReportDetailMap.get(singleRule.getPropertyCode()+"合计");
        					// 已存在计入本次头寸值，否则不计入本次头寸值
                			if (isPattern){
    	    					// 穿透前科目余额 = 穿透前科目余额 + 新增值
    	    					detail.setBalance(detail.getBalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
    	    					// 穿透后科目余额 = 穿透后科目余额 + 新增值
    	    					detail.setBottombalance(detail.getBottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));
								/**G06资产端改造  add by  dongxiangyang  start*/

								//合规穿透前科目余额 = 合规穿透前科目余额 + （【是否合规产品】 = 是 【新增穿透前值】 else 【0】）
								if(StringUtil.equals(DictCodeListExt1.新老产品标记_新产品.getValue(),position.getIsFhxgcp())){
									detail.setXgbalance(detail.getXgbalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
									detail.setXgbottombalance(detail.getXgbottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));
								}else{
									//合规穿透后科目余额 = 合规穿透后科目余额 + 【是否合规产品】 = 是 【新增穿透后值】 else 【0】
									detail.setXgbalance(detail.getXgbalance().add(BigDecimal.ZERO));
									detail.setXgbottombalance(detail.getXgbottombalance().add(BigDecimal.ZERO));
								}
								/**G06资产端改造  add by  dongxiangyang  end*/
                			}
    					}
    				} else {
    					
    					BigDecimal balance = BigDecimal.ZERO;
    					
    					BigDecimal bottombalance = BigDecimal.ZERO;

						BigDecimal xgbalance = BigDecimal.ZERO;

						BigDecimal xgbottombalance = BigDecimal.ZERO;

//    					ChbMonthReportDetail detail = new ChbMonthReportDetail();
//    					// uuid
//    					detail.setDetailuuid(IDGenerator.generate(36));
//    					// 期数
//    					detail.setTermid(DateUtil.format(reportStartDate, "yyyyMM"));
//    					// 报表类型：BALANCE-资产负债表
//    					detail.setReporttype(ChbMonthReportPrdConstants.REPORT_TYPE_ASSET);
//    					// 属性代码
//    					detail.setPropertycode(singleRule.getPropertyCode());
    					// 计入本次头寸值
            			if (isPattern){
            				balance = position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA();
            				
            				bottombalance = position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB();

							if(StringUtil.equals(DictCodeListExt1.新老产品标记_新产品.getValue(),position.getIsFhxgcp())){
								xgbalance=position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA();

								xgbottombalance = position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB();
							}
            				// 期末余额穿透前 = 新增值
//	    					detail.setBalance(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA());
//	    					// 期末余额穿透后 = 新增值
//	    					detail.setBottombalance(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB());
	    					// 场外(内)股票质押融资（穿透后）计行内一级分类IN_ASSET_TYPE1=01场内股票质押回购的资产价值ASSETVALUE_B+ASSETVALUE_A（除一级分类=资管，委外，私募）
//	    					if (singleRule.equals(ChbMonthAssetRule.场内股票质押回购)
//	    							|| singleRule.equals(ChbMonthAssetRule.场外股票质押融资)){
//	    						if (!position.getAssettype().equals(SVReportDictCodeList.资产负债类别_委外投资协议方式)
//	    								&&!position.getAssettype().equals(SVReportDictCodeList.资产负债类别_私募基金)
//	    								&&!position.getAssettype().equals(SVReportDictCodeList.资产负债类别_资产管理产品)){
//	    							detail.setBottombalance(detail.getBottombalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
//	    						}
//	    					}
							ChbMonthAssetPositiondetail assetDetail = ChbMonthReportDetailAssetUtil.buildAssetDetail(position,prodMap.get(position.getProdId()),assetMap.get(position.getAssetCode()),
									buildDetail(position,true,reportStartDate, singleRule.getPropertyCode(), balance, bottombalance,xgbalance,xgbottombalance));
							if(null!=assetDetail){
								assetDetailList.add(assetDetail);
							}
						}
            			
            			//如果汇总项无此合计项则填入
            			assetMonthReportDetailMap.put(singleRule.getPropertyCode(), 
								buildDetail(position,true,reportStartDate, singleRule.getPropertyCode(), balance
										, bottombalance,xgbalance,xgbottombalance));
            			
            			
//    					assetMonthReportDetailMap.put(singleRule.getPropertyCode(), detail);
    				}
    				
    				//update by leipeng 银行模式下报错修改
    				if(null!=deptMap.get(position.getOrgId())){
	    				//独立机构部分数据组装
	    				if (null!=deptMap.get(position.getOrgId())&&
	    						(deptMap.get(position.getOrgId()).containsKey(singleRule.getPropertyCode())||
	    								deptMap.get(position.getOrgId()).containsKey(singleRule.getPropertyCode()+"合计"))){					
	    					// 非合计值
	    					if (deptMap.get(position.getOrgId()).containsKey(singleRule.getPropertyCode())){
	        					ChbMonthReportDetail detail = deptMap.get(position.getOrgId()).get(singleRule.getPropertyCode());
	        					// 已存在计入本次头寸值，否则不计入本次头寸值
	                			if (isPattern){
	    	    					// 穿透前科目余额 = 穿透前科目余额 + 新增值
	    	    					detail.setBalance(detail.getBalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
	    	    					// 穿透后科目余额 = 穿透后科目余额 + 新增值
	    	    					detail.setBottombalance(detail.getBottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));

									/**G06资产端改造  add by  dongxiangyang  start*/

									//合规穿透前科目余额 = 合规穿透前科目余额 + （【是否合规产品】 = 是 【新增穿透前值】 else 【0】）
									if(StringUtil.equals(DictCodeListExt1.新老产品标记_新产品.getValue(),position.getIsFhxgcp())){
										detail.setXgbalance(detail.getXgbalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
										detail.setXgbottombalance(detail.getXgbottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));
									}else{
										//合规穿透后科目余额 = 合规穿透后科目余额 + 【是否合规产品】 = 是 【新增穿透后值】 else 【0】
										detail.setXgbalance(detail.getXgbalance().add(BigDecimal.ZERO));
										detail.setXgbottombalance(detail.getXgbottombalance().add(BigDecimal.ZERO));
									}
									/**G06资产端改造  add by  dongxiangyang  end*/
	                			}
	    					} else if (deptMap.get(position.getOrgId()).containsKey(singleRule.getPropertyCode() + "合计")){
	        					ChbMonthReportDetail detail = deptMap.get(position.getOrgId()).get(singleRule.getPropertyCode()+"合计");
	        					// 已存在计入本次头寸值，否则不计入本次头寸值
	                			if (isPattern){
	    	    					// 穿透前科目余额 = 穿透前科目余额 + 新增值
	    	    					detail.setBalance(detail.getBalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
	    	    					// 穿透后科目余额 = 穿透后科目余额 + 新增值
	    	    					detail.setBottombalance(detail.getBottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));

									/**G06资产端改造  add by  dongxiangyang  start*/

									//合规穿透前科目余额 = 合规穿透前科目余额 + （【是否合规产品】 = 是 【新增穿透前值】 else 【0】）
									if(StringUtil.equals(DictCodeListExt1.新老产品标记_新产品.getValue(),position.getIsFhxgcp())){
										detail.setXgbalance(detail.getXgbalance().add(position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA()));
										detail.setXgbottombalance(detail.getXgbottombalance().add(position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB()));
									}else{
										//合规穿透后科目余额 = 合规穿透后科目余额 + 【是否合规产品】 = 是 【新增穿透后值】 else 【0】
										detail.setXgbalance(detail.getXgbalance().add(BigDecimal.ZERO));
										detail.setXgbottombalance(detail.getXgbottombalance().add(BigDecimal.ZERO));
									}
									/**G06资产端改造  add by  dongxiangyang  end*/
	                			}
	    					}
	    				} else {
	    					//新增一个数据集合
	    					BigDecimal balance = BigDecimal.ZERO;
	    					
	    					BigDecimal bottombalance = BigDecimal.ZERO;

							BigDecimal xgbalance = BigDecimal.ZERO;

							BigDecimal xgbottombalance = BigDecimal.ZERO;
	
	    					// 计入本次头寸值
	            			if (isPattern){
	
	            				balance = position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA();
	            				
	            				
	            				bottombalance = position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB();

								if(StringUtil.equals(DictCodeListExt1.新老产品标记_新产品.getValue(),position.getIsFhxgcp())){
									xgbalance=position.getAssetvalueA() == null?BigDecimal.ZERO:position.getAssetvalueA();

									xgbottombalance = position.getAssetvalueB() == null?BigDecimal.ZERO:position.getAssetvalueB();
								}

	            			}
	            			deptMap.get(position.getOrgId()).put(singleRule.getPropertyCode(), 
									buildDetail(position,false,reportStartDate, singleRule.getPropertyCode(), balance
											, bottombalance,xgbalance,xgbottombalance));
	    				}
    				}
    				
    				
        		}
        	}
        }
        
       
        //多级法人改造。不能出汇总数据，会导致分支机构数据删除错误
        //汇总数据合计
        sumDetail(assetMonthReportDetailMap);
        //汇总数据入库
        insertDetail(assetMonthReportDetailMap, report.getTermid(),null);
		chbMonthReportDetailAssetUtil.insertBatch(assetDetailList,report.getTermid(),report.getEnddate());

		//机构数据合计以及入库
        for (Entry<String, Map<String, ChbMonthReportDetail>> details : deptMap.entrySet()) {
        	sumDetail(details.getValue());
            insertDetail(details.getValue(), report.getTermid(),details.getKey());
		}
        
    	return assetMonthReportDetailMap;
    }
    
    /**
     * 数据合计方法
     * @param assetMonthReportDetailMap
     */
    public void sumDetail(Map<String,ChbMonthReportDetail> assetMonthReportDetailMap){
    	//总合计1.17单独计算
        BigDecimal balanceA=BigDecimal.ZERO;
        BigDecimal balanceB=BigDecimal.ZERO;
		BigDecimal balanceC=BigDecimal.ZERO;
		BigDecimal balanceD=BigDecimal.ZERO;


		// 处理合计值
        for (Entry<String,ChbMonthReportDetail> detail : assetMonthReportDetailMap.entrySet()){
        	if (detail.getKey().contains("合计")){
        		
        		// 获取与合计值匹配的properTycode:AMT1_7_11_
        		String properTycodeSumRule = detail.getKey().substring(0,detail.getKey().lastIndexOf("_") + 1);
        		// 期末余额穿透前
        		BigDecimal balance = BigDecimal.ZERO;
        		// 期末余额穿透后
        		BigDecimal bottombalance = BigDecimal.ZERO;

				BigDecimal xgbalance = BigDecimal.ZERO;

				BigDecimal xgbottombalance = BigDecimal.ZERO;

				// AMT2_3特殊处理，此处不会计算AMT2_1_3故另外计算
        		if (detail.getKey().startsWith("AMT2_3")){
        			if (assetMonthReportDetailMap.containsKey("AMT2_1_3合计")){
        				ChbMonthReportDetail detailAmt213= assetMonthReportDetailMap.get("AMT2_1_3合计");
        				balance = balance.add(detailAmt213.getBalance());
        				bottombalance = bottombalance.add(detailAmt213.getBottombalance());
						xgbalance=xgbalance.add(detailAmt213.getXgbalance());
						xgbottombalance = xgbottombalance.add(detailAmt213.getXgbottombalance());

					} else if (assetMonthReportDetailMap.containsKey("AMT2_1_3")){
        				ChbMonthReportDetail detailAmt213= assetMonthReportDetailMap.get("AMT2_1_3");
        				balance = balance.add(detailAmt213.getBalance());
        				bottombalance = bottombalance.add(detailAmt213.getBottombalance());
						xgbalance=xgbalance.add(detailAmt213.getXgbalance());
						xgbottombalance = xgbottombalance.add(detailAmt213.getXgbottombalance());
        			}
        		}
        		for (Entry<String,ChbMonthReportDetail> detail2 : assetMonthReportDetailMap.entrySet()){
        			// 计算合计值，匹配规则如下：
        			// 1.AMT1_1_1合计 则统计以AMT1_1_开头的
        			// 2.且统计值的最后一个"_"的索引不能大于所需计算的合计值的最后一个"_"
        			// 3.不以_a或_b结尾
        			// 以合计为开头且长度小于等于合计(去除不同纬度)
        			if (detail2.getKey().startsWith(properTycodeSumRule)
        					&& detail2.getKey().lastIndexOf("_") <= detail.getKey().lastIndexOf("_")
        					&& !detail2.getKey().endsWith("_a")
        					&& !detail2.getKey().endsWith("_b")
        					&& !detail2.getKey().endsWith("_c")
        					&& !detail2.getKey().equals(detail.getKey())){
        				balance = balance.add(detail2.getValue().getBalance());
        				bottombalance = bottombalance.add(detail2.getValue().getBottombalance());
						xgbalance = xgbalance.add(detail2.getValue().getXgbalance());
						xgbottombalance = xgbottombalance.add(detail2.getValue().getXgbottombalance());
        			}
        		}
        		ChbMonthReportDetail chbSumDetail = detail.getValue();
        		chbSumDetail.setBalance(balance);
        		chbSumDetail.setBottombalance(bottombalance);
				chbSumDetail.setXgbalance(xgbalance);
				chbSumDetail.setXgbottombalance(xgbottombalance);

				if (detail.getKey().startsWith("AMT1_")){
        			
        			
        			// 计算其他合计累计值
        			balanceA = balanceA.add(balance);
					balanceC = balanceC.add(xgbalance);

					// 去除隐藏列的b列值
        			if (!detail.getKey().startsWith("AMT1_12_9")
        					&& !detail.getKey().startsWith("AMT1_13_7")
        					&& !detail.getKey().startsWith("AMT1_14_8")){
            			balanceB = balanceB.add(bottombalance);
						balanceD= balanceD.add(xgbottombalance);

					}
        		} 
        		
        		detail.setValue(chbSumDetail);
        	}
        }
        // 计算合计值ATM1_17
        ChbMonthReportDetail atm117 = assetMonthReportDetailMap.get("AMT1_15合计");
        atm117.setBalance(balanceA);
        atm117.setBottombalance(balanceB);
		atm117.setXgbalance(balanceC);
		atm117.setXgbottombalance(balanceD);

	}
    
    
    
    
    /**
     * 根据获取的参数进行详细信息数据插入
     * @param assetMonthReportDetailMap
     * @param report
     */
    public void insertDetail(Map<String,ChbMonthReportDetail> assetMonthReportDetailMap,String termid,String orgCode){
        if (assetMonthReportDetailMap != null 
        		&& assetMonthReportDetailMap.size() != 0){
        	
        	ChbMonthReport reportAss = monthReportServiceUtil.queryMonthReportForAsset(termid, orgCode);
        	
            // 资产端银行间月度报表带插入列表数据
            List<ChbMonthReportDetail> assetMonthReportDetailList = new ArrayList<ChbMonthReportDetail>();
            for (Map.Entry<String, ChbMonthReportDetail> entry : assetMonthReportDetailMap.entrySet()) {
            	ChbMonthReportDetail assetValue=entry.getValue();
            	assetValue.setDetailuuid(UUID.randomUUID().toString());
            	assetValue.setReportuuid(reportAss.getReportuuid());
            	assetMonthReportDetailList.add(assetValue);
            }
            // 插入数据,先删后插
            if (!CollectionUtils.isEmpty(assetMonthReportDetailList)){
            	// 资产端银行月度报表明细数据
//            	ChbMonthReport delreport = monthReportServiceUtil.queryMonthReport(termid, orgCode);
//            	
//            	if(null!=delreport&&delreport.getWbsreportuuid()!=null&&!"".equals(delreport.getWbsreportuuid())){
//            		chbMonthReportDetailMapper.deleteByTermId(assetMonthReportDetailList.get(0).getTermid(),
//            				ChbMonthReportPrdConstants.REPORT_TYPE_ASSET,delreport.getWbsreportuuid());
//            	}
            	//由于多次执行插入操作..目前由汇总数据进行一次资产详情删除...分支数据不进行删除
				chbMonthReportDetailMapper.deleteByTermIdEx(termid,
						ChbMonthReportPrdConstants.REPORT_TYPE_ASSET,orgCode);
            	
            			
            	chbMonthReportDetailMapper.insertBatch(assetMonthReportDetailList);
            }
        }
    	
    }

	/**
	 *
	 * @param position 資產頭寸訊息
	 * @param flag  銀行模式數據為true, 合計及聯社數據flase
	 * @param reportStartDate 開始時間
	 * @param properCode 結束時間
	 * @param balance
	 * @param bottombalance
	 * @param xgbalance
	 * @param xgbottombalance
	 * @return
	 */
    public ChbMonthReportDetail buildDetail(ChbMonthAssetPosition position, boolean flag,Date reportStartDate,String properCode,BigDecimal balance,BigDecimal bottombalance,
	     BigDecimal xgbalance,BigDecimal xgbottombalance){
    	ChbMonthReportDetail detail = new ChbMonthReportDetail();
		// uuid
		detail.setDetailuuid(IDGenerator.generate(36));
		// 期数
		detail.setTermid(DateUtil.format(reportStartDate, "yyyyMM"));
		// 报表类型：BALANCE-资产负债表
		detail.setReporttype(ChbMonthReportPrdConstants.REPORT_TYPE_ASSET);
		// 属性代码
		detail.setPropertycode(properCode);
		// 期末余额穿透前 = 新增值
		detail.setBalance(balance);
		// 期末余额穿透后 = 新增值
		detail.setBottombalance(bottombalance);
		/**新增合规穿透代码字段 start  by dongxiangyang */
        //dto.合规穿透前金额 = 入参.合规穿透前金额
        detail.setXgbalance(xgbalance);
        //dto.合规穿透后金额 = 入参.合规穿透后金额
		detail.setXgbottombalance(xgbottombalance);
		/**新增合规穿透代码字段 start  by dongxiangyang */
		return detail;
    }
    

	/**
	 * 根据属性，获取get方法(不区分大小写)
	 * @param ob 对象
	 * @param name 属性名
	 * @return
	 */
	private Object getGetMethod(Object ob , String name){
		Method[] m = ob.getClass().getMethods();
		for(int i = 0;i < m.length;i++){
			if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
				try {
                    return m[i].invoke(ob);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error(e.getMessage(),e);
                    return null;
                }
			}
		}
		return null;
	}
	
	/**
	 * 功能:检查匹配规则是否完善
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年1月31日
	 * @return 
	 * boolean
	 */
	private boolean checkRuleIsRight(boolean isIncludeNull){
		// 检查配置规则项
    	for (ChbMonthAssetRule singleRule :ChbMonthAssetRule.values()){
    		// 检查属性代码是否为空
    		if (StringUtil.isEmpty(singleRule.getPropertyCode())){
    			logger.error("请检查属性代码是否为空！");
    			return false;
    		}
    		if (!isIncludeNull){
	    		// 检查配置规则代码域与配置规则值域是否为空,支持为空时请关闭此项
	    		if (StringUtil.isEmptyList(singleRule.getRuleCodeList())
	    				|| StringUtil.isEmptyList(singleRule.getRuleValueList())){
	    			logger.error("请检查置规则代码域与配置规则值域是否为空！");
	    			return false;
	    		}
    		}
    		// 判断配置规则代码域与配置规则值域是否大小一致，即一一对应
    		if (singleRule.getRuleCodeList().size() != singleRule.getRuleValueList().size()){
    			logger.error("请检查属性代码:"+singleRule.getPropertyCode()+"对应规则值域与对应规则代码域大小是否对应一致！");
    			return false;
    		}
    	}
    	return true;
	}
	
	/**
	 * 功能:判断评级信息大小
	 * @since 1.6.0
	 * @version 1.6.0
	 * @author：weiyuqiang 2019年2月11日
	 * @param s1 待比较评级结果
	 * @param s2 评级比较标准    S2固定只能为AA+或AA-
	 * @return >0 s1>s2 =0 s1 = s2 <0 s1<s2
	 */
	private int compareRating(String s1,String s2){
//        int len1 = s1.length();
//        int len2 = s2.length();
//        int lim = Math.min(len1, len2);
//        char v1[] = s1.toCharArray();
//        char v2[] = s2.toCharArray();
//
//        int k = 0;
//        while (k < lim) {
//            char c1 = v1[k];
//            char c2 = v2[k];
//            if (c1 != c2) {
//                return c2 - c1;
//            }
//            k++;
//        }
//        return len1 - len2;
		if(s1.equals(s2)){
    		return 0;
    	}
    	if(s2.equals("AA+")){
    		if(s1.length()<s2.length()){
    			return -1;
    		}else{
    			if(s1.substring(0, 3).equals(s2)||s1.substring(0, 3).equals("AAA")){
    				return 1;
    			}else{
    				return -1;
    			}
    			
    		}
    	}else{
    		if(s1.length()<s2.length()){
    			return -1;
    		}else{
    			if(s1.substring(0, 3).equals(s2)||s1.substring(0, 3).equals("AAA")||s1.substring(0, 3).equals("AA+")){
    				return 1;
    			}else{
    				return -1;
    			}
    			
    		}
    	}
	}

	// add by qfx 2021-04-22 新增理财子公司月报资产头寸字段设置 start
	/**
	 * 计算期限
	 *
	 * @param report 报送参数
	 * @param prodTermMap 产品期限Map
	 * @param position 资产头寸
	 * @param chbAssetInfo 资产信息
	 * @param supAssetInfo 上层资产信息
	 * @return 期限
	 */
	private BigDecimal newCalcTerms(ChbMonthReport report, Map<String, BigDecimal> prodTermMap,
									ChbMonthAssetPosition position, ChbAssetInfo chbAssetInfo, ChbAssetInfo supAssetInfo) {
		BigDecimal terms = null;
		// 设置期限字段
		boolean isCalcFlag = true;//后续判定是否需要再计算
		//公私募基金 15 19 金融衍生品 07 权益类资产 06 无到期日 默认为 1
		if (!("12".equals(position.getAssettype()) || "16".equals(position.getAssettype())) && chbAssetInfo.getMdate() == null) {
			terms = new BigDecimal(1);
			isCalcFlag = false;//后续判定是否需要再计算

			//所有资产mdate != null && mdate<月报最后一天（逾期资产）,该字段默认=0
		}else if(chbAssetInfo.getMdate() != null && DateUtil.getBetweenDayNumber(report.getEnddate(), chbAssetInfo.getMdate()) < 0){
			terms = BigDecimal.ZERO;
			isCalcFlag = false;//后续判定是否需要再计算

		} else {
			boolean prdFlag = false;
			//asset_type in（'12','16'） 取plan_edate
			if ("12".equals(position.getAssettype()) || "16".equals(position.getAssettype())) {
				if(chbAssetInfo.getPlanEdate() != null) {
					terms = BigDecimal.valueOf(DateUtil.getBetweenDayNumber(report.getEnddate(), chbAssetInfo.getPlanEdate()));
				}
				else if(null != supAssetInfo && supAssetInfo.getPlanEdate() != null) {
					terms = BigDecimal.valueOf(DateUtil.getBetweenDayNumber(report.getEnddate(), supAssetInfo.getPlanEdate()));
				}
			}
			else {
				if(chbAssetInfo.getMdate() != null) {
					terms = BigDecimal.valueOf(DateUtil.getBetweenDayNumber(report.getEnddate(), chbAssetInfo.getMdate()));
				}
				else if(null != supAssetInfo && supAssetInfo.getMdate() != null) {
					terms = BigDecimal.valueOf(DateUtil.getBetweenDayNumber(report.getEnddate(), supAssetInfo.getMdate()));
				}
			}
			// 如果期限还是null，则需要设置为产品期限
			if(terms == null || BigDecimal.ZERO.compareTo(terms) >=0 ) {
				// 如果之前已经计算过产品期限，则直接取值
				if (prodTermMap.containsKey(position.getProdId())) {
					terms = prodTermMap.get(position.getProdId());
					isCalcFlag = false;//后续判定是否需要再计算
				}
				// 否则需要计算产品期限
				else {
					// 获取产品的到期日（封闭式产品：产品到期日；定期开放式产品：下一开放日，高频开放式产品：直接默认期限为1）
					ChbPrdBaseinfo chbPrdBaseinfo = chbMonthAssetPositionMapper.selectChbPrdBaseinfoByProdId(position.getProdId(), report.getEnddate());
					if(chbPrdBaseinfo == null || chbPrdBaseinfo.getMdate() == null) {
						terms = BigDecimal.ONE;
						isCalcFlag = false;//后续判定是否需要再计算
					}
					else {
						terms = BigDecimal.valueOf(DateUtil.getBetweenDayNumber(report.getEnddate(), chbPrdBaseinfo.getMdate()));
					}
					prodTermMap.put(position.getProdId(), terms);
				}
			}
		}
		// 剩余期限计算结果若小于等于0，则该字段值=1；
		if (isCalcFlag && (terms == null || terms.compareTo(BigDecimal.ZERO) <= 0)) {
			terms = BigDecimal.ONE;
			prodTermMap.put(position.getProdId(), terms);
		}
		position.setTerms(terms);
		return terms;
	}
	// add by qfx 2021-04-22 新增理财子公司月报资产头寸字段设置 start
    
}
