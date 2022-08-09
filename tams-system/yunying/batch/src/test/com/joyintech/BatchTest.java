package com.joyintech;

import com.joyintech.framework.base.dao.SysBatchGrpMapper;
import com.joyintech.framework.base.dao.SysBatchPrdGrpMapper;
import com.joyintech.framework.base.dao.SysBatchTaskMapper;
import com.joyintech.framework.base.dao.SysBatchgrpExecInfoMapper;
import com.joyintech.framework.base.dto.Product;
import com.joyintech.framework.base.entity.*;
import com.joyintech.framework.constant.BatchDictCodeList;
import com.joyintech.framework.constant.DictCodeList;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.jdbc.datasource.DynamicDataSourceHolder;
import com.joyintech.framework.jdbc.enums.DataSourceType;
import com.joyintech.framework.security.LoginUserUtil;
import com.joyintech.framework.security.MultilevelDataAuthorityUtil;
import com.joyintech.framework.util.*;
import com.joyintech.tams.common.base.dao.BokBooksetDateMapper;
import com.joyintech.tams.common.base.entity.BokBookset;
import com.joyintech.tams.common.base.entity.BokBooksetDate;
import com.joyintech.tams.valuation.common.dao.BaseCommonMapper;
import com.joyintech.tams.valuation.common.dao.BooksetComMapper;
import com.joyintech.tams.valuation.system.batchstart.dao.ValBatchStart001Mapper;
import com.joyintech.tams.valuation.system.batchstart.dto.BatchGrpPri;
import com.joyintech.tams.valuation.system.batchstart.dto.DimensionValue;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BatchTest {

    public static final String BATCH_DISPATCH = "100";

    public static final String BATCH_UNISTART = "999999";

    public static final String BATCH_UNISTART_WITHOUT_DATECHANGE = "999998";
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(BatchTest.class);

    ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

    @Test
    public void test()  {
        //context.xml中修改数据库配置
        //执行批处理
        //executeBatch(批处理任务id,产品代码,开始日期,结束日期,任务组模式：0是任务组 1是任务,产品组模式：0是产品组 1是产品 )
        //例如executeBatch("10003,10002","DGJ001,JZX0607CP,FFJZ0010","2020-08-23","2020-08-23","1","1")
        //SYS_BATCH_PRD_GRP查询产品组流水号
        // executeBatch("","","","","","");
        executeBatch("3033","null","2021-06-30","2021-06-30","1","1");
        //executeBatch("val","30ecd23dda8c486caed25d3017c4c37b","2020-05-31","2020-05-31","0","0");

    }


    /**
     * 执行批处理
     * <p>
     * selectedBatchGroup
     * 批处理组seqNo,batchGroupType为BATCH_EXECUTE_TYPE_SINGLE时是任务taskId
     * selectedProductGroup
     * 产品组seqNo,prodGroupType为BATCH_EXECUTE_TYPE_SINGLE时是产品prodId startDate
     * 执行开始日 endDate 执行结束日 batchGroupType 任务组模式 prodGroupType 产品组模式
     *
     * @return 启动结果
     */
    @Transactional
    public Boolean executeBatch( String selectedBatchGroup,String selectedProductGroup,
                                String startDate,String endDate,String batchGroupType,String prodGroupType) {
//        String selectedBatchGroup = RequestUtil.getParam("selectedBatchGroup");
//        String selectedProductGroup = RequestUtil.getParam("selectedProductGroup");
//        String startDate = RequestUtil.getParam("startDate");
//        String endDate = RequestUtil.getParam("endDate");
//        String batchGroupType = RequestUtil.getParam("batchGroupType");
//        String prodGroupType = RequestUtil.getParam("prodGroupType");

        String batchGroupSeqNo = selectedBatchGroup;
        String nowString = null;
        List<String> tasks = null;
        String systemCode = null;
        if (GlobalConstant.BATCH_EXECUTE_TYPE_SINGLE.equals(batchGroupType)) {
            tasks = new ArrayList<>(Arrays.asList(selectedBatchGroup.split(",")));
            systemCode = getSystemCode(tasks.get(0));
            batchGroupSeqNo = IDGenerator.generate();
            SysBatchGrp batchGroup = new SysBatchGrp();
            // 自动创建标识
            batchGroup.setSystemCode(systemCode);
            nowString = DateUtil.getCurrentDateTime();
            batchGroup.setSeqNo(batchGroupSeqNo);
            batchGroup.setBatchName( "测试类创建于" + nowString);
            batchGroup.setDetail(batchGroup.getBatchName() + ";启动批处理时创建。");
            insertGroup(batchGroup);

        } else {
            String[] batchGroupSeqNos = StringUtil.split(selectedBatchGroup, ",");
            tasks = selectGroupBySeqNo(batchGroupSeqNos);
            systemCode = getSystemCode(tasks.get(0));
        }

        String prodGroupSeqNo = selectedProductGroup;

        List<String> prodList = null;
        if (StringUtil.isNotEmpty(prodGroupSeqNo) && GlobalConstant.BATCH_EXECUTE_TYPE_SINGLE.equals(prodGroupType)) {
            prodGroupSeqNo = IDGenerator.generate();
            SysBatchPrdGrp productGroup = new SysBatchPrdGrp();
            // 自动创建标识
            productGroup.setSystemCode(systemCode);
            productGroup.setSeqNo(prodGroupSeqNo);
            productGroup.setGroupName(  "测试类创建于"
                    + (nowString == null ? DateUtil.getCurrentDateTime() : nowString));
            productGroup.setDetail(productGroup.getGroupName() + ";启动批处理时创建。");
            String[] prodGroupSeqNos = StringUtil.split(selectedProductGroup, ",");
            List<Product> productList = new ArrayList<Product>();
            BooksetComMapper booksetComMapper = (BooksetComMapper) context.getBean("BooksetComMapper");
            if (booksetComMapper != null) {
                productList = booksetComMapper.findProductByIds(prodGroupSeqNos);
            }
            Map<String, Product> productMap = productList.parallelStream()
                    .collect(Collectors.toMap(Product::getProdId, product -> product));
            List<SysBatchGrpPrdlink> products = new ArrayList<>();
            for (String prodId : prodGroupSeqNos) {
                SysBatchGrpPrdlink groupProducts = new SysBatchGrpPrdlink();
                groupProducts.setProductId(prodId);
                groupProducts.setGroupSeq(prodGroupSeqNo);
                groupProducts.setProductName(productMap.get(prodId).getProdName());
                products.add(groupProducts);
            }
            insertProdGroup(productGroup, products);

            prodList = Arrays.asList(selectedProductGroup.split(","));
        } else if (StringUtil.isNotEmpty(prodGroupSeqNo)
                && GlobalConstant.BATCH_EXECUTE_TYPE_GROUP.equals(prodGroupType)) {
            String[] prodGroupSeqNos = StringUtil.split(selectedProductGroup, ",");
            prodList = findAllProdbyGroup(prodGroupSeqNos);

        }

        // BS-890 【 批处理启动_估值】执行产品，未选择产品，不应给其他机构批跑了 add by lizhi 20200712 start
        if (StringUtil.isEmpty(prodGroupSeqNo) && GlobalConstant.BATCH_EXECUTE_TYPE_SINGLE.equals(prodGroupType)
                && DictCodeList.权限模式_联社模式.getValue().equals(CacheUtil.getSysParam(GlobalConstant.AUTHORITY_TYPE))
                && StringUtil.equals(DictCodeList.是否_是.getValue(), CacheUtil.getSysParam(GlobalConstant.DATA_AUTHORITY))) {
            //当前用户所属机构代码
            String createInst =  MultilevelDataAuthorityUtil.getService().queryOrganizationCode(LoginUserUtil.getUserName());
            if(StringUtil.isNotEmpty(createInst)){
                // 查询登录用户所属机构的所有产品
                ValBatchStart001Mapper valBatchStart001Mapper = (ValBatchStart001Mapper) context.getBean("ValBatchStart001Mapper");
                List<com.joyintech.tams.valuation.system.batchstart.dto.Product> productList =
                        valBatchStart001Mapper.findAllProdByInstitutionCode(createInst);
                if(null != productList && productList.size() > 0){
                    prodList = new ArrayList<String>();
                    for(com.joyintech.tams.valuation.system.batchstart.dto.Product product : productList){
                        prodList.add(product.getProdId());
                    }
                }
            }

            if(StringUtil.isNotEmpty(prodList)){
                selectedProductGroup = StringUtil.joinString(prodList, ",");
                prodGroupSeqNo = IDGenerator.generate();
                SysBatchPrdGrp productGroup = new SysBatchPrdGrp();
                // 自动创建标识
                productGroup.setSystemCode(systemCode);
                productGroup.setSeqNo(prodGroupSeqNo);
                productGroup.setGroupName(  "测试类创建于"
                        + (nowString == null ? DateUtil.getCurrentDateTime() : nowString));
                productGroup.setDetail(productGroup.getGroupName() + ";启动批处理时创建。");
                String[] prodGroupSeqNos = StringUtil.split(selectedProductGroup, ",");
                List<Product> productList = new ArrayList<Product>();
                BooksetComMapper booksetComMapper = (BooksetComMapper) context.getBean("BooksetComMapper");
                if (booksetComMapper != null) {
                    productList = booksetComMapper.findProductByIds(prodGroupSeqNos);
                }
                Map<String, Product> productMap = productList.parallelStream()
                        .collect(Collectors.toMap(Product::getProdId, product -> product));
                List<SysBatchGrpPrdlink> products = new ArrayList<>();
                for (String prodId : prodGroupSeqNos) {
                    SysBatchGrpPrdlink groupProducts = new SysBatchGrpPrdlink();
                    groupProducts.setProductId(prodId);
                    groupProducts.setGroupSeq(prodGroupSeqNo);
                    groupProducts.setProductName(productMap.get(prodId).getProdName());
                    products.add(groupProducts);
                }
                insertProdGroup(productGroup, products);
            }
        }
        // BS-890 【 批处理启动_估值】执行产品，未选择产品，不应给其他机构批跑了 add by lizhi 20200712 end

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        Date end = null;
        try {
            start = StringUtils.isEmpty(startDate) ? null : format.parse(startDate);
            end = StringUtils.isEmpty(endDate) ? null : format.parse(endDate);
        } catch (ParseException e) {
            logger.error("", e);
        }
        Map<String, String> dateMap = new HashMap<>();
        dateMap.put("startDate", startDate);
        dateMap.put("endDate", endDate);
        // 是否走调度
        boolean doBatchDispatch = false;
        String doDateChange = com.joyintech.tams.constant.DictCodeList.是否_是.getValue();
        if (GlobalConstant.BATCH_EXECUTE_TYPE_SINGLE.equals(batchGroupType) && (
                BATCH_DISPATCH.equals(selectedBatchGroup) || BATCH_UNISTART.equals(selectedBatchGroup))) {
            // 如果选择单批【调度批处理】或【统一启动】则走调度逻辑
            doBatchDispatch = true;
        }
        else if (GlobalConstant.BATCH_EXECUTE_TYPE_SINGLE.equals(batchGroupType)
                && BATCH_UNISTART_WITHOUT_DATECHANGE.equals(selectedBatchGroup)) {
            // 如果选择单批【统一启动_不日切】则走调度逻辑，且日切标识设为否
            doBatchDispatch = true;
            doDateChange = com.joyintech.tams.constant.DictCodeList.是否_否.getValue();;
        }
        else if (GlobalConstant.BATCH_EXECUTE_TYPE_GROUP.equals(batchGroupType) && tasks != null && tasks.size() == 1
                && (BATCH_DISPATCH.equals(tasks.get(0)) || BATCH_UNISTART.equals(tasks.get(0)))) {
            // 如果任务组只包含【调度批处理】或【统一启动】则走调度逻辑
            doBatchDispatch = true;
        }
        // 如果走调度，则更新账套日期
        SysBatchgrpExecInfo executeDetail = new SysBatchgrpExecInfo();
        if (doBatchDispatch) {
            executeDetail = getSysBatchgrpExecInfo(start, end, prodList, dateMap);
        }
        startDate = dateMap.get("startDate");
        endDate = dateMap.get("endDate");
        String detailSeqNo = IDGenerator.generate();
        // 任务实例
        List<SysBatchJob> insertJobInstList = new ArrayList<>();
        // 任务组优先级
        List<BatchGrpPri> insertGrpPriList = new ArrayList<>();
        // 维度值
        List<DimensionValue> insertDimValueList = new ArrayList<>();
        // 日期维度列表
        List<Date> dates = new ArrayList<>();
        // 如果走调度逻辑，则日期由调度处理，调度批只执行一次, 否则根据日期递增执行
        if (doBatchDispatch) {
            dates.add(start);
        } else {
            for (Date date = start; !date.after(end); date = DateUtil.addDay(date, 1)) {
                dates.add(date);
            }
        }
        for (Date exeDate : dates) {
            // 任务实例
            List<SysBatchJob> jobInstList = new ArrayList<>();
            String batchGroupSeqNoInst = IDGenerator.generate();
            for (String taskId : tasks) {
                SysBatchJob job = new SysBatchJob();
                job.setBatchSeqNo(batchGroupSeqNoInst);
                job.setTaskId(taskId);
                jobInstList.add(job);
            }
            insertJobInstList.addAll(jobInstList);
            // 任务组优先级
            BatchGrpPri batchGrpPri = new BatchGrpPri();
            batchGrpPri.setGroupSeqNo(batchGroupSeqNoInst);
            batchGrpPri.setDetailSeqNo(detailSeqNo);
            batchGrpPri.setPri(BigDecimal.ZERO);
            batchGrpPri.setExecuteDate(exeDate);
            insertGrpPriList.add(batchGrpPri);
            // 维度值
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setDimensionSeqNo("1");
            dimensionValue.setGroupInsSeqNo(batchGroupSeqNoInst);
            dimensionValue.setDimensionValue(DateUtil.format(exeDate));
            insertDimValueList.add(dimensionValue);
        }
        // 维度批处理必备参数
        insertDimensionTask(insertJobInstList, insertGrpPriList, insertDimValueList);

        executeDetail.setSystemCode(systemCode);
        executeDetail.setSeqNo(detailSeqNo);
        executeDetail.setBatchSeqNo(batchGroupSeqNo);
        executeDetail.setPrdGroupSeqNo(prodGroupSeqNo);
        executeDetail.setStartDate(DateUtil.parse(startDate));
        executeDetail.setEndDate(DateUtil.parse(endDate));
        executeDetail.setStatus(BatchDictCodeList.批处理执行状态_未执行.getValue());
        executeDetail.setCreateUser("测试类创建于");
        executeDetail.setUpdateUser("测试类创建于");
        executeDetail.setDetailName(StringUtil.isBlank(RequestUtil.getParam("detailName"))
                ? ( "测试类创建于" + DateUtil.getCurrentDateTime())
                : RequestUtil.getParam("detailName"));
        // 是否日切
        executeDetail.setOtherParam1(doDateChange);
        // 估值分组
        executeDetail.setOtherParam3(executeDetail.getSeqNo());
        // 批处理任务组执行信息
        insertBatchExecuteDetail(executeDetail);

        // 添加是否激活activemq的判断 added by qiangchengxi 2018年5月10日(星期四)
//		if (isActivemqActive == null || isActivemqActive) {
//			return executeBatch(executeDetail.getSeqNo());
//		} else {
//			return true;
//		}

        return true;
    }

    private String getSystemCode(String taskId) {
        DynamicDataSourceHolder.putDataSource(DataSourceType.OTHER);
        SysBatchTaskMapper sysBatchTaskMapper = (SysBatchTaskMapper) context.getBean("SysBatchTaskMapper");
        SysBatchTask task = sysBatchTaskMapper.selectByPrimaryKey(taskId);
        return task.getSystemCode();
    }

    private void insertGroup(SysBatchGrp batchGroup) {
        DynamicDataSourceHolder.putDataSource(DataSourceType.OTHER);
        try {
            SysBatchGrpMapper batchGroupMapper = (SysBatchGrpMapper) context.getBean("SysBatchGrpMapper");
            batchGroupMapper.insert(batchGroup);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            DynamicDataSourceHolder.clearDataSource();
        }
    }

    private List<String> selectGroupBySeqNo(String[] batchGroupSeqNos) {
        DynamicDataSourceHolder.putDataSource(DataSourceType.OTHER);
        List<String> tasks = new ArrayList<>();
        try {
            ValBatchStart001Mapper valBatchStart001Mapper = (ValBatchStart001Mapper) context.getBean("ValBatchStart001Mapper");
            tasks = valBatchStart001Mapper.findAllTaskbyGroup(batchGroupSeqNos);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            DynamicDataSourceHolder.clearDataSource();
        }
        return tasks;
    }

    private void insertProdGroup(SysBatchPrdGrp productGroup, List<SysBatchGrpPrdlink> products) {
        DynamicDataSourceHolder.putDataSource(DataSourceType.OTHER);
        try {
            ValBatchStart001Mapper valBatchStart001Mapper = (ValBatchStart001Mapper) context.getBean("ValBatchStart001Mapper");
            SysBatchPrdGrpMapper batchProductGroupMapper = (SysBatchPrdGrpMapper) context.getBean("SysBatchPrdGrpMapper");
            batchProductGroupMapper.insert(productGroup);
            valBatchStart001Mapper.batchInsertProdGroup(products);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            DynamicDataSourceHolder.clearDataSource();
        }
    }

    private List<String> findAllProdbyGroup(String[] prodGroupSeqNos) {
        DynamicDataSourceHolder.putDataSource(DataSourceType.OTHER);
        List<String> list = new ArrayList<>();
        try {
            ValBatchStart001Mapper valBatchStart001Mapper = (ValBatchStart001Mapper) context.getBean("ValBatchStart001Mapper");
            list = valBatchStart001Mapper.findAllProdbyGroup(prodGroupSeqNos);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            DynamicDataSourceHolder.clearDataSource();
        }
        return list;
    }

    private void insertDimensionTask(List<SysBatchJob> jobInstList, List<BatchGrpPri> batchGrpPriList,
                                     List<DimensionValue> dimValueList) {
        DynamicDataSourceHolder.putDataSource(DataSourceType.OTHER);
        try {
            ValBatchStart001Mapper valBatchStart001Mapper = (ValBatchStart001Mapper) context.getBean("ValBatchStart001Mapper");
            valBatchStart001Mapper.insertBatchJob(jobInstList);
            valBatchStart001Mapper.insertBatchGrpPri(batchGrpPriList);
            valBatchStart001Mapper.insertDimValue(dimValueList);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            DynamicDataSourceHolder.clearDataSource();
        }
    }

    private void insertBatchExecuteDetail(SysBatchgrpExecInfo executeDetail) {
        DynamicDataSourceHolder.putDataSource(DataSourceType.OTHER);
        try {
            SysBatchgrpExecInfoMapper batchExecuteDetailMapper = (SysBatchgrpExecInfoMapper) context.getBean("SysBatchgrpExecInfoMapper");
            batchExecuteDetailMapper.insert(executeDetail);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            DynamicDataSourceHolder.clearDataSource();
        }
    }

    public SysBatchgrpExecInfo getSysBatchgrpExecInfo(Date start, Date end, List<String> prodList,
                                                      Map<String, String> dateMap) {
        String startDate = dateMap.get("startDate");
        String endDate = dateMap.get("endDate");
        SysBatchgrpExecInfo executeDetail = new SysBatchgrpExecInfo();
        ValBatchStart001Mapper valBatchStart001Mapper = (ValBatchStart001Mapper) context.getBean("ValBatchStart001Mapper");
        // 获取所有账套
        List<BokBookset> booksetList = valBatchStart001Mapper.selectValBokBookset(start, end,
                prodList);
        // 开始日为空
        if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
            BaseCommonMapper baseCommonMapper = (BaseCommonMapper) context.getBean("BaseCommonMapper");
            String sysDate = DateUtil.format(baseCommonMapper.getNowSysDate());;
            startDate = sysDate;
            endDate = sysDate;
            // 画面没有选择日期
            executeDetail.setOtherParam2("N");
        }
        else {
            // 画面选择日期
            executeDetail.setOtherParam2("Y");
            if (booksetList != null && booksetList.size() > 0) {
                for (BokBookset var : booksetList) {
                    Date date1 = null;
                    if (start.compareTo(var.getStartDate()) >= 0) {
                        date1 = start;
                    }
                    else {
                        date1 = var.getStartDate();
                    }
                    // 更新账套日期表
                    BokBooksetDate record = new BokBooksetDate();
                    record.setBooksetId(var.getBooksetId());
                    record.setBooksetDate(date1);
                    BokBooksetDateMapper valBokBooksetDateMapper = (BokBooksetDateMapper) context.getBean("BokBooksetDateMapper");
                    // 切账套会计日期
                    valBokBooksetDateMapper.updateByPrimaryKeySelective(record);
                    //切通道子账套的会计日期
                    valBatchStart001Mapper.updateByBookRe(record);
                }
            }
        }
        dateMap.put("startDate", startDate);
        dateMap.put("endDate", endDate);
        return executeDetail;
    }

}
