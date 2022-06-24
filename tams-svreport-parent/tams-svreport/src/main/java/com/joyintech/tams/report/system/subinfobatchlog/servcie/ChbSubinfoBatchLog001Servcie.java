package com.joyintech.tams.report.system.subinfobatchlog.servcie;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.joyintech.framework.file.SafelyFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.joyintech.framework.ajax.AjaxResult;
import com.joyintech.framework.base.BaseService;
import com.joyintech.framework.base.dto.UploadFile;
import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.grid.Page;
import com.joyintech.framework.listener.FileAttachmentProcess;
import com.joyintech.framework.select.SelectOptionDto;
import com.joyintech.framework.util.DateUtil;
import com.joyintech.framework.util.ExportUtil;
import com.joyintech.framework.util.JsonUtils;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.framework.util.ZipFileUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.report.common.constant.SvrServiceApplication;
import com.joyintech.tams.report.system.subinfobatchlog.dto.ChbSubinfoBatchLog001Dto;

/**
 * 中债报送信息登记生成日志Service
 * 
 * @author zhangyf
 */
@Service
public class ChbSubinfoBatchLog001Servcie extends BaseService<ChbSubinfoBatchLog001Dto> {
	/**
	 * 日志
	 */
	private Logger logger = LoggerFactory.getLogger(ChbSubinfoBatchLog001Servcie.class);

	@Autowired
    private FileAttachmentProcess fileAttachmentProcess;

	public List<SelectOptionDto> getUserList() {
		return restTemplate.getForList(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/selectUserList",
				SelectOptionDto.class);
	}

	public Page<ChbSubinfoBatchLog001Dto> queryBatchResult(Map<String, Object> param) {
		return restTemplate.postForPage(SvrServiceApplication.COMMON_SERVICE_NAME + "/ChbSubinfoBatchLog/findPage",
				param, ChbSubinfoBatchLog001Dto.class);
	}
	
	/**
     * 检查选择的压缩文件是否存在
     * @return 不存在的文件清单
     */
	public AjaxResult checkFileExists(Map<String, Object> param) {
		AjaxResult result = new AjaxResult();
		StringBuilder stringBuilder = new StringBuilder();
		List<ChbSubinfoBatchLog001Dto> fileList = new ArrayList<ChbSubinfoBatchLog001Dto>(16);
		try {
			fileList = JsonUtils.json2List(param.get("keyList").toString(), ChbSubinfoBatchLog001Dto.class);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}		
		for (ChbSubinfoBatchLog001Dto dataFile : fileList) {
			UploadFile	 upd=  fileAttachmentProcess.fetch(dataFile.getCompresFileId());
			if(upd!=null){
				continue;
			}
			if(!StringUtil.isNotEmpty(dataFile.getCompresFilePath())) {
				stringBuilder.append("没有文件").append(",");
				continue;
			}
			File file = new SafelyFile(dataFile.getCompresFilePath());
			if (!file.exists()) {
				String filePath = dataFile.getCompresFilePath();
				String[] filePathSplit = filePath.split("/");
				String zipName = filePathSplit[filePathSplit.length - 1];
				stringBuilder.append(zipName).append(",");
			}
		}
		// 删除最后一个字符“,”
		if (stringBuilder.length() > 0) {
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		result.setData(stringBuilder.toString());
		return result;
	}
	
	/**
	 * 下载压缩文件
	 * @param param 页面参数
	 * @return 压缩文件
	 */
	public ResponseEntity<byte[]> download(Map<String, Object> param) {
		List<ChbSubinfoBatchLog001Dto> fileList = new ArrayList<ChbSubinfoBatchLog001Dto>(16);
		try {
			fileList = JsonUtils.json2List(param.get("keyList").toString(), ChbSubinfoBatchLog001Dto.class);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		List<File> sourceFiles = new ArrayList<File>(16);
		for (ChbSubinfoBatchLog001Dto dataFile : fileList) {
			File file = new SafelyFile(dataFile.getCompresFilePath());
			if(!file.exists()){
				UploadFile	 upd=  fileAttachmentProcess.fetch(dataFile.getCompresFileId());
				sourceFiles.add(upd.getTargetFile());
			}else{
				sourceFiles.add(file);
			}
		}
		// 只有一个文件,直接导出,且不能删除源文件
		if (sourceFiles.size() == 1) {
			String zipFileName = fileList.get(0).getFileType() + DateUtil.getCurrentDateTime() + ".zip";
			zipFileName = zipFileName.replaceAll(":", "-");
			return ExportUtil.export(zipFileName, sourceFiles.get(0), false);
		}
		// 如果生成文件数量大于1,需要压缩之后导出
		File outFile = null;
		if (sourceFiles.size() > 1) {
			String exportFilePath = SvrDataUtil.getSysParam(GlobalConstant.COM_PROP_ACCESS_EXPORTPATH);
			String zipFileName = "中债报送文件" + DateUtil.getCurrentDateTime() + ".zip";
			zipFileName = zipFileName.replaceAll(":", "-");
			outFile = new SafelyFile(exportFilePath + File.separator + zipFileName);
            // 导出路径不存在则重新生成
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
			try {
				ZipFileUtil.compressZip(outFile, false, sourceFiles.toArray(new SafelyFile[] {}));
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
//				// 压缩文件失败后，删除所有临时文件
//				for (File file : sourceFiles) {
//					if (file.exists()) {
//						file.delete();
//					}
//				}
			}
			return ExportUtil.export(outFile.getName(), outFile, true);
		}
		return null;
	}
}
