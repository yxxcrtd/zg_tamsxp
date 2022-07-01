package com.joyintech.tams.svreport.interf.dis.boksubject.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.tams.common.base.entity.BokSubjectErrodataWithBLOBs;
import com.joyintech.tams.common.base.entity.ChbInterfConfigDto;
import com.joyintech.tams.common.svreport.base.service.BatchInsertService;
import com.joyintech.tams.common.svreport.chb.util.ChbInterfUtil;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.dto.ChbCheckTableInfo;
import com.joyintech.tams.common.svreport.datacheck.beforeinsert.service.CommonCodeCheckBatchService;
import com.joyintech.tams.constant.DictCodeListExt1;
import com.joyintech.tams.report.interf.common.constant.InterfConstants;
import com.joyintech.tams.report.interf.common.imp.ImportGenerator;
import com.joyintech.tams.report.interf.common.imp.sqlldr.ImpTable;
import com.joyintech.tams.systemtools.dto.ExcelValidDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.joyintech.tams.common.FileImpService;
import com.joyintech.tams.common.InterfDefine;
import com.joyintech.tams.common.InterfFile;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.svreport.interf.dis.boksubject.dao.DisBokSubjectMapper;
import com.joyintech.tams.svreport.interf.dis.boksubject.dto.BokSubject;
import com.joyintech.tams.svreport.interf.dis.common.DisInterfDecimalUtil;

/**
 * 科目表接口
 * 
 * @author dongxiangyang
 *
 */
@Service
public class BokSubjectInterfService extends FileImpService {

	@Autowired
	private DisBokSubjectMapper bokSubjectMapper;
	@Autowired
	private BatchInsertService batchInsertService;
	@Autowired
	private CommonCodeCheckBatchService commonCodeCheckBatchService;
	/**
	 * 长sql最大一次插入或者更新条数
	 */
	@Value("${application.reportInsertLongCount}")
	public int reportInsertLongCount;

	/**
	 * 重写saveData方法 科目表信息入库 listData 费用交易list saveFlag 入库标识
	 */
	@Override
	public String saveData(List<Map<String, String>> listData, String saveFlag, String dataSource) {
		if (StringUtil.isEmptyList(listData)) {
			logger.info("saveData方法参数listData为空。");
			return "success";
		}
		// 全部删除
		bokSubjectMapper.delete();
		List<BokSubject> list = new ArrayList<>();
		// modify by convertMapToEntity yangxianjiu 2022-01-19 start
		List<BokSubject> listTemp = new ArrayList<>();
		list = (List<BokSubject>) convertMapToEntity(listData,BokSubject.class, dataSource);
		List<BokSubjectErrodataWithBLOBs> bokSubjectErrodataList = Lists.newArrayList();
		list.forEach(e->{
			e.setStatus(DictCodeListExt1.维护数据状态_有效.getValue());

			BokSubjectErrodataWithBLOBs bokSubjectErrodataWithBLOBs = new BokSubjectErrodataWithBLOBs();
			bokSubjectErrodataWithBLOBs.setBooksetId(e.getBooksetId());
			bokSubjectErrodataWithBLOBs.setSubjectNo(e.getSubjectNo());
			bokSubjectErrodataList.add(bokSubjectErrodataWithBLOBs);
		});
		commonCodeCheckBatchService.deleteBokSubjectErrodata(bokSubjectErrodataList);
		//add by zhoubin 20220518 start
		if (list.size()<reportInsertLongCount) {
			bokSubjectMapper.insert(list);
		}else {
			try {
				//sqlloader插入
				ImpTable table = new ImpTable("BOK_SUBJECT");
				table.setColumns(ImportGenerator.createImpTableColumns(table.getName()));
				// 临时文件存放目录
				String tmpPath = CacheUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);

				batchInsertService.exeBatchldr(tmpPath, list, table);
			} catch (Exception e) {
				logger.error("sqlloader插入失败{}",e.getMessage());
				return "fail";
			}

		}
		//add by zhoubin 20220518 end
		return "success";
	}

	 /**
     * 数据校验
     * 
     * @param dataList 入库的数据
     * @param interfFile 文件名
     * @see
     * @since 1.0
     */
    private void checkSaveData(List<Map<String, String>> dataList, InterfFile interfFile) {
        //接口类型
        String inerfType = InterfDefine.科目数据.getCode();
        //检查数据类型
        String dataType = InterfDefine.科目数据.getCode();
        //重复性检查的keys
        String[] checkEuplicatedKeys = {"PROD_ID","SUBJECT_NO"};
        String filename = interfFile == null ? null : interfFile.getFilename();
        List<Map<String, String>>  listA = super.checkData(dataList, filename, inerfType, dataType,null, checkEuplicatedKeys);
//        dataList.addAll(listA);// listA为错误数据，在checkData时被remove，需要重新放回dataList
    }
	
	@Override
	public String importDB(List<Map<String, String>> listData, InterfFile interfFile, String date) {
		 String rs = "success";
	        // 对数据list进行处理
	        if (!CollectionUtils.isEmpty(listData)) {
	            checkSaveData(listData, interfFile);
	            // 数据入库
	            rs = saveData(listData, null,null);
	        }
	        return rs;
	}

	@Override
	public String importDB(List<ExcelValidDto> listExcel, List<Map<String, String>> listData, InterfFile interfFile, String date) {

		ChbInterfConfigDto interfConfig = ChbInterfUtil.queryInterfConfigByInterfid(InterfDefine.科目数据.getCode());
		if (interfConfig == null){
			logger.info("CHB_INTERF_CONFIG表中无配置项！");
			return "fail";
		}
		checkImportData(listExcel,listData, interfFile.getFilename(), ChbCheckTableInfo.科目定义,interfConfig.getCoverFlag(), listExcel == null? "I" : "D");

		String saveFlag = interfConfig.getContentType();
		String result = "";
		if(listData.size()>0){
			result=saveData(listData, listExcel == null?saveFlag: InterfConstants.SAVEFLAG_ADD, listExcel == null? "I" : "D");
		}
		// 更新系统配置时间
		ChbInterfUtil.updateInterfTimeBySysdate("BOK_SUBJECT");
		return result;
	}

}
