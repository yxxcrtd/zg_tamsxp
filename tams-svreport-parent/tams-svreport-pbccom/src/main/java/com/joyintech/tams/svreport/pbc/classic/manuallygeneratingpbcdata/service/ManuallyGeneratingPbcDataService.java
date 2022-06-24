package com.joyintech.tams.svreport.pbc.classic.manuallygeneratingpbcdata.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.export.CommonExportService;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.report.interf.common.constant.DateUtil;
import com.joyintech.tams.svreport.pbc.classic.data.dto.PbcDataFile;
import com.joyintech.tams.svreport.pbc.classic.data.service.PbcClassicsDataFileService;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dao.PbcPrdAccountSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdacccountsub.dto.PbcPrdAccountSub;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.dao.PbcPrdBaseinfoSubMapper;
import com.joyintech.tams.svreport.pbc.classic.prdbaseinfosub.dto.PbcPrdBaseinfoSub;

/**
 * <p>
 * Title: ManuallyGeneratingpbcDataService<／p>
 * <p>
 * Description: 手工生成人行报送文件Service<／p>
 * <p>
 * Company: 安徽兆尹信息科技股份有限公司<／p>
 * 
 * @author gaojian
 * @date 2019年6月12日
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ManuallyGeneratingPbcDataService extends CommonExportService<PbcDataFile> {
	
	@Autowired
	private PbcPrdBaseinfoSubMapper pbcPrdBaseinfoSubMapper;
	
	@Autowired
	private PbcPrdAccountSubMapper pbcPrdAccountSubMapper;

	@Autowired
	private PbcClassicsDataFileService pbcClassicsDataFileService;
	/**
	 * 人行产品代码下拉框初始值
	 * @param param 
	 * 
	 * @return 人行产品代码下拉框初始值
	 */
	public List<SelectOptionDto> getPbcProdId(Map<String, Object> param) {
		List<PbcPrdBaseinfoSub> bcPrdBaseinfoSubList = pbcPrdBaseinfoSubMapper.queryAllPbcPrdBaseInfo(param);
		List<SelectOptionDto> resultList = new ArrayList<>();
		for (PbcPrdBaseinfoSub pbcPrdBaseinfoSub : bcPrdBaseinfoSubList) {
			SelectOptionDto resultDto = new SelectOptionDto();
			resultDto.setId(pbcPrdBaseinfoSub.getPbcProdId());
			resultDto.setText(pbcPrdBaseinfoSub.getProdName());
			resultList.add(resultDto);
		}
		return resultList;
	}
	
	/**
	 * 创建压缩文件
	 * @param param 页面参数
	 * @return 压缩文件之地
	 */
	public String createZipFile(Map<String, Object> param) {
		String filePath = "";
		String prodIds = (String) param.get("prodIds");
		if (StringUtil.isNotEmpty(prodIds)) {
			String[] pbcProdIdArr = prodIds.split(",");
			List<String> prodIdList = Arrays.asList(pbcProdIdArr);
			List<PbcPrdBaseinfoSub> pbcPrdBaseinfoSubList = new ArrayList<PbcPrdBaseinfoSub>(1<<4);
			List<PbcPrdAccountSub> pbcPrdAccountSubList = new ArrayList<PbcPrdAccountSub>(1<<4);
			param.put("prodIdList", prodIdList);
			for (String prodId : prodIdList) {
				PbcPrdBaseinfoSub pbcPrdBaseinfoSub = new PbcPrdBaseinfoSub();
				pbcPrdBaseinfoSub.setProdId(prodId);
				pbcPrdBaseinfoSubList.add(pbcPrdBaseinfoSub);
				PbcPrdAccountSub pbcPrdAccountSub = new PbcPrdAccountSub();
				pbcPrdAccountSub.setAccountId(prodId);
				pbcPrdAccountSubList.add(pbcPrdAccountSub);
			}
			// 产品基本信息列表,主要包含PROD_ID和PBC_PROD_ID
			List<PbcPrdBaseinfoSub> PbcPrdBaseinfoSubList1 = pbcPrdBaseinfoSubMapper.selectAllRegisteData(pbcPrdBaseinfoSubList);
			// 资产池基本信息列表,主要包含ACCOUNT_ID和PBC_ACCOUNT_ID
			List<PbcPrdAccountSub> pbcPrdAccountSubList1 = pbcPrdAccountSubMapper.selectAllRegisteData(pbcPrdAccountSubList);
			param.put("PbcPrdBaseinfoSubList", PbcPrdBaseinfoSubList1);
			param.put("pbcPrdAccountSubList", pbcPrdAccountSubList1);
		}
		filePath = pbcClassicsDataFileService.genDataFile( null, null,null, param);
		return filePath;
	}
	
	/**
	 * 下载压缩文件
	 * @param param 页面参数
	 * @return 压缩文件
	 */
	public ResponseEntity<byte[]> download(Map<String, Object> param) {
		String finalFile = (String) param.get("result");
		File sourceFiles = new File(finalFile);
		// 下载手工生成的文件并删除服务器中的文件
		return ExportUtil.export(sourceFiles.getName(), sourceFiles, true);
	}
    /**
     * 人行资产池代码下拉框初始值
     * @param param 
     * 
     * @return 人行产品代码下拉框初始值
     */
    public List<SelectOptionDto> getPbcAccountId(Map<String, Object> param) {
        List<PbcPrdAccountSub> bcPrdAccountBaseinfoSubList = pbcPrdAccountSubMapper.queryAllPbcAssetBaseInfo(param);
        List<SelectOptionDto> resultList = new ArrayList<>();
        for (PbcPrdAccountSub pbcPrdAccountBaseinfoSub : bcPrdAccountBaseinfoSubList) {
            SelectOptionDto resultDto = new SelectOptionDto();
            resultDto.setId(pbcPrdAccountBaseinfoSub.getPbcAccountId());
            resultDto.setText(pbcPrdAccountBaseinfoSub.getAccountName());
            resultList.add(resultDto);
        }
        return resultList;
    }
    
    /**
	 * 获取平台日期前一日 (用于生成日期初始化)
	 * @return 平台日期前一日
	 */
	public AjaxResult getYesterDayDate(){
        AjaxResult result = new AjaxResult();
        // 昨日日期
        String endDateStr = DateUtil.getYesterDayDate();
        Date endDate = DateUtil.parse(endDateStr);
        // 昨日所在月的月初日期
        Date beginDate = DateUtil.getFirstDay(endDate);
        String beginDateStr = DateUtil.format(beginDate);
        result.setData(beginDateStr + " - " + endDateStr);
        return result;
    }

    public List<SelectOptionDto> getInsideProdId(Map<String, Object> param) {
		List<Map<String,String>> list = pbcPrdAccountSubMapper.queryInsideProdId(param);
		List<SelectOptionDto> resultList = new ArrayList<>();
		for (Map<String,String> map : list) {
			SelectOptionDto resultDto = new SelectOptionDto();
			resultDto.setId(map.get("INSIDE_PROD_ID"));
			resultDto.setText(map.get("INSIDE_PROD_ID"));
			resultList.add(resultDto);
		}
		return resultList;
    }
}
