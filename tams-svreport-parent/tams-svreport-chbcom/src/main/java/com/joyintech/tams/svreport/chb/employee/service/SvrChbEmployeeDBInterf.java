package com.joyintech.tams.svreport.chb.employee.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.joyintech.tams.common.svreport.base.service.BaseCommonCheckService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.base.entity.ChbEmployeeInfo;
import com.joyintech.tams.common.base.entity.ChbEmployeeInfoSub;
import com.joyintech.tams.common.svreport.chb.constant.SVReportGlobalConstant;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.constant.DictCodeListExt2;
import com.joyintech.tams.report.common.util.ChbDeadlineUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.svreport.chb.assetinforeg.dao.AssetInfoRegist001Mapper;
import com.joyintech.tams.svreport.chb.assetinforeg.dto.ChbAssetInfoSub001Dto;
import com.joyintech.tams.svreport.chb.datacheck.beforeinsert.service.ChbCommonCheckService;
import com.joyintech.tams.svreport.chb.employee.dao.SvrChbEmployeeDBInterfMapper;
import com.joyintech.tams.systemtools.constant.DataIndexSequenceType;
import com.joyintech.tams.systemtools.util.CommonUtil;


/**
 * 功能:从业人员信息接口
 * @since 1.0.0
 * @version 1.0.0
 * @作者：lingxq
 * @创建时间：2020年10月17日
 */
@SuppressWarnings("rawtypes")
@Service
@Transactional(rollbackFor = Exception.class)
public class SvrChbEmployeeDBInterf extends BaseService{
	
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SvrChbEmployeeDBInterfMapper svrChbEmployeeDBInterfMapper;
	
	/**
     * 数据批量一次查询或者删除条数
     */
    @Value("${application.reportSelectOrDeleteCount}")
    private int reportSelectOrDeleteCount;

    /**
     * 入库前校验用Service
     */
    @Autowired
    private ChbCommonCheckService chbCommonCheckService;
	
    public void generateData(Date batchDate) {
    	//1、开始检索维护表数据
    	logger.info("从业人员信息检索开始------->>>>>>");
    	List<ChbEmployeeInfo> list = svrChbEmployeeDBInterfMapper.selectBatchData(DateUtil.format(batchDate));
    	logger.info("从业人员信息检索结束-------<<<<<<");
    	
    	if(StringUtil.isEmptyList(list)){
    		logger.info("没有检索到数据,批处理结束!");
    		return;
    	}

		// 调用check逻辑
		logger.debug("从业人员信息登记数据检查开始------检查前数据个数：" + list.size());
		list = chbCommonCheckService.checkData(list, ChbCheckTableInfo.从业人员登记表SUB);
		logger.debug("从业人员信息登记数据检查结束------检查后数据个数：" + list.size());
		
		if(StringUtil.isEmptyList(list)){
			logger.info("检查后的数据全部不合规，批处理结束");
			return;
		} 
    	//2、查询已登记的人员信息
    	List<ChbEmployeeInfoSub> regList = svrChbEmployeeDBInterfMapper.selectRegEmployeeData();
    	//将已存在报送中的数据放入map中
        Map<String,ChbEmployeeInfoSub>   mapSub = regList.stream().collect(
                Collectors.toMap(ChbEmployeeInfoSub::getKey, chbEmployeeInfo001Sub -> chbEmployeeInfo001Sub));
        
    	//3、报送相关字段塞值处理
        //删除数据list
        List<ChbEmployeeInfoSub>  deleteList = new ArrayList<ChbEmployeeInfoSub>();
        //新建数据list
        List<ChbEmployeeInfoSub>  insertList = new ArrayList<ChbEmployeeInfoSub>();
        //中债报送截止日
		List<Date> dateList = null;
		
    	List<ChbEmployeeInfoSub> subList = new ArrayList<ChbEmployeeInfoSub>();
    	//复制上述【1】检索的数据
    	list.forEach(item -> {
    		ChbEmployeeInfoSub sub = new ChbEmployeeInfoSub();
    		BeanUtils.copyProperties(item, sub);
    		subList.add(sub);
    	});
    	
    	for(ChbEmployeeInfoSub sub : subList){
    		//数据日期
        	sub.setStatusDate(new Date());
            //创建人
        	sub.setCreateUser(CommonUtil.getUserName());
            //更新人
        	sub.setUpdateUser(CommonUtil.getUserName());
            //创建部门
        	sub.setCreateDept(CommonUtil.getDeptCode());
            //创建机构
        	sub.setOrgCode(CommonUtil.getOrgCode());
            //发行机构代码取值
        	sub.setIssuePartyId(null == sub.getIssuePartyId()?CommonUtil.selectBankCode():sub.getIssuePartyId());
            //报送流水号
			sub.setSumitSeq(CommonUtil.getSequenceIndexData(DataIndexSequenceType.中债登记管理));
            // 生成的登记都为未报送
			sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());

            if (mapSub.get(sub.getKey())!= null){
				// 临时表存在、业务表也存在 的情况下
            	ChbEmployeeInfoSub subYw = mapSub.get(sub.getKey());
				// （业务表中此条记录的.【报送状态】 == “02：接口报送”|| 业务表中此条记录的.【报送状态】 ==
				// “03：手工处理” ）
				if ((StringUtil.equals(DictCodeListExt2.报送状态_接口报送.getValue(), subYw.getSendStatus())
						|| StringUtil.equals(DictCodeListExt2.报送状态_手工处理.getValue(), subYw.getSendStatus()))) {
					// 当已经报送的数据状态为删除时
					if (StringUtil.equals(subYw.getStatus(), DictCodeListExt2.报送数据状态_删除.getValue())) {
						// tmp表数据为删除时
						if (StringUtil.equals(sub.getStatus(), DictCodeListExt2.数据状态_删除.getValue())) {
							// 删除数据List.add(此条记录）
							deleteList.add(sub);
						} else {
							// 删除数据List.add(此条记录）
							deleteList.add(sub);
							sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
							// 报送状态
							sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
							insertList.add(sub);
						}
					} else {
						// 反馈状态为发送失败和中债退回时候
						if (StringUtil.equals(subYw.getSubmitFeedbackStatus(), DictCodeListExt2.报送反馈状态_中债退回.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_发送失败.getValue())) {
							// tmp表数据为删除时
							if (StringUtil.equals(sub.getStatus(), DictCodeListExt2.数据状态_删除.getValue())) {
								// 删除未报送
								deleteList.add(sub);
							}
							// tmp表数据不为删除时
							else {
								// 删除数据List.add(此条记录）删除的是未报送数据
								deleteList.add(sub);
								// 内容不一致进行处理
								if (!compareEmployeeRegData(subYw, sub)) {
									// 此条记录的【数据状态】= 原来的【数据状态】
									sub.setStatus(subYw.getStatus());
									// 报送状态
									sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
									insertList.add(sub);
								}
							}
						} // 反馈状态为发送成功或中债通过或审核文件下载失败
						else if (StringUtil.equals(subYw.getSubmitFeedbackStatus(),
								DictCodeListExt2.报送反馈状态_发送成功.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_中债通过.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_审核文件下载失败.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_发送中.getValue())
								|| StringUtil.equals(subYw.getSubmitFeedbackStatus(),
										DictCodeListExt2.报送反馈状态_发送异常.getValue())) {
							// tmp表数据为删除时
							if (StringUtil.equals(sub.getStatus(), DictCodeListExt2.数据状态_删除.getValue())) {
								// 删除未报送
								deleteList.add(sub);
								// 新建 数据状态为删除数据状态，未报送数据
								sub.setStatus(DictCodeListExt2.报送数据状态_删除.getValue());
								// 报送状态
								sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
								insertList.add(sub);

							}
							// tmp表数据不为删除时
							else {
								// 删除数据List.add(此条记录）删除的是未报送数据
								deleteList.add(sub);
								// 两条记录的所有申报字段比较，存在不一致的情况下，进行以下处理
								if (!compareEmployeeRegData(subYw, sub)) {
									// 此条记录的【数据状态】= '02:修改'
									sub.setStatus(DictCodeListExt2.报送数据状态_修改.getValue());
									// 报送状态
									sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
									insertList.add(sub);

								}
							}
						}
					}
				} else {
					// 此条记录的【数据状态】= '02:新建'
					sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
					// 报送状态
					sub.setSendStatus(DictCodeListExt2.报送状态_未报送.getValue());
					// 删除数据List.add(此条记录）
					// 新建数据List.add(此条记录）
					deleteList.add(sub);
					insertList.add(sub);
				}
            } else{
                if (DictCodeListExt1.维护数据状态_删除.getValue().equals(sub.getStatus())){
                    deleteList.add(sub);
                    continue;
                } else {
                	sub.setStatus(DictCodeListExt2.报送数据状态_新建.getValue());
                    deleteList.add(sub);
                    insertList.add(sub);
                }
            }
			Map<String, List<Date>> dateMap = new HashMap<String, List<Date>>();
			dateList = ChbDeadlineUtil.getChbEndDate(
	                DictCodeListExt2.报表名称_从业人员信息登记.getValue(), sub.getStatus(), dateMap, DateUtil.now());
	        if (!CollectionUtils.isEmpty(dateList)) {
	        	sub.setChbSubmitDeadline(dateList.get(0));
	        	sub.setSubmitDeadline(dateList.get(1));
	        }
    	}
    	
    	//4、持久化DB
    	logger.info("从业人员信息入库开始------->>>>>>");
    	dbData(deleteList,insertList);
    	logger.info("从业人员信息入库结束-------<<<<<<");
    	
    }
    
    /**
     * 先删后插持久化数据
     * @param deleteList
     * @param insertList
     */
    private void dbData(List<ChbEmployeeInfoSub> deleteList, List<ChbEmployeeInfoSub> insertList) {
    	
    	if (StringUtil.isNotEmpty(deleteList)) {
		    // 插入前先删除未报送的登记数据
		    logger.debug("从业人员登记删除未报送数据：删除开始----------");
		    // 数据批量一次删除条数,如果参数设置过大则强制定位50
            int batchDeleteCount = reportSelectOrDeleteCount;
            // 每批最后一个的下标
            int batchDeleteLastIndex = batchDeleteCount;            
            for(int index = 0; index < deleteList.size();){
                if(batchDeleteLastIndex > deleteList.size()-1){
                    batchDeleteLastIndex = deleteList.size();
                    svrChbEmployeeDBInterfMapper.deleteBatchData(deleteList.subList(index, batchDeleteLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                	svrChbEmployeeDBInterfMapper.deleteBatchData(deleteList.subList(index, batchDeleteLastIndex));
                    index = batchDeleteLastIndex;//设置下一批下标
                    batchDeleteLastIndex = index + batchDeleteCount;                     
                }
            }
            logger.debug("从业人员登记删除未报送数据：删除结束----------");
            logger.debug("从业人员登记插入数据：插入开始----------");
	    		
            int batchinsertLastIndex = batchDeleteCount;        
    		for(int index = 0; index < insertList.size();){
                if(batchinsertLastIndex > insertList.size()-1){
                	batchinsertLastIndex = insertList.size();
                    svrChbEmployeeDBInterfMapper.insertChbEmployeeSubData(insertList.subList(index, batchinsertLastIndex));
                    break;//数据插入完毕,退出循环
                }else{
                	svrChbEmployeeDBInterfMapper.insertChbEmployeeSubData(insertList.subList(index, batchinsertLastIndex));
                    index = batchinsertLastIndex;//设置下一批下标
                    batchinsertLastIndex = index + batchDeleteCount;                     
                }
            }

            logger.debug("从业人员登记插入数据：插入结束----------");
		}
    	
    	
//		if(StringUtil.isEmptyList(deleteList)){
//			return ;
//		}
//		//考虑从业人员信息数据很少，无需分批操作
//		//先删除未报送数据 
//		svrChbEmployeeDBInterfMapper.deleteBatchData(deleteList);
//		
//		
//		if(StringUtil.isEmptyList(insertList)){
//			return ;
//		}
//		svrChbEmployeeDBInterfMapper.insertChbEmployeeSubData(insertList);
		
	}


	/**
     * @Description 判断从业人员登记数据是否有变
     * @param dto
     * @param regdto
     * @return boolean
     * @version 1.7 2019年5月22日
     */
    public boolean compareEmployeeRegData(ChbEmployeeInfoSub dto,ChbEmployeeInfoSub regdto){
    	ChbEmployeeInfoSub compare1 = genEmployeeDto(dto);
    	ChbEmployeeInfoSub compare2 = genEmployeeDto(regdto);
        if(JSON.toJSONString(compare1,SerializerFeature.WriteNullStringAsEmpty).equals(JSON.toJSONString(compare2,SerializerFeature.WriteNullStringAsEmpty))){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 对象封装
     * @param dto
     * @return
     */
	private ChbEmployeeInfoSub genEmployeeDto(ChbEmployeeInfoSub dto) {
		String emptyStr = "";
		// mysql对空字符串特殊处理
		if(BaseCommonCheckService.isMYSQLDataBase()){
			emptyStr = null;
		}
		ChbEmployeeInfoSub tmpDto = new ChbEmployeeInfoSub();
		tmpDto.setCreateTime(null);
		tmpDto.setUpdateTime(null);
		tmpDto.setEmployeeType(null==dto.getEmployeeType()?emptyStr:dto.getEmployeeType());
		tmpDto.setEmployeeName(null==dto.getEmployeeName()?emptyStr:dto.getEmployeeName());
		tmpDto.setEmployeeSex(null==dto.getEmployeeSex()?emptyStr:dto.getEmployeeSex());
		tmpDto.setIssuePartyId(null==dto.getIssuePartyId()?emptyStr:dto.getIssuePartyId());
		tmpDto.setIdentifyNumber(null==dto.getIdentifyNumber()?emptyStr:dto.getIdentifyNumber());
		tmpDto.setBelongsBank(null==dto.getBelongsBank()?emptyStr:dto.getBelongsBank());
		tmpDto.setBelongsPlace(null==dto.getBelongsPlace()?emptyStr:dto.getBelongsPlace());
		tmpDto.setCompanyName(null==dto.getCompanyName()?emptyStr:dto.getCompanyName());
		tmpDto.setBelongsDept(null==dto.getBelongsDept()?emptyStr:dto.getBelongsDept());
		tmpDto.setPosition(null==dto.getPosition()?emptyStr:dto.getPosition());
		tmpDto.setEducation(null==dto.getEducation()?emptyStr:dto.getEducation());
		tmpDto.setAcademicDegree(null==dto.getAcademicDegree()?emptyStr:dto.getAcademicDegree());
		tmpDto.setFirsttime2work(null==dto.getFirsttime2work()?emptyStr:dto.getFirsttime2work());
		tmpDto.setFirsttime2finwork(null==dto.getFirsttime2finwork()?emptyStr:dto.getFirsttime2finwork());
		tmpDto.setProfessionalTitle(null==dto.getProfessionalTitle()?emptyStr:dto.getProfessionalTitle());
		tmpDto.setFinProfessionalCrtf(null==dto.getFinProfessionalCrtf()?emptyStr:dto.getFinProfessionalCrtf());
		tmpDto.setFinTrainingNo(null==dto.getFinTrainingNo()?emptyStr:dto.getFinTrainingNo());
		tmpDto.setAwards(null==dto.getAwards()?emptyStr:dto.getAwards());
		tmpDto.setOfficeTel(null==dto.getOfficeTel()?emptyStr:dto.getOfficeTel());
		tmpDto.setMobileTel(null==dto.getMobileTel()?emptyStr:dto.getMobileTel());
		tmpDto.setMail(null==dto.getMail()?emptyStr:dto.getMail());
		tmpDto.setRegBusiType(null==dto.getRegBusiType()?emptyStr:dto.getRegBusiType());
		tmpDto.setRegEmployeeType(null==dto.getRegEmployeeType()?emptyStr:dto.getRegEmployeeType());
		return tmpDto;
	}
    
	
}
