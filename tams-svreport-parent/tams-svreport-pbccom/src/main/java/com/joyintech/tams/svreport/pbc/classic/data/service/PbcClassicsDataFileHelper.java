/*
 * 文件名：java
 * 版权：Copyright 2017-2018 JoyinTech. Co. Ltd. All Rights Reserved.
 * 描述：理财资管管理系统V2.0
 * 修改人：Administrator
 * 修改时间：2019年6月10日
 * 修改内容：新建
 * 系统名称：理财资管管理系统V2.0
 */
 
package com.joyintech.tams.svreport.pbc.classic.data.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.joyintech.framework.file.SafelyFile;
import com.joyintech.framework.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.joyintech.framework.constant.GlobalConstant;
import com.joyintech.framework.util.CacheUtil;
import com.joyintech.framework.util.StringUtil;
import com.joyintech.tams.common.svreport.chb.util.SvrDataUtil;
import com.joyintech.tams.common.svreport.seq.service.SysSeqService;
import com.joyintech.tams.report.common.util.ReportGlobalConstant;

/**
 * 详细DAT文件生成器
 * 文件存放：rootdir+组织机构代码+日期+压缩文件代码+序列(超过5M递增)+明细文件代码
 * @author Administrator
 * @version 1.0 2019年6月10日
 * @see PbcClassicsDataFileHelper
 * @since 1.0
 */
@Component
public class PbcClassicsDataFileHelper {
	/**
	 * 日志
	 */
	private static Logger logger = LoggerFactory.getLogger(PbcClassicsDataFileHelper.class);

    public static final String OperateType_ADD="01";
    public static final String OperateType_MODIFY="02";
    public static final String OperateType_DEL="03";												
    

    
    public static final String DATAFILE_TYPE_PPI="PPI";
    public static final String DATAFILE_TYPE_PCD="PCD";
    public static final String DATAFILE_TYPE_PVD="PVD";
    public static final String DATAFILE_TYPE_AF1="AF1";
    public static final String DATAFILE_TYPE_AF2="AF2";
    public static final String DATAFILE_TYPE_AF3="AF3";
    public static final String DATAFILE_TYPE_AF4="AF4";
    public static final String DATAFILE_TYPE_AF5="AF5";
	
    public static final String[] PPI={"PPB1","PIB1","PIB2","PIE1","PPE1"};
    public static final String[] PCD={"PCD1"};
    public static final String[] PVD={"PVD1","PVD2","PVD3"};
    public static final String[] AF1={"AF11","AF12"};
    public static final String[] AF2={"AF21","AF22"};
    public static final String[] AF3={"AF31","AF32"};
    public static final String[] AF4={"AF41","AF42","AF43"};
    public static final String[] AF5={"AF51","AF52","AF53","AF54","AF55"};
    public static Map<String,String[]> DATAMAP= null;
    
    // 手工处理和取消手工处理一次性处理的数据条数
    public static final Integer COMMIT_CONSTANTS = 800;

    private static SysSeqService sysSeqService;
    @Autowired
    public void setSysSeqService(SysSeqService sysSeqLogic) {
    	PbcClassicsDataFileHelper.sysSeqService = sysSeqLogic;
    }
	//add by zhoubin 20220425 start
	private static SysSeqService getSysSeqService() {
		if(PbcClassicsDataFileHelper.sysSeqService == null) {
			PbcClassicsDataFileHelper.sysSeqService = SpringContextUtil.getBean(SysSeqService.class);
		}
		return PbcClassicsDataFileHelper.sysSeqService;
	}
	//add by zhoubin 20220425 end
    /**
     * 生成文件
     * @param datas
     * @param type
     * @param dataType
     * @param seqDate
     * @param orgCode
     * @param seqMap
     * @param manualFlag
     * @param fileDataMap 【dat文件生成留痕】保存文件和数据主键list
     * @return
     */
    public static  List<String> genFile(List<LinkedHashMap<String,String>>datas,String type,String dataType,Date seqDate,String orgCode,Map<String,List<String>> seqMap, boolean manualFlag
    		,Map<String,Object> fileDataMap) {
    	
    	//【dat文件生成留痕】数据的key集合 
    	List<String> dataValueList = new ArrayList<String>();
    	//【dat文件生成留痕】key=目录名文件名，value=文件内数据的key集合 
    	Map<String,List<String>> pathfilenameDatakeylistMap;
    	if(null == fileDataMap.get("pathfilenameDatakeylist")) {
    		pathfilenameDatakeylistMap = new HashMap<String,List<String>>();
    		 fileDataMap.put("pathfilenameDatakeylist", pathfilenameDatakeylistMap);
    	}else {
    		pathfilenameDatakeylistMap = (Map<String, List<String>>) fileDataMap.get("pathfilenameDatakeylist");
    	}
    	String rootdir = SvrDataUtil.getSysParam("SVR_PBC_DAT_FILEPATH");
    	// 手工生成的文件存入temporary文件夹
    	if (manualFlag) {
    		rootdir += "temporary" +File.separator;
    	}
    	List<String> result = new ArrayList<String>();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(seqDate); 
        
        int num = 1;
        //序列Code
        String seqCode = orgCode+"_"+type;
        // 手工生成用独立序列
        if (manualFlag) {
        	seqCode += "_temporary";
    	}
        //压缩包名称
    	String filePath= type +"_" + orgCode +"_" + dateStr;
        //3位顺序号
        String seq = getSeqByNum(filePath,num,seqMap,seqCode,seqDate);
    	//dat文件写入
    	BufferedWriter writer = null;
		FileOutputStream fos = null;
		FileOutputStream fosx = null;
    	// 人行dat生成文件编码
    	String SVR_DAT_FILE_ENCODE = CacheUtil.getAppParamCache(GlobalConstant.SYS_PARA_KEY,ReportGlobalConstant.SVR_DAT_FILE_ENCODE);
    	if (StringUtil.isEmpty(SVR_DAT_FILE_ENCODE)) {
    		SVR_DAT_FILE_ENCODE = "UTF-8";
		}
        try {
            String fullPath = rootdir + filePath+"_"+seq;
            result.add(fullPath);
            createFolder(fullPath,type);
            long maxFileSize=100*1024*1024; //4.9M
            long fileSize = 0;
            File file = null;

            
            file = new SafelyFile(fullPath + "/"+ dataType + ".dat");
			fos = new FileOutputStream(file,true);
            writer = new BufferedWriter(new OutputStreamWriter(fos, SVR_DAT_FILE_ENCODE));
            
            for (LinkedHashMap<String,String> data:datas ) {
                if ( fileSize>=maxFileSize ) {
                	//PIB1特殊处理,每个产品的信息不能拆到两个文件中
                	if(!dataType.equals("PIB1")||"1".equals(data.get("type"))){
                		if ( writer!=null )
                            writer.close();
                        //文件大小超过5M，剩余数据放到下一文件夹里
                        num++;
                        seq = getSeqByNum(filePath,num,seqMap,seqCode,seqDate);
                        fullPath = rootdir + filePath+"_"+seq;
                        result.add(fullPath);
                        createFolder(fullPath,type);
//                        file = new File(fullPath + "/"+type + ".dat");
                        file = new SafelyFile(fullPath + "/"+dataType + ".dat");
                        fosx = new FileOutputStream(file,true);
                        writer = new BufferedWriter(new OutputStreamWriter(fosx, SVR_DAT_FILE_ENCODE));
                        // 【dat文件生成留痕】如果创建新的文件，则需要创建新的数据的key集合 
                        dataValueList = new ArrayList<String>();
                	}
                }
            	
                StringBuffer sb = new StringBuffer();
                for ( Entry<String,String>e:data.entrySet() ) {
                	if(e.getValue()!=null) {
                		//【dat文件生成留痕】将每条数据的主键（不为空）保存到list中;"datakey"的值不写入文件。
                		if(StringUtil.equals("datakey",e.getKey())) {
                			if(!StringUtils.isEmpty(e.getKey())) {
                				dataValueList.add(e.getValue());
                			}
                		}else {
                			sb.append(e.getValue()+"|");
                		}
                	}
                	else {
                		sb.append("|");
                	}
                }
                writer.write(sb.substring(0,sb.length()-1));//删除最后一个“|”
                writer.flush();
                writer.newLine();
                fileSize = file.length();
                
                // 【dat文件生成留痕】key=目录名文件名，value=数据的key集合 
				if (!pathfilenameDatakeylistMap.containsKey(fullPath + File.separator+ dataType + ".dat")) {
					pathfilenameDatakeylistMap.put(fullPath + File.separator+ dataType + ".dat", dataValueList);
				}
            }
        } catch (IOException e) {
			logger.error(e.getMessage(),e);
        } finally {
            try {
                if ( writer!=null )
                    writer.close();
                if (fos != null){
                	fos.close();
				}
				if (fosx != null){
					fosx.close();
				}
            } catch (IOException e) {
				logger.error(e.getMessage(),e);
            }
        }

        return result;
    }
    
    
    public static void createFolder(String folderName,String type){
    	//生成文件夹
    	File fp = new SafelyFile(folderName);
        if ( !fp.exists())
           fp.mkdirs();
        //生成各类型的空文件
        init();
        DATAMAP.get(type);
        for(String dataType:DATAMAP.get(type)){
        	File file = new SafelyFile(folderName + File.separator+dataType + ".dat");
        	if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
        	}
        }
    }
    
    public static String getSeqByNum(String filePath,int num,Map<String,List<String>> seqMap,String seqCode,Date seqDate){
    	String seq="";
    	if(seqMap.get(filePath)!=null&&((List<String>)seqMap.get(filePath)).size()>=num){
    		List<String> seqList = (List<String>)seqMap.get(filePath);
			seq = seqList.get(num-1);
    	}else{
    		if(seqMap.get(filePath)==null){
    			List<String> seqList = new ArrayList<String>();
    			seq = String.format("%03d", PbcClassicsDataFileHelper.getSysSeqService().getSysSeq(seqCode, seqDate, null));
    			seqList.add(seq);
    			seqMap.put(filePath, seqList);
    		}else{
    			List<String> seqList = (List<String>)seqMap.get(filePath);
    			seq = String.format("%03d", PbcClassicsDataFileHelper.getSysSeqService().getSysSeq(seqCode, seqDate, null));
    			seqList.add(seq);
    		}
    	}
    	return seq;
    }
    
    public static void init(){
    	if(DATAMAP==null){
    		DATAMAP = new HashMap<String,String[]>();
    		DATAMAP.put(DATAFILE_TYPE_PPI, PPI);
    		DATAMAP.put(DATAFILE_TYPE_PCD, PCD);
    		DATAMAP.put(DATAFILE_TYPE_PVD, PVD);
    		DATAMAP.put(DATAFILE_TYPE_AF1, AF1);
    		DATAMAP.put(DATAFILE_TYPE_AF2, AF2);
    		DATAMAP.put(DATAFILE_TYPE_AF3, AF3);
    		DATAMAP.put(DATAFILE_TYPE_AF4, AF4);
    		DATAMAP.put(DATAFILE_TYPE_AF5, AF5);
    	}
    }
    
    /**
	 * 生成文件标志码map
	 * @return 文件标志码map
	 */
	public static Map<String, String> getFileCodeMap() {
		Map<String, String> fileCodeMap = new HashMap<String, String>(16);
		fileCodeMap.put(DATAFILE_TYPE_PPI, DATAFILE_TYPE_PPI);
		fileCodeMap.put(DATAFILE_TYPE_PCD, DATAFILE_TYPE_PCD);
		fileCodeMap.put(DATAFILE_TYPE_PVD, DATAFILE_TYPE_PVD);
		fileCodeMap.put(DATAFILE_TYPE_AF1, DATAFILE_TYPE_AF1);
		fileCodeMap.put(DATAFILE_TYPE_AF2, DATAFILE_TYPE_AF2);
		fileCodeMap.put(DATAFILE_TYPE_AF3, DATAFILE_TYPE_AF3);
		fileCodeMap.put(DATAFILE_TYPE_AF4, DATAFILE_TYPE_AF4);
		fileCodeMap.put(DATAFILE_TYPE_AF5, DATAFILE_TYPE_AF5);
		return fileCodeMap;
	}
    
    /**
	 * 根据前台传入的文件标志码字符串组装实际所需文件标志码集合
	 * @param fileCodeIds 根据前台传入的文件标志码字符串
	 * @return 实际所需文件标志码集合
	 */
	public static List<String> buildFileCodeList(String fileCodeIds) {
		List<String> fileCodeList = new ArrayList<String>(16);
		if (StringUtil.isNotEmpty(fileCodeIds)) {
			String[] fileCodeArr = fileCodeIds.split(",");
			fileCodeList = Arrays.asList(fileCodeArr);
		}
		return fileCodeList;
	}
}
